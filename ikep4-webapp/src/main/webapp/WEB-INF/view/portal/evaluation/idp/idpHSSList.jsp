<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="prefix" value="message.portal.main.listUser"/>
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/progress/progress.js"></script>

<script type="text/javascript">
var $groupTree;
var $searchWordId;
var isExtendTree = false;

var currentTreeClickUserEmpNo="${params.empno}";
var currentTreeClickUserId="${params.userId}";

var boardList = [];
(function($){

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
					 		$.getAndDrawHSSList(userInfo.userId);//보던 페이지에 사람을 바꿔서 호출.
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

	$(document).ready(function() {

		$.setParam = function(data) {
			var $ajaxForm = $('#ajaxForm');

			$.each(data, function(key, value) {
				$ajaxForm.append("<input type=\"hidden\" name=\""+key+"\" value=\""+value+"\"/>");
			});
			$ajaxForm.append("<input type=\"hidden\" name=\"empno\" value=\""+currentTreeClickUserEmpNo+"\"/>");
			$ajaxForm.append("<input type=\"hidden\" name=\"userId\" value=\""+currentTreeClickUserId+"\"/>");
		};

		$.makeFormData = function(data) {
			var formData = $("#ajaxForm").serializeArray();
			$.each(data, function(key, value) { formData.push({name : key, value : value}); });

			formData.push({name : "action", value : "IDP_HSS_VIEW"});

			return formData;
		}

		$.bindHssRowClickHandler = function() {
			$(".list_table > tbody> tr").click(function() {
				if($(this).find('.emptyRecord').size() > 0) return false;

				$.callProgress();

				var index = $(".list_table > tbody > tr").index(this);

				$.ajax({
					url : "<c:url value='/portal/evaluation/idp/idpCheckView.do'/>"
					, type : "post"
					, data : $.makeFormData(boardList[index])
					, success : function(result) {
						if(result.MSGTY == 'W' || result.MSGTY == 'E') {
							alert(result.MSGTX);
							$.stopProgress();
							return false;
						} else {
							$.setParam(boardList[index]);

							$("#ajaxForm").attr("method", "post");
							$("#ajaxForm").attr("action", "<c:url value='/portal/evaluation/idp/idpHSSView.do'/>");
							$("#ajaxForm").submit();
						}
					}
					, error : function(xhr, exMessage) {
						AjaxError.showAlert(xhr, exMessage);
						$.stopProgress();
					}
				});
			})
			.mouseover(function() {
				if($(this).find('.emptyRecord').size() > 0) return false;
				$(this).addClass('line_focus');
			});
		}

		$.drowTable = function(objList) {
			$('#tmpHSSRow').tmpl(objList).appendTo('.list_table > tbody');

			$.bindHssRowClickHandler();
		}

		$.removeTable = function() {
			$('#selectedUserInfo').text('');
			$('.list_table > tbody > tr').remove();
		}

		$.getAndDrawHSSList = function(userId) {
			$.callProgress();

			$.removeTable();

			$.ajax({
				url : "<c:url value='/portal/evaluation/idp/idpHSSList.do'/>"
				, type : "post"
				, data : {"emp_no" : currentTreeClickUserEmpNo}
				, success : function(result) {
					if(result.ES_RETURN.MSGTY == 'W') {
						alert(result.ES_RETURN.MSGTX);
						$.stopProgress();
						return false;
					} else {
						currentTreeClickUserId = userId;

						$('#selectedUserInfo').text(result.EMP_NO + " " + result.USER_NAME);

						boardList = result.ET_LIST;
						$.drowTable(boardList);
						$.stopProgress();
					}
				}
				, error : function(xhr, exMessage) {
					AjaxError.showAlert(xhr, exMessage);
					$.stopProgress();
				}
			});
		}

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
							<c:if test="${user.jobDutyCode eq '22'}">
							,"controlType" : "ORG22"
							</c:if>
							<c:if test="${user.jobDutyCode != '22'}">
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
		 		//alert(currentTreeClickUserEmpNo);
		 		$.getAndDrawHSSList(userId);//보던 페이지에 사람을 바꿔서 호출.
		 	}
	     });

   	 	if(currentTreeClickUserId != '') $.getAndDrawHSSList(currentTreeClickUserId);
   		setTimeout("selectedUserTree()", 500);//최종적으로 로그인사용자 소속 부서로 트리를 연다.

	});

	/* window risize 시 Tree 윈도우높이로 조절 */
	resizeTreeContentHeight = function(){
		var $treeOutDiv = $(".leftTree_2_mss"); // Tree out div
		var paddingHeight = 10;
		$treeOutDiv.height( $(window).height() - $treeOutDiv.position().top - paddingHeight);
	}

	/* tree 영역 넓히기 */
	extendTreeContent = function() {
		var $btnDivSize = $("#btnDivSize"),
			$sizeAction = $("#sizeAction"),
			$leftMenu = $("#leftMenu"),
			$leftTree_2_mss = $(".leftTree_2_mss"),
			$divTab_s = $("#divTab_s"),
			$mainContents = $("#mainContents");

		if(isExtendTree){ // tree 영역이 넓혀져 있는 경우
			$btnDivSize
				.removeClass("btn_tr_reduce_mss")
				.addClass("btn_tr_enlarge_mss");
			$sizeAction.html("<span>Enlarge</span>");
			$leftMenu.width(195);
			$leftTree_2_mss.width(175);

			$divTab_s.width(175);
			$mainContents.css("margin-left",215);
			isExtendTree = false;
		}else{  // tree 영역이 기본으로 있는 경우
			$btnDivSize
				.removeClass("btn_tr_enlarge_mss")
				.addClass("btn_tr_reduce_mss");
			$sizeAction.html("<span>Reduce</span>");

			var tWidth = $leftTree_2_mss[0].scrollWidth;
			if(tWidth-20 > 175){

				$divTab_s.width(tWidth);
				$leftMenu.width(tWidth+20);
				$leftTree_2_mss.width(tWidth);
				$mainContents.css("margin-left",tWidth+40);
			}else {	// 기본 확장 사이즈 적용
				$leftMenu.width(230);

				$divTab_s.width(210);
				$leftTree_2_mss.width(210);
				$mainContents.css("margin-left",250);
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

	//$jq("#userTree").ajaxLoadStart();
	$groupTree.jstree("search", userId);	//'user33157', 'user27562'
}

function treeSearchReset() {
	//$groupTree.jstree("clear_search");
	//$jq("div.leftTree_2").scrollTop(0);
	$jq("a.jstree-search", $groupTree).removeClass("jstree-search");
}

function createGroupTree(searchUserId) {
	$jq("#searchUserLeftDiv").hide();
	$jq("#userListTbl").hide();
	$jq("#userSearchPage").hide();
	treeSearch(searchUserId);
};

function selectedUserTree(){
	createGroupTree(currentTreeClickUserId);
}

</script>

<script id="tmplUserItemEmpty" type="text/x-jquery-tmpl">
	<tr><td colspan="2" class="emptyRecord">검색결과가 없습니다.</td></tr>
</script>

<script id="tmplUserItem" type="text/x-jquery-tmpl">
	<tr>
		<td class="textLeft"><a href="#a">\${userName} \${jobTitleName}</a></td>
		<td class="textLeft">\${teamName}</td>
	</tr>
</script>

<script id="tmpHSSRow" type="text/x-jquery-tmpl">
	<tr>
		<td>\${AYEAR}</td>
		<td>\${STLTX}</td>
		<td>\${COTXT}</td>
		<td>\${TRFGR}</td>
		<td>\${IDPARNM}</td>
		<td>\${IDSTSX}</td>
	</tr>
</script>

<div id="wrap">

	<h1 class="title">IDP 조회</h1>

	<div class="float_wrap">
		<div class="float_left tviewer" style="width: 15%;">
			<div class="btn_tr_enlarge_mss" id="btnDivSize"><a href="#a" id="sizeAction"><span>Enlarge</span></a></div>

			<div id="divTab_s" class="iKEP_tab_s" >
				<ul>
					<li><a href="#tabs-org">팀뷰어</a></li>
					<c:if test="${user.jobDutyCode != '22'}">
					<li><a href="#tabs-search">검색</a></li>
					</c:if>
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
		</div>

		<div class="float_right ttable" style="width: 85%;">
			<h2 id="selectedUserInfo" class="title"></h2>
			<!-- 리스트 -->
			<table class="list_table">
				<colgroup>
				    <col style="width:15%;" />
				    <col style="width:15%;" />
				    <col style="width:20%;" />
				    <col style="width:15%;" />
				    <col style="width:15%;" />
				    <col style="width:15%;" />
				</colgroup>
				<thead>
					<tr>
						<th>년도</th>
						<th>직무</th>
						<th>직책</th>
						<th>직급</th>
						<th>합의자</th>
						<th>작성상태</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="6" class="emptyRecord center">내역이 없습니다.</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>

<form id="ajaxForm" name="ajaxForm" method="post"></form>

<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>
