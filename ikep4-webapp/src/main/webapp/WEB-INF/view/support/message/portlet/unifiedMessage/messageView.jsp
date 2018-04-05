<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/theme01/jquery_ui_custom.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/common.css"/>" /> 
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/theme01/theme.css"/>" /> 
<c:set var="preList"   value="ui.support.message.portlet.list" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<div class="blockMsgbox_2"> 
	<h3 class="none">message view</h3>
	<div class="blockMsgbox_img">
		<img src="<c:url value='${message.profilePicturePath}' />" width="50" height="50" alt="userImg" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />
	</div>
	<div class="blockMsgbox_info">
		<BR>
		<c:choose>
	      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
	       <span class="blockMsgbox_info_name"><a href="#a"> ${senderUser.userName} ${senderUser.jobTitleName} </a></span>
		   <span class="blockMsgbox_info_team">${senderUser.teamName}</span>
	      </c:when>
	      <c:otherwise>
	       <span class="blockMsgbox_info_name"><a href="#a"> ${senderUser.userEnglishName} ${senderUser.jobTitleEnglishName} </a></span>
		   <span class="blockMsgbox_info_team">${senderUser.teamEnglishName}</span>
	      </c:otherwise>
	     </c:choose>
	     <div>
			<span class="blockMsgbox_info_t"><ikep4j:message pre='${preList}' key='sendDate'/></span>
			<span class="blockMsgbox_info_c"><ikep4j:timezone date="${message.sendDate}" pattern="yyyy.MM.dd HH:mm:ss"/> </span>
		 </div>
	</div>
	<c:if test="${message.fileDataList != null && message.fileDataList != '[]' }">
	<div class="filedown_ic"><ikep4j:message pre='${preList}' key='attachFile'/></div>
	<div class="filedown">
		<ul>
			<c:forEach var="fileData" items="${message.fileDataList}">
				<li><a href='<c:url value="/support/fileupload/downloadFile.do?fileId=${fileData.fileId}&amp;thumbnailYn=N" />' >${fileData.fileRealName}</a> </li>
			</c:forEach> 
		</ul>
	</div>
	</c:if>
	<div class="clear"></div>
	<div class="blockMsgbox_con">
		<p>
		<span id="dtContents"> ${message.contents} </span>
		</p>
	</div>	
</div>
		
	