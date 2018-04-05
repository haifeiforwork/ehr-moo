<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preHeader"  value="ui.lightpack.award.awardItem.readAwardView.header" /> 
<c:set var="preDetail"  value="ui.lightpack.award.awardItem.readAwardView.detailAwardItem" />
<c:set var="preList"    value="ui.lightpack.award.awardItem.listAwardView.list" />
<c:set var="preButton"  value="ui.lightpack.common.button" /> 
<c:set var="preMessage" value="message.lightpack.common.awardItem" /> 
<c:set var="tempEcmFileCount" value="0"/>
<c:forEach var="tempEcmFileData" items="${awardItem.ecmFileDataList}" varStatus="ecmFileDataStatus">
	<c:set var="tempEcmFileCount" value="${tempEcmFileCount+1}"/>
</c:forEach>
<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>  
<%-- 메시지 관련 Prefix 선언 End --%>
<c:set var="portal" value="${sessionScope['ikep.portal']}" /> 
<script type="text/javascript">
<!--  

var da = (document.all) ? 1 : 0
var pr = (window.print) ? 1 : 0
var mac = (navigator.userAgent.indexOf("Mac") != -1);
		
if (da && !pr && !mac) with (document) {
	writeln('<OBJECT ID="WB" WIDTH="0" HEIGHT="0" CLASSID="clsid:8856F961-340A-11D0-A96B-00C04FD705A2"></OBJECT>');
	writeln('<' + 'SCRIPT LANGUAGE="VBScript!">');
	writeln('Sub window_onunload');
	writeln(' On Error Resume Next');
	writeln(' Set WB = nothing');
	writeln('End Sub');
	writeln('Sub vbPrintPage');
	writeln(' OLECMDID_PRINT = 6');
	writeln(' OLECMDEXECOPT_DONTPROMPTUSER = 2');
	writeln(' OLECMDEXECOPT_PROMPTUSER = 1');
	writeln(' On Error Resume Next');
	writeln(' WB.ExecWB OLECMDID_PRINT, OLECMDEXECOPT_DONTPROMPTUSER');
	writeln('End Sub');
	writeln('<' + '/SCRIPT>');
}
		
