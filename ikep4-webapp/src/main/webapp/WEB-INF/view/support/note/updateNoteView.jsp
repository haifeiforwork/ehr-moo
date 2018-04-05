<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.note.updateNoteView.header" /> 
<c:set var="preDetail"  value="ui.support.note.updateNoteView.detail" />
<c:set var="preButton"  value="ui.support.common.button" /> 
<c:set var="preMessage" value="message.support.common.note" /> 
<%-- 메시지 관련 Prefix 선언 End --%>
<%--
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>
--%>     
<link rel="stylesheet" href="<c:url value="/base/js/wceditor/css/editor.css"/>"/>
<script type="text/javascript" src="<c:url value="/base/js/wceditor/js/lang.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/wceditor/js/editor.js"/>"></script>    
    
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
<!--  
var useActXEditor = "${useActiveX}";
(function($){	 
	$(document).ready(function() {
		
		$("#contents").val($("#contentsTemp").val());
		
		if(window.parent.topScroll != null) {
			window.parent.topScroll(); 	
		} 
		
		if(window.parent.menuMark != null) { 
			window.parent.menuMark("${noteFolder.folderId}");
		}

		var curNavText = '&nbsp;&gt;&nbsp;'+'<ikep4j:message pre="${preHeader}" key="pageTitle" />';
		//parent.callLocation(document.URL,curNavText);
		
		$("a.saveNoteButton").click(function() {
			$("input[name=noteDelete]").val(0);
			$("#noteForm").trigger("submit");			
			return false;
		});		

		<%--// editor 초기화--%>
		initEditorSet();
		$("#editorFileUploadParameter input[name=folderId]").val($("select[name=folderId]").val());
		
		$jq("select[name=folderId]").change(function() {
			$("#editorFileUploadParameter input[name=folderId]").val($("select[name=folderId]").val());
        });  
		
		new iKEP.Validator("#noteForm", {   
			rules  : {
				title     : {required : true, rangelength : [1, 100] }
			},
			messages : {
				title     : {direction : "top",    required : "<ikep4j:message key="NotNull.boardItem.title" />", rangelength : "<ikep4j:message key="Size.boardItem.title" />"}
			},   
			
		    submitHandler : function(form) {  
		    	
		    	<%-- ActiveX Editor 사용 여부가 Y인 경우--%>
		    	if(useActXEditor != "Y" || !$jq.browser.msie){
		    		WCEditor.saveContent();

					<%--에디터 내 이미지 파일 링크 정보 세팅--%>
					createEditorFileLink("noteForm");
		    	}
		    	
// 		    	if(confirm("<ikep4j:message pre="${preMessage}" key="updateItem" />")) {
					fileController.upload(function(isSuccess, files) {
						if(isSuccess === true) {
							if($("input[name=priorityDummy]").is(":checked")) {
								$("input[name=priority]").val("1");
							} else {
								$("input[name=priority]").val("0");
							} 
							
							<%--에디터 감추기--%>
							if(useActXEditor == "Y" && $jq.browser.msie){
					    		<%--//태그프리 선택 탭을 디자인으로 변경 후 저장한다.--%>
					    		var tweTab = document.twe.ActiveTab;
					    		if ( tweTab != 1 ) {
					    			document.twe.ActiveTab = 1;
					    		}
					    		<%--//태그프리 Mime 데이타 세팅--%>
					    		var tweBody = document.twe.MimeValue();
					    		$jq("input[name='contents']").val(tweBody);
					    		
					    		var tweBodyObj = document.twe.GetBody();
					    		<%--//에디터 내 이미지 파일 링크 정보 세팅--%>
					    		createActiveXEditorFileLink(tweBodyObj,"noteForm");
					    		
					    		$jq("#twe").css("visibility","hidden");
							}
							
							$("body").ajaxLoadStart("button");
							
							$jq.ajax({
								url : "<c:url value='/support/note/createNoteAjax.do'/>",
								data : $jq("#noteForm").serialize(),
								type : "post",
								dataType : "html",
								success : function(result) {
									parent.location.href = "<c:url value='/support/note/noteView.do'/>?folderId=${noteFolder.folderId}&noteId=${note.noteId}&searchConditionString=${searchConditionString}"; 
								}
							});
						}
					});
// 				}
		    }
		});    
				
	    var uploaderOptions = {
	        <c:if test="${empty fileDataListJson}">files : [],</c:if> 
	 		<c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if> 
			isUpdate : true,
	    	maxTotalSize : ${noteFolder.fileSize},
	    	onLoad : function() {
	    		iKEP.iFrameContentResize();
	    	}
		};  
        
	    var fileController = new iKEP.FileController("#noteForm", "#fileUploadArea", uploaderOptions); 
	   
	    <%--// ActiveX Editor 사용 여부가 Y인 경우--%>
	    if(useActXEditor == "Y"){
		    if (!$.browser.msie) {
		    	<%--//에디터 로딩 후 현재 페이지 높이 세팅 - iframe 높이 조절--%>
		    	iKEP.iFrameContentResize();
		    	<%--
		    	var editor = $("#contents").ckeditorGet();
				editor.on("instanceReady",function() {
					iKEP.iFrameContentResize();
				});
				--%>
				$("input[name=title]").focus();
	    	}
	    }else{
	    	<%--//에디터 로딩 후 현재 페이지 높이 세팅 - iframe 높이 조절--%>
	    	iKEP.iFrameContentResize();
	    	<%--
	    	var editor = $("#contents").ckeditorGet();
			editor.on("instanceReady",function() {
				iKEP.iFrameContentResize();
			});
			--%>
			$("input[name=title]").focus();
	    }
		
	});   
		
	<%-- editor 초기화  --%>
	initEditorSet = function() {
		<%-- ActiveX Editor 사용 여부가 Y인 경우 --%>
	    if(useActXEditor == "Y"){
			<%-- 브라우저가 ie인 경우 --%>
			if ($.browser.msie) {
				<%--// div 높이, 넓이 세팅 --%>
				var cssObj = {
			      'height' : '450px',
			      'width' : '100%'
			    };
				$('#editorDiv').css(cssObj);
				<%--// hidden 필드 추가(contents) - 수정모드 --%>
				iKEP.createActiveXEditor("#editorDiv","${user.localeCode}","#contents",1);
				<%--// ie 세팅 --%>
				$('input[name="msie"]').val('1');
			}else{
				wceditorSimpleToolbar.conversionMode = [];
				
				<%--// wceditor 초기화 --%>
	            $("#contents").wceditor(wceditorSimpleToolbar);
	        
	            <%--// ckeditor 초기화.   --%>
				<%--//$("#contents").ckeditor($.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));  --%>
				<%--// ie 이외 브라우저 값 세팅 --%>
				$('input[name="msie"]').val('0');
			}
	    }else{
	    	wceditorSimpleToolbar.conversionMode = [];
	    	
	        <%-- // wceditor 초기화  --%>
	        $("#contents").wceditor(wceditorSimpleToolbar);
	            
	    	<%-- // ckeditor 초기화. 
			//$("#contents").ckeditor($.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
			// ie 이외 브라우저 값 세팅 --%>
			$('input[name="msie"]').val('0');
	    }
	};
})(jQuery); 
//-->
</script>
<script language="JScript" FOR="twe" EVENT="OnKeyDown(event)">
	<%-- /* 태그프리 에디터 줄바꿈 태그 P => br 로 변경하는 메소드 */ --%>	
	if (!event.shiftKey && event.keyCode == 13){
		twe.InsertHtml("<br>");
		event.returnValue = true; 
	}
	if (event.shiftKey && event.keyCode == 13){
		twe.InsertHtml("<p>");
		event.returnValue = true; 
	}		
