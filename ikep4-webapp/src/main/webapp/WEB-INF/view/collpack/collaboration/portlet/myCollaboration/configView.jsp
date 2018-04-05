<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="prefix" value="message.portal.portlet.myCollaboration.configView"/>
<c:set var="portletList" value="ui.portal.portlet.myCollaboration.configView"/>


<script type="text/javascript">
//<![CDATA[  
(function($) {
	

	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		$jq("#save_${portletConfigId}").click(function() {
			
			$jq.ajax({
				url : "<c:url value='/collpack/collaboration/portlet/myCollaboration/saveMyCollaboration.do'/>",
				data : $jq("#mainForm${portletConfigId}").serialize(),
				type : "post",
				success : function(result) {
					//alert("<ikep4j:message pre="${prefix}" key="alert.saveSuccess" />");
					
					var reloadUrl = '<c:url value="/collpack/collaboration/portlet/myCollaboration/normalView.do"/>?portletId=${portletId}';
					var listSize = $jq("#listCount_${portletConfigId}").val();
					//portletConfigId, 프로퍼티네임(최대 10Byte), 프로퍼티 값(최대 50Byte),설정 후 이동할 url : 없으면 이동하지 않음.
					PortletSimple.setListCount('${portletConfigId}', listSize, reloadUrl, '${portletId}');
				},
				error : function(event, request, settings) { 
					alert("<ikep4j:message pre='${prefix}' key='alert.saveFail' />");
				}
			});
		});
				
	});
	
})(jQuery);
//]]>
</script>


<div id="${portletConfigId}" class="tableList_1">
<form id="mainForm${portletConfigId}" name="mainForm${portletConfigId}" method="post">

	<div class="poEdit">
		<div>
		<ikep4j:message pre='${portletList}' key='list.count' /> :
		<select title="리스트 갯수" id="listCount_${portletConfigId}">
			<option value="3" <c:if test="${listSize eq '3'}">selected="selected"</c:if>><ikep4j:message pre='${portletList}' key='list.count3' /></option>
			<option value="5" <c:if test="${listSize eq '5'}">selected="selected"</c:if>><ikep4j:message pre='${portletList}' key='list.count5' /></option>
			<option value="7" <c:if test="${listSize eq '7'}">selected="selected"</c:if>><ikep4j:message pre='${portletList}' key='list.count7' /></option>
			<option value="10" <c:if test="${listSize eq '10'}">selected="selected"</c:if>><ikep4j:message pre='${portletList}' key='list.count10' /></option>									
		</select>
		
		</div>

	<div>
		<ul>
			<c:forEach var="workspace" items="${myWorkspaceList}" varStatus="status">

			<c:choose>
			<c:when test="${status.index==0}">
			<li>목록 : <input <c:if test="${!empty workspace.selWorkspaceId}">checked="checked"</c:if> type="checkbox" name="workspaceIds" value="${workspace.workspaceId}" title="목록" class="checkbox"> <span>${workspace.workspaceName}</span></li>
			</c:when>
			<c:otherwise>
			<li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><input <c:if test="${!empty workspace.selWorkspaceId}">checked="checked"</c:if> type="checkbox" name="workspaceIds" value="${workspace.workspaceId}" title="목록" class="checkbox"> <span>${workspace.workspaceName}</span></li>
			</c:otherwise>
			</c:choose>

			</c:forEach>
		</ul>
		
	</div>		
		<div class="textRight">
			<a id="save_${portletConfigId}" class="button" href="#a"><span><ikep4j:message pre='${portletList}' key='button.save' /></span></a></div>
	</div>
</form>
</div>


							
							