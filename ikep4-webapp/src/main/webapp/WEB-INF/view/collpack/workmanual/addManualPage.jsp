<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.collpack.workmanual.listWorkManualView</c:set>
<c:set var="messagePrefix">ui.collpack.workmanual.message</c:set>
<c:set var="buttonPrefix">ui.collpack.workmanual.button</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
//<![CDATA[
	$jq(document).ready(function(){
		$jq("#showMoreForm>input[name=maxRowNum]").attr("value", ${maxRowNum});
		$jq("#showMoreForm>input[name=endRowNum]").attr("value", ${endRowNum});
	
	});

//]]>
</script>

		
							<c:forEach var="item" items="${manualList}">
							<tr>
								<td>
									<div class="summaryViewTitle">
										<c:if test="${item.manualType == 'A'}">[<ikep4j:message pre='${messagePrefix}' key='manual.submit'/>]</c:if>
										<c:if test="${item.manualType == 'B'}">[<ikep4j:message pre='${messagePrefix}' key='manual.submit.revision'/>]</c:if>
										<c:if test="${item.manualType == 'C'}">[<ikep4j:message pre='${messagePrefix}' key='manual.disuse'/>]</c:if>
										 &nbsp;<a href="<c:url value='/collpack/workmanual/readManualView.do?manualId=${item.manualId}&amp;pathStep=A'/>">${item.title}</a>&nbsp;(<ikep4j:message pre='${prefix}' key='table.version'/>:${item.version})
									</div>
									<div class="summaryViewInfo">
										<span class="summaryViewInfo_name">
											<c:choose>
											    <c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.updaterName}&nbsp;${item.jobTitleName}</c:when>
											    <c:otherwise>${item.updaterEnglishName}&nbsp;${item.jobTitleEnglishName}</c:otherwise>
										    </c:choose> 
										</span>
										<img src="<c:url value="/base/images/common/bar_info.gif"/>" alt="" />
										<span><ikep4j:timezone date="${item.updateDate}" pattern="yyyy.MM.dd HH:mm"/></span>
										<img src="<c:url value="/base/images/common/bar_info.gif"/>" alt="" />
										<span><ikep4j:message pre="${prefix}" key="table.search"/> <strong>${item.hitCount}</strong></span>
									</div>
									<div class="summaryViewCon">${item.contents}</div>
									<div class="summaryViewTag">
									  <span class="ic_tag"><span><ikep4j:message pre="${prefix}" key="table.tag"/></span></span>
									  <c:forEach var="tag" items="${item.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a></c:forEach>
									</div>
								</td>
							</tr>
							</c:forEach>										