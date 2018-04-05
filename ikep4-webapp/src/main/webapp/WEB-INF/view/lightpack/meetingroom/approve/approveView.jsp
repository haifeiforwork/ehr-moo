<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/fileUploadControll.jsp"%>
<%--메시지 관련 Prefix 선언 Start--%> 
<c:set var="preView"  value="ui.lightpack.planner.view.schedule" /> 
<c:set var="preLabel"    value="ui.lightpack.planner.common.label" />
<c:set var="preButton"  value="ui.lightpack.planner.common.button" /> 

<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="prevButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
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

list = function() {		
	
	var url = "<c:url value='approveList.do' />";

	$jq("#searchForm").attr("action", url);
	$jq("#searchForm")[0].submit();
}

approve = function(scheduleId) {
	
	if(confirm("<ikep4j:message pre='${preMessage}' key='assignConfirm' />")) {
		
		$jq("#scheduleId").val(scheduleId);
		
		$jq("#searchForm").attr("action", "<c:url value='approveUse.do' />");
		$jq("#searchForm")[0].submit();
	}
};

rejectForm = function(scheduleId) {
	
	$jq("#scheduleId").val(scheduleId);
	
	$jq("#reject-dialog").dialog({
		
		modal:true,
		resizable : false,
		title : "<ikep4j:message pre='${preDetail}' key='rejectReason' />",
		open : function() { $jq("textarea", this).val(""); },
		close: function(e, ui) {
			
			$jq("#btn_reject_cancel").unbind("click");
			$jq("#btn_reject_ok").unbind("click");
		}
	});
}

reject = function() {
	
	var contents = $jq("#contents").val();
	
	$jq("#rejectReason").val(contents);
	
	$jq("#searchForm").attr("action", "<c:url value='rejectUse.do' />");
	$jq("#searchForm")[0].submit();
}
//-->
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
<input type="hidden" id="scheduleId" name="scheduleId"/>
<input type="hidden" id="rejectReason" name="rejectReason"/>
<input type="hidden" id="roomOrTool" name="roomOrTool" value="room"/>
</form>

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
					<tr>
						<th scope="row"><ikep4j:message pre="${preLabel}" key="participants" /></th>
						<td colspan="2">
							<ul class="listStyle" id="participants"></ul>
						</td>
					</tr>
					<tr>
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
					<tr>
						<th scope="row"><ikep4j:message pre="${preLabel}" key="workspace" /></th>
						<td>
							<span id="workspace"></span>							
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre='${preDetail}' key='reserveStatus' /></th>
						<td>
							<span id="approveStatus">
								<c:choose>
								<c:when test="${approve.approveStatus == 'A'}">
								<ikep4j:message pre='${preDetail}' key='approval' />
								</c:when>
								<c:when test="${approve.approveStatus == 'W'}">
								<ikep4j:message pre='${preDetail}' key='wait' />
								</c:when>
								<c:when test="${approve.approveStatus == 'R'}">
								<ikep4j:message pre='${preDetail}' key='reject' />
								</c:when>
								<c:otherwise>
								</c:otherwise>
								</c:choose>
							</span>							
						</td>
					</tr>
					<c:if test="${approve.approveStatus == 'R'}">
					<tr>
						<th scope="row"><ikep4j:message pre='${preDetail}' key='rejectReason' /></th>
						<td>
							<span id="rejectReason">${approve.rejectReason}</span>							
						</td>
					</tr>
					</c:if>
				</tbody>
			</table>
		</div>			
		<!--//blockDetail End-->
		<!--blockButton Start-->

		<div class="blockButton"> 
			<ul>
				<c:if test="${approve.approveStatus == 'W'}">
					<li><a class="button btn_approve" href="#a"><span><ikep4j:message pre='${prevButton}' key='approval' /></span></a></li>
					<li><a class="button btn_reject" href="#a"><span><ikep4j:message pre='${prevButton}' key='reject' /></span></a></li>
				</c:if>
				
				<li><a class="button btn_list" href="#a"><span><ikep4j:message pre="${preButton}" key="list" /></span></a></li>
				<c:if test="${user.userId == registerId}">
				<li><a class="button btn_sendmail" href="#a"><span><ikep4j:message pre="${preButton}" key="sendmail" /></span></a></li>
				</c:if>
			</ul>
		</div>
		
		<div id="reject-dialog" title="Dialog Title" class="none">
		<p><ikep4j:message pre='${preMessage}' key='rejectReason' /></p>
		<div>
			<table style="width:274px;">
				<tr>
					<td>
						<textarea id="contents" name="contents" class="w100" title="<ikep4j:message pre='${preDetail}' key='rejectReason' />" cols="" rows="5"></textarea>
					</td>
				</tr>
			</table>
		</div>
		<div style="height:10px;"></div>
		<div class="blockButton"> 
			<ul>
				<li><a id="btn_reject_ok" href="#a" class="button"><span><ikep4j:message pre='${prevButton}' key='reject' /></span></a></li>
				<li><a id="btn_reject_cancel" href="#a" class="button"><span><ikep4j:message pre='${prevButton}' key='cancel' /></span></a></li>
			</ul>
		</div>
	</div>

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
			<c:if test="${approve.approveStatus == 'W'}">
			$("a.btn_approve").click(function() {
				
				approve(scheduleInfo.scheduleId);
			});
			
			$("a.btn_reject").click(function() {
				
				rejectForm(scheduleInfo.scheduleId);
			});
			
			$("#btn_reject_ok").click(function() {
				
				reject();
			});
			
			$("#btn_reject_cancel").click(function() {
				
				$jq("#reject-dialog").dialog("close");
			});
			</c:if>
			
			$("a.btn_list").click(function() {
				
				list();
			});
		});
	})(jQuery);
</script>