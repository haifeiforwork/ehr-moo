<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%@ include file="/base/common/fileUploadControll.jsp"%>

<%--메시지 관련 Prefix 선언 Start--%> 
<c:set var="preEdit"  value="ui.lightpack.planner.edit.schedule" /> 
<c:set var="preLabel"    value="ui.lightpack.planner.common.label" />
<c:set var="preButton"  value="ui.lightpack.planner.common.button" /> 
<c:set var="preDialog"  value="ui.lightpack.planner.dialog.repeat" /> 
<c:set var="preView"  value="ui.lightpack.planner.view.schedule" /> 
<%--메시지 관련 Prefix 선언 End--%>

<c:set var="user" value="${sessionScope['ikep.user']}" />

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/planner/fullcalendar-1.5.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/planner/calendar.css"/>" />

<input type="hidden" id="startDate" value="${startDate}"/>
<input type="hidden" id="endDate" value="${endDate}"/>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.json-2.2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.postjson.js"/>"></script>

<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/fullcalendar-1.5-cust.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/plannerCommon.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/checkSchedule.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/meetingroom/ikep4Cartooletc.js"/>"></script>

<script type="text/javascript">
(function($) {
	$(document).ready(function(event) {

	});
})(jQuery);
</script>

<!-- Modal window Start -->
<!-- 사용시class="none"으로 설정 -->
<div id="mgmt-panel" title="">

<!-- div id="calendar" class="hidden"></div -->
<div style="height:10px;"></div>

	<div class="blockButton"> 
		<ul>
			<li class="btn_save"><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="save" /></span></a></li>
			<li class="btn_delete"><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
			<li class="btn_cancel"><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a></li>
		</ul>
	</div>	
	<div class="blockDetail" id="event-form">
	<form id="fileUploadForm">
		<table summary="<ikep4j:message pre="${preEdit}" key="summary" />">
			<caption></caption>
			<colgroup>
				<col width="18%" />
				<col width="82%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row"><ikep4j:message pre="${preLabel}" key="period" /></th>
					<td >
						<input id="start-date-picker" type="text" class="inputbox date-pick" name="sdate" title="<ikep4j:message pre="${preLabel}" key="startDate" />" size="10" />&nbsp;
						<select id="startDueTimeHour">
										<option value="07">07</option>
										<option value="08">08</option>
										<option value="09">09</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12" selected="selected">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23">23</option>
									</select> 시
									<select id="startDueTimeMinute">
										<option value="00" selected="selected">00</option>
										<option value="05">05</option>
										<option value="10">10</option>
										<option value="15">15</option>
										<option value="20">20</option>
										<option value="25">25</option>
										<option value="30">30</option>
										<option value="35">35</option>
										<option value="40">40</option>
										<option value="45">45</option>
										<option value="50">50</option>
										<option value="55">55</option>
									</select> 분 -
						<input id="end-date-picker" type="hidden" class="inputbox date-pick" name="edate" title="<ikep4j:message pre="${preLabel}" key="endDate" />" size="10" /><!-- &nbsp; -->
						<select id="endDueTimeHour">
										
										<option value="07">07</option>
										<option value="08">08</option>
										<option value="09">09</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12" selected="selected">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23">23</option>
										<option value="24">24</option>
									</select> 시
									<select id="endDueTimeMinute">
										<option value="00" selected="selected">00</option>
										<option value="05">05</option>
										<option value="10">10</option>
										<option value="15">15</option>
										<option value="20">20</option>
										<option value="25">25</option>
										<option value="30">30</option>
										<option value="35">35</option>
										<option value="40">40</option>
										<option value="45">45</option>
										<option value="50">50</option>
										<option value="55">55</option>
									</select> 분&nbsp;
						<!-- label><input id="repeat" name="repeat" class="checkbox" title="<ikep4j:message pre="${preLabel}" key="repeat" />" type="checkbox" value="\${repeat}" /><ikep4j:message pre="${preLabel}" key="repeat" />&nbsp;</label>
						<label><input id="wholeday" name="wholeday" class="checkbox" title="<ikep4j:message pre="${preLabel}" key="allDay" />" type="checkbox" value="\${wholeday}" /><ikep4j:message pre="${preLabel}" key="allDay" />&nbsp;</label -->
						
						<a id="checkDuplicateRepeatReserve" class="button" href="#a"><span><ikep4j:message key="ui.lightpack.meetingroom.button.checkReserve" /></span></a>
						<span id="repeat-div" class="hidden">
						<div class="schedule_bg02" style="margin-top:5px;">
							<select id="repeatType" title="<ikep4j:message pre="${preLabel}" key="daily" />" name="repeatType" class="inputbox">
								<option value="daily"><ikep4j:message pre="${preLabel}" key="daily" /></option>
								<option value="weekly"><ikep4j:message pre="${preLabel}" key="weekly" /></option>
								<option value="monthly"><ikep4j:message pre="${preLabel}" key="monthly" /></option>
								<%--option value="yearly"><ikep4j:message pre="${preLabel}" key="yearly" /></option--%>
							</select>
							<span id="repeat_pane" class="hidden"></span>
						</div>
						</span>
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre="${preLabel}" key="title" /></th>
					<td>
						<div>
							<input name="title" id="title" title="<ikep4j:message pre="${preLabel}" key="title" />" class="inputbox w80" type="text" value=""/>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">예약자원</th>
					<td>
						<input type="hidden" id="place" name="place" value="${schedule.place}"/>
						<input type="hidden" id="scheduleId" name="scheduleId" value="${schedule.scheduleId}"/>
						<input type="hidden" id="cartooletcId" name="cartooletcId" value="${cartooletcId}"/>
						${schedule.place}
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre="${preLabel}" key="contents" /></th>
					<td>
						<div><textarea id="contents" name="contents" class="w100" title="<ikep4j:message pre="${preLabel}" key="input" />" cols="" rows="20"></textarea></div>
					</td>
				</tr>
				
			</tbody>
		</table>

		</form>
	</div>			
	<div class="blockButton"> 
		<ul>
			<li class="btn_save"><a class="button" href="#a"><span>등록</span></a></li>
			<li class="btn_delete"><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
			<li class="btn_cancel"><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a></li>
		</ul>
	</div>	
