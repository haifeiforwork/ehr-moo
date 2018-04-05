<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preMessage"	value="message.approval.work.apprLine.listApprLineView"/>					
<c:set var="preHeader"	value="ui.approval.work.apprLine.listApprLineView.header"/>
<c:set var="preList"	value="ui.approval.work.apprLine.listApprLineView.list"/>
<c:set var="preView"	value="ui.approval.work.apprLine.listApprLineView.view"/>
<c:set var="preSearch"	value="ui.approval.work.apprLine.listApprLineView.search"/>
<c:set var="preButton"	value="ui.approval.work.apprLine.listApprLineView.button"/>	
<c:set var="preCommon"	value="ui.approval.common.apprDefLine.code"/>
			
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript">
<!-- 
(function($) {
	
	var	apprType	=	new Array("<ikep4j:message pre='${preCommon}' key='appr' />",
		"<ikep4j:message pre='${preCommon}' key='agree' />",
		"<ikep4j:message pre='${preCommon}' key='choiceAgree' />",
		"<ikep4j:message pre='${preCommon}' key='receive' />");
	
	// 결재선 정보
	var	items			=	[];
	var	receiveItems	=	[];
	
	itemSet = function(items,item) {			
			items.push({// 공통필수
				type			:	'user',
				apprType		:	item.apprType,
				id				:	item.id,								
				userName		:	item.userName,
				teamName		:	item.teamName,
				jobTitleName	:	item.jobTitleName,			
				approverType	:	0,//결재유형 사용자:0,부서:1
				apprOrder		:	item.apprOrder,				
				apprStatus		:	item.apprStatus,
				lineModifyAuth	:	item.lineModifyAuth,
				docModifyAuth	:	item.docModifyAuth,	
				readModifyAuth	:	item.readModifyAuth,	
				apprDate		:	item.apprDate,
				name			:	apprType[item.apprType]+" "+item.userName +" "+item.jobTitleName
			});	
	};
	
	initItemSet	=	function(){
		// 결재문서의 결재선 정보 초기 설정
		<c:if test="${!empty apprLineList}">
		<c:forEach var="item" items="${apprLineList}">	
		items.push({// 공통필수
			type			:	"user",
			apprType		:	"${item.apprType}",
			id				:	"${item.approverId}",								
			userName		:	"${item.approverName}",
			teamName		:	"${item.approverGroupName}",
			jobTitleName	:	"${item.approverJobTitle}",		
			approverType	:	0,//결재유형 사용자:0,부서:1
			apprOrder		:	${item.apprOrder},				
			apprStatus		:	${item.apprStatus},
			lineModifyAuth	:	${item.lineModifyAuth},
			docModifyAuth	:	${item.docModifyAuth},	
			readModifyAuth	:	${item.readModifyAuth},	
			apprDate		:	"${item.apprDate}",
			name			:	apprType[${item.apprType}]+" "+"${item.approverName}" +" "+"${item.approverJobTitle}"
		});	

		</c:forEach>
		</c:if>	
	}



	receiveItemSet = function(receiveItems,item) {			
			receiveItems.push({// 공통필수
				type			:	item.type,
				apprType		:	item.apprType,
				id				:	item.id,
				code			:	item.code,
				userName		:	item.userName,
				teamName		:	item.teamName,
				jobTitleName	:	item.jobTitleName,			
				approverType	:	item.type=="group"?1:0,//결재유형 사용자:0,부서:1
				apprOrder		:	item.apprOrder,				
				apprStatus		:	item.apprStatus,
				lineModifyAuth	:	item.lineModifyAuth,
				docModifyAuth	:	item.docModifyAuth,	
				readModifyAuth	:	item.readModifyAuth,	
				apprDate		:	item.apprDate,
				name			:	apprType[item.apprType]+" "+item.teamName
			});	
	};

	
	initReceiveItemSet	=	function(){
		// 결재문서의 결재선 정보 초기 설정
		<c:if test="${!empty apprReceiveLineList}">
		<c:forEach var="item" items="${apprReceiveLineList}">	
		receiveItems.push({// 공통필수
			type			:	${item.approverType}==1?"group":"user",
			apprType		:	"${item.apprType}",
			id				:	"${item.approverId}",
			code			:	"${item.approverId}",
			userName		:	"${item.approverName}",
			teamName		:	"${item.approverGroupName}",
			jobTitleName	:	"${item.approverJobTitle}",		
			approverType	:	${item.approverType},
			apprOrder		:	${item.apprOrder},				
			apprStatus		:	${item.apprStatus},
			lineModifyAuth	:	${item.lineModifyAuth},
			docModifyAuth	:	${item.docModifyAuth},	
			readModifyAuth	:	${item.readModifyAuth},	
			apprDate		:	"${item.apprDate}",
			name			:	apprType[${item.apprType}]+" "+"${item.approverName}"
		});	

		</c:forEach>
		</c:if>	
	}	
	
	
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {

		// 기존 결재선 정보 초기화 (DB)
		initItemSet();
		// 기존 수신처 정보 초기화 (DB)
		initReceiveItemSet();
		
		
		getApprDefLine(0);
		
		$jq("#addApprLineButton").click(function() {
			
			var $apprId	=	$jq('input[name=apprId]:hidden').val();
			var callback = function(result){

				$("#apprLineList").empty();
				items	=	[];
				
				$jq.each(result, function() {
					$("<tr><td> "+this.id+"</td><td> "+this.userName+"</td><td> "+this.jobTitleName+"</td><td> "+this.teamName+"</td><td> "+apprType[this.apprType]+"</td><td> "+this.lineModifyAuth+"</td><td> "+this.docModifyAuth+"</td><td> "+this.readModifyAuth+"</td></tr>").appendTo("#apprLineList");
					itemSet(items,this);		
				});
			};
			iKEP.showApprovalLine(callback, items, {selectType:"all",apprId:$apprId, apprType:0,apprLineType:${apprDoc.apprLineType},  isAppend:false, tabs:{common:0, personal:0, collaboration:0, sns:0}});			
		});	
		
		$jq("#addApprReceiveLineButton").click(function() {
			var $apprId	=	$jq('input[name=apprId]:hidden').val();
			var callback = function(result){
				$("#apprReceiveLineList").empty();
				receiveItems	=	[];
				
				$jq.each(result, function() {
					if(this.type=='user') {
						$("<tr><td> "+this.id+"</td><td> "+this.userName+"</td><td> "+this.jobTitleName+"</td><td> "+this.teamName+"</td><td> "+apprType[this.apprType]+"</td><td> "+this.lineModifyAuth+"</td><td> "+this.docModifyAuth+"</td><td> "+this.readModifyAuth+"</td></tr>").appendTo("#apprReceiveLineList");
					} else {
						$("<tr><td> "+this.code+"</td><td> "+this.teamName+"</td><td> "+this.jobTitleName+"</td><td> "+this.teamName+"</td><td> "+apprType[this.apprType]+"</td><td> "+this.lineModifyAuth+"</td><td> "+this.docModifyAuth+"</td><td> "+this.readModifyAuth+"</td></tr>").appendTo("#apprReceiveLineList");
					}
					receiveItemSet(receiveItems,this);		
				});
			};
			iKEP.showApprovalLine(callback, receiveItems, {selectType:"all",apprId:$apprId,apprType:3,apprLineType:${apprDoc.apprLineType},  isAppend:false, tabs:{common:0, personal:0, collaboration:0, sns:0}});			
		});			


		// 사용자 결재선 등록
		$("#btnSave").click(function() {
		
		
			// 결재선 지정
			var apprLineType= $("input[name=apprLineType]").val();
			
			var apprLineList1=[];	//	결재
			var	apprLineList2=[];	//	합의

			var first = '';			//	병렬합의 첫 idx 저장
			var idx=1;				//	apprOrder 시작
			var	receiveIdx=0;		//	수신처 시작 apprOrder
			
			if(apprLineType==1) {	//	병렬합의인 경우 apprOrder 정의			
				idx=1;
				$jq.each(items, function() {
					if(this.apprType==1 || this.apprType==2) {
						if(first=='')
							first=idx;						
						this.apprOrder=first;
						apprLineList2.push(this);
					}
					idx++;
				});
				
				idx=1;
				$jq.each(items, function() {
					if(this.apprType==0) {
						if(first!='' && idx==first)
							idx=idx+1;
						this.apprOrder=idx;
						apprLineList1.push(this);
						idx++;
					}	
				});				
			} else {				// 직렬합의인 경우 apprOrder 정의
				idx=1;
				$jq.each(items, function() {
					this.apprOrder=idx;
					apprLineList1.push(this);
					idx++;
				});
			}
			receiveIdx=idx;
			
			items	=	$.merge(apprLineList1,apprLineList2);

			idx=0;
			$("#apprLineInfoDiv").empty();
			$jq.each(items, function() {
				$("<input type='hidden' name='apprLineList[" + idx + "].apprType' 			value='"+ this.apprType +"' />").appendTo( "#apprLineInfoDiv" );  
				$("<input type='hidden' name='apprLineList[" + idx + "].approverId'			value='"+ this.id +"' />").appendTo( "#apprLineInfoDiv" );
				$("<input type='hidden' name='apprLineList[" + idx + "].approverName'		value='"+ this.userName +"' />").appendTo( "#apprLineInfoDiv" );
				$("<input type='hidden' name='apprLineList[" + idx + "].approverGroupName'	value='"+ this.teamName +"' />").appendTo( "#apprLineInfoDiv" ); 
				$("<input type='hidden' name='apprLineList[" + idx + "].approverJobTitle'	value='"+ this.jobTitleName +"' />").appendTo( "#apprLineInfoDiv" );
				$("<input type='hidden' name='apprLineList[" + idx + "].approverType'		value='"+ this.approverType +"' />").appendTo( "#apprLineInfoDiv" );				
				$("<input type='hidden' name='apprLineList[" + idx + "].apprOrder'			value='"+ this.apprOrder +"' />").appendTo( "#apprLineInfoDiv" );
				$("<input type='hidden' name='apprLineList[" + idx + "].apprStatus'			value='"+ this.apprStatus +"' />").appendTo( "#apprLineInfoDiv" );
	
				$("<input type='hidden' name='apprLineList[" + idx + "].lineModifyAuth'		value='"+ this.lineModifyAuth +"' />").appendTo( "#apprLineInfoDiv" );
				$("<input type='hidden' name='apprLineList[" + idx + "].docModifyAuth'		value='"+ this.docModifyAuth +"' />").appendTo( "#apprLineInfoDiv" );
				$("<input type='hidden' name='apprLineList[" + idx + "].readModifyAuth'		value='"+ this.readModifyAuth +"' />").appendTo( "#apprLineInfoDiv" );				
				idx++;
			});			
			// 결재선 지정 End
			
			
			// 수신처 지정 Start
			idx=0;
			$("#apprReceiveLineInfoDiv").empty();
			var id="";
			$jq.each(receiveItems, function() {
				if(this.type=='group'){
					id=this.code;
				} else {
					id=this.id;
				}
				$("<input type='hidden' name='apprReceiveLineList[" + idx + "].apprType' 			value='"+ this.apprType +"' />").appendTo( "#apprLineInfoDiv" );  
				$("<input type='hidden' name='apprReceiveLineList[" + idx + "].approverId'			value='"+ id +"' />").appendTo( "#apprLineInfoDiv" );
				$("<input type='hidden' name='apprReceiveLineList[" + idx + "].approverName'		value='"+ this.userName +"' />").appendTo( "#apprLineInfoDiv" );
				$("<input type='hidden' name='apprReceiveLineList[" + idx + "].approverGroupName'	value='"+ this.teamName +"' />").appendTo( "#apprLineInfoDiv" ); 
				$("<input type='hidden' name='apprReceiveLineList[" + idx + "].approverJobTitle'	value='"+ this.jobTitleName +"' />").appendTo( "#apprLineInfoDiv" );
				$("<input type='hidden' name='apprReceiveLineList[" + idx + "].approverType'		value='"+ this.approverType +"' />").appendTo( "#apprLineInfoDiv" );				
				$("<input type='hidden' name='apprReceiveLineList[" + idx + "].apprOrder'			value='"+ receiveIdx +"' />").appendTo( "#apprLineInfoDiv" );
				$("<input type='hidden' name='apprReceiveLineList[" + idx + "].apprStatus'			value='"+ this.apprStatus +"' />").appendTo( "#apprLineInfoDiv" );
	
				$("<input type='hidden' name='apprReceiveLineList[" + idx + "].lineModifyAuth'		value='"+ this.lineModifyAuth +"' />").appendTo( "#apprLineInfoDiv" );
				$("<input type='hidden' name='apprReceiveLineList[" + idx + "].docModifyAuth'		value='"+ this.docModifyAuth +"' />").appendTo( "#apprLineInfoDiv" );
				$("<input type='hidden' name='apprReceiveLineList[" + idx + "].readModifyAuth'		value='"+ this.readModifyAuth +"' />").appendTo( "#apprLineInfoDiv" );				
				idx++;
			});			
			// 수신처 지정 End
			
			
			$.ajax({
				url : "<c:url value='/approval/work/apprLine/saveApprLine.do' />",
				type : "post",
				data : $("#apprDocForm").serialize(),
				success : function(result) {
					location.href= "<c:url value='/approval/work/apprLine/listApprLineView.do'/>?apprId=0000"; //목록화면으로 포워딩
				},
				error : function(xhr, exMessage) {					
					var errorItems = $.parseJSON(xhr.responseText).exception;
					validator.showErrors(errorItems);
				}
			});			

			
			return false; 
		});

		

	});
	apprDocStatus = function(apprId){
		$target =$("#apprDocStatus");
		$target.empty();
		//$target.toggle();
		$.post("<c:url value='/approval/work/apprLine/listApprLineInfo.do'/>", {apprId	:	apprId})
			.success(function(result) {
				$target.append(result);
			})
			.error(function(event, request, settings) { alert("error"); });
	};
	
	// Appr User Line Select List
	getApprDefLine = function(defLineType) {		
        $jq.get('<c:url value="/approval/admin/apprDefLine/getApprDefLine.do"/>',{defLineType:defLineType},
			function(data) {
				$jq("#userLineId option:eq(0)").nextAll().remove();	
				$jq.each(data, function() {	
					$jq("<option/>").attr("value",this.defLineId).text(this.defLineName).appendTo("#defLineId");
				});
				
				$("#defLineId > option[value='100096391094']").attr("selected",true);		
			}
		)
		return false; 
	};		
	
	//결재처리
	approvalReqPopup = function(apprId) {
		
		dialog = new iKEP.Dialog({     
			title 		: "결재처리",
			url 		: "<c:url value='/approval/work/apprLine/updateApprovalForm.do'/>?apprId="+apprId,
			modal 		: true,
			width 		: 700,
			height 		: 450,
			params 		: {apprId:apprId},
			callback : function() {
				//검토요청 결과 뿌려주기
			}
		});
	};
	
})(jQuery);  
//-->
</script>
			