(function($){	 
	<c:if test="${award.linereply eq 1 && awardItem.tempSave ne '1'}">
	loadAwardLinereplyList = function() {  
		$("#blockComment").ajaxLoadStart(); 
		$("#blockComment").load('<c:url value="/lightpack/award/awardLinereply/listAwardLinereplyView.do?awardId=${awardItem.awardId}&itemId=${awardItem.itemId}&popupYn=${popupYn}&viewMode=${viewMode}"/>', $("#awardLinereplySearchForm").serialize(), function() {
			<c:if test="${not popupYn}">iKEP.iFrameContentResize();</c:if>  
			$("#blockComment").ajaxLoadComplete();
			//$(".firstLineItem").focus();
		 
		}).error(function(event, request, settings) { alert("error"); });
	};	 
	</c:if>
	loadRelatedAwardItemList = function() {  
		$("#blockRelated").load('<c:url value="/lightpack/award/awardTagging/listRelatedAwardItemView.do?awardId=${awardItem.awardId}&itemId=${awardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&viewMode=${viewMode}"/>', $("#relatedAwardItemSearchForm").serialize(), function() {
			<c:if test="${not popupYn}">iKEP.iFrameContentResize();</c:if> 
			//alert($("#blockRelated *").length);
			if($("#blockRelated *").length == 0) {
				$("#blockRelated").hide();
			} else {
				$("#blockRelated").show();
			}
			
			//$(".firstRelatedItem").focus();
		}).error(function(event, request, settings) { alert("error"); }); 
	};	 
	
	loadAwardItemThreadList = function() {  
		$("#blockReply").load('<c:url value="/lightpack/award/awardItemThread/listReplayItemThreadView.do?awardId=${awardItem.awardId}&itemId=${awardItem.itemId}&itemGroupId=${awardItem.itemGroupId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&viewMode=${viewMode}"/>', function() {
			<c:if test="${not popupYn}">iKEP.iFrameContentResize();</c:if> 
			if($("#blockReply *").length == 0) {
				$("#blockReply").hide();
			} else {
				$("#blockReply").show();
			}
			
			
		}).error(function(event, request, settings) { alert("error"); }); 
	};	 
	
	viewPopUpProfile = function(targetUserId) { 
		var pageURL = "<c:url value='/support/profile/getProfilePopup.do' />?targetUserId=" + targetUserId;
		iKEP.popupOpen( pageURL , {width:820, height:370, callback:function(result) {} });
	};
	
	
	$(document).ready(function() { 
		
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
	        	 $("#sfile").hide();
	           	 $("#lfile").show();
	        }else if(xmlhttp.status==12029){
	        	$("#lfile").hide();
	          	 $("#sfile").show();
			}
	    } 
	    
		
		if(window.parent.topScroll != null) {
			window.parent.topScroll(); 	
		}   
		
		if(window.parent.menuMark != null) { 
			window.parent.menuMark("${awardItem.awardId}");
		} 
		
		iKEP.setContentImageRendering("#awardItemContent");
		
		$("input.datepicker").datepicker({
		    showOn: "button",
		    buttonImage: "<c:url value='/base/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true
		});  
	  
		$("#createFavoriteButton").click(function() { 
			if(confirm("<ikep4j:message pre='${preMessage}' key='addFavorite' />")) {  
				var callback = function(data) { 
					alert(data.message);
					$("#createFavoriteButton").attr("class", "ic_rt_favorite select");
				};
				
				iKEP.addFavorite("CONTENTS","${IKepConstant.ITEM_TYPE_CODE_BBS}", "${awardItem.itemId}", "${fn:escapeXml(awardItem.title)}", callback);
			} 
			
			return false; 
			
		}); 

		<c:if test="${CommonConstant.PACKAGE_VERSION eq CommonConstant.IKEP_VERSION_FULL}">
		$("#createMircoBloggingButton").click(function() {  
			var url = "<c:url value='/lightpack/award/awardItem/readAwardItemLinkView.do?itemId=${awardItem.itemId}'/>";   
			iKEP.showTwittingPage("${fn:escapeXml(awardItem.title)}",url);  
		});
		</c:if>
		
		 
		$("#createMessageButton").click(function() {    
			var url = "<c:url value='/lightpack/award/awardItem/readAwardItemLinkView.do?itemId=${awardItem.itemId}'/>";  
			
			var content = "<a href='"+url+"' target='_blank'>${fn:escapeXml(awardItem.title)}</a>" ;
			
			// 단순 작성 팝업 열기
		    var url = "<c:url value='/support/message/messageNew.do'/>?contents="+content;
			 
		    iKEP.popupOpen(url ,{width:800, height:670}); 
		    
			return false;  
		});  
		
		
	  
		$("#createMailContentButton").click(function() {  
			var url =  iKEP.getWebAppPath() + "/lightpack/award/awardItem/readAwardItemLinkView.do?itemId=${awardItem.itemId}";  
			
			var link = "window.open('"+url+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;"; 
	 
			var contentClone = $("#awardItemContent2").clone();
			//$(contentClone).children().remove(".tableTag");
			
	
			
			//alert($(contentClone).html());
			iKEP.sendMailPop("","", "${fn:escapeXml(awardItem.title)}", $(contentClone).html(), "", ""); 
			
			$(".icboxLayer").hide(); 
			
			return false;   
		}); 
		
	  
		$("#createMailUrlButton").click(function() {  
			var url =  iKEP.getWebAppPath() + "/lightpack/award/awardItem/readAwardItemLinkView.do?itemId=${awardItem.itemId}";   
			//var link = "window.open('"+url+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;";  
			//var content = "<a href=\"#a\" onclick=\"" + link + "\" target=\"_blank\">${fn:escapeXml(awardItem.title)}</a>" ; 
			var content = "<a href=\"" + url + "\" target=\"_blank\">${fn:escapeXml(awardItem.title)}</a>" ; 
			iKEP.sendMailPop("","", "${fn:escapeXml(awardItem.title)}", content, "", ""); 
			
			$(".icboxLayer").hide(); 
			
			return false;   
		}); 
		
		$("#createClipAwardUrlButton").click(function() {  
			var IE = (document.all)?true:false;
			var curPage = document.location.href;
			 
			if(IE){ //익스면
				window.clipawardData.setData("Text", curPage);
			}else{ //그 외 브라우저면
				temp = prompt("이 글의 트랙백 주소입니다. Ctrl+C를 눌러 클립보드로 복사하세요", curPage);
			}
		}); 
		
		$("#createPrintButton").click(function() {			
			var url = iKEP.getContextRoot() + '/lightpack/award/awardItem/readAwardItemPrintView.do?awardId=${awardItem.awardId}&itemId=${awardItem.itemId}';
			
			iKEP.popupOpen(url, {width:800, height:560}, "AwardItemPrint");	
		});	
		
		
		$("#readerListViewButton, #readerListViewButton1").click( function() {  
			var url = "<c:url value='/lightpack/award/awardItem/listReaderView.do?awardItemId=${awardItem.itemId}'/>";
			
			iKEP.popupOpen(url , {width:700, height:500});
		});
		
		
		$("#userDeleteAwardItemButton, #userDeleteAwardItemButton1").click(function() { 
			if(confirm("게시글을 삭제하시겠습니까?")) {
				$("#tagResult").ajaxLoadStart(); 
				location.href="<c:url value='/lightpack/award/awardItem/userDeleteAwardItem.do?awardId=${awardItem.awardId}&itemId=${awardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&viewMode=${viewMode}'/>";  
			} 
			
			return false; 
			
		}); 
		$("#adminDeleteAwardItemButton,#adminDeleteAwardItemButton1").click(function() {
			if(confirm("게시글을 삭제하시겠습니까?")) {
				$("#tagResult").ajaxLoadStart(); 
				location.href="<c:url value='/lightpack/award/awardItem/userDeleteAwardItem.do?awardId=${awardItem.awardId}&itemId=${awardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&viewMode=${viewMode}'/>";
				//location.href="<c:url value='/lightpack/award/awardItem/adminDeleteAwardItem.do?awardId=${awardItem.awardId}&itemId=${awardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&viewMode=${viewMode}'/>&poolIdx=" + iKEP.getUrlArguments("poolIdx"); 
			}  
			
			return false; 
			
		}); 
		
		$("#mailSendButton1,#mailSendButton2").click(function() {
			$.get("<c:url value='/lightpack/award/awardItem/mailSending.do?itemId=${awardItem.itemId}'/>")
			.success(function(data) { 
				if(data == 1) {
					alert("메일 전송이 시작되었습니다.");
				} else {
					alert("이미 메일이 전송 중입니다.");	
				}
				
			})
			.error(function(event, request, settings) { alert("error"); });  
		}); 
		
		$("#updateRecommendCountButton").click(function() { 
			if(confirm("<ikep4j:message pre="${preMessage}" key="recommend" />")) { 
				$.get("<c:url value='/lightpack/award/awardItem/updateRecommendCount.do?awardId=${awardItem.awardId}&itemId=${awardItem.itemId}'/>")
				.success(function(data) { 
					if(data == -1) {
						alert("<ikep4j:message pre="${preMessage}" key="alreadyRecommend" />");
					} else {
						$("#recommendCount").text(data);	
					}
					
				})
				.error(function(event, request, settings) { alert("error"); });  
			} 
			
	 
		}); 
		
		/* $("#createReplyAwardItemViewButton, #createReplyAwardItemViewButton1").click(function() {  
		    location.href="<c:url value='/lightpack/award/awardItem/createReplyAwardItemView.do?awardId=${awardItem.awardId}&itemId=${awardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&viewMode=${viewMode}'/>";  
		});  */
		
		
		 $("#btnMailSend").toggle( 
		     function () { 
		     	$(".icboxLayer").show(); 
		     }, 
		     function () { 
			    $(".icboxLayer").hide(); 
		     } 
		 );

		 
	    var uploaderOptions = {
		   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
		   isUpdate : false 
		}; 
        
	    var fileController = new iKEP.FileController(null, "#fileDownload", uploaderOptions); 
	    <c:if test="${award.linereply eq 1 && awardItem.tempSave ne '1'}">loadAwardLinereplyList();</c:if> 
		
		loadRelatedAwardItemList();
	
		//loadAwardItemThreadList();
		var tmpAwardMaterial1= "${awardItem.awardMaterial}";
		
		var tmpAwardMaterials = tmpAwardMaterial1.split(",");
		var tmpAwardMaterial2 = "";
		
		for(var i=0;i<tmpAwardMaterials.length;i++){
			<c:forEach var="awardMaterial" items="${awardMaterialList}" varStatus="status">
				if("${awardMaterial.comCd}" == tmpAwardMaterials[i]){
					if(tmpAwardMaterial2 == ""){
						tmpAwardMaterial2 = "${awardMaterial.comNm}";
					}else{
						tmpAwardMaterial2 = tmpAwardMaterial2+",${awardMaterial.comNm}";
					}
				}
			</c:forEach>
		}
		$("#awardMaterialTd").append(tmpAwardMaterial2);
		
	});    

})(jQuery); 

