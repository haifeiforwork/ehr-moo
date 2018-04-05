<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>

<c:set var="preUi" 	value="ui.support.tagging" />

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
//<![CDATA[
	$jq(document).ready(function(){
		
		//검색 조건 미리 선택
		var tagItemType = "${param.tagItemType}";
		$jq('#tagSearch option').each(function(){
			if(this.value == tagItemType ){
				this.selected = true;
			}
		}); 
		
		//page per 미리선택
		$jq('#pagePerSelect option').each(function(){
			if(this.value == '${param.pagePer}'){
				this.selected = true;
			}
		});
		
	});

	function tagSearch(){
		
		$jq('#tagSearch').submit();
	}
	
	
	function goPage(){
		
		var pageIndex = $jq('#pageIndex').val();
		
		var param = '<c:forEach var="ram" items="${param}" ><c:if test="${ram.value != '' && ram.key !='pageIndex'}">${ram.key}=${ram.value}&</c:if></c:forEach>';
		
		location.href = 'tagSearch.do?pageIndex='+pageIndex +'&'+ param;
		
	}
	
	function goPagePer(val){
		
		var param = '<c:forEach var="ram" items="${param}" ><c:if test="${ram.value != '' && ram.key !='pagePer'}">${ram.key}=${ram.value}&</c:if></c:forEach>';
		
		var pagePer = $jq('#pagePerSelect').val();
		
		location.href = 'tagSearch.do?pagePer='+ pagePer +'&'+ param;
		
	}
	//]]>	
</script>	
	
<h1 class="none">Contents Area</h1>
 

<!--tableTop Start-->
<div class="tableTop">

	<div class="tableTop_bgR"></div>
	<h2><ikep4j:message pre='${preUi}' key='searchTag'/></h2>
	
	<div class="listInfo">
		<select title="pagePerSelect" id="pagePerSelect" onchange="goPagePer();">
			<option selected="selected">10</option>
			<option>20</option>
			<option>30</option>
			<option>50</option>
			<option>100</option>
		</select>
		<div class="totalNum"><ikep4j:message pre='${preUi}' key='listWhole'/><span> ${count}</span></div>
	</div>																		
	<div class="tableSearch">
			<form id="tagSearch" action="tagSearch.do" method="get" >
				<input name="pageIndex" type="hidden" id="pageIndex" value="1" />
				<input type="hidden" name="listType" value="${listType }"/>
				<select title="tagItemType" name="tagItemType">
					<option value=""><ikep4j:message pre='${preUi}' key='listAllTag'/></option>
					<c:if test="${CommonConstant.PACKAGE_VERSION eq CommonConstant.IKEP_VERSION_FULL}">
					<option value="<%=TagConstants.ITEM_TYPE_PROFILE_PRO %>"><ikep4j:message pre='${preUi}' key='listProfileProTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_PROFILE_ATTENTION %>"><ikep4j:message pre='${preUi}' key='listProfileConTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_WHO %>"><ikep4j:message pre='${preUi}' key='listWhoWhoTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_SOCIAL_BLOG %>"><ikep4j:message pre='${preUi}' key='listSocialBlogTag'/></option>
					<option value="${teamList.teamIdes }"><ikep4j:message pre='${preUi}' key='listTeamCollTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_QNA %>"><ikep4j:message pre='${preUi}' key='listQnaTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_CONPORATE_VOCA %>"><ikep4j:message pre='${preUi}' key='listCoporateVocaTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_WORK_MANUAL %>"><ikep4j:message pre='${preUi}' key='listWorkManualTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_FORUM %>"><ikep4j:message pre='${preUi}' key='listForumtag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_IDEATION %>"><ikep4j:message pre='${preUi}' key='listIdationTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_MICROBLOGGING %>"><ikep4j:message pre='${preUi}' key='listMicrobloggingTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_ONLINE_POLL %>"><ikep4j:message pre='${preUi}' key='listOnlinePollTag'/></option>
					</c:if>
					<option value="<%=TagConstants.ITEM_TYPE_CAFE %>"><ikep4j:message pre='${preUi}' key='listCafeTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_BBS %>"><ikep4j:message pre='${preUi}' key='listBbsTag'/></option>
				</select>											
				<input type="text" class="inputbox" title="inputbox" name="tagName" value="${param.tagName }" size="20" />
				<a href="#a" onclick="tagSearch();return false;" title="<ikep4j:message pre='${preUi}' key='listSearch'/>"><img src="<c:url value="/base/images/theme/theme01/basic/ic_search.gif"/>" alt="<ikep4j:message pre='${preUi}' key='listSearch'/>" /></a>
			</form>
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
						<a href="#a" onclick="iKEP.tagging.goDetail('<c:url value="${fn:replace(tag.tagItemUrl,'&','&amp;')}"/>');">${tag.tagItemName}</a>
					</div>
					<div class="summaryViewInfo">
						<span class="summaryViewInfo_name"><a href="#a" onclick="viewPopUpProfile('${tag.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${tag.registerName} ${tag.jobTitleName}</c:when><c:otherwise>${tag.userEnglishName} ${tag.jobTitleEnglishName}</c:otherwise></c:choose></a> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${tag.teamName}</c:when><c:otherwise>${tag.teamEnglishName}</c:otherwise></c:choose></span>
						<img src="<c:url value="/base/images/theme/theme01/basic/bar_pageNum.gif"/>" alt="" />
						<span><ikep4j:timezone date="${tag.registDate}" pattern="message.support.tagging.timezone.dateformat.type2" keyString="true"/></span>
					</div>
					<div class="summaryViewCon">${fn:escapeXml(tag.tagItemContents)}</div>
					<div class="summaryViewTag">
						<!--tag list-->
						<div class="tableTag" >    
						     <div>  <!--코딩파일엔 div가 없는데 꼭 넣어주어야 함.-->
						        <span class="ic_tag"><span>Tag</span></span> <!--tagList--> 
						           <c:forEach var="tagObject" items="${tag.tagList}" varStatus="tagCount"><c:if test="${tagCount.count != 1}">, </c:if>
						           	   <c:if test="${tag.tagName != tagObject.tagName}">
							           <a href="tagSearch.do?tagId=${tagObject.tagId}&tagItemType=${tagObject.tagItemType}" >
							           </c:if>
							          	 ${tagObject.tagName}
							           <c:if test="${tag.tagName != tagObject.tagName}">
							           </a>
							           </c:if>
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
	<ikep4j:pagination searchFormId="tagSearch" pageIndexInput="pageIndex" searchCondition="${searchCondition}" ajaxEventFunctionName="goPage"/>
	<!--//Page Numbur End-->
			
</div>
<!--//blockListTable End-->
