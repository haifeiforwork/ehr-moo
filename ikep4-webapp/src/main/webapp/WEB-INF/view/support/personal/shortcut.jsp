<%@ include file="/base/common/taglibs.jsp"%>
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preDetail"  value="ui.support.favorite.detail" />
<%-- 메시지 관련 Prefix 선언 End --%> 
			
<ul class="quicktxt_listPerson">
	<li class="document">
		<a href="<c:url value="/support/personal/getListForm.do" />?viewMode=document" target="mainFrame" onclick="hidePersonalBox()">
		<ikep4j:message pre='${preDetail}' key='document' /></a>
	</li>
	<li class="file">
		<a href="<c:url value="/support/personal/getListForm.do" />?viewMode=file" target="mainFrame" onclick="hidePersonalBox()">
		<ikep4j:message pre='${preDetail}' key='file' /></a>
	</li>
	<li class="images">
		<a href="<c:url value="/support/personal/getListForm.do" />?viewMode=image" target="mainFrame" onclick="hidePersonalBox()">
		<ikep4j:message pre='${preDetail}' key='image' /></a>
	</li>
	<!--li class="video">
		<a href="<c:url value="/support/personal/getListForm.do" />?viewMode=video" target="mainFrame" onclick="hidePersonalBox()">
		<ikep4j:message pre='${preDetail}' key='video' /></a>
	</li-->
	<li class="comment">
		<a href="<c:url value="/support/personal/getListForm.do" />?viewMode=comment" target="mainFrame" onclick="hidePersonalBox()">
		<ikep4j:message pre='${preDetail}' key='comment' /></a>
	</li>
	<!-- li class="feedback">
		<a href="<c:url value="/support/personal/getListForm.do" />?viewMode=feedback" target="mainFrame" onclick="hidePersonalBox()">
		<ikep4j:message pre='${preDetail}' key='feedback' /></a>
	</li-->	
	<c:if test="${CommonConstant.PACKAGE_VERSION eq CommonConstant.IKEP_VERSION_FULL}">
		<!-- li class="micblog">
			<a href="<c:url value="/socialpack/microblogging/privateHome.do" />" target="mainFrame" onclick="hidePersonalBox()">
			<ikep4j:message pre='${preDetail}' key='microblog' /></a>
		</li-->
	</c:if>
</ul>