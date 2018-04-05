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
		
		$("#searchButton").click(function() {   
			setAction();
			$jq("#searchForm").submit(); 
			return false; 
		});
		
		$("#excelDownload").click(function() { 
			$jq("#searchForm").attr("action","<c:url value='/servicepack/usagetracker/stats/excel/sms.do'/>");
			$jq("#searchForm").submit(); 
			return false; 
		});
		
		
		var setAction = function(){
			$jq("#searchForm").attr("action","<c:url value='/servicepack/usagetracker/stats/sms.do'/>");
		};
		
		$("#startDate").datepicker(
				{
					//minDate: '-3m',
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
		var tmp1 = 0;
		var tmp2 = 0;
		var tmp3 = 0;
		<c:forEach var="sms" items="${smsTotalList}">
		tmp1 = "${sms.smsCountSum}" * 11;
		tmp2 = "${sms.mmsCountSum}" * 31;
		tmp3 = tmp1+tmp2;
		</c:forEach>
		$("#smsAmount").append(commanTrn(tmp1));
		$("#mmsAmount").append(commanTrn(tmp2));
		$("#allAmount").append(commanTrn(tmp3));
	});
	
	
})(jQuery);

var getColleague = function(userId){
	iKEP.goProfilePopupMain(userId);
};	

function commanTrn(obj) 
{
  var n = obj;
  var reg = /(^[+-]?\d+)(\d{3})/;   // 정규식
  n += '';                          // 숫자를 문자열로 변환
  while (reg.test(n))
  n = n.replace(reg, '$1' + ',' + '$2');  
  return n;     
}
-->
</script>
				<h1 class="none">컨텐츠영역</h1>

				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2>SMS 사용량 통계</h2>
				</div>
				<!--//pageTitle End-->
				<form id="searchForm" action="<c:url value='/servicepack/usagetracker/stats/sms.do'/>" method="post">
					<input type="hidden" name="searchOption"  id="searchOption" title="" value=""/>
						
				<!--blockSearch Start-->
				<div class="blockSearch">
					<div class="corner_RoundBox03">
						
						<table summary="">
							<caption></caption>
							<tbody>
								<tr>
									<td scope="row" style="padding:0;">
										<div class="subInfo">
											<select name="menuId" id="menuId">
												<option value="" >전체</option>
												<option value="본사" <c:if test="${searchCondition.menuId eq '본사'}">selected="selected"</c:if>>본사</option>
												<option value="대구공장" <c:if test="${searchCondition.menuId eq '대구공장'}">selected="selected"</c:if>>대구</option>
												<option value="진주공장" <c:if test="${searchCondition.menuId eq '진주공장'}">selected="selected"</c:if>>진주</option>
												<option value="울산공장" <c:if test="${searchCondition.menuId eq '울산공장'}">selected="selected"</c:if>>울산</option>
												<option value="기타" <c:if test="${searchCondition.menuId eq '기타'}">selected="selected"</c:if>>기타</option>
											</select>
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
				
				<div class="blockListTable">  
					<!--tableTop Start-->  
					<div class="tableTop">  
						<div class="listInfo">  
							<div class="tableSearch">
								<a href="#a"  id="excelDownload"><img src="<c:url value='/base/images/icon/ic_xls.gif'/>" alt="<ikep4j:message  key='ui.servicepack.usagetracker.common.button.download' />" /><ikep4j:message  key='ui.servicepack.usagetracker.common.button.download' /></a>
							</div>
						</div>	  
						<div style="display:none;"><a href="#a" id="searchButton"></a></div>
						<div class="clear"></div>
					</div>
					<!--//tableTop End-->
					<table id="infoTable" summary="">   
						<col style="width: 25%;"/>
						<col style="width: 30%;"/>
						<col style="width: 15%;"/>
						<col style="width: 15%;"/>
						<col style="width: 15%;"/>
						<thead>
							<tr>
								<th scope="col" rowspan="2">사업장</th>
								<th scope="col" rowspan="2" style="border-left:#b1b5ba 1px solid;border-right:#b1b5ba 1px solid;">부서</th>
								<th scope="col" colspan="3">사용건수</th>
							</tr>
							<tr>
								<th scope="col">SMS</th>
								<th scope="col" style="border-left:#b1b5ba 1px solid;border-right:#b1b5ba 1px solid;">LMS</th>
								<th scope="col">합계</th>
							</tr>
						</thead> 
						<tbody>
							<c:forEach var="sms" items="${smsList}">
							<tr>
								<td>${sms.workspace}</td>
								<td>${sms.teamName}</td>
								<td><fmt:formatNumber value="${sms.smsCountSum}" /></td>
								<td><fmt:formatNumber value="${sms.mmsCountSum}" /></td>
								<td><fmt:formatNumber value="${sms.allCountSum}" /></td>
							</tr>
							</c:forEach>	
							<c:forEach var="sms" items="${smsTotalList}">
							<tr>
								<td colspan="2" style="text-align:center;">합계</td>
								<td><fmt:formatNumber value="${sms.smsCountSum}" /></td>
								<td><fmt:formatNumber value="${sms.mmsCountSum}" /></td>
								<td><fmt:formatNumber value="${sms.allCountSum}" /></td>
							</tr>
							</c:forEach>
							<tr>
								<td colspan="2" style="text-align:center;">금액</td>
								<td><span id="smsAmount" name="smsAmount"/></td>
								<td><span id="mmsAmount" name="nmsAmount"/></td>
								<td><span id="allAmount" name="allAmount"/></td>
							</tr>
						</tbody>
					</table>
				</div>
				</form>
