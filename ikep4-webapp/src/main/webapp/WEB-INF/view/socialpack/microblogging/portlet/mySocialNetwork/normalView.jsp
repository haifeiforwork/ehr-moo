<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 
<c:set var="sessionPortal" value="${sessionScope['ikep.portal']}" />
<c:set var="sessionUser" value="${sessionScope['ikep.user']}" />

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preMessage"	value="ui.socialpack.microblogging.message" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
<!--   

(function($) {
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
	    
	});

})(jQuery);  
		 
//-->
</script>

<div id="${portletConfigId}" class="tableList_1">
	
		<div class="imgList_1">
			<ul>
				<c:choose>
				    <c:when test="${empty followingList}">
						<tr>
							<td colspan="3" class="emptyRecord"><ikep4j:message pre='${preMessage}' key='list.empty' /></td> 
						</tr>				        
				    </c:when>
				    <c:otherwise>
						<c:forEach var="userInfo" items="${followingList}">
							<li>
								<a href="#a" onclick="iKEP.showUserContextMenu(this, '${userInfo.userId}', 'bottom')"><img alt="image" src="<c:url value='${userInfo.profilePicturePath}' />" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'"></a>
								<c:choose>
									<c:when test="${sessionUser.localeCode == sessionPortal.defaultLocaleCode}">
										<p><a href="#a" onclick="iKEP.showUserContextMenu(this, '${userInfo.userId}', 'bottom')" >${userInfo.userName}</a></p>
									</c:when>
									<c:otherwise>
										<p><a href="#a">${userInfo.userEnglishName}</a></p>
									</c:otherwise>
								</c:choose>
							</li>
						</c:forEach>
					</c:otherwise>	
				</c:choose>	
			</ul>
			<div class="clear"></div>				
			<div class="l_b_corner"></div><div class="r_b_corner"></div>
		</div>
</div>
