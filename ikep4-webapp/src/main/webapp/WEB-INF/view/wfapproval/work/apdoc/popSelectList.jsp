<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.wfapproval.admin.apform.process.header" />
<c:set var="preProcess" value="ui.wfapproval.admin.apform.process" />
<c:set var="preButton"  value="ui.wfapproval.common.button" />
<c:set var="preMessage" value="ui.wfapproval.common.message" />
<c:set var="preSearch"  value="ui.wfapproval.common.searchCondition" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>iKEP 4.00</title>
<script type="text/javascript" src="/ikep4-webapp/base/js/jquery/jquery.jstree.pack.js"></script>
<script type="text/javascript" src="/ikep4-webapp/base/js/jquery/plugins.pack.js"></script>
<script type="text/javascript" src="/ikep4-webapp/base/js/etc.plugins.pack.js"></script>

<script type="text/javascript"> 
var groupTree;
var $groupTree;

var dialogWindow = null;
var fnCaller;

(function($) {
	
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
					//data : iKEP.arrayToTreeData({"items":[{"name":"LG CNS","hasChild":"11","parent":null,"code":"A00000","type":"group"}]}.items),
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
	
	/**
	 * 프로세스 화면 생성
	 */
	f_viewProcessInfo = function (){
		
		if(! selectData) return;
		
		if (selectData.name) {
			//상세화면 프로세스명 설정.
			$("#selectProcess").text(" > " + selectData.name);
		}
		
		var url = '<c:url value="/workflow/modeler/prism.do?mode=model&processId="/>' + selectData.id 
				+ "&version=" + selectData.ver 
				+ "&processType=workflow"
				+ "&minimapView=false" 
				+ "&scale=0.8";
		
		//alert(url);
		$("#processDetail").html('<iframe id="prism" src="' + url + '" frameborder="0" height="100%" width="100%;" scrolling="no"></iframe>');
	};
	
	
	$(document).ready(function() {
		
		fnCaller = function (params, dialog) {
			if(params) {
				//alert(params.id);
				//selectData = params;
				
				selectData = {
					id		: params.id,
					name	: params.name,
					type	: params.type,
					ver		: params.ver
				};
				appendItem(params.items);
				//프로세스 화면생성
				//f_viewProcessInfo();
			}
			
			dialogWindow 	= dialog;
		};
		$("#btnClose").click(function() {
			dialogWindow.close();
		});
		
		//프로세스 목록
		//$("#processList").load('<c:url value="/wfapproval/admin/apform/ajaxListApFormProcess.do"/>')
   		//.error(function(event, request, settings) { alert("error"); });
		
		createGroupTree();
		
		
		
		$( "#ulResult" ).sortable({
			placeholder: "ui-state-highlight",
			revert:true
		});
		
		$("#ulResult > li").live({
		    "click" : function(event){
		    	selectItem(event.target);
		    }
		});

		//$( "#ulResult" ).disableSelection();
		
		$("#divTab1").tabs();
		
		$jq("#btnAddrControl").click(function() {
			
			var items = [];
			var $sel = $jq("#AddrControlForm").find("[name=addrGroupList]");
			$jq.each($sel.children(), function() {
				items.push($.data(this, "data"));
			});

			$controlType = $jq('input[name=controlType]:hidden').val() ;
			$controlTabType = $jq('input[name=controlTabType]:hidden').val() ;
			$selectType = $jq('input[name=selectType]:hidden').val() ;
			$selectMaxCnt = $jq('input[name=selectMaxCnt]:hidden').val() ;
			$searchSubFlag = $jq('input[name=searchSubFlag]:hidden').val() ;

			var dialog = new iKEP.Dialog({
				title: "주소록",
				url: "<c:url value='/support/addressbook/addresbookPopup.do'/>"+"?controlType=" + $controlType + "&controlTabType=" + $controlTabType + "&selectType=" + $selectType + "&selectMaxCnt=" + $selectMaxCnt + "&searchSubFlag=" + $searchSubFlag,
				modal: true,
				width: 700,
				height: 550,
				//width:1200,
				//height:700,
				params : {search:"keyword", items:items },
				callback : function(result) {
					
					$sel.empty();
					
					$jq.each(result, function() {
						
						var tpl = "";
						
						switch(this.type) {
							case "group" : tpl = "addrBookItemGroup"; break;
							case "user" : tpl = "addrBookItemUser"; break;
						}
						
						var $option = $.tmpl(tpl, this).appendTo($sel);
						
						
						$.data($option[0], "data", this);
						
						if( this.searchSubFlag == true ){
							$jq('input[name=searchSubFlag]:hidden').val("true") ;
						}
						

					})
				}
			});
		});
		$.template("addrBookItemUser", '<option value="\${id}">\${id}    \${name}/\${jobPosition}/\${teamName}</option>');
		$.template("addrBookItemGroup", '<option value="\${code}">\${name}</option>');
		
		tab = $("#divTab1").tabs({
			select : function(event, ui) {
				switch($(ui.panel).attr("id")) {
					case "tabs-1" : createGroupTree();
						break;
					case "tab-pubgroup" :
						break;
					case "tab-prigroup" :
						break;
				}
			}
		});
		
		$("#ulResult").selectable();
		$("#treeDept").bind("dblclick", dblClickOrg);
		$("#btnItemAdd").click(applyItem);
		$("#btnItemRemove").click(function() {
			var $items = $("#ulResult > li.ui-selected");
			if($items.length > 0) {
				$items.remove();
				$("#cntSelectedItem").text($("#ulResult>li").size());
			} else {
				alert("삭제할 아이템을 선택해주세요.");
			}
		});
		
		$("#btnItemRemoveAll").click(function() {
			var $items = $("#ulResult>li");

			$items.each(function(idx) {
				$items.remove();
				$("#cntSelectedItem").text($("#ulResult>li").size());
			});
		});
		
		
		//사용자정의 저장
		$("#btnItemRemoveAllUp").click(function() {
			var result = [];
			//$( "#ulResult" ).sortable();
			//$( "#ulResult" ).disableSelection();
			
			var apprTitle= $("#apprTitle").val();
			
			if ( apprTitle == ''){
				alert(' 결재선명을 입력 후 저장버튼을 클릭해 주세요.');
				return;
			}
			
			var $resultItems = $("#ulResult").children();
			
			if($resultItems.length <= 0) {
				alert("적용할 아이템을 선택하지 않으셨습니다.");
				return false;
			}
			
			$.each($resultItems, function() {
				var $searchSubFlag = $('input[name=searchSubFlag]:checkbox').is(":checked");				
				var data = $.extend({}, $.data(this, "data"));
				$.extend(data, {"searchSubFlag":$searchSubFlag}); 
				var info = {name:$(this).text()};
				result.push(data);//{type:data.type, info:info}
				
			});
			var $sel = $jq("#nForm").find("[name=addrGroupList]");
			$jq.each(result, function() {
				
				var tpl = "";
					
				switch(this.type) {
					case "group" : tpl = "addrBookItemGroup"; break;
					case "user" : tpl = "addrBookItemUser"; break;
				}
					
				var $option = $.tmpl(tpl, this).appendTo($sel);
					
					
				$.data($option[0], "data", this);
					
				if( this.searchSubFlag == true ){
					$jq('input[name=searchSubFlag]:hidden').val("true") ;
				}
					

			})
			
			var idlist = [];
			var itemstr ="";
			$jq('#workers option').each(function(){idlist.push(this.value)});
			$jq.each(idlist,function(index, item) {itemstr += item + ',';});
			
			
			$jq('#etcName').val(itemstr.substring(0, itemstr.length-1));

			
			$.post('<c:url value="/wfapproval/work/apdoc/ajaxApplyApUser.do"/>', $("#nForm").serialize()).success(function(data){
				alert("Success");
			}).error(function(event, request, settings){
               alert("error");
            });
			///////////////////////////////
			//var $items = $("#ulResult>li");

			//$items.each(function(idx) {
			//	alert($items.length);  //길이
			//	//alert($items.);
			//	$items.remove();
			//	$("#cntSelectedItem").text($("#ulResult>li").size());
			//});
		});
		
		////LDK DOWN
		$("#btnItemRemoveAllUp1").click(function() {
			
			var $items = $("#ulResult>li");
			
			$items.each(function(idx) {
				$items.remove();
				$("#cntSelectedItem").text($("#ulResult>li").size());
			});
			
			//var keyword = $("#schKeyword").val();
			var processId= $("#processIdUser").val();
			
			if ( processId == ''){
				alert('사용자 결재선을 선택 후 적용버튼을 클릭해 주세요.');
				return;
			}
			
			var $result = $("#ulResult");
			var arrAddedItems = [];
			var result = $result.children().each(function() {
				
				var data = $.data(this, "data");
				arrAddedItems.push(data.type == "group" ? data.code : data.id);
			});
			var items = [];
			var $li = $(this);
			
			$jq.get('<c:url value="/wfapproval/work/apdoc/ajaxRequestApUserList.do?processId='+processId+'" />')
		    .success(function(message) { 
		    	//iKEP.debug(message.items);
		    	var dbItems = []; var apprType ="";
		    	for(var i=0 ; i<message.items.length ; i++) {
		    		
		    		if (message.items[i].apprType == '0'){
		    			apprType ="결재";
		    		}else{
		    			apprType ="합의";
		    		}
		    			
		    		dbItems.push({// 공통필수
						type:"user",
						id:""+message.items[i].apprType+"^"+message.items[i].apprUserId,
						jobTitle:""+message.items[i].jobPositionName,
						name:""+ apprType+" "+message.items[i].userName,
						searchSubFlag:false,
						teamName:""+message.items[i].teamName
					});
		    	}
		    	appendItem(dbItems);
		    	
		    })    
			.error(function(event, request, settings) { alert("error"); });
			
			
			//$jq.get('<c:url value="/support/popup/requestGroupChildren.do"/>'
			//$jq.get('<c:url value="/support/message/readMessage.do?messageId=100000114526"/>'		
					//{typeId:typeId},						
						//.success(function(data1) { 
						//	iKEP.debug(data1);
						//	alert("1");

					    //})    
						//.error(function(event, request, settings) { alert("error"); });  
						//alert(data1.length);
						//for(var i=0 ; i<data1.length ; i++) {
						//	iKEP.debug(data[i]);
						//}
						//$jq("#categoryId").empty();
						
						//for(var i=0 ; i<data.length ; i++) {
						//	$jq("<option/>").attr("value",data[i].categoryId).text(data[i].categoryName).appendTo("#categoryId");
						//}
						//if(data.length == 0) {
						//	$jq('<option/>').attr('value','').text('<ikep4j:message pre="${preScript}" key="noCategory" />').appendTo('#categoryId');
						//}
					//}

			//)
			
			
			
			////items.push($.extend({name:$li.find("a:first").text()}, $.parseJSON($li.attr("data"))));
			
		});
		
		//$('input[name=searchSubUserFlag]:checkbox').click(function() {
			//viewOrgControl();
		//});
		//,"searchSubUserFlag" : $('input[name=searchSubUserFlag]:checkbox').is(':checked')
		
		
		$("#btnApply").click(function() {
			var result = [];
			var $resultItems = $("#ulResult").children();
			
			if($resultItems.length <= 0) {
				alert("적용할 아이템을 선택하지 않으셨습니다.");
				return false;
			}
			
			$.each($resultItems, function() {
				var $searchSubFlag = $('input[name=searchSubFlag]:checkbox').is(":checked");
				var $apprLineType = $('input[name=apprLineType]:checked').val();
				var data = $.extend({}, $.data(this, "data"));
				$.extend(data, {"searchSubFlag":$searchSubFlag}); 
				$.extend(data, {"apprLineType":$apprLineType}); 
				//var info = {name:$(this).text()};
				result.push(data);//{type:data.type, info:info}
			});
			

			try {	// callback function : dialog 생성시 callback handler 셋팅됨.
				dialogWindow.callback(result);
				dialogWindow.close();
			} catch(e) {}
		});
		
		$("#btnSearch").click(function(){
			var keyword = $("#schKeyword").val();
			var selectType = $('input[name=selectType]:hidden').val()
			if(!keyword) {
				alert("검색어를 입력해주세요.");
				return false;
			}
			
			//tab.tabs('select', '#tab-searchgroup');
			$("#divSearchResult").jstree("destroy").empty();
			$("#divSearchResult").unbind("dblclick");
			
			$.post("<c:url value='/support/addressbook/requestSearchGroup.do' />", {keyword:keyword,selectType:selectType})
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
	
	selectItem = function(item) {
		unselect();
		$(item).addClass("ui-selected");
		//if(isToggle(item)){
		//	clearSelect();
		//}else{
		//	$(item).addClass("ui-selected");
		//	currCategoryId = $(item).attr("id");
		//	showBtn();
		//}
	}
	
	unselect = function() {		
		$("#ulResult > li").removeClass("ui-selected");
	}
	
	function dblClickOrg() {	// 조직도 및 검색 목록에서 아이템을 선택해서 넘길때
		//핵심 tabname정확히
		//alert("Here");
		var $activeTab = $("#divTab1").children("ul.ui-tabs-nav").children("li.ui-state-active");	// active tab item
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
						alert("하위 조직이나 사용자가 없습니다.");
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
						alert("그룹은 Add 버튼을 사용해야 추가 할수 있습니다.");
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
	
	function applyItem() {	// 조직도 및 검색 목록에서 아이템을 선택해서 넘길때
		
		//핵심 tab명을 정확히 입력해야 함.
		var $activeTab = $("#divTab1").children("ul.ui-tabs-nav").children("li.ui-state-active");	// active tab item
		
		
		var $tree = $($activeTab.children("a").attr("href")).find("div.jstree");	// active tree
		
			var $treeItems = $tree.find("a.jstree-clicked").parent();
			if($treeItems.length <= 0) {
				alert("추가할 항목을 선택해주세요.");
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
					alert("사용자 조회시에는 부서는 선택되지 않습니다.");
				}
				if( items.length > 0){
					
					appendItem(items);
				}
			}else{
				
				var items = [];
				$treeItems.each(function(idx) {
					
					
					var $li = $(this);
					//alert($.extend({name:$li.find("a:first").text()}, $.parseJSON($li.attr("data"))));
					items.push($.extend({name:$li.find("a:first").text()}, $.parseJSON($li.attr("data"))));
					
				});
				appendItem(items);
			}

		
	}
	
	function appendItem(addItems) {	// 선택한 목록 추가
		
		var $result = $("#ulResult");
		//iKEP.debug($result.children());
		
		var selectType = $('input[name=apprType]:checked').val();
		
		
		
		var arrAddedItems = [];	// 이미 추가 되어 있는 목록을 뽑아온다.
		//alert($result.children().length);
		//alert(addItems.length);
		
		
		//value 추출해서 결재,합의 건수 체크
		var kcnt=0;
		var hcnt=0;
		
		$result.children().each(function() {				
				var data = $.data(this, "data");
				//iKEP.debug(data.id);
				if ( data.id.substring(0,1) == "0") kcnt++;
				if ( data.id.substring(0,1) == "1") hcnt++;
				
		});
		
		$.each(addItems, function(idx) {
			if ( selectType == "0") kcnt++;
			if ( selectType == "1") hcnt++;
		});
		
		//alert("결재갯수"+(kcnt));
		//alert("합의개수"+(hcnt));		
		//
		

		if( $result.children().length + addItems.length   > ${selectMaxCnt} ){
			alert("최대 선택 갯수를 초과 하였습니다.");
		}else if( kcnt >${selectApprLineCnt} ){
			alert("결재 최대 선택 갯수를 초과 하였습니다.");
		}else if( hcnt >${selectDiscussLineCnt} ){
			alert("합의 최대 선택 갯수를 초과 하였습니다.");
		}else{
			var result = $result.children().each(function() {
				
				var data = $.data(this, "data");
				//iKEP.debug(data.id);
				arrAddedItems.push(data.type == "group" ? data.code : data.id);
			});
			var cntAddedItem = 0;
			$.each(addItems, function(idx) {
				
				
				if ( this.id.indexOf("^") == -1 )
				{
					this.id = selectType + '^'+this.id;
					
					if ( selectType == "0"){
						this.name = "결재"+ this.name;
					}else{
						this.name = "합의"+ this.name;
					}
				}
				if (arrAddedItems.length == 0 || $.inArray(this.type == "group" ? this.code : this.id, arrAddedItems) == -1) {
					var elItem;
					
					if( this.type == "group" ){
						elItem = $.tmpl("tmplResultGroupItem", this).appendTo($result);
					}else{
						elItem = $.tmpl("tmplResultUserItem", this).appendTo($result);
						
						
						
					}
					
					$.data(elItem[0], "data", this);
					cntAddedItem++;
					
					
				}
			});
			
			if(cntAddedItem == 0 && $result.children().length > 0) {
				alert("선택하신 항목은 이미 추가 된 항목입니다.");
			} else {
				$("#cntSelectedItem").text(arrAddedItems.length + cntAddedItem);
				
			}
		}
	}
	
	$.template("tmplResultGroupItem", '<li class="ui-state-default">\${name}</li>');
	$.template("tmplResultUserItem", '<li class="ui-state-default">\${name}/\${jobTitle}/\${teamName}</li>');
	
	
})(jQuery);



</script>


</head>
 
<body>
 
<!--popup Start-->
<div id="popup" style="min-width:900px"><!--다단의 경우 검색영역이나 테이블이 좁아지면 컨텐츠가 깨지므로 min-width를 상황에 따라 설정할 것-->
 
	<!--popup_title Start-->
	<!--  <div id="popup_title">
		<h1>결재선 지정</h1>
		<a href="#a"><img src="../../../base/images/icon/ic_close_popup.gif" alt="닫기" /></a>
	</div>-->
	<!--//popup_title End-->
	
	<!--popup_contents Start-->
	<div id="popup_contents">
 
		<!--tab Start-->		
		<div id="divTab1" class="iKEP_tab">
			<ul>
				<!--  <li><a href="#tabs-1">결재선 검색</a></li>-->
				<li><a href="#tabs-1">사용자 정의 결재선</a></li>
			</ul>				
		<!--  </div>-->		
		<!--//tab End-->
		<div class="tab_con">
		
		<!--  <div id="tabs-1">
		

		

		
		</div>-->
		
		<div id="tabs-1">
		<form id="nForm">
							<div class="shuttletab_ins">
				<label><!--  <input name="searchSubFlag" class="checkbox" title="하위부서 포함" type="checkbox" value="" />하위부서 포함--></label>
				<input name="controlTabType" title="" type="hidden" value="0:0:0:0" />
				<input name="controlType" title="" type="hidden" value="ORG" />
				<input name="selectType" title="" type="hidden" value="USER" />
			</div>
							
		<div class="blockShuttle">
		<div class="shuttle_l" style="width:40%; ">
		
			
				<div id="tab-orggroup" >
					<div>
					
						<div class="sbox" style="height:400px; overflow:auto;">
					
							<div class="shuttleTree">
								<div id="treeDept"></div>
							</div>
						</div>
					</div>
				</div>
						
				<c:if test="${dispPubTabFlag == 'true'}">
				<div id="tab-pubgroup">
					<div>
						<div class="sbox" style="height:300px; overflow:auto;">
							<div class="shuttleTree">
								<div id="treeDept"></div>
							</div>
						</div>
					</div>
				</div>
				</c:if>
						
				<c:if test="${dispPriTabFlag == 'true'}">
				<div id="tab-prigroup">
					<div>
						<div class="sbox" style="height:300px; overflow:auto;">
							<div class="shuttleTree">
								<div id="treeDept"></div>
							</div>
						</div>
					</div>
				</div>
				</c:if>
						
				<c:if test="${dispColTabFlag == 'true'}">
				<div id="tab-colgroup">
					<div>
						<div class="sbox" style="height:300px; overflow:auto;">
							<div class="shuttleTree">
								<div id="treeDept"></div>
							</div>
						</div>
					</div>
				</div>
				</c:if>
						
				<c:if test="${dispSnsTabFlag == 'true'}">
				<div id="tab-snsgroup">
					<div>
						<div class="sbox" style="height:300px; overflow:auto;">
							<div class="shuttleTree">
								<div id="treeDept"></div>
							</div>
						</div>
					</div>
				</div>
				</c:if>
						
				<!--  <div id="tab-searchgroup">
					<div class="shuttleSearch">
					<input type="text" title="이름/부서" class="inputbox" id="schKeyword" value="" size="20" />
					<a id="btnSearch" href="#"><img src="<c:url value='/base/images/theme/theme01/basic/ic_search.gif' />" alt="검색" /></a> 
					</div>
					<h4>검색 결과</h4>
						<div class="sbox" style="height:250px; overflow:auto;">
							<div class="shuttleTree">
								<div id="divSearchResult" style="height:250px; overflow:auto;"></div>
							</div>
						</div>
				</div>-->
				
			</div>

		<div class="shuttle_m" style="margin-top:140px;"><!--margin-top을 셔틀높이에 맞게 설정할 것-->
			<div class="centerBox"> 
				<ul>
					<li><strong>결재방법</strong></li>				
					<li><input type="radio" class="radio" title="" name="apprType" value="0" <c:if test="${isAppr eq 'N'}">disabled</c:if><c:if test="${isAppr eq 'Y'}">checked</c:if>/>결재</li>
					<li><input type="radio" class="radio" title="" name="apprType" value="1" <c:if test="${isDiscuss eq 'N'}">disabled</c:if><c:if test="${isDiscuss eq 'Y' && isAppr eq 'N'}">checked</c:if>/>합의</li>
					<!-- <li><input type="radio" class="radio" title="" name="apprType" disabled value="1" />합의</li> -->													
					<li><a id="btnItemAdd" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_add.gif' />" alt="Add" /></a></li>
					<li><a id="btnItemRemove" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_del.gif' />" alt="Delete" /></a></li>
					<li><a id="btnItemRemoveAll" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_reset.gif' />" alt="Reset" /></a></li>
					
					<li><input type="radio" class="radio" title="" name="apprLineType" value="S" <c:if test="${apprLineType eq 'S'}">checked</c:if>/>직렬합의</li>
					<li><input type="radio" class="radio" title="" name="apprLineType" value="P" <c:if test="${apprLineType eq 'P'}">checked</c:if>/>병렬합의</li>
				</ul>				
			</div>									
		</div>

			<!--  <div class="shuttle_m">
				<ul style="margin-top:140px;">
					<li><input type="radio" class="radio" title="" name="apprType" value="0" checked />결재</li>
					<li><input type="radio" class="radio" title="" name="apprType" value="1" />합의</li>
					
					<li><a id="btnItemAdd" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_add.gif' />" alt="Add" /></a></li>
					<li><a id="btnItemRemove" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_del.gif' />" alt="Delete" /></a></li>
					<li><a id="btnItemRemoveAll" href="#a"><img src="<c:url value='/base/images/common/btn_shuttle_reset.gif' />" alt="Reset" /></a></li>

				</ul>
			</div>-->
			
			

			
			
			
			
			
			
			
			
			
			
			
			
			
			<div class="blockRight" style="width:46%; border:2px solid #eee; padding:10px 10px 0 10px;">
				
				<div class="sbox">
				
				<div class="subTitle_2 noline">
				<h3>결재선 정보</h3>
				</div>
				
			
							<!--blockSearch Start-->
			<div class="blockSearch">
				<div class="corner_RoundBox03">
					
					<table summary="검색테이블">
						<caption></caption>
						<tbody>
							<tr>
								<th scope="row" width="5%">사용자 결재선</th>
								<td width="65%">
									<select id="processIdUser" name="processIdUser" title="사용자 결재선">
										<option value="">선택</option>
			<c:forEach var="apdoc" items="${apdocApUserList}" varStatus="apDocItem">
			<option value="${apdoc.processId}" >${apdoc.processName}</option>
			</c:forEach>
									</select>
								</td>								
							</tr>
						</tbody>
					</table>
					<div class="searchBtn"><a id="btnItemRemoveAllUp1" href="#a"><span>적용</span></a></div> 
 
					
					<!--  <div class="l_t_corner"></div>
					<div class="r_t_corner"></div>
					<div class="l_b_corner"></div>
					<div class="r_b_corner"></div>-->				
				</div>
			</div>	
			<!--//blockSearch End-->			
				
				
				
				
				
				
				
				
				
				
				
				<!--  &nbsp;<b>사용자 결재선</b>-->
				<!--  <select id="processId" name="processId" title="사용자정의선택">
			<option value="">선택</option>
			<c:forEach var="apdoc" items="${apdocApUserList}" varStatus="apDocItem">
			<option value="${apdoc.processId}" >${apdoc.processName}</option>
			</c:forEach></select>&nbsp;&nbsp;<a id="btnItemRemoveAllUp1" href="#a"><b>적용</b></a>-->
			
					<div class="shuttleCon" style="height:250px;">
						<ul id="ulResult" class="list-selectable"></ul>
					</div>
					
					
					
					<!--blockSearch Start-->
			<div class="blockSearch">
				<div class="corner_RoundBox03">
					
					<table summary="검색테이블">
						<caption></caption>
						<tbody>
							<tr>
								<!--  <td scope="row" width="1%">
									
								</td>-->
								<th scope="row" width="5%">결재선명</th>
								<td width="65%"><input type="text" class="inputbox" id="apprTitle" name="apprTitle" title="결재선명" size="35" /></td>								
							</tr>
						</tbody>
					</table>
					<div class="searchBtn"><a id="btnItemRemoveAllUp" href="#a"><span>Search</span></a></div> 
 
					
					<div class="l_t_corner"></div>
					<div class="r_t_corner"></div>
					<div class="l_b_corner"></div>
					<div class="r_b_corner"></div>				
				</div>
			</div>	
			<!--//blockSearch End-->
					
					
					
					
					
					
					
					<!--  &nbsp;<b>결재선명</b>&nbsp;<input type="text" id="apprTitle" name="apprTitle" value="" />
					<a id="btnItemRemoveAllUp" href="#a"><b>저장</b></a>-->
				</div>
				<div align="right">
					<!--  total(<span id="cntSelectedItem">0</span>/<span>${selectMaxCnt}</span>)-->
				</div>
			</div>		
			<div class="clear"></div>	
			
			<input type="hidden" name="registUserId" value="${user.userId}" />
			
             
	        <input type="hidden" name="etcName" id="etcName"/>
			<!--  <select name="addrGroupList" type="hidden" multiple="multiple" size="5" style="width:300px;height:50px;display:none;"></select>-->
			<select id="workers" name="addrGroupList" type="hidden" multiple="multiple" size="5" style="width:300px;height:50px;display:none;"></select>
			
			</form>
		</div>
		<!--//blockShuttle End-->

		<!--blockButton Start-->
		<div class="blockButton suttle_btn">
			<ul>
				<li><a id="btnApply" class="button"><span>적용</span></a></li>
				<li><a id="btnClose" class="button"><span>닫기</span></a></li>
			</ul>
		</div>
			
		</div>
		</div>
				
	</div>
	<!--//popup_contents End-->
 
	<!--popup_footer Start-->
	<div id="popup_footer"></div>
	<!--//popup_footer End-->
			
</div>
<!--//popup End-->
 
</body>
</html>
