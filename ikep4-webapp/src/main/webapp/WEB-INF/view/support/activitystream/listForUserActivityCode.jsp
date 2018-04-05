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
	
	
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		$jq("#applyBtn").click(function() {
			
			if($jq('input[name=activityCodeList]:checked').size() < 1) {
				alert('<ikep4j:message pre='${preMessage}' key='selectMore' />');
				return;
			}
			
			if(confirm('<ikep4j:message pre='${preMessage}' key='save' />')) {
				$jq("#searchForm")[0].submit();		
			}
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
					<h2><ikep4j:message pre='${preList}' key='personal' /></h2>
				</div>
				<!--//pageTitle End-->
				
				
				<span><ikep4j:message pre='${preMessage}' key='codeList' /></span>
				
				<form id="searchForm" method="post" action="<c:url value='/support/activitystream/saveUserActivityCode.do'/>" >  
				
				
				<!--Contents Start-->
				<!--blockListTable Start-->
				
						<div class="blockListTable" >	
						
							<table summary="<ikep4j:message pre='${preList}' key='main.title' />" >
								<caption></caption>
								
								<thead>
									<tr>
										<th scope="col" width="10%">
											<input type="checkbox" name="checkedAll" id="checkedAll" title="all"/>
										</th>
										<th scope="col" width="90%">
											<ikep4j:message pre='${preList}' key='activityName' />
										</th>
									</tr>
								</thead>
								
								<tbody id="listDiv">
								
									<c:choose>
									    <c:when test="${empty userActivityCodeList}">
										 </c:when>
									    <c:otherwise>
											<c:forEach var="activity" items="${userActivityCodeList}">
											
											<tr class="msg_read">
												<td class="tdFirst" width="10%">
													<c:choose>
									    				<c:when test="${activity.userId == null}">
									    					<input type="checkbox" name="activityCodeList" value="${activity.activityCode}" title="activity code"/>
														</c:when>
									    				<c:otherwise>
									    					<input type="checkbox" name="activityCodeList" value="${activity.activityCode}" checked="checked" title="activity code"/>
									    				</c:otherwise>
									    			</c:choose>
												</td>
												<td class="textLeft" width="90%">
													${activity.activityName}
												</td>
												
											</tr>
									
											</c:forEach>				      
									    </c:otherwise> 
									</c:choose>  
								
								</tbody>
							</table>
						
						
						</div>
						
						
				<!--//blockListTable End-->	
				
				</form>		
				
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li><a class="button" href="#a" id="applyBtn"><span><ikep4j:message pre='${preButton}' key='submit' /></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->	
				
		</div>				
