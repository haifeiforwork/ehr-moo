<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%--메시지 관련 Prefix 선언 Start--%> 
<c:set var="preMessage"  value="ui.lightpack.planner.common.message" /> 
<c:set var="preCategory"    value="ui.lightpack.planner.category" />
<c:set var="preButton"  value="ui.lightpack.planner.common.button" /> 
 <%--메시지 관련 Prefix 선언 End--%>
 <link rel="stylesheet" type="text/css" href="<c:url value="/base/css/planner/calendar.css"/>" />

	<h1 class="none"><ikep4j:message pre="${preMessage}" key="contents" /></h1>

	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre="${preCategory}" key="mgmt" /></h2>
	</div>
	<!--//pageTitle End-->	
	
	<!--blockDetail Start-->					
	<div class="blockListTable">
	<form id="listForm" action="">
		<table summary="<ikep4j:message pre="${preCategory}" key="list" />">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="3%" class="textCenter"><input type="checkbox" id="btn_check"/></th>
					<th scope="col" width="*" class="textCenter"><ikep4j:message pre="${preCategory}" key="name" /></th>
					<th scope="col" width="30%" class="textCenter">color</th>
				</tr>
			</thead>
			<tbody id="item_list">
			<c:choose>
				<c:when test="${!empty itemList}">
					<c:forEach var="item" items="${itemList}">
						<tr>
							<td class="textCenter">
							  <c:choose>
							  <c:when test="${item.categoryId == '1' || item.categoryId == '2'}">
								<input type="checkbox" class="checkbox" name="cid" value="${item.categoryId}" />
							  </c:when>
							  <c:otherwise>
								<input type="checkbox" class="checkbox" name="cid" value="${item.categoryId}" />
							  </c:otherwise>		
							  </c:choose>					  
							</td>
							<td class="textCenter"><a href="#a" class="edit-mode">
							<c:forEach var="clocale" items="${item.plannerCategoryLocaleList}" varStatus="status">
								<c:if test="${empty clocale.categoryName}">범주선택없음(${clocale.localeCode}) - 이 라인은 지우거나 수정하지 마세요.</c:if>
								<c:if test="${!empty clocale.categoryName}">${clocale.categoryName}(${clocale.localeCode}) </c:if>
								<c:if test="${not status.last}">&nbsp;/&nbsp;</c:if>
							</c:forEach>
							</a></td>
							<td class="textCenter">
								<span class="${item.color} cal_color_box_sample"></span>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="3" class="emptyRecord"><ikep4j:message pre="${preMessage}" key="nodata" /></td> 
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
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/category.js"/>"></script>
			