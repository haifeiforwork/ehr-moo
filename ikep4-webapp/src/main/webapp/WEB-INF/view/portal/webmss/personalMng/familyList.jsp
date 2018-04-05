<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<div class="list01">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="20%">관계</th>
				<th scope="col" width="20%">이름</th>
				<th scope="col" width="20%">생일</th>
				<th scope="col" width="20%">학력</th>
				<th scope="col" width="20%">동거여부</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty familyResult.ET_LIST}">
					<tr>
						<td colspan="5">조회된 내역이 없습니다.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="result" items="${familyResult.ET_LIST}">
						<tr>
							<td><c:out value="${result.ZKDSVH}"/></td>
							<td><c:out value="${result.ZNAME}"/></td>
							<td>
								<fmt:parseDate var="dateString" value="${result.FGBDT}" pattern="yyyyMMdd" />
								<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
							</td>
							<td><c:out value="${result.ZFASAR}"/></td>
							<td>
								<c:if test="${!empty result.ZLIVID }">
									<img src="<c:url value='/base/images/common/icon_v.png'/>"/>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		 </tbody>
	</table>
</div>