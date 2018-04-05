<%--
=====================================================
	* 기능설명	:	
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>

<%-- Tab Lib End --%> 

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<!--//blockListTable End--> 


<c:set var="treePrefix">message.collpack.collaboration.workspaceMap.tree</c:set>
<c:set var="commonPrefix">message.collpack.collaboration.workspaceMap</c:set>
<c:set var="confirmPrefix">message.collpack.collaboration.workspaceMap.confirm</c:set>
<c:set var="buttonPrefix">message.collpack.collaboration.workspaceMap.button</c:set>
<c:set var="pagePrefix">message.collpack.collaboration.workspaceMap.page</c:set>
<c:set var="searchPrefix">message.collpack.collaboration.workspaceMap.search</c:set>
<c:set var="messagePrefix">message.collpack.collaboration.workspaceMap.message</c:set>
<c:set var="tablePrefix">message.collpack.collaboration.workspaceMap.table</c:set>


<!--pageTitle Start-->
<div id="pageTitle">
	<h2>${mapName}</h2>
</div>
<!--//pageTitle End-->

<!--blockbox Start-->
<div class="Box_type_01">
	<c:forEach var="item" items="${tagList}">
	<div class="open_view textLeft">
		<a href="#a" onclick="subMapClick('${item.tag}'); return false;" <c:if test="${item.tag eq tag}">class="selected"</c:if>>${item.tag}</a></div>
	</c:forEach>
	<div class="clear"></div>
</div>
<!--//blockbox End-->

<div id="mapPage">

<!--tableTop Start-->
<div class="tableTop">
	<div class="listInfo">
		<form id="searchForm">
			<spring:bind path="pageCondition.workspaceId">
				<input type="hidden" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
			<spring:bind path="pageCondition.mapId">
				<input type="hidden" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
			<spring:bind path="pageCondition.mapName">
				<input type="hidden" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
			<spring:bind path="pageCondition.tag">
				<input type="hidden" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
			<spring:bind path="pageCondition.totalCount">
				<input type="hidden" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
			<spring:bind path="pageCondition.requestPage">
				<input type="hidden" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
			<spring:bind path="pageCondition.page">
				<input type="hidden" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
			<spring:bind path="pageCondition.reInit">
				<input type="hidden" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
			
			<spring:bind path="pageCondition.countOfPage">
				<select name="${status.expression}" title="<ikep4j:message pre="${pagePrefix}" key="countOfPage"/>" onchange="pageReflash(); return false;">
					<option <c:if test="${status.value eq 10}">selected="selected"</c:if>>10</option>
					<option <c:if test="${status.value eq 15}">selected="selected"</c:if>>15</option>
					<option <c:if test="${status.value eq 20}">selected="selected"</c:if>>20</option>
					<option <c:if test="${status.value eq 30}">selected="selected"</c:if>>30</option>
					<option <c:if test="${status.value eq 50}">selected="selected"</c:if>>50</option>
				</select>
			</spring:bind>
		</form>
		<div class="totalNum">${pageCondition.page}/${pageCondition.totalPage}<ikep4j:message pre="${pagePrefix}" key="page"/> (<ikep4j:message pre="${pagePrefix}" key="total"/><span>${pageCondition.totalCount}</span>)</div>
	</div>
	<div class="clear"></div>
</div>
<!--//tableTop End-->

<!--blockListTable Start-->
<div class="blockListTable">
	<table summary="">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="10%"><ikep4j:message pre="${tablePrefix}" key="orderNumber"/></th>
				<th scope="col" width="65%"><ikep4j:message pre="${tablePrefix}" key="title"/></th>
				<th scope="col" width="10%"><ikep4j:message pre="${tablePrefix}" key="registName"/></th>
				<th scope="col" width="15%"><ikep4j:message pre="${tablePrefix}" key="registDate"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${tagItemList}">
			<tr>
				<td>${item.recordNumber}</td>
				<td class="textLeft"><a href="#a" onclick="iKEP.tagging.goDetail('<c:url value="${item.tagItemUrl}"/>');">${item.tagItemName}</a></td>
				<td>
					<c:choose>
					<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						${item.registerName}
					</c:when>
					<c:otherwise>
						${item.registerEnglishName}
					</c:otherwise>
					</c:choose>					
				</td>
				<td><fmt:formatDate value="${item.registDate}" type="date" pattern="yyyy-MM-dd"/></td>
			</tr>
		</c:forEach>
		<c:if test="${0 eq fn:length(tagItemList)}">
			<tr>
				<td colspan="4" class="emptyRecord"><ikep4j:message pre="${messagePrefix}" key="emptyRecord"/></td>
			</tr>
		</c:if>
		</tbody>
	</table>
</div>
<!--//blockListTable End-->	

<!--Page Numbur Start-->
<c:if test="${0 ne fn:length(tagItemList)}">
<%--@ include file="/WEB-INF/view/collpack/common/page.jsp"--%>
</c:if>
<!--//Page Numbur End-->
<!--  -->





<!-- Knowledge List 페이징-->
<script type="text/javascript">

// 카테고리 클릭
var subMapClick = function(tag) {
	
	$jq('form[name=searchForm]').attr({
		action:"<c:url value='/collpack/collaboration/workspaceMap/admin/workspaceMapListView.do'/>",
		method:"GET"
	});
	
	var oTag ="${tag}";
	if(tag != oTag){
		
		$jq("input[name=page]").val(1);
		$jq("input[name=requestPage]").val(1);
		$jq("input[name=reInit]").val(true);
		//$jq("input[name=countOfPage]").attr("selected",value=10);
	}
	$jq("input[name=tag]").val(tag);
	$jq("#searchForm").submit(); 
};


// 보기유형
/* var viewSubmit = function(view) {
	$jq("#searchForm input[name=viewType]").val(view);
	pageReflash();
}; */

// 페이지
var pageSubmit = function(page) {
	$jq("#searchForm input[name=requestPage]").val(page);
	$jq('form[name=searchForm]').attr({
		action:"<c:url value='/collpack/collaboration/workspaceMap/admin/workspaceMapListView.do'/>",
		method:"GET"
	});
	$jq("#searchForm").submit();
};

//페이지 Reflash
var pageReflash = function() {
	var _page = $jq("#searchForm input[name=page]").val();
	pageSubmit(_page);
};

//페이지 Reflash (DB값을 다시 읽고 처리한다)
var pageReflashDB = function() {
	$jq("#searchForm input[name=reInit]").val(true);
	pageReflash();
};
</script>



