<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		var page = "DISPLAY";

		$("#btnBack").click(function(){
			$("#leaveReinstatementForm").attr("method", "post");
			$("#leaveReinstatementForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/leaveReinstatementList.do'/>");
			$("#leaveReinstatementForm").submit();
		});

		$("#btnDelete").click(function(){
			if(confirm("정말로 삭제 하시겠습니까?")){
				$.setParam("DELETE");
			}
		});

		$("#startDate, #endDate").change(function(){
			if($("#startDate").val() != "" && $("#endDate").val()){
				$jq.ajax({
					url : "<c:url value='/portal/moorimess/diligenceMng/retrieveCalculatorPeriod.do'/>",
					data : "startDate="+$("#startDate").val()+"&endDate="+$("#endDate").val(),
					type : "post",
					success : function(result) {
						if($.trim(result.EX_RESULT) == "S"){
							$("span.day").html("("+Number($.trim(result.EX_ZMON))+"개월 "+Number($.trim(result.EX_ZDAY))+"일)");
							$("#exZmon").val(Number($.trim(result.EX_ZMON)));
							$("#exDay").val(Number($.trim(result.EX_ZDAY)));
						}else{
							alert(result.EX_MESSAGE);
						}
					}
				});
			}
		});

		$.callSaveRequest = function(){
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/diligenceMng/leaveReinstatementProcess.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				success : function(result) {
					alert(result.EX_MESSAGE);
					if(result.EX_RESULT == "S"){
						$("#btnBack").click();
					}else{
						//에러처리
					}
				}
			});
		};

		$.initSet = function(){
			if("<c:out value="${params.dataLoad}"/>" == "Y"){

				var form = $("#leaveReinstatementForm");

				form.append("<span class=\"rowInfo\"></span>");

				<c:forEach items="${keySet}" var="key">
					form.find("span.rowInfo").append("<input type=\"hidden\" name=\"SEL_<c:out value='${key}'/>\" value=\"<c:out value="${params[key]}"/>\"/>");
					$("#apprLineForm").append("<input type=\"hidden\" name=\"<c:out value='${key}'/>\" value=\"<c:out value="${params[key]}"/>\"/>");
				</c:forEach>

				$("input[name=requestDiv]").val("<c:out value='${params.MASSN}'/>");
				$("input[name=leaveType]").val("<c:out value='${params.MASSG}'/>");

				<fmt:parseDate var="dateString" value="${params.BEGDA}" pattern="yyyyMMdd" />
				$("input[name=startDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");

				<fmt:parseDate var="dateString" value="${params.ENDDA}" pattern="yyyyMMdd" />
				$("input[name=endDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");

				<fmt:parseDate var="dateString" value="${params.RETDT}" pattern="yyyyMMdd" />
				$("input[name=reinstateDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");

				$("input[name=comment]").val("<c:out value="${params.COMET}"/>");

				$("#endDate").change();
			}
		};

		$.setParam = function(eventId){

			$("#ajaxForm").html("");

			<%//리스트에서 선택한 원본 데이터 %>
			if("<c:out value="${params.dataLoad}"/>" == "Y"){
				$("#ajaxForm").append("<span class=\"rowInfo\"></span>");
				$("#ajaxForm").find("span.rowInfo").html($("#leaveReinstatementForm").find("span.rowInfo").html());
			}

			<%//결재라인 데이터 처리%>
			$("#ajaxForm").append("<span class=\"apprInfo\"></span>");

			var apprCnt = 0;
			$("#leaveReinstatementForm").find("span.apprInfo").each(function(index){
				$("#ajaxForm").find("span.apprInfo").append($(this).html());
				apprCnt++;
			});
			$("#ajaxForm").find("span.apprInfo").append("<input type=\"hidden\" name=\"apprCnt\" value=\""+apprCnt+"\"/>");

			<%//그외 추가 데이터%>
			$("#ajaxForm").append("<span class=\"etcInfo\"></span>");

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"exFlag\" value=\""+"<c:out value="${params.exFlag}"/>"+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"eventId\" value=\""+eventId+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"page\" value=\""+page+"\"/>");

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"requestDiv\" value=\""+$("#leaveReinstatementForm").find("input[name=requestDiv]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"leaveType\" value=\""+$("#leaveReinstatementForm").find("input[name=leaveType]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startDate\" value=\""+$("#leaveReinstatementForm").find("input[name=startDate]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endDate\" value=\""+$("#leaveReinstatementForm").find("input[name=endDate]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"reinstateDate\" value=\""+$("#leaveReinstatementForm").find("input[name=reinstateDate]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"exZmon\" value=\""+$("#leaveReinstatementForm").find("input[name=exZmon]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"exZday\" value=\""+$("#leaveReinstatementForm").find("input[name=exZday]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"comment\" value=\""+$("#leaveReinstatementForm").find("input[name=comment]").val()+"\"/>");

			$.callSaveRequest();
		};

		$.initSet();
	});
})(jQuery);
</script>
<form id="leaveReinstatementForm" name="leaveReinstatementForm" method="post" action="" >

<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>휴복직 신청/조회</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
		<a href="#" class="button_img01" id="btnDelete"><span>삭제</span></a>
	</div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">
			결재라인&nbsp;&nbsp;
			<a href="#" class="button_img02" id="btnLineAdd"><span><img src="/base/images/ess/plus.png" alt=""/></span></a>
			<a href="#" class="button_img02" id="btnLineDel"><span><img src="/base/images/ess/minus.png" alt=""/></span></a>
		</p>

		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" id="apprTable">
				<caption></caption>
				<colgroup>
					<col width="3%"/>
					<col width="13%"/>
					<col width="10%"/>
					<col width="*"/>
					<col width="10%"/>
					<col width="10%"/>
					<col width="10%"/>
					<col width="10%"/>
				</colgroup>
				<thead>
					<tr>
						<th></th>
						<th>사번</th>
						<th>이름</th>
						<th>부서명</th>
						<th>직책</th>
						<th>결재상태</th>
						<th>결재일</th>
						<th>결재시간</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="appr" items="${resultMapList.ET_LINE}" varStatus="status">
						<tr>
							<td><input type="checkbox" name="apprFlag" value=""/></td>
							<td>
								<span class="APPNR">
									<c:out value="${appr.APPNR}"/>
								</span>
							</td>
							<td><span class="ENAME"><c:out value="${appr.ENAME}"/></span></td>
							<td><span class="ORGTX"><c:out value="${appr.ORGTX}"/></span></td>
							<td><span class="COTXT"><c:out value="${appr.COTXT}"/></span></td>
							<td><span class="ASTAT"><c:out value="${appr.ASTATT}"/></span></td>
							<td>
								<span class="ADATE">
									<fmt:parseDate var="dateString" value="${appr.ADATE}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</span>
							</td>
							<td>
								<span class="ATIME">
									<c:out value="${appr.ATIME}"/>
								</span>
							</td>
							<span class="apprInfo">
								<c:forEach items="${apprKeySet}" var="key">
									<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${appr[key] }"/>"/>
								</c:forEach>
							</span>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</span>
	</div>

	<c:if test="${!empty resultRefuse }">
		<div class="table_box">
			<p class="f_title" style="padding-bottom:10px">반려사유</p>
			<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<colgroup>
						<col width="10%"/>
						<col width="*"/>
					</colgroup>
					<tbody>
						<tr>
							<th class="list03_td_bg">반려사유</th>
							<td class="list03_td">
								<textarea rows="5" style="width:95%" readonly><c:out value="${resultRefuse.EX_TEXT }"/></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</span>
		</div>
	</c:if>

	<div class="list03">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="10%"/>
				<col width="*"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">신청구분</th>
					<td class="list03_td">
						<c:forEach var="result" items="${requestDivList}">
							<c:if test="${result.KEY eq params.MASSN}">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="requestDiv" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">휴직유형</th>
					<td class="list03_td">
						<c:forEach var="result" items="${leaveTypeList}">
							<c:if test="${result.KEY eq params.MASSG}">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="leaveType" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">휴직 기간</th>
					<td class="list03_td">
						<fmt:parseDate var="dateString" value="${params.BEGDA}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						&nbsp;~&nbsp;
						<fmt:parseDate var="dateString" value="${params.ENDDA}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />&nbsp;
						<span class="day"></span>

						<input type="hidden" name="startDate" id="startDate" value=""/>
						<input type="hidden" name="endDate" id="endDate" value=""/>
						<input type="hidden" name="exZmon" id="exZmon" value=""/>
						<input type="hidden" name="exZday" id="exZday" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">휴(복)직 희망일</th>
					<td class="list03_td">
						<fmt:parseDate var="dateString" value="${params.RETDT}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						<input type="hidden" name="reinstateDate" id="reinstateDate"  value=""/>
					</td>
				</tr>
				<tr>
					<th rowspan="2" class="list03_td_bg">휴직 사유</th>
					<td class="list03_td">
						<c:out value="${params.COMET}"/>
						<input type="hidden" name="comment" value="" />
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<form id="apprLineForm" name="apprLineForm" method="post">
	<input type="hidden" name="menuType" value="PT033"/>
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>