<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preButton" value="ui.portal.admin.screen.systemurl.popupPortalSystemUrlList.button"/>
<c:set var="preMain" value="ui.portal.admin.screen.systemurl.popupPortalSystemUrlList.main"/>

<c:set var="preList" value="ui.portal.admin.screen.systemurl.portalSystemUrlList.list"/>
<c:set var="preSearch" value="ui.portal.admin.screen.systemurl.portalSystemUrlList.search" />

<script type="text/javascript">
//<![CDATA[
(function($) {
	
	// 리스트를 가져오는 함수
	getList = function() {
		$jq("#searchForm").attr("action", "<c:url value='popupPortalSystemUrlList.do' />");
		$jq("#searchForm")[0].submit();
	};
	
	// 목록 정렬
	sort = function(sortColumn, sortType) {
		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			$jq("input[name=sortType]").val('DESC');	
		} else {
			$jq("input[name=sortType]").val('ASC');
		}
		
		getList();
	};	
	
	setSystemUrl = function(url) {
		opener.$jq("input[name=urlInput]").val(url);
		opener.getUrlValue();
		
		iKEP.closePop();
	};

	// 페이지 로딩시 실행하는 jQuery 코드로 list와 취소 버튼 이벤트를 처리.
	$jq(document).ready(function() { 		
		$jq("#closePopupSystemUrlButton").click(function() {
			iKEP.closePop();			
		});
	});
	
})(jQuery);  
//]]>	
</script>

<!--popup Start-->
<div id="popup">
	
	<!--popup_title Start-->
	<div id="popup_title_2">
         <div class="popup_bgTitle_l"></div>
		<h1><ikep4j:message pre="${preMain}" key="pageTitle" /></h1>
		<a href="#" onclick="iKEP.closePop(); return false;" title="<ikep4j:message pre='${preButton}' key='close' />">
			<span><ikep4j:message pre="${preButton}" key="close" /></span>
		</a>
	</div>
	<!--//popup_title End-->
	
	<!--popup_contents Start-->
	<div id="popup_contents">

		<!--blockListTable Start-->
		<div class="blockListTable">			
		
			<div id="listDiv">
				<form id="searchForm" method="post" action="" onsubmit="return false">  

				<spring:bind path="searchCondition.sortColumn">
					<input name="${status.expression}" type="hidden" value="${status.value}"/>
				</spring:bind>
				<spring:bind path="searchCondition.sortType">
					<input name="${status.expression}" type="hidden" value="${status.value}"/>
				</spring:bind>
				
					<!--tableTop Start-->  
					<div class="tableTop">  
						<div class="tableTop_bgR"></div>
						<h2><ikep4j:message pre="${preMain}" key="pageTitle" /></h2>
						<div class="listInfo">  
							<spring:bind path="searchCondition.pagePerRecord">  
								<select name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" onchange="getList(); return false;">
								<c:forEach var="code" items="${portalCode.pageNumList}">
									<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
								</c:forEach> 
								</select> 
							</spring:bind>
							<div class="totalNum">
								<ikep4j:message pre="${preSearch}" key="pageCount.info"/>
								<span>${searchResult.recordCount}</span>
							</div>
						</div>
						<div class="tableSearch"> 
							<spring:bind path="searchCondition.searchColumn">  
								<select name="${status.expression}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />">
									<option value="systemUrlName" <c:if test="${'systemUrlName' eq status.value}">selected="selected"</c:if>><ikep4j:message pre="${preSearch}" key="${status.expression}" post="systemUrlName"/></option>
									<option value="url" <c:if test="${'url' eq status.value}">selected="selected"</c:if>><ikep4j:message pre="${preSearch}" key="${status.expression}" post="url"/></option>
								</select>		
							</spring:bind>		
							<spring:bind path="searchCondition.searchWord">  					
								<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' />" size="20"/>
							</spring:bind>
							<a href="#" id="searchBoardItemButton" name="searchBoardItemButton" onclick="getList(); return false;" class="ic_search">
								<span><ikep4j:message pre="${preSearch}" key="search" /></span>
							</a> 
						</div>	
						<div class="clear"></div>	
					</div>
					<!--//tableTop End-->	
				
					<table summary="<ikep4j:message pre='${preList}' key='main.title' />">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col"	width="20%">
									<a href="#" onclick="sort('A.SYSTEM_URL_ID', '<c:if test="${searchCondition.sortColumn eq 'A.SYSTEM_URL_ID'}">${searchCondition.sortType}</c:if>'); return false;">
										<ikep4j:message pre="${preList}" key="systemUrlId" />
									</a>
								</th>
								<th scope="col" width="30%">
									<a href="#" onclick="sort('A.SYSTEM_URL_NAME', '<c:if test="${searchCondition.sortColumn eq 'A.SYSTEM_URL_NAME'}">${searchCondition.sortType}</c:if>'); return false;">
										<ikep4j:message pre="${preList}" key="systemUrlName" />
									</a>
								</th>
								<th scope="col" width="50%">
									<a href="#" onclick="sort('A.URL', '<c:if test="${searchCondition.sortColumn eq 'A.URL'}">${searchCondition.sortType}</c:if>'); return false;">
										<ikep4j:message pre="${preList}" key="url" />
									</a>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="systemUrl" items="${searchResult.entity}" varStatus="status">
								<tr>
									<td class="textCenter">
										<a href="#" onclick="setSystemUrl('${systemUrl.url}'); return false;">
											${systemUrl.systemUrlId}
										</a>
									</td>
									<td class="textLeft">
										<a href="#" onclick="setSystemUrl('${systemUrl.url}'); return false;">
											${systemUrl.systemUrlName}
										</a>
									</td>
									<td class="textLeft">
										<div class="ellipsis">
										<a href="#" onclick="setSystemUrl('${systemUrl.url}'); return false;">
											${systemUrl.url}
										</a>
										</div>
									</td>
								</tr>
							</c:forEach>
							
							<c:if test="${empty searchResult.entity}">
								<tr>
									<td colspan="3">
										<ikep4j:message pre="${preSearch}" key="emptyRecord" />
									</td>
								</tr>
							</c:if>			
						</tbody>
						
					</table>	
				
					<!--Page Number Start--> 
					<spring:bind path="searchCondition.pageIndex">
						<ikep4j:pagination searchFormId="searchForm" ajaxEventFunctionName="getList" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
					</spring:bind> 
					<!--//Page Number End-->
				
				</form>
			</div>
					
		</div>
		<!--//blockListTable End-->			
		
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li>
					<a href="#" class="button" id="closePopupSystemUrlButton" onclick="return false;">
						<span><ikep4j:message pre="${preButton}" key="close" /></span>
					</a>
				</li>
			</ul>
		</div>
		<!--//blockButton End-->
		
		
	
	</div>
	<!--//popup_contents End-->
 
	<!--popup_footer Start-->
	<div id="popup_footer"></div>
	<!--//popup_footer End-->
	
</div>
<!--//popup End-->