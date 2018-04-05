<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preUi" 			value="ui.collpack.qna.policyForm" />
<c:set var="preResource" 	value="message.collpack.qna" />

<script type="text/javascript">

var validOptions = {
	rules : {
		qnaHitWeight :	{
			digits:true
			,required : true
			,maxlength  : 7
		}
		,qnaLinereplyWeight :	{
			digits:true
			,required : true
			,maxlength  : 7
		}
		,qnaAnswerWeight :	{
			digits:true
			,required : true
			,maxlength  : 7
		}
		,qnaFavoriteWeight :{
			digits:true
			,required : true
			,maxlength  : 7
		}
		,qnaAnswerSumWeight :{
			digits:true
			,required : true
			,maxlength  : 7
		}
		,bestQnaBasisPoint :{
			digits:true
			,required : true
			,maxlength  : 7
		}
		,replyLinereplyWeight : {
			digits:true
			,required : true
			,maxlength  : 7
		}
		,replyRecommendWeight : {
			digits:true
			,required : true
			,maxlength  : 7
		}
		,replyAnswerAdoptWeight : {
			digits:true
			,required : true
			,maxlength  : 7
		}
		,bestAnswerBasisPoint : {
			digits:true
			,required : true
			,maxlength  : 7
		}
		
	},
	messages : {
		qnaHitWeight : {
			digits : "<ikep4j:message key='Digits.collpack.qna.form' />"
			,required : "<ikep4j:message key='NotEmpty.qna.weight' />"
			,maxlength : "<ikep4j:message key='Size.qna.weight' />"
		}
		,qnaLinereplyWeight : {
			digits : "<ikep4j:message key='Digits.collpack.qna.form' />"
			,required : "<ikep4j:message key='NotEmpty.qna.weight' />"
			,maxlength : "<ikep4j:message key='Size.qna.weight' />"
		}
		,qnaAnswerWeight : {
			digits : "<ikep4j:message key='Digits.collpack.qna.form'/>"
			,required : "<ikep4j:message key='NotEmpty.qna.weight' />"
			,maxlength : "<ikep4j:message key='Size.qna.weight' />"
		}
		,qnaFavoriteWeight : {
			digits : "<ikep4j:message key='Digits.collpack.qna.form'/>"
			,required : "<ikep4j:message key='NotEmpty.qna.weight' />"
			,maxlength : "<ikep4j:message key='Size.qna.weight' />"
		}
		,qnaAnswerSumWeight : {
			digits : "<ikep4j:message key='Digits.collpack.qna.form'/>"
			,required : "<ikep4j:message key='NotEmpty.qna.weight' />"
			,maxlength : "<ikep4j:message key='Size.qna.weight' />"
		}
		,bestQnaBasisPoint : {
			digits : "<ikep4j:message key='Digits.collpack.qna.form'/>"
			,required : "<ikep4j:message key='NotEmpty.qna.weight' />"
			,maxlength : "<ikep4j:message key='Size.qna.weight' />"
		}
		,replyLinereplyWeight : {
			digits : "<ikep4j:message key='Digits.collpack.qna.form'/>"
			,required : "<ikep4j:message key='NotEmpty.qna.weight' />"
			,maxlength : "<ikep4j:message key='Size.qna.weight' />"
		}
		,replyRecommendWeight : {
			digits : "<ikep4j:message key='Digits.collpack.qna.form'/>"
			,required : "<ikep4j:message key='NotEmpty.qna.weight' />"
			,maxlength : "<ikep4j:message key='Size.qna.weight' />"
		}
		,replyAnswerAdoptWeight : {
			digits : "<ikep4j:message key='Digits.collpack.qna.form'/>"
			,required : "<ikep4j:message key='NotEmpty.qna.weight' />"
			,maxlength : "<ikep4j:message key='Size.qna.weight' />"
		}
		,bestAnswerBasisPoint : {
			digits : "<ikep4j:message key='Digits.collpack.qna.form'/>"
			,required : "<ikep4j:message key='NotEmpty.qna.weight' />"
			,maxlength : "<ikep4j:message key='Size.qna.weight' />"
		}
	}

};


$jq(document).ready(function() {

	new iKEP.Validator('#policyForm', validOptions);
 /*
	
	$jq('input:text').keyup(function(){
		
		$jq('#qnaTotal').text(weightTotal('qna'));
		
		$jq('#replyTotal').text(weightTotal('reply'));
		
	});
	
	$jq('#qnaTotal').text(weightTotal('qna'));
	
	$jq('#replyTotal').text(weightTotal('reply'));
	*/
});

function weightTotal(type){
	var total = 0;
	$jq('#'+type+'Weight input:text').each(function(i){

		if(i < $jq('#'+type+'Weight input:text').size()-1){
			total += Number($jq(this).val());
		}
		
		
		});
	
	return total;
}

function policySubmit(){
	
	var f = $jq('#policyForm');
	
	var qTotal = $jq('#qnaTotal').text();
	var rTotal = $jq('#replyTotal').text();

	/*
	if(!(qTotal == '100' && rTotal == '100')){
		alert('<ikep4j:message key='Percent.qna.NotPercent'/>');
		return false;
	}
	*/
	
	f.submit();
}

</script>
	
<h1 class="none">contentsArea</h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preUi}' key='pageLocationTitle'/></h2>
</div>
<!--//pageTitle End-->	

<br/>
<div class="subTitle_2 noline">
	<h3><ikep4j:message pre='${preUi}' key='tableHeader1'/></h3>
