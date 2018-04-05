<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preApCommList"    value="ui.approval.work.apprlist" />
<c:set var="preButton"  			value="ui.approval.common.button" />
<c:set var="preSearch"  			value="ui.approval.common.searchCondition" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 <script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>
<c:choose>
	<c:when test="${searchCondition.listType eq 'rejectList'}">
		<c:set var="formActUrl"     value="/approval/work/apprlist/listApprReject.do" />
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
	
	//문서 결재 정보 조회
	getViewApprLine = function(apprId) {
			
		var $divContext = $jq("#"+apprId),
		$trRow = $jq("#apprLineTr"+apprId);
	
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
	getApprDetail = function(apprId) {
		$("#searchForm input[name=apprId]:hidden").val(apprId);
		$("#searchForm").attr("action","<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>");
		$("#searchForm").submit(); 
	};
	
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {
		
		<c:choose>
			<c:when test="${searchCondition.listType eq 'rejectList'}">
				$jq("#apprRejectOfLeft").click();
			</c:when>
		</c:choose>	
	
		$("#searchStartDate").datepicker().next().eq(0).click(function() { $("#searchStartDate").datepicker("show"); });
		$("#searchEndDate").datepicker().next().eq(0).click(function() { $("#searchEndDate").datepicker("show"); });
		
		$("#searchApListButton").click(function() {
			$("input[name=pageIndex]").val('1');
			$("#searchForm").submit(); 
			return false; 
		});
		
		$("#allCheck").click(function() { 
			$("#searchForm input[name=chkApprId]").attr("checked", $(this).is(":checked"));  
		});  
		
		$("#allDelete").click(function() {  
			var apprIds = new Array();
			
			$("#searchForm input[name=chkApprId]:checked").each(function(index) { 
				apprIds.push($(this).val()); 
			});			 
			
			if(apprIds == "") {
				alert("<ikep4j:message pre='${preApCommList}' key='checkDelete' />");
				return;
			}
			
			if(confirm("<ikep4j:message pre='${preApCommList}' key='confirmDelete' />")) {
				$.post("<c:url value='/approval/work/apprlist/deleteApprList.do'/>", {"apprIds" : apprIds.toString()}) 
				.success(function(result) {  
					if(result == "OK") {
						alert("<ikep4j:message pre='${preApCommList}' key='completeDelete' />");
						location.href="<c:url value='/approval/work/apprlist/listApprReject.do'/>";
					}
				})
			} 
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
 
		$("#listApButton").click(function() {   
			location.href='listApTempSearch.do';
		});
		
		$("#createApButton").click(function() {   
			location.href='createApForm.do';
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
					<spring:bind path="searchCondition.searchFormName">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
					<td width="25%">
						<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}" value="${status.value}" size="35" />
					</td>
					</spring:bind>
					<spring:bind path="searchCondition.searchApprDocStatus">
					<th scope="row" width="5%"><ikep4j:message pre='${preSearch}' key='${status.expression}' /></th>
					<td width="25%">
						<select title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" name="${status.expression}">
							<c:forEach var="apCode" items="${docStatusList}">
								<option value="${apCode.key}" <c:if test="${apCode.key eq status.value}">selected="selected"</c:if>><spring:message code="${apCode.value}" /></option>
							</c:forEach>
						</select>
					</td>
					</spring:bind>
				</tr>
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
				<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_03.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.reject'/>" /><ikep4j:message pre='${preApCommList}' key='display.reject'/>
				<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_04.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.return'/>" /><ikep4j:message pre='${preApCommList}' key='display.return'/>
			</div>			
		</div>			
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	<table summary="<ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' />" id="listTable">
		<caption></caption>
		<colgroup>
		<col width="5%"/>
		<col width="5%"/>
		<col width="10%"/>
		<col width="10%"/>
		<col width="40%"/>
		<col width="15%"/>
		<col width="15%"/>
		</colgroup>
		<thead>
			<tr>
				<th scope="col">
					<input name="allCheck" id="allCheck" class="checkbox" title="checkbox" type="checkbox" value="" />
				</th>
				<th scope="col"><ikep4j:message pre='${preSearch}' key='number' /></th>
				<th scope="col"><ikep4j:message pre='${preSearch}' key='apprDocType' /></th>
				<th scope="col"><ikep4j:message pre='${preSearch}' key='codeName' /></th>
				<th scope="col">
					<ikep4j:message pre='${preSearch}' key='apprTitle' />&nbsp;&nbsp;
					<a onclick="f_Sort('apprTitle', '<c:if test="${searchCondition.sortColumn eq 'apprTitle'}">${searchCondition.sortType}</c:if>');"  href="#a">
					</a>
					<c:choose>
					    <c:when test="${searchCondition.sortColumn eq 'apprTitle' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					    <c:when test="${searchCondition.sortColumn eq 'apprTitle' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
				    </c:choose>
				</th>
				<th scope="col">
				    <c:choose>
			 			<c:when test="${searchCondition.listType ne 'myRequestList'}">
							<a onclick="f_Sort('apprReqDate', '<c:if test="${searchCondition.sortColumn eq 'apprReqDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
							<ikep4j:message pre='${preSearch}' key='apprReqDate' />&nbsp;&nbsp;
							</a>
							<c:choose>
							    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
						    </c:choose>
			 			</c:when>
			 			<c:otherwise>
							<a onclick="f_Sort('registDate', '<c:if test="${searchCondition.sortColumn eq 'registDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
							<ikep4j:message pre='${preSearch}' key='apprReqDate' />&nbsp;&nbsp;
							</a>
							<c:choose>
							    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
						    </c:choose>
			 			</c:otherwise>
					</c:choose>					
				</th>
				<th scope="col"><ikep4j:message pre='${preSearch}' key='searchApprDocStatus' /></th>
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
							<td>
								<input type="checkbox" name="chkApprId" id="chkApprId" value="${aplist.apprId}" title="checkbox">
							</td>
							<td>${(searchCondition.recordCount-(searchCondition.pagePerRecord*(searchCondition.pageIndex-1))-i.count)+1}</td>
							<td>
								<c:if test="${aplist.apprDocType eq '0'}"><ikep4j:message pre='${preSearch}' key='apprDocType0' /></c:if>
								<c:if test="${aplist.apprDocType eq '1'}"><ikep4j:message pre='${preSearch}' key='apprDocType1' /></c:if>
							</td>
							<td>${aplist.codeName}</td>
							<td class="textLeft">
								<div class="ellipsis"><a href="#a" onclick="getApprDetail('${aplist.apprId}');" title="${aplist.apprTitle}">${aplist.apprTitle}</a></div>
							</td>
							<td><ikep4j:timezone pattern="yyyy.MM.dd hh:mm" date="${aplist.apprReqDate}"/></td>
					    
							<td>
								<c:choose>
									<c:when test="${aplist.apprDocStatus == '3'}">
										<a href="#a" onclick="getViewApprLine('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_03.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.reject'/>" /></a>
									</c:when>
									<c:when test="${aplist.apprDocStatus == '4'}">
										<a href="#a" onclick="getViewApprLine('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_04.png'/>" alt="<ikep4j:message pre='${preApCommList}' key='display.return'/>" /></a>
									</c:when>
								</c:choose>
							</td>							
						</tr>
						<tr style="display:none;" id="apprLineTr${aplist.apprId}">
							<td colspan="7">
								<div id="${aplist.apprId}" style="display:none;">
								</div>							
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

<div class="clear"></div>
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="allDelete"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete' /></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->	
