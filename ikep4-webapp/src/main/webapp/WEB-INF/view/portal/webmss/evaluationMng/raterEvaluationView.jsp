<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<c:set var="etbodyelements" value="${result.ET_BODY_ELEMENTS}"/>
<c:set var="etbodycells" value="${result.ET_BODY_CELLS}"/>
<c:set var="etbodycolumns" value="${result.ET_BODY_COLUMNS}"/>
<c:set var="etbodynotes" value="${result.ET_BODY_CELL_NOTES}"/>
<c:set var="etbodydescr" value="${result.ET_BODY_ELEMENT_DESCR}"/>
<c:set var="etbodyval" value="${result.ET_BODY_CELL_VAL_VALUES}"/>

<c:set var="etnotesFlg1" value="0"/>
<c:forEach var="etnotes" items="${etbodynotes}" varStatus="status">
<c:if test="${etnotes.COLUMN_IID == '0006'}">
<c:set var="etnotesFlg1" value="1"/>
</c:if>
</c:forEach>
<c:set var="etnotesFlg2" value="0"/>
<c:forEach var="etbody" items="${etbodyelements}" varStatus="status">
<c:forEach var="etnotes" items="${etbodynotes}" varStatus="status">
<c:if test="${etnotes.COLUMN_IID == '0004' && etnotes.ROW_IID == etbody.ROW_IID && !empty etnotes.TDLINE}">
<c:set var="etnotesFlg2" value="1"/>
</c:if>
</c:forEach>
</c:forEach>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		<%
		/*
			exFlag
			CR = 변경 요청
			D = 삭제
			C = 취소 요청
		*/
		%>
		var exFlag = "";
		var exResult = "E";
		var exMessage = "";
		var returnFlag = "FAIL";

		$("select[name=imYear] option[value=<c:out value='${params.imYear}'/>]").attr("selected", "selected");
		$("select[name=imMonth] option[value=<c:out value='${params.imMonth}'/>]").attr("selected", "selected");

		$("#searchButton").click(function() {
			$.callProgress();
			$("#holidayWorkForm").submit();
		});

		$("#btnWrite").click(function(){
			exFlag = "";
			$("#ajaxForm").find("input[name=exFlag]").remove();
			$("#ajaxForm").append("<input type='hidden' name='exFlag' value='"+exFlag+"'>");

			$("#ajaxForm").find("input[name=jspType]").remove();
			$("#ajaxForm").append("<input type='hidden' name='jspType' value='regView'>");
			
			$("#ajaxForm").append("<input type='hidden' name='searchParam' value='?imYear=<c:out value='${params.imYear}'/>&imMonth=<c:out value='${params.imMonth}'/>'/>");
			
			$("#ajaxForm").attr("method", "post");
			$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/holidayWorkView.do'/>");
			$("#ajaxForm").submit();
		});

		$("#btnModify").click(function(){
			$.checkValidation("CHANGE");

			if(returnFlag == "PASS"){
				$("#ajaxForm").find("input[name=jspType]").remove();
				$("#ajaxForm").append("<input type='hidden' name='jspType' value='regView'>");
				
				$("#ajaxForm").append("<input type='hidden' name='searchParam' value='?imYear=<c:out value='${params.imYear}'/>&imMonth=<c:out value='${params.imMonth}'/>'/>");

				$("#ajaxForm").attr("method", "post");
				$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/holidayWorkView.do'/>");
				$("#ajaxForm").submit();
			}
		});

		$("#btnModifyRequest").click(function(){
			$.checkValidation("RECREATE");

			if(returnFlag == "PASS"){
				$("#ajaxForm").find("input[name=jspType]").remove();
				$("#ajaxForm").append("<input type='hidden' name='jspType' value='regView'>");
				
				$("#ajaxForm").append("<input type='hidden' name='searchParam' value='?imYear=<c:out value='${params.imYear}'/>&imMonth=<c:out value='${params.imMonth}'/>'/>");

				$("#ajaxForm").attr("method", "post");
				$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/holidayWorkView.do'/>");
				$("#ajaxForm").submit();
			}
		});

		$("#btnCancelRequest").click(function(){
			$.checkValidation("CANCEL");

			if(returnFlag == "PASS"){
				$("#ajaxForm").find("input[name=jspType]").remove();
				$("#ajaxForm").append("<input type='hidden' name='jspType' value='view'>");
				
				$("#ajaxForm").append("<input type='hidden' name='searchParam' value='?imYear=<c:out value='${params.imYear}'/>&imMonth=<c:out value='${params.imMonth}'/>'/>");

				$("#ajaxForm").attr("method", "post");
				$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/holidayWorkView.do'/>");
				$("#ajaxForm").submit();
			}
		});

		$("#btnDelete").click(function(){
			$.checkValidation("DELETE");

			if(returnFlag == "PASS"){
				$("#ajaxForm").find("input[name=jspType]").remove();
				$("#ajaxForm").append("<input type='hidden' name='jspType' value='view'>");
				
				$("#ajaxForm").append("<input type='hidden' name='searchParam' value='?imYear=<c:out value='${params.imYear}'/>&imMonth=<c:out value='${params.imMonth}'/>'/>");

				$("#ajaxForm").attr("method", "post");
				$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/holidayWorkView.do'/>");
				$("#ajaxForm").submit();
			}
		});

		$("a[name=linkField]").click(function(){
			var tr = $(this).parents("tr");
			var allTr = $("#blktable").find("tbody").find("tr");
			var index = Number(allTr.index(tr));
			var rowInfo = $("span.rowInfo").eq(index).html();

			$("#ajaxForm").html("");
			$("#ajaxForm").html(rowInfo);
			$("#ajaxForm").append("<input type=\"hidden\" name=\"eventId\" value=\"\"/>");

			exFlag = "";
			exResult = "S";
			exMessage = "";
			returnFlag = "PASS";

			if(returnFlag == "PASS"){
				exFlag = "";
				$("#ajaxForm").find("input[name=exFlag]").remove();
				$("#ajaxForm").append("<input type='hidden' name='exFlag' value='"+exFlag+"'>");

				$("#ajaxForm").find("input[name=dataLoad]").remove();
				$("#ajaxForm").append("<input type='hidden' name='dataLoad' value='Y'>");

				$("#ajaxForm").find("input[name=jspType]").remove();
				$("#ajaxForm").append("<input type='hidden' name='jspType' value='view'>");
				
				$("#ajaxForm").append("<input type='hidden' name='searchParam' value='?imYear=<c:out value='${params.imYear}'/>&imMonth=<c:out value='${params.imMonth}'/>'/>");

				$("#ajaxForm").attr("method", "post");
				$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/holidayWorkView.do'/>");
				$("#ajaxForm").submit();
			}
		});

		$.setParam = function(eventId){
			var index = $("input:radio[name=flag]").index($("input:radio[name=flag]:checked"));
			var rowInfo = $("span.rowInfo").eq(index).html();

			$("#ajaxForm").html("");
			$("#ajaxForm").html(rowInfo);
			$("#ajaxForm").append("<input type=\"hidden\" name=\"eventId\" value=\""+eventId+"\"/>");
		};

		$.checkValidation = function(eventId){

			if($("input:radio[name=flag]:checked").length > 0){

				$.setParam(eventId);

				exFlag = "";
				exResult = "E";
				exMessage = "";
				returnFlag = "FAIL";

				if(eventId != ""){
					$jq.ajax({
						url : "<c:url value='/portal/moorimess/diligenceMng/checkedRowValidationPT003.do'/>",
						data : $("#ajaxForm").serialize(),
						type : "post",
						async: false,
						success : function(result) {
							if($.trim(result.EX_RESULT) == "S"){
								exFlag = result.EX_FLAG;
								exResult = result.EX_RESULT;
								exMessage = result.EX_MESSAGE;

								$.setValidationAfter();
							}else{
								alert(result.EX_MESSAGE);
							}
						}
					});
				}else{
					exResult = "S";
					returnFlag = "PASS";
				}
			}else{
				alert("선택된 휴일 근무 내역이 없습니다.");
				returnFlag = "FAIL";
			}
		};

		$.setValidationAfter = function(){
			if($.trim(exResult) == "E"){
				alert($.trim(exMessage));
				returnFlag = "FAIL";
			}else{
				$("#ajaxForm").find("input[name=exFlag]").remove();
				$("#ajaxForm").append("<input type='hidden' name='exFlag' value='"+$.trim(exFlag)+"'>");

				$("#ajaxForm").find("input[name=dataLoad]").remove();
				$("#ajaxForm").append("<input type='hidden' name='dataLoad' value='Y'>");
				returnFlag = "PASS";
			}
		};
		
		save = function(mode){ 
			$("#IM_EVENT_ID").val(mode);
			$("#raterEvaluationForm").attr("action", "<c:url value='/portal/moorimmss/evaluationMng/raterEvaluationSave.do'/>");
			$("#raterEvaluationForm").submit();
		};
	});
})(jQuery);;
</script>

