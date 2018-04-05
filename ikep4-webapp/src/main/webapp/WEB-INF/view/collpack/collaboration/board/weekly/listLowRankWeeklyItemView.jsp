<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.board.weekly.listBoardView.header" /> 
<c:set var="preList"    value="message.collpack.collaboration.board.weekly.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.collaboration.board.weekly.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.collaboration.board.weekly.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.collaboration.board.weekly.listBoardView.alert" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript">

<!--   
(function($){	
	$(document).ready(function() {
		sort = function(sortColumn, sortType) {
			$("#lowRankListForm input[name=sortColumn]").val(sortColumn); 
			$("#lowRankListForm input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');	 
			$("#searchLowRankItemButton").click();
		}; 
		
		$("#searchLowRankItemButton").click(function() {   
			//$("#lowRankListForm").attr({method:'GET'}).submit();
			$("#lowRankListForm").submit();
			return false; 
		});
		
		$("#datetext").datepicker({
			showOn: "button",
			buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			buttonImageOnly: false,
			showOtherMonths : true,
			selectOtherMonths: true,
			hoverWeek : true,
			onSelect: function(date) {
				$("#lowRankListForm input[name=weeklyTerm]").val(date);
				$("#lowRankListForm").submit();
			}
		});
		
		$jq("#checkAllUserIds").click(function() { 
			$("input[name=userIds]").attr("checked", $(this).is(":checked"));  
		});
		
		iKEP.iFrameContentResize();
		
	}); 
	
})(jQuery);
//-->
</script>

<form id="lowRankListForm" action="<c:url value='/collpack/collaboration/board/weekly/listLowRankWeeklyItemView.do'/>">
<!--tableTop Start-->
<div class="tableTop">		
	<div class="tableTop_bgR"></div>
	<h2><ikep4j:message pre="${preHeader}" key="subCollTitle"/></h2>							
	<div class="subInfo">
		<span class="totalNum_s"><ikep4j:message pre="${preList}" key="searchPeriod"/></span>
		<a id="searchLowRankItemButton" href="#a"></a>
		<spring:bind path="searchCondition.weeklyTerm">
			<input type="text" name = "${status.expression}" value="${status.value}" id="datetext" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" readonly/>
		</spring:bind>
	</div>			
	<div class="clear"></div>
</div>
<!--//tableTop End-->			

	<spring:bind path="searchCondition.sortColumn">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<spring:bind path="searchCondition.workspaceId">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
<!--blockListTable Start-->
<div class="blockListTable">
	<table summary="">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="25%">
				<a onclick="sort('WORKSPACE_NAME', '<c:if test="${searchCondition.sortColumn eq 'WORKSPACE_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='workspaceName' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'WORKSPACE_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'WORKSPACE_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
				</th>
				<th scope="col" width="*">
				<a onclick="sort('TITLE', '<c:if test="${searchCondition.sortColumn eq 'TITLE'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='title' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'TITLE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'TITLE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
				</th>
				<th scope="col" width="12%">
				<a onclick="sort('REGISTER_NAME', '<c:if test="${searchCondition.sortColumn eq 'REGISTER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='registerName' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
				</th>
				<th scope="col" width="12%">
				<a onclick="sort('REGIST_DATE', '<c:if test="${searchCondition.sortColumn eq 'REGIST_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='registDate' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'REGIST_DATE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'REGIST_DATE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>
				</th>
			</tr>
		</thead>
		<tbody>
		<c:choose>
		    <c:when test="${searchResult.emptyRecord}">
				<tr>
					<td colspan="4" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
				</tr>				        
		    </c:when>
		    <c:otherwise>
			<c:forEach var="summaryItems" items="${searchResult.entity}">
			<tr>
				<td class="textLeft">${summaryItems.workspaceName}</td>
				<td class="textLeft">
					<c:if test="${summaryItems.isSummary eq 1}">
					<span class="cate_block_5">
						<span class="cate_tit_5"><ikep4j:message pre="${preList}" key="summary"/></span>
					</span>
					</c:if>
					&nbsp;
					<c:choose>
						<c:when test="${weeklyPermission>1}">
							<a href="<c:url value='/collpack/collaboration/board/weekly/readLowRankWeeklyItemView.do?itemId=${summaryItems.itemId}&workspaceId=${searchCondition.workspaceId}&itemWorkspaceId=${summaryItems.workspaceId}'/>" >${summaryItems.title}</a></td>
						</c:when>
						<c:otherwise>
							${summaryItems.title}
						</c:otherwise>
					</c:choose>
				<td>
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					${summaryItems.registerName}
				</c:when>
				<c:otherwise>
					${summaryItems.registerEnglishName}
				</c:otherwise>
				</c:choose>					
				</td>
				<td>
					<ikep4j:timezone pattern="yyyy.MM.dd" date="${summaryItems.registDate}"/>
				</td>
			</tr>
			</c:forEach>
			</c:otherwise>
		</c:choose>
		</tbody>
	</table>
	<!--Page Numbur Start-->
	<spring:bind path="searchCondition.pageIndex">
		<ikep4j:pagination searchButtonId="searchLowRankItemButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" /> 
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" /> 
	</spring:bind>
	<!--//Page Numbur End-->			
</div>
</form>
<!--//blockListTable End-->	