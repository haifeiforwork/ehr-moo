<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="preAlert" value="message.portal.admin.batch.readCronJob"/>
<c:set var="prefix" value="ui.portal.admin.batch.readCronJob"/>

<script type="text/javascript">
//<![CDATA[
(function($) {
	
    $jq(document).ready(function() {
    	
    	//left menu setting
		$jq("#batchTriggerLinkOfLeft").click();
    	
    	$jq("#updateButton").click(function() {
			location.href = "<c:url value='/portal/admin/batch/updateCronJobForm.do?triggerName=${cronJobDetail.triggerName}&jobName=${cronJobDetail.jobName}'/>";
		});
    	
    	$jq("#removeButton").click(function() {
			if(confirm("<ikep4j:message pre='${prefix}' key='confirm.remove'/>")) {
				$jq("#readJobForm")[0].submit();
			}
		});
    	
    });
	
})(jQuery);
//]]>
</script>
<form id="readJobForm" method="post" action="<c:url value='/portal/admin/batch/removeJob.do'/>" onsubmit="return false;">
<input type="hidden" name="jobName" value="${cronJobDetail.jobName}"/>
</form>
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
					<th scope="row"><ikep4j:message pre='${prefix}' key='triggerName'/></th>
					<td colspan="2">
						${cronJobDetail.triggerName}						
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='jobName'/></th>
					<td colspan="2">
						${cronJobDetail.jobName}
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='jobClass'/></th>
					<td colspan="2">
						${cronJobDetail.jobClass}							
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='cronExpression'/></th>
					<td colspan="2">
						${cronJobDetail.cronExpression}							
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='description'/></th>
					<td colspan="2">
						${cronJobDetail.description}							
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='jobStatus'/></th>
					<td colspan="2">
						<c:if test="${cronJobDetail.jobStatus == 'PAUSED'}">
							<ikep4j:message pre='${prefix}' key='pause'/>
						</c:if>
						
						<c:if test="${cronJobDetail.jobStatus == 'WAITING'}">
							<ikep4j:message pre='${prefix}' key='waiting'/>
						</c:if>
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='jobListener'/></th>
					<td colspan="2">
						${cronJobDetail.jobListener}
					</td>
				</tr>
				<tr>
					<th scope="row">파라미터</th>
					<td colspan="2">
						${cronJobDetail.jobCondition}
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
				<li><a class="button" href="<c:url value='/portal/admin/batch/listTrigger.do'/>"><span><ikep4j:message pre='${prefix}' key='button.list'/></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
