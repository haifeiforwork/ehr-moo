<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="cityUiPrefix" value="ui.portal.admin.code.city"/>
<c:set var="cityListPrefix" value="message.portal.admin.code.city.cityList"/> 

			<script type="text/javascript" language="javascript">
			//<!--
			$jq(document).ready(function() {
										
				$jq("#city :input:visible:enabled:first").focus().select();
			
				$jq("#newFormButton").click(function() {
					getView('');
				});
				
				$jq("#submitButton").click(function() {
					$jq("#city").trigger("submit");
				});
				
				$jq("#deleteButton").click(function() {
					if ($jq("#cityId").val() == "") {
						alert("<ikep4j:message pre="${cityListPrefix}" key="alert.cantDelete" />");
						return;
					}
					
					if(confirm("<ikep4j:message pre="${cityListPrefix}" key="confirm.wannaDelete" />")) {
						$jq.ajax({
							url : '<c:url value="deleteCity.do" />',
							data : $jq("#city").serialize(),
							type : "post",
							success : function(result) {
								alert("<ikep4j:message pre="${cityListPrefix}" key="alert.deleteCompleted" />");
								getList();
							},
							error:function(){
								alert("<ikep4j:message pre="${cityListPrefix}" key="alert.failedToDelete" />");
							}
						});
					}
				});
				
				var validOptions = {
						rules : {
							<c:forEach var="i18nMessage" items="${city.i18nMessageList}" varStatus="loopStatus">
							"i18nMessageList[${loopStatus.index}].itemMessage" : {
								required : true,
								rangelength : [0, 100]
							},
							</c:forEach>
						},
						messages : {
							<c:forEach var="i18nMessage" items="${city.i18nMessageList}" varStatus="loopStatus">
							"i18nMessageList[${loopStatus.index}].itemMessage" : {
								required : "<ikep4j:message key="NotNull.city.cityName" />",
								maxlength : "<ikep4j:message key="Size.city.cityName" />"
							},
							</c:forEach>
						},
						submitHandler : function(nation) {
							$jq('#cityName').val($jq('#defaultLocaleValue').val());
							$jq('#cityEnglishName').val($jq('#enLocaleValue').val());
							$jq.ajax({
								url : '<c:url value="createCity.do" />',
								data : $jq("#city").serialize(),
								type : "post",
								success : function(result) {
									alert("<ikep4j:message pre="${cityListPrefix}" key="alert.saveCompleted" />");
									getList();
								},
									error : function(xhr, exMessage) {
									var errorItems = $.parseJSON(xhr.responseText).exception;
									validator.showErrors(errorItems);
								}
							});
						}
					};
					
					var validator = new iKEP.Validator("#city", validOptions);
			
				});
				
			
			//-->
			</script>
			<form id="city" name="city" method="post" onsubmit="return false;" action="">
				<input type="hidden" id="cityId" name="cityId" value="${city.cityId}"/>
				<input type="hidden" name="cityEnglishName" id="cityEnglishName" value="" />
				<input type="hidden" id="cityName" name="cityName" value="" />
				<input type="hidden" id="nationCode" name="nationCode" value="${nationCode}" />
				
				<div class="blockDetail">
					<table summary="<ikep4j:message pre="${cityUiPrefix}" key="tableSummary" />">
						<caption></caption>
							<colgroup>
								<col width="15%"/>
								<col width="10%"/>
								<col width="75%"/>
							</colgroup>
						<tbody>
						<tr>
							<th scope="row" rowspan="${localeSize}">
								<span class="colorPoint">*</span> <ikep4j:message pre="${cityUiPrefix}" key="cityName" />
							</th>
							<c:forEach var="i18nMessage" items="${city.i18nMessageList}" varStatus="loopStatus">
								<c:if test="${i18nMessage.fieldName eq 'cityName' }">
									<c:if test="${i18nMessage.index > 1}">
									<tr>
									</c:if>
										<th scope="row"><span class="colorPoint">*</span> <c:out value="${i18nMessage.localeCode}"/></th>
										<td>
											<div>
												<spring:bind path="city.i18nMessageList[${loopStatus.index}].itemMessage">
													<input type="text" name="i18nMessageList[${loopStatus.index}].itemMessage" value="${i18nMessage.itemMessage}"  size="50" <c:if test="${i18nMessage.localeCode == userLocaleCode}">id="defaultLocaleValue" </c:if>
													<c:if test="${i18nMessage.localeCode == 'en'}">id="enLocaleValue" </c:if> class="inputbox" />
													<input type="hidden" name="i18nMessageList[${loopStatus.index}].localeCode" value="${i18nMessage.localeCode}" />
													<input type="hidden" name="i18nMessageList[${loopStatus.index}].fieldName" value="${i18nMessage.fieldName}" />
													<input type="hidden" name="i18nMessageList[${loopStatus.index}].messageId" value="${i18nMessage.messageId}" />
													
												</spring:bind>
											</div>
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
				
				<!--blockButton Start-->
				<div class="blockButton">
					<ul>
						<li><a class="button" id="newFormButton" href="#a"><span><ikep4j:message pre="${cityUiPrefix}" key="button.new" /></span></a></li>
						<li><a class="button" id="submitButton" href="#a"><span><ikep4j:message pre="${cityUiPrefix}" key="button.save" /></span></a></li>
						<li><a class="button" id="deleteButton" href="#a"><span><ikep4j:message pre="${cityUiPrefix}" key="button.delete" /></span></a></li>
						<li><a class="button" id="cancleButton" href="<c:url value='/portal/admin/code/nation/getList.do'/>"><span><ikep4j:message pre="${cityUiPrefix}" key="button.cancle" /></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->
			</form>