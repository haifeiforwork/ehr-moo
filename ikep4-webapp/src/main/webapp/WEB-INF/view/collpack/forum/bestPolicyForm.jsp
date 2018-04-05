<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.collpack.forum.constants.ForumConstants" %>

<c:set var="preResource" 	value="ui.collpack.forum.bestPolicyForm" />

<script type="text/javascript">

var validOptions = {
		rules : {
			agreementWeight :	{digits:true}
			,recommendWeight :	{digits:true}
			
		},
		messages : {
			agreementWeight : {
				digits : "<ikep4j:message key='Digits.collpack.forum.form' />",
			}
			,recommendWeight : {
				digits : "<ikep4j:message key='Digits.collpack.forum.form' />",
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
		<li><span><ikep4j:message pre='${preResource}' key='contens1'/></span></li>	
		<li><span><ikep4j:message pre='${preResource}' key='contens2'/></span></li>	
		<li><span><ikep4j:message pre='${preResource}' key='contens3'/></span></li>
		<li><span><ikep4j:message pre='${preResource}' key='contens4'/></span></li>																					
	</ul>
</div>
<!--//directive End-->

<div class="blockBlank_20px"></div>
<form method="post" id="policyForm" name="policyForm" action="createPolicy.do">
<input type="hidden" name="policyId" value="${bestPolicy.policyId}"/>
<input type="hidden" name="policyType" value="<%=ForumConstants.POLICY_BEST%>"/>
		
<!--blockDetail Start-->
<div class="blockDetail">
	<table summary="table">
		<caption></caption>
		<tbody>
			<tr>
				<th scope="row" width="32%"><ikep4j:message pre='${preResource}' key='bestItemTitle'/></th>
				<td width="68%" class="textCenter"><div><label for="agreementWeight"><ikep4j:message pre='${preResource}' key='agreeCount'/></label> <input type="text" id="agreementWeight" name="agreementWeight" class="inputbox" size="2" value="${bestPolicy.agreementWeight}"/> <ikep4j:message pre='${preResource}' key='more'/></div></td>
			</tr>
			<tr>
				<th scope="row"><ikep4j:message pre='${preResource}' key='bestLineTitle'/></th>
				<td class="textCenter"><div><label for="recommendWeight"><ikep4j:message pre='${preResource}' key='recomCount'/></label> <input type="text" id="recommendWeight" name="recommendWeight" class="inputbox" size="2" value="${bestPolicy.recommendWeight}"/> <ikep4j:message pre='${preResource}' key='more'/></div></td>
			</tr>
		</tbody>
	</table>
</div>
<!--//blockDetail End-->
</form>
		
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a class="button" href="#a" onclick="policySubmit();return false;" title="<ikep4j:message pre='${preResource}' key='save'/>"><span><ikep4j:message pre='${preResource}' key='save'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->

