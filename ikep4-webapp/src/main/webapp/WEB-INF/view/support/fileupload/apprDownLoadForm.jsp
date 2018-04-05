<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.form.js"/>"></script>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.fileupload.header" /> 
<c:set var="preList"    value="ui.support.fileupload.list" />
<c:set var="preDetail"  value="ui.support.fileupload.detail" />
<c:set var="preButton"  value="ui.support.fileupload.button" /> 
<c:set var="preMessage" value="ui.support.fileupload.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" language="javascript">
<!--  

(function($) {
	
	
	
	
	var dialogWindow = null;
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		
		fnCaller = function(params, dialog){
			
			if(params) {
				$jq("#fileName").val(params.fileName);
				$jq("#htmlDocument").val(params.htmlDocument);
			}
			
			dialogWindow = dialog;
		}
		
		$jq("#submitBtn").click(function() {
			//alert($jq("#htmlDocument").val());	
			fileForm.submit();
			
		});
		
		$jq("#cancelBtn").click(function() {
			
			dialogWindow.close();
		});
		
		
	});
	
})(jQuery);  


//-->	
</script>

		
				<div class="blockBlank_10px"></div>		

				<!--popup_contents Start-->
				<div id="popup_contents">
					
	
						<form name="fileForm" id="fileForm" method="post" action="<c:url value="/support/fileupload/apprDownLoad.do" />" >
					            
					            <input type="hidden" name="fileName" id="fileName" value="${fileName}" title="fileName" />
					            <input type="hidden" name="htmlDocument" id="htmlDocument" value="${htmlDocument}" title="htmlDocument"/>
					           
					            <div>
					            <input type="radio" name="fileType" id="fileType" title="fileType" value="pdf" checked="checked"/> PDF 
					            &nbsp;&nbsp;&nbsp;
					            <input type="radio" name="fileType" id="fileType" title="fileType" value="mht"/> MHT
					            
					            </div>
					    </form>

					<div class="blockBlank_10px"></div>			
							
					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<li><a class="button" id="submitBtn" href="#a"><span>Download</span></a></li>
						</ul>
					</div>
					<!--//blockButton End-->
					
				
				</div>
				<!--//popup_contents End-->
		
		
