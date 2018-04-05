<%--
=====================================================
* 기능 설명 : Workflow Admin SMS관리
* 작성자 : 이재경
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preHeader"  value="ui.workflow.admin.smsAdministration.smsHistoryList" /> 
<c:set var="preButton"  value="ui.workflow.admin.smsAdministration.smsHistoryList.button" />

<script type="text/javascript">
	<!--
	(function($){
		
		/**
		 * onload시 수행할 코드
		 */
		$(document).ready(function(){
			
			$("#startPeriod").datepicker().next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
			$("#endPeriod").datepicker().next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
			
			$("#searchSmsHistoryListButton").click(function() {   
				$("#pageIndex").val("1");
				$("#myForm").submit(); 
				return false; 
			});  
			
			$("#searchkeyword").keypress(function(event){
	            if(event.which == 13) {
	            	$("#searchSmsHistoryListButton").click();
	            }
	        });
			
			$jq("select[name=pagePerRecord]").change(function() {
	            //검색용 폼을 서브밋하는 코드 넣으시면 됩니다. 
	            $jq("#searchSmsHistoryListButton").click();  
	        });  
		});
		
	})(jQuery);
	
	function mouseOver(obj){obj.className = "bgSelected";}
	function mouseOut(obj){obj.className = "";}
	//-->
</script>

<!--mainContents Start-->
<h1 class="none"><ikep4j:message pre='${preHeader}' key='smsHistory'/></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preHeader}' key='smsHistory'/></h2>
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre='${preHeader}' key='workflow'/></li>
			<li><ikep4j:message pre='${preHeader}' key='noticeMgmt'/></li>
			<li class="liLast"><ikep4j:message pre='${preHeader}' key='smsHistory'/></li>
		</ul>
	</div>
</div>
<!--//pageTitle End-->

<form id="myForm" method="post" action="<c:url value='/workflow/admin/smsAdministration/smsHistoryList.do' />">
	<!--blockSearch Start-->
	<div class="blockSearch">
		<div class="corner_RoundBox03">
			<table summary="<ikep4j:message pre='${preHeader}' key='smsHistory'/>">
				<caption></caption>
				<tbody>
					<tr>
						<th scope="row" width="5%"><ikep4j:message pre='${preHeader}' key='searchperiod'/></th>
						<td width="45%">						
							<spring:bind path="adminSmsSearchCondition.startPeriod">											
							<input type="text" class="inputbox" style="width:63px;" id="startPeriod" name="startPeriod" title="<ikep4j:message pre='${preHeader}' key='startperiod'/>" value="${status.value}" size="8" /> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${preHeader}' key='calendar'/>" /> ~
							</spring:bind>
							<spring:bind path="adminSmsSearchCondition.endPeriod">
							<input type="text" class="inputbox" style="width:63px;" id="endPeriod" name="endPeriod" title="<ikep4j:message pre='${preHeader}' key='endperiod'/>" value="${status.value}" size="8" /> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${preHeader}' key='calendar'/>" />
							</spring:bind>		
						</td>
					</tr>
					<tr>
						<spring:bind path="adminSmsSearchCondition.searchcondition">
						<th scope="row" width="5%"><ikep4j:message pre='${preHeader}' key='searchcondition'/></th>
						<td width="45%">		
							<select title="<ikep4j:message pre='${preHeader}' key='searchcondition'/>" name="${status.expression}">
								<c:forEach var="code" items="${workplaceCode.searchSmsConditionList}">
									<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>><spring:message code="${code.value}"/></option>
								</c:forEach> 
							</select>															
						</td>
						</spring:bind>
						<spring:bind path="adminSmsSearchCondition.searchkeyword">
						<th scope="row"><ikep4j:message pre='${preHeader}' key='searchkeyword'/></th>
						<td colspan="3">								
							<input type="text" class="inputbox" title="<ikep4j:message pre='${preHeader}' key='searchkeyword'/>" id="${status.expression}" name="${status.expression}" value="${status.value}" size="35" />
						</td>		
						</spring:bind>		
					</tr>								
				</tbody>
			</table>
			<div class="searchBtn">
				<a id="searchSmsHistoryListButton" name="searchSmsHistoryListButton" href="">
					<img src="<c:url value="/base/images/theme/theme01/basic/btn_search.gif"/>" alt="<ikep4j:message pre='${preButton}' key='search'/>" />
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
				<spring:bind path="adminSmsSearchCondition.pagePerRecord">
				<select name="${status.expression}" title='<ikep4j:message pre='${preHeader}' key='${status.expression}' />'>
					<c:forEach var="code" items="${workplaceCode.pageNumList}">
						<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
					</c:forEach> 
				</select> 
				</spring:bind>
				<div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preHeader}' key='page' /> ( <ikep4j:message pre='${preHeader}' key='totalCount' /><span> ${searchResult.recordCount}</span>)</div>
			</div>			
			<div class="clear"></div>
		</div>
		<!--//tableTop End-->
	
		<table summary="My To-Do 목록">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="5%"><ikep4j:message pre='${preHeader}' key='no' /></th>
					<th scope="col" width="5%"><ikep4j:message pre='${preHeader}' key='register' /></th>
					<th scope="col" width="*"><ikep4j:message pre='${preHeader}' key='contents' /></th>
					<th scope="col" width="10%"><ikep4j:message pre='${preHeader}' key='receiver' /></th>
					<th scope="col" width="10%"><ikep4j:message pre='${preHeader}' key='receiverphone' /></th>
					<th scope="col" width="12%"><ikep4j:message pre='${preHeader}' key='registDate' /></th>
					<th scope="col" width="8%"><ikep4j:message pre='${preHeader}' key='registerYN' /></th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty searchResult.entity}">
						<c:forEach var="smsList" items="${searchResult.entity}" varStatus="status">
						<tr onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
							<td>${(searchResult.recordCount-((searchResult.pageIndex-1)*adminSmsSearchCondition.pagePerRecord)-status.count)+1}</td>
							<td>${smsList.registerName}</td>					
							<td class="textLeft ellipsis">${smsList.contents}</td>
							<td>${smsList.receiverName}</td>
							<td>${smsList.receiverPhoneno}</td>
							<td>${smsList.registDate}</td>
							<td>
								<c:choose>
									<c:when test="${smsList.resultCode eq '0'}">성공</c:when>
									<c:otherwise>실패</c:otherwise>
								</c:choose>
							</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preHeader}' key='display.dataNotFound' /></td>
						</tr>
					</c:otherwise>	
				</c:choose>		
			</tbody>
		</table>
		
		<!--Page Numbur Start--> 
		<spring:bind path="adminSmsSearchCondition.pageIndex">
		<ikep4j:pagination searchFormId="myForm" pageIndexInput="${status.expression}" searchCondition="${adminSmsSearchCondition}" />
		<input id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" />
		</spring:bind> 
		<!--//Page Numbur End--> 
	</div>
	<!--//blockListTable End-->	
</form>
<!--//mainContents End-->