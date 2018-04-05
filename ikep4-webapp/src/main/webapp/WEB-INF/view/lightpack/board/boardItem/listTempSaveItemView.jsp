<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preList"    value="ui.lightpack.board.boardItem.listBoardView.list" />
<c:set var="prefix"    value="ui.lightpack.board.boardItem.listBoardView.list" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />

<script type="text/javascript">
//<![CDATA[

function mouseOver(obj){obj.className = "bgSelected";}
function mouseOut(obj){obj.className = "";}

(function($) {
	$jq(document).ready(function() { 
		
		$jq("select[name=pagePerRecord]").change(function() {
			getList();
        });  
		
		$jq('#searchWord').keypress(function(event) { 
			if(event.which == '13') {
				getList();
			}
		});
		
		$("#checkboxAllBoardItem").click(function() { 
			$("#searchForm input[name=checkboxBoardItem]").attr("checked", $(this).is(":checked"));  
		});  
		$("#adminMultiDeleteBoardItemButton, #adminMultiDeleteBoardItemButton1").click(function() {  
			var itemIds = new Array();
			
			$("#searchForm input[name=checkboxBoardItem]:checked").each(function(index) { 
				itemIds.push($(this).val()); 
			});			 
			
			if(confirm("선택한 게시물을 삭제하시겠습니까?")) {
				$("#searchForm").ajaxLoadStart(); 
				
				$.post("<c:url value='/lightpack/board/boardItem/adminMultiDeleteBoardItem.do'/>", {"boardId" : "${board.boardId}", "itemId" : itemIds.toString(), "popupYn" : ${popupYn}}) 
				.success(function(data) {  
					getList();
				})
			}  
		});  
	});
	
	getList = function() {
		$("#searchForm")[0].submit();
	};
    
    sort = function(sortColumn, sortType) {
		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			$jq("input[name=sortType]").val('DESC');	
		} else {
			$jq("input[name=sortType]").val('ASC');
		}
		
		getList();
	};
})(jQuery);
//]]>
</script>

<form id="searchForm" method="post" action="<c:url value='/lightpack/board/boardItem/listTempSaveItemView.do'/>" onsubmit="return false;">

<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 		
<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>임시저장</h2>
	<div class="listInfo">  
		<spring:bind path="searchCondition.pagePerRecord">  
			<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
			<c:forEach var="code" items="${boardCode.pageNumList}">
				<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
			</c:forEach> 
			</select> 
		</spring:bind>
		<div class="totalNum"> <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
		<%-- div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)</div--%>
	</div>	  
	<div class="tableSearch"> 
		<spring:bind path="searchCondition.searchColumn">  
			<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<option value="title" <c:if test="${'title' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='${status.expression}' post='title'/></option>
			</select>	
		</spring:bind>		
		<spring:bind path="searchCondition.searchWord">  					
			<input name="${status.expression}" id="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
		</spring:bind>
		<a class="ic_search" href="#a" onclick="javascript:getList();" ><span><ikep4j:message pre='${preSearch}' key='search'/></span></a> 
	</div>		
	<div class="clear"></div>
</div>
<!--//tableTop End-->
	
			<!--blockButton Start-->
			<div class="blockButton"> 
				
				<li><a id="adminMultiDeleteBoardItemButton1" class="button" href="#a"><span>삭제</span></a></li>

			</div> 
			<!--//blockButton End-->  
