<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.popup.user.header" /> 
<c:set var="preList"    value="ui.support.popup.user.list" />
<c:set var="preDetail"  value="ui.support.popup.user.detail" />
<c:set var="preButton"  value="ui.support.popup.button" /> 
<c:set var="preMessage" value="ui.support.popup.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" language="javascript">

(function($) {
	
	getList = function() {
		
		$jq.ajax({     
			url : '<c:url value="getPopupUserList.do" />?isMulti=${isMulti}',     
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
		
		var param = iKEP.getPopupArgument();
		$jq("#searchWord").val("${searchCondition.searchWord}");
		$jq("#searchColumn").val("${searchCondition.searchColumn}");
		getList();
		
		$jq("#applyBtn").click(function() {
			
			var $sel = $jq("input[name=userId]:checked");
			if($sel.length <1) {
				alert('<ikep4j:message pre='${preMessage}' key='select' />');
				return;	
			}
			
				  
			var data = new Array();
			var i=0;
			$jq("input[name=userId]:checked").each(function() {
				var $dataDiv = $(this).next("div.none");
   				data.push({
   					type:"user",
   					id: this.value,
   					userName: $("span.data_userName", $dataDiv).text(),
   					jobTitleName : $("span.data_jobTitleName", $dataDiv).text(),
   					teamName : $("span.data_teamName", $dataDiv).text(),
   					email : $("span.data_email", $dataDiv).text(),
   					empNo : $("span.data_empNo", $dataDiv).text(),
   					mobile : $("span.data_mobile", $dataDiv).text(),
   					group : $("span.data_group", $dataDiv).text(),
   					sapId : $("span.data_sapId", $dataDiv).text()
   					
   				});
    			i++;
	    	});
			
			var expVer = navigator.appVersion;

			if(expVer.indexOf("11.") > 0){
				iKEP.returnUserPopup(data);	
			}else{
			  iKEP.returnPopup(data);	
			}
		      
	    });
		
		$jq("#cancelBtn").click(function() {
			iKEP.closePop();			
		});
		
	});
	
})(jQuery);
	
</script>

<!--popup Start-->
<div id="popup">

	<!--popup_title Start-->
	<div id="popup_title_2">
        <div class="popup_bgTitle_l"></div>
		<h1><ikep4j:message pre='${preHeader}' key='title' /></h1>
		<a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
		<div class="popup_bgTitle_l"></div>
	</div>
	<!--//popup_title End-->		
	
	<!--popup_contents Start-->
	<div id="popup_contents">
	
		<!--blockListTable Start-->
		<div class="blockListTable">
				<div id="listDiv">					
					<form id="searchForm" method="post" action="" onsubmit="return false">  
						<spring:bind path="searchCondition.searchColumn">  
							<input name="${status.expression}" id="${status.expression}" value="${status.value}" type="hidden"  />	
						</spring:bind>		
						<spring:bind path="searchCondition.searchWord">  					
							<input name="${status.expression}" id="${status.expression}" value="${status.value}" type="hidden"  />
						</spring:bind>
					</form>					
				</div>
		</div>
		<!--//blockListTable End-->
		
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a class="button" href="#" id="applyBtn"><span><ikep4j:message pre='${preButton}' key='apply' /></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	
	</div>
	<!--//popup_contents End-->
 
	<!--popup_footer Start-->
	<div id="popup_footer"></div>
	<!--//popup_footer End-->

</div>
<!--//popup End-->