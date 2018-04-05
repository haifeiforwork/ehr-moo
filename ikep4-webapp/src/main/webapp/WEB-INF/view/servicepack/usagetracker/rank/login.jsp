<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preStats"  value="ui.servicepack.usagetracker.rank" />
<c:set var="preButton"  value="ui.servicepack.usagetracker.common.button" />
<c:set var="preSearch"  value="ui.servicepack.usagetracker.common.searchCondition" /> 
<c:set var="preMessage"  value="message.servicepack.usagetracker" /> 
<c:set var="common" value="ui.servicepack.usagetracker.common" />

<ikep4j:message  pre='${preMessage}' key='endDate' var="endDate"/>
<ikep4j:message  pre='${preMessage}' key='startDate' var="startDate"/>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script  type="text/javascript">
<!--


(function($) {
	$(document).ready(function() {
		
		$('#searchOption12').click(function(){
			setAction();
			$('#searchOption').val(12);
			$('#searchForm').submit();
		});
		
		$('#searchOption6').click(function(){
			setAction();
			$('#searchOption').val(6);
			$('#searchForm').submit();
		});
		
		$('#searchOption3').click(function(){
			setAction();
			$('#searchOption').val(3);
			$('#searchForm').submit();
		});
		
		$('#searchOption1').click(function(){
			setAction();
			$('#searchOption').val(1);
			$('#searchForm').submit();
		});
		
		$('#btnSearch').click(function(){
			setAction();
			$('#searchOption').val(-1);
			$('#searchForm').submit();
		});
		
		$("select[name=pagePerRecord]").change(function() {
			 $("#searchButton").click();  
		}); 
		
		$("#searchButton").click(function() {   
			setAction();
			$jq("#searchForm").submit(); 
			return false; 
		});
		
		$("#excelDownload").click(function() { 
			$jq("#searchForm").attr("action","<c:url value='/servicepack/usagetracker/rank/excel/login.do'/>");
			$jq("#searchForm").submit(); 
			return false; 
		});
		
		
		var setAction = function(){
			$jq("#searchForm").attr("action","<c:url value='/servicepack/usagetracker/rank/login.do'/>");
		};
		
		$("#startDate").datepicker(
				{
					minDate: '-3m',
					maxDate: '+0d',
				    onSelect: function(dateText, inst) {
				    		var endDate = $jq("#endDate").val();
				    		
				    		if( endDate.length > 0 )
				    		{
				    			var tStartDate = $jq("#startDate").datepicker("getDate");
				    			var tEndDate = $jq("#endDate").datepicker("getDate");
				    			
				    			if( tStartDate > tEndDate )
				    			{
				    				alert("<ikep4j:message key='ui.servicepack.usagetracker.common.lesthen' arguments='${startDate},${endDate}'/>");
				    				$jq(this).val("");
				    			}	
				    		}	
				    	
				    }
				}	
		).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("#endDate").datepicker(
				{
				    maxDate: '+0d',
				    onSelect: function(dateText, inst) {
				    		var startDate = $jq("#startDate").val();
				    		
				    		if( startDate.length > 0 )
				    		{
				    			var tStartDate = $jq("#startDate").datepicker("getDate");
				    			var tEndDate = $jq("#endDate").datepicker("getDate");
					    			
				    			if( tEndDate < tStartDate )
				    			{
				    				alert("<ikep4j:message key='ui.servicepack.usagetracker.common.grethen' arguments='${endDate},${startDate}'/>");
				    				$jq(this).val("");
				    			}	
				    		}	
				    }
				}
		).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
		
	});
	
})(jQuery);

var getColleague = function(userId){
	iKEP.goProfilePopupMain(userId);
};	
-->
</script>
				<h1 class="none">컨텐츠영역</h1>

				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${preStats}' key='pagetitle.login'/></h2>
				</div>
				<!--//pageTitle End-->
				<form id="searchForm" action="<c:url value='/servicepack/usagetracker/rank/login.do'/>" method="post">
					<input type="hidden" name="searchOption"  id="searchOption" title="" value="${result.searchOption}"/>
						
				<!--blockSearch Start-->
				<div class="blockSearch">
					<div class="corner_RoundBox03">
						
						<table summary="">
							<caption></caption>
							<tbody>
								<tr>
									<td scope="row" style="padding:0;">
										<c:forEach var="code" items="${searchOptionList}" begin="2"> 
												<a class="button_s" id="searchOption${code.key}" href="#a"><span><ikep4j:message key='${code.value}' /></span></a>
										</c:forEach> 
										<div class="subInfo">
											<input type="text" class="inputbox datepicker" id="startDate" name="startDate" title="<ikep4j:message pre='${common}'  key='date.start' />" size="10" value="<ikep4j:timezone  date="${searchCondition.startDate}"  pattern="message.servicepack.usagetracker.timezone.dateformat.yyyyMMdd" keyString="true"/>"/><img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${common}' key='date.calendar' />" /> ~
											<input type="text" class="inputbox datepicker" id="endDate" name="endDate" title="<ikep4j:message pre='${common}'  key='date.end' />"  size="10" value="<ikep4j:timezone  date="${searchCondition.endDate}"  pattern="message.servicepack.usagetracker.timezone.dateformat.yyyyMMdd" keyString="true"/>"/><img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${common}' key='date.calendar' />" />
										</div>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="searchBtn"><a href="#a" id="btnSearch"><span><ikep4j:message pre='${preButton}' key='search'/></span></a></div>
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
						<div class="listInfo">  
							<spring:bind path="searchCondition.pagePerRecord">  
								<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
								<c:forEach var="code" items="${codeList}">
									<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
								</c:forEach> 
								</select> 
							</spring:bind>
							<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span> </div>
							<div class="tableSearch">
								<a href="#a" id="excelDownload"><img src="<c:url value='/base/images/icon/ic_xls.gif'/>" alt="<ikep4j:message  key='ui.servicepack.usagetracker.common.button.download' />" /><ikep4j:message  key='ui.servicepack.usagetracker.common.button.download' /></a>
							</div>
						</div>	  
						<div style="display:none;"><a href="#a" id="searchButton"></a></div>
						<div class="clear"></div>
					</div>
					<!--//tableTop End-->
					<table id="infoTable" summary="">   
						<col style="width: 6%;"/>
						<col style="width: 54%;"/>
						<col style="width: 40%;"/>
						<thead>
							<tr>
								<th scope="col"><ikep4j:message pre='${preStats}' key='grid.rowIndex' /></th>
								<th scope="col"><ikep4j:message pre='${preStats}' key='grid.usageCount' /></th>
								<th scope="col"><ikep4j:message pre='${preStats}' key='grid.userName' /></th>
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
									<c:forEach var="info" items="${searchResult.entity}">
									<tr>
										<td> ${rowIndex}</td>
										<td>${info.usageCount}</td>
										<td class="textLeft ellipsis"><a href="#a" onclick="getColleague('${info.userId}');">
												<c:choose>
											      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
											       ${info.userName}&nbsp;${info.jobTitleName}&nbsp;${info.teamName} 
											      </c:when>
											      <c:otherwise>
											       ${info.userEnglishName}&nbsp;${info.jobTitleEnglishName}&nbsp;${info.teamEnglishName}  
											      </c:otherwise>
											     </c:choose>
											     </a>
										</td>
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