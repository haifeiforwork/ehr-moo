<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/validation/validation.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		$("#btnSave, #btnDel").click(function(){

			$("#ajaxForm").html("");
			var tempBtn = this.id;
			if(this.id == "btnDel"){
				if($("input[name=etEduFlag]:checked").length == 0){
					alert("라인을 선택해 주시기 바랍니다.");
					return;
				}
				
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"DELETE\"/>");
				var seq = -1;
				var itDelLineCnt = 0;
				$("input[name=etEduFlag]").each(function(){
					if($(this).is(":checked")){
						seq = Number($("input[name=etEduFlag]").index(this)) + 1;
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
						if($(this).attr("name") == "exchk"){
							$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\"F\"/>");
						}else{
							$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\"X\"/>");
						}
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
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/payMng/callPY003OninputPop3.do'/>",
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
			var tab = $("#education").find("tbody");
			var sb = "";
			
			$("#education").find(".empty").remove();
			
			sb += "<tr>";
			sb += "	<td>";
			sb += "		<input type=\"radio\" name=\"etEduFlag\">";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<select class=\"parameter w90per callFillPop\" name=\"kdsvh\">";
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
			sb += "		<select class=\"parameter w90per callFillPop\" name=\"otelv\">";
			<c:forEach items="${resultMap.ET_EDULV}" var="result">
			sb += "				<option value=\"<c:out value="${result.KEY }"/>\"><c:out value="${result.VALUE }"/></option>";
			</c:forEach>
			sb += "		</select>";
			sb += "	</td>";
			sb += "	";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"otnam\" value=\"\" class=\"input w90per parameter f_right amount\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"otoam\" value=\"\" class=\"input w90per parameter f_right amount\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"checkbox\" name=\"hndid\" class=\"parameter\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"checkbox\" name=\"suchk\" class=\"parameter\"/>";
			sb += "		<span class=\"rowInfo\">";
			<c:forEach items="${educationKeySet}" var="key">
			sb += "				<input type=\"hidden\" name=\"<c:out value="${key}"/>\" value=\"<c:out value="${exEdu[key]}"/>\"/>";
			</c:forEach>
			sb += "		</span>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"checkbox\" name=\"exchk\" class=\"parameter\"/>";
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
			
			if($(this).attr("name") == "kdsvh" && this.tagName == "SELECT"){
				imEventId = "KDSVH";
			}else if($(this).attr("name") == "otelv" && this.tagName == "SELECT"){
				imEventId = "OTELV";
			}else if($(this).attr("name") == "dname" && this.tagName == "SELECT"){
				imEventId = "DNAME";
			}
			
			$("#ajaxForm").html("");
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\""+imEventId+"\"/>");
			
			var tr = $(this).parents("tr");
			var allTr = $("#education").find("tbody").find("tr");
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
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/payMng/callPY003FillPop3.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					
					var exEdu = result.EX_EDU;
					var exLayout = result.EX_LAYOUT;
					var exError = result.EX_ERROR;
					
					if(exError == "X" || exError == "H"){
						$("div.exErrorMsg").html(result.EX_TEXT);
					}
					
					if($.trim(result.EX_RESULT) == "S"){
						
					}else{
						alert(result.EX_MESSAGE);
					}
					
					if(exLayout.HNDID == "TRUE"){
						tr.find("input[name=hndid]").attr("disabled", "disabled");
					}else{
						tr.find("input[name=hndid]").removeAttr("disabled");
					}
					
					<c:forEach var="key" items="${educationKeySet}">
						<c:choose>
							<c:when test="${key eq 'KDSVH'}">
							tr.find("select[name=kdsvh] option[value="+exEdu["<c:out value="${key}"/>"]+"]").attr("selected", "selected");
							</c:when>
							
							<c:when test="${key eq 'DNAME'}">
							tr.find("select[name=dname] option[value="+exEdu["<c:out value="${key}"/>"]+"]").attr("selected", "selected");
							</c:when>
							
							<c:when test="${key eq 'REGNOT'}">
							tr.find("input[name=regnot]").val(exEdu["<c:out value="${key}"/>"]);
							</c:when>
							
							<c:when test="${key eq 'OTELV'}">
							tr.find("select[name=otelv] option[value="+exEdu["<c:out value="${key}"/>"]+"]").attr("selected", "selected");
							</c:when>
							
							<c:when test="${key eq 'OTNAM'}">
							tr.find("input[name=otnam]").val(exEdu["<c:out value="${key}"/>"].addComma());
							</c:when>
							
							<c:when test="${key eq 'OTOAM'}">
							tr.find("input[name=otoam]").val(exEdu["<c:out value="${key}"/>"].addComma());
							</c:when>
							
							<c:when test="${key eq 'HNDID'}">
								if(exEdu["<c:out value="${key}"/>"] == "X"){
									tr.find("input[name=hndid]").attr("checked", "checked");
								}else{
									tr.find("input[name=hndid]").removeAttr("checked");
								}
							</c:when>
							
							<c:when test="${key eq 'SUCHK'}">
								if(exEdu["<c:out value="${key}"/>"] == "X"){
									tr.find("input[name=suchk]").attr("checked", "checked");
								}else{
									tr.find("input[name=suchk]").removeAttr("checked");
								}
							</c:when>
						</c:choose>
						tr.find("span.rowInfo").find("input[name=<c:out value="${key}"/>]").val(exEdu["<c:out value="${key}"/>"]);
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
<form id="educationForm" name="educationForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>교육비 입력</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnAdd"><span>추가등록</span></a>
		<a href="#" class="button_img01" id="btnDel"><span>삭제</span></a>
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
		<a href="#" class="button_img01" id="btnClose"><span>닫기</span></a>
	</div>
	
	<div class="list01">
		<table border="0" cellpadding="0" cellspacing="0" id="education">
			<colgroup>
				<col width="30px"/>
				<col width="100px"/>
				<col width="100px"/>
				<col width="130px"/>
				<col width="180px"/>
				
				<col width="100px"/>
				<col width="120px"/>
				<col width="100px"/>
				<col width="100px"/>
				<col width="100px"/>
			</colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>관계</th>
					<th>이름</th>
					<th>주민등록번호</th>
					<th>교육수준</th>
					
					<th>국세청자료</th>
					<th>그밖의 자료</th>
					<th>장애인교육여부</th>
					<th>교복구입여부</th>
					<th>체험학습비</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty resultMap.ET_EDU}">
						<c:forEach items="${resultMap.ET_EDU }" var="etEdu" varStatus="inx">
							<c:set var="exEdu" value="${resultMap.EX_EDU_LIST[inx.index] }"/>
							<c:set var="exLayout" value="${resultMap.EX_LAYOUT_LIST[inx.index] }"/>
							
							<tr>
								<td>
									<input type="radio" name="etEduFlag">
								</td>
								<td>
									<select class="parameter w90per callFillPop" name="kdsvh">
										<c:forEach items="${resultMap.ET_KDSVH}" var="result">
											<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq exEdu.KDSVH}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
										</c:forEach>
									</select>
								</td>
								<td>
									<select class="parameter w90per callFillPop" name="dname">
										<c:forEach items="${resultMap.ET_FAM_LIST}" var="result">
											<c:if test="${result.SUBTY eq exEdu.KDSVH}">
												<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq exEdu.DNAME}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
											</c:if>
										</c:forEach>
									</select>
								</td>
								<td>
									<input type="text" name="regnot" value="<c:out value="${exEdu.REGNOT }"/>" class="input w90per parameter disabled f_center" readonly/>
								</td>
								<td>
									<select class="parameter w90per callFillPop" name="otelv">
										<c:forEach items="${resultMap.ET_EDULV}" var="result">
											<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq exEdu.OTELV}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
										</c:forEach>
									</select>
								</td>
								
								<td>
									<input type="text" name="otnam" value="<fmt:formatNumber value="${exEdu.OTNAM}" groupingUsed="true"/>" class="input w90per parameter f_right amount"/>
								</td>
								<td>
									<input type="text" name="otoam" value="<fmt:formatNumber value="${exEdu.OTOAM}" groupingUsed="true"/>" class="input w90per parameter f_right amount"/>
								</td>
								<td>
									<input type="checkbox" name="hndid" class="parameter"<c:if test="${exEdu.HNDID eq 'X'}"> checked </c:if> <c:if test="${exLayout.HNDID eq 'TRUE'}"> disabled </c:if>/>
								</td>
								<td>
									<input type="checkbox" name="suchk" class="parameter"<c:if test="${exEdu.SUCHK eq 'X'}"> checked </c:if>/>
									<span class="rowInfo">
										<c:forEach items="${educationKeySet}" var="key">
											<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${exEdu[key]}"/>"/>
										</c:forEach>
									</span>
								</td>
								<td>
									<input type="checkbox" name="exchk" class="parameter"<c:if test="${exEdu.EXCHK eq 'F'}"> checked </c:if>/>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="empty f_left">
							<td colspan="10">
								【 교육비 입력 내역이 없습니다. 추가등록 버튼을 이용해 등록 하십시오. 】
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<div class="f_title exErrorMsg"></div>
	</div>
	<table width="100%" border="0" class="margin_delimiter">
	<tbody>
		<tr>
			<td width="10"></td>
			<td>
			※ 중·고등학교 학생 교복비(체육복 포함) 공제됩니다. (학생 1명당 연 50만원 한도로 함)<br>
			※ 초, 중, 고등학교 학생 체험학습비 공제 됩니다. (학생 1명당 연 30만원 한도로 함)<br>
			※ 학자금 대출 공제 : 재학시 타인의 기본공제자로써 대출받은 학자금을 소득공제 받은경우 해당금액은 공제대상이 아닙니다.
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