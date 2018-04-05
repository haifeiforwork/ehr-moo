<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" errorPage=""  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preModeler"  value="message.workflow.modeler" />
<c:set var="preValidation"  value="validate.workflow.modeler" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script language="JavaScript">
	(function($){
		
		// 프로세스 참여자를 가져와서 리스트를 구성하여 뿌려준다.
		try {
			var participantId = xmlUtil.selectNodes("//lgcns:ParticipantExtendedAttribute/lgcns:Value");
			// ** 선택된 액티비티만 가져온다.
			var activity = xmlUtil.selectNodes("//xpdl:Activity");
			var participantName = xmlUtil.selectNodes("//xpdl:Participant/@Name");
			var participantNameStr = "";
			var participantJobTitle = "";
			var participantTeamName = "";
			var participantType = xmlUtil.selectNodes("//xpdl:ParticipantType/@Type");
			
			if(participantId.length > 0){
				$("#" + '<c:out value="${param.divId}"/>').html("");
			}
			
			for(var i=0; i < participantId.length; i++) {
				var id = participantId[i].firstChild.nodeValue;
				var name = participantName[i].firstChild.nodeValue.split("/")[0];
				var type = participantType[i].firstChild.nodeValue;
				
				for (var a = 0; a < activity.length; a++) {
					if (activity[a].getAttribute("Id") == activityId) {
						createActivityListParticipant (id, type, name, '<c:out value="${param.divId}"/>');
					}
				}
			}
			
			// 이미 참여하고 있는 값을 가져와 비교 후 selected 상태를 만든다.
			var paramParticipants = '<c:out value="${param.checkParticipants}"/>'.split(";");
			$("#" + '<c:out value="${param.divId}"/>' + " > li").find("input[name='participantId']").each(function(){
				for(var i=0; i < paramParticipants.length; i++){
					if( $(this).val() == paramParticipants[i] ){
						$(this).parent().parent().parent().parent().parent().attr("className", "ui-selected");
					}
				}
			});
		}catch(e){
			alert(e);
		}
		
		$( ".button, .bold" ).click(function() {
			// 버튼 이벤트 Id.
			var attrIds = $(this).attr("class").split(" ");
			var attrId = attrIds[attrIds.length - 1];
			
			switch ( attrId ){
				// 프로세스 변수 선택 확인
				case "createActivityParticipantPopupConfirm" :
					var activityParticipantIds = new Array();
					var activityParticipantTypes = new Array();
					var cnt = 0;
					$("#" + '<c:out value="${param.divId}"/>' + " > li.ui-selected").find("input[name='participantId']").each(function(){
						activityParticipantIds[cnt] = $(this).val();
						cnt = cnt + 1;
		            });
					
					$( '#' + '<c:out value="${param.layer}"/>' ).empty();
					$( '#' + '<c:out value="${param.layer}"/>' ).dialog( "close" );
					
					// opner로 참여자 값을 돌려준다.
					participantCallback(activityParticipantIds);
					break;	
					
				// 프로세스 변수 선택 취소
				case "createActivityParticipantPopupCancel" :
					$( '#' + '<c:out value="${param.layer}"/>' ).empty();
					$( '#' + '<c:out value="${param.layer}"/>' ).dialog( "close" );
					break;	
			}
		});
		
		$(function(){
			//=========================================================================
            // * Jquery SelecTable 초기화.
            //=========================================================================
            $('#' + '<c:out value="${param.divId}"/>').selectable();
		});
	})(jQuery);
</script>
<!-- Modal window Start -->
<!--blockDetail Start-->
<div class="blockDetail_2" id="participantsInfo" style="overflow-y:auto;height:100px;">
	<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.email.participant.notifycation'/>">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="20%" class="textCenter first"><ikep4j:message pre='${preModeler}' key='form.participant.id'/></th>
				<th scope="col" width="20%" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.participant.type'/></th>
				<th scope="col" width="20%" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.participant'/></th>
				<th scope="col" width="20%" class="textCenter last"><ikep4j:message pre='${preModeler}' key='form.participant.name'/></th>
			</tr>
		</thead>
	</table>
	<ol id='<c:out value="${param.divId}"/>'>
		<li>
			<table summary="">
				<caption></caption>
				<tbody>
					<tr>
						<td class="textCenter" colspan="4"><ikep4j:message pre='${preModeler}' key='message.30'/></td>
					</tr>
				</tbody>
			</table>
		</li>
	</ol>
</div>
<!--//blockDetail End-->	
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a class="button createActivityParticipantPopupConfirm" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.select'/></span></a></li>
		<li><a class="button createActivityParticipantPopupCancel" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.cancel'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
<!-- //Modal window End -->