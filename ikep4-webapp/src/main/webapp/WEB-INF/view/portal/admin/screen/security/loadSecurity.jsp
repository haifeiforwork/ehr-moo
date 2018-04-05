<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="prefixMessage" value="ui.portal.admin.screen.security.loadSecurity.alert"/>					
<c:set var="prefixUI" value="ui.portal.admin.screen.security.loadSecurity.form"/>
<c:set var="user" value="${sessionScope['ikep.user']}" />

<input type="hidden" id="security.addrGroupType" name="security.addrGroupType" value=""/>
<input type="hidden" id="security.groupTypeCount" name="security.groupTypeCount" value="0"/>
<input type="hidden" id="securityOptionList" name="securityOptionList" value=""/>

<input type="hidden" id="security.className" name="security.className" value="${security.className}"/>
<input type="hidden" id="security.resourceName" name="security.resourceName" value="${security.resourceName}"/>
<input type="hidden" id="security.operationName" name="security.operationName" value="${security.operationName}"/>
<input type="hidden" id="security.addrUserListAll" name="security.addrUserListAll" value=""/>
<input type="hidden" id="security.addrGroupTypeListAll" name="security.addrGroupTypeListAll" value=""/>
<input type="hidden" id="security.addrRoleListAll" name="security.addrRoleListAll" value=""/>
<input type="hidden" id="security.addrJobClassListAll" name="security.addrJobClassListAll" value=""/>
<input type="hidden" id="security.addrJobDutyListAll" name="security.addrJobDutyListAll" value=""/>
<input type="hidden" id="security.addrExpUserListAll" name="security.addrExpUserListAll" value=""/>
<input type="hidden" id="security.ownerId" name="security.ownerId" value="${security.ownerId}"/>
<input type="hidden" id="security.ownerName" name="security.ownerName" value="${security.ownerName}"/>

	<table id="securityTable">	
		<caption></caption>
		<colgroup>
			<col width="10%"/>
			<col width="8%"/>
			<col width="82%"/>
		</colgroup>
		<tbody>
				<!--resource Start-->
				<tr>
					<th scope="col" colspan="2">
						<ikep4j:message pre="${prefixUI}" key="aclResource" />
					</th>
					<td class="textLeft">
						<input type="radio" id="openYes" name="security.aclResourcePermissionRead.open" class="radio" value="1" onclick="changeRadio(1);" <c:if test="${security.aclResourcePermissionRead.open == 1}">checked="checked"</c:if> />
						<label for="openYes"><ikep4j:message pre="${prefixUI}" key="openYes" /></label>&nbsp;
						<input type="radio" id="openNo" name="security.aclResourcePermissionRead.open" class="radio" value="0" onclick="changeRadio(0);" <c:if test="${security.aclResourcePermissionRead.open == 0}">checked="checked"</c:if> />
						<label for="openNo"><ikep4j:message pre="${prefixUI}" key="openNo" /></label>
					</td>
				</tr>
				<!--//resource End-->
				
				
				<tr id="User_TR" style="display:none;">
					<th rowspan="6" scope="row" class="textCenter" id="headTH">
						<ikep4j:message pre="${prefixUI}" key="resourceConfig" />
					</th>
					<th scope="row"><ikep4j:message pre="${prefixUI}" key="user" /></th>
					<td>
						<input name="userName" id="userName" title="<ikep4j:message pre='${prefixUI}' key='user' />" class="inputbox" type="text" size="20" />
						<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='searchIcon' />">
							<span id="userSearchBtn">
								<img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='searchIcon' />"/>
								<ikep4j:message pre="${prefixUI}" key="search" />
							</span>
						</a>
						<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='addressBookIcon' />">
							<span id="btnAddrControl">
								<img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='addressBookIcon' />"/>
								<ikep4j:message pre="${prefixUI}" key="addressBook" />
							</span>
						</a>
						<div class="input_buttonBox">
							<div class="input_buttonBox01">
								<select name="security.addrUserList" size="5" multiple="multiple" class="multi" title="<ikep4j:message pre='${prefixUI}' key='userName' />" ></select>
							</div>
							<div class="input_buttonBox02">
								<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='deleteIcon' />">
									<span id="userNameDelete"><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='deleteIcon' />"/><ikep4j:message pre="${prefixUI}" key="delete" /></span>
								</a>
								<ikep4j:message pre="${prefixUI}" key="total" /> <span id="userTypeNameCount">0</span><ikep4j:message pre="${prefixUI}" key="userNameCount" />
							</div>
						</div>								
					</td>
				</tr>	
				
				<tr id="Group_TR" style="display:none;">
					<th scope="row"><ikep4j:message pre="${prefixUI}" key="group" /></th>
					<td>
						<input name="groupTypeName" id="groupTypeName" class="inputbox" type="text" size="20" />
						<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='searchIcon' />">
							<span id="groupTypeSearchBtn">
								<img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='searchIcon' />"/>
								<ikep4j:message pre="${prefixUI}" key="search" />
							</span>
						</a>
						<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='listIcon' />">
							<span id="groupTypeSearchBtnAll">
								<img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='listIcon' />"/>
								<ikep4j:message pre="${prefixUI}" key="addressBook" />
							</span>
						</a>
						<div class="input_buttonBox">
							<div class="input_buttonBox01">
								<select name="security.addrGroupTypeList" size="5" multiple="multiple" class="multi" title="<ikep4j:message pre='${prefixUI}' key='groupName' />" >
									<c:if test="${!empty security.aclResourcePermissionRead.groupDetailList}">
									<c:forEach var="item" items="${security.aclResourcePermissionRead.groupDetailList}">
										<option value="<c:out value='${item.groupId}'/>"><c:out value='${item.groupName}'/></option>
									</c:forEach>
									</c:if>
								</select>
							</div>
							<div class="input_buttonBox02">	
								<a href="#" class="button_ic mb5" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='groupHierarchyIcon' />">
									<span id="changeHierarchy"><img src="<c:url value='/base/images/icon/ic_btn_subgroup.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='groupHierarchyIcon' />"/><ikep4j:message pre="${prefixUI}" key="groupHierarchy" /></span>
								</a>
								<br />
								<a href="#" class="button_ic mb5" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='deleteIcon' />">
									<span id="groupTypeNameDelete"><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='deleteIcon' />"/><ikep4j:message pre="${prefixUI}" key="delete" /></span>
								</a>
								<ikep4j:message pre="${prefixUI}" key="total" /> <span id="groupTypeNameCount">0</span><ikep4j:message pre="${prefixUI}" key="groupNameCount" />
							</div>
						</div>								
					</td>
				</tr>	
				<tr id="Role_TR" style="display:none;">
					<th scope="row"><ikep4j:message pre="${prefixUI}" key="role" /></th>
					<td>
						<input name="roleName" id="roleName" class="inputbox" type="text" size="20" />
						<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='searchIcon' />">
							<span id="roleSearchBtn">
								<img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='searchIcon' />"/>
								<ikep4j:message pre="${prefixUI}" key="search" />
							</span>
						</a>
						<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='listIcon' />">
							<span id="roleSearchBtnAll">
								<img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='listIcon' />"/>
								<ikep4j:message pre="${prefixUI}" key="list" />
							</span>
						</a>
						<div class="input_buttonBox">
							<div class="input_buttonBox01">
								<select name="security.addrRoleList" size="5" multiple="multiple" class="multi" title="<ikep4j:message pre='${prefixUI}' key='roleName' />" >
									<c:if test="${!empty security.aclResourcePermissionRead.roleDetailList}">
									<c:forEach var="item" items="${security.aclResourcePermissionRead.roleDetailList}">
										<option value="<c:out value='${item.roleId}'/>"><c:out value='${item.roleName}'/></option>
									</c:forEach>
									</c:if>
								</select>
							</div>
							<div class="input_buttonBox02">
								<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='deleteIcon' />">
									<span id="roleNameDelete"><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='deleteIcon' />" /><ikep4j:message pre="${prefixUI}" key="delete" /></span>
								</a>
								<ikep4j:message pre="${prefixUI}" key="total" /> <span id="roleNameCount">0</span><ikep4j:message pre="${prefixUI}" key="roleNameCount" />
							</div>
						</div>								
					</td>
				</tr>	
				<tr id="Job_TR" style="display:none;">
					<th scope="row"><ikep4j:message pre="${prefixUI}" key="jobClass" /></th>
					<td>
						<input name="jobClassName" id="jobClassName" class="inputbox" type="text" size="20" />
						<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='searchIcon' />">
							<span id="jobClassSearchBtn">
								<img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='searchIcon' />" />
								<ikep4j:message pre="${prefixUI}" key="search" />
							</span>
						</a>
						<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='listIcon' />">
							<span id="jobClassSearchBtnAll">
								<img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='listIcon' />" />
								<ikep4j:message pre="${prefixUI}" key="list" />
							</span>
						</a>
						<div class="input_buttonBox">
							<div class="input_buttonBox01">
							<select name="security.addrJobClassList" size="5" multiple="multiple" class="multi" title="<ikep4j:message pre='${prefixUI}' key='jobClassName' />" >
								<c:if test="${!empty security.aclResourcePermissionRead.jobClassDetailList}">
								<c:forEach var="item" items="${security.aclResourcePermissionRead.jobClassDetailList}">
									<option value="<c:out value='${item.jobClassCode}'/>"><c:out value='${item.jobClassName}'/></option>
								</c:forEach>
								</c:if>
							</select>
							</div>
							<div class="input_buttonBox02">
							<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='deleteIcon' />">
								<span id="jobClassNameDelete"><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='deleteIcon' />" /><ikep4j:message pre="${prefixUI}" key="delete" /></span>
							</a>
							<ikep4j:message pre="${prefixUI}" key="total" /> <span id="jobClassNameCount">0</span><ikep4j:message pre="${prefixUI}" key="jobClassNameCount" />
							</div>
						</div>								
					</td>
				</tr>	
				<tr id="Duty_TR" style="display:none;">
					<th scope="row"><ikep4j:message pre="${prefixUI}" key="jobDuty" /></th>
					<td>
						<input name="jobDutyName" id="jobDutyName" class="inputbox" type="text" size="20" />
						<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='searchIcon' />">
							<span id="jobDutySearchBtn">
								<img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='searchIcon' />" />
								<ikep4j:message pre="${prefixUI}" key="search" />
							</span>
						</a>
						<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='listIcon' />">
							<span id="jobDutySearchBtnAll">
								<img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='listIcon' />" />
								<ikep4j:message pre="${prefixUI}" key="list" />
							</span>
						</a>
						<div class="input_buttonBox">
							<div class="input_buttonBox01">
							<select name="security.addrJobDutyList" size="5" multiple="multiple" class="multi" title="<ikep4j:message pre='${prefixUI}' key='jobDutyName' />" >
								<c:if test="${!empty security.aclResourcePermissionRead.jobDutyDetailList}">
								<c:forEach var="item" items="${security.aclResourcePermissionRead.jobDutyDetailList}">
									<option value="<c:out value='${item.jobDutyCode}'/>"><c:out value='${item.jobDutyName}'/></option>
								</c:forEach>
								</c:if>
							</select>
							</div>
							<div class="input_buttonBox02">
							<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='deleteIcon' />">
								<span id="jobDutyNameDelete"><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='deleteIcon' />" /><ikep4j:message pre="${prefixUI}" key="delete" /></span>
							</a>
							<ikep4j:message pre="${prefixUI}" key="total" /> <span id="jobDutyNameCount">0</span><ikep4j:message pre="${prefixUI}" key="jobDutyNameCount" />
							</div>
						</div>								
					</td>
				</tr>
				<tr id="ExpUser_TR" style="display:none;">
					<th scope="row"><ikep4j:message pre="${prefixUI}" key="exception" /></th>
					<td>
						<input name="expUserName" id="expUserName" title="<ikep4j:message pre='${prefixUI}' key='user' />" class="inputbox" type="text" size="20" />
						<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='searchIcon' />">
							<span id="expUserSearchBtn">
								<img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='searchIcon' />"/>
								<ikep4j:message pre="${prefixUI}" key="search" />
							</span>
						</a>
						<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='addressBookIcon' />">
							<span id="expUserSearchBtnAll">
								<img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='addressBookIcon' />"/>
								<ikep4j:message pre="${prefixUI}" key="addressBook" />
							</span>
						</a>
						<div class="input_buttonBox">
							<div class="input_buttonBox01">
								<select name="security.addrExpUserList" size="5" multiple="multiple" class="multi" title="<ikep4j:message pre='${prefixUI}' key='userName' />" ></select>
							</div>
							<div class="input_buttonBox02">
								<a href="#" class="button_ic" onclick="return false;" title="<ikep4j:message pre='${prefixUI}' key='deleteIcon' />">
									<span id="expUserNameDelete"><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="<ikep4j:message pre='${prefixUI}' key='deleteIcon' />"/><ikep4j:message pre="${prefixUI}" key="delete" /></span>
								</a>
								<ikep4j:message pre="${prefixUI}" key="total" /> <span id="expUserTypeNameCount">0</span><ikep4j:message pre="${prefixUI}" key="userNameCount" />
							</div>
						</div>								
					</td>
				</tr>
	</tbody>
