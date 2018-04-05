<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="yearList" value="${resultMap.ET_YEAR }"/>
<c:set var="teamList" value="${resultMap.ET_TEAM }"/>

<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		$("select[name=imYear] option[value=<c:out value='${params.imYear}'/>]").attr("selected", "selected");
		$("input[name=viewType][value=<c:out value='${params.viewType}'/>]").attr("checked", true);
		
		$("input[name=imEname]").val("<c:out value="${params.imEname}"/>");
		$("input[name=selEmpNo]").val("<c:out value="${params.selEmpNo}"/>");
		
		<c:choose>
			<c:when test="${fn:length(teamList) == 1 }">
				$("span.imTeam").html("<c:out value='${teamList[0].VALUE}'/>");
				$("input[name=imTeam]").val("<c:out value='${teamList[0].KEY}'/>");
			</c:when>
			<c:otherwise>
				$("select[name=imTeam] option[value=<c:out value='${params.imTeam}'/>]").attr("selected", "selected");
			</c:otherwise>
		</c:choose>
		
		$("#searchButton, input[name=viewType]").click(function() {
			$.callProgress();
			$("#holidayUseForm").submit();
		});
		
		$("select[name=imTeam]").change(function(){
			$.callProgress();
			$("#holidayUseForm").submit();
		});
	});
})(jQuery);;
</script>
<style type="text/css">
.cursor-pointer{cursor:pointer;}
.week-sum{ background-color:#FFDFDC !important;}
</style>
<form id="holidayUseForm" name="holidayUseForm" method="post" action="<c:url value='/portal/moorimmss/diligencePayMng/holidayUseList.do'/>" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>휴가사용현황 조회</h1>
	
	
	
	<div class="search_box">
		<table border="0">
			<tbody>
				<tr>
					<td>
						<label for="A"><input type="radio" name="viewType" value="A" id="A"/>&nbsp;연월차</label>&nbsp;&nbsp;&nbsp;
						<label for="B"><input type="radio" name="viewType" value="B" id="B"/>&nbsp;건강휴가</label>&nbsp;&nbsp;&nbsp;
						<label for="C"><input type="radio" name="viewType" value="C" id="C"/>&nbsp;예비군/민방위 훈련</label>&nbsp;&nbsp;&nbsp;
						<label for="D"><input type="radio" name="viewType" value="D" id="D"/>&nbsp;생일.결혼기념일/이사/생리 휴가</label>&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</tbody>
		</table>
		<div class="search_delimiter"></div>
		<table border="0">
			<tbody>
				<tr>
					<td width="50px" class="f_right"><span class="f_333">조회연도</span></td>
					<td>
						<select name="imYear" id="imYear">
							<c:forEach var="result" items="${yearList }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="search_delimiter"></div>
		<table border="0">
			<tbody>
				<tr>
					<td width="50px" class="f_right"><span class="f_333">성명</span></td>
					<td>
						<input type="text" name="imEname" value="" class="input"/>
					</td>
					<td>
						<a href="#" class="button_img06" id="searchButton"><span>조회</span></a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div class="button_box"></div>
	
	<div class="table_box">
		<div class="search_box">
			<table border="0">
				<tbody>
					<tr>
						<td><span class="f_333">소속팀</span></td>
						<td>
							<c:choose>
								<c:when test="${fn:length(teamList) == 1 }">
									<span class="imTeam"></span>
									<input type="hidden" name="imTeam" value=""/>
								</c:when>
								<c:otherwise>
									<select name="imTeam" id="imTeam" class="w150px">
										<c:forEach var="result" items="${teamList }">
											<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
										</c:forEach>
									</select>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<!-- 상단버튼-->
		<div class="button_box"></div>
		
		<c:choose>
			<c:when test="${resultMap.EX_BTRTL eq 'P200' }">
				<c:set var="etList" value="${resultMap.ET_LIST_1 }"/>
			</c:when>
			<c:otherwise>
				<c:set var="etList" value="${resultMap.ET_LIST }"/>
			</c:otherwise>
		</c:choose>
		
		<div class="list01">
			<c:choose>
				<c:when test="${params.viewType eq 'A' && resultMap.EX_BTRTL eq 'P200'}">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="100px">사번</th>
								<th scope="col" width="100px">이름</th>
								<th scope="col" width="11%">사용 연차휴가</th>
								<th scope="col" width="11%">보상 연차</th>
								<th scope="col" width="11%">잔여 연차휴가</th>
								<th scope="col" width="11%">사용 월차휴가</th>
								<th scope="col" width="11%">보상 월차</th>
								<th scope="col" width="11%">잔여 월차휴가</th>
								<th scope="col" width="*">잔여 사용가능휴가</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!empty etList }">
									<c:forEach var="result" items="${etList}">
										<tr>
											<td><c:out value="${result.PERNR}"/></td>
											<td><c:out value="${result.ENAME}"/></td>
											<td class="week-sum"><c:out value="${result.QY_USE}"/></td>
											<td><c:out value="${result.QY_COM}"/></td>
											<td><c:out value="${result.QY_REM}"/></td>
											<td><c:out value="${result.QM_USE}"/></td>
											<td><c:out value="${result.QM_COM}"/></td>
											<td><c:out value="${result.QM_REM}"/></td>
											<td><c:out value="${result.Q_REM}"/></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="9">해당 조회 내역이 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						 </tbody>
					</table>
				</c:when>
				
				<c:when test="${params.viewType eq 'A' && resultMap.EX_BTRTL ne 'P200'}">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="100px">사번</th>
								<th scope="col" width="100px">이름</th>
								<th scope="col" width="13%">적치 휴가</th>
								<th scope="col" width="13%">사용 연차휴가</th>
								<th scope="col" width="13%">잔여 연차휴가</th>
								<th scope="col" width="13%">사용 월차휴가</th>
								<th scope="col" width="13%">잔여 월차휴가</th>
								<th scope="col" width="*">잔여 사용가능휴가</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!empty etList }">
									<c:forEach var="result" items="${etList}">
										<tr>
											<td><c:out value="${result.PERNR}"/></td>
											<td><c:out value="${result.ENAME}"/></td>
											<td><c:out value="${result.Q_QUE}"/></td>
											<td class="week-sum"><c:out value="${result.QY_USE}"/></td>
											<td><c:out value="${result.QY_REM}"/></td>
											<td><c:out value="${result.QM_USE}"/></td>
											<td><c:out value="${result.QM_REM}"/></td>
											<td><c:out value="${result.Q_REM}"/></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="8">해당 조회 내역이 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						 </tbody>
					</table>
				</c:when>
				
				<c:when test="${params.viewType eq 'B' }">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="100px">사번</th>
								<th scope="col" width="100px">이름</th>
								<th scope="col" width="40%">사용 건강휴가</th>
								<th scope="col" width="*">잔여 건강휴가</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!empty etList }">
									<c:forEach var="result" items="${etList}">
										<tr>
											<td><c:out value="${result.PERNR}"/></td>
											<td><c:out value="${result.ENAME}"/></td>
											<td><c:out value="${result.QB_USE}"/></td>
											<td><c:out value="${result.QB_REM}"/></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="4">해당 조회 내역이 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						 </tbody>
					</table>
				</c:when>
				
				<c:when test="${params.viewType eq 'C' }">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="100px">사번</th>
								<th scope="col" width="100px">이름</th>
								<th scope="col" width="40%">사용 예비군훈련</th>
								<th scope="col" width="*">사용 민방위훈련</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!empty etList }">
									<c:forEach var="result" items="${etList}">
										<tr>
											<td><c:out value="${result.PERNR}"/></td>
											<td><c:out value="${result.ENAME}"/></td>
											<td><c:out value="${result.QG_USE}"/></td>
											<td><c:out value="${result.QH_USE}"/></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="4">해당 조회 내역이 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						 </tbody>
					</table>
				</c:when>
				
				<c:otherwise>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="100px">사번</th>
								<th scope="col" width="100px">이름</th>
								<th scope="col" width="30%">사용 생일.결혼기념일</th>
								<th scope="col" width="30%">사용 이사휴가</th>
								<th scope="col" width="*">사용 생리휴가</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!empty etList }">
									<c:forEach var="result" items="${etList}">
										<tr>
											<td><c:out value="${result.PERNR}"/></td>
											<td><c:out value="${result.ENAME}"/></td>
											<td><c:out value="${result.QK_USE}"/></td>
											<td><c:out value="${result.QE_USE}"/></td>
											<td><c:out value="${result.QS_USE}"/></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="5">해당 조회 내역이 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						 </tbody>
					</table>
				</c:otherwise>
			</c:choose>
		
			
		</div>
	</div>
	
	<input type="hidden" name="selEmpNo" value=""/>
	<input type="hidden" name="imFirst" value="X"/>
</div>
</form>
<form id="popupForm" name="popupForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>
