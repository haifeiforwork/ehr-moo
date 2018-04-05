(function($) {

$.fn.checkParticipantsSchedule = function(options) {
	this.each(function(i, _element) {
		var element = $(_element);
		var checkSchedule = new CheckSchedule(element, options);
		checkSchedule.init();
	});
	
	return this;
};

function CheckSchedule(element, options) {
	var fc = $jq.fullCalendar;
	var formatDate = fc.formatDate;
	var parseDate = fc.parseDate;
	
	var maxCols = 42;
	var homeUrl = iKEP.getContextRoot();
	var width = options.width || 1000, height = options.height || 550;
	var startDate = options.startDate || new Date();
	var $dialog;
	var userList = {};
	var addressUsers = [];
	var emptySchedules = [];
	
	function _init() {
		initEmptySchedules();
		
		if($.isEmptyObject(userList)) {
			renderEmptySchedule();
		} else {
			readUsersSchedule();
		}
	}
	
	function init() {
		$.each(options.participants, function(idx, item) {
			addUser(item);	// 메인 화면에서 참여자의 일정을 확인 할때...
		} );
		
		$(element).dialog( {modal: true, title: iKEPLang.planner.titleText.checkParticipant,
			width: width, height: height, open: initialRender, close: destroy} );
	}
	
	function clearUserList() {
		$.each(userList, function () {
			this.events = [];
			this.checked = false;
		});
	}
	
	function addUser(user) {
		if(options.ownerId != user.id && !userList[user.id]) {
			userList[user.id] = {
				id: user.id,
				//name : user.userName + " " + user.jobTitleName + " " + user.teamName,
				name : user.userName,
				checked: false,
				events: [],
				data : user
			};
		}
	}
	
	function updateDateTitle() {
		$("h2 span:eq(0)", "#checkSchedule").html(formatDate(startDate, "MM.dd"));
	}
	
	function initialRender() {
		var url = homeUrl + "/base/js/units/lightpack/planner/template/check_schedule.html";

		$.get(url, function(template) {
			$.tmpl(template, {
				addrImgSrc: homeUrl + "/base/images/icon/ic_btn_address.gif",
				searchImgSrc: homeUrl + "/base/images/icon/ic_btn_search.gif"})
				.appendTo("#checkSchedule");

			// 타이틀 및 텍스트 추가
			$("#clickSearchMessage").text(iKEPLang.planner.messageText.clickSearchMessage);
			$("h2 span:eq(1)", "#checkSchedule").text(iKEPLang.planner.titleText.memberSchedule);
			$("#btn_ok span").text(iKEPLang.planner.btnText.ok);
			$("#btn_cancel span").text(iKEPLang.planner.btnText.cancel);
			
			$("#searchDate")
			.datepicker({
				dateFormat: "yy.mm.dd",
				showOn: "both",
				buttonImageOnly: true,
				buttonImage: homeUrl + "/base/images/icon/ic_cal.gif",
				onSelect: function(dateText, inst) {
					startDate = $(this).datepicker("getDate");
					clearUserList();
					initEmptySchedules();
				}
			})
			.datepicker("setDate", startDate)
			.next("img").addClass("dateicon");
			
			$("#address-search").click(function() {
				iKEP.showAddressBook(function(data) {
					$.each(data, function() {	// 조직도에서 추가 선택했을때
						addUser(this);
						readUsersSchedule();
					});
				}, "", {selectType:'user'});
			});
			
			$(".search").click(function() {
				readUsersSchedule();
				updateDateTitle();
			});

			$("#btn_cancel a, #btn_ok a").click(function(e) {
				e.preventDefault();
				if($(this).closest("li").attr('id') == "btn_ok") {
					var el = $("#" + options.selEl);
					el.children().remove();				
					$.each(userList, function(i, user) {
						$.tmpl(iKEP.template.userOption, user.data).appendTo(el)
							.data("data", user.data);
					});
					options.updateCount(options.selEl);
				}
				$(element).dialog("close");
				destroy();
				return false;
			});
			
			_init();
			
			updateDateTitle();
		});
	}
	
	function initEmptySchedules() {
		for(var i=0; i<maxCols; i++) {
			emptySchedules[i] = 0;
		}
	}
	
	function destroy() {
		userList = null;
		emptySchedules = null;
		eventList = null;
		$(element).dialog("destroy");
		$("#checkSchedule").empty();
	}
	
	function renderEmptySchedule() {
		var trEmp = $("<tr/>"), i, s;
		trEmp.append('<td colspan="2"><strong>' + iKEPLang.planner.titleText.emptySchedule + '</strong></td>');

		for(i=0; i<maxCols; i++) {
			if(i%2 === 0) {
				s = '<td' + (emptySchedules[i] === 0 ? ' class="sch_harf sch_free"' : '') + '></td>';
			} else {
				s = '<td' + (emptySchedules[i] === 0 ? ' class="sch_free"' : '') + '></td>';
			}
			trEmp.append(s);
		}

		if( $("#scheduleList tbody tr").size() === 0 ) {
			$("#scheduleList tbody").append(trEmp);
		} else {
			$("#scheduleList tbody tr:nth-child(1)").replaceWith(trEmp);
		}
	}
	
	function getSearchDate() {
		return $("#searchDate").val();
	}
	
	function checkScheduleHandler(data) {
		normalizeEvent(data);
		renderCheckScheduleView();
	}
	
	function recalEmpty(action, events) {
		var i, k, len, lenk, busy;
		for(i=0, len=events.length; i<len; i++) {
			busy = events[i].busy;
			for(k=0, lenk=busy.length; k<lenk; k++) {
				if(action === "plus") {
					emptySchedules[busy[k]] += 1;
				} else {
					emptySchedules[busy[k]] -= 1;
				}
			}
		}
	}

	function renderCheckScheduleView() {
		var trEvent, i, k, len, s, item, clz;

		renderEmptySchedule();
		$("#scheduleList tbody tr:gt(0)").remove();
		
		$.each(userList, function(n, user) {
			trEvent = $("<tr/>");
			var checkbox = $('<input class="checkbox" title="checkbox" type="checkbox" checked=true />')
				.click( function(e) {
					var rec = [];
					if(this.checked) {
						rec = recalEmpty("plus", user.events);
					} else {
						rec = recalEmpty("minus", user.events);
					}
					renderEmptySchedule();
				});
			var nameLink = $('<a href="#">' + user.name + '</a>)')
				.click( function(e) {
					e.preventDefault();
					showDetailSchedule(user);
				});
			var delLink = $('<td><a href="#a"><img src="' + homeUrl + '/base/images/icon/ic_btn_delete2.gif" alt="delete" /></a></td>')
			.click( function(e) {
				e.preventDefault();
				recalEmpty("minus", user.events);
				renderEmptySchedule();
				delete userList[user.id];
				$(this).closest('tr').remove();
			});
			
			trEvent.append("<td class='textLeft'><div class='ellipsis'></div></td>");
			trEvent.find("div.ellipsis").append(checkbox, nameLink);
			trEvent.append(delLink);

			for(i=0; i<42; i++) {
				s = '<td';
				clz = '';
				if(i%2 === 0) {
					clz = "sch_harf";
				}
				for(k=0, len=user.events.length; k<len; k++) {
					item = user.events[k];
					if( $.inArray(i, item.busy) > -1 ) {
						clz += " sch_full";
					}
				}
				if(i+1 == 30) {
					clz += ' tdLast';
				}
				if(clz) {
					s += ' class="' + $.trim(clz) + '"';
				}
				s += '></td>';
				trEvent.append(s);
			}
			$("#scheduleList tbody").append(trEvent);
			user.checked = true;
		});
	}
	
	function readUsersSchedule() {
		var users = [];
		$.each(userList, function(i, user) {
			if(!user.checked) {
				users.push(user.id);
			}
		});
		if(users.length > 0) {			
			var params = {
					users: users,
					startDate: +startDate,
					endDate: +startDate
			};
			
			$.postJSON("readUsersSchedule.do", params, function(data) {
				checkScheduleHandler(data);
			});
		}
	}
	
	function normalizeEvent(data) {
		var i=0, cd, start, end, ulist,allDay,
		bt, eventInfo = {},
		baseDate = parseDate(getSearchDate().replace(/\./g,'-')).setHours(6, 1, 0, 0);

		$.each(data, function() {
			start = new Date(this.startDate);
			end = new Date(this.endDate);
			cd = new Date(+baseDate);
			allDay = this.wholeday == 1 ? true : false;
			bt = [];
			$.each(emptySchedules, function(n, d) {
				if( allDay || (start && cd.between(start, end)) ) {
					emptySchedules[n] ++;
					bt.push(n);
				}
				$.fullCalendar.addMinutes(cd, 30);
			});

			ulist = this.registerId ? userList[this.registerId] : userList[this.participantId];
			ulist.events.push({
				start: start,
				end: end,
				allDay: allDay,
				title: this.title,
				busy: $.extend( [], bt )
			});
		});
	}
	
	function eventCmp(a, b) {
		var allday = ((b.allDay || false) - (a.allDay || false));
		if(allday != 0) {
			return allday;
		} else {			
			return a.start - b.start;
		}
	}
	
	function showDetailSchedule(user) {
		var $uname = $('<div/>').addClass('schName').html(user.name + ' ' + iKEPLang.planner.labelText.schedule);
		var lis = [], i, len = user.events.length, item;
		if(len > 0) {	
			user.events.sort(eventCmp);
			for(i=0; i<len; i++) {
				item = user.events[i];
				if(item.allDay) {
					lis.push('<li><span>' + iKEPLang.planner.labelText.allDayFull + '</span><span style="margin-left:20px;">'
							+ item.title + '</span></li>');
				} else {					
					lis.push('<li><span>' + formatDate(item.start, 'MM.dd HH:mm') + ' ~ '
							+ formatDate(item.end, 'MM.dd HH:mm') + '</span><span style="margin-left:20px;">'
							+ item.title + '</span></li>');
				}
			}
		} else {
			lis.push('<li><span>' + iKEPLang.planner.labelText.emptySchedule + '</span></li>');
		}

		$("#detail-schedule").empty().append( $uname, $('<div/>').addClass('schCon')
		.append($('<ul/>').append(lis.join(''))) );
	}
	
	return {
		init: init
	};
}

Date.prototype.between = function(start, end) {
	var t = this.getTime();
	return start.getTime() <= t && t <= end.getTime();
};

})(jQuery);