<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="prefix" value="message.portal.admin.screen.page.readPageMain"/>

<c:set var="portalPortletList" value="${portalDefaultMain.portletList}" />
<c:set var="portalPortletCategoryList" value="${portalDefaultMain.portletCategoryList}" />
<c:set var="commonPageLayoutList" value="${portalDefaultMain.commonPageLayoutList}" />
<c:set var="commonActivePortletList" value="${portalDefaultMain.commonActivePortletList}" />
<c:set var="defPageLayoutList" value="${portalDefaultMain.defPageLayoutList}" />
<c:set var="defPortletList" value="${portalDefaultMain.defPortletList}" />
<c:set var="commonLayoutList" value="${portalDefaultMain.commonLayoutList}" />
<c:set var="layoutList" value="${portalDefaultMain.layoutList}" />
<c:set var="portalPage" value="${portalDefaultMain.page}" />

<script type="text/javascript">
<!--
var portletManager;
(function($) {
	$(document).ready(function() {
		<c:choose>
		<c:when test="${portalPage.pageId == defPortalPageId}">
		$jq("#portletMainPageUpdateOfLeft").click();
		</c:when>
		<c:otherwise>
		$jq("#portletPageManageOfLeft").click();
		</c:otherwise>
		</c:choose>
		
		$("#divTab1").tabs({
			select : function(event, ui) {
				switch(ui.index) {
					case 0 : //getView();
						$("#areaPortletButton").hide();
						break;
					case 1 :
						$("#areaPortletButton").show();
						if(!portletManager) {
							portletManager = new PortletManager({
								commonSortAreas : commonSortAreas,
								commonPortlets : commonPortlets,
								sortAreas : sortAreas,
								portlets : portlets,
								portletGroups : portletGroups,
								activePortlets : activePortlets,
								isSetting : true
							});
							
							portletManager.setControl();
							
							$("#areaPortletButton").find("li>a")
								.eq(0).click(function() {	// cancel button
									$("#divTab1").tabs("select", 0);
								}).end()
								.eq(1).click(function() {	// save button
									if(confirm("<ikep4j:message pre="${prefix}" key="confirm.saveMessage" />")) {
										var defPortletData = "";	// 서버로 전송할 데이타
										var defLayoutData = "";	// 서버로 전송할 데이타
										var commonPortletData = "";	// 서버로 전송할 데이타
										var commonLayoutData = "";	// 서버로 전송할 데이타
										
										var createdPortlets = portletManager.getCreatedPortlets();
										var areaCommon = portletManager.getOptions("commonSortAreas");
										var areaPersonal = portletManager.getOptions("sortAreas");
										
										$.each(createdPortlets, function() {
											var isCommon = 0;											
											var portletInfo = {rowIndex:-1, colIndex:-1, portletId:$.data(this.portlet.box.container[0], "portletId"), viewMode:"NORMAL"};
											
											var portletDiv = this.portlet.box.container;
											var portletLayout = portletDiv.parent();
											
											if(!portletLayout.attr("id") && portletLayout.hasClass("maximize")) {
												portletDiv = this.portlet.box.getDivParent();
												portletLayout = portletDiv.parent();
											}
											
											var colIdx = $.inArray("#"+portletLayout.attr("id"), areaCommon);
											
											if(colIdx > -1) isCommon = 1;
											else colIdx = $.inArray("#"+portletLayout.attr("id"), areaPersonal);
											
											portletInfo.colIndex = colIdx + 1;
											portletInfo.rowIndex = portletDiv.prevAll().length + 1;
											
											if(isCommon == 1) {
												if(!commonPortletData) {
													commonPortletData = JSON.stringify(portletInfo);
												} else {
													commonPortletData += "^" + JSON.stringify(portletInfo);
												}
											} else {
												portletInfo.viewMode = this.portlet.box.getContainerState();
												
												if(!defPortletData) {
													defPortletData = JSON.stringify(portletInfo);
												} else {
													defPortletData += "^" + JSON.stringify(portletInfo);
												}
											}
										});
										
										var commonLayoutId = $("input[name=commonLayoutId]").val();
										
										$.each(areaCommon, function(idx) {
											var $realItem = $(this.toString());
											var style = $realItem[0].style;
											var colIndex = idx + 1;
											
											var layoutInfo = {pageId:'${portalPage.pageId}', layoutId:commonLayoutId, width:parseFloat(style.width, 10), colIndex:colIndex};
											
											if(!commonLayoutData) {
												commonLayoutData = JSON.stringify(layoutInfo);
											} else {
												commonLayoutData += "^" + JSON.stringify(layoutInfo);
											}												
										});
										
										var defLayoutId = $("input[name=defLayoutId]").val();
										
										$.each(areaPersonal, function(idx) {
											var $realItem = $(this.toString());
											var style = $realItem[0].style;
											var colIndex = idx + 1;
											
											var layoutInfo = {pageId:'${portalPage.pageId}', layoutId:defLayoutId, width:parseFloat(style.width, 10), colIndex:colIndex};
											
											if(!defLayoutData) {
												defLayoutData = JSON.stringify(layoutInfo);
											} else {
												defLayoutData += "^" + JSON.stringify(layoutInfo);
											}
										});
										
										$.ajax({
											url : "<c:url value='createPageDefLayoutPortlet.do'/>",
											data : {pageId:'${portalPage.pageId}', defPortletData:defPortletData, defLayoutData:defLayoutData, commonPortletData:commonPortletData, commonLayoutData:commonLayoutData},
											type : "post",
											success : function(result) {
												alert("<ikep4j:message pre="${prefix}" key="saveMessage" />");
											},
											error : function() {
												alert("error");
											}
										});
									}
								});
						}
						break;
				}
			},
			create : function() {
				getView();
			}
		});
		
		<c:if test="${createSaveFlag == 'Y'}">
			if(confirm("<ikep4j:message pre="${prefix}" key="confirm.moveMessage1" />" + "\n\n" + "<ikep4j:message pre="${prefix}" key="confirm.moveMessage2" />")) {
				$("#divTab1").tabs("select", 1);
			}
		</c:if>
	});
	
	getView = function() {
		$jq.ajax({
			url : '<c:url value="readPage.do" />',
			data : {pageId:'${portalPage.pageId}'},
			type : "post",
			success : function(result) {
				$jq("#pageContents").html(result);
				
				//className,resourceName,operationName,"권한설정 옵션"
				iKEP.readSecurity("Portal-Page","${portalPage.pageId}","READ","User,Group,Role,Job,Duty", 25);
			}
		});
	};
	
	modifyForm = function() {
		$jq.ajax({
			url : '<c:url value="updatePageForm.do" />',
			data : {pageId:'${portalPage.pageId}'},
			type : "post",
			success : function(result) {
				$jq("#pageContents").html(result);
			}
		});
	};
	
	removePage = function() {
		if(confirm("<ikep4j:message pre="${prefix}" key="deleteMessage" />")) {
			$jq("#mainForm").submit();
		}
	};
})(jQuery);
//-->
</script>
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre="${prefix}" key="title" /></h2>
		<!--  div id="pageLocation">
			<ul>
				<li class="liFirst"><ikep4j:message pre="${prefix}" key="home" /></li>
				<li><ikep4j:message pre="${prefix}" key="1depth" /></li>
				<li><ikep4j:message pre="${prefix}" key="2depth" /></li>
				<li><ikep4j:message pre="${prefix}" key="3depth" /></li>
				<li class="liLast"><ikep4j:message pre="${prefix}" key="lastDepth" /></li>
			</ul>
		</div-->
	</div>
	<!--//pageTitle End-->
	
	<!--tab Start-->		
	<div id="divTab1" class="iKEP_tab">
		<ul>
			<li><a href="#pageContents"><ikep4j:message pre="${prefix}" key="basicsData" /></a></li>
			<li><a href="#tabs-2" onclick=""><ikep4j:message pre="${prefix}" key="layoutData" /></a></li>
		</ul>
		<div id="areaPortletButton" class="blockButton tabBtn none"> 
			<ul>
				<li><a class="button" href="#a" title="<ikep4j:message pre="${prefix}" key="button.cancel" />"><span><ikep4j:message pre="${prefix}" key="button.cancel" /></span></a></li>
				<li><a class="button" href="#a" title="<ikep4j:message pre="${prefix}" key="button.save" />"><span><ikep4j:message pre="${prefix}" key="button.save" /></span></a></li>
			</ul>
		</div>
		<div class="tab_con">
			<div id="pageContents" style="padding: 0px;"></div>
			<div id="tabs-2" style="padding: 0px;">
				<c:if test="${portalPage.pageId == defPortalPageId}">
				<div class="subTitle_2 noline subTitle_btn">
					<h3><ikep4j:message pre="${prefix}" key="commonLayoutTitle" /></h3>
					<div><a id="btnLayoutControlCommon" class="button" href="#a" title="<ikep4j:message pre="${prefix}" key="button.commonLayout" />"><span><ikep4j:message pre="${prefix}" key="button.commonLayout" /></span></a></div>
				</div>
				<div class="setgroup_1">
					<div class="setContents">
						<c:forEach var="commonPageLayout" items="${commonPageLayoutList}" varStatus="i">
							<div id="commonPortlet_${i.count}" style="float:left; width: ${commonPageLayout.width}%; <c:if test='${i.count > 1}'>margin-left : ${portalDefaultMain.commonMarginLeft}%;</c:if>"></div>
						</c:forEach>
						<div class="clear"></div>
						<input type="hidden" name="commonLayoutId" value="${portalDefaultMain.commonLayoutId}"/>
					</div>
				</div>
				</c:if>
				
				<div class="setgroup_2">
					<div class="setContents">
						<div id="portletSetting">
							<div id="divPortletSetting" class="portletSetting_c">
								<div>
									<div class="portletcat">
										<div id="divPortletGroup"></div>
									</div>
									<div class="portletsum">
										<div id="divPortletControl" style=""></div>
									</div>
									
								</div>
					
								<div class="clear"></div>
								
							</div>
						</div>
					</div>
				</div>
				<div class="setgroup_2">
					<div class="setContents">
						
						<div class="subTitle_2 noline subTitle_btn">
							<h3><ikep4j:message pre="${prefix}" key="userLayoutTitle" /> <span class="subTitle_ins"><ikep4j:message pre="${prefix}" key="userLayoutTitle.description" /></span></h3>
							<div><a id="btnLayoutControlPersonal" class="button" href="#a" title="<ikep4j:message pre="${prefix}" key="button.userLayout" />"><span><ikep4j:message pre="${prefix}" key="button.userLayout" /></span></a></div>
						</div>
						<div id="portlet">
							<div class="maximize"></div>
							<c:forEach var="defPageLayout" items="${defPageLayoutList}" varStatus="i">
								<div id="portlet_${i.count}" style="float:left; width: ${defPageLayout.width}%; <c:if test='${i.count > 1}'>margin-left : ${portalDefaultMain.marginLeft}%;</c:if>"></div>
							</c:forEach>
							<div class="clear"></div>
							<input type="hidden" name="defLayoutId" value="${portalDefaultMain.defLayoutId}"/>
						</div>
					</div>
				</div>
				
			</div>
		</div>
	</div>
	<!--//tab End-->

