<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%>
<c:set var="prefix" value="message.portal.portlet.rss.normalView"/>
<c:set var="portletList" value="ui.portal.portlet.wiseSaying.popupPortletWiseSayingList"/>

<script type="text/javascript">
<!--
(function($) {
	
	showDatail_${portletConfigId} = function(url, itemId, subId, title) {
		//url = iKEP.getContextRoot() + url;
		//iKEP.popupOpen(url, {width:800, height:600});
		//iKEP.debug("iKEP.getContextRoot()="+iKEP.getContextRoot());
		url = iKEP.getContextRoot() + url + itemId;
		var width = $(window).width()*0.8;
		var height = $(window).height()*0.8;
		
		var options = {
			windowTitle : title,
			documentTitle : title,
			width:600, height:500, modal:true
		};
		
		iKEP.portletPopupOpen(url, options);

	};
	
})(jQuery);  
		 
//-->
</script>

<div id="${portletConfigId}" class="tableList_1">	
	<table summary="" >
		<caption></caption>
		<tbody>			
			<c:choose>
			    <c:when test="${empty searchResult.entity}">
					<tr>
						<td colspan="3" class="emptyRecord"><ikep4j:message pre='${portletList}' key='list.empty' /></td> 
					</tr>				        
			    </c:when>
			    <c:otherwise>
					<c:forEach var="comment" items="${searchResult.entity}">
						<tr>
							<th width="*" scope="row">
								<div class="ellipsis"><a href="#a" onclick="showDatail_${portletConfigId}('${comment.targetUrl}','${comment.targetId}','${comment.subId}','${comment.title}')">
								${fn:replace(comment.contents,'<br>',' ')}</a></div>
							</td>
							<td width="55"><div class="ellipsis"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${comment.linereplyRegisterId}', 'bottom')" >${comment.linereplyRegisterName}</a></div></td>
							<td width="30" class="textRight"><span class="date"><ikep4j:timezone pattern="MM.dd" date="${comment.linereplyRegistDate}"/></span></td>
						</tr>
					</c:forEach>
				</c:otherwise>	
			</c:choose>
		</tbody>
	</table>
</div>