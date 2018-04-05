<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" errorPage=""  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preModeler"  value="message.workflow.modeler" />
<c:set var="preValidation"  value="validate.workflow.modeler" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script language="JavaScript">

	(function($){
		initiateExpressionBuilder = function() {
			//$("expressionVariables:reset");
			
			// clear expressionEditor
			$("expressionEditor:reset");
			
			// clear expressionValidation
			$("expressionValidation:reset");
		};

		//=========================================================================
        // * Expression 변수 정보 리스트 초기화 및  팝업 Function.
        //=========================================================================
        createExpressionVariables = function(){
			//var option = [];
            var dataFieldNodes = xmlUtil.selectNodes("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:DataFields/xpdl:DataField");
            
            if(dataFieldNodes.length > 0) {
            	for(var i=0; i < dataFieldNodes.length; i++) {
                	$("<li>"+dataFieldNodes[i].getAttribute("Id")+"</li>").appendTo("#expressionVariables"); 
            	}
            }
            
        };
        
        // 변수가 DataField에 포함되어 있는지 체크함.
        checkVariable = function(variable) {
        	var result = false;
        	var varCnt = xmlUtil.nodeCount("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:DataFields/xpdl:DataField[@Id='"+variable+"']");
        	
        	if(varCnt > 0) {
        		result = true;
        	}
        	
        	return result;
        };
        
        // javascript 엔진에서 체크하지 않는 수식 체크 (&, |, 변수값)
        checkExpression = function(expression) {
        	var errorMsg = "";
        	var notAllowedOperatorPattern = /===|!==|\&+|\|+/g;
			var variablePattern = /\\$[a-zA-Z0-9\_]*/g;
			
			var notAllowedOperations = expression.match(notAllowedOperatorPattern);
			var invalidOperations;
			
			var variables = expression.match(variablePattern);
			var invalidVariables;
			
			// 허용되지 않는 operation 체크 (javascript에서는 사용이 되나 여기서는 사용안함(&, |))
			if(notAllowedOperations != null) {
				invalidOperations = "";
				for(var i=0; i < notAllowedOperations.length; i++) {
					if(notAllowedOperations[i].indexOf('&') >= 0 || notAllowedOperations[i].indexOf('|') >= 0) {
						if(notAllowedOperations[i].length == 1) {
							invalidOperations += notAllowedOperations[i]+",";
						}
					}
				}
				if(invalidOperations != '') {
					errorMsg = "syntax error ('"+invalidOperations.substring(0, invalidOperations.length-1)+"')\n";
					return errorMsg;
				}
			}
			
			// 변수가 DataField 에 포함되어 있는 체크
			if(variables != null) {
				invalidVariables = "";
				for(var i=0; i < variables.length; i++) {
					if(!checkVariable(variables[i].replace("$", ""))) {
						invalidVariables += variables[i].replace("$", "")+",";
					}
				}
				if(invalidVariables!= '') {
					errorMsg += "invalid variable error ('"+invalidVariables.substring(0,invalidVariables.length-1)+"')";
					return errorMsg;
				}
			}
			
			return errorMsg;
        };
        
        // 변수 더블 클릭시 우측 Contentable 영역에 값을 전달함.
        $( "#expressionVariables" ).dblclick(function() {
        	insertVariable("$"+$("#expressionVariables > li.ui-selected").text());
        });
        
		$( ".button, .bold" ).click(function() {
			// 버튼 이벤트 Id.
			var attrIds = $(this).attr("class").split(" ");
			var attrId = attrIds[attrIds.length - 1];
			
			switch ( attrId ){
			    case "createExpressionVariableCheck" :
					var expression = $("#expressionEditor").text();
					
					if(expression == '') {
						$("#expressionValidation").html("expression is empty");
						return;
					}
					
					try {
						// 수식중 &, |, 변수값 에 대한 유효여부 체크
						var resultMsg = checkExpression(expression);
						
						if(resultMsg != '')
							throw Error(resultMsg);
						
						eval(expression.replace(/\\$[a-zA-Z0-9\_]*/g, "'temp_var'").replace(/and/g, "&&").replace(/or/g, "||"));
						
						$("#expressionValidation").html("expression validation success!");
					} catch (e) {
						$("#expressionValidation").html("expression validation fail!<br>fault : <font color='red'>"+e+"</font>");
						return;
					}
				    break;
				// Expression 빌더 확인 버튼
				case "createExpressionVariableConfirm" :
					
					var expression = $("#expressionEditor").text();
					
					if(expression == '') {
						$("#expressionValidation").html("expression is empty");
						return;
					}
					
					try {
						// 수식중 &, |, 변수값 에 대한 유효여부 체크
						var resultMsg = checkExpression(expression);
						
						if(resultMsg != '')
							throw Error(resultMsg);
						
						eval(expression.replace(/\\$[a-zA-Z0-9\_]*/g, "'temp_var'").replace(/and/g, "&&").replace(/or/g, "||"));
						
						try {
							$("input[name=${target}]").val(expression);
							$( "#dialog-expressionBuilder" ).empty();
							$( "#dialog-expressionBuilder" ).dialog( "close" );
						} catch (e) {
							$("#expressionValidation").html(e);
							return;
						}
					} catch (e) {
						$("#expressionValidation").html("expression validation fail!<br>fault : <font color='red'>"+e+"</font>");
						return;
					}
					$( "#dialog-expressionBuilder" ).empty();
					$( "#dialog-expressionBuilder" ).dialog( "close" );
					break;	
					
				// Expression 빌더 취소 버튼
				case "createExpressionVariableCancel" :
					$( "#dialog-expressionBuilder" ).empty();
					$( "#dialog-expressionBuilder" ).dialog( "close" );
					break;	
			}
		});
		
		// Contentable 영역에 값을 전달함.
		insertVariable = function(variable) {
			
			$( "#expressionEditor" ).focus();
			
			if($.browser.msie) {  // ie
				var rng = document.selection.createRange();
				rng.pasteHTML(variable);
			
			} else {
				document.execCommand('InsertHtml', false, variable);
			}
		};
		
		// 초기화
		$(document).ready(function() {
			$("#expressionVariables").selectable();
			
			// 변수 지움
			initiateExpressionBuilder();
			
			// 변수 나열
			createExpressionVariables();
		});
		
	})(jQuery);
	
	
