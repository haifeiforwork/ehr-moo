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

<c:if test="${not empty printList}">
	<c:forEach var="result" items="${printList}" varStatus="status">
		<div class="wrap">

			<h1 class="title">도전과제</h1>

			<table class="list_table">
				<colgroup>
					<col style="width: 15%;" />
					<col style="width: 35%;" />
					<col style="width: 15%;" />
					<col style="width: 35%;" />
				</colgroup>
				<tbody>
					<tr>
						<th>소속</th>
						<td class="text_left">${result.esRslst.ORGTX}</td>
						<th>성명/사번</th>
						<td class="text_left">${result.esRslst.RSPERNM}(${result.esRslst.RSPER})</td>
					</tr>
					<tr>
						<th>직급</th>
						<td class="text_left">${result.esRslst.TRFGR}</td>
						<th>직무</th>
						<td class="text_left">${result.esRslst.STLTX}</td>
					</tr>
				</tbody>
			</table>

			<div class="group_table">
				<h2 class="title margintop_20">과제명칭</h2>
				<table class="contents_table">
					<colgroup>
						<col style="">
					</colgroup>
					<tbody>
						<tr>
							<td><c:out value="${result.etChaprj[0].CHAPRNM}" /></td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="group_table">
				<h2 class="title margintop_20">과제선정이유</h2>
				<table class="contents_table">
					<colgroup>
						<col style="width: 25%;">
						<col style="width: 75%;">
					</colgroup>
					<tbody>
						<tr>
							<th>현상의 문제점</th>
							<td><textarea class="no_border width_100" rows="4" disabled><c:out value="${result.etChaprj[0].CHAPRD1}" /></textarea></td>
						</tr>
						<tr>
							<th>경쟁기업과의 차이</th>
							<td><textarea class="no_border width_100" rows="4" disabled><c:out value="${result.etChaprj[0].CHAPRD2}" /></textarea></td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="group_table">
				<h2 class="title margintop_20">과제수행방법</h2>
				<table class="contents_table">
					<colgroup>
						<col style="">
					</colgroup>
					<tbody>
						<tr>
							<td><textarea class="no_border width_100" rows="4" disabled><c:out value="${result.etChaprj[0].CHAPRD3}" /></textarea></td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="group_table">
				<h2 class="title margintop_20">과제수행의 기대결과</h2>
				<table class="contents_table">
					<colgroup>
						<col style="">
					</colgroup>
					<tbody>
						<tr>
							<td><textarea class="no_border width_100" rows="4" disabled><c:out value="${result.etChaprj[0].CHAPRD4}" /></textarea></td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="group_table">
				<h2 class="title margintop_20">과제수행결과의 효과</h2>
				<table class="contents_table">
					<colgroup>
						<col style="">
					</colgroup>
					<tbody>
						<tr>
							<td><textarea class="no_border width_100" rows="4" disabled><c:out value="${result.etChaprj[0].CHAPRD5}" /></textarea></td>
						</tr>
					</tbody>
				</table>
			</div>

		</div>
	</c:forEach>
</c:if>