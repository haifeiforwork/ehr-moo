<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#searchButton").click(function() {
			$.callProgress();
			$.setParam();
			$("#laborPositionForm").submit();
		});
		
		$("a[name=page]").click(function(){
			$.callProgress();
			$("#laborPositionForm").find("input[name=currentPage]").val($(this).data("page"));
			$("#laborPositionForm").submit();
		});
		
		$("a[name=linkField]").click(function(){
			var tr = $(this).parents("tr");
			var allTr = $("#blktable").find("tbody").find("tr");
			var index = Number(allTr.index(tr));
			var rowInfo = $("span.rowInfo").eq(index).html();
			
			$("#ajaxForm").html("");
			
			$("#ajaxForm").append(rowInfo);
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imYear\" value=\"<c:out value='${params.hidImYear}'/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imMonth\" value=\"<c:out value='${params.hidImMonth}'/>\"/>");
			
			$("#ajaxForm").append("<input type='hidden' name='searchParam' value='?hidImYear=<c:out value='${params.hidImYear}'/>&hidImMonth=<c:out value='${params.hidImMonth}'/>&hidImWerks=<c:out value='${params.hidImWerks}'/>&currentPage=<c:out value='${params.currentPage}'/>'/>");
			
			$("#ajaxForm").attr("method", "post");
			$("#ajaxForm").attr("action", "<c:url value='/portal/moorimmss/laborMng/laborPositionView.do'/>");
			$("#ajaxForm").submit();
			
		});
		
		$.initSet = function(){
			$("select[name=imYear] option[value=<c:out value='${params.hidImYear}'/>]").attr("selected", "selected");
			$("select[name=imMonth] option[value=<c:out value='${params.hidImMonth}'/>]").attr("selected", "selected");
			$("select[name=imWerks] option[value=<c:out value='${params.hidImWerks}'/>]").attr("selected", "selected");
			
			$("input[name=hidImYear]").val("<c:out value='${params.hidImYear}'/>");
			$("input[name=hidImMonth]").val("<c:out value='${params.hidImMonth}'/>");
			$("input[name=hidImWerks]").val("<c:out value='${params.hidImWerks}'/>");
			
			$("input[name=currentPage]").val("<c:out value='${params.currentPage}'/>");
		};
		
		$.setParam = function(){
			$("#laborPositionForm").append("<input type=\"hidden\" name=\"clearCache\" value=\"Y\"/>");
			
			$("input[name=currentPage]").val("1");
			
			$("input[name=hidImYear]").val($("select[name=imYear]").val());
			$("input[name=hidImMonth]").val($("select[name=imMonth]").val());
			$("input[name=hidImWerks]").val($("select[name=imWerks]").val());
		};
		
		$.initSet();
	});
})(jQuery);;
</script>
<form id="laborPositionForm" name="laborPositionForm" method="post" action="<c:url value='/portal/moorimmss/laborMng/laborPositionList.do'/>">
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>노조 직책자 현황 조회</h1>
	
	<c:set var="etFyear" value="${resultMapCode.ET_FYEAR }"/>
	<c:set var="etFmonth" value="${resultMapCode.ET_FMONTH }"/>
	<c:set var="etWerks" value="${resultMapCode.ET_WERKS }"/>
	
	<div class="search_box">
		<table border="0">
			<tbody>
				<tr>
					<td><span class="f_333">조회년월</span></td>
					<td>
						<select name="imYear" id="imYear">
							<c:forEach var="result" items="${etFyear }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="imMonth" id="imMonth">
							<c:forEach var="result" items="${etFmonth }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="search_delimiter"></div>
		<table border="0">
			<tbody>
				<tr>
					<td><span class="f_333">노동조합</span></td>
					<td>
						<select name="imWerks" id="imWerks" class="w150px">
							<c:forEach var="result" items="${etWerks }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
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

	<!-- 상단버튼-->
	<div class="button_box"></div>
	<c:if test="${!empty resultMap.EX_WERTXT }">
		<div class="f_title">[<c:out value="${resultMap.EX_WERTXT }"/>]</div>
	</c:if>
	
	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="blktable">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="*">부서</th>
					<th scope="col" width="80px">직급</th>
					<th scope="col" width="80px">성명</th>
					<th scope="col" width="80px">입사일자</th>
					<th scope="col" width="80px">생년월일</th>
					<th scope="col" width="11%">노조직책</th>
					<th scope="col" width="80px">노조기수</th>
					<th scope="col" width="12%">근무조</th>
					<th scope="col" width="15%">Position</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty etList}">
						<tr>
							<td colspan="9">해당 노조현황 내역이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList}">
							<tr>
								<td class="f_left"><c:out value="${result.STEXT}"/></td>
								<td><c:out value="${result.JIKWI}"/></td>
								<td><a class="board_2" href="#" name="linkField"><c:out value="${result.ENAME}"/></a></td>
								<td>
									<fmt:parseDate var="dateString" value="${result.ENTDAT}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
								<td>
									<fmt:parseDate var="dateString" value="${result.GBDAT}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
								<td class="f_left"><c:out value="${result.LABTX}"/></td>
								<td><c:out value="${result.LABNUM}"/></td>
								<td class="f_left"><c:out value="${result.RTEXT}"/></td>
								<td class="f_left">
									<c:out value="${result.PLSTX}"/>
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
	
	<c:if test="${!empty etList }">
		<%@include file="/WEB-INF/view/portal/webess/paging.jsp"%>
	</c:if>
	
	<input type="hidden" name="currentPage" value=""/>
	<input type="hidden" name="hidImYear" value=""/>
	<input type="hidden" name="hidImMonth" value=""/>
	<input type="hidden" name="hidImWerks" value=""/>
	
	<div class="button_box"></div>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>