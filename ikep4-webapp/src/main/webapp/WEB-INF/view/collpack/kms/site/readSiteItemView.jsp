<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/fileUploadControll.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.kms.site.boardItem.listBoardView.header" />
<c:set var="preDetail"  value="message.collpack.kms.site.boardItem.createBoardView.detail" /> 
<c:set var="preList"    value="message.collpack.kms.site.boardItem.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.kms.site.boardItem.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.kms.site.boardItem.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.kms.site.boardItem.message.script" />
<%-- 메시지 관련 Prefix 선언 End --%> 
 
<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
 
<script type="text/javascript">
<!--  
(function($){
	$(document).ready(function() { 
		iKEP.iFrameContentResize();
		
		iKEP.setContentImageRendering("div.blockTableRead_c");
		
		$("input.datepicker").datepicker({
		    showOn: "button",
		    buttonImage: "<c:url value='/base/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true
		});  

		$("#adminShareCollSiteItemButton").click(function() {  
			iKEP.showDialog({
				title : "<ikep4j:message pre="${preMessage}" key="shareSubTeam" />",
				width:800,
				height:400,
				url: "<c:url value='/collpack/kms/site/shareSiteItemPop.do?itemId=${siteItem.itemId}'/>",
				modal:true,
				callback : function() {
					location.replace("<c:url value='/collpack/kms/site/listSiteItemView.do'/>");
				}
			});	
		});
		
		$("#adminDeleteBoardItemButton").click(function() {
			if(confirm("<ikep4j:message pre="${preMessage}" key="deleteItem" />")) {
				location.href="<c:url value='/collpack/kms/site/adminDeleteSiteItem.do?itemId=${siteItem.itemId}'/>"; 
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
//-->
</script>
<form id="siteItemForm" name="siteItemForm" action="">
<h1 class="none"></h1>

<!--pageTitle Start-->
<c:if test="${layoutType eq 'layoutNormal'&&!docPopup}">
<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="title" /></h2> 
</div> 
</c:if>
<!--//pageTitle End--> 

<!--blockTableReadWrap Start-->
<div class="blockTableReadWrap">

		<!--blockListTable Start-->
		<div class="blockTableRead">
			<div class="blockTableRead_t">
				<spring:bind path="siteItem.title">
				<p>
					${siteItem.title}
				</p>
				</spring:bind> 
				<div class="summaryViewInfo"> 
					<span class="blockCommentInfo_name">
						<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}">
							<a href="#a" onclick="iKEP.showUserContextMenu(this, '${siteItem.registerId}', 'bottom')">${siteItem.registerName}&nbsp;${siteItem.jobTitleName}</a>
						</c:when>
						<c:otherwise>
							<a href="#a" onclick="iKEP.showUserContextMenu(this, '${siteItem.registerId}', 'bottom')">${siteItem.registerEnglishName}&nbsp;${siteItem.jobTitleEnglishName}</a>
						</c:otherwise>
						</c:choose>	
				    </span>
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
					<spring:bind path="siteItem.hitCount">
						<span><ikep4j:message pre='${preDetail}' key='${status.expression}' /> <strong>${status.value}</strong></span>  
					</spring:bind>  
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
					<span>
						<ikep4j:message pre='${preDetail}' key='registDate' /> 
						<ikep4j:timezone pattern="yyyy.MM.dd" date="${siteItem.registDate}"/>
					</span>			 
				</div>
				
			</div> 
			
			<c:if test="${siteItem.attachFileCount > 0}"> 
				<div id="fileDownload"></div>
			</c:if>
			
			<div class="blockTableRead_c">
				<spring:htmlEscape defaultHtmlEscape="false"> 
					<spring:bind path="siteItem.contents">
					<p>${status.value}</p>
					</spring:bind>
				</spring:htmlEscape> 
				
				<c:if test="${!empty siteItem.tagList}">
				<div class="summaryViewTag">
					<img src="<c:url value='/base/images/theme/theme01/basic/ic_tag.gif'/>" alt="태그" /> 
					<c:forEach var="tags" items="${siteItem.tagList}">
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
					<c:if test="${sitePermission>2 || siteItem.registerId eq user.userId}">
						<c:if test="${isOrganization eq 1 && siteItem.isOwner eq 1}">
							<li>
								<a class="button" id="adminShareCollSiteItemButton"  href="#a"><span><ikep4j:message pre='${preButton}' key='shareColl'/></span></a>
							</li>
						</c:if>
							<li>
								<a class="button" href="<c:url value='/collpack/kms/site/updateSiteItemView.do?itemId=${siteItem.itemId}'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a>
							</li>

						<li>
							<a class="button" id="adminDeleteBoardItemButton"  href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
						</li>
						<li>
							<a class="button" href="<c:url value='/collpack/kms/site/listSiteItemView.do'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a>
						</li>
					</c:if>
				</ul>  
			</div>
			<!--//blockButton End-->
		</div>
		</c:if>
		<!--//tableBottom End-->
</div>
<!--//blockTableReadWrap End-->	
</form> 