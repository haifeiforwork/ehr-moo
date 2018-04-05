<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="prefixUi" value="ui.portal.portlet.weather.configView"/>
<c:set var="prefixMessage" value="message.portal.portlet.weather.configView"/>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">

<!--   
$jq(document).ready(function() {
	
	var weatherDiv = $jq("div[id=${portletConfigId}]");
	
	$jq("#addButton", weatherDiv).click(function() {
		
		var citySelectValue = $jq("#weatherCity", weatherDiv).val();
		var citySelectText = $jq("#weatherCity option:selected", weatherDiv).text();
		var selectCityDivSpans = $jq("#selectCityDiv", weatherDiv).children();
		
		if(selectCityDivSpans.length > 4) {
			alert("<ikep4j:message pre="${prefixMessage}" key="alert.cityCountMessage" />");
			return;
		}
		
		var addFlag = true;
		
		$jq.each(selectCityDivSpans, function() {
			if(this.id == citySelectValue) {
				addFlag = false;
			}
		});
		
		if(addFlag) {
			$jq("#selectCityDiv", weatherDiv).append(" <span id='"+citySelectValue+"'>"+citySelectText+" <img src='<c:url value='/base/images/icon/ic_btn_delete.gif'/>' title='<ikep4j:message pre='${prefixUi}' key='cityDeleteButton' />' alt='<ikep4j:message pre='${prefixUi}' key='cityDeleteButton' />' align='bottom' onclick='javascript: removeWeatherCity(this)'/></span>");
		} else {
			alert("<ikep4j:message pre="${prefixMessage}" key="alert.cityAddMessage" />");
		}
	});
	
	$jq("#weatherConfigSave", weatherDiv).click(function() {
		var cityList = '';
		
		var selectCityDivSpans = $jq("#selectCityDiv", weatherDiv).children();
		$jq.each(selectCityDivSpans, function() {
			if(cityList == '') {
				cityList = this.id;
			} else {
				cityList += "," + this.id;
			}
		});
		
		$jq.ajax({
			url : '<c:url value="/portal/portlet/webWeather/createWeather.do"/>',
			data : {portletConfigId:'${portletConfigId}', cityList:cityList},
			type : "post",
			dataType : "html",
			success : function(result) {
				weatherDiv.parent().parent().parent().trigger("click:reload");
				alert("<ikep4j:message pre="${prefixMessage}" key="alert.saveMessage" />");
			},
			error : function() {alert('error');}
		});		
	});
	
	$jq("#weatherNation", weatherDiv).change(function() {
	   $jq("#weatherCity", weatherDiv).empty();
	   
	   $jq.get('<c:url value="/portal/portlet/webWeather/listWeatherNationCity.do"/>?nationCode='+$jq(this).val())
       .success(function(data) {
    	   for(var i=0; i<data.length; i++){
    		   <c:choose>
				   <c:when test="${user.localeCode == portal.defaultLocaleCode}">
				   	   $jq("#weatherCity", weatherDiv).get(0).options[i] = new Option(data[i].cityName, data[i].cityId);
				   </c:when>
				   <c:otherwise>
				   	   $jq("#weatherCity", weatherDiv).get(0).options[i] = new Option(data[i].cityEnglishName, data[i].cityId);
				   </c:otherwise>
			   </c:choose>
		   }		
       }) 
       .error(function(event, request, settings) { alert("error"); });
	});
});

function removeWeatherCity(obj) {
	$jq(obj).parent().remove();
}

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
	
	var weatherDetailDiv = $jq("#weatherDetailDiv${portletConfigId}");
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
	var weatherDetailDiv = $jq("#weatherDetailDiv${portletConfigId}");
	weatherDetailDiv.html("");
	weatherDetailDiv.hide();
}
//-->
</script>

<div id="${portletConfigId}" class="poEdit"><!--edit펼침화면 display:none____none 제거시 display 됨-->
	<div>
		<ikep4j:message pre="${prefixUi}" key="nationArea" /> :
		<select id="weatherNation" name="weatherNation" title="<ikep4j:message pre="${prefixUi}" key="nationArea" />">
			<c:forEach var="weatherNation" items="${weatherNationList}" varStatus="i">
				<option value="${weatherNation.nationCode}">${weatherNation.nationName}</option>
			</c:forEach>
		</select>									
	</div>
	<div>
		<ikep4j:message pre="${prefixUi}" key="cityAdd" /> :
		<select id="weatherCity" name="weatherCity" title="<ikep4j:message pre="${prefixUi}" key="cityAdd" />">
			<c:forEach var="weatherCity" items="${weatherCityList}" varStatus="i">
				<option value="${weatherCity.cityId}">
					<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}">
							${weatherCity.cityName}
						</c:when>
						<c:otherwise>
							${weatherCity.cityEnglishName}
						</c:otherwise>
					</c:choose>
				</option>
			</c:forEach>
		</select>
		&nbsp;<a id="addButton" class="button_s" href="#a" title="<ikep4j:message pre="${prefixUi}" key="cityAddButton" />"><span><ikep4j:message pre="${prefixUi}" key="cityAddButton" /></span></a>
	</div>
	<div id="selectCityDiv">
		<ikep4j:message pre="${prefixUi}" key="selectCity" /> :
		<c:forEach var="weatherInfo" items="${weatherInfoList}" varStatus="i">
			<span id="${weatherInfo.cityId}">
				<c:choose>
					<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						${weatherInfo.cityName}
					</c:when>
					<c:otherwise>
						${weatherInfo.cityEnglishName}
					</c:otherwise>
				</c:choose>
				<img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" title="<ikep4j:message pre="${prefixUi}" key="cityDeleteButton" />" alt="<ikep4j:message pre="${prefixUi}" key="cityDeleteButton" />" align="bottom" onclick="javascript: removeWeatherCity(this)"/>
			</span>
		</c:forEach>
	</div>
	<div><b>(<ikep4j:message pre="${prefixUi}" key="cityCountMessage" />)</b></div>
	<div class="textRight"><a id="weatherConfigSave" class="button_s" href="#a" title="<ikep4j:message pre="${prefixUi}" key="saveButton" />"><span><ikep4j:message pre="${prefixUi}" key="saveButton" /></span></a></div>
</div>

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
<div id="weatherDetailDiv${portletConfigId}" class="weather_layer" style="top:0px; left:0px; z-index:99; display: none; min-width:135px;"></div>