</table>
<script type="text/javascript">
(function($) {
	var $selectAddrUser = $("select[name='security.addrUserList']");
	var $selectAddrGroup = $("select[name='security.addrGroupTypeList']");
	var $selectAddrExpUser = $("select[name='security.addrExpUserList']");
	
   	<c:forEach var="item" items="${security.aclResourcePermissionRead.assignUserDetailList}">
	var userInfo = {// 공통필수
		type : "user",
		empNo : "",
		id : "${item.userId}",
		name : "${item.userName} ${item.jobTitleName} ${item.teamName}",
		userName : "${item.userName}",
		jobTitleName : "${item.jobTitleName}",
		teamName : "${item.teamName}",
		email : "",
		mobile : ""
	};
	$.tmpl(iKEP.template.userOption, userInfo).appendTo($selectAddrUser)
		.data("data", userInfo);
	</c:forEach>
	
	<c:forEach var="item" items="${security.aclResourcePermissionRead.exceptUserDetailList}">
	var userInfo = {// 공통필수
		type : "user",
		empNo : "",
		id : "${item.userId}",
		name : "${item.userName} ${item.jobTitleName} ${item.teamName}",
		userName : "${item.userName}",
		jobTitleName : "${item.jobTitleName}",
		teamName : "${item.teamName}",
		email : "",
		mobile : ""
	};
	$.tmpl(iKEP.template.userOption, userInfo).appendTo($selectAddrExpUser)
		.data("data", userInfo);
	</c:forEach>
   	
   	// 공개, 비공개 여부에 따라 display 변경
   	changeRadio(<c:out value="${security.aclResourcePermissionRead.open}"/>);

   	// 셀렉트 박스 카운트 넣기
   	$jq("#userTypeNameCount").html($selectAddrUser.children().length);
   	$jq("#groupTypeNameCount").html($selectAddrGroup.children().length);
   	$jq("#roleNameCount").html($jq("select[name='security.addrRoleList']").children().length);
   	$jq("#jobClassNameCount").html($jq("select[name='security.addrJobClassList']").children().length);
   	$jq("#jobDutyNameCount").html($jq("select[name='security.addrJobDutyList']").children().length);
   	$jq("#expUserTypeNameCount").html($selectAddrExpUser.children().length);
   	
   	
       //조직도 팝업 
	$jq("#btnAddrControl").click(function() {
		var items = $.map($selectAddrUser.children(), function(option) {
			return $(option).data("data");
		});

		iKEP.showAddressBook(showAddressBookCallback, items, {selectElement:$selectAddrUser, selectType:"user"});
	});

    //검색 관련 이벤트
    $jq('#userName').keypress( function(event) { 
		iKEP.searchUser(event, "Y", setUser); 
	});
	
	$jq('#userSearchBtn').click( function() { 
		$jq('#userName').trigger("keypress");
	});
       
	
	$jq('#groupTypeName').keypress( function(event) { 
		iKEP.searchGroup(event, "Y", setGroup); 
	});
	
	$jq('#groupTypeSearchBtn').click( function() { 
		$jq('#groupTypeName').trigger("keypress");
	});
	
	
	//그룹목록 추가 버튼에 이벤트 바인딩
	$("#groupTypeSearchBtnAll").click( function() {
		var items = $.map($selectAddrGroup.children(), function(option) {
			return $(option).data("data");
		});
		
		iKEP.showAddressBook(setGroupAddress, items, {selectType:"group",tabs:{common:0}});
	});
	
	$jq('#roleName').keypress( function(event) { 
		iKEP.searchRole(event, "Y", setRole); 
	});
	
	$jq('#roleSearchBtn').click( function() { 
		$jq('#roleName').trigger("keypress");
	});
	
	$jq('#roleSearchBtnAll').click(function(event) { 
		iKEP.popupRole("", "Y", setRole);	
	});
	
	$jq('#jobClassName').keypress( function(event) { 
		iKEP.searchJobClass(event, "Y", setJobClass); 
	});
	
	$jq('#jobClassSearchBtn').click( function() { 
		$jq('#jobClassName').trigger("keypress");
	});
	
	$jq('#jobClassSearchBtnAll').click(function(event) { 
		iKEP.popupJobClass("", "Y", setJobClass);	
	});
	
	$jq('#jobDutyName').keypress( function(event) { 
		iKEP.searchJobDuty(event, "Y", setJobDuty); 
	});
	
	$jq('#jobDutySearchBtn').click( function() { 
		$jq('#jobDutyName').trigger("keypress");
	});
	
	$jq('#jobDutySearchBtnAll').click(function(event) { 
		iKEP.popupJobDuty("", "Y", setJobDuty);	
	});
	
	$jq('#expUserName').keypress( function(event) { 
		iKEP.searchUser(event, "Y", setExpUser); 
	});
	
	$jq('#expUserSearchBtn').click( function() { 
		$jq('#expUserName').trigger("keypress");
	});
	
	$jq("#expUserSearchBtnAll").click(function() {
		var items = $.map($selectAddrExpUser.children(), function(option) {
			return $(option).data("data");
		});
		
		iKEP.showAddressBook(showExpAddressBookCallback, items, {selectElement:$selectAddrExpUser, selectType:"user"});
	});
	
	//삭제 이벤트
	$jq('#userNameDelete').click(function(event) { 
		selectRemove("security.addrUserList", "userTypeNameCount");	
	});
	
	$jq('#groupTypeNameDelete').click(function(event) { 
		selectRemove("security.addrGroupTypeList", "groupTypeNameCount");
	});
	
	$jq('#roleNameDelete').click(function(event) { 
		selectRemove("security.addrRoleList", "roleNameCount");
	});
	
	$jq('#jobClassNameDelete').click(function(event) { 
		selectRemove("security.addrJobClassList", "jobClassNameCount");
	});
	
	$jq('#jobDutyNameDelete').click(function(event) { 
		selectRemove("security.addrJobDutyList", "jobDutyNameCount");
	});
	
	$jq('#expUserNameDelete').click(function(event) { 
		selectRemove("security.addrExpUserList", "expUserTypeNameCount");	
	});
	
	$jq('#changeHierarchy').click(function(event) { 
		changeGroupHierarchy("security.addrGroupTypeList");
	});
	
	changeGroupHierarchy = function (selectName) {
		var $sel = "";	
		var $obj = "";
		
		$sel = $jq("select[name='"+selectName+"']");
		
		if($sel) {
			$sel.find("option:selected").each(function () {
                $obj = $jq.parseJSON($(this).val());
                
                var groupId = $obj.groupId;
    			var hierarchyPermission = $obj.hierarchyPermission;
    			var text = $(this).text();
    			
    			if(hierarchyPermission == 0) {
    				$(this).text("[H]"+text);
    				$obj.hierarchyPermission = 1;
    			} else if(hierarchyPermission == 1) {
    				$(this).text(text.substring(3, text.length));
    				$obj.hierarchyPermission = 0;
    			} else {
    				
    			}
    			
    			$(this).val(JSON.stringify($obj));
			});
			
			setSecurityValue(selectName);
		}
		
	};
	
	// 조직도 popup후 callback
    var showAddressBookCallback = function(data) {
    	$jq("#userTypeNameCount").html($selectAddrUser.children().length);
    	
    	setSecurityValue('security.addrUserList');
    };
    
    var showExpAddressBookCallback = function(data) {
    	if(data.length > 0) {
			$jq(data).each(function(index) {
				$jq.each($selectAddrExpUser.children(), function() {
					if(this.value == "${user.userId}") {
						$jq(this).remove();
						
						alert("<ikep4j:message pre='${prefixMessage}' key='noUseMyAccount' />");
					}
				});
			});
		}
    	
    	$jq("#expUserTypeNameCount").html($selectAddrExpUser.children().length);
    	
    	setSecurityValue('security.addrExpUserList');
    };
	
	setUser = function(data) {
		var selectCheck;
		if(data.length > 0) {
			$('#userName').val("");
			$jq(data).each(function(index) {
				selectCheck = true;
				$jq.each($selectAddrUser.children(), function() {
					if(this.value == data[index].id) {
						selectCheck = false;
					}
				});
				
				if(selectCheck) {
					$.tmpl(iKEP.template.userOption, this).appendTo($selectAddrUser)
						.data("data", this);
				} 
			});
			
			$jq("#userTypeNameCount").html($selectAddrUser.children().length);
		} else {
			alert("<ikep4j:message pre='${prefixMessage}' key='noSearchUser' />");
		}
		
		setSecurityValue('security.addrUserList');
		
		var $lastItem = $selectAddrUser.children(":last");
		$selectAddrUser.scrollTop($lastItem.position().top + $lastItem.height());
	};
	
	setGroup = function(data) {
		var selectCheck;
		if(data.length > 0) {
			$('#groupTypeName').val("");
			$jq(data).each(function(index, groupInfo) {
				selectCheck = true;
				$jq.each($selectAddrGroup.children(), function() {
					if($(this).data("data").code == groupInfo.code) {
						selectCheck = false;
					}
				});
				
				if(selectCheck) {
					$.tmpl(iKEP.template.groupOption, groupInfo).appendTo($selectAddrGroup)
						.data("data", groupInfo)
						.val(JSON.stringify({groupId:groupInfo.code, hierarchyPermission:0}));
				}
			});
			
			$jq("#groupTypeNameCount").html($selectAddrGroup.children().length);
		} else {
			alert("<ikep4j:message pre='${prefixMessage}' key='noSearchGroup' />");
		}
		
		setSecurityValue('security.addrGroupTypeList');
	};
	
	/** 주소록에서 선택한 후 그룹 데이타 세팅 **/
	setGroupAddress = function(data) {
		$selectAddrGroup.children().remove();
		
		$.each(data, function() {
			$.tmpl(iKEP.template.groupOption, this).appendTo($selectAddrGroup)
				.data("data", this)
				.val(JSON.stringify({groupId:this.code, hierarchyPermission:0}));
		});
		
		$jq("#groupTypeNameCount").html($selectAddrGroup.children().length);
		
		setSecurityValue('security.addrGroupTypeList');
	};
	
	
	setRole = function(data) {
		var $sel = $jq("select[name='security.addrRoleList']");
		var str = "";
		var selectCheck;
		
		if(data.length > 0) {
			$jq(data).each(function(index) {
				selectCheck = true
				$jq.each($sel.children(), function() {
					if(this.value == data[index].roleId) {
						selectCheck = false;
					}
				});
				
				if(selectCheck) {
					str = data[index].roleName;
					
					var $option = $('<option/>').appendTo($sel).attr('value', data[index].roleId).html(str);
				}
			});
			
			$jq("#roleNameCount").html($sel.children().length);
		} else {
			alert("<ikep4j:message pre='${prefixMessage}' key='noSearchRole' />");
		}
		
		setSecurityValue('security.addrRoleList');
	};
	
	setJobClass = function(data) {
		var $sel = $jq("select[name='security.addrJobClassList']");
		var str = "";
		var selectCheck;
		
		if(data.length > 0) {
			$jq(data).each(function(index) {
				selectCheck = true
				$jq.each($sel.children(), function() {
					if(this.value == data[index].jobClassCode) {
						selectCheck = false;
					}
				});
				
				if(selectCheck) {
					str = data[index].jobClassName;
					
					var $option = $('<option/>').appendTo($sel).attr('value', data[index].jobClassCode).html(str);
				}
			});
			
			$jq("#jobClassNameCount").html($sel.children().length);
		} else {
			alert("<ikep4j:message pre='${prefixMessage}' key='noSearchJobClass' />");
		}
		
		setSecurityValue('security.addrJobClassList');
	};
	
	setJobDuty = function(data) {
		var $sel = $jq("select[name='security.addrJobDutyList']");
		var str = "";
		var selectCheck;
		
		if(data.length > 0) {
			$jq(data).each(function(index) {
				selectCheck = true
				$jq.each($sel.children(), function() {
					if(this.value == data[index].jobDutyCode) {
						selectCheck = false;
					}
				});
				
				if(selectCheck) {
					str = data[index].jobDutyName;
					
					var $option = $('<option/>').appendTo($sel).attr('value', data[index].jobDutyCode).html(str);
				}
			});
			
			$jq("#jobDutyNameCount").html($sel.children().length);
		} else {
			alert("<ikep4j:message pre='${prefixMessage}' key='noSearchJobDutyName' />");
		}
		
		setSecurityValue('security.addrJobDutyList');
	};
	
	setExpUser = function(data) {
		var checkId;
		var selectCheck;
		if(data.length > 0) {
			$('#expUserName').val("");
			$jq(data).each(function(index) {
				selectCheck = true;
				
				if(data[index].id == "${user.userId}") {
					selectCheck = false;
					
					if(checkId != "${user.userId}") {
						checkId = data[index].id;
					
						alert("<ikep4j:message pre='${prefixMessage}' key='noUseMyAccount' />");
					}
				}
				
				$jq.each($selectAddrExpUser.children(), function() {	
					if(this.value == data[index].id) {
						selectCheck = false;
					}					
				});
				
				if(selectCheck) {
					$.tmpl(iKEP.template.userOption, this).appendTo($selectAddrExpUser)
						.data("data", this);
				} 
			});
			
			$jq("#expUserTypeNameCount").html($selectAddrExpUser.children().length);
		} else {
			alert("<ikep4j:message pre='${prefixMessage}' key='noSearchUser' />");
		}
		
		setSecurityValue('security.addrExpUserList');
		
		var $lastItem = $selectAddrExpUser.children(":last");
		$selectAddrExpUser.scrollTop($lastItem.position().top + $lastItem.height());
	};
	
	selectRemove = function (selectName, countName){
		var $sel = $jq("select[name='"+selectName+"']");
		
		if($sel) {
			$jq.each($sel.children(), function() {
				if(this.selected) {
					$(this).remove();
				}
			});
			
			$jq("#"+countName).html($sel.children().length);
		}
		
		setSecurityValue(selectName);
	};
	
	selectedALL = function (selectName){
		var $sel = $jq("select[name='"+selectName+"']");
		
		if($sel) {
			$jq.each($sel.children(), function() {
				this.selected = true;
			});
		}
	};
})(jQuery);

