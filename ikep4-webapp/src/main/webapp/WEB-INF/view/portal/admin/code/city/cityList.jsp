<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
<c:set var="cityUiPrefix" value="ui.portal.admin.code.city"/>
<c:set var="cityListPrefix" value="message.portal.admin.code.city.cityList"/> 



			<script type="text/javascript" language="javascript">
				//<!--
			
				// 상단에 보이게 될 리스트를 가져오는 함수
				function getList() {
					$jq("#searchForm").attr("action", "<c:url value='getCityList.do' />");
					$jq("#searchForm")[0].submit();
				}
				

				// 하단에 보이게 될 상세정보를 가져오는 함수
				function getView(cityId) {
					$jq.ajax({
						url : '<c:url value="getForm.do" />',
						data : {
							cityId : cityId,
							nationCode : '${searchCondition.nationCode}'
						},
						type : "get",
						success : function(result) {
							$jq("#viewDiv").html(result);
						}
					});
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
					
					getView('');
					
					$jq("#searchBoardItemButton").click(function() {   
						getList();
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
				});
				//-->
			</script>
				
				<!--blockListTable Start-->
				<div class="blockListTable">
						<div id="listDiv">
							<form id="searchForm" name="searchForm" method="post" onsubmit="getList(); return false;" action="">  
							 	<input type="hidden" id="nationCode" name="nationCode" value="${searchCondition.nationCode}" />
								<spring:bind path="searchCondition.sortColumn">
									<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
								</spring:bind>
								<spring:bind path="searchCondition.sortType">
									<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
								</spring:bind>
								
								<!--tableTop Start-->  
								<div class="tableTop">
									<div class="tableTop_bgR"></div>
									<h2><ikep4j:message pre="${cityUiPrefix}" key="pageTitle" /></h2> 
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
												<%-- <option value="code" <c:if test="${'code' eq status.value}">selected="selected"</c:if> >
													<ikep4j:message pre="${cityUiPrefix}" key="nationCode" />
												</option> --%>
												<option value="title" <c:if test="${'title' eq status.value}">selected="selected"</c:if> >
													<ikep4j:message pre="${cityUiPrefix}" key="cityName" />
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
								
								<table summary="<ikep4j:message pre="${cityUiPrefix}" key="tableSummary" />">
									<caption></caption>
										<colgroup>
											<col width="20%"/>
											<col width="40%"/>
											<col width="40%"/>
										</colgroup>
									<thead>
										<tr>
											<th scope="col"><ikep4j:message pre="${cityUiPrefix}" key="num" /></th>
											<th scope="col">
												<a onclick="javascript:sort('NATION_CODE', '<c:if test="${searchCondition.sortColumn eq 'NATION_CODE'}">${searchCondition.sortType}</c:if>');"  href="#a">
												<ikep4j:message pre="${cityUiPrefix}" key="nationCode" />
												</a>
											</th>
											<th scope="col">
												<a onclick="javascript:sort('CITY_NAME', '<c:if test="${searchCondition.sortColumn eq 'CITY_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
												<ikep4j:message pre="${cityUiPrefix}" key="cityName" />
												</a>
											</th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${searchResult.emptyRecord}">
												<tr>
													<td colspan="3" class="emptyRecord"><ikep4j:message pre='${cityListPrefix}' key='emptyRecord' /></td> 
												</tr>				        
										    </c:when>
										    <c:otherwise>
												<c:forEach var="result" items="${searchResult.entity}" varStatus="status">
													<tr>
														<td class="textCenter">${result.num}</td>
														<td class="textCenter"><a href="#a" onclick="getView('${result.cityId}')">${result.nationCode}</a></td>
														<td class="textCenter"><a href="#a" onclick="getView('${result.cityId}')">${result.cityName}</a></td>
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
							</form>
						</div>
					</div>
				<!--//blockListTable End-->
					
				<div class="blockBlank_10px"></div>
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre="${cityUiPrefix}" key="subTitle.detail" /></h3>
				</div>
				<!--//subTitle_2 End--> 
				
				
				<!--blockDetail Start-->
				<div id="viewDiv"></div>
				<!--//blockDetail End--> 