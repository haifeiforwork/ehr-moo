<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.collpack.forum.constants.ForumConstants" %>

<c:set var="preUi" 			value="ui.collpack.ideation.myActivity" />
<c:set var="preUiMenu" 			value="ui.collpack.ideation.ideaListMenu" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
	
	var selectVal = 0;
	
	var selectVal = "";
	
	if(1 == "${param.isUserAdopt}"){
		selectVal = 1;
	} else {
		selectVal = 0;
	}
	
	$jq(document).ready(function() {
		$jq("#divTab1").tabs({     
			selected : selectVal,    
			cache : true,     
			select : function(event, ui) {   
				
					if(ui.index == 0){
						location.href = "myActivity.do?isSuggestion=1";
					} else {
						location.href = "myActivity.do?isUserAdopt=1";
					}
					
			},     
			load : function(event, ui) {        
				//iKEP.debug(ui);     
				} 
		});
	});

	
	function listItemMore(){
		
		var isSuggestion = "${param.isSuggestion}";
		var isUserAdopt = "${param.isUserAdopt}";
		
		listMore("ideaListMore.do", '${totalCount}', {isSuggestion:isSuggestion, isUserAdopt:isUserAdopt, isMy:"1"});
		
	}
</script>


<h1 class="none">Contents Area</h1>
 
<div id="tagResult">
<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3><img class="valign_top" src="<c:url value="/base/images/icon/ic_idea_2.png"/>" alt="top" /> <ikep4j:message pre='${preUi}' key='title'/></h3>
</div>
<!--//subTitle_2 End-->

<c:choose>
	<c:when test="${empty(myActivities) }">
		<c:set var="actiUserId" 		value="${userInfo.userId }"/>
		<c:set var="actiTeamName" 		value="${userInfo.teamName }"/>
		<c:set var="actiTeamEngName" 	value="${userInfo.teamEnglishName }"/>
		<c:set var="actiUserName" 		value="${userInfo.userName }"/>
		<c:set var="actiUserEngName" 	value="${userInfo.userEnglishName }"/>
		<c:set var="actiJobTitle" 		value="${userInfo.jobTitleName }"/>
		<c:set var="actiJobEngTitle" 	value="${userInfo.jobTitleEnglishName }"/>
	</c:when>
	<c:otherwise>
		<c:set var="actiUserId" 		value="${myActivities.userId }"/>
		<c:set var="actiTeamName" 		value="${myActivities.teamName }"/>
		<c:set var="actiTeamEngName" 	value="${myActivities.teamEnglishName }"/>
		<c:set var="actiUserName" 		value="${myActivities.userName }"/>
		<c:set var="actiUserEngName" 	value="${myActivities.userEnglishName }"/>
		<c:set var="actiJobTitle" 		value="${myActivities.jobTitleName }"/>
		<c:set var="actiJobEngTitle" 	value="${myActivities.jobTitleEnglishName }"/>
	</c:otherwise>
