var host = window.location.host || "";
var calendarUrl = iKEP.getContextRoot() + "/lightpack/planner/calendar/"; //2014.09.02 WebDiary에서 일정 등록 하기 위해 추가
var viewUrl = host+iKEP.getContextRoot() + "/lightpack/planner/calendar/viewSchedule.do?docPopup=true&scheduleId=";
var fileDownloadUrl = host+iKEP.getContextRoot() + '/support/fileupload/downloadFile.do?thumbnailYn=N&fileId=';
var imagePath = iKEP.getContextRoot() + "/base/images/icon/ic_attach.gif";

var deleteSchedule;

(function($) {
	function populate(schedule) {
		var pattern = 'yyyy.MM.dd,t,HH:mm';
		var patternDate = 'yyyy.MM.dd';
		var start = new Date(+planner.start || schedule.startDate);
		var end = new Date(+planner.end || schedule.endDate);
		var alarmType = iKEPLang.planner.labelText.alarmTypes;
		var rtypearr = iKEPLang.planner.labelText.repeatTypes;
		var $participants = $("#participants");
		var $referers = $("#referers");
		var pa = [], ra = [], roption;
		var s = '', t, i, len, files;
		var allDay = schedule.wholeday == 1 ? true : false;

		var sr = $.fullCalendar.formatDate(start, pattern).split(",");
		var er = $.fullCalendar.formatDate(end, pattern).split(",");

		if(planner.userLocale == "en" || planner.userLocale != planner.portalLocale) {
			s = schedule.userEnglishInfo;
		} else {
			s = schedule.userInfo;
		}
		$("#userName").html(s)
		.click(function() {
			iKEP.goProfilePopupMain(schedule.registerId);

		})
		.addClass("ilink");
		
		$("#srd").html(sr[0]);
		if(!allDay) {				
			$("#srt").html(sr[2]+"&nbsp;");
		}
		
		$("#erd").html(er[0]);
		if(!allDay) {				
			$("#ert").html(er[2]);
		}
		
		s = schedule.repeat == 1 ? iKEPLang.planner.labelText.repeatSchedule : '&nbsp;';
		$("#repeat").html('<strong>' + s + '</strong>');

		s = allDay ? iKEPLang.planner.labelText.allDayFull : '&nbsp;';
		$("#allDay").html('<strong>' + s + '</strong>');	
		
		s = schedule.schedulePublic == 0 ? "" : iKEPLang.planner.labelText.noopen;
		$("#public").html('<strong>' + s + '</strong>');
		
		s = '';
		
		if(schedule.repeat == 1) {
			$.each(schedule.recurrences, function(n, item) {
				s += n > 0 ? '<br>' : '';
				s += '<span>' + iKEPLang.planner.labelText.every + '</span>&nbsp;';
				s += '<span>' + item.repeatPeriod + '</span>&nbsp;';
				s += '<span>' + rtypearr[item.repeatType - 1] + iKEPLang.planner.labelText.unit + '</span>&nbsp;';
				
				roption = item.repatPeriodOption ? item.repatPeriodOption.split(',') : [];
				if(item.repeatType == 2) {
					for(i=0, len=roption.length; i<len; i++) {
						s += iKEPLang.datepicker.lblDayNames[roption[i]-1] + iKEPLang.planner.labelText.dow + '&nbsp;';
						s += i+1 == len ? '' : ',';
					}
				} else if(item.repeatType == 3 && roption[0] == "a") {
					s += roption[1] + iKEPLang.planner.labelText.date_1 + '&nbsp;';
				} else if(item.repeatType == 3 && roption[0] == "b") {
					s += roption[1] + iKEPLang.planner.labelText.nth + iKEPLang.datepicker.lblDayNames[roption[2]-1] + iKEPLang.planner.labelText.dow + '&nbsp;';
				} else if(item.repeatType == 3 && roption[0] == "c") {
					s += iKEPLang.planner.labelText.last + iKEPLang.datepicker.lblDayNames[roption[1]-1] + iKEPLang.planner.labelText.dow + '&nbsp;';
				} else if(item.repeatType == 3 && roption[0] == "d") {
					s += iKEPLang.planner.labelText.lastDate;
				} else if(item.repeatType == 4 && roption[0] == "a") {
					s += s += roption[2] + iKEPLang.planner.labelText.month + '&nbsp;' + roption[1] + iKEPLang.planner.labelText.date_1 + '&nbsp;';
				} else if(item.repeatType == 4 && roption[0] == "b") {
					s += s += roption[3] + iKEPLang.planner.labelText.month + '&nbsp;' + roption[1] + iKEPLang.planner.labelText.nth + iKEPLang.datepicker.lblDayNames[roption[2]-1] + iKEPLang.planner.labelText.dow + '&nbsp;';
				} else if(item.repeatType == 4 && roption[0] == "c") {
					s += s += roption[2] + iKEPLang.planner.labelText.month + '&nbsp;' + iKEPLang.planner.labelText.last + iKEPLang.datepicker.lblDayNames[roption[1]-1] + iKEPLang.planner.labelText.dow + '&nbsp;';
				} else if(item.repeatType == 4 && roption[0] == "d") {
					s += s += roption[1] + iKEPLang.planner.labelText.month + '&nbsp;' + iKEPLang.planner.labelText.lastDate;
				}
				
				s += '<span>' + $.fullCalendar.formatDate(new Date(item.startDate), patternDate) + '</span>&nbsp;' + iKEPLang.planner.labelText.from + '&nbsp;';
				t = $.fullCalendar.formatDate(new Date(item.endDate), patternDate);
				if(t.indexOf('9999', 0) === -1) {
					s += '<span>' + t + '</span>&nbsp;' + iKEPLang.planner.labelText.untilRepeat;
				} else {
					s += '<span><strong>' + ' ' + iKEPLang.planner.labelText.forever + '</strong></span>&nbsp;';
				}
			});
			$("#repeatDesc").append(s);
		} else {
			$("#repeatDesc").hide();
		}
		
		if(schedule.categoryName!=''){
			$("#category").removeClass("colorPoint");
			$("#category").addClass("font_"+schedule.color);
			$("#category").html('[' + schedule.categoryName + ']');
			$("#category_space").show();
		}else{
			$("#category_space").hide();
		}
		$("#title").html(schedule.title);
		
		s = "";
		$.each(schedule.alarmList, function(n, item) {
			var tmpTime = item.alarmTime;
			if(tmpTime == "1440"){
				tmpTime = "1일";
			}else if(tmpTime == "120"){
				tmpTime = "2시간";
			}else if(tmpTime == "60"){
				tmpTime = "1시간";
			}else if(tmpTime == "30"){
				tmpTime = "30분";
			}
			s += n > 0 ? '<div class="schedule_bg">' : '';
			s += '<span>' + tmpTime + '전' + '</span>&nbsp;&nbsp;&nbsp;';
			s += '<span>' + alarmType[item.alarmType] + '</span>&nbsp;';
			s += n > 0 ? '</div>' : '';
		});
		$("#alarm td").append(s);
		
		var participantSummary = schedule.attendanceRequest == 1 ? {tot:0, att:0, non:0, unk:0} : null;
		$.each(schedule.participantList, function(n, item) {
			//abscentReason
			var pel = item.targetType == 1 ? $participants : $referers;
			var $li = $("<li/>").appendTo(pel);
			
			var $reason = null;
			if(item.targetType == 1 && schedule.attendanceRequest == 1) {	// 참여자이면서 참석 여부 확인을 요청한 경우
				participantSummary.tot++;
				switch(item.isAccept) {
					case 1 :	// 참여
						participantSummary.att++;
						s = $('<span class="colorBlue">[' + iKEPLang.planner.labelText.attendance + ']</span>');
						break;
					case 2 :	// 불참
						participantSummary.non++;
						s = $('<span class="colorPoint">[' + iKEPLang.planner.labelText.noattendance + ']</span>');
						$reason = $('<div class="schedule-noattendance-reason"/>').html(iKEPLang.planner.labelText.reason + " : " + item.abscentReason);
						break;
					default :	// 미정 - 0
						participantSummary.unk++;
						s = $('<span/>');
						//if(item.targetUserId == planner.userId || mandators.indexOf(item.targetUserId) >= 0) {	//해당 일정의 참여자이거나 해당 참여자로 부터 위임 받은 사용자이면...
						//	$('<a class="button_s accept" href="#a"><span><img alt="" src="' + iKEP.getContextRoot() + '/base/images/icon/ic_btn_enroll.gif"> ' + iKEPLang.planner.labelText.attendance + '</span></a>').appendTo(s).click(item.targetUserId, updateAttendance);
						//	$('<a class="button_s reject" style="margin-left:5px;" href="#a"><span><img alt="" src="' + iKEP.getContextRoot() + '/base/images/icon/ic_btn_delete1.gif"> ' + iKEPLang.planner.labelText.noattendance + '</span></a>').appendTo(s).click(item.targetUserId, updateAttendance);
						//} else {
							s.html("[" + iKEPLang.planner.labelText.unknown + "]");
						//}
				}
				
				s.appendTo($li).css("margin-right", "7px");
				
				if(item.targetUserId == planner.userId || $.inArray(item.targetUserId, mandators)!=-1) {	//해당 일정의 참여자이거나 해당 참여자로 부터 위임 받은 사용자이면...
					s = $('<span/>').appendTo($li).css("margin-right", "7px");
					$('<a class="button_s accept" href="#a"><span><img alt="" src="' + iKEP.getContextRoot() + '/base/images/icon/ic_btn_enroll.gif"> ' + iKEPLang.planner.labelText.attendance + '</span></a>').appendTo(s).click(item.targetUserId, updateAttendance);
					$('<a class="button_s reject" style="margin-left:5px;" href="#a"><span><img alt="" src="' + iKEP.getContextRoot() + '/base/images/icon/ic_btn_delete1.gif"> ' + iKEPLang.planner.labelText.noattendance + '</span></a>').appendTo(s).click(item.targetUserId, updateAttendance);
				}
			}
			
			var userInfo = "";
			if(planner.userLocale == "en" || planner.userLocale != planner.portalLocale) {
				userInfo = item.targetUserEnglishName + " " + item.targetUserJobTitleEnglishName + " " + (item.targetUserTeamEnglishName || "");
			} else {
				userInfo = item.targetUserName + " " + item.targetUserJobTitleName + " " + (item.targetUserTeamName || "");
			}

			$("<span/>").html(userInfo)
				.addClass("ilink")
				.click(function() {	iKEP.goProfilePopupMain(item.targetUserId);	})
				.appendTo($li);
			if($reason) $li.append($reason);
		});
		
		if(participantSummary && participantSummary["tot"] > 0) {
			var $div = $('<div class="participant-summary"/>').prependTo($participants.parent())
				.append('<span>' + iKEPLang.planner.labelText.total + ' : ' + participantSummary.tot + '</span>')	//iKEPLang.planner
				.append('<span>' + iKEPLang.planner.labelText.attendance + ' : ' + participantSummary.att + '</span>')
				.append('<span>' + iKEPLang.planner.labelText.noattendance + ' : ' + participantSummary.non + '</span>')
				.append('<span>' + iKEPLang.planner.labelText.unknown + ' : ' + participantSummary.unk + '</span>')
				.children(":gt(0)").css("margin-left", "20px")
				.end();
			
			if(participantSummary.non > 0) {
				$('<a class="button_s" href="#a"><span>' + iKEPLang.planner.btnText.viewReason + '</span></a>').appendTo($div.children(":eq(2)"))
					.css("margin-left", "7px")
					.click(function() {
						$(".schedule-noattendance-reason", $participants).toggle();
					});;
			}
		}
		
		$("#place").html(schedule.place);
		if(schedule.everyoneSchedule=="0"){
			$("#carToolDiv").show();
			$("#tool").html(schedule.cartooletcName);
		}
		$("#contents").html(schedule["contents"] && schedule.contents.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>"));

		$("#workspace").html(schedule.workspaceName);

		iKEP.FileController.setFileList("#attachFiles", schedule.fileDataList);
		
		$(".btn_sendmail").click(function() {
			var nameList = [], emailList = [];
			var content = schedule.contents || "";
			
			$.each(schedule.participantList, function(idx, item) {
				nameList.push(item.targetUserName);
				emailList.push(item.mail);
			});

			content += "<br><p><a href='http://" + viewUrl + scheduleId + 
				"'>" + iKEPLang.planner.messageText.showDetail + "</a><p>";
			//content += $("#mgmt-panel").html();
			iKEP.sendMailPop(nameList, emailList, schedule.title || "", content || "", "", "");			
		});
		
		//alert(schedule.everyoneSchedule);
		if(schedule.everyoneSchedule=="1"){//무림제지 전사일정에서 필요없는 기능 가림. 기본값 셋팅.
			$("#alarm").hide();
			//$("#isAccept").hide();
			$("#participants_view_tr").hide();
			$("#referers_view_tr").hide();
			$("#workspace_view_tr").hide();
			$("#public").hide();
		}
	}
	
	function init() {
		$("#participants").children().empty();
		$("#referers").children().empty();
		$("#attachFiles").children().empty();
		$("#alarm td").empty();
	}
	
	deleteSchedule = function() {
		var data = { eventInfo:{scheduleId: scheduleInfo.scheduleId} };
		var repeatInfo = scheduleInfo.recurrences[0];

		if(repeatInfo.repeatType) {	// 반복 ?�정
			var $dialog = $("#delete-dialog");
			
			$dialog.dialog({
				modal:true,
				resizable : false,
				title : iKEPLang.planner.messageText.repeatDeletDialogTitle,
				open: function() { $("input", $dialog).attr("checked", false); },
				close: function(e, ui) {
					$("#btn_delete_cancel").unbind("click");
					$("#btn_delete_ok").unbind("click");
				}
			});
			
			$("#btn_delete_cancel").click(function() {
				$dialog.dialog("close");
			});
			
			$("#btn_delete_ok").click(function() {
				var wh = $("input:checked", $dialog).val();
				if(!wh) {
					//alert(iKEPLang.planner.messageText.repeatOptionSelect);
					$dialog.dialog("option", "title", iKEPLang.planner.messageText.repeatDeleteOptionSelect);
					$("span.ui-dialog-title").css("color", "yellow");
					setTimeout(function() {
						$dialog.dialog("option", "title", iKEPLang.planner.messageText.repeatDeletDialogTitle);
						$("span.ui-dialog-title").css("color", "");
					}, 3000);
					return false;
				}
				
				data.deleteType = wh;
				$.extend(data.eventInfo, {				
					startDate: (scheduleStartDate || scheduleInfo.startDate).valueOf(),
					endDate: (scheduleEndDate || scheduleInfo.endDate).valueOf(),
					repeatType: repeatInfo.repeatType,
					repeatPeriod: repeatInfo.repeatPeriod,
					repeatPeriodOption: repeatInfo.repeatPeriodOption,
					repeatStartDate: scheduleInfo.recurrences[0].startDate,
					repeatEndDate: scheduleInfo.recurrences[scheduleInfo.recurrences.length-1].endDate,
					updateStart: "",
					updateEnd: "",
					wholeday: scheduleInfo.wholeday
				});

				$dialog.dialog("close");
				
				doDeleteSchedule(data);
			});
		} else { // �?반복 ?�정
			if(confirm(iKEPLang.planner.messageText.confirmDeleteSchedule))
				doDeleteSchedule(data);
		}
		
	};
	
	function doDeleteSchedule(data) {
		data.sendmail = (scheduleInfo.participantList.length > 0) ? confirm(iKEPLang.planner.messageText.doSendMail) : false;
		
		$("#popup_contents").ajaxLoadStart();

		var url = iKEP.getContextRoot() + "/lightpack/planner/calendar/deleteSchedule.do";
		$.postJSON(url, data, function(res) {
			iKEP.returnPopup({action:"delete", deleteType:data["deleteType"]});
		});
	}
	
	function updateAttendance(event) {
		var params = {
			reason: "",
			scheduleId: scheduleId,
			targetUserId: event.data	//userId : ?�임??처리�??�해 ?�당 참여?�의 계정??받아 처리
		};
		
		switch(true) {
			case $(this).hasClass("accept") :	// 참석
				params.isAccept =  1;

				doUpdateAttendance(params);
				break;
			case $(this).hasClass("reject") :	// 불참
				params.isAccept =  2;

				var $dialog = $("#attendance-dialog").dialog({
					title : iKEPLang.planner.titleText.noattendanceTitle,
					modal : true,
					resizable : false,
					open : function() { $("textarea", this).val(""); },
					close : function() {
						$dialog.dialog("destroy").hide()
							.find("a.button").unbind("click");
					}
				});
				$dialog.find("a.button").click(function() {
					if($(this).hasClass("btn_ok")) {
						var reason = $("textarea", $dialog).val();
						//if(!reason) { return false; }
						params.reason =  reason;
						doUpdateAttendance(params);
					}
					$dialog.dialog("close");
				});
				return false;
				break;
		}
	}
	
	function doUpdateAttendance(params) {
		$.postJSON(calendarUrl+"updateAcceptInfo.do", params, function(data) {
			//getScheduleAllData();
			location.reload();
			
		});
	}
	
	
	$(document.body).ready(function() {
		init();
		populate(scheduleInfo);
		
		$(".btn_close").click(function() {
			window.close();
		});
	});
})(jQuery);