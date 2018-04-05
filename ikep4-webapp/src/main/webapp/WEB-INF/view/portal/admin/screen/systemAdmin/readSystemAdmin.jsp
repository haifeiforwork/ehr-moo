<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="prefix" value="message.portal.admin.screen.systemAdmin.readSystemAdmin"/>

<script type="text/javascript">
//<!--
(function($) {
	$jq(document).ready(function() {
		
		//left menu setting
		$jq("#systemAdminLinkOfLeft").click();
		
		//검색 관련 이벤트
	    $jq('#searchWord').keyup( function(event) {
	    	if (event.which == '13' || event.which === undefined) {
	    		readView();
	    	} 
		});
		
	  	//className,resourceName,operationName,"권한설정 옵션"
		iKEP.readSecurity("${searchCondition.resourceName}","${searchCondition.resourceName}","MANAGE","User,Group,ExpUser", 25);

		
		<c:if test="${saveFlag == 'Y'}">
			alert('<ikep4j:message pre="${prefix}" key="saveMessage" />');
		</c:if>
	});
	
 	// 목록 정렬
	sort = function (sortColumn, sortType) {
		$jq("input[name=sortColumn]").val(sortColumn);
		
		if(sortType == 'ASC') {
			$jq("input[name=sortType]").val('DESC');	
		} else {
			$jq("input[name=sortType]").val('ASC');
		}
		
		readView();
	};
	
	//조회
	readItemView = function (resourceName) {
		$jq("input[name=resourceName]").val(resourceName);
		
	    $jq("#searchForm")[0].submit();
	};
	
	//페이징에서 조회
	readView = function () {
		$jq("input[name=resourceName]").val("${searchCondition.resourceName}");
		
		$jq("#searchForm")[0].submit();
	};
	
	updateForm = function () {
		$jq("input[name=resourceName]").val("${searchCondition.resourceName}");
		
		$jq("#searchForm").attr("action", "<c:url value='/portal/admin/screen/systemAdmin/updateSystemAdminForm.do'/>");
		$jq("#searchForm")[0].submit();
	}
})(jQuery);
//-->
</script>

<h1 class="none"><ikep4j:message pre="${prefix}" key="noneTitle" /></h1>

<!--pageTitle Start-->
<!--  div id="pageTitle">
	<h2><ikep4j:message pre="${prefix}" key="topTitle" /></h2>
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${prefix}" key="home" /></li>
			<li><ikep4j:message pre="${prefix}" key="1depth" /></li>
			<li><ikep4j:message pre="${prefix}" key="2depth" /></li>
			<li class="liLast"><ikep4j:message pre="${prefix}" key="lastDepth" /></li>
		</ul>
	</div>
</div-->

<form id="searchForm" method="post" action="<c:url value='/portal/admin/screen/systemAdmin/readSystemAdmin.do'/>" onsubmit="return false;">
<input type="hidden" name="resourceName" />
<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}"/>
</spring:bind>
<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}"/>
</spring:bind>

<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2><ikep4j:message pre="${prefix}" key="topTitle" /></h2>
	<div class="listInfo">
		<spring:bind path="searchCondition.pagePerRecord">  
			<select name="${status.expression}" title="<ikep4j:message pre="${prefix}" key="${status.expression}" />" onchange="readView();">
			<c:forEach var="code" items="${portalCode.pageNumList}">
				<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
			</c:forEach> 
			</select> 
		</spring:bind>
		<div class="totalNum">
			${searchResult.pageIndex}/${searchResult.pageCount}<ikep4j:message pre="${prefix}" key="page" /> (<ikep4j:message pre="${prefix}" key="all" /><span>${searchResult.recordCount}</span>)
		</div>
	</div>					
	<div class="tableSearch">
		<spring:bind path="searchCondition.searchColumn">  
			<select name="${status.expression}" title="<ikep4j:message pre="${prefix}" key="${status.expression}" />">
				<option value="SYSTEM_NAME"><ikep4j:message pre="${prefix}" key="systemName" /></option>
			</select>		
		</spring:bind>		
		<spring:bind path="searchCondition.searchWord">  					
			<input name="${status.expression}" id="${status.expression}" value="${status.value}" type="text" class="inputbox" title="<ikep4j:message pre="${prefix}" key="${status.expression}" />" size="20"/>
		</spring:bind>
		<a class="ic_search" id="searchBoardItemButton" name="searchBoardItemButton" href="#a" onclick="readView();">
			<span><ikep4j:message pre="${prefix}" key="search" /></span>
		</a>
	</div>	
	<div class="clear"></div>
</div>
<!--//tableTop End-->

<!--splitterBox Start-->
<div id="splitterBox">
	<div>
		<!--blockListTable Start-->
		<div class="blockListTable">					
			<table summary="<ikep4j:message pre="${prefix}" key="topTitle" />">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="10%"><ikep4j:message pre="${prefix}" key="num" /></th>
						<th scope="col" width="90%">
							<a href="#a" onclick="sort('SYSTEM_NAME', '<c:if test="${searchCondition.sortColumn eq 'SYSTEM_NAME'}">${searchCondition.sortType}</c:if>');">
								<ikep4j:message pre="${prefix}" key="systemName" />
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
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${searchResult.entity}" varStatus="status">
						<tr <c:if test="${searchCondition.resourceName == item.resourceName }">class="bgSelected"</c:if>>
							<td>
								${item.num}
							</td>
							<td>
								<a href="#a" onclick="readItemView('${item.resourceName}');">
									${item.resourceName}
								</a>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty searchResult.entity}">
						<tr>
							<td colspan="2">
								<ikep4j:message pre="${prefix}" key="noData" />
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<!--Page Number Start--> 
			<spring:bind path="searchCondition.pageIndex">
				<ikep4j:pagination searchFormId="searchForm" ajaxEventFunctionName="readView" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre="${prefix}" key="${status.expression}" />" />
			</spring:bind> 
			<!--//Page Number End-->
		</div>
		<!--//blockListTable End-->
	</div>
</div>
<!--//splitterBox End-->

<br/>
<div style="position:relative; margin-bottom:5px;">
	<h2 style="*font-family:'돋움', 'Dotum', Tahoma;	 font-size:1.2em; color:#111;"><ikep4j:message pre="${prefix}" key="bottomTitle" /></h2>
</div>
<div class="blockDetail">
	<table summary="<ikep4j:message pre="${prefix}" key="bottomTitle" />">
		<caption></caption>
		<colgroup>
			<col width="18%"/>
			<col width="82%"/>
		</colgroup>
		<tbody>
			<tr>
				<th scope="row"><ikep4j:message pre="${prefix}" key="systemName" /></th>
				<td>
					${searchCondition.resourceName}
				</td>
			</tr>
		</tbody>
	</table>
	<div id="securityArea"></div>
</div>
<!--//blockDetail End-->

<!--tableBottom Start-->
<div class="tableBottom">										
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a class="button" href="#a" onclick="updateForm();"><span><ikep4j:message pre="${prefix}" key="modifyForm" /></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
</div>
<!--//tableBottom End-->
</form>