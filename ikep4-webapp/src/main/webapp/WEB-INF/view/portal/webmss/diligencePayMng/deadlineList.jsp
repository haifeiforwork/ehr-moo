<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		$("input[name=imDatum]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
		<fmt:parseDate var="dateString" value="${resultMap.EX_DATUM}" pattern="yyyyMMdd" />
		$("input[name=imDatum]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");

		$("#searchButton").click(function() {
			$.callProgress();
			$("#deadlineForm").submit();
		});

		$("#btnDeadLine").click(function(){
			$.setParam();
			if(confirm($("#imDatum").val()+" 까지 근태를 마감하시겠습니까?")) {
				$jq.ajax({
					url : "<c:url value='/portal/moorimmss/diligencePayMng/deadlineProcess.do'/>",
					data : $("#ajaxForm").serialize(),
					type : "post",
					async: false,
					success : function(result) {
						if($.trim(result.EX_RESULT) == "S"){
							$("#searchButton").click();
						}else{
							alert(result.EX_MESSAGE);
						}
					}
				})
			}
		});
		
		$.setParam = function(){
			
			$("span.rowInfo").each(function(index, value){
				$("#ajaxForm").append($(this).html());
			});
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"rowInfoCnt\" value=\""+$("span.rowInfo").length+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imDatum\" value=\"<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />\"/>");
		};
	});
})(jQuery);;
</script>
<style type="text/css">
.cursor-pointer{cursor:pointer;}
</style>
<form id="deadlineForm" name="deadlineForm" method="post" action="<c:url value='/portal/moorimmss/diligencePayMng/deadlineList.do'/>" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>근태마감</h1>
	
	<div class="search_box">
		<table border="0">
			<tbody>
				<tr>
					<td><span class="f_333">근태마감일</span></td>
					<td>
						<input type="text" name="imDatum" id="imDatum" value="" class="input datepicker" readonly="readonly"/>
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
					</td>
					<td>
						<a href="#" class="button_img06" id="searchButton"><span>조회</span></a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div class="button_box">
		<a href="#" class="button_img01" id="btnDeadLine"><span>마감</span></a>
	</div>
	
	<c:set var="etList" value="${resultMap.ET_LIST }"/>
	
	<div class="table_box">
		<div class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="*">마감상태</th>
						<th scope="col" width="14%">부서</th>
						<th scope="col" width="14%">직급</th>
						<th scope="col" width="14%">사원번호</th>
						<th scope="col" width="14%">이름</th>
						<th scope="col" width="14%">사원그룹</th>
						<th scope="col" width="14%">사원하위</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${!empty etList }">
							<c:forEach var="result" items="${etList}">
								<tr>
									<td>
										<c:choose>
											<c:when test="${result.ZFLAG eq 'X'}">
												<img src="<c:url value="/base/images/ess/green3_icon.gif"/>" alt=""/>
											</c:when>
											<c:otherwise>
												<img src="<c:url value="/base/images/ess/red3_icon.gif"/>" alt=""/>
											</c:otherwise>
										</c:choose>
									</td>
									<td><c:out value="${result.ORGTX}"/></td>
									<td><c:out value="${result.JIKWI}"/></td>
									<td><c:out value="${result.PERNR}"/></td>
									<td><c:out value="${result.ENAME}"/></td>
									<td><c:out value="${result.PTEXT}"/></td>
									<td>
										<c:out value="${result.PKTXT}"/>
										<span class="rowInfo">
											<c:forEach items="${keySet}" var="key">
												<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${result[key]}"/>"/>
											</c:forEach>
										</span>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="7">해당 조회 내역이 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				 </tbody>
			</table>
		</div>
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
