<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preStats"  value="ui.servicepack.usagetracker.stats" />
<c:set var="preButton"  value="ui.servicepack.usagetracker.common.button" />
<c:set var="preSearch"  value="ui.servicepack.usagetracker.common.searchCondition" /> 
<c:set var="preMessage"  value="message.servicepack.usagetracker" /> 
<c:set var="common" value="ui.servicepack.usagetracker.common" />

<ikep4j:message  pre='${preMessage}' key='endDate' var="endDate"/>
<ikep4j:message  pre='${preMessage}' key='startDate' var="startDate"/>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/plot_chart/jquery.jqplot.css"/>" />

<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jquery.jqplot.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/excanvas.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.barRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.pieRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.categoryAxisRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.pointLabels.min.js"/>"></script>
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
		
		$("select[name=menuId]").change(function() {
			 $("#btnSearch").click();  
		});
		
		$("select[name=subMenuId]").change(function() {
			 $("#btnSearch").click();  
		});
		
		$("#searchButton").click(function() {  
			setAction();
			$('#searchOption').val(-1);
			$jq("#searchForm").submit(); 
			return false; 
		});
		
		$("#excelDownload").click(function() { 
			$jq("#searchForm").attr("action","<c:url value='/servicepack/usagetracker/stats/excel/simpleMobileMenu.do'/>");
			$jq("#searchForm").submit(); 
			return false; 
		});
		
		
		var setAction = function(){
			$jq("#searchForm").attr("action","<c:url value='/servicepack/usagetracker/stats/simpleMobileMenu.do'/>");
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
					<h2>모바일 <ikep4j:message pre='${preStats}' key='pagetitle.menu'/></h2>
				</div>
				<!--//pageTitle End-->
				<form id="searchForm" action="<c:url value='/servicepack/usagetracker/stats/simpleMobileMenu.do'/>" method="post">
					<input type="hidden" name="searchOption"  id="searchOption" title="" value="${result.searchOption}"/>
				<!--blockSearch Start-->
				<div class="blockSearch">
					<div class="corner_RoundBox03">
						
						<table summary="">
							<caption></caption>
							<tbody>
								<tr>
									<th scope="row"><ikep4j:message pre='${preStats}' key='menuTitle'/></th>
									<td scope="row" style="padding:0;">
										<spring:bind path="searchCondition.menuId">                             			
		                                   	<select title="${status.expression}" name="${status.expression}">
		                                        <option value="">전체</option>	                                        
		                                        <c:forEach var="code" items="${menuList}" begin="1">
		                                        	<option value="${code.menuId}" <c:if test="${searchCondition.menuId eq code.menuId}">selected="selected"</c:if>>${code.menuName}</option>
		                                        </c:forEach>
		                                    </select>	                                    
		                                </spring:bind>
		                                <spring:bind path="searchCondition.subMenuId">                             			
		                                   	<select title="${status.expression}" name="${status.expression}">
		                                        <option value="">전체</option>	                                        
		                                        <c:forEach var="code" items="${subMenuList}" begin="1">
		                                        	<option value="${code.subMenuId}" <c:if test="${searchCondition.subMenuId eq code.subMenuId}">selected="selected"</c:if>>${code.subMenuName}</option>
		                                        </c:forEach>
		                                    </select>	                                    
		                                </spring:bind>
										<div class="subInfo">
											<input type="text" class="inputbox datepicker" id="startDate" name="startDate" title="<ikep4j:message pre='${common}'  key='date.start' />" size="10" value="<ikep4j:timezone  date="${searchCondition.startDate}"  pattern="message.servicepack.usagetracker.timezone.dateformat.yyyyMMdd" keyString="true"/>"/><img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${common}'  key='date.calendar' />" /> ~
											<input type="text" class="inputbox datepicker" id="endDate" name="endDate" title="<ikep4j:message pre='${common}'  key='date.end' />"  size="10" value="<ikep4j:timezone  date="${searchCondition.endDate}"  pattern="message.servicepack.usagetracker.timezone.dateformat.yyyyMMdd" keyString="true"/>"/><img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${common}'  key='date.calendar' />" />
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
				
				<!--graph Start-->
				<!--//graph End-->
				<div class="blockBlank_20px"></div>
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
							<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span> 
							<span><ikep4j:message pre='${preStats}' key='grid.menu' /></span> 
							</div>
							<div class="tableSearch">
								<a href="#a"  id="excelDownload"><img src="<c:url value='/base/images/icon/ic_xls.gif'/>" alt="<ikep4j:message  key='ui.servicepack.usagetracker.common.button.download' />" /><ikep4j:message  key='ui.servicepack.usagetracker.common.button.download' /></a>
							</div>
						</div>	  
						<div style="display:none;"><a href="#a" id="searchButton"></a></div>
						<div class="clear"></div>
					</div>
					<!--//tableTop End-->
					<table id="infoTable" summary="">   
						<col style="width: 6%;"/>
						<col style="width: 34%;"/>
						<col style="width: 34%;"/>
						<col style="width: 30%;"/>
						<thead>
							<tr>
								<th scope="col"><ikep4j:message pre='${preStats}' key='grid.rowIndex' /></th>
								<th scope="col"><ikep4j:message pre='${preStats}' key='grid.menuName' /></th>
								<th scope="col"><ikep4j:message pre='${preStats}' key='grid.accessUser' /></th>
								<th scope="col"><ikep4j:message pre='${preStats}' key='grid.accessTime' /></th>
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
										<td class="textLeft ellipsis">${info.menuName}</td>
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
										<td>
										<ikep4j:timezone  date="${info.accessDate}"  pattern="message.servicepack.usagetracker.timezone.dateformat.yyyyMMddhhmmss" keyString="true"/></td>
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
