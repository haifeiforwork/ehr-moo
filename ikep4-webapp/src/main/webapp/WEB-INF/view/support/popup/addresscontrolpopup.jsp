<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.addressbook.header" /> 
<c:set var="preControl"    value="ui.support.addressbook.popup.controlpopup" />
<c:set var="preButton"  value="ui.support.addressbook.popup.button" />
<c:set var="preContext"  value="ui.support.addressbook.popup.context" />
<c:set var="preMessage"  value="message.support.addressbook.popup" />
<%-- 메시지 관련 Prefix 선언 End --%>


<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript">

<!--  

/* item data format : array in json object
 * 1. {type:"group", code:"", name:""}
 * 2. {type:"user", id:"", name:"", empNo:"", email:"", group:""}
*/

var dialogWindow = null;
var fnCaller;
var isLayerWindow = false;

var pubGroupItems = $jq.parseJSON('${pubGroupItems}');
var priGroupItems = $jq.parseJSON('${priGroupItems}');
var colGroupItems = $jq.parseJSON('${colGroupItems}');
var snsGroupItems = $jq.parseJSON('${snsGroupItems}');

var tab, $groupTree, $pubAddrgroupTree, $priAddrgroupTree, $colAddrgroupTree, $snsAddrgroupTree;


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
		
		isLayerWindow = true;
	};
	
	// 조직도 팝업에서 그룹과 사용자  트리 조회
	var initTreeItem = "";
	
	if(${selectType=='GROUP'}) {
		initTreeItem = "#treeItem_${user.groupId}";
	}
	else {
		initTreeItem = "#treeItem_${user.userId}";
	}
	
	var createGroupTree = function createGroupTree() {
		
		if(!$groupTree) {
			$("#treeDept").bind("loaded.jstree", function (event, data) {
				var $tree = $(this);
				var $selectItem = $(initTreeItem);
				if($selectItem.length > 0) {
					while(true) {
						$selectItem = $selectItem.parents("li:first", this);
						if($selectItem.length > 0) {
							$tree.jstree("open_node", $selectItem[0], false);
						} else {break;}
					}
				} else {	// 최상위 레벨 오픈
					var $TopItem = $("ul > li:first", this);
					$tree.jstree("open_node", $TopItem, false);
				}
	        });
			
			$groupTree = $("#treeDept").jstree({	// 조직도 생성
				plugins : [ "themes", "json_data", "ui" ],
				json_data : {
					//data : iKEP.arrayToTreeData(${deptItems}.items, null, true),
					ajax : {
						url : "<c:url value='/support/popup/requestGroupChildren.do'/>",
						data : function ($el) {	//$el : current item 
							return { 
								 "operation" : "get_children",  
								 "groupId" : $el == -1 ? '': $el.attr("code")
								,"controlType" : $('input[name=controlType]:hidden').val()
								,"controlTabType" : $('input[name=controlTabType]:hidden').val()
								,"selectType" : $('input[name=selectType]:hidden').val()
							}; 
						},
						success : function(data) {
							
							return iKEP.arrayToTreeData(data.items, null, true);
						}
					}
				}
				, core : {animation : 100}
			}).delegate("a[href$='#']", "dblclick", function (e) { // clicking node text expands and contracts it
				e.preventDefault(); 
				this.blur(); 
				$("#treeDept").jstree("open_node", this, false, true); 
	        });
			
		}
	};

	// 조직도 팝업에서 공용 그룹 트리 조회
	var createPubAddrGroupTree = function createPubAddrGroupTree() {
		//$('input[name=selectType]:hidden').val("ALL");
		if(!$pubAddrgroupTree) {
			//$("#treeDeptPub").bind("loaded.jstree", function (event, data) {
			//	var $TopItem = $("ul > li:first", this);
			//	$("#treeDeptPub").jstree("open_node", $TopItem.children("a")[0], false);
	        //});
		
			$pubAddrgroupTree = $("#treeDeptPub").jstree({	// 조직도 생성
				plugins : [ "themes", "json_data", "ui" ],
				json_data : {
					ajax : {
						url : "<c:url value='/support/popup/requestGroupChildren.do'/>",
						data : function ($el) {	//$el : current item 
							return { 
								 "operation" : "get_children" 
								,"groupId" : $el == -1 ? '': $el.attr("code")
								,"controlType" : 'PUB'
								,"controlTabType" : $('input[name=controlTabType]:hidden').val()
								,"selectType" : $('input[name=selectType]:hidden').val()
							}; 
						},
						success : function(data) {
							return iKEP.arrayToTreeData(data.items);
						}
					}
				}
				, core : {animation : 100}
			}).delegate("a[href$='#']", "dblclick", function (e) { // clicking node text expands and contracts it
				e.preventDefault(); 
				this.blur(); 
				$("#treeDeptPub").jstree("open_node", this, false, true); 
	        });
		}
	};
	
	// 조직도 팝업에서 개인 그룹 트리 조회
	var createPriAddrGroupTree = function createPriAddrGroupTree() {
		if(!$priAddrgroupTree) {
			//$("#treeDeptPri").bind("loaded.jstree", function (event, data) {
			//	var $TopItem = $("ul > li:first", this);
			//	$("#treeDeptPri").jstree("open_node", $TopItem.children("a")[0], false);
	        //});
			
			$priAddrgroupTree = $("#treeDeptPri").jstree({	// 조직도 생성
				plugins : [ "themes", "json_data", "ui" ],
				json_data : {
					ajax : {
						url : "<c:url value='/support/popup/requestGroupChildren.do'/>",
						data : function ($el) {	//$el : current item
							return { 
								 "operation" : "get_children"  
								,"groupId" : $el == -1 ? '': $el.attr("code")
								,"controlType" : 'PRI'
								,"controlTabType" : $('input[name=controlTabType]:hidden').val()
								,"selectType" : $('input[name=selectType]:hidden').val()
							}; 
						},
						success : function(data) {
							return iKEP.arrayToTreeData(data.items);
						}
					}
				}
				, core : {animation : 100}
			}).delegate("a[href$='#']", "dblclick", function (e) { // clicking node text expands and contracts it
				e.preventDefault(); 
				this.blur(); 
				$("#treeDeptPri").jstree("open_node", this, false, true); 
	        });
		}
	};
	
	// 조직도 팝업에서 Collaboration 트리 조회
	var createColAddrGroupTree = function createColAddrGroupTree() {
		if(!$colAddrgroupTree) {
			//$("#treeDeptCol").bind("loaded.jstree", function (event, data) {
			//	var $TopItem = $("ul > li:first", this);
			//	$("#treeDeptCol").jstree("open_node", $TopItem.children("a")[0], false);
	        //});

			$colAddrgroupTree = $("#treeDeptCol").jstree({	// 조직도 생성
				plugins : [ "themes", "json_data", "ui" ],
				json_data : {
					ajax : {
						url : "<c:url value='/support/popup/requestGroupChildren.do'/>",
						data : function ($el) {	//$el : current item 
							return { 
								 "operation" : "get_children",  
								 "groupId" : $el == -1 ? '': $el.attr("code")
								,"controlType" : 'COL'
								,"controlTabType" : $('input[name=controlTabType]:hidden').val()
								,"selectType" : $('input[name=selectType]:hidden').val()
							}; 
						},
						success : function(data) {
							return iKEP.arrayToTreeData(data.items);
						}
					}
				}
				, core : {animation : 100}
			}).delegate("a[href$='#']", "dblclick", function (e) { // clicking node text expands and contracts it
				e.preventDefault(); 
				this.blur(); 
				$("#treeDeptCol").jstree("open_node", this, false, true); 
	        });
		}
	};
	
	// 조직도 팝업에서 SNS 트리 조회
	var createSnsAddrGroupTree = function createSnsAddrGroupTree() {
		if(!$snsAddrgroupTree) {
			//$("#treeDeptSns").bind("loaded.jstree", function (event, data) {
			//	var $TopItem = $("ul > li:first", this);
			//	$("#treeDeptSns").jstree("open_node", $TopItem.children("a")[0], false);
	        //});

			$snsAddrgroupTree = $("#treeDeptSns").jstree({	// 조직도 생성
				plugins : [ "themes", "json_data", "ui" ],
				json_data : {
					ajax : {
						url : "<c:url value='/support/popup/requestGroupChildren.do'/>",
						data : function ($el) {	//$el : current item 
							return { 
								 "operation" : "get_children",  
								 "groupId" : $el == -1 ? '': $el.attr("code")
								,"controlType" : 'SNS'
								,"controlTabType" : $('input[name=controlTabType]:hidden').val()
								,"selectType" : $('input[name=selectType]:hidden').val()
							}; 
						},
						success : function(data) {
							return iKEP.arrayToTreeData(data.items);
						}
					}
				}
				, core : {animation : 100}
			}).delegate("a[href$='#']", "dblclick", function (e) { // clicking node text expands and contracts it
				e.preventDefault(); 
				this.blur(); 
				$("#treeDeptSns").jstree("open_node", this, false, true); 
	        });
		}
	};
	
	
	// 
	$(document).ready(function() {
		
		$("#ulResult").children().remove();
		
		var param = iKEP.getPopupArgument();
		if(param && param.items) {
			if(param.items.length > 0) {
				var items = new Array();
				$.each(param.items, function() { items.push(this); });
				appendItem(items);
			}
			if(param.search) {
				$jq("#treesch").val(param.search);
			}
		}
		
		if(isLayerWindow == false) {
			$("#btnClose").click(function() {iKEP.closePop();});
		}
		
		tab = $("#divTabs").tabs({
			select : function(event, ui) {
				switch($(ui.panel).attr("id")) {
					case "tab-orggroup" : createGroupTree();
						break;
					case "tab-pubgroup" : createPubAddrGroupTree();
						break;
					case "tab-prigroup" : createPriAddrGroupTree();
						break;
					case "tab-colgroup" : createColAddrGroupTree();
						break;
					case "tab-snsgroup" : createSnsAddrGroupTree();
						break;
				}
			}
		});
		
		<c:if test="${empty dispOrgTabFlag || dispOrgTabFlag == 'true'}">
		createGroupTree();
		</c:if>
		<c:if test="${ dispPubTabFlag == 'true' && dispOrgTabFlag == 'false'}">
		createPubAddrGroupTree();
		</c:if>
		

		$("#ulResult").selectable();
		$("#treeDept").bind("dblclick", dblClickOrg);
		$("#treeDeptPub").bind("dblclick", dblClickOrg);
		$("#treeDeptPri").bind("dblclick", dblClickOrg);
		$("#treeDeptCol").bind("dblclick", dblClickOrg);
		$("#treeDeptSns").bind("dblclick", dblClickOrg);
		$("#divSearchResult").bind("dblclick", dblClickOrg);
		
		$("#btnItemAdd").click(applyItem);
		$("#btnItemRemove").click(function() {
			var $items = $("#ulResult > li.ui-selected");
			if($items.length > 0) {
				$items.remove();
				$("#cntSelectedItem").text($("#ulResult>li").size());
			} else {
				alert("<ikep4j:message pre='${preMessage}' key='noselect.delete'/>");
			}
		});
		
		$("#btnItemRemoveAll").click(function() {
			var $items = $("#ulResult>li");

			$items.each(function(idx) {
				$items.remove();
				$("#cntSelectedItem").text($("#ulResult>li").size());
			});
		});
		
		//$('input[name=searchSubUserFlag]:checkbox').click(function() {
			//viewOrgControl();
		//});
		//,"searchSubUserFlag" : $('input[name=searchSubUserFlag]:checkbox').is(':checked')
		
		
		$("#btnApply").click(function() {
			var $activeTab = $("#divTabs").children("ul.ui-tabs-nav").children("li.ui-state-active");	// active tab item
			var $tree = $($activeTab.children("a").attr("href")).find("div.jstree");	// active tree
			var $treeItems = $tree.find("a.jstree-clicked").parent();
			
			var result = [];
			var $resultItems = $("#ulResult").children();
			
			if(!param || !param.items) {	// 초기 아이템이 있었으면 아이템이 모두 삭제 되었을때도 적용해야 하므로...
				if($resultItems.length <= 0) {
					alert("<ikep4j:message pre='${preMessage}' key='noselect.apply'/>");
					return false;
				}
			}
			
			$.each($resultItems, function() {
				var $searchSubFlag = $('input[name=searchSubFlag]:checkbox').is(":checked");
				var data = $.extend({}, $.data(this, "data"));
				$.extend(data, {"searchSubFlag":$searchSubFlag}); 
				//var info = {name:$(this).text()};
				result.push(data);//{type:data.type, info:info}
			});
			var expVer = navigator.appVersion;

			if(dialogWindow != null) {
				try {	// callback function : dialog 생성시 callback handler 셋팅됨.
					dialogWindow.callback(result);
					dialogWindow.close();
				} catch(e) {}
			} else {
				if($.parseJSON($treeItems.attr("data")).type == "group" || $.parseJSON($treeItems.attr("data")).type == "common"){
					if(expVer.indexOf("11.") > 0){
					  iKEP.returnGroupPopup(result);	
					}else{
					  iKEP.returnPopup(result);	
					}
				}else if($.parseJSON($treeItems.attr("data")).type == "user"){
					if(expVer.indexOf("11.") > 0){
						iKEP.returnUserPopup(result);	
					}else{
					  iKEP.returnPopup(result);	
					}
				}else{
					iKEP.returnPopup(result);
				}
				
			}
		});
		
		$("#btnSearch").click(function(){
			var keyword = $("#schKeyword").val();
			var selectType = $('input[name=selectType]:hidden').val()
			if(!keyword) {
				alert("<ikep4j:message pre='${preMessage}' key='nodata.keyword'/>");
				return false;
			}

			//tab.tabs('select', '#tab-searchgroup');
			$("#divSearchResult").jstree("destroy").empty();
			$("#divSearchResult").unbind("dblclick");

			$.post("<c:url value='/support/popup/requestSearchGroup.do' />", {keyword:keyword,selectType:selectType})
				.success(function(result) { 
					if(result.items.length > 0) {
						$("#divSearchResult").jstree({
								plugins : [ "themes", "json_data", "ui" ],
								json_data : { data : iKEP.arrayToTreeData(result.items) },
								themes : {dots:false}
							});
						$("#divSearchResult").bind("dblclick", applyItem);
						
					}
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
						alert("<ikep4j:message pre='${preMessage}' key='nodata.subgroupuser'/>");
					}
				}else{
					$tree.find("a.jstree-clicked").removeClass();
					$subSelect.each(function(idx) {
						if( $.parseJSON($(this).attr("data")).type == 'user' || $.parseJSON($(this).attr("data")).type == 'addruser'  ){
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
						alert("<ikep4j:message pre='${preMessage}' key='must.addbtn'/>");
					}
					
				}else{
					$tree.find("a.jstree-clicked").removeClass();
					$subSelect.each(function(idx) {
						//$(this).find("a").addClass('jstree-clicked');
						if( $.parseJSON($(this).attr("data")).type == 'user' || $.parseJSON($(this).attr("data")).type == 'addruser' ){
							$(this).find("a").addClass('jstree-clicked');
						}
					});
				}
			}else{
				applyItem();
			}
		}
		
	}
	
	function applyItem() {	// 조직도 및 검색 목록에서 아이템을 선택해서 넘길때
		
		var $activeTab = $("#divTabs").children("ul.ui-tabs-nav").children("li.ui-state-active");	// active tab item
		var $tree = $($activeTab.children("a").attr("href")).find("div.jstree");	// active tree

			var $treeItems = $tree.find("a.jstree-clicked").parent();
			if($treeItems.length <= 0) {
				alert("<ikep4j:message pre='${preMessage}' key='noselect.add'/>");
				return false;
			}
			
			var selectType = $('input[name=selectType]:hidden').val();
			//if( selectType == 'GROUP' ){
			//}else 
				
			if( selectType == 'USER' ){
				var items = [];
				$treeItems.each(function(idx) {
					var $li = $(this);
					if( $.parseJSON($li.attr("data")).type == 'user' || $.parseJSON($li.attr("data")).type == 'addruser' ){
						items.push($.extend({name:getTreeItemName($li)}, $.parseJSON($li.attr("data"))));
					}
				});
				
				if( $treeItems.length != items.length ){
					alert("<ikep4j:message pre='${preMessage}' key='noselect.groupbyuser'/>");
				}
				if( items.length > 0){
					appendItem(items);
				}
			}else{
				
				var items = [];
				$treeItems.each(function(idx) {
					var $li = $(this);
					if($.parseJSON($li.attr("data")).type == "common" && $.parseJSON($li.attr("data")).id == null && $.parseJSON($li.attr("data")).hasChild > 0){
						alert("카테고리는 선택 불가능합니다. 그룹을 선택해주세요.");
						return;
					}
					items.push($.extend({name:getTreeItemName($li)}, $.parseJSON($li.attr("data"))));
				});
				appendItem(items);
			}
			
	}
	
	function getTreeItemName($li) {
		return $li.find("a:first").text().replace(String.fromCharCode(160), "");
	}
	
	function appendItem(addItems) {	// 선택한 목록 추가
		var $result = $("#ulResult");
		
		var arrAddedItems = [];	// 이미 추가 되어 있는 목록을 뽑아온다.
		if( $result.children().length + addItems.length   > ${selectMaxCnt} ){
			alert("<ikep4j:message pre='${preMessage}' key='select.maxcount'/>");
		}else{

			var result = $result.children().each(function() {
				var data = $.data(this, "data");
				var alreadys =data.type == "group"||data.type == "common" ? data.code : data.id;
				//alert(alreadys);
				arrAddedItems.push(alreadys);
				
			});
			var tmpId = "tmp";
			var tmpType = "tmp";
			var tmpHasChild = "tmp";
			var cntAddedItem = 0;
			$.each(addItems, function(idx) {	
				//alert("this.type:"+this.type);
				if (arrAddedItems.length == 0 || $.inArray(this.type == "group"||this.type == "common" ? this.code : this.id, arrAddedItems) == -1) {
					var elItem;
					
					if( this.type == "group" ){
						elItem = $.tmpl(tmplResultGroupItem, this).appendTo($result);
					}else if( this.type == "common" ){
						elItem = $.tmpl(tmplResultGroupItem, this).appendTo($result);
					}else{
						elItem = $.tmpl(tmplResultUserItem, this).appendTo($result);
					}
					
					$.data(elItem[0], "data", this);
					cntAddedItem++;
				}
				tmpId = this.id;
				tmpType = this.type;
				tmpHasChild = this.hasChild;
			});
			
			if(cntAddedItem == 0 && $result.children().length > 0 && tmpId+tmpType+tmpHasChild != "tmptmptmp") {
				alert("<ikep4j:message pre='${preMessage}' key='select.already'/>");
			} else {
				$("#cntSelectedItem").text(arrAddedItems.length + cntAddedItem);
			}
		}
	}
	
	var tmplResultGroupItem = $.template(null, '<li>\${name}</li>');
	var tmplResultUserItem = $.template(null, '<li>\${userName} \${jobTitleName} \${teamName}</li>');
})(jQuery);

// -->

</script>

<div id="popup" style="min-width:500px">

	
	<!--popup_title Start-->
	<div id="popup_title_2">
      <div class="popup_bgTitle_l"></div>
		<h1><ikep4j:message pre='${preHeader}' key='title' /></h1>
		<a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
	</div>
	<!--//popup_title End-->
				

	<!--popup_contents Start-->
	<div id="popup_contents">

		<!--tab Start-->		
		<div id="divTabs" class="iKEP_tab">
			<ul>
				<c:if test="${empty dispOrgTabFlag || dispOrgTabFlag == 'true'}">
				<li><a href="#tab-orggroup"><ikep4j:message pre='${preControl}' key='organization'/></a></li>
				</c:if>
				<c:if test="${dispPubTabFlag == 'true'}">
				<li><a href="#tab-pubgroup"><ikep4j:message pre='${preControl}' key='publicgroup'/></a></li>
				</c:if>
				<c:if test="${dispPriTabFlag == 'true'}">
				<li><a href="#tab-prigroup"><ikep4j:message pre='${preControl}' key='privategroup'/></a></li>
				</c:if>
				<c:if test="${dispColTabFlag == 'true'}">
				<li><a href="#tab-colgroup"><ikep4j:message pre='${preControl}' key='collaboration'/></a></li>
				</c:if>
				<c:if test="${dispSnsTabFlag == 'true'}">
				<li><a href="#tab-snsgroup"><ikep4j:message pre='${preControl}' key='sns'/></a></li>
				</c:if>
				<c:if test="${empty dispSchTabFlag || dispSchTabFlag == 'true'}">
				<li><a href="#tab-searchgroup"><ikep4j:message pre='${preControl}' key='search'/></a></li>
				</c:if>
			</ul>	
			<div class="shuttletab_ins">
				<!-- 
					<label><input name="searchSubFlag" class="checkbox" title="<ikep4j:message pre='${preContext}' key='includesubdepart'/>" type="checkbox" value="" ${searchSubFlag == 'true' ? 'checked="checked"':''}/><ikep4j:message pre='${preContext}' key='includesubdepart'/></label>
				 -->
				 <input name="controlTabType" title="" type="hidden" value="${controlTabType}" />
				<input name="controlType" title="" type="hidden" value="${controlType}" />
				<input name="selectType" title="" type="hidden" value="${selectType}" />
			</div>
		
			
			<!--blockShuttle Start-->
			<div class="blockShuttle">
				
				<br/>	
				
				<div class="shuttle_l">
					<div class="sbox" style="height:340px; overflow:auto;">
					
						<c:if test="${empty dispOrgTabFlag || dispOrgTabFlag == 'true'}">
						<div id="tab-orggroup"  class="shuttleTree">
							<div id="treeDept"></div>
						</div>
						</c:if>
					
							
						<c:if test="${dispPubTabFlag == 'true'}">
							<div id="tab-pubgroup" class="shuttleTree">
								<div id="treeDeptPub"></div>
							</div>
						</c:if>
								
						<c:if test="${dispPriTabFlag == 'true'}">
							<div id="tab-prigroup" class="shuttleTree">
								<div id="treeDeptPri"></div>
							</div>
						</c:if>
								
						<c:if test="${dispColTabFlag == 'true'}">
							<div id="tab-colgroup" class="shuttleTree">
								<div id="treeDeptCol"></div>
							</div>
						</c:if>
								
						<c:if test="${dispSnsTabFlag == 'true'}">
							<div id="tab-snsgroup" class="shuttleTree">
								<div id="treeDeptSns"></div>
							</div>
						</c:if>
					
						<c:if test="${empty dispSchTabFlag || dispSchTabFlag == 'true'}">
						<div id="tab-searchgroup"  class="shuttleTree">
							<div class="shuttleSearch">
								<select  style="height:20px;" title="검색조건" name="tablesch_01">
									<c:if test="${selectType == 'USER'}">
										<option value=""><ikep4j:message pre='${preButton}' key='usersearch'/></option>
									</c:if>
									<c:if test="${selectType == 'GROUP'}">
										<option value=""><ikep4j:message pre='${preButton}' key='groupsearch'/></option>
									</c:if>
									<c:if test="${selectType == 'ALL'}">
										<option value=""><ikep4j:message pre='${preButton}' key='allsearch'/></option> 
									</c:if>
								</select>
								<input type="text" title="<ikep4j:message pre='${preButton}' key='search'/>" class="inputbox" id="schKeyword" value="" size="20" />
								<a id="btnSearch" class="ic_search" href="#"><span><ikep4j:message pre='${preButton}' key='search'/></span></a> 
								&nbsp;&nbsp;&nbsp;
							</div>
							<div id="divSearchResult"></div>
						</div>
						</c:if>
						
					</div>
				</div>
				
				<div class="shuttle_m">
					<ul style="margin-top:140px;"><!--margin-top을 셔틀높이에 맞게 설정할 것-->
						<li><a id="btnItemAdd" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_add.gif' />" alt="<ikep4j:message pre='${preButton}' key='reset'/>" /></a></li>
						<li><a id="btnItemRemove" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_del.gif' />" alt="<ikep4j:message pre='${preButton}' key='delete'/>" /></a></li>
						<li><a id="btnItemRemoveAll" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_reset.gif' />" alt="<ikep4j:message pre='${preButton}' key='reset'/>" /></a></li>
					</ul>
				</div>
				
				<div class="shuttle_r">
					<div class="sbox">
						<div class="shuttleTitle" style="text-align:center">
							<ikep4j:message pre='${preButton}' key='select'/> (<span id="cntSelectedItem">0</span>/<span>${selectMaxCnt}</span>)
						</div>
						<div class="shuttleCon" style="height:300px;">
							<ul id="ulResult" class="list-selectable">
								<li></li>
							</ul>
						</div>
					</div>
					
				</div>	
					
				<div class="clear"></div>			
			</div>
			<!--//blockShuttle End-->
	
		
			<!--blockButton Start-->
			<div class="blockButton suttle_btn">
				<ul>
					<li><ikep4j:message pre='${preMessage}' key='multi'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li><a id="btnApply" class="button"><span><ikep4j:message pre='${preButton}' key='apply'/></span></a></li>
				</ul>
			</div>
			<!--//blockButton End-->
		
		</div>
		<!--//tab End-->
		
					
	</div>
	<!--//popup_contents End-->
	
	<!--popup_footer Start-->
	<div id="popup_footer"></div>
	<!--//popup_footer End-->
			
</div>
<!--//popup End-->
