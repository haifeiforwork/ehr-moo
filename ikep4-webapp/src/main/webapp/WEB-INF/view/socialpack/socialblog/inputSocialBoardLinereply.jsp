<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preWebCommon"  value="ui.socialpack.socialblog.common.webstandard" />
<c:set var="preLinereply"  value="ui.socialpack.socialblog.socialBoardItem.readBoardView.listBoardLinereply" />
<c:set var="preButton"  value="ui.socialpack.socialblog.common.button" />

 <%-- 메시지 관련 Prefix 선언 End --%>
 
<c:set var="user" value="${sessionScope['ikep.user']}" />

								<%-- 답글쓰기 가능 여부 누구나. --%> 
								<div class="guestbook_write">
									<form id="socialBoardLinereplyForm${socialBoardLinereply.itemId}" name="socialBoardLinereplyForm${socialBoardLinereply.itemId}" action="" onsubmit="return false;">
									<input name="itemId" type="hidden" value="${socialBoardLinereply.itemId}" title="" />
									<table summary="<ikep4j:message pre='${preWebCommon}' key='createTable'/>">
										<caption></caption>
										<tbody><tr>
											<td>
												<textarea rows="3" cols="" title="<ikep4j:message pre='${preLinereply}' key='contents'/>" id="contents_${socialBoardLinereply.itemId}" name="contents"></textarea>
											</td>
											<td width="74" class="textRight">
												<a href="#a" class="button_re"><span><ikep4j:message pre='${preButton}' key='save'/></span></a>
											</td>
										</tr>
									</tbody></table>
									<div class="guestbook_write_num"><span id='textcount'>0</span>/150</div>
									</form>
								</div>
