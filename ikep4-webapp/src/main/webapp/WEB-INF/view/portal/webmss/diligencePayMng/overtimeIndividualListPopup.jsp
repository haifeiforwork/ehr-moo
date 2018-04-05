<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
	});
	
	$(window).load(function () {
		//window.print();
	});
	
	$.rowspan = function(tdClass){
		
		var $td = $(tdClass);
		var length = $td.length;
		var rowspan = 1;
		var curText;
		
		for(var i = 0 ; i < length ; i++){
			curText = $td.eq(i).text();
			for(var j = i + 1 ; j < length ; j++){
				if(curText == $td.eq(j).text()){
					rowspan++;	
					$td.eq(j).hide();
				}else{
					break;
				}
			}
			$td.eq(i).attr("rowspan", rowspan);
			i = j - 1;
			rowspan = 1;
		}
	};
	
})(jQuery);;
</script>
<form id="overtimeIndividualForm" name="overtimeIndividualForm" method="post" action="<c:url value='/portal/moorimmss/diligencePayMng/overtimeIndividualList.do'/>" >
<div id="wrap">
	
	<c:set var="etBanwon" value="${resultMap.ET_BANWON }"/>
	
	<c:set var="etList" value="${resultMap.ET_LIST }"/>
	<div style="float: right;" >
		<div class="list01"  >
			<table border="0" cellpadding="0" cellspacing="0" style="width:330px; height: 130px;">
				<tr>
					<th rowspan="2" width="30px" height="130px">결<br><br>재</th>
					<th width="100px" >기  안</th>
					<th width="100px" >검  토</th>
					<th width="100px" >승  인</th>
				</tr>
				<tr>
					<td style="height:60px;"></td>
					<td style="height:60px;"></td>
					<td style="height:60px;"></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="list01">
		<table border="0" cellpadding="0" cellspacing="0" id="blktable" style="width:1250px;">
			<caption></caption>
			<thead>
				<tr>
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
							<td colspan="13">해당 데이터가 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList}">
							<tr>
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

<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814"
        codebase="<c:url value="/Bin/smsx.cab#Version=6,5,439,72"/>">
</object>

<script type="text/javascript">

window.onload = function()
{
	printWindow();
}

try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}

function printWindow() 
{
	//factory.printing.paperSize = "A4"
    factory.printing.header = "" //머릿글
	factory.printing.footer = "" //바닥글
	factory.printing.portrait = false //true는 세로 출력, false는 가로 출력
	factory.printing.leftMargin = 5.33 //왼쪽 여백
	factory.printing.topMargin = 4.23 //위쪽 여백
	factory.printing.rightMargin = 5.33 //오른쪽 여백
	factory.printing.bottomMargin = 4.23 //바닥 여백	
	
	//factory.printing.Print(true, window) //true는 표시함, false는 프린트 대화 상자표지 안함, window는 전체 페이지 출력
	factory.printing.Preview();
	//setTimeout("window.close()", 100);
	setTimeout("javascript:iKEP.closePop()", 100);

}	
</script>