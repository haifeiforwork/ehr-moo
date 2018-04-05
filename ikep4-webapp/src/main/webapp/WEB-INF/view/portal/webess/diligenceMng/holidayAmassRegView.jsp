<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		$("#searchButton").click(function() {
			$("#holidayAmassForm").submit();
		});

		$("#btnSave, #btnRequest").click(function(){

			var anzhlG = parseFloat("<c:out value="${resultAfterData.EX_QUONUM.ANZHL_G}"/>");
			var anzhlI = parseFloat("<c:out value="${resultAfterData.EX_QUONUM.ANZHL_I}"/>");

			if(anzhlG - anzhlI < 0){
				alert("정산일수가 마이너스입니다. 적치일수를 확인하세요.");
				return;
			}

			if(this.id == "btnSave"){
				$.setParam("SAVE");
			}else if(this.id == "btnRequest"){
				$.setParam("REQUEST");
			}
		});

		$("input[name=exAdd]").blur(function(){

			var anzhlK = parseFloat("<c:out value="${resultAfterData.EX_ANZHI_K}"/>");
			var duty   = parseFloat($("input[name=exDuty]").val());
			var add    = parseFloat($("input[name=exAdd]").val());
			var anzhlI = parseFloat($("input[name=anzhlI]").val());
			var anzhlJ = parseFloat($("input[name=anzhlJ]").val());
			var ok     = parseFloat($("input[name=exOk]").val());

			if(add > ok){
				alert("추가 가능한 적치 일수는 "+ok+"일 입니다. 확인 하시기 바랍니다.");
				$("input[name=exAdd]").val(add.toFixed(1));
				return false;
			} else {

				anzhlI = duty + add;
				anzhlJ = anzhlK - anzhlI;

				$("input[name=exAdd]").val(add.toFixed(1));
				$("input[name=anzhlI]").val(anzhlI.toFixed(1));
				$("input[name=anzhlJ]").val(anzhlJ.toFixed(1));
			}
		});
		
		$.setParam = function(eventId){

			$("#ajaxForm").html("");

			<%//그외 추가 데이터%>
			$("#ajaxForm").append("<span class=\"etcInfo\"></span>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"eventId\" value=\""+eventId+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"imBegda\" value=\"<c:out value="${resultData.EX_BEGDA}"/>\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"imEndda\" value=\"<c:out value="${resultData.EX_ENDDA}"/>\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"anzhlI\" value=\""+$("#holidayAmassForm").find("input[name=anzhlI]").val()+"\"/>");
			$.callSaveRequest();
		};

		$.callSaveRequest = function(){
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/diligenceMng/holidayAmassProcess.do'/>",
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
	});
})(jQuery);;
</script>
<style>
input.w70px {width:70px;}
</style>
<c:choose>
	<c:when test="${params.scrResult eq 'E'}">
		<div id="wrap">
			<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>연월차 적치 신청/조회</h1>
			<div class="f_title"><c:out value="${params.scrMessage}"/></div>
		</div>
	</c:when>
	<c:otherwise>
		<form id="holidayAmassForm" name="holidayAmassForm" method="post" action="<c:url value='/portal/moorimess/diligenceMng/holidayAmassRegView.do'/>" >
		<div id="wrap">
			<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>연월차 적치 신청/조회</h1>

			<c:set var="exInfo" value="${resultOnCreate.EX_INFO }"/>

			<div class="table_box">
				<div class="list02">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr>
								<th scope="row" width="7%" class="list02_td_bg">소속</th>
								<td width="15%" class="list02_td"><c:out value="${exInfo.BUTXT }"/></td>
								<th scope="row" width="7%" class="list02_td_bg">부서</th>
								<td width="*" class="list02_td"><c:out value="${exInfo.ORGTX }"/></td>
								<th scope="row" width="7%" class="list02_td_bg">직급</th>
								<td width="15%" class="list02_td"><c:out value="${resultOnCreate.EX_ZIKGUB }"/></td>
								<th scope="row" width="7%" class="list02_td_bg">연차기산일</th>
								<td width="15%" class="list02_td">
									<fmt:parseDate var="dateString" value="${resultOnCreate.EX_A5DAT}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div class="table_box">
				<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="11%">적치 연월차<br/>(A)</th>
								<th scope="col" width="11%">발생월차<br/>(B)</th>
								<th scope="col" width="11%">사용연월차<br/>(C)</th>
								<th scope="col" width="*">잔여 연월차<br/>(D) = (A) + (B) - (C)</th>
								<th scope="col" width="11%">마이너스 쿼터</th>
								<th scope="col" width="11%">발생 기본 연차<br/>(E)</th>
								<th scope="col" width="11%">발생 근속 연차<br/>(F)</th>
								<th scope="col" width="11%">적치가능일수<br/>(D) + (E) + (F)</th>
								<th scope="col" width="5%">상태</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="etQuonum" value="${resultData.ET_QUONUM }"/>
							<c:forEach items="${etQuonum}" var="result">
								<td><c:out value="${result.ANZHL_A}"/></td>
								<td><c:out value="${result.ANZHL_B}"/></td>
								<td><c:out value="${result.ANZHL_C}"/></td>
								<td><c:out value="${result.ANZHL_D}"/></td>
								<td><c:out value="${result.ANZHL_K}"/></td>
								<td><c:out value="${result.ANZHL_E}"/></td>
								<td><c:out value="${result.ANZHL_F}"/></td>
								<td><c:out value="${result.ANZHL_G}"/></td>
								<td><c:out value="${result.ANZHL_H}"/></td>
							</c:forEach>
						</tbody>
					</table>
				</span>
			</div>

			<c:if test="${resultData.EX_SAVE eq 'TRUE' || resultData.EX_REQUEST eq 'TRUE'}">
				<div class="button_box">
					<c:if test="${resultData.EX_SAVE eq 'TRUE' }"><a href="#" class="button_img01" id="btnSave"><span>저장</span></a></c:if>
					<c:if test="${resultData.EX_REQUEST eq 'TRUE' }"><a href="#" class="button_img01" id="btnRequest"><span>신청</span></a></c:if>
				</div>
			</c:if>

			<c:set var="exQuonum" value="${resultAfterData.EX_QUONUM }"/>

			<div class="table_box">
				<div class="list03">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr>
								<th scope="row" width="10%" class="list03_td_bg">적치기간</th>
								<td width="*" colspan="3" class="list03_td"><c:out value="${resultData.EX_QUONUM_DATE }"/></td>
							</tr>
							<tr>
								<th scope="row" width="10%" class="list03_td_bg">의무적치일수</th>
								<td width="35%" class="list03_td">
									<input type="text" name="exDuty" class="input disabled f_right w70px" readonly value="<fmt:formatNumber value="${fn:trim(resultAfterData.EX_DUTY) }" pattern="##0.0"/>">
								</td>
								<th scope="row" width="10%" class="list03_td_bg">추가적치일수</th>
								<td width="*" class="list03_td">
									<c:choose>
										<c:when test="${fn:toUpperCase(fn:trim(resultAfterData.EX_DIS_ADD)) eq 'TRUE'}">
											<c:set var="disabled" value="disabled"/>
											<c:set var="readonly" value="readonly"/>
										</c:when>
										<c:otherwise>
											<c:set var="disabled" value=""/>
											<c:set var="readonly" value=""/>
										</c:otherwise>
									</c:choose>
									<input type="text" name="exAdd" class="input w70px f_right <c:out value="${disabled }"/>" value="<c:out value="${fn:trim(resultAfterData.EX_ADD) }"/>" <c:out value="${readonly }"/>/>
								</td>
							</tr>
							<tr>
								<th scope="row" width="10%" class="list03_td_bg">최종적치일수</th>
								<td width="35%" class="list03_td">
									<input type="text" name="anzhlI" class="input disabled f_right w70px" readonly value="<fmt:formatNumber value="${fn:trim(exQuonum.ANZHL_I) }" pattern="##0.0"/>">
								</td>
								<th scope="row" width="10%" class="list03_td_bg">정산일수</th>
								<td width="*" class="list03_td">
									<input type="text" name="anzhlJ" class="input disabled f_right w70px" readonly value="<fmt:formatNumber value="${fn:trim(exQuonum.ANZHL_J) }" pattern="##0.0"/>">
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="f_title">
					<p>【 1 】신청 가능한 추가 적치일수는  <input type="text" name="exOk" class="input disabled f_right w70px" readonly value="<fmt:formatNumber value="${fn:trim(resultAfterData.EX_OK) }" pattern="##0.0"/>">일 입니다.</p>
					<p>【 2 】사무기술직 적치신청 변경</p>
					<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;적치 가능 일수 최소 20일 적치</p>
					<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;20일 이하인 경우 -> 100% 적치</p>
					<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;20일 초과인 경우 -> 20일 이상 적치</p>
				</div>
			</div>
		</div>
		</form>
		<form id="ajaxForm" name="ajaxForm" method="post">
		</form>
	</c:otherwise>
</c:choose>

<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>