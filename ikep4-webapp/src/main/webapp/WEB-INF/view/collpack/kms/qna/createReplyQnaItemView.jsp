<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ page import="destiny.link.slo.service.DestinySLO"%>
<%!
public void setSystemProperty( String key, String value) {
    System.setProperty( key, value);
}

public void setCookie( HttpServletResponse response, String key, String value) {
    Cookie cookie = new Cookie( key, value);
    cookie.setPath( "/");
    cookie.setMaxAge( -1);
    cookie.setVersion( 0);
    //cookie.setComment( "destiny slo test");
    cookie.setSecure( false);
    response.addCookie( cookie);
}
%>
<%
// ECM Server Address
String sloServerAddress = "http://ecm.moorim.co.kr";
String sloAPIKey = "0VbXsZYobdOnciJmv4GQ3h16EvOjAoF0icK5sHMSvX4=";

// GW Login User Account
String userAccount = ((com.lgcns.ikep4.support.user.member.model.User)session.getAttribute("ikep.user")).getUserId(); // 로그인 사용자ID
// GW Login User's GroupCode
String userGroupCode = ((com.lgcns.ikep4.support.user.member.model.User)session.getAttribute("ikep.user")).getGroupId(); // 로그인 사용자 부서코드
// ECM Settings
setSystemProperty( "common.SloAddrKey", sloServerAddress);
setSystemProperty( "common.SloAPIKey", sloAPIKey);
setCookie( response, "ACCOUNT", userAccount);
setCookie( response, "GROUP_CODE", userGroupCode);
%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.lightpack.board.boardItem.createReplyBoardView.header" /> 
<c:set var="preDetail"  value="ui.lightpack.board.boardItem.createReplyBoardView.detail" />
<c:set var="preButton"  value="ui.lightpack.common.button" /> 
<c:set var="preMessage" value="message.lightpack.common.boardItem" /> 
<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script> 
<%@ include file="/base/common/DextfileUploadInit.jsp"%><%-- 파일업로드용 Import --%>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
var fileCnt = 0;

function CyberdigmpopupSelect( action) { 
	        var iframe = document.getElementById( "select_dialog");
	        var callbackFn = 'CyberdigmselectItem';

	        //팝업 설정( 해당 다이얼로그에 맞도록 수정)
	        var settings = "&settings=width:665,height:480,location:0,menubar:0,resizable:0,scrollbars:0,status:0,toolbar:0";

	        iframe.src = "<c:url value='/base/common/destinySLO.jsp?TARGET_URL=popup&action='/>" + action + "&callBack=" + callbackFn + settings;
	    }

