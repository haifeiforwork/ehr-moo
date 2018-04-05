$jq(document).ready(function() {
	
	// element var
	var $btn_check	 = $jq("#btn_check");
	var $btn_insert	 = $jq("#btn_insert");
	var $btn_delete	 = $jq("#btn_delete");
	var $item_list	 = $jq("#item_list");
	var $mgmtForm	 = $jq("#listForm");
	var $editMode	 = $jq(".edit-mode");
	
	var home_url = iKEP.getContextRoot() + "/";
	
	// bind event
	$btn_check.click(function() {
		$item_list.find("input:checkbox").attr("checked", $jq(this).is(":checked"));
	});
	
	$btn_insert.click(function(){
		document.location.href = home_url + "lightpack/planner/category/editForm.do";
	});
	
	$btn_delete.click(function() {
		if($jq("input[name=cid]:checked").size() == 0) {
			alert(iKEPLang.planner.messageText.selectCategory);
			return;
		} 
		if(confirm(iKEPLang.planner.messageText.confirmDeleteCategory)) {
			$mgmtForm.attr("method", "post");
			$mgmtForm.attr("action", home_url + "lightpack/planner/category/delete.do");
			$mgmtForm.submit();
		}
	});
	
	$editMode.click(function(e) {
		document.location.href = home_url + "lightpack/planner/category/editForm.do?categoryId=" +
			$jq(this).parents("tr").find("[name=cid]").val();
	});
});