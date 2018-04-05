<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/validation/validation.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		$("#btnSave, #btnDel").click(function(){
			
			var confirmFlag = false;
			
			$("#ajaxForm").html("");
			
			if(this.id == "btnDel"){
				if($("input[name=etCredFlag]:checked").length == 0){
					alert("라인을 선택해 주시기 바랍니다.");
					return;
				}
				
				if(confirm("삭제하시겠습니까?")){
					confirmFlag = true;
				}
				
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"DELETE\"/>");
				var seq = -1;
				var itDelLineCnt = 0;
				$("input[name=etCredFlag]").each(function(){
					if($(this).is(":checked")){
						seq = Number($("input[name=etCredFlag]").index(this)) + 1;
						$("#ajaxForm").append("<input type=\"hidden\" name=\"itDelLine_SEQ\" value=\""+seq+"\"/>");
						itDelLineCnt++;
					}
				});
				
				$("#ajaxForm").append("<input type=\"hidden\" name=\"itDelLineCnt\" value=\""+itDelLineCnt+"\"/>");
				
			}else{
				
				if(confirm("전통시장 및 대중교통 사용분의 경우 반드시 체크해주시기 바랍니다.\n저장하시겠습니까?")){
					confirmFlag = true;
				}
				
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"SAVE\"/>");
			}
			
			if(!confirmFlag){
				return;
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
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/payMng/callPY003OninputPop6.do'/>",
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
			var tab = $("#credit").find("tbody");
			var sb = "";
			
			$("#credit").find(".empty").remove();
			
			sb += "<tr>";
			sb += "	<td>";
			sb += "		<input type=\"radio\" name=\"etCredFlag\">";
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
			sb += "		<select class=\"parameter w90per callFillPop\" name=\"gubun\">";
			<c:forEach items="${resultMap.ET_GUBUN }" var="result">
			sb += "				<option value=\"<c:out value="${result.KEY }"/>\"><c:out value="${result.VALUE }"/></option>";
			</c:forEach>
			sb += "		</select>";
			sb += "	</td>";

			/* sb += "	<td>";
			sb += "		<select class=\"parameter w90per callFillPop\" name=\"exprd\">";
			<c:forEach items="${resultMap.ET_EXPRD }" var="result">
			sb += "				<option value=\"<c:out value="${result.KEY }"/>\"><c:out value="${result.VALUE }"/></option>";
			</c:forEach>
			sb += "		</select>";
			sb += "	</td>"; */
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"otnam\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"10\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"otoam\" value=\"\" class=\"input w90per f_right amount parameter disabled\" data-maxlength=\"10\" disabled/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"checkbox\" name=\"trdmk\" class=\"parameter callFillPop\" disabled/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"checkbox\" name=\"cctra\" class=\"parameter callFillPop\" disabled/>";
			sb += "		<span class=\"rowInfo\">";
			<c:forEach items="${creditKeySet}" var="key">
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
			}else if($(this).attr("name") == "gubun" && this.tagName == "SELECT"){
				imEventId = "GUBUN";
			/* }else if($(this).attr("name") == "exprd" && this.tagName == "SELECT"){
				imEventId = "EXPRD"; */
			}else if($(this).attr("name") == "trdmk" && this.tagName == "INPUT"){
				imEventId = "TRDMK_CHECK";
			}else if($(this).attr("name") == "cctra" && this.tagName == "INPUT"){
				imEventId = "CCTRA_CHECK";
			}
			
			$("#ajaxForm").html("");
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\""+imEventId+"\"/>");
			
			var tr = $(this).parents("tr");
			var allTr = $("#credit").find("tbody").find("tr");
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
				url : "<c:url value='/portal/moorimess/payMng/callPY003FillPop6.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					
					var exCred = result.EX_CRED;
					var exLayout = result.EX_LAYOUT;
					
					if($.trim(result.EX_RESULT) == "S"){
						
					}else{
						alert(result.EX_MESSAGE);
					}
					
					if(exLayout.TRDMK == "TRUE"){
						tr.find("input[name=trdmk]").attr("disabled", "disabled");
					}else{
						tr.find("input[name=trdmk]").removeAttr("disabled");
					}
					
					if(exLayout.CCTRA == "TRUE"){
						tr.find("input[name=cctra]").attr("disabled", "disabled");
					}else{
						tr.find("input[name=cctra]").removeAttr("disabled");
					}
					
					if(exLayout.OTOAM == "TRUE"){
						tr.find("input[name=otoam]").attr("disabled", "disabled");
						tr.find("input[name=otoam]").addClass("disabled");
					}else{
						tr.find("input[name=otoam]").removeAttr("disabled");
						tr.find("input[name=otoam]").removeClass("disabled");
					}
					
					<c:forEach var="key" items="${creditKeySet}">
						<c:choose>
							
							<c:when test="${key eq 'KDSVH'}">
							tr.find("select[name=kdsvh] option[value="+exCred["<c:out value="${key}"/>"]+"]").attr("selected", "selected");
							</c:when>
							
							<c:when test="${key eq 'DNAME'}">
							tr.find("select[name=dname] option[value="+exCred["<c:out value="${key}"/>"]+"]").attr("selected", "selected");
							</c:when>
							
							<c:when test="${key eq 'REGNOT'}">
							tr.find("input[name=regnot]").val(exCred["<c:out value="${key}"/>"]);
							</c:when>
							
							<c:when test="${key eq 'GUBUN'}">
							tr.find("select[name=gubun] option[value="+exCred["<c:out value="${key}"/>"]+"]").attr("selected", "selected");
							</c:when>
							
							
							
							<c:when test="${key eq 'OTNAM'}">
							tr.find("input[name=otnam]").val(exCred["<c:out value="${key}"/>"].addComma());
							</c:when>
							
							<c:when test="${key eq 'OTOAM'}">
							tr.find("input[name=otoam]").val(exCred["<c:out value="${key}"/>"].addComma());
							</c:when>
							
							<c:when test="${key eq 'TRDMK'}">
								if(exCred["<c:out value="${key}"/>"] == "X"){
									tr.find("input[name=trdmk]").attr("checked", "checked");
								}else{
									tr.find("input[name=trdmk]").removeAttr("checked");
								}
							</c:when>
							
							<c:when test="${key eq 'CCTRA'}">
								if(exCred["<c:out value="${key}"/>"] == "X"){
									tr.find("input[name=cctra]").attr("checked", "checked");
								}else{
									tr.find("input[name=cctra]").removeAttr("checked");
								}
							</c:when>
							
						</c:choose>
						tr.find("span.rowInfo").find("input[name=<c:out value="${key}"/>]").val(exCred["<c:out value="${key}"/>"]);
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
<form id="creditForm" name="creditForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>신용카드 입력</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnAdd"><span>추가등록</span></a>
		<a href="#" class="button_img01" id="btnDel"><span>삭제</span></a>
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
		<a href="#" class="button_img01" id="btnClose"><span>닫기</span></a>
	</div>
	
	<div class="list01">
		<table border="0" cellpadding="0" cellspacing="0" id="credit">
			<colgroup>
				<col width="30px"/>
				<col width="100px"/>
				<col width="100px"/>
				<col width="130px"/>
				<col width="180px"/>
				
				<!-- <col width="*"/> -->
				<col width="100px"/>
				<col width="100px"/>
				<col width="90px"/>
				<col width="90px"/>
			</colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>관계</th>
					<th>이름</th>
					<th>주민등록번호</th>
					<th>구분</th>
					
					<!-- <th>비용 기간</th> -->
					<th>국세청자료</th>
					<th>그밖의 자료</th>
					<th>전통시장 여부</th>
					<th>대중교통 여부</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty resultMap.ET_CRED}">
						<c:forEach items="${resultMap.ET_CRED }" var="etCred" varStatus="inx">
							<c:set var="exCred" value="${resultMap.EX_CRED_LIST[inx.index] }"/>
							<c:set var="exLayout" value="${resultMap.EX_LAYOUT_LIST[inx.index] }"/>
							<tr>
								<td>
									<input type="radio" name="etCredFlag">
								</td>
								<td>
									<select class="parameter w90per" name="kdsvh">
										<c:forEach items="${resultMap.ET_KDSVH}" var="result">
											<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq exCred.KDSVH}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
										</c:forEach>
									</select>
								</td>
								<td>
									<select class="parameter w90per callFillPop" name="dname">
										<c:forEach items="${resultMap.ET_FAM_LIST}" var="result">
											<c:if test="${result.SUBTY eq exCred.KDSVH}">
												<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq exCred.DNAME}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
											</c:if>
										</c:forEach>
									</select>
								</td>
								<td>
									<input type="text" name="regnot" value="<c:out value="${exCred.REGNOT }"/>" class="input w90per parameter disabled f_center" readonly/>
								</td>
								<td>
									<select class="parameter w90per callFillPop" name="gubun">
										<c:forEach items="${resultMap.ET_GUBUN }" var="result">
											<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq exCred.GUBUN}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
										</c:forEach>
									</select>
								</td>
								
								<%-- <td>
									<select class="parameter w90per callFillPop" name="exprd">
										<c:forEach items="${resultMap.ET_EXPRD }" var="result">
											<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq exCred.EXPRD}"> selected </c:if>><c:out value="${result.VALUE }"/></option>
										</c:forEach>
									</select>
								</td> --%>
								<td>
									<input type="text" name="otnam" value="<fmt:formatNumber value="${exCred.OTNAM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="10"/>
								</td>
								<td>
									<input type="text" name="otoam" value="<fmt:formatNumber value="${exCred.OTOAM}" groupingUsed="true"/>" class="input w90per f_right amount parameter <c:if test="${exLayout.OTOAM eq 'TRUE' }"> disabled </c:if>" data-maxlength="10" <c:if test="${exLayout.OTOAM eq 'TRUE' }"> disabled </c:if>/>
								</td>
								<td>
									<input type="checkbox" name="trdmk" class="parameter callFillPop"<c:if test="${exCred.TRDMK eq 'X'}"> checked </c:if> <c:if test="${exLayout.TRDMK eq 'TRUE' }"> disabled </c:if>/>
								</td>
								<td>
									<input type="checkbox" name="cctra" class="parameter callFillPop"<c:if test="${exCred.CCTRA eq 'X'}"> checked </c:if> <c:if test="${exLayout.CCTRA eq 'TRUE' }"> disabled </c:if>/>
									<span class="rowInfo">
										<c:forEach items="${creditKeySet}" var="key">
											<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${exCred[key]}"/>"/>
										</c:forEach>
									</span>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="empty f_left">
							<td colspan="9">
								【 신용카드 입력 내역이 없습니다. 추가등록 버튼을 이용해 등록 하십시오. 】
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
				<td>※ 전통시장 및 대중교통 사용분의 경우 반드시 체크해주시기 바랍니다.</td>
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