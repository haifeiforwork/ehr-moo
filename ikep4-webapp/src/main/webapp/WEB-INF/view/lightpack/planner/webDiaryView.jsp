<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
<% response.setContentType("text/html; charset=utf-8"); %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>

<%--메시지 관련 Prefix 선언 Start--%>
<c:set var="preMessagePlanner"  value="ui.lightpack.planner.common.message" />
<c:set var="preLeftMenuPlanner" value="ui.lightpack.planner.leftmenu" />
<c:set var="preScriptPlanner"   value="ui.lightpack.planner.common.script" />
<c:set var="preCommonPlanner"   value="ui.lightpack.planner.common" />
<c:set var="preDialogPlanner"   value="ui.lightpack.planner.dialog" />
<c:set var="preSearch"          value="ui.ikep4.common.searchCondition" /> 

<%--
<c:set var="prefixNote" value="ui.support.note.portalNote"/>
<c:set var="preMessageNote" value="message.support.common.note" />
--%>
<%--메시지 관련 Prefix 선언 End--%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/webdiary.css'/>" />
<%--
<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/note.css'/>" />
--%>

<script language="JavaScript">
	function bluring(){
		if(event.srcElement.tagName=="A"||event.srcElement.tagName=="IMG")
			document.body.focus();
	}
	
	document.onfocusin=bluring;  //메뉴클릭시 점선 안보이게 //
	
	function MM_preloadImages() { //v3.0
		var d=document; 
		if(d.images){ 
			if(!d.MM_p) d.MM_p=new Array();
			var i,j=d.MM_p.lengtd,a=MM_preloadImages.arguments; for(i=0; i<a.lengtd; i++)
			if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}
		}
	}
	
	function MM_swapImgRestore() { //v3.0
		var i,x,a=document.MM_sr; 
		for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
	}
	
	function MM_findObj(n, d) { //v4.01
		var p,i,x;  
		if(!d) d=document; 
		if((p=n.indexOf("?"))>0&&parent.frames.length) {
			d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);
		}
		if(!(x=d[n])&&d.all) x=d.all[n]; 
		for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
		for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
		if(!x && d.getElementById) x=d.getElementById(n); 
		
		return x;
	}
	
	function MM_swapImage() { //v3.0
		var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
		if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
	}
</script>

