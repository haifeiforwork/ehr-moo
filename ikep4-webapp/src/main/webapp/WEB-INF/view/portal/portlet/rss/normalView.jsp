<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="prefix" value="message.portal.portlet.rss.normalView"/>

<c:set var="channel" value="${channel}" />

<script type="text/javascript">
<!--   
	 function winOpen_${portletConfigId}(url) {
		window.open(url, "", "width=800,height=500,status=no,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=yes");
	 }
//-->
</script>
<div id="${portletConfigId}">
    <c:if test="${!empty channel.channelItemList}">
    <h4 class="guidetitle_han"><c:out value="${channel.channelTitle}"/></h4>
    <div class="tableList_1"> 
        <table width="100%">
            <tbody>
                <c:forEach var="item" items="${channel.channelItemList}" varStatus="i">
                    <c:if test="${portletRss.listCount >= i.count}">
                        <tr>
                            <td class="t_po1">
                                <div class="ellipsis">
                                    <a href="#a" onclick="javascript: winOpen_${portletConfigId}('<c:out value='${item.itemUrl}' escapeXml='false'/>');">${item.itemTitle}</a>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>                            
    </div>
    </c:if>
    <c:if test="${empty portletRss.rssUrl}">
        <ikep4j:message pre="${prefix}" key="rssNoUrlMessage" />
    </c:if>
</div>