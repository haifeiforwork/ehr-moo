<%--
=====================================================
* 기능 설명 : 기안 의견
* 작성자    : approval
=====================================================
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preList"            value="ui.approval.apprForm.list" />
<c:set var="preListMessage"     value="message.approval.apprForm.list" />    
<c:set var="preButton"          value="ui.approval.common.button" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script  type="text/javascript">

<!--// 

	(function($) {
		
		var dialogWindow;    
		
        
	    //- onload시 수행할 코드
        $(document).ready(function() {   
           
            var validOptions = {
                rules  : {
                    registerMessage : {
                        maxlength  : 300
                    }
                },
                messages : {
                    registerMessage : { 
                        maxlength : "<ikep4j:message pre='${preListMessage}' key='changeReason.maxlength'/>"
                    }
                },    
                notice : {
                },      
    	        submitHandler : function(form) {
    	            if($("#mainForm textarea[name=registerMessage]").val()==""){
    	                if(!confirm("<ikep4j:message pre='${preListMessage}' key='registerMessage.noContents'/>")) return false;
    	            }
    	            
    	            dialogWindow.callback($("#mainForm textarea[name=registerMessage]").val());
    	            dialogWindow.close();
                }
    		};	   
    		
           //- caller  
           fnCaller = function (params, dialog) {
				dialogWindow = dialog;
			};
			
			$("#mainForm textarea[name=registerMessage]").focus();
			
    		//- save button  
            $("#saveButton").click(function(){
                 $("#mainForm").trigger("submit");
            });
            
            //- close button  
            $("#cancelButton").click(function(){
    	        dialogWindow.close();
            });
            
            new iKEP.Validator("#mainForm", validOptions);
        });

        
	})(jQuery);  

//-->
</script>

<h1 class="none">contnet area</h1>
<div id="guideConFrame">
	<div class="blockBlank_10px"></div>
	
	<!--subTitle_2 Start-->
	<div class="subTitle_2 noline">
		<h3><ikep4j:message pre='${preList}' key='registMessage'/></h3>
	</div>
	<!--//subTitle_2 End-->
	
	<!--blockDetail Start-->
	<div class="blockDetail">
    	<form id="mainForm" name="mainForm" method="post">
    		<table summary="">
    			<caption></caption>
    			<tbody>
    				<tr>
    					<td>
    						<textarea name="registerMessage" class="inputbox w100" rows="8"></textarea>
    					</td>
    		        </tr>
    			</tbody>
    		</table>
    	</form>
	</div>
	<!--//blockDetail End-->
	
	<!--blockButton Start-->
	<div id="mainFormButton" class="blockButton"> 
		<ul>
			<li><a id="saveButton"  class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='confirm'/></span></a></li>
			<li><a id="cancelButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
</div>