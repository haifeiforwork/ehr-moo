<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%--메시지 관련 Prefix 선언 Start--%> 
<c:set var="preMessage"  value="ui.lightpack.planner.common.message" /> 
<c:set var="preLeftMenu"    value="ui.lightpack.planner.leftmenu" />
<c:set var="preEdit"  value="ui.lightpack.planner.edit.schedule" /> 
<c:set var="preLabel"    value="ui.lightpack.planner.common.label" />
<c:set var="preButton"  value="ui.lightpack.planner.common.button" /> 
<c:set var="preDialog"  value="ui.lightpack.planner.dialog.repeat" /> 
<c:set var="preView"  value="ui.lightpack.planner.view.schedule" /> 
 <%--메시지 관련 Prefix 선언 End--%>
 <link rel="stylesheet" type="text/css" href="<c:url value="/base/css/planner/calendar.css"/>" />


<script type="text/javascript"> 
(function($) {
	viewItem = function(scheduleId){
		var objSplit = null;
		 if(objSplit == null  ) {
			var url = iKEP.getContextRoot() + "/lightpack/planner/calendar/viewSchedule.do";
			var options = {
				width:700,
				height:440,
				resizable:true,
//				argument : {startDate:event.start.toString(), endDate:event.end.toString()},
				callback : function(result) {
					if(result && result["action"]) {
						switch(result.action) {
							case "update" :
								parent.iKEP.goScheduleUpdate({scheduleId:scheduleId}, parent.mainFrame);
								break;
							case "delete" :
								getTodayMySchedule('${currentDate}');
								break;
						}
					}
				}
			};
			iKEP.popupOpen(url + "?scheduleId="+scheduleId, options);
		 }
	};
	
	$jq(document).ready(function() {
		// element var
		var $btn_check	 = $jq("#btn_check");
		var $btn_approve	 = $jq("#btn_apporve");
		var $btn_delete	 = $jq("#btn_delete");
		var $item_list	 = $jq("#item_list");
		var $mgmtForm	 = $jq("#listForm");
		
		var home_url = iKEP.getContextRoot() + "/";
		
		// bind event
		$btn_check.click(function() {
			$item_list.find("input:checkbox").attr("checked", $jq(this).is(":checked"));
		});
		
		$btn_delete.click(function() {
			if($jq("input[name=cid]:checked").size() == 0) {
				alert(iKEPLang.planner.messageText.selectCompany);
				return;
			} 
			if(confirm(iKEPLang.planner.messageText.confirmDeleteSchedule)) {
				$mgmtForm.attr("method", "post");
				$mgmtForm.attr("action", home_url + "lightpack/planner/calendar/companyScheduleDelete.do");
				$mgmtForm.submit();
			}
		});
		

		$btn_approve.click(function() {
			if($jq("input[name=cid]:checked").size() == 0) {
				alert(iKEPLang.planner.messageText.selectCompanyApprove);
				return;
			} 
			if(confirm(iKEPLang.planner.messageText.confirmApproveSchedule)) {
				$mgmtForm.attr("method", "post");
				$mgmtForm.attr("action", home_url + "lightpack/planner/calendar/companyScheduleApprove.do");
				$mgmtForm.submit();
			}
		});
		
		
		//엑셀파일 업로드 버튼 클릭이벤트 처리
		$jq('#btn_excelUp').click(function(event) { 
			var url =  home_url + "lightpack/planner/calendar/companyScheduleExcelForm.do";			
			iKEP.popupOpen(url, {width:800, height:260 ,resizable:false}, 'companyScheduleUpload');
		});
	});
})(jQuery);
</script>	

	<h1 class="none"><ikep4j:message pre="${preLeftMenu}" key="everyoneScheduleMgt" /></h1>

	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre="${preLeftMenu}" key="everyoneScheduleMgt" /></h2>
	</div>
	<!--//pageTitle End-->	
	
	
							<div class="directive"> 
								<ul>
									<li><span style="color:#bf000f">전사일정 등록후 전사일정전체관리자가 승인해야 calendar에 표시됩니다.</span></li>	
									<li><span style="color:#bf000f">전사일정전체관리자는 승인 혹은 삭제할 수 있습니다.</span></li>	
								</ul>
							</div>
							
							<br/>
	<!--blockDetail Start-->					
	<div class="blockListTable">
	<form id="listForm" action="">
		<table summary="<ikep4j:message pre="${preLeftMenu}" key="everyoneScheduleMgt" />">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="3%" class="textCenter"><input type="checkbox" id="btn_check"/></th>
					<th scope="col" width="*" class="textCenter"><ikep4j:message pre="${preLabel}" key="title" /></th>
					<th scope="col" width="25%" class="textCenter"><ikep4j:message pre="${preLabel}" key="period" /></th>
					<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${preLabel}" key="place" /></th>
					<th scope="col" width="10%" class="textCenter"><ikep4j:message pre="${preLabel}" key="register" /></th>
				</tr>
			</thead>
			<tbody id="item_list">
			<c:choose>
				<c:when test="${!empty companyScheduleList}">
					<c:forEach var="item" items="${companyScheduleList}">
						<tr>
							<td class="textCenter">
								<input type="checkbox" class="checkbox" name="cid"  value="${item.scheduleId}"/>
							</td>
							<td class="textCenter"><a href="#a" onclick="viewItem('${item.scheduleId}')">${item.title}</a></td>
							<td class="textCenter">${fn:replace(item.startDate, ":00.0","")} ~ ${fn:replace(item.endDate, ":00.0","")}
							<c:if test="${item.wholeday eq '1'}">&nbsp;&nbsp;종일</c:if>
							<c:if test="${item.repeatType eq '1'}">&nbsp;&nbsp;매일반복</c:if>
							<c:if test="${item.repeatType eq '2'}">&nbsp;&nbsp;매주반복</c:if>
							<c:if test="${item.repeatType eq '3'}">&nbsp;&nbsp;매월반복</c:if>
							<c:if test="${item.repeatType eq '4'}">&nbsp;&nbsp;매년반복</c:if></td>
							<td class="textCenter">${item.place}</td>
							<td class="textCenter">${item.userInfo}</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="5" class="emptyRecord"><ikep4j:message pre="${preMessage}" key="nodata" /></td> 
					</tr>							
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
	</form>
	</div>
	<!--//blockDetail End-->	
						

	<div class="blockButton"> 			
		<ul>
		<c:if test="${sessionScope.isPlannerAdmin == true}">
			<li><a class="button" href="#a" id="btn_apporve"><span><ikep4j:message pre="${preButton}" key="companyAdminOK" /></span></a></li>
			<li><a class="button" href="#a" id="btn_delete"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
		</c:if>	
			<li><a class="button" href="#a" id="btn_excelUp"><span><ikep4j:message pre="${preButton}" key="companyExcel" /></span></a></li>
		</ul>
	</div>

	<!--//blockButton End-->
										
<!--//mainContents End-->
	