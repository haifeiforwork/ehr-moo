<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="jobClassListPrefix" value="message.portal.admin.code.jobClass.jobClassList"/>
<c:set var="jobClassUiPrefix" value="ui.portal.admin.code.jobClass"/>

								<script type="text/javascript" language="javascript">
								//<!--
									$jq(document).ready(function() {
										$jq("#jobClass :input:visible:enabled:first").focus().select();
										
										// ID 중복을 체크함
										$jq("#checkCodeButton").click(function() {
											checkCode();
										});
										
										$jq("input[name=jobClassCode]").change(function() {
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
													jobClassCode : {
														required : true,
														rangelength : [0, 20]
													},
													jobClassName : {
														required : true,
														rangelength : [0, 20]
													},
													jobClassEnglishName : {
														required : true,
														englishName : true,
														rangelength : [0, 100]										
													}
											    },
											    messages : {
													jobClassCode : {
														required : "<ikep4j:message key="NotNull.jobClass.jobClassCode" />",
														rangelength : "<ikep4j:message key="Size.jobClass.jobClassCode" />"
													},
													jobClassName : {
														required : "<ikep4j:message key="NotNull.jobClass.jobClassName" />",
														rangelength : "<ikep4j:message key="Size.jobClass.jobClassName" />"
													},
													jobClassEnglishName : {
														required : "<ikep4j:message key="NotNull.jobClass.jobClassEnglishName" />",
														englishName : "<ikep4j:message key="EnglishName.jobClass.jobClassEnglishName" />",
														rangelength : "<ikep4j:message key="Size.jobClass.jobClassEnglishName" />"
													}
											    },
											    submitHandler : function(jobClass) {
											    	
											    	// 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
													if (($jq("#codeCheck").val() == 'success') || 
															($jq("#codeCheck").val() == 'modify')) {
														$jq.ajax({
															url : '<c:url value="createJobClass.do" />',
															data : $jq("#jobClass").serialize(),
															type : "post",
															success : function(result) {
																alert("<ikep4j:message pre="${jobClassListPrefix}" key="alert.saveCompleted" />");
																$jq("#tempCode").val(result);
																getList();
															},
					 										error : function(xhr, exMessage) {
															var errorItems = $.parseJSON(xhr.responseText).exception;
															validator.showErrors(errorItems);
															}
														});
													} else {
														alert("<ikep4j:message pre="${jobClassListPrefix}" key="alert.checkDuplicaed" />");
														return;
													}
												}

											 };

										var validator = new iKEP.Validator("#jobClass", validOptions);
									});
								//-->
								</script>
								
								<form id="jobClass" name="jobClass" method="post" onsubmit="return false" action="">
								
									<input type="hidden" id="codeCheck" name="codeCheck" value="${jobClass.codeCheck}"/>
								
									<table summary="<ikep4j:message pre="${jobClassUiPrefix}" key="tableSummary" />">
									<caption></caption>
										<colgroup>
											<col width="15%"/>
											<col width="10%"/>
											<col width="75%"/>
										</colgroup>								
									<tbody>
										<tr>
											<th scope="row" colspan="2">
												<span class="colorPoint">*</span> <ikep4j:message pre="${jobClassUiPrefix}" key="jobClassCode" />
											</th>
											<td>
												<div>
													<input type="text" name="jobClassCode" id="jobClassCode" value="${jobClass.jobClassCode}" size="50" class="inputbox"/>
													&nbsp;<a class="button_ic" id="checkCodeButton" href="#a"><span><ikep4j:message pre="${jobClassUiPrefix}" key="checkDuplicated" /></span></a>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row" rowspan="2"><span class="colorPoint">*</span> <ikep4j:message pre="${jobClassUiPrefix}" key="jobClassName" /></th>
											<th><span class="colorPoint">*</span> <ikep4j:message pre="${jobClassUiPrefix}" key="jobClassName" /></th>
											<td>
												<div>
													<input type="text" name="jobClassName" id="jobClassName" value="${jobClass.jobClassName}" size="50" class="inputbox" />
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="colorPoint">*</span> <ikep4j:message pre="${jobClassUiPrefix}" key="jobClassEnglishName" /></th>
											<td>
												<div>
													<input type="text" name="jobClassEnglishName" id="jobClassEnglishName" value="${jobClass.jobClassEnglishName}" size="50" class="inputbox"/>
												</div>
											</td>
										</tr>
									</tbody>
									
									</table>
									
									<input type="hidden" id="sortOrder" name="sortOrder" value="${jobClass.sortOrder}"/>
									<input type="hidden" id="oldSortOrder" name="oldSortOrder" value="${oldSortOrder}"/>
									
								</form>