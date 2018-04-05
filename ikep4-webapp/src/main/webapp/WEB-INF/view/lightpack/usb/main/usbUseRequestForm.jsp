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


<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.json-2.2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.postjson.js"/>"></script>

<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/fullcalendar-1.5-cust.js"/>"></script>

<script type="text/javascript">
(function($) {
	$(document).ready(function(event) {
		$("input[name=startDate]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("input[name=endDate]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		/* var today = iKEP.getCurTime();
		$("input[name=startDate]").attr("readonly", true).datepicker({
			onSelect : function(){
				var form = $(this)[0].form;
				var validator = $(form).validate();
				validator.element(this);
				validator.element("input[name=endDate]", form);
			},
		    showOn: "button",
		    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true
		}).click(function() { $(this).prev().eq(0).datepicker("show"); });   
		$("input[name=endDate]").attr("readonly", true).datepicker({
			onSelect : function(){
				var form = $(this)[0].form;
				var validator = $(form).validate();
				validator.element(this);
				validator.element("input[name=startDate]", form);
			},
		    showOn: "button",
		    minDate : today,
		    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true
		});    */
		
		
		$("a.btn_save").click(function() {
			if($("input[name=ecmFileCk]").is(":checked")) {
				$("input[name=ecmFileYn]").val("1");
			}else{
				$("input[name=ecmFileYn]").val("0");
			}
			
			if($("input[name=foreverChk]").is(":checked")) {
				$("input[name=foreverYn]").val("1");
			} else {
				$("input[name=foreverYn]").val("0");
				if($("#endDate").val() == ""){
					alert("사용기간을 설정하시기 바랍니다.");
					return;
				}
			}
			if($("#startDate").val() == ""){
				alert("사용기간을 설정하시기 바랍니다.");
				return;
			}
			$("#requestForm").submit();
		});
		$("a.btn_cancel").click(function() {
			$jq("#listForm").submit();
		});
		
		$("input[name=foreverChk]").click(function() { 
			if($("input[name=foreverChk]").is(":checked")) {
				$("#endDateSpan").hide();
			} else {
				$("#endDateSpan").show();
			} 
			
		});
	});
})(jQuery);
</script>
<form id="listForm" name="listForm" method="post" action="<c:url value='/lightpack/usb/usbUseRequestMyList.do'/>" ></form>
<!-- Modal window Start -->
<!-- 사용시class="none"으로 설정 -->
<div id="mgmt-panel" title="">

<!-- div id="calendar" class="hidden"></div -->
<div style="height:10px;"></div>
	<div id="pageTitle" class="btnline"> 
		<h2>보안 예외 신청</h2>
	</div>
	<div class="blockDetail" id="event-form">
	<form id="requestForm" name="requestForm" method="post" action="<c:url value='/lightpack/usb/usbUseRequest.do'/>" >
	<input type="hidden" id="ecmFileYn" name="ecmFileYn">
	<input name="foreverYn"           type="hidden" value="0" /> 
		<table summary="<ikep4j:message pre="${preEdit}" key="summary" />">
			<caption></caption>
			<colgroup>
				<col width="18%" />
				<col width="82%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">신청자</th>
					<td >
						<div>
							${user.teamName} ${user.userName} ${user.jobDutyName}
						</div> 
					</td>
				</tr>
				<tr>
					<th scope="row">소속부서 결재자</th>
					<td >
						<div>
							${leader.teamName} ${leader.userName} ${leader.jobDutyName}
						</div> 
					</td>
				</tr>
				<tr>
					<th scope="row">신청 유형</th>
					<td >
						<div>
							<select name="reqType">
								<option value="01">USB 허용</option>
								<option value="02">워터마킹 해제</option>
								<option value="03">ECM 다운로드 권한</option>
								<option value="04">ECM 로컬 편집 권한</option>
							</select>
						</div> 
					</td>
				</tr>
				<tr>
					<th scope="row">사용 기간</th>
					<td >
						<div>
							<input name="startDate"   id="startDate"  class="inputbox datepicker" type="text" value="" readonly="readonly" /> 
                         	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/> ~
                         	<span id="endDateSpan">
                         	<input name="endDate"   id="endDate"  class="inputbox datepicker" type="text" value="" readonly="readonly" /> 
                         	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/> 
                         	</span>
                         	&nbsp;
                         	<input name="foreverChk" id="foreverChk" type="checkbox" class="checkbox"/>영구
						</div> 
					</td>
				</tr>
				<tr>
					<th scope="row">사유</th>
					<td>
						<div><textarea id="requestReason" name="requestReason" class="w100" title="<ikep4j:message pre="${preLabel}" key="input" />" cols="" rows="20"></textarea></div>
					</td>
				</tr>
				<!-- <tr>
					<th scope="row">ECM 파일 다운로드 유무</th>
					<td >
						<div>
							<input name="ecmFileCk" id="ecmFileCk" type="checkbox" class="checkbox"/>
						</div> 
					</td>
				</tr> -->
				<!-- <tr>
					<th scope="row">USB 파일명</th>
					<td >
						<div>
							<input name="fileName" id="fileName" type="text" value="" class="inputbox w100" />
						</div> 
					</td>
				</tr> -->
			</tbody>
		</table>
		</form>
	</div>			
	<div class="blockButton"> 
		<ul>
			<li><a class="button btn_save" href="#a"><span>등록</span></a></li>
			<li><a class="button btn_cancel" href="#a"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a></li>
		</ul>
	</div>	
</div>