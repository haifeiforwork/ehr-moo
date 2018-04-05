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
	loadBoardMoreSellList();
	

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
		
		var url = iKEP.getContextRoot() + '/support/customer/customerBondsIssue/detailBondsIssuePrint.do?seqNum=${bondsIssueHistory.seqNum}';
		iKEP.popupOpen(url, {width:800, height:560}, "BoardItemPrint");
	});	
	
	$("#moreButton").click(function(){
		$("#boardBondsIssueSubForm").show();
		$("input[name=counselDateSub]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
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
	

	$("#readerListViewButton").click( function() {  
		var url = "<c:url value='/support/customer/customerBasicInfo/listReaderView.do?id=${bondsIssueHistory.seqNum}&divCode=BH'/>";
		iKEP.popupOpen(url , {width:700, height:500});
	});
	

});






})(jQuery);

function addReply(id){
	$jq("#linereplyForm_"+id).submit();
	
}


</script>



	<h1 class="none">컨텐츠영역</h1>
								
				<!--tableTop Start-->
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>채권 관련 이슈</h2>
					<div class="clear"></div>
				</div>
				<!--//tableTop End-->

                 <!--blockButton Start-->
                <div class="blockButton"> 
                    <ul>
                   	<c:if test="${birole eq true || isAdmin eq true}">
                    	<li><a id="moreButton"  class="button" href="#a"><span>추가</span></a></li>
                    </c:if>
                    <c:choose>
	 				<c:when test="${bondsIssueHistory.registerId eq user.userId || isAdmin eq true}">
	 				    <li><a id="createPrintButton"  class="button" href="#a"><span>인쇄</span></a></li>
                        <li><a class="button" href="<c:url value='/support/customer/customerBondsIssue/modifyBondsIssueView.do?seqNum=${bondsIssueHistory.seqNum}'/>"><span>수정</span></a></li>
                        <li><a class="button deleteButton" href="#a"><span>삭제</span></a></li>
                        <li><a id="readerListViewButton" class="button" href="#a"><span>조회현황</span></a></li>
                        <li><a class="button" href="<c:url value='/support/customer/customerBondsIssue/bondsIssueList.do'/>"><span>목록</span></a></li>
                    </c:when>
                    <c:otherwise>
                    	  <li><a id="createPrintButton"  class="button" href="#a"><span>인쇄</span></a></li>
                    	  <li><a id="readerListViewButton" class="button" href="#a"><span>조회현황</span></a></li>
                    	  <li><a class="button" href="<c:url value='/support/customer/customerBondsIssue/bondsIssueList.do'/>"><span>목록</span></a></li>
                    </c:otherwise>
                    </c:choose>
                    </ul>
                </div>
                <!--//blockButton End-->
                
                 <!--subTitle_1 Start-->
				<div class="subTitle_2 noline">
					<h3>채권 관련 이슈 상세조회 </h3>
				</div>
				<!--//subTitle_1 End-->	
                
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
								<%-- <th scope="row"><span class="colorPoint">*</span>피상담자</th>
								<td>${bondsIssueHistory.client}</td>
								<th scope="row">연락처</th>
								<td>${bondsIssueHistory.clientPhone}</td> --%>
							</tr>
						    <%-- <tr>
						    	<th scope="row">구분</th>
								<td>${bondsIssueHistory.claimGubun}</td>
								<th scope="row">등급</th>
								<td colspan="3">${bondsIssueHistory.factory}</td>
							</tr> --%>
               		  		
							<%-- <tr>
                                <th scope="row"><span class="colorPoint"></span>등록일</th>
								<td>${bondsIssueHistory.counselDate}</td>
								<th scope="row"><span class="colorPoint"></span>등록자</th>
								<td colspan="3">${bondsIssueHistory.counselor}</td>
								<th scope="row">영업담당자</th>
								<td>${bondsIssueHistory.salesman}</td>
							</tr> --%>
							
							<%-- <tr>
								<th scope="row">등록자</th>
								<td>${bondsIssueHistory.registerName}</td>
								<th scope="row">등록일</th>
								<td colspan="3"><ikep4j:timezone date='${bondsIssueHistory.registDate}'/></td>
							</tr> --%>
							<%-- <tr>
								<th scope="row"><span class="colorPoint"></span>제목</th>
								<td colspan="5">${bondsIssueHistory.subject}</td>
							</tr>
                            <tr>
								<th scope="row" style="min-height:100px"><span class="colorPoint"></span>이슈내용</th>
								<c:set var ="contens2" value ="${fn:replace(bondsIssueHistory.content, crlf, '<br/>')}"  />
								<td colspan="5">${fn:replace(contens2, nbsp, "&nbsp;")}</td>
							</tr>  --%>
                            <%-- <tr>
								<th scope="row" style="min-height:50px">조치사항 및 <br />조치결과 </th>
								<c:set var ="actions2" value ="${fn:replace(bondsIssueHistory.actions, crlf, '<br/>')}"  />
								<td colspan="5">${fn:replace(actions2, nbsp, "&nbsp;")}</td>
							</tr> --%>
							<!-- <tr>
							<td colspan="6"><div id="fileUploadArea"></div></td>
							</tr> -->
                        </tbody>
					</table>
				</div>
                <!--//blockDetail End-->
                
                <!-- <div class="blockBlank_10px"></div> -->

  
                <!--blockButton Start-->
                
                <!--//blockButton End-->
                <!-- <div class="blockBlank_10px"></div> -->
                
                <div id="blockMoreSell" class="blockMoreSell"></div>
  				<br/>
			  	<!--blockComment Start--> 
				<!-- <div id="blockComment" class="blockComment"></div> -->
				<!--//blockComment End-->