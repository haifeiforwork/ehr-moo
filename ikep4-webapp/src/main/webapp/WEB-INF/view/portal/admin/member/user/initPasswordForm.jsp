<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="userUiPrefix" value="ui.portal.admin.member.user"/>

<script type="text/javascript" language="javascript">

(function($) {
	
	
	submitForm = function() {
		$jq("#userForm").trigger("submit");
	};
	
	closePop = function() {
		dialogWindow.close();
	};
	
	var dialogWindow = null;
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		//단축 url 저장하고 입력창에서 값 변경하기
		fnCaller = function(param, dialog){
			dialogWindow = dialog;
		}
		
		var validOptions = {
				rules : {
					userPassword : {
						required : true,
						rangelength : [0, 20]
					}
				},
				messages: {
					userPassword : {
						required : "<ikep4j:message key='NotNull.user.userPassword' />",
			    		rangelength : "<ikep4j:message key='Size.user.userPassword' />",
			    		direction : 'bottom'
					}
				},
				submitHandler : function(form) {
					$jq.ajax({
						url : '<c:url value="/portal/admin/member/user/initPassword.do" />',
						data : $jq("#userForm").serialize(),
						type : "post",
						loadingElement : {container:"#popup_contents_1"}, 
						success : function(data) {
							if(data=="success") { 
								closePop();
							} else {
								 alert("error");
							}
						},
						error : function(event, request, settings) { 
							alert("error"); 
						}
					}); 
				}
		}
		
		var validator = new iKEP.Validator("#userForm", validOptions);
		
	});
	
	
	
})(jQuery);  


	
</script>
			
				<!--popup_contents Start-->
				<div id="popup_contents_1">
				
	
				
					<!--blockDetail Start-->
					<div class="blockDetail">
											
					
						<form id="userForm" name="userForm" method="post" action=""  onsubmit="return false;">
						
							<table summary="<ikep4j:message pre="${userUiPrefix}" key="button.modifyPassword" />">
							<caption></caption>	
							
							<input type="hidden" name="userId" value="${userId}"/>
							<input type="hidden" name="isAllUser" value="${isAllUser}"/>
							
							<tbody>
								
								<tr> 
									<th scope="row"><ikep4j:message pre="${userUiPrefix}" key="button.modifyPassword" /></th>
									<td>
										<input name="userPassword" id="userPassword" type="text" class="inputbox" value=""  
										title="<ikep4j:message pre='${userUiPrefix}' key='button.modifyPassword' />" />
									</td> 
								</tr>				
								
											
							</tbody>					
							
							</table>
						
						</form>
							
							
					</div>
					<!--//blockDetail End-->
															
					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<li><a class="button" href="javascript:submitForm()"><span><ikep4j:message pre="${userUiPrefix}" key="button.save" /></span></a></li>
							<li><a class="button" href="javascript:closePop()"><span><ikep4j:message pre="${userUiPrefix}" key="button.cancel" /></span></a></li>
						</ul>
					</div>
					<!--//blockButton End-->
				
				
				</div>
				<!--//popup_contents End-->