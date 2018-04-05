<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" errorPage=""  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preModeler"  value="message.workflow.modeler" />
<c:set var="preValidation"  value="validate.workflow.modeler" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script language="JavaScript">
	(function($){
		createActivityVariables("selectable", "");
		
		$(function(){
			//=========================================================================
            // * Jquery SelecTable 초기화.
            //=========================================================================
            $("#selectable").selectable();
		});
		
		$( ".button, .bold" ).click(function() {
			// 버튼 이벤트 Id.
			var attrIds = $(this).attr("class").split(" ");
			var attrId = attrIds[attrIds.length - 1];
			
			switch ( attrId ){
				// 프로세스 변수 선택 확인
				case "createActivityVariableConfirm" :
					var activityVariableIds = new Array();
					var variableType = "";
					var cnt = 0;
					$("#selectable > li.ui-selected").find("input[name='Id']").each(function(){
						activityVariableIds[cnt] = $(this).val();
						cnt = cnt + 1;
		            });
					if( $("#activityVariableSearch > option[value=INPUT]").attr("selected") == true ){
						variableType = "in";
					}else{
						variableType = "out";
					}
					applyActivityVariable(variableType, activity.id, activityVariableIds, 'append');
					$( "#dialog-createActivityVariable" ).empty();
					$( "#dialog-createActivityVariable" ).dialog( "close" );
					break;	
					
				// 프로세스 변수 선택 취소
				case "createActivityVariableCancel" :
					$( "#dialog-createActivityVariable" ).empty();
					$( "#dialog-createActivityVariable" ).dialog( "close" );
					break;	
			}
		});
	})(jQuery);
</script>
<!-- Modal window Start -->
<!--blockDetail Start-->
<div class="blockDetail_2" id="activityCreateVariable" style="overflow-y:auto;height:100px;">
	<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.activity.variable.create'/>">
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
	<ol id="selectable"></ol>
</div>
<!--//blockDetail End-->	
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a class="button createActivityVariableConfirm" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.select'/></span></a></li>
		<li><a class="button createActivityVariableCancel" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.cancel'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
<!-- //Modal window End -->