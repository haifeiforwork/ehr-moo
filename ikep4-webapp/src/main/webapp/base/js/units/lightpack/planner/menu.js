(function(){		
	$jq(document).ready(function() {	
		var urlParameters = {};
		var _action;
		
		var home_url = iKEP.getContextRoot() + "/";	
		var calendarUrl = home_url + "lightpack/planner/calendar/init.do?";
		var findUserByNameUrl = home_url + "lightpack/planner/calendar/findUserByName.do";
		var getInitDataUrl = home_url + "lightpack/planner/calendar/getInitData.do";
		var myWorkspaceUrl = home_url + "collpack/collaboration/workspace/myWorkspaceMenu.do?userId=";
		var personIconUrl = home_url + "base/images/icon/ic_person.gif";

		// element var
		var ikepPlanner = null;
		var calElement = $jq("#calendar");
		
		var $leftmenu_datepicker = $jq("#leftmenu-datepicker");
		var $mandate_menu = $jq("#mandate-menu");
		var $category_menu = $jq("#category-menu");
		var $holiday_menu = $jq("#holiday-menu");
		var $teamMenuList = $jq("#teamMenuList");
		var $myCalendar	= $jq("#myCalendar");
		var $companyCalendar = $jq("#companyCalendar");
		var $companyCalendarMgt= $jq("#companyCalendarMgt");
		var $companyCalendarDownMgt= $jq("#companyCalendarDownMgt");
		
		function init() {
			//alert("menu.js-init");
			if(ikepPlanner == null) { // body가 calendar가 아닌 경우				
				ikepPlanner = calElement.data("ikepPlanner");
			}
			// url parameter 읽기
			//urlParameters = iKEP.getUrlArguments() || {};//$jq.getUrlVars();
			//_action = urlParameters["action"] || "onClickMyCalendar";
			
			urlParameters = iKEP.getUrlArguments() || {workAreaName:''};//무림제지 전사 일정을 기본으로 함. 2012.09.05
			_action = urlParameters["action"] || "onClickCompanyCalendar";//무림제지 전사 일정을 기본으로 함. 2012.09.05


			if(_action != "onDirectUpdate") calElement.show();
			
			// 팀 목록 가져오기
			$teamPane = $jq("#team-pane");
			
			if(ikepPlanner.userInfo.userId == "hyun9313" || ikepPlanner.userInfo.userId == "softpower" || ikepPlanner.userInfo.userId == "qo0319" || ikepPlanner.userInfo.userId == "lak511"){
				var tp = $jq('<ul></ul>').appendTo("#team-pane");
				var liEl = $jq('<li class="no_child"><a href="#a" class="teamWorkspace" title="국내영업1팀">국내영업1팀</a></li>')
				.click( function(){
					var params = {
							workspaceId: "100000849143",
							workspaceName: "국내영업1팀"
					};
					triggerHandler("onClickWorkspace", params, calendarUrl);
					//setLicurrent(liEl);
				})
				.appendTo(tp);
			}else{
				if($teamPane.is("*")) {
					$jq.getJSON(myWorkspaceUrl + ikepPlanner.userInfo.userId, function(data){
						if(data.length > 0) {
							var tp = $jq('<ul></ul>').appendTo("#team-pane");
							$jq.each(data, function(idx, item) {
								var liEl = $jq('<li class="no_child"><a href="#a" class="teamWorkspace" title="' + item.workspaceName + '">' + item.workspaceName + '</a></li>')
								.data("workspace", item)
								.click( function(){
									var params = {
											workspaceId: item.workspaceId,
											workspaceName: item.workspaceName
									};
									triggerHandler("onClickWorkspace", params, calendarUrl);
									//setLicurrent(liEl);
								})
								.appendTo(tp);
								if(urlParameters["workspaceId"] == item.workspaceId) {
									//setLicurrent(liEl);
								}
							});
						}
					});
				}
			}
			
			renderMandators();
			
			// 나의 일정
			$myCalendar.click(function() {
				triggerHandler("onClickMyCalendar", {}, calendarUrl);
				setLicurrent("#myCalendar");
			});
			
			//전사일정
			$companyCalendar.click(function() {
				triggerHandler("onClickCompanyCalendar", {workAreaName:''}, calendarUrl);
				setLicurrent("#companyCalendar");
			});
			
			//전사일정 관리
			$companyCalendarMgt.click(function(){
				document.location.href = home_url + "lightpack/planner/calendar/companyScheduleList.do";
			});
			
			$companyCalendarDownMgt.click(function(){
				document.location.href = home_url + "lightpack/planner/calendar/companyScheduleExcelList.do";
			});
			
			
			// date picker
			$leftmenu_datepicker.datepicker({
				gotoCurrent: true,
				onSelect: function(dateText, inst) {
					var params = {
							currentYear: inst.currentYear, 
							currentMonth: inst.currentMonth, 
							currentDay: inst.currentDay
						};
					triggerHandler("onClickDatepicker", params, calendarUrl);
				}
			});
			
			// 사용자 검색 및 일정 보기
			$jq("#targetUserInfo").keypress(function(event, where) {
				var that = this;
				var userName = this.value;
				if(event.keyCode == 13 && userName.length > 0) {
					$jq.getJSON(findUserByNameUrl, {userName: userName}, function(data) {
						if(data.count == 1 && data.userId) {
							var params = {userId: data.userId};
							triggerHandler("onClickTargetUser", params, calendarUrl);
							that.value = "";
						} else {
							searchUser(event);
						}
					});
				} else if(event.keyCode == 13 || where == "targetUserInfoSearch") {
					searchUser(event);
				}
			});
			
			// title link
			$jq("#leftMenu h2").addClass("ilink").click(function() {
				document.location.href = home_url + "lightpack/planner/calendar/init.do";
			});
			
			$jq("#targetUserInfoSearch").bind("click", function() {
				$jq("#targetUserInfo").trigger("keypress", "targetUserInfoSearch");	
			});
			
			// 위임 메뉴
			$mandate_menu.click(function() {
				document.location.href = home_url + "lightpack/planner/mandate/formView.do";
			});
			
			// 범주 메뉴
			$category_menu.click(function(){
				document.location.href = home_url + "lightpack/planner/category/list.do";
			});
			
			// 휴일 메뉴
			$holiday_menu.click(function(){
				document.location.href = home_url + "lightpack/planner/holiday/list.do";
			});
			
			// 모바일 팀 메뉴
			$teamMenuList.click(function(){
				document.location.href = home_url + "lightpack/planner/team/getPlannerTeamMenuList.do";
			});
			
			$jq("a.favoriteUser").click(function(event) {
				var location = $jq(this).attr("href");
				var userId = location.substring(1, location.length);
				triggerHandler("onClickTargetUser", {userId:userId}, calendarUrl);
				
				return false;
			});
			
			$jq("a.favoriteTeam").click(function(event) {
				var workspaceId = $jq(this).attr("href");
				triggerHandler("onClickWorkspace", {workspaceId:workspaceId}, calendarUrl);
				
				return false;
			});
			
			setMenu();
			iKEP.setLeftMenu();
			$jq('#targetUserInfo').hintField();
		}
		
		function searchUser(event) {
			iKEP.searchUser(event, "Y", setUser);
			/*iKEP.searchUser(event, "N", function( data ) {
				if(data && data.length > 0) {
					triggerHandler("onClickTargetUser", {userId:data[0].id}, calendarUrl);
				} else {
					alert(iKEPLang.planner.messageText.noUser);
				}
			});*/
		}
		
		setUser = function(data) {
			if(data && data.length > 0) {
				triggerHandler("onClickTargetUser", {userId:data[0].id}, calendarUrl);
			} else {
				alert(iKEPLang.planner.messageText.noUser);
			}
		};
		
		function renderMandators() {
			var data = ikepPlanner.mandatorList;
			if(data.length > 0) {				
				$jq.each(data, function(idx, item) {
					var liEl = 
						$jq('<li class="no_child"><a href="#a"><img src="' + personIconUrl +  '" alt="" />&nbsp;' +
						(ikepPlanner.userInfo.localeCode == ikepPlanner.portalInfo.defaultLocaleCode ? 
						item.userName + " " + item.jobTitleName :item.userEnglishName + " " + item.jobTitleEnglishName) + 
						'</a></li>')
					.click( function(){
						var params = {
								mandatorId: item.mandatorId
						};
						triggerHandler("onClickMandator", params, calendarUrl);
						setLicurrent(liEl);
					})
					.appendTo("#myCal-pane");
					if(urlParameters["mandatorId"] == item.mandatorId) {
						setLicurrent(liEl);
					}
				});
			}
		}
		
		function triggerHandler(triggerName, data, url) {
			data.action = triggerName;

			if(calElement.length > 0) {
				calElement.trigger(triggerName, data);
			} else {
				document.location.href = url + $jq.param(data);
			}
		}
		
		function setMenu(memu, item) {
			if(window.location.href.indexOf("mandate") > 0 ) {
				setLicurrent("#mandate-menu");
			} else if (window.location.href.indexOf("category") > 0 ) {
				setLicurrent("#category-menu");
			} else if (window.location.href.indexOf("holiday") > 0 ) {
				setLicurrent("#holiday-menu");
			} else if (window.location.href.indexOf("companyScheduleList") > 0 ) {
				setLicurrent("#companyCalendarMgt");
			} else if (window.location.href.indexOf("companyScheduleExcelList") > 0 ) {
				setLicurrent("#companyCalendarDownMgt");
			} else if (window.location.href.indexOf("calFeedMySchedule") > 0 ) {
				setLicurrent("#myCalendar");
			}
			
		}
		
		function clearCurrent() {
			$jq("#leftMenu-pane li").removeClass("licurrent");
		}
		
		function _setMenu(menu, item) {
			clearCurrent();
			$jq("#" + menu , "#leftMenu").addClass("licurrent");
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
		
		if($jq("#calendar").size() === 0) { // body가 calendar가 아닌 경우(ex 위임관리등 관리자 메뉴)
			// 기본 데이터 읽기
			$jq.getJSON(getInitDataUrl, function(data){
				ikepPlanner = $jq.extend({}, data);
				//alert("menu.js-calendar size 0");
				init();
			});
		}
		
		// ikep4cal에서 trigger
		calElement.bind("onCompleteInit", function() {
			//alert("menu.js-onCompleteInit");
			init();
			triggerHandler(_action, urlParameters, calendarUrl);
		});
		
	});	
})();