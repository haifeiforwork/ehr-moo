<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" /> 
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
<!--
(function($) {
	var validOptionsProfileStatus = { 
			rules : {
				profileStatus : {
				    //direction : "bottom",
					maxlength : 40
				}
			},
			messages : {
				profileStatus : {
				    direction : "bottom",
					maxlength : "<ikep4j:message key='Size.user.profileStatus' />"
				}
			},
			notice : {
				profileStatus : {
				    direction : "bottom",
				    message : "<ikep4j:message key='Size.user.profileStatus' />"
				}
		    },
			submitHandler : function(form) {
				$jq.ajax({
				    url : "<c:url value='/support/profile/updateProfilestatus.do'/>",
				    data : $jq('form[name=pfstForm]').serialize(),
				    type : "post",
				    success : function(result) {
				    	$("#profileStatusView").children("div").find("span").text($("#profileStatus").val());
				    	$("#pfst_cancel_btn").click();
				    },
				    error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
			}
	}

	var validator = new iKEP.Validator("#pfstForm", validOptionsProfileStatus);
	
	//입력체크
	updateCheckProfileStatus = function(length){
		var textLengthPS = length;
		$jq("#textcountPS").html(40 - textLengthPS);
	};
	
	$(document).ready(function() {
		iKEP.checkLength("#profileStatus", updateCheckProfileStatus);
		$("#profileStatus").focus();
		
		$('#pfst_save_btn').click(function(){
			$("#pfstForm").trigger("submit");
		});
		
		$("#pfst_cancel_btn").click(function() {
			$("#profileStatusView")
				.children("div").show().end()
				.children("form").hide();
		});
	});
})(jQuery);
//-->
</script>
<form name="pfstForm" id="pfstForm" method="post" onsubmit="return false">
	<input name="userId" value="<c:out value='${profile.userId}'/>" type="hidden" />
	<input name="updaterId" value="<c:out value='${profile.userId}'/>" type="hidden" />
	<input name="updaterName" value="<c:out value='${profile.userName}'/>" type="hidden" />
	<input id="profileStatus" name="profileStatus" class="inputbox" title="<ikep4j:message pre='${preProfileMain}' key='statusupdate.input'/>" type="text" size="60" maxlength="50" style="width:380px;padding:0px 4px" value="<c:out value='${profile.profileStatus}' />" />
	<span id="textcountPS">0</span>/40
	<a href="#a" id="pfst_save_btn"><img src="<c:url value='/base/images/icon/ic_save.gif'/>" alt="<ikep4j:message pre='${preButton}' key='save'/>" /></a>
	<a href="#a" id="pfst_cancel_btn"><img src="<c:url value='/base/images/icon/ic_cancel.gif'/>" alt="<ikep4j:message pre='${preButton}' key='cancel'/>" /></a>
</form>