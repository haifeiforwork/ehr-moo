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
				
				<!--blockDetail Start-->
				<div class="blockDetail">
				<form id="mgmtForm" action="">
				<input id="trusteeId" name="trusteeId" type="hidden" value="" />
				<input id="mandatorId" name="mandatorId" type="hidden" value="${mandator.mandatorId}"/>
					<table summary="<ikep4j:message pre="${preMandator}" key="trusteeMgmt" />">
						<caption></caption>
						<tbody>
							<tr>
								<th scope="row" width="18%"><ikep4j:message pre="${preMandator}" key="preTrustee" /></th>
								<td width="82%">
									<div>
										<input id="search" name="userName" title="<ikep4j:message pre="${preMandator}" key="searchTrustee" />" type="text" class="inputbox"/>
										<a id="btn_searchUser" class="button_s button_ic" href="#a">
										<span><img src="<c:url value="/base/images/icon/ic_btn_search.gif"/>" alt="" />Search</span></a>
									     <c:choose>
									      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
									       <span id="trusteeName">${mandator.trusteeName}</span>
									      </c:when>
									      <c:otherwise>
									       <span id="trusteeName">${mandator.trusteeEnglishName}</span>
									      </c:otherwise>
									     </c:choose>										
									</div>
								</td>
							</tr>					
						</tbody>
					</table>
					<div class="none"><input id="dummy" name="dummy" type="text" style="height:0px" /></div>
				</form>
				</div>
				<!--//blockDetail End-->
							
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li id="btn_save"><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="create" /></span></a></li>
						<li id="btn_list"><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="list" /></span></a></li>
						<c:if test="${!empty mandator.trusteeName}">
							<li id="btn_delete"><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>	
						</c:if>					
					</ul>
				</div>
				<!--//blockButton End-->	
													
			<!--//mainContents End-->
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/plannerCommon.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/mandate.js"/>"></script>
</script>