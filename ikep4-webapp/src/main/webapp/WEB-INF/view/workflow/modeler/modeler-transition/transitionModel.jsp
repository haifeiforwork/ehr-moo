<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" errorPage=""  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp" %>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preModeler"  value="message.workflow.modeler" />
<c:set var="preValidation"  value="validate.workflow.modeler" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script>
    (function($){
		var transitionId = "";
		var fromId = "";
		var toId = "";
		var transitionNodes = xmlUtil.selectNodes("//xpdl:Transition");
		for(var i=0; i < transitionNodes.length; i++){
			if( transitionNodes[i].getAttribute("Id") == '<c:out value="${param.id}"/>' ){
				transitionId = transitionNodes[i].getAttribute("Id");
				fromId = transitionNodes[i].getAttribute("From");
				toId = transitionNodes[i].getAttribute("to");
				$("#transitionDefaultInfo").find("#label").html("");
				if( transitionNodes[i].getAttribute("Name") == '' ){
					$("#transitionDefaultInfo").find("#label").append("<ikep4j:message pre='${preModeler}' key='message.42'/>");	
				}else{
					$("#transitionDefaultInfo").find("#label").append(transitionNodes[i].getAttribute("Name"));
				}
				
				var expression = transitionNodes[i].getElementsByTagName("Expression");
				if( expression.length > 0 ){
					$("#transitionDefaultInfo").find("input[name='conditionExpression']").attr("readonly", false);
					$("#transitionDefaultInfo").find("input[name='conditionExpression']").val(expression[0].firstChild.nodeValue);
				}
				
				var conditionType = transitionNodes[i].getElementsByTagName("Condition");
				if( conditionType.length > 0 ){
					$("#transitionDefaultInfo").find("select[name=conditionType] > option[value=" + conditionType[0].getAttribute("Type") + "]").attr("selected", true);
					if( conditionType[0].getAttribute("Type") == 'CONDITION' ){
						$("#conditionExpression").show();
					}else{
						$("#conditionExpression").hide();
					}
				}
			}
		}
		
		// 조건 유형.
		conditionTypeEventListner = function(obj){
			if( obj.value == 'CONDITION' ){
				$("#conditionExpression").show();
			}else{
				$("#conditionExpression").hide();
			}
		}
		
        $(function(){
            //=========================================================================
            // * 탭, 셀렉트 테이블 초기화. 
            //=========================================================================
            $tabs = $("#tabs_div").tabs();
			// ** 탭 디자인이 Jquery Tab과 충돌로 인해 class를 삭제해줘야 선이 맞음.
			$tabs.find("#tab-1").removeClass("ui-tabs-panel ui-widget-content ui-corner-bottom");
			
			// 프로세스 기본정보 돌아옴.
			$( "#transitionArea" ).click(function() {
				getProcessDefaultInfo();
			});
			
			//=========================================================================
			// * 버튼 이벤트. (연결선 수정, 빌더) 
			//=========================================================================
			$( ".button_s, .bold" ).click(function() {
				var url = "";
				var style = "";
				
				// 버튼 Id 설정.
				var attrId = $(this).attr("id");
				var $dialogId = $( "#dialog-" + attrId );
				switch ( attrId ){
					// 연결선 수정
					case "modifyTransitionVariable" :
						
						var transition = new Array();
						transition["id"] =  transitionId;
						transition["name"] = $("#transitionDefaultInfo").find("#label").text();
						transition["conditionExpression"] =  $("#transitionDefaultInfo").find("input[name='conditionExpression']").val();
						transition["from"] = fromId;
						transition["to"]   = toId;	
						transition["description"] =  "";
						
						// 컨디션 유형.
						if ($("#transitionDefaultInfo").find("select[name=conditionType] > option[value=CONDITION]").attr("selected") == true) {
							transition["conditionType"] =  "CONDITION";	
						}else if ($("#transitionDefaultInfo").find("select[name=conditionType] > option[value=OTHERWISE]").attr("selected") == true){
							transition["conditionType"] =  "OTHERWISE";
						}else{
							transition["conditionType"] =  "";
						}
						
						try {
							xpdl.setTransition(transition, 'replace');
							alert("<ikep4j:message pre='${preModeler}' key='message.43'/>");
						} 
						catch (e) {
							alert(e);
							result = false;
						}
						break;
						
					// 빌더
					case "expressionBuilder" :
						url = '<c:url value="expressionBuilder.do?target=conditionExpression&expressionEditorContent="/>' + encodeURIComponent($("#t_defaultInfo").find("input[name='conditionExpression']").val());
						style = { width: 520, height:280, modal:true, resizable: false };
						break;
				}
				$dialogId.load(url).error(function(event, request, settings) {alert("<ikep4j:message pre='${preValidation}' key='error'/>");});
				$dialogId.dialog(style);
				$dialogId.dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
				$dialogId.dialog("widget").children(":first").addClass("ui-widget-workflow-header");
			});
		});
    })(jQuery);
