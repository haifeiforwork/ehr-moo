<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.collpack.ideation.constants.IdeationConstants" %>

<c:set var="preUi" 			value="ui.collpack.ideation.bestPolicyForm" />

<script type="text/javascript">


var validOptions = {
	rules : {
		recommendWeight :	{
			digits:true
			,required : true
			,maxlength  : 5
		}
	},
	messages : {
		recommendWeight : {
			digits : "<ikep4j:message key='Digits.collpack.ideation.form' />"
			,required : "<ikep4j:message key='NotEmpty.ideation.weight' />"
			,maxlength : "<ikep4j:message key='Size.ideation.weight' />"
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
	<h3><img class="valign_top" src="<c:url value="/base/images/icon/ic_idea_2.png"/>" alt="top" /> <ikep4j:message pre='${preUi}' key='title'/></h3>
</div>
<!--//subTitle_2 End-->
	
<!--directive Start-->
<div class="directive"> 
	<ul>
		<li><span><ikep4j:message pre='${preUi}' key='contens1'/></span></li>	
		<li><span><ikep4j:message pre='${preUi}' key='contens2'/></span></li>
		<li><span><ikep4j:message pre='${preUi}' key='contens3'/></span></li>	
	</ul>
</div>
<!--//directive End-->

<div class="blockBlank_20px"></div>

<!--blockDetail Start-->
<div class="blockDetail">
	<form method="post" id="policyForm" name="policyForm" action="createPolicy.do">
		<input type="hidden" name="policyId" value="${bestPolicy.policyId}"/>
		<input type="hidden" name="policyType" value="<%=IdeationConstants.POLICY_BEST%>"/>
	<table summary="table">
		<caption></caption>
		<tbody>
			<tr>
				<th scope="row" width="32%"><ikep4j:message pre='${preUi}' key='bestItemTitle'/></th>
				<td width="68%"  class="textCenter">
					<span><label for="recommendWeight"><ikep4j:message pre='${preUi}' key='recomCount'/></label> <input name="recommendWeight" id="recommendWeight" class="inputbox" size="5" maxlength="5" type="text" value="${bestPolicy.recommendWeight}"/> <ikep4j:message pre='${preUi}' key='more'/></span>
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
		<li><a class="button" href="#a" title="<ikep4j:message pre='${preUi}' key='save'/>" onclick="policySubmit();return false;"><span><ikep4j:message pre='${preUi}' key='save'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->

