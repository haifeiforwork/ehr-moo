<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.collpack.forum.constants.ForumConstants" %>

<c:set var="preUi" 			value="ui.collpack.forum.myActivity" />

<c:set var="isItem" value="<%=ForumConstants.TAG_TYPE_ITEM%>"/>
<c:set var="isLine" value="<%=ForumConstants.TAP_TYPE_LINEREPLY%>"/>
<c:set var="isDis" value="<%=ForumConstants.TAP_TYPE_DISCUSSION%>"/>
<c:set var="isItemBest" value="<%=ForumConstants.TAP_TYPE_ITEM_BEST%>"/>
<c:set var="isLineBest" value="<%=ForumConstants.TAP_TYPE_LINEREPLY_BEST%>"/>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:if test="${empty(totalCount) }">
	<c:set var="totalCount" value="${linereplyTotalCount}"/>
</c:if>

<script type="text/javascript">
//<![CDATA[
	
	var selectVal = 0;
	
	var selectVal = "";
	var tabType = "${param.tabType}";
	
	if(tabType == "<%=ForumConstants.TAP_TYPE_LINEREPLY%>"){
		selectVal = 1;
	} else if(tabType == "<%=ForumConstants.TAP_TYPE_DISCUSSION%>"){
		selectVal = 2;
	} else if(tabType == "<%=ForumConstants.TAP_TYPE_ITEM_BEST%>"){
		selectVal = 3;
	} else if(tabType == "<%=ForumConstants.TAP_TYPE_LINEREPLY_BEST%>"){
		selectVal = 4;
	} else {
		selectVal = 0;
	}
	
	$jq(document).ready(function() {
		$jq("#divTab1").tabs({     
			selected : selectVal,    
			cache : true,     
			select : function(event, ui) {   
				
					if(ui.index == 0){
						goPage('<%=ForumConstants.TAG_TYPE_ITEM%>');
					} else if (ui.index == 1){
						goPage('<%=ForumConstants.TAP_TYPE_LINEREPLY%>');
					} else if (ui.index == 2) {
						goPage('<%=ForumConstants.TAP_TYPE_DISCUSSION%>');
					} else if (ui.index == 3) {
						goPage('<%=ForumConstants.TAP_TYPE_ITEM_BEST%>');
					} else if (ui.index == 4) {
						goPage('<%=ForumConstants.TAP_TYPE_LINEREPLY_BEST%>');
					}
					
			},     
			load : function(event, ui) {        
				//iKEP.debug(ui);     
				} 
		});

		$jq('#tagResult').data('totalCount', '${totalCount}');
	});

	function goPage(val){
		location.href = "myActivity.do?tabType="+val;
	}
	
	
	function moreList(){
		
		var url = "";
		if(tabType == "<%=ForumConstants.TAP_TYPE_LINEREPLY%>"){
			url = "linereplyListMore.do?isMy=1";
		} else if(tabType == "<%=ForumConstants.TAP_TYPE_DISCUSSION%>"){
			url = "discussionListMore.do?isMy=1";
		} else if(tabType == "<%=ForumConstants.TAP_TYPE_ITEM_BEST%>"){
			url = "itemDisListMore.do?isMy=1&isBest=1";
		} else if(tabType == "<%=ForumConstants.TAP_TYPE_LINEREPLY_BEST%>"){
			url = "linereplyListMore.do?isMy=1&isBest=1";
		} else {
			url = "itemDisListMore.do?isMy=1";
		}
		
		listMore(url, '${totalCount}');
	
	}
	//]]>	
	
</script>

<h1 class="none">Contents Area</h1>
 
<!--pageTitle Start-->
	<c:import url="/WEB-INF/view/collpack/forum/forumSearch.jsp" charEncoding="UTF-8" />
<!--//pageTitle End-->

<div id="tagResult">

