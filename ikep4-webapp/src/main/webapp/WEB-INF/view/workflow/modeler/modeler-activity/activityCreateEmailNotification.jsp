<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" errorPage=""  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preModeler"  value="message.workflow.modeler" />
<c:set var="preValidation"  value="validate.workflow.modeler" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script language="JavaScript">
	(function($){
		
		// 참여자 추가 이미지 아이콘 클릭.
		var name = "";
		$("#createActivityEMAILNotification").find( "a[href=#a] > img" ).click(function() {
			var type = $(this).attr("src").split("/")[$(this).attr("src").split("/").length -1];
			name =  $(this).parent().parent().find("input[type='text']").attr("name");
			
			if ( type == 'ic_alarm.png' ){
				var url = '<c:url value="activityCreateParticipantPopup.do?checkParticipants="/>' 
				+ encodeURIComponent($("#createActivityEMAILNotification").find("input[name='"+ name +"']").val()) 
				+ "&layer=" + "dialog-activityCreateParticipantPopup-EMAIL"
				+ "&divId=" + "activityCreateParticipantPopup-EMAIL";
				
				style = { width: 520, height:210, modal:true, resizable: false };
				$("#dialog-activityCreateParticipantPopup-EMAIL").load(url).error(function(event, request, settings) {alert("error");});
				$("#dialog-activityCreateParticipantPopup-EMAIL").dialog(style);
				$("#dialog-activityCreateParticipantPopup-EMAIL").dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
				$("#dialog-activityCreateParticipantPopup-EMAIL").dialog("widget").children(":first").addClass("ui-widget-workflow-header");
			}else{
				url = '<c:url value="expressionBuilder.do?target=conditionType&expressionEditorContent="/>' + encodeURIComponent($("#createActivityEMAILNotification").find("input[name='conditionType']").val());;
				style = { width: 500, height:270, modal:true, resizable: false };
				$("#dialog-expressionBuilder").load(url).error(function(event, request, settings) {alert("<ikep4j:message pre='${preValidation}' key='error'/>");});
				$("#dialog-expressionBuilder").dialog(style);
				$("#dialog-expressionBuilder").dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
				$("#dialog-expressionBuilder").dialog("widget").children(":first").addClass("ui-widget-workflow-header");
			}
		});
		
		// opener의 함수 호출 참여자 값을 넘겨준다.
		participantCallback = function(activityParticipantIds){
			var participants = "";
			for(var i=0; i < activityParticipantIds.length; i++){
				if( activityParticipantIds.length -1 == i ){
					participants = participants + activityParticipantIds[i];
				}else{
					participants = participants + activityParticipantIds[i] + ";";	
				}
			}
			$("#createActivityEMAILNotification").find("input[name='" + name + "']").val(participants);		
		}
		
		// Trigger 변경시에  option[AFTER, BEFPRE]를 생성한다.
		createActivityEMAILStateChange = function(activityState){
			if( activityState.value == 'EXPIRE'){
				var prop = new Array("BEFORE", "AFTER");
				$("#createActivityEMAILNotification").find("select[name='trigger']").html("");
				$("#createActivityEMAILNotification").find("select[name='trigger']").append('<option value="NONE"><ikep4j:message pre="${preModeler}" key="option.none"/></option>');
				for(var i=0; i < prop.length; i++){
					var option = document.createElement("option");
					option.value = prop[i];
					option.innerHTML = prop[i];
					$("#createActivityEMAILNotification").find("select[name='trigger']").append(option);	
				}
			}else{
				$("#createActivityEMAILNotification").find("select[name='trigger']").html("");
				$("#createActivityEMAILNotification").find("select[name='trigger']").append('<option value="NONE"><ikep4j:message pre="${preModeler}" key="option.none"/></option>');
			};
		}
		
		$(document).ready(function () {
			
			var validOptions = {
				rules : {
					day     : { required : true },
					hour   	: { required : true },
					minutes : { required : true },
					to      : { required : true },
					title   : { required : true },
					body    : { required : true }
				},
				messages : {
					day     : { required  : "<ikep4j:message pre='${preValidation}' key='message.14'/>" },
					hour    : { required  : "<ikep4j:message pre='${preValidation}' key='message.15'/>" },
					minutes : { required  : "<ikep4j:message pre='${preValidation}' key='message.16'/>" },
					to      : { required  : "<ikep4j:message pre='${preValidation}' key='message.17'/>" },
					title   : { required  : "<ikep4j:message pre='${preValidation}' key='message.18'/>"	},
					body    : { required  : "<ikep4j:message pre='${preValidation}' key='message.19'/>" }
				},
				notice : {
			        title   : "<ikep4j:message pre='${preValidation}' key='message.20'/>"
			    },
				submitHandler : function(form){
					var activityNotification = new Array();
					activityNotification["activityId"] = '<c:out value="${param.activityId}"/>';
					activityNotification["id"] 		   = $("#createActivityEMAILNotification").find("input[name=notificationId]").val();
					activityNotification["condition"] 	   = $("#createActivityEMAILNotification").find("input[name=conditionType]").val();
					activityNotification["type"] 	   = 'EMAIL';
					activityNotification["state"] 	   = $("#createActivityEMAILNotification").find("select[name=activityState]").val();
				    activityNotification["trigger"]    = $("#createActivityEMAILNotification").find("select[name=trigger]").val();
				    activityNotification["day"] 	   = $("#createActivityEMAILNotification").find("input[name=day]").val();
				    activityNotification["hour"] 	   = $("#createActivityEMAILNotification").find("input[name=hour]").val();
				    activityNotification["minutes"]    = $("#createActivityEMAILNotification").find("input[name=minutes]").val();
					activityNotification["to"] 		   = $("#createActivityEMAILNotification").find("input[name=to]").val();
			 		activityNotification["to_type"]    = $("#createActivityEMAILNotification").find("input[name=to_type]").val();
			 		activityNotification["cc"] 		   = $("#createActivityEMAILNotification").find("input[name=cc]").val();
			 	    activityNotification["cc_type"]    = $("#createActivityEMAILNotification").find("input[name=cc_type]").val();
			 	    activityNotification["bcc"] 	   = $("#createActivityEMAILNotification").find("input[name=bcc]").val();
			 	    activityNotification["bcc_type"]   = $("#createActivityEMAILNotification").find("input[name=bcc_type]").val();
				    activityNotification["title"] 	   = $("#createActivityEMAILNotification").find("input[name=title]").val();
				    activityNotification["body"] 	   = $("#createActivityEMAILNotification").find("textarea[name=body]").val();
					
					try {
						xpdl.setActivityNotification(activityNotification, 'append');
						alert("<ikep4j:message pre='${preModeler}' key='message.create'/>");
						
						// 하단 탭 리스트를 새로 구성한다.
						createNotificationList('activityAddNotifyTbody', 'EMAIL');
					}catch(e){
//						alert(e);
						alert(activityNotification.state + " <ikep4j:message pre='${preModeler}' key='message.44'/>");
					}
					$( '#' + '<c:out value="${param.layer}"/>' ).empty();
					$( '#' + '<c:out value="${param.layer}"/>' ).dialog( "close" );
				}
			};
			
			new iKEP.Validator("#createActivityEMAILNotificationForm", validOptions);
			
			$("input[name='title']").focus();
		
			$( ".button, .bold" ).click(function() {
				// 버튼 이벤트 Id.
				var attrIds = $(this).attr("class").split(" ");
				var attrId = attrIds[attrIds.length - 1];
				
				switch ( attrId ){
					// 이메일 알림 추가 확인
					case "activityEmailNotificationConfirm" :
						$("#createActivityEMAILNotificationForm").submit();
						break;	
						
					// 이메일 추가 취소
					case "activityEmailNotificationCancel" :
						$( '#' + '<c:out value="${param.layer}"/>' ).empty();
						$( '#' + '<c:out value="${param.layer}"/>' ).dialog( "close" );
						break;	
				}
			});
		});
		
	})(jQuery);

