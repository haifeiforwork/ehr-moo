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

<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814"
        codebase="<c:url value="/Bin/smsx.cab#Version=6,5,439,72"/>">
</object>
<script type="text/javascript">
<!--  

window.onload = function()
{
	printWindow();
}

function printWindow() 
{
	//factory.printing.paperSize = "A4"
    factory.printing.header = "" //머릿글
	factory.printing.footer = "" //바닥글
	factory.printing.portrait = true //true는 세로 출력, false는 가로 출력
	factory.printing.leftMargin = 4.23 //왼쪽 여백
	factory.printing.topMargin = 4.23 //위쪽 여백
	factory.printing.rightMargin = 4.23 //오른쪽 여백
	factory.printing.bottomMargin = 4.23 //바닥 여백	
	
	//factory.printing.Print(true, window) //true는 표시함, false는 프린트 대화 상자표지 안함, window는 전체 페이지 출력
	factory.printing.Preview();
	//setTimeout("window.close()", 100);
	setTimeout("javascript:iKEP.closePop()", 100);

}		
		
(function($){	 
	
	
	$(document).ready(function() { 
		
	});    

})(jQuery); 


//-->
</script> 
<div <c:if test="${!empty boardItem.imageFileId}">style="text-align:center;"</c:if>>
	<ikep4j:extractTextBody text="${boardItem.contents}"/>
</div>

