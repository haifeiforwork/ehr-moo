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

var regurl=/eptest.moorim.co.kr/g;
var cssStr="sap-syscmd=nocookie&sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Feptest.moorim.co.kr%2Fikep4-webapp%2Fbase%2Fcss%2Fsap%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok	
if (regurl.test(location.href)) { 
	cssStr="sap-syscmd=nocookie&sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Feptest.moorim.co.kr%2Fikep4-webapp%2Fbase%2Fcss%2Fsap%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok	
}else{
	cssStr="sap-syscmd=nocookie&sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Fep.moorim.co.kr%2Fbase%2Fcss%2Fsap%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok	
}

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

function mssLink(msslink){
	
	webMssUrl = "";
	webMssUrlFlag = false;
	
	currentMssMenu = msslink;
	document.domain ="moorim.co.kr";//밑에서 프레임 리사이징을 할때 iframe의 높이를 jquery의 contents()함수를 이용해 가져온다. 이때 같은 도메인이어야 exception나지 않는다. 
	//msslink = "${serverLinkUrl}"+msslink+"/default.htm?sap-syscmd=nocookie&sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportalapps%2Fcom.sap.portal.design.portaldesigndata%2Fthemes%2Fportal%2Fcustomer%2FDefault%2Fcontrols%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok		

	
	msslink = "${serverLinkUrl}"+msslink+"/default.htm?"+cssStr;

	setLicurrent("#"+currentMssMenu);
	iKEP.iFrameMenuOnclick(msslink);

}

function mssLinkPopup(msslink){
	
	webMssUrl = "";
	webMssUrlFlag = false;
	
	currentMssMenu = msslink;
	document.domain ="moorim.co.kr";//밑에서 프레임 리사이징을 할때 iframe의 높이를 jquery의 contents()함수를 이용해 가져온다. 이때 같은 도메인이어야 exception나지 않는다. 
	//msslink = "${serverLinkUrl}"+msslink+"/default.htm?sap-syscmd=nocookie&sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportalapps%2Fcom.sap.portal.design.portaldesigndata%2Fthemes%2Fportal%2Fcustomer%2FDefault%2Fcontrols%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok		

	
	msslink = "${serverLinkUrl}"+msslink+"/default.htm?"+cssStr;

	setLicurrent("#"+currentMssMenu);
	iKEP.popupOpen(msslink, {width:1000, height:600 }, "mss");

}

<c:if test="${Bigmenu eq 'personalMng'||Bigmenu eq 'diligencePayMng'||Bigmenu eq 'manPowerMng'||Bigmenu eq 'evaluationMng'}">


function mssTeamLink(msslink, teamX){
	
	webMssUrl = "";
	webMssUrlFlag = false;
	
	currentMssMenu = msslink;
	if(teamX=="X"){
		currentTeamX = teamX;
	}else{
		currentTeamX="";
	}
	document.domain ="moorim.co.kr";//밑에서 프레임 리사이징을 할때 iframe의 높이를 jquery의 contents()함수를 이용해 가져온다. 이때 같은 도메인이어야 exception나지 않는다. 
	//msslink = "${serverLinkUrl}"+msslink+"/default.htm?pa_pernr="+currentTreeClickUserEmpNo+"&sap-syscmd=nocookie&sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportalapps%2Fcom.sap.portal.design.portaldesigndata%2Fthemes%2Fportal%2Fcustomer%2FDefault%2Fcontrols%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok		
	msslink = "${serverLinkUrl}"+msslink+"/default.htm?pa_pernr="+currentTreeClickUserEmpNo+"&"+cssStr;
	setLicurrent("#"+currentMssMenu);
	iKEP.iFrameMenuOnclick(msslink);
}

