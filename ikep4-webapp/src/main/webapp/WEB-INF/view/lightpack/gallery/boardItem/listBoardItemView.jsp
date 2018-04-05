<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" />
<c:set var="preHeader"  value="ui.lightpack.gallery.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="ui.lightpack.gallery.boardItem.listBoardView.list" /> 
<c:set var="preButton"  value="ui.lightpack.gallery.common.button" />
<c:set var="preMessage" value="message.lightpack.gallery.common.boardItem" />
<c:set var="preSearch"  value="ui.ikep4.common.searchCondition" /> 

<c:set var="user"   value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

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

function iframeReflash()  {
	 location.href = "<c:url value='/lightpack/gallery/boardItem/listBoardItemView.do?targetUserId=${targetUserId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>";  
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
	// 다른 사람의 프로파일로 이동
	getProfile = function() {
		document.location.href = "<c:url value='/support/profile/getProfile.do?targetUserId=${targetUserId}'/>";
	};
	
	// My Gallery 이동
	goMyGallery = function() {
		document.location.href = "<c:url value='/lightpack/gallery/boardItem/listBoardItemView.do?targetUserId=${targetUserId}&userId=${user.userId}&userLocale=${user.localeCode}'/>" ;
	};	
	// 방명록 이동
	goGuestbook = function() {
		document.location.href = "<c:url value='/support/guestbook/listGuestbook.do?targetUserId=${targetUserId}'/>" ;
	};	
	$(document).ready(function() {   
	
		//$("#divTab2").tabs({selected : 1});
		
		<c:if test="${board.rss eq 1}">initRssClipBoard();</c:if> 
		
		if(window.parent.menuMark != null) { 
			window.parent.menuMark("${board.boardId}");
		}
		 
		viewPopUpProfile = function(targetUserId) { 
			iKEP.goProfilePopupMain(targetUserId);
			//var pageURL = "<c:url value='/support/profile/getProfilePopup.do' />?targetUserId=" + targetUserId;
			//iKEP.popupOpen( pageURL , {width:820, height:370, callback:function(result) {} });
		};
		
		sort = function(sortColumn, sortType) { 
			$("#searchBoardItemForm input[name=actionType]").val("sort");
			$("#searchBoardItemForm input[name=sortColumn]").val(sortColumn); 
			$("#searchBoardItemForm input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');	 
			$("#searchBoardItemButton").click();
		}; 
		
		//뷰모드 체인지 함수
		changeViewMode = function(changeViewMode) {  
			$("#searchBoardItemForm input[name=actionType]").val("changeView");
			$("#searchBoardItemForm input[name=viewMode]").val(changeViewMode);
			$("#searchBoardItemForm").submit(); 
			return false; 
		}; 
		
		$("#searchBoardItemButton").click(function() {   
			//$("#searchBoardItemForm input[name=actionType]").val("search");
			$("#searchBoardItemForm input[name=actionType]").val("search");			
			$("#searchBoardItemForm").submit(); 
			return false; 
		});
		
		$("#checkboxAllBoardItem").click(function() { 
			$("#searchBoardItemForm input[name=checkboxBoardItem]").attr("checked", $(this).attr("checked"));  
		});  
		
		$("#adminMultiDeleteBoardItemButton").click(function() {  
			var itemIds = new Array();
			
			$("#searchBoardItemForm input[name=checkboxBoardItem]:checked").each(function(index) { 
				itemIds.push($(this).val()); 
			});			 
			
			if(confirm("<ikep4j:message pre="${preMessage}" key="deleteItem" />")) {
				$("#searchBoardItemForm").ajaxLoadStart(); 
				
				$.post("<c:url value='/lightpack/gallery/boardItem/adminMultiDeleteBoardItem.do'/>", {"targetUserId" : "${targetUserId}", "itemId" : itemIds.toString(), "popupYn" : ${popupYn}}) 
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
			$.get('<c:url value="/lightpack/gallery/boardItem/updateUserConfigLayout.do?targetUserId=${targetUserId}&layoutType=layoutVertical"/>');
			
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
			$.get('<c:url value="/lightpack/gallery/boardItem/updateUserConfigLayout.do?targetUserId=${targetUserId}&layoutType=layoutHorizental"/>');
			
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
			$.get('<c:url value="/lightpack/gallery/boardItem/updateUserConfigLayout.do?targetUserId=${targetUserId}&layoutType=layoutNormal"/>');
			
			destroyLayout();
		});		
		
		$("a.boardItem").click(function() {
			$("*.boardItemLine").removeClass("bgSelected");			
			$(this).parents("*.boardItemLine").addClass("bgSelected");
			
			 if(!isLayout) {  
				<c:choose>
					<c:when test="${popupYn}"> 
						iKEP.popupOpen($(this).attr("href")+ "&popupYn=true&searchConditionString=${searchConditionString}&popupYn=${popupYn}", {width:1000, height:600, resizable:false, callback:function(result) {
							location.href = "<c:url value='/lightpack/gallery/boardItem/listBoardItemView.do'/>?targetUserId=" + result[0] + "&searchConditionString=" +  result[1] + "&popupYn=${popupYn}";  
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
		
		parent.$jq("#contentIframe").contents().find("a.image-gallery").ikepGallery();
		//$("a.image-gallery").ikepGallery();	
			
		$("*.boardItem", "span.deletedItem").css("color", "red");		
		
		<c:if test="${not empty itemId}">  
			iKEP.popupOpen("<c:url value='/lightpack/gallery/boardItem/readBoardItemView.do?targetUserId=${targetUserId}&itemId=${itemId}&searchConditionString=searchConditionString&popupYn=${popupYn}'/>", {width:1000, height:600, resizable:false, callback:function(result) {
				location.href = "<c:url value='/lightpack/gallery/boardItem/listBoardItemView.do'/>?boardId=" + result[0] + "&searchConditionString=" +  result[1] + "&popupYn=${popupYn}";  
			}}, "param");			 
		</c:if>
		 
	});
})(jQuery);
//-->
</script>
<%-- 12.08.23 Francis Choi 무림 페이지를 탭 추가 --%>
	<%--tab Start--%>		
	<div class="iKEP_tab_menu_common">
		<ul>
			<li><a href="#a" onclick="getProfile();"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${profile.userName}"/></c:when><c:otherwise><c:out value="${profile.userEnglishName}"/></c:otherwise></c:choose><ikep4j:message pre='${preProfileMain}' key='profile.owner' /></a></li>
		</ul>	
		<ul>
			<li class="selected"><a href="#a" onclick="goMyGallery();"><ikep4j:message pre='${preProfileMain}' key='profile.photo' /></a></li>
		</ul>	
		<ul>
			<li><a href="#a" onclick="goGuestbook();"><ikep4j:message pre='${preProfileMain}' key='profile.guestbook' /></a></li>
		</ul>													
	</div>		
	<%--tab End--%>
		<%-- 12.08.23 Francis Choi 무림 페이지를 탭 추가후 주석처리 --%>
<%--div style="text-align:right; padding:0 20px 8px 0;">
	<a href="<c:url value='/support/profile/getProfile.do?targetUserId=${targetUserId}'/>"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${profile.userName}"/></c:when><c:otherwise><c:out value="${profile.userEnglishName}"/></c:otherwise></c:choose><ikep4j:message pre='${preProfileMain}' key='profile.owner' /></a>
</div--%>
		
<!--mainContents Start-->
 
<div id="tagResult">
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1>  
<!--blockListTable Start-->
<form id="searchBoardItemForm" method="post" action="<c:url value='/lightpack/gallery/boardItem/listBoardItemView.do' />">  
	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<spring:bind path="searchCondition.layoutType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>   
	<spring:bind path="searchCondition.actionType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	<spring:bind path="searchCondition.targetUserId">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>      
	<spring:bind path="searchCondition.popupYn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>   
	<spring:bind path="searchCondition.searchColumn">
	<input name="${status.expression}" type="hidden" value="title" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 	   
	<!--tableTop Start-->  
	<div class="tableTop">
		<div class="tableTop_bgR"></div> 
		<h2><ikep4j:message pre="${preHeader}" key="photo" /></h2> 
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

		<div class="tableSearch"> 
		<!-- 
			<spring:bind path="searchCondition.searchColumn">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<option value="title" <c:if test="${'title' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='${status.expression}' post='title'/></option>
					<option value="registerName" <c:if test="${'registerName' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='${status.expression}' post='registerName'/></option>
				</select>		
			</spring:bind>	
		 -->	
			<spring:bind path="searchCondition.searchWord">  					
				<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
			</spring:bind>
			<a href="#a" id="searchBoardItemButton" class="ic_search"><span><ikep4j:message pre='${preSearch}' key='search'/></span></a>  
		</div>			
		<div class="clear"></div>
	</div>

	<!--//tableTop End--> 
	<!--//pageTitle End-->  

	<!--Layout Start-->
	<div id="container">
		<!--List Layout Start-->
		<div id="listDiv"> 
			<div class="blockListTable galleryView"> 
				<%@ include file="/WEB-INF/view/lightpack/gallery/boardItem/galleryTypeBoardItemView.jsp"%>

				<!--Page Numbur Start--> 
				<spring:bind path="searchCondition.pageIndex">
				<ikep4j:pagination searchButtonId="searchBoardItemButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>  
				<!--//Page Numbur End-->				
			</div>
				
			<!--blockButton Start-->
			<div class="blockButton"> 
				<ul>
					<c:if test="${targetUserId==user.userId}">
						<li><a id='createBoardItemButton' class='button' href='<c:url value='/lightpack/gallery/boardItem/createBoardItemView.do?targetUserId=${targetUserId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>'><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
					</c:if> 
					<c:if test="${targetUserId==user.userId}">
						<li><a id="adminMultiDeleteBoardItemButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
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
	