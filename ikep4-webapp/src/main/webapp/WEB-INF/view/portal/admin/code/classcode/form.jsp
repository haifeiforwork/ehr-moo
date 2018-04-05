<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="classCodeUiPrefix" value="ui.portal.admin.code.classCode"/>
<c:set var="classCodeListPrefix" value="message.portal.admin.code.classCode.classCodeList"/> 

								<script type="text/javascript" language="javascript">
								//<!--
									$jq(document).ready(function() {
										
										$jq("#classCode :input:visible:enabled:first").focus().select();
										
										// ID 중복을 체크함
										$jq("#checkIdButton").click(function() {
											checkId();
										});
										
										// code 값이 바뀌면 중복체크를 위해 codeCheck 값을 바꿔줌
										$jq("input[name=classId]").change(function() {
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
													classId : {
														required : true,
														rangelength : [0, 20]
													},
													<c:forEach var="i18nMessage" items="${classCode.i18nMessageList}" varStatus="loopStatus">
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
													classId : {
														required : "<ikep4j:message key="NotNull.classCode.classId" />",
														rangelength : "<ikep4j:message key="Size.classCode.classId" />"
													},
													<c:forEach var="i18nMessage" items="${classCode.i18nMessageList}" varStatus="loopStatus">
													"i18nMessageList[${loopStatus.index}].itemMessage" : {
														required : "<ikep4j:message key="NotNull.classCode.className" />",
														maxlength : "<ikep4j:message key="Size.classCode.className" />"
													},
													</c:forEach>
													description : {
														required : "<ikep4j:message key="NotNull.classCode.description" />",
														rangelength : "<ikep4j:message key="Size.classCode.description" />"
													}
												},
												submitHandler : function(classCode) {
											    	
											    	// 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
													if (($jq("#codeCheck").val() == "success")||
															($jq("#codeCheck").val() == "modify")) {
														$jq('#className').val($jq('#defaultLocaleValue').val());
														$jq.ajax({
															url : '<c:url value="createClassCode.do" />',
															data : $jq("#classCode").serialize(),
															type : "post",
															success : function(result) {
																alert("<ikep4j:message pre="${classCodeListPrefix}" key="alert.saveCompleted" />");
																$jq("#tempId").val(result);
																getList();
															},
					 										error : function(xhr, exMessage) {
																var errorItems = $.parseJSON(xhr.responseText).exception;
																validator.showErrors(errorItems);
															}
														});
													} else {
														alert("<ikep4j:message pre="${classCodeListPrefix}" key="alert.checkDuplicaed" />");
														return;
													}
												}
											};
											
											var validator = new iKEP.Validator("#classCode", validOptions);
									});
								//-->
								</script>
								
								<form id="classCode" name="classCode" method="post" onsubmit="return false;" action="">
								
									<input type="hidden" id="codeCheck" name="codeCheck" value="${classCode.codeCheck}"/>
								
									<table summary="<ikep4j:message pre="${classCodeUiPrefix}" key="tableSummary" />">
									<caption></caption>
										<colgroup>
											<col width="15%"/>
											<col width="10%"/>
											<col width="75%"/>
										</colgroup>
									<tbody>
										<tr>
											<th scope="row" colspan="2">
												<span class="colorPoint">*</span> <ikep4j:message pre="${classCodeUiPrefix}" key="classId" />
											</th>
											<td>
											<div>
												<input type="text" name="classId" id="classId" value="${classCode.classId}" size="50" class="inputbox" /> 
													&nbsp;<a class="button_ic" id="checkIdButton" href="#a"><span><ikep4j:message pre="${classCodeUiPrefix}" key="checkDuplicated" /></span></a>
											</div>
											</td>
										</tr>
										<tr>
											<th scope="row" rowspan="${localeSize}" width="15%">
												<span class="colorPoint">*</span> <ikep4j:message pre="${classCodeUiPrefix}" key="className" />
											</th>
											<c:forEach var="i18nMessage" items="${classCode.i18nMessageList}" varStatus="loopStatus">
												<c:if test="${i18nMessage.fieldName eq 'className' }">
													<c:if test="${i18nMessage.index > 1}">
													<tr>
													</c:if>
														<th scope="row" width="15%"><span class="colorPoint">*</span> <c:out value="${i18nMessage.localeCode}"/></th>
														<td>
															<div>
																<spring:bind path="classCode.i18nMessageList[${loopStatus.index}].itemMessage">
																	<input type="text" name="i18nMessageList[${loopStatus.index}].itemMessage" value="${i18nMessage.itemMessage}"  size="50" <c:if test="${i18nMessage.localeCode == userLocaleCode}">id="defaultLocaleValue" </c:if> class="inputbox" />
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
											<th scope="row" colspan="2">*<ikep4j:message pre="${classCodeUiPrefix}" key="description" /></th>
											<td>
												<div>
												<input type="text" name="description" id="description" value="${classCode.description}" size="50" class="inputbox" />
												</div> 
											</td>
										</tr>
									</tbody>
									
									</table>
								
									<input type="hidden" id="itemTypeCode" name="itemTypeCode" value="PO"/>
									<input type="hidden" id="fieldName" name="fieldName" value="className"/>
									<input type="hidden" name="className" id="className" value="" />
									
								</form>