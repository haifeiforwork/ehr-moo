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
	
	var validOptionsCurrentJob = { 
			rules : {
				currentJob : {
					maxlength : 100
				}
			},
			messages : {
				currentJob : {
					maxlength : "<ikep4j:message key='Size.user.currentJob' />"
				}
			},
			notice : {
				currentJob : {
					message : "<ikep4j:message key='Size.user.currentJob' />"
				}
		    },
			submitHandler : function(form) {
				$jq.ajax({
				    url : "<c:url value='/support/profile/updateCurrentJob.do'/>",
				    data : $jq('form[name=cujbForm]').serialize(),
				    type : "post",
				    success : function(result) {
				        //$("#divAjaxResult").html(result);
				    	getCurrentJobView('view');
				    },
				    error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
			}
	}

	var validator = new iKEP.Validator("#cujbForm", validOptionsCurrentJob);
	
	//입력체크
	updateCheckCurrentJob = function(length){
		var textLengthCJ = length;
		$jq("#textLengthCJ").html(200 - textLengthCJ);
	};
	
	$jq(document).ready(function() {
	
		<c:if test="${editflag == 'edit' and editAuthFlag == 'true' }">
		iKEP.checkLength("#currentJob", updateCheckCurrentJob);
		$jq("#currentJob").focus();
		</c:if>
		
		$jq('#cujb_save_btn').click(function(){
			$("#cujbForm").trigger("submit");
		});
		$jq('#cujb_cancel_btn').click(function(){
			getCurrentJobView('view');
		});

		$jq('#cujb_edit_btn').click(function(){
			getCurrentJobView('edit');
		});
	});
})(jQuery);  
//-->
</script>

<c:if test="${profile != null}">
	<c:if test="${editflag == 'view'}">
		<div style="width:100%;">
			<div style="float:left;width:63px;padding-left:2px;color:#777777;">Work</div> 
			<div style="float:left;width:88%;" class="ellipsis"><c:out value="${fn:replace(profile.currentJob, lf, '<br/>')}" escapeXml="false" />
				<c:if test="${editAuthFlag == 'true' }">
					<a href="#a" id="cujb_edit_btn"><img src="<c:url value='/base/images/icon/ic_edit.gif' />" alt="<ikep4j:message pre='${preButton}' key='update'/>" /></a>
				</c:if>
			</div>
			<div class="clear"></div>
		</div>
	</c:if>
	
	<c:if test="${editflag == 'edit'}">
		<c:if test="${editAuthFlag == 'true' }">
			<div class="pr_currentwork_p" style="margin:0 0 5px 0;">
				<div style="float:left;width:63px;padding-left:2px;color:#777777;">Work</div> 
				<div style="float:left;height:40px;width:90%;"  >
					<textarea id="currentJob" name="currentJob" title="<ikep4j:message pre='${preProfileMain}' key='currentJob.input'/>" cols="" rows="3" ><c:out value='${profile.currentJob}' /></textarea>
					<span class="num" style="right:120px;"><span id="textLengthCJ" >0</span>/200</span>
				</div>
				<div class="clear"></div>
			</div>
			<div class="blockButton" style="margin-right:10px;"> 
				<ul>
					<li><a id="cujb_save_btn" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
					<li><a id="cujb_cancel_btn" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
				</ul>
			</div>
		</c:if>
	</c:if>
</c:if>

							
							
					