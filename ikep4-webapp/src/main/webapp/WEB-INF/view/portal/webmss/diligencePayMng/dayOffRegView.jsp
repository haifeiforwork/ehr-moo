<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/validation/validation.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		var exList = null;
		var exTaCount = "<c:out value="${resultMap.EX_TA_COUNT}"/>";
		var exBtrtl = "<c:out value="${resultMap.EX_BTRTL}"/>";
		var exOrgeh = "<c:out value="${resultMap.EX_ORGEH}"/>";
		
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
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imBtrtl\" value=\""+exBtrtl+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imOrgeh\" value=\""+exOrgeh+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"SAVE\"/>");
			
			var taFlag = "";
			
			$(".taPernr").each(function(){
				if($(this).val() != ""){
					taFlag = "X";
					return false;
				}
			});
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imTaFlag\" value=\""+taFlag+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imTaCount\" value=\""+exTaCount+"\"/>");
			
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
				url : "<c:url value='/portal/moorimmss/diligencePayMng/dayOffProcess.do'/>",
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
			$("#dayOffForm").attr("method", "post");
			$("#dayOffForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/dayOffList.do'/><c:out value='${params.searchParam}' escapeXml='false'/>");
			$("#dayOffForm").submit();
		});
		
		$("select[name=name], select[name=dayOffType], input[name=startDate], input[name=endDate]").change(function(){
			
			if($("select[name=name]").val() == "" || $("input[name=startDate]").val() == "" || $("input[name=endDate]").val() == ""){
				return;
			}
			
			$("#ajaxForm").html("");
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imFlag\" value=\"<c:out value="${params.imFlag}"/>\"/>");
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"name\" value=\""+$("select[name=name]").val()+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"dayOffType\" value=\""+$("select[name=dayOffType]").val()+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"startDate\" value=\""+$("input[name=startDate]").val()+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"endDate\" value=\""+$("input[name=endDate]").val()+"\"/>");
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimmss/diligencePayMng/callPT018OnInputDetail.do'/>",
				data : $("#ajaxForm").serialize(),
				async : false,
				type : "post",
				success : function(result) {
					
					if($.trim(result.EX_MESSAGE) != ""){
						alert($.trim(result.EX_MESSAGE));
					}
					
					if($.trim(result.EX_RESULT) == "S"){
						var etCalendar = result.ET_CALENDAR;
						var etTaPernr = result.ET_TA_PERNR;
						
						exList = result.EX_LIST;
						exTaCount = result.EX_TA_COUNT;
						exBtrtl = result.EX_BTRTL;
						exOrgeh = result.EX_ORGEH;
						
						var sb = "";
						var flag = "L";
						
						$("div.taDiv").remove();
						
						if(exBtrtl == "S200" || exBtrtl == "P200"){
							$(".taInfo").show();
							
							var index = 1;
							
							for(var i = 0 ; i < etCalendar.length ; i++){
								
								if(flag == "L"){
									sb += "<div class=\"list03 taDiv\" style=\"width:48%; float:left; clear:none;\">";
								}else if(flag == "R"){
									sb += "<div class=\"list03 taDiv\" style=\"width:48%; float:right; clear:none;\">";
								}
								
								sb += "	<p class=\"f_title\" style=\"padding-bottom:10px\">"+$.datepicker.formatDate('yy-mm-dd', $.datepicker.parseDate('yymmdd', etCalendar[i].DATE))+"</p>";
								sb += "	<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">";
								sb += "		<caption></caption>";
								sb += "		<colgroup>";
								sb += "			<col width=\"15%\"/>";
								sb += "			<col width=\"35%\"/>";
								sb += "			<col width=\"15%\"/>";
								sb += "			<col width=\"35%\"/>";
								sb += "		</colgroup>";
								sb += "		<tbody>";
								for(var j = 0 ; j < 4 ; j++){
									sb += "			<tr>";
									sb += "				<th class=\"list03_td_bg\">대근자</th>";
									sb += "				<td class=\"list03_td\">";
									sb += "					<select name=\"taPernr"+index+"\" class=\"w150px parameter taPernr\">";
									for(var x = 0 ; x < etTaPernr.length ; x++){
										if(etTaPernr[x].KEY == exList['TA_PERNR'+index]){
											sb += "					<option value=\""+etTaPernr[x].KEY+"\" selected>"+etTaPernr[x].VALUE+"</option>";
										}else{
											sb += "					<option value=\""+etTaPernr[x].KEY+"\">"+etTaPernr[x].VALUE+"</option>";
										}
									}
									sb += "					</select>";
									sb += "					<input type=\"hidden\" name=\"taDate"+index+"\" value=\""+$.datepicker.formatDate('yy-mm-dd', $.datepicker.parseDate('yymmdd', etCalendar[i].DATE))+"\" class=\"parameter\"/>";
									sb += "				</td>";
									sb += "				<th class=\"list03_td_bg\">대근시간</th>";
									sb += "				<td class=\"list03_td\"><input type=\"text\" name=\"taPernrTime"+index+"\" value=\""+exList['TATM'+index]+"\" class=\"input parameter\"/></td>";
									sb += "			</tr>";
									
									index++;
								}
								sb += "		</tbody>";
								sb += "	</table>";
								sb += "</div>";
								
								$("#wrap").append(sb);
								
								sb = "";
								
								flag = flag == "L" ? "R" : "L";
							}
							
							parent.iKEP.iFrameResize('#contentIframe');
						
						}else{
							$(".taInfo").hide();
						}
						
						$("#ajaxForm").html("");
						
						if(result.EX_BEGWK != ""){
							$("span.startDate").html("("+result.EX_BEGWK+")");
						}
						if(result.EX_ENDWK != ""){
							$("span.endDate").html("("+result.EX_ENDWK+")");
						}
						
						$("input[name=dayOffTime]").val(exList.STDAZ);
						
						if(exList.ALLDF == "X"){
							$("input[name=allDay]").attr("checked", "checked");
						}
						$("span.ABWTG").html(exList.ABWTG);
						$("input[name=dayOffCnt]").val(exList.ABWTG);
						
						$("span.KALTG").html(exList.KALTG);
						$("input[name=calendarCnt]").val(exList.KALTG);
						
						$("span.ABRTG").html(exList.ABRTG);
						$("input[name=useQuarter]").val(exList.ABRTG);
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
				$("#dayOffForm").append("<span class=\"rowInfo\"></span>");
				
				<c:forEach items="${keySet}" var="key">
					<c:set var="selKey">SEL_<c:out value="${key}"/></c:set>
					$("#dayOffForm").find("span.rowInfo").append("<input type=\"hidden\" name=\"<c:out value="${selKey}"/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
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
				
				$("select[name=dayOffType] option[value=<c:out value="${params.AWART}"/>]").attr("selected", "selected");
				
				$("input[name=startTime]").val("<c:out value="${params.BEGUZ}"/>");
				
				$("input[name=endTime]").val("<c:out value="${params.ENDUZ}"/>");
				
				$("input[name=dayOffTime]").val("<c:out value="${params.STDAZ}"/>");
				
				if("<c:out value="${params.ALLDF}"/>" == "X"){
					$("input[name=allDay]").attr("checked", "checked");
				}
				
				$("span.ABWTG").html("<c:out value="${params.ABWTG}"/>");
				$("input[name=dayOffCnt]").val("<c:out value="${params.ABWTG}"/>");
				
				$("span.KALTG").html("<c:out value="${params.KALTG}"/>");
				$("input[name=calendarCnt]").val("<c:out value="${params.KALTG}"/>");
				
				$("span.ABRTG").html("<c:out value="${params.ABRTG}"/>");
				$("input[name=useQuarter]").val("<c:out value="${params.ABRTG}"/>");
				
				var sb = "";
				
				<c:choose>
					<c:when test="${resultMap.EX_BTRTL eq 'S200' || resultMap.EX_BTRTL eq 'P200'}">
						$(".taInfo").show();
					
						<c:set var="flag" value="L"/>
						<c:set var="index" value="1"/>
						
						<c:forEach items="${resultMap.ET_CALENDAR}" var="etCalendar">
						
							<fmt:parseDate var="dateString" value="${etCalendar.DATE}" pattern="yyyyMMdd" />
							
							if("<c:out value="${flag}"/>" == "L"){
								sb += "<div class=\"list03 taDiv\" style=\"width:48%; float:left; clear:none;\">";
							}else if("<c:out value="${flag}"/>" == "R"){
								sb += "<div class=\"list03 taDiv\" style=\"width:48%; float:right; clear:none;\">";
							}
							
							sb += "	<p class=\"f_title\" style=\"padding-bottom:10px\"><fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" /></p>";
							sb += "	<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">";
							sb += "		<caption></caption>";
							sb += "		<colgroup>";
							sb += "			<col width=\"15%\"/>";
							sb += "			<col width=\"35%\"/>";
							sb += "			<col width=\"15%\"/>";
							sb += "			<col width=\"35%\"/>";
							sb += "		</colgroup>";
							sb += "		<tbody>";
							
							<c:forEach begin="1" end="4">
								sb += "			<tr>";
								sb += "				<th class=\"list03_td_bg\">대근자</th>";
								sb += "				<td class=\"list03_td\">";
								sb += "					<select name=\"taPernr<c:out value="${index}"/>\" class=\"w150px parameter taPernr\">";
									<c:forEach items="${resultMap.ET_TA_PERNR}" var="etTaPernr">
									
										<c:set var="taPernr">TA_PERNR<c:out value="${index}"/></c:set>
										<c:set var="taPernrTime">TATM<c:out value="${index}"/></c:set>
										
										<c:choose>
											<c:when test="${params[taPernr] eq etTaPernr.KEY}">
												sb += "					<option value=\"<c:out value="${etTaPernr.KEY}"/>\" selected><c:out value="${etTaPernr.VALUE}"/></option>";
											</c:when>
											<c:otherwise>
												sb += "					<option value=\"<c:out value="${etTaPernr.KEY}"/>\"><c:out value="${etTaPernr.VALUE}"/></option>";
											</c:otherwise>
										</c:choose>
									</c:forEach>
								sb += "					</select>";
								sb += "					<input type=\"hidden\" name=\"taDate<c:out value="${index}"/>\" value=\"<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />\" class=\"parameter\"/>";
								sb += "				</td>";
								sb += "				<th class=\"list03_td_bg\">대근시간</th>";
								sb += "				<td class=\"list03_td\"><input type=\"text\" name=\"taPernrTime<c:out value="${index}"/>\" value=\"<c:out value="${params[taPernrTime]}"/>\" class=\"input parameter\"/></td>";
								sb += "			</tr>";
							
								<c:set var="index" value="${index+1}"/>
							
							</c:forEach>
							
							sb += "		</tbody>";
							sb += "	</table>";
							sb += "</div>";
							
							$("#wrap").append(sb);
							
							sb = "";
							
							<c:choose>
								<c:when test="${flag eq 'L'}">
									<c:set var="flag" value="R"/>
								</c:when>
								<c:when test="${flag eq 'R'}">
									<c:set var="flag" value="L"/>
								</c:when>
							</c:choose>
						</c:forEach>
					</c:when>
					<c:otherwise>
						$(".taInfo").hide();
					</c:otherwise>
				</c:choose>
			}
		};
		
		$.initSet();
	});
})(jQuery);;
</script>
<form id="dayOffForm" name="dayOffForm" method="post" action="" >

