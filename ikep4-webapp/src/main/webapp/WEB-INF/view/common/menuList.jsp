<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.lightpack.board.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="ui.lightpack.board.boardItem.listBoardView.list" /> 
<c:set var="preButton"  value="ui.lightpack.common.button" />
<c:set var="preMessage" value="message.lightpack.common.boardItem" />
<c:set var="preSearch"  value="ui.ikep4.common.searchCondition" /> 

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<%-- 메시지 관련 Prefix 선언 End --%>   
<script type="text/javascript" src="<c:url value='/base/js/zeroclipboard/ZeroClipboard.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/jquery/jquery.layout-latest.js'/>"></script>
<script type="text/javascript">
<!--   
var bbsIframe;  	 // 부모 iframe
var bbsIsIframe = 0; // iframe 존재 여부
var isLayout; // 레이아웃 보기 여부
var bbsLayout = null;
var layoutType = "n"; // n:없음, v:가로보기, h:세로보기

function mouseOver(obj){
	//obj.className = "bgSelected";
	//obj.style.backgroundColor="#edf2f5";
	var tds =$jq(obj).find("td");
	$jq.each( tds , function() {
		$jq(this).attr("style","background-color:#edf2f5;");
	});	
}
function mouseOut(obj){
	
	//obj.className = "";
	//obj.style.backgroundColor="";
	var tds =$jq(obj).find("td");
	$jq.each( tds , function() {
		$jq(this).attr("style","background-color:none;");
	});	
}
function mouseOut2(obj){
	//obj.className = "";
	//obj.style.backgroundColor="";
	var tds =$jq(obj).find("td");
	$jq.each( tds , function() {
		$jq(this).attr("style","background-color:#f9f9f9;");
	});	
}
function iframeReflash()  {
	 location.href = "<c:url value='/lightpack/board/boardItem/listBoardItemView.do?boardId=${board.boardId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>";  
};

