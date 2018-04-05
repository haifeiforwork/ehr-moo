<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preApCommList"    value="ui.approval.work.apprlist" />
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
						getForm('${rootGroup.rootGroupId}','${rootGroup.rootGroupId}');
						
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
				    
					    
					});

				})(jQuery);
				
				function getForm(groupId, parentGroupId) {
					
					$jq.ajax({
						url : '<c:url value="/approval/admin/apprStat/timeStat.do" />',
						data : {
							groupId : groupId,
							searchGroupId : groupId
						},
						loadingElement : {container:"body"},
						type : "get",
						success : function(result) {
							$jq("#groupFormDiv").html(result);
						}
					});
				}
				
				//-->
				</script>
				
				<div id="pageTitle">
					<h2><ikep4j:message pre='${preApCommList}.pageTitle' key='timeStat' /></h2>
				</div>
					
				<!--blockLeft Start-->
				<div class="blockLeft" style="width:20%; border: 1px solid #D4D4D4;">
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
				<div id="groupFormDiv" class="blockRight" style="width:78%;">
					<!--폼-->
				</div>
				<!--//blockRight End-->	