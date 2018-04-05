<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/fileUploadControll.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.kms.announce.boardItem.listBoardView.header" />
<c:set var="preDetail"  value="message.collpack.kms.announce.boardItem.createBoardView.detail" /> 
<c:set var="preList"    value="message.collpack.kms.announce.boardItem.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.kms.announce.boardItem.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.kms.announce.boardItem.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.kms.announce.boardItem.message.script" />
<c:set var="tempEcmFileCount" value="0"/>
<c:forEach var="tempEcmFileData" items="${qnaItem.ecmFileDataList}" varStatus="ecmFileDataStatus">
	<c:set var="tempEcmFileCount" value="${tempEcmFileCount+1}"/>
</c:forEach>
<%-- 메시지 관련 Prefix 선언 End --%> 
 
<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
 
<script type="text/javascript">
<!--  
(function($){
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
		iKEP.iFrameContentResize();
		
		iKEP.setContentImageRendering("div.blockTableRead_c");
		
		$("input.datepicker").datepicker({
		    showOn: "button",
		    buttonImage: "<c:url value='/base/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true
		});  

		$("#adminShareCollQnaItemButton").click(function() {  
			iKEP.showDialog({
				title : "<ikep4j:message pre="${preMessage}" key="shareSubTeam" />",
				width:800,
				height:400,
				url: "<c:url value='/collpack/kms/qna/shareQnaItemPop.do?itemId=${qnaItem.itemId}'/>",
				modal:true,
				callback : function() {
					location.replace("<c:url value='/collpack/kms/qna/listQnaItemView.do'/>");
				}
			});	
		});
		
		/* $("#adminDeleteBoardItemButton").click(function() {
			if(confirm("<ikep4j:message pre="${preMessage}" key="deleteItem" />")) {
				location.href="<c:url value='/collpack/kms/qna/adminDeleteQnaItem.do?itemId=${qnaItem.itemId}'/>"; 
			}  
			return false; 
			
		});  */
		
		
		
		var uploaderOptions = {
		   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
		   isUpdate : false 
		}; 
        
	    var fileController = new iKEP.FileController(null, "#fileDownload", uploaderOptions); 
	    
	});  
	
	deleteFnc = function(){
		if(confirm("<ikep4j:message pre="${preMessage}" key="deleteItem" />")) {
			location.href="<c:url value='/collpack/kms/qna/adminDeleteQnaItem.do?itemId=${qnaItem.itemId}'/>"; 
		}  
		return false; 
	};
	
	updateRecommendCountFnc = function(itemId) {
		<c:if test="${qnaItem.registerId eq user.userId}">
		if(confirm("게시물을 채택하시겠습니까?")) { 
			$.get("<c:url value='/collpack/kms/qna/updateRecommendCount.do?itemId='/>"+itemId)
			.success(function(data) { 
				if(data == -1) {
					alert("채택은 하나의 게시물만 가능합니다.");
				} else {
					$("#recommendCount_"+itemId).text(data);	
					alert("채택을 완료했습니다.");
				}
				
			})
			.error(function(event, request, settings) { alert("error"); });  
		} 
		</c:if>
 
	}; 
	
	//$("#createReplyBoardItemViewButton").click(function() {  alert();
    //var url = "<c:url value='/lightpack/board/boardItem/createReplyBoardItemView.do?itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&viewMode=${viewMode}'/>";  
    //iKEP.popupOpen(url, {width:800, height:600});
    //location.href="<c:url value='/collpack/kms/qna/createReplyQnaItemView.do?itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&viewMode=${viewMode}'/>";
	//}); 

})(jQuery); 

/* function reply(){
	location.href="<c:url value='/collpack/kms/qna/createReplyQnaItemView.do?itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&viewMode=${viewMode}'/>";
} */

function fileDown(url){
	window.open(url, 'filedown', 'width=800px, height=670px, scrollbars=yes');
}
//-->
</script>
<form id="qnaItemForm" name="qnaItemForm" action="">
<h1 class="none"></h1>

<!--pageTitle Start-->
<c:if test="${layoutType eq 'layoutNormal'&&!docPopup}">
<div id="pageTitle"> 
	<h2>무림지식인(Q&A)</h2> 
</div> 
</c:if>
<!--//pageTitle End--> 

