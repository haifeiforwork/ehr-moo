var iKEPLang = {
	uploadControll : {	// file controller
		btnDelete : "삭제",
		titDelete : "삭제",
		lblUploadFile : "파일 업로드",
		lblAttachFile : "첨부파일",
		altRollup : "펼치기",
		altRolldown : "접기",
		lblFilePerMaxSize : "파일별 최대용량 : %w",
		selectFiles : "파일선택",
		filter : {txt:"텍스트 파일", img:"이미지 파일", comp:"압축 파일", doc:"문서 파일", mp:"음악 파일", vod:"동영상 파일", app:"프로그램 파일", all:"모든 파일"},
		plupload : {
			caption : "업로드할 파일을 선택해주세요.",
			description : "파일 선택후 \"업로드\"버튼을 눌러주세요.",
			lblFileName : "파일명",
			lblStatus : "상태",
			lblSize : "용량",
			btnAddFiles : "파일 추가",
			btnStopUpload : "중지",
			noticeUploading : '%d/%d 전송 완료',
			dragComment : "탐색기 등으로 파일을 드래그할 수 있습니다.",
			errExtension : "파일 확장자 오류 : ",
			errSize : "파일 용량 오류 : ",
			errInit : "초기화 오류",
			errHttp : "서버 연결 오류",
			errSecurity : "보안 오류",
			btnStartUpload: "업로드"
		},
		errZeroSize : "선택하신 파일 중 용량이 0인 파일이 포함되어 있습니다.\n\n해당 파일을 삭제해주세요.",
		errTotalSizeOver : "전체 업로드 허용 용량은 %w 입니다.",
		errMaxFileSize : "파일당 최대 용량 : %w",
		lblNoAttachment : "첨부파일 없음"
	},
	datepicker : {	// jquery ui datepicker
		langCode : "ko",
		btnClose : "닫기",
		btnPrev : "이전달",
		btnNext : "다음달",
		btnCurrentDate : "오늘",
		lblMonthNames : ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
		lblMonthNamesShort : ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
		lblDayNames : ["일","월","화","수","목","금","토"],
		lblDayNamesShort: ["일","월","화","수","목","금","토"],
		lblDayNamesMin: ["일","월","화","수","목","금","토"],
		lblYear : " "
	},
	userContextMenu : {
		close : "닫기",
		profile : "프로필",
		sms : "문자메세지",
		message : "쪽지"
	},
	tagging : {	// jquery ui tagging
		required : "태그를 입력해 주십시오.",
		maxlength:"100자 이내로 입력가능 합니다.",
		tagCount:"태그를 10개 이하로 넣어주세요.",
		tagDuplicate:"태그가 중복되어 있습니다.",
		tagNotice:"태그를 입력해 주십시오.",
		save:"저장",
		cancel:"취소",
		edit:"수정",
		tagWord:"알파벳/숫자, 한글, 콤마, 공백만 가능합니다."
	},
	sms : {
		monthSmsCount : "당월발송건수를 초과하였습니다. 당월 최대 발송가능건수는 {}건입니다. 확인 후 다시 시도하십시오.",
		sendSuccess : "전송완료했습니다."		
	},
	searchUserGroup : {
		duplicateUser : "사용자가 중복 되었습니다.",
		duplicateGroup : "그룹이 중복 되었습니다."
	},
	meetingroom : {
		btnText: {
			save: "저장"
		},
		labelText: {
			daily: "일마다",
			weekly: "주마다",
			monthly: "월마다",
			yearly: "년마다",
			month: "월",
			date_1: "일",
			dow: "요일",
			last: "마지막",
			lastDate: "마지막 날",
			nth: "번째",
			am: "오전",
			pm: "오후",
			schedule: "일정",
			emptySchedule: "빈 일정 입니다.",
			alarmTypes: ["메일", "SMS", "쪽지"],
			repeatTypes: ['일', '주', '개월', '년'],
			repeatSchedule: "반복일정",
			open: "공개",
			noopen: "비공개",
			every: "매",
			unit: "마다",
			from: "부터",
			untilRepeat: "까지 반복",
			forever: "무한반복",
			beforeMin: "분 전",
			unknown: "미정",
			attendance: "참여",
			noattendance: "불참",
			allday: "종일",
			holiday: "공휴일",
			date: "날짜",
			add: "추가",
			period: "기간",
			title: "제목",
			allDayFull: "종일일정",
			notAllDayFull: "시간일정",
			CAT: "범주",
			totalCountDesc: "총 {} 명",
			reason : "사유",
			total : "전체"
		},
		messageText : {
			inputTitle: "제목을 입력하여 주세요.",
			inputCycle: "반복주기를 입력하여 주세요.",
			inputCycleDigits: "숫자만 입력할 수 있습니다.",
			doSendMail: "참여자와 참조자에게 메일을 발송하시겠습니까?",
			noAddOwnerToParticipant: "일정 등록자는 참여자/참조자로 추가할 수 없습니다.",
			noUser: "해당 사용자가 없습니다.",
			noResult: "검색 결과가 없습니다.",
			maxTitle: "제목은 최대 300자 까지 가능 합니다.",
			checkTimePick: "예약 시작 시간은 종료 시간보다 작아야 합니다.",
			noReservePastDate: "지난 날짜에 회의실을 예약할 수 없습니다.",
			noReservePastTime: "지난 시간에는 회의실을 예약할 수 없습니다.",
			noReserveReadyTime : "최소 %w분 이전에 예약하여야 합니다.",
			duplicateReserve: "이미 예약된 시간을 포함하고 있습니다.",
			availableReserve : "예약 가능합니다."
		}
	},
	planner: { // 일정관리
		buttonText: {  // fullCalendar 전용
			prev: "이전",
			next: "다음",
			prevYear: "&nbsp;&lt;&lt;&nbsp;",
			nextYear: "&nbsp;&gt;&gt;&nbsp;",
			today: "오늘",
			month: "월간",
			week: "주간",
			day: "일간",
			listMonth: "목록"
		},			
		titleFormat: {	// fullCalendar 전용
			month: 'yyyy.MM',
			week: "yyyy.MM.dd{ ~ MM.dd [yyyy]}",
			day: "yyyy.MM.dd &#40;ddd&#41;"
		},		
		columnFormat: {	// fullCalendar 전용
			month: 'ddd',
			week: "'<span class=num>'dd'</span>' &#40;ddd&#41;",
			day: "'<span class=num>'MM.dd'</span>' &#40;ddd&#41;"
		},		
		btnText: {	// ikep 용
			update: "수정",
			save: "저장",
			remove: "삭제",
			scheduleList: "일정목록",
			ok: "확인",
			cancel: "취소",
			viewReason : "사유보기"
		},
		labelText: {
			daily: "일마다",
			weekly: "주마다",
			monthly: "월마다",
			yearly: "년마다",
			month: "월",
			date_1: "일",
			dow: "요일",
			last: "마지막",
			lastDate: "마지막 날",
			nth: "번째",
			am: "오전",
			pm: "오후",
			schedule: "일정",
			emptySchedule: "빈 일정 입니다.",
			alarmTypes: ["메일", "SMS", "쪽지","Push"],
			repeatTypes: ['일', '주', '개월', '년'],
			repeatSchedule: "반복일정",
			open: "공개",
			noopen: "비공개",
			every: "매",
			unit: "마다",
			from: "부터",
			untilRepeat: "까지 반복",
			forever: "무한반복",
			beforeMin: "분 전",
			unknown: "미정",
			attendance: "참여",
			noattendance: "불참",
			allday: "종일",
			holiday: "공휴일",
			date: "날짜",
			add: "추가",
			period: "기간",
			title: "제목",
			allDayFull: "종일일정",
			notAllDayFull: "시간일정",
			CAT: "범주",
			totalCountDesc: "총 {} 명",
			reason : "사유",
			total : "전체"
		},
		titleText: {
			checkParticipant: "참여자 일정 확인",
			emptySchedule: "비어있는 일정",
			companySchedule: "전사 일정",
			mySchedule: "나의 일정",
			mandatorSchedule: "위임자 일정",
			teamSchedule: "팀 공유 일정",
			userSchedule: "개별 사용자 일정",
			privateSchedule: "비공개 일정",
			memberSchedule: "구성원 일정",
			viewDetailEvent: "일정 상세 보기",
			noattendanceTitle: "불참 사유 작성"
		},
		messageText: {
			inputTitle: "제목을 입력하여 주세요.",
			inputCycle: "반복주기를 입력하여 주세요.",
			inputCycleDigits: "숫자만 입력할 수 있습니다.",
			doSendMail: "참여자와 참조자에게 메일을 발송하시겠습니까?",
			checkTrustee: "수임자명을 확인해주세요.",
			emptySchedule: "일정이 없습니다.",
			emptyTodaySchedule: "오늘 등록된 일정이 없습니다.",			
			showDetail: "일정 상세보기",
			noUser: "해당 사용자가 없습니다.",
			noResult: "검색 결과가 없습니다.",
			selectHoliday: "삭제할 휴일을 선택하여 주십시오.",
			confirmDeleteHoliday: "선택한 휴일을 삭제하시겠습니까?",
			selectCategory: "삭제할 범주를 선택하여 주십시오.",
			selectCompany: "삭제할 전사일정을 선택하여 주십시오.",
			selectCompanyApprove: "확정할 전사일정을 선택하여 주십시오.",
			confirmDeleteCategory: "선택한 범주를 삭제하시겠습니까? 삭제된 범주의 기존 일정은 업무 범주로 변경됩니다.",
			confirmDeleteSchedule: "선택한 일정을 삭제하시겠습니까?",
			confirmApproveSchedule: "선택한 전사일정을 확정하시겠습니까?",
			clickSearchMessage: "주소록에서 등록 후 Search 버튼을 클릭해주세요.",
			maxTitle: "제목은 최대 300자 까지 가능 합니다.",
			checkRepeatDay: "반복 요일을 선택해 주세요.",
			confirmDeleteMantator: "수임자를 삭제하시겠습니까?",
			noAddOwnerToParticipant: "일정 등록자는 참여자/참조자로 추가할 수 없습니다.",
			repeatUpdateDialogTitle : "반복 일정 수정",
			repeatUpdateOptionSelect : "수정 옵션을 선택해주세요.",
			repeatDeletDialogTitle : "반복 일정 삭제",
			repeatDeleteOptionSelect : "삭제 옵션을 선택해주세요.",
			repeatUpdateConfirm : "반복일정을 수정하시겠습니까?"
		},
		errorText: {
			unknowError: "장애발생",
			failUpload: "파일 업로드 실패",
			duplicateName: "해당 국가에 동일한 휴일명이 등록 되어 있습니다.",
			duplicateCategoryName: "동일한 범주명이 등록 되어 있습니다.",
			eventDeleted : "이미 삭제된 건입니다.",
			meetingRoomDuplicate : "선택하신 회의실은 이미 예약 되었습니다.\n\n다른 시간이나 다른 회의실을 선택해주세요."
		}
	},
	mainPortlet : {	//포틀릿
		more : "더보기",
		config : "설정",
		reload : "새로고침",
		maximize : "큰창",
		minimize : "숨기기",
		restore : "보통창",
		help : "도움말",
		close : "삭제",
		titHelpPopup : " 도움말",
		configSuccessMessage : "설정되었습니다.",
		configFailMessage : "설정에 실패했습니다.",
		confirmRemoveMessage : "삭제 하시겠습니까?",
		personalPortletMessage : "개인용 포틀릿은 공용 영역에 올릴 수 없습니다.",
		publicPortletMessage : "공용 포틀릿은 개인 영역에 올릴 수 없습니다.",
		noPage : "페이지를 표시할수 없습니다."
	},
	clock : {
		sDayName : ["일", "월", "화", "수", "목", "금", "토"],
		fDayName : ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"],
		sMonthName : ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
		fMonthName : ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
		am : "오전",
		pm : "오후",
		AM : "오전",
		PM : "오후",
		subscriptYear : "년",
		subscriptMonth : "월",
		subscriptDay : "일"
	},
	gallery : {
		imgCount : "#{cur}/#{tot}"
	},
	ieCheck : {
		msgCompatible :  "브라우저의 \"호환성보기\"설정시 일부기능이 올바로 동작하지 않을 수 있습니다.<br/>브라우저 모드를 호환성 보기가 아닌 IE8 이상으로 설정해주세요.<br/>[<a href=\"#{compatibleUrl}\" traget=\"_blank\">설정방법 보기</a>]",
		msgUpgrade : "Internet Explorer 브라우저 사용을 원하신다면 브라우저 버전 8이상을 사용하셔야 정상적으로 이용하실 수 있습니다.<br/>[<a href=\"#{upgradeUrl}\" traget=\"_blank\">업그레이드하기</a>]<br/>만일, 이미 IE 8 이상을 사용 중 이시라면 개발자도구의 브라우저모드 및 문서모드를 IE8 이상으로 설정하셔야 합니다.<br/>[<a href=\"#{modeUrl}\" traget=\"_blank\">설정방법 보기</a>]"
	}
};