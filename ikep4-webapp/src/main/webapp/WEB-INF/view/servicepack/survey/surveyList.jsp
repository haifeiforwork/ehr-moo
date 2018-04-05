<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.servicepack.survey.header" /> 
<c:set var="preList"    value="ui.servicepack.survey.list" />
<c:set var="preButton"  value="ui.servicepack.survey.common.button" /> 
<c:set var="preSearch"  value="ui.servicepack.survey.common.searchCondition" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 <script type="text/javascript">
<!--   
$jq(document).ready(function() { 
 $jq("select[name=pagePerRecord]").change(function() {
      $jq("#searchSurveyButton").click();  
  }); 
 
 $jq("#searchSurveyButton").click(function() {   
		$jq("#searchForm").submit(); 
		return false; 
	});
 
});  

var getColleague = function(userId){
	iKEP.goProfilePopupMain(userId);
};								   

//-->
 </script> 
<!--mainContents Start-->
	<!--//pageTitle End-->  
	<!--blockListTable Start-->
	<form id="searchForm" method="post" action="<c:url value='/servicepack/survey/surveyList.do' />">  
	<input type="hidden" name="redirect" value="surveyList" title=""/>
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
			<div class="tableSearch">
			    <spring:bind path="searchCondition.surveyStatus">  
					<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<c:forEach var="code" items="${surveyStatusList}">
						<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>><ikep4j:message key='${code.value}'/></option>
					</c:forEach> 
					</select>		
				</spring:bind>
				<spring:bind path="searchCondition.searchColumn">  
					<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
						<option value="title" <c:if test="${'title' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='${status.expression}' post='title'/></option>
						<option value="registerName" <c:if test="${'registerName' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='${status.expression}' post='registerName'/></option>
					</select>		
				</spring:bind>		
				<spring:bind path="searchCondition.searchWord">  					
					<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
				</spring:bind>
				<a id="searchSurveyButton" name="searchSurveyButton" href="#a" class="ic_search"><span>Search</span></a>	
			</div>			
			<div class="clear"></div>
		</div>
		<!--//tableTop End-->
		<table id="surveyInfoTable" summary="<ikep4j:message pre="${preList}" key="summary" />">   
			<col style="width: 6%;"/>
			<col style="width: 44%;"/>
			<col style="width: 20%;"/>
			<col style="width: 20%;"/>
			<col style="width: 10%;"/>
			<thead>
				<tr>
					<th scope="col"><ikep4j:message pre='${preList}' key='rowIndex' /></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='title' /></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='registerName' /></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='updateDate' /></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='surveyStatus' /></th>
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
						<c:forEach var="surveyInfo" items="${searchResult.entity}">
						<tr>
							<td> ${rowIndex}</td>
							<td class="textLeft ellipsis">
								<a href="<c:url value='/servicepack/survey/readSurvey.do'/>?surveyId=${surveyInfo.surveyId}">${surveyInfo.title}</a>
							</td>
							<td class="textLeft ellipsis"><a href="#a" onclick="getColleague('${surveyInfo.registerId}');">
									<c:choose>
								      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
								       ${surveyInfo.userName}&nbsp;${surveyInfo.jobTitleName}&nbsp;${surveyInfo.teamName} 
								      </c:when>
								      <c:otherwise>
								       ${surveyInfo.userEnglishName}&nbsp;${surveyInfo.jobTitleEnglishName}&nbsp;${surveyInfo.teamEnglishName}  
								      </c:otherwise>
								     </c:choose>
								     </a>
							</td>
							<td>
							<ikep4j:timezone  date="${surveyInfo.updateDate}"  pattern="message.servicepack.survey.timezone.dateformat.yyyyMMddhhmmss" keyString="true"/></td>
							<td><ikep4j:message key='${surveyInfo.surveyStatusName}' /></td>
						</tr>
						<c:set var="rowIndex" value="${rowIndex-1}"/>
						</c:forEach>				      
				    </c:otherwise> 
				</c:choose>  
			</tbody>
		</table>
		<!--Page Numbur Start--> 
		<spring:bind path="searchCondition.pageIndex">
		<ikep4j:pagination searchButtonId="searchSurveyButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind> 
		<!--//Page Numbur End--> 
	</div>
	</form>
	<!--//blockListTable End--> 	 
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="createButton"   class="button" href="<c:url value='/servicepack/survey/createSurvey.do'/>"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End--> 