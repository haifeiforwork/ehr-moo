<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preButton"  	value="ui.approval.collaboration.button" />
<c:set var="preDefault"  	value="ui.approval.collaboration.default" />
<c:set var="preMessage"  	value="ui.approval.collaboration.message" />
<c:set var="preTitle"  		value="ui.approval.npd.view.title" />
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>   
<script type="text/javascript" src="<c:url value='/base/js/zeroclipboard/ZeroClipboard.js'/>"></script> 
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>
<script type="text/javascript">

<!--
var ecmFlg = "N";
(function($){
	
	var cachedTdObj;
	
	setRcvDeptGroup = function(data) {
		var groupName = "";
		var groupId = "";
		
		$jq.each(data, function() {
			
			groupName = $jq.trim(this.name);
			groupId = $jq.trim(this.code);	
			
			alert( "선택한 부서( "+ groupName + " )으로 선택이 완료되었습니다.");
			$jq("#sndReviewDeptId").val(groupId);
			$jq("#sndReviewDeptNm").val(groupName);
		});
	};
	
	//저장
	save = function(){
		var url = "<c:url value='/approval/collaboration/npd/createNewProductDev.do'/>";
		if('${viewMode}' == "modify") {
			url = "<c:url value='/approval/collaboration/npd/updateNewProductDev.do'/>";
		}
		
		$("#npdForm").find("select[name='devReqShareDeptIdList']").find("option").attr("selected", true);
		$("#npdForm").attr( "action", url);
		$("#npdForm").submit();
	}
	
	// 접수
	receipt = function() {
		
		if(confirm("<ikep4j:message pre='${preMessage}' key='receiptItem'/>")) {
			
			$jq.ajax({
				url : iKEP.getContextRoot() + "/approval/collaboration/npd/ajaxReceiptNewProductDev.do",
				type : "post",
				data : $("#npdForm").serialize(),
				loadingElement : "",
				success : function(data, textStatus, jqXHR) {
					
					alert("<ikep4j:message pre='${preMessage}' key='receiptSuceess' />");
					var url = "<c:url value='/approval/collaboration/npd/editNewProductDevView.do'/>";
					 
					$("#moveForm").attr( "action", url);
					$("#moveForm").submit();
					return false;
				},
				error : function(jqXHR, textStatus, errorThrown) {
					var arrException = $jq.parseJSON(jqXHR.responseText).exception;
					alert(arrException[0].defaultMessage);
				}
			});
		}
	}
	
	// 반려문서 초기화
	initRejct = function() {
		
		if(confirm("<ikep4j:message pre='${preMessage}' key='receiptItem'/>")) {
			
			$jq.ajax({
				url : iKEP.getContextRoot() + "/approval/collaboration/npd/ajaxInitRejctNewProductDev.do",
				type : "post",
				data : $("#npdForm").serialize(),
				loadingElement : "",
				success : function(data, textStatus, jqXHR) {
					
					alert("<ikep4j:message pre='${preMessage}' key='receiptSuceess' />");
					var url = "<c:url value='/approval/collaboration/npd/editNewProductDevView.do'/>";
					 
					$("#moveForm").attr( "action", url);
					$("#moveForm").submit();
					return false;
				},
				error : function(jqXHR, textStatus, errorThrown) {
					var arrException = $jq.parseJSON(jqXHR.responseText).exception;
					alert(arrException[0].defaultMessage);
				}
			});
		}
	}
	
	// 접수자변경
	changeReceipt = function() {
		
		if(confirm("<ikep4j:message pre='${preMessage}' key='receiptItem'/>")) {
			
			$jq.ajax({
				url : iKEP.getContextRoot() + "/approval/collaboration/npd/ajaxChangeReceiptNewProductDev.do",
				type : "post",
				data : $("#npdForm").serialize(),
				loadingElement : "",
				success : function(data, textStatus, jqXHR) {
					
					alert("<ikep4j:message pre='${preMessage}' key='receiptSuceess' />");
					var url = "<c:url value='/approval/collaboration/npd/editNewProductDevView.do'/>";
					 
					$("#moveForm").attr( "action", url);
					$("#moveForm").submit();
					return false;
				},
				error : function(jqXHR, textStatus, errorThrown) {
					var arrException = $jq.parseJSON(jqXHR.responseText).exception;
					alert(arrException[0].defaultMessage);
				}
			});
		}
	}
	// 승인
	approve = function() {
		
		var confirmMessage = "<ikep4j:message pre='${preMessage}' key='approveItem'/>";
		var succMessage = "<ikep4j:message pre='${preMessage}' key='approveSuceess' />";
		
		<c:if test="${npdPermission.drafter eq true}">
			confirmMessage = "<ikep4j:message pre='${preMessage}' key='approveItem2'/>";
			succMessage = "<ikep4j:message pre='${preMessage}' key='approveSuceess2' />";
		</c:if>
		
		if(confirm( confirmMessage)) {
			
			$jq.ajax({
				url : iKEP.getContextRoot() + "/approval/collaboration/npd/ajaxApproveNewProductDev.do",
				type : "post",
				data : $("#npdForm").serialize(),
				loadingElement : "",
				success : function(data, textStatus, jqXHR) {
					alert( succMessage);
					var url = "<c:url value='/approval/collaboration/npd/editNewProductDevView.do'/>";
					 
					$("#moveForm").attr( "action", url);
					$("#moveForm").submit();
					return false;
				},
				error : function(jqXHR, textStatus, errorThrown) {
					var arrException = $jq.parseJSON(jqXHR.responseText).exception;
					alert(arrException[0].defaultMessage);
				}
			});
		}
	}
	
	// 승인 및 저장
	approveWithSave = function() {
		
		if( $("#npdForm").valid() == false) {
			return;
		}
		
		var confirmMessage = "<ikep4j:message pre='${preMessage}' key='approveItem'/>";
		var succMessage = "<ikep4j:message pre='${preMessage}' key='approveSuceess' />";
		
		<c:if test="${npdPermission.drafter eq true}">
			confirmMessage = "<ikep4j:message pre='${preMessage}' key='approveItem2'/>";
			succMessage = "<ikep4j:message pre='${preMessage}' key='approveSuceess2' />";
		</c:if>
		
		if(confirm( confirmMessage)) {
			
			$jq.ajax({
				url : iKEP.getContextRoot() + "/approval/collaboration/npd/ajaxApproveWithSave.do",
				type : "post",
				data : $("#npdForm").serialize(),
				loadingElement : "",
				success : function(data, textStatus, jqXHR) {
					alert( succMessage);
					var url = "<c:url value='/approval/collaboration/npd/editNewProductDevView.do'/>";
					 
					$("#moveForm").attr( "action", url);
					$("#moveForm").submit();
					return false;
				},
				error : function(jqXHR, textStatus, errorThrown) {
					var arrException = $jq.parseJSON(jqXHR.responseText).exception;
					alert(arrException[0].defaultMessage);
				}
			});
		}
	}
	
	
	
	// 반려
	reject = function() {
		
		iKEP.showDialog({
			title: "<ikep4j:message pre='${preTitle}' key='rejectReason' />",
			url: "<c:url value='/approval/collaboration/npd/rejectNewProductDevPopView.do?mgntNo=' />"+ "${newProductDev.mgntNo}",
			modal: true,
			width: 700,
			height: 240,
			callback : function(_p) {
				
				var url = "<c:url value='/approval/collaboration/npd/editNewProductDevView.do'/>";
				 
				$("#moveForm").attr( "action", url);
 				$("#moveForm").submit();
 				return false;
			}
		});
	}
	
	// 요구일정수정
	updateReqScheduleCd = function() {
		
		iKEP.showDialog({
			title: "<ikep4j:message pre='${preTitle}' key='reqScheduleCd' />",
			url: "<c:url value='/approval/collaboration/npd/reqScheduleCdPopView.do?mgntNo=' />"+ "${newProductDev.mgntNo}",
			modal: true,
			width: 700,
			height: 240,
			callback : function(_p) {
				
				var url = "<c:url value='/approval/collaboration/npd/editNewProductDevView.do'/>";
				 
				$("#moveForm").attr( "action", url);
 				$("#moveForm").submit();
 				return false;
			}
		});
	}
	
	// 삭제
	delNewProductDev = function() {
		
		if(confirm("<ikep4j:message pre='${preMessage}' key='deleteItem'/>")) {
			
			$jq.ajax({
				url : iKEP.getContextRoot() + "/approval/collaboration/npd/ajaxDeleteNewProductDev.do",
				type : "post",
				data : $("#npdForm").serialize(),
				loadingElement : "",
				success : function(data, textStatus, jqXHR) {
					alert("<ikep4j:message pre='${preMessage}' key='deleteSuccess' />");
					var url = "<c:url value='/approval/collaboration/npd/editNewProductDevView.do'/>";
					goList();
					return false;
				},
				error : function(jqXHR, textStatus, errorThrown) {
					var arrException = $jq.parseJSON(jqXHR.responseText).exception;
					alert(arrException[0].defaultMessage);
				}
			});
			
		}
	}
	
	// 결과파일
	rsltFile = function() {
	
		iKEP.showDialog({
			title: "<ikep4j:message pre='${preTitle}' key='rsltFile' />",
			url: "<c:url value='/approval/collaboration/npd/rsltFileNewProductDevPopView.do?mgntNo=' />"+ "${newProductDev.mgntNo}" + "&rsltFileReadYn="+ "${newProductDev.rsltFileReadYn}" ,
			modal: true,
			width: 700,
			height: 250,
			callback : function() {
				updateRsltFile();
 				return false;
			}
		});
	}
	// 결과파일수정
	updateRsltFile = function() {
		
		var fileItemId = $("input[name='rsltFileItemId']").val();
		var isEcmRoll ="${npdPermission.ecmRoll}";
		
		var targetUrl = "<c:url value='/approval/collaboration/npd/editFilePopViewActiveX.do?fileEditType=rslt&mgntNo='/>" + "${newProductDev.mgntNo}" +"&rsltFileItemId=" + fileItemId;
		if( isEcmRoll == "true"){
			targetUrl = "<c:url value='/approval/collaboration/npd/editFilePopViewEcm.do?fileEditType=rslt&mgntNo='/>" + "${newProductDev.mgntNo}" +"&rsltFileItemId=" + fileItemId;
		}
		
		iKEP.showDialog({
			title: "<ikep4j:message pre='${preTitle}' key='rsltFile' />",
			url: targetUrl,
			modal: true,
			width: 700,
			height: 350,
			callback : function(_p) {
				var jsonDatas = $jq.parseJSON(_p);
				var datas =jsonDatas.datas;
				$("input[name='rsltFileItemId']").val( datas.fileItemId);
 				return false;
			}
		});
	}
	
	// 파일팝업 ActiveX
	openPopActiveX = function() {
		var fileItemId = $("input[name='reqFileItemId']").val();
		var targetUrl = "<c:url value='/approval/collaboration/npd/editFilePopViewActiveX.do?fileEditType=req&mgntNo='/>" + "${newProductDev.mgntNo}" +"&reqFileItemId=" + fileItemId;
		
		iKEP.showDialog({
			title: "<ikep4j:message pre='${preTitle}' key='fileItemId' />",
			url: targetUrl,
			modal: true,
			width: 700,
			height: 370,
			callback : function(_p) {
				var jsonDatas = $jq.parseJSON(_p);
				var datas =jsonDatas.datas; 
				// 파일 저장이 완료된후 유첨자료 화면에 뿌림
				
				$("input[name='reqFileItemId']").val( datas.fileItemId);
				$("#fileDownload").empty();
				var uploaderOptions = {
						   files : datas.fileDataListJson, 
						   isUpdate : false 
						}; 
						        
			    new iKEP.FileController(null, "#fileDownload", uploaderOptions);
			}
		});
	}
	// 파일팝업 Ecm
	openPopEcm = function() {
		
		var fileItemId = $("input[name='reqFileItemId']").val();
		var targetUrl = "<c:url value='/approval/collaboration/npd/editFilePopViewEcm.do?fileEditType=req&mgntNo='/>" + "${newProductDev.mgntNo}" +"&reqFileItemId=" + fileItemId;
		
		iKEP.showDialog({
			title: "<ikep4j:message pre='${preTitle}' key='fileItemId' />",
			url: targetUrl,
			modal: true,
			width: 800,
			height: 370,
			callback : function(_p) {
				var jsonDatas = $jq.parseJSON(_p);
				var datas =jsonDatas.datas; 
				// 파일 저장이 완료된후 유첨자료 화면에 뿌림
				$("input[name='reqFileItemId']").val( datas.fileItemId);
				$("#fileDownload").empty();
				var uploaderOptions = {
						   files : datas.fileDataListJson, 
						   isUpdate : false 
						}; 
						        
			    new iKEP.FileController(null, "#fileDownload", uploaderOptions);
			}
		});
	}
	
	ubiOpen = function() {
		
		var ubiUrl = "<c:url value='/approval/collaboration/npd/UbiReport.do?mgntNo='/>" + "${newProductDev.mgntNo}";
		
		window.open( ubiUrl, 'ubiReport', 'menubar=no, status=no, toolbar=no, location=no, resizable=yes');
	}
	
	// 목록으로 가기
	goList = function(){
		 
		var url = "<c:url value='/approval/collaboration/npd/listNewProductDevView.do'/>";
		$("#moveForm").attr( "action", url);
		$("#moveForm").submit();
		return false;
	}
	
	setUser = function(data) {
		
		var managerName = "";
		var jobTitleName = "";
		var teamName = "";
		var userId = "";
		
		$jq.each(data, function() {
			
			managerName = $jq.trim(this.userName);
			jobTitleName = $jq.trim(this.jobTitleName);
			teamName = $jq.trim(this.teamName);
			userId = $jq.trim(this.empNo);	
			
// 			alert( managerName + "으로 선택이 완료되었습니다. ");
			alert( "선택한 임직원( "+ managerName + " )으로 선택이 완료되었습니다.");
			cachedTdObj.find('.searchUserName').val( managerName);
			cachedTdObj.find('.searchUserId').val( userId);
		});
	};
	
	$(document).ready(function() {  
	
		if(window.parent.topScroll != null) {
			window.parent.topScroll(); 	
		} 
		
		var defaultValidRule = {   
				rules  : {},
				messages : {},
				submitHandler : function(form) {
		    	
		    	if(confirm("<ikep4j:message pre='${preMessage}' key='save'/>")) {
		    		if("${newProductDev.processGroupNo}" == "2") {
		    			var frm =$("#npdForm");
		    			var reqItemCD = frm.find("input[name='reqItemCd']:checked").val();
		    			var reqItemEtcTxt = frm.find("input[name='reqItemEtcTxt']").val();
		    			
		    			if( reqItemCD  == "40" && reqItemEtcTxt == "") {
		    				alert("요청사항을 '기타' 선택시 요청사항 기타내역은 필수 입니다. ");
		    				frm.find("input[name='reqItemEtcTxt']").focus();
		    				return;
		    			}
		    			var reqScheduleCd = frm.find("input[name='reqScheduleCd']:checked").val();
		    			var reqScheduleLimitDate = frm.find("input[name='reqScheduleLimitDate']").val();
		    			
		    			if( reqScheduleCd == "50" && reqScheduleLimitDate =="") {
		    				alert("요구일정을 '기타' 선택시 요구일정 기타내역은 필수 입니다. ");
		    				frm.find("input[name='reqScheduleLimitDate']").focus();
		    				return;
		    			}
		    			
		    		}
	    			writeSubmit(form);
				}
		    }
		};
		
		var requredMsg = "<ikep4j:message pre='${preMessage}' key='required'/>";
		var rangeLength100Msg = "<ikep4j:message pre='${preMessage}' key='rangelength.100'/>";
		var rangeLength1000Msg = "<ikep4j:message pre='${preMessage}' key='rangelength.1000'/>";
		var rangeLength2000Msg = "<ikep4j:message pre='${preMessage}' key='rangelength.2000'/>";
		
		var reqValidRule = {
				rules  : {
					companyCode : {required : true},
					productName : {required : true, rangelength : [1, 100] },
					productUsage : {required : true, rangelength : [1, 100] },
					custName : {required : true, rangelength : [1, 100] },
					reqApprEmpNo : {required : true}
				},
				messages : {
					companyCode : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='companyCode'/>" + requredMsg },
					productName : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='productName'/>"+ requredMsg, rangelength : "<ikep4j:message pre='${preTitle}' key='productName'/>" + rangeLength100Msg },
					productUsage : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='productUsage'/>"+ requredMsg, rangelength : "<ikep4j:message pre='${preTitle}' key='productUsage'/>" + rangeLength100Msg},
					custName : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='custName'/>"+ requredMsg, rangelength : "<ikep4j:message pre='${preTitle}' key='custName'/>" + rangeLength100Msg},
					reqApprEmpNo : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='apprUser'/>"+ requredMsg }
				}
		}
		var tcsValidRule = { 
				rules  : {
					tcsApprEmpNo : {required : true},
					sndReviewDeptId : {required : true},
					compAskSample : {required : false, rangelength : [1, 1000]},
					reqItemCd : {required : true},
					reqScheduleCd : {required : true},
					qualityAnalysis : {required : false, rangelength : [1, 2000]},
					etcRequest : {required : false, rangelength : [1, 2000]}
				},
				messages : {
					tcsApprEmpNo : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='apprUser'/>"+ requredMsg},
					sndReviewDeptId : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='sndReviewDeptNm'/>"+ requredMsg},
					compAskSample : {direction : "top", rangelength : "<ikep4j:message pre='${preTitle}' key='compAskSample'/>" + rangeLength1000Msg},
					reqItemCd : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='reqItemCd'/>"+ requredMsg},
					reqScheduleCd : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='reqScheduleCd'/>"+ requredMsg},
					qualityAnalysis : {direction : "top", rangelength : "<ikep4j:message pre='${preTitle}' key='qualityAnalysis'/>" + rangeLength2000Msg},
					etcRequest : {direction : "top", rangelength : "<ikep4j:message pre='${preTitle}' key='etcRequest'/>" + rangeLength2000Msg}
				}
		};
		var sndRcvValidRule = { 
				rules  : {
					sndRcvApprEmpNo : {required : true},
					sndReviewOpinion :{required : true, rangelength : [1, 1000]}
				},
				messages : {
					sndRcvApprEmpNo : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='apprUser'/>"+ requredMsg},
					sndReviewOpinion : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='sndReviewOpinion'/>"+ requredMsg, rangelength : "<ikep4j:message pre='${preTitle}' key='sndReviewOpinion'/>" + rangeLength1000Msg}
				}
		};
		var sndRsltValidRule = { 
				rules  : {
					sndRsltApprEmpNo : {required : true},
					sndReviewResult :{required : true, rangelength : [1, 1000]}
				},
				messages : {
					sndRsltApprEmpNo : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='apprUser'/>"+ requredMsg},
					sndReviewResult : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='sndReviewResult'/>"+ requredMsg, rangelength : "<ikep4j:message pre='${preTitle}' key='sndReviewResult'/>" + rangeLength1000Msg}
				}
		};
		
		var formValidRule = {};
		
		if("${newProductDev.processGroupNo}" == "1") {
			formValidRule = $.extend( defaultValidRule, reqValidRule );
		}else if("${newProductDev.processGroupNo}" == "2") {
			formValidRule = $.extend( defaultValidRule, reqValidRule, tcsValidRule );
		}else if("${newProductDev.processGroupNo}" == "3") {
			formValidRule = $.extend( defaultValidRule, sndRcvValidRule);
		}else if("${newProductDev.processGroupNo}" == "4") {
			formValidRule = $.extend( defaultValidRule, sndRsltValidRule);
		}
		
		new iKEP.Validator("#npdForm", formValidRule);
		
		writeSubmit = function(targetForm){
		
			$("body").ajaxLoadStart();
			targetForm.submit();
			return false;
		};
		
	    var datepickerOpts = {
	    		dateFormat: 'yy.mm.dd', 
	    		yearRange:'c-20:c+10',
	    		beforeShow: function(input) {
	    		    var i_offset= $(input).offset(); //클릭된 input의 위치값 체크
	    		    setTimeout(function(){
	    		       $('#ui-datepicker-div').css({'top':i_offset.top -170});      //datepicker의 div의 포지션을 강제로 input 위치에 그리고 좌측은 모바일이여서 작기때문에 무조건 10px에 놓았다.
	    		    });
	    		}
	   		};
	    $(".calDate").datepicker( datepickerOpts).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
	    // 사람찾기 - 검토자, 승인자 변경
	    $jq('.searchUserName').keypress( function(event) {
			cachedTdObj = $(this).parent("div");
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchUser(event, "N", setUser);			
		});
	    
	 	// [검색-부서선택] Start =====================================
	 	$jq("#addReadPermissionButton").click(function() {
			// 조직도 팝업 테스트

			var items = [];
			var $sel = $jq("#npdForm").find("[name=devReqShareDeptIdList]");
			
			$jq.each($sel.children(), function() {
				items.push($jq.data(this, "data"));
			});

			var callback = function(result){
				$sel.empty();
				$jq.each(result, function() {

					var tpl = "";
					
					switch(this.type) {
						case "group" : tpl = "addrBookItemGroup"; break;
						case "user" : tpl = "addrBookItemUser"; break;
						case "common" : tpl = "addrBookItemGroup"; break;
					}
					
					if(this.type=="group"){
						this.code= this.code;
					}else if(this.type=="common"){
						this.code="C:"+this.code;
					}else{
						this.id ="U:"+this.id;
					}
					
					var $option = $jq.tmpl(tpl, this).appendTo($sel);

					$jq.data($option[0], "data", this);
		
				})
			};
			iKEP.showAddressReaderBook(callback, items, {selectType:"group", selectElement:$sel, isAppend:false, tabs:{common:1}});	
		});
	 	$jq("#deleteReadPermissionButton").click(function(event) {
			event.preventDefault();
			var $rPermissionList=$jq('#devReqShareDeptIdList');
			$jq('option:selected',$rPermissionList).remove();

		});
	 	$jq.template("addrBookItemGroup", "<option value='\${code}' >\${name}</option>");
	 	setCallback = function(result){
			var $sel = $jq("#npdForm").find("[name=devReqShareDeptIdList]");
				$jq.each(result, function() {
					
					var tpl = "";
					
					switch(this.type) {
						case "group" : tpl = "addrBookItemGroup"; break;
						case "user" : tpl = "addrBookItemUser"; break;
						case "common" : tpl = "addrBookItemGroup"; break;
					}
					
					if(this.type=="group"){
						this.code= this.code;
					}else if(this.type=="common"){
						this.code="C:"+this.code;
					}else{
						this.id ="U:"+this.id;
					}
					
					var $option = $jq.tmpl(tpl, this).appendTo($sel);

					$jq.data($option[0], "data", this);
		
				})
			};
		// [검색-부서선택] End =====================================
		
			
			
		// [검색-수신부서선택]  ================== Start
		$jq('#addrGroupSearchBtn').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#sndReviewDeptNm').trigger("keypress");
		});
		
		$('#addrGroupBtn').click( function() {
			
			//파라미터(콜백함수, 전송파라미터, 팝업옵션)
			iKEP.showAddressBook(setRcvDeptGroup, [], {selectType:"group", isAppend:true, selectMaxCnt:1});
		});
		
		$jq('#sndReviewDeptNm').keypress( function(event) {
			
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchGroup(event, "N", setRcvDeptGroup);
		});
		
		$jq('#btnGroupDeleteControl').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#sndReviewDeptId').val('');
			$jq('#sndReviewDeptNm').val('');
		});
		// [검색-수신부서선택]  ================== End
		
		// 권한별 화면 UI변경
		$("input[name='reqItemCd']").change(function(){
			var val = $(this).val();
			if( val == 40) {
				$("input[name='reqItemEtcTxt']").attr("disabled", false).removeClass("inputReadOnly");
			}else{
				$("input[name='reqItemEtcTxt']").attr("disabled", true).addClass("inputReadOnly");
			}
		});
		// 요구일정
		$("input[name='reqScheduleCd']").change(function(){
			var val = $(this).val();
			if( val == 50) {
				$("input[name='reqScheduleLimitDate']").attr("disabled", false);
				$("#reqScheduleLimitDateCal").show();
			}else{
				$("input[name='reqScheduleLimitDate']").attr("disabled", true);
				$("#reqScheduleLimitDateCal").hide();
			}
		});
		
		var reqFlowViewEnale = "${npdPermission.reqViewActive}";
		var tcsModiEnale = "${npdPermission.tcsViewActive}";
		var sndRcvModiEnale = "${npdPermission.sndRcvViewActive}";
		var sndRsltModiEnale = "${npdPermission.sndRsltViewActive}";
		var approveBtnActive = "${npdPermission.approveBtnActive}";
		var rejectBtnActive = "${npdPermission.rejectBtnActive}";
