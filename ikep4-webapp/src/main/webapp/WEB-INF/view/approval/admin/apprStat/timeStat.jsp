<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preApCommList"    value="ui.approval.work.apprlist" />
<c:set var="preSearch"  			value="ui.approval.common.searchCondition" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>

<c:set var="formActUrl"     value="/approval/admin/apprStat/timeStat.do" />

<c:set var="cntTot1"     value="0" />
<c:set var="cntTot2"     value="0" />
<c:set var="cntTot3"     value="0" />
<c:set var="cntTot4"     value="0" />
<c:set var="cntTot5"     value="0" />
<c:set var="cntTot6"     value="0" />
<c:set var="cntTot7"     value="0" />
<c:set var="cntTot8"     value="0" />
<c:set var="cntTot9"     value="0" />
<c:set var="cntTot10"     value="0" />
<c:set var="cntTot11"     value="0" />
<c:set var="cntTot12"     value="0" />
<c:set var="timeTot1"     value="0" />
<c:set var="timeTot2"     value="0" />
<c:set var="timeTot3"     value="0" />
<c:set var="timeTot4"     value="0" />
<c:set var="timeTot5"     value="0" />
<c:set var="timeTot6"     value="0" />
<c:set var="timeTot7"     value="0" />
<c:set var="timeTot8"     value="0" />
<c:set var="timeTot9"     value="0" />
<c:set var="timeTot10"     value="0" />
<c:set var="timeTot11"     value="0" />
<c:set var="timeTot12"     value="0" />
<c:set var="cntSumTot"     value="0" />
<c:set var="timeSumTot"     value="0" />
			 
<script type="text/javascript">
<!-- 

//시작일과 종료일 체크하는 메써드 jQuery에 추가	
jQuery.validator.addMethod("greaterThan", function(value, element, params) {
	var val_Date = new Date(value.substring(0,4), value.substring(5,7), value.substring(8,10) );
	var par_Date = new Date(jQuery(params).val().substring(0,4), jQuery(params).val().substring(5,7), jQuery(params).val().substring(8,10) );
    if (!/Invalid|NaN/.test(val_Date)) {return val_Date >= par_Date;}
    return isNaN(value) && isNaN(jQuery(params).val()) || (parseFloat(value) >= parseFloat(jQuery(params).val())); 
});
jQuery.validator.addMethod("lesserThan", function(value, element, params) {
	var val_Date = new Date(value.substring(0,4), value.substring(5,7), value.substring(8,10) );
	var par_Date = new Date(jQuery(params).val().substring(0,4), jQuery(params).val().substring(5,7), jQuery(params).val().substring(8,10) );
    if (!/Invalid|NaN/.test(val_Date)) {return val_Date <= par_Date;}
    return isNaN(value) && isNaN(jQuery(params).val()) || (parseFloat(value) <= parseFloat(jQuery(params).val())); 
});

(function($) {

	//주의 : 글로벌 함수는 아래와 같이 글로벌 변수에 무명 함수를 assign해서 생성한다.
	
	/**
	 * 정렬 조건 변경 함수
	 * 
	 * @param {Object} sortColumn
	 * @param {Object} sortType
	 */
	 
	 f_Sort = function(sortColumn, sortType) {
		$("input[name=sortColumn]").val(sortColumn);
		
		if(sortType == '') sortType = 'ASC';
		
		if( sortType == 'ASC') {
			$("input[name=sortType]").val('DESC');	
		} else {
			$("input[name=sortType]").val('ASC');
		}
		
		$("#searchApListButton").click();
	};
	
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {
		
		$jq("#apprIntegrateOfLeft").click();
	
		$("#searchStartDate").datepicker().next().eq(0).click(function() { $("#searchStartDate").datepicker("show"); });
		$("#searchEndDate").datepicker().next().eq(0).click(function() { $("#searchEndDate").datepicker("show"); });
		
		$("#searchApListButton").click(function() {
			//$("input[name=pageIndex]").val('1');
			//$("#searchForm").submit(); 
			//return false; 
			$jq("input[name=isExcel]").val("");
			
			$jq.ajax({
				url : '<c:url value="/approval/admin/apprStat/timeStat.do" />',
				data : $("#searchForm").serialize(),
				type : "post",
				success : function(result) {
					$jq("#groupFormDiv").html(result);
				}
			});
			
		});
		
		/**
		 * 페이징 버튼
		 */
		$("#pageIndex").click(function() {
			$jq("input[name=isExcel]").val("");
			$("#searchForm").submit(); 
			return false; 
		});
		
		$("select[name=pagePerRecord]").change(function(){
			$jq("input[name=isExcel]").val("");
            $("#pageIndex").click();
        });
		
		$("#excelDownload").click(function() { 
			$jq("input[name=isExcel]").val("yes");
			$jq("#searchForm").submit(); 
			return false;
		});
		
	});
})(jQuery);  
//-->
</script>
			
