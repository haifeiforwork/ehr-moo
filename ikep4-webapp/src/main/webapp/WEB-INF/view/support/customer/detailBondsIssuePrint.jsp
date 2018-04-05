<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>
<c:set var="prefix" value="ui.support.customer.board" />
<c:set var="preSearch" value="ui.ikep4.common.searchCondition" />
<c:set var="preButton" value="ui.lightpack.common.button" />
<c:set var="preList" value="ui.support.customer.manInfoItemList" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<%pageContext.setAttribute("crlf", "\r\n"); %>
<%pageContext.setAttribute("nbsp", " "); %>

<script type="text/javascript">
var da = (document.all) ? 1 : 0
		var pr = (window.print) ? 1 : 0
		var mac = (navigator.userAgent.indexOf("Mac") != -1);
				
		if (da && !pr && !mac) with (document) {
			writeln('<OBJECT ID="WB" WIDTH="0" HEIGHT="0" CLASSID="clsid:8856F961-340A-11D0-A96B-00C04FD705A2"></OBJECT>');
			writeln('<' + 'SCRIPT LANGUAGE="VBScript!">');
			writeln('Sub window_onunload');
			writeln(' On Error Resume Next');
			writeln(' Set WB = nothing');
			writeln('End Sub');
			writeln('Sub vbPrintPage');
			writeln(' OLECMDID_PRINT = 6');
			writeln(' OLECMDEXECOPT_DONTPROMPTUSER = 2');
			writeln(' OLECMDEXECOPT_PROMPTUSER = 1');
			writeln(' On Error Resume Next');
			writeln(' WB.ExecWB OLECMDID_PRINT, OLECMDEXECOPT_DONTPROMPTUSER');
			writeln('End Sub');
			writeln('<' + '/SCRIPT>');
		}
		
(function($){ 
	loadBoardLinereplyList = function() {  
		
		$("#blockComment").ajaxLoadStart(); 
		$("#blockComment").load('<c:url value="/support/customer/customerLinereply/listLinereplyView.do?itemId=${bondsIssueHistory.seqNum}&itemType=BI"/>', $("#boardLinereplySearchForm").serialize(), function() {
			iKEP.iFrameContentResize();
		$("#blockComment").ajaxLoadComplete();
			//$(".firstLineItem").focus();
		 
		}).error(function(event, request, settings) { alert("error"); });
	};
	
	loadBoardBondsIssueSubList = function() {  
		
		$("#blockMoreSell").ajaxLoadStart(); 
		$("#blockMoreSell").load('<c:url value="/support/customer/customerBondsIssueSub/listBondsIssueSubView.do?itemId=${bondsIssueHistory.seqNum}&itemType=BI"/>', $("#boardBondsIssueSubSearchForm").serialize(), function() {
			iKEP.iFrameContentResize();
		$("#blockMoreSell").ajaxLoadComplete();
			//$(".firstLineItem").focus();
		 
		}).error(function(event, request, settings) { alert("error"); });
	};
	
	loadBoardMoreSellList = function() {  
		
		$("#blockMoreSell").ajaxLoadStart(); 
		$("#blockMoreSell").load('<c:url value="/support/customer/customerBondsIssueSub/listBondsIssueSubView.do?itemId=${bondsIssueHistory.seqNum}&itemType=BI"/>', $("#boardBondsIssueSubSearchForm").serialize(), function() {
			iKEP.iFrameContentResize();
		$("#blockMoreSell").ajaxLoadComplete();
			//$(".firstLineItem").focus();
	
		}).error(function(event, request, settings) { alert("error"); });
	};
	
	viewPopUpProfile = function(targetUserId) { 
		var pageURL = "<c:url value='/support/profile/getProfilePopup.do' />?targetUserId=" + targetUserId;
		iKEP.popupOpen( pageURL , {width:820, height:370, callback:function(result) {} });
	};
	
$jq(document).ready(function() {
	if(window.parent.topScroll != null) {
		window.parent.topScroll(); 	
	}   
	
	//loadBoardLinereplyList();
	//loadBoardMoreSellList();
	
	printWindow();
	$("#createPrintButton").click(function() {			
		/*
		if (pr) // NS4, IE5
			window.print()
		else if (da && !mac) // IE4 (Windows)
			vbPrintPage()
		else // 다른 부라우저
			alert("죄송합니다. 이 브라우저는 이 기능을 지원하지 않습니다!");
		return false;	
		*/
		
		var url = iKEP.getContextRoot() + '/support/customer/customerBondsIssue/detailBondsIssuePrint.do?seqNum=${bondsIssue.seqNum}';
		
		iKEP.popupOpen(url, {width:800, height:560}, "BoardItemPrint");
	});	
	
	$("#moreButton").click(function(){
		$("#boardBondsIssueSubForm").show();
		iKEP.iFrameContentResize();
	});

	
	var uploaderOptions = {// 사용할 수 있는 옵션 항목
		<c:if test="${!empty(fileDataListJson)}">
			files : /* 파일 목록 */ ${fileDataListJson}, 
		</c:if>
	    isUpdate : false,    // 등록 및 수정일때 true
        //uploadUrl : "",    // 파일 업로드 경로가 다를때 설정 : 사용하지 않음
        allowFileType  : "allowAll"    // 허용가능한 파일 확장자 설정, default = "img,comp,doc" (정책에 따라 변경될 수 있습니다.)
    };
	
	//파일업로드 컨트롤로 생성   
	//fileController = new iKEP.FileController(null, "#fileUploadArea", uploaderOptions);

	
	$("a.deleteButton").click(function() {
	
		
		var url = "<c:url value='/support/customer/customerBondsIssue/deleteBondsIssue.do'/>";
		
		if(confirm("삭제하시겠습니까?")){
		
			$.ajax({
				url : url,
				data :{'seqNum': '${bondsIssueHistory.seqNum}',
					   'registerId' : '${bondsIssueHistory.registerId}'},
				type : "get",
				success : function() {
					alert("삭제가 완료되었습니다.");
					
					location.href="<c:url value = '/support/customer/customerBondsIssue/bondsIssueList.do'/>";
				},
				error : function(event, request, settings) { 
					alert("error");	
				}
			});	
		}
	});
	


	

});






})(jQuery);

