<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="preHeader"  value="message.collpack.collaboration.board.boardMigration.listBoardTreeView.header" /> 
<c:set var="preDetail"  value="message.collpack.collaboration.board.boardMigration.listBoardTreeView.detail" />
<c:set var="preCode"    value="message.collpack.collaboration.common.code" />
<c:set var="preButton"  value="message.collpack.collaboration.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.board" />   

<script type="text/javascript">
<!-- 
(function($){	 
	moveBoard = function(result) {
		var orgWorkspaceId = result.orgWorkspaceId,
			orgBoardId = result.orgBoardId,
			targetWorkspaceId = result.targetWorkspaceId,
			targetBoardId = result.targetBoardId;
		
		var itemIds = new Array();
		
		$.post("<c:url value='/collpack/collaboration/board/boardMigration/moveBoard.do'/>", {"orgWorkspaceId":orgWorkspaceId,"orgBoardId": orgBoardId,"targetWorkspaceId":targetWorkspaceId,"targetBoardId":targetBoardId}) 
		.success(function(data) {
			location.href = "<c:url value='/collpack/collaboration/board/boardMigration/listBoardTreeView.do'/>?workspaceId=${workspaceId}&boardRootId=0";	 
		})
	};
	
	$(document).ready(function() {  
		
		$("#adminBoardTree").bind("loaded.jstree", function (event, datet) {
			$(this).jstree("hide_icons");
		}).jstree({
		    plugins:["themes", "ui", "json_data", "contextmenu"],  
		    
		    "json_data" : {
		    	"data" : {"data" : "<ikep4j:message pre='${preDetail}' key='topCategory'/>", "attr" : {"dummy" : "true", "boardRootId":"0","boardId" : "","workspaceId":"${workspaceId}"}, "state" : "open",  children : ${boardTreeJson}}
		    },  
		    	
		    	
			"contextmenu" : {  
				items : function($item){
					var menuItem = {
						"boardMigration" : { label : "<ikep4j:message pre='${preDetail}' key='boardMigration'/>",  action : function(item) { 
						    //var workspaceId   = $(node).attr("workspaceId");
						  
						   var boardId       = $(item).attr("boardId");

							iKEP.showDialog({
								title: "<ikep4j:message pre="${preHeader}" key="pageTitleTarget" />",
								url: "<c:url value='/collpack/collaboration/board/boardMigration/listWorkspaceManagerView.do' />?workspaceId=${workspaceId}&boardId="+boardId,
								modal: true,
								width: 800,
								height: 500,
								callback : moveBoard
							});
							return false; 
						}}, ccp : false, create : false
					};
					 
					var boardId = $item.attr("boardid");
					return menuItem;
				}
			}	    
		}); 
				

	}); 	 
})(jQuery); 
//-->
</script>
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
<!--pageTitle Start-->
<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
</div> 
<!--//pageTitle End-->		
<div class="corner_RoundBox01" style="margin-bottom:20px;">
	<ikep4j:message pre="${preHeader}" key="pageMessage" />
	<div class="l_t_corner"></div>
	<div class="r_t_corner"></div>
	<div class="l_b_corner"></div>
	<div class="r_b_corner"></div>					
</div>	
<!--blockLeft Start-->
<div class="leftTree treeBox">
	<!-- div><img src="<c:url value='/base/images/common/img_title_cate.gif'/>" alt="category" /></div> -->
	<div id="adminBoardTree"></div>
</div>
<!--//blockLeft End-->

<!--blockRight Start-->
<div id="boardDetail" class="blockRight" style="width:0%;">
</div>
<div style="height:500px;">&nbsp;</div>
<!--//blockLight End-->	 			
</div>