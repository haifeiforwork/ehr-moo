<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="prefix" value="ui.support.note.portalNote"/>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/note.css"/>" />
<link rel="stylesheet" href="<c:url value="/base/js/wceditor/css/editor.css"/>"/>
<script type="text/javascript" src="<c:url value="/base/js/wceditor/js/lang.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/wceditor/js/editor.js"/>"></script>
    
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
<!--
//activeX editor 사용여부
var useActXEditor = "N";

(function($){
	
	/* editor 초기화  */
	initEditorSet = function() {
		// ActiveX Editor 사용 여부가 Y인 경우
	    if(useActXEditor == "Y"){
			// 브라우저가 ie인 경우
			if ($.browser.msie) {
				// div 높이, 넓이 세팅
				var cssObj = {
			      'height' : '250px',
			      'width' : '100%'
			    };
				$('#editorDiv').css(cssObj);
				
				// hidden 필드 추가(contents)
				iKEP.createActiveXEditor("#editorDiv","${user.localeCode}","#contents",0);
				// ie 세팅
				$('input[name="msie"]').val('1');
			}else{
				wceditorMiniNoteToolbar.height = "170px";
				wceditorMiniNoteToolbar.miniMode = true;
				wceditorMiniNoteToolbar.conversionMode = [];
				
			    // wceditor 초기화
	            $("#contents").wceditor(wceditorMiniNoteToolbar);
			    
				// ckeditor 초기화.
				//$("#contents").ckeditor($.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
				// ie 이외 브라우저 값 세팅
				$('input[name="msie"]').val('0');
			}
	    }else{
	    	wceditorMiniNoteToolbar.height = "170px";
	    	wceditorMiniNoteToolbar.miniMode = true;
	    	wceditorMiniNoteToolbar.conversionMode = [];
	        // wceditor 초기화
	        $("#contents").wceditor(wceditorMiniNoteToolbar);
	        
	    	// ckeditor 초기화.
			//$("#contents").ckeditor($.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />11" }));
			// ie 이외 브라우저 값 세팅
			$('input[name="msie"]').val('0');
	    }
	};
	
	$(document).ready(function() { 

		// editor 초기화
		initEditorSet();
		
		$("a.saveNoteButton").click(function() {
			$("input[name=noteDelete]").val(0);
			$("#noteForm").trigger("submit");
		});
		
		$("a.cancelButton").click(function() {
			<c:if test="${viewMode eq 'C'}">
			divNoteDetailHide();
			divNoteListShow();	
			</c:if>
			<c:if test="${viewMode eq 'U'}">
			getNoteView($("input[name=noteId]").val());
			</c:if>
		});

		new iKEP.Validator("#noteForm", {   
			rules  : {
				title     : {required : true, rangelength : [1, 100] }
			},
			messages : {
				title     : {direction : "bottom",    required : "<ikep4j:message key="NotEmpty.note.title" />", rangelength : "<ikep4j:message key="Size.note.title" />"}
			},   
			
		    submitHandler : function(form) { 
		    	// ActiveX Editor 사용 여부가 Y인 경우
		    	if(useActXEditor != "Y" || !$jq.browser.msie){
		    		// 내용저장
		    		WCEditor.saveContent();
		    		//ekeditor 데이타 업데이트
		    		//var editor = $jq('#noteForm :input[name=contents]').ckeditorGet(); 
					//editor.updateElement();
					//에디터 내 이미지 파일 링크 정보 세팅
					createEditorFileLink("noteForm");
		    	}
				
		    	var conMsg = "<ikep4j:message pre="${prefix}" key="createItem" />";
				<c:if test="${viewMode eq 'U'}">conMsg = "<ikep4j:message pre="${prefix}" key="updateItem" />";</c:if>
				fileController.upload(function(isSuccess, files) {
					if(isSuccess === true) { 
						if($("input[name=priorityDummy]").is(":checked")) {
							$("input[name=priority]").val("1");
						} else {
							$("input[name=priority]").val("0");
						} 
						
						//에디터 감추기
						if(useActXEditor == "Y" && $jq.browser.msie){
				    		//태그프리 선택 탭을 디자인으로 변경 후 저장한다.
				    		var tweTab = document.twe.ActiveTab;
				    		if ( tweTab != 1 ) {
				    			document.twe.ActiveTab = 1;
				    		}
				    		//태그프리 Mime 데이타 세팅
				    		var tweBody = document.twe.MimeValue();
				    		$jq("input[name='contents']").val(tweBody);
				    		$jq("#twe").css("visibility","hidden");
						}
						
						$jq.ajax({
							url : "<c:url value='/support/note/createNoteAjax.do'/>",
							data : $jq("#noteForm").serialize(),
							type : "post",
							//loadingElement : {container : $jq(".note_body")},
							dataType : "html",
							success : function(result) {
								$jq("#noteViewDiv").html("");					
								getNoteView(result);
							}
						});
					}
				});
		    }
		}); 

		var uploaderOptions = {
		 	<c:if test="${empty fileDataListJson}">files : [],</c:if> 
		 	<c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if> 
	    	isUpdate : true ,
	    	onLoad : function() {
	    		iKEP.iFrameContentResize();
	    	}
	    };  
        
	    var fileController = new iKEP.FileController("#noteForm", "#fileUploadArea", uploaderOptions);

	 	// ActiveX Editor 사용 여부가 Y인 경우
	    if(useActXEditor == "Y"){
		    if (!$.browser.msie) {
		    	//에디터 로딩 후 현재 페이지 높이 세팅 - iframe 높이 조절
		    	//iKEP.iFrameContentResize();
				$("input[name=title]").focus();
	    	}
	    }else{
	    	//에디터 로딩 후 현재 페이지 높이 세팅 - iframe 높이 조절
	    	//iKEP.iFrameContentResize();
			$("input[name=title]").focus();
	    }
		 
	});
})(jQuery);  
//-->
</script>
<script language="JScript" for="twe" event="OnControlInit()">	
	$jq("input[name=title]").focus();
