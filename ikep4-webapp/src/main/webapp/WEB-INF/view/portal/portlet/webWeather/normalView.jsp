<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="prefixUi" value="ui.portal.portlet.weather.normalView"/>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
<!--
	function mouseOver(obj, month, day, tempDay , temperLow, temperHigh, condition) {
		var unit = '';
		
		<c:choose>
		   <c:when test="${user.localeCode == 'en'}">
		   	 unit = ' ℉';
		   </c:when>
		   <c:otherwise>
		  	 unit = ' ℃' 
		   </c:otherwise>
		</c:choose>
		 	
		var weatherDetailDiv = $jq("div[id=${portletConfigId}]");
		var content = "<div class='weather_layer_t'>" + month+"/"+day + "("+tempDay+")</div>";
		    content += "<div class='weather_layer_info'>";
		    content += "<ul>";
		    content += "<li><span class='colorBlue'>"+temperLow+unit+"</span> <span class='w_bar'>/</span> <span class='colorPoint'>"+temperHigh+unit+"</span></li>";
		    content += "<li>"+condition+"</li>";
		    content += "</ul></div>";

		weatherDetailDiv.append(content);  
		    
		var imgObj = $jq(obj);
		var position = imgObj.position();
		var leftMinus = (weatherDetailDiv.width()-imgObj.width())/2;
		var heightMinus = weatherDetailDiv.height();
		
		weatherDetailDiv.css({left:position.left-leftMinus, top:position.top-heightMinus});
		weatherDetailDiv.show();
	}
	
	function mouseOut() {
		var weatherDetailDiv = $jq("div[id=${portletConfigId}]");
		weatherDetailDiv.html("");
		weatherDetailDiv.hide();
	}
//-->
</script>

<c:forEach var="weatherInfo" items="${weatherInfoList}" varStatus="i">
<c:if test="${i.count != 1}">
	<div class="weather_line"><span><ikep4j:message pre="${prefixUi}" key="divideLine" /></span></div>
</c:if>
<div class="weather">
	<span>
		<c:choose>
			<c:when test="${user.localeCode == portal.defaultLocaleCode}">
				${weatherInfo.cityName}
			</c:when>
			<c:otherwise>
				${weatherInfo.cityEnglishName}
			</c:otherwise>
		</c:choose>
	</span>
	<div class="weather_view">
		<div class="weather_icon_b">
			<img src="<c:url value='/base/images/portlet/weather/${weatherInfo.icon}'/>" title="<ikep4j:message pre="${prefixUi}" key="currentCondition" />" alt="<ikep4j:message pre="${prefixUi}" key="currentCondition" />"/>
		</div>
		<div class="weather_info">
			<ul>
				<li><span class="colorPoint mr10">${weatherInfo.temp_c} ℃</span><span>(${weatherInfo.temp_f} ℉)</span></li>
				<li>${weatherInfo.condition}</li>
				<li>${weatherInfo.wind_condition}</li>
				<li>${weatherInfo.humidity}</li>
			</ul>
		</div>
	</div>
	<div class="weather_day">
		<ul>
			<li>
				<p>${weatherInfo.day_of_week1}</p>
				<img src="<c:url value='/base/images/portlet/weather/${weatherInfo.icon1}'/>" title="<ikep4j:message pre="${prefixUi}" key="currentCondition1" />" alt="<ikep4j:message pre="${prefixUi}" key="currentCondition1" />" onmouseover="javascript: mouseOver(this, ${month1}, ${day1}, '${weatherInfo.day_of_week1}', '${weatherInfo.low1}', '${weatherInfo.high1}', '${weatherInfo.condition1}');" onmouseout="javascript: mouseOut();"/>
			</li>
			<li>
				<p>${weatherInfo.day_of_week2}</p>
				<img src="<c:url value='/base/images/portlet/weather/${weatherInfo.icon2}'/>" title="<ikep4j:message pre="${prefixUi}" key="currentCondition2" />" alt="<ikep4j:message pre="${prefixUi}" key="currentCondition2" />" onmouseover="javascript: mouseOver(this, ${month2}, ${day2}, '${weatherInfo.day_of_week2}', '${weatherInfo.low2}', '${weatherInfo.high2}', '${weatherInfo.condition2}');" onmouseout="javascript: mouseOut();"/>
			</li>
			<li>
				<p>${weatherInfo.day_of_week3}</p>
				<img src="<c:url value='/base/images/portlet/weather/${weatherInfo.icon3}'/>" title="<ikep4j:message pre="${prefixUi}" key="currentCondition3" />" alt="<ikep4j:message pre="${prefixUi}" key="currentCondition3" />" onmouseover="javascript: mouseOver(this, ${month3}, ${day3}, '${weatherInfo.day_of_week3}', '${weatherInfo.low3}', '${weatherInfo.high3}', '${weatherInfo.condition3}');" onmouseout="javascript: mouseOut();"/>
			</li>
			<li>
				<p>${weatherInfo.day_of_week4}</p>
				<img src="<c:url value='/base/images/portlet/weather/${weatherInfo.icon4}'/>" title="<ikep4j:message pre="${prefixUi}" key="currentCondition4" />" alt="<ikep4j:message pre="${prefixUi}" key="currentCondition4" />" onmouseover="javascript: mouseOver(this, ${month4}, ${day4}, '${weatherInfo.day_of_week4}', '${weatherInfo.low4}', '${weatherInfo.high4}', '${weatherInfo.condition4}');" onmouseout="javascript: mouseOut();"/>
			</li>
		</ul>
	</div>
	<div class="clear"></div>
</div>
</c:forEach>
<c:if test="${empty weatherInfoList}">
	<ikep4j:message pre="${prefixUi}" key="noWeatherCityMessage" />
</c:if>
<div id="${portletConfigId}" class="weather_layer" style="top:0px; left:0px; z-index:99; display: none; min-width:135px;"></div>