<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="groupTypeListPrefix" value="message.portal.admin.group.groupType.groupTypeList"/>
<c:set var="groupTypeUiPrefix" value="ui.portal.admin.group.groupType"/>

								<script type="text/javascript" language="javascript">
								//<!--
									$jq(document).ready(function() {
										
										$jq("#groupType input:visible:enabled:first").focus().select();
										
										// ID 중복을 체크함
										$jq("#checkIdButton").click(function() {
											checkId();
										});
										
										// code 값이 바뀌면 중복체크를 위해 codeCheck 값을 바꿔줌
										$jq("input[name=groupTypeId]").change(function() {
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
													groupTypeId : {
														required : true,
														rangelength : [0, 20]
													},
													groupTypeName : {
														required : true,
														rangelength : [0, 20]
													},
													groupTypeEnglishName : {
														required : true,
														englishName : true,
														rangelength : [0, 100]
													}
												},
												messages : {
													groupTypeId : {
														required : "<ikep4j:message key="NotNull.groupType.groupTypeId" />",
														rangelength : "<ikep4j:message key="Size.groupType.groupTypeId" />"
													},
													groupTypeName : {
														required : "<ikep4j:message key="NotNull.groupType.groupTypeName" />",
														rangelength : "<ikep4j:message key="Size.groupType.groupTypeName" />"
													},
													groupTypeEnglishName : {
														required : "<ikep4j:message key="NotNull.groupType.groupTypeEnglishName" />",
														englishName : "<ikep4j:message key="EnglishName.groupType.groupTypeEnglishName" />",
														rangelength : "<ikep4j:message key="Size.groupType.groupTypeEnglishName" />"
													}
												},
												submitHandler : function(groupType) {
											    	
											    	// 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
													if (($jq("#codeCheck").val() == "success")||
															($jq("#codeCheck").val() == "modify")) {
														$jq.ajax({
															url : '<c:url value="createGroupType.do" />',
															data : $jq("#groupType").serialize(),
															type : "post",
															success : function(result) {
																alert("<ikep4j:message pre="${groupTypeListPrefix}" key="alert.saveCompleted" />");
																$jq("#tempId").val(result);
																getList();
															},
					 										error : function(xhr, exMessage) {
																var errorItems = $.parseJSON(xhr.responseText).exception;
																validator.showErrors(errorItems);
															}
														});
													} else {
														alert("<ikep4j:message pre="${groupTypeListPrefix}" key="alert.checkDuplicaed" />");
														return;
													}
												}
											};
											
											var validator = new iKEP.Validator("#groupType", validOptions);
											
									});
								//-->
								</script>
								
								<form id="groupType" name="groupType" method="post" onsubmit="return false;" action="">
							
									<input type="hidden" id="codeCheck" name="codeCheck" value="${groupType.codeCheck}"/>
								
									<table summary="<ikep4j:message pre="${groupTypeUiPrefix}" key="tableSummary" />">
									<caption></caption>
										<colgroup>
											<col width="10%"/>
											<col width="15%"/>
											<col width="75%"/>
										</colgroup>
									<tbody>
										<tr>
											<th scope="row" colspan="2">
												<span class="colorPoint">*</span> <ikep4j:message pre="${groupTypeUiPrefix}" key="groupTypeId" />
											</th>
											<td>
												<div>
													<input type="text" name="groupTypeId" id="groupTypeId" value="${groupType.groupTypeId}" size="50" class="inputbox"/> 
														&nbsp;<a href="#a" id="checkIdButton" class="button_ic"><span><ikep4j:message pre="${groupTypeUiPrefix}" key="checkDuplicated" /></span></a>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row" rowspan="2"><span class="colorPoint">*</span> <ikep4j:message pre="${groupTypeUiPrefix}" key="groupTypeName" /></th>
											<th><span class="colorPoint">*</span> <ikep4j:message pre="${groupTypeUiPrefix}" key="groupTypeName" /></th>
											<td>
												<div>
													<input type="text" name="groupTypeName" id="groupTypeName" value="${groupType.groupTypeName}" size="50" class="inputbox"/>
												</div>
											</td>
										</tr>
										<tr>
											<th><span class="colorPoint">*</span> <ikep4j:message pre="${groupTypeUiPrefix}" key="groupTypeEnglishName" /></th>
											<td>
												<div>
													<input type="text" name="groupTypeEnglishName" id="groupTypeEnglishName" value="${groupType.groupTypeEnglishName}" size="50" class="inputbox"/>
												</div>
											</td>
										</tr>
									</tbody>
									
									</table>
								
									<input type="hidden" id="itemTypeCode" name="itemTypeCode" value="PO"/>
									<input type="hidden" id="fieldName" name="fieldName" value="groupTypeName"/>
								
								</form>