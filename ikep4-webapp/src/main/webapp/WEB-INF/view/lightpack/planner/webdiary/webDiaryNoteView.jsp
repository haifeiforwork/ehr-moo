<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="prefix" value="ui.support.note.portalNote"/>
<c:set var="preDetail"  value="ui.support.note.readNoteView.detail" />
<c:set var="preButton"  value="ui.support.common.button" /> 

<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript"> 
//<!--	
(function($) {
	$jq(document).ready(function() {
		// 목록으로 되돌아갈 수 있는 버튼 비 활성화
		$jq("#noteBackBtn").hide();

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
				
				var dHeight = 240;
				if (shareType == 'W') dHeight = 270;
				var showTitle = event.target.innerText + "<ikep4j:message pre="${preDetail}" key="shareTitle" />";
				dialog = new iKEP.showDialog({
		    	    title 	: showTitle,
		    	    url 	: iKEP.getContextRoot() + "/support/note/shareNoteForm.do?" + $jq("#noteShareParameter").serialize(),
		    	    modal 	: true,
		    	    resizable: false, 
		    	    width : 600,
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
				if(confirm("<ikep4j:message pre="${prefix}" key="moveItem" />")) {
					$jq("#noteFolderBox").hide();
					
					var folderId = $anchor.attr("value");
					$jq.ajax({
						url : "<c:url value='/support/note/moveFolderAjax.do'/>",
						data : {folderId:folderId, noteId:${note.noteId}},
						type : "post",
						dataType : "html",
						success : function(result) {
							getNoteView(${note.noteId})
						}
					});
				} 
			}
		});

		$("a.note_flag").click(function() {
			var $el = $jq(this);
			$el.wrap('<div/>').ajaxLoadStart("button");
			$jq.post('<c:url value="/support/note/checkImportant.do" />', {noteId:${note.noteId}})
		    	.success(function(result) { $el.toggleClass("select"); })
				.complete(function() { $el.unwrap().ajaxLoadComplete(); }); 
		});

		$("#updateNoteButton").click(function() {
			//alert(${note.noteId});
			$jq.ajax({
				url : "<c:url value='/support/note/webDiaryNoteUpdate.do'/>",
				data : {noteId:${note.noteId}},
				type : "post",
				//loadingElement : {container : $jq(".note_text")},
				loadingElement : {container : $jq("#noteDetail")},
				dataType : "html",
				success : function(result) {
					$jq("#noteViewDiv").html(result);
				}
			});
		}); 

		$("#logicalDeleteNoteButton").click(function() {
			if(confirm("<ikep4j:message key="message.ikep4.common.confirm.delete" />")) {
				$jq.ajax({
					url : "<c:url value='/support/note/logicalDeleteNoteAjax.do'/>",
					data : {noteId:${note.noteId}},
					type : "post",
					//loadingElement : {container : $jq(".note_text")},
					loadingElement : {container : $jq("#noteDetail")},
					dataType : "html",
					success : function(result) {	
						//divNoteListShow();
						divNoteDetailHide(); // 삭제 후 노트 내용 보기 Hide
					}
				});
			}
		}); 

		$("#physicalDeleteNoteButton").click(function() { 
			if(confirm("<ikep4j:message key="message.ikep4.common.confirm.delete" />")) {
				$jq.ajax({
					url : "<c:url value='/support/note/physicalDeleteNoteAjax.do'/>",
					data : {noteId:${note.noteId}},
					type : "post",
					//loadingElement : {container : $jq(".note_text")},
					loadingElement : {container : $jq("#noteDetail")},
					dataType : "html",
					success : function(result) {			
						//divNoteListShow()
						divNoteDetailHide(); // 삭제 후 노트 내용 보기 Hide
					}
				});				
			} 		
		}); 

		$("#restorationNoteButton").click(function() { 
			if(confirm("<ikep4j:message pre="${prefix}" key="restorationItem" />")) {
				$jq.ajax({
					url : "<c:url value='/support/note/restorationNoteAjax.do'/>",
					data : {noteId:${note.noteId}},
					type : "post",
					//loadingElement : {container : $jq(".note_text")},
					loadingElement : {container : $jq("#noteDetail")},
					dataType : "html",
					success : function(result) {
						//divNoteListShow()
						divNoteDetailHide(); // 삭제 후 노트 내용 보기 Hide
					}
				});  
			} 		
		}); 
		
	    var uploaderOptions = {
		   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
		   isUpdate : false 
		}; 

	    var fileController = new iKEP.FileController(null, "#fileDownload", uploaderOptions);
	});
})(jQuery)
//-->
</script>
<form id="noteShareParameter" action="null"> 
	<input name="folderId" 	value="${note.folderId}" type="hidden"/> 
	<input name="noteId"  	value="${note.noteId}" type="hidden"/> 
	<input name="title"  	value="${note.title}" type="hidden"/> 
	<input name="shareType" value="" type="hidden"/> 
