<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preMenu"  		value="ui.support.abusereporting.menu" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
//<!--
(function($) {
	$(document).ready(function() {	
		// left menu setting
		iKEP.setLeftMenu();		
	});
})(jQuery);
//-->
</script>

<!--leftMenu Start-->
	<h2><ikep4j:message pre='ui.support.abusereporting.title' key='main' /></h2>
	<div class="left_fixed">
		<ul>			
			<li class="opened"><a href="#a"><ikep4j:message pre='${preMenu}' key='abuseCondition' /></a>
				<ul>
					<li class="no_child liLast"><a href="<c:url value='/support/abusereporting/getAbuseReportList.do'/>"><ikep4j:message pre='${preMenu}' key='abuseReport' /></a></li>
					<li class="no_child liLast"><a href="<c:url value='/support/abusereporting/getAbuseStatistics.do'/>"><ikep4j:message pre='${preMenu}' key='abuseStatistics' /></a></li>
				</ul>					
			</li>		
			<c:if test="${true == isSystemAdmin}">	
				<li class="no_child liLast"><a href="<c:url value='/support/abusereporting/getArKeywordManageMain.do'/>"><ikep4j:message pre='${preMenu}' key='keywordManage' /></a></li>
			</c:if>
		</ul>
	</div>
<!--//leftMenu End-->
