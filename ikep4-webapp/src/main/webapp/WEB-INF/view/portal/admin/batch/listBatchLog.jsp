<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"
	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<c:set var="prefix" value="ui.portal.admin.batch.listBatchLog" />
<c:set var="preSearch" value="ui.communication.common.searchCondition" />

<script type="text/javascript">
//<![CDATA[
(function($) {
	$jq(document).ready(function() { 
		
		//left menu setting
		$jq("#batchLogLinkOfLeft").click();
		
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
})(jQuery);
//]]>
</script>

<form id="searchForm" method="post"
	action="<c:url value='/portal/admin/batch/listBatchLog.do'/>"
	onsubmit="return false;">

	<spring:bind path="searchCondition.sortColumn">
		<input name="${status.expression}" type="hidden"
			value="${status.value}"
			title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	<spring:bind path="searchCondition.sortType">
		<input name="${status.expression}" type="hidden"
			value="${status.value}"
			title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	<!--tableTop Start-->
	<div class="tableTop">
		<div class="tableTop_bgR"></div>
		<h2>
			<ikep4j:message pre='${prefix}' key='pageTitle' />
		</h2>
		<div class="listInfo">
			<spring:bind path="searchCondition.pagePerRecord">
				<select name="${status.expression}"
					title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<c:forEach var="code" items="${portalCode.pageNumList}">
						<option value="${code.key}"
							<c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
					</c:forEach>
				</select>
			</spring:bind>
			<div class="totalNum">
				${searchResult.pageIndex}/ ${searchResult.pageCount}
				<ikep4j:message pre='${preSearch}' key='page' />
				(
				<ikep4j:message pre='${preSearch}' key='pageCount.info' />
				<span> ${searchResult.recordCount}</span>)
			</div>
		</div>
		<div class="tableSearch">
			<spring:bind path="searchCondition.searchColumn">
				<select name="${status.expression}"
					title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<option value="JOB_NAME"
						<c:if test="${'JOB_NAME' eq status.value}">selected="selected"</c:if>>
						<ikep4j:message pre='${prefix}' key='jobName' post='' />
					</option>
				</select>
			</spring:bind>
			<spring:bind path="searchCondition.searchWord">
				<input name="${status.expression}" id="${status.expression}"
					value="${status.value}" type="text" class="inputbox"
					title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'
					size="20" />
			</spring:bind>
			<a class="ic_search" href="#a" onclick="javascript:getList();"><span><ikep4j:message
						pre='${prefix}' key='search' />
			</span>
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
				<table summary="summary">
					<caption></caption>
					<thead>
						<tr>
							<th scope="col" width="5%"><ikep4j:message pre='${prefix}' key='id' />
							</th>

							<th scope="col" width="15%"><a
								onclick="sort('JOB_NAME', '<c:if test="${searchCondition.sortColumn eq 'JOB_NAME'}">${searchCondition.sortType}</c:if>');"
								href="#a"> <ikep4j:message pre='${prefix}' key='jobName' />
									<c:if test="${searchCondition.sortColumn eq 'JOB_NAME'}">
										<c:if test="${searchCondition.sortType eq 'DESC'}">
											<img
												src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />"
												alt="up" />
										</c:if>
										<c:if test="${searchCondition.sortType eq 'ASC'}">
											<img
												src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />"
												alt="down" />
										</c:if>
									</c:if> </a></th>

							<th scope="col" width="15%"><a
								onclick="sort('START_DATE', '<c:if test="${searchCondition.sortColumn eq 'START_DATE'}">${searchCondition.sortType}</c:if>');"
								href="#a"> <ikep4j:message pre='${prefix}' key='startDate' />
									<c:if test="${searchCondition.sortColumn eq 'START_DATE'}">
										<c:if test="${searchCondition.sortType eq 'DESC'}">
											<img
												src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />"
												alt="up" />
										</c:if>
										<c:if test="${searchCondition.sortType eq 'ASC'}">
											<img
												src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />"
												alt="down" />
										</c:if>
									</c:if> </a></th>
							<th scope="col" width="15%"><a
								onclick="sort('END_DATE', '<c:if test="${searchCondition.sortColumn eq 'END_DATE'}">${searchCondition.sortType}</c:if>');"
								href="#a"> <ikep4j:message pre='${prefix}' key='endDate' />
									<c:if test="${searchCondition.sortColumn eq 'END_DATE'}">
										<c:if test="${searchCondition.sortType eq 'DESC'}">
											<img
												src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />"
												alt="up" />
										</c:if>
										<c:if test="${searchCondition.sortType eq 'ASC'}">
											<img
												src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />"
												alt="down" />
										</c:if>
									</c:if> </a></th>
							<th scope="col" width="15%"><a
								onclick="sort('RESULT', '<c:if test="${searchCondition.sortColumn eq 'RESULT'}">${searchCondition.sortType}</c:if>');"
								href="#a"> <ikep4j:message pre='${prefix}' key='result' />
									<c:if test="${searchCondition.sortColumn eq 'RESULT'}">
										<c:if test="${searchCondition.sortType eq 'DESC'}">
											<img
												src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />"
												alt="up" />
										</c:if>
										<c:if test="${searchCondition.sortType eq 'ASC'}">
											<img
												src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />"
												alt="down" />
										</c:if>
									</c:if> </a></th>
							<th scope="col" width="30%"><a
								onclick="sort('ERROR_CAUSE', '<c:if test="${searchCondition.sortColumn eq 'ERROR_CAUSE'}">${searchCondition.sortType}</c:if>');"
								href="#a"> <ikep4j:message pre='${prefix}' key='errorCause' />
									<c:if test="${searchCondition.sortColumn eq 'ERROR_CAUSE'}">
										<c:if test="${searchCondition.sortType eq 'DESC'}">
											<img
												src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />"
												alt="up" />
										</c:if>
										<c:if test="${searchCondition.sortType eq 'ASC'}">
											<img
												src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />"
												alt="down" />
										</c:if>
									</c:if> </a></th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${searchResult.emptyRecord}">
								<tr>
									<td colspan="6" class="emptyRecord"><ikep4j:message
											pre='${prefix}' key='list.empty' />
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="quartzLog" items="${searchResult.entity}"
									varStatus="status">
									<tr>
										<td class="textCenter">
														${searchCondition.recordCount -((searchCondition.pageIndex - 1) * searchCondition.pagePerRecord) - status.index}
										</td>
										<td class="textCenter">
														${quartzLog.jobName}
										</td>
										<td class="textCenter">
														<fmt:formatDate value="${quartzLog.startDate}" type="both" pattern="yyyy.MM.dd hh:mm:ss"/>
										</td>
										<td class="textCenter">
														<fmt:formatDate value="${quartzLog.endDate}" type="both" pattern="yyyy.MM.dd hh:mm:ss"/>
										</td>
										<td class="textCenter">
											<c:if test="${quartzLog.result eq'F'}">
												<ikep4j:message pre='${prefix}' key='fail' />
											</c:if>
											<c:if test="${quartzLog.result eq 'C'}">
												<ikep4j:message pre='${prefix}' key='complete' />
											</c:if>
										</td>
										<td class="textCenter">
														${quartzLog.errorCause}
										</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				<!--Page Numbur Start-->
				<spring:bind path="searchCondition.pageIndex">
					<ikep4j:pagination searchFormId="searchForm"
						ajaxEventFunctionName="getList"
						pageIndexInput="${status.expression}"
						searchCondition="${searchCondition}" />
					<input name="${status.expression}" type="hidden"
						value="${status.value}"
						title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				<!--//Page Numbur End-->
			</div>
			<!--//blockListTable End-->
		</div>
	</div>
	<!--//splitterBox End-->
</form>