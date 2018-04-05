<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/validation/validation.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		$("input[name=pabeg], input[name=paend], input[name=exbeg], input[name=exend]").each(function(){
			$(this).datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		});
		
		$("#btnSave, #btnDel").click(function(){

			$("#ajaxForm").html("");
			
			if(this.id == "btnDel"){
				if($("input[name=etLast2Flag]:checked").length == 0){
					alert("라인을 선택해 주시기 바랍니다.");
					return;
				}
				
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"DELETE\"/>");
				var seq = -1;
				var itDelLineCnt = 0;
				$("input[name=etLast2Flag]").each(function(){
					if($(this).is(":checked")){
						seq = Number($("input[name=etLast2Flag]").index(this)) + 1;
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
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"paramCnt\" value=\""+$("#ajaxForm").find("input[name=comnm]").length+"\"/>");
			
			<c:forEach items="${taxKeySet}" var="key">
				$("#ajaxForm").append("<input type=\"hidden\" name=\"exTax_<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_TAX[key]}"/>\"/>");
			</c:forEach>
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/payMng/callPY003OninputPop1.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					alert(result.EX_MESSAGE);
					if($.trim(result.EX_RESULT) == "S"){
						opener.$jq.refresh(1);
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
		
		$("select[name=comnm]").live("change", function(){
			var index = $("select[name=comnm]").index(this);
			$("input[name=bizno]").eq(index).val($(this).val());
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
			
			if($("input[name=etLast2Flag]").length > 4){
				alert("ddddd");
				return;
			}
			
			var tab = $("#last2").find("tbody");
			var sb = "";
			
			$("#last2").find(".empty").remove();
			
			sb += "<tr>";
			sb += "	<td>";
			sb += "		<input type=\"radio\" name=\"etLast2Flag\">";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<select name=\"comnm\" class=\"w150px parameter\">";
			<c:forEach items="${resultMap.ET_LAST }" var="result">
			sb += "				<option value=\"<c:out value="${result.KEY}"/>\"><c:out value="${result.VALUE}"/></option>";
			</c:forEach>
			sb += "		</select>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"bizno\" value=\"\" class=\"input disabled w90per f_center parameter\" readonly=\"readonly\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"pabeg\" value=\"\" class=\"input datepicker w70px parameter\" readonly=\"readonly\"/>";
			sb += "		<img src=\"<c:url value="/base/images/icon/ic_cal.gif"/>\" align=\"absmiddle\" alt=\"calendar\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"paend\" value=\"\" class=\"input datepicker w70px parameter\" readonly=\"readonly\"/>";
			sb += "		<img src=\"<c:url value="/base/images/icon/ic_cal.gif"/>\" align=\"absmiddle\" alt=\"calendar\"/>";
			sb += "	</td>";
			
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"salm\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"10\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"bonm\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"10\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"abnm\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"9\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"intm\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"9\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"retm\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"9\"/>";
			sb += "	</td>";
			
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"medm\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"9\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"npmm\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"9\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"eimm\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"9\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"outm\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"9\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"ovrm\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"9\"/>";
			sb += "	</td>";
			sb += "	";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"othm\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"9\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"fldm\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"9\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"frim\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"9\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"frem\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"9\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"sptm\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"9\"/>";
			sb += "	</td>";
			
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"exbeg\" value=\"\" class=\"input datepicker w70px parameter\" readonly=\"readonly\"/>";
			sb += "		<img src=\"<c:url value="/base/images/icon/ic_cal.gif"/>\" align=\"absmiddle\" alt=\"calendar\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"exend\" value=\"\" class=\"input datepicker w70px parameter\" readonly=\"readonly\"/>";
			sb += "		<img src=\"<c:url value="/base/images/icon/ic_cal.gif"/>\" align=\"absmiddle\" alt=\"calendar\"/>";
			sb += "	</td>";
			sb += "</tr>";
			
			tab.append(sb);
			
			$("input[name=pabeg]:last").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
			$("input[name=paend]:last").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
			$("input[name=exbeg]:last").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
			$("input[name=exend]:last").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		});
		
		$.initSet = function(){};
		
		$.initSet();
	});
})(jQuery);
</script>
<form id="workPlaceForm" name="workPlaceForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>종(전)근무지 추가</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnAdd"><span>추가등록</span></a>
		<a href="#" class="button_img01" id="btnDel"><span>삭제</span></a>
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
		<a href="#" class="button_img01" id="btnClose"><span>닫기</span></a>
	</div>
	
	<div class="list01" style="overflow-x:auto;height:200px;">
		<table border="0" cellpadding="0" cellspacing="0" style="width:2550px;" id="last2">
			<colgroup>
				<col width="30px"/>
				<col width="170px"/>
				<col width="120px"/>
				<col width="120px"/>
				<col width="120px"/>

				<col width="100px"/>
				<col width="100px"/>
				<col width="120px"/>
				<col width="120px"/>
				<col width="140px"/>

				<col width="100px"/>
				<col width="100px"/>
				<col width="100px"/>
				<col width="100px"/>
				<col width="120px"/>

				<col width="100px"/>
				<col width="100px"/>
				<col width="150px"/>
				<col width="180px"/>
				<col width="*"/>

				<col width="120px"/>
				<col width="120px"/>
			</colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>근무지명</th>
					<th>사업자등록번호</th>
					<th>근무시작일</th>
					<th>근무종료일</th>
					
					<th>급여</th>
					<th>상여</th>
					<th>종전근무지인정상여</th>
					<th>결정세액 소득세</th>
					<th>결정세액 지방소득세</th>
					
					<th>건강보험료</th>
					<th>국민연금료</th>
					<th>고용보험료</th>
					<th>해외소득</th>
					<th>비과세초과수당</th>
					
					<th>기타비과세</th>
					<th>현장엔지니어</th>
					<th>소득세법에 의한 면세</th>
					<th>조세감면법에 의한 감면세액</th>
					<th>특별세</th>
					
					<th>감면시작일</th>
					<th>감면종료일</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty resultMap.ET_LAST2}">
						<c:forEach items="${resultMap.ET_LAST2 }" var="etLast2">
							<tr>
								<td>
									<input type="radio" name="etLast2Flag">
								</td>
								<td>
									<select name="comnm" class="w150px parameter">
										<c:forEach items="${resultMap.ET_LAST }" var="result">
											<option value="<c:out value="${result.KEY}"/>" <c:if test="${result.VALUE eq etLast2.COMNM}"> selected </c:if>><c:out value="${result.VALUE}"/></option>
										</c:forEach>
									</select>
								</td>
								<td>
									<input type="text" name="bizno" value="<c:out value="${etLast2.BIZNO}"/>" class="input disabled w90per f_center parameter" readonly="readonly"/>
								</td>
								<td>
									<fmt:parseDate var="dateString" value="${etLast2.PABEG}" pattern="yyyyMMdd" />
									<input type="text" name="pabeg" value="<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />" class="input datepicker w70px parameter" readonly="readonly"/>
									<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
								</td>
								<td>
									<fmt:parseDate var="dateString" value="${etLast2.PAEND}" pattern="yyyyMMdd" />
									<input type="text" name="paend" value="<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />" class="input datepicker w70px parameter" readonly="readonly"/>
									<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
								</td>
								
								<td>
									<input type="text" name="salm" value="<fmt:formatNumber value="${etLast2.SALM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="10"/>
								</td>
								<td>
									<input type="text" name="bonm" value="<fmt:formatNumber value="${etLast2.BONM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="10"/>
								</td>
								<td>
									<input type="text" name="abnm" value="<fmt:formatNumber value="${etLast2.ABNM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="9"/>
								</td>
								<td>
									<input type="text" name="intm" value="<fmt:formatNumber value="${etLast2.INTM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="9"/>
								</td>
								<td>
									<input type="text" name="retm" value="<fmt:formatNumber value="${etLast2.RETM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="9"/>
								</td>
								
								<td>
									<input type="text" name="medm" value="<fmt:formatNumber value="${etLast2.MEDM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="9"/>
								</td>
								<td>
									<input type="text" name="npmm" value="<fmt:formatNumber value="${etLast2.NPMM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="9"/>
								</td>
								<td>
									<input type="text" name="eimm" value="<fmt:formatNumber value="${etLast2.EIMM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="9"/>
								</td>
								<td>
									<input type="text" name="outm" value="<fmt:formatNumber value="${etLast2.OUTM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="9"/>
								</td>
								<td>
									<input type="text" name="ovrm" value="<fmt:formatNumber value="${etLast2.OVRM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="9"/>
								</td>
								
								<td>
									<input type="text" name="othm" value="<fmt:formatNumber value="${etLast2.OTHM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="9"/>
								</td>
								<td>
									<input type="text" name="fldm" value="<fmt:formatNumber value="${etLast2.FLDM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="9"/>
								</td>
								<td>
									<input type="text" name="frim" value="<fmt:formatNumber value="${etLast2.FRIM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="9"/>
								</td>
								<td>
									<input type="text" name="frem" value="<fmt:formatNumber value="${etLast2.FREM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="9"/>
								</td>
								<td>
									<input type="text" name="sptm" value="<fmt:formatNumber value="${etLast2.SPTM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="9"/>
								</td>
								
								<td>
									<fmt:parseDate var="dateString" value="${etLast2.EXBEG}" pattern="yyyyMMdd" />
									<input type="text" name="exbeg" value="<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />" class="input datepicker w70px parameter" readonly="readonly"/>
									<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
								</td>
								<td>
									<fmt:parseDate var="dateString" value="${etLast2.EXEND}" pattern="yyyyMMdd" />
									<input type="text" name="exend" value="<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />" class="input datepicker w70px parameter" readonly="readonly"/>
									<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="empty f_left">
							<td colspan="22">
								【 종(전) 근무지 내역이 없습니다. 추가등록 버튼을 이용해 등록 하십시오. 】
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