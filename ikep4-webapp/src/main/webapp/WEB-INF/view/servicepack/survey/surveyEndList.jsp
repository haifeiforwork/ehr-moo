<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.servicepack.survey.header.end" /> 
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
  
 if('${erroMessage}' !='' )
	  alert('<ikep4j:message  key="${erroMessage}" />');
});


function resultErro(){
	alert('<ikep4j:message  key="message.servicepack.survey.open.not.valid" />');
}

var getColleague = function(userId){
	iKEP.goProfilePopupMain(userId);
};		
//-->
 </script> 
<!--mainContents Start-->
	<!--//pageTitle End-->  
	<!--blockListTable Start-->
	<form id="searchForm" method="post" action="<c:url value='/servicepack/survey/surveyEndList.do' />">  
	<input type="hidden" name="redirect" value="surveyEndList" title=""/>
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
						<option value="registerName" <c:if test="${'registerName' eq status.value}">selected="selected"</c:if>><ikep4j:message pre='${preList}' key='registerName' /></option>
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
			<col style="width: 10%;"/>
			<col style="width: 24%;"/>
			<col style="width: 10%;"/>
			<col style="width: 17%;"/>
			<col style="width: 10%;"/>
			<col style="width: 15%;"/>
			<col style="width: 8%;"/>
			<thead>
				<tr>
					<th scope="col"><ikep4j:message pre='${preList}' key='rowIndex' /></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='resultOpen' /></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='title' /></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='surveyStatus' /></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='registerName' /></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='responseAvg' /></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='openDate' /></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='responseDate' /></th>
				</tr>
			</thead> 
			<tbody>
				<c:choose>
				    <c:when test="${searchResult.emptyRecord}">
						<tr>
							<td colspan="8" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
						</tr>				        
				    </c:when>
				    <c:otherwise>
				        <c:set var="rowIndex" value="${searchCondition.recordCount-searchCondition.startRowIndex}"/>
						<c:forEach var="surveyInfo" items="${searchResult.entity}">
						<tr>
							<td> ${rowIndex}</td>
							<td> 
								<c:choose>
									<c:when test="${surveyInfo.resultOpen eq '0'}">공개</c:when>
									<c:otherwise>비공개</c:otherwise>
								</c:choose>
							</td>
							<td  class="ellipsis">
								<!-- 설문 공개 여부( 0 : 공개, 1: 비공개) -->
								<c:choose>
									<c:when test="${surveyInfo.resultOpen eq '0' or surveyInfo.registerId eq user.userId or isAdmin}">
										<a href="<c:url value='/servicepack/survey/report/result.do'/>?surveyId=${surveyInfo.surveyId}">${surveyInfo.title}</a>
									</c:when>
									<c:otherwise>
									    ${surveyInfo.title}
									</c:otherwise>
								</c:choose>
							</td>
							<td><ikep4j:message key='${surveyInfo.surveyStatusName}' /></td>
							<td><a href="#a" onclick="getColleague('${surveyInfo.registerId}');">
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
							<td><fmt:formatNumber var="totalAvg" value="${surveyInfo.responseAvg}" pattern="#0.00"/>
										${totalAvg} &nbsp;</td>
							<td><ikep4j:timezone  date="${surveyInfo.startDate}"  pattern="message.servicepack.survey.timezone.dateformat.yyyyMMdd" keyString="true"/>~ 
							<ikep4j:timezone  date="${surveyInfo.endDate}"  pattern="message.servicepack.survey.timezone.dateformat.yyyyMMdd" keyString="true"/></td>
							<td><ikep4j:timezone  date="${surveyInfo.responseDate}"  pattern="message.servicepack.survey.timezone.dateformat.yyyyMMdd" keyString="true"/>
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
	<!--//blockButton End--> 