<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		var page = "<c:out value="${params.imFlag}"/>";
		
		$("#btnDelete").click(function(event, params){
			
			var timeValue;
			var error = false;
			var errorObject;

			$(".time").each(function(){
				timeValue = $.trim($(this).val()).replace(/:/g, "");
				
				if(timeValue != "000000"){
					if(isNaN(timeValue) || timeValue.length > 6){
						error = true;
						errorObject = $(this);
						return false;
					}
				}
			});

			if(error){
				alert("유효한 시간이 아닙니다.");
				errorObject.select();
				return;
			}
			
			$("#ajaxForm").html("");
			
			<c:forEach items="${keySet}" var="key">
				<c:set var="selKey">SEL_<c:out value="${key}"/></c:set>
				$("#ajaxForm").append("<input type=\"hidden\" name=\"<c:out value="${key}"/>\" value=\"<c:out value="${params[key]}"/>\"/>");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"<c:out value="${selKey}"/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
			</c:forEach>
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imFlag\" value=\"<c:out value="${params.imFlag}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"DELETE\"/>");
			
			if(params != undefined){
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imYes\" value=\""+params+"\"/>");
			}
			
			$(".parameter").each(function(index, value){
				$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""+$(this).val()+"\"/>");
			});
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimmss/diligencePayMng/overtimeIndividualProcess.do'/>",
				data : $("#ajaxForm").serialize(),
				async : false,
				type : "post",
				success : function(result) {
					if(result.EX_CONFIRM != ""){
						if(confirm(result.EX_CONFIRM)){
							$("#btnSave").trigger("click", ["X"]);
						}
					}else{
						alert(result.EX_MESSAGE);
						if(result.EX_RESULT == "S"){
							$("#btnBack").click();
						}else{
							//에러처리
						}
					}
				}
			});
			
		});
		
		$("#btnBack").click(function(){
			$("#overtimeIndividualForm").attr("method", "post");
			$("#overtimeIndividualForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/overtimeIndividualList.do'/><c:out value='${params.searchParam}' escapeXml='false'/>");
			$("#overtimeIndividualForm").submit();
		});
		
		$("#btnWorkHistory").click(function(){
			$("#popupForm").html("");
			
			$("#popupForm").append("<input type=\"hidden\" name=\"startDate\" value=\""+$("input[name=date]").val()+"\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"endDate\" value=\""+$("input[name=date]").val()+"\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"viewType\" value=\"BASIS\"/>");
			
			var target = "workHistoryPopup";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=1100px, height=600px, top=100px, left=150px, resizable=no";

			var popup = window.open("", target, param);

			$("#popupForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/workHistoryPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();
		});
		
		$.initSet = function(){
			
			if(page != "CRE"){
				$("#overtimeIndividualForm").append("<span class=\"rowInfo\"></span>");
				
				<c:forEach items="${keySet}" var="key">
					<c:set var="selKey">SEL_<c:out value="${key}"/></c:set>
					$("#overtimeIndividualForm").find("span.rowInfo").append("<input type=\"hidden\" name=\"<c:out value="${selKey}"/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
				</c:forEach>
				
				$("input[name=name]").val("<c:out value="${params.PERNR}"/>");
				
				<fmt:parseDate var="dateString" value="${params.BEGDA}" pattern="yyyyMMdd" />
				$("input[name=date]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
				
				$("input[name=startTime]").val("<c:out value="${params.BEGUZ}"/>");
				
				$("input[name=endTime]").val("<c:out value="${params.ENDUZ}"/>");
				
				if("<c:out value="${params.VTKEN}"/>" == "X"){
					$("input[name=allDay]").attr("checked", "checked");
				}
				
				$("input[name=compensationType]").val("<c:out value="${params.VERSL}"/>");
				
				$("input[name=overtimeReason]").val("<c:out value="${params.PREAS}"/>");
				
				$("input[name=reason]").val("<c:out value="${params.PRTXT}"/>");
				
				$("input[name=restStartTime1]").val("<c:out value="${params.PBEG1}"/>");

				$("input[name=restEndTime1]").val("<c:out value="${params.PEND1}"/>");

				$("input[name=restStartTime2]").val("<c:out value="${params.PBEG2}"/>");
				
				$("input[name=restEndTime2]").val("<c:out value="${params.PEND2}"/>");
				
				$("input[name=restStartTime3]").val("<c:out value="${params.PBEG3}"/>");
				
				$("input[name=restEndTime3]").val("<c:out value="${params.PEND3}"/>");
				
				$("input[name=restStartTime4]").val("<c:out value="${params.PBEG4}"/>");
				
				$("input[name=restEndTime4]").val("<c:out value="${params.PEND4}"/>");
			}
		};
		
		$.initSet();
	});
})(jQuery);;
</script>
<form id="overtimeIndividualForm" name="overtimeIndividualForm" method="post" action="">
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>연장근로 개별입력</h1>
	
	<div class="button_box">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
		<c:if test="${params.imFlag eq 'DEL' }">
		<a href="#" class="button_img01" id="btnDelete"><span>삭제</span></a>
		</c:if>
		<a href="#" class="button_img01" id="btnWorkHistory"><span>근무내역서조회</span></a>
	</div>
	
	<c:set var="etBanwon" value="${resultMap.ET_BANWON }"/>
	<c:set var="etVersl" value="${resultMap.ET_VERSL }"/>
	<c:set var="etPreas" value="${resultMap.ET_PREAS }"/>
	
	<div class="list03">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="15%"/>
				<col width="*"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">성명 <span class="redDot">*</span></th>
					<td class="list03_td">
						<c:forEach var="result" items="${etBanwon}">
							<c:if test="${result.KEY eq params.PERNR}">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="name" value="" class="parameter"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">연장근로기간 <span class="redDot">*</span></th>
					<td class="list03_td">
						<fmt:parseDate var="dateString" value="${params.BEGDA}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						<span class="date">(<c:out value="${resultMap.EX_BEGWK }"/>)</span>
						<input type="hidden" name="date" value="" class="parameter"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">시간 <span class="redDot">*</span></th>
					<td class="list03_td">
						<c:out value="${params.BEGUZ }"/>
						&nbsp;~&nbsp;
						<c:out value="${params.ENDUZ }"/>
						&nbsp;
						<label for="allDay"><input type="checkbox" id="allDay" name="allDay" value="" class="parameter" disabled/>&nbsp;전일</label>
						<input type="hidden" name="startTime" value="" class="parameter time"/>
						<input type="hidden" name="endTime" value="" class="parameter time"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">보상유형 <span class="redDot">*</span></th>
					<td class="list03_td">
						<c:forEach items="${etVersl }" var="result">
							<c:if test="${result.KEY eq params.VERSL }">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="compensationType" value="" class="parameter"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">잔업사유 <span class="redDot">*</span></th>
					<td class="list03_td">
						<c:forEach items="${etPreas }" var="result">
							<c:if test="${result.KEY eq params.PREAS }">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="overtimeReason" value="" class="parameter"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">상세사유 <span class="redDot">*</span></th>
					<td class="list03_td">
						<c:out value="${params.PRTXT }"/>
						<input type="hidden" name="reason" value="" class="parameter"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div class="list03">
		<p class="f_title" style="padding-bottom:10px">휴식</p>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="15%"/>
				<col width="*"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">휴식시간1</th>
					<td class="list03_td">
						<c:out value="${params.PBEG1 }"/>
						<c:if test="${!empty params.PBEG1 && !empty params.PEND1}">&nbsp;~&nbsp;</c:if>
						<c:out value="${params.PEND1 }"/>
					
						<input type="hidden" name="restStartTime1" value="" class="parameter time"/>
						<input type="hidden" name="restEndTime1" value="" class="parameter time"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">휴식시간2</th>
					<td class="list03_td">
						<c:out value="${params.PBEG2 }"/>
						<c:if test="${!empty params.PBEG2 && !empty params.PEND2}">&nbsp;~&nbsp;</c:if>
						<c:out value="${params.PEND2 }"/>
					
						<input type="hidden" name="restStartTime2" value="" class="parameter time"/>
						<input type="hidden" name="restEndTime2" value="" class="parameter time"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">휴식시간3</th>
					<td class="list03_td">
						<c:out value="${params.PBEG3 }"/>
						<c:if test="${!empty params.PBEG3 && !empty params.PEND3}">&nbsp;~&nbsp;</c:if>
						<c:out value="${params.PEND3 }"/>
					
						<input type="hidden" name="restStartTime3" value="" class="parameter time"/>
						<input type="hidden" name="restEndTime3" value="" class="parameter time"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">휴식시간4</th>
					<td class="list03_td">
						<c:out value="${params.PBEG4 }"/>
						<c:if test="${!empty params.PBEG4 && !empty params.PEND4}">&nbsp;~&nbsp;</c:if>
						<c:out value="${params.PEND4 }"/>
					
						<input type="hidden" name="restStartTime4" value="" class="parameter time"/>
						<input type="hidden" name="restEndTime4" value="" class="parameter time"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<form id="popupForm" name="popupForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>