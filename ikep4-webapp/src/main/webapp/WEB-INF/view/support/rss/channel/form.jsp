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
	    	//iKEP.debug("submitHandler");
	    	saveForm();
	    }
	 };


	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		new iKEP.Validator("#channelForm", validOptions);
		
	});
	
})(jQuery);  


//-->	
</script>

					<form id="channelForm" method="post" action="">
						
						<spring:bind path="channel.channelId">
							<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preList}' key='${status.expression}'/>" />
						</spring:bind> 
						<spring:bind path="channel.channelCheck">
							<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preList}' key='${status.expression}'/>" />
						</spring:bind>
						 
					
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
							<spring:bind path="channel.categoryId">
                            <tr> 
                                <th scope="row" width="15%"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
                                <td width="85%">
                                   <select id="${status.expression}" name="${status.expression}" dd="${status.value}" style="width:80%;">
                                        <c:forEach var="category" items="${categoryList}">
                                             <option value="${category.categoryId}" <c:if test="${category.categoryId eq status.value}">selected="selected"</c:if>>
                                                ${category.categoryName}
                                            </option>
                                       </c:forEach>                                      
                                   </select>
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
									/>
									&nbsp;<a class="button" href="javascript:checkChannel()"><span><ikep4j:message pre='${preButton}' key='check' /></span></a>
									</div>
									
								</td> 
							</tr>				
							</spring:bind>
										
						</tbody>					
						
						</table>
					
					</form>
					
				