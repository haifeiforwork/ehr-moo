<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="preHeader"  value="ui.support.searchpreprocessor.header" /> 
<c:set var="preButton"  value="ui.support.searchpreprocessor.common.button" /> 
<c:set var="preLeft"  value="ui.support.searchpreprocessor.common.left" /> 
<c:set var="preFiled"  value="message.support.searchpreprocessor.field" /> 
<c:set var="preRequired"  value="message.support.searchpreprocessor" /> 
<ikep4j:message pre="${preFiled}" key="searchKeyword" var="searchKeyword"/>
<ikep4j:message pre="${preFiled}" key="date" var="date"/>

<script type="text/javascript" src="<c:url value='/base/js/searchpreprocessor/searchpreprocessor.js'/>"></script>
<script type="text/javascript">
<!--   
<c:choose>
<c:when test="${user.localeCode == portal.defaultLocaleCode}">
 var myLocale =true;
</c:when>
<c:otherwise>
var myLocale =false; 
</c:otherwise>
</c:choose>
//-->
</script> 
<script type="text/javascript">
<!--
(function($) {
	$(document).ready(function() {
		var validOptions = {
			    rules : {
			    	searchKeyword :    {
			            required : true
			        }
			    },
			    messages : {
			    	searchKeyword : {
			            direction : "bottom",
			            required : "<ikep4j:message pre="${preRequired}" key="required" arguments="${searchKeyword}"/>" 
			        }
			    },
			    notice : {
			    	searchKeyword : "${searchKeyword}"
			    },
			    submitHandler : function(form) {
			    	var searchKeyword = form.searchKeyword.value;
			    	//alert(searchKeyword);
					$.post('<c:url value="/support/searchpreprocessor/searchhistory/related/ajax.do"/>',
					 	{'searchKeyword': searchKeyword})
					 .success(function(data) {
						 $("#viewRow").empty();
						 $(data).each(function(){
							 $.tmpl("rowtemplet",this).appendTo( $("#viewRow") );
						 });
						 
						 if( data.length <= 0 )
						 {
							$('<tr class="msg_unread"><td scope="col"><ikep4j:message key="ui.support.searchpreprocessor.emptyRecord" /></td></tr>').appendTo( $("#viewRow") );
						 } 	 
					 })
					 .error(function(event, request, settings) { alert("error"); }); 
				}
			 };

		new iKEP.Validator("#searchForm", validOptions);
		
		$("#searchBtn").click(function(){
			$("#searchForm").trigger("submit");
		});	
		
		$.template("rowtemplet", '<tr><th class="ellipsis" width="*" scope="row"><a href="#a" onclick="searchCall(\'${searchKeyword}\');">\${searchKeyword}</a></th></tr>');
	});
})(jQuery);
//-->
</script> 
<h1 class="none"><ikep4j:message pre="${preLeft}" key="4" /></h1> 
<!--pageTitle Start-->

	
<!--//pageTitle End-->

<div class="subTitle_3">
	<h3><ikep4j:message pre="${preHeader}" key="subTitle.4" /></h3>
</div>

<div class="pre_search_form">
	<div class="pre_search_input">
		<div class="pre_search_inputbox">
		<form name="searchForm" id="searchForm" onsubmit="return false" action="/support/searchpreprocessor/searchhistory/related/ajax.do">
			<input name="searchKeyword" id="searchKeyword" title="<ikep4j:message pre="${preFiled}" key="inputKeyword" />" class="inputbox" type="text"/>
		</form>	
		</div>
		<span><a class="pre_btn_1" href="#a"  id="searchBtn"><span><ikep4j:message pre="${preFiled}" key="search" /></span></a></span>	
	</div>
</div>

<!--blockLeft Start-->
<div class="blockLeft">
	
	<div class="tableList_2 mb15">
		<table summary="<ikep4j:message pre="${preField}" key="relatedsearch" />">
			<caption></caption>
			<tbody id="viewRow">
				<tr class="msg_unread">
					<td scope="col"><ikep4j:message key="ui.support.searchpreprocessor.emptyRecord" /></td>
				</tr>
			</tbody>
		</table>	
	</div>
					
</div>
