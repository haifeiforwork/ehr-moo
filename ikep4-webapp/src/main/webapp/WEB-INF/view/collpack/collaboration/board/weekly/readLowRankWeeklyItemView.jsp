<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/fileUploadControll.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preHeader"  value="message.collpack.collaboration.board.weekly.listBoardView.header" />
<c:set var="preDetail"  value="message.collpack.collaboration.board.weekly.createBoardView.detail" /> 
<c:set var="preList"    value="message.collpack.collaboration.board.weekly.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.collaboration.board.weekly.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.collaboration.board.weekly.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.collaboration.board.weekly.listBoardView.alert" />  
 <%-- 메시지 관련 Prefix 선언 End --%>
<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript" src="<c:url value='/base/js/jquery/jquery-1.6.2.min.js'/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-ui-1.8.13.min.without.datepicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.ui.datepicker.customize.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/common.pack.js'/>"></script>


<script type="text/javascript">
<!--   

(function($){	

	$(document).ready(function() { 
		var uploaderOptions = {
		   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
		   isUpdate : false 
		}; 
        
	    var fileController = new iKEP.FileController(null, "#fileDownload", uploaderOptions); 
	    
		/***
		var files = [];
		<c:forEach var="fileData" items="${weeklyItem.fileDataList}">
			files.push({fileid:"${fileData.fileId}", name:"${fileData.fileRealName}", size:${fileData.fileSize}, type:""});
		</c:forEach>
		new iKEP.FileController("#weeklyItemForm","#fileDownArea", {
			files : files,
			isUpdate : false
		});
		***/
		iKEP.iFrameContentResize();
	}); 

	
})(jQuery); 
//-->
</script>

<!--pageTitle Start-->
		<div id="pageTitle">
			<h2><ikep4j:message pre="${preHeader}" key="readPageTitle"/></h2>
		</div>
		<!--//pageTitle End-->
<form id="weeklyItemForm">

<!--blockTableReadWrap Start-->
		<div class="blockTableReadWrap">
		<!--blockListTable Start-->
		<div class="blockTableRead">
			<div class="blockTableRead_t">
				<p>
					<c:if test="${weeklyItem.isSummary eq 1}">
						<span class="cate_block_5">
							<span class="cate_tit_5"><ikep4j:message pre="${preList}" key="summary"/></span>
						</span>&nbsp;
					</c:if>
					${weeklyItem.title}
				</p>
		
				<div class="summaryViewInfo">
					<span class="summaryViewInfo_name"><a href="#a">
					<c:choose>
					<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						${weeklyItem.registerName}
					</c:when>
					<c:otherwise>
						${weeklyItem.registerEnglishName}
					</c:otherwise>
					</c:choose>						
					</a></span>
					<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
					<span>
						<c:if test="${!empty weeklyItem.registDate}">
							<ikep4j:timezone pattern="yyyy.MM.dd" date="${weeklyItem.registDate}"/>
						</c:if>
					</span>
					<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
					<span><ikep4j:message pre="${preList}" key="hitCount"/>  <strong>${weeklyItem.hitCount}</strong></span>
					<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
					<span><ikep4j:message pre="${preList}" key="period"/>  ${weeklyItem.weeklyTerm}</span>
				</div>
				<c:if test="${weeklyItem.attachFileCount > 0}"> 
					<div id= "fileDownload"></div>
				</c:if>
			</div>
			
			<div class="blockTableRead_c">
				<spring:htmlEscape defaultHtmlEscape="false"> 
					${weeklyItem.contents}
				</spring:htmlEscape>
			</div>
		</div>
		<!--//blockListTable End-->	
</div>
</form>								
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a class="button" href="<c:url value='/collpack/collaboration/board/weekly/listLowRankWeeklyItemView.do?workspaceId=${workspaceId}&weeklyTerm=${weeklyItem.weeklyTerm}'/>"><span><ikep4j:message pre="${preButton}" key="list"/></span></a></li>							
			</ul>
		</div>
		<!--//blockButton End-->