<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
<c:set var="userListPrefix" value="message.portal.admin.member.user.list"/>
<c:set var="userUiPrefix" value="ui.portal.admin.member.user"/>

				<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
				<script type="text/javascript">
				//<!--
				var $groupTree;
				var $searchWordId;

				(function($){
					
					setUser = function(data) {
			        
				        if(data.length > 0) {
				        	$jq("#userDiv").children().remove();
					        $jq(data).each(function(index) {
					        	if(index == 0) {
					        		getForm(this.id);
					        	}
					            //$jq("#userDiv").append("<li>"+data[index].userName + " " + data[index].jobTitleName + " " + data[index].mobile +"</li>")
					        });
					    } else {
				        	alert("<ikep4j:message pre="${userListPrefix}" key="alert.noRetrievedUser" />");
				        	
				        	newForm();
				        }
				    };
				    var $banchor;
					var createGroupTree = function createGroupTree() {
						if(!$groupTree) {
							$("#userTree").bind("loaded.jstree", function (event, data) {
								var $TopItem = $("ul > li:first", this);
								$("#userTree").jstree("open_node", $TopItem.children("a")[0], false);
					        });
							
							$groupTree = $("#userTree").jstree({	// 조직도 생성
							    plugins:["themes", "ui", "search", "json_data"],
							    ui : { select_multiple_modifier : false },
								json_data : {
									data : iKEP.arrayToTreeData(${deptItems}.items,null,true),
									ajax : {
										url : "<c:url value='/portal/admin/member/user/requestGroupChildren.do'/>",
										data : function ($el) {	//$el : current item 
											return { 
												 "operation" : "get_children",  
												 "groupId" : $el.attr("code")
											}; 
										},
										success : function(data) {
											return iKEP.arrayToTreeData(data.items,null,true);
										}
									}
								},
								search : {
									case_insensitive : true,
									ajax : {
										//url : iKEP.getContextRoot() + "/test/testSearch.do",
										url : iKEP.getContextRoot() + "/portal/main/getDeptPath.do",
										success : function(result, state, xhr) {
											var items = [];
											
											if(result.length > 0) {
												$.each(result, function() {
													if(this.toString() != null && this.toString() != '' && this.toString() !='undefined') {
														items.push("#treeItem_" + this.toString());
													}
												});
											}
											
											return items;
										},
										complete : function(xhr, state) {
											if(state == "parsererror") {
												alert("<ikep4j:message pre='${prefix}' key='alert.noRetrievedUser'/>");
											}
										}
									}
								},
								core : {animation : 100}
							}).bind("search.jstree", function(event, data) {
								var $searchItem = $("#treeItem_" + data.rslt.str);
								if($searchItem.length > 0) {
									var $anchor = $searchItem.children("a");
									$anchor.addClass("jstree-clicked")
								}
							}).delegate("a[href$='#']", "click", function (e) { // clicking node text expands and contracts it
								e.preventDefault();
								this.blur(); 
								$("#userTree").jstree("toggle_node", this, false, true); 
								//$("#producttree").jstree("toggle_node", this); 
					        });
						}
					};
					
					var newForm = function newForm() {
						$jq("#userForm")[0].reset();
						
					};
					
					$(document).ready(function() {

						//left menu setting
						$jq("#userLinkOfLeft").click();
						
						if($searchWordId) {
							$searchWordid = 'name';
						}
						
						var param = iKEP.getPopupArgument();
						if(param) {
							if(param.items) {
								appendItem(param.items);
							}
							if(param.search) {
								$("#treesch").val(param.search);
							}
						}
						
						createGroupTree();
						getForm();
						
						$jq('#searchWord').keypress( function(event) {
							var selectedVal = $jq("#searchColumn option:selected").val();
							
							if(selectedVal=='id'){
					            iKEP.searchUser(event, "N", setUser, "id");
							}else if(selectedVal=='name'){
								iKEP.searchUser(event, "N", setUser);
							}
				        });
				        
				        $jq('#userSearchBtn').click( function() { 
				        	
				        	$jq('#searchWord').trigger("keypress");
				        });
				        
					    $jq("#userTree").delegate("a", "click", function () {
					    	
							var userId = $.parseJSON( $jq(this).parent().attr("data")).id;
							var groupId = $.parseJSON( $jq(this).parent().attr("data")).code;
							var groupName = $jq(this).text();
	
							if(groupId != null && groupName != null) {
// 								$jq("#groupId").val(groupId.trim());
// 								$jq("#teamName").val(groupName.trim());					
							} else {
								getForm(userId);
							}
							
					     });

					    $jq("#searchColumn").change(function(){
					    	$searchWordId = $jq(this).val();
					    	//$jq("#searchWord").attr("id",$jq(this).val());
					    });
					   
					});
				})(jQuery);

					function getForm(userId) {
						
						$jq.ajax({
							url : '<c:url value="form.do" />',
							data : {
								userId : userId
							},
							type : "get",
							success : function(result) {
								$jq('#userTree').jstree('search', userId);
								$jq("#userFormDiv").html(result);
								
// 								if(userId ==undefined || userId =="") {
// 									$jq("#userPassword").attr('readonly', false);
// 								}
// 								else {
// 									$jq("#userPassword").attr('readonly', true);
// 								}
							}
						});
					}
					
					function checkId() {
						if($jq("#userId").val()=='') {
							alert("<ikep4j:message pre="${userListPrefix}" key="alert.noCheckValue" />");
						} else {
							$jq.ajax({
								url : '<c:url value="checkId.do" />',     
								data : {
									id: $jq("#userId").val()
									},     
								type : "post",
								success : function(result) {     
									
									if(result == 'duplicated') {
										alert("<ikep4j:message pre="${userListPrefix}" key="alert.isDuplicated" />");
									} else {
										alert("<ikep4j:message pre="${userListPrefix}" key="alert.isAvailable" />");
										$jq("#checkIdFlag").val("success");
									}
								} 
							});			
						}
					}
					
					function submitForm() {
						
						var checkIdFlag = $jq("#checkIdFlag").val();
						var userId = $jq("#userId").val();
						var teamName = $jq("#teamName").val();
						
						// 조직도에서 부서를 선택하므로 굳이 체크할 필요가 없을듯
						if(teamName) {
							if ((checkIdFlag == "success")||(checkIdFlag == "modify")) {
								$jq.ajax({
									url : '<c:url value="createUser.do" />',
									data : $jq("#userForm").serialize(),
									type : "post",
									success : function(result) {
										//$jq("#userTree").jstree("refresh").jstree("open_node");
										alert("<ikep4j:message pre="${userListPrefix}" key="alert.saveCompleted" />");
										getForm(result);
										//location.reload();
										//getForm();
									}
								});
							} else {
								alert("<ikep4j:message pre="${userListPrefix}" key="alert.checkDuplicated" />");
								return;
							}							
						} else {
							alert("<ikep4j:message pre="${userListPrefix}" key="alert.selectGroup" />");
						}
					}
					
					function deleteForm() {
						if ($jq("#userId").val() == "") {
							alert("<ikep4j:message pre="${userListPrefix}" key="alert.cantDelete" />");
							return;
						}
						
						if(confirm("<ikep4j:message pre="${userListPrefix}" key="confirm.wannaDelete" />")) {
							
							prepareForm();
							
							$jq.ajax({
								url : '<c:url value="deleteUser.do" />',
								data : $jq("#userForm").serialize(),
								type : "post",
								success : function(result) {
									//$jq("#userTree").jstree("refresh").jstree("open_node");
									alert("<ikep4j:message pre="${userListPrefix}" key="alert.deleteCompleted" />");
									location.reload();
									getForm();
								},
								error:function(){
									alert("<ikep4j:message pre="${userListPrefix}" key="alert.failed" />");
								}
							});
						} else {
							return;
						}
					}
					
					
					function initPassword(isAllUser) {
						
						var title;
						var userId = $jq("#userId").val();
						
						if(isAllUser =="Y") {
							title = " <ikep4j:message pre="${userUiPrefix}" key="button.initPasswordAll" />";
						}
						else {
							
							if (userId == "") {
								alert("<ikep4j:message pre="${userListPrefix}" key="alert.cantDelete" />");
								return;
							}
							title = $jq("#userName").val() +" <ikep4j:message pre="${userUiPrefix}" key="button.modifyPassword" />";
						}
					
						iKEP.showDialog({
							title: title,
							url: "<c:url value="/portal/admin/member/user/initPasswordForm.do" />?userId="+userId+"&isAllUser="+isAllUser,
							width:500,
							height:170,
							modal: true
						});
						
					}
					
					
				//-->
				</script>

				<form id="searchForm" name="searchForm" method="post" onsubmit="return false;" action="">

					<!--tableTop Start-->  
					<div class="tableTop">
						<div class="tableTop_bgR"></div>
						<h2><ikep4j:message pre="${userUiPrefix}" key="pageTitle" /></h2>
						<div class="tableSearch"> 
							<select name="searchColumn" id="searchColumn" title="<ikep4j:message pre="${userUiPrefix}" key="searchType" />">
								<option value="name" selected="selected"><ikep4j:message pre="${userUiPrefix}" key="userName" /></option>
								<option value="id"><ikep4j:message pre="${userUiPrefix}" key="userId" /></option>
							</select>
							<input type="text" name="searchWord" id="searchWord" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="searchBox" />" size="20" value="${status.value}"/>
							<a href="#a" id="userSearchBtn" name="userSearchBtn" onclick="return false;" class="ic_search">
								<span><ikep4j:message pre='${preSearch}' key='search' /></span>
							</a>
						</div>
						<div class="clear"></div>	
					</div>
					<!--//tableTop End-->
					
				</form>
					
				<!--blockLeft Start-->
				<div class="blockLeft" style="width:30%; border: 1px solid #D4D4D4;">
					<div class="leftTree" style="width:100%; padding:0; border:1px;min-height: 235px;">
						<div class="shuttleTree">
							<div id="userTree">
								<!--트리-->
							</div>
							<br />
						</div>
					</div>
					
					<!--blockButton Start-->
					<!--  비밀번호 전체 초기화 기능 주석처리 무림제지 2012.08.27
					<div class="blockButton" style="margin-right:7px">
						<ul>
							<li><a class="button_ic" id="btnInitPasswordAll" href="javascript:initPassword('Y')"><span id="actionBtn"><ikep4j:message pre="${userUiPrefix}" key="button.initPasswordAll" /></span></a></li>
						</ul>
					</div>
					-->
					<!--//blockButton End-->
					
				</div>
				<!--//blockLeft End-->
				
				<!--blockRight Start-->
				<div id="userFormDiv" class="blockRight" style="width:68%;">
					<!--폼-->
				</div>
				<!--//blockRight End-->	