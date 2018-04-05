<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="prefix" value="message.portal.admin.screen.page.updatePageForm"/>

<c:if test="${!empty portalPage}">
<script type="text/javascript">
<!--
(function($) {
	
    $jq(document).ready(function() {
    	
		//저장, 취소 버튼 이벤트
		$jq('#saveButton').click(function() {
			$jq("#mainForm").submit();
		});
		
		$jq('#cancelButton').click(function() { 
			getView();
		});
		
		/**
		 * Validation Logic Start
		 */
		
		var validOptions = {
			rules : {
				<c:forEach var="i18nMessage" items="${portalPage.i18nMessageList}" varStatus="loopStatus">
				"i18nMessageList[${loopStatus.index}].itemMessage" : {
					required : true
					,maxlength : 100
				}
				<c:if test="${!loopStatus.last}">,</c:if>
				</c:forEach>
			},
			messages : {
				<c:forEach var="i18nMessage" items="${portalPage.i18nMessageList}" varStatus="loopStatus">
				"i18nMessageList[${loopStatus.index}].itemMessage" : {
					required : "<ikep4j:message key="NotEmpty.portalPortlet.locale" />"
					,maxlength : "<ikep4j:message key="Size.portalPortlet.locale" />"
				}
				<c:if test="${!loopStatus.last}">,</c:if>
				</c:forEach>
			},
			submitHandler : function(form) {
				$jq('#pageName').val($jq('#defaultLocaleValue').val());
				
				$.ajax({
					url : "<c:url value='/portal/admin/screen/page/updatePage.do'/>",
					type : "post",
					data : $(form).serialize(),
					success : function(result) {
						location.href= "<c:url value='/portal/admin/screen/page/readPageMain.do'/>?pageId="+result; //조회화면으로 포워딩
						alert("<ikep4j:message pre="${prefix}" key="alert.saveMessage" />");
					},
					error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
			}
		};


		var validator = new iKEP.Validator("#mainForm", validOptions);

		/**
		 * Validation Logic End
		 */
		 
		//className,resourceName,operationName,"권한설정 옵션"
		iKEP.loadSecurity("Portal-Page","${portalPage.pageId}","READ","User,Group,Role,Job,Duty,ExpUser", 25);
    });
})(jQuery);
//-->
</script>

<!--blockDetail Start-->
<form id="mainForm" method="post">
	<input type="hidden" name="pageId" value="${portalPage.pageId}"/>
	<div class="blockDetail">
		<table summary="<ikep4j:message pre="${prefix}" key="tableSummary" />">
			<caption></caption>
			<tbody>
				<tr id="localeTr1">
					<th scope="row" rowspan="${localeSize}" width="12%" class="textCenter"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="pageTitle" /></th>
					<c:forEach var="i18nMessage" items="${portalPage.i18nMessageList}" varStatus="loopStatus">
					<c:if test="${i18nMessage.index > 1}">
						<tr id="localeTr1${i18nMessage.index}" >
					</c:if>
							<th scope="row" width="6%"><span class="colorPoint">*</span><c:out value="${i18nMessage.localeCode}"/></th>
							<td>
								<div>
									<spring:bind path="portalPage.i18nMessageList[${loopStatus.index}].itemMessage">
										<input type="text" name="i18nMessageList[${loopStatus.index}].itemMessage" value="${i18nMessage.itemMessage}" class="inputbox w100" <c:if test="${i18nMessage.localeCode == userLocaleCode }">id="defaultLocaleValue" </c:if> />
										<input type="hidden" name="i18nMessageList[${loopStatus.index}].localeCode" value="${i18nMessage.localeCode}" />
										<input type="hidden" name="i18nMessageList[${loopStatus.index}].fieldName" value="${i18nMessage.fieldName}" />
										<input type="hidden" name="i18nMessageList[${loopStatus.index}].messageId" value="${i18nMessage.messageId}" />
									</spring:bind>
								</div>
							</td>
						</tr>
					</c:forEach>
					<input type="hidden" name="pageName" id="pageName" value="" />
					
				<tr>
					<th colspan="2" scope="row"><ikep4j:message pre="${prefix}" key="systemName" /></th>
					<td>
						<c:out value="${portalPage.systemName}"/>								
					</td>
				</tr>
				<tr>
					<th colspan="2" scope="row"><ikep4j:message pre="${prefix}" key="url" /></th>
					<td>
						/portal/main/body.do?pageId=<c:out value="${portalPage.pageId}"/>	
					</td>
				</tr>
			</tbody>
		</table>
		<div id="securityArea"></div>
	</div>
	<!--//blockDetail End-->
	
	<!--tableBottom Start-->
	<div class="tableBottom">										
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a id="cancelButton" class="button" href="#a"><span><ikep4j:message pre="${prefix}" key="cancel" /></span></a></li>
				<li><a id="saveButton" class="button" href="#a"><span><ikep4j:message pre="${prefix}" key="save" /></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
</form>
<!--//tableBottom End-->
</c:if>
<c:if test="${empty portalPage}">
	<ikep4j:message pre="${prefix}" key="noPage" />
</c:if>