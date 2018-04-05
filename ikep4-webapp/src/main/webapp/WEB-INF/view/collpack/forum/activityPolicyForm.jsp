<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.collpack.forum.constants.ForumConstants" %>

<c:set var="preResource" 	value="ui.collpack.forum.activityPolicyForm" />

<script type="text/javascript">


var validOptions = {
	rules : {
		discussionWeight :	{digits:true}
		,itemWeight :	{digits:true}
		,linereplyWeight :	{digits:true}
		,bestItemWeight :{digits:true}
		,bestLinereplyWeight : {digits:true}
		,participationWeight : {digits:true}
		,feedbackWeight : {digits:true}
		,favoriteWeight : {digits:true}
		,agreementWeight : {digits:true}
		,recommendWeight : {digits:true}
		
	},
	messages : {
		discussionWeight : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form' />",
		}
		,itemWeight : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form' />",
		}
		,linereplyWeight : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form'/>",
		}
		,bestItemWeight : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form'/>"
		}
		,bestLinereplyWeight : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form'/>"
		}
		,participationWeight : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form'/>"
		}
		,feedbackWeight : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form'/>"
		}
		,favoriteWeight : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form'/>"
		}
		,agreementWeight : {
			digits : "<ikep4j:message key='Digits.collpack.forum.form'/>"
		}
		,recommendWeight : {
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
	<h3><ikep4j:message pre='${preResource}' key='title'/></h3>
</div>
<!--//subTitle_2 End-->
	
<!--directive Start-->
<div class="directive"> 
	<ul>
		<li><span><ikep4j:message pre='${preResource}' key='contents1'/></span></li>	
		<li><span><ikep4j:message pre='${preResource}' key='contents2'/></span></li>	
		<li><span><ikep4j:message pre='${preResource}' key='contents3'/></span></li>																		
	</ul>
</div>
<!--//directive End-->

<!--debate_point Start-->
<div class="debate_point">
	<h3><ikep4j:message pre='${preResource}' key='pointTitle'/> =</h3>
	
	<div class="point_group">
		<div class="point_01">
			<div class="point_01p">
				<p>(<ikep4j:message pre='${preResource}' key='divMulWeg'/>) +</p>
				<p>(<ikep4j:message pre='${preResource}' key='itemMulWeg'/>) +</p>
				<p>(<ikep4j:message pre='${preResource}' key='lineMulWeg'/>) +</p>
				<p>(<ikep4j:message pre='${preResource}' key='bestItemMulWeg'/>) +</p>
				<p>(<ikep4j:message pre='${preResource}' key='bestLineMulWeg'/>) +</p>
			</div>
			<div class="point_Title"><ikep4j:message pre='${preResource}' key='myActiMulWeg'/></div>
		</div>
		<div class="point_plus">
			<img src="<c:url value="/base/images/icon/ic_plus.gif"/>" alt="plus" />
		</div>
		<div class="point_02">
			<div class="point_02p">
				<p>(<ikep4j:message pre='${preResource}' key='itemFavorMulWeg'/>) +</p>
				<p>(<ikep4j:message pre='${preResource}' key='itemAgreeMulWeg'/>) +</p>
				<p>(<ikep4j:message pre='${preResource}' key='lineRecomMulWeg'/>) +</p>
			</div>
			<div class="point_Title"><ikep4j:message pre='${preResource}' key='myFeedWeg'/></div>
		</div>
		<div class="clear"></div>
	</div>
</div>
<!--//debate_point End-->
<form method="post" id="policyForm" name="policyForm" action="createPolicy.do">
<input type="hidden" name="policyId" value="${activityPolicy.policyId}"/>
<input type="hidden" name="policyType" value="<%=ForumConstants.POLICY_ACTIVITY%>"/>
<!--blockDetail Start-->
<div class="blockDetail">
	<table summary="participationTable">
		<caption></caption>

		<tbody>
			<tr>
				<th width="20%" scope="row" rowspan="2"><label for="participationWeight"><ikep4j:message pre='${preResource}' key='myActi'/></label> <span class="colorPoint"><b><input type="text" id="participationWeight" name="participationWeight" size="2" value="${activityPolicy.participationWeight}"/></b></span>%</th>
				<th width="16%" class="textCenter" scope="row"><label for="discussionWeight"><ikep4j:message pre='${preResource}' key='disCount'/></label></th>
				<th width="16%" class="textCenter" scope="row"><label for="itemWeight"><ikep4j:message pre='${preResource}' key='itemCount'/></label></th>
				<th width="16%" class="textCenter" scope="row"><label for="linereplyWeight"><ikep4j:message pre='${preResource}' key='lineCount'/></label></th>
				<th width="16%" class="textCenter" scope="row"><label for="bestItemWeight"><ikep4j:message pre='${preResource}' key='bestItem'/></label></th>
				<th width="16%" class="textCenter" scope="row"><label for="bestLinereplyWeight"><ikep4j:message pre='${preResource}' key='bestLine'/></label></th>
			</tr>
			<tr>
				<td class="textCenter"><div><input type="text" id="discussionWeight" name="discussionWeight" size="2" value="${activityPolicy.discussionWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" id="itemWeight" name="itemWeight" size="2" value="${activityPolicy.itemWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" id="linereplyWeight" name="linereplyWeight" size="2" value="${activityPolicy.linereplyWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" id="bestItemWeight" name="bestItemWeight" size="2" value="${activityPolicy.bestItemWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" id="bestLinereplyWeight" name="bestLinereplyWeight" size="2" value="${activityPolicy.bestLinereplyWeight}"/>%</div></td>
			</tr>
		</tbody>
	</table>
</div>
<!--//blockDetail End-->

<!--blockDetail Start-->
<div class="blockDetail">
	<table summary="feedbackTable">
		<caption></caption>

		<tbody>
			<tr>
				<th width="20%" scope="row" rowspan="2"><label for="feedbackWeight"><ikep4j:message pre='${preResource}' key='myFeed'/></label> <span class="colorPoint"><b><input type="text" id="feedbackWeight" name="feedbackWeight" size="2" value="${activityPolicy.feedbackWeight}"/></b></span>%</th>
				<th width="27%" class="textCenter" scope="row"><label for="favoriteWeight"><ikep4j:message pre='${preResource}' key='itemFavor'/></label></th>
				<th width="27%" class="textCenter" scope="row"><label for="agreementWeight"><ikep4j:message pre='${preResource}' key='itemAgree'/></label></th>
				<th width="26%" class="textCenter" scope="row"><label for="recommendWeight"><ikep4j:message pre='${preResource}' key='lineRecom'/></label></th>
			</tr>
			<tr>
				<td class="textCenter"><div><input type="text" id="favoriteWeight" name="favoriteWeight" size="2" value="${activityPolicy.favoriteWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" id="agreementWeight" name="agreementWeight" size="2" value="${activityPolicy.agreementWeight}"/>%</div></td>
				<td class="textCenter"><div><input type="text" id="recommendWeight" name="recommendWeight" size="2" value="${activityPolicy.recommendWeight}"/>%</div></td>
			</tr>
		</tbody>
	</table>
</div>
<!--//blockDetail End-->
</form>

<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a class="button" href="#a" title="저장" onclick="policySubmit();return false;"><span>저장</span></a></li>
	</ul>
</div>
<!--//blockButton End-->
									

