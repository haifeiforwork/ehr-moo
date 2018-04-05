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
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript">
	<!--
	var dialogWindow = null;
	var fnCaller;

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
									var apprSts = $jq(form).find("input[name=apprStatus]:checked").val();
									var apprStr = "", apprMsg="";
									if(apprSts == "2")  {
										apprStr="<ikep4j:message pre='${preView}' key='approval'/> ";
										apprMsg="<ikep4j:message pre='${preView}' key='approvalMsg'/> ";
									}
									if(apprSts == "3") {
										apprStr="<ikep4j:message pre='${preView}' key='reject'/> ";
										apprMsg="<ikep4j:message pre='${preView}' key='rejectMsg'/> ";
									}
									if (!confirm(apprStr+"<ikep4j:message pre='${preView}' key='qa'/>"))
									{
										$("body").ajaxLoadComplete();
										dblClick=false;
										return false;
									}
									if($jq(form).find("textarea[name=apprMessage]").val() == '') {
										$jq(form).find("textarea[name=apprMessage]").val(apprMsg);	
									}
									$jq.ajax({     
										url : '<c:url value="/approval/work/apprLine/updateApprovalStatus.do"/>',     
										data :  $jq("#myForm").serialize(),     
										type : "post",     
										success : function(result) { 
											//dblClick=true;     
											var str = result.split(":");

											if(str[0] == "OK") {
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
							},
							error : function(event, request, settings){
								 alert("error");
								 dblClick=false;
								 $("body").ajaxLoadComplete();
							}
						});
					</c:if>
					<c:if test="${isPassword eq '0'}">
						var apprSts = $jq(form).find("input[name=apprStatus]:checked").val();
						var apprStr = "", apprMsg="";
						if(apprSts == "2")  {
							apprStr="<ikep4j:message pre='${preView}' key='approval'/> ";
							apprMsg="<ikep4j:message pre='${preView}' key='approvalMsg'/> ";
						}
						if(apprSts == "3") {
							apprStr="<ikep4j:message pre='${preView}' key='reject'/> ";
							apprMsg="<ikep4j:message pre='${preView}' key='rejectMsg'/> ";
						}
						if (!confirm(apprStr+"<ikep4j:message pre='${preView}' key='qa'/>")) {
							dblClick=false;
							$("body").ajaxLoadComplete();
							return false;
						}
						if($jq(form).find("textarea[name=apprMessage]").val() == '') {
							$jq(form).find("textarea[name=apprMessage]").val(apprMsg);	
						}
						$jq.ajax({     
							url : '<c:url value="/approval/work/apprLine/updateApprovalStatus.do"/>',     
							data :  $jq("#myForm").serialize(),     
							type : "post",     
							success : function(result) {      
								var str = result.split(":");

								if(str[0] == "OK") {
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
	<input type="hidden" name="apprIds" value="${apprIds}"/>
	<input type="hidden" name="entrustUserIds" value="${entrustUserIds}"/>
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
						<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="2"
							title="<ikep4j:message pre='${preView}' key='${status.expression}'/>" 
							checked="true"
							/>
						<ikep4j:message pre='${preView}' key='approval'/>
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
						<textarea name="${status.expression}" id="${status.expression}" title="<ikep4j:message pre='${preView}' key='${status.expression}'/>" style="width:80%"  rows="7"></textarea>
					</td>
					</spring:bind>
				</tr>
				<c:if test="${isPassword eq '1'}">
					<tr>
						<spring:bind path="apprLine.apprPassword">
						<th scope="row" width="18%"><ikep4j:message pre='${preView}' key='${status.expression}'/></th>
						<td>
							<input type="password" id="${status.expression}" class="inputbox"  title="<ikep4j:message pre='${preView}' key='${status.expression}'/>" name="${status.expression}" value="" size="20"/>
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
