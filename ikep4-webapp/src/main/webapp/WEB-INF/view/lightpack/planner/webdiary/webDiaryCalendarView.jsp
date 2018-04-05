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

<script type="text/javascript">
   var sId = '${scheduleId}';
   var sWorkspaceId = "${workspaceId}";
</script>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.json-2.2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.postjson.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/jquery.qtip-1.0.0-rc3.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/webdiary/fullcalendar-1.5-cust.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/webdiary/plannerCommon.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/webdiary/checkSchedule.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/webdiary/ikep4cal.eventMgmt.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/webdiary/ikep4Calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/webdiary/ikep4calView.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/event_class.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/meetingroom/config.js"/>"></script>
<!--  
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/webdiary/menu.js"/>"></script>
-->
<!-- Modal window Start -->
<!-- 사용시class="none"으로 설정 -->
<div id="mgmt-panel" title=""></div>

<div id="calendar"></div>
<div style="height:10px;"></div>

<script id="mgmtpanelTemplate" type="text/x-jquery-tmpl">
	<div class="blockButton"> 
		<ul>
			<li class="btn_save"><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="create" /></span></a></li>
			<li class="btn_delete"><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
			<!--
			<li class="btn_cancel"><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a></li>
			-->
		</ul>
	</div>	
	<div class="blockDetail" id="event-form">
	<form id="fileUploadForm">
