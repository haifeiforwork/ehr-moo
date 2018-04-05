<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<c:set var="itemTypeListPrefix" value="message.portal.admin.code.itemType.itemTypeList"/>
<c:set var="itemTypeUiPrefix" value="ui.portal.admin.code.itemType"/>

				<script type="text/javascript" language="javascript">
				//<!--

					// 상단에 보이게 될 리스트를 가져오는 함수
					function getList() {
						$jq("#searchForm").attr("action", "<c:url value='getList.do' />");
						$jq("#searchForm")[0].submit();
					}
				
					// 하단에 보이게 될 상세정보를 가져오는 함수
					function getView(itemTypeCode, tr) {

						var selectClassName = "bgSelected";
						$jq(tr).parent().parent().children().removeClass(selectClassName).end()
								.end().end().addClass(selectClassName);
				
						$jq.ajax({
							url : '<c:url value="getForm.do" />',
							data : {
								code : itemTypeCode
							},
							type : "get",
							success : function(result) {
								$jq("#viewDiv").html(result);
							}
						});
					}
					
					// 코드 중복 체크
					function checkCode() {
						if($jq("#itemTypeCode").val()=='') {
							alert("<ikep4j:message pre="${itemTypeListPrefix}" key="alert.noCheckValue" />");
						} else {
							$jq.ajax({
								url : '<c:url value="checkCode.do" />',     
								data : {code: $jq("#itemTypeCode").val()},     
								type : "post",
								success : function(result) {     
									
									if(result == 'duplicated') {
										alert("<ikep4j:message pre="${itemTypeListPrefix}" key="alert.isDuplicated" />");
										$jq("#codeCheck").val("");
									} else {
										alert("<ikep4j:message pre="${itemTypeListPrefix}" key="alert.isAvailable" />");
										$jq("#codeCheck").val("success");
									}
								} 
							});
						}
					}
					
					// 코드를 삭제함
					function deleteForm() {
						if ($jq("#itemTypeCode").val() == "") {
							alert("<ikep4j:message pre="${itemTypeListPrefix}" key="alert.cantDelete" />");
							return;
						}
						
						if(confirm("<ikep4j:message pre="${itemTypeListPrefix}" key="confirm.wannaDelete" />")) {
							$jq.ajax({
								url : '<c:url value="deleteItemType.do" />',
								data : $jq("#itemType").serialize(),
								type : "post",
								success : function(result) {
									alert("<ikep4j:message pre="${itemTypeListPrefix}" key="alert.deleteCompleted" />");
									getList();
								},
								error:function(){
									alert("<ikep4j:message pre="${itemTypeListPrefix}" key="alert.failedToDelete" />");
								}
							});
						} else {
							return;
						}
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
						$jq("#itemTypeManageOfLeft").click();
						
						// 페이지 로딩 직후 폼의 맨 첫번째 input 박스에 포커스
						$jq("#itemType :input:visible:enabled:first").focus().select();
				
						// 새 폼을 불러옴
						$jq("#newFormButton").click(function() {
							$jq("#codeCheck").val("");
							getView();
						});
						
						$jq("#submitButton").click(function() {
							$jq("#itemType").submit();
						});
						
						$jq("#deleteButton").click(function() {
							deleteForm();
						});
						
						// ID 중복을 체크함
						$jq("#checkCodeButton").click(function() {
							checkCode();
						});
						
						$jq('#searchBoardItemButton').click( function() { 
// 				        	$jq('#searchBox').trigger("keypress");
							getList();
				        });
						
						// code 값이 바뀌면 중복체크를 위해 codeCheck 값을 바꿔줌
						$jq("input[name=itemTypeCode]").change(function() {
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
									<h2><ikep4j:message pre="${itemTypeUiPrefix}" key="pageTitle" /></h2>  
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
													<ikep4j:message pre="${itemTypeUiPrefix}" key="itemTypeCode" />
												</option>								
												<option value="title" <c:if test="${'title' eq status.value}">selected="selected"</c:if> >
													<ikep4j:message pre="${itemTypeUiPrefix}" key="itemTypeName" />
												</option>
											</select>	
										</spring:bind>		
										<spring:bind path="searchCondition.searchWord">  					
											<input id="searchBox" name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
										</spring:bind>
										<a href="#a" id="searchBoardItemButton" name="searchBoardItemButton" class="ic_search">
											<span><ikep4j:message pre='${preSearch}' key='search' /></span>
										</a> 
									</div>
									<div class="clear"></div>
								</div>
								<!--//tableTop End-->
								
								<table summary="<ikep4j:message pre="${itemTypeUiPrefix}" key="tableSummary" />">
									<caption></caption>
										<colgroup>
											<col width="20%"/>
											<col width="40%"/>
											<col width="40%"/>
										</colgroup>
									<thead>
										<tr>
											<th scope="col" ><ikep4j:message pre="${itemTypeUiPrefix}" key="num" /></th>
											<th scope="col" >
												<a onclick="javascript:sort('ITEM_TYPE_CODE', '<c:if test="${searchCondition.sortColumn eq 'ITEM_TYPE_CODE'}">${searchCondition.sortType}</c:if>');"  href="#a">
												<ikep4j:message pre="${itemTypeUiPrefix}" key="itemTypeCode" />
												</a>
											</th>
											<th scope="col" >
												<a onclick="javascript:sort('ITEM_TYPE_NAME', '<c:if test="${searchCondition.sortColumn eq 'ITEM_TYPE_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
												<ikep4j:message pre="${itemTypeUiPrefix}" key="itemTypeName" />
												</a>
											</th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${searchResult.emptyRecord}">
												<tr>
													<td colspan="3" class="emptyRecord"><ikep4j:message pre='${itemTypeListPrefix}' key='emptyRecord' /></td> 
												</tr>				        
										    </c:when>
										    <c:otherwise>
												<c:forEach var="itemType" items="${searchResult.entity}" varStatus="status">
													<tr>
														<td class="textCenter">${itemType.num}</td>
														<td class="textCenter"><a href="#a" onclick="getView('${itemType.itemTypeCode}', this)">${itemType.itemTypeCode}</a></td>
														<td class="textLeft"><a href="#a" onclick="getView('${itemType.itemTypeCode}', this)">${itemType.itemTypeName}</a></td>
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
					<h3><ikep4j:message pre="${itemTypeUiPrefix}" key="subTitle.detail" /></h3>
				</div>
				<!--//subTitle_2 End--> 
				
					<!--blockDetail Start-->
					<div class="blockDetail">
						<div id="viewDiv">
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
						</div>
					</div>
					<!--//blockDetail End--> 
				
				<!--blockButton Start-->
				<div class="blockButton">
					<ul>
						<li><a class="button" id="newFormButton" href="#a"><span><ikep4j:message pre="${itemTypeUiPrefix}" key="button.new" /></span></a></li>
						<li><a class="button" id="submitButton" href="#a"><span><ikep4j:message pre="${itemTypeUiPrefix}" key="button.save" /></span></a></li>
						<li><a class="button" id="deleteButton" href="#a"><span><ikep4j:message pre="${itemTypeUiPrefix}" key="button.delete" /></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->