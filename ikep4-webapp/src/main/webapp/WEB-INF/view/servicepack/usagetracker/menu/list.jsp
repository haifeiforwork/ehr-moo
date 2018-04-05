<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.servicepack.usagetracker.menu" /> 
<c:set var="preButton"  value="ui.servicepack.survey.common.button" /> 
<c:set var="preSearch"  value="ui.servicepack.survey.common.searchCondition" /> 
<c:set var="preCommon"  value="ui.servicepack.usagetracker.common" /> 
<c:set var="preConfirm"  value="message.servicepack.usagetracker.common.confirm" /> 
<c:set var="preSucess"  value="message.servicepack.usagetracker.common.sucess" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 <script type="text/javascript">
<!--   
$jq(document).ready(function() { 
 $jq("select[name=pagePerRecord]").change(function() {
	 $jq("#searchForm").submit(); 
		return false; 
  }); 
});  

(function($) {  
	$(document).ready(function() {  
		$("#menuIdAll").click(function() {  
			var checked = $(this).is(':checked');
			$("input[name='menuId']").each(function(){
				if( checked )
					$(this).attr("checked", true);
				else
					$(this).attr("checked",false);
			})
		 });	
		
		$("#searchButton").click(function() {  
				$jq("#searchForm").attr("action","<c:url value='/servicepack/usagetracker/menu/list.do' />");
				$jq("#searchForm").submit(); 
				return false; 
		 });	
		
		$("#removeButton").click(function() {  
			var checkedLen = $("input[name='menuId']:checked").length;
			if( checkedLen <= 0 )
				alert("<ikep4j:message key='message.servicepack.usagetracker.menu.noselect'/>");  
			else{
				if(confirm("<ikep4j:message pre='${preConfirm}' key='delete'/>")){
					$('#searchForm').attr("action","<c:url value='/servicepack/usagetracker/menu/deleteAll.do' />");
					$('#searchForm').submit();
				}
			}			
			return false;
		 });
		
		$("#createButton").click(function() {  
			iKEP.showDialog({
				title: "<ikep4j:message key='ui.servicepack.usagetracker.menu.dialog.menuCreateTitle'/>",
				url: "<c:url value='/servicepack/usagetracker/menu/createMenu.do'/>",
				modal: true,
				width: 800,
				height: 280,
				resizable:false,
				scroll:"yes",
				params : {},
				callback : function(result) {
					$jq('#searchForm').attr("action","<c:url value='/servicepack/usagetracker/menu/list.do' />");
					$jq('#searchForm').submit();
				}
			});	
			return false;
		 });
	}); 
})(jQuery);

var updateMenu = function(menuId) {
	 iKEP.showDialog({
			title: "<ikep4j:message key='ui.servicepack.usagetracker.menu.dialog.menuModifyTitle'/>",
			url: "<c:url value='/servicepack/usagetracker/menu/open.do?menuId='/>"+menuId,
			modal: true,
			width: 800,
			height: 280,
			resizable:false,
			scroll:"yes",
			params : {},
			callback : function(result) {
				$jq('#searchForm').attr("action","<c:url value='/servicepack/usagetracker/menu/list.do' />");
				$jq('#searchForm').submit();
			}
		});
	};
	
//-->
 </script> 
<!--mainContents Start-->
	<!--//pageTitle End-->  
	<!--blockListTable Start-->
	<form id="searchForm" method="post" action="<c:url value='/servicepack/usagetracker/menu/list.do' />">  
	<input type="hidden" name="redirect" value="list" title=""/>
	<input type="hidden" name="pageIndex" value="1"  title=""/>

	<div class="blockListTable">  
		<!--tableTop Start-->  
		<div class="tableTop">  
		    <div class="tableTop_bgR"></div>
			<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2>
			<div class="listInfo">  
				<spring:bind path="searchCondition.pagePerRecord">  
					<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<c:forEach var="code" items="${codeList}">
						<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
					</c:forEach> 
					</select> 
				</spring:bind>
				<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
			</div>	  
			<div class="clear"></div>
		</div>
		<!--//tableTop End-->
		<table id="surveyInfoTable" summary="">   
			<col style="width: 6%;"/>
			<col style="width: 10%;"/>
			<col style="width: 24%;"/>
			<col style="width: 30%;"/>
			<col style="width: 20%;"/>
			<col style="width: 10%;"/>
			<thead>
				<tr>
					<th scope="col"> <input type="checkbox" name="menuIdAll"   id="menuIdAll" title=""/></th>
					<th scope="col"><ikep4j:message pre='${preHeader}' key='rowIndex' /></th>
					<th scope="col"><ikep4j:message pre='${preHeader}' key='menuName' /></th>
					<th scope="col"><ikep4j:message pre='${preHeader}' key='menuUrl' /></th>
					<th scope="col"><ikep4j:message pre='${preHeader}' key='requestParameter' /></th>
					<th scope="col"><ikep4j:message pre='${preHeader}' key='usage' /></th>
				</tr>
			</thead> 
			<tbody>
				<c:choose>
				    <c:when test="${searchResult.emptyRecord}">
						<tr>
							<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
						</tr>				        
				    </c:when>
				    <c:otherwise>
				    	<c:set var="rowIndex" value="${searchCondition.recordCount-searchCondition.startRowIndex}"/>
						<c:forEach var="menuInfo" items="${searchResult.entity}">
						<tr>
							<td> <input type="checkbox" name="menuId" value="${menuInfo.menuId}" title=""/></td>
							<td> ${rowIndex}</td>
							<td class="textLeft ellipsis">
								<a href="#a" onclick="updateMenu('${menuInfo.menuId}');"><c:out value="${menuInfo.menuName}" escapeXml="true"/></a>
							</td>
							<td class="textLeft ellipsis"><c:out value="${menuInfo.menuUrl}" escapeXml="true"/></td>
							<td class="textLeft ellipsis">${menuInfo.requestParameter}</td>
							<td><ikep4j:message pre='${preCommon}' key='useage.${menuInfo.usage}'/></td>
						</tr>
						<c:set var="rowIndex" value="${rowIndex-1}"/>
						</c:forEach>				      
				    </c:otherwise> 
				</c:choose>  
			</tbody>
		</table>
		<!--Page Numbur Start--> 
		<spring:bind path="searchCondition.pageIndex">
		<ikep4j:pagination searchButtonId="searchButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind> 
		<!--//Page Numbur End--> 
	</div>
	</form>
	<!--//blockListTable End--> 	 
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="createButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
			<li><a id="removeButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
			<li><a id="searchButton"   class="button" href="#a" style="display:none"><span>&nbsp</span></a></li>
		</ul>
	</div>
	<!--//blockButton End--> 
			