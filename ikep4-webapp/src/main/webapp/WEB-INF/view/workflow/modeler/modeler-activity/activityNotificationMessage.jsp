<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" errorPage=""  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preModeler"  value="message.workflow.modeler" />
<c:set var="preValidation"  value="validate.workflow.modeler" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script>
	(function($) {
		$( ".button, .bold" ).click(function() {
			// 버튼 이벤트 Id.
			var attrIds = $(this).attr("class").split(" ");
			var attrId = attrIds[attrIds.length - 1];
			
			switch ( attrId ){
				// 알림 메시지 확인
				case "activityNotificationConfirm" :
					$( "#dialog-activityNotificationMessage" ).empty();
					$( "#dialog-activityNotificationMessage" ).dialog( "close" );
					break;	
			}
		});
	})(jQuery);
</script>

<!-- Modal window Start -->
<!--blockDetail Start-->
<div class="blockDetail_2">
	<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.message'/>">
		<caption></caption>
		<tbody>
			<c:if test="${param.type == 'EMAIL'}">
				<tr>
					<th scope="row" width="40%" class="textLeft"><label for="title"><ikep4j:message pre='${preModeler}' key='form.state.title'/></label></th>
					<td width="60%">
						<span class="textCenter">
							<c:out value='${param.title}'/>
						</span>
					</td>
				</tr>
			</c:if>
			<tr>
				<th scope="row"  width="40%" class="textLeft"><label for="formProcessDesc"><ikep4j:message pre='${preModeler}' key='form.state.message'/></label></th>
				<td>
					<span class="textCenter">
						<c:out value='${param.body}'/>
					</span>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<!--//blockDetail End--
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a class="button activityNotificationConfirm" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.confirm'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
<!-- //Modal window End -->