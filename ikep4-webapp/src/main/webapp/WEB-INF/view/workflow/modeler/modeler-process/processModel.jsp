<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" errorPage=""  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp" %>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preModeler"  value="message.workflow.modeler" />
<c:set var="preValidation"  value="validate.workflow.modeler" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script>
    
    (function($){
		
		//=========================================================================
        // ** XPDL 초기화 및 디자인 로드.
        //=========================================================================
		resizeSouthPaneScroll();
		
        $.post('<c:url value="selectModelXml.do"/>', {
            "processId" : gEntityProcessId
		  , "processVer": gEntityProcessVer
        }).success(function(result){
			// 전역 변수 Entity 초기화
			gEntityProcessId 	 	  = result.processModel.processId;
			gEntityProcessName   	  = result.processModel.processName;
			gEntityProcessVer    	  = result.processModel.processVer;
			gEntityProcessState  	  = result.processModel.state;
			gEntityProcessDescription = result.processModel.description;
			gEntityProcessSaveDate 	  = result.processModel.saveDate;
			gEntityProcessActive	  = result.processModel.processState;
			
			// XPDL 객체 등록.
			if ('<c:out value="${param.loadPrism}"/>' == 'Y') {
				if (result.processModel.modelScript == 'none') {
					createDefaultXPDLDom(gEntityProcessId, gEntityProcessName, gEntityProcessVer, gEntityProcessDescription);
				}
				else {
					createXPDLDom(xmlDOM(result.processModel.modelScript)[0]);
				}
			}
			
			// 기본정보 초기화.
            createProcessInfo();
			
			// 상태바 초기화
			$("#prismStatusProcessId").html(gEntityProcessId);
            $("#prismStatusState").html(gEntityProcessState);
            $("#prismStatusProcessVer").html(gEntityProcessVer);
            $("#prismStatusSaveDate").html(gEntityProcessSaveDate);
			// Icon 배치가 완료 되었을때 활성화.
			if (gEntityProcessActive == 'ACTIVE') {
				$(".t_box_con").find("#prismStatusProcessState").attr("src", "/ikep4-webapp/base/images/workflow/img_blue.gif");
				$("#prismStatusIsDeploy").html("<ikep4j:message pre='${preModeler}' key='form.state.deploy.complete'/>");
			}else{
				$(".t_box_con").find("#prismStatusProcessState").attr("src", "/ikep4-webapp/base/images/workflow/img_red.gif");
				$("#prismStatusIsDeploy").html("<ikep4j:message pre='${preModeler}' key='form.state.deploy.none'/>");
			}
			$(".t_box_con").show();
			
			// 디자인 Load.
			if ('<c:out value="${param.loadPrism}"/>' == 'Y') {
				var url = '<c:url value="prism.do?mode=design&processId="/>' + encodeURIComponent(gEntityProcessId) 
				+ "&version=" + encodeURIComponent(gEntityProcessVer) 
				+ "&processType=workflow&minimapView=false&saveView=false&refreshView=false" 
				+ "&partitionId=" + encodeURIComponent('<c:out value="${param.gEntityPartitionId}"/>') 
				+ "&processTitle=" + encodeURIComponent(gEntityProcessName);
				
				var iframeHeight = $(".p_box_con").height();
				var title = '<ikep4j:message pre="${preModeler}" key="layout.message.6"/>';
				$(".p_box_con").html('<iframe title="' + title + '" id="prism" name="prism" src="' + url + '" frameborder="0" height="' + iframeHeight + '" width="100%;" scrolling="no" ></iframe>');
			}
			
        }).error(function(){
            alert("<ikep4j:message pre='${preValidation}' key='error'/>");
        });
		
		//=========================================================================
        // * 프로세스 XPDL Refresh. Function.
        //=========================================================================
        createProcessInfo = function(){
            createProcessDefaultInfo();
            createProcessVariables();
			try {
				var participantId = xmlUtil.selectNodes("//lgcns:ParticipantExtendedAttribute/lgcns:Value");
				if (participantId.length > 0) {
					$("#processParticipantsTbody").html("");
					getProcessXpdlListParticipant();
				}
				else {
					var $sel = $("#AddrControlForm").find("[name=addrGroupList]");
					$sel.empty();
				}
			}catch(e){
				alert(e);
			}
        };
		
        $(function(){
            //=========================================================================
            // * 탭, 셀렉트 테이블 초기화. 
            //=========================================================================
            $tabs = $("#tabs_div").tabs();
			// ** 탭 디자인이 Jquery Tab과 충돌로 인해 class를 삭제해줘야 선이 맞음.
			$tabs.find("#tab-1").removeClass("ui-tabs-panel ui-widget-content ui-corner-bottom");
			$tabs.find("#tab-2").removeClass("ui-tabs-panel ui-widget-content ui-corner-bottom");
			$tabs.find("#tab-3").removeClass("ui-tabs-panel ui-widget-content ui-corner-bottom");
            $("#selectable").selectable();
			$("#processParticipantsTbody").selectable();
			
			$( "a[href=#tab-1]" ).click(function() {
				$("#createProcessVariable").hide();
				$("#modifyProcessVariable").hide();
				$("#removeProcessVariable").hide();
				$("#createVariableParticipant").hide();
				$("#deleteVariableParticipant").hide();
				$("#processAddrControl").hide();
				
				// 테이블 영역 높이 조절
				resizeSouthPaneScroll();
			});
			
			$( "a[href=#tab-2]" ).click(function() {
				$("#createProcessVariable").show();
				$("#modifyProcessVariable").show();
				$("#removeProcessVariable").show();
				$("#createVariableParticipant").hide();
				$("#deleteVariableParticipant").hide();
				$("#processAddrControl").hide();
				
				// 테이블 영역 높이 조절
				resizeSouthPaneScroll();
			});
			$( "a[href=#tab-3]" ).click(function() {
				$("#createProcessVariable").hide();
				$("#modifyProcessVariable").hide();
				$("#removeProcessVariable").hide();
				$("#createVariableParticipant").show();
				$("#deleteVariableParticipant").show();
				$("#processAddrControl").show();
				
				// 테이블 영역 높이 조절
				resizeSouthPaneScroll();
			});
			
			//=========================================================================
			// * 버튼 이벤트. (프로세스 변수 추가, 수정, 삭제) 
			//=========================================================================
			$( ".button_s" ).click(function() {
				var url = "";
				var style = "";
				var isDialog = "Y";
				
				// 버튼 Id 설정.
				var attrId = $(this).attr("id");
				var $dialogId = $( "#dialog-" + attrId );
				switch ( attrId ){
					// 프로세스 변수 추가
					case "createProcessVariable" :
						url = '<c:url value="processCreateVariable.do"/>';
						style = { width: 520, height:320, modal:true, resizable: false };
						break;
						
					// 프로세스 변수 수정
					case "modifyProcessVariable" :
						// 선택된 변수 개수
						if ($("#p_variables").find("li.ui-selected").length == 1 && $("#p_variables").find("input[name='Id']").length > 0) {
							// 선택된 변수 루프.
		                    $("#p_variables").find("li.ui-selected").each(function(){
		                        var processVariableId 	    = $(this).find("input[name='Id']").val();
		                        var processVariableName 	= $(this).find("input[name='Name']").val();
		                        var processVariableIsArray  = $(this).find("input[name='IsArray']").val();
		                        var processVariableType 	= $(this).find("input[name='Type']").val();
		                        var processVariableMode 	= $(this).find("input[name='InOut']").val();
		                        var processVariableKeyIndex = $(this).find("input[name='KeyIndex']").val();
								var processVariableUseIndex;
								
								// * UseIndex를 가져온다.
								try {
									var dataFields = xmlUtil.selectNodes("//xpdl:DataField");
									
									for(var i=0; i < dataFields.length; i++){
										if( dataFields[i].getAttribute("Id") == processVariableId ){
											processVariableUseIndex = dataFields[i].getAttribute("lgcns:UseIndex");
										}
									}
								}catch(e){
									alert(e);
								}
		                        
		                        url = '<c:url value="processModifyVariable.do"/>' 
									+ "?processVariableId="      	 + encodeURIComponent(processVariableId) 
									+ "&processVariableName="    	 + encodeURIComponent(processVariableName) 
									+ "&processVariableIsArray=" 	 + encodeURIComponent(processVariableIsArray) 
									+ "&processVariableType="    	 + encodeURIComponent(processVariableType) 
									+ "&processVariableMode="    	 + encodeURIComponent(processVariableMode)
									+ "&processVariableKeyIndex="    + encodeURIComponent(processVariableKeyIndex)
									+ "&processVariableUseIndex="    + encodeURIComponent(processVariableUseIndex);
									
								style = { width: 520, height:310, modal:true, resizable: false };
		                    });
		                }else if ($("#p_variables").find("li.ui-selected").length == 0 || $("#p_variables").find("input[name='Id']").length == 0 ) {
							alert("<ikep4j:message pre='${preModeler}' key='message.noneselect'/>");
		                    return false;
		                }else {
							alert("<ikep4j:message pre='${preModeler}' key='message.nonemulti'/>");
		                    return false;
		                }
						break;
					
					// 프로세스 변수 삭제
					case "removeProcessVariable" :
						if ($("#p_variables").find("li.ui-selected").length == 1 && $("#p_variables").find("input[name='Id']").length > 0) {
							applyProcessVariable('remove');
						}else if ($("#p_variables").find("li.ui-selected").length == 0 || $("#p_variables").find("input[name='Id']").length == 0 ) {
							alert("<ikep4j:message pre='${preModeler}' key='message.noneselect'/>");
		                    return false;
		                }else {
							alert("<ikep4j:message pre='${preModeler}' key='message.nonemulti'/>");
		                    return false;
		                }
						break;
					
					// 참여자 변수 팝업.	
					case "createVariableParticipant" :
						url = '<c:url value="processCreateVariableParticipant.do"/>';
						style = { width: 520, height:225, modal:true, resizable: false };
						break;
						
					// 참여자 변수 삭제.	
					case "deleteVariableParticipant" :
						var cnt = 0;
						isDialog == 'N';
						var participant = new Array();
						
						// 참여자중 user, group과 별개인 변수 일경우만 가져온다.
						$("#processParticipantsTbody").find("li.ui-selected").each(function(){
							if( $(this).find("input[name='participantType']").val() != 'SYSTEM' ){
								cnt = cnt + 1;
							};
						});
						
						// 변수만 삭제할 수 있다는 팝업을 띄운다. 
						if( cnt > 0 ){
							alert("<ikep4j:message pre='${preModeler}' key='message.1'/>");
							return false;
						}else{
							// 프로세스 참여자 설정 및 삭제
							try{
								$("#processParticipantsTbody").find("li.ui-selected").each(function(){
									participant["id"] = $(this).find("input[name='participantId']").val();
									xpdl.setProcessParticipant(participant, 'remove');
								});
								
								// 리스트를 전체 삭제.
								$("#processParticipantsTbody").html("");	
								
								// XPDL에서 변수 참여자가 있는지 확인.
								var participantId = xmlUtil.selectNodes("//lgcns:ParticipantExtendedAttribute/lgcns:Value");
								
								if($("#processParticipantsTbody").find("input[name='participantType']").length == 0 && participantId.length == 0){
									$("#processParticipantsTbody").append('<tr><td class="textCenter" colspan="4"><ikep4j:message pre="${preModeler}" key="message.2"/></td></tr>');
								}else{
									// XPDL 참여자 목록 가져오는 Function.
									getProcessXpdlListParticipant();	
								}
						
								alert("<ikep4j:message pre='${preModeler}' key='message.delete'/>");
							}catch(e){
								alert(e);
							}
						}
				}
				if (isDialog == 'Y') {
					$dialogId.load(url).error(function(event, request, settings){
						alert("<ikep4j:message pre='${preValidation}' key='error'/>");
					});
					$dialogId.dialog(style);
					$dialogId.dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
					$dialogId.dialog("widget").children(":first").addClass("ui-widget-workflow-header");
				}
			});
            
            //=========================================================================
            // * 프로세스 기본 수정  버튼 클릭 이벤트 및 팝업 
            //=========================================================================
            $("#modifyProcessDefaultInfo").click(function(){
				if( gEntityProcessDescription == null ){
					gEntityProcessDescription = "";
				}
				
				var $dialogId = $("#dialog-processModifyDefaultInfo");
                $dialogId.load('<c:url value="processModifyDefaultInfo.do"/>' 
				    + "?processId="   + encodeURIComponent(gEntityProcessId) 	 
					+ "&processName=" + encodeURIComponent(gEntityProcessName) 	 
					+ "&version="     + encodeURIComponent(gEntityProcessVer) 
					+ "&description=" + encodeURIComponent(gEntityProcessDescription)
				).error(function(event, request, settings){
                    alert("<ikep4j:message pre='${preValidation}' key='error'/>");
                });
				$dialogId.dialog({ width: 520, height:250, modal:true, resizable: false });
				$dialogId.dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
				$dialogId.dialog("widget").children(":first").addClass("ui-widget-workflow-header");
            });
			
			//=========================================================================
            // * 조직도 팝업. 
            //=========================================================================
			$("#processAddrControl").click(function() {
				var items = [];
				var $sel = $("#AddrControlForm").find("[name=addrGroupList]");
				$.each($sel.children(), function() {
					items.push($.data(this, "data"));
				});
	
				$controlType 	= $('input[name=controlType]:hidden').val() ;
				$controlTabType = $('input[name=controlTabType]:hidden').val() ;
				$selectType 	= $('input[name=selectType]:hidden').val() ;
				$selectMaxCnt 	= $('input[name=selectMaxCnt]:hidden').val() ;
				$searchSubFlag 	= $('input[name=searchSubFlag]:hidden').val() ;
				
				var dialog = new iKEP.Dialog({
					title: "<ikep4j:message pre='${preModeler}' key='popup.title.participant.create'/>",
					url: "<c:url value='/workflow/admin/participants.do'/>" 
						+ "?controlType=" + $controlType 
						+ "&controlTabType=" + $controlTabType 
						+ "&selectType=" + $selectType 
						+ "&selectMaxCnt=" + $selectMaxCnt 
						+ "&searchSubFlag=" + $searchSubFlag,
					modal: true,
					width: 700,
					height:500,
					resizable: false,
					params : {search:"keyword", items:items },
					open : function(event,ui){
						$(this).dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
						$(this).dialog("widget").children(":first").addClass("ui-widget-workflow-header");
				    },
				    scroll : "no",	   
				   	callback : function(result) {
						var cnt = 0;
						$sel.empty();
						$("#processParticipantsTbody").html("");	

						// 참여자 전체삭제 (type - p:group/user/role, v:variable)
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
							cnt = cnt + 1;
						});
						// 변수 참여자가 있는지 확인.
						var participantId = xmlUtil.selectNodes("//lgcns:ParticipantExtendedAttribute/lgcns:Value");
						
						if(cnt == 0 && participantId.length == 0){
							$("#processParticipantsTbody").append('<tr><td class="textCenter" colspan="4"><ikep4j:message pre="${preModeler}" key="message.2"/></td></tr>');
						}else{
							// XPDL 참여자 목록 가져오는 Function.
							getProcessXpdlListParticipant();	
						}
					}
				});
			});
        });
		
		//=========================================================================
        // * XPDL 참여자 목록 리스트 가져오는 Function.
        //=========================================================================
		getProcessXpdlListParticipant = function(){
			try {
				var participantId = xmlUtil.selectNodes("//lgcns:ParticipantExtendedAttribute/lgcns:Value");
				var participantName = xmlUtil.selectNodes("//xpdl:Participant/@Name");
				var participantNameStr = "";
				var participantJobTitle = "";
				var participantTeamName = "";
				var participantType = xmlUtil.selectNodes("//xpdl:ParticipantType/@Type");

				// 조직도 hidden addrGroupList 값 정보.
				var $sel = $("#AddrControlForm").find("[name=addrGroupList]");
				$sel.empty();
				
				var tpl = "";
				var type = "";
				for(var i=0; i < participantId.length; i++) {
					
					var id = participantId[i].firstChild.nodeValue;
					var name = participantName[i].firstChild.nodeValue;
					var type = participantType[i].firstChild.nodeValue;
					
					// 참여 변수는 조직도 공통 $sel에선 제외.
					if (participantId[i].firstChild.nodeValue.indexOf("$") == -1) {
						
						var useType = "";	
						
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
						id = participantId[i].firstChild.nodeValue.replace("$", "");
						name = participantName[i].firstChild.nodeValue.replace("$", "");
					}
					
					// 탭 하단 DIV 참여자 리스트 구성.
					createProcessListParticipant(id, type, id, name);
				}
			}catch(e){
				alert(e);
			}
		}
		
		//=========================================================================
        // * 참여자 하단 탭에 리스트를 뿌려주는 Function.
        //=========================================================================
		createProcessListParticipant = function(id, type, value, name){
			var li = document.createElement("li");
			var table = document.createElement("table");
			var tbody = document.createElement("tbody"); 
			var tr = document.createElement("tr");
			var prop = new Array("participantId", "participantType", "participantValue", "participantName");
			table.width = "100%";
			
			for(var n=0; n < prop.length; n++){
				var td = document.createElement("td");
				var input = document.createElement("input");
				if( n == 0 ){
					td.className = "textCenter first";
				}else if( n == 3 ){
					td.className = "textCenter last";
				}else{
					td.className = "textCenter";	
				}
				td.width = "25%";
				input.name = prop[n];
				input.type = "hidden";
				switch( prop[n] ){
					case "participantId"    :
						input.value = id;
						td.innerHTML = id;
						break;
					case "participantType"  :
						input.value = type;
						td.innerHTML = type;
						break;
					case "participantValue" :
					    input.value = id;
						td.innerHTML = id;
						break;
					case "participantName"  :
						input.value = name;
						if ( type == "user" ){
							td.innerHTML = name.split("/")[0];
						}else{
							td.innerHTML = name;	
						}
						break;
				}
				td.appendChild(input);
				tr.appendChild(td);
			}
			tbody.appendChild(tr);
			table.appendChild(tbody);
			li.appendChild(table);
			$("#processParticipantsTbody").append(li);
		}
        
        //=========================================================================
        // * 프로세스 기본 정보 리스트 Function.
        //=========================================================================
        createProcessDefaultInfo = function(){
            createProcessXpdlInitiate();
            $("#processId").text(gXpdlProcessId);
            $("#processName").text(gXpdlProcessName);
            $("#version").text(gXpdlProcessVer);
            $("#description").text(gXpdlProcessDescription);
        };
        
        //=========================================================================
        // * 프로세스 변수 정보 리스트 Function.
        //=========================================================================
        createProcessVariables = function(){
            // XPDL 프로세스 변수
			try {
				var dataFieldNodes = xmlUtil.selectNodes("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:DataFields/xpdl:DataField");
				var formalParameterNodes = xmlUtil.selectNodes("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:FormalParameters/xpdl:FormalParameter");
				var expression = xmlUtil.selectNodes("//lgcns:Expression");
			
				$("#p_variables").find("#selectable").html("");
				
	            if (dataFieldNodes.length > 0) {
	                var prop = new Array("Id", "Name", "IsArray", "Type", "InOut", "KeyIndex");
	                
	                // 전체 변수 목록
	                for (var i = 0; i < dataFieldNodes.length; i++) {
	                    var li = document.createElement("li");
	                    var table = document.createElement("table");
	                    var tbody = document.createElement("tbody");
	                    var tbodyRow = document.createElement("tr");
	                    table.border = 0;
	                    table.width = "100%";
	                    
	                    for (var n = 0; n < prop.length; n++) {
	                        var text = "";
	                        var name = prop[n];
							
							switch(prop[n]){
								case "Type"  :
									text = dataFieldNodes[i].getElementsByTagName("BasicType")[0].getAttribute("Type");
									break;
								case "InOut" :
									for (var p = 0; p < formalParameterNodes.length; p++) {
	                                    if (dataFieldNodes[i].getAttribute("Id") == formalParameterNodes[p].getAttribute("Id")) {
	                                        text = formalParameterNodes[p].getAttribute("Mode");
	                                    }
	                                }
									break;
								case "IsArray" :
									text = dataFieldNodes[i].getAttribute(prop[n]) != null ? dataFieldNodes[i].getAttribute(prop[n]) : "FALSE";
									break;
								case "KeyIndex" :
									for(var e=0; e < expression.length; e++){
										if( expression[e].firstChild.nodeValue == '$' + dataFieldNodes[i].getAttribute("Id")){
											text = expression[e].parentNode.getAttribute("Index");
										}	
									}
									break;	
								default 	   :
									text = dataFieldNodes[i].getAttribute(prop[n]);
									break;
							}
	                        tbodyRow = addTbodyRowTable(tbodyRow, name, text);
	                    }
	                    tbody.appendChild(tbodyRow);
	                    table.appendChild(tbody);
	                    li.appendChild(table);
						// DIV 프로세스 변수 설정
	                    $("#selectable").append(li);
	                }
	            }else{
					var li = document.createElement("li");
	                var table = document.createElement("table");
	                var tbody = document.createElement("tbody");
	                var tbodyRow = document.createElement("tr");
	                table.border = 0;
	                table.width = "100%";
					
					var name = "";
					var text = "<ikep4j:message pre='${preModeler}' key='message.15'/>";
					tbodyRow = addTbodyRowTable(tbodyRow, name, text);
					tbody.appendChild(tbodyRow);
	                table.appendChild(tbody);
	                li.appendChild(table);
					// DIV 프로세스 변수 설정
	                $("#selectable").append(li);
				}
			}catch(e){
				alert(e);
			}
        };
        
        //=========================================================================
        // * Dynamic Table TbodyRow. Function.
        //=========================================================================
        addTbodyRowTable = function(tbodyRow, name, text){
            var cell = document.createElement("td");
            var input = document.createElement("input");
			if( tbodyRow.getElementsByTagName("td").length == 0 ){
				cell.className = "textCenter first";
			}else if( tbodyRow.getElementsByTagName("td").length == 5 ){
				cell.className = "textCenter last";
			}else{
				cell.className = "textCenter";	
			}
			cell.width = "16.6%";
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
        // * 프로세스 기본정보 수정 이벤트 Function.
        //=========================================================================
        applyProcessDefaultInfo = function(action){
            var process = new Array();
			// DIV 내용 입력.
            process["id"] = $("#processDefaultInfo").find("input[name=processId]").val();
            process["name"] = $("#processDefaultInfo").find("input[name=processName]").val();
			document.getElementById("prism").contentWindow.sendToProcessTitle($("#processDefaultInfo").find("input[name=processName]").val());
            process["version"] = $("#processDefaultInfo").find("input[name=processVersion]").val();
            process["description"] = $("#processDefaultInfo").find("textarea[name=processDescription]").val();
            try {
				xpdl.setProcessDefaultInfo(process, action);
				alert("<ikep4j:message pre='${preModeler}' key='message.update'/>");
			}catch(e){
				alert(e);
			}
        }
        
        //=========================================================================
        // * 프로세스의 변수 추가 및 삭제 이벤트 Function.
        //=========================================================================
        applyProcessVariable = function(action){
            var $processVariable = "";
            var processVariable = new Array();
			
            switch(action){
				// 신규
				case "append"  :
					$processVariable = $("#processCreateVariable");
					break;
				// 수정
				case "replace" :
					$processVariable = $("#processModifyVariable");
					break;
				// 삭제
				case "remove"  :
					var processVariableIds = new Array();
	                var cnt = 0;
	                $("#p_variables").find("li.ui-selected").each(function(){
	                    processVariableIds[cnt] = $(this).find("input[name='Id']").val();
	                    cnt = cnt + 1;
	                });
					// XPDL 삭제 반영.
					try {
						xpdl.removeProcessVariables(processVariableIds);
					}catch(e){
						alert(e);
					}
	                createProcessInfo();

	                if (cnt >= 1) {
						alert("<ikep4j:message pre='${preModeler}' key='message.delete'/>");
	                }
	                else {
						alert("<ikep4j:message pre='${preModeler}' key='message.noneselect'/>");
	                }
					break;
		    }
			// 삭제가 아닐 경우(신규, 수정)
			if( action != "remove" ){
				processVariable["id"] 		= $processVariable.find("input[name=processVariableId]").val();
	            processVariable["name"] 	= $processVariable.find("input[name=processVariableName]").val();
	            processVariable["mode"] 	= $processVariable.find("select[name=processVariableMode]").val();
	            processVariable["type"] 	= $processVariable.find("select[name=processVariableType]").val();
	            processVariable["isArray"] 	= $processVariable.find("select[name=processVariableIsArray]").val();
				processVariable["isKey"] 	= $processVariable.find("input[name=isProcessKey]")[0].checked;
				processVariable["keyIndex"] = $processVariable.find("select[name=processKeyIndex]").val();
				processVariable["useIndex"] = $processVariable.find("select[name=processVariableUseIndex]").val();
				
	            // XPDL 반영.
				try {
					xpdl.setProcessVariable(processVariable, action);
					
					if(action == 'append'){
						alert("<ikep4j:message pre='${preModeler}' key='message.create'/>");	
					}else{
						alert("<ikep4j:message pre='${preModeler}' key='message.update'/>");
					}
				}catch(e){
					alert(e);
				}
				createProcessInfo();
			}
        }
        
        //=========================================================================
        // * 프로세스 참여자 설정 및 삭제 Function.
        //=========================================================================
        applyParticipants = function(action, id, type, value, name){
			var participant = new Array();
			participant["id"] 	 = id;
			participant["type"]  = type;
			participant["value"] = value;
			participant["name"]  = name;
			// XPDL 프로세스 참여자 설정 및 삭제
			try {
				xpdl.setProcessParticipant(participant, action);
			}catch(e){
				alert(e);
			}
        }
        
    })(jQuery);
