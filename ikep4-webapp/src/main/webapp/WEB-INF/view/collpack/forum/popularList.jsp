<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.collpack.forum.constants.ForumConstants" %>

<c:set var="preUi" 			value="ui.collpack.forum.bestList" />

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:set var="isItem" value="<%=ForumConstants.TAP_TYPE_ITEM%>"/>
<c:set var="isDis" value="<%=ForumConstants.TAP_TYPE_DISCUSSION%>"/>

<script type="text/javascript">
//<![CDATA[
	$jq(document).ready(function() {
		
		var selectVal = "";
		var tabType = "${param.tabType}";
		if(tabType == "${isItem}"){
			selectVal = 1;
		} else {
			selectVal = 0;
		}
		
		$jq("#divTab1").tabs({     
			selected : selectVal,    
			cache : true,     
			select : function(event, ui) {   
				if(ui.index == 0){
					listItem('7','${isDis}');
				} else if (ui.index == 1){
					listItem('7','${isItem}');
				} 
			},     
			load : function(event, ui) {        
				//iKEP.debug(ui);     
				} 
		});
	});

	
function listItem(limitDate, type){
		
		if(!type){
			type = "${param.tabType}";
		}
		
		location.href="popularList.do?tabType="+type+"&limitDate="+limitDate;
	}
	
	
	function listItemMore(totalCount){
		
		var type = "${param.tabType}";
		if(!type){type = "discussion";}
		

		var limitDate = "${param.limitDate}";
		if(!limitDate){limitDate = "7";}
		
		var urlPix = ""
		if(type == "item"){
			urlPix = "itemDis";
		} else {
			urlPix = "discussion";
		}
		
		listMore(urlPix+"ListMore.do", totalCount, {isPopular:1,tabType:type,limitDate:limitDate });
		
	}	
	
	//]]>	
</script>


<h1 class="none">Contents Area</h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preUi}' key='popular.title'/></h2>
</div>
<!--//pageTitle End-->
 
<!--pageTitle Start-->
	<c:import url="/WEB-INF/view/collpack/forum/forumSearch.jsp" charEncoding="UTF-8" />
<!--//pageTitle End-->

<div id="tagResult">
<!--forum_topBox Start-->
<div class="forum_topBox">
	<h3 class="none"><ikep4j:message pre='${preUi}' key='popularTop3'/></h3>
	<div class="forum_topBox_l" style="width:50%;">
		<h3><img src="<c:url value="/base/images/icon/ic_docu_2.gif"/>" alt="<ikep4j:message pre='${preUi}' key='popularTop3'/>" /> <ikep4j:message pre='${preUi}' key='popularTop3'/></h3>
		<ul class="ranking">
		
			<c:set var="rankClass" value="1"/>
			<c:forEach var="discussion" items="${topLeftList}" varStatus="loop">
				
				<c:if test="${loop.count > 1}">
					<c:set var="rankClass" value="2"/>
				</c:if>
				
				<li>
					<div class="ranking_photo">
						<div class="ic_ranking_${rankClass }">${loop.count }</div>
						<div class="photoimg"><a href="getView.do?discussionId=${discussion.discussionId }"><img src="<c:url value="/support/fileupload/downloadFile.do"/>?fileId=${discussion.imageId }&amp;thumbnailYn=Y" width="150" height="150" alt="image"/></a></div>
					</div>
					<c:if test="${!empty(discussion.categoryName) }"><span class="cate_block_1"><span class="cate_tit_2">${discussion.categoryName}</span></span></c:if>
					<p><a href="getView.do?discussionId=${discussion.discussionId }">${discussion.title}</a></p>
					<div>
						<span class="forum_name"><a href="#a" onclick="viewPopUpProfile('${discussion.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${discussion.registerName} ${discussion.jobTitleName}</c:when><c:otherwise>${discussion.userEnglishName} ${discussion.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
						<span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${discussion.teamName}</c:when><c:otherwise>${discussion.teamEnglishName}</c:otherwise></c:choose></span>										
					</div>
				</li>
			
			</c:forEach>
			<c:if test="${empty(topLeftList)}">
				<li>
				<ikep4j:message key='message.collpack.forum.nodata.dis.popular'/>
				</li>
			</c:if>
		</ul>								
	</div>	
	<div class="forum_topBox_r" style="width:50%;">					
		<h3><img src="<c:url value="/base/images/icon/ic_docu_3.gif"/>" alt="" /> <ikep4j:message pre='${preUi}' key='popularItemTop3'/></h3>
		<ul class="ranking">
		
			<c:set var="rankClass" value="1"/>
			<c:forEach var="item" items="${topRightList}" varStatus="loop">
				
				<c:if test="${loop.count > 1}">
					<c:set var="rankClass" value="2"/>
				</c:if>
				
			<li>
				<div class="ranking_photo">
					<div class="ic_ranking_${rankClass }">${loop.count}</div>
					<div class="photoimg"><a href="#a" onclick="viewPopUpProfile('${item.registerId}');return false;" title="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.registerName} ${item.jobTitleName}</c:when><c:otherwise>${item.userEnglishName} ${item.jobTitleEnglishName}</c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.teamName}</c:when><c:otherwise>${item.teamEnglishName}</c:otherwise></c:choose>"><img src="<c:url value='${item.profilePicturePath}'/>" alt="profile"  onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></div>
				</div>
				<p><a href="getView.do?itemId=${item.itemId }">${item.title}</a></p>
				<div>
					<span class="forum_name"><a href="#a" onclick="viewPopUpProfile('${item.registerId}');return false;" title="profile"> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.registerName} ${item.jobTitleName}</c:when><c:otherwise>${item.userEnglishName} ${item.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
					<span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.teamName}</c:when><c:otherwise>${item.teamEnglishName}</c:otherwise></c:choose></span>										
				</div>
			</li>
			
			</c:forEach>
			<c:if test="${empty(topRightList)}">
				<li>
				<ikep4j:message key='message.collpack.forum.nodata.item.popular'/>
				</li>
			</c:if>
		</ul>
	</div>	
	<div class="clear"></div>								
