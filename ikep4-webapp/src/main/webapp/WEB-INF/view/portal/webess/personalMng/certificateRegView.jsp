<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		$("#btnBack").click(function(){

			//if(!confirm("저장하지 않은 데이터가 손실됩니다.\n이전화면으로 이동하시겠습니까?")){
			//	return;
			//}

			$("#certificateEmpForm").attr("method", "post");
			$("#certificateEmpForm").attr("action", "<c:url value='/portal/moorimess/personalMng/certificateList.do'/>");
			$("#certificateEmpForm").submit();
		});

		$("#btnRequest").click(function(){
			$.setParam("REQUEST");
		});

		$.callSaveRequest = function(){
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalMng/certificateProcess.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				success : function(result) {
					alert(result.EX_MESSAGE);
					if(result.EX_RESULT == "S"){
						$("#certificateEmpForm").attr("method", "post");
						$("#certificateEmpForm").attr("action", "<c:url value='/portal/moorimess/personalMng/certificateList.do'/>");
						$("#certificateEmpForm").submit();
					}else{
						//에러처리
					}
				}
			});
		};

		$.initSet = function(){

			var form = $("#certificateEmpForm");

			form.append("<span class=\"rowInfo\"></span>");

			<c:forEach items="${keySet}" var="key">
				form.find("span.rowInfo").append("<input type=\"hidden\" name=\"SEL_<c:out value='${key}'/>\" value=\"<c:out value="${params[key]}"/>\"/>");
			</c:forEach>

			$("input[name=requestDate]").val("<c:out value="${params.toDay}"/>");
			$("input[name=address]").val("<c:out value="${params.ADDRESS}"/>");
			$("input[name=staff]").val("<c:out value="${params.ZDAMG}"/>");

			$("input[name=requestDate]").parents("td").append("<c:out value="${params.toDay}"/>");
			$("input[name=address]").parents("td").append("<c:out value="${params.ADDRESS}"/>");
			$("input[name=staff]").parents("td").append("<c:out value="${params.ZDAMG}"/>");
		};

		$.setParam = function(eventId){

			$("#ajaxForm").html("");

			<%//리스트에서 선택한 원본 데이터 %>
			$("#ajaxForm").append("<span class=\"rowInfo\"></span>");
			$("#ajaxForm").find("span.rowInfo").html($("#certificateEmpForm").find("span.rowInfo").html());
			
			

			$.setInputParameter();

			$.callSaveRequest();
		};

		$.setInputParameter = function(){
			var name, val;

			if($("#ajaxForm").find("span.etcInfo").length == 0){
				$("#ajaxForm").append("<span class=\"etcInfo\"></span>");
			}

			$("#certificateEmpForm").find(".parameter").each(function(){
				name = $(this).attr("name");
				val = $(this).val();

				$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\""+name+"\" value=\""+val+"\"/>");
			});
			
			<%//결재라인 데이터 처리%>
			$("#ajaxForm").append("<span class=\"apprInfo\"></span>");

			var apprCnt = 0;
			$("#certificateEmpForm").find("span.apprInfo").each(function(index){
				$("#ajaxForm").find("span.apprInfo").append($(this).html());
				apprCnt++;
			});
			$("#ajaxForm").find("span.apprInfo").append("<input type=\"hidden\" name=\"apprCnt\" value=\""+apprCnt+"\"/>");
		};

		$.initSet();
		
		$("select[name=receive]").change(function(event, params){
			if(this.value == "2"){
				$("select[name=copy] option[value=<c:out value='01'/>]").attr("selected", "selected");
				$("select[name=copy]").attr("disabled", "disabled");
			}else{
				$("select[name=copy]").removeAttr('disabled');
			}
		});
	});
})(jQuery);
</script>
<form id="certificateEmpForm" name="certificateEmpForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>제증명서 신청</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
		<a href="#" class="button_img01" id="btnRequest"><span>신청</span></a>
	</div>
	
	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">
			결재라인
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
							<td <c:if test="${appr.EFLAG eq 'X' }"> style="background-color:#eff7fb"</c:if>>
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

	<div class="list03">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="15%"/>
				<col width="35%"/>
				<col width="15%"/>
				<col width="35%"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">신청일자</th>
					<td class="list03_td">
						<input type="hidden" name="requestDate" class="parameter" value=""/>
					</td>
					<th class="list03_td_bg">신청구분</th>
					<td class="list03_td">
						<select name="certi" style="width:150px" class="parameter">
							<c:forEach var="result" items="${certiList}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">수신방법</th>
					<td class="list03_td">
						<select name="receive" style="width:150px" class="parameter">
							<c:forEach var="result" items="${receiveList}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
					<th class="list03_td_bg">신청부수</th>
					<td class="list03_td">
						<select name="copy" style="width:150px" class="parameter">
							<c:forEach var="result" items="${copyList }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">용도</th>
					<td class="list03_td">
						<select name="cpurps" style="width:150px" class="parameter">
							<c:forEach var="result" items="${useforList}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
					<th class="list03_td_bg">제출처</th>
					<td class="list03_td" colspan="3">
						<input type="text" name="submission" class="input w70per parameter" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">담당자</th>
					<td class="list03_td" colspan="3">
						<input type="hidden" name="staff" class="parameter" value=""/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<b>□ 제증명서(재직증명서, 원천징수영수증) 제출 시, 반드시 회사인감 원본 여부를 제출기관에 사전 확인 후 신청하시기 바랍니다.</b><br>
   
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