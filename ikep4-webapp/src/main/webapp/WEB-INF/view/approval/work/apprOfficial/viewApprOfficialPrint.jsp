<%--
=====================================================
* 기능 설명 : 기안문 인쇄
* 작성자    : wonchu
=====================================================
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/approval/work/apprDoc.css"/>"/>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<%-- 메시지 관련 Prefix 선언 End --%>
<style>
    #formButtonDiv{display:none;}
    #fileDownloadDiv{display:none;}
    #fileListDiv{display:block;}
    
</style>      
<script  type="text/javascript">

<!--// 

	(function($) {
        
	    //- onload시 수행할 코드
        $(document).ready(function() {   
            var param = iKEP.getPopupArgument();
		    $("#guideConFrame").html(param.printHTML);
	        $("#fileDownloadDiv").empty();
	        //window.print();
            //setTimeout("window.close()", 1000);
        });
		
        printDoc = function(){
            $("#buttonDiv").empty();
            window.print();
            setTimeout("window.close()", 1000);
        }
        
	})(jQuery);  

//-->
</script>


				<div id="buttonDiv" class="blockButton"> 
					<ul>
					    <li><a class="button" href="#" onclick="printDoc();return false;"><span>인쇄</span></a></li>
						<li><a class="button" href="#" onclick="self.close();"><span>취소</span></a></li>
					</ul>
				</div>
			
				<div class="Approval" id="guideConFrame">
				</div>
