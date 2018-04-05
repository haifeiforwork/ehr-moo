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
			var tempWorkplace = "";
			$("#searchBoardItemForm input[name=workplaceCheck]:checked").each(function(index) { 
				if(tempWorkplace == ""){
					tempWorkplace = $(this).val();
				}else{
					tempWorkplace = tempWorkplace+","+$(this).val();
				}
			});	
			$("#searchWorkplace").val(tempWorkplace);
			$("#searchBoardItemForm").submit(); 
			return false; 
		});
		
		$("#checkboxAllBoardItem").click(function() { 
			$("#searchBoardItemForm input[name=checkboxBoardItem]").attr("checked", $(this).is(":checked"));  
		});  

		
		/* $("#searchBoardItemForm input[name=workplaceCheck]").click(function() { 
			if($(this).val() == ""){
				if($('input:checkbox[name=workplaceCheck][value=""]').is(":checked") == true){
				$('input:checkbox[name=workplaceCheck]').attr("checked", false);
				$('input:checkbox[name=workplaceCheck][value=""]').attr("checked", true);
				}else{
					$('input:checkbox[name=workplaceCheck][value=""]').attr("checked", false);
				}
			}else{
				$('input:checkbox[name=workplaceCheck][value=""]').attr("checked", false);
			}
		});   */
		
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
		
		$("#readerListViewButton").click( function() {  
			var url = "<c:url value='/lightpack/board/boardItem/listBoardView.do?boardItemId=${board.boardId}'/>";
			
			iKEP.popupOpen(url , {width:700, height:500});
		});
		
		<c:if test="${(board.boardId=='100006240370' && boardAdminRole) || (board.boardId=='100006240370' && permission.isSystemAdmin)}">
		var tmpWorkplaces = "${searchCondition.searchWorkplace}".split(",");
		
		for(var i=0;i<tmpWorkplaces.length;i++){
			$('input:checkbox[name=workplaceCheck][value='+tmpWorkplaces[i]+']').attr("checked", true);
		}
		if(tmpWorkplaces == ""){
			$('input:checkbox[name=workplaceCheck]').attr("checked", true);
		}
		</c:if>
		//<c:if test="${board.boardId=='100006240370'}">
		//	if($jq.cookie("IKEP_POPUP_NOTICE") != 'none') {
		//		window.open("<c:url value='/support/popup/popupNotice.do'/>",'notice','width=605,height=268,location=0,menubar=0,scrollbars=0,toolbar=0,resizable=0');
		//	}
		//</c:if>
	});
})(jQuery);
//-->
</script>
<!--mainContents Start-->
<div id="tagResult">
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1>  
<!--blockListTable Start-->
<form id="searchBoardItemForm" method="post" action="<c:url value='/lightpack/board/boardItem/listBoardItemView.do' />">  
	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<spring:bind path="searchCondition.boardId">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>  
	<spring:bind path="searchCondition.layoutType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>   
	<spring:bind path="searchCondition.actionType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>      
	<spring:bind path="searchCondition.popupYn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	<input name="admin" type="hidden" value="${permission.isSystemAdmin}"/>      
	<input name="searchWorkplace" id="searchWorkplace" type="hidden" value="" />
	<!--tableTop Start-->  
	<div class="tableTop">
		<div class="tableTop_bgR"></div> 
		<h2>
			<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">${board.boardName}</c:when>
				<c:otherwise>${board.boardEnglishName}</c:otherwise>
			</c:choose>
		</h2> 
		<div class="listInfo">  
			<spring:bind path="searchCondition.pagePerRecord">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<c:forEach var="code" items="${boardCode.pageNumList}">
					<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
				</c:forEach> 
				</select> 
			</spring:bind>
			<!-- div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)</div-->
			<div class="totalNum"> <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
		</div>	  
		<spring:bind path="searchCondition.viewMode">
		<div class="listView_1"> 
			<div class="none"><ikep4j:message pre='${preSearch}' key='${status.expression}' post="webstandard"/></div>
			<ul> 
				<c:choose>
				    <c:when test="${status.value eq '0'}">       
				       <li><a name="viewModeTypeButton" onclick="changeViewMode('0');" href="#a"><img src="<c:url value='/base/images/icon/ic_view_list_on.gif' />" alt="" title="<ikep4j:message pre='${preSearch}' key='viewMode.list.on' />" /></a></li>
				    </c:when>
				    <c:otherwise>
				      <li><a name="viewModeTypeButton" onclick="changeViewMode('0');" href="#a"><img src="<c:url value='/base/images/icon/ic_view_list.gif' />" alt="" title="<ikep4j:message pre='${preSearch}' key='viewMode.list' />" /></a></li>
				    </c:otherwise> 
				</c:choose> 
				<!--  
				<c:choose>
				    <c:when test="${status.value eq '1'}">
				       <li><a name="viewModeTypeButton" onclick="changeViewMode('1');" href="#a"><img src="<c:url value='/base/images/icon/ic_view_sumlist_on.gif' />" alt="" title="<ikep4j:message pre='${preSearch}' key='viewMode.simple.on' />" /></a></li>
				    </c:when>
				    <c:otherwise>
				       <li><a name="viewModeTypeButton" onclick="changeViewMode('1');" href="#a"><img src="<c:url value='/base/images/icon/ic_view_sumlist.gif' />" alt="" title="<ikep4j:message pre='${preSearch}' key='viewMode.simple' />" /></a></li>
				    </c:otherwise>  
				</c:choose> 				
				<c:choose>
				   <c:when test="${status.value eq '2'}">
				       <li><a name="viewModeTypeButton" onclick="changeViewMode('2');" href="#a"><img src="<c:url value='/base/images/icon/ic_view_summary_on.gif' />" alt="" title="<ikep4j:message pre='${preSearch}' key='viewMode.summary.on' />" /></a></li>
				    </c:when>
				    <c:otherwise>
				       <li><a name="viewModeTypeButton" onclick="changeViewMode('2');" href="#a"><img src="<c:url value='/base/images/icon/ic_view_summary.gif' />" alt="" title="<ikep4j:message pre='${preSearch}' key='viewMode.summary' />" /></a></li>
				    </c:otherwise> 
				</c:choose> 
				-->
				<c:choose>
				    <c:when test="${status.value eq '3'}">
				       <li><a name="viewModeTypeButton" onclick="changeViewMode('3');" href="#a"><img src="<c:url value='/base/images/icon/ic_view_gallery_on.gif' />" alt="" title="<ikep4j:message pre='${preSearch}' key='viewMode.gallery.on' />" /></a></li>
				    </c:when>
				    <c:otherwise>
				       <li><a name="viewModeTypeButton" onclick="changeViewMode('3');" href="#a"><img src="<c:url value='/base/images/icon/ic_view_gallery.gif' />" alt="" title="<ikep4j:message pre='${preSearch}' key='viewMode.gallery' />" /></a></li>
				    </c:otherwise>  
				</c:choose>  
			</ul> 
		</div>		
		</spring:bind>  							
		<div class="listView_2">
			<div class="none"><ikep4j:message pre='${preSearch}' key='layoutType' post="webstandard"/></div>
			<ul>  
				<li><a id="btnLayoutNormal"     href="#a"><img src="<c:url value='/base/images/icon/ic_splitter_d.gif' />" alt="" title="<ikep4j:message pre='${preSearch}' key='layoutNormal' />" /></a></li>
				<li><a id="btnLayoutVertical"   href="#a"><img src="<c:url value='/base/images/icon/ic_splitter_v.gif' />" alt="" title="<ikep4j:message pre='${preSearch}' key='layoutVertical' />" /></a></li>
				<li><a id="btnLayoutHorizental" href="#a"><img src="<c:url value='/base/images/icon/ic_splitter_h.gif' />" alt="" title="<ikep4j:message pre='${preSearch}' key='layoutHorizental' />" /></a></li> 
			</ul>
		</div>  
		<div class="tableSearch">
			<c:if test="${(board.boardId=='100006240370' && boardAdminRole) || (board.boardId=='100006240370' && permission.isSystemAdmin)}">
				<input name="workplaceCheck" class="checkbox" title="checkbox" type="checkbox" value="전체" />전체
           		<input name="workplaceCheck" class="checkbox" title="checkbox" type="checkbox" value="본사"  />본사
           		<input name="workplaceCheck" class="checkbox" title="checkbox" type="checkbox" value="진주"  />진주
           		<input name="workplaceCheck" class="checkbox" title="checkbox" type="checkbox" value="울산"  />울산
           		<input name="workplaceCheck" class="checkbox" title="checkbox" type="checkbox" value="대구"  />대구
			</c:if>
			<c:if test="${board.wordHead == 1 && board.boardId=='100006240370'}">
				<select id="wordId" name="wordId">
					<option value=""><ikep4j:message pre='${preSearch}' key='pageCount.info' /></option>
					<c:forEach var="category" items="${boardItemCategoryList}">
						<option value="${category.categoryId}" <c:if test="${category.categoryId == searchCondition.wordId}">selected</c:if>>${category.categoryName}</option>
					</c:forEach>
				</select> 
			</c:if>
			<spring:bind path="searchCondition.searchColumn">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<option value="title" <c:if test="${'title' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='${status.expression}' post='title'/></option>
					<option value="registerName" <c:if test="${'registerName' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='${status.expression}' post='registerName'/></option>
				</select>		
			</spring:bind>		
			<spring:bind path="searchCondition.searchWord">  					
				<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
			</spring:bind>
			<a href="#a" id="searchBoardItemButton" class="ic_search"><span><ikep4j:message pre='${preSearch}' key='search'/></span></a>  
		</div>			
		<div class="clear"></div>
	</div>
	<!--//tableTop End--> 
	<!--//pageTitle End-->  
	<c:choose>
		<c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:set var="boardDescription" value="${board.boardDescription}"/></c:when>
		<c:otherwise><c:set var="boardDescription" value="${board.boardEnglishDescription}"/></c:otherwise>
	</c:choose>
	
	<c:if test="${not empty boardDescription}">
	<div class="tableDescription" style="font-size:12px;font-weight:bold;color:black">
		<table summary="게시판설명">
			<caption></caption>
			<tbody>
				<tr>
					<td class="valign_top">
						<div class="floatLeft"><span class="ic_desc"></span></div>
					</td>
					<% pageContext.setAttribute("newLineChar", "\n"); %>  
					<td style="font-size:12px;font-weight:bold;color:black">${fn:replace(boardDescription, newLineChar, '<br/>')}</td>
				</tr>
			</tbody>
		</table>
	</div>
    <!--blockButton Start-->
    <div class="blockButton"> 
		<ul>
			<c:if test="${permission.isWritePermission}"><%--관리자와 쓰기권한자인 경우 등록 버튼을 활성화 시킨다. --%>
				<li><a id='createBoardItemButton' class='button' href='<c:url value='/lightpack/board/boardItem/createBoardItemView.do?boardId=${searchCondition.boardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>'><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
			</c:if> 
			<c:if test="${permission.isSystemAdmin}">
				<c:if test="${board.move == 1}">
				<!-- <li><a id="moveBoardItemButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='move'/></span></a></li> -->
				</c:if>	
				<li><a id="adminMultiDeleteBoardItemButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
			</c:if> 
		</ul>
	</div>	 
	</c:if>
	<!--Layout Start-->
	<div id="container">
		<!--List Layout Start-->
		<div id="listDiv"> 
			<c:choose>
				<c:when test="${searchCondition.viewMode eq '0'}">
					<div class="blockListTable"> 
					<%@ include file="/WEB-INF/view/lightpack/board/boardItem/listTypeBoardItemView.jsp"%> 
				</c:when>
				<c:when test="${searchCondition.viewMode eq '1'}">
					<div class="blockListTable summaryView"> 
					<%@ include file="/WEB-INF/view/lightpack/board/boardItem/simpleTypeBoardItemView.jsp"%>
				</c:when> 				
				<c:when test="${searchCondition.viewMode eq '2'}">
					<div class="blockListTable summaryView"> 
					<%@ include file="/WEB-INF/view/lightpack/board/boardItem/summaryTypeBoardItemView.jsp"%>
				</c:when>
				<c:when test="${searchCondition.viewMode eq '3'}">
					<div class="blockListTable galleryView"> 
					<%@ include file="/WEB-INF/view/lightpack/board/boardItem/galleryTypeBoardItemView.jsp"%>
				</c:when>  
			</c:choose>
				<!--Page Numbur Start--> 
				<spring:bind path="searchCondition.pageIndex">
				<ikep4j:pagination searchButtonId="searchBoardItemButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>  
				<!--//Page Numbur End-->
			</div>
				
			<!--blockButton Start-->
			<div class="blockButton"> 
				<c:if test="${board.rss eq 1}">
					<div class="buttonicon" id="feedDiv" >
						<input type="hidden" id="feedMetaType" name="feedMetaType" value="BD"/>		
						<input type="hidden" id="feedMetaId" name="feedMetaId" value="${searchCondition.boardId}"/>	
						<input type="hidden" id="feedLocale" name="feedLocale" value="${user.localeCode}"/>	
						<img src="<c:url value='/base/images/icon/ic_rss.gif'/>"  alt="rss" id="rssFeedBtn" width="20" height="20"/>
						<img src="<c:url value='/base/images/icon/ic_atom.gif'/>" alt="atom" id="atomFeedBtn" width="20" height="20"/>
					</div>	
				</c:if>					
				<ul>
					<c:if test="${permission.isWritePermission}">
						<li><a id='createBoardItemButton1' class='button' href='<c:url value='/lightpack/board/boardItem/createBoardItemView.do?boardId=${searchCondition.boardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>'><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
					</c:if>
					<c:if test="${permission.isSystemAdmin}">
						<c:if test="${board.move == 1}">
						<!-- <li><a id="moveBoardItemButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='move'/></span></a></li> -->
						</c:if>	
						<li><a id="adminMultiDeleteBoardItemButton1" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
					</c:if> 
					<c:if test="${board.boardId=='100010083357' || board.boardId=='100010089350' || board.boardId=='100010089362'}">
						<c:if test="${boardAdminRole || permission.isSystemAdmin}">
							<li><a id="readerListViewButton" class="button" href="#a"><span>조회현황</span></a></li>
						</c:if>
					</c:if>
				</ul>
			</div> 
			<!--//blockButton End-->  
		</div>  
		<!--List Layout End--> 
		<!--View Layout Start-->
		<div class="ui-layout-center none" id="viewDiv">
			<div id="layoutContent"></div>
		</div>
		<!--View Layout End-->		
	</div>
<!--//Layout End-->		
</form>	
</div>
	