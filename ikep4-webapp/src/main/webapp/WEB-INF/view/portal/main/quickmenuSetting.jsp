<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="prefix" value="ui.portal.main.quickmenuSetting" />
<c:set var="preAlert" value="message.portal.main.quickmenuSetting.alert" />

<script type="text/javascript">
//<![CDATA[
 

           
var activeMenuItems = ${quickMenuList};

var dialogHandler;
function fnCaller(param, dialog) {
	dialogHandler = dialog;
}

(function($) {
	
	var tplMenuIcon = $.template(null, '<a class="\${icon}" title="\${desc}" href="#a"><span>\${desc}</span></a>');
	var $menuItems, initialIcons = [];
	
	function getMenuItem(menuId) {
		var input = null;
		$menuItems.each(function() {
			if($(this).val() == menuId) {
				input = this;
				return false;
			}
		});
		
		return input;
	}
	
	function getMenuIcon(menuId) {
		var li = null;
		var $icons = $("li", "#divMenuIcons");
		$icons.each(function() {
			if($.data(this, "itemId") == menuId) {
				li = this;
				return false;
			}
		});
		
		return li;
	}
	
	$(document).ready(function() {
		$menuItems = $("input[type=checkbox]", "#tblMenuItems");
		
		var $ul = $('<ul/>').appendTo("#divMenuIcons").sortable();
		
		$.each(activeMenuItems, function() {
			var $menu = $('<li/>').append($.tmpl(tplMenuIcon, this)).appendTo($ul);
			$.data($menu[0], "itemId", this.id);
			
			var menuInput = getMenuItem(this.id);
			if(menuInput) $(menuInput).attr("checked", true);
			
			initialIcons.push(this.id);
		});
		
		$("#tblMenuItems").click(function(event) {
			if(event.target.tagName.toLowerCase() == "input") {
				var $el = $(event.target);
				var itemId = $el.val();
				
				if($el.is(":checked")) {	// checked
					var data = {icon:$el.attr("name"), desc:$el.attr("title")};
					var $icon = $('<li/>').append($.tmpl(tplMenuIcon, data)).appendTo($ul);
					$.data($icon[0], "itemId", itemId);
				} else {	// unchecked
					var icon = getMenuIcon(itemId);
					$(icon).remove();
				}
			}
		});
		
		$("#btnCancel").click(function() {
			dialogHandler.close();
		});
		
		$("#btnSave").click(function() {
			var menuItems = [];
			var icons = [];
			var $icons = $("li", "#divMenuIcons");
			$icons.each(function() {
				var itemId = $.data(this, "itemId");
				menuItems.push({id:itemId, icon:$(this).children().attr("className")});
				
				icons.push(itemId);
			});

			if(initialIcons.equal(icons)) {
				if(!confirm("<ikep4j:message pre='${preAlert}' key='saveConfirm' />")) {
					return false;
				}
				dialogHandler.close();
			} else {
				if(menuItems.length < 3) {
					alert("<ikep4j:message pre='${preAlert}' key='saveLength' />");
					
					return false;
				}
				/* else if(menuItems.length > 10){
					alert("퀵메뉴는 3개이상 10개 이하로 설정해주세요.");
					return false;
				} */
				else {
					$.ajax({
						url : "<c:url value='/portal/main/quickMenuSetSave.do'/>",
						type : "post",
						data : {
							userQuickMenu : JSON.stringify(menuItems)
						},
						success : function(result) {
							dialogHandler.callback(menuItems);
							dialogHandler.close();
						},
						error : function() { alert("<ikep4j:message pre='${preAlert}' key='error' />"); }
					});
				}
			}
		});
	});
})(jQuery);
//]]>
</script>

<div class="" id="layer_p" title="<ikep4j:message pre='${prefix}' key='title' />">
	<div class="popTextguide">
		<ul>
			<li><ikep4j:message pre='${prefix}' key='main.guide1' /></li>
			<li><ikep4j:message pre='${prefix}' key='main.guide2' /></li>
			<li>3. 퀵메뉴 적정 개수는 1366*768 해상도는 최대 14개, 1600*900 해상도는 최대 18개까지 선택 가능합니다.</li>
		</ul>				
	</div>
	
	<!--QuickIcon Start-->
		<div class="popQuickguide">
			<div id="divMenuIcons" class="sortableWidth"></div>
			<div class="clear"></div>
		</div>
	<!--//QuickIcon End-->
	
	<!--blockSearch Start-->
	<div class="blockSearch popQuick_select">
		<ul id="tblMenuItems">	
			<c:if test="${empty portalQuickMenuList}">
			<li></li>
			</c:if>
			<c:if test="${!empty portalQuickMenuList}">
			<c:forEach var="quickMenu" items="${portalQuickMenuList}" varStatus="status">
			<li>
				<input id="quickMenu_${quickMenu.quickMenuId}" name="${quickMenu.iconId}" class="checkbox" title="${quickMenu.quickMenuName}" type="checkbox" value="${quickMenu.quickMenuId}" /> 
				<label for="quickMenu_${quickMenu.quickMenuId}"><c:out value="${quickMenu.quickMenuName}"/></label>
			</li>
			</c:forEach>
			</c:if>
		</ul>
		<div class="clear"></div>				
	</div>	
	<!--//blockSearch End-->
	
	<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a id="btnSave" class="button" href="#a"><span><ikep4j:message pre='${prefix}' key='button.save' /></span></a></li>
				<li><a id="btnCancel" class="button" href="#a"><span><ikep4j:message pre='${prefix}' key='button.cancel' /></span></a></li>
			</ul>
		</div>
	<!--//blockButton End-->
</div>