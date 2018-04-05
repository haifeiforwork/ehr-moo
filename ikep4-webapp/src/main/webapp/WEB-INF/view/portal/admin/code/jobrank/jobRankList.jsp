<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
<c:set var="jobRankUiPrefix" value="ui.portal.admin.code.jobRank"/>
<c:set var="jobRankListPrefix" value="message.portal.admin.code.jobRank.jobRankList"/>

				<script type="text/javascript" language="javascript">
				//<!--
				
					// 상단에 보이게 될 리스트를 가져오는 함수
					function getList() {
						$jq("#searchForm").attr("action", "<c:url value='getList.do' />");
						$jq("#searchForm")[0].submit();
					}
				
					// 하단에 보이게 될 상세정보를 가져오는 함수
					function getView(jobRankCode, fieldName, itemTypeCode, tr) {

						var selectClassName = "bgSelected";
						$jq(tr).parent().parent().children().removeClass(selectClassName).end()
								.end().end().addClass(selectClassName);
				
						$jq.ajax({
							url : '<c:url value="getForm.do" />',
							data : {
								code : jobRankCode, 
								fieldName : fieldName,
								itemTypeCode : itemTypeCode
							},
							type : "get",
							success : function(result) {
								$jq("#viewDiv").html(result);
							}
						});
					}
				
					// 코드 중복을 체크함
					function checkCode() {

						if($jq("#jobRankCode").val()=='') {
							alert("<ikep4j:message pre="${jobRankListPrefix}" key="alert.noCheckValue" />");
						} else {
							$jq.ajax({
								url : '<c:url value="checkCode.do" />',     
								data : {code: $jq("#jobRankCode").val()},     
								type : "post",
								success : function(result) {     
									
									if(result == 'duplicated') {
										alert("<ikep4j:message pre="${jobRankListPrefix}" key="alert.isDuplicated" />");
										$jq("#codeCheck").val("modify");
									} else {
										alert("<ikep4j:message pre="${jobRankListPrefix}" key="alert.isAvailable" />");
										$jq("#codeCheck").val("success");
									}
								}
							});
						}
					}
					
					// 코드를 삭제함
					function deleteForm() {
						if ($jq("#jobRankCode").val() == "") {
							alert("<ikep4j:message pre="${jobRankListPrefix}" key="alert.cantDelete" />");
							return;
						}
						
						if(confirm("<ikep4j:message pre="${jobRankListPrefix}" key="confirm.wannaDelete" />")) {
							$jq.ajax({
								url : '<c:url value="deleteJobRank.do" />',
								data : $jq("#jobRank").serialize(),
								type : "post",
								success : function(result) {
									alert("<ikep4j:message pre="${jobRankListPrefix}" key="alert.deleteCompleted" />");
									getList();
								},
								error:function(){
									alert("<ikep4j:message pre="${jobRankListPrefix}" key="alert.failedToDelete" />");
								}
							});
						} else {
							return;
						}
					}
					
					// 순서를 위로
					function setUp(jobRankCode, sortOrder) {
						$jq.ajax({     
							url : '<c:url value="goUp.do" />',     
							data : {code: jobRankCode, sortOrder: sortOrder},     
							type : "post",     
							success : function(result) {
								getList();
							} 
						});  
					}
					
					// 순서를 아래로
					function setDown(jobRankCode, sortOrder) {
						
						$jq.ajax({     
							url : '<c:url value="goDown.do" />',     
							data : {code: jobRankCode, sortOrder: sortOrder},     
							type : "post",     
							success : function(result) {         
								getList();
							} 
						});  
					}
					
					// 소팅을 위한 함수
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
						$jq("#jobrankOfLeft").click();
						
						// 페이지 로딩 직후 폼의 맨 첫번째 input 박스에 포커스
						$jq("#jobRank :input:visible:enabled:first").focus().select();
						
						$jq("#newFormButton").click(function() {
							$jq("#codeCheck").val("");
							getView();
						});
						
						$jq("#submitButton").click(function() {
							$jq("#jobRank").trigger("submit");
						});
						
						$jq("#deleteButton").click(function() {
							deleteForm();
						});
						
						// ID 중복을 체크함
						$jq("#checkCodeButton").click(function() {
							checkCode();
						});
						
						$jq("#searchBoardItemButton").click(function() {   
							getList();
						});
						
						// code 값이 바뀌면 중복체크를 위해 codeCheck 값을 바꿔줌
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
									<h2><ikep4j:message pre="${jobRankUiPrefix}" key="pageTitle" /></h2>
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
												<option value="code" <c:if test="${'code' eq status.value}">selected="selected"</c:if> >
													<ikep4j:message pre="${jobRankUiPrefix}" key="jobRankCode" />
												</option>
												<option value="title" <c:if test="${'title' eq status.value}">selected="selected"</c:if> >
													<ikep4j:message pre="${jobRankUiPrefix}" key="jobRankName" />
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
								
								<table summary="<ikep4j:message pre="${jobRankUiPrefix}" key="tableSummary" />">
									<caption></caption>
										<colgroup>
											<col width="10%"/>
											<col width="20%"/>
											<col width="30%"/>
											<col width="30%"/>
											<col width="10%"/>
										</colgroup>
									<thead>
										<tr>
											<th scope="col">
