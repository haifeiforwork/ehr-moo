<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="preHeader"  value="ui.support.searchpreprocessor.header" /> 
<c:set var="preButton"  value="ui.support.searchpreprocessor.common.button" /> 
<c:set var="preLeft"  value="ui.support.searchpreprocessor.common.left" /> 
<c:set var="preField"  value="message.support.searchpreprocessor.field" /> 
<c:set var="preSearch"  value="ui.support.searchpreprocessor.common.searchCondition" /> 
<c:set var="preList"    value="ui.support.searchpreprocessor.list" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 <script type="text/javascript">
 
<!--
<c:choose>
<c:when test="${user.localeCode == portal.defaultLocaleCode}">
 var myLocale =true;
</c:when>
<c:otherwise>
var myLocale =false; 
</c:otherwise>
</c:choose>

$jq(document).ready(function() { 
	
 $jq("select[name=searchColumn]").change(function() {
      $jq("#searchButton").click();  
  }); 
	
 $jq("select[name=pagePerRecord]").change(function() {
      $jq("#searchButton").click();  
  }); 
 
 $jq("#searchButton").click(function() {   
		$jq("#searchForm").submit(); 
		return false; 
	});
 
});  
//-->
 </script> 
<!--mainContents Start-->
	<h1 class="none"><ikep4j:message pre="${preLeft}" key="8.0" /></h1> 
	<!--pageTitle Start-->

		
	<!--//pageTitle End-->
	
	<div class="subTitle_3">
		<h3><ikep4j:message pre="${preHeader}" key="subTitle.8" /></h3>
	</div>
	
	<!--//pageTitle End-->  
	<!--blockListTable Start-->
	<form id="searchForm" method="post" action="<c:url value='/support/searchpreprocessor/batchlog/history.do' />">  
	<input type="hidden" name="redirect" value="list" title="redirect"/>
	<div class="blockListTable">  
		<!--tableTop Start-->  
		<div class="tableTop">  
			<div class="listInfo">  
				<spring:bind path="searchCondition.pagePerRecord">  
					<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<c:forEach var="code" items="${codeList}">
						<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
					</c:forEach> 
					</select> 
				</spring:bind>
				<div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)</div>
			</div>	  
			<div class="tableSearch"> 
				<spring:bind path="searchCondition.searchColumn">  
					<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
						<option value="rankSchedule" <c:if test="${'rankSchedule' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='${status.expression}'  post='rankSchedule'/></option>
						<option value="rankHistoryScheduled" <c:if test="${'rankHistoryScheduled' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='${status.expression}'  post='rankHistoryScheduled'/></option>
					</select>		
				</spring:bind>	
				<a id="searchButton" name="searchButton" href="#a"><img src="<c:url value='/base/images/theme/theme01/basic/ic_search.gif' />" alt="검색" /></a> 	
			</div>			
			<div class="clear"></div>
		</div>
		<!--//tableTop End-->
		<table id="surveyInfoTable" summary="<ikep4j:message pre="${preList}" key="summary" />">   
			<caption></caption>
			<col style="width: 6%;"/>
			<col style="width: *"/>
			<col style="width: 10%;"/>
			<col style="width: 20%;"/>
			<col style="width: 20%;"/>
			<thead>
				<tr>
					<th scope="col"><ikep4j:message pre='${preList}' key='rowIndex' /></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='name' /></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='result' /></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='startDate' /></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='endDate' /></th>
				</tr>
			</thead> 
			<tbody>
				<c:choose>
				    <c:when test="${searchResult.emptyRecord}">
						<tr>
							<td colspan="4" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
						</tr>				        
				    </c:when>
				    <c:otherwise>
				    	<c:set var="rowNum" value="${searchCondition.startRowIndex }"/>
						<c:forEach var="info" items="${searchResult.entity}"   varStatus="loopStatus">
						<c:set var="rowNum" value="${rowNum+1}"/>
						<tr>
							<td> ${rowNum}</td>
							<td>${info.name}</td>
							<td><ikep4j:message pre="${preField}" key="${info.result}" /></td>
							<td><ikep4j:timezone  date="${info.startDate}" pattern="message.support.searchpreprocessor.timezone.dateformat.yyyyMMddhhmmss" keyString="true"/></td>
							<td><ikep4j:timezone  date="${info.endDate}"  pattern="message.support.searchpreprocessor.timezone.dateformat.yyyyMMddhhmmss" keyString="true"/></td>
						</tr>
						</c:forEach>				      
				    </c:otherwise> 
				</c:choose>  
			</tbody>
		</table>
		<!--Page Numbur Start--> 
		<spring:bind path="searchCondition.pageIndex">
		<ikep4j:pagination searchButtonId="searchButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind> 
		<!--//Page Numbur End--> 
	</div>
	</form>
