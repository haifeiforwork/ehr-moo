<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>  
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preTitle"   value="ui.approval.testreq.list.title" />
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
			<!-- 진행상태  -->
			<th scope="col" width="7%">
				<a onclick="sort('processStatus', '<c:if test="${searchCondition.sortColumn eq 'processStatus'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='processStatus' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'processStatus' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'processStatus' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 작성일 -->
			<th scope="col" width="7%">
				<a onclick="sort('writeDate', '<c:if test="${searchCondition.sortColumn eq 'writeDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='writeDate' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'writeDate' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'writeDate' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			
			<!-- 작성자 -->
			<th scope="col" width="7%">
				<a onclick="sort('writeEmpNm', '<c:if test="${searchCondition.sortColumn eq 'writeEmpNm'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='writeEmpNm' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'writeEmpNm' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'writeEmpNm' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 회사 -->
			<th scope="col" width="7%">
				<a onclick="sort('companyChkVal', '<c:if test="${searchCondition.sortColumn eq 'companyChkVal'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='companyChkVal' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'companyChkVal' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'companyChkVal' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 품목 -->
			<th scope="col" width="7%">
				<a onclick="sort('itemKindNm', '<c:if test="${searchCondition.sortColumn eq 'itemKindNm'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='itemKindNm' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'itemKindNm' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'itemKindNm' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 제목 -->
			<th scope="col" width="*">
				<a onclick="sort('testReqTitle', '<c:if test="${searchCondition.sortColumn eq 'testReqTitle'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='testReqTitle' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'testReqTitle' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'testReqTitle' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 주관부서 -->
			<th scope="col" width="7%">
				<a onclick="sort('rcvDeptNm', '<c:if test="${searchCondition.sortColumn eq 'rcvDeptNm'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='rcvDeptNm' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'rcvDeptNm' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'rcvDeptNm' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 담당자 -->
			<th scope="col" width="7%">
				<a onclick="sort('rcvEmpNm', '<c:if test="${searchCondition.sortColumn eq 'rcvEmpNm'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='rcvEmpNm' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'rcvEmpNm' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'rcvEmpNm' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 완료예정일 -->
			<th scope="col" width="7%">
				<a onclick="sort('completePlanDate', '<c:if test="${searchCondition.sortColumn eq 'completePlanDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='completePlanDate' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'completePlanDate' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'completePlanDate' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 관리번호 -->
			<th scope="col" width="7%">
				<a onclick="sort('trMgntNo', '<c:if test="${searchCondition.sortColumn eq 'trMgntNo'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='trMgntNo' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'trMgntNo' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'trMgntNo' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
		</tr>
	</thead>
	<!-- thead End --> 
	<tbody>
		<c:choose>
		    <c:when test="${searchResult.emptyRecord}">
				<tr>
					<td colspan="11" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
				</tr>				        
		    </c:when>
		    <c:otherwise>
				<c:forEach var="testRequestList" items="${searchResult.entity}">
					<tr>
						<!-- No-->
						<td>
							<span>${testRequestList.rNum}</span>
						</td>
						<!-- 진행상태  -->
						<td class="textLeft">
							<c:forEach var="code" items="${ procStatusList}">
								<c:if test="${code.comCd eq testRequestList.processStatus }">${code.comNm}</c:if>
							</c:forEach>
						</td>
						<!-- 작성일 -->
						<td class="textLeft">
							${testRequestList.writeDate }
						</td>
						<!-- 작성자 -->
						<td class="textLeft">
							${testRequestList.writeEmpNm }
						</td>
						<!-- 회사 -->
						<td class="textLeft">
							${testRequestList.companyChkVal }
						</td>
						<!-- 제품명 -->
						<td class="textLeft">
							${testRequestList.itemKindNm }
						</td>
						<!-- 제목 -->
						<td class="textLeft">
							<a href="#" onClick="goView( '${testRequestList.trMgntNo}')" >${testRequestList.testReqTitle }</a> 
						</td>
						<!-- 주관부서 -->
						<td class="textLeft">
							${testRequestList.rcvDeptNm }
						</td>
						<!-- 담당자 -->
						<td class="textLeft">
							${testRequestList.rcvEmpNm }
						</td>
						<!-- 완료예정일 -->
						<td class="textLeft">
							${testRequestList.completePlanDate }
						</td>
						<!-- 관리번호 -->
						<td class="textLeft">
							${testRequestList.trMgntNo }
						</td>
					</tr>
				</c:forEach>				      
		    </c:otherwise> 
		</c:choose>  
	</tbody>
</table>