<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="uiPrefix" value="ui.portal.admin.group.group"/>
<c:set var="listPrefix" value="message.portal.admin.group.group.list"/>

				<script type="text/javascript">
				//<!--
				(function($) {
					
					//엑셀파일 업로드 후 수행하는 함수
					afterUpload = function(status, fileId, fileName, message, targetId) {
						//$jq("#viewDiv").html(fileId);
						//$jq("input[name=fileId]").val(fileId);
					};
					
					updateUserCount = function(data) {
				    	$("#userCount").html($("#groupUserList").children().length);
				    	
				    	var leaderId = $("#leaderId").val();
				    	if(leaderId) {
				    		var removeLeader = true;
							$jq(data).each(function() {
								if(leaderId == this.id) {
									removeLeader = false;
									return false;
								}
							});
							if(removeLeader == true) {
								setTimeout(function() {	// dialog popup에 가려져서...
									alert("<ikep4j:message pre="${listPrefix}" key="alert.deletedLeader" />");
									$("#leaderList").empty();
									$("#leaderId").val("");
								}, 0);
							}
				    	}
					};
				    
				    setUser = function(data) {
						var $sel = $jq("#leaderList").empty();
						
						if(data.length > 0) {	// 1
							$jq(data).each(function(index) {
								$.tmpl(iKEP.template.userOption, this).appendTo($sel)
									.data();
								
								// leaderId 세팅
								$jq("#leaderId").val(this.id);
								$jq("#searchLeaderId").val("");
								
								appendUserListForLeader(this);
							});
						} else {
							alert("<ikep4j:message pre="${listPrefix}" key="noRetrievedUser" />");
						}
					};
					
					setLeaderId = function(data) {
						var $sel = $jq("#leaderList").empty();
						$jq.each(data, function() {
							$.tmpl(iKEP.template.userOption, this).appendTo($sel)
								.data("data", this);
							
							$jq("#leaderId").val(this.id);
							
							appendUserListForLeader(this);
						});
					};
					
					appendUserListForLeader = function(leaderInfo) {	// 해당 리더를 조직원으로 추가
						var isLeader = false;
						$("#groupUserList").children().each(function() {
							if(leaderInfo.id == $(this).val()) {
								isLeader = true;
								return false;
							}
						});
						if(!isLeader) {
							$.tmpl(iKEP.template.userOption, leaderInfo).appendTo($("#groupUserList"))
								.data("data", leaderInfo);
						}
					};
					
				    $jq(document).ready(function() {
				    	var $groupUserList = $("#groupUserList").children().remove().end();
						
				    	<c:forEach var="item" items="${userList}">
							var item = {
								type : "user",
								empNo : "",
								id : "${item.userId}",
								userName : "${user.localeCode == portal.defaultLocaleCode ? item.userName : item.userEnglishName}",
								jobTitleName : "${user.localeCode == portal.defaultLocaleCode ? item.jobTitleName : item.jobTitleEnglishName}",
								groupId : "${item.groupId}",
								teamName : "${user.localeCode == portal.defaultLocaleCode ? item.teamName : item.teamEnglishName}",
								email : "${item.mail}",
								mobile : ""
							};

							$.tmpl(iKEP.template.userOption, item).appendTo($groupUserList)
								.data("data", item);
						</c:forEach>
				    	
				    	$jq("#userCount").html($jq("#groupUserList").children().length);

						//주소록 버튼에 이벤트 바인딩
						$jq('#btnAddrControl').click( function() {
							var $select = $jq("#groupUserList");
							var items = $.map($select.children(), function(option) {
								return $(option).data("data");
							});
							
							//파라미터(콜백함수, 전송파라미터, 팝업옵션)
							iKEP.showAddressBook(updateUserCount, items, {selectElement:$select,selectType:"user",tabs:{common:1}});
						});
						
						$jq("#btnAddrDelete").click(function(){
							// 셀렉트박스에서 선택된 값들을 제거
							var removeableLeader = removeCheckUserForLeader();
							var removeLeader = false;
							if( (removeableLeader && (removeLeader = confirm("<ikep4j:message pre="${listPrefix}" key="confirm.leaderDelete" />"))) ||
									confirm("<ikep4j:message pre="${listPrefix}" key="confirm.wannaDeleteUsers" />") ) {
								if(removeableLeader == true) {	// 리더가 삭제대상 사용자 목록에 있으면...
									if(removeLeader == false) {	// 리더는 조직원에서 삭제 배제
										var leaderId = $jq("#leaderId").val();
										$jq("#groupUserList option:selected").each(function() {
											if(leaderId == $(this).attr("value")) {
												$(this).attr("selected", false);
												return false;
											}
										});
									} else {	// 리더를 함께 삭제
										$jq("#leaderList").empty();
										$jq("#leaderId").val("");
									}
								}
								$jq("#groupUserList option:selected").remove();
								$jq("#userCount").html($jq("#groupUserList").children().length);
							} else {
								return;
							} 
						});
						
						removeCheckUserForLeader = function() {
							var removeable = false;
							var leaderId = $jq("#leaderId").val();
							$jq("#groupUserList option:selected").each(function() {
								if(leaderId == $(this).val()) {
									removeable = true;
								}
							});
							return removeable;
						};
						
						$jq("#cancelButton").click(function() {
							$jq("#checkCodeFlag").val("");
							getForm();
						});
						
						$jq("#submitButton").click(function() {
							$jq("#groupForm").trigger("submit");
						});
						
						$jq("#deleteButton").click(function() {
							deleteForm();
						});
						
						$jq("#btnAddLeader").click( function() {
// 							var items = [];
// 							var $sel = $jq("#groupForm").find("[name=leaderList]");
// 							$jq.each($sel.children(), function() {
// 								items.push($.data(this, "data"));
// 							});
							
							//파라미터(콜백함수, 전송파라미터, 팝업옵션)
							iKEP.showAddressBook(setLeaderId, "", {selectType:"user", isAppend:false, selectMaxCnt:1, tabs:{common:1}});
						});
						
						$jq("#btnDelLeader").click(function(){
							if(confirm("<ikep4j:message pre="${listPrefix}" key="confirm.wannaDeleteUsers" />")) {
								$jq("#leaderList").empty();
								$jq("#leaderId").val("");
								alert("<ikep4j:message pre="${listPrefix}" key="alert.deleteCompleted" />");
							}
						});
						
						$jq("#searchLeaderId").keypress( function(event) { 
							iKEP.searchUser(event, "N", setUser); 
						});
						
						$jq("#userSearchBtn").click( function() { 
							$jq("#searchLeaderId").trigger("keypress");
						});
						
						//엑셀파일 업로드 버튼 클릭이벤트 처리
						$jq('#groupExcelUpload').click(function(event) {
							var url = iKEP.getContextRoot() + '/portal/admin/group/group/excelForm.do';			
							iKEP.popupOpen(url, {width:800, height:210});
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
						
						$jq("#groupForm :input:visible:enabled:first").focus().select();
						
						var validOptions = {
								rules : {
									groupName : {
										required : true,
										rangelength : [0, 20]
									},
									groupEnglishName : {
										required : true,
										rangelength : [0, 100]
									}
							    },
							    messages : {
							    	groupName : {
										required : "<ikep4j:message key="NotNull.group.groupName" />",
										rangelength : "<ikep4j:message key="Size.group.groupName" />"
									},
									groupEnglishName : {
										required : "<ikep4j:message key="NotNull.group.groupEnglishName" />",
										englishName : "<ikep4j:message key="EnglishName.group.groupEnglishName" />",
										rangelength : "<ikep4j:message key="Size.group.groupEnglishName" />"
									}
							    },
							    submitHandler : function(groupForm) {
							    	
									prepareUserList();
									
									var checkFlag = $jq("#checkCodeFlag").val();
									var groupId = $jq("#groupId").val();
									var rootGroupCount = ${rootGroupCount};

									if(groupId || rootGroupCount == 0) {
										if (checkFlag == "success") {
											$jq("#groupTree").jstree("save_opened");
											$jq.ajax({
												url : '<c:url value="createGroup.do" />',
												data : $jq("#groupForm").serialize(),
												type : "post",
												success : function(result) {
													alert("<ikep4j:message pre="${listPrefix}" key="alert.saveCompleted" />");
													var groupId = $jq("#groupId").val();
													var parentGroupId = $jq("#parentGroupId").val();
													var groupTypeId = $jq("#groupTypeId").val();
													
													if(rootGroupCount > 0) {
														if(($jq(tempCurrItem).attr("class")=='jstree-closed jstree-last')
																||$jq(tempCurrItem).attr("class")=='jstree-last jstree-closed'
																||($jq(tempCurrItem).attr("class")=='jstree-closed')) {
															//노드가 한번도 열리지 않은 경우 
															$jq("#groupTree").jstree("open_node",tempCurrItem,false,false);
															
														} else {
															//노드가 한번이라도 열렸던 경우 아래 코드 수행
															$jq("#groupTree").jstree("refresh",tempCurrItem);
															$jq("#groupTree").jstree("open_node",tempCurrItem.children(),false,false);
														}
													} else {
														$jq("#groupTree").jstree("refresh");
													}
													
													//방금 생성한 노드를 바로 수정하는 경우를 대비하여 tempParent를 새로 매핑한다.
													//하지 않으면 tempParent가 undefined로 에러 발생
													tempParent = tempCurrItem;
													//groupId = $jq.parseJSON(tempCurrItem.attr("data")).code;
													
													getForm(result, '');
												},
		 										error : function(xhr, exMessage) {
													var errorItems = $.parseJSON(xhr.responseText).exception;
													validator.showErrors(errorItems);
												}
											});
										} else if(checkFlag == "modify") {
											$jq.ajax({
												url : '<c:url value="createGroup.do" />',
												data : $jq("#groupForm").serialize(),
												type : "post",
												success : function(result) {
													alert("<ikep4j:message pre="${listPrefix}" key="alert.saveCompleted" />");
													var groupId = $jq("#groupId").val();
													var parentGroupId = $jq("#parentGroupId").val();
													var groupTypeId = $jq("#groupTypeId").val();
													
													// tempParent는 수정된 노드의 부모노드이며 refresh 후 그 자식들 레벨까지 노드를 오픈
													$jq("#groupTree").jstree("refresh",tempParent);
													$jq("#groupTree").jstree("open_node",tempParent.children(),false,false);
													
													getForm(result, '');
												},
		 										error : function(xhr, exMessage) {
													var errorItems = $.parseJSON(xhr.responseText).exception;
													validator.showErrors(errorItems);
												}
											});								
										} else {
											alert("<ikep4j:message pre="${listPrefix}" key="alert.checkDuplicated" />");
											return;
										}							
									} else {
										alert("<ikep4j:message pre="${listPrefix}" key="alert.selectParent" />");
										return;
									}
								}

						 };

						var validator = new iKEP.Validator("#groupForm", validOptions);
					});
				// java script 전역변수 항목에 추가
				$.template("addrBookItemUser", '<option value="\${id}">\${userName}(${id})/\${jobTitleName}/\${teamName}</option>');
				$.template("addrBookItemGroup", '<option value="\${code}">\${name}</option>');

				// 주의 위에 code 가 가이드 입력시 오류로 입력되지 않아서 부득이하게 공백으로 입력
				// 추후 사용시 공백 제거후 사용 해주세요

				})(jQuery);
				//-->
				</script>

				<form id="groupForm" name="groupForm" method="post" onsubmit="return false;" action="">

				
					<!--blockDetail Start-->					
					<div class="blockDetail clear">
						<table summary="<ikep4j:message pre="${uiPrefix}" key="tableSummary" />">
							<caption></caption>
							<colgroup>
								<col width="25%">
								<col width="75%">
							</colgroup>
							<tbody>
								<c:choose>
									<c:when test="${createFlag == 'modify'}">
										<tr>
											<th scope="col"><ikep4j:message pre="${uiPrefix}" key="groupId" /></th>
											<td class="testLeft">${group.groupId}</td>
												<input type="hidden" id="groupId" name="groupId" value="${group.groupId}"/>
												<input type="hidden" id="checkCodeFlag" name="checkCodeFlag" value="${group.checkCodeFlag}"/>
										</tr>
									</c:when>
									<c:otherwise>
										<input type="hidden" id="groupId" name="groupId" class="inputbox" style="width:77%" title="<ikep4j:message pre="${uiPrefix}" key="groupId" />" value="${group.groupId}" />
										<input type="hidden" id="checkCodeFlag" name="checkCodeFlag" value="success"/>
									</c:otherwise>
								</c:choose>
								<tr>
									<th scope="col"><span class="colorPoint">*</span> <ikep4j:message pre="${uiPrefix}" key="groupName" /></th>
									<td class="testLeft">
										<div>
											<input id="groupName" name="groupName" type="text" class="inputbox" style="width:97%" title="그룹명" value="${group.groupName}" class="inputbox" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="col"><span class="colorPoint">*</span> <ikep4j:message pre="${uiPrefix}" key="groupEnglishName" /></th>
									<td class="testLeft">
										<div>
											<input id="groupEnglishName" name="groupEnglishName" type="text" class="inputbox" style="width:97%" title="영문그룹명" value="${group.groupEnglishName}" class="inputbox" />
										</div>
									</td>
								</tr>								
								<c:choose>
									<c:when test="${not empty group.parentGroupId}">
										<tr>
											<th scope="col"><ikep4j:message pre="${uiPrefix}" key="parentInfo" /></th>
											<td class="testLeft">
												<span id="parentGroupInfo">${group.parentGroupName} / ${group.parentGroupId}</span>
											</td>
										</tr>
									</c:when>
									<c:otherwise>

									</c:otherwise>
								</c:choose>
								<!--  
								<tr>
									<th scope="col"><ikep4j:message pre="${uiPrefix}" key="visibleOption" /></th>
									<td class="testLeft">
										<input type="radio" id="viewOptionYes" name="viewOption" class="radioButton" title="<ikep4j:message pre="${uiPrefix}" key="visibleOption" />" value="1" <c:if test="${group.viewOption eq '1'}">checked="checked"</c:if>/>
											<lable for="viewOptionYes"><ikep4j:message pre="${uiPrefix}" key="visible" /></lable>
										<input type="radio" id="viewOptionNo" name="viewOption" class="radioButton" title="<ikep4j:message pre="${uiPrefix}" key="visibleOption" />" value="0" <c:if test="${group.viewOption eq '0'}">checked="checked"</c:if>/>
											<lable for="viewOptionNo"><ikep4j:message pre="${uiPrefix}" key="invisible" /></lable>
									</td>
								</tr>
								-->
								<tr>
									<th scope="col"><ikep4j:message pre="${uiPrefix}" key="leaderId" /></th>
									<td class="testLeft">
										<div style="height:25px">
											<input type="text" name="searchLeaderId" id="searchLeaderId" value="" style="width:43%" class="inputbox"/> 
											<a class="button_ic mb5" id="userSearchBtn" name="userSearchBtn" href="#a"><span><ikep4j:message pre="${uiPrefix}" key="button.search" /></span></a>&nbsp;
											<a class="button_ic mb5" id="btnAddLeader" name="btnAddLeader" href="#a"><span><ikep4j:message pre="${uiPrefix}" key="button.addUser" /></span></a>
										</div>
										<div class="blockLeft" style="width:85%;">
											<select id="leaderList" name="leaderList" size="2" style="width:100%;height:25px">
												<c:choose>
													<c:when test="${empty group.leaderId}">
														<option><ikep4j:message pre="${listPrefix}" key="chooseALeader" /></option>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${userLocaleCode == 'ko' }">
																<option value="${group.leaderId}">${group.leaderName} ${group.leaderJobTitle} ${group.leaderTeamName}</option>
															</c:when>
															<c:otherwise>
																<option value="${group.leaderId}">${group.leaderEnglishName} ${group.leaderEnglishJobTitle} ${group.leaderTeamEnglishName}</option>
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</select>
											<input type="hidden" name="leaderId" id="leaderId" value="${group.leaderId}"/>
										</div>
										<div class="blockRight" style="width:13%;">
											<a class="button_ic mb5" id="btnDelLeader" href="#a"><span id="actionBtn">사용자 삭제</span></a>											
										</div>
									</td>
								</tr>
								<tr>
									<th scope="col"><ikep4j:message pre="${uiPrefix}" key="userList" /></th>
									<td class="testLeft">
									<div class="blockLeft" style="width:85%;">
										<select id="groupUserList" name="groupUserList" multiple="multiple" size="5" style="width:100%;height:170px">
										</select>
									</div>
									<div class="blockRight" style="width:13%;">
										<ul>
											<li><a class="button_ic mb5" id="btnAddrControl" href="#a"><span id="actionBtn"><ikep4j:message pre="${uiPrefix}" key="button.addUser" /></span></a></li>	
											<li><a class="button_ic mb5" id="btnAddrDelete"  href="#a"><span>사용자 삭제</span></a></li>
										</ul>
										
										<ikep4j:message pre="${uiPrefix}" key="total.label" /> <span id="userCount">0</span><ikep4j:message pre="${uiPrefix}" key="total.userCount" />
										
										<input name="controlTabType" title="" type="hidden" value="1:0:1:0" />
										<input name="controlType" title="" type="hidden" value="ORG" />
										<input name="selectType" title="" type="hidden" value="USER" />
										<input name="selectMaxCnt" title="" type="hidden" value="10" />
										<input name="searchSubFlag" title="" type="hidden" value="" />
									</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!--//blockDetail End-->
					
					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<li><a class="button" id="cancelButton" href="#a"><span><ikep4j:message pre="${uiPrefix}" key="button.cancel" /></span></a></li>
							<li><a class="button" id="submitButton" href="#a"><span><ikep4j:message pre="${uiPrefix}" key="button.save" /></span></a></li>
							<li><a class="button" id="deleteButton" href="#a"><span>그룹 삭제</span></a></li>						
							
							<!-- <li><a class="button" id="btnSave" href="javascript:submitForm()"><span id="actionBtn"><ikep4j:message pre="${uiPrefix}" key="button.save" /></span></a></li>	
							<li><a class="button" id="btnCancel" href="javascript:getForm()"><span><ikep4j:message pre="${uiPrefix}" key="button.cancel" /></span></a></li>
							<li><a class="button" id="btnDelete" href="javascript:deleteForm()"><span id="actionBtn"><ikep4j:message pre="${uiPrefix}" key="button.delete" /></span></a></li> -->
							<li>
								<a class="button" href="#" ><span name="groupExcelUpload" id="groupExcelUpload"><ikep4j:message pre="${uiPrefix}" key="button.bulkInsert" /></span></a>
							</li>
						</ul>
					</div>
					<!--//blockButton End-->
					<input type="hidden" id="viewOption" name="viewOption" value="1"/>
					<input type="hidden" id="parentGroupId" name="parentGroupId" value="${group.parentGroupId}"/>
					<input type="hidden" id="sortOrder" name="sortOrder" value="${group.sortOrder}"/>
					<input type="hidden" id="groupTypeId" name="groupTypeId" value="${group.groupTypeId}"/>
					<input type="hidden" id="fullPath" name="fullPath" value="${group.fullPath}"/>
					<input type="hidden" id="childGroupCount" name="childGroupCount" value="${group.childGroupCount}"/>
					<input type="hidden" id="users" name="users" value=""/>
				</form>