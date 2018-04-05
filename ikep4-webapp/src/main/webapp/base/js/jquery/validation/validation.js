(function ($) {
	$.strPad = function(i, l, s, flag) {
		var o = i.toString();
		if (!s) { s = "0"; }
		
		if(flag == "right"){
			while (o.length < l) {
				o = o + s;
			}
		}else{
			while (o.length < l) {
				o = s + o;
			}
		}
		return o;
	};
	
	$.timeValid = function(obj){
		var timeValue = $.trim(obj.val()).replace(/:/g, "");
		
		if(timeValue != "000000"){
			if(isNaN(timeValue) || timeValue.length > 6){
				alert("유효한 시간이 아닙니다.");
				obj.val("00:00:00");
				obj.focus();
				return;
			}
		}
		
		timeValue = $.strPad(timeValue, 6, 0, "right");
		
		if(timeValue > 240000){
			alert("유효한 시간이 아닙니다.");
			obj.val("00:00:00");
			obj.focus();
			return;
		}
		
		obj.val(timeValue.substring(0,2)+":"+timeValue.substring(2,4)+":"+timeValue.substring(4,6));
	};
	
	$.amountValid = function(obj){
		var amountValue = $.trim(obj.val()).replace(/,/g, "");
		var maxlength = Number(obj.data("maxlength"));
		
		if(amountValue != ""){
			if(isNaN(amountValue)){
				alert("유효한 금액이 아닙니다.");
				obj.val("");
				obj.focus();
				return;
			}
			
			if(maxlength != NaN){
				if(amountValue.length > maxlength){
					alert("유효한 금액이 아닙니다.");
					obj.val("");
					obj.focus();
					return;
				}
			}
		}
		
		obj.val(amountValue.addComma());
	};
})(jQuery);