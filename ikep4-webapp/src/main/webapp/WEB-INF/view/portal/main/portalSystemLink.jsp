<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="prefix" value="message.templates.base.header"/>	

<c:set var="portalSystemLinkList" value="${portalSystemLinkList}" />

<script type="text/javascript"> 
//<!--
//-->
</script>

<div class="utilmenu_box">
	<ul>
	
		<c:choose>
		
		<c:when test="${!empty portalSystemLinkList}">
		
		<c:set var="parentValue" value="" />
		<c:set var="childUlStart" value="0" />
		<c:forEach var="items" items="${portalSystemLinkList}" varStatus="status">
		
		<c:if test="${items.depthLevel == 2}">
			<c:if test="${parentValue != items.systemCode && childUlStart == 0}">
		</li>
			</c:if>	
			
			<c:if test="${parentValue != items.systemCode && childUlStart == 1}">
			<c:set var="childUlStart" value="0" />
			</ul>
		</li>
			</c:if>	
		
		<c:set var="parentValue" value="${items.systemCode}" />
		<li>
			<a 
				<c:choose>
				<c:when test="${items.systemType == 'ITEM'}">
				<c:choose>
				<c:when test="${!empty items.url}">
				<c:if test="${items.urlType == 'URL'}">
				href="<c:url value='${ikep4j:urlConverter(items.url, user)}' />"
				onclick="hideSystemLink();"
				</c:if>
				<c:if test="${items.urlType == 'JAVASCRIPT'}">
				onclick="hideSystemLink();${ikep4j:urlConverter(itemks.url, user)};"
				</c:if>
				</c:when>
				<c:otherwise>
				href="#" onclick="hideSystemLink();return false;"
				</c:otherwise>
				</c:choose>
				<c:if test="${items.target == 'INNER'}">
				target="mainFrame"
				</c:if>
				<c:if test="${items.target == 'WINDOW'}">
				target="_blank"
				</c:if>
				</c:when>
				<c:otherwise>
				href="#" onclick="hideSystemLink();return false;"
				</c:otherwise>
				</c:choose>
				>
				<c:out value="${items.systemName}"/>
			</a>
		</c:if>
		
		<c:if test="${childUlStart == 0 && parentValue == items.parentSystemCode}">
			<c:set var="childUlStart" value="1" />
			<ul>
		</c:if>
		
			<c:if test="${items.depthLevel == 3 && parentValue == items.parentSystemCode}">
				<c:set var="parentValue" value="${items.parentSystemCode}" />
				<li>
					<a 
						<c:choose>
						<c:when test="${items.systemType == 'ITEM'}">
						<c:choose>
						<c:when test="${!empty items.url}">
						<c:if test="${items.urlType == 'URL'}">
						href="<c:url value='${ikep4j:urlConverter(items.url, user)}' />"
						onclick="hideSystemLink();"
						</c:if>
						<c:if test="${items.urlType == 'JAVASCRIPT'}">
						onclick="hideSystemLink();${ikep4j:urlConverter(items.url, user)};"
						</c:if>
						</c:when>
						<c:otherwise>
						href="#" onclick="hideSystemLink();return false;"
						</c:otherwise>
						</c:choose>
						<c:if test="${items.target == 'INNER'}">
						target="mainFrame"
						</c:if>
						<c:if test="${items.target == 'WINDOW'}">
						target="_blank"
						</c:if>
						</c:when>
						<c:otherwise>
						href="#" onclick="hideSystemLink();return false;"
						</c:otherwise>
						</c:choose>
						>
						<c:out value="${items.systemName}"/>
					</a>
				</li>
			</c:if>
			
		<c:if test="${status.last}">
		</li>
		</c:if>
		
		</c:forEach>
		
		</c:when>
		
		<c:otherwise>
		
		<li><a href="#" onclick="return false;"><ikep4j:message pre="${prefix}" key="system.noData" /></a></li>
		
		</c:otherwise>
		
		</c:choose>	
							
	</ul>
</div>