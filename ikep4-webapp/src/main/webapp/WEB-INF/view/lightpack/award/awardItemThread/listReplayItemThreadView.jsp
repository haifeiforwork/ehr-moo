<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.lightpack.award.awardItem.readAwardView.header" /> 
<c:set var="preDetail"  value="ui.lightpack.award.awardItem.readAwardView.detailAwardItem" />
<c:set var="preButton"  value="ui.lightpack.common.button" /> 
<c:set var="preMessage" value="message.lightpack.common.awardItem" /> 
<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>    
<c:if test="${ not empty awardItemList and fn:length(awardItemList) gt 1 }"> 
		<div class="blockReply_t"><ikep4j:message pre='${preDetail}' key='replyItemList' /><span class="comment_num">(${fn:length(awardItemList)})</span></div> 
		<ul> 
			<c:forEach var="replyItem" items="${awardItemList}">
			<li>    
				<span class="indent_${replyItem.indentation}"> 
					<c:choose> 
						<c:when test="${replyItem.itemDelete eq 1 and (replyItem.itemId eq itemId)}"><%-- 논리적으로 삭제된 글 --%>
							<c:choose>
						 		<c:when test="${permission.isSystemAdmin}"><%--  관리자, 작성자일 경우  --%>
						 			<strong><span class="deletedItem">[<ikep4j:message pre='${preDetail}' key='itemDelete' post="deleteContents"/>]</span> ${replyItem.title}</strong>
								</c:when>
						 		<c:otherwise>
						 			<strong><span class="deletedItem"><ikep4j:message pre='${preDetail}' key='itemDelete' post="deleteContents"/></span></strong>					
								</c:otherwise> 
							</c:choose>  
						</c:when>  
						<c:when test="${replyItem.itemDelete eq 1 and (replyItem.itemId ne itemId)}"><%-- 논리적으로 삭제된 글 --%>
							<c:choose>
						 		<c:when test="${permission.isSystemAdmin}"><%--  관리자, 작성자일 경우  --%>
						 			<a href="<c:url value='/lightpack/award/awardItem/readAwardItemView.do?awardId=${replyItem.awardId}&amp;itemId=${replyItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span class="deletedItem">[<ikep4j:message pre='${preDetail}' key='itemDelete' post="deleteContents"/>]</span> ${replyItem.title}</a>
								</c:when>
						 		<c:otherwise>
						 			<span class="deletedItem"><ikep4j:message pre='${preDetail}' key='itemDelete' post="deleteContents"/></span>					
								</c:otherwise> 
							</c:choose>  
						</c:when>  
						<c:when test="${replyItem.itemDelete eq 0 and (replyItem.itemId eq itemId)}"><%-- 논리적으로 삭제된 글 --%>
						 	<strong>${replyItem.title}</strong> 
						</c:when>  
						<c:when test="${replyItem.itemDelete eq 0 and (replyItem.itemId ne itemId)}"><%-- 논리적으로 삭제된 글 --%>
						 	<a href="<c:url value='/lightpack/award/awardItem/readAwardItemView.do?awardId=${replyItem.awardId}&amp;itemId=${replyItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>">${replyItem.title}</a>
					    </c:when>   
					</c:choose>	 
					<c:if test="${replyItem.itemId eq itemId}"></strong></c:if> 
					<c:if test="${replyItem.linereplyCount > 0}"><span class="colorPoint">(${replyItem.linereplyCount})</span></c:if>	 
				</span> 	
				<span class="summaryViewInfo"> 
					<span class="summaryViewInfo_name"> 
						<c:choose>
							<c:when test="${award.anonymous eq 1}">
								<span><ikep4j:message pre='${preDetail}' key='anonymous'/></span>
							</c:when>  
							<c:otherwise>
								<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
										<a href="#a" onclick="javascript:viewPopUpProfile('${replyItem.registerId}');">
										${replyItem.user.userName} ${replyItem.user.jobTitleName}
										</a>&nbsp;
										${replyItem.user.teamName}
									</c:when>
									<c:otherwise> 
										<a href="#a" onclick="javascript:viewPopUpProfile('${replyItem.registerId}');">
										${replyItem.user.userEnglishName} ${replyItem.user.jobTitleEnglishName}
										</a>&nbsp;
										 ${replyItem.user.teamEnglishName}
									</c:otherwise>
								</c:choose> 
							</c:otherwise> 
						</c:choose> 
					</span>
					<span><ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${replyItem.registDate}"/></span>
					<span><ikep4j:message pre='${preDetail}' key='hitCount' /> <strong>${replyItem.hitCount}</strong></span> 
				
                    <span><ikep4j:message pre='${preDetail}' key='recommendCount' /> <strong>${replyItem.recommendCount}</strong></span>	
				</span> 
			</li>
			</c:forEach>
		</ul>	 
</c:if> 