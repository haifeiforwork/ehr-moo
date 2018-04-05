<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.collpack.forum.constants.ForumConstants" %>

<c:set var="preResource" 	value="ui.collpack.forum.analysisPolicyForm" />

<script type="text/javascript">

var validOptions = {
		rules : {
			targetTerm :	{digits:true}
			
		},
		messages : {
			targetTerm : {
				digits : "<ikep4j:message key='Digits.collpack.forum.form' />",
				direction : "left",
				
			}
		}

	};


	$jq(document).ready(function() {

		new iKEP.Validator('#policyForm', validOptions);
	 
	});

function policySubmit(){
	
	var f = $jq('#policyForm');
	
	f.submit();
}


function tearmSet(day){
	var target = $jq('#targetTerm');
	
	target.val(day);
	
}

</script>


<h1 class="none">Contents Area</h1>
 
<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3><ikep4j:message pre='${preResource}' key='title'/></h3>
</div>
<!--//subTitle_2 End-->
	
<!--directive Start-->
<div class="directive"> 
	<ul>
		<li><span><ikep4j:message pre='${preResource}' key='contents1'/></span></li>	
		<li><span><ikep4j:message pre='${preResource}' key='contents2'/></span></li>
	</ul>
</div>
<!--//directive End-->

<div class="blockBlank_20px"></div>

<!--blockDetail Start-->
<div class="blockDetail">
<form method="post" id="policyForm" name="policyForm" action="createPolicy.do">
<input type="hidden" name="policyId" value="${analysisPolicy.policyId}"/>
<input type="hidden" name="policyType" value="<%=ForumConstants.POLICY_ANALYSIS%>"/>
	<table summary="list">
		<caption></caption>
		<tbody>
			<tr>
				<th scope="row" width="32%"><ikep4j:message pre='${preResource}' key='settingTitle'/></th>
				<td width="68%"><div>
					<a class="button_s" href="#a" onclick="tearmSet('30');" title="<ikep4j:message pre='${preResource}' key='last30'/>"><span><ikep4j:message pre='${preResource}' key='last30'/></span></a>
					<a class="button_s" href="#a" onclick="tearmSet('7');" title="<ikep4j:message pre='${preResource}' key='last7'/>"><span><ikep4j:message pre='${preResource}' key='last7'/></span></a>
					<a class="button_s" href="#a" onclick="tearmSet('1');" title="<ikep4j:message pre='${preResource}' key='last1'/>"><span><ikep4j:message pre='${preResource}' key='last1'/></span></a>&nbsp;
					<span style="float:right;"><label for="targetTerm"><ikep4j:message pre='${preResource}' key='last'/></label> <input type="text" id="targetTerm" name="targetTerm" class="inputbox" size="3" value="${analysisPolicy.targetTerm}"/> <ikep4j:message pre='${preResource}' key='day'/></span>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	</form>
</div>
<!--//blockDetail End-->
			
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a class="button" href="#a" onclick="policySubmit();return false;" title="저장"><span><ikep4j:message pre='${preResource}' key='save'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
													

