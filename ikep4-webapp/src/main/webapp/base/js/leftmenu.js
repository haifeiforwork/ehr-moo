// left menu
(function($) {
$(document).ready(function() {
	$('#leftMenu li:not(.leftTree li)').children('a')
	.click(function(event) {
		var $thisParent = $(this).parent();
		var $this = $(this);
		
		//console.log($thisParent.attr('class'));
		
		$('#leftMenu li:not(.leftTree li)').removeClass('licurrent');
		$thisParent.parents('#leftMenu li').addClass('licurrent');
		$thisParent.addClass('licurrent');

		if($thisParent.hasClass('no_child') || $thisParent.find('li').size() === 0) {
			return false;
		}
		
		if($thisParent.children('ul:visible, div:visible').size() > 0) {
			$thisParent.children('ul, div').hide();
			$thisParent.removeClass('opened');
		} else {
			$thisParent.children('ul, div').show();
			$thisParent.addClass('opened');	
		}
	});
});
})(jQuery);