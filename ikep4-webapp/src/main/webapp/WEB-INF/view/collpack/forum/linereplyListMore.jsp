<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preList" 	value="ui.collpack.forum.linereplyList.list" />

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:if test="${!empty(linereplyList) }">
	<script type="text/javascript">
	//<![CDATA[
		$jq('#lineReply_${linereplyList[0].itemId}').data("totalCount",'${totalCount}');
	//]]>	
	</script>
</c:if>

<c:set var="backIndentation" value="0"/>
<c:set var="backVal" value="1"/>
<c:forEach var="reply" items="${linereplyList}" varStatus="loop">
<c:set var="itemId" value="${reply.itemId}"/>
	<c:if test="${loop.count != 1 && reply.indentation <= backIndentation}">
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
		<div class="blockCommentPhoto"><img src="<c:url value='${reply.profilePicturePath}'/>" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" alt="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${reply.registerName} ${reply.jobTitleName}</c:when><c:otherwise>${reply.userEnglishName} ${reply.jobTitleEnglishName}</c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${reply.teamName}</c:when><c:otherwise>${reply.teamEnglishName}</c:otherwise></c:choose>"  /></div>
		<div class="blockCommentInfo">
			<span class="blockCommentInfo_name"><a href="#a" onclick="viewPopUpProfile('${reply.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${reply.registerName} ${reply.jobTitleName}</c:when><c:otherwise>${reply.userEnglishName} ${reply.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
			<img src="<c:url value="/base/images/theme/theme01/basic/bar_pageNum.gif"/>" alt="bar" />						
			<span><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${reply.teamName}</c:when><c:otherwise>${reply.teamEnglishName}</c:otherwise></c:choose></span>
			<img src="<c:url value="/base/images/theme/theme01/basic/bar_pageNum.gif"/>" alt="bar" />
			<span><ikep4j:timezone date="${reply.registDate}" pattern="message.collpack.forum.timezone.dateformat.type2" keyString="true"/></span>
			<img src="<c:url value="/base/images/theme/theme01/basic/bar_pageNum.gif"/>" alt="bar" />
			<span><ikep4j:message pre='${preList}' key='recommend'/> ${reply.recommendCount}</span>
			
			<span class="rebtn">
				<c:if test="${reply.indentation < 2}">
				<a href="#a" onclick="showLinereplyAddForm('${reply.linereplyId}', '${reply.itemId}');return false;" title="<ikep4j:message pre='${preList}' key='create'/>" class="ic_reply"><span><ikep4j:message pre='${preList}' key='create'/></span></a>
				</c:if>
			<c:if test="${isAdmin || (user.userId == reply.registerId )}">
				<a href="#a" onclick="linereplyDel('${reply.linereplyId}', '${reply.itemId}');return false;" title="<ikep4j:message pre='${preList}' key='delete'/>" class="ic_delete"><span><ikep4j:message pre='${preList}' key='delete'/></span></a>
				
				<c:choose>
				 	<c:when test="${reply.indentation == 0 }">
						<c:set var="funcName" value="linereplyUpdateForm"/>
					</c:when>
					<c:otherwise>
						<c:set var="funcName" value="linereplyReplyUpdateForm"/>
					</c:otherwise>
				</c:choose>
				
				<c:if test="${reply.linereplyDelete != 1 }">
					<a href="#a" onclick="${funcName }('${reply.linereplyId}', '${reply.itemId}');return false;" title="<ikep4j:message pre='${preList}' key='update'/>" class="ic_modify"><span><ikep4j:message pre='${preList}' key='update'/></span></a>
				</c:if>
			</c:if>
			</span>
			<c:if test="${user.userId != reply.registerId }">
			<span><a href="#a" class="valign_middle" onclick="recomm('${reply.linereplyId}');"><img src="<c:url value="/base/images/icon/ic_recommend.gif"/>" alt="" /></a></span>
			</c:if>
		</div>
		<p id="linereplyContents_${reply.linereplyId}">
			<c:if test="${linereply.bestLinereply == 1}">
				<img src='<c:url value="/base/images/icon/ic_best.gif"/>' alt="best" /> 
			</c:if>
				
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
		<c:set var="itemId" value="${reply.itemId}"/>
	</c:if>
	
</c:forEach>	
<c:if test="${!empty(linereplyList) }">
	<c:forEach begin="0" end="${backIndentation}">
		</div>	
	</c:forEach>
</c:if>

<c:if test="${totalCount > 0 }">
	<!--Page Numbur Start--> 
	<spring:bind path="linereplyCondition.pageIndex">
	<ikep4j:pagination searchFormId="lineForm_${itemId}" pageIndexInput="${status.expression}" searchCondition="${linereplyCondition}" ajaxEventFunctionName="goLinePage"/>
	<form id="lineForm_${itemId}" action="">
		<input name="${status.expression}" type="hidden"  value="${status.value}" />
		<input name="itemId" type="hidden"  value="${itemId}" title="itemId" />
	</form>
	</spring:bind> 
	<!--//Page Numbur End-->
	
</c:if>	