</script>
<!--processInfo Start-->
<div id="processInfo">
	<h1 class="none"><ikep4j:message pre='${preModeler}' key='popup.title.process.area'/></h1>
	<h2>[Process]</h2>
	<!--box_ty1 Start-->
	<div class="box_ty1">
		
		<!--tab Start-->
	    <div id="tabs_div" class="iKEP_tab_work2">
	        <div class="p_box_search">
				<a id="createProcessVariable" class="button_s" href="#a" style="display:none;"><span><ikep4j:message pre='${preModeler}' key='button.create'/></span></a>
				<a id="modifyProcessVariable" class="button_s" href="#a" style="display:none;"><span><ikep4j:message pre='${preModeler}' key='button.update'/></span></a>
				<a id="removeProcessVariable" class="button_s" href="#a" style="display:none;"><span><ikep4j:message pre='${preModeler}' key='button.delete'/></span></a>
				<a id="createVariableParticipant" class="button_s" href="#a" style="display:none;"><span><ikep4j:message pre='${preModeler}' key='button.variable'/></span></a>
				<a id="deleteVariableParticipant" class="button_s" href="#a" style="display:none;"><span><ikep4j:message pre='${preModeler}' key='button.delete'/></span></a>
				<a id="processAddrControl" class="button_s" href="#a" style="display:none;">
					<span><ikep4j:message pre='${preModeler}' key='form.group'/> / <ikep4j:message pre='${preModeler}' key='form.participant'/></span>
				</a>																	
			</div>	
			<ul>
	            <li><a href="#tab-1"><span><ikep4j:message pre='${preModeler}' key='tab.baseinfo'/></span></a></li>
	            <li><a href="#tab-2"><span><ikep4j:message pre='${preModeler}' key='tab.variable'/></span></a></li>
	            <li>
            		<a href="#tab-3"><span><ikep4j:message pre='${preModeler}' key='tab.participant'/></span></a>
					<img src='<c:url value="/base/images/workflow/line_tab1.gif"/>' width="1" height="19" alt="" />
				</li>
	        </ul>
			
			<!--tab-1 Start-->
	        <div id="tab-1">
	            <div id="p_defaultInfo" class="blockDetail">
	                <table summary="<ikep4j:message pre='${preModeler}' key='popup.title.process.defaultinfo'/>">
						<caption></caption>
	                    <tbody id="modifyProcessDefaultInfo">
	                        <tr>
	                            <th class="textCenter first" width="20%"><ikep4j:message pre='${preModeler}' key='form.process.attribute'/></th>
								<th class="textCenter last"><ikep4j:message pre='${preModeler}' key='form.process.attribute.value'/></th>
	                        </tr>
							<tr>
	                            <th class="textLeft first" width="20%"><ikep4j:message pre='${preModeler}' key='form.process.id'/></th>
								<td class="textLeft last"><span id="processId"></span></td>
	                        </tr>
	                        <tr>
	                        	<th class="textLeft first"><ikep4j:message pre='${preModeler}' key='form.process.name'/></th>
								<td class="textLeft last"><span id="processName"></span></td>
	                        </tr>
	                        <tr>
	                        	<th class="textLeft first"><ikep4j:message pre='${preModeler}' key='form.process.ver'/></th>
								<td class="textLeft last"><span id="version"></span></td>
	                        </tr>
	                        <tr>
	                        	<th class="textLeft first"><ikep4j:message pre='${preModeler}' key='form.process.desc'/></th>
								<td class="textLeft last"><span id="description">&nbsp;</span></td>
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
	                    #processParticipantsTbody .ui-selecting, #selectable .ui-selecting { background: #FECA40; }
	                    #processParticipantsTbody .ui-selected, #selectable .ui-selected { background: #F39814; color: white; }
	                    #processParticipantsTbody, #selectable { list-style-type: none;margin: 0;padding: 0;width: 100%; }
	                    #processParticipantsTbody li, #selectable li { margin: 0px;padding: 0px;font-size: 1em; }
	                </style>
	                <table summary="<ikep4j:message pre='${preModeler}' key='popup.title.process.variable.list'/>">
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
	                <ol id="selectable">
	                	<li>
							<table summary="">
								<caption></caption>
								<tbody>
									<tr>
										<td class="textCenter" colspan="6"><ikep4j:message pre='${preModeler}' key='message.16'/></td>
									</tr>
								</tbody>
							</table>
						</li>
	                </ol>
	            </div>
	        </div>
			<!--//tab-2 End-->
			
			<!--tab-3 Start-->
	        <div id="tab-3">
				<div id="participantsInfo" class="blockDetail" style="overflow-y:auto;">
					<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.process.participant.list'/>">
	                	<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="25%" class="textCenter first"><ikep4j:message pre='${preModeler}' key='form.participant.id'/></th>
								<th scope="col" width="25%" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.participant.mode'/></th>
								<th scope="col" width="25%" class="textCenter"><ikep4j:message pre='${preModeler}' key='form.participant.id'/></th>
								<th scope="col" width="25%" class="textCenter last"><ikep4j:message pre='${preModeler}' key='form.participant.name'/></th>
							</tr>
						</thead>
					</table>
					<ol id="processParticipantsTbody">
						<li>
							<table summary="">
								<caption></caption>
								<tbody>
									<tr>
										<td class="textCenter" colspan="4"><ikep4j:message pre='${preModeler}' key='message.2'/></td>
									</tr>
								</tbody>
							</table>
						</li>
					</ol>
				</div>
	        </div>
			<!--//tab-3 End-->
				
	    </div>
		<!--//tab End-->
		
	</div>
	<!--//box_ty1 End-->
</div>
<!--//processInfo End-->