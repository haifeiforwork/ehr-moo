<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="prefix" value="message.portal.main.listUserMain"/>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript">
<!--
var $groupTree;
var $jobTitleTree;
var $jobDutyTree;
var $searchWordId;
var isExtendTree = false;

(function($) {
	
	var createJobTitleTree = function createJobTitleTree() {		
		if(!$jobTitleTree) {
			$("#jobTitleTree").bind("loaded.jstree", function (event, data) {
				var $TopItem = $("ul > li:first", this);
				$("#jobTitleTree").jstree("open_node", $TopItem.children("a")[0], false);
	        });
		
			$jobTitleTree = $("#jobTitleTree").jstree({	// 조직도 생성
				plugins : [ "themes", "json_data", "ui" ],
				json_data : {
					data : iKEP.arrayToTreeData(${jobTitleItems}, null, true),
					ajax : {
						url : "<c:url value='/support/popup/requestGroupChildren.do'/>",
						data : function ($el) {	//$el : current item 
							return { 
								 "operation" : "get_children" 
								,"groupId" : $el == -1 ? '': $el.attr("code")
								,"controlType" : 'JBT'
								,"controlTabType" : 'USER'
								,"selectType" : 'USER'
								,"userId" :  ""
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
				$("#jobTitleTree").jstree("open_node", this, false, true); 
	        }).delegate("a", "click", function () {
	        	var type =  $.parseJSON( $jq(this).parent().attr("data")).type;	            
	            if(type == 'group') {
	                var groupId = $.parseJSON( $jq(this).parent().attr("data")).code; 
	                $jq("#contentIframe").attr("src", "<c:url value='/portal/main/listUser.do'/>?jobTitleCode=" + groupId);
	            } else {
	                var userId = $.parseJSON( $jq(this).parent().attr("data")).id;
	                $jq("#contentIframe").attr("src", "<c:url value='/support/profile/getProfile.do'/>?targetUserId=" + userId);
	            }  
		     });
		}
	};
	
	var createJobDutyTree = function createJobDutyTree() {		
		if(!$jobDutyTree) {
			$("#jobDutyTree").bind("loaded.jstree", function (event, data) {
				var $TopItem = $("ul > li:first", this);
				$("#jobDutyTree").jstree("open_node", $TopItem.children("a")[0], false);
	        });
		
			$jobDutyTree = $("#jobDutyTree").jstree({	// 조직도 생성
				plugins : [ "themes", "json_data", "ui" ],
				json_data : {
					data : iKEP.arrayToTreeData(${jobDutyItems}, null, true),
					ajax : {
						url : "<c:url value='/support/popup/requestGroupChildren.do'/>",
						data : function ($el) {	//$el : current item 
							return { 
								 "operation" : "get_children" 
								,"groupId" : $el == -1 ? '': $el.attr("code")
								,"controlType" : 'JBD'
								,"controlTabType" : 'USER'
								,"selectType" : 'USER'
								,"userId" :  ""
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
				$("#jobDutyTree").jstree("open_node", this, false, true); 
	        }).delegate("a", "click", function () {
	        	var type =  $.parseJSON( $jq(this).parent().attr("data")).type;
	        	if(type == 'group') {
                    var groupId = $.parseJSON( $jq(this).parent().attr("data")).code;
                    $jq("#contentIframe").attr("src", "<c:url value='/portal/main/listUser.do'/>?jobDutyCode=" + groupId);
                } else{
   		 		var userId = $.parseJSON( $jq(this).parent().attr("data")).id;
   		 		$jq("#contentIframe").attr("src", "<c:url value='/support/profile/getProfile.do'/>?targetUserId=" + userId);
               }
	   		 	
		     });
		}
	};
	
	
	
    $jq(document).ready(function() {
    	//iKEP.iFrameMenuOnclick('<c:url value="/portal/main/listUser.do"/>');
    	
    	tab = $("#divTab_s").tabs({
			select : function(event, ui) {
				switch($(ui.panel).attr("id")) {
					case "tabs-orggroup" : createGroupTree();
						break;
					case "tabs-jobtitlegroup" : createJobTitleTree();
						break;
					case "tabs-jobdutygroup" : createJobDutyTree();
						break;
				}
			}
		});    	
    	
    	/* 페이지 로딩시 Tree 영역 윈도우 높이로 조절 */
		resizeTreeContentHeight();
		
		/* 윈도우 resize 이벤트 발생 시 tree 영역 document 높이로 조절 */
		$(window).bind("resize", resizeTreeContentHeight);	
		
		/* tree 영역 넓히기 */
		$("#btnDivSize").click(
			function(){
				extendTreeContent();
			}
		);	
    	
    	$("#userTree").bind("loaded.jstree", function (event, data) {
    		var $TopItem = $("ul > li:first", this);
			$(this).jstree("open_node", $TopItem.children("a")[0], false);
// 				var $selectItem = $(initTreeItem);
// 				while(true) {
// 					$selectItem = $selectItem.parents("li:first", this);
// 					if($selectItem.length > 0) {
// 						$("#userTree").jstree("open_node", $selectItem[0], false);
// 					} else {break;}
// 				}
        });
	
		$groupTree = $("#userTree").jstree({	// 조직도 생성
			plugins : [ "themes", "json_data", "ui", "search"],
			json_data : {
				data : iKEP.arrayToTreeData(${deptItems}, null, true),
				ajax : {
					url : "<c:url value='/support/popup/requestGroupChildren.do'/>",
					data : function ($el) {	//$el : current item 
						return { 
							 "operation" : "get_children",  
							 "groupId" : $el == -1 ? '': $el.attr("code")
							,"controlType" : "ORG"
							,"controlTabType" : "USER"
							,"selectType" : "USER"
							,"userId" :  ""
						}; 
					},
					success : function(data) {
						
						return iKEP.arrayToTreeData(data.items, null, true);
					}
				}
			},
			search : {
				case_insensitive : true,
				ajax : {
					//url : iKEP.getContextRoot() + "/test/testSearch.do",
					url : iKEP.getContextRoot() + "/portal/main/getDeptPath.do",
					success : function(result, state, xhr) {
						var items = [];
						
						if(result.length > 0) {
							$.each(result, function() {
								if(this.toString() != null && this.toString() != '' && this.toString() !='undefined') {
									items.push("#treeItem_" + this.toString());
								}
							});
						}
						
						return items;
					},
					complete : function(xhr, state) {
						if(state == "parsererror") {
							alert("<ikep4j:message pre='${prefix}' key='alert.noSearchUser'/>");
						}
					}
				}
			},
			core : {animation : 100}
		}).bind("search.jstree", function(event, data) {
			var $searchItem = $("#treeItem_" + data.rslt.str);
			if($searchItem.length > 0) {
				var $anchor = $searchItem.children("a");
				//$anchor.addClass("jstree-search");
				
				// 검색 대상 외의 노드 active 스타일 삭제 : 해당 검색어의 like 노드가 모두 active 되므로
				$.grep($("a.jstree-search", this), function(anchor) {
					if($anchor[0] != anchor) $(anchor).removeClass("jstree-search");
				});

				var $container = $("div.leftTree_2");
				var itemPosition = $anchor.offset();
				var offsetPosition = $container.position();
				
				$container.scrollTop(itemPosition.top - offsetPosition.top + $container.scrollTop());
			}
		}).delegate("a[href$='#']", "click", function (e) { // clicking node text expands and contracts it
			e.preventDefault();
			this.blur(); 
			$("#userTree").jstree("toggle_node", this, false, true); 
			//$("#producttree").jstree("toggle_node", this); 
        });
    	
    	
    	
    	<c:choose>
			<c:when test="${empty rightFrameUrl}">
				$jq("#userlistForm")[0].submit();
			</c:when>
			<c:otherwise>
				$jq("#contentIframe").attr("src", "<c:url value='${rightFrameUrl}'/>");
			</c:otherwise>
		</c:choose>
    	
    	 $jq("#userTree").delegate("a", "click", function () {
		    	
   		 	var type =  $.parseJSON( $jq(this).parent().attr("data")).type;
   		 	
   		 	if(type == 'group') {
   		 		var groupId = $.parseJSON( $jq(this).parent().attr("data")).code;
   		 		$jq("#contentIframe").attr("src", "<c:url value='/portal/main/listUser.do'/>?groupId=" + groupId);
   		 	} else {
   		 		var userId = $.parseJSON( $jq(this).parent().attr("data")).id;
   		 		$jq("#contentIframe").attr("src", "<c:url value='/support/profile/getProfile.do'/>?targetUserId=" + userId);
   		 	}
	     });
    	 
    	 //$("#treeDept").bind("dblclick", dblClickOrg);
    });

	/* window risize 시 Tree 윈도우높이로 조절 */
	resizeTreeContentHeight = function(){	
		var $treeOutDiv = $(".leftTree_2"); // Tree out div
		var paddingHeight = 10;
		$treeOutDiv.height( $(window).height() - $treeOutDiv.position().top - paddingHeight);
	}
	
	/* tree 영역 넓히기 */
	extendTreeContent = function(){
		if(isExtendTree){ // tree 영역이 넓혀져 있는 경우
			$("#btnDivSize").removeClass("btn_tr_reduce");
			$("#btnDivSize").addClass("btn_tr_enlarge");
			$("#sizeAction").html("<span>Enlarge</span>");
			$(".leftTree_2").width(185);
			$("#mainContents").css("margin-left",205);
			isExtendTree = false;
		}else{  // tree 영역이 기본으로 있는 경우
			$("#btnDivSize").removeClass("btn_tr_enlarge");
			$("#btnDivSize").addClass("btn_tr_reduce");
			$("#sizeAction").html("<span>Reduce</span>");
			
			var tWidth = $(".leftTree_2")[0].scrollWidth;
			if(tWidth-20 > 185){
				$(".leftTree_2").width(tWidth);
				$("#mainContents").css("margin-left",tWidth+20);
			}else {	// 기본 확장 사이즈 적용
				$(".leftTree_2").width(220);
				$("#mainContents").css("margin-left",240);
			}
		    isExtendTree = true;
		}
	}
    
})(jQuery);


function profileResize() {
	iKEP.iFrameResize('#contentIframe');
}

function treeSearch(userId) {
	treeSearchReset();
	
	//$jq("#userTree").ajaxLoadStart();
	$groupTree.jstree("search", userId);	//'user33157', 'user27562'
}

function treeSearchReset() {
	//$groupTree.jstree("clear_search");
	//$jq("div.leftTree_2").scrollTop(0);
	$jq("a.jstree-search", $groupTree).removeClass("jstree-search");
}

function createGroupTree(searchUserId) {
	treeSearch(searchUserId);
};


//-->
</script>

<form id="userlistForm" action="<c:url value='/portal/main/listUser.do'/>" method="post" target="contentIframe" onsubmit="return false;">
	<input type="hidden" name="searchWord" value="${headerSearchWord}"/>
	<input type="hidden" name="searchColumn" value="${headerSearchColumn}"/>
</form>
<h1 class="none"><ikep4j:message pre='${prefix}' key='tree'/></h1>
<h2 class="treeTitle">Organization</h2>
<div class="btn_tr_enlarge" id="btnDivSize"><a href="#a" id="sizeAction"><span>Enlarge</span></a></div>	
<div class="leftTree_2">
      <div id="divTab_s" class="iKEP_tab_s">
		<ul>
			<li><a href="#tabs-orggroup">부서</a></li>			
			<li><a href="#tabs-jobdutygroup">직책별</a></li>
			<li><a href="#tabs-jobtitlegroup">직급별</a></li>
		</ul>		
		<div>
			<div id="tabs-orggroup"><div id="userTree"></div></div>
			<div id="tabs-jobtitlegroup"><div id="jobTitleTree"></div></div>
			<div id="tabs-jobdutygroup"><div id="jobDutyTree"></div></div>
		</div>					
	</div>
</div>			