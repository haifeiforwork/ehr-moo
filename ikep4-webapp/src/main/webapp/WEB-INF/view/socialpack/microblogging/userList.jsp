<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="sessionPortal" value="${sessionScope['ikep.portal']}" />
<c:set var="sessionUser" value="${sessionScope['ikep.user']}" />

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preTap"    	value="ui.socialpack.microblogging.tap" />
<c:set var="preButton"  value="ui.socialpack.microblogging.button" />
<c:set var="preLink"  	value="ui.socialpack.microblogging.link" /> 
<c:set var="preLabel" 	value="ui.socialpack.microblogging.label" />
<c:set var="preMessage"	value="ui.socialpack.microblogging.message" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<c:if test="${not empty addFollowScript}">

<script type="text/javascript">
<!--   

	// Following 추가 버튼 생성
	addFollowBtn = function(followingUserId, divArea) {
		var str = "<a class='button_pr' href='#a' onclick=\"javascript:follow('"+followingUserId+"','"+divArea+"')\">";
		str = str + "<span name='strongButtons'><img src='" + iKEP.getContextRoot() +"/base/images/icon/ic_plus.gif' alt='' />";
		str = str + "follow</span>";
		str = str + "</a>";
		//alert(str);
		$jq("#"+divArea).html(str);
		$jq("span[name="+divArea+"]").html(str);
		
	};
		
	// Following 삭제 버튼 생성
	delFollowBtn = function(followingUserId, divArea) {
		var str = "<a class='button_follow' href='#a' onclick=\"javascript:unfollow('"+followingUserId+"','"+divArea+"')\">";
		str = str + "<span name='strongButtons'>unfollow</span>";
		str = str + "</a>";
		//alert(str);
		$jq("#"+divArea).html(str);
		$jq("span[name="+divArea+"]").html(str);
	};
		
	// follow하기
	follow = function(userId, divArea){
		//alert(userId);
		$jq.post(iKEP.getContextRoot() + "/socialpack/microblogging/follow/createFollow.do", 
				{'followingUserId':userId}, 
				function(data) {
					//alert(data);
					delFollowBtn(userId, divArea);
		});
	};
	
	// follow 취소하기
	unfollow = function(userId, divArea){
		//alert(userId);
		$jq.post(iKEP.getContextRoot() + "/socialpack/microblogging/follow/removeFollow.do", 
				{'followingUserId':userId}, 
				function(data) {
					//alert(data);
					addFollowBtn(userId, divArea);
		});
	};

//-->
</script>

</c:if>

<c:forEach var="user" items="${userList}" varStatus="loopStatus">
	<li id="userUserId_${user.userId}">
		<div class="microblog_img">
			<a href="#a" name="userInfo" id="${user.userId}">
				<!-- //아이디에 해당하는 사진정보. -->
				<img src="<c:url value='${user.profilePicturePath}' />" width="50" height="50" alt="" title="${user.userId}" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />
			</a>
		</div>
		<div class="microblog_con">
			<span class="microblog_id"><a href="#a" name="userInfo" id="${user.userId}"><c:out value="${user.userId}"/></a></span>
			<span class="microblog_name"><c:choose><c:when test="${sessionUser.localeCode == sessionPortal.defaultLocaleCode}"><c:out value="${user.userName}"/></c:when><c:otherwise><c:out value="${user.userEnglishName}"/></c:otherwise></c:choose></span>
			<div class="ic_micro_ar none"></div>
			<p><c:out value="${user.expertField}" escapeXml="false"/></p>
			<span id="followBtn_${user.userId}" >
				<c:if test="${sessionUser.userId == user.userId}">
					&nbsp;
				</c:if>
				<c:if test="${sessionUser.userId != user.userId}">
					<c:if test="${not empty user.isFollowing}">
						<a class="button_follow" href="#a" onclick="unfollow('${user.userId}','followBtn_${user.userId}');"><span name="strongButtons"><ikep4j:message pre='${preButton}' key='unfollow' /></span></a>
					</c:if>
					<c:if test="${empty user.isFollowing}">
						<a class="button_pr" href="#a" onclick="follow('${user.userId}','followBtn_${user.userId}');"><span name="strongButtons"><img src="<c:url value='/base/images/icon/ic_plus.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='follow' /></span></a>
					</c:if>
				</c:if>
			</span>
		</div>
		<div class="selected_ar"></div>
	</li>
</c:forEach>