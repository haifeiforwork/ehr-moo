<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<c:set var="preHeader"  value="ui.servicepack.survey.header.read" /> 
<c:set var="preList"    value="ui.servicepack.survey.list" />
<c:set var="preButton"  value="ui.servicepack.survey.common.button" /> 
<c:set var="preMessage"  value="message.servicepack.survey" /> 

<ikep4j:message  pre='${preMessage}' key='file' var="file"/>
<ikep4j:message  pre='${preMessage}' key='contents' var="contents"/>
<ikep4j:message  pre='${preMessage}' key='endDate' var="endDate"/>
<ikep4j:message  pre='${preMessage}' key='startDate' var="startDate"/>
<ikep4j:message  pre='${preMessage}' key='surveyTarget' var="surveyTarget"/>
<ikep4j:message  pre='${preMessage}' key='targetGroupList' var="targetGroupList"/>
<ikep4j:message  pre='${preMessage}' key='oppositionReason' var="oppositionReason"/>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
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


	$jq(document).ready(function() { 
		
		var validOptions = {
			    rules : {
			    	oppositionReason : {
			    						 required : true,
			    						 maxlength:300
			    						}
			    },
			    messages : {
			    	oppositionReason : {
			            direction : "top",
			            required : "<ikep4j:message  pre='${preMessage}' key='required' arguments='${oppositionReason}'/>",
			            maxlength : "<ikep4j:message  pre='${preMessage}' key='maxlength' arguments='${oppositionReason},300'/>"
			        }
			    },
			    notice : {
			    	oppositionReason : '<ikep4j:message pre='${preList}' key="oppositionReason"/>'
			    },
			    submitHandler : function(form) {
			    	if( confirm("<ikep4j:message pre='${preMessage}' key='approve.reject'/>") )
			 		{
			 			$jq.post("<c:url value='/servicepack/survey/approve/rejectSubmit.do'/>", $jq("#surveyForm").serialize() )
			 	        .success(function(data) { 
			 	        		dialogWindow.callback(data);
			 	        })
			 	        .error(function(event, request, settings) { alert("error"); });
			 		}
				}
			 };
	
		new iKEP.Validator("#surveyForm", validOptions);
		
	 	$jq("#rejectButton").click(function() {
	 		$("#surveyForm").trigger("submit");
	 		return false; 
	 	});
	});  

})(jQuery);	
//-->
</script>
<!--popup Start-->
<div>
	<!--popup_contents Start-->
	<div id="popup_contents" style="margin-top:30px;">
	
	<form id="surveyForm" method="post">
	    <input type="hidden" name="surveyTitle" value="${survey.title}"/>
		<input type="hidden" name="surveyId" value="${survey.surveyId }" title=""/>
		<input type="hidden" name="surveyStatus" value="2" title=""/>
		<!--blockDetail Start-->
		<div>
			<spring:bind path="survey.oppositionReason">
			  <textarea 
				name="${status.expression}" 
				id="${status.expression}" 
				class="inputbox w100"
				cols="" 
				rows="5" 
				title="<ikep4j:message pre='${preList}' key='${status.expression}' />">${status.value}</textarea>
				<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
			</spring:bind>			
		</div>
		<!--//blockDetail End-->																																			
		<div class="clear"></div>
		
		<!--blockButton Start-->
		<div class="blockButton" style="text-align:center;margin-top:10px;"> 
			<ul> 
				<li><a id="rejectButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='reject'/></span></a></li>
				<li><a id="btnClose" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
			</ul>
		</div>
	</form>
	</div>
</div>
<!--//popup End-->


		



