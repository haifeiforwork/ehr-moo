<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="prefixMessage" value="message.portal.admin.screen.portlet.listPortlet"/>
<c:set var="prefixUI" value="ui.portal.admin.screen.portlet.listPortlet"/>

<script type="text/javascript">
		function readPortlet(portletId) {
			$jq("#searchForm").attr("action","<c:url value='/portal/admin/screen/portlet/readPortlet.do?portletId='/>"+portletId);
			$jq("#searchForm").submit();
		}
		
		function createPortletForm(){
			$jq("#searchForm").attr("action","<c:url value='/portal/admin/screen/portlet/createPortletForm.do'/>");
			$jq("#searchForm").submit();
		}
		
		(function($) {
			
			//주의 : 글로벌 함수는 아래와 같이 글로벌 변수에 무명 함수를 assign해서 생성한다.
			
			/**
			 * 정렬 조건 변경 함수
			 * 
			 * @param {Object} sortColumn
			 * @param {Object} sortType
			 */
			sort = function(sortColumn, sortType) {
				$("input[name=sortColumn]").val(sortColumn);
				
				if(sortType == '') sortType = 'ASC';
				
				if( sortType == 'ASC') {
					$("input[name=sortType]").val('DESC');	
				} else {
					$("input[name=sortType]").val('ASC');
				}
				
				$("#searchItemButton").click();
			};
			
			/**
			 * onload시 수행할 코드
			 */
			$(document).ready(function() {
				
				$jq("#portletMangerOfLeft").click();
				
				/**
				 * 검색버튼
				 */
				$("#searchItemButton").click(function() {
					$("input[name=pageIndex]").val('1');
					$("#searchForm").submit(); 
					return false; 
				});
				
				/**
				 * 페이징 버튼
				 */
				$("#pageIndex").click(function() {
					$("#searchForm").submit(); 
					return false; 
				});
				
				$("select[name=pagePerRecord]").change(function(){
	                $("#pageIndex").click();
	            });
		 
		
			});
		})(jQuery);  
