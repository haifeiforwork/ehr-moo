<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

					<h2><c:out value="${addrgroupName}"/></h2>

					<div id="pageLocation">
						<c:if test="${listNavi != null}">
						<ul>
							<c:forEach var="navi" items="${listNavi}" varStatus="status">
								<c:if test="${(status.index == 0) }">
								<li class="liFirst"><c:out value="${navi.naviTitle}"/></li>
								</c:if>
								<c:if test="${(status.index > 0) && (status.index+1 < listNaviSize) }">
								<li><c:out value="${navi.naviTitle}"/></li>
								</c:if>
								<c:if test="${(status.index > 0) && (status.index+1 == listNaviSize) }">
								<li class="liLast"><c:out value="${navi.naviTitle}"/></li>
								</c:if>
							</c:forEach>
						</ul>
						</c:if>
					</div>