</script>
<script language="JScript" for="twe" event="OnControlInit()">
	var form = document.noteForm;
	document.twe.HtmlValue = $jq("input[name=contents]").val().replaceAll("src=\""+iKEP.getContextRoot(), "src=\""+iKEP.getWebAppPath());
	$jq("input[name=title]").focus();
</script>
<form id="editorFileUploadParameter" action="null"> 
	<input name="folderId"  	value="${note.folderId}" type="hidden"/> 
	<input name="interceptorKey"  value="support.note" type="hidden"/> 
	<input name="maxFileSize"	value="${noteFolder.imageFileSize}" type="hidden"/>
	<input name="maxImageWith"	value="${noteFolder.imageWidth}" type="hidden"/>
</form>
<h1 class="none"><ikep4j:message pre="${preHeader}" key="title" /></h1>

<div id="pageTitle" class="pageTitle_both">
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2>
	<!--blockButton Start-->
	<%-- <div class="blockButton"> 
		<ul>
			<c:if test="${user.userId eq note.registerId}">쓰기 가능 권한인 경우 저장 버튼 활성화
			<li><a class="button saveNoteButton" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			</c:if> 
			<li><a class="button" href='<c:url value='/support/note/listNoteView.do?folderId=${noteFolder.folderId}&amp;noteId=${note.noteId}&amp;searchConditionString=${searchConditionString}'/>'><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
		</ul>
	</div> --%>
	<div class="clear"></div>
