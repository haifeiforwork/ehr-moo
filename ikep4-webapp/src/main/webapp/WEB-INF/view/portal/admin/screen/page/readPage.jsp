<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="prefix" value="message.portal.admin.screen.page.readPage"/>

<c:if test="${!empty portalPage}">
<!--blockDetail Start-->
<form id="mainForm" method="post" action="<c:url value='/portal/admin/screen/page/removePage.do'/>">
<input type="hidden" name="pageId" value='${portalPage.pageId}'/>
<div class="blockDetail">
	<table summary="<ikep4j:message pre="${prefix}" key="tableSummary" />">
		<caption></caption>
		<tbody>
			<tr id="localeTr1">
					<th scope="row" rowspan="${localeSize}" width="12%" class="textCenter"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="pageTitle" /></th>
					<c:forEach var="i18nMessage" items="${portalPage.i18nMessageList}" varStatus="loopStatus">
					<c:if test="${i18nMessage.index > 1}">
						<tr id="localeTr1${i18nMessage.index}" >
					</c:if>
							<th scope="row" width="6%"><span class="colorPoint">*</span><c:out value="${i18nMessage.localeCode}"/></th>
							<td><c:out value="${i18nMessage.itemMessage}"/></td>
						</tr>
					</c:forEach>
			
			<tr>
				<th colspan="2" scope="row"><ikep4j:message pre="${prefix}" key="systemName" /></th>
				<td>
					<c:out value="${portalPage.systemName}"/>
				</td>
			</tr>
			<tr>
				<th colspan="2" scope="row">URL</th>
				<td>
					/portal/main/body.do?pageId=${portalPage.pageId}	
				</td>
			</tr>
		</tbody>
	</table>
	<div id="securityArea"></div>
</div>
<!--//blockDetail End-->

<!--tableBottom Start-->
<div class="tableBottom">										
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<c:if test="${portalPage.pageId == defPortalPageId}">
				<li><a id="modifyFormButton" class="button" href="#a" onclick="modifyForm();"><span><ikep4j:message pre="${prefix}" key="modify" /></span></a></li>	
			</c:if>
			<c:if test="${portalPage.pageId != defPortalPageId}">
				<li><a class="button" href="<c:url value="listPage.do" />"><span><ikep4j:message pre="${prefix}" key="list" /></span></a></li>
				<li><a id="modifyFormButton" class="button" href="#a" onclick="modifyForm();"><span><ikep4j:message pre="${prefix}" key="modify" /></span></a></li>
				<li><a class="button" href="#a" onclick="removePage();"><span><ikep4j:message pre="${prefix}" key="remove" /></span></a></li>
			</c:if>
		</ul>
	</div>

	<!--//blockButton End-->
</div>
<!--//tableBottom End-->
</form>
</c:if>
<c:if test="${empty portalPage}">
	<ikep4j:message pre="${prefix}" key="noPage" />
</c:if>