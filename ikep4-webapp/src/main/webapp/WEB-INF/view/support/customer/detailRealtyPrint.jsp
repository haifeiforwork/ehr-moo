<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/base/common/fileUploadControll.jsp"%>
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
		$("#blockComment").load('<c:url value="/support/customer/customerLinereply/listLinereplyView.do?itemId=${realty.seqNum}&itemType=CH"/>', $("#boardLinereplySearchForm").serialize(), function() {
			iKEP.iFrameContentResize();
		$("#blockComment").ajaxLoadComplete();
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
	

		var no = "${realty.no}";
		var division = "${realty.division}";
		var address = "${realty.address}";  
		var owner = "${realty.owner}";    
		var relation = "${realty.relation}"; 
		var pyeongSpace = "${realty.pyeongSpace}";   
		var pyeongBuilding = "${realty.pyeongBuilding}";
		var meterSpace = "${realty.meterSpace}";    
		var meterBuilding = "${realty.meterBuilding}"; 
		var meterSum = "${realty.meterSum}"; 
		var totalSum = "${realty.totalSum}"; 
		var rightDate = "${realty.rightDate}";
		var collateral = "${realty.collateral}";    
		var rightSum = "${realty.rightSum}"; 
		var debt = "${realty.debt}";
		var note = "${realty.note}";
		var tempno = no.split("|");  
		var tempdivision = division.split("|"); 
		var tempaddress = address.split("|");  
		var tempowner = owner.split("|");    
		var temprelation = relation.split("|"); 
		var temppyeongSpace = pyeongSpace.split("|");   
		var temppyeongBuilding = pyeongBuilding.split("|");
		var tempmeterSpace = meterSpace.split("|");    
		var tempmeterBuilding = meterBuilding.split("|"); 
		var tempmeterSum = meterSum.split("|"); 
		var temptotalSum = totalSum.split("|"); 
		var temprightDate = rightDate.split("|");
		var tempcollateral = collateral.split("|");    
		var temprightSum = rightSum.split("|"); 
		var tempdebt = debt.split("|");
		var tempnote = note.split("|");

		for(i=0;i<tempno.length;i++){
			$jq("#realtyBody").append(
				"<tr>"+
					"<td style=\"text-align:center;\">"+tempno[i]+"</td>"+
					"<td>"+tempdivision[i]+"</td>"+
					"<td>"+tempaddress[i]+"</td>"+
					"<td>"+tempowner[i]+"</td>"+
					"<td>"+temprelation[i]+"</td>"+
					"<td>"+temppyeongSpace[i]+"</td>"+
					"<td>"+temppyeongBuilding[i]+"</td>"+
					"<td>"+tempmeterSpace[i]+"</td>"+
					"<td>"+tempmeterBuilding[i]+"</td>"+
					"<td>"+tempmeterSum[i]+"</td>"+
					"<td>"+temptotalSum[i]+"</td>"+
					"<td>"+temprightDate[i]+"</td>"+
					"<td>"+tempcollateral[i]+"</td>"+
					"<td>"+temprightSum[i]+"</td>"+
					"<td>"+tempdebt[i]+"</td>"+
					"<td>"+tempnote[i]+"</td>"+
				"</tr>"
			);
		}
		printWindow();
	
		$("#createPrintButton").click(function() {			
			var url = iKEP.getContextRoot() + '/support/customer/customerRealty/detailRealtyPrint.do?seqNum=${realty.customerId}';
			iKEP.popupOpen(url, {width:800, height:560}, "BoardItemPrint");
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
		fileController = new iKEP.FileController(null, "#fileUploadArea", uploaderOptions);

	
	
		$("a.deleteButton").click(function() {
		
			
			var url = "<c:url value='/support/customer/customerRealty/deleteRealty.do'/>";
			
			if(confirm("삭제하시겠습니까?")){
			
				$.ajax({
					url : url,
					data :{'seqNum': '${realty.seqNum}',
						   'registerId' : '${realty.registerId}'},
					type : "get",
					success : function() {
						alert("삭제가 완료되었습니다.");
						
						location.href="<c:url value = '/support/customer/customerRealty/realtyList.do'/>";
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
	factory.printing.portrait = false //true는 세로 출력, false는 가로 출력
	factory.printing.leftMargin = 5.33 //왼쪽 여백
	factory.printing.topMargin = 5.23 //위쪽 여백
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
				<h2>부동산 정보</h2>
				<div class="clear"></div>
			</div>
			<!--//tableTop End-->
		
	     
              <!--blockButton Start-->
               <!--//blockButton End-->
               
               <!--subTitle_1 Start-->
				<!--//subTitle_1 End-->	
			<!--blockDetail Start-->
               <div class="blockDetail">
				<table>
					<caption></caption>
					<colgroup>
						<col width="3%" />
						<col width="6%" />
						<col width="10%" />
						<col width="6%" />
						<col width="8%" />
						<col width="5%" />
						<col width="5%" />
						<col width="5%" />
						<col width="5%" />
						<col width="7%" />
						<col width="6%" />
						<col width="7%" />
						<col width="8%" />
						<col width="7%" />
						<col width="6%" />
						<col width="6%" />
					</colgroup>
					<tbody id="realtyBody">
						<tr>
							<th colspan="2" style="text-align:center;">고객명</th>
							<td colspan="14">${realty.customerName}</td>
						</tr>
						<tr>
							<th rowspan="2" style="text-align:center;">NO.</th>
							<th rowspan="2" style="text-align:center;">구분</th>
							<th rowspan="2" style="text-align:center;">주소</th>
							<th rowspan="2" style="text-align:center;">소유자</th>
							<th rowspan="2" style="text-align:center;">소유자와의<br/>관계</th>
							<th colspan="2" style="text-align:center;">면적(단위:평)</th>
							<th colspan="2" style="text-align:center;">면적(단위:㎡)</th>
							<th colspan="2" style="text-align:center;">개별공시지가</th>
							<th colspan="4" style="text-align:center;">권리관계</th>
							<th rowspan="2" style="text-align:center;">비고<br/>(권리침해사항)</th>
						</tr>
						<tr>
							<th style="text-align:center;">대지</th>
							<th style="text-align:center;">건물</th>
							<th style="text-align:center;">대지</th>
							<th style="text-align:center;">건물</th>
							<th style="text-align:center;">㎡당 금액</th>
							<th style="text-align:center;">총금액</th>
							<th style="text-align:center;">설정일자</th>
							<th style="text-align:center;">근저당권자</th>
							<th style="text-align:center;">설정금액</th>
							<th style="text-align:center;">채무자</th>
						</tr>
						<div class="realtyBody"></div>	
					</tbody>
				</table>
				</div>
                <!--//blockDetail End-->
               
                              
					
