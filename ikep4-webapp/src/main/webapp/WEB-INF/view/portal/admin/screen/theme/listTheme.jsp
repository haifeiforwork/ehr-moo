<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preSearch" value="ui.portal.admin.screen.theme.listTheme.search"/>
<c:set var="preList" value="ui.portal.admin.screen.theme.listTheme.list"/>
<c:set var="preButton" value="ui.portal.admin.screen.theme.listTheme.button"/>

<script type="text/javascript">
//<![CDATA[
(function($) {
	
	$jq(document).ready(function() {
		
		//left menu setting
		$jq("#themeLinkOfLeft").click();
		
		$jq("a[name=searchButton]").click(function() {  
			getList();
		}); 
		
	});
	
	enterKey = function(event) {
		if (event.keyCode != 13) { 
			return;
		}
		
		getList();
	};
	
	getList = function() {		
		$jq("#searchForm").attr("action", "<c:url value='listTheme.do'/>");
		$jq("#searchForm")[0].submit();
	};
	
	addForm = function() {
		location.href='<c:url value="createThemeForm.do"/>';
	};
	
	sort = function (sortColumn, sortType) {
		
		$jq("input[name=sortColumn]").val(sortColumn);
		
		if(sortType == 'ASC') {
			
			$jq("input[name=sortType]").val('DESC');	
		} else {
			
			$jq("input[name=sortType]").val('ASC');
		}
		
		getList();
		
	};

})(jQuery);
//]]>
</script>

<!--blockListTable Start-->
<div class="blockListTable">

	<div id="listDiv">
		<form id="searchForm" name="searchForm" method="post" action="" onsubmit="return false;">
		
		<spring:bind path="searchCondition.sortColumn">
			<input name="${status.expression}" type="hidden" value="${status.value}"/>
		</spring:bind>
		<spring:bind path="searchCondition.sortType">
			<input name="${status.expression}" type="hidden" value="${status.value}"/>
		</spring:bind>
		
		<!--tableTop Start-->  
		<div class="tableTop">  
			<div class="tableTop_bgR"></div>
			<h2><ikep4j:message pre="${preList}" key="title" /></h2>
			<div class="listInfo">  
				<spring:bind path="searchCondition.pagePerRecord">  
					<select name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" onchange="getList(); return false;">
					<c:forEach var="code" items="${portalCode.pageNumList}">
						<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
					</c:forEach> 
					</select> 
				</spring:bind>
				<div class="totalNum">
					<ikep4j:message pre="${preSearch}" key="pageCount.info"/>
					<span>${searchResult.recordCount}</span>
				</div>
			</div>
			<div class="tableSearch"> 
				<spring:bind path="searchCondition.searchColumn">  
				<select name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />">
					<option value="themeName" <c:if test="${'themeName' eq status.value}">selected="selected"</c:if>><ikep4j:message pre="${preSearch}" key="${status.expression}" post="themeName"/></option>
				</select>		
				</spring:bind>		
				<spring:bind path="searchCondition.searchWord">  					
				<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" size="20"/>
				</spring:bind>
				<a href="#" id="searchButton" name="searchButton" class="ic_search">
					<span><ikep4j:message pre="${preSearch}" key="search" /></span>
				</a>
			</div>
			<div class="clear"></div>	
		</div>
		<!--//tableTop End-->	
		
		<table summary="<ikep4j:message pre='${preList}' key='title' />">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="10%">
						<ikep4j:message pre="${preList}" key="order" />
					</th>
					<th scope="col" width="40%">
						<a href="#" onclick="sort('THEME_NAME', '<c:if test="${searchCondition.sortColumn eq 'THEME_NAME'}">${searchCondition.sortType}</c:if>'); return false;">
							<ikep4j:message pre="${preList}" key="themeName" />
						</a>
					</th>
					<th scope="col" width="40%">
						<ikep4j:message pre="${preList}" key="description" />
					</th>
					<th scope="col" width="10%">
						<a href="#" onclick="sort('DEFAULT_OPTION', '<c:if test="${searchCondition.sortColumn eq 'DEFAULT_OPTION'}">${searchCondition.sortType}</c:if>'); return false;">
							<ikep4j:message pre="${preList}" key="default" />
						</a>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${searchResult.entity}" varStatus="i">
				<tr>
					<td class="textCenter">
						<c:out value="${i.count}"/>
					</td>
					<td class="textLeft">
						<a href="<c:url value='readTheme.do?themeId='/><c:out value='${item.themeId}'/>">
							<c:out value="${item.themeName}"/>
						</a>
					</td>
					<td class="textLeft">
						<a href="<c:url value='readTheme.do?themeId='/><c:out value='${item.themeId}'/>">
							<c:out value="${item.description}"/>
						</a>
					</td>
					<td class="textCenter">
						<c:if test="${item.defaultOption == 1}">
							<ikep4j:message pre="${preList}" key="yes" />
						</c:if>
						<c:if test="${item.defaultOption == 0}">
							<ikep4j:message pre="${preList}" key="no" />
						</c:if>
					</td>
				</tr>
				</c:forEach>
				
				<c:if test="${empty searchResult.entity}">
					<tr>
						<td colspan="4" class="textCenter">
							<ikep4j:message pre="${preSearch}" key="emptyRecord" />
						</td>
					</tr>
				</c:if>			
			</tbody>
		</table>
		
		<!--Page Number Start--> 
		<spring:bind path="searchCondition.pageIndex">
			<ikep4j:pagination searchFormId="searchForm" ajaxEventFunctionName="getList" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind> 
		<!--//Page Number End-->
		
	</form>
	
	<div class="blockBlank_10px"></div>
	
		<!--blockButton Start-->
		<div class="blockButton">
			<ul>
				<li>
					<a name="createButton" class="button" href="#" onclick="addForm(); return false;">
						<span><ikep4j:message pre="${preButton}" key="save" /></span>
					</a>
				</li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>

</div>
<!--//blockListTable End-->

