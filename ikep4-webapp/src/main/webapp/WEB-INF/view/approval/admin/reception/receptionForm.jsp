<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="uiPrefix" value="ui.approval.admin.reception"/>
<c:set var="listPrefix" value="message.approval.admin.reception"/>
<%-- 메시지 관련 Prefix 선언 End --%>

				<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
				<script type="text/javascript">
				//<!--
				var $groupTreeVar;
				var tempCurrItem;
				var tempPrevItem;
				var tempParent;
				var fromContext = false;

				(function($){
					
					setGroup = function(data) {
						alert(data);
						alert(data.length);
				        if(data.length > 0) {
				        	$jq("#groupFormDiv").children().remove();
					        $jq(data).each(function(index) {
					        	if(index == 0) {
					        		getForm(this.code,this.parent);
					        	}
					            //$jq("#userDiv").append("<li>"+data[index].userName + " " + data[index].jobTitleName + " " + data[index].mobile +"</li>")
					        });
					    } else {
				        	alert("<ikep4j:message pre="${listPrefix}" key="alert.noRetrievedGroup" />");
				        	getForm();
				        }
				    };
					
					var createGroupTree = function createGroupTree(groupTypeId) { //트리 그리는 함수
						
						$("#groupTree").bind("loaded.jstree", function (event, data) {
							var $TopItem = $("ul > li:first", this);
							$("#groupTree").jstree("open_node", $TopItem.children("a")[0], false);
				        });

						$groupTreeVar = $("#groupTree").jstree({// 조직도 생성
							themes : {"icons" : false},
							plugins:  ["themes", "ui", "search", "json_data", "contextmenu", "crrm", "dnd"],
							json_data : {
								ajax : {
									url : "<c:url value='/portal/admin/group/group/requestGroupChildren.do'/>",
									data : function ($el) {	//$el : current item 
										if($el != -1) {
												return {
													"operation" : "get_children",  
													 "groupId" : $el.attr("code"),
													 "groupTypeId" : groupTypeId
												}; 												
										}else if(groupTypeId != '') {
											return {
												"operation" : "get_children",  
												 "groupTypeId" : groupTypeId
											};
										}
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
									url : iKEP.getContextRoot() + "/portal/main/getDeptPathByGroup.do",
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
											alert("<ikep4j:message pre='${prefix}' key='alert.noRetrievedGroup'/>");
										}
									}
								}
							},
							core : {animation : 100},
							contextmenu : {
								items : {
									ccp : false /* 에디터 바꾸기 Context Menu 사용 안함 */
									,create : false /* 생성 Context Menu 사용 안함 */
									,rename : false /* 이름 바꾸기 Context Menu 사용 안함 */
									,remove : false /* Context Menu에서 Delete 사용 안함 */
								}
							}
							
						}).delegate("a[href$='#']", "dblclick", function (e) { // clicking node text expands and contracts it
							$jq("#groupTree").jstree("save_opened");
						
							e.preventDefault(); 
							this.blur(); 
							$("#groupTree").jstree("toggle_node", this, false, true);
						
				        }).bind("search.jstree", function(event, data) {
							var $searchItem = $("#treeItem_" + data.rslt.str);
							if($searchItem.length > 0) {
								var $anchor = $searchItem.children("a");
								$anchor.addClass("jstree-clicked")
							}
						}).bind("move_node.jstree", function(event, data){ //Drag and Drop으로 이동되는 노드를 처리하는 부분 
							var currNode = data.rslt.o; 
						    var parentNode = data.inst._get_parent(currNode);
						    var prevNode = data.inst._get_prev(currNode);
						    
						    var currNodeGroupId = $jq.parseJSON($jq(currNode).attr("data")).code;
						    var prevNodeGroupId = $jq.parseJSON($jq(prevNode).attr("data")).code;
						    var parentGroupId = $jq.parseJSON($jq(parentNode).attr("data")).code;
// 							var boardId       = $(node).attr("boardId");
// 						    var boardParentId = $(parentNode).attr("boardId");

						    if(!fromContext) {
								if(confirm("<ikep4j:message pre="${listPrefix}" key="confirm.wannaMove" />")) {
									$.post("<c:url value='updateSortOrder.do'/>",
											{"currNodeGroupId" : currNodeGroupId
										, "prevNodeGroupId" : prevNodeGroupId
										, "parentGroupId" : parentGroupId}
									).success(function(data) {
										
										alert("<ikep4j:message pre="${listPrefix}" key="alert.moveCompleted" />");
										
									}).error(function(event, request, settings) {
										
										alert("<ikep4j:message pre="${listPrefix}" key="alert.cantMove" />");
										
										$.jstree.rollback(data.rlbk); 	
									});  
								} else {
									$.jstree.rollback(data.rlbk); 
								}						    	
						    } else {
						    	fromContext = false;
						    }
				        	
				        });
					};

					$jq(document).ready(function() { // 페이지 로딩시 수행하는 부분 시작
						
						//left menu setting
						$jq("#receptionFormLeft").click();
						
						var param = iKEP.getPopupArgument();
						if(param) {
							if(param.items) {
								appendItem(param.items);
							}
							if(param.search) {
								$jq("#treesch").val(param.search);
							}
						}
						
						createGroupTree('');
						getForm('${group.groupId}','');
						
					    $jq("#groupTree").delegate("a", "click", function () { //트리의 노드를 클릭하는 경우 해당 노드의 정보를 우측 폼으로 전송함
							
					    	tempParent = $jq(this).parents("li:eq(1)");
					    	tempCurrItem = $jq(this).parent();
					    	tempPrevItem = $jq(this).parent().prev();
					    	
							var groupId = $.parseJSON( $jq(this).parent().attr("data")).code;
							var parentGroupId = $.parseJSON( $jq(this).parent().attr("data")).parent;
							var groupTypeId = $.parseJSON( $jq(this).parent().attr("data")).groupTypeId;
							var groupName = $jq(this).text();
							
							$jq("#groupTypeId").val(groupTypeId);
							
							getForm(groupId, parentGroupId);
					     });
				    
					    $jq("#searchType").change(function(){ //searchType 셀렉트박스의 값이 변경되면 검색 조건을 감지
					    	$groupTree = null;
					    	var id = $jq("#searchType option:selected").val();
							id = $jq("#groupTypeId").val(id);
							groupTypeId = $jq("#groupTypeId").val();

							createGroupTree(groupTypeId);
							
					    });
					    
					    $jq('#searchWord').keypress( function(event) {
							var selectedVal = $jq("#searchColumn option:selected").val();
							iKEP.searchGroup(event, "N", setGroup);

				        });
				        
				        $jq('#groupSearchBtn').click( function() { 
				        	$jq('#searchWord').trigger("keypress");
				        });
					});

				})(jQuery);
				
				function getForm(groupId, parentGroupId) {
					$jq.ajax({
						url : '<c:url value="form.do" />',
						data : {
							groupId : groupId,
							parentGroupId : parentGroupId
						},
						type : "get",
						success : function(result) {
							$jq('#groupTree').jstree('search', groupId);
							$jq("#groupFormDiv").html(result);
						}
					});
				}
				
				function getAddForm(groupId, parentGroupId, groupTypeId) {
					$jq.ajax({
						url : '<c:url value="form.do" />',
						data : {
							groupId : groupId,
							parentGroupId : parentGroupId,
							groupTypeId : groupTypeId
						},
						type : "get",
						success : function(result) {
							$jq("#groupFormDiv").html(result);
						}
					});
				}
				
				function prepareUserList() {
					var users = "";
					var attrValue = "";
					
					var sel = $jq("#groupForm").find("[name=groupUserList]");
					$jq("input[name=userList]").remove();
					$jq("#groupUserList option").each(function (index) {
						attrValue = $jq(this).val();
						users = users + "<input name=\"userList["+ index +"].userId\" value=\""+ attrValue + "\" type=\"hidden\"/>";
					});
				 
					$jq("#groupForm").append(users);
				}

				function checkId() {
					if($jq("#groupId").val()=='') {
						alert("<ikep4j:message pre="${listPrefix}" key="alert.noCheckValue" />");
					} else {
						$jq.ajax({
							url : '<c:url value="checkId.do" />',     
							data : {
								id: $jq("#groupId").val()
								},     
							type : "post",
							success : function(result) {     
								
								if(result == 'duplicated') {
									alert("<ikep4j:message pre="${listPrefix}" key="alert.isDuplicated" />");
								} else {
									alert("<ikep4j:message pre="${listPrefix}" key="alert.isAvailable" />");
									$jq("#checkCodeFlag").val("success");
								}
							} 
						});			
					}
				}
				
				function deleteForm() {

					var tree = this;
					var groupId = null;
					
					if($jq("#childGroupCount").val() != 0) {
						alert("<ikep4j:message pre="${listPrefix}" key="alert.deleteChild" />");
						return;
					}
					
					//groupId = $jq.parseJSON(currItem.attr("data")).code;
					groupId = $jq("#groupId").val();
					if(groupId == null) {
						groupId = $jq("#groupId").val();
					}
					
					if(groupId == null) {
						alert("<ikep4j:message pre="${listPrefix}" key="alert.selectGroup" />");
						return;
					}
					
					if(confirm("<ikep4j:message pre="${listPrefix}" key="confirm.wannaDeleteGroup" />")) {
						$jq.ajax({
							url : '<c:url value="deleteGroup.do" />',
							data : {groupId : groupId},
							type : "post",
							success : function(result) {
								alert("<ikep4j:message pre="${listPrefix}" key="alert.deleteCompleted" />");
								
								if(tempCurrItem != null) {
									//선택되었던(삭제된) 노드의 부모 레벨까지 오픈
									$jq("#groupTree").jstree("refresh",tempCurrItem.parent());
									$jq("#groupTree").jstree("open_node",tempCurrItem.parent().children(),false,false);
								} else {
									$jq("#groupTree").jstree("refresh");
								}
								
								getForm();
							},
							error:function(){
								alert("<ikep4j:message pre="${listPrefix}" key="alert.failedToDelete" />");
							}
						});
					} else {
						return;
					}
				}
				//-->
				</script>
				<div id="pageTitle">
					<h2>접수담당자 관리</h2>
				</div>
				<form id="searchForm" name="searchForm" method="post" onsubmit="return false;" action="">
					<div class="blockBlank_10px"></div>
				</form>

					
				<!--blockLeft Start-->
				<div class="blockLeft" style="width:90%; border: 1px solid #D4D4D4;display:none;">
					<div class="leftTree" style="width:100%; padding:0; border:1px;min-height: 235px;display:none;">
						<div class="shuttleTree">
							<div id="groupTree">
								<!--트리-->
							</div>
							<br />
						</div>
					</div>
				</div>
				<!--//blockLeft End-->
				<!--blockRight Start-->
					<div id="groupFormDiv" class="blockRight" style="width:100%;">
						<!--폼-->
					</div>
					<!--//blockRight End-->	
				
				
				