<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.activitystream.header" /> 
<c:set var="preList"    value="ui.support.activitystream.list" />
<c:set var="preDetail"  value="ui.support.activitystream.detail" />
<c:set var="preButton"  value="ui.support.activitystream.button" /> 
<c:set var="preMessage" value="ui.support.activitystream.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript" src="<c:url value="/base/js/units/support/favorite/favorite.js"/>"></script>
 
<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	getList = function() {
		$jq("#searchForm")[0].submit();
		
	};
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		
	});
	
})(jQuery);  

//-->
</script>

		<div class="conPPS">
		
		
				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${preList}' key='batch' /></h2>
				</div>
				<!--//pageTitle End-->
				
				
				<form id="searchForm" method="post" action="<c:url value='/support/activitystream/getActivityDelLog.do'/>" >  
				
				<div class="blockSearch">
					<div class="corner_RoundBox03">
						<table summary="<ikep4j:message pre='${preList}' key='search.day' />">
							<caption></caption>
							<tbody>
								<tr>
									<th scope="row" width="5%"><ikep4j:message pre='${preList}' key='search.day' /></th>
									<td width="95%">
										<select name="year" title='year'>
											<c:forEach var="yy" begin="2000" end="2030" step="1">
												<option value="${yy}" <c:if test="${yy eq year}">selected="selected"</c:if>>${yy}</option>
											</c:forEach> 
										</select> 
										<select name="month" title='month'>
											<option value="01" <c:if test="${'01' eq month}">selected="selected"</c:if>>01</option>
											<option value="02" <c:if test="${'02' eq month}">selected="selected"</c:if>>02</option>
											<option value="03" <c:if test="${'03' eq month}">selected="selected"</c:if>>03</option>
											<option value="04" <c:if test="${'04' eq month}">selected="selected"</c:if>>04</option>
											<option value="05" <c:if test="${'05' eq month}">selected="selected"</c:if>>05</option>
											<option value="06" <c:if test="${'06' eq month}">selected="selected"</c:if>>06</option>
											<option value="07" <c:if test="${'07' eq month}">selected="selected"</c:if>>07</option>
											<option value="08" <c:if test="${'08' eq month}">selected="selected"</c:if>>08</option>
											<option value="09" <c:if test="${'09' eq month}">selected="selected"</c:if>>09</option>
											<option value="10" <c:if test="${'10' eq month}">selected="selected"</c:if>>10</option>
											<option value="11" <c:if test="${'11' eq month}">selected="selected"</c:if>>11</option>
											<option value="12" <c:if test="${'12' eq month}">selected="selected"</c:if>>12</option>
										</select> 
									</td>
								</tr>
							</tbody>
						</table>
						<div class="searchBtn"><a href="#a" onclick="javascript:getList()" ><span>Search</span></a></div>
						<div class="l_t_corner"></div>
						<div class="r_t_corner"></div>
						<div class="l_b_corner"></div>
						<div class="r_b_corner"></div>				
					</div>
				</div>	
				
				
				<!--Contents Start-->
				<!--blockListTable Start-->
				
						<div class="blockListTable" >	
						
							<table summary="<ikep4j:message pre='${preList}' key='batch' />" >
								<caption></caption>
								
								<tbody id="listDiv">
								
									<c:choose>
									    <c:when test="${empty activityDelLogList}">
									    	<td class="emptyRecord"><ikep4j:message pre='${preMessage}' key='list.empty' /></td>
										 </c:when>
									    <c:otherwise>
											<c:forEach var="activity" items="${activityDelLogList}">
											
											<tr class="msg_read">
												<td class="textLeft" width="100%">
													<c:if test="${activity.success == '1'}">
														Success
													</c:if>
													<c:if test="${activity.success == '0'}">
														Fail
													</c:if>
													&nbsp;
													<ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${activity.startTime}"/>
													~
													<ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${activity.endTime}"/>
													&nbsp;
													${activity.deleteCount} <ikep4j:message pre='${preMessage}' key='list.deleted' />
												</td>
											</tr>
									
											</c:forEach>				      
									    </c:otherwise> 
									</c:choose>  
								
								</tbody>
							</table>
						
						
						</div>
						
						
				<!--//blockListTable End-->	
				
				</form>		
			
				
		</div>				
