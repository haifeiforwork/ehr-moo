<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="workPlaceListPrefix" value="message.portal.admin.code.workPlace.workPlaceList"/>
<c:set var="workPlaceUiPrefix" value="ui.portal.admin.code.workPlace"/>

								<script type="text/javascript" language="javascript">
								//<!--
									$jq(document).ready(function() {
										$jq("#workPlace :input:visible:enabled:first").focus().select();
										
										// ID 중복을 체크함
										$jq("#checkCodeButton").click(function() {
											checkCode();
										});
										
										$jq("input[name=workPlaceCode]").change(function() {
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
													workPlaceCode : {
														required : true,
														rangelength : [0, 20]
													},
													workPlaceName : {
														required : true,
														rangelength : [0, 20]
													},
													workPlaceEnglishName : {
														required : true,
														companyEnglishName : true,
														rangelength : [0, 100]										
													}
											    },
											    messages : {
													workPlaceCode : {
														required : "<ikep4j:message key="NotNull.workPlace.workPlaceCode" />",
														rangelength : "<ikep4j:message key="Size.workPlace.workPlaceCode" />"
													},
													workPlaceName : {
														required : "<ikep4j:message key="NotNull.workPlace.workPlaceName" />",
														rangelength : "<ikep4j:message key="Size.workPlace.workPlaceName" />"
													},
													workPlaceEnglishName : {
														required : "<ikep4j:message key="NotNull.workPlace.workPlaceEnglishName" />",
														companyEnglishName : "<ikep4j:message key="EnglishName.workPlace.workPlaceEnglishName" />",
														rangelength : "<ikep4j:message key="Size.workPlace.workPlaceEnglishName" />"
													}
											    },
											    submitHandler : function(workPlace) {
											    	
											    	// 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
													if (($jq("#codeCheck").val() == 'success') || 
															($jq("#codeCheck").val() == 'modify')) {
														$jq.ajax({
															url : '<c:url value="createWorkPlace.do" />',
															data : $jq("#workPlace").serialize(),
															type : "post",
															success : function(result) {
																alert("<ikep4j:message pre="${workPlaceListPrefix}" key="alert.saveCompleted" />");
																$jq("#tempCode").val(result);
																getList();
															},
					 										error : function(xhr, exMessage) {
															var errorItems = $.parseJSON(xhr.responseText).exception;
															validator.showErrors(errorItems);
															}
														});
													} else {
														alert("<ikep4j:message pre="${workPlaceListPrefix}" key="alert.checkDuplicaed" />");
														return;
													}
												}

											 };

										var validator = new iKEP.Validator("#workPlace", validOptions);
									});
								//-->
								</script>
								
								<form id="workPlace" name="workPlace" method="post" onsubmit="return false;" action="">
									
									<input type="hidden" id="codeCheck" name="codeCheck" value="${workPlace.codeCheck}"/>
								
									<table summary="<ikep4j:message pre="${workPlaceUiPrefix}" key="tableSummary" />">
									<caption></caption>
										<colgroup>
											<col width="15%"/>
											<col width="10%"/>
											<col width="75%"/>
										</colgroup>								
									<tbody>
										<tr>
											<th scope="row" colspan="2">
												<span class="colorPoint">*</span> <ikep4j:message pre="${workPlaceUiPrefix}" key="workPlaceCode" />
											</th>
											<td>
												<div>
													<input type="text" name="workPlaceCode" id="workPlaceCode" value="${workPlace.workPlaceCode}" size="50" class="inputbox" />
													&nbsp;<a class="button_ic" id="checkCodeButton" href="#a"><span><ikep4j:message pre="${workPlaceUiPrefix}" key="checkDuplicated" /></span></a>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row" rowspan="2"><span class="colorPoint">*</span> <ikep4j:message pre="${workPlaceUiPrefix}" key="workPlaceName" /></th>
											<th><span class="colorPoint">*</span> <ikep4j:message pre="${workPlaceUiPrefix}" key="workPlaceName" /></th>
											<td>
												<div>
													<input type="text" name="workPlaceName" id="workPlaceName" value="${workPlace.workPlaceName}" size="50" class="inputbox" />
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="colorPoint">*</span> <ikep4j:message pre="${workPlaceUiPrefix}" key="workPlaceEnglishName" /></th>
											<td>
												<div>
													<input type="text" name="workPlaceEnglishName" id="workPlaceEnglishName" value="${workPlace.workPlaceEnglishName}" size="50" class="inputbox" />
												</div>
											</td>
										</tr>
									</tbody>
									
									</table>
									
									<input type="hidden" id="sortOrder" name="sortOrder" value="${workPlace.sortOrder}"/> 
									<input type="hidden" id="oldSortOrder" name="oldSortOrder" value="${oldSortOrder}"/>
									
								</form>