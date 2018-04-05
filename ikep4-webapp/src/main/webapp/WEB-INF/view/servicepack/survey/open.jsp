<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<c:set var="preHeader"  value="ui.servicepack.survey.header.read" /> 
<c:set var="preList"    value="ui.servicepack.survey.list" />
<c:set var="preButton"  value="ui.servicepack.survey.common.button" /> 
<c:set var="preMessage"  value="message.servicepack.survey" /> 
<c:set var="preMail"  value="ui.servicepack.survey.mail" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<script type="text/javascript">
<!--
var dialogWindow = null;
var fnCaller;

(function($){
	fnCaller = function (params, dialog) {
		if(params) {
		}
		dialogWindow = dialog;
		
		$("#btnClose").click(function() {
			dialogWindow.close();
		});
	};

	$(document).ready(function() { 
	 	$("#publishButton").click(function() {
	 		try {	// callback function : dialog 생성시 callback handler 셋팅됨.
	 			var result = new Object();
	 		
	 			var mailSendYn = $("input[name='mailSendYn']:checked").val();
	 			var sendOption = $("input[name='sendOption']:checked").val();
	 			var sendComment = $("#sendComment").val();
	 			
	 			result.mailSendYn = mailSendYn;
	 			result.sendOption = sendOption;
	 			result.sendComment = sendComment;
	 			
	 			//alert(mailSendYn+":" + sendOption);
	 			
				dialogWindow.callback(result);
				dialogWindow.close();
			} catch(e) {}
	 		return false; 
	 	});
	 	
	 	$("input[name='mailSendYn']").change(function(){
	 		
	 		//alert(1);
	 		
	 		var mailSendYn = $("input[name='mailSendYn']:checked").val();
	 		
	 		if( mailSendYn=='0')
	 			$(".sendOptionBox").hide();
	 		else
	 			$(".sendOptionBox").show();
	 	});
	});  
})(jQuery);	
//-->
</script>
<!--popup Start-->
<div>
	<!--popup_contents Start-->
	<div id="popup_contents" style="margin-top:30px;">
	
	<form id="surveyForm" method="post">
		<!--blockDetail Start-->
		<div class="blockDetail">
		<table summary="<ikep4j:message key='ui.servicepack.survey.header.open.pageTitle'/>">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="18%"><ikep4j:message pre='${preMail}' key='sendOption' /></th>
					<td width="82%">
						<label><input name="mailSendYn" 
										type="radio" 
										class="radio" 
										value="1" 
										size="40" 
										title="<ikep4j:message pre='${preMail}' key='send' />" checked
										/><ikep4j:message pre='${preMail}' key='send' /></label>&nbsp;
						<label><input name="mailSendYn" 
										type="radio" 
										class="radio" 
										value="0" 
										size="40" 
										title="<ikep4j:message pre='${preMail}' key='notSend' />"
										/> <ikep4j:message pre='${preMail}' key='notSend' /></label>
					</td>								
				</tr>
				<tr class="sendOptionBox">
					<th scope="row" width="18%"><ikep4j:message pre='${preMail}' key='sendTime' /></th>
					<td width="82%">
						<label><input name="sendOption" 
										type="radio" 
										class="radio" 
										value="0" 
										size="40" 
										title="<ikep4j:message pre='${preMail}' key='timer.0' />" checked
										/><ikep4j:message pre='${preMail}' key='timer.0' />
										</label>&nbsp;
						<label><input name="sendOption" 
										type="radio" 
										class="radio" 
										value="1" 
										size="40" 
										title="<ikep4j:message pre='${preMail}' key='timer.1' />"
										/> <ikep4j:message pre='${preMail}' key='timer.1' />
										</label>&nbsp;
						<label><input name="sendOption" 
										type="radio" 
										class="radio" 
										value="6" 
										size="40" 
										title="<ikep4j:message pre='${preMail}' key='timer.6' />"
										/> <ikep4j:message pre='${preMail}' key='timer.6' /></label>&nbsp;
						<label><input name="sendOption" 
										type="radio" 
										class="radio" 
										value="12" 
										size="40" 
										title="<ikep4j:message pre='${preMail}' key='timer.12' />"
										/> <ikep4j:message pre='${preMail}' key='timer.12' /></label>
					</td>								
				</tr>	
				<tr>
					<th scope="row" width="18%">메시지</th>
					<td width="82%">
						<textarea NAME="sendComment" id="sendComment" style="width:100%;height:50px"></textarea>
					</td>								
				</tr>										
			</tbody>
		</table>
	    </div>
	
    
		<!--//blockDetail End-->																																			
		<div class="clear"></div>
		
		<!--blockButton Start-->
		<div class="blockButton" style="text-align:center;margin-top:10px;"> 
			<ul> 
				<li><a id="publishButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='confirm'/></span></a></li>
				<li><a id="btnClose" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
			</ul>
		</div>
	</form>
	</div>
</div>
<!--//popup End-->


		



