<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="portletList" value="ui.support.message.portlet.list"/>
<c:set var="preButton" value="ui.support.message.button"/>
<c:set var="preMsg"  value="ui.support.message.MSG" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<script type="text/javascript">
<!--   

(function($) {
	
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 

		$jq("#saveBt_${portletConfigId}").click(function() {
			var reloadUrl = '<c:url value="/support/message/portlet/unifiedMessage/normalView.do"/>?portletId=${portletId}&portletConfigId=${portletConfigId}';
			var listSize = $jq("#listCount").val();
			$jq.ajax({
				url : '<c:url value="/support/message/portlet/unifiedMessage/updateListCount.do"/>',
				data : {count:listSize},
				type : "post",
				dataType : "html",
				success : function(result) {
					alert("<ikep4j:message pre='${preMsg}' key='saveOk' />");
					var container = $jq("#portletDiv").parents("div.portletUnit_c");
					container.children("div.config").empty().hide();
					container.children("div.content").load(reloadUrl,function(){
						container.trigger("reset:containerHeight");
					}).error(function(event, request, settings) { 
						if(errorMessage){
							alert(errorMessage); 
						}
						else{
							alert("error"); 
						}
					}).show();
				},
				error : function() {alert('error');}
			}); 
		}); 
		
	});
	popMessageInfo = function(messageId) {
		url = '<c:url value="/support/message/portlet/unifiedMessage/messageView.do?messageId='+messageId+'" />';
		parent.showMainFrameDialog(url, 'message', 400, 300);
	};
	
})(jQuery);  

//-->
</script>

<div id="${portletConfigId}">
<div class="none" id="popLayer" title="Message">
	<div id="popMessageView" > </div>
</div>

<div id="portletDiv" class="tableList_1">

	<div class="poEdit">
		<div>
		<ikep4j:message pre='${portletList}' key='Count' /> :
		<select title="<ikep4j:message pre='${portletList}' key='Count' />" id="listCount">
			<option value="10" <c:if test="${listSize eq '10'}">selected="selected"</c:if>><ikep4j:message pre='${portletList}' key='Count10' /></option>
			<option value="5" <c:if test="${listSize eq '5'}">selected="selected"</c:if>><ikep4j:message pre='${portletList}' key='Count5' /></option>
			<option value="3" <c:if test="${listSize eq '3'}">selected="selected"</c:if>><ikep4j:message pre='${portletList}' key='Count3' /></option>
		</select>
		
		</div>
		
		<div class="textRight">
			<a id="saveBt_${portletConfigId}" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save' /></span></a></div>
	</div>
	
	
		<table summary="">
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
								<td class="textCenter"  width="55">
									<div class="ellipsis">
									<a href="#a" onclick="iKEP.showUserContextMenu(this, '${message.senderId}', 'bottom');">
									<c:choose>
								      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
								       ${message.senderName}
								      </c:when>
								      <c:otherwise>
								       ${message.senderEnglishName} 
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
		
	
	
</div>
</div>

							
							