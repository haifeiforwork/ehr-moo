$jq(document).ready(function() {

	// element var
	var $btn_insert	  = $jq("#btn_insert");
	var $btn_list	  = $jq("#btn_list");
	var $mgmtForm	  = $jq("#mgmtForm");
	var $holidayDate  = $jq("#holidayDate");
	var $holidayId	  = $jq("#holidayId");
	
	var home_url = iKEP.getContextRoot() + "/";
	
	$holidayDate.datepicker({
		dateFormat: "yy.mm.dd",
		showOn: "both",
		buttonImageOnly: true,
		buttonImage: iKEP.getContextRoot() + "/base/images/icon/ic_cal.gif",
		onSelect : function() {
			$jq(this).trigger("focusout");
		}
	});

	$jq("#holidayName").focus();
	
	// bind event
	$btn_insert.click(function(){
		var params = {nation: $jq("select[name=nation]").val(), 
				holidayName: $jq.trim($jq("#holidayName").val())};

		$jq.getJSON("checkName.do", params, function(res) {
			if(res === "success") {				
				$mgmtForm.attr("method", "post");
				$mgmtForm.attr("action", home_url + "lightpack/planner/holiday/create.do");
				$mgmtForm.submit();
			} else {
				alert(iKEPLang.planner.errorText.duplicateName);
			}
		});
	});
	
	$btn_list.click(function() {
		document.location.href = home_url + "lightpack/planner/holiday/list.do";
	});
});