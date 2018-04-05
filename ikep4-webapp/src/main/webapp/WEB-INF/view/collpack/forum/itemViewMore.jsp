<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<%@ page import="com.lgcns.ikep4.collpack.forum.constants.ForumConstants" %>

<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>

<c:set var="preUi" 			value="ui.collpack.forum.view" />
<c:set var="preResource" 	value="message.collpack.forum" />

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
	$jq(document).ready(function() {
	
	lineValid = new iKEP.Validator('#linereplyForm_${frItem.itemId}', lineValidOptions);
	
	//즐겨찾기
	<c:choose>
		<c:when test="${isFavorite == 'true'}">
			toggleFavorteImg('del', '${frItem.itemId}');
		</c:when>
		<c:otherwise>
			toggleFavorteImg('add', '${frItem.itemId}');
		</c:otherwise>
	</c:choose>
	 
});

</script>
<!--blockListTable Start-->
<div class="blockTableRead">
	<div class="blockTableRead_t">
		<div class="forum_list_photo"><a href="#a" onclick="viewPopUpProfile('${frItem.registerId}');return false;" title="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${frItem.registerName} ${frItem.jobTitleName}</c:when><c:otherwise>${frItem.userEnglishName} ${frItem.jobTitleEnglishName}</c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${frItem.teamName}</c:when><c:otherwise>${frItem.teamEnglishName}</c:otherwise></c:choose> "><img src="<c:url value='${frItem.profilePicturePath}'/>" alt="profileImage" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></div>	
		<p>${frItem.title}&nbsp;
		
			<!--favorite start-->
			<c:if test="${param.docPopup != 'true' || !empty(param.docIframe)}">
				<a class="ic_rt_favorite" href="#a" id="linkFavorite" title="<ikep4j:message pre='${preUi}' key='viewFavoriteAdd'/>"><span></span></a> 
			</c:if>
			<!--//favorite end-->
			
			<c:if test="${(user.userId == frItem.registerId || isAdmin) && (param.docPopup != 'true' || !empty(param.docIframe))}">
				<span class="rebtn">
					<a class="ic_modify" href="#a" onclick="updateItemForm('${frItem.itemId}');"  title="<ikep4j:message pre='${preUi}' key='viewUpdate'/>"><span><ikep4j:message pre='${preUi}' key='viewUpdate'/></span></a>
					<a class="ic_delete" href="#a" onclick="delItem('${frItem.itemId}');" title="<ikep4j:message pre='${preUi}' key='viewDelete'/>"><span><ikep4j:message pre='${preUi}' key='viewDelete'/></span></a>
				</span>
			</c:if>
				
		</p>
		<div class="forum_list_info">
			<span class="forum_name"><a href="#a" onclick="viewPopUpProfile('${frItem.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${frItem.registerName} ${frItem.jobTitleName}</c:when><c:otherwise>${frItem.userEnglishName} ${frItem.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
			<span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${frItem.teamName}</c:when><c:otherwise>${frItem.teamEnglishName}</c:otherwise></c:choose></span>
			<span class="forum_date"><ikep4j:timezone date="${frItem.registDate}" pattern="message.collpack.forum.timezone.dateformat.type2" keyString="true"/></span>
			<span class="forum_etc"><ikep4j:message pre='${preUi}' key='hit'/>  <strong id="itemHitCount">${frItem.hitCount}</strong></span>
			<span class="forum_etc"><ikep4j:message pre='${preUi}' key='favorite'/>  <strong id="itemFavoriteCount">${frItem.favoriteCount}</strong></span>
			<span class="forum_etc"><ikep4j:message pre='${preUi}' key='lineShot'/>  <strong id="itemLinereplyCount">${frItem.linereplyCount}</strong></span>
		</div>	
		<div class="recommend">
			<a class="button_rec_num" href="#a" onclick="agree('${frItem.itemId}', '${frItem.discussionId}');" title="<ikep4j:message pre='${preUi}' key='agree'/>"><span class="num" id="itemAgreementCount">${frItem.agreementCount}</span><span class="doc"><img src="<c:url value="/base/images/icon/ic_recommend.gif"/>" alt="<ikep4j:message pre='${preUi}' key='agree'/>" /><ikep4j:message pre='${preUi}' key='agree'/></span></a>&nbsp;
			<a class="button_rec_num" href="#a" onclick="opposite('${frItem.itemId}', '${frItem.discussionId}');" title="<ikep4j:message pre='${preUi}' key='opp'/>"><span class="num" id="itemOppositionCount">${frItem.oppositionCount}</span><span class="doc"><img src="<c:url value="/base/images/icon/ic_recommend_b.gif"/>" alt="<ikep4j:message pre='${preUi}' key='opp'/>" /><ikep4j:message pre='${preUi}' key='opp'/></span></a>	
		</div>
	</div>
	
	<div class="blockTableRead_c">
		<p>
			${frItem.contents}
		</p>
		
		<!--tag list-->
		<div class="tableTag" id="tagForm_${frItem.itemId}">     
		
		      <input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_FORUM %>"/>      
		      <input type="hidden" name="tagItemSubType" value="<%=ForumConstants.TAG_TYPE_ITEM %>"/>                  <!--서브 파입이 있는 경우만 -->
		      <input type="hidden" name="tagItemName" value="${frItem.title}"/>                          				<!--게시물 제목-->
		      <input type="hidden" name="tagItemContents" value="${fn:escapeXml(frItem.contents)}"/>                   <!--게시물 내용 - html특수 기호 처리-->
		      <input type="hidden" name="tagItemUrl" value="/collpack/forum/getView.do?docPopup=true&amp;discussionId=${frItem.discussionId}&amp;itemId=${frItem.itemId}"/> <!--상세화면 URL -body 화면만 나와야 함. 도메인과 contextPash는 빼주시기 바랍니다.-->
		      
		     <div> 
		        <span class="ic_tag"><span><ikep4j:message pre='${preUi}' key='viewTag'/></span></span> <!--tagList--> 
		           <c:forEach var="tag" items="${itemTagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a></c:forEach>
		        <span class="rebtn">
		         <c:if test="${user.userId == frItem.registerId}">  
		           <a href="#a" <c:if test="${param.docPopup != 'true' || !empty(param.docIframe)}"> onclick="iKEP.tagging.tagFormView('${frItem.itemId}');return false;" </c:if>title="<ikep4j:message pre='${preUi}' key='viewUpdate'/>" class="ic_modify"><span><ikep4j:message pre='${preUi}' key='viewUpdate'/></span></a>
		         </c:if>                                        
		       </span>
		     </div>
		</div>
		<!--//tag list-->
		
	</div>


