<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:set var="preAlert" value="message.portal.portlet.congratulations.configView.alert"/>
<c:set var="preForm" value="ui.portal.portlet.congratulations.configView.form"/>
<c:set var="preButton" value="ui.portal.portlet.congratulations.configView.button"/>
<c:set var="preMain" value="ui.portal.portlet.congratulations.configView.main"/>
<c:set var="preList" value="ui.portal.portlet.congratulations.configView.list"/>

<script type="text/javascript">
//<![CDATA[  
(function($) {
	
	$jq(document).ready(function() {
		
		var portletcongratulationsPriodConfigDiv = $jq("div[id=${portletConfigId}]");
		
		$jq("#saveBtn_${portletConfigId}").click(function() {
			
			$jq.ajax({
				url : "<c:url value='/portal/portlet/congratulations/saveCongratulationsConfig.do'/>",
				data : $jq("#portletCongratulationsForm${portletConfigId}").serialize(),
				type : "post",
				success : function(result) {
					alert("<ikep4j:message pre='${preAlert}' key='saveSuccess' />");
					
					portletcongratulationsPriodConfigDiv.parent().parent().parent().trigger("click:reload");
				},
				error : function(event, request, settings) { 
					alert("<ikep4j:message pre='${preAlert}' key='saveFail' />");
				}
			});
		});
		
	});
	
})(jQuery);
//]]>
</script>

<div class="tableList_1" id="${portletConfigId}">
	<form name="portletCongratulationsForm${portletConfigId}" id="portletCongratulationsForm${portletConfigId}" action="" method="post">
	<input type="hidden" id="portletConfigId" name="portletConfigId" value="${portletConfigId}" />
	<input type="hidden" id="portletId" name="portletId" value="${portletId}" />
	<div class="poEdit">
		<div>
			<ikep4j:message pre="${preForm}" key="targetPeriod" /> : 
			<label><input type="radio" value="WEEK" title="<ikep4j:message pre='${preForm}' key='week' />" class="radio" name="period" <c:if test="${empty congratulationsPriodConfig || (!empty congratulationsPriodConfig && congratulationsPriodConfig.propertyValue == 'WEEK')}">checked="checked"</c:if>><ikep4j:message pre="${preForm}" key="week" /></label>&nbsp;&nbsp;
			<label><input type="radio" value="MONTH" title="<ikep4j:message pre='${preForm}' key='month' />" class="radio" name="period" <c:if test="${!empty congratulationsPriodConfig && congratulationsPriodConfig.propertyValue == 'MONTH'}">checked="checked"</c:if>><ikep4j:message pre="${preForm}" key="month" /></label>									
		</div>
		<div>
			<ikep4j:message pre="${preForm}" key="targetGroup" /> :
			<ikep4j:message pre="${preForm}" key="targetGroupDesc" />
		</div>
		<div>
            <ikep4j:message pre="${preForm}" key="targetMember" /> : 
            <label><input type="radio" value="JRM" title="<ikep4j:message pre='${preForm}' key='JRM' />" class="radio" name="usertype" <c:if test="${empty congratulationsUserTypeConfig || (!empty congratulationsUserTypeConfig && congratulationsUserTypeConfig.propertyValue == 'JRM')}">checked="checked"</c:if>><ikep4j:message pre="${preForm}" key="JRM" /></label>&nbsp;&nbsp;
            <label><input type="radio" value="NJRM" title="<ikep4j:message pre='${preForm}' key='NJRM' />" class="radio" name="usertype" <c:if test="${!empty congratulationsUserTypeConfig && congratulationsUserTypeConfig.propertyValue == 'NJRM'}">checked="checked"</c:if>><ikep4j:message pre="${preForm}" key="NJRM" /></label>&nbsp;&nbsp;   
            <label><input type="radio" value="ALLM" title="<ikep4j:message pre='${preForm}' key='ALLM' />" class="radio" name="usertype" <c:if test="${!empty congratulationsUserTypeConfig && congratulationsUserTypeConfig.propertyValue == 'ALLM'}">checked="checked"</c:if>><ikep4j:message pre="${preForm}" key="ALLM" /></label>                                                                     
        </div>
		<div class="textRight">
			<a href="#" id="saveBtn_${portletConfigId}" class="button_s" onclick="return false;"><span><ikep4j:message pre="${preButton}" key="save" /></span></a>
		</div>
	</div>
	</form>
	
	<div class="pTitle_1">
		<ikep4j:message pre="${preMain}" key="birthdayTitle" />
	</div>
	<table summary="<ikep4j:message pre='${preMain}' key='birthdayTableSummary' />">
        <caption></caption>
        <tbody>
            <c:if test="${empty list}">
            <tr>
                <td class="textCenter">
                    <ikep4j:message pre="${preList}" key="noData" />
                </td>
            </tr>
            </c:if>
            <c:if test="${!empty list}">
            <c:set var="birthdayCnt" value="0" />
            <c:forEach var="birthday" items="${list}" varStatus="status">           
            <c:set var="birthdayCnt" value="${birthdayCnt+1}" />
            <tr>
                <c:choose>
                <c:when test="${currentDate == birthday.solarBirthday}">
                <th class="today" scope="row" >
                    <div class="ellipsis">
                    <img alt="<ikep4j:message pre='${preList}' key='birthday' />" src="<c:url value='/base/images/icon/ic_birthday.gif'/>" />
                </c:when>
                <c:otherwise>
                <th scope="row" >
                    <div class="ellipsis">
                </c:otherwise>
                </c:choose>
                    <a href="#" onclick="iKEP.goProfilePopupMain('${birthday.userId}'); return false;">
                        <c:choose>
                        <c:when test="${user.localeCode == 'ko'}">
                        ${birthday.userName}&nbsp;${birthday.jobPositionName}(${birthday.groupName})
                        </c:when>
                        <c:otherwise>
                        ${birthday.userEnglishName}&nbsp;${birthday.jobPositionEnglishName}(${birthday.groupEnglishName})
                        </c:otherwise>
                        </c:choose>
                    </a>
                    </div>
                </th>
                <c:choose>
                <c:when test="${currentDate == birthday.solarBirthday}">
                <td class="textRight today" width="100px">
                </c:when>
                <c:otherwise>
                <td class="textRight" width="100px">
                </c:otherwise>
                </c:choose>
                    <span class="date">
                        <c:if test="${birthday.type == '1'}">
                        ${birthday.lunisolarBirthday}(음)/${birthday.solarBirthday}
                        </c:if>
                        <c:if test="${birthday.type == '0'}">
                        ${birthday.solarBirthday}(양)/${birthday.solarBirthday}
                        </c:if>                     
                    </span>
                </td>
            </tr>
            </c:forEach>
            <c:if test="${birthdayCnt == 0}">
            <tr>
                <td class="textCenter">
                    <ikep4j:message pre="${preList}" key="noData" />
                </td>
            </tr>
            </c:if>
            </c:if>                     
        </tbody>
    </table>	
	<div class="l_b_corner"></div><div class="r_b_corner"></div>
</div>
