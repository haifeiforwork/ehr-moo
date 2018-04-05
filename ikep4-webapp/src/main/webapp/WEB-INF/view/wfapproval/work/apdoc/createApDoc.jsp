<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.wfapproval.work.apdoc.create.header" /> 
<c:set var="preView"    value="ui.wfapproval.work.apdoc.create.form" />
<c:set var="preButton"  value="ui.wfapproval.work.apdoc.create.button" />
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

	$(document).ready(function() {		
		
		new iKEP.Validator("#apDoc", {
			rules : {
				apprDocNo : { required:true, rangelength:[6, 12] }
			},
			messages : {
				apprDocNo : {
					required : "분류번호를 입력해주세요.",
					rangelength : "분류번호는 6자 이상 12자 이내로 입력하셔야 합니다.",
					direction : "bottom"
				}

			},
			notice : {
				apprDocNo : { message:"분류번호를 입력하는 항목 입니다.", direction:"bottom" },
				apprTitle : { message:"제목을 입력하는 항목 입니다.", direction:"bottom"}
			},
			submitHandler : function(form) {
				//iKEP.debug(form);
				form.submit();
			}
		});
		
		$("#apprDocData").ckeditor($.extend(simpleCkeditorConfig, {"popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" ,		
			width: '100%', 
		    height: 550
		}));  
		
		//공통 fileController
		//var fileController = new iKEP.FileController("#apDoc", "#fileUploadArea", {
		//	//files : /* 파일 목록 */ [, {fileid:2, name:"file 2", size:100, type:"jpg"}], 
		//	//size : /* 파일 컨트롤 싸이즈 특별한 케이스에만 사용 디폴트 w 100 h 100 */ {width:100, height:100},
		//	//uploadUrl : /* 파일 업로드 URL fileupload package 안에 존재함. 커스터마이징 하시면 안됨 */ "",
		//	//excludeFileTypes :/* 업로드제한 파일확장자 */  "jpg^gif",
		//	lang :  "en"
		//});	
		
		//$("#accordion").accordion(); 
		
		//임시저장
		$("a[name=tempSaveApDocButton]").click(function() {
			//임시저장 STATE SET
			document.apDoc.apprDocState.value = '0';
			document.apDoc.apprDocData.value = CKEDITOR.instances.apprDocData.getData();

			//fileController.upload(function(isSuccess, files) {
			//	if(isSuccess === true) { 
					f_SaveApDoc();
			//	}
			//});			
			return false; 
		});
		
		//목록
		$("#listButton").click(function() {		
			var ref = document.referrer;
			location.href=ref;
			
		});
		
		//결재요청
		$("a[name=saveApDocButton]").click(function() {
			
			<c:if test="${apFormTpl.processType eq 'APPROVAL'}">
			if($jq('#workers2 option').length < 1) {
				alert("결재선 지정이 필요합니다.");
				return;
			}
			</c:if>
			
			//결재요청 STATE SET
			document.apDoc.apprDocState.value = '1';
			//fileController.upload(function(isSuccess, files) {
			//	if(isSuccess === true) { 
					//f_SaveApDoc();
			//	}
			//});
			
			document.apDoc.apprDocData.value = CKEDITOR.instances.apprDocData.getData();
			
			//결재요청팝업 버튼 이벤트
			dialog1 = $jq("#dialog").dialog({
				modal:true,
				width:600,
				height:450
				//buttons: {
		        //    '결재요청': function() {
		        //    	if (document.messageAddDialog.apprMessage.value == '')
		         //   	{
		        //    		alert("의견 입력은 필수입니다.");
		         //   		return false;
		         //   	}
		         //   	document.apDoc.apprMessage.value=
			//			document.messageAddDialog.apprMessage.value;
			//			f_SaveApDoc();
			//			dialog1.dialog('close');
		    //        },
		    //        '취소': function() {
		    //        	dialog1.dialog('close')
		    //        	return false; 
		    //        }
		     //   }
			});						
			return false; 
		});
				
		//수신처지정
		$("a[name=receiveAssignButton]").click(function() {  
			
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
			
			var url = "<c:url value='/support/popup/addresbookPopup.do'/>"+"?controlType=" + $controlType + "&controlTabType=" + $controlTabType + "&selectType=" + $selectType + "&selectMaxCnt=10&searchSubFlag=" + $searchSubFlag;
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
			
		});
		$.template("addrBookItemUser", '<option value="\${id}" >\${name}/\${jobTitle}/\${teamName}/\${id}</option>');
		$.template("addrBookItemGroup", '<option value="\${code}" >\${name}</option>');

		//열람권한지정
		$("a[name=viewAuthButton]").click(function() {  
			
			var items = [];
			var $sel = $jq("#apDoc").find("[name=addrGroupList1]");
			$jq.each($sel.children(), function() {
				items.push($.data(this, "data"));
			});
 
			$controlType = $jq('input[name=controlType]:hidden').val() ;
			$controlTabType = $jq('input[name=controlTabType]:hidden').val() ;
			$selectType = $jq('input[name=selectType]:hidden').val() ;
			$selectMaxCnt = $jq('input[name=selectMaxCnt]:hidden').val() ;
			$searchSubFlag = $jq('input[name=searchSubFlag]:hidden').val() ;
			
			var url = "<c:url value='/support/popup/addresbookPopup.do'/>"+"?controlType=" + $controlType + "&controlTabType=" + $controlTabType + "&selectType=" + $selectType + "&selectMaxCnt=10&searchSubFlag=" + $searchSubFlag;
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
			
		});

			
		//결재선지정
		$("a[name=processLineButton]").click(function() {  
			
			var items = [];
			var $sel = $jq("#apDoc").find("[name=addrGroupList2]");
			$jq.each($sel.children(), function() {
				items.push($.data(this, "data"));
			});
	 
			$controlType = $jq('input[name=controlType]:hidden').val() ;
			$controlTabType = $jq('input[name=controlTabType]:hidden').val() ;
			$selectType = $jq('input[name=selectType]:hidden').val() ;
			$selectMaxCnt = $jq('input[name=selectMaxCnt]:hidden').val() ;
			$searchSubFlag = $jq('input[name=searchSubFlag]:hidden').val() ;
	 
			$selectApprLineCnt = $jq('input[name=selectApprLineCnt]:hidden').val() ;
			$selectDiscussLineCnt = $jq('input[name=selectDiscussLineCnt]:hidden').val() ;
			
			$isAppr = $jq('input[name=isAppr]:hidden').val() ;
			$isDiscuss = $jq('input[name=isDiscuss]:hidden').val() ;
			$apprLineType = $jq('input[name=apprLineType]:hidden').val() ;
			
			
			
			var dialog = new iKEP.Dialog({
				title: "결재선지정",
				url: "<c:url value='/wfapproval/work/apdoc/listUserApLine.do'/>"+"?controlType=" + $controlType + "&controlTabType=" + $controlTabType + "&selectType=" + $selectType + "&selectMaxCnt=" + $selectMaxCnt + "&searchSubFlag=" + $searchSubFlag+ "&selectApprLineCnt="+ $selectApprLineCnt+"&selectDiscussLineCnt="+ $selectDiscussLineCnt+"&isAppr="+ $isAppr +"&isDiscuss="+ $isDiscuss +"&apprLineType=" + $apprLineType,
				modal: true,
				width: 1200,
				height: 600,
				params : {search:"keyword", items:items },
				callback : function(result) {
						
					$sel.empty();
					
					//결재합의 리스트 초기화					
					var row = $jq("#processL");
					
					var index = row.length;
					
					while (index == 1)
					{
						row.remove();
						row = $jq("#processL");
						index = row.length;
					}
					//결재합의 리스트 초기화
					
					var approvalCnt=1; var agreeCnt=1;
					$jq.each(result, function() {
							
						var tpl = "";

						//직렬합의,병렬합의 세팅
						if (approvalCnt==1){
							document.apDoc.apprLineType.value = this.apprLineType;
						}
						//if ( this.agreeType == 0) //직렬일때
						//{
						//	agreeCnt = approvalCnt;
						//}
							
						switch(this.type) {
							case "group" : tpl = "addrBookItemGroup"; break;
							case "user" : tpl = "addrBookItemUser"; break;
						}
							
						var $option = $.tmpl(tpl, this).appendTo($sel);
							
							
						$.data($option[0], "data", this);
							
						if( this.searchSubFlag == true ){
							$jq('input[name=searchSubFlag]:hidden').val("true") ;
						}
						
						if ( this.apprLineType == 'S') //직렬일때
						{						
							if (this.name.substring(0,2) == '결재'){
								$('#processList').append('<tr id=\"processL\"><td class=\"textCenter\">'+approvalCnt+'</td><td class=\"textCenter\">'+this.name.substring(0,2)+'</td><td class=\"textCenter\">'+this.name.substring(3)+'</td><td class=\"textCenter\">'+this.teamName+'/'+this.jobTitle+'</td><!--<td class=\"textCenter\">'+getTimeStamp()+'</td>--></tr>');
								approvalCnt++;
							}else{
								$('#processList2').append('<tr id=\"processL\"><td class=\"textCenter\">'+approvalCnt+'</td><td class=\"textCenter\">'+this.name.substring(0,2)+'</td><td class=\"textCenter\">'+this.name.substring(3)+'</td><td class=\"textCenter\">'+this.teamName+'/'+this.jobTitle+'</td><!--<td class=\"textCenter\">'+getTimeStamp()+'</td>--></tr>');	
								approvalCnt++;
							}
						}else{
							if (this.name.substring(0,2) == '결재'){
								$('#processList').append('<tr id=\"processL\"><td class=\"textCenter\">'+approvalCnt+'</td><td class=\"textCenter\">'+this.name.substring(0,2)+'</td><td class=\"textCenter\">'+this.name.substring(3)+'</td><td class=\"textCenter\">'+this.teamName+'/'+this.jobTitle+'</td><!--<td class=\"textCenter\">'+getTimeStamp()+'</td>--></tr>');
								approvalCnt++;
							}else{
								$('#processList2').append('<tr id=\"processL\"><td class=\"textCenter\">'+agreeCnt+'</td><td class=\"textCenter\">'+this.name.substring(0,2)+'</td><td class=\"textCenter\">'+this.name.substring(3)+'</td><td class=\"textCenter\">'+this.teamName+'/'+this.jobTitle+'</td><!--<td class=\"textCenter\">'+getTimeStamp()+'</td>--></tr>');	
								agreeCnt++;
							}							
						}
												
						
						
					})
				}
			});
		});
		
		//기결재참조첨부 
		$("a[name=docAttachButton]").click(function() {  
			
			$prevapprId     = $("input[name=etcName3]");
			//$prevapprName 	= $("input[name=prevapprName]");
			//$processType 	= $("input[name=processType]");
			
			viewApFormProcessDialog = new iKEP.Dialog({     
				title 		: "기결재 참조첨부",
				url 		: "<c:url value="/wfapproval/work/apdoc/listApComplete.do"/>",
				modal 		: true,
				width 		: 1150, //1200,
				height 		: 550,
				params 		: {id:$prevapprId.val() },
				callback	: function(result) {
						//iKEP.debug(result.length);
						
						
						//기결재참조 리스트 초기화					
						var row = $jq("#processList6");
						
						var index = row.length;
						
						
						while (index == 1)
						{   
							row.remove();
							row = $jq("#processList6");
							index = row.length;
							
						}
						//기결재참조 리스트초기화						
						
						if(result){
							for (i=0; i<result.length; i++)
							{
								
								if (i == 0){
									$prevapprId.val(result[i].id +",");
								}else{
									$prevapprId.val($prevapprId.val() +result[i].id +",");
								}
								
								//$prevapprName.val($prevapprName.val() + ","+result[i].name);
								//$('#processList4').append('<tr id=\"processL\">'+result[i].id+'/'+result[i].name+'</tr>');
								$('#processList5').append('<li id=\"processList6\">'+result[i].name+'</li>');
							}
							
						}
				}
			});

		});
		
		
		/**
		 * 의견입력 버튼영역
		 */
		$("#createApDocProcessButton a").click(function(){
	           switch (this.id) {
				case "applyProcessButton":
	              	if (document.messageAddDialog.apprMessage.value == '')
					{
	            	   	alert("의견 입력은 필수입니다.");
						return false;
					}
					document.apDoc.apprMessage.value=
					document.messageAddDialog.apprMessage.value;
					//fileController.upload(function(isSuccess, files) {
					//	if(isSuccess === true) { 
							f_SaveApDoc();
					//	}
					//});
					
					dialog1.dialog('close');
					break;
				case "cancelProcessButton":
					dialog1.dialog('close')
					break;
				default:
					break;
	            }
		});		
		
	});
})(jQuery);

