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

<script type="text/javascript">
<!--
(function($){	 
	$(document).ready(function() { 
$jq("a.btn_delete").click(function() {
	if(confirm("삭제하시겠습니까?")) {
		$jq("#viewForm").attr("action", "<c:url value='/lightpack/usb/usbUseRequestDelete.do' />");
		$jq("#viewForm")[0].submit();
	}
});

$jq("a.btn_update").click(function() {
	$jq("#viewForm").attr("action", "<c:url value='/lightpack/usb/usbUseRequestUpdateForm.do' />");
	$jq("#viewForm")[0].submit();
});

$jq("a.btn_list").click(function() {
	$jq("#viewForm").attr("action", "<c:url value='/lightpack/usb/usbUseRequestList.do' />");
	$jq("#viewForm")[0].submit();
});
	});    
})(jQuery); 
//-->
</script>
<form id="viewForm" name="viewForm" method="post" return false;" action="">
	<input type="hidden" id="usbId" name="usbId" value = "${usb.usbId}"/>
</form>
	
	<!--popup_contents Start-->
	<div id="popup_contents">
		<div id="pageTitle" class="btnline"> 
		<h2>나의 결재 현황</h2>
		</div>
		<!--blockDetail Start-->		
		<div class="blockDetail">
			<table summary="<ikep4j:message pre="${preView}" key="summary" />">
				<caption></caption>
				<colgroup>
					<col width="20%"/>
					<col width="80%"/>
				</colgroup>
				<tbody>
					<tr>
						<th>기간</th>
						<td>
							${usb.startDate} ~ 
							<c:if test="${usb.foreverYn=='1'}">영구</c:if>
			    			<c:if test="${usb.foreverYn!='1'}">${usb.endDate}</c:if>							
						</td>
					</tr>
					<tr>
						<th>신청 유형</th>
						<td>
							<c:choose>
								<c:when test="${usb.reqType=='01'}">
								USB 허용
								</c:when>
								<c:when test="${usb.reqType=='02'}">
								워터마킹 해제
								</c:when>
								<c:when test="${usb.reqType=='03'}">
								ECM 다운로드 권한
								</c:when>
								<c:when test="${usb.reqType=='04'}">
								ECM 로컬 편집 권한
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>						
						</td>
					</tr>
					<tr>
						<th>신청자</th>
						<td>
							${usb.registerName}						
						</td>
					</tr>
					<tr>
						<th>승인자</th>
						<td>
							${usb.approveUserName}
						</td>
					</tr>
					<tr>
						<th>상태</th>
						<td>
							<c:choose>
								<c:when test="${usb.approveStatus == 'S'}">대기</c:when>
								<c:when test="${usb.approveStatus == 'A'}">승인</c:when>
								<c:when test="${usb.approveStatus == 'R'}">반려</c:when>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th>사유</th>
						<td>
							${usb.requestReason}
						</td>
					</tr>
					<%-- <tr>
						<th>ECM 파일 다운로드 유무</th>
						<td>
							<c:choose>
								<c:when test="${usb.ecmFileYn == '1'}">사용</c:when>
								<c:otherwise>미사용</c:otherwise>
							</c:choose>
						</td>
					</tr> --%>
					<c:if test="${usb.approveStatus == 'R'}">
					<tr>
						<th>반려 사유</th>
						<td>
							${usb.rejectReason}
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
				<c:if test="${user.userId == usb.registerId && usb.approveStatus == 'S'}">
				<li><a class="button btn_update" href="#a"><span><ikep4j:message pre="${preButton}" key="update" /></span></a></li>
				<li><a class="button btn_delete" href="#a"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
				</c:if>
				<li><a class="button btn_list" href="#a"><span><ikep4j:message pre="${preButton}" key="list" /></span></a></li>
			</ul>
		</div>
	</div>
