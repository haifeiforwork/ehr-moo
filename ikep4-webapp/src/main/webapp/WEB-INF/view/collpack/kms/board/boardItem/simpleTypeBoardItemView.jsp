<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.board.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="message.collpack.collaboration.board.boardItem.listBoardView.list" />
<c:set var="preCode"    value="message.collpack.collaboration.common.code" />
<c:set var="preButton"  value="message.collpack.collaboration.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.boardItem" />
<c:set var="preSearch"  value="message.collpack.collaboration.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%> 
<!--blockListTable Start--> 
<table summary="<ikep4j:message pre="${preList}" key="summary" />">
	<caption></caption>	 
	<tbody>
		<c:choose>
		    <c:when test="${searchResult.emptyRecord}">
				<tr class="bgWhite">
					<td colspan="2" class="emptyRecord"><ikep4j:message pre='${preList}' key='emptyRecord' /></td> 
				</tr>				        
		    </c:when>
		    <c:otherwise>
				<c:forEach var="boardItem" items="${searchResult.entity}"> 
				<tr class="bgWhite boardItemLine"> 
					<c:if test="${permission.isSystemAdmin}"> <%--[관리자]일괄 삭제를 위한 체크박스 허용--%>
						<td style="width:20px; padding: 5px;"><input name="checkboxBoardItem" class="checkbox" title="checkbox" type="checkbox" value="${boardItem.itemId}" /></td>
					</c:if>	 			
					<td class="textLeft">
						<div class="summaryViewTitle">
							<c:choose>
								<c:when test="${boardItem.itemDelete eq 1}"><%-- 논리적으로 삭제된 글 --%>
									<c:choose>
								 		<c:when test="${permission.isSystemAdmin}"><%--  관리자, 작성자일 경우  --%>
								 			<span class="deletedItem">
									 			<a class="boardItem" href="<c:url value='/collpack/collaboration/board/boardItem/readBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>">[<ikep4j:message pre='${preList}' key='itemDelete' post="deleteContents"/>]<c:if test="${boardItem.itemDisplay eq 1 and boardItem.indentation eq 0}"><ikep4j:message pre='${preList}' key='notice' /></c:if>${boardItem.title}</a>
									 			<c:if test="${boardItem.linereplyCount gt 0}"><span class="colorPoint">(${boardItem.linereplyCount})</span></c:if>
								 			</span>
										</c:when>
								 		<c:otherwise>
								 			<span class="deletedItem"><ikep4j:message pre='${preList}' key='itemDelete' post="deleteContents"/></span>  
										</c:otherwise> 
									</c:choose>  
								</c:when>
								<c:otherwise>
								 	 <a class="boardItem" href="<c:url value='/collpack/collaboration/board/boardItem/readBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><c:if test="${boardItem.itemDisplay eq 1 and boardItem.indentation eq 0}"><ikep4j:message pre='${preList}' key='notice' /></c:if>${boardItem.title}</a> 
									 <c:if test="${boardItem.linereplyCount gt 0}"><span class="colorPoint">(${boardItem.linereplyCount})</span></c:if>
								</c:otherwise> 
							</c:choose>  
						</div>
						<div class="summaryViewInfo">
							<c:if test="${boardItem.attachFileCount > 0}"><img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" /></c:if>
							<span>
								<c:choose>
							 		<c:when test="${board.anonymous eq 1}">
										<span><ikep4j:message pre='${preList}' key='anonymous'/></span>
									</c:when>  
									<c:otherwise>
										<c:set var="user"   value="${sessionScope['ikep.user']}" />
										<c:set var="portal" value="${sessionScope['ikep.portal']}" />
										<c:choose>
											<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
												<span class="summaryViewInfo_name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom')">${boardItem.user.userName} ${boardItem.user.jobTitleName}</a></span> ${boardItem.user.teamName}
											</c:when>
											<c:otherwise> 
												<span class="summaryViewInfo_name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom')">${boardItem.user.userEnglishName} ${boardItem.user.jobTitleEnglishName}</a></span> ${boardItem.user.teamEnglishName}
											</c:otherwise>
										</c:choose>										 
									</c:otherwise> 
								</c:choose>  
							</span>							
							<span class="summaryViewInfo_date"><ikep4j:timezone date="${boardItem.registDate}" pattern="yyyy.MM.dd HH:mm:ss"/></span>							
							<span><ikep4j:message pre='${preList}' key='hitCount' /> <strong>${boardItem.hitCount}</strong></span>							
							<span><ikep4j:message pre='${preList}' key='recommendCount' /> <strong>${boardItem.recommendCount}</strong></span>
						</div> 
						<div class="summaryViewTag"><span class="ic_tag"><span><ikep4j:message pre='${preList}' key='tagList' /> </span></span>
					        <c:forEach var="tag" items="${boardItem.tagList}" varStatus="tagLoop">
					        	<c:if test="${tagLoop.count != 1}">, </c:if>
					        	<a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a>
					        </c:forEach> 
						</div> 
					</td>
				</tr>  
				</c:forEach>				      
		    </c:otherwise> 
		</c:choose>  
	</tbody> 
</table>