<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		var page = "DISPLAY";

		$("#btnBack").click(function(){
			$("#expenseForm").attr("method", "post");
			$("#expenseForm").attr("action", "<c:url value='/portal/moorimess/personalMng/expenseList.do'/><c:out value='${params.searchParam}' escapeXml='false'/>");
			$("#expenseForm").submit();
		});

		$("#btnDelete").click(function(){
			$.setParam("DELETE");
		});

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
				</c:forEach>

				<fmt:parseDate var="dateString" value="${params.REQDT}" pattern="yyyyMMdd" />
				$("input[name=requestDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
				$("input[name=reqType]").val("<c:out value='${params.REQGN}'/>");
				$("input[name=areaType]").val("<c:out value='${params.ZAREA}'/>");

				<fmt:parseDate var="dateString" value="${params.ZBUDT}" pattern="yyyyMMdd" />
				$("input[name=dispatchDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");

				<fmt:parseDate var="dateString" value="${params.ZMODT}" pattern="yyyyMMdd" />
				$("input[name=moveDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");

				$("input[name=moveType]").val("<c:out value='${params.MOVTY}'/>");
				$("input[name=sleepType]").val("<c:out value='${params.SLEEP}'/>");
				$("input[name=marriageType]").val("<c:out value='${params.MARRY}'/>");

				$("input[name=amount]").val("<fmt:formatNumber value="${params.MOVMT}" groupingUsed="true"/>");
			}else{
				$("input[name=requestDate]").val("<c:out value="${params.toDay}"/>");
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
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"paDisabled\" value=\"TRUE\"/>");

			$.setInputParameter();

			$.callSaveRequest();
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
		<c:if test="${params.exFlag eq 'D'}">
			<a href="#" class="button_img01" id="btnDelete"><span>삭제</span></a>
		</c:if>
	</div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">
			결재라인
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
						<fmt:parseDate var="dateString" value="${params.REQDT}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						<input type="hidden" name="requestDate" class="parameter" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">신청 구분</th>
					<td class="list03_td">
						<c:forEach var="result" items="${reqTypeList}">
							<c:if test="${result.KEY eq  params.REQGN}">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="reqType" class="parameter" value=""/>
					</td>
					<th class="list03_td_bg">국/내외 구분</th>
					<td class="list03_td">
						<c:forEach var="result" items="${areaTypeList}">
							<c:if test="${result.KEY eq  params.ZAREA}">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="areaType" class="parameter" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">부임/파견일자</th>
					<td class="list03_td">
						<fmt:parseDate var="dateString" value="${params.ZBUDT}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						<input type="hidden" name="dispatchDate" class="parameter" value=""/>
					</td>
					<th class="list03_td_bg">이사일자</th>
					<td class="list03_td">
						<fmt:parseDate var="dateString" value="${params.ZMODT}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						<input type="hidden" name="moveDate" class="parameter" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">이동유형</th>
					<td class="list03_td">
						<c:forEach var="result" items="${moveTypeList}">
							<c:if test="${result.KEY eq  params.MOVTY}">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="moveType" class="parameter" value=""/>
					</td>
					<th class="list03_td_bg">숙소제공</th>
					<td class="list03_td">
						<c:forEach var="result" items="${sleepTypeList}">
							<c:if test="${result.KEY eq  params.SLEEP}">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="sleepType" class="parameter" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">미혼/기혼</th>
					<td class="list03_td" colspan="3">
						<c:forEach var="result" items="${marriageTypeList}">
							<c:if test="${result.KEY eq  params.MARRY}">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="marriageType" class="parameter" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">금액</th>
					<td class="list03_td">
						<fmt:formatNumber value="${params.MOVMT}" groupingUsed="true"/>
						<input type="hidden" name="amount" class="parameter" value=""/>
					</td>
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
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>