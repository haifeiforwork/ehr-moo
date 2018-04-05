<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 


<c:set var="preDetail"  value="ui.portal.excelupload.detail" />
<c:set var="preButton"  value="ui.portal.excelupload.button" /> 
<c:set var="preButton2"  value="ui.lightpack.planner.common.button" /> 
<c:set var="preMessage" value="ui.portal.excelupload.message" />
<c:set var="userListPrefix" value="message.portal.admin.member.user.list"/>
<c:set var="userUiPrefix" value="ui.portal.admin.member.user"/>
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />


<script type="text/javascript" language="javascript">

(function($) {
	
	
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
	
	// 
	realtyGuide = function() {
		$jq.ajax({
		    url : '<c:url value="/support/customer/customerRealty/chkRealty.do"/>',
		    type : "get",
		    success : function(result) {
		    	if( result > 0 ){
		    		$("#saveForm").submit();
		    	}else{
		    		alert("고객정보가 없습니다.");
		    	}
		    }
		}).error(function(event, request, settings) { alert("error"); });
	};
	
})(jQuery);  


	
</script>

		<!--popup Start-->
		<div id="popup">
		
				<!--popup_title Start-->
				<div id="popup_title_2">
                    <div class="popup_bgTitle_l"></div>
					<h1>부동산 정보 업로드</h1>
					<a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
				</div>
				<!--//popup_title End-->
				
				
				<!--popup_contents Start-->
				<div id="popup_contents">
				
							
					<!--blockDetail Start-->
					<div class="blockDetail">
						<form id="saveForm" method="post" action="<c:url value='/support/customer/customerRealty/downloadExcelRealty.do'/>">
							<input name="targetUserId" type="hidden" value="${user.userId}" />
						</form>				
					
						<form id="fileForm" method="post" action="<c:url value="/support/customer/customerRealty/realtyExcelUpload.do"/>" enctype="multipart/form-data">
							
							<input type="hidden" name="token" value="${token}"/>

							<!--directive Start-->
							<div class="directive"> 
								<ul>
									<li><span style="color:#bf000f"><ikep4j:message pre="${userListPrefix}" key="directive.download" /></span></li>	
									<li><span style="color:#bf000f"><ikep4j:message pre="${userListPrefix}" key="directive.notice" /></span></li>	
									<li><span style="color:#bf000f">[고객_목록.xls]를 다운받아 참고하여 [고객 ID]값을 입력하십시오.</span></li>	
								</ul>
							</div>
							<!--//directive End-->
							
							<table summary="<ikep4j:message pre="${preButton2}" key="companyExcel" />">
							<caption></caption>
								<colgroup>
									<col width="25%"/>
									<col width="75%"/>
								</colgroup>
					
							<tbody>
							
								<tr>
									<th scope="row" ><ikep4j:message pre='${preDetail}' key='fileForm' /></th>
									<td>
										<a href="<c:url value="/base/excel/realty_sample.xls"/>">
											<img  src="<c:url value='/base/images/icon/ic_xls.gif' />"> realty_sample.xls
										</a>&nbsp;&nbsp;
										<a href="<c:url value='/support/customer/customerRealty/downloadExcelRealty.do'/>">
											<img  src="<c:url value='/base/images/icon/ic_xls.gif' />"> 고객_목록.xls
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
		
		
		
		
	