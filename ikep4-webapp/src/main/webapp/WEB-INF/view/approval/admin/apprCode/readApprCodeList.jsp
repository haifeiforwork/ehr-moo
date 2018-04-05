<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.approval.apprCode.header" /> 
<c:set var="preList"    value="ui.approval.apprCode.list" />
<c:set var="preButton"  value="ui.approval.common.button" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>

<script type="text/javascript">
<!-- 
	var codeTree;
	var $treeDiv;
	var initCodeName = "Code Manager";
	
	var debug = true;
	
	/**
	 * Debug Exception 메세지를 반환한다.
	 * 
	 * @param {Object} sT Exception Title
	 * @param {Object} oE Exception
	 */
	function DebugE(sT, oE)
	{
		var sMsg =	"Javascript Debug \n\n"		+
					"[Title		: "+sT+"]\n" 	+
					"[Message	: ("+(oE.number & 0xFFFF)+")"+oE.description+"]\n\n";
					
		if(debug) alert(sMsg);
	}
	
	(function($) {
		
		/**
		 * 트리객체 초기화
		 * 
		 * @param {Object} obj
		 */
		f_InitTree = function(obj){
			
			try {
				if(!codeTree) {
					codeTree = $treeDiv.jstree({
						plugins		: ["themes", "ui", "dnd", "json_data", "types", "contextmenu", "search", "sort", "callback"],
						contextmenu	: {
										items				: customMenu,
										select_node 		: false,
							            show_at_node 		: true,
							            hide_on_mouseleave 	: true
						},
						json_data 	: {
										data : iKEP.arrayToTreeData(${codeItems}.items),
										ajax : {
												url 	: "<c:url value="/approval/admin/apprCode/ajaxTreeCodeChild.do"/>",
												data 	: function ($el) {	//$el : current item 
															return { 
																	"codeId" : $el.attr ? $el.attr("code") : -1 
																	}; },
												success : function(data) {
															//return iKEP.arrayToTreeData(data.items); }
															return iKEP.arrayToTreeData(data); }
												}
						},
						ui			: {
					                    select_limit				: 1,
					                    select_multiple_modifier	: "ctrl",
					                    //selected_parent_close		: "select_parent",	//false
            							select_prev_on_delete 		: true,
            							initially_select            : [ "node_4" ]

					    },
						/**
						 * 트리 스타일 설정 : 각 아이템 아이콘, 연결선, 스킨(classic)
						 */
						/*
					    themes		: {
					                    theme		: "classic",	//apple
					                    dots		: true,
					                    icons		: true
					    },
					    */
						types 		: {
							            root 		: {select_node : false}
						},
					    lang 		: {
								        new_node    : "New folder",	
								        loading     : "Loading ..."
					    },
						search 		: {
							                case_insensitive 	: true,
								            ajax 				: {
													                async 	: true,
													                //type 	: 'POST',
													                url 	: "<c:url value="/approval/dadmin/apprCode/ajaxTreeCodeChild.do"/>",
																	data 	: function ($el) {	//$el : current item 
																				return { 
																						codeId : $el.attr ? $el.attr("code") : -1 
																						}; 
																	},
																	success : function(data) {
																				//return iKEP.arrayToTreeData(data.items); }
																				return iKEP.arrayToTreeData(data); 
																	}
											}
						}
					})
					.bind("select_node.jstree", function (event, data) {
						//alert(data.rslt.obj.html());
						//var codeId 		= $(data.rslt.obj).attr("code");
						//var codeName 	= data.rslt.obj.children("a").text();
						
						//코드 상세정보 가져오기
						f_ApCodeDetailForm(data.rslt.obj);
					})
					.bind("search.jstree", function (e, data) {
			            alert("Found " + data.rslt.nodes.length + " nodes matching '" + data.rslt.str + "'.");
			        });
				}
			} catch (ex) {
				DebugE("f_InitTree", ex);
			}
		};
		
		/**
		 * Context Menu 정의 및 Auction 구현
		 * @param {Object} node
		 */
		customMenu = function (node) {
		    // The default set of all items
		    var items = {
		        createItem: { // The "Create" menu item
		            label: "Create Child",
		            action: function (obj) {
						//하위코드 등록
						f_ApCodeCreateForm(obj);
					}
		        },
		        editItem: { // The "Edit" menu item
		            label: "Edit",
		            action: function (obj) {
						//코드 상세정보 가져오기
						f_ApCodeDetailForm(obj);
					}
		        }
		    };
			
			//Node Selected
			this.select_node(node, true);
		
		    if ($(node).hasClass("folder")) {
		        // Delete the "delete" menu item
		        delete items.deleteItem;
		    }
		
		    return items;
		};
		
		/**
		 * 코드정보 조회
		 * @param {Object} selectData : Node Info
		 */
        f_ApCodeDetailForm = function(selectData){
			
			var codeId 		= $(selectData).attr("code");
			var codeName 	= selectData.children("a").text();
						
			if (codeId == undefined) {
				return false;
			}
			
			//코드 상세화면 코드명 설정.
			$("#selectApCodeName").text("("+$.trim(codeName)+")");
			
			//코드 상세화면
			$("#codeDetail").load('<c:url value="/approval/admin/apprCode/ajaxReadApprCodeView.do?codeId="/>'+codeId)
       		.error(function(event, request, settings) { alert("error"); });
			
			//코드 하위목록화면
			$("#codeChildList").load('<c:url value="/approval/admin/apprCode/ajaxListCodeChild.do?parentCodeId="/>'+codeId)
       		.error(function(event, request, settings) { alert("error"); });
        };
		
		/**
		 * 하위코드 등록
		 * @param {Object} selectData : Node Info
		 */
		f_ApCodeCreateForm = function(selectData){
			
			var codeId 		= $(selectData).attr("code");
			var codeName 	= selectData.children("a").text();
						
			if (codeId == undefined) {
				return false;
			}
			
			//코드 상세화면 코드명 설정.
			$("#selectApCodeName").text("("+codeName + " : 하위코드 등록)");
			
			//코드 상세화면
			$("#codeDetail").load('<c:url value="/approval/admin/apprCode/ajaxReadApprCodeView.do?parentCodeId="/>'+codeId)
       		.error(function(event, request, settings) { alert("error"); });
			
			//코드 하위목록화면
			$("#codeChildList").load('<c:url value="/approval/admin/apprCode/ajaxListCodeChild.do"/>')
       		.error(function(event, request, settings) { alert("error"); });
		};
		
		/**
		 * onload시 수행할 코드
		 */
		$(document).ready(function() { 
			$jq("#apprCodeLinkOfLeft").click();
			
			$treeDiv = $("#codeTree");
			
			//코드트리 초기화작업
			f_InitTree();
			
			$("#listButton a").click(function(){
                switch (this.id) {
                    case "searchApCode":
						$("#codeTree").jstree("search", $("input[name=searchApCodeText]").val());
						break;
                    case "test":
	                    try {
	                        //$.jstree._reference("codeTree").refresh();
							var selectedNode = $.jstree._focused().get_selected();
							alert("ID is " + selectedNode.children("a").text());
							
							$("#codeTree").jstree( "refresh", selectedNode);
						
	                        //$.jstree._focused().refresh();
	                        //$("#codeTree").jstree( "refresh");
	                        //alert('refresh');
	                    } 
	                    catch (e) {
	                        DebugE("testApCode", ex);
	                    }
                        break;
                    default:
                        break;
                }
            });
			
			$.ajaxSetup({ cache: false });
		});
		
		//=================================================================================================
		//	Temp
		//=================================================================================================
		function formatJsonDateTime(jsonDate) {
		    var date = eval(jsonDate.replace(/\/Date\((\d+)\)\//gi, "new Date($1)"));
		    return date.format("M/dd/yyyy HH:mm");
		};
		
		/**
		 * 트리를 클릭했을 경우 이벤트 발생.
		 */
		f_TreeNodeSelected = function(){
			
			var $clickItem;
			var itemData;
			
			try {
				$clickItem	= $treeDiv.find("a.jstree-clicked");
			
				if($clickItem.length <= 0) {
					//alert("항목을 선택해주세요.");
					return false;
				}
				
				itemData = $clickItem.parent();
				//alert($(itemData).attr("data"));
				itemData = $.parseJSON($(itemData).attr("data"));
				//alert(itemData.code);
				
				//코드 상세정보 가져오기
				f_ApCodeDetail(itemData.code);
				
			} catch (ex) {
				DebugE("f_TreeNodeSelected", ex);
			}
		};
		//=================================================================================================
		
	})(jQuery);  
	
//-->
</script>
			
<h1 class="none"><ikep4j:message pre="${preHeader}" key="title" /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
	<%-- <div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.3depth" /></li>
			<li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.4depth" /></li>
		</ul>
	</div> --%>
</div>
<!--//pageTitle End-->

<!--blockLeft Start-->
<div class="blockLeft" style="width:30%;" summary="<ikep4j:message pre="${preList}" key="summary1" />">	
	<!--subTitle_2 Start-->
	<div class="subTitle_2 noline">
		<h3><ikep4j:message pre="${preList}" key="summary1" /></h3>
		<table summary="코드검색" style="display:none;">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="5%"><ikep4j:message pre="${preList}" key="summary1" /></th>
					<td width="25%">
						<input name="searchApCodeText" value="" type="text" class="inputbox" title='test' />
					</td>
					<td>
						<div id="listButton"> 
							<a id="searchApCode"  class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='search'/></span></a>
							<a id="test"	class="button" href="#a"><span>test</span></a>
						</div>
					</td>
				</tr>								
			</tbody>
		</table>
	</div>
	<!--//subTitle_2 End-->						
	<div class="leftTree treeBox" style="height:550px;">	
		<div><img src="<c:url value="/base/images/common/img_title_cate.gif"/>" alt="category" /></div>
		<div id="codeTree"></div>
	</div>
</div>
<!--//blockLeft End-->

<!--blockRight Start-->
<div class="blockRight" style="width:68%;">

	<!--subTitle_2 Start-->
	<div class="subTitle_2">
		<h3><ikep4j:message pre="${preList}" key="summary2" /> <span id="selectApCodeName"></span></h3>
	</div>
	<!--//subTitle_2 End-->
										
	<!--blockDetail Start[Code Detail]-->
	<div id="codeDetail" style="height:250px;"></div>
	<!--//blockDetail End[Code Detail]-->
	
	<!--subTitle_2 Start-->
	<div class="subTitle_2">
		<h3><ikep4j:message pre="${preList}" key="summary3" /></h3>
	</div>
	<!--//subTitle_2 End-->
	
	<!--blockDetail Start[Code Child-List]-->
	<div id="codeChildList"></div>
	<!--//blockDetail End[Code Child-List]-->
	
</div>
<!--//blockLight End-->
