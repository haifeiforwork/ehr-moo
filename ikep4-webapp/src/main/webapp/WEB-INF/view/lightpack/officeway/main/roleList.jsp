<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
<c:set var="messagePrefix" value="message.portal.admin.role.role.list"/>
<c:set var="uiPrefix" value="ui.portal.admin.role.role"/>

				<script type="text/javascript" language="javascript">
				//<!--
					
					function getList() {
						
						var searchForm = document.getElementById("searchForm");
						searchForm.action = '<c:url value="/lightpack/officeway/roleList.do" />';
						searchForm.submit();
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
					
					function getView(roleId) {
						
						$jq("#roleId").val(roleId);

						var searchForm = document.getElementById("searchForm");
						searchForm.action = '<c:url value="/lightpack/officeway/roleForm.do" />';
						searchForm.submit();
					}
					
					// 페이지 로딩시 실행하는 jQuery 코드로 list와 view를 불러온다.
					$jq(document).ready(function() {
						
						//left menu setting
						$jq("#roleLinkOfLeft").click();
						
// 						$jq('#searchBox').keypress( function(event) {
// 							getList();
// 				        });
						$jq('#searchBoardItemButton').click( function() { 
// 				        	$jq('#searchBox').trigger("keypress");
				        	getList();
				        });
						
						$jq("#newFormButton").click(function() {
							getView('');
						})
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
									<h2>사무용품 권한관리</h2>
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
									<%-- <div class="tableSearch"> 
										<spring:bind path="searchCondition.searchColumn">  
											<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
												<option value="title" <c:if test="${'title' eq status.value}">selected="selected"</c:if> >
												 	구분ID
												</option>
											</select>	
										</spring:bind>		
										<spring:bind path="searchCondition.searchWord">  					
											<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
										</spring:bind>
										<a href="#a" id="searchBoardItemButton" name="searchBoardItemButton" class="ic_search">
											<span><ikep4j:message pre='${preSearch}' key='search' /></span>
										</a>
									</div> --%>
									<div class="clear"></div>	
								</div>
								<!--//tableTop End-->
								
								<table summary="<ikep4j:message pre="${uiPrefix}" key="tableSummary" />">
									<caption></caption>
									<colgroup>
										<col width="10%"/>
										<col width="90%"/>
									</colgroup>
									<thead>
										<tr>
											<th scope="col">
												<a onclick="javascript:sort('ROLE_ID', '<c:if test="${searchCondition.sortColumn eq 'ROLE_ID'}">${searchCondition.sortType}</c:if>');"  href="#a">
												<ikep4j:message pre="${uiPrefix}" key="num" />
												</a>
											</th>
											<%-- <th scope="col">
												<a onclick="javascript:sort('ROLE_ID', '<c:if test="${searchCondition.sortColumn eq 'ROLE_ID'}">${searchCondition.sortType}</c:if>');"  href="#a">
												<ikep4j:message pre="${uiPrefix}" key="roleId" />
												</a>
											</th> --%>
											<%-- <th scope="col">
												<a onclick="javascript:sort('ROLE_NAME', '<c:if test="${searchCondition.sortColumn eq 'ROLE_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
												구분ID
												</a>
											</th> --%>
											<th scope="col">
												<a onclick="javascript:sort('DESCRIPTION', '<c:if test="${searchCondition.sortColumn eq 'DESCRIPTION'}">${searchCondition.sortType}</c:if>');"  href="#a">
												구분명
												</a>
											</th>
											<%-- <th scope="col">
												<a onclick="javascript:sort('ROLE_TYPE_NAME', '<c:if test="${searchCondition.sortColumn eq 'ROLE_TYPE_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
												<ikep4j:message pre="${uiPrefix}" key="roleType" />
												</a>
											</th> --%>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${searchResult.emptyRecord}">
												<tr>
													<td colspan="3" class="emptyRecord"><ikep4j:message pre='${messagePrefix}' key='alert.empty' /></td> 
												</tr>				        
										    </c:when>
										    <c:otherwise>
												<c:forEach var="role" items="${searchResult.entity}" varStatus="status">
													<tr>
														<td class="textCenter">${role.num}</td>
														<%-- <td class="textCenter"><a onclick="getView('${role.roleId}')" style="cursor:pointer;">${role.roleId}</a></td> --%>
														<%-- <td class="textCenter"><a onclick="getView('${role.roleId}')" style="cursor:pointer;">${role.roleName}</a></td> --%>
														<td class="textCenter"><a onclick="getView('${role.roleId}')" style="cursor:pointer;">${role.description}</a></td>
														<%-- <td class="textCenter">${role.roleTypeName}</td> --%>
													</tr>
												</c:forEach>
										    </c:otherwise>
										</c:choose>
									</tbody>
								</table>
								
								<!--Page Number Start--> 
								<spring:bind path="searchCondition.pageIndex">
									<ikep4j:pagination searchFormId="searchForm" ajaxEventFunctionName="getList" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
									<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
								</spring:bind> 
								<!--//Page Number End-->
								
								<input type="hidden" id="roleId" name="roleId" value=""/>
								<input type="hidden" id="fieldName" name="fieldName" value="roleName" />
								<input type="hidden" id="itemTypeCode" name="itemTypeCode" value="PO" />
								
							</form>
						</div>
					</div>
					<!--//blockListTable End-->
					
					<!--blockButton Start-->
					<%-- <div class="blockButton">
						<ul>
							<li><a class="button" id="newFormButton" href="#"><span><ikep4j:message pre="${uiPrefix}" key="button.new" /></span></a></li>
						</ul>
					</div> --%>
					<!--//blockButton End-->