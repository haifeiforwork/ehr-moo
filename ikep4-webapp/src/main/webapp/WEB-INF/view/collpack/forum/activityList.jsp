<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preUi" 			value="ui.collpack.forum.activity" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<h1 class="none">Contents Area</h1>
 
<!--subTitle_2 Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preUi}' key='titleBar'/></h2>
</div>
<!--//subTitle_2 End-->

<!--blockListTable_2 Start-->
<div class="blockListTable_2 summaryView">
	<table summary="table">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="50%">Forum <span class="colorPoint"><ikep4j:message pre='${preUi}' key='top10'/></span></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
					<ul class="ranking">
					
					<c:set var="rankClass" value="1"/>
					<c:forEach var="activity" items="${activityList}" varStatus="loop">
					
						<c:if test="${loop.count > 1}">
							<c:set var="rankClass" value="2"/>
						</c:if>
						<li>
							<div class="ranking_photo">
								<div class="ic_ranking_${rankClass}">${loop.count }</div>
								<div class="photoimg"><a href="#a" onclick="viewPopUpProfile('${activity.userId}');return false;" title="profile"><img title="profile" src="<c:url value="${activity.profilePicturePath}"/>" width="35" height="35" alt="profile" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'"/></a></div>
							</div>
							<div class="ranking_name"><a href="#a" onclick="viewPopUpProfile('${activity.userId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${activity.userName} ${activity.jobTitleName}</c:when><c:otherwise>${activity.userEnglishName} ${activity.jobTitleEnglishName}</c:otherwise></c:choose></a> 
							<span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${activity.teamName}</c:when><c:otherwise>${activity.teamEnglishName}</c:otherwise></c:choose></span></div>
							<div class="ranking_num"><span class="normal"><ikep4j:message pre='${preUi}' key='forumActScore'/> :</span> ${activity.discussionScore}<ikep4j:message pre='${preUi}' key='pt'/></div>
							<div class="clear"></div>
						</li>
						
					</c:forEach>
	
					</ul>			
				</td>
			</tr>
		</tbody>
	</table>
</div>	
<!--//blockListTable_2 End-->
