<%--
=====================================================
* 기능 설명 : 다이나믹 타일즈2 베이스 레이아웃 페이지
* 작성자 : 주길재
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%--=============================-->
<!--JSTL & Custom Tag Libray Area-->
<!--=============================--%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%-- script src="<c:url value="/JavaScriptServlet"/>"></script --%>
<%--타이틀--%>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="title"><tiles:getAsString name="title"/></c:set>
<title><spring:message code="${title}" /></title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/base/css/${user.userTheme.cssPath}/jquery_ui_custom.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/base/css/common.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/base/css/ess/ep_style.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/base/css/ess/ep_button.css" />

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/base/css/evaluation/ehr_addstyle.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/jquery-ui-1.8.13.min.without.datepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/jquery.ui.datepicker.customize.pack.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/jquery.jstree.pack.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/jquery.validate-1.8.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/plugins.pack.js"></script>
<script type="text/javascript">var contextRoot = "${pageContext.request.contextPath}";</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/etc.plugins.pack.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/langResource/${empty user.localeCode ? 'en' : user.localeCode}.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/common.pack.js"></script>
<script type="text/javascript">
if (typeof console === "undefined" || typeof console.log === "undefined") {
	console = {};
	console.log = function() {};
}

try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}

var AjaxError = (function() {
	"use strict";

	var _jqXHR = null;
	var _exception = null;
	var _message = {
		"0" : "Not connect.\n Verify Network.",
		"404" : "Requested page not found. [404]",
		"500" : "Internal Server Error [500]",
		"parsererror" : "Requested JSON parse failed",
		"timeout" : "Time out error",
		"abort" : "Ajax request aborted",
		"empty" : "Uncaught Error.\n"
	};

	var setData = function(jqXHR, exception) {
		_jqXHR = jqXHR;
		_exception = exception;
	}

	var getMessage = function() {
		var message;

		if(_jqXHR.status === 0 || _jqXHR.status == 404 || _jqXHR.status == 500) {
			message = _message[_jqXHR.status];
		} else {
			message = _message[_exception];
		}

		if(typeof message === 'undefined') message = _message.empty + _jqXHR.responseText;

		return message;
	}

	return {
		showAlert : function(jqXHR, exception) {
			setData(jqXHR, exception);
			alert(getMessage());
		}
	}
})();

