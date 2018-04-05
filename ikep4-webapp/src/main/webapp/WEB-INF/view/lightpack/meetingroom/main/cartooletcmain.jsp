<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 

<c:set var="user" value="${sessionScope['ikep.user']}" />

<style type="text/css">
	#timeListTable { border:1px solid #ccc; border-collapse:collapse; }
	#timeListTable .ui-selecting { background: #FECA40; }
	#timeListTable .ui-selected { background: #F39814; }
	#timeListTable td { border:1px solid #ccc; text-align:center; line-height:24px; }
	#timeListTable tbody td { width:24px; height:24px; }
	#timeListTable tbody td span { display:none; }
</style>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/meetingroom/config.js"/>"></script>

<c:if test="${param.mode == 'schedule'}">
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.json-2.2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.postjson.js"/>"></script>
<script type="text/javascript"><!-- 일정에서 팝업으로 오픈했을때만 수행(dialog) -->
var fnCaller, applyMeetingRoom;
(function($) {
	fnCaller = function(schedule, dialog) {
		//alert(schedule);
		plannerScheduleInfo = schedule;
		applyMeetingRoom = function(reserveInfo) {
			schedule.startDate = reserveInfo.startTime;
			schedule.endDate = reserveInfo.endTime;
			
			//var parseDay = reserveInfo.day.split("-");
			//reserveInfo.day = new Date(parseDay[0], parseInt(parseDay[1], 10)-1, parseDay[2]);
			
			//if(schedule && schedule.repeat) {
				//var parseFromTime = reserveInfo.from.split(":"),
				//	parseToTime = reserveInfo.to.split(":");
				
				//var startDate = new Date(parseDay[0], parseInt(parseDay[1], 10)-1, parseDay[2], parseFromTime[0], parseFromTime[1]),
				//	endDate = new Date(parseDay[0], parseInt(parseDay[1], 10)-1, parseDay[2], parseToTime[0], parseToTime[1]);
				
				schedule.cartooletcId = reserveInfo.cartooletcId;
				schedule.wholeday = "0";
				//schedule.startDate = startDate;
				//schedule.endDate = endDate;
				
				//var recurrence = schedule.recurrences[0];
				//recurrence.sdStartDate = startDate;
				//recurrence.sdEndDate = endDate;
 
				$("body").ajaxLoadStart();
				$.postJSON(iKEP.getContextRoot() + "/lightpack/meetingroom/reserve/checkCarDuplicate.do", schedule, function(res) {
					if(res.length > 0) {
						alert(langMsg.duplicateReserve);
					} else {
						//alert(langMsg.availableReserve);
						dialog.callback(reserveInfo);
					}
					
					$("body").ajaxLoadComplete();
				});
			//} else {
			//	dialog.callback(reserveInfo);
			//}
		};
	};
})(jQuery);
</script>
</c:if>

<script type="text/javascript">
<!--
var plannerScheduleInfo;
var langMsg = iKEPLang.meetingroom.messageText;
var langRes = iKEPLang.meetingroom;
var configStartHour = 7, endHour = 24;
var reservePerHeight = 20;

var locationManager;
function LocaleManager() {
	var that = this;
	this.currentLocation;
	
	(function initial() {
		this.currentLocation = location.hash;
		setInterval(function() { that.checkLocation(); }, 200);
	})();
}

LocaleManager.prototype.changeLocation = function(hash) {
	hash = "#" + hash;
	if(this.currentLocation != hash) {
		this.currentLocation = hash;
		location.href = location.pathname + location.search + this.currentLocation;
	}
};

LocaleManager.prototype.checkLocation = function() {
	if(location.hash && this.currentLocation != location.hash) {
		var hashs = location.hash.substring(1, location.hash.length).split("&");
		for(var i=0;i<hashs.length;i++) {
			var param = hashs[i].split("=");
			switch(param[0]) {
				case "tabs" : //clickTab(param[1]);
					switch(param[1]) {
						case "place" :		jQuery("#divTab").tabs("select", 0); break;
						case "day" :		jQuery("#divTab").tabs("select", 1); break;
						case "week" :		jQuery("#divTab").tabs("select", 2); break;
						case "recent" :		jQuery("#divTab").tabs("select", 3); break;
						case "favorite" :	jQuery("#divTab").tabs("select", 4); break;
					}
					break;
			}
		}
	}
};

