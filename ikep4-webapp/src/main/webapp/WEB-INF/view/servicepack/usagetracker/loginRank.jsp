<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>

<c:set var="preSearch"  value="ui.servicepack.usagetracker.common.searchCondition" />
<c:set var="common" value="ui.servicepack.usagetracker.common" />
<c:set var="preUi" value="ui.servicepack.usagetracker.loginRank" />
<c:set var="validate"  value="ui.servicepack.usagetracker.validate" />

 

<script  type="text/javascript">
(function($) {  
	recordCnt = function() {
		$("#searchForm").submit();
	};
	
	$(document).ready(function() {
		

	      $("#btnSearch").click(function(){

	    	  var startdate = $jq("input[name='startdate']").val();
	    	  var enddate   = $jq("input[name='enddate']").val();
	    	  
	    	  if(startdate == ''){
	    		  alert("<ikep4j:message pre='${validate}' key='startdate'/>"); 
	    		  $jq("input[name='startdate']").focus();
	    		  return;
	    	  }
	    	  if(enddate == ''){
	    		  alert("<ikep4j:message pre='${validate}' key='enddate'/>");
	    		  $jq("input[name='enddate']").focus();
	    		  return;
	    	  }
	    	  
	    	  var tmp = enddate - startdate;
	    	  if(tmp < 0){
	    		  alert("<ikep4j:message pre='${validate}' key='checkdate'/>");
	    		  $jq("input[name='enddate']").focus();
	    		  return;
	    	  }

	    	  $jq("input[name='searchterm']").val('');
        	  $("#searchForm").submit();
	      });
	    
          $("#searchTerm12").click(function() {
        	  $jq("input[name='searchterm']").val('12');
        	  $jq("input[name='startdate']").val('');
        	  $jq("input[name='enddate']").val('');
        	  
        	  $("#searchForm").submit();
        	}); 
          $("#searchTerm6").click(function() {
        	  $jq("input[name='searchterm']").val('6');
        	  $jq("input[name='startdate']").val('');
        	  $jq("input[name='enddate']").val('');
        	  
        	  $("#searchForm").submit();
        	});          
          $("#searchTerm3").click(function() {
        	  $jq("input[name='searchterm']").val('3');
        	  $jq("input[name='startdate']").val('');
        	  $jq("input[name='enddate']").val('');
        	  
        	  $("#searchForm").submit();
        	});          
          $("#searchTerm1").click(function() {
        	  $jq("input[name='searchterm']").val('1');
        	  $jq("input[name='startdate']").val('');
        	  $jq("input[name='enddate']").val('');
        	  
        	  $("#searchForm").submit();
        	});    	    		
				

	}); 
})(jQuery);
</script>


				<h1 class="none">컨텐츠영역</h1>

				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${preUi}' key='pagetitle'/></h2>
					<div id="pageLocation">
						<ul>
							<li class="liFirst"><ikep4j:message pre='${preUi}' key='home'/></li>
							<li><ikep4j:message pre='${preUi}' key='depth1'/></li>
							<li><ikep4j:message pre='${preUi}' key='depth2'/></li>
							<li class="liLast"><ikep4j:message pre='${preUi}' key='depth3'/></li>
						</ul>
					</div>
				</div>
				<!--//pageTitle End-->
				
				<!--blockSearch Start-->
				<div class="blockSearch">
					<div class="corner_RoundBox03">
						<form id="searchForm" action="listLoginRank.do">
						<input type="hidden" name="pageIndex"  value="${searchCondition.pageIndex}"/>
						<table summary="login Ranking Search">
							<caption></caption>
							<tbody>
								<tr>
									<th scope="row"><ikep4j:message pre='${preUi}' key='subtitle'/></th>
									<td scope="row" style="padding:0;">
										<a class="button_s" id="searchTerm12" href="#a"><span><ikep4j:message pre='${common}' key='btn12'/></span></a>
										<a class="button_s" id="searchTerm6"  href="#a"><span><ikep4j:message pre='${common}' key='btn6'/></span></a>
										<a class="button_s" id="searchTerm3"  href="#a"><span><ikep4j:message pre='${common}' key='btn3'/></span></a>
										<a class="button_s" id="searchTerm1"  href="#a"><span><ikep4j:message pre='${common}' key='btn1'/></span></a>&nbsp;
										<input type="hidden" name="searchterm" value="1">
										<div class="subInfo">
										
											<input type="text" class="inputbox" id="date1" name="startdate" title="<ikep4j:message pre='${common}' key='date.start'/>" value="${searchCondition.startdate}" size="8" /> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${common}' key='date.calendar'/>" /> ~
											<input type="text" class="inputbox" id="date2" name="enddate" title="<ikep4j:message pre='${common}' key='date.end'/>" value="${searchCondition.enddate}" size="8" /> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${common}' key='date.calendar'/>" />
										</div>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="searchBtn"><a href="#a" id="btnSearch"><span><ikep4j:message pre='${common}' key='date.search'/></span></a></div>
						<div class="l_t_corner"></div>
						<div class="r_t_corner"></div>
						<div class="l_b_corner"></div>
						<div class="r_b_corner"></div>				
					</div>
				</div>	
				<!--//blockSearch End-->
				
				<!--tableTop Start-->
				<div class="tableTop">		
					<div class="listInfo">
							<select name="pagePerRecord" title='' onchange="recordCnt()"/>
								<option value="10" <c:if test="${searchCondition.pagePerRecord eq 10}">selected="selected"</c:if>>10</option>
								<option value="20" <c:if test="${searchCondition.pagePerRecord eq 20}">selected="selected"</c:if>>20</option>
								<option value="30" <c:if test="${searchCondition.pagePerRecord eq 30}">selected="selected"</c:if>>30</option>
								<option value="40" <c:if test="${searchCondition.pagePerRecord eq 40}">selected="selected"</c:if>>40</option>
								<option value="50" <c:if test="${searchCondition.pagePerRecord eq 50}">selected="selected"</c:if>>50</option>
							</select> 
						<div class="totalNum">${searchCondition.pageIndex}/ ${searchCondition.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchCondition.recordCount}</span>)</div>
					</div>	
					<div class="tableSearch">
						<a href="#a"><img src="<c:url value="/base/images/icon/ic_xls.gif"/>" alt="<ikep4j:message pre='${common}' key='excel.down'/>" /></a>
					</div>			
					<div class="clear"></div>
				</div>
				<!--//tableTop End-->
 
				<!--blockListTable Start-->
				<div class="blockListTable">
					<table summary="login Count">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="10%"><ikep4j:message pre='${preUi}' key='tableheader1'/></th>
								<th scope="col" width="20%"><ikep4j:message pre='${preUi}' key='tableheader2'/> <a href="#a"><img src="<c:url value="/base/images/icon/ic_tablesort_down.gif"/>" alt="<ikep4j:message pre='${common}' key='order.desc'/>" /></a></th>
								<th scope="col" width="*"><ikep4j:message pre='${preUi}' key='tableheader3'/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="tmp" items="${loginRankList.entity}" varStatus="inx">
							<tr>
								<td>${tmp.rnum}</td>
								<td>${tmp.cnt}</td>
								<td class="textLeft">${tmp.userAgent}</td>
							</tr>
							</c:forEach>
						</table>
																																																																																																				
						</tbody>
					</table>
					<!--Page Numbur Start-->
					<div class="pageNum">	
						<!--Page Numbur Start--> 
						<spring:bind path="searchCondition.pageIndex">
	                    <ikep4j:pagination searchFormId="searchForm" pageIndexInput="${status.expression}" searchCondition="${searchCondition}"/>
						</spring:bind> 
					</div>
					<!--//Page Numbur End-->
					</form>			
				</div>
				<!--//blockListTable End-->
