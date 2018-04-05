<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" errorPage=""  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preModeler"  value="message.workflow.modeler" />
<c:set var="preValidation"  value="validate.workflow.modeler" />

<%-- 메시지 관련 Prefix 선언 End --%>
<script>
	(function($) {
		
		$(document).ready(function () {
			
			var validOptions = {
				rules : {
					partitionId   : { required:true },
					partitionName : { required:true },
					partitionDesc : { required:true }
				},
				messages : {
					partitionId : {
			            required  : "<ikep4j:message pre='${preValidation}' key='message.23'/>",
			        },
					partitionName : {
			            required  : "<ikep4j:message pre='${preValidation}' key='message.24'/>",
			        },
					partitionDesc : {
			            required  : "<ikep4j:message pre='${preValidation}' key='message.25'/>",
			        }
				},
				notice : {
			        partitionId   : "<ikep4j:message pre='${preValidation}' key='message.26'/>",
					partitionName : "<ikep4j:message pre='${preValidation}' key='message.27'/>"
			    },
				submitHandler : function(form){
					var partitionId   = $( "#partitionId" ).val();
					var partitionName = $( "#partitionName" ).val();
					var partitionDesc = $( "#partitionDesc" ).val();
					
					$.ajax({
					    url : '<c:url value="createPartition.do" />',
					    data : {partitionId:partitionId, partitionName:partitionName, description:partitionDesc, dummy:now.getSeconds()},
					    type : "POST",
					    success : function(result) {
							
							if( result == true ){
								alert("<ikep4j:message pre='${preModeler}' key='message.6'/>");
								$( "#partitionId" ).focus();
								return false;
							}else{
								getCreateTree(partitionId);
							
								// form 내용 삭제.
								$( "#dialog-createPartition" ).empty();
						    	$( "#dialog-createPartition" ).dialog( "close" );
								alert(partitionName + " <ikep4j:message pre='${preModeler}' key='message.create'/>");
							}
					    },
						error: function(error) {
							$( "#dialog-createPartition" ).empty();
					    	$( "#dialog-createPartition" ).dialog( "close" );
							alert("<ikep4j:message pre='${preValidation}' key='error'/>");
						}
					});
				}
			};
			
			new iKEP.Validator("#partitionCreateForm", validOptions);
			
			$("input[name='partitionId']").focus();

			$( ".button, .bold" ).click(function() {
				
				// 버튼 이벤트 Id.
				var attrIds = $(this).attr("class").split(" ");
				var attrId = attrIds[attrIds.length - 1];
				
				switch ( attrId ){
					// 파티션 추가 확인
					case "createPartitionConfirm" :
						$("#partitionCreateForm").submit();
						break;	
						
					// 파티션 추가 취소
					case "createPartitionCancel" :
						$( "#dialog-createPartition" ).empty();
						$( "#dialog-createPartition" ).dialog( "close" );
						break;	
				}
			});
		});
		
	})(jQuery);
</script>

<!-- Modal window Start -->
<!--blockDetail Start-->
<form id="partitionCreateForm" name="partitionCreateForm" onsubmit="return false;">
	<div class="blockDetail_2">
		<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.partition.create'/>">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="40%" class="textLeft"><label for="partitionId"><ikep4j:message pre='${preModeler}' key='form.partition.id'/></label></th>
					<td width="60%">
						<span class="textCenter">
							<input type="text" name="partitionId" id="partitionId" style="ime-mode:disabled; width:100%;" class="inputbox w95" title="<ikep4j:message pre='${preModeler}' key='form.partition.id'/>"/>
						</span>
					</td>
				</tr>
				<tr>
					<th colspan="2" scope="row" width="40%" class="textLeft"><br/></th>
				</tr>
				<tr>
					<th scope="row" class="textLeft"><label for="partitionName"><ikep4j:message pre='${preModeler}' key='form.partition.name'/></label></th>
					<td>
						<span class="textCenter">
							<input type="text" name="partitionName" id="partitionName" value="" style="width:100%;" class="inputbox w95" title="<ikep4j:message pre='${preModeler}' key='form.partition.name'/>"/>
						</span>
					</td>
				</tr>
				<tr>
					<th colspan="2" scope="row" width="40%" class="textLeft"><br/></th>
				</tr>
				<tr>
					<th scope="row" class="textLeft"><label for="partitionDesc"><ikep4j:message pre='${preModeler}' key='form.partition.desc'/></label></th>
					<td>
						<span class="textCenter">
							<textarea name="partitionDesc" id="partitionDesc" value="" style="width: 322px; height: 43px;" class="inputbox w95" title="<ikep4j:message pre='${preModeler}' key='form.partition.desc'/>"></textarea>
						</span>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>

<!--//blockDetail End--
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a class="button createPartitionConfirm" href="#create"><span><ikep4j:message pre='${preModeler}' key='button.create'/></span></a></li>
		<li><a class="button createPartitionCancel" href="#cancel"><span><ikep4j:message pre='${preModeler}' key='button.cancel'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->