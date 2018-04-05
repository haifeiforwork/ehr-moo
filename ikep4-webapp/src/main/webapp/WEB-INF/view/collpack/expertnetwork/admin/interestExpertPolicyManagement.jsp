<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="buttonPrefix">ui.collpack.expertnetwork.common.button</c:set>
<c:set var="confirmPrefix">ui.collpack.expertnetwork.common.confirm</c:set>
<c:set var="validationMessagePrefix"></c:set>
<c:set var="interestExpertPolicyManagementMessagePrefix">ui.collpack.expertnetwork.admin.interestExpertPolicyManagement</c:set>

<div id="tagResult">

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${interestExpertPolicyManagementMessagePrefix}" key="pageTitle"/></h2>
</div>
<!--//pageTitle End-->					

<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3><ikep4j:message pre="${interestExpertPolicyManagementMessagePrefix}" key="subTitle"/></h3>
</div>
<!--//subTitle_2 End-->

<!--blockDetail Start-->					
<div class="blockDetail clear">
	<form id="dataForm" action="">
		<input name="id" type="hidden" value="${expertPolicy.id}"/>
	<table summary="<ikep4j:message pre="${interestExpertPolicyManagementMessagePrefix}" key="table.summary"/>">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="25%" class="textCenter"><ikep4j:message pre="${interestExpertPolicyManagementMessagePrefix}" key="table.column1.title"/></th>
				<th scope="col" width="25%" class="textCenter"><ikep4j:message pre="${interestExpertPolicyManagementMessagePrefix}" key="table.column2.title"/></th>
				<th scope="col" width="25%" class="textCenter"><ikep4j:message pre="${interestExpertPolicyManagementMessagePrefix}" key="table.column3.title"/></th>
				<th scope="col" width="25%" class="textCenter"><ikep4j:message pre="${interestExpertPolicyManagementMessagePrefix}" key="table.column4.title"/></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="textCenter"><ikep4j:message pre="${interestExpertPolicyManagementMessagePrefix}" key="table.column1.message"/></td>
				<td class="textCenter"><div>
					<input name="guestbookWeight" class="inputbox" size="3" type="text" value="${expertPolicy.guestbookWeight}" title="input box" style="display:none;"/>
					<span><fmt:formatNumber value="${expertPolicy.guestbookWeight}"/></span>&nbsp;%
				</div></td>
				<td class="textCenter"><div>
					<input name="followWeight" class="inputbox" size="3" type="text" value="${expertPolicy.followWeight}" title="input box" style="display:none;"/>
					<span><fmt:formatNumber value="${expertPolicy.followWeight}"/></span>&nbsp;%
				</div></td>
				<td class="textCenter"><div>
					<input name="favoriteWeight" class="inputbox" size="3" type="text" value="${expertPolicy.favoriteWeight}" title="input box" style="display:none;"/>
					<span><fmt:formatNumber value="${expertPolicy.favoriteWeight}"/></span>&nbsp;%
				</div></td>
			</tr>
		</tbody>
	</table>
	</form>
</div>
<!--//blockDetail End-->

<!--blockButton Start-->
<div class="blockButton">
	<ul>
		<li><a class="button" href="#a" onclick="$jq('#dataForm').submit();" id="saveBtn" style="display:none;"><span><ikep4j:message pre="${buttonPrefix}" key="save"/></span></a></li>
		<li><a class="button" href="#a" onclick="cancelBtnHandler();" id="cancelBtn" style="display:none;"><span><ikep4j:message pre="${buttonPrefix}" key="cancel"/></span></a></li>
		<li><a class="button" href="#a" onclick="modifyBtnHandler();" id="modifyBtn"><span><ikep4j:message pre="${buttonPrefix}" key="modify"/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->

<!--directive Start-->
<div class="directive"> 
	<ul>
		<li><span><ikep4j:message pre="${interestExpertPolicyManagementMessagePrefix}" key="directive.span1"/></span></li>
		<li><span><ikep4j:message pre="${interestExpertPolicyManagementMessagePrefix}" key="directive.span2"/></span></li>
		<li><span><ikep4j:message pre="${interestExpertPolicyManagementMessagePrefix}" key="directive.span3"/></span></li>
	</ul>
</div>
<!--//directive End-->

</div>

<script type="text/javascript">
//<![CDATA[
var validator;

var modifyBtnHandler = function() {
	statusEdit();
};

var saveBtnHandler = function(form) {
	if (!confirm("<ikep4j:message pre='${confirmPrefix}' key='save'/>")) {
		return;
	}

	$jq.ajax({
		url : iKEP.getContextRoot() + "/collpack/expertnetwork/admin/savePolicy.do",
		type : "post",
		data : $jq(form).serialize(),
		loadingElement : "#saveBtn",
		success : function(data, textStatus, jqXHR) {
			$jq("#menuInterestExpertPolicyManagement").click();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			var errorItems = $jq.parseJSON(jqXHR.responseText).exception;
			validator.showErrors(errorItems);
		}
	});
};

var cancelBtnHandler = function() {
	$jq("#dataForm label[class=valid-error]").hide();

	statusRead();
};

var statusEdit = function() {
	$jq("#dataForm input[type=text]").each(function(){
		$jq(this).val($jq(this).next().text().replace(/,/g, ''));
	});

	$jq("#saveBtn").show();
	$jq("#cancelBtn").show();
	$jq("#modifyBtn").hide();
	$jq("#dataForm input[type=text]").show();
	$jq("#dataForm span").hide();
};

var statusRead = function() {
	$jq("#saveBtn").hide();
	$jq("#cancelBtn").hide();
	$jq("#modifyBtn").show();
	$jq("#dataForm input[type=text]").hide();
	$jq("#dataForm span").show();
};
//]]>
</script>

<script type="text/javascript">
//<![CDATA[
var validOptions = {
		rules : {
			guestbookWeight : {
				required : true,
				digits : true,
				range : [0, 1000]
			},
			followWeight : {
				required : true,
				digits : true,
				range : [0, 1000]
			},
			favoriteWeight : {
				required : true,
				digits : true,
				range : [0, 1000]
			}
		},
		messages : {
			guestbookWeight : {
				direction : "bottom",
				required : "<ikep4j:message pre='${validationMessagePrefix}' key='NotNull.expertPolicy.guestbookWeight'/>",
				digits : "<ikep4j:message pre='${validationMessagePrefix}' key='Digits.expertPolicy.guestbookWeight'/>",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.expertPolicy.guestbookWeight'/>"
			},
			followWeight : {
				direction : "bottom",
				required : "<ikep4j:message pre='${validationMessagePrefix}' key='NotNull.expertPolicy.followWeight'/>",
				digits : "<ikep4j:message pre='${validationMessagePrefix}' key='Digits.expertPolicy.followWeight'/>",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.expertPolicy.followWeight'/>"
			},
			favoriteWeight : {
				direction : "bottom",
				required : "<ikep4j:message pre='${validationMessagePrefix}' key='NotNull.expertPolicy.favoriteWeight'/>",
				digits : "<ikep4j:message pre='${validationMessagePrefix}' key='Digits.expertPolicy.favoriteWeight'/>",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.expertPolicy.favoriteWeight'/>"
			}
	    },
	    submitHandler : saveBtnHandler
	};

$jq(document).ready(function() {
	validator = new iKEP.Validator("#dataForm", validOptions);
});
//]]>
</script>