function callbackMail(){
	$jq.ajax({    
		url : "<c:url value='/lightpack/award/awardItem/updateMailCount.do'/>",     
		data : {itemId:'${awardItem.itemId}'},     
		type : "post",  
		dataType : "html",
		success : function(result) {   
		},
		error : function(event, request, settings){
		 alert("error");
		}
	}); 
}

function callbackMBlog(){
	$jq.ajax({    
		url : "<c:url value='/lightpack/award/awardItem/updateMblogCount.do'/>",     
		data : {itemId:'${awardItem.itemId}'},     
		type : "post",  
		dataType : "html",
		success : function(result) {   
		},
		error : function(event, request, settings){
		 alert("error");
		}
	}); 
}

function fileDown(url){
	window.open(url, 'filedown', 'width=800px, height=670px, scrollbars=yes');
}

//-->
</script> 
<div id="tagResult">
	<c:if test="${popupYn}"><div class="contentIframe"></c:if>
	<h1 class="none">컨텐츠영역</h1>
	<!-- bbs location 무림제지 bbs location 막음 2012.10.25 -->
	<!--
	<div class="bbs_location">
		<c:forEach var="parentAward" items="${award.parentList}" varStatus="parentAwardStatus">
			<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">${parentAward.awardName}</c:when>
				<c:otherwise>${parentAward.awardEnglishName}</c:otherwise>
			</c:choose>
			<c:if test="${not parentAwardStatus.last}">&gt;</c:if>
		</c:forEach>
	</div> 
	 -->
	<div style="height:10px"></div>
			  
	<div class="icgroup floatLeft pt5"> 
		<c:if test="${awardItem.tempSave ne '1'}">
		<%-- <div class="btn_icbox"> 
			<a id="createFavoriteButton" class="ic_rt_favorite<c:if test="${isFavorite == 'true'}"> select</c:if>" href="#a" title="<ikep4j:message pre='${preButton}' key='favorite'/>"><span><ikep4j:message pre='${preButton}' key='favorite'/></span></a>
		</div>   --%>
		<%-- <div class="btn_icbox_sel"> 
			<a href="#a" id="btnMailSend" title="<ikep4j:message pre='${preDetail}' key='emailSend'/>"><img class="ic_mail" src="<c:url value='/base/images/icon/ic_mail_2.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='emailSend'/>" /></a> 
			<div class="icboxLayer none"><!--클릭시 레이어 생성 : none 제거--> 
				<ul> 
					<li><a id="createMailContentButton"  href="#a"><ikep4j:message pre='${preDetail}' key='emailSend' post='content'/></a></li> 
					<li><a id="createMailUrlButton" href="#a"><ikep4j:message pre='${preDetail}' key='emailSend' post='url'/></a></li> 
					<li><a id="createClipAwardUrlButton" href="#a">클립보드 복사</a></li>
				</ul>  
			</div> 
		</div> --%>
		</c:if>	 
		<!-- 무림제지 메세지기능 막음 2012.10.10
		<div class="btn_icbox"> 
			<a id="createMessageButton" class="ic_note" href="#a" title="<ikep4j:message pre='${preDetail}' key='messageSend'/>"><img src="<c:url value='/base/images/icon/ic_note.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='messageSend'/>" /></a> 
		</div> 
		 --> 
		<c:if test="${CommonConstant.PACKAGE_VERSION eq CommonConstant.IKEP_VERSION_FULL}">
		<!-- 무림제지 메세지기능 막음 2012.10.10
		<div class="btn_icbox"> 
			<a id="createMircoBloggingButton"  class="ic_microblog" href="#a" title="<ikep4j:message pre='${preDetail}' key='mircoBloggingSend'/>" ><img src="<c:url value='/base/images/icon/ic_microblog.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='mircoBloggingSend'/>" /></a> 
		</div>
		 --> 
		</c:if>	
		
		<div class="btn_icbox"> 
			<a id="createPrintButton"  class="ic_note" href="#a" title="<ikep4j:message pre='${preDetail}' key='print'/>" ><img src="<c:url value='/base/images/icon/ic_print_02.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='print'/>" /></a> 
		</div>	 
	</div> 
	
		<!--blockButton Start-->
	<div class="blockButton floatRight"> 
	<ul> 
		<c:if test="${not portletYn}">
			<c:if test="${not popupYn}">
				<c:if test="${userAuthMgntYn or awardItem.registerId eq user.userId}">
					<li><a class="button" title="<ikep4j:message pre='${preButton}' key='update'/>" href="<c:url value='/lightpack/award/awardItem/updateAwardItemView.do?awardId=${awardItem.awardId}&amp;itemId=${awardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a></li>
				</c:if>
				
				<c:choose> 
				<c:when test="${userAuthMgntYn}">
					<li><a class="button" title="<ikep4j:message pre='${preButton}' key='delete'/>" id="adminDeleteAwardItemButton1" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
				</c:when>
				<c:when test="${awardItem.registerId eq user.userId}">
					<li><a class="button" title="<ikep4j:message pre='${preButton}' key='delete'/>" id="userDeleteAwardItemButton1" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
				</c:when>
				</c:choose> 
			<c:if test="${award.reply == 1}">
				<c:if test="${awardItem.tempSave ne '1'}">
					<c:if test="${awardItem.itemDelete eq 0 and awardItem.indentation lt 4 and permission.isWritePermission }">
						<li><a id="createReplyAwardItemViewButton1" class="button" href="<c:url value='/lightpack/award/awardItem/createReplyAwardItemView.do?awardId=${awardItem.awardId}&itemId=${awardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&viewMode=${viewMode}'/>"><span><ikep4j:message pre='${preButton}' key='reply'/></span></a></li> 
					</c:if>
				</c:if>
			</c:if>  
			<c:if test="${layoutType eq 'layoutNormal'}">
				<c:choose>
					<c:when test="${awardItem.tempSave eq '1'}">
						<li><a class="button" href="<c:url value='/lightpack/award/awardItem/listTempSaveItemView.do'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
					</c:when>
					<c:otherwise>
						<li><a class="button" href="<c:url value='/lightpack/award/awardItem/listAwardItemView.do?awardId=${awardItem.awardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
					</c:otherwise>
				</c:choose>
				<c:if test="${award.contentsReadPermission eq 1 || awardItem.awardId=='100006240370' || awardItem.awardId=='100006259597'}">
					<li><a id="readerListViewButton1" class="button" href="#a"><span>조회현황</span></a></li>
				</c:if>
				<c:if test="${mntrole && awardItem.readerMailFlag == '1'}">
				<li><a id="mailSendButton1" class="button" href="#a"><span>메일바로발송</span></a></li>
				</c:if>
			</c:if> 
			</c:if>
		</c:if>
	</ul> 
	</div>
	
	<div class="clear"></div>
	<!--//blockButton End--> 		
	
	<!--//pageTitle End--> 
	<div class="blockTableReadWrap">
		<!--blockListTable Start-->
		<div class="blockTableRead">
			<div class="blockTableRead_t">
				<spring:bind path="awardItem.title">
				<p id="awardItemTitle"> 
				<c:if test="${awardItem.itemDelete eq 1}"><span class="deletedItem">[<ikep4j:message pre='${preDetail}' key='itemDelete' post="deleteContents"/>]</span></c:if>
				<c:if test="${award.wordHead == 1}"><c:if test="${!empty awardItem.wordName}">[${awardItem.wordName}]</c:if></c:if>${awardItem.awardKindTxt} | [${awardItem.companyCodeTxt}] ${awardItem.awardTitle} | 등급 ${awardItem.awardGrade}		
				</p> 	
				</spring:bind> 
				<div class="summaryViewInfo">
					<c:choose>
						<c:when test="${award.anonymous eq 1}">
							<span class="summaryViewInfo_name"><!--<ikep4j:message pre='${preDetail}' key='anonymous'/>-->${awardItem.displayName}
							<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
							</span>
						</c:when>  
						<c:otherwise>
							<span class="summaryViewInfo_name">
								<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
										<c:if test="${awardItem.awardId=='100006240370' || awardItem.awardId=='100006259597'}">Happy맨</c:if>
										<c:if test="${awardItem.awardId=='100010083357' || awardItem.awardId=='100010089350' || awardItem.awardId=='100010089362'}">회계정보팀</c:if>
										<c:if test="${awardItem.awardId!='100006240370' && awardItem.awardId!='100006259597' && awardItem.awardId!='100010083357' && awardItem.awardId!='100010089350' && awardItem.awardId!='100010089362'}">
										<a href="#a" onclick="iKEP.showUserContextMenu(this, '${awardItem.registerId}', 'bottom')">${awardItem.user.userName}<!-- ${awardItem.user.jobTitleName}  --></a>
										</c:if>
										<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
									</c:when>
									<c:otherwise> 
										<c:if test="${awardItem.awardId=='100006240370' || awardItem.awardId=='100006259597'}">Happy맨</c:if>
										<c:if test="${awardItem.awardId=='100010083357' || awardItem.awardId=='100010089350' || awardItem.awardId=='100010089362'}">회계정보팀</c:if>
										<c:if test="${awardItem.awardId!='100006240370' && awardItem.awardId!='100006259597' && awardItem.awardId!='100010083357' && awardItem.awardId!='100010089350' && awardItem.awardId!='100010089362'}">
										<a href="#a" onclick="iKEP.showUserContextMenu(this, '${awardItem.registerId}', 'bottom')">${awardItem.user.userEnglishName}<!-- ${awardItem.user.jobTitleEnglishName}--></a>
										</c:if>
										<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
									</c:otherwise>
								</c:choose>
							</span>
							<!--
						  	<span>
								<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
										${awardItem.user.teamName} <img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
									</c:when>
									<c:otherwise> 
										${awardItem.user.teamEnglishName} <img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
									</c:otherwise>
								</c:choose>	 
							</span>	
							-->							
						</c:otherwise> 
					</c:choose>
					<span class="blockCommentInfo_name">
						<ikep4j:timezone date="${awardItem.registDate}" pattern="yyyy.MM.dd HH:mm:ss"/>
						<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
				    </span>
					<spring:bind path="awardItem.hitCount">
						<span><ikep4j:message pre='${preDetail}' key='${status.expression}' /> <strong>${status.value}</strong></span>
						<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
					</spring:bind>  
					<span>
					<%-- <ikep4j:message pre='${preDetail}' key='openDate' /> 
					<ikep4j:timezone date="${awardItem.startDate}" /> ~ 
					<c:if test="${awardItem.itemForever eq 1}">영구게시</c:if><c:if test="${awardItem.itemForever ne 1}"><ikep4j:timezone date="${awardItem.endDate}" /></c:if>
					</span>
					<c:if test="${!empty awardItem.disStartDate}">
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
					<span>
					Top <ikep4j:message pre='${preDetail}' key='openDate' /> 
					<ikep4j:timezone date="${awardItem.disStartDate}" /> ~ 
					<ikep4j:timezone date="${awardItem.disEndDate}" />
					</span>		
					</c:if>	  --%>
				</div>
				<%-- <c:if test="${awardItem.itemDelete eq 0}">
					<div class="recommend">
						<a id="updateRecommendCountButton" class="button_rec_num" href="#a"> 
						<spring:bind path="awardItem.recommendCount">
							<span id="recommendCount" class="num">${status.value}</span>
						</spring:bind>   
						<span class="doc"><img src="<c:url value='/base/images/icon/ic_recommend.gif'/>" alt="<ikep4j:message pre='${preButton}' key='recommend'/>" /><ikep4j:message pre='${preButton}' key='recommend'/></span></a>
					</div> 	 
				</c:if>   --%>
			</div> 
			<div class="blockDetail">
        	<table summary="">
            	<caption></caption>
                    <colgroup>
                    	<col width="10%"/>
                        <col width="23%"/>
                        <col width="10%"/>
                        <col width="23%"/>
                        <col width="10%"/>
                        <col width="24%"/>
                    </colgroup>
                    <tbody>
                        <tr>
                        	<th class="textRight">날짜</th>
                        	<td>${awardItem.awardDate}</td>
                            <th class="textRight">발행처(대상)</th>
                            <td>${awardItem.publisher}</td>
                            <th class="textRight">자료</th>
                            <td id="awardMaterialTd"></td>
                        </tr>
                        <tr>
                        	<th class="textRight">보관위치</th>
                        	<td>${awardItem.storageLocTxt} ${awardItem.storageLocDetail}</td>
                            <th class="textRight">주무부서</th>
                            <td>${awardItem.resDeptId}</td>
                            <th class="textRight">내역</th>
                            <td>${awardItem.awardTxt}</td>
                        </tr>
                        </tbody>
                        </table>
                        </div>
			
			<DIV class="none" id="awardItemContent2"><ikep4j:extractTextBody text="${awardItem.contents}"/></div>
			<div class="blockTableRead_c" id="awardItemContent">
		        <spring:htmlEscape defaultHtmlEscape="false"> 
					<spring:bind path="awardItem.contents">
						<p >${status.value}</p>
					</spring:bind> 
		        </spring:htmlEscape> 
		        <div class="clear"></div>  
				 <!--tag list-->
				<%-- <div class="tableTag" id="tagForm_${awardItem.itemId}">     <!-- 게시물 id --> 
				      <input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_BBS%>"/>
				      <input type="hidden" name="tagItemName" value="${awardItem.title}"/>
				      <input type="hidden" name="tagItemContents" value="${fn:escapeXml(awardItem.contents)}"/> 
				      <input type="hidden" name="tagItemUrl" value="/lightpack/award/awardItem/readAwardItemView.do?popupYn=true&amp;awardId=${awardItem.awardId}&amp;itemId=${awardItem.itemId}"/> <!--상세화면 URL -body 화면만 나와야 함. 도메인과 contextPash는 빼주시기 바랍니다.-->
				      <div class="tableTag">
				        <span class="ic_tag"><span><ikep4j:message pre='${preDetail}' key='tagList' /></span></span> <!--tagList--> 
				        <c:forEach var="tag" items="${awardItem.tagList}" varStatus="tagLoop">
				        	<c:if test="${tagLoop.count != 1}">, </c:if>
				        	<a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a>
				        </c:forEach>
				        <span class="rebtn">
				         <c:if test="${user.userId == awardItem.registerId or permission.isSystemAdmin or awardAdminRole}">  <!--권한 체크 등록자랑 세션userID랑 같으면 태그 수정 가능-->
				           <a href="#a" class="ic_modify" onclick="iKEP.tagging.tagFormView('${awardItem.itemId}');return false;" title="<ikep4j:message pre='${preButton}' key='update'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a>
				         </c:if>                                          <!--게시물 id--> 
				       </span>
				     </div>
				</div>   --%>
			</div>  
			<c:if test="${awardItem.attachFileCount > 0}">
				<%-- <c:if test="${tempEcmFileCount > 0}">
					<div style="text-align:right;display:none;" id="lfile">
						<c:forEach var="ecmFileData" items="${awardItem.ecmFileDataList}" varStatus="ecmFileDataStatus">
						<ul><li>
							<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" />
							<a href="" onclick="javascript:fileDown('${ecmFileData.fileUrl1}');return false;"><font style="color:gray;">${ecmFileData.fileRealName}</font></a>
							</li></ul>
						</c:forEach>
					</div>
					<div style="text-align:right;display:none;" id="sfile">
					<c:forEach var="ecmFileData" items="${awardItem.ecmFileDataList}" varStatus="ecmFileDataStatus">
					<ul><li>
						<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" />
						<a href="" onclick="javascript:fileDown('${ecmFileData.fileUrl2}');return false;"><font style="color:gray;">${ecmFileData.fileRealName}</font></a>
						</li></ul>
					</c:forEach>
					</div>
				</c:if>
				<c:if test="${tempEcmFileCount < 1}"> --%>
					<div id="fileDownload"></div>
				<%-- </c:if> --%>
			</c:if> 
			<!--//blockListTable End-->	  
			<!--tableBottom Start--> 
			<div class="tableBottom" style="margin-top: 6px;"> 
				<!--blockButton Start--> 
				<div class="blockButton"> 
					<ul> 
						<c:if test="${not portletYn}">
							<c:if test="${not popupYn}">
								<c:if test="${userAuthMgntYn or awardItem.registerId eq user.userId}">
									<li><a class="button" title="<ikep4j:message pre='${preButton}' key='update'/>" href="<c:url value='/lightpack/award/awardItem/updateAwardItemView.do?awardId=${awardItem.awardId}&amp;itemId=${awardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a></li>
								</c:if>
								<c:choose> 
								<c:when test="${userAuthMgntYn}">
									<li><a class="button" title="<ikep4j:message pre='${preButton}' key='delete'/>" id="adminDeleteAwardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
								</c:when>
								<c:when test="${awardItem.registerId eq user.userId}">
									<li><a class="button" title="<ikep4j:message pre='${preButton}' key='delete'/>" id="userDeleteAwardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
								</c:when>
								</c:choose> 
							
							<c:if test="${award.reply == 1}">
								<c:if test="${awardItem.tempSave ne '1'}">
									<c:if test="${awardItem.itemDelete eq 0 and awardItem.indentation lt 4 and permission.isWritePermission }">
										<li><a id="createReplyAwardItemViewButton" class="button" href="<c:url value='/lightpack/award/awardItem/createReplyAwardItemView.do?awardId=${awardItem.awardId}&itemId=${awardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&viewMode=${viewMode}'/>"><span><ikep4j:message pre='${preButton}' key='reply'/></span></a></li> 
									</c:if>
								</c:if>
							</c:if>  
								<c:if test="${layoutType eq 'layoutNormal'}">
									<c:choose>
										<c:when test="${awardItem.tempSave eq '1'}">
											<li><a class="button" href="<c:url value='/lightpack/award/awardItem/listTempSaveItemView.do'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
										</c:when>
										<c:otherwise>
											<li><a class="button" href="<c:url value='/lightpack/award/awardItem/listAwardItemView.do?awardId=${awardItem.awardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
										</c:otherwise>
									</c:choose>
									<c:if test="${award.contentsReadPermission eq 1 || awardItem.awardId=='100006240370' || awardItem.awardId=='100006259597'}">
										<li><a id="readerListViewButton" class="button" href="#a"><span>조회현황</span></a></li>
									</c:if>
									<c:if test="${mntrole && awardItem.readerMailFlag == '1'}">
									<li><a id="mailSendButton2" class="button" href="#a"><span>메일바로발송</span></a></li>
									</c:if>
								</c:if> 
							</c:if>
						</c:if>
					</ul> 
				</div> 
				<!--//blockButton End--> 
			</div> 
			<!--//tableBottom End--> 
		</div>   	
	</div> 
	<!--blockComment Start--> 
	<c:if test="${award.linereply eq 1 && awardItem.tempSave ne '1'}"><div id="blockComment" class="blockComment"></div></c:if>
	<!--//blockComment End-->
	<!--blockReply Start-->
	<div id="blockReply" class="blockReply"></div> 
	<!--//blockReply End--> 
	<!--blockRelated Start-->
	<div id="blockRelated" class="blockRelated"></div>
</div> 
<!--//blockRelated End-->
<c:if test="${popupYn}"></div></c:if>