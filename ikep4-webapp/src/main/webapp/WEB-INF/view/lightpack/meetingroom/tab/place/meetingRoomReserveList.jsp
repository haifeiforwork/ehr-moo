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
	var reserveItems = [];
	<c:forEach var="reserveItem" items="${reserveList}">
		<c:choose>
			<c:when test="${reserveItem.schedulePublic != '0'}">
			reserveItems.push({scheduleId:"${reserveItem.scheduleId}", title:"<ikep4j:message pre='${preDetail}' key='schedulePublic' />", publicStatus:"${reserveItem.schedulePublic}", approveStatus:"${reserveItem.approveStatus}", startDate:new Date(${reserveItem.startDate}), endDate:new Date(${reserveItem.endDate}), categoryId:"${reserveItem.categoryId}"});
			</c:when>
			<c:otherwise>
			reserveItems.push({scheduleId:"${reserveItem.scheduleId}", title:"${reserveItem.title}", publicStatus:"${reserveItem.schedulePublic}", approveStatus:"${reserveItem.approveStatus}", startDate:new Date(${reserveItem.startDate}), endDate:new Date(${reserveItem.endDate}), categoryId:"${reserveItem.categoryId}"});
			</c:otherwise>
		</c:choose>
	</c:forEach>
	
	
	$(document).ready(function() {
// 		$("table.meeting_schedule").click(function(event) {
// 			var el = event.target;
// 			if(el.tagName.toLowerCase() == "td") {
// 				var $el = $(el);
// 				if($el.hasClass("empty")) {
// 					var data = $el.data();
// 					iKEP.debug(data);
// 					var reserveInfo = $.extend({day:"${day}", fromTime:$el.data("from_time"), toTime:$el.data("to_time")}, meetingRoomInfo);
// 					goForm(reserveInfo);
// 				} else {
// 					if(!$el.hasClass("private"))
// 						getReserve($el.data("schedule_id"));
// 				}
// 			}
// 		});
		
		if($("tr", "table.meeting_schedule").size() == 0) {
			$("table.meeting_schedule").html('<tbody/>');
			var $tbody = $("tbody", "table.meeting_schedule");
			for(var i=startHour;i<endHour;i+=0.5) {
				var startTime, endTime, hour = parseInt(i, 10);
				startTime = (hour<10 ? "0"+hour : hour) + ":" + (hour==i ? "00" : "30");
				
				if(hour != i) hour++;
				endTime = (hour<10 ? "0"+hour : hour) + ":" + (hour>i ? "00" : "30");
				
				$('<tr><td class="empty">&nbsp;</td></tr>').appendTo($tbody)
					.children()
						.data({from_time:startTime, to_time:endTime});
			}
		}
		
		
	});
	
	
})(jQuery);  


//-->	
</script>