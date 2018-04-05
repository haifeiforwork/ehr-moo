<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.lightpack.award.awardItem.listAwardView.header" /> 
<c:set var="preList"    value="ui.lightpack.award.awardItem.listAwardView.list" />
<c:set var="preMessage" value="message.lightpack.common.awardItem" />
<c:set var="preSearch"  value="ui.ikep4.common.searchCondition" /> 
<%-- 메시지 관련 Prefix 선언 End --%>
<table summary="<ikep4j:message pre="${preList}" key="summary" />">   
	<caption></caption> 
	<tbody>
		<c:choose>
		    <c:when test="${searchResult.emptyRecord}">
				<tr>
					<td class="emptyRecord"><ikep4j:message pre='${preList}' key='emptyRecord' /></td> 
				</tr>				        
		    </c:when>
		    <c:otherwise>  
				<tr class="bgWhite">
					<td>
						<ul> 
							<c:forEach var="awardItem" varStatus="varStatus" items="${searchResult.entity}"> 
								<li class="galleryViewList awardItemLine">
									<div class="galleryViewPhoto"> 
										<c:choose>
											<c:when test="${not empty awardItem.imageFileId}">
												<a class="image-gallery" href="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${awardItem.imageFileId}">
													<img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${awardItem.imageFileId}&amp;thumbnailYn=Y" alt="Image"/>
												</a>
											</c:when>
											<c:otherwise>
												<img src="<c:url value='/base/images/common/photo_130x95.gif'/>" alt="Image"/>
											</c:otherwise>
										</c:choose> 
									</div>
									<div class="galleryViewTitle ellipsis">										
										<c:if test="${permission.isSystemAdmin}"> <%--[관리자]일괄 삭제를 위한 체크박스 허용--%>
											<input name="checkboxAwardItem" class="checkbox" title="checkbox" type="checkbox" value="${awardItem.itemId}" />
										</c:if>
										<c:choose>
											<c:when test="${awardItem.itemDelete eq 1}"><%-- 논리적으로 삭제된 글 --%>
												<c:choose>
											 		<c:when test="${permission.isSystemAdmin}"><%--  관리자, 작성자일 경우  --%>													 			
												 		<a class="awardItem deletedItem" href="<c:url value='/lightpack/award/awardItem/readAwardItemView.do?awardId=${awardItem.awardId}&amp;itemId=${awardItem.itemId}&amp;popupYn=${popupYn}'/>"><span class="deletedItem"><c:if test="${awardItem.itemDisplay eq 1 and awardItem.indentation eq 0}"><ikep4j:message pre='${preList}' key='notice' /></c:if>[<ikep4j:message pre='${preList}' key='itemDelete' post="deleteContents"/>]${awardItem.title}</span></a>													 			 							
													</c:when>
											 		<c:otherwise>
												 		<span class="deletedItem"><ikep4j:message pre='${preList}' key='itemDelete' post="deleteContents"/></span>
													</c:otherwise> 
												</c:choose>  
											</c:when>
											<c:otherwise> 
											 	<a class="awardItem"  href="<c:url value='/lightpack/award/awardItem/readAwardItemView.do?awardId=${awardItem.awardId}&amp;itemId=${awardItem.itemId}&amp;popupYn=${popupYn}'/>"><c:if test="${awardItem.itemDisplay eq 1 and awardItem.indentation eq 0}"><ikep4j:message pre='${preList}' key='notice' /></c:if>${awardItem.title}</a>
											</c:otherwise>
										</c:choose>							
									</div> 						
									<div class="galleryViewInfo">
										<span class="galleryViewInfo_name">
											<c:choose>
										 		<c:when test="${award.anonymous eq 1}">
													<span><!--<ikep4j:message pre='${preList}' key='anonymous'/>-->
													<c:if test="${awardItem.awardId=='100006240370' || awardItem.awardId=='100006259597'}">Happy맨</c:if>
													<c:if test="${awardItem.awardId=='100010083357' || awardItem.awardId=='100010089350' || awardItem.awardId=='100010089362'}">회계정보팀</c:if>
													<c:if test="${awardItem.awardId!='100006240370' && awardItem.awardId!='100006259597' && awardItem.awardId!='100010083357' && awardItem.awardId!='100010089350' && awardItem.awardId!='100010089362'}">${awardItem.registerName}</c:if>
													</span>
												</c:when>   
												<c:otherwise> 
													<c:set var="user"   value="${sessionScope['ikep.user']}" />
													<c:set var="portal" value="${sessionScope['ikep.portal']}" />
													<c:choose>
														<c:when test="${user.localeCode == portal.defaultLocaleCode}"> <!--  ${awardItem.user.jobTitleName} ${awardItem.user.teamName} -->
															<c:if test="${awardItem.awardId=='100006240370' || awardItem.awardId=='100006259597'}">Happy맨</c:if>
															<c:if test="${awardItem.awardId=='100010083357' || awardItem.awardId=='100010089350' || awardItem.awardId=='100010089362'}">회계정보팀</c:if>
															<c:if test="${awardItem.awardId!='100006240370' && awardItem.awardId!='100006259597' && awardItem.awardId!='100010083357'}">
															<a href="#a" title="${awardItem.user.userName}" onclick="iKEP.showUserContextMenu(this, '${awardItem.registerId}', 'bottom')">${awardItem.user.userName}<!--  ${awardItem.user.jobTitleName} --></a>
															</c:if>
														</c:when>
														<c:otherwise> 
															<c:if test="${awardItem.awardId=='100006240370' || awardItem.awardId=='100006259597'}">Happy맨</c:if>
															<c:if test="${awardItem.awardId=='100010083357' || awardItem.awardId=='100010089350' || awardItem.awardId=='100010089362'}">회계정보팀</c:if>
															<c:if test="${awardItem.awardId!='100006240370' && awardItem.awardId!='100006259597' && awardItem.awardId!='100010083357'}">
															<a href="#a" title="${awardItem.user.userEnglishName}" onclick="iKEP.showUserContextMenu(this, '${awardItem.registerId}', 'bottom')">${awardItem.user.userEnglishName}<!--  ${awardItem.user.jobTitleEnglishName} --></a>
															</c:if>
														</c:otherwise>
													</c:choose>	 
												</c:otherwise> 
											</c:choose>
										</span>
										<span class="galleryViewInfo_date"><ikep4j:timezone date="${awardItem.registDate}" /></span>
									</div>										
								</li> 
							</c:forEach>  
						</ul>
					</td>
				</tr>  	      
		    </c:otherwise> 
		</c:choose>  
	</tbody>
</table>  