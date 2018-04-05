<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<c:set var="preRes"     value="ui.servicepack.usagetracker.resTimeUrl" />
<c:set var="preButton"  value="ui.servicepack.usagetracker.common.button" /> 
<c:set var="preCommon"  value="ui.servicepack.usagetracker.common" /> 
<c:set var="preConfirm" value="message.servicepack.usagetracker.common.confirm" /> 
<c:set var="preSucess"  value="message.servicepack.usagetracker.common.sucess" /> 
<c:set var="preValid"   value="message.servicepack.valid" /> 
<c:set var="preMessage" value="message.servicepack.usagetracker.resTimeUrl" /> 

<ikep4j:message pre='${preRes}' key="grid.urlName" var="urlName"/>
<ikep4j:message pre='${preRes}' key="grid.url" var="url"/>

<script  type="text/javascript">

var dialogWindow = null;
var fnCaller;

(function($) {  
	$(document).ready(function() {
	
		var validOptions = {
		    rules : {
		    	resTimeUrlName : {required : true},
		    	resTimeUrl : {required : true}
		    },
		    messages : {
		    	resTimeUrlName : {
		            direction : "bottom",
		            required : "<ikep4j:message  pre='${preValid}' key='required' arguments='${urlName}'/>"
		        },
		    	resTimeUrl : {
		            direction : "bottom",
		            required : "<ikep4j:message  pre='${preValid}' key='required' arguments='${url}'/>"
		        }
		    },
		    submitHandler : function(form) {
		    	if( confirm("<ikep4j:message pre='${preConfirm}' key='save'/>") )
		 		{
		  			  $.post('<c:url value="/servicepack/usagetracker/resTimeUrl/createOrUpdateAjax.do"/>', $("#saveForm").serialize())
		  			  .success(function(data){
		  				  	if(data.resultFlag=='1'){
		  				    	alert("<ikep4j:message pre='${preMessage}' key='alreadyAdded'/>");
		  				  	}else{
		  						alert("<ikep4j:message pre='${preSucess}' key='save'/>");
			  					dialogWindow.callback();
			  					dialogWindow.close();
		  				  	}
		  		       }).error(function(event, request, settings){
		  		           alert("error");
		  		       });		
		 		}
			}
		 };
			
		new iKEP.Validator("#saveForm", validOptions);
		
		$("#btnSave").click(function() {
			$("#saveForm").trigger("submit");
			return false; 
		});	
	});
	
	fnCaller = function (params, dialog) {
		
		if(params) {
		}
		dialogWindow = dialog;
		
		$("#btnCancel").click(function() {
			dialogWindow.close();
		});
	};
})(jQuery);
</script>

<!-- Modal window Start -->
<!-- 사용시class="none"으로 설정 -->
<div id="layer_p">
	<form id="saveForm" method="post" action="<c:url value='/servicepack/usagetracker/resTimeUrl/list.do' />">  
		<input type="hidden" name="resTimeUrlId" value="${utResTimeUrl.resTimeUrlId}" />
		
		<!--blockDetail Start-->
		<div class="blockDetail">
			<table summary="">
				<caption></caption>
				<tbody>
					<tr>
						<spring:bind path="utResTimeUrl.resTimeUrlName">
						<th scope="row" width="18%"><span class="colorPoint">*</span><ikep4j:message pre='${preRes}' key='grid.urlName'/></th>
						<td width="82%">
							<div>
								<input type="text" name="${status.expression}" value="${status.value}"  size="50"  class="inputbox" />
							</div>
						</td>
						</spring:bind>
					</tr>
					<tr>
						<spring:bind path="utResTimeUrl.resTimeUrl">
						<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${preRes}' key='grid.url'/></th>
						<td width="82%" class="textLeft">
							<div>
								<input name="${status.expression}" id="${status.expression}" type="text" class="inputbox w100" value="${status.value}" />
								<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
							</div>																	
						</td>
						</spring:bind>	
					</tr>
					<tr>
						<spring:bind path="utResTimeUrl.usage">
							<th scope="row"><ikep4j:message pre='${preRes}' key='grid.usage'/></th>
							<td>
							<c:forEach var="code" begin="0" end="1" step="1"> 
								<input name="${status.expression}" type="radio"	class="radio" value="${code}" <c:if test="${code eq status.value}">checked="checked"</c:if> title="<ikep4j:message pre='${preList}' key='${status.expression}' />" /> 
								<ikep4j:message pre='${preCommon}' key='useage.${code}'/>
							</c:forEach> 
							</td>
						</spring:bind>	
					</tr>							
				</tbody>
			</table>
		</div>
		<!--//blockDetail End-->
		
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a class="button" id="btnSave"   href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
				<li><a class="button" id="btnCancel" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</form>	
</div>	
<!-- //Modal window End -->