<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="preHeader"  value="ui.lightpack.board.boardAdmin.readBoardView" /> 
<c:set var="preDetail"  value="ui.lightpack.board.boardAdmin.readBoardView.detail" />
<c:set var="preDetail2"  value="ui.lightpack.board.boardAdmin.createBoardView.detail" />
<c:set var="preButton"  value="ui.lightpack.common.button" /> 
<c:set var="preMessage" value="message.lightpack.common.boardAdmin" />   
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
  
<script type="text/javascript">
<!-- 
(function($){	 
	$(document).ready(function() {
		window.parent.topScroll(); 
		
		$("a[name=listBoardViewButton]").click(function() {  
		  	location.href = "<c:url value='/lightpack/board/boardAdmin/listBoardView.do?boardRootId=${board.boardRootId}'/>";
		}); 

		$("a[name=createBoardViewButton]").click(function() {  
		  	location.href = "<c:url value='/lightpack/board/boardAdmin/createBoardView.do?boardId=${board.boardId}'/>";
		}); 
		
		$("a[name=updateBoardViewButton]").click(function() {  
		  	location.href = "<c:url value='/lightpack/board/boardAdmin/updateBoardView.do?boardId=${board.boardId}'/>";
		});  
		
		$("a[name=deleteBoardButton]").click(function() {
			if(confirm("<ikep4j:message pre='${preMessage}' key='delete'/>")) {
		  		location.href = "<c:url value='/lightpack/board/boardAdmin/deleteBoard.do?boardId=${board.boardId}'/>";
			}
		});  
		
		window.parent.resizeIframe();
		
	});	 
})(jQuery); 
//-->
</script>   
<!--pageTitle Start-->
<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="title" /></h2> 
</div> 
<!--//pageTitle End--> 
	<!--blockDetail Start-->
