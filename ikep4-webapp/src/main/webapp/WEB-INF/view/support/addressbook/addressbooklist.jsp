<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.addressbook.header" /> 
<c:set var="prePrivate"    value="ui.support.addressbook.addrgroup.private" />
<c:set var="prePublic"  value="ui.support.addressbook.addrgroup.public" />
<%-- 메시지 관련 Prefix 선언 End --%>


				<h1 class="none"><ikep4j:message pre='${preHeader}' key='contents'/></h1>
				
				<!--pageTitle Start-->
				<!-- 
				<div id="pageTitle">
				</div>-->
				<div id="pageTitle">
					<c:choose>
						<c:when test="${empty groupType}">
							<h2><ikep4j:message pre='${prePrivate}' key='addressbook'/> - <ikep4j:message pre='${prePrivate}' key='totalList.title'/></h2>
						</c:when>
						<c:when test="${groupType == 'P'}">
							<h2><ikep4j:message pre='${prePrivate}' key='addressbook'/> - ${addrgroupName}</h2>
						</c:when>
						<c:otherwise>
							<h2><ikep4j:message pre='${prePublic}' key='addressbook'/> - ${addrgroupName}</h2>
						</c:otherwise>
					</c:choose>
				</div>
				
				
				
				<!--//pageTitle End-->
	
				<!--blockListTable Start-->
				<div id="listbody">
				</div>
				<!--//blockListTable End-->	
													