<c:choose>
	<c:when test="${empty(myActivities) }">
		<c:set var="actiUserId" 		value="${userInfo.userId }"/>
		<c:set var="actiTeamName" 		value="${userInfo.teamName }"/>
		<c:set var="actiTeamEngName" 	value="${userInfo.teamEnglishName }"/>
		<c:set var="actiUserName" 		value="${userInfo.userName }"/>
		<c:set var="actiUserEngName" 	value="${userInfo.userEnglishName }"/>
		<c:set var="actiJobTitle" 		value="${userInfo.jobTitleName }"/>
		<c:set var="actiJobEngTitle" 	value="${userInfo.jobTitleEnglishName }"/>
		<c:set var="actiPicturePath" 	value="${userInfo.picturePath }"/>
		<c:set var="actiProfilePicturePath" 	value="${userInfo.profilePicturePath }"/>
	</c:when>
	<c:otherwise>
		<c:set var="actiUserId" 		value="${myActivities.userId }"/>
		<c:set var="actiTeamName" 		value="${myActivities.teamName }"/>
		<c:set var="actiTeamEngName" 	value="${myActivities.teamEnglishName }"/>
		<c:set var="actiUserName" 		value="${myActivities.userName }"/>
		<c:set var="actiUserEngName" 	value="${myActivities.userEnglishName }"/>
		<c:set var="actiJobTitle" 		value="${myActivities.jobTitleName }"/>
		<c:set var="actiJobEngTitle" 	value="${myActivities.jobTitleEnglishName }"/>
		<c:set var="actiPicturePath" 	value="${myActivities.picturePath }"/>
		<c:set var="actiProfilePicturePath" 	value="${myActivities.profilePicturePath }"/>
	</c:otherwise>
</c:choose>

<!--forum_topBox Start-->
<div class="forum_topBox">
	<h3 class="none"><ikep4j:message pre='${preUi}' key='myActi'/></h3>
	<div class="forum_topBox_l" style="width:66%;">
		<div class="expert_topPhoto">
			<span><a href="#a" onclick="viewPopUpProfile('${actiUserId}');return false;" title="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${actiUserName} ${actiJobTitle}</c:when><c:otherwise>${actiUserEngName} ${actiJobEngTitle}</c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${actiTeamName}</c:when><c:otherwise>${actiTeamEngName}</c:otherwise></c:choose>"><img src="<c:url value='${actiProfilePicturePath}'/>" alt="profileImage" onerror="this.src='<c:url value='/base/images/common/photo_170x170.gif'/>'" /></a></span>
			<div class="expert_topPhoto_info">
				<div class="expert_topPhoto_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${actiTeamName}</c:when><c:otherwise>${actiTeamEngName}</c:otherwise></c:choose></div>
				<div class="expert_topPhoto_name"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${actiUserName} ${actiJobTitle}</c:when><c:otherwise>${actiUserEngName} ${actiJobEngTitle}</c:otherwise></c:choose></div>
			</div>
		</div>
		<div class="forum_ranking_1">
			<div class="fr_1"><ikep4j:message pre='${preUi}' key='forumRank'/> :<span class="fr_num">${!empty(myActivities.discussionRank) ? myActivities.discussionRank:0 }</span><span class="fr_num_s"><ikep4j:message pre='${preUi}' key='rank'/></span></div>
			<div class="fr_2"><ikep4j:message pre='${preUi}' key='myPoint'/> :<span class="colorPoint">${!empty(myActivities.discussionScore) ? myActivities.discussionScore:0 }<ikep4j:message pre='${preUi}' key='pt'/></span></div>
		
			<div class="subTitle_2 mb5">
				<img src="<c:url value="/base/images/icon/ic_graph.gif"/>" class="valign_middle" alt="<ikep4j:message pre='${preUi}' key='title'/>" /> <ikep4j:message pre='${preUi}' key='title'/>
			</div>
			<table summary="My Forum Activities">
				<caption></caption>
				<tbody>
					<tr>
						<th scope="row"><ikep4j:message pre='${preUi}' key='dis'/> :</th>
						<td>${!empty(myActivities.discussionCount) ? myActivities.discussionCount:0 }</td>
						<th scope="row"><ikep4j:message pre='${preUi}' key='favoryItem'/> :</th>
						<td>${!empty(myActivities.favoriteCount) ? myActivities.favoriteCount:0 }</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre='${preUi}' key='disCount'/> :</th>
						<td>${!empty(myActivities.itemCount) ? myActivities.itemCount:0 }</td>
						<th scope="row"><ikep4j:message pre='${preUi}' key='agreeCount'/> :</th>
						<td>${!empty(myActivities.agreementCount) ? myActivities.agreementCount:0 }</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre='${preUi}' key='lineCount'/> :</th>
						<td>${!empty(myActivities.linereplyCount) ? myActivities.linereplyCount:0}</td>
						<th scope="row"><ikep4j:message pre='${preUi}' key='oppCount'/> :</th>
						<td>${!empty(myActivities.oppositionCount) ? myActivities.oppositionCount:0 }</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre='${preUi}' key='bestCount'/> :</th>
						<td>${!empty(myActivities.bestItemCount) ? myActivities.bestItemCount:0 }</td>
						<th scope="row"><ikep4j:message pre='${preUi}' key='recommCount'/> :</th>
						<td>${!empty(myActivities.recommendCount) ? myActivities.recommendCount:0 }</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre='${preUi}' key='bestLineCount'/> :</th>
						<td>${!empty(myActivities.bestLinereplyCount) ? myActivities.bestLinereplyCount:0 }</td>
						<th scope="row"></th>
						<td></td>										
					</tr>																																				
				</tbody>
			</table>
		</div>
	</div>	
	<div class="forum_topBox_r" style="width:34%;">					
		<h3><img src="<c:url value="/base/images/icon/ic_ranking.gif"/>" alt="" /> <ikep4j:message pre='${preUi}' key='totalRanking'/></h3>
		<ul class="ranking">
			<c:set var="rankClass" value="1"/>
			<c:forEach var="activity" items="${activityList}" varStatus="loop">
			
				<c:if test="${loop.count > 1}">
					<c:set var="rankClass" value="2"/>
				</c:if>
				
				<li>
					<div class="ranking_photo">
						<div class="ic_ranking_${rankClass}">${loop.count }</div>
						<div class="photoimg"><a href="#a" onclick="viewPopUpProfile('${activity.userId}');return false;" title="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${activity.userName} ${activity.jobTitleName}</c:when><c:otherwise>${activity.userEnglishName} ${activity.jobTitleEnglishName}</c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${activity.teamName}</c:when><c:otherwise>${activity.teamEnglishName}</c:otherwise></c:choose>"><img src="<c:url value='${activity.profilePicturePath}'/>" alt="" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></div>
					</div>
					<div class="ranking_name"><a href="#a" onclick="viewPopUpProfile('${activity.userId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${activity.userName} ${activity.jobTitleName}</c:when><c:otherwise>${activity.userEnglishName} ${activity.jobTitleEnglishName}</c:otherwise></c:choose></a> <span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${activity.teamName}</c:when><c:otherwise>${activity.teamEnglishName}</c:otherwise></c:choose></span></div>
					<div class="ranking_num">${activity.discussionScore}pt</div>
				</li>
			
			</c:forEach>
		</ul>
	</div>	
	<div class="clear"></div>								
