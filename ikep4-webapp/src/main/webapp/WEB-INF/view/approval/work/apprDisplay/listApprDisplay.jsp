<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preHeader"      value="ui.approval.work.apprDisplay" />
<c:set var="preSearch"  	value="ui.approval.common.searchCondition" />
<c:set var="preDisplaySearch"  	value="ui.approval.work.apprDisplay.searchCondition" />
<%-- 메시지 관련 Prefix 선언 End --%>

<c:choose>
	<c:when test="${searchCondition.listType eq 'listApprDisplayWaiting'}">
		<c:set var="formActUrl"     value="/approval/work/apprDisplay/listApprDisplayWaiting.do" />
	</c:when>
	<c:when test="${searchCondition.listType eq 'listApprDisplayComplete'}">
		<c:set var="formActUrl"     value="/approval/work/apprDisplay/listApprDisplayComplete.do" />
	</c:when>
</c:choose>	

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
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
	
	(function($){
		
		f_Sort = function(sortColumn, sortType) {
			$("input[name=sortColumn]").val(sortColumn);
			
			if(sortType == '') sortType = 'ASC';
			
			if( sortType == 'ASC') {
				$("input[name=sortType]").val('DESC');	
			} else {
				$("input[name=sortType]").val('ASC');
			}
			
			$("#searchListButton").click();
		};
		
		getApprDetail = function(apprId) {
			$("#searchForm input[name=apprId]:hidden").val(apprId);
			$("#searchForm").attr("action","<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>");
			$("#searchForm").submit(); 
		};
		
		$(document).ready(function(){
			
			<c:choose>
				<c:when test="${searchCondition.listType eq 'listApprDisplayWaiting'}">
					$jq("#apprDisplayWaitingOfLeft").click();
				</c:when>
				<c:when test="${searchCondition.listType eq 'listApprDisplayComplete'}">
					$jq("#apprDisplayCompleteOfLeft").click();
				</c:when>
			</c:choose>	
			
			$("#searchStartDate").datepicker().next().eq(0).click(function() { $("#searchStartDate").datepicker("show"); });
			$("#searchEndDate").datepicker().next().eq(0).click(function() { $("#searchEndDate").datepicker("show"); });
			
			$("#searchListButton").click(function() {
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
<h1 class="none"><ikep4j:message pre='${preHeader}' key='${searchCondition.listType}' /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="${searchCondition.listType}" /></h2> 
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

<form id="searchForm" name="searchForm" method="post" action="<c:url value='${formActUrl}' />" >

	<spring:bind path="searchCondition.apprId">
		<input name="${status.expression}" type="hidden" value="" title="hidden" />
	</spring:bind>
	<spring:bind path="searchCondition.listType">
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
			<table summary="<ikep4j:message pre="${preHeader}" key="${searchCondition.listType}" />">
				<caption></caption>
				<tbody>
					<tr>					
						<th scope="row" width="5%"><ikep4j:message pre='${preDisplaySearch}' key='sRegistDate' /></th>
						<td width="35%">
							<spring:bind path="searchCondition.searchStartDate">
								<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" /> ~
							</spring:bind>
							<spring:bind path="searchCondition.searchEndDate">
								<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" value="${status.value}" size="10" /> <img class="dateicon" src="/ikep4-webapp/base/images/icon/ic_cal.gif" alt="달력" />
							</spring:bind>							
						</td>
						<spring:bind path="searchCondition.searchApprTitle">
						<th scope="row" width="5%"><ikep4j:message pre='${preDisplaySearch}' key='apprTitle'/></th>
						<td>								
							<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='apprTitle'/>" name="${status.expression}" value="${status.value}" size="35" />
						</td>		
						</spring:bind>		
					</tr>
				</tbody>
			</table>
			<div class="searchBtn">
				<a id="searchListButton" name="searchListButton" href="#a"><img src="<c:url value='/base/images/theme/theme01/basic/btn_search.gif' />" alt="검색" /></a>
			</div>
			
			<div class="l_t_corner"></div>
			<div class="r_t_corner"></div>
			<div class="l_b_corner"></div>
			<div class="r_b_corner"></div>
		</div>
	</div>	
	<!--//blockSearch End-->
		
	<!--blockDetail Start-->
	<div class="blockListTable">
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
		<table summary="<ikep4j:message pre='${preHeader}' key='title' />">
		<caption></caption>
		<colgroup>
		<c:if test="${searchCondition.listType eq 'listApprDisplayWaiting'}">
		<col width="5%"/>
		<col width="10%"/>
		<col width="10%"/>
		<col width="45%"/>
		<col width="15%"/>
		<col width="15%"/>
		</c:if>
		<c:if test="${searchCondition.listType eq 'listApprDisplayComplete'}">
		<col width="5%"/>
		<col width="10%"/>
		<col width="45%"/>
		<col width="10%"/>
		<col width="15%"/>
		<col width="15%"/>
		</c:if>
		</colgroup>
		<thead>
			<tr>
				<th scope="col"><ikep4j:message pre='${preDisplaySearch}' key='number' /></th>
				<th scope="col">
					<c:choose>
			 			<c:when test="${searchCondition.listType eq 'listApprDisplayWaiting'}">
			 				<ikep4j:message pre='${preDisplaySearch}' key='apprDocType' />
			 			</c:when>
			 			<c:otherwise>
			 				<ikep4j:message pre='${preDisplaySearch}' key='codeName' />
			 			</c:otherwise>
			 		</c:choose>
				</th>
				<th scope="col">
					<c:choose>
			 			<c:when test="${searchCondition.listType eq 'listApprDisplayWaiting'}">
			 				<ikep4j:message pre='${preDisplaySearch}' key='codeName' />
			 			</c:when>
			 			<c:otherwise>
							<a onclick="f_Sort('apprTitle', '<c:if test="${searchCondition.sortColumn eq 'apprTitle'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preDisplaySearch}' key='apprTitle' />&nbsp;&nbsp;
							</a>
							<c:choose>
							    <c:when test="${searchCondition.sortColumn eq 'apprTitle' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							    <c:when test="${searchCondition.sortColumn eq 'apprTitle' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
						    </c:choose>
			 			</c:otherwise>
			 		</c:choose>
				</th>
				<th scope="col">
					<c:choose>
			 			<c:when test="${searchCondition.listType eq 'listApprDisplayWaiting'}">
							<a onclick="f_Sort('apprTitle', '<c:if test="${searchCondition.sortColumn eq 'apprTitle'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preDisplaySearch}' key='apprTitle' />&nbsp;&nbsp;
							</a>
							<c:choose>
							    <c:when test="${searchCondition.sortColumn eq 'apprTitle' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							    <c:when test="${searchCondition.sortColumn eq 'apprTitle' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
						    </c:choose>
			 			</c:when>
			 			<c:otherwise>
			 				<ikep4j:message pre='${preDisplaySearch}' key='displayUserId' />
			 			</c:otherwise>
			 		</c:choose>
				</th>
				<th scope="col">
					<c:choose>
			 			<c:when test="${searchCondition.listType eq 'listApprDisplayWaiting'}">
			 				<ikep4j:message pre='${preDisplaySearch}' key='displayUserId' />
			 			</c:when>
			 			<c:otherwise>
			 				<a onclick="f_Sort('dRegistDate', '<c:if test="${searchCondition.sortColumn eq 'dRegistDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preDisplaySearch}' key='dRegistDate' />&nbsp;&nbsp;
							</a>
							<c:choose>
							    <c:when test="${searchCondition.sortColumn eq 'dRegistDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							    <c:when test="${searchCondition.sortColumn eq 'dRegistDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
						    </c:choose>
			 			</c:otherwise>
			 		</c:choose>
				</th>
				<th scope="col">
					<c:choose>
			 			<c:when test="${searchCondition.listType eq 'listApprDisplayWaiting'}">
							<a onclick="f_Sort('dRegistDate', '<c:if test="${searchCondition.sortColumn eq 'dRegistDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preDisplaySearch}' key='dRegistDate' />&nbsp;&nbsp;
							</a>
							<c:choose>
							    <c:when test="${searchCondition.sortColumn eq 'dRegistDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							    <c:when test="${searchCondition.sortColumn eq 'dRegistDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
						    </c:choose>
			 			</c:when>
			 			<c:otherwise>
							<a onclick="f_Sort('confirmDate', '<c:if test="${searchCondition.sortColumn eq 'confirmDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preDisplaySearch}' key='confirmDate' />&nbsp;&nbsp;
							</a>
							<c:choose>
							    <c:when test="${searchCondition.sortColumn eq 'confirmDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							    <c:when test="${searchCondition.sortColumn eq 'confirmDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
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
						<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
					</tr>
			    </c:when>
			    <c:otherwise>
					<c:forEach var="apdisplay" items="${searchResult.entity}" varStatus="i">
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
							<td>
								<c:choose>
						 			<c:when test="${searchCondition.listType eq 'listApprDisplayWaiting'}">
						 				<c:if test="${apdisplay.apprDocType eq '0'}"><ikep4j:message pre='${preSearch}' key='apprDocType0' /></c:if>
										<c:if test="${apdisplay.apprDocType eq '1'}"><ikep4j:message pre='${preSearch}' key='apprDocType1' /></c:if>
						 			</c:when>
						 			<c:otherwise>
						 				${apdisplay.codeName}
						 			</c:otherwise>
						 		</c:choose>
							</td>
							<c:choose>
					 			<c:when test="${searchCondition.listType eq 'listApprDisplayWaiting'}">
					 				<td>${apdisplay.codeName}</td>
					 			</c:when>
					 			<c:otherwise>
					 				<td class="textLeft">
					 					<div class="ellipsis"><a href="#a" onclick="getApprDetail('${apdisplay.apprId}');">${apdisplay.apprTitle}</a></div>
					 				</td>
					 			</c:otherwise>
					 		</c:choose>
					 		<c:choose>
					 			<c:when test="${searchCondition.listType eq 'listApprDisplayWaiting'}">
					 				<td class="textLeft"><div class="ellipsis"><a href="#a" onclick="getApprDetail('${apdisplay.apprId}');">${apdisplay.apprTitle}</a></div></td>
					 			</c:when>
					 			<c:otherwise>
					 				<td><span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${apdisplay.registerId}', 'bottom')">${apdisplay.registerName}</a></span></td>
					 			</c:otherwise>
					 		</c:choose>
							<td>
								<c:choose>
						 			<c:when test="${searchCondition.listType eq 'listApprDisplayWaiting'}">
						 				<span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${apdisplay.registerId}', 'bottom')">${apdisplay.registerName}</a></span>
						 			</c:when>
						 			<c:otherwise>
						 				<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${apdisplay.dRegistDate}"/>&nbsp;
						 			</c:otherwise>
						 		</c:choose>
							</td>
							<td>
								<c:choose>
						 			<c:when test="${searchCondition.listType eq 'listApprDisplayWaiting'}">
						 				<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${apdisplay.dRegistDate}"/>&nbsp;
						 			</c:when>
						 			<c:otherwise>
						 				<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${apdisplay.confirmDate}"/>&nbsp;
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
	<ikep4j:pagination searchButtonId="${status.expression}" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
	<input  id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<!--//Page Numbur End-->
	
</div>
</form>
<!--//blockListTable End-->	
