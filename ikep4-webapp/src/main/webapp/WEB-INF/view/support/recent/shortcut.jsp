<%@ include file="/base/common/taglibs.jsp"%>
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preDetail"  value="ui.support.favorite.detail" />
<%-- 메시지 관련 Prefix 선언 End --%>

<div class="subTitle_4">
	<h3><ikep4j:message pre='${preDetail}' key='people' /></h3>
	<div class="btn_more"><a href="<c:url value="/support/recent/getListForPeople.do" />" target="mainFrame" onclick="hidePersonalBox()"><img src="<c:url value="/base/images/common/btn_more.gif" />" alt="more" /></a></div>
</div>

<ul class="quicktxt_listPeople">
	
	<c:choose>
	    <c:when test="${searchResultForPeople.emptyRecord}">
	    </c:when>
	    <c:otherwise>
			<c:forEach var="favorite" items="${searchResultForPeople.entity}">
				<li class="ellipsis">
					<c:set var="name" value="${favorite.title} ${favorite.jobTitleName}"/>
					<a href="#a" onclick="showDatail('${favorite.targetUrl}','${favorite.targetId}','${favorite.subId}','${favorite.title}')" title="${name}">${name}</a>
				</li>	
			</c:forEach>				      
	    </c:otherwise> 
	</c:choose>  
						
</ul>

<c:if test="${CommonConstant.PACKAGE_VERSION != CommonConstant.IKEP_VERSION_BASIC}">
<div class="subTitle_4">
	<h3><ikep4j:message pre='${preDetail}' key='document' /></h3>
	<div class="btn_more"><a href="<c:url value="/support/recent/getListForDocument.do" />" target="mainFrame" onclick="hidePersonalBox()"><img src="<c:url value="/base/images/common/btn_more.gif" />" alt="more" /></a></div>
</div>

<ul class="quicktxt_listDoc">
	
	<c:choose>
	    <c:when test="${searchResultForDocument.emptyRecord}">
	    </c:when>
	    <c:otherwise>
			<c:forEach var="favorite" items="${searchResultForDocument.entity}">
				<li class="ellipsis">
					<a href="#a" onclick="showDatail('${favorite.targetUrl}','${favorite.targetId}','${favorite.subId}','${ikep4j:replaceQuot(favorite.title)}')" title="${ikep4j:replaceQuot(favorite.title)}">${favorite.title}</a>
				</li>	
			</c:forEach>				      
	    </c:otherwise> 
	</c:choose>  
</ul>
</c:if>