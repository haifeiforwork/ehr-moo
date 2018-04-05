<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript"> 
//<![CDATA[
var tempCurrItem;
var tempPrevItem;
var tempParent;
var fromContext = false;

(function($) {
	
	$jq(document).ready(function() {	
		
		//left menu setting
		//$jq("#systemLinkOfLeft").click();
		
		$jq("#portalSystemForm:input:visible:enabled:first").focus();
		
		$jq("#buildingFloorTree").bind("loaded.jstree", function(event, data) {
			var $TopItem = $jq("ul > li:first", this);
			
			$jq("#buildingFloorTree").jstree("open_node", $TopItem.children("a")[0], false);
        });
		
		$jq("#buildingFloorTree").jstree({
			themes : {"icons" : false},
			plugins: ["themes", "ui", "crrm", "json_data", "contextmenu"],
			json_data : {
				ajax : {
					url : "<c:url value='getBuildingFloorTree.do'/>",
					data : function ($item) {
						if($item != -1) {
								return {
									"operation" : "get_children",  
									 "buildingFloorId" : $item.attr("code")
								}; 												
						}
					},
					success : function(data) {
						return iKEP.arrayToTreeData(data.items);
					},
					error : function(event, request, settings) { 
						//alert("error");	
					}
				}
		    	
		    },
   			contextmenu : {	
   				items : function($item) {
   					
   					var menuItem = {
   							
   						buildingCreate : {
   							label : "<ikep4j:message pre='${preDetail}' key='addBuilding' />",
   							action : function(currItem) {
   								
   								tempCurrItem = currItem;
   								
   								var buildingFloorId = $jq.parseJSON(currItem.attr("data")).code;
   						    	var parentBuildingFloorId = $jq.parseJSON(currItem.attr("data")).parent;
   						    	
   								if(buildingFloorId != "MR000000" && parentBuildingFloorId != "MR000000") {
   									alert("<ikep4j:message pre='${preMessage}' key='noAddBuilding' />");
   									
   									return;
   								} else if(currItem == null) {
   									alert("<ikep4j:message pre='${preMessage}' key='chooseNode' />");
   									
   									return;
   								} else {
   									
   									getForm(buildingFloorId);
   									//showCreateButton();
   								}
   								
   							}
   						},
   						floorCreate : {
   							label : "<ikep4j:message pre='${preDetail}' key='addFloor' />",
   							action : function(currItem) {
   								
   								tempCurrItem = currItem;
   								
   								var buildingFloorId = $jq.parseJSON(currItem.attr("data")).code;
   						    	var parentBuildingFloorId = $jq.parseJSON(currItem.attr("data")).parent;
   						    	
   								if(buildingFloorId != "MR000000" && parentBuildingFloorId != "MR000000") {
   									alert("<ikep4j:message pre='${preMessage}' key='noAddFloor' />");
   									
   									return;
   								} else if(currItem == null) {
   									alert("<ikep4j:message pre='${preMessage}' key='chooseBuilding' />");
   									
   									return;
   								} else {
   									
   									getForm(buildingFloorId);
   									//showCreateButton();
   								}
   								
   							}
   						},
   						buildingDelete : {
   							separator_before : false,
							icon : false,
							separator_after	: false,
   							label : "<ikep4j:message pre='${preDetail}' key='deleteBuilding' />",
   							action : function(currItem) {
   								
   								var buildingFloorId = $jq.parseJSON(currItem.attr("data")).code;
								   								
   								tempCurrItem = currItem;
   								
   								if(buildingFloorId == null) {
   									alert("<ikep4j:message pre='${preMessage}' key='deleteBuilding' />");
   									
   									return;
   								}
								
								if($jq(currItem).attr("class")=="jstree-closed") {
									alert("<ikep4j:message pre='${preMessage}' key='noDeleteBuilding' />");
									
									return;
								}
								
								if($jq(currItem).find("li[class!=jstree]").length > 0) {
									alert("<ikep4j:message pre='${preMessage}' key='noDeleteBuilding' />");
									
									return;
								}
																
								if($jq(currItem).attr("class").search("leaf") != -1) {
									if(confirm("<ikep4j:message pre='${preMessage}' key='delete' />")) {
										$jq.ajax({
											url : "<c:url value='buildingFloorDelete.do' />",
											data : {
												buildingFloorId : buildingFloorId
												},
											type : "post",
											success : function(result) {
												alert("<ikep4j:message pre='${preMessage}' key='deleteComplete' />");
												
												$jq("#buildingFloorTree").jstree("refresh", tempCurrItem.parent());
												
												tempCurrItem = null;
												
												getForm("");
											},
											error : function(event, request, settings) { 
												alert("<ikep4j:message pre='${preMessage}' key='deleteFail' />");
											}
										});
									} else {
										return;
									}
								} else {
									alert("<ikep4j:message pre='${preMessage}' key='noDeleteBuilding' />");
									
									return;
								}
   								
   							}
   						},
   						floorDelete : {
   							separator_before : false,
							icon : false,
							separator_after	: false,
   							label : "<ikep4j:message pre='${preDetail}' key='deleteFloor' />",
   							action : function(currItem) {
   								
   								var buildingFloorId = $jq.parseJSON(currItem.attr("data")).code;
								   								
   								tempCurrItem = currItem;
   								
   								if(buildingFloorId == null) {
   									alert("<ikep4j:message pre='${preMessage}' key='noDeleteFloor' />");
   									
   									return;
   								}
																
								if($jq(currItem).attr("class").search("leaf") != -1) {
									if(confirm("<ikep4j:message pre='${preMessage}' key='delete' />")) {
										$jq.ajax({
											url : "<c:url value='checkMeetingRoom.do' />",
											data : {
												buildingFloorId : buildingFloorId
												},
											type : "post",
											success : function(result) {
												
												if(!result) {
													$jq.ajax({
														url : "<c:url value='buildingFloorDelete.do' />",
														data : {
															buildingFloorId : buildingFloorId
															},
														type : "post",
														success : function(result) {
															alert("<ikep4j:message pre='${preMessage}' key='deleteComplete' />");
															
															$jq("#buildingFloorTree").jstree("refresh", tempCurrItem.parent());
															
															tempCurrItem = null;
															
															getForm("");
														},
														error : function(event, request, settings) { 
															alert("<ikep4j:message pre='${preMessage}' key='deleteFail' />");
														}
													});
												} else {
													
													alert("<ikep4j:message pre='${preMessage}' key='noDeleteMeetingRoomFloor' />");
													
													return;
												}
											},
											error : function(event, request, settings) { 
												alert("error");
											}
										});
									} else {
										return;
									}
								}
   								
   							}
   						},
	 					buildingFloorMoveUp : {
	    					label : "<ikep4j:message pre='${preDetail}' key='moveUp' />",
	    					action : function(currItem) {
	    						fromContext = true;
	    						
								function getPrevItem(tree, depth, prevItem) {
									
									var prevDepthInfo = tree.get_path(prevItem);
									
									switch(true) {
										case  depth == prevDepthInfo.length : return prevItem; break;	
										case  depth > prevDepthInfo.length : return false; break;
										case  depth < prevDepthInfo.length : return getPrevItem(tree, depth, tree._get_parent(prevItem)); break;
									}
									
								}
								
								var prevItem = getPrevItem(this, this.get_path(currItem).length, this._get_prev(currItem));
								
								if(prevItem == false || prevItem == null) {
									alert("<ikep4j:message pre='${preMessage}' key='noMove' />");
								} else {
									var prevNodeId = $jq.parseJSON(prevItem.attr("data")).code;
									var curNodeId = $jq.parseJSON(currItem.attr("data")).code;
									
									$jq.ajax({
										url : "<c:url value='moveUpBuildingFloor.do' />",
										data : {
											prevNodeId : prevNodeId,
											curNodeId : curNodeId
											},
										type : "post",
										success : function(result) {
											$jq("#buildingFloorTree").jstree("move_node", currItem, prevItem, "before");
										},
										error : function(event, request, settings) { 
											alert("error");	
										}
									});
								}
								
							}
						},
						buildingFloorMoveDown : {
	    					label : "<ikep4j:message pre='${preDetail}' key='moveDown' />",
	    					action : function(currItem) {
	    						fromContext = true;
	    						
								function getNextItem(tree, depth, nextItem) {
									
									var nextDepthInfo = tree.get_path(nextItem);
									
									switch(true) {
										case  depth == nextDepthInfo.length : return nextItem; break;
										case  depth > nextDepthInfo.length : return false; break;
										case  depth < nextDepthInfo.length : return getNextItem(tree, depth, tree._get_next(nextItem)); break;
									}
								}
								
								var nextItem = getNextItem(this, this.get_path(currItem).length, this._get_next(currItem));
								
								if(nextItem == false || nextItem == null) {
									alert("<ikep4j:message pre='${preMessage}' key='noMove' />");
								} else {
									var nextNodeId = $jq.parseJSON(nextItem.attr("data")).code;
									var curNodeId = $jq.parseJSON(currItem.attr("data")).code;
									
									$jq.ajax({
										url : "<c:url value='moveDownBuildingFloor.do' />",
										data : {
											nextNodeId : nextNodeId,
											curNodeId : curNodeId
											},
										type : "post",
										success : function(result) {
											$jq("#buildingFloorTree").jstree("move_node", currItem, nextItem, "after");
										},
										error : function(event, request, settings) { 
											alert("error");	
										}
									});
								}
								
							}
						},
	 					
	 					ccp : false
   					};
   					
   					var data = $jq.parseJSON($item.attr("data"));
   					
   					if(data.code == "MR000000") {
   						$jq.extend(menuItem, {floorCreate:false, buildingDelete:false, floorDelete:false, buildingFloorMoveUp:false, buildingFloorMoveDown:false});
   					} else if(data.code != "MR000000" && data.parent == "MR000000") {
   						$jq.extend(menuItem, {buildingCreate:false, floorDelete:false});
   					} else if(data.code != "MR000000" && data.parent != "MR000000") {
   						$jq.extend(menuItem, {buildingCreate:false, floorCreate:false, buildingDelete:false});
   					}
   					
   					return menuItem;
   				}
		    
   			}   
		}).bind("create.jstree", function(event, data) { 

		}).bind("move_node.jstree", function(event, data) {  

		}).delegate("a[href$='#']", "dblclick", function(e) {
			
			$jq("#buildingFloorTree").jstree("save_opened");
			
			e.preventDefault(); 
			this.blur(); 
			
			$jq("#buildingFloorTree").jstree("toggle_node", this, false, true);
        });
		
		/* 노드 클릭시 이벤트*/	
	    $jq("#buildingFloorTree").delegate("a", "click", function() {
	    		
	    	tempParent = $jq(this).parents("li:eq(1)");
	    	tempCurrItem = $jq(this).parent();
	    	tempPrevItem = $jq(this).parent().prev();
	    	
	    	var buildingFloorId = $jq.parseJSON(tempCurrItem.attr("data")).code;
	    	var parentBuildingFloorId = $jq.parseJSON(tempCurrItem.attr("data")).parent;
			
	    	if(parentBuildingFloorId != null && buildingFloorId != parentBuildingFloorId) {
	    		getBuildingFloor(buildingFloorId);
				//showUpdateButton();
	    	} else {
	    		getForm("");
	    		//getBuildingFloor(buildingFloorId);
	    		//showCreateButton();
	    	}
			
		});
			
	});
	
	getForm = function (parentBuildingFloorId) {

		$jq.ajax({
			url : "<c:url value='buildingFloorForm.do'/>",
			data : {
				parentBuildingFloorId : parentBuildingFloorId
			},
			type : "post",
			success : function(result) {
				$jq("#viewDiv").html(result);
			},
			error : function(event, request, settings) { 
				alert("error");	
			}
		});
		
	};

	//트리 선택시 시스템 수정 페이지를 가져오는 함수
	getBuildingFloor = function (buildingFloorId) {
		
		$jq.ajax({
			url : "<c:url value='buildingFloorForm.do'/>",
			data : {
				
				buildingFloorId : buildingFloorId
			},
			type : "post",
			success : function(result) {
				
				$jq("#viewDiv").html(result);
			},
			error : function(event, request, settings) { 
				
				alert("error");	
			}
		});
		
	};
	
})(jQuery);
//]]>
</script>
 
