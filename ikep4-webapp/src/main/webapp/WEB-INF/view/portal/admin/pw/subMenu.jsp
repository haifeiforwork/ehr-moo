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
    	iKEP.iFrameMenuOnclick("<c:url value='/portal/admin/pw/pwUpdateList.do'/>");
		
	
	});
	
})(jQuery);


//-->
</script>



			<!--leftMenu Start-->

				<h1 class="none">LEFT MENU</h1>	
                <h2><a href="<c:url value='/portal/admin/pw/menuView.do'/>">
                <span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
				<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">변경 대상 조회</font>
                </a></h2>	
				<div class="left_fixed" id="leftMenu-pane">
					<ul>
						<li class="no_child">
							<a id="pwUpdateOfLeft" href="<c:url value='/portal/admin/pw/menuView.do'/>">
								변경 대상 조회
							</a>
						</li>
					</ul>
				</div>	

			<!--//leftMenu End-->