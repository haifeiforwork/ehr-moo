var goGuideItem,
	resizeIFreme;

(function($) {
	$(document).ready(function() {
		var href = document.location.href;
		var currElement = null;
		if(href.indexOf("#") > -1) {
			var url = "";
			url = href.substring(href.indexOf("#")+1, href.length);
			currElement = $("#guideLeftMenu li>a[href='" + url + "']");
		}
		
		if(currElement && currElement.length == 1 && currElement[0]) {
			$(currElement).parent().addClass("selected");
			goGuideItem(currElement);
		} else {
			var firstItem = $("#guideLeftMenu li>a").first().parent();
			if(firstItem) {
				firstItem.addClass("selected");
				goGuideItem(firstItem.find("a"));
			}
		}
		
		$("#guideLeftMenu li>a").live("click", function(event) {
			event.preventDefault();
			
			$("#guideLeftMenu li.selected").removeClass("selected");
			$(this).parent().addClass("selected");

			goGuideItem(this);
		});
	});

	goGuideItem = function (el) {
		var url = $.trim($(el).attr("href"));
		var iframeCon = $("#iframeCon");
		
		if(url && url != "#") {
			switch(url) {
				case "Main.html" :
				case "Main_portlet_setting.html" :
				case "Leftmenu.html" :
				case "login.html" :
					iframeCon.attr("src", "about:blank");
					window.open(url, "_blank");
					break;
				default :
					iframeCon.attr("src", url);
					if(location.protocol == "http:" || !jQuery.browser.msie)	// IE local 수행시 #url이 적용되지 않아서...
						location.href = location.pathname + "#" + url;
			}
		} else {
			iframeCon.attr("src", "about:blank");
		}

		iframeCon.height($("#guideLeftMenu").height());
	}

	resizeIFreme = function () {
		setTimeout(function() {
			var iframe = $("#iframeCon");
			var newHeight = iframe.contents().height() + 30;
//			if(newHeight < 830) {
//				newHeight = 830;
//			}
			
		  	iframe.height(newHeight);
		}, 10);
	}
})(jQuery);