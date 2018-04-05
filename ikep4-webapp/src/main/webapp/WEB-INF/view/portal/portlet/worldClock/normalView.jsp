<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="prefix" value="message.portal.portlet.rss.normalView"/>
<c:set var="portletList" value="message.portal.portlet.worldClock.nomalView"/>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
	function getServerTime() {
		var time = iKEP.getCurTime();
		return time.getFullYear() + iKEP.zerofill(time.getMonth()+1, 2) + iKEP.zerofill(time.getDate(), 2) + iKEP.zerofill(time.getHours(), 2) + iKEP.zerofill(time.getMinutes(), 2) + iKEP.zerofill(time.getSeconds(), 2);	// 20110606155001
	}

	function createWorldClock(container) {
		
		var swf = iKEP.getContextRoot()+"/base/swf/portlet/wclock/wclock.swf";
		var title = "<ikep4j:message pre='${portletList}' key='now' />";
		var wxml = iKEP.getContextRoot()+"/base/swf/portlet/wclock/timedata_${user.localeCode}.xml";
		
		var param = "gLocale=${user.localeCode}&amp;gXmlfile="+wxml+"&amp;gRCity=${selectRightCityName}&amp;gLCity=${selectLeftCityName}";
		
		var embedTag = (jQuery.browser["msie"] === true) ? '<object id="embedObj_' + (new Date()).getTime() + '" align="middle" width="310" height="280" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9.0.0.0" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000">' +
				'<param name="allowScriptAccess" value="always"/>' +
				'<param name="movie" value="' + swf + '?' + param + '"/>' +
				'<param name="wmode" value="transparent"/>' +	//window
				'<param name="menu" value="false"/>' +
				'<param name="scaleMode" value="noScale"/>' +
				'<param name="showMenu" value="false"/>' +
				'<param name="align" value="CT"/>' +
				'<param name="quality" value="high"/>' +
				'<param name="bgcolor" value="#ffffff"/>' +
			'</object>' : '<embed align="middle" width="300" height="240"' +
			' pluginspage="http://www.macromedia.com/go/getflashplayer"' +
			' type="application/x-shockwave-flash"' +
			' allowscriptaccess="always"' +
			' bgcolor="#ffffff"' +
			' quality="high"' +
			' menu="false"' +
			' wmode="transparent"' +	//window
			' src="' + swf + '?' + param + '"/>';
		$jq(container).html(embedTag);
	}	
	
	function wClockConfig(type) {
		$jq("#w"+type+"ClockConfig${portletConfigId}").css("display", "");
	}
	
	function wClockConfigClose(type) {
		$jq("#w"+type+"ClockConfig${portletConfigId}").css("display", "none");
	}
	
	function wRClockConfigSave(){
		
          $jq.ajax({
				url : '<c:url value="/portal/portlet/worldClock/createRWorldClock.do"/>',
				data : $jq("#wRClockConfigForm${portletConfigId}").serialize(),
				type : "post",
				dataType : "html",
				success : function() {
					$jq("#${portletConfigId}").parent().parent().parent().trigger("click:reload");
				},
				error : function() {
					alert('error');
				}
			});
	}
	
	function wLClockConfigSave(){
        $jq.ajax({
				url : '<c:url value="/portal/portlet/worldClock/createLWorldClock.do"/>',
				data : $jq("#wLClockConfigForm${portletConfigId}").serialize(),
				type : "post",
				dataType : "html",
				success : function() {
					$jq("#${portletConfigId}").parent().parent().parent().trigger("click:reload");
				},
				error : function() {
					alert('error');
				}
			});
	}
	
	function countryNameChange(obj, type) {
		$jq("#cityName"+type+"Index", $jq("#w"+type+"ClockConfig${portletConfigId}")).empty();
		
		$jq.ajax({
			url : '<c:url value="/portal/portlet/worldClock/listCountryCity.do"/>',
			data : {countryName:$jq(obj).find("option:selected").text()},
			type : "post",
			success : function(data) {
				for(var i=0; i<data.length; i++){
				    $jq("#cityName"+type+"Index", $jq("#w"+type+"ClockConfig${portletConfigId}")).get(0).options[i] = new Option(data[i], i);
				}
			},
			error : function() {
				alert('error');
			}
		});
	}
</script>
<div id="wLClockConfig${portletConfigId}" class="layer_02" style="display: none; text-align: center; left:25px;">
	<A class=layer_close href="#a" onclick='wClockConfigClose("L");'><SPAN><ikep4j:message pre='${portletList}' key='button.close' /></SPAN></A>
	<form id="wLClockConfigForm${portletConfigId}" action="">
		<select name="countryNameLIndex" onchange="countryNameChange(this, 'L');" style="width: 190px;">
			<c:forEach var="countryName" items="${countryNameList}" varStatus="i">
				<option value="${i.index}" <c:if test="${countryName == selectLeftCountryName}">selected</c:if>>${countryName}</option>
			</c:forEach>
		</select><br/>
		<select name="cityNameLIndex" id="cityNameLIndex" style="width: 190px; margin-top: 5px;">
			<c:forEach var="cityName" items="${cityNameLList}" varStatus="i">
				<option value="${i.index}" <c:if test="${cityName == selectLeftCityName}">selected</c:if>>${cityName}</option>
			</c:forEach>
		</select><br/>
		<a class=button_s href="#a" onclick="wLClockConfigSave();" style="margin-top: 3px;"><span><ikep4j:message pre='${portletList}' key='button.save' /></span></a>		
	</form>
</div>
<div id="wRClockConfig${portletConfigId}" class="layer_02" style="display: none; text-align: center; right:25px;">
	<A class=layer_close href="#a" onclick='wClockConfigClose("R");'><SPAN><ikep4j:message pre='${portletList}' key='button.close' /></SPAN></A>
	<form id="wRClockConfigForm${portletConfigId}" action="">
		<select name="countryNameRIndex" onchange="countryNameChange(this,'R');" style="width: 190px;">
			<c:forEach var="countryName" items="${countryNameList}" varStatus="i">
				<option value="${i.index}" <c:if test="${countryName == selectRightCountryName}">selected</c:if>>${countryName}</option>
			</c:forEach>
		</select><br/>
		<select name="cityNameRIndex" id="cityNameRIndex" style="width: 190px; margin-top: 5px;">
			<c:forEach var="cityName" items="${cityNameRList}" varStatus="i">
				<option value="${i.index}" <c:if test="${cityName == selectRightCityName}">selected</c:if>>${cityName}</option>
			</c:forEach>
		</select><br/>
		<a class=button_s href="#a" onclick="wRClockConfigSave();" style="margin-top: 3px;"><span><ikep4j:message pre='${portletList}' key='button.save' /></span></a>		
	</form>
</div>
<div id="${portletConfigId}" class="tableList_1 textCenter" style="position:relative;height:290px;">
<div style="width:100%; height:280px;left: 0px; top: 0px; position: absolute; ">
		<script type="text/javascript"> createWorldClock($jq("#${portletConfigId}").children("div:eq(0)")); </script>		
</div>
<div style="width:100%; left: 0px; top: 39px; position: absolute; ">
	   <div class="colorBlue mt5" ><span style="margin-left:130px;" onclick='wClockConfig("L");'> 
	      <img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>" alt='<ikep4j:message pre='${portletList}' key='button.setting' />' />
	  </span>	   
	   <span onclick='wClockConfig("R");'  style="margin-left:125px;">
	      <img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>" alt='<ikep4j:message pre='${portletList}' key='button.setting' />' />
      </span></div>
</div>
</div>