</script>


<!-- Modal window Start -->
	<style>
        #expressionVariables .ui-selecting { background: #FECA40; }
        #expressionVariables .ui-selected { background: #F39814; color: white; }
        #expressionVariables { list-style-type: none;margin: 0;padding: 0;width: 100%; }
        #expressionVariables li { margin: 0px;padding: 0px;font-size: 1em; }
    </style>
	<div class="box1" style="height:100px;overflow: auto">
       <!-- select id="expressionVariables" name="expressionVariables" style="width:100%;height:100%;border:0;" multiple title="표현식빌더" /-->
       <ol id="expressionVariables"></ol>
	</div>
	
	<div class="box2" id="expressionEditor" contentEditable="true" style="width:315px;height:100px;overflow-y:auto"><c:out value="${param.expressionEditorContent}"/></div>
				
	<div class="box3" id="expressionValidation" style="width:480px;height:60px"/>
	
	<div class="clear"></div>
	<div class="blockBlank_5px"></div>
	<!--blockButton Start-->
	<div class="blockButton" style="width:480px;"> 
		<ul>
		<li><a class="button createExpressionVariableCheck" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.check'/></span></a></li>
		<li><a class="button createExpressionVariableConfirm" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.confirm'/></span></a></li>
		<li><a class="button createExpressionVariableCancel" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.cancel'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->	

<!-- //Modal window End -->