</div>

<form method="post" id="policyForm" name="policyForm" action="createPolicy.do">
	<input type="hidden" name="qnaPolicyId" value="${listPolicy[0].policyId}"/>
	<input type="hidden" name="replyPolicyId" value="${listPolicy[1].policyId}"/>
	
	<!--blockDetail Start-->		
	<div class="blockDetail clear">

	<table summary="<ikep4j:message pre='${preUi}' key='subTile'/>">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="14%" class="textCenter"><ikep4j:message pre='${preUi}' key='type'/></th>
				<th scope="col" width="14%" class="textCenter"><ikep4j:message pre='${preUi}' key='tableHitCount'/></th>
				<th scope="col" width="14%" class="textCenter"><ikep4j:message pre='${preUi}' key='tableLinereplyCount'/></th>
				<th scope="col" width="14%" class="textCenter"><ikep4j:message pre='${preUi}' key='tableAnswerCount'/></th>
				<th scope="col" width="14%" class="textCenter"><ikep4j:message pre='${preUi}' key='tableFavoriteCount'/></th>
				<th scope="col" width="14%" class="textCenter"><ikep4j:message pre='${preUi}' key='qnaScoreSum'/></th>
				<th scope="col" width="14%" class="textCenter"
				style="background:#FFDD22;"><ikep4j:message pre='${preUi}' key='qnaBestBasisScore'/></th>
			</tr>
		</thead>
		
		<tbody>
		
			<tr id="qnaWeight">
				<th class="textCenter"><ikep4j:message pre='${preUi}' key='tableQnaTotal'/> </th>
				<td class="textCenter"><div><input type="text" title="<ikep4j:message pre='${preUi}' key='tableHitCount'/>" name="qnaHitWeight" size="7" value="${listPolicy[0].hitWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" title="<ikep4j:message pre='${preUi}' key='tableLinereplyCount'/>" name="qnaLinereplyWeight" size="7" value="${listPolicy[0].linereplyWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" title="<ikep4j:message pre='${preUi}' key='tableAnswerCount'/>" name="qnaAnswerWeight" size="7" value="${listPolicy[0].answerWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" title="<ikep4j:message pre='${preUi}' key='tableFavoriteCount'/>" name="qnaFavoriteWeight" size="7" value="${listPolicy[0].favoriteWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" title="<ikep4j:message pre='${preUi}' key='qnaScoreSum'/>" name="qnaAnswerSumWeight" size="7" value="${listPolicy[0].answerSumWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" title="<ikep4j:message pre='${preUi}' key='qnaBestBasisScore'/>" name="bestQnaBasisPoint" size="7" value="${listPolicy[0].bestQnaBasisPoint}"/></div></td>
			</tr>
		
		</tbody>
	</table>
	
</div>

<br/><br/>	
<div class="subTitle_2 noline">
	<h3><ikep4j:message pre='${preUi}' key='tableHeader2'/></h3>
</div>

<div class="blockDetail clear">
	<table summary="<ikep4j:message pre='${preUi}' key='subTile'/>">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="20%" class="textCenter"><ikep4j:message pre='${preUi}' key='type'/></th>
				<th scope="col" width="20%" class="textCenter"><ikep4j:message pre='${preUi}' key='answerLinereplyCount'/></th>
				<th scope="col" width="20%" class="textCenter"><ikep4j:message pre='${preUi}' key='answerRecommendCount'/></th>
				<th scope="col" width="20%" class="textCenter"><ikep4j:message pre='${preUi}' key='answerAdoptCount'/></th>
				<th scope="col" width="20%" class="textCenter"
				style="background:#FFDD22;"><ikep4j:message pre='${preUi}' key='answerBestBasisScore'/></th>
			</tr>
		</thead>
		
		<tbody>
		
			<tr id="replyWeight">
				<th  class="textCenter"><ikep4j:message pre='${preUi}' key='tableQnaTotal'/></th>
				<td class="textCenter"><div><input type="text" title="<ikep4j:message pre='${preUi}' key='answerLinereplyCount'/>" name="replyLinereplyWeight" size="7" value="${listPolicy[1].answerLinereplyWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" title="<ikep4j:message pre='${preUi}' key='answerRecommendCount'/>" name="replyRecommendWeight" size="7" value="${listPolicy[1].answerRecommendWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" title="<ikep4j:message pre='${preUi}' key='answerAdoptCount'/>" name="replyAnswerAdoptWeight" size="7" value="${listPolicy[1].answerAdoptWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" title="<ikep4j:message pre='${preUi}' key='answerBestBasisScore'/>" name="bestAnswerBasisPoint" size="7" value="${listPolicy[1].bestAnswerBasisPoint}"/></div></td>
			</tr>
		
		</tbody>
	</table>

</div>
<!--//blockDetail End-->
	
</form>
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a class="button" href="#a" onclick="policySubmit();return false;"><span><ikep4j:message pre='${preUi}' key='tableCreate'/></span></a></li>						
	</ul>
</div>
<!--//blockButton End-->

<!--directive Start-->
<div class="directive"> 
	<ul>
		<li><span><ikep4j:message pre='${preUi}' key='tableContent1'/></span></li>
		<li><span><ikep4j:message pre='${preUi}' key='tableContent2'/></span></li>	
		<li><span><ikep4j:message pre='${preUi}' key='tableContent3'/></span></li>	
		<li><span><ikep4j:message pre='${preUi}' key='tableContent4'/></span></li>																	
	</ul>
</div>
<!--//directive End-->	


