<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>  
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preTitle"   value="ui.approval.proposal.list.title" />
<c:set var="preSearch"	value="message.collpack.kms.board.boardItem.listBoardView.searchCondition" />
<%-- 메시지 관련 Prefix 선언 End --%>

	<table summary="<ikep4j:message pre='${preTitle}' key='summary' />">  
	<caption></caption> 
	<!-- thead Start --> 
	<thead>
		<tr>
			<!-- No-->
			<th scope="col" width="5%">
				<ikep4j:message pre='${preTitle}' key='no' />
			</th>
			<!-- 작성일  -->
			<th scope="col" width="7%">
				<a onclick="sort('reqDate', '<c:if test="${searchCondition.sortColumn eq 'reqDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='reqDate' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'reqDate' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'reqDate' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 의뢰부서  -->
			<th scope="col" width="10%">
				<a onclick="sort('reqDeptId', '<c:if test="${searchCondition.sortColumn eq 'reqDeptId'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='reqDeptId' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'reqDeptId' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'reqDeptId' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			
			<!-- 의뢰자 -->
			<th scope="col" width="7%">
				<a onclick="sort('reqEmpNo', '<c:if test="${searchCondition.sortColumn eq 'reqEmpNo'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='reqEmpNo' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'reqEmpNo' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'reqEmpNo' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 제품명 -->
			<th scope="col" width="*">
				<a onclick="sort('productName', '<c:if test="${searchCondition.sortColumn eq 'productName'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='productName' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'productName' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'productName' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 고객사명 -->
			<th scope="col" width="20%">
				<a onclick="sort('custName', '<c:if test="${searchCondition.sortColumn eq 'custName'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='custName' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'custName' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'custName' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 접수자 -->
			<th scope="col" width="7%">
				<a onclick="sort('tcsRcvEmpNo', '<c:if test="${searchCondition.sortColumn eq 'tcsRcvEmpNo'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='tcsRcvEmpNo' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'tcsRcvEmpNo' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'tcsRcvEmpNo' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 담당자 -->
			<th scope="col" width="7%">
				<a onclick="sort('tcsRcvDate', '<c:if test="${searchCondition.sortColumn eq 'tcsRcvDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='tcsRcvDate' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'rcvEmpNm' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'rcvEmpNm' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
		</tr>
	</thead>
	<!-- thead End --> 
	<tbody>
		<c:choose>
		    <c:when test="${searchResult.emptyRecord}">
				<tr>
					<td colspan="8" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
				</tr>				        
		    </c:when>
		    <c:otherwise>
				<c:forEach var="proposalList" items="${searchResult.entity}">
					<tr>
						<!-- No-->
						<td>
							<span>${proposalList.rNum}</span>
						</td>
						<!-- 작성일 -->
						<td class="textLeft">
							${proposalList.reqDate }
						</td>
						<!-- 의뢰부서 -->
						<td class="textLeft">
							${proposalList.reqDeptNm }
						</td>
						<!-- 의뢰자 -->
						<td class="textLeft">
							${proposalList.reqEmpNm }
						</td>
						<!-- 제품명 -->
						<td class="textLeft">
							<a href="#" onClick="goView( '${proposalList.proposalNo}')" >${proposalList.productName }</a> 
						</td>
						<!-- 고객사명 -->
						<td class="textLeft">
							${proposalList.custName }
						</td>
						<!-- 접수자 -->
						<td class="textLeft">
							${proposalList.tcsRcvEmpNm }
						</td>
						<!-- 접수일자 -->
						<td class="textLeft">
							${proposalList.tcsRcvDate }
						</td>
					</tr>
				</c:forEach>				      
		    </c:otherwise> 
		</c:choose>  
	</tbody>
</table>