$jq(document).ready(function() {
	
	var home_url = iKEP.getContextRoot() + "/";
	var colors = [];
	var eventClassValuePrefix = "event_class_type";
	var eventClassTextPrefix = "type";
	var defaultClassName = "event_class_type1";
	
	// element var
	var $btn_insert	  = $jq("#btn_insert");
	var $btn_list	  = $jq("#btn_list");
	var $color_pane   = $jq("#color_pane"); 
	var $sample_color = $jq("#sample_color");
	var $mgmtForm	  = $jq("#mgmtForm");
	var $categoryColor = $jq("#categoryColor").val();
	
	$jq(".categoryName")[0].focus();
	
	function addOption(sel, value, text) {
		sel.append("<option value='" + value + "' " + (value == $categoryColor ? "selected" : "") + ">" + text + "</option>");		
	}
	
	for(var i=0, len=24; i<len; i++) {  // event_class_type25은 범주 중요에서 전용으로 사용//26은 범주없음에서 사용
		addOption($color_pane, eventClassValuePrefix+(i+1), eventClassTextPrefix+(i+1));
	}

	adjustClass($color_pane ? $color_pane.val() : defaultClassName);
	
	$btn_insert.click(function(){
		$jq.post("create.do", $jq("#mgmtForm").serialize(), function(res) {
			if(res === "success") {
				document.location.href = home_url + "lightpack/planner/category/list.do";
			} else if(res === "duplicate") {
				alert(iKEPLang.planner.errorText.duplicateCategoryName);
			}
		});
	});
	
	$btn_list.click(function() {
		document.location.href = home_url + "lightpack/planner/category/list.do";
	});
	
	$color_pane.bind("change focusout", function() {
		adjustClass(this.value);
	});

	$jq("#color_pane > option").mouseenter(function(){
		adjustClass(this.value);
	});
	
	function adjustClass(v) {
		$sample_color.removeClass()
		.addClass(v + " cal_color_box_sample1");
	}
});