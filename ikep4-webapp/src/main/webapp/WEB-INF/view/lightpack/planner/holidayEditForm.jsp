<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%--메시지 관련 Prefix 선언 Start--%> 
<c:set var="preMessage"  value="ui.lightpack.planner.common.message" /> 
<c:set var="preHoliday"    value="ui.lightpack.planner.holiday" />
<c:set var="preButton"  value="ui.lightpack.planner.common.button" /> 
 <%--메시지 관련 Prefix 선언 End--%>
 
	<h1 class="none"><ikep4j:message pre="${preMessage}" key="contents" /></h1>

	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre="${preHoliday}" key="mgmt" /></h2>
	</div>
	<!--//pageTitle End-->	
	
	<!--blockDetail Start-->
	<div class="blockDetail">
	  <form id="mgmtForm" action="">
		<input id="holidayId" name="holidayId" type="hidden" value="${holiday.holidayId}" />
		
		<table summary="<ikep4j:message pre="${preHoliday}" key="insert" />">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="18%"><ikep4j:message pre="${preHoliday}" key="nationName" /></th>
					<td width="82%">
					<div>
						<select name="nation" title="<ikep4j:message pre="${preHoliday}" key="nationName" />">
								<option value="">select nation</option>
							<c:forEach var="item" items="${nations}" varStatus="status">
								<c:if test="${item.nationCode == holiday.nation }">
									<option value="${item.nationCode}" selected>${item.nationName}</option>	
								</c:if>
								<c:if test="${item.nationCode != holiday.nation }">
									<option value="${item.nationCode}">${item.nationName}</option>	
								</c:if>															
							</c:forEach>
						</select>
					</div>	
					</td>
				</tr>
				<tr>
					<th scope="row" width="18%"><ikep4j:message pre="${preHoliday}" key="name" /></th>
					<td width="82%">
					<div>
						<input name="holidayName" id="holidayName" title="<ikep4j:message pre="${preHoliday}" key="name" />" class="inputbox w100" type="text" value="${holiday.holidayName}" />
					</div>
					</td>
				</tr>				
				<tr>
					<th scope="row"><ikep4j:message pre="${preHoliday}" key="isYearlyRepeat" /></th>
					<td>
						<input name="yearRepeat" title="<ikep4j:message pre="${preHoliday}" key="yearlyRepeatYes" />" type="radio" class="radio" value="0"
							<c:if test="${holiday.yearRepeat == 0}" >
								checked="checked"
							</c:if> /><ikep4j:message pre="${preHoliday}" key="yearlyRepeatYes" />&nbsp;
						<input name="yearRepeat" title="<ikep4j:message pre="${preHoliday}" key="yearlyRepeatNo" />" type="radio" class="radio" value="1" 
							<c:if test="${holiday.yearRepeat == 1}" >
								checked="checked"
							</c:if> /><ikep4j:message pre="${preHoliday}" key="yearlyRepeatNo" />
					</td>
				</tr>							
				<tr>
					<th scope="row"  width="18%"><ikep4j:message pre="${preHoliday}" key="date" /></th>
					<td>
					<div>
						<input name="holidayDateStr" title="<ikep4j:message pre="${preHoliday}" key="date" />" id="holidayDate" type="text" size="10" readonly="readonly" class="inputbox"
							value='<fmt:formatDate value="${holiday.holidayDate}" pattern="yyyy.MM.dd"/>' />
					</div>				
					</td>
				</tr>																						
			</tbody>
		</table>
	  </form>
	</div>
	<!--//blockDetail End-->
		
	<!--tableBottom Start-->
	<div class="tableBottom">										
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a class="button" href="#a" id="btn_insert"><span><ikep4j:message pre="${preButton}" key="create" /></span></a></li>
				<li><a class="button" href="#a" id="btn_list"><span><ikep4j:message pre="${preButton}" key="list" /></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
	<!--//tableBottom End-->		
										
			<!--//mainContents End-->
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/plannerCommon.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/holiday_edit.js"/>"></script>
<script type="text/javascript">
$jq(document).ready(function() {
	var validOptions = {
		rules: {
			holidayName: {
				required: true,
				maxlength: 200
			},
			nation: {
				required : true
			},
			holidayDateStr: {required: true}
		},
		messages: {
			holidayName: {				
	            direction : "top",
	            required : '<ikep4j:message key="NotEmpty.holiday.holidayName" />',
				maxlength : "<ikep4j:message key='Max.holiday.holidayName' arguments='200'/>"
			},
			nation: {
				direction : "top",
	            required : '<ikep4j:message key="NotEmpty.holiday.nation" />'
			},
			holidayDateStr: {
				direction : "bottom",
	            required : '<ikep4j:message key="NotEmpty.holiday.holidayDate" />'
			}
		}
	};
	new iKEP.Validator("#mgmtForm", validOptions);
});
</script>			