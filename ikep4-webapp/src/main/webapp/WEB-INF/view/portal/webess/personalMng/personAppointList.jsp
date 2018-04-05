<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>

<script type="text/javascript">
(function($){
	$(document).ready(function(){

		$("input[name=startDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("input[name=endDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
		$("#searchButton").click(function() {
			$.callProgress();
			$.setParam();
			$("#personAppointForm").submit();
		});
		
		$("a[name=page]").click(function(){
			$.callProgress();
			$("#personAppointForm").find("input[name=currentPage]").val($(this).data("page"));
			$("#personAppointForm").submit();
		});
		
		$.setParam = function(){
			
			$("#personAppointForm").append("<input type=\"hidden\" name=\"clearCache\" value=\"Y\"/>");
			
			$("input[name=currentPage]").val("1");
			$("input[name=hidAppointType]").val($("input[name=appointType]:checked").val());
			$("input[name=hidStartDate]").val($("input[name=startDate]").val());
			$("input[name=hidEndDate]").val($("input[name=endDate]").val());
			$("input[name=hidName]").val($("input[name=name]").val());
		};
		
		$.initSet = function(){
			$("input[name=appointType][value=<c:out value='${params.hidAppointType}'/>]").attr("checked", true);
			$("input[name=startDate]").val("<c:out value="${params.hidStartDate}"/>");
			$("input[name=endDate]").val("<c:out value="${params.hidEndDate}"/>");
			$("input[name=name]").val("<c:out value="${params.hidName}"/>");
			
			$("input[name=hidAppointType]").val("<c:out value="${params.hidAppointType}"/>");
			$("input[name=hidStartDate]").val("<c:out value="${params.hidStartDate}"/>");
			$("input[name=hidEndDate]").val("<c:out value="${params.hidEndDate}"/>");
			$("input[name=hidName]").val("<c:out value="${params.hidName}"/>");
			
			$("input[name=currentPage]").val("<c:out value="${params.currentPage}"/>");
		};

		$.initSet();
	});
})(jQuery);;
</script>
<form id="personAppointForm" name="personAppointForm" method="post" action="<c:url value='/portal/moorimess/personalMng/personAppointList.do'/>">
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>인사발령사항 조회</h1>

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
						<label for="B3"><input type="radio" name="appointType" value="B3" id="B3"/>&nbsp;보직</label>&nbsp;&nbsp;&nbsp;
						<label for="B8"><input type="radio" name="appointType" value="B8" id="B8"/>&nbsp;입사</label>&nbsp;&nbsp;&nbsp;
						<label for="B6"><input type="radio" name="appointType" value="B6" id="B6"/>&nbsp;퇴직</label>&nbsp;&nbsp;&nbsp;
						<label for="B7"><input type="radio" name="appointType" value="B7" id="B7"/>&nbsp;휴직</label>&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</tbody>
		</table>
		<div class="search_delimiter"></div>
		<table border="0">
			<tbody>
				<tr>
					<td><span class="f_333">기간</span></td>
					<td>
						<input type="text" name="startDate" id="startDate" value="" class="datepicker input" readonly="readonly"/>
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
						&nbsp;~&nbsp;
						<input type="text" name="endDate" id="endDate" value="" class="datepicker input" readonly="readonly"/>
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="search_delimiter"></div>
		<table border="0">
			<tbody>
				<tr>
					<td><span class="f_333">성명</span></td>
					<td>
						<input type="text" name="name" value="" class="input"/>
					</td>
					<td>
						<a href="#" class="button_img06" id="searchButton"><span>조회</span></a>
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
					<th scope="col" width="12%">발령유형</th>
					<th scope="col" width="12%">발령사유</th>
					<th scope="col" width="12%">발령일자</th>
					<th scope="col" width="*">소속</th>
					<th scope="col" width="12%">직급/직위</th>
					<th scope="col" width="12%">성명</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty etList}">
						<tr>
							<td colspan="6">조회된 내역이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList}">
							<tr>
								<td><c:out value="${result.MNTXT}"/></td>
								<td><c:out value="${result.MGTXT}"/></td>
								<td>
									<fmt:parseDate var="dateString" value="${result.BEGDA}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
								<td class="f_left"><c:out value="${result.ORGTX}"/></td>
								<td><c:out value="${result.ZTRJI}"/></td>
								<td><c:out value="${result.ENAME}"/></td>
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
	
	<input type="hidden" name="hidAppointType" value=""/>
	<input type="hidden" name="hidStartDate" value=""/>
	<input type="hidden" name="hidEndDate" value=""/>
	<input type="hidden" name="hidName" value=""/>
	
</div>
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>