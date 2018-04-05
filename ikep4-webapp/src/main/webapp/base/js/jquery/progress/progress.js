(function ($) {
	$.callProgress = function(){
		
		$("#wrap").append("<div id=\"layer_back\"></div>");
		$("#wrap").append("<div id=\"layer_pop\"></div>");
	
		var windowHeight = $(window).height();
		var windowWidth = $(window).width();
		
		if($("#layer_back").length > 0){
		
			$("#layer_back").css(
				{ "position":"absolute"
				, "opacity": "0.1"
				, "top":0
				, "left":0
				, "width":windowWidth
				, "height":windowHeight
				, "z-index":1000
				, "background":"#000000"
				}
			);
		
		}
		
		var top = windowHeight / 2 - 35;
		var left = windowWidth / 2 - 35;
		
		if($("#layer_pop").length > 0){
			$("#layer_pop").css(
				{ position:"absolute"
				, "top":top
				, "left":left
				, "z-index":10000
				}
			);
		}
		
		$("#layer_pop").append("<img src='/base/images/ess/darkGray.gif' alt=''/>");
	};
	
	$.stopProgress = function(){
		$("#layer_back").remove();
		$("#layer_pop").remove();
	};
})(jQuery);