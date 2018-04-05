<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
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

(function($) {
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 

	});
})(jQuery);  

//-->
</script>

<table summary="<ikep4j:message pre='${preHeader}' key='meetingRoom' />">
	<caption></caption>
	<thead>
		<tr >
			<th scope="col" width="3%" class="textCenter">&nbsp;</th>
			<th scope="col" width="5%">
				<ikep4j:message pre='${preDetail}' key='building'/>
			</th>
			<th scope="col" width="5%">
				<ikep4j:message pre='${preDetail}' key='floor'/>
			</th>
			<th scope="col" width="20%">
				<ikep4j:message pre='${preDetail}' key='meetingRoomName'/>
			</th>
			<th scope="col" width="12%">
			 예약시스템 사용여부
			</th>
			<th scope="col" width="12%">
			 예약자동승인 여부
			</th>
			<th scope="col" width="10%">
				<ikep4j:message pre='${preDetail}' key='capacity'/>
			</th>
			<th scope="col" width="15%">
				<ikep4j:message pre='${preDetail}' key='equipment'/>
			</th>
			<th scope="col" width="8%">
				<ikep4j:message pre='${preDetail}' key='managerName'/>
			</th>
		</tr>
	</thead>

	
	<tbody>
		<c:choose>
	    <c:when test="${empty meetingRoomList}">
		<tr>
			<td colspan="9" class="emptyRecord">
				<ikep4j:message pre='${preSearch}' key='emptyRecord' />
			</td> 
		</tr>				        
	    </c:when>
	    <c:otherwise>
		<c:forEach var="meetingRoom" items="${meetingRoomList}" varStatus="status">
		<tr id="${meetingRoom.meetingRoomId}" >
			<td class="textCenter">
				<input type="checkbox" id="chkMeetingRoomId" name="chkMeetingRoomId" value="${meetingRoom.meetingRoomId}" title="checkbox"/>
    		</td>
			<td class="textCenter">${meetingRoom.buildingName}</td>
			<td class="textCenter">${meetingRoom.floorName}</td>
			<td class="textCenter"><a href="#a" onclick="getView('${meetingRoom.meetingRoomId}')">${meetingRoom.meetingRoomName}</a></td>
			<td class="textCeneter"><c:if test="${'1'==meetingRoom.useFlag}">사용중</c:if><c:if test="${'0'==meetingRoom.useFlag}">사용중지중</c:if></td>
			<td class="textCeneter"><c:if test="${meetingRoom.autoApprove != 'N'}">자동승인</c:if><c:if test="${meetingRoom.autoApprove == 'N'}">담당자승인</c:if></td>
			<td class="textCeneter">${meetingRoom.capacity}</td>
			<td class="textLeft">${meetingRoom.equipment}</td>
			<td class="textCenter">${meetingRoom.managerName}</td>
		</tr>
		</c:forEach>				      
	    </c:otherwise> 
		</c:choose>  																																																						
	</tbody>
</table>	