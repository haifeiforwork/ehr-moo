<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preUi" 	value="ui.collpack.qna.qnaRelationList" />

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
//<![CDATA[

function goRelationList(){
	
	var pageIndex = $jq('#relationForm input[name=pageIndex]').val();

	location.href="listRelationQna.do?qnaGroupId=${param.qnaGroupId}&pageIndex="+pageIndex;
}
//]]>
</script>


<h1 class="none">Contents Area</h1>

<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3 class="mb2"><ikep4j:message pre='${preUi}' key='viewRelation'/></h3>
</div>
<!--//subTitle_2 End-->

<!--blockListTable Start-->
<div class="blockListTable blockRelated" id="qnaRelation" style="padding:0px;">
		
	<table summary="list">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="5%"><ikep4j:message pre='${preUi}' key='listThQnaType'/></th>
				<th scope="col" width="*"><ikep4j:message pre='${preUi}' key='listThTitle'/></th>
				<th scope="col" width="11%"><ikep4j:message pre='${preUi}' key='listThCategory'/></th>
				<th scope="col" width="11%"><ikep4j:message pre='${preUi}' key='listThRegister'/></th>
				<th scope="col" width="11%"><ikep4j:message pre='${preUi}' key='listThregisteDate'/></th>
				<th scope="col" width="7%"><ikep4j:message pre='${preUi}' key='listThHit'/></th>
				<th scope="col" width="7%"><ikep4j:message pre='${preUi}' key='listThRecommend'/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="qna" items="${qnaRelationList}" varStatus="qnaLoopCount">
			<tr>
				<td>
				
			<c:choose>
	 			<c:when test="${qna.status  == 0}">
	 				<span class="status_None"><ikep4j:message pre='${preUi}' key='listStatusN'/></span>
	 			</c:when>
	 			<c:when test="${qna.status  == 1}">
	 				<span class="status_Ing"><ikep4j:message pre='${preUi}' key='listStatusA'/></span>
	 			</c:when>
	 			<c:when test="${qna.status  == 2}">
	 				<span class="status_Done"><ikep4j:message pre='${preUi}' key='listStatusSolve'/></span>
	 			</c:when>
	 		</c:choose>
				</td>
				<td class="textLeft">
				<div class="ellipsis">
					<a href="getQna.do?qnaId=${qna.qnaGroupId}&amp;listType=My">
					<c:if test="${qna.urgent == 1}">
						<img class="imgtable" src="<c:url value="/base/images/icon/ic_emer.gif"/>" alt="<ikep4j:message pre='${preUi}' key='listStatusUrgent'/>" />
					</c:if>
					${qna.title}
					</a>
					<c:if test="${qna.bestFlag == 1}">
						<img src="<c:url value="/base/images/icon/ic_best.gif"/>" alt="best" />
					</c:if>
				</div>
			 	</td>
				<td><div class="ellipsis">${qna.categoryName}</div></td>
				<td><div class="ellipsis"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${qna.registerName} ${qna.jobTitleName}</c:when><c:otherwise>${qna.userEnglishName} ${qna.jobTitleEnglishName}</c:otherwise></c:choose></div></td>
				<td><div class="ellipsis"><ikep4j:timezone date="${qna.registDate}" pattern="message.collpack.qna.timezone.dateformat.type2" keyString="true"/></div></td>
				<td>${qna.hitCount}</td>
				<td>${qna.recommendCount}</td>
			</tr>	
		</c:forEach>
		</tbody>
	</table>
</div>
	
<!--Page Numbur Start--> 
<spring:bind path="relationSearchCondition.pageIndex">
	<ikep4j:pagination searchFormId="relationForm" pageIndexInput="${status.expression}" searchCondition="${relationSearchCondition}" ajaxEventFunctionName="goRelationList"/>
	<form id="relationForm">
		<input name="${status.expression}" type="hidden"  value="${status.value}" title="pageIndex" />
	</form>
</spring:bind> 
<!--//Page Numbur End-->
