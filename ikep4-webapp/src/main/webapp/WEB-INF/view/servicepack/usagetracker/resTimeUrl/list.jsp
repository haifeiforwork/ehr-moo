<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preRes"     value="ui.servicepack.usagetracker.resTimeUrl" /> 
<c:set var="preButton"  value="ui.servicepack.survey.common.button" /> 
<c:set var="preSearch"  value="ui.servicepack.survey.common.searchCondition" /> 
<c:set var="preCommon"  value="ui.servicepack.usagetracker.common" /> 
<c:set var="preConfirm" value="message.servicepack.usagetracker.common.confirm" /> 
<c:set var="preMessage" value="message.servicepack.usagetracker.resTimeUrl" />

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 <script type="text/javascript">
<!--   
(function($) {  
	$(document).ready(function() {
		$("select[name=pagePerRecord]").change(function() {
			getList(); 
		}); 
		
		$("#resTimeUrlIdAll").click(function() {  
			var checked = $(this).is(':checked');
			$("input[name='resTimeUrlId']").each(function(){
				if(checked)
					$(this).attr("checked", true);
				else
					$(this).attr("checked",false);
			});
		});	

		$("#removeButton").click(function() {  
			var checkedLen = $("input[name='resTimeUrlId']:checked").length;
			if( checkedLen <= 0 )
				alert("<ikep4j:message pre='${preMessage}' key='noselect'/>");  
			else{
				if(confirm("<ikep4j:message pre='${preConfirm}' key='delete'/>")){
					$('#searchForm').attr("action","<c:url value='/servicepack/usagetracker/resTimeUrl/deleteAll.do' />");
					$('#searchForm').submit();
				}
			}			
			return false;
		 });
		
		$("#createButton").click(function() {  
			iKEP.showDialog({
				title: "<ikep4j:message pre='${preRes}' key='create'/>",
				url: "<c:url value='/servicepack/usagetracker/resTimeUrl/createResTimeUrl.do'/>",
				modal: true,
				width: 800,
				height: 180,
				resizable:false,
				scroll:"yes",
				params : {},
				callback : function() {
					$jq('#searchForm').attr("action","<c:url value='/servicepack/usagetracker/resTimeUrl/list.do' />");
					$jq('#searchForm').submit();
				}
			});	
			return false;
		 });
	}); 
})(jQuery);

var updateResTimeUrl = function(resTimeUrlId) {
	 iKEP.showDialog({
		title: "<ikep4j:message pre='${preRes}' key='update'/>",
		url: "<c:url value='/servicepack/usagetracker/resTimeUrl/createResTimeUrl.do?resTimeUrlId='/>"+resTimeUrlId,
		modal: true,
		width: 800,
		height: 180,
		resizable:false,
		scroll:"yes",
		params : {},
		callback : function() {
			$jq('#searchForm').attr("action","<c:url value='/servicepack/usagetracker/resTimeUrl/list.do' />");
			$jq('#searchForm').submit();
		}
	});
};
	
var getList = function() {
	$jq("#searchForm").submit(); 
	return false; 	 
};
//-->
</script> 
<!--mainContents Start-->
	<!--//pageTitle End-->  
	<!--blockListTable Start-->
	<form id="searchForm" method="post" action="<c:url value='/servicepack/usagetracker/resTimeUrl/list.do' />">  
	
	<div class="blockListTable">  
		<!--tableTop Start-->  
		<div class="tableTop">  
		    <div class="tableTop_bgR"></div>
			<h2><ikep4j:message pre='${preRes}' key='pageTitle'/></h2>
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
			<col style="width: 50%;"/>
			<col style="width: 10%;"/>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" name="resTimeUrlIdAll" id="resTimeUrlIdAll" /></th>
					<th scope="col"><ikep4j:message pre='${preRes}' key='grid.rowIndex' /></th>
					<th scope="col"><ikep4j:message pre='${preRes}' key='grid.urlName' /></th>
					<th scope="col"><ikep4j:message pre='${preRes}' key='grid.url' /></th>
					<th scope="col"><ikep4j:message pre='${preRes}' key='grid.usage' /></th>
				</tr>
			</thead> 
			<tbody>
				<c:choose>
				    <c:when test="${searchResult.emptyRecord}">
						<tr>
							<td colspan="5" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
						</tr>				        
				    </c:when>
				    <c:otherwise>
				    	<c:set var="rowIndex" value="${searchCondition.recordCount-searchCondition.startRowIndex}"/>
						<c:forEach var="resTimeUrl" items="${searchResult.entity}">
						<tr>
							<td> <input type="checkbox" name="resTimeUrlId" value="${resTimeUrl.resTimeUrlId}" title=""/></td>
							<td> ${rowIndex}</td>
							<td class="textLeft ellipsis">
								<a href="#a" onclick="updateResTimeUrl('${resTimeUrl.resTimeUrlId}');"><c:out value="${resTimeUrl.resTimeUrlName}" escapeXml="true"/></a>
							</td>
							<td class="textLeft ellipsis"><c:out value="${resTimeUrl.resTimeUrl}" escapeXml="true"/></td>
							<td><ikep4j:message pre='${preCommon}' key='useage.${resTimeUrl.usage}'/></td>
						</tr>
						<c:set var="rowIndex" value="${rowIndex-1}"/>
						</c:forEach>				      
				    </c:otherwise> 
				</c:choose>  
			</tbody>
		</table>
		<!--Page Numbur Start--> 
		<spring:bind path="searchCondition.pageIndex">
		<ikep4j:pagination searchFormId="searchForm" ajaxEventFunctionName="getList"  pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
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
		</ul>
	</div>
	<!--//blockButton End--> 
			