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
<c:set var="preButton"  value="ui.servicepack.seamless.button" /> 
<c:set var="preDetail"  value="ui.servicepack.seamless.messageView.detail" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<!--popup Start-->

<div class="none" id="receivePop_${messageBox.messageId}" title="recipient list">
	<div class="blockListTable msgTable">
		<table>   
			<caption></caption>
			<col style="width: 10%;"/>
			<col style="width: 40%;"/>
			<col style="width: *;"/>
			<tbody id="innerTbl">
				<c:forEach var="recipientList" items="${messageBox.recipientList}">
					<tr>
						<td>${recipientList.receiveType}</td>
						<td>${recipientList.receiverName}</td>
						<td>${recipientList.receiverMail}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<!--popup End-->

<div class="blockMsgbox_2"> 
	<h3 class="none">message view</h3>
		<div class="blockMsgbox_img">
			<c:choose>
				<c:when test="${boxType eq 'R'}">
					<c:choose>
						<c:when test="${messageBox.messageType eq 'Mail'}">
							<img src="<c:url value='/base/images/icon/ic_mail_b.png'/>" alt="mail" />
						</c:when>
						<c:otherwise>
							<img src="<c:url value='${messageBox.profilePicturePath}' />" width="50" height="50" alt="userImg" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:when test="${boxType eq 'S'}">
						<c:choose>
							<c:when test="${messageBox.messageType eq 'Mail'}">
								<span class="icbox">
									<img src="<c:url value='/base/images/icon/ic_mail_b.png'/>" alt="mail" />
								</span>
							</c:when>
							<c:when test="${messageBox.messageType eq 'Message'}">
								<img src="<c:url value='${messageBox.profilePicturePath}' />" width="50" height="50" alt="userImg" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />
							</c:when>
							<c:when test="${messageBox.messageType eq 'SMS'}">
								<span class="icbox">
									<img src="<c:url value='/base/images/icon/ic_sns_b.png'/>" alt="SMS" />
								</span>
							</c:when>
						</c:choose>
				</c:when>	
			</c:choose>										
		</div>
		<div class="blockMsgbox_info">
			<c:if test="${boxType eq 'R'}">
				<c:choose>
					<c:when test="${messageBox.messageType eq 'Mail' && messageBox.senderJobTitleName == null}">
						<span id="dtSendName" class="blockMsgbox_info_name"><a href="#a"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${messageBox.senderName}</c:when><c:otherwise>${messageBox.senderEngName}</c:otherwise></c:choose> 
						<c:if test="${messageBox.senderName != messageBox.senderMail}"> (${messageBox.senderMail})</c:if> </a></span>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}">
								<span id="dtSendName" class="blockMsgbox_info_name"><a href="#a"> ${messageBox.senderName} ${messageBox.senderJobTitleName} </a></span>
								<span id="dtSendTeamName" class="blockMsgbox_info_team">${messageBox.senderTeamName}</span>
							</c:when>
							<c:otherwise>
								<span id="dtSendName" class="blockMsgbox_info_name"><a href="#a"> ${messageBox.senderEngName} ${messageBox.senderJobTitleEngName} </a></span>
								<span id="dtSendTeamName" class="blockMsgbox_info_team">${messageBox.senderTeamEngName}</span>
							</c:otherwise>
					    </c:choose>
					</c:otherwise>
				</c:choose>
			</c:if>
			<div>
				<span class="blockMsgbox_info_t"><ikep4j:message pre='${preDetail}' key='sendDate'/></span>
				<span id="dtSendDate" class="blockMsgbox_info_c"> <ikep4j:timezone date="${messageBox.sendDate}" pattern="yyyy.MM.dd HH:mm:ss"/> </span>
			</div>
			<div>
				<span class="blockMsgbox_info_t"><ikep4j:message pre='${preDetail}' key='receiver'/></span>
				<span id="dtReceiverList" class="blockMsgbox_info_c">
					<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}">
							${messageBox.receiverName}
						</c:when>
						<c:otherwise>
							${messageBox.receiverEngName}
						</c:otherwise>
				    </c:choose>
				<c:if test="${messageBox.receiverName eq ''}">${messageBox.receiverMail}</c:if>
				<c:if test="${messageBox.receiverCount > 1}"> <ikep4j:message pre='${preDetail}' key='overPer'/>  ${messageBox.receiverCount -1}<ikep4j:message pre='${preDetail}' key='person'/>
						<a href="#a" onclick="recipientPop('${messageBox.messageId}','${messageBox.messageType}','${boxType}');"><img src="<c:url value='/base/images/common/btn_recipient.gif' />" alt="recipient list" /></a>
				</c:if>
				</span>
			</div>
		</div>
	<c:if test="${messageBox.messageType eq 'Message' && messageBox.fileDataList != null && messageBox.fileDataList != '[]' }">
	<div class="filedown_ic"><ikep4j:message pre='${preDetail}' key='attachFile'/> <span class="colorPoint">(${messageBox.attachCount})</span> <a href="#a"><img src="<c:url value='/base/images/icon/ic_ar_down.gif' />" alt="" /></a></div>
	<div class="filedown">
		<ul>
			<c:forEach var="fileData" items="${messageBox.fileDataList}">
				<li><a href='<c:url value="/support/fileupload/downloadFile.do?fileId=${fileData.fileId}&amp;thumbnailYn=N" />' >${fileData.fileRealName}</a></li>
			</c:forEach> 
		</ul>
	</div>
	</c:if>
	<c:if test="${messageBox.messageType eq 'Mail' && messageBox.attachFileList != null && messageBox.attachFileList != '[]' }">
	<div class="filedown_ic"><ikep4j:message pre='${preDetail}' key='attachFile'/> <span class="colorPoint">(${messageBox.attachCount})</span> <a href="#a"><img src="<c:url value='/base/images/icon/ic_ar_down.gif' />" alt="" /></a></div>
	<div class="filedown">
		<ul>
			<c:forEach var="attach" items="${messageBox.attachFileList}">
				<li><a href="<c:url value="/servicepack/seamless/downloadMailAttach.do"/>?boxType=${boxType}&messageId=${messageBox.messageId}&multipartPath=${attach.multipartPath}">${attach.attachName}</a></li>
			</c:forEach>
		</ul>
	</div>
	</c:if>
	<div class="blockMsgbox_con">
		<div id="dtTitle" class="blockMsgbox_con_title"> ${messageBox.title} </div>
		<p>
		<span id="dtContents"> ${messageBox.contents} </span>
		</p>
	</div>							
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a class="button" href="#a" onclick="reSendMessage('${messageBox.messageId}','${messageBox.messageType}','${boxType}');"><span><ikep4j:message pre='${preButton}' key='reSend'/></span></a></li>
			<li><a class="button" href="#a" onclick="replyMessage('${messageBox.messageId}','${messageBox.messageType}','${boxType}');"><span>
				<c:choose>
					<c:when test="${boxType eq 'R'}"><ikep4j:message pre='${preButton}' key='reply'/></c:when>
					<c:otherwise><ikep4j:message pre='${preButton}' key='replySend'/></c:otherwise>
				</c:choose>
				
			</span></a></li>
			<li><a class="button" href="#a" onclick="delMessage('${messageBox.messageId}','${messageBox.messageType}','${boxType}');"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->	
</div>

