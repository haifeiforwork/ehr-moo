<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="operationListPrefix" value="message.portal.admin.code.operationCode.operationCodeList"/> 
<c:set var="operationUiPrefix" value="ui.portal.admin.code.operationCode"/>

								<script type="text/javascript" language="javascript">
								//<!--
									$jq(document).ready(function() {
										
										$jq("#operationCode :input:visible:enabled:first").focus().select();
										
										// ID 중복을 체크함
										$jq("#checkIdButton").click(function() {
											checkId();
										});
										
										// code 값이 바뀌면 중복체크를 위해 codeCheck 값을 바꿔줌
										$jq("input[name=operationId]").change(function() {
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
													operationId : {
														required : true,
														rangelength : [0, 20]
													},
													<c:forEach var="i18nMessage" items="${operationCode.i18nMessageList}" varStatus="loopStatus">
													"i18nMessageList[${loopStatus.index}].itemMessage" : {
														required : true,
														rangelength : [0, 100]
													},
													</c:forEach>
													description : {
														required : true,
														rangelength : [0, 150]
													}
												},
												messages : {
													operationId : {
														required : "<ikep4j:message key="NotNull.operationCode.operationId" />",
														rangelength : "<ikep4j:message key="Size.operationCode.operationId" />"
													},
													description : {
														required : "<ikep4j:message key="NotNull.operationCode.description" />",
														rangelength : "<ikep4j:message key="Size.operationCode.description" />"
													},
													<c:forEach var="i18nMessage" items="${operationCode.i18nMessageList}" varStatus="loopStatus">
													"i18nMessageList[${loopStatus.index}].itemMessage" : {
														required : "<ikep4j:message key="NotNull.operationCode.operationName" />",
														maxlength : "<ikep4j:message key="Size.operationCode.operationName" />"
													},
													</c:forEach>
												},
												submitHandler : function(operationCode) {
											    	
											    	// 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
													if (($jq("#codeCheck").val() == "success")||
															($jq("#codeCheck").val() == "modify")) {
														$jq('#operationName').val($jq('#defaultLocaleValue').val());
														$jq.ajax({
															url : '<c:url value="createOperationCode.do" />',
															data : $jq("#operationCode").serialize(),
															type : "post",
															success : function(result) {
																alert("<ikep4j:message pre="${operationListPrefix}" key="alert.saveCompleted" />");
																$jq("#tempId").val(result);
																getList();
															},
					 										error : function(xhr, exMessage) {
																var errorItems = $.parseJSON(xhr.responseText).exception;
																validator.showErrors(errorItems);
															}
														});
													} else {
														alert("<ikep4j:message pre="${operationListPrefix}" key="alert.checkDuplicaed" />");
														return;
													}
												}
											};
											
											var validator = new iKEP.Validator("#operationCode", validOptions);
									});
								//-->
								</script>
								
								<form id="operationCode" name="operationCode" method="post" onsubmit="return false;" action="">
								
									<input type="hidden" id="codeCheck" name="codeCheck" value="${operationCode.codeCheck}"/>
								
									<table summary="<ikep4j:message pre="${operationUiPrefix}" key="tableSummary" />">
									<caption></caption>
										<colgroup>
											<col width="15%"/>
											<col width="10%"/>
											<col width="75%"/>
										</colgroup>
									<tbody>
										<tr>
											<th scope="row" colspan="2">
												<span class="colorPoint">*</span> <ikep4j:message pre="${operationUiPrefix}" key="operationId" />
											</th>
											<td>
												<div>
													<input type="text" name="operationId" id="operationId" value="${operationCode.operationId}" size="50" class="inputbox" /> 
														&nbsp;<a class="button_ic" id="checkIdButton" href="#a"><span><ikep4j:message pre="${operationUiPrefix}" key="checkDuplicated" /></span></a>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row" rowspan="${localeSize}" >
												<span class="colorPoint">*</span> <ikep4j:message pre="${operationUiPrefix}" key="operationName" />
											</th>
											<c:forEach var="i18nMessage" items="${operationCode.i18nMessageList}" varStatus="loopStatus">
												<c:if test="${i18nMessage.fieldName eq 'operationName' }">
													<c:if test="${i18nMessage.index > 1}">
													<tr>
													</c:if>
														<th scope="row" ><span class="colorPoint">*</span> <c:out value="${i18nMessage.localeCode}"/></th>
														<td>
															<div>
																<spring:bind path="operationCode.i18nMessageList[${loopStatus.index}].itemMessage">
																	<input type="text" name="i18nMessageList[${loopStatus.index}].itemMessage" value="${i18nMessage.itemMessage}"  size="50" <c:if test="${i18nMessage.localeCode == userLocaleCode}">id="defaultLocaleValue" </c:if> class="inputbox"/>
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
											<th scope="row" colspan="2">
												<span class="colorPoint">*</span> <ikep4j:message pre="${operationUiPrefix}" key="description" />
											</th>
											<td>
												<div>
												<input type="text" name="description" id="description" value="${operationCode.description}" size="50" class="inputbox" />
												</div> 
											</td>
										</tr>
									</tbody>
									
									</table>
								
									<input type="hidden" id="itemTypeCode" name="itemTypeCode" value="PO"/>
									<input type="hidden" id="fieldName" name="fieldName" value="operationName"/>
									<input type="hidden" name="operationName" id="operationName" value="" />
									
								</form>