<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.addressbook.header" /> 
<c:set var="preControl"    value="ui.support.addressbook.popup.controlpopup" />
<c:set var="preButton"  value="ui.support.addressbook.popup.button" />
<c:set var="preContext"  value="ui.support.addressbook.popup.context" />
<c:set var="preMessage"  value="message.support.addressbook.popup" />
<c:set var="uiPrefix" value="ui.portal.admin.group.group"/>
<c:set var="listPrefix" value="message.portal.admin.group.group.list"/>
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
					
					var createGroupTree = function createGroupTree(groupTypeId) { //트리 그리는 함수
						
						$("#groupTree").bind("loaded.jstree", function (event, data) {
							var $TopItem = $("ul > li:first", this);
							$("#groupTree").jstree("open_node", $TopItem.children("a")[0], false);
				        });

						$groupTreeVar = $("#groupTree").jstree({// 조직도 생성
							themes : {"icons" : false},
							plugins:  ["themes", "ui", "json_data", "contextmenu", "crrm", "dnd"],
							json_data : {
								ajax : {
									url : "<c:url value='/portal/admin/group/group/workflow/requestGroupChildren.do'/>",
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
										return iKEP.arrayToTreeData(data.items);
									}
								}
							},contextmenu : {
								items : {
									"createGroup" : {
										"label"				: "<ikep4j:message pre="${listPrefix}" key="context.addGroup" />",
										"action"			: function (currItem) {
											tempCurrItem = currItem;
											var parentGroupId = $.parseJSON(currItem.attr("data")).code;
											var groupTypeId = $.parseJSON(currItem.attr("data")).groupTypeId;
											
											getAddForm('', parentGroupId, groupTypeId);
										}
									},
// 									"removeGroup" : {
// 										"separator_before"	: false,
// 										"icon"				: false,
// 										"separator_after"	: false,
// 										"label"				: "<ikep4j:message pre="${listPrefix}" key="context.deleteGroup" />",
// 										"action"			: function (currItem) {
											
// 											tempCurrItem = currItem;
											
// 											if($jq(currItem).attr("class").search("leaf") != -1) {
// 												li의 class가 leaf인 경우 자식이 없는 단독 노드이므로 삭제 가능
// 												deleteForm(currItem);
// 											} else {
// 												기타의 경우(closed, open, last-open, last-closed)는 자식 노드가 존재하므로 삭제 불가
// 												alert("<ikep4j:message pre="${listPrefix}" key="alert.deleteChild" />");
// 												return;
// 											}
// 										}
// 									},
									"moveUp" : {
										"label"				: "<ikep4j:message pre="${listPrefix}" key="context.goUp" />",
										"action" : function(currItem) {
											fromContext = true;
											//상위 그룹이 자식을 가지고 있는지 체크하고 가지고 있으면 root를 제외한 최상위 그룹을 찾는다.
											function getPrevItem(tree, depth, prevItem) {
												var prevDepthInfo = tree.get_path(prevItem);
												switch(true) {
													case  depth == prevDepthInfo.length : return prevItem; break;	// move
													case  depth > prevDepthInfo.length : return false; break;	// non-move
													case  depth < prevDepthInfo.length : return getPrevItem(tree, depth, tree._get_parent(prevItem)); break;	// parent
												}
											}
											
											var prevItem = getPrevItem(this, this.get_path(currItem).length, this._get_prev(currItem));
											
											if(prevItem == false) {
												alert("<ikep4j:message pre="${listPrefix}" key="alert.cantMove" />");
											} else {
												var prevNodeGroupId = $.parseJSON(prevItem.attr("data")).code;
												var curNodeGroupId = $.parseJSON(currItem.attr("data")).code;
												
												$jq.ajax({
													url : '<c:url value="goUp.do" />',
													data : {
														prevNodeGroupId : prevNodeGroupId,
														curNodeGroupId : curNodeGroupId
														},
													type : "post",
													success : function(result) {
														$jq("#groupTree").jstree("move_node", currItem, prevItem, "before");
													}
												});
											}
										}
									},
									"moveDown" : {
										"label"				: "<ikep4j:message pre="${listPrefix}" key="context.goDown" />",
										"action" : function(currItem) {
											fromContext = true;
											function getNextItem(tree, depth, nextItem) {
												var nextDepthInfo = tree.get_path(nextItem);
												switch(true) {
													case  depth == nextDepthInfo.length : return nextItem; break;	// move
													case  depth > nextDepthInfo.length : return false; break;	// non-move
													case  depth < nextDepthInfo.length : return getNextItem(tree, depth, tree._get_next(nextItem)); break;	// parent
												}
											}
											
											var nextItem = getNextItem(this, this.get_path(currItem).length, this._get_next(currItem));
											
											if(nextItem == false) {
												alert("<ikep4j:message pre="${listPrefix}" key="alert.cantMove" />");
											} else {
												var nextNodeGroupId = $.parseJSON(nextItem.attr("data")).code;
												var curNodeGroupId = $.parseJSON(currItem.attr("data")).code;
												
												$jq.ajax({
													url : '<c:url value="goDown.do" />',
													data : {
														nextNodeGroupId : nextNodeGroupId,
														curNodeGroupId : curNodeGroupId
														},
													type : "post",
													success : function(result) {
														$jq("#groupTree").jstree("move_node", currItem, nextItem, "after");
													}
												});
											}
										}
									},
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
						getForm();
						
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
								//선택되었던(삭제된) 노드의 부모 레벨까지 오픈
								$jq("#groupTree").jstree("refresh",tempCurrItem.parent());
								$jq("#groupTree").jstree("open_node",tempCurrItem.parent().children(),false,false);
								
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

				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre="${uiPrefix}" key="pageTitle" /></h2>
				</div>
				<!--//pageTitle End-->

				<!--directive Start-->
				<div class="directive"> 
					<ul>
						<li><span><ikep4j:message pre="${listPrefix}" key="howtoAddGroup" /></span></li>	
						<li><span><ikep4j:message pre="${listPrefix}" key="howtoModifyGroup" /></span></li>	
						<li><span><ikep4j:message pre="${listPrefix}" key="howtoDeleteGroup" /></span></li>
						<li><span><ikep4j:message pre="${listPrefix}" key="howtoMoveGroup" /></span></li>																		
					</ul>
				</div>
				<div class="blockBlank_10px"></div>
				<!--//directive End-->
				
				<form id="searchForm" name="searchForm" method="post" onsubmit="return false;" action="">

					<!--tableTop Start-->  
					<div class="tableTop">  
						<div class="tableSearch"> 
						<label for="searchType"><ikep4j:message pre="${uiPrefix}" key="searchTypeLabel" /></label>
							<select id="searchType" name="searchType" title="searchType" >
								<c:forEach var="groupTypeList" items="${groupTypeList}" varStatus="loopStatus">
									<option id="${groupTypeList.groupTypeId}" value="${groupTypeList.groupTypeId}" <c:if test="${groupTypeList.groupTypeId=='G0001'}">selected="selected"</c:if>>
										<c:if test="${userLocaleCode eq 'ko'}">${groupTypeList.groupTypeName}</c:if>
										<c:if test="${userLocaleCode ne 'ko'}">${groupTypeList.groupTypeEnglishName}</c:if>
									</option>
								</c:forEach>
							</select> 
						</div>
						<div class="clear"></div>	
					</div>
					<!--//tableTop End-->
					
				</form>
					
				<!--blockLeft Start-->
				<div class="blockLeft" style="width:30%; border: 1px solid #D4D4D4;">
					<div class="leftTree" style="width:100%; padding:0; border:1px;min-height: 235px;">
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
				<div id="groupFormDiv" class="blockRight" style="width:68%;">
					<!--폼-->
				</div>
				<!--//blockRight End-->	