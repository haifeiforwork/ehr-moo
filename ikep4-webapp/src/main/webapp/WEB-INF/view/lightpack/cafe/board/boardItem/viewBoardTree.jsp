<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.lightpack.cafe.board.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="message.lightpack.cafe.board.boardItem.listBoardView.list" />
<c:set var="preCode"    value="message.lightpack.cafe.common.code" />
<c:set var="preButton"  value="message.lightpack.cafe.common.button" /> 
<c:set var="preMessage" value="message.lightpack.cafe.common.boardItem" />
<c:set var="preSearch"  value="message.lightpack.cafe.common.searchCondition" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript">
<!-- 
var dialogWindow;
function fnCaller(params, dialog) {
	dialogWindow = dialog;
}

(function($){	 
	$(document).ready(function() {  
		$("#boardTree").bind("loaded.jstree", function (event, datet) {
			$(this).jstree("hide_icons");
		}).jstree({
		    plugins:["themes", "ui", "json_data"],  
		    "json_data" : {"data" :  ${boardTreeJson}} 	    
		}); 
				
		/* 노드 클릭시 이벤트*/
	    $("#boardTree").delegate("a", "click", function () {
	    	var targetBoardId = $(this).parent().attr("boardId");

	    	if('${orgBoardId}' == targetBoardId) {
	    		alert('<ikep4j:message pre="${preMessage}" key="sameBoardId" />')
	    		return false;
	    	}
	    	
	    	if(confirm($(this).parent().attr("boardName") +"<ikep4j:message pre="${preMessage}" key="moveBoardItem" />")) {
				dialogWindow.callback({orgBoardId:'${orgBoardId}', targetBoardId:$(this).parent().attr("boardId")});
	    		dialogWindow.close();
	    	}
	     }); 
	});	 
})(jQuery); 
//-->
</script> 
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1>	


<div class="leftTree treeBox"> 
	<div><img src="<c:url value='/base/images/common/img_title_cate.gif'/>" alt="category" /></div> 
	<div id="boardTree"></div> 
</div> 

