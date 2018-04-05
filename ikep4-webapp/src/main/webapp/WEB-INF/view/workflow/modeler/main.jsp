<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preModeler"  value="message.workflow.modeler" />
<c:set var="preValidation"  value="validate.workflow.modeler" />
<%-- 메시지 관련 Prefix 선언 End --%>
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" style="overflow:hidden;">
<head>
	<!--상단 타이틀-->
	<c:set var="title"><tiles:getAsString name="title"/></c:set>
	<title><spring:message code="${title}" /></title>
	
	<%@ include file="/base/common/meta.jsp"%>
	
	<!--Base Contents Start-->
		<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
		<script language="javascript" src='<c:url value="/base/js/jquery/jquery.layout-1.2.0.min.js"/>'></script>
		<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-ui-1.8.13.min.without.datepicker.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.ui.datepicker.customize.pack.js"/>"></script>
		<script language="javascript" src='<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>'></script>
		<script language="javascript" src='<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>'></script>
		<script language="javascript" src='<c:url value="/base/js/common.pack.js"/>'></script>
		<script language="javascript" src='<c:url value="/base/js/workflow/xmlUtil.js"/>'></script>
		<script language="javascript" src='<c:url value="/base/js/workflow/xpdl.js"/>'></script>
		<script language="javascript" src='<c:url value="/base/js/jquery/plugins.pack.js"/>'></script>
		<script language="javascript" src='<c:url value="/base/js/etc.plugins.pack.js"/>'></script>
		<script language="javascript" src='<c:url value="/base/js/jquery/jquery.layout-latest.js"/>'></script>
		
		<link href='<c:url value="/base/css/common.css"/>' type="text/css" rel="stylesheet" />
		<link href='<c:url value="/base/css/theme01/jquery_ui_custom.css"/>' type="text/css" rel="stylesheet" />
		<link href='<c:url value="/base/css/jstree/themes/ikep/style.css"/>' type="text/css" rel="stylesheet" />
		<link href='<c:url value="/base/css/workflow/style.css"/>' type="text/css" rel="stylesheet" />
	<!--//Base Contents End-->
	
	<script> 
	var now = new Date();
	// 디비 전역 변수
	var gEntityPartitionId   	  = "";
	var gEntityPartitionName 	  = "";
	var gEntityProcessId 	 	  = "";
	var gEntityProcessName   	  = "";
	var gEntityProcessVer    	  = "";
	var gEntityProcessState  	  = "";
	var gEntityProcessActive	  = "";
	var gEntityProcessDescription = "";
	var gEntityProcessSaveDate	  = "";
	
	// XPDL 전역 변수
	var gXpdlProcessId 	   		= "";
	var gXpdlProcessName   		= "";
	var gXpdlProcessVer    		= "";
	var gXpdlProcessDescription = "";
	
	// 초기화면 Body 전역 변수.
	var div_north = "";
	var div_south = "";
	
	(function($) {
		// 조직도 관련 Template Variable.
		$.template("addrBookItemUser", '<option value="\${id}">\${name}/\${jobTitle}/\${teamName}</option>');
		$.template("addrBookItemGroup", '<option value="\${code}">\${name}</option>');
		$.template("addrBookItemRole", '<option value="\${name}">\${id}</option>');
		
		var workflowLayout, centerLayout;
		$(document).ready(function () { 
		
			// html을 복사하려면 Ready 안에서 해야됨.
			var $p_box_con       = $(".p_box_con").html();
			var $p_box_info_con  = $(".p_box_info_con").html();
			
			div_north = $p_box_con;
			div_south = $p_box_info_con;
			
			workflowLayout = $('#wrap').layout({ 
				center__paneSelector:	".ui-layout-center" 
				,	west__paneSelector:		".ui-layout-west" 
				,	north__paneSelector:	".ui-layout-north"
				,	south__paneSelector:	".ui-layout-south"
				,	resizerClass:			"splitbarV"
				,	togglerClass:			"toggler"
				,   west__onresize:			resizeWestPaneScroll  // west resize 시 호출
				,	west__size:				225
				,	west__spacing_open:		6
				,   west__spacing_closed:	6
				,	west__resizable:		true
				,	west__slidable:			true
				,	spacing_open:			0 // ALL panes
				,	south__size:			20
				, 	south__spacing_open:	0
				,	south__closable:		false
				,	south__slidable:		false
				,	south__resizable:		false
				,	north__size:			28
				, 	north__spacing_open:	0
				,	north__closable:		false
				,	north__slidable:		false
				,	north__resizable:		false
				,	east__initClosed:       true
			});
			
			centerLayout = $('div.ui-layout-center').layout({
					resizerClass:			"splitbarH"
				,	togglerClass:			"toggler"
				,	center__minSize:		100	// top panes
				,	center__paneSelector:	".center-center"
				,   south__onresize:		resizeSouthPaneScroll  // south resize 시 호출
				,   center__onresize:		resizeCenterPaneScroll  // south resize 시 호출
				,	south__paneSelector:	".center-south"
				,	north__paneSelector:	".center-north"
				,	north__size:			78
				,	north__spacing_open:	0
				,   north__spacing_closed:	0
				,	north__closable:		false
				,	north__slidable:		false
				,	north__resizable:		false
				,	south__size:			"30%"
				,	south__spacing_open:	6
				,   south__spacing_closed:	6
			});	
			
			// 메뉴영역 높이 조절 초기  
			resizeWestPaneScroll();	
			
			// 테이블 영역 높이 조절
			resizeSouthPaneScroll();
			
			// 트리 초기화.
			getCreateTree(gEntityPartitionId);
			
			// 상태바 위 프로세스 탭 이름 초기화.
			$("#divTab_work1").tabs();

			//=============================================================================
			// * 버튼 이벤트. (트리Refresh, 파티션 추가, 프로세스 추가, 프로세스 저장, 배치, 배치해제) 
			//=============================================================================
			$( ".button, .bold" ).click(function() {
				var url = "";
				var style = "";
				var isDialog = "Y";
				
				// 버튼 이벤트 Id.
				var attrIds = $(this).attr("class").split(" ");
				var attrId = attrIds[attrIds.length - 1];
				var $dialogId = $( "#dialog-" + attrId );
				
				switch ( attrId ){
					// 트리 정렬
					case "treeRefresh" :
						getCreateTree("");
						break;
					
					// 파티션 추가
					case "createPartition" :
						url = '<c:url value="partitionCreatePartition.do"/>';
						style = { width: 520, height:270, modal:true, resizable: true };
						break;
					
					// 파티션 삭제
					case "deletePartition" :
						if(gEntityPartitionId != ""){
							url = '<c:url value="partitionDeletePartition.do?partitionId="/>' + encodeURIComponent(gEntityPartitionId)
								+ "&partitionName=" + encodeURIComponent(gEntityPartitionName);
							style = { width: 520, height:180, modal:true, resizable: true };	
						}else{
							alert("<ikep4j:message pre='${preModeler}' key='message.48'/>");
							return false;
						}
						break;
					
					// 프로세스 추가
					case "createProcess"   :
						url = '<c:url value="processCreateProcess.do?partitionId="/>' + encodeURIComponent(gEntityPartitionId);
						style = { width: 520, height:375, modal:true, resizable: true };
						break;
					
					// 프로세스 저장
					case "saveProcess"   :
						var result = createProcessXpdlInitiate();
						if( result == false ){
							return false;
						}
						url = '<c:url value="processSaveProcess.do?processId="/>' + encodeURIComponent(gXpdlProcessId) + '&processName=' + encodeURIComponent(gXpdlProcessName) + '&processVer=' + encodeURIComponent(gXpdlProcessVer);
						style = { width: 520, height:230, modal:true, resizable: true };
						break;
						
					// 프로세스 삭제
					case "deleteProcess"   :
						if (gEntityProcessState == 'SAVED' && gEntityProcessActive != 'ACTIVE') {
							url = '<c:url value="processDeleteProcess.do?processId="/>' + encodeURIComponent(gXpdlProcessId) 
								+ '&processName=' + encodeURIComponent(gXpdlProcessName) 
								+ '&processVer=' + encodeURIComponent(gXpdlProcessVer);
							style = { width: 520, height:180, modal:true, resizable: true };
						}else{
							alert("<ikep4j:message pre='${preModeler}' key='message.49'/>");
							return false;
						}
						break;		
					
					// 배치
					case "deployProcess" :
						// * vadlidation 4가지 중 체크해야됨.
						if( gEntityProcessState == 'SAVED' ){
							createProcessXpdlInitiate();	
							url = '<c:url value="processDeployProcess.do?processId="/>' + encodeURIComponent(gXpdlProcessId) + '&processName=' + encodeURIComponent(gXpdlProcessName) + '&processVer=' + encodeURIComponent(gXpdlProcessVer);
							style = { width: 520, height:180, modal:true, resizable: true };	
						}else{
							alert("<ikep4j:message pre='${preModeler}' key='message.nonesave'/>");
							return false;
						}
						
						break;
					
					// 배치해제
					case "undeployProcess" :
						// * vadlidation 4가지 중 체크해야됨.
						if (gEntityProcessState == 'SAVED' && gEntityProcessActive == 'ACTIVE') {
							createProcessXpdlInitiate();
							url = '<c:url value="processUnDeployProcess.do?processId="/>' + encodeURIComponent(gXpdlProcessId) + '&processName=' + encodeURIComponent(gXpdlProcessName) + '&processVer=' + encodeURIComponent(gXpdlProcessVer);
							style = { width: 520, height:180, modal:true, resizable: true };
						}else{
							alert("<ikep4j:message pre='${preModeler}' key='message.nonebatch'/>");
							return false;
						}
						break;
				}
				// 팝업시에만..
				if( isDialog == 'Y' ){
					$dialogId.load(url).error(function(event, request, settings) {alert("<ikep4j:message pre='${preValidation}' key='error'/>");});
					$dialogId.dialog(style);
					$dialogId.dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
					$dialogId.dialog("widget").children(":first").addClass("ui-widget-workflow-header");	
				}
			});
		});
		
		/* 메뉴영역 높이 조절 */
		resizeWestPaneScroll = function(){
			var westPaneHeight = $(".ui-layout-west").height();
			var menuHeight = $(".l_tree").height();		
			$(".l_tree").height(westPaneHeight-18);
			$(".l_tree_wrap").height(westPaneHeight-36);
		}
		
		/* table영역 높이 조절 */
		resizeSouthPaneScroll = function(){
			var detailHeight = $(".p_box_info_con").height()-27;
//			$(".box_ty1").height(detailHeight);
			
			$("#p_variables").height(detailHeight-28);
			$("#participantsInfo").height(detailHeight-28);
			$("#p_variables").height(detailHeight-28);
			$(".leftbox").height(detailHeight-28);
			$(".rightbox").height(detailHeight-28);
		}	
		
		/* prism 영역 높이 조절 */
		resizeCenterPaneScroll = function(){
			var conHeight = $(".p_box_con").height();
			$("#prism").height(conHeight);
		}	
		
		//=========================================================================
		// * 트리 초기화 Function.
		//=========================================================================
		getCreateTree = function(partitionId){
			var elTree = $("#tree").jstree({
				plugins : [ "themes", "ui", "html_data", "crrm", "dnd" ],
    			core : {"initially_open":[partitionId]},
				crrm : {
			        move : {
			            always_copy : true,
			            check_move : function(rslt) {
			                return false;   // 현재 트리에서는 옮겨지지 않도록 한다.
			            }
			        }
			    },
				html_data : {
					ajax : {
						url : '<c:url value="tree.do" />',
						data : function(n) {
							return{
								id : n.attr ? n.attr("id") : 0,
								rand : new Date().getTime()
							};
						}
					}
				}
			});
			// 트리 theme 변경. ( * 변경시에는 jsp 상단의 css 경로도 함께 변경 해주어야 함 )
			elTree.jstree("set_theme", "ikep");
		};
		
		//===============================================================================
		// * 트리 Root를 클릭 했을 경우 PartitionId를 저장. 프로세스 추가 팝업 선택에서 사용.
		//===============================================================================
		partitionIdClickListner = function(partitionId, partitionName){
			gEntityPartitionId   = partitionId;
			gEntityPartitionName = partitionName;
		};
		
		//=========================================================================
        // * XPDL 데이터 가져와서 전역변수에 셋팅.
        //=========================================================================
        createProcessXpdlInitiate = function(){
			try {
				gXpdlProcessId = xmlUtil.selectSingleNode("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/@Id").nodeValue;
				gXpdlProcessName = xmlUtil.selectSingleNode("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/@Name").nodeValue;
				gXpdlProcessVer = xmlUtil.selectSingleNode("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:RedefinableHeader/xpdl:Version").firstChild.nodeValue;
				gXpdlProcessDescription = "";
				if (xmlUtil.selectSingleNode("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:ProcessHeader/xpdl:Description").hasChildNodes()) {
					gXpdlProcessDescription = xmlUtil.selectSingleNode("//xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:ProcessHeader/xpdl:Description").firstChild.nodeValue;
				}
				else {
					gXpdlProcessDescription = '';
				}
			}catch(e){
				alert("<ikep4j:message pre='${preModeler}' key='message.3'/>");
				return false;
			}
        };
		
		//===============================================================================
		// * 트리 Process 를 클릭했을 경우 디자인을 로드한다.
		//===============================================================================
		processIdClickListner = function(processId, processName, processVer, partitionId, partitionName, description, loadPrism){
			// 전역변수 값 셋팅.
			gEntityPartitionId   	  = partitionId;
			gEntityPartitionName 	  = partitionName;
			gEntityProcessId     	  = processId;
			gEntityProcessName   	  = processName;
			gEntityProcessVer    	  = processVer;
			gEntityProcessDescription = description;
			
			// 디자인을 수정 하다가 다른 프로세스를 클릭 한경우 체크.(edited == true). 프로세스 저장팝업을 로드한다.
			if( edited == true ){
				createProcessXpdlInitiate();
				url = '<c:url value="processSaveProcess.do?processId="/>' + encodeURIComponent(gXpdlProcessId) 
						+ '&processName=' + encodeURIComponent(gXpdlProcessName) 
						+ '&processVer=' + encodeURIComponent(gXpdlProcessVer) 
						+ "&edited=Y";
				style = { width: 520, height:250, modal:true, resizable: true };
				$("#dialog-saveProcess").load(url).error(function(event, request, settings) {alert("<ikep4j:message pre='${preValidation}' key='error'/>");});
				$("#dialog-saveProcess").dialog(style);
				$("#dialog-saveProcess").dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
				$("#dialog-saveProcess").dialog("widget").children(":first").addClass("ui-widget-workflow-header");
			}else{
				// 수정사항이 없을 경우 디자인을 로드.
				processLoad(loadPrism);
			}
		};
		
		//===============================================================================
		// * Process 로드
		//===============================================================================
		processLoad = function(loadPrism){
			var $pbox = $(".p_box_info_con");
			$pbox.load('<c:url value="processModel.do?gEntityPartitionId="/>' + gEntityPartitionId + "&loadPrism=" + loadPrism).error(function(event, request, settings) {alert("<ikep4j:message pre='${preValidation}' key='error'/>");});
			$pbox.removeClass("p_box_info");
		}
		
		//=========================================================================
        // * 디자인에서 다중 컴포넌트 삭제(프로세스 기본정보로 돌아옴)
        //=========================================================================
        getProcessDefaultInfo = function(){
			processLoad("N");
		};
		
		//=========================================================================
        // * 디자인에서 Activity 클릭
        //=========================================================================
        activityClickListner = function(action, id, name, type, gatewayType){
			var $pbox = $(".p_box_info_con");
			$pbox.load('<c:url value="activityModel.do?action="/>' + encodeURIComponent(action) 
				+ '&id=' + encodeURIComponent(id) 
				+ '&name=' + encodeURIComponent(name)
				+ '&type=' + encodeURIComponent(type)
				+ '&gatewayType=' + encodeURIComponent(gatewayType)
				).error(function(event, request, settings) {alert("<ikep4j:message pre='${preValidation}' key='error'/>");});
			$pbox.removeClass("p_box_info");
		};
		
		//=========================================================================
        // * 디자인에서 연결선(Transition) 클릭
        //=========================================================================
        transitionClickListner = function(action, id, label, fromId, toId){
			var result = applyTransition(action, id, label, fromId, toId, '');
			var $pbox = $(".p_box_info_con");
			
			if( action != 'delete' && result == true){
				$pbox.load('<c:url value="transitionModel.do?action="/>' + encodeURIComponent(action) 
					+ '&id=' + encodeURIComponent(id)
					).error(function(event, request, settings) {alert("<ikep4j:message pre='${preValidation}' key='error'/>");});
				$pbox.removeClass("p_box_info");	
			}else{
				getProcessDefaultInfo();
			}
		};
		
		//=========================================================================
        // * 디자인 연결선(Transition) XPDL 연동.
        //=========================================================================
		applyTransition = function(action, id, label, fromId, toId, conditionExpression) {
			var transition = new Array();
			var result = true;
			
			transition["id"]   =  id;
			transition["name"] = "";
			transition["conditionType"] =  "";
			transition["conditionExpression"] =  "";
			// * 사용안함.
			transition["description"] =  "";
			
			// 연결선 이름 변경.
			if (action == 'rename') {
				transition["name"] = label;
			};
			
			// 라인 생성/수정인 경우
			if (action == 'append' || action == 'replace') {
				transition["from"] = fromId;
				transition["to"]   = toId;	
			}
			
			// 라인 생성/수정/삭제 인 경우 XPDL에 저장.
			if (action == 'append' || action == 'delete' || action == 'replace') {
				try {
					xpdl.setTransition(transition, action);
				} 
				catch (e) {
					alert(e);
					result = false;
				}
			// 이름 변경인 경우. XPDL에 저장.
			}else if(action == 'rename'){
				try {
					xpdl.setTransitionName(transition.id, transition.name);
				} 
				catch (e) {
					alert(e);
					result = false;
				}
			}else{
				
			}
			return result;
		}
		
		//=========================================================================
        // * 프로세스 삭제 후 초기화면 Body Refresh.
        //=========================================================================
		getRefreshBody = function(){
			$(".p_box_con").html(div_north);
			$(".p_box_info_con").html(div_south);
			$(".p_box_info_con").addClass("p_box_info");
		}
		
	})(jQuery);

	</script>