<form id="raterEvaluationForm" name="raterEvaluationForm" method="post" action="<c:url value='/portal/moorimmss/evaluationMng/raterEvaluationView.do'/>" >
<input type="hidden" name="IM_EVENT_ID" id="IM_EVENT_ID" />
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>5급사원 평가자 평가</h1>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">헤더</p>
		<c:set var="etHeader" value="${result.EX_HEADER}"/>
	
		<div class="list02">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<th scope="row" width="10%" class="list02_td_bg">성명</th>
						<td width="20%" class="list02_td"><c:out value="${etHeader.ENAMET}"/></td>
						<th scope="row" width="10%" class="list02_td_bg">소속</th>
						<td width="*" class="list02_td"><c:out value="${etHeader.ORGTX}"/></td>
						<th scope="row" width="10%" class="list02_td_bg">평가자</th>
						<td width="20%" class="list02_td"><c:out value="${etHeader.PNAMET}"/></td>
					</tr>
					<tr>
						<th scope="row"  class="list02_td_bg">직급</th>
						<td class="list02_td"><c:out value="${etHeader.TRFGR}"/></td>
						<th scope="row"  class="list02_td_bg">입사일</th>
						<td class="list02_td">
							<fmt:parseDate var="dateString" value="${etHeader.A1DAT}" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateString}" pattern="yyyy.MM.dd" />
						</td>
						<td class="list02_td" colspan="2"></td>
					</tr>
					<tr>
						<th scope="row"  class="list02_td_bg">직군</th>
						<td class="list02_td"><c:out value="${etHeader.STEXT}"/></td>
						<th scope="row"  class="list02_td_bg">직무</th>
						<td class="list02_td" ><c:out value="${etHeader.STLTX}"/></td>
						<td class="list02_td" colspan="2"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id=""><span>뒤로</span></a>
		<a href="#" class="button_img01" id=""><span>인쇄</span></a>
		<c:if test="${result.EX_MODE=='X'}">
			<a href="javascript:save('SAVE');" class="button_img01"><span>저장</span></a>
			<a href="javascript:save('COMPLETE');" class="button_img01"><span>완료</span></a>
		</c:if>
	</div>
	
	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">5급사원 평가</p>
		
		<div class="list02">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<th scope="row" width="15%" class="list02_td_bg">평가항목</th>
						<th scope="row" width="30%" class="list02_td_bg">행동지표</th>
						<th scope="row" width="10%" class="list02_td_bg">가중치</th>
						<th scope="row" width="10%" class="list02_td_bg">평가등급</th>
						<th scope="row" width="10%" class="list02_td_bg">점수</th>
						<th scope="row" width="25%" class="list02_td_bg">근거</th>
					</tr>
					<c:forEach var="etbody" items="${etbodyelements}" varStatus="status">
					<c:if test="${etbody.ELEMENT_TYPE == 'VC'}">
					<tr>
						<!--  평가항목  -->
						<td class="list02_td"><c:out value="${etbody.NAME}"/></td>
						<!--  행동지표  -->
						<td class="list02_td">
							<c:forEach var="etdescr" items="${etbodydescr}" varStatus="status">
							<c:if test="${etbody.ROW_IID == etdescr.ROW_IID}">
							<c:out value="${etdescr.TLINE}"/>
							</c:if>
							</c:forEach>
						</td>
						<!--  가중치  -->
						<td class="list02_td">
							<c:forEach var="etcells" items="${etbodycells}" varStatus="status">
							<c:if test="${etcells.COLUMN_IID == '0001' && etbody.ROW_IID == etcells.ROW_IID}">
							<c:out value="${etcells.VALUE_TXT}"/>
							</c:if>
							</c:forEach>
						</td>
						<!--  평가등급  -->
						<td class="list02_td">
							<select>
							<option value=""></option>
							<c:forEach var="etcells" items="${etbodycells}" varStatus="status">
							
							<c:if test="${etcells.COLUMN_IID == '0002' && etbody.ROW_IID == etcells.ROW_IID}">
								<c:forEach var="etval" items="${etbodyval}" varStatus="status">
									<c:if test="${etval.COLUMN_IID == '0002' && etval.ROW_IID == etbody.ROW_IID}">
										<option value="${etval.VALUE_EID}" <c:if test="${etcells.VALUE_TEXT == etval.VALUE_TEXT}">selected="selected"</c:if>>${etval.VALUE_TEXT}</option>
									</c:if>
								</c:forEach>
							</c:if>
							<span class="rowInfo">
								<c:forEach items="${keySet7}" var="key">
									<input type="hidden" name="etbodycells_<c:out value="${key}"/>" value="<c:out value="${etcells[key]}"/>"/>
								</c:forEach>
							</span>
							</c:forEach>
							</select>
						</td>
						<!--  점수  -->
						<td class="list02_td">
							<c:forEach var="etcells" items="${etbodycells}" varStatus="status">
							<c:if test="${etcells.COLUMN_IID == '0003' && etbody.ROW_IID == etcells.ROW_IID}">
							<c:out value="${etcells.VALUE_TXT}"/>
							</c:if>
							</c:forEach>
						</td>
						<!--  근거  -->
						<td class="list02_td">
							<c:if test="${etnotesFlg2=='0'}">
								<textarea name="tdline" style="width:100%;" rows="5"></textarea>
							</c:if>
							<c:if test="${etnotesFlg2=='1'}">
								<c:forEach var="etnotes" items="${etbodynotes}" varStatus="status">
								<c:if test="${etnotes.COLUMN_IID == '0004' && etnotes.ROW_IID == etbody.ROW_IID && !empty etnotes.TDLINE}">
								<textarea name="tdline" style="width:100%;" rows="5"><c:out value="${etnotes.TDLINE}"/></textarea>
								</c:if>
								</c:forEach>
							</c:if>
						</td>
					</tr>
					</c:if>
					<span class="rowInfo">
						<c:forEach items="${keySet6}" var="key">
							<input type="hidden" name="etbodyelements_<c:out value="${key}"/>" value="<c:out value="${etbody[key]}"/>"/>
						</c:forEach>
					</span>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	
	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">평가자 의견</p>
	
		<div class="list02">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<th scope="row" width="10%" class="list02_td_bg">평가자 의견</th>
						<td width="*" class="list02_td">
							<c:if test="${etnotesFlg1=='0'}">
								<textarea name="tdline" style="width:100%;" rows="5"></textarea>
							</c:if>
							<c:if test="${etnotesFlg1=='1'}">
								<c:forEach var="etnotes" items="${etbodynotes}" varStatus="status">
								<c:if test="${etnotes.COLUMN_IID == '0006'}">
								<textarea name="tdline" style="width:100%;" rows="5"><c:out value="${etnotes.TDLINE}"/></textarea>
								</c:if>
								</c:forEach>
							</c:if>
						</td>
						<th scope="row" width="10%" class="list02_td_bg">평가점수</th>
						<td width="15%" class="list02_td">
							<c:out value="${result.EX_WEIGHT}"/>
						</td>
						<th scope="row" width="10%" class="list02_td_bg">평가등급</th>
						<td width="15%" class="list02_td">
							<c:forEach var="etcells" items="${etbodycells}" varStatus="status">
							<c:if test="${etcells.COLUMN_IID == '0005' && etcells.ROW_IID == '0002'}">
							<c:out value="${etcells.VALUE_TEXT}"/>
							</c:if>
							</c:forEach>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>

