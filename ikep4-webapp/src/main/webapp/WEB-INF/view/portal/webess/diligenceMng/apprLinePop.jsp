<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<script type="text/javascript">
(function($) {
	$(document).ready(function(){

		$("a[name=selectName]").click(function(){

			var index = Number($(this).data("index"));
			var parentsIndex = Number("<c:out value="${params.index}"/>");

			<c:forEach items="${apprAbleKeySet}" var="key">
				<c:choose>
					<c:when test="${key eq 'PERNRT'}">
					$(opener.document).find("span.APPNR").eq(parentsIndex).html($("input[name=<c:out value="${key}"/>]").eq(index).val());
					$(opener.document).find("input[name=APPNR]").eq(parentsIndex).val($("input[name=<c:out value="${key}"/>]").eq(index).val());
					</c:when>
					<c:otherwise>
						$(opener.document).find("span.<c:out value="${key}"/>").eq(parentsIndex).html($("input[name=<c:out value="${key}"/>]").eq(index).val());
						$(opener.document).find("input[name=<c:out value="${key}"/>]").eq(parentsIndex).val($("input[name=<c:out value="${key}"/>]").eq(index).val());
					</c:otherwise>
				</c:choose>
			</c:forEach>

			self.close();
		});
	});
})(jQuery);
//-->
</script>

<div id="wrap" style="padding-top:10px; padding-left:7px;">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>결재자 검색</h1>

	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="25%"/>
				<col width="25%"/>
				<col width="25%"/>
				<col width="25%"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="center">사번</th>
					<th class="center">이름</th>
					<th class="center">부서명</th>
					<th class="center">직책</th>
				</tr>
				<c:forEach var="apprAble" items="${apprAbleList}" varStatus="status">
					<tr>
						<td class="center"><c:out value="${apprAble.PERNRT}"/></td>
						<td class="center"><a href="#" data-index="<c:out value="${status.index }"/>" name="selectName" class="board_2"><c:out value="${apprAble.ENAME}"/></a></td>
						<td class="center"><c:out value="${apprAble.ORGTX}"/></td>
						<td class="center"><c:out value="${apprAble.COTXT}"/></td>
						<c:forEach items="${apprAbleKeySet}" var="key">
							<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${apprAble[key] }"/>"/>
						</c:forEach>
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
