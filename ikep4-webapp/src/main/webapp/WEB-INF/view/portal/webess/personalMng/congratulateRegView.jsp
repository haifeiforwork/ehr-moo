<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		<c:if test="${resultMapList.EX_RESULT eq 'S' && !empty resultMapList.EX_MESSAGE}">
		alert("<c:out value="${resultMapList.EX_MESSAGE}"/>");
		</c:if>

		var page = "CREATE";

		$("input[name=startDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });

		$("#btnBack").click(function(){
			$("#congratulateForm").attr("method", "post");
			$("#congratulateForm").attr("action", "<c:url value='/portal/moorimess/personalMng/congratulateList.do'/><c:out value='${params.searchParam}' escapeXml='false'/>");
			$("#congratulateForm").submit();
		});

		$("#btnSave, #btnRequest").click(function(){
			if(this.id == "btnSave"){
				$.setParam("SAVE");
			}else if(this.id == "btnRequest"){
				if(confirm("결재상신후에는 수정할 수 없습니다. 결재상신 하시겠습니까?")){
					$.setParam("REQUEST");
				}
			}
		});

		$("#btnLineAdd").click(function(){

			var tab = $("#apprTable").find("tbody");
			var sb = "";

			sb += "<tr>";
			sb += "	<td class=\"f_center\"><input type=\"checkbox\" name=\"apprFlag\" value=\"\"/></td>";
			sb += "	<td class=\"f_center\"><span class=\"APPNR\"></span>&nbsp;<a href=\"#\" class=\"button_img03\" name=\"apprLinePop\"><span><img src=\"/base/images/ess/icon01.png\" alt=\"\"/></span></a></td>";
			sb += "	<td class=\"f_center\"><span class=\"ENAME\"></span></td>";
			sb += "	<td class=\"f_center\"><span class=\"ORGTX\"></span></td>";
			sb += "	<td class=\"f_center\"><span class=\"COTXT\"></span></td>";
			sb += "	<td class=\"f_center\"><span class=\"ASTAT\"></span></td>";
			sb += "	<td class=\"f_center\"><span class=\"ADATE\"></span></td>";
			sb += "	<td class=\"f_center\"><span class=\"ATIME\"></span></td>";
			sb += " <span class=\"apprInfo\">";
			<c:forEach items="${apprKeySet}" var="key">
			sb += "	<input type=\"hidden\" name=\"<c:out value="${key}"/>\" value=\"\"/>";
			</c:forEach>
			sb += " </span>";
			sb += "</tr>";

			var point = tab.find("input[name=EFLAG]").index(tab.find("input[name=EFLAG][value=X]"));

			if(point == 0){
				tab.prepend(sb);
			}else if(point < 0){
				tab.append(sb);
			}else{
				tab.find("tr").eq(point).before(sb);
			}
		});

		$("#btnLineDel").click(function(){

			var flag = false;

			$("input[name=apprFlag]:checked").each(function(){
				var index = $("input[name=apprFlag]").index($(this));
				var eflag = $("span.apprInfo").eq(index).find("input[name=EFLAG]").val();
				if(eflag == "X"){
					flag = true;
					return false;
				}
			});

			if(flag){
				alert("삭제할 수 없는 결재자입니다.");
				return;
			}

			$("input[name=apprFlag]:checked").parents("tr").remove();
		});

		$("select[name=type]").change(function(event, params, nextFlag){

			//Clear Field
			$("select[name=recipientType]").html("");
			$("input[name=recipientName]").val("");
			$("input[name=startDate]").val("");
			$("input[name=endDate]").val("");
			$("input[name=holiday]").val("");
			$("input[name=amount]").val("");
			$("select[name=garlandType] option[value=]").attr("selected", "selected");
			$("select[name=garlandType]").attr("disabled", "disabled");

			if( $(this).val() != "" ){
				$("#ajaxForm").html("");
				$("#ajaxForm").append("<span class=\"etcInfo\"></span>");
				$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"type\" value=\""+$("#congratulateForm").find("select[name=type]").val()+"\"/>");

				$jq.ajax({
					url : "<c:url value='/portal/moorimess/personalMng/retrieveRecipientTypeList.do'/>",
					data : $("#ajaxForm").serialize(),
					type : "post",
					async : false,
					success : function(result) {
						data = result.ET_CACRN;
						for( var i=0 ; i<data.length ; i++ ){
							if(params != undefined && data[i].KEY == params){
								$("select[name=recipientType]").append("<option value=\""+data[i].KEY+"\" selected >"+data[i].VALUE+"</option>");
							}else{
								$("select[name=recipientType]").append("<option value=\""+data[i].KEY+"\">"+data[i].VALUE+"</option>");
							}
						}

						if(nextFlag != undefined && nextFlag == true){
							<fmt:parseDate var="dateString" value="${params.BEGDT}" pattern="yyyyMMdd" />
							$("input[name=startDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
							$("input[name=startDate]").change();
						}
					}
				});
			}
		});

		$("input[name=startDate], select[name=recipientType]").change(function(){
			if($("input[name=startDate]").val() != ""){
				$("#ajaxForm").html("");

				<%//결재라인 데이터 처리%>
				$("#ajaxForm").append("<span class=\"apprInfo\"></span>");

				var apprCnt = 0;
				$("#congratulateForm").find("span.apprInfo").each(function(index){
					$("#ajaxForm").find("span.apprInfo").append($(this).html());
					apprCnt++;
				});
				$("#ajaxForm").find("span.apprInfo").append("<input type=\"hidden\" name=\"apprCnt\" value=\""+apprCnt+"\"/>");

				<%//입력 데이터 처리%>
				$("#ajaxForm").append("<span class=\"etcInfo\"></span>");
				$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"type\" value=\""+$("#congratulateForm").find("select[name=type]").val()+"\"/>");
				$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"recipientType\" value=\""+$("#congratulateForm").find("select[name=recipientType]").val()+"\"/>");
				$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startDate\" value=\""+$("#congratulateForm").find("input[name=startDate]").val()+"\"/>");
				$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"flag\" value=\"C\"/>");
				$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"page\" value=\""+page+"\"/>");

				$jq.ajax({
					url : "<c:url value='/portal/moorimess/personalMng/retrieveCongratulateInfo.do'/>",
					data : $("#ajaxForm").serialize(),
					type : "post",
					async : false,
					success : function(result) {

						if($.trim(result.EX_RESULT) == "S"){
							var exDisabled = $.trim(result.EX_DISABLED);
							var endDate = $.trim(result.EX_ENDDT).toString();

							endDate = endDate.substring(0,4)+"-"+endDate.substring(4,6)+"-"+endDate.substring(6,8);

							$("#congratulateForm").find("input[name=endDate]").val(endDate);
							$("#congratulateForm").find("input[name=holiday]").val($.trim(result.EX_ZVACDT));
							$("#congratulateForm").find("input[name=amount]").val($.trim(result.EX_CACMT).addComma());

							if(exDisabled == 'TRUE'){
								$("#congratulateForm").find("select[name=garlandType] option[value="+$.trim(result.EX_GARLD)+"]").attr("selected", "selected");
								$("#congratulateForm").find("select[name=garlandType]").attr("disabled", "disabled");
							}else{
								$("#congratulateForm").find("select[name=garlandType] option[value=Y]").attr("selected", "selected");
								$("#congratulateForm").find("select[name=garlandType]").removeAttr("disabled");
							}
						}else{
							alert(result.EX_MESSAGE);
						}
					}
				});
			}
		});

		$("a[name=apprLinePop]").live("click", function(){
			var tr = $(this).parents("tr");
			var allTr = $("#apprTable").find("tbody").find("tr");
			var index = Number(allTr.index(tr));

			$("#apprLineForm").find("input[name=index]").remove();
			$("#apprLineForm").append("<input type='hidden' name='index' value='"+index+"'/>");

			$("#apprLineForm").find("input[name=page]").remove();
			$("#apprLineForm").append("<input type='hidden' name='page' value='"+page+"'/>");

			var target = "apprLinePopup";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=800px, height=500px, top=200px, left=300px, resizable=no";

			var popup = window.open("", target, param);

			$("#apprLineForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/apprLinePop.do'/>");
			$("#apprLineForm").attr("target", target);
			$("#apprLineForm").submit();

			popup.focus();
		});

		$.callSaveRequest = function(){
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalMng/congratulateProcess.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				success : function(result) {
					alert(result.EX_MESSAGE);
					if($.trim(result.EX_RESULT) == "S"){
						$("#btnBack").click();
					}else{
						//에러처리
					}
				}
			});
		};

		$.initSet = function(){
			if("<c:out value="${params.dataLoad}"/>" == "Y"){

				var form = $("#congratulateForm");

				form.append("<span class=\"rowInfo\"></span>");

				<c:forEach items="${keySet}" var="key">
					<c:set var="selKey">SEL_<c:out value="${key}"/></c:set>
					form.find("span.rowInfo").append("<input type=\"hidden\" name=\"SEL_<c:out value='${key}'/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
					$("#apprLineForm").append("<input type=\"hidden\" name=\"<c:out value='${key}'/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
				</c:forEach>

				<fmt:parseDate var="dateString" value="${params.BEGDA}" pattern="yyyyMMdd" />
				$("input[name=requestDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
				$("input[name=requestDate]").parents("td").append("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");

				$("select[name=type] option[value=<c:out value='${params.CACCD}'/>]").attr("selected", "selected");
				$("select[name=type]").trigger("change", ["<c:out value='${params.CACRN}'/>", true]);

				$("input[name=recipientName]").val("<c:out value='${params.ENAME}'/>");

				page = "CHANGE";

			}else{
				$("input[name=requestDate]").val("<c:out value="${params.toDay}"/>");
				$("input[name=requestDate]").parents("td").append("<c:out value="${params.toDay}"/>");
			}
		};

		$.setParam = function(eventId){

			$("#ajaxForm").html("");

			<%//리스트에서 선택한 원본 데이터 %>
			if("<c:out value="${params.dataLoad}"/>" == "Y"){
				$("#ajaxForm").append("<span class=\"rowInfo\"></span>");
				$("#ajaxForm").find("span.rowInfo").html($("#congratulateForm").find("span.rowInfo").html());
			}

			<%//결재라인 데이터 처리%>
			$("#ajaxForm").append("<span class=\"apprInfo\"></span>");

			var apprCnt = 0;
			$("#congratulateForm").find("span.apprInfo").each(function(index){
				$("#ajaxForm").find("span.apprInfo").append($(this).html());
				apprCnt++;
			});
			$("#ajaxForm").find("span.apprInfo").append("<input type=\"hidden\" name=\"apprCnt\" value=\""+apprCnt+"\"/>");

			<%//그외 추가 데이터%>
			$("#ajaxForm").append("<span class=\"etcInfo\"></span>");

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"exFlag\" value=\""+"<c:out value="${params.exFlag}"/>"+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"eventId\" value=\""+eventId+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"page\" value=\""+page+"\"/>");

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"requestDate\" value=\""+$("#congratulateForm").find("input[name=requestDate]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"type\" value=\""+$("#congratulateForm").find("select[name=type]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"recipientType\" value=\""+$("#congratulateForm").find("select[name=recipientType]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"recipientName\" value=\""+$("#congratulateForm").find("input[name=recipientName]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startDate\" value=\""+$("#congratulateForm").find("input[name=startDate]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endDate\" value=\""+$("#congratulateForm").find("input[name=endDate]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"holiday\" value=\""+$("#congratulateForm").find("input[name=holiday]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"amount\" value=\""+$("#congratulateForm").find("input[name=amount]").val().clearComma()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"garlandType\" value=\""+$("#congratulateForm").find("select[name=garlandType]").val()+"\"/>");

			$.callSaveRequest();
		};

		$.initSet();
	});
})(jQuery);
</script>
<form id="congratulateForm" name="congratulateForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>경조금 신청/조회</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
		<a href="#" class="button_img01" id="btnRequest"><span>신청</span></a>
	</div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">
			결재라인&nbsp;&nbsp;
			<a href="#" class="button_img02" id="btnLineAdd"><span><img src="/base/images/ess/plus.png" alt=""/></span></a>
			<a href="#" class="button_img02" id="btnLineDel"><span><img src="/base/images/ess/minus.png" alt=""/></span></a>
		</p>

		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" id="apprTable">
				<caption></caption>
				<colgroup>
					<col width="3%"/>
					<col width="13%"/>
					<col width="10%"/>
					<col width="*"/>
					<col width="10%"/>
					<col width="10%"/>
					<col width="10%"/>
					<col width="10%"/>
				</colgroup>
				<thead>
					<tr>
						<th></th>
						<th>사번</th>
						<th>이름</th>
						<th>부서명</th>
						<th>직책</th>
						<th>결재상태</th>
						<th>결재일</th>
						<th>결재시간</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="appr" items="${resultMapList.ET_LINE}" varStatus="status">
						<tr>
							<td><input type="checkbox" name="apprFlag" value=""/></td>
							<td <c:if test="${appr.EFLAG eq 'X' }"> style="background-color:#eff7fb"</c:if>>
								<span class="APPNR">
									<c:out value="${appr.APPNR}"/>
								</span>
								<c:if test="${appr.EFLAG ne 'X' }">
									<a href="#" class="button_img03" name="apprLinePop"><span><img src="/base/images/ess/icon01.png" alt=""/></span></a>
								</c:if>
							</td>
							<td><span class="ENAME"><c:out value="${appr.ENAME}"/></span></td>
							<td><span class="ORGTX"><c:out value="${appr.ORGTX}"/></span></td>
							<td><span class="COTXT"><c:out value="${appr.COTXT}"/></span></td>
							<td><span class="ASTAT"><c:out value="${appr.ASTATT}"/></span></td>
							<td>
								<span class="ADATE">
									<fmt:parseDate var="dateString" value="${appr.ADATE}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</span>
							</td>
							<td>
								<span class="ATIME">
									<c:out value="${appr.ATIME}"/>
								</span>
							</td>
							<span class="apprInfo">
								<c:forEach items="${apprKeySet}" var="key">
									<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${appr[key] }"/>"/>
								</c:forEach>
							</span>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</span>
	</div>

	<div class="list03">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="15%"/>
				<col width="35%"/>
				<col width="15%"/>
				<col width="*"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">신청일</th>
					<td class="list03_td" colspan="3">
						<input type="hidden" name="requestDate" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">경조사 유형</th>
					<td class="list03_td" colspan="3">
						<select name="type" style="width:150px">
							<c:forEach var="result" items="${typeList}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">대상</th>
					<td class="list03_td">
						<select name="recipientType" style="width:150px">
						</select>
					</td>
					<th class="list03_td_bg">대상자명</th>
					<td class="list03_td"><input type="text" name="recipientName" value="" class="input"/></td>
				</tr>

				<tr>
					<th class="list03_td_bg">경조기간</th>
					<td class="list03_td">
						<input type="text" name="startDate" id="startDate" class="input datepicker" value="" readonly="readonly" />
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
						&nbsp;~&nbsp;
						<input type="text" name="endDate" class="input disabled" value="" readonly="readonly"/>
					</td>
					<th class="list03_td_bg">휴가일수</th>
					<td class="list03_td"><input type="text" name="holiday" value="" class="input disabled f_right" readonly="readonly"/></td>
				</tr>
				<tr>
					<th class="list03_td_bg">경조금</th>
					<td class="list03_td"><input type="text" name="amount" value="" class="input disabled f_right" readonly="readonly"/></td>
					<th class="list03_td_bg">화환(조화지급)</th>
					<td class="list03_td">
						<select name="garlandType" style="width:150px" disabled>
							<c:forEach var="result" items="${garlandList}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<form id="apprLineForm" name="apprLineForm" method="post">
	<input type="hidden" name="menuType" value="BE004"/>
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>