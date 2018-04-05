<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.lightpack.todo.updateUserSettingView</c:set>
<c:set var="messagePrefix">ui.lightpack.todo.message</c:set>
<c:set var="buttonPrefix">ui.lightpack.todo.button</c:set>
<c:set var="menuPrefix">ui.lightpack.todo.subMenu</c:set>
 <%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
(function($) { 
	$jq(document).ready(function(){
		//저장 버튼 클릭
		$jq("#saveButton").click(function() {
			$jq("#form").submit();
		});
	});


})(jQuery);	
</script>


				<h1 class="none"></h1>

				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre="${menuPrefix}" key="subMenu3"/></h2>
				</div>
				<!--//pageTitle End-->
		
				<!--blockDetail Start-->
				<div class="blockDetail">
					<form id="form" action="<c:url value="/lightpack/todo/updateUserSetting.do"/>" method="post">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
						<caption></caption>
							<tbody>
								<tr>
									<th scope="row"><ikep4j:message pre='${prefix}' key='pageViewNum'/></th>
									<spring:bind path="userSetting.pageViewNum">    
										<td colspan="3">								
											<label><input type="radio" class="radio" title="10" <c:if test="${userSetting.pageViewNum == 10}">checked="checked"</c:if> name="pageViewNum" value="10"/>10</label>&nbsp;&nbsp;&nbsp;
											<label><input type="radio" class="radio" title="15" <c:if test="${userSetting.pageViewNum == 15}">checked="checked"</c:if> name="pageViewNum" value="15"/>15</label>&nbsp;&nbsp;&nbsp;
											<label><input type="radio" class="radio" title="20" <c:if test="${userSetting.pageViewNum == 20}">checked="checked"</c:if> name="pageViewNum" value="20"/>20</label>&nbsp;&nbsp;&nbsp;
											<label><input type="radio" class="radio" title="25" <c:if test="${userSetting.pageViewNum == 25}">checked="checked"</c:if> name="pageViewNum" value="25"/>25</label>&nbsp;&nbsp;&nbsp;
											<label><input type="radio" class="radio" title="30" <c:if test="${userSetting.pageViewNum == 30}">checked="checked"</c:if> name="pageViewNum" value="30"/>30</label>&nbsp;&nbsp;&nbsp;
											<label><input type="radio" class="radio" title="50" <c:if test="${userSetting.pageViewNum == 50}">checked="checked"</c:if> name="pageViewNum" value="50"/>50</label>&nbsp;&nbsp;&nbsp;
										</td>	
									</spring:bind>							
								</tr>
								<tr class="none">
									<th scope="row"><ikep4j:message pre='${prefix}' key='notification.title'/></th>
									<spring:bind path="userSetting.taskEndNotiType">   
										<td colspan="3">								
											<label><input type="radio" class="radio" title="<ikep4j:message pre='${prefix}' key='notification.no'/>" <c:if test="${userSetting.taskEndNotiType == 'N'}">checked="checked"</c:if> name="taskEndNotiType" value="N"/><ikep4j:message pre='${prefix}' key='notification.no'/></label>&nbsp;
											<c:if test="${CommonConstant.PACKAGE_VERSION eq CommonConstant.IKEP_VERSION_FULL}">
											<label><input type="radio" class="radio" title="<ikep4j:message pre='${prefix}' key='notification.sms'/>" <c:if test="${userSetting.taskEndNotiType == 'S'}">checked="checked"</c:if> name="taskEndNotiType" value="S"/><ikep4j:message pre='${prefix}' key='notification.sms'/></label>&nbsp;
											</c:if>
											<label><input type="radio" class="radio" title="<ikep4j:message pre='${prefix}' key='notification.message'/>" <c:if test="${userSetting.taskEndNotiType== 'M'}">checked="checked"</c:if> name="taskEndNotiType" value="M"/><ikep4j:message pre='${prefix}' key='notification.message'/></label>&nbsp;
										</td>
									</spring:bind> 						
								</tr>											
							</tbody>
						</table>
						</form>
						<div class="l_t_corner"></div>
						<div class="r_t_corner"></div>
						<div class="l_b_corner"></div>
						<div class="r_b_corner"></div>
				</div>
				<!--//blockDetail End-->
				
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li><a id="saveButton" class="button"><span><ikep4j:message pre='${buttonPrefix}' key='save'/></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->