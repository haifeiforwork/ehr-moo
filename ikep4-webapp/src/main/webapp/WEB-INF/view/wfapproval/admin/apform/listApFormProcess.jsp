<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.wfapproval.admin.apform.process.header" />
<c:set var="preList"  	value="ui.workflow.workplace.worklist" />
<c:set var="preButton"  value="ui.wfapproval.common.button" />
<c:set var="preMessage" value="ui.wfapproval.common.message" />
<c:set var="preSearch"  value="ui.wfapproval.common.searchCondition" />
 <%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
	<!--
	(function($){
		
		/**
		 * onload시 수행할 코드
		 */
		$(document).ready(function(){
			
			$("#searchkeyword").keypress(function(event){
	            if(event.which == 13) {
	            	$("#pageIndex").click();
	            }
	        });
			
			$("select[name=pagePerRecord]").change(function(){
                $("#pageIndex").click();
            });
			
			/**
			 * 검색버튼
			 */
			$("#searchStartWorkListButton").click(function() {
				$("input[name=pageIndex]").val('1');
				$("#pageIndex").click();
				return false; 
			});
			
			/**
			 * 페이징 버튼
			 */
			$("#pageIndex").click(function() {
				
				$.post('<c:url value="/wfapproval/admin/apform/ajaxListApFormProcess.do"/>', $("#apFormProcess").serialize()).success(function(data){
					
					//alert(data);
					//프로세스 목록
					$("#processList").html(data);
					
                }).error(function(event, request, settings){
                    alert("error");
                });
				
				return false; 
			});
			
		});
		
	})(jQuery);
	//-->
</script>

<form id="apFormProcess" method="post" action="<c:url value='/wfapproval/admin/apform/listApFormProcess.do' />">
	<!--blockSearch Start-->
	<div class="blockSearch">
		<div class="corner_RoundBox03">
			<table summary="검색">
				<caption></caption>
				<tbody>
					<tr>
						<spring:bind path="workplaceSearchCondition.partition">
						<th scope="row" width="15%"><ikep4j:message pre='${preList}' key='partition'/></th>
						<td width="15%">		
							<select title="<ikep4j:message pre='${preList}' key='partition'/>" 
									name="${status.expression}">
								<option value=""><ikep4j:message pre='${preSearch}' key='selectAll'/></option>
							</select>
						</td>
						</spring:bind>
						<spring:bind path="workplaceSearchCondition.searchcondition">
						<th scope="row" width="15%"><ikep4j:message pre='${preSearch}' key='searchColumn'/></th>
						<td width="15%">
							<select title="<ikep4j:message pre='${preSearch}' key='searchColumn'/>" name="${status.expression}">
								<c:forEach var="code" items="${workplaceCode.procSearchClassList}">
									<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>><spring:message code="${code.value}"/></option>
								</c:forEach> 
							</select>															
						</td>
						<td></td>
						</spring:bind>
					</tr>
					<tr>
						<spring:bind path="workplaceSearchCondition.searchkeyword">
						<th scope="row" width="15%"><ikep4j:message pre='${preSearch}' key='searchWord'/></th>
						<td colspan="3">
							<input 	type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='searchWord'/>" 
									id="${status.expression}" name="${status.expression}" value="${status.value}" style="width:98%;" />
						</td>
						<td>
							<a id="searchStartWorkListButton" name="searchStartWorkListButton" href="">
								<img src="<c:url value="/base/images/theme/theme01/basic/btn_search.gif"/>" alt="<ikep4j:message pre='${preSearch}' key='search' />" />
							</a>
						</td>
						</spring:bind>		
					</tr>								
				</tbody>
			</table>
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
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<c:forEach var="code" items="${workplaceCode.pageNumList}">
						<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
					</c:forEach> 
				</select> 
				</spring:bind>
				<div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount' /><span> ${searchResult.recordCount}</span>)</div>
			</div>			
			<div class="clear"></div>
		</div>
		<!--//tableTop End-->
	
		<table summary="My To-Do 목록">
			<caption></caption>
			<thead>
				<tr>
					<!--
					<th scope="col" width="5%"><ikep4j:message pre='${preList}' key='number' /></th>
					-->
					<th scope="col" width="7%"><ikep4j:message pre='${preSearch}' key='select' /></th>
					<th scope="col" width="17%"><ikep4j:message pre='${preList}' key='partition' /></th>
					<th scope="col" width="35%"><ikep4j:message pre='${preList}' key='process' /></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='activity' /></th>
					<!--
					<th scope="col" width="10%"><ikep4j:message pre='${preList}' key='authorname' /></th>
					-->
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty searchResult.entity}">
						<c:forEach var="startProcList" items="${searchResult.entity}" varStatus="status">
						<tr class='${status.count%2 == 1 ? "bgWhite" : "bgGray"}'>
							<!--
							<td>${(searchResult.recordCount-status.count)+1}</td>
							-->
							<td>
								<input 	id="processId" name="processId" class="radio" title="checkbox" type="radio" value="${startProcList.processId}"
										onchange="f_getProcessInfo('${startProcList.processId}', '${startProcList.processName}', '${startProcList.processType}', '${startProcList.processVer}')"/>
							</td>
							<td>${startProcList.partitionName}</a></td>					
							<td class="textLeft">${startProcList.processName}</td>
							<td class="textLeft">${startProcList.activityName}</td>
							<!--
							<td>${startProcList.authorName}</td>
							-->
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="4" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
						</tr>
					</c:otherwise>	
				</c:choose>		
			</tbody>
		</table>
		
		<!--Page Numbur Start--> 
		<spring:bind path="workplaceSearchCondition.pageIndex">
		<ikep4j:pagination searchButtonId="${status.expression}" pageIndexInput="${status.expression}" searchCondition="${workplaceSearchCondition}" />
		<input id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind> 
		<!--//Page Numbur End--> 
	</div>
	<!--//blockListTable End-->	
</form>
