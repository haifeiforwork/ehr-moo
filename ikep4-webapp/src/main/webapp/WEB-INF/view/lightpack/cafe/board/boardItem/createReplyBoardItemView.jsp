<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.lightpack.cafe.board.boardItem.createReplyBoardView.header" /> 
<c:set var="preDetail"  value="message.lightpack.cafe.board.boardItem.createReplyBoardView.detail" />
<c:set var="preButton"  value="message.lightpack.cafe.common.button" /> 
<c:set var="preMessage" value="message.lightpack.cafe.common.boardItem" /> 
<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script> 
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>
<script type="text/javascript">
<!--   
//activeX editor 사용여부
var useActXEditor = "${useActiveX}";
(function($){	 
	$(document).ready(function() {   
		if(window.parent.topScroll != null) {
			window.parent.topScroll(); 	
		} 
		
		if(window.parent.menuMark != null) { 
			window.parent.menuMark("${boardItem.boardId}");
		}
		
		$("a.saveBoardItemButton").click(function() { 
			$("#boardItemForm").trigger("submit"); 
			return false;  
		});
		
		// editor 초기화
		initEditorSet();
		
		
		new iKEP.Validator("#boardItemForm", {   
			rules  : {
				title     : {required : true, rangelength : [1, 100] },
				tag       : {required : true, tagDuplicate : true}
			},
			messages : {
				title     : {direction : "top",    required : "<ikep4j:message key="message.lightpack.cafe.NotNull.boardItem.title" />", rangelength : "<ikep4j:message key="message.lightpack.cafe.Size.boardItem.title" />"},
				tag       : {direction : "top", required : "<ikep4j:message key="message.lightpack.cafe.NotBlank.boardItem.tag" />", tagDuplicate : "<ikep4j:message key="message.lightpack.cafe.TagDuplicate.boardItem.tag" />"} 
			},   
			
		    submitHandler : function(form) {  
		    	
		    	// ActiveX Editor 사용 여부가 Y인 경우
		    	if(useActXEditor != "Y" || !$jq.browser.msie){
		    		//ekeditor 데이타 업데이트
		    		var editor = $jq('#boardItemForm :input[name=contents]').ckeditorGet(); 
					editor.updateElement();
					//에디터 내 이미지 파일 링크 정보 세팅
					createEditorFileLink("boardItemForm");
		    	}
		    	
		    	if(confirm("<ikep4j:message pre="${preMessage}" key="createItemReply" />")) {
					fileController.upload(function(isSuccess, files) {
						if(isSuccess === true) { 

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
							
							$("body").ajaxLoadStart("button");
							form.submit();
						}
					});
				}
		    }
		});   
				 
	    var uploaderOptions = {
	 	    <c:if test="${empty fileDataListJson}">files : "",</c:if> 
	 	    <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if> 
		    <c:if test="${board.fileAttachOption eq 0 and not empty board.allowType}">allowExt : "${board.allowType}",</c:if>
		    <c:if test="${board.fileAttachOption eq 1 and not empty board.allowType}">allowFileType : "${board.allowType}",</c:if>
	        isUpdate : true,
	    	onLoad : function() {
	    		iKEP.iFrameContentResize();
	    	}
	    };  
	    
	    var fileController = new iKEP.FileController("#boardItemForm", "#fileUploadArea", uploaderOptions);	 
	    
	 	// ActiveX Editor 사용 여부가 Y인 경우
	    if(useActXEditor == "Y"){
		    if (!$.browser.msie) {
		    	//에디터 로딩 후 현재 페이지 높이 세팅 - iframe 높이 조절
		    	var editor = $("#contents").ckeditorGet();
				editor.on("instanceReady",function() {
					iKEP.iFrameContentResize();
				});
				$("input[name=title]").focus();
	    	}
	    }else{
	    	//에디터 로딩 후 현재 페이지 높이 세팅 - iframe 높이 조절
	    	var editor = $("#contents").ckeditorGet();
			editor.on("instanceReady",function() {
				iKEP.iFrameContentResize();
			});
			$("input[name=title]").focus();
	    }
	 	
	});  	
	
	/* editor 초기화  */
	initEditorSet = function() {
		// ActiveX Editor 사용 여부가 Y인 경우
	    if(useActXEditor == "Y"){
			// 브라우저가 ie인 경우
			if ($.browser.msie) {
				// div 높이, 넓이 세팅
				var cssObj = {
			      'height' : '450px',
			      'width' : '100%'
			    };
				$('#editorDiv').css(cssObj);

				// hidden 필드 추가(contents)
				iKEP.createActiveXEditor("#editorDiv","${user.localeCode}","#contents",0);
				// ie 세팅
				$('input[name="msie"]').val('1');
			}else{
				// ckeditor 초기화.
				$("#contents").ckeditor($.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
				// ie 이외 브라우저 값 세팅
				$('input[name="msie"]').val('0');
			}
	    }else{
	    	// ckeditor 초기화.
			$("#contents").ckeditor($.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
			// ie 이외 브라우저 값 세팅
			$('input[name="msie"]').val('0');
	    }
	};
	
})(jQuery); 
//-->
</script>
<script language="JScript" FOR="twe" EVENT="OnKeyDown(event)">
	/* 태그프리 에디터 줄바꿈 태그 P => br 로 변경하는 메소드 */
	if (!event.shiftKey && event.keyCode == 13){
		twe.InsertHtml("<br>");
		event.returnValue = true; <!-- Active Designer에서의 처리를 막음 -->
	}
	if (event.shiftKey && event.keyCode == 13){
		twe.InsertHtml("<p>");
		event.returnValue = true; <!-- Active Designer에서의 처리를 막음 -->
	}		
</script>
<script language="JScript" for="twe" event="OnControlInit()">	
	$jq("input[name=title]").focus();
</script>
<c:if test="${popupYn}"><div class="contentIframe"></c:if>
<form id="editorFileUploadParameter" action="null"> 
	<input name="boardId"  value="${board.boardId}" type="hidden"/> 
	<input name="interceptorKey"  value="lightpack.cafe.board" type="hidden"/> 
</form>
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
<!--pageTitle Start--> 
<div id="pageTitle" class="btnline"> 
	<h2>${board.boardName}</h2> 
	<div class="blockButton"> 
		<ul>
			<c:if test="${permission.isWritePermission}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
				<li><a class="button saveBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			</c:if> 
			<li><a class="button" href="<c:url value='/lightpack/cafe/board/boardItem/readBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>	
			<c:if test="${not popupYn}">	
				<li><a class="button" href="<c:url value='/lightpack/cafe/board/boardItem/listBoardItemView.do?boardId=${boardItem.boardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
			</c:if>
		</ul>
	</div>	
</div>  
<!--//pageTitle End--> 
<!--blockDetail Start-->
<div class="blockDetail">
	<form id="boardItemForm" method="post" action="<c:url value='/lightpack/cafe/board/boardItem/createReplyBoardItem.do'/>"> 
		<input name="searchConditionString" type="hidden" value="${searchConditionString}" />
		<input name="popupYn"       type="hidden" value="${popupYn}" />
		<input name="boardId"      	type="hidden" value="${board.boardId}"             title="<ikep4j:message pre='${preDetail}' key='boardId' />"/>
		<input name="itemParentId" 	type="hidden" value="${boardItem.itemId}"          title="<ikep4j:message pre='${preDetail}' key='itemParentId' />"/>  
		<input name="itemGroupId"  	type="hidden" value="${boardItem.itemGroupId}"     title="<ikep4j:message pre='${preDetail}' key='itemGroupId' />"/>  
		<input name="indentation"  	type="hidden" value="${boardItem.indentation + 1}" title="<ikep4j:message pre='${preDetail}' key='indentation' />"/>  
		<input name="step"         	type="hidden" value="${boardItem.step + 1}"        title="<ikep4j:message pre='${preDetail}' key='step'/>" />  
		<input name="itemDisplay"  	type="hidden" value="${boardItem.itemDisplay}"     title="<ikep4j:message pre='${preDetail}' key='itemDisplay'/>" />
		<input name="msie"        	type="hidden" value="0" />  
		<table summary="<ikep4j:message pre="${preDetail}" key="summary" />">
			<caption></caption>
			<col style="width: 15%"/>
			<col style="width: 85%"/> 
			<tbody> 
			<tr> 
				<spring:bind path="boardItem.title">
				<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
						<input 
						name="${status.expression}" 
						type="text" 
						class="inputbox w100"
						value="[RE] ${status.value}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />" />
						<c:forEach items="${status.errorMessages}" var="error">
						    <label for="${status.expression}" class="serverError"> ${error}</label>
						</c:forEach> 
					</div>					
				</td>  
				</spring:bind> 
			</tr>				
			<tr>  
				<spring:bind path="boardItem.tag">
				<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
						<input 
						name="${status.expression}" 
						type="text" 
						class="inputbox w40" 
						value="${status.value}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"/> 
						<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
						<c:forEach items="${status.errorMessages}" var="error">
					   		<label for="${status.expression}" class="serverError"> ${error}</label>
						</c:forEach> 
					</div>
				</td>		
				</spring:bind>  
			</tr>	
			<tr>  
			<spring:bind path="boardItem.contents">
			<td colspan="2" class="ckeditor"> 
				<div id="editorDiv"">					
					<textarea id="contents"
					name="${status.expression}" 
					class="inputbox w100 fullEditor"
					cols="" 
					rows="5" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"></textarea>					
				</div> 				
			</td> 
			</spring:bind> 
		</tr> 	
			</tbody> 
		</table>
		<div id="fileUploadArea" class="mt10"></div>
	</form>
</div>
<!--//blockDetail End-->  									
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<c:if test="${permission.isWritePermission}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
			<li><a class="button saveBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
		</c:if> 
		<li><a class="button" href="<c:url value='/lightpack/cafe/board/boardItem/readBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>	
		<c:if test="${not popupYn}">	
			<li><a class="button" href="<c:url value='/lightpack/cafe/board/boardItem/listBoardItemView.do?boardId=${boardItem.boardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		</c:if>
	 </ul>
</div>
<!--//blockButton End-->  
<c:if test="${popupYn}"></div></c:if>