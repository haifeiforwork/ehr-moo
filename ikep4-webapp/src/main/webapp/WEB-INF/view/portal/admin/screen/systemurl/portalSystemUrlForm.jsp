<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preAlert" value="message.portal.admin.screen.systemurl.portalSystemUrlForm.alert"/>
<c:set var="preForm" value="ui.portal.admin.screen.systemurl.portalSystemUrlForm.form"/>
<c:set var="preButton" value="ui.portal.admin.screen.systemurl.portalSystemUrlForm.button"/>

<script type="text/javascript">
//<![CDATA[
(function($) {
	$(document).ready(function() {
		
		$jq("#portalSystemUrlForm:input:visible:enabled:first").focus();
		
		$jq("a[name=newSystemUrlButton]").click(function() {
			getList();
		});
		
		$jq("a[name=updateSystemUrlButton]").click(function() {  
			$jq("#portalSystemUrlForm").trigger("submit");
		}); 
		
		$jq("a[name=deleteSystemUrlButton]").click(function() {  
			if(confirm("<ikep4j:message pre='${preAlert}' key='confirmDelete' />")) {
				$jq.ajax({
					url : "<c:url value='portalSystemUrlDelete.do' />",
					data : $jq("#portalSystemUrlForm").serialize(),
					type : "post",
					success : function(result) {
						alert("<ikep4j:message pre='${preAlert}' key='deleteSuccess' />");
						
						getList();
					},
					error : function() { 
						alert("<ikep4j:message pre='${preAlert}' key='deleteFail' />");
					}
				});
			} else {
				return;
			}
		});
		
		/**
		 * Validation Logic Start
		 */
		var validOptions = {
			rules : {
				<c:forEach var="i18nMessage" items="${portalSystemUrl.i18nMessageList}" varStatus="loopStatus">
				"i18nMessageList[${loopStatus.index}].itemMessage" : {
					required : true,
					maxlength : 100
				},
				</c:forEach>
				"url" : {
					required : true,
					maxlength : 500
				}
			},
			messages : {
				<c:forEach var="i18nMessage" items="${portalSystemUrl.i18nMessageList}" varStatus="loopStatus">
				"i18nMessageList[${loopStatus.index}].itemMessage" : {
					required : "<ikep4j:message key='NotEmpty.portalSystemUrl.systemUrlName' />",
					maxlength : "<ikep4j:message key='Size.portalSystemUrl.systemUrlName' />"
				},
				</c:forEach>
				"url" : {
					required : "<ikep4j:message key='NotEmpty.portalSystemUrl.url' />",
					maxlength : "<ikep4j:message key='Size.portalSystemUrl.url' />"
				}
			},
			submitHandler : function() {
		    	
				$jq("input[name=systemUrlName]").val($jq("input[id=${userLocaleCode}]").val());
				
				$jq.ajax({
					url : "<c:url value='portalSystemUrlUpdate.do' />",
					data : $jq("#portalSystemUrlForm").serialize(),
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
		
		var validator = new iKEP.Validator("#portalSystemUrlForm", validOptions);

		/**
		 * Validation Logic End
		 */
	});
})(jQuery);
//]]>
</script>

<form id="portalSystemUrlForm" name="portalSystemUrlForm" action="" method="post">  
<input type="hidden" name="systemUrlId" value="${portalSystemUrl.systemUrlId}"/>
<input type="hidden" name="systemUrlName" id="systemUrlName" value="" />
	
	<table summary="<ikep4j:message pre='${preForm}' key='tableSummary' />">
		<caption></caption>
		<colgroup>
			<col width="10%"/>
			<col width="8%"/>
			<col width="82%"/>
		</colgroup>
		<tbody>
			<!--systemUrlName Start-->		
			<tr>
					<th scope="row" rowspan="${localeSize}">
						<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="systemUrlName" />
					</th>
					<c:forEach var="i18nMessage" items="${portalSystemUrl.i18nMessageList}" varStatus="loopStatus">
					<c:if test="${i18nMessage.fieldName eq 'systemUrlName' }">
					<c:if test="${i18nMessage.index > 1}">
				<tr>
					</c:if>
					<th scope="row">
						<span class="colorPoint">*</span><c:out value="${i18nMessage.localeCode}"/>
					</th>
					<td>
						<div>
						<spring:bind path="portalSystemUrl.i18nMessageList[${loopStatus.index}].itemMessage">
							<input type="text" id="${i18nMessage.localeCode}" name="i18nMessageList[${loopStatus.index}].itemMessage" value="${i18nMessage.itemMessage}" class="inputbox w100" />
							<input type="hidden" name="i18nMessageList[${loopStatus.index}].localeCode" value="${i18nMessage.localeCode}" />
							<input type="hidden" name="i18nMessageList[${loopStatus.index}].fieldName" value="${i18nMessage.fieldName}" />
							<input type="hidden" name="i18nMessageList[${loopStatus.index}].messageId" value="${i18nMessage.messageId}" />
							<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
						</spring:bind>
						</div>
					</td>
				</tr>
					</c:if>
					</c:forEach>
			<!--//systemUrlName End-->
				
			<!--url Start-->
			<tr>
				<th colspan="2">
					<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="url" />
				</th>
				<td class="textLeft">
					<div>
					<spring:bind path="portalSystemUrl.url">
					<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${portalSystemUrl.url}"/>
					<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
					</spring:bind>
					</div>
				</td>
			</tr>	
			<!--//url End-->	
		</tbody>
	</table>
	
</form>

<div class="blockBlank_10px"></div>
		
<!--blockButton Start-->
<div class="blockButton">
	<ul>
		<li><a name="newSystemUrlButton" class="button" href="#" onclick="return false;"><span><ikep4j:message pre="${preButton}" key="new" /></span></a></li>
		<li><a name="updateSystemUrlButton" class="button" href="#" onclick="return false;"><span><ikep4j:message pre="${preButton}" key="update" /></span></a></li>
		<li><a name="deleteSystemUrlButton" class="button" href="#" onclick="return false;"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
	</ul>
</div>
<!--//blockButton End-->