<c:set var="imList" value="${rfcParam.IM_LIST}"/>
<c:forEach items="${keySet1}" var="key">
	<input type="hidden" name="imList_<c:out value="${key}"/>" value="<c:out value="${imList[key]}"/>"/>
</c:forEach>

<c:set var="exappraisalId" value="${result.EX_APPRAISAL_ID}"/>
<c:forEach items="${keySet13}" var="key">
	<input type="hidden" name="exappraisalId_<c:out value="${key}"/>" value="<c:out value="${exappraisalId[key]}"/>"/>
</c:forEach>

<c:set var="exdocProcessing" value="${result.EX_DOC_PROCESSING}"/>
<c:forEach items="${keySet2}" var="key">
	<input type="hidden" name="exdocProcessing_<c:out value="${key}"/>" value="<c:out value="${exdocProcessing[key]}"/>"/>
</c:forEach>

<c:set var="etheaderStatus" value="${result.EX_HEADER_STATUS}"/>
<c:forEach items="${keySet14}" var="key">
	<input type="hidden" name="etheaderStatus_<c:out value="${key}"/>" value="<c:out value="${etheaderStatus[key]}"/>"/>
</c:forEach>

<c:set var="exheaderDates" value="${result.EX_HEADER_DATES}"/>
<c:forEach items="${keySet15}" var="key">
	<input type="hidden" name="exheaderDates_<c:out value="${key}"/>" value="<c:out value="${exheaderDates[key]}"/>"/>
