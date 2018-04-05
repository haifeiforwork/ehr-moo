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
<script type="text/javascript">
<!--   

var mailClickItemId; 
(function($){	
	
	loadSocialBoardLinereplyList = function(el, itemId) { //

		var $linereplyForm = $("#socialBoardLinereplySearchForm");
		
		$(el).load('<c:url value="/socialpack/socialblog/listSocialBoardLinereplyView.do"/>?itemId='+ itemId
		//$(el).load('<c:url value="/socialpack/socialblog/listSocialBoardLinereplyView.do"/>'
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

		$jq("#searchBoardItemButton").click(function() {   
			getSearchlistSocialBoardItemView();
		});   
		
		$jq("#btn_search_posting").click(function() {   
			var searchType = $jq("#searchBoardItemForm select[name=searchColumn]").val();
			var searchKeyword = $jq("#searchBoardItemForm input[name=searchWord]").val();
			var pagePerRecord = $jq("#searchBoardItemForm select[name=pagePerRecord]").val();
			getSearchBlogPosting(searchType,searchKeyword,pagePerRecord);

		}); 
		
		
		$jq("#searchBoardItemForm select[name=pagePerRecord]").change(function() {
			$("#searchBoardItemButton").click(); 
		});   
		
		$jq(".blockComment").trigger("click");
		
		
		$jq("input[name=searchWord]").bind("keydown", function(event) {
			if(event.which == 13) {
				var searchType = $jq("#searchBoardItemForm select[name=searchColumn]").val();
				var searchKeyword = $jq("#searchBoardItemForm input[name=searchWord]").val();
				var pagePerRecord = $jq("#searchBoardItemForm select[name=pagePerRecord]").val();
				getSearchBlogPosting(searchType,searchKeyword,pagePerRecord);
				//$(".ic_search").trigger("click");
			}
		});
		
	}); 
	
	//링크 첨부해서 메일로 보내기 팝업 버튼
	sendMailLink = function(itemId,title) {

		var url = iKEP.getWebAppPath() + "/socialpack/socialblog/socialBlogPopup.do?itemId="+itemId;
		var content = "<a href='"+url+"'>" + title + "</a>";

		mailClickItemId = itemId;
        //파라미터로 title 과 content를 만들어서 함수를 실행
		iKEP.sendMailPop("", "", title, content, "", "");
	};

	
	// 즐겨 찾기 추가 여부 
	setProfileFavorite = function(data,itemId,title) {
		var str = "";
		if(data.status == 'exists'){
			str="<a class=\"ic_rt_favorite select\" onclick=\"deleteFavorite('"+itemId+"');\" href=\"#a\"><span></span></a>";
		}else if(data.status == 'success'){
			str="<a class=\"ic_rt_favorite\" onclick=\"addUserFavorite('"+itemId+"','"+title+"');\" href=\"#a\"><span></span></a>";
		}
		$jq("#favoriteBtnView_"+itemId).html(str);

	}
	
	// 즐겨 찾기 추가 여부 
	displyFavorite = function(data,targetId,targetTitle) {
		if(data.status == 'success'){
			iKEP.chkMultiFavorite(targetId, targetTitle, setProfileFavorite);
		}
	}
	
	addUserFavorite = function(itemId,title){
		iKEP.addMultiFavorite('CONTENTS','${IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG}',itemId,title,displyFavorite);			
	}
	
	deleteFavorite = function(itemId,title){
		iKEP.delMultiFavorite('',itemId,title,displyFavorite);
	}
	
})(jQuery);

	
	//메일 성공적으로 보냈을시 콜백함수
	function callbackMail(){
		$jq.ajax({    
			url : "updateMailCount.do",     
			data : {itemId:mailClickItemId},          
			type : "post",  
			dataType : "html",
			success : function(result) {   
			},
			error : function(event, request, settings){
			 alert("error");
			}
		}); 
	}


	var mblogClickItemId;
	//마이크로블로그 팝업
	function twittingPop(itemId, title){

		var content = title; 
		
		var url = iKEP.getWebAppPath() + "/socialpack/socialblog/socialBlogPopup.do?itemId="+itemId;

		mblogClickItemId= itemId;
		iKEP.showTwittingPage(content,url);
		
	};

	//블로그 성공적으로 보내고 콜백함수
	function callbackMBlog(){
		$jq.ajax({    
			url : "updateMblogCount.do",      //  업데이트 URL은 작성 모듈에 맞게 변경
			data : {itemId:mblogClickItemId},  
			type : "post",  
			dataType : "html",
			success : function(result) {   
			},
			error : function(event, request, settings){
			 alert("error");
			}
		}); 
	}
	
	//쪽지 팝업
	function messgaePop(itemId, title) { 
		 
		var url = iKEP.getWebAppPath() + "/socialpack/socialblog/socialBlogPopup.do?itemId="+itemId;
		
		var content = "<a href='"+url+"' target='_blank'>"+title+"</a>" ;
		
		 // 단순 작성 팝업 열기
		 var url = "<c:url value='/support/message/messageNew.do'/>?contents="+content;
		 
		 iKEP.popupOpen(url ,{width:800, height:670} );
	}
	

