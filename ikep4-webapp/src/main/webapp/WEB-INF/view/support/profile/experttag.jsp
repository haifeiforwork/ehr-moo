<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" /> 
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />

							<table summary="<ikep4j:message pre='${preProfileMain}' key='exports.exportTag'/>, <ikep4j:message pre='${preProfileMain}' key='exports.intrestTag'/>, <ikep4j:message pre='${preProfileMain}' key='exports.intrestPart'/>">
								<caption></caption>
								<tbody>
									<tr>
										<th scope="row"><img src="<c:url value='/base/images/common/tag_expert.gif' />" alt="<ikep4j:message pre='${preProfileMain}' key='exports.exportTag'/>" /></th>
										<td>
											<div id="tagForm_${fn:replace(targetUserId, '.', '')}_PRO">
											      <input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_PROFILE_PRO %>" />
											      <input type="hidden" name="tagItemSubType" value="${targetUserId}" />
											      <input type="hidden" name="tagItemName" value="${targetUserId} <ikep4j:message pre='${preProfileMain}' key='exports.exportTag'/>" />
											      <input type="hidden" name="tagItemContents" value="" />
											      <input type="hidden" name="tagItemUrl" value="" />
											      
											     <div>
											     	
											        	<c:forEach var="tag" items="${expertTagList}" varStatus="tagLoop">
											        		<c:if test="${tagLoop.count != 1}">, </c:if>
											           			<p style="display:inline">${tag.tagName}</p>
											           	</c:forEach>
											        
											        <span class="rebtn">
											         <c:if test="${user.userId == targetUserId}">
											           <a href="#a" onclick="iKEP.tagging.tagFormView('${targetUserId}','PRO');return false;">
											           <img src="<c:url value="/base/images/icon/ic_edit.gif"/>" style="margin-bottom:0;" alt="<ikep4j:message pre='${preButton}' key='edit'/>" /></a>
											         </c:if>
											       </span>
											     </div>
											</div>
										</td>
									</tr>

									<tr>
										<th scope="row"><img src="<c:url value='/base/images/common/tag_favor.gif' />" alt="<ikep4j:message pre='${preProfileMain}' key='exports.intrestTag'/>" /></th>
										<td>
											<div  id="tagForm_${fn:replace(targetUserId, '.', '')}_ATT">
											      <input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_PROFILE_ATTENTION %>" />
											      <input type="hidden" name="tagItemSubType" value="${targetUserId}" />
											      <input type="hidden" name="tagItemName" value="${targetUserId} <ikep4j:message pre='${preProfileMain}' key='exports.intrestTag'/>" />
											      <input type="hidden" name="tagItemContents" value="" />
											      <input type="hidden" name="tagItemUrl" value="" />
											      
											     <div class="">
											        	<c:forEach var="tag" items="${intressTagList}" varStatus="tagLoop">
											        		<c:if test="${tagLoop.count != 1}">, </c:if>
											           			<p style="display:inline">${tag.tagName}</p>
											           	</c:forEach>
											        <span class="rebtn">
											         <c:if test="${user.userId == targetUserId}">
											           <a href="#a" onclick="iKEP.tagging.tagFormView('${targetUserId}','ATT');return false;">
											           <img src="<c:url value="/base/images/icon/ic_edit.gif"/>" style="margin-bottom:0;" alt="<ikep4j:message pre='${preButton}' key='edit'/>" /></a>
											         </c:if>
											       </span>
											     </div>
											</div>
										</td>
									</tr>
									<!-- 
									<tr>
										<th scope="row"><img src="<c:url value='/base/images/common/tag_field.gif' />" alt="<ikep4j:message pre='${preProfileMain}' key='exports.intrestPart'/>" /></th>
										<td>
											<ul>

												<li><img src="<c:url value='/base/images/icon/ic_expert.gif' />" alt="expert" />기술,분석 > 언어 > Java</li>
												<li>기술,분석 > Database > DBA</li>
												<li>컴퓨터,통신 > 웹 > 블로그</li>
											</ul>
										</td>
									</tr>
									-->																		
								</tbody>
							</table>
							