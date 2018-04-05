<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.wfapproval.work.apdoc.create.header" />
<c:set var="preButton"  value="ui.wfapproval.work.apdoc.create.button" />
<c:set var="preView"    value="ui.wfapproval.work.apdoc.create.form" />
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
		
		$("#apprDocData").ckeditor($.extend(simpleCkeditorConfig, {"popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" ,		
			width: '100%', 
		    height: 550
		}));
		
		////var fileController = new iKEP.FileController("#apDoc", "#fileUploadArea", {
//	 		files : /* 파일 목록 */ [, {fileid:2, name:"file 2", size:100, type:"jpg"}], 
			//size : /* 파일 컨트롤 싸이즈 특별한 케이스에만 사용 디폴트 w 100 h 100 */ {width:100, height:100},
			//uploadUrl : /* 파일 업로드 URL fileupload package 안에 존재함. 커스터마이징 하시면 안됨 */ "",
			//excludeFileTypes :/* 업로드제한 파일확장자 */  "jpg^gif",
		//	lang :  "en"
		//});	
		//$("#accordion").accordion(); 
		
		//목록
		$("#listButton").click(function() {
		
			var ref = document.referrer;
			location.href=ref;
			
		});
		
		//임시저장
		$("a[name=tempSaveApDocButton]").click(function() {
			
			//임시저장 STATE SET
			document.apDoc.apprDocState.value = '0';
			document.apDoc.apprDocData.value = CKEDITOR.instances.apprDocData.getData();
			//fileController.upload(function(isSuccess, files) {
			//	if(isSuccess === true) { 
			//		//$jq("#boardItemForm").submit();
					f_SaveApDoc();
			//	}
			//});
			
			return false; 
			
		});
		//결재요청
		$("a[name=saveApDocButton]").click(function() {
			
			//결재요청 STATE SET
			document.apDoc.apprDocState.value = '1';
			//fileController.upload(function(isSuccess, files) {
			//	if(isSuccess === true) { 
			//		//$jq("#boardItemForm").submit();
					f_SaveApDoc();
			//	}
			//});
			
			return false; 
			
		});
		

		
		$jq('#workers2 option').each(function() {
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
		$jq('#workers option').each(function() {	
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
		$jq('#workers1 option').each(function() {
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
		
		
		$("a[name=saveBoardItemButton]").click(function() {
			
			alert("HERE");		
			fileController.upload(function(isSuccess, files) {
				if(isSuccess === true) { 
					//$jq("#boardItemForm").submit();
					f_SaveApDoc();
				}
			});
			
			return false; 
			
		});
		
		
		//수신처지정
		$("a[name=receiveAssignButton]").click(function() {  
		//$jq("#btnAddrControl").click(function() {
			//alert('수신처');
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
		
		
		//주소록 버튼에 이벤트 바인딩
        $jq('#addrBtn').click( function() {
               //파라미터(콜백함수, 전송파라미터, 팝업옵션)
		iKEP.showAddressBook(setAddress, "", "");
		});

		
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
<!--<form:form name="apDoc" modelAttribute="apDoc" method="post">-->
<form id="apDoc" name="apDoc" action="/ikep4-webapp/wfapproval/work/apdoc/createApDoc.do" method="post">


<div class="blockButton underline"> 
	<ul> 
		<li><a id="listButton"   class="button"><span><ikep4j:message pre="${preButton}" key="list" /></span></a></li>
		<li><a name="tempSaveApDocButton" id="tempSaveApDocButton" class="button" href="#a" ><span><ikep4j:message pre="${preButton}" key="tempSave" /></span></a></li>
		<li><a name="processLineButton" id="processLineButton" class="button" href="#a" ><span><ikep4j:message pre="${preButton}" key="approvalAssign" /></span></a></li>
		<li><a name="docAttachButton" id="docAttachButton" class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="docAttach" /></span></a></li>
		<li><a name="saveApDocButton" id="saveApDocButton" class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="docRequest" /></span></a></li>
		<li><a name="receiveAssignButton" id="receiveAssignButton" class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="receiveUser" /></span></a></li>
		<li><a name="viewAuthButton" id="viewAuthButton" class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="viewAuth" /></span></a></li>
	</ul>
</div>

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
								<th scope="col" width="20%" class="textCenter"><ikep4j:message pre="${preView}" key="type" /></th>
								<th scope="col" width="35%" class="textCenter"><ikep4j:message pre="${preView}" key="name" /></th>
								<th scope="col" width="35%" class="textCenter"><ikep4j:message pre="${preView}" key="teamname" /></th>
								<!--  <th scope="col" width="30%" class="textCenter">일자</th>-->
							</tr>
						</thead>
						<tbody id="processList"">
							
			<c:forEach var="apProcess" items="${apProcess}" varStatus="apDocItem">
						<c:choose>
						<c:when test="${apProcess.apprOrder eq 0 }">
							<tr>
								<td class="textCenter">&nbsp;</td>
								<td class="textCenter"><ikep4j:message pre="${preView}" key="approvalAssignType2" /></td>							
								<td class="textCenter">${apProcess.userName}</td>
								<td class="textCenter">${apProcess.teamName}/${apProcess.jobPositionName}</td>
								<!--  <td class="textCenter"></td>-->
							</tr>
						</c:when>
						<c:otherwise>
							<c:if test="${apProcess.apprType eq 0 }">
							<tr id="processL">
								<td class="textCenter">${apProcess.apprOrder}</td>
								<td class="textCenter"><ikep4j:message pre="${preView}" key="approvalAssignType3" /></td>							
								<td class="textCenter">${apProcess.userName}</td>
								<td class="textCenter">${apProcess.teamName}/${apProcess.jobPositionName}</td>
								<!--  <td class="textCenter"></td>-->
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
								<th scope="col" width="20%" class="textCenter"><ikep4j:message pre="${preView}" key="type" /></th>
								<th scope="col" width="35%" class="textCenter"><ikep4j:message pre="${preView}" key="name" /></th>
								<th scope="col" width="35%" class="textCenter"><ikep4j:message pre="${preView}" key="teamname" /></th>
								<!--  <th scope="col" width="30%" class="textCenter">일자</th>-->
							</tr>
						</thead>
						<tbody id="processList2">
							
<c:forEach var="apProcess" items="${apProcess}" varStatus="apDocItem">
							
							<c:if test="${apProcess.apprType eq 1 }">
							<tr id="processL">
								<td class="textCenter">${apProcess.apprOrder}</td>
								<td class="textCenter"><ikep4j:message pre="${preView}" key="approvalAssignType4" /></td>							
								<td class="textCenter">${apProcess.userName}</td>
								<td class="textCenter">${apProcess.teamName}/${apProcess.jobPositionName}</td>
								<!--  <td class="textCenter"></td>-->
							</tr>
							</c:if>
							
			</c:forEach>							
							
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
			</div>	
			<div class="clear"></div>


<div class="blockDetail">


					<table summary="<ikep4j:message pre="${preView}" key="detail" />">
						<caption></caption>
						<tbody>
							<tr>
								<th width="18%" scope="row"><ikep4j:message pre="${preView}" key="apprDocCd" /><!--: ${apFormTpl.apprDocCd},${apFormTpl.isApprDoc}--></th>
								
								
								
								
								<td width="32%">
									
									<!--  ${apDoc.apprSecurityCd} / ${apFormTpl.isApprDoc }-->
									
									<c:if test="${apFormTpl.isApprDoc eq 'U'}">
									<c:forEach var="apCode" items="${listApprDocCd}">
										<label>
											<input type="radio" class="radio" title="${apCode.codeName}" name="apprSecurityCd"
													value="${apCode.codeId}" <c:if test="${apCode.codeId eq apDoc.apprSecurityCd}">checked</c:if>/>${apCode.codeName}
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
											name="apprDocNo" value="${apDoc.apprDocNo}" size="30" />

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
									${today}
								</td>
								<th scope="row"><ikep4j:message pre="${preView}" key="apprPeriodCd" /> <!--: ${apFormTpl.apprPeriodCd},${apFormTpl.isApprPeriod}--></th>
								<td>
									<c:if test="${apFormTpl.isApprPeriod eq 'U'}">
									<c:forEach var="apCode" items="${listApprPeriodCd}">
										<label>
											<input type="radio" class="radio" title="${apCode.codeName}" name="apprPeriodCd"
													value="${apCode.codeId}" <c:if test="${apCode.codeId eq apDoc.apprPeriodCd}">checked</c:if>/>${apCode.codeName}
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
													value="${apCode.codeId}" <c:if test="${apCode.codeId eq apDoc.apprTypeCd}">checked</c:if>/>${apCode.codeName}
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
									</c:forEach>
									</select>
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
								<td colspan=3>
									<input 	type="text" class="inputbox" title="<ikep4j:message pre="${preView}" key="title" />" 
											name="apprTitle" value="${apDoc.apprTitle}" size="100" <c:if test="${apFormTpl.isApprTitle eq 'Y'}">readonly onkeyDown='if(event.keyCode == 13 || event.keyCode == 8){event.returnValue=false;}'</c:if>  />
									<!--  onKeydown     backspace,enter 않되게 적용 -->
								</td>
							</tr>

							

						</tbody>
					</table>
					
					<table>
						<tr>
								
								<c:if test="${apFormTpl.isNoneForm eq 'Y'}">
									<textarea 	id="apprDocData" name="apprDocData" cols="" rows="5" class="inputbox w100 fullEditor" 
												title="<ikep4j:message pre="${preView}" key="apprDocData" />" >${apDoc.apprDocData}</textarea>
								</c:if>
								<c:if test="${apFormTpl.isNoneForm eq 'N'}">
												${apDoc.apprDocData}
								</c:if>	
							</tr>
							
					</table>
				</div>
				<!--//blockDetail End-->
				<input type="hidden" name="registUserId" value="${user.userId}" />
				<!--<input 	type="submit" value="저장" /> &nbsp; -->
				
				<!-- 수신처 테스트 -->
				<input type="hidden" name="etcName" id="etcName"/>
				<!-- 수신처 테스트 -->
				<!-- 열람권한지정 테스트 -->
				<input type="hidden" name="etcName1" id="etcName1"/>
				<!-- 열람권한지정 테스트 -->
				<!-- 결재선지정 -->
				<input type="hidden" name="etcName2" id="etcName2"/>
				<!-- 결재선지정  -->
				
			 <input type="hidden" name="apprId" id="apprId" value="${apDoc.apprId}"/>
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
             <input name="apprLineType" title="" type="hidden" value="${apDoc.apprLineType}" />
             
             <!-- 양식값 읽어오기 -->
             <input name="mailReqCd" title="" type="hidden" value="${apFormTpl.mailReqCd}" />
             <input name="mailReqWayCd" title="" type="hidden" value="${apFormTpl.mailReqWayCd}" />
             <input name="mailEndCd" title="" type="hidden" value="${apFormTpl.mailEndCd}" />
             <input name="mailEndWayCd" title="" type="hidden" value="${apFormTpl.mailEndWayCd}" />
             <input name="processId" title="" type="hidden" value="${apFormTpl.processId}" />
             <!-- 양식값 읽어오기 -->
             	
             <select name="addrGroupList2" multiple="multiple" size="5" style="width:300px;height:50px;display:none;" id="workers2">
             <c:forEach var="item" items="${apProcess}">
			 <c:choose>
						<c:when test="${item.apprOrder eq 0 }">
						</c:when>
						<c:otherwise>
							<option value="${item.apprType}^${item.apprUserId}" workerName="<c:if test="${item.apprType eq 0 }">결재</c:if><c:if test="${item.apprType eq 1 }">합의</c:if> ${item.userName}" jobTitle="${item.jobPositionName}" teamName="${item.teamName}">${item.userName}/${item.jobPositionName}/${item.teamName}</option>
						</c:otherwise> 
			</c:choose>             
			</c:forEach>
             </select>
</form:form>


</body>
</html>