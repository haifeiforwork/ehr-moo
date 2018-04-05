<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preHeader"		value="ui.approval.work.apprLine.updateApprovalForm.header"/>
<c:set var="preView"		value="ui.approval.work.apprLine.updateApprovalForm.view"/>
<c:set var="preValidation" 	value="ui.approval.work.apprLine.updateApprovalForm.validation" />
<c:set var="preButton"  	value="ui.approval.work.apprLine.updateApprovalForm.button" />	
<c:set var="preMessage" 	value="message.approval.work.apprLine.updateApprovalForm" />				
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript">
	<!--
	var dialogWindow = null;
	var fnCaller;
	var	DOC2_APPR	="<ikep4j:message pre='${preMessage}' key='doc2Appr'/>";
	var DOC3_APPR	="<ikep4j:message pre='${preMessage}' key='doc3Appr'/>";
	var	DOC4_APPR	="<ikep4j:message pre='${preMessage}' key='doc4Appr'/>";
	var	LINE0_APPR	="<ikep4j:message pre='${preMessage}' key='line0Appr'/>";
	var	LINE2_APPR	="<ikep4j:message pre='${preMessage}' key='line2Appr'/>";
	var	LINE3_APPR	="<ikep4j:message pre='${preMessage}' key='line3Appr'/>";
	var	LINE4_APPR	="<ikep4j:message pre='${preMessage}' key='line4Appr'/>";	
	
	(function($){
		var dblClick=false;
		
		fnCaller = function (params, dialog) {
			if(params) {
			}

			dialogWindow = dialog;
			$("#cancelButton").click(function() {
				dialogWindow.close();
			});
		};
		// 문서 현재 상태 체크
		var entrustUserId = "${apprLine.entrustUserId}";
        getDocStatus = function(apprId) {

			// 문서 정보 확인
			var status = false;			
			$jq.ajax({     
				url : '<c:url value="/approval/work/apprLine/getDocStatus.do"/>',     
				data :  {apprId:apprId, entrustUserId : entrustUserId},     
				type : "get", 
				async : false,
				success : function(result) {   			
					if(result=="OK") {
						status = true;
					} else {
						var str = eval(result+'_'+type);
						alert(str);
						
						$("body").ajaxLoadComplete();
					}
				},
				error : function(event, request, settings){
					 alert("error");
					 dblClick=false;
					 $("body").ajaxLoadComplete();
				}
			});			
			return status;
		}; 		
		$(document).ready(function(){
						
			$("#saveButton").click(function() {
				$("#myForm").submit();
				return false; 
			});
		
			//입력값 확인
			var validOptions = {
				submitHandler	: function(form) {
					if(!dblClick) {
						dblClick=true;
					} else {
						//$("body").ajaxLoadComplete();
						alert("<ikep4j:message pre='${preMessage}' key='processing'/>");
						return false;
					}
					var apprId = $("#myForm input[name=apprId]").val();
					$("body").ajaxLoadStart("button");
					
					<c:if test="${isPassword eq '1'}">
						$jq.ajax({     
							url : '<c:url value="/approval/work/apprLine/confirmApprPassword.do" />',     
							data :  $jq("#myForm").serialize(),     
							type : "post",     
							success : function(result) {
								if(result == "nowPasswordError") {
									alert("<ikep4j:message pre="${preValidation}" key="nowPasswordDisagreement" />");
									$("body").ajaxLoadComplete();
									dblClick=false;
									return false;
								}
								else {
									// 문서 상태 확인
									if( getDocStatus(apprId) ) {						
    
										$("body").ajaxLoadStart("button");
										
										var apprSts = $jq(form).find("input[name=apprStatus]:checked").val();
										var apprStr = "", apprMsg="";
										if(apprSts == "2")  {
											apprStr="<ikep4j:message pre='${preView}' key='approval'/>";
											apprMsg="<ikep4j:message pre='${preView}' key='approvalMsg'/> ";
										}
										if(apprSts == "4") { 
											apprStr="<ikep4j:message pre='${preView}' key='agreement'/> ";
											apprMsg="<ikep4j:message pre='${preView}' key='agreementMsg'/> ";
										}
										if(apprSts == "3") {
											apprStr="<ikep4j:message pre='${preView}' key='reject'/> ";
											apprMsg="<ikep4j:message pre='${preView}' key='rejectMsg'/> ";
										}
										
										if (!confirm(apprStr+"<ikep4j:message pre='${preView}' key='qa'/>")) {
											$("body").ajaxLoadComplete();
											dblClick=false;
											return false;
										}
										if($jq(form).find("textarea[name=apprMessage]").val() == '') {
											$jq(form).find("textarea[name=apprMessage]").val(apprMsg);	
										}
										
										$jq.ajax({     
											url : '<c:url value="/approval/work/apprLine/updateApproval.do"/>',     
											data :  $jq("#myForm").serialize(),     
											type : "post",     
											success : function(result) {      
												
												if(result == "OK") {
													 dialogWindow.callback(result);
													 dialogWindow.close();
												}
											},
											error : function(event, request, settings){
												 alert("error");
												 dblClick=false;
												 $("body").ajaxLoadComplete();
											}
										});
									}	
								
								}
							},
							error : function(event, request, settings){
								 alert("error");
								 dblClick=false;
								 $("body").ajaxLoadComplete();
							}
						});
					</c:if>
					<c:if test="${isPassword eq '0'}">

								
						// 문서 상태 확인
						if( getDocStatus(apprId) ) {     
							//$("body").ajaxLoadStart("button");
							var apprSts = $jq(form).find("input[name=apprStatus]:checked").val();
							var apprStr = "", apprMsg="";
							if(apprSts == "2")  {
								apprStr="<ikep4j:message pre='${preView}' key='approval'/> ";
								apprMsg="<ikep4j:message pre='${preView}' key='approvalMsg'/> ";
							}
							if(apprSts == "4") { 
								apprStr="<ikep4j:message pre='${preView}' key='agreement'/> ";
								apprMsg="<ikep4j:message pre='${preView}' key='agreementMsg'/> ";
							}
							if(apprSts == "3") {
								apprStr="<ikep4j:message pre='${preView}' key='reject'/> ";
								apprMsg="<ikep4j:message pre='${preView}' key='rejectMsg'/> ";
							}
							if (!confirm(apprStr+"<ikep4j:message pre='${preView}' key='qa'/>")) {
								$("body").ajaxLoadComplete();
								dblClick=false;
								return false;
							}
							if($jq(form).find("textarea[name=apprMessage]").val() == '') {
								$jq(form).find("textarea[name=apprMessage]").val(apprMsg);	
							}
							$jq.ajax({     
								url : '<c:url value="/approval/work/apprLine/updateApproval.do"/>',     
								data :  $jq("#myForm").serialize(),     
								type : "post",     
								success : function(result) {    
									  
									if(result == "OK") {
										 dialogWindow.callback(result);
										 dialogWindow.close();
									}
								},
								error : function(event, request, settings){
									 alert("error");
									 dblClick=false;
									 $("body").ajaxLoadComplete();
								}
							});
						}
					</c:if>
					
	            },
	            rules			: {
            		//apprMessage	: {minlength: 2, maxlength: 300 }
		            <c:if test="${isPassword eq '1'}">
		            apprPassword	: { required: true}
		            </c:if>
           		},
	            messages		: {
					/* apprMessage	: {
						direction : "bottom",
                        required		: "<ikep4j:message pre='${preValidation}' key='mustinput.apprMessage'/>",
                        minlength		: "<ikep4j:message pre='${preValidation}' key='mininput.apprMessage' arguments='2'/>",
                        maxlength		: "<ikep4j:message pre='${preValidation}' key='maxinput.apprMessage' arguments='300'/>"
     				} */
	           		<c:if test="${isPassword eq '1'}">
		            apprPassword	: {
		            	direction : "bottom",
		            	required		: "<ikep4j:message pre='${preValidation}' key='mustinput.apprPassword'/>" 
					}
		            </c:if>
	            }
			}; 
		    
			new iKEP.Validator("#myForm", validOptions);
		});
	})(jQuery);
	//-->
