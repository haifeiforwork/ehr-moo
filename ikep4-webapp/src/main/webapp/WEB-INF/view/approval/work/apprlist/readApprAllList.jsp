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
<c:choose>
	<c:when test="${searchCondition.listType eq 'listApprIntegrate'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/readApprAllList.do" />
	</c:when>
</c:choose>	
			 
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
	
	//문서 상세 정보
	getApprDetail = function(apprId) {
		$("#searchForm input[name=apprId]:hidden").val(apprId);
		$("#searchForm").attr("action","<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>");
		$("#searchForm").submit(); 
	};
	
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {
		
		$jq("#apprIntegrateOfLeft").click();
	
		$("#searchStartDate").datepicker().next().eq(0).click(function() { $("#searchStartDate").datepicker("show"); });
		$("#searchEndDate").datepicker().next().eq(0).click(function() { $("#searchEndDate").datepicker("show"); });
		
		$("#searchApListButton").click(function() {
			$("input[name=pageIndex]").val('1');
			$("#searchForm").submit(); 
			return false; 
		});
		
		/**
		 * 페이징 버튼
		 */
		$("#pageIndex").click(function() {
			$("#searchForm").submit(); 
			return false; 
		});
		
		$("select[name=pagePerRecord]").change(function(){
            $("#pageIndex").click();
        });
		
	});
})(jQuery);  
//-->
</script>
			
<h1 class="none"><ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' /></h2>
	<%-- <div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.3depth" /></li>
			<li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.4depth" /></li>
		</ul>
	</div> --%>
</div>
<!--//pageTitle End-->

<!--blockSearch Start-->
<form id="searchForm" method="post" action="<c:url value='${formActUrl}' />">
	<spring:bind path="searchCondition.apprId">
		<input name="${status.expression}" type="hidden" value="" title="hidden" />
	</spring:bind>
	<spring:bind path="searchCondition.listType">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="hidden" />
	</spring:bind>
	<input name="apprIds" type="hidden" value="" title="hidden" />
	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 

<div class="blockSearch">
	<div class="corner_RoundBox03">
		<table summary="<ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' />">
			<caption></caption>
			<tbody>
				<tr>
					<spring:bind path="searchCondition.searchListType">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
					<td colspan="3">
						<select title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}">
							<c:forEach var="apCode" items="${typeList}">
								<option value="${apCode.key}" <c:if test="${apCode.key eq status.value}">selected="selected"</c:if>><spring:message code="${apCode.value}" /></option>
							</c:forEach>
						</select>
					</td>
					</spring:bind>
				</tr>
				<tr>
					<spring:bind path="searchCondition.searchFormName">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
					<td width="25%">
						<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}" value="${status.value}" size="35" />
					</td>
					</spring:bind>
					<spring:bind path="searchCondition.searchUserName">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='searchUserId' /></th>
					<td width="25%">
						<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='searchUserId' />" name="searchUserName" id="searchUserName" value="${status.value}" size="35" />&nbsp;
					</td>
					</spring:bind>
				</tr>
				<tr>
					<th scope="row" width="5%">
						<ikep4j:message pre='${preSearch}' key='apprReqDate' />		
					</th>
					<td width="25%">
						<spring:bind path="searchCondition.searchStartDate">
							<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" /> ~
						</spring:bind>
						<spring:bind path="searchCondition.searchEndDate">
							<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" />
						</spring:bind>							
					</td>
					<spring:bind path="searchCondition.searchGroupId">
					<th scope="row"><ikep4j:message pre='${preSearch}' key='${status.expression}'/></th>
					<td width="25%">
					<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
						<c:forEach var="item" items="${groupList}">
							<option value="${item.groupId}" <c:if test="${item.groupId eq status.value}">selected="selected"</c:if>><c:if test="${user.localeCode eq 'ko'}">${item.groupName}</c:if><c:if test="${user.localeCode ne 'ko'}">${item.groupEnglishName}</c:if></option>
						</c:forEach> 
					</select>
					</td>
					</spring:bind>
				</tr>
				<tr>		
					<spring:bind path="searchCondition.searchApprDocNo">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='searchApprDocNo'/></th>
					<td width="25%">								
						<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='searchApprDocNo'/>" name="${status.expression}" value="${status.value}" size="35" />
					</td>		
					</spring:bind>			
					<spring:bind path="searchCondition.searchApprTitle">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='apprTitle'/></th>
					<td width="25%">								
						<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='apprTitle'/>" name="${status.expression}" value="${status.value}" size="35" />
					</td>		
					</spring:bind>		
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
  <ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' />
</h1>

