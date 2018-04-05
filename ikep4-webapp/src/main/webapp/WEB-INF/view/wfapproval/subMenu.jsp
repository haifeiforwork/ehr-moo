<%--
=====================================================
* 기능 설명 : 다이나믹 타일즈2 Header Page
* 작성자 : 주길재
=====================================================
--%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
<!--
   (function($){
       $(document).ready(function () {
           // Left Menu setting
           //iKEP.setLeftMenu();

    	   $jq("body").addClass("bg_leftline");
    	   
       });
   })(jQuery);
-->
</script>

	<h1 class="none">레프트메뉴</h1>	
	<h2>Approval</h2>
	<div class="left_fixed">
		<ul>			
			<li class="liFirst opened"><a href="#">기안문서</a>
				<ul>
					<li class="no_child"><a href="/ikep4-webapp/wfapproval/work/aplist/listApMyRequest.do">상신함</a></li>
					<li class="no_child"><a href="/ikep4-webapp/wfapproval/work/aplist/listApTemp.do">임시보관함</a></li>
				</ul>					
			</li>
			<li class="opened"><a href="#">결재문서</a>
				<ul>
					<li class="no_child"><a href="/ikep4-webapp/wfapproval/work/aplist/listApTodo.do">미결함</a></li>
					<li class="no_child"><a href="/ikep4-webapp/wfapproval/work/aplist/listApComplete.do">기결함</a></li>
				</ul>
			</li>
			<li class="opened"><a href="#">참조문서</a>
				<ul>
					<li class="no_child"><a href="/ikep4-webapp/wfapproval/work/aplist/listApRef.do">참조문서함</a></li>
				</ul>
			</li>
			<li class="no_child">
				<a href="<c:url value="/wfapproval/work/apform/listApFormSelect.do?linkType=approval"/>">품의서 작성</a>
			</li>
			<!--
			<li class="no_child">
				<a href="<c:url value="/wfapproval/work/apform/listApFormSelect.do?linkType=mywork"/>">품의서 작성 My Work</a>
			</li>
			-->
			<li class="opened"><a href="#">환경설정</a>
				<ul>
					<li class="no_child"><a href="<c:url value="/wfapproval/work/delegate/updateDelegateForm.do"/>">위임관리</a></li>
				</ul>
			</li>
			<li class="opened"><a href="#">Administrator</a>
				<ul>
					<li class="no_child"><a href="<c:url value="/wfapproval/admin/apform/listApForm.do"/>">양식관리</a></li>
					<li class="no_child liLast"><a href="<c:url value="/wfapproval/admin/apcode/listApCode.do"/>">코드관리</a></li>
				</ul>
			</li>	
			<li class="no_child">
				<a href="<c:url value="/workflow/admin/processAdministration.do"/>">Workflow Admin</a>
			</li>
		</ul>
	</div>
