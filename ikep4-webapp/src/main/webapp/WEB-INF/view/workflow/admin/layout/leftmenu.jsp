<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<script type="text/javascript">
(function($){
	 $(document).ready(function () {
		//=========================================================================
		//* 레프트 메뉴 Display설정  
		//=========================================================================
		iKEP.setLeftMenu();		
     });

	//=========================================================================
	//* 모델러 팝업 
	//=========================================================================
	 fnPopupModel =function()
	{
		window.open("<c:url value='/workflow/modeler/main.do'/>",'fnPopupModel','width='+screen.width+',height='+screen.height+'left=0,top=0');
	}
})(jQuery);
</script>
<!--leftMenu Start-->
<div id="leftMenu">
	<h1 class="none"><ikep4j:message key='ui.workflow.admin.processAdministration.workflow'/></h1>	
	<h2><ikep4j:message key='ui.workflow.admin.processAdministration.workflow'/></h2>
	<div class="left_fixed">
		<ul>
			<li class="liFirst opened licurrent">
				<a href="#a"><ikep4j:message key='ui.workflow.admin.processAdministration.current'/></a>
				<ul>
					<li><a href="<c:url value='/workflow/admin/dashBoardAdministration.do'/>"><ikep4j:message key='ui.workflow.admin.dashBoardAdministration.dashBoardAdministration.dashBoard'/></a></li>
					<li><a href="<c:url value='/workflow/admin/processAdministration.do'/>"><ikep4j:message key='ui.workflow.admin.processAdministration.processManagement'/></a></li>
					<li><a href="<c:url value='/workflow/admin/instanceAdministration.do'/>"><ikep4j:message key='ui.workflow.admin.instanceAdministration.instanceManagement'/></a></li>
					<li><a href="<c:url value='/workflow/admin/todoAdministration.do'/>"><ikep4j:message key='ui.workflow.admin.todoAdministration.todoManagement'/></a></li>
					<li class="liLast"><a href="<c:url value='/workflow/admin/workplace/errorWorkList.do'/>"><ikep4j:message key='ui.workflow.workplace.worklist.errorworklist'/></a></li>
				</ul>
			</li>
			<li class="liFirst opened licurrent">
				<a href="#a"><ikep4j:message key='ui.workflow.admin.security'/></a>
				<ul>
					<li><a href="<c:url value='/portal/admin/group/group/workflow/getList.do'/>"><ikep4j:message key='ui.workflow.admin.groupManagement'/></a></li>
					<li><a href="<c:url value='/portal/admin/member/user/workflow/getList.do'/>"><ikep4j:message key='ui.workflow.admin.userManagement'/></a></li>
					<li class="liLast"><a href="<c:url value='/portal/admin/role/role/workflow/list.do'/>"><ikep4j:message key='ui.workflow.admin.roleManagement'/></a></li>
				</ul>
			</li>
			<li class="liFirst opened licurrent">
				<a href="#a"><ikep4j:message key='ui.workflow.admin.smsAdministration.smsHistoryList.noticeMgmt'/></a>
				<ul>
					<li><a href="<c:url value='/workflow/admin/smsAdministration/smsHistoryList.do'/>"><ikep4j:message key='ui.workflow.admin.smsAdministration.smsHistoryList.smsHistory'/></a></li>
					<li class="liLast"><a href="<c:url value='/workflow/admin/emailAdministration/listAdminEmailLogs.do'/>"><ikep4j:message key='ui.workflow.admin.adminEmail.header.pageTitle'/></a></li>
				</ul>
			</li>
			<li class="no_child">
				<a href="#a" onclick="fnPopupModel();"><ikep4j:message key='ui.workflow.admin.processModeler'/></a>
			</li>
		</ul>
	</div>
</div>	
<!--//leftMenu End-->
