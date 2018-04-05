<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.board.boardItem.createBoardView.header" /> 
<c:set var="preDetail"  value="message.collpack.collaboration.board.boardItem.createBoardView.detail" />
<c:set var="preCode"    value="message.collpack.collaboration.common.code" />
<c:set var="preButton"  value="message.collpack.collaboration.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.boardItem" />
<c:set var="preSearch"  value="message.collpack.collaboration.common.searchCondition" /> 

<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>    
<%@ include file="/base/common/DextfileUploadInit.jsp"%><%-- 파일업로드용 Import --%>

<script language="JScript" FOR="twe" EVENT="OnInsertImage(oElem)">
	<c:if test="${workspaceType eq '00003'}">
	    if(twe.GetAttachmentSize() > 1048576)
	    {
	       //alert('이미지 용량이 초과로 등록이 되지 않습니다.');
	       //oElem.parentNode.removeChild(oElem);
	    }    
    </c:if>
</script>
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
		iKEP.iFrameContentResize();
	
		if(window.parent.topScroll != null) {
			window.parent.topScroll(); 	
		} 
		
		if(window.parent.menuMark != null) { 
			window.parent.menuMark("${boardItem.boardId}");
		}
		
		// editor 초기화
		initEditorSet();

		$("input[name=startDate]").attr("readonly", true).datepicker({
			isActiveX : (useActXEditor == "Y" && $.browser.msie) ? true : false,
			onSelect : function(){
				var form = $(this)[0].form;
				var validator = $(form).validate();
				validator.element(this);
				validator.element("input[name=endDate]", form);
			},
		    showOn: "button",
		    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true,
		    buttonText : "<ikep4j:message pre='${preDetail}' key='startDate' />"
		});   
		
		$("input[name=endDate]").attr("readonly", true).datepicker({
			isActiveX : (useActXEditor == "Y" && $.browser.msie) ? true : false,
			onSelect : function(){
				var form = $(this)[0].form;
				var validator = $(form).validate();
				validator.element(this);
				validator.element("input[name=startDate]", form);
			},
		    showOn: "button",
		    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true,
		    buttonText : "<ikep4j:message pre='${preDetail}' key='endDate' />"
		});   
		$jq("input[name=followBoardPermission]").click(function() {
			if($jq("input[name=followBoardPermission]:checked").val()==0) // 개별
			{
				$jq('#readPermissionDiv').show();
				iKEP.iFrameContentResize();
			}
			else{
				$jq('#readPermissionDiv').hide();
				$jq('#readPermissionList').empty();
				$jq('input[name=searchSubFlag]:hidden').val("0") ;
				iKEP.iFrameContentResize();
			}
		});
		
		$jq("input[name=readPermission]").click(function() {
			if($jq("input[name=readPermission]:checked").val()==4)
			{
				$jq('#readPermissionListDiv').show();
				iKEP.iFrameContentResize();
				
			}
			else{
				$('#readPermissionListDiv').hide();
				$jq('#readPermissionList').empty();
				$jq('input[name=searchSubFlag]:hidden').val("0") ;
				iKEP.iFrameContentResize();
			}
		});
		
		$jq("#deleteReadPermissionButton").click(function(event) {
			event.preventDefault();
			var $rPermissionList=$jq('#readPermissionList');
			$jq('option:selected',$rPermissionList).remove();

		});	
				
		$jq("#addReadPermissionButton").click(function() {
			// 조직도 팝업 테스트

			var items = [];
			var $sel = $jq("#boardItemForm").find("[name=readPermissionList]");
			
			$jq.each($sel.children(), function() {
				items.push($jq.data(this, "data"));
			});

			$controlType = $jq('input[name=controlType]:hidden').val() ;
			$controlTabType = $jq('input[name=controlTabType]:hidden').val() ;
			$selectType = $jq('input[name=selectType]:hidden').val() ;
			$selectMaxCnt = $jq('input[name=selectMaxCnt]:hidden').val() ;
			$searchSubFlag = $jq('input[name=searchSubFlag]:hidden').val() ;

			var url = "<c:url value='/support/addressbook/addresbookPopup.do'/>"+"?controlType=" + $controlType + "&controlTabType=" + $controlTabType + "&selectType=" + $selectType + "&selectMaxCnt=" + $selectMaxCnt + "&searchSubFlag=" + $searchSubFlag;
			var callback = function(result){
				$sel.empty();
				$jq.each(result, function() {

					var tpl = "";
					
					switch(this.type) {
						case "group" : tpl = "addrBookItemGroup"; break;
						case "user" : tpl = "addrBookItemUser"; break;
					}
					
					if(this.type=="group")
						this.code="G:"+this.code;
					else
						this.id ="U:"+this.id;
					
					var $option = $jq.tmpl(tpl, this).appendTo($sel);

					$jq.data($option[0], "data", this);

					if( this.searchSubFlag == true ){
						$jq('input[name=searchSubFlag]:hidden').val("1") ;
					}
		
				})
			};
			iKEP.showAddressBook(callback, items, {selectType:"all", selectElement:$sel, isAppend:false, tabs:{common:0}});			
		});	 
		
		
		$jq.template("addrBookItemUser", "<option value='\${id}'>\${userName}/\${jobTitleName}/\${teamName}</option>");
		$jq.template("addrBookItemGroup", "<option value='\${code}'>\${name}</option>");
		//커서 포커스를 첫번째 Input에 넣는다.
		//$("input[name=title]").focus();
				
		$("a.listBoardItemButton").click(function() {
			$("#normalFileTb").show();
		}); 
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
			
			
			$("#boardItemForm input[name=ecmCheck]").attr("checked", true);  

			$("#boardItemForm input[name=ecmCheck]:checked").each(function(index) { 
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
			$("#boardItemForm").trigger("submit"); 
			return false;  
		});
		
		ecmfileDelete = function(){
			/* $jq("#ecmFileList option:selected").each(function () { 
		        $jq(this).remove();
		    });  */
		    $("#boardItemForm input[name=ecmCheck]:checked").each(function(index) { 
				$jq("#ecmTr_"+$(this).val()).remove();
			});

		};
		
		new iKEP.Validator("#boardItemForm", {   
			rules  : {
				title     : {required : true, rangelength : [1, 100] }
			},
			messages : {
				title     : {direction : "top",    required : "<ikep4j:message key="message.collpack.collaboration.NotNull.boardItem.title" />", rangelength : "<ikep4j:message key="message.collpack.collaboration.Size.boardItem.title" />"}
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
		    	
				
				if($jq('input[name=followBoardPermission]:checked').val()==1){
					$jq('input[name=readPermission]').val('${board.readPermission}') ;
				}
            
				$jq("#readPermissionList>option").attr("selected",true);	    	
		    	
				
		    	if(confirm("<ikep4j:message pre="${preMessage}" key="createItem" />")) {

		    			if(ecmFlg == "N"){
							if(document.all["FileUploadManager"].Count>0){
				    			btnTransfer_Onclick("boardItemForm");
				    		}else{
				    			writeSubmit(form);
				    		}
						}else{
				    			writeSubmit(form);
						}

					
				}
		    }
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
        
	    var fileController = new iKEP.FileController("#boardItemForm", "#fileUploadArea", uploaderOptions);
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
	
	writeSubmit = function(targetForm){
		if($("input[name=itemDisplayDummy]").is(":checked")) {
			$("input[name=itemDisplay]").val("1");
		} else {
			$("input[name=itemDisplay]").val("0");
		} 
		
		//if($("input[name=alarmYnCheck]").is(":checked")) {
		//	$("input[name=alarmYn]").val("1");
		//} else {
			$("input[name=alarmYn]").val("0");
		//} 

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
<c:if test="${popupYn}"><div class="contentIframe"></c:if>
<form id="editorFileUploadParameter" action="null"> 
	<input name="boardId"  value="${board.boardId}" type="hidden"/> 
	<input name="interceptorKey"  value="collpack.collaboration.board" type="hidden"/> 
</form> 
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
<!--pageTitle Start--> 
<div id="pageTitle" class="btnline"> 
	<h2>${board.boardName}</h2> 
	<div class="blockButton"> 
		<ul>
			<c:if test="${permission.isWritePermission}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
				<li><a class="button saveBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
			</c:if>  
			<li><a class="button listBoardItemButton" href='<c:url value='/collpack/collaboration/board/boardItem/listBoardItemView.do?boardId=${boardItem.boardId}&amp;searchConditionString=${searchConditionString}&amp;
=${popupYn}'/>'><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		</ul>
	</div>	
</div>  
<!--//pageTitle End--> 
<!--blockDetail Start-->
<div class="blockDetail">
<form id="boardItemForm" name="boardItemForm" method="post" action="<c:url value='/collpack/collaboration/board/boardItem/createBoardItem.do'/>" enctype="multipart/form-data">
	<input name="controlTabType" title="" type="hidden" value="0:1:0:0" />
	<input name="controlType" title="" type="hidden" value="ORG" />
	<input name="selectType" title="" type="hidden" value="ALL" />
	<input name="selectMaxCnt" title="" type="hidden" value="100" />
	<input name="searchSubFlag" title="" type="hidden" value="" />	
	
	<input name="boardId"               type="hidden" value="${board.boardId}" />
	<input name="searchConditionString" type="hidden" value="${searchConditionString}" />
	<input name="itemDisplay"           type="hidden" value="${boardItem.itemDisplay}" /> 
	<input name="alarmYn"           type="hidden" value="${boardItem.alarmYn}" />
	<input name="popupYn"               type="hidden" value="${popupYn}" />
	<input name="itemType" type="hidden" value="0"/>
	<input name="msie"        			type="hidden" value="0" />
	<input type="hidden" name="ecmFileId" value="" /> 
	<input type="hidden" name="ecmFileName" value="" />
	<input type="hidden" name="ecmFilePath" value=""/>  
	<input type="hidden" name="ecmFileUrl1" value=""/> 
	<input type="hidden" name="ecmFileUrl2" value=""/>  

	<table summary="<ikep4j:message pre="${preDetail}" key="summary" />">
		<caption></caption>
		<col style="width: 15%"/>
		<col style="width: *"/>
		<tbody> 
		<tr> 
			<spring:bind path="boardItem.title">
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td> 
				<div>
					<input 
						name="${status.expression}" 
						type="text" 
						class="inputbox" style="width: 70%;" 
						value="${status.value}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						/> 
						<c:forEach items="${status.errorMessages}" var="error">
					    	<label for="${status.expression}" class="serverError"> ${error}</label>
					    </c:forEach> 
					</spring:bind>
					
					<c:if test="${permission.isSystemAdmin}">
					<spring:bind path="boardItem.itemDisplay">   
						<input 
							name="itemDisplayDummy" 
							type="checkbox" 
							class="checkbox" 
							value="${status.value}" 
							size="40" 
							<c:if test="${status.value eq 1}">checked="checked"</c:if>
							title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
							/> 
						<ikep4j:message pre='${preDetail}' key='${status.expression}' /> 
					</spring:bind> 
					</c:if>
					<%-- <spring:bind path="boardItem.alarmYn">   
						<input 
							name="alarmYnCheck" 
							type="checkbox" 
							class="checkbox" 
							value="${status.value}" 
							size="40" 
							/> 
						<ikep4j:message pre='${preDetail}' key='${status.expression}' /> 
					</spring:bind> --%>
				</div>			
			</td>  
		</tr>				
		<tr>  
			<th scope="row"><ikep4j:message pre='${preDetail}' key='registerName' /></th>
			<td>
				<c:choose>
					<c:when test="${board.anonymous eq 1}">
						<span><ikep4j:message pre='${preDetail}' key='anonymous'/></span>
					</c:when>  
					<c:otherwise>  
						<c:set var="user"   value="${sessionScope['ikep.user']}" />
						<c:set var="portal" value="${sessionScope['ikep.portal']}" />
						<c:set var="userTitle"   value="${user.jobDutyName}" />
						<c:if test="${ empty userTitle}">
							<c:set var="userTitle"   value="${user.jobTitleName}" />
						</c:if>
						<c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}">
								${user.userName} ${userTitle} ${user.teamName}
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
				title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>"/>
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
		<tr>			
			<th scope="row"><ikep4j:message pre='${preDetail}' key='readPermission' /></th>
			<td>
				<spring:bind path="boardItem.followBoardPermission">
				<div style="padding-bottom:4px;">
				<c:forEach var="code" items="${boardCode.followBoardPermission}"> 
					<label>
					<input name="${status.expression}" 
					type="radio" 
					class="radio" 
					value="${code.key}" 
					size="40" 
					title="<ikep4j:message key='${code.value}'/>"
					<c:if test="${code.key eq status.value}">checked="checked"</c:if>
					/> 
					<ikep4j:message key='${code.value}'/>
					</label>
				</c:forEach>  
				</div>
				</spring:bind>
				
				<spring:bind path="boardItem.readPermission">
				<div  id="readPermissionDiv" style="display:none" style="padding-bottom:4px;">
				<c:forEach var="code" items="${boardCode.permissionList}" varStatus="varStatus"> 
					<label>
					<input name="${status.expression}" 
					type="radio" 
					class="radio" 
					value="${code.key}" 
					size="40" 
					title="<ikep4j:message key='${code.value}'/>"
					<c:if test="${code.key eq status.value}">checked="checked"</c:if>
					/> 
					<ikep4j:message key='${code.value}'/>
					</label>
				</c:forEach> 
				</div>
				
				<div id="readPermissionListDiv" style="display:none">
					<select name="readPermissionList" id="readPermissionList" class="input_select w80" size="4"	style="height:100px;" multiple>
					</select>						
					<a id="addReadPermissionButton" class="button_ic valign_bottom" href="#a" title="<ikep4j:message pre='${preButton}' key='addReadPermission'/>"><span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" alt="" /><ikep4j:message pre='${preButton}' key='addReadPermission'/></span></a>
					<a id="deleteReadPermissionButton" class="button_ic valign_bottom" href="#a" title="<ikep4j:message pre='${preButton}' key='deleteReadPermission'/>"><span><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="" /><ikep4j:message pre='${preButton}' key='deleteReadPermission'/></span></a>
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
		<c:if test="${permission.isWritePermission}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
			<li><a class="button saveBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
		</c:if>  
		<li><a class="button listBoardItemButton" href='<c:url value='/collpack/collaboration/board/boardItem/listBoardItemView.do?boardId=${boardItem.boardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>'><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
	 </ul>
</div>
<!--//blockButton End-->  
<c:if test="${popupYn}"></div></c:if>