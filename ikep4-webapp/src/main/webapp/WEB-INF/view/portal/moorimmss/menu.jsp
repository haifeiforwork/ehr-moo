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

var regurl=/eptest.moorim.co.kr/g;
var cssStr="sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Feptest.moorim.co.kr%2Fikep4-webapp%2Fbase%2Fcss%2Fsap%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok	
if (regurl.test(location.href)) { 
	cssStr="sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Feptest.moorim.co.kr%2Fikep4-webapp%2Fbase%2Fcss%2Fsap%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok	
}else{
	cssStr="sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Fep.moorim.co.kr%2Fbase%2Fcss%2Fsap%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok	
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
	currentMssMenu = msslink;
	document.domain ="moorim.co.kr";//밑에서 프레임 리사이징을 할때 iframe의 높이를 jquery의 contents()함수를 이용해 가져온다. 이때 같은 도메인이어야 exception나지 않는다. 
	//msslink = "${serverLinkUrl}"+msslink+"/default.htm?sap-syscmd=nocookie&sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportalapps%2Fcom.sap.portal.design.portaldesigndata%2Fthemes%2Fportal%2Fcustomer%2FDefault%2Fcontrols%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok		

	
	msslink = "${serverLinkUrl}"+msslink+"/default.htm?"+cssStr;

	setLicurrent("#"+currentMssMenu);
	iKEP.iFrameMenuOnclick(msslink);

}

function mssLinkPopup(msslink){
	currentMssMenu = msslink;
	document.domain ="moorim.co.kr";//밑에서 프레임 리사이징을 할때 iframe의 높이를 jquery의 contents()함수를 이용해 가져온다. 이때 같은 도메인이어야 exception나지 않는다. 
	//msslink = "${serverLinkUrl}"+msslink+"/default.htm?sap-syscmd=nocookie&sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportalapps%2Fcom.sap.portal.design.portaldesigndata%2Fthemes%2Fportal%2Fcustomer%2FDefault%2Fcontrols%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok		

	
	msslink = "${serverLinkUrl}"+msslink+"/default.htm?"+cssStr;

	setLicurrent("#"+currentMssMenu);
	iKEP.popupOpen(msslink, {width:1000, height:600 }, "mss");

}

<c:if test="${Bigmenu eq 'personalMng'||Bigmenu eq 'diligencePayMng'||Bigmenu eq 'manPowerMng'||Bigmenu eq 'evaluationMng'}">


function mssTeamLink(msslink, teamX){
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
					 		mssTeamLinkUserChange();//보던 페이지에 사람을 바꿔서 호출.
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
		 		mssTeamLinkUserChange();//보던 페이지에 사람을 바꿔서 호출.
		 	}
	     });
   	 	

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		</c:if>
		
		
		
		
		document.domain ="moorim.co.kr";
		//iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/initMssMain.do'/>");//ESS Main 페이지를 호출하며 인증을 태운다.
		
		<c:if test="${Bigmenu eq 'personalMng'}">
			//alert("여기");
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/initMssMain.do'/>?whereLink=zhr_pa_046");
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
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/initMssMain.do'/>?whereLink=zhr_hap_010");
		</c:if>
		
		
		<c:if test="${Bigmenu eq 'evaluationMng'}">
		<c:choose>
		  	<c:when test="${((mssuser.mssAuthCode ne 'M5') &&( mssuser.mssAuthCode ne 'M7') )||isSystemAdmin}">
				iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/initMssMain.do'/>?whereLink=zhr_hap_002");
			</c:when>
			<c:otherwise>
				setLicurrent('#zhr_hap_005');
				iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/initMssMain.do'/>?whereLink=zhr_hap_005");
			</c:otherwise>
		</c:choose>
		</c:if>
		
		<c:if test="${Bigmenu eq 'laborMng'}">
		<c:choose>
		  	<c:when test="${mssuser.mssAuthCode eq 'M7'||mssuser.mssAuthCode eq 'M8'||mssuser.mssAuthCode eq 'M9'||mssuser.mssAuthCode eq 'M10'||isSystemAdmin}">
				iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/initMssMain.do'/>?whereLink=zhr_pa_037");
			</c:when>
			<c:otherwise>
				setLicurrent('#zhr_pa_049');
				iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/initMssMain.do'/>?whereLink=zhr_pa_049");
			</c:otherwise>
		</c:choose>
		</c:if>
		
		
		<c:if test="${Bigmenu eq 'diligencePayMng'}">iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimmss/initMssMain.do'/>?whereLink=zhr_py_005");</c:if>
		
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
</script>

