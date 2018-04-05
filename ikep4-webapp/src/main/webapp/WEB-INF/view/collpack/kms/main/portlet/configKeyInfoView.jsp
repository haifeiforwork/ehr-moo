<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/base/common/taglibs.jsp"%>  
<c:set var="preList"   value="ui.lightpack.board.portlet.recentBoard" /> 
<c:set var="preButton" value="ui.lightpack.common.button" /> 
<script>
(function($){	 
	$(document).ready(function() { 
		$("#updateKeyInfoPageCount").click(function() { 
		    $.post('<c:url value="/collpack/kms/main/portlet/updateKeyInfoPageCount.do"/>', $("#configKeyInfoForm").serialize()) 
		    .success(function(data) {
		    	$jq("#configKeyInfoForm${portletConfigId}").parent().parent().parent().trigger("click:reload");
		    	alert('<ikep4j:message pre='${preList}' key='updateSuccess' />');
		    })
		    .error(function(event, request, settings) { 
		    	alert('<ikep4j:message pre='${preList}' key='updateError' />');
		    	
		    });  
		});
	});  

})(jQuery);	
</script> 
<form id="configKeyInfoForm">
<div class="poEdit">
	<div>
		<ikep4j:message pre='${preList}' key='selectPageCount' />:
		<select title="<ikep4j:message pre='${preList}' key='selectPageCount' />" name="count">
			<option value="3"  <c:if test="${count eq 3}">selected="selected"</c:if>>3</option>
			<option value="5"  <c:if test="${count eq 5}">selected="selected"</c:if>>5</option>
			<option value="7"  <c:if test="${count eq 7}">selected="selected"</c:if>>7</option>
			<option value="10" <c:if test="${count eq 10}">selected="selected"</c:if>>10</option>
		</select>
	</div>
	<div class="textRight"><a href="#a" id="updateKeyInfoPageCount" class="button_s"><span><ikep4j:message pre='${preButton}' key='save' /></span></a></div>
</div>
</form>