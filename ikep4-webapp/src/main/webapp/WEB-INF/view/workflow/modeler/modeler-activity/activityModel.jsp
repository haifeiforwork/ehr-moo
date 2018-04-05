<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" errorPage=""  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp" %>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preModeler"  value="message.workflow.modeler" />
<c:set var="preValidation"  value="validate.workflow.modeler" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script>
	// 전역 액티비티 배열
	var activity = new Array();
	// 전역 액티비티 아이디
	var activityId = "";
	// 전역 액티비티 이름
    var activityName = "";
	// 전역 액티비티 타입
    var activityType = "";
	// 전역 게이트웨이 타입
    var gatewayType = "";
	// 전역 액티비티 우선 순위
	var activityPriority = "";
	// 전역 액티비티 설명
	var activityDescription = "";
	
    (function($){
		
		//=========================================================================
        // * Activity 기본정보 신규/수정/변경(action : append, replace, delete)
        //=========================================================================
		applyActivityDefaultInfo = function(action, id, name, type, gatewayType) {
			
			var $activityDefaultInfo = $("#activityDefaultInfo");
			activity["priority"] = "";
			activity["description"] = "";
			
			// Activity 이름 생성
			if (action == 'append') {
				// 임시번호로 생성한다. 후에 플렉스와 연동..
				activity["id"] = id;
				activity["name"] = name;
				activity["type"] = type;
				activity["gatewayType"] = gatewayType;
			
				// 하드코딩..
				activity["startMode"] = "Manual";
				activity["finishMode"] = "Manual";
				// XPDL에 저장.
				try {
					xpdl.setActivityDefaultInfo(activity, action);
				}catch(e){
					alert(e);
				}
			// Activity 이름 수정	
			}else if(action == 'replace') {	// 수정할 경우만 들어감.
				//Form 수정시..
				activity["id"] = $("#activityDefaultInfo").find("input[name=activityId]").val();
				activity["priority"] = $("#activityDefaultInfo").find("select[name=activityPriority]").val();
				activity["description"] = $("#activityDefaultInfo").find("textarea[name=activityDescription]").val();
				
				// XPDL에 저장.
				try {
					xpdl.setActivityDefaultInfo(activity, action);
				}catch(e){
					alert(e);
				}
			// Activity 삭제	
			}else if (action == 'remove') { // 삭제
				activity["id"] = id;
				// XPDL에 저장.
				try {
					xpdl.setActivityDefaultInfo(activity, action);
				}catch(e){
					alert(e);
				}
				var $pbox = $(".p_box_info_con");
				getProcessDefaultInfo();
			// Activity 이름 변경
			}else if(action == 'rename'){
				//Form 수정시..
				activity["id"] = id;
				activity["name"] = name;
				
				// XPDL에 저장.
				try {
					xpdl.setActivityDefaultInfo(activity, 'replace');
				}catch(e){
					alert(e);
				}
			// Activity 더블 클릭	
			}else {
				try {
					var act = xmlUtil.selectNodes("//xpdl:Activity");
					var activityDefaultInfo = xmlUtil.selectNodes("//xpdl:Activity");
					for(var n=0; n < act.length; n++){
						if (act[n].getAttribute("Id") == id) {
							activity["id"] = act[n].getAttribute("Id");
							activity["name"] = act[n].getAttribute("Name");
						}
					}
					for (var v = 0; v < activityDefaultInfo.length; v++) {
						if (activityDefaultInfo[v].getAttribute("Id") == id && type == 'MANUAL') {
							var priority = activityDefaultInfo[v].getElementsByTagName("Priority");
							var description = activityDefaultInfo[v].getElementsByTagName("Description");
							
							if(priority[0].hasChildNodes()){
								activity["priority"] = priority[0].firstChild.nodeValue;
							};
							if(description[0].hasChildNodes()){
								activity["description"] = description[0].firstChild.nodeValue;
							};
						}
					}
				}catch(e){
					alert(e);
				}
			}
			
			// 최초에 액티비티 기본정보, 액티비티 변수 In/Out 화면 리스트에 뿌려준다.
			createActivityDefaultInfo(activity);
			
			// 액티비티 변수 초기화.
			createActivityVariables("createVariable", "");
			
			// 고급 정보 초기화.
			getActivityAdvanceInfo(id);
			
			// 알림 초기화
			if ($("#tabs_div").find("select[name=notificationType] > option[value=EMAIL]").attr("selected") == true) {
				createNotificationList('activityAddNotifyTbody', 'EMAIL');	
			}else{
				createNotificationList('activityAddNotifyTbody', 'SMS');
			}
			
			// ** 참여자가 있을 경우 하단 리스트에 뿌려준다.
			try {
				var pId = xmlUtil.selectNodes("//lgcns:ParticipantExtendedAttribute/lgcns:Value");
				if (pId.length > 0) {
					getActivityXpdlListParticipant();
				}else{
					var $sel = $("#AddrControlForm").find("[name=addrGroupList]");
					$sel.empty();
				}
			}catch(e){
				alert(e);
			}
		}
		
		//=========================================================================
        // * 액티비티 고급정보 초기화
        //=========================================================================
        getActivityAdvanceInfo = function(id){
            var activity = xmlUtil.selectNodes("//xpdl:Activity");
			var $activityAdvanceInfo = $("#activityAdvanceInfo");
				
			for (var a = 0; a < activity.length; a++) {
				if (activity[a].getAttribute("Id") == id) {
					var duration = activity[a].getElementsByTagName("lgcns:Duration");
					var durationVariable = activity[a].getElementsByTagName("lgcns:DurationVariable");
					var advanceUrl = activity[a].getElementsByTagName("lgcns:ActivityUrl");
					
					if( duration.length > 0 ){
						$("#isDeadline").attr("checked", true);
						$activityAdvanceInfo.find("input[value='durationDay']").attr("checked", true);
						$activityAdvanceInfo.find("input[name='day']").attr("disabled", false);
						$activityAdvanceInfo.find("input[name='hour']").attr("disabled", false);
						$activityAdvanceInfo.find("input[name='minutes']").attr("disabled", false);
						
						// Duration 관련하여 데이터를 replace 한다.
						var replaceDurations = getDateFromISODuration(duration[0].firstChild.nodeValue);
						$activityAdvanceInfo.find("input[name='day']").val(replaceDurations[0].replace("D", ""));
						$activityAdvanceInfo.find("input[name='hour']").val(replaceDurations[1].replace("H", ""));
						$activityAdvanceInfo.find("input[name='minutes']").val(replaceDurations[2].replace("M", ""));
					}
					
					if( durationVariable.length > 0 ){
						$("#isDeadline").attr("checked", true);
						$activityAdvanceInfo.find("input[value='variable']").attr("checked", true);
						$activityAdvanceInfo.find("input[name='durationVariable']").attr("disabled", false);
						$activityAdvanceInfo.find("input[name='durationVariable']").val(durationVariable[0].firstChild.nodeValue);
					}
					
					if( advanceUrl.length > 0 ){
						$activityAdvanceInfo.find("input[name='activityUrl']").val(advanceUrl[0].firstChild.nodeValue);
					}
				}
			}
        };
		
        //=========================================================================
        // * 액티비티 기본 정보 화면 Function.
        //=========================================================================
        createActivityDefaultInfo = function(activity){
            $("#activityId").text(activity.id);			    	   
			$("#activityName").text(activity.name);		    	   
			$("#activityType").text(activity.type);		    	   
			$("#gatewayType").text(activity.gatewayType);   	   
			$("#activityPriority").text(activity.priority); 	   
			$("#activityDescription").text(activity.description);
			
			activityId 		    = activity.id;
			activityName 		= activity.name;
			activityType 		= activity.type;
			gatewayType 		= activity.gatewayType;  
			activityPriority 	= activity.priority;
			activityDescription = activity.description;
			
        };
		
		//=========================================================================
        // * 알림 리스트 초기화
        //=========================================================================
        createNotificationList = function(div, type){
			try {
				var activity = xmlUtil.selectNodes("//xpdl:Activity");
				var emptyMessage = '<table summary=""><caption></caption><tbody><tr><td class="textCenter" colspan="4"><ikep4j:message pre="${preModeler}" key="message.31"/> ' + type + ' <ikep4j:message pre="${preModeler}" key="message.32"/></td></tr></tbody></table>';
				
				for (var a = 0; a < activity.length; a++) {
					if (activity[a].getAttribute("Id") == activityId) {
						var notification = activity[a].getElementsByTagName("lgcns:Notification");
						var prop = new Array("Check", "Trriger", "To", "Duration", "Body");
						var arrayCnt = 0;
						var notifyArray = new Array();
						$("#" + div).html("");
						
						for (var n = 0; n < notification.length; n++) {
							if (notification[n].getAttribute("Type") == type) {
								notifyArray[arrayCnt] = notification[n];
								arrayCnt = arrayCnt + 1;
							}
						}
						
						for (var i = 0; i < notifyArray.length; i++) {
							var li = document.createElement("li");
							var table = document.createElement("table");
							var tbody = document.createElement("tbody");
							var tr = document.createElement("tr");
							table.width = "100%";
							
							for (var p = 0; p < prop.length; p++) {
								var td = document.createElement("td");
								var input = document.createElement("input");
								var action = notifyArray[i].getElementsByTagName("lgcns:Action");
								var recipient = action[0].getElementsByTagName("lgcns:Recipient");
								var body = action[0].getElementsByTagName("lgcns:Body");
								var trigger = "";
								
								switch (prop[p]) {
									case "Check":
										td.innerHTML = "<input type='checkbox'/>";
										td.width = "5%";
										td.className = "textCenter first";
										break;
									
									case "Trriger":
										td.innerHTML = action[0].getAttribute("Name");
										td.width = "20%";
										td.className = "textCenter";
										break;
									case "To":
										for (var m = 0; m < recipient.length; m++) {
											if (recipient[m].getAttribute("Name") == "to") {
												td.innerHTML = "TO. " + recipient[m].firstChild.nodeValue;
												td.width = "45%";
												td.className = "textCenter";
											}
										}
										break;
									case "Duration":
										switch (action[0].getAttribute("Trigger")) {
											case "NONE":
												trigger = "";
												break;
											case "BEFORE":
												trigger = "<ikep4j:message pre='${preModeler}' key='form.state.before'/>";
												break;
											case "AFTER":
												trigger = "<ikep4j:message pre='${preModeler}' key='form.state.after'/>";
												break;
										}
										var replaceDurations = getDateFromISODuration(action[0].getAttribute("Duration"));
										var replaceDuration = "";
										for(var r=0; r < replaceDurations.length; r++){
											replaceDuration = replaceDuration + replaceDurations[r];
										}
										td.innerHTML = replaceDuration + " " + trigger;
										td.width = "16%";
										td.className = "textCenter";
										break;
									case "Body":
										td.innerHTML = "<a href=#a onclick=alertNotificationMessage('" + notifyArray[i].getAttribute("Id") + "','" + type + "');><img src=<c:url value='/base/images/workflow/ic_menu_microblog.png'/> alt='' /></a>";
										td.width = "14%";
										td.className = "textCenter last";
										input.type = "hidden";
										input.name = "notifycationId";
										input.value = notifyArray[i].getAttribute("Id");
										td.appendChild(input);
										break;
								}
								tr.appendChild(td);
							}
							tbody.appendChild(tr);
							table.appendChild(tbody);
							li.appendChild(table);
							$("#" + div).append(li);
						}
						
						if( notifyArray.length == 0 ){
							var li = document.createElement("li");
							li.innerHTML = emptyMessage;
							$("#" + div).append(li);
						}
					}
				}
			}catch(e){
				alert(e);
			}
		}
		
		//=========================================================================
        // * 알림 메시지 팝업
        //=========================================================================
        alertNotificationMessage = function(nId, type){
			try {
				var notification = xmlUtil.selectNodes("//lgcns:Notification");
				for (var n = 0; n < notification.length; n++) {
					if (notification[n].getAttribute("Type") == type) {
						if (notification[n].getAttribute("Id") == nId) {
							var action = notification[n].getElementsByTagName("lgcns:Action");
							var title = action[0].getElementsByTagName("lgcns:Title");
							var body = action[0].getElementsByTagName("lgcns:Body");
							var $dialogId = $( "#dialog-activityNotificationMessage");
							var url = "";
							if( type == 'EMAIL' ){
								url = "<c:url value='activityNotificationMessage.do?title='/>" + encodeURIComponent(title[0].firstChild.nodeValue) + "&body=" + encodeURIComponent(body[0].firstChild.nodeValue) + "&type=" + encodeURIComponent(type);
							}else{
								url = "<c:url value='activityNotificationMessage.do?body='/>" + encodeURIComponent(body[0].firstChild.nodeValue) + "&type=" + encodeURIComponent(type);
							}
							$dialogId.load(url).error(function(event, request, settings) {alert("<ikep4j:message pre='${preValidation}' key='error'/>");});
							$dialogId.dialog({ width: 520, height:160, modal:true, resizable: false });
							$dialogId.dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
							$dialogId.dialog("widget").children(":first").addClass("ui-widget-workflow-header");
						}
					}
				}
			}catch(e){
				alert(e);
			}
		}
		
		//=========================================================================
        // * 액티비티 변수 정보 리스트 초기화 및  팝업 Function.
        //=========================================================================
        createActivityVariables = function(div, activityVariableIds){
			try {
				var dataFieldNodes = xmlUtil.selectNodes("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:DataFields/xpdl:DataField");
				var formalParameterNodes = xmlUtil.selectNodes("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:FormalParameters/xpdl:FormalParameter");
				
				var activity = xmlUtil.selectNodes("//xpdl:Activity");
				var inputSetNodes;
				var outputSetNodes;
				
				// 액티비티가 선택된 변수 In/Out
				for (var a = 0; a < activity.length; a++) {
					if (activity[a].getAttribute("Id") == activityId) {
						inputSetNodes = activity[a].getElementsByTagName("Input");
						outputSetNodes = activity[a].getElementsByTagName("Output");
					}
				}
				
				if( inputSetNodes.length == 0 ){
					$("#selectabledin").html("");
					var li = '<li><table summary=""><caption></caption><tbody><tr><td class="textCenter" colspan="6"><ikep4j:message pre="${preModeler}" key="message.33"/></td></tr></tbody></table></li>';
					addSelecTable(li, "selectabledin");	
				}
				
				if( outputSetNodes.length == 0 ){
					$("#selectabledout").html("");
					var li = '<li><table summary=""><caption></caption><tbody><tr><td class="textCenter" colspan="6"><ikep4j:message pre="${preModeler}" key="message.34"/></td></tr></tbody></table></li>';
					addSelecTable(li, "selectabledout");	
				}
				
				
				var expression = xmlUtil.selectNodes("//lgcns:Name");
				var variableType = "";
				if( $("#activityVariableSearch > option[value=INPUT]").attr("selected") == true ){
					variableType = "in";
				}else{
					variableType = "out";
					
				}
			
				// 팝업시에만 리스트 초기화.
				if( div == "selectable" ){
					$("#selectable").html("");	
				}
				
//				if( div == "selectabledin" || div == "selectabledout" ){
//					if ( $("#" + div).find("input[name='Id']").length == 0 ){
//						$("#" + div).html("");
//					}
//				}
				
	            if (dataFieldNodes.length > 0) {
					
					var prop = new Array("Id", "Name", "IsArray", "Type", "InOut", "KeyIndex");
					// 전체 프로세스 변수 목록
					for (var i = 0; i < dataFieldNodes.length; i++) {
						var li = document.createElement("li");
						var table = document.createElement("table");
						var tbody = document.createElement("tbody");
						var tbodyRow = document.createElement("tr");
						table.border = 0;
						table.width = "100%";
						
						// Id, Name, IsArray, Type, Inout 순서대로 XPDL에서 정보를 가져와 테이블을 구성한다.
						for (var n = 0; n < prop.length; n++) {
							var text = "";
							var name = prop[n];
							
							switch (prop[n]) {
								case "Type":
									text = dataFieldNodes[i].getElementsByTagName("BasicType")[0].getAttribute("Type");
									break;
								case "InOut":
									for (var p = 0; p < formalParameterNodes.length; p++) {
										if (dataFieldNodes[i].getAttribute("Id") == formalParameterNodes[p].getAttribute("Id")) {
											text = formalParameterNodes[p].getAttribute("Mode");
										}
									}
									break;
								case "IsArray":
									text = dataFieldNodes[i].getAttribute(prop[n]) != null ? dataFieldNodes[i].getAttribute(prop[n]) : "FALSE";
									break;
								case "KeyIndex":
									for (var e = 0; e < expression.length; e++) {
										if (expression[e].firstChild.nodeValue == dataFieldNodes[i].getAttribute("Id")) {
											text = expression[e].parentNode.getAttribute("Index");
										}
									}
									break;
								default:
									text = dataFieldNodes[i].getAttribute(prop[n]);
									break;
							}
							var width = "16.6%";
							tbodyRow = addTbodyRowTable(tbodyRow, name, text, width);
						}
						tbody.appendChild(tbodyRow);
						table.appendChild(tbody);
						li.appendChild(table);
						
						switch (div) {
							case "createVariable":
								// InputSet
								if (inputSetNodes.length > 0) {
									for (var o = 0; o < inputSetNodes.length; o++) {
										if (inputSetNodes[o].getAttribute("ArtifactId") == dataFieldNodes[i].getAttribute("Id")) {
											addSelecTable(li, "selectabledin");
										}
									}
								}
								
								// OutputSet
								if (outputSetNodes.length > 0) {
									for (var s = 0; s < outputSetNodes.length; s++) {
										if (outputSetNodes[s].getAttribute("ArtifactId") == dataFieldNodes[i].getAttribute("Id")) {
											addSelecTable(li.cloneNode(true), "selectabledout");
										}
									}
								}
								
								break;
								
							default:
								// 팝업 후 selectabledin/selectabledout Div 목록에 뿌려준다. 
								if (div == "selectabled" + variableType) {
									if( activityVariableIds.length > 0 ){
										for (var v = 0; v < activityVariableIds.length; v++) {
											// 팝업에서 선택된 것만 뿌려준다.
											if (activityVariableIds[v] == dataFieldNodes[i].getAttribute("Id")) {
												addSelecTable(li, div);
											}
										}
									}
								}
								else {
									var Ids = new Array();
									var isData = false;
									var cnt = 0;
									
									// 팝업에서 사용 In/Out 리스트를 비교하여 선택된 건 제외하고 리스트를 뿌려준다.
									$("#selectabled" + variableType).find("input[name='Id']").each(function(){
										Ids[cnt] = $(this).val();
										cnt = cnt + 1;
									});
									
									// 데이터필드와 리스트를 비교해서 단 한개라도 일치하면 true로 반환한다.
									for (var g = 0; g < Ids.length; g++) {
										if (dataFieldNodes[i].getAttribute("Id") == Ids[g]) {
											isData = true;
											break;
										}
									}
									if (isData == false) {
										addSelecTable(li, div);
									}
								}
								break;
						}
					}
				}
			}catch(e){
				alert(e);
			}
        };
        
        //=========================================================================
        // * Dynamic Table TbodyRow. Function.
        //=========================================================================
        addTbodyRowTable = function(tbodyRow, name, text, width){
            var cell = document.createElement("td");
            var input = document.createElement("input");
            if( tbodyRow.getElementsByTagName("td").length == 0 ){
				cell.className = "textCenter first";
			}else{
				cell.className = "textCenter";	
			}
			cell.width = width;
            tbodyRow.id = "variable";
            input.name = name;
            input.value = text;
            input.type = "hidden";
            cell.innerHTML = text;
            cell.appendChild(input);
            tbodyRow.appendChild(cell);
            
            return tbodyRow;
        };
		
		//=========================================================================
        // * Dynamic Table addSelecTable. Function.
        //=========================================================================
        addSelecTable = function(li, div){
			$("#" + div).append(li);
        };
		
		//=========================================================================
        // * Activity 변수 설정 (mode : in, out) (action : append, remove) Function.
        //=========================================================================
		applyActivityVariable = function(mode, activityId, activityVariableIds, action) {
			
			// 변수 추가.
			if( action == 'append') {
				try {
					xpdl.setActivityVariables(mode, activity.id, activityVariableIds, 'append');
					alert("<ikep4j:message pre='${preModeler}' key='message.create'/>");
				}catch(e){
					alert(e);
				}
				if( $("#selectabled" + mode).find("input[name='Id']").length == 0 ){
					$("#selectabled" + mode).html("");	
				}
				// 변수를 선택 한 후에 selected 리스트에 뿌려주는 function.
				createActivityVariables("selectabled" + mode, activityVariableIds);
			}
			
			// 변수 삭제.
			if( action == 'remove' ){
				if($("#selectabled" + mode + " > li.ui-selected").find("input[name='Id']").length >= 1){
					var newActivityVariableIds = new Array();
					var activityVariableIds = new Array();
					var cnt = 0;
					// 현재 선택한 selected 된 테이블 가져오기.
		            $("#selectabled" + mode + " > li.ui-selected").find("input[name='Id']").each(function(){
						activityVariableIds[cnt] = $(this).val();
						cnt = cnt + 1;
		            });
					try {
						xpdl.setActivityVariables(mode, activityId, activityVariableIds, 'remove');
					}catch(e){
						alert(e);
					}
	
					var Ids = new Array();
					cnt = 0;
					// 선택 되기전에 전체 테이블 가져오기.
					$("#selectabled" + mode).find("input[name='Id']").each(function(){
						Ids[cnt] = $(this).val();
						cnt = cnt + 1;
	                });
					
					cnt = 0;
					for(var g=0; g < Ids.length; g++){
						var isData = false;
	
						for (var k = 0; k < activityVariableIds.length; k++) {
							if( activityVariableIds[k] == Ids[g] ){
								isData = true;
								break;
							}
						}
						
						if( isData == false ){
							newActivityVariableIds[cnt] = Ids[g];
							cnt = cnt + 1;
						}
					}
					
//					$("#selectabled" + mode).html("");
					// 변수를 선택 한 후에 selected 리스트에 뿌려주는 function.
					createActivityVariables("selectabled" + mode, newActivityVariableIds);
					alert(activityVariableIds.length + " <ikep4j:message pre='${preModeler}' key='message.delete'/>");
				}else{
					alert("<ikep4j:message pre='${preModeler}' key='message.35'/>");
				}
			}
			
		}
		
		//=========================================================================
        // * XPDL 참여자 목록 리스트 가져오는 Function.
        //=========================================================================
		getActivityXpdlListParticipant = function(){
			try {
				var participantId = xmlUtil.selectNodes("//lgcns:ParticipantExtendedAttribute/lgcns:Value");
				// ** 선택된 액티비티만 가져온다.
				var activity = xmlUtil.selectNodes("//xpdl:Activity");
				var participantName = xmlUtil.selectNodes("//xpdl:Participant/@Name");
				var participantNameStr = "";
				var participantJobTitle = "";
				var participantTeamName = "";
				var participantType = xmlUtil.selectNodes("//xpdl:ParticipantType/@Type");
				var $sel = $("#AddrControlForm").find("[name=addrGroupList]");
				$sel.empty();
			
				$("#activityParticipantsTbody").html("");
				$("#activityAddParticipantsTbody").html("");
				var tpl = "";
	
				for(var i=0; i < participantId.length; i++) {
					var id = participantId[i].firstChild.nodeValue;
					var name = participantName[i].firstChild.nodeValue;
					var type = participantType[i].firstChild.nodeValue;
					
					for (var a = 0; a < activity.length; a++) {
						var useType = "";
						
						if (activity[a].getAttribute("Id") == activityId) {
							var performer = activity[a].getElementsByTagName("Performer");
							
							// 참여 변수는 조직도 공통 $sel에선 제외.
							if (participantId[i].firstChild.nodeValue.indexOf("$") == -1) {
								switch (type) {
									case "ORGANIZATIONAL_UNIT":
										tpl = "addrBookItemGroup";
										useType = "group";
										participantNameStr = name;
										break;
									case "HUMAN":
										tpl = "addrBookItemUser";
										useType = "user";
										participantNameStr = name.split("/")[0];
										participantJobTitle = name.split("/")[1];
										participantTeamName = name.split("/")[2];
										break;
									case "ROLE":
										tpl = "addrBookItemRole";
										useType = "role";
										participantNameStr = name;
										break;
								}
								var data = {
									id: id,
									code: id,
									name: participantNameStr,
									type: useType,
									jobTitle: participantJobTitle,
									teamName: participantTeamName
								};
								var $option = $.tmpl(tpl, data).appendTo($sel);
								$.data($option[0], "data", data);
							}else{
								id = participantId[i].firstChild.nodeValue.replace("$", "");;
								name = participantName[i].firstChild.nodeValue.replace("$", "");;
							}
							
							// 액티비티 performer 개수.
							if( performer.length > 0 ){
								var cnt = 0;
								for( var n=0; n < performer.length; n++ ){
									// 현재 프로세스 참여자와 액티비티 참여자를 비교해서 있는 것만 값을 보여준다.
									if( id == performer[n].firstChild.nodeValue.replace("$", "") ){
										createActivityListParticipant (id, type, name, "activityAddParticipantsTbody");
										cnt = cnt + 1;
									}
								}	
								// 하나라도 프로세스 참여자와 관련된 액티비티 값이 있을 경우는 프로세스 참여자 리스트에서 제외한다.
								if( cnt == 0 ){
									createActivityListParticipant (id, type, name, "activityParticipantsTbody");
								}
							}else{
								createActivityListParticipant (id, type, name, "activityParticipantsTbody");
							}
						}
					}
				}
			}catch(e){
				alert(e);
			}
		}
		
		//=========================================================================
        // * 참여자 하단 탭에 리스트를 뿌려주는 Function.
        //=========================================================================
		createActivityListParticipant = function(paramId, paramType, paramName, paramDiv){
			var li = document.createElement("li");
            var table = document.createElement("table");
            var tbody = document.createElement("tbody");
            var tbodyRow = document.createElement("tr");
            table.border = 0;
            table.width = "100%";
			
			var prop = new Array("participantId", "participantType", "participantValue", "participantName");
			var name = "";
			var text = "";
			for(var n=0; n < prop.length; n++){
				switch( prop[n] ){
					case "participantId"    :
						name = prop[n];
						text = paramId;
						break;
					case "participantType"  :
						name = prop[n];
						text = paramType;
						break;
					case "participantValue" :
					    name = prop[n];
						text = paramId;
						break;
					case "participantName"  :
						name = prop[n];
						if ( paramType == "user" ){
							text = paramName.split("/")[0];
						}else{
							text = paramName;	
						}
						break;
				}
				var width = "25%";
				tbodyRow = addTbodyRowTable(tbodyRow, name, text, width);
				tbody.appendChild(tbodyRow);
             	table.appendChild(tbody);
             	li.appendChild(table);
			}
			addSelecTable(li, paramDiv);
		}
		
		//=========================================================================
        // * 액티비티 참여자 추가.
        //=========================================================================
		function applyActivityPerformers(action) {
			// action : append, remove
			var participantIds = new Array();
			var beforeParticipantIds = new Array();
			switch(action){
				case "append" :
					// 프로세스 참여자 리스트
					$("#activityParticipantsTbody > li.ui-selected").each(function(i) {
						participantIds[i] = $(this).find("input[name=participantId]").val();
					});
					break;
				case "remove" :
					$("#activityAddParticipantsTbody > li.ui-selected").each(function(i) {
						participantIds[i] = $(this).find("input[name=participantId]").val();
					});
					break;
			}
			try {
				xpdl.setActivityPerformers(activityId, participantIds, action);
			}catch(e){
				alert(e);
			}
			getActivityXpdlListParticipant();
		}
		
		//=========================================================================
        // * 액티비티 변수 Layout INPUT/OUPUT
        //=========================================================================
		activityVariableLayout = function(div) {
			if( div == 'INPUT' ){
				$("#selectabled-1").show();
				$("#selectabled-2").hide();
			}else{
				$("#selectabled-1").hide();
				$("#selectabled-2").show();	
			}
		}
		
		// Jquery 초기화
        $(function(){
			
			//=========================================================================
            // * 액티비티 클릭시 신규 생성.
            //=========================================================================
			resizeSouthPaneScroll();
			applyActivityDefaultInfo('<c:out value="${param.action}"/>', '<c:out value="${param.id}"/>', '<c:out value="${param.name}"/>', '<c:out value="${param.type}"/>', '<c:out value="${param.gatewayType}"/>');
        
            //=========================================================================
            // * 탭, 셀렉트 테이블 초기화. 
            //=========================================================================
            $tabs = $("#tabs_div").tabs();
			// ** 탭 디자인이 Jquery Tab과 충돌로 인해 class를 삭제해줘야 선이 맞음.
			$tabs.find("#tab-1").removeClass("ui-tabs-panel ui-widget-content ui-corner-bottom");
			$tabs.find("#tab-2").removeClass("ui-tabs-panel ui-widget-content ui-corner-bottom");
			$tabs.find("#tab-3").removeClass("ui-tabs-panel ui-widget-content ui-corner-bottom");
			$tabs.find("#tab-4").removeClass("ui-tabs-panel ui-widget-content ui-corner-bottom");
			$tabs.find("#tab-5").removeClass("ui-tabs-panel ui-widget-content ui-corner-bottom");
			
			// MANUAL만 전체 탭 보이기..
			if( '<c:out value="${param.id}"/>'.indexOf("MANUAL") == 0 ){
				$( "a[href=#tab-2]" ).show();
				$( "a[href=#tab-3]" ).show();
				$( "a[href=#tab-4]" ).show();
				$( "a[href=#tab-5]" ).show();
			}
			
			$tabs.find("#selectabled-1").removeClass("ui-tabs-panel ui-widget-content ui-corner-bottom");
			$tabs.find("#selectabled-2").removeClass("ui-tabs-panel ui-widget-content ui-corner-bottom");
			
			$("#selectabledin").selectable();
			$("#selectabledout").selectable();
			
			$("#activityParticipantsTbody").selectable();
			$("#activityAddParticipantsTbody").selectable();
			
			$( "a[href=#tab-1]" ).click(function() {
				$("#activityVariableSearch").hide();
				$("#createActivityVariable").hide();
				$("#removeActivityVariable").hide();
				$("#createActivityAdvanceInfo").hide();
				$("#activityAddrControl").hide();
				$("#activitySearch").hide();
				$("#createActivityNotification").hide();
				$("#modifyActivityNotification").hide();
				$("#deleteActivityNotification").hide();
			});
			$( "a[href=#tab-2]" ).click(function() {
				$("#activityVariableSearch").show();
				$("#createActivityVariable").show();
				$("#removeActivityVariable").show();
				$("#createActivityAdvanceInfo").hide();
				$("#activityAddrControl").hide();
				$("#activitySearch").hide();
				$("#createActivityNotification").hide();
				$("#modifyActivityNotification").hide();
				$("#deleteActivityNotification").hide();
				// 테이블 영역 높이 조절
				resizeSouthPaneScroll();
			});
			$( "a[href=#tab-3]" ).click(function() {
				$("#activityVariableSearch").hide();
				$("#createActivityVariable").hide();
				$("#removeActivityVariable").hide();
				$("#createActivityAdvanceInfo").show();
				$("#activityAddrControl").hide();
				$("#activitySearch").hide();
				$("#createActivityNotification").hide();
				$("#modifyActivityNotification").hide();
				$("#deleteActivityNotification").hide();
			});
			$( "a[href=#tab-4]" ).click(function() {
				$("#activityVariableSearch").hide();
				$("#createActivityVariable").hide();
				$("#removeActivityVariable").hide();
				$("#createActivityAdvanceInfo").hide();
				$("#activityAddrControl").show();
				$("#activitySearch").hide();
				$("#createActivityNotification").hide();
				$("#modifyActivityNotification").hide();
				$("#deleteActivityNotification").hide();
				// 테이블 영역 높이 조절
				resizeSouthPaneScroll();
			});
			$( "a[href=#tab-5]" ).click(function() {
				$("#activityVariableSearch").hide();
				$("#createActivityVariable").hide();
				$("#removeActivityVariable").hide();
				$("#createActivityAdvanceInfo").hide();
				$("#activityAddrControl").hide();
				$("#activitySearch").show();
				$("#createActivityNotification").show();
				$("#modifyActivityNotification").show();
				$("#deleteActivityNotification").show();
				// 테이블 영역 높이 조절
				resizeSouthPaneScroll();
			});
			
			// 프로세스 기본정보 돌아옴.
			$( "#activityArea" ).click(function() {
				getProcessDefaultInfo();
			});
            
            //=========================================================================
			// * 버튼 이벤트. (액티비티 변수 추가, 수정, 삭제, 고급정보, 액티비티 참여자 추가,삭제) 
			//=========================================================================
			$( ".button_s, .bold" ).click(function() {
				var attrId = $(this).attr("id");
				var $dialogId = $( "#dialog-" + attrId );
				switch (attrId) {
					// 액티비티 변수 추가
					case "createActivityVariable" :
						$dialogId.load('<c:url value="activityCreateVariable.do"/>').error(function(event, request, settings){alert("<ikep4j:message pre='${preValidation}' key='error'/>");});
						$dialogId.dialog({ width: 520, height:225, modal:true, resizable: false });
						$dialogId.dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
						$dialogId.dialog("widget").children(":first").addClass("ui-widget-workflow-header");
						break;
						
					// 액티비티 변수 삭제	
					case "removeActivityVariable" :
						var variableType = "";
						if( $("#activityVariableSearch > option[value='INPUT']").attr("selected") == true ){
							variableType = "in";
						}else{
							variableType = "out";
						}
						applyActivityVariable(variableType, activity.id, "", 'remove');
						break;
						
					// 액티비티 고급 정보
					case "createActivityAdvanceInfo" :
						activityId = $("#a_DefaultInfo").find("#activityId").text();

						var activityAdvance = new Array();
						activityAdvance["activityId"] = activityId;
						activityAdvance["isDeadline"] = $("#activityAdvanceInfo").find("input[name=isDeadline]")[0].checked;
						
						// durationType : radio type
						activityAdvance["durationType"] = $("#activityAdvanceInfo").find("input[name=duration]:checked")[0].value;
						
						// durationType 이 durationDay 일 경우만 활성화
						activityAdvance["day"] = $("#activityAdvanceInfo").find("input[name=day]").val();
						activityAdvance["hour"] = $("#activityAdvanceInfo").find("input[name=hour]").val();
						activityAdvance["minutes"] = $("#activityAdvanceInfo").find("input[name=minutes]").val();
						
						// durationType 이 variable 일 경우만 활성화
						activityAdvance["durationVariable"] = "$" + $("#activityAdvanceInfo").find("input[name=durationVariable]").val();
						activityAdvance["url"] = $("#activityAdvanceInfo").find("input[name=activityUrl]").val();
						try {
							xpdl.setActivityAdvanceInfo(activityAdvance);
							alert("<ikep4j:message pre='${preModeler}' key='message.36'/>");
						}catch(e){
							alert(e);
						}
						break;
						
					// 액티비티 고급 정보 변수 추가
					case "createActivityAdvanceInfoVariable" :
						$("#activityAdvanceInfo").find("input[value='variable']").click();
						if( $("#isDeadline").is(":checked") ){
							url = '<c:url value="activityCreateAdvanceVariable.do"/>';
							style = { width: 520, height:225, modal:true, resizable: false };
							$dialogId.load(url).error(function(event, request, settings) {alert("<ikep4j:message pre='${preValidation}' key='error'/>");});
							$dialogId.dialog(style);
							$dialogId.dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
							$dialogId.dialog("widget").children(":first").addClass("ui-widget-workflow-header");	
						}else{
							$("#activityAdvanceInfo").find("input[value='variable']").attr("checked", false);
							return false;
						}
						break;
					
					// 액티비티 참여자 추가
					case "activityAddParticipant" :
						if ($("#activityParticipantsTbody > li.ui-selected").length > 0) {
							applyActivityPerformers('append');
						}else{
							alert("<ikep4j:message pre='${preModeler}' key='message.noneselect'/>")
						}
						break;
						
					// 액티비티 참여자 삭제
					case "activityRemoveParticipant" :
						if ($("#activityAddParticipantsTbody > li.ui-selected").length > 0) {
							applyActivityPerformers('remove');
						}else{
							alert("<ikep4j:message pre='${preModeler}' key='message.noneselect'/>")
						}
						break;
						
					// 이메일, SMS 알림 생성 팝업.
					case "createActivityNotification" :
						var url = "";
						var style;
						if( $("#tabs_div").find("select[name=notificationType] > option[value=EMAIL]").attr("selected") == true ){
							$dialogId = $( "#dialog-" + attrId + "-EMAIL" );
							url = "<c:url value='activityCreateEmailNotification.do?activityId='/>" + encodeURIComponent(activityId) + "&layer=" + "dialog-" + encodeURIComponent(attrId) + "-EMAIL";
							style = { width: 520, height:480, modal:true, resizable: true };
						}else{
							$dialogId = $( "#dialog-" + attrId + "-SMS" );
							url = "<c:url value='activityCreateSMSNotification.do?activityId='/>" + encodeURIComponent(activityId) + "&layer=" + "dialog-" + encodeURIComponent(attrId) + "-SMS";
							style = { width: 520, height:430, modal:true, resizable: true };
						};
						
						$dialogId.load(url).error(function(event, request, settings) {alert("<ikep4j:message pre='${preValidation}' key='error'/>");});
						$dialogId.dialog(style);
						$dialogId.dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
						$dialogId.dialog("widget").children(":first").addClass("ui-widget-workflow-header");
						break;
					
					// 이메일, SMS 알림 수정 팝업.
					case "modifyActivityNotification" :
						var url = "";
						var style;
						
						if( $("#activityAddNotifyTbody").find("input[type='checkbox']:checked").length == 1 && $("#activityAddNotifyTbody").find("input[name='notifycationId']").length > 0){
							$("#activityAddNotifyTbody").find("input[type='checkbox']:checked").each(function(){
								
								if( $("#tabs_div").find("select[name=notificationType] > option[value=EMAIL]").attr("selected") == true ){
									url = "<c:url value='activityModifyEmailNotification.do?notifycationId='/>" + encodeURIComponent($(this).parent().parent().find("input[name='notifycationId']").val()) 
										+ "&activityId=" + encodeURIComponent(activityId) 
										+ "&layer=" + "dialog-" + encodeURIComponent(attrId) + "-EMAIL";
									$dialogId = $( "#dialog-" + attrId + "-EMAIL");
									style = { width: 520, height:480, modal:true, resizable: true };
								}else{
									url = "<c:url value='activityModifySMSNotification.do?notifycationId='/>" + encodeURIComponent($(this).parent().parent().find("input[name='notifycationId']").val()) 
										+ "&activityId=" + encodeURIComponent(activityId) 
										+ "&layer=" + "dialog-" + encodeURIComponent(attrId) + "-SMS";
									$dialogId = $( "#dialog-" + attrId + "-SMS");
									style = { width: 520, height:430, modal:true, resizable: true };
								};
								
								$dialogId.load(url).error(function(event, request, settings) {alert("<ikep4j:message pre='${preValidation}' key='error'/>");});
								$dialogId.dialog(style);
								$dialogId.dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
								$dialogId.dialog("widget").children(":first").addClass("ui-widget-workflow-header");
							});
						}else{
							alert("<ikep4j:message pre='${preModeler}' key='message.nonemulti'/>");
							return false;
						}
						break;
						
					// 이메일, SMS 알림 삭제.
					case "deleteActivityNotification" :
						var activityNotification = new Array();
						if ($("#activityAddNotifyTbody").find("input[type='checkbox']:checked").length >= 1 && $("#activityAddNotifyTbody").find("input[name='notifycationId']").length > 0) {
							$("#activityAddNotifyTbody").find("input[type='checkbox']:checked").each(function(){
								activityNotification["activityId"] = activityId;
								activityNotification["id"] = $(this).parent().parent().find("input[name='notifycationId']").val();
								try {
									xpdl.setActivityNotification(activityNotification, 'delete');
								} 
								catch (e) {
									alert(e);
								}
							});
							if ($("#tabs_div").find("select[name=notificationType] > option[value=EMAIL]").attr("selected") == true) {
								createNotificationList('activityAddNotifyTbody', 'EMAIL');
							}
							else {
								createNotificationList('activityAddNotifyTbody', 'SMS');
							}
							alert("<ikep4j:message pre='${preModeler}' key='message.delete'/>");
						}else{
							alert("<ikep4j:message pre='${preModeler}' key='message.37'/>");
							return false;
						}
						break;	
				}
			});
			
			//=========================================================================
            // * 액티비티 기본 수정 버튼 클릭 이벤트  및 팝업
            //=========================================================================
            $("#modifyActivityDefaultInfo").click(function(){
				var $dialogId = $("#dialog-activityModifyDefaultInfo");
                $dialogId.load('<c:url value="activityModifyDefaultInfo.do?activityId="/>' 
					+ encodeURIComponent(activityId) 	   + "&activityName=" 
					+ encodeURIComponent(activityName) 	   + "&activityType=" 
					+ encodeURIComponent(activityType) 	   + "&gatewayType=" 
					+ encodeURIComponent(gatewayType) 	   + "&activityPriority=" 
					+ encodeURIComponent(activityPriority) + "&activityDescription=" 
					+ encodeURIComponent(activityDescription)
				).error(function(event, request, settings){
                    alert("<ikep4j:message pre='${preValidation}' key='error'/>");
                });
				
				$dialogId.dialog({ width: 520, height:275, modal:true, resizable: false });
				$dialogId.dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
				$dialogId.dialog("widget").children(":first").addClass("ui-widget-workflow-header");
            });
			
			//=========================================================================
            // * 액티비티 고급 DeadLine 체크시 & Radio 버튼 클릭시
            //=========================================================================
			$("#isDeadline").click(function(){
				if ($(this).is(":checked")) {
					$("input[name=duration]").each(function(){
						if( $(this).val() == 'durationDay' ){
							$(this).attr("checked", true);
						}
					});
					$("input[name=day]").attr("disabled", false);
					$("input[name=hour]").attr("disabled", false);
					$("input[name=minutes]").attr("disabled", false);
					$("input[name=durationVariable]").attr("disabled", true);
				}else{
					$("input[name=duration]").each(function(){
						$(this).attr("checked", false);
					});
					$("input[name=day]").attr("disabled", true);
					$("input[name=hour]").attr("disabled", true);
					$("input[name=minutes]").attr("disabled", true);
					$("input[name=durationVariable]").attr("disabled", true);
				}
			});
			
			$("input[name=duration]").click(function(){
				if( $("#isDeadline").is(":checked")){
					switch( $(this).val() ){
						case "durationDay" :
							$("input[name=day]").attr("disabled", false);
							$("input[name=hour]").attr("disabled", false);
							$("input[name=minutes]").attr("disabled", false);
							$("input[name=durationVariable]").attr("disabled", true);
							break;
						case "variable" :
							$("input[name=day]").attr("disabled", true);
							$("input[name=hour]").attr("disabled", true);
							$("input[name=minutes]").attr("disabled", true);
							$("input[name=durationVariable]").attr("disabled", false);
							break;
					}
				}else{
					var result = confirm("<ikep4j:message pre='${preModeler}' key='message.38'/>");
					if(result == true){
						$("#isDeadline").attr("checked", true);
						$(this).click();
					}else{
						$("input[name=duration]").each(function(){
							$(this).attr("checked", false);
						});	
					}
				}
			});
			
        });
		
		//=========================================================================
        // * 액티비티 조직도 팝업.
        //=========================================================================
		$("#activityAddrControl").click(function() {
			var items = [];
			var $sel = $("#AddrControlForm").find("[name=addrGroupList]");
			$.each($sel.children(), function() {
				items.push($.data(this, "data"));
			});

			$controlType = $('input[name=controlType]:hidden').val() ;
			$controlTabType = $('input[name=controlTabType]:hidden').val() ;
			$selectType = $('input[name=selectType]:hidden').val() ;
			$selectMaxCnt = $('input[name=selectMaxCnt]:hidden').val() ;
			$searchSubFlag = $('input[name=searchSubFlag]:hidden').val() ;

			var dialog = new iKEP.Dialog({
				title: "<ikep4j:message pre='${preModeler}' key='popup.title.participant.create'/>",
				url: "<c:url value='/workflow/admin/participants.do'/>"+"?controlType=" + $controlType + "&controlTabType=" + $controlTabType + "&selectType=" + $selectType + "&selectMaxCnt=" + $selectMaxCnt + "&searchSubFlag=" + $searchSubFlag,
				modal: true,
				width: 700,
				height:510,
				resizable: false,
				params : {search:"keyword", items:items },
				open : function(event,ui){
							$(this).dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
							$(this).dialog("widget").children(":first").addClass("ui-widget-workflow-header");
							$(this).dialog("widget > iframe").children(":first").css("overflow-y","hidden");
				},
				scroll : "no",	
				callback : function(result) {
					$sel.empty();
					$("#activityParticipantsTbody").html("");
					
					// 참여자 전체삭제
					try {
						xpdl.removeProcessParticipants("p");
					}catch(e){
						alert(e);
					}

					$.each(result, function() {
						var type = "";
						var name = "";
						switch(this.type){
								case "user" :
									type = "HUMAN";
									name = this.name + "/" + this.jobTitle + "/" + this.teamName;
									break;
								case "group" :
									type = "ORGANIZATIONAL_UNIT";
									name = this.name;
									break;	
								case "role" :
									type = "ROLE";
									name = this.name;
									break;	
							}
						// 참여자  XPDL에 반영.
						applyParticipants('replace', (this.type == 'group' ? this.code : this.id), type, (this.type == 'group' ? this.code : this.id), name);
					});
					// 참여자에 없는 액티비티 수행자 삭제
//					try {
//						xpdl.removeInValidActivityPerformer();
//					}catch(e){
//						alert(e);
//					}
					// XPDL 참여자 목록 가져오는 Function.
					getActivityXpdlListParticipant();
				}
			});
		});
    })(jQuery);