<!-- Modal window Start -->
<div class="none" id="dialogLayoutControl">
	<div class="subTitle_2 noline"><h3><ikep4j:message pre="${prefix}" key="layout.title" /></h3></div>
	
	<form id="fmLayoutControl" action="">
		<div class="blockDetail">
			<table id="tblLauoutInfo" summary="<ikep4j:message pre="${prefix}" key="layout.title" />">
				<caption></caption>
				<tbody>
					<tr>
						<th scope="row" width="18%"><ikep4j:message pre="${prefix}" key="layout.description" /></th>
						<td width="82%">
							<select title="<ikep4j:message pre="${prefix}" key="layout.selectLayout" />" name="layout">
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${prefix}" key="layout.iscommon" /></th>
						<td><ikep4j:message pre="${prefix}" key="layout.yes" /></td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${prefix}" key="layout.colCount" /></th>
						<td>3</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="blockBlank_10px"></div>
		<div class="subTitle_2 noline">
			<h3 style="float:left;"><ikep4j:message pre="${prefix}" key="layout.widthTitle" /></h3>
			<div id="lblLayoutWidth" style="float:right;">margin : <span>2.5%</span>, total : <span>100%</span></div>
		</div>
		
		<div class="blockDetail clear">
			<!--table summary="레이아웃 너비 정보">
				<caption></caption>
				<tbody>
					<tr>
						<td width="32.5%" class="textCenter"><input type="text" class="inputbox" title="너비값1" name="" value="32.5%" size="5" /></td>
						<td width="32.5%" class="textCenter"><input type="text" class="inputbox" title="너비값2" name="" value="32.5%" size="5" /></td>
						<td width="32.5%" class="textCenter"><input type="text" class="inputbox" title="너비값3" name="" value="32.5%" size="5" /></td>
					</tr>
				</tbody>
			</table-->
			<div id="divLayout">
				<div class="clear"></div>
			</div>
		</div>
	</form>
	
	<div class="blockButton"> 
		<ul>
			<li><a id="btnDialogLayoutControlCancel" class="button" href="#a" title="<ikep4j:message pre="${prefix}" key="button.cancel" />"><span><ikep4j:message pre="${prefix}" key="button.cancel" /></span></a></li>
			<li><a id="btnDialogLayoutControlAccept" class="button" href="#a" title="<ikep4j:message pre="${prefix}" key="button.apply" />"><span><ikep4j:message pre="${prefix}" key="button.apply" /></span></a></li>
		</ul>
	</div>
