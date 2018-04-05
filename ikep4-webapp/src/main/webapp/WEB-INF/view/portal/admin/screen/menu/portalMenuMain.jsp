<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preAlert" value="message.portal.admin.screen.menu.portalMenuMain.alert"/>		
<c:set var="preMain" value="ui.portal.admin.screen.menu.portalMenuMain.main"/>

<script type="text/javascript"> 
//<![CDATA[
var currMenuId = "";
var currParentMenuId = "";

/* get menu info detail */
var $currItem = null;
var menuItemAction = "add"; // modify
var addMenuKind = "main";

var mainMenuItemMargin = 10;

(function($) {
	
	var menuBoxWidth = 0;
	
	$jq(document).ready(function() {
		
		//left menu setting
		$jq("#menuLinkOfLeft").click();
		
		/*
		var $ul = $("#topMenuList");
		var li_cnt = $ul.children("li").length;
		var ulWidth = 245+(li_cnt*135);
		
		menuBoxWidth = ulWidth;*/
		menuBoxWidth = $jq(".menuguide").width();
		
		var startIndex = -1;
		
		$jq("#topMenuList").sortable({
			placeholder: "ui-state-highlight",
			forcePlaceholderSize:true,
			cancel: '.subMenuList li',
			start : function(event, ui) {
				startIndex = ui.item.parent().children().index(ui.item);				
			},
			update:function(event, ui){
				var $menuItems = ui.item.parent().children();			
				var moveIndex = $menuItems.index(ui.item);
				var menuUrl = "";
				
				if(startIndex > moveIndex) {
					menuUrl = "<c:url value='movePrevPortalMenu.do' />";
				} else if(startIndex < moveIndex) {
					menuUrl = "<c:url value='moveNextPortalMenu.do' />";
				} else {
					return;
				}
				
				$jq.ajax({
					url : menuUrl,
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
		
		setBoxSize();
		
	});	
	
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
		
		var startIndex = -1;
		var startParent = null;
		var updateParent = null;
		
		$jq("#"+idx).sortable({
			placeholder: "ui-state-highlight",
			connectWith: ".subMenuList",
			forcePlaceholderSize:true,
			opacity:0.6,
			revert:useRevert,
			start : function(event, ui) {
				startIndex = ui.item.parent().children().index(ui.item);		
				startParent = ui.item.parent().attr("id");
			},
			update:function(event, ui){
				updateParent = ui.item.parent().attr("id");
				$sender = ui.sender;
				
				if($sender == null && startParent == updateParent) {
					var parentMenuId = ui.item.parent().attr("id");
					var $menuItems = ui.item.parent().children();			
					var moveIndex = $menuItems.index(ui.item);
					var menuUrl = "";
					
					parentMenuId = parentMenuId.replace(/subMenuList_/, "");
					
					if(startIndex > moveIndex) {
						menuUrl = "<c:url value='movePrevPortalMenu.do' />";
					} else if(startIndex < moveIndex) {
						menuUrl = "<c:url value='moveNextPortalMenu.do' />";
					} else {
						return;
					}
					
					$jq.ajax({
						url : menuUrl,
						data : {
							startIndex : startIndex,
							moveIndex : moveIndex,
							parentMenuId : parentMenuId
						},
						type : "post",
						success : function(result) {
							
						},
						error : function(event, request, settings) { 
							alert("<ikep4j:message pre='${preAlert}' key='moveFail' />");	
							
							$jq("#"+idx).sortable("cancel");
						}
					});
				} else if($sender != null) {
					var parentMenuId = ui.item.parent().attr("id");
					var menuId = ui.item.attr("id");
					var $menuItems = ui.item.parent().children();			
					var moveIndex = $menuItems.index(ui.item);
					var menuUrl = "<c:url value='moveOtherPortalMenu.do' />";
					
					parentMenuId = parentMenuId.replace(/subMenuList_/, "");
					menuId = menuId.replace(/menu_/, "");
					
					$jq.ajax({
						url : menuUrl,
						data : {
							moveIndex : moveIndex,
							parentMenuId : parentMenuId,
							menuId : menuId
						},
						type : "post",
						success : function(result) {
							
						},
						error : function(event, request, settings) { 
							alert("<ikep4j:message pre='${preAlert}' key='moveFail' />");	
							
							$jq("#"+idx).sortable("cancel");
						}
					});
				}
			}
		}).disableSelection();
	};
	
	/* sub menu sortable event add */
	subMenuSortable = function(){
		
		var $li = $jq("#topMenuList > li");
		
		$li.each(function(index, item) {
			makeSubMenuSortable($(item).children("ul").attr("id"));
		});
		
	};
	
	getContainerWidth = function() {
		var width = 0;
		$("#topMenuList").children("li").each(function() {
			width += $jq(this).outerWidth() + mainMenuItemMargin;	// menu item size
		});
		width += $("#topMenuList").next().outerWidth();	// add button size
		
		return width;
	};
	
	setBoxSize = function(size) {
		var width = size || getContainerWidth();

		$(".menuguide_con").width(width);
	};
	
	/* menu box resize(width) */
	outBoxResize = function(){
		var width = getContainerWidth() + (mainMenuItemMargin/2);
		setBoxSize(width);	
		
// 		if(width > $(".menuguide").width()){
 			$jq(".blockBox").width(width + (mainMenuItemMargin/2));
// 		}
	};
	
	/* main menu add */
	addMenu = function(){
		
		var $ul = $jq("#topMenuList");
		var li_cnt = $ul.children("li").length+1;
		var idx = "subMenuList"+li_cnt;
		
		var modifyImg = "<c:url value='/base/images/icon/ic_cir_modify.png'/>";
		var deleteImg = "<c:url value='/base/images/icon/ic_cir_minus.png'/>";
		var downArrowImg = "<c:url value='/base/images/icon/ic_arb_down_2.gif'/>";
		
		var resizeDiv = '<div class="menuguide_resize none">'
					  + '<div class="ic_rt"><a href="#" onclick="return false;"><img src="'+modifyImg+'" alt="modify"/></a></div>'
					  + '<div class="ic_rb"><a href="#" onclick="return false;"><img src="'+deleteImg+'" alt="delete" /></a></div>'
					  + '</div>';
		
		var $li = $('<li/>').appendTo($ul).addClass("selected")
				.append('<a href="#" onclick="return false;">New Menu</a>')
				.append(resizeDiv)
				.append('<ul id="'+idx+'" class="subMenuList"/>')
				.append('<div class="addSubMenu"><span><a href="#" onclick="addSubMenu('+li_cnt+'); return false;">Add Menu <img src="'+downArrowImg+'" alt="" /></a></span></div>')
				.end();
		
		outBoxResize();
	
		makeSubMenuSortable(idx);		

		menuItemAction = "add";
		addMenuKind = "main";
		$currItem = $li;
		currParentMenuId = "";
		
		getMenuInfo();
		
		$("div.menuguide").scrollLeft(getContainerWidth());
		
	};
	
	/* sub main menu add */
	addSubMenu = function(id){
		
		var modifyImg = "<c:url value='/base/images/icon/ic_cir_modify.png'/>";
		var deleteImg = "<c:url value='/base/images/icon/ic_cir_minus.png'/>";
		
		var resizeDiv = '<div class="menuguide_resize none">'
			  + '<div class="ic_rt"><a href="#" onclick="return false;"><img src="'+modifyImg+'" alt="modify"/></a></div>'
			  + '<div class="ic_rb"><a href="#" onclick="return false;"><img src="'+deleteImg+'" alt="delete" /></a></div>'
			  + '</div>';
		var $ul = $jq("#subMenuList_"+id);
		var $li = $jq('<li/>').appendTo($ul).addClass("selected")
				.append('<a href="#" onclick="return false;">New Menu</a>')
				.append(resizeDiv);
		
		outBoxResize();
		
		menuItemAction = "add";
		addMenuKind = "sub";
		$currItem = $li;
		currParentMenuId = id;
		
		getMenuInfo();	
		
	};
	
	/* 원복 */
	restoreMenu = function(){		
		if(menuItemAction == "add") {
			$currItem.remove();
			outBoxResize();
		}
		$jq(".blockBox").hide();
	};
	
	/* 취소 */
	cancelMenu = function(){		
		
		if(menuItemAction == "add" && addMenuKind == "main") {
			$currItem.remove();
			outBoxResize();
		} else if(menuItemAction == "add" && addMenuKind == "sub") {
			$currItem.remove();
			setBoxSize();
		}else{
			$currItem.removeClass("selected");	
		}		
		$jq(".blockBox").hide();
		$jq("#menuDetailDiv").empty();
		
		currMenuId = "";
		currParentMenuId = "";
	};
	
	/* save, modify success after */
	saveAfter = function(){		
		$currItem.removeClass("selected");
		$currItem.children(".menuguide_resize").hide();		
		$jq(".blockBox").hide();
		$jq("#menuDetailDiv").empty();
		
		setBoxSize();
		
		//parent.document.reload();
	};
	
	/* modify */
	modifyMenu = function(el, id){
		var $menu = $jq(el).parents("li:first");		
		$currItem = $menu;		
		$menu.addClass("selected");
		menuItemAction = "modify";
		currMenuId = id;
		
		outBoxResize();

		getMenuInfo();		
	};
	
	/* remove */
	removeMenu = function(el, id){		
		var $menu = $jq(el).parents("li:first");	
		var menuUrl = "<c:url value='portalMenuDelete.do'/>";
		
		if(confirm("<ikep4j:message pre='${preAlert}' key='confirmDelete' />")) { 
			$jq.ajax({
				url : menuUrl,
				data : {
					fieldName : "menuName",
					itemTypeCode : "PO",
					menuId : id
				},
				type : "post",
				success : function(result) {
					alert("<ikep4j:message pre='${preAlert}' key='deleteSuccess' />");					
					
					//document.location.reload();
					$menu.remove();
					outBoxResize();
					
					setBoxSize();
				},
				error : function(event, request, settings) {
					alert("<ikep4j:message pre='${preAlert}' key='deleteFail' />");	
				}
			});	
		}		
	};	
		
	/* menu save */
	saveMenu = function() {
		
		switch(menuItemAction) {
			case "add" :
				$jq("input[name=menuName]").val($jq("input[id=${userLocaleCode}]").val());
				$jq("input[name=menuType]").val($jq("input[name=menuTypeRadio]:radio:checked").val());
				
				if($jq("input[name='menuTypeRadio']:radio:checked").val() != "CATEGORY") {
					$jq("input[name=urlType]").val($jq("input[name=urlTypeRadio]:radio:checked").val());
					$jq("input[name=target]").val($jq("input[name=targetRadio]:radio:checked").val());
				} else {
					$jq("input[name=url]").val("");
					$jq("input[name=urlType]").val("");
					$jq("input[name=target]").val("");
				}
				
				//ajax : new add Menu				
				$jq("#portalMenuForm").trigger("submit");
				
				break;
				
			case "modify" :
				$jq("input[name=menuName]").val($jq("input[id=${userLocaleCode}]").val());
				$jq("input[name=menuType]").val($jq("input[name=menuTypeRadio]:radio:checked").val());
				
				if($jq("input[name='menuTypeRadio']:radio:checked").val() != "CATEGORY") {
					$jq("input[name=urlType]").val($jq("input[name=urlTypeRadio]:radio:checked").val());
					$jq("input[name=target]").val($jq("input[name=targetRadio]:radio:checked").val());
				} else {
					$jq("input[name=url]").val("");
					$jq("input[name=urlType]").val("");
					$jq("input[name=target]").val("");
				}
				
				//ajax : Menu info modify form				
				$jq("#portalMenuForm").trigger("submit");
				
				break;
		}
		
	};
	
	getMenuInfo = function(){
		
		$jq(".blockBox").show();
		$currItem.children(".menuguide_resize").hide();
		
		var $target = $jq("#menuDetailDiv");
		var menuUrl = "<c:url value='/admin/screen/menu/portalMenuForm.do'/>";
		var parms = "action="+menuItemAction;
		
		$target.empty();
		
		switch(menuItemAction) {
			case "add" :
									
				//ajax : new add Menu				
				$jq.ajax({
					url : menuUrl,
					data : "",
					type : "post",
					success : function(result) {
						$target.append(result);
						//$("input[name=menuName]").focus();
						
						if(currParentMenuId != "" && currParentMenuId != null) {
							$jq("input[name=parentMenuId]").val(currParentMenuId);
							$jq("#previewTr").hide();
						}
					},
					error : function(event, request, settings) { 
						restoreMenu(); // 복원
						
						alert("<ikep4j:message pre='${preAlert}' key='error' />");	
					}
				});	
					
				// ajax success callback process
				
				break;
				
			case "modify" :
				
				//ajax : Menu info modify form				
				$jq.ajax({
					url : menuUrl,
					data : {
						fieldName : "menuName",
						itemTypeCode : "PO",
						menuId : currMenuId
					},
					type : "post",
					success : function(result) {
						$target.append(result);
						//$("input[name=menuName]").val($currItem.children("a").text());
						//$("input[name=menuName]").focus();
					},
					error : function(event, request, settings) { 
						restoreMenu(); // 복원
						
						alert("<ikep4j:message pre='${preAlert}' key='error' />");	
					}
				});	
				
				break;
		}
		
	};
	
})(jQuery);
//]]>
</script>
 
<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preMain}" key="pageTitle" /></h2>
</div>
<!--//pageTitle End-->

<div class="directive">
	<ul>
		<li><ikep4j:message pre="${preMain}" key="desc1" /></li>
		<li><ikep4j:message pre="${preMain}" key="desc2" /></li>
	</ul>
</div>

<!--contentsMenu Start-->				
<div class="menuguide">
	<div class="menuguide_con">
		<div class="blockBox" style="display:none !important;"></div>
		<ul class="menuguide_list" id="topMenuList">
		
			<c:set var="parentMenuId" value=""/>			
		
			<c:forEach var="menu" items="${portalMenuList}" varStatus="status">
			
			<c:if test="${menu.depthLevel == 1}">
			
			<c:if test="${!status.first && menu.menuId != parentMenuId}">
				</ul>
				<div class="addSubMenu">
					<span>
						<a href="#" onclick="addSubMenu('<c:out value='${parentMenuId}'/>'); return false;">
							Add Menu <img src="<c:url value='/base/images/icon/ic_arb_down_2.gif'/>" alt="" />
						</a>
					</span>
				</div>
			</li>
			</c:if>
			
			<c:set var="parentMenuId" value="${menu.menuId}"/>
			<li id="menu_<c:out value='${menu.menuId}'/>">
				<a href="#" onclick="return false;">
					<c:out value="${menu.menuName}"/>
				</a>
				<div class="menuguide_resize none">
					<div class="ic_rt">
						<a href="#" onclick="modifyMenu(this, '<c:out value='${menu.menuId}'/>'); return false;">
							<img src="<c:url value='/base/images/icon/ic_cir_modify.png'/>" alt="modify"/>
						</a>
					</div>
					<div class="ic_rb">
						<a href="#" onclick="removeMenu(this, '<c:out value='${menu.menuId}'/>'); return false;">
							<img src="<c:url value='/base/images/icon/ic_cir_minus.png'/>" alt="delete" />
						</a>
					</div>												
				</div>
				<ul id="subMenuList_<c:out value='${menu.menuId}'/>" class="subMenuList">
			</c:if>
			
			<c:if test="${menu.depthLevel == 2 && menu.parentMenuId == parentMenuId}">
					<li id="menu_<c:out value='${menu.menuId}'/>">
						<a href="#" onclick="return false;">
							<c:out value="${menu.menuName}"/>
						</a>
						<div class="menuguide_resize none">
							<div class="ic_rt">
								<a href="#" onclick="modifyMenu(this, '<c:out value='${menu.menuId}'/>'); return false;">
									<img src="<c:url value='/base/images/icon/ic_cir_modify.png'/>" alt="modify"/>
								</a>
							</div>
							<div class="ic_rb">
								<a href="#" onclick="removeMenu(this, '<c:out value='${menu.menuId}'/>'); return false;">
									<img src="<c:url value='/base/images/icon/ic_cir_minus.png'/>" alt="delete" />
								</a>
							</div>													
						</div>								
					</li>
			</c:if>
			
			<c:if test="${status.last}">
					<li style="height:0px; border:0px; margin:0px;"></li>
				</ul>
				<div class="addSubMenu">
					<span>
						<a href="#" onclick="addSubMenu('<c:out value='${parentMenuId}'/>'); return false;">
							Add Menu <img src="<c:url value='/base/images/icon/ic_arb_down_2.gif'/>" alt="" />
						</a>
					</span>
				</div>
			</li>
			</c:if>
			
			</c:forEach>
		</ul>
		<div class="addSubMenu_2">
			<span id="addMenu">
				<a href="#" onclick="addMenu(); return false;">
					Add Menu <img src="<c:url value='/base/images/icon/ic_arb_right.gif'/>" alt="" />
				</a>
			</span>
		</div>
		<div class="clear"></div>
	</div>			
</div>
<!--//contentsMenu End-->

<!--//menu Detail Area Start-->
<div id="menuDetailDiv"></div>
<!--//menu Detail Area End-->		