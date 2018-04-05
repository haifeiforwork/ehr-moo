<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preAlert" value="message.portal.admin.screen.portletCategory.readPortletCategory.alert"/>
<c:set var="preSearch" value="ui.portal.admin.screen.portletCategory.readPortletCategory.search"/>
<c:set var="preList" value="ui.portal.admin.screen.portletCategory.readPortletCategory.list"/>
<c:set var="preForm" value="ui.portal.admin.screen.portletCategory.readPortletCategory.form"/>
<c:set var="preButton" value="ui.portal.admin.screen.portletCategory.readPortletCategory.button"/>

<script type="text/javascript">
//<![CDATA[
(function($) {
	
	$jq(document).ready(function() {
		
		//left menu setting
		$jq("#portletGroupLinkOfLeft").click();
		
		$jq("#mainForm:input:visible:enabled:first").focus();
		
		/**
		 * Validation Logic Start
		 */
		var validOptions = {
			rules : {
				"portletCategoryName" : {
					required : true,
					maxlength : 100
				},
				"description" : {
					maxlength : 250
				}
			},
			messages : {
				"portletCategoryName" : {
					required : "<ikep4j:message key='NotEmpty.portalPortletCategory.portletCategoryName' />",
					maxlength : "<ikep4j:message key='Size.portalPortletCategory.portletCategoryName' />"
				},
				"description" : {
					maxlength : "<ikep4j:message key='Size.portalPortletCategory.description' />"
				}
			},
			submitHandler : function() {
				
				$jq.ajax({
					url : "<c:url value='/portal/admin/screen/portletCategory/updatePortletCategory.do' />",
					data : $jq("#mainForm").serialize(),
					type : "post",
					success : function(result) {
						alert("<ikep4j:message pre='${preAlert}' key='saveSuccess' />");
						
						$jq("#tempId").val(result);
						
						getList();
					},
					error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
			}
		};
		
		var validator = new iKEP.Validator("#mainForm", validOptions);
		
		/**
		 * Validation Logic End
		 */
		
	});
	
})(jQuery);
//]]>
</script>

<form id="mainForm" name="mainForm" action="" method="post" onsubmit="return false;">  
<input type="hidden" id="portletCategoryId" name="portletCategoryId" value="<c:out value='${portalPortletCategory.portletCategoryId}'/>"/>
	
	<table summary="<ikep4j:message pre='${preForm}' key='subTitle' />">
		<caption></caption>
		<colgroup>
			<col width="18%"/>
			<col width="82%"/>
		</colgroup>
		<tbody>						
			<!--portletCategoryName Start-->
			<tr>
				<th>
					<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="groupName" />
				</th>
				<td class="textLeft">
					<div>
					<spring:bind path="portalPortletCategory.portletCategoryName">
					<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${portalPortletCategory.portletCategoryName}"/>
					<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
					</spring:bind>
					</div>
				</td>
			</tr>	
			<!--//portletCategoryName End-->	
			
			<!--systemCode Start-->
			<tr>
				<th>
					<ikep4j:message pre="${preForm}" key="system" />
				</th>
				<td class="textLeft">
					<select id="systemCode" name="systemCode">
						<c:forEach var="item" items="${portalSystemMapList}">
						<c:if test="${item.systemCode != '0'}">
						<option value="<c:out value='${item.systemCode}'/>" <c:if test="${item.systemCode == portalPortletCategory.systemCode}">selected="selected"</c:if>><c:out value="${item.systemName}"/></option>
						</c:if>
						</c:forEach>
					</select>
				</td>
			</tr>	
			<!--//systemCode End-->	
			
			<!--description Start-->
			<tr>
				<th>
					<ikep4j:message pre="${preForm}" key="description" />
				</th>
				<td class="textLeft">
					<div>
					<spring:bind path="portalPortletCategory.description">
					<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${portalPortletCategory.description}"/>
					<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
					</spring:bind>
					</div>
				</td>
			</tr>	
			<!--//description End-->	
		</tbody>
	</table>
	
</form>

<div class="blockBlank_10px"></div>

<!--blockButton Start-->
<div class="blockButton">
	<ul>
		<li><a name="addFormButton" class="button" href="#" onclick="addForm(); return false;"><span><ikep4j:message pre="${preButton}" key="new" /></span></a></li>
		<li><a name="modifyButton" class="button" href="#" onclick="modify(); return false;"><span><ikep4j:message pre="${preButton}" key="modify" /></span></a></li>
		<li><a name="removeButton" class="button" href="#" onclick="remove(); return false;"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
