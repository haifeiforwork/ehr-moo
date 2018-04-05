<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>  
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.kms.site.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="message.collpack.kms.site.boardItem.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.kms.site.boardItem.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.kms.site.boardItem.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.kms.site.boardItem.message.script" />  
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
				<c:if test="${sitePermission > 1}">
						<input id="checkboxAllSiteItem" class="checkbox" title="checkbox" type="checkbox" value="" />
				</c:if>
			</th> 
			<th scope="col" width="10%">
				<a onclick="sort('ITEM_SEQ_ID', '<c:if test="${searchCondition.sortColumn eq 'ITEM_SEQ_ID'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='itemSeqId' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'ITEM_SEQ_ID' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'ITEM_SEQ_ID' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose> 
			</th>
			<th scope="col" width="3%">
				<a onclick="sort('ATTACH_FILE_COUNT', '<c:if test="${searchCondition.sortColumn eq 'ATTACH_FILE_COUNT'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='attachFileCount' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'ATTACH_FILE_COUNT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'ATTACH_FILE_COUNT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
				</c:choose>
			</th>
			
			<th scope="col" width="9%">
				<!-- 
				<a onclick="sort('SHARE_COUNT', '<c:if test="${searchCondition.sortColumn eq 'SHARE_COUNT'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='shareCount' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'SHARE_COUNT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'SHARE_COUNT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>
				 -->				
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
			<th scope="col" width="15%">
				<a onclick="sort('REGISTER_NAME', '<c:if test="${searchCondition.sortColumn eq 'REGISTER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='registerName' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col" width="20%">
				<a onclick="sort('REGIST_DATE', '<c:if test="${searchCondition.sortColumn eq 'REGIST_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='startDate' />
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'REGIST_DATE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'REGIST_DATE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col" width="10%">
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
					<td colspan="8" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
				</tr>				        
		    </c:when>
		    <c:otherwise>
				<c:forEach var="siteItem" items="${searchResult.entity}">
				<tr class="<c:if test="${itemDisplay eq 1}"></c:if>" name="boardItemLine">
					<td>
						<c:if test="${sitePermission > 1}">
							<input name="siteItemIds" class="checkbox" title="checkbox" type="checkbox" value="${siteItem.itemId}" />
						</c:if> 
					</td>				
					<td>${siteItem.itemSeqId}</td>
					<td>
						<c:if test="${siteItem.attachFileCount > 0}">
							<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" /> 
						</c:if>
					</td>
					<td>
					<!-- 
						<c:choose>
							<c:when test="${siteItem.shareCount>0}">
								<span class="cate_tit_3"><ikep4j:message pre='${preList}' key='share' /></span>
							</c:when>
							<c:otherwise>
								<span class="cate_tit_4"><ikep4j:message pre='${preList}' key='notShare' /></span>
							</c:otherwise>
						</c:choose>
					 -->
					</td>
					<td class="textLeft ellipsis">
						<c:if test="${siteItem.itemDisplay eq 1}"><span class="notice"></c:if> 
						
						<%--c:if test="${siteItem.itemDisplay eq 1}"><span class="cate_tit_5"><ikep4j:message pre='${preList}' key='mReading' /></span></c:if--%>
						<span>
							<c:choose>
								<c:when test="${sitePermission>0}">
									<a name="siteItem" href="<c:url value='/collpack/kms/site/readSiteItemView.do?itemId=${siteItem.itemId}'/>">${siteItem.title}</a>
								</c:when>
								<c:otherwise>
									${siteItem.title}
								</c:otherwise>
							</c:choose>
						</span>
						<!--						 
						<a name="siteItem" href="<c:url value='/collpack/kms/site/readSiteItemView.do?itemId=${siteItem.itemId}'/>">${siteItem.title}</a>
						-->
						<c:if test="${siteItem.itemDisplay eq 1}"></span></c:if>		
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
										<div class="ellipsis"><a title="${siteItem.registerName} ${siteItem.jobTitleName}" href="#a" onclick="iKEP.showUserContextMenu(this, '${siteItem.registerId}', 'bottom')">${siteItem.registerName} ${siteItem.jobTitleName}</a></div>
									</c:when>
									<c:otherwise> 
										<div class="ellipsis"><a title="${siteItem.registerEnglishName} ${siteItem.jobTitleEnglishName}" href="#a" onclick="iKEP.showUserContextMenu(this, '${siteItem.registerId}', 'bottom')">${siteItem.registerEnglishName} ${siteItem.jobTitleEnglishName}</a></div>
									</c:otherwise>           
								</c:choose>							 
							</c:otherwise> 
						</c:choose>						
					</td>
					<td>
						<ikep4j:timezone pattern="yyyy.MM.dd" date="${siteItem.registDate}"/>
					</td>
					<td>${siteItem.hitCount}</td>
				</tr>
				</c:forEach>				      
		    </c:otherwise> 
		</c:choose>  
	</tbody>
</table>