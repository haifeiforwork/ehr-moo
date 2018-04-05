<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="preHeader"  value="ui.lightpack.board.boardAdmin.createBoardView" /> 
<c:set var="preDetail"  value="ui.lightpack.board.boardAdmin.createBoardView.detail" />
<c:set var="preMessage" value="message.lightpack.common.boardAdmin" />
<c:set var="preButton"  value="ui.lightpack.common.button" />   

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/approval/admin/apprForm.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/base/js/wceditor/css/editor.css'/>"/>
<script type="text/javascript" src="<c:url value='/base/js/units/approval/admin/apprForm.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/planner/fullcalendar-1.5.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/planner/calendar.css"/>" />
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/fullcalendar-1.5-cust.js"/>"></script>

<script  type="text/javascript">
<!--// 

	(function($) {
        
        $(document).ready(function() {   
            
        });
        
        
		
	})(jQuery);  

	function getList() {
	
	$jq("#itemForm").attr("action", "<c:url value='/lightpack/officeway/officewayUseRequestTeamStatistics.do' />");
	$jq("#itemForm")[0].submit();
	}
	
	function teamViewPopup(teamId, calMnt) {
		var url = "<c:url value='/lightpack/officeway/officewayUseRequestTeamList.do'/>?teamId="+teamId+"&viewMonth="+calMnt;
		
		iKEP.popupOpen(url , {width:1200, height:600});
	}
//-->
</script>

<!--popup Start-->

<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>팀/월별 조회</h2>
	<div class="clear"></div>