function cloneDate(date) {
	return new Date(date.getTime());
}

function strDateToDate(str) {	//201208271330
	return new Date(
		str.substring(0, 4),
		str.substring(4, 6)-1,
		str.substring(6, 8),
		str.substring(8, 10),
		str.substring(10, 12),
		0
	);
}

(function($) {
	clickTab = function(tab) {
		var url = "<c:url value='/lightpack/meetingroom/'/>" + tab + "/cartooletcMain.do?mid="+"${param.mid}";
		
		$jq.ajax({     
			url : url,     
			type : "get",     
			loadingElement : {
				container:"#tabs"
			}, 
			success : function(result) {  
				$jq("#tabs").html(result);
				//locationManager.changeLocation("tabs=" + menu);
			},
			error : function(event, request, settings) { 
				alert("error"); 
			}
		});  
	};
	
	reset = function() {
		$("#category").val("");
		$("#region").val("");
		$("#date").val("");
	};
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		$("#divTab").tabs({
			create : function() {
				$(this).tabs("select", 0);
			},
			select : function() {
				//iKEP.debug(arguments);
			}
		});
		
		//locationManager = new LocaleManager();
	});
	
	getBolckSize = function($el) {
		//alert("position().left:"+$el.position().left+" offset().left:"+$el.offset().left+" $el.outerWidth():"+$el.outerWidth());
		return {width:$el.outerWidth(), height:$el.outerHeight()};
	}
	
	createReserveUI_V = function(reserveInfo) {

		var $div = $('<div class="reserve" style="width:99%" />').click(function(event) {
			event.stopPropagation();
			if(!reserveInfo.schedulePublic) getReserveView(reserveInfo, "place");
		});
		$div.addClass(reserveInfo.approveStatus == "A" ? "approve" : "waiting");
		
		var posTop = (reserveInfo.startDate.getHours() - configStartHour) * (reservePerHeight*2) +	// 시간 단위 위치
						parseInt(reserveInfo.startDate.getMinutes()/60*(reservePerHeight*2), 10);	// 분단위 위치

		var height = parseInt((reserveInfo.endDate - reserveInfo.startDate) / 60000 / 30 * reservePerHeight, 10);
		setReserveInfomation($div, reserveInfo, "V");
		return $div.css({top:posTop+"px", height:height+"px"});
	}
	
	createReserveUI_H = function(reserveInfo, roomIndex) {
		//alert(parent.$jq("body").css("padding-left"));
		//alert(parent.$jq("#LeftContainer").outerWidth());
		var leftFlipSize = parent.$jq("#LeftContainer").outerWidth();
		var paddinLeft = parent.$jq("body").css("padding-left");
		var $div = $('<div class="reserve"/>').click(function(event) {
			event.stopPropagation();
			if(!reserveInfo.schedulePublic) {
				if($("#categoryId").is("*")) 
					currentCategoryId = $("#categoryId").val();
				getReserveView(reserveInfo, "day");
			}
		});
		$div.addClass(reserveInfo.approveStatus == "A" ? "approve" : "waiting");
		
		var blockSize = getBolckSize($(".meeting_schedule_v").find("td:first"));	// UI 단위 시간의 블럭  사이즈
		var leftOffset = 159;
		if(paddinLeft=="0px"&&leftFlipSize==170){
			leftOffset = leftOffset-16;
		}
		
		if(paddinLeft=="8px"){
			if(leftFlipSize==170){
				leftOffset = leftOffset-12;
			}else{
				leftOffset = leftOffset-16;
			}
		}
		
		var posTop = roomIndex * blockSize.height;
		var posLeft = leftOffset + (reserveInfo.startDate.getHours() - configStartHour) * (blockSize.width*2) +	// 시간 단위 위치
						parseInt(reserveInfo.startDate.getMinutes()/30*blockSize.width, 10);	// 분단위 위치

		var width = parseInt((reserveInfo.endDate - reserveInfo.startDate) / 60000 / 30 * blockSize.width, 10);
		setReserveInfomation($div, reserveInfo);
		return $div.css({left:posLeft+"px", top:posTop+"px", width:width+"px"});

	}
	
	createReserveUIWeek = function(reserveInfo, startDate, roomIndex) {
		
		var leftFlipSize = parent.$jq("#LeftContainer").outerWidth();
		var paddinLeft = parent.$jq("body").css("padding-left");
		
		
		var $div = $('<div class="reserve"/>').click(function(event) {
			event.stopPropagation();
			if(!reserveInfo.schedulePublic) {
				currentCategoryId = $("#categoryId").val();
				getReserveView(reserveInfo, "week");
			}
		});
		$div.addClass(reserveInfo.approveStatus == "A" ? "approve" : "waiting");
		
		var dayNum = parseInt((reserveInfo.startDate - startDate) /(24*60*60*1000), 10);
		var blockSize = getBolckSize($(".meeting_schedule_v").find("td:first"));	// UI 단위 시간의 블럭  사이즈
		
		var leftOffset = 159;
		if(leftFlipSize==170&&paddinLeft=="0px"){
			leftOffset = leftOffset+3;
		}
		
		if(paddinLeft=="8px"){
			if(leftFlipSize==170){
				leftOffset = leftOffset-12;
			}else{
				leftOffset = leftOffset-16;
			}
		}
		
		var leftOffset = leftOffset + (blockSize.width * 4 * dayNum);
		
		var posTop = roomIndex * blockSize.height;
		var posLeft = leftOffset + (reserveInfo.startDate.getHours() - configStartHour) * ((blockSize.width-10)/3) +	// 시간 단위 위치
						parseInt(reserveInfo.startDate.getMinutes()/180*(blockSize.width-10), 10);	// 분단위 위치
		var width = parseInt((reserveInfo.endDate - reserveInfo.startDate) / 60000 / 180 * (blockSize.width-10), 10);
					
		setReserveInfomation($div, reserveInfo);
		return $div.css({left:posLeft+"px", top:posTop+"px", width:width+"px"});
	}
	
	setReserveInfomation = function($el, reserveInfo, diraction) {
		var timeInfo = {sHour:reserveInfo.startDate.getHours(), sMinute:reserveInfo.startDate.getMinutes(), eHour:reserveInfo.endDate.getHours(), eMinute:reserveInfo.endDate.getMinutes()};
		
		reserveInfo.startTime = (timeInfo.sHour < 10 ? "0"+timeInfo.sHour : timeInfo.sHour) + ":" + (timeInfo.sMinute < 10 ? "0"+timeInfo.sMinute : timeInfo.sMinute);
		reserveInfo.endTime = (timeInfo.eHour < 10 ? "0"+timeInfo.eHour : timeInfo.eHour) + ":" + (timeInfo.eMinute < 10 ? "0"+timeInfo.eMinute : timeInfo.eMinute);
		
		$el.hover(function() {
			$("#divReserveDialog").empty()
				.append($.tmpl($("#tmplReserveInfo").template(), reserveInfo))
				.show();
			var elPos = $el.position();
		    $("#divReserveDialog").css(
		    	diraction == "V" ? 
		    		{top : (elPos.top - $("#divReserveDialog").outerHeight()) + "px"} : 
		    		{top : (elPos.top - $("#divReserveDialog").outerHeight()) + "px", left : ((elPos.left+$el.outerWidth()/2) - ($("#divReserveDialog").outerWidth()/2)) + "px"}
		    );
		}, function() {
			$("#divReserveDialog").hide();
		});
	}
	
	getReserveView = function(reserveInfo, where) {
		//var url  = "<c:url value="/lightpack/meetingroom/reserve/reserveView.do"/>?dialog=1&where="+where+"&scheduleId=" + reserveInfo.scheduleId+"roomortool=tool";
		
		//$("#tabs").load(url);
	};
	
	<c:choose>
	<c:when test="${param.mode == 'schedule'}"><%-- 일정에서 예약시 --%>
	reserveCartooletcFrom = function(meetingRoomInfo, startTime, endTime) {
			var currTime = iKEP.getCurTime();
			// 일정에서 선택한 시간이 회의실 예약 가능 시간이 지나지 않았으면...
			if(plannerScheduleInfo.startDate.getTime() > (currTime.getTime() + (meetingRoomConfig.minReservationTime*60*1000))) {
				var message = "일정 작성시간 [%w]으로 \n선택하신 ["+meetingRoomInfo.cartooletcName+"]자원의 예약이 진행됩니다.";
				var ssDate = plannerScheduleInfo.startDate.toString();
				var seDate = plannerScheduleInfo.endDate.toString();
				var reserveTime = ssDate.substring(0, 4) + "-" + ssDate.substring(4, 6) + "-" + ssDate.substring(6, 8) + " " + ssDate.substring(9, 14) + 
									" ~ " + seDate.substring(0, 4) + "-" + seDate.substring(4, 6) + "-" + seDate.substring(6, 8) + " " + seDate.substring(9, 14);
				
				alert(message.replace("%w", reserveTime));
				startTime = new Date(plannerScheduleInfo.startDate.getTime());
				endTime = new Date(plannerScheduleInfo.endDate.getTime());
			}
			
			
			reserveWithMeetingRoomSchedule(meetingRoomInfo, startTime, endTime);
			
		};
		
		reserveWithMeetingRoomSchedule = function(meetingRoomInfo, startTime, endTime) {
			var currTime = iKEP.getCurTime();
			if(startTime < currTime) {	// 현재 시간보다 이전 시간이면 예약 불가
				alert(langMsg.noReservePastTime);
				return false;
			} else {
				if(meetingRoomConfig.minReservationTime && (startTime.getTime() < currTime.getTime() + (meetingRoomConfig.minReservationTime*60*1000))) {	// 예약 가능 시간인지 확인
					alert(langMsg.noReserveReadyTime.replace("%w", meetingRoomConfig.minReservationTime));
					return false;
				}
			}
			
			applyMeetingRoom({
				cartooletcId : meetingRoomInfo.cartooletcId,
				cartooletcName : meetingRoomInfo.cartooletcName,
				buildingName : meetingRoomInfo.buildingName,
				floorName : meetingRoomInfo.floorName,
				startTime : startTime,
				endTime : endTime
			});
		}
	</c:when>
	<c:otherwise>
	reserveCartooletcFrom = function(cartooletcInfo, startTime, endTime) {
		var currTime = iKEP.getCurTime();
		if(startTime < currTime) {	// 현재 시간보다 이전 시간이면 예약 불가
			alert("지난 시간에는 예약할 수 없습니다.");
			return false;
		} else {
			if(meetingRoomConfig.minReservationTime && (startTime.getTime() < currTime.getTime() + (meetingRoomConfig.minReservationTime*60*1000))) {	// 예약 가능 시간인지 확인
				alert(langMsg.noReserveReadyTime.replace("%w", meetingRoomConfig.minReservationTime));
				return false;
			}
		}
		
		
		$("#tabs").load("<c:url value="/lightpack/meetingroom/reserve/reserveCarForm.do"/>", {
			cartooletcId : cartooletcInfo.cartooletcId,
			day : startTime.getFullYear() + "-" + (startTime.getMonth()+1) + "-" + startTime.getDate(),
			from : startTime.getHours() + ":" + startTime.getMinutes(),
			to : endTime.getHours() + ":" + endTime.getMinutes()
		});
	};
	</c:otherwise>
	</c:choose>


})(jQuery);  
//-->
</script>

		
<!--pageTitle Start-->
<div id="pageTitle">
	<h2>자원 예약</h2>
