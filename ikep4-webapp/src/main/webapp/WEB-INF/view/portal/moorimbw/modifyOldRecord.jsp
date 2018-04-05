<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.lightpack.board.boardItem.updateBoardView.header" /> 
<c:set var="preDetail"  value="ui.lightpack.board.boardItem.updateBoardView.detail" />
<c:set var="preDetail2"  value="ui.lightpack.board.boardItem.createBoardView.detail" />
<c:set var="preButton"  value="ui.lightpack.common.button" /> 
<c:set var="preMessage" value="message.lightpack.common.boardItem" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script> 
<%@ include file="/base/common/DextfileUploadInit.jsp"%><%-- 파일업로드용 Import --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
<!--  
var useActXEditor = "Y";
(function($){	 
	$(document).ready(function() { 
		if(window.parent.topScroll != null) {
			window.parent.topScroll(); 	
		} 

		
		/* <c:if test="${board.anonymous ne 1}">
		if(${boardItem.itemDisplay} == 1){
			$("#categoryId").hide();
		} else {
			$("#categoryId").show();
		} 
		</c:if> */
		

		
		
		
		$("a.saveBoardItemButton").click(function() {
			$("#OldRecordForm").trigger("submit"); 
			return false;
		}); 
		
		
		// editor 초기화
		initEditorSet();
		
		$jq("#deleteReadPermissionButton").click(function(event) {
			event.preventDefault();
			var $rPermissionList=$jq('#readerList');
			$jq('option:selected',$rPermissionList).remove();

		});	
		

		
		
		new iKEP.Validator("#OldRecordForm", {   
			
		    submitHandler : function(form) {  
		    	/*
		    	if(confirm("<ikep4j:message pre="${preMessage}" key="updateItem" />")) {
					fileController.upload(function(isSuccess, files) {
						if(isSuccess === true) {
							var wec = document.Wec;
				    		$("input[name=contents]").val(wec.MIMEValue);
							
				    		if($("input[name=itemDisplayDummy]").is(":checked")) {
								$("input[name=itemDisplay]").val("1");
							} else {
								$("input[name=itemDisplay]").val("0");
								$jq.each($("#OldRecordForm select[name=categoryId] option:selected"),function(index){
									if($jq(this).text() != '') {			
				            			wordId.push($jq(this).val());
				            			wordName.push($jq(this).text());
				            		}
					    		});
								$jq("#wordId").val(wordId);
								$jq("#wordName").val(wordName);
							}
							
							$("body").ajaxLoadStart("button");
							form.submit();
						}
					});
				}
		    	
		    	*/
		    	// ActiveX Editor 사용 여부가 Y인 경우
		    	if(useActXEditor != "Y" || !$jq.browser.msie){
		    		//ekeditor 데이타 업데이트
		    		var editor = $jq('#OldRecordForm :input[name=contents]').ckeditorGet(); 
					editor.updateElement();
					//에디터 내 이미지 파일 링크 정보 세팅
					createEditorFileLink("OldRecordForm");
		    	}
		    	
				
		    	if(confirm("<ikep4j:message pre="${preMessage}" key="updateItem" />")) {

		    		if((oldSizes !=document.getElementById("FileUploadManager").Size)||(document.all["FileUploadManager"].Count>0)){//삭제한것이 있거나 추가한것이 있으면
		    			btnTransfer_Onclick("OldRecordForm");
		    		}else{
		    			//alert("파일업로드 없음");
		    			
		    			oldFileSetting(oldFiles , form);
		    			writeSubmit(form);
		    		}

				}
		    }
		});    
		
		/*
	    var uploaderOptions = {
	 		<c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if> 
			<c:if test="${board.fileAttachOption eq 0 and not empty board.allowType}">allowExt : "${board.allowType}",</c:if>
			<c:if test="${board.fileAttachOption eq 1 and not empty board.allowType}">allowFileType : "${board.allowType}",</c:if>
		    isUpdate : true,
	    	onLoad : function() {
	    		iKEP.iFrameContentResize();
	    	}
		};  
        
	    var fileController = new iKEP.FileController("#OldRecordForm", "#fileUploadArea", uploaderOptions); 
	   */
	   //dextFileUploadInit();
	   dextFileUploadInit("${board.fileSize}" ,"${board.fileAttachOption}", "${board.allowType}");
	   
	   var oldFiles;
	   var oldSizes;
	   <c:if test="${not empty fileDataListJson}"> 
	   oldFiles = ${fileDataListJson};
	   $jq.each(oldFiles,function(index){
		   var fileInfo = $.extend({index:index}, this);
		   document.getElementById("FileUploadManager").AddUploadedFile(fileInfo.fileId, fileInfo.fileRealName, fileInfo.fileSize);
		});
	   
	   dextFileUploadRefresh(); 
	   oldSizes =document.getElementById("FileUploadManager").Size;
	   </c:if> 

		// ActiveX Editor 사용 여부가 Y인 경우

		if(useActXEditor == "Y"){
		    if (!$.browser.msie) {
		    	//에디터 로딩 후 현재 페이지 높이 세팅 - iframe 높이 조절
		    	var editor = $("#contents").ckeditorGet();
				editor.on("instanceReady",function() {
					iKEP.iFrameContentResize();
				});
	    	}
	    }else{
	    	//에디터 로딩 후 현재 페이지 높이 세팅 - iframe 높이 조절
	    	var editor = $("#contents").ckeditorGet();
			editor.on("instanceReady",function() {
				iKEP.iFrameContentResize();
			});
	    }
	});   
		
	writeSubmit = function(targetForm){
		
		
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
			
			var tweBodyObj = document.twe.GetBody();
			//에디터 내 이미지 파일 링크 정보 세팅
			createActiveXEditorFileLink(tweBodyObj,"OldRecordForm");
			
			$jq("#twe").css("visibility","hidden");
		}
		
		$("body").ajaxLoadStart();
		targetForm.submit();
	
	};
		
	
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
				// hidden 필드 추가(contents) - 수정모드
				iKEP.createActiveXEditor("#editorDiv","${user.localeCode}","#contents",1);
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
	var form = document.OldRecordForm;
	//alert(iKEP.getContextRoot()+":"+iKEP.getWebAppPath());
	document.twe.HtmlValue = $jq("input[name=contents]").val();//.replaceAll("src=\""+iKEP.getContextRoot(), "src=\""+iKEP.getWebAppPath());
</script>


<form id="editorFileUploadParameter" action="null"> 
	<input name="interceptorKey"  value="lightpack.board" type="hidden"/> 
</form>


<SCRIPT language="JScript" FOR="Wec" EVENT="OnInitCompleted()">
	var wec = document.Wec;
	wec.Value = $jq("input[name=contents]").val();
</SCRIPT>

<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
<!--pageTitle Start-->
<div id="pageTitle" class="btnline"> 
	<h2>
		과거실적
	</h2> 
	<div class="blockButton"> 
		<ul>
			<li><a class="button saveBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
			 <li><a class="button" href="<c:url value='/portal/moorimess/oldRecord/oldRecordList.do'/>"><span>목록</span></a></li>
		</ul>
	</div>	
</div> 
<!--//pageTitle End--> 
<div class="blockDetail"> 

<form id="OldRecordForm" method="post" action="<c:url value='/portal/moorimess/oldRecord/modifyOldRecord.do'/>" enctype="multipart/form-data">
	<input name="itemId"                type="hidden" value="}" /> 	
	<input name="itemParentId" 	type="hidden" value="$"         />  	
	<input name="itemDisplay"           type="hidden" value="" />
	<input name="msie"        			type="hidden" value="0" />
	<input type="hidden" name="seqNum" value="${oldRecord.seqNum}" />
	<table summary="<ikep4j:message pre="${preDetail}" key="summary" />">
		<caption></caption>
		<col style="width: 15%"/>
		<col style="width: 35%"/>
		<col style="width: 15%"/>
		<col style="width: 35%"/>
		<tbody> 
		<tr> 
			
			<th scope="row">제목</th>
			<td colspan="3"> 
				<div>
						<input 
							name="subject" 
							type="text" 
							class="inputbox w100"
							value="${oldRecord.subject}" 
							size="40" 
							title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />" />
							<c:forEach items="${status.errorMessages}" var="error">
							    <label for="${status.expression}" class="serverError"> ${error}</label>
							</c:forEach> 
				</div>		
			</td> 
		</tr>				
			
		<tr>  
			<td colspan="4" class="ckeditor">
				<!-- 에디터 부분 시작 -->
				<!-- script type="text/javascript" src="<c:url value='/base/namo/NamoWec7.js'/>" charset="UTF-8"></script>
				<input id="contents" name="contents" type="hidden" value="${oldRecord.contents}" /-->
				<!-- 에디터 부분 끝 --> 
				
				<div id="editorDiv"">
					<textarea 
					id="contents"
					name="contents" 
					class="inputbox w100 fullEditor"
					cols="" 
					rows="5" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />">${oldRecord.contents}</textarea>
				</div> 				
			</td> 
		</tr>
		</tbody> 
	</table>
	<table style="width:100%;">
		<tr>
			<td colspan="2" style="border-color:#e5e5e5">
				<OBJECT id="FileUploadManager" codeBase="<c:url value="/Bin/DEXTUploadX.cab#version=2,8,2,0"/>"
					height="140" width="100%" classid="CLSID:DF75BAFF-7DD5-4B83-AF5E-692067C90316" VIEWASTEXT>
					 <param name="ButtonVisible" value="FALSE" />
					 <param name="VisibleContextMenu" value="FALSE" />
					 <param name="StatusBarVisible" value="FALSE" />
					 <param name="VisibleListViewFrame" value="FALSE" />
				</OBJECT>
			</td>
		<tr>

		<tr>
            <td style="border-right:none;border-color:#e5e5e5;background-color:#e8e8e8">전체 <span id="_StatusInfo_count"></span>개 <span id="_StatusInfo_size"></span><span id="_Total_size"></span></div>
			<td align="right" style="border-left:none;border-color:#e5e5e5;background-color:#e8e8e8;">
			<img src="<c:url value="/base/images/dextupload/btn_fileplus_normal.gif"/>" id="imgBtn_fileAdd" name="Image2"  border="0" onclick="btnAddFile_Onclick()" style="cursor:pointer;valign:absmiddle" />
			<img src="<c:url value="/base/images/dextupload/btn_listdelete_normal.gif"/>" name="Image4"  border="0" onclick="btnDeleteItem_Onclick()" style="cursor:pointer;valign:absmiddle" />	
			</td>
		</tr>
	</table>
	</form> 
</div> 
