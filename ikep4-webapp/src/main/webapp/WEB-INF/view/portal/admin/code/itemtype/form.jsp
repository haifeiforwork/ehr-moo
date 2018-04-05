<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="itemTypeListPrefix" value="message.portal.admin.code.itemType.itemTypeList"/>
<c:set var="itemTypeUiPrefix" value="ui.portal.admin.code.itemType"/>

								<script type="text/javascript" language="javascript">
								//<!--
									$jq(document).ready(function() {
										
										$jq("#itemType :input:visible:enabled:first").focus().select();
										
										// ID 중복을 체크함
										$jq("#checkCodeButton").click(function() {
											checkCode();
										});
										
										// code 값이 바뀌면 중복체크를 위해 codeCheck 값을 바꿔줌
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
													itemTypeCode : {
														required : true,
														rangelength : [0, 25]
													},
													itemTypeName : {
														required : true,
														rangelength : [0, 100]
													},
													displayName : {
														rangelength : [0, 65]
													},
													thumbnailWidthSize : {
														digits : true,
														range : [0, 140]
													},
													thumbnailHeightSize : {
														digits : true,
														range : [0, 140]
													}
											    },
											    messages : {
											    	itemTypeCode : {
														required : "<ikep4j:message key="NotNull.itemType.itemTypeCode" />",
														rangelength : "<ikep4j:message key="Size.itemType.itemTypeCode" />"
													},
													itemTypeName : {
														required : "<ikep4j:message key="NotNull.itemType.itemTypeName" />",
														rangelength : "<ikep4j:message key="Size.itemType.itemTypeName" />"
													},
													displayName : {
														rangelength : "<ikep4j:message key="Size.itemType.displayName" />"
													},
													thumbnailWidthSize : {
														digits : "<ikep4j:message key="Digits.itemType.thumbnailWidthSize" />",
														range : "<ikep4j:message key="Size.itemType.thumbnailWidthSize" />"
													},
													thumbnailHeightSize : {
														digits : "<ikep4j:message key="Digits.itemType.thumbnailHeightSize" />",
														range : "<ikep4j:message key="Size.itemType.thumbnailHeightSize" />"
													}
											    },
											    submitHandler : function(itemType) {
											    	
													if ($jq("#codeCheck").val() == "success" || $jq("#codeCheck").val() == "modify") {
														if($jq("#thumbnailWidthSize").val().length < 1) $jq("#thumbnailWidthSize").val("0");
														if($jq("#thumbnailHeightSize").val().length < 1) $jq("#thumbnailHeightSize").val("0");
														
														$jq.ajax({
															url : '<c:url value="createItemType.do" />',
															data : $jq("#itemType").serialize(),
															type : "post",
															success : function(result) {
																alert("<ikep4j:message pre="${itemTypeListPrefix}" key="alert.saveCompleted" />");
																$jq("#tempCode").val(result);
																getList();
															},
					 										error : function(xhr, exMessage) {
																var errorItems = $.parseJSON(xhr.responseText).exception;
																validator.showErrors(errorItems);
															}
														});
													} else {
														alert("<ikep4j:message pre="${itemTypeListPrefix}" key="alert.checkDuplicaed" />");
														return;
													}
												}

											 };

										var validator = new iKEP.Validator("#itemType", validOptions);
									});
								//-->
								</script>
								
								<form id="itemType" name="itemType" onsubmit="return false;" action="">
								
									<input type="hidden" id="codeCheck" name="codeCheck" value="${itemType.codeCheck}"/>
								
									<table summary="<ikep4j:message pre="${itemTypeUiPrefix}" key="tableSummary" />">
									<caption></caption>	
										<colgroup>
											<col width="18%"/>
											<col width="82%"/>
										</colgroup>
									<tbody>
	
										<tr>
											<th scope="row" >
												<span class="colorPoint">*</span> <ikep4j:message pre="${itemTypeUiPrefix}" key="itemTypeCode" />
											</th>
											<td>
												<div>
													<input type="text" name="itemTypeCode" id="itemTypeCode" value="${itemType.itemTypeCode}" size="50" class="inputbox"/> 
													&nbsp;<a class="button_ic" id="checkCodeButton" href="#a"><span><ikep4j:message pre="${itemTypeUiPrefix}" key="checkDuplicated" /></span></a>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row" >
												<span class="colorPoint">*</span> <ikep4j:message pre="${itemTypeUiPrefix}" key="itemTypeName" />
											</th>
											<td>
												<div>
													<input type="text" name="itemTypeName" id="itemTypeName" value="${itemType.itemTypeName}" size="50" class="inputbox"/>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row" ><ikep4j:message pre="${itemTypeUiPrefix}" key="displayName" /></th>
											<td>
												<div>
													<input type="text" name="displayName" id="displayName" value="${itemType.displayName}" size="50" class="inputbox"/>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row" ><ikep4j:message pre="${itemTypeUiPrefix}" key="thumbnailWidthSize" /></th>
											<td>
												<div>
													<input type="text" name="thumbnailWidthSize" id="thumbnailWidthSize" value="${itemType.thumbnailWidthSize}" size="50" class="inputbox"/>
												</div> 
											</td>
										</tr>
										<tr>
											<th scope="row" ><ikep4j:message pre="${itemTypeUiPrefix}" key="thumbnailHeightSize" /></th>
											<td>
												<div>
													<input type="text" name="thumbnailHeightSize" id="thumbnailHeightSize" value="${itemType.thumbnailHeightSize}" size="50" class="inputbox"/>
												</div> 
											</td>
										</tr>
									</tbody>
									
									</table>
								
								</form>