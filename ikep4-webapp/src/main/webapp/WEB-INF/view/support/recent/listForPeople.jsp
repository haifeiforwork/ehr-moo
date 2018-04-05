<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.favorite.header" /> 
<c:set var="preList"    value="ui.support.favorite.list" />
<c:set var="preDetail"  value="ui.support.favorite.detail" />
<c:set var="preButton"  value="ui.support.favorite.button" /> 
<c:set var="preMessage" value="ui.support.favorite.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript" src="<c:url value="/base/js/units/support/favorite/favorite.js"/>"></script>
 
<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	getList = function(isScrollSet) {
		
		$jq("#emptyDiv").hide();
		
		$jq.ajax({     
			url : '<c:url value="/support/recent/getSubListForPeople.do" />',     
			data :  $jq("#searchForm").serialize(),     
			type : "post",     
			loadingElement : {button:"#moreDiv"},
			success : function(result) {  
				$jq("#listDiv").append(result);
				if(isScrollSet) setScroll();
			},
			error : function(event, request, settings) { alert("error"); }
		});  
		
	};
	
	
	setCount = function() {
		
		var recordCount = eval($jq("#recordCount").val());
		var currentCount = eval($jq("#currentCount").val());
		
		var countStr = '<ikep4j:message pre='${preList}' key='totalCount' /> ' + recordCount + '<ikep4j:message pre='${preList}' key='count' /> ';
		countStr = countStr + ' (<ikep4j:message pre='${preList}' key='nowCount' /> ' + currentCount + '<ikep4j:message pre='${preList}' key='count' /> '+")";
		$jq("#countDiv").html(countStr);
		
		setMoreDiv();
	}
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		getList();
		
	});
	
	
	
})(jQuery);  

function addFavoriteRefresh(userId, userName){
	iKEP.addFavorite('PEOPLE','PF',userId, userName);
	reloadFullScreen();
}

function delFavoriteRefresh(userId, userName){
	iKEP.delFavorite('', userId);
	reloadFullScreen();
}

//-->
</script>
			
			
			
<!--skipNavigation Start-->
<div id="skipNavigation">
</div>
<!--//skipNavigation End-->

<!--wrapper Start-->
<div id="wrapper">
		
		<!--blockMain Start-->
		<div id="blockMain">

			<!--mainContents Start-->
			<div id="mainContents" class="conPPS">
			
				<h1><ikep4j:message pre='${preDetail}' key='recentPeople' /></h1>
				
				<form id="searchForm" method="post" action="" onsubmit="return false">  
				
				<spring:bind path="searchCondition.registerId">
					<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
				</spring:bind>	
				
				<spring:bind path="searchCondition.userLocaleCode">
					<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
				</spring:bind>	
				
				<spring:bind path="searchCondition.recordCount">
					<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
				</spring:bind>	
				
				<spring:bind path="searchCondition.currentCount">
					<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
				</spring:bind>	
				
				<spring:bind path="searchCondition.pageIndex">
					<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
				</spring:bind>		
								
						
						
							
						<div id="listDiv">
						
							
						
						
						</div>
						
						
							
				<div class="clear"></div>
				<!--//Contents End-->	
				
				
				<!--blockButton_3 Start-->
				<div id="moreDiv" class="blockButton_3" onclick="getMore()"> 
					<a class="button_3" href="#a"><span><ikep4j:message pre='${preMessage}' key='list.more' /> <img src="<c:url value="/base/images/icon/ic_more_ar.gif" />" alt="" /></span></a>				
				</div>
				
				<div id="emptyDiv" class="blockButton_3" > 
					<a class="button_3" href="#a"><span><ikep4j:message pre='${preMessage}' key='list.empty' /> </span></a>				
				</div>
				<!--//blockButton_3 End-->	
				
				
				</form>
					

			</div>
			<!--//mainContents End-->
			<div class="clear"></div>
			
		</div>
		<!--//blockMain End-->
		
	
</div>
<!--//wrapper End-->