</form>
<div class="title_box" style="background:none">
	<h2>${note.title}</h2>
	<c:if test="${note.registerId eq user.userId}">	
		<c:if test="${note.noteDelete eq 0}">
			<div class="note_btn">
				<%-- <a class="note_share1" id="noteShareBtn" title="<ikep4j:message pre='${preButton}' key='share' />"></a> --%>
				<a class="note_edit" id="updateNoteButton" title="<ikep4j:message pre='${preButton}' key='update' />" ></a>
				<a class="note_share" id="noteFolderBtn" title="<ikep4j:message pre='${preButton}' key='move' />"></a>
				<a class="note_delete" id="logicalDeleteNoteButton" title="<ikep4j:message pre='${preButton}' key='delete' />"></a>
			</div>
			
			<div id="noteShareBox" class="tabMenu_1" style="display:none;top:28px; right:33px;"><!--메뉴길이와 위치에 따라 width, left 조절-->
				<a id="noteShareCloseBtn" class="btnClose" href="#a"><img src="<c:url value="/base/images/icon/ic_close_layer.gif" />" alt="close"></a>
				<ul>				
					<li><span class="icon board"></span><a href="#a" type="B" value="${noteFolder.folderId}"><ikep4j:message pre='${preDetail}' key='board' /></a>&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li><span class="icon workspace"></span><a href="#a" type="W" value="${noteFolder.folderId}"><ikep4j:message pre='${preDetail}' key='workspace' /></a>&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li><span class="icon knowledge"></span><a href="#a" type="K" value="${noteFolder.folderId}"><ikep4j:message pre='${preDetail}' key='knowledge' /></a>&nbsp;&nbsp;&nbsp;&nbsp;</li>
				</ul>
			</div>
						
			<div id="noteFolderBox" class="tabMenu_1" style="display:none;top:28px; right:33px; width:250px;"><!--메뉴길이와 위치에 따라 width, left 조절-->
				<a id="noteFolderCloseBtn" class="btnClose" href="#a"><img src="<c:url value="/base/images/icon/ic_close_layer.gif" />" alt="close"></a>
				<ul>				
				<c:choose>
					<c:when test="${empty folderList}">
						<li>&nbsp;<ikep4j:message pre='${prefix}' key='notMoveFolder' />&nbsp;</li>
					</c:when>
					<c:otherwise>
						<c:forEach var="noteFolder" items="${folderList}" varStatus="status">
						<li style="text-overflow:ellipsis; white-space:nowrap; overflow:hidden;"><span class="note_color_box ${noteFolder.color}"></span><a href="#a" type="U" value="${noteFolder.folderId}" title="${noteFolder.folderName}">${noteFolder.folderName}</a></li>
						</c:forEach>
					</c:otherwise>
				</c:choose>
				</ul>
			</div>
					
		</c:if>
		<c:if test="${note.noteDelete eq 1}">
		<div class="blockButton">
		<ul> 
			<li><a class="button" id="physicalDeleteNoteButton" href="#a"><span><ikep4j:message pre='${prefix}' key='delete'/></span></a></li>
			<li><a class="button" id="restorationNoteButton" href="#a"><span><ikep4j:message pre='${prefix}' key='restoration'/></span></a></li>
		</ul>
		</div>
		</c:if> 			
	</c:if> 
	<div class="clear"></div>
</div>

<div class="subbox">
	<div id="divNote" class="subbox_con">
		<div><span><ikep4j:message pre='${prefix}' key='category'/> :</span> ${note.folderName}</div>
		<div><span><ikep4j:message pre='${prefix}' key='create'/> :</span> <ikep4j:timezone date="${note.registDate}" pattern="yyyy.MM.dd HH:mm"/></div>
		<div><span><ikep4j:message pre='${prefix}' key='update'/> :</span> <ikep4j:timezone date="${note.updateDate}" pattern="yyyy.MM.dd HH:mm"/></div>
	</div>
	<c:if test="${note.registerId eq user.userId}">	
		<c:if test="${note.noteDelete eq 0}">
			<a href="#a" class="note_flag <c:if test="${note.priority eq 0}">select</c:if>"
				title="<c:if test="${note.priority eq 0}"><ikep4j:message pre='${preDetail}' key='setPriority'/></c:if><c:if test="${note.priority eq 1}"><ikep4j:message pre='${preDetail}' key='offPriority'/></c:if>"
			>
				<span><ikep4j:message pre='${prefix}' key='priority'/></span>
			</a>
		</c:if> 
	</c:if>
	<div class="subbox_shadow"></div>
</div>
			
<div id="contents" class="blockMsgbox_2" style="background:none;">

	<c:if test="${note.attachFileCount > 0}"><div id="fileDownload" class="filedown_ic"></div></c:if>
	
	<h3 class="none"><ikep4j:message pre='${prefix}' key='contents'/></h3>
	<div class="blockMsgbox_con border_none" style="overflow: hidden;">
		<!-- <div class="blockMsgbox_con_title">${note.title}</div> -->
		
		<spring:htmlEscape defaultHtmlEscape="false"> 
			<spring:bind path="note.contents">
				<p >${status.value}</p>
			</spring:bind> 
        </spring:htmlEscape> 
	</div>
</div>