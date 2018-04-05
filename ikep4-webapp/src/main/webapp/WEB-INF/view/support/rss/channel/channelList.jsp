<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.rss.channel.header" /> 
<c:set var="preList"    value="ui.support.rss.channel.list" />
<c:set var="preDetail"  value="ui.support.rss.channel.detail" />
<c:set var="preButton"  value="ui.support.rss.button" /> 
<c:set var="preMessage" value="ui.support.rss.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
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
		
		$jq("#sortTable").sortable({
			stop : function(event, ui) {
				var selectIndex;
				var selectId;
				var target = ui.item[0];
				$jq.each(ui.item.parent().children(), function(idx) {
					if(this == target) {
						selectIndex = idx;	// 대상이 이동되고 나서의 인덱스
						selectId = this.id;
					}
				});
				
				changeSort(selectId, selectIndex);
			}
		});

		
	});
	
	
})(jQuery);  

//-->
</script>

					<form id="searchForm" method="post" action="" onsubmit="return false">  
					
					<spring:bind path="searchCondition.sortColumn">
                        <input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
                    </spring:bind>      
                    <spring:bind path="searchCondition.sortType">
                        <input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
                    </spring:bind> 
					

					<table summary="<ikep4j:message pre='${preList}' key='main.title' />">
						<caption></caption>
						<thead>
							<tr >
								<th scope="col" width="4%">
									<a onclick="javascript:sort('SORT_ORDER', '<c:if test="${searchCondition.sortColumn eq 'SORT_ORDER'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preList}' key='channelId' />
									</a>
								</th>
								<th scope="col" width="25%">
                                    <a onclick="javascript:sort('CATEGORY_ID', '<c:if test="${searchCondition.sortColumn eq 'CATEGORY_ID'}">${searchCondition.sortType}</c:if>');"  href="#a">
                                        <ikep4j:message pre='${preList}' key='categoryId' />
                                    </a>
                                </th>
								<th scope="col" width="25%">
									<a onclick="javascript:sort('CHANNEL_TITLE', '<c:if test="${searchCondition.sortColumn eq 'CHANNEL_TITLE'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preList}' key='channelTitle' />
									</a>
								</th>
								<th scope="col" width="46%">
									<a onclick="javascript:sort('CHANNEL_URL', '<c:if test="${searchCondition.sortColumn eq 'CHANNEL_URL'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preList}' key='channelUrl' />
									</a>
								</th>
							</tr>
						</thead>
				
	
						
						<tbody id="sortTable">
						
							<c:choose>
							    <c:when test="${searchResult.emptyRecord}">
									<tr>
										<td colspan="4" class="emptyRecord"><ikep4j:message pre='${preMessage}' key='list.empty' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="channel" items="${searchResult.entity}" varStatus="status">
										<tr  style="cursor:pointer" id="${channel.channelId}" catid="${channel.categoryId}" onMouseDown="setRow('${channel.channelId}',this);">
										
											<td>${status.count}</td>
											<td class="textLeft"><a href="#a" >${channel.categoryName}</a></td>			
											<td class="textLeft"><a href="#a" >${channel.channelTitle}</a></td>
											<td class="textLeft">${channel.channelUrl}</td>
											
										</tr>
									</c:forEach>				      
							    </c:otherwise> 
							</c:choose>  
								
																																																													
						</tbody>
						
					</table>	
					
					</form>		
				
				
