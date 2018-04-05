<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<c:set var="prefix" value="message.portal.main.listUser"/>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>

<script type="text/javascript">
//<![CDATA[
var $groupTree;
var $searchWordId;
var isExtendTree = false;

var currentTreeClickUserEmpNo="${user.empNo}";
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


<c:if test="${Bigmenu eq 'ehrTeamMng'}">




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
	createGroupTree("${user.userId}");
}

</c:if>
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

   					 			mssWebLink(webMssUrl, true);
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
		$(document).ready(function() {

			iKEP.setLeftMenu();

			<c:if test="${Bigmenu eq 'ehrPersonalMng'}">
				<c:if test="${user.essAuthCode == 'E9'}">
					goPersonal('address');
			 	</c:if>
			 	<c:if test="${user.essAuthCode != 'E9'}">
			 		goPersonal('personAppoint');
			 	</c:if>
			</c:if>
			<c:if test="${Bigmenu eq 'ehrBusinessSupport'}">
				goPersonal('eduExpense');
			</c:if>
			<c:if test="${Bigmenu eq 'ehrManPowerMng'}">
				<c:choose>
					<c:when test="${user.essAuthCode != 'E8' && user.essAuthCode != 'E9'}">
						goEtc('JIKMU');
					</c:when>
					<c:otherwise>
						goEtc('QUALI');
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${Bigmenu eq 'ehrResultMng'}">
				<c:choose>
					<c:when test="${A01}">
						goPerformance('OBJECTIVE_SETTING');
					</c:when>
					<c:when test="${A02}">
					goPerformance('OBJECTIVE_AGREEMENT');
					</c:when>
					<c:when test="${A03}">
						goPerformance('OBJECTIVE_EVALUATION');
					</c:when>
					<c:when test="${A04}">
						goPerformance('OBJECTIVE_REVIEW');
					</c:when>
					<c:when test="${A05}">
						goPerformance('OBJECTIVE_REVIEW2');
					</c:when>
					<c:when test="${user.essAuthCode eq 'E1'||user.essAuthCode eq 'E2'}">
						goCompetence('COMPETENCE_FEEDBACK_ESS');
					</c:when>
					<c:otherwise></c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${Bigmenu == 'ehrDiligenceMng'}">
				goDiligence('holiday');
			</c:if>
			<c:if test="${Bigmenu == 'ehrPayMng'}">
				goPay('paystub');
			</c:if>
			<c:if test="${Bigmenu == 'ehrTeamMng'}">
				<c:choose>
					<c:when test="${user.mssAuthCode == 'M1'||user.mssAuthCode == 'M2'||user.mssAuthCode == 'M3'||user.mssAuthCode == 'M4'||user.mssAuthCode == 'M5'||user.mssAuthCode == 'M6'||user.mssAuthCode == 'M7'||user.mssAuthCode == 'M8'||user.mssAuthCode == 'M9'||user.mssAuthCode == 'M10'}">
						mssWebLink('personalInfo');
					</c:when>
					<c:when test="${user.essAuthCode == 'E0'}">
						goCompetence('COMPETENCE_FEEDBACK_MSS');
					</c:when>
					<c:otherwise></c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${Bigmenu == 'ehrHrMng'}">
				hassWebLink('hrApproval');
			</c:if>

			<c:if test="${Bigmenu eq 'ehrTeamMng'}">
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

		 			mssWebLink(webMssUrl, true);
			 	}
		     });


			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			</c:if>



			<c:if test="${Bigmenu eq 'ehrTeamMng'}">
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

    function goPerformance(menu) {
		if(menu == 'OBJECTIVE_REVIEW') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/performance/objectiveReviewList.do'/>");
		} else if(menu == 'OBJECTIVE_REVIEW2') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/performance/objectiveReview2List.do'/>");
		} else if(menu == 'OBJECTIVE_HSS') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/performance/objectiveHSS.do'/>");
		} else {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/performance/objectiveList.do?action="+menu+"'/>");
		}
	}

	function goCompetence(menu) {
		if(menu == 'COMPETENCE_REVIEW') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/competence/competenceReviewList.do'/>");
		} else if(menu == 'COMPETENCE_REVIEW2') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/competence/competenceReview2List.do'/>");
		} else if(menu == 'COMPETENCE_HSS') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/competence/competenceHSS.do'/>");
		} else if(menu == 'COMPETENCE_FEEDBACK_MSS' || menu == 'COMPETENCE_FEEDBACK_ESS') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/competence/competenceFeedBackList.do?action="+menu+"'/>");
		} else {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/competence/competenceList.do?action="+menu+"'/>");
		}
	}

	function goMultiside(menu) {
		if(menu == 'MULTISIDE_DIAGNOSIS') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/multiside/multisideDiagnosisList.do?action="+menu+"'/>");
		} else if(menu == 'MULTISIDE_FEEDBACK') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/multiside/multisideFeedbackList.do'/>");
		}
	}

	function goIdp(menu) {
		if(menu == 'IDP_HSS') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/idp/idpHSS.do'/>");
		} else {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/idp/idpList.do?action="+menu+"'/>");
		}
	}

	function goPromotion(menu) {
		iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/promotion/promotionList.do?action="+menu+"'/>");
	}

	function goTechnical(menu) {
		if(menu == 'EVALUATION') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/technical/technicalList.do?action="+menu+"'/>");
		} else if(menu == 'REVIEW') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/technical/technicalReviewList.do?action="+menu+"'/>");
		} else if(menu == 'HSS') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/technical/technicalHSS.do?action="+menu+"'/>");
		}
	}

	function goPrivilege(menu) {
		if(menu == 'EVALUATION') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/privilege/privilegeList.do?action="+menu+"'/>");
		} else if(menu == 'HSS') {
			iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/privilege/privilegeHSS.do?action="+menu+"'/>");
		}
	}

	function goPosition() {
		iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/position/positionList.do'/>");
	}

	function goEtc(menu) {
		iKEP.iFrameMenuOnclick("<c:url value='/portal/evaluation/etc/retrieve.do?action="+menu+"'/>");
	}

	function goPersonal(menu){
		if(menu == 'address'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/addressList.do'/>");
		}else if(menu == 'education'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/educationList.do'/>");
		}else if(menu == 'family'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/familyList.do'/>");
		}else if(menu == 'prizeDiscipline'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/prizeDisciplineList.do'/>");
		}else if(menu == 'appoint'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/appointList.do'/>");
		}else if(menu == 'foreignLanguage'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/foreignLanguageList.do'/>");
		}else if(menu == 'qualification'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/qualificationList.do'/>");
		}else if(menu == 'personal'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/personalView.do'/>");
		}else if(menu == 'career'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/careerList.do'/>");
		}else if(menu == 'congratulate'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/congratulateList.do'/>");
		}else if(menu == 'cafeteria'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/cafeteriaList.do'/>");
		}else if(menu == 'expense'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/expenseList.do'/>");
		}else if(menu == 'club'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/clubList.do'/>");
		}else if(menu == 'certificateEmp'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/certificateEmpList.do'/>");
		}else if(menu == 'certificate'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/certificateList.do'/>");
		}else if(menu == 'taxWithholding'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/taxWithholdingList.do'/>");
		}else if(menu == 'taxWithholdingView'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/taxWithholdingView.do'/>");
		}else if(menu == 'eduExpense'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/eduExpenseList.do'/>");
		}else if(menu == 'personAppoint'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalMng/personAppointList.do'/>");
		}
	}

	function goPay(menu){
		if(menu == 'paystub'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/payMng/paystubView.do'/>");
		}else if(menu == 'payEarning'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/payMng/payEarningView.do'/>");
		}else if(menu == 'loan'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/payMng/loanList.do'/>");
		}else if(menu == 'yearEndSettlement'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/payMng/yearEndSettlementRegView.do'/>");
		}else if(menu == 'settlementDocument'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/payMng/settlementDocumentView.do'/>");
		}
	}

	function goDiligence(menu){
		if(menu == 'holiday'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/diligenceMng/holidayList.do'/>");
		}else if(menu == 'holidayWork'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/diligenceMng/holidayWorkList.do'/>");
		}else if(menu == 'businessTrip'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/diligenceMng/businessTripList.do'/>");
		}else if(menu == 'yearMonthPaidHoliday'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/diligenceMng/yearMonthPaidHolidayList.do'/>");
		}else if(menu == 'leaveReinstatement'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/diligenceMng/leaveReinstatementList.do'/>");
		}else if(menu == 'evaluation'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/diligenceMng/evaluationList.do'/>");
		}else if(menu == 'holidayAmass'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/diligenceMng/holidayAmassRegView.do'/>");
		}
	}

	function goOrganogram(menu){
		if(menu == 'organizationChart'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/organogramMng/organizationChartList.do'/>");
		}
	}

	function goManPower(menu){
		if(menu == 'jobProfile'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/manPowerMng/jobProfileView.do'/>");
		}else if(menu == 'capaCatalog'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/manPowerMng/capaCatalogView.do'/>");
		}else if(menu == 'statusPoint'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/manPowerMng/statusPointList.do'/>");
		}
	}

	function goPersonalDivision(menu){
		if(menu == 'personalDivision'){
			iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/personalDivisionMng/personalDivisionMngList.do'/>");
		}
	}

	function hassWebLink(menu, setEmpFlag){

		webHassUrl = menu;
		webHassUrlFlag = true;

		if(menu == 'hrApproval'){
			if(setEmpFlag == true){
				iKEP.popupOpen("<c:url value='/portal/moorimhass/hrApproval.do'/>"+"?selEmpNo="+currentTreeClickUserEmpNo+"&setEmpFlag="+setEmpFlag, {width:1000, height:600 }, "hass");
			}else{
				iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimhass/hrApproval.do'/>");
			}
		}
	}