</c:forEach>

<c:set var="exheaderTexts" value="${result.EX_HEADER_TEXTS }"/>
<c:forEach items="${keySet3}" var="key">
	<input type="hidden" name="exheaderTexts_<c:out value="${key}"/>" value="<c:out value="${exheaderTexts[key]}"/>"/>
</c:forEach>

<c:set var="exheaderDisplay" value="${result.EX_HEADER_DISPLAY }"/>
<c:forEach items="${keySet4}" var="key">
	<input type="hidden" name="exheaderDisplay_<c:out value="${key}"/>" value="<c:out value="${exheaderDisplay[key]}"/>"/>
</c:forEach>

<c:set var="etbodyElements" value="${result.ET_BODY_ELEMENTS }"/>
<c:set var="etbodyElementsKeySet" value="${keySet6}"/>
<input type="hidden" name="etbodyElementsCnt" value="${fn:length(etbodyElements)}"/>
<c:forEach var="bodyElements" items="${etbodyElements}" varStatus="status">
	<c:forEach items="${etbodyElementsKeySet}" var="key">
		<input type="hidden" id="etbodyElements_<c:out value="${key}"/>_${status.index}" name="etbodyElements_<c:out value="${key}"/>_${status.index}" value="<c:out value="${bodyElements[key] }"/>"/>
	</c:forEach>
