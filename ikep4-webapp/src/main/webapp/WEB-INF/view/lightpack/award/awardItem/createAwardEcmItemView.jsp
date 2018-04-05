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
<c:set var="preHeader"  value="ui.lightpack.award.awardItem.createAwardView.header" /> 
<c:set var="preDetail"  value="ui.lightpack.award.awardItem.createAwardView.detail" />
<c:set var="preButton"  value="ui.lightpack.common.button" /> 
<c:set var="preMessage" value="message.lightpack.common.awardItem" /> 
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>    
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />



<script language="JScript" FOR="twe" EVENT="OnInsertImage(oElem)">
    var imageFiles = twe.GetLocalFiles.split(";");
    for(i=0;i<imageFiles.length;i++){
    	if(imageFiles[i].indexOf(".tif") != -1){
    		alert("TIF 확장자는 지원하지 않습니다.");
    		oElem.parentNode.removeChild(oElem);
    	}
    }
	<c:if test="${award.awardId eq '100000793108' || award.awardId eq '100000793116'}">
	    if(twe.GetAttachmentSize() > 1048576)
	    {
	       alert('이미지 용량이 초과로 등록이 되지 않습니다.');
	       oElem.parentNode.removeChild(oElem);
	    }    
    </c:if>
</script>

<style type="text/css">
<!--
h4 {
    font-family: Dotum, 돋음, seoul, arial, helvetica
}
a {
    cursor: pointer;
    text-decoration: none;
    font-size: 12px
}
-->
</style>

