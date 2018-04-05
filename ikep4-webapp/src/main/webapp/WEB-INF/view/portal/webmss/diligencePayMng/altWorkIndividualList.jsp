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
			$("#altWorkIndividualForm").submit();
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
			$("#ajaxForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/altWorkIndividualView.do'/>");
			$("#ajaxForm").submit();
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
			$("#ajaxForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/altWorkIndividualView.do'/>");
			$("#ajaxForm").submit();
		};
		
		$.setCommonData = function(){
			<c:forEach items="${resultMap.ET_BANWON}" var="result">
			$("#ajaxForm").append("<input type='hidden' name='etBanwonKey' value='<c:out value="${result.KEY}"/>'/>");
			$("#ajaxForm").append("<input type='hidden' name='etBanwonValue' value='<c:out value="${result.VALUE}"/>'/>");
			</c:forEach>
			
			$("#ajaxForm").append("<input type='hidden' name='etBanwonCnt' value='<c:out value="${fn:length(resultMap.ET_BANWON)}"/>'/>");
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
<form id="altWorkIndividualForm" name="altWorkIndividualForm" method="post" action="<c:url value='/portal/moorimmss/diligencePayMng/altWorkIndividualList.do'/>" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>대체근무 개별입력</h1>
	
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
	</div>
	
	<c:set var="etList" value="${resultMap.ET_LIST }"/>

	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="blktable">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="35px"></th>
					<th scope="col" width="*%">부서명</th>
					<th scope="col" width="10%">직급</th>
					<th scope="col" width="90px">이름</th>
					<th scope="col" width="90px">근무시작일</th>
					
					<th scope="col" width="90px">근무종료일</th>
					<th scope="col" width="10%">근무유형</th>
					<th scope="col" width="15%">근무일정규칙</th>
					<th scope="col" width="10%">일일근무일정</th>
					<th scope="col" width="10%">대체근무시간</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty etList}">
						<tr>
							<td colspan="10">해당 데이터가 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList}">
							<tr>
								<td><input type="radio" name="flag" value=""/></td>
								<td><a href="#" class="board_2" name="linkField"><c:out value="${result.ORGTX}"/></a></td>
								<td><c:out value="${result.JIKWI}"/></td>
								<td><c:out value="${result.ENAME}"/></td>
								<td>
									<fmt:parseDate var="dateString" value="${result.BEGDA}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
								
								<td>
									<fmt:parseDate var="dateString" value="${result.ENDDA}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
								<td><c:out value="${result.VTEXT}"/></td>
								<td><c:out value="${result.RTEXT}"/></td>
								<td><c:out value="${result.TTEXT}"/></td>
								<td>
									<c:out value="${result.STDAZ}"/>
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