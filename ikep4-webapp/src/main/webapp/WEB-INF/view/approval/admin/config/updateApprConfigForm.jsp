<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.approval.admin.config.form.header" />
<c:set var="preForm"  	value="ui.approval.admin.config.form" />
<c:set var="preButton"  value="ui.approval.common.button" />
<c:set var="preMessage" value="ui.approval.common.message" />
<c:set var="preIcon"  	value="ui.approval.common.icon" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>

<script type="text/javascript">
	<!--
	
	(function($){
		
		setIsReadAllTbl = function(){
			if($("input[name=isReadAll]:checked").val() == '1') {
				$("#isReadAllTbl").show();
			}
			else {
				$("#isReadAllTbl").hide();
			}
		};
		
		setUserData = function(){
			var $sel = $jq("select[name=userIdList]").children().remove().end();
			
			<c:forEach var="user" items="${apprAdminConfig.userList}"> 
				var addrObj = $.parseJSON('{"type":"user","id":"${user.userId}","userName":"${user.userName}","jobTitleName":"${user.jobTitleName}","teamName":"${user.teamName}"}');
				$.tmpl(iKEP.template.userBoardOption, addrObj).appendTo($sel).data("data", addrObj);
			</c:forEach>
		};
		
		setUser = function(data) {  
			
			var $sel = $jq("select[name=userIdList]");
			
			$.each(data, function(index, value) {  
				if($("#userIdList option[value=" + value.id + "]").length == 0) {
					$.tmpl(iKEP.template.userBoardOption, this).appendTo($sel).data("data", this);   
				} 
			});   
			
		};
		
		setUserAddress = function(data) {  
			
			var $sel = $jq("select[name=userIdList]").children().remove().end();
			
			$.each(data, function(index, value) {  
					$.tmpl(iKEP.template.userBoardOption, this).appendTo($sel).data("data", this);
			});
			
		};
		
		selectedAll = function (selectName){
			var $sel = $jq("select[name="+selectName+"]");
			
			if($sel) {
				$jq.each($sel.children(), function() {
					this.selected = true;
				});
			}
		};
		
		$(document).ready(function(){
			
			$jq("#updateApprConfigLinkOfLeft").click();
			
			$("#saveAdminConfigButton").click(function() {   
				if (!confirm("<ikep4j:message pre='${preMessage}' key='save'/>")) return false;
				
				selectedAll("userIdList");
				$("#myForm")[0].submit();
			});
			
			$("input[name=isReadAll]").click( function() {  
				setIsReadAllTbl();
			});
			
			$("#addUserButton").click( function() {
				
				var items = [];
				$jq("select[name=userIdList]").children().each(function() {
					items.push($(this).data("data"));
				});
				
				iKEP.showAddressBook(setUserAddress, items, {selectType:"user", tabs:{}});
			});
			
			$("#userName").keypress( function(event) {  
				iKEP.searchUser(event, "Y", setUser); 
			}); 
	        
			$("#searchUserButton").click( function() {  
				$("#userName").trigger("keypress");
			});
			
			$("#deleteUserButton").click( function() {  
				$("#userIdList option:selected").remove();
			}); 
		
			
			setIsReadAllTbl();
			
			setUserData();
		    
		});
	})(jQuery);
	//-->
</script>

<h1 class="none"><ikep4j:message pre="${preHeader}" key="title" /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
	<%-- <div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.3depth" /></li>
			<li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.4depth" /></li>
		</ul>
	</div> --%>
</div>
<!--//pageTitle End-->

<form id="myForm" name="myForm" method="post" action="<c:url value='/approval/admin/config/adminConfigSetSave.do'/>" onsubmit="return false;">

<spring:bind path="apprAdminConfig.configId">
	<input type="hidden" name="${status.expression}" value="${apprAdminConfig.configId}"/>
</spring:bind>

	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${preForm}' key='summary'/>">
			<caption></caption>
			<tbody>
				<tr>
					
					<th scope="row" width="25%"><ikep4j:message pre='${preForm}' key='apprType'/></th>
					<td>
					
						<input type="checkbox" class="checkbox" id="appr" name="appr"  value="1" checked="true" disabled="disabled"
						title="<ikep4j:message pre='${preForm}' key='apprType.appr'/>" /> <ikep4j:message pre='${preForm}' key='apprType.appr'/>
						<br/>
						<input type="checkbox" class="checkbox" id="seq" name="seq"  value="1" checked="true" disabled="disabled"
						title="<ikep4j:message pre='${preForm}' key='apprType.seq'/>" /> <ikep4j:message pre='${preForm}' key='apprType.seq'/>
						<br/>
						<input type="checkbox" class="checkbox" id="par" name="par"  value="1" checked="true" disabled="disabled"
						title="<ikep4j:message pre='${preForm}' key='apprType.par'/>" /> <ikep4j:message pre='${preForm}' key='apprType.par'/>
						<br/>
						<spring:bind path="apprAdminConfig.isReference">
							<input type="checkbox" class="checkbox" id="${status.expression}" name="${status.expression}"  value="1"
							<c:if test="${apprAdminConfig.isReference == 1}">checked="true"</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/> <ikep4j:message pre='${preForm}' key='${status.expression}'/>
						</spring:bind>
