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

			<h1 class="title">IDP (${result.esIdplst.AYEAR}년)</h1>

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
						<td class="text_left">${result.esIdplst.ORGTX}</td>
						<th>성명/사번</th>
						<td class="text_left">${result.esIdplst.IDPAENM}(${result.esIdplst.IDPAE})</td>
					</tr>
					<tr>
						<th>직급</th>
						<td class="text_left">${result.esIdplst.TRFGR}</td>
						<th>직무</th>
						<td class="text_left">${result.esIdplst.STLTX}</td>
					</tr>
				</tbody>
			</table>

			<hr class="margintop_20" />

			<h2 class="title margintop_20">경력목표 및 희망직무</h2>
			<table class="contents_table" id="crjobTable">
				<colgroup>
				    <col style="width:12%;">
				    <col style="width:8%;">
				    <col style="width:40%;">
				    <col style="width:40%;">
				</colgroup>
				<tbody>
					<tr>
						<th>경력목표</th>
						<td colspan="3" class="text_left"><c:out value="${result.esCrjob.IDCGL}" /></td>
					</tr>
					<tr>
						<th rowspan="3">희망이동<br />(직무/부서)</th>
						<th>1순위</th>
						<td class="text_left"><c:out value="${result.esCrjob.HPJOB1T}" /></td>
						<td class="text_left"><c:out value="${result.esCrjob.HPORG1T}" /></td>
					</tr>
					<tr>
						<th>2순위</th>
						<td class="text_left"><c:out value="${result.esCrjob.HPJOB2T}" /></td>
						<td class="text_left"><c:out value="${result.esCrjob.HPORG2T}" /></td>
					</tr>
					<tr>
						<th>3순위</th>
						<td class="text_left"><c:out value="${result.esCrjob.HPJOB3T}" /></td>
						<td class="text_left"><c:out value="${result.esCrjob.HPORG3T}" /></td>
					</tr>
				</tbody>
			</table>

			<h2 class="title margintop_20">자기개발계획</h2>

			<div class="group_table">
				<h3 class="title">1. 필수개발역량</h3>
				<table class="contents_table">
					<colgroup>
					    <col style="width:20%;" />
					    <col style="width:40%;" />
					    <col style="width:18%;" />
					    <col style="width:6%;" />
					    <col style="width:16%;" />
					</colgroup>
					<thead>
						<tr>
							<th>개발목표(역량)</th>
							<th>개발계획 및 활동내용</th>
							<th>교육명(과정명)</th>
							<th>기간</th>
							<th>수행결과</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty result.etRequal}">
							<c:forEach var="requal" items="${result.etRequal}">
								<tr>
									<td valign="top"><c:out value="${requal.IDOBJT}" /></td>
									<td><textarea class="no_border width_100" rows="4" disabled>${requal.IDDES}</textarea></td>
									<td class="padding_0">
										<p class="idp_edu1">
											<span class="edu_label" style="width:35%;">교육명</span>
											<span class="edu_input">
												<c:out value="${requal.IDEDUT}" />
											</span>
										</p>
										<p class="idp_edu2">
											<span class="edu_label" style="width:35%;">과정명</span>
											<span class="edu_input">
												<c:out value="${requal.IDEDUX}" />
											</span>
										</p>
									</td>
									<td class="text_center" valign="top">
										<fmt:parseNumber var="idedpd_num" value="${requal.IDEDPD}"/>
										<c:out value="${idedpd_num}" />월
									</td>
									<td><textarea class="no_border width_100" rows="4" disabled>${requal.IDEDRT}</textarea></td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>

			<div class="group_table">
				<h3 class="title margintop_20">2. 희망직무지식</h3>
				<table class="contents_table">
					<colgroup>
					    <col style="width:20%;" />
					    <col style="width:40%;" />
					    <col style="width:18%;" />
					    <col style="width:6%;" />
					    <col style="width:16%;" />
					</colgroup>
					<thead>
						<tr>
							<th>개발목표(직무)</th>
							<th>개발계획 및 활동내용</th>
							<th>교육명(과정명)</th>
							<th>기간</th>
							<th>수행결과</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty result.etHpqual}">
							<c:forEach var="hpqual" items="${result.etHpqual}">
								<tr>
									<td valign="top"><c:out value="${hpqual.IDOBJT}" /></td>
									<td><textarea class="no_border width_100" rows="4" disabled>${hpqual.IDDES}</textarea></td>
									<td class="padding_0">
										<p class="idp_edu1">
											<span class="edu_label" style="width:35%;">교육명</span>
											<span class="edu_input">
												<c:out value="${hpqual.IDEDUT}" />
											</span>
										</p>
										<p class="idp_edu2">
											<span class="edu_label" style="width:35%;">과정명</span>
											<span class="edu_input">
												<c:out value="${hpqual.IDEDUX}" />
											</span>
										</p>
									</td>
									<td class="text_center" valign="top">
										<fmt:parseNumber var="idedpd_num" value="${hpqual.IDEDPD}" />
										<c:out value="${idedpd_num}" />월
									</td>
									<td><textarea class="no_border width_100" rows="4" disabled>${hpqual.IDEDRT}</textarea></td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>

		</div>
	</c:forEach>
</c:if>