$jq(document).ready(function() {
	// element var
	var $mgmtForm 	= $jq("#mgmtForm");
	var $btn_save	= $jq("#btn_save");
	var $btn_delete = $jq("#btn_delete");
	var $trusteeId	= $jq("#trusteeId");
	var $mandatorId = $jq("#mandatorId");
	var $searchFld  = $jq("#search", "#mgmtForm");
	
	var createUrl = iKEP.getContextRoot() + "/lightpack/planner/mandate/create.do";
	var deleteUrl = iKEP.getContextRoot() + "/lightpack/planner/mandate/delete.do";
	var listUrl = iKEP.getContextRoot() + "/lightpack/planner/mandate/formView.do";
	var editUrl = iKEP.getContextRoot() + "/lightpack/planner/mandate/editForm.do";
	
	var $ikepPlanner = $jq("#ikepPlanner");

	$searchFld.focus();
	
	// bind event
	$btn_save.click(function() {
		if($trusteeId.val().length > 0) {
			$mgmtForm.attr("method", "post");
			$mgmtForm.attr("action", createUrl + ($ikepPlanner.length == 1 ? "" : "?mode=iframe"));
			$mgmtForm.submit();			
		} else {
			alert(iKEPLang.planner.messageText.checkTrustee);
		}
	});
	
	$btn_delete.click(function() {
		if(confirm(iKEPLang.planner.messageText.confirmDeleteMantator)) {
			$mgmtForm.attr("method", "post");
			$mgmtForm.attr("action", deleteUrl + ($ikepPlanner.length == 1 ? "" : "?mode=iframe"));
			$mgmtForm.submit();	
		}
	});
	
	$jq("#btn_list").click(function() {
		document.location.href = listUrl + ($ikepPlanner.length == 1 ? "" : "?mode=iframe"); 
	});

	$jq("#btn_edit, #btn_insert").click(function() {
		document.location.href = editUrl + ($ikepPlanner.length == 1 ? "" : "?mode=iframe");
	});
	
	$searchFld.keypress(function(event) {
		var that = this;
		iKEP.searchUser(event, "N", function( data ) {
			if(data && data.length > 0) {
				var user = data[0];
				$trusteeId.val(user.id);
				that.value = user.userName;
				$jq("#trusteeName").html(user.userName + ' ' + user.jobTitleName + ' ' + user.teamName);
			} else {
				alert(iKEPLang.planner.messageText.noResult);
			}
		});		
	});
	
	$jq("#btn_searchUser", "#mgmtForm").click(function(event) {
		$searchFld.trigger("keypress");	
	});
});