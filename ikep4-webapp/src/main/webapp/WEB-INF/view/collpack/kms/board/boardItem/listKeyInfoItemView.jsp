<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
  
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.kms.board.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="message.collpack.kms.board.boardItem.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.kms.board.boardItem.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.kms.board.boardItem.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.kms.board.boardItem.message.script" />

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
			$("#searchTargetItemForm input[name=sortColumn]").val(sortColumn); 
			$("#searchTargetItemForm input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');	 
			$("#searchTargetItemButton").click();
		}; 
		//뷰모드 체인지 함수
		changeViewMode = function(changeViewMode) { 
			$("#searchTargetItemForm input[name=viewMode]").val(changeViewMode);
			$("#searchTargetItemButton").click(); 
			return false; 
		}; 
		
		$("#searchTargetItemButton").click(function() {   
			$("#searchTargetItemForm").attr({method:'GET'}).submit(); 
			return false; 
		});
		
		
		$("#checkboxAllTargetItem").click(function() { 
			$("#searchTargetItemForm input[name=boardItemIds]").attr("checked", $(this).is(":checked"));  
		});  

		
		$("#searchTargetItemForm select[name=pagePerRecord]").change(function() {
			//서브밋하는 코드 넣으시면 됩니다.
			//ex : $("#searchTargetItemButton").click(); 
			$("#searchTargetItemButton").click(); 
		}); 
		
		var objSplit = null;
		
		$("a[name=boardItem]").click(function() {
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
		
		$("input.datepicker").datepicker({
			onSelect: function(date, event) {
				var arrDate = date.split(".");
			}
		});
	}); 
})(jQuery);
//-->
</script>

<div id="tagResult">
<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="searchTargetItemForm" method="get" action="<c:url value='/collpack/kms/board/boardItem/listKeyInfoItemView.do' />">  
	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	  
	<spring:bind path="searchCondition.layoutType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	<spring:bind path="searchCondition.gubun">
		<input name="${status.expression}" type="hidden" value="${status.value}" />
	</spring:bind>
	
	
<!--mainContents Start-->

	<h1 class="none">경영진 보고 정보</h1> 
<!--pageTitle Start-->
	<div class="tableTop">
		<div class="tableTop_bgR"></div>
		<h2>경영진 보고 정보
		</h2>  
		<div class="listInfo">  
			<spring:bind path="searchCondition.pagePerRecord">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<c:forEach var="code" items="${boardCode.pageNumList}">
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
			<a id="searchTargetItemButton" name="searchTargetItemButton" href="#a"class="ic_search"><span><ikep4j:message pre='${preButton}' key='search'/></span></a>
		</div>			
		<div class="tableSearch">                             	
           	등록일 : 
           	<input name="startDate" id="startDate"  class="inputbox w20 datepicker" type="text" value="${fn:substring(searchCondition.startDate, 0, 10)}"/>
           	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
           	 - 
           	<input name="endDate" id=endDate  class="inputbox w20 datepicker" type="text" value="${fn:substring(searchCondition.endDate, 0, 10)}"/>
           	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>						
		</div>
		<div class="clear"></div>
	</div>
	<!--//tableTop End--> 
	<!--Layout Start-->
	<div id="container">
		<!--List Layout Start-->
		<div class="blockListTable" id="listDiv"> 
			<%@ include file="/WEB-INF/view/collpack/kms/board/boardItem/listTypeKeyInfoItemView.jsp"%>
			
			<!--Page Number Start-->
			<div class="pageNum">	
			<spring:bind path="searchCondition.pageIndex">
				<ikep4j:pagination searchButtonId="searchTargetItemButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" /> 
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" /> 
			</spring:bind>
			<!--//Page Number End-->
			</div>
		</div>
	</div>
</form>		

</div>
