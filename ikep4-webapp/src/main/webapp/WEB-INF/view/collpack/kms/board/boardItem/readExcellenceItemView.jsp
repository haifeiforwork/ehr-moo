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
(function($){	 
	loadBoardLinereplyList = function() {  
		$("#blockComment").load('<c:url value="/collpack/kms/board/boardLinereply/listBoardLinereplyView.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}&popupYn=${popupYn}"/>', $("#boardLinereplySearchForm").serialize(), function() {
			<c:if test="${not popupYn}">
				iKEP.iFrameContentResize();
			</c:if>  
			
			$(".firstLineItem").focus();
		 
		}).error(function(event, request, settings) { alert("error"); });
	};	 
	loadRelatedBoardItemList = function() {  
		$("#blockRelated").load('<c:url value="/collpack/kms/board/boardTagging/listRelatedBoardItemView.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&loginUserId=${user.userId}"/>', $("#relatedBoardItemSearchForm").serialize(), function() {
			<c:if test="${not popupYn}">iKEP.iFrameContentResize();</c:if> 
			
			if($("#blockRelated *").length == 0) {
				$("#blockRelated").hide();
			} else {
				$("#blockRelated").show();
			}
			
			//$(".firstRelatedItem").focus();
		}).error(function(event, request, settings) { alert("error"); }); 
	};	 
	
	loadBoardItemThreadList = function() {  
		$("#blockReply").load('<c:url value="/collpack/kms/board/boardItemThread/listReplayItemThreadView.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}&itemGroupId=${boardItem.itemGroupId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}"/>', function() {
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
		$.get("<c:url value='/collpack/kms/board/boardItem/moveBoardItem.do'/>?orgBoardId="+orgBoardId +"&targetBoardId="+targetBoardId+"&itemIds="+${boardItem.itemId})
		.success(function(data) {
			location.href="<c:url value='/collpack/kms/board/boardItem/listBoardItemView.do?workspaceId=${board.workspaceId}&boardId=${board.boardId}'/>";
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
				
				iKEP.addFavorite("CONTENTS","KMS", "${boardItem.itemId}", "${fn:escapeXml(boardItem.title)}", callback);
			} 
			
			return false; 
			
		}); 

		$("#createMircoBloggingButton").click(function() {  
			var url = "<c:url value='/collpack/kms/board/boardItem/readBoardItemLinkView.do?itemId=${boardItem.itemId}'/>";   
			iKEP.showTwittingPage("${fn:escapeXml(boardItem.title)}",url);  
		}); 
		
		$("#readerListViewButton").click( function() {  
			var url = "<c:url value='/collpack/kms/board/boardItem/listReaderView.do?boardItemId=${boardItem.itemId}'/>";
			
			iKEP.popupOpen(url , {width:700, height:500});
		});
		
		 
		$("#createMessageButton").click(function() {    
			var url = "<c:url value='/collpack/kms/board/boardItem/readBoardItemLinkView.do?itemId=${boardItem.itemId}'/>";  
			
			var content = "<a href='"+url+"' target='_blank'>${fn:escapeXml(boardItem.title)}</a>" ;
			
			// 단순 작성 팝업 열기
			//iKEP.sendMessage("contents="+content);
		    iKEP.sendMessage("contents="+encodeURIComponent(content));
			return false;  
		});  
		
		
	  
		$("#createMailContentButton").click(function() {  
			var url =  "<c:url value='/collpack/kms/board/boardItem/readBoardItemLinkView.do?itemId=${boardItem.itemId}'/>";  
			
			var link = "window.open('"+url+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;"; 
			var contentClone = $("#boardItemContent2").clone();
			iKEP.sendMailPop("","", "${fn:escapeXml(boardItem.title)}", $(contentClone).html(), "", "");	 
			//var content = $("#boardItemContent").html();
			//iKEP.sendMailPop("","", "${fn:escapeXml(boardItem.title)}", content, "", ""); 
			
			return false;   
		}); 
		
	  
		$("#createMailUrlButton").click(function() {  
			var url =  iKEP.getWebAppPath() + "<c:url value='/collpack/kms/board/boardItem/readBoardItemLinkView.do?itemId=${boardItem.itemId}'/>";   
			//var link = "window.open('"+url+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;";  
			//var content = "<a href=\"#a\" onclick=\"" + link + "\" target=\"_blank\">${fn:escapeXml(boardItem.title)}</a>" ; 
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
				location.href="<c:url value='/collpack/kms/board/boardItem/userDeleteBoardItem.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&isKnowhow=${isKnowhow}'/>";  
			} 
			
			return false; 
			
		}); 
		$("#adminDeleteBoardItemButton,#adminDeleteBoardItemButton1").click(function() {
			if(confirm("<ikep4j:message pre="${preMessage}" key="deleteItem" />")) {
				$("#tagResult").ajaxLoadStart(); 
				location.href="<c:url value='/collpack/kms/board/boardItem/adminDeleteBoardItem.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&isKnowhow=${isKnowhow}'/>&poolIdx=" + iKEP.getUrlArguments("poolIdx"); 
			}  
			
			return false; 
			
		}); 
		
		$("#updateRecommendCountButton").click(function() { 
			if("${boardItem.registerId}" == "${user.userId}"){
				alert("본인이 작성한 글에는 추천이 불가능합니다.");
				return;
			}
			if(confirm("<ikep4j:message pre="${preMessage}" key="recommend" />")) { 
				$.get("<c:url value='/collpack/kms/board/boardItem/updateRecommendCount.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}'/>")
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
		
		$("#createPrintButton,#createPrintButton1").click(function() {    
			// 단순 작성 팝업 열기

			var url = iKEP.getContextRoot() + '/collpack/kms/board/boardItem/readBoardItemPrint.do?itemId=${boardItem.itemId}';
			
			iKEP.popupOpen(url, {width:800, height:560}, "BoardItemPrint");
		});
		
		$("#createReplyBoardItemViewButton,#createReplyBoardItemViewButton1").click(function() {  
		    location.href="<c:url value='/collpack/kms/board/boardItem/createReplyBoardItemView.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>";  
		}); 
		
		$("#moveBoardItemButton,#moveBoardItemButton1").click(function() {  

			iKEP.showDialog({
				title: "<ikep4j:message pre="${preMessage}" key="moveBoardItem" />",
				url: "<c:url value='/collpack/kms/board/boardItem/viewBoardTree.do' />?workspaceId=${board.workspaceId}&orgBoardId=${boardItem.boardId}",
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
	    
	    loadBoardLinereplyList(); 
		
		loadRelatedBoardItemList();
		
		//loadBoardItemThreadList();
		 
	});    

})(jQuery); 

function callbackMail(){
	$jq.ajax({    
		url : "<c:url value='/collpack/kms/board/boardItem/updateMailCount.do'/>",     
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
		url : "<c:url value='/collpack/kms/board/boardItem/updateMblogCount.do'/>",     
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
	<div class="icgroup pt5"> 
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
		<div class="btn_icbox"> 
			<a id="createPrintButton"  class="ic_note" href="#a" title="인쇄" ><img src="<c:url value='/base/images/icon/ic_print_02.gif'/>" alt="인쇄" /></a> 
		</div>
		<%--div class="btn_icbox"> 
			<a id="createMircoBloggingButton"  class="ic_microblog" href="#a" title="<ikep4j:message pre='${preDetail}' key='mircoBloggingSend'/>" ><img src="<c:url value='/base/images/icon/ic_microblog.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='mircoBloggingSend'/>" /></a> 
		</div--%>		 
	</div> 
	
	<!--//pageTitle End--> 

	
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
							<a class="ic_modify" title="<ikep4j:message pre='${preButton}' key='update'/>" href="<c:url value='/collpack/kms/board/boardItem/updateBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a>
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
						<c:when test="${boardItem.isKnowhow eq 0 || boardItem.isKnowhow eq 3}">
							<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom')">${boardItem.registerName}</a>
							<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
							<span class="blockCommentInfo_name">
								${boardItem.groupName} 
						    </span>
						    <img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
						</c:when>
						<c:otherwise>
							<c:if test="${permission.isSystemAdmin}">
								<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom')">${boardItem.registerName}</a>
								<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
								<span class="blockCommentInfo_name">
									${boardItem.groupName} 
							    </span>
							    <img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
							</c:if>
						</c:otherwise>
					</c:choose>
					<spring:bind path="boardItem.hitCount">
						<span><ikep4j:message pre='${preDetail}' key='${status.expression}' /> <strong>${status.value}</strong></span>  
					</spring:bind>  
					|
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
			<DIV class="none" id="boardItemContent2">
					<strong>
					<c:choose>
				<c:when test="${boardItem.isKnowhow eq 0 || boardItem.isKnowhow eq 3}">
					작성자: ${boardItem.registerName}(${boardItem.groupName})&nbsp;&nbsp;&nbsp;
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
					<ikep4j:message pre='${preDetail}' key='registDate' />: 
					<ikep4j:timezone date="${boardItem.registDate}" /></strong>
					<br/><br/>
			<ikep4j:extractTextBody text="${boardItem.contents}"/></div>
			<div class="blockTableRead_c" id="boardItemContent">
		        <spring:htmlEscape defaultHtmlEscape="false"> 
					<spring:bind path="boardItem.contents">
						<p>${status.value}</p>
					</spring:bind> 
		        </spring:htmlEscape> 
                <!--tag list-->
				<div class="tableTag" id="tagForm_${boardItem.itemId}">     <!-- 게시물 id --> 
				      <input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_KMS%>"/>
				      <input type="hidden" name="tagItemSubType" value="${tag.tagItemSubType}"/>
				      <input type="hidden" name="tagItemName" value="${boardItem.title}"/>
				      <input type="hidden" name="tagItemContents" value="${fn:escapeXml(boardItem.contents)}"/> 
				      <input type="hidden" name="tagItemUrl" value="/collpack/kms/board/boardItem/readBoardItemView.do?popupYn=true&amp;boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}"/> <!--상세화면 URL -body 화면만 나와야 함. 도메인과 contextPash는 빼주시기 바랍니다.-->
				      <div class="tableTag">
				        <span class="ic_tag"><span><ikep4j:message pre='${preDetail}' key='tagList' /></span></span> <!--tagList--> 
				        <c:forEach var="tag" items="${boardItem.tagList}" varStatus="tagLoop">
				        	<c:if test="${tagLoop.count != 1}">, </c:if>
				        	<a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '');bodyTop();return false;">${tag.tagName}</a>
				        </c:forEach>
				        <span class="rebtn">
				         <c:if test="${user.userId == boardItem.registerId or permission.isSystemAdmin}">  <!--권한 체크 등록자랑 세션userID랑 같으면 태그 수정 가능-->
				           <a href="#a" class="ic_modify" onclick="iKEP.tagging.tagFormView('${boardItem.itemId}');return false;" title="<ikep4j:message pre='${preButton}' key='update'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a>
				         </c:if>                                          <!--게시물 id--> 
				       </span>
				     </div>
				</div>  
                <div class="summaryViewInfo">
					<span class="summaryViewInfo_name">
						지식맵 :  
						<c:if test="${boardItem.isKnowhow eq 0}"> 업무노하우  > </c:if>
						<c:if test="${boardItem.isKnowhow eq 1}"> 일반정보  > </c:if>
						<c:if test="${boardItem.isKnowhow eq 3}"> 원문 게시판  > </c:if>
			<spring:bind path="board.parentList">
					<c:forEach var="boardItem" varStatus="varStatus" items="${status.value}"> 
						<c:if test="${not varStatus.last}">
							<a href="<c:url value='/collpack/kms/board/boardItem/listBoardItemView.do?boardId=${boardItem.boardId}&amp;isKnowhow=${boardItem.isKnowhow}'/>">${boardItem.boardName}</a> > 
						</c:if> 
						<c:if test="${varStatus.last}"><a href="<c:url value='/collpack/kms/board/boardItem/listBoardItemView.do?boardId=${boardItem.boardId}&amp;isKnowhow=${boardItem.isKnowhow}'/>">${boardItem.boardName} </a></c:if> 
					</c:forEach>
			</spring:bind>				
					
					</span>
					<span>출처 : ${boardItem.targetSource} </span>
					<span>상태 : 
						<c:if test="${boardItem.status == 0}"> 
							임시저장
						</c:if>
						<c:if test="${boardItem.status == 1}"> 
							작성완료
						</c:if>
						<c:if test="${boardItem.status == 2}"> 
							전문가요청
						</c:if>
						<c:if test="${boardItem.status == 3}"> 
							지식확인
						</c:if>
						<c:if test="${boardItem.status == 4}"> 
							전문가확인
						</c:if>
						<c:if test="${boardItem.status == 5}"> 
							결재확인
						</c:if>
					</span>
				</div>
		        
		        <div class="clear"></div>  
 
			</div>  
			<!--//blockListTable End-->	  
			<!--tableBottom Start--> 
			<div class="tableBottom" style="margin-top: 6px;"> 
				<c:if test="${param.docPopup != true}">
				<!--blockButton Start--> 
				<div class="blockButton"> 
					<ul>
						<c:if test="${permission.isSystemAdmin}">
						<li><a id="readerListViewButton" class="button" href="#a"><span>조회현황</span></a></li>
						</c:if>
						<li><a id="createPrintButton1" class="button" href="#a" title="인쇄"><span>인쇄</span></a></li>  
						<c:if test="${not portletYn}">
							<c:if test="${not popupYn}">
								<c:if test="${permission.isSystemAdmin or (boardItem.registerId eq user.userId and boardItem.status < 3)}">
									<li><a class="button" title="<ikep4j:message pre='${preButton}' key='update'/>" href="<c:url value='/collpack/kms/board/boardItem/updateBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}&amp;pageGubun=${pageGubun}&amp;isKnowhow=${isKnowhow}'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a></li> 
								</c:if>
								<c:choose> 
									<c:when test="${permission.isSystemAdmin}">
										<li><a class="button" title="<ikep4j:message pre='${preButton}' key='delete'/>" id="adminDeleteBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li> 
									</c:when>
									<c:when test="${boardItem.registerId eq user.userId and boardItem.status < 3}">
										<li><a class="button" title="<ikep4j:message pre='${preButton}' key='delete'/>" id="userDeleteBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li> 
									</c:when>
								</c:choose> 
								<c:if test="${layoutType eq 'layoutNormal' and board.move == 1 and permission.isSystemAdmin}">
									<li><a id="moveBoardItemButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='move'/></span></a></li>
								</c:if>
								<c:if test="${layoutType eq 'layoutNormal'}">
									<li><a class="button" href="<c:url value='/collpack/kms/board/boardItem/listExcellenceItemView.do?boardId=${boardItem.boardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}&amp;isKnowhow=${isKnowhow}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
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

    <!--blockReply Start-->
	<div class="blockReply">
		<div class="blockReply_t">평가기준</div>
		<div class="blockDetail">
        	<table summary="평가기준">
            	<caption></caption>
                                        <colgroup>
                                        	<col width="12%"/>
                                            <col width="15%"/>
                                            <col width="40%"/>
                                            <!-- col width="33%"/-->
                                            <col width="15%"/>
                                            <col width="18%"/>
                                        </colgroup>
                                        <tbody>
                                            <tr>
                                            	<th rowspan="3" class="textRight" scope="row">일반정보/원문 게시판</th>
                                                <th scope="row" class="textRight">지식특성</th>
                                                <td colspan="3"><label><input id="checkboxInfo" name="info" class="checkbox" title="checkbox" type="checkbox" value="1" <c:if test="${boardItem.itemDisplays[0] == 1}">checked</c:if> disabled="true"/> ${boardItem.assessNames[0]} (+1)</label></td>
                                            </tr>
                                            <tr>
                                                <th scope="row" class="textRight">지식내용 및 구성</th>
                                                <td colspan="3">
                                                	<label><input id="checkboxInfo" name="info" class="checkbox" title="checkbox" type="checkbox" value="1" <c:if test="${boardItem.itemDisplays[1] == 1}">checked</c:if> disabled="true"/> ${boardItem.assessNames[1]} (+1)</label></br>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th scope="row" class="textRight">지식활용</th>
                                                <td colspan="3"><label><input id="checkboxInfo" name="info" class="checkbox" title="checkbox" type="checkbox" value="1" <c:if test="${boardItem.itemDisplays[2] == 1}">checked</c:if> disabled="true"/> ${boardItem.assessNames[2]} (+1)</label></td>
                                            </tr>
                                            <tr>
                                              <th rowspan="2" class="textRight" scope="row">업무노하우</th>
                                              <th scope="row" class="textRight">중요도</th>
                                              <td colspan="3">
                                              	<label><input id="checkboxInfo" name="info" class="checkbox" title="checkbox" type="checkbox" value="-1" <c:if test="${boardItem.itemDisplays[3] == 1}">checked</c:if> disabled="true"/> ${boardItem.assessNames[3]} (3점)</label> &nbsp;
                                                <label><input id="checkboxInfo" name="info" class="checkbox" title="checkbox" type="checkbox" value="-1" <c:if test="${boardItem.itemDisplays[4] == 1}">checked</c:if> disabled="true"/> ${boardItem.assessNames[4]} (2점)</label> &nbsp;
                                                <label><input id="checkboxInfo" name="info" class="checkbox" title="checkbox" type="checkbox" value="-1" <c:if test="${boardItem.itemDisplays[5] == 1}">checked</c:if> disabled="true"/> ${boardItem.assessNames[5]} (1점)</label>
                                              </td>
                                            </tr>
                                            <tr>
                                              <th scope="row" class="textRight">활용도</th>
                                              <td colspan="3">
                                              	<label><input id="checkboxInfo" name="info" class="checkbox" title="checkbox" type="checkbox" value="-1" <c:if test="${boardItem.itemDisplays[6] == 1}">checked</c:if> disabled="true"/> ${boardItem.assessNames[6]} (3점)</label> &nbsp;
                                                <label><input id="checkboxInfo" name="info" class="checkbox" title="checkbox" type="checkbox" value="-1" <c:if test="${boardItem.itemDisplays[7] == 1}">checked</c:if> disabled="true"/> ${boardItem.assessNames[7]} (2점)</label> &nbsp;
                                                <label><input id="checkboxInfo" name="info" class="checkbox" title="checkbox" type="checkbox" value="-1" <c:if test="${boardItem.itemDisplays[8] == 1}">checked</c:if> disabled="true"/> ${boardItem.assessNames[8]} (1점)</label>
                                              </td>
                                            </tr>
                                            <tr>
                                                <th colspan="2" scope="row" class="textRight">평가점수</th>
                                                <td><input type="text" class="inputbox" title="inputbox" name="mark" value="${boardItem.mark}" size="20" readonly /></td>
                                                <th scope="row" class="textRight">지식등급</th>
                                                <td><input type="text" class="inputbox" title="inputbox" name="infoGrade" value="<c:if test='${boardItem.infoGrade == "C"}'>공유</c:if><c:if test='${boardItem.infoGrade == "A"}'>보안</c:if><c:if test='${boardItem.infoGrade == "B"}'>보안</c:if>" size="15" readonly /></td>
                                            </tr>
                                            <tr>
                                                <th colspan="2" scope="row" class="textRight">추천/댓글점수</th>
                                                <td colspan="3">
                                                	${boardItem.score}
                                                </td>
                                            </tr>
                                        </tbody>
        	</table>
    	</div>					
	</div>
    <div class="mb10"></div>
	<!--//blockReply End-->
	
	<!--blockComment Start--> 
	<!-- c:if test="${board.linereply eq 1}"-->
	<div id="blockComment" class="blockComment"></div>
	<!-- /c:if-->
	<!--//blockComment End-->
	
	<!--blockReply Start-->
	<div id="blockReply" class="blockReply"></div> 
	<!--//blockReply End--> 
	
	<!--blockRelated Start-->
	<div id="blockRelated" class="blockRelated"></div>
</div> 
<!--//blockRelated End-->
<c:if test="${popupYn}"></div></c:if>