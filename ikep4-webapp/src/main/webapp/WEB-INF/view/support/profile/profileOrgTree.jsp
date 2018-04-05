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

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript">
var $groupTree;


(function($){
	
	// 조직도 팝업에서 그룹과 사용자  트리 조회
	var initTreeItem = "#treeItem_${targetUserId}";
	var createGroupTree = function createGroupTree() {
		if(!$groupTree) {
			
			$("#treeDept").bind("loaded.jstree", function (event, data) {
				var $selectItem = $(initTreeItem);
				while(true) {
					$selectItem = $selectItem.parents("li:first", this);
					if($selectItem.length > 0) {
						$("#treeDept").jstree("open_node", $selectItem[0], false);
					} else {break;}
				}
	        });
			
			//$("#treeDept").bind("loaded.jstree", function (event, data) {
			//	var $TopItem = $("ul > li:first", this);
			//	$("#treeDept").jstree("open_node", $TopItem.children("a")[0], false);
	        //});
			
			$groupTree = $("#treeDept").jstree({	// 조직도 생성
				plugins : [ "themes", "json_data", "ui" ],
				
				json_data : {
					data : iKEP.arrayToTreeData(${deptItems}.items, null, true), //
					ajax : {
						url : "<c:url value='/support/popup/requestGroupChildren.do'/>",
						data : function ($el) {	//$el : current item 
							return { 
								 "operation" : "get_children"
								,"groupId" : $el.attr("code")
								,"controlType" : $('input[name=controlType]:hidden').val()
								,"controlTabType" : $('input[name=controlTabType]:hidden').val()
								,"selectType" : 'USER'
							}; 
						},
						success : function(data) {
							return iKEP.arrayToTreeData(data.items);
						}
					}
				},
				ui: {initially_select: ["treeItem_${targetUserId}"]},
				core : {animation : 100}
			}).delegate("a[href$='#']", "dblclick", function (e) { // clicking node text expands and contracts it
				e.preventDefault(); 
				this.blur(); 
				$("#treeDept").jstree("open_node", this, false, true); 
	        });
		}
	};

	// 
	$(document).ready(function() {
		
		createGroupTree();
		$("#ulResult").selectable();
		$("#treeDept").bind("dblclick", dblClickOrg);
		
	});

	function dblClickOrg() {	// 조직도 및 검색 목록에서 아이템을 선택해서 넘길때
		
		var $tree = $("#leftMenu").find("div.jstree");	// active tree
		var $treeItems = $tree.find("a.jstree-clicked").parent();
		var $subSelect = $treeItems.children("ul").children("li");
		
		if( $.parseJSON($treeItems.attr("data")).type == 'group' ){
			if($subSelect.length <= 0){
				if( $.parseJSON($treeItems.attr("data")).hasChild > 0 ){
				}else{
					alert("<ikep4j:message pre='${preMessage}' key='nodata.subgroupuser'/>");
				}
			}else{
				$tree.find("a.jstree-clicked").removeClass();
			}
		}else{
			document.location.href = "<c:url value='/support/profile/getProfile.do?targetUserId='/>" + $.parseJSON($treeItems.attr("data")).id ;
		}
		
	}
	
})(jQuery);

// -->

</script>

<div id="leftMenu" style="width:160px">

	<div class="sbox" style="height:450px; overflow:auto;">
		<div class="shuttleTree">
			<div id="treeDept"></div>
		</div>
	</div>

</div>
