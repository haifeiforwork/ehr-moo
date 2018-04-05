<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>  
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.kms.announce.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="message.collpack.kms.announce.boardItem.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.kms.announce.boardItem.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.kms.announce.boardItem.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.kms.announce.boardItem.message.script" />  
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- //세션정보 선언 End --%>


	<table summary="<ikep4j:message pre='${preList}' key='summary' />">  
	<caption></caption> 
	<thead>
		<tr>
			<th scope="col" width="3%">
				<c:if test="${qnaPermission > 1}">
						<input id="checkboxAllQnaItem" class="checkbox" title="checkbox" type="checkbox" value="" />
				</c:if>
			</th> 
			<th scope="col" width="7%">
				<a onclick="sort('ITEM_SEQ_ID', '<c:if test="${searchCondition.sortColumn eq 'ITEM_SEQ_ID'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='itemSeqId' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'ITEM_SEQ_ID' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'ITEM_SEQ_ID' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
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
			<th scope="col" width="10%">
					상태
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
				<a onclick="sort('REGISTER_NAME', '<c:if test="${searchCondition.sortColumn eq 'REGISTER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='registerName' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
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
			<th scope="col" width="7%">
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
					<td colspan="7" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
				</tr>				        
		    </c:when>
		    <c:otherwise>
				<c:forEach var="qnaItem" items="${searchResult.entity}" varStatus="status">
				<c:choose> 
							<c:when test="${empty searchCondition.searchWord and empty searchCondition.sortColumn}">
								<c:set var="indent" value="${qnaItem.indentation}"/>
							</c:when>
							<c:otherwise>
								<c:set var="indent" value="0"/>
							</c:otherwise>
						</c:choose>
				<tr class="<c:if test="${itemDisplay eq 1}"></c:if>" name="boardItemLine"  onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
					<td>
						<c:if test="${qnaPermission > 1}">
							<input name="qnaItemIds" class="checkbox" title="checkbox" type="checkbox" value="${qnaItem.itemId}" />
						</c:if> 
					</td>				
					<td>
					${searchCondition.recordCount -((searchCondition.pageIndex - 1) * searchCondition.pagePerRecord) - status.index}
					</td>
					<%-- <td>
						<c:if test="${qnaItem.attachFileCount > 0}">
							<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" /> 
						</c:if>
					</td> --%>
					<td>
						<c:choose>
					 		<c:when test="${qnaItem.indentation != '0'}">
								<c:if test="${qnaItem.recommendCount != '0'}">
									<span>채택</span>
								</c:if>  
							</c:when>  
							<c:when test="${qnaItem.indentation == '0'}">
								<c:choose>
							 		<c:when test="${qnaItem.chooseCnt > 0}">
										<span>채택완료</span>
									</c:when>  
									<c:otherwise>
										<span>채택중</span>
									</c:otherwise> 
								</c:choose>	
							</c:when> 
							<c:otherwise>
							</c:otherwise> 
						</c:choose>	
					</td>
					<td class="textLeft ellipsis">
						<c:if test="${qnaItem.itemDisplay eq 1}"><span class="qna"></c:if> 
						
						<%--c:if test="${qnaItem.itemDisplay eq 1}"><span class="cate_tit_5"><ikep4j:message pre='${preList}' key='mReading' /></span></c:if--%>
							<span class="indent_${indent}">
								<a name="qnaItem" href="<c:url value='/collpack/kms/qna/readQnaItemView.do?itemId=${qnaItem.itemId}'/>">${qnaItem.title}</a>
							</span>
						<!--						 
						<a name="qnaItem" href="<c:url value='/collpack/kms/qna/readQnaItemView.do?itemId=${qnaItem.itemId}'/>">${qnaItem.title}</a>
						-->
						<c:if test="${qnaItem.itemDisplay eq 1}"></span></c:if>		
					</td> 
					<td>
						<c:choose>
					 		<c:when test="${board.anonymous eq 1}">
								<span><ikep4j:message pre='${preList}' key='anonymous'/></span>
							</c:when>  
							<c:otherwise>
								<c:set var="user"   value="${sessionScope['ikep.user']}" /> 
								<c:set var="portal" value="${sessionScope['ikep.portal']}" /> 
								<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
										<div class="ellipsis"><a title="${qnaItem.registerName} ${qnaItem.jobTitleName}" href="#a" onclick="iKEP.showUserContextMenu(this, '${qnaItem.registerId}', 'bottom')">${qnaItem.registerName} ${qnaItem.jobTitleName}</a></div>
									</c:when>
									<c:otherwise> 
										<div class="ellipsis"><a title="${qnaItem.registerEnglishName} ${qnaItem.jobTitleEnglishName}" href="#a" onclick="iKEP.showUserContextMenu(this, '${qnaItem.registerId}', 'bottom')">${qnaItem.registerEnglishName} ${qnaItem.jobTitleEnglishName}</a></div>
									</c:otherwise>           
								</c:choose>							 
							</c:otherwise> 
						</c:choose>						
					</td>
					<td>
						<ikep4j:timezone pattern="yyyy.MM.dd" date="${qnaItem.registDate}"/>
					</td>
					
					<td>${qnaItem.hitCount}</td>
				</tr>
				</c:forEach>				      
		    </c:otherwise> 
		</c:choose>  
	</tbody>
</table>