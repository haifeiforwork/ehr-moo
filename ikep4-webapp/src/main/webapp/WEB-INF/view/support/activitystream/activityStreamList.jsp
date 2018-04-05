<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.activitystream.header" /> 
<c:set var="preList"    value="ui.support.activitystream.list" />
<c:set var="preDetail"  value="ui.support.activitystream.detail" />
<c:set var="preButton"  value="ui.support.activitystream.button" /> 
<c:set var="preMessage" value="ui.support.activitystream.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>
 
 <script type="text/javascript" language="javascript">

(function($) {
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 

		$jq("select[name=pagePerRecord]").change(function() {
			getList();
        });  
		
		$jq('#searchWord').keypress(function(event) { 
			if(event.which == '13') {
				getList();
			}
		}); 
		
		
	});
	
	//$jq("#stopSpan").hide();
	
})(jQuery);  


</script>
					
				

<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

					<form id="searchForm" method="post" action="" onsubmit="return false">  
					
					<spring:bind path="searchCondition.sortColumn">
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
					</spring:bind> 		
					<spring:bind path="searchCondition.sortType">
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
					</spring:bind> 
					
									
					<!--tableTop Start-->  
					<div class="tableTop">  
						<div class="listInfo">  
							<spring:bind path="searchCondition.pagePerRecord">  
								<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
								<c:forEach var="code" items="${boardCode.pageNumList}">
									<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
								</c:forEach> 
								</select> 
							</spring:bind>
							<div class="totalNum">
								${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)
							</div>
						</div>	  
						<div class="tableSearch"> 
							<spring:bind path="searchCondition.searchColumn">  
								<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
									<option value="moduleName" <c:if test="${'moduleName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='moduleName'/></option>
									<option value="activityDescription" <c:if test="${'activityDescription' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='activityDescription'/></option>
									<option value="target" <c:if test="${'target' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='target'/></option>
									<option value="actorId" <c:if test="${'actorId' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='actorId'/></option>
									<option value="actorName" <c:if test="${'actorName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='actorName'/></option>
								</select>	
							</spring:bind>		
							<spring:bind path="searchCondition.searchWord">  					
								<input name="${status.expression}" id="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
							</spring:bind>
							<a id="searchBoardItemButton" name="searchBoardItemButton" href="javascript:getList()" ><img src="<c:url value='/base/images/theme/theme01/basic/ic_search.gif' />" alt="<ikep4j:message pre='${preButton}' key='search' />" /></a> 
						</div>		
						<div class="clear"></div>	
					</div>
					<!--//tableTop End-->		
					
					

					<table summary="<ikep4j:message pre='${preList}' key='main.title' />">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="10%">
									<a onclick="javascript:sort('MODULE_NAME', '<c:if test="${searchCondition.sortColumn eq 'MODULE_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preList}' key='moduleName' />
									</a>
								</th>
								<th scope="col" width="40%">
									<a onclick="sort('TARGET', '<c:if test="${searchCondition.sortColumn eq 'ACTIVITY_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preList}' key='activityName' />
									</a>
								</th>
								<th scope="col" width="15%">
									<a onclick="sort('MODULE_UNIT_ID', '<c:if test="${searchCondition.sortColumn eq 'MODULE_UNIT_ID'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preList}' key='moduleUnitId' />
									</a>
								</th>
								<th scope="col" width="10%">
									<a onclick="sort('ACTOR_ID', '<c:if test="${searchCondition.sortColumn eq 'ACTOR_ID'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preList}' key='actorId' />
									</a>
								</th>
								<th scope="col" width="10%">
									<a onclick="sort('ACTOR_NAME', '<c:if test="${searchCondition.sortColumn eq 'ACTOR_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preList}' key='actorName' />
									</a>
								</th>
								<th scope="col" width="15%">
									<a onclick="sort('ACTIVITY_DATE', '<c:if test="${searchCondition.sortColumn eq 'ACTIVITY_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preList}' key='activityDate' />
									</a>
								</th>
							</tr>
						</thead>
						
						<tbody>
						
							<c:choose>
							    <c:when test="${searchResult.emptyRecord}">
									<tr>
										<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preMessage}' key='list.empty' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="activityStream" items="${searchResult.entity}">
									<tr>
										<td>${activityStream.moduleName}</td>	
										<td>${activityStream.target}</td>	
										<td>${activityStream.moduleUnitId}</td>	
										<td>${activityStream.actorId}</td>	
										<td>${activityStream.actorName}</td>	
										<td><ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${activityStream.activityDate}"/></td>
									</tr>
									</c:forEach>				      
							    </c:otherwise> 
							</c:choose>  
																																																								
						</tbody>
						
					</table>			
					
					
					<!--Page Numbur Start--> 
					<spring:bind path="searchCondition.pageIndex">
						<ikep4j:pagination searchFormId="searchForm" ajaxEventFunctionName="getList" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
					</spring:bind> 
					<!--//Page Numbur End-->
					
					
					</form>
					
					
					
					
	
					
				
				
