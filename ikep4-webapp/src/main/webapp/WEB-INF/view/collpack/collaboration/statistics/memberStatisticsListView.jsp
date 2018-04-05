<%--
=====================================================
	* 기능설명	:	Collaboration Type 별 목록(조직/TFT/Cop/Informal)
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.statistics.header" /> 
<c:set var="prePage"    value="message.collpack.collaboration.statistics.page" />
<c:set var="preList"    value="message.collpack.collaboration.statistics.list" />
<c:set var="preButton"  value="message.collpack.collaboration.statistics.button" /> 
<c:set var="preSearch"  value="message.collpack.collaboration.statistics.search" />
<c:set var="preMessage" value="message.collpack.collaboration.statistics.message.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script>
(function($) {
	$(document).ready(function() {
		
		
		$("#searchBtn").click(function() {  
			$("#statisticsForm").attr({
				action:"<c:url value='/collpack/collaboration/statistics/memberStatisticsListView.do'/>",
				method:'GET'
			}).submit();
			return false; 
		});
		
		
		$("#excelDown").click(function() {  
			$jq("#statisticsForm").attr({
				action:"<c:url value='/collpack/collaboration/statistics/excelDownMember.do'/>",
				method:'POST'
			}).submit();
			return false;
		});
	});
})(jQuery);
</script>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="memberTitle" /></h2>
</div>
<!--//pageTitle End-->

<!--blockSearch Start-->
<div class="blockSearch">
	<div class="corner_RoundBox03">
		<form id="statisticsForm" method="post" action="">
		<spring:bind path="searchCondition.workspaceId">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${prePage}' key='workspaceId'/>"/>
		</spring:bind>
		<spring:bind path="searchCondition.startPeriodFlag">
			<input name="${status.expression}" type="hidden" title="<ikep4j:message pre='${prePage}' key='startPeriodFlag'/>"/>
		</spring:bind>
		<spring:bind path="searchCondition.endPeriodFlag">
			<input name="${status.expression}" type="hidden" title="<ikep4j:message pre='${prePage}' key='endPeriodFlag'/>"/>
		</spring:bind> 		
		<table summary="">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="10%"><ikep4j:message pre="${preSearch}" key="memberSelect" /></th>
					<td scope="row" width="15%">
						<spring:bind path="searchCondition.memberFlag">			
						<select title="<ikep4j:message pre="${preSearch}" key="memberSelect" />" name="${status.expression}">
							<option value="A" <c:if test="${'A' eq status.value}">selected="selected"</c:if>><ikep4j:message pre="${preSearch}" key="associateMember" /></option>
							<option value="M" <c:if test="${'M' eq status.value}">selected="selected"</c:if>><ikep4j:message pre="${preSearch}" key="regularMember" /></option>
						</select>&nbsp;
						</spring:bind>
					</td>
					<c:if test="${!empty workspace.groupName && workspace.isOrganization == 1}">
					<th scope="row" width="10%"><ikep4j:message pre="${preSearch}" key="groupLimit" /></th>
					<td scope="row" width="65%">
						<spring:bind path="searchCondition.groupValue">	
						<select title="<ikep4j:message pre="${preSearch}" key="groupLimit" />" name="${status.expression}">
							<option value="A" <c:if test="${'A' eq status.value}">selected="selected"</c:if>><ikep4j:message pre="${preSearch}" key="allMember"/></option>
							<option value="W" <c:if test="${'W' eq status.value}">selected="selected"</c:if>>${workspace.groupName} <ikep4j:message pre="${preSearch}" key="only"/></option>
						</select>&nbsp;
						</spring:bind>
						<div class="subInfo">
							<spring:bind path="searchCondition.startYear">	
							<select name="${status.expression}">
								<c:forEach var="y_start" begin="2011" end="2099">
									<option value="${y_start}" <c:if test="${y_start eq status.value}">selected</c:if>>${y_start}</option>
								</c:forEach>
							</select> <ikep4j:message pre="${preSearch}" key="year"/>
							</spring:bind>
							<spring:bind path="searchCondition.startMonth">	
							<select name="${status.expression}">
								<c:forEach var="m_start" begin="1" end="12">
									<option value="${m_start}" <c:if test="${m_start eq status.value}">selected</c:if>>${m_start}</option>
								</c:forEach>
							</select> <ikep4j:message pre="${preSearch}" key="month"/>
							</spring:bind>
							~
							<spring:bind path="searchCondition.endYear">	
							<select name="${status.expression}">
								<c:forEach var="y_end" begin="2011" end="2099">
									<option value="${y_end}" <c:if test="${y_end eq status.value}">selected</c:if>>${y_end}</option>
								</c:forEach>
							</select> <ikep4j:message pre="${preSearch}" key="year"/>
							</spring:bind>
							<spring:bind path="searchCondition.endMonth">	
							<select name="${status.expression}">
								<c:forEach var="m_end" begin="1" end="12">
									<option value="${m_end}" <c:if test="${m_end eq status.value}">selected</c:if>>${m_end}</option>
								</c:forEach>
							</select> <ikep4j:message pre="${preSearch}" key="month"/>
							</spring:bind>
						</div>
					</td>
					</c:if>
					<c:if test="${empty workspace.groupName && workspace.isOrganization != 1}">
						<spring:bind path="searchCondition.groupValue">
							<input name="${status.expression}" type="hidden" value="A"/>
						</spring:bind>
					<td colspan="2" scope="row" width="75%">
						<div class="subInfo">
							<spring:bind path="searchCondition.startYear">	
							<select name="${status.expression}">
								<c:forEach var="y_start" begin="2011" end="2099">
									<option value="${y_start}" <c:if test="${y_start eq status.value}">selected</c:if>>${y_start}</option>
								</c:forEach>
							</select> <ikep4j:message pre="${preSearch}" key="year"/>
							</spring:bind>
							<spring:bind path="searchCondition.startMonth">	
							<select name="${status.expression}">
								<c:forEach var="m_start" begin="1" end="12">
									<option value="${m_start}" <c:if test="${m_start eq status.value}">selected</c:if>>${m_start}</option>
								</c:forEach>
							</select> <ikep4j:message pre="${preSearch}" key="month"/>
							</spring:bind>
							~
							<spring:bind path="searchCondition.endYear">	
							<select name="${status.expression}">
								<c:forEach var="y_end" begin="2011" end="2099">
									<option value="${y_end}" <c:if test="${y_end eq status.value}">selected</c:if>>${y_end}</option>
								</c:forEach>
							</select> <ikep4j:message pre="${preSearch}" key="year"/>
							</spring:bind>
							<spring:bind path="searchCondition.endMonth">	
							<select name="${status.expression}">
								<c:forEach var="m_end" begin="1" end="12">
									<option value="${m_end}" <c:if test="${m_end eq status.value}">selected</c:if>>${m_end}</option>
								</c:forEach>
							</select> <ikep4j:message pre="${preSearch}" key="month"/>
							</spring:bind>
						</div>
					</td>
					</c:if>
				</tr>
			</tbody>
		</table>
		<div class="searchBtn"><a id="searchBtn" href="#a"><span>Search</span></a></div>
		</form>
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
		<div class="l_b_corner"></div>
		<div class="r_b_corner"></div>				
	</div>
