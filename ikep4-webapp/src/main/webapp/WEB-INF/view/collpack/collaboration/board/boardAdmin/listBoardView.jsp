<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="preHeader"  value="message.collpack.collaboration.board.boardAdmin.listBoardView.header" /> 
<c:set var="preDetail"  value="message.collpack.collaboration.board.boardAdmin.listBoardView.detail" />
<c:set var="preCode"    value="message.collpack.collaboration.common.code" />
<c:set var="preButton"  value="message.collpack.collaboration.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.boardAdmin" />
 
<script type="text/javascript">
<!-- 
(function($){	 
	$(document).ready(function() { 
		window.parent.topScroll(); 
		
		$("#adminBoardAdminTree").bind("loaded.jstree", function (event, datet) {
			$(this).jstree("hide_icons");
			
			 $("#adminBoardAdminTree").jstree("open_node","#root");
			 
			 window.parent.resizeIframe();
		}).jstree({
		    plugins:["themes", "ui", "dnd", "crrm", "json_data", "contextmenu"],   
		    "json_data" : {  
		    	 "data" : [{  
		    		        "data" : "Category", 
		    				"state" : "closed",
		    				"attr" : {"id" : "root", "dummy" : true, "boardId" : "", "boardRootId" : "1","workspaceId":"${workspaceId}"}
		    			  }], 
		    	"ajax" : {
		    		"url" : "<c:url value='/collpack/collaboration/board/boardAdmin/listChildrenBoard.do'/>",
		    		"data" : function (node) {
		    			//iKEP.debug(node)
		    			return { boardId :  $(node).attr("boardId") ?  $(node).attr("boardId") : "",workspaceId:"${workspaceId}"};
		    			iKEP.iFrameContentResize();
		    		}
		    	}
		    },   
		    "contextmenu" : {  
				items : function($item){
					var menuItem = {
						"1-createBoard" : { label : "<ikep4j:message pre='${preDetail}' key='createBoard'/>",  action : function(item) { 
							location.href = "<c:url value='/collpack/collaboration/board/boardAdmin/createBoardView.do?boardRootId=0'/>" + 
							"&workspaceId=" + $(item).attr("workspaceId")+"&boardParentId=" + $(item).attr("boardId") +
							"&boardType=0"; 
							
						}},
						"2-createCategory" : { label : "<ikep4j:message pre='${preDetail}' key='createCategory'/>",  action : function(item) { 
							location.href = "<c:url value='/collpack/collaboration/board/boardAdmin/createBoardView.do?boardRootId=0'/>" + 
							"&workspaceId=" + $(item).attr("workspaceId")+"&boardParentId=" + $(item).attr("boardId") +
							"&boardType=1";
						}},						
						"3-createLink" : { label : "<ikep4j:message pre='${preDetail}' key='createLink'/>",  action : function(item) { 
							location.href = "<c:url value='/collpack/collaboration/board/boardAdmin/createBoardView.do?boardRootId=0'/>" + 
							"&workspaceId=" + $(item).attr("workspaceId")+"&boardParentId=" + $(item).attr("boardId") +
							"&boardType=2";
						}},

						"4-update" : { label : "<ikep4j:message pre='${preDetail}' key='update'/>",  action : function(item) { 
							location.href = "<c:url value='/collpack/collaboration/board/boardAdmin/updateBoardView.do?boardId='/>" + $(item).attr("boardId") +
							"&workspaceId=" + $(item).attr("workspaceId")+"&boardParentId=" + $(item).attr("boardParentId") +
							"&boardType=0";
						}},
						"5-rename" : { label : "<ikep4j:message pre='${preDetail}' key='rename'/>",  action : function(item) {  
							this.rename(item); 
						}},
						"6-remove" : { label : "<ikep4j:message pre='${preDetail}' key='delete'/>",  action : function(item) {   
							var tree = this;  
							 
							if(confirm("<ikep4j:message pre='${preMessage}' key='delete'/>")) {
								$.get("<c:url value='/collpack/collaboration/board/boardAdmin/deleteBoardAjax.do?workspaceId=${workspaceId}&boardId='/>" + $(item).attr("boardId"))
								.success(function(data) {
									tree.remove(item); 
									parent.boardReload();
								})
								.error(function(event, request, settings) { alert("error"); }); 
							} 
							 
						}}, ccp : false/* 에디터 바꾸기 Context Menu 사용 안함 */, remove : false, create : false/* 생성 Context Menu 사용 안함 *//* 이름 바꾸기 Context Menu 사용 안함 */
					};
					 
					var boardId = $item.attr("boardid");
					var dummy   = $item.attr("dummy"); 
					var dept = $item.parents("li[class^=jstree]").length; 
					
					if(dummy) { 
						$.extend(menuItem, { 
							"4-update" : false,
							"5-rename" : false,	
							"6-remove" : false
						});
					}
				 
					/***
					if(dept > 2) {
						$.extend(menuItem, { 
							"1-createBoard" : false,
							"2-createCategory" : false,	
							"3-createLink" : false
						});	 
					}
					***/ 
					
					if($item.attr("hasChildren") == "null") {  
						$.extend(menuItem, {
							"6-remove" : false
						});
					}  
					 
					return menuItem;
				}
			}	        
		})/* 노드 이동 관련 콜백함수 바인드*/.bind("move_node.jstree", function(event, data){  
			var node          = data.rslt.o; 
		    var parentNode    = data.inst._get_parent(node); 
		    var sortOrder     = data.rslt.cp;
		    var workspaceId   = $(node).attr("workspaceId");
		    var boardId       = $(node).attr("boardId");
		    var boardParentId = $(parentNode).attr("boardId"); 
		    
			if(confirm("<ikep4j:message pre="${preMessage}" key="move" />")) { 
				$.post("<c:url value='/collpack/collaboration/board/boardAdmin/updateMoveBoard.do'/>",  {"workspaceId":workspaceId,"boardId" : boardId, "boardParentId" : boardParentId, "sortOrder" : sortOrder})
				.success(function(data) {
					alert("<ikep4j:message pre='${preMessage}' key='move' post='complete'/>");  
					parent.boardReload();
				})
				.error(function(event, request, settings) { 
					alert("error");
					$.jstree.rollback(data.rlbk); 	
				});  
			} else {
				$.jstree.rollback(data.rlbk); 
			}
		}).bind("rename.jstree", function(event, data) { 
			var node      = data.rslt.obj;
			var workspaceId   = $(node).attr("workspaceId");
			var boardId   = $(node).attr("boardId");
			var boardName = data.rslt.new_name;  
			
			if(node.attr("boardName") == boardName) {
				return false;
			}
			if(boardName == null || boardName.length == 0 ) {
				alert("<ikep4j:message key='message.collpack.collaboration.NotEmpty.board.boardName' />");
				$.jstree.rollback(data.rlbk); 
				return false;
			} 
			
			if(boardName.length > 200) {
				alert("<ikep4j:message key='message.collpack.collaboration.Size.board.boardName' />");
				$.jstree.rollback(data.rlbk); 
				return false;
			}
			
			if(confirm("<ikep4j:message pre="${preMessage}" key="rename" />")) { 
				$.post("<c:url value='/collpack/collaboration/board/boardAdmin/updateBoardName.do'/>",  {"workspaceId":"${workspaceId}","boardId" : boardId, "boardName" : boardName})
				.success(function(data) {
					alert("<ikep4j:message pre='${preMessage}' key='rename' post='complete'/>");  
					parent.boardReload();
				})
				.error(function(event, request, settings) {  
					$.jstree.rollback(data.rlbk); 	
				});  
			} else {
				$.jstree.rollback(data.rlbk); 
			} 
		}).bind("open_node.jstree", function (event, data) {
			window.setTimeout("iKEP.iFrameContentResize()", 400);
			
		}).bind("close_node.jstree", function (event, data) {
			window.setTimeout("iKEP.iFrameContentResize()", 400);
			
		}); 
				
		/* 노드 클릭시 이벤트*/
	    $("#adminBoardAdminTree").delegate("a", "click", function () {
	    	if($(this).parent().attr("boardId")!="")
				location.href = "<c:url value='/collpack/collaboration/board/boardAdmin/readBoardView.do?workspaceId=${workspaceId}&boardId='/>" + $(this).parent().attr("boardId"); 
	     }); 
		
	    iKEP.iFrameContentResize();
	}); 	 
	
	parent.boardReload();

})(jQuery); 
//-->
</script>
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
<!--pageTitle Start-->
<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
</div> 
<!--//pageTitle End-->		 
<!--blockLeft Start-->
<div class="blockLeft" style="width:45%;">	 
	<div class="leftTree" style="width:100%; padding:0;">
		<div id="adminBoardAdminTree"> 
		</div>
	</div>
</div>
<!--//blockLeft End-->

<!--blockRight Start-->
<div id="boardDetail" class="blockRight treeBox" style="width:53%;">
<ikep4j:message pre="${preDetail}" key="help1" /><br/>
&nbsp; <ikep4j:message pre="${preDetail}" key="help2" /><br/>
&nbsp; <ikep4j:message pre="${preDetail}" key="help3" /><br/>
&nbsp; <ikep4j:message pre="${preDetail}" key="help4" /><br/>
&nbsp; <ikep4j:message pre="${preDetail}" key="help5" /><br/>
&nbsp; <ikep4j:message pre="${preDetail}" key="help6" /><br/>
<br/>
<ikep4j:message pre="${preDetail}" key="help7" /><br/>
&nbsp; <ikep4j:message pre="${preDetail}" key="help8" /><br/>
&nbsp; <ikep4j:message pre="${preDetail}" key="help9" /><br/>
<br/>
<ikep4j:message pre="${preDetail}" key="help10" /><br/>
&nbsp; <ikep4j:message pre="${preDetail}" key="help11" /><br/>
</div>
<!--//blockLight End-->	 

			
</div>