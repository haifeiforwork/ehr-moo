<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.activitystream.header" /> 
<c:set var="preList"    value="ui.support.activitystream.list" />
<c:set var="preDetail"  value="ui.support.activitystream.detail" />
<c:set var="preButton"  value="ui.support.activitystream.button" /> 
<c:set var="preMessage" value="ui.support.activitystream.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>

 
<script type="text/javascript" language="javascript">

(function($) {
	
	getList = function() {
		
		$jq.ajax({     
			url : '<c:url value="getSummaryList.do" />',     
			data :  $jq("#searchForm").serialize(),     
			type : "post",     
			success : function(result) {  
				$jq("#listDiv").html(result);
			} 
		});  
		
	};
	
	
	
	
	// 주의 : 글로벌 함수는 아래와 같이 글로벌 변수에 무명 함수를 assign해서 생성한다. 
	//정렬 조건 변경 함수
	sort = function(sortColumn, sortType) {
		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			$jq("input[name=sortType]").val('DESC');	
		} else {
			$jq("input[name=sortType]").val('ASC');
		}
		
		getList();
	};
	
	
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		getList();
		
	});
	
})(jQuery);  


</script>
			
			
			

			<!--mainContents Start-->
			<div id="mainContents" >
			
				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><span id="titleSpan"><ikep4j:message pre='${preList}' key='main.title' /></span></h2>
					<div id="pageLocation">
						<ul>
							<li class="liFirst">Home</li>
							<li><ikep4j:message pre='${preList}' key='main.title' /></li>
							<li class="liLast"><span id="titleSpan2"><ikep4j:message pre='${preList}' key='main.title' /></span></li>
						</ul>
					</div>
				</div>
				<!--//pageTitle End-->
	
				
				<!--blockListTable Start-->
				<div class="blockListTable">
				
					
						
					<div id="listDiv"></div>
					
						
						
				</div>
				<!--//blockListTable End-->	
			
								
				
			</div>
			<!--//mainContents End-->
			
			
			



	