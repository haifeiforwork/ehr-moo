<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preAlert" value="message.portal.admin.screen.theme.readThemeForm.alert"/>
<c:set var="preForm" value="ui.portal.admin.screen.theme.readThemeForm.form"/>
<c:set var="preButton" value="ui.portal.admin.screen.theme.readThemeForm.button"/>

<script type="text/javascript">
//<![CDATA[
(function($) {
	
	$jq(document).ready(function() {
		
		//left menu setting
		$jq("#themeLinkOfLeft").click();
		
		iKEP.showGallery($jq("a.image-gallery"));
		
	});
	
	modifyPortalTheme = function() {
		location.href="<c:url value='updateThemeForm.do?themeId='/><c:out value='${portalTheme.themeId}'/>";   							
	};
	
	removePortalTheme = function() {
		if("${portalTheme.defaultOption}"==1){
			alert("<ikep4j:message pre='${preAlert}' key='deleteDefaultOption' />");
			return;
		}
		if(confirm("<ikep4j:message pre='${preAlert}' key='deleteConfirm' />")){
			var mainForm = document.mainForm;
			
			mainForm.action = "<c:url value='/portal/admin/screen/theme/removeTheme.do'/>";
			
			mainForm.submit();
		}
	};
	
	list = function() {
		location.href="<c:url value='listTheme.do'/>";	  
	};

})(jQuery);
//]]>
</script>
	
<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preForm}" key="title" /></h2>
</div>
<!--//pageTitle End-->

<!--blockDetail Start-->
<div class="blockDetail">
	<form id="mainForm" name="mainForm" method="post" action="" onsubmit="return false;">
	<input type="hidden" id="themeId" name="themeId" value="${portalTheme.themeId}"/>
	
		<table summary="<ikep4j:message pre='${preList}' key='title' />">
			<caption></caption>
			<colgroup>
				<col width="18%"/>
				<col width="82%"/>
			</colgroup>
			<tbody>
				<!--themeName Start-->
				<tr>
					<th scope="row">
						<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="themeName" />
					</th>
					<td>
						<input id="themeName" name="themeName" type="text" class="inputbox w100" title="<ikep4j:message pre='${preForm}' key='themeName' />" value="${portalTheme.themeName}" readonly="readonly" />
					</td>
				</tr>
				<!--themeName End-->
				
				<!--description Start-->
				<tr>
					<th scope="row">
						<ikep4j:message pre="${preForm}" key="description" />
					</th>
					<td>
						<input id="description" name="description" type="text" class="inputbox w100" title="<ikep4j:message pre='${preForm}' key='description' />" value="${portalTheme.description}" readonly="readonly" />
					</td>
				</tr>
				<!--description End-->
				
				<!--cssPath Start-->
				<tr>
					<th scope="row">
						<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="cssPath" />
					</th>
					<td>
						<input id="cssPath" name="cssPath" type="text" class="inputbox w100" title="<ikep4j:message pre='${preForm}' key='cssPath' />" value="${portalTheme.cssPath}" readonly="readonly" />
					</td>
				</tr>
				<!--cssPath End-->
				
				<!--previewImageId Start-->
				<tr>
					<th scope="row">
						<ikep4j:message pre="${preForm}" key="preImage" />
					</th>
					<td>
						<!-- input id="previewImageId" name="previewImageId" type="text" class="inputbox" title="<ikep4j:message pre='${preForm}' key='preImage' />" value="${portalTheme.previewImageId}" readonly="readonly" /-->
						<c:if test="${!empty portalTheme.fileData}">
						<a class="image-gallery" href="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${portalTheme.fileData.fileId}" title="${portalTheme.fileData.fileRealName}">
							<c:out value="${portalTheme.fileData.fileRealName}"/>
						</a>
						</c:if>
					</td>
				</tr>
				<!--previewImageId End-->
				
				<!--defaultOption Start-->
				<tr>
					<th scope="row">
						<ikep4j:message pre="${preForm}" key="default" />
					</th>
					<td>
						<input type="radio" id="optionYes" name="defaultOption" class="radio" title="<ikep4j:message pre='${preForm}' key='yes' />" value="1" <c:if test="${portalTheme.defaultOption == '1'}">checked="checked"</c:if> disabled="disabled" />
						<label for="optionYes"><ikep4j:message pre="${preForm}" key="yes" /></label>&nbsp;
						<input type="radio" id="optionNo" name="defaultOption" class="radio" title="<ikep4j:message pre='${preForm}' key='no' />" value="0" <c:if test="${portalTheme.defaultOption == '0'}">checked="checked"</c:if> disabled="disabled" />
						<label for="optionNo"><ikep4j:message pre="${preForm}" key="no" /></label>
					</td>
				</tr>
				<!--defaultOption End-->
			</tbody>
		</table>
	
	</form>
	
	<div class="blockBlank_10px"></div>
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a class="button" href="#" onclick="modifyPortalTheme(); return false;"><span><ikep4j:message pre="${preButton}" key="update" /></span></a></li>
			<li><a class="button" href="#" onclick="removePortalTheme(); return false;"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
			<li><a class="button" href="#" onclick="list(); return false;"><span><ikep4j:message pre="${preButton}" key="list" /></span></a></li>
		</ul>
	</div>	
	<!--//blockButton End-->
</div>