<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<c:set var="preDetail"  value="ui.lightpack.board.boardTagging.listRelatedBoardView.detail" /> 
<%-- 메시지 관련 Prefix 선언 Start --%>  
<c:set var="preList"  value="ui.lightpack.board.boardTagging.listBoardTagging" /> 
<c:set var="preSearch"  value="ui.ikep4.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>  
<c:if test="${searchCondition.recordCount gt 0}"><div class="blockRelated_t"><ikep4j:message pre='${preList}' key='title'/><span class="comment_num"> (${searchCondition.recordCount})</span></c:if></div>
<ul>
	<c:forEach var="relatedBoardItem" items="${searchResult.entity}">
	<li>
		<span>  
			<a href="<c:url value='/collpack/kms/board/boardItem/readBoardItemView.do?boardId=${relatedBoardItem.boardId}&itemId=${relatedBoardItem.itemId}&searchConditionString=${searchConditionString}'/>">${relatedBoardItem.title}</a>
			<c:if test="${relatedBoardItem.linereplyCount > 0}"><span class="colorPoint">(${relatedBoardItem.linereplyCount})</span></c:if>	
		</span>
		<span class="summaryViewInfo"> 
			<span class="summaryViewInfo_name"> 
			<!-- 
				<c:set var="user"   value="${sessionScope['ikep.user']}" />
				<c:set var="portal" value="${sessionScope['ikep.portal']}" />
				<c:choose>
					<c:when test="${relatedBoardItem.board.anonymous eq 1}">
						<span><ikep4j:message pre='${preDetail}' key='anonymous'/></span>
					</c:when>  
					<c:otherwise>
						<c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
								<a href="#a" onclick="javascript:viewPopUpProfile('${relatedBoardItem.registerId}')">
								${relatedBoardItem.user.userName} 
								${relatedBoardItem.user.jobTitleName}
								</a>&nbsp;&nbsp;
								${relatedBoardItem.user.teamName}
							</c:when>
							<c:otherwise> 
								<a href="#a" onclick="javascript:viewPopUpProfile('${relatedBoardItem.registerId}')">
								${relatedBoardItem.user.userEnglishName} 
								${relatedBoardItem.user.jobTitleEnglishName}
								</a>&nbsp;&nbsp;
								${relatedBoardItem.user.teamEnglishName}  
							</c:otherwise>
						</c:choose>		
					</c:otherwise> 
				</c:choose>
			 --> 				<c:if test="${isSystemAdmin}">
								<a href="#a" onclick="javascript:viewPopUpProfile('${relatedBoardItem.registerId}')">
								${relatedBoardItem.registerName} 
								</a>
								<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
								${relatedBoardItem.groupName}
								</c:if>
			</span>   
			<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
			<span><ikep4j:timezone date="${relatedBoardItem.registDate}" pattern="yyyy.MM.dd HH:mm:ss" /></span> 
			<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
			<span><ikep4j:message pre='${preList}' key='hitCount' /> <strong>${relatedBoardItem.hitCount}</strong></span> 
			<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
			<span><ikep4j:message pre='${preList}' key='recommendCount' /> <strong>${relatedBoardItem.recommendCount}</strong></span> 
		</span>
	</li>		
	</c:forEach> 							
</ul>	
<form id="relatedBoardItemSearchForm" method="post" onsubmit="return false;"> 
	<spring:bind path="searchCondition.pageIndex">
		<ikep4j:pagination searchFormId="relatedBoardItemSearchForm" ajaxEventFunctionName="loadRelatedBoardItemList" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<!--//Page Numbur End-->  
</form>					 