<!-- 						<br/> -->
<%-- 						<spring:bind path="apprAdminConfig.isReservation"> --%>
<%-- 							<input type="checkbox" class="checkbox" id="${status.expression}" name="${status.expression}"  value="1" --%>
<%-- 							<c:if test="${apprAdminConfig.isReservation == 1}">checked="true"</c:if> --%>
<%-- 							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>" --%>
<%-- 							/> <ikep4j:message pre='${preForm}' key='${status.expression}'/> --%>
<%-- 						</spring:bind> --%>
<!-- 						<br/> -->
<%-- 						<spring:bind path="apprAdminConfig.isArbitraryDecision"> --%>
<%-- 							<input type="checkbox" class="checkbox" id="${status.expression}" name="${status.expression}"  value="1" --%>
<%-- 							<c:if test="${apprAdminConfig.isArbitraryDecision == 1}">checked="true"</c:if> --%>
<%-- 							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>" --%>
<%-- 							/> <ikep4j:message pre='${preForm}' key='${status.expression}'/> --%>
<%-- 						</spring:bind> --%>
<!-- 						<br/> -->
<%-- 						<spring:bind path="apprAdminConfig.isPostApproval"> --%>
<%-- 							<input type="checkbox" class="checkbox" id="${status.expression}" name="${status.expression}"  value="1" --%>
<%-- 							<c:if test="${apprAdminConfig.isPostApproval == 1}">checked="true"</c:if> --%>
<%-- 							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>" --%>
<%-- 							/> <ikep4j:message pre='${preForm}' key='${status.expression}'/> --%>
<%-- 						</spring:bind> --%>
					</td>
					
				</tr>
				<tr>
					<spring:bind path="apprAdminConfig.isPassword">	
					<th scope="row" width="25%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<c:forEach var="code" items="${usageList}">
							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}"
							<c:if test="${code.key eq apprAdminConfig.isPassword or apprAdminConfig.isPassword eq null}">checked="true"</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach>
					</td>
					</spring:bind> 
				</tr>
				<tr>
					<spring:bind path="apprAdminConfig.isUseSign">	
					<th scope="row" width="25%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<c:forEach var="code" items="${usageList}">
							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}"
							<c:if test="${code.key eq apprAdminConfig.isUseSign or apprAdminConfig.isUseSign eq null}">checked="true"</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach>
					</td>
					</spring:bind> 
				</tr>
				<tr>
					<spring:bind path="apprAdminConfig.isOverall">	
					<th scope="row" width="25%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<c:forEach var="code" items="${usageList}">
							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}"
							<c:if test="${code.key eq apprAdminConfig.isOverall or apprAdminConfig.isOverall eq null}">checked="true"</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach>
					</td>
					</spring:bind> 
				</tr>
				<tr>
					<spring:bind path="apprAdminConfig.isExam">	
					<th scope="row" width="25%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<c:forEach var="code" items="${usageList}">
							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}"
							<c:if test="${code.key eq apprAdminConfig.isExam or apprAdminConfig.isExam eq null}">checked="true"</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach>
					</td>
					</spring:bind> 
				</tr>
				<tr>
					<spring:bind path="apprAdminConfig.isRefMsg">	
					<th scope="row" width="25%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<c:forEach var="code" items="${usageList}">
							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}"
							<c:if test="${code.key eq apprAdminConfig.isRefMsg or apprAdminConfig.isRefMsg eq null}">checked="true"</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach>
					</td>
					</spring:bind> 
				</tr>
				<tr>
					<spring:bind path="apprAdminConfig.isCancel">	
					<th scope="row" width="25%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<c:forEach var="code" items="${usageList}">
							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}"
							<c:if test="${code.key eq apprAdminConfig.isCancel or apprAdminConfig.isCancel eq null}">checked="true"</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach>
					</td>
					</spring:bind> 
				</tr>
				<tr>
					<spring:bind path="apprAdminConfig.isUpdateLine">	
					<th scope="row" width="25%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<c:forEach var="code" items="${usageList}">
							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}"
							<c:if test="${code.key eq apprAdminConfig.isUpdateLine or apprAdminConfig.isUpdateLine eq null}">checked="true"</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach>
					</td>
					</spring:bind> 
				</tr>
				<tr>
					<spring:bind path="apprAdminConfig.isUpdateDoc">	
					<th scope="row" width="25%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<c:forEach var="code" items="${usageList}">
							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}"
							<c:if test="${code.key eq apprAdminConfig.isUpdateDoc or apprAdminConfig.isUpdateDoc eq null}">checked="true"</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach>
					</td>
					</spring:bind> 
				</tr>
				<tr>
					<spring:bind path="apprAdminConfig.isUpdateRead">	
					<th scope="row" width="25%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<c:forEach var="code" items="${usageList}">
							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}"
							<c:if test="${code.key eq apprAdminConfig.isUpdateRead or apprAdminConfig.isUpdateRead eq null}">checked="true"</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach>
					</td>
					</spring:bind> 
				</tr>
				<tr>
					<spring:bind path="apprAdminConfig.isReceive">	
					<th scope="row" width="25%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<c:forEach var="code" items="${usageList}">
							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}"
							<c:if test="${code.key eq apprAdminConfig.isReceive or apprAdminConfig.isReceive eq null}">checked="true"</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach>
					</td>
					</spring:bind> 
				</tr>
				<tr>
					<spring:bind path="apprAdminConfig.receptionType">	
					<th scope="row" width="25%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<c:forEach var="code" items="${chargeList}">
							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}"
							<c:if test="${code.key eq apprAdminConfig.receptionType}">checked="true"</c:if>
							<c:if test="${apprAdminConfig.receptionType eq null}">
							<c:if test="${code.key eq '0'}">
								checked="true"
							</c:if>
							</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach>
					</td>
					</spring:bind> 
				</tr>
