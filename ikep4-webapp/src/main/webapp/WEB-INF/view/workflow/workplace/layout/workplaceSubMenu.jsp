<%--
=====================================================
* 기능 설명 : 다이나믹 타일즈2 Header Page
* 작성자 : 주길재
=====================================================
--%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">

$jq(document).ready(function(){

	// Left Menu setting
    iKEP.setLeftMenu();
	
	$jq("body").addClass("bg_leftline");
});

</script>
<!--leftMenu Start-->
	<h1 class="none">레프트메뉴</h1>	
	<h2>Workflow</h2>	
	<div class="left_fixed">
		<ul>
			<li class="liFirst opened"><a href="#a">업무목록</a>
				<ul>
					<li class="no_child"><a href="<c:url value="/workflow/workplace/worklist/myWorkList.do"/>">나의 업무목록</a></li>
					<li class="no_child"><a href="<c:url value="/workflow/workplace/worklist/progressWorkList.do"/>">진행중 업무목록</a></li>
					<li class="no_child"><a href="<c:url value="/workflow/workplace/worklist/doneWorkList.do"/>">완료된 업무목록</a></li>
					<li class="no_child"><a href="<c:url value="/workflow/workplace/worklist/errorWorkList.do"/>">에러 업무목록</a></li>
					<li class="liLast no_child"><a href="<c:url value="/workflow/workplace/worklist/startWorkList.do"/>">업무시작</a></li>
				</ul>	
			</li>
			<li class="no_child"><a href="#a">업무통계</a>
				<ul>
					<li class="liLast no_child"><a href="<c:url value="/workflow/workplace/statistics/procStatistics.do"/>">업무통계</a></li>
				</ul>
			</li>
			<li class="opened"><a href="#a">개인설정</a>
				<ul>
					<li class="liLast no_child"><a href="<c:url value="/workflow/workplace/personal/delegateSetDetail.do"/>">위임자 설정</a></li>
				</ul>
			</li>
		</ul>
	</div>
