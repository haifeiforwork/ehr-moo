( function($) {
	function eventMgmt(options) {
		var setting = $.extend({}, options);
		var fc = $.fullCalendar;
		var formatDate = fc.formatDate;
		var parseDate = fc.parseDate;
		var clearTime = fc.clearTime;
		var dayDiff = fc.dayDiff;
		var cloneDate = fc.cloneDate;
		var addDays = fc.addDays;
		var addMonths = fc.addMonths;
		var addYears = fc.addYears;
		var addMinutes = fc.addMinutes;
		
		var FOREVER_DATE = new Date(9999, 11, 31);
		var ignoreTimeDifference = setting.ignoreTimeDifference || false;
		var timeDifference = !ignoreTimeDifference ? setting.ikepPlanner.userInfo.timeDifference : 0;

		var owner = setting.ikepPlanner.userInfo;
		var mandatorList = setting.ikepPlanner.mandatorList;
		var userLocale = owner.localeCode;
		var portalLocale = setting.ikepPlanner.portalInfo.defaultLocaleCode;
		var Message = iKEPLang.planner;
		
		var messageConfirmMail = Message.messageText.doSendMail;
		var messageShowDetail = Message.messageText.showDetail;
		var host = window.location.host || "";
		var home_url = iKEP.getContextRoot() + "/";	
		var baseUrl = home_url + "lightpack/planner/calendar/"; //2014.09.02 WebDiary에서 일정 등록 하기 위해 추가
		var viewUrl = host + iKEP.getContextRoot() + "/lightpack/planner/calendar/viewSchedule.do?docPopup=true&scheduleId=";
		var myWorkspaceUrl = iKEP.getContextRoot() + "/collpack/collaboration/workspace/myWorkspaceMenu.do?userId=";
		var fileDownloadUrl = host + iKEP.getContextRoot() + '/support/fileupload/downloadFile.do?thumbnailYn=N&fileId=';
		var imagePath = iKEP.getContextRoot() + "/base/images/icon/ic_attach.gif";
		var validatorObj;
		
		var mgmtPanelId = "mgmt-panel";
		var $mgmtPanel = $("#"+mgmtPanelId);
		var mgmtEditTemplateId = "mgmtpanelTemplate";
		var mgmtEditTemplate = "mgmtpanelTemplate";
		var regReplaceNewLine = /(\r\n|\r|\n)/g;
		
		
		var initialized = false;
		var schedule = null;
		
		var datepickerOptions = {
				dateFormat: "yy.mm.dd",
				showOn: "both",
				buttonImageOnly: true,
				buttonImage: iKEP.getContextRoot() + "/base/images/icon/ic_cal.gif"
		};
		
		var startTimeOptionStr;
		var endTimeOptionStr;
		
		var recurrenceData = {
				"daily" 	: {freq:"daily",   freqStr: Message.labelText.daily},
				"weekly" 	: {freq:"weekly",  freqStr: Message.labelText.weekly,
								dayNames: iKEPLang.datepicker.lblDayNames},
				"monthly" 	: {freq:"monthly", freqStr: Message.labelText.monthly},
				"yearly" 	: {freq:"yearly", freqStr: Message.labelText.yearly}
		};
		
		var repeatTypeCnvt = {"daily": 1, "weekly": 2, "monthly": 3, "yearly": 4};
		
		var radioIndexStr  = "abcd";
		
		var roptionEl = '<input type="radio" name="rpoption" class="monthly-rop"/>';
		
		var $update_dialog = $jq("#update-dialog");
		var $delete_dialog = $jq("#delete-dialog");
		
		var fileController;
	    var uploaderOptions = {// 사용할 수 있는 옵션 항목
	            isUpdate : true,    // 등록 및 수정일때 true
	            initActive: true
	        };
	    
	    var dialogButton = 0;	//0-cancel, 1-ok
		
		function setStartDatepicker(date, isMeetingRoom) {
			var curDate = iKEP.getCurTime();
			curDate.setHours(0, 0, 0, 0);
			
			var option = {};
			if(isMeetingRoom) {
				option.minDate = curDate;
				option.maxDate = addMonths(cloneDate(curDate), meetingRoomConfig.maxReservationMonth);
			}
			
			$("#start-date-picker")
			.datepicker($.extend(option, datepickerOptions, {
				onSelect: function(dateText, inst) {
					$("#end-date-picker").datepicker("option", "minDate", $(this).val());
					$("#end-time-pick").trigger("changeTime");
				}
			}))
			.next("img").addClass("dateicon");
			
			if(date) {
				var cDate = cloneDate(date);
				$("#start-date-picker").datepicker("setDate", (isMeetingRoom && cDate.setHours(0, 0, 0, 0) < curDate) ? curDate : cDate);
			}
		}
		
		function setEndDatepicker(date, isMeetingRoom) {
			var curDate = iKEP.getCurTime();
			curDate.setHours(0, 0, 0, 0);
			
			var option = {};
			if(isMeetingRoom) {
				option.minDate = curDate;
				option.maxDate = addMonths(cloneDate(curDate), meetingRoomConfig.maxReservationMonth);
			}
			
			$("#end-date-picker")
			.datepicker($.extend(option, datepickerOptions, {
				minDate: $("#start-date-picker").val(),
				onSelect: function() {
					$("#end-time-pick").trigger("changeTime");
				}
			}))
			.next("img").addClass("dateicon");
			
			if(date) {
				var cDate = cloneDate(date);
				$("#end-date-picker").datepicker("setDate", (isMeetingRoom && cDate.setHours(0, 0, 0, 0) < curDate) ? curDate : cDate);
			}
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
			
			$("#start-time-pick")
				.val(stv)
				.bind("change", function(e, isMeetingRoom) {
					var endTime = Number(this.value) + 30;
					$("#end-time-pick").trigger("changeTime", {endTime:endTime, isMeetingRoom:isMeetingRoom});
				});
			
			$("#end-time-pick")
				.val(etv)
				.bind("changeTime", function(e, param) {
					var sd = $("#start-date-picker").datepicker("getDate");
					var ed = $("#end-date-picker").datepicker("getDate");
					var st = $("#start-time-pick").val();
					var sdt,uedt,uet,ued;					

					if( (sd - ed) === 0 ) {
						var sdt = st.substring(0,2) * 60 + (+st.substring(2,4)); // 시작 타임을 분으로 환산
						var uedt;
						
						if(isFirst) {
							uedt = addMinutes(cloneDate(sd), sdt + 30);  // 수정 모드
							isFirst = false;
						} else {							
							uedt = addMinutes(cloneDate(sd), sdt + 30);  // 신규 생성 모드, 1시간 더함
						}
						var uet = formatDate(uedt, "HHmm");
						
						var ued = clearTime(cloneDate(uedt));

						$("option", this)
							.attr("disabled", false)
							.filter(function(i) {
								return this.value < uet;
							})
							.attr("disabled", true);		
						
						if(!$("#wholeday").get(0).checked && param && param.endTime) {
							$(this).val(param.endTime);
						}
						
						$("#end-date-picker").datepicker("setDate", ued);
					} else {
						$("option", this)
						.attr("disabled", false);
					}
					
					if(!param || !param.isMeetingRoom) {
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
					}
				})
				.trigger("changeTime", {endTime:etv});
		}
		
		function setAlldayOption(event) {
			if(event.allDay) {
				$("#wholeday").attr("checked", true);
				$(".time-pick").attr("disabled", true);
			}
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
				
				startBuff.push("<option value=" +value+">" + text + "</option>");				
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
			
//			$("#alarm-div input:radio:disabled").removeAttr("disabled");
//			
//			$("#alarm-div input:radio:checked").each(function() {
//				var that = this;
//				$("#alarm-div input:radio:not(:checked)").each(function() {
//					if(that.value == this.value) {
//						this.disabled = true;
//					}
//				});
//			});

			$("#alarm-div .alarm-item:eq(0)").css("margin-top", "0px");
			$("#alarm-div .alarm-item:gt(0)").css("margin-top", "8px");
		}
		
		function setAlarm(event) {
			var al = event.sco ? event.sco.alarmList : [];
			var el;
			
			if(al.length === 0) {
				appendAlarm();
				$("#alarm-div .alarm-minus").hide();
			} else {
				$.each(al, function(i, o) {
					el = appendAlarm();
					el.find("select").val(o.alarmTime)
					.end()
					.find("input[value=" + o.alarmType + "]")
					.attr("checked", true);
				});
				var alam_list =getAlarmList();
				if(alam_list.length==2){
					$("#alarm-div .alarm-plus").hide();
				}
				adjustAlarmRadio();
			}
		}
		
		function setPublicOption(event) {
			$("#schedulePublic").attr("checked", (event.sco.schedulePublic == 1)  ? true : false);
		}
		
		function setPrivateOption(event) {
			$("#schedulePrivate").attr("checked", (event.sco.schedulePrivate == 1)  ? true : false);
		}
		
		function getLastRepeatEndDate(sco) {
			var recur = sco.recurrences;
			return recur ? new Date(recur[recur.length -1].endDate) : new Date();
		}
		
		function setRepeatTypeOption(event) {
			var sco = event.sco;
			var d;
 
			if(sco && sco.repeatType) {
				$("#repeat").attr("checked", true).click();
				
				$.each(repeatTypeCnvt, function(n, v) {
					if(sco.repeatType == v) {
						$("#repeatType").val(n);
						$("#repeatType").change(); 
					}
				});
				d = getLastRepeatEndDate(sco); 

				if(d - FOREVER_DATE === 0) {
					$("#forever").attr("checked", true);
					$("#endRepeatDate").datepicker("disable");
				} else {
					$("#endRepeatDate").datepicker("setDate", d);
				}
				
				if($("#repeatType").val() == "monthly" || $("#repeatType").val() === "yearly") {
					idx = radioIndexStr.indexOf(sco.repeatPeriodOption.charAt(0), 0);
					$("#roption-span input")[idx].checked = true;
					//$("#roption-span").data("roption", sco.repeatPeriodOption);
				} else if($("#repeatType").val() == "weekly") {
					ar = sco.repeatPeriodOption.split(",");

					for(i=0, len=ar.length; i<len; i++) {
						idx = ar[i] - 1;
						$("#roption-span input")[idx].checked = true;
					}
				}
				
				$("#count").val(sco.repeatPeriod);
				$("#repeat").attr("checked", true);
			}
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
		
		function setCategory(event) {
			var categoryList = setting.ikepPlanner.categoryList;
			var options = [], s, defaultOption;		
			$.each(categoryList, function(k, o) {
				s = "<option value='" + o.categoryId+"' ";	
				if(event.sco && o.categoryId == event.sco.categoryId) {
					s += " selected='selected' ";
				}
				if(o.categoryId == '0') {
					s += ">" +"없음" + "</option>";
				}else{
					s += ">" + o.categoryName + "</option>";
				}
				if(o.categoryId == '0') {
					defaultOption = s;
				} else {					
					options.push(s);
				}
			});
			options.unshift(defaultOption);
			$(options.join("")).appendTo("#category");
		}
	
		function setCompanyArea(event) {
			//alert(event.sco.workAreaName);
			var options = [],s;		
			var workareaList =[{workAreaId:"", workAreaName:"전체"},
			                   {workAreaId:"본사", workAreaName:"본사"},
			                   {workAreaId:"진주", workAreaName:"진주"},
			                   {workAreaId:"울산", workAreaName:"울산"},
			                   {workAreaId:"대구", workAreaName:"대구"}];
			
			$.each(workareaList, function(k, o) {
				s = "<option value='" + o.workAreaId+"' ";	
				if(event.sco && o.workAreaId == event.sco.workAreaName) {
					s += " selected='selected' ";
				}
				s += ">" + o.workAreaName + "</option>";
				if(o.workAreaId == '') {
					defaultOption = s;
				} else {					
					options.push(s);
				}
			});
			options.unshift(defaultOption);
			$(options.join("")).appendTo("#companyArea");
		}
	
		
		function setAttendanceRequest(event) {
			$("#attendanceRequest")[0].checked = event.sco.attendanceRequest == 1 ? true : false;
		}
		
		function setTitle(event) {
			$("#title").val(event.title);
		}
		
		function setUpdateDisplay(event) {
			//alert(event.sco.updateDisplay);
			$("#updateDisplay").attr("checked", (event.sco.updateDisplay) ==1 ? true : false);
		}
		
		
		function setPlace(event) {
			$("#place").val(event.sco.place);
			if(event.sco.meetingRoomId) {
				$("#meetingRoomId").val(event.sco.meetingRoomId);
				setMeetingRoomReserve();
			}
		}
		
		function setTool(event) {
			$("#tool").val(event.sco.cartooletcName);
			if(event.sco.cartooletcId) {
				$("#cartooletcId").val(event.sco.cartooletcId);
				setToolReserve();
			}
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
		
		function alertDuplicate(msg) {
			alert(msg);
		}
		
		// uid가 login user이거나 login user가 수임자인지 체크 : 둘다 아니면 true
		function valideParticipantUser(uid) {
			var isValide;
			var mid = schedule.currentSource.mandatorId;
			if(mid) {
				isValide = mid != uid; 
			} else {
				isValide = uid != owner.userId;
			}
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
		
		function existParticipant() {
			if($("input[name=mailSendYn]").is(":checked") && ($("#participants")[0].options.length > 0 ||$("#referers")[0].options.length > 0 )) {
				return true;
			} else {
				return false;
			}
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
		
		function renderTeam(event) {
			var cs = event.currentSource;
			var mid = cs.mandatorId;
			var userId = owner.userId;
			
			if($("#leftMenu-pane").size() > 0 && !mid) { // left menu가 있는 경우 and 위임자 일정이 아닌 경우
				$("#team-pane li").each( function(i, n) {
					var item = $(this).data("workspace");
					$("<option value=" +item.workspaceId+">" + item.workspaceName + "</option>")
					.appendTo("#team-sel");
				} );			
				updateTeam(event);
			} else {
				getWorkspaceInfo(mid || userId, event);
			}
		}
		
		function getWorkspaceInfo(targetUserId, event) {
			$.getJSON(myWorkspaceUrl + targetUserId, function(data) {
				if(data.length > 0) {
					$.each(data, function(idx, item) {
						$("<option value=" +item.workspaceId+">" + item.workspaceName + "</option>")
						.appendTo("#team-sel");
					});
					updateTeam(event);
				}
			});
		}
		
		function updateTeam(event) {
			if(event.sco && event.sco.workspaceId) {
				var selector = "#team-sel option[value=" + event.sco.workspaceId + "]";
				$(selector).attr("selected", "selected");
			}
		}
		
		function addTimeDifference(d) {
			return timeDifference !== 0 ? addMinutes(cloneDate(d), timeDifference * 60) : cloneDate(d);
		}
		
		function addTime(d, t) {
			var ret = clearTime(cloneDate(d));
			ret.setHours(t.getHours());
			ret.setMinutes(t.getMinutes());
			ret.setSeconds(t.getSeconds()); 
			return ret;
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
		
		function getAlarmList() {
			var res = [], t;

			$(".alarm-item input:radio:checked").each( function(){
				t = $(this).closest("div.alarm-item").find("select").val();
				if(t != "0") {
					
					res.push( {alarmType : this.value, alarmTime : t} );
					//alert("alarmType:"+this.value+" alarmTime:"+t);
				}
			} );
			return res;
		}
		
		function normalizeFile(files) {
			var nf = [];

			$.each(files, function() {
				nf.push({fileId: this.fileId, flag: this.flag});
			});
			
			return nf;
		}
		
		function getData() {
			var recurrence,repeatType,rt, repeatPeriodOption = "",ropIdx,
				ta = [], roption, alarmlist = [], repeat, rptStart, rptEnd,
				sdstart = $.toDate( $("#start-date-picker").val() + $("#startDueTimeHour").val()+ $("#startDueTimeMinute").val() ),
				sdend = $.toDate( $("#end-date-picker").val() + $("#endDueTimeHour").val()+ $("#endDueTimeMinute").val() );
		
		
			sdstart = sdstart; //addTimeDifference(sdstart);
			sdend = sdend; //addTimeDifference(sdend);
			if($("#wholeday:checked").size() == "0"){
				if(sdstart > sdend){
					alert("종료시간을 다시 설정해주십시요.");
					return false;
				}else if(sdstart < sdend){
					//통과
				}else{
					alert("종료시간을 다시 설정해주십시요.");
					return false;
				}
			}
			
			rt = $("#repeatType").get(0).value;
			repeatType = repeatTypeCnvt[rt];
			repeat = $("#repeat:checked").size();
			
			switch(rt) {
				case "dailyly":
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
			//alert($("#meetingRoomId").val());

			var newSchedule = {
					scheduleId			: schedule.id,
					scheduleType		: "",
					startDate			: sdstart,
					endDate				: sdend,
					wholeday			: $("#wholeday:checked").size(),
					title				: $("#title").val(),
					place				: $("#place").val(),
					contents			: $("#contents").val(),
					attendanceRequest	: $("#attendanceRequest:checked").size(),
					categoryId			: $("#category").val(),
					categoryName		: "",
					schedulePublic		: $("#schedulePublic:checked").size(),		// 일정 공개 여부 ( 0 : 공개일정, 1 : 비공개일정)
					schedulePrivate		: $("#schedulePrivate:checked").size(),		// 일정 숨김 여부 ( 0 : 공개일정, 1 : 숨김일정)
					alarmRequest		: 0,											// 알람 요청 여부 ( 0 : 미요청, 1 : 요청)
					repeat				: repeat,
					workspaceId			: $("#team-sel").val(),
					recurrences			: [],									// 반복일정
					alarmList			: getAlarmList(),
					participantList		: getParticipantList(),
					mandatorId			: schedule.currentSource.mandatorId,
					meetingRoomId		: $("#meetingRoomId").val(),
					workAreaName 		: $("#companyArea").val(),
					everyoneSchedule    : $("#everyoneSchedule").val(),
					approveResult    	: $("#approveResult").val(),
					updateDisplay		: $("#updateDisplay:checked").size(),
					cartooletcId		: $("#cartooletcId").val()
							
			};

			if($("#repeat").get(0).checked) {
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
			//var title = $("#title").val();
			var newSchedule = getData();
			
			if(newSchedule) {
				if(newSchedule.meetingRoomId) {	// 회의실 예약 가능한 시간인지 체크
					var currDate = iKEP.getCurTime();
					var reserveTime = cloneDate(newSchedule.startDate);
					reserveTime.setMinutes(reserveTime.getMinutes()-meetingRoomConfig.minReservationTime);
					if(reserveTime < currDate) {
						if(Math.abs((reserveTime - currDate)/(60*1000)) < meetingRoomConfig.minReservationTime)
							alert(iKEPLang.meetingroom.messageText.noReserveReadyTime.replace("%w", meetingRoomConfig.minReservationTime));
						else 
							alert(iKEPLang.meetingroom.messageText.noReservePastTime);
						return false;
					}
				}

				fileController.upload(function(isSuccess, files) {	
					if(isSuccess === true) {
						var isRef = existParticipant();
						
						newSchedule.fileLinkList = normalizeFile(files || []);				
						if(newSchedule.scheduleId) {
							//newSchedule.sendmail = sendmail ? true : false;
							if(schedule.sco.repeatType) { // 반복일정
								if(newSchedule.meetingRoomId) {	// 회의실이 있는 반복 일정은 모든 일정 수정으로 고정
									updateEvent(newSchedule, isRef, 3);
								} else {
									$update_dialog.dialog("open");
									$update_dialog.dialog({
										close: function(e, ui) {
											if(dialogButton == 1) {
												dialogButton = 0;
												
												var chooesUpdateRepeat = $jq("#update-dialog input:checked").val();
												updateEvent(newSchedule, isRef, chooesUpdateRepeat);							
											}
										}
									});
								}
							} else {
								//alert(newSchedule.updateDisplay);
								updateEvent(newSchedule, isRef);
							}
						} else {		
							//newSchedule.sendmail = sendmail ? true : false;
							createEvent(newSchedule, isRef);
						}	
					} else {
						alert(iKEPLang.planner.errorText.failUpload);
					}
				});
			}
		}
		
		function scrollTop() {
			$("html, body").scrollTop(0);
		}
		
		// event 생성
		function createEvent(event, isRef) {
			event.sendmail = isRef; 
			
			var url = event.mandatorId ? "createByTrustee.do" : "create.do";
			
				
			$mgmtPanel.ajaxLoadStart();
			$.postJSON(baseUrl+url, event, function(res) {
				if(res.success == "duplicate") {
					alert(Message.errorText.meetingRoomDuplicate);
					$("#btnMeetingRoomCancel").click();
					return false;
				}
				
				if(event.everyoneSchedule == "1"){//무림제지 전사일정 등록후 승인대기 목록 화면으로 이동
					document.location.href = home_url + "lightpack/planner/calendar/companyScheduleList.do";
				}else{
					destroy()
					$mgmtPanel.hide();
					$mgmtPanel.ajaxLoadComplete();
					$mgmtPanel.trigger("onComplete");
				}
			});
		}
		
		// event 수정
		function updateEvent(newSchedule, isRef, wh) {
			var params = {};//{sendmail: newSchedule.sendmail || false};
			if(schedule.sco.repeatType) { // 반복일정
				params.sendmail = isRef;
				
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
				$.postJSON(baseUrl+"updateSchedule.do", params, function(res) {
					destroy();
					
					$mgmtPanel.hide();
					$mgmtPanel.ajaxLoadComplete();
					
					$mgmtPanel.trigger("onComplete");
				});								
			} else {
				params.sendmail = isRef;
				params.newSchedule = newSchedule;
				//alert("params.updateType:"+params.updateType);
				$mgmtPanel.ajaxLoadStart();
				$.postJSON(baseUrl+"updateSchedule.do", params, function(res) {
					$mgmtPanel.ajaxLoadComplete();
					
					if(res.success == "duplicate") {
						alert(Message.errorText.meetingRoomDuplicate);
						return false;
					}
					destroy();
					
					$mgmtPanel.hide();
					
					$mgmtPanel.trigger("onComplete");
				});
			}
		}
		
		// event 삭제
		function deleteEvent(event, isRef) {	// isRef, 참여/참조자 여부
			var params = {};
			if(!initialized) init();
			
			if(event.sco.repeatType) {	// 반복일정		
				$delete_dialog.dialog("open");
				$delete_dialog.dialog({
					close: function(e, ui) {
						if(dialogButton == 1) {
							dialogButton = 0;
							var wh = $jq("#delete-dialog input:checked").val();
							if(wh == 1 || wh == 2 || wh == 3){
								params.deleteType = wh;
								params.eventInfo = convertEventInfo(event.sco);
								_deleteEvent(params, isRef);		
							}
						}
					}
				});				
			} else {	// 단순일정
				params.eventInfo = {scheduleId: event.id};
				if(confirm(iKEPLang.planner.messageText.confirmDeleteSchedule)) 
					_deleteEvent(params, isRef);
			}
		}
		
		function _deleteEvent(params, isRef) {
			params.sendmail = isRef;
			
			$mgmtPanel.ajaxLoadStart();
			$.postJSON(baseUrl+"deleteSchedule.do", params, function(res) {		
				destroy();
				
				$mgmtPanel.ajaxLoadComplete();
				$mgmtPanel.hide();
				
				$mgmtPanel.trigger("onComplete");
			});
			
		}
		function strikeEvent(event) {
			var params = {};
			
			
			$mgmtPanel.ajaxLoadStart();
			
			params.sendmail = false;
			params.newSchedule = {scheduleId: event.id, updateDisplay: 2};
			
			$.postJSON(baseUrl+"strikeSchedule.do", params, function(res) {		
				destroy();
				
				$mgmtPanel.ajaxLoadComplete();
				$mgmtPanel.hide();
				
				$mgmtPanel.trigger("onComplete");
			});
			
		}
		
		function eventTimeChange(method, event, dayDelta, minuteDelta, allDay, revertFunc, jsEvent) {
			var params = {}, newSchedule;
			
			if(!initialized) {
				init();
			}
			$update_dialog.dialog("open");
			$update_dialog.dialog({
				close: function(e, ui) {
					var wh = $jq("#update-dialog input:checked").val();
					if(wh == 1){
						params.updateType = wh;
						newSchedule = {
								startDate: event.start,
								endDate: event.end,
								scheduleId: event.id,
								mandatorId: "TODO"
						};
						params.newSchedule = newSchedule;
						event.sco.updateStart = formatDate(event.start, 'yyyyMMddHHmm'); // TODO: 정리 후 삭제 요망
						event.sco.updateEnd = formatDate(event.end, 'yyyyMMddHHmm'); // TODO: 정리 후 삭제 요망
						params.eventInfo = convertEventInfo(event.sco);
//						if(wh == 2) {
//							params.isDirtyRepeat = "yes";
//						}			
						$.postJSON(baseUrl+"updateSchedule.do", params, function(res) {
							destroy();
							$mgmtPanel.trigger("onComplete");
						});
					} else if (wh == 2) {
						//iKEP.debug("$update_dialog:" + wh);
					} else {
						//iKEP.debug("$update_dialog:" + wh);
						revertFunc();
					}						
				}
			});
		}
		
		// 반복 조건이 변경되었는지 여부
		function isDirtyRepeat(oldEvent, newEvent) {
			var res = true;
			var roption, oldStart, oldEnd, oldRepeatEndDate;
			var newRecur = newEvent.recurrences[0];
			if(!newRecur || newEvent.repeat == 0) {
				return res;
			}

			if(oldEvent.repeatPeriodOption == null) {
				roption = "";
			}
			oldStart = new Date(oldEvent.startDate);
			oldEnd = new Date(oldEvent.endDate);
			oldRepeatEndDate = getLastRepeatEndDate(oldEvent);
		
			if(oldStart - newEvent.startDate === 0 && 
					oldEnd - newEvent.endDate === 0 &&
					oldRepeatEndDate - newRecur.endDate == 0 &&
					oldEvent.repeatType == newRecur.repeatType &&
					oldEvent.repeatPeriod == newRecur.repeatPeriod &&
					roption == newRecur.repeatPeriodOption){
				res = false;
			}
			return res;
		}
		

		function convertEventInfo(einfo) {
			return {				
				scheduleId: einfo.scheduleId,
				startDate: einfo.startDate,
				endDate: einfo.endDate,
				repeatType: einfo.repeatType,
				repeatPeriod: einfo.repeatPeriod,
				repeatPeriodOption: einfo.repeatPeriodOption,
				repeatStartDate: einfo.repeatStartDate,
				repeatEndDate: einfo.repeatEndDate,
				updateStart: einfo.updateStart,
				updateEnd: einfo.updateEnd,
				wholeday: einfo.wholeday
			};
		}
		
		function replaceNewLine(text) {
			return text ? text.replace(regReplaceNewLine, "<br />\n") : text;
		}
		
		// 조회 화면용
		function populate(schedule, event) {
			var pattern = 'yyyy.MM.dd,t,HH:mm';
			var patternDate = 'yyyy.MM.dd';
			var start = event.start; //new Date(schedule.startDate);
			var end = event.end; //new Date(schedule.endDate);
			var alarmType = Message.labelText.alarmTypes;
			var rtypearr = Message.labelText.repeatTypes;
			var $participants = $("#participants");
			var $referers = $("#referers");
			var pa = [], ra = [], roption;
			var s = '', t, i, len, acceptChecked = false, files;
			var allDay = schedule.wholeday == 1 ? true : false;
	
			var sr = formatDate(start, pattern).split(",");
			var er = formatDate(end, pattern).split(",");

			if(userLocale == "en" || userLocale != portalLocale) {
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

			s = schedule.repeat == 1 ? Message.labelText.repeatSchedule : '&nbsp;';
			$("#repeat").html('<strong>' + s + '</strong>');
			
			s = allDay ? Message.labelText.allDayFull : '&nbsp;';
			$("#allDay").html('<strong>' + s + '</strong>');	
			
			s = schedule.schedulePublic == 0 ? "" : Message.labelText.noopen;
			$("#public").html('<strong>' + s + '</strong>');
			
			s = schedule.schedulePrivate == 0 ? "" : "숨김";
			$("#private").html('<strong>' + s + '</strong>');
			
			s = '';
			
			if(schedule.repeat == 1) {
				$.each(schedule.recurrences, function(n, item) {
					s += n > 0 ? '<br>' : '';
					s += '<span>' + Message.labelText.every + '</span>&nbsp;';
					s += '<span>' + item.repeatPeriod + '</span>&nbsp;';
					s += '<span>' + rtypearr[item.repeatType - 1] + Message.labelText.unit + '</span>&nbsp;';
					
					roption = item.repatPeriodOption ? item.repatPeriodOption.split(',') : [];
					if(item.repeatType == 2) {
						for(i=0, len=roption.length; i<len; i++) {
							s += iKEPLang.datepicker.lblDayNames[roption[i]-1] + Message.labelText.dow + '&nbsp;';
							s += i+1 == len ? '' : ',';
						}
					} else if(item.repeatType == 3 && roption[0] == "a") {
						s += roption[1] + Message.labelText.date_1 + '&nbsp;';
					} else if(item.repeatType == 3 && roption[0] == "b") {
						s += roption[1] + Message.labelText.nth + iKEPLang.datepicker.lblDayNames[roption[2]-1] + Message.labelText.dow + '&nbsp;';
					} else if(item.repeatType == 3 && roption[0] == "c") {
						s += Message.labelText.last + iKEPLang.datepicker.lblDayNames[roption[1]-1] + Message.labelText.dow + '&nbsp;';
					} else if(item.repeatType == 3 && roption[0] == "d") {
						s += Message.labelText.lastDate;
					} else if(item.repeatType == 4 && roption[0] == "a") {
						s += s += roption[2] + Message.labelText.month + '&nbsp;' + roption[1] + Message.labelText.date_1 + '&nbsp;';
					} else if(item.repeatType == 4 && roption[0] == "b") {
						s += s += roption[3] + Message.labelText.month + '&nbsp;' + roption[1] + Message.labelText.nth + iKEPLang.datepicker.lblDayNames[roption[2]-1] + Message.labelText.dow + '&nbsp;';
					} else if(item.repeatType == 4 && roption[0] == "c") {
						s += s += roption[2] + Message.labelText.month + '&nbsp;' + Message.labelText.last + iKEPLang.datepicker.lblDayNames[roption[1]-1] + Message.labelText.dow + '&nbsp;';
					} else if(item.repeatType == 4 && roption[0] == "d") {
						s += s += roption[1] + Message.labelText.month + '&nbsp;' + Message.labelText.lastDate;
					}
					
					s += '<span>' + $.fullCalendar.formatDate(new Date(item.startDate), patternDate) + '</span>&nbsp;' + Message.labelText.from + '&nbsp;';
					t = $.fullCalendar.formatDate(new Date(item.endDate), patternDate);
					if(t.indexOf('9999', 0) === -1) {
						s += '<span>' + t + '</span>&nbsp;' + Message.labelText.untilRepeat;
					} else {
						s += '<span><strong>' + ' ' + Message.labelText.forever + '</strong></span>&nbsp;';
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
			var workareanameStr ="";
			if(event.source && event.source.name=="companyCalendar"){
				if(event.sco.workAreaName){
					if(event.sco.workAreaName!=""){
						workareanameStr = event.sco.workAreaName+") ";
					}
				}
			}
			$("#title").html(workareanameStr+schedule.title);
			
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
				if(item.targetType == 1 && schedule.attendanceRequest == 1) {	// 참여자 이며 참석여부 확인을 요청한 경우
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
							if(item.targetUserId == owner.userId) {	//참여자가 나이면...
								$('<a class="button_s accept" href="#a"><span><img alt="" src="' + iKEP.getContextRoot() + '/base/images/icon/ic_btn_enroll.gif"> ' + iKEPLang.planner.labelText.attendance + '</span></a>').appendTo(s);
								$('<a class="button_s reject" style="margin-left:5px;" href="#a"><span><img alt="" src="' + iKEP.getContextRoot() + '/base/images/icon/ic_btn_delete1.gif"> ' + iKEPLang.planner.labelText.noattendance + '</span></a>').appendTo(s);
							} else {
								s.html("[" + iKEPLang.planner.labelText.unknown + "]");
							}
					}
					
					s.appendTo($li).css("margin-right", "7px");
				}
				
				
				var userInfo = "";
				if(userLocale == "en" || userLocale != portalLocale) {
					userInfo = item.targetUserEnglishName + " " + item.targetUserJobTitleEnglishName + " " + (item.targetUserTeamEnglishName || "");
				} else {
					userInfo = item.targetUserName + " " + item.targetUserJobTitleName + " " + (item.targetUserTeamName || "");
				}

				$("<span/>").html(userInfo)
					.addClass("ilink")
					.click(function() { iKEP.goProfilePopupMain(item.targetUserId); })
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
						});
				}
				
				if(participantSummary.unk > 0) {
					$("a.accept, a.reject", $participants).click(function(event) {
						var params = {
							reason: "",
							scheduleId: schedule.scheduleId,
							targetUserId: owner.userId
						};

						var $anchor = $(this);
						switch(true) {
							case $anchor.hasClass("accept") :	// 참석
								params.isAccept =  1;

								$.postJSON(baseUrl+"updateAcceptInfo.do", params, function(data) {
									$anchor.parent()
										.children("a").unbind("click")
										.end().addClass("colorBlue")
											.html("[" + iKEPLang.planner.labelText.attendance + "]");
									
									var textNode = $div.children(":eq(1)")[0].childNodes[0];
									var label = iKEPLang.planner.labelText.attendance + ' : ';
									textNode.nodeValue = label + (Number(textNode.nodeValue.replace(label, "")) + 1);
									
									textNode = $div.children(":eq(3)")[0].childNodes[0];
									label = iKEPLang.planner.labelText.unknown + ' : ';
									textNode.nodeValue = label + (Number(textNode.nodeValue.replace(label, "")) - 1);
								});
								break;
							case $anchor.hasClass("reject") :	// 불참
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
										
										params.reason =  reason;
										$.postJSON(baseUrl+"updateAcceptInfo.do", params, function(data) {
											var $span = $anchor.parent();
											$span.children("a").unbind("click")
												.end().addClass("colorPoint")
													.html("[" + iKEPLang.planner.labelText.noattendance + "]");
											$('<div class="schedule-noattendance-reason"/>').html(iKEPLang.planner.labelText.reason + " : " + reason)
												.appendTo($span.parent());
											
											var $noattendanceSpan = $div.children(":eq(2)");
											var textNode = $noattendanceSpan[0].childNodes[0];
											var label = iKEPLang.planner.labelText.noattendance + ' : ';
											textNode.nodeValue = label + (Number(textNode.nodeValue.replace(label, "")) + 1);
											
											textNode = $div.children(":eq(3)")[0].childNodes[0];
											label = iKEPLang.planner.labelText.unknown + ' : ';
											textNode.nodeValue = label + (Number(textNode.nodeValue.replace(label, "")) - 1);

											if($noattendanceSpan.children("a").length == 0) {
												$('<a class="button_s" href="#a"><span>' + iKEPLang.planner.btnText.viewReason + '</span></a>').appendTo($noattendanceSpan)
													.css("margin-left", "7px")
													.click(function() {
														$(".schedule-noattendance-reason", $participants).toggle()
															.last().css("border-bottom-width", "0");
													});
											}
										});
									}
									$dialog.dialog("close");
								});
								return false;
								break;
							}
						});
				}
			}
			
			
			$("#place").html(schedule.place);
			if(event.source && event.source.name!="companyCalendar"){
				$("#carToolDiv").show();
				$("#tool").html(schedule.cartooletcName);
			}
			$("#contents").html(schedule["contents"] && schedule.contents.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>"));

			$("#workspace").html(schedule.workspaceName);
			
			iKEP.FileController.setFileList("#attachFiles", schedule.fileDataList);
			
			$(".btn_close").click(function() {
				$mgmtPanel.empty().hide();
				$("#calendar").show();		
			});
			
			$(".btn_sendmail").click(function() {
				var nameList = [], emailList = [];
				var content = schedule.contents || " ";
				var name;
				
				$.each(schedule.participantList, function(idx, item) {
					if(userLocale == "en" || userLocale != portalLocale) {
						name = item.targetUserEnglishName;
					} else {
						name = item.targetUserName;
					}
					nameList.push(name);
					emailList.push(item.mail);
				});
				content += "<br><p><a href='http://" + viewUrl + schedule.scheduleId + 
					"'>" + messageShowDetail + "</a><p>";
				//alert(content);
				iKEP.sendMailPop(nameList, emailList, schedule.title || "", content || "", "", "");			
			});
			
			$(".btn_update").click(function() {
				$mgmtPanel.empty();
				event.sco.contents = schedule.contents;
				event.sco.participantList = schedule.participantList;
				event.sco.alarmList = schedule.alarmList;
				event.sco.fileDataList = schedule.fileDataList;
				event.sco.recurrences = schedule.recurrences;
				event.sco.meetingRoomId = schedule.meetingRoomId;
				event.sco.updateDisplay =schedule.updateDisplay;
				event.sco.workAreaName =schedule.workAreaName;
				event.sco.schedulePrivate =schedule.schedulePrivate;
				event.sco.cartooletcId = schedule.cartooletcId;
				event.sco.cartooletcName = schedule.cartooletcName;
				editEvent(event);
			});
			
			$(".btn_remove").click(function() {
				deleteEvent(event, (schedule.participantList.length > 0));
				/*if(confirm(Message.messageText.confirmDeleteSchedule)) {				
					if(schedule.participantList.length > 0 && confirm(messageConfirmMail)) {
						deleteEvent(event, true);
					} else {
						deleteEvent(event);
					}
				}*/
			});
			$(".btn_strike").click(function() {
				strikeEvent(event);
			});
			
			if(event.currentSource.source=="companyCalendar"){//무림제지 전사일정에서 필요없는 기능 가림. 기본값 셋팅.
				$("#strike_button").show();
				$("#alarm").hide();
				$("#isAccept").hide();
				$("#participants_view_tr").hide();
				$("#referers_view_tr").hide();
				$("#workspace_view_tr").hide();
				$("#public").hide();
				$("#private").hide();
			}else{
				$("#strike_button").hide();
			}
		}
		
		function editEvent(event) {
			$mgmtPanel.empty();
			schedule = $.extend({}, event);
			if(!initialized) {
				init();
			}	
			populateEvent(event);
		}
		
		function populateEvent(event) {
			//alert(event.currentSource.source);
			
			var permitCheck = true;
			if(event.currentSource.source=="companyCalendar"){//무림제지 전사일정에서 필요없는 기능 가림. 기본값 셋팅.
				if(setting.ikepPlanner.companyScheduleAdminFlag){//관리자이면
				}else{
					permitCheck = false;
					//alert("전사일정은 전사일정 관라지만 작성가능합니다.");//2012.11.1 경고막기
					cancel();
					scrollTop();
				}
			}
			if(permitCheck){
				var isMeetingRoom = event.sco && event.sco.meetingRoomId;
				$("#"+mgmtEditTemplateId).tmpl({}).appendTo($mgmtPanel);
				var startDate = cloneDate(event.start),
					endDate = cloneDate(event.end);
				// 회의실 반복 예약의 경우 일정 전체를 수정하므로, 시작일을 반복일정의 시작일로 설정함.
				if(event.sco && event.sco.meetingRoomId && event.sco.repeatType > 0) {
					startDate = cloneDate(event.sco.repeatStartDate);
					endDate = cloneDate(event.sco.repeatStartDate);
				}
				setStartDatepicker(startDate, isMeetingRoom);
				setEndDatepicker(endDate, isMeetingRoom);
				setTime(event);
				setAlldayOption(event);						
				setCategory(event);
				setAlarm(event);
				$("#everyoneScheduleYn").val(event.currentSource.source);
				if(event.id) {
					$(".btn_save span").html(Message.btnText.create);
					setPublicOption(event);
					setPrivateOption(event);
					setRepeatTypeOption(event);
					setAttendanceRequest(event);
					setTitle(event);
					setUpdateDisplay(event);
					setPlace(event);
					if(event.currentSource.source!="companyCalendar"){
						setTool(event);
					}else{
						$("#toolDiv").hide();
					}
					setContents(event);
					setParticipant(event);
					setFileInfo(event); // initFileController 실행
				} else {
					initFileController();
					$(".btn_delete").hide();
					$("#updateDisplay_label").hide();
					
				}
				
				if(event.currentSource.source=="companyCalendar"){//무림제지 전사일정에서 필요없는 기능 가림. 기본값 셋팅.
					//alert("여기");
					setCompanyArea(event);
					$("#schedulePublic_label").hide();
					$("#schedulePrivate_label").hide();
					$("#participants_tr").hide();
					$("#referers_tr").hide();
					$("#workspace_tr").hide();
					$("#everyoneSchedule").val("1");
					$("#alarm_tr").hide();
					$("#categoryView").hide();
					$("#mail_send_yn").hide();
				}else{
					$("#updateDisplay_label").hide();
					$("#companyArea").hide();
				}
				
				renderTeam(event);
				initValidator();
				$mgmtPanel.show();
				scrollTop();
			}
			//$("#title").focus();
		}
		
		function viewEvent(event) {
			//console.log("eventMgmt");
			//console.log(JSON.parse(JSON.stringify(event)));
			
			$.get(baseUrl+"getScheduleAllData.do", {scheduleId: event.id}, function(data) {
				$("#viewEventTemplate").tmpl({}).appendTo($mgmtPanel);
				$(".btn_close span").html(Message.btnText.scheduleList);
				$mgmtPanel.show();
				scrollTop();
				populate(data, event);
			});
		}
		
		function viewEventId(event) {
			//console.log("eventMgmt");
			//console.log(JSON.parse(JSON.stringify(event)));
			
			$.get(baseUrl+"getScheduleAllData.do", {scheduleId: event.id}, function(data) {
				$("#viewEventTemplate").tmpl({}).appendTo($mgmtPanel);
				$(".btn_close span").html(Message.btnText.scheduleList);
				$mgmtPanel.show();
				scrollTop();
				populate(data, event);
			});
		}
		
		function init() {
			$mgmtPanel
			.delegate(".btn_cancel", "click", cancel)
			.delegate("#wholeday", "change", function(e) {
				if(this.checked) {
					//$(".time-pick").attr("disabled", true);
					$("#startDueTimeMinute").attr("disabled", true);
					$("#startDueTimeHour").attr("disabled", true);
					$("#endDueTimeMinute").attr("disabled", true);
					$("#endDueTimeHour").attr("disabled", true);
				} else {
					//$(".time-pick").attr("disabled", false);
					$("#startDueTimeMinute").attr("disabled", false);
					$("#startDueTimeHour").attr("disabled", false);
					$("#endDueTimeMinute").attr("disabled", false);
					$("#endDueTimeHour").attr("disabled", false);
				}
			})
			.delegate("#repeat", "click", function() {
				if(this.checked) {
					$("#repeat-div").removeClass("hidden");
					$("#repeatType").attr("value", "daily");
					$("#repeatType").change();
				} else {
					$("#repeat-div").addClass("hidden");
				}		
			})			
			.delegate("#repeatType", "change", function() {
				$("#repeat_pane").empty().removeClass("hidden");
				//$repeat_pane.removeData("roption");
				
				var selectedType = this.value;
				var sd = $("#start-date-picker").datepicker("getDate");

				var o = {};
				var els = $("#recurrenceTemplate").tmpl( recurrenceData[selectedType], { 
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
				
				var expd = sd;
				var option = {};
				
				if($("#meetingRoomId").val()) {
					$("#forever").attr("checked", false)
						.parent().hide();
					
					var curDate = iKEP.getCurTime();
					curDate.setHours(0, 0, 0, 0);
					
					option.minDate = curDate;
					option.maxDate = addMonths(cloneDate(curDate), meetingRoomConfig.maxReservationMonth);
				}
				
				switch(selectedType) {
				case "daily":
					expd = addDays(cloneDate(sd), 7);
					break;
				case "weekly":
					expd = addMonths(cloneDate(sd), 1);
					$("#roption-span").find("input:checkbox:eq(" +
							sd.getDay() + ")").attr("checked", true);
					$("#roption-span").find("input:checkbox:eq(" +
							sd.getDay() + ")").attr("disabled", true);
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

				$("#endRepeatDate")
				.datepicker($.extend(option, datepickerOptions))
				.datepicker("setDate", expd)
				.next("img").addClass("dateicon");
				
				if(selectedType === "yearly") {
					$("#forever").attr("checked", true);
					$("#endRepeatDate").datepicker("disable");
				}
			})
			.delegate("#forever", "click", function(){
				if(this.checked) {
					$("#endRepeatDate").datepicker("disable");
				} else {
					$("#endRepeatDate").datepicker("enable");
				}
			})
			.delegate(".alarm-plus", "click", function(event) {
				var len = $("#alarm-div .alarm-item").length;
				event.preventDefault();
				if(len === 2) return;
				appendAlarm();
				
				var pn = $("#alarm-div .alarm-item").length;
				if( pn === 1 ) {
					$("#alarm-div .alarm-minus").hide();
					$("#alarm-div .alarm-plus").show();
				} else if(pn === 2) {
					$("#alarm-div .alarm-minus").show();
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
					$("#alarm-div .alarm-plus").show();
				} else if(pn === 2) {
					$("#alarm-div .alarm-minus").show();
					$("#alarm-div .alarm-plus").hide();
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
			.delegate(".btn_delete", "click", function() {
				deleteEvent(schedule, existParticipant());
				/*if(confirm(iKEPLang.planner.messageText.confirmDeleteSchedule)) {		
					if(existParticipant() && confirm(messageConfirmMail)) {
						deleteEvent(schedule, true);
					} else {
						deleteEvent(schedule);
					}
				}*/
			})
			.delegate("#btnMeetingRoomReserve", "click", function() {
				/*
				var schedule = null;
				if($("#repeat").get(0).checked) {
					schedule = getData();
				}
				*/
				var schedule = getData();
				
				iKEP.showDialog({
					title: "회의실예약연동",
					url : iKEP.getContextRoot() + "/lightpack/meetingroom/main.do?mode=schedule",
					modal : true,
					width : 800,
					height: 500,
					params : schedule,
					callback : function(meetingRoomInfo) {
						$("#start-date-picker").datepicker("setDate", meetingRoomInfo.startTime);
						//setStartDatepicker(meetingRoomInfo.day, true);
						$("#end-date-picker").datepicker("setDate", meetingRoomInfo.endTime);

						var stStr =""+meetingRoomInfo.startTime;
						stStr = stStr.split(" ")[1];
						stStr = stStr.split(":")[0]+stStr.split(":")[1];
						var edStr =""+meetingRoomInfo.endTime;
						edStr = edStr.split(" ")[1];
						edStr = edStr.split(":")[0]+edStr.split(":")[1];
						
						$("#start-time-pick").val(stStr);
						$("#end-time-pick").val(edStr);
						
						$("#place").val(meetingRoomInfo.buildingName + " " + meetingRoomInfo.floorName + " " + meetingRoomInfo.meetingRoomName);
						$("#meetingRoomId").val(meetingRoomInfo.meetingRoomId);
						
						setMeetingRoomReserve();
						
						this.close();
					}
				});
			})
			.delegate("#btnMeetingRoomCancel", "click", function() {
				$("#end-date-picker").show()
					.next("img").show();
				$("#place").attr("readonly", false)
					.val("");
				$("#meetingRoomId").val("");
				$("#cartooletcId").val("");
				
				$("#repeatType").change();
				
				$("#wholeday").parent().show();
				
				var datepickerOption = {minDate: null, maxDate:null};
				$("#start-date-picker").datepicker("option", datepickerOption);
				$("#end-date-picker").datepicker("option", datepickerOption);
				$("#endRepeatDate").datepicker("option", datepickerOption);
				
				$(this).hide();
				$("#toolDiv").hide();
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
						$("#start-date-picker").datepicker("setDate", meetingRoomInfo.startTime);
						//setStartDatepicker(meetingRoomInfo.day, true);
						$("#end-date-picker").datepicker("setDate", meetingRoomInfo.endTime);

						var stStr =""+meetingRoomInfo.startTime;
						stStr = stStr.split(" ")[1];
						stStr = stStr.split(":")[0]+stStr.split(":")[1];
						var edStr =""+meetingRoomInfo.endTime;
						edStr = edStr.split(" ")[1];
						edStr = edStr.split(":")[0]+edStr.split(":")[1];
						
						$("#start-time-pick").val(stStr);
						$("#end-time-pick").val(edStr);
						
						$("#tool").val(meetingRoomInfo.cartooletcName);
						$("#cartooletcId").val(meetingRoomInfo.cartooletcId);
						
						setToolReserve();
						
						this.close();
					}
				});
			})
			.delegate("#btnToolCancel", "click", function() {
				$("#end-date-picker").show()
					.next("img").show();
				$("#tool").attr("readonly", false)
					.val("");
				$("#cartooletcId").val("");
				
				$("#repeatType").change();
				
				$("#wholeday").parent().show();
				
				var datepickerOption = {minDate: null, maxDate:null};
				$("#start-date-picker").datepicker("option", datepickerOption);
				$("#end-date-picker").datepicker("option", datepickerOption);
				$("#endRepeatDate").datepicker("option", datepickerOption);
				
				$(this).hide();
			});
			
			$update_dialog.dialog({
				autoOpen: false,
				modal: true,
				resizable : false,
				title : iKEPLang.planner.messageText.repeatUpdateDialogTitle,
				open: function () {
					dialogButton = 0;	// button click clear : close button with
					$jq("#update-dialog input:checked").attr("checked", false);
				}
			});
			
			$jq("#btn_update_ok").click(function(){
				if($jq("#update-dialog input:checked").val()) {
					dialogButton = 1;
					$update_dialog.dialog("close");
				} else {
					//alert(iKEPLang.planner.messageText.repeatUpdateOptionSelect);
					$update_dialog.dialog("option", "title", iKEPLang.planner.messageText.repeatUpdateOptionSelect);
					$("span.ui-dialog-title").css("color", "yellow");
					setTimeout(function() {
						$update_dialog.dialog("option", "title", iKEPLang.planner.messageText.repeatUpdateDialogTitle);
						$("span.ui-dialog-title").css("color", "");
					}, 3000);
				}
			});
			
			$jq("#btn_update_cancel").click(function(){
				/*$jq("#update-dialog input:checked").each(function(){
					this.checked = false;
				});*/
				dialogButton = 0;
				$update_dialog.dialog("close");
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
			})
			;
			
			initTimeStr();
			initialized = true;
		}
		
		function destroy() {
			$(".date-pick").datepicker("destroy");
			$mgmtPanel.empty();
		}
		
		function cancel(e) {
			destroy();
			$mgmtPanel.hide();
			$("#calendar").show();
			
			$mgmtPanel.trigger("onUpdateCancel");
		}
		
		function setToolReserve() {
			$("#tool").attr("readonly", true);
			
			var curDate = iKEP.getCurTime();
			curDate.setHours(0, 0, 0, 0);
			var datepickerOption = {minDate: curDate, maxDate:addMonths(cloneDate(curDate), meetingRoomConfig.maxReservationMonth)};
			$("#start-date-picker").datepicker("option", datepickerOption);
			$("#end-date-picker").datepicker("option", datepickerOption);
			$("#endRepeatDate").datepicker("option", datepickerOption);
			
			$("#end-date-picker").hide()
				.next("img").hide();
			
			if($("#repeatType").val() == "yearly") {
				$("#repeatType").children()
					.eq(0).attr("selected", true)
					.end().filter("[value=yearly]").attr("disabled", true);
				
				$("#repeatType").change();
			} else {
				$("#repeatType").children("[value=yearly]").attr("disabled", true);
			}
			
			$("#wholeday").attr("checked", false)
				.parent().hide();
			
			$("#btnToolCancel").show();
		}
		
		function setMeetingRoomReserve() {
			$("#place").attr("readonly", true);
			
			var curDate = iKEP.getCurTime();
			curDate.setHours(0, 0, 0, 0);
			var datepickerOption = {minDate: curDate, maxDate:addMonths(cloneDate(curDate), meetingRoomConfig.maxReservationMonth)};
			$("#start-date-picker").datepicker("option", datepickerOption);
			$("#end-date-picker").datepicker("option", datepickerOption);
			$("#endRepeatDate").datepicker("option", datepickerOption);
			
			$("#end-date-picker").hide()
				.next("img").hide();
			
			if($("#repeatType").val() == "yearly") {
				$("#repeatType").children()
					.eq(0).attr("selected", true)
					.end().filter("[value=yearly]").attr("disabled", true);
				
				$("#repeatType").change();
			} else {
				$("#repeatType").children("[value=yearly]").attr("disabled", true);
			}
			
			$("#wholeday").attr("checked", false)
				.parent().hide();
			
			$("#btnMeetingRoomCancel").show();
			if($("#everyoneScheduleYn").val()!="companyCalendar"){
				$("#toolDiv").show();
			}
		}
		
		return {
			init: init,
			editEvent: editEvent,
			viewEvent: viewEvent,
			eventTimeChange: eventTimeChange,
			cancel: cancel
		};
	}
	
	$.eventMgmt = eventMgmt;
})(jQuery);