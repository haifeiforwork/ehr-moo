<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:set var="preAlert" value="message.portal.admin.screen.portal.updatePortalForm"/>
<c:set var="prefix" value="ui.portal.admin.screen.portal.updatePortalForm"/>

<script type="text/javascript">
//<![CDATA[
var deleteAdmins = new Array();    
var addAdminCount = ${countAdminUser+1};
           
(function($) {
	
    $jq(document).ready(function() {
    	
    	//left menu setting
		$jq("#portalLinkOfLeft").click();

    	
    	$jq("#addBtn").click(function() {
    		
    		var createUserId = $jq("#createUserId").val();
    		var createUserPassword = $jq("#createUserPassword").val();
    		var createUserName = $jq("#createUserName").val();
    		var checkUserId = $jq("#checkUserId").val();
    		
    		addAdminCount += 1;
    		
    		var htmlValue = "";
    		
    		htmlValue = "<tr id='adminTr_"+addAdminCount+"'>"
    					+ "<td class='textLeft' style='border:0px'><div>"
						+ "<ikep4j:message pre='${prefix}' key='userId' /> : <input type='text' id='createUserId_"+addAdminCount+"' name='createUserId_"+addAdminCount+"' class='inputbox' style='width:14%' value='"+createUserId+"' />"
						+ "&nbsp;<a class='button_ic' href='#' onclick=\"checkId(\'createUserId_"+addAdminCount+"\', \'checkUserId_"+addAdminCount+"\'); return false;\"><span><ikep4j:message pre='${prefix}' key='button.checkDuplicate' /></span></a>&nbsp;&nbsp;"
						+ "<ikep4j:message pre='${prefix}' key='userPassword' /> : <input type='password' name='createUserPassword"+addAdminCount+"' class='inputbox' style='width:17%' value='"+createUserPassword+"' />&nbsp;&nbsp;"
						+ "<ikep4j:message pre='${prefix}' key='userName' /> : <input type='text' name='createUserName"+addAdminCount+"' class='inputbox' style='width:17%' value='"+createUserName+"' />"
						+ "<input type='hidden' id='checkUserId_"+addAdminCount+"' name='checkUserId_"+addAdminCount+"' value='"+checkUserId+"' />"
						+ "</div></td>"
						+ "<td class='textRight' style='border:0px;'>"
						+ "<a id='cancelBtn' class='button_s' href='#' onclick=\"cancel(\'adminTr_"+addAdminCount+"\'); return false;\">"
						+ "<span style='margin-right:6px;'><ikep4j:message pre='${prefix}' key='button.cancel' /></span>"
						+ "</a>"
						+ "</td>"
						+ "</tr>";
    		
    		var $appendItem = $jq(htmlValue).insertBefore("#adminTr");
    		
    		$appendItem.find("input[name^=createUserId]").rules("add", {required:true, messages:{required:"<ikep4j:message key='NotEmpty.portal.userId' />"+iKEP.Validator.getArrowTag()}});
    		$appendItem.find("input[name^=createUserPassword]").rules("add", {required:true, messages:{required:"<ikep4j:message key='NotEmpty.portal.userPassword' />"+iKEP.Validator.getArrowTag()}});
    		$appendItem.find("input[name^=createUserName]").rules("add", {required:true, messages:{required:"<ikep4j:message key='NotEmpty.portal.userName' />"+iKEP.Validator.getArrowTag()}});
    		
    		$jq("#createUserId").val("");
    		$jq("#createUserPassword").val("");
    		$jq("#createUserName").val("");
    		$jq("#checkUserId").val("");
    		
    	});
    	
		$jq("#saveButton").click(function() {
	    	
    		var userId = $jq("#createUserId").val();
    		var userPassword = $jq("#createUserPassword").val();
    		var userName = $jq("#createUserName").val();
    		
    		if((userId != null && userId != '') || (userPassword != null && userPassword != '') || (userName != null && userName != '')) {
    			$jq("input[name=createUserId]").rules("add", {required:true, messages:{required:"<ikep4j:message key='NotEmpty.portal.userId' />"+iKEP.Validator.getArrowTag()}});
	    		$jq("input[name=createUserPassword]").rules("add", {required:true, messages:{required:"<ikep4j:message key='NotEmpty.portal.userPassword' />"+iKEP.Validator.getArrowTag()}});
	    		$jq("input[name=createUserName]").rules("add", {required:true, messages:{required:"<ikep4j:message key='NotEmpty.portal.userName' />"+iKEP.Validator.getArrowTag()}});
    		}
			
			$jq("#updatePortalForm").submit();
			
			$jq("input[name=createUserId]").rules("remove");
    		$jq("input[name=createUserPassword]").rules("remove");
    		$jq("input[name=createUserName]").rules("remove");
		});
		
		/**
		 * Validation Logic Start
		 */
		
		var validOptions = {
			rules : {
				portalName : {
					required : true,
					maxlength : 100
				},
				
				description : {
					required : true,
					maxlength : 250
				},
				
				portalHost : {
					required : true,
					maxlength : 500
				},
				
				portalPath : {
					required : true,
					maxlength : 2000
				}
			},
			messages : {
				portalName :	{
					required : "<ikep4j:message key='NotEmpty.portal.portalName' />",
					maxlength : "<ikep4j:message key='Size.portal.portalName' />"
				},
			
				description :	{
					required : "<ikep4j:message key='NotEmpty.portal.description' />",
					maxlength : "<ikep4j:message key='Size.portal.description' />"
				},
				
				portalHost :	{
					required : "<ikep4j:message key='NotEmpty.portal.portalHost' />",
					maxlength : "<ikep4j:message key='Size.portal.portalHost' />"
				},
				
				portalPath :	{
					required : "<ikep4j:message key='NotEmpty.portal.portalPath' />",
					maxlength : "<ikep4j:message key='Size.portal.portalPath' />"
				}
			},
			submitHandler : function(form) {
				
				var adminCount = $jq("input[name^='adminUserId']").length+$jq("input[name^='createUserId']").length;
				
				if( adminCount < 2 && ($jq("#createUserId").val() == null || $jq("#createUserId").val() == '')) {
					
					//alert("<ikep4j:message pre='${preAlert}' key='managerCnt' />");
					
					//$jq("#createUserId").focus();
					
					//return;
				}
				
				var adminUsers = new Array();
				
				for(var i=0;i<$jq("input[name^='adminUserId']").length;i++) {
					
					var userId = $jq("input[name^='adminUserId']").eq(i).val();
					var userPassword = $jq("input[name^='adminUserPassword']").eq(i).val();
					var preUserPassword = $jq("input[name^='adminPreUserPassword']").eq(i).val();
					var userName = $jq("input[name^='adminUserName']").eq(i).val();
					var preUserName = $jq("input[name^='adminPreUserName']").eq(i).val();
					
					if(userId != null && userId != "" && (userPassword != preUserPassword || userName != preUserName)) {
						var data = {'userId':$jq("input[name^='adminUserId']").eq(i).val(),
								'userPassword':$jq("input[name^='adminUserPassword']").eq(i).val(),
								'preUserPassword':$jq("input[name^='adminPreUserPassword']").eq(i).val(),
								'userName':$jq("input[name^='adminUserName']").eq(i).val()};
						
						adminUsers.push(data);
					}
				}
				
				var addAdmins = new Array();
				
				for(var i=0;i<$jq("input[name^='createUserId']").length;i++) {
					
					var userId = $jq("input[name^='createUserId']").eq(i).val();
					
					if(userId != null && userId != "") {
						var data = {'userId':$jq("input[name^='createUserId']").eq(i).val(),
								'userPassword':$jq("input[name^='createUserPassword']").eq(i).val(),
								'userName':$jq("input[name^='createUserName']").eq(i).val()};
						
						addAdmins.push(data);
					}
					
				}
				
				if(adminUsers.length > 0) {
					$jq("#adminUsers").val(JSON.stringify(adminUsers));
				}
				
				if(addAdmins.length > 0) {
					$jq("#addAdmins").val(JSON.stringify(addAdmins));
				}
				
				if(deleteAdmins.length > 0) {
					$jq("#deleteAdmins").val(JSON.stringify(deleteAdmins));
				}
				
				for(var i=0;i<$jq("input[name^='createUserId']").length;i++) {
					
					var createUserId = $jq("input[name^='createUserId']").eq(i).val();
					var checkUserId = $jq("input[name^='checkUserId']").eq(i).val();
					
					if(createUserId != null && createUserId != '' && createUserId != checkUserId) {
						alert("<ikep4j:message pre='${preAlert}' key='checkUserId' />");
						
						$jq("input[name^='createUserId']").eq(i).focus();
						
						return;
					}
				}
				
				$jq.ajax({
					url : "<c:url value='/portal/admin/screen/portal/updatePortal.do'/>",
					type : "post",
					data : $(form).serialize(),
					success : function(result) {
						alert("<ikep4j:message pre='${preAlert}' key='saveSuccess'/>");
						
						location.href= "<c:url value='/portal/admin/screen/portal/readPortal.do'/>?portalId=" + result; //조회화면으로 포워딩
					},
					error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
				
			}
		};

		var validator = new iKEP.Validator("#updatePortalForm", validOptions);

		/**
		 * Validation Logic End
		 */
		 
		<c:forEach var="admin" items="${listAdminUser.assignUserDetailList}" varStatus="loopStatus">
    	$jq("tr[id=adminTr_${admin.userId}]").find("input[name^=adminUserPassword]").rules("add", {required:true, messages:{required:"<ikep4j:message key='NotEmpty.portal.userPassword' />"+iKEP.Validator.getArrowTag()}});
    	$jq("tr[id=adminTr_${admin.userId}]").find("input[name^=adminUserName]").rules("add", {required:true, messages:{required:"<ikep4j:message key='NotEmpty.portal.userName' />"+iKEP.Validator.getArrowTag()}});
    	</c:forEach>
		
    });
    
	disableTarget = function(value) {
		
		if(value == 0) { 
			$jq("input[name=defaultOption]:radio").attr("disabled", true);
			$jq("#defaultPortal").attr("checked", false);
			$jq("#noDefaultPortal").attr("checked", true);
		} else if(value == 1){
			$jq("input[name=defaultOption]:radio").attr("disabled", false);
		}
		
	};
	
	deleteAdmin = function(userId) {
		
		var sessionUserId = "<c:out value='${user.userId}'/>";
		
		if(sessionUserId == userId) {
			
			alert("<ikep4j:message pre='${preAlert}' key='sameUserId' />");
			
			return;
		}
    	
		var adminCount = $jq("input[name^='adminUserId']").length+$jq("input[name^='createUserId']").length;
		
		if(false && (adminCount < 3 && ($jq("#createUserId").val() == null || $jq("#createUserId").val() == ''))) {
			
			alert("<ikep4j:message pre='${preAlert}' key='managerCnt' />");
			
			return;
			
		} else {
			
	    	if(confirm("<ikep4j:message pre='${preAlert}' key='confirmDelete' />")) {
			
	    		var data = {'userId':userId};
				
	    		deleteAdmins.push(data);
	    		
	    		$jq("tr[id=adminTr_"+userId+"]").remove();
	    		
	    	} else {
	    		
	    		return;
	    	}
		}
    	
    };
    
    cancel = function(id) {
    	
    	var $cancelItem = $jq("tr[id="+id+"]");
    	
    	$cancelItem.find("input[name^=createUserId]").rules("remove");
    	$cancelItem.find("input[name^=createUserPassword]").rules("remove");
    	$cancelItem.find("input[name^=createUserName]").rules("remove");
    	
    	$cancelItem.remove();
    	
    };
    
	checkId = function(userId, checkUserId) {
		
    	var userIdValue = $jq("input[id="+userId+"]").val();
    	
		if(userIdValue == "" || userIdValue == null) {
			alert("<ikep4j:message pre='${preAlert}' key='noCheckValue' />");
		} else {
			$jq.ajax({
				url : '<c:url value="/portal/admin/member/user/checkId.do" />',     
				data : {
					id: userIdValue
				},     
				type : "post",
				success : function(result) {     
					
					if(result == 'duplicated') {
						alert("<ikep4j:message pre='${preAlert}' key='isDuplicated' />");
					} else {
					
						var isDuplicated = false;
						
						for(var i=0;i<$jq("input[name^='createUserId']").length;i++) {
							
							var checkId = $jq("input[name^='createUserId']").eq(i).val();
							var attrId = $jq("input[name^='createUserId']").eq(i).attr("id");
							
							if(checkId != null && checkId != '' && userId != attrId && userIdValue == checkId) {
								isDuplicated = true;
								
								break;
							}
						}
						
						if(isDuplicated) {
							alert("<ikep4j:message pre='${preAlert}' key='isDuplicated' />");
							
							$jq("input[id="+checkUserId+"]").val("");
						} else {
							alert("<ikep4j:message pre='${preAlert}' key='isAvailable' />");
							
							$jq("input[id="+checkUserId+"]").val(userIdValue);
						}
					}
				} 
			});			
		}
		
	};
	
})(jQuery);
//]]>
</script>

<form id="updatePortalForm" method="post" action="">
<input type="hidden" name="portalId" value="${portal.portalId}"/>
<input type="hidden" id="adminUsers" name="adminUsers" value="" />
<input type="hidden" id="addAdmins" name="addAdmins" value="" />
<input type="hidden" id="deleteAdmins" name="deleteAdmins" value="" />

	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre='${prefix}' key='pageTitle'/></h2>
		<!--div id="pageLocation">
			<ul>
				<li class="liFirst"><ikep4j:message pre='${prefix}' key='home'/></li>
				<li><ikep4j:message pre='${prefix}' key='1depth'/></li>
				<li><ikep4j:message pre='${prefix}' key='2depth'/></li>
				<li class="liLast"><ikep4j:message pre='${prefix}' key='lastDepth'/></li>
			</ul>
		</div-->
	</div>
	<!--//pageTitle End-->
	
	<div class="blockDetail">
		<table id="mainTable" summary="<ikep4j:message pre='${prefix}' key='pageTitle'/>">
			<caption></caption>
			<tbody>
				<tr>
					<th width="18%" scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='portalName'/></th>
					<td width="82%">
						<div>
						<spring:bind path="portal.portalName">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${portal.portalName}" title="<ikep4j:message pre='${prefix}' key='portalName'/>"/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
						</div>					
					</td>
				</tr>
				<tr>
					<th width="18%" scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='description'/></th>
					<td width="82%">
						<div>
						<spring:bind path="portal.description">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100"  value="${portal.description}" title="<ikep4j:message pre='${prefix}' key='description'/>"/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
						</div>
					</td>
				</tr>
				<tr>
					<th width="18%" scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='portalHost'/></th>
					<td width="82%">
						<div>
						<spring:bind path="portal.portalHost">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${portal.portalHost}" title="<ikep4j:message pre='${prefix}' key='portalHost'/>"/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
						</div>		
					</td>
				</tr>
				<tr>
					<th width="18%" scope="row"><ikep4j:message pre='${prefix}' key='portalHostAlias'/></th>
					<td width="82%">
						<div>
						<spring:bind path="portal.portalHostAlias">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${portal.portalHostAlias}" title="<ikep4j:message pre='${prefix}' key='portalHostAlias'/>"/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
						</div>	
						<div class="tdInstruction">
							<ikep4j:message pre="${prefix}" key="portalHostAliasDesc" />
						</div>		
					</td>
				</tr>
				<tr style="display:none">
					<th width="18%" scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='portalPath'/></th>
					<td width="82%">
						<div>
						<spring:bind path="portal.portalPath">
						<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${portal.portalPath}" title="<ikep4j:message pre='${prefix}' key='portalPath'/>"/>
						<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
						</spring:bind>
						</div>					
					</td>
				</tr>
				<tr>
					<th width="18%" scope="row"><ikep4j:message pre='${prefix}' key='defaultOption'/></th>
					<td width="82%">
						<label><input type="radio" class="radio" id="defaultPortal" name="defaultOption" value="1" <c:if test="${portal.defaultOption == 1}">checked="checked"</c:if> title="<ikep4j:message pre='${prefix}' key='defaultPortal'/>" <c:if test="${portal.defaultOption == 0 && portal.active == 0}">disabled="disabled"</c:if> /><ikep4j:message pre='${prefix}' key='defaultPortal'/></label>&nbsp;
						<label><input type="radio" class="radio" id="noDefaultPortal" name="defaultOption" value="0" <c:if test="${portal.defaultOption == 0}">checked="checked"</c:if> title="<ikep4j:message pre='${prefix}' key='noDefaultPortal'/>" <c:if test="${portal.defaultOption == 0 && portal.active == 0}">disabled="disabled"</c:if> /><ikep4j:message pre='${prefix}' key='noDefaultPortal'/></label>	
					</td>
				</tr>
				<tr>
					<th width="18%" scope="row"><ikep4j:message pre='${prefix}' key='isUse'/></th>
					<td width="82%">
						<label><input type="radio" class="radio" name="active" value="1" <c:if test="${portal.active == 1}">checked="checked"</c:if> title="<ikep4j:message pre='${prefix}' key='defaultPortal'/>" onclick="disableTarget(1);" /><ikep4j:message pre='${prefix}' key='use'/></label>&nbsp;
						<label><input type="radio" class="radio" name="active" value="0" <c:if test="${portal.active == 0}">checked="checked"</c:if> title="<ikep4j:message pre='${prefix}' key='noDefaultPortal'/>" onclick="disableTarget(0);" /><ikep4j:message pre='${prefix}' key='noUse'/></label>								
					</td>
				</tr>
				<tr>
					<th width="18%" scope="row"><ikep4j:message pre='${prefix}' key='cacheYn'/></th>
					<td width="82%">
						<label><input type="radio" class="radio" name="cacheYn" value="Y" <c:if test="${portal.cacheYn == 'Y'}">checked="checked"</c:if> title="<ikep4j:message pre='${prefix}' key='use'/>" /><ikep4j:message pre='${prefix}' key='use'/></label>&nbsp;
						<label><input type="radio" class="radio" name="cacheYn" value="N" <c:if test="${portal.cacheYn == 'N'}">checked="checked"</c:if> title="<ikep4j:message pre='${prefix}' key='noUse'/>" /><ikep4j:message pre='${prefix}' key='noUse'/></label>								
					</td>
				</tr>
				<tr>
					<th width="18%" scope="row"><ikep4j:message pre='${prefix}' key='defaultLocale'/></th>
					<td width="82%">
						<select name="defaultLocaleCode" title="<ikep4j:message pre='${prefix}' key='defaultLocale'/>">
							<c:forEach var="localeCode" items="${localeCodeList}">
								<option value="${localeCode.localeCode}" <c:if test="${localeCode.localeCode == portal.defaultLocaleCode}">selected="selected"</c:if>>${localeCode.localeName}</option>	
							</c:forEach>
						</select>						
					</td>
				</tr>
				<tr>
					<th width="18%" scope="row"><ikep4j:message pre="${prefix}" key="manager" /></th>
					<td width="82%">
						<table style="border:0px; padding:0px; margin:0px;">
							<caption></caption>
							<colgroup>
								<col width="90%"/>
								<col width="10%"/>
							</colgroup>
							<tbody>
								<c:if test="${!empty listAdminUser.assignUserDetailList}">
								<c:forEach var="admin" items="${listAdminUser.assignUserDetailList}" varStatus="loopStatus">
								<tr id="adminTr_${admin.userId}">
									<td class="textLeft" style="border:0px">
										<div>
										<ikep4j:message pre="${prefix}" key="userId" /> : ${admin.userId}
										<input type="hidden" name="adminUserId_${loopStatus.count}" value="${admin.userId}" />
										&nbsp;
										<ikep4j:message pre="${prefix}" key="userPassword" /> : <input type="password" name="adminUserPassword_${loopStatus.count}" class="inputbox" style="width:18%" value="${admin.userPassword}" />&nbsp;
										<ikep4j:message pre="${prefix}" key="userName" /> : <input type="text" name="adminUserName_${loopStatus.count}" class="inputbox" style="width:18%" value="${admin.userName}" />
										<input type="hidden" name="adminPreUserPassword_${loopStatus.count}" value="${admin.userPassword}" />
										<input type="hidden" name="adminPreUserName_${loopStatus.count}" value="${admin.userName}" />
										</div>
									</td>
									<td class="textRight" style="border:0px;">
										<a class="button_s" href="#" onclick="deleteAdmin('${admin.userId}'); return false;">
											<span style="margin-right:6px;"><ikep4j:message pre="${prefix}" key="button.delete" /></span>
										</a>
									</td>
								</tr>
								</c:forEach>
								</c:if>
								<tr id="adminTr">
									<td class="textLeft" style="border:0px">
										<div>
										<ikep4j:message pre="${prefix}" key="userId" /> : <input type="text" id="createUserId" name="createUserId" class="inputbox" style="width:14%" value="" autocomplete="off"/>
										<a class="button_ic" href="#" onclick="checkId('createUserId', 'checkUserId'); return false;"><span><ikep4j:message pre="${prefix}" key="button.checkDuplicate" /></span></a>&nbsp;
										<ikep4j:message pre="${prefix}" key="userPassword" /> : <input type="password" id="createUserPassword" name="createUserPassword" class="inputbox" style="width:17%" value="" autocomplete="off"/>&nbsp;
										<ikep4j:message pre="${prefix}" key="userName" /> : <input type="text" id="createUserName" name="createUserName" class="inputbox" style="width:17%" value="" />
										<input type="hidden" id="checkUserId" name="checkUserId" value="" />
										</div>
									</td>
									<td class="textRight" style="border:0px;">
										<a id="addBtn" class="button_s" href="#" onclick="return false;">
											<span style="margin-right:6px;"><ikep4j:message pre="${prefix}" key="button.add" /></span>
										</a>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<!--//blockDetail End-->
	
	<!--tableBottom Start-->
	<div class="tableBottom">										
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a id="saveButton" class="button" href="#" onclick="return false;"><span><ikep4j:message pre='${prefix}' key='button.save'/></span></a></li>
				<li><a class="button" href="<c:url value='/portal/admin/screen/portal/listPortal.do'/>"><span><ikep4j:message pre='${prefix}' key='button.list'/></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
</form>
