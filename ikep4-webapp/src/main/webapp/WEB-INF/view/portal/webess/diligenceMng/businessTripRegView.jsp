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
		$("input[name=endDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("input[name=exchangeApplyDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });

		$("input[name=tripPlaceDate]").datepicker({dateFormat: 'yy-mm-dd'});
		$("img[alt=calendar]").click(function() { $(this).prev().eq(0).datepicker("show"); });

		$("#btnBack").click(function(){
			$("#businessTripForm").attr("method", "post");
			$("#businessTripForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/businessTripList.do'/><c:out value='${params.searchParam}' escapeXml='false'/>");
			$("#businessTripForm").submit();
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

		$("input[name=amount]").live({
		keyup : function(event){
			if(event.keyCode == 13){
				$.calcExchangeAmount();
			}
		},
		blur : function(){
			$.calcExchangeAmount();
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

		$("#btnPlaceAdd").click(function(){
			if($.placeAddValid() == "FAIL"){
				alert("출장내역을 모두 입력하세요.");
				return;
			}

			$.appendImList(true);

			$jq.ajax({
				url : "<c:url value='/portal/moorimess/diligenceMng/placeAdd.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				success : function(result) {
					if($.trim(result.EX_RESULT) == "S"){
						$.placeAddSet(result);
					}else{
						alert(result.EX_MESSAGE);
					}
				}
			});
		});

		$("#btnPlaceDel").click(function(){
			$("input[name=placeFlag]:checked").parents("tr").remove();
		});

		$("select[name=startTrans], select[name=endTrans]").change(function(event, params){
			var selector = this.name;
			var imFlag = "S";
			var data, target;

			if(selector == "endTrans"){
				imFlag = "E";
			}

			$.appendBList(true);

			$("#ajaxForm").append("<span class=\"bListInfo\"></span>");
			$("#ajaxForm").find("span.bListInfo").html($("#businessTripForm").find("span.bListInfo").html());

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"IM_FLAG\" value=\""+imFlag+"\"/>");

			$jq.ajax({
				url : "<c:url value='/portal/moorimess/diligenceMng/retrieveTransGrade.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				success : function(result) {
					if($.trim(result.EX_RESULT) == "S"){
						if(selector == "endTrans"){
							target = $("select[name=endGrade]");
							data = result.ET_GRADEB;
						}else{
							target = $("select[name=startGrade]");
							data = result.ET_GRADEA;
						}
						target.html("");
						for(var i=0 ; i<data.length ; i++){
							if(params != undefined && data[i].KEY == params){
								target.append("<option value=\""+data[i].KEY+"\" selected >"+data[i].VALUE+"</option>");
							}else{
								target.append("<option value=\""+data[i].KEY+"\">"+data[i].VALUE+"</option>");
							}
						}
					}else{
						alert(result.EX_MESSAGE);
					}

				}
			});
		});

		$("#btnTripExpenses").click(function(){
			if($("input[name=tripDiv]").val() == "" || $("select[name=tripType]").val() == "X" || $("input[name=startDate]").val() == "" || $("input[name=startDate]").val() == ""){
				alert("출장구분, 출장시작, 출장종료, 출장유형을 입력해 주세요.");
				return;
			}

			$("#tripExpensesForm").find("span.etcInfo").remove();
			$("#tripExpensesForm").append("<span class=\"etcInfo\"></span>");

			$("#tripExpensesForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"tripDiv\" value=\""+$("#businessTripForm").find("input[name=tripDiv]").val()+"\"/>");
			$("#tripExpensesForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startDate\" value=\""+$("#businessTripForm").find("input[name=startDate]").val()+"\"/>");
			$("#tripExpensesForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startHour\" value=\""+$("#businessTripForm").find("select[name=startHour]").val()+"\"/>");
			$("#tripExpensesForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startMin\" value=\""+$("#businessTripForm").find("select[name=startMin]").val()+"\"/>");
			$("#tripExpensesForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endDate\" value=\""+$("#businessTripForm").find("input[name=endDate]").val()+"\"/>");
			$("#tripExpensesForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endHour\" value=\""+$("#businessTripForm").find("select[name=endHour]").val()+"\"/>");
			$("#tripExpensesForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endMin\" value=\""+$("#businessTripForm").find("select[name=endMin]").val()+"\"/>");
			$("#tripExpensesForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"tripType\" value=\""+$("#businessTripForm").find("select[name=tripType]").val()+"\"/>");
			$("#tripExpensesForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"tripPlace\" value=\""+$("#businessTripForm").find("input[name=tripPlace]").val()+"\"/>");
			$("#tripExpensesForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"tripResason\" value=\""+$("#businessTripForm").find("input[name=tripResason]").val()+"\"/>");

			var target = "tripExpensesPop";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=500px, height=500px, top=200px, left=300px, resizable=no";

			var popup = window.open("", target, param);

			$("#tripExpensesForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/retrieveTripExpenses.do'/>");
			$("#tripExpensesForm").attr("target", target);
			$("#tripExpensesForm").submit();

			popup.focus();

		});

		$.calcExchangeAmount = function(){
			var reg = /[^0-9]/gi;

			var tmp = $("input[name=amount]").val().replace(reg, "");
			var rate = $("input[name=exchangeRate]").val();
			var cal = Number(tmp) * Number(rate);

			$("input[name=amount]").val(tmp.addComma());
			$("input[name=exchangeAmount]").val(cal.toString().addComma());
		};

		$.initSet = function(){

			if("<c:out value="${params.dataLoad}"/>" == "Y"){
				page = "CHANGE";
			}

			$("input[name=tripDiv]").val("<c:out value='${params.ZCNTYP}'/>");

			<fmt:parseDate var="dateString" value="${params.DATE_BEG}" pattern="yyyyMMdd" />
			$("input[name=startDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");

			<fmt:parseDate var="dateString" value="${params.DATE_END}" pattern="yyyyMMdd" />
			$("input[name=endDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");

			$("select[name=startHour] option[value=<c:out value='${params.ZTIME_BEG}'/>]").attr("selected", "selected");
			$("select[name=startMin] option[value=<c:out value='${params.ZMINT_BEG}'/>]").attr("selected", "selected");
			$("select[name=endHour] option[value=<c:out value='${params.ZTIME_END}'/>]").attr("selected", "selected");
			$("select[name=endMin] option[value=<c:out value='${params.ZMINT_END}'/>]").attr("selected", "selected");
			$("select[name=tripType] option[value=<c:out value='${params.ACTIVITY_TYPE}'/>]").attr("selected", "selected");
			$("input[name=tripPlace]").val("<c:out value='${params.LOCATION_END}'/>");
			$("input[name=tripResason]").val("<c:out value='${params.REQUEST_REASON}'/>");

			$("input[name=amount]").val("<c:out value="${exAList.VORSC}"/>");

			$("input[name=currency]").parents("td").append("<c:out value="${exAList.WAERS}"/>");
			$("input[name=currency]").val("<c:out value="${exAList.WAERS}"/>");

			$("input[name=exchangeRate]").parents("td").append("<c:out value="${exAList.KURSV}"/>");
			$("input[name=exchangeRate]").val("<c:out value="${exAList.KURSV}"/>");

			<fmt:parseDate var="dateString" value="${exAList.DATVS}" pattern="yyyyMMdd" />
			$("input[name=exchangeApplyDate]").parents("td").append("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
			$("input[name=exchangeApplyDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");

			$.calcExchangeAmount();

			$("select[name=startTrans] option[value=<c:out value='${exBList.TRANS_BEG}'/>]").attr("selected", "selected");
			$("select[name=startTrans]").trigger("change", ["<c:out value='${exBList.TRANSGR_BEG}'/>"]);

			$("select[name=endTrans] option[value=<c:out value='${exBList.TRANS_END}'/>]").attr("selected", "selected");
			$("select[name=endTrans]").trigger("change", ["<c:out value='${exBList.TRANSGR_END}'/>"]);
		};

		$.callSaveRequest = function(){
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/diligenceMng/businessTripProcess.do'/>",
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

		$.placeAddValid = function(){
			if($.trim($("input[name=tripDiv]").val()) == "" || $.trim($("input[name=startDate]").val()) == "" ||
				$.trim($("select[name=startHour]").val()) == "" || $.trim($("select[name=startMin]").val()) == "" ||
				$.trim($("input[name=endDate]").val()) == "" || $.trim($("select[name=endHour]").val()) == "" ||
				$.trim($("select[name=endMin]").val()) == "" || $.trim($("select[name=tripType]").val()) == "X" ||
				$.trim($("input[name=tripPlace]").val()) == "" || $.trim($("input[name=tripResason]").val()) == "" ){
				return "FAIL";
			}
			return "PASS";
		};

		$.appendImList = function(clear){

			if(clear){
				$("#ajaxForm").html("");
			}

			if($("#ajaxForm").find("span.etcInfo").length == 0){
				$("#ajaxForm").append("<span class=\"etcInfo\"></span>");
			}

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"tripDiv\" value=\""+$("#businessTripForm").find("input[name=tripDiv]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startDate\" value=\""+$("#businessTripForm").find("input[name=startDate]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startHour\" value=\""+$("#businessTripForm").find("select[name=startHour]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startMin\" value=\""+$("#businessTripForm").find("select[name=startMin]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endDate\" value=\""+$("#businessTripForm").find("input[name=endDate]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endHour\" value=\""+$("#businessTripForm").find("select[name=endHour]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endMin\" value=\""+$("#businessTripForm").find("select[name=endMin]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"tripType\" value=\""+$("#businessTripForm").find("select[name=tripType]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"tripPlace\" value=\""+$("#businessTripForm").find("input[name=tripPlace]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"tripResason\" value=\""+$("#businessTripForm").find("input[name=tripResason]").val()+"\"/>");
		};

		$.appendBList = function(clear){

			if(clear){
				$("#ajaxForm").html("");
			}

			if($("#ajaxForm").find("span.etcInfo").length == 0){
				$("#ajaxForm").append("<span class=\"etcInfo\"></span>");
			}

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startTrans\" value=\""+$("#businessTripForm").find("select[name=startTrans]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endTrans\" value=\""+$("#businessTripForm").find("select[name=endTrans]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startGrade\" value=\""+$("#businessTripForm").find("select[name=startGrade]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endGrade\" value=\""+$("#businessTripForm").find("select[name=endGrade]").val()+"\"/>");
		};

		$.appendEList = function(clear){

			if(clear){
				$("#ajaxForm").html("");
			}

			if($("#ajaxForm").find("span.etcInfo").length == 0){
				$("#ajaxForm").append("<span class=\"etcInfo\"></span>");
			}

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"comment\" value=\""+$("#businessTripForm").find("textarea[name=comment]").val()+"\"/>");
		};

		$.appendAList = function(clear){

			if(clear){
				$("#ajaxForm").html("");
			}

			if($("#ajaxForm").find("span.etcInfo").length == 0){
				$("#ajaxForm").append("<span class=\"etcInfo\"></span>");
			}

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"amount\" value=\""+$("#businessTripForm").find("input[name=amount]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"currency\" value=\""+$("#businessTripForm").find("input[name=currency]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"exchangeRate\" value=\""+$("#businessTripForm").find("input[name=exchangeRate]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"exchangeApplyDate\" value=\""+$("#businessTripForm").find("input[name=exchangeApplyDate]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"exchangeAmount\" value=\""+$("#businessTripForm").find("input[name=exchangeAmount]").val()+"\"/>");
		};

		$.placeAddSet = function(result){

			var tab = $("#placeTable").find("tbody");
			var sb = "";

			sb += "<tr>";
			sb += "<td class=\"f_center\"><input type=\"checkbox\" name=\"placeFlag\"/></td>";
			sb += "<td class=\"f_center\">";
			sb += "	<input type=\"text\" name=\"tripPlaceLine\" class=\"input\" value=\"\"/>";
			sb += "</td>";
			sb += "<td class=\"f_center\">"+result.ET_RLIST[0]['COUNTRY_END']+"<input type=\"hidden\" name=\"countryKeyLine\" value=\""+result.ET_RLIST[0]['COUNTRY_END']+"\"/></td>";
			sb += "<td class=\"f_center\">"+result.ET_RLIST[0]['ZCNTXT']+"<input type=\"hidden\" name=\"countryNameLine\" value=\""+result.ET_RLIST[0]['ZCNTXT']+"\"/></td>";
			sb += "<td class=\"f_center\"><input type=\"text\" name=\"tripPlaceDate\" class=\"input datepicker w70per\" value=\"\" readonly=\"readonly\"/>&nbsp;<img src=\"<c:url value='/base/images/icon/ic_cal.gif'/>\" align=\"absmiddle\" alt=\"calendar\"/></td>";
			sb += "<td class=\"f_center\">";
			sb += "	<select name=\"tripPlaceStartHour\" class=\"\">";
			<c:forEach var="result" items="${hourList}">
			sb += "		<option value=\"<c:out value='${result.KEY }'/>\"><c:out value='${result.VALUE }'/></option>";
			</c:forEach>
			sb += "	</select>";
			sb += "</td>";
			sb += "<td class=\"f_center\">";
			sb += "	<select name=\"tripPlaceStartMin\" class=\"\">";
			<c:forEach var="result" items="${minList}">
			sb += "		<option value=\"<c:out value='${result.KEY }'/>\"><c:out value='${result.VALUE }'/></option>";
			</c:forEach>
			sb += "	</select>";
			sb += "</td>";
			sb += "<td class=\"f_center\">";
			sb += "	<select name=\"tripPlaceEndHour\" class=\"\">";
			<c:forEach var="result" items="${hourList}">
			sb += "		<option value=\"<c:out value='${result.KEY }'/>\"><c:out value='${result.VALUE }'/></option>";
			</c:forEach>
			sb += "	</select>";
			sb += "</td>";
			sb += "<td class=\"f_center\">";
			sb += "	<select name=\"tripPlaceEndMin\" class=\"\">";
			<c:forEach var="result" items="${minList}">
			sb += "		<option value=\"<c:out value='${result.KEY }'/>\"><c:out value='${result.VALUE }'/></option>";
			</c:forEach>
			sb += "	</select>";
			sb += "</td>";
			sb += "<td class=\"f_center\">";
			sb += "	<input type=\"text\" name=\"tripPlaceReason\" class=\"input\" value=\"\"/>";
			sb += "</td>";
			sb += "<td class=\"f_center\">";
			sb += "	<select name=\"tripPlaceType\" class=\"\">";
			<c:forEach var="result" items="${tripTypeList}">
			sb += "		<option value=\"<c:out value='${result.KEY }'/>\"><c:out value='${result.VALUE }'/></option>";
			</c:forEach>
			sb += "	</select>";
			sb += "</td>";
			sb += "<span class=\"tripPlaceInfo\">";
			<c:forEach items="${rListKeySet}" var="key">
			sb += "	<input type=\"hidden\" name=\"RLIST_<c:out value="${key}"/>\" value=\""+result.ET_RLIST[0]['<c:out value='${key}'/>']+"\"/>";
			</c:forEach>
			sb += "</span>";
			sb += "</tr>";

			tab.append(sb);

			$("select[name=tripPlaceType]:last option[value="+result.ET_RLIST[0]['ACTIVITY']+"]").attr("selected", "selected");
			$("input[name=tripPlaceDate]:last").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		};

		$.setParam = function(eventId){

			$("#ajaxForm").html("");

			<%//리스트에서 선택한 원본 데이터 %>

			$("#ajaxForm").append("<span class=\"rowInfo\"></span>");
			$("#ajaxForm").find("span.rowInfo").html($("#businessTripForm").find("span.rowInfo").html());

			$("#ajaxForm").append("<span class=\"aListInfo\"></span>");
			$("#ajaxForm").find("span.aListInfo").html($("#businessTripForm").find("span.aListInfo").html());

			$("#ajaxForm").append("<span class=\"eListInfo\"></span>");
			$("#ajaxForm").find("span.eListInfo").html($("#businessTripForm").find("span.eListInfo").html());

			$("#ajaxForm").append("<span class=\"bListInfo\"></span>");
			$("#ajaxForm").find("span.bListInfo").html($("#businessTripForm").find("span.bListInfo").html());

			<%//결재라인 데이터 처리%>
			$("#ajaxForm").append("<span class=\"apprInfo\"></span>");

			var apprCnt = 0;
			$("#businessTripForm").find("span.apprInfo").each(function(index){
				$("#ajaxForm").find("span.apprInfo").append($(this).html());
				apprCnt++;
			});
			$("#ajaxForm").find("span.apprInfo").append("<input type=\"hidden\" name=\"apprCnt\" value=\""+apprCnt+"\"/>");

			<%//IT_RLIST%>
			$("#ajaxForm").append("<span class=\"addTripPlaceInfo\"></span>");
			var addTripPlaceCnt = 0;
			$("#businessTripForm").find("span.tripPlaceInfo").each(function(index){

				$("#businessTripForm").find("span.tripPlaceInfo").eq(index).find("input[name=RLIST_LOCATION_END]").val($("input[name=tripPlaceLine]").eq(index).val());
				$("#businessTripForm").find("span.tripPlaceInfo").eq(index).find("input[name=RLIST_COUNTRY_END]").val($("input[name=countryKeyLine]").eq(index).val());
				$("#businessTripForm").find("span.tripPlaceInfo").eq(index).find("input[name=RLIST_ZCNTXT]").val($("input[name=countryNameLine]").eq(index).val());
				$("#businessTripForm").find("span.tripPlaceInfo").eq(index).find("input[name=RLIST_DATE_BEG]").val($("input[name=tripPlaceDate]").eq(index).val());
				$("#businessTripForm").find("span.tripPlaceInfo").eq(index).find("input[name=RLIST_ZTIME_BEG]").val($("select[name=tripPlaceStartHour]").eq(index).val());
				$("#businessTripForm").find("span.tripPlaceInfo").eq(index).find("input[name=RLIST_ZMINT_BEG]").val($("select[name=tripPlaceStartMin]").eq(index).val());
				$("#businessTripForm").find("span.tripPlaceInfo").eq(index).find("input[name=RLIST_ZTIME_END]").val($("select[name=tripPlaceEndHour]").eq(index).val());
				$("#businessTripForm").find("span.tripPlaceInfo").eq(index).find("input[name=RLIST_ZMINT_END]").val($("select[name=tripPlaceEndMin]").eq(index).val());
				$("#businessTripForm").find("span.tripPlaceInfo").eq(index).find("input[name=RLIST_REASON]").val($("input[name=tripPlaceReason]").eq(index).val());
				$("#businessTripForm").find("span.tripPlaceInfo").eq(index).find("input[name=RLIST_ACTIVITY]").val($("select[name=tripPlaceType]").eq(index).val());
				$("#businessTripForm").find("span.tripPlaceInfo").eq(index).find("input[name=RLIST_ACTIVITYT]").val($("select[name=tripPlaceType]").eq(index).find("option:selected").text());

				$("#ajaxForm").find("span.addTripPlaceInfo").append($(this).html());
				addTripPlaceCnt++;
			});
			$("#ajaxForm").find("span.addTripPlaceInfo").append("<input type=\"hidden\" name=\"addTripPlaceCnt\" value=\""+addTripPlaceCnt+"\"/>");

			<%//그외 추가 데이터%>
			$("#ajaxForm").append("<span class=\"etcInfo\"></span>");

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"exFlag\" value=\""+"<c:out value="${params.exFlag}"/>"+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"eventId\" value=\""+eventId+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"page\" value=\""+page+"\"/>");

			<%//IM_LIST%>
			$.appendImList(false);

			<%//IT_ALIST%>
			$.appendAList(false);

			<%//IT_BLIST%>
			$.appendBList(false);

			<%//IT_ELIST%>
			$.appendEList(false);

			$.callSaveRequest();
		};

		$.initSet();
	});
})(jQuery);
</script>
<form id="businessTripForm" name="businessTripForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>출장 신청/조회</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
		<a href="#" class="button_img01" id="btnRequest"><span>신청</span></a>
		<a href="#" class="button_img01" id="btnTripExpenses"><span>출장여비 기준정보 조회</span></a>
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
				<col width="10%"/>
				<col width="*"/>
			</colgroup>
			<tr>
				<th class="list03_td_bg">출장구분</th>
				<td class="list03_td">국내
					<input type="hidden" name="tripDiv" value="1"/>
				</td>
			</tr>
			<tr>
				<th class="list03_td_bg">출장시작</th>
				<td class="list03_td">
					<input name="startDate" id="startDate" class="input datepicker" type="text" value="" readonly="readonly" />
					<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>&nbsp;&nbsp;&nbsp;&nbsp;
					시간
					<select name="startHour" class="">
						<c:forEach var="result" items="${hourList}">
							<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
						</c:forEach>
					</select>
					<select name="startMin" class="">
						<c:forEach var="result" items="${minList}">
							<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th class="list03_td_bg">출장종료</th>
				<td class="list03_td">
					<input name="endDate" id="endDate" class="input datepicker" type="text" value="" readonly="readonly" />
					<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>&nbsp;&nbsp;&nbsp;&nbsp;
					시간
					<select name="endHour" class="">
						<c:forEach var="result" items="${hourList}">
							<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
						</c:forEach>
					</select>
					<select name="endMin" class="">
						<c:forEach var="result" items="${minList}">
							<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th class="list03_td_bg">출장유형</th>
				<td class="list03_td">
					<select name="tripType" style="width:150px">
						<c:forEach var="result" items="${tripTypeList}">
							<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th class="list03_td_bg">최초출장지</th>
				<td class="list03_td">
					<input type="text" name="tripPlace" class="input w90per" value=""/>
				</td>
			</tr>
			<tr>
				<th class="list03_td_bg" rowspan="2">출장사유</th>
				<td class="list03_td">
					<input type="text" name="tripResason" class="input w90per" value=""/>
				</td>
			</tr>
			<tr>
				<td class="list03_td">
					<div class="f_blue">※ 출장사유는 16자이내로 작성해주시기 바랍니다. 자세한 내용은 아래주석에 입력하시기 바랍니다.</div>
				</td>
			</tr>
		</table>
	</div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">
			추가출장지
			<a href="#" class="button_img02" id="btnPlaceAdd"><span><img src="/base/images/ess/plus.png" alt=""/></span></a>
			<a href="#" class="button_img02" id="btnPlaceDel"><span><img src="/base/images/ess/minus.png" alt=""/></span></a>
		</p>

		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" id="placeTable">
				<caption></caption>
				<colgroup>
					<col width="3%"/>
					<col width="15%"/>
					<col width="7%"/>
					<col width="7%"/>
					<col width="*"/>
					<col width="7%"/>
					<col width="8%"/>
					<col width="7%"/>
					<col width="8%"/>
					<col width="15%"/>
					<col width="8%"/>
				</colgroup>
				<thead>
					<tr>
						<th></th>
						<th>출장지</th>
						<th>국가</th>
						<th>국가명</th>
						<th>시작일</th>
						<th>시작시간</th>
						<th>시작시간(분)</th>
						<th>종료시간</th>
						<th>종료시간(분)</th>
						<th>사유</th>
						<th>출장유형</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach items="${exRList }" var="exR">
						<tr>
							<td><input type="checkbox" name="placeFlag"/></td>
							<td>
								<input type="text" name="tripPlaceLine" class="input" value="<c:out value="${exR.LOCATION_END }"/>"/>
							</td>
							<td>
								<c:out value="${exR.COUNTRY_END }"/>
								<input type="hidden" name="countryKeyLine" value="<c:out value="${exR.COUNTRY_END }"/>"/>
							</td>
							<td>
								<c:out value="${exR.ZCNTXT }"/>
								<input type="hidden" name="countryNameLine" value="<c:out value="${exR.ZCNTXT }"/>"/>
							</td>
							<td>
								<fmt:parseDate var="dateString" value="${exR.DATE_BEG}" pattern="yyyyMMdd" />
								<input type="text" name="tripPlaceDate" class="datepicker input" value="<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />" readonly="readonly"/>&nbsp;<img src="<c:url value='/base/images/icon/ic_cal.gif'/>" align="absmiddle" alt="calendar"/>
							</td>
							<td>
								<select name="tripPlaceStartHour" class="input">
									<c:forEach var="result" items="${hourList}">
										<option value="<c:out value='${result.KEY }'/>" <c:if test="${result.KEY eq exR.ZTIME_BEG }"> selected </c:if>><c:out value='${result.VALUE }'/></option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select name="tripPlaceStartMin" class="input">
									<c:forEach var="result" items="${minList}">
										<option value="<c:out value='${result.KEY }'/>" <c:if test="${result.KEY eq exR.ZMINT_BEG }"> selected </c:if>><c:out value='${result.VALUE }'/></option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select name="tripPlaceEndHour" class="input">
									<c:forEach var="result" items="${hourList}">
										<option value="<c:out value='${result.KEY }'/>" <c:if test="${result.KEY eq exR.ZTIME_END }"> selected </c:if>><c:out value='${result.VALUE }'/></option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select name="tripPlaceEndMin" class="input">
									<c:forEach var="result" items="${minList}">
										<option value="<c:out value='${result.KEY }'/>" <c:if test="${result.KEY eq exR.ZMINT_END }"> selected </c:if>><c:out value='${result.VALUE }'/></option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input type="text" name="tripPlaceReason" class="input" value="<c:out value="${exR.REASON }"/>"/>
							</td>
							<td>
								<select name="tripPlaceType" class="input">
									<c:forEach var="result" items="${tripTypeList}">
										<option value="<c:out value='${result.KEY }'/>" <c:if test="${result.KEY eq exR.ACTIVITY }"> selected </c:if>><c:out value='${result.VALUE }'/></option>
									</c:forEach>
								</select>
							</td>
							<span class="tripPlaceInfo">
								<c:forEach items="${rListKeySet}" var="key">
									<input type="hidden" name="RLIST_<c:out value="${key}"/>" value="<c:out value="${exR[key] }"/>"/>
								</c:forEach>
							</span>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</span>
	</div>

	<div class="table_box">
		<p class="f_title">가지급금</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<colgroup>
					<col width="20%"/>
					<col width="20%"/>
					<col width="20%"/>
					<col width="20%"/>
					<col width="20%"/>
				</colgroup>
				<tbody>
					<tr>
						<th>금액</th>
						<th>통화</th>
						<th>환율</th>
						<th>환율적용일자</th>
						<th>환산금액</th>
					</tr>
					<tr>
						<td><input type="text" name="amount" class="input f_right" value=""/></td>
						<td>
							<input type="hidden" name="currency" value=""/>
						</td>
						<td>
							<input type="hidden" name="exchangeRate" value=""/>
						</td>
						<td>
							<input type="hidden" name="exchangeApplyDate" value=""/>
						</td>
						<td><input type="text" name="exchangeAmount" class="input f_right disabled" value="" readonly="readonly"/></td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>

	<div class="table_box">
		<p class="f_title">교통편</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list02">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<colgroup>
					<col width="10%"/>
					<col width="40%"/>
					<col width="10%"/>
					<col width="40%"/>
				</colgroup>
				<tbody>
					<tr>
						<th class="list02_td_bg">출발교통편</th>
						<td class="list02_td f_left">
							<select name="startTrans" style="width:150px">
								<c:forEach var="result" items="${transList}">
									<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
								</c:forEach>
							</select>
						</td>
						<th class="list02_td_bg">복귀교통편</th>
						<td class="list02_td f_left">
							<select name="endTrans" style="width:150px">
								<c:forEach var="result" items="${transList}">
									<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th class="list02_td_bg">등급</th>
						<td class="list02_td f_left">
							<select name="startGrade" style="width:150px">
								<option val=""></option>
							</select>
						</td>
						<th class="list02_td_bg">등급</th>
						<td class="list02_td f_left">
							<select name="endGrade" style="width:150px">
								<option val=""></option>
							</select>
						</td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>

	<div class="table_box">
		<p class="f_title">주석</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<colgroup>
					<col width="10%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th class="list03_td_bg">주석</th>
						<td class="list03_td">
							<textarea rows="5" name="comment" style="width:95%"><c:out value="${exEList.LTEXT }"/></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>

	<span class="rowInfo">
		<c:forEach items="${keySet}" var="key">
			<input type="hidden" name="SEL_<c:out value='${key}'/>" value="<c:out value="${params[key]}"/>"/>
		</c:forEach>
	</span>

	<span class="aListInfo">
		<c:forEach items="${aListKeySet}" var="key">
			<input type="hidden" name="ALIST_<c:out value='${key}'/>" value="<c:out value="${exAList[key]}"/>"/>
		</c:forEach>
	</span>

	<span class="eListInfo">
		<c:forEach items="${eListKeySet}" var="key">
			<input type="hidden" name="ELIST_<c:out value='${key}'/>" value="<c:out value="${exEList[key]}"/>"/>
		</c:forEach>
	</span>

	<span class="bListInfo">
		<c:forEach items="${bListKeySet}" var="key">
			<input type="hidden" name="BLIST_<c:out value='${key}'/>" value="<c:out value="${exBList[key]}"/>"/>
		</c:forEach>
	</span>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post"></form>
<form id="apprLineForm" name="apprLineForm" method="post">
	<input type="hidden" name="menuType" value="PT008"/>
	<c:forEach items="${keySet}" var="key">
		<input type="hidden" name="<c:out value='${key}'/>" value="<c:out value="${params[key]}"/>"/>
	</c:forEach>
</form>
<form id="tripExpensesForm" name="tripExpensesForm" method="post">
<c:forEach items="${keySet}" var="key">
	<input type="hidden" name="<c:out value='${key}'/>" value="<c:out value="${params[key]}"/>"/>
</c:forEach>
</form>
<form id="currencyForm" name="currencyForm" method="post"></form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>