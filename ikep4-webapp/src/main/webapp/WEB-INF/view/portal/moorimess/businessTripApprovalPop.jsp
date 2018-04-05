<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>

<c:choose>
	<c:when test="${resultInit.EX_ASTAT eq '06' }">
		<c:set var="prefix" value="취소"/>
	</c:when>
	<c:otherwise>
		<c:set var="prefix" value=""/>
	</c:otherwise>
</c:choose>

<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#btnClose").click(function(){
			self.close();
		});

		$("#btnApprove, #btnReject").click(function(){

			var id = $(this).prop("id");
			var process = false;
			var eventId;

			if(id == "btnApprove"){
				if(confirm("<c:out value="${prefix}"/>"+"승인 하시겠습니까?")){
					process = true;
					eventId = "APPLY";
				}
			}else{
				if(confirm("<c:out value="${prefix}"/>"+"반려 하시겠습니까?")){
					process = true;
					eventId = "REFUSE";
				}
			}

			if(process){
				$("#approvalForm").find("input[name=eventId]").remove();
				$("#approvalForm").append("<input type=\"hidden\" name=\"eventId\" value=\""+eventId+"\"/>");
				$jq.ajax({
					url : "<c:url value='/portal/moorimess/businessTripOnInput.do'/>",
					data : $("#approvalForm").serialize(),
					type : "post",
					success : function(result) {
						alert(result.EX_MESSAGE);
						opener.$jq.search();
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
			<a href="#" class='button_img01' id="btnApprove"><span><c:out value="${prefix}"/>승인</span></a>
			<a href="#" class='button_img01' id="btnRejectReason"><span><c:out value="${prefix}"/>반려사유작성</span></a>
			<a href="#" class='button_img01 reject' id="btnReject"><span><c:out value="${prefix}"/>반려</span></a>
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
									<c:out value="${appr.ATIME }"/>
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
						<col width="15%"/>
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
				<col width="*"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">신청자</th>
					<td class="list03_td">
						<c:set var="requester"><c:out value="${resultInit.EX_ENAME }"/>(사번:<c:out value="${exList.PERNR }"/>)</c:set>
						<c:out value="${requester }"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">출장시작</th>
					<td class="list03_td">
						<fmt:parseDate var="dateString" value="${exList.DATE_BEG}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						&nbsp;시간
						<c:out value="${exList.ZTIME_BEG}"/> : <c:out value="${exList.ZMINT_BEG}"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">출장종료</th>
					<td class="list03_td">
						<fmt:parseDate var="dateString" value="${exList.DATE_END}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						&nbsp;시간
						<c:out value="${exList.ZTIME_END}"/> : <c:out value="${exList.ZMINT_END}"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">출장구분</th>
					<td class="list03_td">
						<c:out value="${exList.CNTEXT }"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">출장유형</th>
					<td class="list03_td">
						<c:out value="${exList.ZACTXT }"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">최초출장지</th>
					<td class="list03_td">
						<c:out value="${exList.LOCATION_END }"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">출장사유</th>
					<td class="list03_td">
						<c:out value="${exList.REQUEST_REASON }"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">Cost Center</th>
					<td class="list03_td">
						<c:out value="${resultInit.EX_KOSTL }"/>&nbsp;<c:out value="${resultInit.EX_KOSTXT }"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<c:set var="etRlist" value="${resultInit.ET_RLIST }"/>

	<c:if test="${!empty etRlist }">
		<div class="table_box">
			<c:set var="apprList" value="${resultInit.ET_LINE }"/>
			<p class="f_title" style="padding-bottom:10px">추가출장지</p>

			<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<caption></caption>
					<colgroup>
						<col width="10%"/>
						<col width="10%"/>
						<col width="10%"/>
						<col width="10%"/>
						<col width="10%"/>
						<col width="10%"/>
						<col width="*"/>
						<col width="10%"/>
					</colgroup>
					<tbody>
						<tr>
							<th>출장지</th>
							<th>국가</th>
							<th>국가명</th>
							<th>시작일</th>
							<th>시작시간</th>
							<th>시작시간(분)</th>
							<th>사유</th>
							<th>출장유형</th>
						</tr>
						<c:forEach items="${etRlist }" var="result">
							<tr>
								<td><c:out value="${result.LOCATION_END }"/></td>
								<td><c:out value="${result.COUNTRY_END }"/></td>
								<td><c:out value="${result.ZCNTXT }"/></td>
								<td>
									<fmt:parseDate var="dateString" value="${result.DATE_BEG}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
								<td><c:out value="${result.ZTIME_BEG }"/></td>
								<td><c:out value="${result.ZMINT_BEG }"/></td>
								<td><c:out value="${result.REASON }"/></td>
								<td><c:out value="${result.ACTIVITYT }"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</span>
		</div>
	</c:if>

	<c:set var="exAlist" value="${resultInit.EX_ALIST }"/>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">가지급금</p>

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
						<td class="f_right"><fmt:formatNumber value="${exAlist.VORSC}" groupingUsed="true"/></td>
						<td><c:out value="${exAlist.WAERS }"/>&nbsp;(<c:out value="${exAlist.WATXT }"/>)</td>
						<td><c:out value="${exAlist.KURSV }"/></td>
						<td>
							<fmt:parseDate var="dateString" value="${exAlist.DATVS}" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						</td>
						<td  class="f_right"><fmt:formatNumber value="${exAlist.VORHW}" groupingUsed="true"/></td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>

	<c:set var="exBlist" value="${resultInit.EX_BLIST }"/>
	<c:set var="etTrans" value="${resultCode.ET_TRANS }"/>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">교통편</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
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
						<th class="list03_td_bg">출발교통편</th>
						<td class="list03_td">
							<c:set var="text" value=""/>
							<c:forEach items="${etTrans }" var="row">
								<c:if test="${row.KEY eq exBlist.TRANS_BEG}">
									<c:set var="text" value="${row.VALUE }"/>
								</c:if>
							</c:forEach>
							<c:out value="${text}"/>
						</td>
						<th class="list03_td_bg">복귀교통편</th>
						<td class="list03_td">
							<c:set var="text" value=""/>
							<c:forEach items="${etTrans }" var="row">
								<c:if test="${row.KEY eq exBlist.TRANS_END}">
									<c:set var="text" value="${row.VALUE }"/>
								</c:if>
							</c:forEach>
							<c:out value="${text}"/>
						</td>
					</tr>
					<tr>
						<th class="list03_td_bg">등급</th>
						<td class="list03_td">
							<c:out value="${exBlist.TRANSGR_BEG }"/>
						</td>
						<th class="list03_td_bg">등급</th>
						<td class="list03_td">
							<c:out value="${exBlist.TRANSGR_END }"/>
						</td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>

	<c:set var="exElist" value="${resultInit.EX_ELIST }"/>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">주석</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<colgroup>
					<col width="15%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr height="100px">
						<th class="list03_td_bg">주석</th>
						<td class="list03_td">
							<textarea rows="8" style="width:95%"><c:out value="${exElist.LTEXT }"/></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>
</div>

<input type="hidden" name="APPID" value="<c:out value="${params.APPID }"/>"/>
<input type="hidden" name="APLEV" value="<c:out value="${params.APLEV }"/>"/>
<input type="hidden" name="INFTY" value="<c:out value="${params.INFTY }"/>"/>

<input type="hidden" name="IM_ASTAT" value="<c:out value="${resultInit.EX_ASTAT}"/>"/>
<input type="hidden" name="IM_REFUSE_FLAG" value="<c:out value="${resultInit.EX_REFUSE_FLAG}"/>"/>

<span class="exList">
	<c:set var="keySet" value="${params.keySet }"/>
	<c:forEach var="key" items="${keySet }">
		<input type="hidden" name="exList_<c:out value="${key }"/>" value="<c:out value="${exList[key] }"/>"/>
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