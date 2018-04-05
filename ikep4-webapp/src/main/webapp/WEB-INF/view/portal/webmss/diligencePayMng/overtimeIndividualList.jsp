<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		$("input[name=startDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("input[name=endDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
		$("#searchButton").click(function() {
			$.callProgress();
			$("#overtimeIndividualForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/overtimeIndividualList.do'/>");
			$("#overtimeIndividualForm").attr("target", "");
			$("#overtimeIndividualForm").submit();
		});
		
		$("#btnWrite").click(function(){
			$.setParam("CRE");
		});
		
		$("#btnCopy").click(function(){
			$.setParam("COP");
		});
		
		$("#btnModify").click(function(){
			$.setParam("CHA");
		});
		
		$("#btnDelete").click(function(){
			$.setParam("DEL");
		});
		
		$("a[name=linkField]").click(function(){
			var tr = $(this).parents("tr");
			var allTr = $("#blktable").find("tbody").find("tr");
			var index = Number(allTr.index(tr));
			var rowInfo = $("span.rowInfo").eq(index).html();
			
			$("#ajaxForm").html("");
			$("#ajaxForm").html(rowInfo);
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imFlag\" value=\"DIS\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imZeity\" value=\"<c:out value="${resultMap.EX_ZEITY}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imMosid\" value=\"<c:out value="${resultMap.EX_MOSID}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imMofid\" value=\"<c:out value="${resultMap.EX_MOFID}"/>\"/>");
			
			$("#ajaxForm").append("<input type='hidden' name='searchParam' value='?startDate=<c:out value='${resultMap.EX_BEGDA}'/>&endDate=<c:out value='${resultMap.EX_ENDDA}'/>&imFirst=X&imPernr=<c:out value='${params.imPernr}'/>'/>");
			
			$.setCommonData();
			
			$("#ajaxForm").attr("method", "post");
			$("#ajaxForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/overtimeIndividualView.do'/>");
			$("#ajaxForm").submit();
		});
		
		$("#btnPrint").click(function(){
			
			var target = "printPopup";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=750px, height=500px, top=200px, left=300px, resizable=no";

			var popup = window.open("", target, param);
			$("#overtimeIndividualForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/overtimeIndividualListPopup.do?imFirst=X'/>");
			$("#overtimeIndividualForm").attr("target", target);
			$("#overtimeIndividualForm").submit();
			
			popup.focus();
		});
		
		$.setParam = function(imFlag){
			
			if(imFlag == "COP" || imFlag == "CHA" || imFlag == "DEL"){
				if($("input:radio[name=flag]:checked").length == 0){
					alert("해당 라인을 선택해 주세요.");
					return;
				}
			}
			
			var index = $("input:radio[name=flag]").index($("input:radio[name=flag]:checked"));
			
			$("#ajaxForm").html("");
			
			if(index > -1){
				$("#ajaxForm").html($("span.rowInfo").eq(index).html());
			}
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imFlag\" value=\""+imFlag+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imZeity\" value=\"<c:out value="${resultMap.EX_ZEITY}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imMosid\" value=\"<c:out value="${resultMap.EX_MOSID}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imMofid\" value=\"<c:out value="${resultMap.EX_MOFID}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imPernr\" value=\"<c:out value="${params.imPernr}"/>\"/>");
			
			$("#ajaxForm").append("<input type='hidden' name='searchParam' value='?startDate=<c:out value='${resultMap.EX_BEGDA}'/>&endDate=<c:out value='${resultMap.EX_ENDDA}'/>&imFirst=X&imPernr=<c:out value='${params.imPernr}'/>'/>");
			
			$.setCommonData();
			
			$("#ajaxForm").attr("method", "post");
			$("#ajaxForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/overtimeIndividualView.do'/>");
			$("#ajaxForm").submit();
		};
		
		$.setCommonData = function(){
			<c:forEach items="${resultMap.ET_BANWON}" var="result">
			$("#ajaxForm").append("<input type='hidden' name='etBanwonKey' value='<c:out value="${result.KEY}"/>'/>");
			$("#ajaxForm").append("<input type='hidden' name='etBanwonValue' value='<c:out value="${result.VALUE}"/>'/>");
			</c:forEach>
			
			$("#ajaxForm").append("<input type='hidden' name='etBanwonCnt' value='<c:out value="${fn:length(resultMap.ET_BANWON)}"/>'/>");
			
			<c:forEach items="${resultMap.ET_VERSL}" var="result">
			$("#ajaxForm").append("<input type='hidden' name='etVerslKey' value='<c:out value="${result.KEY}"/>'/>");
			$("#ajaxForm").append("<input type='hidden' name='etVerslValue' value='<c:out value="${result.VALUE}"/>'/>");
			</c:forEach>
			
			$("#ajaxForm").append("<input type='hidden' name='etVerslCnt' value='<c:out value="${fn:length(resultMap.ET_VERSL)}"/>'/>");
			
			<c:forEach items="${resultMap.ET_PREAS}" var="result">
			$("#ajaxForm").append("<input type='hidden' name='etPreasKey' value='<c:out value="${result.KEY}"/>'/>");
			$("#ajaxForm").append("<input type='hidden' name='etPreasValue' value='<c:out value="${result.VALUE}"/>'/>");
			</c:forEach>
			
			$("#ajaxForm").append("<input type='hidden' name='etPreasCnt' value='<c:out value="${fn:length(resultMap.ET_PREAS)}"/>'/>");
		};
		
		$.initSet = function(){
			<fmt:parseDate var="dateString" value="${resultMap.EX_BEGDA}" pattern="yyyyMMdd" />
			$("input[name=startDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
			$("span.startDate").html("(<c:out value="${resultMap.EX_BEGWK}"/>)");
			
			<fmt:parseDate var="dateString" value="${resultMap.EX_ENDDA}" pattern="yyyyMMdd" />
			$("input[name=endDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
			$("span.endDate").html("(<c:out value="${resultMap.EX_ENDWK}"/>)");
			
			$("select[name=imPernr] option[value=<c:out value="${params.imPernr}"/>]").attr("selected", "selected");
		};
		
		$.initSet();
	});
})(jQuery);;
</script>
<form id="overtimeIndividualForm" name="overtimeIndividualForm" method="post" action="<c:url value='/portal/moorimmss/diligencePayMng/overtimeIndividualList.do'/>" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>연장근로 개별입력</h1>
	
	<c:set var="etBanwon" value="${resultMap.ET_BANWON }"/>
	
	<div class="search_box">
		<table border="0">
			<tbody>
				<tr>
					<td width="50px"><span class="f_333">조회기간</span></td>
					<td>
						<input name="startDate" id="startDate" class="input datepicker" type="text" value="" readonly="readonly" />
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
						<span class="startDate"></span>
						&nbsp;~&nbsp;
						<input name="endDate" id="endDate" class="input datepicker" type="text" value="" readonly="readonly" />
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
						<span class="endDate"></span>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="search_delimiter"></div>
		<table border="0">
			<tbody>
				<tr>
					<td width="50px"><span class="f_333">성명</span></td>
					<td>
						<select name="imPernr" id="imPernr">
							<c:forEach var="result" items="${etBanwon }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<a href="#" class="button_img06" id="searchButton"><span>조회</span></a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnWrite"><span>작성</span></a>
		<a href="#" class="button_img01" id="btnCopy"><span>복사</span></a>
		<a href="#" class="button_img01" id="btnModify"><span>수정</span></a>
		<a href="#" class="button_img01" id="btnDelete"><span>삭제</span></a>
		<a href="#" class="button_img01" id="btnPrint"><span>인쇄</span></a>
	</div>

	<c:set var="etList" value="${resultMap.ET_LIST }"/>

	<div class="list01" style="overflow-x:scroll;">
		<table border="0" cellpadding="0" cellspacing="0" id="blktable" style="width:1250px;">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="35px"></th>
					<th scope="col" width="*%">부서명</th>
					<th scope="col" width="90px">직급</th>
					<th scope="col" width="90px">이름</th>
					<th scope="col" width="90px">근무일정</th>
					
					<th scope="col" width="90px">일일근무명</th>
					<th scope="col" width="90px">근무상태</th>
					<th scope="col" width="90px">연장시작일</th>
					<th scope="col" width="90px">연장종료일</th>
					<th scope="col" width="90px">시간</th>
					
					<th scope="col" width="90px">시작</th>
					<th scope="col" width="90px">종료</th>
					<th scope="col" width="90px">보상유형</th>
					<th scope="col" width="90px">잔업사유</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty etList}">
						<tr>
							<td colspan="14">해당 데이터가 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList}">
							<tr>
								<td><input type="radio" name="flag" value=""/></td>
								<td><a href="#" class="board_2" name="linkField"><c:out value="${result.ORGTX}"/></a></td>
								<td><c:out value="${result.JIKWI}"/></td>
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