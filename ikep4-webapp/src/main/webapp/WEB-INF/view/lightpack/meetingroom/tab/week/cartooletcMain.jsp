<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />


<script type="text/javascript">
var dialogWindow;

function fnCaller(param, dialog){
	dialogWindow = dialog;
}

(function($) {
	var startDate=0, endDate=0;
	var currentReserveList = null;	// 현재 회의실의 예약 목록
	
	doArrowWeek = function(mode) {
		$.post("<c:url value='/lightpack/meetingroom/week/getPeriodOfWeek.do'/>", {
			movement : mode,
			startDate : startDate && startDate.getTime(),
			endDate : endDate && endDate.getTime()
		}).success(function(result) {
			var start = result.startDate.split(".");
			var end = result.endDate.split(".");
			startDate = new Date(start[0], start[1]-1, start[2], 0, 0, 0);
			endDate = new Date(end[0], end[1]-1, end[2], 0, 0, 0);
			
			var period = result.startDate + " (" + "<ikep4j:message pre='${preDetail}' key='sun' />" + ") ~ " + result.endDate + " (" + "<ikep4j:message pre='${preDetail}' key='sat' />" + ")"; 
			$jq("#period").html(period);
			
			getList();
		});
	}
	
	drawMeetingRoomList = function() {
		$(".reserve", "#divCartooletcList").remove();
		var $container = $("tbody", "#divCartooletcList").empty();
		
		//if(currentReserveList && currentReserveList.length > 0) {
		if(currentReserveList!=null) {
			var $reserveContainer = $("#divCartooletcList");
			var template = $("#tmplCartooletcItemWeek").template();
			$.each(currentReserveList, function(index) {
				var data = {
						cartooletcId : this.cartooletcId,
						cartooletcName : this.cartooletcName,
						categoryName : this.categoryName,
						regionName : this.regionName
				};
				$.tmpl(template, this).data("cartooletcInfo", data)
					.appendTo($container);
				if(this.scheduleList!=null){
					$.each(this.scheduleList, function() {
						createReserveUIWeek(this, startDate, index).appendTo($reserveContainer);
					});
				}
			});
		} else {
			$container.append($.tmpl($("#tmplCartooletcItemEmptyWeek").template()));
		}
	}
	
	
	getList = function() {
		currentReserveList = null;
		var $container = $("tbody", "#divCartooletcList").empty();
		
		var data = { regionId : $("#regionId").val() };
		if(startDate && endDate)
			$.extend(data, {startDate : startDate.getTime(), endDate : endDate.getTime(),mid:"${param.mid}"});
		
		$.get("<c:url value="/lightpack/meetingroom/week/cartooletcList.do" />", data)
			.success(function(result) {
				$.each(result, function(index) {
					if(this.scheduleList!=null){
						$.each(this.scheduleList, function() {
							this.startDate = strDateToDate(this.startDate);
							this.endDate = strDateToDate(this.endDate);
						});
					}
				});
				currentReserveList = result;
				
				drawMeetingRoomList();
			});
	};

	$jq(document).ready(function() { 
		$('.fc-button-prev').click(function() { doArrowWeek("PREV"); });
		$('.fc-button-next').click(function() { doArrowWeek("NEXT"); });
		
		$('#regionId').val("${param.regionId}");
		
		$('#regionId').change(function() {
			getList();
		});
		
		var initStartDate = "${startDayOfWeek}", initEndDate = "${endDayOfWeek}";
		if(initStartDate && initEndDate) {
			var arrDate = initStartDate.split(".");
			startDate = new Date(arrDate[0], arrDate[1]-1, arrDate[2]);
			
			var arrDate = initEndDate.split(".");
			endDate = new Date(arrDate[0], arrDate[1]-1, arrDate[2]);
			
			getList();
		}
		
		$("div.meeting_schedule_v").delegate("td", "click", function(event) {
			var $td = $(this);
			var offsetDay = parseInt($td.parent().children("td").index(this) / 4, 10);
 
			var hour = parseInt($td.text(), 10);
			var startTime = cloneDate(startDate);
			startTime.setHours(hour+(offsetDay*24), 0, 0);
			var endTime = new Date(startTime.getTime() + 30*60*1000);

			reserveCartooletcFrom($(this).parent().data("cartooletcInfo"), startTime, endTime);
	});
		
		$(window).resize(drawMeetingRoomList);
	});
})(jQuery);  
//-->	
</script>

<div class="meetingRoom">
	<div class="toolbar">
		<div style="text-align:center;">
		
			<div class="tableSearch2">
				<select name="regionId" id="regionId"  style="width:120px">
					<c:forEach var="region" items="${regionList}" varStatus="status">
						<option value="${region.regionId}">${region.regionName}</option>
					</c:forEach>
				</select> 
			</div>
			<span class="fc-button-prev"><a href="#a"><span class="none"><ikep4j:message pre='${preDetail}' key='preview' /></span></a></span>
			<div class="fc-header-title">
				<h2 style="padding-right:0px; padding-top:0px;">
					<span id="period">${startDayOfWeek} (<ikep4j:message pre='${preDetail}' key='sun' />) ~ ${endDayOfWeek} (<ikep4j:message pre='${preDetail}' key='sat' />)</span>
				</h2>
			</div>
			<span class="fc-button-next"><a href="#a"><span class="none"><ikep4j:message pre='${preDetail}' key='next' /></span></a></span>
			

		</div>
	</div>			
	
	<div class="roomList">
		<table summary="<ikep4j:message pre='${preHeader}' key='cartooletc' />">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" rowspan="2" width="150px"><ikep4j:message pre='${preDetail}' key='cartooletcName' /></th>
					<th scope="col" colspan="4"><ikep4j:message pre='${preDetail}' key='sun' /></th>
					<th scope="col" colspan="4"><ikep4j:message pre='${preDetail}' key='mon' /></th>
					<th scope="col" colspan="4"><ikep4j:message pre='${preDetail}' key='tue' /></th>
					<th scope="col" colspan="4"><ikep4j:message pre='${preDetail}' key='wed' /></th>
					<th scope="col" colspan="4"><ikep4j:message pre='${preDetail}' key='thu' /></th>
					<th scope="col" colspan="4"><ikep4j:message pre='${preDetail}' key='fri' /></th>
					<th scope="col" colspan="4"><ikep4j:message pre='${preDetail}' key='sat' /></th>
				</tr>
				
				<tr>
					<c:forEach var="i" begin="1" end="7" step="1" varStatus="status">
						<c:forEach var="j" begin="7" end="23" step="5">
							<th scope="col">${j < 10 ? "0" : ""}${j}</th>
						</c:forEach>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="29" style="padding:0;">
						<div id="divCartooletcList" class="meeting_schedule_v" style="position:relative;">
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
	