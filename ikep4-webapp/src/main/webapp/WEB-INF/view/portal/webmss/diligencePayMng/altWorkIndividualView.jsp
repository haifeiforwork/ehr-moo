<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		var page = "<c:out value="${params.imFlag}"/>";
		
		$("input[name=startDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("input[name=endDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
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
				$("#ajaxForm").append("<input type=\"hidden\" name=\"<c:out value="${key}"/>\" value=\"<c:out value="${params[key]}"/>\"/>");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"<c:out value="${selKey}"/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
			</c:forEach>
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imFlag\" value=\"<c:out value="${params.imFlag}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imZeity\" value=\"<c:out value="${params.imZeity}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imMosid\" value=\"<c:out value="${params.imMosid}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imMofid\" value=\"<c:out value="${params.imMofid}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"DELETE\"/>");
			
			if(params != undefined){
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imYes\" value=\""+params+"\"/>");
			}
			
			$(".parameter").each(function(index, value){
				$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""+$(this).val()+"\"/>");
			});
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimmss/diligencePayMng/altWorkIndividualProcess.do'/>",
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
			$("#altWorkIndividualForm").attr("method", "post");
			$("#altWorkIndividualForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/altWorkIndividualList.do'/><c:out value='${params.searchParam}' escapeXml='false'/>");
			$("#altWorkIndividualForm").submit();
		});
		
		$.initSet = function(){
			
			if(page != "CRE"){
				$("#altWorkIndividualForm").append("<span class=\"rowInfo\"></span>");
				
				<c:forEach items="${keySet}" var="key">
					<c:set var="selKey">SEL_<c:out value="${key}"/></c:set>
					$("#altWorkIndividualForm").find("span.rowInfo").append("<input type=\"hidden\" name=\"<c:out value="${selKey}"/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
				</c:forEach>
				
				$("input[name=name]").val("<c:out value="${params.PERNR}"/>");
				
				<fmt:parseDate var="dateString" value="${params.BEGDA}" pattern="yyyyMMdd" />
				$("input[name=startDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
				
				<fmt:parseDate var="dateString" value="${params.ENDDA}" pattern="yyyyMMdd" />
				$("input[name=endDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
				
				$("input[name=workType]").val("<c:out value="${params.VTART}"/>");
				
				$("input[name=startTime]").val("<c:out value="${params.BEGUZ}"/>");
				
				$("input[name=endTime]").val("<c:out value="${params.ENDUZ}"/>");
				
				$("input[name=workTime]").val("<c:out value="${params.STDAZ}"/>");
				
				$("input[name=workSchedule]").val("<c:out value="${params.TPROG}"/>");
				
				$("input[name=workScheduleRule]").val("<c:out value="${params.SCHKZ}"/>");
				
				$("input[name=holidayCalendar]").val("<c:out value="${params.MOFID}"/>");
				$("input[name=empGroup]").val("<c:out value="${params.ZEITY}"/>");
				$("input[name=personGroup]").val("<c:out value="${params.MOSID}"/>");
			}
		};
		
		$.initSet();
	});
})(jQuery);;
</script>
<form id="altWorkIndividualForm" name="altWorkIndividualForm" method="post" action="">
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>대체근무 개별입력</h1>
	
	<div class="button_box">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
	</div>
	
	<c:set var="etBanwon" value="${resultMap.ET_BANWON }"/>
	<c:set var="etVtart" value="${resultMap.ET_VTART }"/>
	<c:set var="etTprog" value="${resultMap.ET_TPROG }"/>
	<c:set var="etSchkz" value="${resultMap.ET_SCHKZ }"/>
	
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
							<c:if test="${result.KEY eq params.PERNR }">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="name" id="name" value="" class="parameter"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">근무기간 <span class="redDot">*</span></th>
					<td class="list03_td">
						<fmt:parseDate var="dateString" value="${params.BEGDA}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />&nbsp;<span class="startDate">(<c:out value="${resultMap.EX_BEGWK }"/>)</span>
						&nbsp;~&nbsp;
						<fmt:parseDate var="dateString" value="${params.ENDDA}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />&nbsp;<span class="endDate">(<c:out value="${resultMap.EX_ENDWK }"/>)</span>
						<input type="hidden" name="startDate" class="parameter" value=""/>
						<input type="hidden" name="endDate" class="parameter" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">근무유형 <span class="redDot">*</span></th>
					<td class="list03_td">
						<c:forEach var="result" items="${etVtart}">
							<c:if test="${result.KEY eq params.VTART }">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="workType" value="" class="parameter"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div class="list03">
		<p class="f_title" style="padding-bottom:10px">개별근무시간</p>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="15%"/>
				<col width="*"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">시간</th>
					<td class="list03_td">
						<c:out value="${params.BEGUZ }"/>
						&nbsp;~&nbsp;
						<c:out value="${params.ENDUZ }"/>
						
						<input type="hidden" name="startTime" class="parameter time" value=""/>
						<input type="hidden" name="endTime" class="parameter time" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">근무시간</th>
					<td class="list03_td">
						<c:out value="${params.STDAZ }"/>
						<input type="hidden" name="workTime" value="" class="parameter"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div class="list03">
		<p class="f_title" style="padding-bottom:10px">일일 근무 일정</p>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="15%"/>
				<col width="*"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">일일 근무 일정</th>
					<td class="list03_td">
						<c:forEach var="result" items="${etTprog}">
							<c:if test="${result.KEY eq params.TPROG }">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="workSchedule" value="" class="parameter"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div class="list03">
		<p class="f_title" style="padding-bottom:10px">근무일정규칙</p>
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
					<th class="list03_td_bg">근무일정규칙</th>
					<td class="list03_td">
						<c:forEach var="result" items="${etSchkz}">
							<c:if test="${result.KEY eq params.SCHKZ }">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="workScheduleRule" value="" class="parameter"/>
					</td>
					<th class="list03_td_bg">사원하위그룹 그룹핑</th>
					<td class="list03_td">
						<c:out value="${params.ZEITY }"/>
						<input type="hidden" name="empGroup" value="" class="parameter"
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">공휴일 달력 ID</th>
					<td class="list03_td">
						<c:out value="${params.MOFID }"/>
						<input type="hidden" name="holidayCalendar" value="" class="parameter"
					</td>
					<th class="list03_td_bg">인사하위그룹 그룹핑</th>
					<td class="list03_td">
						<c:out value="${params.MOSID }"/>
						<input type="hidden" name="personGroup" value="" class="parameter"
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