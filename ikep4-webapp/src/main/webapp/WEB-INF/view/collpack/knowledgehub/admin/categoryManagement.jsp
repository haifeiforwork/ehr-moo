<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="confirmPrefix">ui.collpack.knowledgehub.common.confirm</c:set>
<c:set var="buttonPrefix">ui.collpack.knowledgehub.common.button</c:set>
<c:set var="treePrefix">ui.collpack.knowledgehub.common.tree</c:set>
<c:set var="validationMessagePrefix"></c:set>
<c:set var="messagePrefix">ui.collpack.knowledgehub.admin.categoryManagementView</c:set>

<div id="tagResult">

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${messagePrefix}" key="pageTitle"/></h2>
</div>
<!--//pageTitle End-->

<!--blockLeft Start-->
<div class="blockLeft" style="width:30%;">	
	<div class="leftTree treeBox" style="height:300px;">
		<div id="categoryTree"></div>
	</div>
</div>
<!--//blockLeft End-->

<!--blockRight Start-->
<div class="blockRight" style="width:68%;">

	<!--blockDetail Start-->
	<div id="itemEdit" style="display:none">
		<div class="blockDetail clear">
		<form id="categoryForm" action="">
			<input type="hidden" name="actionType"/>
			<input type="hidden" name="categoryId"/>
			<input type="hidden" name="categoryParentId"/>
			<input type="hidden" name="sortOrder"/>
			<table summary="<ikep4j:message pre="${messagePrefix}" key="body.summary"/>">
				<caption></caption>
				<tbody>
					<tr>
						<th scope="row" width="20%" class="textCenter"><ikep4j:message pre="${messagePrefix}" key="body.category.title"/></th>
						<td class="textLeft"><div>
							<input name="categoryName" title="<ikep4j:message pre="${messagePrefix}" key="body.category.title"/>" class="inputbox w95" type="text"/>
						</div></td>
					</tr>
					<tr>
						<th scope="col" width="20%" class="textCenter"><ikep4j:message pre="${messagePrefix}" key="body.tag.title"/></th>
						<td class="textLeft"><div>
							<input name="tags" title="<ikep4j:message pre="${messagePrefix}" key="body.tag.title"/>" class="inputbox w95" type="text"/></div>
							<div class="tdInstruction">
								<span class="colorPoint"><ikep4j:message pre="${messagePrefix}" key="body.tag.comment"/></span>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col" width="20%" class="textCenter"><ikep4j:message pre="${messagePrefix}" key="body.target.title"/></th>
						<td>
							<ul class="map_adm">
								<li><input name="isBlog" type="checkbox" class="checkbox" title="checkbox" value="1"/>Social Blog&nbsp;</li>
								<li><input name="isColl" type="checkbox" class="checkbox" title="checkbox" value="1"/>WorkSpace&nbsp;</li>
								<li><input name="isQna" type="checkbox" class="checkbox" title="checkbox" value="1"/>Q&A&nbsp;</li>
								<li><input name="isDic" type="checkbox" class="checkbox" title="checkbox" value="1"/>Coporate Voca&nbsp;</li>
								<li><input name="isManual" type="checkbox" class="checkbox" title="checkbox" value="1"/>Work Manual&nbsp;</li>
								<li><input name="isCafe" type="checkbox" class="checkbox" title="checkbox" value="1"/>Cafe&nbsp;</li>
								<li><input name="isForum" type="checkbox" class="checkbox" title="checkbox" value="1"/>Forum&nbsp;</li>
								<li><input name="isIdea" type="checkbox" class="checkbox" title="checkbox" value="1"/>Ideation&nbsp;</li>
								<li><input name="isBbs" type="checkbox" class="checkbox" title="checkbox" value="1"/>BBS&nbsp;</li>
							</ul>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		</div>
		<div class="blockButton" id="changeButtonDiv">
			<ul>
				<li><a id="btnSave" class="button" onclick="$jq('#categoryForm').submit();"><span><ikep4j:message pre="${buttonPrefix}" key="save"/></span></a></li>
				<li><a id="btnCancel" class="button" onclick="btnCancelClickHandler();"><span><ikep4j:message pre="${buttonPrefix}" key="cancel"/></span></a></li>
			</ul>
		</div>
	</div>
	<div id="itemNormal" style="display:none">
		<div class="blockDetail clear">
			<table summary="<ikep4j:message pre="${messagePrefix}" key="body.summary"/>">
				<caption></caption>
				<tbody>
					<tr>
						<th scope="col" width="20%" class="textCenter"><ikep4j:message pre="${messagePrefix}" key="body.category.title"/></th>
						<td class="textLeft"><span id="categoryNameView"></span>
						</td>
					</tr>
					<tr>
						<th scope="col" width="20%" class="textCenter"><ikep4j:message pre="${messagePrefix}" key="body.tag.title"/></th>
						<td class="textLeft"><span id="tagsView"></span>
						</td>
					</tr>
					<tr>
						<th scope="col" width="20%" class="textCenter"><ikep4j:message pre="${messagePrefix}" key="body.target.title"/></th>
						<td>
							<ul class="map_adm">
								<li><input name="isBlogView" type="checkbox" class="checkbox" disabled/>Social Blog&nbsp;</li>
								<li><input name="isCollView" type="checkbox" class="checkbox" disabled/>WorkSpace&nbsp;</li>
								<li><input name="isQnaView" type="checkbox" class="checkbox" disabled/>Q&A&nbsp;</li>
								<li><input name="isDicView" type="checkbox" class="checkbox" disabled/>Coporate Voca&nbsp;</li>
								<li><input name="isManualView" type="checkbox" class="checkbox" disabled/>Work Manual&nbsp;</li>
								<li><input name="isCafeView" type="checkbox" class="checkbox" disabled/>Cafe&nbsp;</li>
								<li><input name="isForumView" type="checkbox" class="checkbox" disabled/>Forum&nbsp;</li>
								<li><input name="isIdeaView" type="checkbox" class="checkbox" disabled/>Ideation&nbsp;</li>
								<li><input name="isBbsView" type="checkbox" class="checkbox" disabled/>BBS&nbsp;</li>
							</ul>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="blockButton" id="normalButtonDiv">
			<ul>
				<li><a id="btnModify" class="button" onclick="btnModifyClickHandler();"><span><ikep4j:message pre="${buttonPrefix}" key="modify"/></span></a></li>
			</ul>
		</div>
	</div>
	<!--//blockDetail End-->

