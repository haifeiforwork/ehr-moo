<%--
=====================================================
* 기능 설명 : Workflow Workplace 업무목록 업무시작
* 작성자 : 이재경
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="ui.workflow.workplace.worklist" />
<c:set var="prefixBtn"  value="ui.workflow.workplace.worklist.button" />
<c:set var="prefixCommon"  value="ui.workflow.workplace.common" />
<c:set var="prefixCommonBtn"  value="ui.workflow.workplace.common.button" />
 <%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
	<!--
	(function($){
		
		/**
		 * onload시 수행할 코드
		 */
		$(document).ready(function(){

			$("#searchStartWorkListButton").click(function() {   
				$("#pageIndex").val("1");
				$("#myForm").submit(); 
				return false; 
			});  
			
			$("#searchkeyword").keypress(function(event){
	            if(event.which == 13) {
	            	$("#searchStartWorkListButton").click();
	            }
	        });
			
			$jq("select[name=pagePerRecord]").change(function() {
	            //검색용 폼을 서브밋하는 코드 넣으시면 됩니다. 
	            $jq("#searchStartWorkListButton").click();  
	        });  
		});
		
	})(jQuery);
	
	function popupWorkform(processId, processVer) {
		var top; 
		var left; 
		var width = 820;
		var height = 600;

		left = (screen.width - width) / 2; 
		top = (screen.height - height) / 2;		
		var contextUrl	= "<c:url value='/workflow/workform.do'/>" + "?processId=" + processId + "&processVer=" + processVer + "&type=start";
		window.open(contextUrl, "", "top=" + top + ", left=" + left +", width="+ width + ", height="+height+", resizable=yes,scrollbars=yes");
	}	
	
	function mouseOver(obj){obj.className = "bgSelected";}
	function mouseOut(obj){obj.className = "";}
	//-->
</script>

<!--mainContents Start-->
<h1 class="none">컨텐츠영역</h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${prefix}' key='startworklist'/></h2>
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre='${prefix}' key='workplace'/></li>
			<li><ikep4j:message pre='${prefix}' key='todolist'/></li>
			<li class="liLast"><ikep4j:message pre='${prefix}' key='startworklist'/></li>
		</ul>
	</div>
</div>
<!--//pageTitle End-->