//]]>
</script>

<!--leftMenu Start-->
<h2 class="han">
	<c:if test="${Bigmenu eq 'ehrPersonalMng'}">
			<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
			<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">인사관리</font>
	</c:if>
	<c:if test="${Bigmenu eq 'ehrBusinessSupport'}">
			<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
			<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">업무지원</font>
	</c:if>
	<c:if test="${Bigmenu eq 'ehrManPowerMng'}">
			<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
			<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">인재개발</font>
	</c:if>
	<c:if test="${Bigmenu eq 'ehrResultMng'}">
			<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
			<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">성과관리</font>
	</c:if>
	<c:if test="${Bigmenu eq 'ehrDiligenceMng'}">
			<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
			<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">근태/출장</font>
	</c:if>
	<c:if test="${Bigmenu eq 'ehrPayMng'}">
			<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
			<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">급여</font>
	</c:if>
	<c:if test="${Bigmenu eq 'ehrTeamMng'}">
			<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
			<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">부서원관리</font>
	</c:if>
	<c:if test="${Bigmenu eq 'ehrHrMng'}">
			<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
			<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">HR담당</font>
	</c:if>
</h2>
<div class="left_fixed" id="leftMenu-pane" style="overflow: inherit;">
<c:if test="${Bigmenu eq 'ehrPersonalMng'}">
	<ul>
	    <c:if test="${user.essAuthCode == 'E9'}">
	    	<li class="liFirst opened licurrent"><a href="#">개인정보</a>
				<ul>
					<li class="no_child" id="zhr_pa_008"><a  href="javascript:goPersonal('address')">주소사항 조회</a></li>
					<li class="no_child" id="zhr_pa_021"><a href="javascript:goPersonal('personal')");">신상조회</a></li>
				</ul>
			</li>
	    </c:if>
	    <c:if test="${user.essAuthCode != 'E9'}">
			<li class="liFirst opened licurrent"><a href="#">개인정보</a>
				<ul>
					<li class="no_child" id="zhr_es_001"><a  href="javascript:goPersonal('personAppoint')">전사발령 조회</a></li>
					<li class="no_child" id="zhr_pa_002"><a  href="javascript:goPersonal('appoint')">발령사항 조회</a></li>
					<li class="no_child" id="zhr_pa_008"><a  href="javascript:goPersonal('address')">주소사항 조회</a></li>
					<li class="no_child" id="zhr_pa_012"><a  href="javascript:goPersonal('education')">학력사항 조회</a></li>
					<li class="no_child" id="zhr_pa_009"><a  href="javascript:goPersonal('family')">가족사항 조회</a></li>
					<li class="no_child" id="zhr_pa_011"><a  href="javascript:goPersonal('prizeDiscipline')");">상벌내역 조회</a></li>
					<li class="no_child" id="zhr_pa_007"><a  href="javascript:goPersonal('foreignLanguage')");">외국어점수 조회</a></li>
					<li class="no_child" id="zhr_pa_010"><a  href="javascript:goPersonal('qualification')");">자격사항 조회</a></li>
					<li class="no_child" id="zhr_pa_021"><a href="javascript:goPersonal('personal')");">신상조회</a></li>
					<li class="no_child" id="zhr_pa_003"><a href="javascript:goPersonal('career')");">입사전 경력 조회</a></li>
				</ul>
			</li>
			<li class="liFirst opened licurrent"><a href="#">조직도</a>
				<ul>
					<li class="no_child" id="zhr_pa_044"><a  href="javascript:goOrganogram('organizationChart')">조직도</a></li>
				</ul>
			</li>
		</c:if>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'ehrBusinessSupport'}">
	<ul>
		<li class=""><a href="#">복리후생</a>
			<ul>
				<li class="no_child" id="zhr_pt_004"><a  href="javascript:goPersonal('eduExpense');">학자금 신청/조회</a></li>
				<li class="no_child" id="zhr_be_004"><a  href="javascript:goPersonal('congratulate');">경조금 신청/조회</a></li>
				<c:if test="${user.essAuthCode eq 'E1'||user.essAuthCode eq 'E2'||user.essAuthCode eq 'E3'||user.essAuthCode eq 'E7'}">
				<li class="no_child" id="zhr_pa_042"><a href="javascript:goPersonal('cafeteria')">카페테리아 신청</a></li>
				</c:if>
				<li class="no_child" id="zhr_pt_006"><a  href="javascript:goPersonal('expense');">이사비/부임비/파견비 신청/조회</a></li>

				<li class="no_child" id="zhr_be_003"><a  href="javascript:goPersonal('club');">동호회 신청/조회</a></li>
			</ul>
		</li>
		<c:if test="${user.essAuthCode != 'E9'}">
		<li class=""><a href="#">증명서</a>
			<ul>
				<li class="no_child" id="zhr_be_002"><a  href="javascript:goPersonal('certificate');">제증명서 신청/조회</a></li>
				<li class="no_child" id="zhr_py_007"><a  href="javascript:goPersonal('taxWithholdingView');">근로소득 원천징수영수증조회</a></li>
			</ul>
		</li>
		</c:if>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'ehrManPowerMng'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">인재개발</a>
			<ul>
				<c:if test="${user.essAuthCode ne 'E8' && user.essAuthCode ne 'E9'}">
				<li class="no_child" id="zhr_pe_001"><a href="javascript:goEtc('JIKMU')">직무 프로파일</a></li>
				</c:if>
				<li class="no_child" id="zhr_pe_002"><a href="javascript:goEtc('QUALI')">역량 카탈로그</a></li>
				<c:if test="${user.essAuthCode ne 'E0' && user.essAuthCode ne 'E4' && user.essAuthCode ne 'E9'}">
				<!-- <li class="no_child" id="zhr_pa_036"><a  href="javascript:goManPower('statusPoint');">승격 Point 조회</a></li> -->
				</c:if>
				<c:if test="${user.essAuthCode eq 'E1'||user.essAuthCode eq 'E5'}">
				<li class="no_child" id="zhr_pe_001"><a  href="javascript:goIdp('IDP_SETTING')">IDP 수립</a></li>
				</c:if>
				<c:if test="${user.essAuthCode eq 'E1'}">
					<li class="no_child" id="zhr_pe_001"><a  href="javascript:goPromotion('GOAL_SETTING_LIST')">도전과제등록 </a></li>
				</c:if>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'ehrResultMng'}">
	<c:if test="${A01||A02||A03||A04||A05}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">업적평가</a>
			<ul>
				<c:if test="${A01}">
				<li class="no_child" id="zhr_pe_001"><a  href="javascript:goPerformance('OBJECTIVE_SETTING');">목표수립</a></li>
				</c:if>
				<c:if test="${A02}">
				<li class="no_child" id="zhr_pe_002"><a  href="javascript:goPerformance('OBJECTIVE_AGREEMENT');">목표합의</a></li>
				</c:if>
				<c:if test="${A01}">
				<li class="no_child" id="zhr_pe_003"><a  href="javascript:goPerformance('OBJECTIVE_MIDDLECHECK');">중간점검</a></li>
				</c:if>
				<c:if test="${A02}">
				<li class="no_child" id="zhr_pe_004"><a  href="javascript:goPerformance('OBJECTIVE_FEEDBACK');">중간점검 Feedback</a></li>
				</c:if>
				<c:if test="${A01}">
				<li class="no_child" id="zhr_pe_005"><a  href="javascript:goPerformance('OBJECTIVE_PERFORMANCE');">실적작성</a></li>
				</c:if>
				<c:if test="${A02||A03}">
				<li class="no_child" id="zhr_pe_006"><a  href="javascript:goPerformance('OBJECTIVE_EVALUATION');">평가자 평가</a></li>
				</c:if>
				<c:if test="${A04}">
				<li class="no_child" id="zhr_pe_007"><a  href="javascript:goPerformance('OBJECTIVE_REVIEW');">1차 Review</a></li>
				</c:if>
				<c:if test="${A05}">
				<li class="no_child" id="zhr_pe_008"><a  href="javascript:goPerformance('OBJECTIVE_REVIEW2');">2차 Review</a></li>
				</c:if>
			</ul>
		</li>
	</ul>
	</c:if>
	<c:if test="${A06||A07||A08||A09||A10||user.essAuthCode eq 'E1'||user.essAuthCode eq 'E2'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">역량평가</a>
			<ul>
				<c:if test="${A06}">
				<li class="no_child" id="zhr_pe_001"><a  href="javascript:goCompetence('COMPETENCE_SETUP')">직무역량 선정</a></li>
				</c:if>
				<c:if test="${A07}">
				<li class="no_child" id="zhr_pe_002"><a  href="javascript:goCompetence('COMPETENCE_AGREEMENT')">직무역량 합의</a></li>
				</c:if>
				<c:if test="${A07||A08}">
				<li class="no_child" id="zhr_pe_003"><a  href="javascript:goCompetence('COMPETENCE_EVALUATION')">평가자 평가</a></li>
				</c:if>
				<c:if test="${A09}">
				<li class="no_child" id="zhr_pe_004"><a  href="javascript:goCompetence('COMPETENCE_REVIEW')">1차 Review</a></li>
				</c:if>
				<c:if test="${A10}">
				<li class="no_child" id="zhr_pe_008"><a  href="javascript:goCompetence('COMPETENCE_REVIEW2');">2차 Review</a></li>
				</c:if>
				<c:if test="${user.essAuthCode eq 'E1'||user.essAuthCode eq 'E2'}">
				<li class="no_child" id="zhr_pe_007"><a  href="javascript:goCompetence('COMPETENCE_FEEDBACK_ESS')">역량평가 Feedback</a></li>
				</c:if>
			</ul>
		</li>
	</ul>
	</c:if>
	<c:if test="${A11||A12||user.essAuthCode eq 'E0'||user.essAuthCode eq 'E1'||user.essAuthCode eq 'E5'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">다면진단</a>
			<ul>
				<c:if test="${A12}">
				<li class="no_child" id="zhr_pe_001"><a  href="javascript:goMultiside('MULTISIDE_DIAGNOSIS')">진단</a></li>
				</c:if>
				<c:if test="${A11||user.essAuthCode eq 'E0'||user.essAuthCode eq 'E1'||user.essAuthCode eq 'E5'}">
				<li class="no_child" id="zhr_pe_002"><a  href="javascript:goMultiside('MULTISIDE_FEEDBACK')">Feedback</a></li>
				</c:if>
			</ul>
		</li>
	</ul>
	</c:if>
	<c:if test="${A13||A14}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">기능직평가</a>
			<ul>
				<c:if test="${A13}">
				<li class="no_child" id="zhr_pe_001"><a href="javascript:goTechnical('EVALUATION')">평가자평가</a></li>
				</c:if>
				<c:if test="${A14}">
				<li class="no_child" id="zhr_pe_002"><a href="javascript:goTechnical('REVIEW')">Review</a></li>
				</c:if>
			</ul>
		</li>
	</ul>
	</c:if>
	<c:if test="${A15}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">촉탁/별정직평가</a>
			<ul>
				<li class="no_child" id="zhr_pe_001"><a href="javascript:goPrivilege('EVALUATION')">평가자평가</a></li>
			</ul>
		</li>
	</ul>
	</c:if>
