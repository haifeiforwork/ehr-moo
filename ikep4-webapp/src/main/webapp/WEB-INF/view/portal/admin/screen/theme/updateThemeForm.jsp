<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preAlert" value="message.portal.admin.screen.theme.updateThemeForm.alert"/>
<c:set var="preForm" value="ui.portal.admin.screen.theme.updateThemeForm.form"/>
<c:set var="preButton" value="ui.portal.admin.screen.theme.updateThemeForm.button"/>

<script type="text/javascript">
//<![CDATA[
(function($) {
	
	$jq(document).ready(function() {
		
		//left menu setting
		$jq("#themeLinkOfLeft").click();
		
		$jq("#mainForm:input:visible:enabled:first").focus();
		
		iKEP.showGallery($jq("a.image-gallery"));
		
		$jq("#fileuploadBtn").click(function(event) { 
			
			iKEP.fileUpload(event.target.id, '0', '1');	
			
		});
		
		/**
		 * Validation Logic Start
		 */
		var validOptions = {
			rules : {
				"themeName" : {
					required : true,
					maxlength : 100
				},
				"description" : {
					maxlength : 250
				},
				"cssPath" : {
					required : true,
					maxlength : 2000
				},
				"previewImageId" : {
					required : true
				}
			},
			messages : {
				"themeName" : {
					required : "<ikep4j:message key='NotEmpty.portalTheme.themeName' />",
					maxlength : "<ikep4j:message key='Size.portalTheme.themeName' />"
				},
				"description" : {
					maxlength : "<ikep4j:message key='Size.portalTheme.description' />"
				},
				"cssPath" : {
					required : "<ikep4j:message key='NotEmpty.portalTheme.cssPath' />",
					maxlength : "<ikep4j:message key='Size.portalTheme.cssPath' />"
				},
				"previewImageId" : {
					required : "<ikep4j:message key='NotEmpty.portalTheme.previewImageId' />"
				}
			},
			submitHandler : function(form) {
				
				form.action = "<c:url value='/portal/admin/screen/theme/updateTheme.do'/>";
				form.submit();
				
			}
		};
		
		var validator = new iKEP.Validator("#mainForm", validOptions);
		
		/**
		 * Validation Logic End
		 */
		
	});
	
	list = function() {
		
		location.href='<c:url value="listTheme.do"/>';	 
		
	};
	
	save = function() {
		
		$jq("#mainForm").trigger("submit");
		
	};
	
	afterFileUpload = function (status, fileId, fileName, message, targetId) {
		
		var htmlText = "<a class='image-gallery' href='<c:url value='/support/fileupload/downloadFile.do'/>?fileId="+fileId+"' title='"+fileName+"'>"+fileName+"</a>";
		
		$jq("#fileNameSpan").html(htmlText);
		$jq("input[name=previewImageId]").val(fileId);
		
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
	
		<table summary="<ikep4j:message pre='${preForm}' key='title' />">
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
						<div>
						<spring:bind path="portalTheme.themeName">
						<input id="${status.expression}" name="${status.expression}" type="text" class="inputbox w100" title="<ikep4j:message pre='${preForm}' key='themeName' />" value="${portalTheme.themeName}" />
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
						</div>
					</td>
				</tr>
				<!--themeName End-->
				
				<!--description Start-->
				<tr>
					<th scope="row">
						<ikep4j:message pre="${preForm}" key="description" />
					</th>
					<td>
						<div>
						<spring:bind path="portalTheme.description">
						<input id="${status.expression}" name="${status.expression}" type="text" class="inputbox w100" title="<ikep4j:message pre='${preForm}' key='description' />" value="${portalTheme.description}" />
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
						</div>
					</td>
				</tr>
				<!--description End-->
				
				<!--cssPath Start-->
				<tr>
					<th scope="row">
						<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="cssPath" />
					</th>
					<td>
						<div>
						<spring:bind path="portalTheme.cssPath">
						<input id="${status.expression}" name="${status.expression}" type="text" class="inputbox w100" title="<ikep4j:message pre='${preForm}' key='cssPath' />" value="${portalTheme.cssPath}" />
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
						</div>
					</td>
				</tr>
				<!--cssPath End-->
				
				<!--previewImageId Start-->
				<tr>
					<th scope="row">
						<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="preImage" />
					</th>
					<td>
						<!-- div>
						<spring:bind path="portalTheme.previewImageId">
						<input id="${status.expression}" name="${status.expression}" type="text" class="inputbox" title="<ikep4j:message pre='${preForm}' key='preImage' />" value="${portalTheme.previewImageId}" />
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
						</div-->
						<input type="hidden" id="previewImageId" name="previewImageId" value="${portalTheme.previewImageId}"/>
						<c:if test="${!empty portalTheme.fileData}">
						<span id="fileNameSpan">
						<a class="image-gallery" href="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${portalTheme.fileData.fileId}" title="${portalTheme.fileData.fileRealName}">
							<c:out value="${portalTheme.fileData.fileRealName}"/>
						</a>
						</span>
						</c:if>
						<c:if test="${empty portalTheme.fileData}">
						<span id="fileNameSpan">
						</span>
						</c:if>
						<a class="button" href="#" onclick="return false;">
							<span id="fileuploadBtn"><ikep4j:message pre="${preButton}" key="attachFile" /></span>
						</a>
					</td>
				</tr>
				<!--previewImageId End-->
				
				<!--defaultOption Start-->
				<tr>
					<th scope="row">
						<ikep4j:message pre="${preForm}" key="default" />
					</th>
					<td>
					<c:choose>
					<c:when test="${portalTheme.defaultOption == '1'}">
						<input type="radio" id="optionYes" name="tdefaultOption" class="radio" title="<ikep4j:message pre='${preForm}' key='yes' />" value="1" <c:if test="${portalTheme.defaultOption == '1'}">checked="checked"</c:if> disabled="disabled" />
						<label for="optionYes"><ikep4j:message pre="${preForm}" key="yes" /></label>&nbsp;
						<input type="radio" id="optionNo" name="tdefaultOption" class="radio" title="<ikep4j:message pre='${preForm}' key='no' />" value="0" <c:if test="${portalTheme.defaultOption == '0'}">checked="checked"</c:if> disabled="disabled" />
						<label for="optionNo"><ikep4j:message pre="${preForm}" key="no" /></label>
						<input type="hidden" name="defaultOption" value="1"/>
					</c:when>
					<c:otherwise>
						<input type="radio" id="optionYes" name="defaultOption" class="radio" title="<ikep4j:message pre='${preForm}' key='yes' />" value="1" <c:if test="${portalTheme.defaultOption == '1'}">checked="checked"</c:if> />
						<label for="optionYes"><ikep4j:message pre="${preForm}" key="yes" /></label>&nbsp;
						<input type="radio" id="optionNo" name="defaultOption" class="radio" title="<ikep4j:message pre='${preForm}' key='no' />" value="0" <c:if test="${portalTheme.defaultOption == '0'}">checked="checked"</c:if> />
						<label for="optionNo"><ikep4j:message pre="${preForm}" key="no" /></label>
					</c:otherwise>
					</c:choose>
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
			<li><a class="button" href="#" onclick="save(); return false;"><span><ikep4j:message pre="${preButton}" key="save" /></span></a></li>
			<li><a class="button" href="#" onclick="list(); return false;"><span><ikep4j:message pre="${preButton}" key="list" /></span></a></li>
		</ul>
	</div>	
	<!--//blockButton End-->
</div>