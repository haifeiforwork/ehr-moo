<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" />
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<% 
	pageContext.setAttribute("lf", "\n"); 
%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:choose>
<c:when test="${myPsInfo != null && editAuthFlag=='true'}">

<table summary="<ikep4j:message pre='${preProfileMain}' key='myPsInfo.title'/>" >
	<caption></caption>
	<colgroup>
		<col width="60" />
		<col width="*" />
	</colgroup>
	<tbody>
		<tr>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='myPsInfo.password'/></th>
			<td colspan="3"><span id="user_password" ><c:out value="●●●●"/></span></td>
		</tr>
		<tr>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='myPsInfo.birthday'/></th>
			<td><span id="user_birthday" ><c:out value="${myPsInfo.birthday}"/></span></td>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='myPsInfo.weddingAnniv'/></th>
			<td><span id="user_wedding_anniv" ><c:out value="${myPsInfo.weddingAnniv}"/></span></td>
		</tr>
		<tr>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='myPsInfo.homePhoneNo'/></th>
			<td><span id="user_home_phone_no" ><c:out value="${myPsInfo.homePhoneNo}"/></span></td>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='myPsInfo.homeZipcode'/></th>
			<td><span id="user_home_zipcod" ><c:out value="${myPsInfo.homeZipcode}"/></span></td>
		</tr>
		<tr>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='myPsInfo.homeBasicAddress'/></th>
			<td><span id="user_home_basic_address" ><c:out value="${myPsInfo.homeBasicAddress}"/></span></td>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='myPsInfo.homeDetailAddress'/></th>
			<td><span id="user_home_detail_address" ><c:out value="${myPsInfo.homeDetailAddress}"/></span></td>
		</tr>																	
	</tbody>
</table>

</c:when>
<c:otherwise>
	<ikep4j:message pre='${preMsgProfile}' key='nodata'/>
</c:otherwise>
</c:choose>