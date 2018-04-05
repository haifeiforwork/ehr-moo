<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preRes"     value="ui.servicepack.usagetracker.resTimeLog" /> 
<c:set var="preButton"  value="ui.servicepack.survey.common.button" /> 
<c:set var="preSearch"  value="ui.servicepack.survey.common.searchCondition" />
<c:set var="common" value="ui.servicepack.usagetracker.common" />
<c:set var="preMessage" value="message.servicepack.usagetracker.resTimeLog" /> 

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
		
		$("select[name=resTimeUrlId]").change(function() {
			getList();
		});
		
		$("#updateResTimeConfigBtn").click(function() {  
			if($("#resTimeConfigSecond").val() == "") {
				alert("<ikep4j:message pre='${preMessage}' key='noTime'/>");
				return false;
			}
			
			$('#searchForm').attr("action", "<c:url value='/servicepack/usagetracker/resTimeLog/updateResTimeConfig.do' />");
			$('#searchForm').submit();
			
			return false;
		 });
		
		$("#viewDaily").click(function() {
	  	  	$("#searchOption").val('1');
	  		$("input[name='pageIndex']").val('1');
	  		getList();
	  	}); 
	    
	    $("#viewMonthly").click(function() {
	  	  	$("#searchOption").val('2');
	  	  	$("input[name='pageIndex']").val('1');
	  		getList();
	  	});
	    
	    $("#viewEntire").click(function() {
	  	  	$("#searchOption").val('3');
	  	  	$("input[name='pageIndex']").val('1');
	  		getList();
	  	});

	});
        
	getList = function() {
		$("#searchForm").submit(); 
		return false; 	 
	};
})(jQuery);

//-->
 </script> 