<!--leftMenu Start-->
<h2 class="han">
	<c:if test="${Bigmenu eq 'personalMng'}"><a href="<c:url value='/portal/moorimmss/personalMng.do'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_mss_person.gif'/>" title="인사관리"></a></c:if>
	<c:if test="${Bigmenu eq 'organMng'}"><a href="<c:url value='/portal/moorimmss/organMng.do'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_mss_org.gif'/>" title="조직관리"></a></c:if>
	<c:if test="${Bigmenu eq 'manPowerMng'}"><a href="<c:url value='/portal/moorimmss/manPowerMng.do'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_mss_manpower.gif'/>" title="인재개발"></a></c:if>
	<c:if test="${Bigmenu eq 'evaluationMng'}"><a href="<c:url value='/portal/moorimmss/evaluationMng.do'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_mss_evalu.gif'/>" title="평가관리"></a></c:if>
	<c:if test="${Bigmenu eq 'laborMng'}"><a href="<c:url value='/portal/moorimmss/laborMng.do'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_mss_labor.gif'/>" title="노무관리"></a></c:if>
	<c:if test="${Bigmenu eq 'diligencePayMng'}"><a href="<c:url value='/portal/moorimmss/diligencePayMng.do'/>"><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_mss_pay.gif'/>" title="근태/급여"></a></c:if>

</h2>
<div class="left_fixed" id="leftMenu-pane">
<c:if test="${Bigmenu eq 'personalMng'}">
	<ul>
	    
		<li class="liFirst opened licurrent"  ><a href="#">인사관리</a>
			<ul>
				<li class="no_child licurrent" id="zhr_pa_046"><a  href="javascript:mssTeamLink('zhr_pa_046');">인사기본조회</a></li>
			</ul>
		</li>
	</ul>
</c:if>

<c:if test="${Bigmenu eq 'organMng'}">
	<ul>
	    
		<li class="liFirst opened licurrent"><a href="#">조직관리</a>
			<ul>
				<li class="no_child licurrent" id="zhr_pd_031"><a  href="javascript:mssLink('zhr_pd_031');">인원확보 요청</a></li>
				<li class="no_child" id="zhr_pd_034"><a  href="javascript:mssLink('zhr_pd_034');">부서원 업무분장 합의</a></li>
				<li class="no_child" id="zhr_pd_035"><a  href="javascript:mssLink('zhr_pd_035');">부서원 업무분장 조회</a></li>
			</ul>
		</li>
	</ul>
</c:if>

<c:if test="${Bigmenu eq 'manPowerMng'}">
	<ul>
		<c:if test="${mssuser.mssAuthCode ne 'M7'||isSystemAdmin}">
		<li class="liFirst opened licurrent"><a href="#">인재개발</a>
			<ul>
				<c:if test="${((mssuser.mssAuthCode ne 'M5') && (mssuser.mssAuthCode ne 'M6')&& (mssuser.mssAuthCode ne 'M7') )||isSystemAdmin}">
				<li class="no_child licurrent" id="zhr_pa_032"><a  href="javascript:hideTeamView(true);mssLink('zhr_pa_032');">승격정보 조회</a></li>
				</c:if>
				<c:if test="${mssuser.mssAuthCode eq 'M1'||mssuser.mssAuthCode eq 'M3'||mssuser.mssAuthCode eq 'M8'||mssuser.mssAuthCode eq 'M9'||isSystemAdmin}">
				<li class="no_child" id="zhr_pd_019"><a  href="javascript:hideTeamView(true);mssLink('zhr_pd_019');">지도사원 평가</a></li>
				</c:if>	
				<c:if test="${mssuser.mssAuthCode ne 'M7'||isSystemAdmin}">
				<li class="no_child" id="zhr_pd_014"><a  href="javascript:hideTeamView(false);loginUserTree();mssTeamLink('zhr_pd_014');">교육이력 조회</a></li>
				</c:if>
				<c:if test="${mssuser.mssAuthCode eq 'M9'||isSystemAdmin}">
				<li class="no_child" id="zhr_pa_050"><a  href="javascript:hideTeamView(true);mssLink('zhr_pa_050');">Succession Plan</a></li>
				</c:if>
			</ul>
		</li>
		</c:if>
		<c:if test="${((mssuser.mssAuthCode ne 'M5') && (mssuser.mssAuthCode ne 'M7') )||isSystemAdmin}">
		<li class=""><a href="#">경력개발계획</a>
			<ul>
				<c:if test="${mssuser.mssAuthCode != 'M6'||isSystemAdmin}">
				<li class="no_child" id="zhr_pd_025"><a  href="javascript:hideTeamView(true);mssLink('zhr_pd_025');">중장기 경력개발계획 합의</a></li>
				<li class="no_child" id="zhr_pd_026"><a  href="javascript:hideTeamView(false);loginUserTree();mssTeamLink('zhr_pd_026');">중장기 경력개발계획 조회</a></li>
				<li class="no_child" id="zhr_pd_028"><a  href="javascript:hideTeamView(true);mssLink('zhr_pd_028');">연간 경력개발계획 합의</a></li>
				</c:if>
				<li class="no_child" id="zhr_pd_029"><a  href="javascript:hideTeamView(false);loginUserTree();mssTeamLink('zhr_pd_029');">연간 경력개발계획 조회</a></li>	
			</ul>
		</li>
		</c:if>
	</ul>
