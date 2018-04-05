<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.servicepack.survey.header.ing" /> 
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
	<div class="tableList_1">  
		<table summary="<ikep4j:message pre="${preList}" key="summary" />">   
			<col style="width: *"/>
			<col style="width: 160px"/>
			<!-- thead>
				<tr>
					<th scope="col"><ikep4j:message pre='${preList}' key='title' /></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='openDate' /></th>
				</tr>
			</thead--> 
			<tbody>
				<c:choose>
				    <c:when test="${searchResult.emptyRecord}">
						<tr>
							<td colspan="5" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
						</tr>				        
				    </c:when>
				    <c:otherwise>
				    <c:if test="${count eq null }">
				    <c:set var="count" value="5"/>
				    </c:if>
						<c:forEach var="surveyInfo" items="${searchResult.entity}" begin="0" end="${count -1}">
						<tr>
							<td  scope="row"  class="t_po1"><div class="ellipsis"><a href="<c:url value='/servicepack/survey/response/response.do'/>?surveyId=${surveyInfo.surveyId}">${surveyInfo.title}</a></div></td>
							<td  align="right"><span class="date"><ikep4j:timezone  date="${surveyInfo.startDate}"  pattern="message.servicepack.survey.timezone.dateformat.yyyyMMdd" keyString="true"/>	~ <ikep4j:timezone  date="${surveyInfo.endDate}"  pattern="message.servicepack.survey.timezone.dateformat.yyyyMMdd" keyString="true"/></span></td>
						</tr>
						</c:forEach>				      
				    </c:otherwise> 
				</c:choose>  
			</tbody>
		</table>
	</div>
	
	<!--//blockListTable End--> 	 
