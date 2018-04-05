<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preUi" 			value="ui.collpack.ideation.ideaListMenu" />
<c:set var="preUiQna" 			value="ui.collpack.qna.qnaMenu" />
<script type="text/javascript">
	$jq(document).ready(function () {
		   
		$jq("#leftCategoryList li:last").addClass("liLast");
		   
	    // Left Menu setting
	    iKEP.setLeftMenu();
	    
	});
	
	
	function viewPopUpProfile(targetUserId){
		//var pageURL = "<c:url value='/support/common/profile/getProfilePopup.do' />" + "?targetUserId=" + targetUserId;
		//iKEP.popupOpen( pageURL , {width:800, height:280 , callback:function(result) {} });

		iKEP.goProfilePopupMain(targetUserId);

	}


	var pageIndex = 1;		//페이징 수
	function listMore(url, totalCount, param){
		
		//페이지수만큼 불렀음 호출 더이상 안함.
		var viewCount = $jq('.lineRow').size();
		
		if(totalCount <= viewCount){
			return;	
		}
		
		if(param && param.pageIndex){
			pageIndex = param.pageIndex;
		} else {
			pageIndex++;
		}
		
		ajaxListAction(url, totalCount, param);
	}	


	function ajaxListAction(url, totalCount, param){
		
		param = $jq.extend(param, {pageIndex:pageIndex});
		
		$jq.ajax({    
			url : url,     
			data : param,     
			type : "post",  
			dataType : "html",
			success : function(result) {   
				
				var frame = $jq("#itemFrame"); 
				
				frame.append(result); 
				var viewCount = $jq('.lineRow').size();
				
				if(totalCount <= viewCount){
					$jq('#moreText').text('<ikep4j:message key='message.collpack.ideation.notMore'/>');
				}
				
			},
			error : function(event, request, settings){
			 alert("error");
			}
		}); 
		
	}


</script>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="requestUrl" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<h1 class="none">left Menu</h1>	

	<h2>
		<span><a href="<c:url value="/collpack/ideation/main.do"/>"><img border="0" style="padding-top:6px" src="<c:url value='/base/images/title/lmt_know_idea.gif'/>"/></a></span>
	</h2>


<div class="blockButton_2 mb10"> 
	<a class="button_2" href="ideaForm.do" title="<ikep4j:message pre='${preUi}' key='ideaSug'/>"><span><img src="<c:url value="/base/images/icon/ic_idea.png"/>" alt="<ikep4j:message pre='${preUi}' key='ideaSug'/>" /> <ikep4j:message pre='${preUi}' key='ideaSug'/></span></a>				
</div>
					
