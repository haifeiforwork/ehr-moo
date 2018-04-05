<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%>  
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

	    iKEP.iFrameMenuOnclick("<c:url value='/support/publication/publication/publicationList.do'/>");
		
	
	});
	
})(jQuery);

function goPublication(){
	iKEP.iFrameMenuOnclick("<c:url value='/support/publication/publication/publicationList.do'/>");
	
}

//-->
</script>



			<!--leftMenu Start-->

				<h1 class="none">LEFT MENU</h1>	
                <h2><a href="<c:url value='/support/publication/publication/menuView.do'/>"><%-- <ikep4j:message pre ='${prefix}' key = 'titleMenu' /> --%>
                <span><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_book_address.gif'/>"/></span></a></h2>	
				<div class="left_fixed" id="leftMenu-pane">
					<ul>			
						<li id="customerList" class="liFirst no_child licurrent"><a href="javascript:goPublication();">간행물 주소록</a></li>                    
					</ul>
				</div>	

			<!--//leftMenu End-->