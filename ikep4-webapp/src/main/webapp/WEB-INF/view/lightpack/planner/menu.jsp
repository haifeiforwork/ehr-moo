<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/> 
<%--메시지 관련 Prefix 선언 Start--%> 
<c:set var="preMessage"  value="ui.lightpack.planner.common.message" /> 
<c:set var="preLeftMenu"    value="ui.lightpack.planner.leftmenu" />
<c:set var="preScript"  value="ui.lightpack.planner.common.script" /> 
<c:set var="preCommon"  value="ui.lightpack.planner.common" />
<c:set var="preCommon"  value="ui.lightpack.planner.common" />
<c:set var="preDialog"  value="ui.lightpack.planner.dialog" />
 <%--메시지 관련 Prefix 선언 End--%>
 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

	<h1 class="none"><ikep4j:message pre="${preMessage}" key="leftmenu" /></h1>	
	<h2><span><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_home_plan.gif'/>"/></span></h2>
	<div id="ikepPlanner" class="none"></div>
	<div id="user-search" class="mb10">
		<input id="targetUserInfo" class="inputbox" value="<ikep4j:message pre="${preMessage}" key="searchUser" />" type="text" name="targetUserInfo" size="21" />
		<a href="#a" id="targetUserInfoSearch" class="ic_search valign_middle"><span><ikep4j:message pre="${preMessage}" key="searchUser" /></span></a>
	</div>	
	<!--<div id="loading-div" class="hidden">loading...</div> -->				
	<div id="leftmenu-datepicker"></div>
	<div class="blockBlank_20px"></div>
	<div class="left_fixed" id="leftMenu-pane">
		<ul>
			<li class="liFirst opened licurrent"><a href="#a"><ikep4j:message pre="${preLeftMenu}" key="everyoneSchedule" /></a>
				<ul>
					<li class="no_child licurrent" id="companyCalendar"><a href="#a"><ikep4j:message pre="${preLeftMenu}" key="everyoneSchedule" /></a></li>
					<c:if test="${sessionScope.isCompanyScheduleAdmin == true||sessionScope.isPlannerAdmin == true}">
					<li class="no_child" id="companyCalendarMgt"><a href="#a"><ikep4j:message pre="${preLeftMenu}" key="everyoneScheduleMgt" /></a></li>
					<c:if test="${sessionScope.isPlannerAdmin == true}">
					<li class="no_child" id="companyCalendarDownMgt"><a href="#a">전사일정 다운로드 관리</a></li>
					</c:if>
					</c:if>
					<!-- 웹다이어리 임시 메뉴  주석처리. 2014.09.01 -->
					<!--
					<li class="no_child" id="webDiaryMgt"><a href="#a" onclick="javascript: iKEP.popupOpen('<c:url value='/lightpack/planner/webDiary/init.do'/>', {width:1024, height:685, menubar:false, resizable:false, status:false, toolbar:false, location:false}, 'webDiary');" >WebDiary</a></li>
					-->
				</ul>
			</li>			
			<li class="liLast opened"><a href="#a"><ikep4j:message pre="${preLeftMenu}" key="myScheduleMgmt" /></a>
				<ul id="myCal-pane">
					<li class="no_child" id="myCalendar"><a href="#a"><img src="<c:url value="/base/images/icon/ic_arrow_2.gif"/>" alt="" />&nbsp;<ikep4j:message pre="${preLeftMenu}" key="mySchedule" /></a></li>
				</ul>
			</li>
		</ul>
		<!-- 무림제지 일정 import, export 기능 주석처리. 2010.08.29-->
		<!--
		<div class="planner_leftbtn" style="position:relative;">
			<span class="btn_planner_import"><a href="#a"><ikep4j:message pre="${preLeftMenu}" key="import" /></a></span>
			<span class="btn_planner_export"><a href="#a"><ikep4j:message pre="${preLeftMenu}" key="export" /></a></span>
		</div>
		-->					
		<ul>
			<c:if test="${CommonConstant.PACKAGE_VERSION eq CommonConstant.IKEP_VERSION_FULL}">
			<li id="team-pane" class="liFirst opened"><a href="#a"><ikep4j:message pre="${preLeftMenu}" key="teamScheduleMgmt" /></a></li>
			</c:if>
			
			<li <c:if test="${!empty favoriteSearchResult}">class="opened"</c:if><c:if test="${favoriteSearchCondition.recordCount==0}">class="no_child"</c:if> id="favorite-menu"><a href="#a"><ikep4j:message pre="${preLeftMenu}" key="favorite" /></a>
				<c:if test="${!empty favoriteSearchResult}">
					<ul>
					<c:forEach var="favorite" items="${favoriteSearchResult.entity}">
						<li class="no_child"><a href="#${favorite.targetId}" class="favoriteUser" title="${favorite.title} ${favorite.jobTitleName} ${favorite.teamName}">${favorite.title} ${favorite.jobTitleName} ${favorite.teamName}</a></li>
					</c:forEach>
					</ul>
				</c:if>
				<ul>
				<div class="addr_setting">
					<a id="favoriteUserSetting" href="#a" title="setting" >설정</a> 
				</div>
				</ul>
			</li>
			<li <c:if test="${!empty favoriteTeamSearchResult}">class="opened"</c:if><c:if test="${favoriteTeamSearchCondition.recordCount==0}">class="no_child"</c:if> id="favorite-team-menu"><a href="#a"><ikep4j:message pre="${preLeftMenu}" key="favorite" />(Team)</a>
				<c:if test="${!empty favoriteTeamSearchResult}">
					<ul>
					<c:forEach var="favorite" items="${favoriteTeamSearchResult.entity}">
						<li class="no_child"><a href="${favorite.targetId}" class="favoriteTeam" title="${favorite.workspaceName}">${favorite.workspaceName}</a></li>
					</c:forEach>
					</ul>
				</c:if>
				<ul>
				<div class="addr_setting">
					<a id="favoriteTeamSetting" href="#a" title="setting" >설정</a> 
				</div>
				</ul>
			</li>
			<li class="no_child" id="mandate-menu"><a href="#a"><ikep4j:message pre="${preLeftMenu}" key="mandateMgmt" /></a></li>
			<c:if test="${sessionScope.isPlannerAdmin == true}">
			<li class="liLast opened"><a href="#a"><ikep4j:message pre="${preLeftMenu}" key="admin" /></a>
				<ul>
					<li class="no_child" id="holiday-menu"><a href="#a"><ikep4j:message pre="${preLeftMenu}" key="holiday" /></a></li>
					<li class="liLast no_child" id="category-menu"><a href="#a"><ikep4j:message pre="${preLeftMenu}" key="category" /></a></li>
				</ul>
			</li>	
			</c:if>	
			<li class="no_child" id="teamMenuList"><a href="#a">모바일 메뉴관리</a></li>
		</ul>
	</div>
	<div id="new-version">&nbsp;&nbsp;</div>
	<div id="dialogImport" class="none">
		<form method="post" enctype="multipart/form-data" action="<c:url value="/lightpack/planner/calendar/importSchedule.do"/>">
			<div>
				<input name="scheduleFile" type="file" size="45" style="width:370px;">
			</div>
			<div class="prPhoto1_Pop" style="color:#888; padding:10px;">
				<ikep4j:message pre='${preDialog}' key='import.comment' />
			</div>
			<div class="blockButton">
				<ul>
					<li><a id="btnImportClose" class="button" href="#a" id="btnSend"><span><ikep4j:message pre='${preCommon}' key='button.close' /></span></a></li>
					<li><a id="btnImportSend" class="button" href="#a" id="btnSend"><span><ikep4j:message pre='${preDialog}' key='import.button' /></span></a></li>
				</ul>
			</div>
		</form>
	</div>
	<div id="dialogExport" class="none">
		<form method="post" action="<c:url value="/lightpack/planner/calendar/exportSchedule.do"/>">
			<div style="margin-top:7px;">
				<ikep4j:message pre='${preDialog}' key='export.label.select' /> : 
				<label><input type="radio" name="exportType" value="ALL" checked="checked"/> <ikep4j:message pre='${preDialog}' key='export.label.all' /></label>
				<label style="margin-left:20px;"><input type="radio" name="exportType" value="MONTH"/> <ikep4j:message pre='${preDialog}' key='export.label.recentMonth' /></label>
				<label style="margin-left:20px;"><input type="radio" name="exportType" value="ETC"/> <ikep4j:message pre='${preDialog}' key='export.label.selectPeriod' /></label>
			</div>
			<div id="divDateAppoint" class="none" style="text-align:right; margin:7px 0px 7px 50px;">
				<input type="text" name="startDate" readonly="readonly" class="inputbox" style="width:65px;"/> <img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/> ~ 
				<input type="text" name="endDate" readonly="readonly" class="inputbox" style="width:65px;"/> <img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
			</div>
			<div class="prPhoto1_Pop" style="color:#888; padding:10px;">
				<ikep4j:message pre='${preDialog}' key='export.comment' />
			</div>
			<div class="blockButton">
				<ul>
					<li><a id="btnExportClose" class="button" href="#a" id="btnSend"><span><ikep4j:message pre='${preCommon}' key='button.close' /></span></a></li>
					<li><a id="btnExportSend" class="button" href="#a" id="btnSend"><span><ikep4j:message pre='${preDialog}' key='export.button' /></span></a></li>
				</ul>
			</div>
		</form>
	</div>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/menu.js"/>"></script>
