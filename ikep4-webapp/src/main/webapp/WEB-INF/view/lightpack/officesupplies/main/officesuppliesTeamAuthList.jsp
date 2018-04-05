<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="preHeader"  value="ui.lightpack.board.boardAdmin.createBoardView" /> 
<c:set var="preDetail"  value="ui.lightpack.board.boardAdmin.createBoardView.detail" />
<c:set var="preMessage" value="message.lightpack.common.boardAdmin" />
<c:set var="preButton"  value="ui.lightpack.common.button" />   

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/approval/admin/apprForm.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/base/js/wceditor/css/editor.css'/>"/>
<script type="text/javascript" src="<c:url value='/base/js/units/approval/admin/apprForm.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/planner/fullcalendar-1.5.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/planner/calendar.css"/>" />
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/fullcalendar-1.5-cust.js"/>"></script>

<script  type="text/javascript">
<!--// 

	(function($) {
        
        $(document).ready(function() {   
		    //- 버튼영역 실행  
		    var today = iKEP.getCurTime();
			$jq("#saveButton").click(function() {   
				var tempStartDate = "";
				var tempEndDate = "";
				var tempYear = "";
				var tempMonth = "";
				var tempStatus = "";
				var startDate = "";
				var endDate = "";
				var year = "";
				var month = "";
				var status = "";
				for(i=1;i<13;i++){
					tempStartDate = $("#startDate"+i).val(); 
					if(tempStartDate == ""){
						tempStartDate = "N";
					}
					tempEndDate = $("#endDate"+i).val();  
					if(tempEndDate == ""){
						tempEndDate = "N";
					}
					tempStatus = $jq("input[name=statusCheck"+i+"]:checked").attr("value");
					tempMonth = i;
					if(i == 1){
						startDate = tempStartDate;
						endDate = tempEndDate;
						status = tempStatus;
						month = tempMonth;
					}else{
						startDate = startDate+"|"+tempStartDate;
						endDate = endDate+"|"+tempEndDate;
						status = status+"|"+tempStatus;
						month = month+"|"+tempMonth;
					}
				}
				$jq('input[name=startDate]').val(startDate); 
				$jq('input[name=endDate]').val(endDate);  
				$jq('input[name=status]').val(status);
				$jq('input[name=month]').val(month);
	    		$.ajax({
					url : "<c:url value='/lightpack/officesupplies/savePeriod.do' />",
					data : $("#itemForm").serialize(),
					type : "post",
					dataType : "html",
					success : function(result) {
						if(result == 'success') {
							alert("<ikep4j:message pre='${preMessage}' key='sendMessage'/>");
						} else {
							alert('error');
						}
					},
					error : function() {alert('error');}
				});
	    	});  
			
			checkRadioBox = function(tmpMth){
				for(i=1;i<13;i++){
					$jq('input:radio[name=statusCheck'+i+']:input[value=N]').attr("checked", true);
				}
				$jq('input:radio[name=statusCheck'+tmpMth+']:input[value=Y]').attr("checked", true);
			};
		    
        });
        
        
		
	})(jQuery);  

	function getList() {
	
	$jq("#itemForm").attr("action", "<c:url value='/lightpack/officesupplies/editPeriodForm.do' />");
	$jq("#itemForm")[0].submit();
	}
//-->
</script>

<!--popup Start-->

<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>부서 권한 관리</h2>
	<div class="clear"></div>
</div>
	<!--popup_contents Start-->
	
	<form id="itemForm" name="itemForm" method="post" action="" onsubmit="return false;">	
   		<!--blockDetail Start-->
		<div class="blockListTable">
			<table style="width:50%;">
				<caption></caption>
				<colgroup>
					<col width="12%" />
					<col width="40%" />
					<col width="25%" />
					<col width="25%" />
				</colgroup>
				<tbody>   
    			    <tr>
    					<th style="text-align:center;border-left:1px solid #e0e0e0;">사용여부</th>
    					<th style="text-align:center;border-left:1px solid #e0e0e0;">부서</th>
    					<th style="text-align:center;border-left:1px solid #e0e0e0;">검토자</th>
    					<th style="text-align:center;border-left:1px solid #e0e0e0;border-right:1px solid #e0e0e0;">결재자</th>
    				</tr>
    				 <c:forEach var="teams" items="${officesuppliesTeamAuthList}">
    				<tr>
    					<td style="text-align:center;border-left:1px solid #e0e0e0;"><c:if test="${teams.useYn == 'Y'}">사용</c:if><c:if test="${teams.useYn == 'N'}">미사용</c:if></td>
						<td style="text-align:center;border-left:1px solid #e0e0e0;">
							<a href="<c:url value='/lightpack/officesupplies/officesuppliesTeamAuthEditForm.do?teamId=${teams.teamId}'/>" > ${teams.teamName}</a>
						</td>
						<td style="text-align:center;border-left:1px solid #e0e0e0;">${teams.teamManagerName}</td>
						<td style="text-align:center;border-left:1px solid #e0e0e0;border-right:1px solid #e0e0e0;">${teams.teamLeaderName}</td>
    			    </tr>
    			    </c:forEach>
    			</tbody>
			</table>			
			
		</div>
		<!--//blockDetail End-->	
																																			
		
		
		<!--blockButton Start-->
		
	</form>
	<!--//popup_contents End-->

<!--//popup End-->