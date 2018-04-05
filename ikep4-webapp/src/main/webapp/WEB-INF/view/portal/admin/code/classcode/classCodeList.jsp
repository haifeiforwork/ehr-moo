<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
<c:set var="classCodeUiPrefix" value="ui.portal.admin.code.classCode"/>
<c:set var="classCodeListPrefix" value="message.portal.admin.code.classCode.classCodeList"/> 

				<script type="text/javascript" language="javascript">
				//<!--
				
					// 상단에 보이게 될 리스트를 가져오는 함수
					function getList() {
						$jq("#searchForm").attr("action", "<c:url value='getList.do' />");
						$jq("#searchForm")[0].submit();
					}
				
					// 하단에 보이게 될 상세정보를 가져오는 함수
					function getView(classId, fieldName, itemTypeCode, tr) {
						
						var selectClassName = "bgSelected";
						$jq(tr).parent().parent().children().removeClass(selectClassName).end()
								.end().end().addClass(selectClassName);
				
						$jq.ajax({
							url : '<c:url value="getForm.do" />',
							data : {
								id : classId,
								fieldName : fieldName,
								itemTypeCode : itemTypeCode
							},
							type : "get",
							success : function(result) {
								$jq("#viewDiv").html(result);
							}
						});
					}
					
					// ID 중복을 체크함
					function checkId() {
						if($jq("#classId").val()=='') {
							alert("<ikep4j:message pre="${classCodeListPrefix}" key="alert.noCheckValue" />");
						} else {
							$jq.ajax({
								url : '<c:url value="checkId.do" />', 
								data : {id: $jq("#classId").val()}, 
								type : "post",
								success : function(result) {     
									
									if(result == 'duplicated') {
										alert("<ikep4j:message pre="${classCodeListPrefix}" key="alert.isDuplicated" />");
									} else {
										alert("<ikep4j:message pre="${classCodeListPrefix}" key="alert.isAvailable" />");
										$jq("#codeCheck").val("success");
									}
								} 
							});
						}
					}
					
					// ID를 삭제함
					function deleteForm() {
						if ($jq("#classId").val() == "") {
							alert("<ikep4j:message pre="${classCodeListPrefix}" key="alert.cantDelete" />");
							return;
						}
						
						if(confirm("<ikep4j:message pre="${classCodeListPrefix}" key="confirm.wannaDelete" />")) {
							$jq.ajax({
								url : '<c:url value="deleteClassCode.do" />',
								data : $jq("#classCode").serialize(),
								type : "post",
								success : function(result) {
									alert("<ikep4j:message pre="${classCodeListPrefix}" key="alert.deleteCompleted" />");
									getList();
								},
								error:function(){
									alert("<ikep4j:message pre="${classCodeListPrefix}" key="alert.failedToDelete" />");
								}
							});
						} else {
							return;
						}
					}

					function sort(sortColumn, sortType) {
						$jq("input[name=sortColumn]").val(sortColumn);
						
						if( sortType == 'ASC') {
							$jq("input[name=sortType]").val('DESC');	
						} else {
							$jq("input[name=sortType]").val('ASC');
						}
						
						getList();
					};
					
					// 페이지 로딩시 실행하는 jQuery 코드로 list와 view를 불러온다.
					$jq(document).ready(function() {
						
						//left menu setting
						$jq("#classManageOfLeft").click();
				
						// 페이지 로딩 직후 폼의 맨 첫번째 input 박스에 포커스
						$jq("#classCode :input:visible:enabled:first").focus().select();
						
						$jq("#newFormButton").click(function() {
							$jq("#codeCheck").val("");
							getView();
						});
						
						$jq("#submitButton").click(function() {
							$jq("#classCode").trigger("submit");
						});
						
						$jq("#deleteButton").click(function() {
							deleteForm();
						});
						
						// ID 중복을 체크함
						$jq("#checkIdButton").click(function() {
							checkId();
						});
						
						$jq("#searchBoardItemButton").click(function() {   
							getList();
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
				
					<!--blockListTable Start-->
					<div class="blockListTable">
						<div id="listDiv">
							<form id="searchForm" name="searchForm" method="post" onsubmit="getList(); return false;" action="">  
							
								<spring:bind path="searchCondition.sortColumn">
									<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
								</spring:bind>
								<spring:bind path="searchCondition.sortType">
									<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
								</spring:bind>
								
								<!--tableTop Start-->  
								<div class="tableTop">
									<div class="tableTop_bgR"></div>
									<h2><ikep4j:message pre="${classCodeUiPrefix}" key="pageTitle" /></h2>
									<div class="listInfo"> 
										<spring:bind path="searchCondition.pagePerRecord">  
											<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />' onchange="javascript:getList()">
												<c:forEach var="code" items="${boardCode.pageNumList}">
													<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
												</c:forEach> 
											</select> 
										</spring:bind>
										<div class="totalNum">
											${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> 
											( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)
										</div>
									</div>
									<div class="tableSearch"> 
										<spring:bind path="searchCondition.searchColumn">  
											<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
												<option value="id" <c:if test="${'id' eq status.value}">selected="selected"</c:if> >
													<ikep4j:message pre="${classCodeUiPrefix}" key="classId" />
												</option>
												<option value="title" <c:if test="${'title' eq status.value}">selected="selected"</c:if> >
													<ikep4j:message pre="${classCodeUiPrefix}" key="className" />
												</option>
											</select>	
										</spring:bind>		
										<spring:bind path="searchCondition.searchWord">  					
											<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
										</spring:bind>
										<a id="searchBoardItemButton" name="searchBoardItemButton" href="#a" class="ic_search">
											<span><ikep4j:message pre='${preSearch}' key='search' /></span>
										</a> 
									</div>
									<div class="clear"></div>	
								</div>
								<!--//tableTop End-->
								
								<table summary="<ikep4j:message pre="${classCodeUiPrefix}" key="tableSummary" />">
									<caption></caption>
										<colgroup>
											<col width="10%"/>
											<col width="20%"/>
											<col width="30%"/>
											<col width="40%"/>
										</colgroup>
									<thead>
										<tr>
											<th scope="col"><ikep4j:message pre="${classCodeUiPrefix}" key="num" /></th>
											<th scope="col">
												<a onclick="javascript:sort('CLASS_ID', '<c:if test="${searchCondition.sortColumn eq 'CLASS_ID'}">${searchCondition.sortType}</c:if>');"  href="#a">
												<ikep4j:message pre="${classCodeUiPrefix}" key="classId" />
												</a>
											</th>
											<th scope="col">
												<a onclick="javascript:sort('CLASS_NAME', '<c:if test="${searchCondition.sortColumn eq 'CLASS_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
												<ikep4j:message pre="${classCodeUiPrefix}" key="className" />
												</a>
											</th>
											<th scope="col"><ikep4j:message pre="${classCodeUiPrefix}" key="description" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${searchResult.emptyRecord}">
												<tr>
													<td colspan="4" class="emptyRecord"><ikep4j:message pre='${classCodeListPrefix}' key='emptyRecord' /></td> 
												</tr>
										    </c:when>
										    <c:otherwise>
												<c:forEach var="classCode" items="${searchResult.entity}" varStatus="status">
													<tr>
														<td class="textCenter">${classCode.num}</td>
														<td class="textCenter"><a href="#a" onclick="getView('${classCode.classId}', 'className', 'PO', this)">${classCode.classId}</a></td>
														<td class="textCenter"><a href="#a" onclick="getView('${classCode.classId}', 'className', 'PO', this)">${classCode.className}</a></td>
														<td class="textCenter"><a href="#a" onclick="getView('${classCode.classId}', 'className', 'PO', this)">${classCode.description}</a></td>
													</tr>
												</c:forEach>
										    </c:otherwise>
										</c:choose>	
									</tbody>
								</table>
							
								<!--Page Number Start--> 
								<spring:bind path="searchCondition.pageIndex">
									<ikep4j:pagination searchButtonId="searchBoardItemButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
									<input id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
								</spring:bind> 
								<!--//Page Number End-->
								
								<input type="hidden" name="tempId" id="tempId" value=""	/>
								
							</form>
						</div>
					</div>
					<!--//blockListTable End-->
				
				<div class="blockBlank_10px"></div>
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre="${classCodeUiPrefix}" key="subTitle.detail" /></h3>
				</div>
				<!--//subTitle_2 End--> 
				
					<!--blockDetail Start-->
					<div class="blockDetail">
						<div id="viewDiv">
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
						</div>
					</div>
					<!--//blockDetail End--> 
				
				<!--blockButton Start-->
				<div class="blockButton">
					<ul>
						<li><a class="button" id="newFormButton" href="#a"><span><ikep4j:message pre="${classCodeUiPrefix}" key="button.new" /></span></a></li>
						<li><a class="button" id="submitButton" href="#a"><span><ikep4j:message pre="${classCodeUiPrefix}" key="button.save" /></span></a></li>
						<li><a class="button" id="deleteButton" href="#a"><span><ikep4j:message pre="${classCodeUiPrefix}" key="button.delete" /></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->