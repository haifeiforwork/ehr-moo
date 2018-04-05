<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<%@ page import="com.lgcns.ikep4.collpack.forum.constants.ForumConstants" %>

<c:set var="preUi" 			value="ui.collpack.forum.discussionForm" />

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:forEach var="discussion" items="${discussionList}" varStatus="qnaLoopCount">
	
	<li class="lineRow">
		<c:if test="${!empty(discussion.categoryName) }"><span class="cate_block_1"><span class="cate_tit_2">${discussion.categoryName}</span></span></c:if>
		<p><a href="getView.do?discussionId=${discussion.discussionId }">${discussion.title}</a></p> <span class="forum_topBox_num">(${discussion.itemCount})</span>
		<span class="forum_name"><a href="#a" onclick="viewPopUpProfile('${item.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${discussion.registerName} ${discussion.jobTitleName}</c:when><c:otherwise>${discussion.userEnglishName} ${discussion.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
		<span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${discussion.teamName}</c:when><c:otherwise>${discussion.teamEnglishName}</c:otherwise></c:choose></span>						
		<div class="forum_con_re">
			<a href="getView.do?discussionId=${discussion.discussionId }"><ikep4j:extractText text="${discussion.contents}" length="500"/></a>					
		</div>	
		
		<!--tag list-->
		<div class="corporateViewTag" id="tagForm_${discussion.discussionId}">    

		      <input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_FORUM %>"/>      <!--게시물 type TagConstants에서 맞는 타입 골라서 사용하면 됨.-team coll은 type ID를 넣으셔야 합니다.-->
		      <input type="hidden" name="tagItemSubType" value="<%=ForumConstants.TAG_TYPE_DISCUSSION%>"/>                      <!--서브 파입이 있는 경우만 -->
		      <input type="hidden" name="tagItemName" value="${discussion.title}"/>                          <!--게시물 제목-->
		      <input type="hidden" name="tagItemContents" value="${fn:escapeXml(discussion.contents)}"/>                   <!--게시물 내용 - html특수 기호 처리-->
		      <input type="hidden" name="tagItemUrl" value="/collpack/forum/getView.do?docPopup=true&amp;discussionId=${discussion.discussionId}"/> 
		      
		     <div>  <!--코딩파일엔 div가 없는데 꼭 넣어주어야 함.-->
		        <span class="ic_tag"><span><ikep4j:message pre='${preUi}' key='tag'/></span></span> <!--tagList--> 
		           <c:forEach var="tag" items="${discussion.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a></c:forEach>
		        <span class="rebtn">
		         <c:if test="${user.userId == discussion.registerId}">  <!--권한 체크 등록자랑 세션userID랑 같으면 태그 수정 가능-->
		           <a href="#a" onclick="iKEP.tagging.tagFormView('${discussion.discussionId}');return false;" title="<ikep4j:message pre='${preUi}' key='update'/>" class="ic_modify"><span><ikep4j:message pre='${preUi}' key='update'/></span></a>
		         </c:if>                                          <!--게시물 id--> 
		       </span>
		     </div>
		</div>
		<!--//tag list-->										
	</li>		
					
</c:forEach>	

<c:if test="${empty(discussionList)}">
	<li>
		<c:set var="requestUrl" value="${requestScope['javax.servlet.forward.request_uri']}"/>
		
		<c:choose>
			<c:when test="${fn:contains(requestUrl,'popularList.do')}">
				<ikep4j:message key='message.collpack.forum.nodata.dis.popular'/>
			</c:when>
			<c:when test="${fn:contains(requestUrl,'myActivity.do')}">
				<ikep4j:message key='message.collpack.forum.nodata.my.dis'/>
			</c:when>
			<c:otherwise>
				<ikep4j:message key='message.collpack.forum.noData.dis'/>
			</c:otherwise>
		</c:choose>
		
	</li>
</c:if>
