<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="prefix" value="ui.portal.admin.batch.updateSimpleJobForm"/>

<script type="text/javascript">
//<![CDATA[
(function($) {
	
    $jq(document).ready(function() {
    	
    	//left menu setting
		$jq("#batchTriggerLinkOfLeft").click();
    	
		$jq("#updateSimpleJobForm:input:visible:enabled:first").focus();
		
		/**
		 * Validation Logic Start
		 */
		
		var validOptions = {
			rules : {
				triggerName : {
					required : true,
					maxlength : 100
				},
				
				jobName : {
					required : true,
					maxlength : 250
				},
				
				jobClass : {
					required : true,
					maxlength : 500
				},
				
				description : {
					required : true,
					maxlength : 200
				},
				
				repeatInterval : {
					required : true,
					maxlength : 2000
				},
				
				repeatCount : {
					required : true,
					maxlength : 2000
				}
			},
			messages : {
				triggerName :	{
					required : "<ikep4j:message key='NotEmpty.portal.batch.triggerName' />",
					maxlength : "<ikep4j:message key='Size.portal.batch.triggerName' />"
				},
			
				jobName :	{
					required : "<ikep4j:message key='NotEmpty.portal.batch.jobName' />",
					maxlength : "<ikep4j:message key='Size.portal.batch.jobName' />"
				},
				
				jobClass :	{
					required : "<ikep4j:message key='NotEmpty.portal.batch.jobClass' />",
					maxlength : "<ikep4j:message key='Size.portal.batch.jobClass' />"
				},
				
				description :	{
					required : "<ikep4j:message key='NotEmpty.portal.batch.description' />",
					maxlength : "<ikep4j:message key='Size.portal.batch.description' />"
				},
				
				repeatInterval :	{
					required : "<ikep4j:message key='NotEmpty.portal.batch.repeatInterval' />",
					maxlength : "<ikep4j:message key='Digits.portal.batch.repeatInterval' />"
				},
				
				repeatCount :	{
					required : "<ikep4j:message key='NotEmpty.portal.batch.repeatCount' />",
					maxlength : "<ikep4j:message key='Digits.portal.batch.repeatCount' />"
				}
			},
			submitHandler : function(form) {
				form.action = "<c:url value='/portal/admin/batch/updateSimpleJob.do'/>";
				form.submit();
			}
		};
		
		var validator = new iKEP.Validator("#updateSimpleJobForm", validOptions);
    });
    
	save = function() {
		$jq("#updateSimpleJobForm").trigger("submit");
	};
	
})(jQuery);
//]]>
</script>
<form id="updateSimpleJobForm" name="updateSimpleJobForm" method="post" action="" onsubmit="return false;">
	<input type="hidden" name="oldJobName" value="${simpleJobDetail.jobName}">
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre='${prefix}' key='pageTitle'/></h2>
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
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='triggerName'/></th>
					<td colspan="2">
					<div>
						<spring:bind path="simpleJobDetail.triggerName">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${simpleJobDetail.triggerName}" title="<ikep4j:message pre='${prefix}' key='triggerName'/>"/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
					</div>			
					</td>
				</tr>
				<tr>
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='jobName'/></th>
					<td colspan="2">
					<div>
						<spring:bind path="simpleJobDetail.jobName">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${simpleJobDetail.jobName}" title="<ikep4j:message pre='${prefix}' key='jobName'/>"/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
					</div>	
					</td>
				</tr>
				<tr>
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='jobClass'/></th>
					<td colspan="2">
					<div>
						<spring:bind path="simpleJobDetail.jobClass">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${simpleJobDetail.jobClass}" title="<ikep4j:message pre='${prefix}' key='jobClass'/>"/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
					</div>					
					</td>
				</tr>
				<tr>
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='description'/></th>
					<td colspan="2">
					<div>
						<spring:bind path="simpleJobDetail.description">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${simpleJobDetail.description}" title="<ikep4j:message pre='${prefix}' key='description'/>"/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
					</div>					
					</td>
				</tr>
				<tr>
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='repeatInterval'/></th>
					<td colspan="2">
					<div>
						<spring:bind path="simpleJobDetail.repeatInterval">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${simpleJobDetail.repeatInterval}" title="<ikep4j:message pre='${prefix}' key='repeatInterval'/>"/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
					</div>						
					</td>
				</tr>
				<tr>
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='repeatCount'/></th>
					<td colspan="2">
					<div>
						<spring:bind path="simpleJobDetail.repeatCount">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${simpleJobDetail.repeatCount}" title="<ikep4j:message pre='${prefix}' key='repeatCount'/>"/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
					</div>						
					</td>
				</tr>
				<tr>
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='jobStatus'/></th>
					<td colspan="2">
					<div>
						<label><input type="radio" class="radio" name="jobStatus" value="WAITING" <c:if test="${simpleJobDetail.jobStatus == 'WAITING'}">checked="checked"</c:if> title="<ikep4j:message pre='${prefix}' key='waiting'/>" /><ikep4j:message pre='${prefix}' key='waiting'/></label>&nbsp;
						<label><input type="radio" class="radio" name="jobStatus" value="PAUSED" <c:if test="${simpleJobDetail.jobStatus == 'PAUSED'}">checked="checked"</c:if> title="<ikep4j:message pre='${prefix}' key='pause'/>" /><ikep4j:message pre='${prefix}' key='pause'/></label>	
					</div>	
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='jobListener'/></th>
					<td colspan="2">
					<div>
						<spring:bind path="simpleJobDetail.jobListener">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${simpleJobDetail.jobListener}" title="<ikep4j:message pre='${prefix}' key='jobListener'/>"/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
					</div>	
					</td>
				</tr>
				<tr>
					<th scope="row">파라미터</th>
					<td colspan="2">
					<div>
						<spring:bind path="simpleJobDetail.jobCondition">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${simpleJobDetail.jobCondition}" />
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
					</div>	
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
				<li><a class="button" href="#" onclick="save(); return false;"><span><ikep4j:message pre="${prefix}" key="button.save" /></span></a></li>
				<li><a class="button" href="<c:url value='/portal/admin/batch/listTrigger.do'/>"><span><ikep4j:message pre='${prefix}' key='button.list'/></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
</form>