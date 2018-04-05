<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<div class="list01" style="overflow-x:auto">
	<table border="0" cellpadding="0" cellspacing="0" style="width:1300px">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="80px">자격/면허</th>
				<th scope="col" width="50px">등급</th>
				<th scope="col" width="100px">자격/면허 번호</th>
				<th scope="col" width="80px">취득일</th>
				<th scope="col" width="90px">법정인정여부</th>
				
				<th scope="col" width="90px">수당지급여부</th>
				<th scope="col" width="80px">자격수당</th>
				<th scope="col" width="100px">자격수당 금액</th>
				<th scope="col" width="50px">통화</th>
				<th scope="col" width="120px">자격/면허 명</th>
				
				<th scope="col" width="50px">내역</th>
				<th scope="col" width="60px">발행처</th>
				<th scope="col" width="*">발행처 텍스트</th>
				<th scope="col" width="80px">시작일</th>
				<th scope="col" width="80px">종료일</th>
				
				<th scope="col" width="30px">&nbsp;</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty qualificationResult.ET_LIST}">
					<tr>
						<td colspan="16">자격사항 대한 정보가 없습니다.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="result" items="${qualificationResult.ET_LIST}">
						<tr>
							<td><c:out value="${result.LICCD}"/></td>
							<td><c:out value="${result.LICGR}"/></td>
							<td><c:out value="${result.LICNO}"/></td>
							<td>
								<fmt:parseDate var="dateString" value="${result.LICDT}" pattern="yyyyMMdd" />
								<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
							</td>
							<td><c:out value="${result.APPRV}"/></td>
							
							<td><c:out value="${result.ALLGN}"/></td>
							<td class="f_right"><c:out value="${result.BENFT}"/></td>
							<td class="f_right"><c:out value="${result.LICMT}"/></td>
							<td><c:out value="${result.WAERS}"/></td>
							<td><c:out value="${result.LICTX}"/></td>
							
							<td><c:out value="${result.LIGRT}"/></td>
							<td><c:out value="${result.PUBSH}"/></td>
							<td><c:out value="${result.PUBTX}"/></td>
							<td>
								<fmt:parseDate var="dateString" value="${result.BEGDA}" pattern="yyyyMMdd" />
								<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
							</td>
							<td>
								<fmt:parseDate var="dateString" value="${result.ENDDA}" pattern="yyyyMMdd" />
								<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
							</td>
							
							<td><c:out value="${result.LICMT_T}"/></td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		 </tbody>
	</table>
</div>