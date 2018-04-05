var goForm;
( function($) {
	var mgmtPanelId = "mgmt-panel";
	var $mgmtPanel = $("#"+mgmtPanelId);
	var home_url = iKEP.getContextRoot() + "/";	
	var getInitDataUrl = home_url + "lightpack/planner/calendar/getInitData.do";
	
	var ikepPlanner;
	var owner;
	var setting;
	
	var Message = iKEPLang.meetingroom;
	var messageConfirmMail = Message.messageText.doSendMail;
	var today = iKEP.getCurTime();
	var fc = $.fullCalendar;
	var formatDate = fc.formatDate;
	var cloneDate = fc.cloneDate;
	var addMinutes = fc.addMinutes;
	var clearTime = fc.clearTime;
	var addDays = fc.addDays;
	var addMonths = fc.addMonths;
	var addYears = fc.addYears;
	var addMinutes = fc.addMinutes;
	
	var schedule = null;
	var startTimeOptionStr;
	var endTimeOptionStr;
	var ignoreTimeDifference = true;
	var FOREVER_DATE = new Date(9999, 11, 31);
	
	var fileController;
	var uploaderOptions = {// 사용할 수 있는 옵션 항목
        isUpdate : true,    // 등록 및 수정일때 true
        initActive: true
    };
	
	var $update_dialog = $jq("#update-dialog");
	var $delete_dialog = $jq("#delete-dialog");
	var dialogButton = 0;	//0-cancel, 1-ok
	
	var roptionEl = '<input type="radio" name="rpoption" class="monthly-rop"/>';
	
	var recurrenceData = {
			"daily" 	: {freq:"daily",   freqStr: Message.labelText.daily},
			"weekly" 	: {freq:"weekly",  freqStr: Message.labelText.weekly,
							dayNames: iKEPLang.datepicker.lblDayNames},
			"monthly" 	: {freq:"monthly", freqStr: Message.labelText.monthly},
			"yearly" 	: {freq:"yearly", freqStr: Message.labelText.yearly}
	};
	
	var radioIndexStr  = "abcd";
	
	var repeatTypeCnvt = {"daily": 1, "weekly": 2, "monthly": 3, "yearly": 4};
	
	var sco;
	
	function init() {
		
		$mgmtPanel
		.delegate(".btn_cancel", "click", cancel)
		/*
		 * 무한 반복 없음
		.delegate("#forever", "click", function(e) {
			
			if(this.checked) {
				$("#endRepeatDate").datepicker("disable");
			} else {
				$("#endRepeatDate").datepicker("enable");
			}
		})
		*/
		.delegate("#repeat", "click", function(e) {
			if(this.checked) {
				$("#repeat-div").removeClass("hidden");
				$("#repeatType").attr("value", "daily");
				$("#repeatType").change();
			} else {
				$("#repeat-div").addClass("hidden");
			}
		})
		.delegate("#btnToolReserve", "click", function() {
				/*
				var schedule = null;
				if($("#repeat").get(0).checked) {
					schedule = getData();
				}
				*/
				var schedule = getData();
				
				iKEP.showDialog({
					title: "자원예약연동",
					url : iKEP.getContextRoot() + "/lightpack/meetingroom/cartooletcMain.do?mode=schedule&mid="+$("#meetingRoomId").val(),
					modal : true,
					width : 800,
					height: 500,
					params : schedule,
					callback : function(meetingRoomInfo) {
						
						
						$("#tool").val(meetingRoomInfo.cartooletcName);
						$("#cartooletcId").val(meetingRoomInfo.cartooletcId);
						
						setToolReserve();
						
						this.close();
					}
				});
			})
			.delegate("#btnToolCancel", "click", function() {
				$("#tool").attr("readonly", false)
					.val("");
				$("#cartooletcId").val("");
				
				$(this).hide();
			})
		.delegate("#checkDuplicateRepeatReserve", "click", function(e) {
			if(!checkReserveTime()) return false;

			var event = getData();
			
			event.fileLinkList = [];
			event.sendmail = false;
			
			$mgmtPanel.ajaxLoadStart();
			$.postJSON(home_url + "lightpack/meetingroom/reserve/checkDuplicate.do", event, function(res) {
				if(res.length > 0) {
					alert(Message.messageText.duplicateReserve);
				} else {
					alert(Message.messageText.availableReserve);
				}
				
				$mgmtPanel.ajaxLoadComplete();
			});
		})
		.delegate("#repeatType", "change", function(e) {
			
			$("#repeat_pane").empty().removeClass("hidden");
			//$repeat_pane.removeData("roption");
			
			var selectedType = this.value;
			var sd = $("#start-date-picker").datepicker("getDate");
	
			var o = {};
			var els = $("#recurrenceTemplate").tmpl( recurrenceData[selectedType], 
				{ 
					count: 1,
					getOption: function() {
						if(selectedType === "monthly" || selectedType === "yearly") {
							o = getOptionMsg(sd, selectedType);
							return o.msg;
						}
						return "";
					},
					isForever: function() {
						return this.data.endDate == "9999-12-31" ? "checked" : "";
					}
				}).appendTo( "#repeat_pane" );
	
			var expd;
			
			switch(selectedType) {
			case "daily":
				expd = addDays(cloneDate(sd), 7);
				break;
			case "weekly":
				expd = addMonths(cloneDate(sd), 1);
				$("#roption-span").find("input:checkbox:eq(" +
						sd.getDay() + ")").attr("checked", true);
				$("#roption-span").find("input:checkbox:eq(" +
						sd.getDay() + ")");//.attr("disabled", true);
				break;
			case "monthly":
				expd = addYears(cloneDate(sd), 1);
				$("#roption-span").data("roption", o.op);
				$("#roption-span").find("input:radio:eq(0)").attr("checked", true);
				break;
			case "yearly":
				expd = addYears(cloneDate(sd), 1);
				$("#roption-span").data("roption", o.op);
				$("#roption-span").find("input:radio:eq(0)").attr("checked", true);
				break;
			}	
	
			var maxDate = new Date(today.getTime());
			maxDate.setMonth(maxDate.getMonth()+meetingRoomConfig.maxReservationMonth);
			$("#endRepeatDate")
			.datepicker($.extend({}, datepickerOptions, {
				minDate : today,
				maxDate : maxDate
			}))
			.datepicker("setDate", expd);
			//.next("img").addClass("dateicon");
			
			if(selectedType === "yearly") {
				//$("#forever").attr("checked", true);
				$("#endRepeatDate").datepicker("disable");
			}
		})
		.delegate(".alarm-plus", "click", function(event) {
			var len = $("#alarm-div .alarm-item").length;
			event.preventDefault();
			if(len === 3) return;
			appendAlarm();
			
			var pn = $("#alarm-div .alarm-item").length;
			if( pn === 2 ) {
				$("#alarm-div .alarm-minus").show();
			} else if(pn === 3) {
				$("#alarm-div .alarm-plus").hide();
			}
		})
		.delegate(".alarm-minus", "click", function(event) {
			var len = $("#alarm-div .alarm-item").length;
			event.preventDefault();
			if(len === 1) return;
			$(this).parents(".alarm-item").remove();
			
			adjustAlarmRadio();
			
			var pn = $("#alarm-div .alarm-item").length;
			if( pn === 1 ) {
				$("#alarm-div .alarm-minus").hide();
			} else if(pn === 2) {
				$("#alarm-div .alarm-plus").show();
			}
		})
		.delegate(".alarm-item input:radio", "change", function() {
			var op = $(".alarm-item select option");
	
			if(op[0].selected && this.checked) {		
				op[1].selected = true;
			}
			
			adjustAlarmRadio();
		})	
		// 참여자, 참조자
		// TODO: 인원 제한
		.delegate("#participantInput, #refererInput", "keypress", function(event, wh) {
			if(event.keyCode == 13 || wh) {			
				var selId = $(this).siblings("div").children("select")[0].id;
				searchUser(event, selId);
			}
		})
		.delegate("#btn_parti_searchUser, #btn_referer_searchUser", "click", function(event) {
			var targetId = $(this).prev()[0].id;
			$("#" + targetId).trigger("keypress", "btn");
		})
		.delegate("#btn_parti_showAddress, #btn_referer_showAddress", "click", function(event) {
			var selId = $(this).siblings("div").children("select")[0].id;
			searchUserByAddressbook(event, selId);
		})
		.delegate("#btn_delete_paticipant", "click", function(){
			$("#participants option:selected").remove();
			updateCount("participants");
		})
		.delegate("#btn_delete_referer", "click", function(){
			$("#referers option:selected").remove();
			updateCount("referers");
		})
		.delegate("#btn_parti_schedule", "click", function() {
			var participants = [];
			$("#participants option").each(function() {
				//participants.push({registerId: this.value, registerName: this.text});
				participants.push($(this).data("data"));
			});
			
			$("#checkSchedule").checkParticipantsSchedule({
				participants: participants,
				startDate: $("#start-date-picker").datepicker("getDate"),
				selEl: "participants",
				updateCount: updateCount,
				ownerId: owner.userId
			});
		})			
		.delegate(".btn_save a", "click", function(e) {
			e.preventDefault();
			//save(existParticipant());	// 저장 의사가 확인 되었을때 메일 발송 여부를 확인하기 위해 메일을 발송할 수 있는 상태인지만 확인함
			
			$("#fileUploadForm").trigger("submit");	// Validator를 거치도록 변경
			
			/*if(existParticipant() && confirm(messageConfirmMail)) {
				save("sendmail");
			} else {
				save();
			}*/
		})
		.delegate(".btn_delete a", "click", function(e) {
			
			deleteEvent(sco, (sco.participantList.length > 0));
		});
		
		$jq.getJSON(getInitDataUrl, function(data){
			
			ikepPlanner = $jq.extend({}, data);
			ikepPlanner.ignoreTimeDifference = ignoreTimeDifference;
			$jq.ikepPlanner = ikepPlanner;
			
			owner = ikepPlanner.userInfo;

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
				minTime:'7:00',
				maxTime:'24:00',
				defaultView: "agendaWeek",
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
				dayNamesShort: iKEPLang.datepicker.lblDayNamesShort
			};
			
			var options = {
					
				ikepPlanner : ikepPlanner,
				calOption : calOption
			};
			
			setting = $.extend({}, options);
			
			initTimeStr();
			
			setWorkspace();
			
			var paramId = $jq("#scheduleId").val();
			var url = home_url + "lightpack/meetingroom/reserve/getReserveData.do?scheduleId=" + paramId;

			$jq.getJSON(url, function(data){
				
				sco = data;
				
				var startDate;
				var endDate;
				
				if(sco) {
					
					startDate = new Date(sco.startDate);
					endDate = new Date(sco.endDate);
				} else {
					
					startDate = new Date($jq("#startDate").val());
					endDate = new Date($jq("#endDate").val());
				}
				var allDay = false;
				//editForm(startDate, endDate, false);
				
				var params = {
						
					start : startDate,
					end : endDate,
					allDay : allDay,
					sco : sco
				};
				
				editEvent(params);
			});
			
			
		});
		
		$jq("#btn_delete_ok").click(function(){
			if($jq("#delete-dialog input:checked").val()) {
				dialogButton = 1;
				$delete_dialog.dialog("close");
			} else {
				//alert(iKEPLang.planner.messageText.repeatDeleteOptionSelect);
				$delete_dialog.dialog("option", "title", iKEPLang.planner.messageText.repeatDeleteOptionSelect);
				$("span.ui-dialog-title").css("color", "yellow");
				setTimeout(function() {
					$delete_dialog.dialog("option", "title", iKEPLang.planner.messageText.repeatDeletDialogTitle);
					$("span.ui-dialog-title").css("color", "");
				}, 3000);
			}
		});
		
		$jq("#btn_delete_cancel").click(function(){
			/*$jq("#delete-dialog input:checked").each(function(){
				this.checked = false;
			});*/
			dialogButton = 0;
			$delete_dialog.dialog("close");
		});
		
		$delete_dialog.dialog({
			autoOpen: false,
			modal: true,
			resizable : false,
			title : iKEPLang.planner.messageText.repeatDeletDialogTitle,
			open: function () {
				dialogButton = 0;	// button click clear : close button with
				$jq("#delete-dialog input:checked").attr("checked", false);
			}
		});
		
	}
	
	var datepickerOptions = {
		dateFormat: "yy.mm.dd",
		showOn: "both",
		buttonImageOnly: true,
		buttonImage: iKEP.getContextRoot() + "/base/images/icon/ic_cal.gif"
	};
	
	function getTabName() {
		var tab;
		var index = $("#divTab").tabs("option", "selected");
		switch(index) {
			case 0 : tab = "place"; break;
			case 1 : tab = "day"; break;
			case 2 : tab = "week"; break;
			case 3 : tab = "recent"; break;
			case 4 : tab = "favorite"; break;
		}
		return tab;
	}
	
	function appendNthMon(rtype, nthMon) {
		return rtype === "yearly" ? "," + nthMon : "";
	}
	
	function lastDayOfMonth(d) {
		var cd = new Date(d.getFullYear(), d.getMonth() + 1, 0);
		return cd.getDate();
	}
	
	function getOptionMsg(sd, rtype) {
		var msg = "", data = [];

		var lastDate = lastDayOfMonth(sd);
		var dow = sd.getDay() + 1;		// Day of the week 값 oracle 기준(1 ~ 7)

		var ar = $.datepicker.formatDate("d-D-m", sd).split("-");
		var hw = Math.floor(( Number(ar[0] ) - 1)/7) + 1;
		
		var mon = rtype === "yearly" ? ar[2] + Message.labelText.month : "";
		
		msg =  roptionEl + mon + ar[0] + Message.labelText.date_1 + "&nbsp;&nbsp;";
		data.push("a," + ar[0] + appendNthMon(rtype,ar[2]));
		
		msg += roptionEl + mon + hw + Message.labelText.nth+ ' ' + ar[1] + Message.labelText.dow + "&nbsp;&nbsp;";
		data.push("b," + hw + "," + dow + appendNthMon(rtype,ar[2]));
		
		var isLastWeek = ( Number(ar[0]) + 7 ) > lastDate;
		if(isLastWeek) {
			msg += roptionEl + mon + Message.labelText.last + ' ' + ar[1] + Message.labelText.dow + "&nbsp;&nbsp;";
			data.push("c," + dow + appendNthMon(rtype,ar[2]));
		}
		
		if(ar[0] == lastDate) {
			msg += roptionEl + mon + Message.labelText.lastDate;
			data.push("d" + appendNthMon(rtype,ar[2]));
		}
		
		return {
			msg	: msg,
			op	: data
		};
	}
	
//	function editForm(start, end, allDay) {
//		
//		var params;
//			
//		params = {
//				
//			start : start,
//			end : end,
//			allDay : allDay,
//			sco : sco
//		};
//		
//		editEvent(params);
//		
//	}
	
	// event 삭제
	function deleteEvent(event, isRef) {	// isRef, 참여/참조자 여부
		var params = {};
		
		if(event.recurrences[0].repeatType) {	// 반복일정		
			
			$delete_dialog.dialog("open");
			
			$delete_dialog.dialog({
				close: function(e, ui) {
					if(dialogButton == 1) {
						dialogButton = 0;
						var wh = $jq("#delete-dialog input:checked").val();
						if(wh == 1 || wh == 2 || wh == 3){
							params.deleteType = wh;
							params.eventInfo = convertEventInfo(event);
							_deleteEvent(params, isRef);		
						}
					}
				}
			});				
		} else {	// 단순일정
			params.eventInfo = {scheduleId: event.scheduleId};
			if(confirm(iKEPLang.planner.messageText.confirmDeleteSchedule)) 
				_deleteEvent(params, isRef);
		}
	}
	
	function _deleteEvent(params, isRef) {
		params.sendmail = isRef;
		
		$mgmtPanel.ajaxLoadStart();
		
		var url = home_url + "lightpack/meetingroom/reserve/deleteReserve.do";
				
		$.postJSON(url, params, function(res) {		
			
			$("#date").val($("#start-date-picker").val());
			
			var meetingRoomId = $("#meetingRoomId").val();
			
			destroy();
			
			$mgmtPanel.ajaxLoadComplete();
			
			var url = home_url + "lightpack/meetingroom/" + getTabName() + "/meetingRoomMain.do";
			
			$jq.ajax({     
				
				url : url,    
				data :  {
					
					meetingRoomId : meetingRoomId,
					buildingId : $("building").val(),
					floorId : $("floor").val()
				},     
				type : "post",     
				loadingElement : {
					
					container : "#tabs"
				},
				success : function(result) {
					
					$jq("#tabs").html(result);
				},
				error : function(event, request, settings) { 
					
					alert("error"); 
				}
			});
			
			//$mgmtPanel.trigger("onComplete");
		});
		
	}
	
	function convertEventInfo(einfo) {
		return {				
			scheduleId: einfo.scheduleId,
			startDate: einfo.startDate,
			endDate: einfo.endDate,
			repeatType: einfo.recurrences[0].repeatType,
			repeatPeriod: einfo.recurrences[0].repeatPeriod,
			repeatPeriodOption: einfo.recurrences[0].repeatPeriodOption,
			repeatStartDate: einfo.startDate,
			repeatEndDate: einfo.endDate,
			updateStart: einfo.updateStart,
			updateEnd: einfo.updateEnd,
			wholeday: einfo.wholeday
		};
	}
	
	function editEvent(event) {
		
		schedule = $.extend({}, event);

		populateEvent(event);
	}
	
	function populateEvent(event) {
		//$("#"+mgmtEditTemplateId).tmpl({}).appendTo($mgmtPanel);
		setStartDatepicker(event);
		setEndDatepicker(event);
		setTime(event);		
		setCategory(event);
		setAlarm(event);
		setWorkspaceInfo(event);

		if(sco && event.sco.scheduleId) {
			$(".btn_save span").html(Message.btnText.save);
			setPublicOption(event);
			setRepeatTypeOption(event);
			setAttendanceRequest(event);
			setTitle(event);
			//setPlace(event);
			setContents(event);
			setParticipant(event);
			setFileInfo(event); // initFileController 실행
		} else {
			initFileController();
			$(".btn_delete").hide();
		}
		
		//renderTeam(event);
		initValidator();
		//$mgmtPanel.show();
		scrollTop();
		//$("#title").focus();
	}
	
	function initTimeStr() {
		var d = new Date();
		var value, text, fs;
		var startBuff = [];
		var endBuff = [];
		
		var minTime = setting.calOption.minTime.split(":");
		var maxTime = setting.calOption.maxTime.split(":");
		var endTime = (maxTime[0]-minTime[0])*2;
		if(Number(maxTime[1]) > 0){
			endTime++;
		}
		
		d.setHours(minTime[0], minTime[1], 0, 0);  // 시간, 분, 초, 밀리초
		for(var i=0; i<endTime; i++) {
			
			fs = formatDate(d, "tt,hh,mm,HH").split(",");
			value = "".concat(fs[3], fs[2]);
			text  = "".concat(fs[3], ":", fs[2]);
			
			startBuff.push("<option value=" + value + ">" + text + "</option>");				
			addMinutes(d, 30);
		}
		d.setHours(minTime[0], minTime[1], 0, 0);  // 시간, 분, 초, 밀리초
		for(var i=0; i<=endTime; i++) {
			fs = formatDate(d, "tt,hh,mm,HH").split(",");
			value = "".concat(fs[3], fs[2]);
			text  = "".concat(fs[3], ":", fs[2]);
			
			endBuff.push("<option value=" +value+">" + text + "</option>");				
			addMinutes(d, 30);
		}	
		startTimeOptionStr = startBuff.join("");
		endTimeOptionStr = endBuff.join("");
	}
	
	function setStartDatepicker(event) {
		var maxDate = new Date(today.getTime());
		maxDate.setMonth(maxDate.getMonth()+meetingRoomConfig.maxReservationMonth);
		$("#start-date-picker")
		.datepicker($.extend({},datepickerOptions, {
			minDate : today,
			maxDate : maxDate,
			onSelect: function(dateText, inst) {
				$("#end-date-picker").datepicker("option", "minDate", $(this).val());
				$("#end-time-pick").trigger("changeTime");
			}
		}))
		.datepicker("setDate", event.start)
		.next("img").addClass("dateicon");
	}
	
	function setEndDatepicker(event) {
		/*
		$("#end-date-picker")
		.datepicker($.extend({},datepickerOptions, {
			minDate: $("#start-date-picker").val(),
			onSelect: function() {
				$("#end-time-pick").trigger("changeTime");
			}
		}))
		.datepicker("setDate", event.end)
		.next("img").addClass("dateicon");
		*/
		$("#end-date-picker")
		.datepicker($.extend({}, {
			minDate: $("#start-date-picker").val(),
			onSelect: function() {
				$("#end-time-pick").trigger("changeTime");
			}
		}))
		.datepicker("setDate", event.end);
	}
	
	function setTime(event) {
		$(startTimeOptionStr).appendTo("#start-time-pick");
		$(endTimeOptionStr).appendTo("#end-time-pick");

		var stv = formatDate(event.start, "HHmm");
		var etv = formatDate(event.end, "HHmm");
		var stvh = formatDate(event.start, "HH");
		var stvm = formatDate(event.start, "mm");
		var etvh = formatDate(event.end, "HH");
		var etvm = formatDate(event.end, "mm");
		var isFirst = event.sco ? true : false;
		
		$("#startDueTimeHour").val(stvh);
		$("#startDueTimeMinute").val(stvm);
		$("#endDueTimeHour").val(etvh);
		$("#endDueTimeMinute").val(etvm);
		
		$("#date").val($("#start-date-picker").val());
					
		$("#start-time-pick")
			.val(stv)
			.bind("change", function(e) {
				var endTime = Number(this.value) + 30;
				$("#end-time-pick").trigger("changeTime", endTime);
			});
		
		$("#end-time-pick")
			.val(etv)
			.bind("changeTime", function(e, v) {
				var sd = $("#start-date-picker").datepicker("getDate");
				var ed = $("#end-date-picker").datepicker("getDate");
				var st = $("#start-time-pick").val();
				var sdt,uedt,uet,ued;					
				
				if( (sd - ed) === 0 ) {
					var sdt = st.substring(0,2) * 60 + (+st.substring(2,4)); // 시작 타임을 분으로 환산
					var uedt;
					
					if(isFirst) {
						uedt = addMinutes(cloneDate(sd), sdt + 5);  // 수정 모드
						isFirst = false;
					} else {							
						uedt = addMinutes(cloneDate(sd), sdt + 5);  // 신규 생성 모드, 1시간 더함
					}
					var uet = formatDate(uedt, "HHmm");
					
					var ued = clearTime(cloneDate(uedt));

					$("option", this)
						.attr("disabled", false)
						.filter(function(i) {
							return this.value < uet;
						})
						.attr("disabled", true);		
					
					$(this).val(v);
					
					$("#end-date-picker").datepicker("setDate", ued);
					//.next("img").addClass("hidden");
				} else {
					$("option", this)
					.attr("disabled", false);
				}
				
				/*
				 * 반복 일정
				if($("#repeat").get(0).checked && $("#repeatType").val() === "weekly") {
					for(var i=0; i<$("#roption-span").find("input:checkbox").length; i++) {
						if($("#roption-span").find("input:checkbox").get(i).disabled) {
							$("#roption-span").find("input:checkbox").get(i).disabled = false;
							$("#roption-span").find("input:checkbox").get(i).checked = false;
						}
					}
					
					$("#roption-span").find("input:checkbox:eq(" +
							sd.getDay() + ")").attr("checked", true);
					$("#roption-span").find("input:checkbox:eq(" +
							sd.getDay() + ")").attr("disabled", true);
				}
				*/
			})
			.trigger("changeTime", etv);
	}
	
	function setPublicOption(event) {
		$("#schedulePublic").attr("checked", (event.sco.schedulePublic == 1)  ? true : false);
	}
	
	function setRepeatTypeOption(event) {
		var obj = event.sco.recurrences[0];
		var d;
		
		if(obj && obj.repeatType) {
			$("#repeat").attr("checked", true).click();

			$.each(repeatTypeCnvt, function(n, v) {
				if(obj.repeatType == v) {
					$("#repeatType").val(n);
					$("#repeatType").change(); 
				}
			});
			d = getLastRepeatEndDate(obj); 
			
			if(d - FOREVER_DATE === 0) {
				//$("#forever").attr("checked", true);
				$("#endRepeatDate").datepicker("disable");
			} else {
				$("#endRepeatDate").datepicker("setDate", d);
			}
			
			if($("#repeatType").val() == "monthly" || $("#repeatType").val() === "yearly") {
				idx = radioIndexStr.indexOf(obj.repeatPeriodOption.charAt(0), 0);
				$("#roption-span input")[idx].checked = true;
				//$("#roption-span").data("roption", obj.repeatPeriodOption);
			} else if($("#repeatType").val() == "weekly") {
				ar = obj.repeatPeriodOption.split(",");

				for(i=0, len=ar.length; i<len; i++) {
					idx = ar[i] - 1;
					$("#roption-span input")[idx].checked = true;
				}
			}
			
			$("#count").val(obj.repeatPeriod);
			$("#repeat").attr("checked", true);
		}
	}
	
	function getLastRepeatEndDate(recurrence) {
		return (recurrence && new Date(recurrence.endDate)) || new Date();
	}
	
	function setAttendanceRequest(event) {
		$("#attendanceRequest")[0].checked = event.sco.attendanceRequest == 1 ? true : false;
	}
	
	function setTitle(event) {
		$("#title").val(event.sco.title);
	}
	
	function setContents(event) {
		$("#contents").val(event.sco.contents);
	}
	
	// 일정 수정시 참여/참조자 정보 셋팅
	function setParticipant(event) {
		var pl = event.sco.participantList, option;
		var participants = [], referers = [];

		if(pl) {
			var isLocaleEqual = setting.ikepPlanner.portalInfo.defaultLocaleCode == setting.ikepPlanner.userInfo.localeCode;
			$.each(pl, function(i, o) {
				var userInfo = {type:"user",
					empNo:"",
					id:this.targetUserId,
					userName:isLocaleEqual ? this.targetUserName : this.targetUserEnglishName,
					jobTitleName:isLocaleEqual ? this.targetUserJobTitleName : this.targetUserJobTitleEnglishName,
					group:"",
					teamName:isLocaleEqual ? this.targetUserTeamName : this.targetUserTeamEnglishName,
					email:this.mail,
					mobile:this.targetUserMobile
				};
				option = $.tmpl(iKEP.template.userOption, userInfo).data("data", userInfo);
				if(o.targetType == "1") {
					option.appendTo("#participants");
				} else if(o.targetType == "2") {
					option.appendTo("#referers");
				}
			});
			updateCount("participants");
			updateCount("referers");
		}
	}
	
	function setCategory(event) {
		var categoryList = setting.ikepPlanner.categoryList;
		var options = [], s, defaultOption;		
		$.each(categoryList, function(k, o) {
			s = "<option value=" + o.categoryId;	
			if(event.sco && o.categoryId == event.sco.categoryId) {
				s += " selected='selected' ";
			}
			s += ">" + o.categoryName + "</option>";
			if(o.categoryId == '1') {
				defaultOption = s;
			} else {					
				options.push(s);
			}
		});
		options.unshift(defaultOption);
		$(options.join("")).appendTo("#category");
	}
	
	function setAlarm(event) {
		var al = event.sco ? event.sco.alarmList : [];
		var el;
		
		if(al.length === 0) {
			appendAlarm();
		} else {
			$.each(al, function(i, o) {
				el = appendAlarm();
				el.find("select").val(o.alarmTime)
				.end()
				.find("input[value=" + o.alarmType + "]")
				.attr("checked", true);
			});
			adjustAlarmRadio();
		}
	}
	
	function setWorkspace() {
		var url = home_url + "collpack/collaboration/workspace/myWorkspace.do?userId=" + owner.userId;
		$.getJSON(url, function(data) {
			if(data.length > 0) {
				$.each(data, function(idx, item) {
					$("<option value=" +item.workspaceId+">" + item.workspaceName + "</option>")
					.appendTo("#team-sel");
				});
			}
		});
	}
	
	function setWorkspaceInfo(event) {
		if(event.sco && event.sco.workspaceId) {
			$("#team-sel").val(event.sco.workspaceId);
		}
	}
	
	function getAlarmList() {
		var res = [], t;

		$(".alarm-item input:radio:checked").each( function(){
			t = $(this).closest("div.alarm-item").find("select").val();
			if(t != "0") {
				res.push( {alarmType : this.value, alarmTime : t} );
			}
		} );
		return res;
	}
	
	// uid가 login user이거나 login user가 수임자인지 체크 : 둘다 아니면 true
	function valideParticipantUser(uid) {
		var isValide;
//		var mid = schedule.currentSource.mandatorId;
//		if(mid) {
//			isValide = mid != uid; 
//		} else {
			isValide = uid != owner.userId;
//		}
		return isValide;
	}
	
	function searchUser(event, selId) {	// append
		var result, msg;
		var $select = $("#" + selId);

		iKEP.searchUser(event, "Y", function( data ) {
			if(data && data.length > 0) {				
				$(event.target).val("");
				
				result = normalizeSearchedUser(data);
				$.each(result.newUsers || [], function(i, o) { 		
					if(valideParticipantUser(this.id)) {
						$.tmpl(iKEP.template.userOption, this).data("data", this)
							.appendTo($select);
					} else {
						msg = Message.messageText.noAddOwnerToParticipant;
						setTimeout( "alert('" + msg + "')", 100 );	
					}
				});
				
				updateCount(selId);
				if(result.overlapUsers.length > 0) {		
					msg = "[" + result.overlapUsers.join(",") + "] " + iKEPLang.searchUserGroup.duplicateUser;
					setTimeout( "alert('" + msg + "')", 100 );		
				}
			} else {
				alert(Message.messageText.noUser);
			}
		});
	}
	
	function searchUserByAddressbook(event, selId) {	// replace
		var items = [];
		$("#" + selId).children().each(function() {
			items.push($(this).data("data"));
		});
		
		iKEP.showAddressBook(function(data) {
			var result;
			if(data && data.length > 0) {				
				result = normalizeSearchedUser(data, selId);
				$("#" + selId).empty();
				$.each(result.newUsers || [], function(i, o) { 		
					if(valideParticipantUser(this.id)) {
						$.tmpl(iKEP.template.userOption, this).data("data", this)
							.appendTo($("#" + selId));
					} else {
						msg = Message.messageText.noAddOwnerToParticipant;
						setTimeout( "alert('" + msg + "')", 100 );	
					}
				});
				updateCount(selId);
				if(result.overlapUsers.length > 0) {					
					var msg = "[" + result.overlapUsers.join(",") + "] " + iKEPLang.searchUserGroup.duplicateUser;
					setTimeout( "alert('" + msg + "')", 100 );				
				}
			}
		}, items, {selectType:"user",tabs:{common:1,personal:1}});
	}
	
	function normalizeSearchedUser(users, selId) {
		var newUsers = [], overlapUsers = [];
		var oldUsers = [];
		var $options = selId ? $("option", "#" + (selId == "participants" ? "referers" : "participants")) : $("#participants option, #referers option");
		$options.each(function(idx, item) {
			oldUsers.push(item.value);
		});
		
		newUsers = $.map(users, function(user) {
			if($.inArray(user.userId || user.id, oldUsers) == -1) {
				return user;
			} else {
				overlapUsers.push(user.userName || user.name);
				return null;
			}
		});
		
		return {newUsers: newUsers, overlapUsers: overlapUsers};
	}
	
	function getParticipantList() {
		var res = [];
		$("#participants option").each(function() {
			res.push({
				scheduleId: schedule.id || "",
				targetUserId: this.value,
				targetType: 1
			});
		});
		$("#referers option").each(function() {
			res.push({
				scheduleId: schedule.id || "",
				targetUserId: this.value,
				targetType: 2
			});
		});
		return res;
	}
	
	function existParticipant() {
		if($("input[name=mailSendYn]").is(":checked") && $("#participants")[0].options.length > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	function normalizeFile(files) {
		var nf = [];

		$.each(files, function() {
			nf.push({fileId: this.fileId, flag: this.flag});
		});
		
		return nf;
	}
	
	// 알람
	var idxObj = { idx : 1 };
	function appendAlarm() {
		var el;
		
		idxObj.idx ++;

		el = $("#alarmTemplate").tmpl( idxObj ).appendTo("#alarm-div");
		adjustAlarmRadio();
		return el;
	}
	
	function adjustAlarmRadio() {

		$("#alarm-div .alarm-item:eq(0)").css("margin-top", "0px");
		$("#alarm-div .alarm-item:gt(0)").css("margin-top", "8px");
	}
	
	function destroy() {
		$(".date-pick").datepicker("destroy");
		$mgmtPanel.empty();
	}
	
	// event 생성
	function createEvent(event, isRef) {
		if(!checkReserveTime()) return false;
		
		event.sendmail = isRef; 
		
		var url = home_url + "lightpack/meetingroom/reserve/createReserve.do";
		
		$mgmtPanel.ajaxLoadStart();
		
		$.postJSON(url, event, function(res) {
			if(res.success == 'success') {
				var startDate =$("#start-date-picker").datepicker("getDate");
				destroy();
	
				$mgmtPanel.ajaxLoadComplete();				
				
				var url = home_url + "lightpack/meetingroom/" + getTabName() + "/meetingRoomMain.do";
				$("#tabs").load(url, {
					meetingRoomId : event.meetingRoomId,
					buildingId : currentBuildingId,
					date : startDate.getTime()
				});
			} else if(res.success == 'duplicate') {
				alert(Message.messageText.duplicateReserve);
				$mgmtPanel.ajaxLoadComplete();	
			} else {
				alert("error");
				$mgmtPanel.ajaxLoadComplete();	
			}
		});
	}
	
	// event 수정
	function updateEvent(newSchedule, isRef) {
		if(!checkReserveTime()) return false;
		
		var params = {};//{sendmail: newSchedule.sendmail || false};
		var url = home_url + "lightpack/meetingroom/reserve/updateReserve.do";
		
		if(schedule.sco.repeatType) { // 반복일정
			
			$update_dialog.dialog("open");
			$update_dialog.dialog({
				close: function(e, ui) {
					if(dialogButton == 1) {
						dialogButton = 0;
						
						params.sendmail = isRef;
						
						var wh = $jq("#update-dialog input:checked").val();
						if(wh == 1 || wh == 2 || wh == 3){
							params.updateType = wh;
						} else {
							return;
						}

						params.newSchedule = newSchedule;
						if(wh == 2 || wh == 3) {
							params.isDirtyRepeat = isDirtyRepeat(schedule.sco, params.newSchedule) ? "yes" : "no";
						}			

						if(wh == 3) {
							if(newSchedule.recurrences.length > 0) {
								//newSchedule.startDate = new Date(schedule.sco.recurrences[0].sdStartDate);
								//newSchedule.endDate = new Date(schedule.sco.recurrences[0].sdEndDate);
								newSchedule.recurrences[0].startDate = clearTime(cloneDate(newSchedule.startDate));
								newSchedule.recurrences[0].sdStartDate = newSchedule.startDate;
								newSchedule.recurrences[0].sdEndDate = newSchedule.endDate;	
							}
						}
						
						
						schedule.sco.updateStart = +newSchedule.startDate;
						schedule.sco.updateEnd = +newSchedule.endDate;												
						
						params.eventInfo = convertEventInfo(schedule.sco);
						
						$mgmtPanel.ajaxLoadStart();
						
						$.postJSON(url, params, function(res) {
							var startDate =$("#start-date-picker").datepicker("getDate");
							destroy();
							
							$mgmtPanel.hide();
							$mgmtPanel.ajaxLoadComplete();
							
							var url = home_url + "lightpack/meetingroom/" + getTabName() + "/meetingRoomMain.do";
							$("#tabs").load(url, {
								meetingRoomId : event.meetingRoomId,
								buildingId : currentBuildingId,
								date : startDate.getTime()
							});
						});								
					}
				}
			});		
		} else {
			
			params.sendmail = isRef;
			params.newSchedule = newSchedule;
			
			$mgmtPanel.ajaxLoadStart();
			
			$.postJSON(url, params, function(res) {
				if(res.success == 'success') {
					var startDate =$("#start-date-picker").datepicker("getDate");
					destroy();
					
					$mgmtPanel.ajaxLoadComplete();
					$mgmtPanel.hide();
					
					var url = home_url + "lightpack/meetingroom/" + getTabName() + "/meetingRoomMain.do";
					$("#tabs").load(url, {
						meetingRoomId : event.meetingRoomId,
						buildingId : currentBuildingId,
						date : startDate.getTime()
					});
				} else if(res.success == 'duplicate') {
					
					alert(Message.messageText.duplicateReserve);
					
					$mgmtPanel.ajaxLoadComplete();	
				} else {
					
					alert("error");
					
					$mgmtPanel.ajaxLoadComplete();	
				}
			});
		}
	}
	
	function checkReserveTime() {
		var startDate = $("#start-date-picker").val();
		var startTime = $("#startDueTimeHour").val()+ $("#startDueTimeMinute").val();
		var endTime = $("#endDueTimeHour").val()+ $("#endDueTimeMinute").val();
		
		if(startTime >= endTime){
			alert(Message.messageText.checkTimePick);
			return false;
		}
		
		var currDate = iKEP.getCurTime();
		
		var reserveTime = new Date(startDate.replace(/\./g, "-"));
		reserveTime.setHours(startTime.substring(0, 2), startTime.substring(2, 4)-meetingRoomConfig.minReservationTime);
		if(reserveTime < currDate) {
			if(Math.abs((reserveTime - currDate)/(60*1000)) < meetingRoomConfig.minReservationTime)
				alert(langMsg.noReserveReadyTime.replace("%w", meetingRoomConfig.minReservationTime));
			else 
				alert(langMsg.noReservePastTime);
			return false;
		}
		
		return true;
	}
	
	function updateCount(target) {
		var ds = Message.labelText.totalCountDesc.replace("{}", $("#" + target)[0].options.length);
		$("#" + target + "_count").html(ds);
	}
	
	function setFileInfo(event) {
		var files = event.sco.fileDataList;
		var options = {};

		if(files && files.length > 0) {
			options.files = [];
			$.each(files, function() {
				options.files.push({
					fileId: this.fileId, 
					fileRealName: this.fileRealName, 
					fileSize: this.fileSize, 
					fileExtension: this.fileExtension
				});
			} );
		}
		initFileController(options);
	}
	
	function initFileController(options) {
		fileController = new iKEP.FileController("#fileUploadForm", "#fileUploadArea", 
				$.extend({}, uploaderOptions, options));
	}
	
	function initValidator() {
		
		validatorObj = new iKEP.Validator("#fileUploadForm", {
			
			rules: {
				title: {
					required: true,
					maxlength: 300
				},
				count: {
					required : function() { return $("input[name=repeat]", "#fileUploadForm").is(":checked"); },	// 반복설정인 경우 반복주기를 반드시 입력하도록 함.
					min : 1
				}
			},
			messages: {
				title: {
					direction : "bottom",
		            required : Message.messageText.inputTitle,
					maxlength : Message.messageText.maxTitle
				},
				count: {
					direction : "bottom",
					required : Message.messageText.inputCycle,
					min : Message.messageText.inputCycleDigits
				}
			},
			submitHandler : save
		});
	}
	
	function scrollTop() {
		
		$jq("html, body").scrollTop(0);
	}
	
	function getData() {
		
		var recurrence,repeatType,rt, repeatPeriodOption = "",ropIdx,
			ta = [], roption, alarmlist = [], repeat, rptStart, rptEnd,
			sdstart = $.toDate( $("#start-date-picker").val() + $("#startDueTimeHour").val()+ $("#startDueTimeMinute").val() ),
			sdend = $.toDate( $("#end-date-picker").val() + $("#endDueTimeHour").val()+ $("#endDueTimeMinute").val() );
	
		
		sdstart = sdstart; //addTimeDifference(sdstart);
		sdend = sdend; //addTimeDifference(sdend);

		rt = $("#repeatType").get(0).value;
		repeatType = repeatTypeCnvt[rt];
		repeat = $("#repeat:checked").size();
		switch(rt) {
			case "daily":
				repeatPeriodOption = "";
				break;
			case "weekly":
				$(".weekly-dow:checked").each(function(){
					ta.push(this.value);
				});
				
				repeatPeriodOption = ta.join(",");
				if(repeatPeriodOption.length == 0) {
					alert(iKEPLang.planner.messageText.checkRepeatDay);
					return null;
				}
				break;
			case "monthly":
			case "yearly":
				ropIdx = $(".monthly-rop:checked").index(".monthly-rop");
				roption = $("#roption-span").data("roption");
				repeatPeriodOption = roption[ropIdx];
				break;
		}
		
		var mandatorId;
		var scheduleId;
		
		if(schedule.currentSource) {
			mandatorId = schedule.currentSource.mandatorId;
		}
		
		if(sco && sco.scheduleId != null) {
			scheduleId = sco.scheduleId;
		}
		
		var newSchedule = {
				scheduleId			: scheduleId,
				scheduleType		: "",
				startDate			: sdstart,
				endDate				: sdend,
				title				: $("#title").val(),
				place				: $("#place").val(),
				contents			: $("#contents").val(),
				attendanceRequest	: $("#attendanceRequest:checked").size(),
				categoryId			: $("#category").val(),
				categoryName		: "",
				schedulePublic		: $("#schedulePublic:checked").size(),		// 일정 공개 여부 ( 0 : 공개일정, 1 : 비공개일정)
				alarmRequest		: 0,											// 알람 요청 여부 ( 0 : 미요청, 1 : 요청)
				repeat				: repeat,
				workspaceId			: $("#team-sel").val(),
				recurrences			: [],									// 반복일정
				alarmList			: getAlarmList(),
				participantList		: getParticipantList(),
				mandatorId			: mandatorId,
				meetingRoomId		: $("#meetingRoomId").val(),
				cartooletcId		: $("#cartooletcId").val()
		};
		
		if(repeat) {
			rptStart = clearTime(cloneDate(sdstart));
			if($("#forever:checked").size() == 1) {
				rptEnd = FOREVER_DATE;
			} else {
				rptEnd = $("#endRepeatDate").datepicker("getDate");
			}
			
			recurrence = {
					repeatType			: repeatType,
					repeatPeriod		: $.trim($("#count").val()),
					repeatPeriodOption	: repeatPeriodOption,
					startDate			: rptStart,
					endDate				: rptEnd,
					sdStartDate			: sdstart,
					sdEndDate			: sdend
			};
			
			newSchedule.recurrences.push(recurrence);
		}
		
		return newSchedule;
	}
	
	// save
	function save() {
		
		var title = $("#title").val();
		var newSchedule;
		
		newSchedule = getData();			
		
		if(newSchedule) {
			
			fileController.upload(function(isSuccess, files) {	
				
				if(isSuccess === true) {
					
					var isRef = existParticipant();
					
					newSchedule.fileLinkList = normalizeFile(files || []);
					
					if(newSchedule.scheduleId) {
						
						updateEvent(newSchedule, isRef);
					} else {			
						
						createEvent(newSchedule, isRef);
					}	
				} else {
					
					alert(iKEPLang.planner.errorText.failUpload);
				}
			});
		}
	}
	
	function view(scheduleId) {    
		
		var url  = home_url + "lightpack/meetingroom/reserve/reserveView.do";
		
		$jq.ajax({     
			
			url : url,    
			data : {
				
				scheduleId : scheduleId,
				dialog : 1
			},     
			type : "post",     
			loadingElement : {
				
				container : "#tabs"
			},
			success : function(result) {       
				
				$jq("#tabs").html(result);
			},
			error : function(event, request, settings) { 
				
				alert("error"); 
			}
		}); 
		
	};
	
	function setToolReserve() {
		$("#tool").attr("readonly", true);
		
		$("#btnToolCancel").show();
	}
	
	function cancel() {
		
		var meetingRoomId = $("#meetingRoomId").val();
		
		destroy();
		
		$mgmtPanel.hide();
		
		var url;

		if(sco && sco.scheduleId.length > 0) {
			
			url = home_url + "lightpack/meetingroom/reserve/reserveView.do";
			
			$jq.ajax({     
				
				url : url,    
				data :  {
					
					scheduleId : sco.scheduleId,
					dialog : 1
				},     
				type : "post",     
				loadingElement : {
					
					container : "#tabs"
				},
				success : function(result) {
					
					$jq("#tabs").html(result);
				},
				error : function(event, request, settings) { 
					
					alert("error"); 
				}
			});
		} else {
			
			url = home_url + "lightpack/meetingroom/" + getTabName() + "/meetingRoomMain.do";
			
			$jq.ajax({     
				
				url : url,    
				data :  {
					
					meetingRoomId : meetingRoomId,
					buildingId : $("building").val(),
					floorId : $("floor").val()
				},     
				type : "post",     
				loadingElement : {
					
					container : "#tabs"
				},
				success : function(result) {
					
					$jq("#tabs").html(result);
				},
				error : function(event, request, settings) { 
					
					alert("error"); 
				}
			});
		}
		
		$mgmtPanel.trigger("onUpdateCancel");
	}
	
	$jq(document).ready(function() {
		
		init();
		
		
	});
})(jQuery);