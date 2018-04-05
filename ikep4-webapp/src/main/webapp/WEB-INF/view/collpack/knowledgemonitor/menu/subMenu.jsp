<%--
=====================================================
* 기능 설명 : 다이나믹 타일즈2 Left Page
* 작성자 : 이동희
=====================================================
--%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ikep4j" uri="/WEB-INF/tld/ikep4j.tld"%>

<c:set var="menuPrefix">ui.collpack.knowledgemonitor.menu</c:set>

<h2><a href="<c:url value='/collpack/knowledgemonitor/knowledgeAccumulationInfo.do'/>"><ikep4j:message pre="${menuPrefix}" key="menuTitle"/></a></h2>
<div class="left_fixed">
	<ul>
		<li class="no_child liFirst <c:if test="${11 eq menuId}">licurrent</c:if>"><a href="<c:url value='/collpack/knowledgemonitor/knowledgeAccumulationInfo.do'/>"><ikep4j:message pre="${menuPrefix}" key="knowledgeAccumulationInfo"/></a></li>
		<li class="no_child <c:if test="${21 eq menuId}">licurrent</c:if> <c:if test="${!knowledgeMonitorAdmin}">liLast</c:if>"><a href="<c:url value='/collpack/knowledgemonitor/knowledgeRankingInfoView.do'/>"><ikep4j:message pre="${menuPrefix}" key="knowledgeRankingInfo"/></a></li>
	<c:if test="${knowledgeMonitorAdmin}">
		<li class="opened liLast <c:if test="${30 lt menuId}">licurrent</c:if>"><a href="#a"><ikep4j:message pre="${menuPrefix}" key="administrator"/></a>
			<ul>
				<li class="no_child liFirst <c:if test="${31 eq menuId}">licurrent</c:if>"><a href="<c:url value='/collpack/knowledgemonitor/admin/moduleManagerView.do'/>"><ikep4j:message pre="${menuPrefix}" key="administrator.moduleManager"/></a></li>
				<li class="no_child <c:if test="${32 eq menuId}">licurrent</c:if>"><a href="<c:url value='/collpack/knowledgemonitor/admin/contentCriteriaManagerView.do'/>"><ikep4j:message pre="${menuPrefix}" key="administrator.contentCriteriaManager"/></a></li>
				<!--<li class="no_child <c:if test="${33 eq menuId}">licurrent</c:if>"><a href="<c:url value='/collpack/knowledgemonitor/admin/commentCriteriaManagerView.do'/>"><ikep4j:message pre="${menuPrefix}" key="administrator.commentCriteriaManager"/></a></li>-->
				<li class="no_child liLast <c:if test="${34 eq menuId}">licurrent</c:if>"><a href="<c:url value='/collpack/knowledgemonitor/admin/batchLogViewerView.do'/>"><ikep4j:message pre="${menuPrefix}" key="administrator.batchLogViewer"/></a></li>
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
