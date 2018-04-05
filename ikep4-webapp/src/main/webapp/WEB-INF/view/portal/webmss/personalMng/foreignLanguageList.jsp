<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<div class="list01">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="8%">구분</th>
				<th scope="col" width="12%">평가구분</th>
				<th scope="col" width="*">평가기관</th>
				<th scope="col" width="80px">평가일자</th>
				<th scope="col" width="12%">필기점수</th>
				<th scope="col" width="12%">회화점수</th>
				<th scope="col" width="12%">종합점수</th>
				<th scope="col" width="12%">평가등급</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty foreignLanguageResult.ET_LIST}">
					<tr>
						<td colspan="8">외국어 점수에 대한 정보가 없습니다.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="result" items="${foreignLanguageResult.ET_LIST}">
						<tr>
							<td><c:out value="${result.LNTXT}"/></td>
							<td><c:out value="${result.GNTXT}"/></td>
							<td class="f_left"><c:out value="${result.APRMN}"/></td>
							<td>
								<fmt:parseDate var="dateString" value="${result.APRDT}" pattern="yyyyMMdd" />
								<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
							</td>
							<td class="f_right"><c:out value="${result.WTPNT}"/></td>
							<td class="f_right"><c:out value="${result.TKPNT}"/></td>
							<td class="f_right"><c:out value="${result.TTPNT}"/></td>
							<td><c:out value="${result.TTGRDTXT}"/></td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		 </tbody>
	</table>
</div>