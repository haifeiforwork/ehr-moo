<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preAlert" value="message.portal.admin.screen.quickmenu.portalQuickMenuMain.alert"/>		
<c:set var="preMain" value="ui.portal.admin.screen.quickmenu.portalQuickMenuMain.main"/>
<c:set var="preLabel" value="ui.portal.admin.screen.quickmenu.portalQuickMenuMain.label"/>	
<c:set var="preForm" value="ui.portal.admin.screen.quickmenu.portalQuickMenuMain.form"/>
<c:set var="preButton" value="ui.portal.admin.screen.quickmenu.portalQuickMenuMain.button"/>

<script type="text/javascript"> 
//<![CDATA[
var currQuickMenuId = "";

/* get menu info detail */
var $currItem = null;
var menuItemAction = "add"; // modify

(function($) {
	var menuBoxWidth = 0;
	
	$jq(document).ready(function() {
		
		//left menu setting
		$jq("#quickMenuLinkOfLeft").click();
		
		$jq("#portalQuickMenuForm:input:visible:enabled:first").focus();
		
		menuBoxWidth = $(".menuguide").width();
		
		var startIndex = -1;
		
		$jq("#topMenuList").sortable({
			placeholder: "ui-state-highlight",
			forcePlaceholderSize:true,
			cancel: '.subMenuList li',
			start : function(event, ui) {
				startIndex = ui.item.parent().children().index(ui.item);
			},
			update:function(event, ui){
				var $quickMenuItems = ui.item.parent().children();			
				var moveIndex = $quickMenuItems.index(ui.item);
				var quickMenuUrl = "";
				
				if(startIndex > moveIndex) {
					quickMenuUrl = "<c:url value='movePrevPortalQuickMenu.do' />";
				} else if(startIndex < moveIndex) {
					quickMenuUrl = "<c:url value='moveNextPortalQuickMenu.do' />";
				} else {
					return;
				}
				
				$jq.ajax({
					url : quickMenuUrl,
					data : {
						startIndex : startIndex,
						moveIndex : moveIndex,
						parentMenuId : ""
					},
					type : "post",
					success : function(result) {
						
					},
					error : function(event, request, settings) { 
						alert("<ikep4j:message pre='${preAlert}' key='moveFail' />");	
						
						$jq("#topMenuList").sortable("cancel");
					}
				});
			}
		}).disableSelection();
		
		$jq("#topMenuList > li > a").live("mouseover", 
			  function () {		
				hideConfigImg();
			    $jq(this).parent().children(".menuguide_resize").show();		   
			  }			 		
		);
				
		$jq("#topMenuList > li > ul > li > a").live("mouseover", 
			  function () {	
				hideConfigImg();
			    $jq(this).parent().children(".menuguide_resize").show();				
			  }				 		
		);		
		
		// init sub menu sortable 
		subMenuSortable();
		
		//setMenuForm();
		
		iKEP.loadSecurity("Portal-QuickMenu", "${portalQuickMenu.quickMenuId}", "READ", "User,Group,Role,Job,Duty,ExpUser");
	});	
	
	show_layer = function () {
// 		iKEP.showDialog({
// 		    title : "<ikep4j:message pre='${preLabel}' key='iconPreview' />",
// 		    url : "<c:url value='/admin/screen/quickmenu/portalQuickMenuIconPreView.do'/>",
// 		    width : 510, 
// 		    height : 250,
// 		    modal : true, 
// 		    resizable : false
// 		});

		var classValue = $jq("#iconId").val();
		var noImage = '<img src="<c:url value="/base/images/common/noimage_noImage.gif"/>" alt="noImage"/>';
		
		$jq("#previewArea").show();
		$jq("#previewImage").css("background-image", "");
		$jq("#previewImage").removeClass();
		$jq("#previewImage").addClass(classValue);
		
		if($jq("#previewImage").css("background-image") == "none") { 
			$jq("#previewImage").removeClass();
			$jq("#classId").html(classValue);
			$jq("#previewImage").html(noImage);
		} else { 
			$jq("#previewImage").removeClass();
			$jq("#previewImage").html("");
			$jq("#classId").html(classValue);
			$jq("#previewImage").addClass(classValue);
		}
		
	};
	
	displayInputValue = function(value) {
		
		if(value == 1) { 
			$jq("#intervalTimeTr").show();
			$jq("#countUrlTr").show();
		} else if(value == 0){
			$jq("#intervalTimeTr").hide();
			$jq("#countUrlTr").hide();
		}
		
	};

	popupSystemUrl = function () {
		
		var url = "<c:url value='/admin/screen/systemurl/popupPortalSystemUrlList.do' />";
		
		iKEP.popupOpen(url, {width:800, height:510}, "PortalSystemUrl");
		
	};
	
	getUrlValue = function() {
		
		$jq("input[name=moreUrl]").val($jq("input[name=urlInput]").val());
		
	};

	selfInputCheck = function () {
		
		if($jq("#selfInput").is(":checked")) {
			$jq("#moreUrl").attr("readonly", false);
		} else {
			$jq("#moreUrl").val("");
			$jq("#moreUrl").attr("readonly", true);
		}
		
	};
	
	/* hide other config img */
	hideConfigImg = function(){
		$jq("#topMenuList > li").children(".menuguide_resize").hide();
		$jq("#topMenuList > li > ul > li").children(".menuguide_resize").hide();
	};
	
	/* sub menu sortable event add */
	makeSubMenuSortable = function(idx){
		var useRevert = true;
		if($jq.browser.msie){
			useRevert = false;
		}
		
		$jq("#"+idx).sortable({
			placeholder: "ui-state-highlight",
			connectWith: ".subMenuList",
			forcePlaceholderSize:true,
			opacity:0.6,
			revert:useRevert,
			update:function(event, ui){
				//iKEP.debug(ui);
			}
		}).disableSelection();
	};
	
	/* sub menu sortable event add */
	subMenuSortable = function(){
		
		var $li = $("#topMenuList > li");
		
		$li.each(function(index, item) {
			makeSubMenuSortable($jq(item).children("ul").attr("id"));
		});
		
	};
	
	/* menu box resize(width) */
	outBoxResize = function(action){
		
		var $ul = $jq("#topMenuList");
		var li_cnt = $ul.children("li").length;
		var ulWidth = 125+(li_cnt*135);
		
		var $outBox = $(".menuguide");
		var $inBox = $(".menuguide_con");
		var boxWidth = $inBox.width();				
		
		if(action == "add"){
			if(ulWidth > menuBoxWidth){
				$inBox.width(boxWidth+150);
			}
		}else{
			if(ulWidth > menuBoxWidth){
				$inBox.width(boxWidth-150);
			}
		}	
		
		if($inBox.width()>$outBox.width()){
			$jq(".blockBox").width($inBox.width()+5);
		}
		
	};
	
	/* sub main menu add */
	addSubMenu = function(){
		
		//$jq("#menuDetailDiv").empty();
		
		var $ul = $jq("#topMenuList");
		var $li = $jq('<li/>').appendTo($ul).addClass("selected").append('<a href="#a">New Menu</a>');
		
		menuItemAction = "add";
		$currItem = $li;
		
		$jq(".blockBox").show();
		getMenuInfo();
		
	};
	
	/* 원복 */
	restoreMenu = function() {		
		if(menuItemAction == "add") {
			$currItem.remove();
			outBoxResize("remove");
		}
		$jq(".blockBox").hide();
	};
	
	/* 취소 */
	cancelMenu = function() {
		//location.reload();
		if(menuItemAction == "add") {
			$currItem.remove();
			outBoxResize("remove");
		}else{
			$currItem.removeClass("selected");	
		}		
		$jq(".blockBox").hide();
		//$jq("#menuDetailDiv").empty();
		
		setMenuForm();
		
		menuItemAction = "add";
		
	};
	
	/* save, modify success after */
	saveAfter = function(id) {		
			
		//$jq(".blockBox").hide();
		//$jq("#menuDetailDiv").empty();
		
		currQuickMenuId = id;
		menuItemAction = "modify";
		
		getMenuInfo();
		
	};
	
	/* modify */
	modifyMenu = function(el, id) {
		var $menu = $jq(el).parents("li:first");		
		$currItem = $menu;		
		$menu.addClass("selected");
		menuItemAction = "modify";
		currQuickMenuId = id;
		
		$jq(".blockBox").show();
		
		getMenuInfo();		
	};
	
	/* remove */
	removeMenu = function(id) {		
		//var $menu = $(el).parents("li:first");	
		var menuUrl = "<c:url value='portalQuickMenuDelete.do'/>";
				
		if(confirm("<ikep4j:message pre='${preAlert}' key='confirmDelete' />")) { 
		
			$jq.ajax({
				url : menuUrl,
				data : {
					quickMenuId : id
				},
				type : "post",
				success : function(result) {
					alert("<ikep4j:message pre='${preAlert}' key='deleteSuccess' />");	
					
					//$menu.remove();
					$jq("#quickMenu_"+id).remove();
					outBoxResize("remove");
					$jq(".blockBox").hide();
					//$jq("#menuDetailDiv").empty();
					setMenuForm();
					
					//restoreMenu();
				},
				error : function(event, request, settings) {
					alert("<ikep4j:message pre='${preAlert}' key='deleteFail' />");	
					
				}
			});	
		}
		
	};			
	
	getMenuInfo = function() {
		
		//$jq(".blockBox").show();
		$currItem.children(".menuguide_resize").hide();
		
		//var $target = $jq("#menuDetailDiv");
		var menuUrl = "<c:url value='portalQuickMenuForm.do'/>";
		//var parms = "action="+menuItemAction;
		
		//$target.empty();
		
		switch(menuItemAction) {
			case "add" :
				
				//ajax : new add Menu				
				$jq.ajax({
					url : menuUrl,
					data : {
						action : "add"
					},
					type : "post",
					success : function(result) {
						//$target.append(result);
						$jq("#menuDetailDiv").html(result);
						//$("input[name=menuName]").focus();
					},
					error : function(event, request, settings) { 
						restoreMenu(); // 복원
						alert("error");	
						
					}
				});	
					
				// ajax success callback process
				
				break;
				
			case "modify" :
				
				//ajax : Menu info modify form				
				$jq.ajax({
					url : menuUrl,
					data : {
						action : "modify",
						quickMenuId : currQuickMenuId
					},
					type : "post",
					success : function(result) {
						//$target.append(result);
						$jq("#menuDetailDiv").html(result);
					},
					error : function(event, request, settings) { 
						restoreMenu(); // 복원
						alert("error");	
					}
				});	
				
				// ajax success callback process
				
				break;
		}
		
	};
	
	/* menu save */
	saveMenu = function() {
		
		switch(menuItemAction) {
			case "add" :
				$jq("input[name=quickMenuName]").val($jq("input[id=${userLocaleCode}]").val());
				
				if($jq("input[name='count']:radio:checked").val() == 0) {
					$jq("input[name=intervalTime]").val("0");
					$jq("input[name=countUrl]").val("");
				}
				
				$jq("#portalQuickMenuForm").trigger("submit");
				
				break;
				
			case "modify" :
				$jq("input[name=quickMenuName]").val($jq("input[id=${userLocaleCode}]").val());
				
				if($jq("input[name='count']:radio:checked").val() == 0) {
					$jq("input[name=intervalTime]").val("0");
					$jq("input[name=countUrl]").val("");
				}
				
				$jq("#portalQuickMenuForm").trigger("submit");
				
				break;
		}
		
	};
	
	setMenuForm = function() {
		
		//var $target = $jq("#menuDetailDiv");
		var menuUrl = "<c:url value='portalQuickMenuForm.do'/>";
		
		//$target.empty();
		
		$jq.ajax({
			url : menuUrl,
			data : {
				action : "cancel"
			},
			type : "post",
			success : function(result) {
				//$target.append(result);
				$jq("#menuDetailDiv").html(result);
			},
			error : function(event, request, settings) { 
				alert("error");	
				
			}
		});		
		
	};
	
	iconPreviewClose = function() {
		
		$jq("#previewArea").hide();
		
	};
	
})(jQuery);
//]]>
</script>
 
