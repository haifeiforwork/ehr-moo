<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:forEach var="linereply" items="${linereplyList}" varStatus="loop">
	
	<c:if test="${comDiscussionId != linereply.frItem.frDiscussion.discussionId }">
		
		<c:if test="${loop.count > 1}">
				  </ul>						
				</div>											
			</li>
		</c:if>
		
		<li>
			<c:if test="${!empty(linereply.frItem.frDiscussion.categoryName) }"><span class="cate_block_1"><span class="cate_tit_2">${linereply.frItem.frDiscussion.categoryName}</span></span></c:if>
			<p><a href="getView.do?discussionId=${linereply.frItem.frDiscussion.discussionId }">${linereply.frItem.frDiscussion.title }</a></p> <span class="forum_topBox_num">(${linereply.frItem.frDiscussion.itemCount})</span>
			<span class="forum_name"><a href="#a" onclick="viewPopUpProfile('${linereply.frItem.frDiscussion.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${linereply.frItem.frDiscussion.registerName} ${linereply.frItem.frDiscussion.jobTitleName}</c:when><c:otherwise>${linereply.frItem.frDiscussion.userEnglishName} ${linereply.frItem.frDiscussion.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
			<span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${linereply.frItem.frDiscussion.teamName}</c:when><c:otherwise>${linereply.frItem.frDiscussion.teamEnglishName}</c:otherwise></c:choose></span>						
			<div class="forum_con_re">
				<ul>
			
		<c:set var="comDiscussionId" value="${linereply.frItem.frDiscussion.discussionId}"/>
		
	</c:if>
	
	<c:if test="${comItemId != linereply.frItem.itemId }">
	
		<li>
			<span class="forum_con">
			<c:if test="${linereply.frItem.bestItem == 1}">
				<img src='<c:url value="/base/images/icon/ic_best.gif"/>' alt="best" /> 
			</c:if>
			<a href="getView.do?itemId=${linereply.frItem.itemId }">${linereply.frItem.title }</a> <span class="forum_topBox_num">(${linereply.frItem.linereplyCount})</span></span>
			<span class="forum_name"><a href="#a" onclick="viewPopUpProfile('${linereply.frItem.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${linereply.frItem.registerName} ${linereply.frItem.jobTitleName}</c:when><c:otherwise>${linereply.frItem.userEnglishName} ${linereply.frItem.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
			<span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${linereply.frItem.teamName}</c:when><c:otherwise>${linereply.frItem.teamEnglishName}</c:otherwise></c:choose></span>
			<span class="forum_date"><ikep4j:timezone date="${linereply.frItem.registDate}" pattern="message.collpack.forum.timezone.dateformat.type2" keyString="true"/></span>
		</li>
		
		<c:set var="comItemId" value="${linereply.frItem.itemId}"/>
				
	</c:if>
	
		<li class="ml10 lineRow">
			<span class="forum_con">
			<c:if test="${linereply.bestLinereply == 1}">
				<img src='<c:url value="/base/images/icon/ic_best.gif"/>' alt="best" /> 
			</c:if>
			<a class="corporateBg" style="text-decoration: none;">${linereply.contents}</a></span>
			<span class="forum_name"><a href="#a" onclick="viewPopUpProfile('${linereply.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${linereply.registerName} ${linereply.jobTitleName}</c:when><c:otherwise>${linereply.userEnglishName} ${linereply.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
			<span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${linereply.teamName}</c:when><c:otherwise>${linereply.teamEnglishName}</c:otherwise></c:choose></span>
			<span class="forum_date"><ikep4j:timezone date="${linereply.registDate}" pattern="message.collpack.forum.timezone.dateformat.type2" keyString="true"/></span>
		</li>																								

</c:forEach>


<c:if test="${empty(linereplyList)}">
	<li><div><ul><li>
	
	<c:set var="requestUrl" value="${requestScope['javax.servlet.forward.request_uri']}"/>
	
	<c:choose>
		<c:when test="${fn:contains(requestUrl,'popularList.do')}">
			<ikep4j:message key='message.collpack.forum.nodata.line.popular'/>
		</c:when>
		<c:when test="${fn:contains(requestUrl,'bestList.do')}">
			<ikep4j:message key='message.collpack.forum.nodata.line.best'/>
		</c:when>
		<c:when test="${fn:contains(requestUrl,'lastList.do')}">
			<ikep4j:message key='message.collpack.forum.nodata.line.last'/>
		</c:when>
		<c:when test="${fn:contains(requestUrl,'myActivity.do')}">
			<c:choose>
				<c:when test="${param.tabType == 'linereplyBest' }">
					<ikep4j:message key='message.collpack.forum.nodata.my.best.line'/>
				</c:when>
				<c:otherwise>
					<ikep4j:message key='message.collpack.forum.nodata.my.line'/>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<ikep4j:message key='message.collpack.forum.noData.line'/>
		</c:otherwise>
	</c:choose>
	</li>
</c:if>
 	  	</ul>						
	</div>											
</li>