function initSecurityValue(){
	setSecurityValue('security.addrUserList');
	setSecurityValue('security.addrGroupTypeList');
	setSecurityValue('security.addrRoleList');
	setSecurityValue('security.addrJobClassList');
	setSecurityValue('security.addrJobDutyList');
	setSecurityValue('security.addrExpUserList');
};

function setSecurityValue(selectName){
	
	var selValue = "";
	var $sel = $jq("select[name='"+selectName+"']");
	
	if($sel) {
		$jq.each($sel.children(), function(index) {
			if(index == 0){
				selValue = this.value;
			}else{
				selValue = selValue+"^"+this.value;
			}
		});
	}
	
	$jq("input[name='"+selectName+"All']").val(selValue);
};


/** 수정시 기존 등록된 그룹 정보 세팅 **/
function changeHierarchyGroupName(selectName) {
	
	var $sel = $jq("select[name='"+selectName+"']");
	var $obj = "";
	var value = "";
	var text = "";
	var groupId = "";
	
	// option 에 주소록 연계를 위한 데이타 세팅
	<c:forEach var="group" items="${security.aclResourcePermissionRead.groupDetailList}">
		var item = {
			type:'group',				
			code:'${group.groupId}',
			name:'${group.groupName}',
			parent:'${group.parentGroupId}'	
		};
		
		$obj = $sel.find("option[value=${group.groupId}]");
		$obj.data("data", item);			
	</c:forEach>
	
	<c:forEach var="item" items="${security.aclResourcePermissionRead.groupPermissionList}">			
		
		<c:choose>
			<c:when test="${item.hierarchyPermission == 1}">
				$obj = $sel.find("option[value=${item.groupId}]");
				value = {'groupId':'${item.groupId}', 'hierarchyPermission':<c:out value="${item.hierarchyPermission}"/>};
				text = $obj.text();
				
				$obj.val(JSON.stringify(value));
				$obj.text("[H]"+text);					
			</c:when>
			<c:when test="${item.hierarchyPermission == 0}">
				$obj = $sel.find("option[value=${item.groupId}]");
				value = {'groupId':'${item.groupId}', 'hierarchyPermission':<c:out value="${item.hierarchyPermission}"/>};
				
				$obj.val(JSON.stringify(value));
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
	</c:forEach>
			
	setSecurityValue('security.addrGroupTypeList');
}

function changeRadio(value) {
	var optionArray = '${optionList}'.split(",");
	
	if(value == 0) { 
		for(var i=0;i<optionArray.length;i++)
		{
			$jq("#"+optionArray[i]+"_TR").show();
		}
	} else if(value == 1){
		for(var i=0;i<optionArray.length;i++)
		{
			$jq("#"+optionArray[i]+"_TR").hide();
		}
	}
}

changeHierarchyGroupName("security.addrGroupTypeList");
initSecurityValue();
</script>