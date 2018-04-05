<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<c:set var="prefix" value="message.portal.main.listUser"/>

<c:set var="mssuser" value="${sessionScope['ikep.user']}" />


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




(function($) {



	$jq(document).ready(function() {
		iKEP.setLeftMenu();
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/teamViewSpecialList.do'/>");

		


	});
	

	


})(jQuery);

//]]>
</script>

<!--leftMenu Start-->
<h2 class="han">
	<a href="<c:url value='/portal/moorimmss/teamViewMng.do'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_mss_teamViewSpecialmng.gif'/>" title="MSS 팀뷰어 예외자 관리"></a>

</h2>
<div class="left_fixed" id="leftMenu-pane">

	<ul>
	    
		<li class="liFirst opened licurrent"  ><a href="#">MSS 팀뷰어 관리</a>
			<ul>
				<li class="no_child licurrent"><a  href="<c:url value="/portal/moorimmss/teamViewMng.do" />">MSS 팀뷰어 예외자 관리</a></li>
			</ul>
		</li>
	</ul>


</div>

	

