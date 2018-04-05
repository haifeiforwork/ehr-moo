<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preProfileMenu" value="ui.socialpack.socialblog.management.designsetting.layout.portlet" />
<c:set var="preButton"  value="ui.socialpack.socialblog.common.button" /> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preMain" value="ui.socialpack.socialblog.common.webstandard" />
<c:set var="preManage" value="ui.socialpack.socialblog.management" />
<c:set var="preMessage" value="message.socialpack.socialblog.common.socialBoardItem" />
<c:set var="preManageMessage" value="message.socialpack.socialblog.management" />

<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value='/base/js/zeroclipboard/ZeroClipboard.js'/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/socialpack/socialblog.js"/>"></script>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
<!--

(function($) {
	
	$jq(document).ready(function() {
		
		SocialBlog.getBlogintroductionView('${socialBlog.ownerId}','view');	
		
		$jq("input[name=totalSearch]").bind("keydown", function(event) {
			if(event.which == 13) {
				//$("#btnSave").trigger("click");
				getSearchAllType('searchAllType');
			}
		});
		
		initRssClipBoard2();
		
	});
	
	getSearchAllType = function() {		
		searchKeyword = $jq("input[name=totalSearch]").val();
		getSearchBlogPosting('ALL',searchKeyword,'3');
	}; 
	
	
	getSocialBlogManagePopup = function(isSystemAdmin,isBlogAdmin,blogOwnerId) {
		
		if( isSystemAdmin || isBlogAdmin ){
			var result = iKEP.dialogOpen("<c:url value='/socialpack/socialblog/setting/socialblogSettingHome.do?blogOwnerId='/>"+blogOwnerId, {width:960, height:620, modal : true, resizable:true, callback : function(result) { refreshSocialBlog(); }});
			refreshSocialBlog();
		}else{
			alert("<ikep4j:message pre='${preManageMessage}' key='openError' />");
		}
	}; 
	
	// 블로그 새로 고침
	refreshSocialBlog = function() {
		document.location.href = "<c:url value='/socialpack/socialblog/socialBlogHome.do?blogOwnerId=${blogOwnerId}'/>";
	};
	
	// 소셜 블로그 이동
	goProfile = function() {
		iKEP.goProfileMain('${blogOwnerId}');
		//document.location.href = "<c:url value='${profileHomeUrl}?targetUserId=${blogOwnerId}'/>" ;
	};
	
	// 마이크로 블로그 이동
	goMicroblog = function() {
		document.location.href = "<c:url value='${microblogHomeUrl}?ownerId=${blogOwnerId}'/>" ;
	};

})(jQuery);  
//-->
</script>

<!--socialblog_pr Start-->
<div class="socialblog_pr" >
	<h2 class="none"><ikep4j:message pre='${preProfileMenu}' key='profile.blogPR'/></h2>
	
	<c:if test="${profile != null}">
	<img id="mainPictureImage" src="<c:url value='${profile.profilePicturePath}' />" width="150" height="150" alt="프로파일 큰 이미지"  onerror="this.src='<c:url value='/base/images/common/photo_170x170.gif'/>'"/>
	<div class="socialblog_pr_info">
		<span class="socialblog_pr_name"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${profile.userName}"/></c:when><c:otherwise><c:out value="${profile.userEnglishName}"/></c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${profile.jobTitleName}"/></c:when><c:otherwise><c:out value="${profile.jobTitleEnglishName}"/></c:otherwise></c:choose></span>
		<span class="socialblog_pr_id">(<c:out value="${profile.userId}"/>)</span>
	</div>
	</c:if>
	
	<c:if test="${blogOwnerId == user.userId}">
	<div class="addr_setting">
		<a href="#a" title="setting" onclick="getSocialBlogManagePopup('${isSystemAdmin}','${isBlogAdmin}','${blogOwnerId}')" ><ikep4j:message pre='${preProfileMenu}' key='profile.management'/></a> 
	</div>
	</c:if>
	
				
	<div class="socialblog_pr_ic" id="feedDiv" style="position:relative">
		<ul>
			<li>
			  <input type="hidden" id="feedMetaType" name="feedMetaType" value="SB" />
			  <input type="hidden" id="feedMetaId" name=""feedMetaId"" value="${user.userId}" />
			  <input type="hidden" id="feedLocale" name="feedLocale" value="${user.localeCode}" />
			  <img src="<c:url value='/base/images/icon/ic_rss_2.gif'/>"  alt="rss" id="rssFeedBtn" />
			  <img src="<c:url value='/base/images/icon/ic_atom_2.gif'/>" alt="atom" id="atomFeedBtn" />
			</li>
			<li><a href="#a" onclick="goProfile();"><img src="<c:url value='/base/images/icon/ic_microblog.gif' />" alt="<ikep4j:message pre='${preMain}' key='goProfile' />" title="<ikep4j:message pre='${preMain}' key='goProfile' />" /></a></li>
			<li><a href="#a" onclick="goMicroblog();"><img src="<c:url value='/base/images/icon/ic_socialblog.gif '/>" alt="<ikep4j:message pre='${preMain}' key='goMicroBlog' />" title="<ikep4j:message pre='${preMain}' key='goMicroBlog' />" /></a></li>
		</ul>
	</div>

	<div class="borderFrame" id="edit_introduction">
	</div>
	
</div>
<!--//socialblog_pr End-->

<c:if test="${blogOwnerId == user.userId}">
<!--blockButton_2 Start-->
<div class="blockButton_2"> 
	<a onclick="SocialBlog.getNewPosting('${blogOwnerId}')" class="button_2" href="#a"><span><img src="<c:url value='/base/images/icon/ic_post.gif' />" alt="<ikep4j:message pre='${preProfileMenu}' key='profile.writePosting'/>" /><ikep4j:message pre='${preProfileMenu}' arguments='${followerCount}' key='profile.writePosting'/></span></a>			
</div>
</c:if>

<!--socialblog_search Start-->
<div class="socialblog_search">
	<h2 class="none"><ikep4j:message pre='${preProfileMenu}' key='profile.searchBlog'/></h2>
	<input type="text" class="inputbox" title="<ikep4j:message pre='${preButton}' key='search'/>" name="totalSearch" value="" style="width:116px" />
	<a onclick="getSearchAllType('searchAllType')" href="#a" class="ic_search"><span><ikep4j:message pre='${preButton}' key='search'/></span></a>												
</div>
<!--//socialblog_search End-->
						
						
						
						
