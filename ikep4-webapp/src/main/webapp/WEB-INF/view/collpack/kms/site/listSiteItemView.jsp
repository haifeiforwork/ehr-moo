<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
  
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.kms.site.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="message.collpack.kms.site.boardItem.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.kms.site.boardItem.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.kms.site.boardItem.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.kms.site.boardItem.message.script" />

<c:if test="${!empty searchResult.entity}">
<c:forEach var="info" items="${searchResult.entity}">

</c:forEach>
</c:if>
 
<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value='/base/js/jquery/splitter.js'/>"></script>
<script type="text/javascript">

<!--   
(function($){	
	$(document).ready(function() {    
		iKEP.iFrameContentResize();
		sort = function(sortColumn, sortType) {
			$("#searchSiteItemForm input[name=sortColumn]").val(sortColumn); 
			$("#searchSiteItemForm input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');	 
			$("#searchSiteItemButton").click();
		}; 
		//뷰모드 체인지 함수
		changeViewMode = function(changeViewMode) { 
			$("#searchSiteItemForm input[name=viewMode]").val(changeViewMode);
			$("#searchSiteItemButton").click(); 
			return false; 
		}; 
		
		$("#searchSiteItemButton").click(function() {   
			$("#searchSiteItemForm").attr({method:'GET'}).submit(); 
			return false; 
		});
		
		
		$("#checkboxAllSiteItem").click(function() { 
			$("#searchSiteItemForm input[name=siteItemIds]").attr("checked", $(this).is(":checked"));  
		});  
		
		$("#adminMultiDeleteSiteItemButton").click(function() {  
			var countCheckBox	=	$("input[name=siteItemIds]:checkbox:checked").length;
			if(countCheckBox>0)
			{
				if(confirm("<ikep4j:message pre="${preMessage}" key="deleteItem" />")) {
					$jq.ajax({
						url : "<c:url value='/collpack/kms/site/adminMultiDeleteSiteItem.do'/>?"+$jq("#searchSiteItemForm").serialize(),
						type : "get",
						success : function(result) {
							$("#searchSiteItemButton").click();
						}
					});		
				}
			}else{
				//체크 없음 메세지
				alert("<ikep4j:message pre="${preMessage}" key="emptySelectedItem" />");
			}
			
		});  
		
		$("#searchSiteItemForm select[name=pagePerRecord]").change(function() {
			//서브밋하는 코드 넣으시면 됩니다.
			//ex : $("#searchSiteItemButton").click(); 
			$("#searchSiteItemButton").click(); 
		}); 
		
		var objSplit = null;
		
		$("a[name=siteItem]").click(function() {
			$("*[name=boardItemLine]").removeClass("bgSelected");
			
			$(this).parents("*[name=boardItemLine]").addClass("bgSelected");
			
			 if(objSplit == null  ) {
				return true;
			 } else if(objSplit.getType() == "v") { 
				 
				$("#BottomPane").remove();
				$("#RightPane").load($(this).attr("href") + "&layoutType=layoutVertical");
				return false;
				
			} else if(objSplit.getType() == "h") { 
				$("#RightPane").remove();
				$("#BottomPane").load($(this).attr("href") + "&layoutType=layoutHorizental");
				return false;
			} else {  
				return true;
			}
			 
			 return false;
			
		}); 
		
		$("#btnLayoutNormal").click(function() {  
			if(objSplit != null) {
				objSplit.destroy();
				objSplit = null;
				$("input[name=layoutType]").val("layoutNormal");
			}
		});
		
		$("#btnLayoutVertical").click(function(event) { 
			if(objSplit == null) {
				var options = {type : "v", callback:function($divContent) {}}; 
				objSplit = new iKEP.Splitter("#splitterBox", options);
				$("input[name=layoutType]").val("layoutVertical");
			} else {
				objSplit.destroy();
				objSplit = null;
				$(this).trigger("click"); 
			} 
		}); 
		
		$("#btnLayoutHorizental").click(function(event) {   
			if(objSplit == null) {
				var options = {type : "h", callback:function($divContent) {}}; 
				objSplit = new iKEP.Splitter("#splitterBox", options);
				$("input[name=layoutType]").val("layoutHorizental");
			} else {
				objSplit.destroy();
				objSplit = null;
				$(this).trigger("click"); 
			} 
		}); 
		if($("input[name=layoutType]").val() ==  "layoutVertical") {
			$("#btnLayoutVertical").click();
		} else if($("input[name=layoutType]").val() ==  "layoutHorizental") {
			$("#btnLayoutHorizental").click();
		} else {
			
		} 
	}); 
})(jQuery);
//-->
</script>

<div id="tagResult">
<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="searchSiteItemForm" method="get" action="<c:url value='/collpack/kms/site/listSiteItemView.do' />">  
	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	
	<!-- 
	<spring:bind path="searchCondition.workspaceId">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	 -->
	  
	<spring:bind path="searchCondition.layoutType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	
<!--mainContents Start-->

	<h1 class="none">지식공유사이트</h1> 
<!--pageTitle Start-->
	<div class="tableTop">
		<div class="tableTop_bgR"></div>
		<h2>지식공유사이트</h2>  
		<div class="listInfo">  
			<spring:bind path="searchCondition.pagePerRecord">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<c:forEach var="code" items="${siteCode.pageNumList}">
					<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
				</c:forEach> 
				</select> 
			</spring:bind>
			<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
		</div>	  
						
		
		<div class="tableSearch"> 
			<spring:bind path="searchCondition.searchColumn">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<option value="title" <c:if test="${'title' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='title'/></option>
					<option value="registerName" <c:if test="${'registerName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='registerName'/></option>
				</select>		
			</spring:bind>		
			<spring:bind path="searchCondition.searchWord">  					
				<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='' size="20"/>
			</spring:bind>
			<a id="searchSiteItemButton" name="searchSiteItemButton" href="#a"class="ic_search"><span><ikep4j:message pre='${preButton}' key='search'/></span></a>
		</div>			
		<div class="clear"></div>
	</div>
	<!--//tableTop End--> 
	<!--Layout Start-->
	<div id="container">
		<!--List Layout Start-->
		<div id="listDiv"> 
		
			<c:choose>
				<c:when test="${searchCondition.viewMode eq '0'}">
				<!--blockListTable Start-->
				<div class="blockListTable">
					<%@ include file="/WEB-INF/view/collpack/kms/site/listTypeSiteItemView.jsp"%>
				</c:when>
				<c:when test="${searchCondition.viewMode eq '1'}">
				<div class="blockListTable summaryView"> 	
					<%@ include file="/WEB-INF/view/collpack/kms/site/summaryTypeSiteItemView.jsp"%>
				</c:when>
			</c:choose>
			
			<!--Page Number Start-->
			<spring:bind path="searchCondition.pageIndex">
				<ikep4j:pagination searchButtonId="searchSiteItemButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" /> 
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" /> 
			</spring:bind>
			<!--//Page Number End-->
			</div>
		</div>
	
		<!--blockButton Start-->
		<div class="blockButton">
			<ul>
				<c:if test="${sitePermission>1}">
					<li>
						<!--a id="createSiteItemButton" class="button" href="<c:url value='/collpack/kms/site/createSiteItemView.do?workspaceId=${searchCondition.workspaceId}'/>" -->
						<a id="createSiteItemButton" class="button" href="<c:url value='/collpack/kms/site/createSiteItemView.do'/>">
							<span><ikep4j:message pre='${preButton}' key='create'/></span>
						</a>
					</li>
					<li>
						<a id="adminMultiDeleteSiteItemButton" class="button" href="#a">
							<span><ikep4j:message pre='${preButton}' key='delete'/></span>
						</a>
					</li>
				</c:if>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
</form>		

</div>
