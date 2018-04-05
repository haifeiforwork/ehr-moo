<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.socialpack.socialanalyzer.listSocialRankingView</c:set>
<c:set var="messagePrefix">ui.socialpack.socialanalyzer.message</c:set>
<c:set var="buttonPrefix">ui.socialpack.socialanalyzer.button</c:set>
<c:set var="preSearch">ui.socialpack.socialanalyzer.common.searchCondition</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
(function($) {
	$jq(document).ready(function(){
		//페이징 클릭
		$jq("#pagePerRecord").change(function() {
			$jq("#searchpagingButton").click();
        });
		
		//조회 버튼 클릭
		$jq("#searchpagingButton").click(function() {
			$jq("#form").attr("action", "<c:url value='/socialpack/socialanalyzer/listSocialRankingView.do'/>");
			$jq("#form").submit();
		});

		//조회 버튼 클릭
		$jq("#excelButton").click(function() {
			$jq("#form").attr("action", "<c:url value='/socialpack/socialanalyzer/saveExcel.do'/>");
			$jq("#form").submit();
		});
		
	});


	//SORT
	sort = function(sortType) {
		$jq("#form>input[name=sortType]").val(sortType);
		$jq("#form").attr("action", "<c:url value='/socialpack/socialanalyzer/listSocialRankingView.do'/>");
		$jq("#form").submit();
	}


})(jQuery);
</script>

				
				<h1 class="none"></h1>
				
				<form id="form" action="<c:url value="/socialpack/socialanalyzer/listSocialRankingView.do"/>" method="post">
				<input type="hidden" name="sortType" value="${searchCondition.sortType}"/>
				<!--tableTop Start-->
				<div class="tableTop">		
					<div class="tableTop_bgR"></div>
					<h2><ikep4j:message pre="${prefix}" key="main.title"/></h2>
					<div class="listInfo">
						<spring:bind path="searchCondition.pagePerRecord">
						<select id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${prefix}' key='search.pageName'/>">
								<option <c:if test="${searchCondition.pagePerRecord == 10}">selected="selected"</c:if> value="10">10</option>
								<option <c:if test="${searchCondition.pagePerRecord == 15}">selected="selected"</c:if> value="15">15</option>
								<option <c:if test="${searchCondition.pagePerRecord == 20}">selected="selected"</c:if> value="20">20</option>
								<option <c:if test="${searchCondition.pagePerRecord == 30}">selected="selected"</c:if> value="30">30</option>
								<option <c:if test="${searchCondition.pagePerRecord == 50}">selected="selected"</c:if> value="50">50</option>							
						</select>
						</spring:bind>
						<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /> <span>${searchResult.recordCount}</span></div>
					</div>			
					<div class="tableSearch">
						<ikep4j:message pre='${prefix}' key='search.searchType'/>		
						<input type="text" class="inputbox" title="inputbox" name="searchText" value="${searchCondition.searchText}" size="20" maxlength="20"/>
						<a href="#a" id="searchpagingButton" class="ic_search"><span><ikep4j:message pre='${buttonPrefix}' key='search'/></span></a>&nbsp;
						<c:if test="${searchResult.recordCount > 0}">
							<a href="#a" id="excelButton" class="ic_icon"><img src="<c:url value='/base/images/icon/ic_xls.gif'/>" alt="<ikep4j:message pre='${buttonPrefix}' key='excel'/>" /></a>
						</c:if>
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
								<th scope="col" width="4%"><ikep4j:message pre='${prefix}' key='table.column1'/></th>
								<th scope="col" width="15%"><ikep4j:message pre='${prefix}' key='table.column2'/></th>
								<th scope="col" width="*"><ikep4j:message pre='${prefix}' key='table.column3'/></th>
								<th scope="col" width="13%"><ikep4j:message pre='${prefix}' key='table.column4'/></th>
								<th scope="col" width="13%">
									<c:choose>
									    <c:when test="${searchCondition.sortType == 'A'}"><a href="#a" onclick="sort('B')"><ikep4j:message pre='${prefix}' key='table.column5'/><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>" alt="<ikep4j:message pre='${buttonPrefix}' key='sort.down'/>"/></a></c:when>
									    <c:when test="${searchCondition.sortType == 'B'}"><a href="#a" onclick="sort('A')"><ikep4j:message pre='${prefix}' key='table.column5'/><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>" alt="<ikep4j:message pre='${buttonPrefix}' key='sort.up'/>"/></a></c:when>
									    <c:otherwise><a href="#a" onclick="sort('A')"><ikep4j:message pre='${prefix}' key='table.column5'/></a></c:otherwise>
								    </c:choose>
								</th>
								<th scope="col" width="13%">
									<c:choose>
									    <c:when test="${searchCondition.sortType == 'C'}"><a href="#a" onclick="sort('D')"><ikep4j:message pre='${prefix}' key='table.column6'/><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>" alt="<ikep4j:message pre='${buttonPrefix}' key='sort.down'/>"/></a></c:when>
									    <c:when test="${searchCondition.sortType == 'D'}"><a href="#a" onclick="sort('C')"><ikep4j:message pre='${prefix}' key='table.column6'/><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>" alt="<ikep4j:message pre='${buttonPrefix}' key='sort.up'/>"/></a></c:when>
									    <c:otherwise><a href="#a" onclick="sort('C')"><ikep4j:message pre='${prefix}' key='table.column6'/></a></c:otherwise>
								    </c:choose>
								</th>
								<th scope="col" width="13%">
									<c:choose>
									    <c:when test="${searchCondition.sortType == 'E'}"><a href="#a" onclick="sort('F')"><ikep4j:message pre='${prefix}' key='table.column7'/><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>" alt="<ikep4j:message pre='${buttonPrefix}' key='sort.down'/>"/></a></c:when>
									    <c:when test="${searchCondition.sortType == 'F'}"><a href="#a" onclick="sort('E')"><ikep4j:message pre='${prefix}' key='table.column7'/><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>" alt="<ikep4j:message pre='${buttonPrefix}' key='sort.up'/>"/></a></c:when>
									    <c:otherwise><a href="#a" onclick="sort('E')"><ikep4j:message pre='${prefix}' key='table.column7'/></a></c:otherwise>
								    </c:choose>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${searchResult.entity}">
								<tr>
									<td>${item.rNum}</td>
									<td><c:choose>
										    <c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.userName} ${item.jobTitleName}</c:when>
										    <c:otherwise>${item.userEnglishName} ${item.jobTitleEnglishName}</c:otherwise>
									    </c:choose>
								    </td>
									<td><c:choose>
										    <c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.teamName}</c:when>
										    <c:otherwise>${item.teamEnglishName}</c:otherwise>
									    </c:choose>
									</td>
									<td>${item.topPercent}</td>
									<td>${item.indexSociality}</td>
									<td>${item.indexInfluence}</td>
									<td>${item.indexFellowship}</td>
								</tr>
							</c:forEach>																																																																																											
						</tbody>
					</table>
					
					<!--Page Numbur Start-->
					<c:if test="${searchResult.recordCount > 0}">
						<spring:bind path="searchCondition.pageIndex">
						<ikep4j:pagination searchButtonId="searchpagingButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
						</spring:bind>
					</c:if>
					<!--//Page Numbur End-->					
				</div>
				<!--//blockListTable End-->
				</form>