</div>
<!--//pageTitle End-->

<input type="hidden" id="listType" name="listType"/>
<input type="hidden" id="category" name="category"/>
<input type="hidden" id="region" name="region"/>
<input type="hidden" id="date" name="date"/>

<!-- tab start -->
<div id="divTab" class="iKEP_tab">
	<ul>
		<li><a href="#tabs" onclick="clickTab('place')">장소별</a></li>
		<li><a href="#tabs" onclick="clickTab('day')"><ikep4j:message pre='${preHeader}' key='byDay' /></a></li>
		<li><a href="#tabs" onclick="clickTab('week')"><ikep4j:message pre='${preHeader}' key='byWeek' /></a></li>
		<li><a href="#tabs" onclick="clickTab('recent')"><ikep4j:message pre='${preHeader}' key='byRecent' /></a></li>
		<li><a href="#tabs" onclick="clickTab('favorite')"><ikep4j:message pre='${preHeader}' key='byFavorite' /></a></li>
	</ul>
	<div class="tab_con">
		<div id="tabs" class="meetingRoom">
		</div>
	</div>
</div>
<!-- tab end -->

<div class="clear"></div>

<div id="selectTimeDialog" class="none" style="margin:15px 0 0;">
	<div>
		<select name="startHour">
			<c:forEach var="hour" begin="8" end="19">
				<option value="${hour}">${hour}</option>
			</c:forEach>
		</select> :
		<select name="startMinute">
			<c:forEach var="minute" begin="0" end="59" step="5">
				<option value="${minute}">${minute < 10 ? "0" : ""}${minute}</option>
			</c:forEach>
		</select>
		&nbsp; ~ &nbsp;
		<select name="endHour">
			<c:forEach var="hour" begin="8" end="20">
				<option value="${hour}">${hour}</option>
			</c:forEach>
		</select> :
		<select name="endMinute">
			<c:forEach var="minute" begin="0" end="59" step="5">
				<option value="${minute}">${minute < 10 ? "0" : ""}${minute}</option>
			</c:forEach>
		</select>
	</div>
	<div style="margin-top:10px;">
		<table id="timeListTable">
			<thead>
				<tr>
					<c:forEach var="i" begin="8" end="19">
					<td colspan="2">${i<10?"0":""}${i}</td>
					</c:forEach>
					
				</tr>
			</thead>
			<tbody>
				<tr>
					<c:forEach var="i" begin="8" end="19">
						<c:forEach var="j" begin="0" end="59" step="30">
						<td><span>${j eq 0 ? "00" : j}</span></td>
						</c:forEach>
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="blockButton">
		<ul>
			<li class="btn_save"><a href="#a" class="button"><span><ikep4j:message pre='${preButton}' key='apply' /></span></a></li>
			<li class="btn_cancel"><a href="#a" class="button"><span><ikep4j:message pre='${preButton}' key='cancel' /></span></a></li>
		</ul>
	</div>
