<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%--메시지 관련 Prefix 선언 Start--%> 
<c:set var="preMessage"  value="ui.lightpack.planner.common.message" /> 
<c:set var="preCategory"    value="ui.lightpack.planner.category" />
<c:set var="preButton"  value="ui.lightpack.planner.common.button" /> 
 <%--메시지 관련 Prefix 선언 End--%>
 <link rel="stylesheet" type="text/css" href="<c:url value="/base/css/planner/calendar.css"/>" />

	<h1 class="none"><ikep4j:message pre="${preMessage}" key="contents" /></h1>
	
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre="${preCategory}" key="mgmt" /></h2>
	</div>
	
	<!--blockDetail Start-->
	<div class="blockDetail">
	  <form id="mgmtForm" action="">
		<input id="categoryId" name="categoryId" type="hidden" value="${category.categoryId}"/>
		<input id="categoryColor" type="hidden" value="${category.color}"/>
		
		<table summary="<ikep4j:message pre="${preCategory}" key="insert" />">
			<caption></caption>
			<tbody>
				<c:forEach var="item" items="${category.plannerCategoryLocaleList}" varStatus="status">
				<tr>
					<th scope="row" width="18%"><ikep4j:message pre="${preCategory}" key="name" /> (${item.localeCode})</th>
					<td width="82%">
					<div>
					  <input name="plannerCategoryLocaleList[${status.index }].categoryName" class="inputbox w80 categoryName" type="text" 
					  	title="<ikep4j:message pre="${preCategory}" key="name" />" value="${item.categoryName}" />		
					</div>					  
					  <input name="plannerCategoryLocaleList[${status.index }].localeCode" value="${item.localeCode}" type="hidden"/>	
					  <input name="plannerCategoryLocaleList[${status.index }].localeName" value="${item.localeName}" type="hidden"/>							  
					</td>
				</tr>
				</c:forEach>
				<tr>
					<th scope="row" width="18%"><ikep4j:message pre="${preCategory}" key="color" /></th>
					<td width="82%">
						<select id="color_pane" title="<ikep4j:message pre="${preCategory}" key="selectColor" />" name="color"
						 value="${category.color}"></select>
						<span id="sample_color" class="cal_color_box_sample1"></span>
					</td>
				</tr>																						
			</tbody>
		</table>
	  </form>
	</div>
	<!--//blockDetail End-->
		
	<!--tableBottom Start-->
	<div class="tableBottom">										
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a class="button" href="#a" id="btn_insert"><span><ikep4j:message pre="${preButton}" key="create" /></span></a></li>
				<li><a class="button" href="#a" id="btn_list"><span><ikep4j:message pre="${preButton}" key="list" /></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
	<!--//tableBottom End-->		

<!--//mainContents End-->
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/plannerCommon.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/category_edit.js"/>"></script>
<script type="text/javascript">
$jq(document).ready(function() {
	var validOptions = {rules:{}, messages:{}};
	var rules = {}, messages = {};
	$jq(".categoryName").each(function(idx) {
		rules[this.name] = {
				required: true,
				maxlength: 200
		};
		messages[this.name] = {
			direction : "right",
			required : '<ikep4j:message key="NotEmpty.planner.common" />',
			maxlength : "<ikep4j:message key='Max.holiday.holidayName' arguments='200'/>"
		}
	});
	validOptions.rules = rules;
	validOptions.messages = messages;
	new iKEP.Validator("#mgmtForm", validOptions);
});
</script>