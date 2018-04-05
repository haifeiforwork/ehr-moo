<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="preAlert" value="message.portal.admin.screen.portal.readPortal"/>
<c:set var="prefix" value="ui.portal.admin.screen.portal.readPortal"/>

<script type="text/javascript">
//<![CDATA[
(function($) {
	
    $jq(document).ready(function() {
    	
    	//left menu setting
		$jq("#portalLinkOfLeft").click();
    	
    	$jq("#updateButton").click(function() {
			location.href = "<c:url value='/portal/admin/screen/portal/updatePortalForm.do?portalId=${portal.portalId}'/>";
		});
    	
    	$jq("#removeButton").click(function() {
			if(confirm("<ikep4j:message pre='${prefix}' key='confirm.remove'/>")) {
				$jq("#readPortalForm")[0].submit();
			}
		});
    	
    });
	
})(jQuery);
//]]>
</script>
<form id="readPortalForm" method="post" action="<c:url value='/portal/admin/screen/portal/removePortal.do'/>" onsubmit="return false;">
<input type="hidden" name="portalId" value="${portal.portalId}"/>
</form>
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre='${prefix}' key='pageTitle'/></h2>
		<!--div id="pageLocation">
			<ul>
				<li class="liFirst"><ikep4j:message pre='${prefix}' key='home'/></li>
				<li><ikep4j:message pre='${prefix}' key='1depth'/></li>
				<li><ikep4j:message pre='${prefix}' key='2depth'/></li>
				<li class="liLast"><ikep4j:message pre='${prefix}' key='lastDepth'/></li>
			</ul>
		</div-->
	</div>
	<!--//pageTitle End-->
	
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${prefix}' key='pageTitle'/>">
			<caption></caption>
			<colgroup>
				<col width="18%"/>
				<col width="66%"/>
				<col width="16%"/>
			</colgroup>
			<tbody>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='portalName'/></th>
					<td colspan="2">
						${portal.portalName}						
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='description'/></th>
					<td colspan="2">
						${portal.description}
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='portalHost'/></th>
					<td colspan="2">
						${portal.portalHost}							
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='portalHostAlias'/></th>
					<td colspan="2">
						${portal.portalHostAlias}							
					</td>
				</tr>
				<tr style="display:none">
					<th scope="row"><ikep4j:message pre='${prefix}' key='portalPath'/></th>
					<td colspan="2">
						${portal.portalPath}							
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='defaultOption'/></th>
					<td colspan="2">
						<c:choose>
							<c:when test="${portal.defaultOption == 1}">
								<ikep4j:message pre='${prefix}' key='defaultPortal'/>
							</c:when>
							<c:otherwise>
								<ikep4j:message pre='${prefix}' key='noDefaultPortal'/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='isUse'/></th>
					<td colspan="2">
						<c:choose>
							<c:when test="${portal.active == 1}">
								<ikep4j:message pre='${prefix}' key='use'/>
							</c:when>
							<c:otherwise>
								<ikep4j:message pre='${prefix}' key='noUse'/>
							</c:otherwise>
						</c:choose>		
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='cacheYn'/></th>
					<td colspan="2">
						<c:choose>
							<c:when test="${portal.cacheYn == 'Y'}">
								<ikep4j:message pre='${prefix}' key='use'/>
							</c:when>
							<c:otherwise>
								<ikep4j:message pre='${prefix}' key='noUse'/>
							</c:otherwise>
						</c:choose>									
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='defaultLocale'/></th>
					<td colspan="2">
						${portal.defaultLocaleName}					
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='manager'/></th>
					<td colspan="2">
						<c:if test="${!empty listAdminUser.assignUserDetailList}">
						<c:forEach var="admin" items="${listAdminUser.assignUserDetailList}" varStatus="loopStatus">
						<c:choose>
						<c:when test="${!loopStatus.first}">
						,${admin.userId}
						</c:when>
						<c:otherwise>
						${admin.userId}
						</c:otherwise>
						</c:choose>
						</c:forEach>
						</c:if>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<!--//blockDetail End-->
	
	<!--tableBottom Start-->
	<div class="tableBottom">										
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a id="updateButton" class="button" href="#a"><span><ikep4j:message pre='${prefix}' key='button.modify'/></span></a></li>
				<li><a id="removeButton" class="button" href="#a"><span><ikep4j:message pre='${prefix}' key='button.remove'/></span></a></li>
				<li><a class="button" href="<c:url value='/portal/admin/screen/portal/listPortal.do'/>"><span><ikep4j:message pre='${prefix}' key='button.list'/></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
