<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<c:set var="preMenu"  value="ui.lightpack.usb.menu" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

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
		if(window.location.href.indexOf("usbUseRequestMyList") > 0 ) {
			setLicurrent("#usbUseRequestMyList");
		} else if (window.location.href.indexOf("usbUseRequestForm") > 0 ) {
			setLicurrent("#usbUseRequestForm");
		} else if (window.location.href.indexOf("usbUseRequestList") > 0 ) {
			setLicurrent("#usbUseRequestList");
		} 
		iKEP.setLeftMenu();
		iKEP.iFrameMenuOnclick("<c:url value='/lightpack/usb/usbUseRequestMyList.do'/>");
		
		
	});
	function goUsbUseRequestForm(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/usb/usbUseRequestForm.do'/>");
	}

	function goUsbUseRequestMyList(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/usb/usbUseRequestMyList.do'/>");
	}

	function goUsbUseRequestList(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/usb/usbUseRequestList.do'/>");
	}
	
	function goUsbUseRequestAllList(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/usb/usbUseRequestAllList.do'/>");
	}
//]]>
</script>

<!--leftMenu Start-->
<h2 class="han">
	<a href="<c:url value='/lightpack/usb/usbUseRequestMenuView.do'/>">
		<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
		<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">보안예외신청</font>
	</a>
</h2>
<div class="left_fixed" id="leftMenu-pane">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">보안 예외 신청</a>
			<ul>
				<li class="no_child" id="usbUseRequestForm">
					<a  href="javascript:goUsbUseRequestForm();">
						보안 예외 신청
					</a>
				</li>
				<li class="no_child licurrent" id="usbUseRequestMyList">
					<a  href="javascript:goUsbUseRequestMyList();">
						나의 신청 현황
					</a>
				</li>
				
				<c:if test="${user.leader == '1'}">
				<li class="no_child" id="usbUseRequestList">
					<a  href="javascript:goUsbUseRequestList();">
						승인/결재 현황
					</a>
				</li>
				</c:if>
				<c:if test="${usbrole}">
				<li class="no_child" id="usbUseRequestAllList">
					<a  href="javascript:goUsbUseRequestAllList();">
						예외 신청 현황
					</a>
				</li>
				</c:if>
			</ul>
		</li>
	</ul>
	

</div>