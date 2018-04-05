<%--
=====================================================
* 기능 설명 : 양식 카테고리
* 작성자    : wonchu
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.approval.apprForm.header" /> 
<c:set var="preList"    value="ui.approval.apprForm.list" />
<c:set var="preButton"  value="ui.approval.common.button" />
<c:set var="preSearch"  value="ui.approval.common.search" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript">
	<!--
	
	(function($){
	    
		var dialogWindow; 
		
		//- onload시 수행할 코드
		$(document).ready(function(){

    		//- category tree 생성
			createTree();
			
			//- caller
			fnCaller = function (params, dialog) {
				dialogWindow = dialog;
			};
		});
		
		//- createTree
		createTree = function () {
		    $("#categoryTreeDiv").bind("loaded.jstree", function (event, datet) {
    			$(this).jstree("hide_icons");
    			$(this).jstree("open_all");
    		}).jstree({
    		    plugins     : ["themes", "ui", "json_data"],  
    		    ui			: { select_limit : 1},
    		    json_data   : {"data" :  ${categoryTreeJson}}
    		}).bind("select_node.jstree", function (event, data) {
    		    if($(data.rslt.obj).attr("codeid")==$("#categoryForm input[name=formParentId]").val()) return;
    		    
    		    dialogWindow.callback({"codeId" : $(data.rslt.obj).attr("codeid"), "codeName" : $(data.rslt.obj).attr("codeName")});
                dialogWindow.close();
    		});
		}
	})(jQuery);
	
	
	//-->
</script>

<h1 class="none">contnet area</h1>
<div id="guideConFrame">
    <div class="leftTree treeBox">	
		<div><img src="/ikep4-webapp/base/images/common/img_title_cate.gif" alt="category" /></div>
		<div id="categoryTreeDiv"></div>
	</div>
	<form id="categoryForm">
	    <input type="hidden" name="formParentId" value="${formParentId}" />
    </form>
</div>