<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" /> 
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/units/support/favorite/favorite.js"/>"></script>

							<div class="mylist">
								<h3><ikep4j:message pre='${preProfileMain}' key='files.title'/></h3> 
								<div class="more"><a onclick="goMyFiles()" href="#a"><img src="<c:url value='/base/images/common/btn_more.gif' />" alt="more" /></a></div>
								<c:choose>
								<c:when test="${empty searchResult.entity}">
									<p align="center"><ikep4j:message pre='${preMsgProfile}' key='nodata'/></p>
								</c:when>
								<c:otherwise>
									<ul>
										<c:forEach var="favorite" items="${searchResult.entity}" varStatus="status">
										<c:if test="${status.index <= 3 }" >
											<li><div class="ellipsis">
											<c:choose>
												<c:when test="${favorite.fileExtension=='XLS' || favorite.fileExtension=='XLSX'}">
													<img src="<c:url value="/base/images/icon/ic_ppt.gif" />" alt="" />
												</c:when>
												<c:when test="${favorite.fileExtension=='DOC'}">
													<img src="<c:url value="/base/images/icon/ic_doc.gif" />" alt="" />
												</c:when>
												<c:when test="${favorite.fileExtension=='HWP'}">
													<img src="<c:url value="/base/images/icon/ic_hwp.gif" />" alt="" />
												</c:when>
												<c:otherwise>
													<img src="<c:url value="/base/images/icon/ic_attach.gif" />" alt="" />
												</c:otherwise>
											</c:choose>
											<a href="<c:url value="/support/fileupload/downloadFile.do" />?fileId=${favorite.fileId}">${favorite.fileRealName}</a>
											</div>
											</li>
										</c:if>
										</c:forEach>
									</ul>
								</c:otherwise>
								</c:choose>
							</div>
								