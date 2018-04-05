<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- ë©ìì§ ê´ë ¨ Prefix ì ì¸ Start --%> 
<c:set var="preHeader"  value="ui.portal.myspace.header" /> 
<c:set var="preList"    value="ui.portal.myspace.list" />
<c:set var="preDetail"  value="ui.portal.myspace.detail" />
<c:set var="preButton"  value="ui.portal.myspace.button" /> 
<c:set var="preMessage" value="ui.portal.myspace.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<%-- ë©ìì§ ê´ë ¨ Prefix ì ì¸ End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript" src="<c:url value="/base/js/units/support/favorite/favorite.js"/>"></script>

<script type="text/javascript" language="javascript">

(function($) {
	
	clickMyMenu = function(obj) {
		
		$chk = $jq(obj).parent().hasClass("opened");

		if($chk) {
			$jq(obj).parent().removeClass()
			$jq(obj).parent().children("ul").hide();
		}
		else {
			$jq(obj).parent().addClass("opened")
			$jq(obj).parent().children("ul").show();
		}
		
	}
	
	
	// onloadì ìíí  ì½ë
	$jq(document).ready(function() { 
		
	});
	
})(jQuery);  


</script>
 
<h1 class="none"><ikep4j:message pre='${preList}' key='mySpace' /></h1>
<div class="perInfo">
	<table summary="<ikep4j:message pre='${preList}' key='mySpace' />">
		<caption></caption>
		<tbody>
			<tr>
				<td width="49"><a href="<c:url value='/portal/main/listUserMain.do'/>?rightFrameUrl=/support/profile/getProfile.do" target="mainFrame">
					<img src="<c:url value='' /><c:out value='${user.profilePicturePath}' />" alt="image" width="41px" height="41px" onerror="this.src='<c:url value='/base/images/common/photo_170x170.gif'/>'"/></a>
				</td>
				<td width="155">
					<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}">
							<div class="perInfo_name">${user.userName} ${user.jobTitleName}</div>
							<div class="perInfo_team">${user.teamName}</div>
							<div class="perInfo_team">${user.timezoneName}</div>
						</c:when>
						<c:otherwise>
							<div class="perInfo_name">${user.userEnglishName} ${user.jobTitleEnglishName}</div>
							<div class="perInfo_team">${user.teamEnglishName}</div>
							<div class="perInfo_team">${user.timezoneName}</div>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<div class="conList" id="leftMyMenu">
	<ul>
		<li class="opened"><a href="#a" onclick="clickMyMenu(this);"><img src="<c:url value="/base/images/icon/ic_menu_place.png" />" alt="" /><ikep4j:message pre='${preList}' key='myPlace' /></a>
			<ul>
				
				<li>
					<a href="<c:url value='/portal/main/listUserMain.do'/>?rightFrameUrl=/support/profile/getProfile.do" target="mainFrame">
						<img src="<c:url value="/base/images/icon/ic_menu_profile.png" />" alt="" /> <ikep4j:message pre='${preList}' key='mySocialBlog' />
					</a>
				</li>
				<%--li>
					<a href="<c:url value='/support/addressbook/addressbookHome.do'/>" target="mainFrame">
						<img src="<c:url value="/base/images/icon/ic_menu_address.png" />" alt="" /> <ikep4j:message pre='${preList}' key='address' />
					</a>
				</li--%>
				<li>
					<a href="#a" onclick="iKEP.inputMemo();return false;">
						<img src="<c:url value="/base/images/icon/ic_menu_memo.png" />" alt="" /> <ikep4j:message pre='${preList}' key='memo' />
					</a>
				</li>
				<li>
                     <a onclick="iKEP.sendSms('', ''); return false;" href="#" target="mainFrame" title="<ikep4j:message pre='${preList}' key='sms' />">
                         <div class="ellipsis"><img src="<c:url value="/base/images/icon/ic_menu_sns.png" />" alt="" />
                         <ikep4j:message pre='${preList}' key='sms' /></div>
                     </a>
                </li>
