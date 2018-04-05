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
	<table summary="Notification List" >
		<caption></caption>
		<tbody>		
			<c:choose>
			    <c:when test="${empty messageList}">
					<tr>
						<td colspan="3" class="emptyRecord"><ikep4j:message pre='${portletList}' key='empty' /></td> 
					</tr>				        
			    </c:when>
			    <c:otherwise>
					<c:forEach var="message" items="${messageList}" varStatus="status">
						<tr>
							<td class="textLeft">
								<a href="#a" onclick="popMessageInfo('${message.messageId}');">
								<div class="ellipsis" title="${message.contents}">${message.contents}</div></a>
							</td>	
							<td width="55">
								<div class="ellipsis">
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
							    	 </a>
								</div>
							</td>
							<td class="textRight" width="30"><span class="date"><ikep4j:timezone pattern="MM.dd" date="${message.sendDate}"/></span></td>
						</tr>
					</c:forEach>
				</c:otherwise>	
			</c:choose>	
		</tbody>
	</table>
	
	<div class="none" id="popLayer" title="Message">
		<div id="popMessageView" > </div>
	</div>
</div>