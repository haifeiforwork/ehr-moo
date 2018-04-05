<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  	value="ui.support.note.listFolderView" />
<c:set var="preHeader"  value="ui.support.note.listFolderView.header" /> 
<c:set var="preDetail"  value="ui.support.note.listFolderView.detail" />
<c:set var="preTree" 	value="ui.support.note.common.leftNoteView" /> 
<c:set var="preMessage" value="message.support.common.note" />   
<script type="text/javascript">
<!-- 
var maxBoardDepth = 3;
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

	/**
	* 폴더 등록
	* @param {Object} selectData : Node Info
	*/
	f_FolderCreateForm = function(selectData){
		var folderParentId	= $(selectData).attr("folderId");

		if (folderParentId == undefined) {
			return false;
		}

		//폴더 상세화면
		$("#folderDetail").load('<c:url value="/support/note/createFolderView.do?folderParentId="/>'+folderParentId+'&folderType=0');
	};

	$(document).ready(function() { 
		var curNavText = '&nbsp;&gt;&nbsp;'+'<ikep4j:message pre="${prefix}" key="folderAdmin" />';
		//parent.callLocation(document.URL,curNavText);	
		
		
	 	try {
			window.parent.topScroll();
	 	} catch(e) {}
			
		var RootTitle = "<ikep4j:message pre='${preTree}' key='folderTree' />";
		if(RootTitle == '') RootTitle = "NOTE";
	 	
		$("#noteFolderTree").bind("loaded.jstree", function (event, datet) { 
			$("#noteFolderTree").jstree("open_all");
			iKEP.iFrameContentResize();
		}).jstree({
		    plugins:["themes", "ui", "dnd", "crrm", "json_data", "contextmenu"],   
		    "json_data" : {  
		    	 "data" : [{  
		    		        "data" : {"title":RootTitle,"icon":"none"}, 
		    				"state" : "closed",
		    				"attr" : {"id" : "treeRootItem", "dummy" : true, "folderId" : "", "folderRootId" : "0", folderType : 1}
		    			  }], 
		    	"ajax" : {
		    		"url" : "<c:url value='/support/note/listChildrenFolder.do'/>",
		    		"data" : function (node) {   
		    			return { folderId :  $(node).attr("folderId") ?  $(node).attr("folderId") : ""}; 
		    		},
		    		success : function(items) {
						$.each(items, function() {
							//this.data += " : " + this.attr.sortOrder;
							if(this.attr.folderType == 1){
								this.data = {title:this.data, icon:"board_category"};
							}else if(this.attr.folderType == 0){
								this.data = {title:this.data, icon:""};
							}
						});
						return items;
		    		}
		    	}
		    }, 
		    "crrm" : {
		    	move : {
		    		check_move : function(rslt) {
		    			if(rslt.np.attr("id") == "treeRootItem" || rslt.np.attr("folderType") == 1) {
		    				
		    				if(rslt.np.attr("id") == "treeRootItem" && rslt.p == "last")		    					
		    					return false;
		    					
		    				if(rslt.o.attr("folderType") == 1) {	// 이동하는 대상이 카테고리면
		    					var terminalDepth = rslt.ot.get_path(rslt.o).length + iKEP.getTreeTerminalNode(rslt.ot, rslt.o, true);
		    					if(!terminalDepth || terminalDepth > maxBoardDepth) {
		    						return false;
		    					}
		    				}
			    			return rslt.ot.get_path(rslt.np).length < maxBoardDepth;
		    			}
		    			return false;
		    		}
		    	}
		    },
		    "contextmenu" : {  
				items : function($item){
					var menuItem = {
						"1-createFolder" : { label : "<ikep4j:message pre='${preDetail}' key='createFolder'/>",  action : function(item) { 
							$("#folderDetail").ajaxLoadStart();
							f_FolderCreateForm(item);
							
						}},
						"2-createCategory" : { label : "<ikep4j:message pre='${preDetail}' key='createCategory'/>",  action : function(item) { 
							location.href = "<c:url value='/support/note/createFolderView.do'/>" + 
							"?folderParentId=" + $(item).attr("folderId") +
							"&folderType=1";
						}},
						"3-update" : { label : "<ikep4j:message pre='${preDetail}' key='update'/>",  action : function(item) { 
							$("#folderDetail").ajaxLoadStart();
							$("#folderDetail").load('<c:url value="/support/note/updateFolderView.do?folderId="/>'+$(item).attr("folderId"));							 
						}},
						"4-rename" : { label : "<ikep4j:message pre='${preDetail}' key='rename'/>",  action : function(item) {  
							this.rename(item); 
						}},
						"5-remove" : { label : "<ikep4j:message pre='${preDetail}' key='delete'/>",  action : function(item) {   
							var tree = this;  
							 
							if(confirm("<ikep4j:message pre='${preMessage}' key='delete'/>")) {
								$.get("<c:url value='/support/note/deleteFolderAjax.do?folderId='/>" + $(item).attr("folderId"))
								.success(function(data) {
									//tree.remove(item); 
									parent.location.href = "<c:url value='/support/note/noteView.do?folderId=F'/>"; 
								}); 
							} 
							 
						}}, ccp : false/* 에디터 바꾸기 Context Menu 사용 안함 */, remove : false, create : false/* 생성 Context Menu 사용 안함 *//* 이름 바꾸기 Context Menu 사용 안함 */
					};
					 
					var folderid = $item.attr("folderid");
					var dummy   = $item.attr("dummy"); 
					var depth = $("#noteFolderTree").jstree("get_path", $item).length; 
					if(dummy) {
						$.extend(menuItem, { 
							"2-createCategory" : false,
							"3-update" : false,
							"4-rename" : false,
							"5-remove" : false
						});
					}
				 
					if($item.attr("folderType") != 1 || depth >= (maxBoardDepth-1)) {// 카테고리가 아니거나 마지막 아이템이면 카테고리를 만들지 못하도록함
						$.extend(menuItem, {
							"1-createFolder" : false,
							"2-createCategory" : false,
							"3-update" : false
						});
					}
					
					if($item.attr("folderType") != 1 /* || depth >= maxBoardDepth */) {	// 카테고리가 아니면 폴더 생성하지 못함 
						$.extend(menuItem, { 
							"1-createFolder" : false
						});	 
					}

					//if($item.attr("hasChildren") > 0) {  
					if(!$item.hasClass("jstree-leaf")) {
						$.extend(menuItem, {
							"5-remove" : false
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

		    var folderId       = $node.attr("folderId");
		    var folderParentId = $parentNode.attr("folderId");

			if(confirm("<ikep4j:message pre="${preMessage}" key="move" />")) { 
				$.post("<c:url value='/support/note/updateMoveFolder.do'/>",  {"folderId" : folderId, "folderParentId" : folderParentId, "sortOrder" : sortOrder})
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
					parent.location.href = "<c:url value='/support/note/noteView.do?folderId=F'/>"; 
				})
				.error(function(event, request, settings) { 
					//alert("error");
					$.jstree.rollback(data.rlbk); 	
				});  
			} else {
				$.jstree.rollback(data.rlbk); 
			}
		}).bind("rename.jstree", function(event, data) { 
			var node      = data.rslt.obj;  
			var folderId   = $(node).attr("folderId");
			var folderName = data.rslt.new_name;  
		 
			if(node.attr("folderName") == folderName) {
				return false;
			}
			if(folderName == null || folderName.length == 0 ) {
				alert("<ikep4j:message key='NotEmpty.note.folderName' />");
				$.jstree.rollback(data.rlbk); 
				return false;
			} 
			
			if(folderName.length > 100) {
				alert("<ikep4j:message key='Size.note.folderName' />");
				$.jstree.rollback(data.rlbk); 
				return false;
			}

			$jq.ajax({     
				url : '<c:url value="/support/note/existFolderName.do" />',
				data :  {"folderId":folderId, "folderName":folderName},     
				async : false,
				success : function(result) {      
					if(!result) {
						if(confirm("<ikep4j:message pre="${preMessage}" key="rename" />")) { 
							$.post("<c:url value='/support/note/updateFolderName.do'/>",  {"folderId" : folderId, "folderName" : folderName})
							.success(function(data) {
								alert("<ikep4j:message pre='${preMessage}' key='rename' post='complete'/>");  
								parent.location.href = "<c:url value='/support/note/noteView.do?folderId=F'/>"; 	
							})
							.error(function(event, request, settings) {  
								$.jstree.rollback(data.rlbk); 	
							});  
						} else {
							$.jstree.rollback(data.rlbk); 
						} 
					}else {
						$.jstree.rollback(data.rlbk); 
						alert("<ikep4j:message pre='${prefix}' key='alert.message2' />");
					}
				}
		    });	
		}).bind("open_node.jstree", function (event, data) {
			window.setTimeout("iKEP.iFrameContentResize()", 500);
			
		}).bind("close_node.jstree", function (event, data) {
			window.setTimeout("iKEP.iFrameContentResize()", 500);
			
		});
				
		/* 노드 클릭시 이벤트*/
	    $("#noteFolderTree").delegate("a", "click", function () { 
	    	if($(this).parent().attr("folderId") != "") { 
	    		$("#folderDetail").ajaxLoadStart();	
				$("#folderDetail").load('<c:url value="/support/note/updateFolderView.do?folderId="/>'+$(this).parent().attr("folderId"));
	    	}
			
	     }); 
	}); 	 

})(jQuery); 
//-->
</script> 

<h1 class="none"><ikep4j:message pre="${preHeader}" key="title" /></h1> 

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
</div>
<!--//pageTitle End-->

<!--directive Start-->
<div class="directive"> 
	<ul>
		<li><span><ikep4j:message pre="${preHeader}" key="howtoAdd" /></span></li>	
		<li><span><ikep4j:message pre="${preHeader}" key="howtoModify" /></span></li>	
		<li><span><ikep4j:message pre="${preHeader}" key="howtoDelete" /></span></li>
		<li><span><ikep4j:message pre="${preHeader}" key="howtoMove" /></span></li>																	
	</ul>
</div>
<div class="blockBlank_10px"></div>
<!--//directive End-->	

<!--blockLeft Start-->
<div class="blockLeft" style="width:28%;">	
	<div class="leftTree treeBox" style="height:550px;">	
		<c:choose>
		<c:when test="${user.localeCode == 'jp'}">
		<div><img src="<c:url value="/base/images/common/img_title_cate_jp.gif"/>" alt="category" /></div>
		</c:when>
		<c:otherwise>
		<div><img src="<c:url value="/base/images/common/img_title_cate.gif"/>" alt="category" /></div>
		</c:otherwise>
		</c:choose> 			

		<div id="noteFolderTree"> </div>
	</div>
</div>
<!--//blockLeft End-->

<!--blockRight Start-->
<div class="blockRight" style="width:70%;">

	<!-- blockDetail Start -->
	<div id="folderDetail" style="height:250px;"></div>
	<!--//blockDetail End -->
	
</div>
<!--//blockLight End-->