</c:choose>




				<!--forum_topBox Start-->
				<div class="forum_topBox">
					<div class="forum_topBox_l" style="width:66%;">
						<div class="expert_topPhoto" style="border:none;">
							<span><img src="<c:url value='/base/images/common/img_coll_170.gif' />" alt="image" /></span>
						</div>
						<div class="forum_ranking_1 corner_RoundBox11">
							<div class="fr_1"><ikep4j:message pre='${preUi}' key='sugRanking'/> :<span class="fr_num colorBlue2">${(empty(myActivities.suggestionRank)|| myActivities.suggestionRank == -1)? 0:myActivities.suggestionRank }</span><span class="fr_num_s"><ikep4j:message pre='${preUi}' key='ranking'/></span></div>
							<div class="fr_1 mb10"><ikep4j:message pre='${preUi}' key='conRanking'/> :<span class="fr_num colorBlue2">${(empty(myActivities.contributionRank)|| myActivities.contributionRank == -1)? 0:myActivities.contributionRank }</span><span class="fr_num_s"><ikep4j:message pre='${preUi}' key='ranking'/></span></div>
							<div class="subTitle_2 mb5 pb5" style="color:#555;">
								<img src="<c:url value='/base/images/icon/ic_graph.gif' />" class="valign_middle" alt="" /> <ikep4j:message pre='${preUi}' key='title'/>
							</div>
							<table summary="<ikep4j:message pre='${preUi}' key='myIdeaActi'/>">
								<caption></caption>
								<tbody>
									<tr>
										<th scope="row"><ikep4j:message pre='${preUi}' key='sugIdea'/> :</th>
										<td  class="colorPoint">${(empty(myActivities.itemCount)|| myActivities.itemCount == -1)? 0:myActivities.itemCount}</td>
										<th rowspan="4" scope="row" width="20" class="bg_none">&nbsp;</th>
										<th scope="row"><ikep4j:message pre='${preUi}' key='recomIdea'/> :</th>
										<td  class="colorPoint">${(empty(myActivities.recommendCount)|| myActivities.recommendCount == -1)? 0:myActivities.recommendCount}</td>
									</tr>
									<tr>
										<th scope="row"><ikep4j:message pre='${preUi}' key='recomedIdea'/> :</th>
										<td  class="colorPoint">${(empty(myActivities.recommendItemCount)|| myActivities.recommendItemCount == -1)? 0:myActivities.recommendItemCount}</td>
										<th scope="row"><ikep4j:message pre='${preUi}' key='foverIdea'/> :</th>
										<td  class="colorPoint">${(empty(favoriteCount)|| favoriteCount == -1)? 0:favoriteCount}</td>
									</tr> 
									<tr>
										<th scope="row"><ikep4j:message pre='${preUi}' key='bestIdes'/> :</th>
										<td  class="colorPoint">${(empty(myActivities.bestItemCount)|| myActivities.bestItemCount == -1)? 0:myActivities.bestItemCount}</td>
										<th scope="row"><ikep4j:message pre='${preUi}' key='linereplyCount'/> :</th>
										<td  class="colorPoint">${(empty(myActivities.linereplyCount)|| myActivities.linereplyCount == -1)? 0:myActivities.linereplyCount}</td>
									</tr>
									<tr>
										<th scope="row"><ikep4j:message pre='${preUiMenu}' key='busiAdoptIdea'/>  :</th>
										<td  class="colorPoint">${(empty(myActivities.businessItemCount)|| myActivities.businessItemCount == -1)? 0:myActivities.businessItemCount}</td>
										<th scope="row"></th>
										<td></td>
									</tr>
								</tbody>
							</table>
							<div class="l_t_corner"></div>
							<div class="r_t_corner"></div>
							<div class="l_b_corner"></div>
							<div class="r_b_corner"></div>	
						</div>
					</div>	
					<div class="forum_topBox_r" style="width:34%;">					
						<h3><img src="<c:url value='/base/images/icon/ic_ranking.gif' />" alt="" /> <ikep4j:message pre='${preUi}' key='totalIdeaRanking'/></h3>
						<ul class="ranking">
							<c:set var="rankClass" value="1"/>
							<c:forEach var="activity" items="${activityList}" varStatus="loop">
									
								<c:if test="${loop.count > 1}">
									<c:set var="rankClass" value="2"/>
								</c:if>
												<li>
													<div class="ranking_photo">
														<div class="ic_ranking_${rankClass}">${loop.count }</div>&nbsp;&nbsp;&nbsp;
													</div>
													<div class="ranking_name"><a href="#a" onclick="viewPopUpProfile('${activity.userId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${activity.userName} ${activity.jobTitleName}</c:when><c:otherwise>${activity.userEnglishName} ${activity.jobTitleEnglishName}</c:otherwise></c:choose></a> <span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${activity.teamName}</c:when><c:otherwise>${activity.teamEnglishName}</c:otherwise></c:choose></span></div>
													<div class="ranking_num"><span class="normal"><ikep4j:message pre='${preUi}' key='ideaSugActiCount'/> :</span> ${activity.suggestionScore}pt</div>
													<div class="clear"></div>
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
		<li><a href="#tabs-1"><ikep4j:message pre='${preUi}' key='mySugIdea'/></a></li>
<!-- 		<li><a href="#tabs-2"><ikep4j:message pre='${preUi}' key='myAdoptIdea'/></a></li>											 -->
	</ul>	
	<div style="display:none;">
		<div id="tabs-1"></div>
<!-- 		<div id="tabs-2"></div> -->
	</div>		
</div>		
<!--//tab End-->


<!--blockListTable Start-->
<div class="blockListTable summaryView Ideation_list">
	<table summary="table">
		<caption></caption>						
		<tbody id="itemFrame">
			<c:import url="/WEB-INF/view/collpack/ideation/ideaListMore.jsp"/>																																																
		</tbody>
	</table>
</div>
<!--//blockListTable End-->


<c:if test="${totalCount > 10}">
<!--blockButton_3 Start-->
<div class="blockButton_3"> 
	<a class="button_3" href="#a" title="<ikep4j:message pre='${preUi}' key='more10'/>" onclick="listItemMore();"><span id="moreText"><ikep4j:message pre='${preUi}' key='more10'/> <img src="<c:url value="/base/images/icon/ic_more_ar.gif"/>" alt="<ikep4j:message pre='${preUi}' key='more10'/>" /></span></a>				
</div>
<!--//blockButton_3 End-->	
</c:if>	


</div>	