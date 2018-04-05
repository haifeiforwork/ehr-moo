<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.wfapproval.work.apdoc.create.header" />
<c:set var="preView"    value="ui.wfapproval.work.apdoc.create.form" /> 
<c:set var="preButton"  value="ui.wfapproval.work.apdoc.create.button" />
<c:set var="preForm"  	value="ui.wfapproval.work.delegate.form" />
<c:set var="preMessage" value="ui.wfapproval.common.message" />
<c:set var="preSearch"  value="ui.wfapproval.common.searchCondition" />
 <%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>

<script type="text/javascript">
(function($) {
	setAddress = function(data) {

		var addStr="";

		$jq.each(data, function() {
			addStr = addStr + "\""+$jq.trim(this.name)+"\" "+$jq.trim(this.email)+",";
		})

		$jq("#addrDiv").html(addStr.substring(0,addStr.length-1));

	};
	var viewUnitDelegateDialog;
	/**
	 * 위임 설정 화면 오픈.
	 */
	viewUnitDelegateDialog = function () {
		
		$instanceLogId	= $("input[name=insLogId]:hidden");
		
		viewApFormProcessDialog = new iKEP.Dialog({     
			title 		: "<ikep4j:message pre='${preForm}' key='subTitle'/>",
			url 		: "<c:url value="/wfapproval/work/delegate/updateUnitDelegateForm.do"/>",
			modal 		: true,
			width 		: 650,
			height 		: 250,
			params 		: {id:$instanceLogId.val(), target:"approval"}
		});
	};
	
	/**
	 * 기결재첨부 뷰 팝업.
	 */
	f_ViewApDocRelation = function(apprId) {
		
		var dialog = new iKEP.Dialog({     
			title 		: "기결재첨부 상세보기",
			url 			: "/ikep4-webapp/wfapproval/work/apdoc/printApDoc.do?apprId="+apprId,
			modal 		: true,
			width 		: 1000, //1200,
			height 		: 600,
			params 		: null,
			callback	: null
		});
	};
	

	$(document).ready(function() {		
		$("#apprDocData").ckeditor($.extend(fullCkeditorConfig, {"popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));  

		////var fileController = new iKEP.FileController("#apDoc", "#fileUploadArea", {
//	 		files : /* 파일 목록 */ [, {fileid:2, name:"file 2", size:100, type:"jpg"}], 
			//size : /* 파일 컨트롤 싸이즈 특별한 케이스에만 사용 디폴트 w 100 h 100 */ {width:100, height:100},
			//uploadUrl : /* 파일 업로드 URL fileupload package 안에 존재함. 커스터마이징 하시면 안됨 */ "",
			//excludeFileTypes :/* 업로드제한 파일확장자 */  "jpg^gif",
		//	lang :  "en"
		//});	
		//$("#accordion").accordion(); 

		
		$jq('#workers2 option').each(function() {
			//alert(this.value + " " + this.workerName + " " + this.jobTitle + " " + this.teamName);
			$popdata={
			    type:"user",
				id:this.value,
				jobTitle:this.jobTitle,
				name:this.workerName,
				searchSubFlag:false,
				teamName:this.teamName
			}
			$jq.data(this, "data", $popdata);
		});
		
		$("#viewUnitDelegateButton").click(function(){
			viewUnitDelegateDialog();
		});
		
		//결재/반려처리
		$("#approvalButton").click(function() {

			//alert("결재");
			
			//결재요청팝업 버튼 이벤트
			dialog1 = $jq("#dialog").dialog({
				modal:true,
				width:300,
				height:250,
				buttons: {
		            '승인': function() {
						document.apDoc.apprMessage.value=
						document.messageAddDialog.apprMessage.value;
						document.apDoc.approvalYn.value='Y';
						$.post('<c:url value="/wfapproval/work/apdoc/ajaxConfirmApDoc.do"/>', $("#apDoc").serialize()).success(function(data){
							alert("승인완료입니다.");
							window.location.href ="<c:url value="/wfapproval/work/aplist/listApTodo.do"/>";
						}).error(function(event, request, settings){
			               alert("승인시 오류가 발생하였습니다.");
			            });
						dialog1.dialog('close');
		            },
		            '반려': function() {
		            	document.apDoc.apprMessage.value=
		            	document.messageAddDialog.apprMessage.value;
		            	document.apDoc.approvalYn.value='N';
		            	$.post('<c:url value="/wfapproval/work/apdoc/ajaxConfirmApDoc.do"/>', $("#apDoc").serialize()).success(function(data){
							alert("반려완료입니다.");
							window.location.href ="<c:url value="/wfapproval/work/aplist/listApTodo.do"/>";
						}).error(function(event, request, settings){
			               alert("승인시 오류가 발생하였습니다.");
			            });
		            	dialog1.dialog('close')
		            	return false; 
		            },
		            '취소': function() {
		            	dialog1.dialog('close')
		            	return false; 
		            }		            
		        }
			});
		});		

		//합의/재고려 처리
		$("#agreeButton").click(function() {

			//alert("결재");
			
			//결재요청팝업 버튼 이벤트
			dialog1 = $jq("#dialog").dialog({
				modal:true,
				width:300,
				height:250,
				buttons: {
		            '합의': function() {
						document.apDoc.apprMessage.value=
						document.messageAddDialog.apprMessage.value;
						document.apDoc.approvalYn.value='Y';
						$.post('<c:url value="/wfapproval/work/apdoc/ajaxConfirmApDoc.do"/>', $("#apDoc").serialize()).success(function(data){
							alert("합의완료입니다.");
							window.location.href ="<c:url value="/wfapproval/work/aplist/listApTodo.do"/>";
						}).error(function(event, request, settings){
			               alert("합의시 오류가 발생하였습니다.");
			            });
						dialog1.dialog('close');
		            },
		            '재고려': function() {
		            	document.apDoc.apprMessage.value=
						document.messageAddDialog.apprMessage.value;
						document.apDoc.approvalYn.value='N';
						$.post('<c:url value="/wfapproval/work/apdoc/ajaxConfirmApDoc.do"/>', $("#apDoc").serialize()).success(function(data){
							alert("재고려 완료입니다.");
							window.location.href ="<c:url value="/wfapproval/work/aplist/listApTodo.do"/>";
						}).error(function(event, request, settings){
			               alert("재고려시 오류가 발생하였습니다.");
			            });						
		            	dialog1.dialog('close')
		            	return false; 
		            },
		            '취소': function() {
		            	dialog1.dialog('close')
		            	return false; 
		            }		            
		        }
			});
		});

		//전결
		$("#proxyButton").click(function() {

			//alert("전결");
			
			//결재요청팝업 버튼 이벤트
			dialog1 = $jq("#dialog").dialog({
				modal:true,
				width:300,
				height:250,
				buttons: {
		            '전결': function() {
						document.apDoc.apprMessage.value=
						document.messageAddDialog.apprMessage.value;
						document.apDoc.decisionYn.value='Y';
						$.post('<c:url value="/wfapproval/work/apdoc/ajaxConfirmApDoc.do"/>', $("#apDoc").serialize()).success(function(data){
							alert("전결완료입니다.");
							window.location.href ="<c:url value="/wfapproval/work/aplist/listApTodo.do"/>";
						}).error(function(event, request, settings){
			               alert("전결시 오류가 발생하였습니다.");
			            });
						dialog1.dialog('close');
		            },
		            '취소': function() {
		            	dialog1.dialog('close')
		            	return false; 
		            }		            
		        }
			});
		});
		
		//목록
		$("#listButton").click(function() {
		
			var ref = document.referrer;
			location.href=ref;
			
		});
		
		
		/**
		 * 프로세스 진행현황 화면 오픈.
		 */
		f_ViewApProcessState = function(apprId, instanceId, processId) {
			var mode = "runtime";//"model";//"design";
			viewApProcessStateDialog = new iKEP.Dialog({     
				title 		: "결재진행현황",
				url 			: "/ikep4-webapp/workflow/modeler/prism.do?mode="+mode+"&apprId="+apprId+"&processId="+processId+"&version=&instanceId="+instanceId+"&onlyProcessView=true&processType=approval",
				modal 		: true,
				width 		: 950, //1200,
				height 		: 500,
				params 		: null,
				callback	: null
			});
		}
		
		
		//전달 버튼 클릭시
		$("a[name=sendMailButton]").click(function() {
			
			//alert("sendmail");		
			//fileController.upload(function(isSuccess, files) {
			//	if(isSuccess === true) { 
			//		//$jq("#boardItemForm").submit();
			//		f_SaveApDoc();
			//	}
			//});
			
			//iKEP.sendMailPop();

			
			
			var nameList = ['유승목'];
			var emailList = ['handul32@hanmail.net'];
			var title = $jq('#titleDiv').html();
			var content = $jq('#contentDiv').html();


			
			
			var fileIdList = ['111','222'];
			var fileNameList = ['111.txt','222.txt'];
			
			iKEP.sendMailPop(nameList, emailList, title, content, fileIdList, fileNameList);
			
			
			
			//var dialog = new iKEP.Dialog({     
			//	title 		: "메일보내기",
			//	url 		: "/ikep4-webapp/support/mail/sendMailForm.do",
			//	modal 		: true,
			//	width 		: 800, //1200,
			//	height 		: 500,
			//	params 		: null,
			//	callback	: null
			//});
			
			
			return false; 
			
		});
		
		//var rows = jQuery("#test");
		//alert(rows);
		//var index = rows.length;
		//alert(index);
		
		//수신처지정
		$("a[name=createBoardViewButton1]").click(function() {  
		//$jq("#btnAddrControl").click(function() {
			alert('수신처');
			var items = [];
			var $sel = $jq("#apDoc").find("[name=addrGroupList]");
			$jq.each($sel.children(), function() {
				items.push($.data(this, "data"));
			});
 
			$controlType = $jq('input[name=controlType]:hidden').val() ;
			$controlTabType = $jq('input[name=controlTabType]:hidden').val() ;
			$selectType = $jq('input[name=selectType]:hidden').val() ;
			$selectMaxCnt = $jq('input[name=selectMaxCnt]:hidden').val() ;
			$searchSubFlag = $jq('input[name=searchSubFlag]:hidden').val() ;
 
			
			var url = "<c:url value='/support/popup/addresbookPopup.do'/>"+"?controlType=" + $controlType + "&controlTabType=" + $controlTabType + "&selectType=" + $selectType + "&selectMaxCnt=" + $selectMaxCnt + "&searchSubFlag=" + $searchSubFlag;
            iKEP.popupOpen(
                url
                , {width:700, height:550
                    ,argument : {search:"keyword", items:items }
                    , callback:function(result) {
                        $sel.empty();
                        $jq.each(result, function() {
                            var tpl = "";
                            switch(this.type) {
                                case "group" : tpl = "addrBookItemGroup"; break;
                                case "user" : tpl = "addrBookItemUser"; break;
                            }
                            var $option = $.tmpl(tpl, this).appendTo($sel);
                            $.data($option[0], "data", this);
                            if( this.searchSubFlag == true ){
                                $jq('input[name=searchSubFlag]:hidden').val("true") ;
                            }
        
                        })
                    }
                }
            );
			
			//var dialog = new iKEP.Dialog({
			//	title: "수신처지정",
			//	url: "/ikep4-webapp/support/addressbook/addresbookPopup.do"+"?controlType=" + $controlType + "&controlTabType=" + $controlTabType + "&selectType=" + $selectType + "&selectMaxCnt=" + $selectMaxCnt + "&searchSubFlag=" + $searchSubFlag,
			//	modal: true,
			//	width: 700,
			//	height: 540,
			//	params : {search:"keyword", items:items },
			//	callback : function(result) {
			//		
			//		$sel.empty();
					
			//		$jq.each(result, function() {
			//			
			//			var tpl = "";
			//			
			//			switch(this.type) {
			//				case "group" : tpl = "addrBookItemGroup"; break;
			//				case "user" : tpl = "addrBookItemUser"; break;
			//			}
						
			//			var $option = $.tmpl(tpl, this).appendTo($sel);
						
						
			//			$.data($option[0], "data", this);
						
			//			if( this.searchSubFlag == true ){
			//				$jq('input[name=searchSubFlag]:hidden').val("true") ;
			//			}
			//			
 
			//		})
			//	}
			//});
		});
		$.template("addrBookItemUser", '<option value="\${id}" >\${name}/\${jobTitle}/\${teamName}/\${id}</option>');
		$.template("addrBookItemGroup", '<option value="\${code}" >\${name}</option>');
		
		
		//주소록 버튼에 이벤트 바인딩
        $jq('#addrBtn').click( function() {
               //파라미터(콜백함수, 전송파라미터, 팝업옵션)
		iKEP.showAddressBook(setAddress, "", "");
		});

		
		//인쇄
		$("a[name=printButton]").click(function() {  
			
			var dialog = new iKEP.Dialog({     
				title 		: "인쇄",
				url 			: "/ikep4-webapp/wfapproval/work/apdoc/printApDoc.do?apprId=${apDoc.apprId}",
				modal 		: true,
				width 		: 1000, //1200,
				height 		: 500,
				params 		: null,
				callback	: null
			});
			
		});
			
			
			

		
			
			
			
			
			
		
	});
})(jQuery);

</script>

<script  type="text/javascript"> 
 
	/**
	 * 양식 저장
	 * @param {String} 양식ID
	 */
	function f_SaveApDoc(formId) {
		var oForm = $jq('form[name=apDoc]');
		//oForm.action = "/applyApDoc.do";
		 
		if($jq('#workers option').length < 1) {
				alert("지정이 필요합니다.");
				return;
		}
		if($jq('#workers1 option').length < 1) {
			alert("지정이 필요합니다.");
			return;
		}
		
		var idlist = [];
		var itemstr ="";
		$jq('#workers option').each(function(){idlist.push(this.value)});
		$jq.each(idlist,function(index, item) {itemstr += item + ',';});

		var idlist1 = [];
		var itemstr1 ="";
		$jq('#workers1 option').each(function(){idlist1.push(this.value)});
		$jq.each(idlist1,function(index, item) {itemstr1 += item + ',';});
		
		
		alert("HEre");
		alert(itemstr);
		document.apDoc.etcName.value = itemstr.substring(0, itemstr.length-1);
		document.apDoc.etcName1.value = itemstr1.substring(0, itemstr1.length-1);
		alert(document.apDoc.etcName.value);
		
		alert(document.apDoc.etcName1.value);

		
		alert("end");
		
		
		 //oForm.submit();
		document.apDoc.submit(); //이것도 사용가능
	}

	/**
	 * ModealPopo 
	 * @param {String} 양식ID
	 */
	function f_SaveApDocPop(formId) {
		 
		 var items = [];
		 
			var $sel = $jq("#apDoc").find("[name=addrGroupList2]");
			$jq.each($sel.children(), function() {
				items.push($.data(this, "data"));
			});
			
		 var dialog = new iKEP.Dialog({
				title: "결재선지정",
				//url: "<c:url value='/support/addressbook/addresbookPopup.do'/>"+"?controlType=" + $controlType + "&controlTabType=" + $controlTabType + "&selectType=" + $selectType + "&selectMaxCnt=" + $selectMaxCnt + "&searchSubFlag=" + $searchSubFlag,
				//url: "<c:url value='/wfapproval/work/apdoc/popApDoc.do'/>",
				url: "<c:url value='/wfapproval/work/apdoc/listUserApLine.do'/>",
				modal: true,
				//width: 700,
				//height: 550,
				width:950,
				height:700,
				params : {search:"keyword", items:items },
				callback : function(result) {

					$sel.empty();
					
					$jq.each(result, function() {
							
						var tpl = "";
							
						switch(this.type) {
							case "group" : tpl = "addrBookItemGroup"; break;
							case "user" : tpl = "addrBookItemUser"; break;
						}
							
						var $option = $.tmpl(tpl, this).appendTo($sel);
							
							
						$.data($option[0], "data", this);
							
						if( this.searchSubFlag == true ){
							$jq('input[name=searchSubFlag]:hidden').val("true") ;
						}
							
	 
					})
					
					
					
					
					
				}
			});
		 
	}

</script>
 

			
<h1 class="none"><ikep4j:message pre="${preHeader}" key="title"/></h1>

<!--pageTitle Start-->

<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="pageTitleRead" /></h2>
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.3depth" /></li>
			<!--  <li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.4depth" /></li>-->
		</ul>
	</div>
</div>
<!--//pageTitle End-->
<form:form name="apDoc" modelAttribute="apDoc" method="post">
<div class="blockButton underline"> 
	<ul> 
		<c:forEach var="apProcessUserId" items="${apProcessUserId}" varStatus="apDocItem">
		<c:if test="${apProcessUserId.apprType eq 0 }">
		<input type="hidden" name="apprOrder" id="apprOrder" value="${apProcessUserId.apprOrder }"/>
		<input type="hidden" name="apprType" id="apprType" value="${apProcessUserId.apprType }"/>
		<li><a name="approvalButton" id="approvalButton" class="button" href="#a" ><span><ikep4j:message pre="${preButton}" key="approval" /></span></a></li>
		<li><a id="viewUnitDelegateButton" name="viewUnitDelegateButton" class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="delegate" /></span></a></li>
		<li><a name="proxyButton" id="proxyButton" class="button" href="#a" ><span><ikep4j:message pre="${preButton}" key="proxy" /></span></a></li>
		</c:if>
		<c:if test="${apProcessUserId.apprType eq 1 }">
		<input type="hidden" name="apprOrder" id="apprOrder" value="${apProcessUserId.apprOrder }"/>
		<input type="hidden" name="apprType" id="apprType" value="${apProcessUserId.apprType }"/>
		<li><a name="agreeButton" id="agreeButton" class="button" href="#a" ><span><ikep4j:message pre="${preButton}" key="agree" /></span></a></li>
		</c:if>
		</c:forEach>
		<c:if test="${apDoc.processId ne 'APPROVAL_PROCESS'}">
			<c:if test="${apDoc.insLogId ne ''}">
		<li><a name="approvalButton" id="approvalButton" class="button" href="#a" ><span><ikep4j:message pre="${preButton}" key="approval" /></span></a></li>
			</c:if>
		</c:if>
		<li><a id="sendMailButton" name="sendMailButton" class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="sendMail" /></span></a></li>
		<li><a id="statusButton" name="statusButton" class="button" href="javaScript:f_ViewApProcessState('${apDoc.apprId}','${apDoc.instanceId}','APPROVAL_PROCESS');" ><span><ikep4j:message pre="${preButton}" key="status" /></span></a></li>
		<c:if test="${apDoc.linkType ne 'mywork'}">
		<li><a name="listButton" id="listButton" class="button" ><span><ikep4j:message pre="${preButton}" key="list" /></span></a></li>
		</c:if>
		<li><a name="printButton" id="printButton" class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="print" /></span></a></li>
	</ul>
</div>


<c:if test="${apDoc.processId eq 'APPROVAL_PROCESS'}">
<div class="blockLeft">
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre="${preView}" key="approvalAssignType1" /></h3>
				</div>			
				<!--blockDetail Start-->					
				<div class="blockDetail">
					<table summary="<ikep4j:message pre="${preView}" key="approvalAssignType1" />">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="10%" class="textCenter"><ikep4j:message pre="${preView}" key="seq" /></th>
								<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${preView}" key="status" /></th>
								<th scope="col" width="20%" class="textCenter"><ikep4j:message pre="${preView}" key="name" /></th>
								<th scope="col" width="25%" class="textCenter"><ikep4j:message pre="${preView}" key="teamname" /></th>
								<th scope="col" width="30%" class="textCenter"><ikep4j:message pre="${preView}" key="date" /></th>
							</tr>
						</thead>
						<tbody id="test">
						<c:forEach var="apProcess" items="${apProcess}" varStatus="apDocItem">
						<c:choose>
						<c:when test="${apProcess.apprOrder eq 0 }">
						<c:set var="apprDate" value="${apProcess.apprDate}" />
							<tr>
								<td class="textCenter">&nbsp;</td>
								<td class="textCenter"><b><ikep4j:message pre="${preView}" key="approvalAssignType2" /></b></td>							
								<td class="textCenter">${apProcess.userName}</td>
								<td class="textCenter">${apProcess.teamName}/${apProcess.jobPositionName}</td>
								<td class="textCenter"><spring:eval expression="apDoc.apprReqDate"/></td>
							</tr>
							<tr>
								<td class="textLeft" colspan="5">&nbsp;${apProcess.apprMessage}</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:if test="${apProcess.apprType eq 0 }">
							<tr>
								<td class="textCenter">${apProcess.apprOrder}</td>
								<td class="textCenter"><c:if test="${apProcess.apprState eq 'COMPLETE' }"><b><ikep4j:message pre="${preView}" key="approvalAssignType5" /></b></c:if><c:if test="${apProcess.apprState eq 'ASSIGN' }"><ikep4j:message pre="${preView}" key="approvalAssignType6" /></c:if><c:if test="${apProcess.apprState eq 'REJECT' }"><b><ikep4j:message pre="${preView}" key="approvalAssignType7" /></b></c:if></td>							
								<td class="textCenter">${apProcess.userName}</td>
								<td class="textCenter">${apProcess.teamName}/${apProcess.jobPositionName}</td>

								<td class="textCenter"><c:if test="${apProcess.apprDate ne apprDate }"><spring:eval expression="apProcess.apprDate"/></c:if></td>
							</tr>
							<tr>
								<td class="textLeft" colspan="5">&nbsp;${apProcess.apprMessage}</td>
							</tr>
							</c:if>
						</c:otherwise> 
						</c:choose>
						</c:forEach>
							
							
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
			</div>
<div class="blockRight">
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre="${preView}" key="approvalAssignType4" /></h3>
				</div>			
				<!--blockDetail Start-->					
				<div class="blockDetail">
					<table summary="<ikep4j:message pre="${preView}" key="approvalAssignType4" />">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="10%" class="textCenter"><ikep4j:message pre="${preView}" key="seq" /></th>
								<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${preView}" key="status" /></th>
								<th scope="col" width="20%" class="textCenter"><ikep4j:message pre="${preView}" key="name" /></th>
								<th scope="col" width="25%" class="textCenter"><ikep4j:message pre="${preView}" key="teamname" /></th>
								<th scope="col" width="30%" class="textCenter"><ikep4j:message pre="${preView}" key="date" /></th>
							</tr>
							
						</thead>
						<tbody>
							
							<c:forEach var="apProcess" items="${apProcess}" varStatus="apDocItem">
							
							<c:if test="${apProcess.apprType eq 1 }">
							<tr>
								<td class="textCenter">${apProcess.apprOrder}</td>
								<td class="textCenter"><c:if test="${apProcess.apprState eq 'COMPLETE' }"><b><ikep4j:message pre="${preView}" key="approvalAssignType5" /></b></c:if><c:if test="${apProcess.apprState eq 'ASSIGN' }"><ikep4j:message pre="${preView}" key="approvalAssignType6" /></c:if><c:if test="${apProcess.apprState eq 'REJECT' }"><b><ikep4j:message pre="${preView}" key="approvalAssignType8" /></b></c:if></td>							
								<td class="textCenter">${apProcess.userName}</td>
								<td class="textCenter">${apProcess.teamName}/${apProcess.jobPositionName}</td>
								<td class="textCenter"><c:if test="${apProcess.apprDate ne apprDate }"><spring:eval expression="apProcess.apprDate"/></c:if></td>
							</tr>
							<tr>
								<td class="textLeft" colspan="5">&nbsp;${apProcess.apprMessage}</td>
							</tr>
							</c:if>
							
							</c:forEach>							
							
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
			</div>	
			<div class="clear"></div>
</c:if>

<div class="blockDetail">


					<table summary="<ikep4j:message pre="${preView}" key="detail" />">
						<caption></caption>
						<tbody>
							<tr>
								<th width="18%" scope="row"><ikep4j:message pre="${preView}" key="apprDocCd" /><!--: ${apFormTpl.apprDocCd},${apFormTpl.isApprDoc}--></th>
								
								
								
								
								<td width="32%">
									
									<!--${apDoc.apprSecurityCd}-->
									
									<c:forEach var="apCode" items="${listApprDocCd}">
										<c:if test="${apCode.codeId eq apDoc.apprSecurityCd}">
										<label>${apCode.codeName}
											<!--  <input type="radio" class="radio" title="${apCode.codeName}" name="apprSecurityCd"
													value="${apCode.codeId}" <c:if test="${apCode.codeId eq apDoc.apprSecurityCd}">checked</c:if>/>${apCode.codeName}-->
										</label>&nbsp;&nbsp;
										</c:if>
									</c:forEach>
									

									<c:if test="${apFormTpl.isApprDoc eq 'U'}">
									<c:forEach var="apCode" items="${listApprDocCd}">
										<label>
											<input type="radio" class="radio" title="${apCode.codeName}" name="apprSecurityCd"
													value="${apCode.codeId}" <c:if test="${apCode.codeId eq apFormTpl.apprDocCd}">checked</c:if>/>${apCode.codeName}
										</label>&nbsp;&nbsp;
									</c:forEach>										
									</c:if>
									<c:if test="${apFormTpl.isApprDoc eq 'A'}">
									<c:forEach var="apCode" items="${listApprDocCd}">
										<c:if test="${apCode.codeId eq apFormTpl.apprDocCd}">
									<label>
											<input type="radio" class="radio" title="${apCode.codeName}" name="apprSecurityCd"
													value="${apCode.codeId}" checked/>${apCode.codeName}
										</label>&nbsp;&nbsp;
										</c:if>
									</c:forEach>
									</c:if>
							
								</td>
								
								<th width="18%" scope="row"><ikep4j:message pre="${preView}" key="apprDocNo" /></th>
								<td width="32%">${apDoc.apprDocNo}
								</td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre="${preView}" key="apprTeam" /></th>
								<td>
						<c:forEach var="apProcess" items="${apProcess}" varStatus="apDocItem">
						<c:choose><c:when test="${apProcess.apprOrder eq 0 }">
								${apProcess.teamName}
						</c:when></c:choose>
						</c:forEach><!--  ${user.teamName}-->
								</td>
								<th scope="row"><ikep4j:message pre="${preView}" key="apprRegister" /></th>
								<td>
						<c:forEach var="apProcess" items="${apProcess}" varStatus="apDocItem">
						<c:choose><c:when test="${apProcess.apprOrder eq 0 }">
								${apProcess.userName}/${apProcess.jobPositionName}/${apDoc.registUserId}
						</c:when></c:choose>
						</c:forEach>
								</td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre="${preView}" key="apprDate" /></th>
								<td>

									<spring:eval expression="apDoc.apprReqDate"/>
								</td>
								<th scope="row"><ikep4j:message pre="${preView}" key="apprPeriodCd" /> <!--: ${apFormTpl.apprPeriodCd},${apFormTpl.isApprPeriod}--></th>
								<td>

									
									<c:forEach var="apCode" items="${listApprPeriodCd}">
										
										<c:if test="${apCode.codeId eq apDoc.apprPeriodCd}">
										<label>${apCode.codeName}
											<!--  <input type="radio" class="radio" title="${apCode.codeName}" name="apprPeriodCd"
													value="${apCode.codeId}" <c:if test="${apCode.codeId eq apDoc.apprPeriodCd}">checked</c:if>/>${apCode.codeName}-->
										</label>&nbsp;&nbsp;
										</c:if>
									</c:forEach>
									
									
									
									<c:if test="${apFormTpl.isApprPeriod eq 'U'}">
									<c:forEach var="apCode" items="${listApprPeriodCd}">
										<label>
											<input type="radio" class="radio" title="${apCode.codeName}" name="apprPeriodCd"
													value="${apCode.codeId}" <c:if test="${apCode.codeId eq apFormTpl.apprDocCd}">checked</c:if>/>${apCode.codeName}
										</label>&nbsp;&nbsp;
									</c:forEach>										
									</c:if>
									<c:if test="${apFormTpl.isApprPeriod eq 'A'}">
									<c:forEach var="apCode" items="${listApprPeriodCd}">
										<c:if test="${apCode.codeId eq apFormTpl.apprPeriodCd}">
									<label>
											<input type="radio" class="radio" title="${apCode.codeName}" name="apprPeriodCd"
													value="${apCode.codeId}" checked/>${apCode.codeName}
										</label>&nbsp;&nbsp;
										</c:if>
									</c:forEach>
									</c:if>
									
								</td>
							</tr>

							<tr>
								<th scope="row"><ikep4j:message pre="${preView}" key="apprTypeCd" /><!--   : ${apFormTpl.apprTypeCd},${apFormTpl.isApprType}--></th>
								<td colspan=3>
								<!--${apDoc.apprTypeCd}-->
								
									<c:forEach var="apCode" items="${listFormTypeCd}">
										
										<c:if test="${apCode.codeId eq apDoc.apprTypeCd}">
										<label>${apCode.codeName}
											<!--  <input type="radio" class="radio" title="${apCode.codeName}" name="apprTypeCd"
													value="${apCode.codeId}" <c:if test="${apCode.codeId eq apDoc.apprTypeCd}">checked</c:if>/>${apCode.codeName}-->
										</label>&nbsp;&nbsp;
										</c:if>
										
									</c:forEach>
									<c:if test="${apFormTpl.isApprType eq 'U'}">
									
									<c:forEach var="apCode" items="${listFormTypeCd}">
										<label>
											<input type="radio" class="radio" title="${apCode.codeName}" name="apprTypeCd"
													value="${apCode.codeId}" <c:if test="${apCode.codeId eq apFormTpl.apprTypeCd}">checked</c:if>/>${apCode.codeName}
										</label>&nbsp;&nbsp;
									</c:forEach>										
									</c:if>
									<c:if test="${apFormTpl.isApprType eq 'A'}">
									<c:forEach var="apCode" items="${listFormTypeCd}">
										<c:if test="${apCode.codeId eq apFormTpl.apprTypeCd}">
									<label>
											<input type="radio" class="radio" title="${apCode.codeName}" name="apprTypeCd"
													value="${apCode.codeId}" checked/>${apCode.codeName}
										</label>&nbsp;&nbsp;
										</c:if>
									</c:forEach>
									</c:if>
								</td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre="${preView}" key="receive" /></th>
								<td >
									<select id="workers" name="addrGroupList" multiple="multiple" size="5" style="width:300px;height:50px;">
									<c:forEach var="item1" items="${apReceive}">
									<option value="${item1.receiveId}" workerName="${item1.userName}" jobTitle="${item1.jobPositionName}" teamName="${item1.teamName}">${item1.userName}/${item1.jobPositionName}/${item1.teamName}</option>
									</c:forEach></select>
								</td>
								<th scope="row"><ikep4j:message pre="${preView}" key="authUser" /></th>
								<td >
									<select id="workers1" name="addrGroupList1" multiple="multiple" size="5" style="width:300px;height:50px;">
									<c:forEach var="item1" items="${apAuthUser}">
									<option value="${item1.authUserId}" workerName="${item1.userName}" jobTitle="${item1.jobPositionName}" teamName="${item1.teamName}">${item1.userName}/${item1.jobPositionName}/${item1.teamName}</option>
									</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre="${preView}" key="title" /> <!--  : ${apFormTpl.isApprTitle}--></th>
								<td colspan=3>${apDoc.apprTitle}
									
									<!--  onKeydown     backspace,enter 않되게 적용 -->
								</td>
								
								
								
							</tr>
							
							<tr>
								<th scope="row"><ikep4j:message pre="${preView}" key="attachApDoc" /></th>
								<td colspan="3" >
								
									
									<ul class="listStyle" id="processList5">
									
									<c:forEach var="item1" items="${apRelations}">
									
									<li><a href="javaScript:f_ViewApDocRelation('${item1.apprRefId}');">${item1.apprTitle}</a></li>
									</c:forEach>

									</ul>	
									
									
								</td>
							</tr>
						</tbody>
					</table>
					
					<table>
					<tr>
								<!--  <th scope="row">내용</th>-->
								<!--  <td colspan=4>-->
								<td >
									<b><ikep4j:message pre="${preView}" key="approvalDocData" /></b><br><br><br>${apDoc.apprDocData}									
								</td>
					</tr>
					</table>
				</div>
				
				<!--//blockDetail End-->
				<input type="hidden" name="registUserId" value="${user.userId}" />
				
				
				<!-- 수신처 테스트 -->
				<input type="hidden" name="etcName" id="etcName"/>
				<!-- 수신처 테스트 -->
				<!-- 열람권한지정 테스트 -->
				<input type="hidden" name="etcName1" id="etcName1"/>
				<!-- 열람권한지정 테스트 -->
				
				
				<input type="hidden" name="apprId" id="apprId" value="${apDoc.apprId}"/>
				<input type="hidden" name="instanceId" id="instanceId" value="${apDoc.instanceId}"/>
				<input type="hidden" name="insLogId" id="insLogId" value="${apDoc.insLogId}"/>
				<input type="hidden" name="activityId" id="activityId" value="${apDoc.activityId}"/>
				<input type="hidden" name="processId" id="processId" value="${apDoc.processId}"/>
				<input name="apprMessage" title="" type="hidden" value="" />
			 
             
             <input name="controlTabType" title="" type="hidden" value="0:0:0:0" />
             <input name="controlType" title="" type="hidden" value="ORG" />
             <input name="selectType" title="" type="hidden" value="ALL" />
             <input name="selectMaxCnt" title="" type="hidden" value="10" />
             <input name="searchSubFlag" title="" type="hidden" value="" />
             <select name="addrGroupList2" multiple="multiple" size="5" style="width:300px;height:50px;display:none;" id="workers2">
             <c:forEach var="item" items="${apProcess}">
			 <c:choose>
						<c:when test="${item.apprOrder eq 0 }">
						</c:when>
						<c:otherwise>
							<option value="0^${item.apprUserId}" workerName="<c:if test="${item.apprType eq 0 }">결재</c:if><c:if test="${item.apprType eq 1 }">합의</c:if> ${item.userName}" jobTitle="${item.jobPositionName}" teamName="${item.teamName}">${item.userName}/${item.jobPositionName}/${item.teamName}</option>
						</c:otherwise> 
			</c:choose>             
			</c:forEach>
             </select>
             
             <input name="apprLineType" title="" type="hidden" value="${apDoc.apprLineType}" />
             <input name="approvalYn" title="" type="hidden" value="" />
             <input name="decisionYn" title="" type="hidden" value="" />
             <input name="discussCd" title="" type="hidden" value="${apFormTpl.discussCd}" />
             <textarea 	id="contentDiv" style="width:300px;height:50px;display:none;">${apDoc.apprDocData}</textarea>									
             
</form:form>

	
	
<!--의견입력 팝업 [S]-->
<div id="dialog" title="결재/합의 의견입력" style="display:none">
	<form name="messageAddDialog" action="">
		<textarea 	name="apprMessage" cols="49" rows="7"  title="의견" ></textarea>
	</form>
</div>
<!--의견입력 팝업 [E]-->
</body>
</html>