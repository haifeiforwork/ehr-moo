<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="prefix" value="message.portal.admin.role.role.form"/>
<c:set var="messagePrefix" value="message.portal.admin.role.role.list"/>
<c:set var="uiPrefix" value="ui.portal.admin.role.role"/>

	<script type="text/javascript">
				//<!--
				(function($) {
					
					removeOptions = function(option) {
						
						if(option == 'user') {
							$jq("#roleUserList option:selected").remove();			
						} else if(option == 'group') {
							$jq("#roleGroupList option:selected").remove();
						} else {
							return;
						}
					};
					
					setGroup = function(data) {
						var $sel = $jq("#roleGroupList");
						var selectCheck;
						
						if(data.length > 0) {
							$jq(data).each(function(index) {
								selectCheck = true;
								$jq.each($sel.children(), function() {
									if(this.value == data[index].code) {
										selectCheck = false;
									}
								});
								
								if(selectCheck) {
									$.tmpl(iKEP.template.groupOption, this).appendTo($sel)
										.data("data", this);
									$jq("#groupName").val("");
								}
							});
							
							updateCount('group');
						} else {
							alert("<ikep4j:message pre="${prefix}" key="alert.noRetrievedGroup" />");
						}
					};	
					        
					setUser = function(data) {
						var $sel = $jq("#roleUserList");
						var selectCheck;
						
						if(data.length > 0) {
							$jq(data).each(function(index) {
								selectCheck = true;
								$jq.each($sel.children(), function() {
									if(this.value == data[index].id) {
										selectCheck = false;
									}
								});
								
								if(selectCheck) {
									$.tmpl(iKEP.template.userOption, this).appendTo($sel)
										.data("data", this);
									
									$jq("#userName").val("");
								} 
							});
							
							updateCount('user');
						} else {
							alert("<ikep4j:message pre="${messagePrefix}" key="alert.noRetrievedUser" />");
						}
					};
					
					prepareForm = function() {
						//역할의 이름을 현재 세션에 있는 유저의 Localecode값이 ID인 녀석의 Value로 매핑
						
						var groups = "";
						var users = "";
						
						var sel = $jq("#roleForm").find("[name=roleGroupList]");
						var sel = $jq("#roleForm").find("[name=roleUserList]");
						
						$jq("#roleGroupList option").each(function (index) {
							groups = groups + "<input name=\"groupList["+ index +"].groupId\" value=\""+ $jq(this).val() + "\" type=\"hidden\"/>";
						});
						
						$jq("#roleUserList option").each(function (index) {
							users = users + "<input name=\"userList["+ index +"].userId\" value=\""+ $jq(this).val() + "\" type=\"hidden\"/>";
						});
						
						$jq("#roleForm").append(groups);
						$jq("#roleForm").append(users);
						
					};
					
					updateCount = function(flag) {
						
						if(flag=='user') {
							$jq("#userCount").html($jq("select[name=roleUserList]").children().length);
						} else {
							$jq("#groupCount").html($jq("select[name=roleGroupList]").children().length);
						}
					};
					
					updateUserCount = function(data) {
				    	var $sel = $jq("select[name=roleUserList]");
				    	$jq("#userCount").html($sel.children().length);
				    };
					
					updateGroupCount = function(data) {
				    	var $sel = $jq("select[name=roleGroupList]");
				    	$jq("#groupCount").html($sel.children().length);
				    };
					
					deleteForm = function() {
						if(confirm("역할을 삭제하시면 해당 역할에 관련된 모든 정보가 삭제됩니다. 삭제하시겠습니까?")) {
							$jq.ajax({
								url : '<c:url value="delete.do" />',
								data : $jq("#roleForm").serialize(),
								type : "post",
								success : function(result) {
									alert("<ikep4j:message pre="${messagePrefix}" key="alert.deleteCompleted" />");
									var searchForm = document.getElementById("roleForm");
									searchForm.action = '<c:url value="/lightpack/overtimework/roleList.do" />';
									searchForm.submit();
								},
								error : function(result) {
									alert("<ikep4j:message pre="${messagePrefix}" key="alert.failToDelete" />");
								}
							})
						} else {
							return;
						}
					};
				
					$jq(document).ready(function(){
						
						var tmpUserOption = $.template(null, '<option value="\${id}">\${userName} \${jobTitleName} \${teamName}</option>');
						var tmpGroupOption = $.template(null, '<option value="\${code}">\${name}</option>');
				
						var $roleUserList = $("select[name=roleUserList]").children().remove().end();
						<c:forEach var="item" items="${roleUserList}">							
							var item = {
								type : "user",
								empNo : "",
								id : "${item.userId}",
								userName : "${user.localeCode == portal.defaultLocaleCode ? item.userName : item.userEnglishName}",
								jobTitleName : "${user.localeCode == portal.defaultLocaleCode ? item.jobTitleName : item.jobTitleEnglishName}",
								groupId : "",
								teamName : "${user.localeCode == portal.defaultLocaleCode ? item.groupName : item.groupEnglishName}",
								email : "",
								mobile : ""
							};
			
							$.tmpl(tmpUserOption, item).appendTo($roleUserList)
								.data("data", item);							
						</c:forEach>						
				    	
				    	var $roleGroupList = $("select[name=roleGroupList]").children().remove().end();
						<c:forEach var="item" items="${roleGroupList}">
														
							var item = {
								type : "group",
								code : "${item.groupId}",
								name : "${user.localeCode == portal.defaultLocaleCode ? item.groupName : item.groupEnglishName}",
								parent : ""
							};

							$.tmpl(tmpGroupOption, item).appendTo($roleGroupList)
								.data("data", item);
							
						</c:forEach>
						
						//left menu setting
						$jq("#roleLinkOfLeft").click();
						
				    	// 셀렉트 박스 카운트 넣기
				    	$jq("#userCount").html($jq("select[name=roleUserList]").children().length);
				    	$jq("#groupCount").html($jq("select[name=roleGroupList]").children().length);
						
						//그룹목록 추가 버튼에 이벤트 바인딩
						$jq("#btnAddGroup").click( function() {
							var $sel = $jq("#roleGroupList");
							var items = $jq.map($sel.children(), function(option) {
								return $.data(option, "data");
							});
							
							//파라미터(콜백함수, 전송파라미터, 팝업옵션)
							iKEP.showAddressBook(updateGroupCount, items, {selectElement:$sel,selectType:"group",tabs:{common:1}});
						});
						
						$jq("#btnAddUser").click( function() {
							var $sel = $jq("#roleUserList");
							var items = $jq.map($sel.children(), function(option) {
								return $(option).data("data");
							});
							
							//파라미터(콜백함수, 전송파라미터, 팝업옵션)
							iKEP.showAddressBook(updateUserCount, items, {selectElement:$sel,selectType:"user", tabs:{common:1}});
						});
						
						//그룹 목록의 항목 삭제
						$jq("#btnDelGroup").click(function(){
								var option = 'group';
								removeOptions(option);
								updateCount('group');
						});
						
						//사용자 목록의 항목 삭제
						$jq("#btnDelUser").click(function(){
							var option = 'user';
							removeOptions(option);
							updateCount('user');
						});
				
						$jq("#groupName").keypress( function(event) { 
							iKEP.searchGroup(event, "Y", setGroup); 
						});
						
						$jq("#groupSearchBtn").click( function() { 
							$jq("#groupName").trigger("keypress");
						});
						
						$jq("#userName").keypress( function(event) { 
							iKEP.searchUser(event, "Y", setUser); 
						});
						
						$jq("#userSearchBtn").click( function() { 
							$jq("#userName").trigger("keypress");
						});
						
						// 등록/수정
						$jq("#saveButton").click(function(){
							$jq("#roleForm").trigger("submit");
						});
						
						// 삭제
						$jq("#deleteButton").click(function(){
							deleteForm();
						});
						
						// 목록
						$jq("#listButton").click(function(){
							var searchForm = document.getElementById("roleForm");
							searchForm.action = '<c:url value="/lightpack/overtimework/roleList.do" />';
							searchForm.submit();
						});
						
						// 백스페이스 방지(input, select 제외)
						$jq(document).keydown(function(e) {
							var element = e.target.nodeName.toLowerCase();
							if (element != 'input' && element != 'option') {
							    if (e.keyCode === 8) {
							        return false;
							    }
							}
						});
						
						var validOptions = {
								rules : {
									roleName : {
										required : true,
										rangelength : [0, 20]
									},
									roleEnglishName : {
										required : true,
										rangelength : [0, 100]
									},
									description : {
										required : true,
										rangelength : [0, 150]
									},
									roleTypeId : {
										required : true
									}
								},
								messages : {
									roleName : {
										required : "<ikep4j:message key="NotNull.role.roleName" />",
										rangelength : "<ikep4j:message key="Size.role.roleName" />"
									},
									roleEnglishName : {
										required : "<ikep4j:message key="NotNull.role.roleEnglishName" />",
										englishName : "<ikep4j:message key="EnglishName.role.roleEnglishName" />",
										rangelength : "<ikep4j:message key="Size.role.roleEnglishName" />"
									},
									description : {
										required : "<ikep4j:message key="NotNull.role.description" />",
										rangelength : "<ikep4j:message key="Size.role.description" />"
									},
									roleTypeId : {
										required : "<ikep4j:message key="NotNull.role.roleType" />"
									}
								},
								submitHandler : function(role) {
							    	
							    	// 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
									prepareForm();

							    	$jq.ajax({
										url : '<c:url value="/lightpack/overtimework/roleOnSubmit.do" />',
										data : $jq("#roleForm").serialize(),
										type : "post",
										success : function(result) {
											alert("<ikep4j:message pre="${messagePrefix}" key="alert.saveCompleted" />");
											var searchForm = document.getElementById("roleForm");
											searchForm.action = '<c:url value="/lightpack/overtimework/roleList.do" />';
											searchForm.submit();
										},
// 										error : function(result) {
// 											alert("<ikep4j:message pre="${messagePrefix}" key="alert.failToSave" />");
// 										},
 										error : function(xhr, exMessage) {
											var errorItems = $.parseJSON(xhr.responseText).exception;
											validator.showErrors(errorItems);
										}
									});
								}
							};
							
							var validator = new iKEP.Validator("#roleForm", validOptions);
						
					});	
				})(jQuery);
		//-->
		</script>
				
		<form id="roleForm" name="roleForm" method="post" onsubmit="return false;" action="">
		
			<!--pageTitle Start-->
			<div id="pageTitle">
				<h2>휴일근무 권한관리</h2>
			</div>
			<!--//pageTitle End--> 
		
			<!--subTitle_2 Start-->
			<!--//subTitle_2 End-->
			
			<!--blockDetail Start-->
			<div class="blockDetail">
				<table>
					<caption></caption>
					<colgroup>
						<col width="15%"/>
						<col width="10%"/>
						<col width="75%"/>
					</colgroup>
					<tbody>
						<tr>
							<th colspan="2">구분ID</th>
							<td>
								<div>${role.roleName}
									<input type="hidden" name="roleName" id="roleName" value="${role.roleName}" size="50" class="inputbox" readonly="readonly"/>
								</div>
							</td>
						</tr>
						<input type="hidden" name="roleEnglishName" id="roleEnglishName" value="${role.roleEnglishName}" size="50" class="inputbox" />
						<tr>
							<th colspan="2">구분명</th>
							<td>
								<div>${role.description}
									<input type="hidden" id="description" name="description" value="${role.description}" size="50" class="inputbox" readonly="readonly" />
								</div>
							</td>
						</tr>		
						<input type="hidden" name="roleTypeId" id="roleTypeId" value="${role.roleTypeId}" size="50" class="inputbox" />			
					</tbody>
				</table>
			</div>
			<!--//blockDetail End-->
			
			<div class="blockBlank_10px"></div>
			
			<!--subTitle_2 Start-->
			<div class="subTitle_2 noline">
				<h3><ikep4j:message pre="${uiPrefix}" key="subTitle.2" /></h3>
			</div>
			<!--//subTitle_2 End-->
			
			<!--blockDetail Start-->					
			<div class="blockDetail" style="height:240px;">
				<div class="blockLeft" style="width:50%;">
					<table summary="<ikep4j:message pre="${uiPrefix}" key="tableSummary.groupList" />">
						<caption><ikep4j:message pre="${uiPrefix}" key="table.caption.groupList" /></caption>
						<tbody>
							<tr>
								<th class="textLeft"><ikep4j:message pre="${uiPrefix}" key="table.groupList" /></th>
							</tr>
							<tr>
								<td>
									<input type="text" name="groupName" id="groupName" value="" size="28" class="inputbox" /> 
									<a class="button_img05" href="#a" >
										<span id="groupSearchBtn">
											<ikep4j:message pre="${uiPrefix}" key="button.search" />
										</span>
									</a>&nbsp;
									<a class="button_img05" href="#a" id="btnAddGroup" name="btnAddGroup">
										<span>
											<ikep4j:message pre="${uiPrefix}" key="button.addGroup" />
										</span>
									</a>
									<div class="blockLeft" style="width:83%;">
										<select id="roleGroupList" name="roleGroupList" multiple="multiple" size="5" style="width:100%;height:170px;">
										</select>
									</div>
									<div class="blockRight" style="width:15%;">
										<a class="button_img05" id="btnDelGroup" href="#a">
										<span id="groupDelete">
											그룹 삭제
										</span>
										</a><br>
										<ikep4j:message pre="${uiPrefix}" key="total.label" /> <span id="groupCount">0</span><ikep4j:message pre="${uiPrefix}" key="total.group" />
									</div>
								</td>
							</tr>
						</tbody>
					</table>		
				</div>
				<div class="blockRight" style="width:50%;">
					<table summary="<ikep4j:message pre="${uiPrefix}" key="tableSummary.userList" />">
						<caption><ikep4j:message pre="${uiPrefix}" key="table.caption.userList" /></caption>
						<tbody>
							<tr>
								<th class="textLeft"><ikep4j:message pre="${uiPrefix}" key="table.userList" /></th>
							</tr>
							<tr>
								<td>
									<input type="text" name="userName" id="userName" value="" size="26" class="inputbox" /> 
									<a class="button_img05" href="#" >
										<span id="userSearchBtn">
											<ikep4j:message pre="${uiPrefix}" key="button.search" />
										</span>
									</a>&nbsp;
									<a class="button_img05" href="#a" id="btnAddUser" name="btnAddUser">
										<span>
											<ikep4j:message pre="${uiPrefix}" key="button.addUser" />
										</span>
									</a>
									<div class="blockLeft" style="width:83%;">
										<select id="roleUserList" name="roleUserList" multiple="multiple" size="5" style="width:100%;height:170px">
										</select>
									</div>
									<div class="blockRight" style="width:15%;">
										<a class="button_img05" id="btnDelUser" href="#a">
										<span id="userDelete">
											사용자 삭제
										</span>
										</a><br>
										<ikep4j:message pre="${uiPrefix}" key="total.label" /> <span id="userCount">0</span><ikep4j:message pre="${uiPrefix}" key="total.persons" />
									</div>
								</td>
							</tr>
						</tbody>
					</table>		
				</div>
			</div>
			<!--//blockDetail End-->
			
			<!--blockButton Start-->
			<div class="nblockButton">
					<a class="button_img01" id="saveButton" href="#a"><span><ikep4j:message pre="${uiPrefix}" key="button.save" /></span></a>
					<!-- <li><a class="button" id="deleteButton" href="#a"><span>역할 삭제</span></a></li> -->
					<a class="button_img01" id="listButton" href="#a"><span><ikep4j:message pre="${uiPrefix}" key="button.list" /></span></a>
			</div>
			<!--//blockButton End-->
			
			<input type="hidden" id="itemTypeCode" name="itemTypeCode" value="PO"/>
			<input type="hidden" id="oldRoleId" name="oldRoleId" value="${currRoleId}"/>
			<input type="hidden" id="createFlag" name="createFlag" value="${role.codeCheck}"/>
			<input type="hidden" id="roleId" name="roleId" value="${role.roleId}"/>
			
		</form>