<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>휴무 개별입력</h1>
	
	<div class="button_box">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
	</div>
	
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
					<th class="list03_td_bg">휴무기간 <span class="redDot">*</span></th>
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
					<th class="list03_td_bg">휴무유형 <span class="redDot">*</span></th>
					<td class="list03_td">
						<select name="dayOffType" id="dayOffType" class="w150px parameter" <c:if test="${params.imFlag eq 'CHA'}"> disabled </c:if>>
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
					<th class="list03_td_bg">휴무시간</th>
					<td class="list03_td">
						<input type="text" name="dayOffTime" class="input parameter" value=""/>&nbsp;<label for="allDay"><input type="checkbox" id="allDay" name="allDay" value="" class="parameter" disabled/>&nbsp;전일</label>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">휴무일수</th>
					<td class="list03_td">
						<span class="ABWTG"></span>
						<input type="hidden" name="dayOffCnt" value="" class="parameter"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">달력일수</th>
					<td class="list03_td">
						<span class="KALTG"></span>
						<input type="hidden" name="calendarCnt" value="" class="parameter"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">사용쿼터(일)</th>
					<td class="list03_td">
						<span class="ABRTG"></span>
						<input type="hidden" name="useQuarter" value="" class="parameter"/>
					</td>
				</tr>
				<tr class="taInfo" style="display:none;">
					<th class="list03_td_bg">대근자정보</th>
					<td class="list03_td">
						<p>1. 대근자 정보를 필히 입력하여 주시기 바랍니다.(상주 근무자인 경우 제외)</p>
						<p>2. 대근자 정보입력은 실제 근태와 관련이 없으며 급여에 반영되지 않습니다.</p>
						<p>3. 연장근로 개별 및 일괄 입력에서 반드시 입력하셔야 근태 및 급여에 반영됩니다.</p>
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