<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<c:set var="prefix" value="message.portal.main.listUser"/>

<c:set var="mssuser" value="${sessionScope['ikep.user']}" />





<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript">
//<![CDATA[
var $groupTree;
var $searchWordId;
var isExtendTree = false;

var currentTreeClickUserEmpNo="${mssuser.empNo}";
var currentMssMenu="";
var currentTeamX="";

var webMssUrl;
var webMssUrlFlag = false;


function setLicurrent(el) {
	var $el = el;
	if(typeof el === "string") {
		$el = $jq(el);
	}
	clearCurrent();
	$el.addClass("licurrent");
	$el.parents("li.opened", "#leftMenu-pane").addClass("licurrent");
}

function clearCurrent() {
	$jq("#leftMenu-pane li").removeClass("licurrent");
}


function profileResize() {
	iKEP.iFrameResize('#contentIframe');
}

function treeSearch(userId) {
	treeSearchReset();
	
	$groupTree.jstree("search", userId);	
}

function treeSearchReset() {
	$jq("a.jstree-search", $groupTree).removeClass("jstree-search");
}

function createGroupTree(searchUserId) {
	$jq("#searchUserLeftDiv").hide();
	$jq("#userListTbl").hide();
	$jq("#userSearchPage").hide();
	treeSearch(searchUserId);
};

function loginUserTree(){
	createGroupTree("${mssuser.userId}");
}


