<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/fileUploadControll.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.collpack.workmanual.readManualView</c:set>
<c:set var="messagePrefix">ui.collpack.workmanual.message</c:set>
<c:set var="buttonPrefix">ui.collpack.workmanual.button</c:set>
<c:set var="preSearch">ui.collpack.workmanual.common.searchCondition</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
//<![CDATA[
(function($) { 
	$jq(document).ready(function(){
		//파일 다운로드
		var uploaderOptions = {
		   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
		   isUpdate : false 
		}; 
	    var fileController = new iKEP.FileController(null, "#fileDownload", uploaderOptions);
	});
	
	loadLinereplyList = function() { 
		$("#linereplySearchForm").submit();
		//location.href = '<c:url value="/collpack/workmanual/addLinereplyPage.do?manualId=${manual.manualId}"/>';
		//$("#blockComment").load('<c:url value="/collpack/workmanual/addLinereplyPage.do?manualId=${manual.manualId}"/>', $("#linereplySearchForm").serialize()).error(function(event, request, settings) { alert("error"); });   
	};
})(jQuery);
//]]>
</script>

				<h1 class="none"></h1>
				
				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2>${manualCategory.categoryName}</h2>
				</div>
				<!--//pageTitle End-->

				<!--blockTableReadWrap Start-->
	            <div class="blockTableReadWrap">
					<!--blockListTable Start-->
					<div class="blockTableRead">
						<div class="blockTableRead_t">
							<p><c:if test="${manual.manualType == 'A'}">[<ikep4j:message pre='${messagePrefix}' key='manual.submit'/>]</c:if>
							   <c:if test="${manual.manualType == 'B'}">[<ikep4j:message pre='${messagePrefix}' key='manual.submit.revision'/>]</c:if>
							   <c:if test="${manual.manualType == 'C'}">[<ikep4j:message pre='${messagePrefix}' key='manual.disuse'/>]</c:if>&nbsp;
							       ${manual.title}(<ikep4j:message pre="${prefix}" key="body.version"/>:${manual.version})</p>
							<div class="summaryViewInfo">
								<span class="summaryViewInfo_name">${manual.registerName}&nbsp;${manual.jobTitleName}</span>
								<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
								<span><ikep4j:timezone date="${manual.updateDate}" pattern="yyyy.MM.dd HH:mm"/></span>
								<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
								<span><ikep4j:message pre="${prefix}" key="body.hit"/><strong>${manual.hitCount}</strong></span>
							</div>
							<c:if test="${fn:length(manualVersion.fileDataList) > 0}"><div id="fileDownload"></div></c:if>
						</div>
						
						<div class="blockTableRead_c">
							<p>${manual.contents}</p>
							<div class="tableTag">
								<img src="<c:url value='/base/images/theme/theme01/basic/ic_tag.gif'/>" alt="tag"/>
								<c:forEach var="tag" items="${manual.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if>${tag.tagName}</c:forEach>
							</div>					
						</div>
					</div>
					<!--//blockListTable End-->
				</div>
	            <!--//blockTableReadWrap End-->
											