<%-- <h1 class="none"><ikep4j:message pre='${preApCommList}.pageTitle' key='timeStat' /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preApCommList}.pageTitle' key='timeStat' /></h2>
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.3depth" /></li>
			<li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.4depth" /></li>
		</ul>
	</div>
</div>
<!--//pageTitle End--> --%>

<!--blockSearch Start-->
<form id="searchForm" method="post" action="<c:url value='${formActUrl}' />">
	<spring:bind path="searchCondition.apprId">
		<input name="${status.expression}" type="hidden" value="" title="hidden" />
	</spring:bind>
	<spring:bind path="searchCondition.listType">
		<input name="${status.expression}" type="hidden" value="listApprDocAllUser" title="hidden" />
	</spring:bind>
	<input name="apprIds" type="hidden" value="" title="hidden" />
	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<spring:bind path="searchCondition.searchGroupId">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<spring:bind path="searchCondition.isExcel">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 

<div class="blockSearch">
	<div class="corner_RoundBox03">
		<table summary="<ikep4j:message pre='${preApCommList}.pageTitle' key='timeStat' />">
			<caption></caption>
			<tbody>
				<tr>
					
					<spring:bind path="searchCondition.searchStatisType">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='searchStatisType'/></th>
					<td width="15%">								
						<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
							<c:forEach var="type" items="${typeList}">
								<option value="${type.key}" <c:if test="${type.key eq status.value}">selected="selected"</c:if>><spring:message code="${type.value}" /></option>
							</c:forEach> 
						</select> 
					</td>		
					</spring:bind>	
					
					<th scope="row" width="5%">
						<ikep4j:message pre='${preSearch}' key='searchPeriod' />		
					</th>
					<td width="35%">
					
						<spring:bind path="searchCondition.searchStartYear">
						<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
							<c:forEach var="year" items="${yearList}">
								<option value="${year.key}" <c:if test="${year.key eq status.value}">selected="selected"</c:if>>${year.value}</option>
							</c:forEach> 
						</select> 
						</spring:bind>
						
						<spring:bind path="searchCondition.searchStartMonth">
						<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
							<c:forEach var="month" items="${monthList}">
								<option value="${month.key}" <c:if test="${month.key eq status.value}">selected="selected"</c:if>>${month.value}</option>
							</c:forEach> 
						</select> 
						</spring:bind>
						
						~
						
						<spring:bind path="searchCondition.searchEndYear">
						<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
							<c:forEach var="year" items="${yearList}">
								<option value="${year.key}" <c:if test="${year.key eq status.value}">selected="selected"</c:if>>${year.value}</option>
							</c:forEach> 
						</select> 
						</spring:bind>
						
						<spring:bind path="searchCondition.searchEndMonth">
						<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
							<c:forEach var="month" items="${monthList}">
								<option value="${month.key}" <c:if test="${month.key eq status.value}">selected="selected"</c:if>>${month.value}</option>
							</c:forEach> 
						</select> 
						</spring:bind>
								
					</td>
					
				</tr>
			</tbody>
		</table>
		<div class="searchBtn">
			<a id="searchApListButton" name="searchApListButton" href="#a"><img src="<c:url value='/base/images/theme/theme01/basic/btn_search.gif' />" alt="검색" /></a>
		</div>
		
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
		<div class="l_b_corner"></div>
		<div class="r_b_corner"></div>
	</div>
</div>	
<!--//blockSearch End-->

<h1 class="none">
  <ikep4j:message pre='${preApCommList}.pageTitle' key='timeStat' />
</h1>

