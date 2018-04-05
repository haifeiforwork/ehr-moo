<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		var exTaCount = "<c:out value="${resultMap.EX_TA_COUNT}"/>";
		
		var page = "<c:out value="${params.imFlag}"/>";
		
		$("#btnDelete").click(function(event, params){
			$("#ajaxForm").html("");
			
			<c:forEach items="${keySet}" var="key">
				<c:set var="selKey">SEL_<c:out value="${key}"/></c:set>
				$("#ajaxForm").append("<input type=\"hidden\" name=\"<c:out value="${key}"/>\" value=\"<c:out value="${params[key]}"/>\"/>");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"<c:out value="${selKey}"/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
			</c:forEach>
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imFlag\" value=\"<c:out value="${params.imFlag}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imBtrtl\" value=\"<c:out value="${resultMap.EX_BTRTL}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imOrgeh\" value=\"<c:out value="${resultMap.EX_ORGEH}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"DELETE\"/>");
			
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
				$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""+$(this).val()+"\"/>");
			});
			
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimmss/diligencePayMng/familyEventProcess.do'/>",
				data : $("#ajaxForm").serialize(),
				async : false,
				type : "post",
				success : function(result) {
					if(result.EX_CONFIRM != ""){
						if(confirm(result.EX_CONFIRM)){
							$("#btnDelete").trigger("click", ["X"]);
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
		
		$("input[name=familyEventType]").change(function(event, params){
			$jq.ajax({
				url : "<c:url value='/portal/moorimmss/diligencePayMng/retrieveFamilyEventPersonList.do'/>",
				data : "imCaccd="+$(this).val(),
				type : "post",
				async : false,
				success : function(result) {
					var data = result.ET_CACRN;
					
					for( var i=0 ; i < data.length ; i++){
						if(params != undefined && data[i].KEY == params){
							$("input[name=familyEventPerson]").val(data[i].KEY);
							$("span.CACRN").html(data[i].VALUE);
						}
					}
				}
			});
			
			$("select[name=familyEventPerson]").trigger("change");
		});
		
		$("#btnBack").click(function(){
			$("#familyEventForm").attr("method", "post");
			$("#familyEventForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/familyEventList.do'/><c:out value='${params.searchParam}' escapeXml='false'/>");
			$("#familyEventForm").submit();
		});
		
		$.initSet = function(){
			
			if(page != "CRE"){
				$("#familyEventForm").append("<span class=\"rowInfo\"></span>");
				
				<c:forEach items="${keySet}" var="key">
					<c:set var="selKey">SEL_<c:out value="${key}"/></c:set>
					$("#familyEventForm").find("span.rowInfo").append("<input type=\"hidden\" name=\"<c:out value="${selKey}"/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
				</c:forEach>
				
				$("input[name=name]").val("<c:out value="${params.PERNR}"/>");
				
				<fmt:parseDate var="dateString" value="${params.BEGDA}" pattern="yyyyMMdd" />
				$("input[name=requestDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
				
				$("input[name=familyEventType]").val("<c:out value="${params.CACCD}"/>");
				
				$("input[name=familyEventType]").trigger("change", ["<c:out value="${params.CACRN}"/>"]);
				
				$("input[name=familyEventPersonName]").val("<c:out value="${params.ENAME}"/>");
				
				<fmt:parseDate var="dateString" value="${params.BEGDT}" pattern="yyyyMMdd" />
				$("input[name=startDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
				
				<fmt:parseDate var="dateString" value="${params.ENDDT}" pattern="yyyyMMdd" />
				$("input[name=endDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
				
				$("input[name=vacationDay]").val("<c:out value="${params.VACDT}"/>");
				
				$("input[name=familyEventAmount]").val("<c:out value="${params.CACMT}"/>");
				
				$("input[name=garland]").val("<c:out value="${params.GARLD}"/>");
				
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
								<c:forEach items="${resultMap.ET_TA_PERNR}" var="etTaPernr">
									<c:set var="taPernr">TA_PERNR<c:out value="${index}"/></c:set>
									<c:set var="taPernrTime">TATM<c:out value="${index}"/></c:set>
									<c:if test="${params[taPernr] eq etTaPernr.KEY}">
									sb += "				<c:out value="${etTaPernr.VALUE}"/>";
									</c:if>
								</c:forEach>
								
								<c:choose>
									<c:when test="${params[taPernr] ne '00000000'}">
									sb += "					<input type=\"hidden\" name=\"taPernr<c:out value="${index}"/>\" value=\"<c:out value="${params[taPernr]}"/>\" class=\"parameter taPernr\"/>";
									</c:when>
									<c:otherwise>
									sb += "					<input type=\"hidden\" name=\"taPernr<c:out value="${index}"/>\" value=\"\" class=\"parameter taPernr\"/>";
									</c:otherwise>
								</c:choose>
								sb += "					<input type=\"hidden\" name=\"taDate<c:out value="${index}"/>\" value=\"<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />\" class=\"parameter\"/>";
								sb += "				</td>";
								sb += "				<th class=\"list03_td_bg\">대근시간</th>";
								sb += "				<td class=\"list03_td\">";
								<c:choose>
									<c:when test="${params[taPernrTime] ne '0.00'}">
									sb += "					<c:out value="${params[taPernrTime]}"/>";
									sb += "					<input type=\"hidden\" name=\"taPernrTime<c:out value="${index}"/>\" value=\"<c:out value="${params[taPernrTime]}"/>\" class=\"input parameter\"/>";
									</c:when>
									<c:otherwise>
									sb += "					<input type=\"hidden\" name=\"taPernrTime<c:out value="${index}"/>\" value=\"0.00\" class=\"input parameter\"/>";
									</c:otherwise>
								</c:choose>
								sb += "				</td>";
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
			}else{
				$("input[name=familyEventType]").trigger("change");
			}
		};
		
		$.initSet();
	});
})(jQuery);;
</script>
<form id="familyEventForm" name="familyEventForm" method="post" action="" >

<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>경조사 개별입력</h1>
	
	<div class="button_box">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
		<c:if test="${params.imFlag eq 'DEL'}">
			<a href="#" class="button_img01" id="btnDelete"><span>삭제</span></a>
		</c:if>
	</div>
	
	<c:set var="etBanwon" value="${resultMapCode.ET_BANWON }"/>
	<c:set var="etCaccd" value="${resultMapCode.ET_CACCD }"/>
	<c:set var="etGarld" value="${resultMapCode.ET_GARLD }"/>
	
	<div class="list03">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="15%"/>
				<col width="35%"/>
				<col width="15%"/>
				<col width="35%"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">성명 <span class="redDot">*</span></th>
					<td class="list03_td" colspan="3">
						<c:forEach var="result" items="${etBanwon}">
							<c:if test="${result.KEY eq params.PERNR }">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="name" class="parameter" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">신청일 <span class="redDot">*</span></th>
					<td class="list03_td" colspan="3">
						<fmt:parseDate var="dateString" value="${params.BEGDA}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						<input type="hidden" name="requestDate" class="parameter" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">경조사유형 <span class="redDot">*</span></th>
					<td class="list03_td" colspan="3">
						<c:forEach var="result" items="${etCaccd}">
							<c:if test="${result.KEY eq params.CACCD }">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="familyEventType" class="parameter" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">대상 <span class="redDot">*</span></th>
					<td class="list03_td">
						<span class="CACRN"></span>
						<input type="hidden" name="familyEventPerson" value="" class="parameter"/>
					</td>
					<th class="list03_td_bg">대상자명</th>
					<td class="list03_td">
						<c:out value="${params.ENAME }"/>
						<input type="hidden" name="familyEventPersonName" value="" class="input parameter"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">경조기간 <span class="redDot">*</span></th>
					<td class="list03_td">
						<fmt:parseDate var="dateString" value="${params.BEGDT}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />&nbsp;<span class="startDate">(<c:out value="${resultMap.EX_BEGWK }"/>)</span>
						&nbsp;~&nbsp;
						<fmt:parseDate var="dateString" value="${params.ENDDT}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />&nbsp;<span class="endDate">(<c:out value="${resultMap.EX_ENDWK }"/>)</span>
						
						<input type="hidden" name="startDate" class="parameter" value=""/>
						<input type="hidden" name="endDate" class="parameter" value=""/>
					</td>
					<th class="list03_td_bg">휴가일수</th>
					<td class="list03_td">
						<c:out value="${params.VACDT }"/>
						<input type="hidden" name="vacationDay" value="" class="parameter"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">경조금</th>
					<td class="list03_td">
						<fmt:formatNumber value="${params.CACMT}" groupingUsed="true"/>
						<input type="hidden" name="familyEventAmount" value="" class="parameter"/>
					</td>
					<th class="list03_td_bg">화환(조화)지급</th>
					<td class="list03_td">
						<c:forEach var="result" items="${etGarld}">
							<c:if test="${result.KEY eq params.GARLD }">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="garland" class="parameter" value=""/>
					</td>
				</tr>
				<tr class="taInfo" style="display:none;">
					<th class="list03_td_bg">대근자정보</th>
					<td class="list03_td" colspan="3">
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