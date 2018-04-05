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
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/meetingroom/ikep4MyReserve.js"/>"></script>

<script type="text/javascript">
(function($){
	$(document).ready(function() {
		$("#meetingRoomReserveList").parent().addClass("licurrent");
	});
})(jQuery);
</script>

<form id="searchForm" name="searchForm" method="post">
<spring:bind path="searchCondition.sortColumn">
<input type="hidden" name="${status.expression}" value="${status.value}"/>
</spring:bind> 	
<spring:bind path="searchCondition.sortType">
<input type="hidden" name="${status.expression}" value="${status.value}"/>
</spring:bind> 
<spring:bind path="searchCondition.searchType">
<input type="hidden" name="${status.expression}" value="${status.value}"/>
</spring:bind> 
<spring:bind path="searchCondition.pageIndex">
<input type="hidden" name="${status.expression}" value="${status.value}"/>
</spring:bind> 
<spring:bind path="searchCondition.pagePerRecord">
<input type="hidden" name="${status.expression}" value="${status.value}"/>
</spring:bind> 
<input type="hidden" name="startPeriod" value="${startPeriod}"/>
<input type="hidden" name="endPeriod" value="${endPeriod}"/>
<input type="hidden" id="scheduleId" name="scheduleId" value="${schedule.scheduleId}"/>
						<input type="hidden" id="meetingRoomId" name="meetingRoomId" value="${meetingRoomId}"/>
</form>

<!-- Modal window Start -->
<!-- 사용시class="none"으로 설정 -->
<div id="mgmt-panel" title="">

<!-- div id="calendar" class="hidden"></div -->
<div style="height:10px;"></div>

	<div class="blockButton"> 
		<ul>
			<li class="btn_save"><a class="button" href="#a"><span>등록</span></a></li>
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
						<select id="start-time-pick" title="<ikep4j:message pre="${preLabel}" key="startTime" />" name="" class="inputbox time-pick"></select> -
						<input id="end-date-picker" type="hidden" class="inputbox date-pick" name="edate" title="<ikep4j:message pre="${preLabel}" key="endDate" />" size="10" /><!-- &nbsp; -->
						<select id="end-time-pick" title="<ikep4j:message pre="${preLabel}" key="endTime" />" name="" class="inputbox time-pick"></select>&nbsp;
						<!-- label><input id="repeat" name="repeat" class="checkbox" title="<ikep4j:message pre="${preLabel}" key="repeat" />" type="checkbox" value="\${repeat}" /><ikep4j:message pre="${preLabel}" key="repeat" />&nbsp;</label -->
						<!-- label><input id="wholeday" name="wholeday" class="checkbox" title="<ikep4j:message pre="${preLabel}" key="allDay" />" type="checkbox" value="\${wholeday}" /><ikep4j:message pre="${preLabel}" key="allDay" />&nbsp;</label -->
						<label><input id="schedulePublic" name="schedulePublic" class="checkbox" title="<ikep4j:message pre="${preLabel}" key="nopublic" />" type="checkbox" value="\${schedulePublic}" /><ikep4j:message pre="${preLabel}" key="nopublic" />&nbsp;</label>
						<span id="repeat-div" class="hidden">
						<div class="schedule_bg02" style="margin-top:5px;">
							<select id="repeatType" title="<ikep4j:message pre="${preLabel}" key="daily" />" name="repeatType" class="inputbox">
								<option value="daily"><ikep4j:message pre="${preLabel}" key="daily" /></option>
								<option value="weekly"><ikep4j:message pre="${preLabel}" key="weekly" /></option>
								<option value="monthly"><ikep4j:message pre="${preLabel}" key="monthly" /></option>
								<option value="yearly"><ikep4j:message pre="${preLabel}" key="yearly" /></option>
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
							<select id="category" class="inputbox">
							</select>&nbsp;
							<input name="title" id="title" title="<ikep4j:message pre="${preLabel}" key="title" />" class="inputbox w80" type="text" value="" maxlength="300"/>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre="${preLabel}" key="alarm" /></th>
					<td>
						<div id="alarm-div"></div>
					</td>
				</tr>
				<tr id="mail_send_yn">
					<th scope="row">메일발송 여부</th>
					<td>
						<input name="mailSendYn" id="mailSendYn" type="checkbox" class="checkbox" /> <font color="gray">※ 체크시 참석자와 참조자에게 메일을 발송합니다.</font>
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre="${preLabel}" key="participants" /></th>
					<td colspan="2">
						<input id="participantInput" name="" title="<ikep4j:message pre="${preLabel}" key="participants" />" class="inputbox" type="text" size="20"/>
						<a id="btn_parti_searchUser" class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_search.gif"/>" alt="" />Search</span></a>
						<a id="btn_parti_showAddress" class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" alt="" />Address</span></a>
						<a id="btn_parti_schedule" class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_schedule.gif"/>" alt="" />Schedule</span></a>&nbsp;
						<input id="attendanceRequest" name="" class="checkbox" 
							title="<ikep4j:message pre="${preLabel}" key="requestIsAttendance" />" 
							type="checkbox" style="vertical-align:middle"/><ikep4j:message pre="${preLabel}" key="requestIsAttendance" />
						<div class="mt5">
							<select id="participants" multiple="multiple" size="5" style="height:auto;" class="w80" title="<ikep4j:message pre="${preLabel}" key="participants" />">
							</select>
							<a id="btn_delete_paticipant" class="button_ic valign_bottom" href="#a" style="vertical-align:bottom"><span><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="" />Delete</span></a> 
							<a id="participants_count" class="totalNum_s"><ikep4j:message pre="${preLabel}" key="total" /> 0 <ikep4j:message pre="${preLabel}" key="no" /></a>
						</div>	
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre="${preLabel}" key="referers" /></th>
					<td colspan="2">
						<input id="refererInput" name="" title="<ikep4j:message pre="${preLabel}" key="referers" />" class="inputbox" type="text" size="20"/>
						<a id="btn_referer_searchUser" class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_search.gif"/>" alt="" />Search</span></a>
						<a id="btn_referer_showAddress" class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" alt="" />Address</span></a>
						<span class="tdInstruction"><ikep4j:message pre="${preLabel}" key="referersDesc" /></span>
						<div class="mt5">
							<select id="referers" multiple="multiple" size="5" style="height:auto;" class="w80" title="<ikep4j:message pre="${preLabel}" key="referers" />">
							</select>
							<a id="btn_delete_referer" class="button_ic valign_bottom" href="#a" style="vertical-align:bottom"><span><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="" />Delete</span></a> 
							<a id="referers_count" class="totalNum_s"><ikep4j:message pre="${preLabel}" key="total" /> 0 <ikep4j:message pre="${preLabel}" key="no" /></a>
						</div>	
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre="${preLabel}" key="place" /></th>
					<td>
						<input type="hidden" id="place" name="place" value="${schedule.place}"/>
						${schedule.place}
					</td>
				</tr>
				<tr>
					<th scope="row">자원</th>
					<td>
						<input type="hidden" id="tool" name="tool" value="${schedule.cartooletcId}"/>
						<input type="hidden" id="toolName" name="toolName" value="${schedule.cartooletcName}"/>
						${schedule.cartooletcName}
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre="${preLabel}" key="contents" /></th>
					<td>
						<div><textarea id="contents" name="contents" class="w100" title="<ikep4j:message pre="${preLabel}" key="input" />" cols="" rows="20"></textarea></div>
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre="${preLabel}" key="workspace" /></th>
					<td>
						<select id="team-sel" title="<ikep4j:message pre="${preLabel}" key="workspace" />" name="" class="inputbox">
							<option value=""><ikep4j:message pre="${preLabel}" key="workspaceOptionTitle" /></option>
						</select>&nbsp;	
						<span class="tdInstruction"><ikep4j:message pre="${preLabel}" key="workspaceDesc" /></span>						
					</td>
				</tr>
			</tbody>
		</table>

		<div class="fileAttachArea">
			<div class="wrap">
				<div class="th" style="width:18%;">
					<div class="title"><ikep4j:message pre="${preLabel}" key="attachFiles" /></div>
				</div>
				<div class="td" style="margin-left:18%;">
					<div class="content">
						<div id="fileUploadArea"></div>
					</div>
				</div>
			</div>
		</div>

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

