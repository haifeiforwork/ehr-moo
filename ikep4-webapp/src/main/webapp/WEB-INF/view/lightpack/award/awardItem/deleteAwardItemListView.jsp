<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.lightpack.award.awardItem.listAwardView.header" /> 
<c:set var="preList"    value="ui.lightpack.award.awardItem.listAwardView.list" /> 
<c:set var="preButton"  value="ui.lightpack.common.button" />
<c:set var="preMessage" value="message.lightpack.common.awardItem" />
<c:set var="preSearch"  value="ui.ikep4.common.searchCondition" /> 

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<%-- 메시지 관련 Prefix 선언 End --%>   
<script type="text/javascript" src="<c:url value='/base/js/zeroclipaward/ZeroClipaward.js'/>"></script>
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
	 location.href = "<c:url value='/lightpack/award/awardItem/deleteAwardItemListView.do?searchConditionString=${searchConditionString}'/>";  
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
		ZeroClipaward = null;
		cafeCkeditorConfig = null;
		fullCkeditorConfig = null;
		
		iKEP = null;
		iKEPLang = null;
		
		simpleCkeditorConfig = null;
		$jq = null;
		jQuery = null;
	});
	
	moveAwardItem = function(result) {
		var orgAwardId = result.orgAwardId,
			targetAwardId = result.targetAwardId;

		var itemIds = new Array();
		$("#searchAwardItemForm input[name=checkboxAwardItem]:checked").each(function(index) { 
			itemIds.push($(this).val()); 
		});

		$.get("<c:url value='/lightpack/award/awardItem/moveAwardItem.do'/>?orgAwardId="+orgAwardId +"&targetAwardId="+targetAwardId+"&itemIds="+itemIds)
		.success(function(data) {
			location.href="<c:url value='/lightpack/award/awardItem/listAwardItemView.do?awardParentId=${award.awardParentId}&awardId=${award.awardId}'/>";
		})
		.error(function(event, request, settings) { alert("error"); }); 		
	}; 
	
	$(document).ready(function() {   
		<c:if test="${award.rss eq 1}">initRssClipAward();</c:if> 
		
		if(window.parent.menuMark != null) { 
			window.parent.menuMark("${award.awardId}");
		}
		 
		viewPopUpProfile = function(targetUserId) { 
			var pageURL = "<c:url value='/support/profile/getProfilePopup.do' />?targetUserId=" + targetUserId;
			iKEP.popupOpen( pageURL , {width:820, height:370, callback:function(result) {} });
		};
		
		sort = function(sortColumn, sortType) { 
			$("#searchAwardItemForm input[name=actionType]").val("sort");
			$("#searchAwardItemForm input[name=sortColumn]").val(sortColumn); 
			$("#searchAwardItemForm input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');	 
			$("#searchAwardItemButton").click();
		}; 
		
		//뷰모드 체인지 함수
		changeViewMode = function(viewMode) {
			$.cookie("viewMode", viewMode, {path:location.pathname});
			
			$("#searchAwardItemForm input[name=actionType]").val("changeView");
			$("#searchAwardItemForm").submit(); 
			return false; 
		}; 
		
		$("#searchAwardItemButton").click(function() {   
			$("#searchAwardItemForm input[name=actionType]").val("search");			
			$("#searchAwardItemForm").submit(); 
			return false; 
		});
		
		$("#checkboxAllAwardItem").click(function() { 
			$("#searchAwardItemForm input[name=checkboxAwardItem]").attr("checked", $(this).is(":checked"));  
		});  

		//alert($("#isSystemAdmin", parent.document).val());
		
		if($("#isSystemAdmin", parent.document).val()=="0"){
			$("#adminMultiDeleteAwardItemButton, #adminMultiDeleteAwardItemButton1").hide();
		}else{
			$("#adminMultiDeleteAwardItemButton, #adminMultiDeleteAwardItemButton1").show();
		}
		
		$("#adminMultiDeleteAwardItemButton, #adminMultiDeleteAwardItemButton1").click(function() {  
			var itemIds = new Array();
			
			$("#searchAwardItemForm input[name=checkboxAwardItem]:checked").each(function(index) { 
				itemIds.push($(this).val()); 
			});			 
			
			if(confirm("선택한 게시글을 삭제하시겠습니까?")) {
				$("#searchAwardItemForm").ajaxLoadStart(); 
				
				$.post("<c:url value='/lightpack/award/awardItem/adminMultiDeleteAwardItem.do'/>", {"awardId" : "${award.awardId}", "itemId" : itemIds.toString(), "popupYn" : ${popupYn}}) 
				.success(function(data) {  
					$("#searchAwardItemButton").click();
				})
			}  
		});   
		
		$("#searchAwardItemForm select[name=pagePerRecord]").change(function() { 
			$("#searchAwardItemForm input[name=actionType]").val("pagePerRecord"); 
			$("#searchAwardItemForm").submit(); 
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
			$.get('<c:url value="/lightpack/award/awardItem/updateUserConfigLayout.do?awardId=${award.awardId}&layoutType=layoutVertical"/>');
			
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
			$.get('<c:url value="/lightpack/award/awardItem/updateUserConfigLayout.do?awardId=${award.awardId}&layoutType=layoutHorizental"/>');
			
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
			$.get('<c:url value="/lightpack/award/awardItem/updateUserConfigLayout.do?awardId=${award.awardId}&layoutType=layoutNormal"/>');
			
			destroyLayout();
		});		
		
		$("a.awardItem").click(function() {
			$("*.awardItemLine").removeClass("bgSelected");			
			$(this).parents("*.awardItemLine").addClass("bgSelected");
			 if(!isLayout) {  
				<c:choose>
					<c:when test="${popupYn}"> 
						iKEP.popupOpen($(this).attr("href")+ "&popupYn=true&searchConditionString=${searchConditionString}&popupYn=${popupYn}", {width:1000, height:600, resizable:false, callback:function(result) {
							location.href = "<c:url value='/lightpack/award/awardItem/listAwardItemView.do'/>?awardId=" + result[0] + "&searchConditionString=" +  result[1] + "&popupYn=${popupYn}";  
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
		
		$("a.awardItem").each(function() {
			$(this).attr("title", $(this).html()); 
		});		
		
		iKEP.showGallery($("a.image-gallery"));
		
		$("*.awardItem", "span.deletedItem").css("color", "red");		
		
		//<c:if test="${not empty itemId}">  
		//	iKEP.popupOpen("<c:url value='/lightpack/award/awardItem/readAwardItemView.do?awardId=${award.awardId}&itemId=${itemId}&searchConditionString=searchConditionString&popupYn=${popupYn}'/>", {width:1000, height:600, resizable:false, callback:function(result) {
		//		location.href = "<c:url value='/lightpack/award/awardItem/listAwardItemView.do'/>?awardId=" + result[0] + "&searchConditionString=" +  result[1] + "&popupYn=${popupYn}";  
		//	}}, "param");			 
		//</c:if>
		$("#moveAwardItemButton").click(function() {  
			var $sel = $jq("input[name=checkboxAwardItem]:checked");
			if($sel.length <1) {
				alert('<ikep4j:message pre='${preMessage}' key='select' />');
				return;	
			}
			
			iKEP.showDialog({
				title: "<ikep4j:message pre="${preMessage}" key="moveAwardItem" />",
				url: "<c:url value='/lightpack/award/awardItem/viewAwardTree.do' />?awardParentId=${award.awardParentId}&orgAwardId=${award.awardId}",
				modal: true,
				width: 400,
				height: 300,
				callback : moveAwardItem
			});
		});	
	});
})(jQuery);
//-->
</script>
<!--mainContents Start-->
<div id="tagResult">
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1>  
<!--blockListTable Start-->
<form id="searchAwardItemForm" method="post" action="<c:url value='/lightpack/award/awardItem/deleteAwardItemListView.do' />">  
	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<spring:bind path="searchCondition.awardId">
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
	<!--tableTop Start-->  
	<div class="tableTop">
		<div class="tableTop_bgR"></div> 
		<h2>
			<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">${award.awardName}</c:when>
				<c:otherwise>${award.awardEnglishName}</c:otherwise>
			</c:choose>
		</h2> 
		<div class="listInfo">  
			<spring:bind path="searchCondition.pagePerRecord">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<c:forEach var="code" items="${awardCode.pageNumList}">
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
			<c:if test="${award.wordHead == 1}">
				<select id="wordId" name="wordId">
					<option value=""><ikep4j:message pre='${preSearch}' key='pageCount.info' /></option>
					<c:forEach var="category" items="${awardItemCategoryList}">
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
			<a href="#a" id="searchAwardItemButton" class="ic_search"><span><ikep4j:message pre='${preSearch}' key='search'/></span></a>  
		</div>			
		<div class="clear"></div>
	</div>
	<!--//tableTop End--> 
	<!--//pageTitle End-->  
	<c:choose>
		<c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:set var="awardDescription" value="${award.awardDescription}"/></c:when>
		<c:otherwise><c:set var="awardDescription" value="${award.awardEnglishDescription}"/></c:otherwise>
	</c:choose>
	
	<c:if test="${not empty awardDescription}">
	<div class="tableDescription" style="font-size:12px;font-weight:bold;color:black">
		<table summary="게시판설명">
			<caption></caption>
			<tbody>
				<tr>
					<td class="valign_top">
						<div class="floatLeft"><span class="ic_desc"></span></div>
					</td>
					<% pageContext.setAttribute("newLineChar", "\n"); %>  
					<td style="font-size:12px;font-weight:bold;color:black">${fn:replace(awardDescription, newLineChar, '<br/>')}</td>
				</tr>
			</tbody>
		</table>
	</div>
    <!--blockButton Start-->
    <div class="blockButton"> 
		<ul>
			<c:if test="${permission.isWritePermission}"><%--관리자와 쓰기권한자인 경우 등록 버튼을 활성화 시킨다. --%>
				<li><a id='createAwardItemButton' class='button' href='<c:url value='/lightpack/award/awardItem/createAwardItemView.do?awardId=${searchCondition.awardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>'><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
			</c:if> 
			<c:if test="${permission.isSystemAdmin}">
				<c:if test="${award.move == 1}">
				<!-- <li><a id="moveAwardItemButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='move'/></span></a></li> -->
				</c:if>	
				<li><a id="adminMultiDeleteAwardItemButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
			</c:if> 
		</ul>
	</div>	 
	</c:if>
	<!--Layout Start-->
	<div id="container">
		<!--List Layout Start-->
		<div id="listDiv"> 
			<div class="blockListTable"> 
			<%@ include file="/WEB-INF/view/lightpack/award/awardItem/listTypeDeleteAwardItemView.jsp"%> 
				<!--Page Numbur Start--> 
				<spring:bind path="searchCondition.pageIndex">
				<ikep4j:pagination searchButtonId="searchAwardItemButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>  
				<!--//Page Numbur End-->
			</div>
				
			<!--blockButton Start-->
			<div class="blockButton"> 
				<c:if test="${award.rss eq 1}">
					<div class="buttonicon" id="feedDiv" >
						<input type="hidden" id="feedMetaType" name="feedMetaType" value="BD"/>		
						<input type="hidden" id="feedMetaId" name="feedMetaId" value="${searchCondition.awardId}"/>	
						<input type="hidden" id="feedLocale" name="feedLocale" value="${user.localeCode}"/>	
						<img src="<c:url value='/base/images/icon/ic_rss.gif'/>"  alt="rss" id="rssFeedBtn" width="20" height="20"/>
						<img src="<c:url value='/base/images/icon/ic_atom.gif'/>" alt="atom" id="atomFeedBtn" width="20" height="20"/>
					</div>	
				</c:if>					
				<ul>
					<c:if test="${permission.isWritePermission}">
						<li><a id='createAwardItemButton1' class='button' href='<c:url value='/lightpack/award/awardItem/createAwardItemView.do?awardId=${searchCondition.awardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>'><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
					</c:if>
					<c:if test="${permission.isSystemAdmin}">
						<c:if test="${award.move == 1}">
						<!-- <li><a id="moveAwardItemButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='move'/></span></a></li> -->
						</c:if>	
						<li><a id="adminMultiDeleteAwardItemButton1" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
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
	