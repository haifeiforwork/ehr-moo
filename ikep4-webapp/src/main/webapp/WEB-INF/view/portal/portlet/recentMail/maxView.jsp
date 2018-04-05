<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 
<c:set var="prefix" value="message.portal.portlet.rss.normalView"/>
<c:set var="portletList" value="ui.portal.portlet.wiseSaying.popupPortletWiseSayingList"/>

<script type="text/javascript">
<!-- 
(function($) {
	
	getMailDetailView = function(itemUrl) {		
		$("#frame_${portletConfigId}").ajaxLoadStart();
		$jq("#frame_${portletConfigId}").attr("src", itemUrl).bind("load", function(){ $("#frame_${portletConfigId}").ajaxLoadComplete(); }); 		
	};	
	
})(jQuery);  
//-->
</script>

<div id="${portletConfigId}" class="tableList_1">	
	<table summary="" >
		<caption></caption>
		<tbody>
			<c:choose>
				<c:when test="${empty mailList}">
					<tr>
						<td colspan="3" class="emptyRecord"><ikep4j:message pre='${portletList}' key='list.empty' /></td> 
					</tr>				        
			    </c:when>
			    <c:otherwise>
					<c:forEach var="mail" items="${mailList}" varStatus="status">
						<c:if test="${status.count <= listSize}">
						<tr>
							<th width="*" scope="row">
								<div class="ellipsis"><a href="#a" onclick="getMailDetailView('<c:url value='/support/mail/viewMail.do'/>?mailId=${mail.mailId}')">
								${mail.title}</a></div>
							</th>	
							<td width="80"><div class="ellipsis">${mail.fromName}</div></td>
							<td width="90" class="textRight"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd hh:mm" date="${mail.sendDate}"/></span></td>
						</tr>
						</c:if>
					</c:forEach>	
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	
	<div class="dotline_1"></div>	
	<div class="blockMsgbox" style="margin-bottom:0px;"> 
		<div class="msgbox_frame"></div>		
		<iframe id="frame_${portletConfigId}" name="frame_${portletConfigId}" width="100%" height="600px" scrolling="yes" frameborder="0">
		</iframe>				
	</div>
	<div class="l_b_corner"></div><div class="r_b_corner"></div>
</div>