(function($){

	jQuery.browser = {};
	(function () {
	    jQuery.browser.msie = false;
	    jQuery.browser.version = 0;
	    if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
	        jQuery.browser.msie = true;
	        jQuery.browser.version = RegExp.$1;
	    }
	})();

	$.fn.Rowspan = function(colIdx, isStats) {
		return this.each(function(){
			var that;
			$('tr', this).each(function(row) {
				$('th:eq('+colIdx+')', this).filter(':visible').each(function(col) {

					if ($(this).html() == $(that).html()
						&& (!isStats
								|| isStats && $(this).prev().html() == $(that).prev().html()
								)
						) {
						rowspan = $(that).attr("rowspan") || 1;
						rowspan = Number(rowspan)+1;

						$(that).attr("rowspan",rowspan);

						$(this).hide();
						//$(this).remove();

					} else {
						that = this;
					}

					that = (that == null) ? this : that;
				});
			});
		});
	};

	$.fn.Colspan = function(rowIdx) {
		return this.each(function() {

			var that;
			$('tr', this).filter(":eq(" + rowIdx + ")").each(function(row) {
				$(this).find('th').filter(':visible').each(function(col) {
					if ($(this).html() == $(that).html()) {
						colspan = $(that).attr("colSpan");
						if (colspan == undefined) {
							$(that).attr("colSpan", 1);
							colspan = $(that).attr("colSpan");
						}
						colspan = Number(colspan) + 1;
						$(that).attr("colSpan", colspan);
						$(this).hide();
					} else {
						that = this;
					}
					that = (that == null) ? this : that;
				});
			});

		});
	}

	jQuery.fn.SelectboxToInput = function() {
		var $this = $(this),
			$td = $this.closest('td'),
			_inputName = $this.attr('name'),
			_displayValue = $this.find('option:selected').text();

		$this.remove();
		$td.append('<input type="text" name="'+_inputName+'" class="width_100" value="'+_displayValue+'" disabled />');

		return this;
	}

	jQuery.fn.AutoHeightResizeTextarea = function(options) {
	    return this.filter('textarea').each(function() {
	        var self         = this;
	        var $self        = $(self);
	        var minHeight    = $self.height();
	        var noFlickerPad = $self.hasClass('autogrow-short') ? 0 : parseInt($self.css('lineHeight')) || 0;
	        var settings = $.extend({
	            preGrowCallback: null,
	            postGrowCallback: null
	          }, options );

	        var shadow = $('<div></div>').css({
	            position:    'absolute',
	            top:         -10000,
	            left:        -10000,
	            width:       $self.width(),
	            fontSize:    $self.css('fontSize'),
	            fontFamily:  $self.css('fontFamily'),
	            fontWeight:  $self.css('fontWeight'),
	            lineHeight:  $self.css('lineHeight'),
	            resize:      'none',
				'word-wrap': 'break-word'
	        }).appendTo(document.body);

	        var update = function(event) {
	            var times = function(string, number) {
	                for (var i=0, r=''; i<number; i++) r += string;
	                return r;
	            };

	            var val = self.value.replace(/&/g, '&amp;')
	                                .replace(/</g, '&lt;')
	                                .replace(/>/g, '&gt;')
	                                .replace(/\n$/, '<br/>&#xa0;')
	                                .replace(/\n/g, '<br/>')
	                                .replace(/ {2,}/g, function(space){ return times('&#xa0;', space.length - 1) + ' ' });

				if (event && event.data && event.data.event === 'keydown' && event.keyCode === 13) {
					val += '<br />';
				}

	            shadow.css('width', $self.width());
	            shadow.html(val + (noFlickerPad === 0 ? '...' : ''));

	            var newHeight=Math.max(shadow.height() + noFlickerPad, minHeight);
	            if(settings.preGrowCallback!=null){
	            	newHeight=settings.preGrowCallback($self,shadow,newHeight,minHeight);
	            }

	           	if($self.closest('tr').find('textarea:visible').length > 1) {
	           		var groupHeightArray = [];
	           		var isGroupResize = false;
	           		$self.closest('tr').find('textarea:visible').each(function() {
	           			var thisVal = this.value.replace(/&/g, '&amp;')
					                            .replace(/</g, '&lt;')
					                            .replace(/>/g, '&gt;')
					                            .replace(/\n$/, '<br/>&#xa0;')
					                            .replace(/\n/g, '<br/>')
					                            .replace(/ {2,}/g, function(space){ return times('&#xa0;', space.length - 1) + ' ' });

	           			shadow.css('width', $(this).width());
		                shadow.html(thisVal + (noFlickerPad === 0 ? '...' : ''));

		                groupHeightArray.push(shadow.height());

		                if(shadow.height() <= newHeight) {
	           				$(this).height(newHeight);
	           			} else {
	           				isGroupResize = true;
	           			}
	           		});

	           		if(isGroupResize) {
	               		var groupMaxHeight = Math.max.apply(null, groupHeightArray);

	               		$self.closest('tr').find('textarea').each(function() {
	               			$(this).height(groupMaxHeight);
	               		});
	           		}
	           	} else {
	           		$self.height(newHeight);
	           	}

	            if(settings.postGrowCallback!=null){
	            	settings.postGrowCallback($self);
	            }
	        }

	        $self.change(update).keyup(update).keydown({event:'keydown'},update);
	        $(window).resize(update);

	        update();
	    });
	}

	jQuery.CallPrintPagePopup = function(options) {
		var settings = $.extend({}, {
            title: 'NewDocument',
            form: 'ajaxForm'
        }, options || {});

		if(settings.form == null) return false;

		var print_window = window.open(
			'',
            settings.title,
            'height=800,width=1020,scrollbars=yes'
        );

		var printForm = document.forms[settings.form];
		printForm.target = settings.title;
		printForm.submit();

		print_window.focus();
	}

	jQuery.DisabledBackgroundStyleChange = function() {
		$('#wrap').find('input:disabled').each(function() { $(this).css('background', '#ffffff'); });
		$('#wrap').find('textarea:disabled').each(function() { $(this).css('background', '#ffffff'); });
	}

	jQuery.SumScore = function(target_name) {
		var sum = 0,
			_score;
		$('[name=' + target_name + ']').each(function() {
			_score = $(this).val();
			if(_score != '' && !isNaN(_score)) sum += parseFloat(_score);
		});

		return $.ScoreRound(sum, 1);
	}

	jQuery.ScoreRound = function(n, pos) {
		var num = n;
		var digits = Math.pow(10, pos);
		var num_check=/^[-|+]?\d+\.?\d*$/;
		if(num_check.test(num)) {
			num = Math.round(n * digits) / digits;
			num = num.toFixed(pos);
		}

		return num;
	}

	jQuery.CallListPage = function(options) {
		var settings = $.extend({
				SUBMIT_FORM : "ajaxForm",
				SUBMIT_ACTION : null,
				SUBMIT_METHOD : "POST",
				PARAM_ACTION : null,
				PARAM_PAGE_NUM : 1,
				PARAM_EMPNO : null,
				PARAM_USERID : null
        	}, options );

		var $targetForm = $("#" + settings.SUBMIT_FORM);

		$targetForm.empty();
		if(settings.PARAM_EMPNO) $targetForm.append("<input type=\"hidden\" name=\"empno\" value=\"" + settings.PARAM_EMPNO + "\"/>");
		if(settings.PARAM_USERID) $targetForm.append("<input type=\"hidden\" name=\"userId\" value=\"" + settings.PARAM_USERID + "\"/>");
		if(settings.PARAM_ACTION) $targetForm.append("<input type=\"hidden\" name=\"action\" value=\"" + settings.PARAM_ACTION + "\"/>");
		$targetForm.append("<input type=\"hidden\" name=\"currentPage\" value=\"" + settings.PARAM_PAGE_NUM + "\"/>");

		$targetForm.attr("target", '_self');
		$targetForm.attr("method", settings.SUBMIT_METHOD);
		$targetForm.attr("action", settings.SUBMIT_ACTION);
		$targetForm.submit();
	}

})(jQuery);

</script>
</head>
<body>
<!--wrapper Start-->
<a id="TOP"></a>
<div id="wrapper">
	<!--blockContainer Start-->
	<div id="blockContainer">
		<!--blockMain Start-->
		<div id="blockMain">
			<div id="mainContents_3">

				<tiles:insertAttribute name="body" />

			</div>
		</div>
		<!--blockMain End-->
	</div>
	<!--blockContainer End-->
</div>
<!--wrapper End-->
</body>
</html>