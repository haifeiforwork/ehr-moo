<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>  
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preTitle"   value="ui.approval.npd.list.title" />
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
			<!-- 의뢰일 -->
			<th scope="col" width="7%">
				<a onclick="sort('reqDate', '<c:if test="${searchCondition.sortColumn eq 'reqDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='reqDate' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'reqDate' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'reqDate' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			
			<!-- 의뢰부서 -->
			<th scope="col" width="7%">
				<a onclick="sort('reqDeptNm', '<c:if test="${searchCondition.sortColumn eq 'reqDeptNm'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='reqDeptId' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'reqDeptNm' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'reqDeptNm' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 의뢰자 -->
			<th scope="col" width="7%">
				<a onclick="sort('reqEmpNm', '<c:if test="${searchCondition.sortColumn eq 'reqEmpNm'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='reqEmpNo' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'reqEmpNm' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'reqEmpNm' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
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
			<th scope="col" width="10%">
				<a onclick="sort('custName', '<c:if test="${searchCondition.sortColumn eq 'custName'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='custName' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'custName' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'custName' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- TCS담당자 -->
			<th scope="col" width="7%">
				<a onclick="sort('fstReviewEmpNm', '<c:if test="${searchCondition.sortColumn eq 'fstReviewEmpNm'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='tcs' /> <br />
					<ikep4j:message pre='${preTitle}' key='tcsDraftEmpNo' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'fstReviewEmpNm' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'fstReviewEmpNm' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- TCS완료일 -->
			<th scope="col" width="7%">
				<a onclick="sort('fstReviewDate', '<c:if test="${searchCondition.sortColumn eq 'fstReviewDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='tcs' /> <br />
					<ikep4j:message pre='${preTitle}' key='tcsApprDate' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'fstReviewDate' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'fstReviewDate' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 회사코드 -->
			<th scope="col" width="7%">
				<a onclick="sort('companyCode', '<c:if test="${searchCondition.sortColumn eq 'companyCode'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='companyCode' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'companyCode' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'companyCode' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 2차검토부서 -->
			<th scope="col" width="7%">
				<a onclick="sort('sndReviewDeptNm', '<c:if test="${searchCondition.sortColumn eq 'sndReviewDeptNm'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='sndReview' /> <br />
					<ikep4j:message pre='${preTitle}' key='sndReviewDeptId' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'sndReviewDeptNm' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'sndReviewDeptNm' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 2차검토담당자 -->
			<th scope="col" width="7%">
				<a onclick="sort('sndRcvDraftEmpNm', '<c:if test="${searchCondition.sortColumn eq 'sndRcvDraftEmpNm'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='sndReview' /> <br />
					<ikep4j:message pre='${preTitle}' key='sndRcvDraftEmpNo' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'sndRcvDraftEmpNm' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'sndRcvDraftEmpNm' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			
			<!-- 2차검토완료일 -->
			<th scope="col" width="8%">
				<a onclick="sort('apprDate', '<c:if test="${searchCondition.sortColumn eq 'apprDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='sndReview' /> <br />
					<ikep4j:message pre='${preTitle}' key='sndRsltApprDate' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'apprDate' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'apprDate' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
		</tr>
	</thead>
	<!-- thead End --> 
	<tbody>
		<c:choose>
		    <c:when test="${searchResult.emptyRecord}">
				<tr>
					<td colspan="13" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
				</tr>				        
		    </c:when>
		    <c:otherwise>
				<c:forEach var="newProductDevList" items="${searchResult.entity}">
					<tr>
						<!-- No-->
						<td>
							<span>${newProductDevList.rNum}</span>
						</td>
						<!-- 진행상태  -->
						<td class="textLeft">
							<c:forEach var="code" items="${ procStatusList}">
								<c:if test="${code.comCd eq newProductDevList.processStatus }">${code.comNm}</c:if>
							</c:forEach>
						</td>
						<!-- 의뢰일 -->
						<td class="textLeft">
							${newProductDevList.reqDate }
						</td>
						<!-- 의뢰부서 -->
						<td class="textLeft">
							${newProductDevList.reqDeptNm }
						</td>
						<!-- 의뢰자 -->
						<td class="textLeft">
							${newProductDevList.reqEmpNm }
						</td>
						<!-- 제품명 -->
						<td class="textLeft">
							<a href="#" onClick="goView( '${newProductDevList.mgntNo}', '${newProductDevList.rsltFileReadYn}')" >${newProductDevList.productName }</a> 
						</td>
						<!-- 고객사명 -->
						<td class="textLeft">
							${newProductDevList.custName }
						</td>
						<!-- TCS담당자 -->
						<td class="textLeft">
							${newProductDevList.fstReviewEmpNm }
						</td>
						<!-- TCS완료일 -->
						<td class="textLeft">
							${newProductDevList.fstReviewDate }
						</td>
						<!-- 회사코드 -->
						<td class="textLeft">
							<c:forEach var="code" items="${ c00010List}">
								<c:if test="${code.comCd eq newProductDevList.companyCode }">${code.comNm}</c:if>
							</c:forEach>
						</td>
						<!-- 2차검토부서 -->
						<td class="textLeft">
							${newProductDevList.sndReviewDeptNm }
						</td>
						<!-- 2차검토담당자 -->
						<td class="textLeft">
							${newProductDevList.sndRcvDraftEmpNm }
						</td>
						<!-- 2차검토완료일 -->
						<td class="textLeft">
							${newProductDevList.apprDate }
						</td>
					</tr>
				</c:forEach>				      
		    </c:otherwise> 
		</c:choose>  
	</tbody>
</table>