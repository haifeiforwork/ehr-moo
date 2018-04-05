<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" /> 
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<% 
	pageContext.setAttribute("lf", "\n"); 
%>

<script type="text/javascript" src="<c:url value='/base/js/units/support/guestbook/guestbook.js'/>"></script>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />


							<c:if test="${ size > 0 }">
								<c:forEach var="guestbookList" items="${searchResult.entity}" varStatus="status">
								
								<input type="hidden" class="hidden" name="guestbookId" value="<c:out value="${guestbookList.guestbookId}"/>"  />
								<c:if test="${size != status.index}">
								<div class="guestbook_c" id="GUEST_${guestbookList.guestbookId}">
								</c:if>
								<c:if test="${size == status.index}">
								<div class="guestbook_c divLast" id="GUEST_${guestbookList.guestbookId}">
								</c:if>
								
									<div class="pr_guestbookInfo">
										<span class="pr_guestbookInfo_name"><a href="#a" onclick="goOtherProfile('${guestbookList.registerId}')"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbookList.guestbookUserName}"/></c:when><c:otherwise><c:out value="${guestbookList.guestbookUserEngName}"/></c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbookList.registerJobTitleName}"/></c:when><c:otherwise><c:out value="${guestbookList.registerJobTitleEngName}"/></c:otherwise></c:choose></a></span>						
										<span><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbookList.registerTeamName}"/></c:when><c:otherwise><c:out value="${guestbookList.registerTeamEngName}"/></c:otherwise></c:choose></span>
										<span><ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${guestbookList.registDate}"/></span>
		
										<span class="rebtn">
										<c:if test="${user.userId == guestbookList.registerId or user.userId == targetUserId }">
										<a onclick="Guestbook.deleteGuestbook('${guestbookList.guestbookId}',this)" href="#a" class="ic_delete"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
										</c:if>					
										<a onclick="inputGuestbookLineReplyValid('${guestbookList.guestbookId}',this,'1')" href="#a" class="ic_reply"><span><ikep4j:message pre='${preButton}' key='createLinereply'/></span></a>
										</span>
									</div>

									<div class="commentNum"><ikep4j:message pre='${preGuestbook}' key='linereply'/> (<span class="colorPoint"><c:out value="${guestbookList.guestbookLineReplyCnt}"/></span>)</div>
									
									<div class="summaryViewConTest">
									<p>${fn:replace(guestbookList.contents, lf, "<br/>")}</p>
									<div class="gbLineReplyInputView" ></div>
									<div class="prependGbLineReplyview">
										<c:if test="${guestbookList.guestbookList != null}">
										<c:forEach var="guestbooklist1dp" items="${guestbookList.guestbookList}" varStatus="lrStatus">
										
										<!--blockComment_rewrite Start-->
										<div class="blockComment_re">
											<div class="reply_ar"><img src="<c:url value='/base/images/icon/ic_reply_ar.gif' />" alt="" /></div>
											<div class="blockCommentInfo">
												<span class="blockCommentInfo_name"><a href="#a" onclick="goOtherProfile('${guestbooklist1dp.registerId}')" ><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbooklist1dp.guestbookUserName}"/></c:when><c:otherwise><c:out value="${guestbooklist1dp.guestbookUserEngName}"/></c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbooklist1dp.registerJobTitleName}"/></c:when><c:otherwise><c:out value="${guestbooklist1dp.registerJobTitleEngName}"/></c:otherwise></c:choose></a></span>					
												<span><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbooklist1dp.registerTeamName}"/></c:when><c:otherwise><c:out value="${guestbooklist1dp.registerTeamEngName}"/></c:otherwise></c:choose></span>
												<span><ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${guestbooklist1dp.registDate}"/></span>
			
			
												<span class="rebtn">
													<c:if test="${user.userId == guestbooklist1dp.registerId or user.userId == targetUserId }">
														<a onclick="Guestbook.deleteGuestbookLineReplyByGroupId('${guestbooklist1dp.guestbookId}','${guestbooklist1dp.groupId}')" href="#a" class="ic_delete"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
													</c:if>
														<a onclick="inputGuestbookLineReplyValid('${guestbooklist1dp.guestbookId}',this,'2')" href="#a" class="ic_reply"><span><ikep4j:message pre='${preButton}' key='createLinereply'/></span></a>
												</span>
												
											</div>
											
											<div class="summaryViewConTest">
											<p>${fn:replace(guestbooklist1dp.contents, lf, '<br/>')}</p>
											<div class="gbLineReplyInputView"></div>
											<div class="prependGbLineReplyview"></div>
											</div>
											
											<c:if test="${guestbooklist1dp.guestbookList != null}">
											<c:forEach var="guestbooklist2dp" items="${guestbooklist1dp.guestbookList}" varStatus="lrStatus">
											<div class="blockComment_re">
												<div class="reply_ar"><img src="<c:url value='/base/images/icon/ic_reply_ar.gif' />" alt="" /></div>
												<div class="blockCommentInfo">
													<span class="blockCommentInfo_name"><a href="#a" onclick="goOtherProfile('${guestbooklist2dp.registerId}')"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbooklist2dp.guestbookUserName}"/></c:when><c:otherwise><c:out value="${guestbooklist2dp.guestbookUserEngName}"/></c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbooklist2dp.registerJobTitleName}"/></c:when><c:otherwise><c:out value="${guestbooklist2dp.registerJobTitleEngName}"/></c:otherwise></c:choose></a></span>					
													<span><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbooklist2dp.registerTeamName}"/></c:when><c:otherwise><c:out value="${guestbooklist2dp.registerTeamEngName}"/></c:otherwise></c:choose></span>
													<span><ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${guestbooklist2dp.registDate}"/></span>
				
				
													<span class="rebtn">
														<c:if test="${user.userId == guestbooklist2dp.registerId or user.userId == targetUserId }">
															<a onclick="Guestbook.deleteGuestbookLineReplyByGroupId('${guestbooklist2dp.guestbookId}','${guestbooklist2dp.groupId}')" href="#a" class="ic_delete"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
														</c:if>
															<a onclick="inputGuestbookLineReplyValid('${guestbooklist2dp.guestbookId}',this,'3')" href="#a" class="ic_reply"><span><ikep4j:message pre='${preButton}' key='createLinereply'/></span></a>
													</span>
													
												</div>
												<div class="summaryViewConTest">
												<p>${fn:replace(guestbooklist2dp.contents, lf, '<br/>')}</p>
												<div class="gbLineReplyInputView"></div>
												<div class="prependGbLineReplyview">

													<c:if test="${guestbooklist2dp.guestbookList != null}">
													<c:forEach var="guestbooklist3dp" items="${guestbooklist2dp.guestbookList}" varStatus="lrStatus">
													
													<div class="blockComment_re">
														<div class="reply_ar"><img src="<c:url value='/base/images/icon/ic_reply_ar.gif' />" alt="" /></div>
														<div class="blockCommentInfo">
															<span class="blockCommentInfo_name"><a href="#a" onclick="goOtherProfile('${guestbooklist3dp.registerId}')" ><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbooklist3dp.guestbookUserName}"/></c:when><c:otherwise><c:out value="${guestbooklist3dp.guestbookUserEngName}"/></c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbooklist3dp.registerJobTitleName}"/></c:when><c:otherwise><c:out value="${guestbooklist3dp.registerJobTitleEngName}"/></c:otherwise></c:choose></a></span>					
															<span><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbooklist3dp.registerTeamName}"/></c:when><c:otherwise><c:out value="${guestbooklist3dp.registerTeamEngName}"/></c:otherwise></c:choose></span>
															<span><ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${guestbooklist3dp.registDate}"/></span>
						
						
															<span class="rebtn">
																<c:if test="${user.userId == guestbooklist3dp.registerId or user.userId == targetUserId }">
																	<a onclick="Guestbook.deleteGuestbookLineReplyByGroupId('${guestbooklist3dp.guestbookId}','${guestbooklist3dp.groupId}');" href="#a" class="ic_delete"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
																</c:if>
															</span>
															
														</div>
														<p>${fn:replace(guestbooklist3dp.contents, lf, '<br/>')}</p>
													</div>
													
													</c:forEach>
													</c:if>
																								
												</div>
												</div>
											</div>
											</c:forEach>
											</c:if>
										
										</div>
										</c:forEach>
										</c:if>
									</div>
									</div>

								</div>
								</c:forEach>
							</c:if>
