<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%>  
<c:set var="preMenu" value="ui.support.note.common.leftNoteView" />
<c:set var="preMessage" value="message.support.common.note" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 메시지 관련 Prefix 선언 End --%> 
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/note.css"/>" />
<script type="text/javascript">
<!--
var currentGroupType = "U";
var currentFolderId = "";
var searchConditionString = "${searchConditionString}";
var noteId = "${noteId}";
   
function resizeIframe() { 
	iKEP.iFrameContentResize();  
}
function topScroll() { 
	$jq(window).scrollTop(0);
} 
function notePriorityCountChange(val) { 
	var notePriorityCount = parseInt($jq("#notePriorityCount").text());
	if (val){
		notePriorityCount = notePriorityCount - 1;
	} else {
		notePriorityCount = notePriorityCount + 1;
	}
	$jq("#notePriorityCount").text(notePriorityCount);
} 

function menuSetCss(folderId) {
	$jq("#userFolderList").find("a.board").each(function() {
		var $anchor = $jq(this);
		if($anchor.data("folderId") == folderId) {
			$anchor.trigger("click");
			return false;
		}
	});		
}

function wResize(){
	var windowHeight = $jq(window).height();
	var iframe = $jq("#contentIframe");
	var menuHeight = $jq("#leftMenu").height();

	if(windowHeight-90 >= menuHeight){
		iframe.height(windowHeight-130);
	}else{
		iframe.height(menuHeight);
	}
}

