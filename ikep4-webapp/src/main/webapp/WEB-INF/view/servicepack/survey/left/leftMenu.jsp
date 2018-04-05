<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<c:set var="preLeft"   value="ui.servicepack.survey.menu.left" />
<c:set var="preButton"  value="ui.servicepack.survey.common.button" /> 

<c:set var="user" value="${sessionScope['ikep.user']}" />
<script type="text/javascript">
<!--
(function($) {
	$(document).ready(function() {
		 iKEP.setLeftMenu();
		 
		 var href = location.href.replace(iKEP.getWebAppPath(), "");
		 $("li > a", "div.left_fixed").each(function() {
			 if($(this).attr("href").indexOf(href) >= 0) {
				 $(this).parents("li").addClass("licurrent");
				 return false;
			 }
		 });
	});
})(jQuery);
-->
</script>
<h1 class="none"><ikep4j:message pre="${preLeft}" key="manager" /></h1>	
<h2><a href="<c:url value='/servicepack/survey/index.do'/>" style="vertical-align:bottom;">
<span><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_comm_survey.gif'/>"/></span></a></h2>   
    <!--blockButton_2 Start-->
    <div class="blockButton_2 mb10">                
        <a class="button_2" href="<c:url value='/servicepack/survey/createSurvey.do'/>"><span><img src="<c:url value="/base/images/icon/ic_registration.gif"/>" width="14" height="17" alt="<ikep4j:message pre='${preButton}' key='create'/>"/><ikep4j:message pre='${preButton}' key='create'/></span></a>             
    </div>
    <!--//blockButton_2 End-->      
<div class="left_fixed">
	<ul>
		<li class="liFirst opened"><a href="#a"><ikep4j:message pre="${preLeft}" key="manager" /></a>
			<ul>
				<li class="no_child"><a href="<c:url value='/servicepack/survey/surveyIngList.do'/>"><ikep4j:message pre="${preLeft}" key="response" /></a></li>
				<li class="no_child"><a href="<c:url value='/servicepack/survey/surveyList.do'/>"><ikep4j:message pre="${preLeft}" key="create" /></a></li>
				<li class="no_child liLast"><a href="<c:url value='/servicepack/survey/surveyEndList.do'/>"><ikep4j:message pre="${preLeft}" key="report" /></a></li>
			</ul>					
		</li>
		<c:if test="${isAdmin}">
		<li  class="opened"><a href="#a">Admin</a>
			<ul>
				<li class="no_child"><a href="<c:url value='/servicepack/survey/approve/approveList.do'/>"><ikep4j:message pre="${preLeft}" key="approve" /></a></li>
				
				<li class="no_child liLast"><a href="<c:url value='/servicepack/survey/surveyEndList.do'/>?admin"><ikep4j:message pre="${preLeft}" key="report" /></a></li>
				
			</ul>					
		</li>
		</c:if>
	</ul>
</div>