<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preMain}" key="pageTitle" /></h2>
</div>
<!--//pageTitle End-->

<!--contentsMenu Start-->				
<div class="quickicon_reg">
				
	<div class="quickicon_regLeft">
		<!--contentsMenu Start-->				
		<div class="menuguide" style="overflow:hidden;">
			<div class="blockBox" style="display:none !important;"></div>
			<div class="menuguide_con">
				<div class="quickGuide_group">
					<ul class="quickGuide_list" id="topMenuList">
						
						<c:forEach var="quickMenu" items="${portalQuickMenuList}" varStatus="status">
						<li id="quickMenu_${quickMenu.quickMenuId}">
							<a href="#" onclick="modifyMenu(this, '<c:out value='${quickMenu.quickMenuId}'/>'); return false;">
								<c:out value="${quickMenu.quickMenuName}"/>
							</a>
						</li>
						</c:forEach>
						
					</ul> 
					<div class="addSubMenu_3">
						<span>
							<a href="#" onclick="addSubMenu(); return false;">
								Add Menu <img src="<c:url value='/base/images/icon/ic_arb_down_2.gif'/>" alt="" />
							</a>
						</span>
					</div>
				</div>
			</div>			
		</div>
		<!--//contentsMenu End-->
		
	</div>
	
	<div class="quickicon_regRight">
		<!--blockListTable Start-->
		<div id="menuDetailDiv">
			<form name="portalQuickMenuForm" id="portalQuickMenuForm" action="" method="post" >
			<input type="hidden" id="quickMenuId" name="quickMenuId" value="<c:out value='${portalQuickMenu.quickMenuId}'/>" />
			<input type="hidden" id="sortOrder" name="sortOrder" value="<c:out value='${portalQuickMenu.sortOrder}'/>" />
			<input type="hidden" id="urlInput" name="urlInput" value="" />
			<input type="hidden" id="quickMenuName" name="quickMenuName" value="" />
			 
			<div class="blockDetail">
				<table id="mainTable" summary="<ikep4j:message pre='${preForm}' key='tableSummary' />">
					<caption></caption>
					<colgroup>
						<col width="10%"/>
						<col width="8%"/>
						<col width="82%"/>
					</colgroup>
					<tbody>
						<!--quickMenuName Start-->
						<tr>
							<th scope="row" rowspan="${localeSize}">
								<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="iconName" />
							</th>
							<c:forEach var="i18nMessage" items="${portalQuickMenu.i18nMessageList}" varStatus="loopStatus">
							<c:if test="${i18nMessage.fieldName eq 'quickMenuName' }">
							<c:if test="${i18nMessage.index > 1}">
						<tr>
							</c:if>
							<th scope="row">
								<span class="colorPoint">*</span><c:out value="${i18nMessage.localeCode}"/>
							</th>
							<td>
								<div>
								<spring:bind path="portalQuickMenu.i18nMessageList[${loopStatus.index}].itemMessage">
									<input type="text" id="${i18nMessage.localeCode}" name="i18nMessageList[${loopStatus.index}].itemMessage" value="${i18nMessage.itemMessage}" class="inputbox w100" />
									<input type="hidden" name="i18nMessageList[${loopStatus.index}].localeCode" value="${i18nMessage.localeCode}" />
									<input type="hidden" name="i18nMessageList[${loopStatus.index}].fieldName" value="${i18nMessage.fieldName}" />
									<input type="hidden" name="i18nMessageList[${loopStatus.index}].messageId" value="${i18nMessage.messageId}" />
								</spring:bind>
								</div>
							</td>
						</tr>
							</c:if>
							</c:forEach>
						<!--//quickMenuName End-->
						
						<!--moreUrlType Start-->
						<tr>
							<th colspan="2" scope="row">
								<ikep4j:message pre="${preForm}" key="moreUrlType" />
							</th>
							<td>
								<input type="radio" class="radio" title="<ikep4j:message pre='${preForm}' key='url' />" id="moreUrlTypeUrl" name="moreUrlType" value="URL" <c:choose><c:when test="${empty portalQuickMenu.quickMenuId}">checked="checked"</c:when><c:when test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.moreUrlType == 'URL'}">checked="checked"</c:when><c:otherwise></c:otherwise></c:choose> />
								<label for="moreUrlTypeUrl"><ikep4j:message pre="${preForm}" key="url" /></label>&nbsp;
								<input type="radio" class="radio" title="<ikep4j:message pre='${preForm}' key='javascript' />" id="moreUrlTypeJavascript" name="moreUrlType" value="JAVASCRIPT" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.moreUrlType == 'JAVASCRIPT'}">checked="checked"</c:if> />
								<label for="moreUrlTypeJavascript"><ikep4j:message pre="${preForm}" key="javascript" /></label>
							</td>
						</tr>
						<!--//moreUrlType End-->
						
						<!-- moreUrl Start -->
						<tr>
							<th colspan="2" scope="row">
								<ikep4j:message pre="${preForm}" key="moreUrl" />
							</th>
							<td>
								<div>
									<input type="text" id="moreUrl" name="moreUrl" class="inputbox w70" title="More URL" value="${portalQuickMenu.moreUrl}" readonly="readonly" />&nbsp;
									<input type="checkbox" id="selfInput" name="selfInput" class="checkbox valign_middle" title="<ikep4j:message pre='${preForm}' key='directInput' />" value="" onclick="selfInputCheck();" /><ikep4j:message pre="${preForm}" key="directInput" />&nbsp;
									<a href="#" class="button_s" onclick="popupSystemUrl(); return false;"><span><ikep4j:message pre="${preButton}" key="example" /></span></a>
								</div>
								<div class="tdInstruction">
									<ikep4j:message pre="${preForm}" key="moreUrlDesc" />
								</div>
							</td>
						</tr>
						<!--// moreUrl End -->
						
						<!--target Start-->
						<tr>
							<th colspan="2" scope="row">
								<ikep4j:message pre="${preForm}" key="target" />
							</th>
							<td>
								<input type="radio" id="targetInner" name="target" class="radio" title="<ikep4j:message pre='${preForm}' key='inner' />" value="INNER" <c:choose><c:when test="${empty portalQuickMenu.quickMenuId}">checked="checked"</c:when><c:when test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.target == 'INNER'}">checked="checked"</c:when><c:otherwise></c:otherwise></c:choose> />
								<label for="targetInner"><ikep4j:message pre="${preForm}" key="inner" /></label>&nbsp;
								<input type="radio" id="targetWindow" name="target" class="radio" title="<ikep4j:message pre='${preForm}' key='window' />" value="WINDOW" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.target == 'WINDOW'}">checked="checked"</c:if> />
								<label for="targetWindow"><ikep4j:message pre="${preForm}" key="window" /></label>
							</td>
						</tr>
						<!--//target End-->
						
						<!--count Start-->
						<tr>
							<th colspan="2" scope="row">
								<span class="colorPoint">*</span><ikep4j:message pre="${preForm}" key="count" />
							</th>
							<td>
								<input type="radio" id="countYes" name="count" class="radio" title="<ikep4j:message pre='${preForm}' key='yes' />" value="1" <c:choose><c:when test="${empty portalQuickMenu.quickMenuId}">checked="checked"</c:when><c:when test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.count == 1}">checked="checked"</c:when><c:otherwise></c:otherwise></c:choose> onclick="displayInputValue(1);" />
								<label for="countYes"><ikep4j:message pre="${preForm}" key="yes" /></label>&nbsp;
								<input type="radio" id="countNo" name="count" class="radio" title="<ikep4j:message pre='${preForm}' key='no' />" value="0" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.count == 0}">checked="checked"</c:if> onclick="displayInputValue(0);" />
								<label for="countNo"><ikep4j:message pre="${preForm}" key="no" /></label>
								<div class="tdInstruction">
									<ikep4j:message pre="${preForm}" key="countDesc" />
								</div>
							</td>
						</tr>
						<!--//count End-->
						
						<!--intervalTime Start-->
						<tr id="intervalTimeTr" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.count == 0}">style="display:none;"</c:if>>
							<th colspan="2" scope="row">
								<ikep4j:message pre="${preForm}" key="intervalTime" />
							</th>
							<td>
								<select id="intervalTime" name="intervalTime">
									<option value="0" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.intervalTime == 0}">selected="selected"</c:if>><ikep4j:message pre="${preForm}" key="noUse" /></option>
									<option value="1" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.intervalTime == 1}">selected="selected"</c:if>>1<ikep4j:message pre="${preForm}" key="min" /></option>
									<option value="2" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.intervalTime == 2}">selected="selected"</c:if>>2<ikep4j:message pre="${preForm}" key="min" /></option>
									<option value="3" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.intervalTime == 3}">selected="selected"</c:if>>3<ikep4j:message pre="${preForm}" key="min" /></option>
									<option value="4" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.intervalTime == 4}">selected="selected"</c:if>>4<ikep4j:message pre="${preForm}" key="min" /></option>
									<option value="5" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.intervalTime == 5}">selected="selected"</c:if>>5<ikep4j:message pre="${preForm}" key="min" /></option>
								    <option value="10" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.intervalTime == 10}">selected="selected"</c:if>>10<ikep4j:message pre="${preForm}" key="min" /></option>
								    <option value="15" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.intervalTime == 15}">selected="selected"</c:if>>15<ikep4j:message pre="${preForm}" key="min" /></option>
								    <option value="30" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.intervalTime == 30}">selected="selected"</c:if>>30<ikep4j:message pre="${preForm}" key="min" /></option>
								</select>
							</td>
						</tr>
						<!--//intervalTime End-->
						
						<!--countUrl Start-->
						<tr id="countUrlTr" <c:if test="${!empty portalQuickMenu.quickMenuId && portalQuickMenu.count == 0}">style="display:none;"</c:if>>
							<th colspan="2" scope="row">
								<ikep4j:message pre="${preForm}" key="countUrl" />
							</th>
							<td>
								<div>
									<input id="countUrl" name="countUrl" type="text" class="inputbox w100" title="<ikep4j:message pre='${preForm}' key='countUrl' />" value="${portalQuickMenu.countUrl}" />
								</div>
							</td>
						</tr>
						<!--//countUrl End-->
						
						<!--iconId Start-->
						<tr>
							<th colspan="2" scope="row">
								<ikep4j:message pre="${preForm}" key="iconId" />
							</th>
							<td>	
								<div>				
									<input id="iconId" name="iconId" type="text" class="inputbox w70" title="<ikep4j:message pre='${preForm}' key='iconId' />" value="${portalQuickMenu.iconId}" />
									<a href="#" class="button_s" onclick="show_layer(); return false;"><span><ikep4j:message pre="${preButton}" key="preview" /></span></a>
									<!--layer start-->
									<div id="previewArea" class="process_layer" style="position:absolute; min-width:120px; margin-top:10px; z-index:99; display:none;">
										<div class="process_layer_t">
											<ikep4j:message pre="${preButton}" key="preview" />
											<a href="#" id="btnPreviewClose" onclick="iconPreviewClose(); return false;"><img src="<c:url value='/base/images/icon/ic_close_layer.gif'/>" alt="Close" />
											</a>
										</div>
										<div class="subTitle_2 noline" style="padding:1px 5px;">
											<h3><ikep4j:message pre="${preForm}" key="classId" /> : <span id="classId"></span></h3>
										</div>
										<div id="previewDiv" style="text-align:center; vertical-align:middle; padding:0px 5px 2px 5px;">	
											<ul>
												<li>							
													<a id="previewImage" href="#" onclick="return false;" class=""></a>
												</li>
											</ul>
										</div>
									</div>
									<!--layer end-->
								</div>
								<div class="tdInstruction">
									<ikep4j:message pre="${preForm}" key="iconIdDesc" />
								</div>
							</td>
						</tr>
						<!--//iconId End-->
																																							
					</tbody>
				</table>
			</div>
			<!--//blockDetail End-->
			
			</form>
		</div>
		<!--//blockDetail End-->
	</div>
		
</div>	
<!--//contentsMenu End-->	