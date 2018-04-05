<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" errorPage=""  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preModeler"  value="message.workflow.modeler" />
<c:set var="preValidation"  value="validate.workflow.modeler" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script language="JavaScript">
	(function($){
		
		$(function(){
			//=========================================================================
            // * Jquery SelecTable 초기화.
            //=========================================================================
            $("#variableAdvanceTbody").selectable();
		});
		
		//=========================================================================
        // * Dynamic Table TbodyRow. Function.
        //=========================================================================
        addTbodyRowTable = function(tbodyRow, name, text){
            var cell = document.createElement("td");
            var input = document.createElement("input");
            if( tbodyRow.getElementsByTagName("td").length == 0 ){
				cell.className = "textCenter first";
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
		
		// 프로세스 참여자를 가져와서 리스트를 구성하여 뿌려준다.
		try {
			var dataFieldNodes = xmlUtil.selectNodes("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:DataFields/xpdl:DataField");
			var formalParameterNodes = xmlUtil.selectNodes("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:FormalParameters/xpdl:FormalParameter");
			var expression = xmlUtil.selectNodes("//lgcns:Name");
			
			if (dataFieldNodes.length > 0) {
				$("#variableAdvanceTbody").html("");
				var prop = new Array("Id", "Name", "IsArray", "Type", "InOut", "KeyIndex");
				var appendData = 0;
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
						tbodyRow = addTbodyRowTable(tbodyRow, name, text);
					}
					tbody.appendChild(tbodyRow);
					table.appendChild(tbody);
					li.appendChild(table);
					
					// 이미 선택된 변수는 selected로 만든다.
					if( dataFieldNodes[i].getAttribute("Id") == $("#activityAdvanceInfo").find("input[name='durationVariable']").val() ){
						li.className = "ui-selected";
					}
					
					$("#variableAdvanceTbody").append(li);
				}

			}else{
				$("#variableAdvanceTbody").html("");
				$("#variableAdvanceTbody").append('<li><table summary="리스트"><caption></caption><tbody><tr><td class="textCenter" colspan="6"><ikep4j:message pre="${preModeler}" key="message.8"/></td></tr></tbody></table></li>');
			}
		}catch(e){
			alert(e);
		}
		
		$( ".button, .bold" ).click(function() {
			// 버튼 이벤트 Id.
			var attrIds = $(this).attr("class").split(" ");
			var attrId = attrIds[attrIds.length - 1];
			
			switch ( attrId ){
				// 고급정보 프로세스 변수 선택 확인
				case "createVariableAdvanceConfirm" :
					var activityVariableIds = new Array();
					var cnt = 0;
					if( $("#variableAdvanceTbody > li.ui-selected").length > 1){
						alert("<ikep4j:message pre='${preModeler}' key='message.28'/>");
						return false;	
					}else{
						$("#variableAdvanceTbody > li.ui-selected").each(function(){
							if( $(this).find("input[name='Id']").length == 0 ){
								alert("<ikep4j:message pre='${preModeler}' key='message.29'/>");
							}else{
								$("#activityAdvanceInfo").find("input[name='durationVariable']").val($(this).find("input[name='Id']").val());
								cnt = cnt + 1;
							}
			            });
						if( cnt > 0 ){
							$( "#dialog-createActivityAdvanceInfoVariable" ).empty();
							$( "#dialog-createActivityAdvanceInfoVariable" ).dialog( "close" );	
						}
					};
					break;	
					
				// 고급정보 프로세스 변수 선택 취소
				case "createVariableAdvanceCancel" :
					$( "#dialog-createActivityAdvanceInfoVariable" ).empty();
					$( "#dialog-createActivityAdvanceInfoVariable" ).dialog( "close" );
					break;	
			}
		});
	})(jQuery);
</script>
<style>
    #variableAdvanceTbody .ui-selecting { background: #FECA40; }
    #variableAdvanceTbody .ui-selected { background: #F39814; color: white; }
    #variableAdvanceTbody { list-style-type: none;margin: 0;padding: 0;width: 100%; }
    #variableAdvanceTbody li { margin: 0px;padding: 0px;font-size: 1em; }
</style>
<!-- Modal window Start -->
<!--blockDetail Start-->
<div class="blockDetail_2" id="processCreateVariableAdvance">
	<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.process.advanceinfo.variable.select'/>">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="16.6%" class="textCenter first">Id</th>
				<th scope="col" width="16.6%" class="textCenter">Name</th>
				<th scope="col" width="16.6%" class="textCenter">IsArray</th>
				<th scope="col" width="16.6%" class="textCenter">Type</th>
				<th scope="col" width="16.6%" class="textCenter last">InOut</th>
				<th scope="col" width="16.6%" class="textCenter last">KeyIndex</th>
			</tr>
		</thead>
	</table>
	<ol id="variableAdvanceTbody">
		<li>
			<table summary="">
				<caption></caption>
				<tbody>
					<tr>
						<td scope="row" class="textCenter" colspan="6"><ikep4j:message pre='${preModeler}' key='message.8'/></td>
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
		<li><a class="button createVariableAdvanceConfirm" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.select'/></span></a></li>
		<li><a class="button createVariableAdvanceCancel" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.cancel'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
<!--// Modal window End -->