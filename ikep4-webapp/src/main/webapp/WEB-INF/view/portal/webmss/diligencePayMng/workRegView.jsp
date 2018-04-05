<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/validation/validation.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		var exList = null;
		
		var page = "<c:out value="${params.imFlag}"/>";
		
		$("input[name=startDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("input[name=endDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
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
				url : "<c:url value='/portal/moorimmss/diligencePayMng/workProcess.do'/>",
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
			$("#workForm").attr("method", "post");
			$("#workForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/workList.do'/><c:out value='${params.searchParam}' escapeXml='false'/>");
			$("#workForm").submit();
		});
		
		$("select[name=name], select[name=workType], input[name=startDate], input[name=endDate]").change(function(){
			
			$("#ajaxForm").html("");
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imFlag\" value=\"<c:out value="${params.imFlag}"/>\"/>");

			$("#ajaxForm").append("<input type=\"hidden\" name=\"name\" value=\""+$("select[name=name]").val()+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"workType\" value=\""+$("select[name=workType]").val()+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"startDate\" value=\""+$("input[name=startDate]").val()+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"endDate\" value=\""+$("input[name=endDate]").val()+"\"/>");
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimmss/diligencePayMng/callPT019OnInputDetail.do'/>",
				data : $("#ajaxForm").serialize(),
				async : false,
				type : "post",
				success : function(result) {
					
					if($.trim(result.EX_MESSAGE) != ""){
						alert($.trim(result.EX_MESSAGE));
					}
					
					if($.trim(result.EX_RESULT) == "S"){
						exList = result.EX_LIST;
						
						var data = result.ET_AWART;
						var initVal = $("select[name=workType]").val();
						
						$("select[name=workType]").html("");
						
						for(var i = 0 ; i < data.length ; i++){
							if(initVal == data[i].KEY){
								$("select[name=workType]").append("<option value=\""+data[i].KEY+"\" selected>"+data[i].VALUE+"</option>");
							}else{
								$("select[name=workType]").append("<option value=\""+data[i].KEY+"\">"+data[i].VALUE+"</option>");
							}
						}
						
						$("#ajaxForm").html("");
						
						if(result.EX_BEGWK != ""){
							$("span.startDate").html("("+result.EX_BEGWK+")");
						}
						if(result.EX_ENDWK != ""){
							$("span.endDate").html("("+result.EX_ENDWK+")");
						}
						
						$("input[name=workTime]").val(exList.STDAZ);
						
						if(exList.ALLDF == "X"){
							$("input[name=allDay]").attr("checked", "checked");
						}
						$("span.ABWTG").html(exList.ABWTG);
						$("input[name=dayOffCnt]").val(exList.ABWTG);
						
						$("span.KALTG").html(exList.KALTG);
						$("input[name=calendarCnt]").val(exList.KALTG);
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
				$("#workForm").append("<span class=\"rowInfo\"></span>");
				
				<c:forEach items="${keySet}" var="key">
					<c:set var="selKey">SEL_<c:out value="${key}"/></c:set>
					$("#workForm").find("span.rowInfo").append("<input type=\"hidden\" name=\"<c:out value="${selKey}"/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
				</c:forEach>
				
				$("select[name=name] option[value=<c:out value="${params.PERNR}"/>]").attr("selected", "selected");
				
				<fmt:parseDate var="dateString" value="${params.BEGDA}" pattern="yyyyMMdd" />
				$("input[name=startDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
				
				<fmt:parseDate var="dateString" value="${params.ENDDA}" pattern="yyyyMMdd" />
				$("input[name=endDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
				
				if("<c:out value="${resultMap.EX_BEGWK}"/>" != ""){
					$("span.startDate").html("(<c:out value="${resultMap.EX_BEGWK}"/>)");
				}
				if("<c:out value="${resultMap.EX_ENDWK}"/>" != ""){
					$("span.endDate").html("(<c:out value="${resultMap.EX_ENDWK}"/>)");
				}
				
				$("select[name=workType] option[value=<c:out value="${params.AWART}"/>]").attr("selected", "selected");
				
				$("input[name=startTime]").val("<c:out value="${params.BEGUZ}"/>");
				
				$("input[name=endTime]").val("<c:out value="${params.ENDUZ}"/>");
				
				$("input[name=workTime]").val("<c:out value="${params.STDAZ}"/>");
				
				if("<c:out value="${params.ALLDF}"/>" == "X"){
					$("input[name=allDay]").attr("checked", "checked");
				}
				
				$("span.ABWTG").html("<c:out value="${params.ABWTG}"/>");
				$("input[name=workCnt]").val("<c:out value="${params.ABWTG}"/>");
				
				$("span.KALTG").html("<c:out value="${params.KALTG}"/>");
				$("input[name=calendarCnt]").val("<c:out value="${params.KALTG}"/>");
			}
		};
		
		$.initSet();
	});
})(jQuery);;
</script>
<form id="workForm" name="workForm" method="post" action="" >

<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>근무 개별입력</h1>
	
	<div class="button_box">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
	</div>
	
	<c:set var="etBanwon" value="${resultMap.ET_BANWON }"/>
	<c:set var="etAwart" value="${resultMap.ET_AWART }"/>
	
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
						<select name="name" id="name" class="w150px parameter" <c:if test="${params.imFlag eq 'CHA'}"> disabled </c:if>>
							<c:forEach var="result" items="${etBanwon}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">근무기간 <span class="redDot">*</span></th>
					<td class="list03_td">
						<input type="text" name="startDate" id="startDate" class="input datepicker parameter" value="" readonly="readonly" />
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
						<span class="startDate"></span>
						&nbsp;~&nbsp;
						<input type="text" name="endDate" id="endDate" class="input datepicker parameter" value="" readonly="readonly" />
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
						<span class="endDate"></span>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">근무유형 <span class="redDot">*</span></th>
					<td class="list03_td">
						<select name="workType" id="workType" class="w150px parameter" <c:if test="${params.imFlag eq 'CHA'}"> disabled </c:if>>
							<c:forEach var="result" items="${etAwart}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">시간</th>
					<td class="list03_td">
						<input type="text" name="startTime" id="startTime" class="input parameter time" value="00:00:00"/>
						&nbsp;~&nbsp;
						<input type="text" name="endTime" id="endTime" class="input parameter time" value="00:00:00"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">근무시간</th>
					<td class="list03_td">
						<input type="text" name="workTime" class="input parameter" value=""/>&nbsp;<label for="allDay"><input type="checkbox" id="allDay" name="allDay" value="" class="parameter" disabled/>&nbsp;전일</label>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">근무일수</th>
					<td class="list03_td">
						<span class="ABWTG"></span>
						<input type="hidden" name="workCnt" value="" class="parameter"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">달력일수</th>
					<td class="list03_td">
						<span class="KALTG"></span>
						<input type="hidden" name="calendarCnt" value="" class="parameter"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>