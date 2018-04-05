<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<div class="margin20_0 f_center">
	<table border="0" cellspacing="0" cellpadding="0" align="center">
		<tbody>
		<tr>
			<td class="f_right ">
				<c:if test="${params.leftDoubleArrow eq true }">
					<a href="#" class="paging_arrow" data-page="<c:out value="${params.startPage-1}"/>" name="page"><span><img src="<c:url value='/base/images/ess/paging_arrow_first.png'/>"/><img src="<c:url value='/base/images/ess/paging_arrow_first_over.png'/>" class="over"/></span></a>
				</c:if>
				<c:if test="${params.leftSingleArrow eq true }">
					<a href="#" class="paging_arrow" data-page="<c:out value="${params.currentPage-1}"/>" name="page"><span><img src="<c:url value='/base/images/ess/paging_arrow_l.png'/>"/><img src="<c:url value='/base/images/ess/paging_arrow_l_over.png'/>" class="over"/></span></a>
				</c:if>
			</td>
			<td class="f_center" style="padding:0 10px;">
				<c:forEach begin="${params.startPage }" end="${params.endPage}" step="1" varStatus="inx" var="result">
					<c:choose>
						<c:when test="${result eq params.currentPage}">
							<a href="#" class="paging active"><span><c:out value="${result}"/></span></a>
						</c:when>
						<c:otherwise>
							<a href="#" data-page="<c:out value="${result}"/>" name="page" class="paging"><span><c:out value="${result}"/></span></a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</td>
			<td class="f_left">
				<c:if test="${params.rightSingleArrow eq true }">
					<a href="#" class="paging_arrow" data-page="<c:out value="${params.currentPage+1}"/>" name="page"><span><img src="<c:url value='/base/images/ess/paging_arrow_r.png'/>"/><img src="<c:url value='/base/images/ess/paging_arrow_r_over.png'/>" class="over"/></span></a>
				</c:if>
				<c:if test="${params.rightDoubleArrow eq true }">
					<a href="#" class="paging_arrow" data-page="<c:out value="${params.endPage+1}"/>" name="page"><span><img src="<c:url value='/base/images/ess/paging_arrow_end.png'/>"/><img src="<c:url value='/base/images/ess/paging_arrow_end_over.png'/>" class="over"/></span></a>
				</c:if>
			</td>
		</tr>
		</tbody>
	</table>
</div>