</script>

					
						<!--blockListTable Start-->
						<div class="blockListTable">
						
							<div id="listDiv">

				<form id="searchForm" method="post" action="<c:url value='/portal/admin/screen/portlet/listPortlet.do'/>">  

					<spring:bind path="searchCondition.sortColumn">
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${prefixUI}' key='${status.expression}'/>" />
					</spring:bind>
					<spring:bind path="searchCondition.sortType">
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${prefixUI}' key='${status.expression}'/>" />
					</spring:bind>
					
					<!--tableTop Start-->  
					<div class="tableTop">  
						<div class="tableTop_bgR"></div>
						<h2><ikep4j:message pre='${prefixUI}' key='header.title' /></h2>
						<div class="listInfo"> 
							<spring:bind path="searchCondition.pagePerRecord">  
								<select name="${status.expression}" title='<ikep4j:message pre='${prefixUI}' key='${status.expression}' />' >
									<c:forEach var="code" items="${boardCode.pageNumList}">
										<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
									</c:forEach> 
								</select> 
							</spring:bind>
							<div class="totalNum">
								<ikep4j:message pre='${prefixUI}' key='search.total' /><span> ${searchResult.recordCount}</span>
							</div>
						</div>
						<div class="tableSearch"> 
							<spring:bind path="searchCondition.searchColumn">  
								<select name="${status.expression}" title='<ikep4j:message pre='${prefixUI}' key='${status.expression}' />'>
									<option value="portletName" <c:if test="${'portletName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre="${prefixUI}" key="search.portletName" /></option>
									<option value="systemName" <c:if test="${'systemName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre="${prefixUI}" key="search.systemName" /></option>
									<option value="portletCategoryName" <c:if test="${'portletCategoryName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre="${prefixUI}" key="search.portletCategoryName" /></option>
								</select>	
							</spring:bind>		
							<spring:bind path="searchCondition.searchWord">  					
								<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${prefixUI}' key='${status.expression}' />'  size="20" />
							</spring:bind>
							<a class="ic_search" id="searchItemButton" name="searchItemButton" href="#a" ></a> 
						</div>
						<div class="clear"></div>	
					</div>
					<!--//tableTop End-->
					
					<table summary="<ikep4j:message pre="${prefixUI}" key="list.summary" />">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col"	width="8%"><ikep4j:message pre="${prefixUI}" key="list.number" /></th>
								<th scope="col" width="15%">
									<a onclick="javascript:sort('PORTLET_CATEGORY_NAME', '<c:if test="${searchCondition.sortColumn eq 'PORTLET_CATEGORY_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre="${prefixUI}" key="list.portletCategoryName" />
									</a>
								</th>
								<th scope="col" width="19%">
									<a onclick="javascript:sort('PORTLET_NAME', '<c:if test="${searchCondition.sortColumn eq 'PORTLET_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre="${prefixUI}" key="list.portletName" />
									</a>
								</th>
								<th scope="col" width="10%">
									<a onclick="javascript:sort('PORTLET_TYPE', '<c:if test="${searchCondition.sortColumn eq 'PORTLET_TYPE'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre="${prefixUI}" key="list.portletType" />
									</a>
								</th>
								<th scope="col" width="10%">
									<a onclick="javascript:sort('SYSTEM_NAME', '<c:if test="${searchCondition.sortColumn eq 'SYSTEM_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre="${prefixUI}" key="list.systemName" />
									</a>
								</th>
								<th scope="col" width="10%">
									<a onclick="javascript:sort('MOVEABLE', '<c:if test="${searchCondition.sortColumn eq 'MOVEABLE'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre="${prefixUI}" key="list.moveable" />
									</a>
								</th>
								<th scope="col" width="8%">
									<a onclick="javascript:sort('PUBLIC_OPTION', '<c:if test="${searchCondition.sortColumn eq 'PUBLIC_OPTION'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre="${prefixUI}" key="list.publicOption" />
									</a>
								</th>
								<th scope="col" width="10%">
									<a onclick="javascript:sort('STATUS', '<c:if test="${searchCondition.sortColumn eq 'STATUS'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre="${prefixUI}" key="list.status" />
									</a>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${searchResult.emptyRecord}">
									<tr>
										<td colspan="8" class="emptyRecord"><ikep4j:message pre='${prefixUI}' key='list.empty' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="portalPortlet" items="${searchResult.entity}" varStatus="status">
										<tr onclick="readPortlet('${portalPortlet.portletId}')">
											<td class="textCenter">${portalPortlet.num}</td>
											<td class="textCenter">${portalPortlet.portletCategoryName}</td>
											<td class="textCenter"><a href="#" onclick="readPortlet('${portalPortlet.portletId}')">${portalPortlet.portletName}</a></td>
											<td class="textCenter">${portalPortlet.portletType}</td>
											<td class="textCenter">${portalPortlet.systemName}</td>
											<td class="textCenter">
											<c:choose>
											<c:when test="${portalPortlet.moveable==1}">
											<ikep4j:message pre="${prefixUI}" key="list.possible" />
											</c:when>
											<c:otherwise>
											<ikep4j:message pre="${prefixUI}" key="list.impossible" />
											</c:otherwise>
											</c:choose>
											</td>
											<td>
											<c:choose>
											<c:when test="${portalPortlet.publicOption==1}">
											<ikep4j:message pre="${prefixUI}" key="list.public" />
											</c:when>
											<c:otherwise>
											<ikep4j:message pre="${prefixUI}" key="list.private" />
											</c:otherwise>
											</c:choose>
											</td>
											<td>
											<c:choose>
											<c:when test="${portalPortlet.status==1}">
											<ikep4j:message pre="${prefixUI}" key="list.use" />
											</c:when>
											<c:otherwise>
											<ikep4j:message pre="${prefixUI}" key="list.unuse" />
											</c:otherwise>
											</c:choose>
											</td>
										</tr>
									</c:forEach>
							    </c:otherwise>
							</c:choose>
						</tbody>
					</table>

						<!--Page Numbur Start--> 
						<spring:bind path="searchCondition.pageIndex">
						<ikep4j:pagination searchButtonId="${status.expression}" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
						<input  id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${prefixUI}' key='${status.expression}'/>" />
						</spring:bind> 
						<!--//Page Numbur End--> 
					
				</form>
							
							</div>
						
						</div>
						<!--//blockListTable End-->
					
					<div class="blockBlank_10px"></div>
 
					
					<!--blockButton Start-->
					<div class="blockButton">
						<ul>
							<li><a class="button"  href="javascript:createPortletForm();"><span><ikep4j:message pre="${prefixUI}" key="button.create" /></span></a></li>
						</ul>
					</div>
					<!--//blockButton End-->