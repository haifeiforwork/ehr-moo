<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.lightpack.award.awardItem.createReplyAwardView.header" /> 
<c:set var="preDetail"  value="ui.lightpack.award.awardItem.createReplyAwardView.detail" />
<c:set var="preButton"  value="ui.lightpack.common.button" /> 
<c:set var="preMessage" value="message.lightpack.common.awardItem" /> 
<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script> 
<%@ include file="/base/common/DextfileUploadInit.jsp"%><%-- 파일업로드용 Import --%>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

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
			window.parent.menuMark("${awardItem.awardId}");
		}
		
		$("a.saveAwardItemButton").click(function() { 
			$("input[name=tempSave]").val("0");
			$("#awardItemForm").trigger("submit"); 
			return false;  
		});
		
		$("a.tempSaveAwardItemButton").click(function() {
			$("input[name=tempSave]").val("1");
			$("#awardItemForm").trigger("submit"); 
			return false;  
		});
		
		// editor 초기화
		initEditorSet();
		
		
		new iKEP.Validator("#awardItemForm", {   
			rules  : {
				title     : {required : true, rangelength : [1, 100] }
			},
			messages : {
				title     : {direction : "top",    required : "<ikep4j:message key="NotNull.awardItem.title" />", rangelength : "<ikep4j:message key="Size.awardItem.title" />"}
			},   
			
		    submitHandler : function(form) {  
		    	/*
		    	if(confirm("<ikep4j:message pre="${preMessage}" key="createItemReply" />")) {
					fileController.upload(function(isSuccess, files) {
						if(isSuccess === true) { 
							var wec = document.Wec;
				    		$("input[name=contents]").val(wec.MIMEValue);
							
							$("body").ajaxLoadStart("button");
							form.submit();
						}
					});
				}
		    	*/
		    	
		    	// ActiveX Editor 사용 여부가 Y인 경우
		    	if(useActXEditor != "Y" || !$jq.browser.msie){
		    		//ekeditor 데이타 업데이트
		    		var editor = $jq('#awardItemForm :input[name=contents]').ckeditorGet(); 
					editor.updateElement();
					//에디터 내 이미지 파일 링크 정보 세팅
					createEditorFileLink("awardItemForm");
		    	}
		    	
				var confirmStr ="답글을 등록하시겠습니까?";
				if($("input[name=tempSave]").val()=="1"){
					confirmStr ="답글을 임시저장 하시겠습니까?";
				}else{
					confirmStr ="답글을 등록 하시겠습니까?";
				}
		    	if(confirm(confirmStr)) {
		    		if(document.all["FileUploadManager"].Count>0){
		    			btnTransfer_Onclick("awardItemForm");
		    		}else{
		    			//alert("파일업로드 없음");
		    			writeSubmit(form);
		    		}
							
				}
		    	
		    }
		});   
				 

	    
	    
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
	 	
	    dextFileUploadInit("${award.fileSize}" ,"${award.fileAttachOption}", "${award.allowType}");
	    
	});  	
	
	
	writeSubmit = function(targetForm){
	
		<c:if test="${award.contentsReadPermission eq 1}">
		if($("input[name=readerMailFlagDummy]").is(":checked")) {
			$("input[name=readerMailFlag]").val("1");
		} else {
			$("input[name=readerMailFlag]").val("0");
		}
		</c:if>
		
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
	<input name="awardId"  value="${award.awardId}" type="hidden"/> 
	<input name="interceptorKey"  value="lightpack.award" type="hidden"/> 
</form>
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
<!--pageTitle Start--> 
<div id="pageTitle" class="btnline"> 
	<h2>
		<c:choose>
			<c:when test="${user.localeCode == portal.defaultLocaleCode}">${award.awardName}</c:when>
			<c:otherwise>${award.awardEnglishName}</c:otherwise>
		</c:choose>
	</h2> 
	<div class="blockButton"> 
		<ul>
			<c:if test="${permission.isWritePermission}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
				<li><a class="button tempSaveAwardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='tempSave'/></span></a></li>
				<li><a class="button saveAwardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
			</c:if> 
			<li><a class="button" href="<c:url value='/lightpack/award/awardItem/readAwardItemView.do?awardId=${awardItem.awardId}&amp;itemId=${awardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>	
			<c:if test="${not popupYn}">	
				<li><a class="button" href="<c:url value='/lightpack/award/awardItem/listAwardItemView.do?awardId=${awardItem.awardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
			</c:if>
		</ul>
	</div>	
</div>  
<!--//pageTitle End--> 
<!--blockDetail Start-->
<div class="blockDetail">
	<form id="awardItemForm" method="post" action="<c:url value='/lightpack/award/awardItem/createReplyAwardItem.do'/>" enctype="multipart/form-data"> 
		<input name="searchConditionString" type="hidden" value="${searchConditionString}" />
		<input name="popupYn"       type="hidden" value="${popupYn}" />
		<input name="awardId"      	type="hidden" value="${award.awardId}"             title="<ikep4j:message pre='${preDetail}' key='awardId' />"/>
		<input name="itemParentId" 	type="hidden" value="${awardItem.itemId}"          title="<ikep4j:message pre='${preDetail}' key='itemParentId' />"/>  
		<input name="itemGroupId"  	type="hidden" value="${awardItem.itemGroupId}"     title="<ikep4j:message pre='${preDetail}' key='itemGroupId' />"/>  
		<input name="indentation"  	type="hidden" value="${awardItem.indentation + 1}" title="<ikep4j:message pre='${preDetail}' key='indentation' />"/>  
		<input name="step"         	type="hidden" value="${awardItem.step + 1}"        title="<ikep4j:message pre='${preDetail}' key='step'/>" />  
		<input name="itemDisplay"  	type="hidden" value="${awardItem.itemDisplay}"     title="<ikep4j:message pre='${preDetail}' key='itemDisplay'/>" />
		<input name="readerMailFlag"           type="hidden" value="0" /> 

		<input name="tempSave" 				type="hidden" value="${awardItem.tempSave}"/>
		<input name="anonymous"				type="hidden" value="${award.anonymous}" />
		<input name="msie"        	type="hidden" value="0" />  
		<table summary="<ikep4j:message pre="${preDetail}" key="summary" />">
			<caption></caption>
			<col style="width: 15%"/>
			<col style="width: 85%"/> 
			<tbody> 
			<tr> 
				<spring:bind path="awardItem.title">
				<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
						<input 
						name="${status.expression}" 
						type="text" 
						class="inputbox w100"
						value="[RE] <c:if test="${!empty awardItem.wordName}">[${awardItem.wordName}]</c:if> ${status.value}" 
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
			<th scope="row"><ikep4j:message pre='${preDetail}' key='registerName' /></th>
				<td>
					<c:choose>
						<c:when test="${award.anonymous eq 1}">
							<!-- <span><ikep4j:message pre='${preDetail}' key='anonymous'/></span>-->
							<spring:bind path="awardItem.registerName">  		
							<input 
							name="${status.expression}" 
							type="text" 
							class="inputbox w40" 
							<c:if test="${!empty status.value}">
							value="${status.value}"
							</c:if>
							<c:if test="${empty status.value}">
							value="익명"
							</c:if>
							size="40" 
							title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>"/>
							</spring:bind>
						</c:when>  
						<c:otherwise>  
							<c:set var="user"   value="${sessionScope['ikep.user']}" />
							<c:set var="portal" value="${sessionScope['ikep.portal']}" />
							<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									${user.userName} ${user.jobTitleName} ${user.teamName}
								</c:when>
								<c:otherwise>
									${user.userEnglishName} ${user.jobTitleEnglishName} ${user.teamEnglishName}
								</c:otherwise>
							</c:choose>						
						</c:otherwise> 
					</c:choose> 
				</td> 
			</tr>
			
			<c:if test="${award.contentsReadPermission eq 1}">
			<tr>
				<th scope="row">독서자 메일발송 여부</th>
				<td colspan="3">
					<spring:bind path="awardItem.readerMailFlag">   
							<input 
								name="readerMailFlagDummy" 
								type="checkbox" 
								class="checkbox" 
								value="${status.value}" 
								size="40" 
								/> 
							메일발송
					</spring:bind><c:if test="${awardItem.step ne 0}">&nbsp;&nbsp;&nbsp;※ 답글의 독서자는 원본글과 동일하게 설정됩니다. 답글의 메일발송여부만 설정가능.</c:if>
				</td>
			</tr>
			</c:if>	 
						

			<tr>  
			<spring:bind path="awardItem.contents">

			<td colspan="2" class="ckeditor">
				<!-- 에디터 부분 시작 -->
				<!--<script type="text/javascript" src="<c:url value='/base/namo/NamoWec7.js'/>" charset="UTF-8"></script> -->
				
				<!-- 에디터 부분 끝 -->
			 
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
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<c:if test="${permission.isWritePermission}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
			<li><a class="button tempSaveAwardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='tempSave'/></span></a></li>
			<li><a class="button saveAwardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
		</c:if> 
		<li><a class="button" href="<c:url value='/lightpack/award/awardItem/readAwardItemView.do?awardId=${awardItem.awardId}&amp;itemId=${awardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>	
		<c:if test="${not popupYn}">	
			<li><a class="button" href="<c:url value='/lightpack/award/awardItem/listAwardItemView.do?awardId=${awardItem.awardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		</c:if>
	 </ul>
</div>
<!--//blockButton End-->  
<c:if test="${popupYn}"></div></c:if>