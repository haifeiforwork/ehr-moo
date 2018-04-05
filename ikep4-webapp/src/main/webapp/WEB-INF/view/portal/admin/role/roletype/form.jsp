<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="roleTypeListPrefix" value="message.portal.admin.role.roleType.roleTypeList"/>
<c:set var="roleTypeUiPrefix" value="ui.portal.admin.role.roleType"/>

								<script type="text/javascript" language="javascript">
								//<!--
									$jq(document).ready(function() {
										
										$jq("#roleType :input:visible:enabled:first").focus().select();
										
										// ID 중복을 체크함
										$jq("#checkIdButton").click(function() {
											checkId();
										});
										
										// code 값이 바뀌면 중복체크를 위해 codeCheck 값을 바꿔줌
										$jq("input[name=roleTypeId]").change(function() {
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
													<c:forEach var="i18nMessage" items="${roleType.i18nMessageList}" varStatus="loopStatus">
													"i18nMessageList[${loopStatus.index}].itemMessage" : {
														required : true,
														rangelength : [0, 100]
													},
													</c:forEach>
													roleTypeId : {
														required : true,
														rangelength : [0, 20]
													}
												},
												messages : {
													<c:forEach var="i18nMessage" items="${roleType.i18nMessageList}" varStatus="loopStatus">
													"i18nMessageList[${loopStatus.index}].itemMessage" : {
														required : "<ikep4j:message key="NotNull.roleType.roleTypeName" />",
														maxlength : "<ikep4j:message key="Size.roleType.roleTypeName" />"
													},
													</c:forEach>
													roleTypeId : {
														required : "<ikep4j:message key="NotNull.roleType.roleTypeId" />",
														rangelength : "<ikep4j:message key="Size.roleType.roleTypeId" />"
													}
												},
												submitHandler : function(roleType) {
											    	
											    	// 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
													if (($jq("#codeCheck").val() == "success")||
															($jq("#codeCheck").val() == "modify")) {
														$jq('#roleTypeName').val($jq('#defaultLocaleValue').val());
														$jq.ajax({
															url : '<c:url value="createRoleType.do" />',
															data : $jq("#roleType").serialize(),
															type : "post",
															success : function(result) {
																alert("<ikep4j:message pre="${roleTypeListPrefix}" key="alert.saveCompleted" />");
																$jq("#tempId").val(result);
																getList();
															},
					 										error : function(xhr, exMessage) {
																var errorItems = $.parseJSON(xhr.responseText).exception;
																validator.showErrors(errorItems);
															}
														});
													} else {
														alert("<ikep4j:message pre="${roleTypeListPrefix}" key="alert.checkDuplicaed" />");
														return;
													}
												}
											};
											
											var validator = new iKEP.Validator("#roleType", validOptions);
											
									});
								//-->
								</script>
								
								<form id="roleType" name="roleType" method="post" onsubmit="return false;" action="">
							
									<input type="hidden" id="codeCheck" name="codeCheck" value="${roleType.codeCheck}"/>
								
									<table summary="<ikep4j:message pre="${roleTypeUiPrefix}" key="tableSummary" />">
									<caption></caption>
										<colgroup>
											<col width="15%" />
											<col width="10%" />
											<col width="75%" />
										</colgroup>
									<tbody>
										<tr>
											<th scope="row" colspan="2">
												<span class="colorPoint">*</span> <ikep4j:message pre="${roleTypeUiPrefix}" key="roleTypeId" />
											</th>
											<td>
												<div>
													<input type="text" name="roleTypeId" id="roleTypeId" value="${roleType.roleTypeId}" size="50" class="inputbox"/> 
														&nbsp;<a class="button_ic" id="checkIdButton" href="#a"><span><ikep4j:message pre="${roleTypeUiPrefix}" key="checkDuplicated" /></span></a>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row" rowspan="${localeSize}" width="15%">
												<span class="colorPoint">*</span> <ikep4j:message pre="${roleTypeUiPrefix}" key="roleTypeName" />
											</th>
											<c:forEach var="i18nMessage" items="${roleType.i18nMessageList}" varStatus="loopStatus">
												<c:if test="${i18nMessage.fieldName eq 'roleTypeName' }">
													<c:if test="${i18nMessage.index > 1}">
													<tr>
													</c:if>
														<th scope="row" width="15%"><span class="colorPoint">*</span> <c:out value="${i18nMessage.localeCode}"/></th>
														<td>
															<div>
																<spring:bind path="roleType.i18nMessageList[${loopStatus.index}].itemMessage">
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
									</tbody>
									
									</table>
								
									<input type="hidden" id="itemTypeCode" name="itemTypeCode" value="PO"/>
									<input type="hidden" id="fieldName" name="fieldName" value="roleTypeName"/>
									<input type="hidden" name="roleTypeName" id="roleTypeName" value="" />
								</form>