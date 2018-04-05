<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.approval.work.entrust.form.header" />
<c:set var="preForm"  	value="ui.approval.work.entrust.form" />
<c:set var="preValidation" value="ui.approval.work.entrust.validation" />
<c:set var="preButton"  value="ui.approval.common.button" />
<c:set var="preMessage" value="ui.approval.common.message" />
<c:set var="preIcon"  	value="ui.approval.common.icon" />
 <%-- 메시지 관련 Prefix 선언 End --%>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>

<script type="text/javascript">
	<!--
	
	(function($){
		
		/**
		 * 부모창에서 넘어온값
		 */
		var selectData;
		/**
		 * 부모객체
		 */
		var dialogWindow;
		
		$(document).ready(function(){
			
			/**
			 * 부모창 전달값 처리.
			 * 
			 * @param {Object} params
			 * @param {Object} dialog
			 */
			fnCaller = function (params, dialog) {
				
				//$instanceLogId = $("input[name=instanceLogId]:hidden");
				
				if(params) {
					//alert(params.id);
					
					selectData = {
						id		: params.id,
						target	: params.target
					};
					
					//$instanceLogId.val(params.id);
				}
				
				dialogWindow 	= dialog;
			};
			
		});
	})(jQuery);
	//-->
</script>
	<div id="entrustLayer">
	<table summary="<ikep4j:message pre='${preForm}' key='summary'/>">
		<caption></caption>
		<tbody>
			<c:choose>
				<c:when test="${user.userId eq apprEntrust.signUserId}">
					<tr>
						<th scope="row" width="20%"><ikep4j:message pre='${preForm}' key='userName'/></th>
						<td>
							${apprEntrust.userName}
						</td>
					</tr>
				</c:when>
				<c:when test="${user.userId eq apprEntrust.userId}">
					<tr>
						<th scope="row" width="20%"><ikep4j:message pre='${preForm}' key='signUserName'/></th>
						<td>
							${apprEntrust.signUserName}
						</td>
					</tr>
				</c:when>
			</c:choose>
			<tr>
				<th scope="row" width="20%"><ikep4j:message pre='${preForm}' key='delegateperiod'/></th>
				<td>
					<ikep4j:timezone date="${apprEntrust.startDate}" pattern="yyyy.MM.dd"/> ~
					<ikep4j:timezone date="${apprEntrust.endDate}" pattern="yyyy.MM.dd"/>
				</td>
			</tr>
			<tr>
				<th scope="row"><ikep4j:message pre='${preForm}' key='reason'/></th>
				<% pageContext.setAttribute("newLineChar", "\n"); %> 
				<td>
					${fn:replace(apprEntrust.reason, newLineChar, '<br/>')}
				</td>
			</tr>
		</tbody>
	</table>	
	</div>