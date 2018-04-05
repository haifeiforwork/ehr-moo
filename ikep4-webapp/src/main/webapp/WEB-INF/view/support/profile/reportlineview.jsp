<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

									<c:if test="${reportLineList != null}">
									<c:forEach var="reportLine" items="${reportLineList}" varStatus="status">
										<c:if test="${status.index == '0'}">
										<div class="divFirst">
											<img src="<c:url value='/base/images/common/tree_m.gif' />" alt="" /> <c:out value="${reportLine.leaderName}"/> <c:out value="${reportLine.leaderJobPosition}"/>
										</c:if>
										
										<c:if test="${ status.index != '0'}">
										<div>
											<img src="<c:url value='/base/images/common/tree_m.gif' />" alt="" /> <c:out value="${reportLine.leaderName}"/> <c:out value="${reportLine.leaderJobPosition}"/>
										</c:if>
									</c:forEach>
										<div class="divLast">
											<span><img src="<c:url value='/base/images/icon/ic_person.gif' />" alt="" /> <c:out value="${profile.userName}"/> <c:out value="${profile.jobPositionName}"/></span>
										</div>
									<c:forEach var="reportLineList" items="${reportLineList}" varStatus="status">
										</div>
									</c:forEach>									
									</c:if>
