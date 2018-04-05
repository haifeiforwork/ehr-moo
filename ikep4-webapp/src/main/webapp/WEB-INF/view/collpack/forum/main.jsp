<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<c:set var="preUi" 			value="ui.collpack.forum.bestList" />

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">

	function moreList(){
		
		listMore('itemLastListMore.do', '${totalCount}');
	
	}
	
</script>

<h1 class="none">contens Area</h1>
 
<!--pageTitle Start-->
	<c:import url="/WEB-INF/view/collpack/forum/forumSearch.jsp" charEncoding="UTF-8" />
<!--//pageTitle End-->

<div id="tagResult">
<!--forum_topBox Start-->
<div class="forum_topBox">
	<div class="forum_topBox_c">
		
		<div class="forum_topBox_img">
			<div class="hotIssue"><img src="<c:url value="/base/images/common/img_hot.png"/>" alt="Hot Issue" /></div>
			<c:forEach var="discussion" items="${poplurDiscussionList}" varStatus="loop" >	
				<img width="150" height="150" src="<c:url value="/support/fileupload/downloadFile.do"/>?fileId=${discussion.imageId}&amp;thumbnailYn=Y" alt="image"/>
			</c:forEach>
		</div>
		<div class="forum_topBox_con">
			<c:forEach var="discussion" items="${poplurDiscussionList}" varStatus="loop">	
				<img src="<c:url value="/base/images/icon/ic_best.gif"/>" alt="best" />
			<h3>
				<a href="getView.do?discussionId=${discussion.discussionId }">${discussion.title}</a> <span class="forum_topBox_num">(${discussion.itemCount})</span> 
			</h3>
			</c:forEach>
			<c:if test="${empty(poplurDiscussionList) }">
				<ikep4j:message key='message.collpack.forum.popularItem.nodata'/>
			</c:if>
			<div class="forum_topBox_con_p">
				<c:forEach var="item" items="${poplurItemList}" varStatus="loop">
					<a href="getView.do?itemId=${item.itemId}">${item.title} </a>
				</c:forEach>
			</div>
			<div class="forum_con_re">
				<c:if test="${!empty(poplurLinereplyList) }">
				<ul>
				<c:forEach var="line" items="${poplurLinereplyList}" varStatus="loop">
					<li>
						<span class="forum_con">${line.contents}</span>
						<span class="forum_name"><a href="#a" onclick="viewPopUpProfile('${line.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${line.registerName} ${line.jobTitleName}</c:when><c:otherwise>${line.userEnglishName} ${line.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
						<span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${line.teamName}</c:when><c:otherwise>${line.teamEnglishName}</c:otherwise></c:choose></span>
					</li>
				</c:forEach>
				</ul>	
				</c:if>					
			</div>
		</div>
	</div>
	<div class="forum_topBox_sub">
		<h3><img src="<c:url value="/base/images/icon/ic_medal.gif"/>" alt="<ikep4j:message pre='${preUi}' key='mainCateDis'/>" /> <ikep4j:message pre='${preUi}' key='mainCateDis'/></h3>
		<div class="blockLeft" style="width:49%">
			<h4>${randomList1CategoryName}</h4>
			<c:if test="${!empty(randomList1) }">
			<ul>
				<c:forEach var="item" items="${randomList1}" varStatus="loop">
					<li><a href="getView.do?itemId=${item.itemId}">${item.title}</a> <span class="colorPoint"><c:if test="${item.linereplyCount > 0}">(${item.linereplyCount})</c:if></span></li>
				</c:forEach>
			</ul>
			</c:if>
		</div>
		<div class="blockRight" style="width:49%">
			<h4>${randomList2CategoryName}</h4>
			<c:if test="${!empty(randomList2) }">
			<ul>
				<c:forEach var="item" items="${randomList2}" varStatus="loop">
					<li><a href="getView.do?itemId=${item.itemId}">${item.title}</a> <span class="colorPoint"><c:if test="${item.linereplyCount > 0}">(${item.linereplyCount})</c:if></span></li>
				</c:forEach>
			</ul>
			</c:if>
		</div>	
		<div class="clear"></div>					
	</div>					
</div>
<!--//forum_topBox End-->

<!--subTitle_2 Start-->
<div class="cafeSubTitle border_b1">
	<h3><ikep4j:message pre='${preUi}' key='lastTitle'/></h3>
	<div class="cafe_sort">
		<span class="cafe_sort_smenu">
		</span>					
	</div>								
</div>
<!--//subTitle_2 End-->	

<ul class="forum_list mb15" id="itemFrame">
	<c:import url="/WEB-INF/view/collpack/forum/lastListMore.jsp" charEncoding="UTF-8" />
</ul>

<!--blockButton_3 Start-->
<c:if test="${totalCount > 10}">
<div class="blockButton_3"> 
	<a class="button_3" href="#a" onclick="moreList();return false;" title="<ikep4j:message pre='${preUi}' key='more10'/>"><span id="moreText"><ikep4j:message pre='${preUi}' key='more10'/> <img src="<c:url value="/base/images/icon/ic_more_ar.gif"/>" alt="<ikep4j:message pre='${preUi}' key='more10'/>" /></span></a>				
</div>
</c:if>
<!--//blockButton_3 End-->	
										
</div>
