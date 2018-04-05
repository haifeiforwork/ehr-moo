<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.note.readFolderView.header" /> 
<c:set var="preDetail"  value="ui.support.note.readFolderView.detail" />
<c:set var="preButton"  value="ui.support.common.button" /> 
<c:set var="preMessage" value="message.support.common.note" />   
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 메시지 관련 Prefix 선언 End --%>   
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/note.css"/>" />
<script type="text/javascript">
<!-- 
(function($){	 
	$(document).ready(function() {
		window.parent.topScroll(); 
		
		$("a[name=listFolderViewButton]").click(function() {  
		  	location.href = "<c:url value='/support/note/listFolderView.do'/>";
		}); 
		
		$("a[name=updateFolderViewButton]").click(function() {  
		  	location.href = "<c:url value='/support/note/updateFolderView.do?folderId=${noteFolder.folderId}'/>";
		});  
		
		$("a[name=deleteFolderButton]").click(function() {
			if(confirm("<ikep4j:message pre='${preMessage}' key='delete'/>")) {
		  		location.href = "<c:url value='/support/note/deleteFolder.do?folderId=${noteFolder.folderId}'/>";
			}
		});  
		
		window.parent.resizeIframe();
		
	});	 
})(jQuery); 
//-->
</script>   
<!--pageTitle Start-->
<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
</div> 
<!--//pageTitle End--> 
	<!--blockDetail Start-->
<div class="blockDetail">  
	<table summary="<ikep4j:message pre='${preDetail}' key='summary' />"> 
		<caption></caption>
		<colgroup>
			<col style="width: 18%;"/>
			<col style="width: 82%;"/> 			
		</colgroup>  
		<spring:bind path="noteFolder.folderName">
		<tr> 
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>${status.value}</td> 
		</tr>				
		</spring:bind> 
		<c:if test="${noteFolder.folderType eq 0}">
			<spring:bind path="noteFolder.color">
			<tr> 
				<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" />
					<div><span class="note_color_box ${status.value}"></span></div>
					<c:forEach items="${status.errorMessages}" var="error">
			       		<label for="${status.expression}" class="serverError"> ${error}</label>
			  	   </c:forEach>  
				</td>  
			</tr>				
			</spring:bind>  
			<spring:bind path="noteFolder.defaultFlag">
			<tr> 
				<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>		
						<label>
						<c:if test="${1 eq status.value}">Y</c:if>
						<c:if test="${0 eq status.value}">N</c:if>
						</label>	
					</div>
					<c:forEach items="${status.errorMessages}" var="error">
			       		<label for="${status.expression}" class="serverError"> ${error}</label>
			  	   </c:forEach>  
				</td>  
			</tr>				
			</spring:bind>  
			<spring:bind path="noteFolder.shared">
			<tr> 
				<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<label>
						<c:if test="${'0' eq status.value}"><ikep4j:message pre='${preDetail}' key='sharedN' /></c:if>
						<c:if test="${'1' eq status.value}"><ikep4j:message pre='${preDetail}' key='sharedY' /></c:if>					
					</label>
				</td>  
			</tr>				
			</spring:bind> 
			<c:if test="${not empty noteFolder.sharedUserList}"> 
			<tr> 
				<th scope="row" rowspan="2"><ikep4j:message pre='${preDetail}' key='sharedPermissionInput' /></th>
				<td>  
					<ul class="listStyle">
						<c:forEach var="user" items="${noteFolder.sharedUserList}">  
							<li>${user.userName} ${user.jobTitleName} ${user.teamName}</li> 
						</c:forEach> 
					</ul>
				</td>   
			</tr>
			</c:if>		
		</c:if>
	</table> 
</div>
<!--//blockDetail End-->
<!--blockButton Start-->
<div class="blockButton" id="divButton"> 
	<ul> 
	 
		<li><a name="updateFolderViewButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='update'/></span></a></li> 
		<li><a name="deleteFolderButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete' /></span></a></li>
		<li><a name="listFolderViewButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
	</ul>
</div>
<!--//blockButton End--> 
