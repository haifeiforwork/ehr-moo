<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preButton"  	value="ui.approval.common.button" />
<c:set var="preMessage"    	value="ui.approval.common.message" />
<c:set var="preDefault"     value="ui.approval.collaboration.default" />
<c:set var="preMessage2"    value="ui.approval.collaboration.message" />
<c:set var="preTitle"    	value="ui.approval.proposal.view.title" />
<c:set var="preButton2"  	value="ui.approval.collaboration.button" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript">
	<!--
	var dialogWindow = null;
	var fnCaller;
	var validator;
	(function($){
		var dblClick=false;
		
		fnCaller = function (params, dialog) {
			if(params) {
			}

			dialogWindow = dialog;
			$(".cancelButton").click(function() {
				dialogWindow.close();
			});
		};
		
		$(document).ready(function(){
			
			// 저장
			$("#saveButton").click(function() {
				
				$("#editForm").submit();
				return false; 
			});
			
			// [validation] ============================================= Start		
			var rangeLength2000Msg = "<ikep4j:message pre='${preMessage}' key='rangelength.2000'/>";
			var validOptions = {
				rules			: {
					opinionValue : {required : true, rangelength : [1, 2000]},
			  	},
			  	messages		: {
			  		opinionValue : {
			  			required: "<ikep4j:message pre='${preTitle}' key='opinion'/>" + " " + "<ikep4j:message pre='${preMessage2}' key='required'/>"
				    }
			  	},
			    submitHandler : function(form) {

					if (!confirm("<ikep4j:message pre='${preMessage2}' key='save'/>")) {
						return;
					}
					
			 		$jq.ajax({
						url : iKEP.getContextRoot() + "/approval/collaboration/proposal/ajaxUpdateOpinion.do",
						type : "post",
						data : $("#editForm").serialize(),
						loadingElement : "#saveButton",
						success : function(data, textStatus, jqXHR) {
							alert("<ikep4j:message pre='${preMessage2}' key='suceess' />");
							dialogWindow.callback();
							dialogWindow.close();
							return false;
						},
						error : function(jqXHR, textStatus, errorThrown) {
							
							var arrException = $jq.parseJSON(jqXHR.responseText).exception;
							alert(arrException[0].defaultMessage);
						}
					});
				}
			};
			validator = new iKEP.Validator("#editForm", validOptions);
		});
		
	})(jQuery);
	//-->
</script>
	<h1 class="none"><ikep4j:message pre='${preTitle}' key="opinion" /></h1>
	
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre="${preTitle}" key="opinion" /></h2> 
	</div>
	<!--//pageTitle End-->
	
	<!--blockDetail Start-->
	<div class="blockDetail" style="width: 98%;">
		<div>
			<form id="editForm" name="editForm" method="post" action="">
				<input type="hidden" name="proposalNo" value="${proposal.proposalNo}"/>
				<input type="hidden" name="opinionType" value="${proposal.opinionType}"/>
				<table summary="<ikep4j:message pre='${preView}' key='summary'/>">
					<caption></caption>
					<tbody>
						<tr>
							<!-- 부서별의견 -->
							<th scope="row" width="20%" style="text-align:center;"><ikep4j:message pre='${preTitle}' key='opinion' /></th>
		                    <td width="*">
								<textarea name="opinionValue" style="line-height:16px; overflow:auto; width:98%; height:200px; border:none; background:none;">${ proposal.opinionValue}</textarea>
		                    </td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
		<div class="clear"></div>
		
		<!--blockButton Start-->
		<div class="blockButton" style="margin-top: 10px;"> 
			<ul>
				<li><a id="saveButton" class="button" href="#a"><span><ikep4j:message pre='${preButton2}' key='save'/></span></a></li>
				<li><a class="button cancelButton" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
	<!--//blockDetail End-->