<!--blockTableReadWrap Start-->
<div class="blockTableReadWrap">

		<!--blockListTable Start-->
		<div class="blockTableRead">
			<div class="blockTableRead_t">
				<spring:bind path="qnaItem.title">
				<p>
					${qnaItem.title}
				</p>
				</spring:bind> 
				<div class="summaryViewInfo"> 
					<span class="blockCommentInfo_name">
						<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}">
							<a href="#a" onclick="iKEP.showUserContextMenu(this, '${qnaItem.registerId}', 'bottom')">${qnaItem.registerName}&nbsp;${qnaItem.jobTitleName}</a>
						</c:when>
						<c:otherwise>
							<a href="#a" onclick="iKEP.showUserContextMenu(this, '${qnaItem.registerId}', 'bottom')">${qnaItem.registerEnglishName}&nbsp;${qnaItem.jobTitleEnglishName}</a>
						</c:otherwise>
						</c:choose>	
				    </span>
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
					<spring:bind path="qnaItem.hitCount">
						<span><ikep4j:message pre='${preDetail}' key='${status.expression}' /> <strong>${status.value}</strong></span>  
					</spring:bind>  
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
					<span>
						<ikep4j:message pre='${preDetail}' key='registDate' /> 
						<ikep4j:timezone pattern="yyyy.MM.dd" date="${qnaItem.registDate}"/>
					</span>			 
				</div>
				
			</div> 
			
			<c:if test="${qnaItem.attachFileCount > 0}"> 
				<%-- <c:if test="${tempEcmFileCount > 0}">
					<div style="text-align:right;display:none;" id="lfile">
						<c:forEach var="ecmFileData" items="${qnaItem.ecmFileDataList}" varStatus="ecmFileDataStatus">
						<ul><li>
							<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" />
							<a href="" onclick="javascript:fileDown('${ecmFileData.fileUrl1}');return false;"><font style="color:gray;">${ecmFileData.fileRealName}</font></a>
							</li></ul>
						</c:forEach>
					</div>
					<div style="text-align:right;display:none;" id="sfile">
					<c:forEach var="ecmFileData" items="${qnaItem.ecmFileDataList}" varStatus="ecmFileDataStatus">
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
			
			<div class="blockTableRead_c">
				<spring:htmlEscape defaultHtmlEscape="false"> 
					<spring:bind path="qnaItem.contents">
					<p>${status.value}</p>
					</spring:bind>
				</spring:htmlEscape> 
				
				<c:if test="${!empty qnaItem.tagList}">
				<div class="summaryViewTag">
					<img src="<c:url value='/base/images/theme/theme01/basic/ic_tag.gif'/>" alt="태그" /> 
					<c:forEach var="tags" items="${qnaItem.tagList}">
						${tags.tagName}
					</c:forEach>
				</div>
				</c:if>
			</div>  
			
		</div>
		
		<!--//blockListTable End-->	 
		<!--tableBottom Start-->
		<c:if test="${!docPopup}">
		<div class="tableBottom">										
			<!--blockButton Start-->
			<div class="blockButton"> 
				<ul>
					<c:if test="${qnaPermission>1 || qnaItem.registerId eq user.userId}">
						<c:if test="${isOrganization eq 1 && qnaItem.isOwner eq 1}">
							<li>
								<a class="button" id="adminShareCollQnaItemButton"  href="#a"><span><ikep4j:message pre='${preButton}' key='shareColl'/></span></a>
							</li>
						</c:if>
							<li>
								<a class="button" href="<c:url value='/collpack/kms/qna/updateQnaItemView.do?itemId=${qnaItem.itemId}'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a>
							</li>

						<li>
							<a class="button" id="adminDeleteBoardItemButton" onclick="deleteFnc();"  href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
						</li>
					</c:if>
					<c:if test="${qnaItem.indentation == '0'}">
					<li><a id="createReplyBoardItemViewButton" class="button" href="<c:url value='/collpack/kms/qna/createReplyQnaItemView.do?itemId=${qnaItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&viewMode=${viewMode}'/>"><span><ikep4j:message pre='${preButton}' key='reply'/></span></a></li>
					</c:if>
						<li>
							<a class="button" href="<c:url value='/collpack/kms/qna/listQnaItemView.do'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a>
						</li>
				</ul>  
			</div>
			<!--//blockButton End-->
		</div>
		</c:if>
		<!--//tableBottom End-->
		
		
