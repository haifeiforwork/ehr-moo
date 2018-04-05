<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="preList"    value="ui.lightpack.board.portlet.markBoard" />  

<script type="text/javascript">
//<![CDATA[
(function($){	  

	$(document).ready(function() {
		
		$(".markitem").click( function() {  			
			var url = $(this).attr("link_url")+ "&popupYn=true";
			
			var options = {
				windowTitle : $(this).html(),
				documentTitle : "<ikep4j:message pre='${preList}' key='normalView.documentTitle' />" ,
				width:720, 
				height:500, 
				modal:true 
			};
			
			iKEP.portletPopupOpen(url, options);  
			
			return false;
		});
		

	});  

})(jQuery);	
//]]>
</script>
<div id="${portletConfigId}">
	<c:forEach var="board" items="${boardList}" varStatus="status">
		<c:if test="${board.viewCount ne null }">
				<c:set var="setcount" value="${board.viewCount}"/>
		</c:if>
	</c:forEach>
	<c:choose>
    <c:when test="${empty boardList}">
        <ikep4j:message pre='${preList}' key='list.noData' />                                            
    </c:when>
    <c:otherwise>
	<c:forEach var="board" items="${boardList}">
		<img src="<c:url value='/base/images/common/ic_title_01.gif'/>" title="title_bar" / style="padding-right:4px; padding-bottom:2px; vertical-align:middle;"><a id="${board.boardId}" href="<c:url value='/lightpack/board/boardCommon/boardView.do?boardId='/>${board.boardId}&portletYn=true"><strong>${board.boardName}</strong></a>
		<jsp:include page="/lightpack/board/portlet/markBoard/listBoardItem.do?boardId=${board.boardId}&amp;popupYn=true">
		<jsp:param name="setcount" value="${setcount}"/>
		</jsp:include>
			<input type="hidden" id="${board.boardId}_hidden" value=""/> 
		<br>
	</c:forEach> 
  </c:otherwise>  
</c:choose>
 </div> 