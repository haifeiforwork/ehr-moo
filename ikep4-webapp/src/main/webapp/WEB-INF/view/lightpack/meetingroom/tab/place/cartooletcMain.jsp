<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />

<script type="text/javascript">
var currentCartooletcId = null;	// 현재 선택한 회의실 ID
var currentReserveList = null;	// 현재 회의실의 예약 목록
var currentCategoryId;

(function($) {
	getCartooletcList = function(event, cartooletcId) {
		currentCategoryId = $("#selectCategory").val();
		currentCartooletcId = cartooletcId||null;
		$("#divCartooletcInfo").empty();
		$("#divPlaceTimeline").hide();
		
		$(document.body).ajaxLoadStart();
		
	   // alert("이거:"+$("#selectCategory").val());
	    
		$.get("<c:url value="/lightpack/meetingroom/place/cartooletcList.do" />", { regionId:$("#regionId").val(),mid:"${param.mid}" })
			.success(function(result) {
				var $container = $("#cartooletcListTbl").children("tbody").empty();
				if(!result || result.length == 0) {
					$container.append($.tmpl($("#tmplCartooletcItemEmpty").template()));
				} else {
					var template = $("#tmplCartooletcItem").template();
					$.each(result, function() {
						var cartooletcInfo = this;
						var $tr = $.tmpl(template, cartooletcInfo).appendTo($container);
						$tr.data({
							cartooletcId:cartooletcInfo.cartooletcId,
							cartooletcName:cartooletcInfo.cartooletcName,
							description:cartooletcInfo.description,
							categoryName:cartooletcInfo.categoryName
						});
						$("a", $tr).click(function() {
							getCartooletc(cartooletcInfo.cartooletcId);
						});
					});
					//$("#tmplCartooletcItem").tmpl(result).appendTo($container);
				}
				
				getCartooletc(currentCartooletcId);
			}).complete(function() {
				$(document.body).ajaxLoadComplete();
			});
	};
	
	getCartooletc = function(cartooletcId) {
		if(!cartooletcId) return false;
		
		currentCartooletcId = cartooletcId;
		
		$("#divCartooletcInfo").ajaxLoadStart();
		$.get("<c:url value="/lightpack/meetingroom/place/cartooletcView.do" />", { cartooletcId : cartooletcId })
			.success(function(result) { $("#divCartooletcInfo").html(result); })
			.complete(function() { $("#divCartooletcInfo").ajaxLoadComplete(); });
		
		var curDate = $("#divCalendar").datepicker("getDate");
		getReserveList(curDate);
	};
	
	getReserveList = function(date) {
		//debugger;
		
		var $container = $("div.meeting_schedule_h");
		var cur_start_date;
		currentReserveList = null;
		$("div.reserve", $container).remove();
		$("#divPlaceTimeline").show();
		//alert(currentCartooletcId);
		$("#divCartooletcRight").ajaxLoadStart();
		$.get("<c:url value="/lightpack/meetingroom/place/cartooletcReserveList.do" />", {
			cartooletcId : currentCartooletcId,
			curDate : date.getTime()
		}).success(function(result) {
			
				$.each(result, function() {
					cur_start_date = this.startDate;
					
					//	{meetingRoomName, startDate:1345802400000, floorName, scheduleId, wholeday:0, buildingName, categoryId:"1", participantId:null, endDate:1345890600000, repeatEndDate, attendanceRequest:0, schedulePublic:0, workspaceId:null, categoryName, title, approveStatus:"A", meetingRoomId, registerId, place}
					this.startDate = new Date(this.startDate);
					this.endDate = new Date(this.endDate);
					
					$container.append(createReserveUI_V(this));
				});

				currentReserveList = result;
				
				//alert("성공");
		}).complete(function() { $("#divCartooletcRight").ajaxLoadComplete(); })
		.error(function() { alert("실패"); });
		
		//alert(currentReserveList);
		
		var month = date.getMonth() + 1;
		var day = date.getDate()
		$("#lblCurrentDate").html(date.getFullYear() + "-" + (month<10?"0":"")+month + "-" + (day<10?"0":"")+day);
	};
	
// 	afterSave = function() {
// 		getMeetingList('${currentYear}','${currentMonth}','${currentDay}');
// 	};
	
	$(document).ready(function() {
		var initDate = "${today}";
		var regionId = "${regionId}";
		
		$("#divCalendar").datepicker({
			dateFormat : "yy-mm-dd",
			defaultDate : initDate,
			onSelect : function(dateText, inst) {
				$("a.ui-state-highlight", this).removeClass("ui-state-active ui-state-highlight");
				$("td", this).filter(function() {
					return $(this).text() == inst.currentDay; 
				}).children("a").addClass("ui-state-highlight");
				
				if(currentCartooletcId)
					getReserveList($(this).datepicker("getDate"));
			}
		});
		
		$("#lblCurrentDate").html(initDate);
		
		if(regionId) {
			$("#regionId").val(regionId);
			if(currentCartooletcId) getCartooletc(currentCartooletcId);
		}
		$("#regionId").change(getCartooletcList)
			.trigger("change", ["${cartooletcId}"]);
		
		$("#divCartooletcInfo").delegate("a.ic_rt_favorite", "click", function() {
			var $btn = $(this);
			var cartooletcId = $btn.attr("href");	// #포함
			var action = $btn.hasClass("select") ? "remove" : "add";
			var url = action == "add" ? "<c:url value="/lightpack/meetingroom/place/addCarFavorite.do" />" : "<c:url value="/lightpack/meetingroom/place/delCarFavorite.do" />";
			
			$("#divCartooletcInfo").ajaxLoadStart();
			$.get(url, { cartooletcId : cartooletcId.substring(1, cartooletcId.length) })
				.success(function(result) {
					$btn.toggleClass("select")
						.attr("title", (action == "add" ? "<ikep4j:message pre='${preDetail}' key='delFavorite' />" : "<ikep4j:message pre='${preDetail}' key='addFavorite' />") );
				}).complete(function() { $("#divCartooletcInfo").ajaxLoadComplete(); });
		});
		
		$("div.meeting_schedule_h").children("table").click(function(event) {
			if(!currentCartooletcId) return false;
			
			var el = event.target;
			if(el.tagName.toLowerCase() == "td") {
				var $el = $(el);
				var strTime = $el.text();
				var hour = parseInt(strTime, 10);
				var minute = strTime == hour ? 0 : 30;
				var startTime = $("#divCalendar").datepicker("getDate");
				startTime.setHours(hour, minute, 0);
				var endTime = new Date(startTime.getTime() + 30*60*1000);
				
				reserveCartooletcFrom(getCartooletcInfo(currentCartooletcId), startTime, endTime);
			}
		});
	});
	
	function getCartooletcInfo(cartooletcId) {
		var $trs = $("#cartooletcListTbl").children("tbody").find("tr");
		for(var i=0;i<$trs.size();i++) {
			var data = $trs.eq(i).data();
			if(data.cartooletcId == cartooletcId) {
				return data
			}
		}
		return {};
	}
})(jQuery);  
</script>

