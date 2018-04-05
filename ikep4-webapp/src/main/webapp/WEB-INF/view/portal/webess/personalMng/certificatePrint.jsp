<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
	});
	
	$(window).load(function () {
		window.print();
		opener.printComplete();
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
	
})(jQuery);
</script>
<form id="" name="" method="post" action="" >
<div id="wrap" style="width:100%;">
	<div class="list06" style="width:100%;">
	<table border="0" cellpadding="0" cellspacing="0" id="blktable" style="width:97%;height: 100%;font-family: 굴림;">
		<tr style="height: 50px;">
			<td colspan="4" style="text-align:left;vertical-align: top;border-bottom: 0; font-size: 17px;font-weight: bold;"><br>&nbsp;&nbsp;&nbsp;<c:out value="${EX_CERTI1.DOC_NO}"/></td>
		</tr>
		<tr style="height: 200px;">
			<td colspan="4" style="font-size: 38px;font-weight: bold;"><u>재 직 증 명 서<br><br><br></u></td>
		</tr>
		<tr style="height: 60px;">
			<td width="20%" style="border-top: 0; font-size: 17px;font-weight: bold;background-color: #D5D5D5;">성명</td>
			<td width="30%" style="font-size: 17px;font-weight: bold;"><c:out value="${EX_CERTI1.ENAME}"/></td>
			<td width="20%" style="border-top: 0; font-size: 17px;font-weight: bold;background-color: #D5D5D5;">주민등록번호</td>
			<td width="30%" style="font-size: 17px;font-weight: bold;"><c:out value="${EX_CERTI1.REGNO}"/></td>
		</tr>
		<tr style="height: 60px;">
			<td style="border-top: 0; font-size: 17px;font-weight: bold;background-color: #D5D5D5;">근무기간</td>
			<td colspan="3" style="font-size: 17px;font-weight: bold;"><c:out value="${EX_CERTI1.PERIOD}"/></td>
		</tr>
		<tr style="height: 60px;">
			<td style="border-top: 0; font-size: 17px;font-weight: bold;background-color: #D5D5D5;">근무부서</td>
			<td style="font-size: 17px;font-weight: bold;"><c:out value="${EX_CERTI1.ORGTX}"/></td>
			<td style="border-top: 0; font-size: 17px;font-weight: bold;background-color: #D5D5D5;">직급/직위</td>
			<td style="font-size: 17px;font-weight: bold;"><c:out value="${EX_CERTI1.JIK}"/></td>
		</tr>
		<tr style="height: 60px;">
			<td style="border-top: 0; font-size: 17px;font-weight: bold;background-color: #D5D5D5;">용도</td>
			<td style="font-size: 17px;font-weight: bold;"><c:out value="${EX_CERTI1.PURPS}"/></td>
			<td style="border-top: 0; font-size: 17px;font-weight: bold;background-color: #D5D5D5;">담당</td>
			<td style="font-size: 17px;font-weight: bold;"><c:out value="${EX_CERTI1.DPPNM}"/></td>
		</tr>
		<tr style="height: 100px;">
			<td colspan="4" style="border-bottom: 0;font-weight: bold; font-size: 17px;"><br><br><br><br>위 내용이 틀림없음을 증명함</td>
		</tr>
		<tr style="height: 80px;">
			<td colspan="4" style="border-bottom: 0; font-size: 17px;"><br><br><c:out value="${EX_CERTI1.ZDATE}"/></td>
		</tr>
		<tr style="height: 125px;">
			<td colspan="4" style="border-bottom: 0; font-size: 17px;"><br><br><br><br><br><c:out value="${EX_CERTI1.ADDRESS}"/></td>
		</tr>
		<tr style="height: 40px;">
			<td colspan="4" style="border-bottom: 0;font-size: 30px;font-weight: bold;"><br><c:out value="${EX_CERTI1.NAME}"/></td>
		</tr>
		<tr style="height: 150px;">
			<td colspan="4" style="vertical-align: top;">
			<span style="position:absolute;padding-top: 41px;width:97%; font-size: 17px;">
			대&nbsp;&nbsp;&nbsp;표&nbsp;&nbsp;&nbsp;이&nbsp;&nbsp;&nbsp;사&nbsp;&nbsp;&nbsp;&nbsp;김석만&nbsp;&nbsp;&nbsp;&nbsp;(印)
			<%-- <c:out value="${EX_CERTI1.KR_REPRES}"/> --%>
			</span>
			<span style="position:absolute;z-index:99;vertical-align: top;width:97%; padding-left: 110px;">
			<c:choose>
				<c:when test="${IM_WERKS=='P100'}"><img src="<c:url value="/base/images/common/stamp_p100.png"/>" alt=""  width="98" height="98"/></c:when>
				<c:when test="${IM_WERKS=='M100'}"><img src="<c:url value="/base/images/common/stamp_m100.png"/>" alt=""  width="98" height="98"/></c:when>
				<c:when test="${IM_WERKS=='S100'}"><img src="<c:url value="/base/images/common/stamp_s100.png"/>" alt=""  width="98" height="98"/></c:when>
				<c:otherwise></c:otherwise>
			</c:choose>
			</span>
			</td>
		</tr>
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
	//printWindow();
	//opener.printComplete();
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
	factory.printing.portrait = true //true는 세로 출력, false는 가로 출력
	factory.printing.leftMargin = 5.33 //왼쪽 여백
	factory.printing.topMargin = 15.23 //위쪽 여백
	factory.printing.rightMargin = 5.33 //오른쪽 여백
	factory.printing.bottomMargin = 15.23 //바닥 여백	
	
	//factory.printing.Print(true, window) //true는 표시함, false는 프린트 대화 상자표지 안함, window는 전체 페이지 출력
	factory.printing.Preview();
	//setTimeout("window.close()", 100);
	setTimeout("javascript:iKEP.closePop()", 100);

}	
</script>