<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%>  
<c:set var="prefix"    value="ui.support.customer.leftmenu" /> 
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
		// left menu setting
		iKEP.setLeftMenu();
		if("${roleId}" == "100001059165" || "${roleId}" == "100000803133"){
	    	iKEP.iFrameMenuOnclick("<c:url value='/support/customer/customerBasicInfo/customerList.do'/>");
		}else{
			goCounselHistory();
		}
		
	
	});
	
})(jQuery);

function goCustomerList(){
	iKEP.iFrameMenuOnclick("<c:url value='/support/customer/customerBasicInfo/customerList.do'/>");
}


function goManInfo(){
	 iKEP.iFrameMenuOnclick("<c:url value='/support/customer/customerCommon/manInfo.do'/>");
}

function goQualityClaim(){
	 iKEP.iFrameMenuOnclick("<c:url value='/support/customer/customerQualityClaim/qualityClaimList.do'/>");
}

function goQualityClaimSell(){
	 iKEP.iFrameMenuOnclick("<c:url value='/support/customer/customerQualityClaimSell/qualityClaimSellList.do'/>");
}

function goBondsIssue(){
	 iKEP.iFrameMenuOnclick("<c:url value='/support/customer/customerBondsIssue/bondsIssueList.do'/>");
}

function goCounselHistory(){
	 iKEP.iFrameMenuOnclick("<c:url value='/support/customer/customerCounselHistory/counselHistoryList.do'/>");
}
function goMainSelling(){
	 iKEP.iFrameMenuOnclick("<c:url value='/support/customer/customerMainSelling/mainSellingList.do'/>");
}

function goRealty(){
	 iKEP.iFrameMenuOnclick("<c:url value='/support/customer/customerRealty/realtyList.do'/>");
}

function goFinance(){
	iKEP.iFrameMenuOnclick("<c:url value='/support/customer/customerFinance/financeList.do'/>");
}

function goFinanceManage(){
	iKEP.iFrameMenuOnclick("<c:url value='/support/customer/customerFinance/editFinance.do'/>");
}

//-->
</script>



			<!--leftMenu Start-->

				<h1 class="none">LEFT MENU</h1>	
                <h2><a href="<c:url value='/support/customer/customerCommon/menuView.do'/>"><%-- <ikep4j:message pre ='${prefix}' key = 'titleMenu' /> --%>
                <span><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_coll_custom.gif'/>"/></span></a></h2>	
				<div class="left_fixed" id="leftMenu-pane">
					<ul>
						<c:if test="${roleId eq '100001059165' or roleId eq '100000803133'}">
							<li id="customerList" class="liFirst no_child licurrent"><a href="javascript:goCustomerList();"><ikep4j:message pre='${prefix}' key='customerList'/></a></li>
                        	<li id="manInfo" class="no_child"><a href="javascript:goManInfo();"><ikep4j:message pre='${prefix}' key = 'manInfo' /></a></li>
	                        <li id="majorRetailer" class="no_child"><a href="javascript:goMainSelling();"><ikep4j:message pre='${prefix}' key = 'majorRetailer' /></a></li>
						</c:if>
						
						
                        <li id="counselHistory" class="no_child"><a href="javascript:goCounselHistory();"><ikep4j:message pre='${prefix}' key = 'counselHistory' /></a></li>
                       <%--  <li class="no_child"><a href="#a"><ikep4j:message pre='${prefix}' key = 'counselList' /></a></li> --%>
                        <li id="quailityCounsel" class="no_child"><a href="javascript:goQualityClaim();">old)<ikep4j:message pre='${prefix}' key = 'quailityCounsel' /></a></li>
                        <li id="quailityCounsel" class="no_child"><a href="javascript:goQualityClaimSell();">new)<ikep4j:message pre='${prefix}' key = 'quailityCounsel' /></a></li>
                        <c:if test="${birole}">
                        	<li id="bondsIssue" class="no_child"><a href="javascript:goBondsIssue();"><ikep4j:message pre='${prefix}' key = 'bondsIssue' /></a></li>
                      	</c:if>
                      	<li id="realty" class="no_child"><a href="javascript:goRealty();">부동산 정보</a></li>
                      	<li id="finance" class="no_child"><a href="javascript:goFinance();">재무/손익 정보</a></li>
                      	<c:if test="${firole}">
                      	<li id="finance" class="no_child"><a href="javascript:goFinanceManage();">재무/손익 정보 기간 관리</a></li>
                      	</c:if>
                      <%--  <li class="no_child"><a href="#a"><ikep4j:message pre='${prefix}' key = 'creditRating' /></a></li> --%>
                      <%--   <li class="no_child"><a href="#a"><ikep4j:message pre='${prefix}' key = 'insolvency' /></a></li> --%>
						<%-- <li class="opened"><a href="#a"><ikep4j:message pre='${prefix}' key = 'warrant' /></a>
							<ul>
								<li class="no_child liLast"><a href="#a"><ikep4j:message pre='${prefix}' key = 'submenu.loans' /></a></li>
								<li class="no_child liLast"><a href="#a"><ikep4j:message pre='${prefix}' key = 'submenu.warrant' /></a></li>
								<li class="no_child liLast"><a href="#a"><ikep4j:message pre='${prefix}' key = 'submenu.monthRotation' /></a></li>							
							</ul>					
						</li>			
                        <li class="opened"><a href="#a"><ikep4j:message pre ='${prefix}' key ='bondInfo' /></a>
							<ul>
								<li class="no_child liLast"><a href="#a"><ikep4j:message pre='${prefix}' key = 'submenu.CEO' /></a></li>
								<li class="no_child liLast"><a href="#a"><ikep4j:message pre='${prefix}' key = 'submenu.store' /></a></li>
								<li class="no_child liLast"><a href="#a"><ikep4j:message pre='${prefix}' key = 'submenu.property' /></a></li>			
                                <li class="no_child liLast"><a href="#a"><ikep4j:message pre='${prefix}' key = 'submenu.financial' /></a></li>	
                                <li class="no_child liLast"><a href="#a"><ikep4j:message pre='${prefix}' key = 'submenu.subContact' /></a></li>			
							</ul>					
						</li>	 --%>	
					</ul>
				</div>	

			<!--//leftMenu End-->