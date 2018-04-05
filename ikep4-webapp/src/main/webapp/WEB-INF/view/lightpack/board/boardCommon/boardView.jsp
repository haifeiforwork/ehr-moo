<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%>  
<c:set var="preTree"    value="ui.lightpack.board.common.leftBoardView" /> 
<c:set var="prefix"    value="ui.lightpack.board.boardCommon.boardView" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
 <%-- 메시지 관련 Prefix 선언 End --%> 
<script type="text/javascript">
<!--   
function resizeIframe() { 
	iKEP.iFrameContentResize();  
}
function topScroll() { 
	$jq(window).scrollTop(0);
} 

function iframeReflash(url) {
	("#contentIframe").attr("src", url); 
}

// iframe의 게시판 목록화면에서 호출됨 : 불필요해 보임(?)
function menuMark(boardId) {
	return false;
	var $li = null;
	$jq("#boardList").find("a.board").each(function() {
		var $anchor = $jq(this);
		if($anchor.data("boardId") == boardId) {
			$li = $anchor.parent();
			return false;
		}
	});
	
	var $currLi = $jq("#boardList").find("li.licurrent").has(".no_child");
	if($li != null && $li[0] != $currLi[0]) {
		$jq("#boardList").find("li.board").has(".licurrent").removeClass("licurrent");
		
		$li.addClass("licurrent");
		var $parent = $li.parents("li.board", "#boardList");
		if($parent.size() > 0) {
			$parent.addClass("licurrent");
			$parent.children("ul").show();
		}
	}
}

function menuSetCss(boardId) {
	$jq("#boardList").find("a.board").each(function() {
		var $anchor = $jq(this);
		if($anchor.data("boardId") == boardId) {
			$anchor.trigger("click");
			return false;
		}
	});
}

function wResize(){
	var iframe = $jq("#contentIframe");
	var windowHeight = $jq(window).height();
	var conHeight = iframe.contents().height();
	
	if(windowHeight > conHeight){		
		iframe.height(windowHeight-19);
	}else{
		iframe.height(conHeight+3);
	}
}

function changeMenuType(isTree) {
	var $container = $jq("#boardList").empty();
	
	if(isTree) {
		setBbsTree(true);
	} else {
		$container.jstree("destroy");
		setBbsMenu(true);
	}
}

//게시판 아이템 구분 아이콘
function getBoardItemTypeHtml(boardType) {
	var itemClass = "";
	switch(boardType) {
		case 0 : itemClass = "board"; break;
		case 1 : itemClass = "link"; break;
		case 2 : itemClass = "category"; break;
	}
	return '<ins class="' + itemClass + '"/>';
}

