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
<c:forEach var="tempEcmFileData" items="${announceItem.ecmFileDataList}" varStatus="ecmFileDataStatus">
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

		$("#adminShareCollAnnounceItemButton").click(function() {  
			iKEP.showDialog({
				title : "<ikep4j:message pre="${preMessage}" key="shareSubTeam" />",
				width:800,
				height:400,
				url: "<c:url value='/collpack/kms/announce/shareAnnounceItemPop.do?itemId=${announceItem.itemId}'/>",
				modal:true,
				callback : function() {
					location.replace("<c:url value='/collpack/kms/announce/listAnnounceItemView.do'/>");
				}
			});	
		});
		
		$("#adminDeleteBoardItemButton").click(function() {
			if(confirm("<ikep4j:message pre="${preMessage}" key="deleteItem" />")) {
				location.href="<c:url value='/collpack/kms/announce/adminDeleteAnnounceItem.do?itemId=${announceItem.itemId}'/>"; 
			}  
			return false; 
			
		}); 
		
		var uploaderOptions = {
		   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
		   isUpdate : false 
		}; 
        
	    var fileController = new iKEP.FileController(null, "#fileDownload", uploaderOptions); 
	    
	});  

})(jQuery); 

function fileDown(url){
	window.open(url, 'filedown', 'width=800px, height=670px, scrollbars=yes');
}
//-->
</script>
<form id="announceItemForm" name="announceItemForm" action="">
<h1 class="none"></h1>

<!--pageTitle Start-->
<c:if test="${layoutType eq 'layoutNormal'&&!docPopup}">
<div id="pageTitle"> 
	<h2>지식공지</h2> 
</div> 
</c:if>
<!--//pageTitle End--> 

<!--blockTableReadWrap Start-->
<div class="blockTableReadWrap">

		<!--blockListTable Start-->
		<div class="blockTableRead">
			<div class="blockTableRead_t">
				<spring:bind path="announceItem.title">
				<p>
					${announceItem.title}
				</p>
				</spring:bind> 
				<div class="summaryViewInfo"> 
					<span class="blockCommentInfo_name">
						<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}">
							<a href="#a" onclick="iKEP.showUserContextMenu(this, '${announceItem.registerId}', 'bottom')">${announceItem.registerName}&nbsp;${announceItem.jobTitleName}</a>
						</c:when>
						<c:otherwise>
							<a href="#a" onclick="iKEP.showUserContextMenu(this, '${announceItem.registerId}', 'bottom')">${announceItem.registerEnglishName}&nbsp;${announceItem.jobTitleEnglishName}</a>
						</c:otherwise>
						</c:choose>	
				    </span>
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
					<spring:bind path="announceItem.hitCount">
						<span><ikep4j:message pre='${preDetail}' key='${status.expression}' /> <strong>${status.value}</strong></span>  
					</spring:bind>  
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
					<span>
						<ikep4j:message pre='${preDetail}' key='registDate' /> 
						<ikep4j:timezone pattern="yyyy.MM.dd" date="${announceItem.registDate}"/>
					</span>			 
				</div>
				
			</div> 
			
			<c:if test="${announceItem.attachFileCount > 0}"> 
				<%-- <c:if test="${tempEcmFileCount > 0}">
					<div style="text-align:right;display:none;" id="lfile">
						<c:forEach var="ecmFileData" items="${announceItem.ecmFileDataList}" varStatus="ecmFileDataStatus">
						<ul><li>
							<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" />
							<a href="" onclick="javascript:fileDown('${ecmFileData.fileUrl1}');return false;"><font style="color:gray;">${ecmFileData.fileRealName}</font></a>
							</li></ul>
						</c:forEach>
					</div>
					<div style="text-align:right;display:none;" id="sfile">
					<c:forEach var="ecmFileData" items="${announceItem.ecmFileDataList}" varStatus="ecmFileDataStatus">
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
					<spring:bind path="announceItem.contents">
					<p>${status.value}</p>
					</spring:bind>
				</spring:htmlEscape> 
				
				<c:if test="${!empty announceItem.tagList}">
				<div class="summaryViewTag">
					<img src="<c:url value='/base/images/theme/theme01/basic/ic_tag.gif'/>" alt="태그" /> 
					<c:forEach var="tags" items="${announceItem.tagList}">
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
					<c:if test="${announcePermission>1 || announceItem.registerId eq user.userId}">
						<c:if test="${isOrganization eq 1 && announceItem.isOwner eq 1}">
							<li>
								<a class="button" id="adminShareCollAnnounceItemButton"  href="#a"><span><ikep4j:message pre='${preButton}' key='shareColl'/></span></a>
							</li>
						</c:if>
							<li>
								<a class="button" href="<c:url value='/collpack/kms/announce/updateAnnounceItemView.do?itemId=${announceItem.itemId}'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a>
							</li>

						<li>
							<a class="button" id="adminDeleteBoardItemButton"  href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
						</li>
					</c:if>
						<li>
							<a class="button" href="<c:url value='/collpack/kms/announce/listAnnounceItemView.do'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a>
						</li>
				</ul>  
			</div>
			<!--//blockButton End-->
		</div>
		</c:if>
		<!--//tableBottom End-->
</div>
<!--//blockTableReadWrap End-->	
</form> 