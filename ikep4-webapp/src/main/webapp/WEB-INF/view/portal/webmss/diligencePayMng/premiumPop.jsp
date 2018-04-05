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
				if($("input[name=etInsFlag]:checked").length == 0){
					alert("라인을 선택해 주시기 바랍니다.");
					return;
				}
				
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"DELETE\"/>");
				var seq = -1;
				var itDelLineCnt = 0;
				$("input[name=etInsFlag]").each(function(){
					if($(this).is(":checked")){
						seq = Number($("input[name=etInsFlag]").index(this)) + 1;
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
				url : "<c:url value='/portal/moorimmss/diligencePayMng/callPY003OninputPop5.do'/>",
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
			var tab = $("#premium").find("tbody");
			var sb = "";
			
			$("#premium").find(".empty").remove();
			
			sb += "<tr>";
			sb += "	<td>";
			sb += "		<input type=\"radio\" name=\"etInsFlag\">";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<select class=\"parameter w90per\" name=\"kdsvh\">";
			<c:forEach items="${resultMap.ET_KDSVH}" var="result">
			sb += "				<option value=\"<c:out value="${result.KEY }"/>\"><c:out value="${result.VALUE }"/></option>";
			</c:forEach>
			sb += "		</select>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<select class=\"parameter w90per callFillPop\" name=\"dname\">";
			sb += "		</select>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"regnot\" value=\"\" class=\"input w90per parameter disabled f_center\" readonly/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"otnam\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"10\"/>";
			sb += "	</td>";
			sb += "	";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"otoam\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"10\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"checkbox\" name=\"othan\" class=\"parameter\" disabled/>";
			sb += "		<span class=\"rowInfo\">";
			<c:forEach items="${premiumKeySet}" var="key">
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
		
		$(".callFillPop").live("change", function(){
			
			var imEventId = "";
			
			if($(this).attr("name") == "dname" && this.tagName == "SELECT"){
				imEventId = "DNAME";
				
				var index = $("select[name=dname]").index(this);
				
				if($(this).val() == ""){
					$("input[name=regnot]").eq(index).val("");
				}else{
					$("input[name=regnot]").eq(index).val($(this).val().substring(0,6)+"-"+$(this).val().substring(6,13));
				}
			}
			
			$("#ajaxForm").html("");
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\""+imEventId+"\"/>");
			
			var tr = $(this).parents("tr");
			var allTr = $("#premium").find("tbody").find("tr");
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
				url : "<c:url value='/portal/moorimmss/diligencePayMng/callPY003FillPop5.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					
					var exIns = result.EX_INS;
					var exLayout = result.EX_LAYOUT;
					
					if($.trim(result.EX_RESULT) == "S"){
						
					}else{
						alert(result.EX_MESSAGE);
					}
					
					if(exLayout.OTHAN == "TRUE"){
						tr.find("input[name=othan]").attr("disabled", "disabled");
					}else{
						tr.find("input[name=othan]").removeAttr("disabled");
					}
					
					<c:forEach var="key" items="${premiumKeySet}">
						<c:choose>
						
							<c:when test="${key eq 'KDSVH'}">
							tr.find("select[name=kdsvh] option[value="+exIns["<c:out value="${key}"/>"]+"]").attr("selected", "selected");
							</c:when>
						
							<c:when test="${key eq 'DNAME'}">
							tr.find("select[name=dname] option[value="+exIns["<c:out value="${key}"/>"]+"]").attr("selected", "selected");
							</c:when>
							
							<c:when test="${key eq 'REGNOT'}">
							tr.find("input[name=regnot]").val(exIns["<c:out value="${key}"/>"]);
							</c:when>
							
							<c:when test="${key eq 'OTNAM'}">
							tr.find("input[name=otnam]").val(exIns["<c:out value="${key}"/>"].addComma());
							</c:when>
							
							<c:when test="${key eq 'OTOAM'}">
							tr.find("input[name=otoam]").val(exIns["<c:out value="${key}"/>"].addComma());
							</c:when>
							
							<c:when test="${key eq 'OTHAN'}">
								if(exIns["<c:out value="${key}"/>"] == "X"){
									tr.find("input[name=othan]").attr("checked", "checked");
								}else{
									tr.find("input[name=othan]").removeAttr("checked");
								}
							</c:when>
							
						</c:choose>
						tr.find("span.rowInfo").find("input[name=<c:out value="${key}"/>]").val(exIns["<c:out value="${key}"/>"]);
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
<form id="premiumForm" name="premiumForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>보험료 입력</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnAdd"><span>추가등록</span></a>
		<a href="#" class="button_img01" id="btnDel"><span>삭제</span></a>
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
		<a href="#" class="button_img01" id="btnClose"><span>닫기</span></a>
	</div>
	
	<div class="list01">
		<table border="0" cellpadding="0" cellspacing="0" id="premium">
			<colgroup>
				<col width="30px"/>
				<col width="100px"/>
				<col width="100px"/>
				<col width="130px"/>
				<col width="180px"/>
				
				<col width="*"/>
				<col width="100px"/>
			</colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>관계</th>
					<th>이름</th>
					<th>주민등록번호</th>
					<th>국세청자료</th>
					
					<th>그밖의 자료</th>
					<th>장애인보험</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty resultMap.ET_INS}">
						<c:forEach items="${resultMap.ET_INS }" var="etIns" varStatus="inx">
							<c:set var="exIns" value="${resultMap.EX_INS_LIST[inx.index] }"/>
							<c:set var="exLayout" value="${resultMap.EX_LAYOUT_LIST[inx.index] }"/>
							<tr>
								<td>
									<input type="radio" name="etInsFlag">
								</td>
								<td>
									<select class="parameter w90per" name="kdsvh">
										<c:forEach items="${resultMap.ET_KDSVH}" var="result">
											<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq exIns.KDSVH}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
										</c:forEach>
									</select>
								</td>
								<td>
									<select class="parameter w90per callFillPop" name="dname">
										<c:forEach items="${resultMap.ET_FAM_LIST}" var="result">
											<c:if test="${result.SUBTY eq exIns.KDSVH}">
												<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq exIns.DNAME}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
											</c:if>
										</c:forEach>
									</select>
								</td>
								<td>
									<input type="text" name="regnot" value="<c:out value="${exIns.REGNOT }"/>" class="input w90per parameter disabled f_center" readonly/>
								</td>
								<td>
									<input type="text" name="otnam" value="<fmt:formatNumber value="${exIns.OTNAM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="10"/>
								</td>
								
								<td>
									<input type="text" name="otoam" value="<fmt:formatNumber value="${exIns.OTOAM}" groupingUsed="true"/>" class="input w90per f_right amount parameter <c:if test="${exLayout.OTOAM eq 'TRUE' }"> disabled </c:if>" data-maxlength="10" <c:if test="${exLayout.OTOAM eq 'TRUE' }"> disabled </c:if>/>
								</td>
								<td>
									<input type="checkbox" name="othan" class="parameter"<c:if test="${exIns.OTHAN eq 'X'}"> checked </c:if> <c:if test="${exLayout.OTHAN eq 'TRUE' }"> disabled </c:if>/>
									<span class="rowInfo">
										<c:forEach items="${premiumKeySet}" var="key">
											<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${exIns[key]}"/>"/>
										</c:forEach>
									</span>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="empty f_left">
							<td colspan="7">
								【 보험료 입력 내역이 없습니다. 추가등록 버튼을 이용해 등록 하십시오. 】
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
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