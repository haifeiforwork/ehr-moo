<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preHeader"  value="ui.lightpack.board.boardItem.readBoardView.header" /> 
<c:set var="preDetail"  value="ui.lightpack.board.boardItem.readBoardView.detailBoardItem" />
<c:set var="preList"    value="ui.lightpack.board.boardItem.listBoardView.list" />
<c:set var="preButton"  value="ui.lightpack.common.button" /> 
<c:set var="preMessage" value="message.lightpack.common.boardItem" /> 
<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>  
<%-- 메시지 관련 Prefix 선언 End --%>
<c:set var="portal" value="${sessionScope['ikep.portal']}" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<style type="text/css">
<!--
   td {border:1px black solid !important; font-size: 12pt; font-family: 굴림 !important; line-height:130%;}
   A:link    {text-decoration: none; color: #385174; font-family: 굴림;}
   A:visited {text-decoration: none; color: #385174; font-family: 굴림;}
   A:active  {text-decoration: none; color: #385174; font-family: 굴림;}
   A:hover   {text-decoration: underline; color: #385174; font-family: 굴림;}
-->
</style>

<script type="text/javascript">
<!--
(function($){	 
	$(document).ready(function() {
		iKEP.setContentImageRendering("#boardItemContent");
	});
})(jQuery); 
//-->

</script>

<!--popup Start-->

		<table width="98%" border="1" cellpadding="3" cellspacing="0" bordercolor="#000000" style="border-collapse:collapse;margin-left:margin-right:5px;">		
		<tr height="30">
			<td  width="80" align="center">작성일자</td>
			<td style="padding-left:5px" width="250"><span ><ikep4j:timezone date="${boardItem.registDate}" /></span></td>
			<td  width="80" align="center">처리번호</td>
			<td style="padding-left:5px" width="250"><span >${boardItem.itemSeqId}</span></td>
		</tr>
		<%-- <tr height="30">
			<td width="80" align="center">작성자</td>
			<td style="padding-left:5px" width="250"><span >${boardItem.registerName}</span></td>
			<td width="80" align="center">사번</td>
			<td style="padding-left:5px" width="250"><span >${boardItem.empNo}</span></td>
		</tr>
		<tr height="30">
			<td width="80" align="center">사업장</td>
			<td style="padding-left:5px" width="250"><span >${boardItem.workPlaceName}</span></td>
			<td  width="80" align="center">부서</td>
			<td style="padding-left:5px" width="250"><span >${boardItem.groupName}</span></td>
		</tr> --%>
		<c:if test="${isSystemAdmin}">
		<tr height="30">
			<td width="80" align="center">작성자</td>
			<td style="padding-left:5px" width="250"><span >${boardItem.registerName}</span></td>
			<td  width="80" align="center">부서</td>
			<td style="padding-left:5px" width="250"><span >${boardItem.groupName}</span></td>
		</tr>
		</c:if>
		<tr height="30">
			<td width="80" align="center">제목</td>
			<td style="padding-left:5px" width="580" colspan="3"><span >${boardItem.title}</span></td>
		</tr>
		<tr height="30">
			<td width="80" align="center">출처</td>
			<td style="padding-left:5px" width="580" colspan="3"><span >${boardItem.targetSource}&nbsp;</span></td>
		</tr>
		<tr>
			<td width="80" align="center">내용</td>
			<td style="padding-left:5px" colspan="3" valign="top">
				<div style="width:600px;display:block;" id="boardItemContent">
		        <spring:htmlEscape defaultHtmlEscape="false"> 
					<spring:bind path="boardItem.contents">
						<p >&nbsp;${status.value}</p>
					</spring:bind> 
		        </spring:htmlEscape> 
		        </div>
			</td>
		</tr>
		<c:if test="${boardItem.attachFileCount > 0}">
		<c:forEach var="fileData" items="${boardItem.fileDataList}" varStatus="fileDataLoop">
		<tr height="30">
			<td width="80" align="center" >첨부파일</td>
			<td style="padding-left:5px" width="580" colspan="3">
			 	<span><a href="#">${fileData.fileRealName}</a>&nbsp;</span>
			</td>
		</tr>
		</c:forEach>
		</c:if>
		<tr height="30">
			<td width="80" align="center">분류</td>
			<td style="padding-left:5px" width="580" colspan="3">
				<span>${kmsMapName}</span>
			</td>
		</tr>
		<tr height="30">
			<td width="80" align="center" >정보등급</td>
			<td style="padding-left:5px" width="250" >
				<span>
				<c:if test='${boardItem.infoGrade == "C"}'>공유</c:if><c:if test='${boardItem.infoGrade == "A"}'>보안</c:if><c:if test='${boardItem.infoGrade == "B"}'>보안</c:if>
				</span>
			</td>
			<td width="80" align="center" >&nbsp;조회수</td>
			<td style="padding-left:5px" width="250" ><span >${boardItem.hitCount}</span></td>	
		</tr>
		</table>
				
		<!---- 활용정보 시작--->
		<c:if test="${searchCondition.recordCount gt 0}">
		  <table width="98%" border="1" cellpadding="3" cellspacing="0" bordercolor="#000000" style="border-collapse:collapse;margin-top:5px;margin-left:5px;margin-right:5px;">
		   	<tr height="30">
				<td width="100%" colspan="4">
					<div style="width:100%;"><span><b>활용정보</b></span></div>
				</td>
			</tr>
			<c:forEach var="relatedBoardItem" items="${searchResult.entity}">
			<tr height="30">
				<td width="80" align="center">작성일자</td>
				<td style="padding-left:5px" width="250"><span ><ikep4j:timezone date="${relatedBoardItem.registDate}" pattern="yyyy.MM.dd" /></span></td>
				<td width="80" align="center">처리번호</td>
				<td style="padding-left:5px" width="250"><span >${relatedBoardItem.itemSeqId}</span></td>
			</tr>
			<tr height="30">
				<td width="80" align="center">제목</td>
				<td style="padding-left:5px" width="250"><span >${relatedBoardItem.title}</span></td>
				<td width="80" align="center">출처</td>
				<td style="padding-left:5px" width="250"><span >${relatedBoardItem.targetSource}</span></td>
			</tr>
			</c:forEach>
		  </table>
		</c:if>	


<!--//popup End-->

<!-- MeadCo ScriptX --> 
<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814"
        codebase="<c:url value="/Bin/smsx.cab#Version=6,5,439,72"/>">
</object>

<script language="JavaScript">
window.onload = function()
{
	if(confirm("본 정보는 당사 보안정보로 외부로의 유출을 금합니다.")){
		printWindow();
	}else{
		window.close();
	}
	
}

function getTimeStamp() {
	 var d = new Date();
	 
	  var s =
	  leadingZeros(d.getFullYear(), 4) + '-' +
	  leadingZeros(d.getMonth() + 1, 2) + '-' +
	  leadingZeros(d.getDate(), 2) + ' ' +
	  
	  leadingZeros(d.getHours(), 2) + ':' +
	  leadingZeros(d.getMinutes(), 2) + ':' +
	  leadingZeros(d.getSeconds(), 2);
	 return s;
	}

	function leadingZeros(n, digits) {
	 var zero = '';
	 n = n.toString();
	 
	 if (n.length < digits) {
	  for (i = 0; i < digits - n.length; i++)
	  zero += '0';
	 }
	 return zero + n;
	}

	
function printWindow() 
{
	//factory.printing.paperSize = "A4"
    factory.printing.header = "" //머릿글
	factory.printing.footer = getTimeStamp()+" ${user.teamName} ${user.userName}" //바닥글
	factory.printing.portrait = true //true는 세로 출력, false는 가로 출력
	factory.printing.leftMargin = 5.33 //왼쪽 여백
	factory.printing.topMargin = 22.23 //위쪽 여백
	factory.printing.rightMargin = 5.33 //오른쪽 여백
	factory.printing.bottomMargin = 4.23 //바닥 여백	
	
	//factory.printing.Print(true, window) //true는 표시함, false는 프린트 대화 상자표지 안함, window는 전체 페이지 출력
	factory.printing.Preview();
	//setTimeout("window.close()", 100);
	setTimeout("javascript:iKEP.closePop()", 100);

}		

      //window.print();
</script>