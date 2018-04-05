<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<c:set var="hassuser" value="${sessionScope['ikep.user']}" />
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>

<script type="text/javascript">
//<![CDATA[

	var $groupTree;
	var $searchWordId;
	var isExtendTree = false;
	var currentTreeClickUserEmpNo="${hassuser.empNo}";
	
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

	function loginUserTree(){
		createGroupTree("${hassuser.userId}");
	}
	
	function mssTeamLinkUserChange(empno){
		currentTreeClickUserEmpNo = empno;
		hassLink("${serverLinkUrl}"+"zhr_hass_es_003/default.htm");
	}
	

	function hassLink(hassLink){
		document.domain ="moorim.co.kr";//밑에서 프레임 리사이징을 할때 iframe의 높이를 jquery의 contents()함수를 이용해 가져온다. 이때 같은 도메인이어야 exception나지 않는다. 
		var regurl=/eptest.moorim.co.kr/g;
    	var cssStr="sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Feptest.moorim.co.kr%2Fikep4-webapp%2Fbase%2Fcss%2Fsap%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok	
		if (regurl.test(location.href)) { 
			cssStr="sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Feptest.moorim.co.kr%2Fikep4-webapp%2Fbase%2Fcss%2Fsap%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok	
		}else{
			cssStr="sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Fep.moorim.co.kr%2Fbase%2Fcss%2Fsap%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok	
		}
    	
		hassLink = hassLink+"?"+cssStr+"&pa_pernr="+currentTreeClickUserEmpNo;
		
		//iKEP.popupOpen(hassLink, {width:1000, height:600 }, "hass");
		iKEP.iFrameMenuOnclick(hassLink);

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
			 
				//alert($("#searchWord").val());
				if($("#searchWord").val()==""){
					alert("검색어를 입력하세요.");
				}else{
				$.post("<c:url value="/portal/moorimmss/userList.do" />",  $("#searchForm").serialize())
					.success(function(result) {
						/*
						$.each(result, function(index) {
						 alert(index);
						});
						*/
						

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
							 		//alert(currentTreeClickUserEmpNo);
							 		mssTeamLinkUserChange(currentTreeClickUserEmpNo);//보던 페이지에 사람을 바꿔서 호출.
								});
							});
							var searchCondition = result.searchCondition;
							$("#currnetUserPage").text(result.pageIndex);
							$("#userSearchTotalPage").text(result.pageCount);
							//alert(result.pageCount+":"+result.pageIndex);
							/*
							$.each(result.entity, function() {
								alert(this.userName+" "+this.teamName+" "+this.userId+" "+this.jobTitleName+" "+this.empNo+" "+this.leader);
							});
							*/
						}
						


					})
					.error(function(){
						alert("error");
					});
				}

			};
		
		$jq(document).ready(function() {
			


			//iKEP.setLeftMenu();
			
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
//	 				var $selectItem = $(initTreeItem);
//	 				while(true) {
//	 					$selectItem = $selectItem.parents("li:first", this);
//	 					if($selectItem.length > 0) {
//	 						$("#userTree").jstree("open_node", $selectItem[0], false);
//	 					} else {break;}
//	 				}
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
								<c:if test="${hassuser.jobDutyCode eq '22'}"> 
								,"controlType" : "ORG22"
								</c:if>
								<c:if test="${hassuser.jobDutyCode != '22'}"> 
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
			 		mssTeamLinkUserChange(currentTreeClickUserEmpNo);//보던 페이지에 사람을 바꿔서 호출.
			 	}
		     });

			document.domain ="moorim.co.kr";
			//hassLink("${serverLinkUrl}"+"zhr_hass_es_003/default.htm");
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimhass/initHassMain.do'/>?whereLink=zhr_hass_es_003");

			setTimeout("loginUserTree()", 500);//최종적으로 로그인사용자 소속 부서로 트리를 연다.
			
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
</script>

<!--leftMenu Start-->
<h2 class="han">
	<a href="<c:url value='/portal/moorimhass/hassLink.do'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_hass.gif'/>" title="HASS"/></a>

</h2>
<div class="left_fixed" id="leftMenu-pane">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">HR결재</a>
			<ul>
				<li class="no_child licurrent" id="zhr_hass_es_003"><a  href="javascript:hassLink('${serverLinkUrl}zhr_hass_es_003/default.htm');">HR결재</a></li>
			</ul>
		</li>
		<li class=""><a href="#">역량평가</a>
			<ul>
				<li class="no_child" id="zhr_hass_hp_012"><a  href="javascript:hassLink('${serverLinkUrl}zhr_hass_hp_012/default.htm');">역량평가 목표합의 진행</a></li>
				<li class="no_child" id="zhr_hass_hp_015"><a  href="javascript:hassLink('${serverLinkUrl}zhr_hass_hp_015/default.htm');">역량평가 중간면담 진행</a></li>
				<li class="no_child" id="zhr_hass_hp_013"><a  href="javascript:hassLink('${serverLinkUrl}zhr_hass_hp_013/default.htm');">역량평가 평가자 평가 진행</a></li>
				<li class="no_child" id="zhr_hass_hp_014"><a  href="javascript:hassLink('${serverLinkUrl}zhr_hass_hp_014/default.htm');">역량평가결과 Review 진행</a></li>
			</ul>
		</li>
		<li class=""><a href="#">5급사원평가</a>
			<ul>
				<li class="no_child" id="zhr_hass_hp_003"><a  href="javascript:hassLink('${serverLinkUrl}zhr_hass_hp_003/default.htm');">5급사원 평가자 평가</a></li>
			</ul>
		</li>
		<li class=""><a href="#">기능직평가</a>
			<ul>
				<li class="no_child" id="zhr_hass_hp_005"><a  href="javascript:hassLink('${serverLinkUrl}zhr_hass_hp_005/default.htm');">기능직평가 평가자 평가</a></li>	
				<li class="no_child" id="zhr_hass_hp_018"><a  href="javascript:hassLink('${serverLinkUrl}zhr_hass_hp_018/default.htm');">기능직평가 평가결과 Feedback</a></li>
			</ul>
		</li>
	</ul>
</div>

<div height="20px">&nbsp;</div>

<div class="btn_tr_enlarge_mss" id="btnDivSize"><a href="#a" id="sizeAction"><span>Enlarge</span></a></div>


<div id="divTab_s" class="iKEP_tab_s" >
	<ul>
		<li><a href="#tabs-org">팀뷰어</a></li>	
		<c:if test="${hassuser.jobDutyCode != '22'}">		
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
						<option value="title"  >이름</option>
						<option value="id"  >아이디</option>
						<option value="team"  >부서</option>
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