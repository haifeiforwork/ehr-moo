<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preAlert"  value="message.portal.portlet.wiseSaying.excelPortletWiseSayingResult.alert" />
<c:set var="preMain"  value="ui.portal.portlet.wiseSaying.excelPortletWiseSayingResult.main" />
<c:set var="preView"  value="ui.portal.portlet.wiseSaying.excelPortletWiseSayingResult.view" />
<c:set var="preList"  value="ui.portal.portlet.wiseSaying.excelPortletWiseSayingResult.list" />
<c:set var="preButton"  value="ui.portal.portlet.wiseSaying.excelPortletWiseSayingResult.button" />  

<script type="text/javascript">
//<![CDATA[
(function($) {

	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		var $hh = $jq("#popup").height()+100;
		
		if($hh > 768) $hh = 768;
		
		window.resizeTo(800,$hh);
		
		$jq("#cancelBtn").click(function() {
			
			iKEP.closePop();
		});
		
		if(opener != null) {
			opener.getList();
		}
		
	});
	
})(jQuery);  
//]]>
</script>

<!--popup Start-->
<div id="popup">

	<!--popup_title Start-->
	<div id="popup_title_2">
        <div class="popup_bgTitle_l"></div>
		<h1><ikep4j:message pre="${preMain}" key="popupTitle" /></h1>
		<a href="#" onclick="iKEP.closePop(); return false;" title="<ikep4j:message pre='${preButton}' key='close' />">
			<span>
				<ikep4j:message pre="${preButton}" key="close" />
			</span>
		</a>
	</div>
	<!--//popup_title End-->
		
	<!--popup_contents Start-->
	<div id="popup_contents">
			
		<!--blockDetail Start-->
		<div class="blockDetail">
			<table summary="<ikep4j:message pre='${preView}' key='tableSummary' />">
				<caption></caption>	
		
				<tbody>
					<tr>
						<th scope="row" width="20%">
							<ikep4j:message pre="${preView}" key="totalCount" />
						</th>
						<td>
							${totalCount}
						</td>
					</tr>								
					<tr>
						<th scope="row">
							<ikep4j:message pre="${preView}" key="successCount" />
						</th>
						<td>
							${successCount}
						</td>
					</tr>	
					<tr>
						<th scope="row">
							<ikep4j:message pre="${preView}" key="failCount" />
						</th>
						<td>
							${failCount}
						</td>
					</tr>			
				</tbody>		
			</table>
		</div>
		<!--//blockDetail End-->
		
		<!--blockListTable Start-->
		<div class="blockListTable">	
			<table summary="<ikep4j:message pre="${preList}" key="tableSummary" />">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="5%">
							<ikep4j:message pre="${preList}" key="no" />
						</th>
						<th scope="col" width="10%">
							<ikep4j:message pre="${preList}" key="writerName" />
						</th>
						<th scope="col" width="10%">
							<ikep4j:message pre="${preList}" key="wiseSayingContents" />
						</th>
						<th scope="col" width="75%">
							<ikep4j:message pre="${preList}" key="errMsg" />
						</th>
					</tr>
				</thead>
				
				<tbody>
					<c:choose>
				    <c:when test="${empty wiseSayingList}">
					<tr>
						<td colspan="8" class="emptyRecord">
							<ikep4j:message pre="${preList}" key="noFail" />
						</td> 
					</tr>				        
				    </c:when>
				    <c:otherwise>
					<c:forEach var="wiseSaying" items="${wiseSayingList}" varStatus="status">
					<c:if test="${wiseSaying.successYn == 'N'}">
					<tr>
						<td>${status.count}</td>			
						<td class="textCenter">
							${wiseSaying.writerName}
						</td>
						<td class="textLeft">
							<div class="ellipsis">
							${wiseSaying.wiseSayingContents}
							</div>
						</td>
						<td class="textLeft">
							${wiseSaying.errMsg}
						</td>
					</tr>
					</c:if>
					</c:forEach>				      
				    </c:otherwise> 
					</c:choose>  																																																					
				</tbody>
			</table>		
		</div>
		<!--//blockListTable End-->			
												
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li>
					<a class="button_s" id="cancelBtn" href="#" onclick="return false;" title="<ikep4j:message pre='${preButton}' key='close' />">
						<span>
							<ikep4j:message pre="${preButton}" key="close" />
						</span>
					</a>
				</li>
			</ul>
		</div>
		<!--//blockButton End-->
	
	</div>
	<!--//popup_contents End-->

</div>
<!--//popup End-->