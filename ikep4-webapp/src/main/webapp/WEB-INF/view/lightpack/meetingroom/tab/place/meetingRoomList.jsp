<%@ include file="/base/common/taglibs.jsp"%>
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
getMeetingRoom = function(meetingRoomId) {
	
	$jq.ajax({     
		
		url : '<c:url value="/lightpack/meetingroom/place/meetingRoomView.do" />',     
		data :  {
			
			meetingRoomId : meetingRoomId
		},     
		type : "post",     
		loadingElement : {
			
			container : "#detailDiv"
		},
		success : function(result) {
			
			$jq("#detailDiv").html(result);
		},
		error : function(event, request, settings) { 
			
			alert("error1"); 
		}
	});  
	
};

getMeetingCalendar = function(meetingRoomId) {
	
	$jq.ajax({     
		
		url : '<c:url value="/lightpack/meetingroom/place/meetingRoomCalendar.do" />',     
		data : {
			
			meetingRoomId : meetingRoomId
		},     
		type : "post",     
		loadingElement : {
			
			container:"#meetingDiv"
		},
		success : function(result) {      
			
			$jq("#meetingDiv").html(result);
		},
		error : function(event, request, settings) { 
			
			alert("error"); 
		}
	});  
};

getView = function(meetingRoomId) {
	
	getMeetingRoom(meetingRoomId);
	getMeetingCalendar(meetingRoomId);
	
	$jq("#meetingRoomListTbl").data("meetingRoomId" ,meetingRoomId);
};

(function($) {
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 

		<c:if test="${!empty meetingRoomId}">
		getView('${meetingRoomId}');
		</c:if>
	});
})(jQuery);  

//-->
</script>

<table id="meetingRoomListTbl" summary="<ikep4j:message pre='${preHeader}' key='meetingRoom' />" border="1">
	<caption></caption>
	<thead>
		<tr >
			<th scope="col" width="40%">
				<ikep4j:message pre='${preDetail}' key='meetingRoomName' />
			</th>
			<th scope="col" width="15%">
				<ikep4j:message pre='${preDetail}' key='capacity' />
			</th>
			<th scope="col" width="45%">
				<ikep4j:message pre='${preDetail}' key='equipment' />
			</th>
		</tr>
	</thead>
						
	<tbody id="sortTable">
		<c:choose>
		<c:when test="${empty meetingRoomList}">
		<tr>
			<td colspan="3" class="emptyRecord">
				<ikep4j:message pre='${preMessage}' key='list.empty' />
			</td> 
		</tr>				        
		</c:when>
		<c:otherwise>
		<c:forEach var="meetingRoom" items="${meetingRoomList}" varStatus="status">
		<tr id="${meetingRoom.meetingRoomId}" onclick="getView('${meetingRoom.meetingRoomId}')" style="cursor:pointer">
			<td class="textLeft">${meetingRoom.meetingRoomName}</td>
			<td class="textCenter">${meetingRoom.capacity}</td>
			<td class="textLeft">${meetingRoom.equipment}</td>
		</tr>
		</c:forEach>      
	    </c:otherwise> 
		</c:choose>  																																																						
	</tbody>
						
</table>	