</div>
<!--//blockListTable End-->	


<!--blockComment Start-->
<div class="blockComment">
	<div class="blockComment_t">
		<ikep4j:message pre='${preUi}' key='viewLinereply'/> <span class="comment_num" id="item2LinereplyCount"><c:if test="${frItem.linereplyCount > 0}">(${frItem.linereplyCount})</c:if></span>
	</div>

	<div class="guestbook_write" id="guestbook_write_${frItem.itemId}">
		<form id="linereplyForm_${frItem.itemId}" action="">
		<input type="hidden" name="discussionId" value="${frItem.discussionId}"/>
		<input type="hidden" name="itemId" value="${frItem.itemId}"/>
		<input type="hidden" name="tmpId" value="${frItem.itemId}"/>
		<table summary="write">
			<caption></caption>
			<tr>
				<td><div>
					<textarea name="contents" title="contents" cols="" rows="3" ></textarea>
					</div>
				</td>
				<td width="74" class="textRight">
					<a class="button_re" href="#a" onclick="addReply('${frItem.itemId}');" title="<ikep4j:message pre='${preUi}' key='viewSave'/>"><span><ikep4j:message pre='${preUi}' key='viewSave'/></span></a>
				</td>
			</tr>
		</table>
		</form>
	</div>
			
	<div id="lineReply_${frItem.itemId}">
		<c:import url="/WEB-INF/view/collpack/forum/linereplyListMore.jsp" charEncoding="UTF-8" />
	</div>	

</div>
<!--//blockComment End-->