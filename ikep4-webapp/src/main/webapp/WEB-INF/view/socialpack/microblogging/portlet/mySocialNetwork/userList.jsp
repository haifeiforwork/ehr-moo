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

		<c:forEach var="user" items="${userList}">
			<li id="userUserId_${user.userId}" onclick="iKEP.showUserContextMenu(this, '${user.userId}', 'bottom')">
				<table summary="following 목록">
					<caption></caption>
					<tbody>
						<tr>
							<td></td>
							<td><a href="#a"><img alt="image" src="<c:url value='${user.profilePicturePath}' />" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'"></a></td>
							<td>
								<div class="imgList_1_info">
									<span class="imgList_1_id"><a href="#a">${user.userId}</a></span> 
									<c:choose>
										<c:when test="${sessionUser.localeCode == sessionPortal.defaultLocaleCode}">
											<span class="imgList_1_name"><a href="#a">${user.userName}</a></span>	
											<div>${user.teamName}</div>
										</c:when>
										<c:otherwise>
											<span class="imgList_1_name"><a href="#a">${user.userEnglishName}</a></span>	
											<div>${user.teamEnglishName}</div>
										</c:otherwise>
									</c:choose>
								</div>													
							</td>
						</tr>
					</tbody>
				</table>
			</li>
		</c:forEach>
		
																																											
						
<script type="text/javascript" language="javascript">
<!--
(function($) {
	
	// onload시 수행할 코드
	$jq(document).ready(function() {

		$jq("#followingCount").val("${followingCount}");
		$jq("#followerCount").val("${followerCount}");

		setMoreDiv();
	});
	
	
})(jQuery);  

//-->
</script>		
						