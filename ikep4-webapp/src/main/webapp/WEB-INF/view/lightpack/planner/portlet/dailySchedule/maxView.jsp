<%@ page contentType="text/html; charset=utf-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/fullcalendar-date-utils.js"/>"></script>
<%-- normal view 와 동일한 디자인 : 화면 기획 없음. --%>
<div class="po_schedule" id="${portletConfigId}">
	<div class="date"></div>
	<div class="pr_schedule_c">
		<table class="pr_schedule_time" summary="">
			<caption></caption>
			<tbody>
				<tr>
					<td style="width:18.18%" class="textCenter">09:00</td>
					<td style="width:9.09%;"></td>
					<td style="width:18.18%" class="textCenter">12:00</td>
					<td style="width:9.09%;"></td>
					<td style="width:18.18%" class="textCenter">15:00</td>
					<td style="width:9.09%;"></td>
					<td style="width:18.18%" class="textCenter">18:00</td>
				</tr>
			</tbody>
		</table>
		<table summary="My Schedule">
			<caption></caption>
			<thead>
				<tr>
					<th></th>
					<th></th>
					<th><img alt="" src='<c:url value="/base/images/common/bar_sch.gif"/>'></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th><img alt="" src='<c:url value="/base/images/common/bar_sch.gif"/>'></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th><img alt="" src='<c:url value="/base/images/common/bar_sch.gif"/>'></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th><img alt="" src='<c:url value="/base/images/common/bar_sch.gif"/>'></th>
					<th></th>
				</tr>
			</thead>									
			<tbody id="daily_schedule_pane">
				<tr>
					<td><span>08:00~08:30</span></td>
					<td><span>08:30~09:00</span></td>
					<td><span>09:00~09:30</span></td>
					<td><span>09:30~10:00</span></td>
					<td><span>10:00~10:30</span></td>
					<td><span>10:30~11:00</span></td>
					<td><span>11:00~11:30</span></td>
					<td><span>11:30~12:00</span></td>
					<td><span>12:00~12:30</span></td>
					<td><span>12:30~13:00</span></td>
					<td><span>13:00~13:30</span></td>
					<td><span>13:30~14:00</span></td>
					<td><span>14:00~14:30</span></td>
					<td><span>14:30~15:00</span></td>
					<td><span>15:00~15:30</span></td>
					<td><span>15:30~16:00</span></td>
					<td><span>16:00~16:30</span></td>
					<td><span>16:30~17:00</span></td>
					<td><span>17:00~17:30</span></td>
					<td><span>17:30~18:00</span></td>
					<td><span>18:00~18:30</span></td>
					<td><span>18:30~19:00</span></td>
				</tr>
			</tbody>
		</table>
	<div class="pr_schedule_c">							
		<ul>			
		</ul>
	</div>
	</div>
	<div class="clear"></div>				
	<div class="l_b_corner"></div><div class="r_b_corner"></div>
</div>		

<script type="text/javascript">
var dailyOptions = {
		userId: '${user.userId}',
		portletConfigId: '${portletConfigId}'
};
</script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/portlet/dailySchedule.js"/>"></script>