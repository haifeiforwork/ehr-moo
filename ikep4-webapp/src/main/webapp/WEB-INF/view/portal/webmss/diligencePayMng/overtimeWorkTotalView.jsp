<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		$("#searchButton").click(function() {
			$("#overtimeWorkTotalForm").submit();
		});
		
		$("select[name=imYyyymm] option[value=<c:out value='${params.imYyyymm}'/>]").attr("selected", "selected");
		$("select[name=imPernr] option[value=<c:out value='${params.imPernr}'/>]").attr("selected", "selected");
		
		if("<c:out value="${resultMap.EX_RESULT}"/>" == "E"){
			alert("<c:out value="${resultMap.EX_MESSAGE}"/>");
		}
	});
})(jQuery);;
</script>

<form id="overtimeWorkTotalForm" name="overtimeWorkTotalForm" method="post" action="<c:url value='/portal/moorimmss/diligencePayMng/overtimeWorkTotalView.do'/>">

<c:set var="etYyyymm" value="${resultMap.ET_YYYYMM }"/>
<c:set var="etBanwon" value="${resultMap.ET_BANWON }"/>

<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>연장근로 집계표 조회</h1>

	<div class="search_box">
		<table border="0">
			<tbody>
				<tr>
					<td width="50px"><span class="f_333">조회년월</span></td>
					<td>
						<select name="imYyyymm" id="imYyyymm" class="w150px">
							<c:forEach var="result" items="${etYyyymm }">
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
		<div class="search_delimiter"></div>
		<table border="0">
			<tbody>
				<tr>
					<td width="50px"><span class="f_333">성명</span></td>
					<td>
						<select name="imPernr" id="imPernr" class="w150px">
							<c:forEach var="result" items="${etBanwon }">
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
		<p class="f_title" style="padding-bottom:10px">연장근로집계표</p>
		<c:choose>
			<c:when test="${resultMap.EX_RESULT eq 'S' }">
				<embed src="<c:url value='/portal/moorimmss/diligencePayMng/overtimeWorkTotalGetPDF.do'/>?imYyyymm=<c:out value="${params.imYyyymm }"/>&imPernr=<c:out value="${params.imPernr }"/>&imFirst=<c:out value="${params.imFirst }"/>" width="100%" height="500px" type='application/pdf'></embed>
			</c:when>
			<c:otherwise>
				<embed src="" width="100%" height="500px"></embed>
			</c:otherwise>
		</c:choose>
	</div>

	<div class="table_box" style="height:40px">
		<a href="http://get.adobe.com/kr/reader/" target='_blank' class="left">
			<img name="adobe" height="39" src="<c:url value="/base/images/ess/get_adobe_reader.png"/>" width="158" border=0>
		</a>
		<p>만약 Viewer 가 정상적으로 보이지 않는다면 Adobe Reader 가 설치되어 있는지 확인 하시기 바랍니다.</p>
		<p>Adobe Reader 가 설치되지 않은 사용자는 왼편 링크를 통해 설치 하시기 바랍니다.</p>
	</div>
	
	<input type="hidden" name="imFirst" value="X"/>
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