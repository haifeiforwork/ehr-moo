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


<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	getMeetingList = function(currentYear, currentMonth, currentDay) {
		
		var meetingRoomId = $jq("#meetingRoomListTbl").data("meetingRoomId");
		
		$jq.ajax({     
			
			url : '<c:url value="/lightpack/meetingroom/place/meetingRoomReserveList.do" />',     
			data :  {
				
				meetingRoomId : meetingRoomId,
				currentYear : currentYear, 
				currentMonth : currentMonth, 
				currentDay : currentDay
			},     
			type : "post",     
			loadingElement : {
				
				container : "#meetingList"
			},
			success : function(result) {         
			
				$jq("#meetingList").html(result);
				$jq("#meetingDetail").html("");
			},
			error : function(event, request, settings) { 
				
				alert("error"); 
			}
		});  
	};
	
	$jq(document).ready(function() { 
		
		var $planner_datepicker = $jq("#planner-datepicker");
		
		$planner_datepicker.datepicker({
			
			onSelect : function(dateText, inst) {
				
				getMeetingList(inst.currentYear, inst.currentMonth + 1, inst.currentDay);
				//$(this).datepicker( "seDate" ,$(this).datepicker( "getDate" ));
				//alert($(this).datepicker( "getDate" ));
			}
		});
		
		var chkVale = /[0-9]{4}.[0-9]{2}.[0-9]{2}/;
		var dateValue = $jq("#date").val();
		
 		if(chkVale.test(dateValue)) {
 			
			var year = dateValue.substring(0, 4);
			var month = dateValue.substring(5, 7);
			var day = dateValue.substring(8, 10);
			
			getMeetingList(year, month, day);
			
 			$jq("#date").val("");
 		} else {
			
 			getMeetingList();
 		}
	});
})(jQuery);  

//-->	
</script>

<div id="planner-datepicker"></div>

<!--blockListTable Start-->
<div id="detailDiv" class="blockListTable" style="margin-top:10px; min-width:170px;">
	<div id="meetingList"></div>
</div>
<!--//blockListTable End-->		