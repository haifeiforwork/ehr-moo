<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#searchButton").click(function() {
			$.callProgress();
			$.setParam();
			$("#organizationChartForm").append("<input type=\"hidden\" name=\"imFirst\" value=\"X\"/>");
			$("#organizationChartForm").submit();
		});
		
		$("a[name=page]").click(function(){
			$.callProgress();
			$("#organizationChartForm").find("input[name=currentPage]").val($(this).data("page"));
			<c:if test="${params.imFirst eq 'X'}">
			$("#organizationChartForm").append("<input type=\"hidden\" name=\"imFirst\" value=\"X\"/>");
			</c:if>
			$("#organizationChartForm").submit();
		});
		
		$.initSet = function(){
			$("select[name=imOrg] option[value=<c:out value='${params.hidImOrg}'/>]").attr("selected", "selected");
			$("input[name=hidImOrg]").val("<c:out value="${params.hidImOrg}"/>");
			$("input[name=currentPage]").val("<c:out value="${params.currentPage}"/>");
		};
		
		$.setParam = function(){
			$("#organizationChartForm").append("<input type=\"hidden\" name=\"clearCache\" value=\"Y\"/>");
			
			$("input[name=currentPage]").val("1");
			$("input[name=hidImOrg]").val($("select[name=imOrg]").val());
		};
		
		$.initSet();
	});
})(jQuery);;

function openFileWin_2(page){
	window.open(page, '_blank', 'width=800,height=600,top=10,left=10,scrollbars=yes,resizable=yes,status=no');
}
</script>
<form id="organizationChartForm" name="organizationChartForm" method="post" action="<c:url value='/portal/moorimess/organogramMng/organizationChartList.do'/>">
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>조직도</h1>

	<c:set var="etOrg" value="${resultMap.ET_ORG }"/>

	<div class="search_box">
		<table border="0">
			<tbody>
				<tr>
					<td><span class="f_333">사업장</span></td>
					<td>
						<select name="imOrg" id="imOrg" class="w150px">
							<c:forEach var="result" items="${etOrg }">
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

	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="blktable">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="20%">기준일</th>
					<th scope="col" width="20%">사업장</th>
					<th scope="col" width="*">제목</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty etList}">
						<tr>
							<td colspan="3">조직도 조회내역이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList}">
							<tr>
								<td>
									<fmt:parseDate var="dateString" value="${result.REGDA}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
								<td><c:out value="${result.LTEXT}"/></td>
								<td class="f_left"><a href="<c:out value="${result.HREF1 }"/>" class="board_2"><c:out value="${result.TITLE}"/></a></td>
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
	<input type="hidden" name="hidImOrg" value=""/>
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