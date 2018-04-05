$jq(document).ready(function() {
	
	// element var
	var $btn_check	 = $jq("#btn_check");
	var $btn_insert	 = $jq("#btn_insert");
	var $btn_delete	 = $jq("#btn_delete");
	var $item_list	 = $jq("#item_list");
	var $mgmtForm	 = $jq("#listForm");
	var $holiday_name = $jq(".holiday-name");
	
	var home_url = iKEP.getContextRoot() + "/";
	
	// bind event
	$btn_check.click(function() {
		$item_list.find("input:checkbox").attr("checked", $jq(this).is(":checked"));
	});
	
	$btn_insert.click(function(){
		document.location.href = home_url 
			+ "lightpack/planner/holiday/editForm.do?holidayId=";
	});
	
	$btn_delete.click(function() {
		if($jq("input[name=hid]:checked").size() == 0) {
			alert(iKEPLang.planner.messageText.selectHoliday);
			return;
		}
		if(confirm(iKEPLang.planner.messageText.confirmDeleteHoliday)) {			
			$mgmtForm.attr("method", "post");
			$mgmtForm.attr("action", home_url + "lightpack/planner/holiday/delete.do");
			$mgmtForm.submit();
		}
	});

	$holiday_name.css("cursor","pointer").css("cursor","hand")
	 .click(function() {
		 var hid = $jq(this).parent().find("input[name=hid]").val();
		 document.location.href = home_url + 
		 	"lightpack/planner/holiday/editForm.do?holidayId=" + hid;
	 });
	
});