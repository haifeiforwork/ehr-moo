<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/validation/validation.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		$("input[name=etRcbeg]").each(function(){
			$(this).datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		});
		
		$("#btnSave, #btnDel").click(function(){

			$("#ajaxForm").html("");
			
			if(this.id == "btnDel"){
				if($("input[name=etChungFlag]:checked").length == 0){
					alert("라인을 선택해 주시기 바랍니다.");
					return;
				}
				
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"DELETE\"/>");
				var seq = -1;
				var itDelLineCnt = 0;
				$("input[name=etChungFlag]").each(function(){
					if($(this).is(":checked")){
						seq = Number($("input[name=etChungFlag]").index(this)) + 1;
						$("#ajaxForm").append("<input type=\"hidden\" name=\"itDelLine_SEQ\" value=\""+seq+"\"/>");
						itDelLineCnt++;
					}
				});
				
				$("#ajaxForm").append("<input type=\"hidden\" name=\"itDelLineCnt\" value=\""+itDelLineCnt+"\"/>");
				
			}else{
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"SAVE\"/>");
			}
			
			$(".parameter").each(function(){
				if($(this).hasClass("amount")){
					$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""+$(this).val().clearComma()+"\"/>");
				}else{
					$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""+$(this).val()+"\"/>");
				}
			});
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"paramCnt\" value=\""+$("#ajaxForm").find("input[name=etPnsty]").length+"\"/>");
			
			<c:forEach items="${taxKeySet}" var="key">
				$("#ajaxForm").append("<input type=\"hidden\" name=\"exTax_<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_TAX[key]}"/>\"/>");
			</c:forEach>
			
			$("span.rowInfo").each(function(){
				$("#ajaxForm").append($(this).html());
			});
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imZyear\" value=\"<c:out value="${params.EX_YEAR}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imBegda\" value=\"<c:out value="${params.EX_BEGDA}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEndda\" value=\"<c:out value="${params.EX_ENDDA}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imTot\" value=\"<c:out value="${params.EX_TOT}"/>\"/>");
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imRegno\" value=\"<c:out value="${params.REGNO}"/>\"/>");
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/payMng/callPY003OninputPop8.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					alert(result.EX_MESSAGE);
					if($.trim(result.EX_RESULT) == "S"){
						opener.$jq.refresh(3);
						self.close();
					}else{
					}
				},complete : function(){
				}
			});
		});
		
		$("#btnClose").click(function(){
			self.close();
		});
		
		$("input.amount").live("blur", function(event) {
			if (!$(this).hasClass('keyupping')){
				$.amountValid($(this));
			}
		});
		
		$("input.amount").live("keyup", function(e) {
			$(this).addClass('keyupping');
			if(e.which == 13){
				$.amountValid($(this));
			}
			$(this).removeClass('keyupping');
		});
		
		$("#btnAdd").click(function(){
			var tab = $("#annuity").find("tbody");
			var sb = "";
			
			$("#annuity").find(".empty").remove();
			
			sb += "<tr>";
			sb += "	<td>";
			sb += "		<input type=\"radio\" name=\"etChungFlag\">";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<select name=\"etPnsty\" class=\"parameter w90per\">";
			<c:forEach items="${resultMap.ET_SLIST }" var="result">
			sb += "				<option value=\"<c:out value="${result.KEY }"/>\"><c:out value="${result.VALUE }"/></option>";
			</c:forEach>
			sb += "		</select>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<select name=\"etFinco\" class=\"parameter w90per\">";
			<c:forEach items="${resultMap.ET_BANK }" var="result">
			sb += "				<option value=\"<c:out value="${result.KEY }"/>\"><c:out value="${result.VALUE }"/></option>";
			</c:forEach>
			sb += "		</select>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"etAccno\" value=\"\" class=\"input w90per parameter f_center\"/>";
			sb += "	</td>";
			sb += "<td>";
			sb += "		<input type=\"text\" name=\"etRcbeg\" value=\"\" class=\"input datepicker w70px parameter\" readonly=\"readonly\"/>";
			sb += "		<img src=\"<c:url value="/base/images/icon/ic_cal.gif"/>\" align=\"absmiddle\" alt=\"calendar\"/>";
			sb += "</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"etnam\" value=\"\" class=\"input w90per f_right amount parameter <c:if test="${resultMap.EX_TAX.HSHLD ne 'X' }"> disabled </c:if>\" data-maxlength=\"10\" <c:if test="${resultMap.EX_TAX.HSHLD ne 'X' }"> disabled </c:if>/>";
			sb += "		<span class=\"rowInfo\">";
			<c:forEach items="${houseKeySet}" var="key">
			sb += "				<input type=\"hidden\" name=\"<c:out value="${key}"/>\" value=\"\"/>";
			</c:forEach>
			sb += "		</span>";
			sb += "	</td>";
			sb += "</tr>";
			
			tab.append(sb);
			
			$("input[name=etRcbeg]:last").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		});
		
		$.initSet = function(){ };
		
		$.initSet();
	});
})(jQuery);
</script>
<form id="houseForm" name="houseForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>주택마련저축 입력</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnAdd"><span>추가등록</span></a>
		<a href="#" class="button_img01" id="btnDel"><span>삭제</span></a>
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
		<a href="#" class="button_img01" id="btnClose"><span>닫기</span></a>
	</div>
	
	<div class="list01">
		<table border="0" cellpadding="0" cellspacing="0" id="annuity">
			<colgroup>
				<col width="30px"/>
				<col width="*"/>
				<col width="200px"/>
				<col width="200px"/>
				<col width="130px"/>
				<col width="150px"/>
			</colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>세부유형</th>
					<th>금융기관코드</th>
					<th>계좌번호</th>
					<th>가입일자</th>
					<th>금액</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty resultMap.ET_CHUNG}">
						<c:forEach items="${resultMap.ET_CHUNG }" var="etChung" varStatus="inx">
							<tr>
								<td>
									<input type="radio" name="etChungFlag">
								</td>
								<td>
									<select name="etPnsty" class="parameter w90per">
										<c:forEach items="${resultMap.ET_SLIST }" var="result">
											<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq etChung.ET_PNSTY}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
										</c:forEach>
									</select>
								</td>
								<td>
									<select name="etFinco" class="parameter w90per">
										<c:forEach items="${resultMap.ET_BANK }" var="result">
											<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq etChung.ET_FINCO}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
										</c:forEach>
									</select>
								</td>
								<td>
									<input type="text" name="etAccno" value="<c:out value="${etChung.ET_ACCNO }"/>" class="input w90per parameter f_center"/>
								</td>
								<td>
									<fmt:parseDate var="dateString" value="${etChung.ET_RCBEG}" pattern="yyyyMMdd" />
									<input type="text" name="etRcbeg" value="<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />" class="input datepicker w70px parameter" readonly="readonly"/>
									<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
								</td>
								
								<td>
									<input type="text" name="etnam" value="<fmt:formatNumber value="${etChung.ETNAM}" groupingUsed="true"/>" class="input w90per f_right amount parameter <c:if test="${resultMap.EX_TAX.HSHLD ne 'X' }"> disabled </c:if>" data-maxlength="10" <c:if test="${resultMap.EX_TAX.HSHLD ne 'X' }"> disabled </c:if>/>
									<span class="rowInfo">
										<c:forEach items="${houseKeySet}" var="key">
											<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${etChung[key]}"/>"/>
										</c:forEach>
									</span>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="empty f_left">
							<td colspan="10">
								【 청약저축 내역이 없습니다. 추가등록 버튼을 이용해 등록 하십시오. 】
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	
	<table width="100%" border="0" class="margin_delimiter">
		<tbody>
			<tr>
				<td width="10"><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
				<td>※ 본인의 가입내역만 입력가능합니다.(가족명의 가입분 공제불가)</td>
			</tr>
		</tbody>
	</table>
	
</div>
</form>
<form name="ajaxForm" id="ajaxForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>