</div>
<!--//forum_topBox End-->

<!--tab Start-->		
<div id="divTab1" class="iKEP_tab">
	<ul>
		<li><a href="#tabs-1"><ikep4j:message pre='${preUi}' key='myRegistItem'/></a></li>
		<li><a href="#tabs-2"><ikep4j:message pre='${preUi}' key='myRegistLine'/></a></li>
		<li><a href="#tabs-3"><ikep4j:message pre='${preUi}' key='myRegistDis'/></a></li>
		<li><a href="#tabs-4"><ikep4j:message pre='${preUi}' key='bestItem'/></a></li>
		<li><a href="#tabs-5"><ikep4j:message pre='${preUi}' key='bestLine'/></a></li>												
	</ul>	
	<div style="display:none;">
		<div id="tabs-1"></div>
		<div id="tabs-2"></div>
		<div id="tabs-3"></div>
		<div id="tabs-4"></div>
		<div id="tabs-5"></div>
	</div>			
</div>		
<!--//tab End-->	

<ul class="forum_list mb15" id="itemFrame">
	<c:choose>
		<c:when test="${param.tabType == isLine || param.tabType == isLineBest}">
			<c:import url="/WEB-INF/view/collpack/forum/linereplyDisListMore.jsp" charEncoding="UTF-8" />
		</c:when>
		<c:when test="${param.tabType == isDis}">
			<c:import url="/WEB-INF/view/collpack/forum/discussionListMore.jsp" charEncoding="UTF-8" />
		</c:when>
		<c:otherwise>
			<c:import url="/WEB-INF/view/collpack/forum/itemDisListMore.jsp" charEncoding="UTF-8" />
		</c:otherwise>
	</c:choose>
</ul>

<!--blockButton_3 Start-->
<div class="blockButton_3" <c:if test="${totalCount <= 10}">style="display:none"</c:if> id="moreFrame"> 
	<a class="button_3" href="#a" onclick="moreList();return false;" title="<ikep4j:message pre='${preUi}' key='more10'/>"><span id="moreText"><ikep4j:message pre='${preUi}' key='more10'/> <img src="<c:url value="/base/images/icon/ic_more_ar.gif"/>" alt="<ikep4j:message pre='${preUi}' key='more10'/>" /></span></a>				
</div>
<!--//blockButton_3 End-->	

</div>