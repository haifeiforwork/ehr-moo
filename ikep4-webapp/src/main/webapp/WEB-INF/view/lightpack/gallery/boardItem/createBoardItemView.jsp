<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" />
<c:set var="preHeader"  value="ui.lightpack.gallery.boardItem.createBoardView.header" /> 
<c:set var="preDetail"  value="ui.lightpack.gallery.boardItem.createBoardView.detail" />
<c:set var="preButton"  value="ui.lightpack.gallery.common.button" /> 
<c:set var="preMessage" value="message.lightpack.gallery.common.boardItem" /> 

<c:set var="user"   value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
				
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>    

<%-- @ include file="/base/common/fileUploadControll.jsp" --%>
<%-- 파일업로드용 Import --%>

<script type="text/javascript">
<!--
var useActXEditor = "${useActiveX}";
var limitFileCount = 10;
var limitFileSize	= 5*1024*1024; // 5M


(function($){

	// 다른 사람의 프로파일로 이동
	getProfile = function() {
		document.location.href = "<c:url value='/support/profile/getProfile.do?targetUserId=${targetUserId}'/>";
	};
	
	// My Gallery 이동
	goMyGallery = function() {
		document.location.href = "<c:url value='/lightpack/gallery/boardItem/listBoardItemView.do?targetUserId=${targetUserId}&userId=${user.userId}&userLocale=${user.localeCode}'/>" ;
	};	
	// 방명록 이동
	goGuestbook = function() {
		document.location.href = "<c:url value='/support/guestbook/listGuestbook.do?targetUserId=${targetUserId}'/>" ;
	};		
	$(document).ready(function() { 

		//$("#divTab2").tabs({selected : 1});

		if(window.parent.topScroll != null) {
			window.parent.topScroll(); 	
		} 
		
		if(window.parent.menuMark != null) { 
			window.parent.menuMark("${targetUserId}");
		}
		
		//파일업로드 버튼 이벤트 바인딩
		$jq('#fileuploadBtn').click(function(event) {
			//파일업로드 팝업창
			//파라미터(이벤트객체이름,에디터에서사용여부(0:일반,1:에디터에서),이미지여부(0:모든파일,1:이미지파일만 가능)), DRM 사용여부(0:미사용, 1:사용)
			iKEP.fileUpload(event.target.id,'0','1','1');
			//에디터 감추기
			
	
			
			/**if(useActXEditor == "Y"){
			   	if ($.browser.msie) {
			   		$("#twe").css("visibility","hidden");
			   	}
			}**/
		});
		// editor 초기화
		initEditorSet();
		

		$("a.saveBoardItemButton").click(function() {

			if($jq("input[name=file]").val()==null || $jq("input[name=file]").val()=="")
			{
				alert('<ikep4j:message key="NotNull.gallery.boardItem.file" />');
				return false;
			}			
			$("#boardItemForm").trigger("submit"); 
			return false;  
		});
		
		new iKEP.Validator("#boardItemForm", {   
			rules  : {
				title     : {required : true, rangelength : [1, 100] }
			},
			messages : {
				title     : {direction : "top",    required : "<ikep4j:message key="NotNull.gallery.boardItem.title" />", rangelength : "<ikep4j:message key="Size.gallery.boardItem.title" />"}
			},   			
		    submitHandler : function(form) { 
		    	// ActiveX Editor 사용 여부가 Y인 경우
		    	if(useActXEditor == "Y"){
			    	if (!$.browser.msie) {
			    		//ekeditor 데이타 업데이트
			    		var editor = $("#contents").ckeditorGet(); 
						editor.updateElement();
						//에디터 내 이미지 파일 링크 정보 세팅
						createEditorFileLink("boardItemForm");
			    	}
		    	}else{
		    		//ekeditor 데이타 업데이트
		    		var editor = $("#contents").ckeditorGet(); 
					editor.updateElement();
					//에디터 내 이미지 파일 링크 정보 세팅
					createEditorFileLink("boardItemForm");
		    	}
				
		    	if(confirm("<ikep4j:message pre="${preMessage}" key="createItem" />")) {
					//fileController.upload(function(isSuccess, files) {

						//if(isSuccess === true) { 
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
						    		
						    		var form = document.boardItemForm;
									// 첨부파일 용량 제한
									if(form.twe.IsOverAttachmentLimit()) {
										alert('<ikep4j:message pre='${preMessage}' key='editor.limitSize' />');
										return false;
									}
									// 첨부파일 갯수 제한
									if(form.twe.GetAttachmentCount()>limitFileCount) {
										alert('<ikep4j:message pre='${preMessage}' key='editor.limitCount' /> '+limitFileCount+'<ikep4j:message pre='${preMessage}' key='editor.limitCount1' />');
										return false;
									}	
						    		$("#twe").css("visibility","hidden");
						    	}
							}
							
							$("body").ajaxLoadStart("button");
							form.submit();
					//	}
					//});
				}
		    }
		}); 
		
	    var uploaderOptions = {
		 	<c:if test="${empty fileDataListJson}">files : [],</c:if> 
		 	<c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if> 
			<c:if test="${not empty board.fileSize}">maxFileSize : ${board.fileSize}, </c:if>  
			<c:if test="${board.fileAttachOption eq 0 and not empty board.allowType}">allowExt : "${board.allowType}",</c:if>
			<c:if test="${board.fileAttachOption eq 1 and not empty board.allowType}">allowFileType : "${board.allowType}",</c:if>
			maxFileSize : 10*1024*1024,
	    	isUpdate : true ,
	    	onLoad : function() {
	    		iKEP.iFrameContentResize();
	    	}
	    };  
        
	    //var fileController = new iKEP.FileController("#boardItemForm", "#fileUploadArea", uploaderOptions);
		
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
    //업로드완료후 fileId 리턴
	afterFileUpload = function(status, fileId, fileName, message, gubun) {

		//리턴받은 fileId를 Hidden값으로 저장함
		//폼을 최종 저장할때 filId를 가지고, fileLink정보를 생성함
		var imgsrc ="<c:url value="/support/fileupload/downloadFile.do"/>?fileId="+fileId;
		var img ="<img width='300px' id='viewImageDiv' src='"+imgsrc+"' />";
		//$jq("#viewDiv").html(fileId);
		$jq("#viewDiv").html(img);
		$jq("input[name=fileId]").val(fileId);
		
		//에디터 보이기
		if(useActXEditor == "Y"){
	    	if ($.browser.msie) {
	    		$("#twe").css("visibility","");
	    	}
		}
		
		setTimeout(function() {
			iKEP.iFrameContentResize(); 
		}, 200);
		 
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
	// 이미지 용량제한
	twe.AttachmentLimitSize = limitFileSize; // 5MByte
</script>

<c:if test="${popupYn}"><div class="contentIframe"></c:if>
<form id="editorFileUploadParameter" action="null"> 
	<input name="boardId"  value="${targetUserId}" type="hidden"/> 
	<input name="interceptorKey"  value="lightpack.gallery" type="hidden"/> 
</form> 

	<%--tab Start-->		
	<div class="iKEP_tab_menu_common">
		<ul>
			<li><a href="#a" onclick="getProfile();"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${profile.userName}"/></c:when><c:otherwise><c:out value="${profile.userEnglishName}"/></c:otherwise></c:choose><ikep4j:message pre='${preProfileMain}' key='profile.owner' /></a></li>
		</ul>	
		<ul>
			<li class="selected"><a href="#a" onclick="goMyGallery();"><ikep4j:message pre='${preProfileMain}' key='profile.photo' /></a></li>
		</ul>	
		<ul>
			<li><a href="#a" onclick="goGuestbook();"><ikep4j:message pre='${preProfileMain}' key='profile.guestbook' /></a></li>
		</ul>													
	</div>		
	<!--//tab End--%>
<div style="text-align:right; padding:0 20px 8px 0;">
	<a href="<c:url value='/support/profile/getProfile.do?targetUserId=${targetUserId}'/>"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${profile.userName}"/></c:when><c:otherwise><c:out value="${profile.userEnglishName}"/></c:otherwise></c:choose><ikep4j:message pre='${preProfileMain}' key='profile.owner' /></a>
</div>

<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 

<!--pageTitle Start--> 
<div id="pageTitle" class="btnline"> 
	<h2><ikep4j:message pre="${preHeader}" key="photo" /></h2> 
	<div class="blockButton"> 
		<ul>
			<c:if test="${targetUserId==user.userId}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
				<li><a class="button saveBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			</c:if>  
			<li><a class="button" href='<c:url value='/lightpack/gallery/boardItem/listBoardItemView.do?targetUserId=${targetUserId}&amp;searchConditionString=${searchConditionString}&amp;
=${popupYn}'/>'><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		</ul>
	</div>	
</div>  
<!--//pageTitle End--> 

<!--blockDetail Start-->
<div class="blockDetail">
<form id="boardItemForm" name="boardItemForm" method="post" action="<c:url value='/lightpack/gallery/boardItem/createBoardItem.do'/>"  enctype="multipart/form-data">
	<input name="targetUserId"          type="hidden" value="${targetUserId}" />
	<input name="searchConditionString" type="hidden" value="${searchConditionString}" />
	<input name="popupYn"               type="hidden" value="${popupYn}" />
	<input name="msie"        			type="hidden" value="0" />
	<table summary="<ikep4j:message pre="${preDetail}" key="summary" />">
		<caption></caption>
		<col style="width: 15%"/>
		<col style="width: 35%"/>
		<col style="width: 15%"/>
		<col style="width: 35%"/>
		<tbody> 
		<tr> 
			<spring:bind path="boardItem.title">
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3"> 
				<div>
					<input 
						name="${status.expression}" 
						type="text" 
						class="inputbox" style="width: 80%;" 
						value="${status.value}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						/> 
						<c:forEach items="${status.errorMessages}" var="error">
					    	<label for="${status.expression}" class="serverError"> ${error}</label>
					    </c:forEach> 
					</spring:bind>
					
				</div>			
			</td>  
		</tr>				
		<tr>  
			<th scope="row"><ikep4j:message pre='${preDetail}' key='registerName' /></th>
			<td colspan="3">

				<c:choose>
					<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						${user.userName} ${user.jobTitleName} ${user.teamName}
					</c:when>
					<c:otherwise>
						${user.userEnglishName} ${user.jobTitleEnglishName} ${user.teamEnglishName}
					</c:otherwise>
				</c:choose>						
			</td>
		</tr>	
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='image' /></th>
			<td colspan="3"><input name="file" type="file" class="file w70" title=""/>
	
			<!--input name="fileId" type="hidden" value="" title="fileId" />
			<span id="viewDiv"></span>	
			<a class="button" href="#a" >
				<span name="fileuploadBtn" id="fileuploadBtn"><ikep4j:message pre='${preButton}' key='image' /></span>
			</a-->
			</td>
		</tr>			

		<tr>  
			<spring:bind path="boardItem.contents">
			<td colspan="4" class="ckeditor"> 
				<div id="editorDiv">					
					<textarea id="contents"
					name="${status.expression}" 
					class="inputbox w100 fullEditor"
					cols="" 
					rows="5" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />">${status.value}</textarea>					
				</div> 				
			</td> 
			</spring:bind> 
		</tr>  
		</tbody> 
	</table>
	</form> 
</div> 
<!--//blockDetail End-->  									
<!--blockButton Start-->
<div class="blockButton"> 
	<ul> 
		<c:if test="${targetUserId==user.userId}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
			<li><a class="button saveBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
		</c:if>  
		<li><a class="button" href='<c:url value='/lightpack/gallery/boardItem/listBoardItemView.do?targetUserId=${targetUserId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>'><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
	 </ul>
</div>
<!--//blockButton End-->  
<c:if test="${popupYn}"></div></c:if>