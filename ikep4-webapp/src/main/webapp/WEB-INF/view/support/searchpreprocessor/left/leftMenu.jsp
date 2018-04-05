<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<c:set var="preLeft"  value="ui.support.searchpreprocessor.common.left" /> 
<script type="text/javascript">
<!--
(function($) {
	$(document).ready(function() {
		 iKEP.setLeftMenu();		
	});
})(jQuery);
-->
</script>
<h1 class="none">레프트메뉴</h1>	
<h2><a href="<c:url value='/support/searchpreprocessor/searchhistory/history.do?searchOption=month3'/>"><ikep4j:message pre="${preLeft}" key="title" /></a></h2>


<c:choose>
 <c:when test="${user.localeCode == portal.defaultLocaleCode}">
  <div class="smsvs_pr">${user.userName}<span class="smsvs_pr_team">${user.teamName}</span></div>
 </c:when>
 <c:otherwise>
  <div class="smsvs_pr">${user.userEnglishName}<span class="smsvs_pr_team">${user.teamEnglishName}</span></div>
 </c:otherwise>
</c:choose>
								     
<div class="left_fixed">
	<ul>
		<li class="liFirst no_child"><a href="<c:url value='/support/searchpreprocessor/searchhistory/history.do?searchOption=month3'/>"><ikep4j:message pre="${preLeft}" key="0" /></a></li>
		<li class="no_child"><a href="<c:url value='/support/searchpreprocessor/searchhistory/colleague.do'/>"><ikep4j:message pre="${preLeft}" key="1" /></a></li>
		<li class="no_child"><a href="<c:url value='/support/searchpreprocessor/searchhistory/recommend.do'/>"><ikep4j:message pre="${preLeft}" key="2" /></a></li>
		<li class="no_child"><a href="<c:url value='/support/searchpreprocessor/searchhistory/popular.do?searchOption=real'/>"><ikep4j:message pre="${preLeft}" key="3" /></a></li>
		<li class="no_child"><a href="<c:url value='/support/searchpreprocessor/searchhistory/related/init.do'/>"><ikep4j:message pre="${preLeft}" key="4" /></a></li>
		<li class="no_child"><a href="<c:url value='/support/searchpreprocessor/searchhistory/recommendtag.do'/>"><ikep4j:message pre="${preLeft}" key="5" /></a></li>
		<li class="no_child"><a href="<c:url value='/support/searchpreprocessor/searchhistory/populartag.do'/>"><ikep4j:message pre="${preLeft}" key="6" /></a></li>
		<li class="no_child"><a href="<c:url value='/support/searchpreprocessor/report/reportHistory.do'/>"><ikep4j:message pre="${preLeft}" key="7" /></a></li>
		<c:if test="${isAdmin}">
		<li class="opened"><a href="#a"><ikep4j:message pre="${preLeft}" key="8" /></a>
			<ul>
				<li class="no_child"><a href="<c:url value='/support/searchpreprocessor/batchlog/history.do'/>"><ikep4j:message pre="${preLeft}" key="8.0" /></a></li>
			</ul>					
		</li>
		</c:if>
	</ul>										
</div>
<form id="intelligentSearchForm" name="intelligentSearchForm" method="post" action="<c:url value="/servicepack/intelligentsearch/search.do"/>">
<input name="searchKeyword" id="searchKeyword1" class="inputbox" title="search" type="hidden"/>	
</form>