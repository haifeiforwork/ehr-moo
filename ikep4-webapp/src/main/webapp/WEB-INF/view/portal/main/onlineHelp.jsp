<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preHelp"    value="ui.portal.help" />
<c:set var="msgHelp"    value="message.portal.help" />

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Online Help - Moorim Paper</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/online_help/onlinehelp.css"/>" />
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.5.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-ui-1.8.13.min.without.datepicker.js"/>"></script>
<script type='text/javascript' src="<c:url value="/base/js/jquery/jquery.accordion.js"/>"></script>

<script type="text/javascript">
<!--
var locale = "${user.localeCode}";
var contextRoot = "${pageContext.request.contextPath}";

(function($) {	
			
	$(document).ready(function() {
		$(".helpMenu_group li>a").live("click", function(event) {
			event.preventDefault();
			viewHelp(this);
		});
		$("#helpHome").live("click", function(event) {
			mainHelp();
		});
		
		$('#list1b').accordion({
			autoheight: false,
			header: '.head'
		});	
		
		$(".innerLink").find("a").click( 
				goToBookmark
		);
		
		$(".innerLink li").children().bind("click", function(event) {
			setSelected(this)
		});
		
		$(".innerLink li ul li").children().bind("click", function(event) {
			setSelected(this)
		});
		
		$(".head").bind("click", function(event) {
			viewHelpContent(this);
			setSelected();
		});
		
		$(document.body).click(function(event) {
			$("#outlookContainer").hide();
		});
		$("#btnOutlook, #outlookContainer").click(function(event) {
			event.stopPropagation();

			if(this == event.target)
				$("#outlookContainer").toggle();
			else {
				if(event.target.tagName.toLowerCase() == "a") {
					$(document.body).trigger("click");
				}
			}
		});
		
		$("#btnLogo").click(mainHelp);
	});
	
	setSelected = function(el){
		$(".innerLink").children().removeClass("selected");
		$(".innerLink li ul").children().removeClass("selected");
		
		if(el){
			$(el).parent().addClass("selected");
		}		
	};
	
	/* HELP 메인으로 이동 */
	mainHelp = function(){
		$("#online_blockMain").hide();
		$(".helpMenu").show();
	};
	
	/* 해당 help 이동 */
	viewHelp = function(el){
		var url = $.trim($(el).attr("href"));
		var active = $(el).attr("id");
		
		if(active == 'n'){
			alert("<ikep4j:message pre='${msgHelp}' key='second.open' />");
			return false;
		}
		
		url = contextRoot + "/base/help/"+ locale + "/" + url;
		var $helpMain = $("#online_blockMain");
		var $helpContent = $("#online_mainContents");		
		$.ajax({				
			url : url,
			data : "",
			type : "get",
			success : function(result) {
				$(".helpMenu").hide();
				$helpContent.html(result);
				$helpMain.show();
				setAccordionMenu(active);
				//setLeftHeight();
			},
			error : function(event, request, settings) { 
				alert("error");	
			}
		});	
	};
	
	/* Accordion 세팅 */
	setAccordionMenu = function(idx){	
		$("#list1b").accordion("activate", Number(idx) );
	};
	
	/* 해당  help Content 보기 */
	viewHelpContent = function(el){
		
		var url = $.trim($(el).attr("href"));
		url = contextRoot + "/base/help/" + locale + "/" + url;
		var $helpContent = $("#online_mainContents");
		var $helpMain = $("#online_blockMain");
		$.ajax({				
			url : url,
			data : "",
			type : "get",
			success : function(result) {
				$(".helpMenu").hide();
				$helpContent.html(result);
				$helpMain.scrollTop(0);
			},
			error : function(event, request, settings) { 
				alert("error");	
			}
		});	
	};
	
	
	/* Left Menu Height 조절 */
	setLeftHeight = function(){
		setTimeout(function() {
			$('#online_leftMenu').height($('.online_contents02').height()-32);
		}, 500);
	};
	
	/* go click Content */
	goToBookmark = function( hash ) {
		
		if (hash == undefined || hash == '')
			return true;
		else if (typeof hash != 'string') {
			if (location.pathname.replace(/^\//,'') != this.pathname.replace(/^\//,'')
				|| location.hostname != this.hostname
			)
				return true;
			else {
				hash = this.hash;
				if (!hash || !hash.length) return true;
			}
		}

		var $Pane	= $('#online_blockMain');
		var $Target = $(hash);
		
		$Target = $Target.length && $Target || $('[name='+ hash.slice(1) +']');
		
		if ($Target.length) {
			var targetTop = $Target.offset().top;
			var paneTop = $Pane.offset().top;
			// absolute scrolling - ALWAYS from the top!
			//$Pane.animate({ scrollTop: targetTop +'px' }, 1000, 'linear', function(){
			// relative scrolling
			$Pane.animate({ scrollTop: '+='+ (targetTop - paneTop) +'px' }, 1000, 'linear', function(){
				self.location.replace( hash ); // make sure we scroll ALL the way!
			});
			
			return false; // cancel normal hyperlink
		}
	};
	
})(jQuery);
-->
</script>
</head> 
<body>
 
<!--online_wrapper Start-->
<div id="online_wrapper">
 
	<!--onlineHeader Start-->
	<div class="onlineHeader">
		<h1 class="none">Header Info Area</h1>
		<div class="online_logo">
			<a id="btnLogo" href="#a"><img src="<c:url value="/base/images/onlinehelp/online_logo.gif"/>" alt="help_logo" /></a>
		</div>
		<div class="online_contents">
			<!-- <a id="btnOutlook" href="#a" title="outlook button"></a> -->
			<a href="#a" id="helpHome" title="content button"></a>
			
			<!--  outlook 
			<div id="outlookContainer">
				<div class="utilmenu_box">
					<ul>
						<li><a href="http://portal.lgls.com:5080/ssoLoginImage/Win7_아웃룩2010구성방법_V1.0.ppt"><ikep4j:message pre='${preHelp}' key='button.outlookWin7' /></a></li>
						<li><a href="http://portal.lgls.com:5080/ssoLoginImage/WinXP_아웃룩2007구성방법_V1.0.ppt"><ikep4j:message pre='${preHelp}' key='button.outlookXP2007' /></a></li>
						<li><a href="http://portal.lgls.com:5080/ssoLoginImage/WinXP_아웃룩2010구성방법_V1.0.ppt"><ikep4j:message pre='${preHelp}' key='button.outlookXP' /></a></li>
						<li><a href="http://portal.lgls.com:5080/ssoLoginImage/(English)WinXP_Outlook2007_Setting_V1.0.ppt"><ikep4j:message pre='${preHelp}' key='button.outlookEnglishXP2007' /></a></li>
						<li><a href="http://portal.lgls.com:5080/ssoLoginImage/(English)Win7_Outlook2007_Setting_V1.0.ppt"><ikep4j:message pre='${preHelp}' key='button.outlookEnglishWin7' /></a></li>
					</ul>
				</div>
			</div>
			-->
			
		</div>
		<div class="clear"></div>
	</div>
	<!--//onlineHeader End-->
	
	<!--onlineMenu Start-->
	<div class="helpMenu">
	
		<div class="helpMenu_group">
			<div class="help_group01">
				<ul>
					<li><span><ikep4j:message pre='${preHelp}' key='part.title1' /></span>
						<ul>
							<li><a href="portal.html" id="0"><ikep4j:message pre='${preHelp}' key='part1.portal' /></a></li>
							<li><a href="profile.html" id="1"><ikep4j:message pre='${preHelp}' key='part1.profile' /></a></li>
							<li><a href="planner.html" id="2"><ikep4j:message pre='${preHelp}' key='part1.planner' /></a></li>
							<li><a href="todo.html" id="3"><ikep4j:message pre='${preHelp}' key='part1.todo' /></a></li>
							<li><a href="addressbook.html" id="4"><ikep4j:message pre='${preHelp}' key='part1.address' /></a></li>
						</ul>
					</li>
				</ul>			
			</div>
			<div class="help_group02">
				<ul>
					<li><span><ikep4j:message pre='${preHelp}' key='part.title2' /></span>
						<ul>
							<li><a href="bbs.html" id="5"><ikep4j:message pre='${preHelp}' key='part2.bbs' /></a></li>
							<li><a href="qa.html" id="6"><ikep4j:message pre='${preHelp}' key='part2.qna' /></a></li>
							<li><a href="meetingroom.html" id="7"><ikep4j:message pre='${preHelp}' key='part2.meetingRoom' /></a></li>
							<li><a href="survey.html" id="8"><ikep4j:message pre='${preHelp}' key='part2.survey' /></a></li>
							<li><a href="onlinePoll.html" id="9"><ikep4j:message pre='${preHelp}' key='part2.poll' /></a></li>
							<!--메일<li><a href="mail.html" id="2"><ikep4j:message pre='${preHelp}' key='part2.mail' /></a></li> -->
							<!--전자결재  <li><a href="workflow.html" id="4"><ikep4j:message pre='${preHelp}' key='part2.approval' /></a></li> -->
							<!--메모<li><a href="#a" id="n"><ikep4j:message pre='${preHelp}' key='part2.memo' /></a></li> -->
						</ul>
					</li>
				</ul>			
			</div>
			<div class="help_group03">
				<ul>
					<li><span><ikep4j:message pre='${preHelp}' key='part.title3' /></span>
						<ul class="colls">
							<li><a href="workspace.html" id="10"><ikep4j:message pre='${preHelp}' key='part3.workspace' /></a></li>
							<!--쪽지  <li><a href="message.html" id="12"><ikep4j:message pre='${preHelp}' key='part3.message' /></a></li> -->
						</ul>
					</li>
				</ul>			
			</div>	
			<div class="help_group04">
				<ul>
					<li><span><ikep4j:message pre='${preHelp}' key='part.title4' /></span>
						<ul class="colls">
							<li><a href="kms.html" id="13"><ikep4j:message pre='${preHelp}' key='part4.kms' /></a></li>
							<li><a href="ideation.html" id="11"><ikep4j:message pre='${preHelp}' key='part4.ideation' /></a></li>
							<li><a href="custommer.html" id="12"><ikep4j:message pre='${preHelp}' key='part4.custommer' /></a></li>
						</ul>
					</li>
				</ul>			
			</div>				
		</div>
		<div class="clear" style="padding-top:20px;">
			<div class="img_question_mark"></div>
<!--  		<div class="img_elife<c:if test="${user.localeCode != 'ko'}">_${user.localeCode}</c:if>"></div> -->
			<div class="img_helpdesk<c:if test="${user.localeCode != 'ko'}">_${user.localeCode}</c:if>"></div>
			<div class="clear"></div>
		</div>
	</div>
	<div class="clear"></div>
	<!--//onlineMenu End-->
	
	<!--online_blockMain Start-->
	<div id="online_blockMain" style="display:none;">
		
		<!--online_leftMenu Start-->
		<div id="online_leftMenu">
			<h1 class="none">Left Menu</h1>	
			<h2><ikep4j:message pre='${preHelp}' key='h2' /></h2>
			<!-- div class="left_line">
				<span class="none">leftMenu_line</span>
			</div-->
				
			<div class="online_left_fixed basic" id="list1b">				
					
				<ul>
								
					<li class="liFirst no_child">
						<a class="head" href="portal.html"><ikep4j:message pre='${preHelp}' key='portal.title' /></a>											
						<ul class="innerLink">
							<li><a href="#portal_01"><ikep4j:message pre='${preHelp}' key='portal.menu1' /></a></li>
							<li><a href="#portal_02"><ikep4j:message pre='${preHelp}' key='portal.menu2' /></a></li>
							<li><a href="#portal_03"><ikep4j:message pre='${preHelp}' key='portal.menu3' /></a>
								<ul>
									<li><a href="#portal_03_01"><ikep4j:message pre='${preHelp}' key='portal.menu3_1' /></a></li>
									<li><a href="#portal_03_02"><ikep4j:message pre='${preHelp}' key='portal.menu3_2' /></a></li>
									<li><a href="#portal_03_03"><ikep4j:message pre='${preHelp}' key='portal.menu3_3' /></a></li>
									<li><a href="#portal_03_04"><ikep4j:message pre='${preHelp}' key='portal.menu3_4' /></a></li>
									<li><a href="#portal_03_05"><ikep4j:message pre='${preHelp}' key='portal.menu3_5' /></a></li>
								</ul>
							</li>											
						</ul>	
					</li>
					
					<li class="no_child">
						<a class="head" href="profile.html"><ikep4j:message pre='${preHelp}' key='profile.title' /></a>
						<ul class="innerLink">
							<li><a href="#profile_01"><ikep4j:message pre='${preHelp}' key='profile.menu1' /></a></li>
							<li><a href="#profile_02"><ikep4j:message pre='${preHelp}' key='profile.menu2' /></a>
								<ul>
									<li><a href="#profile_02_01"><ikep4j:message pre='${preHelp}' key='profile.menu2_1' /></a></li>
									<li><a href="#profile_02_02"><ikep4j:message pre='${preHelp}' key='profile.menu2_2' /></a></li>
								</ul>
							</li>
						</ul>						
					</li>					
					
					<!-- 메일
					<li class="no_child">
						<a class="head" href="mail.html"><ikep4j:message pre='${preHelp}' key='mail.title' /></a>
						<ul class="innerLink">
							<li><a href="#mail_01"><ikep4j:message pre='${preHelp}' key='mail.menu1' /></a>
								<ul>
									<li><a href="#mail_01_01"><ikep4j:message pre='${preHelp}' key='mail.menu1_1' /></a></li>
									<li><a href="#mail_01_02"><ikep4j:message pre='${preHelp}' key='mail.menu1_2' /></a></li>
								</ul>
							</li>
						</ul>						
					</li>
					-->
					
					<li class="no_child">
						<a class="head" href="planner.html"><ikep4j:message pre='${preHelp}' key='planner.title' /></a>
						<ul class="innerLink">
							<li><a href="#planner_01"><ikep4j:message pre='${preHelp}' key='planner.menu1' /></a></li>
							<li><a href="#planner_02"><ikep4j:message pre='${preHelp}' key='planner.menu2' /></a>
								<ul>
									<li><a href="#planner_02_01"><ikep4j:message pre='${preHelp}' key='planner.menu2_1' /></a></li>
									<li><a href="#planner_02_02"><ikep4j:message pre='${preHelp}' key='planner.menu2_2' /></a></li>
									<li><a href="#planner_02_03"><ikep4j:message pre='${preHelp}' key='planner.menu2_3' /></a></li>
									<li><a href="#planner_02_04"><ikep4j:message pre='${preHelp}' key='planner.menu2_4' /></a></li>
									<li><a href="#planner_02_05"><ikep4j:message pre='${preHelp}' key='planner.menu2_5' /></a></li>
									<!-- li><a href="#planner_02_06"><ikep4j:message pre='${preHelp}' key='planner.menu2_6' /></a></li -->
								</ul>							
							</li>
							<li><a href="#planner_03"><ikep4j:message pre='${preHelp}' key='planner.menu3' /></a>
								<ul>
									<li><a href="#planner_03_01"><ikep4j:message pre='${preHelp}' key='planner.menu3_1' /></a></li>
									<li><a href="#planner_03_02"><ikep4j:message pre='${preHelp}' key='planner.menu3_2' /></a></li>
									<!--li><a href="#planner_03_03"><ikep4j:message pre='${preHelp}' key='planner.menu3_3' /></a></li-->
								</ul>	
							</li>
							<li><a href="#planner_04"><ikep4j:message pre='${preHelp}' key='planner.menu4' /></a>
								<ul>
									<li><a href="#planner_04_01"><ikep4j:message pre='${preHelp}' key='planner.menu4_1' /></a></li>
									<li><a href="#planner_04_02"><ikep4j:message pre='${preHelp}' key='planner.menu4_2' /></a></li>
								</ul>								
							</li>					
						</ul>	
					</li>
					
					<!-- 전자결재
					<li class="no_child">
						<a class="head" href="workflow.html"><ikep4j:message pre='${preHelp}' key='workflow.title' /></a>
						<ul class="innerLink">
							<li><a href="#workflow_01"><ikep4j:message pre='${preHelp}' key='workflow.menu1' /></a></li>
							<li><a href="#workflow_02"><ikep4j:message pre='${preHelp}' key='workflow.menu2' /></a></li>
							<li><a href="#workflow_03"><ikep4j:message pre='${preHelp}' key='workflow.menu3' /></a>
								<ul>
									<li><a href="#workflow_03_01"><ikep4j:message pre='${preHelp}' key='workflow.menu3_1' /></a></li>
									<li><a href="#workflow_03_02"><ikep4j:message pre='${preHelp}' key='workflow.menu3_2' /></a></li>
									<li><a href="#workflow_03_03"><ikep4j:message pre='${preHelp}' key='workflow.menu3_3' /></a></li>
									<li><a href="#workflow_03_04"><ikep4j:message pre='${preHelp}' key='workflow.menu3_4' /></a></li>
								</ul>
							</li>
						</ul>	
					</li>		
					 -->
					 
					<li class="no_child">
						<a class="head" href="addressbook.html"><ikep4j:message pre='${preHelp}' key='address.title' /></a>
						<ul class="innerLink">
							<li><a href="#addressbook01"><ikep4j:message pre='${preHelp}' key='address.menu1' /></a></li>
							<li><a href="#addressbook02"><ikep4j:message pre='${preHelp}' key='address.menu2' /></a></li>
							<li><a href="#addressbook03"><ikep4j:message pre='${preHelp}' key='address.menu3' /></a></li>	
						</ul>
					</li>	
					
					<li class="no_child">
						<a class="head" href="meetingroom.html"><ikep4j:message pre='${preHelp}' key='meetingroom.title' /></a>
						<ul class="innerLink">
							<li><a href="#meetingroom_01"><ikep4j:message pre='${preHelp}' key='meetingroom.menu1' /></a>
								<ul>
									<li><a href="#meetingroom_01_01"><ikep4j:message pre='${preHelp}' key='meetingroom.menu1_1' /></a></li>
									<li><a href="#meetingroom_01_02"><ikep4j:message pre='${preHelp}' key='meetingroom.menu1_2' /></a></li>
									<li><a href="#meetingroom_01_03"><ikep4j:message pre='${preHelp}' key='meetingroom.menu1_3' /></a></li>
									<li><a href="#meetingroom_01_04"><ikep4j:message pre='${preHelp}' key='meetingroom.menu1_4' /></a></li>
									<li><a href="#meetingroom_01_05"><ikep4j:message pre='${preHelp}' key='meetingroom.menu1_5' /></a></li>
									<li><a href="#meetingroom_01_06"><ikep4j:message pre='${preHelp}' key='meetingroom.menu1_6' /></a></li>
								</ul>	
							</li>
							<li><a href="#meetingroom_02"><ikep4j:message pre='${preHelp}' key='meetingroom.menu2' /></a></li>
							<li><a href="#meetingroom_03"><ikep4j:message pre='${preHelp}' key='meetingroom.menu3' /></a>
								<ul>
									<li><a href="#meetingroom_03_01"><ikep4j:message pre='${preHelp}' key='meetingroom.menu3_1' /></a></li>
									<li><a href="#meetingroom_03_02"><ikep4j:message pre='${preHelp}' key='meetingroom.menu3_2' /></a></li>
									<li><a href="#meetingroom_03_03"><ikep4j:message pre='${preHelp}' key='meetingroom.menu3_3' /></a></li>
									<li><a href="#meetingroom_03_04"><ikep4j:message pre='${preHelp}' key='meetingroom.menu3_4' /></a></li>
									<li><a href="#meetingroom_03_05"><ikep4j:message pre='${preHelp}' key='meetingroom.menu3_5' /></a></li>
									<li><a href="#meetingroom_03_06"><ikep4j:message pre='${preHelp}' key='meetingroom.menu3_6' /></a></li>
								</ul>	
							</li>
							<li><a href="#meetingroom_04"><ikep4j:message pre='${preHelp}' key='meetingroom.menu4' /></a></li>
						</ul>	
					</li>	
					
					<li class="no_child">
						<a class="head" href="todo.html"><ikep4j:message pre='${preHelp}' key='todo.title' /></a>
						<ul class="innerLink">
							<li><a href="#TODO_01"><ikep4j:message pre='${preHelp}' key='todo.menu1' /></a></li>
							<li><a href="#TODO_02"><ikep4j:message pre='${preHelp}' key='todo.menu2' /></a></li>
							<li><a href="#TODO_03"><ikep4j:message pre='${preHelp}' key='todo.menu3' /></a></li>
							<li><a href="#TODO_04"><ikep4j:message pre='${preHelp}' key='todo.menu4' /></a></li>
							<!--li><a href="#TODO_05"><ikep4j:message pre='${preHelp}' key='todo.menu5' /></a></li -->
						</ul>	
					</li>
					
					<!-- 메모
					<li class="no_child">
						<a class="head" href="memo.html">9. 메모</a>
						<ul class="innerLink">
							<li><a href="#RSS_01">Content 1 1</a></li>
							<li><a href="#RSS_02">Content 1 2</a></li>
							<li><a href="#RSS_03_01">Content 1 3</a></li>
						</ul>	
					</li>
					-->
					
					<li class="no_child">
						<a class="head" href="bbs.html"><ikep4j:message pre='${preHelp}' key='bbs.title' /></a>
						<ul class="innerLink">
							<li><a href="#bbs_01"><ikep4j:message pre='${preHelp}' key='bbs.menu1' /></a>
								<ul>
									<li><a href="#bbs_01_01"><ikep4j:message pre='${preHelp}' key='bbs.menu1_1' /></a></li>
									<li><a href="#bbs_01_02"><ikep4j:message pre='${preHelp}' key='bbs.menu1_2' /></a></li>
									<li><a href="#bbs_01_03"><ikep4j:message pre='${preHelp}' key='bbs.menu1_3' /></a></li>
									<li><a href="#bbs_01_04"><ikep4j:message pre='${preHelp}' key='bbs.menu1_4' /></a></li>
								</ul>	
							</li>
							<li><a href="#bbs_02"><ikep4j:message pre='${preHelp}' key='bbs.menu2' /></a>
								<ul>
									<li><a href="#bbs_02_01"><ikep4j:message pre='${preHelp}' key='bbs.menu2_1' /></a></li>
									<!--  <li><a href="#bbs_02_02"><ikep4j:message pre='${preHelp}' key='bbs.menu2_2' /></a></li> -->
								</ul>	
							</li>
						</ul>	
					</li>	
					
					<li class="no_child">
						<a class="head" href="survey.html"><ikep4j:message pre='${preHelp}' key='survey.title' /></a>
						<ul class="innerLink">
							<li><a href="#survey_01"><ikep4j:message pre='${preHelp}' key='survey.menu1' /></a></li>
							<li><a href="#survey_02"><ikep4j:message pre='${preHelp}' key='survey.menu2' /></a></li>
							<li><a href="#survey_03"><ikep4j:message pre='${preHelp}' key='survey.menu3' /></a>
								<ul>
									<li><a href="#survey_03_01"><ikep4j:message pre='${preHelp}' key='survey.menu3_1' /></a></li>
									<li><a href="#survey_03_02"><ikep4j:message pre='${preHelp}' key='survey.menu3_2' /></a></li>
									<li><a href="#survey_03_03"><ikep4j:message pre='${preHelp}' key='survey.menu3_3' /></a></li>
									<li><a href="#survey_03_04"><ikep4j:message pre='${preHelp}' key='survey.menu3_4' /></a></li>
									<li><a href="#survey_03_05"><ikep4j:message pre='${preHelp}' key='survey.menu3_5' /></a></li>																											
								</ul>	
							</li>
						</ul>	
					</li>	
					
					<li class="no_child">
						<a class="head" href="onlinePoll.html"><ikep4j:message pre='${preHelp}' key='poll.title' /></a>
						<ul class="innerLink">
							<li><a href="#online_01"><ikep4j:message pre='${preHelp}' key='poll.menu1' /></a></li>
							<li><a href="#online_02"><ikep4j:message pre='${preHelp}' key='poll.menu2' /></a>
								<ul>
									<li><a href="#online_02_01"><ikep4j:message pre='${preHelp}' key='poll.menu2_1' /></a></li>
									<li><a href="#online_02_02"><ikep4j:message pre='${preHelp}' key='poll.menu2_2' /></a></li>		
									<li><a href="#online_02_03"><ikep4j:message pre='${preHelp}' key='poll.menu2_3' /></a></li>															
								</ul>	
							</li>
							<li><a href="#online_03"><ikep4j:message pre='${preHelp}' key='poll.menu3' /></a></li>
						</ul>	
					</li>
					
					<li class="no_child">
						<a class="head" href="qa.html"><ikep4j:message pre='${preHelp}' key='qna.title' /></a>
						<ul class="innerLink">
							<li><a href="#QA_01"><ikep4j:message pre='${preHelp}' key='qna.menu1' /></a></li>
							<li><a href="#QA_02"><ikep4j:message pre='${preHelp}' key='qna.menu2' /></a></li>
							<li><a href="#QA_03"><ikep4j:message pre='${preHelp}' key='qna.menu3' /></a></li>
							<li><a href="#QA_04"><ikep4j:message pre='${preHelp}' key='qna.menu4' /></a></li>
							<li><a href="#QA_05"><ikep4j:message pre='${preHelp}' key='qna.menu5' /></a></li>
							<!--  
							<li><a href="#QA_06"><ikep4j:message pre='${preHelp}' key='qna.menu6' /></a>
								<ul>
									<li><a href="#QA_07"><ikep4j:message pre='${preHelp}' key='qna.menu6_1' /></a></li>
									<li><a href="#QA_08"><ikep4j:message pre='${preHelp}' key='qna.menu6_2' /></a></li>		
								</ul>	
							</li>
							-->
						</ul>	
					</li>		
					
					<!-- 쪽지
					<li class="no_child">
						<a class="head" href="message.html"><ikep4j:message pre='${preHelp}' key='message.title' /></a>
						<ul class="innerLink">
							<li><a href="#MSG_01"><ikep4j:message pre='${preHelp}' key='message.menu1' /></a></li>
							<li><a href="#MSG_02"><ikep4j:message pre='${preHelp}' key='message.menu2' /></a></li>
							<li><a href="#MSG_03"><ikep4j:message pre='${preHelp}' key='message.menu3' /></a></li>
							<li><a href="#MSG_04"><ikep4j:message pre='${preHelp}' key='message.menu4' /></a></li>
							<li><a href="#MSG_05"><ikep4j:message pre='${preHelp}' key='message.menu5' /></a></li>
						</ul>	
					</li>		
					-->								
					
					<li class="no_child">
						<a class="head" href="ideation.html"><ikep4j:message pre='${preHelp}' key='ideation.title' /></a>
						<ul class="innerLink">
							<li><a href="#Idea_01"><ikep4j:message pre='${preHelp}' key='ideation.menu1' /></a></li>
							<li><a href="#Idea_02"><ikep4j:message pre='${preHelp}' key='ideation.menu2' /></a></li>
							<li><a href="#Idea_03"><ikep4j:message pre='${preHelp}' key='ideation.menu3' /></a></li>
							<li><a href="#Idea_04"><ikep4j:message pre='${preHelp}' key='ideation.menu4' /></a></li>
							<li><a href="#Idea_05"><ikep4j:message pre='${preHelp}' key='ideation.menu5' /></a></li>
							<li><a href="#Idea_06"><ikep4j:message pre='${preHelp}' key='ideation.menu6' /></a></li>
							<li><a href="#Idea_07"><ikep4j:message pre='${preHelp}' key='ideation.menu7' /></a>
								<ul>
									<li><a href="#Idea_08"><ikep4j:message pre='${preHelp}' key='ideation.menu7_1' /></a></li>
									<li><a href="#Idea_09"><ikep4j:message pre='${preHelp}' key='ideation.menu7_2' /></a></li>		
								</ul>	
							</li>
						</ul>	
					</li>						
					
					<li class="no_child">
						<a class="head" href="workspace.html"><ikep4j:message pre='${preHelp}' key='workspace.title' /></a>
						<ul class="innerLink">
							<li><a href="#workspace01"><ikep4j:message pre='${preHelp}' key='workspace.menu1' /></a></li>
							<li><a href="#workspace02"><ikep4j:message pre='${preHelp}' key='workspace.menu2' /></a>
								<ul>
									<li><a href="#workspace03"><ikep4j:message pre='${preHelp}' key='workspace.menu2_1' /></a></li>
									<li><a href="#workspace04"><ikep4j:message pre='${preHelp}' key='workspace.menu2_2' /></a></li>		
								</ul>	
							</li>
							<li><a href="#workspace05"><ikep4j:message pre='${preHelp}' key='workspace.menu3' /></a></li>
							<li><a href="#workspace06"><ikep4j:message pre='${preHelp}' key='workspace.menu4' /></a>
								<ul>
									<li><a href="#workspace07"><ikep4j:message pre='${preHelp}' key='workspace.menu4_1' /></a></li>
									<li><a href="#workspace08"><ikep4j:message pre='${preHelp}' key='workspace.menu4_2' /></a></li>	
									<li><a href="#workspace09"><ikep4j:message pre='${preHelp}' key='workspace.menu4_3' /></a></li>		
								</ul>	
							</li>
							<li><a href="#workspace10"><ikep4j:message pre='${preHelp}' key='workspace.menu5' /></a>
								<ul>
									<li><a href="#workspace11"><ikep4j:message pre='${preHelp}' key='workspace.menu5_1' /></a></li>
									<li><a href="#workspace12"><ikep4j:message pre='${preHelp}' key='workspace.menu5_2' /></a></li>		
								</ul>	
							</li>
							<li><a href="#workspace13"><ikep4j:message pre='${preHelp}' key='workspace.menu6' /></a>
								<ul>
									<li><a href="#workspace14"><ikep4j:message pre='${preHelp}' key='workspace.menu6_1' /></a></li>
									<li><a href="#workspace15"><ikep4j:message pre='${preHelp}' key='workspace.menu6_2' /></a></li>		
								</ul>	
							</li>
						</ul>	
					</li>						
					
					<li class="no_child">
						<a class="head" href="custommer.html"><ikep4j:message pre='${preHelp}' key='custommer.title' /></a>
						<ul class="innerLink">
							<li><a href="#custommer_01"><ikep4j:message pre='${preHelp}' key='custommer.menu1' /></a>
								<ul>
									<li><a href="#custommer_01_01"><ikep4j:message pre='${preHelp}' key='custommer.menu1_1' /></a></li>
									<li><a href="#custommer_01_02"><ikep4j:message pre='${preHelp}' key='custommer.menu1_2' /></a></li>		
								</ul>	
							</li>
							<li><a href="#custommer02"><ikep4j:message pre='${preHelp}' key='custommer.menu2' /></a>
								<ul>
									<li><a href="#custommer_02_01"><ikep4j:message pre='${preHelp}' key='custommer.menu2_1' /></a></li>	
								</ul>	
							</li>
							<li><a href="#custommer_03"><ikep4j:message pre='${preHelp}' key='custommer.menu3' /></a>
								<ul>
									<li><a href="#custommer_03_01"><ikep4j:message pre='${preHelp}' key='custommer.menu3_1' /></a></li>	
								</ul>	
							</li>
							<li><a href="#custommer_04"><ikep4j:message pre='${preHelp}' key='custommer.menu4' /></a>
								<ul>
									<li><a href="#custommer_04_01"><ikep4j:message pre='${preHelp}' key='custommer.menu4_1' /></a></li>	
								</ul>	
							</li>
							<li><a href="#custommer_05"><ikep4j:message pre='${preHelp}' key='custommer.menu5' /></a>
								<ul>
									<li><a href="#custommer_05_01"><ikep4j:message pre='${preHelp}' key='custommer.menu5_1' /></a></li>	
								</ul>	
							</li>
						</ul>	
					</li>
					
					
					<li class="no_child">
						<a class="head" href="kms.html"><ikep4j:message pre='${preHelp}' key='kms.title' /></a>
						<ul class="innerLink">
							<li><a href="#Kms_01"><ikep4j:message pre='${preHelp}' key='kms.menu1' /></a>
							</li>
							<li><a href="#Kms_02"><ikep4j:message pre='${preHelp}' key='kms.menu2' /></a>	
							</li>
							<li><a href="#Kms_03"><ikep4j:message pre='${preHelp}' key='kms.menu3' /></a>								
							</li>
							<li><a href="#Kms_04"><ikep4j:message pre='${preHelp}' key='kms.menu4' /></a>								
							</li>
							<li><a href="#Kms_05"><ikep4j:message pre='${preHelp}' key='kms.menu5' /></a>
							</li>
						</ul>	
					</li>
				</ul>
			
			</div>	
		</div>	
		<!--//online_leftMenu End-->
		
		<!--online_mainContents Start-->
		<div id="online_mainContents"></div>			
	
	</div>
	<!--//blockMain End-->
	
	<div class="clear"></div>
	<!--onlinefooter Start-->	
	<div class="online_footer">
		<h1 class="none"><ikep4j:message pre='${preHelp}' key='copyright.title' /></h1>
		<div class="textCenter">
			<div class="footer_logo">
				<a href="#a"><img src="<c:url value="/base/images/common/logo_s.png"/>" alt="logo_small" /></a>
				<span>Copyright © 2012 Moorim Paper. All rights reserved.</span>
			</div>
		</div>
	</div>
	<!--//onlinefooter End-->
		
</div>
<!--//online_wrapper End-->
</body>
</html>