</c:if>

<c:if test="${Bigmenu eq 'evaluationMng'}">
	<ul>
		<c:if test="${((mssuser.mssAuthCode ne 'M5') && (mssuser.mssAuthCode ne 'M7') )||isSystemAdmin}">
		<li class="liFirst opened licurrent"><a href="#">업적 평가</a>
			<ul>
				<li class="no_child licurrent" id="zhr_hap_002"><a  href="javascript:hideTeamView(true);mssLinkPopup('zhr_hap_002');">업적평가 목표합의 진행</a></li>
				<li class="no_child" id="zhr_hap_008"><a  href="javascript:hideTeamView(true);mssLinkPopup('zhr_hap_008');">업적평가 중간면담 진행</a></li>
				<li class="no_child" id="zhr_hap_006"><a  href="javascript:hideTeamView(true);mssLinkPopup('zhr_hap_006');">업적평가 평가자 평가 진행</a></li>
				<li class="no_child" id="zhr_hap_007"><a  href="javascript:hideTeamView(true);mssLinkPopup('zhr_hap_007');">업적평가결과 임원Review 진행 </a></li>	
			</ul>
		</li>
		<li class=""><a href="#">역량 평가</a>
			<ul>
				<li class="no_child" id="zhr_hap_012"><a  href="javascript:hideTeamView(true);mssLinkPopup('zhr_hap_012');">역량평가 목표합의 진행</a></li>
				<li class="no_child" id="zhr_hap_015"><a  href="javascript:hideTeamView(true);mssLinkPopup('zhr_hap_015');">역량평가 중간면담 진행</a></li>
				<li class="no_child" id="zhr_hap_013"><a  href="javascript:hideTeamView(true);mssLinkPopup('zhr_hap_013');">역량평가 평가자 평가 진행</a></li>
				<li class="no_child" id="zhr_hap_014"><a  href="javascript:hideTeamView(true);mssLinkPopup('zhr_hap_014');">역량평가결과 Review 진행 </a></li>	
			</ul>
		</li>
		<li class=""><a href="#">5급사원 평가</a>
			<ul>
				<li class="no_child" id="zhr_hap_01=003"><a  href="javascript:hideTeamView(true);mssLinkPopup('zhr_hap_003');">5급사원 평가자 평가</a></li>
	
			</ul>
		</li>
		</c:if>
		<c:if test="${((mssuser.mssAuthCode ne 'M1') && (mssuser.mssAuthCode ne 'M2') )||isSystemAdmin}">
		<li class=""><a href="#">기능직 평가</a>
			<ul>
				<li class="no_child" id="zhr_hap_005"><a  href="javascript:hideTeamView(true);mssLinkPopup('zhr_hap_005');">기능직평가 평가자 평가</a></li>
				<li class="no_child" id="zhr_hap_018"><a  href="javascript:hideTeamView(true);mssLinkPopup('zhr_hap_018');">기능직평가 평가결과 Feedback</a></li>
			</ul>
		</li>
		</c:if>
		<c:if test="${((mssuser.mssAuthCode ne 'M5') && (mssuser.mssAuthCode ne 'M6') )||isSystemAdmin}">
		<li class=""><a href="#">평가결과</a>
			<ul>
				<li class="no_child" id="zhr_hap_010"><a  href="javascript:hideTeamView(false);loginUserTree();mssTeamLink('zhr_hap_010');">평가이력조회</a></li>
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
				<li class="no_child licurrent" id="zhr_pa_037"><a  href="javascript:mssLink('zhr_pa_037');">노사 협의회 회의록</a></li>
				<li class="no_child" id="zhr_pa_038"><a  href="javascript:mssLink('zhr_pa_038');">노동조합 조직도</a></li>
				<li class="no_child" id="zhr_pa_040"><a  href="javascript:mssLink('zhr_pa_040');">임단협 회의록</a></li>
				<li class="no_child" id="zhr_pa_039"><a  href="javascript:mssLink('zhr_pa_039');">임단협 합의서</a></li>
				<li class="no_child" id="zhr_pa_024"><a  href="javascript:mssLink('zhr_pa_024');">노조직책자 현황 조회</a></li>
				</c:if>
				<li class="no_child" id="zhr_pa_049"><a  href="javascript:mssLink('zhr_pa_049');">임금비교자료</a></li>
				<li class="no_child" id="zhr_pd_023"><a  href="javascript:mssLink('zhr_pd_023');">노무관리 면담일지</a></li>
			</ul>
		</li>
	</ul>
