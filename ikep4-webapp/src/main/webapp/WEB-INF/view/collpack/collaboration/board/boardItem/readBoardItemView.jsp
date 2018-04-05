<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.board.boardItem.readBoardView.header" /> 
<c:set var="preDetail"  value="message.collpack.collaboration.board.boardItem.readBoardView.detailBoardItem" />
<c:set var="preCode"    value="message.collpack.collaboration.common.code" />
<c:set var="preButton"  value="message.collpack.collaboration.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.boardItem" />
<c:set var="preSearch"  value="message.collpack.collaboration.common.searchCondition" /> 
<c:set var="tempEcmFileCount" value="0"/>
<c:forEach var="tempEcmFileData" items="${boardItem.ecmFileDataList}" varStatus="ecmFileDataStatus">
	<c:set var="tempEcmFileCount" value="${tempEcmFileCount+1}"/>
</c:forEach>
<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>  
 <%-- 메시지 관련 Prefix 선언 End --%> 
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
	<c:if test="${board.linereply eq 1}">
	loadBoardLinereplyList = function() {  
		$("#blockComment").load('<c:url value="/collpack/collaboration/board/boardLinereply/listBoardLinereplyView.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}&popupYn=${popupYn}"/>', $("#boardLinereplySearchForm").serialize(), function() {
			<c:if test="${not popupYn}">iKEP.iFrameContentResize();</c:if>  
			
			$(".firstLineItem").focus();
		 
		}).error(function(event, request, settings) { alert("error"); });
	};	 
	</c:if>
	loadRelatedBoardItemList = function() {  
		$("#blockRelated").load('<c:url value="/collpack/collaboration/board/boardTagging/listRelatedBoardItemView.do?workspaceId=${board.workspaceId}&boardId=${boardItem.boardId}&itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}"/>', $("#relatedBoardItemSearchForm").serialize(), function() {
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
	
	loadBoardItemThreadList = function() {  
		$("#blockReply").load('<c:url value="/collpack/collaboration/board/boardItemThread/listReplayItemThreadView.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}&itemGroupId=${boardItem.itemGroupId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}"/>', function() {
			<c:if test="${not popupYn}">iKEP.iFrameContentResize();</c:if> 
			if($("#blockReply *").length == 0) {
				$("#blockReply").hide();
			} else {
				$("#blockReply").show();
			}
			
			
		}).error(function(event, request, settings) { alert("error"); }); 
	};	 
	
	//viewPopUpProfile = function(targetUserId) { 
	//	var pageURL = "<c:url value='/support/profile/getProfilePopup.do' />?targetUserId=" + targetUserId;
	//	iKEP.popupOpen( pageURL , {width:820, height:370, callback:function(result) {} });
	//} 
	viewPopUpProfile = function(targetUserId) { 
		iKEP.goProfilePopupMain(targetUserId);
		//var pageURL = "<c:url value='/support/profile/getProfile.do' />?targetUserId=" + targetUserId;
		//iKEP.popupOpen( pageURL , {width:820, height:370, callback:function(result) {} });
	}; 	
	moveBoardItem = function(result) {
		var orgBoardId = result.orgBoardId,
			targetBoardId = result.targetBoardId;

		var itemIds = new Array();
		$.get("<c:url value='/collpack/collaboration/board/boardItem/moveBoardItem.do'/>?orgBoardId="+orgBoardId +"&targetBoardId="+targetBoardId+"&itemIds="+${boardItem.itemId})
		.success(function(data) {
			location.href="<c:url value='/collpack/collaboration/board/boardItem/listBoardItemView.do?workspaceId=${board.workspaceId}&boardId=${board.boardId}'/>";
		})
		.error(function(event, request, settings) { alert("error"); }); 		
	}; 
	bodyTop = function() {
		setTimeout(function() {
			parent.topScroll();
		}, 100);	
		//parent.topScroll();
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
	        }
	        else if(xmlhttp.status==12029){
	        	$("#lfile").hide();
	          	 $("#sfile").show();
			}
	        else{
				
			}
	    } 
		if(window.parent.topScroll != null) {
			window.parent.topScroll(); 	
		}   
		
		if(window.parent.menuMark != null) { 
			window.parent.menuMark("${boardItem.boardId}");
		}
		
		iKEP.setContentImageRendering("#boardItemContent");
 
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
				
				iKEP.addFavorite("CONTENTS","${IKepConstant.ITEM_TYPE_CODE_WORK_SPACE}", "${boardItem.itemId}", "${fn:escapeXml(boardItem.title)}", callback);
			} 
			
			return false; 
			
		}); 

		$("#createMircoBloggingButton").click(function() {  
			var url = "<c:url value='/collpack/collaboration/board/boardItem/readBoardItemLinkView.do?itemId=${boardItem.itemId}'/>";   
			iKEP.showTwittingPage("${fn:escapeXml(boardItem.title)}",url);  
		}); 
		
		 
		$("#createMessageButton").click(function() {    
			var url = "<c:url value='/collpack/collaboration/board/boardItem/readBoardItemLinkView.do?itemId=${boardItem.itemId}'/>";  
			
			var content = "<a href='"+url+"' target='_blank'>${fn:escapeXml(boardItem.title)}</a>" ;
			
			// 단순 작성 팝업 열기
			//iKEP.sendMessage("contents="+content);
		    iKEP.sendMessage("contents="+encodeURIComponent(content));
			return false;  
		});  
		
		
	  
		$("#createMailContentButton").click(function() {  
			var url =  iKEP.getWebAppPath() + "/collpack/collaboration/board/boardItem/readBoardItemLinkView.do?itemId=${boardItem.itemId}";  
			
			var link = "window.open('"+url+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;"; 
	 
			var contentClone = $("#boardItemContent").clone();
			$(contentClone).children().remove(".tableTag");
			
			//alert($(contentClone).html());
			iKEP.sendMailPop("","", "${fn:escapeXml(boardItem.title)}", $(contentClone).html(), "", ""); 
			
			return false;   
		}); 
		
	  
		$("#createPrintButton").click(function() {			
			if (pr) // NS4, IE5
				window.print()
			else if (da && !mac) // IE4 (Windows)
				vbPrintPage()
			else // 다른 부라우저
				alert("죄송합니다. 이 브라우저는 이 기능을 지원하지 않습니다!");
			return false;		
		});	
		
		$("#createMailUrlButton").click(function() {  
			var url =  iKEP.getWebAppPath() + "/collpack/collaboration/board/boardItem/readBoardItemLinkView.do?itemId=${boardItem.itemId}";   
			//var link = "window.open('"+url+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;";  
			var content = "<a href=\"" + url + "\" target=\"_blank\">${fn:escapeXml(boardItem.title)}</a>" ; 
			
			iKEP.sendMailPop("","", "${fn:escapeXml(boardItem.title)}", content, "", ""); 
			
			return false;   
		}); 
		
		$("#createClipBoardUrlButton").click(function() {  
			var IE = (document.all)?true:false;
			var curPage = document.location.href;
			 
			if(IE){ //익스면
				window.clipboardData.setData("Text", curPage);
			}else{ //그 외 브라우저면
				temp = prompt("이 글의 트랙백 주소입니다. Ctrl+C를 눌러 클립보드로 복사하세요", curPage);
			}
		}); 
		
		
	  
		$("#userDeleteBoardItemButton,#userDeleteBoardItemButton1").click(function() { 
			if(confirm("<ikep4j:message pre="${preMessage}" key="deleteItem" />")) {
				$("#tagResult").ajaxLoadStart(); 
				location.href="<c:url value='/collpack/collaboration/board/boardItem/userDeleteBoardItem.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>";  
			} 
			
			return false; 
			
		}); 
		$("#adminDeleteBoardItemButton,#adminDeleteBoardItemButton1").click(function() {
			if(confirm("<ikep4j:message pre="${preMessage}" key="deleteItem" />")) {
				$("#tagResult").ajaxLoadStart(); 
				location.href="<c:url value='/collpack/collaboration/board/boardItem/adminDeleteBoardItem.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>&poolIdx=" + iKEP.getUrlArguments("poolIdx"); 
			}  
			
			return false; 
			
		}); 
		
		$("#updateRecommendCountButton").click(function() { 
			if(confirm("<ikep4j:message pre="${preMessage}" key="recommend" />")) { 
				$.get("<c:url value='/collpack/collaboration/board/boardItem/updateRecommendCount.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}'/>")
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
		
		$("#createReplyBoardItemViewButton,#createReplyBoardItemViewButton1").click(function() {  
		    location.href="<c:url value='/collpack/collaboration/board/boardItem/createReplyBoardItemView.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>";  
		}); 
		
		$("#moveBoardItemButton,#moveBoardItemButton1").click(function() {  

			iKEP.showDialog({
				title: "<ikep4j:message pre="${preMessage}" key="moveBoardItem" />",
				url: "<c:url value='/collpack/collaboration/board/boardItem/viewBoardTree.do' />?workspaceId=${board.workspaceId}&orgBoardId=${boardItem.boardId}",
				modal: true,
				width: 400,
				height: 300,
				callback : moveBoardItem
			});
			return false; 
		});	  
		
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
	    <c:if test="${board.linereply eq 1}">loadBoardLinereplyList();</c:if> 
		
		loadRelatedBoardItemList();
		
		//loadBoardItemThreadList();
		 
	});    

})(jQuery); 

