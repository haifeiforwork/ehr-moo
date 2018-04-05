<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" />
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<% 
	pageContext.setAttribute("lf", "\n"); 
%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<c:if test="${profile != null}">
<script type="text/javascript">
	$jq(document).ready(function() {
		
		/* var tmpCurrentJob1 = "${profile.currentJob}";
		var tmpCurrnetJob2 = tmpCurrentJob1.split(",");
		for(j=0;j<tmpCurrnetJob2.length;j++){
			if(tmpCurrnetJob2[j] != ""){
				$jq("#currentJobView"+(j+1)).append((j+1)+". "+tmpCurrnetJob2[j]);
			}
		} */
		//<c:if test="${editAuthFlag}">
		//if ( isDeviceToggle ) {
		//	$jq(".deviceToggle").click();
		//}
		//</c:if>
	});
	
</script>
<div class="prInfo_team break-word">
	<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${userTeamName}"/></c:when><c:otherwise><c:out value="${userTeamEnglishName}"/></c:otherwise></c:choose>
	<c:if test="${userOtherTeamName != ' '}">
	<br/><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${userOtherTeamName}</c:when><c:otherwise>${userOtherTeamEnglishName}</c:otherwise></c:choose>
	</c:if>
</div>
<table summary="<ikep4j:message pre='${preProfileMain}' key='profile.info'/>" >
	<caption></caption>
	<colgroup>
		<col width="100" />
		<col width="10" style="text-align:center"/>
		<col width="*" />
	</colgroup>
	<tbody>
		<tr>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='phone.title'/></th>
			<td>:</td><td> <span id="user_mobile" ><c:out value="${profile.mobile}"/></span> / <span id="user_office_phone_no" ><c:out value="${profile.officePhoneNo}"/></span></td>
		</tr>
		<tr>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='mailid.title'/></th>
			<td>:</td><td> 
			<%-- 12.08.23 Francis Choi 무림 쇼셜 관련 부분 로직을 제거 --%>
			<span id="user_mail" ><c:out value="${profile.mail}"/></span> &nbsp;&nbsp;
            </td>	
		</tr>
		<tr>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='location.title'/></th>
			<td>:</td><td> <span id="user_office_basic_address" >			
			<%-- <c:choose>
                  <c:when test="${user.localeCode == portal.defaultLocaleCode}">
                       <c:out value="${profile.workPlaceName}" />
                  </c:when>
                   <c:otherwise>
                             <c:out value="${profile.workPlaceEnglishName}" />
                   </c:otherwise>
            </c:choose> 
			</span> / 
			--%>
			<span id="user_office_basic_address" ><c:out value="${profile.officeBasicAddress}"/></span></td>
		</tr>
		<tr>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='locale.title'/></th>
			<td>:</td><td> <!-- span id="user_locale_name" ><c:out value="${profile.localeName}"/></span-->			
			<span id="user_timezone_name" ><c:out value="${profile.timezoneName}"/></span></td>
		</tr>
		<tr>
			<th scope="row" ><ikep4j:message pre='${preProfileMain}' key='work.title'/></th>
			<td style="vertical-align:top;">:</td><td> <div>
			<%-- <c:out value="${fn:replace(profile.currentJob, lf, '<br/>')}" escapeXml="false" /> --%>
			${profile.currentJob}
			<!-- <span name="currentJobView1" id="currentJobView1"></span><br>
			<span name="currentJobView2" id="currentJobView2"></span><br>
			<span name="currentJobView3" id="currentJobView3"></span> -->
			</div></td>
		</tr>	
		<tr>
			<th scope="row"><ikep4j:message pre='${preProfileMain}' key='work.details'/></th>
			<td style="vertical-align:top;">:</td>
			<td> 
				<div style="width:80%;" >
					<c:set var="tempDetailJob" value="${fn:replace(profile.detailJob, lf, '<br/>')}" />
					<c:out value="${fn:replace(tempDetailJob, ' ', '&nbsp;')}" escapeXml="false" />
				</div>
			</td>
		</tr>	
		<%-- <c:if test="${editAuthFlag}">
		<tr>
			<th scope="row" ><ikep4j:message pre='${preProfileMain}' key='mobile.title'/></th>
			<td valign="top">:</td>
			<td id="mobileTd">
				<c:choose>
					<c:when test="${not empty deviceList.error}">
						<img src='<c:url value="/base/images/icon/ic_alert.gif"/>'/> ${deviceList.error}
					</c:when>
					<c:when test="${deviceList.header.returnCode ne 0}">
						<img src='<c:url value="/base/images/icon/ic_alert.gif"/>'/> ${deviceList.header.returnDesc}
					</c:when>
					<c:otherwise>
						<c:forEach var="device" varStatus="varStatus" items="${deviceList.body.DeviceList}">  
							<c:set var="size" value="${fn:length(deviceList.body.DeviceList)}"/>
							<div class="mDeviceDiv">
								<span class="mDevice mDevice_${device.deviceStatus}" <c:if test="${not empty device.phoneNo}">title="${device.phoneNo}"</c:if> style="display:inline-block;width:80px;vertical-align:top;">${device.modelName}</span>
								<span class="mDeviceStatus" style="display:inline-block;width:70px;vertical-align:middle;"><ikep4j:message pre='${preProfileMain}' key='mobile.${device.deviceStatus}' /></span>
								<span class="mDeviceButton" >
									<c:choose>
									<c:when test="${device.deviceStatus eq 'DPS'}">
									<a class="button_s" href="#a" style="vertical-align:middle;" onclick="updateDeviceStatus('${device.uuid}',0);"><span class="btn_v4 btn-mini"><ikep4j:message pre='${preProfileMain}' key='mobile.unlock'/></span></a>
									</c:when>
									<c:otherwise>
									<a class="button_s" href="#a" style="vertical-align:middle;" onclick="updateDeviceStatus('${device.uuid}',1);"><span class="btn_v4 btn-mini"><ikep4j:message pre='${preProfileMain}' key='mobile.lock'/></span></a>
									</c:otherwise>
									</c:choose>
									<a class="button_s" href="#a" style="vertical-align:middle;" onclick="initializeDevice('${device.uuid}');"><span class="btn_v4 btn-mini"><ikep4j:message pre='${preProfileMain}' key='mobile.initialize'/></span></a>
									<c:if test="${size gt 1 and varStatus.first}">
										<a href="#a"><img class='deviceToggle' src='<c:url value="/base/images/icon/ic_ar_down.gif"/>'/></a>
									</c:if>
								</span>
							</span>
							<c:if test="${size gt 1 and varStatus.first}">
							<div id="moreDeviceList" class="none">
							</c:if>
							<c:if test="${size gt 1 and varStatus.last}">
							</div>
							</c:if>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>	
		</c:if>		 --%>														
	</tbody>
</table>

</c:if>
