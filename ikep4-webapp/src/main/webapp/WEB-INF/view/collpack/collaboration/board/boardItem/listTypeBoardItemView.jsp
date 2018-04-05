<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.board.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="message.collpack.collaboration.board.boardItem.listBoardView.list" />
<c:set var="preCode"    value="message.collpack.collaboration.common.code" /> 
<c:set var="preButton"  value="message.collpack.collaboration.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.boardItem" />
<c:set var="preSearch"  value="message.collpack.collaboration.common.searchCondition" />  
<%-- 메시지 관련 Prefix 선언 End --%>
<!--blockListTable Start-->    
<table id="boardItemTable" summary="<ikep4j:message pre="${preList}" key="summary" />">   
	<caption></caption> 
	<c:if test="${permission.isSystemAdmin}"> <%-- 검색어가 없고 관리자라면 --> 링크 허용--%>
		<col style="width: 3%;"/>
	</c:if>		
	<col style="width: 6%;"/>
	<col style="width: 3%;"/>
	<col style="width: ${permission.isSystemAdmin ? 44 : 41}%;"/>
	<col style="width: 15%;"/>
	<col style="width: 15%;"/>
	<col style="width: 7%;"/>
	<col style="width: 7%;"/>
	<thead>
		<tr>
			<c:if test="${permission.isSystemAdmin}"> <%-- 검색어가 없고 관리자라면 --> 링크 허용--%>
				<th scope="col"><input id="checkboxAllBoardItem" class="checkbox" title="checkbox" type="checkbox" value="" /></th> 
			</c:if>		 
			<th scope="col">
				<a onclick="sort('ITEM_SEQ_ID', '<c:if test="${searchCondition.sortColumn eq 'ITEM_SEQ_ID'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='itemSeqId' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'ITEM_SEQ_ID' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'ITEM_SEQ_ID' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<th scope="col">
				<a onclick="sort('ATTACH_FILE_COUNT', '<c:if test="${searchCondition.sortColumn eq 'ATTACH_FILE_COUNT'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='attachFileCount' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'ATTACH_FILE_COUNT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'ATTACH_FILE_COUNT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
				</c:choose>
			</th>
			<th scope="col">
				<a onclick="sort('TITLE', '<c:if test="${searchCondition.sortColumn eq 'TITLE'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='title' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'TITLE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'TITLE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col">
				<a onclick="sort('REGISTER_NAME', '<c:if test="${searchCondition.sortColumn eq 'REGISTER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='registerName' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col">
				<a onclick="sort('REGIST_DATE', '<c:if test="${searchCondition.sortColumn eq 'REGIST_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='registDate' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'START_DATE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'START_DATE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col">
				<a onclick="sort('HIT_COUNT', '<c:if test="${searchCondition.sortColumn eq 'HIT_COUNT'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='hitCount' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'HIT_COUNT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'HIT_COUNT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc"  />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col">
				<a onclick="sort('RECOMMEND_COUNT', '<c:if test="${searchCondition.sortColumn eq 'RECOMMEND_COUNT'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='recommendCount' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'RECOMMEND_COUNT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc"  />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'RECOMMEND_COUNT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}"key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
		</tr>
	</thead> 
	<tbody>
		<c:choose>
		    <c:when test="${searchResult.emptyRecord}">
				<tr>
					<td colspan="${permission.isSystemAdmin ? 8 : 7}" class="emptyRecord"><ikep4j:message pre='${preList}' key='emptyRecord' /></td> 
				</tr>				        
		    </c:when>
		    <c:otherwise>
				<c:forEach var="boardItem" items="${searchResult.entity}" varStatus="status"> 
				<tr  onmouseover="mouseOver(this);" <c:if test="${itemDisplay eq 1}">onmouseout="mouseOut2(this);"</c:if><c:if test="${itemDisplay ne 1}">onmouseout="mouseOut(this);"</c:if> >
					<c:if test="${permission.isSystemAdmin}"> <%--[관리자]일괄 삭제를 위한 체크박스 허용--%>
						<td <c:if test="${itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
							<input name="checkboxBoardItem" class="checkbox" title="checkbox" type="checkbox" value="${boardItem.itemId}" /> 
						</td>
					</c:if> 						
					<% /* %><td>${boardItem.itemSeqId}</td> <% */ %>
					<td <c:if test="${itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>${searchCondition.recordCount -((searchCondition.pageIndex - 1) * searchCondition.pagePerRecord) - status.index}</td>
					<td <c:if test="${itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
						<c:if test="${boardItem.attachFileCount > 0}">
							<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" /> 
						</c:if>
					</td>
					<td <c:if test="${itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if> class="textLeft">
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
							 						<a class="boardItem" href="<c:url value='/collpack/collaboration/board/boardItem/readBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><c:if test="${boardItem.itemDisplay eq 1 and boardItem.indentation eq 0}"><ikep4j:message pre='${preList}' key='notice' /></c:if>[<ikep4j:message pre='${preList}' key='itemDelete' post="deleteContents"/>] ${boardItem.title}</a>
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
								 		<c:if test="${boardItem.itemDisplay eq 1 and boardItem.indentation eq 0}"><span class="notice"></c:if>
								 		<a class="boardItem" href="<c:url value='/collpack/collaboration/board/boardItem/readBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><c:if test="${boardItem.itemDisplay eq 1 and boardItem.indentation eq 0}"><ikep4j:message pre='${preList}' key='notice' /></c:if>${boardItem.title}</a>
								 		<c:if test="${boardItem.linereplyCount gt 0}"><span class="colorPoint">(${boardItem.linereplyCount})</span></c:if>
								 		<c:if test="${boardItem.itemDisplay eq 1 and boardItem.indentation eq 0}"></span></c:if>
								 	</span>
							 	</div> 
							</c:otherwise> 
						</c:choose> 	  
						<c:if test="${boardItem.itemDisplay eq 1 and boardItem.indentation eq 0}"></span></c:if>
					</td> 
					<td <c:if test="${itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
						<c:choose>
					 		<c:when test="${board.anonymous eq 1}">
								<span><ikep4j:message pre='${preList}' key='anonymous'/></span>
							</c:when>  
							<c:otherwise>
								<c:set var="user"   value="${sessionScope['ikep.user']}" /> 
								<c:set var="portal" value="${sessionScope['ikep.portal']}" /> 
								<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
										<div class="ellipsis"><a title="${boardItem.user.userName} ${boardItem.user.jobTitleName} ${boardItem.user.teamName}" href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom')">${boardItem.user.userName} ${boardItem.user.jobTitleName}</a></div>
									</c:when>
									<c:otherwise> 
										<div class="ellipsis"><a title="${boardItem.user.userEnglishName} ${boardItem.user.jobTitleEnglishName} ${boardItem.user.teamEnglishName}" href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom')">${boardItem.user.userEnglishName} ${boardItem.user.jobTitleEnglishName}</a></div>
									</c:otherwise>           
								</c:choose>							 
							</c:otherwise> 
						</c:choose>
					</td>
					<td <c:if test="${itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>><div class="ellipsis"><ikep4j:timezone date="${boardItem.registDate}"/></div></td>
					<td <c:if test="${itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>${boardItem.hitCount}</td>
					<td <c:if test="${itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>${boardItem.recommendCount}</td>
				</tr>
				</c:forEach>				      
		    </c:otherwise> 
		</c:choose>  
	</tbody>
</table> 


