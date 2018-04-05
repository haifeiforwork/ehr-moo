<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" /> 
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
<!--

(function($) {
	
	// Profile 이동
	goProfile = function(targetUserId) {
		document.location.href = "<c:url value='/support/profile/getProfile.do?targetUserId='/>" + targetUserId  ;
	};
	
	// Follower List 이동
	goFollowList = function(type) {
		if( type == 'FOLLOWING' ){ //tabNum = tab7
			parent.document.location.href = "<c:url value='/socialpack/microblogging/privateHome.do?tabNum=tab7&ownerId=${targetUserId}'/>";
		}else{ //, tabNum = tab8
			parent.document.location.href = "<c:url value='/socialpack/microblogging/privateHome.do?tabNum=tab8&ownerId=${targetUserId}'/>";
		}
		
	};
	
	getFollowUser = function(){ 
		
		$jq.ajax({
		    url : "<c:url value='/socialpack/microblogging/getFollowingFollowerList.do'/>" ,
		    data : {'ownerId':'${targetUserId}'},  
		    type : "get",
		    success : function(result) {

		    	var followerList, followingList, followCountHtml, followingCountHtml, followBodyHtml, followingBodyHtml;
		    	
		    	$.each(result, function(index, value){
		    		
		    		if( index == 'followerList'){
		    			
		    				followerList = value;
				    		followBodyHtml = "<ul>";
				    		idxCount = 0;
				    		
				    		//개별 Row
				    		$.each(followerList, function(frindex, frvalue){
				    			
				    			var frindex = frvalue;
				    			var userId, userName, profilePicturePath; 
				    			
				    			$.each(frindex, function(frindex1, frvalue1){
				    				
				    				if( frindex1 == 'userId' ){
				    					userId = frvalue1;
				    				}
				    				
				    				<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}">
										if( frindex1 == 'userName' ){
				    						userName = frvalue1;
				    					}
									</c:when>
									<c:otherwise>
										if( frindex1 == 'userEnglishName' ){
											userName = frvalue1;
				    					}
									</c:otherwise>
									</c:choose>
									
									if( frindex1 == 'profilePicturePath' ){
										profilePicturePath = frvalue1;
			    					}
				    			});
				    			
				    			if(idxCount < 5){
				    				followBodyHtml = followBodyHtml + "<li><a onclick=\"goProfile(\'"+userId+"\');\" href=\"#a\"><img src=\""+iKEP.getContextRoot()+profilePicturePath+"\" width=\"34\" height=\"34\" title=\""+userName+"\" alt=\""+userName+"\" onerror=\"this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'\" /></a></li>";	
				    			}
				    			
				    			idxCount++;
				    		});
				    		
				    		followCountHtml = "("+idxCount+")";
				    		$("#proFollowerCount").html(followCountHtml);
				    		followBodyHtml = followBodyHtml + "<ul>";
				    		if( idxCount <= 0 ){
				    			$("#proFollowerList").html("<p align=\"center\"><ikep4j:message pre='${preMsgProfile}' arguments='Follower' key='follow.nodata'/></p>");
				    		}else{
				    			$("#proFollowerList").html(followBodyHtml);
				    		}
				    		
			    		
		    		}
		    		
		    		if( index == 'followingList'){
		    			
			    			followingList = value;
				    		followingBodyHtml = "<ul>";
				    		idxCount = 0;
				    		
				    		//개별 Row
				    		$.each(followingList, function(fgindex, fgvalue){
				    			
				    			var fgindex = fgvalue;
				    			var userId, userName, profilePicturePath; 
				    			
				    			$.each(fgindex, function(fgindex1, fgvalue1){
				    				
				    				if( fgindex1 == 'userId' ){
				    					userId = fgvalue1;
				    				}
				    				
									<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}">
										if( fgindex1 == 'userName' ){
					    					userName = fgvalue1;
					    				}
									</c:when>
									<c:otherwise>
										if( fgindex1 == 'userEnglishName' ){
											userName = fgvalue1;
					    				}
									</c:otherwise>
									</c:choose>
									
									if( fgindex1 == 'profilePicturePath' ){
										profilePicturePath = fgvalue1;
			    					}
				    				
				    			});
				    			
				    			if(idxCount < 5){
				    				followingBodyHtml = followingBodyHtml + "<li><a onclick=\"goProfile(\'"+userId+"\');\" href=\"#a\"><img src=\""+iKEP.getContextRoot()+profilePicturePath+"\" width=\"34\" height=\"34\" title=\""+userName+"\" alt=\""+userName+"\" onerror=\"this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'\" /></a></li>";
				    			}
				    			idxCount++;
				    		});
				    		
				    		followingCountHtml = "("+idxCount+")";
				    		$("#proFollowingCount").html(followingCountHtml);
				    		followingBodyHtml = followingBodyHtml + "<ul>";

				    		if( idxCount <= 0 ){
				    			$("#proFollowingList").html("<p valign=\"middle\" align=\"center\"><ikep4j:message pre='${preMsgProfile}' arguments='Following' key='follow.nodata'/></p>");
				    		}else{
				    			$("#proFollowingList").html(followingBodyHtml);
				    		}
		    		}

		    	});
		   	     
		    }
		});
	};
	
	
	$jq(document).ready(function() {
		getFollowUser();
	});
	
})(jQuery);  
//-->
</script>

						<div class="pr_follow">	
							<div class="follow_photo" >
								<h3><ikep4j:message pre='${preProfileMain}' key='following.title'/> <span id="proFollowingCount"></span></h3>
								<div class="more"><a onclick="goFollowList('FOLLOWING');" href="#a"><img src="<c:url value='/base/images/common/btn_more.gif' />" alt="more" /></a></div>
								<div id="proFollowingList">
								</div>
							</div>
							<div class="follow_photo"=>
								<h3><ikep4j:message pre='${preProfileMain}' key='follower.title'/> <span id="proFollowerCount"></span></h3>
								<div class="more"><a onclick="goFollowList('FOLLOWIER');"href="#a"><img src="<c:url value='/base/images/common/btn_more.gif' />" alt="more" /></a></div>
								<div id="proFollowerList">
								</div>
							</div>
							<div class="follow_photo">
								<h3><ikep4j:message pre='${preProfileMain}' key='fellowexport.title'/><span> (${fellowExpertListSize})</span></h3> 
								<!-- <div class="more"><a href="#a"><img src="<c:url value='/base/images/common/btn_more.gif' />" alt="more" /></a></div>-->
								
								<c:choose>
								<c:when test="${empty fellowExpertList}">
									<p align="center"><ikep4j:message pre='${preMsgProfile}' key='fellowExpert.nodata'/></p>
								</c:when>
								<c:otherwise>
									<ul>
									<c:forEach var="fellowExpert" items="${fellowExpertList}" varStatus="status">
									<c:if test="${status.index < 5 }" >
										<li><a onclick="goProfile('${fellowExpert.expertUserId}');" href="#a"><img src="<c:url value='${fellowExpert.profilePicturePath}' />" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" width="34" height="34" title="<c:choose><c:when test='${user.localeCode == portal.defaultLocaleCode}'>${fellowExpert.userName}</c:when><c:otherwise>${fellowExpert.userEnglishName}</c:otherwise></c:choose>" alt="<c:choose><c:when test='${user.localeCode == portal.defaultLocaleCode}'>${fellowExpert.userName}</c:when><c:otherwise>${fellowExpert.userEnglishName}</c:otherwise></c:choose>" /></a></li>
									</c:if>
									</c:forEach>
									</ul>
									<ul>
									<c:forEach var="fellowExpert" items="${fellowExpertList}" varStatus="status">
									<c:if test="${status.index >= 5 and status.index < 10}" >
										<li><a onclick="goProfile('${fellowExpert.expertUserId}');" href="#a"><img src="<c:url value='${fellowExpert.profilePicturePath}' />" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" width="34" height="34" title="<c:choose><c:when test='${user.localeCode == portal.defaultLocaleCode}'>${fellowExpert.userName}</c:when><c:otherwise>${fellowExpert.userEnglishName}</c:otherwise></c:choose>" alt="<c:choose><c:when test='${user.localeCode == portal.defaultLocaleCode}'>${fellowExpert.userName}</c:when><c:otherwise>${fellowExpert.userEnglishName}</c:otherwise></c:choose>" /></a></li>
									</c:if>
									</c:forEach>
									</ul>
								</c:otherwise>
								</c:choose>
							</div>
						</div>