</head>
<!--body Start-->
<body>
	<div id="wrap">
		<!--tiles Start-->
		<tiles:insertAttribute name="header" />
		
		<!-- West, North, South Layout이 있음. 파티션 및 프로세스 바탕화면 그리고 하단탭 Start-->
			<tiles:insertAttribute name="body" />
		<!--// West, North, South Layout이 있음. 파티션 및 프로세스 바탕화면 그리고 하단탭 End-->
		
		<tiles:insertAttribute name="footer" />
		<!--//tiles End-->
	</div>
</body>
<!--//body End-->
</html>

<!--dialog Start-->
	<div id="dialog-createPartition" title="<ikep4j:message pre='${preModeler}' key='popup.title.partition.create'/>" class="none ui-dialog1"></div>
	<div id="dialog-deletePartition" title="<ikep4j:message pre='${preModeler}' key='popup.title.partition.delete'/>" class="none ui-dialog1"></div>
	<div id="dialog-createProcess" title="<ikep4j:message pre='${preModeler}' key='popup.title.process.create'/>" class="none ui-dialog1"></div>
	<div id="dialog-processModifyDefaultInfo" title="<ikep4j:message pre='${preModeler}' key='popup.title.process.defaultinfo.update'/>" class="none ui-dialog1"></div>
	<div id="dialog-createProcessVariable" title="<ikep4j:message pre='${preModeler}' key='popup.title.process.variable.create'/>" class="none ui-dialog1"></div>
	<div id="dialog-modifyProcessVariable" title="<ikep4j:message pre='${preModeler}' key='popup.title.process.variable.update'/>" class="none ui-dialog1"></div>
	<div id="dialog-createVariableParticipant" title="<ikep4j:message pre='${preModeler}' key='popup.title.participant.variable.create'/>" class="none ui-dialog1"></div>
	<div id="dialog-saveProcess" title="<ikep4j:message pre='${preModeler}' key='popup.title.process.save'/>" class="none ui-dialog1"></div>
	<div id="dialog-deployProcess" title="<ikep4j:message pre='${preModeler}' key='popup.title.process.deploy'/>" class="none ui-dialog1"></div>
	<div id="dialog-undeployProcess" title="<ikep4j:message pre='${preModeler}' key='popup.title.process.undeploy'/>" class="none ui-dialog1"></div>
	<div id="dialog-deleteProcess" title="<ikep4j:message pre='${preModeler}' key='popup.title.process.delete'/>" class="none ui-dialog1"></div>
	<div id="dialog-expressionBuilder" title="<ikep4j:message pre='${preModeler}' key='popup.title.process.builder.create'/>" class="none ui-dialog1"></div>
	<div id="dialog-createActivityVariable" title="<ikep4j:message pre='${preModeler}' key='popup.title.activity.variable.select'/>" class="none ui-dialog1"></div>
	<div id="dialog-activityModifyDefaultInfo" title="<ikep4j:message pre='${preModeler}' key='popup.title.activity.defaultinfo.update'/>" class="none ui-dialog1"></div>
	<div id="dialog-createActivityAdvanceInfoVariable" title="<ikep4j:message pre='${preModeler}' key='popup.title.process.advanceinfo.variable.select'/>" class="none ui-dialog1"></div>
	<div id="dialog-createActivityNotification-EMAIL" title="<ikep4j:message pre='${preModeler}' key='popup.title.email.notifycation'/>" class="none ui-dialog1"></div>
	<div id="dialog-createActivityNotification-SMS" title="<ikep4j:message pre='${preModeler}' key='popup.title.sms.notifycation'/>" class="none ui-dialog1"></div>
	<div id="dialog-modifyActivityNotification-EMAIL" title="<ikep4j:message pre='${preModeler}' key='popup.title.email.notifycation.update'/>" class="none ui-dialog1"></div>
	<div id="dialog-modifyActivityNotification-SMS" title="<ikep4j:message pre='${preModeler}' key='popup.title.sms.notifycation.update'/>" class="none ui-dialog1"></div>
	<div id="dialog-activityNotificationMessage" title="<ikep4j:message pre='${preModeler}' key='popup.title.message'/>" class="none ui-dialog1"></div>
	<div id="dialog-activityCreateParticipantPopup-EMAIL" title="<ikep4j:message pre='${preModeler}' key='popup.title.email.participant.notifycation'/>" class="none ui-dialog1"></div>
	<div id="dialog-activityCreateParticipantPopup-SMS" title="<ikep4j:message pre='${preModeler}' key='popup.title.sms.participant.notifycation'/>" class="none ui-dialog1"></div>
	<div id="dialog-activityModifyParticipantPopup-EMAIL" title="<ikep4j:message pre='${preModeler}' key='popup.title.email.participant.notifycation.update'/>" class="none ui-dialog1"></div>
	<div id="dialog-activityModifyParticipantPopup-SMS" title="<ikep4j:message pre='${preModeler}' key='popup.title.sms.participant.notifycation.update'/>" class="none ui-dialog1"></div>
	<textarea id="dialog-xpdl_source" rows="50" cols="200" style="display: none;" name="dialog-xpdl_source"></textarea>
<!--//dialog End-->

<!--조직도 Form Start-->
<form id="AddrControlForm">
	<select name="addrGroupList" type="hidden" multiple="multiple" size="5" style="display:none;"></select>
	<input name="controlTabType" title="" type="hidden" value="1:0:1:0" />
	<input name="controlType" title="" type="hidden" value="ORG" />
	<input name="selectType" title="" type="hidden" value="ALL" />
	<input name="selectMaxCnt" title="" type="hidden" value="3" />
	<input name="searchSubFlag" title="" type="hidden" value="" />
</form>
<!--//조직도 Form  End-->