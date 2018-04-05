<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"
	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j"%>

<c:set var="prefix" value="ui.portal.admin.batch.createJobForm"/>

<script type="text/javascript">
	(function($) {

		/**
		 * onload시 수행할 코드
		 */
		$(document).ready(function() {
			$("#repeatIntervalTr").hide();
			$("#repeatCountTr").hide();
			
			$("input[name=triggerType]").click(function() {
				if($(this).val() == "CRON") {
					$("#triggerNameTr").show();
					$("#jobNameTr").show();
					$("#jobClassTr").show();
					$("#cronExpressionTr").show();
					$("#descriptionTr").show();
					$("#repeatIntervalTr").hide();
					$("#repeatCountTr").hide();
					$("#jobStatusTr").show();
					$("#jobListenerTr").show();
					$("#jobConditionTr").show();
				}
				else { 
					//Cron Job 항목 hidden 처리
					$("#triggerNameTr").show();
					$("#jobNameTr").show();
					$("#jobClassTr").show();
					$("#cronExpressionTr").hide();
					$("#descriptionTr").show();
					$("#repeatIntervalTr").show();
					$("#repeatCountTr").show();
					$("#jobStatusTr").show();
					$("#jobListenerTr").show();
					$("#jobConditionTr").show();
				}
			});
			
			
			/**
			 * Validation Logic Start
			 */
			
			var validOptions = {
				rules : {
					triggerName : {
						required : true,
						rangelength : [5,140]
					},
					
					jobName : {
						required : true,
						rangelength : [5,140]
					},
					
					jobClass : {
						required : true,
						rangelength : [5,140]
					},
					
					cronExpression : {
						required : function(element)
						{
							return $(".radio:checked").val()=="CRON";
						},
						rangelength : [5,80]
					},
					
					description : {
						required : true,
						rangelength : [5,200]
					},
					
					repeatInterval : {
						required : function(element)
						{
							return $(".radio:checked").val()=="SIMPLE";
						},
						digits : 10
					},
					
					repeatCount : {
						required : function(element)
						{
							return $(".radio:checked").val()=="SIMPLE";
						},
						digits : 10
					}
				},
				messages : {
					triggerName :	{
						required : "<ikep4j:message key='NotEmpty.portal.batch.triggerName' />",
						rangelength : "<ikep4j:message key='Size.portal.batch.triggerName' />"
					},
				
					jobName :	{
						required : "<ikep4j:message key='NotEmpty.portal.batch.jobName' />",
						rangelength : "<ikep4j:message key='Size.portal.batch.jobName' />"
					},
					
					jobClass :	{
						required : "<ikep4j:message key='NotEmpty.portal.batch.jobClass' />",
						rangelength : "<ikep4j:message key='Size.portal.batch.jobClass' />"
					},
					
					cronExpression :	{
						required : "<ikep4j:message key='NotEmpty.portal.batch.cronExpression' />",
						rangelength : "<ikep4j:message key='Size.portal.batch.cronExpression' />"
					},
					
					description :	{
						required : "<ikep4j:message key='NotEmpty.portal.batch.description' />",
						rangelength : "<ikep4j:message key='Size.portal.batch.description' />"
					},
					
					repeatInterval :	{
						required : "<ikep4j:message key='NotEmpty.portal.batch.repeatInterval' />",
						digits : "<ikep4j:message key='Digits.portal.batch.repeatInterval' />"
					},
					
					repeatCount :	{
						required : "<ikep4j:message key='NotEmpty.portal.batch.repeatCount' />",
						digits : "<ikep4j:message key='Digits.portal.batch.repeatCount' />"
					}
				},
				submitHandler : function(form) {
					var triggerType = $(".radio:checked").val();
					
					if(triggerType == 'CRON') {
						form.action = "<c:url value='/portal/admin/batch/createCronJob.do'/>";
					}else {
						form.action = "<c:url value='/portal/admin/batch/createSimpleJob.do'/>";
					}
					form.submit();
				}
			};
			
			var validator = new iKEP.Validator("#createJobForm", validOptions);
		});
		
		save = function() {
			$jq("#createJobForm").trigger("submit");				
		};
		
	})(jQuery); 
	

	</script>
	<link type="text/css" rel="stylesheet" href="<c:url value="${cssPath}"/>" />
	
<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${prefix}' key='pageTitle' /></h2>
</div>
<!--//pageTitle End-->


