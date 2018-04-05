<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="preList"   value="ui.lightpack.board.portlet.markBoard.configView" /> 
<c:set var="preButton" value="ui.lightpack.common.button" /> 
<script type="text/javascript">
//<![CDATA[
(function($){	 
	$(document).ready(function() {    

		
		$("#updatePortlet").click(function() {
			if($("input.boardId:checked").length == 0){
				alert('<ikep4j:message pre='${preList}' key='selectBoard' />');
				return;
			}
		    $.post('<c:url value="/lightpack/board/portlet/markBoard/updatePortlet.do"/>', $("#configForm").serialize()) 
		    .success(function(data) {
		    	alert('<ikep4j:message pre='${preList}' key='updateSuccess' />');
		    	$("#portlet-lightpack-publicboard").parent().parent().parent().parent().trigger("click:reload"); 
		    })
		    .error(function(event, request, settings) { 
		    	alert('<ikep4j:message pre='${preList}' key='updateError' />');
		    	
		    }); 
		});
	}); 

})(jQuery);
//]]>
</script>
<form id="configForm">
	<input name="portletConfigId" type="hidden" value="${portletConfigId}">
	<div id="portlet-lightpack-publicboard" class="poEdit">
		<div class="pTitle_1">
			<c:forEach var="board" items="${boardList}" varStatus="status">
				<c:if test="${board.viewCount ne null }">
					<c:set var="setcount" value="${board.viewCount}" />
				</c:if>
			</c:forEach>
			<ikep4j:message pre='${preList}' key='selectPageCount' />
			: <select
				title="<ikep4j:message pre='${preList}' key='selectPageCount' />"
				name="viewCount">
				<option value="3" <c:if test="${setcount eq 3}">selected</c:if>>3</option>
				<option value="5" <c:if test="${setcount eq 5}">selected</c:if>>5</option>
				<option value="7" <c:if test="${setcount eq 7}">selected</c:if>>7</option>
				<option value="10" <c:if test="${setcount eq 10}">selected</c:if>>10</option>
			</select>
		</div>
		<div class="pTitle_1">
			<ikep4j:message pre='${preList}' key='title' />
		</div>
		<div>
			<table summary="<ikep4j:message pre="${preList}" key="summary" />">
				<caption></caption>
				<c:forEach var="board" items="${boardList}" varStatus="status">
					<tr>
						<td><input class="boardId" name="boardId" type="checkbox"
							class="checkbox" value="${board.boardId}"
							<c:if test="${board.portlet gt 0}">checked="checked"</c:if> />
							${board.boardName}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="textRight">
			<a href="#a" id="updatePortlet" class="button_s"><span><ikep4j:message	pre='${preButton}' key='save' /></span></a>
		</div>
</form>
</div>