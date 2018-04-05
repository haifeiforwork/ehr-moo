<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="preHeader"  value="ui.lightpack.award.awardAdmin.readAwardView" /> 
<c:set var="preDetail"  value="ui.lightpack.award.awardAdmin.readAwardView.detail" />
<c:set var="preDetail2"  value="ui.lightpack.award.awardAdmin.createAwardView.detail" />
<c:set var="preButton"  value="ui.lightpack.common.button" /> 
<c:set var="preMessage" value="message.lightpack.common.awardAdmin" />   
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
  
<script type="text/javascript">
<!-- 
(function($){	 
	$(document).ready(function() {
		window.parent.topScroll(); 
		
		$("a[name=listAwardViewButton]").click(function() {  
		  	location.href = "<c:url value='/lightpack/award/awardAdmin/listAwardView.do?awardRootId=${award.awardRootId}'/>";
		}); 

		$("a[name=createAwardViewButton]").click(function() {  
		  	location.href = "<c:url value='/lightpack/award/awardAdmin/createAwardView.do?awardId=${award.awardId}'/>";
		}); 
		
		$("a[name=updateAwardViewButton]").click(function() {  
		  	location.href = "<c:url value='/lightpack/award/awardAdmin/updateAwardView.do?awardId=${award.awardId}'/>";
		});  
		
		$("a[name=deleteAwardButton]").click(function() {
			if(confirm("<ikep4j:message pre='${preMessage}' key='delete'/>")) {
		  		location.href = "<c:url value='/lightpack/award/awardAdmin/deleteAward.do?awardId=${award.awardId}'/>";
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
		<spring:bind path="award.parentList">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='awardPath' /></th>
			<td>
				<c:forEach var="awardItem" varStatus="varStatus" items="${status.value}"> 
					<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}">${awardItem.awardName}</c:when>
						<c:otherwise>${awardItem.awardEnglishName}</c:otherwise>
					</c:choose>
					<c:if test="${not varStatus.last}"> ></c:if> 
				</c:forEach> 
			</td> 
		</tr>				
		</spring:bind> 			 	
		<spring:bind path="award.awardName">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>${status.value}</td> 
		</tr>				
		</spring:bind> 
		<spring:bind path="award.awardEnglishName">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>${status.value}</td> 
		</tr>				
		</spring:bind> 
		<c:if test="${award.awardType eq 0 or award.awardType eq 1 }">
		<spring:bind path="award.awardDescription">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>${status.value}</td> 
		</tr>				
		</spring:bind> 
		<spring:bind path="award.awardEnglishDescription">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>${status.value}</td> 
		</tr>				
		</spring:bind>  
		</c:if>
		<c:if test="${award.awardType eq 1 }">
		<spring:bind path="award.url">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>${status.value}</td> 
		</tr>				
		</spring:bind>
		<spring:bind path="award.awardPopup">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${awardCode.useUnuseList}">  
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach> 				
			</td> 
		</tr>				
		</spring:bind> 		 	 
		</c:if>
		
		
		<c:if test="${award.awardType eq 2}"> 
		<spring:bind path="award.readPermission">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${awardCode.openClosedList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
			</td>  
		</tr>				
		</spring:bind> 
			<c:if test="${not empty award.readUserList or not empty award.readGroupList or not empty award.readRoleList}"> 
			<tr> 
				<th scope="row" rowspan="3"><ikep4j:message pre='${preDetail}' key='readPermissionInput' /></th>
				<th scope="row"><ikep4j:message pre='${preDetail}' key='user' /></th> 
				<td>  
					<ul class="listStyle">
						<c:forEach var="user" items="${award.readUserList}">  
							<li>${user.userName} ${user.jobTitleName} ${user.teamName}</li> 
						</c:forEach> 
					</ul>
				</td>   
			</tr>
			<tr>  
				<th scope="row"><ikep4j:message pre='${preDetail}' key='group' /></th> 
				<td> 
					<ul class="listStyle">
						<c:forEach var="group" items="${award.readGroupList}">  
							<li><c:if test="${group.hierarchied eq 1}">[H]</c:if>${group.groupName}</li>
						</c:forEach>    
					</ul>
				</td>
			</tr> 
			<tr>  
				<th scope="row">역할</th> 
				<td> 
					<ul class="listStyle">
						<c:forEach var="role" items="${award.readRoleList}">  
							<li>${role.roleName}</li>
						</c:forEach>    
					</ul>
				</td>
			</tr> 
			</c:if>		
		</c:if>	
		
		<c:if test="${award.awardType eq 0}"> 
		<spring:bind path="award.listType">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${awardCode.listTypeList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach>
			</td> 
		</tr>				
		</spring:bind>	 
		<spring:bind path="award.anonymous">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${awardCode.useUnuseList}">  
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach> 				
			</td> 
		</tr>				
		</spring:bind> 
		<spring:bind path="award.rss">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td> 
				<c:forEach var="code" items="${awardCode.useUnuseList}">  
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach> 		
			</td> 
		</tr> 
		</spring:bind>  
		<spring:bind path="award.fileSize">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${awardCode.fileSizeList}">
					<c:if test="${code.key eq status.value}">${code.value}</c:if>
				</c:forEach> 
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
			</td> 
		</tr>				
		</spring:bind>	 
		<spring:bind path="award.imageFileSize">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${awardCode.imageFileSizeList}">
					<c:if test="${code.key eq status.value}">${code.value}</c:if>
				</c:forEach>
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
			</td> 
		</tr>				
		</spring:bind>	
		
		<spring:bind path="award.imageWidth">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${awardCode.imageWidthList}">
					<c:if test="${code.key eq status.value}">${code.value}</c:if>
				</c:forEach>
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
			</td> 
			
		</tr>				
		</spring:bind>	 
		<spring:bind path="award.pageNum">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${awardCode.pageNumList}">
					<c:if test="${code.key eq status.value}">${code.value}</c:if>
				</c:forEach>
			</td> 
		</tr>				
		</spring:bind>	 
		<spring:bind path="award.docPopup">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}'/></th> 
			<td> 
				<c:forEach var="code" items="${awardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach>  
			</td> 
		</tr>				
		</spring:bind>	 
		<spring:bind path="award.reply">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}'/></th>
			<td>
				<c:forEach var="code" items="${awardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
			</td> 
		</tr>				
		</spring:bind>	
		<spring:bind path="award.linereply">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${awardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
			</td> 
		</tr>				
		</spring:bind>	
		<spring:bind path="award.contentsReadPermission">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail2}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${awardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
				<span class="tdInstruction"></span>
			</td> 
		</tr>				
		</spring:bind>	
		<spring:bind path="award.contentsReadMail">
		<tr> 
			<th scope="row" colspan="2">독서자 메일 발송</th>
			<td>
				<c:forEach var="code" items="${awardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
				<span class="tdInstruction"></span>
			</td> 
		</tr>				
		</spring:bind>	
		<%/* %>
		<spring:bind path="award.portlet">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<div>
				<c:forEach var="code" items="${awardCode.yesNoList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
				</div>
			</td> 
		</tr>				
		</spring:bind>	 
		<spring:bind path="award.restrictionType">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>${status.value}</td> 
		</tr>				
		</spring:bind>
		  
		<spring:bind path="award.move"> 
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${awardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach> 
				
			</td> 	
		</tr>				
		</spring:bind>	
		<%*/ %>	 
		<spring:bind path="award.wordHead"> 
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${awardCode.useUnuseList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach> 
				
			</td> 	
		</tr>				
		</spring:bind>	
		<spring:bind path="award.fileAttachOption"> 
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${awardCode.attechFileOptionList}">   
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if> 
				</c:forEach>   			
			</td> 
		</tr>				
		</spring:bind>	
		<spring:bind path="award.allowType">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>${status.value}</td> 
		</tr>				
		</spring:bind> 
		<spring:bind path="award.readPermission">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${awardCode.openClosedList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
			</td>  
		</tr>				
		</spring:bind> 
		<c:if test="${not empty award.readUserList or not empty award.readGroupList or not empty award.readRoleList}"> 
			<tr> 
				<th scope="row" rowspan="3"><ikep4j:message pre='${preDetail}' key='readPermissionInput' /></th>
				<th scope="row"><ikep4j:message pre='${preDetail}' key='user' /></th> 
				<td>  
					<ul class="listStyle">
						<c:forEach var="user" items="${award.readUserList}">  
							<li>${user.userName} ${user.jobTitleName} ${user.teamName}</li> 
						</c:forEach> 
					</ul>
				</td>   
			</tr>
			<tr>  
				<th scope="row"><ikep4j:message pre='${preDetail}' key='group' /></th> 
				<td> 
					<ul class="listStyle">
						<c:forEach var="group" items="${award.readGroupList}">  
							<li><c:if test="${group.hierarchied eq 1}">[H]</c:if>${group.groupName}</li>
						</c:forEach>    
					</ul>
				</td>
			</tr> 
			<tr>  
				<th scope="row">역할</th> 
				<td> 
					<ul class="listStyle">
						<c:forEach var="role" items="${award.readRoleList}">  
							<li>${role.roleName}</li>
						</c:forEach>    
					</ul>
				</td>
			</tr> 
		</c:if>		
		<spring:bind path="award.writePermission">
		<tr> 
			<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:forEach var="code" items="${awardCode.openClosedList}"> 
					<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
				</c:forEach> 
			</td>  
		</tr>				
		</spring:bind> 
			<c:if test="${not empty award.writeUserList or not empty award.writeGroupList or not empty award.writeRoleList}">
			<tr> 
				<th scope="row" rowspan="3"><ikep4j:message pre='${preDetail}' key='writePermissionInput' /></th>
				<th scope="row"><ikep4j:message pre='${preDetail}' key='user' /></th> 
				<td>    
					<ul class="listStyle">
						<c:forEach var="user" items="${award.writeUserList}">  
							<li>${user.userName} ${user.jobTitleName} ${user.teamName}</li> 
						</c:forEach>  
					</ul>	 
				</td>   
			</tr>
			<tr>  
				<th scope="row"><ikep4j:message pre='${preDetail}' key='group' /></th> 
				<td> 
					<ul class="listStyle">
						<c:forEach var="group" items="${award.writeGroupList}">  
							<li><c:if test="${group.hierarchied eq 1}">[H]</c:if>${group.groupName}</li>
						</c:forEach>     
					</ul>	 
				</td>
			</tr> 
			<tr>  
				<th scope="row">역할</th> 
				<td> 
					<ul class="listStyle">
						<c:forEach var="role" items="${award.writeRoleList}">  
							<li>${role.roleName}</li>
						</c:forEach>    
					</ul>
				</td>
			</tr> 	
			</c:if>	 
			<spring:bind path="award.adminPermission">
			<tr> 
				<th scope="row" colspan="2">관리자 권한</th>
				<td>
					<c:forEach var="code" items="${awardCode.openClosedList}"> 
						<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>	
					</c:forEach> 
				</td>  
			</tr>				
			</spring:bind> 
				<c:if test="${not empty award.adminUserList or not empty award.adminGroupList or not empty award.adminRoleList}">
				<tr> 
					<th scope="row" rowspan="3">관리자 권한 설정</th>
					<th scope="row"><ikep4j:message pre='${preDetail}' key='user' /></th> 
					<td>    
						<ul class="listStyle">
							<c:forEach var="user" items="${award.adminUserList}">  
								<li>${user.userName} ${user.jobTitleName} ${user.teamName}</li> 
							</c:forEach>  
						</ul>	 
					</td>   
				</tr>
				<tr>  
					<th scope="row"><ikep4j:message pre='${preDetail}' key='group' /></th> 
					<td> 
						<ul class="listStyle">
							<c:forEach var="group" items="${award.adminGroupList}">  
								<li><c:if test="${group.hierarchied eq 1}">[H]</c:if>${group.groupName}</li>
							</c:forEach>     
						</ul>	 
					</td>
				</tr> 
				<tr>  
					<th scope="row">역할</th> 
					<td> 
						<ul class="listStyle">
							<c:forEach var="role" items="${award.adminRoleList}">  
								<li>${role.roleName}</li>
							</c:forEach>    
						</ul>
					</td>
				</tr> 	
			</c:if>	
			<spring:bind path="award.mobileUse">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<c:forEach var="code" items="${awardCode.useUnuseList}"> 
						<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
					</c:forEach> 
				</td>  
			</tr>	
			</spring:bind>
			<spring:bind path="award.mobileWriteUse">
			<tr> 
				<th scope="row" colspan="2"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<c:forEach var="code" items="${awardCode.useUnuseList}"> 
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
		<li><a name="listAwardViewButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li> 
		<li><a name="updateAwardViewButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='update'/></span></a></li> 
		<li><a name="deleteAwardButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete' /></span></a></li>
	</ul>
</div>
<!--//blockButton End--> 
