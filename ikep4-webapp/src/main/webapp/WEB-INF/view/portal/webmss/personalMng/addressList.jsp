<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<div class="list01">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" id="blktable">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="20%">주소유형</th>
				<th scope="col" width="*">주소</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty addressResult.ET_LIST}">
					<tr>
						<td colspan="2" class="emptyRecord center">해당 주소 내역이 없습니다.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="result" items="${addressResult.ET_LIST}">
						<tr>
							<td>
								<c:out value="${result.ZTYPE}"/>
							</td>
							<td class="f_left">
								<c:out value="${result.ZADDRESS}"/>
								<span class="rowInfo">
									<c:forEach items="${keySet}" var="key">
										<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${result[key]}"/>"/>
									</c:forEach>
								</span>
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		 </tbody>
	</table>
</div>