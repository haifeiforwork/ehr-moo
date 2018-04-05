<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
	});
})(jQuery);;
</script>

<c:set var="etList" value="${resultMap.ET_LIST}"/>

<form id="statusPointForm" name="statusPointForm" method="post" action="<c:url value='/portal/moorimess/manPowerMng/statusPointList.do'/>">

<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>승격Point 조회</h1>

	<p class="f_title" style="padding-bottom:10px">※승격 POINT 조회는 매년 1월, 12월에만 가능합니다.</p>

	<c:choose>
		<c:when test="${resultMap.EX_OPEN eq 'X' }">
			<div class="list01">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<caption></caption>
					<colgroup>
						<col width="12%"/>
						<col width="12%"/>
						<col width="12%"/>
						<col width="12%"/>
						<col width="12%"/>
						<col width="12%"/>
						<col width="12%"/>
						<col width="*"/>
					</colgroup>
					<thead>
						<tr>
							<th>년도</th>
							<th>평가<br/>Point</th>
							<th>교육<br/>Point</th>
							<th>지식<br/>Point</th>
							<th>상벌<br/>Point</th>
							<th>어학<br/>Point</th>
							<th>합계<br/>Point</th>
							<th>비고</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${etList }" var="result">
							<tr>
								<td><c:out value="${result.AYR }"/></td>
								<td><c:out value="${result.APT }"/></td>
								<td><c:out value="${result.EDU }"/></td>
								<td><c:out value="${result.KNL}"/></td>
								<td><c:out value="${result.JUT}"/></td>
								<td><c:out value="${result.LAN}"/></td>
								<td><c:out value="${result.TOTAL1}"/></td>
								<td><c:out value="${result.COMMT}"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:when>
		<c:otherwise>
			<p>※조회기간이 아닙니다.</p>
		</c:otherwise>
	</c:choose>
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