<h1 class="none"><ikep4j:message pre='${preHeader}' key='pageTitle' /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2>기안문 작성</h2>
</div>
<!--//pageTitle End-->

<!--blockSearch Start-->
<form id="apprDocForm" name="apprDocForm" method="post" action="<c:url value="/approval/work/apprLine/listApprLineView.do"/>">

	<input name="apprId" title="" type="hidden" value="0000" />
	<input name="apprLineType" title="" type="hidden" value="${apprDoc.apprLineType}" />
	
	<div id="apprLineInfoDiv"></div>
	<div id="apprReceiveLineInfoDiv"></div>
	
	<input name="controlTabType" title="" type="hidden" value="1:0:0:0" />
	<input name="controlType" title="" type="hidden" value="ORG" />
	<input name="selectType" title="" type="hidden" value="ALL" />
	<input name="selectMaxCnt" title="" type="hidden" value="100" />
	<input name="searchSubFlag" title="" type="hidden" value="" />	

	<select title="aaaa" id="defLineId" style="width:80%;">
	<option value=''>	--	선택	--	</option>
	</select>	
	
<!--blockListTable Start-->
<div class="blockListTable">



결재문서 번호 : 0000 <br />

 기존에 설정된 결재선(수신제외) 정보 세팅
	<table summary="<ikep4j:message pre='${prePreView}' key='summary' />">
		<caption></caption>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>
		<col style="width: 20%;"/>
		<col style="width: 20%;"/>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>
		<col style="width: 5%;"/>
		<col style="width: 5%;"/>		
		<thead>
			<tr>				
				<th scope="col">결재ID</th>
				<th scope="col">이름</th>
				<th scope="col">직위</th>
				<th scope="col">부서</th>
				<th scope="col">타입</th>
				<th scope="col">Line</th>
				<th scope="col">Doc</th>
				<th scope="col">Read</th>	
			</tr>
		</thead>
		<tbody id='apprLineList'>
			<c:if test="${!empty apprLineList}">
			<c:forEach var="item" items="${apprLineList}">
			<tr>
			<td> ${item.approverId}</td>
			<td> ${item.approverName}</td>
			<td> ${item.approverJobTitle}</td>
			<td> ${item.approverGroupName}</td>
			<td> ${item.apprType}</td>
			<td> ${item.lineModifyAuth}</td>
			<td> ${item.docModifyAuth}</td>
			<td> ${item.readModifyAuth}</td>
			</tr>
			</c:forEach>
			</c:if>		
		</tbody>
	</table>	
