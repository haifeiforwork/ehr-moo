<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#btnClose").click(function(){
			self.close();
		});

		$("#btnApprove, #btnReject, #btnSave").click(function(){

			var id = $(this).prop("id");
			var process = false;
			var eventId;

			if(id == "btnApprove"){
				if(confirm("승인 하시겠습니까?")){
					process = true;
					eventId = "APPLY";
				}
			}else if(id == "btnSave"){
				if(confirm("저장 하시겠습니까?")){
					process = true;
					eventId = "SAVE";
				}
			}else{
				if(confirm("반려 하시겠습니까?")){
					process = true;
					eventId = "REFUSE";
				}
			}

			$("#exList_CPURPS").val($("#cpurps").val());
			$("#exList_SUBMIT").val($("#submission").val());
			
			if(process){
				$("#approvalForm").find("input[name=eventId]").remove();
				$("#approvalForm").append("<input type=\"hidden\" name=\"eventId\" value=\""+eventId+"\"/>");
				$jq.ajax({
					url : "<c:url value='/portal/moorimess/certificateOnInput.do'/>",
					data : $("#approvalForm").serialize(),
					type : "post",
					success : function(result) {
						alert(result.EX_MESSAGE);
						if(result.EX_RESULT == "S"){
							opener.$jq.search();
						}
					}
				});
			}
		});

		$("#btnRejectReason").click(function(){
			$(this).hide();
			$(".reject").show();
		});

		$(".reject").hide();

	});
})(jQuery);;
</script>
<form id="approvalForm" name="approvalForm" method="post">
<div id="wrap">
	<!-- 상단버튼-->
	<div class="button_box">
		<c:if test="${params.approveFlag eq 'N' }">
			<a href="#" class='button_img01' id="btnApprove"><span>승인</span></a>
			<a href="#" class='button_img01' id="btnRejectReason"><span>반려사유작성</span></a>
			<a href="#" class='button_img01 reject' id="btnReject"><span>반려</span></a>
			<a href="#" class='button_img01' id="btnSave"><span>저장</span></a>
		</c:if>
		<a href="#" class='button_img01' id="btnClose"><span>닫기</span></a>
	</div>

	<div class="table_box">
		<c:set var="apprList" value="${resultInit.ET_LINE }"/>
		<p class="f_title" style="padding-bottom:10px">결재라인</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<colgroup>
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
					<c:forEach var="appr" items="${apprList}" varStatus="status">
						<tr>
							<td>
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
						<col width="25%"/>
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

	<div class="table_box reject">
		<p class="f_title" style="padding-bottom:10px">반려사유입력</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01 reject">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<colgroup>
					<col width="15%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th class="list03_td_bg">반려사유</th>
						<td class="list03_td">
							<textarea name="reason" rows="5" style="width:95%"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>

	<c:set var="exList" value="${resultInit.EX_LIST }"/>

	
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
					<th class="list03_td_bg">신청일자</th>
					<td class="list03_td">
						<fmt:parseDate var="dateString" value="${exList.AEDTM}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
					</td>
					<th class="list03_td_bg">신청구분</th>
					<td class="list03_td">
						<c:forEach var="result" items="${resultInit.ET_CERTI}">
							<c:if test="${exList.CERTI == result.KEY}">
								<c:out value="${result.VALUE}"/>
							</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">수신방법</th>
					<td class="list03_td">
						<c:forEach var="result" items="${resultInit.ET_RECEIVE}">
							<c:if test="${exList.RECEIVE == result.KEY}">
								<c:out value="${result.VALUE}"/>
							</c:if>
						</c:forEach>
					</td>
					<th class="list03_td_bg">신청부수</th>
					<td class="list03_td">
						<c:forEach var="result" items="${resultInit.ET_COPIES}">
							<c:if test="${exList.COPIES == result.KEY}">
								<c:out value="${result.VALUE}"/>
							</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">용도</th>
					<td class="list03_td">
						<select name="cpurps" id="cpurps" style="width:150px" class="parameter">
							<c:forEach var="result" items="${resultInit.ET_USEFOR}">
								<option value="<c:out value="${result.KEY}"/>" <c:if test="${exList.CPURPS == result.KEY}">selected="selected"</c:if>><c:out value="${result.VALUE}"/></option>
							</c:forEach>
						</select>
					</td>
					<th class="list03_td_bg">제출처</th>
					<td class="list03_td" colspan="3">
						<input type="text" name="submission" id="submission" class="input w70per parameter" value="${exList.SUBMIT}" />
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">담당자</th>
					<td class="list03_td" colspan="3">
						<c:out value="${exList.DPPNM}"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
<input type="hidden" name="APPID" value="<c:out value="${params.APPID }"/>"/>
<input type="hidden" name="APLEV" value="<c:out value="${params.APLEV }"/>"/>
<input type="hidden" name="INFTY" value="<c:out value="${params.INFTY }"/>"/>

<input type="hidden" name="IM_ASTAT" value="<c:out value="${resultInit.EX_ASTAT}"/>"/>
<input type="hidden" name="IM_REFUSE_FLAG" value="<c:out value="${resultInit.EX_REFUSE_FLAG}"/>"/>

<span class="exList">
	<c:set var="keySet" value="${params.keySet}"/>
	<c:forEach var="key" items="${keySet}">
		<input type="hidden" id="exList_<c:out value="${key}"/>" name="exList_<c:out value="${key}"/>" value="<c:out value="${exList[key]}"/>"/>
	</c:forEach>
</span>

<span class="etLine">
	<input type="hidden" name="etLineCnt" value="${fn:length(apprList)}"/>
	<c:set var="apprKeySet" value="${params.apprKeySet }"/>
	<c:forEach var="appr" items="${apprList}" varStatus="status">
		<c:forEach items="${apprKeySet}" var="key">
			<input type="hidden" name="etLine_<c:out value="${key}"/>" value="<c:out value="${appr[key] }"/>"/>
		</c:forEach>
	</c:forEach>
</span>

<c:set var="apprAbleList" value="${resultInit.ET_APPNR }"/>
<span class="etAppnr">
	<input type="hidden" name="etAppnrCnt" value="${fn:length(apprAbleList)}"/>
	<c:set var="apprAbleKeySet" value="${params.apprAbleKeySet }"/>
	<c:forEach var="apprAble" items="${apprAbleList}" varStatus="status">
		<c:forEach items="${apprAbleKeySet}" var="key">
			<input type="hidden" name="etAppnr_<c:out value="${key}"/>" value="<c:out value="${apprAble[key] }"/>"/>
		</c:forEach>
	</c:forEach>
</span>

</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>