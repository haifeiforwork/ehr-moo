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
 <link rel="stylesheet" type="text/css" href="<c:url value="/base/css/kms/kms.css"/>" />

	<table summary="<ikep4j:message pre='${preList}' key='summary' />">  
	<caption></caption> 
	<thead>
		<tr>
			<th scope="col" width="3%">
				<c:if test="${boardPermission > 0}">
						<input id="checkboxAllBoardItem" class="checkbox" title="checkbox" type="checkbox" value="" />
				</c:if>
			</th> 
			<%-- <th scope="col" width="10%">
				<a onclick="sort('ITEM_SEQ_ID', '<c:if test="${searchCondition.sortColumn eq 'ITEM_SEQ_ID'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='itemSeqId' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'ITEM_SEQ_ID' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'ITEM_SEQ_ID' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th> --%>

			<th scope="col" width="8%">
				<a onclick="sort('IS_KNOWHOW', '<c:if test="${searchCondition.sortColumn eq 'IS_KNOWHOW'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='isKnowhow' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'IS_KNOWHOW' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'IS_KNOWHOW' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>
			</th>
			<th scope="col" width="10%">
				<a onclick="sort('ASSESS_DATE', '<c:if test="${searchCondition.sortColumn eq 'ASSESS_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
					게시일
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'ASSESS_DATE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'ASSESS_DATE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
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
			<c:choose>
				<c:when test="${searchCondition.isKnowhow == 0 || searchCondition.isKnowhow == 3}">
					<th scope="col" width="10%">
						<a onclick="sort('REGISTER_NAME', '<c:if test="${searchCondition.sortColumn eq 'REGISTER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
							<ikep4j:message pre='${preList}' key='registerName' />
						</a>
						<c:choose>
							<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
						</c:choose>				
					</th>
				</c:when>
				<c:otherwise>
					<c:if test="${boardPermission > 0}">
						<th scope="col" width="10%">
						<a onclick="sort('REGISTER_NAME', '<c:if test="${searchCondition.sortColumn eq 'REGISTER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
							<ikep4j:message pre='${preList}' key='registerName' />
						</a>
						<c:choose>
							<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
							<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
						</c:choose>				
					</th>
					</c:if>
				</c:otherwise>
			</c:choose>
			<th scope="col" width="8%">
					보안등급
			</th>
			<th scope="col" width="6%">
					점수
			</th>
			<th scope="col" width="4%">
				<a onclick="sort('RECOMMEND_COUNT', '<c:if test="${searchCondition.sortColumn eq 'RECOMMEND_COUNT'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='recommendCount' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'RECOMMEND_COUNT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'RECOMMEND_COUNT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc"  />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col" width="4%">
				<a onclick="sort('LINEREPLY_COUNT', '<c:if test="${searchCondition.sortColumn eq 'LINEREPLY_COUNT'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='linereplyCount' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'LINEREPLY_COUNT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'LINEREPLY_COUNT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc"  />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col" width="4%">
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
					<c:choose>
						<c:when test="${searchCondition.isKnowhow == 0 || searchCondition.isKnowhow == 3}">
							<td colspan="11" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
						</c:when>
						<c:otherwise>
							<c:if test="${boardPermission > 0}">
								<td colspan="11" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
							</c:if>
							<c:if test="${boardPermission < 1}">
								<td colspan="10" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
							</c:if> 
						</c:otherwise>
					</c:choose>
				</tr>				        
		    </c:when>
		    <c:otherwise>
				<c:forEach var="announceItem" items="${searchResult.entity}">
				<tr <c:if test="${announceItem.itemDisplay=='1'}">style="background-color:#f9f9f9;"</c:if>>
					<td>
						<c:if test="${boardPermission > 0}">
							<input name="boardItemIds" class="checkbox" title="checkbox" type="checkbox" value="${announceItem.itemId}" />
						</c:if> 
					</td>	
					<%-- <td>${announceItem.itemSeqId}</td> --%>

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
						<ikep4j:timezone pattern="yyyy.MM.dd" date="${announceItem.assessDate}"/>
					</td>
					<td>
						<ikep4j:timezone pattern="yyyy.MM.dd" date="${announceItem.registDate}"/>
					</td>

					<td style="text-align:left;<c:if test="${announceItem.itemDisplay=='1'}">font-weight:bold;</c:if>">
						<c:if test="${announceItem.status == 5}">
						<span class="deletedItem">
						<span>
						<B>
							<a name="announceItem" href="<c:url value='/collpack/kms/board/boardItem/readBoardItemView.do?itemId=${announceItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}&amp;pageGubun=${pageGubun}'/>">${announceItem.title}</a>
						</B>
						</span>	
						</span>
						</c:if>
						<c:if test="${announceItem.status != 5}">
							<a name="announceItem" href="<c:url value='/collpack/kms/board/boardItem/readBoardItemView.do?itemId=${announceItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}&amp;pageGubun=${pageGubun}'/>"><span class="font_${announceItem.color}">${announceItem.title}</span></a>
						</c:if>
					</td>
					<td>${announceItem.targetSource}</td> 
					<c:choose>
						<c:when test="${searchCondition.isKnowhow == 0 || searchCondition.isKnowhow == 3}">
							<td>
								<div class="ellipsis"><a title="${announceItem.registerName}" href="#a" onclick="iKEP.showUserContextMenu(this, '${announceItem.registerId}', 'bottom')">${announceItem.registerName}</a></div>
							</td>
						</c:when>
						<c:otherwise>
							<c:if test="${boardPermission > 0}">
								<td>
									<div class="ellipsis"><a title="${announceItem.registerName}" href="#a" onclick="iKEP.showUserContextMenu(this, '${announceItem.registerId}', 'bottom')">${announceItem.registerName}</a></div>
								</td>
							</c:if>
						</c:otherwise>
					</c:choose>
					<td>
						<c:choose>
							<c:when test="${announceItem.infoGrade == 'A'}">
								보안
							</c:when>
							<c:when test="${announceItem.infoGrade == 'B'}">
								보안
							</c:when>
							<c:when test="${announceItem.infoGrade == 'C'}">
								공유
							</c:when>
							<c:otherwise>공유</c:otherwise>
						</c:choose>
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
					</td>
					<td>${announceItem.recommendCount}</td>
					<td>${announceItem.linereplyCount}</td>
					<td>${announceItem.hitCount}</td>
				</tr>
				</c:forEach>				      
		    </c:otherwise> 
		</c:choose>  
	</tbody>
</table>