<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="jobDutyListPrefix" value="message.portal.admin.code.jobDuty.jobDutyList"/>
<c:set var="jobDutyUiPrefix" value="ui.portal.admin.code.jobDuty"/>

								<script type="text/javascript" language="javascript">
								//<!--
									$jq(document).ready(function() {
										$jq("#jobDuty :input:visible:enabled:first").focus().select();
										
										// ID 중복을 체크함
										$jq("#checkCodeButton").click(function() {
											checkCode();
										});
										
										$jq("input[name=jobDutyCode]").change(function() {
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
													jobDutyCode : {
														required : true,
														rangelength : [0, 20]
													},
													jobDutyName : {
														required : true,
														rangelength : [0, 20]
													},
													jobDutyEnglishName : {
														required : true,
														englishName : true,
														rangelength : [0, 100]										
													}
											    },
											    messages : {
													jobDutyCode : {
														required : "<ikep4j:message key="NotNull.jobDuty.jobDutyCode" />",
														rangelength : "<ikep4j:message key="Size.jobDuty.jobDutyCode" />"
													},
													jobDutyName : {
														required : "<ikep4j:message key="NotNull.jobDuty.jobDutyName" />",
														rangelength : "<ikep4j:message key="Size.jobDuty.jobDutyName" />"
													},
													jobDutyEnglishName : {
														required : "<ikep4j:message key="NotNull.jobDuty.jobDutyEnglishName" />",
														englishName : "<ikep4j:message key="EnglishName.jobDuty.jobDutyEnglishName" />",
														rangelength : "<ikep4j:message key="Size.jobDuty.jobDutyEnglishName" />"
													}
											    },
											    submitHandler : function(jobDuty) {
											    	
											    	// 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
													if (($jq("#codeCheck").val() == 'success') || 
															($jq("#codeCheck").val() == 'modify')) {
														$jq.ajax({
															url : '<c:url value="createJobDuty.do" />',
															data : $jq("#jobDuty").serialize(),
															type : "post",
															success : function(result) {
																alert("<ikep4j:message pre="${jobDutyListPrefix}" key="alert.saveCompleted" />");
																$jq("#tempCode").val(result);
																getList();
															},
					 										error : function(xhr, exMessage) {
															var errorItems = $.parseJSON(xhr.responseText).exception;
															validator.showErrors(errorItems);
															}
														});
													} else {
														alert("<ikep4j:message pre="${jobDutyListPrefix}" key="alert.checkDuplicaed" />");
														return;
													}
												}

											 };

										var validator = new iKEP.Validator("#jobDuty", validOptions);
									});
								//-->
								</script>
								
								<form id="jobDuty" name="jobDuty" method="post" onsubmit="return false;" action="">
								
									<input type="hidden" id="codeCheck" name="codeCheck" value="${jobDuty.codeCheck}"/>
								
									<table summary="<ikep4j:message pre="${jobDutyUiPrefix}" key="tableSummary" />">
									<caption></caption>
										<colgroup>
											<col width="15%"/>
											<col width="10%"/>
											<col width="75%"/>
										</colgroup>								
									<tbody>
										<tr>
											<th scope="row" colspan="2">
												<span class="colorPoint">*</span> <ikep4j:message pre="${jobDutyUiPrefix}" key="jobDutyCode" />
											</th>
											<td>
												<div>
													<input type="text" name="jobDutyCode" id="jobDutyCode" value="${jobDuty.jobDutyCode}" size="50" class="inputbox" />
													&nbsp;<a class="button_ic" id="checkCodeButton" href="#a"><span><ikep4j:message pre="${jobDutyUiPrefix}" key="checkDuplicated" /></span></a>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row" rowspan="2"><span class="colorPoint">*</span> <ikep4j:message pre="${jobDutyUiPrefix}" key="jobDutyName" /></th>
											<th><span class="colorPoint">*</span> <ikep4j:message pre="${jobDutyUiPrefix}" key="jobDutyName" /></th>
											<td>
												<div>
													<input type="text" name="jobDutyName" id="jobDutyName" value="${jobDuty.jobDutyName}" size="50" class="inputbox" />
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="colorPoint">*</span> <ikep4j:message pre="${jobDutyUiPrefix}" key="jobDutyEnglishName" /></th>
											<td>
												<div>
													<input type="text" name="jobDutyEnglishName" id="jobDutyEnglishName" value="${jobDuty.jobDutyEnglishName}" size="50" class="inputbox" />
												</div>
											</td>
										</tr>
									</tbody>
									
									</table>
									
									<input type="hidden" id="sortOrder" name="sortOrder" value="${jobDuty.sortOrder}"/> 
									<input type="hidden" id="oldSortOrder" name="oldSortOrder" value="${oldSortOrder}"/>
									
								</form>