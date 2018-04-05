<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="preList"   value="ui.lightpack.board.portlet.publicBoard" /> 
<c:set var="preButton" value="ui.lightpack.common.button" /> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
//<![CDATA[
(function($){	 
	$(document).ready(function() {    
		$("#${portletConfigId} input.boardId").click(function() {  
			if($(this).is(":checked") == false) {
				
			} else if($("#${portletConfigId} input.boardId:checked").length < 4) {
				$(this).attr("checked", true);
			
			} else {
				return false; 
			}
		});
		
		$("#updatePortlet").click(function() {
			if($("#${portletConfigId} input.boardId:checked").length == 0){
				alert('<ikep4j:message pre='${preList}' key='selectBoard' />');
				return;
			}
		    $.post('<c:url value="/lightpack/board/portlet/publicBoard/updatePortlet.do"/>', $("#configForm").serialize()) 
		    .success(function(data) {
		    	alert('<ikep4j:message pre='${preList}' key='updateSuccess' />');
		    	$("#portlet-lightpack-publicboard").parent().parent().parent().trigger("click:reload"); 
		    })
		    .error(function(event, request, settings) { 
		    	alert('<ikep4j:message pre='${preList}' key='updateError' />');
		    	
		    }); 
		});
	}); 

})(jQuery);
//]]>
</script> 
<div id="${portletConfigId}">
<div id="portlet-lightpack-publicboard" class="poEdit"> 
<div class="pTitle_1"><ikep4j:message pre='${preList}' key='title' /></div> 
<form id="configForm">
	<div>
		<table summary="<ikep4j:message pre="${preList}" key="summary" />">
			<caption></caption> 
			<c:forEach var="board" items="${boardList}" varStatus="status"> 
				<tr>
					<td>
						<input class="boardId" name="boardId" type="checkbox"  class="checkbox"  value="${board.boardId}" <c:if test="${board.portlet eq 1}">checked="checked"</c:if>/>
						 <c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}">${board.boardName}</c:when>
							<c:otherwise>${board.boardEnglishName}</c:otherwise>
						</c:choose>
					</td> 
				</tr>	 
			</c:forEach>				    																																	
		</table>	
	</div>	
	<span class="tdInstruction"><span class="colorPoint"><ikep4j:message pre='${preList}' key='help' /></span></span>					
	<div class="textRight"><a href="#a" id="updatePortlet" class="button_s"><span><ikep4j:message pre='${preButton}' key='save' /></span></a></div>
</form>
</div>
</div>