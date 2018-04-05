<%--
=====================================================
* 기능 설명 : 다이나믹 타일즈2 Left Page
* 작성자 : 이동희
=====================================================
--%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ikep4j" uri="/WEB-INF/tld/ikep4j.tld"%>

<c:set var="menuPrefix">ui.collpack.knowledgehub.subMenu.menu</c:set>

<h2><a href="<c:url value='/collpack/knowledgehub/knowledgeMapMainView.do'/>"><ikep4j:message pre="${menuPrefix}" key="menuTitle"/></a></h2>
<div class="left_fixed">
	<ul>
		<li class="opened liFirst"><a href="#a"><ikep4j:message pre="${menuPrefix}" key="personal"/></a>
			<ul>
				<li class="no_child liFirst"><a href="#a" id="menuPopularityKnowledge"><ikep4j:message pre="${menuPrefix}" key="personal.popularityKnowledge"/></a></li>
				<li class="no_child liLast"><a href="#a" id="menuRecommendKnowledge"><ikep4j:message pre="${menuPrefix}" key="personal.recommendKnowledge"/></a></li>
			</ul>
		</li>
		<li class="opened <c:if test="${!knowledgeAdmin}">liLast</c:if>"><a href="#a"><ikep4j:message pre="${menuPrefix}" key="category"/></a>
			<div class="boxList_child">
				<div class="leftTree">
					<div><img src="<c:url value='/base/images/common/img_title_cate.gif'/>" alt="category" /></div>
					<div id="categoryTreeMenu"></div>
				</div>
			</div>
		</li>
	<c:if test="${knowledgeAdmin}">
		<li class="opened liLast"><a href="#a"><ikep4j:message pre="${menuPrefix}" key="administrator"/></a>
			<ul>
				<li class="no_child liFirst"><a href="#a" id="menuCategoryManagement"><ikep4j:message pre="${menuPrefix}" key="administrator.categoryManagement"/></a></li>
				<!--<li class="no_child liLast"><a href="#a" id="menuPopularityKnowledgePolicyManagement"><ikep4j:message pre="${menuPrefix}" key="administrator.popularityKnowledgePolicyManagement"/></a></li>  -->
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
				url : iKEP.getContextRoot() + "/collpack/knowledgehub/listChildMenuCategorys.do",
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
		saveWindowId = 3;

		var node = event.rslt.obj;

		$jq.ajax({
			url : iKEP.getContextRoot() + "/collpack/knowledgehub/category/knowledgeListViewAjax.do",
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

	$jq("#menuPopularityKnowledge").bind("click", function() {
		unbindHandler();
		saveWindowId = 1;

		$jq.ajax({
			url : iKEP.getContextRoot() + "/collpack/knowledgehub/personal/knowledgePopularListViewAjax.do",
			type : "get",
			data : "",
			loadingElement : {container : "#workContents"},
			success : function(data, textStatus, jqXHR) {
				$jq("#workContents").empty();
				$jq("#workContents").html(data);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("Knowledge Popular List Load Fail!!!");
			}
		});
	});

	$jq("#menuRecommendKnowledge").bind("click", function() {
		unbindHandler();
		saveWindowId = 2;

		$jq.ajax({
			url : iKEP.getContextRoot() + "/collpack/knowledgehub/personal/knowledgeRecommendListViewAjax.do",
			type : "get",
			data : "",
			loadingElement : {container : "#workContents"},
			success : function(data, textStatus, jqXHR) {
				$jq("#workContents").empty();
				$jq("#workContents").html(data);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("Knowledge Popular List Load Fail!!!");
			}
		});
	});

	$jq("#menuCategoryManagement").bind("click", function() {
		unbindHandler();
		saveWindowId = 4;

		$jq.ajax({
			url : iKEP.getContextRoot() + "/collpack/knowledgehub/admin/categoryManagementViewAjax.do",
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

	$jq("#menuPopularityKnowledgePolicyManagement").bind("click", function() {
		unbindHandler();
		saveWindowId = 5;

		$jq.ajax({
			url : iKEP.getContextRoot() + "/collpack/knowledgehub/admin/popularityKnowledgePolicyManagementViewAjax.do",
			type : "get",
			data : "",
			loadingElement : {container : "#workContents"},
			success : function(data, textStatus, jqXHR) {
				$jq("#workContents").empty();
				$jq("#workContents").html(data);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("PopularityKnowledgePolicyManagement Load Fail!!!");
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
		case 4 :
			$jq("#categoryTree").jstree("destroy");
			$jq("#categoryTree").empty();
			break;

		default :
	}
};

//]]>
</script>

<script type="text/javascript">
//<![CDATA[

var menuTreeCreateNode = function(pid, data) {
	return;
	var obj = $jq("#categoryTreeMenu li[id=" + pid + "]");
	$jq("#categoryTreeMenu").jstree("create_node", obj, "last", data, function() {});
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
