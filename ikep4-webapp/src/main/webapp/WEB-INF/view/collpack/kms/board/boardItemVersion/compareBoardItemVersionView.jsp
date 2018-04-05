<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.board.boardItem.listBoardItemVersionView.header" /> 
<c:set var="preList"    value="message.collpack.collaboration.board.boardItem.listBoardItemVersionView.list" />
<c:set var="preCode"    value="message.collpack.collaboration.common.code" /> 
<c:set var="preButton"  value="message.collpack.collaboration.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.boardItem" />
<c:set var="preSearch"  value="message.collpack.collaboration.common.searchCondition" />  
<%-- 메시지 관련 Prefix 선언 End --%>
 
<style>
.del {color:red; text-decoration:line-through }
.ins {color:blue;text-decoration:underline}
</style>
<script type="text/javascript" src="<c:url value="/base/js/units/collpack/collaboration/board/diff.js"/>"></script>
<script type="text/javascript">
<!--  
(function($){	 
	$(document).ready(function() { 
		$("input.datepicker").datepicker({
		    showOn: "button",
		    buttonImage: "<c:url value='/base/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true
		});  
		$("#itemViewButton").click(function() { 
		    location.href="<c:url value='/collpack/collaboration/board/boardItem/readBoardItemView.do?workspaceId=${searchCondition.workspaceId}&itemId=${searchCondition.itemId}'/>";  
		});	 

		var itemContent = $("#itemContent").val();
		var compareItemContent = $("#compareItemContent").val();

		var reContent = diffString(compareItemContent , itemContent );
		
		$('#main_text').html(reContent);
		
	    var uploaderOptions = {
		   <c:if test="${not empty boardItemVersion.fileDataListJson}">files : ${boardItemVersion.fileDataListJson},</c:if>  
		   isUpdate : false 
		}; 
        
	    var fileController = new iKEP.FileController(null, "#fileDownload", uploaderOptions);
	    
		
	    var uploaderOptions1 = {
		   <c:if test="${not empty boardItemVersion.compareBoardItem.fileDataListJson}">files : ${boardItemVersion.compareBoardItem.fileDataListJson},</c:if>  
		   isUpdate : false 
		}; 
        
	    var fileController1 = new iKEP.FileController(null, "#fileDownload1", uploaderOptions1);	    		
		iKEP.iFrameContentResize();
	});  


	
})(jQuery); 
//-->
</script>
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
<form id="mainForm" method="get" action="<c:url value='/collpack/collaboration/board/boardItemVersion/listBoardItemVersionView.do'/>">  
	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<spring:bind path="searchCondition.workspaceId">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
	</spring:bind>		
	<spring:bind path="searchCondition.boardId">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>  
	<spring:bind path="searchCondition.itemId">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	<input type="hidden" id="itemContent" name=""itemContent"" value = '<c:out value="${boardItemVersion.contents}" escapeXml="false" /> '/>
	<input type="hidden" id="compareItemContent" name="compareItemContent" value = '<c:out value="${boardItemVersion.compareBoardItem.contents}" escapeXml="false"/>'/>
	<input type="hidden" id="searchConditionString" name="searchConditionString" value = '<c:out value="${searchConditionString}" escapeXml="false"/>'/>
	<input type="hidden" id="popupYn" name="popupYn" value = '<c:out value="${popupYn}" escapeXml="false"/>'/>
			
<!--pageTitle Start-->
<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
	<%--div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.3depth" /></li>
		</ul>
	</div--%>
</div> 
<!--//pageTitle End--> 

<!--blockListTable Start-->
<div class="blockTableRead">
	<div class="blockTableRead_t">
		
		<spring:bind path="boardItemVersion.title">
		<p> 
		${boardItemVersion.title}
		</p> 
		</spring:bind>
				
		<div class="summaryViewInfo"> 
			<span class="summaryViewInfo_name"> 
				<a href="#a">
					<c:set var="user"   value="${sessionScope['ikep.user']}" />
					<c:set var="portal" value="${sessionScope['ikep.portal']}" />
					<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
							<a href="#a" title="${boardItemVersion.registerName} ${boardItemVersion.jobTitleName}" onclick="iKEP.showUserContextMenu(this, '${boardItemVersion.registerId}', 'bottom')">${boardItemVersion.registerName} ${boardItemVersion.jobTitleName}</a>
						</c:when>
						<c:otherwise> 
							<a href="#a" title="${boardItemVersion.registerEnglishName} ${boardItemVersion.jobTitleEnglishName}" onclick="iKEP.showUserContextMenu(this, '${boardItemVersion.registerId}', 'bottom')">${boardItemVersion.registerEnglishName} ${boardItemVersion.jobTitleEnglishName}</a>
						</c:otherwise>
					</c:choose>	 
				</a>
			</span> 
			<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" /> 
			<span class="blockCommentInfo_name">
				<ikep4j:message pre='${preList}' key='updateDate' />
					<span><ikep4j:timezone pattern="yyyy.MM.dd" date="${boardItemVersion.registDate}"/></span> 
		    </span>
			<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
			<span>
			<ikep4j:message pre='${preList}' key='openDate' /> 
			<ikep4j:timezone pattern="yyyy.MM.dd" date="${boardItemVersion.startDate}"/> ~ 
			<ikep4j:timezone pattern="yyyy.MM.dd" date="${boardItemVersion.endDate}"/>
			</span>			 
		</div>
		
		
		<c:if test="${boardItemVersion.attachFileCount > 0}"> 
		<div class="filedown"><!--첨부파일 없을시 div 통째로 삭제할 것-->
			<%--ul>
				<c:forEach var="fileData" items="${boardItemVersion.fileDataList}">
				<li><input name="" class="checkbox" title="checkbox" type="checkbox" value="" /> <a href="#a">${fileData.fileRealName}</a></li>
				</c:forEach>  
			</ul--%>
			<c:if test="${boardItemVersion.attachFileCount > 0}"> 
			Version : ${boardItemVersion.version} <div id="fileDownload"> 
			</c:if>
			<BR />
			<c:if test="${boardItemVersion.compareBoardItem.attachFileCount > 0}"> 
			Version : ${boardItemVersion.compareBoardItem.version} <div id="fileDownload1"> 
			</c:if>						
			<!--div class="filedown_btn"><a class="button" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_download.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='download' /></span></a></div-->
		</div>
		</c:if>
	</div>
	
	<div class="blockTableRead_c">
		<div id="main_text"></div>
	</div>  
</div>
</form>
<!--//blockListTable End-->	 

									
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>   
		<li><a class="button" href="<c:url value='/collpack/collaboration/board/boardItemVersion/listBoardItemVersionView.do?workspaceId=${searchCondition.workspaceId}&boardId=${searchCondition.boardId}&itemId=${boardItemVersion.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>" title="<ikep4j:message pre='${preButton}' key='versionList'/>"><span><ikep4j:message pre='${preButton}' key='versionList'/></span></a></li>
		<li><a class="button" href="<c:url value='/collpack/collaboration/board/boardItem/readBoardItemView.do?workspaceId=${searchCondition.workspaceId}&boardId=${searchCondition.boardId}&itemId=${boardItemVersion.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>" title="<ikep4j:message pre='${preButton}' key='itemView'/>"><span><ikep4j:message pre='${preButton}' key='itemView'/></span></a></li>
		<li><a class="button" href="<c:url value='/collpack/collaboration/board/boardItem/listBoardItemView.do?workspaceId=${searchCondition.workspaceId}&boardId=${searchCondition.boardId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>" title="<ikep4j:message pre='${preButton}' key='list'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
	 </ul>  
</div>
<!--//blockButton End-->
