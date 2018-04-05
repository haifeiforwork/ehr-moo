<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.favorite.header" /> 
<c:set var="preList"    value="ui.support.favorite.list" />
<c:set var="preDetail"  value="ui.support.favorite.detail" />
<c:set var="preButton"  value="ui.support.favorite.button" /> 
<c:set var="preMessage" value="ui.support.favorite.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />					

<c:forEach var="favorite" items="${searchResult.entity}">
	<div class="MyImage_topPhoto">
		<span><a class="image-gallery" href="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${favorite.fileId}" title="${favorite.fileRealName}"><img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${favorite.fileId}" alt="image" width="170px" height="170px"/></a></span>
		<div class="MyImage_topPhoto_info" onclick="showDatail('${favorite.targetUrl}','${favorite.targetId}','${favorite.subId}','${ikep4j:replaceQuot(favorite.title)}')" style="cursor:pointer">
			<div class="MyImage_topPhoto_team ellipsis">
				${favorite.title}
			</div>
			<div class="MyImage_topPhoto_name ellipsis">${favorite.fileRealName}</div>
			<div class="MyImage_category ellipsis">${favorite.module}</div>
		</div>
	</div>
</c:forEach>				      

<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	// onload시 수행할 코드
	$jq(document).ready(function() {
		
		iKEP.showGallery($("a.image-gallery"));  
		
		$jq("#recordCount").val("${searchCondition.recordCount}");
		$jq("#currentCount").val("${searchCondition.currentCount}");
		
		setMoreDiv();
		
	});
	
	
})(jQuery);  

//-->
</script>					