<br />
<br />
 기존에 설정된 결재선(수신만) 정보 세팅
	<table summary="<ikep4j:message pre='${prePreView}' key='summary' />">
		<caption></caption>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>
		<col style="width: 20%;"/>
		<col style="width: 20%;"/>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>
		<col style="width: 5%;"/>
		<col style="width: 5%;"/>		
		<thead>
			<tr>				
				<th scope="col">결재ID</th>
				<th scope="col">이름</th>
				<th scope="col">직위</th>
				<th scope="col">부서</th>
				<th scope="col">타입</th>
				<th scope="col">Line</th>
				<th scope="col">Doc</th>
				<th scope="col">Read</th>	
			</tr>
		</thead>
		<tbody id='apprReceiveLineList'>
			<c:if test="${!empty apprReceiveLineList}">
			<c:forEach var="item" items="${apprReceiveLineList}">
			<tr>
			<td> ${item.approverId}</td>
			<td> ${item.approverName}</td>
			<td> ${item.approverJobTitle}</td>
			<td> ${item.approverGroupName}</td>
			<td> ${item.apprType}</td>
			<td> ${item.lineModifyAuth}</td>
			<td> ${item.docModifyAuth}</td>
			<td> ${item.readModifyAuth}</td>
			</tr>
			</c:forEach>
			</c:if>		
		</tbody>
	</table>				
			

</div>
</form>
<!--//blockListTable End-->	
<div id='apprDocStatus'></div>
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a class="button" href="#a" onclick="apprDocStatus('0000')"><span>문서상태</span></a></li>	
		<li><a id="btnSave" class="button" href="#a"><span>결재요청/임시저장</span></a></li>
		<li><a id="addApprReceiveLineButton" class="button" href="#a"><span>수신처 지정</span></a></li>
		<li><a id="addApprLineButton" class="button" href="#a"><span>결재선 지정</span></a></li>
		<li><a id="saveButton" class="button" href="#a"  onclick="approvalReqPopup('0000')"><span>결재요청</span></a></li>
	</ul>
</div>

<div id="previewDetailDiv"></div>

<!--//blockButton End-->