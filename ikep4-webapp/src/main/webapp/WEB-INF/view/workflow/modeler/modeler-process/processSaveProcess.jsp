<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" errorPage=""  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preModeler"  value="message.workflow.modeler" />
<c:set var="preValidation"  value="validate.workflow.modeler" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script language="JavaScript">
	(function($) {
		var modelScript = "";
		
		// 프리즘 CallBack 함수.
		getViewScriptModel = function(result){
			var partitionId = '<c:out value="${processList.processModel.partitionId}"/>';
			var partitionName = '<c:out value="${processList.processModel.partitionName}"/>';
			var processId = xmlUtil.selectSingleNode("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/@Id").nodeValue;
			var processName = xmlUtil.selectSingleNode("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/@Name").nodeValue;
			var xpdlProcessVersion = xmlUtil.selectSingleNode("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:RedefinableHeader/xpdl:Version").firstChild.nodeValue;
			var processVersion = $("input[name='saveProcessVer']").val();
			// 프로세스 설명 Description Error로 인해 확인해야됨.
			if(xmlUtil.selectSingleNode("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:ProcessHeader/xpdl:Description").hasChildNodes()) {
				processDescription = xmlUtil.selectSingleNode("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:ProcessHeader/xpdl:Description").firstChild.nodeValue;
			} else {
				processDescription = '';
			}
			var processState = "";
			var url = "";
			
			if( processVersion != xpdlProcessVersion ){
				url = '<c:url value="createProcess.do" />';
				processState = "none";
				// 새로 버전을 올릴 시에 XPDL에 함께 적용.
				xpdl.replaceRedefinableHeader(processVersion);
			}else{
				url = '<c:url value="updateProcess.do" />';
				processState = "SAVED";
			}
			$.ajax({
			    url : url,
			    data : {partitionId		: partitionId
				      , partitionName	: partitionName
					  , processId		: processId
			    	  , processName		: processName
			    	  , processVer		: processVersion
			    	  , modelScript		: xmlStr(xmlUtil.getDocument())
			    	  , viewScript		: result
			    	  , state			: 'SAVED'
					  , description		: processDescription
			    	  , dummy:now.getSeconds()},
			    type : "POST",
			    success : function(result) {
					$( "#dialog-saveProcess" ).empty();
					$( "#dialog-saveProcess" ).dialog( "close" );
					
					// iFrame Script 저장 호출.
					document.getElementById("prism").contentWindow.sendToActionScript(1, processVersion);
					
					edited = false;
					processIdClickListner(processId, processName, processVersion, partitionId, partitionName, processDescription, "N");
					if (processVersion != xpdlProcessVersion) {
						alert("<ikep4j:message pre='${preModeler}' key='message.17'/> " + processId + "(" + processVersion + ")" + " <ikep4j:message pre='${preModeler}' key='message.18'/>");
						getCreateTree(partitionId);
					}
					
					document.getElementById("prism").contentWindow.sendToActionScript(1, processVersion);
			    },
				error: function() {
					$( "#dialog-saveProcess" ).empty();
					$( "#dialog-saveProcess" ).dialog( "close" );
					alert(processName + " <ikep4j:message pre='${preValidation}' key='error'/>");
				},
				complete : function() {}
			});
		}
		
		// 페이지 로딩시.
		$(document).ready(function () {
			
			var validOptions = {
				rules : {
					saveProcessVer   : { required : true, number : true, rangelength : [0, 3] }
				},
				messages : {
					saveProcessVer   : {
			            required    : "<ikep4j:message pre='${preValidation}' key='message.3'/>",
						rangelength : "<ikep4j:message pre='${preValidation}' key='message.9'/>"
			        }
				},
				notice : {
			        saveProcessVer : "<ikep4j:message pre='${preValidation}' key='message.10'/>"
			    },
				submitHandler : function(form){
					// iFrame Script 저장 호출.
					document.getElementById("prism").contentWindow.sendToActionScript('100');
					$( "#dialog-saveProcess" ).dialog( "close" );
				}
			};
			// Jquery Validate 인스턴스 생성.
			new iKEP.Validator("#processSaveForm", validOptions);
			// 버전 포커스.
			$("input[name='saveProcessVer']").focus();
		
			$( ".button, .bold" ).click(function() {
				// 버튼 이벤트 Id.
				var attrIds = $(this).attr("class").split(" ");
				var attrId = attrIds[attrIds.length - 1];
				
				switch ( attrId ){
					// 프로세스 저장
					case "saveProcessConfirm" :
						var xpdlProcessVersion = xmlUtil.selectSingleNode("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:RedefinableHeader/xpdl:Version").firstChild.nodeValue;
						var processVersion = $("input[name='saveProcessVer']").val();
						// * 버전업을 하려는데 기존 프로세스가 저장이 안된 경우..
						if (processVersion != xpdlProcessVersion) {
							if( edited == true ){
								$( "#dialog-saveProcess" ).empty();
								$( "#dialog-saveProcess" ).dialog( "close" );
								alert("<ikep4j:message pre='${preModeler}' key='message.19'/>");
								return false;
							}
						}
						$("#processSaveForm").submit();
						break;
						
					// 프로세스 저장 하지 않음.
					case "saveProcessNone" :
						processLoad("Y");
						$( "#dialog-saveProcess" ).empty();
						$( "#dialog-saveProcess" ).dialog( "close" );
						break;	
						
					// 프로세스 취소.
					case "saveProcessCancel" :
						$( "#dialog-saveProcess" ).empty();
						$( "#dialog-saveProcess" ).dialog( "close" );
						break;	
				}
			});
		});
	})(jQuery);
</script>
<!-- Modal window Start -->
<form id="processSaveForm" name="processSaveForm" onsubmit="return false;">
	<!--blockDetail Start-->
	<div class="blockDetail_2">
		<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.process.save'/>">
			<caption></caption>
			<tbody>
				<tr>
					<td colspan="2">	
						<c:if test="${param.edited eq 'Y'}">
							<strong><font color="#FF0000"> * [<ikep4j:message pre='${preModeler}' key='message.20'/>]</font></strong><br/>
						</c:if>
						<c:out value="${param.processName}"/> (<c:out value="${param.processVer}"/>) <ikep4j:message pre='${preModeler}' key='message.21'/>						
					</td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>	
				<tr>
					<th scope="row" class="textLeft"><ikep4j:message pre='${preModeler}' key='form.state.ver'/></th>
					<td>
						<span class="textCenter">
							<input name="saveProcessVer" type="text" class="inputbox w100" title="" value="${param.processVer}" style="ime-mode:disabled;"/>
						</span>
					</td>
				</tr>		
			</tbody>
		</table>
		
		<!--directive Start-->
		<div class="directive"> 
			<ul>
				<li>
					<span>
						<strong><ikep4j:message pre='${preModeler}' key='form.process.save'/></strong> <br />

						<ikep4j:message pre='${preModeler}' key='message.22'/>
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
			<li><a class="button saveProcessConfirm" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.save'/></span></a></li>
			<c:if test="${param.edited eq 'Y'}">
				<li><a class="button saveProcessNone" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.notsaved'/></span></a></li>
			</c:if>
			<li><a class="button saveProcessCancel" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.cancel'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
</form>
<!-- //Modal window End -->