function addReply(id){
	$jq("#linereplyForm_"+id).submit();
	
}

function printWindow() 
{
	//factory.printing.paperSize = "A4"
    factory.printing.header = "" //머릿글
	factory.printing.footer = "" //바닥글
	factory.printing.portrait = true //true는 세로 출력, false는 가로 출력
	factory.printing.leftMargin = 5.33 //왼쪽 여백
	factory.printing.topMargin = 22.23 //위쪽 여백
	factory.printing.rightMargin = 5.33 //오른쪽 여백
	factory.printing.bottomMargin = 4.23 //바닥 여백	
	
	factory.printing.Preview();
	setTimeout("javascript:iKEP.closePop()", 100);

}		


</script>

<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814"
        codebase="<c:url value="/Bin/smsx.cab#Version=6,5,439,72"/>">
</object>

	<h1 class="none">컨텐츠영역</h1>
								
				<!--tableTop Start-->
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>채권 관련 이슈</h2>
					<div class="clear"></div>
				</div>
				<!--//tableTop End-->
                
				<!--blockDetail Start-->
                <div class="blockDetail">
					<table summary="상담이력 상세조회">
						<caption></caption>
                        <colgroup>
                            <col width="10%"/>
                            <col width="32%"/>
                            <col width="10%"/>
                            <col width="15%"/>
                            <col width="10%"/>
                            <col width="23%"/>
                        </colgroup>
						<tbody>
							<tr>
                                <th scope="row"><span class="colorPoint"></span>고객명</th>
								<td colspan="5">${bondsIssueHistory.customer}</td>
							</tr>
                        </tbody>
					</table>
					
				</div>
                <!--//blockDetail End-->
                
                <div class="blockBlank_10px"></div>

				<c:forEach var="boardBondsIssueSub" varStatus="varStatus" items="${searchResult.entity}">
					<div class="blockDetail">
						<table>
							<caption></caption>
		                     <colgroup>
		                        <col width="10%"/>
	                            <col width="32%"/>
	                            <col width="10%"/>
	                            <col width="15%"/>
	                            <col width="10%"/>
	                            <col width="23%"/>
		                     </colgroup>
							<tbody>
								<tr>
									<th>구분</th>
									<td colspan="2">${boardBondsIssueSub.claimGubunSubName}</td>
									<th>등급</th>
									<td colspan="3">${boardBondsIssueSub.factorySub}</td>
								</tr>
								<tr>
									<th>등록일</th>
									<td colspan="2">${boardBondsIssueSub.counselDateSub}</td>
									<th>등록자</th>
									<td colspan="3">${boardBondsIssueSub.counselorSub}</td>
								</tr>
								<tr>
									<th>제목</th>
									<td colspan="6">${boardBondsIssueSub.subjectSub}</td>
								</tr>
								<tr>
									<th scope="row">이슈내용</th>
									<td colspan="6">
										<% pageContext.setAttribute("newLineChar", "\n"); %> 
										<p name="contents">${fn:replace(boardBondsIssueSub.contents, newLineChar, '<br/>')}</p>
									</td>
								</tr>
							</tbody>
						</table> 
					</div>
				</c:forEach>
  
                <!--blockButton Start-->
                
                <!--//blockButton End-->
                <div class="blockBlank_10px"></div>
                
                <div id="blockMoreSell" class="blockMoreSell"></div>
  				<br/>
			  	<!--blockComment Start--> 
				<!-- <div id="blockComment" class="blockComment"></div> -->
				<!--//blockComment End-->