var setPrivateMenu, setPublicMenu;
(function($){

	$(document).ready(function() {
		wResize();
		//$(window).bind("resize", wResize); 
		
		$("#folderViewButton").click(function() {
			$("#mainContents").ajaxLoadStart();
			
			currentGroupType = "U";
			currentFolderId = "F";
			iKEP.iFrameMenuOnclick("<c:url value='/support/note/listFolderView.do'/>");  
		});
		
		$("#listAllView").click(function() {
			clickFolder("A", "U");
		}); 

		$("#listPriorityView").click(function() {
			clickFolder("I", "U");
		}); 

		$("#listTrashView").click(function() {
			clickFolder("D", "U");
		}); 
		
		$("#newNoteButton").click(function() {
			if(userFolderList.length > 0) {
				$("li").removeClass("licurrent");
				iKEP.iFrameMenuOnclick("<c:url value='/support/note/createNoteView.do?folderId=" + currentFolderId + "&groupType=" + currentGroupType + "'/>");
			} else {
				var conMsg = "<ikep4j:message pre="${preMessage}" key="createFolder" />";		    	
		    	if(confirm(conMsg)) {
		    		$jq("#folderViewButton").click();
		    	}
			}
		});

		<%-- 개인 폴더 보여주기 --%>
		setPrivateMenu();
		<%-- 공유 폴더 보여주기 --%>
		setPublicMenu();
		
		$("#contentIframe").load(function() { 
			$("#mainContents").ajaxLoadComplete(); 
		});
		
		iKEP.setLeftMenu();
	});
	
	var userFolderList = [];
	<c:forEach var="folder" varStatus="varStatus" items="${userFolderList}">
		userFolderList.push({folderId:"${folder.folderId}", folderType:${folder.folderType}, folderLevel:${folder.indentation}, cntChild:${folder.hasChildren}, noteCnt:${folder.noteCnt}, color:"${folder.color}", folderName:"${folder.folderNameReplaceString}"});
	</c:forEach>

	var sharedFolderList = [];
	sharedFolderList.push({folderId:"root", folderType:1, folderLevel:1, folderName:"<ikep4j:message pre="${preMenu}" key="public" />", userName:"", userTitle:""});
	<c:forEach var="folder" varStatus="varStatus" items="${sharedFolderList}">
		sharedFolderList.push({folderId:"${folder.folderId}", folderType:0, folderLevel:2, folderName:"${folder.folderName}", userName:"${folder.userName}", userTitle:"${folder.userTitleName}"});
	</c:forEach>

	function clickFolder(folderId, searchType) {
		$("#mainContents").ajaxLoadStart();
		currentGroupType = searchType;
		currentFolderId = folderId;

		iKEP.iFrameMenuOnclick("<c:url value='/support/note/listNoteView.do'/>?folderId=" + folderId + "&noteId=" + noteId + "&searchConditionString="+searchConditionString+"&searchType=" + searchType);
		searchConditionString = "";
		noteId = "";
	}

	<%-- 개인 폴더 조회 --%> 
	setPrivateMenu = function(isInitial) {	// normal menu
		$("#userFolderList").removeClass("mt5 mb10");
		$("#userFolderList").addClass("left_fixed");
		if(userFolderList.length > 0) {
			var $container = $("<ul/>").appendTo("#userFolderList");
			var $parents = [];	// li
			$.each(userFolderList, function(idx, item) {
				
				var aClassName = "board";
				var $anchor = $("<a href=\"#a\" class=\""+aClassName+"\" title=\""+this.folderName+"\"><span class=\"note_color_box "+this.color+"\"></span>" + this.folderName + " (" + this.noteCnt + ")" + "</a>").data({folderId:this.folderId, folderType:this.folderType, cntChild:this.cntChild, folderLevel:this.folderLevel});
				
				var $item = $("<li class=\"board\"/>").append($anchor);
				if(idx == 0) $item.addClass("liFirst");
				$item.addClass(this.cntChild > 0 ? "opened" : "no_child");
				
				if(this.folderLevel == 1) {
					$container.append($item);
					$parents[0] = $item;	
				} else {
					switch(this.folderLevel - $parents[this.folderLevel-2].children("a").data("folderLevel")) {
						case 1 :	<%-- parent의 하위 레벨 --%>
							$parents[this.folderLevel-1] = $item;	
							if($parents[this.folderLevel-2].children("ul").length > 0) $parents[this.folderLevel-2].children("ul").append($item);
							else $("<ul/>").appendTo($parents[this.folderLevel-2]).append($item);
							break;
						case -1 :	<%-- parent의 상위 레벨 --%>
							$parents[this.folderLevel-2].children("ul").append($item);
							break;
						case 0 :	<%-- parent와 동일 레벨 --%>
							$parents[this.folderLevel-2].children("ul").append($item);
							break;
					} 
				}
			});		
			
			$("#userFolderList").click(function(event) {
				var $anchor = $(event.target);
				if(event.target.tagName.toLowerCase() == "a" && $anchor.hasClass("board")) {
					var folderInfo = $anchor.data();
					clickFolder(folderInfo.folderId, "U");
				}
			});
			
			if(isInitial != true) {
				
			}
		}
	};	


	<%-- 공유 폴더 조회 --%> 
	setPublicMenu = function() {
		$("#sharedFolderList").removeClass("mt5 mb10");
		$("#sharedFolderList").addClass("left_fixed");
		if(sharedFolderList.length > 1) {
			$("#sharedFolderList").empty();
			var $container = $("<ul/>").appendTo("#sharedFolderList");
			var $parents = [];	// li
			$.each(sharedFolderList, function(idx, item) {
				
				var aFolderStyle = "";
				var folderName = this.folderName;
				if(this.folderType == 1){
					aFolderStyle = "<span class=\"note_icon_leftmenu5\"></span>";
				} else {
					folderName = this.folderName + "("+this.userName+" "+this.userTitle+")";
				}
				
				var $anchor = $("<a href=\"#a\" class=\"board\">" + aFolderStyle + folderName + "</a>").data({folderId:this.folderId, folderType:this.folderType, folderLevel:this.folderLevel});
				
				var $item = $("<li class=\"board\"/>").append($anchor);
				if(idx == 0) $item.addClass("liFirst");
				
				if(this.folderLevel == 1) {
					$item.addClass("opened");
					$container.append($item);
					$parents[0] = $item;	//li
				} else {
					switch(this.folderLevel - $parents[this.folderLevel-2].children("a").data("folderLevel")) {
						case 1 :	<%-- parent의 하위 레벨  --%>
							$parents[this.folderLevel-1] = $item;	
							if($parents[this.folderLevel-2].children("ul").length > 0) $parents[this.folderLevel-2].children("ul").append($item);
							else $("<ul/>").appendTo($parents[this.folderLevel-2]).append($item);
							break;
						case -1 :	<%-- parent의 상위 레벨 --%>
							$parents[this.folderLevel-2].children("ul").append($item);
							break;
						case 0 :	<%-- parent와 동일 레벨 --%>
							$parents[this.folderLevel-2].children("ul").append($item);
							break;
					} 
				}
			});	
			
			$("#sharedFolderList").click(function(event) {
				var $anchor = $(event.target);
				if(event.target.tagName.toLowerCase() == "a" && $anchor.hasClass("board")) {
					var folderInfo = $anchor.data();
					switch(folderInfo.folderType) {
						case 0 :	// folder
							clickFolder(folderInfo.folderId, "S");
							break;
						case 1 :	// category
					}
				}
			});			
		}
		
		<c:choose>
			<c:when test="${not empty folderId}">
				if ('${folderId}' == 'A') {
					$jq("#listAllView").click();
				} else if ('${folderId}' == 'I') {
					$jq("#listPriorityView").click();
				} else if ('${folderId}' == 'D') {
					$jq("#listTrashView").click();
				} else if ('${folderId}' == 'F') {
					$jq("#folderViewButton").click();
				} else {
					menuSetCss(${folderId});
				}
			</c:when>
			<c:otherwise>
				$jq("#listAllView").click();
			</c:otherwise> 
		</c:choose>
	}; 
	
})(jQuery);
//-->
</script>
	<h1 class="none"><ikep4j:message pre="${preMenu}" key="tree" /></h1>
	<h2 id="leftTopModuleTitleH2">&nbsp;</h2> 
	<%-- <h2><a href="<c:url value='/support/note/noteView.do'/>"><ikep4j:message pre="${preMenu}" key="menuName"/></a></h2> --%>
    <div id="newNoteButton" class="blockButton_2" style="margin-bottom:10px;"> 
    	<a href="#a" class="button_2"><span><img src="<c:url value='/base/images/icon/ic_post.gif' />" alt="<ikep4j:message key='ui.support.common.button.create'/>" /><ikep4j:message key='ui.support.common.button.create'/></span></a>			
    </div>    
	<div class="left_fixed">
		<ul>	
			<li class="liFirst no_child"><a id="listAllView" href="#a"><span class="note_icon_leftmenu1"></span><ikep4j:message pre="${preMenu}" key="noteAllListView" /> (${folderACnt})</a></li>
		</ul>
	</div>	 
	<div class="left_fixed">
		<ul>	
			<li class="liFirst no_child"><a id="listPriorityView" href="#a"><span class="note_icon_leftmenu2"></span><ikep4j:message pre="${preMenu}" key="notePriorityListView" /> (<span id="notePriorityCount">${folderICnt}</span>)</a></li>
		</ul>
	</div>	
	<div id="userFolderList" class="left_fixed"></div>
	<div class="left_fixed">
		<ul>	
			<li class="liFirst no_child"><a id="listTrashView" href="#a"><span class="note_icon_leftmenu3"></span><ikep4j:message pre="${preMenu}" key="noteTrashListView" /></a></li>
		</ul>
	</div>
	<div class="left_fixed">
		<ul>
			<li class="liFirst no_child"><a id="folderViewButton" href="#a"><span class="note_icon_leftmenu4"></span><ikep4j:message pre="${preMenu}" key="folderManagement" /></a></li>				
		</ul>	
	</div>
	<%-- <div id="sharedFolderList" class="left_fixed">
		<ul>
			<li class="liFirst opened"><a id="folderViewButton" href="#a" ><span class="note_icon_leftmenu5"></span><ikep4j:message pre="${preMenu}" key="public" /></a>
				<ul>
					<li class="no_child liLast">
						<div class="pl7"><ikep4j:message pre="${preMessage}" key="sharefolder.notExist" /></div>
					</li>
				</ul>
			</li>			
		</ul>
	</div> --%>