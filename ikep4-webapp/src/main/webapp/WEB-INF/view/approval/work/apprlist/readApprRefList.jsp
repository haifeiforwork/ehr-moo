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
	<c:when test="${searchCondition.listType eq 'listApprReference'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprReference.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprCompleteRef'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprCompleteRef.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprReading'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprReading.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprReadingAssign'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprReadingAssign.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprDelegate'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprDelegate.do" />
	</c:when>
	<c:otherwise>
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprReference.do" />
	</c:otherwise>
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
	
	/**
	 * 위임 설정 화면 오픈.
	 */
	viewUnitDelegateDialog = function (userId) {
		
		viewApFormProcessDialog = new iKEP.Dialog({     
			title 		: "<ikep4j:message key='ui.approval.work.entrust.form.subTitle'/>",
			url 		: "<c:url value='/approval/work/config/updateUnitEntrustForm.do'/>?userId="+userId,
			modal 		: true,
			width 		: 650,
			height 		: 200,
			params 		: {userId:userId}
		});
	};
	
	//문서 결재 정보 조회
	getViewApprLine = function(apprId) {
		
		var $divContext = $jq("#"+apprId+"_${user.userId}"),
			$trRow = $jq("#apprLineTr"+apprId+"_${user.userId}");
	
		if($divContext.is(":hidden")) {
			
			$jq("#listTable").ajaxLoadStart("button");
			
			$jq("tr[id^=apprLineTr]^").each(function() { 
				var rowApprid = $(this).find("div").attr("id");
				
				$jq("#"+rowApprid).empty();
				$jq("#apprLineTr"+rowApprid).css("display","block");
				$jq("#"+rowApprid).hide();
				$jq("#apprLineTr"+rowApprid).hide();
			});
			
			$.post("<c:url value='/approval/work/apprLine/listApprLineInfo.do'/>", {apprId	:	apprId})
				.success(function(result) {
					$divContext.append(result);
					$trRow.css("display","");
					$divContext.show();
					$jq("#listTable").ajaxLoadComplete();
				})
				.error(function(event, request, settings) { alert("error");$jq("#listTable").ajaxLoadComplete(); });
		}else {
			$divContext.empty();
			$trRow.css("display","block");
			$divContext.hide();
			$trRow.hide();
		}
		
	};
	
	//문서 상세 정보
	getApprDetail = function(apprId,entrustUserId) {
		$("#searchForm input[name=apprId]:hidden").val(apprId);
		$("#searchForm input[name=entrustUserId]:hidden").val(entrustUserId);
		$("#searchForm").attr("action","<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>");
		$("#searchForm").submit(); 
	};
	
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {
		
		//left 메뉴
		<c:choose>
			<c:when test="${searchCondition.listType eq 'listApprReference'}">
				$jq("#apprReferenceOfLeft").click();
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprCompleteRef'}">
				$jq("#apprCompleteRefOfLeft").click();
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprReading'}">
				$jq("#apprReadingOfLeft").click();
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprReadingAssign'}">
				$jq("#apprReadingAssignOfLeft").click();
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprDelegate'}">
				$jq("#apprDelegateOfLeft").click();
			</c:when>
			<c:otherwise>
				$jq("#apprReferenceOfLeft").click();
			</c:otherwise>
		</c:choose>	
		
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
	<spring:bind path="searchCondition.entrustUserId">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="hidden" />
	</spring:bind>
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
					<spring:bind path="searchCondition.searchFormName">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
					<td width="25%">
						<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}" value="${status.value}" size="35" />
					</td>
					</spring:bind>
					<c:choose>
						<c:when test="${searchCondition.listType eq 'listApprReference'}">
							<spring:bind path="searchCondition.searchApprDocStatus">
							<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
							<td width="25%">
								<select title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}">
									<c:forEach var="apCode" items="${docStatusList}">
										<c:if test="${apCode.key ne '4'}">
										<option value="${apCode.key}" <c:if test="${apCode.key eq status.value}">selected="selected"</c:if>><spring:message code="${apCode.value}" /></option>
										</c:if>
									</c:forEach>
								</select>
							</td>
							</spring:bind>
						</c:when>
						<c:when test="${searchCondition.listType eq 'listApprDelegate'}">
							<spring:bind path="searchCondition.searchApprDocStatus">
							<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
							<td width="25%">
								<select title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}">
									<c:forEach var="apCode" items="${docStatusList}">
										<c:if test="${apCode.key ne '4'}">
										<option value="${apCode.key}" <c:if test="${apCode.key eq status.value}">selected="selected"</c:if>><spring:message code="${apCode.value}" /></option>
										</c:if>
									</c:forEach>
								</select>
							</td>
							</spring:bind>
						</c:when>
						<c:when test="${searchCondition.listType eq 'listApprReadingAssign'}">
							<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='apprReqDate' /></th>
							<td width="25%">
								<spring:bind path="searchCondition.searchStartDate">
									<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" /> ~
								</spring:bind>
								<spring:bind path="searchCondition.searchEndDate">
									<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" />
								</spring:bind>							
							</td>
						</c:when>
						<c:otherwise>
							<spring:bind path="searchCondition.searchUserName">
							<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='searchUserId' /></th>
							<td width="25%">
								<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='searchUserId' />" name="searchUserName" id="searchUserName" value="<c:out value="${status.value}"/>" size="35" />
							</td>
							</spring:bind>
						</c:otherwise>
					</c:choose>
				</tr>
				<c:choose>
					<c:when test="${searchCondition.listType ne 'listApprReadingAssign'}">
						<tr>
							<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='apprReqDate' /></th>
							<td width="25%">
								<spring:bind path="searchCondition.searchStartDate">
									<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" /> ~
								</spring:bind>
								<spring:bind path="searchCondition.searchEndDate">
									<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" />
								</spring:bind>							
							</td>
							<c:choose>
								<c:when test="${searchCondition.listType eq 'listApprReference' or searchCondition.listType eq 'listApprDelegate'}">
									<spring:bind path="searchCondition.searchUserName">
									<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='searchUserId' /></th>
									<td width="25%">
										<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='searchUserId' />" name="searchUserName" id="searchUserName" value="<c:out value="${status.value}"/>" size="35" />
									</td>
									</spring:bind>
								</c:when>
								<c:otherwise>
									<spring:bind path="searchCondition.searchApprTitle">
									<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='apprTitle'/></th>
									<td width="25%">								
										<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='apprTitle'/>" name="${status.expression}" value="${status.value}" size="35" />
									</td>		
									</spring:bind>	
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${searchCondition.listType eq 'listApprDelegate'}">
									<tr>
										<spring:bind path="searchCondition.searchApprTitle">
										<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='apprTitle'/></th>
										<td width="25%">								
											<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='apprTitle'/>" name="${status.expression}" value="${status.value}" size="35" />
										</td>		
										</spring:bind>	
										<spring:bind path="searchCondition.searchEntrustUserName">
										<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='searchEntrustUserId' /></th>
										<td width="25%">
											<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='searchEntrustUserId' />" name="searchEntrustUserName" id="searchEntrustUserName" value="<c:out value="${status.value}"/>" size="35"/>
										</td>
										</spring:bind>
									</tr>
								</c:when>
							</c:choose>
						</tr>	
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${searchCondition.listType eq 'listApprReference' or searchCondition.listType eq 'listApprReadingAssign'}">
						<tr>
							<spring:bind path="searchCondition.searchApprTitle">
							<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='apprTitle'/></th>
							<td colspan="3">								
								<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='apprTitle'/>" name="${status.expression}" value="${status.value}" size="35" />
							</td>		
							</spring:bind>	
						</tr>
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${searchCondition.listType eq 'listApprCompleteRef'}">
						<tr>					
							<spring:bind path="searchCondition.searchApprDocNo">
							<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='searchApprDocNo'/></th>
							<td colspan="3">								
								<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='searchApprDocNo'/>" name="${status.expression}" value="${status.value}" size="35" />
							</td>		
							</spring:bind>		
						</tr>
					</c:when>
				</c:choose>						
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
				<c:if test="${searchCondition.listType eq 'listApprReference' or searchCondition.listType eq 'listApprDelegate'}">
				<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_01.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.progress'/>" /><ikep4j:message pre='${preApCommList}' key='display.progress'/>
				<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_02.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.complete'/>" /><ikep4j:message pre='${preApCommList}' key='display.complete'/>
				<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_03.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.reject'/>" /><ikep4j:message pre='${preApCommList}' key='display.reject'/>
				</c:if>
			</div>			
		</div>			
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	<table summary="<ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' />" id="listTable">
		<caption></caption>
		<colgroup>
		<c:choose>
			<c:when test="${searchCondition.listType eq 'listApprReadingAssign'}">
				<col width="5%"/>
				<col width="10%"/>
				<col width="10%"/>
				<col width="45%"/>
				<col width="15%"/>
				<col width="15%"/>
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprDelegate'}">
				<col width="5%"/>
				<col width="10%"/>
				<col width="10%"/>
				<col width="30%"/>
				<col width="10%"/>
				<col width="10%"/>
				<col width="15%"/>
				<col width="10%"/>
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprCompleteRef'}">
				<col width="10%"/>
				<col width="10%"/>
				<col width="10%"/>
				<col width="25%"/>
				<col width="15%"/>
				<col width="15%"/>
				<col width="15%"/>
			</c:when>
			<c:otherwise>
				<col width="5%"/>
				<col width="10%"/>
				<col width="10%"/>
				<col width="30%"/>
				<col width="15%"/>
				<col width="15%"/>
				<col width="15%"/>
			</c:otherwise>
		</c:choose>		
		</colgroup>
		<thead>
			<tr>
				<th scope="col">
					<c:choose>
						<c:when test="${searchCondition.listType eq 'listApprCompleteRef'}">
							<ikep4j:message pre='${preSearch}' key='apprDocNo' />
						</c:when>
						<c:otherwise>
							<ikep4j:message pre='${preSearch}' key='number' />
						</c:otherwise>
					</c:choose>
				</th>
				<th scope="col"><ikep4j:message pre='${preSearch}' key='apprDocType' /></th>
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
				<c:choose>
					<c:when test="${searchCondition.listType eq 'listApprDelegate'}">
						<th scope="col"><ikep4j:message pre='${preSearch}' key='searchEntrustUserId' /></th>
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${searchCondition.listType ne 'listApprReadingAssign'}">
						<th scope="col"><ikep4j:message pre='${preSearch}' key='searchUserId' /></th>
					</c:when>
				</c:choose>
				<th scope="col">
					<c:choose>
			 			<c:when test="${searchCondition.listType eq 'listApprDelegate' or searchCondition.listType eq 'listApprReference' or searchCondition.listType eq 'listApprCompleteRef' or searchCondition.listType eq 'listApprReadingAssign'}">
						    <c:choose>
					 			<c:when test="${searchCondition.listType eq 'listApprTodo'}">
									<a onclick="f_Sort('apprReqDate', '<c:if test="${searchCondition.sortColumn eq 'apprReqDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preSearch}' key='apprReqDate' />&nbsp;&nbsp;
									</a>
									<c:choose>
									    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								    </c:choose>
					 			</c:when>
					 			<c:otherwise>
									<a onclick="f_Sort('apprReqDate', '<c:if test="${searchCondition.sortColumn eq 'apprReqDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preSearch}' key='apprReqDate' />&nbsp;&nbsp;
									</a>
									<c:choose>
									    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								    </c:choose>
					 			</c:otherwise>
							</c:choose>					
			 			</c:when>
			 			<c:otherwise>
							<ikep4j:message pre='${preSearch}' key='groupId' />
			 			</c:otherwise>
					</c:choose>
				</th>
				<th scope="col">
					<c:choose>
			 			<c:when test="${searchCondition.listType eq 'listApprReference' or searchCondition.listType eq 'listApprDelegate'}">
							<ikep4j:message pre='${preSearch}' key='searchApprDocStatus' />
						</c:when>
			 			<c:otherwise>
							<a onclick="f_Sort('apprEndDate', '<c:if test="${searchCondition.sortColumn eq 'apprEndDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
							<ikep4j:message pre='${preSearch}' key='apprEndDate' />&nbsp;&nbsp;
							</a>
							<c:choose>
							    <c:when test="${searchCondition.sortColumn eq 'apprEndDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							    <c:when test="${searchCondition.sortColumn eq 'apprEndDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
						    </c:choose>
			 			</c:otherwise>
					</c:choose>		
				</th>
			</tr>
		</thead> 
		<tbody>	
			<c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<c:choose>
							<c:when test="${searchCondition.listType eq 'listApprReadingAssign'}">
								<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>		
							</c:when>
							<c:when test="${searchCondition.listType eq 'listApprDelegate'}">
								<td colspan="8" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>		
							</c:when>
							<c:otherwise>
								<td colspan="7" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
							</c:otherwise>
						</c:choose>
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
							<td>
								<c:choose>
									<c:when test="${searchCondition.listType eq 'listApprCompleteRef'}">
										<div class="ellipsis" title="${aplist.apprDocNo}" style="cursor:default;">${aplist.apprDocNo}&nbsp;</div>
									</c:when>
									<c:otherwise>
										${(searchCondition.recordCount-(searchCondition.pagePerRecord*(searchCondition.pageIndex-1))-i.count)+1}
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:if test="${aplist.apprDocType eq '0'}"><ikep4j:message pre='${preSearch}' key='apprDocType0' /></c:if>
								<c:if test="${aplist.apprDocType eq '1'}"><ikep4j:message pre='${preSearch}' key='apprDocType1' /></c:if>
							</td>
							<td>${aplist.codeName}</td>
							<td class="textLeft">
								<div class="ellipsis"><a href="#a" onclick="getApprDetail('${aplist.apprId}','${aplist.approvalId}');" title="${aplist.apprTitle}">${aplist.apprTitle}</a></div>
							</td>
							<c:choose>
								<c:when test="${searchCondition.listType eq 'listApprDelegate'}">
									<td><span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${aplist.entrustUserId}', 'bottom')">${aplist.entrustUserName}</a></span></td>
								</c:when>
							</c:choose>
							<c:choose>
								<c:when test="${searchCondition.listType ne 'listApprReadingAssign'}">
									<td><span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${aplist.registerId}', 'bottom')">${aplist.registerName}</a></span></td>
								</c:when>
							</c:choose>
							<td>
								<c:choose>
						 			<c:when test="${searchCondition.listType eq 'listApprDelegate' or searchCondition.listType eq 'listApprReference' or searchCondition.listType eq 'listApprCompleteRef' or searchCondition.listType eq 'listApprReadingAssign'}">
										<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprReqDate}"/>
						 			</c:when>
						 			<c:otherwise>
										${aplist.teamName}
						 			</c:otherwise>
								</c:choose>
							</td>
							<c:choose>
								<c:when test="${searchCondition.listType eq 'listApprReference' or searchCondition.listType eq 'listApprDelegate'}">
									<c:choose>
										<c:when test="${aplist.apprDocStatus == '1'}">
											<td><a href="#a" onclick="getViewApprLine('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_01.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.progress'/>" /></a></td>
										</c:when>
										<c:when test="${aplist.apprDocStatus == '2'}">
											<td><a href="#a" onclick="getViewApprLine('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_02.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.complete'/>" /></a></td>
										</c:when>
										<c:when test="${aplist.apprDocStatus == '3'}">
											<td><a href="#a" onclick="getViewApprLine('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_03.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.reject'/>" /></a></td>
										</c:when>
									</c:choose>
								</c:when>
								<c:otherwise>
									<td><ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprEndDate}"/></td>
					 			</c:otherwise>
							</c:choose>	
						</tr>
						<c:choose>
							<c:when test="${searchCondition.listType eq 'listApprComplete' or searchCondition.listType eq 'listApprReference' or searchCondition.listType eq 'listApprDelegate'}">
								<tr style="display:none;" id="apprLineTr${aplist.apprId}_${user.userId}">
								<c:choose>
									<c:when test="${searchCondition.listType eq 'listApprReadingAssign'}">
										<td colspan="6">		
									</c:when>
									<c:when test="${searchCondition.listType eq 'listApprDelegate'}">
										<td colspan="8">		
									</c:when>
									<c:otherwise>
										<td colspan="7">
									</c:otherwise>
								</c:choose>
										<div id="${aplist.apprId}_${user.userId}" style="display:none;">
										</div>							
									</td>
								</tr>
							</c:when>
						</c:choose>	
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