</div>	
<!-- //Modal window End -->
<script type="text/javascript" src="<c:url value="/base/js/portlet.js"/>"></script>
<script type="text/javascript">
<!--
(function($){
	var isSetDialog = false, isCommonLayout;
	var $fmLayout = $("#fmLayoutControl");
	var layoutInfo = [];
	
	var tplPortletLayout = $.template(null, '<div style="float:left; min-height:100px; width:\${percentWidth}; margin-left:\${marginLeft};"></div>');
	var tplPortletLayoutItem = $.template(null, '<div style="float:left; background-color:#eee; padding:5px 0; width:\${percentWidth}; margin-left:\${marginLeft};"></div>');
	var tplPortletLayoutInput = $.template(null, '<input type="text" class="inputbox" title="<ikep4j:message pre="${prefix}" key="layout.widthValue" />" name="width" value="\${width}" size="5" style="text-align:right;"/>%');
	
	$("#btnLayoutControlCommon, #btnLayoutControlPersonal").click(function() {
		isCommonLayout = ($(this).attr("id") == "btnLayoutControlCommon");
		
		$("#dialogLayoutControl").dialog({
			title : (isCommonLayout ? "<ikep4j:message pre='${prefix}' key='layout.common' /> " : "<ikep4j:message pre='${prefix}' key='layout.user' /> ") + "<ikep4j:message pre='${prefix}' key='layout.layoutTitle' />",
			width: 520,
			height:265,
			modal:true,
			resizable: false,
			open : function() {
				var areas = portletManager.getOptions(isCommonLayout ? "commonSortAreas" : "sortAreas");
				var $rows = $("tr" ,"#tblLauoutInfo");
				
				$rows.eq(0).find("select").empty();
				
				if(isCommonLayout) {
					<c:forEach var="commonLayout" items="${commonLayoutList}" varStatus="i">
						$('<option/>').appendTo($rows.eq(0).find("select")).attr('value', '${commonLayout.layoutNum}').html('${commonLayout.layoutName}');
					</c:forEach>
				} else {
					<c:forEach var="layout" items="${layoutList}" varStatus="i">
						$('<option/>').appendTo($rows.eq(0).find("select")).attr('value', '${layout.layoutNum}').html('${layout.layoutName}');
					</c:forEach>
				}
				
				$rows.eq(0).find("select").val(areas.length);
				$rows.eq(1).children(":eq(1)").html(isCommonLayout ? "<ikep4j:message pre="${prefix}" key="layout.yes" />" : "<ikep4j:message pre="${prefix}" key="layout.no" />");
				$rows.eq(2).children(":eq(1)").html(areas.length);
				
				layoutInfo = [];
				var $divLayout = $("#divLayout").empty();
				var totalMargin=0, total = 0;
				
				$.each(areas, function(idx) {
					var $realItem = $(this.toString());
					var style = $realItem[0].style;
					var data = {percentWidth:style.width, width:parseFloat(style.width, 10), marginLeft:style.marginLeft};
					$.tmpl(tplPortletLayoutItem, data).appendTo($divLayout).append($.tmpl(tplPortletLayoutInput, data)).addClass("textCenter");
					
					layoutInfo.push(data.width);
					totalMargin += parseFloat(data.marginLeft, 10) || 0;
					total += data.width || 0;
				});
				$divLayout.append('<div class="clear"/>');
				
				$("span", "#lblLayoutWidth").eq(0).html(totalMargin+"%").end()
					.eq(1).html(total + totalMargin + "%");
			}
		});
		
		
		
		if(isSetDialog == false) {
			$("select[name=layout]", $fmLayout).change(function(event) {
				var $divLayout = $("#divLayout").empty();
				switch(parseInt($(this).val(), 0)) {
					case 1 :
						var style = {percentWidth:"100%", width:100};
						$.tmpl(tplPortletLayoutItem, style).appendTo($divLayout).append($.tmpl(tplPortletLayoutInput, style)).addClass("textCenter");
						$("span", "#lblLayoutWidth").eq(0).html("0%");
						break;
					case 2 :
						var style = {percentWidth:"49.5%", width:49.5};
						$.tmpl(tplPortletLayoutItem, style).appendTo($divLayout).append($.tmpl(tplPortletLayoutInput, style)).addClass("textCenter");
						style["marginLeft"] = "1%";
						$.tmpl(tplPortletLayoutItem, style).appendTo($divLayout).append($.tmpl(tplPortletLayoutInput, style)).addClass("textCenter");
						$("span", "#lblLayoutWidth").eq(0).html("1%");
						break;
					case 3 :
						var style = {percentWidth:"32.5%", width:32.5};
						$.tmpl(tplPortletLayoutItem, style).appendTo($divLayout).append($.tmpl(tplPortletLayoutInput, style)).addClass("textCenter");
						style["marginLeft"] = "1.25%";
						$.tmpl(tplPortletLayoutItem, style).appendTo($divLayout).append($.tmpl(tplPortletLayoutInput, style)).addClass("textCenter");
						$.tmpl(tplPortletLayoutItem, style).appendTo($divLayout).append($.tmpl(tplPortletLayoutInput, style)).addClass("textCenter");
						$("span", "#lblLayoutWidth").eq(0).html("2.5%");
						break;
					case 4 :
						var style = {percentWidth:"24.1%", width:24.1};
						$.tmpl(tplPortletLayoutItem, style).appendTo($divLayout).append($.tmpl(tplPortletLayoutInput, style)).addClass("textCenter");
						style["marginLeft"] = "1.2%";
						$.tmpl(tplPortletLayoutItem, style).appendTo($divLayout).append($.tmpl(tplPortletLayoutInput, style)).addClass("textCenter");
						$.tmpl(tplPortletLayoutItem, style).appendTo($divLayout).append($.tmpl(tplPortletLayoutInput, style)).addClass("textCenter");
						$.tmpl(tplPortletLayoutItem, style).appendTo($divLayout).append($.tmpl(tplPortletLayoutInput, style)).addClass("textCenter");
						$("span", "#lblLayoutWidth").eq(0).html("3.6%");
						break;
				}
				
				var $rows = $("tr" ,"#tblLauoutInfo");
				
				$rows.eq(2).children(":eq(1)").html($(this).val());
				$divLayout.append('<div class="clear"/>');
			});
			
			$("#btnDialogLayoutControlCancel").click(function() {
				$("#dialogLayoutControl").dialog("close");
			});
			$("#btnDialogLayoutControlAccept").click(function() {
				var layoutWidths = [], widths = [];	//{width, margin}
				var $dialogLayouts = $("#divLayout").children("div:not(.clear)");
				$("input[name^=width]", $fmLayout).each(function(idx) {
					var width = parseFloat($(this).val(), 10) || 0;
					widths.push(width);
					layoutWidths.push({ width:width, margin:parseFloat($dialogLayouts.eq(idx)[0].style.marginLeft, 10) || 0 });
				});

				if(!layoutInfo.equal(widths)) {
					var margin = 0, size = 0;
					$.each(layoutWidths, function() {
						margin += this.margin;
						size += this.width;
					});
					
					if((margin + size) != 100) {
						alert("<ikep4j:message pre="${prefix}" key="layout.alert.sizeMessage" />");
						return false;
					}
					
					if(!isCommonLayout) {
						if($("div.maximize").children("div").length > 0) {
							var caption = $("div.maximize").children().eq(0);
							var $iconBar = $("div.po_icon", caption);
							
							$("li.po_icon_restore", $iconBar).trigger("click");
						}
					}
					
					var areas = portletManager.getOptions(isCommonLayout ? "commonSortAreas" : "sortAreas");
					switch(true) {
						case areas.length > layoutWidths.length :	// 삭제
							for(var i=layoutWidths.length, length=areas.length; i<length; i++) {
								$(areas.pop()).trigger("layout:remove").remove();
							}
							break;
						case areas.length < layoutWidths.length :	// 추가
							var lastLayout = $(areas[areas.length-1]);
							var layoutSimbol = isCommonLayout ? "commonPortlet_" : "portlet_";
							for(var i=areas.length, length=layoutWidths.length; i<length; i++) {
								var layoutId = layoutSimbol+(i+1);
								areas.push("#"+layoutId);
								lastLayout = lastLayout.after($.tmpl(tplPortletLayout).attr("id", layoutId).css("background-color", "").addClass("ui-sortable")).next();
								portletManager.setLayoutRemoveEvent(lastLayout);
							}
							break;
					}

					
					$.each(areas, function(idx) {
						$(this.toString()).css({width:layoutWidths[idx].width+"%", marginLeft:layoutWidths[idx].margin+"%", minHeight:"100px"});
					});
					
					portletManager.resetSortable(isCommonLayout);
					
					var $rows = $("tr" ,"#tblLauoutInfo");
					var $selIndex = $rows.eq(0).find("select option").index($rows.eq(0).find("select option:selected"));
					var layoutIds = []; 
					
					if(isCommonLayout) {
						<c:forEach var="commonLayout" items="${commonLayoutList}" varStatus="i">
							layoutIds.push('${commonLayout.layoutId}');
						</c:forEach>
						
						$("input[name=commonLayoutId]").val(layoutIds[$selIndex]);
					} else {
						<c:forEach var="layout" items="${layoutList}" varStatus="i">
							layoutIds.push('${layout.layoutId}');
						</c:forEach>
						
						$("input[name=defLayoutId]").val(layoutIds[$selIndex]);
					}
				}
				
				$('#btnDialogLayoutControlCancel').click();
			});
			
			isSetDialog = true;
		}
	});
})(jQuery);

