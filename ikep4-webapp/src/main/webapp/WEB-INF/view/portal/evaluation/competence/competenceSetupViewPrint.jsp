<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<style>

@media print {
    @page {
      size: auto;
      margin: 10mm 10mm 0 10mm;
    }

    body {
	    margin: 0px;
	    padding: 0px;
	    -webkit-print-color-adjust: exact;
    }

    .wrap {
    	page-break-after: always;
    }

    .group_table {
    	page-break-inside: avoid;
    }
}

textarea {
	overflow-y : hidden;
}

</style>

<script type="text/javascript">
(function($){
	$(document).ready(function() {

		$('textarea').each(function() {
			$(this).AutoHeightResizeTextarea();
			$(this).css('background', '#ffffff');
		});

		window.print();

	});
})(jQuery);
</script>

<div class="wrap">

	<h1 class="title">${esHeader.AYEAR}년 역량평가</h1>

	<table class="list_table">
		<colgroup>
			<col style="width: 13%;">
			<col style="width: 20%;">
			<col style="width: 13%;">
			<col style="width: 20%;">
			<col style="width: 13%;">
			<col style="width: 20%;">
		</colgroup>
		<tbody>
			<tr>
				<th>성명/사번</th>
				<td class="text_left"><c:out value="${esHeader.APRSENM}" />(<c:out value="${esHeader.APRSE}" />)</td>
				<th>직책</th>
				<td class="text_left"><c:out value="${esHeader.COTXT}" /></td>
				<th>직급</th>
				<td class="text_left"><c:out value="${esHeader.TRFGR}" /></td>
			</tr>
			<tr>
				<th>소속</th>
				<td colspan="5" class="text_left"><c:out value="${esHeader.ORGTX}" /></td>
			</tr>
			<tr>
				<th>1차평가자</th>
				<td class="text_left"><c:out value="${esHeader.APRSRNM1}" /></td>
				<th>2차평가자</th>
				<td class="text_left"><c:out value="${esHeader.APRSRNM2}" /></td>
				<th>Reviewer</th>
				<td class="text_left"><c:out value="${esHeader.REVWRNM}" /></td>
			</tr>
		</tbody>
	</table>

	<div class="group_table">
		<h2 class="title margintop_20">공통역량</h2>
		<table class="contents_table">
			<colgroup>
			    <col style="width:20%;" />
			    <col style="width:35%;" />
			    <col style="width:45%;" />
			</colgroup>
			<thead>
				<tr>
					<th>역량명</th>
					<th>정의</th>
					<th>행동지표</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty commonList}">
					<c:forEach var="result" items="${commonList}">
						<tr>
							<td valign="top"><c:out value="${result.STEXT}" /></td>
							<td><textarea class="no_border width_100" rows="3" disabled><c:out value="${result.DFTXT}" /></textarea></td>
							<td><textarea class="no_border width_100" rows="3" disabled><c:out value="${result.BHIDX}" /></textarea></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>

	<div class="group_table">
		<h2 class="title margintop_20">직무역량</h2>
		<table class="contents_table">
			<colgroup>
			    <col style="width:20%;" />
			    <col style="width:35%;" />
			    <col style="width:45%;" />
			</colgroup>
			<thead>
				<tr>
					<th>역량명</th>
					<th>정의</th>
					<th>행동지표</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty stellList}">
					<c:forEach var="result" items="${stellList}">
						<tr>
							<td valign="top"><c:out value="${result.STEXT}" /></td>
							<td><textarea class="no_border width_100" rows="3" disabled><c:out value="${result.DFTXT}" /></textarea></td>
							<td><textarea class="no_border width_100" rows="3" disabled><c:out value="${result.BHIDX}" /></textarea></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>

</div>