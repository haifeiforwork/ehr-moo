<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preAlert" value="message.portal.admin.screen.layout.readLayout.alert"/>
<c:set var="preList" value="ui.portal.admin.screen.layout.readLayout.list"/>
<c:set var="preForm" value="ui.portal.admin.screen.layout.readLayout.form"/>
<c:set var="preButton" value="ui.portal.admin.screen.layout.readLayout.button"/>

<script type="text/javascript">
//<![CDATA[
(function($) {
	
	$jq(document).ready(function() {
		
		//left menu setting
		$jq("#portletLayoutLinkOfLeft").click();
		
		$jq("#mainForm:input:visible:enabled:first").focus();
		
		/**
		 * Validation Logic Start
		 */
		var validOptions = {
			rules : {
				"layoutName" : {
					required : true,
					maxlength : 100
				},
				"layoutNum" : {
					digits : true,
					range : [1, 99999]
				},
				"description" : {
					maxlength : 250
				}
			},
			messages : {
				"layoutName" : {
					required : "<ikep4j:message key='NotEmpty.portalLayout.layoutName' />",
					maxlength : "<ikep4j:message key='Size.portalLayout.layoutName' />"
				},
				"layoutNum" : {
					digits : "<ikep4j:message key='Digits.portalLayout.layoutNum' />",
					range : "<ikep4j:message key='Range.portalLayout.layoutNum' />"
				},
				"description" : {
					maxlength : "<ikep4j:message key='Size.portalLayout.description' />"
				}
			},
			submitHandler : function(form) {
				
				$jq.ajax({
					url : "<c:url value='/portal/admin/screen/layout/updateLayout.do'/>",
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
	/*
	modify = function() {
		
		$jq("#mainForm").trigger("submit");
		
	};
	
	remove = function() {
		
		if(confirm("<ikep4j:message pre='${preAlert}' key='deleteConfirm' />")){
			var mainForm = document.mainForm;
			
			mainForm.action = '<c:url value="/portal/admin/screen/layout/removeLayout.do"/>';
			mainForm.submit();
		}
		
	};
	*/
	/*
	num_check = function(form_name) {
		
	 	if (isNaN(eval(form_name).value)) {
	  		alert ("<ikep4j:message pre='${preAlert}' key='onlyNumber' />");
	  		
	  		eval(form_name).value = "";
	  		eval(form_name).focus();
		}
	 	
	};
	*/
	/*
	view = function(layoutId) {
		
		location.href = '<c:url value="readLayout.do"/>?layoutId=' + layoutId;
		
	};
	
	addForm = function() {
		
		location.href = '<c:url value="createLayoutForm.do"/>';
		
	};
	*/
	
})(jQuery);
//]]>
</script>

<form id="mainForm" name="mainForm" action="" method="post" onsubmit="return false;">  
<input type="hidden" id="layoutId" name="layoutId" value="<c:out value='${portalLayout.layoutId}'/>"/>
			
	<table summary="<ikep4j:message pre='${preForm}' key='subTitle' />">
		<caption></caption>
		<colgroup>
			<col width="18%"/>
			<col width="82%"/>
		</colgroup>
		<tbody>						
			<!--layoutName Start-->
			<tr>
				<th>
					<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="layoutName" />
				</th>
				<td class="textLeft">
					<div>
					<spring:bind path="portalLayout.layoutName">
					<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="<c:out value='${portalLayout.layoutName}'/>"/>
					<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
					</spring:bind>
					</div>
				</td>
			</tr>	
			<!--//layoutName End-->	
			
			<!--layoutNum Start-->
			<tr>
				<th>
					<ikep4j:message pre="${preForm}" key="tileCount" />
				</th>
				<td class="textLeft">
					<div>
					<spring:bind path="portalLayout.layoutNum">
					<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox" value="<c:out value='${portalLayout.layoutNum}'/>" />
					<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
					</spring:bind>
					</div>
				</td>
			</tr>	
			<!--//layoutNum End-->	
			
			<!--description Start-->
			<tr>
				<th>
					<ikep4j:message pre="${preForm}" key="description" />
				</th>
				<td class="textLeft">
					<div>
					<spring:bind path="portalLayout.description">
					<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="<c:out value='${portalLayout.description}'/>"/>
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