<!-- 				<li> -->
<%-- 					<a href="<c:url value='/support/addressbook/addressbookHome.do'/>" target="mainFrame"> --%>
<%-- 						<img src="<c:url value="/base/images/icon/ic_menu_addressbook.png" />" alt="" /> <ikep4j:message pre='${preList}' key='myAddressbook' /> --%>
<!-- 					</a> -->
<!-- 				</li> -->
				
				<c:if test="${CommonConstant.PACKAGE_VERSION eq CommonConstant.IKEP_VERSION_FULL or CommonConstant.PACKAGE_VERSION eq CommonConstant.IKEP_VERSION_LIGHT}">
					<c:if test="${CommonConstant.PACKAGE_VERSION eq CommonConstant.IKEP_VERSION_FULL}">
					    <%--120919 최재영 무림에서 사용하지 않는 메뉴를 감춘다 --%>
						<!-- li><a href="<c:url value="/socialpack/socialblog/socialBlogHome.do" />" target="mainFrame"><img src="<c:url value="/base/images/icon/ic_menu_socialblog.png" />" alt="" />
							<ikep4j:message pre='${preList}' key='mySocialBlog' /></a></li-->
						<!-- li><a href="<c:url value="/socialpack/microblogging/privateHome.do" />" target="mainFrame"><img src="<c:url value="/base/images/icon/ic_menu_microblog.png" />" alt="" />
							<ikep4j:message pre='${preList}' key='myMicroBlog' /></a><span class="bottomline"></span></li-->
						
						<c:forEach var="collaboration" items="${collaborationList}">
							<li >
								<a href="<c:url value="/collpack/collaboration/main/Workspace.do?workspaceId=${collaboration.targetId}" />" target="mainFrame" title="${collaboration.title}">
								<div class="ellipsis">
								<img src="<c:url value="/base/images/icon/ic_menu_teamcoll.png" />" alt="" />
								${collaboration.title}
								</div></a>
							</li>
						</c:forEach>
						
						<c:forEach var="mblog" items="${mblogList}">
							<li >
								<a href="<c:url value="/socialpack/microblogging/groupHome.do?mbgroupId=${mblog.targetId}" />" target="mainFrame" title="${mblog.title}">
								<div class="ellipsis"><img src="<c:url value="/base/images/icon/ic_menu_sns.png" />" alt="" />
								${mblog.title}
								</div></a>
							</li>
						</c:forEach>
					</c:if>
				
					<c:forEach var="cafe" items="${cafeList}">
						<li >
							<!-- a href="#a" title="${cafe.title}" -->
							<a href="<c:url value='/lightpack/cafe/main/cafe.do?cafeId=${cafe.targetId}' />" target="mainFrame" title="${cafe.title}">
							<div class="ellipsis"><img src="<c:url value="/base/images/icon/ic_menu_cafe.png" />" alt="" />
							${cafe.title}
							</div></a>
						</li>
					</c:forEach>
				</c:if>
			</ul>
		</li>
		<li class="opened"><a href="#a" onclick="clickMyMenu(this);"><img src="<c:url value="/base/images/icon/ic_menu_history.png" />" alt="" /><ikep4j:message pre='${preList}' key='myHistory' /></a>
			<ul>
				
				<c:forEach var="favorite" items="${searchResultForPeople.entity}">
					<li class="ar per">
						<a href="<c:url value='/portal/main/listUserMain.do'/>?rightFrameUrl=/support/profile/getProfile.do?targetUserId=${favorite.targetId}" target="mainFrame" title="${favorite.title} ${favorite.jobTitleName}">
						<div class="ellipsis">${favorite.title} ${favorite.jobTitleName}</div></a></li>
				</c:forEach>
				
				<c:forEach var="favorite" items="${searchResultForDocument.entity}">
					<li class="ar docu">
						<a href="javascript:showDatail('${favorite.targetUrl}','${favorite.targetId}','${favorite.subId}','${ikep4j:replaceQuot(favorite.title)}')" title="${ikep4j:replaceQuot(favorite.title)}">
						<div class="ellipsis">${favorite.title}</div></a></li>		
				</c:forEach>			
					      
			</ul>
		</li>
	</ul>
</div>

<div class="btn_refresh"><a href="#a" onclick="refreshMySpace();"><span>refresh</span></a></div>
<div class="LeftContainer_bar">
	<div class="LeftContainer_bar_opened"><a href="#a" onclick="hideMySpace();"><span>closed</span></a></div>
</div>