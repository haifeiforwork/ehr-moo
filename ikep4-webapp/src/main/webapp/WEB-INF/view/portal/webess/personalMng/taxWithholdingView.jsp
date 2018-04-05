<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		if("<c:out value="${resultMap.EX_RESULT}"/>" == "E"){
			alert("<c:out value="${resultMap.EX_MESSAGE}"/>");
		}

		$("select[name=imYear]").change(function(){
			$("#taxWithholdingForm").submit();
		});

		$("select[name=imYear] option[value=<c:out value='${params.imYear}'/>]").attr("selected", "selected");
	});
})(jQuery);;
</script>

<form id="taxWithholdingForm" name="taxWithholdingForm" method="post" action="<c:url value='/portal/moorimess/personalMng/taxWithholdingView.do'/>">

<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>근로소득 원천징수영수증 조회</h1>

	<div class="search_box">
		<table border="0">
			<tbody>
				<tr>
					<td><span class="f_333">조회년도</span></td>
					<td>
						<select name="imYear" id="imYear">
							<c:forEach var="result" items="${yearList }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="button_box"></div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">근로소득 원천징수 영수증</p>
		<embed src="<c:url value='/portal/moorimess/personalMng/taxWithholdingGetPDF.do'/>?imYear=<c:out value="${params.imYear }"/>#toolbar=0" width="100%" height="500px" type='application/pdf'></embed>
	</div>

	<div class="table_box" style="height:40px">
		<a href="http://get.adobe.com/kr/reader/" target='_blank' class="left">
			<img name="adobe" height="39" src="<c:url value="/base/images/ess/get_adobe_reader.png"/>" width="158" border=0>
		</a>
		<p>만약 Viewer 가 정상적으로 보이지 않는다면 Adobe Reader 가 설치되어 있는지 확인 하시기 바랍니다.</p>
		<p>Adobe Reader 가 설치되지 않은 사용자는 왼편 링크를 통해 설치 하시기 바랍니다.</p>
	</div>
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