// 		alert( reqFlowViewEnale);
		if( reqFlowViewEnale == 'true') {
			$(".reqElement").each(function(){ 
				if( this.tagName == 'INPUT' ) {
					var type = $(this).attr('type').toLowerCase();
					if( type == 'checkbox' || type == 'radio' ) {
						$(this).attr("disabled", false).removeClass("inputReadOnly");	
					}else{
						$(this).attr("readonly", false).removeClass("inputReadOnly");
					}
					
				} else {
					$(this).attr("readonly", false).removeClass("inputReadOnly");	
				}
			});
			$(".reqEnableArea").show();
			$(".reqDisableArea").hide();
			
			if( tcsModiEnale == "false" ) {
				
				if('${viewMode}' != "modify" || approveBtnActive == "true" ) {
					
					$(".reqOnlyEnableArea").show();
					$(".reqOnlyDisableArea").hide();
				}
			}
		}
		
		if( tcsModiEnale == "true") {
			
			$(".tcsEnableArea").show();
			$(".tcsDisableArea").hide();
			$(".tcsElement").each(function(){ 
				if( this.tagName == 'INPUT' ) {
					var type = $(this).attr('type').toLowerCase();
					if( type == 'checkbox' || type == 'radio' ) {
						$(this).attr("disabled", false).removeClass("inputReadOnly");	
					}else{
						$(this).attr("readonly", false).removeClass("inputReadOnly");
					}
					
				} else if( this.tagName == 'SELECT'){
					
					$(this).attr("disabled", false);
				} else {
					$(this).attr("readonly", false).removeClass("inputReadOnly");	
				}
			});
			
			$("input[name='reqItemCd']:checked").trigger("change");
			$("input[name='reqScheduleCd']:checked").trigger("change");
			
			if( rejectBtnActive == "true") {
				
				$(".tcsOnlyEnableArea").show();
				$(".tcsOnlyDisableArea").hide();
			}
		}
		if( sndRcvModiEnale == "true") {
			showElement( "sndRcv");	
			if( rejectBtnActive == "true") {
				
				$(".sndRcvOnlyEnableArea").show();
				$(".sndRcvOnlyDisableArea").hide();
			}
		}
		
		if( sndRsltModiEnale == "true") {
			showElement( "sndRslt");
			if( rejectBtnActive == "true") {
				
				$(".sndRsltOnlyEnableArea").show();
				$(".sndRsltOnlyDisableArea").hide();
			}
		}
		
		function showElement( _type) {
			
			$("."+_type+"EnableArea").show();
			$("."+_type+"DisableArea").hide();
			$("."+_type+"Element").each(function(){ 
				if( this.tagName == 'INPUT' ) {
					var type = $(this).attr('type').toLowerCase();
					if( type == 'checkbox' || type == 'radio' ) {
						$(this).attr("disabled", false).removeClass("inputReadOnly");	
					}else{
						$(this).attr("readonly", false).removeClass("inputReadOnly");
					}
					
				} else if( this.tagName == 'SELECT'){
					
					$(this).attr("disabled", false);
				} else {
					$(this).attr("readonly", false).removeClass("inputReadOnly");	
				}
			});
		}
		
		<c:if test="${not empty fileDataListJson}">
		var uploaderOptions = {
		   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
		   isUpdate : false 
		}; 
		        
	    new iKEP.FileController(null, "#fileDownload", uploaderOptions);
	    </c:if>
		
	    $(".searchUserName").change(function(){
	    	var val = $(this).val();
	    	var idObj = $(this).parent("div").find(".searchUserId");
	    	var idval = $(this).parent("div").find(".searchUserId").val() || "";
	    	
	    	if( val == '' && idval) {
	    		alert("선택된 임직원이 삭제되었습니다.");
	    		idObj.val("");
	    	}
	    });
	   
	    function makeComma( val ) {

	    	var val = val + "";
	    	val = val.replace(/,/g, '');
	    	var len = val.length;
	    	var rstr = "";


	    	for( var i=len-1,count=0;i>=0;i--,count++ ) {
	    		if( count%3 == 0 && count != 0 ) rstr = ","+rstr;
	    		rstr = val.charAt(i)+rstr;
	    	}

	    	return rstr;

	    }
	    
	    $('.onlyNumb').blur(function () { 
	        var thisVal = $(this).val();
	    	$(this).val( makeComma(thisVal));
	    });
	    
	    // readonly일때 포커스 안가도록 처리
	    $(".readOnlyFocusBlur").focus(function(){
	    	
	    	var isReadOnlyEl = $(this).attr("readonly") || '';
	    	if( isReadOnlyEl.toLowerCase() == 'readonly') {
	    		$(this).blur();
	    	}
	    });
	});
})(jQuery);  