<SCRIPT LANGUAGE="JavaScript">
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
	var wordId   = [];
	var wordName = [];
	var today = iKEP.getCurTime();
	var maxDate = new Date(today.getTime());
	
	$(document).ready(function() {  
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
	          	 //$("#ecmBtn").hide();
	          	 //$("#ecmTb").hide();
	          	 //$("#normalFileTb").show();
			} 
	    } 
		if(window.parent.topScroll != null) {
			window.parent.topScroll(); 	
		} 
		
		if(window.parent.menuMark != null) { 
			window.parent.menuMark("${awardItem.awardId}");
		}
		
		// editor 초기화
		initEditorSet();

		$jq("#deleteReadPermissionButton").click(function(event) {
			event.preventDefault();
			var $rPermissionList=$jq('#readerList');
			$jq('option:selected',$rPermissionList).remove();

		});	
		
		$jq("#addReadPermissionButton").click(function() {
			// 조직도 팝업 테스트

			var items = [];
			var $sel = $jq("#awardItemForm").find("[name=readerList]");
			
			$jq.each($sel.children(), function() {
				items.push($jq.data(this, "data"));
			});

			var callback = function(result){
				$sel.empty();
				$jq.each(result, function() {

					var tpl = "";
					
					switch(this.type) {
						case "group" : tpl = "addrBookItemGroup"; break;
						case "user" : tpl = "addrBookItemUser"; break;
						case "common" : tpl = "addrBookItemGroup"; break;
					}
					
					if(this.type=="group"){
						this.code="G:"+this.code;
					}else if(this.type=="common"){
						this.code="C:"+this.code;
					}else{
						this.id ="U:"+this.id;
					}
					
					var $option = $jq.tmpl(tpl, this).appendTo($sel);

					$jq.data($option[0], "data", this);
		
				})
			};
			<c:if test="${award.awardId eq '100000793108' || award.awardId eq '100000793116' }">
			iKEP.showAddressReaderBook(callback, items, {selectType:"all", selectElement:$sel, isAppend:false, tabs:{common:1}});	
			</c:if>
			<c:if test="${award.awardId ne '100000793108' && award.awardId ne '100000793116' }">
			iKEP.showAddressReaderBook(callback, items, {selectType:"all", selectElement:$sel, isAppend:false, tabs:{common:1}});	
			</c:if>
		});	 
		
		setCallback = function(result){
		var $sel = $jq("#awardItemForm").find("[name=readerList]");
			$jq.each(result, function() {
				
				var tpl = "";
				
				switch(this.type) {
					case "group" : tpl = "addrBookItemGroup"; break;
					case "user" : tpl = "addrBookItemUser"; break;
					case "common" : tpl = "addrBookItemGroup"; break;
				}
				
				if(this.type=="group"){
					this.code="G:"+this.code;
				}else if(this.type=="common"){
					this.code="C:"+this.code;
				}else{
					this.id ="U:"+this.id;
				}
				
				var $option = $jq.tmpl(tpl, this).appendTo($sel);

				$jq.data($option[0], "data", this);
	
			})
		};

		
		$jq.template("addrBookItemUser", "<option value='\${id}'>\${userName}/\${jobTitleName}/\${teamName}</option>");
		$jq.template("addrBookItemGroup", "<option value='\${code}'>\${name}</option>");
		
		$("input[name=awardDate]").attr("readonly", true).datepicker({
			isActiveX : (useActXEditor == "Y" && $.browser.msie) ? true : false,
			onSelect : function(){
				var form = $(this)[0].form;
				var validator = $(form).validate();
				validator.element(this);
			},
		    showOn: "button",
		    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true
		});   
		
		$("input[name=startDate]").attr("readonly", true).datepicker({
			isActiveX : (useActXEditor == "Y" && $.browser.msie) ? true : false,
			onSelect : function(){
				var form = $(this)[0].form;
				var validator = $(form).validate();
				validator.element(this);
				validator.element("input[name=endDate]", form);
			},
			maxDate : today,
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
		    minDate : today,
		    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true,
		    buttonText : "<ikep4j:message pre='${preDetail}' key='endDate' />"
		});   
		$("input[name=disStartDate]").attr("readonly", true).datepicker({
			isActiveX : (useActXEditor == "Y" && $.browser.msie) ? true : false,
			onSelect : function(){
				var form = $(this)[0].form;
				var validator = $(form).validate();
				validator.element(this);
				validator.element("input[name=disEndDate]", form);
			},
			maxDate : today,
		    showOn: "button",
		    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true,
		    buttonText : ""
		});   
		$("input[name=disEndDate]").attr("readonly", true).datepicker({
			isActiveX : (useActXEditor == "Y" && $.browser.msie) ? true : false,
			onSelect : function(){
				var form = $(this)[0].form;
				var validator = $(form).validate();
				validator.element(this);
				validator.element("input[name=disStartDate]", form);
			},
		    showOn: "button",
		    minDate : today,
		    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true,
		    buttonText : ""
		});  
 		
		$("input[name=itemDisplayDummy]").click(function() { 
			if($("input[name=itemDisplayDummy]").is(":checked")) {
				$("#displayDate").show();
			} else {
				$("#displayDate").hide();
			} 
			
		});
		
		$("input[name=itemForeverDummy]").click(function() { 
			if($("input[name=itemForeverDummy]").is(":checked")) {
				$("#endDateSpan").hide();
			} else {
				$("#endDateSpan").show();
			} 
			
		});

		$("a.listAwardItemButton").click(function() {
			$("#normalFileTb").show();
		}); 
		$("a.saveAwardItemButton").click(function() {
				
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
			
			
			$("#awardItemForm input[name=ecmCheck]").attr("checked", true);  

			$("#awardItemForm input[name=ecmCheck]:checked").each(function(index) { 
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
			
			<c:if test="${award.awardId=='100006240370'}">
			var workplaces = "";
			$("#awardItemForm input[name=workplaceCheck]:checked").each(function(index) { 
				if(workplaces == ""){
					workplaces = $(this).val(); 
				}else{
					workplaces = workplaces+","+$(this).val(); 
				}
			});
			
			$('input[name=workplaces]').val(workplaces); 
			</c:if>
			$("input[name=awardMaterial]:checked").each(function(index) { 
				var tempAwardMaterial = "";
				if(tempAwardMaterial == ""){
					tempAwardMaterial = $(this).val();
				}else{
					tempAwardMaterial = tempAwardMaterial+","+$(this).val();
				}
				$("#awardMaterials").val(tempAwardMaterial);
			});
			
			
				$("input[name=tempSave]").val("0");
				$("#awardItemForm").trigger("submit"); 
				return false;  
		});
		
		$("a.tempSaveAwardItemButton").click(function() {
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
			
			
			$("#awardItemForm input[name=ecmCheck]").attr("checked", true);  

			$("#awardItemForm input[name=ecmCheck]:checked").each(function(index) { 
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
			$("input[name=tempSave]").val("1");
			
			<c:if test="${award.awardId=='100006240370'}">
			var workplaces = "";
			$("#awardItemForm input[name=workplaceCheck]:checked").each(function(index) { 
				if(workplaces == ""){
					workplaces = $(this).val(); 
				}else{
					workplaces = workplaces+","+$(this).val(); 
				}
			});
			
			$('input[name=workplaces]').val(workplaces); 
			</c:if>
			$("#awardItemForm").trigger("submit"); 
			return false;  
		});
		
		new iKEP.Validator("#awardItemForm", {   
			rules  : {
				awardKind : {required : true},
				awardTitle : {required : true},
				awardTxt : {required : true},
				publisher : {required : true},
				awardGrade : {required : true},
				awardMaterial : {required : true},
				storageLocDetail : {required : true}
			},
			messages : {
				awardKind : {direction : "bottom", required : "종류를 선택해주세요"},
				awardTitle : {direction : "bottom", required : "행사명을 넣어주세요"},
				awardTxt : {direction : "bottom", required : "내역을 넣어주세요"},
				publisher : {direction : "bottom", required : "발행처를 넣어주세요"},
				awardGrade : {direction : "bottom", required : "등급을 선택해주세요"},
				awardMaterial : {direction : "bottom", required : "자료를 선택해주세요"},
				storageLocDetail : {direction : "bottom", required : "보관위치를 넣어주세요"}
			},   
			
		    submitHandler : function(form) { 
	    		/*
		    	if(confirm("<ikep4j:message pre="${preMessage}" key="createItem" />")) {
					fileController.upload(function(isSuccess, files) {
						if(isSuccess === true) {
							var wec = document.Wec;
							
				    		$("input[name=contents]").val(wec.MIMEValue);
							
							if($("input[name=itemDisplayDummy]").is(":checked")) {
								$("input[name=itemDisplay]").val("1");
							} else {
								$("input[name=itemDisplay]").val("0");
								$jq.each($("#awardItemForm select[name=categoryId] option:selected"),function(index){
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
		    	
		    	$("#awardItemForm").find('select[name=ecmFileList] option').each(function(){
					this.selected = true;
				});
		    	// ActiveX Editor 사용 여부가 Y인 경우
		    	if(useActXEditor != "Y" || !$jq.browser.msie){
		    		//ekeditor 데이타 업데이트
		    		var editor = $jq('#awardItemForm :input[name=contents]').ckeditorGet(); 
					editor.updateElement();
					//에디터 내 이미지 파일 링크 정보 세팅
					createEditorFileLink("awardItemForm");
		    	}
				
		    	<c:if test="${award.contentsReadPermission eq 1}">
				$jq("#readerList>option").attr("selected",true);	    	
				</c:if>
				
				var confirmStr ="게시글을 등록하시겠습니까?";
				if($("input[name=tempSave]").val()=="1"){
					confirmStr ="게시글을 임시저장 하시겠습니까?";
				}else{
					confirmStr ="게시글을 등록 하시겠습니까?";
				}
		    	if(confirm(confirmStr)) {
					
	    			writeSubmit(form);
				
				}
		    }
		}); 
		
			/* var uploaderOptions = {
			 	<c:if test="${empty fileDataListJson}">files : [],</c:if> 
			 	<c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if> 
				<c:if test="${award.fileAttachOption eq 0 and not empty award.allowType}">allowExt : "${award.allowType}",</c:if>
				<c:if test="${award.fileAttachOption eq 1 and not empty award.allowType}">allowFileType : "${award.allowType}",</c:if>
		    	isUpdate : true ,
		    	maxTotalSize : ${award.fileSize},
		    	onLoad : function() {
		    		iKEP.iFrameContentResize();
		    	}
		    };  
	        
		    var fileController = new iKEP.FileController("#awardItemForm", "#fileUploadA", uploaderOptions); */
		
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
	    
	    $("#awardItemForm input[name=workplaceCheck]").click(function() {
			if($(this).val() == "전체"){
				if($('input:checkbox[name=workplaceCheck][value="전체"]').is(":checked") == true){
				$('input:checkbox[name=workplaceCheck]').attr("checked", false);
				$('input:checkbox[name=workplaceCheck][value="전체"]').attr("checked", true);
				}else{
					$('input:checkbox[name=workplaceCheck][value="전체"]').attr("checked", false);
				}
			}else{
				$('input:checkbox[name=workplaceCheck][value="전체"]').attr("checked", false);
			}
		});  
	   
	});
	
	
	writeSubmit = function(targetForm){
		
		<c:if test="${award.anonymous ne 1 || award.contentsReadPermission eq 1 || award.awardId=='100006240370'}">
		if($("input[name=itemDisplayDummy]").is(":checked")) {
			$("input[name=itemDisplay]").val("1");
			$jq.each($("#awardItemForm select[name=categoryId] option:selected"),function(index){
				if($jq(this).text() != '') {			
        			wordId.push($jq(this).val());
        			wordName.push($jq(this).text());
        		}
    		});
			$jq("#wordId").val(wordId);
			$jq("#wordName").val(wordName);	
		} else {
			$("input[name=itemDisplay]").val("0");
			$jq.each($("#awardItemForm select[name=categoryId] option:selected"),function(index){
				if($jq(this).text() != '') {			
        			wordId.push($jq(this).val());
        			wordName.push($jq(this).text());
        		}
    		});
			$jq("#wordId").val(wordId);
			$jq("#wordName").val(wordName);								
		} 
		</c:if>
		
		if($("input[name=itemForeverDummy]").is(":checked")) {
			$("input[name=itemForever]").val("1");
		} else {
			$("input[name=itemForever]").val("0");
		}
		
		<c:if test="${award.contentsReadMail eq 1}">
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
	
	ecmfileDelete = function(){
	    $("#awardItemForm input[name=ecmCheck]:checked").each(function(index) { 
			$jq("#ecmTr_"+$(this).val()).remove();
		});
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
	<input name="maxFileSize"  value="${award.imageFileSize}" type="hidden"/>
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
				<%-- <li><a class="button tempSaveAwardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='tempSave'/></span></a></li> --%>
				<li><a class="button saveAwardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
			</c:if>  
			<li><a class="button listAwardItemButton" href='<c:url value='/lightpack/award/awardItem/listAwardItemView.do?awardId=${awardItem.awardId}&amp;searchConditionString=${searchConditionString}&amp;=${popupYn}'/>'><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		</ul>
	</div>	
</div>  
<!--//pageTitle End--> 
<!--blockDetail Start-->
<div class="blockDetail">
<form id="awardItemForm" name="awardItemForm" method="post" action="<c:url value='/lightpack/award/awardItem/createAwardItem.do'/>" enctype="multipart/form-data">
	<input name="awardId"               type="hidden" value="${award.awardId}" />
	<input name="searchConditionString" type="hidden" value="${searchConditionString}" />
	<input name="itemDisplay"           type="hidden" value="0" /> 
	<input name="itemForever"           type="hidden" value="0" /> 
	<input name="readerMailFlag"           type="hidden" value="0" /> 
	<input name="anonymous"				type="hidden" value="${award.anonymous}" />
	<input name="popupYn"               type="hidden" value="${popupYn}" />
	<input name="msie"        			type="hidden" value="0" />
	<input name="toDate" 				type="hidden" value="<ikep4j:timezone date='${awardItem.toDate}'/>"/>	
	<input name="tempSave" 				type="hidden" value="0"/>
	<input type="hidden" id="wordId" name="wordId"/> 	
	<input type="hidden" id="wordName" name="wordName"/> 	
	<input type="hidden" name="ecmFileId" value="" /> 
	<input type="hidden" name="ecmFileName" value="" />
	<input type="hidden" name="ecmFilePath" value=""/>  
	<input type="hidden" name="ecmFileUrl1" value=""/> 
	<input type="hidden" name="ecmFileUrl2" value=""/>  
	
	<input type="hidden" name="workplaces" value=""/>  

	<table summary="<ikep4j:message pre="${preDetail}" key="summary" />">
		<caption></caption>
		<col style="width: 15%"/>
		<col style="width: 35%"/>
		<col style="width: 15%"/>
		<col style="width: 35%"/>
		<tbody> 
		<tr>
			<th scope="row">종류</th>
			<td colspan="3"> 
				<c:forEach var="awards" items="${awardKindList}" varStatus="status">
					<label>
					<input name="awardKind" type="radio" class="radio" value="${awards.comCd}" /> 
					${awards.comNm}	
					</label>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<th scope="row">회사</th>
			<td colspan="3"> 
				<select name="companyCode">
					<c:forEach var="awards" items="${companyCodeList}" varStatus="status">
						<option value="${awards.comCd}">${awards.comNm}</option>
					</c:forEach>
				</select>
				
			</td>
		</tr>
		<tr> 
			<th scope="row">행사명</th>
			<td colspan="3"> 
				<div>
					<input name="awardTitle" type="text" class="inputbox" style="width: 40%;" value="" size="40" /> 
				</div>			
			</td>  
		</tr>
		<tr> 
			<th scope="row">내역</th>
			<td colspan="3"> 
				<div>
					<input name="awardTxt" type="text" class="inputbox" style="width: 40%;" value="" size="40" /> 
				</div>			
			</td>  
		</tr>
		<tr> 
			<th scope="row">발행처(대상)</th>
			<td colspan="3"> 
				<div>
					<input name="publisher" type="text" class="inputbox" style="width: 40%;" value="" size="40" /> 
				</div>			
			</td>  
		</tr>
		<tr> 
			<th scope="row">날짜</th>
			<td colspan="3"> 
				<div>
					<spring:bind path="awardItem.awardDate"> 
						<input 
						name="${status.expression}" 
						type="text" 
						class="inputbox datepicker" 
						value="<ikep4j:timezone date='${awardItem.toDate}'/>" size="10" 
						title=""
						/>  
					</spring:bind>
				</div>			
			</td>  
		</tr>
		<tr>
			<th scope="row">등급</th>
			<td colspan="3"> 
				<c:forEach var="awards" items="${awardGradeList}" varStatus="status">
					<label>
					<input name="awardGrade" type="radio" class="radio" value="${awards.comCd}" /> 
					${awards.comNm}	
					</label>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<th scope="row">자료</th>
			<td colspan="3"> 
				<c:forEach var="awards" items="${awardMaterialList}" varStatus="status">
					<label>
					<input name="awardMaterial" id="awardMaterial${status}" type="checkbox" class="checkbox" value="${awards.comCd}" /> 
					${awards.comNm}	
					</label>
				</c:forEach>
				<input type="hidden" name="awardMaterials" id="awardMaterials" />
			</td>
		</tr>
		<tr>
			<th scope="row">보관위치</th>
			<td colspan="3"> 
				<select name="storageLocCd">
					<c:forEach var="awards" items="${storageLocCdList}" varStatus="status">
						<option value="${awards.comCd}">${awards.comNm}</option>
					</c:forEach>
					<input name="storageLocDetail" type="text" class="inputbox" style="width: 40%;" value="" size="40" /> 
				</select>
				
			</td>
		</tr>
		<tr> 
			<th scope="row">주무부서</th>
			<td colspan="3"> 
				<div>
					<input name="resDeptId" type="text" class="inputbox" style="width: 40%;" value="" size="40" />  
				</div>			
			</td>  
		</tr>
		<spring:bind path="awardItem.title">
			<input name="${status.expression}" type="hidden" value="수상자료" />
		</spring:bind>
				
		<tr>  
			<th scope="row"><ikep4j:message pre='${preDetail}' key='registerName' /></th>
			<td colspan="3">
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
			<th scope="row">독서자</th>
			<td colspan="3">
			<select name="readerList" id="readerList" class="input_select" size="4"	style="height:100px;width:60%" multiple></select>						
			<a id="addReadPermissionButton" class="button_ic valign_bottom" href="#a" title="독서자추가"><span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" alt="" />독서자추가</span></a>
			<a id="deleteReadPermissionButton" class="button_ic valign_bottom" href="#a" title="독서자삭제"><span><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="" />독서자삭제</span></a>
			</td>
		</tr>
		</c:if>
		<c:if test="${award.contentsReadMail eq 1}">
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
				</spring:bind>
			</td>
		</tr>
		</c:if>
	
		<tr>  
			<spring:bind path="awardItem.contents">
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
	<table style="width:100%;" id="ecmTb">
		<tr><th style="width:3%;text-align:center;"></th><th style="width:17%;text-align:center;">파일명</th><th style="width:80%;text-align:center;">URL</th></tr>
	</table>
	<table style="width:100%;" id="ecmBtn">
		<tr>
			<td style="border-left:none;border-color:#e5e5e5;background-color:#e8e8e8;text-align:right;">
			<img src="<c:url value="/base/images/dextupload/btn_fileplus_normal.gif"/>" id="imgBtn_fileAdd" name="Image2"  border="0" onclick="CyberdigmpopupSelect('selectAllFiles');" style="cursor:pointer;valign:absmiddle" />
			<img src="<c:url value="/base/images/dextupload/btn_listdelete_normal.gif"/>" name="Image4"  border="0" onclick="ecmfileDelete();" style="cursor:pointer;valign:absmiddle" />	
			</td>
		</tr>
	</table>
	</div>
	<iframe width="0" height="0" src="<c:url value="/base/common/destinySLO.jsp?TARGET_URL=install"/>"></iframe>
	<%-- <iframe width="0" height="0" src="<c:url value="/base/common/file_sample.jsp"/>"></iframe> --%>
	<iframe id="select_dialog" src="" style="display:none;"></iframe>
	</form> 
</div> 
<!--//blockDetail End-->  									
<!--blockButton Start-->
<div class="blockButton"> 
	<ul> 
		<c:if test="${permission.isWritePermission}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
			<%-- <li><a class="button tempSaveAwardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='tempSave'/></span></a></li> --%>
			<li><a class="button saveAwardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
		</c:if>  
		<li><a class="button listAwardItemButton" href='<c:url value='/lightpack/award/awardItem/listAwardItemView.do?awardId=${awardItem.awardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>'><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
	 </ul>
</div>
<!--//blockButton End-->  
<c:if test="${popupYn}"></div></c:if>