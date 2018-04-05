<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>  

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
goDayTab = function(meetingRoomId, day) {    
	$jq("#listType").val("day");
	$jq("#placeLi").attr("class", "ui-state-default ui-corner-top");
	$jq("#dayLi").attr("class", "ui-state-default ui-corner-top ui-tabs-selected ui-state-active");
	$jq("#weekLi").attr("class", "ui-state-default ui-corner-top");
	$jq("#recentLi").attr("class", "ui-state-default ui-corner-top");
	$jq("#favoriteLi").attr("class", "ui-state-default ui-corner-top");
	
	$jq("category").val($jq("categoryId").val());
	$jq("region").val($jq("regionId").val());
	
	var url  = '<c:url value="/lightpack/meetingroom/day/cartooletcMain.do"/>';
	$jq.ajax({     
		url : url,    
		type : "post",
		data : {
			catooletcId : catooletcId,
			currentYear : day.substring(0, 4),
			currentMonth : day.substring(5, 7),
			currentDay : day.substring(8, 10)
		},
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

// afterSave = function() {
// 	getList();
// };

(function($) {
	$jq(document).ready(function() { 
		$("table.meeting_schedule").click(function(event) {
			var el = event.target;
			if(el.tagName.toLowerCase() == "td") {
				var $el = $(el);
				if($el.hasClass("empty")) {
					var tdData = $el.data();
					var trData = $el.parent().data();
					
					var reserveInfo = {
						cartooletcId : trData.cartooletc_id,		
						cartooletcName : trData.cartooletc_name,
						categoryName : trData.category_name,		
						regionName : trData.region_name,
						day:tdData.day, fromTime:tdData.from_time, toTime:tdData.to_time
					};
					goForm(reserveInfo, "week");
				} else {
					goDayTab($el.parent().data("cartooletc_id"), $el.data("day"));
				}
			}
		});
	});
})(jQuery);
//-->	
</script>
			
<!--blockDetail Start-->
<div class="blockListTable">

	<table class="meeting_schedule" summary="<ikep4j:message pre='${preHeader}' key='cartooletc' />" border="1" >
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" rowspan="2" width="17%">
					<ikep4j:message pre='${preDetail}' key='category' />
				</th>
				<th scope="col" rowspan="2" width="8%">
					<ikep4j:message pre='${preDetail}' key='region' />
				</th>
				<th scope="col" rowspan="2" width="*">
					<ikep4j:message pre='${preDetail}' key='cartooletcName' />
				</th>
				<th scope="col" colspan="4" width="8%">
					<ikep4j:message pre='${preDetail}' key='sun' />
				</th>
				<th scope="col" colspan="4" width="8%">
					<ikep4j:message pre='${preDetail}' key='mon' />
				</th>
				<th scope="col" colspan="4" width="8%">
					<ikep4j:message pre='${preDetail}' key='tue' />
				</th>
				<th scope="col" colspan="4" width="8%">
					<ikep4j:message pre='${preDetail}' key='wed' />
				</th>
				<th scope="col" colspan="4" width="8%">
					<ikep4j:message pre='${preDetail}' key='thu' />
				</th>
				<th scope="col" colspan="4" width="8%">
					<ikep4j:message pre='${preDetail}' key='fri' />
				</th>
				<th scope="col" colspan="4" width="8%">
					<ikep4j:message pre='${preDetail}' key='sat' />
				</th>
			</tr>
			<tr>
				<c:forEach begin="1" end="7" step="1">
				<th scope="col" width="2%" style="padding-left:0px; padding-right:0px;">8</th>
				<th scope="col" width="2%" style="padding-left:0px; padding-right:0px;">11</th>
				<th scope="col" width="2%" style="padding-left:0px; padding-right:0px;">14</th>
				<th scope="col" width="2%" style="padding-left:0px; padding-right:0px;">17</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:if test="${!empty timeList}">
			<c:forEach var="timeList" items="${timeList}" varStatus="status">
				<c:forEach var="time" items="${timeList}" varStatus="status">
					<c:if test="${time.isHeader == 'Y'}">
					<tr data-building_name="${time.categoryName}" data-floor_name="${time.regionName}" data-meeting_room_name="${time.cartooletcName}" data-meeting_room_id="${time.cartooletcId}">
						<th class="ellipsis textLeft" width="17%" title="${time.categoryName}">${time.categoryName}</th>
						<th class="ellipsis textLeft" width="8%" title="${time.regionName}">${time.regionName}</th>
						<th class="ellipsis textLeft" width="*" title="${time.cartooletcName}">${time.cartooletcName}</th>
					</c:if>
					<c:if test="${time.isHeader == 'N'}">
						<td class="<c:choose>
									<c:when test="${empty time.scheduleId}">empty</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${time.approveStatus == 'A'}">approve</c:when>
											<c:otherwise>waiting</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>"
							data-day="${time.day}" data-from_time="${time.from}" data-to_time="${time.to}"
							>&nbsp;</td>
					</c:if>
				</c:forEach>
				</tr>
			</c:forEach>	
			</c:if>
			
			<c:if test="${empty timeList}">
			<tr>
				<td colspan="31" class="emptyRecord">
					<ikep4j:message pre='${preSearch}' key='emptyRecord' />
				</td> 
			</tr>	
			</c:if>				      
		</tbody>
		
	</table>			

</div>
<!--blockDetail End-->