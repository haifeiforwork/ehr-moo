<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="prefix"     value="ui.portal.admin.screen.popup.listPopup"/>
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />

<script type="text/javascript">
//<!--
(function($) {
	$jq(document).ready(function() { 
		//left menu setting
		$jq("#popupManager").click();
		
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
//-->
</script>

<!--//pageTitle End-->
<form id="searchForm" method="post" action="<c:url value='listPopup.do'/>" onsubmit="return false;">

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
				<option value="POPUP_NAME" <c:if test="${'POPUP_NAME' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${prefix}' key='popupName'/></option>
			</select>	
		</spring:bind>		
		<spring:bind path="searchCondition.searchWord">  					
			<input name="${status.expression}" id="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
		</spring:bind>
		<a class="ic_search" href="#a" onclick="javascript:getList();" ><span><ikep4j:message pre='${prefix}' key='button.search'/></span></a> 
	</div>		
	<div class="clear"></div>
</div>
<!--//tableTop End-->

<!--splitterBox Start-->
<div id="splitterBox">
	<div>
		<!--blockListTable Start-->
		<div class="blockListTable">					
			<table summary="<ikep4j:message pre='${prefix}' key='summary'/>">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="56%">
							<a onclick="javascript:sort('POPUP_NAME', '<c:if test="${searchCondition.sortColumn eq 'POPUP_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='popupName'/>
								<c:if test="${searchCondition.sortColumn eq 'POPUP_NAME'}">
									<c:if test="${searchCondition.sortType eq 'DESC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
									</c:if>
									<c:if test="${searchCondition.sortType eq 'ASC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
									</c:if>
								</c:if>
							</a>
						</th>
						<%--
						<th scope="col" width="36%">
							<a onclick="javascript:sort('POPUP_URL', '<c:if test="${searchCondition.sortColumn eq 'POPUP_URL'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='popupUrl'/>
								<c:if test="${searchCondition.sortColumn eq 'POPUP_URL'}">
									<c:if test="${searchCondition.sortType eq 'DESC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
									</c:if>
									<c:if test="${searchCondition.sortType eq 'ASC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
									</c:if>
								</c:if>
							</a>
						</th> --%>
						<th scope="col" width="12%">
							<a onclick="javascript:sort('POPUP_START_DATE', '<c:if test="${searchCondition.sortColumn eq 'POPUP_START_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='popupStartDate'/>
								<c:if test="${searchCondition.sortColumn eq 'POPUP_START_DATE'}">
									<c:if test="${searchCondition.sortType eq 'DESC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
									</c:if>
									<c:if test="${searchCondition.sortType eq 'ASC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
									</c:if>
								</c:if>
							</a>
						</th>
						<th scope="col" width="12%">
							<a onclick="javascript:sort('POPUP_END_DATE', '<c:if test="${searchCondition.sortColumn eq 'POPUP_END_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='popupEndDate'/>
								<c:if test="${searchCondition.sortColumn eq 'POPUP_END_DATE'}">
									<c:if test="${searchCondition.sortType eq 'DESC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
									</c:if>
									<c:if test="${searchCondition.sortType eq 'ASC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
									</c:if>
								</c:if>
							</a>
						</th>
						<th scope="col" width="10%">
							<a onclick="javascript:sort('WINDOW_YN', '<c:if test="${searchCondition.sortColumn eq 'WINDOW_YN'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='howtopopup'/>
								<c:if test="${searchCondition.sortColumn eq 'WINDOW_YN'}">
									<c:if test="${searchCondition.sortType eq 'DESC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
									</c:if>
									<c:if test="${searchCondition.sortType eq 'ASC'}">
										<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
									</c:if>
								</c:if>
							</a>
						</th>
						<th scope="col" width="10%">
							<a onclick="javascript:sort('POPUP_ACTIVE', '<c:if test="${searchCondition.sortColumn eq 'POPUP_ACTIVE'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='popupActive'/>
								<c:if test="${searchCondition.sortColumn eq 'POPUP_ACTIVE'}">
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
									<td colspan="5" class="emptyRecord"><ikep4j:message pre='${prefix}' key='noPopupData'/></td> 
								</tr>				        
						    </c:when>
						    <c:otherwise>
								<c:forEach var="popup" items="${searchResult.entity}" varStatus="status">
								<tr>
									<td class="ellipsis"><a href="<c:url value='readPopup.do?popupId=${popup.popupId}'/>">${popup.popupName}</a></td>
									<%-- td class="textLeft ellipsis">${popup.popupUrl}</td --%>
									<td>${popup.popupStartDate}</td>
									<td>${popup.popupEndDate}</td>
									<td>
										<c:choose>
											<c:when test="${popup.windowYn == 'Y'}">
												<ikep4j:message pre='${prefix}' key='newWindow'/>
											</c:when>
											<c:otherwise>
												<ikep4j:message pre='${prefix}' key='layer'/>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${popup.popupActive == 1}">
												<ikep4j:message pre='${prefix}' key='use'/>
											</c:when>
											<c:otherwise>
												<ikep4j:message pre='${prefix}' key='noUse'/>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								</c:forEach>				      
						    </c:otherwise> 
						</c:choose>
				</tbody>
			</table>
			<!--Page Numbur Start--> 
			<spring:bind path="searchCondition.pageIndex">
				<ikep4j:pagination searchFormId="searchForm" ajaxEventFunctionName="getList" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
				<input name="${status.expression}" type="hidden" value="${status.value}" title="pageIndex" />
			</spring:bind> 
			<!--//Page Numbur End-->
		</div>
		<!--//blockListTable End-->
		
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a class="button" href="<c:url value='createPopupForm.do'/>"><span><ikep4j:message pre='${prefix}' key='button.addForm'/></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
</div>
<!--//splitterBox End-->
</form>