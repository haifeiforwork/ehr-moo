<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:set var="preUi" 			value="ui.collpack.forum.bestList" />

<c:if test="${listType == 'Search' && pageCondition.pageIndex == 1 }">
	<ul class="forum_list mb15" id="itemFrame">
</c:if>

<c:forEach var="item" items="${itemList}" varStatus="loop">
	
	<c:if test="${comDiscussionId != item.frDiscussion.discussionId }">
		
		<c:if test="${loop.count > 1}">
				  </ul>					
		       </div>
			</li>
		</c:if>
		
		<li>
			<c:if test="${!empty(item.frDiscussion.categoryName) }"><span class="cate_block_1"><span class="cate_tit_2">${item.frDiscussion.categoryName}</span></span></c:if>
			<p><a href="getView.do?discussionId=${item.frDiscussion.discussionId }">${item.frDiscussion.title }</a></p> <span class="forum_topBox_num">(${item.frDiscussion.itemCount})</span>
			<span class="forum_name"><a href="#a" onclick="viewPopUpProfile('${item.frDiscussion.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.frDiscussion.registerName} ${item.frDiscussion.jobTitleName}</c:when><c:otherwise>${item.frDiscussion.userEnglishName} ${item.frDiscussion.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
			<span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.frDiscussion.teamName}</c:when><c:otherwise>${item.frDiscussion.teamEnglishName}</c:otherwise></c:choose></span>	
			<div class="forum_con_re">
				 <ul>
				 
		<c:set var="comDiscussionId" value="${item.frDiscussion.discussionId}"/>
			
	</c:if>
	
		<li class="lineRow">
			<span class="forum_con">
			<c:if test="${item.bestItem == 1}">
				<img src='<c:url value="/base/images/icon/ic_best.gif"/>' alt="best" /> 
			</c:if>
			<a href="getView.do?itemId=${item.itemId }">${item.title}</a></span>
			<span class="forum_name"><a href="#a" onclick="viewPopUpProfile('${item.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.registerName} ${item.jobTitleName}</c:when><c:otherwise>${item.userEnglishName} ${item.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
			<span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.teamName}</c:when><c:otherwise>${item.teamEnglishName}</c:otherwise></c:choose></span>
			<span class="forum_date"><ikep4j:timezone date="${item.registDate}" pattern="message.collpack.forum.timezone.dateformat.type2" keyString="true"/></span>
		</li>																								
														
	

</c:forEach>



<c:if test="${empty(itemList)}">
	<li><div><ul><li>
	
	<c:set var="requestUrl" value="${requestScope['javax.servlet.forward.request_uri']}"/>
	
	<c:choose>
		<c:when test="${fn:contains(requestUrl,'popularList.do')}">
			<ikep4j:message key='message.collpack.forum.nodata.item.popular'/>
		</c:when>
		<c:when test="${fn:contains(requestUrl,'bestList.do')}">
			<ikep4j:message key='message.collpack.forum.nodata.item.best'/>
		</c:when>
		<c:when test="${fn:contains(requestUrl,'lastList.do')}">
			<ikep4j:message key='message.collpack.forum.nodata.item.last'/>
		</c:when>
		<c:when test="${fn:contains(requestUrl,'myActivity.do')}">
			<c:choose>
				<c:when test="${param.tabType == 'itemBest' }">
					<ikep4j:message key='message.collpack.forum.nodata.my.best.item'/>
				</c:when>
				<c:otherwise>
					<ikep4j:message key='message.collpack.forum.nodata.my.item'/>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<ikep4j:message key='message.collpack.forum.noData.item'/>
		</c:otherwise>
	</c:choose>
	</li>
</c:if>
			
		</ul>					
      </div>
</li>

<c:if test="${listType == 'Search' && pageCondition.pageIndex == 1 }">
	</ul>
	<c:if test="${totalCount > 10}">
		<div class="blockButton_3" > 
			<a class="button_3" href="#a" onclick="listSearchMore('${totalCount}');" title="<ikep4j:message pre='${preUi}' key='more10'/>"><span id="moreText"><ikep4j:message pre='${preUi}' key='more10'/> <img src="<c:url value="/base/images/icon/ic_more_ar.gif"/>" alt="<ikep4j:message pre='${preUi}' key='more10'/>" /></span></a>				
		</div>
	</c:if>
</c:if>


