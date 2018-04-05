<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%--메시지 관련 Prefix 선언 Start--%> 
<c:set var="preMessage"  value="ui.lightpack.planner.common.message" /> 
<c:set var="preHoliday"    value="ui.lightpack.planner.holiday" />
<c:set var="preButton"  value="ui.lightpack.planner.common.button" /> 
 <%--메시지 관련 Prefix 선언 End--%>
 
	<h1 class="none"><ikep4j:message pre="${preMessage}" key="contents" /></h1>
	
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre="${preHoliday}" key="mgmt" /></h2>
	</div>
	<!--//pageTitle End-->	
		
	<!--blockDetail Start-->					
	<div class="blockListTable">
	<form id="listForm" action="">
		<table summary="<ikep4j:message pre="${preHoliday}" key="list" />">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="3%" class="textCenter"><input type="checkbox" id="btn_check"/></th>
					<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${preHoliday}" key="nationName" /></th>
					<th scope="col" width="*" class="textCenter"><ikep4j:message pre="${preHoliday}" key="name" /></th>
					<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${preHoliday}" key="date" /></th>
					<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${preHoliday}" key="isYearlyRepeat" /></th>
				</tr>
			</thead>
			<tbody id="item_list">
			<c:choose>
				<c:when test="${!empty itemList}">
					<c:forEach var="item" items="${itemList}">
						<tr>
							<td class="textCenter">
								<input type="checkbox" name="hid" value="${item.holidayId}" class="checkbox"/>
							</td>
							<td class="textCenter">${item.nationName}</td>
							<td class="textCenter holiday-name">${item.holidayName}</td>
							<td class="textCenter">
								<fmt:formatDate value="${item.holidayDate}" type="date" pattern="yyyy.MM.dd"/>
							</td>
							<td class="textCenter">
								<c:if test="${item.yearRepeat == '0' }">
									<ikep4j:message pre="${preHoliday}" key="yearlyRepeatYes" />
								</c:if>
								<c:if test="${item.yearRepeat != '0' }">
									<ikep4j:message pre="${preHoliday}" key="yearlyRepeatNo" />
								</c:if>								
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="5" class="emptyRecord"><ikep4j:message pre="${preMessage}" key="nodata" /></td> 
					</tr>							
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
	</form>
	</div>
	<!--//blockDetail End-->	
						
	<!--blockButton Start-->
	<div class="blockButton"> 			
		<ul>
			<li><a class="button" href="#a" id="btn_insert"><span><ikep4j:message pre="${preButton}" key="insert" /></span></a></li>
			<li><a class="button" href="#a" id="btn_delete"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
										
<!--//mainContents End-->
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/plannerCommon.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/holiday.js"/>"></script>
			