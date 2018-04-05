(function($) {
var fc = $.fullCalendar;
var formatDate = fc.formatDate;
var parseISO8601 = fc.parseISO8601;
var addDays = fc.addDays;
var addMinutes = fc.addMinutes;
var applyAll = fc.applyAll;

var timeDifference;
var owner;

fc.sourceNormalizers.push(function(sourceOptions) {
	if (sourceOptions.dataType == 'ikep4' ||
		sourceOptions.dataType === undefined) {
			sourceOptions.dataType = 'ikep4';
		}
});


fc.sourceFetchers.push(function(sourceOptions, start, end) {
	if (sourceOptions.dataType == 'ikep4') {
		return transformOptions(sourceOptions, start, end);
	}
});

fc.createEvent = function(item, optionName) {
	var sd = item.startDate || item.start;
	var ed = item.endDate || item.end;
	var styles = eventStyle[item.color];

	var start = new Date(sd); //addMinutes(new Date(sd), timeDifference * -60);
	var end = ed ? new Date(ed) : null; //ed ? addMinutes(new Date(ed), timeDifference * -60) : null;
	var editable = item.editable;
	var viewable = item.viewable;

	var title = (editable || viewable) || (optionName === "holiday") ? 
			item.title : iKEPLang.planner.titleText.privateSchedule;
	var categoryName = item.color;
	var backgroundColor, borderColor;
	if(styles) {						
		backgroundColor = styles.background_color;
		borderColor = styles.border;
	}
	
	return {
		id: item.scheduleId,
		title: title,
		start: start,
		end: end,
		allDay: item.wholeday,
		sco: item,
		editable: editable, 
		viewable: viewable,
		resizable:item.resizable,
		draggable:item.draggable,
		isPrivate: item.schedulePublic,
		categoryName: categoryName,
		backgroundColor: backgroundColor,
		borderColor: borderColor,
		refYn: item.refYn,
		pacYn: item.pacYn,
		isHidden: item.schedulePrivate
	};
};


function transformOptions(sourceOptions, start, end) {
//	if(!timeDifference) {		
//		var ikepPlanner = $.ikepPlanner;
//		var ignoreTimeDifference = ikepPlanner.ignoreTimeDifference;
//		timeDifference = !ignoreTimeDifference ? ikepPlanner.userInfo.timeDifference : 0;
//	}
	if(!owner) {		
		var ikepPlanner = $.ikepPlanner;
		var owner = ikepPlanner.userInfo;
	}
	
	var success = sourceOptions.success;
	var dataParams = $.extend({}, sourceOptions.data || {}, {
		startDate: +start,
		endDate  : +end
	});

//	var isEditable = function(event) {
//		return sourceOptions.editable ? 
//				(owner.userId == event.registerId || sourceOptions.name == "mantator") : sourceOptions.editable;
//	};
	
	return $.extend({}, sourceOptions, {
		url: sourceOptions.url,
		dataType: 'json',
		contentType: "application/json; charset=utf-8",
		data: dataParams,
		startParam: false,
		endParam: false,
		success: function(data) {
			var uinfo = data.userInfo || data.mandatorInfo || {};
			var events = [];

			if (data.events) {
				$.each(data.events, function(i, item) {
					events.push(fc.createEvent(item, sourceOptions.name));
				});
			}
			var args = [uinfo,events].concat(Array.prototype.slice.call(arguments, 1));
			var res = applyAll(success, this, args);
			if ($.isArray(res)) {
				return res;
			}
			return events;
		}
	});
	
}

})(jQuery);
