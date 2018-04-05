(function ($) {
	String.prototype.addComma = function(point) {	// 문자형 숫자에 콤마 붙이기
		var result = "";
		if(isNaN(this)) {
			result = "NaN";
		} else {
			var sign = "";
			point = point || 3;
			var number = jQuery.trim(this).clearComma();
			
			if(isNaN(number.substring(0, 1))) {
				sign = number.substring(0, 1);
				number = number.substring(1);
			}
		
			
			var intergers = number.split(".");
			
			var integer = "";
			for(var i=0; i<intergers[0].length; i++) {
				if(i > 1 && ((i+1) % point) == 1) integer = "," + integer;
				integer = intergers[0].substr(intergers[0].length-i-1, 1) + integer;
			}
				 
			result = sign + integer;
			if(intergers.length > 1) result += "." + intergers[1];
		}
		
		return result;
	};
	String.prototype.clearComma = function (isNumber) { // 콤마 없애기
		var result = this.replaceAll(",", "");
		
		if(isNumber && !isNaN(result)) result = Number(result);
		
		 return result;
	};

	String.prototype.replaceAll = function(val, replVal) {
		var reg = new RegExp(val, "g");
		return this.replace(reg, replVal);
	};
})(jQuery);