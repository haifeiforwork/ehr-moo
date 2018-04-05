<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<script type="text/javascript">
(function($) {
	$(document).ready(function(){

		$("a[name=selectName]").click(function(){

			var index = Number($(this).data("index"));
			var parentsIndex = Number("<c:out value="${params.index}"/>");

			<c:forEach items="${keySet}" var="key">
				<c:choose>
					<c:when test="${key eq 'OBJID'}">
						$(opener.document).find("span.OBJ").eq(parentsIndex).html($("input[name=<c:out value="${key}"/>]").eq(index).val());
						$(opener.document).find("input[name=OBJ]").eq(parentsIndex).val($("input[name=<c:out value="${key}"/>]").eq(index).val());
					</c:when>
					<c:when test="${key eq 'STEXT'}">
						$(opener.document).find("span.OBJ_T").eq(parentsIndex).html($("input[name=<c:out value="${key}"/>]").eq(index).val());
						$(opener.document).find("input[name=OBJ_T]").eq(parentsIndex).val($("input[name=<c:out value="${key}"/>]").eq(index).val());
					</c:when>
					<c:otherwise>
						$(opener.document).find("span.<c:out value="${key}"/>").eq(parentsIndex).html($("input[name=<c:out value="${key}"/>]").eq(index).val());
						$(opener.document).find("input[name=<c:out value="${key}"/>]").eq(parentsIndex).val($("input[name=<c:out value="${key}"/>]").eq(index).val());
					</c:otherwise>
				</c:choose>

			</c:forEach>

			opener.$jq.callTaskList($(opener.document).find("input[name=OBJ]").eq(parentsIndex));

			self.close();
		});

		$("#searchButton").click(function() {
			$("#jobLineForm").submit();
		});

		$("input[name=imSearch]").val("<c:out value="${params.imSearch}"/>");
	});
})(jQuery);
//-->
</script>
<form id="jobLineForm" name="jobLineForm" method="post" action="<c:url value='/portal/moorimess/personalDivisionMng/jobLinePop.do'/>">
<div id="wrap" style="padding-top:10px; padding-left:7px;">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>직무 검색</h1>

	<div class="search_box">
		<table border="0">
			<tbody>
				<tr>
					<td><span class="f_333">직무명</span></td>
					<td>
						<input type="text" name="imSearch" value="" class="input"/>
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
			<colgroup>
				<col width="50%"/>
				<col width="50%"/>
			</colgroup>
			<tbody>
				<tr>
					<th>직무코드</th>
					<th>직무명</th>
				</tr>
				<c:forEach var="result" items="${resultMap.EX_TAB}" varStatus="status">
					<tr>
						<td><a href="#" data-index="<c:out value="${status.index }"/>" name="selectName" class="board_2"><c:out value="${result.OBJID}"/></a></td>
						<td>
							<c:out value="${result.STEXT}"/>
							<c:forEach items="${keySet}" var="key">
								<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${result[key] }"/>"/>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
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
