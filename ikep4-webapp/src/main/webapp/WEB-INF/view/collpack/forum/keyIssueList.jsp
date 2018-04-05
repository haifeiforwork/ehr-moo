<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<c:set var="preUi" 			value="ui.collpack.forum.keyIssueList" />

<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">

$jq(document).ready(function(){
	
	var tagId = "${tagId}";
	
	if(tagId){
		iKEP.tagging.tagSearch(tagId, '<%=TagConstants.ITEM_TYPE_FORUM%>');
		
		
		 tagNameChange('${tagList[0].tagName}');
	}
	
});

function tagNameChange(tag){
	$jq('#tagTitle').text("["+tag+"]");
}

function tagClk(tagId, tagName){
	
	iKEP.tagging.tagSearch(tagId, '<%=TagConstants.ITEM_TYPE_FORUM%>');
	
	$jq('#moreBtn').remove();
	
	tagNameChange(tagName);
	
	return false;
}
</script>



<h1 class="none">Contents Area</h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h3><ikep4j:message pre='${preUi}' key='keyIssues'/></h3>
</div>
<!--//pageTitle End-->
	
<!--pageTitle Start-->
	<c:import url="/WEB-INF/view/collpack/forum/forumSearch.jsp" charEncoding="UTF-8" />
<!--//pageTitle End-->
				
<!--lntel_tag_con Start-->
<div class="lntel_tag_con">
	<!--subTitle_2 Start-->
	<div class="subTitle_2 noline">
		<h3><ikep4j:message pre='${preUi}' key='contents'/></h3>
	</div>
	<!--//subTitle_2 End-->	
	<div class="lntel_tag">
		<div class="lntel_tagBox02" style="min-height: 10px;">
			<div class="tag_cloud lntel_tag_cloud textLeft" style="min-height: 10px;">
				<c:forEach var="tag" items="${tagList}" varStatus="loop">
					<span><a href="#a" class="tag${tag.displayStep}" onclick="tagClk('${tag.tagId}', '${tag.tagName}');" >${tag.tagName}</a></span>
				</c:forEach>
				<c:if test="${empty(tagList) }"><span><ikep4j:message pre='${preUi}' key='tagResult.none'/></span></c:if>
			</div>				
		</div>
	</div>	
</div>
<!--//lntel_tag_con End-->
	
<!--subTitle_2 Start-->
<div class="subTitle_2">
	<h3><span id="tagTitle">['<ikep4j:message pre='${preUi}' key='selectTag'/>']</span> <ikep4j:message pre='${preUi}' key='tagResult'/></h3>
</div>
<!--//subTitle_2 End-->

<div class="blockComment" id="tagResult">
	<c:if test="${empty(tagList) }"><span><ikep4j:message pre='${preUi}' key='tagSearch.none'/></span></c:if>							
</div>
										