<%-- 												<a onclick="javascript:sort('SORT_ORDER', '<c:if test="${searchCondition.sortColumn eq 'SORT_ORDER'}">${searchCondition.sortType}</c:if>');"  href="#a"> --%>
												<ikep4j:message pre="${jobRankUiPrefix}" key="num" />
<%-- 												</a> --%>
											</th>
											<th scope="col">
<%-- 												<a onclick="javascript:sort('JOB_RANK_CODE', '<c:if test="${searchCondition.sortColumn eq 'JOB_RANK_CODE'}">${searchCondition.sortType}</c:if>');"  href="#a"> --%>
												<ikep4j:message pre="${jobRankUiPrefix}" key="jobRankCode" />
<%-- 												</a> --%>
											</th>
											<th scope="col">
<%-- 												<a onclick="javascript:sort('JOB_RANK_NAME', '<c:if test="${searchCondition.sortColumn eq 'JOB_RANK_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a"> --%>
												<ikep4j:message pre="${jobRankUiPrefix}" key="jobRankName" />
<%-- 												</a> --%>
											</th>
											<th scope="col">
<%-- 												<a onclick="javascript:sort('JOB_RANK_ENGLISH_NAME', '<c:if test="${searchCondition.sortColumn eq 'JOB_RANK_ENGLISH_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a"> --%>
												<ikep4j:message pre="${jobRankUiPrefix}" key="jobRankEnglishName" />
<%-- 												</a> --%>
											</th>
											<th scope="col"><ikep4j:message pre='${jobRankUiPrefix}' key='sortOrder' /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${searchResult.emptyRecord}">
												<tr>
													<td colspan="5" class="emptyRecord"><ikep4j:message pre='${jobRankListPrefix}' key='emptyRecord' /></td> 
												</tr>				        
										    </c:when>
										    <c:otherwise>
												<c:forEach var="jobRank" items="${searchResult.entity}" varStatus="status">
													<tr>
														<td class="textCenter">${jobRank.num}</td>
														<td class="textCenter"><a href="#a" onclick="getView('${jobRank.jobRankCode}', 'jobRankName', 'PO', this)">${jobRank.jobRankCode}</a></td>
														<td class="textCenter"><a href="#a" onclick="getView('${jobRank.jobRankCode}', 'jobRankName', 'PO', this)">${jobRank.jobRankName}</a></td>
														<td class="textCenter"><a href="#a" onclick="getView('${jobRank.jobRankCode}', 'jobRankName', 'PO', this)">${jobRank.jobRankEnglishName}</a></td>
														<td class="textCenter">
															<a href="#" onclick="setUp('${jobRank.jobRankCode}', '${jobRank.sortOrder}')">
																<img src="<c:url value='/base/images/icon/ic_tablesort_up.gif' />" alt="UP" />
															</a>
															<a href="#" onclick="setDown('${jobRank.jobRankCode}', '${jobRank.sortOrder}')">
																<img src="<c:url value='/base/images/icon/ic_tablesort_down.gif' />" alt="DOWN" />
															</a>
														</td>
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
								
								<input type="hidden" name="tempCode" id="tempCode" value=""	/>

							</form>
						</div>
					</div>
					<!--//blockListTable End-->
				
				<div class="blockBlank_10px"></div>
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre="${jobRankUiPrefix}" key="subTitle.detail" /></h3>
				</div>
				<!--//subTitle_2 End--> 
				
					<!--blockDetail Start-->
					<div class="blockDetail">
						<div id="viewDiv">
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
						</div>
					</div>
					<!--//blockDetail End--> 
				
				<!--blockButton Start-->
				<div class="blockButton">
					<ul>
						<li><a class="button" id="newFormButton" href="#a"><span><ikep4j:message pre="${jobRankUiPrefix}" key="button.new" /></span></a></li>
						<li><a class="button" id="submitButton" href="#a"><span><ikep4j:message pre="${jobRankUiPrefix}" key="button.save" /></span></a></li>
						<li><a class="button" id="deleteButton" href="#a"><span><ikep4j:message pre="${jobRankUiPrefix}" key="button.delete" /></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->