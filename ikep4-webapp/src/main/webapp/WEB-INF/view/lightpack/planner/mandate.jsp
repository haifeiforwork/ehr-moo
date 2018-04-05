<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%--메시지 관련 Prefix 선언 Start--%> 
<c:set var="preMessage"  value="ui.lightpack.planner.common.message" /> 
<c:set var="preMandator"    value="ui.lightpack.planner.mandator" />
<c:set var="preButton"  value="ui.lightpack.planner.common.button" /> 
 <%--메시지 관련 Prefix 선언 End--%>
 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" /> 

				<h1 class="none"><ikep4j:message pre="${preMessage}" key="contents" /></h1>
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre="${preMandator}" key="trusteeMgmt" /></h3>
				</div>
				<!--//subTitle_2 End-->
				
				<!--blockListTable Start-->
				<div class="blockListTable">
				<form id="mgmtForm" action="delete.do"><input id="mandatorId" name="mandatorId" type="hidden" value="${mandator.mandatorId}"/>
					<table summary="<ikep4j:message pre="${preMandator}" key="trusteeMgmt" />">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="50%"><ikep4j:message pre="${preMandator}" key="trustee" /></th>
								<th scope="col" width="*"><ikep4j:message pre="${preMandator}" key="registDate" /></th>
							</tr>
						</thead>
						<tbody>
							<tr>
							<c:if test="${!empty mandator.trusteeName}">
							     <c:choose>
							      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
							       <td>${mandator.trusteeName}</td>
							      </c:when>
							      <c:otherwise>
							       <td>${mandator.trusteeEnglishName}</td>
							      </c:otherwise>
							     </c:choose>
								<td><ikep4j:timezone date="${mandator.registDate}" pattern="yyyy.MM.dd HH:mm:ss"/></td>
							</c:if>
							<c:if test="${empty mandator.trusteeName}">
								<td colspan="2"><ikep4j:message pre="${preMandator}" key="noTrustee" /></td>
							</c:if>
							</tr>																																	
						</tbody>
					</table>	
				</form>				
				</div>
				<!--//blockListTable End-->	
							
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<c:if test="${empty mandator.trusteeName}">
							<li id="btn_insert"><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="insert" /></span></a></li>
						</c:if>
						<c:if test="${!empty mandator.trusteeName}">
							<li id="btn_edit"><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="update" /></span></a></li>
							<li id="btn_delete"><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>	
						</c:if>					
					</ul>
				</div>
				<!--//blockButton End-->	
													
			<!--//mainContents End-->
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/plannerCommon.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/mandate.js"/>"></script>
			