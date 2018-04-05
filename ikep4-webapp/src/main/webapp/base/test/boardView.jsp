<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%>  
<c:set var="preTree"    value="ui.lightpack.board.common.leftBoardView" /> 
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

function menuMark(boardId) {

	var $this = $jq("#AB_" + boardId).children();
	var $thisParent = $this.parent();

	$jq('#leftMenu li:not(.leftTree li)').removeClass('licurrent');
	$thisParent.parents('#leftMenu li').addClass('licurrent');
	$thisParent.addClass('licurrent');

	if($thisParent.hasClass('no_child') || $thisParent.find('li').size() === 0) {
		return true;
	}
	
	if($thisParent.children('ul:visible, div:visible').size() > 0) {
		$thisParent.children('ul, div').hide();
		$thisParent.removeClass('opened');
	} else {
		$thisParent.children('ul, div').show();
		$thisParent.addClass('opened');	
	}
}

function menuSetCss(boardId) {
	$jq("#boardList").find("a.board").each(function() {
		$anchor = $jq(this);
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

(function($) {
	$(document).ready(function() {
		setBbsMenu();
		
		$(window).bind("resize", wResize); 
		
		$("#boardAdminViewButton").click(function() {   
			iKEP.iFrameMenuOnclick("<c:url value='/lightpack/board/boardAdmin/listBoardView.do?boardRootId=${boardRootId}&popupYn=${popupYn}'/>");  
		}); 
	}); 

	var boardList = [];
	<c:forEach var="board" varStatus="varStatus" items="${boardList}">
	boardList.push({boardId:"${board.boardId}", boardType:${board.boardType}, boardLevel:${board.indentation}, cntChild:${board.hasChildren}, boardName:"${user.localeCode == portal.defaultLocaleCode ? board.boardName : board.boardEnglishName}"});
	</c:forEach>
	
	function setBbsMenu() {	// normal menu
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
			});
			
			$("#boardList").click(function(event) {
				var $anchor = $(event.target);
				if(event.target.tagName.toLowerCase() == "a" && $anchor.hasClass("board")) {
					var boardInfo = $anchor.data();
					if(boardInfo.cntChild <= 1) {
						switch(boardInfo.boardType) {
							case 0 :	// 게시판
								iKEP.iFrameMenuOnclick("<c:url value='/lightpack/board/boardItem/listBoardItemView.do'/>?boardId=" + boardInfo.boardId + "&popupYn=${popupYn}");
								break;
							case 1 :	// 링크
								$("#contentIframe").attr("src", "<c:url value='/lightpack/board/boardCommon/readLinkBoardView.do'/>?boardId=" + boardInfo.boardId + "&popupYn=${popupYn}");
								break;
							case 2 :	// 카테고리
								break;
						}
					}
				}
			});
			
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
			
			iKEP.setLeftMenu();
		}
	}
	
	function setBbsTree() {
		var bbsList = [];
		var parent = [];
		$.each(boardList, function() {
			if(this.boardLevel == 1) {
				bbsList.push(this);
				parent[0] = this;
			} else {
				switch(this.boardLevel - parent[this.boardLevel-2].boardLevel) {
					case 1 :	// parent의 하위 레벨
						parent[this.boardLevel-1] = this;	
						if(parent[this.boardLevel-2]["cihldrens"] == undefined) parent[this.boardLevel-2]["cihldrens"] = new Array(this);
						else parent[this.boardLevel-2]["cihldrens"].push(this);
						break;
					case -1 :	// parent의 상위 레벨
						parent[this.boardLevel-2]["childrens"].push(this);
						parent[this.boardLevel-1] = this;
						break;
					case 0 :	// parent와 동일 레벨
						parent[this.boardLevel-2]["cihldrens"].push(this);
						break;
				} 
			}
		});
		iKEP.debug(bbsList);
	}
})(jQuery);
//-->
</script>
<!--leftMenu Start-->   
	<h1 class="none"><ikep4j:message pre="${preTree}" key="tree" /></h1> 
	<h2><a href="<c:url value='/lightpack/board/boardCommon/boardView.do?boardRootId=0'/>"><ikep4j:message pre="${preTree}" key="menuName"/></a></h2>
	<div id="boardList" class="left_fixed">	
	</div> 	  
	<c:if test="${isSystemAdmin and not popupYn}"> 	    
	<div class="left_fixed">	
		<div class="boxList mt-1"><!--상위의 라인과 겹치는 경우만 mt-1을 입력함-->
			<p><ikep4j:message pre="${preTree}" key="administrator" /></p>				
		</div>
		<div class="boxList_sub">
			<ul> 
				<li class="liFirst no_child"><a id="boardAdminViewButton" href="#a"><ikep4j:message pre="${preTree}" key="boardManagement" /></a></li> 
			</ul>
		</div>	 
	</div>
	</c:if> 
		