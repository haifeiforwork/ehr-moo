<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="preMessage" value="message.workflow.admin.participants" />
<c:set var="preHeader" value="ui.workflow.admin.participants" />
<c:set var="preButton" value="ui.workflow.admin.participants.button" />


<script type="text/javascript">
/* item data format : array in json object
 * 1. {type:"group", code:"", name:""}
 * 2. {type:"user", id:"", name:"", empNo:"", email:"", group:""}
*/
var dialogWindow = null;
var fnCaller;

var tab, $groupTree;

var tabRole = false;

var abc = false;
(function($){
	fnCaller = function (params, dialog) {
		if(params) {
			if(params.items) {
				appendItem(params.items);
			}
			if(params.search) {
				$("#treesch").val(params.search);
			}
		}
		dialogWindow = dialog;
		$("#btnClose").click(function() {
			dialogWindow.close();
		});
	};
	
	var createGroupTree = function createGroupTree() {
		if(!$groupTree) {
			$("#treeDept").bind("loaded.jstree", function (event, data) {
				var $TopItem = $("ul > li:first", this);
				$("#treeDept").jstree("open_node", $TopItem.children("a")[0], false);
	        });
			
			$groupTree = $("#treeDept").jstree({	// 조직도 생성
				plugins : [ "themes", "json_data", "ui" ],
				json_data : {
					data : iKEP.arrayToTreeData(${deptItems}.items),
					ajax : {
						url : "<c:url value='/support/popup/requestGroupChildren.do'/>",
						data : function ($el) {	//$el : current item 
							return { 
								 "operation" : "get_children",  
								 "groupId" : $el.attr("code")
								,"controlType" : $('input[name=controlType]:hidden').val()
								,"controlTabType" : $('input[name=controlTabType]:hidden').val()
								,"selectType" : $('input[name=selectType]:hidden').val()
							}; 
						},
						success : function(data) {
							return iKEP.arrayToTreeData(data.items);
						}
					}
				}
			}).delegate("a[href$='#']", "dblclick", function (e) { // clicking node text expands and contracts it
				e.preventDefault(); 
				this.blur(); 
				$("#treeDept").jstree("open_node", this, false, true); 
				//$("#producttree").jstree("toggle_node", this); 
	        });
		}
	};

	$(document).ready(function() {
		var param = iKEP.getPopupArgument();
		if(param) {
			if(param.items) {
				appendItem(param.items);
			}
			if(param.search) {
				$("#treesch").val(param.search);
			}
		}
		
		tab = $("#divTabs").tabs({
			select : function(event, ui) {
				switch($(ui.panel).attr("id")) {
					case "tab-orggroup" :
						var $roleItems = $("#divSearchResult").find("li.ui-selected");
						$roleItems.removeClass("ui-selected");
						createGroupTree();
						break;
					case "tab-role" : 
						if(tabRole == false){
							$jq.post("<c:url value='/workflow/admin/participantsSearchRole.do' />")
							.success(function(result) {
								$("#divSearchResult").html(result); 
							})
							.error(function(event, request, settings) { alert("error"); });
							
							$("#divSearchResult").bind("dblclick", applyRole);
							
							tabRole = true;
							
							$("#divRole").css("display","inline");
						}
						break;	
				}
			}
		});
		
		createGroupTree();
		
		$("#ulResult").selectable();
		$("#treeDept").bind("dblclick", dblClickOrg);
		$("#btnItemAdd").click(function(){
			var $roleItems = $("#divSearchResult").find("li.ui-selected");
			if($roleItems.length > 0){
				applyRole();
				$roleItems.removeClass("ui-selected");
			}else{
				applyItem();
			}
		});
		
		$("#btnItemRemove").click(function() {
			var $items = $("#ulResult > li.ui-selected");
			if($items.length > 0) {
				$items.remove();
			} else {
				alert("<ikep4j:message pre='${preMessage}' key='alert.deleteItem' />");
			}
		});
		
		$("#btnItemRemoveAll").click(function() {
			var $items = $("#ulResult>li");

			$items.each(function(idx) {
				$items.remove();
			});
		});
		
		$("#btnApply").click(function() {
			var result = [];
			var $resultItems = $("#ulResult").children();
			
			$.each($resultItems, function() {
				var data = $.extend({}, $.data(this, "data"));
				result.push(data);//{type:data.type, info:info}
			});
			if(dialogWindow != null) {
				try {	// callback function : dialog 생성시 callback handler 셋팅됨.
					dialogWindow.callback(result);
					dialogWindow.close();
				} catch(e) {}
			} else {
				iKEP.returnPopup(result);
			}
		});
		
		$("#btnSearch").click(function(){
			var keyword = $("#schKeyword").val();
			var selectType = $jq('input[name=selectType]:hidden').val()
			if(!keyword) {
				alert("<ikep4j:message pre='${preMessage}' key='alert.searchConditionInput' />");
				return false;
			}
			
			$.post("<c:url value='/workflow/admin/participantsSearchRole.do' />", {"participantSearchTitle":keyword})
			.success(function(result) {
				$("#divSearchResult").html(result);
			})
			.error(function(event, request, settings) { alert("error"); });
		});
		
		$("#schKeyword").bind("keypress", function(event) {
			if(event.which == 13) {
				$("#btnSearch").trigger("click");
			}
		});
	});

	function dblClickOrg() {	// 조직도 및 검색 목록에서 아이템을 선택해서 넘길때
		
		var $activeTab = $("#divTabs").children("ul.ui-tabs-nav").children("li.ui-state-active");	// active tab item
		var $tree = $($activeTab.children("a").attr("href")).find("div.jstree");	// active tree

		var $treeItems = $tree.find("a.jstree-clicked").parent();
		var $subSelect = $treeItems.children("ul").children("li");
		
		var selectType = $('input[name=selectType]:hidden').val();
		if( selectType == 'GROUP' ){
			if($subSelect.length > 0){
				$tree.find("a.jstree-clicked").removeClass();
				$subSelect.each(function(idx){
					$subSelect.find("a").addClass('jstree-clicked');
				});
			}else{
				applyItem();
			}
		}else if( selectType == 'USER' ){
			if( $.parseJSON($treeItems.attr("data")).type == 'group' ){
				if($subSelect.length <= 0){
					if( $.parseJSON($treeItems.attr("data")).hasChild > 0 ){
						//$tree("open_node", $treeItems);
						//$treeItems.children("ins").trigger("click");
					}else{
						alert("<ikep4j:message pre='${preMessage}' key='alert.participantsNotFound' />");
					}
				}else{
					$tree.find("a.jstree-clicked").removeClass();
					$subSelect.each(function(idx) {
						if( $.parseJSON($(this).attr("data")).type == 'user' ){
							$(this).find("a").addClass('jstree-clicked');
						}
					});
				}
			}else{
				applyItem();
			}
		}else{

			if( $.parseJSON($treeItems.attr("data")).type == 'group' ){
				if($subSelect.length <= 0){

					if( $.parseJSON($treeItems.attr("data")).hasChild > 0 ){
						//alert("open subdirectory");	
					}else{
						//applyItem();
						alert("<ikep4j:message pre='${preMessage}' key='alert.groupAddClick' />");
					}
					
				}else{
					$tree.find("a.jstree-clicked").removeClass();
					$subSelect.each(function(idx) {
						//$(this).find("a").addClass('jstree-clicked');
						if( $.parseJSON($(this).attr("data")).type == 'user' ){
							$(this).find("a").addClass('jstree-clicked');
						}
					});
				}
			}else{
				applyItem();
			}
		}
		
	}
	
	function applyRole(){
		var $roleItems = $("#divSearchResult").find("li.ui-selected");
		if($roleItems.length <= 0){
			alert("<ikep4j:message pre='${preMessage}' key='alert.addSelected' />");
			return false;	
		}
		var items = [];

		$roleItems.each(function(idx) {
			var $li = $(this);
			items.push($.extend({name:$roleItems.attr("name")}, $.parseJSON($li.attr("data")) ));
		});
		appendItem(items);
	} 
	
	function applyItem() {	// 조직도 및 검색 목록에서 아이템을 선택해서 넘길때
		
		var $activeTab = $("#divTabs").children("ul.ui-tabs-nav").children("li.ui-state-active");	// active tab item
		var $tree = $($activeTab.children("a").attr("href")).find("div.jstree");	// active tree

			var $treeItems = $tree.find("a.jstree-clicked").parent();
			if($treeItems.length <= 0) {
				alert("<ikep4j:message pre='${preMessage}' key='alert.addSelected' />");
				return false;
			}
			
			var selectType = $('input[name=selectType]:hidden').val();
			//if( selectType == 'GROUP' ){
			//}else 
			if( selectType == 'USER' ){
				var items = [];
				$treeItems.each(function(idx) {
					var $li = $(this);
					if( $.parseJSON($li.attr("data")).type == 'user' ){
						items.push($.extend({name:$li.find("a:first").text()}, $.parseJSON($li.attr("data"))));
					}
				});
				
				if( $treeItems.length != items.length ){
					alert("<ikep4j:message pre='${preMessage}' key='alert.userSearchNoGroupSelected' />");
				}
				if( items.length > 0){
					appendItem(items);
				}
			}else{
				var items = [];
				$treeItems.each(function(idx) {
					var $li = $(this);
					items.push($.extend({name:$li.find("a:first").text()}, $.parseJSON($li.attr("data"))));
				});
				appendItem(items);
			}

		
	}
	
	function appendItem(addItems) {	// 선택한 목록 추가
		var $result = $("#ulResult");
		
		var arrAddedItems = [];	// 이미 추가 되어 있는 목록을 뽑아온다.

		var result = $result.children().each(function() {
			var data = $.data(this, "data");
			arrAddedItems.push(data.type == "group" ? data.code : data.id);
		});
			
		var cntAddedItem = 0;
		$.each(addItems, function(idx) {

			if (arrAddedItems.length == 0 || $.inArray(this.type == "group" ? this.code : this.id, arrAddedItems) == -1) {
				var elItem;
				if( this.type == "group" ){
					elItem = $.tmpl("tmplResultGroupItem", this).appendTo($result);
				}else if(this.type == "user"){
					elItem = $.tmpl("tmplResultUserItem", this).appendTo($result);
				}else{
					elItem = $.tmpl("tmplResultRoleItem", this).appendTo($result);
				}
				
				$.data(elItem[0], "data", this);
				cntAddedItem++;
			}
		});
			
		if(cntAddedItem == 0 && $result.children().length > 0) {
			alert("<ikep4j:message pre='${preMessage}' key='alert.alreadySeleted' />");
		} 
	}
	
	$.template("tmplResultGroupItem", '<li>\${name}</li>');
	$.template("tmplResultUserItem", '<li>\${name}/\${jobTitle}/\${teamName}</li>');
	$.template("tmplResultRoleItem", '<li>\${name}/\${id}</li>');
})(jQuery);

