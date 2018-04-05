<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.popup.jobclass.header" /> 
<c:set var="preList"    value="ui.support.popup.jobclass.list" />
<c:set var="preDetail"  value="ui.support.popup.jobclass.detail" />
<c:set var="preButton"  value="ui.support.popup.button" /> 
<c:set var="preMessage" value="ui.support.popup.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" language="javascript">

(function($) {
	
	$jq(document).ready(function() { 
		
		$jq("#checkedAll").click(function() {

	 		if ($jq("#checkedAll").is(":checked")) {
	 			$jq('input[name=chk]:not(checked)').attr("checked", true);
			}else{
		   		$jq('input[name=chk]:checked').attr("checked", false);
		    }

	    });
		
		$jq("select[name=pagePerRecord]").change(function() {
			getList();
        });  
		
		$jq('#searchWord').keypress(function(event) { 
			if(event.which == '13') {
				getList();
			}
		}); 
		
		
	});
	
})(jQuery); 
 
 </script>

					<form id="searchForm" method="post" action="" onsubmit="return false">  
					
					<spring:bind path="searchCondition.sortColumn">
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
					</spring:bind> 		
					<spring:bind path="searchCondition.sortType">
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
					</spring:bind> 
				
									
					<!--tableTop Start-->  
					<div class="tableTop">  
						<div class="listInfo">  
							<spring:bind path="searchCondition.pagePerRecord">  
								<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
								<c:forEach var="code" items="${boardCode.pageNumList}">
									<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
								</c:forEach> 
								</select> 
							</spring:bind>
							<div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)</div>
						</div>	  
						<div class="tableSearch"> 
							<spring:bind path="searchCondition.searchColumn">  
								<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
									<option value="title" <c:if test="${'title' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preList}' key='jobClassName' post=''/></option>
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
								<th scope="col" width="5%">
									<c:choose>
					    				<c:when test="${isMulti == 'Y'}">
					    					<input type="checkbox" name="checkedAll" id="checkedAll" />
										</c:when>
					    			</c:choose>
								</th>
								<th scope="col" width="95%">
									<a onclick="javascript:sort('JOB_CLASS_NAME', '<c:if test="${searchCondition.sortColumn eq 'JOB_CLASS_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preList}' key='jobClassName' />
										<c:if test="${searchCondition.sortColumn eq 'JOB_CLASS_NAME'}">
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
										<td colspan="2" class="emptyRecord"><ikep4j:message pre='${preMessage}' key='list.empty' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="jobClass" items="${searchResult.entity}" varStatus="status">
									<tr>
										<td>
											<c:choose>
							    				<c:when test="${isMulti == 'Y'}">
							    					<input type="checkbox" name="chk" value="${jobClass.jobClassCode}"/>
												</c:when>
							    				<c:otherwise>
							    					<input type="radio" name="chk" value="${jobClass.jobClassCode}"/>
							    				</c:otherwise>
							    			</c:choose>
							    			<input type="hidden" name="chk2" value="${jobClass.jobClassName}"/>
										</td>
										<td class="textLeft">
											<div class="ellipsis">${jobClass.jobClassName}</div>
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
					
					
					</form>		
				
				