</div>
<!--//blockTableReadWrap End-->	

<c:choose>
		    <c:when test="${searchResult.emptyRecord}">
		    </c:when>
		    <c:otherwise>
				<c:forEach var="qnaItems" items="${searchResult.entity}">
				<div class="blockTableReadWrap">
					<div class="blockTableRead">
					<div class="blockTableRead_t">
						<p>
							${qnaItems.title}
						</p>
						<div class="summaryViewInfo"> 
							<span class="blockCommentInfo_name">
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									<a href="#a" onclick="iKEP.showUserContextMenu(this, '${qnaItems.registerId}', 'bottom')">${qnaItems.registerName}&nbsp;${qnaItems.jobTitleName}</a>
								</c:when>
								<c:otherwise>
									<a href="#a" onclick="iKEP.showUserContextMenu(this, '${qnaItems.registerId}', 'bottom')">${qnaItems.registerEnglishName}&nbsp;${qnaItems.jobTitleEnglishName}</a>
								</c:otherwise>
								</c:choose>	
						    </span>
							<span>
								<ikep4j:message pre='${preDetail}' key='registDate' /> 
								<ikep4j:timezone pattern="yyyy.MM.dd" date="${qnaItems.registDate}"/>
							</span>			 
						</div>
						<c:if test="${qnaItem.registerId eq user.userId}">
						<div class="recommend">
						<a id="updateRecommendCountButton" onclick="updateRecommendCountFnc(${qnaItems.itemId});" class="button_rec_num" href="#a"> 
							<span id="recommendCount_${qnaItems.itemId}" class="num">${qnaItems.recommendCount}</span>
						<span class="doc"><img src="<c:url value='/base/images/icon/ic_recommend.gif'/>" />채택</span></a>
						</div>
						</c:if>
					</div> 
					
					<c:if test="${qnaItems.attachFileCount > 0}"> 
						<DIV id=fileDownload><DIV class=filedown_ic>첨부파일 <SPAN class=colorPoint>${qnaItems.attachFileCount}</SPAN></DIV>
						<DIV class=filedown>
						<UL>
						
						<c:forEach var="files" items="${qnaItems.fileDataList}" varStatus="status">
						<c:if test="${files.editorAttach == '0'}"> 
							<LI><IMG onerror="iKEP.FileController.setEtcFileIcon(this)" alt="" src="<c:url value='/base/images/icon/ic_file_${files.fileExtension}.gif' />">&nbsp;
							<A href="<c:url value='/support/fileupload/downloadFile.do?fileId=${files.fileId}' />">${files.fileRealName}</A></LI>
						</c:if>
						</c:forEach>
						</UL></DIV></DIV>
						
						<%-- <c:if test="${tempEcmFileCount > 0}">
							<div style="text-align:right;display:none;" id="lfile">
								<c:forEach var="ecmFileData" items="${qnaItem.ecmFileDataList}" varStatus="ecmFileDataStatus">
								<ul><li>
									<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" />
									<a href="" onclick="javascript:fileDown('${ecmFileData.fileUrl1}');return false;"><font style="color:gray;">${ecmFileData.fileRealName}</font></a>
									</li></ul>
								</c:forEach>
							</div>
							<div style="text-align:right;display:none;" id="sfile">
							<c:forEach var="ecmFileData" items="${qnaItem.ecmFileDataList}" varStatus="ecmFileDataStatus">
							<ul><li>
								<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" />
								<a href="" onclick="javascript:fileDown('${ecmFileData.fileUrl2}');return false;"><font style="color:gray;">${ecmFileData.fileRealName}</font></a>
								</li></ul>
							</c:forEach>
							</div>
						</c:if>
						<c:if test="${tempEcmFileCount < 1}"> --%>
							<!-- <div id="fileDownload"></div> -->
						<%-- </c:if> --%>
					</c:if>
					
					<div class="blockTableRead_c">
						<spring:htmlEscape defaultHtmlEscape="false"> 
							<p>${qnaItems.contents}</p>
						</spring:htmlEscape> 
					</div>  
					
				</div>
				</div>
				</c:forEach>
		</c:otherwise>
		</c:choose>
</form> 