<script type="text/javascript">
<!--
	var taskKey = "";
	var workspaceId = "";
	var selectDate = "";
	var sGbn = "";
	var aday=new Array('일','월','화','수','목','금','토');

	(function($){
		$(document.body).ready(function() {

			//Date 선언
			var date = new Date();
			//달 구하기
			var month = date.getMonth()+1;
			//요일 인덱스 구하기
			var day=date.getDay();
			//년+월+일+요일
			var day = date.getFullYear()+'.'+month+'.'+date.getDate()+"("+aday[date.getDay()]+")";
			
			selectDate = "${nowDay}";
			sGbn = "${sGubun}";
			workspaceId = "${workspaceId}";

			/*
			if (selectDate != ""){
			    dateParts = selectDate.match(/(\d+)/g);
			    realDate = new Date(dateParts[0], dateParts[1] - 1, dateParts[2]);  

			    //$('#diaryCalendar').datepicker({ dateFormat: 'yy/mm/dd' }); // format to show
			    //$("#diaryCalendar").datepicker("setDate", realDate);
			};
			*/
			
			$("#diaryCalendar").datepicker({
				onSelect: function(dateText, inst) {
					getCalSchedule(dateText, sGbn);
					//getCalScheduleList(dateText, sGbn);
					//getMyTaskList(dateText, sGbn);
				}
			});
			
			$("#btnBefore").click(function(){
			    var dateParts = $("#diaryCalendar").val().match(/(\d+)/g);
				var x = new Date(dateParts[0], dateParts[1] - 1, dateParts[2]);
				var dayOfMonth = x.getDate();
				x.setDate(dayOfMonth - 1);
		        $( "#diaryCalendar" ).datepicker( "setDate", x );
				getCalSchedule($("#diaryCalendar").val(), sGbn);
				//getCalScheduleList($("#diaryCalendar").val(), sGbn);
				//getMyTaskList($("#diaryCalendar").val(), sGbn);
			});
	        
			$("#btnNext").click(function(){
			    var dateParts = $("#diaryCalendar").val().match(/(\d+)/g);
				var x = new Date(dateParts[0], dateParts[1] - 1, dateParts[2]);
				var dayOfMonth = x.getDate();
				x.setDate(dayOfMonth + 1);
		        $( "#diaryCalendar" ).datepicker( "setDate", x );
				getCalSchedule($jq("#diaryCalendar").val(), sGbn);
				//getCalScheduleList($jq("#diaryCalendar").val(), sGbn);
				//getMyTaskList($("#diaryCalendar").val(), sGbn);
			});
			
			$("#diaryCalendar").datepicker().next().eq(0).click(function() { 
				$(this).prev().eq(0).datepicker("show"); 
			});
			
		    /* todo function */
			//나의 업무 등록 화면 표시 
			$('#btnTaskCreate').click(function(){
				getTaskCreate();
			});
		    
		    /* todo function */
			
			/* Schedule Function */
			//일정 등록 화면 표시 
			$('#btnCreate').click(function(){
				getScheduleCreate();
			});
		    
			//팀일정 조회
			$('#btnTeamPlan').click(function(){
				sGbn = "workspace";
				//getCalSchedule(selectDate,sGbn);
				getCalScheduleList(selectDate,sGbn);
			});

			//개인일정 조회
			$('#btnUserPlan').click(function(){
				sGbn = "personal";
				//getCalSchedule(selectDate,sGbn);
				getCalScheduleList(selectDate,sGbn);
			});
			
			// 일정 내역 조회
			$("span.space_left").live("click", function(){
				var scheduleId = $(this).attr("id");
				getScheduleView(scheduleId);
			});
			
			
			/* Schedule Function */


		});
		
	})(jQuery);

	function trim(s) {
		s += ''; // 숫자라도 문자열로 변환
		return s.replace(/^\s*|\s*$/g, '');
	}

	/* javascript */
	// 나의 업무, 업무 지시 내용 조회화면 호출
	function getTaskDetail(taskKey) {
		$jq.ajax({
			url : "<c:url value='/lightpack/todo/webDiaryReadTodoView.do'/>",
			data : {taskId:taskKey},
			type : "post",
			//loadingElement : {container : $jq("#todoDetail")},
			dataType : "html",
			success : function(result) {
				$jq("#scheduleCreate").css("display", "none");
				$jq("#todoCreate").css("display", "none");
				$jq("#todoDetail").css("display", "block");

				$jq("#todoViewDiv").html(result);
			},
			error:function(request,status,error){
		        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	};
	
	// 나의 업무 등록 화면을 호출
	function getTaskCreate() {
		$jq.ajax({
			url : "<c:url value='/lightpack/todo/webDiaryCreateTodoView.do'/>",
			type : "post",
			//loadingElement : {container : $jq("#todoCreateDiv")},
			dataType : "html",
			success : function(result) {
				$jq("#scheduleCreate").css("display", "none");
				$jq("#todoCreate").css("display", "block");
				$jq("#todoDetail").css("display", "none");

				$jq("#todoCreateDiv").html(result);
			},
			error:function(request,status,error){
		        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	};

	// 나의 업무 수정화면에서 호출
	function updateMyTask(taskKey, todoSearchConditionString){
		$jq.ajax({
			url : "<c:url value='/lightpack/todo/webDiaryUpdateTodoView.do'/>",
			data : {taskId:taskKey, searchConditionString:todoSearchConditionString},
			type : "post",
			//loadingElement : {container : $jq("#todoDetail")},
			dataType : "html",
			success : function(result) {
				$jq("#scheduleCreate").css("display", "none");
				$jq("#todoCreate").css("display", "none");
				$jq("#todoDetail").css("display", "block");

				$jq("#todoViewDiv").html(result);
			},
			error:function(request,status,error){
		        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}

	// 나의 업무 목록에서 등록
	function myTaskCreate(title){
		if (trim(title) == ""){
			return false;
		}
		
		var today = new Date();
		var x1 = new Date(today.getFullYear(), today.getMonth(), today.getDate());
	    var dateParts = $jq("#diaryCalendar").val().match(/(\d+)/g);
		var x2 = new Date(dateParts[0], dateParts[1] - 1, dateParts[2]);
		
		if( x1 > x2 )
		{
			alert("등록일이 현재보다 과거입니다.");
			return false;
		}

		var strDate = "";
		if (selectDate == ""){
			strDate = $jq("#diaryCalendar").val();
		}else{
			strDate = selectDate;
		}
		
		$jq.ajax({
			url : "<c:url value='/lightpack/todo/webDiaryCreateMyTask.do'/>",
			data : {title:title, subworkCode:"MYTASK", sDate:strDate},
			type : "post",
			//loadingElement : {container : $jq("#todoViewDiv")},
			dataType : "html",
			success : function(result) {
				$jq("#web_diary").html(result);
				//$jq("#todoViewDiv").html(result);
			},
			error:function(request,status,error){
		        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	
	// 나의 업무 목록에서 수정
	function myTaskUpdate(title, taskKey){
		$jq.ajax({
			url : "<c:url value='/lightpack/todo/webDiaryChangeMyTask.do'/>",
			data : {title:title, taskId:taskKey},
			type : "post",
			//loadingElement : {container : $jq("#todoViewDiv")},
			dataType : "html",
			success : function(result) {
				$jq("#web_diary").html(result);
				//$jq("#todoViewDiv").html(result);
			},
			error:function(request,status,error){
		        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});

	}
	
	// 일정 등록 화면 표시
	function getScheduleCreate() {
		var today = new Date();
		$jq("#todoDetail").css("display", "none");
		$jq("#todoCreate").css("display", "none");
		$jq("#scheduleCreate").css("display", "block");
		$jq("#createPlan").css("display", "block");
		$jq("#viewPlan").css("display", "none");
		
		$jq.ajax({
			url : "<c:url value='/lightpack/planner/calendar/webDiaryCalendar.do'/>",
			data : {startDate:today.toString(), endDate:today.toString(), scheduleId:"", workspaceId:workspaceId},
			type : "post",
			loadingElement : {container : $jq("#scheduleCreateDiv")},
			dataType : "html",
			success : function(result) {
				$jq("#scheduleCreateDiv").html(result);
			},
			error:function(request,status,error){
		        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	};
	
	// 일정 조회 화면 표시
	function getScheduleView(scheduleId) {
		var today = new Date();
		$jq("#todoDetail").css("display", "none");
		$jq("#todoCreate").css("display", "none");
		$jq("#scheduleCreate").css("display", "block");
		$jq("#createPlan").css("display", "none");
		$jq("#viewPlan").css("display", "block");

		$jq.ajax({
			url : "<c:url value='/lightpack/planner/calendar/webDiaryCalendarView.do'/>",
			data : {startDate:today.toString(), endDate:today.toString(), scheduleId:scheduleId, workspaceId:workspaceId},
			type : "post",
			loadingElement : {container : $jq("#scheduleCreateDiv")},
			dataType : "html",
			success : function(result) {
				$jq("#scheduleCreateDiv").html(result);
			},
			error:function(request,status,error){
		        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});

	};
	
	// 지정일자의 일정 조회(전체 - 팀일정, 나의일정 변경 조회 시 사용)
	function getCalSchedule(strDate,scheduleGbn){
		var schGbn = scheduleGbn;

		if (strDate == "") strDate = $jq("#diaryCalendar").val();
		if (schGbn == "") schGbn = sGbn;

		$jq.ajax({
			url : "<c:url value='/lightpack/planner/webDiary/getDateSchedule.do'/>",
			data : {sDate:strDate, sGubun:schGbn},
			type : "post",
			loadingElement : {container : $jq("#tblPlanList")},
			//dataType : "html",
			success : function(result) {
				//console.log(result);
				//$jq("#web_diary").html(result);

				selectDate = result.nowDay;
				sGbn = result.sGubun;
				workspaceId = result.workspaceId;
				// 스케줄 표시
				$jq(".sched_inner_table tr").remove();
				var scheduleList = result.searchSchedule;
				var tab, con;
				if (result.success == "success" && scheduleList != null){
					if (scheduleList.length > 0){
						for (var i=0;i<scheduleList.length;i++){
							tab = "tbl_"+scheduleList[i].timeGbn;
							con = "";
							if (scheduleList[i].categoryId == '1'){
								con = '<tr><td style="background-color:#7B8493; color: white;"><span class="space_left" id="'+scheduleList[i].scheduleId+'" value="'+scheduleList[i]+'">'+scheduleList[i].title+'</span></td></tr>';
							} else if (scheduleList[i].categoryId == '2'){
								con = '<tr><td style="background-color:#F3672A; color: white;"><span class="space_left" id="'+scheduleList[i].scheduleId+'" value="'+scheduleList[i]+'">'+scheduleList[i].title+'</span></td></tr>';
							}
							$jq("#"+tab).append(con);
						}
					}
				}
				
				// 나의업무 표시
				var todoList = result.searchTodo;
				var listCon, listCon1, listCon2, listCon3;
				//console.log(list);
				$jq(".todo_list_line").remove();
				for (var i=0;i<18;i++){
					listCon = "";
					listCon1 = "";
					listCon2 = "";
					listCon3 = "";
					if (todoList != null){
						//console.log(list[i]);
						if (todoList[i] != null || todoList[i] != undefined) {
							if (todoList[i].subworkCode != 'MYTASK') {
								listCon1 = '<span style="padding-left:7px; color: #3967B2; border: 0px;" value="'+todoList[i].subworkCode+'">지시</span>';
							} else if (todoList[i].subworkCode == 'MYTASK') {
								listCon1 = '<span style="padding-left:7px; border: 0px;" value="'+todoList[i].subworkCode+'">본인</span>';
							}
				
							if (todoList[i].subworkCode != 'MYTASK') {
								listCon2 = '<span id="'+todoList[i].taskKey+'" style="cursor: pointer; padding-left:17px; border: 0px solid #ff0000; color: #3967B2;" value="'+todoList[i].subworkCode+'" onclick="javascript:getTaskDetail('+todoList[i].taskKey+');" title="'+todoList[i].title+'">'+todoList[i].title+'</span>';
							} else if (todoList[i].subworkCode == 'MYTASK') {
								listCon2 = '<span id="'+todoList[i].taskKey+'" style="cursor: pointer; padding-left:17px; border: 0px solid #ff0000;" value="'+todoList[i].subworkCode+'" onclick="javascript:getTaskDetail('+todoList[i].taskKey+');" title="'+todoList[i].title+'">'+todoList[i].title+'</span>';
							}
							listCon = '<div class="todo_list_line">'+listCon1+listCon2+'</div>';
							$jq("#frmTodoList").append(listCon);
						} else {
							//con3 = '<input class="diary_input" type="text" name="textfield_'+i+'" id="textfield_'+i+'" size="20" onkeypress="if(event.keyCode==13 || event.keyCode==9){ myTaskCreate(this.value);}" onblur="myTaskCreate(this.value);">';
							listCon3 = '<input class="diary_input" type="text" name="textfield_'+i+'" id="textfield_'+i+'" size="20" onkeypress="if(event.keyCode==13 || event.keyCode==9){ myTaskCreate(this.value);}">';
							listCon = '<div class="todo_list_line">'+listCon3+'</div>';
							$jq("#frmTodoList").append(listCon);
						}
					} else {
						//con3 = '<input class="diary_input" type="text" name="textfield_'+i+'" id="textfield_'+i+'" size="20" onkeypress="if(event.keyCode==13 || event.keyCode==9){ myTaskCreate(this.value);}" onblur="myTaskCreate(this.value);">';
						listCon3 = '<input class="diary_input" type="text" name="textfield_'+i+'" id="textfield_'+i+'" size="20" onkeypress="if(event.keyCode==13 || event.keyCode==9){ myTaskCreate(this.value);}">';
						listCon = '<div class="todo_list_line">'+listCon3+'</div>';
						$jq("#frmTodoList").append(listCon);
					}
				}

				// 구분별 화면 표시
				if (schGbn === "workspace"){
					$jq("#userTle").css("display", "none");
					$jq("#userBtn").css("display", "block");
					$jq("#teamTle").css("display", "block");
					$jq("#teamBtn").css("display", "none");
				}else{
					$jq("#teamTle").css("display", "none");
					$jq("#teamBtn").css("display", "block");
					$jq("#userTle").css("display", "block");
					$jq("#userBtn").css("display", "none");
				}
			},
			error:function(request,status,error){
		        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	
	// 지정일자의 일정 조회(목록)
	function getCalScheduleList(strDate,scheduleGbn){
		var schGbn = scheduleGbn;

		if (strDate == "") strDate = $jq("#diaryCalendar").val();
		
		$jq(".sched_inner_table tr").remove();

		$jq.ajax({
			url : "<c:url value='/lightpack/planner/webDiary/getDateScheduleList.do'/>",
			data : {sDate:strDate, sGubun:schGbn},
			type : "post",
			loadingElement : {container : $jq("#tblPlanList")},
			//dataType : "html",
			success : function(result) {
				selectDate = result.nowDay;
				sGbn = result.sGubun;
				workspaceId = result.workspaceId;
				var scheduleList = result.searchSchedule;
				var tab, con;
				if (result.success == "success" && scheduleList != null){
					if (scheduleList.length > 0){
						for (var i=0;i<scheduleList.length;i++){
							tab = "tbl_"+scheduleList[i].timeGbn;
							con = "";
							if (scheduleList[i].categoryId == '1'){
								con = '<tr><td style="background-color:#7B8493; color: white;"><span class="space_left" id="'+scheduleList[i].scheduleId+'" value="'+scheduleList[i]+'">'+scheduleList[i].title+'</span></td></tr>';
							} else if (scheduleList[i].categoryId == '2'){
								con = '<tr><td style="background-color:#F3672A; color: white;"><span class="space_left" id="'+scheduleList[i].scheduleId+'" value="'+scheduleList[i]+'">'+scheduleList[i].title+'</span></td></tr>';
							}
							$jq("#"+tab).append(con);
						}
					}
				}
				
				if (schGbn === "workspace"){
					$jq("#userTle").css("display", "none");
					$jq("#userBtn").css("display", "block");
					$jq("#teamTle").css("display", "block");
					$jq("#teamBtn").css("display", "none");
				}else{
					$jq("#teamTle").css("display", "none");
					$jq("#teamBtn").css("display", "block");
					$jq("#userTle").css("display", "block");
					$jq("#userBtn").css("display", "none");
				}
			},
			error:function(request,status,error){
		        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	
	// 나의 업무 목록 조회
	function getMyTaskList(strDate, scheduleGbn){
		var schGbn = scheduleGbn;

		if (strDate == "") strDate = $jq("#diaryCalendar").val();

		$jq.ajax({
			url : "<c:url value='/lightpack/planner/webDiary/getMyTaskList.do'/>",
			data : {sDate:strDate, sGubun:schGbn},
			type : "post",
			loadingElement : {container : $jq("#frmTodoList")},
			//dataType : "html",
			success : function(result) {
				selectDate = result.nowDay;
				sGbn = result.sGubun;
				workspaceId = result.workspaceId;
				var list = result.searchTodo;
				var con,con1,con2,con3;
				//console.log(list);
				$jq(".todo_list_line").remove();
				for (var i=0;i<18;i++){
					con = "";
					con1 = "";
					con2 = "";
					con3 = "";
					if (list != null){
						//console.log(list[i]);
						if (list[i] != null || list[i] != undefined) {
							if (list[i].subworkCode != 'MYTASK') {
								con1 = '<span style="padding-left:7px; color: #3967B2; border: 0px;" value="'+list[i].subworkCode+'">지시</span>';
							} else if (list[i].subworkCode == 'MYTASK') {
								con1 = '<span style="padding-left:7px; border: 0px;" value="'+list[i].subworkCode+'">본인</span>';
							}
				
							if (list[i].subworkCode != 'MYTASK') {
								con2 = '<span id="'+list[i].taskKey+'" style="cursor: pointer; padding-left:17px; border: 0px solid #ff0000; color: #3967B2;" value="'+list[i].subworkCode+'" onclick="javascript:getTaskDetail('+list[i].taskKey+');" title="'+list[i].title+'">'+list[i].title+'</span>';
							} else if (list[i].subworkCode == 'MYTASK') {
								con2 = '<span id="'+list[i].taskKey+'" style="cursor: pointer; padding-left:17px; border: 0px solid #ff0000;" value="'+list[i].subworkCode+'" onclick="javascript:getTaskDetail('+list[i].taskKey+');" title="'+list[i].title+'">'+list[i].title+'</span>';
							}
							con = '<div class="todo_list_line">'+con1+con2+'</div>';
							$jq("#frmTodoList").append(con);
						} else {
							//con3 = '<input class="diary_input" type="text" name="textfield_'+i+'" id="textfield_'+i+'" size="20" onkeypress="if(event.keyCode==13 || event.keyCode==9){ myTaskCreate(this.value);}" onblur="myTaskCreate(this.value);">';
							con3 = '<input class="diary_input" type="text" name="textfield_'+i+'" id="textfield_'+i+'" size="20" onkeypress="if(event.keyCode==13 || event.keyCode==9){ myTaskCreate(this.value);}">';
							con = '<div class="todo_list_line">'+con3+'</div>';
							$jq("#frmTodoList").append(con);
						}
					} else {
						//con3 = '<input class="diary_input" type="text" name="textfield_'+i+'" id="textfield_'+i+'" size="20" onkeypress="if(event.keyCode==13 || event.keyCode==9){ myTaskCreate(this.value);}" onblur="myTaskCreate(this.value);">';
						con3 = '<input class="diary_input" type="text" name="textfield_'+i+'" id="textfield_'+i+'" size="20" onkeypress="if(event.keyCode==13 || event.keyCode==9){ myTaskCreate(this.value);}">';
						con = '<div class="todo_list_line">'+con3+'</div>';
						$jq("#frmTodoList").append(con);
					}
				}
			},
			error:function(request,status,error){
		        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	
	function closeMyTask(){
		$jq("#todoDetail").css("display", "none");
		$jq("#todoCreate").css("display", "none");
	}
	
//-->
</script>

<body id="web_diary" onLoad="MM_preloadImages('<c:url value="/base/images/webDiary/btn1_on.jpg"/>','<c:url value="/base/images/webDiary/btn2_on.jpg"/>','<c:url value="/base/images/webDiary/btn3_on.jpg"/>')" >
	<div id="wrap">
		<div id="content">
			<div id="todoArea" class="left_area">
				<div id="todoList" class="todo_list">

		            <table width="180" border="0"cellspacing=0>
						<tr>
							<td height="39" ><img src='<c:url value="/base/images/webDiary/plan_tle2.png"/>' border=0></td>
							<td align="right" valign="middle" ><a href="#"><img src='<c:url value="/base/images/webDiary/btn1.jpg"/>' border=0 style="cursor: pointer;" id="btnTaskCreate" onMouseOver="MM_swapImage('btnTaskCreate','','<c:url value="/base/images/webDiary/btn1_on.jpg"/>',1)" onMouseOut="MM_swapImgRestore()"></a></td>
						</tr>
		            </table>

					<form id="frmTodoList" name="frmTodoList" metdod="post" action="">
						<c:forEach begin="0" end="18" varStatus="status">
							<div class="todo_list_line">
								<c:choose>
									<c:when test="${searchTodo[status.index].title ne null}">
										<c:if test="${searchTodo[status.index].subworkCode ne 'MYTASK'}">
											<span style="padding-left:7px; border: 0px; color: #3967B2;" value="${searchTodo[status.index].subworkCode}">지시</span>
										</c:if>
										<c:if test="${searchTodo[status.index].subworkCode eq 'MYTASK'}">
											<span style="padding-left:7px; border: 0px;" value="${searchTodo[status.index].subworkCode}">본인</span>
										</c:if>
									</c:when>
								</c:choose>

								<c:choose>
									<c:when test="${searchTodo[status.index].title ne null}">
										<c:if test="${searchTodo[status.index].subworkCode ne 'MYTASK'}">
											<span id="${searchTodo[status.index].taskKey}" style="cursor: pointer; padding-left:17px; border: 0px solid #ff0000; color: #3967B2;" value="${searchTodo[status.index].subworkCode}" 
												onclick="javascript:getTaskDetail('${searchTodo[status.index].taskKey}');" title="${searchTodo[status.index].title}" >${searchTodo[status.index].title}</span>
										</c:if>
										<c:if test="${searchTodo[status.index].subworkCode eq 'MYTASK'}">
											<span id="${searchTodo[status.index].taskKey}" style="cursor: pointer; padding-left:17px; border: 0px solid #ff0000;" value="${searchTodo[status.index].subworkCode}"
												onclick="javascript:getTaskDetail('${searchTodo[status.index].taskKey}');" title="${searchTodo[status.index].title}" >${searchTodo[status.index].title}</span>
										</c:if>
									</c:when>
									<c:otherwise>
										<%--
										<input class="diary_input" type="text" name="textfield_${status.index}" id="textfield_${status.index}" size="20" onkeypress="if(event.keyCode==13 || event.keyCode==9){ myTaskCreate(this.value);}" onblur="myTaskCreate(this.value);"> 
										--%>
										<input class="diary_input" type="text" name="textfield_${status.index}" id="textfield_${status.index}" size="20" onkeypress="if(event.keyCode==13 || event.keyCode==9){ myTaskCreate(this.value);}"> 
									</c:otherwise>
								</c:choose>
							</div>
						</c:forEach>
					</form> <!-- frmTodoList -->
				</div> <!-- todoList -->
			</div> <!-- leftArea -->
		
			<div class="plan_date_select">
				<table width="210" height="25" border="0" cellpadding=0 cellspacing=0>
					<tr>
						<td width="32" height="20" align="right" valign="middle" style="padding-top: 2px;"><img id="btnBefore" src='<c:url value="/base/images/webDiary/btn_before.gif"/>' style="cursor: pointer;" onMouseOver="MM_swapImage('btnBefore','','<c:url value="/base/images/webDiary/btn_before_on.gif"/>',1)" onMouseOut="MM_swapImgRestore()"></td>
						<td width="150" align="center" valign="middle" class="plan_date_font"><input type="text" class="inputbox plan_date_font" readonly="readonly" id="diaryCalendar" name="diaryCalendar" value="${nowDay}" size="10" style="border: 0px;background: none;font-weight: bold;text-align: center;"/>&nbsp;<img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" style="cursor:pointer;"/></td>
						<td width="28" valign="middle" style="padding-top: 2px;"><img id="btnNext" src='<c:url value="/base/images/webDiary/btn_next.gif"/>' style="cursor: pointer;" onMouseOver="MM_swapImage('btnNext','','<c:url value="/base/images/webDiary/btn_next_on.gif"/>',1)" onMouseOut="MM_swapImgRestore()"></td>
					</tr>
				</table>
			</div>   
			<%--
			<div class="plan_date_select">
				<table width="210" height="25" border="0" cellpadding=0 cellspacing=0>
					<tr>
						<td width="32" height="20" align="right" ><img id="btnBefore" src='<c:url value="/base/images/webDiary/btn_before.gif"/>'  onMouseOver="MM_swapImage('btnBefore','','<c:url value="/base/images/webDiary/btn_before_on.gif"/>',1)" onMouseOut="MM_swapImgRestore()"></td>
						<td width="114" align="center" valign="top"><input type="text" class="inputbox plan_date_font" readonly id="diaryCalendar" name="diaryCalendar" value="${nowDay}" size="10"/> </td>
						<td width="36" valign="middle"><img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${prefix}' key='search.calendar'/>"  style="cursor:pointer;"/></td>
						<td width="28"><img id="btnNext" src='<c:url value="/base/images/webDiary/btn_next.gif"/>'  onMouseOver="MM_swapImage('btnNext','','<c:url value="/base/images/webDiary/btn_next_on.gif"/>',1)" onMouseOut="MM_swapImgRestore()"></td>
					</tr>
				</table>
			</div>
			--%>

			<div id="planArea" class="plan">
				<div id="planTitle" class="plan_title">
					<%--
					<dl>
						<dt id="userTle" style="width:100px; float:left; display: block;"><img src='<c:url value="/base/images/webDiary/plan_tle1.png"/>' border=0></dt> <!-- 나의일정 -->
						<dt id="teamTle" style="width:100px; float:left; display: none;"><img src='<c:url value="/base/images/webDiary/plan_tle6.png"/>' border=0></dt> <!-- 팀일정 -->
						<dt id="userBtn" style="display: none;"><img src='<c:url value="/base/images/webDiary/btn4.jpg"/>' border=0 id="btnUserPlan" onMouseOver="MM_swapImage('btnUserPlan','','<c:url value="/base/images/webDiary/btn4_on.jpg"/>',1)" onMouseOut="MM_swapImgRestore()" ></dt>
						<dt id="teamBtn" style="display: block;"><img src='<c:url value="/base/images/webDiary/btn2.jpg"/>' border=0 id="btnTeamPlan" onMouseOver="MM_swapImage('btnTeamPlan','','<c:url value="/base/images/webDiary/btn2_on.jpg"/>',1)" onMouseOut="MM_swapImgRestore()" ></dt>
						<dt><a href="#"><img src='<c:url value="/base/images/webDiary/btn1.jpg"/>' border=0 id="btnCreate" onMouseOver="MM_swapImage('btnCreate','','<c:url value="/base/images/webDiary/btn1_on.jpg"/>',1)" onMouseOut="MM_swapImgRestore()"></a></dt>
					</dl>
					--%>
					<table width="210" border="0"cellspacing=0>
						<tr>
							<td height="39"  id="userTle" style="display:block;"><img src='<c:url value="/base/images/webDiary/plan_tle1.png"/>' border=0></td>
							<td height="39"  id="teamTle" style="display:none;" ><img src='<c:url value="/base/images/webDiary/plan_tle6.png"/>' border=0></td>
							<td align="right" valign="middle" id="userBtn" style="display: none;"><img src='<c:url value="/base/images/webDiary/btn4.jpg"/>' style="cursor: pointer;" border=0 id="btnUserPlan" onMouseOver="MM_swapImage('btnUserPlan','','<c:url value="/base/images/webDiary/btn4_on.jpg"/>',1)" onMouseOut="MM_swapImgRestore()" ></td>
							<td align="right" valign="middle" id="teamBtn" style="display: block;"><img src='<c:url value="/base/images/webDiary/btn2.jpg"/>' style="cursor: pointer;" border=0 id="btnTeamPlan" onMouseOver="MM_swapImage('btnTeamPlan','','<c:url value="/base/images/webDiary/btn2_on.jpg"/>',1)" onMouseOut="MM_swapImgRestore()" ></td>
							<td align="right" valign="middle" ><a href="#"><img src='<c:url value="/base/images/webDiary/btn1.jpg"/>' border=0 id="btnCreate" onMouseOver="MM_swapImage('btnCreate','','<c:url value="/base/images/webDiary/btn1_on.jpg"/>',1)" onMouseOut="MM_swapImgRestore()"></a></td>
						</tr>
		            </table>
				</div> <!-- planTitle -->
	            
				<table class="sched_table" id="tblPlanList" width="194" cellpadding="0" cellspacing="0" >
					<tr>
						<td height="24" colspan="2"  align="center">all day</td>
						<td width="148" align="left"  height="48">
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_00A">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '00A'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>	
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="2" align="center">07</td>
						<td height="24" align="center">00</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_07A">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '07A'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td height="24" align="center">30</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_07P">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '07P'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="2" align="center">08</td>
						<td height="24" align="center">00</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_08A">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '08A'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td height="24" align="center">30</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_08P">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '08P'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="2" align="center">09</td>
						<td height="24" align="center">00</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_09A">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '09A'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td height="24" align="center">30</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table"  id="tbl_09P">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '09P'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="2" align="center">10</td>
						<td height="24" align="center">00</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_10A">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '10A'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td height="24" align="center">30</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_10P">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx">
								<c:if test="${scheduleData.timeGbn == '10P'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="2" align="center">11</td>
						<td height="24" align="center">00</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_11A">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '11A'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td height="24" align="center">30</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_11P">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '11P'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="2" align="center">12</td>
						<td height="24" align="center">00</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_12A">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '12A'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td height="24" align="center">30</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_12P">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '12P'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="2" align="center">13</td>
						<td height="24" align="center">00</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_13A">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '13A'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td height="24" align="center">30</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_13P">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '13P'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="2" align="center">14</td>
						<td height="24" align="center">00</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_14A">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '14A'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td height="24" align="center">30</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_14P">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '14P'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="2" align="center">15</td>
						<td height="24" align="center">00</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_15A">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '15A'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td height="24" align="center">30</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_15P">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '15P'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="2" align="center">16</td>
						<td height="24" align="center">00</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_16A">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '16A'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td height="24" align="center">30</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_16P">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '16P'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="2" align="center">17</td>
						<td height="24" align="center">00</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_17A">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '17A'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td height="24" align="center">30</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_17P">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '17P'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="2" align="center">18</td>
						<td height="24" align="center">00</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_18A">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '18A'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td height="24" align="center">30</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_18P">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '18P'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="2" align="center">19</td>
						<td height="24" align="center">00</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_19A">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '19A'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td height="24" align="center">30</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_19P">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '19P'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="2" align="center">20</td>
						<td height="24" align="center">00</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_20A">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '20A'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td height="24" align="center">30</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_20P">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '20P'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="2" align="center">21</td>
						<td height="24" align="center">00</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_21A">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '21A'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td height="24" align="center">30</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_21P">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '21P'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="2" align="center">22</td>
						<td height="24" align="center">00</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_22A">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '22A'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td height="24" align="center">30</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_22P">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '22P'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td rowspan="2" align="center">23</td>
						<td height="24" align="center">00</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_23A">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '23A'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td height="24" align="center" value="23P">30</td>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="sched_inner_table" id="tbl_23P">
							<c:forEach var="scheduleData" items="${searchSchedule}" varStatus="idx" >
								<c:if test="${scheduleData.timeGbn == '23P'}">
									<c:if test="${scheduleData.categoryId == '1'}">
										<tr><td style="background-color:#7B8493; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
									<c:if test="${scheduleData.categoryId == '2'}">
										<tr><td style="background-color:#F3672A; color: white;" ><span class="space_left" id="${scheduleData.scheduleId}" value="${scheduleData}">${scheduleData.title}</span></td></tr>
									</c:if>
								</c:if>
							</c:forEach>
							</table>
						</td>
					</tr>
				</table>
			</div> <!-- plan -->

			<div id="todoDetail" class="todo_detail" style="display: none;">
				<div class="todo_detail_title">
		             <dl>
		               <dt style="width:345px; float:left"><img src='<c:url value="/base/images/webDiary/plan_tle7.png"/>' border=0></dt>
		             </dl>
		        </div>
				<div id="todoViewDiv"></div>			
			</div>
			
			<div id="todoCreate" class="todo_detail" style="display: none;">
				<div class="todo_detail_title">
		             <dl>
		               <dt style="width:345px; float:left"><img src='<c:url value="/base/images/webDiary/plan_tle10.png"/>' border=0></dt>
		             </dl>
		        </div>
				<div id="todoCreateDiv"></div>			
			</div>

			<div id="scheduleCreate" class="todo_detail" style="display: none;">
				<div class="todo_detail_title">
		             <dl>
		               <dt id="createPlan" style="width:345px; float:left"><img src='<c:url value="/base/images/webDiary/plan_tle8.png"/>' border=0></dt>
		               <dt id="viewPlan" style="width:345px; float:left; display: none;"><img src='<c:url value="/base/images/webDiary/plan_tle9.png"/>' border=0></dt>
		             </dl>
		        </div>
				<div id="scheduleCreateDiv"></div>			
			</div>
		</div> <!-- content -->
	</div> <!-- wrap -->
</body>
