$jq(document).ready(function() {
	var fc = $jq.fullCalendar;
	var formatDate = fc.formatDate;
	var addDays = fc.addDays;
	var cloneDate = fc.cloneDate;
	var dayDiff = fc.dayDiff;
	var addMinutes = fc.addMinutes;

	var home_url = iKEP.getContextRoot() + "/";	
	var today = new Date();

	var events = [];
	var viewUrl = home_url + "lightpack/planner/calendar/viewSchedule.do?dialog=1&scheduleId=";
	var $periodTd = $jq("#daily_schedule_pane", "#" + dailyOptionex.portletConfigId).find("td");
	var $ulPane = $jq("ul", "#" + dailyOptionex.portletConfigId);
	var periodCount = $periodTd.size();
	
	var startTime = today.setHours(8, 0, 0, 0);
	var endTime = today.setHours(18, 0, 0, 0);
	
	var portletPopupOpenOptions = {
			width:600, height:500, modal:true
	};
	$jq.getJSON(home_url+"lightpack/planner/calendar/readUserDateSchedule.do",
			{userId: dailyOptionex.userId, 
			 startDate: dailyOptionex.personalScheduleDate,
			 endDate: dailyOptionex.personalScheduleDate}, 
			function(data) {
				 var event = {};
				$jq.each(data, function(n, item) {
					event = {
						start: toDate(item.startDate),
						end: toDate(item.endDate),
						title: item.title,
						allDay: item.wholeday,
						id: item.scheduleId,
						color: item.color,
						participantId:item.participantId,
						registerId:item.registerId,
						schedulePublic:item.schedulePublic
					};
					//alert(dailyOptionex.loginUserId);
					if(dailyOptionex.loginUserId != item.registerId && item.schedulePrivate == '0'){
						events.push(event);
					}else if(dailyOptionex.loginUserId == item.registerId){
						events.push(event);
					}
				});			
				
				events.sort(cmp);
				//render();
				showTotalEvent();
			 });
		
	function cmp(a, b) {
			return a.start - b.start;
	}
	
	function render() {
		var baseDate = today.setHours(8, 1, 0, 0);
		var currDate;
		
		$jq(".date", "#" + dailyOptionex.portletConfigId).html(formatDate(today, iKEPLang.planner.titleFormat.day));
		
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
			.appendTo($jq("ul", "#" + dailyOptionex.portletConfigId));
		}
	}
	
	function dispEvent(event) {
//		var start = dayDiff(cloneDate(today, true), cloneDate(e.start, true)) == 0 ? formatDate(e.start, "HH:mm") : formatDate(e.start, "MM.dd HH:mm");
//		var end = dayDiff(cloneDate(today, true), cloneDate(e.end, true)) == 0 ? formatDate(e.end, "HH:mm") : formatDate(e.end, "MM.dd HH:mm");
		var start = formatDate(event.start, "HH:mm");
		var end = formatDate(event.end, "HH:mm");
		var loginUserId = dailyOptionex.loginUserId;//로그인 사용자
		var participantId = event.participantId;//비공개일경우 참여자와 참조자 즉 조회가능자 
		var registerId = event.registerId;//일정 주인
		var schedulePublic= event.schedulePublic;//1 비공개 
		var titleStr="";
		var privateFlag=false;
		
		if(schedulePublic=="0"||loginUserId==registerId){//공개일정이거나 일정주인이면
			titleStr = event.title;
		}else if(participantId.indexOf(loginUserId)>-1){//비공개일정이면 포함되는지 확인
			titleStr = event.title;
		}else{
			titleStr = iKEPLang.planner.titleText.privateSchedule;//비공개 일정 
			privateFlag=true;
		}

		var liEl = $jq("<li class='ic'></li>")
		.append("<span>" + start + " ~ " + end + "</span>")
		.append("<span class='cal_color_box " + event.color + "'></span>");
		if( privateFlag==false){
			$jq("<a  href='#a'>&nbsp;" +titleStr +"</a>")
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
		}else{
			$jq("<a  href='javascript:alert(\""+titleStr+"\");'>&nbsp;" +titleStr +"</a>")
			.appendTo(liEl);
		}
		
		liEl.appendTo($jq("ul", "#" + dailyOptionex.portletConfigId));
	}

	var dreg =/^(\d{4})[-|:|.]*(\d{2})[-|:|.]*(\d{2})(\d{2})(\d{2})$/;
	function toDate(ds) {
		var ar = dreg.exec(ds);
		return new Date(ar[1],Number(ar[2]) - 1,ar[3],ar[4],ar[5]);
	}
});

Date.prototype.between = function(start, end) {
	var t = this.getTime();
	return start.getTime() <= t && t <= end.getTime();
};
