<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.collpack.collaboration.workspace.main.menu.detail.menu" />
<%-- 메시지 관련 Prefix 선언 End --%>


<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript" language="javascript">

(function($) {

	getList = function(isScrollSet) {
		
		$jq("#emptyDiv").hide();
		
		$jq.ajax({     
			url : '<c:url value='/socialpack/microblogging/teamTimelineHome.do'/>',     
			data :  $jq("#searchForm").serialize(),     
			type : "post",     
			success : function(result) {  
				$jq("#listDiv").append(result);

				//$jq("#emptyDiv").hide();
				
				
			}, 
			error : function(event, request, settings) { alert("error"); }
		});  
		
		//afterSearch(msg);
		iKEP.iFrameContentResize();
	};
	

	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		getList();
		
	});
	
})(jQuery);  


</script>

<div id="mainContents_4" >

	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><span id="titleSpan"><ikep4j:message pre='${prefix}' key='teamMentions.pageTitle' /></span></h2>
	</div>
	<!--//pageTitle End-->
	
	<div class="corner_RoundBox01 mb10">
		<c:choose>
			<c:when test="${user.localeCode == portal.defaultLocaleCode}">
				${workspace.groupName}
			</c:when>
			<c:otherwise>
				${workspace.groupEnglishName}
			</c:otherwise>
		</c:choose>			
		<ikep4j:message pre='${prefix}' key='teamMentions.pageSubTitle' />
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
		<div class="l_b_corner"></div>
		<div class="r_b_corner"></div>					
	</div>

	<form id="searchForm" method="post" action="" onsubmit="return false">  
	<input type="hidden" name="groupId" value="${groupId}" />
	<div id="tabInputDiv">
		<input type="hidden" id="${group.groupId}" name="groupList"  value="${groupList}" />
		<input type="hidden" id="pageIndex" name="pageIndex"  value="${pageIndex+1}" />	
	</div>
			

	<!--Contents Start-->
	<!--blockListTable Start-->
	<div id="listDiv"></div>
	<!--//blockListTable End-->
	
	<div id="emptyDiv" class="blockButton_3" > 
		<a class="button_3" href="#a"><span><ikep4j:message pre='${prefix}' key='teamMentions.empty' /> </span></a>				
	</div>
	<!--//blockButton_3 End-->	
	<!--//Contents End-->		
	
	</form>							
</div>