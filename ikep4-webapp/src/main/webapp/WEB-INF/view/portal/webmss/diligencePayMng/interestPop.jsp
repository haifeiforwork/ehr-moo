<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<title>관리자 연말정산</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<link rel="stylesheet" type="text/css" href="/base/css/theme06/jquery_ui_custom.css" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/ep_style.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/ep_button.css"/>" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-ui-1.8.13.min.without.datepicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.ui.datepicker.customize.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/validation/validation.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/mss/utils.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/langResource/ko.js"/>"></script>

<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		$("input[name=etRcbeg], input[name=etRcend]").each(function(){
			$(this).datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		});
		
		$("#btnSave, #btnDel").click(function(){

			$("#ajaxForm").html("");
			
			if(this.id == "btnDel"){
				if($("input[name=etRepayFlag]:checked").length == 0){
					alert("라인을 선택해 주시기 바랍니다.");
					return;
				}
				
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"DELETE\"/>");
				var seq = -1;
				var itDelLineCnt = 0;
				$("input[name=etRepayFlag]").each(function(){
					if($(this).is(":checked")){
						seq = Number($("input[name=etRepayFlag]").index(this)) + 1;
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
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"paramCnt\" value=\""+$("#ajaxForm").find("input[name=etRcbeg]").length+"\"/>");
			
			<c:forEach items="${taxKeySet}" var="key">
				$("#ajaxForm").append("<input type=\"hidden\" name=\"exTax_<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_TAX[key]}"/>\"/>");
			</c:forEach>
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imZyear\" value=\"<c:out value="${params.EX_YEAR}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imRegno\" value=\"<c:out value="${params.REGNO}"/>\"/>");
			
			$.ajax({
				url : "<c:url value='/portal/moorimmss/diligencePayMng/callPY003OninputPop17.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					alert(result.EX_MESSAGE);
					if($.trim(result.EX_RESULT) == "S"){
						opener.$.refresh(2);
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
			var tab = $("#interest").find("tbody");
			var sb = "";
			
			$("#interest").find(".empty").remove();
			
			sb += "<tr>";
			sb += "	<td>";
			sb += "		<input type=\"radio\" name=\"etRepayFlag\">";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"etRcbeg\" value=\"\" class=\"input datepicker w70px parameter\" readonly=\"readonly\"/>";
			sb += "		<img src=\"<c:url value="/base/images/icon/ic_cal.gif"/>\" align=\"absmiddle\" alt=\"calendar\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"etRcend\" value=\"\" class=\"input datepicker w70px parameter\" readonly=\"readonly\"/>";
			sb += "		<img src=\"<c:url value="/base/images/icon/ic_cal.gif"/>\" align=\"absmiddle\" alt=\"calendar\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"etLnprd\" value=\"\" class=\"input w70per parameter\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"checkbox\" name=\"etFixrt\" class=\"parameter\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"checkbox\" name=\"etNodef\" class=\"parameter\"/>";
			sb += "	</td>";
			sb += "	<td>";
			sb += "		<input type=\"text\" name=\"etnam\" value=\"\" class=\"input w90per f_right amount parameter\" data-maxlength=\"10\"/>";
			sb += "	</td>";
			sb += "</tr>";
			
			tab.append(sb);
			
			$("input[name=etRcbeg]:last").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
			$("input[name=etRcend]:last").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		});
		
		$.initSet = function(){};
		
		$.initSet();
	});
})(jQuery);
</script>
</head>
<body>
<form id="workPlaceForm" name="workPlaceForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>이자상환액 입력</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnAdd"><span>추가등록</span></a>
		<a href="#" class="button_img01" id="btnDel"><span>삭제</span></a>
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
		<a href="#" class="button_img01" id="btnClose"><span>닫기</span></a>
	</div>
	
	<div class="list01">
		<table border="0" cellpadding="0" cellspacing="0" id="interest">
			<colgroup>
				<col width="30px"/>
				<col width="150px"/>
				<col width="150px"/>
				<col width="120px"/>
				<col width="120px"/>
				
				<col width="*"/>
			</colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>시작일자</th>
					<th>종료일자</th>
					<th>상환기간(년)</th>
					<th>고정금리</th>
					<th>비거치</th>
					<th>금액</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty resultMap.ET_REPAY}">
						<c:forEach items="${resultMap.ET_REPAY }" var="etRepay">
							<tr>
								<td>
									<input type="radio" name="etRepayFlag">
								</td>
								<td>
									<fmt:parseDate var="dateString" value="${etRepay.ET_RCBEG}" pattern="yyyyMMdd" />
									<input type="text" name="etRcbeg" value="<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />" class="input datepicker w70px parameter" readonly="readonly"/>
									<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
								</td>
								<td>
									<fmt:parseDate var="dateString" value="${etRepay.ET_RCEND}" pattern="yyyyMMdd" />
									<input type="text" name="etRcend" value="<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />" class="input datepicker w70px parameter" readonly="readonly"/>
									<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
								</td>
								<td>
									<input type="text" name="etLnprd" value="<fmt:formatNumber value="${etRepay.ET_LNPRD}" groupingUsed="true"/>" class="input w70per f_right parameter"/>
								</td>
								<td>
									<input type="checkbox" name="etFixrt" class="parameter"<c:if test="${etRepay.ET_FIXRT eq 'X'}"> checked </c:if>/>
								</td>
								<td>
									<input type="checkbox" name="etNodef" class="parameter"<c:if test="${etRepay.ET_NODEF eq 'X'}"> checked </c:if>/>
								</td>
								
								<td>
									<input type="text" name="etnam" value="<fmt:formatNumber value="${etRepay.ETNAM}" groupingUsed="true"/>" class="input w90per f_right amount parameter" data-maxlength="10"/>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="empty f_left">
							<td colspan="7">
								【 장기주택저당차입금 이자상환액 내역이 없습니다. 추가등록 버튼을 이용해 등록 하십시오. 】
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
				<td>※ 국세청 자료를 참조하여 입력하는 경우 소득공제 대상액 입력 (연간합계액 X)</td>
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