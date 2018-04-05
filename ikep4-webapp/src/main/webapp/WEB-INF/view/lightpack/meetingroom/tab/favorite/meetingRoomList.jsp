<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>  

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" language="javascript">
<!-- 
getReserve = function(scheduleId) {    
	
	var url  = '<c:url value="/lightpack/meetingroom/reserve/reserveView.do"/>';
	
	$jq.ajax({     
		
		url : url,    
		data : {
			
			scheduleId : scheduleId,
			dialog : 1
		},     
		type : "post",     
		loadingElement : {
			
			container : "#tabs"
		},
		success : function(result) {       
			
			$jq("#tabs").html(result);
		},
		error : function(event, request, settings) { 
			
			alert("error"); 
		}
	}); 
	
};

afterSave = function() {
	
	getList();
};

show_layer = function (day, from, to, title, el) {

	var period = day + " " + from + "~" + to;
	
	$jq("#reservePeriod").html(period);
	$jq("#reserveTitle").html(title);
	
	var tempX = 0;
    var tempY = 0;
    
    tempX = $jq(el).position().left - 200;
    tempY = $jq(el).position().top + 10;
   	
    $jq("#previewArea").css("top", tempY + "px");
    $jq("#previewArea").css("left", tempX + "px");
	
	$jq("#previewArea").show();
};

hidden_layer = function () {

	$jq("#previewArea").hide();
};

(function($) {
	$jq(document).ready(function() { 
		$("table.meeting_schedule").click(function(event) {
			var el = event.target;
			if(el.tagName.toLowerCase() == "td") {
				var $el = $(el);
				if($el.hasClass("empty")) {
					var tdData = $el.data();
					var trData = $el.parent().data();
					
					var reserveInfo = {
						meetingRoomId : trData.meeting_room_id,		meetingRoomName : trData.meeting_room_name,
						buildingName : trData.building_name,		floorName : trData.floor_name,
						day:"${day}", fromTime:tdData.from_time, toTime:tdData.to_time
					};
					goForm(reserveInfo);
				} else {
					if(!$el.hasClass("private"))
						getReserve($el.data("schedule_id"));
				}
			}
		});
		
		$("td.approve:not(.private), td.waiting:not(.private)", "table.meeting_schedule").mouseover(function() {
			var data = $(this).data();
			show_layer("${day}", data.from_time, data.to_time, data.title, this);
		}).mouseout(function () {
			$jq("#previewArea").hide();
		});
	});
})(jQuery);
//-->	
</script>
			
<!--blockDetail Start-->
<div class="blockListTable">

	<table class="meeting_schedule" summary="<ikep4j:message pre='${preHeader}' key='meetingRoom' />" border="1" >
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" rowspan="2" width="12%"><ikep4j:message pre='${preDetail}' key='building' /></th>
				<th scope="col" rowspan="2" width="5%"><ikep4j:message pre='${preDetail}' key='floor' /></th>
				<th scope="col" rowspan="2" width="*"><ikep4j:message pre='${preDetail}' key='meetingRoomName' /></th>
				<th scope="col" colspan="10" width="30%">
					<ikep4j:message pre='${preDetail}' key='am' />
				</th>
				<th scope="col" colspan="14" width="42%">
					<ikep4j:message pre='${preDetail}' key='pm' />
				</th>
			</tr>
			<tr>
				<th scope="col" width="6%" colspan="2">8</th>
				<th scope="col" width="6%" colspan="2">9</th>
				<th scope="col" width="6%" colspan="2">10</th>
				<th scope="col" width="6%" colspan="2">11</th>
				<th scope="col" width="6%" colspan="2">12</th>
				<th scope="col" width="6%" colspan="2">13</th>
				<th scope="col" width="6%" colspan="2">14</th>
				<th scope="col" width="6%" colspan="2">15</th>
				<th scope="col" width="6%" colspan="2">16</th>
				<th scope="col" width="6%" colspan="2">17</th>
				<th scope="col" width="6%" colspan="2">18</th>
				<th scope="col" width="6%" colspan="2">19</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${!empty timeList}">
				<c:forEach var="timeList" items="${timeList}" varStatus="status">
					<c:forEach var="time" items="${timeList}" varStatus="status">
						<c:if test="${time.isHeader == 'Y'}">
						<tr data-building_name="${time.buildingName}" data-floor_name="${time.floorName}" data-meeting_room_name="${time.meetingRoomName}" data-meeting_room_id="${time.meetingRoomId}">
							<th class="ellipsis textLeft" width="12%" title="${time.buildingName}">${time.buildingName}</th>
							<th class="ellipsis textLeft" width="5%" title="${time.floorName}">${time.floorName}</th>
							<th class="ellipsis textLeft" width="*" title="${time.meetingRoomName}">${time.meetingRoomName}</th>
						</c:if>
						<c:if test="${time.isHeader == 'N'}">
							<td class="<c:choose>
										<c:when test="${empty time.scheduleId}">empty</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${time.approveStatus == 'A'}">approve</c:when>
												<c:otherwise>waiting</c:otherwise>
											</c:choose>
											<c:if test="${time.schedulePublic != '0'}">private</c:if>
										</c:otherwise>
									</c:choose>"
								data-from_time="${time.from}" data-to_time="${time.to}"
								<c:if test="${not empty time.scheduleId}">data-schedule_id="${time.scheduleId}"</c:if>
								<c:if test="${time.schedulePublic == '0'}">data-title="${time.title}"</c:if>
								width="3%">&nbsp;</td>
						</c:if>
					</c:forEach>
					</tr>
				</c:forEach>
			</c:if>
			
			<c:if test="${empty timeList}">
			<tr>
				<td colspan="27" class="emptyRecord">
					<ikep4j:message pre='${preSearch}' key='emptyRecord' />
				</td> 
			</tr>	
			</c:if>					      
		</tbody>
		
	</table>			

</div>
<!--blockDetail End-->

<!--layer start-->
<div id="previewArea" class="process_layer" style="position:absolute; width:180px; margin-top:10px; z-index:99; display:none;">
	<div class="process_layer_t" style="text-align:center;">
		<span id="reservePeriod"></span>
	</div>
	<div class="subTitle_2 noline" style="padding:1px 5px;">
		<h4><span id="reserveTitle"></span></h4>
	</div>
</div>
<!--layer end-->