(function($){	 
	/* window risize 시 Contaner 높이 조절 */
	resizeContanerHeight = function(){
		var docHeight = 0;
		var adjustHeight = 20;		
		var $lefMenu;
		var $Container	= $('#container');
		
		if(isLayout){
			// iframe 안에서 호출된 경우
			if(bbsIsIframe>0){
				docHeight = $(parent).height();
				
				$lefMenu = $("#leftMenu", parent.document);
				var leftMenuPostion = $lefMenu.offset();
				if($lefMenu.length > 0) {
					$lefMenu.height(docHeight - leftMenuPostion.top);
				}
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( docHeight - $Container.offset().top - adjustHeight );
				// 부모 iframe 높이 조절 - 부모 윈도우 높이로 조절
				bbsIframe.height(docHeight-17);				
			}else{
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( $(window).height() - $Container.offset().top - adjustHeight );
			}	
		}
		
		if(bbsLayout != null){
			bbsLayout.resizeAll();	
		} 
	}
	
	/* Contaner & iframe 높이 조절 */
	setContanerHeight = function() {
		var docHeight = 0;
		var adjustHeight = 20;
		var $lefMenu;
		var $Container	= $('#container');
		
		// layout 존재하므로 layout destroy 함수 호출시
		if(isLayout){
			// iframe 안에서 호출된 경우
			if(bbsIsIframe>0){
				docHeight = $(parent).height();
				$lefMenu = $("#leftMenu", parent.document);
				var leftMenuPostion = $lefMenu.offset();
				if($lefMenu.length > 0) {
					$lefMenu.height(docHeight - leftMenuPostion.top)
					.css({overflowY:"auto",overflowX:"hidden"});
				}
				
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( docHeight - $Container.offset().top - adjustHeight );
				// 부모 iframe 높이 조절 - 부모 윈도우 높이로 조절
				bbsIframe.height(docHeight-19);
			}else{
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( $(window).height() - $Container.offset().top - adjustHeight );
			}			
			
		}else{ // layout 없으므로 layout 함수 호출시
			// iframe 안에서 호출된 경우
			if(bbsIsIframe>0){
				$lefMenu = $("#leftMenu", parent.document);
				$lefMenu.css({overflowY:"",overflowX:""});
				bbsIframe.height($(document).height());
			}
		
		}
		
		if(bbsLayout != null){
			bbsLayout.resizeAll();	
		}
		
	}
	
	/* layout 가로 분할보기 */
	createLayoutVertical = function() {
		
		isLayout = true;
		layoutType = "v";
		
		$Container = $('#container');
		$Container.height( $(window).height() - $Container.offset().top - 20 );
		
		$("#layoutContent").show();
		
		$ListPane = $('#listDiv');
		if($ListPane.hasClass("ui-layout-north")){
			$ListPane.removeClass("ui-layout-north");
		}
		
		$ListPane.addClass("ui-layout-west");
		
		bbsLayout = $('#container').layout({			
			togglerLength_open:50,
			west__size: .50,
			west__minSize: 200,
			west__slidable:	false
			
		});
		
		setContanerHeight();

	};
	
	/* layout 세로 분할보기 */
	createLayoutHorizental = function() {
		
		isLayout = true;
		layoutType = "h";
		
		$Container = $('#container');
		$Container.height( $(window).height() - $Container.offset().top - 20);
		
		$("#layoutContent").show();
		
		$ListPane = $('#listDiv');
		if($ListPane.hasClass("ui-layout-west")){
			$ListPane.removeClass("ui-layout-west");
		}
		$ListPane.addClass("ui-layout-north");
		
		bbsLayout = $('#container').layout({			 
			togglerLength_open:50,
			north__size: .50,
			north__minSize: 200,
			north__slidable: false
		});
		
		setContanerHeight();
	};
	
	/* layout destroy */
	destroyLayout = function(action) {
		
		isLayout = false;
		layoutType = "";
		
		$Container = $('#container');
		$Container.layout().destroy();
		
		$("#layoutContent").empty();
		
		$ListPane = $('#listDiv');
		if($ListPane.hasClass("ui-layout-west")){
			$ListPane.removeClass("ui-layout-west");
		}else if($ListPane.hasClass("ui-layout-north")){
			$ListPane.removeClass("ui-layout-north");
		}
		bbsLayout = null;
		setContanerHeight();
		
		if(action == "v"){
			createLayoutVertical();
		}else if(action == "h"){
			createLayoutHorizental();
		}
		
	};
	
	$(window).bind("unload", function() {
		bbsIframe = null;
		bbsIsIframe = null;
		isLayout = null;
		bbsLayout = null;
		layoutType = null;
		
		contextRoot = null;
		ZeroClipboard = null;
		cafeCkeditorConfig = null;
		fullCkeditorConfig = null;
		
		iKEP = null;
		iKEPLang = null;
		
		simpleCkeditorConfig = null;
		$jq = null;
		jQuery = null;
	});
	
	moveBoardItem = function(result) {
		var orgBoardId = result.orgBoardId,
			targetBoardId = result.targetBoardId;

		var itemIds = new Array();
		$("#searchBoardItemForm input[name=checkboxBoardItem]:checked").each(function(index) { 
			itemIds.push($(this).val()); 
		});

		$.get("<c:url value='/lightpack/board/boardItem/moveBoardItem.do'/>?orgBoardId="+orgBoardId +"&targetBoardId="+targetBoardId+"&itemIds="+itemIds)
		.success(function(data) {
			location.href="<c:url value='/lightpack/board/boardItem/listBoardItemView.do?boardParentId=${board.boardParentId}&boardId=${board.boardId}'/>";
		})
		.error(function(event, request, settings) { alert("error"); }); 		
	}; 
	
	$(document).ready(function() {   
		<c:if test="${board.rss eq 1}">initRssClipBoard();</c:if> 
		
		if(window.parent.menuMark != null) { 
			window.parent.menuMark("${board.boardId}");
		}
		 
		viewPopUpProfile = function(targetUserId) { 
			var pageURL = "<c:url value='/support/profile/getProfilePopup.do' />?targetUserId=" + targetUserId;
			iKEP.popupOpen( pageURL , {width:820, height:370, callback:function(result) {} });
		};
		
		sort = function(sortColumn, sortType) { 
			$("#searchBoardItemForm input[name=actionType]").val("sort");
			$("#searchBoardItemForm input[name=sortColumn]").val(sortColumn); 
			$("#searchBoardItemForm input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');	 
			$("#searchBoardItemButton").click();
		}; 
		
		//뷰모드 체인지 함수
		changeViewMode = function(viewMode) {
			$.cookie("viewMode", viewMode, {path:location.pathname});
			
			$("#searchBoardItemForm input[name=actionType]").val("changeView");
			$("#searchBoardItemForm").submit(); 
			return false; 
		}; 
		
		$("#searchBoardItemButton").click(function() {   
			$("#searchBoardItemForm input[name=actionType]").val("search");			
			$("#searchBoardItemForm").submit(); 
			return false; 
		});
		
		$("#checkboxAllBoardItem").click(function() { 
			$("#searchBoardItemForm input[name=checkboxBoardItem]").attr("checked", $(this).is(":checked"));  
		});  

		//alert($("#isSystemAdmin", parent.document).val());
		
		if($("#isSystemAdmin", parent.document).val()=="0"){
			$("#adminMultiDeleteBoardItemButton, #adminMultiDeleteBoardItemButton1").hide();
		}else{
			$("#adminMultiDeleteBoardItemButton, #adminMultiDeleteBoardItemButton1").show();
		}
		
		$("#adminMultiDeleteBoardItemButton, #adminMultiDeleteBoardItemButton1").click(function() {  
			var itemIds = new Array();
			
			$("#searchBoardItemForm input[name=checkboxBoardItem]:checked").each(function(index) { 
				itemIds.push($(this).val()); 
			});			 
			
			if(confirm("선택한 게시글을 삭제하시겠습니까?")) {
				$("#searchBoardItemForm").ajaxLoadStart(); 
				
				$.post("<c:url value='/lightpack/board/boardItem/adminMultiDeleteBoardItem.do'/>", {"boardId" : "${board.boardId}", "itemId" : itemIds.toString(), "popupYn" : ${popupYn}}) 
				.success(function(data) {  
					$("#searchBoardItemButton").click();
				})
			}  
		});   
		
		$("#searchBoardItemForm select[name=pagePerRecord]").change(function() { 
			$("#searchBoardItemForm input[name=actionType]").val("pagePerRecord"); 
			$("#searchBoardItemForm").submit(); 
			return false;
		});  
		
		/* iframe 구성여부 확인 */
		bbsIframe = $(parent.document).find("iframe[name=contentIframe]");
		bbsIsIframe = bbsIframe.length;
		
		/* 기본 layout 설정 여부 */ 
		isLayout = false;	
		layoutType = "n";		
		
		/* 윈도우 resize 이벤트 */
		$(window).bind("resize", resizeContanerHeight);		
		
		/* 가로 분할 보기 */
		$("#btnLayoutVertical").click(function(event) { 
			$("input[name=layoutType]").val("layoutVertical");
			$.get('<c:url value="/lightpack/board/boardItem/updateUserConfigLayout.do?boardId=${board.boardId}&layoutType=layoutVertical"/>');
			
			if(!isLayout){
				createLayoutVertical();
			}else{
				if(layoutType == "h"){
					var layoutContent = $("#layoutContent").html();
					destroyLayout("v");
					$("#layoutContent").html(layoutContent);
				}
			}
		});
		
		/* 세로 분할 보기 */
		$("#btnLayoutHorizental").click(function(event) {
			$("input[name=layoutType]").val("layoutHorizental");
			$.get('<c:url value="/lightpack/board/boardItem/updateUserConfigLayout.do?boardId=${board.boardId}&layoutType=layoutHorizental"/>');
			
			if(!isLayout){
				createLayoutHorizental();
			}else{
				if(layoutType == "v"){
					var layoutContent = $("#layoutContent").html();
					destroyLayout("h");
					$("#layoutContent").html(layoutContent);
				}
			}
		});
		
		/* 분할 보기 해제 */
		$("#btnLayoutNormal").click(function(event) { 
			$("input[name=layoutType]").val("layoutNormal");
			$.get('<c:url value="/lightpack/board/boardItem/updateUserConfigLayout.do?boardId=${board.boardId}&layoutType=layoutNormal"/>');
			
			destroyLayout();
		});		
		
		$("a.boardItem").click(function() {
			$("*.boardItemLine").removeClass("bgSelected");			
			$(this).parents("*.boardItemLine").addClass("bgSelected");
			 if(!isLayout) {  
				<c:choose>
					<c:when test="${popupYn}"> 
						iKEP.popupOpen($(this).attr("href")+ "&popupYn=true&searchConditionString=${searchConditionString}&popupYn=${popupYn}", {width:1000, height:600, resizable:false, callback:function(result) {
							location.href = "<c:url value='/lightpack/board/boardItem/listBoardItemView.do'/>?boardId=" + result[0] + "&searchConditionString=" +  result[1] + "&popupYn=${popupYn}";  
						}}, "param");   
						
						return false;	
					</c:when> 
					<c:otherwise>
						return true;
					</c:otherwise>
				</c:choose>
				
			 } else if(layoutType == "v") {
				$("#layoutContent").html("");  
				$("#layoutContent").load($(this).attr("href") + "&layoutType=layoutVertical"); 
				return false;
				
			} else if(layoutType == "h") {  
				$("#layoutContent").html(""); 
				$("#layoutContent").load($(this).attr("href") + "&layoutType=layoutHorizental");
				return false;
			} else {  
				return true;
			}
			 
			return false;			
		}); 
 
		if($("input[name=layoutType]").val() ==  "layoutVertical") { 
			$("#btnLayoutVertical").click();
			
		} else if($("input[name=layoutType]").val() ==  "layoutHorizental") {
			$("#btnLayoutHorizental").click();
			
		} else {
			
		}  
		
		$("a.boardItem").each(function() {
			$(this).attr("title", $(this).html()); 
		});		
		
		iKEP.showGallery($("a.image-gallery"));
		
		$("*.boardItem", "span.deletedItem").css("color", "red");		
		
		//<c:if test="${not empty itemId}">  
		//	iKEP.popupOpen("<c:url value='/lightpack/board/boardItem/readBoardItemView.do?boardId=${board.boardId}&itemId=${itemId}&searchConditionString=searchConditionString&popupYn=${popupYn}'/>", {width:1000, height:600, resizable:false, callback:function(result) {
		//		location.href = "<c:url value='/lightpack/board/boardItem/listBoardItemView.do'/>?boardId=" + result[0] + "&searchConditionString=" +  result[1] + "&popupYn=${popupYn}";  
		//	}}, "param");			 
		//</c:if>
		$("#moveBoardItemButton").click(function() {  
			var $sel = $jq("input[name=checkboxBoardItem]:checked");
			if($sel.length <1) {
				alert('<ikep4j:message pre='${preMessage}' key='select' />');
				return;	
			}
			
			iKEP.showDialog({
				title: "<ikep4j:message pre="${preMessage}" key="moveBoardItem" />",
				url: "<c:url value='/lightpack/board/boardItem/viewBoardTree.do' />?boardParentId=${board.boardParentId}&orgBoardId=${board.boardId}",
				modal: true,
				width: 400,
				height: 300,
				callback : moveBoardItem
			});
		});	
	});
})(jQuery);
//-->
</script>
<!--mainContents Start-->
<div id="tagResult">
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1>  
</form>	
</div>
	