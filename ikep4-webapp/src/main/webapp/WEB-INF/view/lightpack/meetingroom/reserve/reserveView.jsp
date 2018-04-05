<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/fileUploadControll.jsp"%>
<%--메시지 관련 Prefix 선언 Start--%> 
<c:set var="preView"  value="ui.lightpack.planner.view.schedule" /> 
<c:set var="preLabel"    value="ui.lightpack.planner.common.label" />
<c:set var="preButton"  value="ui.lightpack.planner.common.button" /> 
 <%--메시지 관련 Prefix 선언 End--%>
 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="isDialog" value="${param.dialog}"/>

<script type="text/javascript">
<!--
var home_url = iKEP.getContextRoot() + "/";	
var scheduleInfo = ${scheduleInfo};

var planner = {
		userId: '${user.userId}',
		scheduleId: '${param.scheduleId}',
		start: '${param.start}',
		end: '${param.end}',
		userLocale: '${user.localeCode}',
		portalLocale:'${portal.defaultLocaleCode}'
};

goEdit = function(scheduleId, meetingRoomId) {
	
	var url = '<c:url value="/lightpack/meetingroom/reserve/reserveForm.do"/>';
	
	$jq.ajax({     
		url : url,    
		data : {
			
			scheduleId : scheduleId,
			meetingRoomId : meetingRoomId
		},     
		type : "post",     
		loadingElement : {
			
			container : "#tabs"
		},
		success : function(result) {       
			
			$jq("#tabs").html(result);
		},
		error : function(event, request, settings) { 
			
			alert("error"); 
		}
	});  
	
};

cancel = function() {		
	
	var meetingRoomId = $jq("#meetingRoomId").val();	
	var subContext ="${param.where}"; //$jq("#listType").val();
	var url = home_url + "lightpack/meetingroom/" + subContext + "/meetingRoomMain.do";
	
	$jq.ajax({     
		
		url : url,    
		data :  {
			
			meetingRoomId : meetingRoomId,
			buildingId : $jq("building").val(),
			floorId : $jq("floor").val()
		},     
		type : "post",     
		loadingElement : {
			
			container : "#tabs"
		},
		success : function(result) {
			
			$jq("#tabs").html(result);
		},
		error : function(event, request, settings) { 
			
			alert("error"); 
		}
	});
}
//-->
</script>

<c:if test="${param.dialog != 1 }">
<!--popup Start-->
<div id="popup">
	<!--popup_title Start-->
	<div id="popup_title_2">
        <div class="popup_bgTitle_l"></div>
		<h1><ikep4j:message pre="${preView}" key="title" /></h1>
		<a href="#a" class="btn_close"><span><ikep4j:message pre="${preButton}" key="close" /></span></a>
	</div>
	<!--//popup_title End-->
	
	<!--popup_contents Start-->
	<div id="popup_contents">
</c:if>

		<div>
			<span class="colorBlue">*</span><span class="colorBlue" id="userName"></span><ikep4j:message pre="${preView}" key="registerSuffix" />
		</div>
		<!--blockDetail Start-->		
		<div class="blockDetail">
			<table summary="<ikep4j:message pre="${preView}" key="summary" />">
				<caption></caption>
				<tbody>
					<tr>
						<th width="18%" scope="row"><ikep4j:message pre="${preLabel}" key="period" /></th>
						<td width="82%">
							<div class="schedule_bg00">
								<span id="srd"></span>&nbsp;
								<span id="srt"></span>~
								<span id="erd"></span>&nbsp;
								<span id="ert"></span>&nbsp;&nbsp;
								<span id="repeat"></span>&nbsp;&nbsp;
								<span id="allDay"></span>&nbsp;&nbsp;
								<span id="public"></span>								
							</div>
							<div class="schedule_bg02" id="repeatDesc">
							</div>						
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${preLabel}" key="title" /></th>
						<td>
							<span class="colorPoint" id="category"></span>&nbsp;
							<span id="title"></span>							
						</td>
					</tr>
					<tr id="alarm">
						<th scope="row"><ikep4j:message pre="${preLabel}" key="alarm" /></th>
						<td>						
						</td>
					</tr>
					<tr id="participants_view_tr">
						<th scope="row"><ikep4j:message pre="${preLabel}" key="participants" /></th>
						<td colspan="2">
							<ul class="listStyle" id="participants"></ul>
						</td>
					</tr>
					<tr id="referers_view_tr">
						<th scope="row"><ikep4j:message pre="${preLabel}" key="referers" /></th>
						<td colspan="2">
							<ul class="listStyle" id="referers"></ul>		
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${preLabel}" key="place" /></th>
						<td>
							<span id="place"></span>						
						</td>
					</tr>
					<tr id="carToolDiv">
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
		<!--//blockDetail End-->
		<!--blockButton Start-->

		<div class="blockButton"> 
			<ul>
				<c:if test="${isUpdate == true && param.dialog == 1 && registerId == user.userId}">
