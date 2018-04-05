<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="prefix" value="message.portal.main.listUser"/>
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />

<script type="text/javascript">
//<!--
(function($) {
	$jq(document).ready(function() { 
		
		$jq("select[name=pagePerRecord]").change(function() {
			getList();
        });  
		
		$jq('#searchWord').keypress(function(event) { 
			if(event.which == '13') {
				getList();
			}
		});
		
		if(parent.createGroupTree) {
			parent.createGroupTree('${searchUserId}');
		}
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
	
	getProfile = function(userId) {
		location.href = "<c:url value='/support/profile/getProfile.do'/>?targetUserId=" + userId;
		//parent.location.href = "<c:url value='/portal/main/listUserMain.do'/>?rightFrameUrl=/support/profile/getProfile.do?targetUserId=" + userId;
	}
})(jQuery);
//-->
</script>

<h1 class="none"><ikep4j:message pre="${prefix}" key="pageTitle" /></h1>

<!--pageTitle Start-->
<!--  div id="pageTitle">
	<h2><ikep4j:message pre='${prefix}' key='pageTitle'/></h2>
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre='${prefix}' key='home'/></li>
			<li class="liLast"><ikep4j:message pre='${prefix}' key='lastDepth'/></li>
		</ul>
	</div>
</div-->
<!--//pageTitle End-->

<form id="searchForm" method="post" action="<c:url value='/portal/main/listUser.do'/>" onsubmit="return false;">
	<input name="groupId" type="hidden" value="${searchCondition.groupId}"/>
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
				<option value="title" <c:if test="${'title' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${prefix}' key='userName' post=''/></option>
				<option value="id" <c:if test="${'id' eq status.value}">selected="selected"</c:if> >아이디</option>
				<option value="team" <c:if test="${'team' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${prefix}' key='teamName' post=''/></option>
			</select>	
		</spring:bind>		
		<spring:bind path="searchCondition.searchWord">  					
			<input name="${status.expression}" id="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
		</spring:bind>
		<a class="ic_search" id="searchBoardItemButton" name="searchBoardItemButton" href="#a" onclick="javascript:getList();"><span><ikep4j:message pre='${prefix}' key='search' /></span></a> 
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
						<th scope="col" width="5%">
							&nbsp;
						</th>
						<th scope="col" width="20%">
							<a onclick="javascript:sort('USER_NAME', '<c:if test="${searchCondition.sortColumn eq 'USER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='userName' />
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
					
						<th scope="col" width="20%">
							<a onclick="javascript:sort('TEAM_NAME', '<c:if test="${searchCondition.sortColumn eq 'TEAM_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='teamName' />
								<c:if test="${searchCondition.sortColumn eq 'TEAM_NAME'}">
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
							<a onclick="javascript:sort('JOB_TITLE_NAME', '<c:if test="${searchCondition.sortColumn eq 'JOB_TITLE_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='jobTitleName' />
								<c:if test="${searchCondition.sortColumn eq 'JOB_TITLE_NAME'}">
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
							<a onclick="javascript:sort('MAIL', '<c:if test="${searchCondition.sortColumn eq 'MAIL'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='mail' />
								<c:if test="${searchCondition.sortColumn eq 'MAIL'}">
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
							<a onclick="javascript:sort('MOBILE', '<c:if test="${searchCondition.sortColumn eq 'MOBILE'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='mobile' />
								<c:if test="${searchCondition.sortColumn eq 'MOBILE'}">
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
									<td colspan="6" class="emptyRecord"><ikep4j:message pre='${prefix}' key='list.empty' /></td> 
								</tr>				        
						    </c:when>
						    <c:otherwise>
								<c:forEach var="user" items="${searchResult.entity}" varStatus="status">
								<tr>
									<td class="textRight">
										<c:if test="${user.leader == 1}">
											<img src="<c:url value='/base/images/icon/people_t.png' />" title="<ikep4j:message pre='${prefix}' key='leader' />" alt="<ikep4j:message pre='${prefix}' key='leader' />" />
										</c:if>
									</td>
									<td class="textLeft">
										<a href="#a" onclick="javascript: getProfile('${user.userId}');">
											${user.userName}
										</a>
									</td>
									<td>${user.teamName}</td>
									<td>${user.jobTitleName}</td>			
									<td class="textLeft">
										${user.mail}
									</td>
									<td>${user.mobile}</td>
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
	</div>
</div>
<!--//splitterBox End-->
</form>