function CyberdigmselectItem( _p, type) {
	var data = eval( "(" + decodeURIComponent( _p) + ")");

	for( var i = 0; i < data.length; i++){

    	//내부URL(에이전트 설치 시)
        var fileUrl1 = "http://127.0.0.1:36482/viewFile?fileName=" + encodeURIComponent( data[i].fileName) + "&docID=" + data[i].targetOID + "&fileID_=" + data[i].OID + "&history=true&overWrite=true&recently=true&clientType=I";

    	//외부URL(에이전트 미설치 시)

    	var index = data[i].fileFullPath.indexOf( '?');

    	var str = data[i].fileFullPath.substring( index + 1, data[i].fileFullPath.length);

    	//개발서버
        //var fileUrl2 = data[i].fileFullPath;

    	//운영
        var fileUrl2 =  "http://ecm.moorim.co.kr:80/servlet/blob?" + str;

    	//모바일용 외부 URL
    	var fileUrl3 = data[i].fileFullPath;

        fileCnt++;
        
        $jq("#ecmTb").append(
				"<tr id=\"ecmTr_"+data[i].OID+"\">"+
				"<td><input name=\"ecmCheck\" type=\"checkbox\" class=\"checkbox\" value=\""+data[i].OID+"\" /></td>"+
					"<td><input name=\"ecmFileName_"+data[i].OID+"\" id=\"ecmFileName_"+data[i].OID+"\" type=\"text\" value=\""+data[i].fileName+"\" class=\"inputbox w100\" readonly=\"readonly\" /></td>"+
					"<td><input name=\"ecmFileUrl1_"+data[i].OID+"\" id=\"ecmFileUrl1_"+data[i].OID+"\" type=\"text\" value=\""+fileUrl1.replaceAll("'","")+"\" class=\"inputbox w100\" readonly=\"readonly\" /></td>"+
					"<input name=\"ecmFileUrl2_"+data[i].OID+"\" id=\"ecmFileUrl2_"+data[i].OID+"\" type=\"hidden\" value=\""+fileUrl2.replaceAll("'","")+"\" />"+
					"<input name=\"ecmFilePath_"+data[i].OID+"\" id=\"ecmFilePath_"+data[i].OID+"\" type=\"hidden\" value=\""+data[i].fileFullPath+"\" />"+
					"<input name=\"ecmFileId_"+data[i].OID+"\" id=\"ecmFileId_"+data[i].OID+"\" type=\"hidden\" value=\""+data[i].OID+"\" />"+
				"</tr>"
			);
    }
    
    iKEP.iFrameContentResize();
}	
<!--   
var ecmFlg = "N";
//activeX editor 사용여부
var useActXEditor = "${useActiveX}";
(function($){	 
	$(document).ready(function() {   
		<c:if test="${ecmrole && user.essAuthCode != 'E9'}">
		$("#ecmBtn").show();
     	$("#ecmTb").show();
		var xmlhttp; 
		  
	    if(window.XMLHttpRequest){ // code for IE7+, Firefox, Chrome, Opera, Safari 
	        xmlhttp=new XMLHttpRequest(); 
	    }else{ // code for IE6, IE5 
	        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP"); 
	    } 
	    xmlhttp.open("POST","http://127.0.0.1:36482/",true); 
	    //Request에 따라서 적절한 헤더 정보를 보내준다 
	    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded"); 
	    xmlhttp.send(); 
	  
	    xmlhttp.onreadystatechange=function(){ 
	    	if (xmlhttp.readyState==4 && xmlhttp.status==200){ 
	        	 //$("#normalFileTb").hide();
	          	 //$("#ecmBtn").show();
	          	 //$("#ecmTb").show();
	          	ecmFlg = "Y";
	        }else if(xmlhttp.status==12029){ 
	          	 $("#ecmBtn").hide();
	          	 $("#ecmTb").hide();
	          	 $("#normalFileTb").show();
			} 
	    } 
	    </c:if>
	    <c:if test="${!ecmrole || user.essAuthCode == 'E9'}">
	    	$("#normalFileTb").show();
	    </c:if>
		if(window.parent.topScroll != null) {
			window.parent.topScroll(); 	
		} 
		
		if(window.parent.menuMark != null) { 
			window.parent.menuMark("${qnaItem.boardId}");
		}
		
		$("a.saveBoardItemButton").click(function() { 
			var tempEcmFileId = "";  
			var tempEcmFileName = ""; 
			var tempEcmFilePath = "";  
			var tempEcmFileUrl1 = "";    
			var tempEcmFileUrl2 = ""; 
			var ecmFileCnt = 0;
			var ecmFileId = "";
			var ecmFileName = "";
			var ecmFilePath = "";
			var ecmFileUrl1 = "";
			var ecmFileUrl2 = "";
			
			
			$("#qnaItemForm input[name=ecmCheck]").attr("checked", true);  

			$("#qnaItemForm input[name=ecmCheck]:checked").each(function(index) { 
				tempEcmFileId = $("#ecmFileId_"+$(this).val()).val();  
				tempEcmFileName = $("#ecmFileName_"+$(this).val()).val(); 
				tempEcmFilePath = $("#ecmFilePath_"+$(this).val()).val();   
				tempEcmFileUrl1 = $("#ecmFileUrl1_"+$(this).val()).val();     
				tempEcmFileUrl2 = $("#ecmFileUrl2_"+$(this).val()).val();  
				if(ecmFileCnt == 0){
					ecmFileId = tempEcmFileId;
					ecmFileName = tempEcmFileName;
					ecmFilePath = tempEcmFilePath;
					ecmFileUrl1 = tempEcmFileUrl1;
					ecmFileUrl2 = tempEcmFileUrl2;
				}else{
					ecmFileId = ecmFileId+"|"+tempEcmFileId;
					ecmFileName = ecmFileName+"|"+tempEcmFileName;
					ecmFilePath = ecmFilePath+"|"+tempEcmFilePath;
					ecmFileUrl1 = ecmFileUrl1+"|"+tempEcmFileUrl1;
					ecmFileUrl2 = ecmFileUrl2+"|"+tempEcmFileUrl2;
				}
				ecmFileCnt++;
			});
			
			$jq('input[name=ecmFileId]').val(ecmFileId);  
			$jq('input[name=ecmFileName]').val(ecmFileName); 
			$jq('input[name=ecmFilePath]').val(ecmFilePath);  
			$jq('input[name=ecmFileUrl1]').val(ecmFileUrl1);    
			$jq('input[name=ecmFileUrl2]').val(ecmFileUrl2);
			$("input[name=tempSave]").val("0");
			$("#qnaItemForm").trigger("submit"); 
			return false;  
		});
		
		$("a.tempSaveBoardItemButton").click(function() {
			$("input[name=tempSave]").val("1");
			$("#qnaItemForm").trigger("submit"); 
			return false;  
		});
		
		// editor 초기화
		initEditorSet();
		
		
		new iKEP.Validator("#qnaItemForm", {   
			rules  : {
				title     : {required : true, rangelength : [1, 100] }
			},
			messages : {
				title     : {direction : "top",    required : "<ikep4j:message key="NotNull.qnaItem.title" />", rangelength : "<ikep4j:message key="Size.qnaItem.title" />"}
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
		    		var editor = $jq('#qnaItemForm :input[name=contents]').ckeditorGet(); 
					editor.updateElement();
					//에디터 내 이미지 파일 링크 정보 세팅
					createEditorFileLink("qnaItemForm");
		    	}
		    	
				var confirmStr ="답글을 등록하시겠습니까?";
				if($("input[name=tempSave]").val()=="1"){
					confirmStr ="답글을 임시저장 하시겠습니까?";
				}else{
					confirmStr ="답글을 등록 하시겠습니까?";
				}
		    	if(confirm(confirmStr)) {
		    		if(document.all["FileUploadManager"].Count>0){
		    			btnTransfer_Onclick("qnaItemForm");
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
	    if(ecmFlg == "N"){
	    dextFileUploadInit("${board.fileSize}" ,"${board.fileAttachOption}", "${board.allowType}");
	    }
	    
	});  	
	
	ecmfileDelete = function(){
		/* $jq("#ecmFileList option:selected").each(function () { 
	        $jq(this).remove();
	    });  */
	    $("#qnaItemForm input[name=ecmCheck]:checked").each(function(index) { 
			$jq("#ecmTr_"+$(this).val()).remove();
		});

	};
	
	writeSubmit = function(targetForm){
	
		<c:if test="${board.contentsReadPermission eq 1}">
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
		$("#normalFileTb").show();
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


<form id="editorFileUploadParameter" action="null"> 
	<input name="boardId"  value="${board.boardId}" type="hidden"/> 
	<input name="interceptorKey"  value="lightpack.board" type="hidden"/> 
</form>
<h1 class="none">무림지식인(Q&A)</h1> 
<!--pageTitle Start--> 
<div id="pageTitle" class="btnline"> 
	<h2>
		무림지식인(Q&A)
	</h2> 
	<div class="blockButton"> 
		<ul>
			<c:if test="${permission.isWritePermission}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
				<li><a class="button tempSaveBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='tempSave'/></span></a></li>
				<li><a class="button saveBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
			</c:if> 
			<li><a class="button" href="<c:url value='/collpack/kms/qna/readQnaItemView.do?boardId=${qnaItem.boardId}&amp;itemId=${qnaItem.itemId}&amp;searchConditionString=${searchConditionString}'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>	
				<li><a class="button" href="<c:url value='/collpack/kms/qna/listQnaItemView.do?boardId=${qnaItem.boardId}&amp;searchConditionString=${searchConditionString}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		</ul>
	</div>	
</div>  
<!--//pageTitle End--> 
<!--blockDetail Start-->
<div class="blockDetail">
	<form id="qnaItemForm" method="post" action="<c:url value='/collpack/kms/qna/createReplyQnaItem.do'/>" enctype="multipart/form-data"> 
		<input name="searchConditionString" type="hidden" value="${searchConditionString}" />
		<input name="boardId"      	type="hidden" value="${board.boardId}"             title="<ikep4j:message pre='${preDetail}' key='boardId' />"/>
		<input name="itemParentId" 	type="hidden" value="${qnaItem.itemId}"          title="<ikep4j:message pre='${preDetail}' key='itemParentId' />"/>  
		<input name="itemGroupId"  	type="hidden" value="${qnaItem.itemGroupId}"     title="<ikep4j:message pre='${preDetail}' key='itemGroupId' />"/>  
		<input name="indentation"  	type="hidden" value="${qnaItem.indentation + 1}" title="<ikep4j:message pre='${preDetail}' key='indentation' />"/>  
		<input name="step"         	type="hidden" value="${qnaItem.step + 1}"        title="<ikep4j:message pre='${preDetail}' key='step'/>" />  
		<input name="itemDisplay"  	type="hidden" value="${qnaItem.itemDisplay}"     title="<ikep4j:message pre='${preDetail}' key='itemDisplay'/>" />
		<input name="tempSave" 				type="hidden" value="${qnaItem.tempSave}"/>
		<input name="anonymous"				type="hidden" value="${board.anonymous}" />
		<input name="msie"        	type="hidden" value="0" />  
		<input type="hidden" name="ecmFileId" value="" /> 
	<input type="hidden" name="ecmFileName" value="" />
	<input type="hidden" name="ecmFilePath" value=""/>  
	<input type="hidden" name="ecmFileUrl1" value=""/> 
	<input type="hidden" name="ecmFileUrl2" value=""/>  
		<table summary="<ikep4j:message pre="${preDetail}" key="summary" />">
			<caption></caption>
			<col style="width: 15%"/>
			<col style="width: 85%"/> 
			<tbody> 
			<tr> 
				<spring:bind path="qnaItem.title">
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
					</div>					
				</td>  
				</spring:bind> 
			</tr>
			<tr>  
			<th scope="row"><ikep4j:message pre='${preDetail}' key='registerName' /></th>
				<td>
					<c:choose>
						<c:when test="${board.anonymous eq 1}">
							<!-- <span><ikep4j:message pre='${preDetail}' key='anonymous'/></span>-->
							<spring:bind path="qnaItem.registerName">  		
							<input 
							name="${status.expression}" 
							type="text" 
							class="inputbox w40" 
							value="${status.value}"
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
			
			<tr>  
			<spring:bind path="qnaItem.contents">

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
		<table style="width:100%;display:none;" id="normalFileTb">
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
	<table style="width:100%;display:none;" id="ecmTb">
		<tr><th style="width:3%;text-align:center;"></th><th style="width:17%;text-align:center;">파일명</th><th style="width:80%;text-align:center;">URL</th></tr>
	</table>
	<table style="width:100%;display:none;" id="ecmBtn">
		<tr>
			<td style="border-left:none;border-color:#e5e5e5;background-color:#e8e8e8;text-align:right;">
			<img src="<c:url value="/base/images/dextupload/btn_fileplus_normal.gif"/>" id="imgBtn_fileAdd" name="Image2"  border="0" onclick="CyberdigmpopupSelect('selectAllFiles');" style="cursor:pointer;valign:absmiddle" />
			<img src="<c:url value="/base/images/dextupload/btn_listdelete_normal.gif"/>" name="Image4"  border="0" onclick="ecmfileDelete();" style="cursor:pointer;valign:absmiddle" />	
			</td>
		</tr>
	</table>	
	</form>
</div>

<c:if test="${ecmrole && user.essAuthCode != 'E9'}">
	<iframe width="0" height="0" src="<c:url value="/base/common/destinySLO.jsp?TARGET_URL=install"/>"></iframe>
	<%-- <iframe width="0" height="0" src="<c:url value="/base/common/file_sample.jsp"/>"></iframe> --%>
	<iframe id="select_dialog" src="" style="display:none;"></iframe>
	</c:if>		
<!--//blockDetail End-->  									
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
			<li><a class="button saveBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
		<li><a class="button" href="<c:url value='/collpack/kms/qna/readBoardItemView.do?boardId=${qnaItem.boardId}&amp;itemId=${qnaItem.itemId}&amp;searchConditionString=${searchConditionString}'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>	
			<li><a class="button" href="<c:url value='/collpack/kms/qna/listBoardItemView.do?boardId=${qnaItem.boardId}&amp;searchConditionString=${searchConditionString}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
	 </ul>
</div>
<div class="blockBlank_50px"></div>	
	<div class="blockBlank_50px"></div>
<!--//blockButton End-->  