// -->

</script>

<div style="min-width:600px;">
	<!--popup_contents Start-->
	<div id="popup_contents" style="margin:5px 5px 0;padding:10px">

		<!--tab Start-->		
		<div id="divTabs" class="iKEP_tab">
			<ul>
				<li><a href="#tab-orggroup"><ikep4j:message pre="${preHeader}" key="organization" /></a></li>
				<li><a href="#tab-role"><ikep4j:message pre="${preHeader}" key="role" /></a></li>
			</ul>	
			<div class="shuttletab_ins">
				<input name="searchSubFlag" title="" type="hidden" value="" />
				<input name="controlTabType" title="" type="hidden" value="${controlTabType}" />
				<input name="controlType" title="" type="hidden" value="${controlType}" />
				<input name="selectType" title="" type="hidden" value="${selectType}" />
			</div>

		<!--blockShuttle Start-->
		<div class="blockShuttle">

			<div class="shuttle_l">
				<div id="tab-orggroup">
					<div>
						<div class="sbox" style="height:300px; overflow:auto;">
							<div class="shuttleTree">
								<div id="treeDept"></div>
							</div>
						</div>
					</div>
				</div>
				<div id="tab-role">
					<div id="divRole" style="display:none;">
						<div class="shuttleSearch">
						<input type="text" title="<ikep4j:message pre='${preHeader}' key='roleName' />" class="inputbox" id="schKeyword" value="" size="20"/>
						<a id="btnSearch" href="#" style="vertical-align:bottom;"><img src="<c:url value='/base/images/theme/theme01/basic/ic_search.gif' />" alt="<ikep4j:message pre='${preButton}' key='search' />" /></a> 
						</div>
						<div class="sbox" id="divSearchResult" style="overflow:hidden;">
						</div>
					</div>
				</div>
			</div>
			<div class="shuttle_m">
				<ul style="margin-top:140px;"><!--margin-top을 셔틀높이에 맞게 설정할 것-->
					<li><a id="btnItemAdd" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_add.gif' />" alt="Add" /></a></li>
					<li><a id="btnItemRemove" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_del.gif' />" alt="Delete" /></a></li>
					<li><a id="btnItemRemoveAll" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_reset.gif' />" alt="Reset" /></a></li>
				</ul>
			</div>
			<div class="shuttle_r">
				<div class="sbox">
					<div class="shuttleCon" style="height:300px;">
						<ul id="ulResult" class="list-selectable"></ul>
					</div>
				</div>
			</div>		
			<div class="clear"></div>			
		</div>
		<!--//blockShuttle End-->

		<!--blockButton Start-->
		<div class="blockButton suttle_btn" style="margin:0 0 0 0;width:100%">
			<a id="btnApply" class="button_s"><span><ikep4j:message pre='${preButton}' key='apply' /></span></a>
			<a id="btnClose" class="button_s"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
		</div>
		<!--//blockButton End-->
		</div>		
		<!--//tab End-->				
	</div>
	<!--//popup_contents End-->
</div>
<!--//popup End-->