</div>

<div class="blockDetail">
<form id="noteForm" method="post" action="<c:url value='/support/note/updateNote.do'/>">
	<input name="searchConditionString" type="hidden" value="${searchConditionString}" /> 		
	<input name="msie"        			type="hidden" value="0" />
	<input name="selFolderId"           type="hidden" value="${noteFolder.folderId}" /> 		
	<input name="noteId"                type="hidden" value="${note.noteId}" /> 	
	<input name="noteDelete"            type="hidden" />		
	<input name="priority"            	type="hidden" />	
	<table summary="Note Edit">
		<caption></caption>
		<colgroup>
			<col width="18%" />
			<col width="82%" />
		</colgroup>
		<tbody>
			<tr>
				<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${preDetail}' key='title' /></th>
				<td> 
					<div>
						<spring:bind path="note.title">
							<input 
							name="${status.expression}" 
							type="text" 
							class="inputbox w90"
							value="${status.value}" 
							title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
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
								title="<ikep4j:message pre='${preDetail}' key='${status.expression}.tooltip' />"
								/> 
							<ikep4j:message pre='${preDetail}' key='${status.expression}' /> 
						</spring:bind>
					</div>
				</td>  
			</tr>
			<tr>
				<th scope="row"><ikep4j:message pre='${preDetail}' key='folder' /></th>
				<td>
					<div>
						<spring:bind path="note.folderId">
							<select name="${status.expression}" title='<ikep4j:message pre='${preDetail}' key='${status.expression}' />' class="inputbox" style="width:300px;">
								<c:forEach var="folder" items="${folderList}">
									<option value="${folder.folderId}" <c:if test="${folder.folderId eq status.value}">selected="selected"</c:if> >
										${folder.folderName}
									</option>
								</c:forEach>
							</select>
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
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"></textarea>					
					</div>
					<input type="hidden" id="contentsTemp" value="${status.value}"/>				
				</td> 
				</spring:bind> 
			</tr>
		</tbody>
	</table>
	<div id="fileUploadArea" class="mt10"></div>
	</form> 
</div> 

<div class="blockButton"> 
	<ul>
		<c:if test="${user.userId eq note.registerId}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
		<li><a class="button saveNoteButton" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>			
		</c:if> 
		<li><a class="button" href='<c:url value='/support/note/listNoteView.do?folderId=${noteFolder.folderId}&amp;noteId=${note.noteId}&amp;searchConditionString=${searchConditionString}'/>'><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
	</ul>
</div>