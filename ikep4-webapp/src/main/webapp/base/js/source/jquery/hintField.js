/*
 * hintField
 * hint의 유/무에 따라서 수정 화면 또는 등록 화면으로 분류해 사용한다.
 * hint가 없으면 초기 등록화면으로 간주하여 해당 element의 value값이 hint가 된다.
 * hint가 있으면 수정화면으로 보며, 단일 element에 대하여 해당 element의 등록시 입력값을 value로 입력하고, value가 삭제될때를 대비하여 hintField생성시 hint를 별도 전달 받는다.
 * 
 * 주의) hintField생성시 지정한 hint는 별도의 처리가 없을 경우 해당 element의 value로 들어 가게 되므로 데이타 전송시 반드시 이를 확인하여야 한다.
 */
(function($) {
	$.fn.hintField = function(settings) {		
		settings = jQuery.extend({
			className : "hintField",
			hint : ""
		}, settings);
		
		if(settings.hint) {
			var $el = $(this);
			
			$el.focusin(function() {
				if($el.val() == settings.hint)
					$el.val("").removeClass(settings.className);
			});
			
			$el.focusout(function() {
				if($el.val() == "")
					$el.val(settings.hint).addClass(settings.className);
			});
			
			$el.trigger("focusout");
			
			return $el;
		} else {
			return $(this).each(function() {		
				var $el = $(this);
				
				if($el.val()) {
					$el.addClass(settings.className)
						.data("hint", $el.val());
			
					$el.focusin(function() {			
						if($el.val() == $el.data("hint")) 
							$el.val("").removeClass(settings.className);
					});
					
					$el.focusout(function() {
						if($el.val() == "")
							$el.val($el.data("hint")).addClass(settings.className);
					});	
				}
			});
		}
	};
})(jQuery);