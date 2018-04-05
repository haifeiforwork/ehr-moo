<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="prefix" value="message.portal.portlet.webDictionary.normalView"/>
<style type="text/css">
	label.dic { padding-left:10px; display:inline-block; line-height:24px; }
	label.dic input { vertical-align:middle; margin-right:4px; }
</style>
<script type="text/javascript">
(function($) {
	var dicUrl = "http://dic.daum.net/search.do",
		$inputWord,
		$dicType;
	
	$(document).ready(function() {
		$inputWord = $("input[name=word]");
		$dicType = $("input[name=dicType]");
		
		var defaultDicType = $.cookie("dicType");
		if(!defaultDicType) $dicType.eq(0).attr("checked", true);
		else $dicType.each(function() {
				if($(this).val() == defaultDicType) {
					$(this).attr("checked", true);
					return false;
				}
			});
		
		$inputWord.keyup( function(event) {
    		if (event.which == 13) $("a.button_s").click();
		});
		
		$("a.button_s").click(function() {
			var dicType = $dicType.filter(":checked").val(),
				word = $inputWord.val();
			if(word) {
				var url = dicUrl + "?" + (dicType ? "dic="+dicType+"&" : "")
						+ "q=" + encodeURI(word);
				var winDaumDic = window.open(url, "winDaumDic", "width=400, height=500, scrollbars=yes, resizable=yes, location=no, toolbar=no, status=no, menubar=no");
				winDaumDic.focus();
				
				$.cookie("dicType", dicType||null, {expires:60*60*24*365});
			} else {
				alert('<ikep4j:message pre="${prefix}" key="alert.noSearchWord" />');
			}
		});
	});
})(jQuery);
</script>

<div class="pTitle_1">
	<ikep4j:message pre="${prefix}" key="title" />
</div>
<div>
	<p>
		<label class="dic"><input type="radio" name="dicType" value="" class="radio" /><ikep4j:message pre="${prefix}" key="searchType.all" /></label>
		<label class="dic"><input type="radio" name="dicType" value="kor" class="radio" /><ikep4j:message pre="${prefix}" key="searchType.korean" /></label>
		<label class="dic"><input type="radio" name="dicType" value="eng" class="radio" /><ikep4j:message pre="${prefix}" key="searchType.english" /></label>
		<label class="dic"><input type="radio" name="dicType" value="ee" class="radio" /><ikep4j:message pre="${prefix}" key="searchType.eenglish" /></label>
		<label class="dic"><input type="radio" name="dicType" value="hanja" class="radio" /><ikep4j:message pre="${prefix}" key="searchType.hanja" /></label>
		<label class="dic"><input type="radio" name="dicType" value="jp" class="radio" /><ikep4j:message pre="${prefix}" key="searchType.japanese" /></label>
		<label class="dic"><input type="radio" name="dicType" value="ch" class="radio" /><ikep4j:message pre="${prefix}" key="searchType.chinese" /></label>
	</p>
	<div class="dotline_1"></div>
	<p>
		<input name="word" class="inputbox" type="text" style="width: 80%;"/>
		<a class="button_s" href="#a"><span><ikep4j:message pre="${prefix}" key="search" /></span></a>
	</p>
</div>
<br/>