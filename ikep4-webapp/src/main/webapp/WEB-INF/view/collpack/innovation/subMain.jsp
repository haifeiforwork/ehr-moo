<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:choose>
	<c:when test="${param.type=='plan' }">
	<img src="<c:url value="/base/images/futureplanet/planner.gif"/>" alt="" />
	</c:when>
	<c:otherwise>
		<iframe id="contentIframe" name="contentIframe" width="100%" scrolling="no" frameborder="0" title="innovation iframe"></iframe>
	</c:otherwise>
</c:choose>

<script type="text/javascript">

//<![CDATA[
$jq(document).ready(function() {

	var itemId = "${param.itemId}";
	var type = "${param.type}";

	var urlLink = "";

	if(type == "discussion") {
		var discussionId= "${param.discussionId}";
		if(discussionId.length > 0){
			urlLink = "<c:url value='/collpack/forum/getView.do?docIframe=true&docPopup=true&discussionId="+discussionId+"'/>";
		} else {
			urlLink = "<c:url value='/collpack/forum/lastList.do?docIframe=true'/>";
		}
		
	} else if(type == "idea"){

		if(itemId.length > 0){
			urlLink = "<c:url value='/collpack/ideation/getView.do?docIframe=true&docPopup=true&itemId="+itemId+"'/>";
		} else {
			urlLink = "<c:url value='/collpack/ideation/lastList.do?docIframe=true'/>";
		}
		
		
	} else {
		if(itemId.length > 0){
			urlLink = "<c:url value='/lightpack/board/boardItem/readBoardItemView.do'/>?boardId=${param.boardId}&amp;itemId=${param.itemId}";
		} else {
			urlLink = "<c:url value='/lightpack/board/boardItem/listBoardItemView.do'/>?boardId=${param.boardId}";
		}
	}

	iKEP.iFrameMenuOnclick(urlLink);

	$jq("#contentIframe").load(function(){iKEP.iFrameResize('#contentIframe');});
	
});

function resizeIframe() { 
	iKEP.iFrameContentResize();  
}
//]]>
</script>