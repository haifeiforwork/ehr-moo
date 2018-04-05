<%--
=====================================================
	* 기능설명	:	Collaboration Type 별 목록(조직/TFT/Cop/Informal)
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="commonPrefix">message.collpack.collaboration.workspaceMap</c:set>
<c:set var="headerPrefix">message.collpack.collaboration.workspaceMap.header</c:set>
<c:set var="confirmPrefix">message.collpack.collaboration.workspaceMap.confirm</c:set>
<c:set var="buttonPrefix">message.collpack.collaboration.workspaceMap.button</c:set>
<c:set var="pagePrefix">message.collpack.collaboration.workspaceMap.page</c:set>
<c:set var="searchPrefix">message.collpack.collaboration.workspaceMap.search</c:set>
<c:set var="messagePrefix">message.collpack.collaboration.workspaceMap.message</c:set>
<c:set var="tablePrefix">message.collpack.collaboration.workspaceMap.table</c:set>
<c:set var="treePrefix">message.collpack.collaboration.workspaceMap.tree</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${headerPrefix}" key="admin"/></h2>
</div>

<!--//pageTitle End-->

<!--blockLeft Start-->
<div class="blockLeft" style="width:30%;">	
	<div class="leftTree treeBox" style="height:300px;">
		<div id="mapAdminTree"></div>
	</div>
</div>
<!--//blockLeft End-->

<!--blockRight Start-->
<div class="blockRight" style="width:68%;">

	<!--blockDetail Start-->
	<div id="itemEdit" style="display:none">
		<div class="blockDetail clear">
		<form id="mapForm">
			<input type="hidden" name="workspaceId" value="${workspaceId}" title="<ikep4j:message pre="${messagePrefix}" key="workspaceId"/>"/>
			<input type="hidden" name="actionType" title="<ikep4j:message pre="${messagePrefix}" key="actionType"/>"/>
			<input type="hidden" name="mapId" title="<ikep4j:message pre="${messagePrefix}" key="mapId"/>"/>
			<input type="hidden" name="mapParentId" title="<ikep4j:message pre="${messagePrefix}" key="mapParentId"/>"/>
			<input type="hidden" name="sortOrder" title="<ikep4j:message pre="${messagePrefix}" key="sortOrder"/>"/>
			<table summary="<ikep4j:message pre="${messagePrefix}" key="tableSummary"/>">
				<caption></caption>
				<tbody>
					<tr>
						<th scope="row" width="20%" class="textCenter"><ikep4j:message pre="${tablePrefix}" key="mapName"/></th>
						<td class="textLeft">
							<spring:bind path="workspaceMap.mapName">
								<input name="${status.expression}" id="${status.expression}" value="${status.value}" class="inputbox w95" type="text"/>
								<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
							</spring:bind>
						</td>
					</tr>
					<tr>
						<th scope="row" width="20%" class="textCenter"><ikep4j:message pre="${tablePrefix}" key="mapDesc"/></th>
						<td class="textLeft">
							<spring:bind path="workspaceMap.mapDescription">
								<input name="${status.expression}" id="${status.expression}" value="${status.value}" class="inputbox w95" type="text"/>
								<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
							</spring:bind>
						</td>
					</tr>
					<tr>
						<th scope="col" width="20%" class="textCenter">Tags</th>
						<td class="textLeft">
							<spring:bind path="workspaceMap.tags">
								<input name="${status.expression}" id="${status.expression}" value="${status.value}" class="inputbox w95" type="text"/>
								<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
							</spring:bind>
							<div class="tdInstruction">
								<span class="colorPoint"><ikep4j:message pre="${tablePrefix}" key="mapInst"/></span>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		</div>
		<div class="blockButton" id="changeButtonDiv">
			<ul>
				<li><a id="btnSave" class="button" onclick="btnSaveClickHandler(); return false;"><span><ikep4j:message pre="${buttonPrefix}" key="create"/></span></a></li>
				<li><a id="btnCancel" class="button" onclick="btnCancelClickHandler(); return false;"><span><ikep4j:message pre="${buttonPrefix}" key="cancel"/></span></a></li>
			</ul>
		</div>
	</div>
	<div id="itemNormal" style="display:none">
		<div class="blockDetail clear">
			<table summary="<ikep4j:message pre="${messagePrefix}" key="tableSummary"/>">
				<caption></caption>
				<tbody>
					<tr>
						<th scope="col" width="20%" class="textCenter"><ikep4j:message pre="${tablePrefix}" key="mapName"/></th>
						<td class="textLeft"><span id="mapNameView"></span>
						</td>
					</tr>
					<tr>
						<th scope="col" width="20%" class="textCenter"><ikep4j:message pre="${tablePrefix}" key="mapDesc"/></th>
						<td class="textLeft"><span id="mapDescView"></span>
						</td>
					</tr>
					<tr>
						<th scope="col" width="20%" class="textCenter">Tags</th>
						<td class="textLeft"><span id="tagsView"></span>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="blockButton" id="normalButtonDiv">
			<ul>
				<li>
					<a id="btnModify" class="button" onclick="btnModifyClickHandler(); return false;">
						<span><ikep4j:message pre="${buttonPrefix}" key="modify"/></span>
					</a>
				</li>
			</ul>
		</div>
	</div>
	
	<div id="help" style="display:block">
	<div class="blockDetail clear">
	<ikep4j:message pre="${messagePrefix}" key="help1" /><br/>
	&nbsp; <ikep4j:message pre="${messagePrefix}" key="help2" /><br/>
	&nbsp; <ikep4j:message pre="${messagePrefix}" key="help3" /><br/>
	<br/>
	<ikep4j:message pre="${messagePrefix}" key="help4" /><br/>
	&nbsp; <ikep4j:message pre="${messagePrefix}" key="help5" /><br/>
	&nbsp; <ikep4j:message pre="${messagePrefix}" key="help6" /><br/>
	<br/>
	<ikep4j:message pre="${messagePrefix}" key="help7" /><br/>
	&nbsp; <ikep4j:message pre="${messagePrefix}" key="help8" /><br/>
	</div>
	<!--//blockDetail End-->