<!--blockListTable Start-->
<div class="blockListTable">

	<!--tableTop Start-->
	<div class="tableTop">
		<div class="tableTop_bgR"></div> 
		<div class="listInfo">
			<spring:bind path="searchCondition.pagePerRecord">
			<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<c:forEach var="commonCode" items="${commonCode}">
					<option value="${commonCode.key}" <c:if test="${commonCode.key eq status.value}">selected="selected"</c:if>>${commonCode.value}</option>
				</c:forEach> 
			</select> 
			</spring:bind>
			<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
			<div align="right">
				
			</div>			
		</div>			
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	<table summary="<ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' />" id="listTable">
		<caption></caption>
		<colgroup>
		<col width="13%"/>
		<col width="8%"/>
		<col width="8%"/>
		<col width="34%"/>
		<col width="9%"/>
		<col width="14%"/>
		<col width="14%"/>
		</colgroup>
		<thead>
			<tr>
				<th scope="col"><ikep4j:message pre='${preSearch}' key='searchApprDocNo'/></th>
				<th scope="col"><ikep4j:message pre='${preSearch}' key='apprStatus' /></th>
				<th scope="col"><ikep4j:message pre='${preSearch}' key='codeName' /></th>
				<th scope="col">
					<a onclick="f_Sort('apprTitle', '<c:if test="${searchCondition.sortColumn eq 'apprTitle'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preSearch}' key='apprTitle' />&nbsp;&nbsp;
					</a>
					<c:choose>
					    <c:when test="${searchCondition.sortColumn eq 'apprTitle' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					    <c:when test="${searchCondition.sortColumn eq 'apprTitle' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
				    </c:choose>
				</th>
				<th scope="col"><ikep4j:message pre='${preSearch}' key='searchUserId' /></th>
				<th scope="col">
				    <a onclick="f_Sort('apprReqDate', '<c:if test="${searchCondition.sortColumn eq 'apprReqDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preSearch}' key='apprReqDate' />&nbsp;&nbsp;
					</a>
					<c:choose>
					    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
				    </c:choose>				
				</th>
				<th scope="col">
					<a onclick="f_Sort('apprEndDate', '<c:if test="${searchCondition.sortColumn eq 'apprEndDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preSearch}' key='apprEndDate' />&nbsp;&nbsp;
					</a>
					<c:choose>
					    <c:when test="${searchCondition.sortColumn eq 'apprEndDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					    <c:when test="${searchCondition.sortColumn eq 'apprEndDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
				    </c:choose>
				</th>
			</tr>
		</thead> 
		<tbody>
			<c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<td colspan="7" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
					</tr>
			    </c:when>
			    <c:otherwise>
					<c:forEach var="aplist" items="${searchResult.entity}" varStatus="i">
						<c:choose>
				 			<c:when test="${i.count % 2 == 0}">
				 				<c:set var="className" value="bgWhite"/>
				 			</c:when>
				 			<c:otherwise>
				 				<c:set var="className" value="bgSelected"/>
				 			</c:otherwise>
				 		</c:choose>  
						<tr class="${className}">
							<td><div class="ellipsis" title="${aplist.apprDocNo}" style="cursor:default;">${aplist.apprDocNo}</div></td>
							<td>
								<c:choose>
									<c:when test="${aplist.mListType eq 'APPR_AD' or searchCondition.searchListType eq 'appr_ad'}">
										<ikep4j:message pre='${preSearch}' key='apprAd' />
									</c:when>
									<c:when test="${aplist.mListType eq 'APPR_AL' or searchCondition.searchListType eq 'appr_al'}">
										<ikep4j:message pre='${preSearch}' key='apprAl' />
									</c:when>
									<c:when test="${aplist.mListType eq 'APPR_AL2' or searchCondition.searchListType eq 'appr_al2'}">
										<ikep4j:message pre='${preSearch}' key='apprAl2' />
									</c:when>
									<c:when test="${aplist.mListType eq 'APPR_AR' or searchCondition.searchListType eq 'appr_ar'}">
										<ikep4j:message pre='${preSearch}' key='apprAr' />
									</c:when>
									<c:when test="${aplist.mListType eq 'APPR_ARE' or searchCondition.searchListType eq 'appr_are'}">
										<ikep4j:message pre='${preSearch}' key='apprAre' />
									</c:when>
									<c:when test="${aplist.mListType eq 'APPR_DEPT' or searchCondition.searchListType eq 'appr_dept'}">
										<ikep4j:message pre='${preSearch}' key='apprDept' />
									</c:when>
								</c:choose>
							</td>
							<td>${aplist.codeName}</td>
							<td class="textLeft">
								<div class="ellipsis"><a href="#a" onclick="getApprDetail('${aplist.apprId}');" title="${aplist.apprTitle}">${aplist.apprTitle}</a></div>
							</td>
							<td><span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${aplist.registerId}', 'bottom')">${aplist.registerName}</a></span></td>
							<td>
								<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprReqDate}"/>
							</td>
							<td>
								<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprEndDate}"/>	
							</td>
						</tr>
					</c:forEach>				      
			    </c:otherwise> 
			</c:choose>  
		</tbody>
	</table>
	<!--Page Numbur Start--> 
	<spring:bind path="searchCondition.pageIndex">
	<ikep4j:pagination searchButtonId="${status.expression}" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
	<input  id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<!--//Page Numbur End-->
	
</div>
</form>
<!--//blockListTable End-->
