<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" />
<c:set var="preHeader"  value="ui.lightpack.board.boardItem.readBoardView.header" /> 
<c:set var="preDetail"  value="ui.lightpack.board.boardItem.readBoardView.detailBoardItem" />
<c:set var="preButton"  value="ui.lightpack.common.button" /> 
<c:set var="preMessage" value="message.lightpack.common.boardItem" /> 
<c:set var="preSearch"  value="ui.ikep4.common.searchCondition" /> 
<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>  

<c:set var="user"   value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<%-- 메시지 관련 Prefix 선언 End --%> 
<script type="text/javascript">
<!--
var loadBoardLinereplyList,
	viewPopUpProfile,
	getProfile,
	goMyGallery,
	goGuestbook,
	showGalleryPopup;

(function($){	 
	$(document).ready(function() {
		if(window.parent.topScroll != null) {
			window.parent.topScroll(); 	
		}
		
		var imgContainerWidth = $("#boardItemContent_photo")
			.css("text-align", "center")
			.width();
		
		$("#boardItemContent_photo").ajaxLoadStart();
		
		var images = new Array();
		<c:forEach var="fileDataList" items="${boardItem.fileDataList}" varStatus="fileLoop">
			images.push("<c:url value="/support/fileupload/downloadFile.do"/>?fileId=${fileDataList.fileId}");
		</c:forEach>
		
		for(var i=0;i<images.length;i++) {
			var img = new Image();
			img.src = images[i];
			img.onload = function() {
				var $img = $(this).appendTo("#boardItemContent_photo");
				var imgSizeInfo = {width:this.width, height:this.height};
				if(imgSizeInfo.width > imgContainerWidth) {
					$img.attr("width", "100%");
				}
				$img.resize(function() { iKEP.iFrameContentResize(); })
					.click(showGalleryPopup)
					.css("cursor", "pointer")
					.data("sizeInfo", imgSizeInfo)
					.removeAttr("height");	// IE의 경우 image DOM을 동적으로 생성한 경우 height 속성이 기본 설정 되어 가로만 저절 하여 가로:세로 비율이 유지 되지 않음
				
				iKEP.iFrameContentResize();
				$("#boardItemContent_photo").ajaxLoadComplete();
			};
		}
		
		$(window).resize(function() {
			var imgContainerWidth = $("#boardItemContent_photo").width();
			$("img", "#boardItemContent_photo").each(function() {
				var imgSizeInfo = $(this).data("sizeInfo");
				if(imgSizeInfo) {
					if(imgSizeInfo.width > imgContainerWidth) {
						$(this).attr("width", "100%");
					} else {
						$(this).removeAttr("width");
					}
				}
			});
		});
		
		$("img", "#boardItemContent").load(function() {
			iKEP.iFrameContentResize();
		});
		
		$("#searchBoardItemButton").click(function() {	// list navigation
			//$("#searchBoardItemForm input[name=actionType]").val("search");
			$("#searchBoardItemForm input[name=actionType]").val("search");			
			$("#searchBoardItemForm").submit(); 
		});
		
		$("#adminDeleteBoardItemButton").click(function() {
			if(confirm("<ikep4j:message pre="${preMessage}" key="deleteItem" />")) {
				$("body").ajaxLoadStart(); 
				location.href="<c:url value='/lightpack/gallery/boardItem/adminDeleteBoardItem.do?targetUserId=${targetUserId}&itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>&poolIdx=" + iKEP.getUrlArguments("poolIdx"); 
			}  
		});
		
	    loadBoardLinereplyList();
<%--	    
	    $("a.ic_rt_favorite").click(function() { 
			if(confirm("<ikep4j:message pre='${preMessage}' key='addFavorite' />")) {  
				iKEP.addFavorite("CONTENTS","${IKepConstant.ITEM_TYPE_CODE_BBS}", "${boardItem.itemId}", "${fn:escapeXml(boardItem.title)}", function(data) { 
					alert(data.message);
				});
			} 			
			return false; 			
		});

	    $("a.ic_microblog").click(function() {  
			var url = "<c:url value='/lightpack/gallery/boardItem/readBoardItemLinkView.do?itemId=${boardItem.itemId}'/>";   
			iKEP.showTwittingPage("${fn:escapeXml(boardItem.title)}",url);  
		});

	    $("a.ic_note").click(function() {    
			var url = "<c:url value='/lightpack/gallery/boardItem/readBoardItemLinkView.do?itemId=${boardItem.itemId}'/>";  			
			var content = "<a href='"+url+"' target='_blank'>${fn:escapeXml(boardItem.title)}</a>" ;			
			iKEP.sendMessage("contents="+encodeURIComponent(content));
		});

		$("#createMailContentButton").click(function() {  
			var url =  "<c:url value='/lightpack/gallery/boardItem/readBoardItemLinkView.do?itemId=${boardItem.itemId}'/>";  			
			var link = "window.open('"+url+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;"; 	 
			var content = $jq("#boardItemContent").html();
			iKEP.sendMailPop("","", "${fn:escapeXml(boardItem.title)}", content, "", ""); 			
			return false;   
		}); 
		$("#createMailUrlButton").click(function() {  
			var url =  "<c:url value='/lightpack/gallery/boardItem/readBoardItemLinkView.do?itemId=${boardItem.itemId}'/>";   
			var link = "window.open('"+url+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;";  
			var content = "<a href=\"#a\" onclick=\"" + link + "\" target=\"_blank\">${fn:escapeXml(boardItem.title)}</a>" ;			
			iKEP.sendMailPop("","", "${fn:escapeXml(boardItem.title)}", content, "", ""); 			
			return false;   
		}); 		
	
		$("#createMailContentButton").click(function() {  
			//var url =  "<c:url value='/lightpack/gallery/boardItem/readBoardItemLinkView.do?itemId=${boardItem.itemId}'/>";   
			//var link = "window.open('"+url+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;";  
			//var content = "<a href=\"#a\" onclick=\"" + link + "\" target=\"_blank\">${fn:escapeXml(boardItem.title)}</a>" ;			
			
			iKEP.popupOpen("", {width:800, height:760}, "MailSendExchange");
			
			$jq('#mailForm').attr({
				target:"MailSendExchange"				
			});
			
			var content = $('#boardItemContent_photo').html()+$('#boardItemContent').html();

			$('input[name="mailContent"]').val(content);
			$('input[name="mailSubject"]').val('${fn:escapeXml(boardItem.title)}');
			$jq("#mailForm").submit();
		}); 		
	  
		$("#createMailUrlButton").click(function() {  
			var url =  "<c:url value='/lightpack/gallery/boardItem/readBoardItemLinkView.do?itemId=${boardItem.itemId}'/>";   
			var link = "window.open('"+url+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;";  
			var content = "<a href=\"#a\" onclick=\"" + link + "\" target=\"_blank\">${fn:escapeXml(boardItem.title)}</a>" ;			
			
			iKEP.popupOpen("", {width:800, height:760}, "MailSendExchange");
			
			$jq('#mailForm').attr({
				target:"MailSendExchange"				
			});
			$('input[name="mailContent"]').val(content);
			$('input[name="mailSubject"]').val('${fn:escapeXml(boardItem.title)}');
			$jq("#mailForm").submit();
		});
		
		$("#btnMailSend").toggle( 
		     function () { 
		     	$(".icboxLayer").show(); 
		     }, 
		     function () { 
			    $(".icboxLayer").hide(); 
		     } 
		 );
		
		$("#userDeleteBoardItemButton").click(function() { 
			if(confirm("<ikep4j:message pre="${preMessage}" key="deleteItem" />")) {
				$("body").ajaxLoadStart(); 
				location.href="<c:url value='/lightpack/gallery/boardItem/userDeleteBoardItem.do?targetUserId=${targetUserId}&itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>";  
			} 			
		}); 
		 
		$("#updateRecommendCountButton").click(function() { 
			if(confirm("<ikep4j:message pre="${preMessage}" key="recommend" />")) { 
				$.get("<c:url value='/lightpack/gallery/boardItem/updateRecommendCount.do?targetUserId=${targetUserId}&itemId=${boardItem.itemId}'/>")
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
		    location.href="<c:url value='/lightpack/gallery/boardItem/createReplyBoardItemView.do?targetUserId=${targetUserId}&itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>";  
		});
--%>
	});    

	loadBoardLinereplyList = function() {  
		$("#blockComment").load('<c:url value="/lightpack/gallery/boardLinereply/listBoardLinereplyView.do?targetUserId=${targetUserId}&itemId=${boardItem.itemId}"/>', $("#boardLinereplySearchForm").serialize(), function() {
			iKEP.iFrameContentResize();			
			//$(".firstLineItem").focus();		 
		}).error(function(event, request, settings) { alert("error"); });
	};	 

	viewPopUpProfile = function(targetUserId) { 
		iKEP.goProfilePopupMain(targetUserId);
		//var pageURL = "<c:url value='/support/profile/getProfilePopup.do' />?targetUserId=" + targetUserId;
		//iKEP.popupOpen( pageURL , {width:820, height:370, callback:function(result) {} });
	};
	
	// 다른 사람의 프로파일로 이동
	getProfile = function() {
		document.location.href = "<c:url value='/support/profile/getProfile.do?targetUserId=${targetUserId}'/>";
	};
	
	// My Gallery 이동
	goMyGallery = function() {
		document.location.href = "<c:url value='/lightpack/gallery/boardItem/listBoardItemView.do?targetUserId=${targetUserId}&userId=${user.userId}&userLocale=${user.localeCode}'/>" ;
	};	
	
	// 방명록 이동
	goGuestbook = function() {
		document.location.href = "<c:url value='/support/guestbook/listGuestbook.do?targetUserId=${targetUserId}'/>" ;
	};
	
	showGalleryPopup = function() {
		//var winGallery = window.open("about:blank", "winGallery", "width=500, height=400, scrollbars=yes");
		var imgSizeInfo = $(this).data("sizeInfo");
		var offset = ($.browser.msie && Number($.browser.version) < 9) ? 40 : 20 ;
		if(imgSizeInfo) {
			var winWidth = screen.width+offset < imgSizeInfo.width ?  screen.width : imgSizeInfo.width+offset;
			var winHeight = screen.height+offset < imgSizeInfo.height ?  screen.height : imgSizeInfo.height+offset;
			if(winWidth < 300) winWidth = 300;
			if(winHeight < 300) winHeight = 300;
			
			var winGallery = iKEP.popupOpen("about:blank", {width:winWidth, height:winHeight}, "winGallery");
			var html = '<a onclick="window.close()" href="#a"><img src="' + $(this).attr("src") + '" border="0"/></a>';
			winGallery.document.write(html);
			winGallery.document.focus();
		}
	}
	
})(jQuery); 
//-->
</script>

<%--tab Start-->		
<div id="divTab2" class="iKEP_tab_menu_common">
	<ul>
		<li><a href="#a" onclick="getProfile();"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${profile.userName}"/></c:when><c:otherwise><c:out value="${profile.userEnglishName}"/></c:otherwise></c:choose><ikep4j:message pre='${preProfileMain}' key='profile.owner' /></a></li>
		<li class="selected"><a href="#a" onclick="goMyGallery();"><ikep4j:message pre='${preProfileMain}' key='profile.photo' /></a></li>
		<li><a href="#a" onclick="goGuestbook();"><ikep4j:message pre='${preProfileMain}' key='profile.guestbook' /></a></li>
	</ul>													
</div>		
<!--//tab End--%>
<div style="text-align:right; padding:0 20px 8px 0;">
	<a href="<c:url value='/support/profile/getProfile.do?targetUserId=${targetUserId}'/>"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${profile.userName}"/></c:when><c:otherwise><c:out value="${profile.userEnglishName}"/></c:otherwise></c:choose><ikep4j:message pre='${preProfileMain}' key='profile.owner' /></a>
</div>	

<div>
	<c:if test="${popupYn}"><div class="contentIframe"></c:if>

	<h1 class="none">content area</h1>   
	<%--div class="icgroup"> 
		<div class="btn_icbox"> 
			<a class="ic_rt_favorite" href="#a"><span><ikep4j:message pre='${preButton}' key='favorite'/></span></a>
		</div>  
		<div class="btn_icbox_sel"> 
			<a href="#a" id="btnMailSend"><img class="ic_mail" src="<c:url value='/base/images/icon/ic_mail_2.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='emailSend'/>" /></a> 
			<div class="icboxLayer none">
			<!--클릭시 레이어 생성 : none 제거--> 
				<ul> 
					<li><a id="createMailContentButton"  href="#a"><ikep4j:message pre='${preDetail}' key='emailSend' post='content'/></a></li> 
					<li><a id="createMailUrlButton" href="#a"><ikep4j:message pre='${preDetail}' key='emailSend' post='url'/></a></li> 
				</ul> 
			</div> 
		</div>	 
		<div class="btn_icbox"> 
			<a class="ic_note" href="#a"><img src="<c:url value='/base/images/icon/ic_note.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='messageSend'/>" /></a> 
		</div> 
		<div class="btn_icbox"> 
			<a  class="ic_microblog" href="#a"><img src="<c:url value='/base/images/icon/ic_microblog.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='mircoBloggingSend'/>" /></a> 
		</div>			 
	</div--%> 
	
	<div style="float:right; width:334px; padding:10px 0 0 10px; background:#f6f6f6;">
		<form id="searchBoardItemForm" method="get" action="<c:url value='/lightpack/gallery/boardItem/readBoardItemView.do' />">  
		<spring:bind path="searchCondition.sortColumn">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind> 		
		<spring:bind path="searchCondition.sortType">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind> 
		<spring:bind path="searchCondition.layoutType">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind>   
		<spring:bind path="searchCondition.actionType">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind>
		<spring:bind path="searchCondition.targetUserId">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind>      
		<spring:bind path="searchCondition.popupYn">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind>
		<input name="itemId" type="hidden" value="${boardItem.itemId}" title="" />
		<a href="#a" id="searchBoardItemButton"></a>
		
			<!--expert_topList Start-->
			<div style="margin-bottom:5px;"><ikep4j:message pre='${preSearch}' key='pageCount.info' />(${searchResult.recordCount})</div>
			<div id="slider" class="MyImage_topList">
				<c:forEach var="boardItemList" varStatus="varStatus" items="${searchResult.entity}">
					<div class="MyImage_topPhoto_3">
						<span><a href="<c:url value='/lightpack/gallery/boardItem/readBoardItemView.do?targetUserId=${targetUserId}&amp;pageIndex=${searchResult.pageIndex}&amp;itemId=${boardItemList.itemId}&amp;popupYn=${popupYn}'/>"><img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${boardItemList.imageFileId}&amp;thumbnailYn=Y" alt="Image"/></a></span>
						<div class="MyImage_topPhoto_info_3">
							<div class="ellipsis MyImage_topPhoto_title"><a href="<c:url value='/lightpack/gallery/boardItem/readBoardItemView.do?targetUserId=${targetUserId}&amp;pageIndex=${searchResult.pageIndex}&amp;itemId=${boardItemList.itemId}&amp;popupYn=${popupYn}'/>">${ikep4j:replaceQuot(boardItemList.title)}</a></div>
						</div>
					</div>			
				</c:forEach>  		
			</div>
			<div class="clear"></div>
			<!--Page Numbur Start--> 
			<spring:bind path="searchCondition.pageIndex">
			<ikep4j:pagination searchButtonId="searchBoardItemButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind>  
		</form>						
	</div>
	
	<div class="blockTableReadWrap" style="margin-right:354px;">
		<!--blockListTable Start-->
		<div class="blockTableRead" style="min-width:250px;">
		
			<div class="blockTableRead_t">
				<spring:bind path="boardItem.title">
				<p id="boardItemTitle">${boardItem.title}</p> 	
				</spring:bind> 
				
				<div class="summaryViewInfo" style="clear:none;">
					<span class="summaryViewInfo_name">
						<c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
								<a href="#a" onclick="javascript:viewPopUpProfile('${boardItem.registerId}');">${boardItem.user.userName} ${boardItem.user.jobTitleName}</a>
							</c:when>
							<c:otherwise> 
								<a href="#a" onclick="javascript:viewPopUpProfile('${boardItem.registerId}');">${boardItem.user.userEnglishName} ${boardItem.user.jobTitleEnglishName}</a> 
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

					<span class="blockCommentInfo_name">
						<ikep4j:timezone date="${boardItem.registDate}" pattern="yyyy.MM.dd HH:mm:ss"/> 
				    </span>
					<spring:bind path="boardItem.hitCount">
						<span><ikep4j:message pre='${preDetail}' key='${status.expression}' /> <strong>${status.value}</strong></span>  
					</spring:bind>  
				</div>
			</div> 
			
			
			<div class="blockTableRead_c">
				<div id="boardItemContent_photo" style="min-height:100px;"></div>
		        <spring:htmlEscape defaultHtmlEscape="false"> 
					<spring:bind path="boardItem.contents">
						<div id="boardItemContent" style="margin-top:25px; overflow:auto;">${status.value}</div>
					</spring:bind> 
		        </spring:htmlEscape> 
			</div>  
			

			 
			<!--tableBottom Start--> 
			<div class="tableBottom" style="margin-top: 6px;"> 			
				<!--blockButton Start--> 
				<div class="blockButton"> 
					<ul> 
						<c:if test="${targetUserId == user.userId}">
							<li><a class="button" title="<ikep4j:message pre='${preButton}' key='update'/>" href="<c:url value='/lightpack/gallery/boardItem/updateBoardItemView.do?targetUserId=${targetUserId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a></li>
							<li><a class="button" title="<ikep4j:message pre='${preButton}' key='delete'/>" id="adminDeleteBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
						</c:if> 					
						<li><a class="button" href="<c:url value='/lightpack/gallery/boardItem/listBoardItemView.do?targetUserId=${targetUserId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
					</ul> 
				</div> 
				<!--//blockButton End--> 
			</div>
			<!--//tableBottom End--> 
			
		</div>

		<!--blockComment Start--> 
		<div id="blockComment" class="blockComment"></div>
		<!--//blockComment End-->
	</div>
	
	<div class="clear"></div>

	<c:if test="${popupYn}"></div></c:if>
</div>

<form name="mailForm" id="mailForm" action="${mailUrl}/eW/eWM/Mail/Write/WriteMail.aspx" method="post">
	<input type="hidden" name="mailCmd" value ="OTHER" />
	<input type="hidden" id="mailContent" name="mailContent" value ="" />
	<input type="hidden" id="emailAddr" name="emailAddr" value ="" />
	<input type="hidden" id="mailSubject" name="mailSubject" value ="" />
</form>
