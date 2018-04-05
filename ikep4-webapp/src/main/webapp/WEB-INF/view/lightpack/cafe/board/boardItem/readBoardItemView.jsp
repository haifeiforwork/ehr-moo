<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.lightpack.cafe.board.boardItem.readBoardView.header" /> 
<c:set var="preDetail"  value="message.lightpack.cafe.board.boardItem.readBoardView.detailBoardItem" />
<c:set var="preButton"  value="message.lightpack.cafe.common.button" /> 
<c:set var="preMessage" value="message.lightpack.cafe.common.boardItem" /> 
<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>  
 <%-- 메시지 관련 Prefix 선언 End --%> 
<script type="text/javascript">
<!--  
(function($){	 
	<c:if test="${board.linereply eq 1}">
	loadBoardLinereplyList = function() {  
		$("#blockComment").load('<c:url value="/lightpack/cafe/board/boardLinereply/listBoardLinereplyView.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}&popupYn=${popupYn}"/>', $("#boardLinereplySearchForm").serialize(), function() {
			<c:if test="${not popupYn}">iKEP.iFrameContentResize();</c:if>  
			
			$(".firstLineItem").focus();
		 
		}).error(function(event, request, settings) { alert("error"); });
	};	 
	</c:if>
	loadRelatedBoardItemList = function() {  
		$("#blockRelated").load('<c:url value="/lightpack/cafe/board/boardTagging/listRelatedBoardItemView.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}"/>', $("#relatedBoardItemSearchForm").serialize(), function() {
			<c:if test="${not popupYn}">iKEP.iFrameContentResize();</c:if> 
			
			if($("#blockRelated *").length == 0) {
				$("#blockRelated").hide();
			} else {
				$("#blockRelated").show();
			}
			
			$(".firstRelatedItem").focus();
		}).error(function(event, request, settings) { alert("error"); }); 
	};	 
	
	loadBoardItemThreadList = function() {  
		$("#blockReply").load('<c:url value="/lightpack/cafe/board/boardItemThread/listReplayItemThreadView.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}&itemGroupId=${boardItem.itemGroupId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}"/>', function() {
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
	} 
	
	moveBoardItem = function(result) {
		var orgBoardId = result.orgBoardId,
			targetBoardId = result.targetBoardId;
		
		var itemIds = new Array();
		$jq.ajax({
			url : "<c:url value='/lightpack/cafe/board/boardItem/moveBoardItem.do'/>?orgBoardId="+orgBoardId +"&targetBoardId="+targetBoardId+"&itemIds="+${boardItem.itemId},
			type : "get",
			loadingElement : {container:"#tagResult"},
			success : function(result) {
				location.href="<c:url value='/lightpack/cafe/board/boardItem/listBoardItemView.do?cafeId=${board.cafeId}&boardId=${board.boardId}'/>";
			}
		});	
	}; 
	
	$(document).ready(function() { 
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
				
				iKEP.addFavorite("CONTENTS","${IKepConstant.ITEM_TYPE_CODE_CAFE}", "${boardItem.itemId}", "${fn:escapeXml(boardItem.title)}", callback);
			} 
			
			return false; 
			
		}); 

		$("#createMircoBloggingButton").click(function() {  
			var url = "<c:url value='/lightpack/cafe/board/boardItem/readBoardItemLinkView.do?itemId=${boardItem.itemId}'/>";   
			iKEP.showTwittingPage("${fn:escapeXml(boardItem.title)}",url);  
		}); 
		
		 
		$("#createMessageButton").click(function() {    
			var url = "<c:url value='/lightpack/cafe/board/boardItem/readBoardItemLinkView.do?itemId=${boardItem.itemId}'/>";  
			
			var content = "<a href='"+url+"' target='_blank'>${fn:escapeXml(boardItem.title)}</a>" ;
			
			// 단순 작성 팝업 열기
		    var url = "<c:url value='/support/message/messageNew.do'/>?contents="+content;
			 
		    iKEP.popupOpen(url ,{width:800, height:670}); 
		    
			return false;  
		});  
		
		
	  
		$("#createMailContentButton").click(function() {  
			var url =  "<c:url value='/lightpack/cafe/board/boardItem/readBoardItemLinkView.do?itemId=${boardItem.itemId}'/>";  
			
			var link = "window.open('"+url+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;"; 
	 
			var content = $("#boardItemContent").html();

			iKEP.sendMailPop("","", "${fn:escapeXml(boardItem.title)}", content, "", ""); 
			
			return false;   
		}); 
		
	  
		$("#createMailUrlButton").click(function() {  
			var url =  "<c:url value='/lightpack/cafe/board/boardItem/readBoardItemLinkView.do?itemId=${boardItem.itemId}'/>";   
			var link = "window.open('"+url+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;";  
			var content = "<a href=\"#a\" onclick=\"" + link + "\" target=\"_blank\">${fn:escapeXml(boardItem.title)}</a>" ; 
			
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
		
	  
		$("#userDeleteBoardItemButton").click(function() { 
			if(confirm("<ikep4j:message pre="${preMessage}" key="deleteItem" />")) {
				$("#tagResult").ajaxLoadStart(); 
				location.href="<c:url value='/lightpack/cafe/board/boardItem/userDeleteBoardItem.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>";  
			} 
			
			return false; 
			
		}); 
		$("#adminDeleteBoardItemButton").click(function() {
			if(confirm("<ikep4j:message pre="${preMessage}" key="deleteItem" />")) {
				$("#tagResult").ajaxLoadStart(); 
				location.href="<c:url value='/lightpack/cafe/board/boardItem/adminDeleteBoardItem.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>&poolIdx=" + iKEP.getUrlArguments("poolIdx"); 
			}  
			
			return false; 
			
		}); 
		
		$("#updateRecommendCountButton").click(function() { 
			if(confirm("<ikep4j:message pre="${preMessage}" key="recommend" />")) { 
				$.get("<c:url value='/lightpack/cafe/board/boardItem/updateRecommendCount.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}'/>")
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
		
		$("#createReplyBoardItemViewButton").click(function() {  
		    location.href="<c:url value='/lightpack/cafe/board/boardItem/createReplyBoardItemView.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>";  
		}); 
		
		$("#moveBoardItemButton").click(function() {  
			iKEP.showDialog({
				title: "<ikep4j:message pre="${preMessage}" key="moveBoardItem" />",
				url: "<c:url value='/lightpack/cafe/board/boardItem/viewBoardTree.do' />?cafeId=${board.cafeId}&orgBoardId=${board.boardId}",
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
		
		loadBoardItemThreadList();
		 
	});    

})(jQuery); 


function callbackMail(){
	$jq.ajax({    
		url : "<c:url value='/lightpack/cafe/board/boardItem/updateMailCount.do'/>",     
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
		url : "<c:url value='/lightpack/cafe/board/boardItem/updateMblogCount.do'/>",     
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

//-->
</script> 
<div id="tagResult">
	<c:if test="${popupYn}"><div class="contentIframe"></c:if>
	<h1 class="none">컨텐츠영역</h1>   
	<div class="icgroup"> 
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
			<a id="createMessageButton" class="ic_note" href="#a" title="<ikep4j:message pre='${preDetail}' key='messageSend'/>"><img src="<c:url value='/base/images/icon/ic_note.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='messageSend'/>" /></a> 
		</div> 
		<div class="btn_icbox"> 
			<a id="createMircoBloggingButton"  class="ic_microblog" href="#a" title="<ikep4j:message pre='${preDetail}' key='mircoBloggingSend'/>" ><img src="<c:url value='/base/images/icon/ic_microblog.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='mircoBloggingSend'/>" /></a> 
		</div>		 
	</div> 
	
	<!--//pageTitle End--> 
	<div class="blockTableReadWrap">
		<!--blockListTable Start-->
		<div class="blockTableRead">
			<div class="blockTableRead_t">
				<spring:bind path="boardItem.title">
				<p id="boardItemTitle"> 
				<c:if test="${boardItem.itemDelete eq 1}"><span class="deletedItem">[<ikep4j:message pre='${preDetail}' key='itemDelete' post="deleteContents"/>]</span></c:if> ${boardItem.title}
					<c:if test="${not portletYn}">
					<span class="rebtn"> 
						<c:if test="${permission.isSystemAdmin or boardItem.registerId eq user.userId}">
							<a class="ic_modify" title="<ikep4j:message pre='${preButton}' key='update'/>" href="<c:url value='/lightpack/cafe/board/boardItem/updateBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a>
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
					</c:if> 		
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
										${boardItem.user.teamName}
									</c:when>
									<c:otherwise> 
										${boardItem.user.teamEnglishName}
									</c:otherwise>
								</c:choose>	 
							</span>								
						</c:otherwise> 
					</c:choose>
					<span class="blockCommentInfo_name">
						<ikep4j:timezone date="${boardItem.registDate}" pattern="yyyy.MM.dd HH:mm:ss"/> 
				    </span>
					<spring:bind path="boardItem.hitCount">
						<span><ikep4j:message pre='${preDetail}' key='${status.expression}' /> <strong>${status.value}</strong></span>  
					</spring:bind>  
					<span>
					<ikep4j:message pre='${preDetail}' key='openDate' /> 
					<ikep4j:timezone date="${boardItem.startDate}" />
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
			<c:if test="${boardItem.attachFileCount > 0}"><div id="fileDownload"></c:if> 
			<div class="blockTableRead_c" id="boardItemContent">
		        <spring:htmlEscape defaultHtmlEscape="false"> 
					<spring:bind path="boardItem.contents">
						<p >${status.value}</p>
					</spring:bind> 
		        </spring:htmlEscape> 
		        <div class="clear"></div>  
				 <!--tag list-->
				<div class="tableTag" id="tagForm_${boardItem.itemId}">     <!-- 게시물 id --> 
				      <input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_CAFE%>"/>
				      <input type="hidden" name="tagItemName" value="${boardItem.title}"/>
				      <input type="hidden" name="tagItemContents" value="${fn:escapeXml(boardItem.contents)}"/> 
				      <input type="hidden" name="tagItemUrl" value="/lightpack/cafe/board/boardItem/readBoardItemView.do?popupYn=true&amp;boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}"/> <!--상세화면 URL -body 화면만 나와야 함. 도메인과 contextPash는 빼주시기 바랍니다.-->
				      <div class="tableTag">
				        <span class="ic_tag"><span><ikep4j:message pre='${preDetail}' key='tagList' /></span></span> <!--tagList--> 
				        <c:forEach var="tag" items="${boardItem.tagList}" varStatus="tagLoop">
				        	<c:if test="${tagLoop.count != 1}">, </c:if>
				        	<a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a>
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
				<!--blockButton Start--> 
				<div class="blockButton"> 
					<ul> 
						<c:if test="${not portletYn}">
							<c:if test="${board.reply == 1}">
								<c:if test="${boardItem.itemDelete eq 0 and boardItem.indentation lt 4 and permission.isWritePermission }">
									<li><a id="createReplyBoardItemViewButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='reply'/></span></a></li> 
								</c:if>
							</c:if>  
							<c:if test="${not popupYn}">	
								<c:if test="${layoutType eq 'layoutNormal' and board.move == 1}">
									<li><a id="moveBoardItemButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='move'/></span></a></li>
								</c:if>
								<li><a class="button" href="<c:url value='/lightpack/cafe/board/boardItem/listBoardItemView.do?boardId=${boardItem.boardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
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