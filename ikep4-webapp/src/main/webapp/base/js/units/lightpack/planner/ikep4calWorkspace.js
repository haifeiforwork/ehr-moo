$jq(document).ready(function() {
	var fc = $jq.fullCalendar;
	var formatDate = fc.formatDate;
	var parseISO8601 = fc.parseISO8601;
	var addDay = fc.addDays;
	var applyAll = fc.applyAll;
	var updateLeftTitle = fc.updateLeftTitle;
	var cloneDate = fc.cloneDate;
	var addMinutes = fc.addMinutes;
	var clearTime = fc.clearTime;
	var monthHeight;
	
	var tviewName = "";
	var tworkspaceId = "";
	var today = iKEP.getCurTime();
	
	var calElement = $jq("#calendar");
	var leftMenu = $jq("#leftMenu-pane");

	var home_url = iKEP.getContextRoot() + "/";	
	var getInitDataUrl = home_url + "lightpack/planner/calendar/getInitData.do";
	var viewUrl = home_url + "lightpack/planner/calendar/viewSchedule.do?dialog=1&";
	
	var iconInfo = {
			mySchedule: {iconName: "ic_planner_my", title: iKEPLang.planner.titleText.mySchedule},
			mandatorSchedule: {iconName: "ic_planner_entrust", title: iKEPLang.planner.titleText.mandatorSchedule},
			teamSchedule: {iconName: "ic_planner_workspace", title: iKEPLang.planner.titleText.teamSchedule},
			userSchedule: {iconName: "ic_planner_other", title: iKEPLang.planner.titleText.userSchedule}
		};

	var calOption = {
		year : today.getFullYear(),
		month : today.getMonth(),
		date : today.getDate(),
			header: {
				left: 'userTitle,today',
				center: 'prev,title,next',
				right: 'month,agendaWeek,agendaDay,listMonth'
			},
			buttonText: iKEPLang.planner.buttonText,			
			titleFormat: iKEPLang.planner.titleFormat,
			columnFormat: iKEPLang.planner.columnFormat,
			timeFormat: { // for event elements
				'': 'HH:mm', // default
				agenda: 'HH:mm{ - HH:mm}'
			},
			defaultView: "month",
			axisFormat: 'HH:mm',
			allDayDefault: true,
			selectable: false,
			selectHelper: false,
			lazyFetching: true,
			editable: false,
			weekMode: "liquid",
			// locale
			monthNames: iKEPLang.datepicker.lblMonthNames,
			monthNamesShort: iKEPLang.datepicker.lblMonthNamesShort, 
			dayNames: iKEPLang.datepicker.lblDayNames,
			dayNamesShort: iKEPLang.datepicker.lblDayNamesShort,
			//callback
			eventRender: function(event, element, view) {
				if(event.source.name == "holiday") {					
					$jq(view.element).find('div.fc-day-number').each(function(idx, ele) {
						if($jq(this).html() == event.start.getDate()) {
							$jq(ele).addClass("fc-sun");
						}
					});
				} else {
					$jq(element).attr("title", event.title);
				}
				tviewName = view.name;
				
				boxColorChange();
			},
			eventClick: function(event, jsEvent, view) {
				if(event.source.name == 'holiday') {
					return false;
				}
				if(event.viewable) {					
					viewEvent(event);
				}
				tviewName = view.name;
				
				boxColorChange();
			},			
			loading: function(isLoading, view) {
				
				boxColorChange();
			},
			viewDisplay: function(view) {
				
				if(!monthHeight) {
					monthHeight = $jq("#calendar").fullCalendar("option").height();
					
				} else {					
					if(view.name === "month" || view.name === "listMonth") {
						$jq('#calendar').fullCalendar('option', 'contentHeight', monthHeight);
					} else {
						$jq('#calendar').fullCalendar('option', 'contentHeight', 3000);
						$jq(".fc-event-head").attr("style","background-color:white;");
						$jq(".fc-event-inner").attr("style","background-color:white;color:black;border-color:black;");
					}
				}	
				
			}
		};
	
	var cal;
	var ikepPlanner;
	var owner;
	
	var eventSources = {
		holiday: {
			url:"calFeedHoliday.do", 
			editable: false, 
			name: "holiday", 
			allDayDefault: true,
			color: 'white', 
			textColor: 'red'
		},
		workspace: {
			url: "calFeedWorkspaceSchedule.do",
			name: "workspace", 
			editable: false, 
			success: function() {
	    		cal.fullCalendar("updateLeftTitle", this.workspaceName,
	    				{iconName: "ic_planner_workspace", title: iKEPLang.planner.titleText.teamSchedule});
			}
		}
	};
	
	var currentSource = null;
	
	var eventMgmt;

	function init() {
		// 기본 데이터 읽기
		$jq.getJSON(getInitDataUrl, function(data){
			ikepPlanner = $jq.extend({}, data);

			$jq.ikepPlanner = ikepPlanner;
			calElement.data("ikepPlanner", ikepPlanner);
			owner = ikepPlanner.userInfo;

			var urlParameters = $jq.getUrlVars();
			calElement.trigger("onClickWorkspace", {
				workspaceId: urlParameters.workspaceId,
				workspaceName: urlParameters.workspaceName
			});
		});
		
		
		// bind trigger
		calElement
		.bind("onClickWorkspace", function(e, data) {
			
			var hbNameParameters = data.hbName || '';
			eventSources.workspace.hbName = hbNameParameters;
			var workspaceIdParameters = data.workspaceId || '';
			if(workspaceIdParameters != ""){
				tworkspaceId = workspaceIdParameters;
			}else{
				workspaceIdParameters = tworkspaceId;
			}
			eventSources.workspace.data = {workspaceId: workspaceIdParameters,hbName: hbNameParameters};
			eventSources.workspace.workspaceName = data.workspaceName;
			cal.fullCalendar("removeEventSource");
			cal.fullCalendar("addEventSource", eventSources.workspace);
			cal.fullCalendar("addEventSource", eventSources.holiday);
			currentSource = {source: "workspace", workspaceId: workspaceIdParameters,hbName:hbNameParameters};
			changeDefaultView("month");
		});
	}
	
	function changeDefaultView() {
		if($jq(cal).css("display") === "none") { // 조회/수정 화면의 경우
			eventMgmt.cancel();
		}
		if(cal.fullCalendar("getView") != calOption.defaultView ) {
			cal.fullCalendar("changeView", calOption.defaultView);
		}
		scrollTop();
	}
	
	function scrollTop() {
		$jq("html, body").scrollTop(0);
	}
	
	function getUserInfoText(uinfo) {
		var localeSame = ikepPlanner.userInfo.localeCode == ikepPlanner.portalInfo.defaultLocaleCode;
	
		return '<strong>' + (localeSame ? uinfo.userName : uinfo.userEnglishName) + '</strong> ' 
			+ ( (localeSame ? uinfo.jobTitleName : uinfo.jobTitleEnglishName) || "" ) + " "
			+ ( (localeSame ? uinfo.teamName : uinfo.teamEnglishName) || "" );
	}
	
	function viewEvent(event) {
		var sch = event.sco || {};
		var end;		
	
		var params = {
			scheduleId: event.id,
			start: event.start.getTime(),
			end: event.end.getTime()
		};
		var url = viewUrl + $jq.param(params);
		//parent.parent.showMainFrameDialog(url, event.title, 800, 500);
		var options = {
			width:700,
			height:340,
			resizable:true,
			argument : {startDate:event.start.toString(), endDate:event.end.toString()},
			callback : function(result) {
				if(result && result["action"]) {
					switch(result.action) {
						case "update" :
							parent.iKEP.goScheduleUpdate({scheduleId:event.id}, null);
							break;
						case "delete" :
							calElement.fullCalendar("refetchEvents");
							break;
					}
				}
			}
		};
		iKEP.popupOpen(url, options);
	}
	
	function boxColorChange(){
		if(tviewName == "agendaWeek" || tviewName == "agendaDay"){
			$jq(".fc-event-head").attr("style","background-color:white;");
			$jq(".fc-event-inner").attr("style","background-color:white;color:black;border-color:black;");
		}
	}
	
	cal = calElement.fullCalendar(calOption);
	// main
	init();
	
	$jq("input:radio[name='hRadio2']").click(function() {
		calElement.trigger("onClickWorkspace" , {
			hbName: $jq(this).val()
		});
	});
});