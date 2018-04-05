<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" errorPage=""  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preModeler"  value="message.workflow.modeler" />
<c:set var="preValidation"  value="validate.workflow.modeler" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script>
	(function($) {
		
		// IsArray 선택 시 UseIndex를 사용할지 안할지 선택.
		createIsArrayValueChange = function(obj){
			if( obj.value == 'TRUE' ){
				$("select[name='processVariableUseIndex']").show();
			}else{
				$("select[name='processVariableUseIndex']").hide();
			}
		}
		
		$(document).ready(function () {
			
			var validOptions = {
				rules : {
					processVariableId   : { required : true },
					processVariableName : { required : true }
				},
				messages : {
					processVariableId   : {
			            required  : "<ikep4j:message pre='${preValidation}' key='message.11'/>"
			        },
					processVariableName : {
						required  : "<ikep4j:message pre='${preValidation}' key='message.12'/>"
					}
				},
				notice : {
			        processVariableId   : "<ikep4j:message pre='${preValidation}' key='message.13'/>"
			    },
				submitHandler : function(form){
					applyProcessVariable('append');
					$( "#dialog-createProcessVariable" ).empty();
	                $( "#dialog-createProcessVariable" ).dialog( "close" );
				}
			};
			
			new iKEP.Validator("#processCreateVariableForm", validOptions);
			
			$("input[name='processVariableId']").focus();
			
			$( ".button, .bold" ).click(function() {
				// 버튼 이벤트 Id.
				var attrIds = $(this).attr("class").split(" ");
				var attrId = attrIds[attrIds.length - 1];
				
				switch ( attrId ){
					// 변수 추가 확인
					case "createProcessVariableConfirm" :
						$("#processCreateVariableForm").submit();
						break;	
						
					// 변수 추가 취소
					case "createProcessVariableCancel" :
						$( "#dialog-createProcessVariable" ).empty();
						$( "#dialog-createProcessVariable" ).dialog( "close" );
						break;	
				}
			});
			
			// Key 체크를 할경우 Index를 선택.
			$("#processCreateVariable").find("input[name=isProcessKey]").click(function(){
				if ($(this).is(":checked")) {
					$("#processCreateVariable").find("select[name=processKeyIndex]").attr("disabled", false);
				}else{
					$("#processCreateVariable").find("select[name=processKeyIndex]").attr("disabled", true);
				}
			});
		});
	})(jQuery);
</script>

<!-- Modal window Start -->
<!--blockDetail Start-->
<form id="processCreateVariableForm" name="processCreateVariableForm" onsubmit="return false;">
<div class="blockDetail_2" id="processCreateVariable">
	<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.process.variable.create'/>">
		<caption></caption>
		<tbody>
			<tr>
				<th scope="row" width="40%" class="textLeft"><label for="processVariableId">Id</label></th>
				<td width="60%" colspan="2">
					<span class="textCenter">
						<input type="text" name="processVariableId" id="processVariableId" value="" style="ime-mode:disabled; width:100%;" class="inputbox w95"/>
					</span>
				</td>
			</tr>
			<tr>
				<th scope="row" class="textLeft" colspan="2">&nbsp;</th>
			</tr>
			<tr>
				<th scope="row" class="textLeft"><label for="processVariableName">Name</label></th>
				<td colspan="2">
					<span class="textCenter">
						<input type="text" name="processVariableName" id="processVariableName" value="" style="width:100%;" class="inputbox w95"/>
					</span>
				</td>
			</tr>
			<tr>
				<th scope="row" class="textLeft"><label for="processVariableType">Type</label></th>
				<td colspan="2">
					<span class="textCenter">
						<select id="processVariableType" name="processVariableType" style="width:100%;">
							<option value="STRING">STRING</option>			
							<option value="DATE">DATE</option>
							<option value="INT">INT</option>
						</select>
					</span>
				</td>
			</tr>
			<tr>
				<th scope="row" class="textLeft"><label for="processVariableIsArray">IsArray</label></th>
				<td colspan="2">
					<span class="textCenter">
						<select id="processVariableIsArray" name="processVariableIsArray" onchange="createIsArrayValueChange(this);" style="width:100%;">
							<option value="FALSE">FALSE</option>			
							<option value="TRUE">TRUE</option>
						</select>
					</span>
				</td>
			</tr>
			<tr>
				<th scope="row" class="textLeft"><label for="processVariableUseIndex">UseIndex</label></th>
				<td colspan="2">
					<span class="textCenter">
						<select id="processVariableUseIndex" name="processVariableUseIndex" style="display:none;width:100%;">
							<option value="FALSE">FALSE</option>			
							<option value="TRUE">TRUE</option>
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
							<option value="IN">IN</option>
							<option value="OUT">OUT</option>
							<option value="INOUT">INOUT</option>
						</select>
					</span>
				</td>
			</tr>
			<tr>
				<th scope="row" class="textLeft"><label for="processVariableMode">Key</label></th>
				<td><span class="textCenter"><input type="checkbox" name="isProcessKey"></span></td>
				<td class="textRight">
					<span class="textRight">
						<select id="processKeyIndex" name="processKeyIndex" disabled="true">
							<option value="1">1</option>
						    <option value="2">2</option>
						    <option value="3">3</option>
						    <option value="4">4</option>
						    <option value="5">5</option>
						    <option value="6">6</option>
						    <option value="7">7</option>
						    <option value="8">8</option>
						    <option value="9">9</option>
						    <option value="10">10</option>
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
		<li><a class="button createProcessVariableConfirm" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.create'/></span></a></li>
		<li><a class="button createProcessVariableCancel" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.cancel'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
<!--// Modal window End -->