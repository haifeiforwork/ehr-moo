<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.collpack.collaboration.workspace.main" />
 <%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript" src="<c:url value="/base/js/units/support/favorite/favorite.js"/>"></script>
 
<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	getList = function(isScrollSet) {
		
		$jq("#emptyDiv").hide();
		
		$jq.ajax({     
			url : '<c:url value="/collpack/collaboration/workspace/getSubListWorkspaceImage.do" />',     
			data :  $jq("#searchForm").serialize(),     
			type : "post",     
			loadingElement : {button:"#moreDiv"},
			success : function(result) {  
				$jq("#slider").append(result);
				if(isScrollSet) setScroll();
				iKEP.iFrameContentResize();
			},
			error : function(event, request, settings) { alert("error"); }
		});  
		
	};
	
	getListAndClear = function() {
		
		$jq("#moreDiv").hide();
		$jq("#emptyDiv").hide();
		$jq("#pageIndex").val(1);
		
		$jq("#currentCount").val("0");
		
		var msg = '';

		$jq.ajax({     
			url : '<c:url value="/collpack/collaboration/workspace/getSubListWorkspaceImage.do" />',     
			data :  $jq("#searchForm").serialize(),     
			type : "post",     
			loadingElement : {container:"#blockMain"}, 
			success : function(result) {  
				
				$jq("#slider").children().remove();
				$jq("#slider").append(result);
				iKEP.iFrameContentResize();
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
		
		getListAndClear();
		
		$jq('#searchWord').keypress(function(event) { 
			if(event.which == '13') {
				getListAndClear();
			}
		}); 
		
	});
	
})(jQuery);  

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

			
				<h3><ikep4j:message pre='${prefix}' key='portlet.gallery' /></h3>
				
				<div class="blockBlank_20px"></div>
				
				<form id="searchForm" method="post" action="" onsubmit="return false">  
				
					<spring:bind path="searchCondition.workspaceId">  
							<input name="${status.expression}" id="${status.expression}" type="hidden" title="workspaceId" value="${status.value}" />
					</spring:bind>
					
					<spring:bind path="searchCondition.recordCount">
						<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" title="recordCount"/>
					</spring:bind>	
					
					<spring:bind path="searchCondition.currentCount">
						<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" title="currentCount"/>
					</spring:bind>	
									
				
				<!--Contents Start-->
				<!--blockListTable Start-->
				
						<div>
							<div id="slider" class="MyImage_topList">
								
							</div>
						</div>

						
				<div class="clear"></div>						
				<!--//blockListTable End-->	
				
				<div class="blockBlank_20px"></div>
				
				<!--blockButton_3 Start-->
				<div id="moreDiv" class="blockButton_3 mt5" onclick="getMore()"> 
					<a class="button_3" href="#a"><span><ikep4j:message pre='${prefix}' key='portlet.more' /> <img src="<c:url value="/base/images/icon/ic_more_ar.gif" />" alt="" /></span></a>				
				</div>
				
				<div id="emptyDiv" class="blockButton_3" > 
					<a class="button_3" href="#a"><span><ikep4j:message pre='${prefix}' key='portlet.noDataRecentItem' /> </span></a>				
				</div>
				<!--//blockButton_3 End-->	
				<!--//Contents End-->		
				
				</form>							

			<div class="clear"></div>
			
		</div>
		<!--//blockMain End-->
	
	
</div>
<!--//wrapper End-->
