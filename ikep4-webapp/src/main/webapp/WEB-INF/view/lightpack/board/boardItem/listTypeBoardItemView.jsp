<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.lightpack.board.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="ui.lightpack.board.boardItem.listBoardView.list" />
<c:set var="preButton"  value="ui.lightpack.common.button" /> 
<c:set var="preMessage" value="message.lightpack.common.boardItem" />
<c:set var="preSearch"  value="ui.ikep4.common.searchCondition" />  
<%-- 메시지 관련 Prefix 선언 End --%>
<!--blockListTable Start-->    
<table id="boardItemTable" summary="<ikep4j:message pre="${preList}" key="summary" />">   
	<caption></caption> 
	<c:if test="${permission.isSystemAdmin}"> <%-- 검색어가 없고 관리자라면 --> 링크 허용--%>
		<col style="width: 3%;"/>
	</c:if>		
	<col style="width: 6%;"/>
	<col style="width: 5%;"/>
	<c:if test="${board.boardId=='100006240370'}">
		<col style="width: 8%;"/>
		<col style="width: 10%;"/>
		<c:if test="${permission.isSystemAdmin || boardAdminRole}">
			<col style="width: 10%;"/>
		</c:if>
	</c:if>
	<col style="width: ${permission.isSystemAdmin ? 42 : 39}%;"/>
	<c:choose>
		<c:when test="${board.boardId=='100010083357'}">
			<col style="width: 5%;"/>
		</c:when>
	</c:choose>
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
				<ikep4j:message pre='${preList}' key='itemSeqId' />
			</th>
			<c:choose>
				<c:when test="${board.boardId=='100010083357'}">
					<th scope="col">
						<a onclick="sort('WORD_NAME', '<c:if test="${searchCondition.sortColumn eq 'WORD_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
						분류
						</a>
						<c:choose>
							<c:when test="${searchCondition.sortColumn eq 'WORD_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							<c:when test="${searchCondition.sortColumn eq 'WORD_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
						</c:choose>	
					</th>
				</c:when>
				<c:otherwise>
					<th scope="col">
						<a onclick="sort('ATTACH_FILE_COUNT', '<c:if test="${searchCondition.sortColumn eq 'ATTACH_FILE_COUNT'}">${searchCondition.sortType}</c:if>');"  href="#a">
							<ikep4j:message pre='${preList}' key='attachFileCount' />
						</a>
						<c:choose>
							<c:when test="${searchCondition.sortColumn eq 'ATTACH_FILE_COUNT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							<c:when test="${searchCondition.sortColumn eq 'ATTACH_FILE_COUNT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
						</c:choose>
					</th>
				</c:otherwise>
			</c:choose>
			
			<c:if test="${board.boardId=='100006240370'}">
				<th scope="col">
					<a onclick="sort('WORD_NAME', '<c:if test="${searchCondition.sortColumn eq 'WORD_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
						진행상태
					</a>
					<c:choose>
						<c:when test="${searchCondition.sortColumn eq 'WORD_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
						<c:when test="${searchCondition.sortColumn eq 'WORD_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
					</c:choose>				
				</th>
				<th scope="col">
				</th>
				<c:if test="${permission.isSystemAdmin || boardAdminRole}">
					<th scope="col">
						사업장
					</th>
				</c:if>
			</c:if>
			<th scope="col">
				<a onclick="sort('TITLE', '<c:if test="${searchCondition.sortColumn eq 'TITLE'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='title' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'TITLE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'TITLE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<c:choose>
				<c:when test="${board.boardId=='100010083357'}">
					<th scope="col">
						<a onclick="sort('ATTACH_FILE_COUNT', '<c:if test="${searchCondition.sortColumn eq 'ATTACH_FILE_COUNT'}">${searchCondition.sortType}</c:if>');"  href="#a">
							<ikep4j:message pre='${preList}' key='attachFileCount' />
						</a>
						<c:choose>
							<c:when test="${searchCondition.sortColumn eq 'ATTACH_FILE_COUNT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							<c:when test="${searchCondition.sortColumn eq 'ATTACH_FILE_COUNT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
						</c:choose>
					</th>
				</c:when>
			</c:choose>
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
				<a onclick="sort('START_DATE', '<c:if test="${searchCondition.sortColumn eq 'START_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='startDate' />
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
					<c:when test="${searchCondition.sortColumn eq 'RECOMMEND_COUNT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
		</tr>
	</thead> 
	<tbody>
		<c:choose>
		    <c:when test="${searchResult.emptyRecord}">
		    	<c:choose>
					<c:when test="${boardItem.boardId=='100010083357'}">
						<tr>
							<td colspan="${permission.isSystemAdmin ? 9 : 8}" class="emptyRecord"><ikep4j:message pre='${preList}' key='emptyRecord' /></td>
						</tr>		
					</c:when>
					<c:otherwise>
						<tr>
							<c:if test="${board.boardId=='100006240370'}">
								<c:choose>
									<c:when test="${permission.isSystemAdmin || boardAdminRole}">
										<td colspan="${permission.isSystemAdmin ? 11 : 10}" class="emptyRecord">
									</c:when>
									 <c:otherwise>
									 	<td colspan="${permission.isSystemAdmin ? 10 : 9}" class="emptyRecord">
									 </c:otherwise>
								 </c:choose>
								<ikep4j:message pre='${preList}' key='emptyRecord' /></td>
							</c:if>
							<c:if test="${board.boardId!='100006240370'}">
								<td colspan="${permission.isSystemAdmin ? 8 : 7}" class="emptyRecord"><ikep4j:message pre='${preList}' key='emptyRecord' /></td>
							</c:if>
						</tr>		
					</c:otherwise>
				</c:choose>
						        
		    </c:when>
		    <c:otherwise>
				<c:forEach var="boardItem" items="${searchResult.entity}" varStatus="status"> 
				<tr  onmouseover="mouseOver(this);" <c:if test="${boardItem.itemDisplay eq 1}">onmouseout="mouseOut2(this);"</c:if><c:if test="${boardItem.itemDisplay ne 1}">onmouseout="mouseOut(this);"</c:if>>
					<c:if test="${permission.isSystemAdmin}"> <%--[관리자]일괄 삭제를 위한 체크박스 허용--%>
						<td <c:if test="${boardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
							<input name="checkboxBoardItem" class="checkbox" title="checkbox" type="checkbox" value="${boardItem.itemId}" /> 
						</td>
					</c:if> 						
					<% /* %><td>${boardItem.itemSeqId}</td> <% */ %>
					<td <c:if test="${boardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
						<c:if test="${boardItem.itemDisplay eq 1}">
					        <img src="<c:url value='/base/images/icon/ic_notice.gif' />" alt="notice" /> 
					    </c:if>
					    <c:if test="${boardItem.itemDisplay ne 1}">
						${searchCondition.recordCount -((searchCondition.pageIndex - 1) * searchCondition.pagePerRecord) - status.index}
						</c:if>
					</td>
					<c:choose>
						<c:when test="${boardItem.boardId=='100010083357'}">
							<td <c:if test="${boardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
								${boardItem.wordName}
							</td>
						</c:when>
						<c:otherwise>
							<td <c:if test="${boardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
								<c:if test="${boardItem.attachFileCount > 0}">
									<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" /> 
								</c:if>
							</td>
						</c:otherwise>
					</c:choose>
					<c:if test="${board.boardId=='100006240370'}">
						<td class="textCenter" <c:if test="${boardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>[${boardItem.wordName}]</td>
						<td class="textCenter" <c:if test="${boardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
							<c:choose> 
								<c:when test="${boardItem.wordName == '접수'}">
									<img src="<c:url value='/base/images/ess/red_icon.gif'/>">
								</c:when>
								<c:when test="${boardItem.wordName == '완료'}">
									<img src="<c:url value='/base/images/ess/green_icon.gif'/>">
								</c:when>
								<c:when test="${boardItem.wordName == '진행 중'}">
									<img src="<c:url value='/base/images/ess/yellow_icon.gif'/>">
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</td>
						<c:if test="${permission.isSystemAdmin || boardAdminRole}">
						<td class="textCenter" <c:if test="${boardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
							<c:choose> 
								<c:when test="${empty boardItem.workplaceName}">
									전체
								</c:when>
								<c:otherwise>
									${boardItem.workplaceName}
								</c:otherwise>
							</c:choose>
						</td>
						</c:if>
					</c:if>
					<c:choose>
						<c:when test="${boardItem.boardId=='100010083357'}">
							<td <c:if test="${boardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if> class="textLeft">
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
									 						<a class="boardItem" <c:if test="${boardItem.readYn=='1' || boardItem.readFlg=='0'}" >style="color:#5D5D5D;"</c:if><c:if test="${boardItem.readYn=='0' && boardItem.readFlg=='1'}" >style="color:#050099;"</c:if> href="<c:url value='/lightpack/board/boardItem/readBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>">[<ikep4j:message pre='${preList}' key='itemDelete' post="deleteContents"/>] ${boardItem.title}</a>
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
										 		<a class="boardItem"  <c:if test="${board.colorUse == 1 &&(boardItem.readYn=='1' || boardItem.readFlg=='0')}" >style="color:#5D5D5D;"</c:if><c:if test="${board.colorUse == 1 && (boardItem.readYn=='0' && boardItem.readFlg=='1')}" >style="color:#050099;"</c:if> href="<c:url value='/lightpack/board/boardItem/readBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"> ${boardItem.title}</a>
										 		<c:if test="${boardItem.linereplyCount gt 0}"><span class="colorPoint">(${boardItem.linereplyCount})</span></c:if>
										 		<c:if test="${boardItem.itemDisplay eq 1 and boardItem.indentation eq 0}"></span></c:if>	
										 	
										 	</span>
										 	
									 	</div> 
									</c:otherwise> 
								</c:choose> 	  
								<c:if test="${boardItem.itemDisplay eq 1 and boardItem.indentation eq 0}"></span></c:if>
							</td> 
						</c:when>
						<c:otherwise>
							<td <c:if test="${boardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if> class="textLeft">
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
									 						<a class="boardItem" <c:if test="${boardItem.readYn=='1' || boardItem.readFlg=='0'}" >style="color:#5D5D5D;"</c:if><c:if test="${boardItem.readYn=='0' && boardItem.readFlg=='1'}" >style="color:#050099;"</c:if> href="<c:url value='/lightpack/board/boardItem/readBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><c:if test="${board.wordHead == 1}"><c:if test="${boardItem.wordName !=null && board.boardId!='100006240370'}">[${boardItem.wordName}]</c:if></c:if>[<ikep4j:message pre='${preList}' key='itemDelete' post="deleteContents"/>] ${boardItem.title}</a>
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
										 		<a class="boardItem"  <c:if test="${board.colorUse == 1 &&(boardItem.readYn=='1' || boardItem.readFlg=='0')}" >style="color:#5D5D5D;"</c:if><c:if test="${board.colorUse == 1 && (boardItem.readYn=='0' && boardItem.readFlg=='1')}" >style="color:#050099;"</c:if> href="<c:url value='/lightpack/board/boardItem/readBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><c:if test="${board.wordHead == 1}"><c:if test="${!empty boardItem.wordName && board.boardId!='100006240370'}">[${boardItem.wordName}]</c:if></c:if> ${boardItem.title}</a>
										 		<c:if test="${boardItem.linereplyCount gt 0}"><span class="colorPoint">(${boardItem.linereplyCount})</span></c:if>
										 		<c:if test="${boardItem.itemDisplay eq 1 and boardItem.indentation eq 0}"></span></c:if>	
										 	
										 	</span>
										 	
									 	</div> 
									</c:otherwise> 
								</c:choose> 	  
								<c:if test="${boardItem.itemDisplay eq 1 and boardItem.indentation eq 0}"></span></c:if>
							</td> 
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${boardItem.boardId=='100010083357'}">
							<td <c:if test="${boardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
								<c:if test="${boardItem.attachFileCount > 0}">
									<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" /> 
								</c:if>
							</td>
						</c:when>
					</c:choose>
					<td <c:if test="${boardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
						<c:choose>
					 		<c:when test="${board.anonymous eq 1}">
								<!-- <span><ikep4j:message pre='${preList}' key='anonymous'/></span> -->
								<span>
									<c:if test="${boardItem.boardId=='100006240370' || boardItem.boardId=='100006259597'}">Happy맨</c:if>
									<c:if test="${boardItem.boardId=='100010083357' || boardItem.boardId=='100010089350' || boardItem.boardId=='100010089362'}">회계정보팀</c:if>
									<c:if test="${boardItem.boardId!='100006240370' && boardItem.boardId!='100006259597' && boardItem.boardId!='100010083357' && boardItem.boardId!='100010089350' && boardItem.boardId!='100010089362'}">${boardItem.displayName}</c:if>
								</span>
							</c:when>  
							<c:otherwise>
								<c:set var="user"   value="${sessionScope['ikep.user']}" /> 
								<c:set var="portal" value="${sessionScope['ikep.portal']}" /> 
								<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}"> <!-- ${boardItem.user.jobTitleName} ${boardItem.user.teamName}--> 
										<c:if test="${boardItem.boardId=='100006240370' || boardItem.boardId=='100006259597'}">Happy맨</c:if>
										<c:if test="${boardItem.boardId=='100010083357' || boardItem.boardId=='100010089350' || boardItem.boardId=='100010089362'}">회계정보팀</c:if>
										<c:if test="${boardItem.boardId!='100006240370' && boardItem.boardId!='100006259597' && boardItem.boardId!='100010083357' && boardItem.boardId!='100010089350' && boardItem.boardId!='100010089362'}">
										<div class="ellipsis"><a title="${boardItem.user.userName}" href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom')">${boardItem.user.userName}<!-- ${boardItem.user.jobTitleName} --> </a></div>
										</c:if>
									</c:when>
									<c:otherwise> 
										<c:if test="${boardItem.boardId=='100006240370' || boardItem.boardId=='100006259597'}">Happy맨</c:if>
										<c:if test="${boardItem.boardId=='100010083357' || boardItem.boardId=='100010089350' || boardItem.boardId=='100010089362'}">회계정보팀</c:if>
										<c:if test="${boardItem.boardId!='100006240370' && boardItem.boardId!='100006259597' && boardItem.boardId!='100010083357' && boardItem.boardId!='100010089350' && boardItem.boardId!='100010089362'}">
										<div class="ellipsis"><a title="${boardItem.user.userEnglishName}" href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom')">${boardItem.user.userEnglishName}<!--  ${boardItem.user.jobTitleEnglishName} --></a></div>
										</c:if>
										
									</c:otherwise>           
								</c:choose>							 
							</c:otherwise> 
						</c:choose>
					</td>
					<td <c:if test="${boardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>><div class="ellipsis"><ikep4j:timezone date="${boardItem.startDate}"/></div></td>
					<td <c:if test="${boardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>${boardItem.hitCount}</td>
					<td <c:if test="${boardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>${boardItem.recommendCount}</td>
				</tr>
				</c:forEach>				      
		    </c:otherwise> 
		</c:choose>  
	</tbody>
</table> 