<form id="myForm" method="post" action="<c:url value='/workflow/workplace/worklist/startWorkList.do' />">
	<!--blockSearch Start-->
	<div class="blockSearch">
		<div class="corner_RoundBox03">
			<table summary="To-Do 검색">
				<caption></caption>
				<tbody>
					<tr>
						<spring:bind path="workplaceSearchCondition.partition">
						<th scope="row" width="5%"><ikep4j:message pre='${prefix}' key='partition'/></th>
						<td width="45%">		
							<select title="<ikep4j:message pre='${prefix}' key='partition'/>" name="${status.expression}">
								<option value=""><ikep4j:message pre="${prefixCommon}" key="all"/></option>
								<c:forEach var="code" items="${partitionClass}">
									<option value="${code.partitionId}" <c:if test="${code.partitionId eq status.value}">selected="selected"</c:if>>${code.partitionName}</option>
								</c:forEach> 
							</select>
						</td>
						</spring:bind>
					</tr>
					<tr>
						<spring:bind path="workplaceSearchCondition.searchcondition">
						<th scope="row" width="5%"><ikep4j:message pre='${prefixCommon}' key='searchcondition'/></th>
						<td width="45%">		
							<select title="<ikep4j:message pre='${prefixCommon}' key='searchcondition'/>" name="${status.expression}">
								<c:forEach var="code" items="${workplaceCode.procSearchClassList}">
									<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>><spring:message code="${code.value}"/></option>
								</c:forEach> 
							</select>															
						</td>
						</spring:bind>
						<spring:bind path="workplaceSearchCondition.searchkeyword">
						<th scope="row"><ikep4j:message pre='${prefixCommon}' key='searchkeyword'/></th>
						<td colspan="3">								
							<input type="text" class="inputbox" title="<ikep4j:message pre='${prefixCommon}' key='searchkeyword'/>" id="${status.expression}" name="${status.expression}" value="${status.value}" size="35" />
						</td>		
						</spring:bind>		
					</tr>								
				</tbody>
			</table>
			<div class="searchBtn">
				<a id="searchStartWorkListButton" name="searchStartWorkListButton" href="">
					<img src="<c:url value="/base/images/theme/theme01/basic/btn_search.gif"/>" alt="<ikep4j:message pre='${prefixCommonBtn}' key='search'/>" />
				</a>
			</div>
			<div class="l_t_corner"></div>
			<div class="r_t_corner"></div>
			<div class="l_b_corner"></div>
			<div class="r_b_corner"></div>				
		</div>
	</div>	
	<!--//blockSearch End-->

	<!--blockListTable Start-->
	<div class="blockListTable">
		
		<!--tableTop Start-->
		<div class="tableTop">
			<div class="listInfo">
				<spring:bind path="workplaceSearchCondition.pagePerRecord">
				<select name="${status.expression}" title='<ikep4j:message pre='${prefixCommon}' key='${status.expression}' />'>
					<c:forEach var="code" items="${workplaceCode.pageNumList}">
						<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
					</c:forEach> 
				</select> 
				</spring:bind>
				<div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${prefixCommon}' key='page' /> ( <ikep4j:message pre='${prefixCommon}' key='pageAll' /><span> ${searchResult.recordCount}</span>)</div>
			</div>			
			<div class="clear"></div>
		</div>
		<!--//tableTop End-->
	
		<table summary="My To-Do 목록">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="5%"><ikep4j:message pre='${prefix}' key='number' /></th>
					<th scope="col" width="10%"><ikep4j:message pre='${prefix}' key='partition' /></th>
					<th scope="col" width="*"><ikep4j:message pre='${prefix}' key='process' /></th>
					<th scope="col" width="10%"><ikep4j:message pre='${prefix}' key='activity' /></th>
					<th scope="col" width="10%"><ikep4j:message pre='${prefix}' key='authorname' /></th>
					<th scope="col" width="10%"><ikep4j:message pre='${prefix}' key='processCreateDate' /></th>
					<th scope="col" width="10%"></th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty searchResult.entity}">
						<c:forEach var="startProcList" items="${searchResult.entity}" varStatus="status">
						<tr onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
							<td>${(searchResult.recordCount-((searchResult.pageIndex-1)*workplaceSearchCondition.pagePerRecord)-status.count)+1}</td>
							<td>${startProcList.partitionName}</td>					
							<td class="textLeft ellipsis">${startProcList.processName}</td>
							<td>${startProcList.activityName}</td> 
							<td> 
								<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}">
		  								${startProcList.authorName}
	  								</c:when>
	  								<c:otherwise>
	  									${startProcList.userEnglishName}
	  								</c:otherwise>
	  							</c:choose>
							</td>
							<td><ikep4j:timezone date="${startProcList.createDate}" pattern="yyyy.MM.dd HH:mm"/></td> 
							<td>
								<a class="button_s" href="javascript:popupWorkform('${startProcList.processId}', '${startProcList.processVer}');">
								<span><ikep4j:message pre='${prefixBtn}' key='startproc' /></span></a>
							</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="6" class="emptyRecord"><ikep4j:message pre='${prefixCommon}' key='emptyRecord' /></td>
						</tr>
					</c:otherwise>	
				</c:choose>		
			</tbody>
		</table>
		
		<!--Page Numbur Start--> 
		<spring:bind path="workplaceSearchCondition.pageIndex">
		<ikep4j:pagination searchFormId="myForm" pageIndexInput="${status.expression}" searchCondition="${workplaceSearchCondition}" />
		<input id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${prefixCommon}' key='${status.expression}'/>" />
		</spring:bind> 
		<!--//Page Numbur End--> 
	</div>
	<!--//blockListTable End-->	
</form>
<!--//mainContents End-->