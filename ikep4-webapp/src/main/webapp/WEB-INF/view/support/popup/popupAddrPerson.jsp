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
			url : '<c:url value="getAddrPersonList.do" />?isMulti=${isMulti}',     
			data :  $jq("#searchForm").serialize(),     
			type : "post",     
			success : function(result) {         
				$jq("#listDiv").html(result);
			} 
		});       
		
	};
		

	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		getList();
		
		$jq("#applyBtn").click(function() {

			var $sel = $jq("input[name=chk]:checked");
			if($sel.length <1) {
				alert('<ikep4j:message pre='${preMessage}' key='select' />');
				return;	
			}
				  
			      var data = new Array();
			      var i=0;
			      $jq("input[name=chk]").each(
			    		function() {
			    			if(this.checked) {
			    				data.push({personId: $jq("input[name=chk01]").get(i).value, personName: $jq("input[name=chk02]").get(i).value, jobTitle : $jq("input[name=chk03]").get(i).value, teamName : $jq("input[name=chk04]").get(i).value, companyName : $jq("input[name=chk05]").get(i).value,  mail : $jq("input[name=chk06]").get(i).value,  mobile : $jq("input[name=chk07]").get(i).value});
			    			}
			    			i++;
			    		}
			      );
			      
			      iKEP.returnPopup(data);
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
					<h1><ikep4j:message pre='${preHeader}' key='titleForOut' /></h1>
					<a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
				</div>
				<!--//popup_title End-->
				
				
				<!--popup_contents Start-->
				<div id="popup_contents">
				
	
				
					<!--blockListTable Start-->
					<div class="blockListTable">
										
							<div id="listDiv">
							
							</div>
							
							
					</div>
					<!--//blockListTable End-->			
					
					
					
					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<li><a class="button" href="#" id="applyBtn"><span><ikep4j:message pre='${preButton}' key='apply' /></span></a></li>
							<li><a class="button" href="#" id="cancelBtn"><span><ikep4j:message pre='${preButton}' key='cancel' /></span></a></li>
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
		
	