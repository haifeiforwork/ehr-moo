<%--
=====================================================
* 기능 설명 : Message Sub Menu Page
* 작성자 : 손정환
=====================================================
--%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preMenu"  value="ui.support.message.Menu" /> 
<script type="text/javascript">
<!-- 
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		$jq("#newMessageButton").click(function() {   
			var url = "<c:url value='/support/message/messageNew.do'/>";
			iKEP.popupOpen(url , {width:500, height:500});
		}); 
		iKEP.setLeftMenu();
	});
//-->
</script>
		<h1 class="none"><ikep4j:message pre="${preMenu}" key="sideTitle" /></h1>	
		<h2 class="message_title"><a href="<c:url value='/support/message/messageListView.do'/>">Message</a></h2>	
		<div class="clear"></div>

		<div class="blockButton_2 mb10">
			<a class="button_2" id="newMessageButton" name="newMessageButton" href="#a"><span><img src="<c:url value="/base/images/icon/ic_message.gif"/>" alt="" /><ikep4j:message pre="${preMenu}" key="newMessage" /></span></a>				
		</div>
	
		<div class="left_fixed"> 
			<ul>	
				<li class="liFirst no_child<c:if test="${viewCode eq 'R'}"> licurrent</c:if>"><a href="<c:url value='/support/message/messageListView.do'/>"><ikep4j:message pre="${preMenu}" key="messageListView" /> <span class="colorPoint">${newMessage}</span></a></li>
				<li class="no_child<c:if test="${viewCode eq 'S'}"> licurrent</c:if>"><a href="<c:url value='/support/message/messageSendListView.do'/>"><ikep4j:message pre="${preMenu}" key="messageSendListView" /></a></li>				
				<li class="no_child<c:if test="${viewCode eq 'K'}"> licurrent</c:if>"><a href="<c:url value='/support/message/messageStoreListView.do'/>"><ikep4j:message pre="${preMenu}" key="messageStoreListView" /></a></li>
				<c:if test="${adminYn eq 'Y'}">
					<li class="opened licurrent"><a href="#a">Administration</a>
						<ul>
							<li class="no_child<c:if test="${viewCode eq 'T'}"> licurrent</c:if>"><a href="<c:url value='/support/message/messageAdminSeting.do'/>"><ikep4j:message pre="${preMenu}" key="messageAdminSeting" /></a></li>		
							<li class="no_child<c:if test="${viewCode eq 'V'}"> licurrent</c:if>"><a href="<c:url value='/support/message/messageUserVolumnInfo.do'/>"><ikep4j:message pre="${preMenu}" key="messageUserVolumnInfo" /></a></li>
							<li class="no_child liLast <c:if test="${viewCode eq 'E'}"> licurrent</c:if>"><a href="<c:url value='/support/message/messageSpecialUser.do'/>"><ikep4j:message pre="${preMenu}" key="messageSpecialUser" /></a></li>
						</ul>
					</li>	
				</c:if>
									
			</ul>
		</div>
		<div class="messagebox">
			<div class="messagebox_num">${userMessageAdmin.nowMonth}<ikep4j:message pre="${preMenu}" key="messageUserMonth" />
				<c:if test="${userMessageAdmin.useMonthFilesize > userMessageAdmin.maxMonthFilesize}"><font color="red"></c:if>
				<c:choose>
					<c:when test="${userMessageAdmin.useMonthFilesize >= 1024}"><fmt:formatNumber value='${userMessageAdmin.useMonthFilesize/1024}' pattern="0" />GB</c:when>
					<c:otherwise>${userMessageAdmin.useMonthFilesize}MB</c:otherwise>
				</c:choose>
				<c:if test="${userMessageAdmin.useMonthFilesize > userMessageAdmin.maxMonthFilesize}"></font></c:if>/
				<c:choose>
					<c:when test="${userMessageAdmin.maxMonthFilesize >= 1024}"><fmt:formatNumber value='${userMessageAdmin.maxMonthFilesize/1024}' pattern="0" />GB</c:when>
					<c:otherwise>${userMessageAdmin.maxMonthFilesize}MB</c:otherwise>
				</c:choose>
			</div>
			<div class="messagebox_graph">
				<c:choose>
					<c:when test="${userMessageAdmin.useMonthFilesize > userMessageAdmin.maxMonthFilesize}">
						<div class="messagebox_bar_color" style="width:100%;"></div>
					</c:when>
					<c:otherwise>
						<div class="messagebox_bar_color" style="width:${userMessageAdmin.useMonthFilesize * 100 / userMessageAdmin.maxMonthFilesize}%;"></div><!--그래프는 width로 수치 조절_1기가 중 30메가는 30%-->
					</c:otherwise>
				</c:choose>
			</div>
			<div class="messagebox_num"><ikep4j:message pre="${preMenu}" key="messageUserStore" />
				<c:if test="${userMessageAdmin.useStoredFilesize > userMessageAdmin.maxStoredFilesize}"><font color="red"></c:if>
				<c:choose>
					<c:when test="${userMessageAdmin.useStoredFilesize >= 1024}"><fmt:formatNumber value='${userMessageAdmin.useStoredFilesize/1024}' pattern="0" />GB</c:when>
					<c:otherwise>${userMessageAdmin.useStoredFilesize}MB</c:otherwise>
				</c:choose>
				<c:if test="${userMessageAdmin.useStoredFilesize > userMessageAdmin.maxStoredFilesize}"></font></c:if>/
				<c:choose>
					<c:when test="${userMessageAdmin.maxStoredFilesize >= 1024}"><fmt:formatNumber value='${userMessageAdmin.maxStoredFilesize/1024}' pattern="0" />GB</c:when>
					<c:otherwise>${userMessageAdmin.maxStoredFilesize}MB</c:otherwise>
				</c:choose>
			</div>
			<div class="messagebox_graph">
				<c:choose>
					<c:when test="${userMessageAdmin.useStoredFilesize > userMessageAdmin.maxStoredFilesize}">
						<div class="messagebox_bar_color" style="width:100%;"></div>
					</c:when>
					<c:otherwise>
						<div class="messagebox_bar_color" style="width:${userMessageAdmin.useStoredFilesize * 100 / userMessageAdmin.maxStoredFilesize}%;"></div><!--그래프는 width로 수치 조절_1기가 중 30메가는 30%-->
					</c:otherwise>
				</c:choose>
			</div>
			<div class="messagebox_ins">
				<ul>
					<li><ikep4j:message pre="${preMenu}" key="maxAttachFilesize" />
						<c:choose>
							<c:when test="${userMessageAdmin.maxAttachFilesize >= 1024}"><fmt:formatNumber value='${userMessageAdmin.maxAttachFilesize/1024}' pattern="0" />GB</c:when>
							<c:otherwise>${userMessageAdmin.maxAttachFilesize}MB</c:otherwise>
						</c:choose>
					</li>
<%--					
 					<li><ikep4j:message pre="${preMenu}" key="maxMonthFilesize" />
						<c:choose>
							<c:when test="${userMessageAdmin.maxMonthFilesize >= 1024}"><fmt:formatNumber value='${userMessageAdmin.maxMonthFilesize/1024}' pattern="0" />GB</c:when>
							<c:otherwise>${userMessageAdmin.maxMonthFilesize}MB</c:otherwise>
						</c:choose>
					</li>
					<li><ikep4j:message pre="${preMenu}" key="maxStoredFilesize" />
						<c:choose>
							<c:when test="${userMessageAdmin.maxStoredFilesize >= 1024}"><fmt:formatNumber value='${userMessageAdmin.maxStoredFilesize/1024}' pattern="0" />GB</c:when>
							<c:otherwise>${userMessageAdmin.maxStoredFilesize}MB</c:otherwise>
						</c:choose>
					</li>
--%>					
				</ul>
			</div>
		</div>
