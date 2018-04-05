<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>  
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.fileupload.header" /> 
<c:set var="preList"    value="ui.support.fileupload.list" />
<c:set var="preDetail"  value="ui.support.fileupload.detail" />
<c:set var="preButton"  value="ui.support.fileupload.button" /> 
<c:set var="preMessage" value="ui.support.fileupload.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
<%-- 메시지 관련 Prefix 선언 End --%> 
<script type="text/javascript" language="javascript">  
(function($) { 
	// onload시 수행할 코드
	$(document).ready(function() {
		// 업로드 가능 용량 표기 위해
		$maxFileSize = $("input[name=maxFileSize]");
		if($maxFileSize.is("*")) {	
			$("<p/>").prependTo("form")
				.text("* <ikep4j:message key='ui.support.fileupload.maxFileSize' /> : " + ($maxFileSize.val()/1024) + "KByte");
			
			$maxFileSize.remove();
		}
		
		$("#submitBtn").click(function() {    
			
			var fileName = $jq("input[name=file]").val();
			
			if($("input[name=file]").val() == "") {
				alert('<ikep4j:message pre='${preMessage}' key='file.select' />');
				return;
			}
			
			if(!fileName.match(/(.jpg|.JPG|.jpeg|.JPEG|.gif|.GIF|.png|.PNG|.bmp|.BMP)$/)) {
				alert('<ikep4j:message pre='${preMessage}' key='file.image' />');
				return;
			}
			
			$jq("#fileForm").submit(); 
		});  
	});  
})(jQuery);  
</script>  
<!--popup_contents Start-->
<div id="popup_contents" style="padding-bottom:8px;"> 
	<form name="fileForm" id="fileForm" method="post" action="<c:url value="/support/fileupload/uploadFileForEditor.do"/>" enctype="multipart/form-data">
	    <input type="file" name="file" id="file" class="file"  size="71"/>  
		<c:forEach items="${parameterMap}" var="status"> 
			 <input type="hidden" name="${status.key}" value="${status.value}"/>  
		</c:forEach>
	</form> 
	<!--blockButton Start-->
	<div class="blockButton mt10"> 
		<ul>
			<li><a class="button" id="submitBtn" href="#"><span><ikep4j:message pre='${preButton}' key='upload' /></span></a></li>
		</ul>
	</div>
	<!--//blockButton End--> 
</div>
<!--//popup_contents End--> 