<script id="recurrenceTemplate" type="text/x-jquery-tmpl"> 
	<ikep4j:message pre="${preLabel}" key="every" />
	<input id="count" name="count" title="" class="inputbox" type="text" size="2" value="\${$item.count}"  />&nbsp;\${freqStr}
	<input id="endRepeatDate" type="text" class="inputbox" name="sdate" 
		title="<ikep4j:message pre="${preLabel}" key="date" />" value="" size="10" />&nbsp;<ikep4j:message pre="${preLabel}" key="until" />
	<input id="forever" \${$item.isForever()} name="forever" class="checkbox" 
		title="<ikep4j:message pre="${preLabel}" key="forever" />" type="checkbox"/><ikep4j:message pre="${preLabel}" key="forever" />			

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

<script id="alarmTemplate" type="text/x-jquery-tmpl">
	<div class="alarm-item schedule_bg">
		<select title="<ikep4j:message pre="${preLabel}" key="before" />" class="inputbox">
			<option value="0"><ikep4j:message pre="${preLabel}" key="nouse" /></option>
			<option value="30"><ikep4j:message pre="${preLabel}" key="before30min" /></option>
			<option value="60"><ikep4j:message pre="${preLabel}" key="before1hr" /></option>
			<option value="120"><ikep4j:message pre="${preLabel}" key="before2hr" /></option>
			<option value="1440"><ikep4j:message pre="${preLabel}" key="before1day" /></option>
		</select>&nbsp;
		<label><input name="alarmCheck-\${idx}" class="checkbox" 
			title="<ikep4j:message pre="${preLabel}" key="mail" />" type="radio" 
			value="0" /><ikep4j:message pre="${preLabel}" key="mail" />&nbsp;	</label>				
		<label><input name="alarmCheck-\${idx}" class="checkbox" 
			title="<ikep4j:message pre="${preLabel}" key="sms" />" type="radio" 
			value="1" /><ikep4j:message pre="${preLabel}" key="sms" />&nbsp;&nbsp;&nbsp;</label>	
<%--		<label><input name="alarmCheck-\${idx}" class="checkbox" 
			title="<ikep4j:message pre="${preLabel}" key="note" />" type="radio" 
			value="2" /><ikep4j:message pre="${preLabel}" key="note" />&nbsp;&nbsp;&nbsp;</label>--%>
		<a href="#a" class="valign_middle alarm-plus"><span><img src="<c:url value="/base/images/icon/ic_btn_plus.gif"/>" alt="" /></span></a>
		<a href="#a" class="valign_middle alarm-minus"><span><img src="<c:url value="/base/images/icon/ic_btn_minus.gif"/>" alt="" /></span></a>
	</div>
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