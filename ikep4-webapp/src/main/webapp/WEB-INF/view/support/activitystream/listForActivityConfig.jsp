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

<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript" src="<c:url value="/base/js/units/support/favorite/favorite.js"/>"></script>
 
<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	var validOptions = {
		    rules : {
		    	configValue :    {
		            required : true,
		            number : true,
		            range : [0, 1000000]
		        }
		    },
		    messages : {
		    	configValue : {
		            direction : "bottom",
		            required : "<ikep4j:message key="NotEmpty.configValue.title" />",
		            number : "<ikep4j:message key="Number.configValue.title" />",
		            range : "<ikep4j:message key="Range.configValue.title" />"
		        }
		    },
		    submitHandler : function(form) {
		    	
		    	if(confirm('<ikep4j:message pre='${preMessage}' key='save' />')) {
					$jq("#searchForm")[0].submit();
		    	}
		    }
		 };
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		new iKEP.Validator("#searchForm", validOptions);
		
		$jq("#applyBtn").click(function() {
			
			$jq("#searchForm").submit();	
			
		});
		
		$jq("#checkedAll").click(function() {

	 		if ($jq("#checkedAll").is(":checked")) {
	 			$jq('input[name=activityCodeList]:not(checked)').attr("checked", true);
			}else{
		   		$jq('input[name=activityCodeList]:checked').attr("checked", false);
		    }

	    });
		
		
	});
	
})(jQuery);  

//-->
</script>
		
		<div class="conPPS">
		
				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${preList}' key='maxSize' /></h2>
				</div>
				<!--//pageTitle End-->
				
				
				<form id="searchForm" method="post" action="<c:url value='/support/activitystream/saveActivityConfig.do'/>" >  
					
				<!--Contents Start-->
				<!--blockListTable Start-->
				
						<div class="blockListTable" >	
						
							<table summary="<ikep4j:message pre='${preList}' key='main.title' />" >
								<caption></caption>
								
								<tbody id="listDiv">
								
									<tr class="msg_read">
									
										<td class="textLeft" width="50%">
										
											<ikep4j:message pre='${preList}' key='maxSizeCount' />
											&nbsp;
											<input 
											name="maxSaveValue" 
											type="text" 
											class="inputbox" 
											value="${activityCode.maxSaveValue}" 
											size="10" 
											title="<ikep4j:message pre='${preList}' key='maxSizeCount' />"
											/>
											&nbsp;
											<a class="button" href="#a" id="applyBtn"><span><ikep4j:message pre='${preButton}' key='submit' /></span></a>
										
										</td>
										
										
									</tr>
								
								</tbody>
							</table>
						
						
						</div>
						
						
				<!--//blockListTable End-->	
				
				</form>		
				
				<span><ikep4j:message pre='${preMessage}' key='maxSize' /></span>
				
		</div>		
				
