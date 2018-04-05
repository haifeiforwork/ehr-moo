<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preUi" 			value="ui.collpack.forum.forumMenu" />

<script type="text/javascript">
//<![CDATA[

    $jq(document).ready(function () {
 	   
 	   	$jq("#leftCategoryList li:last").addClass("liLast");
 	   
        // Left Menu setting
        iKEP.setLeftMenu();
        
    });
    

    function viewPopUpProfile(targetUserId){

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

    			if( pageIndex == 1){
    				frame.html(result);
    			} else {
    				frame.append(result);
        		}
        		
    			 
    			var viewCount = $jq('.lineRow').size();
    			
    			if($jq("#itemFrame").data('totalCount')){
    				$jq("#moreBtn").show();
    			}
    			
    			if(totalCount <= viewCount){
    				$jq('#moreText').text('<ikep4j:message  key='message.collpack.forum.notMore'/>');
    			}
    			
    		},
    		error : function(event, request, settings){
    		 alert("error");
    		}
    	}); 
    	
    }



    //참가자 리스트
    var partyPageindex = 1;
    function partyList(discussionId, totalCount){
    	
    	var viewCount = $jq('#partyFrame_'+discussionId+' li').size();
    	
    	if(totalCount <= viewCount){
    		return;	
    	}
    	partyPageindex++;
    	
    	$jq.ajax({    
    		url : "participantListMore.do",     
    		data : {discussionId:discussionId, pageIndex:partyPageindex},     
    		type : "post",  
    		dataType : "html",
    		success : function(result) {   

        		if( partyPageindex == 1){
        			$jq('#partyFrame_'+discussionId).html(result);
    			} else {
    				$jq('#partyFrame_'+discussionId).append(result);
        		}
    			
    			viewCount = $jq('#partyFrame_'+discussionId+' li').size();

    			if(totalCount <= viewCount){
    				$jq('#partyMoreBtn_'+discussionId).hide();
    			}

    			var partCount =  $jq('#partyFrame_'+discussionId+' li:last').attr("class").replace("participant_","");

   				if(partCount > 5 && partyPageindex == 1){
   					$jq("#partyMoreBtn_"+discussionId).show();
   				}
    			
    		},
    		error : function(event, request, settings){
    		 alert("error");
    		}
    	}); 
    }


    function goViewItem(itemId){

    	location.href="getView.do?itemId="+itemId+"&pageIndex="+pageIndex+"#a";
     }
  //]]>
</script>


<c:set var="requestUrl" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<h1 class="none">Left Menu</h1>	
<h2><a href="main.do"><ikep4j:message pre='${preUi}' key='title'/></a></h2>

<div class="blockButton_2 mb10"> 
	<a class="button_2" href="discussionForm.do<c:if test="${!empty(param.categoryId)}">?categoryId=${param.categoryId }</c:if>" title="<ikep4j:message pre='${preUi}' key='disWrite'/>"><span><img src="<c:url value="/base/images/icon/ic_forum.png"/>" alt="<ikep4j:message pre='${preUi}' key='disWrite'/>" /> <ikep4j:message pre='${preUi}' key='disWrite'/></span></a>				
