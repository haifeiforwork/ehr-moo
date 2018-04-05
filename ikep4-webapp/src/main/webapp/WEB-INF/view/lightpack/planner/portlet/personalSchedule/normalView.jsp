<%@ page contentType="text/html; charset=utf-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/fullcalendar-date-utils.js"/>"></script>

<div class="po_schedule" id="${portletConfigId}">
	<div class="boxDiv">
		<select id="personalScheduleDate" name="personalScheduleDate" onchange="javascript: selectChange();" style="width:100px;">
			<option value="${dateValue1}" <c:if test="${personalScheduleDate == dateValue1}">selected</c:if> <c:if test="${dateWeekDay1 == 'Sat.'}">style="color: blue;"</c:if> <c:if test="${dateWeekDay1 == 'Sun.'}">style="color: red;"</c:if>>${dateText1} (${dateWeekDay1})</option>
			<option value="${dateValue2}" <c:if test="${personalScheduleDate == dateValue2}">selected</c:if> <c:if test="${dateWeekDay2 == 'Sat.'}">style="color: blue;"</c:if> <c:if test="${dateWeekDay2 == 'Sun.'}">style="color: red;"</c:if>>${dateText2} (${dateWeekDay2})</option>
			<option value="${dateValue3}" <c:if test="${personalScheduleDate == dateValue3}">selected</c:if> <c:if test="${dateWeekDay3 == 'Sat.'}">style="color: blue;"</c:if> <c:if test="${dateWeekDay3 == 'Sun.'}">style="color: red;"</c:if>>${dateText3} (${dateWeekDay3})</option>
			<option value="${dateValue4}" <c:if test="${personalScheduleDate == dateValue4}">selected</c:if> <c:if test="${dateWeekDay4 == 'Sat.'}">style="color: blue;"</c:if> <c:if test="${dateWeekDay4 == 'Sun.'}">style="color: red;"</c:if>>${dateText4} (${dateWeekDay4})</option>
			<option value="${dateValue5}" <c:if test="${personalScheduleDate == dateValue5}">selected</c:if> <c:if test="${dateWeekDay5 == 'Sat.'}">style="color: blue;"</c:if> <c:if test="${dateWeekDay5 == 'Sun.'}">style="color: red;"</c:if>>${dateText5} (${dateWeekDay5})</option>
			<option value="${dateValue6}" <c:if test="${personalScheduleDate == dateValue6}">selected</c:if> <c:if test="${dateWeekDay6 == 'Sat.'}">style="color: blue;"</c:if> <c:if test="${dateWeekDay6 == 'Sun.'}">style="color: red;"</c:if>>${dateText6} (${dateWeekDay6})</option>
			<option value="${dateValue7}" <c:if test="${personalScheduleDate == dateValue7}">selected</c:if> <c:if test="${dateWeekDay7 == 'Sat.'}">style="color: blue;"</c:if> <c:if test="${dateWeekDay7 == 'Sun.'}">style="color: red;"</c:if>>${dateText7} (${dateWeekDay7})</option>
		</select>&nbsp;&nbsp;
		<select id="personalUserId" name="personalUserId" onchange="javascript: selectChange();" style="width:150px;">
			<option value="-본인-">------- 본 인 -------</option>
			<option value="${user.userId}^M" <c:if test="${user.userId == selectPersonalUserId && 'M' == selectPersonalUserType}">selected</c:if>>${user.userName}</option>
			<option value="-favorite-">------ Favorite ------</option>
			<c:forEach var="favorite" items="${searchResultForPeople.entity}">
				<option value="${favorite.targetId}^F" <c:if test="${favorite.targetId == selectPersonalUserId && 'F' == selectPersonalUserType}">selected</c:if>>${favorite.title}/${favorite.jobTitleName}/${favorite.teamName}</option>
			</c:forEach>
			<option value="-팀원-">------- 팀 원 -------</option>
			<c:forEach var="teamUser" items="${teamUserList}">
				<option value="${teamUser.userId}^T" <c:if test="${teamUser.userId == selectPersonalUserId && 'T' == selectPersonalUserType}">selected</c:if>>${teamUser.userName}/${teamUser.jobTitleName}/${teamUser.teamName}</option>
			</c:forEach>
		</select>
	</div>
	
	<div class="pr_schedule_c">							
			<ul>			
			</ul>
	</div>

	<div class="clear"></div>				
</div>		

<script type="text/javascript">
var dailyOptions = {
		userId: '${selectPersonalUserId}',
		portletConfigId: '${portletConfigId}',
		personalScheduleDate: '${personalScheduleDate}',
		loginUserId:'${user.userId}'
};

function selectChange(){
	var div = $jq("#${portletConfigId}");
	var personalScheduleDate = $jq("#personalScheduleDate").val();
	var personalUserId = $jq("#personalUserId").val();
	
	if(personalUserId != "-favorite-" && personalUserId != "-팀원-" && personalUserId != "-본인-") {
		$jq.ajax({
			url : '<c:url value="/lightpack/planner/portlet/personalSchedule/normalView.do"/>',
			data : {portletConfigId:'${portletConfigId}', personalScheduleDate:personalScheduleDate, personalUserId:personalUserId},
			type : "get",
			loadingElement : {container : div.parent()},
			success : function(result) {
				div.parent().html(result);
			},
			error : function() {
				div.parent().html("");
			}
		});
	}
}
</script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/portlet/personalSchedule.js"/>"></script>