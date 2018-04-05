<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<%@ page import="com.lgcns.ikep4.collpack.forum.constants.ForumConstants" %>

<c:set var="preUi" 			value="ui.collpack.forum.discussionForm" />

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:forEach var="discussion" items="${discussionList}" varStatus="loop">
<li class="lineRow">
	<c:if test="${!empty(discussion.categoryName) }"><span class="cate_block_1"><span class="cate_tit_2">${discussion.categoryName}</span></span></c:if>
	<p><a href="getView.do?discussionId=${discussion.discussionId}<c:if test="${!empty(param.docIframe) }">&amp;docPopup=${param.docIframe}&amp;docIframe=${param.docIframe}</c:if>">${discussion.title}</a></p> <span class="forum_topBox_num"><c:if test="${discussion.itemCount > 0}">[${discussion.itemCount}]</c:if></span>
	<span class="forum_name"><a href="#a" onclick="viewPopUpProfile('${discussion.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${discussion.registerName} ${discussion.jobTitleName}</c:when><c:otherwise>${discussion.userEnglishName} ${discussion.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
	<span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${discussion.teamName}</c:when><c:otherwise>${discussion.teamEnglishName}</c:otherwise></c:choose></span>						
	<div class="forum_con_re">
		<c:if test="${!empty(discussion.itemList)}">
		<ul>
			<c:forEach var="item" items="${discussion.itemList}" varStatus="loop">
				<li>
					<span class="forum_con"><a href="getView.do?itemId=${item.itemId }<c:if test="${!empty(param.docIframe) }">&amp;docPopup=${param.docIframe}&amp;docIframe=${param.docIframe}</c:if>">${item.title}</a> <span class="forum_topBox_num"><c:if test="${item.linereplyCount > 0}">(${item.linereplyCount})</c:if></span></span>
					<span class="forum_name"><a href="#a" onclick="viewPopUpProfile('${item.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.registerName} ${item.jobTitleName}</c:when><c:otherwise>${item.userEnglishName} ${item.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
					<span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.teamName}</c:when><c:otherwise>${item.teamEnglishName}</c:otherwise></c:choose></span>
					<span class="forum_date"><ikep4j:timezone date="${item.registDate}" pattern="message.collpack.forum.timezone.dateformat.type2" keyString="true"/></span>
				</li>
			</c:forEach>
		</ul>	
		</c:if>					
	</div>	
	
	<div class="forum_list_img" style="float:left;">
		<c:if test="${!empty(discussion.participantList)}">
		<ul id="partyFrame_${discussion.discussionId}">
			<c:forEach var="participant" items="${discussion.participantList}" varStatus="loop">
				<li><a href="#a" onclick="viewPopUpProfile('${participant.registerId}');return false;" title="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${participant.userName} ${participant.jobTitleName}</c:when><c:otherwise>${participant.userEnglishName} ${participant.jobTitleEnglishName}</c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${participant.teamName}</c:when><c:otherwise>${participant.teamEnglishName}</c:otherwise></c:choose>"><img src="<c:url value='${participant.profilePicturePath}'/>" alt="profileImage" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></li>
			</c:forEach>
		</ul>
		</c:if>
	</div>
	<c:if test="${discussion.participationCount > 5}">
		<div style="padding-top: 5px;" id="partyMoreBtn_${discussion.discussionId }"><a href="#a" class="next" onclick="partyList('${discussion.discussionId}','${discussion.participationCount}');return false;" title="next"><img src="<c:url value="/base/images/icon/ic_ar_next.gif"/>" alt="next" /></a></div>
	</c:if>
	<div style="clear: both;"></div>
	
	<div class="corporateViewTag">
	 	<!--tag list-->
		<div class="tableTag" id="tagForm_${discussion.discussionId}">    

		      <input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_FORUM %>"/>      <!--게시물 type TagConstants에서 맞는 타입 골라서 사용하면 됨.-team coll은 type ID를 넣으셔야 합니다.-->
		      <input type="hidden" name="tagItemSubType" value="<%=ForumConstants.TAG_TYPE_DISCUSSION%>"/>                      <!--서브 파입이 있는 경우만 -->
		      <input type="hidden" name="tagItemName" value="${discussion.title}"/>                          <!--게시물 제목-->
		      <input type="hidden" name="tagItemContents" value="${fn:escapeXml(discussion.contents)}"/>                   <!--게시물 내용 - html특수 기호 처리-->
		      <input type="hidden" name="tagItemUrl" value="/collpack/forum/getView.do?docPopup=true&amp;discussionId=${discussion.discussionId}"/> 
		      
		     <div>  <!--코딩파일엔 div가 없는데 꼭 넣어주어야 함.-->
		        <span class="ic_tag"><span><ikep4j:message pre='${preUi}' key='tag'/></span></span> <!--tagList--> 
		           <c:forEach var="tag" items="${discussion.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a></c:forEach>
		        <span class="rebtn">
		        <%-- 
		         <c:if test="${user.userId == discussion.registerId}">  <!--권한 체크 등록자랑 세션userID랑 같으면 태그 수정 가능-->
		           <a href="#a" onclick="iKEP.tagging.tagFormView('${discussion.discussionId}');return false;" title="<ikep4j:message pre='${preUi}' key='update'/>"><img src="<c:url value="/base/images/icon/ic_modify.gif"/>" alt="<ikep4j:message pre='${preUi}' key='update'/>" /></a>
		         </c:if>                                          <!--게시물 id--> 
		         --%>
		       </span>
		     </div>
		</div>
		<!--//tag list-->
	</div>											
</li>

</c:forEach>

<c:if test="${empty(discussionList)}">
	<li>
		<ikep4j:message key='message.collpack.forum.nodata.last'/>
	</li>
</c:if>