var commonSortAreas = [
<c:forEach var="commonPageLayout" items="${commonPageLayoutList}" varStatus="i">
	"#commonPortlet_${i.count}"
	<c:if test="${!i.last}">,</c:if>
</c:forEach>
];

var sortAreas = [
<c:forEach var="defPageLayout" items="${defPageLayoutList}" varStatus="i">
	"#portlet_${i.count}"
	<c:if test="${!i.last}">,</c:if>
</c:forEach>
];

<c:set var="rowCount" value="0" />
<c:set var="colIndexChk" value="0"/>
var activePortlets = [
<c:forEach var="defPortlet" items="${defPortletList}" varStatus="i">
	<c:choose>
		<c:when test="${defPortlet.colIndex == colIndexChk}">
			<c:set var="rowCount" value="${rowCount + 1}" />
		</c:when>
		<c:otherwise>
			<c:set var="rowCount" value="0" />
			<c:set var="colIndexChk" value="${defPortlet.colIndex}"/>
		</c:otherwise>
	</c:choose>
	{ id:"${defPortlet.portletId}", 
      portletConfigId:"${defPortlet.defaultPortletConfigId}", 
      layout:"#portlet_${defPortlet.colIndex}", 
      seq:${rowCount}, 
      viewMode: "${defPortlet.viewMode}",
      headerMode: "${defPortlet.headerMode}"}<c:if test="${!i.last}">,</c:if>
</c:forEach>
];

