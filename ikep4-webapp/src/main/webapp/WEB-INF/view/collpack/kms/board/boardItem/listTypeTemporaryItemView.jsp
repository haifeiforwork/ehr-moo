<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>  
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.kms.board.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="message.collpack.kms.board.boardItem.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.kms.board.boardItem.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.kms.board.boardItem.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.kms.board.boardItem.message.script" />  
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- //세션정보 선언 End --%>


	<table summary="<ikep4j:message pre='${preList}' key='summary' />">  
	<caption></caption> 
	<thead>
		<tr>
			<th scope="col" width="10%">
				<a onclick="sort('ITEM_SEQ_ID', '<c:if test="${searchCondition.sortColumn eq 'ITEM_SEQ_ID'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='itemSeqId' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'ITEM_SEQ_ID' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'ITEM_SEQ_ID' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<th scope="col" width="8%">
				<a onclick="sort('IS_KNOWHOW', '<c:if test="${searchCondition.sortColumn eq 'IS_KNOWHOW'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='isKnowhow' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'IS_KNOWHOW' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'IS_KNOWHOW' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>
			</th>
			<th scope="col" width="8%">
				<a onclick="sort('STATUS', '<c:if test="${searchCondition.sortColumn eq 'STATUS'}">${searchCondition.sortType}</c:if>');"  href="#a">
					상태
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'STATUS' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'STATUS' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>
			</th>

			<th scope="col" width="10%">
				<a onclick="sort('REGIST_DATE', '<c:if test="${searchCondition.sortColumn eq 'REGIST_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='startDate' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'REGIST_DATE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'REGIST_DATE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			
			<th scope="col" width="*">
				<a onclick="sort('TITLE', '<c:if test="${searchCondition.sortColumn eq 'TITLE'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='title' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'TITLE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'TITLE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col" width="10%">
				<a onclick="sort('TARGET_SOURCE', '<c:if test="${searchCondition.sortColumn eq 'TARGET_SOURCE'}">${searchCondition.sortType}</c:if>');"  href="#a">
					출처
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'TARGET_SOURCE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'TARGET_SOURCE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col" width="10%">
				<a onclick="sort('REGISTER_NAME', '<c:if test="${searchCondition.sortColumn eq 'REGISTER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='registerName' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<%-- <th scope="col" width="3%">
				<a onclick="sort('ATTACH_FILE_COUNT', '<c:if test="${searchCondition.sortColumn eq 'ATTACH_FILE_COUNT'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='attachFileCount' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'ATTACH_FILE_COUNT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'ATTACH_FILE_COUNT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
				</c:choose>
			</th> --%>
			<th scope="col" width="6%">
					점수
			</th>
			<c:if test="${gubun eq '2'}">
				<th scope="col" width="6%">
					<a onclick="sort('INFO_GRADE', '<c:if test="${searchCondition.sortColumn eq 'INFO_GRADE'}">${searchCondition.sortType}</c:if>');"  href="#a">
						등급
					</a>
					<c:choose>
						<c:when test="${searchCondition.sortColumn eq 'INFO_GRADE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
						<c:when test="${searchCondition.sortColumn eq 'INFO_GRADE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc"  />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
					</c:choose>				
				</th>
			</c:if>
			
			<th scope="col" width="6%">
				<a onclick="sort('RECOMMEND_COUNT', '<c:if test="${searchCondition.sortColumn eq 'RECOMMEND_COUNT'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='recommendCount' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'RECOMMEND_COUNT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'RECOMMEND_COUNT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc"  />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col" width="6%">
				<a onclick="sort('LINEREPLY_COUNT', '<c:if test="${searchCondition.sortColumn eq 'LINEREPLY_COUNT'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='linereplyCount' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'LINEREPLY_COUNT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'LINEREPLY_COUNT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc"  />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col" width="6%">
				<a onclick="sort('HIT_COUNT', '<c:if test="${searchCondition.sortColumn eq 'HIT_COUNT'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='hitCount' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'HIT_COUNT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'HIT_COUNT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc"  />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
		</tr>
	</thead> 
	<tbody>
		<c:choose>
		    <c:when test="${searchResult.emptyRecord}">
				<tr>
					<td colspan="11" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
				</tr>				        
		    </c:when>
		    <c:otherwise>
				<c:forEach var="announceItem" items="${searchResult.entity}">
				<tr>
					<td>${announceItem.itemSeqId}</td>
					<td>
						<c:if test="${announceItem.isKnowhow == 0}">
							<ikep4j:message pre='${preList}' key='isKnowhow0' />
						</c:if>
						<c:if test="${announceItem.isKnowhow == 1}">
							<ikep4j:message pre='${preList}' key='isKnowhow1' />
						</c:if>
						<c:if test="${announceItem.isKnowhow == 3}">
							원문 게시판
						</c:if>
					</td>
					<td>
						<c:choose>
							<c:when test="${announceItem.status == '0'}">
								임시저장
							</c:when>
							<c:when test="${announceItem.status == '1' || announceItem.status == '2'}">
								등록완료
							</c:when>
							<c:when test="${(announceItem.status == '3' || announceItem.status == '5') && announceItem.infoGrade != 'E'}">
								채택완료
							</c:when>
							<c:when test="${(announceItem.status == '3' || announceItem.status == '5') && announceItem.infoGrade == 'E'}">
								미공유지식
							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</td>  
					<td>
						<ikep4j:timezone pattern="yyyy.MM.dd" date="${announceItem.registDate}"/>
					</td>

					<td class="textLeft">
						<c:if test="${announceItem.itemDisplay eq 1}"><span class="notice"></c:if> 
						<c:if test="${announceItem.status == 5}">
						<span class="deletedItem">
						</c:if>
						<span>
							<c:if test="${gubun eq '0'}">
								<a name="announceItem" href="<c:url value='/collpack/kms/board/boardItem/readTemporaryItemView.do?itemId=${announceItem.itemId}&gubun=${gubun}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>">${announceItem.title}</a>
							</c:if>
							<c:if test="${gubun eq '1'}">
								<a name="announceItem" href="<c:url value='/collpack/kms/board/boardItem/readTemporaryItemView.do?itemId=${announceItem.itemId}&gubun=${gubun}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>">${announceItem.title}</a>
							</c:if>
							<c:if test="${gubun eq '2'}">
								<a name="announceItem" href="<c:url value='/collpack/kms/board/boardItem/readTemporaryItemView.do?itemId=${announceItem.itemId}&gubun=${gubun}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>">${announceItem.title}</a>
							</c:if>
							<c:if test="${gubun eq '3'}">
								<a name="announceItem" href="<c:url value='/collpack/kms/board/boardItem/readTemporaryItemView.do?itemId=${announceItem.itemId}&gubun=${gubun}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>">${announceItem.title}</a>
							</c:if>
							<c:if test="${gubun eq '4'}">
								<a name="announceItem" href="<c:url value='/collpack/kms/board/boardItem/readTemporaryItemView.do?itemId=${announceItem.itemId}&gubun=${gubun}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>">${announceItem.title}</a>
							</c:if>
						</span>	
					</td>
					<td>${announceItem.targetSource}</td>  
					<td>
						<div class="ellipsis"><a title="${announceItem.registerName}" href="#a" onclick="iKEP.showUserContextMenu(this, '${announceItem.registerId}', 'bottom')">${announceItem.registerName}</a></div>
					</td>
					<td>
						<c:choose>
							<c:when test="${announceItem.status == '0'}">
								0
							</c:when>
							<c:when test="${announceItem.status == '1' || announceItem.status == '2'}">
								0
							</c:when>
							<c:when test="${(announceItem.status == '3' || announceItem.status == '5') && announceItem.infoGrade != 'E'}">
								${announceItem.mark}
							</c:when>
							<c:when test="${(announceItem.status == '3' || announceItem.status == '5') && announceItem.infoGrade == 'E'}">
								0
							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
						<%-- <c:if test="${announceItem.attachFileCount > 0}">
							<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" /> 
						</c:if> --%>
					</td>
					<c:if test="${gubun eq '2'}">
						<td>
							<c:choose>
							<c:when test="${announceItem.infoGrade == 'B'}">
								보안
							</c:when>
							<c:when test="${announceItem.infoGrade == 'C'}">
								공유
							</c:when>
							<c:otherwise>공유</c:otherwise>
						</c:choose>
						</td>
					</c:if>
					
					<td>${announceItem.recommendCount}</td>
					<td>${announceItem.linereplyCount}</td>
					<td>${announceItem.hitCount}</td>
				</tr>
				</c:forEach>				      
		    </c:otherwise> 
		</c:choose>  
	</tbody>
</table>