function mssTeamLinkPopup(msslink, teamX){
	
	webMssUrl = "";
	webMssUrlFlag = false;
	
	currentMssMenu = msslink;
	if(teamX=="X"){
		currentTeamX = teamX;
	}else{
		currentTeamX="";
	}
	document.domain ="moorim.co.kr";//밑에서 프레임 리사이징을 할때 iframe의 높이를 jquery의 contents()함수를 이용해 가져온다. 이때 같은 도메인이어야 exception나지 않는다. 
	//msslink = "${serverLinkUrl}"+msslink+"/default.htm?pa_pernr="+currentTreeClickUserEmpNo+"&sap-syscmd=nocookie&sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportalapps%2Fcom.sap.portal.design.portaldesigndata%2Fthemes%2Fportal%2Fcustomer%2FDefault%2Fcontrols%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok		
	msslink = "${serverLinkUrl}"+msslink+"/default.htm?pa_pernr="+currentTreeClickUserEmpNo+"&"+cssStr;
	setLicurrent("#"+currentMssMenu);
	iKEP.popupOpen(msslink, {width:1000, height:600 }, "mss");
}

function mssTeamLinkUserChange(){
	var msslink;

	//alert(currentMssMenu);
	if(currentMssMenu==""){
		<c:if test="${Bigmenu eq 'personalMng'}">
		currentMssMenu="zhr_pa_046";
		</c:if>
		<c:if test="${Bigmenu eq 'diligencePayMng'}">
		currentMssMenu="zhr_py_005";
		</c:if>
	}
	document.domain ="moorim.co.kr";//밑에서 프레임 리사이징을 할때 iframe의 높이를 jquery의 contents()함수를 이용해 가져온다. 이때 같은 도메인이어야 exception나지 않는다. 
	var teamviewX ="";
	if(currentTeamX=="X"){
		teamviewX="&TEAMV=X";
	}
	msslink = "${serverLinkUrl}"+currentMssMenu+"/default.htm?pa_pernr="+currentTreeClickUserEmpNo+teamviewX+"&"+cssStr;

	setLicurrent("#"+currentMssMenu);
	iKEP.iFrameMenuOnclick(msslink);
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

function loginUserTree(){
	createGroupTree("${mssuser.userId}");
}

</c:if>



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
					 		
					 		if(webMssUrlFlag){
					 			mssWebLink(webMssUrl, true);
					 		}else{
					 			mssTeamLinkUserChange();//보던 페이지에 사람을 바꿔서 호출.
					 		}
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
		

		/*
		if(window.location.href.indexOf("meetingroom/main") > 0 ) {
			setLicurrent("#meetingRoomReserve");
		} else if (window.location.href.indexOf("myReserveList") > 0 ) {
			setLicurrent("#meetingRoomReserveList");
		} 
		*/
		<c:if test="${Bigmenu != 'personalMng'&& Bigmenu != 'diligencePayMng' && Bigmenu != 'manPowerMng' && Bigmenu != 'evaluationMng'}">
		iKEP.setLeftMenu();//팀뷰어를 볼때는 좌측메뉴접기기능을 사용하지 않는다.
		</c:if>
		
		
		<c:if test="${Bigmenu eq 'personalMng'||Bigmenu eq 'diligencePayMng'||Bigmenu eq 'manPowerMng'||Bigmenu eq 'evaluationMng'}">
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		
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
		 		//alert(currentTreeClickUserEmpNo);

		 		if(webMssUrlFlag){
		 			mssWebLink(webMssUrl, true);
		 		}else{
		 			mssTeamLinkUserChange();//보던 페이지에 사람을 바꿔서 호출.
		 		}
		 	}
	     });
   	 	

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		</c:if>
		
		
		
		
		document.domain ="moorim.co.kr";
		//iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/initMssMain.do'/>");//ESS Main 페이지를 호출하며 인증을 태운다.
		
		<c:if test="${Bigmenu eq 'personalMng'}">
			mssWebLink('personalInfo');
		</c:if>
		<c:if test="${Bigmenu eq 'organMng'}">
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/initMssMain.do'/>?whereLink=zhr_pd_031");
		</c:if>
		
		<c:if test="${Bigmenu eq 'manPowerMng'}">
		hideTeamView(true);
		<c:choose>
		  	<c:when test="${((mssuser.mssAuthCode ne 'M5') && (mssuser.mssAuthCode ne 'M6')&& (mssuser.mssAuthCode ne 'M7') )||isSystemAdmin}">
				iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/initMssMain.do'/>?whereLink=zhr_pa_032");
			</c:when>
			<c:otherwise>
				setLicurrent('#zhr_pd_014');
				iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/initMssMain.do'/>?whereLink=zhr_pd_014");
			</c:otherwise>
		</c:choose>
		</c:if>
		<c:if test="${Bigmenu eq 'evaluationMng'}">
			hideTeamView(true);
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/evaluationMng/evaluationList.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo);
		</c:if>
		
		
		<c:if test="${Bigmenu eq 'evaluationMng'}">
		<c:choose>
		  	<c:when test="${((mssuser.mssAuthCode ne 'M5') &&( mssuser.mssAuthCode ne 'M7') )||isSystemAdmin}">
		  	iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/evaluationMng/evaluationList.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo);
			</c:when>
			<c:otherwise>
				setLicurrent('#zhr_hap_005');
				iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/evaluationMng/evaluationList.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo);
			</c:otherwise>
		</c:choose>
		</c:if>
		
		<c:if test="${Bigmenu eq 'laborMng'}">
		<c:choose>
		  	<c:when test="${mssuser.mssAuthCode eq 'M7'||mssuser.mssAuthCode eq 'M8'||mssuser.mssAuthCode eq 'M9'||mssuser.mssAuthCode eq 'M10'||isSystemAdmin}">
		  	iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/laborMng/laborPositionList.do'/>");
			</c:when>
			<c:otherwise>
				setLicurrent('#zhr_pa_049');
				iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/laborMng/laborPositionList.do'/>");
			</c:otherwise>
		</c:choose>
		</c:if>
		
		
		<c:if test="${Bigmenu eq 'diligencePayMng'}">mssWebLink('payEarning');</c:if>
		
		<c:if test="${Bigmenu eq 'personalMng'||Bigmenu eq 'diligencePayMng'}">
		setTimeout("loginUserTree()", 500);//최종적으로 로그인사용자 소속 부서로 트리를 연다.
		</c:if>

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
	
	webMssUrl = menu;
	webMssUrlFlag = true;
	setLicurrent("#"+menu);
	
	if(menu == 'monthDilegence'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/monthDilegenceView.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo);
	}else if(menu == 'yearMonthPaidHoliday'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/yearMonthPaidHolidayList.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo);
	}else if(menu == 'payEarning'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/payEarningList.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo);
	}else if(menu == 'holidayUse'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/holidayUseList.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo);
	}else if(menu == 'deadline'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/deadlineList.do'/>");
	}else if(menu == 'assessments'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/assessmentsList.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo+"&setEmpFlag="+setEmpFlag);
	}else if(menu == 'workHistory'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/workHistoryList.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo+"&setEmpFlag="+setEmpFlag);
	}else if(menu == 'monthDiligenceReport'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/monthDiligenceReportList.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo+"&setEmpFlag="+setEmpFlag);
	}else if(menu == 'overtimeWorkTotal'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/overtimeWorkTotalView.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo+"&setEmpFlag="+setEmpFlag);
	}else if(menu == 'dayOff'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/dayOffList.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo+"&setEmpFlag="+setEmpFlag);
	}else if(menu == 'work'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/workList.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo+"&setEmpFlag="+setEmpFlag);
	}else if(menu == 'familyEvent'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/familyEventList.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo+"&setEmpFlag="+setEmpFlag);
	}else if(menu == 'altWorkIndividual'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/altWorkIndividualList.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo+"&setEmpFlag="+setEmpFlag);
	}else if(menu == 'altWorkBatch'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/altWorkBatchList.do'/>");
	}else if(menu == 'overtimeIndividual'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/overtimeIndividualList.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo+"&setEmpFlag="+setEmpFlag);
	}else if(menu == 'overtimeBatch'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/diligencePayMng/overtimeBatchList.do'/>");
	}else if(menu == 'personalInfo'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/personalMng/personalInfoView.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo);
	}else if(menu == 'evaluationList'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/evaluationMng/evaluationList.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo);
	}else if(menu == 'laborPosition'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/laborMng/laborPositionList.do'/>");
	}else if(menu == 'laborInterview'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/laborMng/laborInterviewList.do'/>");
	}else if(menu == 'raterEvaluation'){
		iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/evaluationMng/raterEvaluationList.do'/>");
	}
}
</script>

