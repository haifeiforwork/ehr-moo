<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.lightpack.todo.listTodoView</c:set>
<c:set var="messagePrefix">ui.lightpack.todo.message</c:set>
<c:set var="buttonPrefix">ui.lightpack.todo.button</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
 
<script type="text/javascript">
//<![CDATA[
(function($) {
	//todo 제목 클릭
	goURL_${portletConfigId} = function(isTodo, target, url, systemCode, subworkCode, taskKey, workerId) {
		if(isTodo == "N") {
			alert("<ikep4j:message pre='${messagePrefix}' key='goUrl'/>");
		} else {
			if(target == "WINDOW") {
				var pageURL = url + "?systemCode=" + systemCode + "&subworkCode=" + subworkCode + "&taskKey=" + taskKey + "&workerId=" + workerId;
				window.open (pageURL, '', 'fullscreen=yes, resizable=yes, scrollbar=yes, toolbar=yes, menubar=yes');
			} else {
				var pageURL = "<c:url value='/lightpack/todo/todoView.do'/>" + "?systemCode=" + systemCode + "&subworkCode=" + subworkCode + "&taskKey=" + taskKey + "&workerId=" + workerId;
				window.location.href = pageURL;
			}
		}
	}
})(jQuery);	
//]]>
</script>
<div id="${portletConfigId}" class="po_schedule_w">
	<div class="blockListTable msgTable">
		<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="55%"><ikep4j:message pre='${prefix}' key='table.column4'/></th>
					<th scope="col" width="20%"><ikep4j:message pre='${prefix}' key='table.column7'/></th>
					<th scope="col" width="25%" class="tdLast"><ikep4j:message pre='${prefix}' key='table.column8'/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${todoList}" varStatus="status">
				<tr>
					<td class="textLeft" width="55%">
						<c:if test="${item.systemCode == todoSystemCode && item.subworkCode == todoSubworkCode}">
							<div class="ellipsis"><a href="#a" onclick="goURL_${portletConfigId}('Y', '${item.target}', '<c:url value="${item.url}"/>', '${item.systemCode}', '${item.subworkCode}', '${item.taskKey}', '${item.workerId}')">${item.title}</a></div>
						</c:if>
						<c:if test="${item.systemCode != todoSystemCode || item.subworkCode != todoSubworkCode}">
							<div class="ellipsis"><a href="#a" onclick="goURL_${portletConfigId}('N', '${item.target}', '<c:url value="${item.url}"/>', '${item.systemCode}', '${item.subworkCode}', '${item.taskKey}', '${item.workerId}')">${item.title}</a></div>
						</c:if>
					</td>
					<td><ikep4j:timezone date="${item.dueDate}" pattern="MM.dd"/></td>
					<td class="tdLast">
						<div class="ellipsis">
							<c:if test="${item.todoStatusName == 'B'}"><ikep4j:message pre='${messagePrefix}' key='status.complete'/></c:if>
							<c:if test="${fn:substring(item.todoStatusName, 0, 1) == 'A'}">${fn:substring(item.todoStatusName, 1, fn:length(item.todoStatusName))}</c:if>
							<c:if test="${fn:substring(item.todoStatusName, 0, 1) == 'D'}"><div title="<ikep4j:message pre='${messagePrefix}' key='status.delay'/> D+${fn:substring(item.todoStatusName, 1, fn:length(item.todoStatusName))}"><span class="colorPointS inline"><ikep4j:message pre='${messagePrefix}' key='status.delay'/></span><span class="delay inline">D+${fn:substring(item.todoStatusName, 1, fn:length(item.todoStatusName))}</span></div></c:if>
						</div>
					</td>   
				</tr>
				</c:forEach>																													
			</tbody>
		</table>
	</div>					
</div>