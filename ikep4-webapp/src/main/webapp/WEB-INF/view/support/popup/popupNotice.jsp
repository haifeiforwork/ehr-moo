<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preButton"  value="ui.support.popup.button" /> 
<c:set var="prePrivate"    value="ui.support.addressbook.addrgroup.private" />
<c:set var="prePublic"  value="ui.support.addressbook.addrgroup.public" />
<c:set var="prePerList"  value="ui.support.addressbook.person.list" />
<c:set var="prePerDetail"  value="ui.support.addressbook.person.detail" />
<c:set var="preSumMessage"  value="message.support.addressbook.summary" />
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
<!--
(function($) {
	
	$jq(document).ready(function() {
		$jq("#popupCheck_notice").click(function() {
    		var now = new Date(); 
			var dday = new Date(0000,00,00,00,00,00);
			var days = (dday - now) / 1000 / 60 / 60 / 24; 
			var daysRound = Math.floor(days); 
			var hours = (dday - now) / 1000 / 60 / 60 - (24 * daysRound); 
			var hoursRound = Math.floor(hours); 
			var minutes = (dday - now) / 1000 /60 - (24 * 60 * daysRound) - (60 * hoursRound); 
			var minutesRound = Math.floor(minutes); 
			var seconds = (dday - now) / 1000 - (24 * 60 * 60 * daysRound) - (60 * 60 * hoursRound) - (60 * minutesRound); 
			var secondsRound = Math.round(seconds); 
			var extime = (hoursRound*60*60)+(minutesRound*60)+secondsRound
    		var domain = document.domain;
    		
    		var option = {
    			domain: domain,
    	   		//expires: 1 * 24 * 60 * 60,
				expires: extime,
    	   		path: '/'
            };

    		if($jq("#popupCheck_notice").attr("checked")) {
    			$.cookie("IKEP_POPUP_NOTICE", "none", option);
    		} else {
    			$.cookie("IKEP_POPUP_NOTICE", "", option);
    		}
    		
   			window.close();	
		});
	});
})(jQuery);  
//-->
</script>

<div id="popup">
    <!--popup_title Start-->
      <div id="popup_title_2">
           <div class="popup_bgTitle_l"></div>
                    <h1>제증명서 출력 전 주의 사항</h1>
                    <a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
      </div>
    <!--//popup_title End-->  
    <!--popup_contents Start-->
      <div id="popup_contents">                  
             <b>□ 페이지 설정 필요(아래 글 참고)</b><br>
  &nbsp;(※위치 : 인터넷익스플로러 좌측상단 「파일>페이지설정」)<br>
  &nbsp;&nbsp;①머리글(H) : -비어 있음-(상, 중, 하 전부)<br>
   &nbsp;&nbsp;②바닥글(F) : -비어 있음-(상, 중, 하 전부)<br>
   &nbsp;&nbsp;③여백(밀리미터) : 왼쪽(L) → 8mm, 오른쪽(R) → 8mm, 위쪽(T) → 19mm, 아래쪽(B) → 19mm
        </div>
        <!--//popup_contents End-->
        <!--popup_footer Start-->
      <div id="popup_footer"></div>
        <!--//popup_footer End-->
        <div id="checkboxDiv" style="width:100%; background-color: #CACACA;text-align: right; padding-bottom:1px;">
				<input type="checkbox" id="popupCheck_notice" name="popupCheck_notice" title="popupCheck" />
				<span><ikep4j:message key='message.portal.admin.screen.popup.mainPopup.checkMessage'/></span>
			</div>
 </div>
<!--//popup End-->