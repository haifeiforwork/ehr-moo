$jq(document).ready(function() {
	var fc = $jq.fullCalendar;
	var formatDate = fc.formatDate;
	var addDays = fc.addDays;
	var cloneDate = fc.cloneDate;
	var dayDiff = fc.dayDiff;
	var addMinutes = fc.addMinutes;

	var home_url = iKEP.getContextRoot() + "/";	
	var today = iKEP.getCurTime();//new Date();

	var events = [];
	var viewUrl = home_url + "lightpack/planner/calendar/viewSchedule.do?dialog=1&scheduleId=";
	var $periodTd = $jq("#daily_schedule_pane", "#" + dailyOptions.portletConfigId).find("td");
	var $ulPane = $jq("ul", "#" + dailyOptions.portletConfigId);
	var periodCount = $periodTd.size();
	
	var startTime = today.setHours(8, 0, 0, 0);
	var endTime = today.setHours(18, 0, 0, 0);
	
	var portletPopupOpenOptions = {
			width:600, height:500, modal:true
	};

	$jq.getJSON(home_url+"lightpack/planner/calendar/calFeedMySchedule.do",
			{userId: dailyOptions.userId, 
			 startDate: +today,
			 endDate: +today}, 
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
				render();
				showTotalEvent();
			 });
		
	function cmp(a, b) {
			return a.start - b.start;
	}
	
	function render() {
		var baseDate = today.setHours(8, 1, 0, 0);
		var currDate;
		
		$jq(".date", "#" + dailyOptions.portletConfigId).html(formatDate(today, iKEPLang.planner.titleFormat.day));
		
		$jq.each(events, function(idx, event) {
			var elist = [];
			currDate = new Date(+baseDate);
			$jq.each($periodTd, function(i, period) {
				if(currDate.between(event.start, event.end) 
						|| (periodCount == (i+1) && currDate < event.start)
						|| (i == 0 && currDate > event.start)) {
					elist = $jq(period).data("eventList");
					if(!elist) {
						elist = [];
					}
					elist.push(event);
					$jq(period)
					.addClass("pr_schbar_2 ilink")
					.data("eventList", elist)
					.click(function() {
						var eobj = $jq(period).data("eventList");
						$ulPane.empty();
						$jq.each(eobj, function(k, e) {		
							dispEvent(e);
						});
					});
				}
				$jq(period).addClass("period-time-" + formatDate(currDate, "H-m"));
				addMinutes(currDate, 30);
			});
		});
	};
	
	function showTotalEvent() {
		var len = events.length;
		var e;
		
		$ulPane.empty();
		if(len > 0) {
			for(var i=0; i<len; i++) {
				dispEvent(events[i]);
			}
		} else {			
			$jq("<li><span>" + "" + iKEPLang.planner.messageText.emptySchedule +  "</span></li>")
			.appendTo($jq("ul", "#" + dailyOptions.portletConfigId));
		}
	}
	
	function dispEvent(event) {
		var start = formatDate(event.start, "MM.dd HH:mm");
		var end = formatDate(event.end, "MM.dd HH:mm");

		var liEl = $jq("<li></li>")
		.append("<span>" + start + " ~ " + end + "</span>")
		.append("<span class='cal_color_box " + event.color + "'></span>");
		
		$jq("<a  href='#a'>&nbsp;" + event.title + "</a>")
		.click(function(ev) {
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
									break;
							}
						}
					}
				})
			);
			ev.preventDefault();
		}).appendTo(liEl);
		
		liEl.appendTo($jq("ul", "#" + dailyOptions.portletConfigId));
	}
});

Date.prototype.between = function(start, end) {
	var t = this.getTime();
	return start.getTime() <= t && t <= end.getTime();
};
