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

//document.domain="moorim.co.kr";
var dialogWindow = null;
var fnCaller;
var isLayerWindow = false;

var pubGroupItems = $jq.parseJSON('${pubGroupItems}');
var priGroupItems = $jq.parseJSON('${priGroupItems}');
var colGroupItems = $jq.parseJSON('${colGroupItems}');
var snsGroupItems = $jq.parseJSON('${snsGroupItems}');

var tab, $groupTree, $pubAddrgroupTree, $priAddrgroupTree, $colAddrgroupTree, $snsAddrgroupTree;
var tabId="";
var selectType = "";
var tabDiv = "";

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
				var $selectItem = $(initTreeItem);
				while(true) {
					$selectItem = $selectItem.parents("li:first", this);
					if($selectItem.length > 0) {
						$("#treeDept").jstree("open_node", $selectItem[0], false);
					} else {break;}
				}
	        });
			
			$groupTree = $("#treeDept").jstree({	// 조직도 생성
				plugins : [ "themes", "json_data", "ui" ],
				json_data : {			
					ajax : {
						url : "<c:url value='/support/popup/requestGroupChildren.do'/>",
						data : function ($el) {	//$el : current item 
							return { 
								 "operation" : "get_children",  
								 "groupId" : $el == -1 ? '': $el.attr("code")
								,"controlType" : $('input[name=controlType]:hidden').val()
								,"controlTabType" : $('input[name=controlTabType]:hidden').val()
								,"selectType" : selectType
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

		if(!$pubAddrgroupTree) {

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
								,"selectType" : selectType
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
								,"selectType" : selectType
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
								,"selectType" : selectType
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
								,"selectType" : selectType
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
		
		var itemsReceiver ={};
		var itemsRefer ={};
		var itemsBcc = {};
		
		
		$("#ulReceiverResult").children().remove();
		$("#ulReferResult").children().remove();
		$("#ulBccResult").children().remove();
		
		var param = iKEP.getPopupMailArgument();
		
		// 수신자 입력
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
		
		// 참조자 입력
		if(param && param.referItems) {
			if(param.referItems.length > 0) {
				var referItems = new Array();
				$.each(param.referItems, function() { referItems.push(this); });
				appendReferItem(referItems);
			}
			if(param.search) {
				$jq("#treesch").val(param.search);
			}
		}
		
		// 숨은참조자 입력
		if(param && param.bccItems) {
			if(param.bccItems.length > 0) {
				var bccItems = new Array();
				$.each(param.bccItems, function() { bccItems.push(this); });
				appendBccItem(bccItems);
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
				tabId = $(ui.panel).attr("id");
				switch($(ui.panel).attr("id")) {
					case "tab-orggroup" : 
						tabDiv = "tab-orggroup";
						selectType = $('input[name=selectType]:hidden').val();
						createGroupTree();
						break;
					case "tab-pubgroup" : 
						tabDiv = "tab-pubgroup";
						selectType = "ALL";
					    createPubAddrGroupTree();
						break;
					case "tab-prigroup" : 
						tabDiv = "tab-prigroup";
						selectType = "ALL";
					    createPriAddrGroupTree();
						break;
					case "tab-colgroup" : 
						tabDiv = "tab-colgroup";
						createColAddrGroupTree();
					    selectType = "USER";
						break;
					case "tab-snsgroup" : 
						tabDiv = "tab-snsgroup";
						createSnsAddrGroupTree();
					    selectType = "USER";
						break;
				}
			}
		});

		createGroupTree();
		tabId="tab-orggroup";
		
		//$("#ulResult").selectable();
		$("#ulReceiverResult").selectable();
		$("#ulReferResult").selectable();
		$("#ulBccResult").selectable();

		$("#treeDept").bind("dblclick", dblClickOrg);
		$("#treeDeptPub").bind("dblclick", dblClickOrg);
		$("#treeDeptPri").bind("dblclick", dblClickOrg);
		$("#treeDeptCol").bind("dblclick", dblClickOrg);
		$("#treeDeptSns").bind("dblclick", dblClickOrg);
		$("#divSearchResult").bind("dblclick", dblClickOrg);
		
		// receiver
		$("#btnReceiverItemAdd").click(applyItem);
		$("#btnReceiverItemRemove").click(function() {
			var $items = $("#ulReceiverResult > li.ui-selected");
			if($items.length > 0) {
				$items.remove();
				$("#cntReceiverSelectedItem").text($("#ulReceiverResult>li").size());
			} else {
				alert("<ikep4j:message pre='${preMessage}' key='noselect.delete'/>");
			}
		});
		
		$("#btnReceiverItemRemoveAll").click(function() {
			var $items = $("#ulReceiverResult>li");

			$items.each(function(idx) {
				$items.remove();
				$("#cntReceiverSelectedItem").text($("#ulReceiverResult>li").size());
			});
		});

		// refer
		$("#btnReferItemAdd").click(applyReferItem);
		$("#btnReferItemRemove").click(function() {
			var $items = $("#ulReferResult > li.ui-selected");
			if($items.length > 0) {
				$items.remove();
				$("#cntReferSelectedItem").text($("#ulReferResult>li").size());
			} else {
				alert("<ikep4j:message pre='${preMessage}' key='noselect.delete'/>");
			}
		});
		
		$("#btnReferItemRemoveAll").click(function() {
			var $items = $("#ulReferResult>li");

			$items.each(function(idx) {
				$items.remove();
				$("#cntReferSelectedItem").text($("#ulReferResult>li").size());
			});
		});

		// bcc
		$("#btnBccItemAdd").click(applyBccItem);
		$("#btnBccItemRemove").click(function() {
			var $items = $("#ulBccResult > li.ui-selected");
			if($items.length > 0) {
				$items.remove();
				$("#cntBccSelectedItem").text($("#ulBccResult>li").size());
			} else {
				alert("<ikep4j:message pre='${preMessage}' key='noselect.delete'/>");
			}
		});
		
		$("#btnBccItemRemoveAll").click(function() {
			var $items = $("#ulBccResult>li");

			$items.each(function(idx) {
				$items.remove();
				$("#cntBccSelectedItem").text($("#ulBccResult>li").size());
			});
		});		
		
		$("#btnApply").click(function() {
			var result = [];

			var $resultReceiverItems = $("#ulReceiverResult").children();
			var $resultReferItems = $("#ulReferResult").children();
			var $resultBccItems = $("#ulBccResult").children();
			
			if($resultReceiverItems.length <= 0 && $resultReferItems.length <=0 && $resultBccItems.length<=0) {
				alert("<ikep4j:message pre='${preMessage}' key='noselect.apply'/>");
				return false;
			}
			
			$.each($resultReceiverItems, function() {
				var $searchSubFlag = $('input[name=searchSubFlag]:checkbox').attr('checked');
				var data = $.extend({}, $.data(this, "data"));
				$.extend(data, {"searchSubFlag":$searchSubFlag}); 
				// callback 구분자 ( 수신, 참조, 숨은참조)
				$.extend(data, {"gubun":'Receiver'}); 
				//var info = {name:$(this).text()};
				result.push(data);//{type:data.type, info:info}
			});

			$.each($resultReferItems, function() {
				var $searchSubFlag = $('input[name=searchSubFlag]:checkbox').attr('checked');
				var data = $.extend({}, $.data(this, "data"));
				$.extend(data, {"searchSubFlag":$searchSubFlag}); 
				// callback 구분자 ( 수신, 참조, 숨은참조)
				$.extend(data, {"gubun":'Refer'}); 
				//var info = {name:$(this).text()};
				result.push(data);//{type:data.type, info:info}
			});
			

			$.each($resultBccItems, function() {
				var $searchSubFlag = $('input[name=searchSubFlag]:checkbox').attr('checked');
				var data = $.extend({}, $.data(this, "data"));
				$.extend(data, {"searchSubFlag":$searchSubFlag}); 
				// callback 구분자 ( 수신, 참조, 숨은참조)
				$.extend(data, {"gubun":'Bcc'}); 
				//var info = {name:$(this).text()};
				result.push(data);//{type:data.type, info:info}
			});
			
			
			
			if(dialogWindow != null) {
				try {	// callback function : dialog 생성시 callback handler 셋팅됨.
					dialogWindow.callback(result);
					dialogWindow.close();
				} catch(e) {}
			} else {
				//iKEP.returnPopup(result);
				
				var toList = "";
                var ccList = "";
                var bccList = "";
                
                //parent.document.getElementById("tempPopAddr").value = JSON.stringify(result);
                //parent.document.getElementById("tempPopAddrBtn").click();
                //iKEP.returnPopup(result);
                for (var i = 0; i < result.length; i++) {
                    /*
                    for (var prop in data[i]) {
                        if (data[i].hasOwnProperty(prop)) {
                            alert("[" + prop + "] " + data[i][prop]);
                        
                        }
                    }
                    */                    
                    if(result[i].gubun=="Receiver") {                    	
                    	switch(result[i].type)
                    	{                    	
                        case 'user':
                        	if(result[i].name.indexOf(result[i].teamName) > 0)
                        	{
                        		toList += ('"'+result[i].name + '"'+"<"+result[i].email+">");                        		
                        	}else
                        	{
                        		toList += ('"'+result[i].name + " " + result[i].teamName +'"'+"<"+result[i].email+">");
                        	}                    		
                    		break;
                        case 'addruser':                    
                            toList += ('"'+result[i].name + '"'+"<"+result[i].email+">");
                            break;
                    	case 'group':
                    		toList += ('#'+result[i].name+"."+result[i].code+'');
                    		break;
                    	case "common":
                    		toList += ('&'+result[i].code+'.'+result[i].name+'');           
                    		break;
                    	case "private":
                    		toList += ('$'+result[i].code+'.'+result[i].name+'');                    	
                    		break;
                    	} 
                    }
                    if(result[i].gubun=="Refer") {
                        switch(result[i].type)
                        {                       
                        case 'user':
                        	if(result[i].name.indexOf(result[i].teamName) > 0)
                            {
                        		ccList += ('"'+result[i].name + '"'+"<"+result[i].email+">");                               
                            }else
                            {
                            	ccList += ('"'+result[i].name + " " + result[i].teamName +'"'+"<"+result[i].email+">");
                            }    
                            break;
                        case 'addruser':                    
                        	ccList += ('"'+result[i].name +'"'+"<"+result[i].email+">");
                            break;
                        case 'group':
                        	ccList += ('#'+result[i].name+"."+result[i].code+'');
                            break;
                        case "common":
                        	ccList += ('&'+result[i].code+'.'+result[i].name+'');           
                            break;
                        case "private":
                        	ccList += ('$'+result[i].code+'.'+result[i].name+'');                       
                            break;
                        }
                        //alert(2222);
                    }
                    if(result[i].gubun=="Bcc") {
                    	switch(result[i].type)
                        {                       
                        case 'user':                    
                        	if(result[i].name.indexOf(result[i].teamName) > 0)
                            {
                                bccList += ('"'+result[i].name + '"'+"<"+result[i].email+">");                               
                            }else
                            {
                                bccList += ('"'+result[i].name + " " + result[i].teamName +'"'+"<"+result[i].email+">");
                            }    
                            break;
                        case 'addruser':                    
                        	bccList += ('"'+result[i].name + '"'+"<"+result[i].email+">");
                            break;
                        case 'group':
                        	bccList += ('#'+result[i].name+"."+result[i].code+'');
                            break;
                        case "common":
                        	bccList += ('&'+result[i].code+'.'+result[i].name+'');           
                            break;
                        case "private":
                        	bccList += ('$'+result[i].code+'.'+result[i].name+'');                       
                            break;
                        }
                        //alert(3333);
                    }
                    
                    if(result.length-1 != i){
                        
                        if(result[i].gubun=="Receiver") toList += ",";
                        if(result[i].gubun=="Refer") ccList += ",";
                        if(result[i].gubun=="Bcc") bccList += ",";
                                                
                    } 
                }
                               
                var frm = document.tmsAddrSender;
                frm.toParams.value = toList;
                frm.ccParams.value = ccList;
                frm.bccParams.value = bccList;
                frm.submit();
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
		}
		else{
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

	function getTreeItemName($li) {
		return $li.find("a:first").text().replace(String.fromCharCode(160), "");
	}
	
	function applyItem() {	// 조직도 및 검색 목록에서 아이템을 선택해서 넘길때
		
		var $activeTab = $("#divTabs").children("ul.ui-tabs-nav").children("li.ui-state-active");	// active tab item
		var $tree = $($activeTab.children("a").attr("href")).find("div.jstree");	// active tree

			var $treeItems = $tree.find("a.jstree-clicked").parent();
			if($treeItems.length <= 0) {
				alert("<ikep4j:message pre='${preMessage}' key='noselect.add'/>");
				return false;
			}			

			if( selectType == 'USER' ){

				var items = [];
				$treeItems.each(function(idx) {
					var $li = $(this);
					if( $.parseJSON($li.abtnApplyttr("data")).type == 'user' || $.parseJSON($li.attr("data")).type == 'addruser' ){
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
					if(tabDiv == "tab-prigroup"){
						if( $treeItems.length != items.length && $.parseJSON($li.attr("data")).hasChild > 0){
							alert("개인그룹은 사용자를 선택하셔야합니다.");
							return;
						}
					}
					items.push($.extend({name:getTreeItemName($li)}, $.parseJSON($li.attr("data"))));
				});
				appendItem(items);
			}
			
	}

	
	function appendItem(addItems) {	// 선택한 목록 추가
		var $result = $("#ulReceiverResult");
		
		var arrAddedItems = [];	// 이미 추가 되어 있는 목록을 뽑아온다.
		if( $result.children().length + addItems.length   > ${selectMaxCnt} ){
			alert("<ikep4j:message pre='${preMessage}' key='select.maxcount'/>");
		}else{

			var result = $result.children().each(function() {
				var data = $.data(this, "data");
				arrAddedItems.push(data.type == "user" ? data.id : data.type == "addruser" ? data.id : data.code);
			});
			var cntAddedItem = 0;
			
			$.each(addItems, function(idx) {				
				if (arrAddedItems.length == 0 || $.inArray(this.type == "user" ? this.id : this.type == "addruser" ? this.id : this.code, arrAddedItems) == -1) {
					var elItem;
					
					if( this.type == "user" ){
						elItem = $.tmpl("tmplResultUserItem", this).appendTo($result);						
					}else{
						elItem = $.tmpl("tmplResultGroupItem", this).appendTo($result);
					}					
					$.data(elItem[0], "data", this);
					cntAddedItem++;
				}
			});
			if(cntAddedItem == 0 && $result.children().length > 0) {
				alert("<ikep4j:message pre='${preMessage}' key='select.already'/>");
			} else {
				$("#cntReceiverSelectedItem").text(arrAddedItems.length + cntAddedItem);
			}
		}
	}

	function applyReferItem() {	// 조직도 및 검색 목록에서 아이템을 선택해서 넘길때
		
		var $activeTab = $("#divTabs").children("ul.ui-tabs-nav").children("li.ui-state-active");	// active tab item
		var $tree = $($activeTab.children("a").attr("href")).find("div.jstree");	// active tree

			var $treeItems = $tree.find("a.jstree-clicked").parent();
			if($treeItems.length <= 0) {
				alert("<ikep4j:message pre='${preMessage}' key='noselect.add'/>");
				return false;
			}
			
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
					appendReferItem(items);
				}
			}else{
				var items = [];
				$treeItems.each(function(idx) {
					var $li = $(this);
					if($.parseJSON($li.attr("data")).type == "common" && $.parseJSON($li.attr("data")).id == null && $.parseJSON($li.attr("data")).hasChild > 0){
						alert("카테고리는 선택 불가능합니다. 그룹을 선택해주세요.");
						return;
					}
					
					if(tabDiv == "tab-prigroup"){
						if( $treeItems.length != items.length && $.parseJSON($li.attr("data")).hasChild > 0){
							alert("개인그룹은 사용자를 선택하셔야합니다.");
							return;
						}
					}
					items.push($.extend({name:getTreeItemName($li)}, $.parseJSON($li.attr("data"))));
				});
				appendReferItem(items);
			}			
	}

	
	function appendReferItem(addItems) {	// 선택한 목록 추가
		var $result = $("#ulReferResult");
		
		var arrAddedItems = [];	// 이미 추가 되어 있는 목록을 뽑아온다.
		if( $result.children().length + addItems.length   > ${selectMaxCnt} ){
			alert("<ikep4j:message pre='${preMessage}' key='select.maxcount'/>");
		}else{

			var result = $result.children().each(function() {
				var data = $.data(this, "data");
				arrAddedItems.push(data.type == "user" ? data.id : data.type == "addruser" ? data.id : data.code);
			});
			var cntAddedItem = 0;
			$.each(addItems, function(idx) {				
				if (arrAddedItems.length == 0 || $.inArray(this.type == "user" ? this.id : this.type == "addruser" ? this.id : this.code, arrAddedItems) == -1) {
					var elItem;
					
					if( this.type == "user" ){
						elItem = $.tmpl("tmplResultUserItem", this).appendTo($result);
					}else{						
						elItem = $.tmpl("tmplResultGroupItem", this).appendTo($result);
					}
					
					$.data(elItem[0], "data", this);
					cntAddedItem++;
				}
			});
			if(cntAddedItem == 0 && $result.children().length > 0) {
				alert("<ikep4j:message pre='${preMessage}' key='select.already'/>");
			} else {
				$("#cntReferSelectedItem").text(arrAddedItems.length + cntAddedItem);
			}
		}
	}
	
	function applyBccItem() {	// 조직도 및 검색 목록에서 아이템을 선택해서 넘길때
		
		var $activeTab = $("#divTabs").children("ul.ui-tabs-nav").children("li.ui-state-active");	// active tab item
		var $tree = $($activeTab.children("a").attr("href")).find("div.jstree");	// active tree

			var $treeItems = $tree.find("a.jstree-clicked").parent();
			if($treeItems.length <= 0) {
				alert("<ikep4j:message pre='${preMessage}' key='noselect.add'/>");
				return false;
			}
			
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
					appendBccItem(items);
				}
			}else{
				var items = [];
				$treeItems.each(function(idx) {
					var $li = $(this);
					if($.parseJSON($li.attr("data")).type == "common" && $.parseJSON($li.attr("data")).id == null && $.parseJSON($li.attr("data")).hasChild > 0){
						alert("카테고리는 선택 불가능합니다. 그룹을 선택해주세요.");
						return;
					}
					
					if(tabDiv == "tab-prigroup"){
						if( $treeItems.length != items.length && $.parseJSON($li.attr("data")).hasChild > 0){
							alert("개인그룹은 사용자를 선택하셔야합니다.");
							return;
						}
					}
					items.push($.extend({name:getTreeItemName($li)}, $.parseJSON($li.attr("data"))));
				});
				appendBccItem(items);
			}
			
	}

	
	function appendBccItem(addItems) {	// 선택한 목록 추가
		var $result = $("#ulBccResult");
		
		var arrAddedItems = [];	// 이미 추가 되어 있는 목록을 뽑아온다.
		if( $result.children().length + addItems.length   > ${selectMaxCnt} ){
			alert("<ikep4j:message pre='${preMessage}' key='select.maxcount'/>");
		}else{

			var result = $result.children().each(function() {
				var data = $.data(this, "data");
				arrAddedItems.push(data.type == "user" ? data.id : data.type == "addruser" ? data.id : data.code);
			});
			var cntAddedItem = 0;
			$.each(addItems, function(idx) {				
				if (arrAddedItems.length == 0 || $.inArray(this.type == "user" ? this.id : this.type == "addruser" ? this.id : this.code, arrAddedItems) == -1) {
					var elItem;
					
					if( this.type == "user" ){
						elItem = $.tmpl("tmplResultUserItem", this).appendTo($result);						
					}else{
						elItem = $.tmpl("tmplResultGroupItem", this).appendTo($result);
					}
					
					$.data(elItem[0], "data", this);
					cntAddedItem++;
				}
			});
			if(cntAddedItem == 0 && $result.children().length > 0) {
				alert("<ikep4j:message pre='${preMessage}' key='select.already'/>");
			} else {
				$("#cntBccSelectedItem").text(arrAddedItems.length + cntAddedItem);
			}
		}
	}
			
	$.template("tmplResultGroupItem", '<li>\${name}</li>');
	$.template("tmplResultUserItem", '<li>\${userName} \${jobTitleName} \${teamName}</li>');
})(jQuery);

// -->

</script>

<div id="popup" style="min-width:500px">

	
	<!--popup_title Start-->
	<div id="popup_title_2">
		<div class="popup_bgTitle_l"></div>
		<h1><ikep4j:message pre='${preHeader}' key='title' /></h1>
		<a href="javascript:parent.window.close()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
	</div>
	<!--//popup_title End-->
				

	<!--popup_contents Start-->
	<div id="popup_contents">

		<!--tab Start-->		
		<div id="divTabs" class="iKEP_tab">
			<ul>
				<li><a href="#tab-orggroup"><ikep4j:message pre='${preControl}' key='organization'/></a></li>
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
				<li><a href="#tab-searchgroup"><ikep4j:message pre='${preControl}' key='search'/></a></li>
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
						
						<div id="tab-orggroup"  class="shuttleTree">
							<div id="treeDept"></div>
						</div>
					
							
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
						
					</div>
				</div>
				
				<div class="shuttle_m">
					<ul style="margin-top:30px;"><!--margin-top을 셔틀높이에 맞게 설정할 것-->
						<li><a id="btnReceiverItemAdd" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_add.gif' />" alt="<ikep4j:message pre='${preButton}' key='reset'/>" /></a></li>
						<li><a id="btnReceiverItemRemove" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_del.gif' />" alt="<ikep4j:message pre='${preButton}' key='delete'/>" /></a></li>
						<li><a id="btnReceiverItemRemoveAll" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_reset.gif' />" alt="<ikep4j:message pre='${preButton}' key='reset'/>" /></a></li>
					</ul>
					<ul style="margin-top:45px;"><!--margin-top을 셔틀높이에 맞게 설정할 것-->
						<li><a id="btnReferItemAdd" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_add.gif' />" alt="<ikep4j:message pre='${preButton}' key='reset'/>" /></a></li>
						<li><a id="btnReferItemRemove" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_del.gif' />" alt="<ikep4j:message pre='${preButton}' key='delete'/>" /></a></li>
						<li><a id="btnReferItemRemoveAll" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_reset.gif' />" alt="<ikep4j:message pre='${preButton}' key='reset'/>" /></a></li>
					</ul>
					<ul style="margin-top:50px;"><!--margin-top을 셔틀높이에 맞게 설정할 것-->
						<li><a id="btnBccItemAdd" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_add.gif' />" alt="<ikep4j:message pre='${preButton}' key='reset'/>" /></a></li>
						<li><a id="btnBccItemRemove" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_del.gif' />" alt="<ikep4j:message pre='${preButton}' key='delete'/>" /></a></li>
						<li><a id="btnBccItemRemoveAll" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_reset.gif' />" alt="<ikep4j:message pre='${preButton}' key='reset'/>" /></a></li>
					</ul>
				</div>
				
				<div class="shuttle_r">
					<div class="sbox">
						<div class="shuttleTitle" style="text-align:center">
							수신자<ikep4j:message pre='${preButton}' key='select'/> (<span id="cntReceiverSelectedItem">0</span>/<span>${selectMaxCnt}</span>)
						</div>
						<div class="shuttleCon" style="height:60px;">
							<ul id="ulReceiverResult" class="list-selectable">
								<li></li>
							</ul>
						</div>
					</div>					
				</div>	
				<div class="shuttle_r">
					<div class="sbox">
						<div class="shuttleTitle" style="text-align:center">
							참조자<ikep4j:message pre='${preButton}' key='select'/> (<span id="cntReferSelectedItem">0</span>/<span>${selectMaxCnt}</span>)
						</div>
						<div class="shuttleCon" style="height:60px;">
							<ul id="ulReferResult" class="list-selectable">
								<li></li>
							</ul>
						</div>
					</div>					
				</div>	
				<div class="shuttle_r">
					<div class="sbox">
						<div class="shuttleTitle" style="text-align:center">
							숨은참조자<ikep4j:message pre='${preButton}' key='select'/> (<span id="cntBccSelectedItem">0</span>/<span>${selectMaxCnt}</span>)
						</div>
						<div class="shuttleCon" style="height:60px;">
							<ul id="ulBccResult" class="list-selectable">
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
	

			
</div>
<!--//popup End-->
<form action="<c:out value="${mailserver}"/>/public/email_return.jsp" method="post" id="tmsAddrSender" name="tmsAddrSender">
    <input type="hidden" name="toParams" />
    <input type="hidden" name="ccParams" />
    <input type="hidden" name="bccParams" />
</form>