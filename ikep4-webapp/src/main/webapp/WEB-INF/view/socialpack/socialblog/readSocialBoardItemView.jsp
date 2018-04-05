<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preMain" value="ui.socialpack.socialblog.common.webstandard" />
<c:set var="preDetail" value="ui.socialpack.socialblog.socialBoardItem.readBoardView.detailBoardItem" />
<c:set var="preCode"    value="ui.socialpack.socialblog.common.code" />
<c:set var="preButton"  value="ui.socialpack.socialblog.common.button" /> 
<c:set var="preMessage" value="message.socialpack.socialblog.common.socialBoardItem" />
<c:set var="preSearch"  value="ui.socialpack.socialblog.common.searchCondition" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" /> 
<%@ include file="/base/common/fileUploadControll.jsp"%>

<script type="text/javascript" src="<c:url value="/base/js/units/socialpack/socialblog.js"/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/jquery/splitter.js'/>"></script>
<script type="text/javascript">
<!--   
(function($){	
	
	loadSocialBoardLinereplyList = function(el, itemId) {

		var $linereplyForm = $("#socialBoardLinereplySearchForm");
		
		$(el).load('<c:url value="/socialpack/socialblog/listSocialBoardLinereplyView.do"/>?itemId='+ itemId
			, $linereplyForm.serialize())
			.error(function(event, request, settings) {  alert("error"); });
		$(el).removeAttr( "onclick" );
	};
	
	loadSocialBoardLinereplyListPage = function(formId) { //  	
		$("#" + formId).parents(".blockComment").load('<c:url value="/socialpack/socialblog/listSocialBoardLinereplyView.do"/>', $("#" + formId).serialize())
		.error(function(event, request, settings) {  alert("error"); });
	};
	
	updateRecommendCount = function(itemId) {
		
		if(confirm("<ikep4j:message pre="${preMessage}" key="recommend" />")) {
			$.get("<c:url value='/socialpack/socialblog/updateRecommendCount.do'/>", {"blogOwnerId":"${socialBlog.ownerId}","itemId":itemId})
			.success(function(data) {
				if ( data == 'SUCCESSRECMD'){
					var currentRecommentCount = Number($("#recommendCount" +itemId+ " strong").text());
					$("#recommendCount" +itemId+ " strong").text(currentRecommentCount + 1);
				}else if ( data == 'ALREADYRECMD'){
					alert("<ikep4j:message pre="${preMessage}" key="recommend.already" />");
				}else if ( data == 'MYSELFRECMD'){
					alert("<ikep4j:message pre="${preMessage}" key="recommend.myself" />");
				}
			})
			.error(function(event, request, settings) { alert("error"); });  
		} 

	};
	
	
	$(document).ready(function() {    
		$jq(".blockComment").trigger("click");
	}); 
	
})(jQuery);
//-->
</script>

						<h2 class="none"><ikep4j:message pre='${preMain}' key='socialBlog.content' /></h2>
							
						<!--corner_RoundBox07 Start-->
						<div style="padding: 5px 15px;" class="corner_RoundBox07">
						
							<c:choose>
							<c:when test="${searchResult.emptyRecord}">
								<div class="blockTableRead_t_info_2">
									<p align="center"> 
									<ikep4j:message pre='${preSearch}' key='${status.expression}emptyRecord'/>
									</p>
								</div>
							</c:when>
							<c:otherwise>
														
							<!--blockListTable Start-->
							<c:forEach var="socialBoardItem" items="${searchResult.entity}" varStatus="status">
							
<script type="text/javascript">
(function($){
	    new iKEP.FileController(null, "#fileDownload_${socialBoardItem.itemId}", { <c:if test="${not empty socialBoardItem.fileDataListJson}"> files : ${socialBoardItem.fileDataListJson},</c:if>isUpdate : false});
		// Favorite 유무 확인
		//iKEP.chkMultiFavorite('${socialBoardItem.itemId}','${fn:replace(fn:replace(socialBoardItem.title,"\"",""),"'","")}',setProfileFavorite);
})(jQuery);

