<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%>  
<c:set var="prefix"    value="ui.support.partner.leftmenu" /> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

 <%-- 메시지 관련 Prefix 선언 End --%> 
<script type="text/javascript">
<!--   

function setLicurrent(el) {
	var $el = el;
	if(typeof el === "string") {
		$el = $jq(el);
	}
	clearCurrent();
	$el.addClass("licurrent");
	$el.parents("li.opened", "#leftMenu-pane").addClass("licurrent");
}

function clearCurrent() {
	$jq("#leftMenu-pane li").removeClass("licurrent");
}


(function($) {
	$jq(document).ready(function(){
		iKEP.setLeftMenu();
		goQualityClaimSell();
	});
})(jQuery);


function goQualityClaimSell(){
	 iKEP.iFrameMenuOnclick("<c:url value='/support/partner/partnerQualityClaimSell/qualityClaimSellList.do'/>");
}

function goManInfo(){
	 iKEP.iFrameMenuOnclick("<c:url value='/support/partner/partnerCommon/manInfo.do'/>");
}

function roleList(){
	 iKEP.iFrameMenuOnclick("<c:url value='/support/partner/partnerCommon/roleList.do'/>");
}

function goRegInfo(){
	 iKEP.iFrameMenuOnclick("<c:url value='/support/partner/partnerCommon/regInfo.do'/>");
}


//-->
</script>



			<!--leftMenu Start-->

				<h1 class="none">LEFT MENU</h1>	
                <h2><a href="<c:url value='/support/partner/partnerCommon/menuView.do'/>"><%-- <ikep4j:message pre ='${prefix}' key = 'titleMenu' /> --%>
                <span><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span><span style="color:#3299CC; font-family:맑은고딕; font-size:17px; font-weight:bold; letter-spacing:0pt; padding-bottom:5px;">&nbsp;거래처 정보</span></a></h2>	
				<div class="left_fixed" id="leftMenu-pane">
					<ul>
						<li id="quailityCounsel" class="no_child"><a href="javascript:goQualityClaimSell();">Contact Report</a></li>
						<li id="manInfo" class="no_child"><a href="javascript:goManInfo();">거래처/인물 정보</a></li>
						<c:if test="${cradminrole}">
						 	<!-- <li id="regInfo" class="no_child"><a href="javascript:goRegInfo();">등록현황</a></li>  -->
						</c:if>
					</ul>
				</div>	
				<br>
				<c:if test="${cradminrole}">
				<div class="blockButton_2"> 
					<a class="button_2 normal" href="javascript:roleList();" ><span>CR 권한관리</span></a>				
				</div>
				</c:if>

			<!--//leftMenu End-->