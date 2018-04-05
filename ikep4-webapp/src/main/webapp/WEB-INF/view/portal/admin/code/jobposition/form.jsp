<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="jobPositionListPrefix" value="message.portal.admin.code.jobPosition.jobPositionList"/>
<c:set var="jobPositionUiPrefix" value="ui.portal.admin.code.jobPosition"/>

								<script type="text/javascript" language="javascript">
								//<!--
									$jq(document).ready(function() {
										$jq("#jobPosition :input:visible:enabled:first").focus().select();
										
										// ID 중복을 체크함
										$jq("#checkCodeButton").click(function() {
											checkCode();
										});
										
										$jq("input[name=jobPositionCode]").change(function() {
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
													jobPositionCode : {
														required : true,
														rangelength : [0, 20]
													},
													jobPositionName : {
														required : true,
														rangelength : [0, 20]
													},
													jobPositionEnglishName : {
														required : true,
														englishName : true,
														rangelength : [0, 100]										
													}
											    },
											    messages : {
													jobPositionCode : {
														required : "<ikep4j:message key="NotNull.jobPosition.jobPositionCode" />",
														rangelength : "<ikep4j:message key="Size.jobPosition.jobPositionCode" />"
													},
													jobPositionName : {
														required : "<ikep4j:message key="NotNull.jobPosition.jobPositionName" />",
														rangelength : "<ikep4j:message key="Size.jobPosition.jobPositionName" />"
													},
													jobPositionEnglishName : {
														required : "<ikep4j:message key="NotNull.jobPosition.jobPositionEnglishName" />",
														englishName : "<ikep4j:message key="EnglishName.jobPosition.jobPositionEnglishName" />",
														rangelength : "<ikep4j:message key="Size.jobPosition.jobPositionEnglishName" />"
													}
											    },
											    submitHandler : function(jobPosition) {
											    	
											    	// 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
													if (($jq("#codeCheck").val() == 'success') || 
															($jq("#codeCheck").val() == 'modify')) {
														$jq.ajax({
															url : '<c:url value="createJobPosition.do" />',
															data : $jq("#jobPosition").serialize(),
															type : "post",
															success : function(result) {
																alert("<ikep4j:message pre="${jobPositionListPrefix}" key="alert.saveCompleted" />");
																$jq("#tempCode").val(result);
																getList();
															},
					 										error : function(xhr, exMessage) {
															var errorItems = $.parseJSON(xhr.responseText).exception;
															validator.showErrors(errorItems);
															}
														});
													} else {
														alert("<ikep4j:message pre="${jobPositionListPrefix}" key="alert.checkDuplicaed" />");
														return;
													}
												}

											 };

										var validator = new iKEP.Validator("#jobPosition", validOptions);
									});
								//-->
								</script>
								
								<form id="jobPosition" name="jobPosition" method="post" onsubmit="return false;" action="">
									
									<input type="hidden" id="codeCheck" name="codeCheck" value="${jobPosition.codeCheck}"/>
								
									<table summary="<ikep4j:message pre="${jobPositionUiPrefix}" key="tableSummary" />">
									<caption></caption>
										<colgroup>
											<col width="15%"/>
											<col width="10%"/>
											<col width="75%"/>
										</colgroup>								
									<tbody>
										<tr>
											<th scope="row" colspan="2">
												<span class="colorPoint">*</span> <ikep4j:message pre="${jobPositionUiPrefix}" key="jobPositionCode" />
											</th>
											<td>
												<div>
													<input type="text" name="jobPositionCode" id="jobPositionCode" value="${jobPosition.jobPositionCode}" size="50" class="inputbox" />
													&nbsp;<a class="button_ic" id="checkCodeButton" href="#a"><span><ikep4j:message pre="${jobPositionUiPrefix}" key="checkDuplicated" /></span></a>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row" rowspan="2"><span class="colorPoint">*</span> <ikep4j:message pre="${jobPositionUiPrefix}" key="jobPositionName" /></th>
											<th><span class="colorPoint">*</span> <ikep4j:message pre="${jobPositionUiPrefix}" key="jobPositionName" /></th>
											<td>
												<div>
													<input type="text" name="jobPositionName" id="jobPositionName" value="${jobPosition.jobPositionName}" size="50" class="inputbox" />
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="colorPoint">*</span> <ikep4j:message pre="${jobPositionUiPrefix}" key="jobPositionEnglishName" /></th>
											<td>
												<div>
													<input type="text" name="jobPositionEnglishName" id="jobPositionEnglishName" value="${jobPosition.jobPositionEnglishName}" size="50" class="inputbox" />
												</div>
											</td>
										</tr>
									</tbody>
									
									</table>
									
									<input type="hidden" id="sortOrder" name="sortOrder" value="${jobPosition.sortOrder}"/> 
									<input type="hidden" id="oldSortOrder" name="oldSortOrder" value="${oldSortOrder}"/>
									
								</form>