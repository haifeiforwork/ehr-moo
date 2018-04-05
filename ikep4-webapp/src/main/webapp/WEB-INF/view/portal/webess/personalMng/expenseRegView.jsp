<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		<c:if test="${resultMapList.EX_RESULT eq 'S' && !empty resultMapList.EX_MESSAGE}">
		alert("<c:out value="${resultMapList.EX_MESSAGE}"/>");
		</c:if>

		var page = "CREATE";

		$("input[name=requestDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("input[name=dispatchDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("input[name=moveDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });

		$("#btnBack").click(function(){
			$("#expenseForm").attr("method", "post");
			$("#expenseForm").attr("action", "<c:url value='/portal/moorimess/personalMng/expenseList.do'/><c:out value='${params.searchParam}' escapeXml='false'/>");
			$("#expenseForm").submit();
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

		$("select[name=reqType]").change(function(){

			if($(this).val() == ""){
				$.areaTypeUse("disable");
				$.dispatchDateUse("disable");
				$.moveDateUse("disable");
				$.moveTypeUse("disable");
				$.sleepTypeUse("disable");
				$.marriageTypeUse("disable");
				$.amountUse("disable");
			}else{
				$.areaTypeUse("init");
				$.dispatchDateUse("init");
				$.moveDateUse("init");
				$.moveTypeUse("init");
				$.sleepTypeUse("init");
				$.marriageTypeUse("init");
				$.amountUse("init");

				$.callSetReqgn();
			}
		});

		$("select[name=areaType]").change(function(){

			var reqType = $("select[name=reqType]").val();

			if(reqType == "2"){
				$.callGetArea();
			}
		});

		$("select[name=moveType]").change(function(){

			var reqType = $("select[name=reqType]").val();

			$.amountUse("disable");

			if(reqType == "1"){
				$.marriageTypeUse("init");
				if($(this).val() != ""){
					$.callGetMovamt();
				}
			}else if(reqType == "3"){
				$.amountUse("enable");
			}
		});

		$("select[name=marriageType]").change(function(){
			var reqTypeVal = $("select[name=reqType]").val();
			var moveTypeVal = $("select[name=moveType]").val();
			var marriageTypeVal = $("select[name=marriageType]").val();

			if(reqTypeVal == "1"){
				if(moveTypeVal == ""){
					$.amountUse("disable");
				}else{
					if(marriageTypeVal == "Y"){
						$.amountUse("disable");
						$.callGetMovamt();
					}else if(marriageTypeVal == "N"){
						$.amountUse("init");
						$.amountUse("enable");
					}else{
						$.amountUse("disable");
						$.callGetMovamt();
					}
				}
			}else{
				$.amountUse("disable");
			}
		});

		$("input[name=amount]").blur(function(){
			var tmp = $("input[name=amount]").val();
			tmp = tmp.clearComma();
			$(this).val(tmp.addComma());
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

		$.callSetReqgn = function(){

			$("#ajaxForm").html("");

			$("#ajaxForm").append("<span class=\"etcInfo\"></span>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"paDisabled\" value=\"FALSE\"/>");

			$.setInputParameter();

			var reqType, dispatchDate, moveDate, moveType, marriageType, amount;

			reqType = $("select[name=reqType]").val();

			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalMng/retrieveSetReqgn.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async : false,
				success : function(result) {

					if($.trim(result.EX_RESULT) == "S"){
						dispatchDate = result.EX_POST_DISABLED;
						moveDate = result.EX_MOVE_DISABLED;
						moveType = result.EX_MVTY_DISABELD;
						marriageType = result.EX_MARRY_DISABLED;
						amount = result.EX_MOVMT_DISABELD;

						if(reqType == 2){
							$.areaTypeUse("enable");
						}else{
							$.areaTypeUse("disable");
						}

						if(dispatchDate == "TRUE"){
							$.dispatchDateUse("disable");
							$.sleepTypeUse("disable");
						}else if(dispatchDate == "FALSE"){
							$.dispatchDateUse("enable");
							$.sleepTypeUse("enable");
						}

						if(moveDate == "TRUE"){
							$.moveDateUse("disable");
						}else if(moveDate == "FALSE"){
							$.moveDateUse("enable");
						}

						if(moveType == "TRUE"){
							$.moveTypeUse("disable");
						}else if(moveType == "FALSE"){
							$.moveTypeUse("enable");
						}

						if(marriageType == "TRUE"){
							$.marriageTypeUse("disable");
						}else if(moveType == "FALSE"){
							$.marriageTypeUse("enable");
						}

						if(amount == "TRUE"){
							$.amountUse("disable");
						}else if(amount == "FALSE"){
							$.amountUse("enable");
						}
					}else{
						alert(result.EX_MESSAGE);
					}
				}
			});
		};

		$.callGetArea = function(){
			$("#ajaxForm").html("");
			$.setInputParameter();

			var moveType, amount;

			<%//ZHR_RFC_PT_006_GET_AREA%>
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalMng/retrieveGetArea.do'/>",
				data : $("#ajaxForm").serialize(),
				async : false,
				type : "post",
				success : function(result) {
					if($.trim(result.EX_RESULT) == "S"){
						moveType = result.EX_MVTY_DISABELD;

						if(moveType == "TRUE"){
							$.moveTypeUse("disable");
						}else if(moveType == "FALSE"){
							$.moveTypeUse("enable");
						}

						amount = result.EX_MOVMT.toString();
						$("input[name=amount]").val(amount.addComma());
					}else{
						alert(result.EX_MESSAGE);
					}
				}
			});
		};

		$.callGetMovamt = function(){
			$("#ajaxForm").html("");
			$.setInputParameter();

			var amount;

			<%//ZHR_RFC_PT_006_GET_MOVAMT%>
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalMng/retrieveGetMovamt.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async : false,
				success : function(result) {
					if($.trim(result.EX_RESULT) == "S"){
						amount = result.EX_MVAMT.toString();
						$("input[name=amount]").val(amount.addComma());
					}else{
						alert(result.EX_MESSAGE);
					}
				}
			});
		};

		$.callSaveRequest = function(){
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalMng/expenseProcess.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				success : function(result) {
					alert(result.EX_MESSAGE);
					if(result.EX_RESULT == "S"){
						$("#btnBack").click();
					}else{
						//에러처리
					}
				}
			});
		};

		$.initSet = function(){
			if("<c:out value="${params.dataLoad}"/>" == "Y"){

				var form = $("#expenseForm");

				form.append("<span class=\"rowInfo\"></span>");

				<c:forEach items="${keySet}" var="key">
					<c:set var="selKey">SEL_<c:out value="${key}"/></c:set>
					form.find("span.rowInfo").append("<input type=\"hidden\" name=\"SEL_<c:out value='${key}'/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
					$("#apprLineForm").append("<input type=\"hidden\" name=\"<c:out value='${key}'/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
				</c:forEach>

				<fmt:parseDate var="dateString" value="${params.REQDT}" pattern="yyyyMMdd" />
				$("input[name=requestDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");

				$("select[name=reqType] option[value=<c:out value='${params.REQGN}'/>]").attr("selected", "selected");
				$("select[name=reqType]").change();

				$("select[name=areaType] option[value=<c:out value='${params.ZAREA}'/>]").attr("selected", "selected");
				$("select[name=areaType]").change();

				<fmt:parseDate var="dateString" value="${params.ZBUDT}" pattern="yyyyMMdd" />
				$("input[name=dispatchDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");

				<fmt:parseDate var="dateString" value="${params.ZMODT}" pattern="yyyyMMdd" />
				$("input[name=moveDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");

				$("select[name=moveType] option[value=<c:out value='${params.MOVTY}'/>]").attr("selected", "selected");
				$("select[name=moveType]").change();

				$("select[name=sleepType] option[value=<c:out value='${params.SLEEP}'/>]").attr("selected", "selected");

				$("select[name=marriageType] option[value=<c:out value='${params.MARRY}'/>]").attr("selected", "selected");
				$("select[name=marriageType]").change();

				$("input[name=amount]").val("<fmt:formatNumber value="${params.MOVMT}" groupingUsed="true"/>");

				page = "CHANGE";

			}else{
				$("input[name=requestDate]").val("<c:out value="${params.toDay}"/>");

				$.areaTypeUse("disable");
				$.dispatchDateUse("disable");
				$.sleepTypeUse("disable");
				$.moveDateUse("disable");
				$.moveTypeUse("disable");
				$.marriageTypeUse("disable");
				$.amountUse("disable");
			}
		};

		$.setParam = function(eventId){

			$("#ajaxForm").html("");

			<%//리스트에서 선택한 원본 데이터 %>
			if("<c:out value="${params.dataLoad}"/>" == "Y"){
				$("#ajaxForm").append("<span class=\"rowInfo\"></span>");
				$("#ajaxForm").find("span.rowInfo").html($("#expenseForm").find("span.rowInfo").html());
			}

			<%//결재라인 데이터 처리%>
			$("#ajaxForm").append("<span class=\"apprInfo\"></span>");

			var apprCnt = 0;
			$("#expenseForm").find("span.apprInfo").each(function(index){
				$("#ajaxForm").find("span.apprInfo").append($(this).html());
				apprCnt++;
			});
			$("#ajaxForm").find("span.apprInfo").append("<input type=\"hidden\" name=\"apprCnt\" value=\""+apprCnt+"\"/>");

			<%//그외 추가 데이터%>
			$("#ajaxForm").append("<span class=\"etcInfo\"></span>");

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"exFlag\" value=\""+"<c:out value="${params.exFlag}"/>"+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"eventId\" value=\""+eventId+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"page\" value=\""+page+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"paDisabled\" value=\"FALSE\"/>");

			$.setInputParameter();

			$.callSaveRequest();
		};

		$.areaTypeUse = function(flag){
			if(flag == 'enable'){
				$("select[name=areaType]").removeAttr("disabled");
			}else if(flag == 'disable'){
				$("select[name=areaType] option[value=]").attr("selected", "selected");
				$("select[name=areaType]").attr("disabled", "disabled");
			}else if(flag == 'init'){
				$("select[name=areaType] option[value=]").attr("selected", "selected");
			}
		};

		$.dispatchDateUse = function(flag){
			if(flag == 'enable'){
				$("input[name=dispatchDate]").removeClass("disabled");
				$("input[name=dispatchDate]").removeAttr("readonly");
				$("input[name=dispatchDate]").datepicker("enable");
				$("input[name=dispatchDate]").next("img").show();
			}else if(flag == 'disable'){
				$("input[name=dispatchDate]").val("");
				$("input[name=dispatchDate]").addClass("disabled");
				$("input[name=dispatchDate]").attr("readonly", "readonly");
				$("input[name=dispatchDate]").datepicker("disable");
				$("input[name=dispatchDate]").next("img").hide();
			}else if(flag == 'init'){
				$("input[name=dispatchDate]").val("");
			}
		};

		$.sleepTypeUse = function(flag){
			if(flag == 'enable'){
				$("select[name=sleepType]").removeAttr("disabled");
			}else if(flag == 'disable'){
				$("select[name=sleepType] option[value=]").attr("selected", "selected");
				$("select[name=sleepType]").attr("disabled", "disabled");
			}else if(flag == 'init'){
				$("select[name=sleepType] option[value=]").attr("selected", "selected");
			}
		};

		$.moveDateUse = function(flag){
			if(flag == 'enable'){
				$("input[name=moveDate]").removeClass("disabled");
				$("input[name=moveDate]").removeAttr("readonly");
				$("input[name=moveDate]").datepicker("enable");
				$("input[name=moveDate]").next("img").show();
			}else if(flag == 'disable'){
				$("input[name=moveDate]").val("");
				$("input[name=moveDate]").addClass("disabled");
				$("input[name=moveDate]").attr("readonly", "readonly");
				$("input[name=moveDate]").datepicker("disable");
				$("input[name=moveDate]").next("img").hide();
			}else if(flag == 'init'){
				$("input[name=moveDate]").val("");
			}
		};

		$.moveTypeUse = function(flag){
			if(flag == 'enable'){
				$("select[name=moveType]").removeAttr("disabled");
			}else if(flag == 'disable'){
				$("select[name=moveType] option[value=]").attr("selected", "selected");
				$("select[name=moveType]").attr("disabled", "disabled");
			}else if(flag == 'init'){
				$("select[name=moveType] option[value=]").attr("selected", "selected");
			}
		};

		$.marriageTypeUse = function(flag){
			if(flag == 'enable'){
				$("select[name=marriageType]").removeAttr("disabled");
			}else if(flag == 'disable'){
				$("select[name=marriageType] option[value=]").attr("selected", "selected");
				$("select[name=marriageType]").attr("disabled", "disabled");
			}else if(flag == 'init'){
				$("select[name=marriageType] option[value=]").attr("selected", "selected");
			}
		};

		$.amountUse = function(flag){
			if(flag == 'enable'){
				$("input[name=amount]").removeClass("disabled");
				$("input[name=amount]").removeAttr("readonly");
			}else if(flag == 'disable'){
				$("input[name=amount]").val("0");
				$("input[name=amount]").addClass("disabled");
				$("input[name=amount]").attr("readonly", "readonly");
			}else if(flag == 'init'){
				$("input[name=amount]").val("0");
			}
		};

		$.setInputParameter = function(){
			var name, val;

			if($("#ajaxForm").find("span.etcInfo").length == 0){
				$("#ajaxForm").append("<span class=\"etcInfo\"></span>");
			}

			$("#expenseForm").find(".parameter").each(function(){
				name = $(this).attr("name");
				val = $(this).val();

				if(name == "amount"){
					val = val.clearComma();
				}

				$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\""+name+"\" value=\""+val+"\"/>");

			});
		};

		$.initSet();
	});
})(jQuery);
</script>
<form id="expenseForm" name="expenseForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>이사비/부임비/파견비 신청/조회</h1>

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
				<col width="30%"/>
				<col width="15%"/>
				<col width="*"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">신청일자</th>
					<td class="list03_td" colspan="3">
						<input type="text" name="requestDate" class="input datepicker parameter" value="" readonly="readonly"/>
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">신청 구분</th>
					<td class="list03_td">
						<select name="reqType" style="width:150px" class="parameter">
							<c:forEach var="result" items="${reqTypeList}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
					<th class="list03_td_bg">국/내외 구분</th>
					<td class="list03_td">
						<select name="areaType" style="width:150px" class="parameter">
							<c:forEach var="result" items="${areaTypeList}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">부임/파견일자</th>
					<td class="list03_td">
						<input type="text" name="dispatchDate" id="dispatchDate" class="input datepicker parameter" value="" readonly="readonly" />
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
					</td>
					<th class="list03_td_bg">이사일자</th>
					<td class="list03_td">
						<input type="text" name="moveDate" id="moveDate" class="input datepicker parameter" value="" readonly="readonly" />
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">이동유형</th>
					<td class="list03_td">
						<select name="moveType" style="width:150px" class="parameter">
							<c:forEach var="result" items="${moveTypeList}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
					<th class="list03_td_bg">숙소제공</th>
					<td class="list03_td">
						<select name="sleepType" style="width:150px" class="parameter">
							<c:forEach var="result" items="${sleepTypeList}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">미혼/기혼</th>
					<td class="list03_td" colspan="3">
						<select name="marriageType" style="width:150px" class="parameter">
							<c:forEach var="result" items="${marriageTypeList}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">금액</th>
					<td class="list03_td"><input type="text" name="amount" class="input datepicker parameter f_right" value="0" readonly="readonly" /></td>
					<td class="list03_td" colspan="2">
						<p>※ 미혼자는 이사비 신청시 실비금액(이사업체 영수증상)을 직접 입력하며 최대한도는 기본급의 50%임.</p>
						<p>(기혼자도 본인만 이사(가족은 미이동)하는 경우는 미혼자와 동일 기준으로 신청 요망)</p>
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
	<input type="hidden" name="menuType" value="PA006"/>
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>