</script>	
							
								<div class="blockTableRead">

								<div class="blockTableRead_t">
									<p>
										<spring:bind path="searchResult.entity[${status.index}].title"> 
											<span>${status.value}</span> 
										</spring:bind>
										<span class="blockTableRead_t_info_1">
										<spring:bind path="searchResult.entity[${status.index}].categoryName"> 
											<span>${status.value}</span> 
										</spring:bind>
										</span>
									</p>
									<!--
									<div class="recommend">
										<a onclick="updateRecommendCount('${socialBoardItem.itemId}')" class="button_rec" href="#a"><span><img src="<c:url value='/base/images/icon/ic_recommend.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='recommend'/></span></a>	
									</div>
									-->
									<span class="date"><ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${socialBoardItem.registDate}"/></span>
									<spring:bind path="searchResult.entity[${status.index}].recommendCount">
										<span id="recommendCount${socialBoardItem.itemId}"><ikep4j:message pre='${preDetail}' key='recommendCount' /> <strong>${status.value}</strong></span>
									</spring:bind>
								</div>

								<c:if test="${not empty socialBoardItem.fileDataList}"> 
								<div id="fileDownload_${socialBoardItem.itemId}"></div>
								</c:if>	

								
								<div class="blockTableRead_c">
									<spring:htmlEscape defaultHtmlEscape="false"> 
										<spring:bind path="searchResult.entity[${status.index}].contents">
											<p>${status.value}</p>
										</spring:bind> 
					        		</spring:htmlEscape>
									
									<div class="tableTag" id="tagForm_${socialBoardItem.itemId}">     <!-- 게시물 id -->
									      <input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_SOCIAL_BLOG %>" />      <!--게시물 type TagConstants에서 맞는 타입 골라서 사용하면 됨.-->
									      <input type="hidden" name="tagItemSubType" value="${socialBlog.ownerId}" />                      <!--서브 파입이 있는 경우만 -->
									      <input type="hidden" name="tagItemName" value="${socialBoardItem.title}" />                          <!--게시물 제목-->
									      <input type="hidden" name="tagItemContents" value="${fn:escapeXml(socialBoardItem.contents)}" />                   <!--게시물 내용-->
									      <input type="hidden" name="tagItemUrl" value="/socialpack/socialblog/socialBlogPopup.do?blogOwnerId=${socialBlog.ownerId}&itemId=${socialBoardItem.itemId}" /> <!--상세화면 URL -도메인과 contextPash는 빼주시기 바랍니다.-->
									      
									     <div><!--코딩파일엔 div가 없는데 꼭 넣어주어야 함.-->
									        <span class="ic_tag"><span><ikep4j:message pre='${preDetail}' key='tag' /></span></span> <!--tagList--> 
									           <c:forEach var="tag" items="${socialBoardItem.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a></c:forEach>
									        <span class="rebtn">
									         <c:if test="${user.userId == socialBoardItem.registerId}">  <!--권한 체크 등록자랑 세션userID랑 같으면 태그 수정 가능-->
									           <a href="#a" onclick="iKEP.tagging.tagFormView('${socialBoardItem.itemId}');return false;" class="ic_modify"><span><ikep4j:message pre='${preButton}' key='update' /></span></a>
									         </c:if>                                          <!--게시물 id--> 
									       </span>
									     </div>
									</div>					
								</div>	
								<div class="border_t1"></div>
							
								<!--blockComment Start--> 
								<div class="blockComment" id="blockComment_${socialBoardItem.itemId}" onclick="loadSocialBoardLinereplyList(this,'${socialBoardItem.itemId}')">
								<form id="socialBoardLinereplySearchForm" name="socialBoardLinereplySearchForm" action="" method="post"> 
									<input name="pageIndex" type="hidden" value="0" title="pageIndex" /> 
								</form>
								</div>
								<!--//blockComment End-->
								
							</c:forEach>
							<!--//blockListTable End-->	 
							</c:otherwise>
							</c:choose> 
									
						</div>
						<!--//corner_RoundBox07 End-->