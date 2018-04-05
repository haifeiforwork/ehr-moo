<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"
	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<c:set var="prefix" value="ui.portal.admin.batch.listTrigger" />
<c:set var="preSearch" value="ui.communication.common.searchCondition" />

<script type="text/javascript">
//<![CDATA[
(function($) {
	$jq(document).ready(function() { 
		
		//left menu setting
		$jq("#batchTriggerLinkOfLeft").click();
		
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
	action="<c:url value='/portal/admin/batch/listTrigger.do'/>"
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
					<option value="TRIGGER_NAME"
						<c:if test="${'TRIGGER_NAME' eq status.value}">selected="selected"</c:if>>
						<ikep4j:message pre='${prefix}' key='triggerName' post='' />
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
							<th scope="col" width="35%"><a
								onclick="sort('TRIGGER_NAME', '<c:if test="${searchCondition.sortColumn eq 'TRIGGER_NAME'}">${searchCondition.sortType}</c:if>');"
								href="#a"> <ikep4j:message pre='${prefix}' key='triggerName' />
									<c:if test="${searchCondition.sortColumn eq 'TRIGGER_NAME'}">
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

							<th scope="col" width="35%"><a
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
								onclick="sort('TRIGGER_TYPE', '<c:if test="${searchCondition.sortColumn eq 'TRIGGER_TYPE'}">${searchCondition.sortType}</c:if>');"
								href="#a"> <ikep4j:message pre='${prefix}' key='triggerType' />
									<c:if test="${searchCondition.sortColumn eq 'TRIGGER_TYPE'}">
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
								onclick="sort('TRIGGER_STATUS', '<c:if test="${searchCondition.sortColumn eq 'TRIGGER_STATUS'}">${searchCondition.sortType}</c:if>');"
								href="#a"> <ikep4j:message pre='${prefix}' key='triggerStatus' />
									<c:if test="${searchCondition.sortColumn eq 'TRIGGER_STATUS'}">
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
									<td colspan="5" class="emptyRecord"><ikep4j:message
											pre='${prefix}' key='list.empty' />
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="batchTrigger" items="${searchResult.entity}"
									varStatus="status">
									<tr>
										<td class="textCenter"><c:choose>
												<c:when test="${batchTrigger.triggerType == 'CRON'}">
													<a
														href="<c:url value='/portal/admin/batch/readCronJob.do?triggerName=${batchTrigger.triggerName}&jobName=${batchTrigger.jobName}&triggerType=${batchTrigger.triggerType}'/>">
														${batchTrigger.triggerName} </a>
												</c:when>
												<c:otherwise>
													<a
														href="<c:url value='/portal/admin/batch/readSimpleJob.do?triggerName=${batchTrigger.triggerName}&jobName=${batchTrigger.jobName}&triggerType=${batchTrigger.triggerType}'/>">
														${batchTrigger.triggerName} </a>
												</c:otherwise>
											</c:choose></td>
										<td><c:choose>
												<c:when test="${batchTrigger.triggerType == 'CRON'}">
													<a
														href="<c:url value='/portal/admin/batch/readCronJob.do?triggerName=${batchTrigger.triggerName}&jobName=${batchTrigger.jobName}&triggerType=${batchTrigger.triggerType}'/>">
														${batchTrigger.jobName} </a>
												</c:when>
												<c:otherwise>
													<a
														href="<c:url value='/portal/admin/batch/readSimpleJob.do?triggerName=${batchTrigger.triggerName}&jobName=${batchTrigger.jobName}&triggerType=${batchTrigger.triggerType}'/>">
														${batchTrigger.jobName} </a>
												</c:otherwise>
											</c:choose></td>
										<td class="textCenter">
												<c:choose>
													<c:when test="${batchTrigger.triggerType == 'CRON'}">
														<a
															href="<c:url value='/portal/admin/batch/readCronJob.do?triggerName=${batchTrigger.triggerName}&jobName=${batchTrigger.jobName}&triggerType=${batchTrigger.triggerType}'/>">
															${batchTrigger.triggerType} </a>
													</c:when>
													<c:otherwise>
														<a
															href="<c:url value='/portal/admin/batch/readSimpleJob.do?triggerName=${batchTrigger.triggerName}&jobName=${batchTrigger.jobName}&triggerType=${batchTrigger.triggerType}'/>">
															${batchTrigger.triggerType} </a>
													</c:otherwise>
												</c:choose>
										</td>
										<td class="textCenter">
											<div class="ellipsis">
												<c:choose>
													<c:when test="${batchTrigger.triggerType == 'CRON'}">
														<a
															href="<c:url value='/portal/admin/batch/readCronJob.do?triggerName=${batchTrigger.triggerName}&jobName=${batchTrigger.jobName}&triggerType=${batchTrigger.triggerType}'/>">
															<c:if test="${batchTrigger.triggerStatus == 'PAUSED'}">
																<ikep4j:message pre='${prefix}' key='pause'/>
															</c:if>
										
															<c:if test="${batchTrigger.triggerStatus == 'WAITING'}">
																<ikep4j:message pre='${prefix}' key='waiting'/>
															</c:if>
													    </a>
													</c:when>
													<c:otherwise>
														<a
															href="<c:url value='/portal/admin/batch/readSimpleJob.do?triggerName=${batchTrigger.triggerName}&jobName=${batchTrigger.jobName}&triggerType=${batchTrigger.triggerType}'/>">
															<c:if test="${batchTrigger.triggerStatus == 'PAUSED'}">
																<ikep4j:message pre='${prefix}' key='pause'/>
															</c:if>
										
															<c:if test="${batchTrigger.triggerStatus == 'WAITING'}">
																<ikep4j:message pre='${prefix}' key='waiting'/>
															</c:if>															
													    </a>
													</c:otherwise>
												</c:choose>
											</div></td>
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

			<!--blockButton Start-->
			<div class="blockButton">
				<ul>
					<li><a class="button"
						href="<c:url value='/portal/admin/batch/createJobForm.do'/>"><span><ikep4j:message
									pre='${prefix}' key='button.addForm' />
						</span>
					</a>
					</li>
				</ul>
			</div>
			<!--//blockButton End-->
		</div>
	</div>
	<!--//splitterBox End-->
</form>