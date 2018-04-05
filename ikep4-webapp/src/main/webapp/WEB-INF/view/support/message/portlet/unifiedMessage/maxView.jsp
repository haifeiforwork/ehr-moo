<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="portletList" value="ui.support.message.portlet.list"/>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
<!--  
(function($) {
	
	getMailDetailView = function(itemUrl) {		
		$jq("#frame_${portletConfigId}").attr("src", itemUrl); 		
	};
	
	popMessageInfo = function(messageId) {
		url = '<c:url value="/support/message/portlet/unifiedMessage/messageView.do?messageId='+messageId+'" />';
		parent.showMainFrameDialog(url, 'message', 400, 300);
	}
	
})(jQuery);
//-->
</script>
<div class="none" id="popLayer" title="Message">
	<div id="popMessageView" > </div>
</div>
<div id="${portletConfigId}" class="tableList_1">
	<table summary="" >
		<caption></caption>
			<col width="50"/>
			<col width="10"/>
			<col style="width:*;"/>
			<col style="width:10%;"/>
			<col width="90"/>
		<tbody>			
			<c:choose>
				<c:when test="${empty messageList}">
					<tr>
						<td colspan="5" class="emptyRecord"><ikep4j:message pre='${portletList}' key='empty' /></td> 
					</tr>				        
			    </c:when>
			    <c:otherwise>
					<c:forEach var="message" items="${messageList}" varStatus="status">
						<tr>
							<td class="textLeft">
								<c:choose>
						      		<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						       			${message.categoryName}
						      		</c:when>
						      		<c:otherwise>
						       			${message.categoryEnglishName}
						      		</c:otherwise>
						     	</c:choose>
							</td>
							<td class="textCenter">
								<c:if test="${message.isUrgent eq 1 }"><img src="<c:url value='/base/images/icon/ic_emer.gif' />" alt="<ikep4j:message pre='${preList}' key='isUrgent' />" /></c:if>
							</td>
							<td class="textLeft">
								<a href="#a" onclick="popMessageInfo('${message.messageId}');">
									<div class="ellipsis" title="${message.contents}">${message.contents}</div>
								</a>
							</td>	
							<td class="ellipsis">								
							 	<c:choose>
						      		<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						       			<a href="#a" onclick="iKEP.showUserContextMenu(this, '${message.senderId}', 'bottom');" title="${message.senderName}">
						       				${message.senderName}
						       			</a>
						      		</c:when>
						      		<c:otherwise>
						      			<a href="#a" onclick="iKEP.showUserContextMenu(this, '${message.senderId}', 'bottom');" title="${message.senderEnglishName}">
						       				${message.senderEnglishName} 
						       			</a>
						      		</c:otherwise>
						     	</c:choose>
							</td>
							<td class="textRight"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${message.sendDate}"/></span></td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose> 
		</tbody>
	</table>	
	
	<div class="dotline_1"></div>
	<div class="l_b_corner"></div><div class="r_b_corner"></div>
</div>
