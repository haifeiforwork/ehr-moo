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
		
		
		fnCaller = function(param, dialog){
			dialogWindow = dialog;
		}
		
		$jq("#submitBtn").click(function() {
				
				var fileName = $jq("input[name=file]").val();
				
				if(fileName == "") {
					alert('<ikep4j:message pre='${preMessage}' key='file.select' />');
					return;
				}
				
				if($jq("#imageYn").val() == '1') {
					if(!fileName.match(/(.jpg|.JPG|.jpeg|.JPEG|.gif|.GIF|.png|.PNG|.bmp|.BMP)$/)) {
						alert('<ikep4j:message pre='${preMessage}' key='file.image' />');
						return;
					}
				}
				
				var options = {
					beforeSubmit:function(){
	    			},
	    			success:function(data){
	    				
	    				try {
		    				data = $jq(data).text().replaceAll("<PRE>","");
		    				var result = $.parseJSON(data);
		    				
		    				setTimeout(function() {
		    					if(result.status == "success") {
		    						dialogWindow.fnCallback(result.status, result.fileId, result.fileName, result.message, result.targetId);
			    					dialogWindow.close();
		    					}
		    					else {
		    						alert(result.message);
		    						dialogWindow.close();
		    					}
		    				}, 200);
	    				}	
	    				catch(e)
	    				{
	    					alert('<ikep4j:message key="support.fileupload.message.upload.failMaxSize" />');	
	    					dialogWindow.close();
	    				}
	    				
	    			},
	    			error : function() {alert('error');},
	    			url:'<c:url value="/support/fileupload/uploadFile.do"/>',
	    			type:'post',
	    			dataType:'html'
	    			
	    		};
	    		
				try {
					
	    			$("#fileForm").ajaxSubmit(options);
				}
				catch(e)
				{
				}
			
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
					
	
						<form name="fileForm" id="fileForm" method="post" action="" enctype="multipart/form-data">
					            
					            <input type="hidden" name="targetId" id="targetId" value="${targetId}" title="targetId" />
					            <input type="hidden" name="editorAttachYn" id="editorAttachYn" value="${editorAttachYn}" title="editorAttachYn"/>
					            <input type="hidden" name="imageYn" id="imageYn" value="${imageYn}" title="imageYn"/>
					            <div>
					            <input type="file" name="file" id="file" class="file" style="width:100%;" title="file"/>
					            </div>
					    </form>

					<div class="blockBlank_10px"></div>			
							
					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<li><a class="button" id="submitBtn" href="#a"><span><ikep4j:message pre='${preButton}' key='upload' /></span></a></li>
						</ul>
					</div>
					<!--//blockButton End-->
					
				
				</div>
				<!--//popup_contents End-->
		
		
