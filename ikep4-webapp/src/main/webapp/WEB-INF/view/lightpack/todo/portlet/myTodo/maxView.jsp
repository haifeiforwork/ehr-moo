<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.lightpack.todo.listTodoView</c:set>
<c:set var="messagePrefix">ui.lightpack.todo.message</c:set>
<c:set var="buttonPrefix">ui.lightpack.todo.button</c:set>
<c:set var="preSearch">ui.lightpack.todo.common.searchCondition</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
 
<script type="text/javascript">
//<![CDATA[
(function($) { 
	$jq(document).ready(function(){	
		$jq("#searchpagingButton_${portletConfigId}").hide();
		
		$jq("#pagePerRecordTodo_${portletConfigId}").change(function() {
			var pageURL = "<c:url value='/lightpack/todo/portlet/myTodo/maxView.do'/>" + "?pagePerRecord=" + $jq("#pagePerRecordTodo_${portletConfigId}").val() + "&pageIndex=" + $jq("#form input[name=pageIndex]").val();
			PortletSimple.load('${portletConfigId}', pageURL, 'error');
        });
		$jq("#searchpagingButton_${portletConfigId}").click(function() {
			var pageURL = "<c:url value='/lightpack/todo/portlet/myTodo/maxView.do'/>" + "?pagePerRecord=" + $jq("#pagePerRecordTodo_${portletConfigId}").val() + "&pageIndex=" + $jq("#form input[name=pageIndex]").val();
			PortletSimple.load('${portletConfigId}', pageURL, 'error');
		});		
	});

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

<div id="${portletConfigId}" class="portletUnit_c">
	<form id="form" action='<c:url value="/lightpack/todo/portlet/myTodo/maxView.do"/>' method="post">
		<!--tableTop Start-->					
		<div class="tableTop">
			<div class="tableTop_bgR"></div>
			<div class="listInfo">
			<spring:bind path="todoSearchCondition.pagePerRecord">
			<select id="pagePerRecordTodo_${portletConfigId}" name="${status.expression}" title="<ikep4j:message pre='${prefix}' key='search.pageName'/>">
				<option <c:if test="${todoSearchCondition.pagePerRecord == 10}">selected="selected"</c:if> value="10">10</option>
				<option <c:if test="${todoSearchCondition.pagePerRecord == 15}">selected="selected"</c:if> value="15">15</option>
				<option <c:if test="${todoSearchCondition.pagePerRecord == 20}">selected="selected"</c:if> value="20">20</option>
				<option <c:if test="${todoSearchCondition.pagePerRecord == 30}">selected="selected"</c:if> value="30">30</option>
				<option <c:if test="${todoSearchCondition.pagePerRecord == 40}">selected="selected"</c:if> value="40">40</option>
				<option <c:if test="${todoSearchCondition.pagePerRecord == 50}">selected="selected"</c:if> value="50">50</option>							
			</select>
			</spring:bind>
			<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span>${searchResult.recordCount}</span></div>
			</div>
			<a href="#none" id="searchpagingButton_${portletConfigId}" class="ic_search"><span><ikep4j:message pre='${buttonPrefix}' key='search'/></span></a>
			<div class="clear"></div>
		</div>
		<!--//tableTop End-->								

		<!--blockListTable Start-->
		<div class="blockListTable msgTable" style="margin-bottom:0px;">
			<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="3%"><ikep4j:message pre='${prefix}' key='table.column2'/></th>
						<th scope="col" width="20%"><ikep4j:message pre='${prefix}' key='table.column3'/></th>
						<th scope="col" width="*"><ikep4j:message pre='${prefix}' key='table.column4'/></th>
						<th scope="col" width="10%"><ikep4j:message pre='${prefix}' key='table.column5'/></th>
						<th scope="col" width="80"><ikep4j:message pre='${prefix}' key='table.column6'/></th>
						<th scope="col" width="80"><ikep4j:message pre='${prefix}' key='table.column7'/></th>
						<th class="tdLast" scope="col" width="110"><ikep4j:message pre='${prefix}' key='table.column8'/></th>
			
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${searchResult.entity}" varStatus="status">
						<tr>
							<td>${(todoSearchCondition.pagePerRecord*(todoSearchCondition.pageIndex-1))+status.count}</td>
							<td class="textLeft"><div class="ellipsis">[${item.systemName}]&nbsp;${item.subworkName}</div></td>
							<td class="textLeft">
								<c:if test="${item.systemCode == todoSystemCode && item.subworkCode == todoSubworkCode}">
									<div class="ellipsis"><a href="#a" onclick="goURL_${portletConfigId}('Y', '${item.target}', '<c:url value="${item.url}"/>', '${item.systemCode}', '${item.subworkCode}', '${item.taskKey}', '${item.workerId}')" title="${item.title}">${item.title}</a></div>
								</c:if>
								<c:if test="${item.systemCode != todoSystemCode || item.subworkCode != todoSubworkCode}">
									<div class="ellipsis"><a href="#a" onclick="goURL_${portletConfigId}('N', '${item.target}', '<c:url value="${item.url}"/>', '${item.systemCode}', '${item.subworkCode}', '${item.taskKey}', '${item.workerId}')" title="${item.title}">${item.title}</a></div>
								</c:if>
							</td>
							<td>
								<c:choose>
								    <c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.etcName}</c:when>
								    <c:otherwise>${item.etcEnglishName}</c:otherwise>
							    </c:choose>
							</td>
							<td><ikep4j:timezone date="${item.syncInsertDate}" pattern="yyyy.MM.dd"/></td>
							<td><ikep4j:timezone date="${item.dueDate}" pattern="yyyy.MM.dd"/></td>
							<td class="tdLast">
								<div class="ellipsis">
									<c:if test="${item.todoStatusName == 'B'}"><ikep4j:message pre='${messagePrefix}' key='status.complete'/></c:if>
									<c:if test="${fn:substring(item.todoStatusName, 0, 1) == 'A'}">${fn:substring(item.todoStatusName, 1, fn:length(item.todoStatusName))}</c:if>
									<c:if test="${fn:substring(item.todoStatusName, 0, 1) == 'D'}"><span class="colorPointS"><ikep4j:message pre='${messagePrefix}' key='status.delay'/></span> <span class="delay">D+${fn:substring(item.todoStatusName, 1, fn:length(item.todoStatusName))}</span></c:if>
								</div>
							</td>   
						</tr>	
					</c:forEach>																								
				</tbody>
			</table>	
			
			<!--Page Numbur Start-->
			<c:if test="${searchResult.recordCount > 0}">
				<spring:bind path="todoSearchCondition.pageIndex">
				<ikep4j:pagination searchButtonId="searchpagingButton_${portletConfigId}" pageIndexInput="${status.expression}" searchCondition="${todoSearchCondition}" />
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
			</c:if>
			<!--//Page Numbur End-->	
		</div>		
	</form>										
</div>	