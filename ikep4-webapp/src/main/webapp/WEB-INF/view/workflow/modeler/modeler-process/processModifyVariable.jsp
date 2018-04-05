<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" errorPage=""  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preModeler"  value="message.workflow.modeler" />
<c:set var="preValidation"  value="validate.workflow.modeler" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script>
	(function($) {
		modifyIsArrayValueChange = function(obj){
			if( obj.value == 'TRUE' ){
				$("select[name='processVariableUseIndex']").show();
			}else{
				$("select[name='processVariableUseIndex']").hide();
			}
		}
		
		$(document).ready(function () {
			
			var validOptions = {
				rules : {
					processVariableName   : { required : true }
				},
				messages : {
					processVariableName   : {
			            required  : "<ikep4j:message pre='${preValidation}' key='message.11'/>"
			        }
				},
				notice : {
			        processVariableName   : "<ikep4j:message pre='${preValidation}' key='message.12'/>"
			    },
				submitHandler : function(form){
					applyProcessVariable('replace');
					$( "#dialog-modifyProcessVariable" ).empty();
		            $( "#dialog-modifyProcessVariable" ).dialog( "close" );
				}
			};
			
			new iKEP.Validator("#processModifyVariableForm", validOptions);
			
			$("input[name='processVariableName']").focus();
			
			if('<c:out value="${param.processVariableIsArray}"/>' == 'FALSE'){
				$("select[name='processVariableUseIndex']").hide();
			}
			
			$( ".button, .bold" ).click(function() {
				// 버튼 이벤트 Id.
				var attrIds = $(this).attr("class").split(" ");
				var attrId = attrIds[attrIds.length - 1];
				
				switch ( attrId ){
					// 변수 수정 확인
					case "modifyProcessVariableConfirm" :
						$("#processModifyVariableForm").submit();
						break;	
						
					// 변수 수정 취소
					case "modifyProcessVariableCancel" :
						$( "#dialog-modifyProcessVariable" ).empty();
						$( "#dialog-modifyProcessVariable" ).dialog( "close" );
						break;	
				}
			});
			
			// Key 체크를 할경우 Index를 선택.
			$("#processModifyVariable").find("input[name=isProcessKey]").click(function(){
				if ($(this).is(":checked")) {
					$("#processModifyVariable").find("select[name=processKeyIndex]").attr("disabled", false);
				}else{
					$("#processModifyVariable").find("select[name=processKeyIndex]").attr("disabled", true);
				}
			});
		});
	})(jQuery);
</script>

