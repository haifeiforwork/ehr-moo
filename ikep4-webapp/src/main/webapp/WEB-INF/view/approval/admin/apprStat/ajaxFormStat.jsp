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

<c:set var="formActUrl"     value="/approval/admin/apprStat/formStat.do" />

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
<c:set var="cntSumTot"     value="0" />

<script type="text/javascript">
<!-- 
(function($) {
	
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {
		
		
	});
})(jQuery);  
//-->
</script>

<h1 class="none"><ikep4j:message pre='${preApCommList}.pageTitle' key='docStat' /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2></h2>
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

<div class="blockSearch">
	<div class="corner_RoundBox03">
		<table summary="<ikep4j:message pre='${preApCommList}.pageTitle' key='userStat' />">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="5%">
						<ikep4j:message pre='${preSearch}' key='searchPeriod' />	
					</th>
					<td colspan="3">
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
			<a href="#a" onclick="readFormList();return false;"><img src="<c:url value='/base/images/theme/theme01/basic/btn_search.gif' />" alt="검색" /></a>
		</div>
		
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
		<div class="l_b_corner"></div>
		<div class="r_b_corner"></div>
	</div>
</div>	
<!--//blockSearch End-->
<h1 class="none">
</h1>
<c:set var="rowCnt" value="0"></c:set>
<!--blockListTable Start-->
<div class="blockListTable">

	<!--tableTop Start-->
	<div class="tableTop">
		<div class="tableTop_bgR"></div> 
		<div class="listInfo">
			<div class="totalNum"></div>
			<div align="right">
				<ikep4j:message pre='${preSearch}' key='searchStatisType00' /> &nbsp;&nbsp;
				<a href="#a" onclick="excelDownload();return false;" id="excel_Download" title="excel download"><img src="<c:url value='/base/images/icon/ic_xls.gif'/>" alt="excel download" /></a>
			</div>			
		</div>			
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	<table summary="<ikep4j:message pre='${preApCommList}.pageTitle' key='userStat' />" id="statistics_listTable">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col">
					<ikep4j:message pre='${preSearch}' key='searchFormName' />&nbsp;&nbsp;
				</th>
				<c:if test="${!empty searchCondition.ym1}">
					<c:set var="rowCnt" value="${rowCnt+1}"></c:set>
					<th scope="col">${fn:substring(searchCondition.ym1,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym2}">
					<c:set var="rowCnt" value="${rowCnt+1}"></c:set>
					<th scope="col">${fn:substring(searchCondition.ym2,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym3}">
					<c:set var="rowCnt" value="${rowCnt+1}"></c:set>
					<th scope="col">${fn:substring(searchCondition.ym3,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym4}">
					<c:set var="rowCnt" value="${rowCnt+1}"></c:set>
					<th scope="col">${fn:substring(searchCondition.ym4,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym5}">
					<c:set var="rowCnt" value="${rowCnt+1}"></c:set>
					<th scope="col">${fn:substring(searchCondition.ym5,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym6}">
					<c:set var="rowCnt" value="${rowCnt+1}"></c:set>
					<th scope="col">${fn:substring(searchCondition.ym6,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym7}">
					<c:set var="rowCnt" value="${rowCnt+1}"></c:set>
					<th scope="col">${fn:substring(searchCondition.ym7,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym8}">
					<c:set var="rowCnt" value="${rowCnt+1}"></c:set>
					<th scope="col">${fn:substring(searchCondition.ym8,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym9}">
					<c:set var="rowCnt" value="${rowCnt+1}"></c:set>
					<th scope="col">${fn:substring(searchCondition.ym9,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym10}">
					<c:set var="rowCnt" value="${rowCnt+1}"></c:set>
					<th scope="col">${fn:substring(searchCondition.ym10,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym11}">
					<c:set var="rowCnt" value="${rowCnt+1}"></c:set>
					<th scope="col">${fn:substring(searchCondition.ym11,5,7)}&nbsp;</th>
				</c:if>
				<c:if test="${!empty searchCondition.ym12}">
					<c:set var="rowCnt" value="${rowCnt+1}"></c:set>
					<th scope="col">${fn:substring(searchCondition.ym12,5,7)}&nbsp;</th>
				</c:if>
				<th scope="col">
					<ikep4j:message pre='${preSearch}' key='sum' />
				</th>
			</tr>
		</thead> 
		<tbody> 
			<c:choose>
			    <c:when test="${fn:length(searchResult.entity) eq 0}">
						<tr>
							<td colspan="${rowCnt+2}" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
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
								<td class="textLeft">
									<div class="ellipsis">${aplist.formName}</div>
								</td>
							<c:if test="${!empty searchCondition.ym1}">
								<td>
									&nbsp;${aplist.cnt1}
									<c:set var="cntTot1" value="${aplist.cnt1 + cntTot1}"></c:set>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym2}">
								<td>
									${aplist.cnt2}
									<c:set var="cntTot2" value="${aplist.cnt2 + cntTot2}"></c:set>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym3}">
								<td>
									${aplist.cnt3}
									<c:set var="cntTot3" value="${aplist.cnt3 + cntTot3}"></c:set>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym4}">
								<td>
									${aplist.cnt4}
									<c:set var="cntTot4" value="${aplist.cnt4 + cntTot4}"></c:set>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym5}">
								<td>
									${aplist.cnt5}
									<c:set var="cntTot5" value="${aplist.cnt5 + cntTot5}"></c:set>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym6}">
								<td>
									${aplist.cnt6}
									<c:set var="cntTot6" value="${aplist.cnt6 + cntTot6}"></c:set>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym7}">
								<td>
									${aplist.cnt7}
									<c:set var="cntTot7" value="${aplist.cnt7 + cntTot7}"></c:set>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym8}">
								<td>
									${aplist.cnt8}
									<c:set var="cntTot8" value="${aplist.cnt8 + cntTot8}"></c:set>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym9}">
								<td>
									${aplist.cnt9}
									<c:set var="cntTot9" value="${aplist.cnt9 + cntTot9}"></c:set>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym10}">
								<td>
									${aplist.cnt10}
									<c:set var="cntTot10" value="${aplist.cnt10 + cntTot10}"></c:set>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym11}">
								<td>
									${aplist.cnt11}
									<c:set var="cntTot11" value="${aplist.cnt11 + cntTot11}"></c:set>
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym12}">
								<td>
									${aplist.cnt12}
									<c:set var="cntTot12" value="${aplist.cnt12 + cntTot12}"></c:set>
								</td>
							</c:if>
							<td>
								${aplist.totSum}
								<c:set var="cntSumTot" value="${aplist.totSum + cntSumTot}"></c:set>
							</td>
						</tr>
					</c:forEach>		
					
						<tr class="last">
							
								<td class="textCenter title="<ikep4j:message pre='${preSearch}' key='sum' />">
									<ikep4j:message pre='${preSearch}' key='sum' />
								</td>
							<c:if test="${!empty searchCondition.ym1}">
								<td>
									&nbsp;${cntTot1}
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym2}">
								<td>
									${cntTot2}
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym3}">
								<td>
									${cntTot3}
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym4}">
								<td>
									${cntTot4}
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym5}">
								<td>
									${cntTot5}
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym6}">
								<td>
									${cntTot6}
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym7}">
								<td>
									${cntTot7}
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym8}">
								<td>
									${cntTot8}
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym9}">
								<td>
									${cntTot9}
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym10}">
								<td>
									${cntTot10}
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym11}">
								<td>
									${cntTot11}
								</td>
							</c:if>
							<c:if test="${!empty searchCondition.ym12}">
								<td>
									${cntTot11}
								</td>
							</c:if>
							<td>
								${cntSumTot}
							</td>
						</tr>
								      
			    </c:otherwise> 
			</c:choose>  
		</tbody>
	</table>
</div>
</form>
<!--//blockListTable End-->
