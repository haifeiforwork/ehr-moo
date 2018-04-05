<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preAlert" value="message.portal.admin.screen.system.portalSystemMain.alert"/>					
<c:set var="preLabel" value="ui.portal.admin.screen.system.portalSystemMain.label"/>
<c:set var="preForm" value="ui.portal.admin.screen.system.portalSystemMain.form"/>
<c:set var="preButton" value="ui.portal.admin.screen.system.portalSystemMain.button"/>					
<c:set var="preMain" value="ui.portal.admin.screen.system.portalSystemMain.main"/>

<script type="text/javascript"> 
//<![CDATA[
var tempCurrItem;
var tempPrevItem;
var tempParent;
var fromContext = false;

(function($) {
	
	$jq(document).ready(function() {	
		
		//left menu setting
		$jq("#systemLinkOfLeft").click();
		
		$jq("#portalSystemForm:input:visible:enabled:first").focus();
		
		$jq("#portalSystemTree").bind("loaded.jstree", function(event, data) {
			var $TopItem = $jq("ul > li:first", this);
			
			$jq("#portalSystemTree").jstree("open_node", $TopItem.children("a")[0], false);
        });
		
		
		$jq("#portalSystemTree").jstree({
			themes : {"icons" : false},
			plugins: ["themes", "ui", "crrm", "dnd", "json_data", "contextmenu"],
			json_data : {
				ajax : {
					url : "<c:url value='getPortalSystemTree.do'/>",
					data : function ($item) {
						if($item != -1) {
								return {
									"operation" : "get_children",  
									 "systemCode" : $item.attr("code")
								}; 												
						}
					},
					success : function(data) {
						return iKEP.arrayToTreeData(data.items);
					},
					error : function(event, request, settings) { 
						alert("error");	
					}
				}
		    	
		    },
   			contextmenu : {	
   				items : function($item) {
   					
   					var menuItem = {
   							
   						systemCreate : {
   							label : "<ikep4j:message pre='${preLabel}' key='systemCreate' />",
   							action : function(currItem) {
   								
   								tempCurrItem = currItem;
   								
   								var systemCode = $jq.parseJSON(currItem.attr("data")).code;
   						    	var parentSystemCode = $jq.parseJSON(currItem.attr("data")).parent;
   						    	
   								if(systemCode != "S000000" && parentSystemCode != "S000000") {
   									alert("<ikep4j:message pre='${preAlert}' key='noCreateSystem' />");
   									
   									return;
   								} else if(currItem == null) {
   									alert("<ikep4j:message pre='${preAlert}' key='chooseNode' />");
   									
   									return;
   								} else {
   									getForm();
   									//showCreateButton();
   								}
   								
   							}
   						},
   						systemDelete : {
   							separator_before : false,
							icon : false,
							separator_after	: false,
   							label : "<ikep4j:message pre='${preLabel}' key='systemDelete' />",
   							action : function(currItem) {
   								
   								var systemCode = $jq.parseJSON(currItem.attr("data")).code;
								   								
   								tempCurrItem = currItem;
   								
   								if(systemCode == null) {
   									alert("<ikep4j:message pre='${preAlert}' key='noDeleteSystem' />");
   									
   									return;
   								}
								
								if($jq(currItem).attr("class")=="jstree-closed") {
									alert("<ikep4j:message pre='${preAlert}' key='noDeleteSystemChild' />");
									
									return;
								}
								
								if($jq(currItem).find("li[class!=jstree]").length > 0) {
									alert("<ikep4j:message pre='${preAlert}' key='noDeleteSystemChild' />");
									
									return;
								}
																
								if($jq(currItem).attr("class").search("leaf") != -1) {
									if(confirm("<ikep4j:message pre='${preAlert}' key='confirmDelete' />")) {
										$jq.ajax({
											url : "<c:url value='portalSystemDelete.do' />",
											data : {
												systemCode : systemCode
												},
											type : "post",
											success : function(result) {
												alert("<ikep4j:message pre='${preAlert}' key='deleteSuccess' />");
												
												$jq("#portalSystemTree").jstree("refresh", tempCurrItem.parent());
												
												tempCurrItem = null;
												
												getForm();
											},
											error : function(event, request, settings) { 
												alert("<ikep4j:message pre='${preAlert}' key='deleteFail' />");
											}
										});
									} else {
										return;
									}
								} else {
									alert("<ikep4j:message pre='${preAlert}' key='noDeleteSystemChild' />");
									
									return;
								}
   								
   							}
   						},
	 					systemMoveUp : {
	    					label : "<ikep4j:message pre='${preLabel}' key='systemMoveUp' />",
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
									alert("<ikep4j:message pre='${preAlert}' key='noSystemMove' />");
								} else {
									var prevNodeId = $jq.parseJSON(prevItem.attr("data")).code;
									var curNodeId = $jq.parseJSON(currItem.attr("data")).code;
									
									$jq.ajax({
										url : "<c:url value='moveUpPortalSystem.do' />",
										data : {
											prevNodeId : prevNodeId,
											curNodeId : curNodeId
											},
										type : "post",
										success : function(result) {
											$jq("#portalSystemTree").jstree("move_node", currItem, prevItem, "before");
										},
										error : function(event, request, settings) { 
											alert("error");	
										}
									});
								}
								
							}
						},
						systemMoveDown : {
	    					label : "<ikep4j:message pre='${preLabel}' key='systemMoveDown' />",
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
									alert("<ikep4j:message pre='${preAlert}' key='noSystemMove' />");
								} else {
									var nextNodeId = $jq.parseJSON(nextItem.attr("data")).code;
									var curNodeId = $jq.parseJSON(currItem.attr("data")).code;
									
									$jq.ajax({
										url : "<c:url value='moveDownPortalSystem.do' />",
										data : {
											nextNodeId : nextNodeId,
											curNodeId : curNodeId
											},
										type : "post",
										success : function(result) {
											$jq("#portalSystemTree").jstree("move_node", currItem, nextItem, "after");
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
   					
   					if(data.code == "S000000") {
   						$jq.extend(menuItem, {systemDelete:false, systemMoveUp:false, systemMoveDown:false});
   					} else if(data.code != "S000000" && data.parent != "S000000") {
   						$jq.extend(menuItem, {systemCreate:false});
   					}
   					
   					return menuItem;
   				}
		    
   			}   
		}).bind("create.jstree", function(event, data) { 

		}).bind("move_node.jstree", function(event, data) {  
		    var currNode = data.rslt.o; 
		    var parentNode = data.inst._get_parent(currNode);
		    var prevNode = data.inst._get_prev(currNode);
		    var prevParentNode = data.inst._get_parent(prevNode);
		    
		    var currNodeSystemCode = $jq.parseJSON($jq(currNode).attr("data")).code;
		    var currParentSystemCode = $jq.parseJSON($jq(parentNode).attr("data")).code;
		    var prevNodeSystemCode = $jq.parseJSON($jq(prevNode).attr("data")).code;
		    var prevParentSystemCode = $jq.parseJSON($jq(prevParentNode).attr("data")).code;
		    
		    var parentSystemCodeOfParent = $jq.parseJSON($jq(parentNode).attr("data")).parent;
		    
		    if(parentSystemCodeOfParent != 'S000000' && currParentSystemCode != 'S000000') {
		    	alert("<ikep4j:message pre='${preAlert}' key='moveLevelLimit' />");
		    	
		    	$jq.jstree.rollback(data.rlbk);
		    	
		    	return;
		    }
		   	
		    if(($jq(currNode).attr("class")=="jstree-closed" || $jq(currNode).find("li[class!=jstree]").length > 0) && currParentSystemCode != 'S000000') {
		    	alert("<ikep4j:message pre='${preAlert}' key='moveOnlyRoot' />");
		    	
				$jq.jstree.rollback(data.rlbk);
		    	
		    	return;
		    }

		    if(!fromContext) {
				if(confirm("<ikep4j:message pre='${preAlert}' key='confirmMove' />")) {
					$jq.post("<c:url value='moveDndPortalSystem.do'/>", 
							{"currNodeSystemCode" : currNodeSystemCode, 
							"currParentSystemCode" : currParentSystemCode,
							"prevNodeSystemCode" : prevNodeSystemCode,
							"prevParentSystemCode" : prevParentSystemCode}
					).success(function(data) {
						
					}).error(function(event, request, settings) {
						alert("<ikep4j:message pre='${preAlert}' key='noSystemMove' />");
						
						$jq.jstree.rollback(data.rlbk); 	
					});  
				} else {
					$jq.jstree.rollback(data.rlbk); 
				}						    	
		    } else {
		    	fromContext = false;
		    }
		}).delegate("a[href$='#']", "dblclick", function(e) {
			$jq("#portalSystemTree").jstree("save_opened");
			
			e.preventDefault(); 
			this.blur(); 
			
			$jq("#portalSystemTree").jstree("toggle_node", this, false, true);
        });
		
		/* 노드 클릭시 이벤트*/	
	    $jq("#portalSystemTree").delegate("a", "click", function() {
	    		
	    	tempParent = $jq(this).parents("li:eq(1)");
	    	tempCurrItem = $jq(this).parent();
	    	tempPrevItem = $jq(this).parent().prev();
	    	
	    	var systemCode = $jq.parseJSON(tempCurrItem.attr("data")).code;
	    	var parentSystemCode = $jq.parseJSON(tempCurrItem.attr("data")).parent;
			
	    	if(parentSystemCode != null && systemCode != parentSystemCode) {
	    		getSystem(systemCode);
				//showUpdateButton();
	    	} else {
	    		//getForm();
	    		getSystem(systemCode);
	    		//showCreateButton();
	    	}
			
		});
		
		iKEP.loadSecurity("Portal-System", "${portalSystem.systemCode}", "READ", "User,Group,Role,Job,Duty,ExpUser");
		
	});
	
	getForm = function (serializeData) {

		$jq.ajax({
			url : "<c:url value='portalSystemForm.do'/>",
			data : serializeData,
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
	getSystem = function (systemCode) {
		
		$jq.ajax({
			url : "<c:url value='portalSystemForm.do'/>",
			data : {
				systemCode : systemCode,
				fieldName : "systemName",
				itemTypeCode : "PO"
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
	
	displayInputValue = function(value) {
		
		if(value == 0) { 
			$jq("#urlTypeTr").show();
			$jq("#urlTr").show();
			$jq("#targetTr").show();
		} else if(value == 1){
			$jq("#urlTypeTr").hide();
			$jq("#urlTr").hide();
			$jq("#targetTr").hide();
		}
		
	};
	
	popupSystemUrl = function() {
		
		var url = "<c:url value='/admin/screen/systemurl/popupPortalSystemUrlList.do' />";
		
		iKEP.popupOpen(url, {width:800, height:510, callback:getUrlValue()}, "PortalSystemUrl");
		
	};
	
	getUrlValue = function() {
		
		$jq("input[name=url]").val($jq("input[name=urlInput]").val());
		
	};
	
	checkSystemCode = function () {
		
		<c:choose>
		<c:when test="${childCount > 0}">
		alert("<ikep4j:message pre='${preAlert}' key='noUpdateSystemCode' />");
		
		return;
		</c:when>
		<c:otherwise>
		if($jq("#systemCode").val() == "") {	
			alert("<ikep4j:message pre='${preAlert}' key='noCheckSystemCode' />");
		} else {
			$jq.ajax({
				url : "<c:url value='checkSystemCode.do' />",     
				data : {
					systemCode : $jq("#systemCode").val()
				},     
				type : "post",
				success : function(result) {     
					if(result == "duplicated") {
						alert("<ikep4j:message pre='${preAlert}' key='systemCodeDuplicate' />");
					} else {
						alert("<ikep4j:message pre='${preAlert}' key='useSystemCode' />");
						
						$jq("#codeCheck").val("success");
						$jq("#codeValue").val($jq("#systemCode").val());
					}
				},
				error : function(event, request, settings) { 	
					alert("error");	
				}
			});			
		}
		</c:otherwise>
		</c:choose>
		
	};
	
})(jQuery);
//]]>
</script>
 
<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preMain}" key="pageTitle" /></h2>
</div>
<!--//pageTitle End-->		

<!--directive Start-->
<div class="directive"> 
	<ul>
		<li><span><ikep4j:message pre="${preMain}" key="howtoAddSystem" /></span></li>	
		<li><span><ikep4j:message pre="${preMain}" key="howtoModifySystem" /></span></li>	
		<li><span><ikep4j:message pre="${preMain}" key="howtoDeleteSystem" /></span></li>
		<li><span><ikep4j:message pre="${preMain}" key="howtoMoveSystem" /></span></li>																		
	</ul>
</div>
<div class="blockBlank_10px"></div>
<!--//directive End-->

<!--blockLeft Start-->
<div class="blockLeft" style="width:30%;">	
	<div class="leftTree treeBox">
	
		<div id="portalSystemTree"></div>
		
	</div>
</div>
<!--//blockLeft End-->


<!--blockRight Start-->
<div class="blockRight" style="width:68%;">

	<div id="viewDiv">
		<form name="portalSystemForm" id="portalSystemForm" action="" method="post" >
		<input type="hidden" id="oldSystemCode" name="oldSystemCode" value="${portalSystem.systemCode}"/>
		<input type="hidden" id="parentSystemCode" name="parentSystemCode" value="${portalSystem.parentSystemCode}"/>
		<input type="hidden" id="systemType" name="systemType" value="" />
		<input type="hidden" id="sortOrder" name="sortOrder" value="${portalSystem.sortOrder}" />
		<input type="hidden" id="urlType" name="urlType" value="" />
		<input type="hidden" id="urlInput" name="urlInput" value="" />
		<input type="hidden" id="target" name="target" value="" />
		<input type="hidden" id="childCount" name="childCount" value="${childCount}"/>
		<input type="hidden" id="codeCheck" name="codeCheck" value=""/>
		<input type="hidden" id="codeValue" name="codeValue" value=""/>
		<input type="hidden" id="addrGroupType" name="addrGroupType" value=""/>
		<input type="hidden" id="groupTypeCount" name="groupTypeCount" value="0"/>
		<input type="hidden" id="systemName" name="systemName" value="" />
			
			<div class="blockDetail">
				<table id="mainTable" summary="<ikep4j:message pre='${preForm}' key='tableSummary' />">
					<caption></caption>
					<colgroup>
						<col width="10%"/>
						<col width="8%"/>
						<col width="82%"/>
					</colgroup>
					<tbody>
						<tr>
							<th scope="col" colspan="2">
								<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="systemCode" />
							</th>
							<td class="textLeft">
								<div>
									<input id="systemCode" name="systemCode" type="text" class="inputbox" style="width:30%" value="${portalSystem.systemCode}" <c:if test="${childCount > 0}">readonly="readonly"</c:if> />
									<a href="#" class="button_ic" onclick="checkSystemCode(); return false;" title="<ikep4j:message pre='${preButton}' key='checkDuplicate' />">
										<span><ikep4j:message pre="${preButton}" key="checkDuplicate" /></span>
									</a>
								</div>
							</td>
						</tr>
						
						<!--systemName Start-->
						<tr>
							<th scope="row" rowspan="${localeSize}">
								<ikep4j:message pre="${preForm}" key="systemName" />
							</th>
							<c:forEach var="i18nMessage" items="${portalSystem.i18nMessageList}" varStatus="loopStatus">
							<c:if test="${i18nMessage.fieldName eq 'systemName' }">
							<c:if test="${i18nMessage.index > 1}">
						<tr>
							</c:if>
							<th scope="row">
								<span class="colorPoint">*</span><c:out value="${i18nMessage.localeCode}"/>
							</th>
							<td>
								<div>
								<spring:bind path="portalSystem.i18nMessageList[${loopStatus.index}].itemMessage">
									<input type="text" id="${i18nMessage.localeCode}" name="i18nMessageList[${loopStatus.index}].itemMessage" value="${i18nMessage.itemMessage}" class="inputbox w100" />
									<input type="hidden" name="i18nMessageList[${loopStatus.index}].localeCode" value="${i18nMessage.localeCode}" />
									<input type="hidden" name="i18nMessageList[${loopStatus.index}].fieldName" value="${i18nMessage.fieldName}" />
									<input type="hidden" name="i18nMessageList[${loopStatus.index}].messageId" value="${i18nMessage.messageId}" />
								</spring:bind>
								</div>
							</td>
						</tr>
							</c:if>
							</c:forEach>
						<!--//systemName End-->
						
						<!--//className Start-->
						<tr>
							<th colspan="2">
								<ikep4j:message pre="${preForm}" key="className" />
							</th>
							<td class="textLeft">
								<div>
								<spring:bind path="portalSystem.className">
									<input id="${status.expression}" name="${status.expression}" type="text" class="inputbox w100" value="${portalSystem.className}" />
									<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
								</spring:bind>
								<input type="hidden" id="oldClassName" name="oldClassName" value="${portalSystem.className}" />
								</div>
								<div class="tdInstruction">
									<ikep4j:message pre="${preForm}" key="classNameDesc" />
								</div>
							</td>
							
						</tr>
						<!--//className End-->
						
						<!--description Start-->	
						<tr>
							<th scope="col" colspan="2">
								<ikep4j:message pre="${preForm}" key="description" />
							</th>
							<td class="textLeft">
								<div>
								<spring:bind path="portalSystem.description">
									<input id="${status.expression}" name="${status.expression}" type="text" class="inputbox w100" value="${portalSystem.description}" />
								</spring:bind>
								</div>
							</td>
						</tr>
						<!--//description End-->
						
						<!--mainView Start-->
						<tr>
							<th scope="col" colspan="2">
								<ikep4j:message pre="${preForm}" key="mainView" />
							</th>
							<td class="textLeft">
								<input type="radio" id="mainViewYes" name="mainView" class="radio" value="1" title="<ikep4j:message pre='${preForm}' key='yes' />" <c:choose><c:when test="${empty portalSystem.systemCode}">checked="checked"</c:when><c:when test="${!empty portalSystem.systemCode && portalSystem.mainView == 1}">checked="checked"</c:when><c:otherwise></c:otherwise></c:choose> />
								<label for="mainViewYes"><ikep4j:message pre="${preForm}" key="yes" /></label>&nbsp;
								<input type="radio" id="mainViewNo" name="mainView" class="radio" value="0" title="<ikep4j:message pre='${preForm}' key='no' />" <c:if test="${!empty portalSystem.systemCode && portalSystem.mainView == 0}">checked="checked"</c:if> />
								<label for="mainViewNo"><ikep4j:message pre="${preForm}" key="no" /></label>
							</td>
						</tr>
						<!--//mainView End-->
						
						<!--systemType Start-->
						<tr>
							<th scope="col"	colspan="2">
								<ikep4j:message pre="${preForm}" key="systemType" />
							</th>
							<td class="textLeft">
								<input type="radio" id="systemTypeItem" name="systemTypeRadio" class="radio" value="ITEM" <c:choose><c:when test="${empty portalSystem.systemCode}">checked="checked"</c:when><c:when test="${!empty portalSystem.systemCode && portalSystem.systemType == 'ITEM'}">checked="checked"</c:when><c:otherwise></c:otherwise></c:choose> onclick="displayInputValue(0);" />
								<label for="systemTypeItem"><ikep4j:message pre="${preForm}" key="link" /></label>&nbsp;
								<input type="radio" id="systemTypeCategory" name="systemTypeRadio" class="radio" value="CATEGORY" <c:if test="${!empty portalSystem.systemCode && portalSystem.systemType == 'CATEGORY'}">checked="checked"</c:if> onclick="displayInputValue(1);" />
								<label for="systemTypeCategory"><ikep4j:message pre="${preForm}" key="category" /></label>
							</td>
						</tr>
						<!--//systemType End-->
						
						<!--urlType Start-->
						<tr id="urlTypeTr" <c:if test="${!empty portalSystem.systemCode && portalSystem.systemType == 'CATEGORY'}">style="display:none;"</c:if>>
							<th scope="col" colspan="2">
								<ikep4j:message pre="${preForm}" key="urlType" />
							</th>
							<td class="textLeft">
								<input type="radio" class="radio" title="<ikep4j:message pre='${preForm}' key='url' />" id="urlTypeUrl" name="urlTypeRadio" value="URL" <c:choose><c:when test="${empty portalSystem.systemCode}">checked="checked"</c:when><c:when test="${!empty portalSystem.systemCode && portalSystem.urlType == 'URL'}">checked="checked"</c:when><c:otherwise></c:otherwise></c:choose> />
								<label for="urlTypeUrl"><ikep4j:message pre="${preForm}" key="url" /></label>&nbsp;
								<input type="radio" class="radio" title="<ikep4j:message pre='${preForm}' key='javascript' />" id="urlTypeJavascript" name="urlTypeRadio" value="JAVASCRIPT" <c:if test="${!empty portalSystem.systemCode && portalSystem.urlType == 'JAVASCRIPT'}">checked="checked"</c:if> />
								<label for="urlTypeJavascript"><ikep4j:message pre="${preForm}" key="javascript" /></label>
							</td>
						</tr>
						<!--//urlType End-->
						
						<!--url Start-->
						<tr id="urlTr" <c:if test="${!empty portalSystem.systemCode && portalSystem.systemType == 'CATEGORY'}">style="display:none;"</c:if>>
							<th scope="col" colspan="2">
								<ikep4j:message pre="${preForm}" key="url" />
							</th>
							<td class="textLeft">
								<div>
								<spring:bind path="portalSystem.url">
									<input id="url" name="url" type="text" class="inputbox" style="width:80%" value="${portalSystem.url}" />
								</spring:bind>
								<a href="#" class="button_ic" onclick="popupSystemUrl(); return false;" title="<ikep4j:message pre='${preButton}' key='example' />">
									<span><ikep4j:message pre="${preButton}" key="example" /></span>
								</a>
								</div>
							</td>
						</tr>
						<!--//url End-->
						
						<!--target Start-->
						<tr	id="targetTr" <c:if test="${!empty portalSystem.systemCode && portalSystem.systemType == 'CATEGORY'}">style="display:none;"</c:if>>
							<th scope="col"	colspan="2">
								<ikep4j:message pre="${preForm}" key="target" />
							</th>
							<td class="textLeft">
								<div>
									<input type="radio" id="targetWindow" name="targetRadio" class="radio" value="WINDOW" <c:if test="${!empty portalSystem.systemCode && portalSystem.target == 'WINDOW'}">checked="checked"</c:if> />
									<label for="targetWindow"><ikep4j:message pre="${preForm}" key="window" /></label>&nbsp;
									<input type="radio" id="targetInner" name="targetRadio" class="radio" value="INNER" <c:choose><c:when test="${empty portalSystem.systemCode}">checked="checked"</c:when><c:when test="${!empty portalSystem.systemCode && portalSystem.target == 'INNER'}">checked="checked"</c:when><c:when test="${!empty portalSystem.systemCode && empty portalSystem.target && portalSystem.systemType == 'CATEGORY'}">checked="checked"</c:when><c:otherwise></c:otherwise></c:choose> />
									<label for="targetInner"><ikep4j:message pre="${preForm}" key="inner" /></label>
								</div>
								<div class="tdInstruction">
									<ikep4j:message pre="${preForm}" key="targetDesc" />
								</div>
							</td>
						</tr>
						<!--//target End-->
						
					</tbody>
				</table>
			</div>
		</form>
	</div>
	
</div>
<!--//blockLight End-->