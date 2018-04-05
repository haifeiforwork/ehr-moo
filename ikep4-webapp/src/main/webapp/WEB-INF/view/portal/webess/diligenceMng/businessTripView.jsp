<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		var page = "DISPLAY";

		$("#btnBack").click(function(){
			$("#businessTripForm").attr("method", "post");
			$("#businessTripForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/businessTripList.do'/><c:out value='${params.searchParam}' escapeXml='false'/>");
			$("#businessTripForm").submit();
		});

		$("#btnCancelRequest, #btnDelete").click(function(){
			if(this.id == "btnCancelRequest"){
				$.setParam("CANCEL");
			}else if(this.id == "btnDelete"){
				if(confirm("정말로 삭제 하시겠습니까?")){
					$.setParam("DELETE");
				}
			}
		});

		$("input[name=startTrans], input[name=endTrans]").change(function(event, params){
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
							target = $("input[name=endGrade]");
							data = result.ET_GRADEB;
						}else{
							target = $("input[name=startGrade]");
							data = result.ET_GRADEA;
						}
						for(var i=0 ; i<data.length ; i++){
							if(params != undefined && data[i].KEY == params){
								target.parents("TD").append(data[i].VALUE);
								target.val(data[i].KEY);
							}
						}
					}else{
						alert(result.EX_MESSAGE);
					}
				}
			});
		});

		$("#btnTripExpenses").click(function(){
			if($("input[name=tripDiv]").val() == "" || $("input[name=tripType]").val() == "X" || $("input[name=startDate]").val() == "" || $("input[name=startDate]").val() == ""){
				alert("출장구분, 출장시작, 출장종료, 출장유형을 입력해 주세요.");
				return;
			}

			$("#tripExpensesForm").find("span.etcInfo").remove();
			$("#tripExpensesForm").append("<span class=\"etcInfo\"></span>");

			$("#tripExpensesForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"tripDiv\" value=\""+$("#businessTripForm").find("input[name=tripDiv]").val()+"\"/>");
			$("#tripExpensesForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startDate\" value=\""+$("#businessTripForm").find("input[name=startDate]").val()+"\"/>");
			$("#tripExpensesForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startHour\" value=\""+$("#businessTripForm").find("input[name=startHour]").val()+"\"/>");
			$("#tripExpensesForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startMin\" value=\""+$("#businessTripForm").find("input[name=startMin]").val()+"\"/>");
			$("#tripExpensesForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endDate\" value=\""+$("#businessTripForm").find("input[name=endDate]").val()+"\"/>");
			$("#tripExpensesForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endHour\" value=\""+$("#businessTripForm").find("input[name=endHour]").val()+"\"/>");
			$("#tripExpensesForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endMin\" value=\""+$("#businessTripForm").find("input[name=endMin]").val()+"\"/>");
			$("#tripExpensesForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"tripType\" value=\""+$("#businessTripForm").find("input[name=tripType]").val()+"\"/>");
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

		$.clearAList = function(){
			$("input[name=amount]").val("");
			$("input[name=currency]").val("KRW");
			$("input[name=exchangeRate]").val("1.00000");
			$("input[name=exchangeAmount]").val("");
		};

		$.getExchangeRate = function(){
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/diligenceMng/retrieveExchangeRate.do'/>",
				data : "IM_WAERS="+$("input[name=currency]").val()+"&IM_DATVS="+$("input[name=exchangeApplyDate]").val(),
				type : "post",
				success : function(result) {
					if($.trim(result.EX_RESULT) == "S"){
						var exKursv = Number(result.EX_KURSV) / 100;
						$("input[name=exchangeRate]").val(exKursv.toFixed(5));
					}else{
						alert(result.EX_MESSAGE);
						$("input[name=currency]").val("KRW");
						$("input[name=exchangeRate]").val("1.00000");
					}
				}
			});
		};

		$.calcExchangeAmount = function(){
			var reg = /[^0-9]/gi;

			var tmp = $("input[name=amount]").val().replace(reg, "");
			var rate = $("input[name=exchangeRate]").val();
			var cal = Number(tmp) * Number(rate);

			$("input[name=amount]").val(tmp.addComma());
			$("input[name=amount]").parents("TD").append(tmp.addComma());
			$("input[name=exchangeAmount]").val(cal.toString().addComma());
			$("input[name=exchangeAmount]").parents("TD").append(cal.toString().addComma());
		};

		$.initSet = function(){

			$.calcExchangeAmount();
			$("input[name=startTrans]").trigger("change", ["<c:out value='${exBList.TRANSGR_BEG}'/>"]);
			$("input[name=endTrans]").trigger("change", ["<c:out value='${exBList.TRANSGR_END}'/>"]);
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

		$.appendImList = function(clear){

			if(clear){
				$("#ajaxForm").html("");
			}

			if($("#ajaxForm").find("span.etcInfo").length == 0){
				$("#ajaxForm").append("<span class=\"etcInfo\"></span>");
			}

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"tripDiv\" value=\""+$("#businessTripForm").find("input[name=tripDiv]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startDate\" value=\""+$("#businessTripForm").find("input[name=startDate]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startHour\" value=\""+$("#businessTripForm").find("input[name=startHour]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startMin\" value=\""+$("#businessTripForm").find("input[name=startMin]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endDate\" value=\""+$("#businessTripForm").find("input[name=endDate]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endHour\" value=\""+$("#businessTripForm").find("input[name=endHour]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endMin\" value=\""+$("#businessTripForm").find("input[name=endMin]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"tripType\" value=\""+$("#businessTripForm").find("input[name=tripType]").val()+"\"/>");
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

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startTrans\" value=\""+$("#businessTripForm").find("input[name=startTrans]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endTrans\" value=\""+$("#businessTripForm").find("input[name=endTrans]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startGrade\" value=\""+$("#businessTripForm").find("input[name=startGrade]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endGrade\" value=\""+$("#businessTripForm").find("input[name=endGrade]").val()+"\"/>");
		};

		$.appendEList = function(clear){

			if(clear){
				$("#ajaxForm").html("");
			}

			if($("#ajaxForm").find("span.etcInfo").length == 0){
				$("#ajaxForm").append("<span class=\"etcInfo\"></span>");
			}

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"comment\" value=\""+$("#businessTripForm").find("input[name=comment]").val()+"\"/>");
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
				$("#businessTripForm").find("span.tripPlaceInfo").eq(index).find("input[name=RLIST_ZTIME_BEG]").val($("input[name=tripPlaceStartHour]").eq(index).val());
				$("#businessTripForm").find("span.tripPlaceInfo").eq(index).find("input[name=RLIST_ZMINT_BEG]").val($("input[name=tripPlaceStartMin]").eq(index).val());
				$("#businessTripForm").find("span.tripPlaceInfo").eq(index).find("input[name=RLIST_ZTIME_END]").val($("input[name=tripPlaceEndHour]").eq(index).val());
				$("#businessTripForm").find("span.tripPlaceInfo").eq(index).find("input[name=RLIST_ZMINT_END]").val($("input[name=tripPlaceEndMin]").eq(index).val());
				$("#businessTripForm").find("span.tripPlaceInfo").eq(index).find("input[name=RLIST_REASON]").val($("input[name=tripPlaceReason]").eq(index).val());
				$("#businessTripForm").find("span.tripPlaceInfo").eq(index).find("input[name=RLIST_ACTIVITY]").val($("input[name=tripPlaceType]").eq(index).val());
				$("#businessTripForm").find("span.tripPlaceInfo").eq(index).find("input[name=RLIST_ACTIVITYT]").val($("input[name=tripPlaceTypeNm]").eq(index).val());

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
		<a href="#" class='button_img01' id="btnBack"><span>뒤로</span></a>
		<c:if test="${params.exFlag eq 'N' }">
			<a href="#" class='button_img01' id="btnCancelRequest"><span>취소신청</span></a>
		</c:if>
		<c:if test="${params.exFlag eq 'D' }">
			<a href="#" class='button_img01' id="btnDelete"><span>삭제</span></a>
		</c:if>
		<a href="#" class='button_img01' id="btnTripExpenses"><span>출장여비 기준정보 조회</span></a>
	</div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">결재라인</p>

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

	<c:if test="${!empty resultRefuse }">
		<div class="table_box">
			<p class="f_title" style="padding-bottom:10px">반려사유</p>
			<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<colgroup>
						<col width="10%"/>
						<col width="*"/>
					</colgroup>
					<tbody>
						<tr>
							<th class="list03_td_bg">반려사유</th>
							<td class="list03_td">
								<textarea rows="5" style="width:95%" readonly><c:out value="${resultRefuse.EX_TEXT }"/></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</span>
		</div>
	</c:if>

	<div class="list03">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="10%"/>
				<col width="*"/>
			</colgroup>
			<tr>
				<th class="list03_td_bg">출장구분</th>
				<td class="list03_td">
					<c:forEach var="result" items="${tripDivList}">
						<c:if test="${result.KEY eq params.ZCNTYP}"><c:out value="${result.VALUE }"/></c:if>
					</c:forEach>
					<input type="hidden" name="tripDiv" value="<c:out value='${params.ZCNTYP}'/>"/>
				</td>
			</tr>
			<tr>
				<th class="list03_td_bg">출장시작</th>
				<td class="list03_td">
					<fmt:parseDate var="dateString" value="${params.DATE_BEG}" pattern="yyyyMMdd" />
					<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
					<input type="hidden" name="startDate" value="<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					시간
					<c:out value='${params.ZTIME_BEG}'/> : <c:out value='${params.ZMINT_BEG}'/>
					<input type="hidden" name="startHour" value="<c:out value='${params.ZTIME_BEG}'/>"/>
					<input type="hidden" name="startMin" value="<c:out value='${params.ZMINT_BEG}'/>"/>
				</td>
			</tr>
			<tr>
				<th class="list03_td_bg">출장종료</th>
				<td class="list03_td">
					<fmt:parseDate var="dateString" value="${params.DATE_END}" pattern="yyyyMMdd" />
					<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
					<input type="hidden" name="endDate" value="<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					시간
					<c:out value='${params.ZTIME_END}'/> : <c:out value='${params.ZMINT_END}'/>
					<input type="hidden" name="endHour" value="<c:out value='${params.ZTIME_END}'/>"/>
					<input type="hidden" name="endMin" value="<c:out value='${params.ZMINT_END}'/>"/>
				</td>
			</tr>
			<tr>
				<th class="list03_td_bg">출장유형</th>
				<td class="list03_td">
					<c:forEach var="result" items="${tripTypeList}">
						<c:if test="${result.KEY eq params.ACTIVITY_TYPE}"><c:out value="${result.VALUE }"/></c:if>
					</c:forEach>
					<input type="hidden" name="tripType" value="<c:out value='${params.ACTIVITY_TYPE}'/>"/>
				</td>
			</tr>
			<tr>
				<th class="list03_td_bg">최초출장지</th>
				<td class="list03_td">
					<c:out value='${params.LOCATION_END}'/>
					<input type="hidden" name="tripPlace" value="<c:out value='${params.LOCATION_END}'/>"/>
				</td>
			</tr>
			<tr>
				<th class="list03_td_bg" rowspan="2">출장사유</th>
				<td class="list03_td">
					<c:out value='${params.REQUEST_REASON}'/><br/>
					<input type="hidden" name="tripResason" value="<c:out value='${params.REQUEST_REASON}'/>"/>
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
		<p class="f_title" style="padding-bottom:10px">추가출장지</p>

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
								<c:out value="${exR.LOCATION_END }"/>
								<input type="hidden" name="tripPlaceLine" value="<c:out value="${exR.LOCATION_END }"/>"/>
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
								<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								<input type="hidden" name="tripPlaceDate" value="<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />"/>
							</td>
							<td>
								<c:forEach var="result" items="${hourList}">
									<c:if test="${result.KEY eq exR.ZTIME_BEG }"><c:out value="${result.VALUE }"/></c:if>
								</c:forEach>
								<input type="hidden" name="tripPlaceStartHour" value="<c:out value="${exR.ZTIME_BEG }"/>"/>
							</td>
							<td>
								<c:forEach var="result" items="${minList}">
									<c:if test="${result.KEY eq exR.ZMINT_BEG }"><c:out value="${result.VALUE }"/></c:if>
								</c:forEach>
								<input type="hidden" name="tripPlaceStartMin" value="<c:out value="${exR.ZMINT_BEG }"/>"/>
							</td>
							<td>
								<c:forEach var="result" items="${hourList}">
									<c:if test="${result.KEY eq exR.ZTIME_END }"><c:out value="${result.VALUE }"/></c:if>
								</c:forEach>
								<input type="hidden" name="tripPlaceEndHour" value="<c:out value="${exR.ZTIME_END }"/>"/>
							</td>
							<td>
								<c:forEach var="result" items="${minList}">
									<c:if test="${result.KEY eq exR.ZMINT_END }"><c:out value="${result.VALUE }"/></c:if>
								</c:forEach>
								<input type="hidden" name="tripPlaceEndMin" value="<c:out value="${exR.ZMINT_END }"/>"/>
							</td>
							<td>
								<c:out value="${exR.REASON }"/>
								<input type="hidden" name="tripPlaceReason" value="<c:out value="${exR.REASON }"/>"/>
							</td>
							<td>
								<c:forEach var="result" items="${tripTypeList}">
									<c:if test="${result.KEY eq exR.ACTIVITY }">
										<c:out value="${result.VALUE }"/>
										<c:set var="ACTIVITYT" value="${result.VALUE }"/>
									</c:if>
								</c:forEach>
								<input type="hidden" name="tripPlaceType" value="<c:out value="${exR.ACTIVITY }"/>" >
								<input type="hidden" name="tripPlaceTypeNm" value="<c:out value="${ACTIVITYT }"/>" >
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
						<td>
							<input type="hidden" name="amount" value="<c:out value="${exAList.VORSC}"/>"/>
						</td>
						<td>
							<c:out value="${exAList.WAERS}"/>
							<input type="hidden" name="currency" value="<c:out value="${exAList.WAERS}"/>"/>
						</td>
						<td>
							<c:out value="${exAList.KURSV}"/>
							<input type="hidden" name="exchangeRate" value="<c:out value="${exAList.KURSV}"/>"/>
						</td>
						<td>
							<fmt:parseDate var="dateString" value="${exAList.DATVS}" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
							<input type="hidden" name="exchangeApplyDate" value="<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />"/>
						</td>
						<td>
							<input type="hidden" name="exchangeAmount" value="" />
						</td>
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
						<td class="list02_td">
							<c:forEach var="result" items="${transList}">
								<c:if test="${result.KEY eq exBList.TRANS_BEG }"><c:out value="${result.VALUE }"/></c:if>
							</c:forEach>
							<input type="hidden" name="startTrans" value="<c:out value="${exBList.TRANS_BEG }"/>" />
						</td>
						<th class="list02_td_bg">복귀교통편</th>
						<td class="list02_td">
							<c:forEach var="result" items="${transList}">
								<c:if test="${result.KEY eq exBList.TRANS_END }"><c:out value="${result.VALUE }"/></c:if>
							</c:forEach>
							<input type="hidden" name="endTrans" value="<c:out value="${exBList.TRANS_END }"/>" />
						</td>
					</tr>
					<tr>
						<th class="list02_td_bg">등급</th>
						<td class="list02_td">
							<input type="hidden" name="startGrade" value="" />
						</td>
						<th class="list02_td_bg">등급</th>
						<td class="list02_td">
							<input type="hidden" name="endGrade" value="" />
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
							<textarea rows="5" style="width:95%" readonly><c:out value="${exEList.LTEXT }"/></textarea>
							<input type="hidden" name="comment" value="<c:out value="${exEList.LTEXT }"/>">
						</td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>

	<span class="rowInfo">
		<c:forEach items="${keySet}" var="key">
			<c:set var="selKey">SEL_<c:out value="${key }"/></c:set>
			<input type="hidden" name="SEL_<c:out value='${key}'/>" value="<c:out value="${params[selKey]}"/>"/>
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
		<c:set var="selKey">SEL_<c:out value="${key }"/></c:set>
		<input type="hidden" name="<c:out value='${key}'/>" value="<c:out value="${params[selKey]}"/>"/>
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