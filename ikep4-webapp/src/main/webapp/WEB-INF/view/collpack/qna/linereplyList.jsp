<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:set var="preList" 	value="ui.collpack.qna.linereplyList.list" />

<p style="display:none;" count="${count}"/>	

<c:set var="backIndentation" value="0"/>
<c:set var="backVal" value="1"/>
<c:forEach var="reply" items="${replyList}" varStatus="qnaLoopCount">
	
	<c:if test="${qnaLoopCount.count != 1 && reply.indentation <= backIndentation}">
		<c:set var="endVal" value="${backIndentation - reply.indentation}"/>
		<c:forEach begin="0" end="${endVal}">
			</div>	
		</c:forEach>
		<c:set var="backVal" value="${backVal - backIndentation - reply.indentation +1}"/>
	</c:if>
	
		<c:if test="${reply.indentation > backIndentation}">
			<c:set var="backVal" value="${backVal +1}"/>
		</c:if>
	 	
		<c:choose>
		 	<c:when test="${reply.indentation == 0 }">
				<div class="blockComment_c">
			</c:when>
			<c:otherwise>
				<div class="blockComment_re ">
				<div class="reply_ar"><img src="<c:url value="/base/images/icon/ic_reply_ar.gif"/>" alt="reply" /></div>
			</c:otherwise>
		</c:choose>
		<div class="blockCommentInfo">
			<span class="blockCommentInfo_name"><a href="#a" onclick="viewPopUpProfile('${reply.registerId}');return false;" title=""><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${reply.registerName} ${reply.jobTitleName}</c:when><c:otherwise>${reply.userEnglishName} ${reply.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
			<img src="<c:url value="/base/images/theme/theme01/basic/bar_pageNum.gif"/>" alt="bar" />						
			<span><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${reply.teamName}</c:when><c:otherwise>${reply.teamEnglishName}</c:otherwise></c:choose></span>
			<img src="<c:url value="/base/images/theme/theme01/basic/bar_pageNum.gif"/>" alt="bar" />
			<span><ikep4j:timezone date="${reply.registDate}" pattern="message.collpack.qna.timezone.dateformat.type2" keyString="true"/></span>
			
			<span class="rebtn">
				<c:if test="${reply.indentation < 2}">
				<a href="#a" onclick="showLinereplyAddForm('${reply.linereplyId}', '${reply.qnaId}');return false;" title="<ikep4j:message pre='${preList}' key='create'/>" class="ic_reply"><span><ikep4j:message pre='${preList}' key='create'/></span></a>
				</c:if>
			<c:if test="${isAdmin || user.userId == reply.registerId }">
				<a href="#a" onclick="linereplyDel('${reply.linereplyId}', '${reply.qnaId}');return false;" title="<ikep4j:message pre='${preList}' key='delete'/>" class="ic_delete"><span><ikep4j:message pre='${preList}' key='delete'/></span></a>
				
				<c:choose>
				 	<c:when test="${reply.indentation == 0 }">
						<c:set var="funcName" value="linereplyUpdateForm"/>
					</c:when>
					<c:otherwise>
						<c:set var="funcName" value="linereplyReplyUpdateForm"/>
					</c:otherwise>
				</c:choose>
				
				<c:if test="${reply.linereplyDelete != 1 }">
					<a href="#a" onclick="${funcName }('${reply.linereplyId}', '${reply.qnaId}');return false;" title="<ikep4j:message pre='${preList}' key='update'/>" class="ic_modify"><span><ikep4j:message pre='${preList}' key='update'/></span></a>
				</c:if>
			</c:if>
			</span>
			
		</div>
		<p id="linereplyContents_${reply.linereplyId}">
			<c:choose>
	 			<c:when test="${reply.linereplyDelete == 1}">
	 				<span class="deletedItem"><ikep4j:message pre='${preList}' key='deleteMessage'/></span>
	 			</c:when>
	 			<c:otherwise>
	 				${reply.contents}
	 			</c:otherwise>
	 		</c:choose>
		</p>
		
	<c:set var="backIndentation" value="${reply.indentation}"/>
	<c:if test="${qnaLoopCount.count == 1}">
		<c:set var="qnaId" value="${reply.qnaId}"/>
	</c:if>
	
</c:forEach>	

<c:forEach begin="0" end="${backIndentation}">
	</div>	
</c:forEach>

<c:if test="${count > 0 }">
	<!--Page Numbur Start--> 
	<spring:bind path="searchCondition.pageIndex">
	<ikep4j:pagination searchFormId="lineForm_${qnaId}" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" ajaxEventFunctionName="goLinePage"/>
	<form id="lineForm_${qnaId}">
		<input name="${status.expression}" type="hidden"  value="${status.value}" />
		<input name="qnaId" type="hidden"  value="${qnaId}" title="qnaId" />
	</form>
	</spring:bind> 
	<!--//Page Numbur End-->
	
</c:if>