</c:if>
<c:if test="${Bigmenu eq 'ehrDiligenceMng'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">근태출장</a>
			<ul>
				<c:if test="${user.essAuthCode ne 'E0' && user.essAuthCode ne 'E8' && user.essAuthCode ne 'E9'}">
				<li class="no_child licurrent" id="zhr_pt_003"><a  href="javascript:goDiligence('holidayWork');">휴일 근무 신청/조회</a></li>
				</c:if>
				<c:if test="${user.essAuthCode ne 'E9'}">
				<li class="no_child" id="zhr_pt_002"><a href="javascript:goDiligence('holiday');">휴가 신청/조회</a></li>
				<li class="no_child" id="zhr_pt_007"><a  href="javascript:goDiligence('evaluation');">월 근태평가 Feedback</a></li>
				<li class="no_child" id="zhr_pt_005"><a  href="javascript:goDiligence('holidayAmass');">연월차 적치 신청/조회</a></li>
				<li class="no_child" id="zhr_pt_010"><a  href="javascript:goDiligence('yearMonthPaidHoliday')">연월차 내역 조회</a></li>
				<li class="no_child" id="zhr_pa_033"><a  href="javascript:goDiligence('leaveReinstatement');">휴복직 신청/조회</a></li>
				<li class="no_child" id="zhr_pt_008"><a href="javascript:goDiligence('businessTrip');">출장 신청/조회</a></li>
				</c:if>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'ehrPayMng'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">급여</a>
			<ul>
				<c:if test="${user.essAuthCode ne 'E4' && user.essAuthCode ne 'E9'}">
				<li class="no_child licurrent" id="zhr_py_002"><a  href="javascript:goPay('paystub');">임금명세서</a></li>
				<li class="no_child" id="zhr_py_004"><a  href="javascript:goPay('payEarning');">급여실적 조회</a></li>
				</c:if>
				<c:if test="${user.essAuthCode ne 'E0' && user.essAuthCode ne 'E4' && user.essAuthCode ne 'E9' }">
				<li class="no_child" id="zhr_py_003"><a  href="javascript:goPay('yearEndSettlement')">연말정산 소득공제 신청</a></li>
				<li class="no_child" id="zhr_py_006"><a  href="javascript:goPay('settlementDocument');">소득공제 신고서 조회</a></li>
				<li class="no_child" id="zhr_py_001"><a  href="javascript:goPay('loan');">대출내역 조회</a></li>
				</c:if>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'ehrPayDiligenceMng'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">급여/근태</a>
			<ul>
				<c:if test="${user.essAuthCode == 'E9'}">
				<li class="no_child licurrent" id="zhr_py_002"><a  href="javascript:goPay('paystub');">개인임금명세서 조회</a></li>
				<li class="no_child" id="zhr_py_004"><a  href="javascript:goPay('payEarning');">급여실적 조회</a></li>
				<li class="no_child" id="zhr_pt_011"><a  href="javascript:goDiligence('evaluation');">월 근태현황 조회</a></li>
				<li class="no_child" id="zhr_pt_010"><a  href="javascript:goDiligence('yearMonthPaidHoliday')">연월차 내역 조회</a></li>
				<li class="no_child" id="zhr_py_003"><a  href="javascript:goPay('yearEndSettlement')">연말정산 소득공제 신청</a></li>
				<li class="no_child" id="zhr_py_006"><a  href="javascript:goPay('settlementDocument');">소득공제 신고서 조회</a></li>
				<li class="no_child" id="zhr_py_007"><a  href="javascript:goPersonal('taxWithholdingView');">근로소득 원천징수영수증<br/>조회</a></li>
				</c:if>
			</ul>
		</li>
	</ul>
