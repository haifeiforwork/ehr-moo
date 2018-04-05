<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preHeader"  value="ui.support.note.readNoteView.header" /> 
<c:set var="preDetail"  value="ui.support.note.readNoteView.detail" />
<c:set var="preButton"  value="ui.support.common.button" /> 
<c:set var="preMessage" value="message.support.common.note" /> 
<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>  
<%-- 메시지 관련 Prefix 선언 End --%>
<c:set var="portal" value="${sessionScope['ikep.portal']}" /> 
<script type="text/javascript">
<!--  
(function($){	 		
	resizeHeight = function(){
		if (layoutType == "layoutVertical") {
			var $Container	= $('#container');
			var $NoteContents= $('#layoutContent');
			var $Contents= $('#contents');

			$NoteContents.height( $Container.height() - 2);
			$Contents.height( $NoteContents.height() - 95);
		}
	}

	$(document).ready(function() { 
		resizeHeight();
		$("#contentsLayout").ajaxLoadComplete();
		
		iKEP.setContentImageRendering("#noteContent");

		$("#noteShareBtn").click(function(event) { 

	   	 	var $noteShareBox = $("#noteShareBox");

	   		if($noteShareBox.css("display") == 'none') {
	   		 	$noteShareBox.show();
	   	 	} else {
	   		 	$noteShareBox.hide();
	   	 	}
		});

		$("#noteFolderBtn").click(function(event) { 

	   	 	var $noteFolderBox = $("#noteFolderBox");

	   		if($noteFolderBox.css("display") == 'none') {
	   		 	$noteFolderBox.show();
	   	 	} else {
	   		 	$noteFolderBox.hide();
	   	 	}
		});

		$jq('#noteShareCloseBtn').click(function(event) { 
			$jq("#noteShareBox").hide();
		});

		$jq('#noteFolderCloseBtn').click(function(event) { 
			$jq("#noteFolderBox").hide();
		});

		$("#noteShareBox ul").click(function(event) {
			var $anchor = $(event.target);
			if(event.target.tagName.toLowerCase() == "a") {
				$("#noteShareBox").hide();
				
				var shareType = $anchor.attr("type");
				$("#noteShareParameter input[name=shareType]").val(shareType);
				
				var dHeight = 270;
				if (shareType == 'W') dHeight = 300;
				var showTitle = event.target.innerText + "<ikep4j:message pre="${preDetail}" key="shareTitle" />";
				dialog = new iKEP.showDialog({
		    	    title 	: showTitle,
		    	    url 	: iKEP.getContextRoot() + "/support/note/shareNoteForm.do?" + $jq("#noteShareParameter").serialize(),
		    	    modal 	: true,
		    	    resizable: false, 
		    	    width : 730,
		    	    height : dHeight,
		    	    callback : function(result) {		
						if(result == "OK") {
						}
		    	    },
		    	    scroll : "no" 
		    	}); 						
				
			}
		});

		$("#noteFolderBox ul").click(function(event) {
			var $anchor = $(event.target);
			if(event.target.tagName.toLowerCase() == "a") {
				if(confirm("<ikep4j:message pre="${preMessage}" key="moveItem" />")) {
					$jq("#noteFolderBox").hide();
					$("#tagResult").ajaxLoadStart(); 
					var folderId = $anchor.attr("value");
					$jq.ajax({
						url : "<c:url value='/support/note/moveFolderAjax.do'/>",
						data : {folderId:folderId, noteId:${note.noteId}},
						type : "post",
						dataType : "html",
						success : function(result) {
							parent.location.href = "<c:url value='/support/note/noteView.do'/>?folderId=${noteFolder.folderId}&noteId=${note.noteId}"; 
						}
					});	
				} 
			}
		});

		$("#logicalDeleteNoteButton").click(function() { 
			if(confirm("<ikep4j:message key="message.ikep4.common.confirm.delete" />")) {
				$("#tagResult").ajaxLoadStart(); 
				//location.href="<c:url value='/support/note/logicalDeleteNote.do?folderId=${noteFolder.folderId}&noteId=${note.noteId}&searchConditionString=${searchConditionString}'/>";  
				
				$jq.ajax({
					url : "<c:url value='/support/note/logicalDeleteNoteAjax.do'/>",
					data : {noteId:${note.noteId}},
					type : "post",
					dataType : "html",
					success : function(result) {	
						parent.location.href = "<c:url value='/support/note/noteView.do'/>?folderId=${noteFolder.folderId}&noteId=${note.noteId}&searchConditionString=${searchConditionString}'/>"; 
					}
				});
			} 
			
			return false; 			
		}); 

		$("#physicalDeleteNoteButton").click(function() { 
			if(confirm("<ikep4j:message key="message.ikep4.common.confirm.delete" />")) {
				$("#tagResult").ajaxLoadStart(); 
				//location.href="<c:url value='/support/note/physicalDeleteNote.do?folderId=${noteFolder.folderId}&noteId=${note.noteId}&searchConditionString=${searchConditionString}'/>";  

				$jq.ajax({
					url : "<c:url value='/support/note/physicalDeleteNoteAjax.do'/>",
					data : {noteId:${note.noteId}},
					type : "post",
					dataType : "html",
					success : function(result) {	
						parent.location.href = "<c:url value='/support/note/noteView.do'/>?folderId=${noteFolder.folderId}&searchConditionString=${searchConditionString}'/>"; 
					}
				});
			} 
			
			return false; 			
		}); 

		$("#restorationNoteButton").click(function() { 
			if(confirm("<ikep4j:message pre="${preMessage}" key="restorationItem" />")) {
				$("#tagResult").ajaxLoadStart(); 
				//location.href="<c:url value='/support/note/restorationNote.do?folderId=${noteFolder.folderId}&noteId=${note.noteId}&searchConditionString=${searchConditionString}'/>";

				$jq.ajax({
					url : "<c:url value='/support/note/restorationNoteAjax.do'/>",
					data : {noteId:${note.noteId}},
					type : "post",
					dataType : "html",
					success : function(result) {	
						parent.location.href = "<c:url value='/support/note/noteView.do'/>?folderId=${noteFolder.folderId}&searchConditionString=${searchConditionString}'/>"; 
					}
				});
			} 
			
			return false; 			
		}); 
		
		$("a.note_flag").click(function() {
			var $el = $jq(this);
			$el.wrap('<div/>').ajaxLoadStart("button");
			$jq.post('<c:url value="/support/note/checkImportant.do" />', {noteId:${note.noteId}})
		    	.success(function(result) { 
		    		$el.toggleClass("select"); 
		    		if(window.parent.notePriorityCountChange != null) {
						window.parent.notePriorityCountChange($el.hasClass("select"));
		    		}
		    	})    
				.complete(function() { $el.unwrap().ajaxLoadComplete(); }); 
		});
		
	    var uploaderOptions = {
		   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
		   isUpdate : false 
		}; 

	    var fileController = new iKEP.FileController(null, "#fileDownload", uploaderOptions);
	});    

})(jQuery); 
//-->
</script> 		
<form id="noteShareParameter" action="null"> 
	<input name="folderId" 	value="${note.folderId}" type="hidden"/> 
	<input name="noteId"  	value="${note.noteId}" type="hidden"/> 
	<input name="title"  	value="${note.title}" type="hidden"/> 
	<input name="shareType" value="" type="hidden"/> 