</div>
<!--//blockRight End-->

</div>

<script type="text/javascript">
//<![CDATA[
var validator;

// 처리 상태 상수
var STATUS_CREATE = "create";
var STATUS_UPDATE = "update";


//선택된 카테고리
var selectedItem = null;

var nodeCreateHandler = function(item) {
	itemClear();
	setActionStatus(STATUS_CREATE);
	$jq("#categoryForm input[name=categoryParentId]").val(item.attr("id"));
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
	if (!confirm($jq("#categoryTree").jstree("get_text", item) + " <ikep4j:message pre='${confirmPrefix}' key='delete' />")) {
		return;
	}

	var id = item.attr("id");

	$jq.ajax({
		url : iKEP.getContextRoot() + "//collpack/knowledgehub//admin/deleteCategory.do",
		type : "post",
		data : "categoryId=" + id,
		success : function(data, textStatus, jqXHR) {
	    	$jq("#categoryTree").jstree("delete_node", item);
			// 메뉴트리 반영
	    	menuTreeDeleteNode(id);
		},
		error : function(jqXHR, textStatus, errorThrown) {
	    	alert("category delete error");
		}
	});
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
	return ("${rootCategory.categoryId}" == item.attr("id"));
};

// Action Status 변경 (추가 / 수정)
var setActionStatus = function(status) {
	$jq("#categoryForm input[name=actionType]").val(status);
};

var getActionStatus = function() {
	return $jq("#categoryForm input[name=actionType]").val();
};

var isActionStatusCreate = function() {
	return (getActionStatus() == STATUS_CREATE)
};

// 카테고리 숨김모드 변환
var statusChangeHide = function() {
	$jq("#itemNormal").hide();
	$jq("#itemEdit").hide();
};

// 카테고리 수정모드 변환
var statusChangeEdit = function() {
	$jq("#itemNormal").hide();
	$jq("#itemEdit").show();
};

// 카테고리 일반모드 변환
var statusChangeNormal = function() {
	$jq("#itemEdit").hide();
	$jq("#itemNormal").show();
};