<input type="hidden" id="everyoneSchedule" name="everyoneSchedule" value="0"/>
<input type="hidden" id="approveResult" name="approveResult" value="0"/>
<input id="everyoneScheduleYn" type="hidden" title=""/>
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
										<option value="00">00</option>
										<option value="01">01</option>
										<option value="02">02</option>
										<option value="03">03</option>
										<option value="04">04</option>
										<option value="05">05</option>
										<option value="06">06</option>
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
									</select> 분	~
						<br/>
						<input id="end-date-picker" type="text" class="inputbox date-pick" name="edate" title="<ikep4j:message pre="${preLabel}" key="endDate" />" size="10" />&nbsp;
						<select id="endDueTimeHour">
										<option value="00">00</option>
										<option value="01">01</option>
										<option value="02">02</option>
										<option value="03">03</option>
										<option value="04">04</option>
										<option value="05">05</option>
										<option value="06">06</option>
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
									</select> 분
						</select>&nbsp;
						<br/>
						<label><input id="repeat" name="repeat" class="checkbox" title="<ikep4j:message pre="${preLabel}" key="repeat" />" type="checkbox" value="\${repeat}" /><ikep4j:message pre="${preLabel}" key="repeat" />&nbsp;</label>
						<label><input id="wholeday" name="wholeday" class="checkbox" title="<ikep4j:message pre="${preLabel}" key="allDay" />" type="checkbox" value="\${wholeday}" /><ikep4j:message pre="${preLabel}" key="allDay" />&nbsp;</label>
						<label id="schedulePublic_label"><input id="schedulePublic" name="schedulePublic" class="checkbox" title="<ikep4j:message pre="${preLabel}" key="nopublic" />" type="checkbox" value="\${schedulePublic}" /><ikep4j:message pre="${preLabel}" key="nopublic" />&nbsp;</label>
						<label id="schedulePrivate_label"><input id="schedulePrivate" name="schedulePrivate" class="checkbox" title="숨김" type="checkbox" value="\${schedulePrivate}" />숨김&nbsp;</label>
						<label id="updateDisplay_label"><input id="updateDisplay" name="updateDisplay" class="checkbox" title="<ikep4j:message pre="${preLabel}" key="updateDisplay" />" type="checkbox" value="\${updateDisplay}" /><ikep4j:message pre="${preLabel}" key="updateDisplay" />&nbsp;</label>
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
							<span id="categoryView">
							범주 : <select id="category" class="inputbox">
							</select>&nbsp;
							</span>
							<select id="companyArea" class="inputbox">
							</select>&nbsp;
							<input name="title" id="title" title="<ikep4j:message pre="${preLabel}" key="title" />" class="inputbox" style="width:60%" type="text" value="\${title}"/>
						</div>
					</td>
				</tr>
				<tr id="alarm_tr">
					<th scope="row"><ikep4j:message pre="${preLabel}" key="alarm" /></th>
					<td>
						<div id="alarm-div"></div>
					</td>
				</tr>
				<tr id="mail_send_yn">
					<th scope="row">메일발송 여부</th>
					<td>
						<input name="mailSendYn" id="mailSendYn" type="checkbox" class="checkbox" /> <font color="gray">※ 체크시 참여자와 참조자에게 메일을 발송합니다.</font>
					</td>
				</tr>
				<tr id="participants_tr">
					<th scope="row"><ikep4j:message pre="${preLabel}" key="participants" /></th>
					<td colspan="2">
						<input id="participantInput" name="" title="<ikep4j:message pre="${preLabel}" key="participants" />" class="inputbox" type="text" size="12"/>
						<a id="btn_parti_searchUser" class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_search.gif"/>" alt="" />Search</span></a>
						<a id="btn_parti_showAddress" class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" alt="" />Address</span></a>
						<a id="btn_parti_schedule" class="button_ic" href="#a"><span><img src="<c:url value="/base/images/icon/ic_btn_schedule.gif"/>" alt="" />Schedule</span></a>&nbsp;
						<br/>
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
				<tr id="referers_tr">
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
       					<input id="place" title="<ikep4j:message pre="${preLabel}" key="place" />" class="inputbox" style="width:230px;" type="text"/>
						<input id="meetingRoomId" type="hidden" title="<ikep4j:message pre="${preLabel}" key="meetingRoom" />"/>
						<a id="btnMeetingRoomReserve" href="#a" class="button_ic">
							<span><ikep4j:message pre="${preButton}" key="meetingRoomReserve" /></span>
						</a>
						<a id="btnMeetingRoomCancel" href="#a" class="button_ic" style="display:none;">
							<span><ikep4j:message pre="${preButton}" key="meetingRoomCancel" /></span>
						</a>
					</td>
				</tr>
				<tr id="toolDiv" style="display:none;">
					<th scope="row">자원</th>
					<td>
       					<input id="tool" title="" class="inputbox" style="width:400px;" type="text"/>
						<input id="cartooletcId" type="hidden" title=""/>
						<a id="btnToolReserve" href="#a" class="button_ic">
							<span>자원예약</span>
						</a>
						<a id="btnToolCancel" href="#a" class="button_ic" style="display:none;">
							<span>자원예약취소</span>
						</a>
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre="${preLabel}" key="contents" /></th>
					<td>
						<div><textarea id="contents" name="contents" class="w100" title="<ikep4j:message pre="${preLabel}" key="input" />" cols="" rows="20"></textarea></div>
					</td>
				</tr>
				<!--
				<tr>
					<th scope="row"><ikep4j:message pre="${preLabel}" key="attachFiles" /></th>
					<td>
						
					</td>
				</tr>
				-->
				<tr id="workspace_tr">
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
			<li class="btn_save"><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="create" /></span></a></li>
			<li class="btn_delete"><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
			<!--<li class="btn_cancel"><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a></li>-->
		</ul>
	</div>	
</script>

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
			value="1" /><ikep4j:message pre="${preLabel}" key="sms" />&nbsp;</label>
		<label><input name="alarmCheck-\${idx}" class="checkbox" 
			title="<ikep4j:message pre="${preLabel}" key="note" />" type="radio" 
			value="3" />Push&nbsp;&nbsp;&nbsp;</label>
		<a href="#a" class="valign_middle alarm-plus"><span><img src="<c:url value="/base/images/icon/ic_btn_plus.gif"/>" alt="" /></span></a>
		<a href="#a" class="valign_middle alarm-minus"><span><img src="<c:url value="/base/images/icon/ic_btn_minus.gif"/>" alt="" /></span></a>
	</div>
