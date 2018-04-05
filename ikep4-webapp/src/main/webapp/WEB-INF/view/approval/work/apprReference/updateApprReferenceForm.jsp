<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  	value="ui.approval.work.apprReference.ReferenceView.header" />
<c:set var="preView"  		value="ui.approval.work.apprReference.ReferenceView.view" />
<c:set var="preList"  		value="ui.approval.work.apprReference.ReferenceView.list" />
<c:set var="preButton"		value="ui.approval.work.apprReference.ReferenceView.button" />
<c:set var="preMessage"		value="message.approval.work.apprReference.ReferenceView" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript">
	<!--
	var dialogWindow = null;
	var fnCaller;
	
	
	(function($){
		
		fnCaller = function (params, dialog) {
			if(params) {
			}

			dialogWindow = dialog;
			$("#btnClose").click(function() {
				dialogWindow.close();
			});
		};


		//입력값 확인
		var validOptions = {
			submitHandler	: function(form) {
				var apprId = $("#myForm input[name=apprId]").val();
				
				$("body").ajaxLoadStart("button");

				if (!confirm("<ikep4j:message pre='${preMessage}' key='confirm'/>")) {
					$("body").ajaxLoadComplete();
					return false;
				}
				

				$jq.ajax({     
					url : '<c:url value="/approval/work/apprReference/updateApprReference.do" />',     
					data :  $jq("#myForm").serialize(),
					type : "post",   
					success : function(result) {      
						if(result == "OK") {
							$("body").ajaxLoadComplete();
							dialogWindow.callback(result);
							dialogWindow.close();
						}else if(result == "EXIST") { // 하나의 결재 문서에 같은 사람을 요청할수 없음.
							alert("<ikep4j:message pre='${preMessage}' key='exist'/>");
							$("body").ajaxLoadComplete();
						}
					},
					error : function(event, request, settings){
						alert("error");
						$("body").ajaxLoadComplete();
					}
				   });

                  
            },
            rules			: {
            	receiveMessage	: { 
            		required: true, 
            		maxlength: 60 
            	}            		
            },
            messages		: {
            	receiveMessage	: {
					direction 		: "bottom",
                	required		: "<ikep4j:message key='NotEmpty.apprReference.receiveMessage' />",
                	maxlength		: "<ikep4j:message key='Size.apprReference.receiveMessage' arguments='60'/>"
     			}
            }
		}; 
		
		$(document).ready(function(){
			
			new iKEP.Validator("#myForm", validOptions);
			
			$("#saveReqButton").click(function() {   
				$("#myForm").submit(); 
				return false; 
			});
            //- close button  
            $("#btnClose").click(function(){
    	        dialogWindow.close();
            });
            
			
		});
	})(jQuery);
	//-->
</script>
<form id="myForm" name="myForm" method="post" action="">

<spring:bind path="apprReference.apprId">
	<input type="hidden" name="${status.expression}" value="${apprId}"/>
</spring:bind>

	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${preView}' key='summary'/>">
			<caption></caption>
			<tbody>

				<tr>
					<spring:bind path="apprReference.receiveMessage">
					<th scope="row" width="20%"><ikep4j:message pre='${preView}' key='${status.expression}'/></th>
					<td>
						<textarea name="${status.expression}" class="tabletext  w100" title="<ikep4j:message pre='${preView}' key='${status.expression}'/>" cols="200" rows="7">${apprReference.receiveMessage}</textarea>
					</td>
					</spring:bind>
				</tr>

			</tbody>
		</table>
		
	</div>
	<!--//blockDetail End-->
	
	<div class="clear"></div>
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="saveReqButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			<li><a id="btnClose"   		class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='close'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
	
</form>			

<!--//mainContents End-->