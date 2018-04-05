<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />

<script type="text/javascript">
var currentMeetingRoomId = null;	// 현재 선택한 회의실 ID
var currentReserveList = null;	// 현재 회의실의 예약 목록
var currentBuildingId;

(function($) {
	getMeetingRoomList = function(event, meetingRoomId) {
		currentBuildingId = $("#selectBuilding").val();
		currentMeetingRoomId = meetingRoomId||null;
		$("#divMeetingRoomInfo").empty();
		$("#divPlaceTimeline").hide();
		
		$(document.body).ajaxLoadStart();
		$.get("<c:url value="/lightpack/meetingroom/place/meetingRoomList.do" />", { buildingId:$("#selectBuilding").val() })
			.success(function(result) {
				var $container = $("#meetingRoomListTbl").children("tbody").empty();
				if(!result || result.length == 0) {
					$container.append($.tmpl($("#tmplMeetingRoomItemEmpty").template()));
				} else {
					var template = $("#tmplMeetingRoomItem").template();
					$.each(result, function() {
						var meetingRoomInfo = this;
						var $tr = $.tmpl(template, meetingRoomInfo).appendTo($container);
						$tr.data({
							meetingRoomId:meetingRoomInfo.meetingRoomId,
							meetingRoomName:meetingRoomInfo.meetingRoomName,
							buildingName:meetingRoomInfo.buildingName,
							floorName:meetingRoomInfo.floorName
						});
						$("a", $tr).click(function() {
							getMeetingRoomView(meetingRoomInfo.meetingRoomId);
						});
					});
					//$("#tmplMeetingRoomItem").tmpl(result).appendTo($container);
				}
				
				getMeetingRoomView(currentMeetingRoomId);
			}).complete(function() {
				$(document.body).ajaxLoadComplete();
			});
	};
	
	getMeetingRoomView = function(meetingRoomId) {
		if(!meetingRoomId) return false;
		
		currentMeetingRoomId = meetingRoomId;
		
		$("#divMeetingRoomInfo").ajaxLoadStart();
		$.get("<c:url value="/lightpack/meetingroom/place/meetingRoomView.do" />", { meetingRoomId : meetingRoomId })
			.success(function(result) { $("#divMeetingRoomInfo").html(result); })
			.complete(function() { $("#divMeetingRoomInfo").ajaxLoadComplete(); });
		
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
		
		$("#divMeetingRoomRight").ajaxLoadStart();
		$.get("<c:url value="/lightpack/meetingroom/place/meetingRoomReserveList.do" />", {
			meetingRoomId : currentMeetingRoomId,
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
		}).complete(function() { $("#divMeetingRoomRight").ajaxLoadComplete(); })
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
		var buildingId = "${buildingId}";
		
		$("#divCalendar").datepicker({
			dateFormat : "yy-mm-dd",
			defaultDate : initDate,
			onSelect : function(dateText, inst) {
				$("a.ui-state-highlight", this).removeClass("ui-state-active ui-state-highlight");
				$("td", this).filter(function() {
					return $(this).text() == inst.currentDay; 
				}).children("a").addClass("ui-state-highlight");
				
				if(currentMeetingRoomId)
					getReserveList($(this).datepicker("getDate"));
			}
		});
		
		$("#lblCurrentDate").html(initDate);
		
		if(buildingId) {
			$("#selectBuilding").val(buildingId);
			if(currentMeetingRoomId) getMeetingRoomView(currentMeetingRoomId);
		}
		$("#selectBuilding").change(getMeetingRoomList)
			.trigger("change", ["${meetingRoomId}"]);
		
		$("#divMeetingRoomInfo").delegate("a.ic_rt_favorite", "click", function() {
			var $btn = $(this);
			var meetingRoomId = $btn.attr("href");	// #포함
			var action = $btn.hasClass("select") ? "remove" : "add";
			var url = action == "add" ? "<c:url value="/lightpack/meetingroom/place/addFavorite.do" />" : "<c:url value="/lightpack/meetingroom/place/delFavorite.do" />";
			
			$("#divMeetingRoomInfo").ajaxLoadStart();
			$.get(url, { meetingRoomId : meetingRoomId.substring(1, meetingRoomId.length) })
				.success(function(result) {
					$btn.toggleClass("select")
						.attr("title", (action == "add" ? "<ikep4j:message pre='${preDetail}' key='delFavorite' />" : "<ikep4j:message pre='${preDetail}' key='addFavorite' />") );
				}).complete(function() { $("#divMeetingRoomInfo").ajaxLoadComplete(); });
		});
		
		$("div.meeting_schedule_h").children("table").click(function(event) {
			if(!currentMeetingRoomId) return false;
			
			var el = event.target;
			if(el.tagName.toLowerCase() == "td") {
				var $el = $(el);
				var strTime = $el.text();
				var hour = parseInt(strTime, 10);
				var minute = strTime == hour ? 0 : 30;
				var startTime = $("#divCalendar").datepicker("getDate");
				startTime.setHours(hour, minute, 0);
				var endTime = new Date(startTime.getTime() + 30*60*1000);
				
				reserveMeetingRoomFrom(getMeetingRoomInfo(currentMeetingRoomId), startTime, endTime);
			}
		});
	});
	
	function getMeetingRoomInfo(meetingRoomId) {
		var $trs = $("#meetingRoomListTbl").children("tbody").find("tr");
		for(var i=0;i<$trs.size();i++) {
			var data = $trs.eq(i).data();
			if(data.meetingRoomId == meetingRoomId) {
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
		<div id="divMeetingRoomRight">
			<div id="divCalendar"></div>

			<div id="divPlaceTimeline" class="meeting_timeline none">
				<table summary="<ikep4j:message pre='${preHeader}' key='meetingRoom' />">
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
				<select id="selectBuilding" title="<ikep4j:message pre='${preDetail}' key='building' />" style="width:120px">
					<c:forEach var="building" items="${buildingList}" varStatus="status">
						<option value="${building.buildingFloorId}">${building.buildingFloorName}</option>
					</c:forEach>
				</select>
		</div>			
				
		<div class="roomList">
			<table id="meetingRoomListTbl" summary="<ikep4j:message pre='${preHeader}' key='meetingRoom' />">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="10%">장소</th>
						<th scope="col" width="7%">층</th>
						<th scope="col" width="28%"><ikep4j:message pre='${preDetail}' key='meetingRoomName' /></th>
						<th scope="col" width="15%"><ikep4j:message pre='${preDetail}' key='capacity' /></th>
						<th scope="col" width="40%"><ikep4j:message pre='${preDetail}' key='equipment' /></th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
		<div id="divMeetingRoomInfo" style="margin-top:15px;"></div>
	</div>
	<div class="clear"></div>
</div>

<script id="tmplMeetingRoomItemEmpty" type="text/x-jquery-tmpl">
	<tr><td colspan="5" class="emptyRecord"><ikep4j:message pre='${preMessage}' key='list.empty' /></td></tr>
</script>

<script id="tmplMeetingRoomItem" type="text/x-jquery-tmpl">
	<tr>
		<td class="textCenter">\${buildingName}</td>
		<td class="textCenter">\${floorName}</a></td>
		<td class="textLeft"><a href="#a">\${meetingRoomName}</a></td>
		<td class="textCenter">\${capacity}</td>
		<td class="textLeft">\${equipment}</td>
	</tr>
</script>