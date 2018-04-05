<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preUi" value="ui.collpack.qna.qnaMoreList" />

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />


<c:if test="${listType == 'Search' }">
<div class="qna_sub" id="tagResult">

	<!--qna_bl Start-->
	<div class="qna_bl block">						
		<div class="qna_mainlist line">
			<table summary="">
				<tbody id="listFrame">
</c:if>
					
<c:forEach var="qna" items="${qnaList}" varStatus="qnaLoopCount">
		<c:choose>		<%--질문일때 답변일때 class 변경 --%>
			<c:when test="${qnaLoopCount.count == 1}">
				<c:set var="className" value="tdfirst"/>
			</c:when>
			<c:when test="${qnaLoopCount.count == 10}">
				<c:set var="className" value="tdend"/>
			</c:when>
			<c:otherwise>
				<c:set var="className" value=""/>
			</c:otherwise>
		</c:choose>
		
	<tr class="">
		<td width="30">
			<div class="qna_mainlist_photo">
				<div class="ic_question"><img src="<c:url value="/base/images/icon/ic_question.png"/>" alt="question" /></div>
				<div class="photoimg"><a href="#a" onclick="viewPopUpProfile('${qna.registerId}');return false;" title="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${qna.registerName} ${qna.jobTitleName}</c:when><c:otherwise>${qna.userEnglishName} ${qna.jobTitleEnglishName}</c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${qna.teamName}</c:when><c:otherwise>${qna.teamEnglishName}</c:otherwise></c:choose>"><!-- <img src="<c:url value='${qna.profilePicturePath}'/>" alt="profileImage" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'"  /> --></a></div>
			</div>
		</td>
		<td>
			<div class="qna_mainlist_q">
			<c:choose>
	 			<c:when test="${qna.status  == 0}">
	 				<span class="status_None"><ikep4j:message pre='${preUi}' key='listStatusN'/></span>
	 			</c:when>
	 			<c:when test="${qna.status  == 1}">
	 				<span class="status_Ing"><ikep4j:message pre='${preUi}' key='listStatusA'/></span>
	 			</c:when>
	 			<c:when test="${qna.status  == 2}">
	 				<span class="status_Done"><ikep4j:message pre='${preUi}' key='listStatusSolve'/></span>
	 			</c:when>
	 		</c:choose>
			
				<c:if test="${qna.urgent == 1}">
 					<img class="imgtable" src="<c:url value="/base/images/icon/ic_emer.gif"/>" alt="<ikep4j:message pre='${preUi}' key='listStatusUrgent'/>" />
 				</c:if>
 			
 			<c:choose>
	 			<c:when test="${qna.itemDelete  == 1}">
	 			
		 			<c:choose>
						<c:when test="${qna.qnaType == 0}">
							<c:set var="delText"><ikep4j:message pre='${preUi}' key='listDelText'/></c:set>
						</c:when>
						<c:otherwise>
							<c:set var="delText"><ikep4j:message pre='${preUi}' key='listDelText.answer'/></c:set>
						</c:otherwise>
					</c:choose>
			 					
		 			<c:choose> <%--삭제된 글인지 체크 --%>
			 			<c:when test="${isAdmin}">
			 				<a href="#a" onclick="goDetail('${qna.qnaGroupId}');return false;" title="${delText }" >
			 					<span class="deletedItem">${delText }</span>
			 				</a> 
			 			</c:when>
			 			<c:otherwise>	
			 				<span class="deletedItem">${delText }</span>
			 			</c:otherwise>
			 		</c:choose>
	 			</c:when>
	 			<c:otherwise>	
	 				<a href="#a" onclick="goDetail('${qna.qnaGroupId}');return false;" title="${qna.title}">${qna.title}</a> 
	 			</c:otherwise>
	 		</c:choose>
			
				<c:if test="${qna.bestFlag  == 1}">
					<img src="<c:url value="/base/images/icon/ic_best.gif"/>" alt="best" />
				</c:if>
				<c:if test="${qna.qnaType == 0 && qna.replyCount > 0}">
					(${qna.replyCount})	
				</c:if>
			</div>
			
			<span class="qna_mainlist_name"><a href="#a" onclick="viewPopUpProfile('${qna.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${qna.registerName} ${qna.jobTitleName}</c:when><c:otherwise>${qna.userEnglishName} ${qna.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
			<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
			<span class="qna_mainlist_name"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${qna.teamName}</c:when><c:otherwise>${qna.teamEnglishName}</c:otherwise></c:choose></span>
			<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
			<span class="qna_mainlist_date"><ikep4j:timezone date="${qna.registDate}" pattern="message.collpack.qna.timezone.dateformat.type2" keyString="true"/></span>					
		</td>
		<td width="130" class="textRight">
			<div class="qna_mainlist_num">
				<ikep4j:message pre='${preUi}' key='listHit'/> <span class="colorPoint">(${qna.hitCount})</span>
				<img src="<c:url value="/base/images/theme/theme01/basic/bar_pageNum.gif"/>" alt="bar" />
				<ikep4j:message pre='${preUi}' key='listRecommend'/> <span class="colorPoint">(${qna.recommendCount})</span>
			</div>
		</td>
	</tr>
	</c:forEach>

<c:if test="${listType == 'Search' }">
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!--//qna_bl End-->	
		
	<c:if test="${count > 10}">
		<!--blockButton_3 Start-->
		<div class="blockButton_3"> 
			<a class="button_3" href="#a" onclick="searchMore('${count}');return false;" title="more"><span id="moreText"><ikep4j:message pre='${preUi}' key='listMore'/> <img src="<c:url value="/base/images/icon/ic_more_ar.gif"/>" alt="more" /></span></a>				
		</div>
		<!--//blockButton_3 End-->	
	</c:if>	
</c:if>
