<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.rss.channel.header" /> 
<c:set var="preList"    value="ui.support.rss.channel.list" />
<c:set var="preDetail"  value="ui.support.rss.channel.detail" />
<c:set var="preButton"  value="ui.support.rss.button" /> 
<c:set var="preMessage" value="ui.support.rss.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	submitForm = function() {
		
		$jq("#channelForm").submit();
		
	};
	
	saveForm = function() {
		
				
			$jq.post('<c:url value="/support/rss/channel/createChannel.do" />', $jq("#channelForm").serialize())
			.success(function(data) {  
				
				var json = $jq.parseJSON(data);
				if(json.status=="success") { 
					//alert('<ikep4j:message key='ui.support.rss.message.channel.insert.success' />');
					alert(json.message);
					closePop();
				}
				else {
					alert(json.message);
				}
			})
			.error(function(event, request, settings) { alert("error"); });   
		
		
	};
	
	closePop = function() {
		dialogWindow.close();
	};
	
	var validOptions = {
	    rules : {
	    	channelTitle :    {
	            required : true,
	            maxlength : 500
	        },
	        channelUrl :    {
	            required : true,
	            maxlength : 500
	        }
	    },
	    messages : {
	    	channelTitle : {
	            direction : "bottom",
	            required : "<ikep4j:message key="NotEmpty.channel.channelTitle" />",
	            maxlength : "<ikep4j:message key="Size.channel.channelTitle" />"
	        },
	        channelUrl : {
	            direction : "bottom",
	            required : "<ikep4j:message key="NotEmpty.channel.channelUrl" />",
	            maxlength : "<ikep4j:message key="Size.channel.channelUrl" />"
	        }
	    },
	    submitHandler : function(form) {
	    	saveForm();
	    }
	 };
	
	
	var dialogWindow = null;
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		new iKEP.Validator("#channelForm", validOptions);
		
		fnCaller = function(param, dialog){
			dialogWindow = dialog;
		}
		
	});
	
})(jQuery);  


//-->	
</script>
			
				<!--popup_contents Start-->
				<div id="popup_contents">
				
	
				
					<!--blockDetail Start-->
					<div class="blockDetail">
											
					
						<form id="channelForm" method="post" action="">
						
							<table summary="<ikep4j:message pre='${preDetail}' key='main.title' />">
							<caption></caption>	
					
							<tbody>
								
								<spring:bind path="channel.channelTitle">
								<tr> 
									<th scope="row" width="15%"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
									<td width="85%">
										<div>
										<input 
										name="${status.expression}" 
										type="text" 
										class="inputbox" 
										value="${status.value}" 
										size="40" 
										title="<ikep4j:message pre='${preList}' key='${status.expression}' />"
										/>
										</div>
									</td> 
								</tr>				
								</spring:bind>
								
								<spring:bind path="channel.channelUrl">
								<tr> 
									<th scope="row"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
									<td>
										<div>
										<input 
										name="${status.expression}" 
										type="text" 
										class="inputbox" 
										value="${status.value}" 
										size="80" 
										title="<ikep4j:message pre='${preList}' key='${status.expression}' />"
										readOnly=true 
										/>
										</div>
									</td> 
								</tr>				
								</spring:bind>
											
							</tbody>					
							
							</table>
						
						</form>
							
							
					</div>
					<!--//blockDetail End-->
															
					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<li><a class="button" href="#a" onclick="javascript:submitForm()"><span><ikep4j:message pre='${preButton}' key='submit' /></span></a></li>
							<li><a class="button" href="#a" onclick="javascript:closePop()"><span><ikep4j:message pre='${preButton}' key='cancel' /></span></a></li>
						</ul>
					</div>
					<!--//blockButton End-->
				
				
				</div>
				<!--//popup_contents End-->
			 
		
	