<div class="blockDetail">  
	<table summary="<ikep4j:message pre='${preDetail}' key='summary' />"> 
		<caption></caption>
		<colgroup>
			<col style="width: 10%;"/>
			<col style="width: 8%;"/>
			<col style="width: 82%;"/> 			
		</colgroup>  
		<spring:bind path="board.parentList">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='boardPath' /></th>
			<td>
				<c:forEach var="boardItem" varStatus="varStatus" items="${status.value}"> 
					<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}">${boardItem.boardName}</c:when>
						<c:otherwise>${boardItem.boardEnglishName}</c:otherwise>
					</c:choose>
					<c:if test="${not varStatus.last}"> ></c:if> 
				</c:forEach> 
			</td> 
		</tr>				
		</spring:bind> 			 	
		<spring:bind path="board.boardName">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>${status.value}</td> 
		</tr>				
		</spring:bind> 
		<spring:bind path="board.boardEnglishName">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>${status.value}</td> 
		</tr>				
		</spring:bind> 
		<c:if test="${board.boardType eq 0 or board.boardType eq 1 }">
		<spring:bind path="board.boardDescription">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>${status.value}</td> 
		</tr>				
		</spring:bind> 
		<spring:bind path="board.boardEnglishDescription">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>${status.value}</td> 
		</tr>				
		</spring:bind>  
		</c:if>
		<c:if test="${board.boardType eq 1 }">
		<spring:bind path="board.url">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>${status.value}</td> 
		</tr>				
		</spring:bind>
		<spring:bind path="board.boardPopup">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.useUnuseList}">  
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach> 				
			</td> 
		</tr>				
		</spring:bind> 		 	 
		</c:if>
		
		
		<c:if test="${board.boardType eq 2}"> 
		<spring:bind path="board.readPermission">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.openClosedList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
			</td>  
		</tr>				
		</spring:bind> 
			<c:if test="${not empty board.readUserList or not empty board.readGroupList or not empty board.readRoleList}"> 
			<tr> 
				<th scope="row" rowspan="3"><ikep4j:message pre='${preDetail}' key='readPermissionInput' /></th>
				<th scope="row"><ikep4j:message pre='${preDetail}' key='user' /></th> 
				<td>  
					<ul class="listStyle">
						<c:forEach var="user" items="${board.readUserList}">  
							<li>${user.userName} ${user.jobTitleName} ${user.teamName}</li> 
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
			<tr>  
				<th scope="row">역할</th> 
				<td> 
					<ul class="listStyle">
						<c:forEach var="role" items="${board.readRoleList}">  
							<li>${role.roleName}</li>
						</c:forEach>    
					</ul>
				</td>
			</tr> 
			</c:if>		
		</c:if>	
		
		<c:if test="${board.boardType eq 0}"> 
		<spring:bind path="board.listType">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.listTypeList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach>
			</td> 
		</tr>				
		</spring:bind>	 
		<spring:bind path="board.anonymous">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.useUnuseList}">  
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach> 				
			</td> 
		</tr>				
		</spring:bind> 
		<spring:bind path="board.rss">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td> 
				<c:forEach var="code" items="${boardCode.useUnuseList}">  
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach> 		
			</td> 
		</tr> 
		</spring:bind>  
		<spring:bind path="board.fileSize">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.fileSizeList}">
					<c:if test="${code.key eq status.value}">${code.value}</c:if>
				</c:forEach> 
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
			</td> 
		</tr>				
		</spring:bind>	 
		<spring:bind path="board.imageFileSize">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.imageFileSizeList}">
					<c:if test="${code.key eq status.value}">${code.value}</c:if>
				</c:forEach>
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
			</td> 
		</tr>				
		</spring:bind>	
		
		<spring:bind path="board.imageWidth">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.imageWidthList}">
					<c:if test="${code.key eq status.value}">${code.value}</c:if>
				</c:forEach>
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
			</td> 
			
		</tr>				
		</spring:bind>	 
		<spring:bind path="board.pageNum">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.pageNumList}">
					<c:if test="${code.key eq status.value}">${code.value}</c:if>
				</c:forEach>
			</td> 
		</tr>				
		</spring:bind>	 
		<spring:bind path="board.docPopup">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}'/></th> 
			<td> 
				<c:forEach var="code" items="${boardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach>  
			</td> 
		</tr>				
		</spring:bind>	 
		<spring:bind path="board.reply">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}'/></th>
			<td>
				<c:forEach var="code" items="${boardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
			</td> 
		</tr>				
		</spring:bind>	
		<spring:bind path="board.linereply">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
			</td> 
		</tr>				
		</spring:bind>	
		<spring:bind path="board.contentsReadPermission">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail2}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
				<span class="tdInstruction"></span>
			</td> 
		</tr>				
		</spring:bind>	
		<spring:bind path="board.contentsReadMail">
		<tr> 
			<th scope="row" colspan="2">독서자 메일 발송</th>
			<td>
				<c:forEach var="code" items="${boardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
				<span class="tdInstruction"></span>
			</td> 
		</tr>				
		</spring:bind>	
		<%/* %>
		<spring:bind path="board.portlet">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<div>
				<c:forEach var="code" items="${boardCode.yesNoList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
				</div>
			</td> 
		</tr>				
		</spring:bind>	 
		<spring:bind path="board.restrictionType">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>${status.value}</td> 
		</tr>				
		</spring:bind>
		  
		<spring:bind path="board.move"> 
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach> 
				
			</td> 	
		</tr>				
		</spring:bind>	
		<%*/ %>	 
		<spring:bind path="board.wordHead"> 
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach> 
				
			</td> 	
		</tr>				
		</spring:bind>	
		<spring:bind path="board.fileAttachOption"> 
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.attechFileOptionList}">   
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if> 
				</c:forEach>   			
			</td> 
		</tr>				
		</spring:bind>	
		<spring:bind path="board.allowType">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>${status.value}</td> 
		</tr>				
		</spring:bind> 
		<spring:bind path="board.readPermission">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${boardCode.openClosedList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
			</td>  
		</tr>				
		</spring:bind> 
		<c:if test="${not empty board.readUserList or not empty board.readGroupList or not empty board.readRoleList}"> 
			<tr> 
				<th scope="row" rowspan="3"><ikep4j:message pre='${preDetail}' key='readPermissionInput' /></th>
				<th scope="row"><ikep4j:message pre='${preDetail}' key='user' /></th> 
				<td>  
					<ul class="listStyle">
						<c:forEach var="user" items="${board.readUserList}">  
							<li>${user.userName} ${user.jobTitleName} ${user.teamName}</li> 
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
			<tr>  
				<th scope="row">역할</th> 
				<td> 
					<ul class="listStyle">
						<c:forEach var="role" items="${board.readRoleList}">  
							<li>${role.roleName}</li>
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
			<c:if test="${not empty board.writeUserList or not empty board.writeGroupList or not empty board.writeRoleList}">
			<tr> 
				<th scope="row" rowspan="3"><ikep4j:message pre='${preDetail}' key='writePermissionInput' /></th>
				<th scope="row"><ikep4j:message pre='${preDetail}' key='user' /></th> 
				<td>    
					<ul class="listStyle">
						<c:forEach var="user" items="${board.writeUserList}">  
							<li>${user.userName} ${user.jobTitleName} ${user.teamName}</li> 
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
			<tr>  
				<th scope="row">역할</th> 
				<td> 
					<ul class="listStyle">
						<c:forEach var="role" items="${board.writeRoleList}">  
							<li>${role.roleName}</li>
						</c:forEach>    
					</ul>
				</td>
			</tr> 	
			</c:if>	 
			<spring:bind path="board.adminPermission">
			<tr> 
				<th scope="row" colspan="2">관리자 권한</th>
				<td>
					<c:forEach var="code" items="${boardCode.openClosedList}"> 
						<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
					</c:forEach> 
				</td>  
			</tr>				
			</spring:bind> 
				<c:if test="${not empty board.adminUserList or not empty board.adminGroupList or not empty board.adminRoleList}">
				<tr> 
					<th scope="row" rowspan="3">관리자 권한 설정</th>
					<th scope="row"><ikep4j:message pre='${preDetail}' key='user' /></th> 
					<td>    
						<ul class="listStyle">
							<c:forEach var="user" items="${board.adminUserList}">  
								<li>${user.userName} ${user.jobTitleName} ${user.teamName}</li> 
							</c:forEach>  
						</ul>	 
					</td>   
				</tr>
				<tr>  
					<th scope="row"><ikep4j:message pre='${preDetail}' key='group' /></th> 
					<td> 
						<ul class="listStyle">
							<c:forEach var="group" items="${board.adminGroupList}">  
								<li><c:if test="${group.hierarchied eq 1}">[H]</c:if>${group.groupName}</li>
							</c:forEach>     
						</ul>	 
					</td>
				</tr> 
				<tr>  
					<th scope="row">역할</th> 
					<td> 
						<ul class="listStyle">
							<c:forEach var="role" items="${board.adminRoleList}">  
								<li>${role.roleName}</li>
							</c:forEach>    
						</ul>
					</td>
				</tr> 	
			</c:if>	
			<spring:bind path="board.mobileUse">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<c:forEach var="code" items="${boardCode.useUnuseList}"> 
						<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
					</c:forEach> 
				</td>  
			</tr>	
			</spring:bind>
			<spring:bind path="board.mobileWriteUse">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<c:forEach var="code" items="${boardCode.useUnuseList}"> 
						<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
					</c:forEach> 
				</td>  
			</tr>	
			</spring:bind>
		</c:if>
	</table> 
</div>
<!--//blockDetail End-->
<!--blockButton Start-->
<div class="blockButton" id="divButton"> 
	<ul> 
		<li><a name="listBoardViewButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li> 
		<li><a name="updateBoardViewButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='update'/></span></a></li> 
		<li><a name="deleteBoardButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete' /></span></a></li>
	</ul>
</div>
<!--//blockButton End--> 
