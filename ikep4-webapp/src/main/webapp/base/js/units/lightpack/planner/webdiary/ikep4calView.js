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
	
	var today = iKEP.getCurTime();
	
	var calElement = $jq("#calendar");
	//var leftMenu = $jq("#leftMenu-pane");
	var leftMenu = $jq("#planArea");

	var home_url = iKEP.getContextRoot() + "/";	
	var baseUrl = home_url + "lightpack/planner/calendar/"; //2014.09.02 WebDiary에서 일정 등록 하기 위해 추가
	var calendarUrl = home_url + "lightpack/planner/calendar/init.do?";
	var getInitDataUrl = home_url + "lightpack/planner/calendar/getWebDiaryInitData.do";
	var viewUrl = home_url + "lightpack/planner/calendar/viewSchedule.do?dialog=1&";
	
	var iconInfo = {
			companySchedule: {iconName: "ic_planner_company", title: iKEPLang.planner.titleText.companySchedule}, //무림제지 전사일정 기능 추가 2012.09.05
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
			//left: 'userTitle,today,company',
			left: '',
			center: 'prev,title,next',
			//right: 'month,agendaWeek,agendaDay,listMonth'
			right: ''
		},
		buttonText: iKEPLang.planner.buttonText,			
		titleFormat: iKEPLang.planner.titleFormat,
		columnFormat: iKEPLang.planner.columnFormat,
		timeFormat: { // for event elements
			'': 'HH:mm', // default
			agenda: 'HH:mm{ - HH:mm}'
		},
		minTime:'7:00',
		maxTime:'24:00',
		//defaultView: "agendaWeek",
		defaultView: "agendaDay",
		axisFormat: 'HH:mm',
		allDayDefault: true,
		selectable: true,
		selectHelper: true,
		lazyFetching: true,
		editable: true,
		weekMode: "liquid",
		// locale
		monthNames: iKEPLang.datepicker.lblMonthNames,
		monthNamesShort: iKEPLang.datepicker.lblMonthNamesShort, 
		dayNames: iKEPLang.datepicker.lblDayNames,
		dayNamesShort: iKEPLang.datepicker.lblDayNamesShort,
		//callback
		dayClick: function(date, allDay, jsEvent, view) {
			if(view.name == "listMonth") {
				editForm(date, date, allDay, view); 
			}
		},
		select: function(start, end, allDay) {
			editForm(start, end, allDay); 
		},
		eventDrop: function(event, dayDelta, minuteDelta, allDay, revertFunc, jsEvent, ui, view) {
			eventTimeChange("move", event, dayDelta, minuteDelta, allDay, revertFunc, jsEvent);
		},
		eventResize: function(event, dayDelta, minuteDelta, revertFunc, jsEvent, ui, view) {
			eventTimeChange("resize", event, dayDelta, minuteDelta, null, revertFunc, jsEvent);
		},
		eventRender: function(event, element, view) {
			//alert("editable:"+event.editable +"\n"+ "resizable:"+event.resizable +"\n"+ "draggable:"+event.draggable );
			if(event.source.name == "holiday") {					
				$jq(view.element).find('div.fc-day-number').each(function(idx, ele) {
					var date = parseInt($jq(this).html(), 10);
					var month = view.start.getMonth();	// 현재 월
					if($jq(this).parent().parent().hasClass("fc-other-month")) {	// 현재 월이 아니면...
						switch(true) {
							case date > 28-7 : month = month == 0 ? 11 : month-1; break;	// 전월
							case date < 0+7 : month = month == 11 ? 0 : month+1; break;	// 다음월
						}
					}

					if(month == event.start.getMonth() && date == event.start.getDate()) {
						$jq(ele).addClass("fc-sun");
						return false;
					}
				});
			} else {
				
				var startDateStr = (""+event.start).split(" ")[0];
				var endDateStr = (""+event.end).split(" ")[0];
				var startTimeStr = ((""+event.start).split(" ")[1]).split(":")[0]+":"+((""+event.start).split(" ")[1]).split(":")[1];
				var endTimeStr = ((""+event.end).split(" ")[1]).split(":")[0]+":"+((""+event.end).split(" ")[1]).split(":")[1];
				if(endTimeStr=="00:00"){
					endTimeStr = "24:00";
				}
				
				var titleStr ="";
				if(startDateStr!=endDateStr){
					titleStr = event.start.getFullYear()+"."+(event.start.getMonth()+1)+"."+event.start.getDate()+" "+startTimeStr
					+" ~ "+
					event.end.getFullYear()+"."+(event.end.getMonth()+1)+"."+event.end.getDate()+" "+endTimeStr;
				}else{
					titleStr = startTimeStr+" ~ "+endTimeStr;
				}
				
				/*
				if(event.source.name == "companyCalendar" ){

					$jq(element).qtip({
					      content: {
					         text: titleStr+"<br>"+event.title+"<br>"+event.sco.place
					      },
					      position: {
			                  corner: {
			                     tooltip: 'bottomMiddle', // Use the corner...
			                     target: 'topLeft' // ...and opposite corner
			                  }
			               },
			               style: {
			                   border: {
			                      width: 2,
			                      radius: 5
			                   },
			                   padding: 2, 
			                   //textAlign: 'center',
			                   tip: true, // Give it a speech bubble tip with automatic corner detection
			                   name: 'blue'// Style it according to the preset 'cream' style
			               }
					 });
				}else{
					$jq(element).attr("title", titleStr+"\n"+event.title+"\n"+event.sco.place);
				}
				*/
				$jq(element).attr("title", titleStr+"\n"+event.title+"\n"+event.sco.place);
			}
		},
		eventClick: function(event, jsEvent, view) {
			if(event.source.name == 'holiday') {
				return false;
			}
			if(event.viewable) {					
				viewEvent(event);
			}
		},			
		loading: function(isLoading, view) {
		},
		viewDisplay: function(view) {
			// 월별 로딩시 휴일로 설정된 날짜의 스타일을 초기화 하기 위해 추가함
			$jq(view.element).find("div.fc-sun").removeClass("fc-sun");
			
			if(!monthHeight) {
				monthHeight = $jq("#calendar").fullCalendar("option").height();
			} else {					
				if(view.name === "month" || view.name === "listMonth") {
					$jq('#calendar').fullCalendar('option', 'contentHeight', monthHeight);
				} else {
					$jq('#calendar').fullCalendar('option', 'contentHeight', 3000);
				}
			}
		}
	};

	var cal;
	var owner;
	
	var eventSources = {
			myCalendar: {
				url: baseUrl+"calFeedMySchedule.do",
				name: "myCalendar",
				editable: true,
				success: function(uinfo) {
					//alert(uinfo.userName);
					cal.fullCalendar("updateLeftTitle", getUserInfoText(uinfo), iconInfo["mySchedule"]);
				}
			},
			workspace: {
				url: baseUrl+"calFeedWorkspaceSchedule.do",
				name: "workspace", 
				success: function(uinfo) {
					//alert(uinfo.workspaceName);
					/*
					var liEl = $jq(".teamWorkspace[title='"+uinfo.workspaceName+"']");

					$jq("#leftMenu-pane li").removeClass("licurrent");
					//alert($jq(liEl).attr("title"));
					$jq(liEl).parent().addClass("licurrent");
					$jq(liEl).parents("li.opened", "#leftMenu-pane").addClass("licurrent");
					*/
		    		cal.fullCalendar("updateLeftTitle", uinfo.workspaceName,
		    				{iconName: "ic_planner_workspace", title: iKEPLang.planner.titleText.teamSchedule});
				}
			}
		/*
		companyCalendar: { //무림제지 전사일정 기능 추가 2012.09.05
			url: baseUrl+"calFeedCompanySchedule.do",
			disableDragging: false,
			disableResizing: false,
			name: "companyCalendar",
			success: function(uinfo) {
				cal.fullCalendar("updateLeftTitle", getCompanyInfoText(this.workAreaName), iconInfo["companySchedule"], this.workAreaName);
			}
		},
		myCalendar: {
			url: baseUrl+"calFeedMySchedule.do",
			name: "myCalendar",
			editable: true,
			success: function(uinfo) {
				//alert(uinfo.userName);
				cal.fullCalendar("updateLeftTitle", getUserInfoText(uinfo), iconInfo["mySchedule"]);
			}
		},
		holiday: {
			url:"calFeedHoliday.do", 
			editable: false, 
			name: "holiday", 
			allDayDefault: true,
			color: 'white', 
			textColor: 'red'
		},
		targetUser: {
			url: "calFeedTargetUserSchedule.do",
			name: "targetUser", 
			success: function(uinfo) {
				cal.fullCalendar("updateLeftTitle", getUserInfoText(uinfo), iconInfo["userSchedule"]);
			}
		},
		workspace: {
			url: "calFeedWorkspaceSchedule.do",
			name: "workspace", 
			success: function(uinfo) {
				//alert(uinfo.workspaceName);
				var liEl = $jq(".teamWorkspace[title='"+uinfo.workspaceName+"']");

				$jq("#leftMenu-pane li").removeClass("licurrent");
				//alert($jq(liEl).attr("title"));
				$jq(liEl).parent().addClass("licurrent");
				$jq(liEl).parents("li.opened", "#leftMenu-pane").addClass("licurrent");
				
	    		cal.fullCalendar("updateLeftTitle", uinfo.workspaceName,
	    				{iconName: "ic_planner_workspace", title: iKEPLang.planner.titleText.teamSchedule});
			}
		},
		mandator: {
			url: "calFeedByTrustee.do",
			editable: true, 
			name: "mandator", 
			success: function(uinfo) {
				cal.fullCalendar("updateLeftTitle", getUserInfoText(uinfo), iconInfo["mandatorSchedule"]);
			}
		}
		*/
	};
	
	var currentSource = null;
	
	var eventMgmt;
	
	var ignoreTimeDifference = true;
	
	function isWorkspacePage() {
		return window.location.href.indexOf("workspaceInit") != -1 ? true: false;
	}

	function init() {
		//alert("scheduleId : "+sId);
		//console.log(viewScheduleInfo);
		// 기본 데이터 읽기
		calElement.hide();
		$jq.getJSON(getInitDataUrl, function(data){
			ikepPlanner = $jq.extend({}, data);
			ikepPlanner.ignoreTimeDifference = ignoreTimeDifference;
			$jq.ikepPlanner = ikepPlanner;
			calElement.data("ikepPlanner", ikepPlanner);
			owner = ikepPlanner.userInfo;
			//cal = calElement.fullCalendar(calOption);

			// event 등록/수정/삭제 기능
			if($jq("#mgmt-panel").size() > 0) {
				var options = {
						ikepPlanner: ikepPlanner,
						calOption: calOption
						
				};
				eventMgmt = $jq.eventMgmt(options);
			}
			//console.log(JSON.parse(JSON.stringify(data)));
			//console.log(JSON.parse(JSON.stringify(ikepPlanner)));
			//console.log(JSON.parse(JSON.stringify(calOption)));
			if(leftMenu.length > 0) {
				calElement.trigger("onCompleteInit");
			} else if(isWorkspacePage()) {
				var urlParameters = iKEP.getUrlArguments();//$jq.getUrlVars();

				calElement.trigger("onClickWorkspace", {
					workspaceId: urlParameters.workspaceId,
					workspaceName: urlParameters.workspaceName
				});
			} else {
				calElement.trigger("onClickMyCalendar");
			}
			
		});
		
		
		// bind trigger
		calElement
		.bind("onCompleteInit", function() {
			init2();
			triggerHandler(_action, urlParameters, calendarUrl);
		})
		.bind("onClickMyCalendar", function(e, data) {
			cal.fullCalendar("removeEventSource");
			cal.fullCalendar("addEventSource", eventSources.myCalendar);
			cal.fullCalendar("addEventSource", eventSources.holiday);
			currentSource = {source: "myCalendar"};
			//changeDefaultView("month");
			changeDefaultView("agendaDay"); // 2014.09.03 웹다이어리에서 기본 달력 화면을 일간으로 변경
			//setLicurrent("#myCalendar");
			/* 2014.09.03 웹다이어리에서 left menu관련 사항은 사용하지 않음으로 주석처리
			$jq("#leftMenu-pane li").removeClass("licurrent");
			$jq("#myCalendar").addClass("licurrent");
			$jq("#myCalendar").parents("li.opened", "#leftMenu-pane").addClass("licurrent");
			*/
		})
		.bind("onClickWorkspace", function(e, data) {
			var workspaceIdParameters = data.workspaceId || '';
			
			eventSources.workspace.data = {workspaceId: workspaceIdParameters};
			eventSources.workspace.workspaceName = data.workspaceName;
			cal.fullCalendar("removeEventSource");
			cal.fullCalendar("addEventSource", eventSources.workspace);
			cal.fullCalendar("addEventSource", eventSources.holiday);
			currentSource = {source: "workspace", workspaceId: workspaceIdParameters};
			//changeDefaultView("month"); // 2014.09.03 기본 달력 화면을 일간으로 변경
			changeDefaultView("agendaDay");
		})
		.bind("onDirectUpdate", function(e, data) {
			// 반복일정인 경우 전체 반복 일정 중 해당하는 일정을 수정하기 위해
			if(data["start"]) data.start = new Date(data.start);
			if(data["end"]) data.end = new Date(data.end);
			
			currentSource = {source: "myCalendar", workspaceId: sWorkspaceId};
			editEvent(data);
		})
		.bind("onDirectView", function(e, data) { // 2014.09.03 웹다이어리 등록 화면을 위한 기능 추가
			if(data["start"]) data.start = new Date(data.start);
			if(data["end"]) data.end = new Date(data.end);
			
			currentSource = {source: "myCalendar", workspaceId: sWorkspaceId};
			viewEvents(viewScheduleInfo);
		});
		/* 2014.09.03 웹다이어리에서 사용하지 않는 기능 주석처리
		.bind("onClickMyCalendar", function(e, data) {
			cal.fullCalendar("removeEventSource");
			cal.fullCalendar("addEventSource", eventSources.myCalendar);
			cal.fullCalendar("addEventSource", eventSources.holiday);
			currentSource = {source: "myCalendar"};
			changeDefaultView("month");
			//setLicurrent("#myCalendar");
			$jq("#leftMenu-pane li").removeClass("licurrent");
			$jq("#myCalendar").addClass("licurrent");
			$jq("#myCalendar").parents("li.opened", "#leftMenu-pane").addClass("licurrent");
		})
		.bind("onClickTargetUser", function(e, data) {
			eventSources.targetUser.data = {targetUserId: data.userId};
			cal.fullCalendar("removeEventSource");
			cal.fullCalendar("addEventSource", eventSources.targetUser);
			currentSource = {source: "targetUser"};
			changeDefaultView("month");
			$jq("#leftMenu-pane li").removeClass("licurrent");
		})
		.bind("onClickDatepicker", function(e, data) {
			var $cancelBtn = $jq(".btn_cancel");
			var $closeBtn = $jq(".btn_close");
			if($cancelBtn.length > 0) {		// /수정 화면의 경우
				$cancelBtn.click();
			} else if($closeBtn.length > 0) { // 조회 화면의 경우
				$closeBtn.click();
			}
			cal.fullCalendar("changeView", "agendaDay");
			cal.fullCalendar("gotoDate", data.currentYear, data.currentMonth, data.currentDay);
			if(currentSource == null) {
				cal.fullCalendar("addEventSource", eventSources.myCalendar);
				cal.fullCalendar("addEventSource", eventSources.holiday);
				currentSource = {source: "targetUser"};
			}
		})
		.bind("onClickCompanyCalendar", function(e, data) {//무림제지 전사일정 기능 추가 2012.09.05
			
			var workAreaNameParameters = data.workAreaName || '';
			//eventSources.companyCalendar.data = {workAreaName: encodeURIComponent(workAreaNameParameters)};
			eventSources.companyCalendar.data = {workAreaName: workAreaNameParameters};
			eventSources.companyCalendar.workAreaName = workAreaNameParameters;
			cal.fullCalendar("removeEventSource");
			
			cal.fullCalendar("addEventSource", eventSources.companyCalendar);
			cal.fullCalendar("addEventSource", eventSources.holiday);
			//alert(cal.fullCalendar("option","editable"));
			currentSource = {source: "companyCalendar", workAreaName:workAreaNameParameters};
			changeDefaultView("month");//무림제지 전사일정은 month가 디폴트

		})
		.bind("onClickWorkspace", function(e, data) {
			var workspaceIdParameters = data.workspaceId || '';
			
			eventSources.workspace.data = {workspaceId: workspaceIdParameters};
			eventSources.workspace.workspaceName = data.workspaceName;
			cal.fullCalendar("removeEventSource");
			cal.fullCalendar("addEventSource", eventSources.workspace);
			cal.fullCalendar("addEventSource", eventSources.holiday);
			currentSource = {source: "workspace", workspaceId: workspaceIdParameters};
			changeDefaultView("month");
		})
		.bind("onClickMandator", function(e, data) {
			eventSources.mandator.data = {mandatorId: data.mandatorId};
			cal.fullCalendar("removeEventSource");
			cal.fullCalendar("addEventSource", eventSources.mandator);
			cal.fullCalendar("addEventSource", eventSources.holiday);
			currentSource = {source: "mandator", mandatorId: data.mandatorId};
			changeDefaultView("month");
		})
		.bind("onDirectUpdate", function(e, data) {
			// 반복일정인 경우 전체 반복 일정 중 해당하는 일정을 수정하기 위해
			if(data["start"]) data.start = new Date(data.start);
			if(data["end"]) data.end = new Date(data.end);
			
			editEvent(data);
		});
		*/
		$jq("#mgmt-panel").bind("onComplete", function() {
			var $myCalendarBtn = $jq("#myCalendar");
			/* 2014.09.03 웹다이어리에서 사용하지 않는 기능 주석처리
			calElement.show();
			if(currentSource.source === "mandator") {
				cal.fullCalendar("refetchEvents");
			}else if(currentSource.source === "companyCalendar") {//무림제지 전사일정
				//alert(currentSource.workAreaName);
				calElement.trigger("onClickCompanyCalendar" , {
					workAreaName: currentSource.workAreaName
				});
			} else {				
				if($myCalendarBtn.length > 0) {
					$myCalendarBtn.click();	// 나의일정 메뉴
				} else {
					calElement.trigger("onClickMyCalendar");
				}
			}
			if($myCalendarBtn.length > 0) {
				$myCalendarBtn.click();	// 나의일정 메뉴
			} else {
				calElement.trigger("onClickMyCalendar");
			}
			*/
			calElement.trigger("onDirectUpdate");
		});

	}

	function changeDefaultView(changeName) {
		if($jq(cal).css("display") === "none") { // 조회/수정 화면의 경우
			eventMgmt.cancel();
		}
		var defaultName;
		if(changeName){
			defaultName = changeName;
		}else{
			defaultName = calOption.defaultView;
		}
		if(cal.fullCalendar("getView") != defaultName ) {
			cal.fullCalendar("changeView", defaultName);
		}
		scrollTop();
	}

	// 201.09.03 웹다이어리에서 필요한 기능 menu.js에서 추출하여 추가
	function triggerHandler(triggerName, data, url) {
		data.action = triggerName;

		if(triggerName != "onDirectView"){
			if(calElement.length > 0) {
				calElement.trigger(triggerName, data);
			} else {
				document.location.href = url + $jq.param(data);
			}
		}else{
			currentSource = {source: "myCalendar", workspaceId: sWorkspaceId};
			viewEvents(sId);
		}
	}
	
	// 201.09.03 웹다이어리에서 필요한 기능 menu.js에서 추출하여 추가
	function init2() {
		if(ikepPlanner == null) { // body가 calendar가 아닌 경우				
			ikepPlanner = calElement.data("ikepPlanner");
		}
		// url parameter 읽기
		urlParameters = iKEP.getUrlArguments() || {};//$jq.getUrlVars();
		//_action = urlParameters["action"] || "onClickMyCalendar";
			_action = urlParameters["action"] || "onDirectView";
		
		//if(_action != "onDirectUpdate") calElement.show();
		if((_action != "onDirectUpdate")||(_action != "onDirectView")) calElement.show();
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
	
	function getCompanyInfoText(winfo) {

		if(winfo){
			return iKEPLang.planner.titleText.companySchedule + " (" + winfo + ")" ;
		}else{
			return iKEPLang.planner.titleText.companySchedule;
		}

	}
	
	function clearMinutes(d) {
		var c = cloneDate(d);
		c.setMinutes(0);
		c.setSeconds(0); 
		c.setMilliseconds(0);
		return c;
	}
	
	function getDefaultTime(start, end) {
		var now = new Date();
		var startDate =cloneDate(start).setHours(now.getHours());
		var addMin = now.getMinutes() < 30 ? 0 : 60;
		return {
			start: addMinutes(cloneDate(startDate), addMin),
			end: addMinutes(cloneDate(startDate), addMin+60)
		};
	}
	
	function editForm(start, end, allDay, view) {
		//console.log("start : "+start+"\nend : "+end+"\nallDay : "+allDay);
		//console.log(JSON.parse(JSON.stringify(currentSource)));
		var params, dt;
		if(eventMgmt) {
			if(allDay && ( (end - start) == 0) ) {
				dt = getDefaultTime(start, end);
			}
			params = {
				start: dt ? dt.start : start,
				end: dt ? dt.end : end,
				allDay: dt ? false: allDay,
				currentSource: currentSource,
			};
			calElement.hide();
			//console.log("====");
			//console.log(JSON.parse(JSON.stringify(params)));
			eventMgmt.editEvent(params);
		}
	}
	
	function viewEvent(event) {
		var sch = event.sco || {};
		var end;		

		if(eventMgmt && event.editable) {
			//alert("히히");
				calElement.hide();
				event.currentSource = currentSource;
				eventMgmt.viewEvent(event);
		} else {			
			var params = {
				scheduleId: event.id,
				start: event.start.getTime(),
				end: event.end.getTime()
			};
			var url = viewUrl + $jq.param(params);
			//top.showMainFrameDialog(url, event.title, 800, 500);
			iKEP.showDialog({
				title : event.title,
				url : url,
				width : 800,
				height : 500,
				modal : true
			});
		}
	}

	function viewEvents(scheduleId) {
		//console.log(scheduleId);
		calElement.hide();
		$jq.get(baseUrl+"getScheduleAllData.do", {scheduleId: scheduleId}, function(schedule, state, xhr) {
			if(xhr.responseText == "") {
				alert(iKEPLang.planner.errorText.eventDeleted);
				cal.fullCalendar("removeEventSource");
				calElement.trigger("onClickMyCalendar");
			} else {
				calElement.hide();
				
				$jq.extend(schedule, {editable:true, viewable:true, color:""});
				var event = $jq.fullCalendar.createEvent(schedule);
				
				currentSource = {source: "myCalendar", mandatorId: schedule.mandatorId};

				event.currentSource = currentSource;

				if(event.sco.repeat > 0) {	// 반복일정이면 반복 일정 중 해당 일자의 일정을 수정할 수 있도록...

					event.start = scheduleInfo.start;	// Date object
					event.end = scheduleInfo.end;
					
					var repeatInfo = null;
					if(event.sco.recurrences.length == 1) repeatInfo = event.sco.recurrences[0];	// 반복일정이 쪼개지지 않았을 때...
					else {	// 반복일정이 쪼개진 경우...
						var startDate = scheduleInfo.start.valueOf();
						var endDate = scheduleInfo.end.valueOf();
						$.each(event.sco.recurrences, function() {
							if(startDate >= this.startDate && endDate <= this.endDate) {
								repeatInfo = this;
							}
						});
					}
					
					event.sco.repeatType = repeatInfo.repeatType;
					event.sco.repeatPeriod = repeatInfo.repeatPeriod;
					event.sco.repeatPeriodOption = repeatInfo.repeatPeriodOption;
					event.sco.repeatStartDate = repeatInfo.startDate;
					event.sco.repeatEndDate = repeatInfo.endDate;
				}

				event.sco.contents = schedule.contents;
				event.sco.participantList = schedule.participantList;
				event.sco.alarmList = schedule.alarmList;
				event.sco.fileDataList = schedule.fileDataList;
				event.sco.recurrences = schedule.recurrences;
				event.sco.meetingRoomId = schedule.meetingRoomId;
				//console.log(event);
				eventMgmt.viewEvent(event);
				
				$jq("#mgmt-panel").bind("onUpdateCancel", function() {
					calElement.trigger("onClickMyCalendar");
					$jq(this).unbind("onUpdateCancel");
				});
			}
		});
	}
	
	function editEvent(scheduleInfo) {
		calElement.hide();
		$jq.get(baseUrl+"getScheduleAllData.do", {scheduleId: scheduleInfo["scheduleId"]||scheduleInfo["id"]}, function(schedule, state, xhr) {
			if(xhr.responseText == "") {
				alert(iKEPLang.planner.errorText.eventDeleted);
				cal.fullCalendar("removeEventSource");
				calElement.trigger("onClickMyCalendar");
			} else {
				calElement.hide();
				
				$jq.extend(schedule, {editable:true, viewable:true, color:""});
				var event = $jq.fullCalendar.createEvent(schedule);
				
				currentSource = {source: "myCalendar", mandatorId: schedule.mandatorId};
				event.currentSource = currentSource;

				if(event.sco.repeat > 0) {	// 반복일정이면 반복 일정 중 해당 일자의 일정을 수정할 수 있도록...

					event.start = scheduleInfo.start;	// Date object
					event.end = scheduleInfo.end;
					
					var repeatInfo = null;
					if(event.sco.recurrences.length == 1) repeatInfo = event.sco.recurrences[0];	// 반복일정이 쪼개지지 않았을 때...
					else {	// 반복일정이 쪼개진 경우...
						var startDate = scheduleInfo.start.valueOf();
						var endDate = scheduleInfo.end.valueOf();
						$.each(event.sco.recurrences, function() {
							if(startDate >= this.startDate && endDate <= this.endDate) {
								repeatInfo = this;
							}
						});
					}
					
					event.sco.repeatType = repeatInfo.repeatType;
					event.sco.repeatPeriod = repeatInfo.repeatPeriod;
					event.sco.repeatPeriodOption = repeatInfo.repeatPeriodOption;
					event.sco.repeatStartDate = repeatInfo.startDate;
					event.sco.repeatEndDate = repeatInfo.endDate;
				}

				event.sco.contents = schedule.contents;
				event.sco.participantList = schedule.participantList;
				event.sco.alarmList = schedule.alarmList;
				event.sco.fileDataList = schedule.fileDataList;
				event.sco.recurrences = schedule.recurrences;
				event.sco.meetingRoomId = schedule.meetingRoomId;
				eventMgmt.editEvent(event);
				
				$jq("#mgmt-panel").bind("onUpdateCancel", function() {
					calElement.trigger("onClickMyCalendar");
					$jq(this).unbind("onUpdateCancel");
				});
			}
		});
	}
	
	function eventTimeChange(method, event, dayDelta, minuteDelta, allDay, revertFunc, jsEvent) {
		var url, params, isAllDay;
		// mothod
		// 1. resize : 그리드에서 시간 조정(시작시간과 끝시간의 간격이 수정됨 - 끝시간 변경)
		// 2. move : 일정을 이동하여 시간 조정(시작시간과 끝시간의 간격은 동일 - 시작, 끝시간 모두 변경)
		//   ※ move case
		//   2-1. 시간일정의 시간이 수정되는 경우 (O)
		//   2-2. 시간일정이 종일일정으로 수정되는 경우 (X)
		//   2-3. 종일일정이 시간일정으로 수정되는 경우 (X)
		//   2-4. 종일일정의 날짜가 수정되는 경우 (O)
		// event : 일정 정보
		// dayDelta : 이동한 날짜 정보 이전 날짜 이동시 -로 표시 ex) 1(다음날), -2(이틀전)
		// minuteDelta : 이동한 분 정보 이전 시간으로 이동시 -로 표시 ex) 60, -60
		// allDay : 종일일정으로 이동했는지 여부 ex) true(종일일정으로 이동), false
		
		// 등록된 일정이 종일일정인지 여부 확인
		if(event.sco.wholeday == 1){ 
			isAllDay = true;  // 종일일정
		}else{
			isAllDay = false;  // 시간일정
		}
		
		if(event.sco.repeatType) { // 반복일정
			var start = cloneDate(event.start), end = cloneDate(event.end);
			
			if(confirm(iKEPLang.planner.messageText.repeatUpdateConfirm)) {
				revertFunc();
				//viewEvent(event);

				editEvent(event);
			} else {
				revertFunc();
			}
		} else {
			
			if(method == "move"){
				
				// 1. 등록일정 타입 == 이동일정 타입 
				// (시간일정에서 시간일정으로 이동, 종일일정에서 종일일정으로 이동)			
				if(isAllDay == allDay){
					
					url = baseUrl+method + ".do";
					params = {
							sid: event.id,
							dayDelta: dayDelta,
							minuteDelta: minuteDelta,
							updateType: event.updateType || '0'
					};
					
				}else{
					// 등록된 일정 : 시간일정, 이동후 일정 : 종일일정
					if(isAllDay == false && allDay == true){
						
						url = baseUrl+"moveAllday.do";
						params = {
								sid: event.id,
								dayDelta: dayDelta,
								minuteDelta: minuteDelta,
								endMinuteDelta: 0,
								wholeday : 1,
								updateType: event.updateType || '0'
						};
						
					}else{ // isAllDay == ture && allDay == false
					// 등록된 일정 : 종일일정, 이동후 일정 : 시간일정
						var start = cloneDate(event.start).toString();
						var end = addMinutes(cloneDate(event.start), 60).toString();
							
						url = baseUrl+"moveTime.do";						
						params = {
								sid: event.id,
								startDate: start,
								endDate: end,
								wholeday : 0,
								updateType: event.updateType || '0'
						};
						
					}
					
				}
				
			}else{ 
				
				// resize
				url = baseUrl+method + ".do";
				params = {
						sid: event.id,
						dayDelta: dayDelta,
						minuteDelta: minuteDelta,
						updateType: event.updateType || '0'
				};
			}
			
			
			$jq.postJSON(baseUrl+url, params, function(res) {
				cal.fullCalendar("refetchEvents");
			}, {error: revertFunc} );			
			
		}
	}
	cal = calElement.fullCalendar(calOption).hide();	// 초기 수정화면으로 갈 수 있기 때문에 기본은 보여지지 않도록 함.
	// main
	init();
	
	/* 2014.09.03 웹다이어리에서 사용하지 않는 기능 주석처리
	$jq("input:radio[name='areaRadio']").click(function() {
		//alert($jq(this).val());
		calElement.trigger("onClickCompanyCalendar" , {
			workAreaName: $jq(this).val()
		});
		
	});
	*/
});