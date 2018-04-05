<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.collpack.forum.constants.ForumConstants" %>

<c:set var="preUi" 			value="ui.collpack.forum.popularPolicyForm" />

<script type="text/javascript">

var validOptions = {
	rules : {
		maxCountDis :	{digits:true}
		,itemWeightDis :	{digits:true}
		,linereplyWeightDis :	{digits:true}
		,agreementWeightDis :{digits:true}
		,favoriteWeightDis : {digits:true}
		,recommendWeightDis : {digits:true}
		,maxCountItem : {digits:true}
		,linereplyWeightItem : {digits:true}
		,agreementWeightItem : {digits:true}
		,favoriteWeightItem : {digits:true}
		
	},
	messages : {
		maxCountDis : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form' />",
		}
		,itemWeightDis : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form' />",
		}
		,linereplyWeightDis : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form'/>",
		}
		,agreementWeightDis : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form'/>"
		}
		,favoriteWeightDis : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form'/>"
		}
		,recommendWeightDis : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form'/>"
		}
		,maxCountItem : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form'/>"
		}
		,linereplyWeightItem : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form'/>"
		}
		,agreementWeightItem : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form'/>"
		}
		,favoriteWeightItem : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form'/>"
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
	<h3><ikep4j:message pre='${preUi}' key='title'/></h3>
</div>
<!--//subTitle_2 End-->
	
<!--directive Start-->
<div class="directive"> 
	<ul>
		<li><span><ikep4j:message pre='${preUi}' key='contents1'/></span></li>	
		<li><span><ikep4j:message pre='${preUi}' key='contents2'/></span></li>	
		<li><span><ikep4j:message pre='${preUi}' key='contents3'/></span></li>
		<li><span><ikep4j:message pre='${preUi}' key='contents4'/></span></li>																					
	</ul>
</div>
<!--//directive End-->

<!--debate_point Start-->
<div class="debate_point">
	<h3><ikep4j:message pre='${preUi}' key='popularDis'/> =</h3>
	
	<div class="point_box">
		<span>(<ikep4j:message pre='${preUi}' key='itemCount'/> × <ikep4j:message pre='${preUi}' key='weigth'/>) + (<ikep4j:message pre='${preUi}' key='lineCount'/> × <ikep4j:message pre='${preUi}' key='weigth'/>) 
		+ (<ikep4j:message pre='${preUi}' key='agreeCount'/>+<ikep4j:message pre='${preUi}' key='oppCount'/>) × <ikep4j:message pre='${preUi}' key='weigth'/> + (<ikep4j:message pre='${preUi}' key='favoriteCount'/> × <ikep4j:message pre='${preUi}' key='weigth'/>) + (<ikep4j:message pre='${preUi}' key='recommendCount'/> × <ikep4j:message pre='${preUi}' key='weigth'/>)</span>
	</div>
</div>
<!--//debate_point End-->	

<form method="post" id="policyForm" name="policyForm" action="createPopularPolicy.do">
<input type="hidden" name="disPolicyId" value="${popularDisPolicy.policyId}"/>
<input type="hidden" name="itemPolicyId" value="${popularItemPolicy.policyId}"/>
<input type="hidden" name="policyTypeDis" value="<%=ForumConstants.POLICY_POPULAR_DISCUSSION%>"/>
<input type="hidden" name="policyTypeItem" value="<%=ForumConstants.POLICY_POPULAR_ITEM%>"/>
		
<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3><ikep4j:message pre='${preUi}' key='popularDisManager'/></h3>
	<div class="subTitle_2_input"><h4><label for="maxCountDis"><ikep4j:message pre='${preUi}' key='max'/></label> <input type="text" id="maxCountDis" name="maxCountDis" class="inputbox w20" value="${popularDisPolicy.maxCount}"/></h4></div>
</div>
<!--//subTitle_2 End-->

<div class="blockBlank_5px"></div>

