<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.note.listNoteView.header" /> 
<c:set var="preList"    value="ui.support.note.listNoteView.list" />
<c:set var="preButton"  value="ui.support.common.button" />
<c:set var="preSearch"  value="ui.ikep4.common.searchCondition" /> 
<c:set var="preMessage" value="message.support.common.note" /> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 메시지 관련 Prefix 선언 End --%>   
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/note.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/layout/layout-note.css"/>" /> 
<script type="text/javascript" src="<c:url value='/base/js/jquery/jquery.layout-note.min.js'/>"></script>
<script type="text/javascript">
<!--   
var nextNoteId;
var noteIframe;  	 <%-- 부모 iframe --%>
var noteIsIframe = 0; <%-- iframe 존재 여부 --%>
var myLayout = null;
var layoutType = ""; <%-- layoutVertical:가로보기, layoutHorizental:세로보기 --%>

function menuSetCss(noteId) {
	$jq("#noteList").find("a.note").each(function() {
		var $anchor = $jq(this);
		if($anchor.attr("value") == noteId) {
			$anchor.trigger("click");
			return false;
		}
	});
}

(function($){		
	<%-- window risize 시 Contaner 높이 조절 --%>
	resizeContanerHeight = function(){
		if (layoutType == "layoutVertical") {
			var adjustHeight = 50;	
			var windowHeight = $jq(parent).height();
			var $lefMenu 	= $("#leftMenu", parent.document);
			$lefMenu.css("max-height",500);
			var $Container	= $('#container');
			var $NoteList	= $('#noteList');
			var $NoteContents= $('#layoutContent');
			var $Contents= $('#contents');

			if(windowHeight-130 >= $lefMenu.height()){
				noteIframe.height(windowHeight-130);
			} else {
				noteIframe.height($lefMenu.height());
			}

			if(noteIsIframe>0){
				if (noteIframe.height() > adjustHeight) {
					$Container.height( noteIframe.height() - adjustHeight );	
				}
			}else{
				$Container.height( $(window).height() - $Container.offset().top - adjustHeight );
			}

		    if ($.browser.msie) {
				$NoteList.height( $Container.height()-32 );
		    } else {
				$NoteList.height( $Container.height()-37 );
		    }
			if ($('#moreText').length>0) {
				$NoteList.height( $NoteList.height() - 48 );
			}
			$NoteContents.height( $Container.height() - 2);
			$Contents.height( $NoteContents.height() - 95);
		} else if (layoutType == "layoutHorizental") {
			var docHeight 	= $(document.body).height();
			noteIframe.height(docHeight);
			
			if(noteIsIframe>0){
				if (noteIframe.height() > adjustHeight) {
					$Container.height( noteIframe.height() - adjustHeight );	
				}
			}else{
				$Container.height( $(window).height() - $Container.offset().top - adjustHeight );
			}
		}
	}

	$(window).bind("unload", function() {
		noteIframe = null;
		noteIsIframe = null;
		myLayout = null;
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
	
	$(document).ready(function() {   		
		$("#container").ajaxLoadComplete();

		/* <c:choose>
			<c:when test="${noteFolder.folderId == 'A'}">	    	
				var curNavText = '&nbsp;&gt;&nbsp;'+'<ikep4j:message pre="ui.support.note.common.leftNoteView" key="noteAllListView" />';
				parent.callLocation(document.URL,curNavText);
			</c:when>
			<c:when test="${noteFolder.folderId == 'I'}">	    	
				var curNavText = '&nbsp;&gt;&nbsp;'+'<ikep4j:message pre="ui.support.note.common.leftNoteView" key="notePriorityListView" />';
				parent.callLocation(document.URL,curNavText);
			</c:when>
			<c:when test="${noteFolder.folderId == 'D'}">	    	
				var curNavText = '&nbsp;&gt;&nbsp;'+'<ikep4j:message pre="ui.support.note.common.leftNoteView" key="noteTrashListView" />';
				parent.callLocation(document.URL,curNavText);
			</c:when>
			<c:otherwise>
				var curNavText = '&nbsp;&gt;&nbsp;'+'${noteFolder.folderNameReplaceString}';
				parent.callLocation(document.URL,curNavText);
			</c:otherwise>			
		</c:choose> */
		
		<%-- 뷰모드 체인지 함수 --%>
		changeLayout = function(layoutType) {
			$.get('<c:url value="/support/note/updateUserConfigLayout.do?folderId=${searchCondition.folderId}&layoutType='+layoutType+'"/>')
			.success(function() { 
				$("#searchNoteForm input[name=layoutType]").val(layoutType);
				$("#searchNoteForm input[name=paramFolderId]").val('${searchCondition.folderId}');
				$("#searchNoteForm").submit(); 
				return false; 				
			});			
		}; 

		$jq('#searchWord').keypress(function(event) { 
			if(event.which == '13') {
				$("#searchNoteButton").click();
			}
		});
		
		$("#searchNoteButton").click(function() {  
			$("#searchNoteForm input[name=paramFolderId]").val('${searchCondition.folderId}');
			$("#container").ajaxLoadStart();
			$("#searchNoteForm").submit(); 
			return false; 
		});

		$jq("select[name=sortWord]").change(function() {
			var sortInfo = $("#searchNoteForm select[name=sortWord]").val().split(":");
			$("#searchNoteForm input[name=sortColumn]").val(sortInfo[0]);
			$("#searchNoteForm input[name=sortType]").val(sortInfo[1]);
			$jq("input[name=pageIndex]").val(1);
            $jq("#searchNoteButton").click();  
        });  
		
		<%-- iframe 구성여부 확인 --%>
		noteIframe = $(parent.document).find("iframe[name=contentIframe]");
		noteIsIframe = noteIframe.length;

		<%-- 윈도우 resize 이벤트 --%>
		$(window).bind("resize", resizeContanerHeight);		
		
		<%-- 가로 분할 보기 --%>
		setLayoutVertical = function() {
			layoutType = "layoutVertical";
			
			resizeContanerHeight();
			
			$("input[name=layoutType]").val("layoutVertical");
			$("#listLayout").removeClass("note_blockTop");
			$("#contentsLayout").removeClass("note_blockBottom");
			$("#listLayout").addClass("note_blockLeft");	
			$("#contentsLayout").addClass("note_blockRight");	
			if($("#listLayout").hasClass("ui-layout-north")){
				$("#listLayout").removeClass("ui-layout-north");
			}
			$("#listLayout").addClass("ui-layout-west");
			if(!$("#contentsLayout").hasClass("ui-layout-center")){
				$("#contentsLayout").addClass("ui-layout-center");	
			}

			myLayout = $("#container").layout({	
				//	enable showOverflow on west-pane so CSS popups will overlap north pane
				//	west__showOverflowOnHover: true	
				//	reference only - these options are NOT required because 'true' is the default
					resizeWithWindow:		true
				,	closable:				true	// pane can open & close
				,	resizable:				true	// when open, pane can be resized 
				,	slidable:				false	// when closed, pane can 'slide' open over other panes - closes on mouse-out
				,   togglerTip_open:	"<ikep4j:message key='ui.ikep4.common.layout.toggler.open'/>"
				,   togglerTip_closed:	"<ikep4j:message key='ui.ikep4.common.layout.toggler.close'/>"
				,   resizerTip:			"<ikep4j:message key='ui.ikep4.common.layout.resizer'/>"
				//	some pane-size settings
				,   togglerLength_open:	50
				,	west__size: 			.40		// percentage size expresses as a decimal
				,	west__minSize:			380
				,	center__minSize:		200
			});

			$jq("#layoutUL").append('<li><a onclick="changeLayout(\'layoutHorizental\');" href="#a">' +
		    	       '<img src="<c:url value="/base/images/icon/ic_splitter_h.gif"/>" alt="<ikep4j:message pre="${preSearch}" key="layoutHorizental"/>" ' +
		    	       ' title="<ikep4j:message pre="${preSearch}" key="layoutHorizental"/>" /></a></li>');
		};

		<%-- 세로 분할 보기 --%>
		setLayoutHorizental = function() {
			layoutType = "layoutHorizental";

			$("input[name=layoutType]").val("layoutHorizental");
			$("#listLayout").removeClass("note_blockLeft");
			$("#contentsLayout").removeClass("note_blockRight");
			$("#listLayout").addClass("note_blockTop");		
			$("#contentsLayout").addClass("note_blockBottom");	
			if($("#listLayout").hasClass("ui-layout-west")){
				$("#listLayout").removeClass("ui-layout-west");
			}
			$("#listLayout").addClass("ui-layout-north");
			if(!$("#contentsLayout").hasClass("ui-layout-center")){
				$("#contentsLayout").addClass("ui-layout-center");	
			}

			$jq("#layoutUL").append('<li><a onclick="changeLayout(\'layoutVertical\');" href="#a">' +
		    	       '<img src="<c:url value="/base/images/icon/ic_splitter_v.gif"/>" alt="<ikep4j:message pre="${preSearch}" key="layoutVertical"/>" ' +
		    	       ' title="<ikep4j:message pre="${preSearch}" key="layoutVertical"/>" /></a></li>');
		};

		$("#listFrame").delegate("a.note", "click", function() {	
			$("#noteShareBox").hide();
			$("#noteFolderBox").hide();
			$("*.bgWhite").removeClass("selected");			
			$(this).parents("*.bgWhite").addClass("selected");
			nextNoteId = $(this).parents("*.bgWhite").next().find("input").val();
			if (!nextNoteId) {
				nextNoteId = $(this).parents("*.bgWhite").prev().find("input").val();
			}
			if(layoutType == "layoutVertical") {
				$("#contentsLayout").ajaxLoadStart();
				//$("#contentsLayout").html("");  
				$("#contentsLayout").load($(this).attr("href") + "&layoutType=layoutVertical"); 
				return false;				
			} else if(layoutType == "layoutHorizental") {  
				$("#contentsLayout").ajaxLoadStart();
				//$("#contentsLayout").html(""); 
				$("#contentsLayout").load($(this).attr("href") + "&layoutType=layoutHorizental");
				return false;
			} else {  
				return true;
			}
			 
			return false;			
		});

		if($("input[name=layoutType]").val() ==  "layoutVertical") { 
			setLayoutVertical();		
		} else if($("input[name=layoutType]").val() ==  "layoutHorizental") {
			setLayoutHorizental();
		} 

		var selNoteId = "";
		<c:if test="${not empty noteId}">
		$jq("#noteList").find("a.note").each(function() {
			var $anchor = $jq(this);
			if($anchor.attr("value") == "${noteId}") {
				selNoteId = "${noteId}";
				return false;
			}
		});		
		</c:if>
		
		if (selNoteId == ""){
			var firstLoadNote = $("div.note_box_con_tl::eq(0) a", $("#noteList").html()).attr("value");
			//var firstLoadNote = $("#noteList").find("input[name=noteId]::eq(0)").val();
			if(firstLoadNote) {							
				menuSetCss(firstLoadNote);
			}			
		} else {
			menuSetCss(selNoteId);
		}
	});
})(jQuery);

function listMoreAjax(totalCount){	
	var viewCount = $jq("#listFrame tr").size();
	if(totalCount <= viewCount){
		return;	
	}
	
	ajaxListAction(totalCount);
}

var pageIndex = 1;
function ajaxListAction(totalCount){
	pageIndex = pageIndex+1;
	$jq("input[name=pageIndex]").val(pageIndex);
	$jq.ajax({
		url : "<c:url value='/support/note/listMore.do'/>",
		data : $jq("#searchNoteForm").serialize(),
		type : "post",
		loadingElement : {container : $jq(".noteTable")},
		dataType : "html",
		success : function(result) {
			$jq("#listFrame").append(result); 
			
			var viewCount = $jq("#listFrame tr").size();

			if(totalCount <= viewCount){
				$jq('#moreText').text('<ikep4j:message key="ui.ikep4.common.noData" />');
			}
		}
	});
}

//-->
</script>
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1>	
<form id="searchNoteForm" method="post" action="<c:url value='/support/note/listNoteView.do' />">  
	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	<spring:bind path="searchCondition.layoutType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	<spring:bind path="searchCondition.searchType" >  
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	<spring:bind path="searchCondition.pagePerRecord" >  
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	<spring:bind path="searchCondition.pageIndex" >  
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	<spring:bind path="searchCondition.paramFolderId" >  
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>

	<div class="blockSearch mb10">
		<div class="corner_RoundBox13">
			<div class="tableSearch">
				<spring:bind path="searchCondition.searchWord">  					
					<input id="${status.expression}" name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="40" />
				</spring:bind>
				<a href="#a" id="searchNoteButton" class="ic_search"><span><ikep4j:message pre='${preSearch}' key='search'/></span></a>
			</div>	
			<div class="clear"></div>
		</div>
	</div>	
	
	<div id="container" class="note_blockAll h100">
		<div id="listLayout" class="note_blockLeft">
			<div class="corner_RoundBox07">	
				<div class="search_box">
					<h2>
					<c:choose>
						<c:when test="${noteFolder.folderId eq 'A'}">
						<ikep4j:message pre="${preHeader}" key="noteAllListView" />
						</c:when>
						<c:when test="${noteFolder.folderId eq 'I'}">
						<ikep4j:message pre="${preHeader}" key="notePriorityListView" />
						</c:when>
						<c:when test="${noteFolder.folderId eq 'D'}">
						<ikep4j:message pre="${preHeader}" key="noteTrashListView" />
						</c:when>
						<c:otherwise>
						${noteFolder.folderName}
						</c:otherwise> 
					</c:choose>
					</h2>
					<div class="listView_2">
						<div class="none"><ikep4j:message pre="${preButton}" key="toggle" /></div>
						<ul id="layoutUL"></ul>
					</div>
					<div class="tableSearch mr0">
					<spring:bind path="searchCondition.sortWord">
					<select name="${status.expression}" style="width:120px;">
						<option value="REGIST_DATE:DESC" <c:if test="${'REGIST_DATE:DESC' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='registDate.desc' /></option>
					    <option value="REGIST_DATE:ASC" <c:if test="${'REGIST_DATE:ASC' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='registDate.asc' /></option>
					    <option value="TITLE:ASC" <c:if test="${'TITLE:ASC' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='title.asc' /></option>
						<option value="TITLE:DESC" <c:if test="${'TITLE:DESC' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='title.desc' /></option>
					</select>	
					</spring:bind>
					</div>
					<div class="clear"></div>
				</div>
	
				<div id="noteList" class="noteTable <c:if test="${searchCondition.layoutType eq 'layoutHorizental'}">list</c:if><c:if test="${searchCondition.layoutType eq 'layoutVertical'}">summary</c:if>">
					<table summary="List">
						<caption></caption>
						<c:choose>
						    <c:when test="${searchResult.emptyRecord}">
								<tr>
									<td class="emptyRecord"><ikep4j:message key="ui.ikep4.common.noData" /></td> 
								</tr>				        
						    </c:when>
						    <c:otherwise>
								<colgroup>
									<col style="width:80px;">
									<col style="width:*;">
								</colgroup>
						    	<tbody id="listFrame">
									<c:choose>
										<c:when test="${searchCondition.layoutType eq 'layoutVertical'}">
										<%@ include file="/WEB-INF/view/support/note/summaryTypeNoteView.jsp"%>
										</c:when>
										<c:when test="${searchCondition.layoutType eq 'layoutHorizental'}">
										<%@ include file="/WEB-INF/view/support/note/listTypeNoteView.jsp"%> 
										</c:when>
									</c:choose>
								</tbody>			      
						    </c:otherwise> 
						</c:choose>		
					</table>
				</div>
		
				<c:if test="${searchResult.recordCount > 10}">
					<div class="blockButton_3"> 
						<a class="button_3" href="#a" onclick="listMoreAjax(${searchResult.recordCount});return false;"><span id="moreText"><ikep4j:message pre='${preButton}' key='listMore'/> <img src="<c:url value="/base/images/icon/ic_more_ar.gif"/>" alt="more" /></span></a>				
					</div>
				</c:if>	
				<div class="l_t_corner"></div>
				<div class="r_t_corner"></div>
			</div>
		</div>
		<div id="contentsLayout" class="note_blockRight"></div>
	</div>
</form>