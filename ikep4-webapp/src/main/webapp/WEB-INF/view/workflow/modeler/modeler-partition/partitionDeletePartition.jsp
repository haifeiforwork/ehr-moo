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
				// 파티션 삭제 확인
				case "deletePartitionConfirm" :
					$.post('<c:url value="deletePartition.do"/>', {
				            "partitionId" : '<c:out value="${param.partitionId}"/>'
			        }).success(function(result){
						if (result == 1) {
							// Body 초기화.
							getRefreshBody();
							getCreateTree('<c:out value="${param.partitionId}"/>');
							alert("<ikep4j:message pre='${preModeler}' key='message.53'/> " + '<c:out value="${param.partitionId}"/>' + "<ikep4j:message pre='${preModeler}' key='message.52'/>");
							
							xmlUtil.clearDocument();
							$( "#dialog-deletePartition" ).empty();
							$( "#dialog-deletePartition" ).dialog( "close" );
						}else{
							alert("<ikep4j:message pre='${preValidation}' key='error'/>");
						}
			        }).error(function(){
			            alert("<ikep4j:message pre='${preValidation}' key='error'/>");
			        });
					break;	
					
				// 파티션 삭제 취소
				case "deletePartitionCancel" :
					
					$( "#dialog-deletePartition" ).empty();
					$( "#dialog-deletePartition" ).dialog( "close" );
					break;	
			}
		});
	})(jQuery);
</script>
<!-- Modal window Start -->
<!--blockDetail Start-->
	<div class="blockDetail_2">
		<table summary="<ikep4j:message pre='${preModeler}' key='popup.title.partition.delete'/>">
			<caption></caption>
			<tbody>
				<tr>
					<td colspan="2">	
						<c:out value="${param.partitionName}"/> <ikep4j:message pre='${preModeler}' key='message.50'/>				
					</td>

				</tr>
			</tbody>
		</table>
		
		<!--directive Start-->
		<div class="directive"> 
			<ul>
				<li>
					<span>
						<strong>Partition Delete</strong> <br />

						<ikep4j:message pre='${preModeler}' key='message.47'/>
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

			<li><a class="button deletePartitionConfirm" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.confirm'/></span></a></li>
			<li><a class="button deletePartitionCancel" href="#a"><span><ikep4j:message pre='${preModeler}' key='button.cancel'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
<!--// Modal window End -->