<div class="left_fixed">	
	<div class="boxList_2">
		<div class="subTitle_2">
			<a href="myActivity.do" ><ikep4j:message pre='${preUi}' key='myIdeaActi'/></a>
		</div>
		<ul>
			<li><ikep4j:message pre='${preUi}' key='sugRanking'/> : <span class="colorPointS">${ (empty(myActivities.suggestionRank) || myActivities.suggestionRank == -1)? 0:myActivities.suggestionRank}</span>&nbsp;<ikep4j:message pre='${preUi}' key='ranking'/></li>
			<li><ikep4j:message pre='${preUi}' key='conRanking'/> : <span class="colorPointS">${(empty(myActivities.contributionRank)|| myActivities.contributionRank == -1)? 0:myActivities.contributionRank}</span>&nbsp;<ikep4j:message pre='${preUi}' key='ranking'/></li>
			<li><ikep4j:message pre='${preUi}' key='subIdea'/> : <strong>${(empty(myActivities.itemCount)|| myActivities.itemCount == -1)? 0:myActivities.itemCount}</strong>&nbsp;<ikep4j:message pre='${preUi}' key='count'/></li>
			<!-- 			
			<li><a href="myActivity.do?isUserAdopt=1" title="<ikep4j:message pre='${preUi}' key='adoptIdea'/>"><ikep4j:message pre='${preUi}' key='adoptIdea'/> : <strong>${(empty(myActivities.adoptCount)|| myActivities.adoptCount == -1)? 0:myActivities.adoptCount}</strong>&nbsp;<ikep4j:message pre='${preUi}' key='count'/></a></li>
			-->
		</ul>
	</div>
	<ul>		
		<li class="liFirst opened"><a href="#a" title="<ikep4j:message pre='${preUi}' key='ideaManager'/>"><ikep4j:message pre='${preUi}' key='ideaManager'/></a>
			<ul>
				<li class="no_child <c:if test="${fn:contains(requestUrl,'lastList.do') && (empty(param.isNullCategory) && empty(param.categoryId) && empty(param.isOpenContest) && empty(param.isBest) && empty(param.isAdopt) && empty(param.isBusiness))}"> licurrent</c:if>"><a href="lastList.do" title="<ikep4j:message pre='${preUi}' key='sugingIdea'/>"><ikep4j:message pre='${preUi}' key='sugingIdea'/></a></li>
				<li class="no_child <c:if test="${fn:contains(requestUrl,'lastList.do') && param.isBest == 1}"> licurrent</c:if>"><a href="lastList.do?isBest=1" title="<ikep4j:message pre='${preUi}' key='bestIdea'/>"><ikep4j:message pre='${preUi}' key='bestIdea'/></a></li>
				<!-- 				
				<li class="no_child <c:if test="${fn:contains(requestUrl,'lastList.do') && param.isAdopt == 1}"> licurrent</c:if>"><a href="lastList.do?isAdopt=1" title="<ikep4j:message pre='${preUi}' key='adoptedIdea'/>"><ikep4j:message pre='${preUi}' key='adoptedIdea'/></a></li>
				-->
				<li class="no_child liLast <c:if test="${fn:contains(requestUrl,'lastList.do') && param.isBusiness==1}"> licurrent</c:if>"><a href="lastList.do?isBusiness=1" title="<ikep4j:message pre='${preUi}' key='busiAdoptIdea'/>"><ikep4j:message pre='${preUi}' key='busiAdoptIdea'/></a></li>

			</ul>
		</li>					
		<!-- 	
		<li class="opened"><a href="#a" title="<ikep4j:message pre='${preUi}' key='aniIdea'/>"><ikep4j:message pre='${preUi}' key='aniIdea'/></a>
			<ul>
				<li class="no_child <c:if test="${fn:contains(requestUrl,'keyIssueList.do') && empty(param.isBest)}"> licurrent</c:if>"><a href="keyIssueList.do" title="<ikep4j:message pre='${preUi}' key='popularIdeaKey'/>"><ikep4j:message pre='${preUi}' key='popularIdeaKey'/></a></li>
				<li class="no_child liLast <c:if test="${fn:contains(requestUrl,'keyIssueList.do') && param.isBest == 1}"> licurrent</c:if>"><a href="keyIssueList.do?isBest=1" title="<ikep4j:message pre='${preUi}' key='bestIdeaKey'/>"><ikep4j:message pre='${preUi}' key='bestIdeaKey'/></a></li>
			</ul>
		</li> -->
		<li class="opened"><a href="#a" title="<ikep4j:message pre='${preUi}' key='ideaActiApp'/>"><ikep4j:message pre='${preUi}' key='ideaActiApp'/></a>
			<ul>
				<li class="no_child <c:if test="${fn:contains(requestUrl,'activityList.do')}"> licurrent</c:if>"><a href="activityList.do" title="<ikep4j:message pre='${preUi}' key='ideaActiApp'/>"><ikep4j:message pre='${preUi}' key='ideaActiApp'/></a></li>
				<li class="no_child liLast <c:if test="${fn:contains(requestUrl,'businessList.do')}"> licurrent</c:if>"><a href="businessList.do" title="<ikep4j:message pre='${preUi}' key='ideaBusiPresent'/>"><ikep4j:message pre='${preUi}' key='ideaBusiPresent'/></a></li>
			</ul>
		</li>
		<li class="opened " id="leftCategoryList"><a href="#a" title="<ikep4j:message pre='${preUiQna}' key='menuCategory'/>"><ikep4j:message pre='${preUiQna}' key='menuCategory'/></a>
			<ul class="qnalist_sub">
				<c:forEach var="category" items="${categoryList}" varStatus="loopCount">
					
					<c:choose>
						<c:when test="${category.categoryId == param.categoryId}">
							<c:set var="selectedClass" value="licurrent"/>
						</c:when>
						<c:otherwise>
							<c:set var="selectedClass" value=""/>
						</c:otherwise>
					</c:choose>
					
					<li class="qnalist ${selectedClass} "><a href="lastList.do?categoryId=${category.categoryId}" title="${category.categoryName}">${category.categoryName}</a></li>
				</c:forEach>
				
					<li class="qnalist <c:if test="${fn:contains(requestUrl,'lastList') && !empty(param.isNullCategory)}">licurrent</c:if>" ><a href="lastList.do?isNullCategory=1" title="<ikep4j:message pre='${preUiQna}' key='cetera'/>"><ikep4j:message pre='${preUiQna}' key='cetera'/></a></li>
			</ul>	
		</li>
		<c:if test="${isAdmin == true}">
			<li class="opened"><a href="#a" title="<ikep4j:message pre='${preUi}' key='administration'/>"><ikep4j:message pre='${preUi}' key='administration'/></a>
				<ul>
					<li class="no_child <c:if test="${fn:contains(requestUrl,'listCategory.do')}"> licurrent</c:if>" ><a href="listCategory.do" title="<ikep4j:message pre='${preUiQna}' key='menuCategoryAdmin'/>"><ikep4j:message pre='${preUiQna}' key='menuCategoryAdmin'/></a></li>
					<li class="no_child <c:if test="${fn:contains(requestUrl,'bestPolicyForm.do')}"> licurrent</c:if>"><a href="bestPolicyForm.do" title="<ikep4j:message pre='${preUi}' key='bestIdeaManager'/>"><ikep4j:message pre='${preUi}' key='bestIdeaManager'/></a></li>
					<li class="no_child <c:if test="${fn:contains(requestUrl,'suggestionPolicyForm.do')}"> licurrent</c:if>"><a href="suggestionPolicyForm.do" title="<ikep4j:message pre='${preUi}' key='ideaSugActiManager'/>"><ikep4j:message pre='${preUi}' key='ideaSugActiManager'/></a></li>
					<li class="no_child liLast <c:if test="${fn:contains(requestUrl,'contributionPolicyForm.do')}"> licurrent</c:if>"><a href="contributionPolicyForm.do" title="<ikep4j:message pre='${preUi}' key='ideaConActiManager'/>"><ikep4j:message pre='${preUi}' key='ideaConActiManager'/></a></li>
				</ul>
			</li>	
		</c:if>											
	</ul>
</div>	
