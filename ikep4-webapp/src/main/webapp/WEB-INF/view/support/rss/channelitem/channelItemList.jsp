<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.rss.channelitem.header" /> 
<c:set var="preList"    value="ui.support.rss.channelitem.list" />
<c:set var="preDetail"  value="ui.support.rss.channelitem.detail" />
<c:set var="preButton"  value="ui.support.rss.button" /> 
<c:set var="preMessage" value="ui.support.rss.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 

<c:set var="user" value="${sessionScope['ikep.user']}" />

 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 

		$jq("select[name=pagePerRecord]").change(function() {
			getList();
        });  
		
		$jq('#searchWord').keypress(function(event) { 
			if(event.which == '13') {
				getList();
			}
		}); 
		
		$jq("#stopRefresh").hide();
		
		
	});
	
	//$jq("#stopSpan").hide();
	
})(jQuery);  

//-->
</script>
					
				

<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

					<form id="searchForm" method="post" action="" onsubmit="return false">  
					
					<spring:bind path="searchCondition.sortColumn">
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
					</spring:bind> 		
					<spring:bind path="searchCondition.sortType">
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
					</spring:bind> 
					<spring:bind path="searchCondition.channelId">
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
					</spring:bind> 
					<spring:bind path="searchCondition.categoryId">
                        <input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
                    </spring:bind> 
	
									
					<!--tableTop Start-->  
					<div class="tableTop">  
						
						<div class="tableTop_bgR"></div>
						<h2><ikep4j:message pre='${preHeader}' key='title' /></h2>
						
						<div class="listInfo">  
							<spring:bind path="searchCondition.pagePerRecord">  
								<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
								<c:forEach var="code" items="${boardCode.pageNumList}">
									<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
								</c:forEach> 
								</select> 
							</spring:bind>
							<div class="totalNum">
								<ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>
							</div>
						</div>	  
						<div class="listView_1"> 
							<ul>
								<li id="startRefresh"><a class="button_ic" href="#a" onclick="javascript:goStart()"><span><img src="<c:url value="/base/images/icon/ic_refresh1.gif"/>" alt="edit" /><ikep4j:message pre='${preButton}' key='refresh' /></span></a></li>
								<li id="stopRefresh"><a class="button_ic" href="#a" onclick="javascript:goStop()"><span><img src="<c:url value="/base/images/icon/ic_refresh2.gif"/>" alt="edit" /><ikep4j:message pre='${preButton}' key='loading' /></span></a></li>
							</ul>
						</div>
						<div class="tableSearch"> 
							<spring:bind path="searchCondition.searchColumn">  
								<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
									<option value="title" <c:if test="${'title' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='title'/></option>
									<option value="registerName" <c:if test="${'registerName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preList}' key='itemRegister' post=''/></option>
								</select>	
							</spring:bind>		
							<spring:bind path="searchCondition.searchWord">  					
								<input name="${status.expression}" id="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
							</spring:bind>
							<a id="searchBoardItemButton" name="searchBoardItemButton" href="#a" onclick="javascript:getList()" class="ic_search"><span>Search</span></a> 
						</div>		
						<div class="clear"></div>	
					</div>
					<!--//tableTop End-->		
					
					

					<table summary="<ikep4j:message pre='${preList}' key='main.title' />">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="55%">
									<a onclick="javascript:sort('ITEM_TITLE', '<c:if test="${searchCondition.sortColumn eq 'ITEM_TITLE'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preList}' key='itemTitle' />
										<c:if test="${searchCondition.sortColumn eq 'ITEM_TITLE'}">
											<c:if test="${searchCondition.sortType eq 'DESC'}">
												<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
											</c:if>
											<c:if test="${searchCondition.sortType eq 'ASC'}">
												<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
											</c:if>
										</c:if>
									</a>
								</th>
								<th scope="col" width="15%">
									<a onclick="sort('ITEM_REGISTER', '<c:if test="${searchCondition.sortColumn eq 'ITEM_REGISTER'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preList}' key='itemRegister' />
										<c:if test="${searchCondition.sortColumn eq 'ITEM_REGISTER'}">
											<c:if test="${searchCondition.sortType eq 'DESC'}">
												<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
											</c:if>
											<c:if test="${searchCondition.sortType eq 'ASC'}">
												<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
											</c:if>
										</c:if>
									</a>
								</th>
								<th scope="col" width="10%">
										<ikep4j:message pre='${preList}' key='itemTime' />
								</th>
								<th scope="col" width="20%">
									<a onclick="sort('ITEM_PUBLISH_DATE', '<c:if test="${searchCondition.sortColumn eq 'ITEM_PUBLISH_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preList}' key='itemPublishDate' />
										<c:if test="${searchCondition.sortColumn eq 'ITEM_PUBLISH_DATE'}">
											<c:if test="${searchCondition.sortType eq 'DESC'}">
												<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
											</c:if>
											<c:if test="${searchCondition.sortType eq 'ASC'}">
												<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
											</c:if>
										</c:if>
									</a>
								</th>
							</tr>
						</thead>
						
						<tbody>
						
							<c:choose>
							    <c:when test="${searchResult.emptyRecord}">
									<tr>
										<td colspan="4" class="emptyRecord"><ikep4j:message pre='${preMessage}' key='list.empty' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="channelItem" items="${searchResult.entity}">
									<tr>
										<td class="textLeft">
											<a href="#a" onclick="iKEP.channelItemView('${channelItem.itemUrl}', '${ikep4j:replaceQuot(channelItem.itemTitle)}')" title="${ikep4j:replaceQuot(channelItem.itemTitle)}">
											<div class="ellipsis">${channelItem.itemTitle}</div></a>
										</td>	
										<td><div class="ellipsis">${channelItem.itemRegister}</div></td>	
										<td><div class="ellipsis">${ikep4j:getTimeInterval(channelItem.itemPublishDate, user.localeCode)}</div></td>		
										<td><div class="ellipsis"><ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${channelItem.itemPublishDate}"/></div></td>
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
					
					
					</form>
					
					
					
					
	
					
				
				
