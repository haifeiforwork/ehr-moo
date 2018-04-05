<%--
=====================================================
* 기능 설명 : 다이나믹 타일즈2 Left Page
* 작성자 : 이동희
=====================================================
--%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ikep4j" uri="/WEB-INF/tld/ikep4j.tld"%>

<c:set var="menuPrefix">ui.collpack.assess.menu</c:set>

<h2><a href="<c:url value='/collpack/assess/userRanking.do'/>"><span><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_analy_rank.gif'/>"/></span></a></h2>
<div class="left_fixed">
	<ul>
		<li class="no_child liFirst <c:if test="${11 eq menuId}">licurrent</c:if>"><a href="<c:url value='/collpack/assess/userRanking.do'/>"><ikep4j:message pre="${menuPrefix}" key="userRanking"/></a></li>
		<li class="no_child <c:if test="${21 eq menuId}">licurrent</c:if> <c:if test="${!assessmentManagementAdmin}">liLast</c:if>"><a href="<c:url value='/collpack/assess/groupRankingView.do'/>"><ikep4j:message pre="${menuPrefix}" key="groupRanking"/></a></li>
	<c:if test="${assessmentManagementAdmin}">
		<li class="opened liLast <c:if test="${30 lt menuId}">licurrent</c:if>"><a href="#a"><ikep4j:message pre="${menuPrefix}" key="administrator"/></a>
			<ul>
				<li class="no_child liFirst <c:if test="${31 eq menuId}">licurrent</c:if>"><a href="<c:url value='/collpack/assess/admin/targetManagerView.do'/>"><ikep4j:message pre="${menuPrefix}" key="administrator.targetManager"/></a></li>
				<li class="no_child <c:if test="${32 eq menuId}">licurrent</c:if>"><a href="<c:url value='/collpack/assess/admin/assessmentCriteriaManagerView.do'/>" id="menuAssessmentCriteriaManager"><ikep4j:message pre="${menuPrefix}" key="administrator.assessmentCriteriaManager"/></a></li>
				<li class="no_child <c:if test="${33 eq menuId}">licurrent</c:if>"><a href="<c:url value='/collpack/assess/admin/assessmentScoreInitializeView.do'/>"><ikep4j:message pre="${menuPrefix}" key="administrator.assessmentScoreInitialize"/></a></li>
				<li class="no_child <c:if test="${34 eq menuId}">licurrent</c:if>"><a href="<c:url value='/collpack/assess/admin/batchLogViewerView.do'/>"><ikep4j:message pre="${menuPrefix}" key="administrator.batchLogViewer"/></a></li>
				<li class="no_child liLast <c:if test="${35 eq menuId}">licurrent</c:if>"><a href="<c:url value='/collpack/assess/admin/visualSymbolManagerView.do'/>"><ikep4j:message pre="${menuPrefix}" key="administrator.visualSymbolManager"/></a></li>
			</ul>
		</li>
	</c:if>
	</ul>
</div>

<script type="text/javascript">
//<![CDATA[
$jq(document).ready(function() {
	// left menu setting
	iKEP.setLeftMenu();
});
//]]>
</script>
