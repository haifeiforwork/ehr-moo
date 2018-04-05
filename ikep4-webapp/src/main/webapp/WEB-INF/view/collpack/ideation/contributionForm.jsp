<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.collpack.ideation.constants.IdeationConstants" %>

<c:set var="preUi" 			value="ui.collpack.ideation.contributionForm" />
<script type="text/javascript">


var validOptions = {
	rules : {
		recommendWeight :	{
			digits:true
			,required : true
			,maxlength  : 5
		}
		,adoptWeight :	{
			digits:true
			,required : true
			,maxlength  : 5
		}
		,favoriteWeight :	{
			digits:true
			,required : true
			,maxlength  : 5
		}
		,linereplyWeight :{
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
		,adoptWeight : {
			digits : "<ikep4j:message key='Digits.collpack.ideation.form' />"
			,required : "<ikep4j:message key='NotEmpty.ideation.weight' />"
			,maxlength : "<ikep4j:message key='Size.ideation.weight' />"
		}
		,favoriteWeight : {
			digits : "<ikep4j:message key='Digits.collpack.ideation.form'/>"
			,required : "<ikep4j:message key='NotEmpty.ideation.weight' />"
			,maxlength : "<ikep4j:message key='Size.ideation.weight' />"
		}
		,linereplyWeight : {
			digits : "<ikep4j:message key='Digits.collpack.ideation.form'/>"
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


<h1 class="none">Contens Area</h1>
				
<!--subTitle_2 Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preUi}' key='title'/></h2>
</div>
<!--//subTitle_2 End-->
	
<!--debate_point Start-->
<div class="debate_point">
	<h3><ikep4j:message pre='${preUi}' key='ideaActiCount'/> =</h3>
	
	<div class="point_box">
		<span>(<ikep4j:message pre='${preUi}' key='ideaRecomm'/> × <ikep4j:message pre='${preUi}' key='weight'/>) + (<ikep4j:message pre='${preUi}' key='ideaAdopt'/> × <ikep4j:message pre='${preUi}' key='weight'/>) + (<ikep4j:message pre='${preUi}' key='ideaFavoriteCount'/> × <ikep4j:message pre='${preUi}' key='weight'/>) 
			+ (<ikep4j:message pre='${preUi}' key='ideaLinereplyCount'/> × <ikep4j:message pre='${preUi}' key='weight'/>) </span>
	</div>
</div>
<!--//debate_point End-->

<!--blockDetail Start-->					
<div class="blockDetail">
	<form method="post" id="policyForm" name="policyForm" action="createPolicy.do">
		<input type="hidden" name="policyId" value="${activityPolicy.policyId}"/>
		<input type="hidden" name="policyType" value="<%=IdeationConstants.POLICY_CONTRIBUTION%>"/>
	<table summary="table">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="25%" class="textCenter"><label for="recommendWeight"><ikep4j:message pre='${preUi}' key='ideaRecomm'/></label></th>
				<th scope="col" width="25%" class="textCenter"><label for="adoptWeight"><ikep4j:message pre='${preUi}' key='ideaAdopt'/></label></th>
				<th scope="col" width="25%" class="textCenter"><label for="favoriteWeight"><ikep4j:message pre='${preUi}' key='ideaFavoriteCount'/></label></th>
				<th scope="col" width="25%" class="textCenter"><label for="linereplyWeight"><ikep4j:message pre='${preUi}' key='ideaLinereplyCount'/></label></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="textCenter"><div><input type="text" id="recommendWeight" name="recommendWeight" size="5" maxlength="5" value="${activityPolicy.recommendWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" id="adoptWeight" name="adoptWeight" size="5"  maxlength="5" value="${activityPolicy.adoptWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" id="favoriteWeight" name="favoriteWeight" size="5"  maxlength="5" value="${activityPolicy.favoriteWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" id="linereplyWeight" name="linereplyWeight" size="5"  maxlength="5" value="${activityPolicy.linereplyWeight}"/>%</div></td>
			</tr>
		</tbody>
	</table>
	</form>
</div>
<!--//blockDetail End-->

<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a class="button" href="#a" onclick="policySubmit();return false;" title="<ikep4j:message pre='${preUi}' key='save'/>"><span><ikep4j:message pre='${preUi}' key='save'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->

<!--directive Start-->
<div class="directive"> 
	<ul>
		<li><span><ikep4j:message pre='${preUi}' key='contens1'/></span></li>	
		<li><span><ikep4j:message pre='${preUi}' key='contens2'/></span></li>
		<li><span><ikep4j:message pre='${preUi}' key='contens3'/></span></li>																					
	</ul>
</div>
<!--//directive End-->
