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
				if($("input[name=etMediFlag]:checked").length == 0){
					alert("라인을 선택해 주시기 바랍니다.");
					return;
				}
				
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"DELETE\"/>");
				var seq = -1;
				var itDelLineCnt = 0;
				$("input[name=etMediFlag]").each(function(){
					if($(this).is(":checked")){
						seq = Number($("input[name=etMediFlag]").index(this)) + 1;
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
				url : "<c:url value='/portal/moorimmss/diligencePayMng/callPY003OninputPop2.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					if($.trim(result.EX_RESULT) == "S"){
						alert(result.EX_MESSAGE);
						opener.$.refresh(4);
						self.close();
					}else{
						if(tempBtn == "btnDel"){
							alert("삭제되었습니다.");
							opener.$.refresh(4);
							self.close();
						}else{
							alert(result.EX_MESSAGE);
						}
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
			var tab = $("#medical").find("tbody");
			var sb = "";
			
			$("#medical").find(".empty").remove();
			
			sb += "<tr>";
			sb += "	<td>";
			sb += "		<input type=\"radio\" name=\"etMediFlag\">";
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
			sb += "		<select class=\"parameter w90per callFillPop\" name=\"mepcd\">";
			<c:forEach items="${resultMap.ET_MEPCD}" var="result">
			sb += "				<option value=\"<c:out value="${result.KEY }"/>\"><c:out value="${result.VALUE }"/></option>";
			</c:forEach>
			sb += "		</select>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"menum\" value=\"\" class=\"input w90per parameter disabled f_center\" disabled/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"menam\" value=\"\" class=\"input w90per parameter disabled f_center\" disabled/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"mecdtt\" value=\"\" class=\"input w90per parameter f_center\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"mecda\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"10\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"checkbox\" name=\"glchk\" class=\"parameter\" disabled/>";
			sb += "	</td>";
			sb += "	";
			sb += "	<td>";
			sb += "		<input type=\"checkbox\" name=\"surpg\" class=\"parameter\"/>";
			sb += "		<span class=\"rowInfo\">";
			<c:forEach items="${medicalKeySet}" var="key">
			sb += "				<input type=\"hidden\" name=\"<c:out value="${key}"/>\" value=\"<c:out value="${exMedi[key]}"/>\"/>";
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
		
		$(".callFillPop").live("change", function(){
			
			var imEventId = "";
			
			if($(this).attr("name") == "mepcd" && this.tagName == "SELECT"){
				imEventId = "MEPCD";
			}
			
			$("#ajaxForm").html("");
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\""+imEventId+"\"/>");
			
			var tr = $(this).parents("tr");
			var allTr = $("#medical").find("tbody").find("tr");
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
				url : "<c:url value='/portal/moorimmss/diligencePayMng/callPY003FillPop2.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					
					var exMedi = result.EX_MEDI;
					var exLayout = result.EX_LAYOUT;
					
					if($.trim(result.EX_RESULT) == "S"){
						
					}else{
						alert(result.EX_MESSAGE);
					}
					
					if(exLayout.GLCHK == "TRUE"){
						tr.find("input[name=glchk]").attr("disabled", "disabled");
					}else{
						tr.find("input[name=glchk]").removeAttr("disabled");
					}
					
					if(exLayout.MENUM == "TRUE"){
						tr.find("input[name=menum]").attr("disabled", "disabled");
						tr.find("input[name=menum]").addClass("disabled");
					}else{
						tr.find("input[name=menum]").removeAttr("disabled");
						tr.find("input[name=menum]").removeClass("disabled");
					}
					
					if(exLayout.MENAM == "TRUE"){
						tr.find("input[name=menam]").attr("disabled", "disabled");
						tr.find("input[name=menam]").addClass("disabled");
					}else{
						tr.find("input[name=menam]").removeAttr("disabled");
						tr.find("input[name=menam]").removeClass("disabled");
					}
					
					<c:forEach var="key" items="${medicalKeySet}">
						<c:choose>
							<c:when test="${key eq 'KDSVH'}">
							tr.find("select[name=kdsvh] option[value="+exMedi["<c:out value="${key}"/>"]+"]").attr("selected", "selected");
							</c:when>
							
							<c:when test="${key eq 'DNAME'}">
							tr.find("select[name=dname] option[value="+exMedi["<c:out value="${key}"/>"]+"]").attr("selected", "selected");
							</c:when>
							
							<c:when test="${key eq 'REGNOT'}">
							tr.find("input[name=regnot]").val(exMedi["<c:out value="${key}"/>"]);
							</c:when>
							
							<c:when test="${key eq 'MEPCD'}">
							tr.find("select[name=mepcd] option[value="+exMedi["<c:out value="${key}"/>"]+"]").attr("selected", "selected");
							</c:when>
							
							<c:when test="${key eq 'MENUM'}">
							tr.find("input[name=menum]").val(exMedi["<c:out value="${key}"/>"]);
							</c:when>
							
							<c:when test="${key eq 'MENAM'}">
							tr.find("input[name=menam]").val(exMedi["<c:out value="${key}"/>"]);
							</c:when>
							
							<c:when test="${key eq 'MECDTT'}">
							tr.find("input[name=mecdtt]").val(Number(exMedi["<c:out value="${key}"/>"]));
							</c:when>
							
							<c:when test="${key eq 'MECDA'}">
							tr.find("input[name=mecda]").val(exMedi["<c:out value="${key}"/>"].addComma());
							</c:when>
							
							<c:when test="${key eq 'GLCHK'}">
								if(exMedi["<c:out value="${key}"/>"] == "X"){
									tr.find("input[name=glchk]").attr("checked", "checked");
								}else{
									tr.find("input[name=glchk]").removeAttr("checked");
								}
							</c:when>
							
							<c:when test="${key eq 'SURPG'}">
							if(exMedi["<c:out value="${key}"/>"] == "X"){
								tr.find("input[name=surpg]").attr("checked", "checked");
							}else{
								tr.find("input[name=surpg]").removeAttr("checked");
							}
							</c:when>
						</c:choose>
						tr.find("span.rowInfo").find("input[name=<c:out value="${key}"/>]").val(exMedi["<c:out value="${key}"/>"]);
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
<form id="medicalForm" name="medicalForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>의료비 입력</h1>

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
				<td width="10"><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
				<td>3% 금액 : <fmt:formatNumber value="${params.EX_TO3}" groupingUsed="true"/>원</td>
			</tr>
		</tbody>
	</table>
	
	<div class="list01">
		<table border="0" cellpadding="0" cellspacing="0" id="medical">
			<colgroup>
				<col width="30px"/>
				<col width="100px"/>
				<col width="100px"/>
				<col width="130px"/>
				<col width="180px"/>
				
				<col width="*"/>
				<col width="100px"/>
				<col width="60px"/>
				<col width="130px"/>
				<col width="100px"/>
				
				<col width="80px"/>
			</colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>관계</th>
					<th>이름</th>
					<th>주민등록번호</th>
					<th>의료증빙유형</th>
					
					<th>지급처 사업자등록번호</th>
					<th>지급처 상호</th>
					<th>건수</th>
					<th>카드(현금영수증)금액</th>
					<th>안경콘텍트구입<br/>여부</th>
					
					<th>난임시술비<br/>여부</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty resultMap.ET_MEDI}">
						<c:forEach items="${resultMap.ET_MEDI }" var="etMedi" varStatus="inx">
							<c:set var="exMedi" value="${resultMap.EX_MEDI_LIST[inx.index] }"/>
							<c:set var="exLayout" value="${resultMap.EX_LAYOUT_LIST[inx.index] }"/>
							<tr>
								<td>
									<input type="radio" name="etMediFlag">
								</td>
								<td>
									<select class="parameter w90per" name="kdsvh">
										<c:forEach items="${resultMap.ET_KDSVH}" var="result">
											<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq exMedi.KDSVH}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
										</c:forEach>
									</select>
								</td>
								<td>
									<select class="parameter w90per" name="dname">
										<c:forEach items="${resultMap.ET_FAM_LIST}" var="result">
											<c:if test="${result.SUBTY eq exMedi.KDSVH}">
												<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq exMedi.DNAME}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
											</c:if>
										</c:forEach>
									</select>
								</td>
								<td>
									<input type="text" name="regnot" value="<c:out value="${exMedi.REGNOT }"/>" class="input w90per parameter disabled f_center" readonly/>
								</td>
								<td>
									<select class="parameter w90per callFillPop" name="mepcd">
										<c:forEach items="${resultMap.ET_MEPCD}" var="result">
											<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq exMedi.MEPCD}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
										</c:forEach>
									</select>
								</td>
								
								<td>
									<input type="text" name="menum" value="<c:out value="${exMedi.MENUM }"/>" class="input w90per parameter f_center <c:if test="${exLayout.MENUM eq 'TRUE' }"> disabled </c:if>" <c:if test="${exLayout.MENUM eq 'TRUE' }"> disabled </c:if>/>
								</td>
								<td>
									<input type="text" name="menam" value="<c:out value="${exMedi.MENAM }"/>" class="input w90per parameter f_center <c:if test="${exLayout.MENAM eq 'TRUE' }"> disabled </c:if>" <c:if test="${exLayout.MENAM eq 'TRUE' }"> disabled </c:if>/>
								</td>
								<td>
									<input type="text" name="mecdtt" value="<c:out value="${exMedi.MECDTT}"/>" class="input w90per parameter f_center"/>
								</td>
								<td>
									<input type="text" name="mecda" value="<fmt:formatNumber value="${exMedi.MECDA}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="10"/>
								</td>
								<td>
									<input type="checkbox" name="glchk" class="parameter"<c:if test="${exMedi.GLCHK eq 'X'}"> checked </c:if> <c:if test="${exLayout.GLCHK eq 'TRUE' }"> disabled </c:if>/>
								</td>
								
								<td>
									<input type="checkbox" name="surpg" class="parameter"<c:if test="${exMedi.SURPG eq 'X'}"> checked </c:if>/>
									<span class="rowInfo">
										<c:forEach items="${medicalKeySet}" var="key">
											<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${exMedi[key]}"/>"/>
										</c:forEach>
									</span>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="empty f_left">
							<td colspan="11">
								【 의료비 입력 내역이 없습니다. 추가등록 버튼을 이용해 등록 하십시오. 】
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
				<td width="10"></td>
				<td>※ 지급처 사업자등록번호는 - 빼고 입력하시기 바랍니다.
				<br>※ 난임시술 증빙은 해당 의료기관에서 발급 받아야합니다.
				</td>
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
</body>
</html>