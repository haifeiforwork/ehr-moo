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
<c:set var="preButton"          value="ui.approval.common.button" />
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
            $("body").css("padding", "10px");
		    $("#printDiv").html(param.printHTML);
	        $("#fileDownloadDiv").empty();
        });
        
        printDoc = function(){
            $("body").css("padding", "0px");
            $("#buttonDiv").empty();
            window.print();
            setTimeout("window.close()", 1000);
        }
        
	})(jQuery);  

//-->
</script>
<div id="buttonDiv" class="blockButton"> 
	<ul>
	    <li><a class="button" href="#" onclick="printDoc();return false;"><span><ikep4j:message pre='${preButton}' key='print'/></span></a></li>
		<li><a class="button" href="#" onclick="self.close();"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
	</ul>
</div>
<div id="printDiv"></div>