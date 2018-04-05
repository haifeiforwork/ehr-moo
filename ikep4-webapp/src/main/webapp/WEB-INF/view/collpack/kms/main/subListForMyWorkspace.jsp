<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.collpack.collaboration.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
					

							<c:forEach var="myWorkspaceList" items="${myWorkspaceList}"  varStatus="status">
								<c:choose>
								<c:when test="${myWorkspaceList.typeName=='Team'}">
								<c:set var="class" value="cate_tit_fix_1"/>				
								</c:when>
								<c:when test="${myWorkspaceList.typeName=='TFT'}">
								<c:set var="class" value="cate_tit_fix_2"/>				
								</c:when>
								<c:when test="${myWorkspaceList.typeName=='Cop'}">
								<c:set var="class" value="cate_tit_fix_3"/>				
								</c:when>
								<c:otherwise>
								<c:set var="class" value="cate_tit_fix_4"/>				
								</c:otherwise>
								</c:choose>				
								
								<c:if test="${myWorkspaceList.isDefault=='1' }">
									<li class="selected">
								</c:if>
								<c:if test="${myWorkspaceList.isDefault!='1' }">
									<li>
								</c:if>	
											
								<span class="${class}">${myWorkspaceList.typeName}</span><a href="<c:url value="/collpack/collaboration/main/Workspace.do"/>?workspaceId=${myWorkspaceList.workspaceId}" title="${myWorkspaceList.workspaceName}"><span class="collName_my">${myWorkspaceList.workspaceName}</span></a>
								<a onclick="defaultSetting('${myWorkspaceList.workspaceId}')" href="#a" title="<ikep4j:message pre="${prefix}" key="detail.myCollaboration.defaultSetting" />"><span class="arrow"><ikep4j:message pre="${prefix}" key="detail.myCollaboration.default" /></span></a></li>
							</c:forEach>
							
						
					
					
					
<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	$jq(document).ready(function() {
	
	});
	
	
})(jQuery);  

//-->
</script>
					