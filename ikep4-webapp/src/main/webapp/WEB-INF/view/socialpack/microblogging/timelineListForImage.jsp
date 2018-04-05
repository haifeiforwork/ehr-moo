<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="sessionPortal" value="${sessionScope['ikep.portal']}" />
<c:set var="sessionUser" value="${sessionScope['ikep.user']}" />

<c:forEach var="mblog" items="${mblogList}" varStatus="loopStatus">
	<li id="timelineMblogId_${mblog.mblogId}" name="timelineAddonCode_${mblog.addonCode}">
		<div class="microblog_img_3">
			<a href="#a" name='addonLink' onclick="iKEP.viewImageFile('${mblog.sourceLink}');">
				<!-- //이미지 정보. -->
				<img src="<c:url value='/support/fileupload/downloadFile.do?fileId=${mblog.sourceLink}' />" alt="Image" />
			</a>
		</div>
		<div class="microblog_con_3">
			<span class="valign_top ">
				<a href="#a" name="userInfo" id="${mblog.registerId}">
					<!-- //아이디에 해당하는 사진정보. -->
					<img src="<c:url value='${mblog.profilePicturePath}' />" width="25" height="25" title="${mblog.registerId}" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />
				</a>
			</span>
			<span class="microblog_id valign_middle"> <a href="#a" name="userInfo" id="${mblog.registerId}">${mblog.registerId}</a></span>
			<span class="microblog_name valign_middle">
				<c:choose>
					<c:when test="${sessionUser.localeCode == sessionPortal.defaultLocaleCode}">
						<c:out value="${mblog.registerName}"/>
					</c:when>
					<c:otherwise>
						<c:out value="${mblog.registerEnglishName}"/>
					</c:otherwise>
				</c:choose>
			</span>
			<p class="ellipsis"><span class="microblog_id"><a href="#a" name='addonLink' onclick="iKEP.viewImageFile('${mblog.sourceLink}');"><c:out value="${mblog.displayCode}"/></a></span>  <c:out value="${mblog.fileRealName}"/><br>
			${mblog.contents} </p>
			<span class="microblog_time">${ikep4j:getTimeInterval(mblog.registDate, sessionUser.localeCode)} </span>
		</div>
	</li>
</c:forEach>