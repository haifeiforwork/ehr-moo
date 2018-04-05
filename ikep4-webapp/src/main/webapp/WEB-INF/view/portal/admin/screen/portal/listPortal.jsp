<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="prefix" value="ui.portal.admin.screen.portal.listPortal"/>
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />

<script type="text/javascript">
//<![CDATA[
(function($) {
	$jq(document).ready(function() { 
		
		//left menu setting
		$jq("#portalLinkOfLeft").click();
		
		$jq("select[name=pagePerRecord]").change(function() {
			getList();
        });  
		
		$jq('#searchWord').keypress(function(event) { 
			if(event.which == '13') {
				getList();
			}
		});
	});
	
	getList = function() {
		$("#searchForm")[0].submit();
	};
    
    sort = function(sortColumn, sortType) {
		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			$jq("input[name=sortType]").val('DESC');	
		} else {
			$jq("input[name=sortType]").val('ASC');
		}
		
		getList();
	};
})(jQuery);
//]]>
</script>

<form id="searchForm" method="post" action="<c:url value='/portal/admin/screen/portal/listPortal.do'/>" onsubmit="return false;">

<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 		
<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2><ikep4j:message pre='${prefix}' key='pageTitle'/></h2>
	<div class="listInfo">  
		<spring:bind path="searchCondition.pagePerRecord">  
			<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
			<c:forEach var="code" items="${portalCode.pageNumList}">
				<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
			</c:forEach> 
			</select> 
		</spring:bind>
		<div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)</div>
	</div>	  
	<div class="tableSearch"> 
		<spring:bind path="searchCondition.searchColumn">  
			<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<option value="PORTAL_NAME" <c:if test="${'PORTAL_NAME' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${prefix}' key='portalName' post=''/></option>
			</select>	
		</spring:bind>		
		<spring:bind path="searchCondition.searchWord">  					
			<input name="${status.expression}" id="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
		</spring:bind>
		<a class="ic_search" href="#a" onclick="javascript:getList();" ><span><ikep4j:message pre='${prefix}' key='search' /></span></a> 
	</div>		
	<div class="clear"></div>
</div>
<!--//tableTop End-->

<!--splitterBox Start-->
<div id="splitterBox">
	<div>
		<!--blockListTable Start-->
		<div class="blockListTable">					
			<table summary="summary">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="20%">
							<a onclick="sort('PORTAL_ID', '<c:if test="${searchCondition.sortColumn eq 'PORTAL_ID'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='portalId' />
								<c:if test="${searchCondition.sortColumn eq 'PORTAL_ID'}">
									<c:if test="${searchCondition.sortType eq 'DESC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
									</c:if>
									<c:if test="${searchCondition.sortType eq 'ASC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
									</c:if>
								</c:if>
							</a>
						</th>
					
						<th scope="col" width="25%">
							<a onclick="sort('PORTAL_NAME', '<c:if test="${searchCondition.sortColumn eq 'PORTAL_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='portalName' />
								<c:if test="${searchCondition.sortColumn eq 'PORTAL_NAME'}">
									<c:if test="${searchCondition.sortType eq 'DESC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
									</c:if>
									<c:if test="${searchCondition.sortType eq 'ASC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
									</c:if>
								</c:if>
							</a>
						</th>
						
						<th scope="col" width="35%">
							<a onclick="sort('DESCRIPTION', '<c:if test="${searchCondition.sortColumn eq 'DESCRIPTION'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='description' />
								<c:if test="${searchCondition.sortColumn eq 'DESCRIPTION'}">
									<c:if test="${searchCondition.sortType eq 'DESC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
									</c:if>
									<c:if test="${searchCondition.sortType eq 'ASC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
									</c:if>
								</c:if>
							</a>
						</th>
						<th scope="col" width="10%">
							<a onclick="sort('DEFAULT_OPTION', '<c:if test="${searchCondition.sortColumn eq 'DEFAULT_OPTION'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='defaultOption' />
								<c:if test="${searchCondition.sortColumn eq 'DEFAULT_OPTION'}">
									<c:if test="${searchCondition.sortType eq 'DESC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
									</c:if>
									<c:if test="${searchCondition.sortType eq 'ASC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
									</c:if>
								</c:if>
							</a>
						</th>
						<th scope="col" width="10%">
							<a onclick="sort('ACTIVE', '<c:if test="${searchCondition.sortColumn eq 'ACTIVE'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='active' />
								<c:if test="${searchCondition.sortColumn eq 'ACTIVE'}">
									<c:if test="${searchCondition.sortType eq 'DESC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
									</c:if>
									<c:if test="${searchCondition.sortType eq 'ASC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
									</c:if>
								</c:if>
							</a>
						</th>
					</tr>
				</thead>
				<tbody>
						<c:choose>
						    <c:when test="${searchResult.emptyRecord}">
								<tr>
									<td colspan="5" class="emptyRecord"><ikep4j:message pre='${prefix}' key='list.empty' /></td> 
								</tr>				        
						    </c:when>
						    <c:otherwise>
								<c:forEach var="portal" items="${searchResult.entity}" varStatus="status">
								<tr>
									<td class="textCenter">
										<a href="<c:url value='/portal/admin/screen/portal/readPortal.do?portalId=${portal.portalId}'/>">
											${portal.portalId}
										</a>
									</td>
									<td>
										<a href="<c:url value='/portal/admin/screen/portal/readPortal.do?portalId=${portal.portalId}'/>">
											${portal.portalName}
										</a>
									</td>
									<td class="textLeft">
										<div class="ellipsis">
											<a href="<c:url value='/portal/admin/screen/portal/readPortal.do?portalId=${portal.portalId}'/>">
												${portal.description}
											</a>
										</div>
									</td>
									<td class="textCenter">
										<c:if test="${portal.defaultOption == 1}">
											<ikep4j:message pre='${prefix}' key='defaultPortal' />
										</c:if>
										<c:if test="${portal.defaultOption != 1}">
											<ikep4j:message pre='${prefix}' key='noDefaultPortal' />
										</c:if>
									</td>
									<td class="textCenter">
										<c:if test="${portal.active == 1}">
											<ikep4j:message pre='${prefix}' key='use' />
										</c:if>
										<c:if test="${portal.active != 1}">
											<ikep4j:message pre='${prefix}' key='noUse' />
										</c:if>
									</td>
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
		</div>
		<!--//blockListTable End-->
		
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a class="button" href="<c:url value='/portal/admin/screen/portal/createPortalForm.do'/>"><span><ikep4j:message pre='${prefix}' key='button.addForm' /></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
</div>
<!--//splitterBox End-->
</form>