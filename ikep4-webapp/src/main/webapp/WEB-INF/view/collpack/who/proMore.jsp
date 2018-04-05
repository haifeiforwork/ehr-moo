<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/plot_chart/jquery.jqplot.css'/>" />
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jquery.jqplot.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/excanvas.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jqplot.pieRenderer.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jqplot.barRenderer.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jqplot.categoryAxisRenderer.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jqplot.pointLabels.min.js'/>"></script>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%pageContext.setAttribute("crlf", "\r\n"); %>

<c:set var="preForm"    value="ui.collpack.who.whoForm" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />


<style type="text/css">
.online_test {position:absolute; top:75px; right:20px; width:140px;height:140px}
</style>
<script type="text/javascript">
<!--
(function($) { 
	$(document).ready(function() { 		
	    
	});

})(jQuery); 
//-->
</script> 
<c:forEach var="pro" items="${proList}" varStatus="proLoopCount">
							<div class="conBox">
								<div>
								<img id="profilePictureImage" src="<c:url value='/support/fileupload/downloadFile.do?fileId=${pro.profilePictureId}' />" width="50" height="50" style="vertical-align:top;" alt="<ikep4j:message pre='${preForm}' key='formPicture' />" />
								</div>
								<div>
									<ul>
									    <c:choose>
									    	<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									    		<c:set var="userInfo" value="${pro.userName} ${pro.jobTitleName}"/>
									    		<c:set var="userTeamInfo" value="${pro.teamName}"/>
									      	</c:when>
									      	<c:otherwise>
									      		<c:set var="userInfo" value="${pro.userEnglishName} ${pro.jobTitleEnglishName}"/>
									      		<c:set var="userTeamInfo" value="${pro.teamEnglishName}"/>
									      	</c:otherwise>
									    </c:choose>		
									    <li title="${userInfo}">${ikep4j:cutString(userInfo,20,"")}</li>
									    <li title="${userTeamInfo}">${ikep4j:cutString(userTeamInfo,20,"")}</li>	
										<li><a href="">${pro.mail}</a></li>
										<li>${pro.mobile}</li>
									</ul>
								</div>
							</div>	
</c:forEach>							