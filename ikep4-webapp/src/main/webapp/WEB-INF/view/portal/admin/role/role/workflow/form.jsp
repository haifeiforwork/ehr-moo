<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
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
					}
					
					setGroup = function(data) {
						
						var $sel = $jq("select[name=roleGroupList]");
						var str = "";
						var selectCheck;
						
						if(data.length > 0) {
							$jq(data).each(function(index) {
								selectCheck = true;
								$jq.each($sel.children(), function() {
									if(this.value == data[index].groupId) {
										selectCheck = false;
									}
								});
								
								if(selectCheck) {
									str = "&nbsp;"+data[index].groupName;
									var $option = $("<option/>").appendTo($sel).attr('value', data[index].groupId).html(str);
								}
							});
							
							updateCount('group');
						} else {
							alert("<ikep4j:message pre="${prefix}" key="noRetrievedGroup" />");
						}
					};	
					        
					setUser = function(data) {
						var str = "";
						var userId = "";
						var userName = "";
						var jobTitleName = "";
						var teamName = "";
						var mail = "";
						var empNo = "";
						var mobile = "";
						
						var $sel = $jq("select[name=roleUserList]");
						var selectCheck;
						
						if(data.length > 0) {
							$jq(data).each(function(index) {
								selectCheck = true
								$jq.each($sel.children(), function() {
									if(this.value == data[index].userId) {
										selectCheck = false;
									}
								});
								
								if(selectCheck) {
									userId = data[index].userId;
									userName = data[index].userName;
									jobTitleName = data[index].jobTitleName;
									teamName = data[index].teamName;
									mail = data[index].mail;
									empNo = data[index].empNo;
									mobile = data[index].mobile;
									
									str = "&nbsp;"+userName + "/" + jobTitleName + "/" + teamName;
									
									var $option = $("<option/>").appendTo($sel).attr('value', userId).html(str);
									$jq.data($option[0], "data", {id:userId, jobTitle:jobTitleName, name:userName, teamName:teamName, type:"user", searchSubFlag:false});
								} 
							});
							
							updateCount('user');
						} else {
							alert("<ikep4j:message pre="${messagePrefix}" key="noRetrievedUser" />");
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
						
					}
					
					updateCount = function(flag) {
						
						if(flag=='user') {
							$jq("#userCount").html($jq("select[name=roleUserList]").children().length);
						} else {
							$jq("#groupCount").html($jq("select[name=roleGroupList]").children().length);
						}
					}
					
					updateUserCount = function(data) {
				    	var $sel = $jq("select[name=roleUserList]");
				    	$jq("#userCount").html($sel.children().length);
				    }
					
					updateGroupCount = function(data) {
				    	var $sel = $jq("select[name=roleGroupList]");
				    	$jq("#groupCount").html($sel.children().length);
				    }
					
					deleteForm = function() {
						if(confirm("<ikep4j:message pre="${messagePrefix}" key="confirm.wannaDelete" />")) {
							$jq.ajax({
								url : '<c:url value="delete.do" />',
								data : $jq("#roleForm").serialize(),
								type : "post",
								success : function(result) {
									alert("<ikep4j:message pre="${messagePrefix}" key="alert.deleteCompleted" />");
									var searchForm = document.getElementById("roleForm");
									searchForm.action = '<c:url value="list.do" />';
									searchForm.submit();
								},
								error : function(result) {
									alert("<ikep4j:message pre="${messagePrefix}" key="alert.failToDelete" />");
								}
							})
						} else {
							return;
						}
					}
				
					$jq(document).ready(function(){
						
				    	// 셀렉트 박스 카운트 넣기
				    	$jq("#userCount").html($jq("select[name=roleUserList]").children().length);
				    	$jq("#groupCount").html($jq("select[name=roleGroupList]").children().length);
						
						//그룹목록 추가 버튼에 이벤트 바인딩
						$jq("#btnAddGroup").click( function() {
							var items = [];
							var $sel = $jq("#roleForm").find("[name=roleGroupList]");

							$jq.each($sel.children(), function() {
								items.push($.data(this, "data"));
							});
							
							//파라미터(콜백함수, 전송파라미터, 팝업옵션)
							iKEP.showAddressBook(updateGroupCount, items, {selectElement:$sel,selectType:"group",isAppend:true, tabs:{common:1}});
						});
						
						$jq("#btnAddUser").click( function() {
							var items = [];
							var $sel = $jq("#roleForm").find("[name=roleUserList]");
							$jq.each($sel.children(), function() {
								items.push($.data(this, "data"));
							});
							
							//파라미터(콜백함수, 전송파라미터, 팝업옵션)
							iKEP.showAddressBook(updateUserCount, items, {selectElement:$sel,selectType:"user",isAppend:true, tabs:{common:1}});
						});
						
						//그룹 목록의 항목 삭제
						$jq("#btnDelGroup").click(function(){
							if(confirm("<ikep4j:message pre="${messagePrefix}" key="confirm.deleteGroup" />")) {
								var option = 'group';
								removeOptions(option);
								alert("<ikep4j:message pre="${messagePrefix}" key="alert.deleteCompleted" />");
								updateCount('group');
							} else {
								return;
							}
						});
						
						//사용자 목록의 항목 삭제
						$jq("#btnDelUser").click(function(){
							if(confirm("<ikep4j:message pre="${messagePrefix}" key="confirm.deleteUser" />")) {
								var option = 'user';
								removeOptions(option);
								alert("<ikep4j:message pre="${messagePrefix}" key="alert.deleteCompleted" />");
								updateCount('user');
							} else {
								return;
							}
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
							searchForm.action = '<c:url value="list.do" />';
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
									}
								},
								submitHandler : function(role) {
							    	
							    	// 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
									prepareForm();

							    	$jq.ajax({
										url : '<c:url value="onSubmit.do" />',
										data : $jq("#roleForm").serialize(),
										type : "post",
										success : function(result) {
											alert("<ikep4j:message pre="${messagePrefix}" key="alert.saveCompleted" />");
											var searchForm = document.getElementById("roleForm");
											searchForm.action = '<c:url value="list.do" />';
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
						<h2><ikep4j:message pre="${uiPrefix}" key="detail.pageTitle" /></h2>
					</div>
					<!--//pageTitle End--> 
				
					<!--subTitle_2 Start-->
					<div class="subTitle_2 noline">
						<h3><ikep4j:message pre="${uiPrefix}" key="subTitle.1" /></h3>
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
											<input type="text" name="groupName" id="groupName" value="" size="28"/> 
											<a class="button_ic mb5" href="#a" >
												<span id="groupSearchBtn">
													<img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="<ikep4j:message pre='${uiPrefix}' key='searchIcon' />"/>
													<ikep4j:message pre="${uiPrefix}" key="button.search" />
												</span>
											</a>&nbsp;
											<a class="button_ic mb5" href="#a" id="btnAddGroup" name="btnAddGroup">
												<span>
													<img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="<ikep4j:message pre='${uiPrefix}' key='addressBookIcon' />"/>
													<ikep4j:message pre="${uiPrefix}" key="button.addGroup" />
												</span>
											</a>
											<div class="blockLeft" style="width:83%;">
												<select id="roleGroupList" name="roleGroupList" multiple="multiple" size="5" style="width:100%;height:170px;">
													<c:forEach var="groupList" items="${roleGroupList}" varStatus="loopStatus">
														<div id="${groupList.groupId}">
															<c:choose>
																<c:when test="${userLocaleCode == 'ko' }">
																	<option value="${groupList.groupId}" name="groupList[index].groupId" >&nbsp;${groupList.groupName}(${groupList.groupId})</option>
																</c:when>
																<c:otherwise>
																	<option value="${groupList.groupId}" name="groupList[index].groupId" >&nbsp;${groupList.groupEnglishName}(${groupList.groupId})</option>
																</c:otherwise>
															</c:choose>
														</div>
													</c:forEach>
												</select>
											</div>
											<div class="blockRight" style="width:15%;">
												<a class="button_ic mb5" id="btnDelGroup" href="#a">
												<span id="groupDelete">
													<img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" title="<ikep4j:message pre="${uiPrefix}" key="button.delete" />" alt="<ikep4j:message pre="${uiPrefix}" key="button.delete" />"/>
													<ikep4j:message pre="${uiPrefix}" key="button.delete" />
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
											<input type="text" name="userName" id="userName" value="" size="26"/> 
											<a class="button_ic mb5" href="#" >
												<span id="userSearchBtn">
													<img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="<ikep4j:message pre='${uiPrefix}' key='searchIcon' />"/>
													<ikep4j:message pre="${uiPrefix}" key="button.search" />
												</span>
											</a>&nbsp;
											<a class="button_ic mb5" href="#a" id="btnAddUser" name="btnAddUser">
												<span>
													<img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="<ikep4j:message pre='${uiPrefix}' key='addressBookIcon' />"/>
													<ikep4j:message pre="${uiPrefix}" key="button.addUser" />
												</span>
											</a>
											<div class="blockLeft" style="width:83%;">
												<select id="roleUserList" name="roleUserList" multiple="multiple" size="5" style="width:100%;height:170px">
													<c:forEach var="userList" items="${roleUserList}" varStatus="loopStatus">
														<div id="${userList.userId}">
															<c:choose>
																<c:when test="${userLocaleCode == 'ko' }">
																	<option value="${userList.userId}">&nbsp;${userList.userName}/${userList.jobTitleName}/${userList.groupName}</option>
																</c:when>
																<c:otherwise>
																	<option value="${userList.userId}">&nbsp;${userList.userEnglishName}/${userList.jobTitleEnglishName}/${userList.groupEnglishName}</option>
																</c:otherwise>
															</c:choose>
														</div>
													</c:forEach>
												</select>
											</div>
											<div class="blockRight" style="width:15%;">
												<a class="button_ic mb5" id="btnDelUser" href="#a">
												<span id="userDelete">
													<img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" title="<ikep4j:message pre="${uiPrefix}" key="button.delete" />" alt="<ikep4j:message pre="${uiPrefix}" key="button.delete" />"/>
													<ikep4j:message pre="${uiPrefix}" key="button.delete" />
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
											<input type="text" name="groupName" id="groupName" value="" size="28"/> 
											<a class="button" href="#" >
												<span id="groupSearchBtn">
													<img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="<ikep4j:message pre='${uiPrefix}' key='searchIcon' />"/>
													<ikep4j:message pre="${uiPrefix}" key="button.search" />
												</span>
											</a>&nbsp;
											<a class="button" href="#a" id="btnAddGroup" name="btnAddGroup">
												<span>
													<img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="<ikep4j:message pre='${uiPrefix}' key='addressBookIcon' />"/>
													<ikep4j:message pre="${uiPrefix}" key="button.addGroup" />
												</span>
											</a>
											<div>
												<select id="roleGroupList" name="roleGroupList" multiple="multiple" size="5" style="width:85%;height:170px;">
													<c:forEach var="groupList" items="${roleGroupList}" varStatus="loopStatus">
														<div id="${groupList.groupId}">
															<c:choose>
																<c:when test="${userLocaleCode == 'ko' }">
																	<option value="${groupList.groupId}" name="groupList[index].groupId" >&nbsp;${groupList.groupName}(${groupList.groupId})</option>
																</c:when>
																<c:otherwise>
																	<option value="${groupList.groupId}" name="groupList[index].groupId" >&nbsp;${groupList.groupEnglishName}(${groupList.groupId})</option>
																</c:otherwise>
															</c:choose>
														</div>
													</c:forEach>
												</select>
												<a class="button" id="btnDelGroup" href="#a">
												<span id="groupDelete">
													<img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" title="<ikep4j:message pre="${uiPrefix}" key="button.delete" />" alt="<ikep4j:message pre="${uiPrefix}" key="button.delete" />"/>
													<ikep4j:message pre="${uiPrefix}" key="button.delete" />
												</span>
												</a>
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
											<input type="text" name="userName" id="userName" value="" size="26"/> 
											<a class="button" href="#" >
												<span id="userSearchBtn">
													<img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="<ikep4j:message pre='${uiPrefix}' key='searchIcon' />"/>
													<ikep4j:message pre="${uiPrefix}" key="button.search" />
												</span>
											</a>&nbsp;
											<a class="button" href="#a" id="btnAddUser" name="btnAddUser">
												<span>
													<img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="<ikep4j:message pre='${uiPrefix}' key='addressBookIcon' />"/>
													<ikep4j:message pre="${uiPrefix}" key="button.addUser" />
												</span>
											</a>
											<div>
												<select id="roleUserList" name="roleUserList" multiple="multiple" size="5" style="width:85%;height:170px">
													<c:forEach var="userList" items="${roleUserList}" varStatus="loopStatus">
														<div id="${userList.userId}">
															<c:choose>
																<c:when test="${userLocaleCode == 'ko' }">
																	<option value="${userList.userId}">&nbsp;${userList.userName}/${userList.jobTitleName}/${userList.groupName}</option>
																</c:when>
																<c:otherwise>
																	<option value="${userList.userId}">&nbsp;${userList.userEnglishName}/${userList.jobTitleEnglishName}/${userList.groupEnglishName}</option>
																</c:otherwise>
															</c:choose>
														</div>
													</c:forEach>
												</select>
												<a class="button" id="btnDelUser" href="#a">
												<span id="userDelete">
													<img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" title="<ikep4j:message pre="${uiPrefix}" key="button.delete" />" alt="<ikep4j:message pre="${uiPrefix}" key="button.delete" />"/>
													<ikep4j:message pre="${uiPrefix}" key="button.delete" />
												</span>
												</a>
												<ikep4j:message pre="${uiPrefix}" key="total.label" /> <span id="userCount">0</span><ikep4j:message pre="${uiPrefix}" key="total.persons" />
											</div>
										</td>
									</tr>
								</tbody>
							</table>		
						</div>
					</div>
					<!--//blockDetail End-->
					
					<div class="blockBlank_10px"></div>
					
					<!--blockButton Start-->
					<div class="blockButton">
						<ul>
							<li><a class="button" id="saveButton" href="#a"><span><ikep4j:message pre="${uiPrefix}" key="button.save" /></span></a></li>
							<li><a class="button" id="deleteButton" href="#a"><span><ikep4j:message pre="${uiPrefix}" key="button.delete" /></span></a></li>
							<li><a class="button" id="listButton" href="#a"><span><ikep4j:message pre="${uiPrefix}" key="button.list" /></span></a></li>
						</ul>
					</div>
					<!--//blockButton End-->
					
					<input type="hidden" id="itemTypeCode" name="itemTypeCode" value="PO"/>
					<input type="hidden" id="oldRoleId" name="oldRoleId" value="${currRoleId}"/>
					<input type="hidden" id="createFlag" name="createFlag" value="${role.codeCheck}"/>
					
				</form>