</div>	
<!--//blockSearch End-->

<!--tableTop Start-->
<div class="tableTop">		
	<div class="tableSearch">
		<a href="#a" id="excelDown" class="ic_icon"><img src="<c:url value='/base/images/icon/ic_xls.gif'/>" alt="<ikep4j:message pre="${preSearch}" key="excelDownload"/>" /></a>
	</div>			
	<div class="clear"></div>
</div>
<!--//tableTop End-->

<!--blockDetail Start-->					
<div class="blockDetail">
	<table summary="">
		<caption></caption>
		<thead>
			<tr>
				<th rowspan="2" width="5%" class="textCenter" scope="col"><ikep4j:message pre="${preList}" key="no"/></th>
				<th rowspan="2" width="8%" class="textCenter" scope="col"><ikep4j:message pre="${preList}" key="name"/></th>
				<th rowspan="2" width="6%" class="textCenter" scope="col"><ikep4j:message pre="${preList}" key="position"/></th>
				<th rowspan="2" width="8%" class="textCenter" scope="col"><ikep4j:message pre="${preList}" key="role"/></th>
				<th rowspan="2" width="14%" class="textCenter" scope="col"><ikep4j:message pre="${preList}" key="group"/></th>
				<th colspan="2" width="14%" class="textCenter table_color01" scope="col"><ikep4j:message pre="${preList}" key="knowledgeCreate"/></th>
				<th colspan="4" width="29%" class="textCenter table_color02" scope="col"><ikep4j:message pre="${preList}" key="knowledgeFeedBack"/></th>
				<%--th colspan="2" width="16%" class="textCenter table_color04" scope="col"><ikep4j:message pre="${preList}" key="valueIndex"/></th--%>
			</tr>
			<tr>
				<th scope="col" class="textCenter table_color01"><ikep4j:message pre="${preList}" key="docCount"/></th>
				<th scope="col" class="textCenter table_color01"><ikep4j:message pre="${preList}" key="weeklyCount"/></th>
				<th scope="col" class="textCenter table_color02"><ikep4j:message pre="${preList}" key="hitCount"/></th>
				<th scope="col" class="textCenter table_color02"><ikep4j:message pre="${preList}" key="recommendCount"/></th>
				<th scope="col" class="textCenter table_color02"><ikep4j:message pre="${preList}" key="lineReplyCount"/></th>
				<th scope="col" class="textCenter table_color02"><ikep4j:message pre="${preList}" key="favoriteCount"/></th>
				<%--th scope="col" class="textCenter table_color04"><ikep4j:message pre="${preList}" key="pvi"/></th>
				<th scope="col" class="textCenter table_color04"><ikep4j:message pre="${preList}" key="cvi"/></th--%>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${searchResult.entity}" varStatus="status">
			<tr>
				<td class="textCenter">${status.count}</td>
				<td class="textCenter">
					<a href="#a" onclick="iKEP.showUserContextMenu(this, '${item.memberId}', 'bottom');iKEP.iFrameContentResize(); return false;">
					<c:choose>
					<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						${item.userName}
					</c:when>
					<c:otherwise>
						${item.userEnglishName}
					</c:otherwise>
					</c:choose>
					</a>				
				</td>
				<td class="textCenter">
					<c:choose>
					<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						${item.jobTitleName}
					</c:when>
					<c:otherwise>
						${item.jobTitleEnglishName}
					</c:otherwise>
					</c:choose>					
				</td>
				<td class="textCenter">
						<c:choose>
							<c:when test="${item.memberLevel eq 1}"><ikep4j:message pre="${preList}" key="sysop"/></c:when>
							<c:when test="${item.memberLevel eq 2}"><ikep4j:message pre="${preList}" key="manager"/></c:when>
							<c:when test="${item.memberLevel eq 3}"><ikep4j:message pre="${preList}" key="regularMember"/></c:when>
							<c:when test="${item.memberLevel eq 4}"><ikep4j:message pre="${preList}" key="associateMember"/></c:when>
							<c:otherwise>-</c:otherwise>
						</c:choose>
				</td>
				<td class="textCenter">
					<c:choose>
					<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						${item.teamName}
					</c:when>
					<c:otherwise>
						${item.teamEnglishName}
					</c:otherwise>
					</c:choose>					
				</td>
				<td class="textCenter">
					<c:choose>
						<c:when test="${empty item.docCount}">0</c:when>
						<c:when test="${!empty item.docCount}">${item.docCount}</c:when>
					</c:choose>
				</td>
				<td class="textCenter">
					<c:choose>
						<c:when test="${empty item.weeklyCount}">0</c:when>
						<c:when test="${!empty item.weeklyCount}">${item.weeklyCount}</c:when>
					</c:choose>
				</td>
				<td class="textCenter">
					<c:choose>
						<c:when test="${empty item.hitCount}">0</c:when>
						<c:when test="${!empty item.hitCount}">${item.hitCount}</c:when>
					</c:choose>
				</td>
				<td class="textCenter">
					<c:choose>
						<c:when test="${empty item.recommendCount}">0</c:when>
						<c:when test="${!empty item.recommendCount}">${item.recommendCount}</c:when>
					</c:choose>
				</td>
				<td class="textCenter">
					<c:choose>
						<c:when test="${empty item.lineReplyCount}">0</c:when>
						<c:when test="${!empty item.lineReplyCount}">${item.lineReplyCount}</c:when>
					</c:choose>
				</td>
				<td class="textCenter">
					<c:choose>
						<c:when test="${empty item.favoriteCount}">0</c:when>
						<c:when test="${!empty item.favoriteCount}">${item.favoriteCount}</c:when>
					</c:choose>
				</td>
				<%--td class="textCenter">
					<c:choose>
						<c:when test="${empty item.pvi}">0</c:when>
						<c:when test="${!empty item.pvi}">${item.pvi}</c:when>
					</c:choose>
				</td>
				<td class="textCenter">
					<c:choose>
						<c:when test="${empty item.cvi}">0</c:when>
						<c:when test="${!empty item.cvi}">${item.cvi}</c:when>
					</c:choose>
				</td--%>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<!--//blockDetail End-->