// 아이템 Clear
var itemClear = function() {
	$jq("#categoryForm input[name=actionType]").val("");
	$jq("#categoryForm input[name=categoryId]").val("");
	$jq("#categoryForm input[name=categoryParentId]").val("");
	$jq("#categoryForm input[name=sortOrder]").val("0");

	$jq("#categoryForm input[name=categoryName]").val("");
	$jq("#categoryForm input[name=tags]").val("");

	$jq("#categoryForm input:checkbox").attr("checked", false);

	$jq("#categoryNameView").text("");
	$jq("#tagsView").text("");
};

// 아이템 Setting
var itemSetting = function(item) {
	$jq("#categoryForm input[name=categoryId]").val(item.attr("id"));
	$jq("#categoryForm input[name=categoryParentId]").val(item.attr("pid"));
	$jq("#categoryForm input[name=sortOrder]").val(item.attr("order"));

	$jq("#categoryForm input[name=categoryName]").val($jq("#categoryTree").jstree("get_text", item));
	$jq("#categoryForm input[name=tags]").val(item.attr("tags"));

	$jq("#categoryNameView").text($jq("#categoryTree").jstree("get_text", item));
	$jq("#tagsView").text(item.attr("tags"));

	var checkedStatus = false;
	if ("1" == item.attr("isBlog")) { checkedStatus = true; } else { checkedStatus = false; };
	$jq("#categoryForm input[name=isBlog]").attr("checked", checkedStatus);
	$jq("#itemNormal input[name=isBlogView]").attr("checked", checkedStatus);
	if ("1" == item.attr("isColl")) { checkedStatus = true; } else { checkedStatus = false; };
	$jq("#categoryForm input[name=isColl]").attr("checked", checkedStatus);
	$jq("#itemNormal input[name=isCollView]").attr("checked", checkedStatus);
	if ("1" == item.attr("isQna")) { checkedStatus = true; } else { checkedStatus = false; };
	$jq("#categoryForm input[name=isQna]").attr("checked", checkedStatus);
	$jq("#itemNormal input[name=isQnaView]").attr("checked", checkedStatus);
	if ("1" == item.attr("isDic")) { checkedStatus = true; } else { checkedStatus = false; };
	$jq("#categoryForm input[name=isDic]").attr("checked", checkedStatus);
	$jq("#itemNormal input[name=isDicView]").attr("checked", checkedStatus);
	if ("1" == item.attr("isManual")) { checkedStatus = true; } else { checkedStatus = false; };
	$jq("#categoryForm input[name=isManual]").attr("checked", checkedStatus);
	$jq("#itemNormal input[name=isManualView]").attr("checked", checkedStatus);
	if ("1" == item.attr("isCafe")) { checkedStatus = true; } else { checkedStatus = false; };
	$jq("#categoryForm input[name=isCafe]").attr("checked", checkedStatus);
	$jq("#itemNormal input[name=isCafeView]").attr("checked", checkedStatus);
	if ("1" == item.attr("isForum")) { checkedStatus = true; } else { checkedStatus = false; };
	$jq("#categoryForm input[name=isForum]").attr("checked", checkedStatus);
	$jq("#itemNormal input[name=isForumView]").attr("checked", checkedStatus);
	if ("1" == item.attr("isIdea")) { checkedStatus = true; } else { checkedStatus = false; };
	$jq("#categoryForm input[name=isIdea]").attr("checked", checkedStatus);
	$jq("#itemNormal input[name=isIdeaView]").attr("checked", checkedStatus);
	if ("1" == item.attr("isBbs")) { checkedStatus = true; } else { checkedStatus = false; };
	$jq("#categoryForm input[name=isBbs]").attr("checked", checkedStatus);
	$jq("#itemNormal input[name=isBbsView]").attr("checked", checkedStatus);
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

var btnSaveClickHandler = function(form) {

	if (!confirm("<ikep4j:message pre='${confirmPrefix}' key='save'/>")) {
		return;
	}

	if (isActionStatusCreate()) {
		// 신규
		$jq("#categoryTree").jstree("open_node", selectedItem);
		$jq.ajax({
			url : iKEP.getContextRoot() + "//collpack/knowledgehub//admin/createCategory.do",
			type : "post",
			data : $jq(form).serialize(),
			loadingElement : "#btnSave",
			success : function(data, textStatus, jqXHR) {
				$jq("#categoryTree").jstree("create_node", selectedItem, "last", $jq.parseJSON(data),
		    			function() {
							$jq("#categoryTree").jstree("open_node", selectedItem);
		    				statusChangeHide();
							// 메뉴트리 반영
		    				menuTreeCreateNode(selectedItem.attr("id"), $jq.parseJSON(data));
		    			}
		    	);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				var errorItems = $jq.parseJSON(jqXHR.responseText).exception;
				validator.showErrors(errorItems);
			}
		});
	} else {
		// 수정
		$jq.ajax({
			url : iKEP.getContextRoot() + "//collpack/knowledgehub//admin/updateCategory.do",
			type : "post",
			data : $jq(form).serialize(),
			loadingElement : "#btnSave",
			success : function(data, textStatus, jqXHR) {
		    	var categoryName = $jq("#categoryForm input[name=categoryName]").val();
		    	var tags = $jq("#categoryForm input[name=tags]").val();
		    	$jq("#categoryTree").jstree("rename_node", selectedItem, categoryName);
		    	selectedItem.attr("tags", tags);
				//
				var checkedStatus = "";
				if ($jq("#categoryForm input[name=isBlog]").is(":checked")) { checkedStatus = "1"; } else { checkedStatus = "0"; };
				selectedItem.attr("isBlog", checkedStatus);
				if ($jq("#categoryForm input[name=isColl]").is(":checked")) { checkedStatus = "1"; } else { checkedStatus = "0"; };
				selectedItem.attr("isColl", checkedStatus);
				if ($jq("#categoryForm input[name=isQna]").is(":checked")) { checkedStatus = "1"; } else { checkedStatus = "0"; };
				selectedItem.attr("isQna", checkedStatus);
				if ($jq("#categoryForm input[name=isDic]").is(":checked")) { checkedStatus = "1"; } else { checkedStatus = "0"; };
				selectedItem.attr("isDic", checkedStatus);
				if ($jq("#categoryForm input[name=isManual]").is(":checked")) { checkedStatus = "1"; } else { checkedStatus = "0"; };
				selectedItem.attr("isManual", checkedStatus);
				if ($jq("#categoryForm input[name=isCafe]").is(":checked")) { checkedStatus = "1"; } else { checkedStatus = "0"; };
				selectedItem.attr("isCafe", checkedStatus);
				if ($jq("#categoryForm input[name=isForum]").is(":checked")) { checkedStatus = "1"; } else { checkedStatus = "0"; };
				selectedItem.attr("isForum", checkedStatus);
				if ($jq("#categoryForm input[name=isIdea]").is(":checked")) { checkedStatus = "1"; } else { checkedStatus = "0"; };
				selectedItem.attr("isIdea", checkedStatus);
				if ($jq("#categoryForm input[name=isBbs]").is(":checked")) { checkedStatus = "1"; } else { checkedStatus = "0"; };
				selectedItem.attr("isBbs", checkedStatus);

				nodeSelectHandler(selectedItem);
				// 메뉴트리 반영
		    	menuTreeRenameNode(selectedItem.attr("id"), categoryName, tags);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				var errorItems = $jq.parseJSON(jqXHR.responseText).exception;
				validator.showErrors(errorItems);
			}
		});
	}
};

$jq(document).ready(function() {

	$jq("#categoryTree").jstree({
		core : {
			animation : 200
		},
		plugins:["themes", "ui", "json_data", "dnd", "crrm", "contextmenu"],
		ui : {
			select_limit : 1
		},
		json_data : {
			data : {data:{title:"<ikep4j:message pre="${treePrefix}" key="root.title"/>",icon:"dept"},attr:{id:"${rootCategory.categoryId}"},state:"open",
					children:${categoryJSON}
				},
			ajax : {
				url : iKEP.getContextRoot() + "//collpack/knowledgehub//admin/listChildCategorys.do",
				data : function($el) {
					return {
						"categoryParentId" : $el.attr("id")
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
					create : { label : "<ikep4j:message pre="${treePrefix}" key="menu.create"/>", action : nodeCreateHandler},
					rename : { label : "<ikep4j:message pre="${treePrefix}" key="menu.rename"/>", action : nodeRenameHandler},
					remove : { label : "<ikep4j:message pre="${treePrefix}" key="menu.remove"/>", action : nodeRemoveHandler},
					ccp : false
				};
				switch($item.attr("id")) {
					case "${rootCategory.categoryId}" : $jq.extend(menuItem, {rename:false, remove:false});
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

		// Root Category 로 이동할수 없다.
		if ("categoryTree" == $jq(np).attr("id")) {
			$jq.jstree.rollback(data.rlbk);
			alert("<ikep4j:message pre='${treePrefix}' key='alert.doNotMoveToRoot'/>");
			return;
		}

		var sourceText = $jq("#categoryTree").jstree("get_text", o);
		if (!confirm(sourceText + "<ikep4j:message pre='${treePrefix}' key='confirm.move'/>")) {
			$jq.jstree.rollback(data.rlbk);
			return;
		}

		var sourceId = o.attr("id");
		var sourceParentId = op.attr("id");
		var sourceSortOrder = o.attr("order");
		var targetParentId = np.attr("id");
		var targetSortOrder = cp + 1;

		var params = "";
		params += "sourceId=" + sourceId;
		params += "&sourceParentId=" + sourceParentId;
		params += "&sourceSortOrder=" + sourceSortOrder;
		params += "&targetParentId=" + targetParentId;
		params += "&targetSortOrder=" + targetSortOrder;

		$jq.ajax({
			url : iKEP.getContextRoot() + "//collpack/knowledgehub//admin/moveCategory.do",
			type : "post",
			data : params,
			success : function(data, textStatus, jqXHR) {
				// 노드들의 속성값 변경
				if (sourceParentId == targetParentId) { // 같은 부모 노드에서 변경된 경우
					if (sourceSortOrder > targetSortOrder) { // 노드가 위로 이동한 경우
						o.nextAll().each(function() {
							if (sourceSortOrder > $jq(this).attr("order")) {
		    					$jq(this).attr("order", parseInt($jq(this).attr("order")) + 1);
			    			}
			    		});

			    		o.attr("order", targetSortOrder);
			    	} else { // 노드가 아래로 이동한 경우
			    		if (1 == (targetSortOrder - sourceSortOrder)) {
			    			// 변화 없다.
			    			return;
			    		}
			    		
						o.prevAll().each(function() {
							if (sourceSortOrder < $jq(this).attr("order")) {
		    					$jq(this).attr("order", parseInt($jq(this).attr("order")) - 1);
			    			}
			    		});

			    		o.attr("order", parseInt(targetSortOrder) - 1);
			    	}
		    	} else { // 다른 부모 노드로 이동된 경우
		    		$jq(op.children()[2]).children().each(function() {
		    			var order = $jq(this).attr("order");
		    			if (sourceSortOrder < order) {
		    				$jq(this).attr("order", parseInt(order) - 1);
		    			}
		    		});
		    		
		    		$jq(np.children()[2]).children().each(function() {
		    			var order = $jq(this).attr("order");
		    			if (targetSortOrder <= order) {
		    				$jq(this).attr("order", parseInt(order) + 1);
		    			}
		    		});
		    		
		    		o.attr("order", targetSortOrder);
		    		o.attr("pid", targetParentId);
		    	}
			},
			error : function(jqXHR, textStatus, errorThrown) {
		    	$jq.jstree.rollback(data.rlbk);
		    	alert("category move error");
			}
		});
	})
	;
});
//]]>
</script>

<script type="text/javascript">
//<![CDATA[
var validOptions = {
		rules : {
			categoryName : {
				required : true,
				maxlength : 100
			},
			tags : {
				required : true,
				tagCount : 10,
				tagDuplicate : []
			}
		},
		messages : {
			categoryName : {
				direction : "top",
				required : "<ikep4j:message pre='${validationMessagePrefix}' key='NotEmpty.KMCategory.categoryName'/>",
				maxlength : "<ikep4j:message pre='${validationMessagePrefix}' key='Size.KMCategory.categoryName'/>"
			},
			tags : {
				direction : "top",
				required : "<ikep4j:message pre='${validationMessagePrefix}' key='NotEmpty.KMCategory.tags'/>",
				tagCount : "<ikep4j:message pre='${validationMessagePrefix}' key='MaxCount.KMCategory.tags'/>",
				tagDuplicate : "<ikep4j:message pre='${validationMessagePrefix}' key='Duplication.KMCategory.tags'/>"
			}
	    },
	    submitHandler : btnSaveClickHandler
	};

$jq(document).ready(function() {
	validator = new iKEP.Validator("#categoryForm", validOptions);
});
//]]>
</script>
