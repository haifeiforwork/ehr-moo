<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="prefix" value="message.portal.admin.screen.page.createPageForm"/>

<script type="text/javascript">
<!--
(function($) {
	
    $jq(document).ready(function() {
		$("#divTab1").tabs();

		//저장, 취소 버튼 이벤트
		$jq('#saveButton').click(function() {
			$jq("#mainForm").submit();
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
					url : "<c:url value='/portal/admin/screen/page/createPage.do'/>",
					type : "post",
					data : $(form).serialize(),
					success : function(result) {
						location.href= "<c:url value='/portal/admin/screen/page/readPageMain.do'/>?pageId="+result+"&createSaveFlag=Y"; //조회화면으로 포워딩
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
		iKEP.loadSecurity("Portal-Page","","READ","User,Group,Role,Job,Duty,ExpUser", 25);

    });
	
})(jQuery);
//-->
</script>

<!--blockDetail Start-->
<form id="mainForm" action="" method="post">
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre="${prefix}" key="title" /></h2>
		<!--  div id="pageLocation">
			<ul>
				<li class="liFirst"><ikep4j:message pre="${prefix}" key="home" /></li>
				<li><ikep4j:message pre="${prefix}" key="1depth" /></li>
				<li><ikep4j:message pre="${prefix}" key="2depth" /></li>
				<li><ikep4j:message pre="${prefix}" key="3depth" /></li>
				<li class="liLast"><ikep4j:message pre="${prefix}" key="lastDepth" /></li>
			</ul>
		</div-->
	</div>
	<!--//pageTitle End-->
	
	<!--tab Start-->		
	<div id="divTab1" class="iKEP_tab">
		<ul>
			<li><a href="#tabs-1"><ikep4j:message pre="${prefix}" key="basicsData" /></a></li>
		</ul>
		<div id="tabs-1" style="padding: 0px;"></div>
	</div>
	<!--//tab End-->

	<div class="blockDetail">
		<input type="hidden" name="pageName" id="pageName" />
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
										<input type="text" name="i18nMessageList[${loopStatus.index}].itemMessage" value="${i18nMessage.itemMessage}" size="50" title="<ikep4j:message pre="${prefix}" key="pageTitle" />" <c:if test="${i18nMessage.localeCode == userLocaleCode }">id="defaultLocaleValue" </c:if> />
										<input type="hidden" name="i18nMessageList[${loopStatus.index}].localeCode" value="${i18nMessage.localeCode}" />
										<input type="hidden" name="i18nMessageList[${loopStatus.index}].fieldName" value="${i18nMessage.fieldName}" />
									</spring:bind>
								</div>
							</td>
						</tr>
					</c:forEach>
				<tr>
					<th colspan="2" scope="row"><ikep4j:message pre="${prefix}" key="systemName" /></th>
					<td>
						<select name="systemCode" title="<ikep4j:message pre="${prefix}" key="systemName" />">
							<c:forEach var="systemMap" items="${systemMapList}" varStatus="loopStatus">
								<option value="<c:out value='${systemMap.systemCode}'/>"><c:out value='${systemMap.systemName}'/></option>
							</c:forEach>
						</select>								
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
				<li><a id="cancelButton" class="button" href="<c:url value="listPage.do" />"><span><ikep4j:message pre="${prefix}" key="cancel" /></span></a></li>
				<li><a id="saveButton" class="button" href="#a"><span><ikep4j:message pre="${prefix}" key="save" /></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
</form>
<!--//tableBottom End-->