var setBbsTree, setBbsMenu;
(function($){	 
	$(document).ready(function() {
		<c:choose>
			<c:when test="${isMenuTree == true}">setBbsTree();</c:when>
			<c:otherwise>setBbsMenu();</c:otherwise>
		</c:choose>
		
		$(window).bind("resize", wResize); 
		
		$("#boardAdminViewButton").click(function() {   
			iKEP.iFrameMenuOnclick("<c:url value='/lightpack/board/boardAdmin/listBoardView.do?boardRootId=${boardRootId}&popupYn=${popupYn}'/>");  
		}); 
		$("#deleteBoardItemViewButton").click(function() {   
			iKEP.iFrameMenuOnclick("<c:url value='/lightpack/board/boardItem/deleteBoardItemListView.do'/>");  
		}); 
		$("#boardMenuAdminForMobileButton").click(function() {   
			iKEP.iFrameMenuOnclick("<c:url value='/lightpack/board/boardItem/getBoardMenuList.do'/>");  
		}); 
		
		$("#tempSaveButton").click(function() {   
			iKEP.iFrameMenuOnclick("<c:url value='/lightpack/board/boardItem/listTempSaveItemView.do'/>");  
		});
		
	});
	
	var boardList = [];
	<c:forEach var="board" varStatus="varStatus" items="${boardList}">
	boardList.push({boardId:"${board.boardId}", boardType:${board.boardType}, boardLevel:${board.indentation}, cntChild:${board.hasChildren}, boardName:"${user.localeCode == portal.defaultLocaleCode ? board.boardName : board.boardEnglishName}"});
	</c:forEach>
	
	function clickBoard(boardId, boardType) {
		switch(parseInt(boardType, 10)) {
			case 0 :	// 게시판
				iKEP.iFrameMenuOnclick("<c:url value='/lightpack/board/boardItem/listBoardItemView.do'/>?boardId=" + boardId + "&popupYn=${popupYn}&isSystemAdmin=${isSystemAdmin}");
				break;
			case 1 :	// 링크
				$("#contentIframe").attr("src", "<c:url value='/lightpack/board/boardCommon/readLinkBoardView.do'/>?boardId=" + boardId + "&popupYn=${popupYn}");
				break;
			case 2 :	// 카테고리
				break;
		}
	}
	
	setBbsMenu = function(isInitial) {	// normal menu
		$("#boardList").addClass("left_fixed");
		if(boardList.length > 0) {
			var $container = $("<ul/>").appendTo("#boardList");
			var $parents = [];	// li
			$.each(boardList, function(idx, item) {
				var $anchor = $("<a href=\"#a\" class=\"board\">" + this.boardName + "</a>").data({boardId:this.boardId, boardType:this.boardType, cntChild:this.cntChild, boardLevel:this.boardLevel});
				var $item = $("<li class=\"board\"/>").append($anchor);
				if(idx == 0) $item.addClass("liFirst");
				$item.addClass(this.cntChild > 0 ? "opened" : "no_child");
				
				if(this.boardLevel == 1) {
					$container.append($item);
					$parents[0] = $item;	//li
				} else {
					switch(this.boardLevel - $parents[this.boardLevel-2].children("a").data("boardLevel")) {
						case 1 :	// parent의 하위 레벨
							$parents[this.boardLevel-1] = $item;	
							if($parents[this.boardLevel-2].children("ul").length > 0) $parents[this.boardLevel-2].children("ul").append($item);
							else $("<ul/>").appendTo($parents[this.boardLevel-2]).append($item);
							break;
						case -1 :	// parent의 상위 레벨
							$parents[this.boardLevel-2].children("ul").append($item);
							break;
						case 0 :	// parent와 동일 레벨
							$parents[this.boardLevel-2].children("ul").append($item);
							break;
					} 
				}
				
				// 아이템 구분
				$anchor.prepend(getBoardItemTypeHtml(this.boardType));
			});
			
			iKEP.setLeftMenu();
			
			$("#boardList").click(function(event) {
				var $anchor = $(event.target);
				if(event.target.tagName.toLowerCase() == "a" && $anchor.hasClass("board")) {
					var boardInfo = $anchor.data();
					switch(boardInfo.boardType) {
						case 0 :	// board
						case 1 :	// link
							clickBoard(boardInfo.boardId, boardInfo.boardType);
							break;
						case 2 : //category
					}
				}
			});
			
			if(isInitial != true) {
				<c:choose>
					<c:when test="${not empty itemId}">
						iKEP.iFrameMenuOnclick("<c:url value='/lightpack/board/boardItem/readBoardItemLinkView.do'/>?itemId=${itemId}&popupYn=${popupYn}"); 
					</c:when>
					<c:when test="${not empty boardId}">
						menuSetCss(${boardId});
					</c:when>
					<c:otherwise>
						var firstLoadBoard = $("li.no_child::eq(0) a", $container).data();
						if(firstLoadBoard) {
							menuSetCss(firstLoadBoard.boardId);
						}
					</c:otherwise> 
				</c:choose>
			}
		}
	};
	
	setBbsTree = function(isInitial) {
		var bbsList = [];
		var parent = [];
		var fnToData = function(orgData) {
			var icon = "category";
			switch(orgData.boardType) {
				case 0 : icon = "board"; break;
				case 1 : icon = "link"; break;
			}
			return {
				data:{title:orgData.boardName, icon : icon},
				attr:{id:"BBSItem_"+orgData.boardId, board_type:orgData.boardType},
				boardLevel:orgData.boardLevel
			};
		};
		$.each(boardList, function() {
			var treeData = fnToData(this);
			if(treeData.boardLevel == 1) {
				bbsList.push(treeData);
				parent[0] = treeData;
			} else {
				switch(treeData.boardLevel - parent[treeData.boardLevel-2].boardLevel) {
					case 1 :	// parent의 하위 레벨
						parent[treeData.boardLevel-1] = treeData;	
						if(parent[treeData.boardLevel-2]["children"] == undefined) parent[treeData.boardLevel-2]["children"] = new Array(treeData);
						else parent[treeData.boardLevel-2]["children"].push(treeData);
						break;
					case -1 :	// parent의 상위 레벨
						parent[treeData.boardLevel-2]["children"].push(treeData);
						parent[treeData.boardLevel-1] = treeData;
						break;
					case 0 :	// parent와 동일 레벨
						parent[treeData.boardLevel-2]["children"].push(treeData);
						break;
				} 
			}
		});
		
		$("#boardList").removeClass("left_fixed")
			.bind("loaded.jstree", function(event) {
				
				$(this)//.jstree("hide_icons")
					.jstree("open_all");
				var tree = $.jstree._reference(this);
				
				if(isInitial != true) {
					var $items = tree._get_children(-1),
						$firstChild;
	 				while($items.size() > 0) {
	 					$firstChild = $items.eq(0);
						$items = tree._get_children($firstChild);
	 				}
	 				
/*	 				$(this).find("li").each(function(event) {
// 	 					$(this).children("a").prepend(getBoardItemTypeHtml(parseInt($(this).attr("board_type"), 10)));
	 					$(this).children("a").children("ins").removeClass("jstree-icon").addClass("board");
	 				});*/
	 				
	 				$firstChild.children("a").trigger("click");	// 첫번째 아이템 클릭
				}
			}).jstree({
				plugins:["themes", "ui", "json_data"],
				json_data:{data:bbsList}
			}).bind("select_node.jstree", function(event, data) {
				var tree = jQuery.jstree._reference(this);
				var $li = data.rslt.obj;
				switch($li.attr("board_type")) {
					case "0" :
					case "1" :
						var boardId = $li.attr("id").split("_")[1];
						clickBoard(boardId, $li.attr("board_type"));
						break;
				}
			});
	};
})(jQuery);
//-->
</script>
<!--leftMenu Start-->   
	<h1 class="none"><ikep4j:message pre="${preTree}" key="tree" /></h1> 
	<h2><a href="<c:url value='/lightpack/board/boardCommon/boardView.do?boardRootId=0'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_comm_bbs.gif'/>"/></a></h2>	 
	<div id="boardList" class="left_fixed"></div> 
	<div>	
		<div class="boxList mt-1"><!--상위의 라인과 겹치는 경우만 mt-1을 입력함-->
			<p><ikep4j:message pre="${prefix}" key="myItem" /></p>				
		</div>
		<div class="boxList_sub">
			<ul>
				<li class="liFirst no_child"><a id="tempSaveButton" href="#a"><ikep4j:message pre="${prefix}" key="tempSave" /></a></li> 
			</ul>
		</div>	 
	</div>  

	<input type="hidden" id="isSystemAdmin" <c:if test="${isSystemAdmin}"> value="1" </c:if><c:if test="${!isSystemAdmin}"> value="0" </c:if> />

	<c:if test="${isSystemAdmin and not popupYn}"> 	    
	<div>	
		<div class="boxList mt-1"><!--상위의 라인과 겹치는 경우만 mt-1을 입력함-->
			<p><ikep4j:message pre="${preTree}" key="administrator" /></p>				
		</div>
		<div class="boxList_sub">
			<ul> 
				<li class="liFirst no_child"><a id="boardAdminViewButton" href="#a"><ikep4j:message pre="${preTree}" key="boardManagement" /></a></li> 
				<!-- <li class="liFirst no_child"><a id="deleteBoardItemViewButton" href="#a">삭제 게시물 관리</a></li>  -->
			</ul>
		</div>	 
	</div>
	</c:if>
	 
	<div>
		<div class="boxList mt-1"><!--상위의 라인과 겹치는 경우만 mt-1을 입력함-->
			<p><ikep4j:message pre="${preTree}" key="boardMenuAdmin" /></p>				
		</div>
		<div class="boxList_sub">
			<ul> 
				<li class="liFirst no_child"><a id="boardMenuAdminForMobileButton" href="#a"><ikep4j:message pre="${preTree}" key="boardMenuAdminForMobileButton" /></a></li> 
			</ul>
		</div>
	</div>

		