</div>

					
<div class="left_fixed">	
	<div class="boxList_2">
		<div class="subTitle_2">
			<ikep4j:message pre='${preUi}' key='myDisAct'/>
		</div>
		<ul>
			<li><a href="myActivity.do"><ikep4j:message pre='${preUi}' key='forumRanking'/> : <span class="colorPointS">${!empty(myActivities.discussionRank) ? myActivities.discussionRank:0}</span></a></li>
			<li><a href="myActivity.do?tabType=discussion"><ikep4j:message pre='${preUi}' key='discussion'/> : <strong>${!empty(myActivities.discussionCount) ? myActivities.discussionCount:0}</strong><ikep4j:message pre='${preUi}' key='count'/></a></li>
			<li><a href="myActivity.do"><ikep4j:message pre='${preUi}' key='item'/> : <strong>${!empty(myActivities.itemCount) ? myActivities.itemCount:0}</strong><ikep4j:message pre='${preUi}' key='count'/></a></li>
			<li><a href="myActivity.do?tabType=linereply"><ikep4j:message pre='${preUi}' key='linereply'/> : <strong>${!empty(myActivities.linereplyCount) ? myActivities.linereplyCount:0}</strong><ikep4j:message pre='${preUi}' key='count'/></a></li>
		</ul>
	</div>
	<ul>		
		<li class="liFirst opened"><a href="#a"><ikep4j:message pre='${preUi}' key='disAct'/></a>

			<ul>
				<li class="no_child <c:if test="${fn:contains(requestUrl,'popularList.do')}"> licurrent</c:if>"><a href="popularList.do" title="<ikep4j:message pre='${preUi}' key='popularDis'/>"><ikep4j:message pre='${preUi}' key='popularDis'/></a></li>
				<li class="no_child <c:if test="${fn:contains(requestUrl,'bestList.do')}"> licurrent</c:if>"><a href="bestList.do" title="<ikep4j:message pre='${preUi}' key='bestDis'/>"><ikep4j:message pre='${preUi}' key='bestDis'/></a></li>
				<li class="no_child liLast <c:if test="${fn:contains(requestUrl,'lastList.do') && empty(param.categoryId)}"> licurrent</c:if>"><a href="lastList.do" title="<ikep4j:message pre='${preUi}' key='lastDis'/>"><ikep4j:message pre='${preUi}' key='lastDis'/></a></li>
			</ul>
		</li>					
		<li class="opened" id="leftCategoryList"><a href="#a"  title="<ikep4j:message pre='${preUi}' key='disCategory'/>"><ikep4j:message pre='${preUi}' key='disCategory'/></a>

			<ul>
			<c:forEach var="category" items="${categoryList}" varStatus="loopCount">
			
				<c:choose>
					<c:when test="${category.categoryId == param.categoryId}">
						<c:set var="selectedClass" value="licurrent"/>
					</c:when>
					<c:otherwise>
						<c:set var="selectedClass" value=""/>
					</c:otherwise>
				</c:choose>
				
				<li class="no_child ${selectedClass}"><a href="lastList.do?categoryId=${category.categoryId}" title="${category.categoryName}">${category.categoryName}</a></li>
			</c:forEach>
			</ul>
		</li>		
		<li class="opened liLast"><a href="#a" title="<ikep4j:message pre='${preUi}' key='disAnaly'/>"><ikep4j:message pre='${preUi}' key='disAnaly'/></a>
			<ul>
				<li class="no_child liLast <c:if test="${fn:contains(requestUrl,'keyIssueList.do')}"> licurrent</c:if>"><a href="keyIssueList.do" title="<ikep4j:message pre='${preUi}' key='keyIssues'/>"><ikep4j:message pre='${preUi}' key='keyIssues'/></a></li>
				<li class="no_child liLast <c:if test="${fn:contains(requestUrl,'rankList.do')}"> licurrent</c:if>"><a href="rankList.do" title="<ikep4j:message pre='${preUi}' key='rankList'/>"><ikep4j:message pre='${preUi}' key='rankList'/></a></li>
				
			</ul>
		</li>
		<c:if test="${isAdmin == true}">
		<li class="opened"><a href="#a" title="<ikep4j:message pre='${preUi}' key='admin'/>"><ikep4j:message pre='${preUi}' key='admin'/></a>

			<ul>
				<li class="no_child  <c:if test="${fn:contains(requestUrl,'categoryList.do')}"> licurrent</c:if>"><a href="categoryList.do" title="<ikep4j:message pre='${preUi}' key='disCateManager'/>"><ikep4j:message pre='${preUi}' key='disCateManager'/></a></li>
				<li class="no_child <c:if test="${fn:contains(requestUrl,'activityPolicyForm.do')}"> licurrent</c:if>"><a href="activityPolicyForm.do" title="<ikep4j:message pre='${preUi}' key='disActiManager'/>"><ikep4j:message pre='${preUi}' key='disActiManager'/></a></li>
				<li class="no_child <c:if test="${fn:contains(requestUrl,'popularPolicyForm.do')}"> licurrent</c:if>"><a href="popularPolicyForm.do" title="<ikep4j:message pre='${preUi}' key='popularDisManager'/>"><ikep4j:message pre='${preUi}' key='popularDisManager'/></a></li>
				<li class="no_child <c:if test="${fn:contains(requestUrl,'bestPolicyForm.do')}"> licurrent</c:if>"><a href="bestPolicyForm.do" title="<ikep4j:message pre='${preUi}' key='bestDisManager'/>"><ikep4j:message pre='${preUi}' key='bestDisManager'/></a></li>
				<li class="no_child liLast <c:if test="${fn:contains(requestUrl,'analysisPolicyForm.do')}"> licurrent</c:if>"><a href="analysisPolicyForm.do" title="<ikep4j:message pre='${preUi}' key='disAnalyManager'/>"><ikep4j:message pre='${preUi}' key='disAnalyManager'/></a></li>
			</ul>
		</li>	
		</c:if>	
													
	</ul>

</div>	
