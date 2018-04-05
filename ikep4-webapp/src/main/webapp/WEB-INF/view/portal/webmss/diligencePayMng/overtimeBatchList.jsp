<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/validation/validation.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		$("input[name=imBegda]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
		$("#btnSave").click(function(){
			$.setParam("SAVE", "SAV");
		});
		
		$("#btnDelete").click(function(){
			$.setParam("DELETE", "DEL");
		});
		
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
		
		$("#chkAll").click(function(){
			if($(this).is(":checked")){
				$("input[name=chkFlag]").attr("checked", true);
			}else{
				$("input[name=chkFlag]").removeAttr("checked");
			}
		});

		$("input[name=chkFlag]").click(function(){
			if($("input[name=chkFlag]").length == $("input[name=chkFlag]:checked").length){
				$("#chkAll").attr("checked", true);
			}else{
				$("#chkAll").removeAttr("checked");
			}
		});
		
		$("img.prev, img.next").click(function(){
			$.callProgress();
			$("#overtimeBatchForm").append("<input type=\"hidden\" name=\"moveDate\" value=\""+$(this).attr("class")+"\"/>");
			$("#overtimeBatchForm").submit();
		});
		
		$("input[name=imBegda]").change(function(){
			$.callProgress();
			$("#overtimeBatchForm").submit();
		});
		
		$.setParam = function(imEventId, imFlag){
			if($("input[name=chkFlag]:checked").length > 0){
				
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
				
				$.callProgress();

				$("#ajaxForm").html("");

				$("input[name=chkFlag]").each(function(index, value){
					if($(this).is(":checked")){
						var rowInfo = $("span.rowInfo").eq($("input[name=chkFlag]").index(this)).html();
						$("#ajaxForm").append(rowInfo);
					}
				});

				$("#ajaxForm").append("<input type=\"hidden\" name=\"rowCnt\" value=\""+$("input[name=chkFlag]:checked").length+"\"/>");
				
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imBegda\" value=\""+$("input[name=imBegda]").val()+"\"/>");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\""+imEventId+"\"/>");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imFlag\" value=\""+imFlag+"\"/>");
				
				$(".parameter").each(function(index, value){
					if($(this).attr("name") == "allDay"){
						if($(this).is(":checked")){
							$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\"X\"/>");
						}else{
							$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\"\"/>");
						}
					}else{
						
						if(this.tagName == "SELECT"){
							$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"Text\" value=\""+$(this).find("option:selected").text()+"\"/>");	
						}
						$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""+$(this).val()+"\"/>");
					}
				});
				
				$jq.ajax({
					url : "<c:url value='/portal/moorimmss/diligencePayMng/overtimeBatchProcess.do'/>",
					data : $("#ajaxForm").serialize(),
					type : "post",
					success : function(result) {
						if($.trim(result.EX_RESULT) == "E"){
							alert($.trim(result.EX_MESSAGE));
						}
						
						var data = result.ET_MESSAGE;
						var etMessageCnt = 0;
						
						for(var i = 0 ; i < data.length ; i++){
							<c:forEach items="${keySet}" var="key">
								$("#overtimeBatchForm").append("<input type=\"hidden\" name=\"etMessage_<c:out value="${key}"/>\" value=\""+data[i]["<c:out value="${key}"/>"]+"\"/>");
							</c:forEach>
							etMessageCnt++;
						}
						
						$("#overtimeBatchForm").append("<input type=\"hidden\" name=\"etMessageCnt\" value=\""+etMessageCnt+"\"/>");
						$("#overtimeBatchForm").submit();
					},
					complete : function(){
						$.stopProgress();
					}
				});
			}else{
				alert("선택된 데이터가 없습니다.");
			}
		};
		
		$.initSet = function(){
			<fmt:parseDate var="dateString" value="${resultMap.EX_BEGDA}" pattern="yyyyMMdd" />
			$("input[name=imBegda]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
			$("span.imBegda").html("(<c:out value="${resultMap.EX_BEGWK}"/>)");
			
			$("input[name=startTime]").val("<c:out value="${params.startTime}"/>");
			$("input[name=endTime]").val("<c:out value="${params.endTime}"/>");
			$("select[name=compensationType] option[value=<c:out value="${params.compensationType}"/>]").attr("selected", "selected");
			$("select[name=overtimeReason] option[value=<c:out value="${params.overtimeReason}"/>]").attr("selected", "selected");
			$("input[name=reason]").val("<c:out value="${params.reason}"/>");
			
			$("input[name=restStartTime1]").val("<c:out value="${params.restStartTime1}"/>");
			$("input[name=restEndTime1]").val("<c:out value="${params.restEndTime1}"/>");
			$("input[name=restStartTime2]").val("<c:out value="${params.restStartTime2}"/>");
			$("input[name=restEndTime2]").val("<c:out value="${params.restEndTime2}"/>");
			$("input[name=restStartTime3]").val("<c:out value="${params.restStartTime3}"/>");
			$("input[name=restEndTime3]").val("<c:out value="${params.restEndTime3}"/>");
			$("input[name=restStartTime4]").val("<c:out value="${params.restStartTime4}"/>");
			$("input[name=restEndTime4]").val("<c:out value="${params.restEndTime4}"/>");
			
			$("input.time").trigger("blur");
			
		};
		
		$.initSet();
	});
})(jQuery);;
</script>
<style>
.font {font-size:11px !important;}
</style>
<form id="overtimeBatchForm" name="overtimeBatchForm" method="post" action="<c:url value='/portal/moorimmss/diligencePayMng/overtimeBatchList.do'/>" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>연장근로 일괄입력</h1>
	
	<div class="search_box">
		<table border="0">
			<tbody>
				<tr>
					<td width="90px"><span class="f_333">연장근로일자</span> <span class="redDot">*</span></td>
					<td>
						<input name="imBegda" id="imBegda" class="input datepicker" type="text" value="" readonly="readonly" />
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
						<span class="imBegda"></span>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="<c:url value="/base/images/ess/butt_prev.png"/>" alt="" height="20" width="20" class="prev" style="cursor:pointer;"/>
						<img src="<c:url value="/base/images/ess/butt_next.png"/>" alt="" height="20" width="20" class="next" style="cursor:pointer;"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<c:set var="etVersl" value="${resultMap.ET_VERSL }"/>
	<c:set var="etPreas" value="${resultMap.ET_PREAS }"/>
	<c:set var="etList" value="${resultMap.ET_LIST }"/>
	
	<div class="list03">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="150px"/>
				<col width="*"/>
			</colgroup>
			<tbody>
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
				<col width="150px"/>
				<col width="405px"/>
				<col width="150px"/>
				<col width="405px"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">휴식시간1</th>
					<td class="list03_td">
						<input type="text" name="restStartTime1" class="input parameter time" value="00:00:00"/>
						&nbsp;~&nbsp;
						<input type="text" name="restEndTime1" class="input parameter time" value="00:00:00"/>
					</td>
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
	
	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
		<a href="#" class="button_img01" id="btnDelete"><span>삭제</span></a>
	</div>
	
	<div class="list01">
		<table border="0" cellpadding="0" cellspacing="0" id="blktable" width="100%" class="font">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" name="chkAll" id="chkAll" value=""/></th>
					<th scope="col">처리상태</th>
					<th scope="col">처리결과</th>
					<th scope="col">부서명</th>
					<th scope="col">직급</th>
					
					<th scope="col">사번</th>
					<th scope="col">이름</th>
					<th scope="col">근무일정</th>
					<th scope="col">일일근무명</th>
					<th scope="col">근무상태</th>
					
					<th scope="col">연장시작일</th>
					<th scope="col">연장종료일</th>
					<th scope="col">시간</th>
					<th scope="col">시작</th>
					<th scope="col">종료</th>
					
					<th scope="col">보상유형</th>
					<th scope="col">잔업사유</th>
					<th scope="col">상세사유</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty etList}">
						<tr>
							<td colspan="18">해당 데이터가 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList}">
							<tr>
								<td><input type="checkbox" name="chkFlag" value=""/></td>
								<td>
									<c:choose>
										<c:when test="${result.SUBRC eq 'S' }">
											<img src="<c:url value="/base/images/ess/green3_icon.gif"/>" alt=""/>
										</c:when>
										<c:when test="${result.SUBRC eq 'E' }">
											<img src="<c:url value="/base/images/ess/red3_icon.gif"/>" alt=""/>
										</c:when>
									</c:choose>
								</td>
								<td><c:out value="${result.MESSAGE}"/></td>
								<td><c:out value="${result.ORGTX}"/></td>
								<td><c:out value="${result.JIKWI}"/></td>
								
								<td><c:out value="${result.PERNR}"/></td>
								<td><c:out value="${result.ENAME}"/></td>
								<td><c:out value="${result.RTEXT}"/></td>
								<td><c:out value="${result.TTEXT}"/></td>
								<td><c:out value="${result.ATEXT}"/></td>
								
								<td>
									<fmt:parseDate var="dateString" value="${result.BEGDA}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
								<td>
									<fmt:parseDate var="dateString" value="${result.ENDDA}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
								<td><c:out value="${result.STDAZ}"/></td>
								<td><c:out value="${result.BEGUZ }"/></td>
								<td><c:out value="${result.ENDUZ }"/></td>
								
								<td><c:out value="${result.VTEXT}"/></td>
								<td><c:out value="${result.PRETX}"/></td>
								<td>
									<c:out value="${result.PRTXT}"/>
									<span class="rowInfo">
										<c:forEach items="${keySet}" var="key">
											<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${result[key]}"/>"/>
										</c:forEach>
									</span>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			 </tbody>
		</table>
	</div>
</div>

<input type="hidden" name="imFirst" value="X"/>

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