//-->
</script>

						<h2 class="none"><ikep4j:message pre='${preMain}' key='socialBlog.content' /></h2>
						
						<form id="searchBoardItemForm" name="searchBoardItemForm" action="" method="post" onsubmit="return false;">
						<spring:bind path="searchCondition.pageIndex"> 
							<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
						</spring:bind> 
						<input name="blogOwnerId" type="hidden" value="${socialBlog.ownerId}" />	
						<spring:bind path="searchCondition.sortColumn">
							<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
						</spring:bind>
						
						<!--corner_RoundBox07 Start-->
						<div class="corner_RoundBox07">
							<div class="listInfo">
									<spring:bind path="searchCondition.pagePerRecord">  
										<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
										<c:forEach var="code" items="${socialBoardCode.pageNumList}">
											<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
										</c:forEach> 
										</select> 
									</spring:bind>
									<div class="totalNum"><!-- ${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> --> <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
							</div>														
							<div class="tableSearch">
								<spring:bind path="searchCondition.searchColumn">  
										<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
											<option value="ALL" <c:if test="${'ALL' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='${status.expression}' post='all'/></option>
											<option value="title" <c:if test="${'title' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='${status.expression}' post='title'/></option>
											<option value="contents" <c:if test="${'contents' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='${status.expression}' post='contents'/></option>
										</select>		
									</spring:bind>
									<spring:bind path="searchCondition.searchWord">  					
										<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
									</spring:bind>
									<a id="searchBoardItemButton" name="searchBoardItemButton" href="#a"></a>
									<a class="ic_search" href="#a" id="btn_search_posting"><span><ikep4j:message pre='${preButton}' key='search'/></span></a>
							</div>			
							<div class="clear"></div>							
							<div class="l_t_corner"></div>
							<div class="r_t_corner"></div>
							<div class="l_b_corner"></div>
							<div class="r_b_corner"></div>				
						</div>
						<!--//corner_RoundBox07 End-->
						
						
						</form>			
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
		iKEP.chkMultiFavorite('${socialBoardItem.itemId}','${fn:replace(fn:replace(socialBoardItem.title,"\"",""),"'","")}',setProfileFavorite);
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
										<span class="rebtn">
											<!--blockButton Start-->
											<c:if test="${isSystemAdmin and socialBoardItem.registerId != user.userId }">  <%-- 시스템 관리자 사용자라 --%>
												<a onclick="SocialBlog.getEditBlogPosting('${socialBlog.ownerId}','${socialBoardItem.itemId}')" href="#a" class="ic_modify" title="<ikep4j:message pre='${preButton}' key='update'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a>
											</c:if>
											<c:if test="${isSystemAdmin and socialBoardItem.registerId != user.userId }">  <%-- 시스템 관리자 사용자라 --%>
												<a onclick="adminDeleteSocialBoardItem('${socialBoardItem.itemId}')" href="#a" class="ic_delete" title="<ikep4j:message pre='${preButton}' key='delete'/>"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>	
											</c:if>
											<c:if test="${socialBoardItem.registerId eq user.userId}">  <%-- 로그인유저가 작성자와 같다면 --> 수정, 사용자용 삭제기능 --%>
												<a onclick="SocialBlog.getEditBlogPosting('${socialBlog.ownerId}','${socialBoardItem.itemId}')" href="#a" class="ic_modify" title="<ikep4j:message pre='${preButton}' key='update'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a>
											</c:if>
											<c:if test="${socialBoardItem.registerId eq user.userId}">  <%-- 로그인유저가 작성자와 같다면 --> 수정, 사용자용 삭제기능 --%>
												<a onclick="userDeleteSocialBoardItem('${socialBoardItem.itemId}')" href="#a" class="ic_delete" title="<ikep4j:message pre='${preButton}' key='delete'/>"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>	
											</c:if>
											<!--//blockButton End-->
										</span>	
									</p>
									

									<span class="date"><ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${socialBoardItem.registDate}"/></span>
									<span id="favoriteBtnView_${socialBoardItem.itemId}"></span>
									<a class="valign_top" href="#a" onclick="sendMailLink('${socialBoardItem.itemId}','${fn:replace(fn:replace(socialBoardItem.title,"\"",""),"'","")}')"><img src="<c:url value='/base/images/icon/ic_mail_2.gif' />" alt="<ikep4j:message pre='${preButton}' key='linkSender'/>" title="<ikep4j:message pre='${preButton}' key='linkSender'/>" /></a>
								    <span class="icon">
								    	<a class="valign_top" href="#a" onclick="twittingPop('${socialBoardItem.itemId}','${fn:replace(fn:replace(socialBoardItem.title,"\"",""),"'","")}')"><img src="<c:url value='/base/images/icon/ic_microblog.gif' />" alt="<ikep4j:message pre='${preButton}' key='linkSender'/>" title="<ikep4j:message pre='${preButton}' key='linkSender'/>" /></a>
										<a class="valign_top" href="#a" onclick="messgaePop('${socialBoardItem.itemId}','${fn:replace(fn:replace(socialBoardItem.title,"\"",""),"'","")}')"><img src="<c:url value='/base/images/icon/ic_note.gif' />" alt="<ikep4j:message pre='${preButton}' key='linkSender'/>" title="<ikep4j:message pre='${preButton}' key='linkSender'/>" /></a>
									</span>
									<div class="recommend">
										<a onclick="updateRecommendCount('${socialBoardItem.itemId}')" class="button_rec_num" href="#a">
											<spring:bind path="searchResult.entity[${status.index}].recommendCount">
											<span class="num" id="recommendCount${socialBoardItem.itemId}">${status.value}</span><span class="doc"><img src="<c:url value='/base/images/icon/ic_recommend.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='recommend'/></span>
											</spring:bind>
										</a>	
									</div>
	
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
									      <input type="hidden" name="tagItemUrl" value="/socialpack/socialblog/socialBlogPopup.do?blogOwnerId=${socialBlog.ownerId}&itemId=${socialBoardItem.itemId}"> <!--상세화면 URL -도메인과 contextPash는 빼주시기 바랍니다.-->
									      
									      <div><!--코딩파일엔 div가 없는데 꼭 넣어주어야 함.-->
											<span class="ic_tag"><span><ikep4j:message pre='${preDetail}' key='tag' /></span></span>
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
								
							</div>
							
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
							<div class="l_t_corner"></div>
							<div class="r_t_corner"></div>
							<div class="l_b_corner"></div>
							<div class="r_b_corner"></div>	 
							<!--Page Number Start--> 
							<div>
								<spring:bind path="searchCondition.pageIndex">
									<c:if test="${ searchCondition.pageCount > 1 }"> 
									<ikep4j:pagination searchButtonId="searchBoardItemButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
									</c:if> 
								</spring:bind> 
							</div>
							<!--//Page Number Start-->
										
						</div>
						<!--//corner_RoundBox07 End-->