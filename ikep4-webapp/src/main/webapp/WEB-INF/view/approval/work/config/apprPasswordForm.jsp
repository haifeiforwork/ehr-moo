<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.approval.work.apprPassword.form.header" />
<c:set var="preForm"  	value="ui.approval.work.apprPassword.form" />
<c:set var="preValidation" value="ui.approval.work.apprPassword.validation" />
<c:set var="preButton"  value="ui.approval.common.button" />
<c:set var="preMessage" value="ui.approval.common.message" />
<c:set var="preIcon"  	value="ui.approval.common.icon" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript">
	<!--
	
	(function($){
		
		saveForm = function() {
			
			if(!iKEP.checkApprPassword($jq("#newApprPassword").val())) {
				alert("<ikep4j:message pre="${preForm}" key="msg1" />");
				return;	
			}
			
			if($jq("#newApprPassword").val() != $jq("#newApprPassword2").val()) {
				alert("<ikep4j:message pre="${preValidation}" key="newPasswordDisagreement" />");
				return;	
			}
			
			if(confirm("<ikep4j:message pre="${preMessage}" key="save" />")) {
				
				$jq.ajax({     
					url : '<c:url value="/approval/work/config/changeApprPassword.do" />',     
					data :  $jq("#searchForm").serialize(),     
					type : "post",     
					success : function(result) {         
						if(result == "nowPasswordError") {
							alert("<ikep4j:message pre="${preValidation}" key="nowPasswordDisagreement" />");
						}
						else {
							alert("<ikep4j:message pre="${preMessage}" key="saveSuccess" />");
							$jq("#nowApprPassword").val("");
							$jq("#newApprPassword").val("");
							$jq("#newApprPassword2").val("");
						}
					},
					error : function(event, request, settings){
						 alert("error");
					}
				});
			}  
			
		};

		var validOptions = {
		    rules : {
		    	nowApprPassword :    {
		            required : true,
		            minlength : 5,
		            maxlength : 16
		        },
		        newApprPassword :    {
		            required : true,
		            minlength : 5,
		            maxlength : 16
		        }
		    },
		    messages : {
		    	nowApprPassword : {
		            direction : "bottom",
		            required		: "<ikep4j:message pre='${preValidation}' key='required.nowApprPassword'/>",
                    minlength		: "<ikep4j:message pre='${preValidation}' key='maxlength.nowApprPassword'/>",
                    maxlength		: "<ikep4j:message pre='${preValidation}' key='maxlength.nowApprPassword'/>"
		        },
		        newApprPassword : {
		            direction : "bottom",
		            required		: "<ikep4j:message pre='${preValidation}' key='required.newApprPassword'/>",
                    minlength		: "<ikep4j:message pre='${preValidation}' key='maxlength.newApprPassword'/>",
                    maxlength		: "<ikep4j:message pre='${preValidation}' key='maxlength.newApprPassword'/>"
		        }
		    },
		    submitHandler : function(form) {
		    	saveForm();
		    }
		};
		
		
		$(document).ready(function(){
			
			$jq("#apprPasswordFormOfLeft").click();
			
			new iKEP.Validator("#searchForm", validOptions);

			$jq("#btnCancel").click(function(){
				
				$jq("#nowApprPassword").val("");
				$jq("#newApprPassword").val("");
				$jq("#newApprPassword2").val("");
				
			});
			
			$jq("#btnSave").click(function() {  
				
				$jq("#searchForm").submit();
				
			});   
			
		});
		
	})(jQuery);
	//-->
</script>

<h1 class="none"><ikep4j:message pre="${preHeader}" key="title" /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
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

<form id="searchForm" name="searchForm" method="post" action="">

	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${preForm}' key='summary'/>">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="18%"><ikep4j:message pre='${preForm}' key='nowApprPassword'/></th>
					<td>
						<div>
						<input type="password" class="inputbox" id="nowApprPassword" name="nowApprPassword" title="<ikep4j:message pre='${preForm}' key='nowApprPassword'/>" value="" size="20" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row" width="18%"><ikep4j:message pre='${preForm}' key='newApprPassword'/></th>
					<td>
						<div>
						<input type="password" class="inputbox" id="newApprPassword" name="newApprPassword" title="<ikep4j:message pre='${preForm}' key='newApprPassword'/>" value="" size="20" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row" width="18%"><ikep4j:message pre='${preForm}' key='newApprPassword2'/></th>
					<td>
						<div>
						<input type="password" class="inputbox" id="newApprPassword2" name="newApprPassword2" title="<ikep4j:message pre='${preForm}' key='newApprPassword2'/>" value="" size="20" />
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		
	</div>
	<!--//blockDetail End-->
	
	<!--directive Start-->
	<div class="directive"> 
		<ul>
			<li><span><ikep4j:message pre="${preForm}" key="msg1" /></span></li>	
			<li><span><ikep4j:message pre="${preForm}" key="msg2" /></span></li>	
		</ul>
	</div>
	<div class="blockBlank_10px"></div>
	<!--//directive End-->
	
	<div class="clear"></div>
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="btnSave"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			<li><a id="btnCancel"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
	
	
</form>			