<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/validation/validation.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		$("input[name=etRcbeg], input[name=etRcend]").each(function(){
			$(this).datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		});
		
		$("#btnSave, #btnDel").click(function(){
			
			$("#ajaxForm").html("");
			
			if(this.id == "btnDel"){
				if($("input[name=etRentFlag]:checked").length == 0){
					alert("라인을 선택해 주시기 바랍니다.");
					return;
				}
				
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"DELETE\"/>");
				var seq = -1;
				var itDelLineCnt = 0;
				$("input[name=etRentFlag]").each(function(){
					if($(this).is(":checked")){
						seq = Number($("input[name=etRentFlag]").index(this)) + 1;
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
				}else if($(this).attr("type") == 'checkbox'){
					if($(this).is(":checked")){
						$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\"X\"/>");
					}else{
						$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\"\"/>");
					}
					
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
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imTot\" value=\"<c:out value="${params.EX_TOT}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imRegno\" value=\"<c:out value="${params.REGNO}"/>\"/>");
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/payMng/callPY003OninputPop11.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					alert(result.EX_MESSAGE);
					if($.trim(result.EX_RESULT) == "S"){
						opener.$jq.refresh(4);
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
			var tab = $("#rent").find("tbody");
			var sb = "";
			
			$("#rent").find(".empty").remove();
			
			sb +="<tr>";
			sb +="	<td>";
			sb +="		<input type=\"radio\" name=\"etRentFlag\">";
			sb +="	</td>";
			sb +="	<td>";
			sb +="		<select name=\"etPnsty\" class=\"parameter w90per\">";
			<c:forEach items="${resultMap.ET_PNSTY }" var="result">
			<c:if test="${'01' eq result.KEY}">
			sb +="				<option value=\"<c:out value="${result.KEY }"/>\"><c:out value="${result.VALUE }"/></option>";
			</c:if>
			</c:forEach>
			sb +="		</select>";
			sb +="	</td>";
			sb +="	<td>";
			sb +="		<input type=\"text\" name=\"etLdnam\" value=\"\" class=\"input w90per parameter f_left\"/>";
			sb +="	</td>";
			sb +="	<td>";
			sb +="		<input type=\"text\" name=\"etLdreg\" value=\"\" class=\"input w90per parameter f_left\"/>";
			sb +="	</td>";
			sb +="	<td>";
			sb +="		<input type=\"text\" name=\"etAddre\" value=\"\" class=\"input w90per parameter f_left\"/>";
			sb +="	</td>";
			sb +="	";
			sb +="	<td>";
			sb +="		<select name=\"houty\" class=\"parameter w90per\">";
			<c:forEach items="${resultMap.ET_HOUTY }" var="result">
			sb +="				<option value=\"<c:out value="${result.KEY }"/>\"><c:out value="${result.VALUE }"/></option>";
			</c:forEach>
			sb +="		</select>";
			sb +="	</td>";
			sb +="	<td>";
			sb +="		<input type=\"text\" name=\"hostx\" value=\"\" class=\"input w90per parameter f_center\"/>";
			sb +="	</td>";
			sb +="	<td>";
			sb +="		<input type=\"text\" name=\"etRcbeg\" value=\"\" class=\"input w70px parameter datepicker\"/>";
			sb +="		<img src=\"<c:url value="/base/images/icon/ic_cal.gif"/>\" align=\"absmiddle\" alt=\"calendar\"/>";
			sb +="	</td>";
			sb +="	<td>";
			sb +="		<input type=\"text\" name=\"etRcend\" value=\"\" class=\"input w70px parameter datepicker\"/>";
			sb +="		<img src=\"<c:url value="/base/images/icon/ic_cal.gif"/>\" align=\"absmiddle\" alt=\"calendar\"/>";
			sb +="	</td>";
			sb +="	<td>";
			sb +="		<input type=\"text\" name=\"etnam\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"10\"/>";
			sb +="		<span class=\"rowInfo\">";
			<c:forEach items="${rentKeySet}" var="key">
			sb +="				<input type=\"hidden\" name=\"<c:out value="${key}"/>\" value=\"\"/>";
			</c:forEach>
			sb +="		</span>";
			sb +="	</td>";
			sb +="</tr>";
			
			tab.append(sb);
			
			$("input[name=etRcbeg]:last").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
			$("input[name=etRcend]:last").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		});
		
		$.initSet = function(){ };
		
		$.initSet();
	});
})(jQuery);
</script>
<form id="rentForm" name="rentForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>월세 입력</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnAdd"><span>추가등록</span></a>
		<a href="#" class="button_img01" id="btnDel"><span>삭제</span></a>
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
		<a href="#" class="button_img01" id="btnClose"><span>닫기</span></a>
	</div>
	
	<table width="100%" border="0" class="margin_delimiter">
		<tbody>
			<tr>
				<td width="10"><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
				<td>총급여액 : <fmt:formatNumber value="${params.EX_TOT}" groupingUsed="true"/>원</td>
			</tr>
		</tbody>
	</table>
	
	<div class="list01">
		<table border="0" cellpadding="0" cellspacing="0" id="rent">
			<colgroup>
				<col width="30px"/>
				<col width="100px"/>
				<col width="100px"/>
				<col width="120px"/>
				<col width="*"/>
				
				<col width="130px"/>
				<col width="110px"/>
				<col width="120px"/>
				<col width="120px"/>
				<col width="140px"/>
			</colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>유형</th>
					<th>임대인 성명</th>
					<th>주민등록번호</th>
					<th>임대계약서 상 주소지</th>
					
					<th>주택유형</th>
					<th>주택계약면적(㎡)</th>
					<th>계약시작일</th>
					<th>계약종료일</th>
					<th>계약기간 내 월세총액</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty resultMap.ET_RENT}">
						<c:forEach items="${resultMap.ET_RENT}" var="etRent" varStatus="inx">
							<tr>
								<td>
									<input type="radio" name="etRentFlag">
								</td>
								<td>
									<select name="etPnsty" class="parameter w90per">
										<c:forEach items="${resultMap.ET_PNSTY }" var="result">
											<c:if test="${'01' eq result.KEY}">
												<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq etRent.ET_PNSTY}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
											</c:if>
										</c:forEach>
									</select>
								</td>
								<td>
									<input type="text" name="etLdnam" value="<c:out value="${etRent.ET_LDNAM }"/>" class="input w90per parameter f_left"/>
								</td>
								<td>
									<input type="text" name="etLdreg" value="<c:out value="${etRent.ET_LDREG }"/>" class="input w90per parameter f_left"/>
								</td>
								<td>
									<input type="text" name="etAddre" value="<c:out value="${etRent.ET_ADDRE }"/>" class="input w90per parameter f_left"/>
								</td>
								
								<td>
									<select name="houty" class="parameter w90per">
										<c:forEach items="${resultMap.ET_HOUTY }" var="result">
											<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq etRent.HOUTY}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
										</c:forEach>
									</select>
								</td>
								<td>
									<input type="text" name="hostx" value="<c:out value="${etRent.HOSTX}"/>" class="input w90per parameter f_center"/>
								</td>
								<td>
									<fmt:parseDate var="dateString" value="${etRent.ET_RCBEG}" pattern="yyyyMMdd" />
									<input type="text" name="etRcbeg" value="<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />" class="input w70px parameter datepicker"/>
									<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
								</td>
								<td>
									<fmt:parseDate var="dateString" value="${etRent.ET_RCEND}" pattern="yyyyMMdd" />
									<input type="text" name="etRcend" value="<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />" class="input w70px parameter datepicker"/>
									<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
								</td>
								<td>
									<input type="text" name="etnam" value="<fmt:formatNumber value="${etRent.ETNAM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="10"/>
									<span class="rowInfo">
										<c:forEach items="${rentKeySet}" var="key">
											<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${etRent[key]}"/>"/>
										</c:forEach>
									</span>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="empty f_left">
							<td colspan="10">
								【 월세 입력 내역이 없습니다. 추가등록 버튼을 이용해 등록 하십시오. 】
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
				<td>※ 총급여액이 7천만원 이하인 근로자에 한해 월세액 공제가 가능합니다.</td>
			</tr>
			<tr>
				<td width="10"><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
				<td>※ 임대차계약서에 명시된 계약시작일과 계약종료일을 반드시 입력바랍니다.</td>
			</tr>
			<tr>
				<td width="10"></td>
				<td>ex) 2012.6.28 ~ 2014.6.27</td>
			</tr>
			<tr>
				<td width="10"><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
				<td>※ 본인 또는 기본공제 대상자(배우자 등)가 계약을 체결한 경우도 가능합니다.</td>
			</tr>
			<tr>
				<td width="10"><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
				<td>※ 주민등록상 주소지와 월세 주소지가 같아야 하며, 세대원도 신청이 가능합니다.</td>
			</tr>
			<tr>
				<td width="10"><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
				<td>※  임대인의 주민등록번호는 숫자만 입력바랍니다. ex)9102112012345</td>
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