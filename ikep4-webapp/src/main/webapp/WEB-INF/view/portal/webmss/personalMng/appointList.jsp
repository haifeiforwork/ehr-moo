<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<div class="search_box">
	<table border="0">
		<tbody>
			<tr>
				<td><span class="f_333">발령유형선택</span></td>
				<td>
					<label for="00"><input type="radio" name="appointType" value="00" id="00"/>&nbsp;전체</label>&nbsp;&nbsp;&nbsp;
					<label for="B5"><input type="radio" name="appointType" value="B5" id="B5"/>&nbsp;이동</label>&nbsp;&nbsp;&nbsp;
					<label for="B2"><input type="radio" name="appointType" value="B2" id="B2"/>&nbsp;승격</label>&nbsp;&nbsp;&nbsp;
					<label for="B1"><input type="radio" name="appointType" value="B1" id="B1"/>&nbsp;승진</label>&nbsp;&nbsp;&nbsp;
					<label for="B3"><input type="radio" name="appointType" value="B3" id="B3"/>&nbsp;보직</label>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<div class="button_box"></div>

<div class="list01">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="9%">발령유형</th>
				<th scope="col" width="9%">발령사유</th>
				<th scope="col" width="9%">발령일자</th>
				<th scope="col" width="15%">소속</th>
				<th scope="col" width="8%">직책</th>
				<th scope="col" width="9%">직급/직위</th>
				<th scope="col" width="9%">사원구분</th>
				<th scope="col" width="9%">직무</th>
				<th scope="col" width="9%">포지션</th>
				<th scope="col" width="*">겸직/겸무정보</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty appointResult.ET_LIST}">
					<tr>
						<td colspan="10">조회된 내역이 없습니다.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="result" items="${appointResult.ET_LIST}">
						<tr>
							<td><c:out value="${result.MNTXT}"/></td>
							<td><c:out value="${result.MGTXT}"/></td>
							<td>
								<fmt:parseDate var="dateString" value="${result.BEGDA}" pattern="yyyyMMdd" />
								<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
							</td>
							<td class="f_left"><c:out value="${result.ORGTX}"/></td>
							<td><c:out value="${result.COTXT}"/></td>
							<td><c:out value="${result.ZTRJI}"/></td>
							<td><c:out value="${result.PKTXT}"/></td>
							<td><c:out value="${result.STLTX}"/></td>
							<td><c:out value="${result.PLSTX}"/></td>
							<td class="f_left"><c:out value="${result.ZZDLP}"/></td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		 </tbody>
	</table>
</div>