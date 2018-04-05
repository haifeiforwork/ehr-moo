<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="prefix" value="ui.support.note.portalNote"/>
<c:set var="preMessage" value="message.support.common.note" />

<c:set var="user" value="${sessionScope['ikep.user']}" />
<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-ui-1.8.13.min.without.datepicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.ui.datepicker.customize.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/plugins.pack.js"/>"></script>
<script type="text/javascript">var contextRoot = "${pageContext.request.contextPath}";</script>
<script type="text/javascript" src="<c:url value="/base/js/etc.plugins.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/langResource/${empty user.localeCode ? 'en' : user.localeCode}.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/common.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.layout-latest.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jclock.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/support/favorite/favorite.js"/>"></script>
<script type="text/javascript"> 
//<!--	
var currentFolderId = "A";
var currentFolderType = "";	

(function($) {
	$(document).ready(function() {

		$("#portalNoteLayer").ajaxLoadComplete();
		
		$('#noteText').keypress(function(event) { 
			if(event.which == '13') {
				divNoteListShow();
			}
		});
		
		$('#noteSearchBtn').click(function(event) { 
			divNoteListShow();
		});
		
		$('#noteCloseBtn').click(function(event) { 
			$("#noteMenuBox").hide();
			portalNoteLayerHide();
		});
		
		$('#noteListBtn').click(function(event) { 

	   	 	var $noteMenuBox = $("#noteMenuBox");

	   		if($noteMenuBox.css("display") == 'none') {
	   		 	$noteMenuBox.show();
	   	 	} else {
	   		 	$noteMenuBox.hide();
	   	 	}
		});

		$('#noteBackBtn').click(function(event) { 

			divNoteDetailHide();
			divNoteListShow();
		});

		$('#noteListCloseBtn').click(function(event) { 
			$("#noteMenuBox").hide();
		});

		$("#noteMenu").click(function(event) {
			var $anchor = $(event.target);
			if(event.target.tagName.toLowerCase() == "a") {
				$("#noteText").val("");
				
				var folderId = $anchor.attr("value");
				currentFolderId = folderId;
				var folderType = $anchor.attr("type");
				currentFolderType = folderType;
				divNoteListShow();
			}
		});
		
		$('#noteCreateBtn').click(function(event) { 	
			<c:choose>
				<c:when test="${userFolderList == null || empty userFolderList}">
					var conMsg = "<ikep4j:message pre="${prefix}" key="createFolder" />";
					if(confirm(conMsg)) {
						portalNoteLayerHide();
						mainFrame.location.href = "<c:url value='/support/note/noteView.do?folderId=F'/>"; 
			    	}
				</c:when>
				<c:otherwise>				
					divNoteListHide();
					divNoteDetailHide();
							
					getNoteCreate();
					$("#noteDetail").show();	
				</c:otherwise> 
			</c:choose>
		});

		getSearchNoteList();

	});
})(jQuery);

function getSearchNoteList() {	
	$jq("#noteDiv").html("");
	$jq.ajax({
		url : "<c:url value='/support/note/portalNoteList.do'/>",
		data : {folderId: currentFolderId, searchType: currentFolderType, searchText: $jq("#noteText").val(), sortColumn: $jq("#sortColumn").val(), sortType: $jq("#sortType").val()},
		type : "post",
		loadingElement : {container : $jq(".note_body")},
		dataType : "html",
		success : function(result) {
			$jq("#noteDiv").html(result);
		}
	});
}

function getNoteCreate() {		
	$jq.ajax({
		url : "<c:url value='/support/note/portalNoteCreate.do'/>",
		data : {folderId: currentFolderId},
		type : "get",
		loadingElement : {container : $jq(".note_body")},
		dataType : "html",
		success : function(result) {
			$jq("#noteViewDiv").html(result);
		}
	});
}

function portalNoteLayerHide() {	
	$jq("#noteDiv").html("");
	$jq("#noteViewDiv").html("");
	$jq("#portalNoteLayer").hide();
}

function divNoteListShow() {	
	$jq("#noteList").show();
	$jq("#noteMenuBox").hide();	
	$jq("#noteBackBtn").hide();		
	divNoteDetailHide();

	getSearchNoteList();	
}
function divNoteListHide() {	
	$jq("#noteList").hide();
	$jq("#noteDiv").html("");
}
function divNoteDetailHide() {
	$jq("#noteDetail").hide();
	$jq("#noteViewDiv").html("");
}
//-->
</script>

<h4 class="note_head"><span>Note</span>
	<div class="note_ext_closeBtn" id="noteCloseBtn" style="cursor: pointer;"></div>
