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
	<c:when test="${searchCondition.listType eq 'listApprRequestExam'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprRequestExam.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprCompleteExam'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprCompleteExam.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprExam'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprExam.do" />
	</c:when>
	<c:otherwise>
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprRequestExam.do" />
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
	
	//문서 결재 정보 조회
	getViewApprLine = function(apprId) {
		
		var $divContext = $jq("#"+apprId+"_${user.userId}"),
		$trRow = $jq("#apprLineTr"+apprId+"_${user.userId}");
		
		if($divContext.is(":hidden")) {
			
			$jq("#listTable").ajaxLoadStart("button");
			
			$jq("tr[id^=apprLineTr]").each(function() { 
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
				.error(function(event, request, settings) { alert("error"); $jq("#listTable").ajaxLoadComplete();});
		}else {
			$divContext.empty();
			$trRow.css("display","block");
			$divContext.hide();
			$trRow.hide();
		}
		
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
		
		//left 메뉴
		<c:choose>
			<c:when test="${searchCondition.listType eq 'listApprRequestExam'}">
				$jq("#apprRequestExamOfLeft").click();
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprCompleteExam'}">
				$jq("#apprCompleteExamOfLeft").click();
			</c:when>
			<c:when test="${searchCondition.listType eq 'listApprExam'}">
				$jq("#apprExamOfLeft").click();
			</c:when>
			<c:otherwise>
				$jq("#apprRequestExamOfLeft").click();
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
					<c:choose>
						<c:when test="${searchCondition.listType eq 'listApprRequestExam' or searchCondition.listType eq 'listApprCompleteExam'}">
							<spring:bind path="searchCondition.searchReqUserName">
							<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='searchReqUserId' /></th>
							<td width="25%">
								<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='searchReqUserId' />" name=searchReqUserName id="searchReqUserName" value="${status.value}" size="35"/>&nbsp;
							</td>
							</spring:bind>
						</c:when>
						<c:otherwise>
							<spring:bind path="searchCondition.searchFormName">
							<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
							<td width="25%">
								<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}" value="${status.value}" size="35" />
							</td>
							</spring:bind>
						</c:otherwise>
					</c:choose>
					<spring:bind path="searchCondition.searchUserName">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='searchUserId' /></th>
					<td width="25%">
						<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='searchUserId' />" name="searchUserName" id="searchUserName" value="<c:out value="${status.value}"/>" size="35"/>
					</td>
					</spring:bind>
				</tr>
				<tr>					
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='examReqDate' /></th>
					<td width="25%">
						<spring:bind path="searchCondition.searchStartDate">
							<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" /> ~
						</spring:bind>
						<spring:bind path="searchCondition.searchEndDate">
							<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" />
						</spring:bind>							
					</td>
					<c:choose>
						<c:when test="${searchCondition.listType eq 'listApprRequestExam'}">
							<spring:bind path="searchCondition.searchApprTitle">
							<th scope="row"><ikep4j:message pre='${preSearch}' key='apprTitle'/></th>
							<td width="25%">								
								<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='apprTitle'/>" name="${status.expression}" value="${status.value}" size="35" />
							</td>		
							</spring:bind>	
						</c:when>
						<c:when test="${searchCondition.listType eq 'listApprExam'}">
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
					</c:choose>
					<c:choose>
						<c:when test="${searchCondition.listType eq 'listApprCompleteExam' or searchCondition.listType eq 'listApprExam'}">
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
				<c:if test="${searchCondition.listType eq 'listApprExam'}">
				<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_01.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.progress'/>" /><ikep4j:message pre='${preApCommList}' key='display.progress'/>
				<c:if test="${searchCondition.listType eq 'listApprCompleteExam' or searchCondition.listType eq 'listApprExam'}">
				<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_02.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.complete'/>" /><ikep4j:message pre='${preApCommList}' key='display.complete'/>
				</c:if>
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
		<col width="5%"/>
		<col width="10%"/>
		<col width="30%"/>
		<col width="10%"/>
		<col width="15%"/>
		<col width="15%"/>
		<col width="15%"/>
		</colgroup>
		<thead>
			<tr>
				<th scope="col"><ikep4j:message pre='${preSearch}' key='number' /></th>
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
				<th scope="col"><ikep4j:message pre='${preSearch}' key='searchReqUserId' /></th>
				<th scope="col">
					<a onclick="f_Sort('examReqDate', '<c:if test="${searchCondition.sortColumn eq 'examReqDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preSearch}' key='examReqDate' />&nbsp;&nbsp;
					</a>
					<c:choose>
					    <c:when test="${searchCondition.sortColumn eq 'examReqDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					    <c:when test="${searchCondition.sortColumn eq 'examReqDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
				    </c:choose>
				</th>
				<th scope="col">
					<c:choose>
			 			<c:when test="${searchCondition.listType eq 'listApprRequestExam'}">
			 				<ikep4j:message pre='${preSearch}' key='searchUserId' />
			 			</c:when>
			 			<c:when test="${searchCondition.listType eq 'listApprCompleteExam' or searchCondition.listType eq 'listApprExam'}">
							<a onclick="f_Sort('examDate', '<c:if test="${searchCondition.sortColumn eq 'examDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preSearch}' key='examDate' />&nbsp;&nbsp;
							</a>
							<c:choose>
							    <c:when test="${searchCondition.sortColumn eq 'examDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							    <c:when test="${searchCondition.sortColumn eq 'examDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
						    </c:choose>
			 			</c:when>
					</c:choose>
				</th>
				<th scope="col">
				    <c:choose>
			 			<c:when test="${searchCondition.listType eq 'listApprRequestExam'}">
							<a onclick="f_Sort('apprReqDate', '<c:if test="${searchCondition.sortColumn eq 'apprReqDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preSearch}' key='apprReqDate' />&nbsp;&nbsp;
							</a>
							<c:choose>
							    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
						    </c:choose>
			 			</c:when>
			 			<c:when test="${searchCondition.listType eq 'listApprCompleteExam' or searchCondition.listType eq 'listApprExam'}">
			 				<ikep4j:message pre='${preSearch}' key='searchApprDocStatus' />
			 			</c:when>
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
							<td>${(searchCondition.recordCount-(searchCondition.pagePerRecord*(searchCondition.pageIndex-1))-i.count)+1}</td>
							<td>${aplist.codeName}</td>
							<td class="textLeft">
								<div class="ellipsis"><a href="#a" onclick="getApprDetail('${aplist.apprId}');" title="${aplist.apprTitle}">${aplist.apprTitle}</a></div>
							</td>
							<td>${aplist.examReqName}</td>
							<td><ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.examReqDate}"/></td>
							<td>
								<c:choose>
									<c:when test="${searchCondition.listType eq 'listApprRequestExam'}">
										<span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${aplist.registerId}', 'bottom')">${aplist.registerName}</a></span>
									</c:when>
									<c:when test="${searchCondition.listType eq 'listApprCompleteExam' or searchCondition.listType eq 'listApprExam'}">
										<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.examDate}"/>
									</c:when>
								</c:choose>	
							</td>
							<td>
								<c:choose>
									<c:when test="${searchCondition.listType eq 'listApprRequestExam'}">
										<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprReqDate}"/>
									</c:when>
									<c:when test="${searchCondition.listType eq 'listApprCompleteExam' or searchCondition.listType eq 'listApprExam'}">
										<c:choose>
											<c:when test="${aplist.apprDocStatus == '1'}">
												<a href="#a" onclick="getViewApprLine('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_01.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.progress'/>" /></a>
											</c:when>
											<c:when test="${aplist.apprDocStatus == '2'}">
												<a href="#a" onclick="getViewApprLine('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_02.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.complete'/>" /></a>
											</c:when>
											<c:when test="${aplist.apprDocStatus == '3'}">
												<a href="#a" onclick="getViewApprLine('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_03.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.reject'/>" /></a>
											</c:when>
										</c:choose>
									</c:when>
								</c:choose>	
							</td>							
						</tr>
						<c:choose>
							<c:when test="${searchCondition.listType eq 'listApprCompleteExam' or searchCondition.listType eq 'listApprExam'}">
								<tr style="display:none;" id="apprLineTr${aplist.apprId}_${user.userId}">
									<td colspan="7">
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