</div>
	<!--popup_contents Start-->
	
	<form id="itemForm" name="itemForm" method="post" action="" onsubmit="return false;">	
		<input type="hidden" name="startDate" value=""/>    
		<input type="hidden" name="endDate" value=""/> 
		<input type="hidden" name="status" value=""/> 
		<input type="hidden" name="month" value=""/> 
   		<!--blockDetail Start-->
		<div class="blockDetail">
			<table>
				<caption></caption>
				<colgroup>
					<col width="15%" />
					<col width="85%" />
				</colgroup>
				<tbody>   
    	      		<tr>
    					<th>년도</th>
    					<td>
		                    <select name="year" onchange="getList();">                               		
						        <c:forEach var="year" begin="2016" end="${nowYear}" step="1">
						        	<option value="${year}" <c:if test="${year eq nowYear}">selected="selected"</c:if>>${year}</option>
						        </c:forEach>
						        <option value="${nowYear+1}" <c:if test="${year eq nowYear+1}">selected="selected"</c:if>>${nowYear+1}</option>
						        <option value="${nowYear+2}" <c:if test="${year eq nowYear+2}">selected="selected"</c:if>>${nowYear+2}</option>
					        </select>	
					        <c:if test="${oftrRole || oftlRole}">
					        <select  name="teamId" onchange="getList();">
								<c:forEach var="officewayTeam" items="${officewayUseTeamList}" varStatus="status">
									<option value="${officewayTeam.teamId}" <c:if test="${teamId eq officewayTeam.teamId}">selected="selected"</c:if>>${officewayTeam.teamName}</option>
								</c:forEach>
							</select>	
							</c:if>	                       	
						</td>
    			    </tr>
    			</tbody>
			</table>
			<table>
				<caption></caption>
				<colgroup>
					<col width="12%" />
					<col width="*" />
					<col width="10%" />
				</colgroup>
				<tbody>   
    	      		<tr>
    					<td style="padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;border:0 0 0 0;">
    						<table>
								<caption></caption>
								<colgroup>
									<col width="*" />
								</colgroup>
								<tbody>  
									<tr><th style="text-align:center;">부서</th></tr>
									<c:set var="team1" value="" /> 
									<c:forEach var="statistics1" items="${statisticsList1}" varStatus="status">
									<c:if test="${empty team1 || team1 != statistics1.teamId}" >
				    	      		<tr>
				    					<th style="text-align:center;">${statistics1.teamName}</th>
				    			    </tr>
				    			    </c:if>
				    			    <c:set var="team1" value="${statistics1.teamId}" />
				    			    </c:forEach>
				    			</tbody>
							</table>
    					</td>
    			    	<td style="padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;border:0 0 0 0;">
    			    		<table>
								<caption></caption>
								<colgroup>
									<col width="8%" />
									<col width="8%" />
									<col width="8%" />
									<col width="8%" />
									<col width="8%" />
									<col width="8%" />
									<col width="8%" />
									<col width="8%" />
									<col width="9%" />
									<col width="9%" />
									<col width="9%" />
									<col width="9%" />
								</colgroup>
								<tbody>   
									<tr>
				    					<th style="text-align:center;">1월</th>
				    					<th style="text-align:center;">2월</th>
				    					<th style="text-align:center;">3월</th>
				    					<th style="text-align:center;">4월</th>
				    					<th style="text-align:center;">5월</th>
				    					<th style="text-align:center;">6월</th>
				    					<th style="text-align:center;">7월</th>
				    					<th style="text-align:center;">8월</th>
				    					<th style="text-align:center;">9월</th>
				    					<th style="text-align:center;">10월</th>
				    					<th style="text-align:center;">11월</th>
				    					<th style="text-align:center;">12월</th>
				    			    </tr>
									<c:set var="team2" value="" />
									<tr>
									<c:forEach var="statistics1" items="${statisticsList1}" varStatus="status">
				    	      			<c:if test="${empty team2 || team2 == statistics1.teamId}" >
				    	      				<c:if test="${statistics1.price2 != '0'}" >
				    						<td style="text-align:right;"><a style="cursor: pointer;" onclick="teamViewPopup(${statistics1.teamId},${statistics1.calMnt});" ><fmt:formatNumber value="${statistics1.price2}" type="number" /></a></td>
				    						</c:if>
				    						<c:if test="${statistics1.price2 == '0'}" >
				    							<td style="text-align:right;"><fmt:formatNumber value="${statistics1.price2}" type="number" /></td>
				    						</c:if>
				    					</c:if>
				    			    	<c:if test="${!empty team2 && team2 != statistics1.teamId}" >
				    			    		</tr>
				    			    		<tr>
				    			    			<c:if test="${statistics1.price2 != '0'}" >
				    							<td style="text-align:right;"><a style="cursor: pointer;" onclick="teamViewPopup(${statistics1.teamId},${statistics1.calMnt});" ><fmt:formatNumber value="${statistics1.price2}" type="number" /></a></td>
				    							</c:if>
				    							<c:if test="${statistics1.price2 == '0'}" >
				    								<td style="text-align:right;"><fmt:formatNumber value="${statistics1.price2}" type="number" /></td>
				    							</c:if>
				    					</c:if>
				    			    <c:set var="team2" value="${statistics1.teamId}" />
				    			    </c:forEach>
				    			    </tr>
				    			</tbody>
							</table>
    			    	</td>
    			    	<td style="padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;border:0 0 0 0;">
    			    		<table>
								<caption></caption>
								<colgroup>
									<col width="*" />
								</colgroup>
								<tbody>   
									<tr><th style="text-align:center;background-color:#FAED7D;color:black;font-weight:bold;">합계</th></tr>
									<c:forEach var="statistics2" items="${statisticsList2}" varStatus="status">
				    	      		<tr>
				    					<td style="text-align:right;background-color:#FAED7D;color:black;font-weight:bold;"><fmt:formatNumber value="${statistics2.price2}" type="number" /></td>
				    			    </tr>
				    			    </c:forEach>
				    			</tbody>
							</table>
    			    	</td>
    			    </tr>
    			    <tr>
    			    	<th style="padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;text-align:center;background-color:#FAED7D;color:black;font-weight:bold;">합계</th>
    			    	<td style="padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;border:0 0 0 0;background-color:#FAED7D;color:black;font-weight:bold;">
    			    		<table>
								<caption></caption>
								<colgroup>
									<col width="8%" />
									<col width="8%" />
									<col width="8%" />
									<col width="8%" />
									<col width="8%" />
									<col width="8%" />
									<col width="8%" />
									<col width="8%" />
									<col width="9%" />
									<col width="9%" />
									<col width="9%" />
									<col width="9%" />
								</colgroup>
								<tbody>   
									<tr>
									<c:forEach var="statistics3" items="${statisticsList3}" varStatus="status">
				    					<td style="text-align:right;background-color:#FAED7D;color:black;font-weight:bold;"><fmt:formatNumber value="${statistics3.price2}" type="number" /></td>
				    			    </c:forEach>
				    			    </tr>
				    			</tbody>
							</table>
    			    	</td>
    			    	<td style="padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;text-align:right;background-color:#FAED7D;color:black;font-weight:bold;"><fmt:formatNumber value="${price6}" type="number" />&nbsp;</td>
    			    </tr>
    			</tbody>
			</table>
		</div>
		<!--//blockDetail End-->	
																																			
		
		
		<!--blockButton Start-->
		
	</form>

<!--//popup End-->