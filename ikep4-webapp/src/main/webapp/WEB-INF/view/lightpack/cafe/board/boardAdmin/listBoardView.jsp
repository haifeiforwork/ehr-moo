<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="preHeader"  value="message.lightpack.cafe.board.boardAdmin.listBoardView.header" /> 
<c:set var="preDetail"  value="message.lightpack.cafe.board.boardAdmin.listBoardView.detail" />
<c:set var="preCode"    value="message.lightpack.cafe.common.code" />
<c:set var="preButton"  value="message.lightpack.cafe.common.button" /> 
<c:set var="preMessage" value="message.lightpack.cafe.common.boardAdmin" />
 
<script type="text/javascript">
<!-- 
(function($){	 
	$(document).ready(function() { 
		window.parent.topScroll(); 
		
		$("#adminBoardAdminTree").bind("loaded.jstree", function (event, datet) { 
			$(this).jstree("hide_icons");
			
			 $("#adminBoardAdminTree").jstree("open_node","#treeRootItem");
			 
			 window.parent.resizeIframe();
		}).jstree({
		    plugins:["themes", "ui", "dnd", "crrm", "json_data", "contextmenu"],   
		    "json_data" : {  
		    	 "data" : [{  
		    		        "data" : "Category", 
		    				"state" : "closed",
		    				"attr" : {"id" : "treeRootItem", "dummy" : true, "boardId" : "", "boardRootId" : "1","cafeId":"${cafeId}"}
		    			  }], 
		    	"ajax" : {
		    		"url" : "<c:url value='/lightpack/cafe/board/boardAdmin/listChildrenBoard.do'/>",
		    		"data" : function (node) {
		    			//iKEP.debug(node)
		    			return { boardId :  $(node).attr("boardId") ?  $(node).attr("boardId") : "",cafeId:"${cafeId}"};
		    			iKEP.iFrameContentResize();
		    		}
		    	}
		    },
		    "crrm" : {
		    	move : {
		    		check_move : function(rslt) {
		    			return rslt.r[0] != rslt.np[0] && rslt.ot.get_path(rslt.np).length <= 1;
		    		}
		    	}
		    },
		    "contextmenu" : {  
				items : function($item){
					var menuItem = {
						"1-createBoard" : { label : "<ikep4j:message pre='${preDetail}' key='createBoard'/>",  action : function(item) {
							$jq("#boardDetail").empty();
							$jq("#boardDetail").load('<c:url value="/lightpack/cafe/board/boardAdmin/createBoardView.do"/>?boardRootId=0&cafeId=' + $(item).attr("cafeId")+'&boardParentId=' + $(item).attr("boardId") +'&boardType=0')
							.error(function(event, request, settings) { alert("error"); });							
						}},
						/*"2-createCategory" : { label : "<ikep4j:message pre='${preDetail}' key='createCategory'/>",  action : function(item) {
							$jq("#boardDetail").empty();
							$jq("#boardDetail").load('<c:url value="/lightpack/cafe/board/boardAdmin/createBoardView.do"/>?boardRootId=0&&cafeId=' + $(item).attr("cafeId")+'&boardParentId=' + $(item).attr("boardId") +'&boardType=1')
							.error(function(event, request, settings) { alert("error"); });
						}},*/						
						"3-createLink" : { label : "<ikep4j:message pre='${preDetail}' key='createLink'/>",  action : function(item) {
							$jq("#boardDetail").empty();
							$jq("#boardDetail").load('<c:url value="/lightpack/cafe/board/boardAdmin/createBoardView.do"/>?boardRootId=0&cafeId=' + $(item).attr("cafeId")+'&boardParentId=' + $(item).attr("boardId") +'&boardType=2')
							.error(function(event, request, settings) { alert("error"); });	
						}},

						"4-update" : { label : "<ikep4j:message pre='${preDetail}' key='update'/>",  action : function(item) { 
							$jq("#boardDetail").empty();
							$jq("#boardDetail").load('<c:url value="/lightpack/cafe/board/boardAdmin/updateBoardView.do"/>?boardId='+ $(item).attr("boardId") +'&cafeId=' + $(item).attr("cafeId")+'&boardParentId=' + $(item).attr("boardParentId") +'&boardType=0')
							.error(function(event, request, settings) { alert("error"); });
						}},
						"5-rename" : { label : "<ikep4j:message pre='${preDetail}' key='rename'/>",  action : function(item) {  
							this.rename(item); 
						}},
						"6-remove" : { label : "<ikep4j:message pre='${preDetail}' key='delete'/>",  action : function(item) {   
							var tree = this;  
							 
							if(confirm("<ikep4j:message pre='${preMessage}' key='delete'/>")) {
								$.get("<c:url value='/lightpack/cafe/board/boardAdmin/deleteBoardAjax.do?cafeId=${cafeId}&boardId='/>" + $(item).attr("boardId"))
								.success(function(data) {
									tree.remove(item); 
									$jq("#boardDetail").empty();
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
				 
					if(dept >= 1) {
						$.extend(menuItem, { 
							"1-createBoard" : false,
							//"2-createCategory" : false,	
							"3-createLink" : false
						});	 
					} 
					
					if($item.attr("hasChildren") == "null") {  
						$.extend(menuItem, {
							"6-remove" : false
						});
					}  
					 
					return menuItem;
				}
			}	        
		})/* 노드 이동 관련 콜백함수 바인드*/.bind("move_node.jstree", function(event, data){
			var rslt = data.rslt;
			var tree = data.inst;
			
			var direction = rslt.cp - rslt.cop;
			var $node          = rslt.o;
		    var $parentNode    = rslt.np;
		    
		    var sortOrder;//     = rslt.cp;
		    switch(rslt.p) {
		    	case "before" :
		    		if(direction < 0) {	//올라갈때
		    			if(rslt.r[0] == $parentNode[0]) sortOrder = 0;
			    		else sortOrder = rslt.r.attr("sortOrder");
		    		} else {
			    		sortOrder = tree._get_prev($node, true).attr("sortOrder");
		    		}
		    		break;
		    	case "after" :
		    		if(direction < 0) {	//올라갈때
		    			sortOrder = tree._get_next($node, true).attr("sortOrder");
		    		} else {
		    			sortOrder = rslt.r.attr("sortOrder");
		    		}
		    		break;
		    }
		    
		    var cafeId   = $node.attr("cafeId");
		    var boardId       = $node.attr("boardId");
		    var boardParentId = $parentNode.attr("boardId"); 
		    
			if(confirm("<ikep4j:message pre="${preMessage}" key="move" />")) { 
				$.post("<c:url value='/lightpack/cafe/board/boardAdmin/updateMoveBoard.do'/>",  {"cafeId":cafeId,"boardId" : boardId, "boardParentId" : boardParentId, "sortOrder" : sortOrder})
				.success(function(data) {
					//alert("<ikep4j:message pre='${preMessage}' key='move' post='complete'/>");
					var $items = tree._get_children(rslt.np);
					if(direction < 0) {	//올라갈때
						for(var i=rslt.cp+1; i<rslt.cop+1; i++) {
							var $item = $items.eq(i);
							var reorder = parseInt($item.attr("sortOrder"), 10)+1;
							$item.attr("sortOrder", reorder);
						}
					} else {
						for(var i=rslt.cop; i<rslt.cp-1; i++) {
							var $item = $items.eq(i);
							var reorder = parseInt($item.attr("sortOrder"), 10)-1;
							$item.attr("sortOrder", reorder);
						}
					}
					
					$node.attr("sortOrder", sortOrder);
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
			var cafeId   = $(node).attr("cafeId");
			var boardId   = $(node).attr("boardId");
			var boardName = data.rslt.new_name;  
			
			if(node.attr("boardName") == boardName) {
				return false;
			}
			if(boardName == null || boardName.length == 0 ) {
				alert("<ikep4j:message key='message.lightpack.cafe.NotEmpty.board.boardName' />");
				$.jstree.rollback(data.rlbk); 
				return false;
			} 
			
			if(boardName.length > 200) {
				alert("<ikep4j:message key='message.lightpack.cafe.Size.board.boardName' />");
				$.jstree.rollback(data.rlbk); 
				return false;
			}
			
			if(confirm("<ikep4j:message pre="${preMessage}" key="rename" />")) { 
				$.post("<c:url value='/lightpack/cafe/board/boardAdmin/updateBoardName.do'/>",  {"cafeId":"${cafeId}","boardId" : boardId, "boardName" : boardName})
				.success(function(data) {
					alert("<ikep4j:message pre='${preMessage}' key='rename' post='complete'/>");  
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
	    	if($(this).parent().attr("boardId")!="") {
	    		$jq("#boardDetail").empty();
				$jq("#boardDetail").load('<c:url value="/lightpack/cafe/board/boardAdmin/readBoardView.do"/>?boardId='+ $(this).parent().attr("boardId") +'&cafeId=' + ${cafeId},"").error(function(event, request, settings) { alert("error"); });
	    	}
				
	     }); 
		
	    iKEP.iFrameContentResize();
	}); 	 
	

})(jQuery); 
//-->
</script>
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
<!--pageTitle Start-->
<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
	<span><ikep4j:message pre='${preHeader}' key='help' /><span>
</div> 
<!--//pageTitle End-->		 
<!--blockLeft Start-->
<div class="blockLeft" style="width:30%;">	 
	<div class="leftTree" style="width:100%; padding:0;">
		<div id="adminBoardAdminTree"> 
		</div>
	</div>
</div>
<!--//blockLeft End-->

<!--blockRight Start-->
<div class="blockRight" style="width:68%;">
	<div id="boardDetail">
	</div>
</div>
<!--//blockRight End-->	 
