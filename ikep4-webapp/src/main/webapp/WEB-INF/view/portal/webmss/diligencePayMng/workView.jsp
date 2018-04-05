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
		
		$.initSet = function(){
			
			if(page != "CRE"){
				$("#workForm").append("<span class=\"rowInfo\"></span>");
				
				<c:forEach items="${keySet}" var="key">
					<c:set var="selKey">SEL_<c:out value="${key}"/></c:set>
					$("#workForm").find("span.rowInfo").append("<input type=\"hidden\" name=\"<c:out value="${selKey}"/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
				</c:forEach>
				
				$("input[name=name]").val("<c:out value="${params.PERNR}"/>");
				
				<fmt:parseDate var="dateString" value="${params.BEGDA}" pattern="yyyyMMdd" />
				$("input[name=startDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
				
				<fmt:parseDate var="dateString" value="${params.ENDDA}" pattern="yyyyMMdd" />
				$("input[name=endDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
				
				$("input[name=workType]").val("<c:out value="${params.AWART}"/>");
				
				$("input[name=startTime]").val("<c:out value="${params.BEGUZ}"/>");
				
				$("input[name=endTime]").val("<c:out value="${params.ENDUZ}"/>");
				
				$("input[name=workTime]").val("<c:out value="${params.STDAZ}"/>");
				
				if("<c:out value="${params.ALLDF}"/>" == "X"){
					$("input[name=allDay]").attr("checked", "checked");
				}
				
				$("input[name=workCnt]").val("<c:out value="${params.ABWTG}"/>");
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
		<c:if test="${params.imFlag eq 'DEL' }">
		<a href="#" class="button_img01" id="btnDelete"><span>삭제</span></a>
		</c:if>
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
						<c:forEach var="result" items="${etBanwon}">
							<c:if test="${result.KEY eq params.PERNR }">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="name" value="" class="parameter"/>
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
						<c:forEach var="result" items="${etAwart}">
							<c:if test="${result.KEY eq params.AWART }">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="workType" value="" class="parameter"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">시간</th>
					<td class="list03_td">
						<c:out value="${params.BEGUZ}"/>
						&nbsp;~&nbsp;
						<c:out value="${params.ENDUZ}"/>
						
						<input type="hidden" name="startTime" class="parameter time" value=""/>
						<input type="hidden" name="endTime" class="parameter time" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">근무시간</th>
					<td class="list03_td">
						<c:out value="${params.STDAZ}"/>&nbsp;<label for="allDay"><input type="checkbox" id="allDay" name="allDay" value="" class="parameter" disabled/>&nbsp;전일</label>
						<input type="hidden" name="workTime" class="parameter" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">근무일수</th>
					<td class="list03_td">
						<c:out value="${params.ABWTG}"/>
						<input type="hidden" name="workCnt" value="" class="parameter"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">달력일수</th>
					<td class="list03_td">
						<c:out value="${params.KALTG}"/>
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