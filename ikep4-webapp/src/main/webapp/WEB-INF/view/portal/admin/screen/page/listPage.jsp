<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="prefix" value="message.portal.admin.screen.page.listPage"/>

<script type="text/javascript">
//<!--
(function($) {
	$jq(document).ready(function() { 
		
		//left menu setting
		$jq("#portletPageManageOfLeft").click();
		
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
	
	getView = function (pageId) {
		location.href = '<c:url value="readPageMain.do?pageId="/>'+pageId;
	};
})(jQuery);
//-->
</script>

<form id="searchForm" method="post" action="<c:url value='/portal/admin/screen/page/listPage.do'/>" onsubmit="return false;">
<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}"/>
</spring:bind>
<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}"/>
</spring:bind>

<h1 class="none"><ikep4j:message pre="${prefix}" key="noneTitle" /></h1>

<!--pageTitle Start-->
<!--  div id="pageTitle">
	<h2><ikep4j:message pre="${prefix}" key="pageTitle" /></h2>
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${prefix}" key="home" /></li>
			<li><ikep4j:message pre="${prefix}" key="1depth" /></li>
			<li><ikep4j:message pre="${prefix}" key="2depth" /></li>
			<li><ikep4j:message pre="${prefix}" key="3depth" /></li>
			<li class="liLast"><ikep4j:message pre="${prefix}" key="lastDepth" /></li>
		</ul>
	</div>
</div-->
<!--//pageTitle End-->

<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2><ikep4j:message pre="${prefix}" key="pageTitle" /></h2>
	<div class="listInfo">
		<spring:bind path="searchCondition.pagePerRecord">  
			<select name="${status.expression}" title="<ikep4j:message pre="${prefix}" key="${status.expression}" />" onchange="getList(); return false;">
			<c:forEach var="code" items="${portalCode.pageNumList}">
				<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
			</c:forEach> 
			</select> 
		</spring:bind>
		<div class="totalNum">
			${searchResult.pageIndex}/${searchResult.pageCount}<ikep4j:message pre="${prefix}" key="page" /> (<ikep4j:message pre="${prefix}" key="page" /><span>${searchResult.recordCount}</span>)
		</div>
	</div>					
	<div class="tableSearch">
		<spring:bind path="searchCondition.searchColumn">  
			<select name="${status.expression}" title="<ikep4j:message pre='${prefix}' key='${status.expression}' />">
				<option value="PAGE_NAME" <c:if test="${'PAGE_NAME' eq status.value}">selected="selected"</c:if>><ikep4j:message pre="${prefix}" key="pageName"/></option>
				<option value="SYSTEM_NAME" <c:if test="${'SYSTEM_NAME' eq status.value}">selected="selected"</c:if>><ikep4j:message pre="${prefix}" key="systemName"/></option>
			</select>		
		</spring:bind>		
		<spring:bind path="searchCondition.searchWord">  					
			<input name="${status.expression}" id="${status.expression}" value="${status.value}" type="text" class="inputbox" title="<ikep4j:message pre='${prefix}' key='${status.expression}' />" size="20"/>
		</spring:bind>
		<a class="ic_search" href="#a" onclick="javascript:getList();"><span><ikep4j:message pre='${prefix}' key='search' /></span></a>
	</div>	
	<div class="clear"></div>
</div>
<!--//tableTop End-->

<!--splitterBox Start-->
<div id="splitterBox">
	<div>
		<!--blockListTable Start-->
		<div class="blockListTable">					
			<table summary="<ikep4j:message pre='${prefix}' key='summary' />">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="5%"><ikep4j:message pre='${prefix}' key='num' /></th>
						<th scope="col" width="30%">
							<a href="#" onclick="sort('PAGE_NAME', '<c:if test="${searchCondition.sortColumn eq 'PAGE_NAME'}">${searchCondition.sortType}</c:if>'); return false;">
								<ikep4j:message pre='${prefix}' key='pageName' />
								<c:if test="${searchCondition.sortColumn eq 'PAGE_NAME'}">
									<c:if test="${searchCondition.sortType eq 'DESC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
									</c:if>
									<c:if test="${searchCondition.sortType eq 'ASC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
									</c:if>
								</c:if>
							</a>
						</th>
						<th scope="col" width="20%">
							<a href="#" onclick="sort('LAYOUT_NAME', '<c:if test="${searchCondition.sortColumn eq 'LAYOUT_NAME'}">${searchCondition.sortType}</c:if>'); return false;">
								<ikep4j:message pre='${prefix}' key='layoutName' />
								<c:if test="${searchCondition.sortColumn eq 'LAYOUT_NAME'}">
									<c:if test="${searchCondition.sortType eq 'DESC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
									</c:if>
									<c:if test="${searchCondition.sortType eq 'ASC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
									</c:if>
								</c:if>
							</a>
						</th>
						<th scope="col" width="20%">
							<a href="#" onclick="sort('SYSTEM_NAME', '<c:if test="${searchCondition.sortColumn eq 'SYSTEM_NAME'}">${searchCondition.sortType}</c:if>'); return false;">
								<ikep4j:message pre='${prefix}' key='systemName' />
								<c:if test="${searchCondition.sortColumn eq 'SYSTEM_NAME'}">
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
							<a href="#" onclick="sort('COMMON', '<c:if test="${searchCondition.sortColumn eq 'COMMON'}">${searchCondition.sortType}</c:if>'); return false;">
								<ikep4j:message pre='${prefix}' key='isCommon' />
								<c:if test="${searchCondition.sortColumn eq 'COMMON'}">
									<c:if test="${searchCondition.sortType eq 'DESC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
									</c:if>
									<c:if test="${searchCondition.sortType eq 'ASC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
									</c:if>
								</c:if>
							</a>
						</th>
						<th scope="col" width="15%">
							<a href="#" onclick="sort('USER_NAME', '<c:if test="${searchCondition.sortColumn eq 'USER_NAME'}">${searchCondition.sortType}</c:if>'); return false;">
								<ikep4j:message pre='${prefix}' key='ownerName' />
								<c:if test="${searchCondition.sortColumn eq 'USER_NAME'}">
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
					<c:forEach var="page" items="${searchResult.entity}" varStatus="status">
						<tr>
							<td>
								${page.num}
							</td>
							<td>
								<a href="#" onclick="getView('${page.pageId}'); return false;">
									${page.pageName}
								</a>
							</td>
							<td>
								${page.layoutName}
							</td>
							<td>
								${page.systemName}
							</td>
							<td>
								<c:if test="${page.common == 1}">
									<ikep4j:message pre='${prefix}' key='yes' />
								</c:if>
								<c:if test="${page.common == 0}">
									<ikep4j:message pre='${prefix}' key='no' />
								</c:if>
							</td>
							<td>
								${page.ownerName}
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty searchResult.entity}">
						<tr>
							<td colspan="6">
								<ikep4j:message pre='${prefix}' key='noPage' />
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<!--Page Number Start--> 
			<spring:bind path="searchCondition.pageIndex">
				<ikep4j:pagination searchFormId="searchForm" ajaxEventFunctionName="getList" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${prefix}' key='${status.expression}'/>" />
			</spring:bind> 
			<!--//Page Number End-->
		</div>
		<!--//blockListTable End-->
					
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a class="button" href="<c:url value='/portal/admin/screen/page/createPageForm.do'/>"><span><ikep4j:message pre='${prefix}' key='addForm' /></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
</div>
<!--//splitterBox End-->
</form>