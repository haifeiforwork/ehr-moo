<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preMessage" value="message.support.addressbook.ContactHistory" />
<c:set var="preContactType"  value="ui.support.addressbook.ContactHistory.contactType" /> 
<%-- 메시지 관련 Prefix 선언 End --%>
			
						<c:choose>
							<c:when test="${contactList.emptyRecord}">
								<c:if test="${searchContactId == ''}">
								<tr>
								<td colspan="4">
									<ikep4j:message pre='${preMessage}' key='nodata'/>
								</td>
								</c:if>
							</tr>			        
							</c:when>
							<c:otherwise>
							<c:forEach var="contact" items="${contactList.entity}" varStatus="status">
							<tr>
								<td class="textCenter"><input type="hidden" class="hidden" title="<c:out value="${contact.contactUserName}"/>" name="contactId" value="<c:out value="${contact.contactId}"/>"  />
								<c:out value="${contact.contactUserId}"/></td>
								<td class="textCenter">
									<c:if test="${contact.contactType == 'PF'}">
									<ikep4j:message pre='${preContactType}' key='profile'/>
									</c:if>
									<c:if test="${contact.contactType == 'BG'}">
									<ikep4j:message pre='${preContactType}' key='blog'/>
									</c:if>
									<c:if test="${contact.contactType == 'MB'}">
									<ikep4j:message pre='${preContactType}' key='microblog'/>
									</c:if>
									<c:if test="${contact.contactType == 'ML'}">
									<ikep4j:message pre='${preContactType}' key='mail'/>
									</c:if>
									<c:if test="${contact.contactType == 'MS'}">
									<ikep4j:message pre='${preContactType}' key='message'/>
									</c:if>
									<c:if test="${contact.contactType == 'SM'}">
									<ikep4j:message pre='${preContactType}' key='sms'/>
									</c:if>
								</td>
								<td class="textCenter"><fmt:formatDate value="${contact.registDate}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td class="textCenter"><a href="#a" class="button_pr"><span><img alt="" src="<c:url value='/base/images/icon/ic_plus.gif' />">Follow</span></a></td>
							</tr>
							</c:forEach>
							</c:otherwise>	
						</c:choose>																	