</c:if>
<c:if test="${Bigmenu eq 'ehrTeamMng'}">
	<c:if test="${user.mssAuthCode == 'M1'||user.mssAuthCode == 'M2'||user.mssAuthCode == 'M3'||user.mssAuthCode == 'M4'||user.mssAuthCode == 'M5'||user.mssAuthCode == 'M6'||user.mssAuthCode == 'M7'||user.mssAuthCode == 'M8'||user.mssAuthCode == 'M9'||user.mssAuthCode == 'M10'}">
	<ul>
		<li class="liFirst opened licurrent"  ><a href="#">인사관리</a>
			<ul>
				<li class="no_child" id="personalInfo"><a  href="javascript:mssWebLink('personalInfo');">인사기본조회</a></li>
			</ul>
		</li>
	</ul>
	</c:if>
	<c:if test="${user.mssAuthCode == 'M1'||user.mssAuthCode == 'M3'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">팀원포지션변경</a>
			<ul>
				<li class="no_child" id="zhr_pe_001"><a href="javascript:goPosition()">팀원포지션변경</a></li>
			</ul>
		</li>
	</ul>
	</c:if>
	<c:if test="${user.essAuthCode == 'E0'||user.mssAuthCode == 'M1'||user.mssAuthCode == 'M2'||user.mssAuthCode == 'M3'||user.mssAuthCode == 'M4'||user.mssAuthCode == 'M7'||user.mssAuthCode == 'M8'||user.mssAuthCode == 'M9'||user.mssAuthCode == 'M10'}">
	<ul>
		<li class=""><a href="#">성과관리</a>
			<ul>
				<c:if test="${user.mssAuthCode == 'M1'||user.mssAuthCode == 'M2'||user.mssAuthCode == 'M3'||user.mssAuthCode == 'M4'||user.mssAuthCode == 'M7'||user.mssAuthCode == 'M8'||user.mssAuthCode == 'M9'||user.mssAuthCode == 'M10'}">
				<li class="no_child" id="evaluationList"><a href="javascript:hideTeamView(false);mssWebLink('evaluationList')">평가이력조회</a></li>
				</c:if>
				<c:if test="${user.essAuthCode == 'E0'||user.mssAuthCode == 'M1'||user.mssAuthCode == 'M3'}">
				<li class="no_child" id="zhr_pe_006"><a  href="javascript:goCompetence('COMPETENCE_FEEDBACK_MSS')">구성원 역량평가 Feedback</a></li>
				</c:if>
				<c:if test="${user.mssAuthCode == 'M1'||user.mssAuthCode == 'M2'||user.mssAuthCode == 'M3'||user.mssAuthCode == 'M4'}">
				<li class="no_child" id="zhr_pe_002"><a  href="javascript:goIdp('IDP_AGREEMENT')">IDP 합의</a></li>
				</c:if>
			</ul>
		</li>
	</ul>
	</c:if>
	<c:if test="${user.mssAuthCode == 'M1'||user.mssAuthCode == 'M2'||user.mssAuthCode == 'M3'||user.mssAuthCode == 'M4'||user.mssAuthCode eq 'M7'||user.mssAuthCode eq 'M8'||user.mssAuthCode eq 'M9'||user.mssAuthCode eq 'M10'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">노무관리</a>
			<ul>
				<c:if test="${user.mssAuthCode eq 'M7'||user.mssAuthCode eq 'M8'||user.mssAuthCode eq 'M9'||user.mssAuthCode eq 'M10'}">
				<li class="no_child" id="laborPosition"><a  href="javascript:mssWebLink('laborPosition');">노조직책자 현황 조회</a></li>
				</c:if>
				<c:if test="${user.mssAuthCode == 'M1'||user.mssAuthCode == 'M2'||user.mssAuthCode == 'M3'||user.mssAuthCode == 'M4'||user.mssAuthCode eq 'M7'||user.mssAuthCode eq 'M8'||user.mssAuthCode eq 'M9'||user.mssAuthCode eq 'M10'}">
				<li class="no_child" id="laborInterview"><a  href="javascript:mssWebLink('laborInterview');">노무관리 면담일지</a></li>
				</c:if>
			</ul>
		</li>
	</ul>
	</c:if>
	<c:if test="${user.mssAuthCode == 'M1'||user.mssAuthCode == 'M2'||user.mssAuthCode == 'M3'||user.mssAuthCode == 'M4'||user.mssAuthCode == 'M5'||user.mssAuthCode == 'M6'||user.mssAuthCode == 'M8'||user.mssAuthCode == 'M9'||user.mssAuthCode == 'M10'}">
	<ul>
		<li class="liFirst opened licurrent"><a href="#">근태/급여</a>
			<ul>
				<c:if test="${user.mssAuthCode == 'M1'||user.mssAuthCode == 'M2'||user.mssAuthCode == 'M3'||user.mssAuthCode == 'M4'||user.mssAuthCode == 'M5'||user.mssAuthCode == 'M6'||user.mssAuthCode == 'M8'||user.mssAuthCode == 'M9'||user.mssAuthCode == 'M10'}">
				<li class="no_child" id="payEarning"><a  href="javascript:mssWebLink('payEarning');">급여실적 조회</a></li>
				<li class="no_child" id="monthDilegence"><a href="javascript:mssWebLink('monthDilegence')">월근태현황 조회</a></li>
				<li class="no_child" id="yearMonthPaidHoliday"><a href="javascript:mssWebLink('yearMonthPaidHoliday')">연월차내역 조회</a></li>
				<li class="no_child" id="holidayUse"><a href="javascript:mssWebLink('holidayUse')">휴가사용현황 조회</a></li>
				</c:if>
				<c:if test="${user.mssAuthCode eq 'M3'||user.mssAuthCode eq 'M4'||user.mssAuthCode eq 'M5'||user.mssAuthCode eq 'M6'}">
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
				<c:if test="${user.mssAuthCode eq 'M1'||user.mssAuthCode eq 'M2'||user.mssAuthCode eq 'M3'||user.mssAuthCode eq 'M4'||user.mssAuthCode eq 'M5'||user.mssAuthCode eq 'M6'||user.mssAuthCode eq 'M8'||user.mssAuthCode eq 'M9'||user.mssAuthCode eq 'M10'}">
				<li class="no_child" id="workHistory"><a href="javascript:mssWebLink('workHistory');">근무내역서</a></li>
				</c:if>
				<c:if test="${user.mssAuthCode eq 'M3'||user.mssAuthCode eq 'M4'||user.mssAuthCode eq 'M5'||user.mssAuthCode eq 'M6'}">
				<li class="no_child" id="monthDiligenceReport"><a href="javascript:mssWebLink('monthDiligenceReport');">월근태보고서 조회</a></li>
				<li class="no_child" id="overtimeWorkTotal"><a  href="javascript:mssWebLink('overtimeWorkTotal');">연장근로 집계표 조회</a></li>
				</c:if>

			</ul>
		</li>
	</ul>
	</c:if>