<c:set var="rowCount" value="0" />
<c:set var="colIndexChk" value="0"/>
var commonPortlets = [
<c:forEach var="commonPortlet" items="${commonActivePortletList}" varStatus="i">
	<c:choose>
		<c:when test="${commonPortlet.colIndex == colIndexChk}">
			<c:set var="rowCount" value="${rowCount + 1}" />
		</c:when>
		<c:otherwise>
			<c:set var="rowCount" value="0" />
			<c:set var="colIndexChk" value="${commonPortlet.colIndex}"/>
		</c:otherwise>
	</c:choose>
	{ id:"${commonPortlet.portletId}", 
	  portletConfigId:"${commonPortlet.portletConfigId}", 
	  layout:"#commonPortlet_${commonPortlet.colIndex}", 
	  seq:${rowCount}, 
	  viewMode: "${commonPortlet.viewMode}",
	  headerMode: "${commonPortlet.headerMode}"}<c:if test="${!i.last}">,</c:if>
</c:forEach>
];

var portletGroups = [
	{code:"ALL", name:"ALL", desc:"all"}
<c:forEach var="portletCategory" items="${portalPortletCategoryList}" varStatus="i">
 	,{code:"${portletCategory.portletCategoryId}", 
     name:"${portletCategory.portletCategoryName}", 
     desc:"${portletCategory.description}"}
</c:forEach>	
];