<!-- 				<tr> -->
<%-- 					<spring:bind path="apprAdminConfig.isPreView">	 --%>
<%-- 					<th scope="row" width="25%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th> --%>
<!-- 					<td> -->
<%-- 						<c:forEach var="code" items="${usageList}"> --%>
<%-- 							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}" --%>
<%-- 							<c:if test="${code.key eq apprAdminConfig.isPreView or apprAdminConfig.isPreView eq null}">checked="true"</c:if> --%>
<%-- 							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>" --%>
<!-- 							/> -->
<%-- 							<spring:message code="${code.value}" /> --%>
<%-- 						</c:forEach> --%>
<!-- 					</td> -->
<%-- 					</spring:bind>  --%>
<!-- 				</tr> -->
				<tr>
					<spring:bind path="apprAdminConfig.isMessageOpen">	
					<th scope="row" width="25%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<c:forEach var="code" items="${messageOpendList}">
							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}"
							<c:if test="${code.key eq apprAdminConfig.isMessageOpen}">checked="true"</c:if>
							<c:if test="${apprAdminConfig.isMessageOpen eq null}">
							<c:if test="${code.key eq '1'}">
								checked="true" 
							</c:if>
							</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach>
					</td>
					</spring:bind> 
				</tr>			
				
				<tr>
					<spring:bind path="apprAdminConfig.docNoMethod">	
					<th scope="row" width="25%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<c:forEach var="code" items="${docNoMethodList}">
							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}"
							<c:if test="${code.key eq apprAdminConfig.docNoMethod}">checked="true"</c:if>
							<c:if test="${apprAdminConfig.docNoMethod eq null}">
							<c:if test="${code.key eq '1'}">
								checked="true" 
							</c:if>
							</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach>
					</td>
					</spring:bind> 
				</tr>	
				<tr>
					<spring:bind path="apprAdminConfig.lineViewType">	
					<th scope="row" width="25%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<c:forEach var="code" items="${lineViewTypeList}">
							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}"
							<c:if test="${code.key eq apprAdminConfig.lineViewType}">checked="true"</c:if>
							<c:if test="${apprAdminConfig.lineViewType eq null}">
							<c:if test="${code.key eq '0'}">
								checked="true" 
							</c:if>
							</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach>
					</td>
					</spring:bind> 
				</tr>	
				<tr>
					<spring:bind path="apprAdminConfig.isReadAll">	
					<th scope="row" width="25%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<c:forEach var="code" items="${usageList}">
							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}"
							<c:if test="${code.key eq apprAdminConfig.isReadAll or apprAdminConfig.isReadAll eq null}">checked="true"</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach>
						
						<table summary="<ikep4j:message pre='${preForm}' key='isReadAll' />" id="isReadAllTbl">
							<caption></caption>
							<colgroup>
									<col width="100%"/>
							</colgroup>
							<tbody>
								<tr>
									<th scope="col" class="textLeft">
										<ikep4j:message pre='${preForm}' key='readAll' />
									</th>
								</tr>
								<tr>
									<td>
										<input id="userName" name="userName" title="<ikep4j:message pre='${preForm}' key='userName' />" class="inputbox" type="text" size="20" /> 
										<a id="searchUserButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a> 
										<a id="addUserButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='address' /></span></a> 
										<div class="mt5">  
											<select title="select" id="userIdList" name="userIdList" size="10" multiple="multiple" class="multi w60" title="<ikep4j:message pre='${preForm}' key='userName' />" >
												<c:forEach var="user" items="${apprAdminConfig.userList}"> 
													<option value="${user.userId}" >
														${user.userName} ${user.jobTitleName} ${user.teamName}
													</option>
												</c:forEach>  
											</select>	 
											<a id="deleteUserButton" class="button_ic valign_bottom" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='delete' /></span></a> 
										</div>	 
									</td>
								</tr>
			
							</tbody>
						</table>
			
					</td>
					</spring:bind> 
				</tr>	
				
			</tbody>
		</table>
		
	</div>
	<!--//blockDetail End-->
	
	<div class="clear"></div>
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="saveAdminConfigButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
	
</form>			

<!--//mainContents End-->