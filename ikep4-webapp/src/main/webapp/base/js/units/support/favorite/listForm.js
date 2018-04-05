var getList,
	getListAndClear,
	initialBody;

(function($) {
	getList = function(isScrollSet) {
		$("#emptyDiv").hide();
		
		$.ajax({     
			url : $("#searchForm").attr("action"),     
			data :  $("#searchForm").serialize(),     
			type : "post",     
			loadingElement : {button:"#moreDiv"},
			success : function(result) {  
				$("#listDiv").append(result);
				if(isScrollSet) setScroll();
			},
			error : function(event, request, settings) { alert("error"); }
		});  
	};
	
	getListAndClear = function() {
		$("#moreDiv").hide();
		$("#emptyDiv").hide();
		$("#pageIndex").val(1);
		
		$("#currentCount").val("0");
		
		$.ajax({     
			url : $("#searchForm").attr("action"),     
			data :  $("#searchForm").serialize(),     
			type : "post",     
			loadingElement : {container:"#blockMain"}, 
			success : function(result) {  
				$("#listDiv").children().remove();
				$("#listDiv").append(result);
			},
			error : function(event, request, settings) { alert("error"); }
		});  
	};
	
	initialBody = function() {
		var $ul = $("div.listInfo ul");
		
		$("#listDiv").children().remove();
		getListAndClear();

		$ul.find("a").click(function() {
			var $li = $(this).parent();
			var $input = $li.find("input");

			if($li.hasClass("on")) {
				$li.removeClass("on");
				$input.attr("disabled", true);
			} else {
				$li.addClass("on");
				$input.attr("disabled", false);
			}
			
			getListAndClear();
		});
		
		$("#searchWord").keypress(function(event) { 
			if(event.which == 13) {
				getListAndClear();
			}
		}); 
	};
})(jQuery);