<!--leftMenu Start-->
<h2 class="han">
	<c:if test="${Bigmenu eq 'personalMng'}"><a href="<c:url value='/portal/moorimmss/webPersonalMng.do'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_mss_person.gif'/>" title="인사관리"></a></c:if>
	<c:if test="${Bigmenu eq 'organMng'}"><a href="<c:url value='/portal/moorimmss/webOrganMng.do'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_mss_org.gif'/>" title="조직관리"></a></c:if>
	<c:if test="${Bigmenu eq 'manPowerMng'}"><a href="<c:url value='/portal/moorimmss/webManPowerMng.do'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_mss_manpower.gif'/>" title="인재개발"></a></c:if>
	<c:if test="${Bigmenu eq 'evaluationMng'}"><a href="<c:url value='/portal/moorimmss/webEvaluationMng.do'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_mss_evalu.gif'/>" title="평가관리"></a></c:if>
	<c:if test="${Bigmenu eq 'laborMng'}"><a href="<c:url value='/portal/moorimmss/webLaborMng.do'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_mss_labor.gif'/>" title="노무관리"></a></c:if>
	<c:if test="${Bigmenu eq 'diligencePayMng'}"><a href="<c:url value='/portal/moorimmss/webDiligencePayMng.do'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_mss_pay.gif'/>" title="근태/급여"></a></c:if>

