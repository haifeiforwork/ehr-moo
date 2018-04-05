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
<c:set var="portal"	value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript">
<!-- 
(function($) {
	
	fnCaller = function (params, dialog) {
		if(params) {
		}

		dialogWindow = dialog;
		$("#cancelButton").click(function() {
			dialogWindow.close();
		});
	};
	
	var	apprType	=	new Array("<ikep4j:message pre='${preCommon}' key='appr' />",
									"<ikep4j:message pre='${preCommon}' key='agree' />",
									"<ikep4j:message pre='${preCommon}' key='choiceAgree' />",
									"<ikep4j:message pre='${preCommon}' key='receive' />");
	
			// 문서완료여부
        getDocComplete = function(apprId,type) {

			// 문서 정보 확인
			var status = false;
		
			$jq.ajax({     
				url : '<c:url value="/approval/work/apprLine/getDocComplete.do"/>',     
				data :  {apprId : apprId},     
				type : "get", 
				async : false,
				success : function(result) {  
			
					if(result=="OK") {
						status = true;
					} else {
						if(type=="RESTORE") {
							alert('<ikep4j:message pre='${preMessage}' key='admin.canRestoreInProgress' />');
						} else {
							alert('<ikep4j:message pre='${preMessage}' key='admin.canWithdrawInProgress' />');
						}
						status = false;
						
					}
				},
				error : function(event, request, settings){
					 alert("error getDocComplete");
				}
			});		

			return status;
		}; 	
			
	//결재 회수
	ajaxUpdateApprDocInitAdmin = function() {
	
		if( getDocComplete($("#mainForm input[name=apprId]").val(),"WITHDRAW") ) {
		
        	if(!confirm("<ikep4j:message pre='${preMessage}' key='admin.withdraw' />")) 
        		return;
        
			$("body").ajaxLoadStart("button");
        	var apprId			=	$("#mainForm input[name=apprId]").val();
        
        
			$.post("<c:url value='/approval/work/apprWorkDoc/ajaxUpdateApprDocInitAdmin.do'/>", {apprId	:	apprId})
			.success(function(result) {
				$("body").ajaxLoadComplete();
				if(result == "OK") {
					 dialogWindow.callback(result+"W");
	                 dialogWindow.close();
				}
			})
			.error(function(event, request, settings) { alert("error"); });
			
		} else {
			dialogWindow.callback("NO");
			dialogWindow.close();
		}
			
	};
	
	//결재 복원
	updateApprovalLineRestore = function() {
	
	
		if( getDocComplete($("#mainForm input[name=apprId]").val(),"RESTORE") ) {
			if(!confirm("<ikep4j:message pre='${preMessage}' key='admin.restore' />?"))
				return;		
			$("body").ajaxLoadStart("button");
	
			// 체크박스 활성화
			$("input:checkbox").removeAttr('disabled');	
			
			$jq.ajax({
				url : '<c:url value='/approval/work/apprLine/updateApprovalLineRestore.do'/>',
				data : $jq("#mainForm").serialize(),
				type : "post",
				success : function(result) {
					$("body").ajaxLoadComplete();

					if(result == "OK") {
						dialogWindow.callback(result+"R");
	  	               	dialogWindow.close();
					}
				},
				error : function(event, request, settings) { alert("error"); }
			});	
		} else {
			dialogWindow.callback("NO");
			dialogWindow.close();
		}
	};
	
	checkBox = function(curApproverId){
 
		var chkLen			=	$("input[name=approverIds]:checkbox").length;
		
		var tmpIndex		=	'';	// 선택한 요소의 Index Default : 100
		var check			=	false;	// 현재 클릭한 checkbox 상태
		var tmpApprOrder	=	100;	// 선택한 apprOrder 값
		var curApprOrder	=	0;
		var apprOrder		=	0;
		var countSameOrder	=	0;
		var firstSameOrder	=	100;
		var curCheck		=	0;
 
		
		$("input[name=approverIds]").each(function(index){			
			if(curApproverId==$(this).val()){
				tmpIndex	=	index;
				check		=	$(this).is(":checked");
				curApprOrder=	$("#mainForm input[name=apprOrder"+index+"]").val();	
			}	
			tmpApprOrder=	$("#mainForm input[name=apprOrder"+index+"]").val();						
			if(apprOrder == tmpApprOrder) {
				if(countSameOrder==0)
					firstSameOrder=tmpApprOrder;	
				countSameOrder++;				
			} else {
				apprOrder =tmpApprOrder;
			}

		});
		$("input[name=approverIds]").each(function(index){			
			tmpApprOrder=	$("#mainForm input[name=apprOrder"+index+"]").val();			
			if(firstSameOrder== tmpApprOrder && $(this).is(":checked") ){
				curCheck++;
			}
		});	 	

		$("input[name=approverIds]").each(function(index){

			apprOrder	=	$("#mainForm input[name=apprOrder"+index+"]").val();			

			// 현재 클릭한 요소 이후 check box 상태 변경
			if( index > tmpIndex )
			{
				if( curApprOrder == apprOrder ) {
					$(this).removeAttr('disabled');
				} else if( !check ) {
					if( countSameOrder>0 ) {
						if(firstSameOrder== apprOrder) { 
							$(this).removeAttr('disabled');	
						} else {
							if(curCheck>0 || firstSameOrder == apprOrder )	{
							} else {
								this.checked = true;
								$(this).removeAttr('disabled');								
							}						
						}				
					} else {
						if( tmpIndex+1 == index ) {
							this.checked = true;
							$(this).removeAttr('disabled');
						}
					}				
				} else {
					this.checked = true;
					$(this).attr('disabled',true);
				}			
			}
		});
	};
 
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {
		
	});
})(jQuery);  
//-->
</script>
			
			
	<!--popup_contents Start-->
	<div id="popup_contents_2">		
		<div class="blockBlank_10px"></div>
	
		<!--subTitle_2 Start-->
		<div id="pageTitle">
			<h2><ikep4j:message pre='${preView}' key='admin.pageTitle' /></h2>
		</div>
		<!--//subTitle_2 End-->		
		
		<!--blockListTable Start-->
		<form id="mainForm" method="post">
		<input name="apprId" title="" type="hidden" value="${apprDoc.apprId}" />
		
		<div class="blockDetail">
			<table summary="<ikep4j:message pre='${preView}' key='admin.summary' />">
				<caption></caption>
				<col style="width: 10%;"/>
				<col />
				<col style="width: 15%;"/>
				<col style="width: 15%;"/>
				<col style="width: 15%;"/>
				<thead>
					<tr>				
						<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='admin.no' /></th>
						<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='admin.approver' /></th>
						<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='admin.apprType' /></th>
						<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='admin.status' /></th>
						<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='admin.restore' /></th>
					</tr>
				</thead>
				<tbody>

					<c:if test="${!empty apprLineList}">
					<c:forEach var="item" items="${apprLineList}" varStatus="status">
					<c:if test="${item.apprType!=3}">

					<tr>
					<td class="textCenter">${item.apprOrder}</td>
					<td class="textCenter">
					
						<c:if test="${item.approverType==0}">
						${item.approverName} ${item.approverJobTitle} ${item.approverGroupName} 
						</c:if>
						
						<c:if test="${item.approverType==1 && item.apprLineList==null}">
						${item.approverGroupName}
						</c:if>	
												
						<c:if test="${item.approverType==1 && item.apprLineList!=null}">
						<c:forEach var="itemSub" items="${item.apprLineList}" varStatus='varStatus'>
						
						<c:if test="${itemSub.apprDate !=''}">
						<a href="#a" onclick="apprDocStatus('${itemSub.apprId}');">${itemSub.approverGroupName} ( ${itemSub.approverName} ${itemSub.approverJobTitle} )</a>
						</c:if>
						
						</c:forEach> 
						</c:if>	
											
					</td>
					<td class="textCenter">							
						<c:forEach var="code" items="${commonCode.apprTypeList}">
							<c:if test="${code.key eq item.apprType}"><ikep4j:message key='${code.value}'/></c:if>
						</c:forEach>
					</td>							
					<td class="textCenter">
						<c:forEach var="code" items="${commonCode.apprStatusList}">
							<c:if test="${code.key eq item.apprStatus}"><ikep4j:message key='${code.value}'/></c:if>
						</c:forEach>	
					</td>
					<td class="textCenter">
						<c:if test="${item.apprStatus>1}">
							<input type="checkbox"	name="approverIds" 					value="${item.approverId}"	title="checkbox"	onclick="checkBox(this.value)"/>
							<input type="hidden"	name="apprOrder${status.index}" 	value="${item.apprOrder}"	title="checkbox"	onclick="checkBox(this.value)"/>
						</c:if>
					</td>
					</tr>
					
					</c:if>					
					</c:forEach>
					</c:if>		
				</tbody>
			</table>
		</div>
		</form>
						
		<div class="clear"></div>		
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a id="ajaxUpdateApprDocInitAdmin"	onclick="ajaxUpdateApprDocInitAdmin()" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='withdraw' /></span></a></li>
				<li><a id="updateApprovalLineRestore"	onclick="updateApprovalLineRestore()"  class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='restore' /></span></a></li>
			</ul>
		</div>
	</div>
