<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.portal.excelupload.header" /> 
<c:set var="preList"    value="ui.portal.excelupload.list" />
<c:set var="preDetail"  value="ui.portal.excelupload.detail" />
<c:set var="preButton"  value="ui.portal.excelupload.button" /> 
<c:set var="preMessage" value="ui.portal.excelupload.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
<c:set var="prefix" value="message.portal.admin.group.group.excelForm"/>
<%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript" language="javascript">

(function($) {
	
	
	
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		
		
		$jq('#submitBtn').click( function() { 
			
			$jq("#fileForm").trigger("submit");
			
		});
		
		$jq("#cancelBtn").click(function() {
			
			iKEP.closePop();
		});
		
		var validOptions = {
				rules : {
					file : {
						required : true,
						accept : "xls"
					}
				},
				messages: {
					file :	{
						required : "<ikep4j:message key="NotEmpty.excel.file" />"
						,accept : "<ikep4j:message key="Pattern.excel.file" />"
					}
				}
		}
		
		var validator = new iKEP.Validator("#fileForm", validOptions);
		
	});
	
})(jQuery);  


	
</script>

		<!--popup Start-->
		<div id="popup">
		
				<!--popup_title Start-->
				<div id="popup_title_2">
                    <div class="popup_bgTitle_l"></div>
					<h1><ikep4j:message pre='${preDetail}' key='title.group' /></h1>
					<a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
				</div>
				<!--//popup_title End-->
				
				
				<!--popup_contents Start-->
				<div id="popup_contents">
				
							
					<!--blockDetail Start-->
					<div class="blockDetail">
											
					
						<form id="fileForm" method="post" action="<c:url value="/portal/admin/group/group/excelUpload.do"/>" enctype="multipart/form-data">
						
							<input type="hidden" name="token" value="${token}"/>
							
							<!--directive Start-->
							<div class="directive"> 
								<ul>
									<li><span><ikep4j:message pre="${preMessage}" key="download" /></span></li>	
									<li><span><ikep4j:message pre="${preMessage}" key="setText" /></span></li>	
								</ul>
							</div>
							<!--//directive End-->
							
							<table summary="<ikep4j:message pre='${preDetail}' key='title.group' />">
							<caption></caption>	
								<colgroup>
									<col width="25%"/>
									<col width="75%"/>
								</colgroup>
							<tbody>
							
								<tr>
									<th scope="row" ><ikep4j:message pre='${preDetail}' key='fileForm' /></th>
									<td>
										<a href="<c:url value="/base/excel/group_sample.xls"/>">
											<ikep4j:message pre="${preDetail}" key="templateName" />
										</a>
									</td>
								</tr>								
								
								<tr>
									<th scope="row" ><ikep4j:message pre='${preDetail}' key='fileUpload' /></th>
									<td>
										<input type="file" name="file" id="file" class="file" />
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
							<li><a class="button" id="submitBtn" href="#"><span><ikep4j:message pre='${preButton}' key='process' /></span></a></li>
							<li><a class="button" id="cancelBtn" href="#"><span><ikep4j:message pre='${preButton}' key='close' /></span></a></li>
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
		
		
		
		
	