<!-- Modal window Start -->
<!--blockDetail Start-->
<form id="processModifyVariableForm" name="processModifyVariableForm" onsubmit="return false;">
<div class="blockDetail_2" id="processModifyVariable">
	<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.process.variable.update'/>">
		<caption></caption>
		<tbody>
			<tr>
				<th scope="row" width="30%" class="textLeft"><label for="processVariableId">Id</label></th>
				<td width="60%" colspan="2">
					<span class="textCenter">
						<c:out value="${param.processVariableId}"/>
						<input type="hidden" name="processVariableId" id="processVariableId" value="${param.processVariableId}"/>
					</span>
				</td>
			</tr>
			<tr>
				<th scope="row" width="30%" class="textLeft" colspan="2"></th>
			</tr>
			<tr>
				<th scope="row" class="textLeft"><label for="processVariableName">Name</label></th>
				<td colspan="2">
					<span class="textCenter">
						<input type="text" name="processVariableName" id="processVariableName" value="${param.processVariableName}" style="width:100%;" class="inputbox w95"/>
					</span>
				</td>
			</tr>
			<tr>
				<th scope="row" class="textLeft"><label for="processVariableType">Type</label></th>
				<td colspan="2">
					<span class="textCenter">
						<select id="processVariableType" name="processVariableType" style="width:100%;">
							<option value="STRING" <c:if test="${'STRING' eq param.processVariableType}">selected="selected"</c:if>>STRING</option>			
							<option value="DATE" <c:if test="${'DATE' eq param.processVariableType}">selected="selected"</c:if>>DATE</option>
							<option value="INT" <c:if test="${'INT' eq param.processVariableType}">selected="selected"</c:if>>INT</option>
						</select>
					</span>
				</td>
			</tr>
			<tr>
				<th scope="row" class="textLeft"><label for="processVariableIsArray">IsArray</label></th>
				<td colspan="2">
					<span class="textCenter">
						<select id="processVariableIsArray" name="processVariableIsArray" onchange="modifyIsArrayValueChange(this);" style="width:100%;">
							<option value="FALSE" <c:if test="${'FALSE' eq param.processVariableIsArray}">selected="selected"</c:if>>FALSE</option>			
							<option value="TRUE" <c:if test="${'TRUE' eq param.processVariableIsArray}">selected="selected"</c:if>>TRUE</option>
						</select>
					</span>
				</td>
			</tr>
			<tr>
				<th scope="row" class="textLeft"><label for="processVariableUseIndex">UseIndex</label></th>
				<td colspan="2">
					<span class="textCenter">
						<select id="processVariableUseIndex" name="processVariableUseIndex" style="width:100%;">
							<option value="FALSE" <c:if test="${'FALSE' eq param.processVariableUseIndex}">selected="selected"</c:if>>FALSE</option>			
							<option value="TRUE" <c:if test="${'TRUE' eq param.processVariableUseIndex}">selected="selected"</c:if>>TRUE</option>
						</select>
					</span>
				</td>
			</tr>
			<tr>
				<th scope="row" class="textLeft"><label for="processVariableMode">Mode</label></th>
				<td colspan="2">
					<span class="textCenter">
						<select id="processVariableMode" name="processVariableMode" style="width:100%;">
							<option value=""><ikep4j:message pre='${preModeler}' key='option.none'/></option>			
							<option value="IN" <c:if test="${'IN' eq param.processVariableMode}">selected="selected"</c:if>>IN</option>
							<option value="OUT" <c:if test="${'OUT' eq param.processVariableMode}">selected="selected"</c:if>>OUT</option>
							<option value="INOUT" <c:if test="${'INOUT' eq param.processVariableMode}">selected="selected"</c:if>>INOUT</option>
						</select>
					</span>
				</td>
			</tr>
			<tr>
				<th scope="row" class="textLeft"><label for="processVariableMode">Key</label></th>
				<td><span class="textCenter"><input type="checkbox" name="isProcessKey" <c:if test="${'' != param.processVariableKeyIndex}">checked="checked"</c:if>></span></td>
				<td class="textRight">
					<span class="textRight">
						<select id="processKeyIndex" name="processKeyIndex" <c:if test="${'' eq param.processVariableKeyIndex}">disabled="true"</c:if>>
							<option value="1" <c:if test="${'1' eq param.processVariableKeyIndex}">selected="selected"</c:if>>1</option>
						    <option value="2" <c:if test="${'2' eq param.processVariableKeyIndex}">selected="selected"</c:if>>2</option>
						    <option value="3" <c:if test="${'3' eq param.processVariableKeyIndex}">selected="selected"</c:if>>3</option>
						    <option value="4" <c:if test="${'4' eq param.processVariableKeyIndex}">selected="selected"</c:if>>4</option>
						    <option value="5" <c:if test="${'5' eq param.processVariableKeyIndex}">selected="selected"</c:if>>5</option>
						    <option value="6" <c:if test="${'6' eq param.processVariableKeyIndex}">selected="selected"</c:if>>6</option>
						    <option value="7" <c:if test="${'7' eq param.processVariableKeyIndex}">selected="selected"</c:if>>7</option>
						    <option value="8" <c:if test="${'8' eq param.processVariableKeyIndex}">selected="selected"</c:if>>8</option>
						    <option value="9" <c:if test="${'9' eq param.processVariableKeyIndex}">selected="selected"</c:if>>9</option>
						    <option value="10" <c:if test="${'10' eq param.processVariableKeyIndex}">selected="selected"</c:if>>10</option>
						</select>
					</span>
				</td>
			</tr>
		</tbody>
	</table>
</div>
</form>
<!--//blockDetail End-->
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a class="button modifyProcessVariableConfirm" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.update'/></span></a></li>
		<li><a class="button modifyProcessVariableCancel" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.cancel'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
<!-- //Modal window End -->