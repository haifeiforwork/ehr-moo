<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<c:set var="preMenu"  value="ui.lightpack.overtimework.menu" />
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
		
		iKEP.setLeftMenu();
		
		if("${user.userId}"=="gate"){
			iKEP.iFrameMenuOnclick("<c:url value='/lightpack/overtimework/overtimeworkInOutRegistForm.do'/>");
		}else{
			iKEP.iFrameMenuOnclick("<c:url value='/lightpack/overtimework/overtimeworkInOutMyList.do'/>");
		}
	});
	
	function roleList(){
		 iKEP.iFrameMenuOnclick("<c:url value='/lightpack/overtimework/roleList.do'/>");
	}
	
	function goEditUserCardForm(){
		iKEP.iFrameMenuOnclick("<c:url value='/lightpack/overtimework/OvertimeworkUserCardList.do'/>");
	}
	
	function goOvertimeWorkInOutRegistForm(){
		iKEP.iFrameMenuOnclick("<c:url value='/lightpack/overtimework/overtimeworkInOutRegistForm.do'/>");
	}
	
	function goOvertimeWorkInOutMyList(){
		iKEP.iFrameMenuOnclick("<c:url value='/lightpack/overtimework/overtimeworkInOutMyList.do'/>");
	}
	
	function goOvertimeWorkInOutUnidentifiedList(){
		iKEP.iFrameMenuOnclick("<c:url value='/lightpack/overtimework/overtimeworkInOutUnidentifiedList.do'/>");
	}
	
	
//]]>
</script>

<!--leftMenu Start-->
<h2 class="han">
	<a href="<c:url value='/lightpack/overtimework/overtimeworkUseRequestMenuView.do'/>">
		<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
		<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">휴일근무</font>
	</a>
</h2>
<div class="left_fixed" id="leftMenu-pane">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">휴일근무</a>
			<ul>
				<c:if test="${owadmRole || owusrRole}">
				<li class="no_child" id="overtimeworkInOutRegistForm">
					<a  href="javascript:goOvertimeWorkInOutRegistForm();">
						출입 등록
					</a>
				</li>
				<li class="no_child" id="overtimeworkInOutUnidentifiedList">
					<a  href="javascript:goOvertimeWorkInOutUnidentifiedList();">
						미확인 출입이력 조회
					</a>
				</li>
				</c:if>
				<li class="no_child licurrent" id="overtimeworkInOutMyList">
					<a  href="javascript:goOvertimeWorkInOutMyList();">
						출입이력 조회
					</a>
				</li>
			</ul>
		</li>
		<c:if test="${owadmRole}">
		<li class="closed"><a href="#a">Administrator</a>
			<ul style="display:none">
                <li><a href="javascript:roleList();">권한관리</a></li>
				<li><a href="javascript:goEditUserCardForm();">사용자 카드 관리</a></li>
			</ul>						
		</li>
		</c:if>
	</ul>
</div>
