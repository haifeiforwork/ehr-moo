<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preButton" value="ui.portal.admin.screen.menuurl.popupPortalSystemUrlList.button"/>
<c:set var="preMain" value="ui.portal.admin.screen.menuurl.popupPortalMenuUrlList.main"/>

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
	
	setSystemUrl = function(url,menuName, menuId) {
		
		iKEP.returnPopup({url:url, menuName:menuName, menuId:menuId});
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
				
					<!--tableTop Start-->  
					<div class="tableTop">  
						<div class="tableTop_bgR"></div>
						<h2><ikep4j:message pre="${preMain}" key="pageTitle" /></h2>
						<div class="clear"></div>	
					</div>
					<!--//tableTop End-->	
				
					<table summary="<ikep4j:message pre='${preList}' key='main.title' />">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="40%">		
										<ikep4j:message pre="${preMain}" key="menuName" />
								</th>
								<th scope="col" width="60%">									
										<ikep4j:message pre="${preMain}" key="url" />								
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="portalMenuList" items="${portalMenuList}" varStatus="status">
								<tr>
									<td class="textLeft">							
											${portalMenuList.menuName}										
									</td>
									<td class="textLeft">
										<div class="ellipsis">
										<a href="#" onclick="setSystemUrl('${portalMenuList.url}','${portalMenuList.menuName}','${portalMenuList.menuId}'); return false;">
											${portalMenuList.url}
										</a>
										</div>
									</td>
								</tr>
							</c:forEach>
							
							<c:if test="${empty portalMenuList}">
								<tr>
									<td colspan="3">
										<ikep4j:message pre="${preSearch}" key="emptyRecord" />
									</td>
								</tr>
							</c:if>			
						</tbody>
						
					</table>	
				
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