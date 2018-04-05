<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.collpack.workmanual.listApprovalView</c:set>
<c:set var="messagePrefix">ui.collpack.workmanual.message</c:set>
<c:set var="buttonPrefix">ui.collpack.workmanual.button</c:set>
<c:set var="preSearch">ui.collpack.workmanual.common.searchCondition</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
//<![CDATA[
(function($) {
	$jq(document).ready(function(){
		//페이징 클릭
		$jq("#pagePerRecord").change(function() {
			$jq("#searchpagingButton").click();
        });
		
		//조회 버튼 클릭
		$jq("#searchpagingButton").click(function() {
			$jq("#form").submit();
		});
		
	});

	readApproval = function(approvalId) {
		location.href = "<c:url value='/collpack/workmanual/readApprovalView.do'/>" + "?approvalId=" + approvalId;
	}
	
})(jQuery);
//]]>
</script>

				<h1 class="none"></h1>
				
				<form id="form" action="<c:url value='/collpack/workmanual/listApprovalView.do'/>" method="post">
				<!--tableTop Start-->
				<div class="tableTop">	
					<div class="tableTop_bgR"></div>	
					<h2><ikep4j:message pre="${prefix}" key="main.title"/></h2>
					<div class="listInfo">
						<spring:bind path="approvalSearchCondition.pagePerRecord">
						<select id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${prefix}' key='search.pageName'/>">
								<option <c:if test="${approvalSearchCondition.pagePerRecord == 10}">selected="selected"</c:if> value="10">10</option>
								<option <c:if test="${approvalSearchCondition.pagePerRecord == 15}">selected="selected"</c:if> value="15">15</option>
								<option <c:if test="${approvalSearchCondition.pagePerRecord == 20}">selected="selected"</c:if> value="20">20</option>
								<option <c:if test="${approvalSearchCondition.pagePerRecord == 30}">selected="selected"</c:if> value="30">30</option>
								<option <c:if test="${approvalSearchCondition.pagePerRecord == 40}">selected="selected"</c:if> value="40">40</option>
								<option <c:if test="${approvalSearchCondition.pagePerRecord == 50}">selected="selected"</c:if> value="50">50</option>							
						</select>
						</spring:bind>
						<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /> <span>${searchResult.recordCount}</span></div>
					</div>
					<div class="tableSearch">
						<spring:bind path="approvalSearchCondition.approvalType">
						<select title="<ikep4j:message pre="${prefix}" key="search.approvalType"/>" name="${status.expression}">
							<option <c:if test="${status.value == 'A'}">selected="selected"</c:if> value="A"><ikep4j:message pre="${prefix}" key="search.approvalType1"/></option>
							<option <c:if test="${status.value == 'B'}">selected="selected"</c:if> value="B"><ikep4j:message pre="${prefix}" key="search.approvalType2"/></option>
							<option <c:if test="${status.value == 'C'}">selected="selected"</c:if> value="C"><ikep4j:message pre="${prefix}" key="search.approvalType3"/></option>
						</select>													
						</spring:bind>				
						<spring:bind path="approvalSearchCondition.searchType">
						<select title="<ikep4j:message pre="${prefix}" key="search.searchType"/>" name="${status.expression}">
							<option <c:if test="${status.value == 'A'}">selected="selected"</c:if> value="A"><ikep4j:message pre="${prefix}" key="search.searchType1"/></option>
							<option <c:if test="${status.value == 'B'}">selected="selected"</c:if> value="B"><ikep4j:message pre="${prefix}" key="search.searchType2"/></option>
						</select>													
						</spring:bind>
						<spring:bind path="approvalSearchCondition.searchText">
						<input type="text" class="inputbox" title="<ikep4j:message pre="${prefix}" key="search.searchText"/>" name="${status.expression}" value="${status.value}" size="20" />
						</spring:bind>
						<a href="#a" id="searchpagingButton" class="ic_search"><span><ikep4j:message pre='${buttonPrefix}' key='search'/></span></a>
					</div>			
					<div class="clear"></div>
				</div>
				<!--//tableTop End-->

				<!--blockListTable Start-->
				<div class="blockListTable">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="5%"><ikep4j:message pre="${prefix}" key="table.column1"/></th>
								<th scope="col" width="*"><ikep4j:message pre="${prefix}" key="table.column2"/></th>
								<th scope="col" width="10%"><ikep4j:message pre="${prefix}" key="table.column3"/></th>
								<th scope="col" width="20%"><ikep4j:message pre="${prefix}" key="table.column4"/></th>
								<th scope="col" width="10%"><ikep4j:message pre="${prefix}" key="table.column5"/></th>
								<th scope="col" width="10%"><ikep4j:message pre="${prefix}" key="table.column6"/></th>
								<th class="tdLast" scope="col" width="10%"><ikep4j:message pre="${prefix}" key="table.column7"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${searchResult.entity}">
							<tr>
								<td>${item.indexRowNum}</td>
								<td class="textLeft" width="35%"><a href="#a" onclick="readApproval('${item.approvalId}')"><div class="ellipsis">${item.versionTitle}</div></a></td>
								<td><c:choose>
										<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.registerName}</c:when>
										<c:otherwise>${item.registerEnglishName}</c:otherwise>
									</c:choose>
								</td>
								<td><ikep4j:timezone date="${item.registDate}" pattern="yyyy.MM.dd HH:mm"/></td>
								<td><c:if test="${item.manualType == 'A'}"><ikep4j:message pre='${messagePrefix}' key='manual.submit'/></c:if>
									<c:if test="${item.manualType == 'B'}"><ikep4j:message pre='${messagePrefix}' key='manual.submit.revision'/></c:if>
									<c:if test="${item.manualType == 'C'}"><ikep4j:message pre='${messagePrefix}' key='manual.disuse'/></c:if>
								</td>
								<td>${item.version}</td>
								<td><c:if test="${item.approvalResult == 'A'}"><ikep4j:message pre='${messagePrefix}' key='approvalResult.prepare'/></c:if>
									<c:if test="${item.approvalResult == 'B'}"><ikep4j:message pre='${messagePrefix}' key='approvalResult.holding'/></c:if>
									<c:if test="${item.approvalResult == 'C'}"><ikep4j:message pre='${messagePrefix}' key='approvalResult.accept'/></c:if>
									<c:if test="${item.approvalResult == 'D'}"><ikep4j:message pre='${messagePrefix}' key='approvalResult.reject'/></c:if>
								</td>
							</tr>	
							</c:forEach>																																																																																													
						</tbody>
					</table>
					
					<!--Page Numbur Start-->
					<c:if test="${searchResult.recordCount > 0}">
						<spring:bind path="approvalSearchCondition.pageIndex">
						<ikep4j:pagination searchButtonId="searchpagingButton" pageIndexInput="${status.expression}" searchCondition="${approvalSearchCondition}" />
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
						</spring:bind>
					</c:if>
					<!--//Page Numbur End-->			
				</div>
				<!--//blockListTable End-->
				</form>