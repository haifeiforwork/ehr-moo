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
<c:set var="preButton"  value="message.collpack.kms.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.boardItem" />
<c:set var="preSearch"  value="message.collpack.collaboration.common.searchCondition" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>   
<script type="text/javascript" src="<c:url value='/base/js/zeroclipboard/ZeroClipboard.js'/>"></script> 
<%@ include file="/base/common/DextfileUploadInit.jsp"%><%-- 파일업로드용 Import --%>
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
var useActXEditor = "${useActiveX}";

var ecmFlg = "N";
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
			iKEP.showAddressBook(callback, items, {selectType:"all", selectElement:$sel, isAppend:false, tabs:{common:1}});			
		});	 
		//커서 포커스를 첫번째 Input에 넣는다.
		//$("input[name=title]").focus();
		
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
			$("input[name=status]").val("1");
  			
			var isKnowhow = $("input[name=isKnowhow1]:checked").attr("value");
			if(isKnowhow == "0"){
				if($("#assessorId").val() == ""){
					alert("업무노하우 등록은 전문가를 선택해야합니다.");
					return;
				}
			}
			
			$("input[name=isKnowhow]").val(isKnowhow);
			
			//var hopeGrade = $("input[name=hopeGradeCheck]:checked").attr("value");
			
			//alert(hopeGrade);
			
			var $sel1 = $jq("input[name=hopeGradeCheck]:checked");
			if($sel1.length > 0) {
				var hopeGrade = $("input[name=hopeGradeCheck]:checked").attr("value");
				$("#boardItemForm input[name=hopeGrade]").val(hopeGrade); 
			}else{
				alert("보안등급을 선택해 주세요.");
				return;
			}
			
			if(isKnowhow == "3"){
				$("input[name=infoGrade]").val(hopeGrade);
				$("input[name=status]").val("3");
			}
			
			if($("input[name=reportReqCk]").is(":checked")) {
				$("input[name=reportReq]").val("1");
			}else{
				$("input[name=reportReq]").val("0");
			}
			
			var boardId = $("input[name=boardId]").val();
			if(boardId == 0 || boardId == ""){
				alert("지식맵을 선택해 주세요.");
				return false;
			}else{
				$("#boardItemForm").trigger("submit"); 
				return false;				
			}
		});
		
		$("a.listBoardItemButton").click(function() {
			$("#normalFileTb").show();
		}); 
		$("a.tempSaveBoardItemButton").click(function() {
			
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
			$("input[name=status]").val("0");
			
			var isKnowhow = $("input[name=isKnowhow1]:checked").attr("value");
			$("input[name=isKnowhow]").val(isKnowhow)
			
			var $sel1 = $jq("input[name=hopeGradeCheck]:checked");
			if($sel1.length > 0) {
				var hopeGrade = $("input[name=hopeGradeCheck]:checked").attr("value");
				$("#boardItemForm input[name=hopeGrade]").val(hopeGrade); 
			}else{
				alert("보안등급을 선택해 주세요.");
				return;
			}
			if($("input[name=reportReqCk]").is(":checked")) {
				$("input[name=reportReq]").val("1");
			}else{
				$("input[name=reportReq]").val("0");
			}
			
			var boardId = $("input[name=boardId]").val();
			if(boardId == 0 || boardId == ""){
				alert("지식맵을 선택해 주세요.");
				return false;
			}else{
				$("#boardItemForm").trigger("submit"); 
				return false;				
			}
		});
		
		isKnowChk = function(tmpChk){
			if(tmpChk == "2"){
				$('#expertDiv').show();
			}else{
				$('#expertDiv').hide();
			}
		};
		
		setExpert = function(tmpUserId, tmpUserName, tmpTeam, tmpJob) {
			$("#assessor").val(tmpUserName+" "+tmpJob+" "+tmpTeam);
			$("#assessorId").val(tmpUserId);
			$("#assessorName").val(tmpUserName);
		}
		
		
		$jq('#addPersonButton').live("click", function() {		
			var url = iKEP.getContextRoot() + '/collpack/kms/board/boardItem/expertView.do';   
			iKEP.popupOpen(url, {width:500, height:500}, "popview");
		});
		
		new iKEP.Validator("#boardItemForm", {   
			rules  : {
				title     		: {required : true, rangelength : [1, 1000] },
				targetSource	: {required : true},
				tag       		: {tagDuplicate : true}
			},
			messages : {
				title     		: {direction : "top",    required : "<ikep4j:message key="message.collpack.collaboration.NotNull.boardItem.title" />", rangelength : "<ikep4j:message key="message.collpack.collaboration.Size.boardItem.title" />"},
				targetSource	: {direction : "top",    required : "출처는 필수 입력항목입니다."},
				tag       		: {direction : "top", 	 tagDuplicate : "<ikep4j:message key="message.collpack.collaboration.TagDuplicate.boardItem.tag" />"} 
			},   
			
		    submitHandler : function(form) {
		    	
		    	$(form).find('select[name=targetGroupList] option').each(function(){
					this.selected = true;
				});
		    	
		    	$(form).find('select[name=targetList] option').each(function(){
					this.selected = true;
				});
  
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
		    			//alert("파일업로드 없음");
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
	    $jq("#btnAddrControl,#btnGroupAddrControl").click(function() {
		var $container = $(this).parent();
		var $select = $("select", $container);
		var isUser = $select.attr("id") == "targetList";

		var items = [];
		$jq.each($select.children(), function() {
			items.push($.data(this, "data"));
		});

		iKEP.showAddressBook(function(result){
			/*$select.empty();
			$jq.each(result, function() {
				$.tmpl(isUser ? iKEP.template.userOption : iKEP.template.groupOption, this).appendTo($select)
					.data("data", this);
			})*/
			
			var msgTotal = "<ikep4j:message key='ui.servicepack.survey.common.total'/>" + $select.children().length + (isUser ? "<ikep4j:message key='ui.servicepack.survey.common.totalMemberMsg'/>" : "<ikep4j:message key='ui.servicepack.survey.common.totalGroupMsg'/>");
			$("span.totalNum_s", $container).text(msgTotal);
		}, items, {selectElement:$select, selectType:isUser ? "user" : "group",selectMaxCnt:2000, tabs:{common:0, personal:1, collaboration:0, sns:0}});
		});
	    
	    $jq('#userName').keypress( function(event) {
			iKEP.searchUser(event, "Y", setUser);
		});
		
		$jq('#userSearchBtn').click( function() {
			$jq('#userName').trigger("keypress");
		});
		
		$jq('#groupName').keypress( function(event) { 
			iKEP.searchGroup(event, "Y", setGroup); 
		});
		
		$jq('#groupSearchBtn').click( function() { 
			$jq('#groupName').trigger("keypress");
		});
		$jq("#btnDeleteControl,#btnGroupDeleteControl").click(function() {
			
			$jq("#targetGroupList option:selected").each(function () { 
	            $jq(this).remove();
	        }); 
			
			$jq("#targetList option:selected").each(function () { 
	            $jq(this).remove();
	        }); 
			
			var selectTree = $jq(this).attr("id");
			
			if( selectTree == "btnDeleteControl" )
			{	
				var $sel = $jq("#boardItemForm").find("#targetList");
				//$jq("#totalMember").text($sel.children().length);
				
				$jq("#totalMember").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$sel.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalMemberMsg' />");
			}
			else
			{	
				var $sel = $jq("#boardItemForm").find("#targetGroupList");
				//$jq("#totalGroup").text($sel.children().length);
				$jq("#totalGroup").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$sel.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalGroupMsg' />");
			}
		});
		});
	setUser = function(data) {
		if(data=="")			{
			alert('검색대상이 없습니다');
		}
		else {		
			var $select = $jq("#targetList");
			var duplicationUsers = [];
			$jq.each(data, function() {
				var hasOption = $select.find('option[value="'+this.id+'"]');

				if(hasOption.length > 0){
					duplicationUsers.push(this.userName);
				} else {
					$.tmpl(iKEP.template.userOption, this).appendTo($select)
						.data("data", this);
				}	
			})
			
			$jq("#totalMember").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$select.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalMemberMsg' />");
			
			if(duplicationUsers.length > 0) alert("[" + duplicationUsers.join(",") + "] " + iKEPLang.searchUserGroup.duplicateUser);
			
			$jq('#userName').val('');
		}
	};
	setGroup = function(data) {
		if(data=="") {
			alert('검색대상이 없습니다');
		} else {
			var $select = $jq("#targetGroupList");
			var duplicationGroups = [];
			$jq.each(data, function() {
				var hasOption = $select.find('option[value="'+this.code+'"]');

				if(hasOption.length > 0){
					duplicationGroups.push(this.name);
				} else {
					$.tmpl(iKEP.template.groupOption, this).appendTo($select)
						.data("data", this);
				}	
			})
			$jq("#totalGroup").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$select.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalGroupMsg' />");
			
			if(duplicationGroups.length > 0) alert("[" + duplicationGroups.join(",") + "] " + iKEPLang.searchUserGroup.duplicateGroup);
			
			$jq('#groupName').val('');
		}
	};
	
	writeSubmit = function(targetForm){
	
		if($("input[name=itemDisplayDummy]").is(":checked")) {
			$("input[name=itemDisplay]").val("1");
		} else {
			$("input[name=itemDisplay]").val("0");
		} 
	
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
			      'width' : '650px'
			    };
				$('#editorDiv').css(cssObj);

				// hidden 필드 추가(contents)
				iKEP.createActiveXEditor("#editorDiv","kms_${user.localeCode}","#contents",0);
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
	
	
	var $addHtml  = ""; 
	var $addFullHtml = "";	
	//alert($("#infoWinner_").length);
	var rows = $("#infoWinner_").length+1;	
	//alert(rows);
	  
	$("#delButton").live("click", function(){
		//alert(rows);
		if(rows > 1){
			var clickedRow = $(this).parent().parent().parent().parent().parent();
			clickedRow.remove();
			rows--;
		}
			
	});
	
	$("#addButton").live("click", function() { 
		//alert(rows);
		var addRefItemId = "";
					  
		if($addFullHtml == ""){
			addRefItemId = $("#boardItemForm").find('input[name=refItemId]').val();
			
		}else{				
			addRefItemId = $addFullHtml.find('input[name=refItemId]:last').val();
		}
		
		
		if(addRefItemId == ""){
			//alert("<ikep4j:message pre='${preMessage}' key='required' />");
			alert("데이터를 선택해주세요.");
			return false;
		}			
		
		++rows;			
		if(rows > 6){
			//alert("<ikep4j:message pre='${preMessage}' key='maxRegist' />");
			alert("최대 등록 갯수는 6개입니다. ");
			return false;
		}
		
		$addFullHtml = $("#infoStart").append					
			("<div class=\"blockDetail\" id='infoWinner_'" + rows+">"
           +"		<table summary=\"게시판등록\">"
           +"			<caption></caption>"
           +"			<colgroup>"
           +"				<col width=\"12%\" />"
           +"				<col width=\"20%\" />"
           +"				<col width=\"12%\" />"
           +"				<col width=\"20%\" />"
           +"               <col width=\"12%\" />"
           +"				<col width=\"20%\" />"
           +"				<col width=\"4%\" />"
           +"			</colgroup>"
           +"			<tbody>"
           +" 		  		<tr>"
           +"					<th scope=\"row\">제목</th>"
           +"					<td colspan=\"5\">"
           +"                      	<input name=\"refTitle\" title=\"제목\" class=\"inputbox w70\" type=\"text\"  readonly/>"
           +"                      	<input name=\"refItemId\" value=\"\" type=\"hidden\"/>"
           +"                          <a class=\"button_s\"  id=\"addRefButton\" href=\"#a\"><span>검색</span></a>"
           +"                   </td>"
           +"                   <td class=\"textCenter\"><a href=\"#a\" id=\"addButton\"><img src=\"<c:url value='/base/images/icon/ic_btn_plus.gif'/>\" alt=\"\" /></a></td>"
           +"				</tr>"
           +"				<tr>"
           +"                   <th scope=\"row\">등록자</th>"
           +"					<td><input name=\"refUserInfo\" value=\"\" class=\"inputbox w30\" type=\"text\" readonly/></td>"
           +"					<th scope=\"row\">등록일</th>"
           +"					<td><input name=\"refRegisterDate\" value=\"\" class=\"inputbox w30\" type=\"text\" readonly/></td>"
           +"                   <th scope=\"row\">출처</th>"
           +"					<td><input name=\"refTargetSource\" value=\"\" class=\"inputbox w30\" type=\"text\" readonly/></td>"
           +"					<td class=\"textCenter\"><a href=\"#a\" id=\"delButton\"><img src=\"<c:url value='/base/images/icon/ic_btn_minus.gif'/>\" alt=\"\" /></a></td>"
           +"				</tr>"
           +"           </tbody>"
           +"		</table>"
           +"</div>");			
		
		iKEP.iFrameContentResize();
	});
	

	setRef = function(data) {
		
		$jq(data).each(function(index) {

            $addHtml.find('input[name=refTitle]').val(data[index].title);
            $addHtml.find('input[name=refItemId]').val(data[index].itemId);
            $addHtml.find('input[name=refUserInfo]').val(data[index].registerName);
            $addHtml.find('input[name=refRegisterDate]').val(data[index].registDate);
            $addHtml.find('input[name=refTargetSource]').val(data[index].targetSource);
		});	
	}
	
	$("#moveBoardItemButton1").live("click", function(){ 
		
		var isKnowhow = $("input[name=isKnowhow1]:checked").attr("value");
		var url = iKEP.getContextRoot() + "/collpack/kms/board/boardItem/viewBoardTreePopup.do?isKnowhow="+isKnowhow+"&orgBoardId=0";
		//var pageURL = "<c:url value='/collpack/kms/board/boardItem/viewBoardTreePopup.do' />?isKnowhow=0&orgBoardId=0";
		iKEP.popupOpen( url , {width:400, height:730 , callback:function(result){moveBoardItem1(result);}}, "MoveBoardItem");
	});

	moveBoardItem1 = function(result) {
		$('input[name="boardId"]').val(result);
		$.get("<c:url value='/collpack/kms/board/boardItem/getKmsMapName.do?boardId='/>" +result)
		.success(function(data) { 
			$("#kmsMapName").text(data);	
		})
		.error(function(event, request, settings) { alert("error"); });  
	};
	
	
	
	$jq('#addRefButton').live("click", function(event) {			
		event.which = "13";			
		//$addHtml = $(this).parent().parent();
		$addHtml = $(this).parent().parent().parent().parent();
		
		//alert(setRef);
		iKEP.searchRef(event, "N", setRef);
	});	
	
	// 사용자 검색 팝업
	iKEP.popupRef = function(name, column, isMulti, callback) {
		var url = iKEP.getContextRoot() + '/collpack/kms/board/boardItem/popupRef.do?isMulti='+isMulti;
		
		iKEP.popupOpen(url, {width:800, height:560, callback:callback, argument:{name:name,column:column,isMulti:isMulti}}, "BoardItemSearch");
	};


	// 사용자 검색 : AJAX
	iKEP.searchRef = function(event, isMulti, callback, column) {

		if (event.which == '13' || event.which === undefined) {
			var $el = jQuery(event.target);
			var name = $el.val();
			
			if(column == undefined) {
				column= "title";
			}
			
			if(!name) {
				iKEP.popupRef(name, column, isMulti, callback);
			} else {
				jQuery.post(iKEP.getContextRoot()+"/support/popup/getUser.do", {name:name, column:column})
				.success(function(data) {
					
					switch(data.userName) {
						case "isMany" : iKEP.popupUser(name, column, isMulti, callback); break;
						case "isEmpty" : callback([]); break;
						default :
							callback([{
								type:"user",
								id:data.userId,
								empNo:data.empNo,
								userName:data.userName,
								jobTitleName:data.jobTitleName,
								group:data.groupId,
								teamName:data.teamName,
								email:data.mail,
								mobile:data.mobile
							}]);
					}
				})
				.error(function(event, request, settings) { alert("error"); });
			}
		}
	};
	
	ecmfileDelete = function(){
	    $("#boardItemForm input[name=ecmCheck]:checked").each(function(index) { 
			$jq("#ecmTr_"+$(this).val()).remove();
		});

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
	<h2>지식등록</h2> 
	<div class="blockButton"> 
		<ul>
			<c:if test="${permission.isWritePermission}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
				<li><a class="button saveBoardItemButton" href="#a"><span>등록</span></a></li>
				<li><a class="button tempSaveBoardItemButton" href="#a"><span>임시저장</a></li>
			</c:if>  
			<!-- li><a class="button" href='<c:url value='/collpack/kms/board/boardItem/listBoardItemView.do?isKnowhow=${board.isKnowhow}&amp;boardId=${boardItem.boardId}&amp;searchConditionString=${searchConditionString}&amp;=${popupYn}'/>'><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li-->
			<li><a class="button listBoardItemButton" href="<c:url value='/collpack/kms/board/boardItem/listSearchItemView.do?isKnowhow=1&popupYn=false&pageGubun=searchItem&pagePerRecord='/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
			<%-- <li><a class="button" href='javascript:history.back();'><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li> --%>
		</ul>
	</div>	
</div>  
<!--//pageTitle End--> 
<!--blockDetail Start-->
<div class="blockDetail">
<form id="boardItemForm" name="boardItemForm" method="post" action="<c:url value='/collpack/kms/board/boardItem/createBoardItem.do'/>" enctype="multipart/form-data">
	<input name="controlTabType" title="" 	type="hidden" value="0:1:0:0" />
	<input name="controlType" title="" 		type="hidden" value="ORG" />
	<input name="selectType" title="" 		type="hidden" value="ALL" />
	<input name="selectMaxCnt" title="" 	type="hidden" value="100" />
	<input name="searchSubFlag" title="" 	type="hidden" value="" />	
	
	<input name="boardId"               type="hidden" value="${board.boardId}" />
	<input name="isKnowhow"             type="hidden" value="${board.isKnowhow}" />
	<input name="searchConditionString" type="hidden" value="${searchConditionString}" />
	<input name="itemDisplay"           type="hidden" value="${boardItem.itemDisplay}" /> 
	<input name="popupYn"               type="hidden" value="${popupYn}" />
	<input name="itemType" 				type="hidden" value="0"/>
	<input name="msie"        			type="hidden" value="0" />
	<input name="status" 				type="hidden" value="${boardItem.status}"/>
	<input name="hopeGrade" 				type="hidden" value="${boardItem.hopeGrade}"/>
	<input name="infoGrade" 				type="hidden" />
	<input name="reportReq" id="reportReq"				type="hidden" />
	<input type="hidden" id="wordName" name="wordName"/> 	
	<input type="hidden" name="ecmFileId" value="" /> 
	<input type="hidden" name="ecmFileName" value="" />
	<input type="hidden" name="ecmFilePath" value=""/>  
	<input type="hidden" name="ecmFileUrl1" value=""/> 
	<input type="hidden" name="ecmFileUrl2" value=""/>  

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
			<td> 
				<div>
					<input 
						name="${status.expression}" 
						type="text" 
						class="inputbox w80"
						value="${status.value}" 
						size="1000" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						/> 
						<c:forEach items="${status.errorMessages}" var="error">
					    	<label for="${status.expression}" class="serverError"> ${error}</label>
					    </c:forEach> 
					</spring:bind>
				</div>			
			</td>  
			<th scope="row">경영진보고 요청</th>
			<td>
				<input name="reportReqCk" id="reportReqCk" type="checkbox" class="checkbox"/>
			</td>
		</tr>				
		<tr>  
			<th scope="row"><ikep4j:message pre='${preDetail}' key='registerName' /></th>
			<td>
				${user.userName} ${user.teamName}
			</td> 
			<th scope="row">출처</th>
			<td><input name="targetSource" title="출처" class="inputbox w60" type="text" /></td> 	 
		</tr>
        <tr>
			<th scope="row">지식분류</th>
			<td>
				<label>
				<input name="isKnowhow1" type="radio" class="radio" value="1" title="지식분류" <c:if test="${board.isKnowhow eq '1' or empty board.isKnowhow}">checked="checked"</c:if> onclick="javascript:isKnowChk('1');" /> 
				일반정보
				</label>
				<label>
				<input name="isKnowhow1" type="radio" class="radio" value="0" title="지식분류" <c:if test="${board.isKnowhow eq '0'}">checked="checked"</c:if> onclick="javascript:isKnowChk('2');" /> 
				업무노하우
				</label>
				<label>
				<c:if test="${isSystemAdmin}">
				<input name="isKnowhow1" type="radio" class="radio" value="3" title="지식분류" <c:if test="${board.isKnowhow eq '3'}">checked="checked"</c:if> onclick="javascript:isKnowChk('3');" /> 
				원문 게시판
				</label>
				</c:if>
			</td>
			<th scope="row">지식맵</th>
			<td>
				<span id="kmsMapName">${kmsMapName}</span>	
				<a id="moveBoardItemButton1" class="button_s" href="#"><span>지식맵</span></a>
			</td>
		</tr>
		<tr id="expertDiv" style="display:none;">
			<th scope="row">전문가</th>
			<td colspan="3">
				<input id="assessor" name="assessor" title="전문가" class="inputbox w20" type="text" value="" readonly/>
            	<input id="assessorId" name="assessorId" value="" type="hidden"/>
            	<input id="assessorName" name="assessorName" value="" type="hidden"/>
                <a class="button_s" id="addPersonButton" href="#a"><span>Search</span></a>
			</td>
		</tr>
		<tr>
			<th scope="row">보안등급요청</th>
			<td colspan="3">
				<label>
				<input name="hopeGradeCheck" type="radio" class="radio" value="B" /> 
				보안
				</label>
				<label>
				<input name="hopeGradeCheck" type="radio" class="radio" value="C"  /> 
				공유
				</label>
				<span class="tdInstruction">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;※ 보안: 팀장 이상, 공유: 전사 임직원</span> 
			</td>
		</tr>				
		<tr>  
			<spring:bind path="boardItem.tag">
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3">
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
			<th>알림 대상 부서</th>
			<td>
				<input 
					name="groupName" 
					id="groupName" 
					type="text" 
					class="inputbox"
					size="20" 
					title="groupName"
					/>
				<a name="groupSearchBtn" id="groupSearchBtn" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a>
				<a id="btnGroupAddrControl" name="btnGroupAddrControl" class="button_ic" href="#a"><span><ikep4j:message  key='ui.servicepack.survey.common.button.address' /></span></a>
				<div style="padding-top:4px;">
				    <c:set var="targetGroupListCnt" value="0"/>
					<select name="targetGroupList" id="targetGroupList"   size="5" multiple="multiple" class="multi w70" title="<ikep4j:message pre='${preList}' key='targetGroupList' />">
					<c:forEach var="target" items="${boardItem.targetGroup}" varStatus="loopStatus">
					    <c:set var="targetGroupListCnt" value="${targetGroupListCnt+1}"/>
						<option label="<c:out value="${target.targetGroupName}"/>" value="<c:out value="${target.targetGroupId}"/>"><c:out value="${target.targetGroupName}"/></option>
					</c:forEach>
					</select>	
					<a class="button_ic valign_bottom" href="#a" id="btnGroupDeleteControl"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="" />Delete</span></a>
					<span id='totalGroup'  class='totalNum_s'><ikep4j:message key='ui.servicepack.survey.common.totalGroup' arguments="${targetGroupListCnt}"/></span>
				</div>
				<ikep4j:message key='ui.servicepack.survey.common.subGroupSelected' />
			</td>
			<th>알림 대상<br>사용자</th>
			<td>
				<input 
					name="userName" 
					id="userName" 
					type="text" 
					class="inputbox"
					size="20" 
					 title=""
					/>
				<a name="userSearchBtn" id="userSearchBtn" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a>
				<a id="btnAddrControl" class="button_ic" href="#a"><span><ikep4j:message  key='ui.servicepack.survey.common.button.address' /></span></a>
				<div style="padding-top:4px;"> 
					<select name="targetList" id="targetList"  size="5" multiple="multiple" class="multi w80" ></select>									
					<a class="button_ic valign_bottom" href="#a" id="btnDeleteControl"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="<ikep4j:message  key='ui.servicepack.survey.common.button.delete' />"/>Delete</span></a>
					<span id='totalMember' class='totalNum_s'></span>
				</div>
			</td>
		</tr>
		<tr>  
			<spring:bind path="boardItem.contents">
			<td colspan="4" class="ckeditor"> 
				<table style="border-width:0 0 0 0;">
				<tr>
				<td style="border-width:0 0 0 0;width:650px;">
				<div id="editorDiv">					
					<textarea id="contents"
					name="${status.expression}" 
					class="inputbox w60 fullEditor"
					cols="" 
					rows="5" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />">${status.value}</textarea>					
				</div>
				</td> 
				<td style="vertical-align:top;border-width:0 0 0 0;">
				<table style="border-width:0 0 0 0;">
				<tr>
				<td style="border-width:0 0 0 0;">
				<% pageContext.setAttribute("newLineChar", "\n"); %> 
					<p name="contents">${fn:replace(boardItem.caution, newLineChar, '<br/>')}</p>
					</td>
				</tr>	
				</table>
				</td>
				</tr>	
				</table>
			</td>
			</spring:bind> 
		</tr>
		</tbody> 
	</table>
	<div class="mb20"></div>
                <!--blockDetail Start-->
                <div id="pageTitle">
					<h2>활용정보</h2>
				</div>	
                <div class="mb5"></div>
                <div id="infoStart">
                <div class="blockDetail">
					<table summary="게시판등록">
						<caption></caption>
						<colgroup>
							<col width="12%" />
							<col width="20%" />
							<col width="12%" />
							<col width="20%" />
                            <col width="12%" />
							<col width="20%" />
							<col width="4%" />
						</colgroup>
						<tbody>
               		  		<tr>
								<th scope="row">제목</th>
								<td colspan="5">
                                	<input name="refTitle" title="제목" class="inputbox w80" type="text"  readonly/>
                                	<input name="refItemId" value="" type="hidden"/>
                                    <a class="button_s" id="addRefButton" href="#a"><span>검색</span></a>
                                </td>
                                <td class="textCenter"><a href="#a" id="addButton"><img src="<c:url value='/base/images/icon/ic_btn_plus.gif'/>" alt="" /></a></td>
							</tr>
							<tr>
								<c:if test="${isSystemAdmin}">
                                <th scope="row">등록자</th>
								<td><input name="refUserInfo" value="" class="inputbox w90" type="text" readonly/></td>
								<th scope="row">등록일</th>
								<td><input name="refRegisterDate" value="" class="inputbox w90" type="text" readonly/></td>
                                <th scope="row">출처</th>
								<td><input name="refTargetSource" value="" class="inputbox w90" type="text" readonly/></td>
								</c:if>
								<c:if test="${!isSystemAdmin}">
								<th scope="row">등록일</th>
								<td colspan="2"><input name="refRegisterDate" value="" class="inputbox w90" type="text" readonly/></td>
                                <th scope="row">출처</th>
								<td colspan="2"><input name="refTargetSource" value="" class="inputbox w90" type="text" readonly/></td>
								</c:if>
								<td class="textCenter"><a href="#a" id="delButton"><img src="<c:url value='/base/images/icon/ic_btn_minus.gif'/>" alt="" /></a></td>
							</tr>
                        </tbody>
					</table>
				</div>
				</div>
                <!--//blockDetail End-->
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
		<c:if test="${permission.isWritePermission}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
			<li><a class="button saveBoardItemButton" href="#a"><span>등록</span></a></li>
			<li><a class="button tempSaveBoardItemButton" href="#a"><span>임시저장</a></li>
		</c:if>  
		<li><a class="button listBoardItemButton" href="<c:url value='/collpack/kms/board/boardItem/listSearchItemView.do?isKnowhow=1&popupYn=false&pageGubun=searchItem&pagePerRecord='/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		<%-- <li><a class="button" href='<c:url value='/collpack/kms/board/boardItem/listSearchItemView.do?isKnowhow=1&popupYn=false&pageGubun=searchItem&pagePerRecord='/>'><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li> --%>
	 </ul>
</div>
<!--//blockButton End-->  
<c:if test="${popupYn}"></div></c:if>