</script>
<form id="editorFileUploadParameter" action="null"> 
	<input name="folderId"  value="${note.folderId}" type="hidden"/> 
	<input name="interceptorKey"  value="support.note" type="hidden"/> 
</form> 

<div class="title_box" style="height: 35px;">
	<!-- 
	<h2>
		<c:if test="${viewMode eq 'C'}"><ikep4j:message key="ui.support.note.createNoteView.header.pageTitle" /></c:if>
		<c:if test="${viewMode eq 'U'}"><ikep4j:message key="ui.support.note.updateNoteView.header.pageTitle" /></c:if>
	</h2>
	-->
	<div class="note_btn">
		<a class="button saveNoteButton" href="#a"><span><ikep4j:message pre='${prefix}' key='save'/></span></a>
		<a class="button cancelButton" href="#a"><span><ikep4j:message pre='${prefix}' key='cancel'/></span></a>
	</div>
	<div class="clear"></div>
</div>
	
<div class="blockDetail">
<form id="noteForm" name="noteForm" method="post">
	<spring:bind path="note.noteId">
	<input name="${status.expression}" type="hidden" value="${status.value}" /> 
	</spring:bind>
	<input name="msie"        			type="hidden" value="0" />	
	<input name="noteDelete"            type="hidden" />	
	<input name="priority"            	type="hidden" />	
	<table summary="게시판등록">
		<caption></caption>
		<colgroup>
			<col width="18%" />
			<col width="82%" />
		</colgroup>
		<tbody>
			<tr>
				<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='title' /></th>
				<td> 
					<div>
					<spring:bind path="note.folderId">
					<select name="${status.expression}" class="inputbox" style="width:100px;">
					<c:forEach var="folder" items="${folderList}">
						<option value="${folder.folderId}"
						<c:choose>
							<c:when test="${not empty note.folderId && not empty note.folderName}">	    	
								<c:if test="${noteFolder.folderId eq folder.folderId}">selected="selected"</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${folder.defaultFlag eq 1}">selected="selected"</c:if>
							</c:otherwise>			
						</c:choose>
						 >
							${folder.folderName}
						</option>
					</c:forEach>
					</select>
					</spring:bind>
					&nbsp;
					<spring:bind path="note.title">
					<input 
						name="${status.expression}" 
						type="text" 
						class="inputbox w33"
						value="${status.value}" 
						title="<ikep4j:message pre='${prefix}' key='${status.expression}' />"
						/> 
						<c:forEach items="${status.errorMessages}" var="error">
					    	<label for="${status.expression}" class="serverError"> ${error}</label>
					    </c:forEach> 
					</spring:bind>	
					&nbsp;
					<spring:bind path="note.priority">   
						<input 
							name="priorityDummy" 
							type="checkbox" 
							class="checkbox" 
							value="${status.value}" 
							size="40" 
							<c:if test="${status.value eq 1}">checked="checked"</c:if>
							title="<ikep4j:message pre='${prefix}' key='${status.expression}.tooltip' />"
							/> 
						<ikep4j:message pre='${prefix}' key='${status.expression}' /> 
					</spring:bind> 	
					</div>
				</td>  
			</tr>
			<tr>
				<spring:bind path="note.contents">
				<td colspan="2" class="ckeditor">  
					<div id="editorDiv">					
						<textarea id="contents"
						name="${status.expression}" 
						class="inputbox w100 fullEditor"
						cols="" 
						rows="5" 
						title="<ikep4j:message pre='${prefix}' key='${status.expression}' />">${status.value}</textarea>					
					</div> 				
				</td> 
				</spring:bind> 
			</tr>
		</tbody>
	</table>
	<div id="fileUploadArea" class="mt5"></div>
</form> 
</div>