<!--blockDetail Start-->
<div class="blockDetail">

	<div id="viewDiv">
		<form method="post"	name="createJobForm" id="createJobForm" action="">
			<table id="mainTable" summary="<ikep4j:message pre="${prefix}" key="pageTitle" />">
				<caption></caption>
				<colgroup>
					<col width="18%"/>
					<col width="82%"/>
				</colgroup>
				<tbody>
					<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='triggerType'/></th>
					<td>
					<div>
						<input type="radio" class="radio" title="triggerType" name="triggerType" value="CRON" checked="checked" /> CRON
						<input type="radio" class="radio" title="triggerType" name="triggerType" value="SIMPLE"/> SIMPLE
					</div>			
					</td>
				</tr>
					<tr id="triggerNameTr">
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='triggerName'/></th>
					<td>
					<div>
						<input type="text" id="triggerName" name="triggerName" class="inputbox w100" value="" title="<ikep4j:message pre='${prefix}' key='triggerName'/>"/>
					</div>			
					</td>
				</tr>
				<tr id="jobNameTr">
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='jobName'/></th>
					<td>
					<div>
						<input type="text" id="jobName" name="jobName" class="inputbox w100" value="" title="<ikep4j:message pre='${prefix}' key='jobName'/>"/>
					</div>	
					</td>
				</tr>
				<tr id="jobClassTr">
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='jobClass'/></th>
					<td>
					<div>
						<input type="text" id="jobClass" name="jobClass" class="inputbox w100" value="" title="<ikep4j:message pre='${prefix}' key='jobClass'/>"/>
					</div>					
					</td>
				</tr>
				<tr id="descriptionTr">
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='description'/></th>
					<td>
					<div>
						<input type="text" id="description" name="description" class="inputbox w100" value="" title="<ikep4j:message pre='${prefix}' key='description'/>"/>
					</div>					
					</td>
				</tr>
				<tr id="repeatIntervalTr">
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='repeatInterval'/></th>
					<td>
					<div>
						<input type="text" id="repeatInterval" name="repeatInterval" class="inputbox w100" value="" title="<ikep4j:message pre='${prefix}' key='repeatInterval'/>"/>
					</div>					
					</td>
				</tr>
				<tr id="repeatCountTr">
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='repeatCount'/></th>
					<td>
					<div>
						<input type="text" id="repeatCount" name="repeatCount" class="inputbox w100" value="" title="<ikep4j:message pre='${prefix}' key='repeatCount'/>"/>
					</div>					
					</td>
				</tr>
				<tr id="cronExpressionTr">
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='cronExpression'/></th>
					<td>
					<div>
						<input type="text" id="cronExpression" name="cronExpression" class="inputbox w100" value="" title="<ikep4j:message pre='${prefix}' key='cronExpression'/>"/>
					</div>						
					</td>
				</tr>
				<tr id="jobStatusTr">
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='jobStatus'/></th>
					<td>
					<div>
						<label><input type="radio" class="radio" name="jobStatus" value="WAITING" checked="checked" title="<ikep4j:message pre='${prefix}' key='waiting'/>" /><ikep4j:message pre='${prefix}' key='waiting'/></label>&nbsp;
						<label><input type="radio" class="radio" name="jobStatus" value="PAUSED" title="<ikep4j:message pre='${prefix}' key='pause'/>" /><ikep4j:message pre='${prefix}' key='pause'/></label>	
					</div>						
					</td>
				</tr>
				<tr id="jobListenerTr">
					<th scope="row"><ikep4j:message pre='${prefix}' key='jobListener'/></th>
					<td>
					<div>
						<input type="text" id="jobListener" name="jobListener" class="inputbox w100" value="" title="<ikep4j:message pre='${prefix}' key='jobListener'/>"/>
					</div>	
					</td>
				</tr>
				<tr id="jobConditionTr">
					<th scope="row">파라미터</th>
					<td>
					<div>
						<input type="text" id="jobCondition" name="jobCondition" class="inputbox w100" value="" />
					</div>	
					</td>
				</tr>
				</tbody>
			</table>
		</form>
	</div>
</div>
<!--//blockDetail End-->

<!--blockButton Start-->
<div class="blockButton">
	<ul>
		<li>
		<a class="button" href="#" onclick="save(); return false;"><span><ikep4j:message pre="${prefix}" key="button.save" /></span></a>
		<a class="button" href="<c:url value='/portal/admin/batch/listTrigger.do'/>"><span><ikep4j:message pre='${prefix}' key='button.list'/></span></a>
		</li>
	</ul>
</div>
<!--//blockButton End-->