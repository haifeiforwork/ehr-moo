<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" errorPage=""  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preModeler"  value="message.workflow.modeler" />
<c:set var="preValidation"  value="validate.workflow.modeler" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script language="JavaScript">
	(function($) {
		
		$( ".button, .bold" ).click(function() {
			// 버튼 이벤트 Id.
			var attrIds = $(this).attr("class").split(" ");
			var attrId = attrIds[attrIds.length - 1];
			
			switch ( attrId ){
				// 프로세스 배치 확인
				case "deployProcessConfirm" :
					
					$.ajax({
					    url : '<c:url value="deployProcess.do" />',
					    data : {partitionId: gEntityPartitionId
					    	  , processId  : gEntityProcessId
						      , modelScript:xmlStr(xmlUtil.getDocument())
					    	  , dummy:now.getSeconds()},
					    type : "POST",
					    success : function(result) {
								$( "#dialog-deployProcess" ).empty();
								$( "#dialog-deployProcess" ).dialog( "close" );
								
								if( result == true ){
									edited = false;
									var partitionId = '<c:out value="${processList.processModel.partitionId}"/>';
									var partitionName = '<c:out value="${processList.processModel.partitionName}"/>';
									var processId = xmlUtil.selectSingleNode("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/@Id").nodeValue;
									var processName = xmlUtil.selectSingleNode("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/@Name").nodeValue;
									var xpdlProcessVersion = xmlUtil.selectSingleNode("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:RedefinableHeader/xpdl:Version").firstChild.nodeValue;
									if(xmlUtil.selectSingleNode("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:ProcessHeader/xpdl:Description").hasChildNodes()) {
										processDescription = xmlUtil.selectSingleNode("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:ProcessHeader/xpdl:Description").firstChild.nodeValue;
									} else {
										processDescription = '';
									}
									processIdClickListner(processId, processName, xpdlProcessVersion, partitionId, partitionName, processDescription, "N");
									alert("<ikep4j:message pre='${preModeler}' key='message.10'/>");
								}else{
									alert("<ikep4j:message pre='${preModeler}' key='message.11'/>");
									return false;
								}
								
					    },
						error: function(error) {
							$( "#dialog-undeployProcess" ).empty();
							$( "#dialog-undeployProcess" ).dialog( "close" );
							alert("<ikep4j:message pre='${preModeler}' key='message.12'/>");
						},
						complete : function() {}
					});
					break;	
					
				// 프로세스 배치 취소
				case "deployProcessCancel" :
					$( "#dialog-deployProcess" ).empty();
					$( "#dialog-deployProcess" ).dialog( "close" );
					break;	
			}
		});
	})(jQuery);
</script>
<!-- Modal window Start -->
<!--blockDetail Start-->
	<div class="blockDetail_2">
		<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.process.deploy'/>">
			<caption></caption>
			<tbody>
				<tr>
					<td colspan="2">	
						<c:out value="${param.processName}"/> (<c:out value="${param.processVer}"/>) <ikep4j:message pre='${preModeler}' key='message.13'/>					
					</td>

				</tr>
			</tbody>
		</table>
		
		<!--directive Start-->
		<div class="directive"> 
			<ul>
				<li>
					<span>
						<strong>Process Deploy</strong> <br />

						<ikep4j:message pre='${preModeler}' key='message.14'/>
					</span>
				</li>	
			</ul>
		</div>
		<!--//directive End-->
	</div>
	<!--//blockDetail End-->
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>

			<li><a class="button deployProcessConfirm" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.batch'/></span></a></li>
			<li><a class="button deployProcessCancel" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.cancel'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
<!--// Modal window End -->