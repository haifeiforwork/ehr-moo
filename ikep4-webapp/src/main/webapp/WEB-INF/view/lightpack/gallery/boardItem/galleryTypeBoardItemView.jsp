<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.lightpack.board.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="ui.lightpack.board.boardItem.listBoardView.list" />
<c:set var="preMessage" value="message.lightpack.common.boardItem" />
<c:set var="preSearch"  value="ui.ikep4.common.searchCondition" /> 

<c:set var="user"   value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
													
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
							<c:forEach var="boardItem" varStatus="varStatus" items="${searchResult.entity}"> 
								<li class="galleryViewList boardItemLine">
									<div class="galleryViewPhoto"> 
										<c:choose>
											<c:when test="${not empty boardItem.imageFileId}">
												<a class="image-gallery" href="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${boardItem.imageFileId}">
													<img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${boardItem.imageFileId}&amp;thumbnailYn=Y" alt="Image"/>
												</a>
											</c:when>
											<c:otherwise>
												<img src="<c:url value='/base/images/common/photo_130x95.gif'/>" alt="Image"/>
											</c:otherwise>
										</c:choose> 
									</div>
									<div class="galleryViewTitle ellipsis">										
										<c:if test="${targetUserId==user.userId}">
											<input name="checkboxBoardItem" class="checkbox" title="checkbox" type="checkbox" value="${boardItem.itemId}" />
										</c:if>
										<a class="boardItem"  href="<c:url value='/lightpack/gallery/boardItem/readBoardItemView.do?targetUserId=${targetUserId}&amp;pageIndex=${searchResult.pageIndex}&amp;itemId=${boardItem.itemId}&amp;popupYn=${popupYn}'/>">${boardItem.title}</a>
									</div> 						
									<div class="galleryViewInfo">
										<span class="galleryViewInfo_name">
											<c:choose>
												<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
													<a href="#a" title="${boardItem.user.userName} ${boardItem.user.jobTitleName} ${boardItem.user.teamName}" onclick="javascript:viewPopUpProfile('${boardItem.registerId}')">${boardItem.user.userName} ${boardItem.user.jobTitleName}</a>
												</c:when>
												<c:otherwise> 
													<a href="#a" title="${boardItem.user.userEnglishName} ${boardItem.user.jobTitleEnglishName} ${boardItem.user.teamEnglishName}" onclick="javascript:viewPopUpProfile('${boardItem.registerId}')">${boardItem.user.userEnglishName} ${boardItem.user.jobTitleEnglishName}</a>
												</c:otherwise>
											</c:choose>	 
										</span>
										<span class="galleryViewInfo_date"><ikep4j:timezone date="${boardItem.registDate}" /></span>
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