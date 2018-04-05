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


<script type="text/javascript">
(function($) {
	getReserve = function(scheduleId) {    
		var url  = '<c:url value="/lightpack/meetingroom/reserve/reserveView.do"/>';
		$.ajax({     
			url : url,    
			data : { scheduleId : scheduleId, dialog : 1 },     
			type : "post",
			loadingElement : { container : "#tabs" },
			success : function(result) {
				$("#tabs").html(result);
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
		
		$("#reservePeriod").html(period);
		$("#reserveTitle").html(title);
		
		var tempX = 0;
	    var tempY = 0;
	    
	    tempX = $(el).position().left - 200;
	    tempY = $(el).position().top + 10;
	   	
	    $("#previewArea").css("top", tempY + "px");
	    $("#previewArea").css("left", tempX + "px");
		
		$("#previewArea").show();
	};

	hidden_layer = function () {
		$("#previewArea").hide();
	};
	
	$(document).ready(function() { 
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
			$("#previewArea").hide();
		});
	});
})(jQuery);  
</script>
			
<!--blockDetail Start-->

			<c:if test="${!empty timeList}">
				<c:forEach var="timeList" items="${timeList}" varStatus="status">
					<c:forEach var="time" items="${timeList}" varStatus="status">
						<c:if test="${time.isHeader == 'Y'}">
						<tr data-building_name="${time.buildingName}" data-floor_name="${time.floorName}" data-meeting_room_name="${time.meetingRoomName}" data-meeting_room_id="${time.meetingRoomId}">
							<th class="ellipsis textLeft" width="*" title="${time.buildingName}/${time.floorName}">${time.meetingRoomName}</th>
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
