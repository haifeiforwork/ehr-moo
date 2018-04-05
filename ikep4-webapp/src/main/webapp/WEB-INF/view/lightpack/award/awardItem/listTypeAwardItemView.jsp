<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.lightpack.award.awardItem.listAwardView.header" /> 
<c:set var="preList"    value="ui.lightpack.award.awardItem.listAwardView.list" />
<c:set var="preButton"  value="ui.lightpack.common.button" /> 
<c:set var="preMessage" value="message.lightpack.common.awardItem" />
<c:set var="preSearch"  value="ui.ikep4.common.searchCondition" />  
<%-- 메시지 관련 Prefix 선언 End --%>
<!--blockListTable Start-->    
<table id="awardItemTable" summary="<ikep4j:message pre="${preList}" key="summary" />">   
	<caption></caption> 
	<c:if test="${userAuthMgntYn}"> <%-- 검색어가 없고 관리자라면 --> 링크 허용--%>
		<col style="width: 3%;"/>
	</c:if>		
	<col style="width: 5%;"/>
	<col style="width: 5%;"/>
	<%-- <c:if test="${award.awardId=='100006240370'}">
		<col style="width: 8%;"/>
		<col style="width: 10%;"/>
		<c:if test="${permission.isSystemAdmin || awardAdminRole}">
			<col style="width: 10%;"/>
		</c:if>
	</c:if> --%>
	<col style="width: 7%;"/>
	<%-- <c:choose>
		<c:when test="${award.awardId=='100010083357'}">
			<col style="width: 5%;"/>
		</c:when>
	</c:choose> --%>
	<col style="width: 7%;"/>
	<col style="width: *;"/>
	<col style="width: 7%;"/>
	<col style="width: 7%;"/>
	<col style="width: 10%;"/>
	<col style="width: 7%;"/>
	<col style="width: 7%;"/>
	<col style="width: 5%;"/>
	<col style="width: 7%;"/>
	<thead>
		<tr>
			<c:if test="${userAuthMgntYn}"> <%-- 검색어가 없고 관리자라면 --> 링크 허용--%>
				<th scope="col"><!-- <input id="checkboxAllAwardItem" class="checkbox" title="checkbox" type="checkbox" value="" /> --></th> 
			</c:if>		 
			<th scope="col">
				<ikep4j:message pre='${preList}' key='itemSeqId' />
			</th>
			<%-- <c:choose>
				<c:when test="${award.awardId=='100010083357'}">
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
			
			<c:if test="${award.awardId=='100006240370'}">
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
				<c:if test="${permission.isSystemAdmin || awardAdminRole}">
					<th scope="col">
						사업장
					</th>
				</c:if>
			</c:if> --%>
			
			<th scope="col">
				<a onclick="sort('AWARD_KIND', '<c:if test="${searchCondition.sortColumn eq 'AWARD_KIND'}">${searchCondition.sortType}</c:if>');"  href="#a">
					종류
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'AWARD_KIND' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'AWARD_KIND' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col">
				<a onclick="sort('COMPANY_CODE', '<c:if test="${searchCondition.sortColumn eq 'COMPANY_CODE'}">${searchCondition.sortType}</c:if>');"  href="#a">
					회사
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'COMPANY_CODE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'COMPANY_CODE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col">
				<a onclick="sort('AWARD_DATE', '<c:if test="${searchCondition.sortColumn eq 'AWARD_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
					날짜
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'AWARD_DATE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'AWARD_DATE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col">
				<a onclick="sort('AWARD_TITLE', '<c:if test="${searchCondition.sortColumn eq 'AWARD_TITLE'}">${searchCondition.sortType}</c:if>');"  href="#a">
					행사명
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'AWARD_TITLE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'AWARD_TITLE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col">
				<a onclick="sort('AWARD_TXT', '<c:if test="${searchCondition.sortColumn eq 'AWARD_TXT'}">${searchCondition.sortType}</c:if>');"  href="#a">
					내역
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'AWARD_TXT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'AWARD_TXT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col">
				<a onclick="sort('PUBLISHER', '<c:if test="${searchCondition.sortColumn eq 'PUBLISHER'}">${searchCondition.sortType}</c:if>');"  href="#a">
					발행처
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'PUBLISHER' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'PUBLISHER' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col">
				<a onclick="sort('AWARD_MATERIAL', '<c:if test="${searchCondition.sortColumn eq 'AWARD_MATERIAL'}">${searchCondition.sortType}</c:if>');"  href="#a">
					자료
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'AWARD_MATERIAL' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'AWARD_MATERIAL' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col">
				<a onclick="sort('STORAGE_LOC_CD', '<c:if test="${searchCondition.sortColumn eq 'STORAGE_LOC_CD'}">${searchCondition.sortType}</c:if>');"  href="#a">
					보관위치
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'STORAGE_LOC_CD' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'STORAGE_LOC_CD' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<th scope="col">
				<a onclick="sort('AWARD_GRADE', '<c:if test="${searchCondition.sortColumn eq 'AWARD_GRADE'}">${searchCondition.sortType}</c:if>');"  href="#a">
					등급
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'AWARD_GRADE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'AWARD_GRADE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
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
				<a onclick="sort('RES_DEPT_ID', '<c:if test="${searchCondition.sortColumn eq 'RES_DEPT_ID'}">${searchCondition.sortType}</c:if>');"  href="#a">
					주무부서
				</a>
				<c:choose>
					<c:when test="${searchCondition.sortColumn eq 'RES_DEPT_ID' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'RES_DEPT_ID' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				</c:choose>				
			</th>
			<%-- <th scope="col">
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
			</th> --%>
		</tr>
	</thead> 
	<tbody>
		<c:choose>
		    <c:when test="${searchResult.emptyRecord}">
		    	<c:choose>
					<c:when test="${awardItem.awardId=='100010083357'}">
						<tr>
							<td colspan="${userAuthMgntYn ? 9 : 8}" class="emptyRecord"><ikep4j:message pre='${preList}' key='emptyRecord' /></td>
						</tr>		
					</c:when>
					<c:otherwise>
						<tr>
							<c:if test="${award.awardId=='100006240370'}">
								<c:choose>
									<c:when test="${userAuthMgntYn || awardAdminRole}">
										<td colspan="${userAuthMgntYn ? 11 : 10}" class="emptyRecord">
									</c:when>
									 <c:otherwise>
									 	<td colspan="${userAuthMgntYn ? 10 : 9}" class="emptyRecord">
									 </c:otherwise>
								 </c:choose>
								<ikep4j:message pre='${preList}' key='emptyRecord' /></td>
							</c:if>
							<c:if test="${award.awardId!='100006240370'}">
								<td colspan="${userAuthMgntYn ? 13 : 12}" class="emptyRecord"><ikep4j:message pre='${preList}' key='emptyRecord' /></td>
							</c:if>
						</tr>		
					</c:otherwise>
				</c:choose>
						        
		    </c:when>
		    <c:otherwise>
				<c:forEach var="awardItem" items="${searchResult.entity}" varStatus="status"> 
				<tr  onmouseover="mouseOver(this);" <c:if test="${awardItem.itemDisplay eq 1}">onmouseout="mouseOut2(this);"</c:if><c:if test="${awardItem.itemDisplay ne 1}">onmouseout="mouseOut(this);"</c:if>>
					<c:if test="${userAuthMgntYn}"> <%--[관리자]일괄 삭제를 위한 체크박스 허용--%>
						<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
							<%-- <input name="checkboxAwardItem" class="checkbox" title="checkbox" type="checkbox" value="${awardItem.itemId}" />  --%>
							<input name="radioboxAwardItem" class="radio" type="radio" value="${awardItem.itemId}" />
						</td>
					</c:if> 						
					<% /* %><td>${awardItem.itemSeqId}</td> <% */ %>
					<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
						<c:if test="${awardItem.itemDisplay eq 1}">
					        <img src="<c:url value='/base/images/icon/ic_notice.gif' />" alt="notice" /> 
					    </c:if>
					    <c:if test="${awardItem.itemDisplay ne 1}">
						${searchCondition.recordCount -((searchCondition.pageIndex - 1) * searchCondition.pagePerRecord) - status.index}
						</c:if>
					</td>
					<%-- <c:choose>
						<c:when test="${awardItem.awardId=='100010083357'}">
							<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
								${awardItem.wordName}
							</td>
						</c:when>
						<c:otherwise>
							<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
								<c:if test="${awardItem.attachFileCount > 0}">
									<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" /> 
								</c:if>
							</td>
						</c:otherwise>
					</c:choose> --%>
					<%-- <c:if test="${award.awardId=='100006240370'}">
						<td class="textCenter" <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>[${awardItem.wordName}]</td>
						<td class="textCenter" <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
							<c:choose> 
								<c:when test="${awardItem.wordName == '접수'}">
									<img src="<c:url value='/base/images/ess/red_icon.gif'/>">
								</c:when>
								<c:when test="${awardItem.wordName == '완료'}">
									<img src="<c:url value='/base/images/ess/green_icon.gif'/>">
								</c:when>
								<c:when test="${awardItem.wordName == '진행 중'}">
									<img src="<c:url value='/base/images/ess/yellow_icon.gif'/>">
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</td>
						<c:if test="${permission.isSystemAdmin || awardAdminRole}">
						<td class="textCenter" <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
							<c:choose> 
								<c:when test="${empty awardItem.workplaceName}">
									전체
								</c:when>
								<c:otherwise>
									${awardItem.workplaceName}
								</c:otherwise>
							</c:choose>
						</td>
						</c:if>
					</c:if> --%>
					<%-- <c:choose>
						<c:when test="${awardItem.awardId=='100010083357'}">
							<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if> class="textLeft">
								<c:if test="${awardItem.itemDisplay eq 1 and awardItem.indentation eq 0}"><span class="notice"></c:if> 
								<c:choose> 
									<c:when test="${empty searchCondition.searchWord and empty searchCondition.sortColumn}">
										<c:set var="indent" value="${awardItem.indentation}"/>
									</c:when>
									<c:otherwise>
										<c:set var="indent" value="0"/>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${awardItem.itemDelete eq 1}">논리적으로 삭제된 글
										<c:choose>
									 		<c:when test="${permission.isSystemAdmin}"> 관리자, 작성자일 경우 
									 			<div class="ellipsis">
									 				<c:if test="${awardItem.itemDisplay eq 1 and awardItem.indentation eq 0}"><span class="notice"></c:if> 
									 				<span class="indent_${indent}">
									 					<span class="deletedItem">
									 						<a class="awardItem" <c:if test="${awardItem.readYn=='1' || awardItem.readFlg=='0'}" >style="color:#5D5D5D;"</c:if><c:if test="${awardItem.readYn=='0' && awardItem.readFlg=='1'}" >style="color:#050099;"</c:if> href="<c:url value='/lightpack/award/awardItem/readAwardItemView.do?awardId=${awardItem.awardId}&amp;itemId=${awardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>">[<ikep4j:message pre='${preList}' key='itemDelete' post="deleteContents"/>] ${awardItem.title}</a>
									 					</span> 
									 				    <c:if test="${awardItem.linereplyCount gt 0}"><span class="colorPoint">(${awardItem.linereplyCount})</span></c:if>
									 				</span> 
									 				<c:if test="${awardItem.itemDisplay eq 1 and awardItem.indentation eq 0}"></span></c:if>	
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
										 	
										 		<c:if test="${(awardItem.itemDisplay eq 1 and awardItem.indentation eq 0) or (awardItem.wordName !=null and awardItem.indentation eq 0)}"><span class="notice"></c:if>
										 		<a class="awardItem"  <c:if test="${award.colorUse == 1 &&(awardItem.readYn=='1' || awardItem.readFlg=='0')}" >style="color:#5D5D5D;"</c:if><c:if test="${award.colorUse == 1 && (awardItem.readYn=='0' && awardItem.readFlg=='1')}" >style="color:#050099;"</c:if> href="<c:url value='/lightpack/award/awardItem/readAwardItemView.do?awardId=${awardItem.awardId}&amp;itemId=${awardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"> ${awardItem.title}</a>
										 		<c:if test="${awardItem.linereplyCount gt 0}"><span class="colorPoint">(${awardItem.linereplyCount})</span></c:if>
										 		<c:if test="${awardItem.itemDisplay eq 1 and awardItem.indentation eq 0}"></span></c:if>	
										 	
										 	</span>
										 	
									 	</div> 
									</c:otherwise> 
								</c:choose> 	  
								<c:if test="${awardItem.itemDisplay eq 1 and awardItem.indentation eq 0}"></span></c:if>
							</td> 
						</c:when>
						<c:otherwise>
							<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if> class="textLeft">
								<c:if test="${awardItem.itemDisplay eq 1 and awardItem.indentation eq 0}"><span class="notice"></c:if> 
								<c:choose> 
									<c:when test="${empty searchCondition.searchWord and empty searchCondition.sortColumn}">
										<c:set var="indent" value="${awardItem.indentation}"/>
									</c:when>
									<c:otherwise>
										<c:set var="indent" value="0"/>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${awardItem.itemDelete eq 1}">논리적으로 삭제된 글
										<c:choose>
									 		<c:when test="${permission.isSystemAdmin}"> 관리자, 작성자일 경우 
									 			<div class="ellipsis">
									 				<c:if test="${awardItem.itemDisplay eq 1 and awardItem.indentation eq 0}"><span class="notice"></c:if> 
									 				<span class="indent_${indent}">
									 					<span class="deletedItem">
									 						<a class="awardItem" <c:if test="${awardItem.readYn=='1' || awardItem.readFlg=='0'}" >style="color:#5D5D5D;"</c:if><c:if test="${awardItem.readYn=='0' && awardItem.readFlg=='1'}" >style="color:#050099;"</c:if> href="<c:url value='/lightpack/award/awardItem/readAwardItemView.do?awardId=${awardItem.awardId}&amp;itemId=${awardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><c:if test="${award.wordHead == 1}"><c:if test="${awardItem.wordName !=null && award.awardId!='100006240370'}">[${awardItem.wordName}]</c:if></c:if>[<ikep4j:message pre='${preList}' key='itemDelete' post="deleteContents"/>] ${awardItem.title}</a>
									 					</span> 
									 				    <c:if test="${awardItem.linereplyCount gt 0}"><span class="colorPoint">(${awardItem.linereplyCount})</span></c:if>
									 				</span> 
									 				<c:if test="${awardItem.itemDisplay eq 1 and awardItem.indentation eq 0}"></span></c:if>	
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
										 	
										 		<c:if test="${(awardItem.itemDisplay eq 1 and awardItem.indentation eq 0) or (awardItem.wordName !=null and awardItem.indentation eq 0)}"><span class="notice"></c:if>
										 		<a class="awardItem"  <c:if test="${award.colorUse == 1 &&(awardItem.readYn=='1' || awardItem.readFlg=='0')}" >style="color:#5D5D5D;"</c:if><c:if test="${award.colorUse == 1 && (awardItem.readYn=='0' && awardItem.readFlg=='1')}" >style="color:#050099;"</c:if> href="<c:url value='/lightpack/award/awardItem/readAwardItemView.do?awardId=${awardItem.awardId}&amp;itemId=${awardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><c:if test="${award.wordHead == 1}"><c:if test="${!empty awardItem.wordName && award.awardId!='100006240370'}">[${awardItem.wordName}]</c:if></c:if> ${awardItem.title}</a>
										 		<c:if test="${awardItem.linereplyCount gt 0}"><span class="colorPoint">(${awardItem.linereplyCount})</span></c:if>
										 		<c:if test="${awardItem.itemDisplay eq 1 and awardItem.indentation eq 0}"></span></c:if>	
										 	
										 	</span>
										 	
									 	</div> 
									</c:otherwise> 
								</c:choose> 	  
								<c:if test="${awardItem.itemDisplay eq 1 and awardItem.indentation eq 0}"></span></c:if>
							</td> 
						</c:otherwise>
					</c:choose> --%>
					<%-- <c:choose>
						<c:when test="${awardItem.awardId=='100010083357'}">
							<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
								<c:if test="${awardItem.attachFileCount > 0}">
									<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" /> 
								</c:if>
							</td>
						</c:when>
					</c:choose>
					<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
						<c:choose>
					 		<c:when test="${award.anonymous eq 1}">
								<!-- <span><ikep4j:message pre='${preList}' key='anonymous'/></span> -->
								<span>
									<c:if test="${awardItem.awardId=='100006240370' || awardItem.awardId=='100006259597'}">Happy맨</c:if>
									<c:if test="${awardItem.awardId=='100010083357' || awardItem.awardId=='100010089350' || awardItem.awardId=='100010089362'}">회계정보팀</c:if>
									<c:if test="${awardItem.awardId!='100006240370' && awardItem.awardId!='100006259597' && awardItem.awardId!='100010083357' && awardItem.awardId!='100010089350' && awardItem.awardId!='100010089362'}">${awardItem.displayName}</c:if>
								</span>
							</c:when>  
							<c:otherwise>
								<c:set var="user"   value="${sessionScope['ikep.user']}" /> 
								<c:set var="portal" value="${sessionScope['ikep.portal']}" /> 
								<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}"> <!-- ${awardItem.user.jobTitleName} ${awardItem.user.teamName}--> 
										<c:if test="${awardItem.awardId=='100006240370' || awardItem.awardId=='100006259597'}">Happy맨</c:if>
										<c:if test="${awardItem.awardId=='100010083357' || awardItem.awardId=='100010089350' || awardItem.awardId=='100010089362'}">회계정보팀</c:if>
										<c:if test="${awardItem.awardId!='100006240370' && awardItem.awardId!='100006259597' && awardItem.awardId!='100010083357' && awardItem.awardId!='100010089350' && awardItem.awardId!='100010089362'}">
										<div class="ellipsis"><a title="${awardItem.user.userName}" href="#a" onclick="iKEP.showUserContextMenu(this, '${awardItem.registerId}', 'bottom')">${awardItem.user.userName}<!-- ${awardItem.user.jobTitleName} --> </a></div>
										</c:if>
									</c:when>
									<c:otherwise> 
										<c:if test="${awardItem.awardId=='100006240370' || awardItem.awardId=='100006259597'}">Happy맨</c:if>
										<c:if test="${awardItem.awardId=='100010083357' || awardItem.awardId=='100010089350' || awardItem.awardId=='100010089362'}">회계정보팀</c:if>
										<c:if test="${awardItem.awardId!='100006240370' && awardItem.awardId!='100006259597' && awardItem.awardId!='100010083357' && awardItem.awardId!='100010089350' && awardItem.awardId!='100010089362'}">
										<div class="ellipsis"><a title="${awardItem.user.userEnglishName}" href="#a" onclick="iKEP.showUserContextMenu(this, '${awardItem.registerId}', 'bottom')">${awardItem.user.userEnglishName}<!--  ${awardItem.user.jobTitleEnglishName} --></a></div>
										</c:if>
										
									</c:otherwise>           
								</c:choose>							 
							</c:otherwise> 
						</c:choose> 
					</td>--%>
					<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>${awardItem.awardKindTxt}</td>
					<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>${awardItem.companyCodeTxt}</td>
					<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>${awardItem.awardDate}</td>
					<td 
						<c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>><a href="<c:url value='/lightpack/award/awardItem/readAwardItemView.do?awardId=${awardItem.awardId}&amp;itemId=${awardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>">${awardItem.awardTitle}</a>
					</td>
					<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>${awardItem.awardTxt}</td>
					<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>${awardItem.publisher}</td>
					<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>${awardItem.awardMaterial}</td>
					<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>${awardItem.storageLocTxt} ${awardItem.storageLocDetail}</td>
					<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>${awardItem.awardGrade}</td>
					<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>
						<c:if test="${awardItem.attachFileCount > 0}">
							<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" /> 
						</c:if>
					</td>
					<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>${awardItem.resDeptId}</td>
					<%-- <td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>><div class="ellipsis"><ikep4j:timezone date="${awardItem.startDate}"/></div></td>
					<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>${awardItem.hitCount}</td>
					<td <c:if test="${awardItem.itemDisplay eq 1}">style="background-color:#f9f9f9;"</c:if>>${awardItem.recommendCount}</td> --%>
				</tr>
				</c:forEach>				      
		    </c:otherwise> 
		</c:choose>  
	</tbody>
</table> 


