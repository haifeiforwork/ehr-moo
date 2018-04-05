<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="prefix" value="ui.portal.admin.batch.updateCronJobForm"/>

<script type="text/javascript">
//<![CDATA[
(function($) {
	
    $jq(document).ready(function() {
    	
    	//left menu setting
		$jq("#batchTriggerLinkOfLeft").click();
    	
		$jq("#updateCronJobForm:input:visible:enabled:first").focus();
		
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
				
				cronExpression : {
					required : true,
					maxlength : 2000
				},
				
				description : {
					required : true,
					maxlength : 200
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
				
				cronExpression :	{
					required : "<ikep4j:message key='NotEmpty.portal.batch.cronExpression' />",
					maxlength : "<ikep4j:message key='Size.portal.batch.cronExpression' />"
				},
				
				description :	{
					required : "<ikep4j:message key='NotEmpty.portal.batch.description' />",
					maxlength : "<ikep4j:message key='Size.portal.batch.description' />"
				}
			},
			submitHandler : function(form) {
				form.action = "<c:url value='/portal/admin/batch/updateCronJob.do'/>";
				form.submit();
			}
		};
		
		var validator = new iKEP.Validator("#updateCronJobForm", validOptions);
    });
    
	save = function() {
		$jq("#updateCronJobForm").trigger("submit");
	};
	
})(jQuery);
//]]>
</script>
<form id="updateCronJobForm" name="updateCronJobForm" method="post" action="" onsubmit="return false;">
	<input type="hidden" name="oldJobName" value="${cronJobDetail.jobName}">
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
						<spring:bind path="cronJobDetail.triggerName">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${cronJobDetail.triggerName}" title="<ikep4j:message pre='${prefix}' key='triggerName'/>"/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
					</div>			
					</td>
				</tr>
				<tr>
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='jobName'/></th>
					<td colspan="2">
					<div>
						<spring:bind path="cronJobDetail.jobName">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${cronJobDetail.jobName}" title="<ikep4j:message pre='${prefix}' key='jobName'/>"/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
					</div>	
					</td>
				</tr>
				<tr>
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='jobClass'/></th>
					<td colspan="2">
					<div>
						<spring:bind path="cronJobDetail.jobClass">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${cronJobDetail.jobClass}" title="<ikep4j:message pre='${prefix}' key='jobClass'/>"/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
					</div>					
					</td>
				</tr>
				<tr>
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='cronExpression'/></th>
					<td colspan="2">
					<div>
						<spring:bind path="cronJobDetail.cronExpression">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${cronJobDetail.cronExpression}" title="<ikep4j:message pre='${prefix}' key='cronExpression'/>"/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
					</div>						
					</td>
				</tr>
				<tr>
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='description'/></th>
					<td colspan="2">
					<div>
						<spring:bind path="cronJobDetail.description">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${cronJobDetail.description}" title="<ikep4j:message pre='${prefix}' key='description'/>"/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
					</div>						
					</td>
				</tr>
				<tr>
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='jobStatus'/></th>
					<td colspan="2">
					<div>
						<label><input type="radio" class="radio" name="jobStatus" value="WAITING" <c:if test="${cronJobDetail.jobStatus == 'WAITING'}">checked="checked"</c:if> title="<ikep4j:message pre='${prefix}' key='waiting'/>" /><ikep4j:message pre='${prefix}' key='waiting'/></label>&nbsp;
						<label><input type="radio" class="radio" name="jobStatus" value="PAUSED" <c:if test="${cronJobDetail.jobStatus == 'PAUSED'}">checked="checked"</c:if> title="<ikep4j:message pre='${prefix}' key='pause'/>" /><ikep4j:message pre='${prefix}' key='pause'/></label>	
					</div>	
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='jobListener'/></th>
					<td colspan="2">
					<div>
						<spring:bind path="cronJobDetail.jobListener">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${cronJobDetail.jobListener}" title="<ikep4j:message pre='${prefix}' key='jobListener'/>"/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
					</div>	
					</td>
				</tr>
				<tr>
					<th scope="row">파라미터</th>
					<td colspan="2">
					<div>
						<spring:bind path="cronJobDetail.jobCondition">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${cronJobDetail.jobCondition}" />
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