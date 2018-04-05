var todayCalendar, nextdayCalendar;

// workalingment 오늘, 내일 일정 (일간보기)
$jq(document).ready(function() {
	var fc = $jq.fullCalendar;
	var formatDate = fc.formatDate;
	var addDays = fc.addDays;
	var cloneDate = fc.cloneDate;
	
	var today = iKEP.getCurTime();//new Date();
	var nextday = addDays(cloneDate(today), 1);
	
	var rangeDate = {
		startDate: today.getTime(),
		endDate  : nextday.getTime()
	};
	
	var calUrl = iKEP.getContextRoot() + "/lightpack/planner/calendar/";
	
	var options = {
		header: null,
		buttonText: null,			
		titleFormat: iKEPLang.planner.titleFormat,
		columnFormat: iKEPLang.planner.columnFormat,
		timeFormat: { // for event elements
			'': 'HH:mm', // default
			agenda: 'HH:mm{ - HH:mm}'
		},
		axisFormat: 'HH:mm',
		defaultView: 'agendaDay',
		allDaySlot: false,
		minTime: '7:00am',
		maxTime: '8:00pm',
		allDayDefault: false,
		lazyFetching: true,
		// locale
		monthNames: iKEPLang.datepicker.lblMonthNames,
		monthNamesShort: iKEPLang.datepicker.lblMonthNamesShort, 
		dayNames: iKEPLang.datepicker.lblDayNames,
		dayNamesShort: iKEPLang.datepicker.lblDayNamesShort,
		eventRender: function(event, element, view) {
			element.addClass("fc-event-draggable");
		},
		eventClick : function(event, jsEvent, view) {
			if(event.source.name == 'holiday') {
				return false;
			}
			
			var url = iKEP.getContextRoot() + "/lightpack/planner/calendar/viewSchedule.do";
			//var params = {scheduleId: event.id, start: event.start.getTime(), end: event.end.getTime()};
			//top.showMainFrameDialog(url, event.title, 800, 500);
			var options = {
				width:700,
				height:320,
				resizable:true,
				//argument : {startDate:strToDate(startDate), endDate:strToDate(endDate)},
				argument : {startDate:event.start.toString(), endDate:event.end.toString()},
				callback : function(result) {
					if(result && result["action"]) {
						switch(result.action) {
							case "update" :
								iKEP.goScheduleUpdate({scheduleId:event.id}, null);
								break;
							case "delete" :
								todayCalendar.fullCalendar("removeEventSource");
								nextdayCalendar.fullCalendar("removeEventSource");
								
								$jq.post(calUrl + "calFeedMySchedule.do", rangeDate, addEvents);
								break;
						}
					}
				}
			};
			iKEP.popupOpen(url + "?scheduleId="+event.id, options);
		}
	};
	
	todayCalendar = $jq('#todayCalendar').fullCalendar($jq.extend({}, options, {
			year : today.getFullYear(),
			month : today.getMonth(),
			date : today.getDate()
		}));
	
	
	nextdayCalendar = $jq('#nextdayCalendar')
		.fullCalendar($jq.extend({}, options, {
				year: nextday.getFullYear(),
				month: nextday.getMonth(),
				date: nextday.getDate()
		}));
	
	function addEvents(res) {
		var data = res.events;
		var todayEvents = [], nextdayEvents = [];
		var td = today.getDate();
		var e;
		
		$jq.each(data, function(n, item) {
			var styles = eventStyle[item.color];
			var backgroundColor, borderColor;
			if(styles) {						
				backgroundColor = styles.background_color;
				borderColor = styles.border;
			}
			e = {
				start: new Date(item.startDate),
				end: new Date(item.endDate),
				title: item.title,
				id: item.scheduleId,
				backgroundColor: backgroundColor,
				borderColor: borderColor
			};
			if(e.start.getDate() == td) {
				todayEvents.push(e);
			} else {
				nextdayEvents.push(e);						
			}
		});
		todayCalendar.fullCalendar('addEventSource', todayEvents);
		nextdayCalendar.fullCalendar('addEventSource', nextdayEvents);		
	}
	
	$jq.post(calUrl + "calFeedMySchedule.do", rangeDate, addEvents);	
});