<!--splitterBox Start-->
<div id="splitterBox">
	<div>
		<!--blockListTable Start-->
		<div class="blockListTable">
			<table id="boardItemTable">   
				<caption></caption> 
				<col style="width: 3%;"/>
				<col style="width: 6%;"/>
				<col style="width: 3%;"/>
				<col style="width: 14%;"/>
				<col style="width: 44%;"/>
				<col style="width: 15%;"/>
				<col style="width: 15%;"/>
				<%-- col style="width: 7%;"/>
				<col style="width: 7%;"/--%>
				<thead>
					<tr>
						<th scope="col"><input id="checkboxAllBoardItem" class="checkbox" title="checkbox" type="checkbox" value="" /></th> 
						<th scope="col">
							번호
						</th>
						<th scope="col">
							<a onclick="sort('ATTACH_FILE_COUNT', '<c:if test="${searchCondition.sortColumn eq 'ATTACH_FILE_COUNT'}">${searchCondition.sortType}</c:if>');"  href="#a">
								파일
							</a>
							<c:choose>
								<c:when test="${searchCondition.sortColumn eq 'ATTACH_FILE_COUNT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
								<c:when test="${searchCondition.sortColumn eq 'ATTACH_FILE_COUNT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
							</c:choose>
						</th>
						<th scope="col">
							게시판명
						</th>
						<th scope="col">
							<a onclick="sort('TITLE', '<c:if test="${searchCondition.sortColumn eq 'TITLE'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='title' />
							</a>
							<c:choose>
								<c:when test="${searchCondition.sortColumn eq 'TITLE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
								<c:when test="${searchCondition.sortColumn eq 'TITLE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
							</c:choose>				
						</th>
						<th scope="col">
							<a onclick="sort('REGISTER_NAME', '<c:if test="${searchCondition.sortColumn eq 'REGISTER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='registerName' />
							</a>
							<c:choose>
								<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
								<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
							</c:choose>				
						</th>
						<th scope="col">
							<a onclick="sort('START_DATE', '<c:if test="${searchCondition.sortColumn eq 'START_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${prefix}' key='startDate' />
							</a>
							<c:choose>
								<c:when test="${searchCondition.sortColumn eq 'START_DATE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
								<c:when test="${searchCondition.sortColumn eq 'START_DATE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
							</c:choose>				
						</th>

					</tr>
				</thead> 
				<tbody>
					<c:choose>
					    <c:when test="${searchResult.emptyRecord}">
							<tr>
								<td colspan="7" class="emptyRecord"><ikep4j:message pre='${prefix}' key='emptyRecord' /></td> 
							</tr>				        
					    </c:when>
					    <c:otherwise>
							<c:forEach var="boardItem" items="${searchResult.entity}" varStatus="status"> 
							<tr class="<c:if test="${itemDisplay eq 1}"></c:if> boardItemLine"  onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
								<td>
									<input name="checkboxBoardItem" class="checkbox" title="checkbox" type="checkbox" value="${boardItem.itemId}" /> 
								</td>
								<td>${searchCondition.recordCount -((searchCondition.pageIndex - 1) * searchCondition.pagePerRecord) - status.index}</td>
								<td>
									<c:if test="${boardItem.attachFileCount > 0}">
										<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${prefix}' key='attachFileCount' />" /> 
									</c:if>
								</td>
								<td class="textLeft ellipsis">${boardItem.board.boardName}</td>
								<td class="textLeft">
									<c:if test="${boardItem.itemDisplay eq 1 and boardItem.indentation eq 0}"><span class="notice"></c:if> 
									<c:choose> 
										<c:when test="${empty searchCondition.searchWord and empty searchCondition.sortColumn}">
											<c:set var="indent" value="${boardItem.indentation}"/>
										</c:when>
										<c:otherwise>
											<c:set var="indent" value="0"/>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${boardItem.itemDelete eq 1}"><%-- 논리적으로 삭제된 글 --%>
											<c:choose>
										 		<c:when test="${permission.isSystemAdmin}"><%--  관리자, 작성자일 경우  --%>
										 			<div class="ellipsis">
										 				<c:if test="${boardItem.itemDisplay eq 1 and boardItem.indentation eq 0}"><span class="notice"></c:if> 
										 				<span class="indent_${indent}">
										 					<span class="deletedItem">
										 						<a class="boardItem" href="<c:url value='/lightpack/board/boardItem/readBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><c:if test="${board.wordHead == 1}"><c:if test="${boardItem.wordName !=null}">[${boardItem.wordName}]</c:if></c:if><c:if test="${boardItem.itemDisplay eq 1 and boardItem.indentation eq 0}"><ikep4j:message pre='${preList}' key='notice' /></c:if>[<ikep4j:message pre='${preList}' key='itemDelete' post="deleteContents"/>] ${boardItem.title}</a>
										 					</span> 
										 				    <c:if test="${boardItem.linereplyCount gt 0}"><span class="colorPoint">(${boardItem.linereplyCount})</span></c:if>
										 				</span> 
										 				<c:if test="${boardItem.itemDisplay eq 1 and boardItem.indentation eq 0}"></span></c:if>	
										 			</div>
												</c:when>
										 		<c:otherwise>
										 			<div class="ellipsis"><span class="indent_${indent}"><span class="deletedItem"><ikep4j:message pre='${preList}' key='itemDelete' post="deleteContents"/></span></span></div>						
												</c:otherwise> 
											</c:choose>  
										</c:when>
										<c:otherwise>
										 	<div class="ellipsis">
											 	<span class="indent_${indent}">
											 	
											 		<c:if test="${(boardItem.itemDisplay eq 1 and boardItem.indentation eq 0) or (boardItem.wordName !=null and boardItem.indentation eq 0)}"><span class="notice"></c:if>
											 		<a class="boardItem" href="<c:url value='/lightpack/board/boardItem/readBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><c:if test="${board.wordHead == 1}"><c:if test="${!empty boardItem.wordName}">[${boardItem.wordName}]</c:if></c:if><c:if test="${boardItem.itemDisplay eq 1 and boardItem.indentation eq 0}"><ikep4j:message pre='${preList}' key='notice' /></c:if> ${boardItem.title}</a>
											 		<c:if test="${boardItem.linereplyCount gt 0}"><span class="colorPoint">(${boardItem.linereplyCount})</span></c:if>
											 		<c:if test="${boardItem.itemDisplay eq 1 and boardItem.indentation eq 0}"></span></c:if>	
											 	
											 	</span>
											 	
										 	</div> 
										</c:otherwise> 
									</c:choose> 	  
									<c:if test="${boardItem.itemDisplay eq 1 and boardItem.indentation eq 0}"></span></c:if>
								</td> 
								<td>
									<c:choose>
								 		<c:when test="${board.anonymous eq 1}">
											<span><ikep4j:message pre='${prefix}' key='anonymous'/></span>
										</c:when>  
										<c:otherwise>
											<c:set var="user"   value="${sessionScope['ikep.user']}" /> 
											<c:set var="portal" value="${sessionScope['ikep.portal']}" /> 
											<c:choose>
												<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
													<div class="ellipsis" title="${boardItem.user.userName} ${boardItem.user.jobTitleName} ${boardItem.user.teamName}">${boardItem.user.userName} ${boardItem.user.jobTitleName}</div>
												</c:when>
												<c:otherwise> 
													<div class="ellipsis" title="${boardItem.user.userEnglishName} ${boardItem.user.jobTitleEnglishName} ${boardItem.user.teamEnglishName}">${boardItem.user.userEnglishName} ${boardItem.user.jobTitleEnglishName}</div>
												</c:otherwise>           
											</c:choose>							 
										</c:otherwise> 
									</c:choose>
								</td>
								<td><div class="ellipsis">
								<c:choose>
									<c:when test="${empty boardItem.itemParentId}">
									<ikep4j:timezone date="${boardItem.startDate}"/>
									</c:when>
									<c:otherwise>
									<ikep4j:timezone date="${boardItem.registDate}"/>
									</c:otherwise>
								</c:choose>
								</div></td>
								<%-- td>${boardItem.hitCount}</td>
								<td>${boardItem.recommendCount}</td--%>
							</tr>
							</c:forEach>				      
					    </c:otherwise> 
					</c:choose>  
				</tbody>
			</table>
			<!--Page Numbur Start--> 
			<spring:bind path="searchCondition.pageIndex">
				<ikep4j:pagination searchFormId="searchForm" ajaxEventFunctionName="getList" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind> 
			<!--//Page Numbur End-->
		</div>
		<!--//blockListTable End-->
	</div>
</div>
			<!--blockButton Start-->
			<div class="blockButton"> 
				
				<li><a id="adminMultiDeleteBoardItemButton" class="button" href="#a"><span>삭제</span></a></li>

			</div> 
			<!--//blockButton End-->  
<!--//splitterBox End-->
</form>