<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.board.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="message.collpack.collaboration.board.boardItem.listBoardView.list" />
<c:set var="preCode"    value="message.collpack.collaboration.common.code" />
<c:set var="preButton"  value="message.collpack.collaboration.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.boardItem" />
<c:set var="preSearch"  value="message.collpack.collaboration.common.searchCondition" /> 
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
		
		$("#divTab1").tabs();
		$("#divTab2").tabs();
		$("#divTab3").tabs();
		$("#divTab4").tabs();
	   	$("#divTab_box1").tabs();
		$("#divTab_s").tabs();
		$("#divTab5").tabs();
		$("#divTab6").tabs();
		$("#divTab7").tabs();
		
		$("#boardTree").bind("loaded.jstree", function (event, datet) {
			$(this).jstree("hide_icons");
		}).jstree({
		    plugins:["themes", "ui", "json_data"],  
		    "json_data" : {"data" :  ${boardTreeJson1}} 	    
		}); 
				
		/* 노드 클릭시 이벤트*/
	    $("#boardTree").delegate("a", "click", function () {
	    	var targetBoardId = $(this).parent().attr("boardId");

	    	if("${orgBoardId}" == targetBoardId) {
	    		alert('<ikep4j:message pre="${preMessage}" key="sameBoardId" />');
	    		return false;
	    	}
	    	if($(this).parent().attr("boardType") == "0") {
		    	if(confirm($(this).parent().attr("boardName") +"<ikep4j:message pre="${preMessage}" key="moveBoardItem" />")){
		    		dialogWindow.callback({orgBoardId:'${orgBoardId}', targetBoardId:$(this).parent().attr("boardId"), isKnowhow:1});
		    		dialogWindow.close();
		    	}
	    	}
	     }); 
		
		$("#boardTree1").bind("loaded.jstree", function (event, datet) {
			$(this).jstree("hide_icons");
		}).jstree({
		    plugins:["themes", "ui", "json_data"],  
		    "json_data" : {"data" :  ${boardTreeJson}} 	    
		}); 
				
		/* 노드 클릭시 이벤트*/
	    $("#boardTree1").delegate("a", "click", function () {
	    	var targetBoardId = $(this).parent().attr("boardId");

	    	if("${orgBoardId}" == targetBoardId) {
	    		alert('<ikep4j:message pre="${preMessage}" key="sameBoardId" />');
	    		return false;
	    	}
	    	if($(this).parent().attr("boardType") == "0") {
		    	if(confirm($(this).parent().attr("boardName") +"<ikep4j:message pre="${preMessage}" key="moveBoardItem" />")){
		    		dialogWindow.callback({orgBoardId:'${orgBoardId}', targetBoardId:$(this).parent().attr("boardId"), isKnowhow:0});
		    		dialogWindow.close();
		    	}
	    	}
	     }); 
		
	    $("#boardTree2").bind("loaded.jstree", function (event, datet) {
			$(this).jstree("hide_icons");
		}).jstree({
		    plugins:["themes", "ui", "json_data"],  
		    "json_data" : {"data" :  ${boardTreeJson2}} 	    
		}); 
				
		/* 노드 클릭시 이벤트*/
	    $("#boardTree2").delegate("a", "click", function () {
	    	var targetBoardId = $(this).parent().attr("boardId");

	    	if("${orgBoardId}" == targetBoardId) {
	    		alert('<ikep4j:message pre="${preMessage}" key="sameBoardId" />');
	    		return false;
	    	}
	    	if($(this).parent().attr("boardType") == "0") {
		    	if(confirm($(this).parent().attr("boardName") +"<ikep4j:message pre="${preMessage}" key="moveBoardItem" />")){
		    		dialogWindow.callback({orgBoardId:'${orgBoardId}', targetBoardId:$(this).parent().attr("boardId"), isKnowhow:3});
		    		dialogWindow.close();
		    	}
	    	}
	     }); 
	});	 
})(jQuery); 
//-->
</script> 
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1>	

<!--pageTitle Start-->

<%--div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.3depth" /></li>
		</ul>
	</div>
</div--%>
<!--//pageTitle End-->		


    	<!--tab Start-->		
        <div id="divTab_box1" class="iKEP_tab_s">
            <ul>
                <li><a href="#tabs-1">일반정보</a></li>
                <li><a href="#tabs-2">업무노하우</a></li>
                <li><a href="#tabs-3">원문 게시판</a></li>
            </ul>
            <div class="tab_conbox">
                <div id="tabs-1">
					<!-- div class="leftTree treeBox"  --> 
						<div id="boardTree"></div> 
					<!-- /div -->                 	
                </div>
                <div id="tabs-2">
                	<div id="boardTree1"></div>
                </div>
                <div id="tabs-3">
                	<div id="boardTree2"></div>
                </div>
            </div>				
        </div>		
        <!--//tab End-->

<!--  
<div class="leftTree treeBox"> 
	<div><img src="<c:url value='/base/images/common/img_title_cate.gif'/>" alt="category" /></div> 
	<div id="boardTree"></div> 
</div> 
-->