<script type="text/javascript">
var completeImport = null;
var noExportEvent = null;
(function($){
	$(document.body).ready(function(event) {
		
		var home_url = iKEP.getContextRoot() + "/";
		$jq('#favoriteTeamSetting').click( function(event) { 
			var url =  home_url + "lightpack/planner/calendar/favoriteSettingForm.do?flg=2";			
			iKEP.popupOpen(url, {width:800, height:300 ,resizable:false}, 'favoriteSetting');
		});
		
		$jq('#favoriteUserSetting').click( function(event) { 
			var url =  home_url + "lightpack/planner/calendar/favoriteSettingForm.do?flg=1";			
			iKEP.popupOpen(url, {width:800, height:300 ,resizable:false}, 'favoriteSetting');
		});
		

		
		$(".btn_planner_import").click(function() {
			$("#dialogImport").dialog({
				title : "<ikep4j:message pre='${preDialog}' key='import.title' />",
				width : 400,
				height:180,
				modal: true,
				resizable : false,
				open : function() {
					$('<iframe name="frmScheduleImport" frameborder="0" style="width:0; height:0; border:0;"/>').appendTo(this);
					$("input[type=file]", this).val("");
					$("#btnImportSend").click(function() {
						var $form = $(this).parents("form:first");
						var fileName = $form.find("input[type=file]").val();
						var arrFileName = fileName.split(".");
						if(!fileName) {
							alert("<ikep4j:message pre='${preDialog}' key='import.message.select' />");
							return false;
						}
						if(arrFileName[arrFileName.length-1].toUpperCase() != "ICS") {
							alert("<ikep4j:message pre='${preDialog}' key='import.message.fileExtend' />");
							return false;
						}
						if(completeImport == null) {
							completeImport = function() {
								$("#dialogImport").ajaxLoadComplete();
								$("#dialogImport").dialog("close");
								
								$("#mgmt-panel").trigger("onComplete");	// 화면 갱신
							};
						}
						
						$("#dialogImport").ajaxLoadStart();
						$form.attr("target", "frmScheduleImport");
						$form.submit();
					});
					$("#btnImportClose").click(function() { $("#dialogImport").dialog("close"); });
				},
				close : function() {
					$("#btnImportSend").unbind("click");
					$("#btnImportClose").unbind("click");
					$("iframe[name=frmScheduleImport]").remove();
				}
			});
		});
		
		$(".btn_planner_export").click(function() {
			$("#dialogExport").dialog({
				title : "<ikep4j:message pre='${preDialog}' key='export.title' />",
				width : 400,
				height:200,
				modal: true,
				resizable : false,
				open : function() {
					$('<iframe name="frmScheduleExport" frameborder="0" style="width:0; height:0; border:0;"/>').appendTo(this);
					$("#btnExportSend").click(function() {
						var $form = $(this).parents("form:first");
						
						var type = $("input[name=exportType]:checked", $form).val();
						if(type) {
							if(type == "ETC") {
								if(!$("input[name=startDate]", $form).val()) {
									alert("<ikep4j:message pre='${preDialog}' key='export.message.selectPeriod' />");
									$("input[name=startDate]", $form).trigger("focus");
									return false;
								}
								if(!$("input[name=endDate]", $form).val()) {
									alert("<ikep4j:message pre='${preDialog}' key='export.message.selectPeriod' />");
									$("input[name=endDate]", $form).trigger("focus");
									return false;
								}
							}
							
							$form.attr("target", "frmScheduleExport");
							$form.submit();
						} else {
							alert("<ikep4j:message pre='${preDialog}' key='export.message.selectLabel' />");
						}
						
						if(noExportEvent == null) {
							noExportEvent = function() {
								alert("<ikep4j:message pre='${preDialog}' key='export.message.noExportEvent' />");
								
								$("#dialogExport").dialog("close");
							};
						}
					});
					$("input[name=exportType]", this).click(function(){
						if($(this).val() == "ETC") {
							$("#divDateAppoint").show();
						} else {
							$("#divDateAppoint").hide();
						}
					});
					var startDate = new Date(), endDate = new Date();
					startDate.setMonth(startDate.getMonth()-1);
					startDate.setDate(startDate.getDate()+1);
					
					$("input[name=startDate]", "#divDateAppoint").datepicker().next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); })
						.end().end().val($.datepicker.formatDate($.datepicker.regional[iKEPLang.datepicker.langCode].dateFormat, startDate));
					$("input[name=endDate]", "#divDateAppoint").datepicker().next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); })
						.end().end().val($.datepicker.formatDate($.datepicker.regional[iKEPLang.datepicker.langCode].dateFormat, endDate));
					
					$("#btnExportClose").click(function() { $("#dialogExport").dialog("close"); });
					$("input[name=exportType]:checked", this).trigger("click");
				},
				close : function() {
					$("#btnExportSend").unbind("click");
					$("#btnExportClose").unbind("click");
					$("input[name=exportType]", this).unbind("click");
					$("input", "#divDateAppoint").datepicker("destroy");
					$("iframe[name=frmScheduleExport]").remove();
				}
			});
		});
	});
})(jQuery);
 
</script>