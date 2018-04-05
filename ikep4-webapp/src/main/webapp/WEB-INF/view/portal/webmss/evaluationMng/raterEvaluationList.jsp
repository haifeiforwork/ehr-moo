<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#searchButton").click(function() {
			$("#raterEvaluationForm").submit();
		});
		
		//$("a[name=linkField]").click(function(){
			//var tr = $(this).parents("tr");
			//var allTr = $("#blktable").find("tbody").find("tr");
			//var index = Number(allTr.index(tr));
			//var rowInfo = $("span.rowInfo").html()+$("span.etDoc").html()+$("span.etMy").html();
//alert(rowInfo);
			//$("#ajaxForm").html("");
			//$("#ajaxDiv").append(rowInfo);
			
			//alert($("#ajaxDiv").html());
			//$("#ajaxForm").html($("#ajaxDiv").html());

			//$("#raterEvaluationForm").attr("action", "<c:url value='/portal/moorimmss/evaluationMng/raterEvaluationView.do'/>");
			//$("#raterEvaluationForm").submit();

		//});
		
		detailview = function(pernr){ 
			$("#raterEvaluationForm").attr("action", "<c:url value='/portal/moorimmss/evaluationMng/raterEvaluationView.do'/>?pernr="+pernr);
			$("#raterEvaluationForm").submit();
		};
	});
	
	
})(jQuery);;
</script>
<form id="raterEvaluationForm" name="raterEvaluationForm" method="post" action="<c:url value='/portal/moorimmss/evaluationMng/raterEvaluationList.do'/>">
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>5급사원 평가가 평가</h1>
	<c:set var="yearList" value="${result.ET_YEAR}"/>
	<div class="search_box">
		<table border="0">
			<tbody>
				<tr>
					<td><span class="f_333">조회년월</span></td>
					<td>
						<select name="imYear" id="imYear">
							<c:forEach var="result1" items="${yearList}">
								<option value="<c:out value="${result1.KEY }"/>"><c:out value="${result1.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<a href="#" class="button_img06" id="searchButton"><span>조회</span></a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="button_box"></div>

	<c:set var="etList" value="${result.ET_LIST}"/>

	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="12%">사번</th>
					<th scope="col" width="12%">직급</th>
					<th scope="col" width="9%">성명</th>
					<th scope="col" width="20%">직무</th>
					<th scope="col" width="8%">평가시작일</th>
					<th scope="col" width="9%">평가종료일</th>
					<th scope="col" width="9%">점수</th>
					<th scope="col" width="9%">등급</th>
					<th scope="col" width="*">평가상태</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty etList}">
						<tr>
							<td colspan="9">조회된 내역이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList}">
							<tr>
								<td><a href="javascript:detailview(${result.PERNR});" class="board_2" name="linkField"><c:out value="${result.PERNR}"/></a></td>
								<td><a href="javascript:detailview(${result.PERNR});" class="board_2" name="linkField"><c:out value="${result.TRFGR}"/></a></td>
								<td><a href="javascript:detailview(${result.PERNR});" class="board_2" name="linkField"><c:out value="${result.ENAME}"/></a></td>
								<td><a href="javascript:detailview(${result.PERNR});" class="board_2" name="linkField"><c:out value="${result.STLTX}"/></a></td>
								<td><a href="javascript:detailview(${result.PERNR});" class="board_2" name="linkField">
									<fmt:parseDate var="dateString1" value="${result.BEGDA}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString1}" pattern="yyyy.MM.dd" /></a>
								</td>
								<td><a href="javascript:detailview(${result.PERNR});" class="board_2" name="linkField">
									<fmt:parseDate var="dateString2" value="${result.ENDDA}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString2}" pattern="yyyy.MM.dd" /></a>
									<span class="rowInfo">
										<c:forEach items="${keySet1}" var="key1">
											<input type="hidden" name="<c:out value="${key1}"/>_${result.PERNR}" value="<c:out value="${result[key1]}"/>"/>
										</c:forEach>
									</span>
								</td>
								<td><a href="javascript:detailview(${result.PERNR});" class="board_2" name="linkField"><c:out value="${result.POINT}"/></a></td>
								<td><a href="javascript:detailview(${result.PERNR});" class="board_2" name="linkField"><c:out value="${result.GRADE}"/></a></td>
								<td><a href="javascript:detailview(${result.PERNR});" class="board_2" name="linkField"><c:out value="${result.STATUST}"/></a></td>
							</tr>
							
						</c:forEach>
					</c:otherwise>
				</c:choose>
			 </tbody>
		</table>
	</div>
	
</div>
<c:set var="etdocument" value="${result.ET_DOCUMENTS }"/>
<c:set var="etdocumentKeySet" value="${keySet2}"/>
<span class="etDoc">
	<input type="hidden" name="etdocumentCnt" value="${fn:length(etdocument)}"/>
	<c:forEach var="etdoc" items="${etdocument}" varStatus="status">
		<c:forEach items="${etdocumentKeySet}" var="key">
			<input type="hidden" id="etdocument_<c:out value="${key}"/>_${status.index}" name="etdocument_<c:out value="${key}"/>_${status.index}" value="<c:out value="${etdoc[key] }"/>"/>
		</c:forEach>
	</c:forEach>
</span>

<c:set var="etmyself" value="${result.ET_MYSELF }"/>
<span class="etMy">
	<input type="hidden" name="etmyselfCnt" value="${fn:length(etmyself)}"/>
	<c:forEach var="etmy" items="${etmyself}" varStatus="status">
		<input type="hidden" id="etmyself_SOBID_${status.index}" name="etmyself_SOBID_${status.index}" value="<c:out value="${etmy.SOBID}"/>"/>
		<input type="hidden" id="etmyself_PLVAR_${status.index}" name="etmyself_PLVAR_${status.index}" value="<c:out value="${etmy.PLVAR}"/>"/>
		<input type="hidden" id="etmyself_OTYPE_${status.index}" name="etmyself_OTYPE_${status.index}" value="<c:out value="${etmy.OTYPE}"/>"/>
	</c:forEach>
</span>
</form>

<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>