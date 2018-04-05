<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="uiPrefix" value="ui.approval.admin.reception"/>
<c:set var="listPrefix" value="message.approval.admin.reception"/>
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

				<script type="text/javascript">
				//<!--
				(function($) {
					
					selectReception = function(val) {
						location.href='<c:url value="/approval/admin/reception/receptionForm.do" />?groupId='+val;
					};
					
				    $jq(document).ready(function() {
				    	
				    	var $groupUserList = $("#groupUserList").children().remove().end();
				    	var $selectedGroupUserList = $("#selectedGroupUserList").children().remove().end();
				    	<c:forEach var="item" items="${userList}">
							var item = {
								type : "user",
								empNo : "",
								id : "${item.userId}",
								userName : "${user.localeCode == portal.defaultLocaleCode ? item.userName : item.userEnglishName}",
								jobTitleName : "${user.localeCode == portal.defaultLocaleCode ? item.jobTitleName : item.jobTitleEnglishName}",
								groupId : "${item.groupId}",
								teamName : "",
								email : "${item.mail}",
								mobile : ""
							};

							$.tmpl(iKEP.template.userOption, item).appendTo($groupUserList)
								.data("data", item);
						</c:forEach>
						
						<c:forEach var="item" items="${receptionList}">
							var item = {
								type : "user",
								empNo : "",
								id : "${item.userId}",
								userName : "${user.localeCode == portal.defaultLocaleCode ? item.userName : item.userEnglishName}",
								jobTitleName : "${user.localeCode == portal.defaultLocaleCode ? item.jobTitleName : item.jobTitleEnglishName}",
								groupId : "${item.groupId}",
								teamName : "",
								email : "",
								mobile : ""
							};
	
							$.tmpl(iKEP.template.userOption, item).appendTo($selectedGroupUserList)
								.data("data", item);
						</c:forEach>

						//주소록 버튼에 이벤트 바인딩
						$jq('#btnAddrControl').click( function() {
							var $selectedGroupUserList = $("#selectedGroupUserList");
							
							$jq("#groupUserList option:selected").each(function() {
								$jq.ajax({
									url : '<c:url value="getGroupUserInfo.do" />',
									data : {userId:$(this).attr("value")},
									success : function(result) {
										var item = {
											type : "user",
											empNo : "",
											id : result.userId,
											userName : result.userName, //"${user.localeCode == portal.defaultLocaleCode ? "+result.userName+" : "+result.userEnglishName+"}",
											jobTitleName : result.jobTitleName, //"${user.localeCode == portal.defaultLocaleCode ? "+result.jobTitleName+" : "+result.jobTitleEnglishName+"}",
											groupId : "${group.groupId}",
											teamName : "",
											email : "",
											mobile : ""
										};
										
										var duplicationUsers = [];
										var hasOption = $selectedGroupUserList.find('option[value="'+result.userId+'"]');

										if(hasOption.length > 0){
											duplicationUsers.push(result.userName);
										} else {
											$.tmpl(iKEP.template.userOption, item).appendTo($selectedGroupUserList)
											.data("data", item);
										}
									},
										error : function(xhr, exMessage) {
										var errorItems = $.parseJSON(xhr.responseText).exception;
										validator.showErrors(errorItems);
									}
								});
							});
							
						});
						
						$jq("#btnAddrDelete").click(function(){
							// 셀렉트박스에서 선택된 값들을 제거
							$jq("#selectedGroupUserList option:selected").remove();
						});
						
						$jq("#btnAddrReset").click(function(){
							
							var $selectedGroupUserList = $("#selectedGroupUserList").children().remove().end();
							<c:forEach var="item" items="${receptionList}">
								var item = {
									type : "user",
									empNo : "",
									id : "${item.userId}",
									userName : "${user.localeCode == portal.defaultLocaleCode ? item.userName : item.userEnglishName}",
									jobTitleName : "${user.localeCode == portal.defaultLocaleCode ? item.jobTitleName : item.jobTitleEnglishName}",
									groupId : "${item.groupId}",
									teamName : "",
									email : "",
									mobile : ""
								};
		
								$.tmpl(iKEP.template.userOption, item).appendTo($selectedGroupUserList)
									.data("data", item);
							</c:forEach>
						});
						
						
						$jq("#submitButton").click(function() {
							$jq("#groupForm").trigger("submit");
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
						
						//$jq("#groupForm :input:visible:enabled:first").focus().select();
						
						var validOptions = {
								rules : {
									
							    },
							    messages : {
							    	
							    },
							    submitHandler : function(groupForm) {
									
							    	var groupId = $jq("#groupId").val();
							    	var $sel = $jq("select[name=groupUserList]");
							    	if($sel) {
										$jq.each($sel.children(), function() {
											this.selected = false;
										});
									}
							    	var $selGroup = $jq("select[name=selectedGroupUserList]");
							    	if($selGroup) {
										$jq.each($selGroup.children(), function() {
											this.selected = true;
										});
									}
							    	
							    	$jq("#groupTree").jstree("save_opened");
							    	$jq.ajax({
										url : '<c:url value="/approval/admin/reception/updateReception.do" />',
										data : $jq("#groupForm").serialize(),
										type : "post",
										async : false,
										success : function(result) {
											
											alert("<ikep4j:message pre="${listPrefix}" key="alert.saveCompleted" />");
											if($selGroup) {
												$jq.each($selGroup.children(), function() {
													this.selected = false;
												});
											}
											var groupId = $jq("#groupId").val();
											var parentGroupId = $jq("#parentGroupId").val();
											var groupTypeId = $jq("#groupTypeId").val();
											
											// tempParent는 수정된 노드의 부모노드이며 refresh 후 그 자식들 레벨까지 노드를 오픈
											$jq("#groupTree").jstree("refresh",tempParent);
											$jq("#groupTree").jstree("open_node",tempParent.children(),false,false);
											//getForm(result, '');
											getForm(groupId, parentGroupId);
											
										},
										error : function(event, request, settings){
											 alert("error");
										}
									});
								}

						 };

						var validator = new iKEP.Validator("#groupForm", validOptions);
					});
				// java script 전역변수 항목에 추가
				$.template("addrBookItemUser", '<option value="\${id}">\${userName}(${id})/\${jobTitleName}/\${teamName}</option>');

				// 주의 위에 code 가 가이드 입력시 오류로 입력되지 않아서 부득이하게 공백으로 입력
				// 추후 사용시 공백 제거후 사용 해주세요

				})(jQuery);
				//-->
				</script>

				<form id="groupForm" name="groupForm" method="post" action="">

				
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
									<c:when test="${fn:length(groupList) > 1}">
										<tr>
											<th scope="col"><ikep4j:message pre="${uiPrefix}" key="groupName" /></th>
											<td class="testLeft">
												<div>
													<spring:bind path="apprReception.groupId">
													<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'
													 onchange="selectReception(this.value);">
														<c:forEach var="item" items="${groupList}">
															<option value="${item.groupId}" <c:if test="${item.groupId eq group.groupId}">selected="selected"</c:if>><c:if test="${user.localeCode eq 'ko'}">${item.groupName}</c:if><c:if test="${user.localeCode ne 'ko'}">${item.groupEnglishName}</c:if></option>
														</c:forEach> 
													</select>
													</spring:bind>
												</div>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<th scope="col"><ikep4j:message pre="${uiPrefix}" key="groupName" /></th>
											<td class="testLeft">
												<div>
													${group.groupName}
												</div>
											</td>
										</tr>
										<spring:bind path="apprReception.groupId">
										<input type="hidden" id="groupId" name="groupId" value="${group.groupId}"/>
										</spring:bind>
									</c:otherwise>
								</c:choose>
								
<!-- 								<tr> -->
<%-- 									<th scope="col"><ikep4j:message pre="${uiPrefix}" key="groupEnglishName" /></th> --%>
<!-- 									<td class="testLeft"> -->
<!-- 										<div> -->
<%-- 											${group.groupEnglishName} --%>
<!-- 										</div> -->
<!-- 									</td> -->
<!-- 								</tr>								 -->
								<tr>
									<th scope="col"><ikep4j:message pre="${uiPrefix}" key="userList" /></th>
									<td class="testLeft">
									<div class="blockLeft" style="width:42%;">
										<select id="groupUserList" name="groupUserList" multiple="multiple" size="5" style="width:100%;height:230px">
										</select>
									</div>
									<div class="blockLeft ml15" style="width:10%;">
										<ul>
											<li><a class="button_ic mb5" id="btnAddrControl" href="#a"><span id="actionBtn"><ikep4j:message pre="${uiPrefix}" key="button.addUser" /></span></a></li>	
											<li><a class="button_ic mb5" id="btnAddrDelete"  href="#a"><span><ikep4j:message pre="${uiPrefix}" key="button.deleteUser" /></span></a></li>
											<li><a class="button_ic mb5" id="btnAddrReset"  href="#a"><span><ikep4j:message pre="${uiPrefix}" key="button.resetUser" /></span></a></li>
										</ul>
									</div>
									<div class="blockRight" style="width:42%;">
										<spring:bind path="apprReception.selectedGroupUserList">
										<select id="selectedGroupUserList" name="selectedGroupUserList" multiple="multiple" size="5" style="width:100%;height:230px">
										</select>
										</spring:bind>
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
							<li><a class="button" id="submitButton" href="#a"><span><ikep4j:message pre="${uiPrefix}" key="button.save" /></span></a></li>
						</ul>
					</div>
					<!--//blockButton End-->
				</form>