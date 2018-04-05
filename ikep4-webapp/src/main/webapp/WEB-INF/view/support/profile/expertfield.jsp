<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" /> 
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<% 
	pageContext.setAttribute("lf", "\n"); 
%>

<script type="text/javascript">
<!--

(function($) {
	
	var validOptionsExpertField = { 
			rules : {
				expertField : {
					maxlength : 300
				}
			},
			messages : {
				expertField : {
					maxlength : "<ikep4j:message key='Size.user.expertField' />"
				}
			},
			notice : {
				expertField : {
					message : "<ikep4j:message key='Size.user.expertField' />"
				}
		    },
			submitHandler : function(form) {
				$jq.ajax({
				    url : "<c:url value='/support/profile/updateExpertField.do'/>",
				    data : $jq('form[name=exfdForm]').serialize(),
				    type : "post",
				    success : function(result) {
				        //$("#divAjaxResult").html(result);
				    	getExpertFieldView('view');
				    },
				    error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
			}
	}

	var validator = new iKEP.Validator("#exfdForm", validOptionsExpertField);
	
	//입력체크
	updateCheckExpertField = function(length){
		var textLengthExpFd = length;
		$jq("#textLengthExpFd").html(300 - textLengthExpFd);
	};
	
	$jq(document).ready(function() {
	
		<c:if test="${editflag == 'edit'}">
		iKEP.checkLength("#expertField", updateCheckExpertField);
		$jq("#expertField").focus();
		</c:if>
		
		$jq('#exfd_save_btn').click(function(){
			//submitExpertFieldView();
			$("#exfdForm").trigger("submit");
			//$("#exfdForm").submit();
		});
		$jq('#exfd_cancel_btn').click(function(){
			getExpertFieldView('view');
		});
		
	});
})(jQuery);  
//-->
</script>

							<c:if test="${profile != null}">
							<c:if test="${editflag == 'view'}">
							<span><c:out value="${fn:replace(profile.expertField, lf, '<br/>')}" escapeXml="false" /></span>
							</c:if>
							<c:if test="${editflag == 'edit'}">
							<div class="pr_specialization_textarea">
								<textarea id="expertField" name="expertField" title="<ikep4j:message pre='${preProfileMain}' key='experts.input'/>" cols="" rows="3" ><c:out value='${profile.expertField}' /></textarea>
								<span class="num"><span id="textLengthExpFd">0</span>/300</span>
							</div>
							<div class="blockButton"> 
								<ul>
									<li><a id="exfd_save_btn" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
									<li><a id="exfd_cancel_btn" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
								</ul>
							</div>
							</c:if>
							</c:if>
							
							
							
					