</div>

<%--input id="forever" \${$item.isForever()} name="forever" class="checkbox"
 		title="<ikep4j:message pre="${preLabel}" key="forever" />" type="checkbox"/><ikep4j:message pre="${preLabel}" key="forever" /--%>
<script id="recurrenceTemplate" type="text/x-jquery-tmpl"> 
	<ikep4j:message pre="${preLabel}" key="every" />
	<input id="count" name="count" title="" class="inputbox" type="text" size="2" value="\${$item.count}"  />&nbsp;\${freqStr}
	<input id="endRepeatDate" type="text" class="inputbox" name="sdate" 
		title="<ikep4j:message pre="${preLabel}" key="date" />" value="" size="10" />&nbsp;<ikep4j:message pre="${preLabel}" key="until" />
				

	<span id="roption-span">
	{{if freq == 'weekly'}}
		<div class="schedule_bg02">
		{{tmpl($item.dayNames) '#weeklyTemplate'}}
		</div>
	{{else freq == 'monthly'}}
		<div class="schedule_bg02">
		{{html $item.getOption()}}
		</div>
	{{else freq == 'yearly'}}
		<div class="schedule_bg02">
		{{html $item.getOption()}}
		</div>
	{{/if}}
	</span>
</script>

<script id="weeklyTemplate" type="text/x-jquery-tmpl"> 
	{{each(i, dn) dayNames}}
		<label><input type="checkbox" class="checkbox weekly-dow" title="\${dn}" value="\${i+1}" />\${dn}&nbsp;&nbsp;</label>
	{{/each}}
</script>


<!--//Modal window Start-->
<div class="hidden">
	<div id="update-dialog" title="Dialog Title">	
		<p><ikep4j:message pre="${preDialog}" key="updateTitle" /></p>
		<ul>
			<li>
				<label>
					<input name="uradio" value="1" type="radio"/>
					<ikep4j:message pre="${preDialog}" key="updateThis" />
				</label>
			</li>
			<li>
				<label>
					<input name="uradio" value="2" type="radio"/>
					<ikep4j:message pre="${preDialog}" key="updateAfter" />
				</label>
			</li>
			<li>
				<label>
					<input name="uradio" value="3" type="radio"/>
					<ikep4j:message pre="${preDialog}" key="updateAll" />
				</label>
			</li>			
		</ul>
		<div class="blockButton"> 
			<ul>
				<li><a id="btn_update_ok" href="#a" class="button"><span><ikep4j:message pre="${preButton}" key="ok" /></span></a></li>
				<li><a id="btn_update_cancel" href="#a" class="button"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a></li>
			</ul>
		</div>
	</div>
</div>

<div class="hidden">
	<div id="delete-dialog" title="Dialog Title">	
		<p><ikep4j:message pre="${preDialog}" key="deleteTitle" /></p>
		<ul>
			<li>
				<label>
					<input name="dradio" value="1" type="radio"/>
					<ikep4j:message pre="${preDialog}" key="deleteThis" />
				</label>
			</li>
			<li>
				<label>
					<input name="dradio" value="2" type="radio"/>
					<ikep4j:message pre="${preDialog}" key="deleteAfter" />
				</label>
			</li>
			<li>
				<label>
					<input name="dradio" value="3" type="radio"/>
					<ikep4j:message pre="${preDialog}" key="deleteAll" />
				</label>
			</li>			
		</ul>
		<div class="blockButton"> 
			<ul>
				<li><a id="btn_delete_ok" href="#a" class="button"><span><ikep4j:message pre="${preButton}" key="ok" /></span></a></li>
				<li><a id="btn_delete_cancel" href="#a" class="button"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a></li>
			</ul>
		</div>
	</div>
</div>

<div id="attendance-dialog" class="none">
	<div><textarea style="width:97%; height:80px;"></textarea></div>
	<div class="blockButton" style="margin-top:7px;">
		<ul>
			<li><a href="#a" class="button btn_ok"><span><ikep4j:message pre="${preButton}" key="ok" /></span></a></li>
			<li><a href="#a" class="button btn_cancel"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a></li>
		</ul>
	</div>
</div>
<!--//Modal window End-->
<div id="checkSchedule"></div>