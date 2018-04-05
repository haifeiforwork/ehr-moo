<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<c:set var="menuPrefix">ui.socialpack.socialanalyzer.subMenu</c:set>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
(function($) {
	$jq(document).ready(function(){
		// left menu setting
		iKEP.setLeftMenu();
		
	});
	
})(jQuery);
</script>

			<!--leftMenu Start-->
				<h1 class="none"></h1>	
				<h2><ikep4j:message pre="${menuPrefix}" key="subMenu.title"/></h2>
				<div class="smsvs_pr">
					<c:choose>
					    <c:when test="${user.localeCode == portal.defaultLocaleCode}">${user.userName} ${user.teamName}</c:when>
					    <c:otherwise>${user.userEnglishName} ${user.teamEnglishName}</c:otherwise>
				    </c:choose>
				</div>
				<div class="left_fixed">
					<ul>
						<li class="liFirst no_child"><a href="<c:url value='/socialpack/socialanalyzer/listSocialGraphView.do'/>"><ikep4j:message pre="${menuPrefix}" key="subMenu1"/></a></li>
						<li class="no_child"><a href="<c:url value='/socialpack/socialanalyzer/listSocialRankingView.do'/>"><ikep4j:message pre="${menuPrefix}" key="subMenu2"/></a></li>
						
						<c:if test="${adminYn == 'Y'}">
						<li class="opened"><a><ikep4j:message pre="${menuPrefix}" key="subMenu3"/></a>
							<ul>
								<li class="no_child"><a href="<c:url value='/socialpack/socialanalyzer/updateSocialDistanceView.do'/>"><ikep4j:message pre="${menuPrefix}" key="subMenu4"/></a></li>
								<li class="no_child"><a href="<c:url value='/socialpack/socialanalyzer/updateSocialRankingView.do'/>"><ikep4j:message pre="${menuPrefix}" key="subMenu5"/></a></li>
								<li class="no_child liLast"><a href="<c:url value='/socialpack/socialanalyzer/listBatchLogView.do'/>"><ikep4j:message pre="${menuPrefix}" key="subMenu6"/></a></li>
							</ul>					
						</li>
						</c:if>
					</ul>										
				</div>
			<!--//leftMenu End-->			