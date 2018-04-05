<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 

<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />					
				

											
						
									<c:forEach var="file" items="${searchResult.entity}">
									
										<div class="MyImage_topPhoto">
											<span><a class="image-gallery" href="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${file.fileId}" title="${file.fileName}"><img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${file.fileId}" alt="image" width="170px" height="170px"/></a></span>
											<div class="MyImage_topPhoto_info" >
												<div class="MyImage_topPhoto_name ellipsis">${file.fileName}</div>
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