<c:set var="commonPrefix">ui.collpack.expertnetwork.common</c:set>
<c:set var="confirmPrefix">ui.collpack.expertnetwork.common.confirm</c:set>
<c:set var="buttonPrefix">ui.collpack.expertnetwork.common.button</c:set>
<c:set var="expertListMessagePrefix">ui.collpack.expertnetwork.expertList</c:set>

<!--dialog Start-->
<div id="expertAppendDialog" title="<ikep4j:message pre="${expertListMessagePrefix}" key="admin.dialog.expertAppend.title"/>" style="display:none">
<div class="blockDetail" id="expertAppendDlg">
	<table summary="<ikep4j:message pre="${expertListMessagePrefix}" key="title"/>">
		<caption></caption>
		<tbody>
			<tr>
				<th scope="row" width="18%"><ikep4j:message pre="${expertListMessagePrefix}" key="admin.dialog.expertAppend.item.category.title"/></th>
				<td width="80%">
					<span name="categoryName"></span>
				</td>
			</tr>
			<tr>
				<th scope="row"><ikep4j:message pre="${expertListMessagePrefix}" key="admin.dialog.expertAppend.item.expert.title"/></th>
				<td>
					<input name="searchName" onkeypress="iKEP.searchUser(event, 'Y', searchUserCallback);" title="<ikep4j:message pre="${expertListMessagePrefix}" key="admin.dialog.expertAppend.item.expert.title"/>" class="inputbox" type="text" size="15" />
					<a class="button_ic" name="searchBtn" onclick="searchUserDlg();" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="" /><ikep4j:message pre="${buttonPrefix}" key="address.search"/></span></a>
					<a class="button_ic" name="addressBtn" onclick="searchAddressPopup();" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="" /><ikep4j:message pre="${buttonPrefix}" key="address.address"/></span></a>
					<div>
						<select name="selectedExpert" title="" multiple="multiple" class="w70" size="3" style="height:70px;"></select>
						<a class="button_ic valign_bottom" name="deleteBtn" href="#a" onclick="deleteSelectedItem();"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" /><ikep4j:message pre="${buttonPrefix}" key="address.delete"/></span></a>
						<span class="totalNum_s" name="selectedText"></span>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="blockButton clear" style="padding-top:5px;" id="btnPopDiv">
		<ul>
			<li><a class="button" id="registerBtn" href="#a" onclick="registerItem();"><span><ikep4j:message pre="${buttonPrefix}" key="create"/></span></a></li>
			<li><a class="button" id="cancelBtn" href="#a" onclick="cancelClose();"><span><ikep4j:message pre="${buttonPrefix}" key="cancel"/></span></a></li>
		</ul>
	</div>
</div>
</div>
<!--//dialog Start-->

<script type="text/javascript">
//<![CDATA[

var $expertAppendDialog;

//추가등록 popup 보이기
var expertAppendDialogShow = function() {
	// 카테고리 명칭
	var _categoryName = $jq("#expertForm input[name=categoryName]").val();
	$jq("#expertAppendDlg span[name=categoryName]").text(_categoryName);
	// 기존 Input 에 들어있는 값 Clear
	$jq("#expertAppendDlg input[name=searchName]").val("");
	// 기존 Select 에 들어있는 값 Clear
	var _sel = $jq("#expertAppendDlg select[name=selectedExpert]");
	_sel.empty();
	// 총 0명 Clear
	calcExpertSelectCount();

	$expertAppendDialog.dialog("open");
};

// 추가등록 popup 숨기기
var expertAppendDialogHide = function() {
	$expertAppendDialog.dialog("close");
};

// 조직도 popup후 callback
var showAddressBookCallback = function(data) {
	calcExpertSelectCount();
};

// 사용자명칭 search후 callback
var searchUserCallback = function(data) {
	var $sel = $jq("#expertAppendDlg select[name=selectedExpert]");
	$jq(data).each(function(index) {
		$.tmpl(iKEP.template.userOption, this).appendTo($sel)
			.data("data", this);
	});
	calcExpertSelectCount();
};

// 선택된 전문가 개수 계산
var calcExpertSelectCount = function() {
	var _options = $jq("#expertAppendDlg select[name=selectedExpert] option");

	var message1 = "<ikep4j:message pre="${expertListMessagePrefix}" key="admin.dialog.expertAppend.selected.message1"/>";
	var message2 = "<ikep4j:message pre="${expertListMessagePrefix}" key="admin.dialog.expertAppend.selected.message2"/>";

	$jq("#expertAppendDlg span[name=selectedText]").text(message1 + " " + _options.length + message2);
};


//명칭으로 찾기
var searchUserDlg = function(event) {
	$jq("#expertAppendDlg input[name=searchName]").trigger("keypress");
};

//조직도 팝업
var searchAddressPopup = function() {
	var $sel = $jq("#expertAppendDlg select[name=selectedExpert]");
	iKEP.showAddressBook(showAddressBookCallback, "", {selectElement:$sel,selectType:"user",isAppend:true});
};

//선택된 항목삭제
var deleteSelectedItem = function() {
	var _selectedOptions = $jq("#expertAppendDlg select[name=selectedExpert] option:selected");
	_selectedOptions.remove();
	calcExpertSelectCount();
};

//등록
var registerItem = function () {
	var _options = $jq("#expertAppendDlg select[name=selectedExpert] option");

	var id = "";

	if (1 > _options.length) {
		alert("<ikep4j:message pre="${expertListMessagePrefix}" key="alert.expertAppendNotSelect"/>");
		return;
	}

	if (!confirm("<ikep4j:message pre="${expertListMessagePrefix}" key="alert.expertAppend"/>")) {
		return;
	}

	_options.each(function(index){id += this.value + ","});
	id = id.substring(0, id.length - 1);

	$jq("#expertForm input[name=addExpertId]").val(id);

	$jq.ajax({
		url : iKEP.getContextRoot() + "/collpack/expertnetwork/category/expertAppend.do",
		type : "post",
		data : $jq("#expertForm").serialize(),
		loadingElement : {button:"#btnPopDiv"},
		success : function(data, textStatus, jqXHR) {
			expertAppendDialogHide();
			pageReflashDB();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("<ikep4j:message pre="${commonPrefix}" key="message.fail"/>");
		}
	});
}

//취소
var cancelClose = function() {
	expertAppendDialogHide();
}

$jq(document).ready(function() {
	// 추가등록 팝업 생성
	$expertAppendDialog = $jq("#expertAppendDialog").dialog({modal:true, autoOpen:false, resizable:false, width:500});
});
//]]>
</script>
