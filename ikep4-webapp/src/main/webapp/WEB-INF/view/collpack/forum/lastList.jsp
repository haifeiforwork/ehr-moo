<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<c:set var="preUi" 			value="ui.collpack.forum.bestList" />

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
//<![CDATA[
           
function listItemMore(totalCount){
	
	var categoryId = "${param.categoryId}";
	
	listMore("itemLastListMore.do", totalCount, {categoryId:categoryId });
	
}	

//]]>
</script>


<h1 class="none">Contents Area</h1>


<!--pageTitle Start-->
<div id="pageTitle">
	<c:if test="${!empty(categoryName)}">
		<h2>[${categoryName}] <ikep4j:message pre='${preUi}' key='category'/> <ikep4j:message pre='${preUi}' key='forum'/></h2>
	</c:if>
	<c:if test="${empty(categoryName)}">
		<h2><ikep4j:message pre='${preUi}' key='lastTitle'/></h2>
	</c:if>
</div>
<!--//pageTitle End-->

 
<!--pageTitle Start-->
	<c:import url="/WEB-INF/view/collpack/forum/forumSearch.jsp" charEncoding="UTF-8" />
<!--//pageTitle End-->

<div id="tagResult">
<!--forum_topBox Start-->
<div class="forum_topBox">
	<h3 class="none"><ikep4j:message pre='${preUi}' key='popularItemTop3'/></h3>
	<div class="forum_topBox_l" style="width:50%;">
		<h3><img src="<c:url value="/base/images/icon/ic_docu_2.gif"/>" alt="" /><c:if test="${!empty(categoryName)}"><span class="cate_tit_2">${categoryName}</span>  <ikep4j:message pre='${preUi}' key='category'/></c:if> <ikep4j:message pre='${preUi}' key='popularItemTop3'/></h3>
		<ul class="ranking">
		
			<c:set var="rankClass" value="1"/>
			<c:forEach var="item" items="${topLeftList}" varStatus="loop">
				
				<c:if test="${loop.count > 1}">
					<c:set var="rankClass" value="2"/>
				</c:if>
				
			<li>
				<div class="ranking_photo">
					<div class="ic_ranking_1">${loop.count }</div>
					<div class="photoimg"><a href="#a" onclick="viewPopUpProfile('${item.registerId}');return false;" title="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.registerName} ${item.jobTitleName}</c:when><c:otherwise>${item.userEnglishName} ${item.jobTitleEnglishName}</c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.teamName}</c:when><c:otherwise>${item.teamEnglishName}</c:otherwise></c:choose>"><img src="<c:url value='${item.profilePicturePath}'/>" alt="profile" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></div>
				</div>
				<p ><a href="getView.do?itemId=${item.itemId }<c:if test="${!empty(param.docIframe) }">&amp;docPopup=${param.docIframe}&amp;docIframe=${param.docIframe}</c:if>">${item.title}</a></p>
				<div>
					<span class="forum_name"><a href="#a" onclick="viewPopUpProfile('${item.registerId}');return false;" title="profile"> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.registerName} ${item.jobTitleName}</c:when><c:otherwise>${item.userEnglishName} ${item.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
					<span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.teamName}</c:when><c:otherwise>${item.teamEnglishName}</c:otherwise></c:choose></span>										
				</div>
			</li>
			
			</c:forEach>
			<c:if test="${empty(topLeftList)}">
				<li>
				<ikep4j:message key='message.collpack.forum.noData.item'/>
				</li>
			</c:if>
		</ul>								
	</div>	
	<div class="forum_topBox_r" style="width:50%;">					
		<h3><img src="<c:url value="/base/images/icon/ic_docu_3.gif"/>" alt="" /><c:if test="${!empty(categoryName)}"><span class="cate_tit_2">${categoryName}</span> <ikep4j:message pre='${preUi}' key='category'/></c:if> <ikep4j:message pre='${preUi}' key='bestItemTop3'/></h3>
		<ul class="ranking">
		
			<c:set var="rankClass" value="1"/>
			<c:forEach var="item" items="${topRightList}" varStatus="loop">
				
				<c:if test="${loop.count > 1}">
					<c:set var="rankClass" value="2"/>
				</c:if>
				
			<li>
				<div class="ranking_photo">
					<div class="ic_ranking_${rankClass }">${loop.count}</div>
					<div class="photoimg"><a href="#a" onclick="viewPopUpProfile('${item.registerId}');return false;" title="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.registerName} ${item.jobTitleName}</c:when><c:otherwise>${item.userEnglishName} ${item.jobTitleEnglishName}</c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.teamName}</c:when><c:otherwise>${item.teamEnglishName}</c:otherwise></c:choose>"><img src="<c:url value='${item.profilePicturePath}'/>" alt="profile" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></div>
				</div>
				<p><a href="getView.do?itemId=${item.itemId }<c:if test="${!empty(param.docIframe) }">&amp;docPopup=${param.docIframe}&amp;docIframe=${param.docIframe}</c:if>">${item.title}</a></p>
				<div>
					<span class="forum_name"><a href="#a" onclick="viewPopUpProfile('${item.registerId}');return false;" title="profile"> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.registerName} ${item.jobTitleName}</c:when><c:otherwise>${item.userEnglishName} ${item.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
					<span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.teamName}</c:when><c:otherwise>${item.teamEnglishName}</c:otherwise></c:choose></span>										
				</div>
			</li>
			
			</c:forEach>
			<c:if test="${empty(topRightList)}">
				<li>
				<ikep4j:message key='message.collpack.forum.noData.item'/>
				</li>
			</c:if>
		</ul>
	</div>	
	<div class="clear"></div>								
</div>
<!--//forum_topBox End-->

<ul class="forum_list mb15" id="itemFrame" >
	
	<c:import url="/WEB-INF/view/collpack/forum/lastListMore.jsp" charEncoding="UTF-8" />
																												
</ul>

<!--blockButton_3 Start-->

<div class="blockButton_3" <c:if test="${totalCount <= 10}">style="display:none"</c:if>> 
	<a class="button_3" href="#a" onclick="listItemMore('${totalCount}');" title="<ikep4j:message pre='${preUi}' key='more10'/>"><span id="moreText"><ikep4j:message pre='${preUi}' key='more10'/> <img src="<c:url value="/base/images/icon/ic_more_ar.gif"/>" alt="<ikep4j:message pre='${preUi}' key='more10'/>" /></span></a>				
</div>

<!--//blockButton_3 End-->	
										
</div>
