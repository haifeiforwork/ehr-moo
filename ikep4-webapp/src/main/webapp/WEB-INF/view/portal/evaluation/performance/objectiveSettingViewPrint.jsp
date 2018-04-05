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

	<h1 class="title">${esHeader.AYEAR}년 업적평가(MBO)</h1>

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
		<h2 class="title margintop_20">정량</h2>
		<table class="contents_table">
			<colgroup>
				<col style="width:30%;" />
				<col style="width:30%;" />
				<col style="width:30%;" />
				<col style="width:10%;" />
			</colgroup>
			<thead>
				<tr>
					<th>실행계획/담당업무</th>
					<th>세부내용/산식</th>
					<th>목표</th>
					<th>가중치(%)</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty qualList}">
					<c:forEach var="result" items="${qualList}">
						<tr>
							<td><textarea class="no_border width_100" rows="3" disabled><c:out value="${result.ATASK}" /></textarea></td>
							<td><textarea class="no_border width_100" rows="3" disabled><c:out value="${result.DTAIL}" /></textarea></td>
							<td><textarea class="no_border width_100" rows="3" disabled><c:out value="${result.TRGET}" /></textarea></td>
							<td valign="top" class="text_center"><c:out value="${result.WEIGHT}" /></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>

	<div class="group_table">
		<h2 class="title margintop_20">정성</h2>
		<table class="contents_table">
			<colgroup>
			    <col style="width:30%;" />
			    <col style="width:30%;" />
			    <col style="width:30%;" />
			    <col style="width:10%;" />
			</colgroup>
			<thead>
				<tr>
					<th>실행계획/담당업무</th>
					<th>세부내용/산식</th>
					<th>목표</th>
					<th>가중치(%)</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty quanList}">
					<c:forEach var="result" items="${quanList}">
						<tr>
							<td><textarea class="no_border width_100" rows="3" disabled><c:out value="${result.ATASK}" /></textarea></td>
							<td><textarea class="no_border width_100" rows="3" disabled><c:out value="${result.DTAIL}" /></textarea></td>
							<td><textarea class="no_border width_100" rows="3" disabled><c:out value="${result.TRGET}" /></textarea></td>
							<td valign="top" class="text_center"><c:out value="${result.WEIGHT}" /></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>

</div>