<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="prefix" value="message.portal.portlet.webDictionary.maxView"/>

<script type="text/javascript">
   <!--
   (function($) {
	    $jq(document).ready(function() {
	    	
	    	$jq('#search_English').keyup( function(event) {
	    		if (event.which == '13' || event.which === undefined) {
	    			searchEnglish();
	    		}
			});
	    	
	    	$jq('#search_Chinese').keyup( function(event) {
	    		if (event.which == '13' || event.which === undefined) {
	    			searchChinese();
	    		}
			});
	    });
	})(jQuery);
   
   function searchEnglish()
   {
   	var url;
   	
   		if(checkWord(document.getElementById("search_English").value))
    	{
    	  var type = document.getElementsByName("searchType");
    		if(type[1].checked)
    		{
    			url='http://engdic.daum.net/dicen/search.do?m=word&endic_kind=all&q=';
    		}
    		else if(type[2].checked)
    		{
    			url='http://engdic.daum.net/dicen/search.do?m=idiom&endic_kind=all&q=';
    		}
    		else if(type[3].checked)
    		{
    			url='http://engdic.daum.net/dicen/search.do?m=exam&endic_kind=all&q=';
    		}
    		else
    		{
    			url='http://engdic.daum.net/dicen/search.do?endic_kind=all&m=all&q=';
    		}
    		var xPos=(window.screen.width) ? (window.screen.width-1000)/2 : 0;
	 		var yPos=(window.screen.height) ? (window.screen.height-768-60)/2 : 0;
   	 		var searchWord=document.getElementById("search_English").value;
   	  		var win = window.open(encodeURI(url+searchWord),"_daumEng","width=1000,height=768,left="+xPos+",top="+yPos+",location=no,toolbar=no,status=no,scrollbars=yes,menubar=no,resizable=yes");
	     	win.focus();
   	 	}
   		else
   		{
   			alert('<ikep4j:message pre="${prefix}" key="alert.noSearchWord" />');
   		}

   }
  
    function searchChinese()
   {
   	var url='http://cndic.daum.net/index.html?search=yes&lang=all&q=';
    	
   		if(checkWord(document.getElementById("search_Chinese").value))
    	{
    		var xPos=(window.screen.width) ? (window.screen.width-1000)/2 : 0;
	 			var yPos=(window.screen.height) ? (window.screen.height-768-60)/2 : 0;
   	 		var searchWord=document.getElementById("search_Chinese").value;
   	  	var win = window.open(encodeURI(url+searchWord),"_daumChi","width=1000,height=768,left="+xPos+",top="+yPos+",location=no,toolbar=no,status=no,scrollbars=yes,menubar=no,resizable=yes");
	     	win.focus();
   	 	}
   		else
   		{
   			alert('<ikep4j:message pre="${prefix}" key="alert.noSearchWord" />')
   		}
   }
   
   
   
   	 function checkWord(word)
     {
	   	 if(word==null || word=='')
	   	 {
	   	 	return false;
	   	 }
	   	 else
	   	 {
	   	 	return true;
	   	 }
   	 }
	// -->
</script>

<div class="pTitle_1">
	<ikep4j:message pre="${prefix}" key="title.englishMessage" />
</div>
<table width="100%" summary="<ikep4j:message pre="${prefix}" key="title.englishMessage" />">
	<caption></caption>
	<tbody>
		<tr>
			<td>
				<input id="search_English" class="inputbox" type="text" size="41" />
				<a class="button_s" href="#a" onclick="javascript:searchEnglish();"><span><ikep4j:message pre="${prefix}" key="search" /></span></a>
			</td>
		</tr>
		<tr>
			<td style="padding-top: 5px;">
				<input type="radio" name="searchType" id="searchType"  value="1" checked="checked" class="radio" onfocus='this.blur();' />&nbsp;<ikep4j:message pre="${prefix}" key="searchType.all" />
				<input type="radio" name="searchType"  id="searchType"  value="2" class="radio" onfocus='this.blur();' />&nbsp;<ikep4j:message pre="${prefix}" key="searchType.word" />&nbsp;
				<input type="radio" name="searchType"  id="searchType"  value="3" class="radio" onfocus='this.blur();' />&nbsp;<ikep4j:message pre="${prefix}" key="searchType.idiom" />&nbsp;
				<input type="radio" name="searchType"  id="searchType"  value="4" class="radio" onfocus='this.blur();' />&nbsp;<ikep4j:message pre="${prefix}" key="searchType.example" />				
			</td>
		</tr>
	</tbody>
</table>
<div class="dotline_1"></div>
<div class="pTitle_1">
	<ikep4j:message pre="${prefix}" key="title.chinaMessage" />
</div>								
<table width="100%" summary="<ikep4j:message pre="${prefix}" key="title.chinaMessage" />">
	<caption></caption>
	<tbody>
		<tr>
			<td>
				<input id="search_Chinese" class="inputbox" type="text" size="41" />
				<a class="button_s" href="#a" onclick="javascript:searchChinese();"><span><ikep4j:message pre="${prefix}" key="search" /></span></a>		
			</td>
		</tr>
		<tr>
			<td>
				<span class="tdInstruction">※ <ikep4j:message pre="${prefix}" key="searchMessage" /></span>
			</td>
		</tr>
	</tbody>
</table>