<!--pageTitle Start-->
<div id="pageTitle">
	<!-- h2><ikep4j:message pre="${preMain}" key="pageTitle" /></h2 -->
	<h2><ikep4j:message pre='${preHeader}' key='buildingFloor' /></h2>
</div>
<!--//pageTitle End-->	

<!--directive Start-->
<div class="directive"> 
	<ul>
		<li><span><ikep4j:message pre="${preDetail}" key="howtoAddBuildingFloor" /></span></li>	
		<li><span><ikep4j:message pre="${preDetail}" key="howtoModifyBuildingFloor" /></span></li>	
		<li><span><ikep4j:message pre="${preDetail}" key="howtoDeleteBuildingFloor" /></span></li>
		<li><span><ikep4j:message pre="${preDetail}" key="howtoMoveBuildingFloor" /></span></li>																	
	</ul>
</div>
<div class="blockBlank_10px"></div>
<!--//directive End-->	

<!--blockLeft Start-->
<div class="blockLeft" style="width:30%;">	
	<div class="leftTree treeBox">
	
		<div id="buildingFloorTree"></div>
		
	</div>
</div>
<!--//blockLeft End-->


<!--blockRight Start-->
<div class="blockRight" style="width:68%;">

	<div id="viewDiv">
		<form name="buildingFloorForm" id="buildingFloorForm" action="" method="post" >
		<input type="hidden" id="buildingFloorId" name="buildingFloorId" value="${buildingFloor.buildingFloorId}"/>
		<input type="hidden" id="parentBuildingFloorId" name="parentBuildingFloorId" value="${buildingFloor.parentBuildingFloorId}"/>
		<input type="hidden" id="sortOrder" name="sortOrder" value="${buildingFloor.sortOrder}" />
		<input type="hidden" id="childCount" name="childCount" value="${childCount}"/>
			
			<div class="blockDetail">
				<table id="mainTable" summary="<ikep4j:message pre='${preForm}' key='tableSummary' />">
					<caption></caption>
					<colgroup>
						<col width="25%"/>
						<col width="15%"/>
						<col width="60%"/>
					</colgroup>
					<tbody>
						
						<!--buildingName Start-->
						<tr>
							<th scope="row" rowspan="2">
								<span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='buildingFloorName' />
							</th>
							<th><span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='name' /></th>
							<td>
								<div>
									<input type="text" name="buildingFloorName" id="buildingFloorName" value="${buildingFloor.buildingFloorName}" size="50" class="inputbox"/>
								</div>
							</td>
						</tr>
						<tr>
							<th><span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='englishName' /></th>
							<td>
								<div>
									<input type="text" name="buildingFloorEnglishName" id="buildingFloorEnglishName" value="${buildingFloor.buildingFloorEnglishName}" size="50" class="inputbox" />
								</div>
							</td>
						</tr>
						<!--//buildingName End-->
						
						<!--//useFlag Start-->
						<tr>
							<th scope="row" colspan="2">
								<span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='useFlag' />
							</th>
							<td>
								<div>
									<input type="radio" id="useRadio" name="useFlag" class="radio" value="1" <c:if test="${empty buildingFloor.useFlag || buildingFloor.useFlag == '1'}">checked="checked"</c:if> />
									<label for="useRadio"><ikep4j:message pre='${preDetail}' key='useFlagY' /></label>&nbsp;
									<input type="radio" id="unUseRadio" name="useFlag" class="radio" value="0" <c:if test="${!empty buildingFloor.useFlag && buildingFloor.useFlag != '1'}">checked="checked"</c:if> />
									<label for="unUseRadio"><ikep4j:message pre='${preDetail}' key='useFlagN' /></label>
								</div>
							</td>
						</tr>
						<!--//useFlag End-->
						
					</tbody>
				</table>
			</div>
		</form>
	</div>
	
</div>
<!--//blockLight End-->