</c:forEach>

<c:set var="etbodyCells" value="${result.ET_BODY_CELLS }"/>
<c:set var="etbodyCellsKeySet" value="${keySet7}"/>
<input type="hidden" name="etbodyCellsCnt" value="${fn:length(etbodyCells)}"/>
<c:forEach var="bodyCells" items="${etbodyCells}" varStatus="status">
	<c:forEach items="${etbodyElementsKeySet}" var="key">
		<input type="hidden" id="etbodyCells_<c:out value="${key}"/>_${status.index}" name="etbodyCells_<c:out value="${key}"/>_${status.index}" value="<c:out value="${bodyCells[key] }"/>"/>
	</c:forEach>
</c:forEach>

<c:set var="etbodyCellNotes" value="${result.ET_BODY_CELL_NOTES }"/>
<c:set var="etbodyCellNotesKeySet" value="${keySet9}"/>
<input type="hidden" name="etbodyCellNotesCnt" value="${fn:length(etbodyCellNotes)}"/>
<c:forEach var="bodyCellNotes" items="${etbodyCellNotes}" varStatus="status">
	<c:forEach items="${etbodyCellNotesKeySet}" var="key">
		<input type="hidden" id="etbodyCellNotes_<c:out value="${key}"/>_${status.index}" name="etbodyCellNotes_<c:out value="${key}"/>_${status.index}" value="<c:out value="${bodyCellNotes[key] }"/>"/>
	</c:forEach>
