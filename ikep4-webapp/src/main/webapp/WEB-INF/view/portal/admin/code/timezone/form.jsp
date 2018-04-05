<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="timezoneListPrefix" value="message.portal.admin.code.timezone.timezoneList"/> 
<c:set var="timezoneUiPrefix" value="ui.portal.admin.code.timezone"/>

								<script type="text/javascript" language="javascript">
								//<!--
									$jq(document).ready(function() {
										
										$jq("#timezone :input:visible:enabled:first").focus().select();
										
										// ID 중복을 체크함
										$jq("#checkIdButton").click(function() {
											checkId();
										});
										
										// code 값이 바뀌면 중복체크를 위해 codeCheck 값을 바꿔줌
										$jq("input[name=timezoneId]").change(function() {
											$jq("#codeCheck").val('changed');
										});
										
										// 백스페이스 방지(input, select 제외)
										$jq(document).keydown(function(e) {
											var element = e.target.nodeName.toLowerCase();
											if (element != 'input') {
											    if (e.keyCode === 8) {
											        return false;
											    }
											}
										});
										
										var validOptions = {
												rules : {
													timezoneId : {
														required : true,
														rangelength : [0, 20]
													},
													<c:forEach var="i18nMessage" items="${timezone.i18nMessageList}" varStatus="loopStatus">
													"i18nMessageList[${loopStatus.index}].itemMessage" : {
														required : true,
														rangelength : [0, 100]
													},
													</c:forEach>
													timeDifference : {
														required : true,
														number : true,
														rangelength : [0, 3]
													},
													description : {
														required : true,
														rangelength : [0, 150]
													}								
												},
												messages : {
													timezoneId : {
														required : "<ikep4j:message key="NotNull.timezone.timezoneId" />",
														rangelength : "<ikep4j:message key="Size.timezone.timezoneId" />"
													},
													<c:forEach var="i18nMessage" items="${timezone.i18nMessageList}" varStatus="loopStatus">
													"i18nMessageList[${loopStatus.index}].itemMessage" : {
														required : "<ikep4j:message key="NotNull.timezone.timezoneName" />",
														rangelength : "<ikep4j:message key="Size.timezone.timezoneName" />"
													},
													</c:forEach>
													timeDifference : {
														required : "<ikep4j:message key="NotNull.timezone.timeDifference" />",
														number : "<ikep4j:message key="Number.timezone.timeDifference" />",
														rangelength : "<ikep4j:message key="Size.timezone.timeDifference" />"
													},
													description : {
														required : "<ikep4j:message key="NotNull.timezone.description" />",
														rangelength : "<ikep4j:message key="Size.timezone.description" />"
													}
												},
												submitHandler : function(timezone) {
											    	
											    	// 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
													if (($jq("#codeCheck").val() == "success")||
															($jq("#codeCheck").val() == "modify")) {
														$jq('#timezoneName').val($jq('#defaultLocaleValue').val());
														$jq.ajax({
															url : '<c:url value="createTimezone.do" />',
															data : $jq("#timezone").serialize(),
															type : "post",
															success : function(result) {
																alert("<ikep4j:message pre="${timezoneListPrefix}" key="alert.saveCompleted" />");
																$jq("#tempId").val(result);
																getList();
															},
					 										error : function(xhr, exMessage) {
																var errorItems = $.parseJSON(xhr.responseText).exception;
																validator.showErrors(errorItems);
															}
														});
													} else {
														alert("<ikep4j:message pre="${timezoneListPrefix}" key="alert.checkDuplicaed" />");
														return;
													}
												}
											};
											
											var validator = new iKEP.Validator("#timezone", validOptions);
									});
								//-->
								</script>
								
								<form id="timezone" name="timezone" method="post" onsubmit="return false;" action="">
								
									<input type="hidden" id="codeCheck" name="codeCheck" value="${timezone.codeCheck}"/>
								
									<table summary="<ikep4j:message pre="${timezoneUiPrefix}" key="tableSummary" />">
									<caption></caption>	
										<colgroup>
											<col width="15%"/>
											<col width="15%"/>
											<col width="70%"/>
										</colgroup>
									<tbody>
									
										<tr>
											<th scope="row" colspan="2">*<ikep4j:message pre="${timezoneUiPrefix}" key="timezoneId" /></th>
											<td>
												<div>
													<input type="text" name="timezoneId" id="timezoneId" value="${timezone.timezoneId}" size="50" /> 
														&nbsp;<a class="button" id="checkIdButton" href="#a"><span><ikep4j:message pre="${timezoneUiPrefix}" key="checkDuplicated" /></span></a>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row" rowspan="${localeSize}">* <ikep4j:message pre="${timezoneUiPrefix}" key="timezoneName" /></th>
											<c:forEach var="i18nMessage" items="${timezone.i18nMessageList}" varStatus="loopStatus">
												<c:if test="${i18nMessage.fieldName eq 'timezoneName' }">
													<c:if test="${i18nMessage.index > 1}">
													<tr>
													</c:if>
														<th scope="row" width="15%">*<c:out value="${i18nMessage.localeCode}"/></th>
														<td>
															<div>
																<spring:bind path="timezone.i18nMessageList[${loopStatus.index}].itemMessage">
																	<input type="text" name="i18nMessageList[${loopStatus.index}].itemMessage" value="${i18nMessage.itemMessage}"  size="50" <c:if test="${i18nMessage.localeCode == userLocaleCode}">id="defaultLocaleValue" </c:if> />
																	<input type="hidden" name="i18nMessageList[${loopStatus.index}].localeCode" value="${i18nMessage.localeCode}" />
																	<input type="hidden" name="i18nMessageList[${loopStatus.index}].fieldName" value="${i18nMessage.fieldName}" />
																	<input type="hidden" name="i18nMessageList[${loopStatus.index}].messageId" value="${i18nMessage.messageId}" />
																</spring:bind>
															</div>
														</td>
													</tr>
												</c:if>
											</c:forEach>
											
										<tr>
											<th scope="row" colspan="2"><ikep4j:message pre="${timezoneUiPrefix}" key="displayName" /></th>
											<td>
												<input type="text" name="displayName" id="displayName" value="${timezone.displayName}" size="50" /> 
											</td>
										</tr>
										<tr>
											<th scope="row" colspan="2">*<ikep4j:message pre="${timezoneUiPrefix}" key="timeDifference" /></th>
											<td>
												<div>
													<input type="text" name="timeDifference" id="timeDifference" value="${timezone.timeDifference}" size="50" />
												</div> 
											</td>
										</tr>
										<tr>
											<th scope="row" colspan="2">*<ikep4j:message pre="${timezoneUiPrefix}" key="description" /></th>
											<td>
												<div>
													<input type="text" name="description" id="description" value="${timezone.description}" size="50" />
												</div> 
											</td>
										</tr>
									</tbody>
									
									</table>
									<input type="hidden" id="sortOrder" name="sortOrder" value="${timezone.sortOrder}"/>
									<input type="hidden" id="itemTypeCode" name="itemTypeCode" value="PO"/>
									<input type="hidden" id="fieldName" name="fieldName" value="timezoneName"/>
									<input type="hidden" id="oldSortOrder" name="oldSortOrder" value="${oldSortOrder}"/>
									<input type="hidden" id="timezoneName" name="timezoneName" value="" />
								</form>