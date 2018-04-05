<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<div class="list01">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="25%">근무회사</th>
				<th scope="col" width="16%">근무기간</th>
				<th scope="col" width="16%">근무부서</th>
				<th scope="col" width="*">담당업무</th>
				<th scope="col" width="16%">최종직위</th>
				<th scope="col" width="10%">연봉</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty careerResult.ET_LIST}">
					<tr>
						<td colspan="6">해당 경력사항이 없습니다.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="result" items="${careerResult.ET_LIST}">
						<tr>
							<td><c:out value="${result.ARBGB}"/></td>
							<td><c:out value="${result.PERIOD}"/></td>
							<td><c:out value="${result.ZZORG}"/></td>
							<td><c:out value="${result.ZZJOB}"/></td>
							<td><c:out value="${result.ZZJGD}"/></td>
							<td class="f_right"><c:out value="${result.SALARY}"/></td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		 </tbody>
	</table>
</div>