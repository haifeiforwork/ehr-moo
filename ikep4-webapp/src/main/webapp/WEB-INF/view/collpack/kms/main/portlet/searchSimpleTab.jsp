<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>


<script type="text/javascript">
	
	
	function selUserKeyword(a_query){
		
		var tabFrame = document.getElementById("ifrmNewSimpleTab");
		tabFrame.src = "<c:url value='/search/kmsMySearchSimpleResult.jsp?method=MAINTAB&query=" + encodeURI(a_query) + "&isAdmin=${isSystemAdmin}'/>";
	}	
			 

</script>


	<div valign="top" style="padding:0px; margin:0px; height:25px;border-bottom:1px solid #d8d8d8; padding-bottom:1px;">
		<iframe frameborder="0" width="100%" height="25px" margin="0" scrolling="no" src="<c:url value='/collpack/kms/main/portlet/searchSimpleTabTop.do'/>"  name="ifrmTab" id="ifrmTab"></iframe>
	</div>
	<div valign="top" style="padding:0px; margin:0px; height:110px;padding-top:10px;">
		<c:choose>
		<c:when test="${!empty firstKeyword}">
			<iframe frameborder="0" width="100%" height="110px" margin="0" scrolling="no" src="<c:url value='/search/kmsMySearchSimpleResult.jsp?query=${firstKeyword.KEYWORD}&isAdmin=${isSystemAdmin}'/>" name="ifrmNewSimpleTab" id="ifrmNewSimpleTab" ></iframe>		
		</c:when>
		<c:otherwise>
			<div style="text-align:center;">설정된 관심지식이 없습니다.</div>
		</c:otherwise>
		</c:choose>
	</div>
