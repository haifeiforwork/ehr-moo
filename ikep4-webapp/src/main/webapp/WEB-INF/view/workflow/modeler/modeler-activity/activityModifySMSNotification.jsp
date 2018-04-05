<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" errorPage=""  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preModeler"  value="message.workflow.modeler" />
<c:set var="preValidation"  value="validate.workflow.modeler" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script language="JavaScript">
	(function($){
		
		// Trigger 변경시에  option[AFTER, BEFPRE]를 생성한다.
		modifyActivitySMSStateChange = function(activityState, trigger){
			if( typeof(activityState) == 'string' ){
				activityState = activityState;
			}else{
				activityState = activityState.value;
			}
			
			if( activityState == 'EXPIRE'){
				var prop = new Array("BEFORE", "AFTER");
				$("#modifyActivitySMSNotification").find("select[name='trigger']").html("");
				$("#modifyActivitySMSNotification").find("select[name='trigger']").append('<option value="NONE"><ikep4j:message pre="${preModeler}" key="option.none"/></option>');
				for(var i=0; i < prop.length; i++){
					var option = document.createElement("option");
					option.value = prop[i];
					option.innerHTML = prop[i];
					$("#modifyActivitySMSNotification").find("select[name='trigger']").append(option);	
					if (prop[i] == trigger) {
						$("#modifyActivitySMSNotification").find("select[name='trigger'] > option[value=" + trigger + "]").attr("selected", true);
					}
				}
			}else{
				$("#modifyActivitySMSNotification").find("select[name='trigger']").html("");
				$("#modifyActivitySMSNotification").find("select[name='trigger']").append('<option value="NONE"><ikep4j:message pre="${preModeler}" key="option.none"/></option>');
			};
		}
		
		var activity = xmlUtil.selectNodes("//xpdl:Activity");
		for (var a = 0; a < activity.length; a++) {
			if (activity[a].getAttribute("Id") == '<c:out value="${param.activityId}"/>') {
				var notification = activity[a].getElementsByTagName("lgcns:Notification");
				var notifyArray = new Array();
				var arrayCnt = 0;
				
				for (var n = 0; n < notification.length; n++) {
					if (notification[n].getAttribute("Id") == '<c:out value="${param.notifycationId}"/>') {
						notifyArray[arrayCnt] = notification[n];
						arrayCnt = arrayCnt + 1;
					}
				}
				
				for (var i = 0; i < notifyArray.length; i++) {
					var expression = notifyArray[i].getElementsByTagName("lgcns:Expression");
					var action = notifyArray[i].getElementsByTagName("lgcns:Action");
					var recipient = action[0].getElementsByTagName("lgcns:Recipient");
					var body = action[0].getElementsByTagName("lgcns:Body");
					
					// conditionType
					if( expression.length > 0 ){
						$("#modifyActivitySMSNotification").find("input[name='conditionType']").val(expression[0].firstChild.nodeValue);
					}
					
					for (var m = 0; m < recipient.length; m++) {
						if (recipient[m].getAttribute("Name") == "to") {
							$("#modifyActivitySMSNotification").find("input[name='to']").val(recipient[m].firstChild.nodeValue);
						}
						
						if (recipient[m].getAttribute("Name") == "from") {
							$("#modifyActivitySMSNotification").find("input[name='from']").val(recipient[m].firstChild.nodeValue);
						}
					}
					// 시작시에 Trigger 관련 데이터를 생성하여 selected 한다.
					modifyActivitySMSStateChange(action[0].getAttribute("Name"), action[0].getAttribute("Trigger"));
					
					$("#modifyActivitySMSNotification").find("select[name='activityState'] > option[value=" + action[0].getAttribute("Name") + "]").attr("selected", true);
					$("#modifyActivitySMSNotification").find("textarea[name='body']").val(body[0].firstChild.nodeValue);
					
					// Duration 관련하여 데이터를 replace 한다.
					var replaceDurations = getDateFromISODuration(action[0].getAttribute("Duration"));
					if (replaceDurations.length == 1) {
						$("#modifyActivityEMAILNotification").find("input[name='day']").val("0");
					$("#modifyActivityEMAILNotification").find("input[name='hour']").val("0");
						$("#modifyActivityEMAILNotification").find("input[name='minutes']").val(replaceDurations[0].replace("M", ""));
					}
					else if (replaceDurations.length == 2) {
						$("#modifyActivityEMAILNotification").find("input[name='day']").val("0");
						$("#modifyActivityEMAILNotification").find("input[name='hour']").val(replaceDurations[0].replace("H", ""));
						$("#modifyActivityEMAILNotification").find("input[name='minutes']").val(replaceDurations[1].replace("M", ""));
					}else{
						$("#modifyActivityEMAILNotification").find("input[name='day']").val(replaceDurations[0].replace("D", ""));
						$("#modifyActivityEMAILNotification").find("input[name='hour']").val(replaceDurations[1].replace("H", ""));
						$("#modifyActivityEMAILNotification").find("input[name='minutes']").val(replaceDurations[2].replace("M", ""));
					}
				}
			}
		}
		
		$(document).ready(function () {
			
			var validOptions = {
				rules : {
					day     : { required : true },
					hour   	: { required : true },
					minutes : { required : true },
					to      : { required : true },
					body    : { required : true }
				},
				messages : {
					day     : { required  : "<ikep4j:message pre='${preValidation}' key='message.14'/>" },
					hour    : { required  : "<ikep4j:message pre='${preValidation}' key='message.15'/>" },
					minutes : { required  : "<ikep4j:message pre='${preValidation}' key='message.16'/>" },
					to      : { required  : "<ikep4j:message pre='${preValidation}' key='message.17'/>" },
					body    : { required  : "<ikep4j:message pre='${preValidation}' key='message.19'/>" }
				},
				notice : {
			        body   : "<ikep4j:message pre='${preValidation}' key='message.21'/>"
			    },
				submitHandler : function(form){
					var activityNotification = new Array();
					activityNotification["activityId"] = '<c:out value="${param.activityId}"/>';
					activityNotification["id"] 		   = '<c:out value="${param.notifycationId}"/>';
					activityNotification["condition"]  = $("#modifyActivitySMSNotification").find("input[name=conditionType]").val();
					activityNotification["type"] 	   = 'SMS';
					activityNotification["state"] 	   = $("#modifyActivitySMSNotification").find("select[name=activityState]").val();
				    activityNotification["trigger"]    = $("#modifyActivitySMSNotification").find("select[name=trigger]").val();
				    activityNotification["day"] 	   = $("#modifyActivitySMSNotification").find("input[name=day]").val();
				    activityNotification["hour"] 	   = $("#modifyActivitySMSNotification").find("input[name=hour]").val();
				    activityNotification["minutes"]    = $("#modifyActivitySMSNotification").find("input[name=minutes]").val();
			 		activityNotification["from"] 	   = $("#modifyActivitySMSNotification").find("input[name=from]").val();
			 	    activityNotification["from_type"]  = $("#modifyActivitySMSNotification").find("input[name=from_type]").val();
			 	    activityNotification["to"] 		   = $("#modifyActivitySMSNotification").find("input[name=to]").val();
				    activityNotification["to_type"]    = $("#modifyActivitySMSNotification").find("input[name=to_type]").val();
				    activityNotification["title"] 	   = $("#modifyActivitySMSNotification").find("input[name=title]").val();
				    activityNotification["body"] 	   = $("#modifyActivitySMSNotification").find("textarea[name=body]").val();
				    
					try {
						xpdl.setActivityNotification(activityNotification, 'replace');
						alert("<ikep4j:message pre='${preModeler}' key='message.update'/>");
						
						createNotificationList('activityAddNotifyTbody', 'SMS');
					}catch(e){
//						alert(e);
						alert(activityNotification.state + " <ikep4j:message pre='${preModeler}' key='message.44'/>");
					}
					$( '#' + '<c:out value="${param.layer}"/>' ).empty();
					$( '#' + '<c:out value="${param.layer}"/>' ).dialog( "close" );
				}
			};
			
			new iKEP.Validator("#modifyActivitySMSNotificationForm", validOptions);
			
			$("input[name='day']").focus();
			
			$( ".button, .bold" ).click(function() {
				// 버튼 이벤트 Id.
				var attrIds = $(this).attr("class").split(" ");
				var attrId = attrIds[attrIds.length - 1];
				
				switch ( attrId ){
					// SMS 추가 확인
					case "activitySMSNotificationConfirm" :
						$("#modifyActivitySMSNotificationForm").submit();
						break;	
						
					// SMS 추가 취소
					case "activitySMSNotificationCancel" :
						$( '#' + '<c:out value="${param.layer}"/>' ).empty();
						$( '#' + '<c:out value="${param.layer}"/>' ).dialog( "close" );
						break;	
				}
			});
		});
		
		// 참여자 추가 이미지 클릭.
		var name = "";
		$("#modifyActivitySMSNotification").find( "a[href=#a] > img" ).click(function() {
			var type = $(this).attr("src").split("/")[$(this).attr("src").split("/").length -1];
			name =  $(this).parent().parent().find("input[type='text']").attr("name");
			
			if ( type == 'ic_alarm.png' ){
				var url = '<c:url value="activityCreateParticipantPopup.do?checkParticipants="/>' 
				+ encodeURIComponent($("#modifyActivitySMSNotification").find("input[name='"+ name +"']").val()) 
				+ "&layer=" + "dialog-activityModifyParticipantPopup-SMS"
				+ "&divId=" + "activityModifyParticipantPopup-SMS";
				style = { width: 520, height:210, modal:true, resizable: false };
				$("#dialog-activityModifyParticipantPopup-SMS").load(url).error(function(event, request, settings) {alert("error");});
				$("#dialog-activityModifyParticipantPopup-SMS").dialog(style);
				$("#dialog-activityModifyParticipantPopup-SMS").dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
				$("#dialog-activityModifyParticipantPopup-SMS").dialog("widget").children(":first").addClass("ui-widget-workflow-header");
			}else{
				url = '<c:url value="expressionBuilder.do?target=conditionType&expressionEditorContent="/>' + encodeURIComponent($("#modifyActivitySMSNotification").find("input[name='conditionType']").val());;
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
			$("input[name='" + name + "']").val(participants);		
		}
		
	})(jQuery);
</script>
<style>
    #activityModifyParticipantPopup-SMS .ui-selecting { background: #FECA40; }
    #activityModifyParticipantPopup-SMS .ui-selected { background: #F39814; color: white; }
    #activityModifyParticipantPopup-SMS { list-style-type: none; margin: 0; padding: 0; width: 100%; }
	#activityModifyParticipantPopup-SMS li { margin: 0px; padding: 0px; font-size: 1em; }
</style>
<!-- Modal window Start -->
<!--blockDetail_2 Start-->
<form id="modifyActivitySMSNotificationForm" name="modifyActivitySMSNotificationForm" onsubmit="return false;">
<div class="blockDetail_2">
	<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.sms.notifycation.update'/>" id="modifyActivitySMSNotification" border="1">
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
					<select title="<ikep4j:message pre='${preModeler}' key='form.state.state'/>" name="activityState" onchange="modifyActivitySMSStateChange(this, '');">
						<option value="ASSIGN">ASSIGN</option>
						<option value="FINISH">FINISH</option>
						<option value="EXPIRE">EXPIRE</option>
					</select>
				</td>
				<th scope="row" width="20%" class=" textCenter"><ikep4j:message pre='${preModeler}' key='form.state.time'/></th>
				<td width="30%">							
					<select title="<ikep4j:message pre='${preModeler}' key='form.state.time'/>" name="trigger">
						<option value="NONE"><ikep4j:message pre='${preModeler}' key='option.none'/></option>
						<option value="BEFORE">BEFORE</option>
						<option value="AFTER">AFTER</option>
					</select>
				</td>					
			</tr>
			<tr>
				<th scope="row" class="textLeft"><ikep4j:message pre='${preModeler}' key='form.state.period'/></th>
				<td colspan="3">
					day <input name="day" type="text" class="inputbox" title="" value="" size="4" />
					hour <input name="hour" type="text" class="inputbox" title="" value="" size="4" />
					minute <input name="minutes" type="text" class="inputbox" title="" value="" size="4" />					
				</td>
			</tr>
			<tr>
				<th scope="row" class="textLeft" colspan="4"><strong><ikep4j:message pre='${preModeler}' key='form.state.to'/></strong></th>
			</tr>							
			<tr>
				<th scope="row" class="textLeft">From</th>
				<td colspan="3">
					<input name="to" type="text" class="inputbox w70" title="" value="" size="18" readonly="true"/>&nbsp;
					<input name="to_type" type="hidden" class="inputbox w70" title="" value="participant" size="18" />&nbsp;
					<a href="#a"><img src="<c:url value='/base/images/icon/ic_alarm.png'/>" width="16" height="16" alt="" /></a>&nbsp;<a href="#a"><img src="<c:url value='/base/images/icon/ic_menu_microblog.png'/>" width="16" height="16" alt="" /></a>
				</td>
			</tr>	
			<tr>
				<th scope="row" class="textLeft">To</th>

				<td colspan="3">
					<input name="from" type="text" class="inputbox w70" title="" value="" size="18" readonly="true"/>&nbsp;
					<input name="from_type" type="hidden" class="inputbox w70" title="" value="participant" size="18" />&nbsp;
					<a href="#a"><img src="<c:url value='/base/images/icon/ic_alarm.png'/>" width="16" height="16" alt="" /></a>&nbsp;<a href="#a"><img src="<c:url value='/base/images/icon/ic_menu_microblog.png'/>" width="16" height="16" alt="" /></a>
				</td>
			</tr>	
			<tr>
				<th scope="row" class="textLeft" colspan="4"><strong><ikep4j:message pre='${preModeler}' key='form.state.message'/></strong></th>
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
		<li><a class="button activitySMSNotificationConfirm" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.update'/></span></a></li>
		<li><a class="button activitySMSNotificationCancel" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.cancel'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
<!-- //Modal window End -->