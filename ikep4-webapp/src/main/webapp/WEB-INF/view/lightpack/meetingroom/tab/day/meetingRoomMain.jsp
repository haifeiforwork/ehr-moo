<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 

<script type="text/javascript">
var dialogWindow;
var totalMessage = "<ikep4j:message pre='${preDetail}' key='all' />";

function fnCaller(param, dialog){
	dialogWindow = dialog;
}

(function($) {
	var currentReserveList = null;	// 현재 회의실의 예약 목록
	
	drawMeetingRoomList = function() {
		$(".reserve", "#divMeetingRoomList").remove();
		var $container = $("tbody", "#divMeetingRoomList").empty();
		
		//if(currentReserveList && currentReserveList.length > 0) {
		if(currentReserveList!=null) {
			
		
			var $reserveContainer = $("#divMeetingRoomList");
			var template = $("#tmplMeetingRoomItemDay").template();
			$.each(currentReserveList, function(index) {
				var data = {
					meetingRoomId : this.meetingRoomId,
					meetingRoomName : this.meetingRoomName,
					buildingName : this.buildingName,
					floorName : this.floorName
				};
				$.tmpl(template, this).data("meetingRoomInfo", data)
					.appendTo($container);
				if(this.scheduleList!=null){
					$.each(this.scheduleList, function() {
						//createReserveUI_H(divMeetingRoomList, index).appendTo($reserveContainer);
						var shd =this;
						var tds =$("#divMeetingRoomList tbody td");

						$.each($("#divMeetingRoomList tbody td span") , function(){
							
							//alert($(this).text());
							if($(this).attr("roomId")==data.meetingRoomId){
								
								if(
									( eval($(this).text())>=eval(shd.startDate.getHours())+(eval(shd.startDate.getMinutes())/60))
									&&
									( eval($(this).text())< eval(shd.endDate.getHours())+(eval(shd.endDate.getMinutes())/60))
								  ){
									
									//alert($(this).text());
									if(shd.approveStatus == "A"){
										$(this).parent().attr("style", "background-color:rgb(131, 148, 207)");
									}else{
										$(this).parent().attr("style", "background-color:rgb(240, 98, 128)");
									}
									
									$(this).parent().click(function(event) {
										event.stopPropagation();
										if(!shd.schedulePublic) {
											if($("#buildingId").is("*")) 
												currentBuildingId = $("#buildingId").val();
											getReserveView(shd, "day");
										}
									});
									setReserveInfomation($(this).parent(), shd);
								}
							}
							
						});

					});
				}
			});
		} else {
			$container.append($.tmpl($("#tmplMeetingRoomItemEmptyDay").template()));
		}
	}
	
	
	getList = function(date) {
		currentReserveList = null;
		var $container = $("tbody", "#divMeetingRoomList").empty();
		
		var data = { buildingId:$("#buildingId").val(), curDate:date.getTime() };
		$.get("<c:url value="/lightpack/meetingroom/day/meetingRoomList.do" />", data)
			.success(function(result) {
	    		
				$.each(result, function(index) {
					//alert("index:"+index);
				
					if(this.scheduleList!=null){
						$.each(this.scheduleList, function() {
							this.startDate = strDateToDate(this.startDate);
							this.endDate = strDateToDate(this.endDate);
						});
					}
				});
			    
				currentReserveList = result;
				
				drawMeetingRoomList();
			})
			.error(function(){
				alert("error");
			
			});

	};
	
	$(document).ready(function() {
		var initDate, today = "${today}";
		
		$('#buildingId').val("${param.buildingId}");
		
		if(today) {
			$("#selectDate").val(today);
			
			var arrDate = today.split(".");
			initDate = new Date(arrDate[0], arrDate[1]-1, arrDate[2]);
			getList(initDate);
		}
		
		$("#selectDate").datepicker({
			onSelect : function() {
				getList($(this).datepicker("getDate"));
			}
		}).next().eq(0).click(function() {
			$(this).prev().eq(0).datepicker("show");
		});
		
		$('#buildingId').change(function() {
			getList($("#selectDate").datepicker("getDate"));
		});
		
		$("div.meeting_schedule_v").delegate("td", "click", function(event) {
				var strTime = $(this).text();
				var hour = parseInt(strTime, 10);
				var minute = strTime == hour ? 0 : 30;
				var startTime = $("#selectDate").datepicker("getDate");
				startTime.setHours(hour, minute, 0);
				var endTime = new Date(startTime.getTime() + 30*60*1000);
	
				currentBuildingId = $("#buildingId").val();
				reserveMeetingRoomFrom($(this).parent().data("meetingRoomInfo"), startTime, endTime);
		});
		
		$(window).resize(drawMeetingRoomList);
	});
})(jQuery);  
</script>

<div class="meetingRoom">
	<div class="toolbar">
		<input id="selectDate" name="selectDate" type="text" class="inputbox datepicker" value="${today}" size="10" title="<ikep4j:message pre='${preDetail}' key='date' />" readonly="readonly" /> 
		<img class="dateicon"  src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='calendar' />" />
		<select name="buildingId" id="buildingId" title='<ikep4j:message pre='${preDetail}' key='building' />' style="width:120px">
			<c:forEach var="building" items="${buildingList}" varStatus="status">
				<option value="${building.buildingFloorId}">${building.buildingFloorName}</option>
			</c:forEach>
		</select>
	</div>			
	
	<div class="roomList">
		<table summary="<ikep4j:message pre='${preHeader}' key='meetingRoom' />">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" rowspan="2" width="150px"><ikep4j:message pre='${preDetail}' key='meetingRoomName' /></th>
					<th scope="col" colspan="6"><ikep4j:message pre='${preDetail}' key='am' /></th>
					<th scope="col" colspan="11"><ikep4j:message pre='${preDetail}' key='pm' /></th>
				</tr>
				
				<tr>
					<c:forEach var="i" begin="7" end="23" step="1" varStatus ="status">
						<th scope="col">${i < 10 ? "0" : ""}${i}:00</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="18" style="padding:0;">
						<div id="divMeetingRoomList" class="meeting_schedule_v" style="position:relative;">
							<div id="divReserveDialog" class="reserve_dialog none"></div>
							<table class="schedule">
								<tbody></tbody>
							</table>
						</div>
					</td>
				</tr>
			</tbody>
		</table>			
	</div>
</div>
