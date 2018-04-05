<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%@ include file="/base/common/fileUploadControll.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.mail.header" /> 
<c:set var="preList"    value="ui.support.mail.list" />
<c:set var="preDetail"  value="ui.support.mail.detail" />
<c:set var="preButton"  value="ui.support.mail.button" /> 
<c:set var="preMessage" value="ui.support.mail.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript">
<!--   


(function($) {

	// onload시 수행할 코드
	$jq(document).ready(function() { 

	});
	
})(jQuery);  


//-->
</script>

		
				<!--popup_contents Start-->
				
							
					<!--blockDetail Start-->
					<div class="blockDetail">
		
		
						<table summary="<ikep4j:message pre='${preDetail}' key='main.send' />">
							<caption></caption>	
					
							<tbody id="tbodyDiv">
						
								<tr>
									<th scope="row" width="15%"><ikep4j:message pre='${preDetail}' key='fromEmail' /></th>
									<td class="textLeft">${mail.fromName}</td>
								</tr>
								<tr>
									<th scope="row"><ikep4j:message pre='${preDetail}' key='title' /></th>
									<td class="textLeft">${mail.title}</td>
								</tr>
								<tr>
									<th scope="row"><ikep4j:message pre='${preDetail}' key='content' /></th>
									<td class="textLeft">${mail.content}</td>
								</tr>
								
								<tr>
									<th scope="row"><ikep4j:message pre='${preDetail}' key='fileList' /></th>
									<td class="textLeft">
									
										<c:forEach var="attach" items="${mail.attachList}">
											<a href="<c:url value="/support/mail/downloadMailAttach.do"/>?folderName=inbox&mailId=${mail.mailId}&multipartPath=${attach.multipartPath}">${attach.name}</a><br/>
										</c:forEach>
										
									</td>
								</tr>
						
							</tbody>					
							
						</table>
						
	
					</div>
					<!--//blockDetail End-->
					
				</div>
				<!--//popup_contents End-->
				
