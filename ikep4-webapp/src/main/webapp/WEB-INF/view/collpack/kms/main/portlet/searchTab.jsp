<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>


<script type="text/javascript">
	
	
	function selUserKeyword(a_query){
		
		var tabFrame = document.getElementById("ifrmNewTab");
		tabFrame.src = "<c:url value='/search/kmsMySearchResult.jsp?method=MAINTAB&query=" + encodeURI(a_query) + "&isAdmin=${isSystemAdmin}'/>";
	}	
			 
</script>


<div id="TabCotents">
	<div valign="top" style="padding:0px; margin:0px; height:80px;">
		<iframe frameborder="0" width="100%" height="80px" margin="0" scrolling="no" src="<c:url value='/collpack/kms/main/portlet/searchTabTop.do'/>"  name="ifrmTab" id="ifrmTab"></iframe>
	</div>
	<div valign="top" style="padding:0px; margin:0px; height:120px;">
		<c:choose>
		<c:when test="${!empty firstKeyword}">
			<iframe frameborder="0" width="100%" height="120px" margin="0" scrolling="no" src="<c:url value='/search/kmsMySearchResult.jsp?query=${firstKeyword.KEYWORD}&isAdmin=${isSystemAdmin}'/>" name="ifrmNewTab" id="ifrmNewTab" ></iframe>		
		</c:when>
		<c:otherwise>
			<iframe frameborder="0" width="100%" height="120px" margin="0" scrolling="no" src="<c:url value='/search/kmsSearchTabAdd.jsp'/>" name="ifrmNewTab" id="ifrmNewTab" ></iframe>
		</c:otherwise>
		</c:choose>
	</div>
</div>
