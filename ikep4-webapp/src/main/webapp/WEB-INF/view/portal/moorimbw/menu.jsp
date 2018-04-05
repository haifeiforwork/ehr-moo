<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<c:set var="hassuser" value="${sessionScope['ikep.user']}" />


<script type="text/javascript">
//<![CDATA[

	
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
	
	$jq(document).ready(function() {
		


		iKEP.setLeftMenu();

		document.domain ="moorim.co.kr";

		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/initBwMain.do'/>");


	});

	function bwLink(bwLinks){
		document.domain ="moorim.co.kr";//밑에서 프레임 리사이징을 할때 iframe의 높이를 jquery의 contents()함수를 이용해 가져온다. 이때 같은 도메인이어야 exception나지 않는다. 
		iKEP.iFrameMenuOnclick(bwLinks);

	}
	
	function oldRecordList(){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/oldRecord/oldRecordList.do'/>");

	}
	
	function menuSetCookie(cookieName, menuId) {
		
		var domain = document.domain;
		
		var option = {
	   		domain: domain,
	   		expires: 365,
	   		path: '/'
	    }
		
		if($jq(menuId).css("display") == 'none') {
			$jq.cookie(cookieName, "open", option);
		} else {
			$jq.cookie(cookieName, "close", option);
		}
	}
//]]>
</script>

<!--leftMenu Start-->
<h2 class="han">
	<a href="<c:url value='/portal/moorimess/initBwMain.do'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_know_rival.gif'/>" title="경쟁사 실적"/></a>

</h2>
<%-- <div class="left_fixed">
	<ul>
		<li class="liFirst opened">
		<a href="#" onclick="menuSetCookie('IKEP_PORTAL_ADMIN_MENU_SAVED1', '#IKEP_PORTAL_ADMIN_MENU1');">생산/판매 실적</a>
		<ul id="IKEP_PORTAL_ADMIN_MENU1">
		<li class="no_child"><a href="javascript:bwLink('${serverLinkUrl}bw/BEx?sap-language=KO&bsplanguage=KO&CMD=LDOC&TEMPLATE_ID=ZEIS_C01_WT001');">최근실적</a></li>
		<li class="no_child"><a href="javascript:oldRecordList();">과거실적</a></li>
		<c:if test="${isAcl||isBwLinkSystemAdmin}">
		<li class="no_child"><a href="javascript:bwLink('${serverLinkUrl}bw/BEx?sap-language=KO&bsplanguage=KO&CMD=LDOC&TEMPLATE_ID=ZEIS_M01_WT001');">경영실적</a></li>
		</c:if>
		<c:if test="${isBwLinkSystemAdmin}">
		<li class="no_child"><a href="javascript:bwLink('${serverLinkUrl}bc/bsp/sap/zbw_main/zeis_data_m.htm');">실적관리</a></li>
		</c:if>
		
		</ul>
		</li>
	</ul>
</div> --%>

<div class="left_fixed" id="leftMenu-pane">
	<ul>
		<li class="liFirst no_child licurrent"><a href="javascript:bwLink('${serverLinkUrl}bw/BEx?sap-language=KO&bsplanguage=KO&CMD=LDOC&TEMPLATE_ID=ZEIS_C01_WT001');">생산/판매 실적</a></li>
		<c:if test="${isAcl||isBwLinkSystemAdmin}">
		<li class="no_child"><a href="javascript:bwLink('${serverLinkUrl}bw/BEx?sap-language=KO&bsplanguage=KO&CMD=LDOC&TEMPLATE_ID=ZEIS_M01_WT001');">경영실적</a></li>
		</c:if>
		<c:if test="${isBwLinkSystemAdmin}">
		<li class="no_child"><a href="javascript:bwLink('${serverLinkUrl}bc/bsp/sap/zbw_main/zeis_data_m.htm');">실적관리</a></li>
		</c:if>
		<!-- <li class="no_child"><a href="javascript:oldRecordList();">과거실적</a></li> -->
	</ul>
</div>