<%-- 					<c:if test="${approve.approveStatus == 'W'}"> --%>
					<li><a class="button btn_update" href="#a"><span><ikep4j:message pre="${preButton}" key="update" /></span></a></li>
<%-- 					</c:if> --%>
					<li><a class="button btn_delete" href="#a"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
				</c:if>
				<c:if test="${param.dialog == 1 }">
				<li><a class="button btn_back" href="#a"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a></li>
				</c:if>
				<li><a class="button btn_sendmail" href="#a"><span><ikep4j:message pre="${preButton}" key="sendmail" /></span></a></li>
			</ul>
		</div>
		
		<!--//blockButton End-->	
<c:if test="${param.dialog != 1 }">
	</div>
</div>	
</c:if>

<input type="hidden" id="meetingRoomId" name="meetingRoomId" />

<div id="attendance-dialog" class="none">
	<div><textarea style="width:97%; height:80px;"></textarea></div>
	<div class="blockButton" style="margin-top:7px;">
		<ul>
			<li><a href="#a" class="button btn_ok"><span><ikep4j:message pre="${preButton}" key="ok" /></span></a></li>
			<li><a href="#a" class="button btn_cancel"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a></li>
		</ul>
	</div>
</div>

<c:if test="${isUpdate == true}">	<%-- 삭제 조건 확인 --%>
<div id="delete-dialog" title="Dialog Title" class="none">
	<c:set var="preDialog"  value="ui.lightpack.planner.dialog.repeat" />	
	<p><ikep4j:message pre="${preDialog}" key="deleteTitle" /></p>
	<ul>
		<li>
			<label>
				<input name="deleteOption" value="1" type="radio"/>
				<ikep4j:message pre="${preDialog}" key="deleteThis" />
			</label>
		</li>
		<li>
			<label>
				<input name="deleteOption" value="2" type="radio"/>
				<ikep4j:message pre="${preDialog}" key="deleteAfter" />
			</label>
		</li>
		<li>
			<label>
				<input name="deleteOption" value="3" type="radio"/>
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
</c:if>

<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/fullcalendar-date-utils.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.postjson.js"/>"></script>

<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/meetingroom/ikep4ViewReserve.js"/>"></script>
<script type="text/javascript">

	var parameters = iKEP.getPopupArgument();
	
	// 위임자 목록
	var mandators = [];<c:forEach var="user" items="${mandators}">
	mandators.push("${user.mandatorId}");</c:forEach>
	
	var scheduleId = planner.scheduleId;
	var userId = planner.userId;
	
	var scheduleStartDate = (parameters && parameters["startDate"]) || null;
	var scheduleEndDate = (parameters && parameters["endDate"]) || null;

	(function($){
		$(document.body).ready(function() {
			<c:if test="${isUpdate == true}">
			$("a.btn_update").click(function() {
				<c:if test="${approve.approveStatus == 'A' && meetingRoom.autoApprove == 'N'}">
					if(!confirm("<ikep4j:message key="ui.lightpack.meetingroom.message.updateApproveRetry" />")) return false;
				</c:if>
				var meetingRoomId = $jq("#meetingRoomId").val();
				
				goEdit(scheduleId, meetingRoomId);
			});
			
			$("a.btn_delete").click(deleteSchedule);
			</c:if>
			
			$("a.btn_back").click(function() {
				
				cancel();
			});
		});
	})(jQuery);
</script>