</h2>
<div class="left_fixed" id="leftMenu-pane">
<c:if test="${Bigmenu eq 'personalMng'}">
	<ul>
	    
		<li class="liFirst opened licurrent"  ><a href="#">인사관리</a>
			<ul>
				<li class="no_child" id="personalInfo"><a  href="javascript:mssWebLink('personalInfo');">인사기본조회</a></li>
			</ul>
		</li>
	</ul>
</c:if>

<c:if test="${Bigmenu eq 'evaluationMng'}">
	<ul>
		<c:if test="${((mssuser.mssAuthCode ne 'M5') && (mssuser.mssAuthCode ne 'M6') )||isSystemAdmin}">
		<li class=""><a href="#">평가결과</a>
			<ul>
				<li class="no_child" id="evaluationList"><a href="javascript:hideTeamView(false);mssWebLink('evaluationList')">평가이력조회</a></li>
			</ul>
		</li>
		</c:if>
	</ul>
</c:if>

<c:if test="${Bigmenu eq 'laborMng'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">노무관리</a>
			<ul>
				<c:if test="${mssuser.mssAuthCode eq 'M7'||mssuser.mssAuthCode eq 'M8'||mssuser.mssAuthCode eq 'M9'||mssuser.mssAuthCode eq 'M10'||isSystemAdmin}">
				<li class="no_child" id="laborPosition"><a  href="javascript:mssWebLink('laborPosition');">노조직책자 현황 조회</a></li>
				</c:if>
				<li class="no_child" id="laborInterview"><a  href="javascript:mssWebLink('laborInterview');">노무관리 면담일지</a></li>
			</ul>
		</li>
	</ul>
</c:if>

