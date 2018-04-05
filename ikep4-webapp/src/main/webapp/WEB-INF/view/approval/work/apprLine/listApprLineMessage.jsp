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

<c:set var="st" value=""></c:set>
<c:if test="${!empty apprDoc && apprDoc.apprDocStatus!='6'}">
	<c:set var="st" value="exist"></c:set>
</c:if>	
<c:if test="${st eq 'exist'}">

<script type="text/javascript">
<!-- 
(function($) {

	var	apprType	=	new Array("<ikep4j:message pre='${preCommon}' key='appr' />",
									"<ikep4j:message pre='${preCommon}' key='agree' />",
									"<ikep4j:message pre='${preCommon}' key='choiceAgree' />",
									"<ikep4j:message pre='${preCommon}' key='receive' />");
		
	// 결재선 정보
	var	items		=	[];

	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {

	});
})(jQuery);  
//-->
</script>
			
<!--blockListTable Start-->
<b><ikep4j:message pre='${preView}' key='apprMessage.title' /></b>
<div class="Approval_1">
	<table summary="<ikep4j:message pre='${preView}' key='summaryMsg' />">
		<caption></caption>
		<col style="width: 12%;"/>
		<col style="width: 30%;"/>
		<col />
		<thead>
			<tr>	
				<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='apprMessage.result' /></th>
				<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='apprMessage.approver' /></th>
				<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='apprMessage.message' /></th>							
			</tr>
		</thead>
		<tbody>
			<!-- 기안자의 결재요청 메시지 -->
			<c:if test="${!receiveEnd}">
			<tr>
			<td class="textCenter">
			<c:if test="${empty apprDoc.parentApprId}">
				<ikep4j:message pre='${preView}' key='apprMessage.register' />
			</c:if>
			<c:if test="${!empty apprDoc.parentApprId}">
				<ikep4j:message pre='${preView}' key='apprMessage.receiver' />
			</c:if>			
			</td>
			<td class="textCenter">${apprDoc.registerName} ${apprDoc.registerJobTitle} ${apprDoc.apprGroupName}</td>
			<td class="textLeft">
				<% pageContext.setAttribute("newLineChar", "\n"); %> 
				${fn:replace(apprDoc.registerMessage, newLineChar, '<br/>')}								
			</td>
			</tr>
			</c:if>						
			
			<c:if test="${!empty apprLineList}">
			<c:forEach var="item" items="${apprLineList}">
			<c:if test="${item.apprType!=3 && !empty item.apprMessage}">
			<tr>
			<td class="textCenter">
				<c:if test="${receiveEnd}">
				<ikep4j:message pre='${preView}' key='apprMessage.receiveEnd' />
				</c:if>
				<c:if test="${!receiveEnd}">
				<c:forEach var="code" items="${commonCode.apprStatusList}"> 
					<c:if test="${code.key eq item.apprStatus}"><ikep4j:message key='${code.value}'/></c:if>
				</c:forEach>
				</c:if>
			</td>
			<td class="textCenter">
				<c:if test="${item.approverType==0}">
					${item.approverName} ${item.approverJobTitle} ${item.approverGroupName}
					<c:if test="${!empty item.entrustUserId}">
						<br />
						<ikep4j:message pre='${preView}' key='entrust' /> ${item.entrustUserName} ${item.entrustJobTitle} ${item.entrustGroupName}
					</c:if>				
				</c:if>
				
				<c:if test="${item.approverType==1 && item.apprLineList ==null}">
				${item.approverGroupName}
				</c:if>		
											
				<c:if test="${item.approverType==1  && item.apprLineList !=null}">
				<c:forEach var="itemSub" items="${item.apprLineList}" varStatus='varStatus'>
					<c:if test="${itemSub.apprDate !=''}">
						${item.approverGroupName} ( ${itemSub.approverName} ${itemSub.approverJobTitle} )
						<c:if test="${!empty itemSub.entrustUserId}">
							<br />
							<ikep4j:message pre='${preView}' key='entrust' /> ${itemSub.approverGroupName} ( ${itemSub.entrustUserName} ${itemSub.entrustJobTitle} )
						</c:if>					
					</c:if>
				</c:forEach> 
				</c:if>
			</td>
			<td class="textLeft">
				<% pageContext.setAttribute("newLineChar", "\n"); %> 
				${fn:replace(item.apprMessage, newLineChar, '<br/>')}
			</td>
			</tr>
			</c:if>	
			</c:forEach>
			</c:if>		
		</tbody>
	</table>
</div>
</c:if>	
