<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<c:set var="preMenu"  value="ui.lightpack.meetingroom.menu" />

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
		
		if(window.location.href.indexOf("meetingroom/main") > 0 ) {
			setLicurrent("#meetingRoomReserve");
		} else if (window.location.href.indexOf("myReserveList") > 0 ) {
			setLicurrent("#meetingRoomReserveList");
		} else if (window.location.href.indexOf("approveList") > 0 ) {
			setLicurrent("#meetingRoomAssignReserve");
		} else if (window.location.href.indexOf("buildingFloorMain") > 0 ) {
			setLicurrent("#buildingManage");
		} else if (window.location.href.indexOf("getEquipmentList") > 0 ) {
			setLicurrent("#equipmentManage");
		} else if (window.location.href.indexOf("meetingRoomMain") > 0 ) {
			setLicurrent("#meetingRoomManage");
		} else if (window.location.href.indexOf("meetingroom/cartooletcMain") > 0 ) {
			setLicurrent("#carReserve");
		} else if (window.location.href.indexOf("myCarReserveList") > 0 ) {
			setLicurrent("#carReserveList");
		} else if (window.location.href.indexOf("approveCarList") > 0 ) {
			setLicurrent("#carAssignReserve");
		} else if (window.location.href.indexOf("cartooletc/cartooletcMain") > 0 ) {
			setLicurrent("#carManage");
		} else if (window.location.href.indexOf("approveUse") > 0 ) {
			if($jq("#roomOrTool").val()=="tool"){
				setLicurrent("#carAssignReserve");
			}else{
				setLicurrent("#meetingRoomAssignReserve");
			}
		} else if (window.location.href.indexOf("rejectUse") > 0 ) {
			if($jq("#roomOrTool").val()=="tool"){
				setLicurrent("#carAssignReserve");
			}else{
				setLicurrent("#meetingRoomAssignReserve");
			}
		} 
		iKEP.setLeftMenu();
	});

//]]>
</script>

<!--leftMenu Start-->
<h2 class="han">
	<a href="<c:url value='/lightpack/meetingroom/main.do'/>">
		<span><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_comm_meeting.gif'/>"/></span>
	</a>
</h2>
<div class="left_fixed" id="leftMenu-pane">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">회의실 예약</a>
			<ul>
				<li class="no_child licurrent" id="meetingRoomReserve">
					<a  href="<c:url value='/lightpack/meetingroom/main.do'/>">
						<ikep4j:message pre='${preMenu}' key='meetingRoomReserve' />
					</a>
				</li>
				<li class="no_child" id="meetingRoomReserveList">
					<a  href="<c:url value='/lightpack/meetingroom/reserve/myReserveList.do'/>">
						<ikep4j:message pre='${preMenu}' key='meetingRoomReserveList' />
					</a>
				</li>
				
				<c:if test="${isMeetingRoomManager||isSystemAdmin}">
				<li class="no_child" id="meetingRoomAssignReserve">
					<a  href="<c:url value='/lightpack/meetingroom/approve/approveList.do'/>">
						<ikep4j:message pre='${preMenu}' key='meetingRoomAssignReserve' />
					</a>
				</li>
				</c:if>
			</ul>
		</li>
		<li class="opened"><a href="#">자원 예약</a>
			<ul>
				<li class="no_child" id="carReserve">
					<a  href="<c:url value='/lightpack/meetingroom/cartooletcMain.do'/>">
						자원 예약
					</a>
				</li>
				<li class="no_child" id="carReserveList">
					<a  href="<c:url value='/lightpack/meetingroom/reserve/myCarReserveList.do'/>">
						나의 자원 예약 내역
					</a>
				</li>
				
				<c:if test="${isCarToolManager||isSystemAdmin}">
				<li class="no_child" id="carAssignReserve">
					<a  href="<c:url value='/lightpack/meetingroom/approve/approveCarList.do'/>">
						자원 예약 합의
					</a>
				</li>
				</c:if>
			</ul>
		</li>
		<c:if test="${isSystemAdmin}">
		<li class="opened"><a href="#">회의실 관리</a>
			<ul>
				<li class="liFirst no_child" id="buildingManage">
					<a  href="<c:url value='/lightpack/meetingroom/buildingfloor/buildingFloorMain.do'/>">
						<ikep4j:message pre='${preMenu}' key='buildingManage' />
					</a>
				</li>
				<!-- 무림제지 설치기자재 관리기능 비활성화 함. 2012.09.24 ikep4 개발미비된상태로 받아서 사용할수 없음.
				<li class="no_child"  id="equipmentManage">
					<a href="<c:url value='/lightpack/meetingroom/equipment/getEquipmentList.do'/>">
						<ikep4j:message pre='${preMenu}' key='equipmentManage' />
					</a>
				</li>
				 -->
				<li class="no_child" id="meetingRoomManage">
					<a  href="<c:url value='/lightpack/meetingroom/meetingroom/meetingRoomMain.do'/>">
						<ikep4j:message pre='${preMenu}' key='meetingRoomManage' />
					</a>
				</li>
			</ul>
		</li>
		<li class="liLast opened"><a href="#">자원 관리</a>
			<ul>
				<li class="liFirst no_child" id="carManage">
					<a  href="<c:url value='/lightpack/meetingroom/cartooletc/cartooletcMain.do'/>">
						자원 관리
					</a>
				</li>
			</ul>
		</li>
		</c:if>
	</ul>
	

</div>