</c:forEach>

<c:set var="etheaderAppraiser" value="${result.ET_HEADER_APPRAISER }"/>
<c:set var="etheaderAppraiserKeySet" value="${keySet10}"/>
<input type="hidden" name="etheaderAppraiserCnt" value="${fn:length(etheaderAppraiser)}"/>
<c:forEach var="headerAppraiser" items="${etheaderAppraiser}" varStatus="status">
	<c:forEach items="${etheaderAppraiserKeySet}" var="key">
		<input type="hidden" id="etheaderAppraiser_<c:out value="${key}"/>_${status.index}" name="etheaderAppraiser_<c:out value="${key}"/>_${status.index}" value="<c:out value="${headerAppraiser[key] }"/>"/>
	</c:forEach>
</c:forEach>

<c:set var="etheaderPartAppraisers" value="${result.ET_HEADER_PART_APPRAISERS }"/>
<c:set var="etheaderPartAppraisersKeySet" value="${keySet8}"/>
<input type="hidden" name="etheaderPartAppraisersCnt" value="${fn:length(etheaderPartAppraisers)}"/>
<c:forEach var="headerPartAppraisers" items="${etheaderPartAppraisers}" varStatus="status">
	<c:forEach items="${etheaderPartAppraisersKeySet}" var="key">
		<input type="hidden" id="etheaderPartAppraisers_<c:out value="${key}"/>_${status.index}" name="etheaderPartAppraisers_<c:out value="${key}"/>_${status.index}" value="<c:out value="${headerPartAppraisers[key] }"/>"/>
	</c:forEach>
</c:forEach>

<c:set var="etheaderOthers" value="${result.ET_HEADER_OTHERS }"/>
<c:set var="etheaderOthersKeySet" value="${keySet11}"/>
<input type="hidden" name="etheaderOthersCnt" value="${fn:length(etheaderOthers)}"/>
<c:forEach var="headerOthers" items="${etheaderOthers}" varStatus="status">
	<c:forEach items="${etheaderOthersKeySet}" var="key">
		<input type="hidden" id="etheaderOthers_<c:out value="${key}"/>_${status.index}" name="etheaderOthers_<c:out value="${key}"/>_${status.index}" value="<c:out value="${headerOthers[key] }"/>"/>
	</c:forEach>
</c:forEach>

<c:set var="etbodyColumns" value="${result.ET_BODY_COLUMNS }"/>
<c:set var="etbodyColumnsKeySet" value="${keySet12}"/>
<input type="hidden" name="etbodyColumnsCnt" value="${fn:length(etbodyColumns)}"/>
<c:forEach var="bodyColumns" items="${etbodyColumns}" varStatus="status">
	<c:forEach items="${etbodyColumnsKeySet}" var="key">
		<input type="hidden" id="etbodyColumns_<c:out value="${key}"/>_${status.index}" name="etbodyColumns_<c:out value="${key}"/>_${status.index}" value="<c:out value="${bodyColumns[key] }"/>"/>
	</c:forEach>
</c:forEach>

</form>



<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>