<h1 class="none"><ikep4j:message pre='${preDetail}' key='contentsArea' /></h1>

<div class="meetingRoom">
	<div style="float:right; width:175px;">
		<div id="divCartooletcRight">
			<div id="divCalendar"></div>

			<div id="divPlaceTimeline" class="meeting_timeline none">
				<table summary="<ikep4j:message pre='${preHeader}' key='cartooletc' />">
					<caption></caption>
					<colgroup>
						<col width="30%">
						<col width="70%">
					</colgroup>
					<thead>
						<tr><th id="lblCurrentDate" scope="col" colspan="2"></th></tr>
					</thead>
					<tbody>
						<tr>
							<td>
								<table class="timeline">
									<c:forEach var="i" begin="7" end="23" step="1" varStatus ="status">
										<tr><th>${i < 10 ? "0" : ""}${i}:00</th></tr>
									</c:forEach>
								</table>
							</td>
							<td>
								<div class="meeting_schedule_h">
									<div id="divReserveDialog" class="reserve_dialog none"></div>
									<table>
										<c:forEach var="i" begin="0" end="33" step="1" varStatus ="status">
											<c:set var="time" value="${i+7+(i*-0.5)}"/>
											<tr><td><span>${time}</span></td></tr>
										</c:forEach>
									</table>
								</div>
							</td>
						</tr>
							
					</tbody>
					
				</table>
			</div>
		</div>
	</div>
	<div style="margin-right:190px;">
		<div class="toolbar">
		<select name="regionId" id="regionId"  style="width:120px">
			<c:forEach var="region" items="${regionList}" varStatus="status">
				<option value="${region.regionId}">${region.regionName}</option>
			</c:forEach>
		</select> 
		</div>			
				
		<div class="roomList">
			<table id="cartooletcListTbl" summary="<ikep4j:message pre='${preHeader}' key='cartooletc' />">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="10%">장소</th>
						<th scope="col" width="30%"><ikep4j:message pre='${preDetail}' key='cartooletcName' /></th>
						<th scope="col" width="15%">분류</th>
						<th scope="col" width="45%"><ikep4j:message pre='${preDetail}' key='description2' /></th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
		<div id="divCartooletcInfo" style="margin-top:15px;"></div>
	</div>
	<div class="clear"></div>
</div>

<script id="tmplCartooletcItemEmpty" type="text/x-jquery-tmpl">
	<tr><td colspan="4" class="emptyRecord"><ikep4j:message pre='${preMessage}' key='list.empty' /></td></tr>
</script>

<script id="tmplCartooletcItem" type="text/x-jquery-tmpl">
	<tr>
		<td class="textCenter">\${regionName}</td>
		<td class="textLeft"><a href="#a">\${cartooletcName}</a></td>
		<td class="textCenter">\${categoryName}</td>
		<td class="textLeft">\${description}</td>
	</tr>
</script>