(function($) {

	 searchUserPageMove = function(plusMinus)  {
		$("#pageIndex").val(eval($("#currnetUserPage").text())+plusMinus);
	 };
	
	 seachFormView = function(){
		$("#searchUserLeftDiv").show();
		if($("#searchWord").val()!=""){
			getList();
		}
	 };
	 
	 getList = function()  {
		 
		if($("#searchWord").val()==""){
			alert("검색어를 입력하세요.");
		}else{
		$.post("<c:url value="/portal/moorimmss/userList.do" />",  $("#searchForm").serialize())
			.success(function(result) {
				$("#userListTbl").show();
				$("#userSearchPage").show();
				
				var $container = $("#userListTbl").children("tbody").empty();
								
				if(!result.entity||result.entity.length==0){
					$container.append($.tmpl($("#tmplUserItemEmpty").template()));
				}else{
					
					var template = $("#tmplUserItem").template();
					
					$.each(result.entity, function() {
						var userInfo = this;
						var $tr = $.tmpl(template, userInfo).appendTo($container);
						$tr.data({
							userName:userInfo.userName,
							jobTitleName:userInfo.jobTitleName,
							teamName:userInfo.teamName
						});
						$("a", $tr).click(function() {
					 		
					 		currentTreeClickUserEmpNo =  userInfo.empNo;//사번을 할당한다.
				 			mssWebLink(webMssUrl, true);
						});
					});
					var searchCondition = result.searchCondition;
					$("#currnetUserPage").text(result.pageIndex);
					$("#userSearchTotalPage").text(result.pageCount);
				}
				


			})
			.error(function(){
				alert("error");
			});
		}

	};
	
	$jq(document).ready(function() {
		
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/loanList.do'/>?selEmpNo="+currentTreeClickUserEmpNo);
		
		
    	tab = $("#divTab_s").tabs({
			select : function(event, ui) {
				switch($(ui.panel).attr("id")) {
					case "tabs-org" : createGroupTree();
						break;
					case "tabs-search" : seachFormView();
						break;
					
				}
			}
		});  
    	
    	$jq('#searchWord').keypress(function(event) { 
			if(event.which == '13') {
				getList();
				return false;
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
							<c:if test="${mssuser.jobDutyCode eq '22'}"> 
							,"controlType" : "ORG22"
							</c:if>
							<c:if test="${mssuser.jobDutyCode != '22'}"> 
							,"controlType" : "ORG"
							</c:if>
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

				var $container = $("div.leftTree_2_mss");
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
    	
   	 	$jq("#userTree").delegate("a", "click", function () {
	    	
		 	var type =  $.parseJSON( $jq(this).parent().attr("data")).type;
		 	
		 	if(type == 'group') {
		 		var groupId = $.parseJSON( $jq(this).parent().attr("data")).code;
		 		
		 	} else {
		 		var userId = $.parseJSON( $jq(this).parent().attr("data")).id;
		 		currentTreeClickUserEmpNo =  $.parseJSON( $jq(this).parent().attr("data")).empNo;//사번을 할당한다.

	 			mssWebLink(webMssUrl, true);
		 	}
	     });
   	 	
	});
	
	/* window risize 시 Tree 윈도우높이로 조절 */
	resizeTreeContentHeight = function(){	
		var $treeOutDiv = $(".leftTree_2_mss"); // Tree out div
		var paddingHeight = 10;
		$treeOutDiv.height( $(window).height() - $treeOutDiv.position().top - paddingHeight);
	}
	
	/* tree 영역 넓히기 */
	extendTreeContent = function(){
		if(isExtendTree){ // tree 영역이 넓혀져 있는 경우
			$("#btnDivSize").removeClass("btn_tr_reduce_mss");
			$("#btnDivSize").addClass("btn_tr_enlarge_mss");
			$("#sizeAction").html("<span>Enlarge</span>");
			$("#leftMenu").width(195);
			$(".leftTree_2_mss").width(175);
			
			$("#divTab_s").width(175);
			$("#mainContents").css("margin-left",215);
			isExtendTree = false;
		}else{  // tree 영역이 기본으로 있는 경우
			$("#btnDivSize").removeClass("btn_tr_enlarge_mss");
			$("#btnDivSize").addClass("btn_tr_reduce_mss");
			$("#sizeAction").html("<span>Reduce</span>");
			
			var tWidth = $(".leftTree_2_mss")[0].scrollWidth;
			if(tWidth-20 > 175){
				
				$("#divTab_s").width(tWidth);
				$("#leftMenu").width(tWidth+20);
				$(".leftTree_2_mss").width(tWidth);
				$("#mainContents").css("margin-left",tWidth+40);
			}else {	// 기본 확장 사이즈 적용
				$("#leftMenu").width(230);
				
				$("#divTab_s").width(210);
				$(".leftTree_2_mss").width(210);
				$("#mainContents").css("margin-left",250);
			}
		    isExtendTree = true;
		}
	}
	
	hideTeamView = function(param){
		if(param){
			$("#divTab_s").hide();
			$("#btnDivSize").hide();
		}else{
			$("#divTab_s").show();
			$("#btnDivSize").show();
		}
	}


})(jQuery);

//]]>

function mssWebLink(menu, setEmpFlag){
	iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/loanList.do'/>?selEmpNo="+currentTreeClickUserEmpNo);
}	
function goLoanList(){
	iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/loanList.do'/>?selEmpNo="+currentTreeClickUserEmpNo);
}


</script>

<!--leftMenu Start-->
<h2 class="han">
	<a href="javascript:goLoanList();">
		<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
		<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">대출내역 조회</font>
	</a>
</h2>
<div class="left_fixed" id="leftMenu-pane">
	<ul>
	    
		<li class="liFirst opened licurrent"  ><a href="#">대출내역 조회</a>
			<ul>
				<li class="no_child" id="zhr_py_001"><a  href="javascript:goLoanList();">대출내역 조회</a></li>
			</ul>
		</li>
	</ul>
</div>


<div height="20px">&nbsp;</div>

<div class="btn_tr_enlarge_mss" id="btnDivSize"><a href="#a" id="sizeAction"><span>Enlarge</span></a></div>


<div id="divTab_s" class="iKEP_tab_s" >
	<ul>
		<li><a href="#tabs-org">팀뷰어</a></li>	
		<li><a href="#tabs-search">검색</a></li>
	</ul>	

 	<div class="leftTree_2_mss">
		<div id="tabs-org"><div id="userTree"></div></div>
		<div id="tabs-search">
			<form id="searchForm">
				<input name="groupId" type="hidden" value=""/>
				<input name="pageIndex" id="pageIndex" type="hidden" value="1" />
				<div id="searchUserLeftDiv" style="display:none;padding-bottom:10px;padding-right:10px" class="tableSearch" > 
					<select name="searchColumn">
						<option value="title"  ><ikep4j:message pre='${prefix}' key='userName' post=''/></option>
						<option value="id"  >아이디</option>
						<option value="team"  ><ikep4j:message pre='${prefix}' key='teamName' post=''/></option>
					</select>	
					<input name="searchWord" id="searchWord" type="text" class="inputbox" style="width:60px" size="20" />
					<img src="<c:url value='/base/images/theme/theme01/basic/ic_search.gif'/>" style="vertical-align:middle" onclick='javascript:getList($jq("#searchForm").serialize());' > 
				</div>	
			</form>
			<div style="height:5px"></div>
			<div  class="blockListTable" style="min-width:175px;">
				<table id="userListTbl" style="display:none;">
					<caption></caption>
					<thead>
						<tr>
							<th scope="col" width="70px">이름</th>
							<th scope="col" width="70px">부서</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
				<div id="userSearchPage" class='pageNum' style="display:none">
			  		<ul> 
			  			<li><a class='btn_page_pre' href="#a" onclick='searchUserPageMove(-1); getList($jq("#searchForm").serialize());  return false;'><span></span></a></li> 
				  		<li><span  style="background-image: none;" id="currnetUserPage"></span> / <span  style="background-image: none;"  id="userSearchTotalPage"></span></li>  
				  		<li><a class='btn_page_next' href="#a" onclick='searchUserPageMove(1); getList($jq("#searchForm").serialize());  return false;'><span></span></a></li>
			  		</ul>
				</div>
			</div>
		</div>
	</div>		
</div>		
	


	
<script id="tmplUserItemEmpty" type="text/x-jquery-tmpl">
	<tr><td colspan="2" class="emptyRecord">검색결과가 없습니다.</td></tr>
</script>
		
<script id="tmplUserItem" type="text/x-jquery-tmpl">
	<tr>
		<td class="textLeft"><a href="#a">\${userName} \${jobTitleName}</a></td>
		<td class="textLeft">\${teamName}</td>
	</tr>
</script>

