<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="jobRankUiPrefix" value="ui.portal.admin.code.jobRank"/>
<c:set var="jobRankListPrefix" value="message.portal.admin.code.jobRank.jobRankList"/>

								<script type="text/javascript" language="javascript">
								//<!--
									$jq(document).ready(function() {
										$jq("#jobRank :input:visible:enabled:first").focus().select();
										
										// ID 중복을 체크함
										$jq("#checkCodeButton").click(function() {
											checkCode();
										});
										
										$jq("input[name=jobRankCode]").change(function() {
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
													jobRankCode : {
														required : true,
														rangelength : [0, 20]
													},
													jobRankName : {
														required : true,
														rangelength : [0, 20]
													},
													jobRankEnglishName : {
														required : true,
														englishName : true,
														rangelength : [0, 100]										
													}
											    },
											    messages : {
													jobRankCode : {
														required : "<ikep4j:message key="NotNull.jobRank.jobRankCode" />",
														rangelength : "<ikep4j:message key="Size.jobRank.jobRankCode" />"
													},
													jobRankName : {
														required : "<ikep4j:message key="NotNull.jobRank.jobRankName" />",
														rangelength : "<ikep4j:message key="Size.jobRank.jobRankName" />"
													},
													jobRankEnglishName : {
														required : "<ikep4j:message key="NotNull.jobRank.jobRankEnglishName" />",
														englishName : "<ikep4j:message key="EnglishName.jobRank.jobRankEnglishName" />",
														rangelength : "<ikep4j:message key="Size.jobRank.jobRankEnglishName" />"
													}
											    },
											    submitHandler : function(jobRank) {
											    	
											    	// 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
													if (($jq("#codeCheck").val() == 'success') || 
															($jq("#codeCheck").val() == 'modify')) {
														$jq.ajax({
															url : '<c:url value="createJobRank.do" />',
															data : $jq("#jobRank").serialize(),
															type : "post",
															success : function(result) {
																alert("<ikep4j:message pre="${jobRankListPrefix}" key="alert.saveCompleted" />");
																$jq("#tempCode").val(result);
																getList();
															},
					 										error : function(xhr, exMessage) {
															var errorItems = $.parseJSON(xhr.responseText).exception;
															validator.showErrors(errorItems);
															}
														});
													} else {
														alert("<ikep4j:message pre="${jobRankListPrefix}" key="alert.checkDuplicaed" />");
														return;
													}
												}

											 };

										var validator = new iKEP.Validator("#jobRank", validOptions);
									});
								//-->
								</script>
								
								<form id="jobRank" name="jobRank" method="post" onsubmit="return false;" action="">
								
									<input type="hidden" id="codeCheck" name="codeCheck" value="${jobRank.codeCheck}"/>
								
									<table summary="<ikep4j:message pre="${jobRankUiPrefix}" key="tableSummary" />">
									<caption></caption>
										<colgroup>
											<col width="15%"/>
											<col width="10%"/>
											<col width="75%"/>
										</colgroup>								
									<tbody>
										<tr>
											<th scope="row" colspan="2">
												<span class="colorPoint">*</span> <ikep4j:message pre="${jobRankUiPrefix}" key="jobRankCode" />
											</th>
											<td>
												<div>
													<input type="text" name="jobRankCode" id="jobRankCode" value="${jobRank.jobRankCode}" size="50" class="inputbox" />
													&nbsp;<a class="button_ic" id="checkCodeButton" href="#a"><span><ikep4j:message pre="${jobRankUiPrefix}" key="checkDuplicated" /></span></a>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row" rowspan="2"><span class="colorPoint">*</span> <ikep4j:message pre="${jobRankUiPrefix}" key="jobRankName" /></th>
											<th><span class="colorPoint">*</span> <ikep4j:message pre="${jobRankUiPrefix}" key="jobRankName" /></th>
											<td>
												<div>
													<input type="text" name="jobRankName" id="jobRankName" value="${jobRank.jobRankName}" size="50" class="inputbox" />
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="colorPoint">*</span> <ikep4j:message pre="${jobRankUiPrefix}" key="jobRankEnglishName" /></th>
											<td>
												<div>
													<input type="text" name="jobRankEnglishName" id="jobRankEnglishName" value="${jobRank.jobRankEnglishName}" size="50" class="inputbox" />
												</div>
											</td>
										</tr>
									</tbody>
									
									</table>
									
									<input type="hidden" id="sortOrder" name="sortOrder" value="${jobRank.sortOrder}"/> 
									<input type="hidden" id="oldSortOrder" name="oldSortOrder" value="${oldSortOrder}"/>
									
								</form>