<!--mainContents Start-->
	
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre='${preRes}' key='pageTitle'/></h2>
	</div>
	<!--//pageTitle End-->
  
	<!--blockListTable Start-->
	<form id="searchForm" method="post" action="<c:url value='/servicepack/usagetracker/resTimeLog/list.do' />">  
	<input type="hidden" name="searchOption" id="searchOption" title="" value="${searchCondition.searchOption}"/>
	
	<!--blockSearch Start-->
	<div class="blockSearch">
		<div class="corner_RoundBox03">
			<table summary="">
				<caption></caption>
				<tbody>
					<tr>
						<td scope="row" style="padding:0;">
							<a class="button_s" id="viewDaily"   href="#a"><span><ikep4j:message pre='${preRes}' key='top.daily'/></span></a>
							<a class="button_s" id="viewMonthly" href="#a"><span><ikep4j:message pre='${preRes}' key='top.monthly'/></span></a> 
							<a class="button_s" id="viewEntire"  href="#a"><span><ikep4j:message pre='${preRes}' key='top.entire'/></span></span></a>
							<spring:bind path="searchCondition.resTimeUrlId">  
								<select name="${status.expression}">
									<option value=""><ikep4j:message pre='${preRes}' key='top.allTarget'/></option>
									<c:forEach var="resTimeUrl" items="${resTimeUrlList}">
										<option value="${resTimeUrl.resTimeUrlId}" <c:if test="${resTimeUrl.resTimeUrlId eq status.value}">selected="selected"</c:if>>${resTimeUrl.resTimeUrlName}</option>
									</c:forEach> 
								</select> 
							</spring:bind>
						</td>
						<td>
							
						</td>
						<td style="text-align:right;">
							<ikep4j:message pre='${preRes}' key='top.setUpTime'/> : ${utResTimeConfig.resTimeConfigSecond} <ikep4j:message pre='${preRes}' key='top.second'/>		
							<input name="resTimeConfigSecond" id="resTimeConfigSecond" value="" type="text" class="inputbox" size="5"/>
							<a class="button_s" id="updateResTimeConfigBtn" href="#a"><span><ikep4j:message pre='${preRes}' key='top.editTime'/></span></a>			
						</td>
					</tr>
				</tbody>
			</table>
			<div class="l_t_corner"></div>
			<div class="r_t_corner"></div>
			<div class="l_b_corner"></div>
			<div class="r_b_corner"></div>
		</div>
	</div>	
	<!--//blockSearch End-->
	
	
	<div class="blockListTable">  
		<!--tableTop Start-->  
		<div class="tableTop">  
		    <div class="tableTop_bgR"></div>
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
			<c:choose>
				<c:when test="${searchCondition.searchOption ne 3}">
					<col style="width: 10%;"/>
					<col style="width: 30%;"/>
					<col style="width: 20%;"/>
					<col style="width: 20%;"/>
					<col style="width: 20%;"/>
				</c:when>
				<c:otherwise>
					<col style="width: 10%;"/>
					<col style="width: 25%;"/>
					<col style="width: 15%;"/>
					<col style="width: 20%;"/>
					<col style="width: 15%;"/>
					<col style="width: 15%;"/>
				</c:otherwise>
			</c:choose>
			
			<thead>
				<tr>
					<c:choose>
						<c:when test="${searchCondition.searchOption ne 3}">
							<th scope="col"><ikep4j:message pre='${preRes}' key='grid.rowIndex'/></th>
							<th scope="col"><ikep4j:message pre='${preRes}' key='grid.urlName'/></th>
							<th scope="col"><ikep4j:message pre='${preRes}' key='grid.logTime'/></th>
							<th scope="col"><ikep4j:message pre='${preRes}' key='grid.avrResTime'/></th>
							<th scope="col"><ikep4j:message pre='${preRes}' key='grid.healthyCheck'/></th>
						</c:when>
						<c:otherwise>
							<th scope="col"><ikep4j:message pre='${preRes}' key='grid.rowIndex'/></th>
							<th scope="col"><ikep4j:message pre='${preRes}' key='grid.urlName'/></th>
							<th scope="col"><ikep4j:message pre='${preRes}' key='grid.userName'/></th>
							<th scope="col"><ikep4j:message pre='${preRes}' key='grid.logTime'/></th>
							<th scope="col"><ikep4j:message pre='${preRes}' key='grid.responseTime'/></th>
							<th scope="col"><ikep4j:message pre='${preRes}' key='grid.healthyCheck'/></th>
						</c:otherwise>
					</c:choose>
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
						<c:forEach var="resTimeLog" items="${searchResult.entity}">
						<tr>
							<td>${rowIndex}</td>
							<td class="textLeft ellipsis">${resTimeLog.resTimeUrlName}</td>
							
							<c:if test="${searchCondition.searchOption eq 3}">
								<td class="textLeft ellipsis">${resTimeLog.userName}</td>
								<td><ikep4j:timezone date="${resTimeLog.accessDate}" pattern="yyyy.MM.dd HH:mm:ss" /></td>
							</c:if>
							<c:if test="${searchCondition.searchOption eq 1}">
								<td><ikep4j:timezone date="${resTimeLog.accessDate}" pattern="yyyy.MM.dd" /></td>
							</c:if>
							<c:if test="${searchCondition.searchOption eq 2}">
								<td><ikep4j:timezone date="${resTimeLog.accessDate}" pattern="yyyy.MM" /></td>
							</c:if>
							
							<c:choose>
								<c:when test="${resTimeLog.resTime > utResTimeConfig.resTimeConfigSecond * 1000}">
									<td>
										<span class="colorPoint">
											${resTimeLog.resTime / 1000}<ikep4j:message pre='${preRes}' key='top.second'/>
										</span>
									</td>
									<td><img src="<c:url value='/base/images/plupload/delete.gif'/>" /></td>
								</c:when>
								<c:otherwise>
									<td>${resTimeLog.resTime / 1000}<ikep4j:message pre='${preRes}' key='top.second'/></td>
									<td><img src="<c:url value='/base/images/plupload/done.gif'/>" /></td>
								</c:otherwise>
							</c:choose>
						</tr>
						<c:set var="rowIndex" value="${rowIndex-1}"/>
						</c:forEach>				      
				    </c:otherwise> 
				</c:choose>  
			</tbody>
		</table>
		<!--Page Number Start--> 
		<spring:bind path="searchCondition.pageIndex">
		<ikep4j:pagination searchFormId="searchForm" ajaxEventFunctionName="getList"  pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind> 
		<!--//Page Number End--> 
	</div>
	</form>
	<!--//blockListTable End-->