function callbackMail(){
	$jq.ajax({    
		url : "<c:url value='/collpack/collaboration/board/boardItem/updateMailCount.do'/>",     
		data : {itemId:'${boardItem.itemId}'},     
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
		url : "<c:url value='/collpack/collaboration/board/boardItem/updateMblogCount.do'/>",     
		data : {itemId:'${boardItem.itemId}'},     
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
	<div class="icgroup floatLeft pt5"> 
		<div class="btn_icbox"> 
			<a id="createFavoriteButton" class="ic_rt_favorite<c:if test="${isFavorite == 'true'}"> select</c:if>" href="#a" title="<ikep4j:message pre='${preButton}' key='favorite'/>"><span><ikep4j:message pre='${preButton}' key='favorite'/></span></a>
		</div>  
		<div class="btn_icbox_sel"> 
			<a href="#a" id="btnMailSend" title="<ikep4j:message pre='${preDetail}' key='emailSend'/>"><img class="ic_mail" src="<c:url value='/base/images/icon/ic_mail_2.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='emailSend'/>" /></a> 
			<div class="icboxLayer none"><!--클릭시 레이어 생성 : none 제거--> 
				<ul> 
					<li><a id="createMailContentButton"  href="#a"><ikep4j:message pre='${preDetail}' key='emailSend' post='content'/></a></li> 
					<li><a id="createMailUrlButton" href="#a"><ikep4j:message pre='${preDetail}' key='emailSend' post='url'/></a></li> 
					<li><a id="createClipBoardUrlButton" href="#a">클립보드 복사</a></li>
				</ul> 
			</div> 
		</div>	
		<!-- 무림제지 메세지기능 막음 2012.10.10 
		<div class="btn_icbox"> 
			<a id="createMessageButton" class="ic_note" href="#a" title="<ikep4j:message pre='${preDetail}' key='messageSend'/>"><img src="<c:url value='/base/images/icon/ic_note.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='messageSend'/>" /></a> 
		</div> 
		 --> 
		<%--div class="btn_icbox"> 
			<a id="createMircoBloggingButton"  class="ic_microblog" href="#a" title="<ikep4j:message pre='${preDetail}' key='mircoBloggingSend'/>" ><img src="<c:url value='/base/images/icon/ic_microblog.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='mircoBloggingSend'/>" /></a> 
		</div--%>	
		<div class="btn_icbox"> 
			<a id="createPrintButton"  class="ic_note" href="#a" title="<ikep4j:message pre='${preDetail}' key='print'/>" ><img src="<c:url value='/base/images/icon/ic_print_02.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='print'/>" /></a> 
		</div>	 	 
	</div> 
	
	<!--//pageTitle End--> 
		
		<!--blockButton Start-->
		<div class="blockButton floatRight"> 
		<ul> 
			<c:if test="${not portletYn && param.docPopup != true}">

				<c:if test="${permission.isSystemAdmin or boardItem.registerId eq user.userId or (board.wiki == 1 && permission.isWritePermission)}">
					<li><a class="button" title="<ikep4j:message pre='${preButton}' key='update'/>" href="<c:url value='/collpack/collaboration/board/boardItem/updateBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a></li>
					<!-- <li><a class="button" id="copyButton" href="#a"><span><ikep4j:message pre='${preCopy}' key='copy'/></span></a></li> -->
				</c:if>
				<c:choose> 
				<c:when test="${permission.isSystemAdmin}">
					<li><a class="button" title="<ikep4j:message pre='${preButton}' key='delete'/>" id="adminDeleteBoardItemButton1" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
				</c:when>
				<c:when test="${boardItem.registerId eq user.userId}">
					<li><a class="button" title="<ikep4j:message pre='${preButton}' key='delete'/>" id="userDeleteBoardItemButton1" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
				</c:when>
				</c:choose> 

				<c:if test="${board.reply == 1}">
					<c:if test="${boardItem.itemDelete eq 0 and boardItem.indentation lt 4 and permission.isWritePermission }">
						<li><a id="createReplyBoardItemViewButton1" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='reply'/></span></a></li> 
					</c:if>
				</c:if>  
				<c:if test="${not popupYn}">
								<c:if test="${layoutType eq 'layoutNormal' and board.move == 1 and permission.isSystemAdmin}">
									<li><a id="moveBoardItemButton1" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='move'/></span></a></li>
								</c:if>
								<c:if test="${layoutType eq 'layoutNormal' and board.versionManage eq 1}">
									<li><a class="button" href="<c:url value='/collpack/collaboration/board/boardItemVersion/listBoardItemVersionView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='versionList'/></span></a></li>
								</c:if> 					
					<c:if test="${layoutType eq 'layoutNormal'}">
						<li><a class="button" href="<c:url value='/collpack/collaboration/board/boardItem/listBoardItemView.do?boardId=${boardItem.boardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
					</c:if> 
				</c:if>
			</c:if>
		</ul> 
		</div>
		<div class="clear"></div>
		<!--//blockButton End-->
	
	
	<div class="blockTableReadWrap">
		<!--blockListTable Start-->
		<div class="blockTableRead">
			<div class="blockTableRead_t">
				<spring:bind path="boardItem.title">
				<p id="boardItemTitle"> 
					<c:if test="${boardItem.itemDelete eq 1}"><span class="deletedItem">[<ikep4j:message pre='${preDetail}' key='itemDelete' post="deleteContents"/>]</span></c:if> 
					${boardItem.title}
					<%--c:if test="${not portletYn}">
					<span class="rebtn"> 
						<c:if test="${permission.isSystemAdmin or boardItem.registerId eq user.userId}">
							<a class="ic_modify" title="<ikep4j:message pre='${preButton}' key='update'/>" href="<c:url value='/collpack/collaboration/board/boardItem/updateBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a>
						</c:if>
						<c:choose> 
							<c:when test="${permission.isSystemAdmin}">
								<a class="ic_delete" title="<ikep4j:message pre='${preButton}' key='delete'/>" id="adminDeleteBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
							</c:when>
							<c:when test="${boardItem.registerId eq user.userId}">
								<a class="ic_delete" title="<ikep4j:message pre='${preButton}' key='delete'/>" id="userDeleteBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
							</c:when>
						</c:choose> 
					</span>
					</c:if--%> 		
				</p> 	
				</spring:bind> 
				<div class="summaryViewInfo">
					<c:choose>
						<c:when test="${board.anonymous eq 1}">
							<span class="summaryViewInfo_name"><ikep4j:message pre='${preDetail}' key='anonymous'/></span>
						</c:when>  
						<c:otherwise>
							<c:set var="user"   value="${sessionScope['ikep.user']}" />
							<c:set var="portal" value="${sessionScope['ikep.portal']}" />
							<span class="summaryViewInfo_name">
								<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
										<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom')">${boardItem.user.userName} ${boardItem.user.jobTitleName}</a>
									</c:when>
									<c:otherwise> 
										<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom')">${boardItem.user.userEnglishName} ${boardItem.user.jobTitleEnglishName}</a> 
									</c:otherwise>
								</c:choose>
							</span>
						  	<span>
								<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
									<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />	${boardItem.user.teamName}
									</c:when>
									<c:otherwise> 
									<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />	${boardItem.user.teamEnglishName}
									</c:otherwise>
								</c:choose>	 
							</span>								
						</c:otherwise> 
					</c:choose>
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
					<span class="blockCommentInfo_name">
						<ikep4j:timezone date="${boardItem.registDate}" pattern="yyyy.MM.dd HH:mm:ss"/> 
				    </span>
				    <img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
					<spring:bind path="boardItem.hitCount">
						<span><ikep4j:message pre='${preDetail}' key='${status.expression}' /> <strong>${status.value}</strong></span>  
					</spring:bind>  
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
					<span>
					<ikep4j:message pre='${preDetail}' key='registDate' /> 
					<ikep4j:timezone date="${boardItem.registDate}" />
					</span>			 
				</div>
				<c:if test="${boardItem.itemDelete eq 0}">
					<div class="recommend">
						<a id="updateRecommendCountButton" class="button_rec_num" href="#a"> 
						<spring:bind path="boardItem.recommendCount">
							<span id="recommendCount" class="num">${status.value}</span>
						</spring:bind>   
						<span class="doc"><img src="<c:url value='/base/images/icon/ic_recommend.gif'/>" alt="<ikep4j:message pre='${preButton}' key='recommend'/>" /><ikep4j:message pre='${preButton}' key='recommend'/></span></a>
					</div> 	 
				</c:if>  
			</div> 
			<c:if test="${boardItem.attachFileCount > 0}">
				<%-- <c:if test="${tempEcmFileCount > 0}">
					<div style="text-align:right;display:none;" id="lfile">
						<c:forEach var="ecmFileData" items="${boardItem.ecmFileDataList}" varStatus="ecmFileDataStatus">
						<ul><li>
							<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" />
							<a href="" onclick="javascript:fileDown('${ecmFileData.fileUrl1}');return false;"><font style="color:gray;">${ecmFileData.fileRealName}</font></a>
							</li></ul>
						</c:forEach>
					</div>
					<div style="text-align:right;display:none;" id="sfile">
					<c:forEach var="ecmFileData" items="${boardItem.ecmFileDataList}" varStatus="ecmFileDataStatus">
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
			<div class="blockTableRead_c" id="boardItemContent">
		        <spring:htmlEscape defaultHtmlEscape="false"> 
					<spring:bind path="boardItem.contents">
						<p>${status.value}</p>
					</spring:bind> 
		        </spring:htmlEscape> 
		        <div class="clear"></div>  
				 <!--tag list-->
				<div class="tableTag" id="tagForm_${boardItem.itemId}">     <!-- 게시물 id --> 
				      <input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_WORKSPACE%>"/>
				      <input type="hidden" name="tagItemSubType" value="${board.workspaceId}"/>
				      <input type="hidden" name="tagItemName" value="${boardItem.title}"/>
				      <input type="hidden" name="tagItemContents" value="${fn:escapeXml(boardItem.contents)}"/> 
				      <input type="hidden" name="tagItemUrl" value="/collpack/collaboration/board/boardItem/readBoardItemView.do?popupYn=true&amp;boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}"/> <!--상세화면 URL -body 화면만 나와야 함. 도메인과 contextPash는 빼주시기 바랍니다.-->
				      <div class="tableTag">
				        <span class="ic_tag"><span><ikep4j:message pre='${preDetail}' key='tagList' /></span></span> <!--tagList--> 
				        <c:forEach var="tag" items="${boardItem.tagList}" varStatus="tagLoop">
				        	<c:if test="${tagLoop.count != 1}">, </c:if>
				        	<a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${board.workspaceId}');bodyTop();return false;">${tag.tagName}</a>
				        </c:forEach>
				        <span class="rebtn">
				         <c:if test="${user.userId == boardItem.registerId or permission.isSystemAdmin}">  <!--권한 체크 등록자랑 세션userID랑 같으면 태그 수정 가능-->
				           <a href="#a" class="ic_modify" onclick="iKEP.tagging.tagFormView('${boardItem.itemId}');return false;" title="<ikep4j:message pre='${preButton}' key='update'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a>
				         </c:if>                                          <!--게시물 id--> 
				       </span>
				     </div>
				</div>  
			</div>  
			<!--//blockListTable End-->	  
			<!--tableBottom Start--> 
			<div class="tableBottom" style="margin-top: 6px;"> 
				<c:if test="${param.docPopup != true}">
				<!--blockButton Start--> 
				<div class="blockButton"> 
					<ul> 
						<c:if test="${not portletYn}">

					<c:if test="${not portletYn}">

						<c:if test="${permission.isSystemAdmin or boardItem.registerId eq user.userId or (board.wiki == 1 && permission.isWritePermission)}">
							<li><a class="button" title="<ikep4j:message pre='${preButton}' key='update'/>" href="<c:url value='/collpack/collaboration/board/boardItem/updateBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a></li> 
						</c:if>
						<c:choose> 
							<c:when test="${permission.isSystemAdmin}">
								<li><a class="button" title="<ikep4j:message pre='${preButton}' key='delete'/>" id="adminDeleteBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li> 
							</c:when>
							<c:when test="${boardItem.registerId eq user.userId}">
								<li><a class="button" title="<ikep4j:message pre='${preButton}' key='delete'/>" id="userDeleteBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li> 
							</c:when>
						</c:choose> 
					</c:if>
							<c:if test="${board.reply == 1}">
								<c:if test="${boardItem.itemDelete eq 0 and boardItem.indentation lt 4 and permission.isWritePermission }">
									<li><a id="createReplyBoardItemViewButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='reply'/></span></a></li> 
								</c:if>
							</c:if>										
							<c:if test="${not popupYn}">	
								<c:if test="${layoutType eq 'layoutNormal' and board.move == 1 and permission.isSystemAdmin}">
									<li><a id="moveBoardItemButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='move'/></span></a></li>
								</c:if>
								<c:if test="${layoutType eq 'layoutNormal' and board.versionManage eq 1}">
									<li><a class="button" href="<c:url value='/collpack/collaboration/board/boardItemVersion/listBoardItemVersionView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='versionList'/></span></a></li>
								</c:if> 	
								<c:if test="${layoutType eq 'layoutNormal'}">
									<li><a class="button" href="<c:url value='/collpack/collaboration/board/boardItem/listBoardItemView.do?boardId=${boardItem.boardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
								</c:if> 
							</c:if>
						</c:if>
					</ul> 
				</div> 
				<!--//blockButton End-->
				</c:if>
			</div> 
			<!--//tableBottom End--> 
		</div>   	
	</div> 
	<!--blockComment Start--> 
	<c:if test="${board.linereply eq 1}"><div id="blockComment" class="blockComment"></div></c:if>
	<!--//blockComment End-->
	<!--blockReply Start-->
	<div id="blockReply" class="blockReply"></div> 
	<!--//blockReply End--> 
	<!--blockRelated Start-->
	<div id="blockRelated" class="blockRelated"></div>
</div> 
<!--//blockRelated End-->
<c:if test="${popupYn}"></div></c:if>