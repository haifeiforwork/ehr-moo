<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" /> 
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

							<form name="gbLineReplyForm" action="" method="post" onsubmit="return false">
								<fieldset>	
								<!--blockComment_rewrite Start-->
								<input type="hidden" name="targetUserId" value="${guestbook.targetUserId}" />
								<input type="hidden" name="registerId" value="${guestbook.registerId}" />
								<input type="hidden" name="registerName" value="${guestbook.registerName}" />
								
								<input type="hidden" name="parentId" value="${guestbook.parentId}" />
								<input type="hidden" name="groupId" value="${guestbook.groupId}" />
								<input type="hidden" name="step" value="${guestbook.step}" />
								<input type="hidden" name="indentation" value="${guestbook.indentation}" />
					
								
								<div class="blockComment_rewrite">
									<div class="reply_ar"><img src="<c:url value='/base/images/icon/ic_reply_ar_b.gif' />" alt="" /></div>
									<table summary="<ikep4j:message pre='${preMsgGuestbook}' key='inputTable'/>">
										<caption></caption>
										<tr>
											<td>
												<div><input name="contents" title="<ikep4j:message pre='${preMsgGuestbook}' key='input'/>" class="inputbox" maxlength="300" type="text" value="<c:out value="${guestbook.contents}"/>" /></div>
											</td>
											<td width="95" class="textRight">
												<ul>
													<li><a class="button_s" href="#a" title="<ikep4j:message pre='${preButton}' key='create'/>"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
													<li><a onclick="Guestbook.cancelGuestbookLineReply(this,'${guestbook.indentation}')" class="button_s" href="#a" title="<ikep4j:message pre='${preButton}' key='cancel'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
												</ul>
											</td>

										</tr>
									</table>				
								</div>
								<!--//blockComment_rewrite End-->
								</fieldset>
							</form>
							