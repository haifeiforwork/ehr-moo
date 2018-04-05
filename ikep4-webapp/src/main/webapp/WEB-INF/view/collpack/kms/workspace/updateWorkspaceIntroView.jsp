<%--
=====================================================
	* 기능설명	:	workspace 소개화면 편집
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"	value="message.collpack.collaboration.workspace.updateWorkspace.header" />
<c:set var="preSearch"	value="message.collpack.collaboration.workspace.updateWorkspace.search" />
<c:set var="preCode"	value="message.collpack.common.code" />
<c:set var="preDetail"	value="message.collpack.collaboration.workspace.updateWorkspace.detail" />
<c:set var="preButton"	value="message.collpack.collaboration.workspace.updateWorkspace.button" />
<c:set var="preScript"	value="message.collpack.collaboration.workspace.updateWorkspace.script" />
<c:set var="preCommon"  value="message.collpack.collaboration.workspace.common" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>


<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>  

<script type="text/javascript">
<!-- 
var useActXEditor = "${useActiveX}";

(function($) {

	// onload시 수행할 코드
	$jq(document).ready(function() { 

		iKEP.iFrameContentResize();  
		$jq("#initWorkspaceButton").click(function() {
			$jq("#mainForm")[0].reset(); 	
			return false; 	
		});

		$jq("#saveWorkspaceButton").click(function() {
	
			//$("#mainForm").submit();
			$("#mainForm").trigger("submit"); 
			return false; 
		});

		//$("#introduction").ckeditor($.extend(fullCkeditorConfig, {"popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));  	
		
		/**
		 * Validation Logic Start
		 */
		
		var validOptions = {
			/**rules : {
				introduction :	{
					required : true
				}
			},
			messages : {
				introduction : {
					direction	:	"bottom",
					required	:	"<ikep4j:message key="NotEmpty.workspace.introduction" />"
					
				}
			},**/
			submitHandler : function(form) {
				// 부가 검증 처리
				/* 에디터에 첨부되어 있는 이미지를 업로드한다.*/
				/**createEditorFileLink("mainForm");
				
				if($jq("textarea[name=introduction]").val().byteLength()<1)
				{
					alert('<ikep4j:message pre='${preScript}' key='introduction' />');
					return false;
				}
				
				if(!confirm("<ikep4j:message pre='${preCommon}' key='save' />")) {
					return;
				}
				
				$.ajax({
					url : "<c:url value='/collpack/collaboration/workspace/updateWorkspaceIntro.do' />",
					type : "post",
					data : $(form).serialize(),
					loadingElement : {container:"#pageLodingDiv"},
					success : function(result) {
						location.href= "<c:url value='/collpack/collaboration/main/WorkspaceBody.do'/>?workspaceId="+result; // WS 메인화면으로 이동
					},
					error : function(xhr, exMessage) {
					
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				}); **/
				
		    	// ActiveX Editor 사용 여부가 Y인 경우
		    	if(useActXEditor == "Y"){
			    	if (!$.browser.msie) {
			    		//ekeditor 데이타 업데이트
			    		var editor = $("#contents").ckeditorGet(); 
						editor.updateElement();
						//에디터 내 이미지 파일 링크 정보 세팅
						createEditorFileLink("mainForm");
			    	} 			    	
		    	}else{
		    		//ekeditor 데이타 업데이트
		    		var editor = $("#contents").ckeditorGet(); 
					editor.updateElement();
					//에디터 내 이미지 파일 링크 정보 세팅
					createEditorFileLink("mainForm");
		    	}
		    	
		    	if(confirm("<ikep4j:message pre='${preCommon}' key='save' />")) {
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
							createActiveXEditorFileLink(tweBodyObj,"mainForm");
										
							$("#twe").css("visibility","hidden");

							$jq("textarea[name=introduction]").val(tweBody);
							if($jq("textarea[name=introduction]").val().length<1)
							{
								alert('<ikep4j:message pre='${preScript}' key='introduction' />');
								return false;
							}

						} else {

							var contents = $jq("textarea[name=contents]").val();
							$jq("textarea[name=introduction]").val(contents);
							contents =null;					

							if($jq("textarea[name=introduction]").val().length<1)
							{
								alert('<ikep4j:message pre='${preScript}' key='introduction' />');
								return false;
							}						
						}
					} else {
							var contents = $jq("textarea[name=contents]").val();
							$jq("textarea[name=introduction]").val(contents);
							contents =null;					
							if($jq("textarea[name=introduction]").val().length<1)
							{
								alert('<ikep4j:message pre='${preScript}' key='introduction' />');
								return false;
							}						
					}		
					$("body").ajaxLoadStart("button");
					

									
					$.ajax({
						url : "<c:url value='/collpack/collaboration/workspace/updateWorkspaceIntro.do' />",
						type : "post",
						data : $(form).serialize(),
						loadingElement : {container:"#pageLodingDiv"},
						success : function(result) {
							location.href= "<c:url value='/collpack/collaboration/main/WorkspaceBody.do'/>?workspaceId="+result; // WS 메인화면으로 이동
						},
						error : function(xhr, exMessage) {
						
							var errorItems = $.parseJSON(xhr.responseText).exception;
							validator.showErrors(errorItems);
						}
					});				
					//form.submit();
				}
			}
		};


		var validator = new iKEP.Validator("#mainForm", validOptions);
		
		/**
		 * Validation Logic End
		 */		
	
		//setTimeout(function(){iKEP.iFrameContentResize();},1000);
		
		// editor 초기화
		initEditorSet();
		
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
	var form = document.mainForm;
	document.twe.HtmlValue = $jq("input[name=contents]").val().replaceAll("src=\""+iKEP.getContextRoot(), "src=\""+iKEP.getWebAppPath());
</script>
<div id="pageLodingDiv">

<form id="editorFileUploadParameter"> 
	<input name="workspaceId"  value="${workspace.workspaceId}" type="hidden"/>	
	<input name="interceptorKey"  value="ikep4.system"    type="hidden"/>
</form>

<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 

<!--pageTitle Start-->

<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitleIntro" /></h2> 
</div> 
<!--//pageTitle End-->  

				
<div class="corner_RoundBox01" style="margin-bottom:20px;">
	<ikep4j:message pre="${preHeader}" key="subPageTitleIntro" />
	<div class="l_t_corner"></div>
	<div class="r_t_corner"></div>
	<div class="l_b_corner"></div>
	<div class="r_b_corner"></div>					
</div>				
	


<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="post" action="<c:url value='/collpack/collaboration/workspace/updateWorkspaceIntro.do' />">  
<spring:bind path="searchCondition.sortColumn">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 		
<spring:bind path="searchCondition.sortType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.workspaceId">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.workspaceStatus">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.listType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.listUrl">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.isOrganization">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<input name="workspaceIds" id="workspaceIds" type="hidden" value="${workspace.workspaceId}"/>
<input name="teamId" id="teamId" type="hidden" value="${workspace.teamId}"/>
<input name="msie"        			type="hidden" value="0" />

<textarea id="introduction" name="introduction" class="none" rows="0" cols="0"></textarea>
<div id="editorDiv">
	<span class="none">
	<input name="workspaceName" id="workspaceName"	class="inputbox w80" type="text" value="${workspace.workspaceName}"/>
	<textarea name="description" class="tabletext"	title="<ikep4j:message pre='${preDetail}' key='description' />" cols="" rows="5">${workspace.description}</textarea></td>
	</span>
	<spring:bind path="workspace.introduction">	
	<textarea 
		id="contents"
		name="contents" 
		class="inputbox w100 fullEditor"
		cols="" 
		rows="5" 
		title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />">${status.value}</textarea>
	<label for="contents" class="serverError"> ${status.errorMessage}</label>
	</spring:bind> 

</div>
<div class="clear">&nbsp;</div>
</form>
<!--//blockListTable End--> 
	 
<!--blockButton Start-->
<div class="blockButton">
<ul>
	<li><a id="saveWorkspaceButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='save'/>"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
	<!--li><a id="initWorkspaceButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='init'/>"><span><ikep4j:message pre='${preButton}' key='init'/></span></a></li-->
</ul>
</div>
<!--//blockButton End-->	

</div>	


