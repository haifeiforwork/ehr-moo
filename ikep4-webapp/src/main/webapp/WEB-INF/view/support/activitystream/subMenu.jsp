<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.activitystream.header" /> 
<c:set var="preList"    value="ui.support.activitystream.list" />
<c:set var="preDetail"  value="ui.support.activitystream.detail" />
<c:set var="preButton"  value="ui.support.activitystream.button" /> 
<c:set var="preMessage" value="ui.support.activitystream.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
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
<h1 class="none"><ikep4j:message pre='${preList}' key='main.title' /></h1>	
<h2 class="han"><a href="<c:url value='/support/activitystream/getListForActivityStream.do'/>"><ikep4j:message pre='${preList}' key='main.title' /></a></h2>
<div class="left_fixed">
	<ul>
	
		<li class="liFirst opened">
			<a href="<c:url value='/support/activitystream/getListForActivityStream.do'/>" ><ikep4j:message pre='${preList}' key='activityStream' /></a>
		</li>
		
		<li class="liFirst opened">
			<a href="<c:url value='/support/activitystream/getUserActivityCode.do'/>" ><ikep4j:message pre='${preList}' key='personal' /></a>
		</li>
		
		<li class="liFirst opened">
			<a href="#a" onclick="return false;"><ikep4j:message pre='${preList}' key='administration' /></a>
			<ul>
				<li class="no_child">
					<a href="<c:url value='/support/activitystream/getActivityConfig.do'/>">
						<ikep4j:message pre='${preList}' key='maxSize' />
					</a>
				</li>
				<li class="no_child">
					<a href="<c:url value='/support/activitystream/getActivityDelLog.do'/>">
						<ikep4j:message pre='${preList}' key='batch' />
					</a>
				</li>
			</ul>					
		</li>
	
	</ul>
</div>	
<!--//leftMenu End-->