</h4>
<div class="note_body">
	<div class="note_extArrow" style="top: 162px;"></div>
	<div class="note_extSearchArea">
		<div class="note_btnContainer" id="noteListBtn">
			<a href="#a" class="ext_note" title="<ikep4j:message pre='${prefix}' key='list' />"><span class="note_icon_list"></span></a>   
		</div>
		
		<div class="layerBox none" id="noteMenuBox" style="display:none;">
			<div>
				<span class="arrow"></span>
				<div class="quicktxt_tit_personal" id="noteListCloseBtn">
					<a class="btnClose" href="#a"><img src="<c:url value='/base/images/icon/ic_close_layer.gif'/>" alt="close"></a>
				</div>
				<div id="noteMenu" style="width:200px;">
				<ul>
					<li><a href="#a" value="A"><span class="icon allNote"></span><ikep4j:message pre="${prefix}" key="noteAllListView" /></a></li>
					<li><a href="#a" value="I"><span class="icon importantNotes"></span><ikep4j:message pre="${prefix}" key="notePriorityListView" /></a></li>
					<c:forEach var="noteFolder" items="${userFolderList}" varStatus="status">
						<li style="text-overflow:ellipsis; white-space:nowrap; overflow:hidden;"><a href="#a" type="U" value="${noteFolder.folderId}" title="${noteFolder.folderName}"><span class="note_color_box ${noteFolder.color}"></span>${noteFolder.folderName}</a></li>
					</c:forEach>					
					<li><a href="#a" value="D"><span class="icon wastebasket"></span><ikep4j:message pre="${prefix}" key="noteTrashListView" /></a></li>
					<%-- <c:if test="${!empty sharedFolderList}">
						<li class="last-child"><span class="icon publicFolder"></span><ikep4j:message pre="${prefix}" key="public" />
							<c:forEach var="noteFolder" items="${sharedFolderList}" varStatus="status">
								<div class="shareMenu"><a href="#a" type="S" value="${noteFolder.folderId}" title="${noteFolder.folderName}" style="text-overflow:ellipsis; white-space:nowrap; overflow:hidden;">${noteFolder.folderName}</a></div>
							</c:forEach>
						</li>
					</c:if>
					<c:if test="${empty sharedFolderList}">
						<li class="last-child"><span class="icon publicFolder"></span><ikep4j:message pre="${prefix}" key="public" />
							<div class="shareMenu pl20"><ikep4j:message pre="${preMessage}" key="sharefolder.notExist" /></div>
						</li>
					</c:if> --%>
				</ul>
				</div>
			</div>
		</div>
		
		<div class="note_btnContainer" id="noteCreateBtn">
			<a href="#a" class="ext_note" title="<ikep4j:message pre='${prefix}' key='create' />"><span class="note_icon_add"></span><P><ikep4j:message pre='${prefix}' key='create' /></P></a>   
		</div>				
	
		<div class="note_headerSearch">
			<h2 class="none"></h2>
			<table class="note_headerSearch_sel">
				<tbody>
					<tr>
						<td>
							<input id="noteText" name="noteText" type="text">
							<input id="sortColumn" name="sortColumn" type="hidden">
							<input id="sortType" name="sortType" type="hidden">
						</td>
						<td><a class="sel_btn" href="#a" id="noteSearchBtn"><span></span></a></td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="note_btnContainer floatRight mr10" style="display:none;" id="noteBackBtn">
			<a href="#a" class="ext_note" title="<ikep4j:message pre='${prefix}' key='back' />"><span class="note_icon_arrow_back"></span></a>   
		</div>				
		<%-- 
		<div class="note_btnContainer floatRight mr10">
			<a href="#a" class="ext_note disable" title="list"><span class="note_icon_arrow_r"></span></a>   
		</div>
		<div class="note_btnContainer floatRight mr5">
			<a href="#a" class="ext_note disable" title="list"><span class="note_icon_arrow_l"></span></a>   
		</div>				
		<div class="note_btnContainer floatRight mr10">
			<a href="#a" class="ext_note" title="list"><span class="note_icon_arrow_r"></span></a>   
		</div>					
		<div class="note_btnContainer floatRight mr5">
			<a href="#a" class="ext_note" title="list"><span class="note_icon_arrow_l"></span></a>   
		</div>
		--%>
	</div>
	
	<div class="note_List portal note_autoScrollHeight" style="" id="noteList">	
		<div class="corner_RoundBox07">	
			<div id="noteDiv"></div>
			<div class="l_t_corner"></div>
			<div class="r_t_corner"></div>
		</div>	
	</div>
	
	<div class="note_List portal note_autoScrollHeight" style="display:none;" id="noteDetail">
		<div class="corner_RoundBox07">						
			<div id="noteViewDiv"></div>			
			<div class="l_t_corner"></div>
			<div class="r_t_corner"></div>
		</div>
	</div>	
</div>