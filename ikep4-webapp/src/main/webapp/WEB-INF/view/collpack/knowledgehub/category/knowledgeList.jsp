<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<div id="tagResult">

<!--pageTitle Start-->
<div id="pageTitle">
	<h2>${categoryName}</h2>
</div>
<!--//pageTitle End-->

<!--blockbox Start-->
<div class="Box_type_01">
	<div class="open_view textLeft"><a href="#a" class="selected">${categoryName}</a></div>
	<c:forEach var="item" items="${categoryList}">
	<div class="open_view textLeft"><a href="#a" onclick="subCategoryClick('${item.categoryId}', '${item.categoryName}');">${item.categoryName}</a></div>
	</c:forEach>
	<div class="clear"></div>
</div>
<!--//blockbox End-->

<div id="knowledgePage">
	<%@ include file="/WEB-INF/view//collpack/knowledgehub//common/knowledgePageList.jsp"%>
</div>

</div>


<!-- Knowledge List 페이징-->
<script type="text/javascript">
//<![CDATA[

// 카테고리 클릭
var subCategoryClick = function(categoryId, categoryName) {
	$jq.ajax({
		url : iKEP.getContextRoot() + "//collpack/knowledgehub//category/knowledgeListViewAjax.do",
		type : "get",
		data : "categoryId=" + categoryId + "&categoryName=" + categoryName,
		loadingElement : {container : "#workContents"},
		success : function(data, textStatus, jqXHR) {
			$jq("#workContents").empty();
			$jq("#workContents").html(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("Knowledge List Load Fail!!!");
		}
	});
};


// 보기유형
var viewSubmit = function(view) {
	$jq("#searchForm input[name=viewType]").val(view);
	pageReflash();
};

// 페이지
var pageSubmit = function(page) {
	$jq("#searchForm input[name=requestPage]").val(page);

	$jq.ajax({
		url : iKEP.getContextRoot() + "//collpack/knowledgehub//category/knowledgePage.do",
		type : "get",
		data : $jq("#searchForm").serialize(),
		loadingElement : {container : "#knowledgePage"},
		success : function(data, textStatus, jqXHR) {
			$jq("#knowledgePage").empty();
			$jq("#knowledgePage").html(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("Knowledge Page Load Fail!!!");
		}
	});
};

//페이지 Reflash
var pageReflash = function() {
	var _page = $jq("#searchForm input[name=page]").val();
	pageSubmit(_page);
};

//페이지 Reflash (DB값을 다시 읽고 처리한다)
var pageReflashDB = function() {
	$jq("#searchForm input[name=reInit]").val(true);
	pageReflash();
};
//]]>
</script>
