<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.servicepack.survey.header.ing" /> 
<c:set var="preList"    value="ui.servicepack.survey.list" />
<c:set var="preButton"  value="ui.servicepack.survey.common.button" /> 
<c:set var="preMessage"  value="message.servicepack.survey" /> 

<ikep4j:message  pre='${preMessage}' key='file' var="file"/>
<ikep4j:message  pre='${preMessage}' key='contents' var="contents"/>
<ikep4j:message  pre='${preMessage}' key='endDate' var="endDate"/>
<ikep4j:message  pre='${preMessage}' key='startDate' var="startDate"/>
<ikep4j:message  pre='${preMessage}' key='surveyTarget' var="surveyTarget"/>
<ikep4j:message  pre='${preMessage}' key='targetGroupList' var="targetGroupList"/>
<ikep4j:message  pre='${preMessage}' key='title' var="title"/>
<ikep4j:message  pre='${preMessage}' key='approverId' var="approverId"/>
<ikep4j:message  pre='${preMessage}' key='oppositionReason' var="oppositionReason"/>
<ikep4j:message  pre='${preMessage}' key='etcoppositionReason' var="etcoppositionReason"/>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<script type="text/javascript">
<!--   
var oppositionReasonRequed = '<ikep4j:message  key="message.servicepack.survey.reject.oppositionReason.requed" /> ';
var etcRequed = '<ikep4j:message  key="message.servicepack.survey.reject.etc.requed" /> ';
var requed = '<ikep4j:message   key="message.servicepack.survey.reject.requed" /> ';


(function($) {
	$jq(document).ready(function() { 
		var validOptions = {
			    rules : {
			    	surveyRejectReason1 : {required : true},
			    	surveyRejectReason2 :  {required :function(element){
				    			return $(":radio:checked").val() == '3';
				    	   }
			    	}
			    },
			    messages : {
			    	surveyRejectReason1 : {
			            direction : "bottom",
			            required : "<ikep4j:message  pre='${preMessage}' key='required' arguments='${oppositionReason}'/>" 
			        },
			        surveyRejectReason2 : {
			            direction : "bottom",
			            required : "<ikep4j:message  pre='${preMessage}' key='required' arguments='${etcoppositionReason}'/>"
			        }
			    },
			    notice : {
			    	surveyRejectReason1 : '<ikep4j:message pre='${preMessage}' key="oppositionReason"/>',
			    	surveyRejectReason2 : '<ikep4j:message pre='${preMessage}' key="etcoppositionReason"/>'
			    },
			    submitHandler : function(form) {
			    	form.submit();
				}
			 };
	
		new iKEP.Validator("#rejectForm", validOptions);
		
		$("#rejectButton").click(function() {
			$("#rejectForm").trigger("submit");
			return false; 
		});	
	});  
})(jQuery); 		
//-->
</script>
 <%-- 메시지 관련 Prefix 선언 End --%>
 <script type="text/javascript" src="<c:url value='/base/js/survey/response.js'/>"></script>
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
<!-- pageTitle Start -->
<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
</div> 
<!-- pageTitle End --> 
<!-- blockDetail Start -->
<div class="blockDetail">
 <form id="surveyForm" method="post" action="<c:url value='/servicepack/survey/response/responseSubmit.do'/>">
  <input name="surveyId" title="" type="hidden" value="${survey.surveyId}"  title=""/>
  <%@ include file="/WEB-INF/view/servicepack/survey/includeSurvey.jsp"%>
 </form>
 <!-- blockDetail End --> 
<!-- tableBottom Start -->
<div class="tableBottom">										
	<!-- blockButton Start -->
	<div class="blockButton"> 
		<ul>
			<li><a id="saveButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='reponseSubmit'/></span></a></li>
			<li><a id="listButton" class="button" href="<c:url value='/servicepack/survey/surveyIngList.do'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		 </ul>
	</div>
	<!-- blockButton End --> 
</div>

<!-- 설문 거부 버튼 표시 여부( 1 : 비표시, 0 :  표시) -->
<c:if test="${survey.rejectButton eq '0'}">
 <form id="rejectForm" method="post" action="<c:url value='/servicepack/survey/response/rejectSubmit.do'/>">
 <input name="surveyId" title="" type="hidden" value="${survey.surveyId}"  title=""/>
  <div class="survey_preview_t">				
		<h3><ikep4j:message key='ui.servicepack.survey.common.reject.h' /></h3>
		<div class="surveyList" style="background:none;">
			<p><ikep4j:message key='ui.servicepack.survey.common.reject.t' /></p>
			<ul><!--inline 삭제 또는 삽입 여부에 따라 display 방식 변경-->
				<li class="bg_none"><input name="surveyRejectReason1" type="radio" title="" class="radio" value="1" /><ikep4j:message key='ui.servicepack.survey.common.reject.r.1' /></li>
				<li class="bg_none"><input name="surveyRejectReason1" type="radio" title="" class="radio" value="2" /><ikep4j:message key='ui.servicepack.survey.common.reject.r.2' /></li>
				<li class="bg_none"><input name="surveyRejectReason1" type="radio" title="" class="radio" value="3" /><ikep4j:message key='ui.servicepack.survey.common.reject.r.3' />
					<input name="surveyRejectReason2" type="text" class="inputbox" title="" size="80"  title=""/></li>
			</ul>
		 </div>
	</div>
 </form>

<!-- blockDetail End --> 
<!-- tableBottom Start -->
<div class="tableBottom">										
	<!-- blockButton Start -->
	<div class="blockButton"> 
		<ul>
			<li><a id="rejectButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='rejectSubmit'/></span></a></li>
			<li><a id="listButton1" class="button" href="<c:url value='/servicepack/survey/surveyIngList.do'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		 </ul>
	</div>
	<!-- blockButton End --> 
</div>
<!-- 높이 조절 -->
<div class="blockBlank_10px"></div>
</c:if>
</div>
<!-- tableBottom End --> 
