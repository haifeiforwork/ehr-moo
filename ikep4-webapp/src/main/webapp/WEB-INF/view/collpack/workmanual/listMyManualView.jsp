<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.collpack.workmanual.listMyManualView</c:set>
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

	//업무 메뉴얼 상세 보기
	readManualVersionView = function(versionId) {
		$jq("#form>input[name=versionId]").attr("value", versionId);
		$jq("#form").attr("action", "<c:url value='/collpack/workmanual/readManualVersionView.do'/>");
		$jq("#form").submit();
	}
	
})(jQuery);
//]]>
</script>

				<h1 class="none"></h1>

				<form id="form" action="<c:url value='/collpack/workmanual/listMyManualView.do'/>" method="post">
					<input type="hidden" name="versionId" value=""/>
					<input type="hidden" name="pathStep"  value="C"/>
				<!--tableTop Start-->
				<div class="tableTop">	
					<div class="tableTop_bgR"></div>	
					<h2><ikep4j:message pre="${prefix}" key="main.title"/></h2>
					<div class="listInfo">
						<spring:bind path="manualSearchCondition.pagePerRecord">
						<select id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${prefix}' key='search.pageName'/>">
								<option <c:if test="${manualSearchCondition.pagePerRecord == 10}">selected="selected"</c:if> value="10">10</option>
								<option <c:if test="${manualSearchCondition.pagePerRecord == 15}">selected="selected"</c:if> value="15">15</option>
								<option <c:if test="${manualSearchCondition.pagePerRecord == 20}">selected="selected"</c:if> value="20">20</option>
								<option <c:if test="${manualSearchCondition.pagePerRecord == 30}">selected="selected"</c:if> value="30">30</option>
								<option <c:if test="${manualSearchCondition.pagePerRecord == 40}">selected="selected"</c:if> value="40">40</option>
								<option <c:if test="${manualSearchCondition.pagePerRecord == 50}">selected="selected"</c:if> value="50">50</option>							
						</select>
						</spring:bind>
						<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /> <span>${searchResult.recordCount}</span></div>
					</div>
					<div class="tableSearch">
						<spring:bind path="manualSearchCondition.searchType">
						<select title="<ikep4j:message pre="${prefix}" key="search.searchType"/>" name="${status.expression}">
							<option <c:if test="${status.value == 'A'}">selected="selected"</c:if> value="A"><ikep4j:message pre="${prefix}" key="search.searchType1"/></option>
							<option <c:if test="${status.value == 'B'}">selected="selected"</c:if> value="B"><ikep4j:message pre="${prefix}" key="search.searchType2"/></option>
						</select>													
						</spring:bind>
						<spring:bind path="manualSearchCondition.searchText">
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
								<th scope="col" width="5%"><ikep4j:message pre="${prefix}" key="table.column2"/></th>
								<th scope="col" width="*"><ikep4j:message pre="${prefix}" key="table.column3"/></th>
								<th scope="col" width="10%"><ikep4j:message pre="${prefix}" key="table.column4"/></th>
								<th scope="col" width="10%"><ikep4j:message pre="${prefix}" key="table.column5"/></th>
								<th scope="col" width="5%"><ikep4j:message pre="${prefix}" key="table.column6"/></th>
								<th scope="col" width="10%"><ikep4j:message pre="${prefix}" key="table.column7"/></th>
								<th class="tdLast" scope="col" width="10%"><ikep4j:message pre="${prefix}" key="table.column8"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${searchResult.entity}">
							<tr>
								<td>${item.indexRowNum}</td>
								<td><c:if test="${item.isAbolition == 1}"><ikep4j:message pre='${messagePrefix}' key='manual.disuse'/></c:if>
									<c:if test="${item.isAbolition != 1 && item.version <= 1}"><ikep4j:message pre='${messagePrefix}' key='manual.submit'/></c:if>
									<c:if test="${item.isAbolition != 1 && item.version > 1}"><ikep4j:message pre='${messagePrefix}' key='manual.submit.revision'/></c:if>
								</td>
								<td class="textLeft" width="45%"><div class="ellipsis"><a href="#a" onclick="readManualVersionView('${item.versionId}');">${item.versionTitle}</a></div></td>
								<td><c:choose>
									    <c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.registerName}</c:when>
									    <c:otherwise>${item.registerEnglishName}</c:otherwise>
								    </c:choose>
								</td>
								<td><ikep4j:timezone date="${item.registDate}" pattern="yyyy.MM.dd"/></td>
								<td>${item.version}</td>
								<td><c:if test="${item.isPublic == 0}"><ikep4j:message pre='${messagePrefix}' key='manual.public.close'/></c:if>
									<c:if test="${item.isPublic == 1}"><ikep4j:message pre='${messagePrefix}' key='manual.public.open'/></c:if>
								</td>
								<td><c:choose> 
									  <c:when test="${item.approvalStatus == 'A'}"> 
									    <ikep4j:message pre='${messagePrefix}' key='manual.status.save'/>
									  </c:when> 
									  <c:when test="${item.approvalStatus == 'B'}"> 
									    <ikep4j:message pre='${messagePrefix}' key='manual.approval.submit'/>
									  </c:when> 
									  <c:when test="${item.approvalStatus == 'C'}"> 
									    <ikep4j:message pre='${messagePrefix}' key='manual.approval.accept'/>
									  </c:when> 
									  <c:when test="${item.approvalStatus == 'D'}"> 
									    <ikep4j:message pre='${messagePrefix}' key='manual.approval.reject'/>
									  </c:when>
									</c:choose>
								</td>
							</tr>	
							</c:forEach>																																																																																													
						</tbody>
					</table>
					
					<!--Page Numbur Start-->
					<c:if test="${searchResult.recordCount > 0}">
						<spring:bind path="manualSearchCondition.pageIndex">
						<ikep4j:pagination searchButtonId="searchpagingButton" pageIndexInput="${status.expression}" searchCondition="${manualSearchCondition}" />
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
						</spring:bind>
					</c:if>
					<!--//Page Numbur End-->			
				</div>
				<!--//blockListTable End-->
				</form>