//-->
</script>
<script language="JScript" FOR="twe" EVENT="OnKeyDown(event)">
<!--
	/* 태그프리 에디터 줄바꿈 태그 P => br 로 변경하는 메소드 */
	if (!event.shiftKey && event.keyCode == 13){
		twe.InsertHtml("<br>");
		event.returnValue = true; <!-- Active Designer에서의 처리를 막음 -->
	}
	if (event.shiftKey && event.keyCode == 13){
		twe.InsertHtml("<p>");
		event.returnValue = true; <!-- Active Designer에서의 처리를 막음 -->
	}	
//-->
</script>

<style>
.nameArea {height: 40px; text-align: center;}
.container{width:950px;}
.clearMarPadd0{ margin: 0 !important; padding: 0 !important; border:0px !important; }
.w25percent{width:25% !important;}
.w30percent{width:30% !important;}
.w40percent{width:40% !important;}
.w50percent{width:50% !important;}
.w60percent{width:60% !important;}
.w90percent{width:90% !important;}
.w100percent{width:100% !important;}
.w20pix{width:20px; !important;}
.devReqTitleArea{text-align:center; width:60%; height: 100%; line-height: 50px; vertical-align: middle; font-size:30px; font-weight:bold;}
.floatLeft{float:left !important;}
.marginLeft10{margin-left:10px !important;}
.inputReadOnly{ border: 0 !important; background: #fff !important;}
.inputReadOnly2{ background: #fff;}
.container INPUT[type='text'] {width:100%;}
.inputNum{text-align:right !important;}
.thMinH{min-height: 28px !important;}
.paddingRight15{padding-right: 15px !important;}
</style>

<form id="moveForm" name="moveForm" action="">
	<input type="hidden" name="searchConditionString" value="${searchConditionString }" /> 
	<input type="hidden" name="mgntNo" value="${newProductDev.mgntNo}" />
	<input type="hidden" name="viewMode" value="modify" />
</form>
	
<div class="container"> 
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<c:if test="${viewMode eq 'modify'}">
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="ubiOpen();" ><span><ikep4j:message pre="${preButton}" key="ubiReport" /></span></a></li>
			</c:if>
			<c:if test="${npdPermission.ecmRoll ne true && npdPermission.reqViewActive eq true}">
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="openPopActiveX();" ><span><ikep4j:message pre="${preButton}" key="fileItemId" /></span></a></li>
			</c:if>
			<c:if test="${npdPermission.ecmRoll eq true && npdPermission.reqViewActive eq true}">
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="openPopEcm();" ><span><ikep4j:message pre="${preButton}" key="fileItemId" /></span></a></li>
			</c:if>
			<c:if test="${npdPermission.reReceiptBntActive eq true}">
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="changeReceipt();" ><span><ikep4j:message pre="${preButton}" key="receipt" /></span></a></li>
			</c:if>
			<c:if test="${npdPermission.initRejctBtnActive eq true}">
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="initRejct();" ><span><ikep4j:message pre="${preButton}" key="receipt" /></span></a></li>
			</c:if>
			<c:if test="${npdPermission.receiptBtnActive eq true}">
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="receipt();" ><span><ikep4j:message pre="${preButton}" key="receipt" /></span></a></li>
			</c:if>
			<c:if test="${npdPermission.saveBtnActive eq true}">
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="save();" ><span><ikep4j:message pre="${preButton}" key="save" /></span></a></li>
			</c:if>
			<c:if test="${npdPermission.deleteBtnActive eq true }">
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="delNewProductDev();" ><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
			</c:if>
			<c:if test="${npdPermission.approveBtnActive eq true}">
				<li>
					<c:if test="${npdPermission.saveBtnActive eq true}">
						<a class="button listBoardItemButton" href="javascript:;" onclick="approveWithSave();" >
					</c:if>
					<c:if test="${npdPermission.saveBtnActive eq false}">
						<a class="button listBoardItemButton" href="javascript:;" onclick="approve();" >
					</c:if>
					<c:if test="${npdPermission.drafter eq false}">
						<span><ikep4j:message pre="${preButton}" key="approval" /></span>
					</c:if>
					<c:if test="${npdPermission.drafter eq true}">
						<span><ikep4j:message pre="${preButton}" key="approval2" /></span>
					</c:if>
					</a>
				</li>
			</c:if>
			<c:if test="${npdPermission.rejectBtnActive eq true}">
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="reject();" ><span><ikep4j:message pre="${preButton}" key="deny" /></span></a></li>
			</c:if>
			<c:if test="${npdPermission.rejectResonBtnActive eq true}">
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="reject();" ><span><ikep4j:message pre="${preButton}" key="denyReason" /></span></a></li>
			</c:if>
			<li><a class="button listBoardItemButton" href="javascript:;" onclick="goList();"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
			<c:if test="${npdPermission.rsltFileBtnActive eq true || npdPermission.rsltFileViewActive eq true}">
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="rsltFile();" ><span><ikep4j:message pre="${preButton}" key="rsltFile" /></span></a></li>
			</c:if>
		 </ul>
	</div>
	<!--//blockButton End-->
	
	<h1 class="none"><ikep4j:message pre="${preTitle}" key="devRequest" /></h1> 
	
<form id="npdForm" name="npdForm" method="post" action="" enctype="multipart/form-data">
	<input type="hidden" name="mgntNo" value="${newProductDev.mgntNo}" /> 
	<input type="hidden" name="reqFileItemId" value="${newProductDev.reqFileItemId}" /> 
	<input type="hidden" name="rsltFileItemId" value="${newProductDev.rsltFileItemId}" /> 
	<input type="hidden" name="processStatus" value="${newProductDev.processStatus}" /> 
	<input type="hidden" name="apprStsCd" value="${newProductDev.apprStsCd}" /> 
	<input type="hidden" name="approveStsCd" value="" /> 
	<!-- ecmFile 변수 -->	
	<input type="hidden" name="ecmFileId" value="" /> 
	<input type="hidden" name="ecmFileName" value="" />
	<input type="hidden" name="ecmFilePath" value=""/>  
	<input type="hidden" name="ecmFileUrl1" value=""/> 
	<input type="hidden" name="ecmFileUrl2" value=""/>  
	
	<input type="hidden" name="processGroupNo" value="${newProductDev.processGroupNo}" />
	<!--  Title Area Start -->
	<div style="height:70px;">
		<div class="blockDetail w30percent floatLeft" >
			<table>
				<colgroup>
					<col width="25%"/>
					<col width="*" />
				</colgroup>
				<tbody>
					<!-- 분류No. -->
					<tr>
						<th class="textCenter"><ikep4j:message pre="${preTitle}" key="no" /></th>
						<td class="textCenter paddingRight15" >
							<spring:bind path="newProductDev.divisionNo">
								<input name="${status.expression}" type="text" value="${status.value}" class="reqElement inputReadOnly inputbox" readonly="readonly" />
							</spring:bind>
						</td>
					</tr>
					<!-- 관리번호 -->
					<tr>
						<th class="textCenter"><ikep4j:message pre="${preTitle}" key="mgntNo" /></th>
						<td>
							<div class="reqEnableArea" style="display:none;">
								<spring:bind path="newProductDev.companyCode">
									<select name="${status.expression}">
										<c:forEach var="code" items="${ c00010List}">
											<option value="${code.comCd}" <c:if test="${code.comCd eq status.value}">selected="selected"</c:if>>${code.comNm}</option>
										</c:forEach>
									</select>
								</spring:bind>
								<span>${newProductDev.mgntNo}</span>
							</div>
							<div class="reqDisableArea">
								<c:forEach var="code" items="${ c00010List}">
									<c:if test="${code.comCd eq newProductDev.companyCode}"><c:out value="${ code.comNm}"></c:out></c:if>
								</c:forEach>
								<span>${newProductDev.mgntNo}</span>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!--  title -->
		<div class="devReqTitleArea floatLeft">
			<span><ikep4j:message pre="${preTitle}" key="devRequest" /></span>
		</div>
	</div>
	<!--  Title Area End -->
	
	<!--blockDetail Start-->
	<div class="blockDetail">
			<!-- main1 Area Start -->
			<div class="main1Area">
				<table summary="<ikep4j:message pre="${preTitle}" key="summary" />">
					<caption></caption>
					<tbody> 
						<tr>
							<!-- 담당자 -->
							<th class="textCenter" style="width:80px;"><ikep4j:message pre="${preTitle}" key="reqEmpNm" /></th>
							<td class="w25percent">
								<spring:bind path="newProductDev.reqEmpNo">
									<input name="${status.expression}" type="hidden" value="${status.value}"  />
								</spring:bind>
								${ newProductDev.reqEmpNm}
							</td>
							
							<!-- 의뢰일 -->
							<th class="textCenter"><ikep4j:message pre="${preTitle}" key="reqDate" /></th>
							<td class="w25percent">
								${newProductDev.reqDate }
							</td>
							<!-- 의뢰부서 ( 결재 상태 ) -->
							<td rowspan="2" colspan="4" class="clearMarPadd0" >
								<table>
									<tr>
										<th rowspan="3" class="textCenter w20pix">의<br/>뢰<br/>부<br/>서</th>
										<th class="textCenter"><ikep4j:message pre="${ preTitle}" key="draft" /></th>
										<th class="textCenter"><ikep4j:message pre="${ preTitle}" key="review" /></th>
										<th class="textCenter"><ikep4j:message pre="${ preTitle}" key="approval" /></th>
									</tr>
									<tr>
										<!-- 의뢰부서:기안자  -->
										<td class="nameArea">
											<spring:bind path="newProductDev.reqDraftEmpNo">
												<input name="${status.expression}" type="hidden" value="${status.value}"  />
											</spring:bind>
											<p>${ newProductDev.reqDraftEmpNm}</p>
											<p>( ${ newProductDev.reqDraftDate} )</p>
										</td>
										<!-- 의뢰부서:검토자  -->
										<td class="nameArea paddingRight15">
											<div class="reqOnlyEnableArea" style="display:none;">
												<spring:bind path="newProductDev.reqReviewEmpNo">
													<input name="${status.expression}" type="hidden" class="searchUserId" value="${status.value}"  />
												</spring:bind>
												<spring:bind path="newProductDev.reqReviewEmpNm">
													<input name="${status.expression}" type="text" class="searchUserName inputbox" value="${status.value}" />
												</spring:bind>
											</div>
											<div class="reqOnlyDisableArea">
												<p>${ newProductDev.reqReviewEmpNm}</p>
												<p>
													<c:if test="${newProductDev.reqReviewDate != '' && newProductDev.reqReviewDate ne null}">( ${ newProductDev.reqReviewDate} )</c:if> 
												</p>
											</div>
										</td>
										<!-- 의뢰부서 :승인자 -->
										<td class="nameArea paddingRight15">
											<div class="reqOnlyEnableArea" style="display:none;">
												<spring:bind path="newProductDev.reqApprEmpNo">
													<input name="${status.expression}" type="hidden" class="searchUserId" value="${status.value}"  />
												</spring:bind>
												<spring:bind path="newProductDev.reqApprEmpNm">
													<input name="${status.expression}" type="text" class="searchUserName inputbox" value="${status.value}" />
												</spring:bind>
											</div>
											<div class="reqOnlyDisableArea">
												<p>${ newProductDev.reqApprEmpNm}</p>
												<p>
													<c:if test="${newProductDev.reqApprDate != '' && newProductDev.reqApprDate ne null}">( ${ newProductDev.reqApprDate} )</c:if> 
												</p>
											</div>
										</td>
									</tr>
									<!-- 의뢰 진행 상태 -->
									<tr>
										<td class="textCenter">
											<c:forEach var="code" items="${ c00005List}">
												<c:if test="${code.comCd eq newProductDev.reqDraftStsCd}">${code.charCol1}</c:if>
											</c:forEach>
										</td>
										<td class="textCenter">
											<c:forEach var="code" items="${ c00005List}">
												<c:if test="${code.comCd eq newProductDev.reqReviewStsCd and code.numCol1 eq 1}">${code.comNm}</c:if>
											</c:forEach>
										</td>
										<td class="textCenter">
											<c:forEach var="code" items="${ c00005List}">
												<c:if test="${code.comCd eq newProductDev.reqApprStsCd and code.numCol1 eq 1 }">${code.comNm}</c:if>
											</c:forEach>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<!-- 의뢰부서 -->
							<th class="textCenter"><ikep4j:message pre="${preTitle}" key="reqDeptNm" /></th>
							<td>
								<spring:bind path="newProductDev.reqDeptId">
									<input name="${status.expression}" type="hidden" value="${status.value}"  />
								</spring:bind>
								${ newProductDev.reqDeptNm}
							</td>
							
							<!-- 수신 -->
							<th class="textCenter"><ikep4j:message pre="${preTitle}" key="reqRcvDeptNm" /></th>
							<td>
								<spring:bind path="newProductDev.reqRcvDeptId">
									<input id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}"  />
								</spring:bind>
								${newProductDev.reqRcvDeptNm }
							</td>
						</tr>
						<tr>
							<!-- 담당자 -->
							<th class="textCenter"><ikep4j:message pre="${preTitle}" key="fstReviewEmpNm" /></th>
							<td>${newProductDev.tcsDraftEmpNm}</td>
							
							<!-- 1차검토일 -->
							<th class="textCenter"><ikep4j:message pre="${preTitle}" key="fstReviewDate" /></th>
							<td>${ newProductDev.fstReviewDate }</td>
							
							<!-- TCS팀 area Start -->
							<td rowspan="2" colspan="4" class="clearMarPadd0" >
								<table>
									<!-- 여기 -->
									<tr>
										<th rowspan="3" class="textCenter w20pix">T<br/>C<br/>S<br/>팀</th>
										<th class="textCenter"><ikep4j:message pre="${ preTitle}" key="draft" /></th>
										<th class="textCenter"><ikep4j:message pre="${ preTitle}" key="review" /></th>
										<th class="textCenter"><ikep4j:message pre="${ preTitle}" key="approval" /></th>
									</tr>
									<tr>
										<!-- TCS:기안자  -->
										<td class="nameArea" style="height: 40px;">
											<spring:bind path="newProductDev.tcsDraftEmpNo">
												<input name="${status.expression}" type="hidden" value="${status.value}"  />
											</spring:bind>
											<p>${ newProductDev.tcsDraftEmpNm}</p>
											<p>
												<c:if test="${newProductDev.tcsDraftDate != '' && newProductDev.tcsDraftDate ne null}">( ${ newProductDev.tcsDraftDate} )</c:if>
											</p>
										</td>
										<!-- TCS:검토자  -->
										<td class="nameArea paddingRight15">
											<div class="tcsOnlyEnableArea" style="display:none;">
												<spring:bind path="newProductDev.tcsReviewEmpNo">
													<input name="${status.expression}" type="hidden" class="searchUserId" value="${status.value}"  />
												</spring:bind>
												<spring:bind path="newProductDev.tcsReviewEmpNm">
													<input name="${status.expression}" type="text" class="searchUserName inputbox" value="${status.value}" />
												</spring:bind>
											</div>
											<div class="tcsOnlyDisableArea">
												<p>${ newProductDev.tcsReviewEmpNm}</p>
												<p>
													<c:if test="${newProductDev.tcsReviewDate != '' && newProductDev.tcsReviewDate ne null}">( ${ newProductDev.tcsReviewDate} )</c:if> 
												</p>
											</div>
										</td>
										
										<!-- TCS:승인자 -->
										<td class="nameArea paddingRight15">
											<div class="tcsOnlyEnableArea" style="display:none;">
												<spring:bind path="newProductDev.tcsApprEmpNo">
													<input name="${status.expression}" type="hidden" class="searchUserId" value="${status.value}"  />
												</spring:bind>
												<spring:bind path="newProductDev.tcsApprEmpNm">
													<input name="${status.expression}" type="text" class="searchUserName inputbox" value="${status.value}" />
												</spring:bind>
											</div>
											<div class="tcsOnlyDisableArea">
												<p>${ newProductDev.tcsApprEmpNm}</p>
												<p>
													<c:if test="${newProductDev.tcsApprDate != '' && newProductDev.tcsApprDate ne null}">( ${ newProductDev.tcsApprDate} )</c:if> 
												</p>
											</div>
										</td>
									</tr>
									<!-- TCS팀 진행 상태 -->
									<tr>
										<td class="textCenter" style="height: 20px;">
											<c:forEach var="code" items="${ c00005List}">
												<c:if test="${code.comCd eq newProductDev.tcsDraftStsCd}">${code.charCol1}</c:if>
											</c:forEach>
										</td>
										<td class="textCenter">
											<c:forEach var="code" items="${ c00005List}">
												<c:if test="${code.comCd eq newProductDev.tcsReviewStsCd and code.numCol1 eq 1}">${code.comNm}</c:if>
											</c:forEach>
										</td>
										<td class="textCenter">
											<c:forEach var="code" items="${ c00005List}">
												<c:if test="${code.comCd eq newProductDev.tcsApprStsCd and code.numCol1 eq 1}">${code.comNm}</c:if>
											</c:forEach>
											
										</td>
									</tr>
								</table>
							</td>
							<!-- TCS팀 area End -->
						</tr>
						<tr>
							<!-- 1차검토부서 -->
							<th class="textCenter"><ikep4j:message pre="${preTitle}" key="fstReviewDeptNm" /></th>
							<td>
								<spring:bind path="newProductDev.fstReviewDeptId">
									<input name="${status.expression}" type="hidden" value="${status.value}"  />
								</spring:bind>
								${newProductDev.fstReviewDeptNm }
							</td>
							
							<!-- 수신 : 2차검토부서  -->
							<th class="textCenter"><ikep4j:message pre="${preTitle}" key="sndReviewDeptNm" /></th>
							<td class="paddingRight15">
								<div class="tcsEnableArea" style="display:none;">
									<spring:bind path="newProductDev.sndReviewDeptId">
										<input id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}"  />
									</spring:bind>
									<spring:bind path="newProductDev.sndReviewDeptNm">
				                    	<input id="${status.expression}" name="${status.expression}" type="text" class="inputbox" value="${status.value}" size="20"/>
									</spring:bind>
									<a name="addrGroupSearchBtn" id="addrGroupSearchBtn" class="button_ic" href="#a">
										<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search' /></span>
									</a>
									<a id="addrGroupBtn" href="#a" class="button_ic">
										<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle"><ikep4j:message pre='${preButton}' key='address' /></span>
									</a>
									<a class="button_ic valign_bottom" href="#a" id="btnGroupDeleteControl">
										<span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="Delete" /><ikep4j:message pre='${preButton}' key='delete' /></span>
									</a>
								</div>
								<div class="tcsDisableArea">
									${newProductDev.sndReviewDeptNm }
								</div>
							</td>
						</tr>
						<tr>
							<!-- 제품명 -->
							<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="productName" /></th>
							<td class="paddingRight15">
								<spring:bind path="newProductDev.productName">
									<input name="${status.expression}" type="text" class="reqElement inputReadOnly inputbox" value="${status.value}" style="width:100%;" readonly="readonly" />
								</spring:bind>
							</td>
							<!-- 예상수량, 예상판가, 규격 -->
							<th class="textCenter"><ikep4j:message pre="${preTitle}" key="estQty" /></th>
							<td class="clearMarPadd0" colspan="5">
								<table>
									<colgroup>
										<col width="132px;"/>
										<col width="105px;" />
										<col width="122px;"/>
										<col width="55px;"/>
										<col width="*"/>
									</colgroup>
									<tbody>
										<tr>
											<!-- 예상수량 -->
											<td class="textRight paddingRight15">
												<spring:bind path="newProductDev.estQty">
													<input name="${status.expression}" type="text" style="ime-mode:disabled;" onKeyPress="if ((event.keyCode> 47) && (event.keyCode < 57)){event.returnValue=true;} else { event.returnValue=false;}" class="inputNum onlyNumb reqElement inputReadOnly inputbox" value="${status.value}" readonly="readonly"/>
												</spring:bind>
											</td>
											<!-- 예상판가 -->
											<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="estPrice" /></th>
											<td class="textRight paddingRight15">
												<spring:bind path="newProductDev.estPrice">
													<input name="${status.expression}" type="text" style="ime-mode:disabled;" onKeyPress="if ((event.keyCode> 47) && (event.keyCode < 57)){event.returnValue=true;} else { event.returnValue=false;}" class="inputNum onlyNumb reqElement inputReadOnly inputbox" value="${status.value}"  readonly="readonly" />
												</spring:bind>
											</td>
											<!-- 규격 -->
											<th class="textCenter"><ikep4j:message pre="${preTitle}" key="standard" /></th>
											<td class="textRight paddingRight15">
												<spring:bind path="newProductDev.standard">
													<input name="${status.expression}" type="text" class="reqElement inputReadOnly inputbox" value="${status.value}" readonly="readonly" />
												</spring:bind>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<!-- 제품용도 -->
							<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="productUsage" /></th>
							<td class="paddingRight15">
								<spring:bind path="newProductDev.productUsage">
									<input name="${status.expression}" type="text" class="reqElement inputReadOnly inputbox" value="${status.value}"  readonly="readonly" />
								</spring:bind>
							</td>
							<!-- 고객사명 -->
							<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="custName" /></th>
							<td colspan="5" class="paddingRight15">
								<spring:bind path="newProductDev.custName">
									<input name="${status.expression}" type="text" class="reqElement inputReadOnly inputbox" value="${status.value}" readonly="readonly" />
								</spring:bind>
							</td>
						</tr>
						<!-- 비교요청샘플 -->
						<tr>
							<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="compAskSample" /></th>
							<td colspan="7" class="paddingRight15">
								<spring:bind path="newProductDev.compAskSample">
									<input name="${status.expression}" type="text" value="${status.value}" class="tcsElement inputReadOnly inputbox" readonly="readonly" />
								</spring:bind>
							</td>
						</tr>
						<!-- 의뢰목적 -->
						<tr>
							<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="reqPurpose" /></th>
							<td colspan="7" class="paddingRight15">
								<spring:bind path="newProductDev.reqPurpose">
									<input name="${status.expression}" type="text" class="reqElement inputReadOnly inputbox" value="${status.value}" readonly="readonly" />
								</spring:bind>
							</td>
						</tr>
						<!-- 요청사항 -->
						<tr>
							<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="reqItemCd" /></th>
							<td colspan="7">
								<div class="floatLeft" style="width: 370px;">
									<spring:bind path="newProductDev.reqItemCd">
										<c:forEach var="code" items="${c00003List}">
											<input type="radio" class="tcsElement radio" disabled="disabled" name="${status.expression}" <c:if test="${code.comCd eq status.value}">checked="checked"</c:if> value="${code.comCd }" /> <span>${code.comNm}</span>
										</c:forEach>
									</spring:bind>
								</div>
								<div class="floatLeft">
									<spring:bind path="newProductDev.reqItemEtcTxt">
										<input name="${status.expression}" disabled="disabled" style="width: 467px;" class="inputReadOnly2 inputbox" type="text" value="${status.value}"/>
									</spring:bind>
								</div>
							</td>
						</tr>
						<!-- 요구일정 -->
						<tr>
							<th class="thMinH textCenter"><ikep4j:message pre="${preTitle}" key="reqScheduleCd" /></th>
							<td colspan="7">
								<div class="floatLeft" >
									<spring:bind path="newProductDev.reqScheduleCd">
										<c:forEach var="code" items="${c00004List}">
											<input type="radio" class="tcsElement radio" disabled="disabled" name="${status.expression}" <c:if test="${code.comCd eq status.value}">checked="checked"</c:if> value="${code.comCd }" /> <span>${code.comNm}</span>
										</c:forEach>
									</spring:bind>
								</div>
								<div class="floatLeft marginLeft10">
									<spring:bind path="newProductDev.reqScheduleLimitDate">
										<input name="${status.expression}" disabled="disabled" class="inputbox w20 datepicker calDate inputReadOnly2" type="text" size="50" value="${status.value}"/>
									</spring:bind>
							        <img id="reqScheduleLimitDateCal" style="display: none;" src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
								</div>
								<div class="floatRight">
									<c:if test="${npdPermission.reqScheduleCdBtnActive eq true}">
										<a class="button listBoardItemButton" href="javascript:;" onclick="updateReqScheduleCd();" ><span><ikep4j:message pre="${preButton}" key="reqScheduleCd" /></span></a>
									</c:if>
								</div>
								
							</td>
						</tr>
						<!-- 유첨자료 -->
						<tr>
							<th class="thMinH textCenter"><ikep4j:message pre="${preTitle}" key="reqFileItem" /></th>
							<td colspan="7"><div id="fileDownload"></div></td>
						</tr>
					</tbody> 
				</table>
			</div>
			<!-- main1 Area End -->
			
			<div class="mb20"></div>
			
			<!-- main2 Area Start -->
			<!-- 1. 분석목적 및 배경/ 2. 분석 요청사항-->
			<div class="blockDetail main2Area btnline">
				<ul style="margin-top:10px;">
					<li><h1><ikep4j:message pre="${preTitle}" key="main.analysis" /></h1></li>
					<!-- 가. 분석목적 -->
					<li style="padding: 10px; 10px; 20px; 20px;">
						<p><ikep4j:message pre="${preTitle}" key="analysisPurpose" /></p>
						<div style="margin-left:15px;">
							<spring:bind path="newProductDev.analysisPurpose">
								<textarea name="${status.expression}" class="reqElement addNbsp readOnlyFocusBlur" readonly="readonly" style="line-height:16px; overflow:auto; width:100%; height:120px; border:none; background:none;">${status.value}</textarea>
							</spring:bind>
						</div>
					</li>
					<!-- 나.영업정보 -->
					<li style="padding: 10px; 10px; 20px; 20px;">
						<p><ikep4j:message pre="${preTitle}" key="businessInfo" /></p>
						<div style="margin-left:15px;">
							<spring:bind path="newProductDev.businessInfo">
								<textarea name="${status.expression}" class="reqElement addNbsp readOnlyFocusBlur" readonly="readonly" style="line-height:16px; overflow:auto; width:100%; height:120px; border:none; background:none;">${status.value}</textarea>
							</spring:bind>
						</div>
					</li>
					<!-- 가.품질 분석 항목-->
					<li><h1><ikep4j:message pre="${preTitle}" key="main.quality" /></h1></li>
					<li style="padding: 10px; 10px; 20px; 20px;">
						<p><ikep4j:message pre="${preTitle}" key="qualityAnalysis" /></p>
						<div style="margin-left:15px;">
							<spring:bind path="newProductDev.qualityAnalysis">
								<textarea name="${status.expression}" class="tcsElement addNbsp readOnlyFocusBlur" readonly="readonly" style="line-height:16px; overflow:auto; width:100%; height:120px; border:none; background:none;">${status.value}</textarea>
							</spring:bind>
						</div>
					</li>
					<!-- 나.기타요청 사항-->
					<li style="padding: 10px; 10px; 20px; 20px;">
						<p><ikep4j:message pre="${preTitle}" key="etcRequest" /></p>
						<div style="margin-left:15px;">
							<spring:bind path="newProductDev.etcRequest">
								<textarea name="${status.expression}" class="tcsElement addNbsp readOnlyFocusBlur" readonly="readonly" style="line-height:16px; overflow:auto; width:100%; height:120px; border:none; background:none;">${status.value}</textarea>
							</spring:bind>
						</div>
					</li>
				</ul>
			</div>
			<div class="mb20"></div>
			<div class="blockDetail main2Area btnline" style="padding-top: 0;">
				<table>
					<tr>
						<th class="textCenter" style="width: 100px;"><ikep4j:message pre="${preTitle}" key="shareDeptId" /></th>
						<td>
							<select name="devReqShareDeptIdList" id="devReqShareDeptIdList" class="input_select tcsElement" size="4"	style="height:100px;width:60%" multiple>						
								<c:forEach var="item" items="${newProductDev.devReqShareDeptList}">
									<option value="${item.shareDeptId}" ><c:out value='${item.shareDeptNm}'/></option>
								</c:forEach>
							</select>
							<a id="addReadPermissionButton" class="button_ic valign_bottom tcsEnableArea" style="display:none;"  href="#a" title="참조부서추가"><span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" alt="" />참조부서추가</span></a>
							<a id="deleteReadPermissionButton" class="button_ic valign_bottom tcsEnableArea" style="display:none;"  href="#a" title="참조부서삭제"><span><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="" />참조부서삭제</span></a>
						</td>
					</tr>
				</table>
			</div>
		<!-- main2 Area End -->
		<div class="mb20"></div>
		
		<!-- main3 Area Start -->
		<div class="blockDetail main3Area">
			<table>
				<colgroup>
					<col style="width:31px;">
					<col style="width:*;">
					<col style="width:316px;">
				</colgroup>
				<tbody>
					<tr>
						<th colspan="3" class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="sndRcvDeptNm" /></th>
					</tr>
					<!-- 2차검토부서접수  -->
					<tr>
						<th class="textCenter">의<br>견</th>
						<td class="">
							<spring:bind path="newProductDev.sndReviewOpinion">
								<textarea name="${status.expression}" class="sndRcvElement readOnlyFocusBlur" readonly="readonly" style="padding:0 0;overflow:auto;  line-height:16px; width:100%; height:100px; border:none; background:none;">${status.value}</textarea>
							</spring:bind>
						</td>
						<td class="clearMarPadd0">
							<table>
								<tr>
									<th rowspan="3" class="textCenter w20pix">접<br/>수<br/>부<br/>서</th>
									<th class="textCenter"><ikep4j:message pre="${ preTitle}" key="draft" /></th>
									<th class="textCenter"><ikep4j:message pre="${ preTitle}" key="review" /></th>
									<th class="textCenter"><ikep4j:message pre="${ preTitle}" key="approval" /></th>
								</tr>
								<tr class="clearMarPadd0" style="height: 60px;">
									<!-- 2차검토부서접수:기안자  -->
									<td class="nameArea" style="height: 40px;">
										<spring:bind path="newProductDev.sndRcvDraftEmpNo">
											<input name="${status.expression}" type="hidden" value="${status.value}"  />
										</spring:bind>
										<p>${ newProductDev.sndRcvDraftEmpNm}</p>
										<c:if test="${newProductDev.sndRcvDraftDate != '' && newProductDev.sndRcvDraftDate ne null}">( ${ newProductDev.sndRcvDraftDate} )</c:if>
									</td>
									<!-- 2차검토부서접수:검토자  -->
									<td class="nameArea">
										<div class="sndRcvOnlyEnableArea paddingRight15" style="display:none;">
											<spring:bind path="newProductDev.sndRcvReviewEmpNo">
												<input name="${status.expression}" type="hidden" class="searchUserId" value="${status.value}"  />
											</spring:bind>
											<spring:bind path="newProductDev.sndRcvReviewEmpNm">
												<input name="${status.expression}" type="text" class="searchUserName inputbox" value="${status.value}" />
											</spring:bind>
										</div>
										<div class="sndRcvOnlyDisableArea">
											<p>${ newProductDev.sndRcvReviewEmpNm}</p>
											<p>
												<c:if test="${newProductDev.sndRcvReviewDate != '' && newProductDev.sndRcvReviewDate ne null}">( ${ newProductDev.sndRcvReviewDate} )</c:if> 
											</p>
										</div>
									</td>
									<!-- 2차검토부서접수 :승인자 -->
									<td class="nameArea">
										<div class="sndRcvOnlyEnableArea paddingRight15" style="display:none;">
											<spring:bind path="newProductDev.sndRcvApprEmpNo">
												<input name="${status.expression}" type="hidden" class="searchUserId" value="${status.value}"  />
											</spring:bind>
											<spring:bind path="newProductDev.sndRcvApprEmpNm">
												<input name="${status.expression}" type="text" class="searchUserName inputbox" value="${status.value}" />
											</spring:bind>
										</div>
										<div class="sndRcvOnlyDisableArea">
											<p>${ newProductDev.sndRcvApprEmpNm}</p>
											<p>
												<c:if test="${newProductDev.sndRcvApprDate != '' && newProductDev.sndRcvApprDate ne null}">( ${ newProductDev.sndRcvApprDate} )</c:if> 
											</p>
										</div>
									</td>
								</tr>
								<!-- 2차검토부서접수 진행 상태 -->
								<tr>
									<td class="textCenter" style="height: 20px;">
										<c:forEach var="code" items="${ c00005List}">
											<c:if test="${code.comCd eq newProductDev.sndRcvDraftStsCd}">${code.charCol1}</c:if>
										</c:forEach>
									</td>
									<td class="textCenter">
										<c:forEach var="code" items="${ c00005List}">
											<c:if test="${code.comCd eq newProductDev.sndRcvReviewStsCd and code.numCol1 eq 1}">${code.comNm}</c:if>
										</c:forEach>
									</td>
									<td class="textCenter">
										<c:forEach var="code" items="${ c00005List}">
											<c:if test="${code.comCd eq newProductDev.sndRcvApprStsCd and code.numCol1 eq 1 }">${code.comNm}</c:if>
										</c:forEach>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<!-- 2차검토부서결과 결과 -->
					<tr>
						<th class="textCenter">결<br/>과</th>
						<td class="">
							<spring:bind path="newProductDev.sndReviewResult">
								<textarea name="${status.expression}" class="sndRsltElement readOnlyFocusBlur" readonly="readonly" style="padding:0 0;overflow:auto;  line-height:16px; width:100%; height:100px; border:none; background:none;">${status.value}</textarea>
							</spring:bind>
						</td>
						<td class="clearMarPadd0" style="height: 40px;">
							<table class="clearMarPadd0">
								<tr>
									<th rowspan="3" class="textCenter w20pix">접<br/>수<br/>부<br/>서</th>
									<th class="textCenter"><ikep4j:message pre="${ preTitle}" key="draft" /></th>
									<th class="textCenter"><ikep4j:message pre="${ preTitle}" key="review" /></th>
									<th class="textCenter"><ikep4j:message pre="${ preTitle}" key="approval" /></th>
								</tr>
								<tr class="clearMarPadd0" style="height: 60px;">
									<!-- 2차검토부서결과:기안자  -->
									<td class="nameArea">
										<spring:bind path="newProductDev.sndRsltDraftEmpNo">
											<input name="${status.expression}" type="hidden" value="${status.value}"  />
										</spring:bind>
										<p>${ newProductDev.sndRsltDraftEmpNm}</p>
										<c:if test="${newProductDev.sndRsltDraftDate != '' && newProductDev.sndRsltDraftDate ne null}">( ${ newProductDev.sndRsltDraftDate} )</c:if>
									</td>
									<!-- 2차검토부서결과:검토자  -->
									<td class="nameArea paddingRight15">
										<div class="sndRsltOnlyEnableArea" style="display:none;">
											<spring:bind path="newProductDev.sndRsltReviewEmpNo">
												<input name="${status.expression}" type="hidden" class="searchUserId" value="${status.value}"  />
											</spring:bind>
											<spring:bind path="newProductDev.sndRsltReviewEmpNm">
												<input name="${status.expression}" type="text" class="searchUserName inputbox" value="${status.value}" />
											</spring:bind>
										</div>
										<div class="sndRsltOnlyDisableArea">
											<p>${ newProductDev.sndRsltReviewEmpNm}</p>
											<p>
												<c:if test="${newProductDev.sndRsltReviewDate != '' && newProductDev.sndRsltReviewDate ne null}">( ${ newProductDev.sndRsltReviewDate} )</c:if> 
											</p>
										</div>
									</td>
									<!-- 2차검토부서결과:승인자 -->
									<td class="nameArea paddingRight15">
										<div class="sndRsltOnlyEnableArea" style="display:none;">
											<spring:bind path="newProductDev.sndRsltApprEmpNo">
												<input name="${status.expression}" type="hidden" class="searchUserId" value="${status.value}"  />
											</spring:bind>
											<spring:bind path="newProductDev.sndRsltApprEmpNm">
												<input name="${status.expression}" type="text" class="searchUserName inputbox" value="${status.value}" />
											</spring:bind>
										</div>
										<div class="sndRsltOnlyDisableArea">
											<p>${ newProductDev.sndRsltApprEmpNm}</p>
											<p>
												<c:if test="${newProductDev.sndRsltApprDate != '' && newProductDev.sndRsltApprDate ne null}">( ${ newProductDev.sndRsltApprDate} )</c:if> 
											</p>
										</div>
									</td>
								</tr>
								<!-- 2차검토부서결과 진행 상태 -->
								<tr>
									<td class="textCenter" style="height: 20px;">
										<c:forEach var="code" items="${ c00005List}">
											<c:if test="${code.comCd eq newProductDev.sndRsltDraftStsCd}">${code.charCol1}</c:if>
										</c:forEach>
									</td>
									<td class="textCenter">
										<c:forEach var="code" items="${ c00005List}">
											<c:if test="${code.comCd eq newProductDev.sndRsltReviewStsCd and code.numCol1 eq 1}">${code.comNm}</c:if>
										</c:forEach>
									</td>
									<td class="textCenter">
										<c:forEach var="code" items="${ c00005List}">
											<c:if test="${code.comCd eq newProductDev.sndRsltApprStsCd and code.numCol1 eq 1 }">${code.comNm}</c:if>
										</c:forEach>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- main3 Area End -->
	</div> 
						
		</form>
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<c:if test="${viewMode eq 'modify'}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="ubiOpen();" ><span><ikep4j:message pre="${preButton}" key="ubiReport" /></span></a></li>
				</c:if>
				<c:if test="${npdPermission.ecmRoll ne true && npdPermission.reqViewActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="openPopActiveX();" ><span><ikep4j:message pre="${preButton}" key="fileItemId" /></span></a></li>
				</c:if>
				<c:if test="${npdPermission.ecmRoll eq true && npdPermission.reqViewActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="openPopEcm();" ><span><ikep4j:message pre="${preButton}" key="fileItemId" /></span></a></li>
				</c:if>
				<c:if test="${npdPermission.reReceiptBntActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="changeReceipt();" ><span><ikep4j:message pre="${preButton}" key="receipt" /></span></a></li>
				</c:if>
				<c:if test="${npdPermission.initRejctBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="initRejct();" ><span><ikep4j:message pre="${preButton}" key="receipt" /></span></a></li>
				</c:if>
				<c:if test="${npdPermission.receiptBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="receipt();" ><span><ikep4j:message pre="${preButton}" key="receipt" /></span></a></li>
				</c:if>
				<c:if test="${npdPermission.saveBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="save();" ><span><ikep4j:message pre="${preButton}" key="save" /></span></a></li>
				</c:if>
				<c:if test="${npdPermission.deleteBtnActive eq true }">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="delNewProductDev();" ><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
				</c:if>
				<c:if test="${npdPermission.approveBtnActive eq true}">
					<li>
						<c:if test="${npdPermission.saveBtnActive eq true}">
							<a class="button listBoardItemButton" href="javascript:;" onclick="approveWithSave();" >
						</c:if>
						<c:if test="${npdPermission.saveBtnActive eq false}">
							<a class="button listBoardItemButton" href="javascript:;" onclick="approve();" >
						</c:if>
						<c:if test="${npdPermission.drafter eq false}">
							<span><ikep4j:message pre="${preButton}" key="approval" /></span>
						</c:if>
						<c:if test="${npdPermission.drafter eq true}">
							<span><ikep4j:message pre="${preButton}" key="approval2" /></span>
						</c:if>
						</a>
					</li>
				</c:if>
				<c:if test="${npdPermission.rejectBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="reject();" ><span><ikep4j:message pre="${preButton}" key="deny" /></span></a></li>
				</c:if>
				<c:if test="${npdPermission.rejectResonBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="reject();" ><span><ikep4j:message pre="${preButton}" key="denyReason" /></span></a></li>
				</c:if>
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="goList();"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
				<c:if test="${npdPermission.rsltFileBtnActive eq true || npdPermission.rsltFileViewActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="rsltFile();" ><span><ikep4j:message pre="${preButton}" key="rsltFile" /></span></a></li>
				</c:if>
			 </ul>
		</div>
		<!--//blockButton End-->
		
	</div>
	<!--//blockDetail End-->  			
							
	