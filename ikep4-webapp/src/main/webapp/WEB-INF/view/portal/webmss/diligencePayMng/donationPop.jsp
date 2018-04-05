<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<title>관리자 연말정산</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/ep_style.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/ep_button.css"/>" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>

<script type="text/javascript" src="<c:url value="/base/js/jquery/validation/validation.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/mss/utils.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		$("#btnSave, #btnDel").click(function(){

			$("#ajaxForm").html("");
			var tempBtn = this.id;
			if(this.id == "btnDel"){
				if($("input[name=etDonaFlag]:checked").length == 0){
					alert("라인을 선택해 주시기 바랍니다.");
					return;
				}
				
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"DELETE\"/>");
				var seq = -1;
				var itDelLineCnt = 0;
				$("input[name=etDonaFlag]").each(function(){
					if($(this).is(":checked")){
						seq = Number($("input[name=etDonaFlag]").index(this)) + 1;
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
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"paramCnt\" value=\""+$("#ajaxForm").find("input[name=kdsvh]").length+"\"/>");
			
			<c:forEach items="${taxKeySet}" var="key">
				$("#ajaxForm").append("<input type=\"hidden\" name=\"exTax_<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_TAX[key]}"/>\"/>");
			</c:forEach>
			
			$("span.rowInfo").each(function(){
				$("#ajaxForm").append($(this).html());
			});
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imZyear\" value=\"<c:out value="${params.EX_YEAR}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imRegno\" value=\"<c:out value="${params.REGNO}"/>\"/>");
			
			$.ajax({
				url : "<c:url value='/portal/moorimmss/diligencePayMng/callPY003OninputPop4.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					if($.trim(result.EX_RESULT) == "S"){
						if(result.EX_MESSAGE == "" && tempBtn == "btnDel"){
							alert("삭제되었습니다.");
						}else{
							alert(result.EX_MESSAGE);
						}
						opener.$.refresh(4);
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
			var tab = $("#donation").find("tbody");
			var sb = "";
			
			$("#donation").find(".empty").remove();
			
			sb += "<tr>";
			sb += "	<td>";
			sb += "		<input type=\"radio\" name=\"etDonaFlag\">";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<select class=\"parameter w90per\" name=\"kdsvh\">";
			<c:forEach items="${resultMap.ET_KDSVH}" var="result">
			sb += "				<option value=\"<c:out value="${result.KEY }"/>\"><c:out value="${result.VALUE }"/></option>";
			</c:forEach>
			sb += "		</select>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<select class=\"parameter w90per\" name=\"dname\">";
			sb += "		</select>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"regnot\" value=\"\" class=\"input w90per parameter disabled f_center\" readonly/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<select class=\"parameter w90per\" name=\"docod\">";
			<c:forEach items="${resultMap.ET_CODE}" var="result">
			sb += "				<option value=\"<c:out value="${result.KEY }"/>\"><c:out value="${result.VALUE }"/></option>";
			</c:forEach>
			sb += "		</select>";
			sb += "	</td>";
			sb += "	";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"docodt\" value=\"\" class=\"input w90per parameter disabled f_center\" readonly/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"donum\" value=\"\" class=\"input w90per parameter f_left\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"donam\" value=\"\" class=\"input w90per parameter f_left\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"dorec\" value=\"\" class=\"input w90per parameter f_left\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"docntt\" value=\"\" class=\"input w90per parameter f_center\"/>";
			sb += "	</td>";
			sb += "	";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"doamt\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"10\"/>";
			sb += "		<span class=\"rowInfo\">";
			<c:forEach items="${donationKeySet}" var="key">
			sb += "				<input type=\"hidden\" name=\"<c:out value="${key}"/>\" value=\"\"/>";
			</c:forEach>
			sb += "		</span>";
			sb += "	</td>";
			sb += "</tr>";
			
			tab.append(sb);
		});
		
		$("select[name=kdsvh]").live("change", function(){
			var index = $("select[name=kdsvh]").index(this);
			var target = $("select[name=dname]").eq(index);
			
			target.html("");
			<c:forEach items="${resultMap.ET_FAM_LIST}" var="result">
				if("<c:out value="${result.SUBTY}"/>" == $(this).val()){
					target.append("<option value=\"<c:out value="${result.KEY}"/>\"><c:out value="${result.VALUE}"/></option>");
				}
			</c:forEach>
			$("input[name=regnot]").eq(index).val("");
		});
		
		$("select[name=dname]").live("change", function(){
			var index = $("select[name=dname]").index(this);
			
			if($(this).val() == ""){
				$("input[name=regnot]").eq(index).val("");
			}else{
				$("input[name=regnot]").eq(index).val($(this).val().substring(0,6)+"-"+$(this).val().substring(6,13));
			}
		});
		
		$("select[name=docod]").live("change", function(){
			var index = $("select[name=docod]").index(this);
			
			if($(this).val() == ""){
				$("input[name=docodt]").eq(index).val("");
			}else{
				$("input[name=docodt]").eq(index).val($(this).val());
			}
		});
		
		$(".callFillPop").live("change", function(){
			
			var imEventId = "";
			
			if($(this).attr("name") == "kdsvh" && this.tagName == "SELECT"){
				imEventId = "KDSVH";
			}
			
			$("#ajaxForm").html("");
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\""+imEventId+"\"/>");
			
			var tr = $(this).parents("tr");
			var allTr = $("#donation").find("tbody").find("tr");
			var index = Number(allTr.index(tr));
			var rowInfo = $("span.rowInfo").eq(index).html();
			
			$("#ajaxForm").append(rowInfo);
			
			tr.find(".parameter").each(function(){
				if($(this).hasClass("amount")){
					$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""+$(this).val().clearComma()+"\"/>");
				}else if($(this).attr("type") == "checkbox"){
					if($(this).is(":checked")){
						$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\"X\"/>");
					}else{
						$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\"\"/>");
					}
				}else{
					
					$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""+$(this).val()+"\"/>");
				}
			});
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imYear\" value=\"<c:out value="${params.EX_YEAR}"/>\"/>");
			
			$.ajax({
				url : "<c:url value='/portal/moorimmss/diligencePayMng/callPY003FillPop4.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					
					var exDona = result.EX_DONA;
					var exLayout = result.EX_LAYOUT;
					
					if($.trim(result.EX_RESULT) == "S"){
						
					}else{
						alert(result.EX_MESSAGE);
					}
					
					<c:forEach var="key" items="${donationKeySet}">
						<c:choose>
							<c:when test="${key eq 'KDSVH'}">
							tr.find("select[name=kdsvh] option[value="+exDona["<c:out value="${key}"/>"]+"]").attr("selected", "selected");
							</c:when>
							
							<c:when test="${key eq 'DNAME'}">
							tr.find("select[name=dname] option[value="+exDona["<c:out value="${key}"/>"]+"]").attr("selected", "selected");
							</c:when>
							
							<c:when test="${key eq 'REGNOT'}">
							tr.find("input[name=regnot]").val(exDona["<c:out value="${key}"/>"]);
							</c:when>
							
							<c:when test="${key eq 'DOCOD'}">
							tr.find("select[name=docod] option[value="+exDona["<c:out value="${key}"/>"]+"]").attr("selected", "selected");
							</c:when>
							
							<c:when test="${key eq 'DOCODT'}">
							tr.find("input[name=docodt]").val(exDona["<c:out value="${key}"/>"]);
							</c:when>
							
							<c:when test="${key eq 'DONUM'}">
							tr.find("input[name=donum]").val(exDona["<c:out value="${key}"/>"]);
							</c:when>
							
							<c:when test="${key eq 'DONAM'}">
							tr.find("input[name=donam]").val(exDona["<c:out value="${key}"/>"]);
							</c:when>
							
							<c:when test="${key eq 'DOREC'}">
							tr.find("input[name=dorec]").val(exDona["<c:out value="${key}"/>"]);
							</c:when>
							
							<c:when test="${key eq 'DOCNTT'}">
							tr.find("input[name=docntt]").val(Number(exDona["<c:out value="${key}"/>"]));
							</c:when>
							
							<c:when test="${key eq 'DOAMT'}">
							tr.find("input[name=otoam]").val(exDona["<c:out value="${key}"/>"].addComma());
							</c:when>
							
						</c:choose>
						tr.find("span.rowInfo").find("input[name=<c:out value="${key}"/>]").val(exDona["<c:out value="${key}"/>"]);
					</c:forEach>
					
				},complete : function(){
					$("#ajaxForm").html("");
				}
			});
		});
		
		$.initSet = function(){ };
		
		$.initSet();
	});
})(jQuery);
</script>
</head>
<body>
<form id="donationForm" name="donationForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>기부금 입력</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnAdd"><span>추가등록</span></a>
		<a href="#" class="button_img01" id="btnDel"><span>삭제</span></a>
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
		<a href="#" class="button_img01" id="btnClose"><span>닫기</span></a>
	</div>
	
	<div class="list01">
		<table border="0" cellpadding="0" cellspacing="0" id="donation">
			<colgroup>
				<col width="30px"/>
				<col width="100px"/>
				<col width="100px"/>
				<col width="130px"/>
				<col width="180px"/>
				
				<col width="*"/>
				<col width="140px"/>
				<col width="130px"/>
				<col width="150px"/>
				<col width="70px"/>
				
				<col width="110px"/>
			</colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>관계</th>
					<th>이름</th>
					<th>주민등록번호</th>
					<th>기부금 설명</th>
					
					<th>기부금 코드</th>
					<th>기부처 사업자등록번호</th>
					<th>기부처 법인명</th>
					<th>기부금영수증일련번호</th>
					<th>기부금 건수</th>
					
					<th>기부금액</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty resultMap.ET_DONA}">
						<c:forEach items="${resultMap.ET_DONA }" var="etDona" varStatus="inx">
							<c:set var="exDona" value="${resultMap.EX_DONA_LIST[inx.index] }"/>
							<c:set var="exLayout" value="${resultMap.EX_LAYOUT_LIST[inx.index] }"/>
							
							<tr>
								<td>
									<input type="radio" name="etDonaFlag">
								</td>
								<td>
									<select class="parameter w90per" name="kdsvh">
										<c:forEach items="${resultMap.ET_KDSVH}" var="result">
											<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq exDona.KDSVH}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
										</c:forEach>
									</select>
								</td>
								<td>
									<select class="parameter w90per" name="dname">
										<c:forEach items="${resultMap.ET_FAM_LIST}" var="result">
											<c:if test="${result.SUBTY eq exDona.KDSVH}">
												<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq exDona.DNAME}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
											</c:if>
										</c:forEach>
									</select>
								</td>
								<td>
									<input type="text" name="regnot" value="<c:out value="${exDona.REGNOT }"/>" class="input w90per parameter disabled f_center" readonly/>
								</td>
								<td>
									<select class="parameter w90per" name="docod">
										<c:forEach items="${resultMap.ET_CODE}" var="result">
											<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq exDona.DOCOD}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
										</c:forEach>
									</select>
								</td>
								
								<td>
									<input type="text" name="docodt" value="<c:out value="${exDona.DOCODT }"/>" class="input w90per parameter disabled f_center" readonly/>
								</td>
								<td>
									<input type="text" name="donum" value="<c:out value="${exDona.DONUM }"/>" class="input w90per parameter f_left"/>
								</td>
								<td>
									<input type="text" name="donam" value="<c:out value="${exDona.DONAM }"/>" class="input w90per parameter f_left"/>
								</td>
								<td>
									<input type="text" name="dorec" value="<c:out value="${exDona.DOREC }"/>" class="input w90per parameter f_left"/>
								</td>
								<td>
									<input type="text" name="docntt" value="<c:out value="${exDona.DOCNTT }"/>" class="input w90per parameter f_center"/>
								</td>
								
								<td>
									<input type="text" name="doamt" value="<fmt:formatNumber value="${exDona.DOAMT}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="10"/>
									<span class="rowInfo">
										<c:forEach items="${donationKeySet}" var="key">
											<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${exDona[key]}"/>"/>
										</c:forEach>
									</span>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="empty f_left">
							<td colspan="11">
								【 기부금 입력 내역이 없습니다. 추가등록 버튼을 이용해 등록 하십시오. 】
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
				<td>※ 지급처 사업자등록번호는 - 빼고 입력하시기 바랍니다.</td>
			</tr>
			<tr>
				<td width="10"><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
				<td>※ 정치 기부금인경우 기부처 사업자등록번호와 기부처 법인명은 입력하지 않아도 됩니다.</td>
			</tr>
			
			<tr>
				<td width="10"><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
				<td>※ 기부금 공제 우선순위</td>
			</tr>
			<tr>
				<td width="10"></td>
				<td>1순위 : 정치자금 기부금 </td>
			</tr>
			<tr>
				<td width="10"></td>
				<td>2순위 : 당해 법정기부금 </td>
			</tr>
			<tr>
				<td width="10"></td>
				<td>3순위 : 2014년 이후 지출분 중 이월 법정기부금</td>
			</tr>
			<tr>
				<td width="10"></td>
				<td>4순위 : 우리사주조합기부금</td>
			</tr>
			<tr>
				<td width="10"></td>
				<td>5순위 : 2013년 이전 지출분 중 이월 종교단체 외 지정기부금</td>
			</tr>
			<tr>
				<td width="10"></td>
				<td>6순위 : 2013년 이전 지출분 중 이월 종교단체 지정기부금	</td>
			</tr>
			<tr>
				<td width="10"></td>
				<td>7순위 : 당해 종교단체 외 지정기부금</td>
			</tr>
			<tr>
				<td width="10"></td>
				<td>8순위 : 2014년 이후 지출분 중 이월 종교단체 외 지정기부금</td>
			</tr>
			<tr>
				<td width="10"></td>
				<td>9순위 : 당해 종교단체 지정기부금</td>
			</tr>
			<tr>
				<td width="10"></td>
				<td>10순위 : 2014년 이후 지출분 중 이월 종교단체 지정기부금</td>
			</tr>
			<!-- <tr>
				<td width="10"></td>
				<td>11순위 : 2014년 이후 지출한 이월 종교단체 지정기부금</td>
			</tr> -->
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
</body>
</html>