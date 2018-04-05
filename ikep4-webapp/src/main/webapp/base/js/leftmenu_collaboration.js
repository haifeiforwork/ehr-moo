// left menu collaboration
(function($) {
$(document).ready(function() {
	$(".leftMenu_coll .coll_menu a").click(function() {
		var $thisParent = $(this).parent();
		$thisParent.siblings('.selected').removeClass('selected');
		$thisParent.addClass('selected');
	});
	
	$(".leftMenu_coll > ul a").click(function() {
		var $thisParent = $(this).parent();
		
		$('.leftMenu_coll > ul .licurrent').removeClass('licurrent');
		$thisParent.parents('.leftMenu_coll li').addClass('licurrent');
		$thisParent.addClass('licurrent');
		
		if($thisParent.children('ul').size() === 0) {
			return false;
		}		
		
		if($thisParent.children('ul:visible').size() > 0) {
			$thisParent.children('ul, div').hide();
			$thisParent.removeClass('opened');
		} else {
			$thisParent.children('ul, div').show();
			$thisParent.addClass('opened');	
		}
	});
	
	$(".leftMenu_coll > .coll_box a").click(function() {
		$(this).parents('tbody').find('span.selected').removeClass('selected');
		$(this).parents('tr').find('span').addClass('selected');
	});
});
})(jQuery);