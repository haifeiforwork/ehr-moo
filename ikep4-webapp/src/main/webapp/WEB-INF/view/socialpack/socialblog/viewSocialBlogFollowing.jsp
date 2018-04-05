<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<c:set var="preFollowMenu" value="ui.socialpack.socialblog.management.designsetting.layout.portlet" />
<c:set var="preFollowMessage" value="message.socialpack.socialblog.management.designsetting.layout.portlet" />

<script type="text/javascript">
<!--

(function($) {

	// Follower List 이동
	goFollowingList = function() {
		document.location.href = "<c:url value='/socialpack/microblogging/privateHome.do?tabNum=tab7&ownerId=${targetUserId}'/>";
		
	};
	
	getFollowingUser = function(){ 
		
		$jq.ajax({
		    url : "<c:url value='/socialpack/microblogging/getFollowingFollowerList.do'/>" ,
		    data : {'ownerId':'${blogOwnerId}'},  
		    type : "get",
		    success : function(result) {

		    	var followingList, followingCountHtml, followingBodyHtml;
		    	
		    	$.each(result, function(index, value){
		    		
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
				    				followingBodyHtml = followingBodyHtml + "<li><a  href=\"<c:url value='/portal/main/listUserMain.do'/>?rightFrameUrl=/support/profile/getProfile.do?targetUserId="+userId+"\"><img src=\""+iKEP.getContextRoot()+profilePicturePath+"\" width=\"25\" height=\"25\" title=\""+userName+"\" alt=\""+userName+"\" onerror=\"this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'\" /></a></li>";
				    			}
				    			
				    			idxCount++;
				    		});
				    		followingCountHtml = "("+idxCount+")";
				    		$("#proFollowingCount").html(followingCountHtml);
				    		followingBodyHtml = followingBodyHtml + "<ul>";

				    		if( idxCount <= 0 ){
				    			$("#proFollowingList").html("<p valign=\"middle\" align=\"center\"><ikep4j:message pre='${preFollowMessage}' key='following.nodata'/></p>");
				    		}else{
				    			$("#proFollowingList").html(followingBodyHtml);
				    			
				    			$("#followingmore").html("<a onclick=\"goFollowingList();\" href=\"#a\"><img src=\"<c:url value='/base/images/common/btn_more.gif' />\" alt=\"more\" /></a>" );
				    			$("#followingLink").html("<img src=\"<c:url value='/base/images/icon/ic_blog.gif' />\" alt=\"\" /> <a href=\"#a\" onclick=\"getMicroBlogFollowingTimeLine();\"><ikep4j:message pre='${preFollowMenu}' key='following.recentBlogging'/></a>");
				    			
				    		}
		    		}

		    	});
		   	     
		    }
		});
	};
	
	
	$jq(document).ready(function() {
		getFollowingUser();
	});
	
})(jQuery);  
//-->
</script>

						<div class="blockList_2">

							<h2><ikep4j:message pre='${preFollowMenu}' key='following.title'/></h2>
							<div class="more" id="followingmore"></div>
							<div id="proFollowingList">
							</div>
							<div class="link" id="followingLink"></div>
						</div>

