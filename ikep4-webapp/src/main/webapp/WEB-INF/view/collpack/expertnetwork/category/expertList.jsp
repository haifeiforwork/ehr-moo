<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="commonPrefix">ui.collpack.expertnetwork.common</c:set>
<c:set var="confirmPrefix">ui.collpack.expertnetwork.common.confirm</c:set>
<c:set var="buttonPrefix">ui.collpack.expertnetwork.common.button</c:set>
<c:set var="expertListMessagePrefix">ui.collpack.expertnetwork.expertList</c:set>

<div id="tagResult">

<!--expert_bl Start-->
<div id="expertPage">
	<%@ include file="/WEB-INF/view/collpack/expertnetwork/category/expertPage.jsp"%>
</div>
<!--//expert_bl End-->

<!--blockButton Start-->
<c:if test="${pageCondition.expertAdmin}">
<div class="blockButton clear" id="btnDiv">
	<ul>
		<li><a class="button" href="#a" onclick="expertCreate();" id="expertCreate"><span><ikep4j:message pre="${expertListMessagePrefix}" key="admin.button.expertCreate"/></span></a></li>
		<li><a class="button" href="#a" onclick="expertDelete();" id="expertDelete"><span><ikep4j:message pre="${expertListMessagePrefix}" key="admin.button.expertDelete"/></span></a></li>
		<li><a class="button" href="#a" onclick="expertAppendDialogShow();" id="expertAppend"><span><ikep4j:message pre="${expertListMessagePrefix}" key="admin.button.expertAppend"/></span></a></li>
	</ul>
</div>
</c:if>
<!--//blockButton End-->

<form id="expertForm" action="">
	<spring:bind path="pageCondition.categoryId">
		<input type="hidden" name="${status.expression}" value="${status.value}"/>
	</spring:bind>
	<spring:bind path="pageCondition.categoryName">
		<input type="hidden" name="${status.expression}" value="${status.value}"/>
	</spring:bind>
	<input type="hidden" name="addExpertId" value=""/>
</form>

</div>

<!-- Dialog Import -->
<%@ include file="/WEB-INF/view/collpack/expertnetwork/category/expertAppendDialog.jsp"%>

<script type="text/javascript">
//<![CDATA[

var expertCreate = function() {
	var _checkedCount = $jq("#bodyForm input[name=selectItem]:checked").size();

	if (1 > _checkedCount) {
		return;
	}

	var confirmMessage = "";
	confirmMessage += "<ikep4j:message pre="${expertListMessagePrefix}" key="confirm.expertSelect"/>";
	confirmMessage += " " + _checkedCount;
	confirmMessage += "<ikep4j:message pre="${expertListMessagePrefix}" key="confirm.expertCreate"/>";
	if (!confirm(confirmMessage)) {
		return;
	}

	$jq.ajax({
		url : iKEP.getContextRoot() + "/collpack/expertnetwork/category/expertCreate.do",
		type : "post",
		data : $jq("#bodyForm").serialize(),
		loadingElement : {button:"#btnDiv"},
		success : function(data, textStatus, jqXHR) {
			pageReflash();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("<ikep4j:message pre="${commonPrefix}" key="message.fail"/>");
		}
	});
};

var expertDelete = function() {
	var _checkedCount = $jq("#bodyForm input[name=selectItem]:checked").size();

	if (1 > _checkedCount) {
		return;
	}

	var confirmMessage = "";
	confirmMessage += "<ikep4j:message pre="${expertListMessagePrefix}" key="confirm.expertSelect"/>";
	confirmMessage += " " + _checkedCount;
	confirmMessage += "<ikep4j:message pre="${expertListMessagePrefix}" key="confirm.expertDelete"/>";
	if (!confirm(confirmMessage)) {
		return;
	}

	$jq.ajax({
		url : iKEP.getContextRoot() + "/collpack/expertnetwork/category/expertDelete.do",
		type : "post",
		data : $jq("#bodyForm").serialize(),
		loadingElement : {button:"#btnDiv"},
		success : function(data, textStatus, jqXHR) {
			pageReflash();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("<ikep4j:message pre="${commonPrefix}" key="message.fail"/>");
		}
	});
};
//]]>
</script>

<!-- 전문가 리스트 페이징(expertPage.jsp)-->
<script type="text/javascript">
//<![CDATA[

// 페이지
var pageSubmit = function(page) {
	$jq("#searchForm input[name=requestPage]").val(page);

	$jq.ajax({
		url : iKEP.getContextRoot() + "/collpack/expertnetwork/category/expertPage.do",
		type : "get",
		data : $jq("#searchForm").serialize(),
		loadingElement : {container:"#expertPage"},
		success : function(data, textStatus, jqXHR) {
			$jq("#expertPage").empty();
			$jq("#expertPage").html(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("Expert Page Load Fail!!!");
		}
	});
};

//페이지 Reflash
var pageReflash = function() {
	var _page = $jq("#searchForm input[name=page]").val();
	pageSubmit(_page);
};

//페이지 Reflash (DB값을 다시 읽고 처리한다, 전체건수가 변경되었을때)
var pageReflashDB = function() {
	$jq("#searchForm input[name=reInit]").val(true);
	pageReflash();
};

//]]>
</script>
