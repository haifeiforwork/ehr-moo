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
					activityDescription : { required:true }
				},
				messages : {
					activityDescription : {
			            required  : "<ikep4j:message pre='${preValidation}' key='message.22'/>",
			        }
				},
				submitHandler : function(form){
					applyActivityDefaultInfo("replace");
					$( "#dialog-activityModifyDefaultInfo" ).empty();
	                $( "#dialog-activityModifyDefaultInfo" ).dialog( "close" );
				}
			};
			
			new iKEP.Validator("#activityDefaultInfoForm", validOptions);
			
			$("textarea[name='activityDescription']").focus();
			
			$( ".button, .bold" ).click(function() {
				// 버튼 이벤트 Id.
				var attrIds = $(this).attr("class").split(" ");
				var attrId = attrIds[attrIds.length - 1];
				
				switch ( attrId ){
					// 액티비티 기본정보 수정 확인
					case "activityModifyDefaultInfoConfirm" :
						$("#activityDefaultInfoForm").submit();
						break;	
						
					// 액티비티 기본정보 수정 취소
					case "activityModifyDefaultInfoCancel" :
						$( "#dialog-activityModifyDefaultInfo" ).empty();
						$( "#dialog-activityModifyDefaultInfo" ).dialog( "close" );
						break;	
				}
			});
		});
	})(jQuery);
</script>

<!-- Modal window Start -->
<!--blockDetail Start-->
<form id="activityDefaultInfoForm" name="activityDefaultInfoForm" onsubmit="return false;">
<div class="blockDetail_2" id="activityDefaultInfo">
	<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.process.defaultinfo.update'/>">
		<caption></caption>
		<tbody>
			<tr>
				<th scope="row" width="40%" class="textLeft"><label for="activityId"><ikep4j:message pre='${preModeler}' key='form.activity.id'/></label></th>
				<td width="60%">
					<span class="textCenter">
						<c:out value="${param.activityId}"/>
						<input type="hidden" name="activityId" value="${param.activityId}"/>
					</span>
				</td>
			</tr>
			<tr>
				<th scope="row" class="textLeft"><label for="activityName"><ikep4j:message pre='${preModeler}' key='form.activity.name'/></label></th>
				<td>
					<span class="textCenter">
						<c:out value="${param.activityName}"/>
						<input type="hidden" name="activityName" value="${param.activityName}"/>
					</span>
				</td>
			</tr>
			<tr>
				<th scope="row" class="textLeft"><label for="activityPriority"><ikep4j:message pre='${preModeler}' key='form.activity.priority'/></label></th>
				<td>
					<span class="textCenter">
						<select id="activityPriority" name="activityPriority">
							<option value="100" <c:if test="${'100' eq param.activityPriority}">selected="selected"</c:if>>100</option>
							<option value="50" <c:if test="${'50' eq param.activityPriority}">selected="selected"</c:if>>50</option>
							<option value="0" <c:if test="${'0' eq param.activityPriority}">selected="selected"</c:if>>0</option>
						</select>
					</span>
				</td>
			</tr>
			<tr>
				<th scope="row" class="textLeft"><label for="activityDescription"><ikep4j:message pre='${preModeler}' key='form.activity.desc'/></label></th>
				<td>
					<span class="textCenter">
						<textarea name="activityDescription" style="width: 322px; height: 43px;" class="inputbox w95">${param.activityDescription}</textarea>
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
		<li><a class="button activityModifyDefaultInfoConfirm" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.update'/></span></a></li>
		<li><a class="button activityModifyDefaultInfoCancel" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.cancel'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
<!-- //Modal window End -->