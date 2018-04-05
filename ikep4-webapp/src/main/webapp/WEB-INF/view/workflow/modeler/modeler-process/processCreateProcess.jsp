<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" errorPage=""  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preModeler"  value="message.workflow.modeler" />
<c:set var="preValidation"  value="validate.workflow.modeler" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script language="JavaScript">
	(function($) {
		// select 파티션 선택 시 value 값 설정.
		partitionIdEventListner = function(partitionId){
			$("#formPartitionId").val(partitionId.value);
		}
		
		$(document).ready(function () {
			
			var validOptions = {
				rules : {
					formProcessId   : { required:true },
					formProcessName : { required:true },
					formProcessVer  : { required:true }
				},
				messages : {
					formProcessId   : {
			            required  : "<ikep4j:message pre='${preValidation}' key='message.1'/>",
			        },
					formProcessName : {
			            required  : "<ikep4j:message pre='${preValidation}' key='message.2'/>",
			        },
					formProcessVer  : {
			            required  : "<ikep4j:message pre='${preValidation}' key='message.3'/>",
			        }
				},
				notice : {
			        formPartitionName : "<ikep4j:message pre='${preValidation}' key='message.4'/>",
					formProcessId     : "<ikep4j:message pre='${preValidation}' key='message.5'/>",
					formProcessName   : "<ikep4j:message pre='${preValidation}' key='message.6'/>",
					formProcessVer    : "<ikep4j:message pre='${preValidation}' key='message.7'/>"
			    },
				submitHandler : function(form){
					if( $("select[name=formPartitionName] > option[value=0]").attr("selected") == true ){
						alert("<ikep4j:message pre='${preModeler}' key='message.partition.none'/>");
						return false;
					}
					
					var partitionId = $( "#formPartitionId" ).val();
					var partitionName = $( "#formPartitionName" ).val();
					var processId = $( "#formProcessId" ).val();
					var processName = $( "#formProcessName" ).val();
					var processVer = $( "#formProcessVer" ).val();
					var processDesc = $( "#formProcessDesc" ).val();
					
					if( partitionId == '' ){
						alert("<ikep4j:message pre='${preModeler}' key='message.5'/>");
						$( "#processId" ).focus();
						return false;
					}else{
						$.ajax({
						    url : '<c:url value="createProcess.do" />',
						    data : {partitionId   : partitionId
						    	  , partitionName : partitionName
						    	  , processId     : processId
						    	  , processName   : processName
						    	  , processVer	  : processVer
						    	  , modelScript	  : 'none'
						    	  , viewScript	  : ''
						    	  , state		  : "NOT_SAVED"
								  , description	  : processDesc
						    	  , dummy:now.getSeconds()},
						    type : "POST",
						    success : function(result) {
								
								if( result == true ){
									alert("<ikep4j:message pre='${preModeler}' key='message.6'/>");
									return false;
								}else{
									$( "#dialog-createProcess" ).empty();
									$( "#dialog-createProcess" ).dialog( "close" );
									getCreateTree(partitionId);
									alert("<ikep4j:message pre='${preModeler}' key='message.7'/>");
									processIdClickListner(processId, processName, processVer, partitionId, partitionName, processDesc, "Y");
								}
						    },
							error: function() {
								$( "#dialog-createProcess" ).empty();
								$( "#dialog-createProcess" ).dialog( "close" );
								alert("<ikep4j:message pre='${preValidation}' key='error'/>");
							},
							complete : function() {}
						});
					}
				}
			};
			
			new iKEP.Validator("#processCreateForm", validOptions);
			
			$("select[name='formPartitionName']").focus();
		
			$( ".button, .bold" ).click(function() {
				// 버튼 이벤트 Id.
				var attrIds = $(this).attr("class").split(" ");
				var attrId = attrIds[attrIds.length - 1];
				
				switch ( attrId ){
					// 프로세스 추가 확인
					case "createProcessConfirm" :
						$("#processCreateForm").submit();
						break;	
						
					// 프로세스 추가 취소
					case "createProcessCancel" :
						$( "#dialog-createProcess" ).empty();
						$( "#dialog-createProcess" ).dialog( "close" );
						break;	
				}
			});
			
		});
	})(jQuery);
</script>
<!-- Modal window Start -->
<!--blockDetail Start-->
<form id="processCreateForm" name="processCreateForm" onsubmit="return false;">
	<div class="blockDetail_2">
		<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.process.create'/>">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="40%" class="textLeft"><label for="formPartitionName">Partition</label></th>
					<td width="60%">
						<span class="textCenter">
							<select name="formPartitionName" id="formPartitionName" onchange="partitionIdEventListner(this);" style="width:100%;">
								<option value=0><spring:message code="message.workflow.modeler.option.choice"/></option>
								<c:forEach var="partitionList" items="${processList.partitionList}">
									<option value=${partitionList.partitionId} <c:if test="${partitionList.partitionId eq param.partitionId}">selected="selected"</c:if>>${partitionList.partitionName}</option>
									<c:if test="${partitionList.partitionId eq param.partitionId}">
										<c:set var="setPartitionId" value="${partitionList.partitionId}"/>
									</c:if>
								</c:forEach>
							</select>
						<span>
					</td>
				</tr>
				<tr>
					<th colspan="2" scope="row" class="textLeft"><br/></th>
				</tr>
				<tr>
					<th scope="row" class="textLeft"><label for="formProcessId"><ikep4j:message pre='${preModeler}' key='form.process.id'/></label></th>
					<td>
						<span class="textCenter">
							<input type="text" name="formProcessId" id="formProcessId" value="" style="ime-mode:disabled; width:100%;" class="inputbox w95"/>
						</span>
					</td>
				</tr>
				<tr>
					<th colspan="2" scope="row" class="textLeft"><br/></th>
				</tr>
				<tr>
					<th scope="row" class="textLeft"><label for="formProcessName"><ikep4j:message pre='${preModeler}' key='form.process.name'/></label></th>
					<td>
						<span class="textCenter">
							<input type="text" name="formProcessName" id="formProcessName" value="" style="width:100%;" class="inputbox w95"/>
						</span>
					</td>
				</tr>
				<tr>
					<th colspan="2" scope="row" class="textLeft"><br/></th>
				</tr>
				<tr>
					<th scope="row" class="textLeft"><label for="formProcessVer"><ikep4j:message pre='${preModeler}' key='form.process.ver'/></label></th>
					<td>
						<span class="textCenter">
							<input type="text" name="formProcessVer" id="formProcessVer" value="1.0" style="width:100%;" class="inputbox w95"/>
						</span>
					</td>
				</tr>
				<tr>
					<th colspan="2" scope="row" class="textLeft"><br/></th>
				</tr>
				<tr>
					<th scope="row" class="textLeft"><label for="formProcessDesc"><ikep4j:message pre='${preModeler}' key='form.process.desc'/></label></th>
					<td>
						<span class="textCenter">
							<textarea name="formProcessDesc" id="formProcessDesc" value="" style="width: 322px; height: 43px;" class="inputbox w95"></textarea>
							<input type="hidden" name="formPartitionId" id="formPartitionId" value="${setPartitionId}" />
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
		<li><a class="button createProcessConfirm" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.create'/></span></a></li>
		<li><a class="button createProcessCancel" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.cancel'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
<!--// Modal window End -->