</script>
<style>
    #activityCreateParticipantPopup-EMAIL .ui-selecting { background: #FECA40; }
    #activityCreateParticipantPopup-EMAIL .ui-selected { background: #F39814; color: white; }
    #activityCreateParticipantPopup-EMAIL { list-style-type: none; margin: 0; padding: 0; width: 100%; }
	#activityCreateParticipantPopup-EMAIL li { margin: 0px; padding: 0px; font-size: 1em; }
</style>
<!-- Modal window Start -->
<!--blockDetail_2 Start-->
<form id="createActivityEMAILNotificationForm" name="createActivityEMAILNotificationForm" onsubmit="return false;">
<div class="blockDetail_2" id="createActivityEMAILNotification">
	<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.email.notifycation.update'/>" border="1">
		<caption></caption>
		<tbody>
			<tr>
				<th scope="row" class="textLeft"><ikep4j:message pre='${preModeler}' key='form.state.type'/></th>
				<td colspan="3">
					<input name="conditionType" type="text" class="inputbox w70" title="" value="" size="18" readonly="true"/>&nbsp;
					<a href="#a"><img src="<c:url value='/base/images/icon/ic_menu_microblog.png'/>" width="16" height="16" alt="" /></a>					
				</td>
			</tr>
			<tr>
				<th scope="row" width="20%" class="textLeft"><ikep4j:message pre='${preModeler}' key='form.state.state'/></th>
				<td width="30%">							
					<select title="<ikep4j:message pre='${preModeler}' key='form.state.state'/>" name="activityState" onchange="createActivityEMAILStateChange(this);">
						<option value="ASSIGN">ASSIGN</option>
						<option value="FINISH">FINISH</option>
						<option value="EXPIRE">EXPIRE</option>
					</select>
				</td>
				<th scope="row" width="20%" class="textLeft"><ikep4j:message pre='${preModeler}' key='form.state.time'/></th>
				<td width="30%">							
					<select title="<ikep4j:message pre='${preModeler}' key='form.state.time'/>" name="trigger">
						<option value="NONE"><ikep4j:message pre='${preModeler}' key='option.none'/></option>
					</select>
				</td>					
			</tr>
			<tr>
				<th scope="row" class="textLeft"><ikep4j:message pre='${preModeler}' key='form.state.period'/></th>
				<td colspan="3">
					<input type="hidden" name="notificationId" />
					D <input name="day" type="text" class="inputbox" title="" value="0" size="4" />
					H <input name="hour" type="text" class="inputbox" title="" value="0" size="4" />
					M <input name="minutes" type="text" class="inputbox" title="" value="1" size="4" />					
				</td>
			</tr>
			<tr>
				<th scope="row" class="textLeft" colspan="4"><strong><ikep4j:message pre='${preModeler}' key='form.state.to'/></strong></th>
			</tr>							
			<tr>
				<th scope="row" class="textLeft">To</th>
				<td colspan="3">
					<input name="to" type="text" class="inputbox w70" title="" value="" size="18" readonly="true"/>&nbsp;
					<input name="to_type" type="hidden" class="inputbox w70" title="" value="participant" size="18" />&nbsp;
					<a href="#a"><img src="<c:url value='/base/images/icon/ic_alarm.png'/>" width="16" height="16" alt="" /></a>&nbsp;
				</td>
			</tr>	
			<tr>
				<th scope="row" class="textLeft">Cc</th>
				<td colspan="3">
					<input name="cc" type="text" class="inputbox w70" title="" value="" size="18" readonly="true"/>&nbsp;
					<input name="cc_type" type="hidden" class="inputbox w70" title="" value="participant" size="18" />&nbsp;
					<a href="#a"><img src="<c:url value='/base/images/icon/ic_alarm.png'/>" width="16" height="16" alt="" /></a>&nbsp;
				</td>
			</tr>	
			<tr>
				<th scope="row" class="textLeft">Bcc</th>
				<td colspan="3">
					<input name="bcc" type="text" class="inputbox w70" title="" value="" size="18" readonly="true"/>&nbsp;
					<input name="bcc_type" type="hidden" class="inputbox w70" title="" value="participant" size="18" />&nbsp;
					<a href="#a"><img src="<c:url value='/base/images/icon/ic_alarm.png'/>" width="16" height="16" alt="" /></a>&nbsp;
				</td>
			</tr>										
			<tr>
				<th scope="row" class="textLeft" colspan="4"><strong><ikep4j:message pre='${preModeler}' key='form.state.message'/></strong></th>
			</tr>									
			<tr>
				<th scope="row" class="textLeft"><ikep4j:message pre='${preModeler}' key='form.state.title'/></th>
				<td colspan="3">
					 <input name="title" type="text" class="inputbox w80" title="" value="" size="18" />
				</td>
			</tr>		
			<tr>
				<th scope="row" class="textLeft"><ikep4j:message pre='${preModeler}' key='form.state.content'/></th>
				<td colspan="3">
					 <textarea name="body" cols="0" rows="6" class="inputbox w80" title=""></textarea>
				</td>
			</tr>										
		</tbody>
	</table>
</div>
</form>
<!--//blockDetail_2 End-->	
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a class="button activityEmailNotificationConfirm" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.set'/></span></a></li>
		<li><a class="button activityEmailNotificationCancel" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.cancel'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
<!-- //Modal window End -->