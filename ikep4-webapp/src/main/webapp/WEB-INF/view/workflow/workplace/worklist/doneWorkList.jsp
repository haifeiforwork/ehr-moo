<%--
=====================================================
* 기능 설명 : Workflow Workplace 업무목록 완료된 업무목록
* 작성자 : 이재경
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="ui.workflow.workplace.worklist" />
<c:set var="prefixBtn"  value="ui.workflow.workplace.worklist.button" />
<c:set var="prefixCommon"  value="ui.workflow.workplace.common" />
<c:set var="prefixCommonBtn"  value="ui.workflow.workplace.common.button" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript">
	<!--
	(function($){
		$(document).ready(function(){
			
			$("#startPeriod").datepicker().next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
			$("#endPeriod").datepicker().next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
			
			$("#searchDoneWorkListButton").click(function() {   
				$("#pageIndex").val("1");
				$("#myForm").submit(); 
				return false; 
			});  
			
			$("#searchkeyword").keypress(function(event){
	            if(event.which == 13) {
	            	$("#searchDoneWorkListButton").click();
	            }
	        });
			
			$jq("select[name=pagePerRecord]").change(function() {
	            //검색용 폼을 서브밋하는 코드 넣으시면 됩니다. 
	            $jq("#searchDoneWorkListButton").click();  
	        });  
		});
	})(jQuery);
	
	function mouseOver(obj){obj.className = "bgSelected";}
	function mouseOut(obj){obj.className = "";}
	
	function popupState(instanceId, partitionId, processId, processVer) {
		var top; 
		var left; 
		var width = 650;
		var height = 600;

		left = (screen.width - width) / 2; 
		top = (screen.height - height) / 2;		
		var param = "mode=runtime&instanceId="+instanceId+"&processType=workflow&partitionId="+partitionId+"&processId="+processId+"&version="+processVer+"&minimapView=false&saveView=false&refreshView=false";
		var contextUrl	= "<c:url value='/workflow/modeler/prism.do'/>?"+param;
		window.open(contextUrl, "", "top=" + top + ", left=" + left +", width="+ width + ", height="+height+", resizable=yes,scrollbars=yes");
	}
	//-->
</script>

<!--mainContents Start-->
<h1 class="none">컨텐츠영역</h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${prefix}' key='doneworklist'/></h2>
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre='${prefix}' key='workplace'/></li>
			<li><ikep4j:message pre='${prefix}' key='todolist'/></li>
			<li class="liLast"><ikep4j:message pre='${prefix}' key='doneworklist'/></li>
		</ul>
	</div>
</div>
<!--//pageTitle End-->

<form id="myForm" method="post" action="<c:url value='/workflow/workplace/worklist/doneWorkList.do' />">
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
						<th scope="row" width="5%"><ikep4j:message pre='${prefix}' key='searchperiod'/></th>
						<td width="45%">						
							<spring:bind path="workplaceSearchCondition.startPeriod">											
							<input type="text" class="inputbox" style="width:63px;" id="startPeriod" name="startPeriod" title="<ikep4j:message pre='${prefix}' key='startperiod'/>" value="${status.value}" size="8" /> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${prefixCommon}' key='calendar'/>" /> ~
							</spring:bind>
							<spring:bind path="workplaceSearchCondition.endPeriod">
							<input type="text" class="inputbox" style="width:63px;" id="endPeriod" name="endPeriod" title="<ikep4j:message pre='${prefix}' key='endperiod'/>" value="${status.value}" size="8" /> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${prefixCommon}' key='calendar'/>" />
							</spring:bind>			
						</td>									
					</tr>
					<tr>
						<spring:bind path="workplaceSearchCondition.searchcondition">
						<th scope="row" width="5%"><ikep4j:message pre='${prefixCommon}' key='searchcondition'/></th>
						<td width="45%">		
							<select title="<ikep4j:message pre='${prefixCommon}' key='searchcondition'/>" name="${status.expression}">
								<c:forEach var="code" items="${workplaceCode.workSearchClassList}">
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
				<a id="searchDoneWorkListButton" name="searchDoneWorkListButton" href="">
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
					<th scope="col" width="8%"><ikep4j:message pre='${prefix}' key='partition' /></th>
					<th scope="col" width="10%"><ikep4j:message pre='${prefix}' key='process' /></th>
					<th scope="col" width="*"><ikep4j:message pre='${prefix}' key='title' /></th>
					<th scope="col" width="12%"><ikep4j:message pre='${prefix}' key='procstartdate' /></th>
					<th scope="col" width="12%"><ikep4j:message pre='${prefix}' key='procenddate' /></th>
					<th scope="col" width="10%"></th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty searchResult.entity}">
						<c:forEach var="doneWorkList" items="${searchResult.entity}" varStatus="status">
						<tr onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
							<td>${(searchResult.recordCount-((searchResult.pageIndex-1)*workplaceSearchCondition.pagePerRecord)-status.count)+1}</td>
							<td>${doneWorkList.partitionName}</td>					
							<td class="textLeft ellipsis">${doneWorkList.processName}</td>
							<td class="textLeft ellipsis">${doneWorkList.title}</td>
							<td><ikep4j:timezone date="${doneWorkList.procStartDate}" pattern="yyyy.MM.dd HH:mm"/></td>
							<td><ikep4j:timezone date="${doneWorkList.procEndDate}" pattern="yyyy.MM.dd HH:mm"/></td>
							<td>
								<a class="button_s" href="javascript:popupState('${doneWorkList.instanceId}', '${doneWorkList.partitionId}', '${doneWorkList.processId}', '${doneWorkList.processVer}');"><span><ikep4j:message pre='${prefixBtn}' key='history' /></span></a>
							</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="8" class="emptyRecord"><ikep4j:message pre='${prefixCommon}' key='emptyRecord' /></td>
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