var portlets = [
<c:forEach var="portlet" items="${portalPortletList}" varStatus="i">
	{id:"${portlet.portletId}", 
	 groupId:"${portlet.portletCategoryId}", 
	 title:"${portlet.portletName}", 
	 constructor:${portlet.linkType}, 
	 area:"#portlet_${portlet.defaultColIndex}",
	 multipleMode:"${portlet.multipleMode}",
	 headerMode: "${portlet.headerMode}",
	 publicOption: "${portlet.publicOption}",
	 //moveable: "${portlet.moveable}",
	 moveable: true, // 관리자는 항상 포틀릿의 이동이 가능하도록 함.
	 options:{title:"${portlet.portletName}",
			  <c:if test="${!empty portlet.normalViewUrl}">url:"<c:url value='${portlet.normalViewUrl}'/>",</c:if>
			  <c:if test="${portlet.linkType eq 'PortletIFrameExt'}">iframeHeight:"<c:url value='${portlet.iframeHeight}'/>",</c:if>
		 	  icons:{
		 		<c:choose>
		 			<c:when test="${portlet.publicOption == 0}">
				 		<c:if test="${portlet.reloadMode == 1}">
				 		  reload:true,
				 		</c:if>
				 		<c:choose>
			 				<c:when test="${portlet.moreMode == 1}">
			 					more:{url:"<c:url value='${portlet.moreViewUrl}'/>"},
			 				</c:when>
			 				<c:otherwise>
			 					more:false,
			 				</c:otherwise>
			 			</c:choose>
		 		 		<c:choose>
			 				<c:when test="${portlet.maxMode == 1}">
			 					maximize:{url:"<c:url value='${portlet.maxViewUrl}'/>"},
			 				</c:when>
			 				<c:otherwise>
			 					maximize:false,
			 				</c:otherwise>
			 			</c:choose>
			 		</c:when>
			 		<c:otherwise>
			 			minimize:false,
			 			restore:false,
			 		</c:otherwise>
			 	</c:choose>
		 		<c:if test="${portlet.removeMode == 1}">
	 				close:true,
	 			</c:if>
		 		<c:choose>
	 				<c:when test="${portlet.configMode == 1}">
	 					config:{url:"<c:url value='${portlet.configViewUrl}'/>"}
	 				</c:when>
	 				<c:otherwise>
	 					config:false
	 				</c:otherwise>
	 			</c:choose>
	 		  }
  		}, 
 	imagePath:"${portlet.previewImageId}",
 	desc:"${portlet.portletName}"}
	<c:if test="${!i.last}">,</c:if>
</c:forEach>     
];
//-->
</script>