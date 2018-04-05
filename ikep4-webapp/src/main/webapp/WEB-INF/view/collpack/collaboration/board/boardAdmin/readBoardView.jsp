<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<c:set var="preHeader"  value="message.collpack.collaboration.board.boardAdmin.createBoardView" /> 
<c:set var="preDetail"  value="message.collpack.collaboration.board.boardAdmin.createBoardView.detail" />
<c:set var="preCode"    value="message.collpack.collaboration.common.code" />
<c:set var="preButton"  value="message.collpack.collaboration.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.board" />
<c:set var="preSearch"  value="message.collpack.collaboration.common.searchCondition" />

<script type="text/javascript">
<!-- 
(function($){	 
	$(document).ready(function() {
		//window.parent.topScroll(); 
		
		$("a[name=listBoardViewButton]").click(function() {  
		  	location.href = "<c:url value='/collpack/collaboration/board/boardAdmin/listBoardView.do?workspaceId=${workspaceId}&boardRootId=${board.boardRootId}'/>";
		}); 

		$("a[name=createBoardViewButton]").click(function() {  
		  	location.href = "<c:url value='/collpack/collaboration/board/boardAdmin/createBoardView.do?workspaceId=${workspaceId}&boardId=${board.boardId}'/>";
		}); 
		
		$("a[name=updateBoardViewButton]").click(function() {  
		  	location.href = "<c:url value='/collpack/collaboration/board/boardAdmin/updateBoardView.do?workspaceId=${workspaceId}&boardId=${board.boardId}'/>";
		});  
		
		$("a[name=deleteBoardButton]").click(function() {  
			
			if(confirm("<ikep4j:message pre='${preMessage}' key='delete'/>")) {
		  		location.href = "<c:url value='/collpack/collaboration/board/boardAdmin/deleteBoard.do?workspaceId=${workspaceId}&boardId=${board.boardId}'/>";
			}
		});  
		<c:if test="${leftBoardMenuReload}">parent.boardReload();</c:if>
		iKEP.iFrameContentResize();
		
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
	<table summary="<ikep4j:message pre='${preDetail}' key='summary' />" border=1> 
		<caption></caption>
		<col style="width: 12%;"/>
		<col style="width: 6%;"/>
		<col style="width: 32%;"/>
		<col style="width: 18%;"/>  
		<col style="width: 32%;"/> 
		<spring:bind path="board.parentList">
		<tr> 
			<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='boardPath' /></th>
			<td colspan="3"> <ikep4j:message pre='${preDetail}' key='path' /> >
				<c:forEach var="boardItem" varStatus="varStatus" items="${status.value}"> 
					<c:if test="${not varStatus.last}">
						${boardItem.boardName} >
					</c:if> 
					<c:if test="${varStatus.last}">${boardItem.boardName}</c:if> 
				</c:forEach> 
			</td> 
		</tr>				
		</spring:bind> 	
 					
		<spring:bind path="board.boardName">
		<tr> 
			<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3">${status.value}</td> 
		</tr>				
		</spring:bind> 

				

		<spring:bind path="board.description">
		<tr> 
			<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3">${status.value}</td> 
		</tr>				
		</spring:bind> 

				
		<spring:bind path="board.boardType">
		<tr> 
			<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3">
				<c:forEach var="code" items="${boardCode.boardTypeList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach>
			</td> 
		</tr>				
		</spring:bind>	
		


		<c:if test="${board.boardType eq 2 }">		
		<spring:bind path="board.url">
		<tr> 
			<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3">${status.value}
				<c:forEach var="code" items="${boardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach>
			<c:if test="${board.urlPopup eq 1 }">[<ikep4j:message pre="${preDetail}" key="urlPopup" />]</c:if>
			<c:if test="${board.urlPopup eq 0 }">[<ikep4j:message pre="${preDetail}" key="currentWindow" />]</c:if>
			</td> 
		</tr>				
		</spring:bind>  
		</c:if>
		
		<c:if test="${board.boardType eq 0}"> 
		<spring:bind path="board.listType">
		<tr> 
			<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3">
				<c:forEach var="code" items="${boardCode.listTypeList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach>
			</td> 
		</tr>				
		</spring:bind>	

				
		<spring:bind path="board.rss">		
		<tr> 
			
			<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td> 
				<c:forEach var="code" items="${boardCode.useUnuseList}">  
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach> 		
			</td> 
			</spring:bind>	
			
			<spring:bind path="board.anonymous">
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.useUnuseList}">  
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach> 				
			</td> 
			
		</tr>				
		</spring:bind> 
		
		<tr> 
			<spring:bind path="board.versionManage">
			<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td> 
				<c:forEach var="code" items="${boardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach> 	
			</td> 
			</spring:bind>  	
			
			<spring:bind path="board.wiki">
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach> 				
			</td> 
			</spring:bind>
		</tr>
		
		
		
		<tr> 
			<spring:bind path="board.pageNum">
			<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.pageNumList}">
					<c:if test="${code.key eq status.value}">${code.value}</c:if>
				</c:forEach>
			</td> 
			</spring:bind>
			
			<spring:bind path="board.docPopup">
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}'/></th> 
			<td> 
				<c:forEach var="code" items="${boardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach>  
			</td> 
			</spring:bind>				
		</tr>				
										
	
		<tr> 
			<spring:bind path="board.reply">
			<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}'/></th>
			<td>
				<c:forEach var="code" items="${boardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
			</td> 
			</spring:bind>
			
			<spring:bind path="board.linereply">
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach> 
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>				
			</td> 
						
			</spring:bind>	
		</tr>
		
		<tr> 
			<spring:bind path="board.fileSize">
			<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.fileSizeList}">
					<c:if test="${code.key eq status.value}">${code.value}</c:if>
				</c:forEach> 
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
			</td> 
			</spring:bind>	 						

			<spring:bind path="board.move">
		
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach> 
				
			</td> 
						
			</spring:bind>						 
		</tr>
		
		
		<tr> 
			<spring:bind path="board.imageFileSize">
			<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.imageFileSizeList}">
					<c:if test="${code.key eq status.value}">${code.value}</c:if>
				</c:forEach>
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>

			</td> 
			</spring:bind>	
		
			<spring:bind path="board.imageWidth">
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.imageWidthList}">
					<c:if test="${code.key eq status.value}">${code.value}</c:if>
				</c:forEach>
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
			</td> 
			</spring:bind>	 
		</tr>	
		

		<%--spring:bind path="board.restrictionType">
		<tr> 
			<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3">${status.value}</td> 
		</tr>				
		</spring:bind--%>	  

		<spring:bind path="board.fileAttachOption"> 
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3">
				<c:forEach var="code" items="${boardCode.attachFileOptionList}">   
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if> 
				</c:forEach>   			
			</td> 
		</tr>				
		</spring:bind>
		
		<spring:bind path="board.allowType">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3">${status.value}</td> 
		</tr>				
		</spring:bind>
		



		<spring:bind path="board.readPermission">
		<tr> 
			<th rowspan="2"  scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}'  post="readPermission" /></th>
			<td colspan="3">
				<div style="padding-bottom:4px;">
				<c:forEach var="code" items="${boardCode.permissionList}" varStatus="varStatus"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
				</div>
				
				<ul>
				<c:forEach var="user" items="${board.readUserList}">  
					<li>${user.userName} ${user.jobTitleName} ${user.teamName}</li> 
				</c:forEach>
				<br />
				<c:forEach var="group" items="${board.readGroupList}">  
					<li><c:if test="${group.hierarchied eq 1}">[H]</c:if>${group.groupName}</li>
				</c:forEach> 							
				</ul>
			</td>  
		</tr>				
		</spring:bind> 
		
		<spring:bind path="board.writePermission">
		<tr> 
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3">
				<div style="padding-bottom:4px;">
				<c:forEach var="code" items="${boardCode.permissionList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
				</div>
				
				<ul>
				<c:forEach var="user" items="${board.writeUserList}">  
					<li>${user.userName} ${user.jobTitleName} ${user.teamName}</li> 
				</c:forEach>
				<br />
				<c:forEach var="group" items="${board.writeGroupList}">  
					<li><c:if test="${group.hierarchied eq 1}">[H]</c:if>${group.groupName}</li>
				</c:forEach>				
				</ul>
			</td>  
		</tr>				
		</spring:bind> 
		
		
		<tr> 
			<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='alliance' /></th>
			<td colspan="3">
				<c:forEach var="allianceList" items="${allianceList}"> 
				<c:if test="${!empty allianceList.boardId}">
					${allianceList.workspaceName}</br>
				</c:if>
				</c:forEach>		
			</td>  
		</tr>				

		<tr>
			<th colspan="2" scope="row">&nbsp;<ikep4j:message pre='${preDetail}' key='tag' /></th>
			<td colspan="3">
			<c:forEach var="tag" items="${board.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if>${tag.tagName}</c:forEach>
			</td>
		</tr>
		
		</c:if>
				
		</tbody> 				



		<%--
		<c:if test="${board.boardType eq 0}"> 
			   

		<c:if test="${not empty board.readUserList or not empty board.readGroupList}"> 
			<tr> 
				<th scope="row" rowspan="2"><ikep4j:message pre='${preDetail}' key='readPermissionInput' /></th>
				<th scope="row"><ikep4j:message pre='${preDetail}' key='user' /></th> 
				<td>  
					<ul class="listStyle">
						<c:forEach var="user" items="${board.readUserList}">  
							<li>${user.userName} ${user.jobTitleName} (${user.teamName}, ${user.mail})</li> 
						</c:forEach> 
					</ul>
				</td>   
			</tr>
			<tr>  
				<th scope="row"><ikep4j:message pre='${preDetail}' key='group' /></th> 
				<td> 
					<ul class="listStyle">
						<c:forEach var="group" items="${board.readGroupList}">  
							<li><c:if test="${group.hierarchied eq 1}">[H]</c:if>${group.groupName}</li>
						</c:forEach>    
					</ul>
				</td>
			</tr> 
		</c:if>		
		<spring:bind path="board.writePermission">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.openClosedList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
			</td>  
		</tr>				
		</spring:bind> 
			<c:if test="${not empty board.writeUserList or not empty board.writeGroupList }">
			<tr> 
				<th scope="row" rowspan="2"><ikep4j:message pre='${preDetail}' key='writePermissionInput' /></th>
				<th scope="row"><ikep4j:message pre='${preDetail}' key='user' /></th> 
				<td>    
					<ul class="listStyle">
						<c:forEach var="user" items="${board.writeUserList}">  
							<li>${user.userName} ${user.jobTitleName} (${user.teamName}, ${user.mail})</li> 
						</c:forEach>  
					</ul>	 
				</td>   
			</tr>
			<tr>  
				<th scope="row"><ikep4j:message pre='${preDetail}' key='group' /></th> 
				<td> 
					<ul class="listStyle">
						<c:forEach var="group" items="${board.writeGroupList}">  
							<li><c:if test="${group.hierarchied eq 1}">[H]</c:if>${group.groupName}</li>
						</c:forEach>     
					</ul>	 
				</td>
			</tr> 	
			</c:if>	 
		</c:if>
		
		<tr>
			<th colspan="2" scope="row">&nbsp;<ikep4j:message pre='${preDetail}' key='tag' /></th>
			<td colspan="3">
			<c:forEach var="tag" items="${board.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if>${tag.tagName}</c:forEach>
			</td>
		</tr>						
		--%>
	</table> 
</div>
<!--//blockDetail End-->
<!--blockButton Start-->
<div class="blockButton" id="divButton"> 
	<ul> 
		<li><a name="updateBoardViewButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='update'/></span></a></li> 
		<li><a name="deleteBoardButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete' /></span></a></li>
		<li><a name="listBoardViewButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li> 
	</ul>
</div>
<!--//blockButton End--> 
