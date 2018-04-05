<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.message.messageUserVolumnInfo.header" /> 
<c:set var="preList"    value="ui.support.message.messageUserVolumnInfo.list" />
<c:set var="preSearch"  value="ui.support.message.searchCondition" /> 
<c:set var="preButton"  value="ui.support.message.button" /> 
<c:set var="preMsg"  value="ui.support.message.MSG" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
<!-- 
	
	$jq(document).ready(function() { 
		sort = function(sortColumn, sortType) {
			$jq("#searchForm input[name=sortColumn]").val(sortColumn); 
			$jq("#searchForm input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');	 
			$jq("#searchMessageListButton").click();
		}; 
		
		$jq("#searchMessageListButton").click(function() {   
			$jq("#searchForm").submit(); 
			return false; 
		});  
		
		$jq("select[name=pagePerRecord]").change(function() {
            $jq("#searchMessageListButton").click();  
        }); 
	});
	
//-->
</script>

		<!--blockListTable Start-->
		<form id="searchForm" method="post" action="<c:url value='/support/message/messageUserVolumnInfo.do' />">  
			<spring:bind path="searchCondition.sortColumn">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind> 		
			<spring:bind path="searchCondition.sortType">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind> 
		<div class="blockListTable msgTable" style="margin-bottom:0;">
			<!--tableTop Start-->
			<div class="tableTop">
				<div class="tableTop_bgR"></div>
				<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
				<div class="listInfo">
					<spring:bind path="searchCondition.pagePerRecord" >  
						<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
						<c:forEach var="code" items="${messageCode.pageNumList}">
							<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
						</c:forEach> 
						</select> 
					</spring:bind>
					<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
				</div>					
		
				<div class="tableSearch">
					<ikep4j:message pre='${preSearch}' key='userName'/>
					<spring:bind path="searchCondition.userName">  					
						<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
					</spring:bind>
					<a id="searchMessageListButton" name="searchMessageListButton" href="#a" class="ic_search"><span>Search</span></a>
				</div>			
				<div class="clear"></div>
			</div>
			<!--//tableTop End-->
		</div>
		<div class="blockListTable msgTable">
			<table summary="<ikep4j:message pre="${preList}" key="summary" />">   
				<caption></caption>
				<col style="width: 10%;"/>
				<col style="width: 25%;"/>
				<col style="width: *;"/>
				<col style="width: 20%;"/>
				<col style="width: 20%;"/>
				<thead>
					<tr>
						<th scope="col"><ikep4j:message pre='${preList}' key='rnum' /></th>
						<th scope="col"><ikep4j:message pre='${preList}' key='userName' /></th>
						<th scope="col"><ikep4j:message pre='${preList}' key='teamName' /></th>
						<th scope="col">
							<a onclick="sort('USE_MONTH_FILESIZE', '<c:if test="${searchCondition.sortColumn eq 'USE_MONTH_FILESIZE'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preList}' key='useMonthFilesize' />
							</a>
							<c:choose>
								<c:when test="${searchCondition.sortColumn eq 'USE_MONTH_FILESIZE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
								<c:when test="${searchCondition.sortColumn eq 'USE_MONTH_FILESIZE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
							</c:choose>
						</th>
						<th scope="col" class="tdLast">
							<a onclick="sort('USE_STORED_FILESIZE', '<c:if test="${searchCondition.sortColumn eq 'USE_STORED_FILESIZE'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preList}' key='useStoredFilesize' />
							</a>
							<c:choose>
								<c:when test="${searchCondition.sortColumn eq 'USE_STORED_FILESIZE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
								<c:when test="${searchCondition.sortColumn eq 'USE_STORED_FILESIZE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
							</c:choose>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
					    <c:when test="${searchResult.emptyRecord}">
							<tr>
								<td colspan="5" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
							</tr>				        
					    </c:when>
					    <c:otherwise>
							<c:forEach var="messageMonitorItem" items="${searchResult.entity}" varStatus = "status"> 
							<tr>
								<td>${messageMonitorItem.rnum}</td>
								
								<c:choose>
							      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
								      <td>${messageMonitorItem.userName}</td>
									  <td>${messageMonitorItem.teamName}</td>
								  </c:when>
							      <c:otherwise>
							       	  <td>${messageMonitorItem.userEnglishName}</td>
									  <td>${messageMonitorItem.teamEnglishName}</td>
							      </c:otherwise>
							    </c:choose>
							     
								<td class="textRight">${messageMonitorItem.useMonthFilesize}MB</td>
								<td class="tdLast" style="text-align:right;">${messageMonitorItem.useStoredFilesize}MB </td>
							</tr>
							</c:forEach>				      
					    </c:otherwise> 
					</c:choose>  
				</tbody>
			</table>
			<!--Page Numbur Start-->
			<spring:bind path="searchCondition.pageIndex">
			<ikep4j:pagination searchButtonId="searchMessageListButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind> 
			<!--//Page Numbur End-->			
		</div>
		<!--//blockListTable End-->	
		</form>
