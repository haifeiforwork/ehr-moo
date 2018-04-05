<%--
=====================================================
* 기능 설명 : 다이나믹 타일즈2 Left Page
* 작성자 : 이동희
=====================================================
--%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ikep4j" uri="/WEB-INF/tld/ikep4j.tld"%>

<c:set var="commonPrefix">ui.collpack.expertnetwork.common</c:set>
<c:set var="buttonPrefix">ui.collpack.expertnetwork.common.button</c:set>
<c:set var="menuPrefix">ui.collpack.expertnetwork.subMenu.menu</c:set>

<h2><a href="<c:url value='/collpack/expertnetwork/expertNetworkMainView.do'/>"><ikep4j:message pre="${menuPrefix}" key="menuTitle"/></a></h2>
<div class="left_fixed">
	<ul>
		<li class="opened liFirst"><a href="#a"><ikep4j:message pre="${menuPrefix}" key="category"/></a>
			<div class="boxList_child">
				<div class="leftTree">
					<div><img src="<c:url value='/base/images/common/img_title_cate.gif'/>" alt="category" /></div>
					<div id="categoryTreeMenu"></div>
				</div>
			</div>
		</li>
	<c:if test="${expertAdmin}">
		<li class="opened liLast"><a href="#a"><ikep4j:message pre="${menuPrefix}" key="administrator"/></a>
			<ul>
				<li class="no_child liFirst"><a href="#a" id="menuCategoryManagement"><ikep4j:message pre="${menuPrefix}" key="administrator.categoryManagement"/></a></li>
				<li class="no_child liLast"><a href="#a" id="menuInterestExpertPolicyManagement"><ikep4j:message pre="${menuPrefix}" key="administrator.interestExpertPolicyManagement"/></a></li>
			</ul>
		</li>
	</c:if>
	</ul>
</div>

<script type="text/javascript">
//<![CDATA[
$jq(document).ready(function() {
	// left menu setting
	iKEP.setLeftMenu();
});
//]]>
</script>

<script type="text/javascript">
//<![CDATA[
$jq(document).ready(function() {

	// Tree setting
	$jq("#categoryTreeMenu").jstree({
		core : {
			animation : 200
		},
		plugins:["themes", "ui", "json_data"],
		ui : {
			select_limit:1,
		},
		json_data : {
			data : ${menuRootCategory},
			ajax : {
				url : iKEP.getContextRoot() + "/collpack/expertnetwork/listChildMenuCategorys.do",
				data : function($el) {
					return {
						"categoryParentId" : $el.attr("id")
					};
				},
				success : function(data) {
					return data.items;
				}
			}
		}
	})
	.bind("select_node.jstree", function(data,event){
		unbindHandler();
		saveWindowId = 1;
		searchAreaShow();

		var node = event.rslt.obj;

		$jq.ajax({
			url : iKEP.getContextRoot() + "/collpack/expertnetwork/category/expertListViewAjax.do",
			type : "get",
			data : "categoryId=" + node.attr("id") + "&categoryName=" + $jq("#categoryTreeMenu").jstree("get_text", node),
			loadingElement : {container : "#workContents"},
			success : function(data, textStatus, jqXHR) {
				$jq("#workContents").empty();
				$jq("#workContents").html(data);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("Expert List Load Fail!!!");
			}
		});
	})
	;

	$jq("#menuCategoryManagement").bind("click", function() {
		unbindHandler();
		saveWindowId = 2;
		searchAreaHide();

		$jq.ajax({
			url : iKEP.getContextRoot() + "/collpack/expertnetwork/admin/categoryManagementViewAjax.do",
			type : "get",
			data : "",
			loadingElement : {container : "#workContents"},
			success : function(data, textStatus, jqXHR) {
				$jq("#workContents").empty();
				$jq("#workContents").html(data);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("CategoryManagement Load Fail!!!");
			}
		});
	});

	$jq("#menuInterestExpertPolicyManagement").bind("click", function() {
		unbindHandler();
		saveWindowId = 3;
		searchAreaHide();

		$jq.ajax({
			url : iKEP.getContextRoot() + "/collpack/expertnetwork/admin/interestExpertPolicyManagementViewAjax.do",
			type : "get",
			data : "",
			loadingElement : {container : "#workContents"},
			success : function(data, textStatus, jqXHR) {
				$jq("#workContents").empty();
				$jq("#workContents").html(data);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("InterestExpertPolicyManagement Load Fail!!!");
			}
		});
	});

});
//]]>
</script>


<script type="text/javascript">
//<![CDATA[
var saveWindowId = 0;

function unbindHandler() {
	switch (saveWindowId) {
		case 0 :
			// expertNetworkMain.jsp
			break;

		case 1 :
			// expertPage.jsp
			//if (typeof pageReflash != 'undefined') { $jq("#countOfPage").unbind("change", pageReflash); }
			break;

		case 2 :
			// categoryManagement.jsp
			//if (typeof btnCancelClickHandler != 'undefined') { $jq("#btnCancel").unbind("click", btnCancelClickHandler); }
			//if (typeof btnModifyClickHandler != 'undefined') { $jq("#btnModify").unbind("click", btnModifyClickHandler); }
			//if (typeof btnSaveClickHandler != 'undefined') { $jq("#btnSave").unbind("click", btnSaveClickHandler); }

			$jq("#categoryTree").jstree("destroy");
			$jq("#categoryTree").empty();
			break;

		case 3 :
			break;

		default :
	}
	// Dialog Destory and remove
	$jq("#expertAppendDialog").dialog("destroy").remove();
};

function searchAreaShow() {
	$jq("#pageTitle_main").show();
};

function searchAreaHide() {
	$jq("#pageTitle_main").hide();
};

//]]>
</script>

<script type="text/javascript">
//<![CDATA[

var menuTreeCreateNode = function(pid, data) {
	return;
	var obj = $jq("#categoryTreeMenu li[id=" + pid + "]");
	var status = $jq("#categoryTreeMenu").jstree("is_loaded", obj);
	iKEP.debug(obj);
	iKEP.debug(status);
	if (status) {
		$jq("#categoryTreeMenu").jstree("create_node", obj, "last", data, function() {});
	}
};

var menuTreeRenameNode = function(id, name) {
	var obj = $jq("#categoryTreeMenu li[id=" + id + "]");
	$jq("#categoryTreeMenu").jstree("rename_node", obj, name);
};

var menuTreeDeleteNode = function(id) {
	var obj = $jq("#categoryTreeMenu li[id=" + id + "]");

	$jq("#categoryTreeMenu").jstree("delete_node", obj);
};

//]]>
</script>
