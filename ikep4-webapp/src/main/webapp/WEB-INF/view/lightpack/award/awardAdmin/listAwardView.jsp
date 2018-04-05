<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<c:set var="preHeader"  value="ui.lightpack.award.awardAdmin.listAwardView.header" /> 
<c:set var="preDetail"  value="ui.lightpack.award.awardAdmin.listAwardView.detail" />  
<c:set var="preMessage" value="message.lightpack.common.awardAdmin" />   
<script type="text/javascript">
<!-- 
var isAwardTreeMenu = ${isMenuTree == true ? "true" : "false"};
var maxAwardDepth = 4;
(function($){
	iKEP.getTreeTerminalNode = function(tree, node, isDepth) {
		var $terminalNode;
		var $node = tree._get_node(node);
		var depth = 0;
		
		function _getTerminalNode($item, _depth) {
			if(tree._is_loaded($item)) {
				if(depth < _depth) {
					depth = _depth;
					$terminalNode = $item;
				}
				
				var $items = tree._get_children($item);
				if($items.size() > 0) {
					for(var i=0; i<$items.size(); i++) {
						_getTerminalNode($($items[i]), _depth+1);
					}
				}
			} else {
				depth = undefined;
				$terminalNode = undefined;
			}
		}
		
		_getTerminalNode($node, depth);
		return isDepth ? depth : $terminalNode;
	};

	$(document).ready(function() { 
	 	try {
			window.parent.topScroll();
	 	} catch(e) {}
			
		
		$("#adminAwardAdminTree").bind("loaded.jstree", function (event, datet) { 
			//$(this).jstree("hide_icons");
			$("#adminAwardAdminTree").jstree("open_node","#treeRootItem");  
			 $(".leftTree").height($(".leftTree").height() + 100);  
			 iKEP.iFrameContentResize();
		}).jstree({
		    plugins:["themes", "ui", "dnd", "crrm", "json_data", "contextmenu"],   
		    "json_data" : {  
		    	 "data" : [{  
		    		        "data" : "Category", 
		    				"state" : "closed",
		    				"attr" : {"id" : "treeRootItem", "dummy" : true, "awardId" : "", "awardRootId" : "0", awardType : 2}
		    			  }], 
		    	"ajax" : {
		    		"url" : "<c:url value='/lightpack/award/awardAdmin/listChildrenAward.do'/>",
		    		"data" : function (node) {   
		    			return { awardId :  $(node).attr("awardId") ?  $(node).attr("awardId") : ""}; 
		    		},
		    		success : function(items) {
						$.each(items, function() {
							//this.data += " : " + this.attr.sortOrder;
							if(this.attr.awardType == 2) this.data = {title:this.data, icon:"award_category"};
							else this.data = {title:this.data, icon:"none"};
						});
						return items;
		    		}
		    	}
		    }, 
		    "crrm" : {
		    	move : {
		    		check_move : function(rslt) {
		    			if(rslt.np.attr("id") == "treeRootItem" || rslt.np.attr("awardType") == 2) {
		    				if(rslt.o.attr("awardType") == 2) {	// 이동하는 대상이 카테고리면
		    					var terminalDepth = rslt.ot.get_path(rslt.o).length + iKEP.getTreeTerminalNode(rslt.ot, rslt.o, true);
		    					if(!terminalDepth || terminalDepth > maxAwardDepth) {
		    						return false;
		    					}
		    				}
			    			return rslt.ot.get_path(rslt.np).length < maxAwardDepth;
		    			}
		    			return false;
		    		}
		    	}
		    },
		    "contextmenu" : {  
				items : function($item){
					var menuItem = {
						"1-createAward" : { label : "<ikep4j:message pre='${preDetail}' key='createAward'/>",  action : function(item) { 
							location.href = "<c:url value='/lightpack/award/awardAdmin/createAwardView.do?awardRootId=0'/>" + 
							"&awardParentId=" + $(item).attr("awardId") +
							"&awardType=0"; 
							
						}},
						"2-createLink" : { label : "<ikep4j:message pre='${preDetail}' key='createLink'/>",  action : function(item) { 
							location.href = "<c:url value='/lightpack/award/awardAdmin/createAwardView.do?awardRootId=0'/>" + 
							"&awardParentId=" + $(item).attr("awardId") +
							"&awardType=1";
						}},
						"3-createCategory" : { label : "<ikep4j:message pre='${preDetail}' key='createCategory'/>",  action : function(item) { 
							location.href = "<c:url value='/lightpack/award/awardAdmin/createAwardView.do?awardRootId=0'/>" + 
							"&awardParentId=" + $(item).attr("awardId") +
							"&awardType=2";
						}},
						"4-update" : { label : "<ikep4j:message pre='${preDetail}' key='update'/>",  action : function(item) { 
							location.href = "<c:url value='/lightpack/award/awardAdmin/updateAwardView.do?awardId='/>" + $(item).attr("awardId") +
							"&awardParentId=" + $(item).attr("awardParentId") +
							"&awardType=0";
						}},
						"5-rename" : { label : "<ikep4j:message pre='${preDetail}' key='rename'/>",  action : function(item) {  
							this.rename(item); 
						}},
						"6-remove" : { label : "<ikep4j:message pre='${preDetail}' key='delete'/>",  action : function(item) {   
							var tree = this;  
							 
							if(confirm("<ikep4j:message pre='${preMessage}' key='delete'/>")) {
								$.get("<c:url value='/lightpack/award/awardAdmin/deleteAwardAjax.do?awardId='/>" + $(item).attr("awardId"))
								.success(function(data) {
									tree.remove(item); 
								})
								.error(function(event, request, settings) { alert("error"); }); 
							} 
							 
						}}, ccp : false/* 에디터 바꾸기 Context Menu 사용 안함 */, remove : false, create : false/* 생성 Context Menu 사용 안함 *//* 이름 바꾸기 Context Menu 사용 안함 */
					};
					 
					var awardId = $item.attr("awardid");
					var dummy   = $item.attr("dummy"); 
					var depth = $("#adminAwardAdminTree").jstree("get_path", $item).length; 
					if(dummy) {
						$.extend(menuItem, { 
							"4-update" : false,
							"5-rename" : false,	
							"6-remove" : false,
							"7-setMenuType" : {
								label : "게시판 메뉴 설정",
								submenu : {
									"7-1-menu" : {label : "menu", _disabled:!isAwardTreeMenu, action : function() { setAwardMenuType(!isAwardTreeMenu); }},
									"7-2-tree" : {label : "tree", _disabled:isAwardTreeMenu, action : function() { setAwardMenuType(!isAwardTreeMenu); }}
								}
							}
						});
					}
				 
					if($item.attr("awardType") != 2 || depth >= (maxAwardDepth-1)) {// 카테고리가 아니거나 마지막 아이템이면 카테고리를 만들지 못하도록함
						$.extend(menuItem, {"3-createCategory" : false});
					}
					
					if($item.attr("awardType") != 2 /* || depth >= maxAwardDepth */) {	// 카테고리가 아니면 게시판 생성하지 못함 
						$.extend(menuItem, { 
							"1-createAward" : false,
							"2-createLink" : false
						});	 
					}
					
					//if($item.attr("hasChildren") > 0) {  
					if(!$item.hasClass("jstree-leaf")) {
						$.extend(menuItem, {
							"6-remove" : false
						});
					}  
					 
					return menuItem;
				}
			}	        
		})/* 노드 이동 관련 콜백함수 바인드*/.bind("move_node.jstree", function(event, data){
			/**
			 * rslt.ot : 이동되는 노드가 있었던 트리의 인스턴스
			 * rslt.rt : 이동되는 트리의 인스턴스 
			 * rslt.o : 이동 되는 노드
			 * rslt.r : 참조 노드
			 * rslt.p : 참조노드(r)를 기준으로 이동되는 위치 - first, last, befor, after....
			 * rslt.cop : 이동되기 전 위치의 색인
			 * rslt.cp : 이동된는 위치의 색인
			 * rslt.np : 이동 후의 부모노드
			 * rslt.cr : np와 동일, 이동되는 노드가 루트로 간 경우 -1
			 * rslt.op : 이전의 부모 노드
			 * rslt.or : 이동 위치에 이전에 있던 노드
			 * rslt.cy : 이동되는 노드가 복사본이면 true
			 * rslt.oc : cy가 true일때 이동되는 원본 노드
			 */
			var rslt = data.rslt;
			var tree = data.inst;
			
			var direction = rslt.cp - rslt.cop;	// 0 보다 크면 아래로 이동, 작으면 위로 이동
			var $node          = rslt.o; // 이동되는 노드
		    var $parentNode    = rslt.np;//tree._get_parent(node);
		    
		    var sortOrder;//     = rslt.cp;
		    if(rslt.op[0] == rslt.np[0]) {	// 동일한 카테고리 레벨에서 이동시 : 단순 정렬 변경
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
		    } else {	// 다른 카테고리로 이동
		    	switch(rslt.p) {
		    		case "before" :
		    			sortOrder = rslt.r.attr("sortOrder");
		    			break;
		    		case "after" :
		    			sortOrder = parseInt(rslt.r.attr("sortOrder"), 10) + 1;
		    			break;
		    		case "last" :
		    			var $items = tree._get_children($parentNode);
			    		sortOrder = $items.size() == 1 ? 0 : parseInt($items.eq($items.size()-2).attr("sortOrder"), 10) + 1;
		    			break;
		    	}
		    }

		    var awardId       = $node.attr("awardId");
		    var awardParentId = $parentNode.attr("awardId");
		    
			if(confirm("<ikep4j:message pre="${preMessage}" key="move" />")) { 
				$.post("<c:url value='/lightpack/award/awardAdmin/updateMoveAward.do'/>",  {"awardId" : awardId, "awardParentId" : awardParentId, "sortOrder" : sortOrder})
				.success(function(data) {
					//alert($(node).attr("<ikep4j:message pre='${preMessage}' key='move' post='complete'/>");
					if(rslt.op[0] == rslt.np[0]) {
						//rslt.cp - rslt.cop
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
					} else {
						var $orgItems = tree._get_children(rslt.op);
						for(var i=rslt.cop; i<$orgItems.size(); i++) {
							var $item = $orgItems.eq(i);
							var reorder = parseInt($item.attr("sortOrder"), 10)-1;
							$item.attr("sortOrder", reorder);
						}
						
						var $tarItems = tree._get_children(rslt.np);
						for(var i=rslt.cp+1; i<$tarItems.size(); i++) {
							var $item = $tarItems.eq(i);
							var reorder = parseInt($item.attr("sortOrder"), 10)+1;
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
			var awardId   = $(node).attr("awardId");
			var awardName = data.rslt.new_name;  
		 
			if(node.attr("awardName") == awardName) {
				return false;
			}
			if(awardName == null || awardName.length == 0 ) {
				alert("<ikep4j:message key='NotEmpty.award.awardName' />");
				$.jstree.rollback(data.rlbk); 
				return false;
			} 
			
			if(awardName.length > 200) {
				alert("<ikep4j:message key='Size.award.awardName' />");
				$.jstree.rollback(data.rlbk); 
				return false;
			}
			
			if(confirm("<ikep4j:message pre="${preMessage}" key="rename" />")) { 
				$.post("<c:url value='/lightpack/award/awardAdmin/updateAwardName.do'/>",  {"awardId" : awardId, "awardName" : awardName})
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
			window.setTimeout("iKEP.iFrameContentResize()", 500);
			
		}).bind("close_node.jstree", function (event, data) {
			window.setTimeout("iKEP.iFrameContentResize()", 500);
			
		}); 
		
		
				
		/* 노드 클릭시 이벤트*/
	    $("#adminAwardAdminTree").delegate("a", "click", function () { 
	    	if($(this).parent().attr("awardId") != "") { 
	    		location.href = "<c:url value='/lightpack/award/awardAdmin/readAwardView.do?awardId='/>" + $(this).parent().attr("awardId"); 	
	    	}
			
	     }); 
 

	}); 	 
	
	function setAwardMenuType(isTree) {
		if(confirm("<ikep4j:message pre="${preMessage}" key="applyMenuType" />")) {
			$.get("<c:url value='/lightpack/award/awardAdmin/changeAwardMenuType.do'/>", {isTree:isTree})
				.success(function(result) {
					isAwardTreeMenu = isTree;
					try {
						parent.changeMenuType(isTree);
					} catch(e) {}
				});
		}
	}

})(jQuery); 
//-->
</script> 
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
<!--pageTitle Start-->
<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="title" /></h2> 
</div>
<div class="corner_RoundBox01 mb10">
	<ikep4j:message pre="${preHeader}" key="desc01" /><br/>
	<ikep4j:message pre="${preHeader}" key="desc02" />	 
	<div class="l_t_corner"></div>
	<div class="r_t_corner"></div>
	<div class="l_b_corner"></div>
	<div class="r_b_corner"></div>
</div> 
<!--blockLeft Start-->
<div class="blockLeft" style="width:45%;">
<div class="leftTree">
	<div id="adminAwardAdminTree"> 
	</div>
</div>
</div>
<!--//blockLeft End-->

<!--blockRight Start-->
<div id="awardDetail" class="blockRight treeBox" style="width:53%;">
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