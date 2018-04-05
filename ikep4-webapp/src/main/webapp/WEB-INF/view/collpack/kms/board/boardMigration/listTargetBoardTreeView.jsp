<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="preHeader"  value="message.collpack.collaboration.board.boardAdmin.listBoardView.header" /> 
<c:set var="preDetail"  value="message.collpack.collaboration.board.boardAdmin.listBoardView.detail" />
<c:set var="preCode"    value="message.collpack.collaboration.common.code" />
<c:set var="preButton"  value="message.collpack.collaboration.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.board" />


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--타이틀-->

<title></title>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/theme01/jquery_ui_custom.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/theme01/theme.css"/>" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-ui-1.8.13.min.without.datepicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.ui.datepicker.customize.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/plugins.pack.js"/>"></script>
<script type="text/javascript">var contextRoot = "${pageContext.request.contextPath}";</script>
<script type="text/javascript" src="<c:url value="/base/js/etc.plugins.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/langResource/${empty user.localeCode ? 'en' : user.localeCode}.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/common.pack.js"/>"></script>
 
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>


</head>


<script type="text/javascript">
<!-- 
(function($){	 
	$(document).ready(function() {  

					
		$("#boardTree").bind("loaded.jstree", function (event, datet) {
			$(this).jstree("hide_icons");
		}).jstree({
		    plugins:["themes", "ui", "json_data"],  
		    
		    "json_data" : {
		    	"data" : {
		    		"data" : "<ikep4j:message pre='${preDetail}' key='topCategory'/>", 
		    		"attr" : {"dummy" : "true", "boardRootId":"0","boardId" : "","workspaceId":"${workspaceId}"}, 
		    		"state" : "open",  
		    		children :  ${boardTreeJson}
		    	}
		    } 	    
		}); 
		/* 노드 클릭시 이벤트*/
	    $("#boardTree").delegate("a", "click", function () {
	    	var targetBoardId = $(this).parent().attr("boardId");
			var boardname		= $(this).parent().attr("boardName");
	    	//if(targetBoardId==null || targetBoardId=="")
	    	//	targetBoardId=$(this).parent().attr("boardRootId");
	    	if(boardname==null || boardname=="")
	    		boardname="<ikep4j:message pre='${preDetail}' key='topCategory'/>";

	    	if(confirm(boardname +" - <ikep4j:message pre="${preMessage}" key="moveBoard" />")) {
				//parent.parent.moveBoard('${workspaceId}','${boardId}','${targetWorkspaceId}',targetBoardId);
				parent.applyMigration('${workspaceId}','${boardId}','${targetWorkspaceId}',targetBoardId);
	    	}
	     }); 		
	}); 	 
})(jQuery); 
//-->
</script>

<div id="boardTree"></div>


