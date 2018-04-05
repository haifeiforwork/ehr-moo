<%@ page language="java" errorPage="/base/common/error.jsp"	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.collpack.collaboration.findCollaboration" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>



		
		<!--tableTop Start-->
		<div class="tableTop">
			<div class="tableTop_bgR"></div>
			<h2><span>'${name}'</span>&nbsp;<ikep4j:message pre="${prefix}" key="search.result" /></h2>
			<div class="listInfo">  
				<spring:bind path="searchCondition.pagePerRecord">  
					<select name="${status.expression}" title='<ikep4j:message pre='${prefix}' key='search.pagePerRecord' />' onchange="selectChange();">
					<c:forEach var="code" items="${workspaceCode.pageNumList}">
						<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
					</c:forEach> 
					</select> 
				</spring:bind>
				<div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${prefix}' key='search.page' /> ( <ikep4j:message pre='${prefix}' key='search.pageCount.info' /><span> ${searchResult.recordCount}</span>)</div>
			</div>

			<div class="clear"></div>
			
			<div class="cafe_sort">
				<span class="cafe_sort_smenu">
					<span <c:if test="${searchCondition.sortColumn eq 'workspaceName'}">class="current"</c:if>><a href="#a" onclick="javascript:findCollaborationBySortColumn('workspaceName','<c:if test="${searchCondition.sortColumn eq 'workspaceName'}">${searchCondition.sortType}</c:if>');">
						<ikep4j:message pre="${prefix}" key="list.workspaceName" /> 
						<c:choose>
						<c:when test="${searchCondition.sortColumn eq 'workspaceName' and searchCondition.sortType eq 'DESC'}"><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_down_on.gif'/>"></c:when>
						<c:when test="${searchCondition.sortColumn eq 'workspaceName' and searchCondition.sortType eq 'ASC'}"><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_up_on.gif'/>"></c:when> 
						<c:otherwise><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_down.gif'/>"></c:otherwise> 
						</c:choose> 						
					</a></span>
					<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
					<span <c:if test="${searchCondition.sortColumn eq 'memberCount'}">class="current"</c:if>><a href="#a" onclick="javascript:findCollaborationBySortColumn('memberCount','<c:if test="${searchCondition.sortColumn eq 'memberCount'}">${searchCondition.sortType}</c:if>');">
						<ikep4j:message pre="${prefix}" key="list.memberCount" /> 
						<c:choose>
						<c:when test="${searchCondition.sortColumn eq 'memberCount' and searchCondition.sortType eq 'DESC'}"><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_down_on.gif'/>"></c:when>
						<c:when test="${searchCondition.sortColumn eq 'memberCount' and searchCondition.sortType eq 'ASC'}"><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_up_on.gif'/>"></c:when> 
						<c:otherwise><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_down.gif'/>"></c:otherwise> 
						</c:choose> 
					</a></span>
					<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
					<span <c:if test="${searchCondition.sortColumn eq 'openDate'}">class="current"</c:if>><a href="#a" onclick="javascript:findCollaborationBySortColumn('openDate','<c:if test="${searchCondition.sortColumn eq 'openDate'}">${searchCondition.sortType}</c:if>');">
						<ikep4j:message pre="${prefix}" key="list.registDate" />
						<c:choose>
						<c:when test="${searchCondition.sortColumn eq 'openDate' and searchCondition.sortType eq 'DESC'}"><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_down_on.gif'/>"></c:when>
						<c:when test="${searchCondition.sortColumn eq 'openDate' and searchCondition.sortType eq 'ASC'}"><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_up_on.gif'/>"></c:when> 
						<c:otherwise><img alt="" src="<c:url value='/base/images/icon/ic_cafesort_down.gif'/>"></c:otherwise> 
						</c:choose> 
					</a></span>
				</span>					
			</div>			
		</div>
		<!--//tableTop End-->	

		<!--blockListTable Start-->
		<div class="corporateView TeamColl_summaryView">
			<table summary="<ikep4j:message pre='${prefix}' key='list'/>">
				<caption></caption>						
				<tbody>
					<c:forEach var="workspaceList" items="${workspaceList}"  varStatus="status">
					<tr>
						<td>
							<div class="TeamColl_summaryViewTitle">
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									<span class="cate_block_1"><span class="cate_tit_1">${workspaceList.typeName}</span></span>
								</c:when>
								<c:otherwise>
									<span class="cate_block_1"><span class="cate_tit_1">${workspaceList.typeEnglishName}</span></span>
								</c:otherwise>
								</c:choose>		
								<span><a href="<c:url value="/collpack/collaboration/main/Workspace.do"/>?workspaceId=${workspaceList.workspaceId}">${workspaceList.workspaceName}</a></span>
							</div>
							<span class="corporateViewInfo">
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									<span class="corporateViewInfo_name"><a href="#a"  onclick="iKEP.showUserContextMenu(this, '${workspaceList.sysopId}', 'bottom');iKEP.iFrameContentResize(); return false;"><ikep4j:message pre='${prefix}' key='list.sysopName'/> : ${workspaceList.sysopName}</a></span>
									<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
									<span><ikep4j:message pre='${prefix}' key='list.registDate'/> :<ikep4j:timezone pattern="yyyy.MM.dd" date="${workspaceList.openDate}"/></span>
									<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
									<span><ikep4j:message pre='${prefix}' key='list.memberCount'/> : ${workspaceList.memberCount}</span>
									<c:if test="${!empty workspaceList.groupName}">
									<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
									<span><ikep4j:message pre='${prefix}' key='list.teamName'/> : ${workspaceList.groupName}</span>
									</c:if>
								</c:when>
								<c:otherwise>
									<span class="corporateViewInfo_name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${workspaceList.sysopId}', 'bottom');iKEP.iFrameContentResize(); return false;"><ikep4j:message pre='${prefix}' key='list.sysopName'/> : ${workspaceList.sysopEnglishName}</a></span>
									<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
									<span><ikep4j:message pre='${prefix}' key='list.registDate'/> :<ikep4j:timezone pattern="yyyy.MM.dd" date="${workspaceList.openDate}"/></span>
									<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
									<span><ikep4j:message pre='${prefix}' key='list.memberCount'/> : ${workspaceList.memberCount}</span>
									<c:if test="${!empty workspaceList.groupEnglishName}">
									<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
									<span><ikep4j:message pre='${prefix}' key='list.teamName'/> : ${workspaceList.groupEnglishName}</span>
									</c:if>
								</c:otherwise>
								</c:choose>

							</span>
							<div class="corporateViewCon">${workspaceList.description}</div>						

							<c:if test="${not empty workspaceList.tagList}">
							<div class="corporateViewTag">
								<span class="ic_tag"><span><ikep4j:message pre='${prefix}' key='tag' /></span></span>
								<!--tagList--> 
								<c:forEach var="tag" items="${workspaceList.tagList}" varStatus="tagLoop">
								<c:if test="${tagLoop.count != 1}">, </c:if>
								<a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a>
								</c:forEach>
							</div>
							</c:if>							
							
						</td>
					</tr>
					</c:forEach>
																																														
				</tbody>
			</table>

			<!--Page Numbur Start--> 
			<spring:bind path="searchCondition.pageIndex">
				<ikep4j:pagination searchFormId="mainForm" ajaxEventFunctionName="findCollaboration" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind> 
			<!--//Page Numbur End--> 			
		</div>			
 
		