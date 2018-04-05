<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>
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
				if(confirm("승인 하시겠습니까?")){
					process = true;
					eventId = "APPLY";
				}
			}else{
				if(confirm("반려 하시겠습니까?")){
					process = true;
					eventId = "REFUSE";
				}
			}

			if(process){
				$("#approvalForm").find("input[name=eventId]").remove();
				$("#approvalForm").append("<input type=\"hidden\" name=\"eventId\" value=\""+eventId+"\"/>");
				$jq.ajax({
					url : "<c:url value='/portal/moorimhass/reportEduOnInput.do'/>",
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
			<a href="#" class='button_img01' id="btnApprove"><span>승인</span></a>
			<a href="#" class='button_img01' id="btnRejectReason"><span>반려사유작성</span></a>
			<a href="#" class='button_img01 reject' id="btnReject"><span>반려</span></a>
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
					<col width="17%"/>
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
	<c:set var="exDlist" value="${resultInit.EX_DLIST }"/>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">과정정보</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<colgroup>
					<col width="17%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th class="list03_td_bg">과정명</th>
						<td class="list03_td">
							<c:out value="${exDlist.STEXT }"/>
						</td>
					</tr>
					<tr>
						<th class="list03_td_bg">참가자</th>
						<td class="list03_td">
							<c:out value="${exDlist.ENAME }"/>
						</td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">교육정보</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<colgroup>
					<col width="17%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th class="list03_td_bg">과정평가</th>
						<td class="list03_td">
							<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<caption></caption>
									<colgroup>
										<col width="*"/>
										<col width="12%"/>
										<col width="12%"/>
										<col width="12%"/>
										<col width="12%"/>
										<col width="12%"/>
									</colgroup>
									<thead>
										<tr>
											<th>항목</th>
											<th>매우우수</th>
											<th>우수</th>
											<th>보통</th>
											<th>미흡</th>
											<th>매우미흡</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>과정에 대한 전체적인 평가</td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZATOT eq '01' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZATOT eq '02' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZATOT eq '03' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZATOT eq '04' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZATOT eq '05' }">checked</c:if>/></td>
										</tr>
										<tr>
											<td>과정에 대한 이해도</td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZAUND eq '01' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZAUND eq '02' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZAUND eq '03' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZAUND eq '04' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZAUND eq '05' }">checked</c:if>/></td>
										</tr>
										<tr>
											<td>자기개발 도움정도</td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZASEL eq '01' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZASEL eq '02' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZASEL eq '03' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZASEL eq '04' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZASEL eq '05' }">checked</c:if>/></td>
										</tr>
										<tr>
											<td>과정내용의 향후 업무적용 도움 정도</td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZAUSI eq '01' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZAUSI eq '02' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZAUSI eq '03' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZAUSI eq '04' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZAUSI eq '05' }">checked</c:if>/></td>
										</tr>
									</tbody>
								</table>
							</span>
						</td>
					</tr>
					<tr>
						<th class="list03_td_bg">교육지원평가</th>
						<td class="list03_td">
							<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<caption></caption>
									<colgroup>
										<col width="*"/>
										<col width="10%"/>
										<col width="10%"/>
										<col width="10%"/>
										<col width="10%"/>
										<col width="10%"/>
									</colgroup>
									<thead>
										<tr>
											<th>항목</th>
											<th>매우우수</th>
											<th>우수</th>
											<th>보통</th>
											<th>미흡</th>
											<th>매우미흡</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>교육지원만족도</td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZHRSU eq '01' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZHRSU eq '02' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZHRSU eq '03' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZHRSU eq '04' }">checked</c:if>/></td>
											<td><input type="radio" value="" disabled <c:if test="${exDlist.ZHRSU eq '05' }">checked</c:if>/></td>
										</tr>
									</tbody>
								</table>
							</span>
						</td>
					</tr>
					<tr>
						<th class="list03_td_bg">교육결과</th>
						<td class="list03_td">자격증취득여부&nbsp;
							<c:forEach items="${resultInit.ET_QUALI }" var="result">
								<c:if test="${result.KEY eq exDlist.QUALI }">
									<c:out value="${result.VALUE }"/>
								</c:if>
							</c:forEach>
						</td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">교육내용</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<colgroup>
					<col width="17%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th class="list03_td_bg">교육내용</th>
						<td class="list03_td">
							<textarea rows="6" style="width:95%"><c:out value="${resultInit.EX_CONT }"/></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">교육소감(교육과정에 대한 장단점/업무개선 Idea/건의사항등 )</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<colgroup>
					<col width="17%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th class="list03_td_bg">교육소감</th>
						<td class="list03_td">
							<textarea rows="6" style="width:95%"><c:out value="${resultInit.EX_FEEL }"/></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>


	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">전달교육 여부</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<colgroup>
					<col width="17%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th class="list03_td_bg">전달교육 여부</th>
						<td class="list03_td">
							<c:forEach items="${resultInit.ET_REEDU }" var="result">
								<c:if test="${result.KEY eq exDlist.REEDU }">
									<c:out value="${result.VALUE }"/>
								</c:if>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th class="list03_td_bg">전달교육 날짜</th>
						<td class="list03_td">
							<fmt:parseDate var="dateString" value="${exDlist.EFROM}" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
							&nbsp;~&nbsp;
							<fmt:parseDate var="dateString" value="${exDlist.EDUTO}" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						</td>
					</tr>
					<tr>
						<th class="list03_td_bg">전달교육 장소</th>
						<td class="list03_td">
							<c:out value="${exDlist.REEPL }"/>
						</td>
					</tr>
				</tbody>
			</table>
		</span>
	</div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">첨부파일</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<colgroup>
					<col width="17%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th class="list03_td_bg">첨부파일1</th>
						<td class="list03_td">
							<a href="<c:url value="${exDlist.ATCH1 }"/>" target="_blank" class="board_2"><c:out value="${exDlist.ATCH1 }"/></a>
						</td>
					</tr>
					<tr>
						<th class="list03_td_bg">첨부파일2</th>
						<td class="list03_td">
							<a href="<c:url value="${exDlist.ATCH2 }"/>" target="_blank" class="board_2"><c:out value="${exDlist.ATCH2 }"/></a>
						</td>
					</tr>
					<tr>
						<th class="list03_td_bg">첨부파일3</th>
						<td class="list03_td">
							<a href="<c:url value="${exDlist.ATCH3 }"/>" target="_blank" class="board_2"><c:out value="${exDlist.ATCH3 }"/></a>
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

<span class="exDlist">
	<c:set var="keySet" value="${params.dListkeySet }"/>
	<c:forEach var="key" items="${keySet }">
		<input type="hidden" name="exDlist_<c:out value="${key }"/>" value="<c:out value="${exDlist[key] }"/>"/>
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