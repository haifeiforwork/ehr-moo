<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="jobTitleListPrefix" value="message.portal.admin.code.jobTitle.jobTitleList"/>
<c:set var="jobTitleUiPrefix" value="ui.portal.admin.code.jobTitle"/>

								<script type="text/javascript" language="javascript">
								//<!--
									$jq(document).ready(function() {
										$jq("#jobTitle :input:visible:enabled:first").focus().select();
										
										// ID 중복을 체크함
										$jq("#checkCodeButton").click(function() {
											checkCode();
										});
										
										$jq("input[name=jobTitleCode]").change(function() {
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
													jobTitleCode : {
														required : true,
														rangelength : [0, 20]
													},
													jobTitleName : {
														required : true,
														rangelength : [0, 20]
													},
													jobTitleEnglishName : {
														required : true,
														englishName : true,
														rangelength : [0, 100]										
													},
				                                    jobTitleDescription : {
				                                        required : true,                                       
				                                        rangelength : [0, 100]                                      
				                                    }
											    },
											    messages : {
													jobTitleCode : {
														required : "<ikep4j:message key="NotNull.jobTitle.jobTitleCode" />",
														rangelength : "<ikep4j:message key="Size.jobTitle.jobTitleCode" />"
													},
													jobTitleName : {
														required : "<ikep4j:message key="NotNull.jobTitle.jobTitleName" />",
														rangelength : "<ikep4j:message key="Size.jobTitle.jobTitleName" />"
													},
													jobTitleEnglishName : {
														required : "<ikep4j:message key="NotNull.jobTitle.jobTitleEnglishName" />",
														englishName : "<ikep4j:message key="EnglishName.jobTitle.jobTitleEnglishName" />",
														rangelength : "<ikep4j:message key="Size.jobTitle.jobTitleEnglishName" />"
													},
				                                    jobTitleDescription : {
				                                        required : "<ikep4j:message key="NotNull.jobTitle.jobTitleDescription" />",                                        
				                                        rangelength : "<ikep4j:message key="Size.jobTitle.jobTitleDescription" />"
				                                    }
											    },
											    submitHandler : function(jobTitle) {
											    	
											    	// 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
													if (($jq("#codeCheck").val() == 'success') || 
															($jq("#codeCheck").val() == 'modify')) {
														$jq.ajax({
															url : '<c:url value="createJobTitle.do" />',
															data : $jq("#jobTitle").serialize(),
															type : "post",
															success : function(result) {
																alert("<ikep4j:message pre="${jobTitleListPrefix}" key="alert.saveCompleted" />");
																$jq("#tempCode").val(result);
																getList();
															},
					 										error : function(xhr, exMessage) {
															var errorItems = $.parseJSON(xhr.responseText).exception;
															validator.showErrors(errorItems);
															}
														});
													} else {
														alert("<ikep4j:message pre="${jobTitleListPrefix}" key="alert.checkDuplicaed" />");
														return;
													}
												}

											 };

										var validator = new iKEP.Validator("#jobTitle", validOptions);
									});
								//-->
								</script>
								
								<form id="jobTitle" name="jobTitle" method="post" onsubmit="return false;" action="">
								
									<input type="hidden" id="codeCheck" name="codeCheck" value="${jobTitle.codeCheck}"/>
								
									<table summary="<ikep4j:message pre="${jobTitleUiPrefix}" key="tableSummary" />">
									<caption></caption>
										<colgroup>
											<col width="15%"/>
											<col width="10%"/>
											<col width="75%"/>
										</colgroup>
									<tbody>
										<tr>
											<th scope="row" colspan="2">
												<span class="colorPoint">*</span> <ikep4j:message pre="${jobTitleUiPrefix}" key="jobTitleCode" />
											</th>
											<td>
												<div>
													<input type="text" name="jobTitleCode" id="jobTitleCode" value="${jobTitle.jobTitleCode}" size="50" class="inputbox" />
													&nbsp;<a class="button_ic" id="checkCodeButton" href="#a"><span><ikep4j:message pre="${jobTitleUiPrefix}" key="checkDuplicated" /></span></a>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row" rowspan="3">
												<span class="colorPoint">*</span> <ikep4j:message pre="${jobTitleUiPrefix}" key="jobTitleName" />
											</th>
											<th><span class="colorPoint">*</span> <ikep4j:message pre="${jobTitleUiPrefix}" key="jobTitleName" /></th>
											<td>
												<div>
													<input type="text" name="jobTitleName" id="jobTitleName" value="${jobTitle.jobTitleName}" size="50" class="inputbox"/>
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="colorPoint">*</span> <ikep4j:message pre="${jobTitleUiPrefix}" key="jobTitleEnglishName" /></th>
											<td>
												<div>
													<input type="text" name="jobTitleEnglishName" id="jobTitleEnglishName" value="${jobTitle.jobTitleEnglishName}" size="50" class="inputbox" />
												</div>
											</td>
										</tr>
										<tr>
                                            <th><span class="colorPoint">*</span> <ikep4j:message pre="${jobTitleUiPrefix}" key="jobTitleDescription" /></th>
                                            <td>
                                                <div>
                                                    <input type="text" name="jobTitleDescription" id="jobTitleDescription" value="${jobTitle.jobTitleDescription}" size="50" class="inputbox" />
                                                </div>
                                            </td>
                                        </tr>
									</tbody>
									
									</table>
									
									<input type="hidden" id="sortOrder" name="sortOrder" value="${jobTitle.sortOrder}"/> 
									<input type="hidden" id="oldSortOrder" name="oldSortOrder" value="${oldSortOrder}"/>
									
								</form>