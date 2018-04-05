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
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 

		$jq("#save_${portletConfigId}").click(function() {
			var reloadUrl = '<c:url value="/portal/portlet/recentComment/normalView.do"/>?portletId=${portletId}';
			var listSize = $jq("#listCount_${portletConfigId}").val();
			//portletConfigId, 프로퍼티네임(최대 10Byte), 프로퍼티 값(최대 50Byte),설정 후 이동할 url : 없으면 이동하지 않음.
			PortletSimple.setListCount('${portletConfigId}', listSize, reloadUrl, '${portletId}');
		}); 
		
	});
	
})(jQuery);  
//-->
</script>


<div id="${portletConfigId}" class="tableList_1">

	<div class="poEdit">
		<div>
		<ikep4j:message pre='${portletList}' key='list.Count' /> :
		<select title="<ikep4j:message pre='${portletList}' key='Count' />" id="listCount_${portletConfigId}">
			<option value="10" <c:if test="${listSize eq '10'}">selected="selected"</c:if>><ikep4j:message pre='${portletList}' key='list.Count10' /></option>
			<option value="5" <c:if test="${listSize eq '5'}">selected="selected"</c:if>><ikep4j:message pre='${portletList}' key='list.Count5' /></option>
			<option value="3" <c:if test="${listSize eq '3'}">selected="selected"</c:if>><ikep4j:message pre='${portletList}' key='list.Count3' /></option>
		</select>		
		</div>		
		<div class="textRight">
			<a id="save_${portletConfigId}" class="button_s" href="#a"><span><ikep4j:message pre='${portletList}' key='button.create' /></span></a></div>
	    </div>
	
	
		<table summary="">
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
								<td width="55" class="textCenter"><div class="ellipsis"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${comment.linereplyRegisterId}', 'bottom')">${comment.linereplyRegisterName}</a></div></td>
								<td width="30" class="textRight"><span class="date"><ikep4j:timezone pattern="MM.dd" date="${comment.linereplyRegistDate}"/></span></td>
							</tr>
						</c:forEach>
					</c:otherwise>			
				</c:choose>	      
		
			</tbody>
		</table>
		
	
	
</div>


							
							