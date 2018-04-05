<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/base/common/fileUploadControll.jsp"%>
<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>
<c:set var="prefix" value="ui.support.partner.board" />
<c:set var="preSearch" value="ui.ikep4.common.searchCondition" />
<c:set var="preButton" value="ui.lightpack.common.button" />
<c:set var="preList" value="ui.support.partner.manInfoItemList" />
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
		$("#blockComment").load('<c:url value="/support/partner/partnerLinereply/listLinereplyView.do?itemId=${qualityClaimSellHistory.seqNum}&itemType=CL"/>', $("#boardLinereplySearchForm").serialize(), function() {
			iKEP.iFrameContentResize();
		$("#blockComment").ajaxLoadComplete();
			//$(".firstLineItem").focus();
		
		}).error(function(event, request, settings) { alert("error"); });
	};
	
	loadBoardQualityClaimSellMoreList = function() {  
		
		$("#blockMoreSell").ajaxLoadStart(); 
		$("#blockMoreSell").load('<c:url value="/support/partner/partnerQualityClaimSellMore/listQualityClaimSellMoreView.do?itemId=${qualityClaimSellHistory.seqNum}&itemType=CL"/>', $("#boardQualityClaimSellMoreSearchForm").serialize(), function() {
			iKEP.iFrameContentResize();
		$("#blockMoreSell").ajaxLoadComplete();
			//$(".firstLineItem").focus();
		 
		}).error(function(event, request, settings) { alert("error"); });
	};
	
	loadBoardMoreSellList = function() {  
		
		$("#blockMoreSell").ajaxLoadStart(); 
		$("#blockMoreSell").load('<c:url value="/support/partner/partnerQualityClaimSellMore/listQualityClaimSellMoreView.do?itemId=${qualityClaimSellHistory.seqNum}&itemType=CL"/>', $("#boardQualityClaimSellMoreSearchForm").serialize(), function() {
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
	
	var relCompany = "${qualityClaimSellHistory.relationCompany}";
	if(relCompany != ""){
		var relCompany1 = relCompany.split("^");
		var relCompany2 = "";
		for(i=0;i<relCompany1.length;i++){
			relCompany2 = relCompany1[i].split("|");
			$jq("#relationCompanyBody").append(
				"<tr>"+
				    "<td>"+relCompany2[0]+"</td>"+
				    "<td>"+relCompany2[1]+"</td>"+
				    "<td>"+relCompany2[2]+"</td>"+
				 "</tr>"
			);
		}
		relComCnt = relCompany1.length;	
	}else{
		$jq("#relationCompanyBody").append(
				"<tr>"+
				    "<td></td>"+
				    "<td>"+
				    "<td>"+
				 "</tr>"
		);
	}
	
	//loadBoardLinereplyList();
	//loadBoardMoreSellList();
	printWindow();
	$("#createPrintButton").click(function() {			
		if (pr) // NS4, IE5
			window.print()
		else if (da && !mac) // IE4 (Windows)
			vbPrintPage()
		else // 다른 부라우저
			alert("죄송합니다. 이 브라우저는 이 기능을 지원하지 않습니다!");
		return false;		
	});	

	$("#moreButton").click(function(){
		$("#boardQualityClaimSellMoreForm").show();
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
	
		
		var url = "<c:url value='/support/partner/partnerQualityClaimSell/deleteQualityClaimSell.do'/>";
		
		if(confirm("삭제하시겠습니까?")){
		
			$.ajax({
				url : url,
				data :{'seqNum': '${qualityClaimSellHistory.seqNum}',
					   'registerId' : '${qualityClaimSellHistory.registerId}'},
				type : "get",
				success : function() {
					alert("삭제가 완료되었습니다.");
					
					location.href="<c:url value = '/support/partner/partnerQualityClaimSell/qualityClaimSellList.do'/>";
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
					<h2>Contact Report</h2>
					<div class="clear"></div>
				</div>
				<!--//tableTop End-->

                
                
				<!--blockDetail Start-->
                <div class="blockDetail">
					<table summary="상담이력 상세조회">
						<caption></caption>
                        <colgroup>
                             <col width="10%"/>
	                         <col width="15%"/>
	                         <col width="10%"/>
	                         <col width="15%"/>
	                         <col width="10%"/>
	                         <col width="15%"/>
	                         <col width="10%"/>
	                         <col width="15%"/>
                        </colgroup>
						<tbody>
						    <tr>
                                <th scope="row">거래처명</th>
								<td colspan="3">${qualityClaimSellHistory.partnerName}</td>
								<th scope="row">대표자</th>
								<td colspan="3">${qualityClaimSellHistory.ceoName}</td>
							</tr>
							<tr>
                                <th scope="row">업태</th>
								<td colspan="3">${qualityClaimSellHistory.category}</td>
								<th scope="row">사업자번호</th>
								<td colspan="3">${qualityClaimSellHistory.businessNo}</td>
							</tr>
							<tr>
                                <th scope="row">종목</th>
								<td colspan="3">${qualityClaimSellHistory.sector}</td>
								<th scope="row">법인번호</th>
								<td colspan="3">${qualityClaimSellHistory.corporationNo}</td>
							</tr>
							<tr>
                                <th scope="row">우편번호</th>
								<td colspan="3">${qualityClaimSellHistory.zipNo}</td>
								<th scope="row">담당자</th>
								<td colspan="3">${qualityClaimSellHistory.keyMan}</td>
							</tr>
							<tr>
								<th scope="row">주소</th>
								<td colspan="3">${qualityClaimSellHistory.address}</td>
								<th scope="row">연락처</th>
								<td colspan="3">${qualityClaimSellHistory.contacts}</td>
							</tr>
							<tr>
                                <th scope="row">대표전화</th>
								<td colspan="3">${qualityClaimSellHistory.mainPhone}</td>
								<th scope="row">Fax</th>
								<td colspan="3">${qualityClaimSellHistory.fax}</td>
							</tr>
							<tr>
								<th scope="row">E-mail</th>
								<td colspan="3">${qualityClaimSellHistory.email}</td>
								<th scope="row">SAP ID</th>
								<td colspan="3">${qualityClaimSellHistory.sapId}</td>
							</tr>
							<tr>
								<th scope="row">구분</th>
								<td colspan="3">
								<c:choose>
									<c:when test="${qualityClaimSellHistory.purpose == 'CR01'}">
									주원료
									</c:when>
									<c:when test="${qualityClaimSellHistory.purpose == 'CR02'}">
									부원료
									</c:when>
									<c:when test="${qualityClaimSellHistory.purpose == 'CR03'}">
									기자재
									</c:when>
									<c:when test="${qualityClaimSellHistory.purpose == 'CR04'}">
									포장재
									</c:when>
									<c:when test="${qualityClaimSellHistory.purpose == 'CR05'}">
									펄프판매
									</c:when>
									<c:when test="${qualityClaimSellHistory.purpose == 'CR07'}">
									펠릿
									</c:when>
									<c:when test="${qualityClaimSellHistory.purpose == 'CR06'}">
									기타
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
								</td>
								<th scope="row">등록자</th>
								<td colspan="3">${qualityClaimSellHistory.registerName}</td>
							</tr>
							<tr>
								<th scope="row">품목</th>
								<td colspan="3">${qualityClaimSellHistory.items}</td>
								<th scope="row">등록일</th>
								<td colspan="3"><fmt:formatDate pattern="yyyy.MM.dd" value="${qualityClaimSellHistory.registDate}"/></td>
							</tr>
                        </tbody>
					</table>
				</div>
                <!--//blockDetail End-->
                
				<c:forEach var="boardQualitySub" varStatus="varStatus" items="${searchResult.entity}">
					<div class="blockDetail">
						<table>
							<caption></caption>
				                     <colgroup>
				                         <col width="7%"/>
				                         <col width="8%"/>
				                         <col width="15%"/>
				                         <col width="10%"/>
				                         <col width="10%"/>
				                         <col width="10%"/>
				                         <col width="15%"/>
				                         <col width="10%"/>
				                         <col width="15%"/>
				                     </colgroup>
							<tbody>
								<tr>
									<th>순번:${linereplyCount-varStatus.index}
									</th>
									<th>상담일</th>
									<td>${boardQualitySub.counselDate}</td>
									<th>면담형태</th>
									<td>
										<c:choose>
											<c:when test="${boardQualitySub.counselType == '1'}">
											유선
											</c:when>
											<c:when test="${boardQualitySub.counselType == '2'}">
											방문
											</c:when>
											<c:otherwise></c:otherwise>
										</c:choose>
									</td>
									<th>피상담자</th>
									<td>${boardQualitySub.requestor}</td>
									<th>상담자</th>
									<td>${boardQualitySub.counselor}</td>
								</tr>
								<span class="viewDivCtl" id="viewDiv_${boardQualitySub.linereplyId}" name="viewDiv_${boardQualitySub.linereplyId}">
								<tr>
									<th colspan="2">제목</th>
									<td colspan="7">
										${boardQualitySub.counselTitle}
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">상담내역</th>
									<td colspan="7">
										<p name="counselContents">${fn:replace(boardQualitySub.counselContents, newLineChar, '<br/>')}</p>
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">팀장Comment</th>
									<td colspan="7">
										<font style="color:#003366;font-weight:bold;">${boardQualitySub.commentuser1}</font> <font style="color:#999;font-size:0.9em;">${boardQualitySub.commentuserTeam1}</font> <img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" /> <font style="color:#999;font-size:0.9em;"><fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${boardQualitySub.commentRegistDate1}"/></font>
										<p name="comment1">${fn:replace(boardQualitySub.comment1, newLineChar, '<br/>')}</p>
									</td>
								</tr>
								<tr>
									<th scope="row" colspan="2">임원Comment</th>
									<td colspan="7">
										<font style="color:#003366;font-weight:bold;">${boardQualitySub.commentuser2}</font> <font style="color:#999;font-size:0.9em;">${boardQualitySub.commentuserTeam2}</font> <img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" /> <font style="color:#999;font-size:0.9em;"><fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${boardQualitySub.commentRegistDate2}"/></font>
										<p name="comment2">${fn:replace(boardQualitySub.comment2, newLineChar, '<br/>')}</p>
									</td>
								</tr>
							</tbody>
						</table> 
					</div>
				</c:forEach>
                <!--blockButton Start-->
                
                
                <div id="blockMoreSell" class="blockMoreSell"></div>
  				<br/>
			  	<!--blockComment Start--> 
				<div id="blockComment" class="blockComment"></div>
				<!--//blockComment End-->