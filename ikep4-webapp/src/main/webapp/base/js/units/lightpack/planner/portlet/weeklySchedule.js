	var fc = $jq.fullCalendar;
	var formatDate = fc.formatDate;
	var addDays = fc.addDays;
	var cloneDate = fc.cloneDate;
	var dayDiff = fc.dayDiff;

	var home_url = iKEP.getContextRoot() + "/";	
	var today = iKEP.getCurTime();//new Date();
	var todayDay = today.getDay();

	var sunday = addDays(cloneDate(today), 0 - todayDay);
	var satday = addDays(cloneDate(today), 6 - todayDay);
	
	var events = [];
	var eventsCompany = [];
	var eventsWorkspace = [];
	var viewUrl = home_url + "lightpack/planner/calendar/viewSchedule.do?dialog=1&scheduleId=";

	var portletPopupOpenOptions = {
			width:600, height:500, modal:true
	};
	function getWeeklyPlan(){

		events = [];
		
		$jq("thead", "#" + weeklyOptions.portletConfigId).find("th").each(function (idx, $th) {
			$jq("span", this).html(iKEPLang.datepicker.lblDayNamesShort[idx]);
		});
	

		
		$jq.getJSON(home_url+"lightpack/planner/calendar/calFeedMySchedule.do",
			{userId: weeklyOptions.userId, 
			 startDate: +cloneDate(sunday),
			 endDate: +cloneDate(satday)}, 
			function(data) {
				 var event = {};
				$jq.each(data.events, function(n, item) {
					event = {
						start: new Date(item.startDate),
						end: new Date(item.endDate),
						title: item.title,
						allDay: item.wholeday,
						id: item.scheduleId,
						color: item.color
					};
					events.push(event);
				});
				events.sort(cmp);
				render(weeklyOptions.portletConfigId,events);
				showTodayEvent(weeklyOptions.portletConfigId);

		});
	};

	
	function getWeeklyPlanCompany(){

		eventsCompany = [];
		
		$jq("thead", "#" + weeklyOptions.portletConfigIdCompany).find("th").each(function (idx, $th) {
			$jq("span", this).html(iKEPLang.datepicker.lblDayNamesShort[idx]);
		});
	

		
		$jq.getJSON(home_url+"lightpack/planner/calendar/calFeedCompanySchedulePortlet.do",
			{workAreaName: "", 
			 startDate: +cloneDate(sunday),
			 endDate: +cloneDate(satday)}, 
			function(data) {
				 var event = {};
				$jq.each(data.events, function(n, item) {
					event = {
						start: new Date(item.startDate),
						end: new Date(item.endDate),
						title: item.title,
						allDay: item.wholeday,
						id: item.scheduleId,
						color: item.color
					};
					eventsCompany.push(event);
				});
				eventsCompany.sort(cmp);
				render(weeklyOptions.portletConfigIdCompany, eventsCompany);
				showTodayEvent(weeklyOptions.portletConfigIdCompany);


		});
	};
	
	function getWeeklyWorkspacePlan(){

		eventsWorkspace = [];
		
		$jq("thead", "#" + weeklyOptions.portletConfigIdWorkspace).find("th").each(function (idx, $th) {
			$jq("span", this).html(iKEPLang.datepicker.lblDayNamesShort[idx]);
		});
	

		
		$jq.getJSON(home_url+"lightpack/planner/calendar/calFeedWorkspaceSchedule.do",
			{workspaceId: '', 
			 startDate: +cloneDate(sunday),
			 endDate: +cloneDate(satday)}, 
			function(data) {
				 var event = {};
				$jq.each(data.events, function(n, item) {
					event = {
						start: new Date(item.startDate),
						end: new Date(item.endDate),
						title: item.title,
						allDay: item.wholeday,
						id: item.scheduleId,
						color: item.color
					};
					eventsWorkspace.push(event);
				});
				eventsWorkspace.sort(cmp);
				render(weeklyOptions.portletConfigIdWorkspace,eventsWorkspace);
				showTodayEvent(weeklyOptions.portletConfigIdWorkspace);

		});
	};
	
	function render(whereDiv , thatEvents) {
		$jq("tbody", "#" +whereDiv ).find("td").each(function(idx, ele) {
			var empty = true;
			var cdate = addDays(cloneDate(sunday, true), idx);
			var $td = $jq(this);
			var elist = [];
			$td.data("cdate", cdate);

			$jq.each(thatEvents, function(i, event) {
				if(cdate.between(cloneDate(event.start, true), cloneDate(event.end, true))) {
					empty = false;
					elist.push(event);
				}
			});
			$td.data("eventList", elist);
			
			if(!empty) {
				$jq("span", $td).addClass("pr_schbar_2 ilink")
				.click(function() {			
					$jq("ul", "#" + whereDiv).empty();
					$jq(".date", "#"+whereDiv).html(formatDate(cdate, iKEPLang.planner.titleFormat.day));

					$jq.each(elist, function(i, event) {						
						var start = ""+formatDate(event.start, "MM.dd HH:mm");
						var end = ""+formatDate(event.end, "MM.dd HH:mm");
						if(end.indexOf("00:00")>-1){
							end = end.split(" ")[0]+" 24:00";
						}
						
							
						
						/*
						if(event.allDay){
							 start = formatDate(event.start, "MM.dd");
							 end = formatDate(event.end, "MM.dd");	
							 if(start==end){
								 start = "[종일일정] "+start;
							 }else{
								 start = "[기간일정] "+start;
							 }
						}
						*/
						dispEvent(event, start, end ,whereDiv);
					});
				});
			}
		});
	};

	function showTodayEvent(whereDiv) {
		var todayTd = $jq("tbody td:eq(" + todayDay + ") span", "#" + whereDiv);
		
		$jq(".date", "#"+whereDiv).html(formatDate(today, iKEPLang.planner.titleFormat.day));

		if(todayTd.parent().data("eventList") && todayTd.parent().data("eventList").length > 0) {
			todayTd.click();
		} else {
			
			$jq("ul", "#" + whereDiv).html($jq("<li><span>" + iKEPLang.planner.messageText.emptyTodaySchedule + "</span></li>"));
		}
	}
	
	function dispEvent(event, startStr, endStr,whereDiv) {
		var liEl = $jq("<li></li>")
			.append("<span>" + startStr + " ~ " + endStr + "</span>")
			.append("<span class='cal_color_box " + event.color + "'></span>");
		
		$jq("<a href='#a'>&nbsp;" + event.title + "</a>")
		.click(function(e) {
			iKEP.portletPopupOpen(viewUrl + event.id, 
				$jq.extend(portletPopupOpenOptions, {
					windowTitle :event.title,
					documentTitle: iKEPLang.planner.titleText.viewDetailEvent,
					argument : {startDate:event.start, endDate:event.end},
					callback:function(result) {
						if(result && result["action"]) {
							switch(result.action) {
								case "update" :
									// 반복일정의 경우 해당 일정의 수정을 위해 날짜 정보를 함께 보냄.
									var scheduleInfo = {scheduleId:event.id, start:event.start.valueOf(), end:event.end.valueOf()};
									iKEP.goScheduleUpdate(scheduleInfo);
									break;
								case "delete" :
									var portletId = liEl.parents("div.portletUnit").data("portletId");
									var portletManager = PortletManager.getInstance();
									var portlet = portletManager.getPortlet(portletId);
									portlet.box.container.trigger("click:reload");
/*
									if(!result.deleteType) {	// 단순일정
									} else {	// 반복일정
										switch(result.deleteType) {
											case 1 : break;	// 이 일정만 삭제
											case 2 : break;	// 이후 모두 삭제
											case 3 : break;	// 전체 삭제
										}
									}
									liEl.remove();*/
									break;
							}
						}
					}
				})
			);
			e.preventDefault();
		}).appendTo(liEl);

		liEl.appendTo($jq("ul", "#" + whereDiv));
	}
	
	function cmp(a, b) {
		return a.start - b.start;
	}
	
	var dreg =/^(\d{4})[-|:|.]*(\d{2})[-|:|.]*(\d{2})(\d{2})(\d{2})$/;
	function toDate(ds) {
		var ar = dreg.exec(ds);
		return new Date(ar[1],Number(ar[2]) - 1,ar[3],ar[4],ar[5]);
	}
	
$jq(document).ready(function() {
	
	//getWeeklyPlanCompany();
	

});

Date.prototype.between = function(start, end) {
	var t = this.getTime();
	return start.getTime() <= t && t <= end.getTime();
};