</div>
<!--//blockRight End-->
<!--//blockListTable End-->


<script type="text/javascript">

// 처리 상태 상수
var STATUS_CREATE = "create";
var STATUS_UPDATE = "update";


	//선택된 카테고리
	var selectedItem = null;
	var orgSelectedItem = null;
	
	var nodeCreateHandler = function(item) {

		itemClear();
		setActionStatus(STATUS_CREATE);
		$jq("#mapForm input[name=mapParentId]").val(item.attr("id"));
		statusChangeEdit();
		selectedItem = item;
	};

	var nodeRenameHandler = function(item) {
		itemClear();
		setActionStatus(STATUS_UPDATE);
		itemSetting(item);
		statusChangeEdit();
		selectedItem = item;
	};

	var nodeRemoveHandler = function(item) {
		if (!confirm($jq("#mapAdminTree").jstree("get_text", item) + " 를(을) 삭제하시겠습니까?")) {
			return false;
		}

		var id = item.attr("id");
		$jq.post("<c:url value='/collpack/collaboration/workspaceMap/admin/deleteMap.do'/>", "mapId=" + id+"&workspaceId="+${workspaceId})
		.success(function(data) {
			$jq("#mapAdminTree").jstree("delete_node", item);
			// 메뉴트리 반영
			parent.menuTreeDeleteNode(id);
		})
		.error(function(event, request, settings) { alert("category delete error"); }
		);
	};

	var nodeSelectHandler = function(item) {
		if (isRootNode(item)) {
			statusChangeHide();
			return;
		}

		itemSetting(item);
		statusChangeNormal();
	};


	// 카테고리 선택
	var setSelectedItem = function(item) {
		selectedItem = item;
	};

	// Root Category 판별
	var isRootNode = function(item) {
		return ("${rootMap.mapId}" == item.attr("id"));
	};

	// Action Status 변경 (추가 / 수정)
	var setActionStatus = function(status) {
		$jq("#mapForm input[name=actionType]").val(status);
	};

	var getActionStatus = function() {
		return $jq("#mapForm input[name=actionType]").val();
	};

	var isActionStatusCreate = function() {
		return (getActionStatus() == STATUS_CREATE)
	};

	// 카테고리 숨김모드 변환
	var statusChangeHide = function() {
		$jq("#itemNormal").hide();
		$jq("#itemEdit").hide();
		$jq("#help").show();
	};

	// 카테고리 수정모드 변환
	var statusChangeEdit = function() {
		$jq("#help").hide();
		$jq("#itemNormal").hide();
		$jq("#itemEdit").show();
	};

	// 카테고리 일반모드 변환
	var statusChangeNormal = function() {
		$jq("#help").hide();
		$jq("#itemEdit").hide();
		$jq("#itemNormal").show();
	};

	// 아이템 Clear
	var itemClear = function() {
		$jq("#mapForm input[name=actionType]").val("");
		$jq("#mapForm input[name=mapId]").val("");
		$jq("#mapForm input[name=mapParentId]").val("");
		$jq("#mapForm input[name=sortOrder]").val("");

		$jq("#mapForm input[name=mapName]").val("");
		$jq("#mapForm input[name=mapDescription]").val("");
		$jq("#mapForm input[name=tags]").val("");

		$jq("#mapNameView").text("");
		$jq("#mapDescView").text("");
		$jq("#tagsView").text("");
	};

	// 아이템 Setting
	var itemSetting = function(item) {
		$jq("#mapForm input[name=mapId]").val(item.attr("id"));
		$jq("#mapForm input[name=mapParentId]").val(item.attr("pid"));
		$jq("#mapForm input[name=sortOrder]").val(item.attr("order"));

		$jq("#mapForm input[name=mapName]").val($jq("#mapAdminTree").jstree("get_text", item));
		$jq("#mapForm input[name=mapDescription]").val(item.attr("mapDescription"));
		$jq("#mapForm input[name=tags]").val(item.attr("tags"));

		$jq("#mapNameView").text($jq("#mapAdminTree").jstree("get_text", item));
		$jq("#mapDescView").text(item.attr("mapDescription"));
		$jq("#tagsView").text(item.attr("tags"));
	};

	// 트리 아이템 추가

	// 버튼 Click

	var btnCancelClickHandler = function() {
		statusChangeNormal();
	};

	var btnModifyClickHandler = function() {
		statusChangeEdit();
		setActionStatus(STATUS_UPDATE);
	};


	var btnSaveClickHandler = function() {
		$jq("#mapForm").submit(); 	
		return false; 
	};




	$jq(document).ready(function() {
	
	/**
	 * Validation Logic Start
	 */
	
		var validOptions = {
			rules : {
				mapName : {
					required : true,
					maxlength : 200
				},
				mapDescription : {
					required : true,
					maxlength : 500
				},
				tags : {
					required : true
				}
			},
			messages : {
				mapName : {
					direction	:	"bottom",
					required	:	"<ikep4j:message key="NotEmpty.map.mapName" />",
					maxlength	:	"<ikep4j:message key="Size.map.mapName" />"
					
				},
				mapDescription : {
					direction	:	"bottom",
					required	:	"<ikep4j:message key="NotEmpty.map.mapDescription" />",
					maxlength	:	"<ikep4j:message key="Size.map.mapDescription" />"
					
				},
				tags : {
					direction	:	"bottom",
					required	:	"<ikep4j:message key="NotEmpty.map.tags" />"
					
				}
			},
			submitHandler : function(form) {
				// 부가 검증 처리
				// Tag 개수, 중복 체크
					var tags = $jq("#mapForm input[name=tags]").val();
					var tagArray = tags.split(",");
					if (10 < tagArray.length) {
						alert("<ikep4j:message pre='${messagePrefix}' key='alert.maxTag'/>");
						return;
					}
					tagArray.sort();
					for (var i=0; i<tagArray.length-1; i++) {
						if (tagArray[i] == tagArray[i+1]) {
							alert("<ikep4j:message pre='${messagePrefix}' key='alert.dupTag'/>" + " (" + tagArray[i] + ")");
							return;
						}
					}
				
					if (!confirm("<ikep4j:message pre='${confirmPrefix}' key='save'/>")) {
						return;
					}
				
					
					if (isActionStatusCreate()) {
						// 신규
						$jq("#mapForm input[name=sortOrder]").val("0");
						$jq.post("<c:url value='/collpack/collaboration/workspaceMap/admin/createMap.do'/>", $jq("#mapForm").serialize())
					    .success(function(data) {
					    	$jq("#mapAdminTree").jstree("create_node", selectedItem, "last",$jq.parseJSON(data),
					    			function() {
					    				$jq("#mapAdminTree").jstree("open_node", selectedItem);

										// 메뉴트리 반영
					    				parent.menuTreeCreateNode(selectedItem.attr("id"), $jq.parseJSON(data));
			    						orgSelectedItem=selectedItem;
			    						
					    				// 맴 메인호출
					    				//location.href="<c:url value='/collpack/collaboration/workspaceMap/admin/workspaceMapAdminMain.do?workspaceId=${workspaceId}&mapId='/>"+$jq.parseJSON(data).attr.id;
					    				
										$jq("#mapForm input[name=mapId]").val($jq.parseJSON(data).attr.id);
					    				$jq("#mapForm input[name=mapParentId]").val($jq.parseJSON(data).attr.pid);
					    				$jq("#mapForm input[name=sortOrder]").val($jq.parseJSON(data).attr.order);
				
					    				$jq("#mapForm input[name=mapName]").val($jq.parseJSON(data).data.title);
					    				$jq("#mapForm input[name=mapDescription]").val($jq.parseJSON(data).attr.mapDescription);
					    				$jq("#mapForm input[name=tags]").val($jq.parseJSON(data).attr.tags);
				
					    				$jq("#mapNameView").text($jq.parseJSON(data).data.title);
					    				$jq("#mapDescView").text($jq.parseJSON(data).attr.mapDescription);
					    				$jq("#tagsView").text($jq.parseJSON(data).attr.tags);
										
										$jq("#mapAdminTree").find("#"+$jq("#mapForm input[name=mapId]").val()).each( function (event, data) {
											selectedItem=data;
											//setSelectedItem(selectedItem);
										});					    				
					    				statusChangeNormal();
					    			}
					    	);
					    })
					    .error(function(event, request, settings) { alert("category create error"); }
					    );
					} else {
						// 수정
						$jq.post("<c:url value='/collpack/collaboration/workspaceMap/admin/updateMap.do'/>", $jq("#mapForm").serialize())
					    .success(function(data) {
					    	var mapName = $jq("#mapForm input[name=mapName]").val();
					    	var mapDescription = $jq("#mapForm input[name=mapDescription]").val();
					    	var tags = $jq("#mapForm input[name=tags]").val();

					    	$jq("#mapAdminTree").jstree("rename_node", selectedItem, mapName);
					    	var obj = $jq("#" + $jq.parseJSON(data).attr.id);
					    	obj.attr("mapdescription", mapDescription);
					    	obj.attr("tags",tags);
					    	
							// 메뉴트리 반영
					    	parent.menuTreeUpdateNode($jq.parseJSON(data).attr.id, mapName, mapDescription, tags);
							
					    						    	
							$jq("#mapForm input[name=mapId]").val($jq.parseJSON(data).attr.id);
					    	$jq("#mapForm input[name=mapParentId]").val($jq.parseJSON(data).attr.pid);
					    	$jq("#mapForm input[name=sortOrder]").val($jq.parseJSON(data).attr.order);
				
					    	$jq("#mapForm input[name=mapName]").val($jq.parseJSON(data).data.title);
					    	$jq("#mapForm input[name=mapDescription]").val($jq.parseJSON(data).attr.mapDescription);
					    	$jq("#mapForm input[name=tags]").val($jq.parseJSON(data).attr.tags);
				
					    	$jq("#mapNameView").text($jq.parseJSON(data).data.title);
					    	$jq("#mapDescView").text($jq.parseJSON(data).attr.mapDescription);
					    	$jq("#tagsView").text($jq.parseJSON(data).attr.tags);
										
					    	statusChangeNormal();					    	
					    	
					    })
					    .error(function(event, request, settings) { alert("category rename error"); }
					    );
					}
			}
		}
	
		var validator = new iKEP.Validator("#mapForm", validOptions);
		
	
		$jq("#mapAdminTree").jstree({
			core : {
				animation : 200
			},
			plugins:["themes", "ui", "json_data", "dnd", "crrm", "contextmenu"],
			ui : {
				select_limit : 1
			},
			json_data : {
				data : {data:{title:"${rootMap.mapName}",icon:"dept"},attr:{id:"${rootMap.mapId}",pid:"${rootMap.mapParentId}",workspaceId:"${workspaceId}"},state:"open",children:${mapTagJSON}},
				ajax : {
					url : "<c:url value='/collpack/collaboration/workspaceMap/admin/listChildMaps.do'/>",
					data : function($el) {
						return {
							"mapParentId" : $el.attr("id"),
							"workspaceId" : $el.attr("workspaceId")
						};
					},
					success : function(data) {
						return data.items;
					}
				}
			},
			contextmenu : {
				items : function($item) {
					var menuItem = {
						create : { label : "추가", action : nodeCreateHandler},
						rename : { label : "수정", action : nodeRenameHandler},
						remove : { label : "삭제", action : nodeRemoveHandler},
						ccp : false
					};
					//iKEP.debug($item)
					switch($item.attr("id")) {
						case "${rootMap.mapId}" : $jq.extend(menuItem, {rename:false, remove:false});
							break;
					}
					return menuItem;
	  			}
			}
		})
		.bind("select_node.jstree", function(event, data) {
			var node = data.rslt.obj;

			selectedItem = node;
			nodeSelectHandler(node);
		})
		.bind("move_node.jstree", function(event, data) {

			var o = data.rslt.o;   // 이동한 카테고리
			var np = data.rslt.np; // 이동후 부모 카테고리
			var op = data.rslt.op; // 이동전 부모 카테고리
			var cp = data.rslt.cp; // 이동후 순번 (0부터 시작)

			// Root Map 로 이동할수 없다.
			if ("mapAdminTree" == $jq(np).attr("id")) {
				$jq.jstree.rollback(data.rlbk);
				alert("<ikep4j:message pre='${treePrefix}' key='alert.doNotMoveToRoot'/>");
				return;
			}

			var sourceText = $jq("#mapAdminTree").jstree("get_text", o);
			//if (!confirm(sourceText + "<ikep4j:message pre='${treePrefix}' key='confirm.move'/>")) {
			if (!confirm(sourceText + "를(을) 이동하시겠습니까?")) {
				$jq.jstree.rollback(data.rlbk);
				return;
			}

			var params = "";
			params += "sourceId=" + o.attr("id");
			params += "&sourceParentId=" + op.attr("id");
			params += "&sourceSortOrder=" + o.attr("order");
			params += "&targetParentId=" + np.attr("id");
			params += "&targetSortOrder=" + ++cp;
			$jq.post("<c:url value='/collpack/collaboration/workspaceMap/admin/moveWorkspaceMap.do'/>", params)
		    .success(function(data) {})
		    .error(function(event, request, settings) {
		    	$jq.jstree.rollback(data.rlbk);
		    	alert("category move error");
		    });
		    
		    parent.menuMapTreeReload();
		});
		

	});


</script>