<%--
=====================================================
	* 기능설명	:	카테고리 관리 목록
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.lightpack.cafe.category.listCategory.header" />
<c:set var="preSearch"  value="message.lightpack.cafe.category.listCategory.search" />
<c:set var="preTree"    value="message.lightpack.cafe.category.listCategory.tree" />
<c:set var="preList"    value="message.lightpack.cafe.category.listCategory.list" />
<c:set var="preButton"  value="message.lightpack.cafe.category.listCategory.button" />
<c:set var="preScript"  value="message.lightpack.cafe.category.listCategory.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript">
<!-- 
var dialogWindow;
function fnCaller(param, dialog) {
	dialogWindow = dialog;
}

(function($){	 
	$(document).ready(function() {  
		$("#adminCategoryTree").bind("loaded.jstree", function (event, datet) {
			$(this).jstree("hide_icons");
		}).jstree({
		    plugins:["themes", "ui", "json_data"],  
		    
		    "json_data" : {"data" :  ${categoryTreeJson}} 	    
		}); 
				
		/* 노드 클릭시 이벤트*/
	    $("#adminCategoryTree").delegate("a", "click", function () {

	    	//if(confirm($(this).parent().attr("categoryName") +"로 설정하시겠습니까?"))
	    	//{
	    		var id = $(this).parent().attr("categoryId");
	    		var name = $(this).parent().attr("categoryName");
	    		//parent.selectCategory(id,name);
	    		dialogWindow.callback({id:id, name:name});
	    		dialogWindow.close();
	    	//}
	     }); 
	});	 
})(jQuery); 
//-->
</script> 

<h1 class="none"></h1>	
<div class="leftTree treeBox"> 
	<div><img src="<c:url value='/base/images/common/img_title_cate.gif'/>" alt="category" /></div> 
	<div id="adminCategoryTree" style="height:160px; overflow:auto;"></div> 
</div> 