</script>

<script id="viewEventTemplate" type="text/x-jquery-tmpl">
		<div>
			<span class="colorBlue">*</span><span class="colorBlue" id="userName"></span><ikep4j:message pre="${preView}" key="registerSuffix" />
		</div>
		<div class="blockDetail">
			<table summary="<ikep4j:message pre="${preView}" key="summary" />">
				<caption></caption>
				<tbody>
					<tr>
						<th width="18%" scope="row"><ikep4j:message pre="${preLabel}" key="period" /></th>
						<td width="82%">
							<div class="schedule_bg00">
								<span id="srd"></span>
								<span id="srt"></span>~
								<span id="erd"></span>
								<span id="ert"></span>&nbsp;
								<span id="repeat"></span>&nbsp;
								<span id="allDay"></span>&nbsp;
								<span id="public"></span>&nbsp;
								<span id="private"></span>									
							</div>
							<div class="schedule_bg02" id="repeatDesc">
							</div>						
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${preLabel}" key="title" /></th>
						<td>
							<span class="colorPoint" id="category"></span><span id="category_space">&nbsp;</span>
							<span id="title"></span>							
						</td>
					</tr>
					<tr id="alarm">
						<th scope="row"><ikep4j:message pre="${preLabel}" key="alarm" /></th>
						<td>						
						</td>
					</tr>
					<tr id="isAccept" class="none">
						<th scope="row"><ikep4j:message pre="${preLabel}" key="checkAttendance" /></th>
						<td>
							<a class="button_s accept" href="#a"><span><ikep4j:message pre="${preLabel}" key="attendance" /></span></a>
							<a class="button_s reject" href="#a"><span><ikep4j:message pre="${preLabel}" key="noattendance" /></span></a>&nbsp;&nbsp;
							<ikep4j:message pre="${preLabel}" key="noabsenceReason" />&nbsp;<input id="absence_reason" title="<ikep4j:message pre="${preLabel}" key="noabsenceReason" />" class="inputbox w80" type="text"/>				
						</td>
					</tr>
					<tr id="participants_view_tr">
						<th scope="row"><ikep4j:message pre="${preLabel}" key="participants" /></th>
						<td colspan="2">
							<ul class="listStyle" id="participants">
							</ul>
						</td>
					</tr>
					<tr id="referers_view_tr">
						<th scope="row"><ikep4j:message pre="${preLabel}" key="referers" /></th>
						<td colspan="2">
							<ul class="listStyle" id="referers">
							</ul>		
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${preLabel}" key="place" /></th>
						<td>
							<span id="place"></span>						
						</td>
					</tr>
					<tr id="carToolDiv" style="display:none;">
						<th scope="row">자원</th>
						<td>
							<span id="tool"></span>						
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${preLabel}" key="contents" /></th>
						<td>
							<span id="contents"></span>							
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${preLabel}" key="attachFiles" /></th>
						<td>
							<ul class="listStyle" id="attachFiles"></ul>				
						</td>
					</tr>
					<tr id="workspace_view_tr">
						<th scope="row"><ikep4j:message pre="${preLabel}" key="workspace" /></th>
						<td>
							<span id="workspace"></span>							
						</td>
					</tr>
				</tbody>
			</table>
		</div>			
		<div class="blockButton"> 
			<ul>
				<c:if test="${isUpdate == true}">
				<li><a class="button btn_update" href="#a"><span><ikep4j:message pre="${preButton}" key="update" /></span></a></li>
				<li><a class="button btn_remove" href="#a"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
				</c:if>
				<li><a class="button btn_sendmail" href="#a"><span><ikep4j:message pre="${preButton}" key="sendmail" /></span></a></li>
				<!--<li><a class="button btn_close" href="#a"><span><ikep4j:message pre="${preButton}" key="close" /></span></a></li>-->
				
				<li id="strike_button"><a class="button btn_strike" href="#a"><span>삭제표시</span></a></li>
			</ul>
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