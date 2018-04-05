<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<script type="text/javascript">
(function($) {
	$(document).ready(function(){});
})(jQuery);
//-->
</script>

<div id="wrap" style="padding-top:10px; padding-left:7px;">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>연월차 내역 상세 조회</h1>
	
	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="*"/>
				<col width="33%"/>
				<col width="33%"/>
			</colgroup>
			<thead>
				<tr>
					<th>날짜</th>
					<th>휴무유형</th>
					<th>사용일수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${resultList }" var="result">
					<tr>
						<td>
							<fmt:parseDate var="dateString" value="${result.BEGDA}" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						</td>
						<td>
							<c:out value="${result.ATEXT }"/>
						</td>
						<td>
							<c:out value="${result.ANZHL }"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>