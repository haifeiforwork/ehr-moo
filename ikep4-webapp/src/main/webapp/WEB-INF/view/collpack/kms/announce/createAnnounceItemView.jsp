<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/DextfileUploadInit.jsp"%><%-- 파일업로드용 Import --%> 
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
<c:set var="preHeader"  value="message.collpack.kms.announce.boardItem.listBoardView.header" />
<c:set var="preDetail"  value="message.collpack.kms.announce.boardItem.createBoardView.detail" /> 
<c:set var="preList"    value="message.collpack.kms.announce.boardItem.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.kms.announce.boardItem.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.kms.announce.boardItem.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.kms.announce.boardItem.message.script" />
 <%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

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
//activeX editor 사용여부
var ecmFlg = "N";
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

		// editor 초기화
		initEditorSet();
		
		var validOptions = {
			rules : {
				title : {
					required : true,
					maxlength : 1024
				}
				/** ,
				tag : {required : true, tagDuplicate : true} **/
			},
			messages : {
				title : {
					direction	:	"top",
					required	:	"<ikep4j:message key="NotEmpty.announce.title" />",
					maxlength	:	"<ikep4j:message key="Size.announce.title" />"

				}
				/** ,
				tag       : {
					direction : "bottom",
					required : "<ikep4j:message key="message.collpack.kms.NotBlank.boardItem.tag" />", 
					tagDuplicate : "<ikep4j:message key="TagDuplicate.boardItem.tag" />"} **/
			},
			submitHandler : function(form) {
				
		    	// ActiveX Editor 사용 여부가 Y인 경우
		    	if(useActXEditor == "Y"){
			    	if (!$.browser.msie) {
			    		//ekeditor 데이타 업데이트
			    		var editor = $("#contents").ckeditorGet(); 
						editor.updateElement();
						//에디터 내 이미지 파일 링크 정보 세팅
						createEditorFileLink("announceItemForm");
			    	}
		    	}else{
		    		//ekeditor 데이타 업데이트
		    		var editor = $("#contents").ckeditorGet(); 
					editor.updateElement();
					//에디터 내 이미지 파일 링크 정보 세팅
					createEditorFileLink("announceItemForm");
		    	}
				
				// 부가 검증 처리
				if(confirm("<ikep4j:message pre="${preMessage}" key="createItem" />")) {
					if(ecmFlg == "N"){
		    		if(document.all["FileUploadManager"].Count>0){
		    			btnTransfer_Onclick("announceItemForm");
		    		}else{
		    			//alert("파일업로드 없음");
		    			writeSubmit(form);
		    		}
					}else{
						writeSubmit(form);
					}
				}
			}
		}
	
		var validator = new iKEP.Validator("#announceItemForm", validOptions);
	
		$("#listAnnounceItemButton").click(function() { 
			$("#normalFileTb").show();
		});
		$("#saveAnnounceItemButton").click(function() { 
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
			
			
			$("#announceItemForm input[name=ecmCheck]").attr("checked", true);  

			$("#announceItemForm input[name=ecmCheck]:checked").each(function(index) { 
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
			//var editor = $("#contents").ckeditorGet(); 
			//editor.updateElement(); 
			
			$jq("#announceItemForm").submit();
			return false;
		});
		
		/*
		var uploaderOptions = {
			 	<c:if test="${empty fileDataListJson}">files : [],</c:if> 
			 	<c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if> 
				<c:if test="${board.fileAttachOption eq 0 and not empty board.allowType}">allowExt : "${board.allowType}",</c:if>
				<c:if test="${board.fileAttachOption eq 1 and not empty board.allowType}">allowFileType : "${board.allowType}",</c:if>
		    	isUpdate : true ,
		    	onLoad : function() {
		    		iKEP.iFrameContentResize();
		    	}
		    };  
		
		var fileController = new iKEP.FileController("#announceItemForm", "#fileUploadArea", uploaderOptions);
		*/
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
	    $("#announceItemForm input[name=ecmCheck]:checked").each(function(index) { 
			$jq("#ecmTr_"+$(this).val()).remove();
		});

	};
	
	writeSubmit = function(targetForm){
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
	    		$("#twe").css("visibility","hidden");
	    	}
		}
	
		$("#normalFileTb").show();
		$("body").ajaxLoadStart("button");
		targetForm.submit();
	}
	
	/* editor 초기화  */
	initEditorSet = function() {
		// ActiveX Editor 사용 여부가 Y인 경우
	    if(useActXEditor == "Y"){
			// 브라우저가 ie인 경우
			if($.browser.msie) {
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
			} else {
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
<!--
	/* 태그프리 에디터 줄바꿈 태그 P => br 로 변경하는 메소드 */
	if (!event.shiftKey && event.keyCode == 13){
		twe.InsertHtml("<br>");
		event.returnValue = true; <!-- Active Designer에서의 처리를 막음 -->
	}
	if (event.shiftKey && event.keyCode == 13){
		twe.InsertHtml("<p>");
		event.returnValue = true; <!-- Active Designer에서의 처리를 막음 -->
	}	
//-->
</script>
<script language="JScript" for="twe" event="OnControlInit()">	
	$jq("input[name=title]").focus();
</script>

<form id="editorFileUploadParameter"> 
	<input name="interceptorKey"  value="ikep4.system"    type="hidden"/>
</form>
<h1 class="none">지식공지</h1> 
<!--pageTitle Start-->
<div id="pageTitle" class="btnline"> 
	<h2>지식공지</h2> 
</div> 
<!--//pageTitle End--> 
<!--blockDetail Start-->
<div class="blockDetail">
	<form id="announceItemForm" name="announceItemForm" method="post" action="<c:url value='/collpack/kms/announce/createAnnounceItem.do'/>" enctype="multipart/form-data">
	<input name="msie"        			type="hidden" value="0" />
	<input type="hidden" name="ecmFileId" value="" /> 
	<input type="hidden" name="ecmFileName" value="" />
	<input type="hidden" name="ecmFilePath" value=""/>  
	<input type="hidden" name="ecmFileUrl1" value=""/> 
	<input type="hidden" name="ecmFileUrl2" value=""/>  
	<table summary="게시판등록">
		<caption></caption>
		<col style="width: 15%"/>
		<col style="width: 85%"/>
<!-- 		<col style="width: 15%"/> -->
<!-- 		<col style="width: 35%"/> -->
		<tbody> 
		<tr> 
			
			<spring:bind path="announceItem.title">
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
				    		<label for="${status.expression}" class="serverError"> ${error}</label>
					</spring:bind>
					<spring:bind path="announceItem.itemDisplay">  
						<input 
						name="${status.expression}" 
						type="checkbox" 
						class="checkbox" 
						value="1" 
						size="40" 
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						/>
						<ikep4j:message pre='${preDetail}' key='${status.expression}' /> 
					</spring:bind> 		
				</div>	
			</td> 
		</tr>				
		<tr>  
			<th scope="row"><ikep4j:message pre='${preDetail}' key='registerName' /></th>
			<td>
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					${user.userName}
				</c:when>
				<c:otherwise>
					${user.userEnglishName}
				</c:otherwise>
				</c:choose>				
			</td>
		</tr>	
		<%--tr>  
				<spring:bind path="announceItem.tag">
				<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td colspan="3">
					<div>
					<input name="${status.expression}" type="text" 
					class="inputbox w40" value="${status.value}" 
					size="40" title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"/>
					<ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" />
					</div>	
				</td>		
				</spring:bind>
		<tr--%> 		
		<tr>  
			
			<td colspan="4">
					<spring:bind path="announceItem.contents">
						<div id="editorDiv">
						<textarea id="contents"
						name="${status.expression}" 
						class="tabletext fullEditor"
						cols="" 
						rows="20" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />">${status.value}</textarea>
						</div>
					</spring:bind> 
			</td> 
			
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
<!--//blockDetail End--> 
<c:if test="${ecmrole && user.essAuthCode != 'E9'}">
	<iframe width="0" height="0" src="<c:url value="/base/common/destinySLO.jsp?TARGET_URL=install"/>"></iframe>
	<%-- <iframe width="0" height="0" src="<c:url value="/base/common/file_sample.jsp"/>"></iframe> --%>
	<iframe id="select_dialog" src="" style="display:none;"></iframe>
	</c:if>									
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="saveAnnounceItemButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			<li><a id="listAnnounceItemButton" class="button" href="<c:url value='/collpack/kms/announce/listAnnounceItemView.do'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		 </ul>
	</div>
	<!--//blockButton End--> 

