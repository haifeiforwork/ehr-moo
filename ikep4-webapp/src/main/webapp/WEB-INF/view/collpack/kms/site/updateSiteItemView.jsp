<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/DextfileUploadInit.jsp"%><%-- 파일업로드용 Import --%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.kms.site.boardItem.listBoardView.header" />
<c:set var="preDetail"  value="message.collpack.kms.site.boardItem.createBoardView.detail" /> 
<c:set var="preList"    value="message.collpack.kms.site.boardItem.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.kms.site.boardItem.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.kms.site.boardItem.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.kms.site.boardItem.message.script" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>    
 
<script type="text/javascript">
<!-- 
var useActXEditor = "${useActiveX}";
(function($){	 
	$(document).ready(function() { 
		
		// editor 초기화
		initEditorSet();
		
		$("input.datepicker").datepicker({
		    showOn: "button",
		    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true
		}); 
		
		
		var validOptions = {
			rules : {
				title : {
					required : true,
					maxlength : 1024
				}
				/**,
				tag : {required : true, tagDuplicate : true}**/
			},
			messages : {
				title : {
					direction	:	"top",
					required	:	"<ikep4j:message key="NotEmpty.site.title" />",
					maxlength	:	"<ikep4j:message key="Size.site.title" />"

				}/**,
				tag       : {
					direction : "bottom", 
					required : "<ikep4j:message key="message.collpack.kms.NotBlank.boardItem.tag" />", 
					tagDuplicate : "<ikep4j:message key="TagDuplicate.boardItem.tag" />"
				} **/
			},
			submitHandler : function(form) {


		    	// ActiveX Editor 사용 여부가 Y인 경우
		    	if(useActXEditor == "Y"){
			    	if (!$.browser.msie) {
			    		//ekeditor 데이타 업데이트
			    		var editor = $("#contents").ckeditorGet(); 
						editor.updateElement();
						//에디터 내 이미지 파일 링크 정보 세팅
						createEditorFileLink("siteItemForm");
			    	}
		    	}else{
		    		//ekeditor 데이타 업데이트
		    		var editor = $("#contents").ckeditorGet(); 
					editor.updateElement();
					//에디터 내 이미지 파일 링크 정보 세팅
					createEditorFileLink("siteItemForm");
		    	}
				
				
				
				// 부가 검증 처리
				if(confirm("<ikep4j:message pre="${preMessage}" key="updateItem" />")) {


					var check =  $('#itemDisplayCheck:checked').val();
					
					if(check == null){check = 0;}
					
					$('#itemDisplay').val(check);
					
		    		if((oldSizes !=document.getElementById("FileUploadManager").Size)||(document.all["FileUploadManager"].Count>0)){//삭제한것이 있거나 추가한것이 있으면
		    			btnTransfer_Onclick("siteItemForm");
		    		}else{
		    			//alert("파일업로드 없음");
		    			
		    			oldFileSetting(oldFiles , form);
		    			writeSubmit(form);
		    		}
				}
			}
		}
	
		var validator = new iKEP.Validator("#siteItemForm", validOptions);
	
		
		
		$("#saveBoardItemButton").click(function() {
			//var editor = $("#contents").ckeditorGet(); 
			//editor.updateElement(); 
			
			$jq("#siteItemForm").submit();
			return false;
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
        
	    var fileController = new iKEP.FileController("#siteItemForm", "#fileUploadArea", uploaderOptions); 
	    */
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
	
	writeSubmit = function(targetForm){
		var check =  $('#itemDisplayCheck:checked').val();
		
		if(check == null){check = 0;}
		
		$('#itemDisplay').val(check);
		
	
		//에디터 감추기
		if(useActXEditor == "Y"){
	    	if ($.browser.msie) {
	    		//태그프리 선택 탭을 디자인으로 변경 후 저장한다.
	    		var tweTab = document.twe.ActiveTab;
	    		if ( tweTab != 1 ) {
	    			document.twe.ActiveTab = 1;
	    		}
	    		//태그프리 Mime 데이타 세팅
	    		var tweBody = document.twe.MimeValue();
	    		$('input[name="contents"]').val(tweBody);
	    		var tweBodyObj = document.twe.GetBody();
	    		//에디터 내 이미지 파일 링크 정보 세팅
	    		createActiveXEditorFileLink(tweBodyObj,"siteItemForm");
	    		
	    		$("#twe").css("visibility","hidden");
	    	}
		}
		
		$("body").ajaxLoadStart("button");	
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
	var form = document.siteItemForm;
	document.twe.HtmlValue = $jq("input[name=contents]").val().replaceAll("src=\""+iKEP.getContextRoot(), "src=\""+iKEP.getWebAppPath());
	$jq("input[name=title]").focus();
</script>

<form id="editorFileUploadParameter"> 
	<input name="interceptorKey"  value="ikep4.system"    type="hidden"/>
</form>
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
<!--pageTitle Start-->
<div id="pageTitle" class="btnline"> 
	<h2><ikep4j:message pre="${preHeader}" key="title" /></h2> 
</div> 
<!--//pageTitle End--> 
<!--blockDetail Start-->
<div class="blockDetail">
	<form id="siteItemForm" name="siteItemForm" method="post" action="<c:url value='/collpack/kms/site/updateSiteItem.do'/>">
	<input name="msie" type="hidden" value="0" />
	<spring:bind path="siteItem.itemDisplay">
		<input id="itemDisplay" name="${status.expression}" type="hidden" value="${status.value}" />
	</spring:bind> 
	
	<table summary="게시판등록">
		<caption></caption>
		<col style="width: 15%"/>
		<col style="width: 85%"/>
		<tbody> 
		<tr> 
			<spring:bind path="siteItem.title">
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<div>
				<input 
				name="${status.expression}" 
				type="text" 
				class="inputbox" style="width: 80%;" 
				value="${status.value}" 
				size="40" 
				title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
				/>
				<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
			</spring:bind>
			
				<input id="itemDisplayCheck" name="itemDisplayCheck" type="checkbox" class="checkbox" value="1" size="40" <c:if test="${siteItem.itemDisplay==1}">checked="checked"</c:if> title="<ikep4j:message pre='${preDetail}' key='itemDisplay' />"/>
				<ikep4j:message pre='${preDetail}' key='itemDisplay' /> 
				</div>		
			</td> 
			
		</tr>			
		<tr>  
			<th scope="row"><ikep4j:message pre='${preDetail}' key='registerName' /></th>
			<td>
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					${siteItem.registerName}
				</c:when>
				<c:otherwise>
					${siteItem.registerEnglishName}
				</c:otherwise>
				</c:choose>	
			</td>
		</tr>	
		<%--tr>  
			<spring:bind path="siteItem.tag">
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3">
				<div>
				<input name="${status.expression}" 
				type="text" 
				class="inputbox w40" 
				value="<c:forEach var="tag" items="${siteItem.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if>${tag.tagName}</c:forEach>" 
				size="40" 
				title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />" >
				<ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" />
				</div>	
			</td>		
			</spring:bind>
		<tr--%> 			
		<tr>  
			<spring:bind path="siteItem.contents">
			<td colspan="4">
				<div id="editorDiv">
				<textarea id="contents"
				name="${status.expression}" 
				class="tabletext fullEditor"
				cols="" 
				rows="20" 
				title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />">${status.value}</textarea>
				</div>
			</td> 
			</spring:bind> 
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
<!--//blockDetail End--> 
<!--tableBottom Start-->
<div class="tableBottom">										
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="saveBoardItemButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			<li><a id="listBoardItemButton" class="button" href="<c:url value='/collpack/kms/site/listSiteItemView.do'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		 </ul>
	</div>
	<!--//blockButton End--> 
</div>
<!--//tableBottom End--> 
