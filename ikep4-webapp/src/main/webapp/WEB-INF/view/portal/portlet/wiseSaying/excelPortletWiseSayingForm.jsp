<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preAlert"  value="message.portal.portlet.wiseSaying.excelPortletWiseSayingForm.alert" />
<c:set var="preMain"  value="ui.portal.portlet.wiseSaying.excelPortletWiseSayingForm.main" />
<c:set var="preForm"  value="ui.portal.portlet.wiseSaying.excelPortletWiseSayingForm.form" />
<c:set var="preButton"  value="ui.portal.portlet.wiseSaying.excelPortletWiseSayingForm.button" />  

<script type="text/javascript">
//<![CDATA[
(function($) {

	// onload시 수행할 코드
	$jq(document).ready(function() { 

		$jq('#submitBtn').click( function() { 
			
			if($jq("input[name=file]").val() == "") {
				alert("<ikep4j:message pre='${preAlert}' key='selectExcelFile' />");
				return;
			}
			
			$jq("#fileForm").submit();
			
		});
		
		$jq("#cancelBtn").click(function() {
			iKEP.closePop();
		});
		
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
			<form id="fileForm" method="post" action="<c:url value='excelPortletWiseSayingUpload.do'/>" enctype="multipart/form-data">
				<input type="hidden" name="token" value="${token}"/>
			
				<table summary="<ikep4j:message pre='${preForm}' key='tableSummary' />">
					<caption></caption>	
			
					<tbody>
						<tr>
							<th scope="row" width="15%"><ikep4j:message pre="${preForm}" key="fileForm" /></th>
							<td>
								<a href="<c:url value='/base/excel/wiseSaying_sample.xls'/>">wiseSaying_sample.xls</a>
								&nbsp;<ikep4j:message pre="${preForm}" key="note" />
							</td>
						</tr>								
						
						<tr>
							<th scope="row" width="85%"><ikep4j:message pre="${preForm}" key="fileUpload" /></th>
							<td>
								<input type="file" name="file" id="file" class="file"  size="68"/>
							</td>
						</tr>		
					</tbody>					
				</table>
			</form>	
		</div>
		<!--//blockDetail End-->
												
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li>
					<a class="button_s" id="submitBtn" href="#" onclick="return false;" title="<ikep4j:message pre='${preButton}' key='process' />">
						<span>
							<ikep4j:message pre="${preButton}" key="process" />
						</span>
					</a>
				</li>
				<li>
					<a class="button_s" id="cancelBtn" href="#" title="<ikep4j:message pre='${preButton}' key='close' />">
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