</div>

<script id="tmplReserveInfo" type="text/x-jquery-tmpl">
	<h4>\${title}</h4>
	<div>\${startTime} ~ \${endTime}</div>
</script>

<script id="tmplCartooletcItemEmptyDay" type="text/x-jquery-tmpl">
	<tr><td colspan="25" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td></tr>
</script>

<script id="tmplCartooletcItemDay" type="text/x-jquery-tmpl">
	<tr>
		<th class="ellipsis" title="\${categoryName}/\${regionName}" style="width:150px; border:none; text-align:left">&nbsp;\${regionName} \${cartooletcName}</th>
		<c:forEach var="i" begin="0" end="33" step="1" varStatus ="status">
			<c:set var="time" value="${i+7+(i*-0.5)}"/>
			<td><span toolId="\${cartooletcId}">${time}</span></td>
		</c:forEach>
	</tr>
</script>

<script id="tmplCartooletcItemEmptyWeek" type="text/x-jquery-tmpl">
	<tr><td colspan="28" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td></tr>
</script>

<script id="tmplCartooletcItemWeek" type="text/x-jquery-tmpl">
	<tr>
		<th class="ellipsis" title="\${categoryName}/\${regionName}" style="width:150px; border:none; text-align:left">&nbsp\${regionName} \${cartooletcName}</th>
		<c:forEach var="i" begin="1" end="7" step="1" varStatus="status">
			<c:forEach var="j" begin="7" end="23" step="5">
				<td><span>${j < 10 ? "0" : ""}${j}</span></td>
			</c:forEach>
		</c:forEach>
	</tr>
</script>