</script>
<!--processInfo Start-->
<div id="transitionInfo">
	<h1 class="none"><ikep4j:message pre='${preModeler}' key='popup.title.transition.area'/></h1>
	<h2><span id="transitionArea" style="cursor:hand;">[Transition] <font color="#FF0000"> <ikep4j:message pre='${preModeler}' key='message.39'/></font></span></h2>
	<!--box_ty1 Start-->
	<div class="box_ty1">
		
		<!--tab Start-->
	    <div id="tabs_div" class="iKEP_tab_work2">
	        <div class="p_box_search">
				<a id="modifyTransitionVariable" class="button_s" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.update'/></span></a>
			</div>	
			<ul>
	            <li><a href="#tab-1"><span><ikep4j:message pre='${preModeler}' key='tab.baseinfo'/></span></a></li>
	        </ul>
			
			<!--blockDetail Start-->					
			<div class="blockDetail">
				<!--tab-1 Start-->
		        <div id="tab-1">
		            <div id="t_defaultInfo">
		                <table summary="<ikep4j:message pre='${preModeler}' key='popup.title.transition.defaultinfo'/>">
							<caption></caption>
		                    <tbody id="transitionDefaultInfo">
		                        <tr>
		                            <th class="textLeft first" width="20%"><ikep4j:message pre='${preModeler}' key='form.transition.name'/></th>
									<td class="textLeft last"><span id="label"></span></td>
		                        </tr>
		                        <tr>
		                        	<th class="textLeft first"><ikep4j:message pre='${preModeler}' key='form.transition.condition.type'/></th>
									<td class="textLeft last">
										<select name="conditionType" id="conditionType" onchange="conditionTypeEventListner(this);">
											<option value=0><ikep4j:message pre='${preModeler}' key='option.none'/></option>
											<option value="CONDITION">CONDITION</option>
											<option value="OTHERWISE">OTHERWISE</option>
										</select>
									</td>
		                        </tr>
								<tr>
									<th class="textLeft first" width="20%"><ikep4j:message pre='${preModeler}' key='form.transition.condition.value'/></th>
									<td class="textLeft last">
										<span id="conditionExpression" style="display:none;"> 
											<input name="conditionExpression" type="text" class="inputbox w70" title="" value="" size="18" readonly="true"/>&nbsp;
											<a href="#" class="bold" id="expressionBuilder"><img src="<c:url value='/base/images/icon/ic_menu_microblog.png'/>" width="16" height="16" alt="" /></a>
										</span>
									</td>
								</tr>
		                    </tbody>
		                </table>
		            </div>
		        </div>
				<!--//tab-1 End-->
			</div>
			<!--//blockDetail End-->
	    </div>
		<!--//tab End-->
		
	</div>
	<!--//box_ty1 End-->
</div>
<!--//processInfo End-->