<c:if test="${Bigmenu eq 'diligencePayMng'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">근태/급여</a>
			<ul>
				<li class="no_child" id="payEarning"><a  href="javascript:mssWebLink('payEarning');">급여실적 조회</a></li>
				<li class="no_child" id="monthDilegence"><a href="javascript:mssWebLink('monthDilegence')">월근태현황 조회</a></li>
				<li class="no_child" id="yearMonthPaidHoliday"><a href="javascript:mssWebLink('yearMonthPaidHoliday')">연월차내역 조회</a></li>
				<li class="no_child" id="holidayUse"><a href="javascript:mssWebLink('holidayUse')">휴가사용현황 조회</a></li>

				<c:if test="${mssuser.mssAuthCode eq 'M3'||mssuser.mssAuthCode eq 'M4'||mssuser.mssAuthCode eq 'M5'||mssuser.mssAuthCode eq 'M6'}">
				<li class="no_child" id="dayOff"><a  href="javascript:mssWebLink('dayOff');">휴무 개별입력</a></li>
				<li class="no_child" id="work"><a  href="javascript:mssWebLink('work');">근무 개별입력</a></li>
				<li class="no_child" id="familyEvent"><a  href="javascript:mssWebLink('familyEvent');">경조사 개별입력</a></li>
				<li class="no_child" id="altWorkIndividual"><a  href="javascript:mssWebLink('altWorkIndividual')">대체근무 개별입력</a></li>
				<li class="no_child" id="altWorkBatch"><a  href="javascript:mssWebLink('altWorkBatch');">대체근무 일괄입력</a></li>
				<li class="no_child" id="overtimeIndividual"><a  href="javascript:mssWebLink('overtimeIndividual');">연장근로 개별입력</a></li>
				<li class="no_child" id="overtimeBatch"><a  href="javascript:mssWebLink('overtimeBatch');">연장근로 일괄입력</a></li>
				<li class="no_child" id="deadline"><a href="javascript:mssWebLink('deadline')">근태마감</a></li>
				<li class="no_child" id="assessments"><a href="javascript:mssWebLink('assessments')">근태평가</a></li>
				</c:if>
				<c:if test="${mssuser.mssAuthCode eq 'M1'||mssuser.mssAuthCode eq 'M2'||mssuser.mssAuthCode eq 'M3'||mssuser.mssAuthCode eq 'M4'||mssuser.mssAuthCode eq 'M5'||mssuser.mssAuthCode eq 'M6'||mssuser.mssAuthCode eq 'M8'||mssuser.mssAuthCode eq 'M9'||mssuser.mssAuthCode eq 'M10'}">
				<li class="no_child" id="workHistory"><a href="javascript:mssWebLink('workHistory');">근무내역서</a></li>
				</c:if>
				<c:if test="${mssuser.mssAuthCode eq 'M3'||mssuser.mssAuthCode eq 'M4'||mssuser.mssAuthCode eq 'M5'||mssuser.mssAuthCode eq 'M6'}">
				<li class="no_child" id="monthDiligenceReport"><a href="javascript:mssWebLink('monthDiligenceReport');">월근태보고서 조회</a></li>
				<li class="no_child" id="overtimeWorkTotal"><a  href="javascript:mssWebLink('overtimeWorkTotal');">연장근로 집계표 조회</a></li>
				</c:if>
				
			</ul>
		</li>
	</ul>
</c:if>
</div>

<c:if test="${Bigmenu eq 'personalMng'||Bigmenu eq 'diligencePayMng'||Bigmenu eq 'manPowerMng'||Bigmenu eq 'evaluationMng' }">

<div height="20px">&nbsp;</div>

<div class="btn_tr_enlarge_mss" id="btnDivSize"><a href="#a" id="sizeAction"><span>Enlarge</span></a></div>


<div id="divTab_s" class="iKEP_tab_s" >
	<ul>
		<li><a href="#tabs-org">팀뷰어</a></li>	
		<c:if test="${mssuser.jobDutyCode != '22'}">		
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
	


	
<script id="tmplUserItemEmpty" type="text/x-jquery-tmpl">
	<tr><td colspan="2" class="emptyRecord">검색결과가 없습니다.</td></tr>
</script>
		
<script id="tmplUserItem" type="text/x-jquery-tmpl">
	<tr>
		<td class="textLeft"><a href="#a">\${userName} \${jobTitleName}</a></td>
		<td class="textLeft">\${teamName}</td>
	</tr>
</script>

</c:if>
