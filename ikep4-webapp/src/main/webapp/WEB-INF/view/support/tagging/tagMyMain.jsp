<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<c:set var="preUi" 	value="ui.support.tagging" />

<script type="text/javascript">
//<![CDATA[
function goPage(val){
	
	location.href = 'listAllTag.do?tagItemType='+val+'&goMore=1&isMy=1';
	
}
//]]>
</script>

<c:forEach var="tagItem" items="${items}">
	<div class="subTitle_2">
		<h3>${tagItem.title}</h3>
		<div class="btn_more">
			<c:if test="${tagItem.count >= 10}">
				<a href="#a" onclick="goPage('${tagItem.code}');return false;" title="more"><img src="<c:url value="/base/images/common/btn_more.gif"/>" alt="more" /></a>
			</c:if>
		</div>
	</div>
	<div class="tag_cloud">
		<c:choose>
			<c:when test="${tagItem.count > 0}">
				<c:forEach var="tag" items="${tagItem.tags}">
					<span><a href="tagSearch.do?tagId=${tag.tagId}&amp;tagItemType=${tagItem.code}" class="tag${tag.displayStep}" title="tag">${tag.tagName}</a></span>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<ikep4j:message key='message.support.tagging.noDate.registe'/>
			</c:otherwise>
		</c:choose>
	</div>
</c:forEach>