<!--blockDetail Start-->					
<div class="blockDetail">
	<table summary="popular discussion table">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="20%" class="textCenter"><label for="itemWeightDis"><ikep4j:message pre='${preUi}' key='itemCount'/></label></th>
				<th scope="col" width="20%" class="textCenter"><label for="linereplyWeightDis"><ikep4j:message pre='${preUi}' key='lineCount'/></label></th>
				<th scope="col" width="20%" class="textCenter"><label for="agreementWeightDis"><ikep4j:message pre='${preUi}' key='agreeCount'/> + <ikep4j:message pre='${preUi}' key='oppCount'/></label></th>
				<th scope="col" width="20%" class="textCenter"><label for="favoriteWeightDis"><ikep4j:message pre='${preUi}' key='favoriteCount'/></label></th>
				<th scope="col" width="20%" class="textCenter"><label for="recommendWeightDis"><ikep4j:message pre='${preUi}' key='recommendCount'/></label></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="textCenter"><div><input type="text" id="itemWeightDis" name="itemWeightDis" class="inputbox w20" value="${popularDisPolicy.itemWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" id="linereplyWeightDis" name="linereplyWeightDis" class="inputbox w20" value="${popularDisPolicy.linereplyWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" id="agreementWeightDis" name="agreementWeightDis" class="inputbox w20" value="${popularDisPolicy.agreementWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" id="favoriteWeightDis" name="favoriteWeightDis" class="inputbox w20" value="${popularDisPolicy.favoriteWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" id="recommendWeightDis" name="recommendWeightDis" class="inputbox w20" value="${popularDisPolicy.recommendWeight}"/>%</div></td>
			</tr>
		</tbody>
	</table>
</div>
<!--//blockDetail End-->

<!--debate_point Start-->
<div class="debate_point">
	<h3><ikep4j:message pre='${preUi}' key='popularItem'/> =</h3>
	
	<div class="point_box">
		<span>(<ikep4j:message pre='${preUi}' key='lineCount'/> × <ikep4j:message pre='${preUi}' key='weigth'/>) + (<ikep4j:message pre='${preUi}' key='agreeCount'/>+<ikep4j:message pre='${preUi}' key='oppCount'/>) × <ikep4j:message pre='${preUi}' key='weigth'/> + (<ikep4j:message pre='${preUi}' key='favoriteCount'/> × <ikep4j:message pre='${preUi}' key='weigth'/>)</span>
	</div>
</div>
<!--//debate_point End-->

<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3><ikep4j:message pre='${preUi}' key='popularItemManager'/></h3>
	<div class="subTitle_2_input"><h4><label for="maxCountItem"><ikep4j:message pre='${preUi}' key='max'/></label> <input type="text" id="maxCountItem" name="maxCountItem" class="inputbox w20" value="${popularItemPolicy.maxCount}"/></h4></div>
</div>
<!--//subTitle_2 End-->

<div class="blockBlank_5px"></div>

<!--blockDetail Start-->					
<div class="blockDetail">
	<table summary="popular item table">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="34%" class="textCenter"><label for="linereplyWeightItem"><ikep4j:message pre='${preUi}' key='lineCount'/></label></th>
				<th scope="col" width="34%" class="textCenter"><label for="agreementWeightItem"><ikep4j:message pre='${preUi}' key='agreeCount'/> + <ikep4j:message pre='${preUi}' key='oppCount'/></label></th>
				<th scope="col" width="32%" class="textCenter"><label for="favoriteWeightItem"><ikep4j:message pre='${preUi}' key='favoriteCount'/></label></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="textCenter"><div><input type="text" id="linereplyWeightItem" name="linereplyWeightItem" class="inputbox w20" value="${popularItemPolicy.linereplyWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" id="agreementWeightItem" name="agreementWeightItem" class="inputbox w20" value="${popularItemPolicy.agreementWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" id="favoriteWeightItem" name="favoriteWeightItem" class="inputbox w20" value="${popularItemPolicy.favoriteWeight}"/>%</div></td>
			</tr>
		</tbody>
	</table>
</div>
<!--//blockDetail End-->

<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a class="button" href="#a" onclick="policySubmit();return false;" title="<ikep4j:message pre='${preUi}' key='save'/>"><span><ikep4j:message pre='${preUi}' key='save'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
										
</form>

