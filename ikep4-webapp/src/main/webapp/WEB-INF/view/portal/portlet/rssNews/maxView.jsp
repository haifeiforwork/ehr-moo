<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%>
<c:set var="prefix" value="message.portal.portlet.rss"/>
<c:set var="portletList" value="ui.portal.portlet.wiseSaying.popupPortletWiseSayingList"/>

<script type="text/javascript">
<!--   
(function($) {
	
	getChannelItemView = function(itemUrl) {	
		
		window.open(itemUrl, "", "width=800,height=500,status=no,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=yes");
		
		//alert(itemUrl);
		//$("#frame_${portletConfigId}").ajaxLoadStart();
		//$jq("#frame_${portletConfigId}").attr("src", itemUrl).bind("load", function(){ $("#frame_${portletConfigId}").ajaxLoadComplete(); });
	};
	
	$(document).ready(function(event) {
		var portletManager = PortletManager.getInstance();
		var thisPortlet = portletManager.getPortletByConfigId(${portletConfigId});
		
		thisPortlet.box.container.unbind("click:reload")
			.bind("click:reload", function(event) {	// reload
				if(thisPortlet.options.reload && thisPortlet.options.reload.callback) {
					thisPortlet.options.reload.callback();
				} else {
					$.post(iKEP.getContextRoot() + "/support/rss/channel/readChannel.do", {channelId:""})
						.complete(function(xhr, status) {
							if(status != "success") alert("<ikep4j:message pre='${prefix}' key='reload.updateError' />");
							else thisPortlet.loadContent();
						});
				}
			});
	});
	
})(jQuery);
//-->
</script>

<div id="${portletConfigId}" class="tableList_1">	
	<table summary="" >
		<caption></caption>
		<tbody>
			<c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<td colspan="3" class="emptyRecord"><ikep4j:message pre='${portletList}' key='list.empty' /></td> 
					</tr>				        
			    </c:when>
			    <c:otherwise>
					<c:forEach var="channelItem" items="${searchResult.entity}">
						<tr>
							<th width="*" scope="row">
								<div class="ellipsis"><a href="#a" onclick="getChannelItemView('${channelItem.itemUrl}')">
								${channelItem.itemTitle}</a></div>
							</th>	
							<td width="90"><div class="ellipsis">${channelItem.itemRegister}</div></td>
							<td width="90" class="textRight"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd hh:mm" date="${channelItem.itemPublishDate}"/></span></td>
						</tr>
					</c:forEach>	
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>	
	
	<!--
	<div class="dotline_1"></div>	
	<div class="blockMsgbox" style="margin-bottom:0px;"> 
		<div class="msgbox_frame"></div>		
		<iframe id="frame_${portletConfigId}" name="frame_${portletConfigId}" width="100%" height="600px" scrolling="yes" frameborder="0">		
		</iframe>				
	</div>
	
	  -->
	<div class="l_b_corner"></div><div class="r_b_corner"></div>	
</div>