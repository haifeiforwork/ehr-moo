<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<c:set var="preUi" 	value="ui.support.tagging" />

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
//<![CDATA[
function viewPopUpProfile(targetUserId){

	iKEP.goProfilePopupMain(targetUserId);
}
//]]>
</script>
<div class="bgWhite">

<!--tableTop Start-->
<div class="tableTop">

	<div class="tableTop_bgR"></div>
	<h2><ikep4j:message pre='${preUi}' key='searchTag'/></h2>

	<div class="listInfo">
		<select title="pagePerSelect" id="pagePerSelect" onchange="iKEP.tagging.goPagePer()">
			<option>10</option>
			<option>20</option>
			<option>30</option>
			<option>50</option>
			<option>100</option>
		</select>
		<div class="totalNum"><ikep4j:message pre='${preUi}' key='listWhole'/><span> ${count}</span></div>
	</div>																		
	<div class="clear"></div>
</div>
<!--//tableTop End-->	

<!--blockListTable Start-->
<div class="blockListTable summaryView">
	<table summary="list">
		<caption></caption>						
		<tbody>
		
		<c:forEach var="tag" items="${tagList}" varStatus="loop">
			<tr>
				<td>
					<div class="summaryViewTitle">
						<c:choose>
							<c:when test="${tag.tagItemType == 'WS' && empty(tag.tagItemSubType) }">
								<a href="<c:url value="${tag.tagItemUrl}"/>"  title="tag">
							</c:when>
							<c:when test="${(tag.tagItemType == 'PE' || tag.tagItemType == 'PI') && empty(tag.tagItemUrl) }">
								<a href="<c:url value="${tag.tagItemUrl}"/>"  title="tag" onclick="viewPopUpProfile('${tag.tagItemId}');return false;">
							</c:when>
							<c:otherwise>
								<a href="#a"  onclick="iKEP.tagging.goDetail('<c:url value="${tag.tagItemUrl}"/>');" title="tag">
							</c:otherwise>
						</c:choose>
							${tag.tagItemName}
						</a>
					</div>
					<div class="summaryViewInfo">
						<span class="summaryViewInfo_name"><a href="#a" onclick="viewPopUpProfile('${tag.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${tag.registerName} ${tag.jobTitleName}</c:when><c:otherwise>${tag.userEnglishName} ${tag.jobTitleEnglishName}</c:otherwise></c:choose></a> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${tag.teamName}</c:when><c:otherwise>${tag.teamEnglishName}</c:otherwise></c:choose></span>
						<img src="<c:url value="/base/images/theme/theme01/basic/bar_pageNum.gif"/>" alt="" />
						<span><ikep4j:timezone date="${tag.registDate}" pattern="message.support.tagging.timezone.dateformat.type2" keyString="true"/></span>
					</div>
					<div class="summaryViewCon">${tag.tagItemContents}</div>
					<div class="summaryViewTag">
						<!--tag list-->
						<div class="tableTag" >    
						     <div>  <!--코딩파일엔 div가 없는데 꼭 넣어주어야 함.-->
						        <span class="ic_tag"><span>Tag</span></span> <!--tagList--> 
						           <c:forEach var="tagObject" items="${tag.tagList}" varStatus="tagCount"><c:if test="${tagCount.count != 1}">, </c:if>
							           <a href="#a" onclick="iKEP.tagging.tagSearchByName('${tagObject.tagName}', '${tagObject.tagItemType}', '${tagObject.tagItemSubType}');return false;">
							          	 ${tagObject.tagName}
							           </a>
						           </c:forEach>
						        <span class="rebtn">
						       </span>
						     </div>
						</div>
						<!--//tag list-->
					</div>
				</td>
			</tr>
		</c:forEach>																																															
		</tbody>
	</table>
	
	<!--Page Numbur Start--> 
	<ikep4j:pagination searchFormId="tagIndexForm" pageIndexInput="pageIndex" searchCondition="${searchCondition}" ajaxEventFunctionName="iKEP.tagging.goPageIndex"/>
	<form id="tagIndexForm">
		<input name="pageIndex" type="hidden" id="tag_pageIndex" value="1" title="pageIndex" />
	</form>
	<!--//Page Numbur End-->
			
</div>
<!--//blockListTable End-->
</div>