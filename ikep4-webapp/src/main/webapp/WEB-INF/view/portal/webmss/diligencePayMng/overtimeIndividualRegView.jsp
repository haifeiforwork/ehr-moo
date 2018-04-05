<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/validation/validation.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		var page = "<c:out value="${params.imFlag}"/>";
		
		var exList = null;
		
		$("input[name=date]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
		$("input.time").live("keyup", function(event){
			$(this).addClass('keyupping');
			if(event.which == 13){
				$.timeValid($(this));
			}
			$(this).removeClass('keyupping');
		});

		$("input.time").live("blur", function(event){
			if (!$(this).hasClass('keyupping')){
				$.timeValid($(this));
			}
		});
		
		$("#btnSave").click(function(event, params){
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
				if(exList != null){
					$("#ajaxForm").append("<input type=\"hidden\" name=\"<c:out value="${key}"/>\" value=\""+exList["<c:out value="${key}"/>"]+"\"/>");
				}else{
					$("#ajaxForm").append("<input type=\"hidden\" name=\"<c:out value="${key}"/>\" value=\"<c:out value="${params[key]}"/>\"/>");
				}
				$("#ajaxForm").append("<input type=\"hidden\" name=\"<c:out value="${selKey}"/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
			</c:forEach>
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imFlag\" value=\"<c:out value="${params.imFlag}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"SAVE\"/>");
			
			if(params != undefined){
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imYes\" value=\""+params+"\"/>");
			}
			
			$(".parameter").each(function(index, value){
				if($(this).attr("name") == "allDay"){
					if($(this).is(":checked")){
						$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\"X\"/>");
					}else{
						$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\"\"/>");
					}
				}else{
					$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""+$(this).val()+"\"/>");
				}
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
			
			if($("input[name=date]").val() == "" || $("select[name=name]").val() == ""){
				alert("연장근로자의 성명 및 연장근로기간을 선택하세요.");
				return;
			}
			
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
		
		$("select[name=name], input[name=date], input[name=startTime], input[name=endTime], select[name=compensationType], select[name=overtimeReason]").change(function(){
			
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
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imFlag\" value=\"<c:out value="${params.imFlag}"/>\"/>");

			$("#ajaxForm").append("<input type=\"hidden\" name=\"name\" value=\""+$("select[name=name]").val()+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"date\" value=\""+$("input[name=date]").val()+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"startTime\" value=\""+$("input[name=startTime]").val()+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"endTime\" value=\""+$("input[name=endTime]").val()+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"compensationType\" value=\""+$("select[name=compensationType]").val()+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"overtimeReason\" value=\""+$("select[name=overtimeReason]").val()+"\"/>");
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimmss/diligencePayMng/callPT022OnInputDetail.do'/>",
				data : $("#ajaxForm").serialize(),
				async : false,
				type : "post",
				success : function(result) {
					if($.trim(result.EX_MESSAGE) != ""){
						alert($.trim(result.EX_MESSAGE));
					}
					if($.trim(result.EX_RESULT) == "S"){
						exList = result.EX_LIST;
					}else{
						//에러처리
					}
				}
			});
		});
		
		$.initSet = function(){
			
			if(page == "CRE"){
				$("select[name=name] option[value=<c:out value="${params.imPernr}"/>]").attr("selected", "selected");
			}else{
				$("#overtimeIndividualForm").append("<span class=\"rowInfo\"></span>");
				
				<c:forEach items="${keySet}" var="key">
					<c:set var="selKey">SEL_<c:out value="${key}"/></c:set>
					$("#overtimeIndividualForm").find("span.rowInfo").append("<input type=\"hidden\" name=\"<c:out value="${selKey}"/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
				</c:forEach>
				
				$("select[name=name] option[value=<c:out value="${params.PERNR}"/>]").attr("selected", "selected");
				
				<fmt:parseDate var="dateString" value="${params.BEGDA}" pattern="yyyyMMdd" />
				$("input[name=date]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
				
				if("<c:out value="${resultMap.EX_BEGWK}"/>" != ""){
					$("span.date").html("(<c:out value="${resultMap.EX_BEGWK}"/>)");
				}
			
				$("input[name=startTime]").val("<c:out value="${params.BEGUZ}"/>");
				
				$("input[name=endTime]").val("<c:out value="${params.ENDUZ}"/>");
				
				if("<c:out value="${params.VTKEN}"/>" == "X"){
					$("input[name=allDay]").attr("checked", "checked");
				}
				
				$("select[name=compensationType] option[value=<c:out value="${params.VERSL}"/>]").attr("selected", "selected");
				
				$("select[name=overtimeReason] option[value=<c:out value="${params.PREAS}"/>]").attr("selected", "selected");
				
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
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
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
						<select name="name" id="name" class="w150px parameter">
							<c:forEach var="result" items="${etBanwon}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">연장근로기간 <span class="redDot">*</span></th>
					<td class="list03_td">
						<input type="text" name="date" id="date" class="input datepicker parameter" value="" readonly="readonly" />
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
						<span class="date"></span>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">시간 <span class="redDot">*</span></th>
					<td class="list03_td">
						<input type="text" name="startTime" class="input parameter time" value="00:00:00"/>
						&nbsp;~&nbsp;
						<input type="text" name="endTime" class="input parameter time" value="00:00:00"/>
						&nbsp;
						<label for="allDay"><input type="checkbox" id="allDay" name="allDay" value="" class="parameter" disabled/>&nbsp;전일</label>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">보상유형 <span class="redDot">*</span></th>
					<td class="list03_td">
						<select name="compensationType" class="parameter w150px">
							<c:forEach items="${etVersl }" var="result">
								<option value="<c:out value="${result.KEY }"/>"/><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">잔업사유 <span class="redDot">*</span></th>
					<td class="list03_td">
						<select name="overtimeReason" class="parameter w150px">
							<c:forEach items="${etPreas }" var="result">
								<option value="<c:out value="${result.KEY }"/>"/><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">상세사유 <span class="redDot">*</span></th>
					<td class="list03_td">
						<input type="text" name="reason" class="input parameter w90per" value=""/>
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
						<input type="text" name="restStartTime1" class="input parameter time" value="00:00:00"/>
						&nbsp;~&nbsp;
						<input type="text" name="restEndTime1" class="input parameter time" value="00:00:00"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">휴식시간2</th>
					<td class="list03_td">
						<input type="text" name="restStartTime2" class="input parameter time" value="00:00:00"/>
						&nbsp;~&nbsp;
						<input type="text" name="restEndTime2" class="input parameter time" value="00:00:00"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">휴식시간3</th>
					<td class="list03_td">
						<input type="text" name="restStartTime3" class="input parameter time" value="00:00:00"/>
						&nbsp;~&nbsp;
						<input type="text" name="restEndTime3" class="input parameter time" value="00:00:00"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">휴식시간4</th>
					<td class="list03_td">
						<input type="text" name="restStartTime4" class="input parameter time" value="00:00:00"/>
						&nbsp;~&nbsp;
						<input type="text" name="restEndTime4" class="input parameter time" value="00:00:00"/>
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