</script>
<h1 class="none"><ikep4j:message pre='${preHeader}' key='title' /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="title" /></h2> 
	<%-- <div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.3depth" /></li>
			<li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.4depth" /></li>
		</ul>
	</div> --%>
</div>
<!--//pageTitle End-->

<form id="myForm" name="myForm" method="post" action="">
<input type="hidden" name="apprType" value="${apprLine.apprType}"/>
<spring:bind path="apprLine.apprId">
	<input type="hidden" name="${status.expression}" value="${apprId}"/>
</spring:bind>
<!-- 위임자 정보 -->
<input type="hidden" name="entrustUserId" value="${apprLine.entrustUserId}"/>
	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${preView}' key='summary'/>">
			<caption></caption>
			<tbody>
				<tr>
					<spring:bind path="apprLine.apprStatus">
					<th scope="row" width="18%"><ikep4j:message pre='${preView}' key='${status.expression}'/></th>
					<td>
						<c:if test="${apprLine.apprType==0}">
						<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="2"
							title="<ikep4j:message pre='${preView}' key='${status.expression}'/>" 
							checked="true"
							/>
						<ikep4j:message pre='${preView}' key='approval'/>
						</c:if>
						<c:if test="${apprLine.apprType==1 || apprLine.apprType==2}">
						<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="4"
							title="<ikep4j:message pre='${preView}' key='${status.expression}'/>" 
							checked="true"
							/>							
						<ikep4j:message pre='${preView}' key='agreement'/>
						</c:if>					
						<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="3"
							title="<ikep4j:message pre='${preView}' key='${status.expression}'/>"
							/>
						<ikep4j:message pre='${preView}' key='reject'/>
					</td>
					</spring:bind>
				</tr>
				<tr>
					<spring:bind path="apprLine.apprMessage">
					<th scope="row" width="18%"><ikep4j:message pre='${preView}' key='${status.expression}'/></th>
					<td>
						<textarea name="${status.expression}" id="${status.expression}"  title="<ikep4j:message pre='${preView}' key='${status.expression}'/>" style="width:80%" rows="7"></textarea>
					</td>
					</spring:bind>
				</tr>
				<c:if test="${isPassword eq '1'}">
					<tr>
						<spring:bind path="apprLine.apprPassword">
						<th scope="row" width="18%"><ikep4j:message pre='${preView}' key='${status.expression}'/></th>
						<td>
							<input type="password" id="${status.expression}" class="inputbox" title="<ikep4j:message pre='${preView}' key='${status.expression}'/>" name="${status.expression}" value="" size="20"/>
						</td>
						</spring:bind>
					</tr>
				</c:if>
			</tbody>
		</table>
		
	</div>
	<!--//blockDetail End-->
	
	<div class="clear"></div>
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="saveButton"		class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='appr'/></span></a></li>
			<li><a id="cancelButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
	
</form>	
<!--//blockListTable End-->	
