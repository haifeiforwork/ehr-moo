<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.collpack.workmanual.listHistoryView</c:set>
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
			$jq("#form").attr("action", "<c:url value='/collpack/workmanual/listHistoryView.do'/>");
			$jq("#form").submit();
		});
		
		//취소 버튼 클릭
		$jq("#cancelButton").click(function() {
			$jq("#form").attr("action", "<c:url value='/collpack/workmanual/readManualView.do'/>");
			$jq("#form").submit();
		});
		
	});

	//되돌리기 클릭
	redoManualVersion = function(versionId) {
		$jq("#form>input[name=versionId]").css("value", versionId);
		$jq("#form").attr("action", "<c:url value='/collpack/workmanual/redoManualVersion.do'/>");
		$jq("#form").submit();
	}
	
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
				
				<form id="form" method="post">
					<input type="hidden" name="manualId"  value="${manualSearchCondition.manualId}"/>
					<input type="hidden" name="versionId" value="${manualSearchCondition.versionId}"/>
					<input type="hidden" name="pathStep"  value="${manualSearchCondition.pathStep}"/>
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
								<option <c:if test="${manualSearchCondition.pagePerRecord == 50}">selected="selected"</c:if> value="50">50</option>							
						</select>
						</spring:bind>
						<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /> <span>${searchResult.recordCount}</span></div>
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
								<th scope="col" width="10%"><ikep4j:message pre="${prefix}" key="table.column2"/></th>
								<th scope="col" width="*"><ikep4j:message pre="${prefix}" key="table.column3"/></th>
								<th scope="col" width="10%"><ikep4j:message pre="${prefix}" key="table.column4"/></th>
								<th scope="col" width="16%"><ikep4j:message pre="${prefix}" key="table.column5"/></th>
								<th scope="col" width="7%"><ikep4j:message pre="${prefix}" key="table.column6"/></th>
							</tr>
						</thead>
						<tbody id="tbody">
							<c:forEach var="item" items="${searchResult.entity}">
							<tr>
								<td>${item.indexRowNum}</td>
								<td>${item.version}</td>
								<td class="textLeft" width="52%"><a href="#a" onclick="readManualVersionView('${item.versionId}');"><div class="ellipsis">${item.updateReason}</div></a></td>
								<td><c:choose>
									    <c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.registerName}</c:when>
									    <c:otherwise>${item.registerEnglishName}</c:otherwise>
								    </c:choose>
								</td>
								<td><ikep4j:timezone date="${item.registDate}" pattern="yyyy.MM.dd HH:mm"/></td>
								<td><c:if test="${redoAuthorityYn == 'Y' && manual.manualType != 'C' && item.version >= standardVersion && item.version < standardVersion + 1}">
										<a href="#a" onclick="redoManualVersion('${item.versionId}');"><img src="<c:url value="/base/images/icon/ic_relocate.gif"/>" alt="<ikep4j:message pre="${prefix}" key="table.column6"/>" /></a>
									</c:if>
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
				
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li><a class="button" href="#none" id="cancelButton"><span><ikep4j:message pre='${buttonPrefix}' key='cancel'/></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->