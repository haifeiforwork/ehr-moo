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
					processName        : { required:true },
					processDescription : { required:true }
				},
				messages : {
					processName        : {
			            required  : "<ikep4j:message pre='${preValidation}' key='message.2'/>",
			        },
					processDescription : {
			            required  : "<ikep4j:message pre='${preValidation}' key='message.8'/>",
			        }
				},
				submitHandler : function(form){
					// 기본정보 XPDL에 저장.
					applyProcessDefaultInfo("replace");
					// 정보 초기화.
                    createProcessInfo();
					$( "#dialog-processModifyDefaultInfo" ).empty();
                    $( "#dialog-processModifyDefaultInfo" ).dialog( "close" );
				}
			};
			
			new iKEP.Validator("#processDefaultInfoForm", validOptions);
			
			$("select[name='processName']").focus();
			
			$( ".button, .bold" ).click(function() {
				// 버튼 이벤트 Id.
				var attrIds = $(this).attr("class").split(" ");
				var attrId = attrIds[attrIds.length - 1];
				
				switch ( attrId ){
					// 파티션 추가 확인
					case "processModifyDefaultInfoConfirm" :
						$("#processDefaultInfoForm").submit();
						break;	
						
					// 파티션 추가 취소
					case "processModifyDefaultInfoCancel" :
						$( "#dialog-processModifyDefaultInfo" ).empty();
						$( "#dialog-processModifyDefaultInfo" ).dialog( "close" );
						break;	
				}
			});
		});
	})(jQuery);
</script>

<!-- Modal window Start -->
<!--blockDetail Start-->
<form id="processDefaultInfoForm" name="processDefaultInfoForm" onsubmit="return false;">
<div class="blockDetail_2" id="processDefaultInfo">
	<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.process.defaultinfo.update'/>">
		<caption></caption>
		<tbody>
			<tr>
				<th scope="row" width="40%" class="textLeft"><label for="processId"><spring:message code="message.workflow.modeler.form.process.id"/></label></th>
				<td width="60%">
					<span class="textCenter">
						<c:out value="${param.processId}"/>
						<input type="hidden" name="processId" value="${param.processId}"/>
					</span>
				</td>
			</tr>
			<tr>
				<th scope="row" class="textLeft"><label for="processName"><spring:message code="message.workflow.modeler.form.process.name"/></label></th>
				<td>
					<span class="textCenter">
						<input type="text" name="processName" value="${param.processName}" style="width:100%;" class="inputbox w95"/>
					</span>
				</td>
			</tr>
			<tr>
				<th scope="row" class="textLeft"><label for="processVersion"><spring:message code="message.workflow.modeler.form.process.ver"/></label></th>
				<td>
					<span class="textCenter">
						<c:out value="${param.version}"/>
						<input type="hidden" name="processVersion" value="${param.version}"/>
					</span>
				</td>
			</tr>
			<tr>
				<th scope="row" class="textLeft"><label for="processDescription"><spring:message code="message.workflow.modeler.form.process.desc"/></label></th>
				<td>
					<span class="textCenter">
						<textarea name="processDescription" style="width: 322px; height: 43px;" class="inputbox w95">${param.description}</textarea>
					<span>
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
		<li><a class="button processModifyDefaultInfoConfirm" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.update'/></span></a></li>
		<li><a class="button processModifyDefaultInfoCancel" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.cancel'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
<!-- //Modal window End -->