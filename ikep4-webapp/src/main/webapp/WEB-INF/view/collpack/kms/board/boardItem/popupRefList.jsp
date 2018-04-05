<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.popup.user.header" /> 
<c:set var="preList"    value="ui.support.popup.user.list" />
<c:set var="preDetail"  value="ui.support.popup.user.detail" />
<c:set var="preButton"  value="ui.support.popup.button" /> 
<c:set var="preMessage" value="ui.support.popup.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript" language="javascript">

(function($) {
	
	$jq(document).ready(function() { 
		$jq("#checkedAll").click(function() {

	 		if ($jq("#checkedAll").is(":checked")) {
	 			$jq('input[name=itemSeqId]:not(checked)').attr("checked", true);
			}else{
		   		$jq('input[name=itemSeqId]:checked').attr("checked", false);
		    }

	    });
		
	});
	
})(jQuery); 
 
 </script>
 

        <!--//blockListTable Start-->	
        <div class="blockListTable">
            <table summary="게시판설명">
                <caption></caption>
                <thead>
                    <tr>
						<th scope="col" width="5%">
							<c:choose>
			    				<c:when test="${isMulti == 'Y'}">
			    					<input type="checkbox" name="checkedAll" id="checkedAll" />
								</c:when>
			    			</c:choose>
						</th>
						<th scope="col" width="14%">
							<a onclick="sort('ITEM_SEQ_ID', '<c:if test="${searchCondition.sortColumn eq 'ITEM_SEQ_ID'}">${searchCondition.sortType}</c:if>');"  href="#a">
								번호
							</a>
							<c:choose>
								<c:when test="${searchCondition.sortColumn eq 'ITEM_SEQ_ID' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
								<c:when test="${searchCondition.sortColumn eq 'ITEM_SEQ_ID' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
							</c:choose> 
						</th>
						<th scope="col" width="14%">
							<a onclick="sort('IS_KNOWHOW', '<c:if test="${searchCondition.sortColumn eq 'IS_KNOWHOW'}">${searchCondition.sortType}</c:if>');"  href="#a">
								구분
							</a>
							<c:choose>
								<c:when test="${searchCondition.sortColumn eq 'IS_KNOWHOW' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
								<c:when test="${searchCondition.sortColumn eq 'IS_KNOWHOW' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
							</c:choose>
						</th>
						<th scope="col" width="*">
							<a onclick="sort('TITLE', '<c:if test="${searchCondition.sortColumn eq 'TITLE'}">${searchCondition.sortType}</c:if>');"  href="#a">
								제목
							</a>
							<c:choose>
								<c:when test="${searchCondition.sortColumn eq 'TITLE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
								<c:when test="${searchCondition.sortColumn eq 'TITLE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
							</c:choose>				
						</th>
						<th scope="col" width="14%">
							<a onclick="sort('REGISTER_NAME', '<c:if test="${searchCondition.sortColumn eq 'REGISTER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
								등록자
							</a>
							<c:choose>
								<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
								<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
							</c:choose>				
						</th>
						<th scope="col" width="14%">
							<a onclick="sort('REGIST_DATE', '<c:if test="${searchCondition.sortColumn eq 'REGIST_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
								등록일
							</a>
							<c:choose>
								<c:when test="${searchCondition.sortColumn eq 'REGIST_DATE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
								<c:when test="${searchCondition.sortColumn eq 'REGIST_DATE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
							</c:choose>				
						</th>
                    </tr>																														
                </tbody>

						<tbody>
							<c:choose>
							    <c:when test="${searchResult.emptyRecord}">
									<tr>
										<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preMessage}' key='list.empty' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="user" items="${searchResult.entity}" varStatus="status">
									<tr>
										<td>
											<c:choose>
							    				<c:when test="${isMulti == 'Y'}">
							    					<input type="checkbox" name="itemId" value="${user.itemId}"/>
												</c:when>
							    				<c:otherwise>
							    					<input type="radio" name="itemId" value="${user.itemId}"/>
							    				</c:otherwise>
							    			</c:choose>
							    			<div class="none">
							    				<span class="data_itemId">${user.itemId}</span>
							    				<span class="data_title">${user.title}</span>
							    				<span class="data_registerName">${user.registerName} ${user.jobTitleName}</span>
							    				<span class="data_registDate"><ikep4j:timezone pattern="yyyy.MM.dd" date="${user.registDate}"/></span>
							    				<span class="data_targetSource">${user.targetSource}</span>
							    			</div>
										</td>
										<td>${user.itemSeqId}</td>
										<td>
											<c:if test="${user.isKnowhow == 0}">
												업무노하우
											</c:if>
											<c:if test="${user.isKnowhow == 1}">
												일반정보
											</c:if>
											<c:if test="${user.isKnowhow == 3}">
												원문 게시판
											</c:if>
										</td>
										<td>${user.title}</td>
										<td>
										<c:choose>
											<c:when test="${user.isKnowhow == 0 || user.isKnowhow == 3}">
													${user.registerName} ${user.jobTitleName}
											</c:when>
											<c:otherwise>
												<c:if test="${isSystemAdmin}">
														${user.registerName} ${user.jobTitleName}
												</c:if>
											</c:otherwise>
										</c:choose>
										
										
										</td>
										<td>
											<ikep4j:timezone pattern="yyyy.MM.dd" date="${user.registDate}"/>
										</td>										
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

				
				
