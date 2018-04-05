var iKEPLang = {
	uploadControll : {	// file controller
		btnDelete : "Delete",
		titDelete : "Delete",
		lblUploadFile : "File upload",
		lblAttachFile : "Attach",
		altRollup : "rolldown",
		altRolldown : "rollup",
		lblFilePerMaxSize : " Max : %w",
		selectFiles : "Select Files",
		filter : {txt:"TEXT File", img:"Image File", comp:"Compress File", doc:"Document File", mp:"Music File", vod:"Movie File", app:"Program File", all:"All"},
		plupload : {
			caption : "Select files",
			description : "Add files to the upload queue and click the start button.",
			lblFileName : "Filename",
			lblStatus : "Status",
			lblSize : "Size",
			btnAddFiles : "Add files",
			btnStopUpload : "Stop",
			noticeUploading : 'Uploaded %d/%d files',
			dragComment : "Drag files here.",
			errExtension : "Invalid file extension",
			errSize : "File to large",
			errInit : "Init error.",
			errHttp : "HTTP Error.",
			errSecurity : "Security error.",
			btnStartUpload: "Start upload"
		},
		errZeroSize : "Capacity of the chosen file contains a file with 0.\n\nPlease delete the file.",
		errTotalSizeOver : "Allow is total upload size %w.",
		errMaxFileSize : "Max file size : %w",
		lblNoAttachment : "No Attachment"
	},
	datepicker : {	// jquery ui datepicker
		langCode : "en",
		btnClose : "Close",
		btnPrev : "Prev",
		btnNext : "Next",
		btnCurrentDate : "Today",
		lblMonthNames : ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],
		lblMonthNamesShort : ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],
		lblDayNames : ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],
		lblDayNamesShort: ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"],
		lblDayNamesMin: ["Su","Mo","Tu","We","Th","Fr","Sa"],
		lblYear : ""
	},
	userContextMenu : {
		close : "Close",
		profile : "Profile",
		sms : "SMS",
		message : "Message"
	},
	tagging : {	// jquery ui tagging
		required : "Please enter your tag",
		maxlength:"100 characters can be entered into",
		tagCount:"Please put in more than 10 tags",
		tagDuplicate:"Duplicated tags.",
		tagNotice:"Please enter your tag.",
		save:"Save",
		cancel:"Cancel",
		edit:"Edit",
		tagWord:"Only letters, digits, commas and spaces are allowed."
			
	},
	sms : {
		monthSmsCount : "You have exceeded the number of sms this month. The number of sms up to {} thing. After checking, try again.",
		sendSuccess : "Transfer has completed."		
	},
	searchUserGroup : {
		duplicateUser : "Duplicate users",
		duplicateGroup : "Duplicate group"
	},
	meetingroom : {
		btnText: {
			save: "Save"
		},
		labelText: {
			daily: "Daily",
			weekly: "Weekly",
			monthly: "Monthly",
			yearly: "Yearly",
			month: "Month",
			date_1: "Date",
			dow: "Day",
			last: "Last",
			lastDate: "Last date",
			nth: "(th)",
			am: "am",
			pm: "pm",
			schedule: "Schedule",
			emptySchedule: "No schedules.",
			alarmTypes: ["Mail", "SMS", "Msg"],
			repeatTypes: ['Day', 'Week', 'Month', 'Year'],
			repeatSchedule: "Rep. Schedule",
			open: "Public",
			noopen: "Private",
			every: "Always",
			unit: "Every",
			from: "From",
			untilRepeat: "Repeat until",
			forever: "Forever",
			beforeMin: "min. before",
			unknown: "Unknown",
			attendance: "Join",
			noattendance: "Absence",
			allday: "All day",
			holiday: "Holiday",
			date: "Date",
			add: "Add",
			period: "Period",
			title: "Title",
			allDayFull: "Whole day",
			notAllDayFull: "Non-whole day",
			CAT: "Category",
			totalCountDesc: "Total {} Person(s)",
			reason : "Reason",
			total : "Total"
		},
		messageText : {
			inputTitle: "Write a title.",
			inputCycle: "Please enter a repeating cycle.",
			inputCycleDigits: "You can only enter digits.",
			doSendMail: "Proceed to send an E-mail to the participants and referenced people?",
			noAddOwnerToParticipant: "Schedule registrant participant / reference material can not be added to",
			noUser: "No users",
			noResult: "No results for search",
			maxTitle: "The length of title should be less than 300.",
			checkTimePick: "Start time reservations must be less than the end time.",
			noReservePastDate: "You can not schedule a meeting on the last date.",
			noReservePastTime: "Last time we can not schedule a meeting.",
			noReserveReadyTime : "Reservations must be at least %w minutes before.",
			duplicateReserve: "Already includes the scheduled time.",
			availableReserve : "Reservations are available."
		}
	},
	planner: { // 일정관리
		buttonText: {  // fullCalendar 전용
			prev: "Prev",
			next: "Next",
			prevYear: "&nbsp;&lt;&lt;&nbsp;",
			nextYear: "&nbsp;&gt;&gt;&nbsp;",
			today: "Today",
			month: "Monthly",
			week: "Weekly",
			day: "Daily",
			listMonth: "List"
		},			
		titleFormat: {	// fullCalendar 전용
			month: 'yyyy.MM',
			week: "yyyy.MM.dd{ ~ MM.dd [yyyy]}",
			day: "yyyy.MM.dd ddd."
		},		
		columnFormat: {	// fullCalendar 전용
			month: 'ddd',
			week: "'<span class=num>'dd'</span>' ddd",
			day: "'<span class=num>'MM.dd'</span>' ddd"
		},		
		btnText: {	// ikep 용
			update: "Edit",
			save: "Save",
			remove: "Delete",
			scheduleList: "List",
			ok: "OK",
			cancel: "Cancel",
			viewReason : "View reason"
		},
		labelText: {
			daily: "Daily",
			weekly: "Weekly",
			monthly: "Monthly",
			yearly: "Yearly",
			month: "Month",
			date_1: "Date",
			dow: "Day",
			last: "Last",
			lastDate: "Last date",
			nth: "(th)",
			am: "am",
			pm: "pm",
			schedule: "Schedule",
			emptySchedule: "No schedules.",
			alarmTypes: ["Mail", "SMS", "Msg"],
			repeatTypes: ['Day', 'Week', 'Month', 'Year'],
			repeatSchedule: "Rep. Schedule",
			open: "Public",
			noopen: "Private",
			every: "Always",
			unit: "Every",
			from: "From",
			untilRepeat: "Repeat until",
			forever: "Forever",
			beforeMin: "min. before",
			unknown: "Unknown",
			attendance: "Join",
			noattendance: "Absence",
			allday: "All day",
			holiday: "Holiday",
			date: "Date",
			add: "Add",
			period: "Period",
			title: "Title",
			allDayFull: "Whole day",
			notAllDayFull: "Non-whole day",
			CAT: "Category",
			totalCountDesc: "Total {} Person(s)",
			reason : "Reason",
			total : "Total"
		},
		titleText: {
			checkParticipant: "Check participants.",
			emptySchedule: "Non-preemptive",
			emptyTodaySchedule: "No schedules for today",
			companySchedule: "Company schedules",
			mySchedule: "My schedules",
			mandatorSchedule: "Mandator's schedule",
			teamSchedule: "Team schedules",
			userSchedule: "Individual schedules",
			privateSchedule: "Private",
			memberSchedule: "Members' schedule",
			viewDetailEvent: "Schedule information",
			noattendanceTitle: "Write of noattendance reason"
		},
		messageText: {
			inputTitle: "Write a title.",
			inputCycle: "Please enter a repeating cycle.",
			inputCycleDigits: "You can only enter digits.",
			doSendMail: "Proceed to send an E-mail to the participants and referenced people?",
			checkTrustee: "Check the name of the deputy.",
			emptySchedule: "No schedules.",
			emptyTodaySchedule: "No schedules for today",			
			showDetail: "Schedule information",
			noUser: "No users",
			noResult: "No results for search",
			selectHoliday: "Select a holiday to delete.",
			confirmDeleteHoliday: "Proceed to delete the holiday?",
			selectCategory: "Select a category to delete.",
			selectCompany: "Select a company-schedule to delete.",
			selectCompanyApprove: "Select a company-schedule to approve.",
			confirmDeleteCategory: "Proceed to delete the category? Schedules of the deleted category are changed to default Work category",
			confirmDeleteSchedule: "Proceed to delete the schedule?",
			confirmApproveSchedule: "Proceed to approve the schedule?",
			clickSearchMessage: "Click search button after selecting users for search.",
			maxTitle: "The length of title should be less than 300.",
			checkRepeatDay: "Please select Repeat Day of the week.",
			confirmDeleteMantator: "Proceed to delete the deputy?",
			noAddOwnerToParticipant: "Schedule registrant participant / reference material can not be added to",
			repeatUpdateDialogTitle : "Update for repeat schedule.",
			repeatUpdateOptionSelect : "Repeat schedule update option is select.",
			repeatDeletDialogTitle : "Delete for repeat schedule.",
			repeatDeleteOptionSelect : "Delete option is select.",
			repeatUpdateConfirm : "Are you sure you want to edit a recurring event?"
		},
		errorText: {
			unknowError: "Unknown error.",
			failUpload: "File upload failed.",
			duplicateName: "Holidays can be registered the same name.",
			duplicateCategoryName: "This Category that is already registered.",
			eventDeleted : "Thing has already been deleted.",
			meetingRoomDuplicate : "The meeting room was already booked your choice.\n\nPlease select another time or another conference."
		}
	},
	mainPortlet : {	//포틀릿
		more : "more",
		config : "config",
		reload : "reload",
		maximize : "maximize",
		minimize : "minimize",
		restore : "restore",
		help : "help",
		close : "close",
		titHelpPopup : " help",
		configSuccessMessage : "Successfully configured.",
		configFailMessage : "Failed to configure.",
		confirmRemoveMessage : "Proceed to delete?",
		personalPortletMessage : "You may not post personal portlet public areas.",
		publicPortletMessage : "You may not post public portlet personal areas.",
		noPage : "ページを表示することができません."
	},
	clock : {
		sDayName : ["月", "火", "水", "木", "金", "土", "日"],
		fDayName : ["月", "火", "水", "木", "金", "土", "日"],
		sMonthName : ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
		fMonthName : ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
		am : "午前",
		pm : "午後",
		AM : "午前",
		PM : "午後",
		subscriptYear : "年",
		subscriptMonth : "月",
		subscriptDay : "日"
	},
	gallery : {
		imgCount : "Image #{cur} of #{tot}"
	},
	ieCheck : {
		msgCompatible :  "Browser \"compatibility view\" in setting up some features may not work correctly.<br/>Browser Compatibility View mode, please take a moment at least, not IE8.<br/>[<a href=\"#{compatibleUrl}\" traget=\"_blank\">Find out how to set up</a>]",
		msgUpgrade : "If you wish to use Internet Explorer browser, version 8 or later must use a browser can use normally.<br/>[<a href=\"#{upgradeUrl}\" traget=\"_blank\">Upgrade</a>]<br/>If you're already in IE 8 uses more of a developer tool than IE8 browser mode and document mode must be set.<br/>[<a href=\"#{modeUrl}\" traget=\"_blank\">Find out how to set up</a>]"
	}
};