</c:if>

<c:if test="${Bigmenu eq 'diligencePayMng'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">근태/급여</a>
			<ul>
				<li class="no_child licurrent" id="zhr_py_005"><a  href="javascript:mssTeamLink('zhr_py_005');">급여실적 조회</a></li>
				<li class="no_child" id="zhr_pt_011"><a  href="javascript:mssTeamLink('zhr_pt_011');">월근태현황 조회</a></li>
				<li class="no_child" id="zhr_pt_012"><a  href="javascript:mssTeamLink('zhr_pt_012');">연월차내역 조회</a></li>
				<li class="no_child" id="zhr_pt_013"><a  href="javascript:mssTeamLink('zhr_pt_013');">휴가사용현황 조회</a></li>

				<c:if test="${mssuser.mssAuthCode eq 'M3'||mssuser.mssAuthCode eq 'M4'||mssuser.mssAuthCode eq 'M5'||mssuser.mssAuthCode eq 'M6'||isSystemAdmin}">
				<li class="no_child" id="zhr_pt_018"><a  href="javascript:mssTeamLink('zhr_pt_018','X');">휴무 개별입력</a></li>
				<li class="no_child" id="zhr_pt_019"><a  href="javascript:mssTeamLink('zhr_pt_019','X');">근무 개별입력</a></li>
				<li class="no_child" id="zhr_pt_024"><a  href="javascript:mssTeamLink('zhr_pt_024','X');">경조사 개별입력</a></li>
				<li class="no_child" id="zhr_pt_020"><a  href="javascript:mssTeamLink('zhr_pt_020','X');">대체근무 개별입력</a></li>
				<li class="no_child" id="zhr_pt_021"><a  href="javascript:mssTeamLink('zhr_pt_021','X');">대체근무 일괄입력</a></li>
				<li class="no_child" id="zhr_pt_022"><a  href="javascript:mssTeamLink('zhr_pt_022','X');">연장근로 개별입력</a></li>
				<li class="no_child" id="zhr_pt_023"><a  href="javascript:mssTeamLink('zhr_pt_023','X');">연장근로 일괄입력</a></li>
				<li class="no_child" id="zhr_pt_016"><a  href="javascript:mssTeamLink('zhr_pt_016','X');">근태마감</a></li>
				<li class="no_child" id="zhr_pt_025"><a  href="javascript:mssTeamLink('zhr_pt_025','X');">근태평가</a></li>
				</c:if>
				<c:if test="${mssuser.mssAuthCode eq 'M1'||mssuser.mssAuthCode eq 'M2'||mssuser.mssAuthCode eq 'M3'||mssuser.mssAuthCode eq 'M4'||mssuser.mssAuthCode eq 'M5'||mssuser.mssAuthCode eq 'M6'||mssuser.mssAuthCode eq 'M8'||mssuser.mssAuthCode eq 'M9'||mssuser.mssAuthCode eq 'M10'||isSystemAdmin}">
				<li class="no_child" id="zhr_pt_027"><a  href="javascript:mssTeamLink('zhr_pt_027','X');">근무내역서</a></li>
				</c:if>
				<c:if test="${mssuser.mssAuthCode eq 'M3'||mssuser.mssAuthCode eq 'M4'||mssuser.mssAuthCode eq 'M5'||mssuser.mssAuthCode eq 'M6'||isSystemAdmin}">
				<li class="no_child" id="zhr_pt_017"><a  href="javascript:mssTeamLink('zhr_pt_017','X');">월근태보고서 조회</a></li>
				<li class="no_child" id="zhr_pt_015"><a  href="javascript:mssTeamLink('zhr_pt_015','X');">연장근로 집계표 조회</a></li>
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