</script>

<script  type="text/javascript"> 
	function getTimeStamp() {
	  var d = new Date();

	  var s =
	    leadingZeros(d.getFullYear(), 4) + '-' +
	    leadingZeros(d.getMonth() + 1, 2) + '-' +
	    leadingZeros(d.getDate(), 2) 

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
	/**
	 * 양식 저장
	 * @param {String} 양식ID
	 */
	function f_SaveApDoc(formId) {
		var oForm = $jq('form[name=apDoc]');
		
		var idlist = [];
		var itemstr ="";
		$jq('#workers option').each(function(){idlist.push(this.value)});
		$jq.each(idlist,function(index, item) {itemstr += item + ',';});

		var idlist1 = [];
		var itemstr1 ="";
		$jq('#workers1 option').each(function(){idlist1.push(this.value)});
		$jq.each(idlist1,function(index, item) {itemstr1 += item + ',';});
		
		var idlist2 = [];
		var itemstr2 ="";
		$jq('#workers2 option').each(function(){idlist2.push(this.value)});
		$jq.each(idlist2,function(index, item) {itemstr2 += item + ',';});
				
		document.apDoc.etcName.value = itemstr.substring(0, itemstr.length-1);
		document.apDoc.etcName1.value = itemstr1.substring(0, itemstr1.length-1);
		document.apDoc.etcName2.value = itemstr2.substring(0, itemstr2.length-1);
		
		//alert( document.apDoc.etcName3.value);
		//document.apDoc.submit(); 
		
		
		oForm.submit();
		
	}
</script>
 
			
<h1 class="none"><ikep4j:message pre="${preHeader}" key="title"/></h1>

<!--pageTitle Start-->

<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2>
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
		<!--  <li><a id="listApDocButton"   class="button" href="<c:url value='/wfapproval/work/apform/listApFormSelect.do'/>"><span><ikep4j:message pre="${preButton}" key="list" /></span></a></li>-->
		<li><a name="listButton" id="listButton" class="button" ><span><ikep4j:message pre="${preButton}" key="list" /></span></a></li>
		<li><a name="tempSaveApDocButton" id="tempSaveApDocButton" class="button" href="#a" ><span><ikep4j:message pre="${preButton}" key="tempSave" /></span></a></li>
		<c:if test="${apFormTpl.processType eq 'APPROVAL'}">
		<li><a name="processLineButton" id="processLineButton" class="button" href="#a" ><span><ikep4j:message pre="${preButton}" key="approvalAssign" /></span></a></li>
		</c:if>
		<li><a name="docAttachButton" id="docAttachButton" class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="docAttach" /></span></a></li>
		<li><a name="saveApDocButton" id="saveApDocButton" class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="docRequest" /></span></a></li>
		<li><a name="receiveAssignButton" id="receiveAssignButton" class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="receiveUser" /></span></a></li>
		<li><a name="viewAuthButton" id="viewAuthButton" class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="viewAuth" /></span></a></li>
	</ul>
</div>
<c:if test="${apFormTpl.processType eq 'APPROVAL'}">
<!--  <div class="blockLeft">-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre="${preView}" key="approvalAssignTitle" /></h3>
				</div>			

<!--blockLeft Start-->
				<div class="blockLeft" style="width:49%">
					<!--blockDetail Start-->
					
				<div class="blockDetail">
					<table summary="<ikep4j:message pre="${preView}" key="approvalAssignType1" />">
						<caption></caption>
						<thead>
							<tr>
								<th colspan="4" class="textCenter" scope="col"><ikep4j:message pre="${preView}" key="approvalAssignType1" /></th>
							</tr>
							<tr>
								<th scope="col" width="10%" class="textCenter"><ikep4j:message pre="${preView}" key="seq" /></th>
								<th scope="col" width="30%" class="textCenter"><ikep4j:message pre="${preView}" key="type" /></th>
								<th scope="col" width="30%" class="textCenter"><ikep4j:message pre="${preView}" key="name" /></th>
								<th scope="col" width="30%" class="textCenter"><ikep4j:message pre="${preView}" key="teamname" /></th>
								
							</tr>
						</thead>
						<tbody id="processList">
							<tr>
								<td class="textCenter">&nbsp;</td>
								<td class="textCenter"><ikep4j:message pre="${preView}" key="approvalAssignType2" /></td>
								<td class="textCenter">${user.userName}</td>
								<td class="textCenter">${user.teamName}/${user.jobPositionName}</td>
								<!--  <td class="textCenter">${today}</td>-->
							</tr>
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
			</div>
			<!--blockLeft End-->
			<!--blockRight Start-->
			<div class="blockRight" style="width:49%">
							
				<!--blockDetail Start-->					
				  <div class="blockDetail">
					<table summary="<ikep4j:message pre="${preView}" key="approvalAssignType4" />">
						<caption></caption>
						<thead>
							<tr>
								<th colspan="4" class="textCenter" scope="col"><ikep4j:message pre="${preView}" key="approvalAssignType4" /></th>
							</tr>
							<tr>
								<th scope="col" width="10%" class="textCenter"><ikep4j:message pre="${preView}" key="seq" /></th>
								<th scope="col" width="30%" class="textCenter"><ikep4j:message pre="${preView}" key="type" /></th>								
								<th scope="col" width="30%" class="textCenter"><ikep4j:message pre="${preView}" key="name" /></th>
								<th scope="col" width="30%" class="textCenter"><ikep4j:message pre="${preView}" key="teamname" /></th>
								
							</tr>
						</thead>
						<tbody id="processList2">
						</tbody>
						
					</table>
				</div>
				<!--//blockDetail End-->
			</div>	
			<!--blockRight End-->
				<div class="clear"></div>
				<div class="blockBlank_10px"></div>
			
			</c:if>
			<!--blockDetail Start-->
			

			<div class="blockDetail">
					<table summary="<ikep4j:message pre="${preView}" key="detail" /> ">
						<caption></caption>
						<tbody>
							<tr>
								<th width="18%" scope="row"><ikep4j:message pre="${preView}" key="apprDocCd" /><!--: ${apFormTpl.apprDocCd},${apFormTpl.isApprDoc}--></th>
								<td width="32%">
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
								<td width="32%">
									<input 	type="text" class="inputbox" title="<ikep4j:message pre="${preView}" key="apprDocNo" />" 
											name="apprDocNo" value="" size="30" />

								</td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre="${preView}" key="apprTeam" /></th>
								<td>
									${user.teamName}
								</td>
								<th scope="row"><ikep4j:message pre="${preView}" key="apprRegister" /></th>
								<td>
									${user.userName} ${user.jobPositionName}
								</td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre="${preView}" key="apprDate" /></th>
								<td>
									${today}
								</td>
								<th scope="row"><ikep4j:message pre="${preView}" key="apprPeriodCd" /> <!--: ${apFormTpl.apprPeriodCd},${apFormTpl.isApprPeriod}--></th>
								<td>
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
									<!-- 수신자  리스트  select --><select id="workers" name="addrGroupList" multiple="multiple" size="5" style="width:300px;height:50px;"></select>
								</td>
								<th scope="row"><ikep4j:message pre="${preView}" key="authUser" /></th>
								<td >
									<!-- 열람권한 리스트  select --><select id="workers1" name="addrGroupList1" multiple="multiple" size="5" style="width:300px;height:50px;"></select>
								</td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre="${preView}" key="title" /> <!--  : ${apFormTpl.isApprTitle}--></th>
								<td colspan=3>
									<input 	type="text" class="inputbox" title="<ikep4j:message pre="${preView}" key="title" />" name="apprTitle" value="${apFormTpl.apprTitle}" size="100" <c:if test="${apFormTpl.isApprTitle eq 'Y'}">readonly onkeyDown='if(event.keyCode == 13 || event.keyCode == 8){event.returnValue=false;}'</c:if>  />
											<!--  name="apprTitle" value="${apFormTpl.apprTitle}" size="100" <c:if test="${apFormTpl.isApprTitle eq 'Y'}">readonly</c:if> onkeyDown="if(event.keyCode == 13 || event.keyCode == 8){event.returnValue=false;}" />-->
											
											
									<!--  onKeydown     backspace,enter 않되게 적용 -->
								</td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre="${preView}" key="attachApDoc" /></th>
								<td colspan="3" >
									<ul class="listStyle" id="processList5">
									

									</ul>	
									
									
								</td>
							</tr>
							
							
							
						</tbody>
					</table>
					
					<table>
							<tr>
								<td >
								
								<c:if test="${apFormTpl.isNoneForm eq 'Y'}">
									<textarea 	id="apprDocData" name="apprDocData" cols="" rows="5" class="inputbox w100 fullEditor" 
												title="<ikep4j:message pre="${preView}" key="apprDocData" />" >${apFormTpl.apprFormData}</textarea>
								</c:if>
								<c:if test="${apFormTpl.isNoneForm eq 'N'}">
												${apFormTpl.apprFormData}
								</c:if>			
								</td>
							</tr>
					</table>
					
					<table>
							 
							<tr>
								<td colspan=0 id="fileUploadArea">
								</td>
							</tr>
					</table>
				


				<input type="hidden" name="registUserId" value="${user.userId}" />				
				<!-- 수신처 -->
				<input type="hidden" name="etcName" id="etcName"/>
				<!-- 수신처  -->
				<!-- 열람권한지정  -->
				<input type="hidden" name="etcName1" id="etcName1"/>
				<!-- 열람권한지정  -->
				<!-- 결재선지정 -->
				<input type="hidden" name="etcName2" id="etcName2"/>
				<!-- 결재선지정  -->
				<!-- 기결재참조 -->
				<input type="hidden" name="etcName3" id="etcName3"/>
				<!-- 기결재참조 -->
				
			 	<input type="hidden" name="apprDocState" id="apprDocState"/>
             
             	<input name="controlTabType" title="" type="hidden" value="0:0:0:0" />
             	<input name="controlType" title="" type="hidden" value="ORG" />
             	<input name="selectType" title="" type="hidden" value="USER" />
             	<input name="selectMaxCnt" title="" type="hidden" value="${apFormTpl.apprLineCnt+apFormTpl.discussLineCnt}" />
             	<input name="selectApprLineCnt" title="" type="hidden" value="${apFormTpl.apprLineCnt}" />
             	<input name="selectDiscussLineCnt" title="" type="hidden" value="${apFormTpl.discussLineCnt}" />
             	<input name="isAppr" title="" type="hidden" value="${apFormTpl.isAppr}" />
             	<input name="isDiscuss" title="" type="hidden" value="${apFormTpl.isDiscuss}" />
             	<input name="searchSubFlag" title="" type="hidden" value="" />
             	<input name="apprMessage" title="" type="hidden" value="" />
             	
             	
             	<!-- 양식값 읽어오기 -->
             	<input name="mailReqCd" title="" type="hidden" value="${apFormTpl.mailReqCd}" />
             	<input name="mailReqWayCd" title="" type="hidden" value="${apFormTpl.mailReqWayCd}" />
             	<input name="mailEndCd" title="" type="hidden" value="${apFormTpl.mailEndCd}" />
             	<input name="mailEndWayCd" title="" type="hidden" value="${apFormTpl.mailEndWayCd}" />
             	<input name="processId" title="" type="hidden" value="${apFormTpl.processId}" />
             	<!-- 양식값 읽어오기 -->
             	
             	
             	
             	<input name="apprLineType" title="" type="hidden" value="S" />
             
             	<!-- 결재선 리스트  select -->
             	<select id="workers2" name="addrGroupList2" multiple="multiple" size="5" style="width:300px;height:50px;display:none;"></select>


</form:form>
<!--blockButton Start-->
<!--//blockButton End-->
<!--의견입력 팝업 [S]-->
<div id="dialog" title="의견입력" style="display:none">
	<form name="messageAddDialog" action="">
		<!-- <textarea 	name="apprMessage" cols="49" rows="7"  title="의견" ></textarea>-->
	

	<div id="popup_contents">
 
		<div>
			<textarea name="apprMessage" class="w100" title="입력" cols="" rows="15"></textarea>
		</div>	
		<div class="blockBlank_10px"></div>
		
		<!--blockButton Start-->
		<div id="createApDocProcessButton" class="blockButton textCenter"> 
			<ul>
				<li><a id="applyProcessButton" class="button" href="#a"><span>결재요청</span></a></li>
				<li><a id="cancelProcessButton" class="button" href="#a"><span>취소</span></a></li>				
			</ul>
		</div>
		<!--//blockButton End-->			
				
	</div>
	<!--//popup_contents End-->
 
	<!--popup_footer Start-->
	<div id="popup_footer"></div>

	</form>


</div>
<!--의견입력 팝업 [E]-->
</body>
</html>