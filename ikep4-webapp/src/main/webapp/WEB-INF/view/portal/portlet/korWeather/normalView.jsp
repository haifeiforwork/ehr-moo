<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 
<c:set var="prefix" value="ui.portal.portlet.korWeather.title"/>
<script type="text/javascript">
(function($) {
    $(document).ready(function() {  
        
    	loadKorWeather('&amp;pageId=1001');
    	
        tab = $("#divKorWeatherTab_s").tabs({
            select : function(event, ui) {
                switch($(ui.panel).attr("id")) {
                    case "korWeathertabs-1" :                    	
                    	loadKorWeather('&amp;pageId=1001');
                        break;
                    case "korWeathertabs-2" :
                        loadKorWeather('&amp;pageId=1002&amp;when=0&amp;daytime=am');
                        break;
                    case "korWeathertabs-3" :
                    	loadKorWeather('&amp;pageId=1002&amp;when=0&amp;daytime=pm');
                        break;
                    case "korWeathertabs-4" :
                    	loadKorWeather('&amp;pageId=1002&amp;when=1&amp;daytime=am');
                        break;
                    case "korWeathertabs-5" :
                    	loadKorWeather('&amp;pageId=1002&amp;when=1&amp;daytime=pm');
                        break;
                    case "korWeathertabs-6" :
                    	loadKorWeather('&amp;pageId=1002&amp;when=2&amp;daytime=am');
                        break;
                }                
            }
        }); 
    });
})(jQuery);

function loadKorWeather(mode) {
	
    var swf = "http://photo-section.daum-img.net/daum/weather/flash/wt_map0108.swf";
 
    var embedTag = '<embed align="middle" width="315" height="330"' +
        ' flashVars="' + mode + '"' +         
        ' pluginspage="http://www.macromedia.com/go/getflashplayer"' +
        ' type="application/x-shockwave-flash"' +
        ' allowscriptaccess="always"' +
        ' bgcolor="#ffffff"' +
        ' quality="high"' +
        ' menu="false"' +
        ' wmode="transparent"' +    //window
        ' src="' + swf  + '"/>';
    $jq("#korWeatherContainer").hide().html(embedTag).fadeIn(1300);   
}   

</script>

<div id="divKorWeatherTab_s" class="iKEP_tab_s">
        <ul>          
            <li><a href="#korWeathertabs-1">현재날씨</a></li>
            <c:if test="${APM == 0}">
            <li><a href="#korWeathertabs-2">오늘오전</a></li>
            </c:if>
            <li><a href="#korWeathertabs-3">오늘오후</a></li>
            <li><a href="#korWeathertabs-4">내일오전</a></li>
            <li><a href="#korWeathertabs-5">내일오후</a></li>
            <c:if test="${APM == 1}">
            <li><a href="#korWeathertabs-6">모레오전</a></li>
            </c:if>
        </ul>
        <div>
            <div id="korWeathertabs-1"></div>
            <div id="korWeathertabs-2"></div>
            <div id="korWeathertabs-3"></div>
            <div id="korWeathertabs-4"></div>
            <div id="korWeathertabs-5"></div>    
            <div id="korWeathertabs-6"></div>  
        </div> 
        <div style="width:100%;height:340px;position:relative;left:0;margin-left: -9px;">
			   <div id="korWeatherContainer" style="width:100%; height:330px;left: 0px; top: 0px; position: absolute;text-align:center ">
			     
			   </div> 
			   <div style='position:absolute;top:0px;right:10px;z-index:1001;' class="btn_more">
			      <a href='http://weather.media.daum.net/' target='_new'>
			          <img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more">
			      </a>
			    </div>
			    <div style='position:absolute;top:328px;right:10px;z-index:1001;'>
			          <img src="<c:url value='/base/images/portlet/by_daum.gif'/>" title="<ikep4j:message pre="${prefix}" key="daum" />" />
			      </div>
        </div>             
</div>