</form>
<c:if test="${!searchResult.emptyRecord}">
    <div class="corner_RoundBox07">	
		<div id="layoutContent">	
			<div id="tagResult" class="note_List note_autoScrollHeight">
				<div id="tagTitle" class="title_box">
					<h2>${note.title}</h2>
					<c:if test="${note.registerId eq user.userId}">	
						<c:if test="${note.noteDelete eq 0}">
							<div class="note_btn">
								<%-- <a class="note_share1" id="noteShareBtn" title="<ikep4j:message pre='${preButton}' key='share' />"></a> --%>
								<a class="note_edit" title="<ikep4j:message pre='${preButton}' key='update' />" href="<c:url value='/support/note/updateNoteView.do?folderId=${noteFolder.folderId}&amp;noteId=${note.noteId}&amp;searchConditionString=${searchConditionString}'/>"></a>
								<a class="note_share" id="noteFolderBtn" title="<ikep4j:message pre='${preButton}' key='move' />"></a>
								<a class="note_delete" id="logicalDeleteNoteButton" title="<ikep4j:message pre='${preButton}' key='delete' />"></a>
							</div>
							
							<div id="noteShareBox" class="tabMenu_1" style="display:none;top:28px; right:33px;"><%--메뉴길이와 위치에 따라 width, left 조절--%>
								<a id="noteShareCloseBtn" class="btnClose" href="#a"><img src="<c:url value="/base/images/icon/ic_close_layer.gif" />" alt="close"></a>
								<ul>				
									<li><span class="icon board"></span><a href="#a" type="B" value="${noteFolder.folderId}"><ikep4j:message pre='${preDetail}' key='board' /></a>&nbsp;&nbsp;&nbsp;&nbsp;</li>
									<li><span class="icon workspace"></span><a href="#a" type="W" value="${noteFolder.folderId}"><ikep4j:message pre='${preDetail}' key='workspace' /></a>&nbsp;&nbsp;&nbsp;&nbsp;</li>
									<li><span class="icon knowledge"></span><a href="#a" type="K" value="${noteFolder.folderId}"><ikep4j:message pre='${preDetail}' key='knowledge' /></a>&nbsp;&nbsp;&nbsp;&nbsp;</li>
								</ul>								
							</div>
										
							<div id="noteFolderBox" class="tabMenu_1" style="display:none;top:28px; right:33px; width:250px;"><%--메뉴길이와 위치에 따라 width, left 조절--%>
								<a id="noteFolderCloseBtn" class="btnClose" href="#a"><img src="<c:url value="/base/images/icon/ic_close_layer.gif" />" alt="close"></a>
								<ul>				
								<c:choose>
									<c:when test="${empty folderList}">
										<li>&nbsp;<ikep4j:message pre='${preMessage}' key='notMoveFolder' />&nbsp;</li>
									</c:when>
									<c:otherwise>
										<c:forEach var="noteFolder" items="${folderList}" varStatus="status">
										<li style="text-overflow:ellipsis; white-space:nowrap; overflow:hidden;"><span class="note_color_box ${noteFolder.color}"></span><a href="#a" type="U" value="${noteFolder.folderId}" title="${noteFolder.folderName}">${noteFolder.folderName}</a>&nbsp;&nbsp;&nbsp;&nbsp;</li>
										</c:forEach>
									</c:otherwise>
								</c:choose>
								</ul>
							</div>									
						</c:if>
						<c:if test="${note.noteDelete eq 1}">
							<div class="blockButton">
							<ul> 
								<li><a class="button" id="physicalDeleteNoteButton" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
								<li><a class="button" id="restorationNoteButton" href="#a"><span><ikep4j:message pre='${preButton}' key='restoration'/></span></a></li>
							</ul>
							</div>
						</c:if> 			
					</c:if> 
				</div>	
				<div class="subbox">
					<div id="divNote" class="subbox_con">
						<div><span><ikep4j:message pre='${preDetail}' key='category'/> :</span> ${note.folderName}</div>
						<div><span><ikep4j:message pre='${preDetail}' key='create'/> :</span> <ikep4j:timezone date="${note.registDate}" pattern="yyyy.MM.dd HH:mm"/></div>
						<div><span><ikep4j:message pre='${preDetail}' key='update'/> :</span> <ikep4j:timezone date="${note.updateDate}" pattern="yyyy.MM.dd HH:mm"/></div>
					</div>
					<c:if test="${note.registerId eq user.userId}">
						<c:if test="${note.noteDelete eq 0}">
							<a href="#a" class="note_flag <c:if test="${note.priority eq 0}">select</c:if>"
								title="<c:if test="${note.priority eq 0}"><ikep4j:message pre='${preDetail}' key='setPriority'/></c:if><c:if test="${note.priority eq 1}"><ikep4j:message pre='${preDetail}' key='offPriority'/></c:if>"
							>
								<span><ikep4j:message pre='${preDetail}' key='priority'/></span>
							</a>
						</c:if>
					</c:if>
					<div class="subbox_shadow"></div>
				</div>					
				
				<div id="contents" class="blockMsgbox_2">				
					<c:if test="${note.attachFileCount > 0}"><div id="fileDownload"></div></c:if>				
					<h3 class="none"></h3>
					<div class="blockMsgbox_con border_none">
						<spring:htmlEscape defaultHtmlEscape="false"> 
							<spring:bind path="note.contents">
								<p >${status.value}</p>
							</spring:bind> 
				        </spring:htmlEscape> 
					</div>
				</div>
			</div>
		</div>
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
	</div>
</c:if> 	