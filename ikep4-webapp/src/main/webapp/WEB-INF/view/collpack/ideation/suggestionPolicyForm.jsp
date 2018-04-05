<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.collpack.ideation.constants.IdeationConstants" %>

<c:set var="preUi" 			value="ui.collpack.ideation.suggestionolicyForm" />

<script type="text/javascript">

var validOptions = {
	rules : {
		suggestionWeight :	{
			digits:true
			,required : true
			,maxlength  : 5
		}
		,recommendWeight :	{
			digits:true
			,required : true
			,maxlength  : 5
		}
		,adoptWeight :	{
			digits:true
			,required : true
			,maxlength  : 5
		}
		,bestWeight :{
			digits:true
			,required : true
			,maxlength  : 5
		}
		,businessWeight : {
			digits:true
			,required : true
			,maxlength  : 5
		}
		
	},
	messages : {
		suggestionWeight : {
			digits : "<ikep4j:message key='Digits.collpack.ideation.form' />"
			,required : "<ikep4j:message key='NotEmpty.ideation.weight' />"
			,maxlength : "<ikep4j:message key='Size.ideation.weight' />"
		}
		,recommendWeight : {
			digits : "<ikep4j:message key='Digits.collpack.ideation.form' />"
			,required : "<ikep4j:message key='NotEmpty.ideation.weight' />"
			,maxlength : "<ikep4j:message key='Size.ideation.weight' />"
		}
		,adoptWeight : {
			digits : "<ikep4j:message key='Digits.collpack.ideation.form'/>"
			,required : "<ikep4j:message key='NotEmpty.ideation.weight' />"
			,maxlength : "<ikep4j:message key='Size.ideation.weight' />"
		}
		,bestWeight : {
			digits : "<ikep4j:message key='Digits.collpack.ideation.form'/>"
			,required : "<ikep4j:message key='NotEmpty.ideation.weight' />"
			,maxlength : "<ikep4j:message key='Size.ideation.weight' />"
		}
		,businessWeight : {
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



<h1 class="none">Contents</h1>
				
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

<!--debate_point Start-->
<div class="debate_point">
	<h3><ikep4j:message pre='${preUi}' key='ideaActiCount'/> =</h3>
	
	<div class="point_box">
		<span>(<ikep4j:message pre='${preUi}' key='ideaSugCount'/> × <ikep4j:message pre='${preUi}' key='weight'/>) + (<ikep4j:message pre='${preUi}' key='recommIdeaCount'/> × <ikep4j:message pre='${preUi}' key='weight'/>) 
		<!-- + (<ikep4j:message pre='${preUi}' key='adoptIdeaCount'/> × <ikep4j:message pre='${preUi}' key='weight'/>)  -->+ (<ikep4j:message pre='${preUi}' key='bestIdeaCount'/> × <ikep4j:message pre='${preUi}' key='weight'/>) + (<ikep4j:message pre='${preUi}' key='adoptIdeaCount'/> × <ikep4j:message pre='${preUi}' key='weight'/>)</span>
	</div>
</div>
<!--//debate_point End-->

<!--blockDetail Start-->					
<div class="blockDetail">
	<form method="post" id="policyForm" name="policyForm" action="createPolicy.do">
		<input type="hidden" name="policyId" value="${suggestionPolicy.policyId}"/>
		<input type="hidden" name="policyType" value="<%=IdeationConstants.POLICY_SUGGESTION%>"/>
	<table summary="table">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="25%" class="textCenter"><label for="suggestionWeight"><ikep4j:message pre='${preUi}' key='ideaSug'/></label></th>
				<th scope="col" width="25%" class="textCenter"><label for="recommendWeight"><ikep4j:message pre='${preUi}' key='ideaRocommCount'/></label></th>
				<!-- <th scope="col" width="20%" class="textCenter"><label for="adoptWeight"><ikep4j:message pre='${preUi}' key='ideaAdoptCount'/></label></th> -->
				<th scope="col" width="25%" class="textCenter"><label for="bestWeight"><ikep4j:message pre='${preUi}' key='ideaBestCount'/></label></th>
				<th scope="col" width="25%" class="textCenter"><label for="businessWeight"><ikep4j:message pre='${preUi}' key='ideaAdoptCount'/></label></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="textCenter"><div><input type="text" id="suggestionWeight" name="suggestionWeight" size="5" maxlength="5" value="${suggestionPolicy.suggestionWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" id="recommendWeight" 	name="recommendWeight" size="5"  maxlength="5" value="${suggestionPolicy.recommendWeight}"/>%</div></td>
				<!-- <td class="textCenter"><div><input type="text" id="adoptWeight" 		name="adoptWeight" size="5"  maxlength="5" value="${suggestionPolicy.adoptWeight}"/>%</div></td> -->
				<input type="hidden" id="adoptWeight" 		name="adoptWeight" size="5"  maxlength="5" value="0"/>
				<td class="textCenter"><div><input type="text" id="bestWeight" 		name="bestWeight" size="5"  maxlength="5" value="${suggestionPolicy.bestWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" id="businessWeight" 	name="businessWeight" size="5"  maxlength="5" value="${suggestionPolicy.businessWeight}"/>%</div></td>
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


