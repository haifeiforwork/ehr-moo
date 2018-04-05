<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

											
<span class="ic_tag"><span>Tag</span></span> 
<c:forEach var="info" items="${expertTagList}"  varStatus="loopStatus">
		<a href="#a" onclick="searchCall('${info.tagName}');">${info.tagName}</a>
</c:forEach>
							
	
				
				
