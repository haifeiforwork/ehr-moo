<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>  
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preTitle"   value="ui.approval.userauthmgnt.list.title" />
<c:set var="preSearch"	value="message.collpack.kms.board.boardItem.listBoardView.searchCondition" />
<%-- 메시지 관련 Prefix 선언 End --%>

	<table summary="<ikep4j:message pre='${preTitle}' key='summary' />">  
	<caption></caption> 
	<!-- thead Start --> 
	<thead>
		<tr>
			<!-- 업무구분 -->
			<th scope="col" width="5%">
				<input id="checkboxAll" class="checkbox" title="checkbox" type="checkbox" value=""  style="display:none;"/>
				<ikep4j:message pre='${preTitle}' key='no' />
			</th>
			<th scope="col" width="10%">
				<a onclick="sort('workGbnCd', '<c:if test="${searchCondition.sortColumn eq 'workGbnCd'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='workGbnCd' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'workGbnCd' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'workGbnCd' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 부서 -->
			<th scope="col" width="25%">
				<a onclick="sort('groupName', '<c:if test="${searchCondition.sortColumn eq 'groupName'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='dept' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'groupName' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'groupName' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			
			<!-- 사용자 -->
			<th scope="col" width="*">
				<a onclick="sort('userName', '<c:if test="${searchCondition.sortColumn eq 'userName'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='user' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'userName' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'userName' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 승인자여부 -->
			<th scope="col" width="10%">
				<a onclick="sort('apprYn', '<c:if test="${searchCondition.sortColumn eq 'apprYn'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='apprYn' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'apprYn' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'apprYn' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 결과파일읽기권한 -->
			<th scope="col" width="10%">
				<a onclick="sort('rsltFileReadYn', '<c:if test="${searchCondition.sortColumn eq 'rsltFileReadYn'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='rsltFileReadYn' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'rsltFileReadYn' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'rsltFileReadYn' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<!-- 사용여부 -->
			<th scope="col" width="10%">
				<a onclick="sort('useYn', '<c:if test="${searchCondition.sortColumn eq 'useYn'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preTitle}' key='useYn' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'useYn' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'useYn' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
		</tr>
	</thead>
	<!-- thead End --> 
	<tbody>
		<c:choose>
		    <c:when test="${searchResult.emptyRecord}">
				<tr>
					<td colspan="7" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
				</tr>				        
		    </c:when>
		    <c:otherwise>
				<c:forEach var="userAuthMgntList" items="${searchResult.entity}">
					<tr>
						<td>
							<input name="boardItemIds" class="checkbox" title="checkbox" type="checkbox" value="" style="display:none;"/>
							<span>${userAuthMgntList.rNum}</span>
							<input type="hidden" name="workGbnCd" value="${userAuthMgntList.workGbnCd }" />
							<input type="hidden" name="deptId" value="${userAuthMgntList.deptId }" />
							<input type="hidden" name="empNo" value="${userAuthMgntList.empNo }" />
						</td>
					<!-- 업무구분 -->
						<td class="textLeft">
							<c:forEach var="code" items="${ workGnbCdList}">
								<c:if test="${code.comCd eq userAuthMgntList.workGbnCd }">${code.comNm}</c:if>
							</c:forEach>
						</td>
					<!-- 부서 -->
						<td class="textLeft">
							<a href="#" onclick="openModify('${userAuthMgntList.workGbnCd}', '${userAuthMgntList.empNo}', '${userAuthMgntList.deptId}' )"><span>${userAuthMgntList.groupName}( ${userAuthMgntList.deptId} ) </span> </a>
						</td>
					<!-- 사용자 -->
						<td class="textCenter">
							<a href="#" onclick="openModify('${userAuthMgntList.workGbnCd}', '${userAuthMgntList.empNo}', '${userAuthMgntList.deptId}' )">${userAuthMgntList.userName}( ${userAuthMgntList.empNo} ) </a>
						</td>
					<!-- 승인자여부 -->
						<td class="textCenter">
							${userAuthMgntList.apprYn}
						</td>
					<!-- 결과파일 읽기 권한여부 -->
						<td class="textCenter">
							${userAuthMgntList.rsltFileReadYn}
						</td>
					<!-- 사용여부 -->
						<td class="textCenter">
							${userAuthMgntList.useYn}
						</td>
					</tr>
				</c:forEach>				      
		    </c:otherwise> 
		</c:choose>  
	</tbody>
</table>