</c:if>
<c:if test="${Bigmenu eq 'ehrHrMng'}">
	<c:if test="${H0}">
	<ul>
	 	<li class="liFirst opened licurrent"><a href="#">HR결재</a>
			<ul>
				<li class="no_child" id="hrApproval"><a href="javascript:hassWebLink('hrApproval')">HR결재</a></li>
			</ul>
		</li>
	</ul>
	<ul>
	 	<li class="liFirst opened licurrent"><a href="#">평가관리</a>
			<ul>
				<li class="no_child" id="zhr_pe_008"><a  href="javascript:goPerformance('OBJECTIVE_HSS');">업적평가 평가표조회</a></li>
				<li class="no_child" id="zhr_pe_005"><a  href="javascript:goCompetence('COMPETENCE_HSS')">역량평가 평가표조회</a></li>
				<li class="no_child" id="zhr_pe_003"><a href="javascript:goTechnical('HSS')">기능직평가 평가표조회</a></li>
				<li class="no_child" id="zhr_pe_002"><a  href="javascript:goPrivilege('HSS')">촉탁/별정직평가 평가표조회</a></li>
			</ul>
		</li>
	</ul>
	<ul>
		<li class="liFirst opened licurrent"><a href="#">IDP</a>
			<ul>
				<li class="no_child" id="zhr_pe_003"><a  href="javascript:goIdp('IDP_HSS')">IDP조회</a></li>
			</ul>
		</li>
	</ul>
	</c:if>
</c:if>
<c:if test="${Bigmenu eq 'ehrTeamMng'}">

<div height="20px">&nbsp;</div>

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
</div>