</script>
<textarea id="xpdl_source" rows="50" cols="100" style="display: none">
</textarea>
<div id="activityInfo">
	<h1 class="none"><ikep4j:message pre='${preModeler}' key='popup.title.activity.area'/></h1>
	<h2><span id="activityArea" style="cursor:hand;">[Activity] <font color="#FF0000"> <ikep4j:message pre='${preModeler}' key='message.39'/></font></span></h2>
	
	<!--box_ty1 Start-->
	<div class="box_ty1">
	
		<!--tab Start-->	
	    <div id="tabs_div" class="iKEP_tab_work2">
	    	<div class="p_box_search">
	    		<select id="activityVariableSearch" title="<ikep4j:message pre='${preModeler}' key='popup.title.search'/>" name="variableType" class="select" onchange="activityVariableLayout(this.value);" style="display:none;">
					<option value="INPUT" selected="selected">INPUT</option>
					<option value="OUTPUT">OUTPUT</option>								
				</select>
				<a id="createActivityVariable" class="button_s" href="#a" style="display:none;"><span><ikep4j:message pre='${preModeler}' key='button.create'/></span></a>
				<a id="removeActivityVariable" class="button_s" href="#a" style="display:none;"><span><ikep4j:message pre='${preModeler}' key='button.delete'/></span></a>
				<a id="createActivityAdvanceInfo" class="button_s" href="#a" style="display:none;"><span><ikep4j:message pre='${preModeler}' key='button.advanceinfo'/></span></a>
				<a id="activityAddrControl" class="button_s" href="#a" style="display:none;"><span><ikep4j:message pre='${preModeler}' key='button.group'/></span></a>
				<select id="activitySearch" title="<ikep4j:message pre='${preModeler}' key='popup.title.search'/>" name="notificationType" class="select" onchange="createNotificationList('activityAddNotifyTbody', this.value);" style="display:none;">
					<option value="EMAIL" selected="selected"><ikep4j:message pre='${preModeler}' key='form.process.email'/></option>
					<option value="SMS"><ikep4j:message pre='${preModeler}' key='form.process.sms'/></option>								
				</select>		
				<a id="createActivityNotification" class="button_s" href="#a" style="display:none;"><span><ikep4j:message pre='${preModeler}' key='button.create'/></span></a>																			
				<a id="modifyActivityNotification" class="button_s" href="#a" style="display:none;"><span><ikep4j:message pre='${preModeler}' key='button.update'/></span></a>
				<a id="deleteActivityNotification" class="button_s" href="#a" style="display:none;"><span><ikep4j:message pre='${preModeler}' key='button.delete'/></span></a>
			</div>	
	        <ul>
	            <li><a href="#tab-1"><span><ikep4j:message pre='${preModeler}' key='tab.baseinfo'/></span></a></li>
	            <li><a href="#tab-2" style="display:none;"><span><ikep4j:message pre='${preModeler}' key='tab.variable'/></span></a></li>
				<li><a href="#tab-3" style="display:none;"><span><ikep4j:message pre='${preModeler}' key='tab.advanceinfo'/></span></a></li>
				<li><a href="#tab-4" style="display:none;"><span><ikep4j:message pre='${preModeler}' key='tab.participant'/></span></a></li>
				<li>
					<a href="#tab-5" style="display:none;"><span><ikep4j:message pre='${preModeler}' key='tab.notifycation'/></span></a>
					<img src='<c:url value="/base/images/workflow/line_tab1.gif"/>' width="1" height="19" alt="" />
				</li>
	        </ul>
			
			<!--tab-1 Start-->
	        <div id="tab-1"> 
	            <div id="a_DefaultInfo" class="blockDetail">
	                <table summary="<ikep4j:message pre='${preModeler}' key='popup.title.activity.defaultinfo'/>">
						<caption></caption>
	                    <tbody id="modifyActivityDefaultInfo">
	                    	<tr>
	                            <th class="textCenter first" width="20%"><ikep4j:message pre='${preModeler}' key='form.activity.attribute'/></th>
								<th class="textCenter last"><ikep4j:message pre='${preModeler}' key='form.activity.attribute.value'/></th>
	                        </tr>
	                        <tr>
	                        	<th class="textLeft first" width="20%"><ikep4j:message pre='${preModeler}' key='form.activity.id'/></th>
								<td class="textLeft last"><span id="activityId"></span></td>
	                        </tr>
							
							<tr>
	                        	<th class="textLeft first" width="20%"><ikep4j:message pre='${preModeler}' key='form.activity.name'/></th>
								<td class="textLeft last"><span id="activityName"></span></td>
	                        </tr>
							<tr>
	                        	<th class="textLeft first" width="20%"><ikep4j:message pre='${preModeler}' key='form.activity.priority'/></th>
								<td class="textLeft last"><span id="activityPriority"></span></td>
	                        </tr>
							<tr>
	                        	<th class="textLeft first" width="20%"><ikep4j:message pre='${preModeler}' key='form.activity.desc'/></th>
								<td class="textLeft last"><span id="activityDescription"></span></td>
	                        </tr>
	                    </tbody>
	                </table>
	            </div>
	        </div>
			<!--//tab-1 End-->
			
			<!--tab-2 Start-->
	        <div id="tab-2">
	            <div id="p_variables" class="blockDetail" style="overflow-y:auto;">
	                <style>
	                    #activityAddParticipantsTbody .ui-selecting, #activityParticipantsTbody .ui-selecting, #selectabledin .ui-selecting, #selectabledout .ui-selecting, #selectable .ui-selecting { background: #FECA40; }
	                    #activityAddParticipantsTbody .ui-selected, #activityParticipantsTbody .ui-selected, #selectabledin .ui-selected, #selectabledout .ui-selected, #selectable .ui-selected { background: #F39814; color: white; }
	                    #activityAddParticipantsTbody, #activityParticipantsTbody, #selectabledin, #selectabledout, #selectable { list-style-type: none; margin: 0; padding: 0; width: 100%; }
						#activityAddParticipantsTbody li, #activityParticipantsTbody li, #selectabledin li, #selectabledout li, #selectable li { margin: 0px; padding: 0px; font-size: 1em;}
	                </style>
					
					<!--selectabled-1 Start-->					
					<div id="selectabled-1">
						<table summary="">
							<caption></caption>
							<thead>
								<tr>
									<th scope="col" width="16.6%" class="textCenter first"><ikep4j:message pre='${preModeler}' key='form.process.variable.id'/></th>
									<th scope="col" width="16.6%" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.process.variable.name'/></th>
									<th scope="col" width="16.6%" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.process.variable.isarray'/></th>
									<th scope="col" width="16.6%" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.process.variable.type'/></th>
									<th scope="col" width="16.6%" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.process.variable.inout'/></th>
									<th scope="col" width="16.6%" class="textCenter last"><ikep4j:message pre='${preModeler}' key='form.process.variable.keyindex'/></th>
								</tr>
							</thead>
						</table>
						<!--Dynamic Variable List Start-->
			                <ol id="selectabledin"></ol>
						<!--//Dynamic Variable List End-->
					</div>
					<!--//selectabled-1 End-->	
					
					<!--selectabled-2 Start-->					
					<div id="selectabled-2" style="display:none;">
						<table summary="">
							<caption></caption>
							<thead>
								<tr>
									<th scope="col" width="16.6%" class="textCenter first"><ikep4j:message pre='${preModeler}' key='form.process.variable.id'/></th>
									<th scope="col" width="16.6%" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.process.variable.name'/></th>
									<th scope="col" width="16.6%" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.process.variable.isarray'/></th>
									<th scope="col" width="16.6%" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.process.variable.type'/></th>
									<th scope="col" width="16.6%" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.process.variable.inout'/></th>
									<th scope="col" width="16.6%" class="textCenter last"><ikep4j:message pre='${preModeler}' key='form.process.variable.keyindex'/></th>
								</tr>
							</thead>
						</table>
						<!--Dynamic Variable List Start-->
			                <ol id="selectabledout"></ol>
						<!--//Dynamic Variable List End-->
					</div>
					<!--//selectabled-2 End-->

	            </div>
	        </div>
			<!--//tab-2 기본정보 End-->
			
			<!--tab-3 Start-->
			<div id="tab-3" class="blockDetail">
				<div id="activityAdvanceInfo">
					<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.advanceinfo'/>">
						<caption></caption>
						<thead></thead>
						<tbody>
							<tr>
								<th class="textLeft first"><ikep4j:message pre='${preModeler}' key='form.activity.deadline'/></th>
								<td colspan="6" class="textLeft last"><input type="checkbox" class="checkbox" title="checkbox" id="isDeadline" name="isDeadline"/></td>
							</tr>
							<tr>
								<th scope="col" width="20%" class="textLeft first">
									<input type="radio" name="duration" value="durationDay"><label for="durationDay"><ikep4j:message pre='${preModeler}' key='form.activity.durationday'/></label>
								</th>
								<th scope="col" width="10%" class="textLeft first"><ikep4j:message pre='${preModeler}' key='form.activity.day'/></th>
								<td scope="col" width="20%" class="textLeft last"><input type="text" name="day" disabled="true"> </td>
								<th scope="col" width="10%" class="textLeft"><ikep4j:message pre='${preModeler}' key='form.activity.hour'/></th>
								<td scope="col" width="20%" class="textLeft last"><input type="text" name="hour" disabled="true"></td>
								<th scope="col" width="10%" class="textLeft"><ikep4j:message pre='${preModeler}' key='form.activity.minutes'/></th>
								<td scope="col" width="20%" class="textLeft last"><input type="text" name="minutes" disabled="true"></td>
							</tr>
							<tr>
								<th class="textLeft first">
									<input type="radio" name="duration" value="variable"><label for="variable"><ikep4j:message pre='${preModeler}' key='form.activity.variable'/></label>
								</th>
								<td colspan="6" class="textLeft last">
									<input type="text" name="durationVariable" disabled="true" value=""/>
									<a href="#" class="bold" id="createActivityAdvanceInfoVariable"><img src="<c:url value='/base/images/icon/ic_menu_microblog.png'/>" width="16" height="16" alt="" /></a>
								</td>
							</tr>
							<tr>
								<th class="textLeft first"><ikep4j:message pre='${preModeler}' key='form.activity.url'/></th>
								<td colspan="6" class="textLeft last"><input type="text" id="activityUrl" name="activityUrl" size="100"/></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>	
			<!--//tab-3 기본정보 End-->
			
			<!--tab-4 참여자 Start-->
			<div id="tab-4">
				<!--participantsInfo Start-->
				<div id="participantsInfo">
					<!--leftbox Start-->
					<div class="leftbox" style="overflow-y:auto;">
						<div class="blockDetail">
							<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.process.participant.list'/>">
								<caption></caption>
								<thead>
									<tr>
										<th width="25%" scope="col" class="textCenter first"><ikep4j:message pre='${preModeler}' key='form.participant.id'/></th>
										<th width="25%" scope="col" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.participant.type'/></th>
										<th width="25%" scope="col" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.participant.id'/></th>
										<th width="25%" scope="col" class="textCenter last"><ikep4j:message pre='${preModeler}' key='form.participant.name'/></th>
									</tr>
								</thead>
							</table>
							<ol id="activityParticipantsTbody">
								<li>
									<table summary="">
										<caption></caption>
										<tbody>
											<tr>
												<td class="textCenter" colspan="4"><ikep4j:message pre='${preModeler}' key='message.40'/></td>
											</tr>
										</tbody>
									</table>
								</li>
							</ol>
						</div>
					</div>
					<!--leftbox End-->
					
					<!--centerbox Start-->
					<div class="centerbox">
						<span>	
							<a id="activityAddParticipant" class="button_s" href="#a"><span><img src="<c:url value='/base/images/workflow/arrow2.png'/>" alt="" /></span></a>
							<div class="clear pt5"></div>
							<a id="activityRemoveParticipant" class="button_s" href="#a"><span><img src="<c:url value='/base/images/workflow/arrow1.png'/>" alt="" /></span></a>	
						</span>					
					</div>
					<!--centerbox End-->
					
					<!--rightbox Start-->
					<div class="rightbox" style="overflow-y:auto;">
						<div class="blockDetail">
							<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.activity.participant.list'/>">
								<caption></caption>
								<thead>
									<tr>
										<th width="25%" scope="col" class="textCenter first"><ikep4j:message pre='${preModeler}' key='form.participant.id'/></th>
										<th width="25%" scope="col" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.participant.type'/></th>
										<th width="25%" scope="col" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.participant.id'/></th>
										<th width="25%" scope="col" class="textCenter last"><ikep4j:message pre='${preModeler}' key='form.participant.name'/></th>
									</tr>
								</thead>
							</table>
							<ol id="activityAddParticipantsTbody">
								<li>
									<table summary="">
										<caption></caption>
										<tbody>
											<tr>
												<td class="textCenter" colspan="4"><ikep4j:message pre='${preModeler}' key='message.40'/></td>
											</tr>
										</tbody>
									</table>
								</li>
							</ol>
						</div>
					</div>
					<!--rightbox End-->
				</div>
				<!--//participantsInfo End-->
			</div>
			<!--//tab-4 참여자 End-->
			
			<!--tab-5 알림 Start-->
			<div id="tab-5" class="blockDetail">
				<table summary="">
					<caption></caption>
					<thead>
						<tr>
							<th scope="col" width="5%" class="textCenter first">&nbsp;</th>
							<th scope="col" width="20%" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.state.state'/></th>
							<th scope="col" width="45%" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.state.to'/></th>
							<th scope="col" width="16%" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.state.notifycation'/></th>
							<th scope="col" width="14%" class="textCenter last"><ikep4j:message pre='${preModeler}' key='form.state.message'/></th>
						</tr>
					</thead>
				</table>
				<ol id="activityAddNotifyTbody">
					<li>
						<table summary="">
							<caption></caption>
							<tbody>
								<tr>
									<td class="textCenter" colspan="4"><ikep4j:message pre='${preModeler}' key='message.41'/></td>
								</tr>
							</tbody>
						</table>
					</li>
				</ol>
			</div>
			<!--//tab-5 알림 End-->
				
	    </div>
		<!--//tab End-->
		
	</div>
	<!--//box_ty1 End-->
</div>