<!--blockComment Start-->  
<div id="blockComment" class="blockComment"> 
	<div class="blockComment_t"> 
		<ikep4j:message pre="${prefix}" key="body.reply"/><span class="comment_num">(${searchCondition.recordCount})</span>  
	</div> 

	<c:forEach var="linereply" items="${searchResult.entity}" varStatus="varStatus"> 
		<c:choose>
			<c:when test="${not varStatus.first}"> 
				<c:forEach begin="${linereply.indentation}" end="${preIndentation}"></div></c:forEach>  
			</c:when> 
		</c:choose>	 
		<c:choose>
	 		<c:when test="${linereply.indentation == 0}">
				<div class="blockComment_c"> 
			</c:when>
			<c:otherwise>
				<div class="blockComment_re ">
				<div class="reply_ar"><img src="<c:url value="/base/images/icon/ic_reply_ar.gif"/>" alt=""/></div>
			</c:otherwise>
		</c:choose>	  
		    <div class="blockCommentPhoto"><img src="<c:url value='${linereply.profilePicturePath}'/>" alt="image" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></div> 
		    <div class="blockCommentInfo">
		    	<span class="blockCommentInfo_name">${linereply.registerName}&nbsp;${linereply.jobTitleName}</span>
		    	<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />						
		    	<span>${linereply.teamName}</span>
		    	<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
		    	<span><ikep4j:timezone date="${linereply.registDate}" pattern="yyyy.MM.dd HH:mm"/></span>
		    </div> 
		    
			<c:choose>
		 		<c:when test="${linereply.isDelete == 1}">
		 			<p><span class="deletedItem"><ikep4j:message pre='${prefix}' key='body.deleteContents'/></span></p> 
				</c:when>
				<c:otherwise> 
					<p name="linereplyContents">${linereply.linereplyContents}</p> 
					<!--blockComment_rewrite Start--> 
					<div id="${linereply.linereplyId}UpdateForm"  name="updateForm" class="blockComment_rewrite modify"> 
						<form name="updateForm"> 
							<input type="hidden" name="linereplyId" value="${linereply.linereplyId}"/> 
						    <table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>"> 
								<caption></caption> 
								<tr> 
									<td> 
									</td> 
									<td width="95" class="textRight"> 
										<ul> 
										</ul> 
									</td>  
								</tr>  
							</table> 
						</form>
					</div> 
					<!--//blockComment_rewrite End--> 
				</c:otherwise>
			</c:choose> 
		</form>	 
		<c:choose>
	 		<c:when test="${varStatus.last}">
				<c:forEach begin="0" end="${linereply.indentation}">
					</div>	
				</c:forEach>
			</c:when>  
			<c:when test="${not(varStatus.first or varStatus.last)}"> 
				<c:set var="preIndentation" value="${linereply.indentation}"/>
			</c:when> 
		</c:choose>	  		
	</c:forEach> 
	
	<!--Page Numbur Start--> 
	<form id="linereplySearchForm" action="<c:url value='/collpack/workmanual/readManualPopupView.do'/>" method="post"> 
		<input type="hidden" name="manualId" value="${manualVersion.manualId}"/>
		<spring:bind path="searchCondition.pageIndex">
			<ikep4j:pagination searchFormId="linereplySearchForm" ajaxEventFunctionName="loadLinereplyList" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind> 
		<!--//Page Numbur End-->  
	</form>
</div> 
<!--//blockComment End-->
				
				<c:if test="${manualVersion.approvalStatus != 'A'}">
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre="${prefix}" key="table.title"/></h3>
				</div>
				<!--//subTitle_2 End-->
				
				<!--blockDetail Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
						<caption></caption>
						<thead>
							<tr>
								<th class="textCenter" scope="col" width="5%"><ikep4j:message pre="${prefix}" key="table.column1"/></th>
								<th class="textCenter" scope="col" width="10%"><ikep4j:message pre="${prefix}" key="table.column2"/></th>
								<th class="textCenter" scope="col" width="10%"><ikep4j:message pre="${prefix}" key="table.column3"/></th>
								<th class="textCenter" scope="col" width="65%"><ikep4j:message pre="${prefix}" key="table.column5"/></th>
								<th class="textCenter" scope="col" width="10%"><ikep4j:message pre="${prefix}" key="table.column4"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${approvalLineList}"> 
							<tr>
								<td class="textCenter">${item.approvalLine}</td>
								<td class="textLeft">${item.approvalUserName}&nbsp;${item.approvalUserJobTitleName}</td>
								<td class="textCenter"><c:if test="${item.approvalResult == 'A'}"><ikep4j:message pre='${messagePrefix}' key='approvalResult.prepare'/></c:if>
														<c:if test="${item.approvalResult == 'B'}"><ikep4j:message pre='${messagePrefix}' key='approvalResult.holding'/></c:if>
														<c:if test="${item.approvalResult == 'C'}"><ikep4j:message pre='${messagePrefix}' key='approvalResult.accept'/></c:if>
														<c:if test="${item.approvalResult == 'D'}"><ikep4j:message pre='${messagePrefix}' key='approvalResult.reject'/></c:if>
								</td>
								<td class="textLeft">${item.approvalComment}</td>
								<td class="textCenter"><ikep4j:timezone date="${item.approvalDate}" pattern="yyyy.MM.dd HH:mm"/></td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
				</c:if>							