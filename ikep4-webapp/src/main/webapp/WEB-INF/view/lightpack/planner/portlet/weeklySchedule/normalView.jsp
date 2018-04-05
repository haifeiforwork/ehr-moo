<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/fullcalendar-date-utils.js"/>"></script>
<script type="text/javascript">
var weeklyOptions = {
		userId: '${user.userId}',
		portletConfigId: '${portletConfigId}',
		portletConfigIdCompany: '${portletConfigId}Company',
		portletConfigIdWorkspace: '${portletConfigId}Workspace'
		
};
</script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/portlet/weeklySchedule.js"/>"></script>
<script type="text/javascript">

var dataLoadComplete= false;

(function($) {
	

		
	goWeeklySchedule = function(whereLink){
		var linkurl = "<c:url value='/lightpack/planner/calendar/init.do'/>?action="+whereLink;
		//alert(linkurl);
		location.href=linkurl;
	};
		
	createTab = function(){
		tab = $("#divTab_planer").tabs({
			select : function(event, ui) {
				switch($(ui.panel).attr("id")) {
					case "tabs-1" : getWeeklyPlanCompany();
						break;
					case "tabs-2" : getWeeklyWorkspacePlan();
						break;
					case "tabs-3" : getWeeklyPlan();
						break;
					
				}
			}
		});
	};
	
	$(document).ready(function() {

  	  getWeeklyPlanCompany();

	  createTab();

    	    	
	});
})(jQuery);
</script>


<div id="divTab_planer" class="iKEP_tab_s">
		<ul>
			<li><a href="#tabs-1">전사주간일정</a></li>
			<li><a href="#tabs-2">팀주간일정</a></li>
			<li><a href="#tabs-3">개인주간일정</a></li>

		</ul>
		<div>
			<div id="tabs-1">

			<div style="align:left;float:right;padding-bottom:3px"><img alt="more" src="<c:url value="/base/images/common/btn_more.gif"/>" onclick="goWeeklySchedule('onClickCompanyCalendar')" style="cursor:pointer"/></div>
				<div class="po_schedule_w" id="${portletConfigId}Company">
					<div class="blockListTable msgTable">
						<table summary="">
							<caption></caption>
							<thead>
								<tr>
									<th scope="col"><span class="fc_day_sun">Sunday</span></th>
									<th scope="col"><span>Monday</span></th>
									<th scope="col"><span>Tuesday</span></th>
									<th scope="col"><span>Wednesday</span></th>
									<th scope="col"><span>Thursday</span></th>
									<th scope="col"><span>Friday</span></th>
									<th scope="col" class="tdLast"><span class="colorBlue">Saturday</span></th>
								</tr>				
							</thead>
							<tbody>
								<tr>
									<td><span></span></td>
									<td><span></span></td>
									<td><span></span></td>
									<td><span></span></td>
									<td><span></span></td>
									<td><span></span></td>
									<td class="tdLast"><span></span></td>
								</tr>											
							</tbody>
						</table>
					</div>	
					<div class="pr_schedule_c">							
						<div class="date"></div>
						<ul>			
						</ul>
					</div>
					<div class="clear"></div>				
					<div class="l_b_corner"></div><div class="r_b_corner"></div>
				</div>
			
			
			</div>
			<div id="tabs-2">

			<div style="align:left;float:right;padding-bottom:3px"><img alt="more" src="<c:url value="/base/images/common/btn_more.gif"/>" onclick="goWeeklySchedule('onClickWorkspace')" style="cursor:pointer"/></div>
			
				<div class="po_schedule_w" id="${portletConfigId}Workspace">
					<div class="blockListTable msgTable">
						<table summary="">
							<caption></caption>
							<thead>
								<tr>
									<th scope="col"><span class="fc_day_sun">Sunday</span></th>
									<th scope="col"><span>Monday</span></th>
									<th scope="col"><span>Tuesday</span></th>
									<th scope="col"><span>Wednesday</span></th>
									<th scope="col"><span>Thursday</span></th>
									<th scope="col"><span>Friday</span></th>
									<th scope="col" class="tdLast"><span class="colorBlue">Saturday</span></th>
								</tr>				
							</thead>
							<tbody>
								<tr>
									<td><span></span></td>
									<td><span></span></td>
									<td><span></span></td>
									<td><span></span></td>
									<td><span></span></td>
									<td><span></span></td>
									<td class="tdLast"><span></span></td>
								</tr>											
							</tbody>
						</table>
					</div>	
					<div class="pr_schedule_c">							
						<div class="date"></div>
						<ul>			
						</ul>
					</div>
					<div class="clear"></div>				
					<div class="l_b_corner"></div><div class="r_b_corner"></div>
				</div>
			
			
			
			
			
			</div>
			<div id="tabs-3">

			<div style="align:left;float:right;padding-bottom:3px"><img alt="more" src="<c:url value="/base/images/common/btn_more.gif"/>" onclick="goWeeklySchedule('onClickMyCalendar')" style="cursor:pointer"/></div>
			
				<div class="po_schedule_w" id="${portletConfigId}">
					<div class="blockListTable msgTable">
						<table summary="">
							<caption></caption>
							<thead>
								<tr>
									<th scope="col"><span class="fc_day_sun">Sunday</span></th>
									<th scope="col"><span>Monday</span></th>
									<th scope="col"><span>Tuesday</span></th>
									<th scope="col"><span>Wednesday</span></th>
									<th scope="col"><span>Thursday</span></th>
									<th scope="col"><span>Friday</span></th>
									<th scope="col" class="tdLast"><span class="colorBlue">Saturday</span></th>
								</tr>				
							</thead>
							<tbody>
								<tr>
									<td><span></span></td>
									<td><span></span></td>
									<td><span></span></td>
									<td><span></span></td>
									<td><span></span></td>
									<td><span></span></td>
									<td class="tdLast"><span></span></td>
								</tr>											
							</tbody>
						</table>
					</div>	
					<div class="pr_schedule_c">							
						<div class="date"></div>
						<ul>			
						</ul>
					</div>
					<div class="clear"></div>				
					<div class="l_b_corner"></div><div class="r_b_corner"></div>
				</div>
			
			
			
			
			
			</div>

		</div>				
</div>
	