</div>
<!--//forum_topBox End-->

<!--tab Start-->		
<div id="divTab1" class="iKEP_tab">
	<ul>
		<li><a href="#tabs-1"><ikep4j:message pre='${preUi}' key='popularDis'/></a></li>
		<li><a href="#tabs-2"><ikep4j:message pre='${preUi}' key='popularTitle'/></a></li>												
	</ul>
	<div style="display:none;">
		<div id="tabs-1"></div>
		<div id="tabs-2"></div>
	</div>				
</div>	
<div class="textRight">
	<a class="button_s <c:if test="${empty(param.limitDate) || param.limitDate == '7'}">selected</c:if>" href="#a" onclick="listItem(7)" title="<ikep4j:message pre='${preUi}' key='lastWeek'/>"><span><ikep4j:message pre='${preUi}' key='lastWeek'/></span></a>
	<a class="button_s <c:if test="${param.limitDate == '30'}">selected</c:if>" onclick="listItem(30)"  href="#a" title="<ikep4j:message pre='${preUi}' key='lastMonth'/>"><span><ikep4j:message pre='${preUi}' key='lastMonth'/></span></a>
	<a class="button_s <c:if test="${param.limitDate == '90'}">selected</c:if>" onclick="listItem(90)" href="#a" title="<ikep4j:message pre='${preUi}' key='last3Month'/>"><span><ikep4j:message pre='${preUi}' key='last3Month'/></span></a>
	<a class="button_s <c:if test="${param.limitDate == '180'}">selected</c:if>" onclick="listItem(180)" href="#a" title="<ikep4j:message pre='${preUi}' key='last6Month'/>"><span><ikep4j:message pre='${preUi}' key='last6Month'/></span></a>				
</div>
<!--//tab End-->	

<ul class="forum_list mb15" id="itemFrame" >
	
	<c:choose>
		<c:when test="${param.tabType == isItem}">
			<c:import url="/WEB-INF/view/collpack/forum/itemDisListMore.jsp" charEncoding="UTF-8" />
		</c:when>
		<c:otherwise>
			<c:import url="/WEB-INF/view/collpack/forum/discussionListMore.jsp" charEncoding="UTF-8" />
		</c:otherwise>
	</c:choose>
																												
</ul>

<!--blockButton_3 Start-->
<c:if test="${totalCount > 10}">
<div class="blockButton_3"> 
	<a class="button_3" href="#a" onclick="listItemMore('${totalCount}');" title="<ikep4j:message pre='${preUi}' key='more10'/>"><span id="moreText"><ikep4j:message pre='${preUi}' key='more10'/> <img src="<c:url value="/base/images/icon/ic_more_ar.gif"/>" alt="<ikep4j:message pre='${preUi}' key='more10'/>" /></span></a>				
</div>
</c:if>
<!--//blockButton_3 End-->	
										
</div>