<!--blockListTable Start-->
<div class="blockListTable">

	<!--tableTop Start-->
	<div class="tableTop">
		<div class="tableTop_bgR"></div> 
		<div class="listInfo">
			<%-- <spring:bind path="searchCondition.pagePerRecord">
			<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<c:forEach var="commonCode" items="${commonCode}">
					<option value="${commonCode.key}" <c:if test="${commonCode.key eq status.value}">selected="selected"</c:if>>${commonCode.value}</option>
				</c:forEach> 
			</select> 
			</spring:bind>
			<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
			 --%>
			<div align="right">
				<c:if test="${searchCondition.searchStatisType == '0'}">
					<ikep4j:message pre='${preSearch}' key='searchStatisType00' />
				</c:if>
				<c:if test="${searchCondition.searchStatisType == '1'}">
					<ikep4j:message pre='${preSearch}' key='searchStatisType11' />
				</c:if>
				&nbsp;&nbsp;
				<a href="#a" id="excelDownload" title="excel download"><img src="<c:url value='/base/images/icon/ic_xls.gif'/>" alt="excel download" /></a>	
			</div>			
		</div>			
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	<table summary="<ikep4j:message pre='${preApCommList}.pageTitle' key='timeStat' />" id="statistics_listTable">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col">
					<a onclick="f_Sort('groupName', '<c:if test="${searchCondition.sortColumn eq 'groupName'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preSearch}' key='apprDept' />&nbsp;&nbsp;
					</a>
					<c:choose>
					    <c:when test="${searchCondition.sortColumn eq 'groupName' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					    <c:when test="${searchCondition.sortColumn eq 'groupName' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
				    </c:choose>
				</th>
				<c:if test="${!empty searchCondition.ym1}">
					<th scope="col">${fn:substring(searchCondition.ym1,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym2}">
					<th scope="col">${fn:substring(searchCondition.ym2,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym3}">
					<th scope="col">${fn:substring(searchCondition.ym3,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym4}">
					<th scope="col">${fn:substring(searchCondition.ym4,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym5}">
					<th scope="col">${fn:substring(searchCondition.ym5,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym6}">
					<th scope="col">${fn:substring(searchCondition.ym6,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym7}">
					<th scope="col">${fn:substring(searchCondition.ym7,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym8}">
					<th scope="col">${fn:substring(searchCondition.ym8,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym9}">
					<th scope="col">${fn:substring(searchCondition.ym9,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym10}">
					<th scope="col">${fn:substring(searchCondition.ym10,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym11}">
					<th scope="col">${fn:substring(searchCondition.ym11,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym12}">
					<th scope="col">${fn:substring(searchCondition.ym12,5,7)}&nbsp;</th>
				</c:if>
				<th scope="col">
					<c:if test="${searchCondition.searchStatisType == '0'}">
						<a onclick="f_Sort('cntSum', '<c:if test="${searchCondition.sortColumn eq 'cntSum'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preSearch}' key='sum' />&nbsp;&nbsp;
						</a>
						<c:choose>
						    <c:when test="${searchCondition.sortColumn eq 'cntSum' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
						    <c:when test="${searchCondition.sortColumn eq 'cntSum' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
					    </c:choose>
					</c:if>
					<c:if test="${searchCondition.searchStatisType == '1'}">
						<a onclick="f_Sort('timeSum', '<c:if test="${searchCondition.sortColumn eq 'timeSum'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preSearch}' key='sum' />&nbsp;&nbsp;
						</a>
						<c:choose>
						    <c:when test="${searchCondition.sortColumn eq 'timeSum' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
						    <c:when test="${searchCondition.sortColumn eq 'timeSum' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
					    </c:choose>
					</c:if>
				</th>
			</tr>
		</thead> 
		<tbody>
			<c:choose>
			    <c:when test="${empty searchResult.entity}">
					<tr>
						<td colspan="13" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
					</tr>
			    </c:when>
			    <c:otherwise>
					<c:forEach var="aplist" items="${searchResult.entity}" varStatus="i">
						<c:choose>
				 			<c:when test="${i.count % 2 == 0}">
				 				<c:set var="className" value="bgWhite"/>
				 			</c:when>
				 			<c:otherwise>
				 				<c:set var="className" value="bgWhite"/>
				 			</c:otherwise>
				 		</c:choose>  
						<tr class="${className}">
							<td class="ellipsis" title="${aplist.groupName}">${fn:trim(aplist.groupName)}</td>
							<c:if test="${!empty searchCondition.ym1}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${aplist.cnt1}
										<c:set var="cntTot1" value="${aplist.cnt1 + cntTot1}"></c:set>
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${aplist.time1}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym2}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${aplist.cnt2}
										<c:set var="cntTot2" value="${aplist.cnt2 + cntTot2}"></c:set>
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${aplist.time2}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym3}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${aplist.cnt3}
										<c:set var="cntTot3" value="${aplist.cnt3 + cntTot3}"></c:set>
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${aplist.time3}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym4}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${aplist.cnt4}
										<c:set var="cntTot4" value="${aplist.cnt4 + cntTot4}"></c:set>
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${aplist.time4}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym5}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${aplist.cnt5}
										<c:set var="cntTot5" value="${aplist.cnt5 + cntTot5}"></c:set>
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${aplist.time5}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym6}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${aplist.cnt6}
										<c:set var="cntTot6" value="${aplist.cnt6 + cntTot6}"></c:set>
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${aplist.time6}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym7}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${aplist.cnt7}
										<c:set var="cntTot7" value="${aplist.cnt7 + cntTot7}"></c:set>
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${aplist.time7}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym8}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${aplist.cnt8}
										<c:set var="cntTot8" value="${aplist.cnt8 + cntTot8}"></c:set>
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${aplist.time8}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym9}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${aplist.cnt9}
										<c:set var="cntTot9" value="${aplist.cnt9 + cntTot9}"></c:set>
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${aplist.time9}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym10}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${aplist.cnt10}
										<c:set var="cntTot10" value="${aplist.cnt10 + cntTot10}"></c:set>
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${aplist.time10}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym11}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${aplist.cnt11}
										<c:set var="cntTot11" value="${aplist.cnt11 + cntTot11}"></c:set>
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${aplist.time11}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym12}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${aplist.cnt12}
										<c:set var="cntTot12" value="${aplist.cnt12 + cntTot12}"></c:set>
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${aplist.time12}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<td>&nbsp;
								<c:if test="${searchCondition.searchStatisType == '0'}">
									${aplist.cntSum}
									<c:set var="cntSumTot" value="${aplist.cntSum + cntSumTot}"></c:set>
								</c:if>
								<c:if test="${searchCondition.searchStatisType == '1'}">
									<fmt:formatNumber value="${aplist.timeSum}" pattern="0.#"/>
									<c:set var="timeSumTot" value="${aplist.timeSum + timeSumTot}"></c:set>
								</c:if>
							</td>
						</tr>
					</c:forEach>		
					
						<tr class="last">
							<td class="ellipsis" title="<ikep4j:message pre='${preSearch}' key='sum' />"><ikep4j:message pre='${preSearch}' key='sum' /></td>
							<c:if test="${!empty searchCondition.ym1}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${cntTot1}
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${timeTot1}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym2}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${cntTot2}
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${timeTot2}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym3}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${cntTot3}
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${timeTot3}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym4}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${cntTot4}
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${timeTot4}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym5}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${cntTot5}
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${timeTot5}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym6}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${cntTot6}
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${timeTot6}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym7}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${cntTot7}
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${timeTot7}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym8}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${cntTot8}
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${timeTot8}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym9}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${cntTot9}
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${timeTot9}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym10}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${cntTot10}
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${timeTot10}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym11}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${cntTot11}
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${timeTot11}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym12}">
								<td>&nbsp;
									<c:if test="${searchCondition.searchStatisType == '0'}">
										${cntTot12}
									</c:if>
									<c:if test="${searchCondition.searchStatisType == '1'}">
										<fmt:formatNumber value="${timeTot12}" pattern="0.#"/>
									</c:if>
								</td>
							</c:if>
							<td>&nbsp;
								<c:if test="${searchCondition.searchStatisType == '0'}">
									${cntSumTot}
								</c:if>
								<c:if test="${searchCondition.searchStatisType == '1'}">
									<fmt:formatNumber value="${timeSumTot}" pattern="0.#"/>
								</c:if>
							</td>
						</tr>
								      
			    </c:otherwise> 
			</c:choose>  
		</tbody>
	</table>
	
	<!--Page Numbur Start--> 
	<%-- <spring:bind path="searchCondition.pageIndex">
	<ikep4j:pagination searchButtonId="${status.expression}" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
	<input  id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>  --%>
	<!--//Page Numbur End-->
	
</div>
</form>
<!--//blockListTable End-->
