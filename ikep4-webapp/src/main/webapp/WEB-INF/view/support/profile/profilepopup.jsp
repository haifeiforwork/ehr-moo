<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" /> 
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<%-- 메시지 관련 Prefix 선언 End --%>


<script language=javascript>
		
	/* 	var hsmeIdAr = new Array();
		var hsmeTdAr = new Array();
		
		hsmeIdAr[0] = "hxUxWjfdVdXeDXNHgBiGSw=="; 
		hsmeTdAr[0] = "HSME_1";  */
		
		/*hsmeIdAr[0] = "jf7EZ9oCHRZBeAHM5r2cGA==";	//TEST1
		hsmeIdAr[1] = "DNQ9P0tr/MjdDOe/2adYMQ==";	//TEST2
		hsmeIdAr[2] = "+VWb3QvwQ78JMRuRBdtEgQ==";	//TEST3
		hsmeIdAr[3] = "yFA+QHW8vWI+Nq/6f8lyCA==";	//TEST4
		hsmeIdAr[4] = "gV9uKfQcMv9rgDfN6VcuBw==";	//TEST5
		hsmeIdAr[5] = "HbUe4wr/5th8DojjOSfODg==";	//TEST6
		
		hsmeTdAr[0] = "HSME_1";				//TEST1 KEY
		hsmeTdAr[1] = "HSME_2";				//TEST2 KEY
		hsmeTdAr[2] = "HSME_3";				//TEST3 KEY
		hsmeTdAr[3] = "HSME_4";				//TEST4 KEY
		hsmeTdAr[4] = "HSME_5";				//TEST5 KEY
		hsmeTdAr[5] = "HSME_6";				//TEST6 KEY*/
		
		function GetUserStatus()
		{
			AtMessengerEnabler.GetUserStatus(hsmeIdAr, hsmeTdAr);
		}
		
		function HSMEUserStatus(idVal, state)
		{
			if ( document.getElementById(idVal) == null ) return;
			
			
			
			if ( state == 0 )
			{
				document.getElementById(idVal).innerHTML = "오프라인";
				document.getElementById("mStatusIcon").src="<c:url value='/base/images/icon/satom_off.bmp' />";
				document.getElementById("mStatusIcon").title="오프라인";
			}
			else if ( state == 1 )
			{
				document.getElementById(idVal).innerHTML = "온라인";
				document.getElementById("mStatusIcon").src="<c:url value='/base/images/icon/satom_on.bmp' />";
				document.getElementById("mStatusIcon").title="온라인";
			}
			else if ( state == 2 )
			{
				document.getElementById(idVal).innerHTML = "자리비움";
				document.getElementById("mStatusIcon").src="<c:url value='/base/images/icon/satom_time.bmp' />";
				document.getElementById("mStatusIcon").title="자리비움";
			}
			else if ( state == 3 )
			{
				document.getElementById(idVal).innerHTML = "회의중";
				document.getElementById("mStatusIcon").src="<c:url value='/base/images/icon/satom_meeting.bmp' />";
				document.getElementById("mStatusIcon").title="회의중";
			}
			else if ( state == 4 )
			{
				document.getElementById(idVal).innerHTML = "통화중";
				document.getElementById("mStatusIcon").src="<c:url value='/base/images/icon/satom_hp.bmp' />";
				document.getElementById("mStatusIcon").title="통화중";
			}
			else if ( state == 5 )
			{
				document.getElementById(idVal).innerHTML = "다른용무중";
				document.getElementById("mStatusIcon").src="<c:url value='/base/images/icon/satom_other.bmp' />";
				document.getElementById("mStatusIcon").title="다른용무중";
			}
		}
	
		function goMessenger(hsmeIdAr1,hsmeTdAr1){
			
			var hsmeIdAr = new Array();
			var hsmeTdAr = new Array();
			
			hsmeIdAr[0] = hsmeIdAr1; 
			hsmeTdAr[0] = hsmeTdAr1; 
			AtMessengerEnabler.GetUserStatus(hsmeIdAr, hsmeTdAr);
		}
	</script>
	
	<SCRIPT LANGUAGE="JAVASCRIPT" EVENT="SetUserStatus(id, state)" FOR="AtMessengerEnabler">
		HSMEUserStatus(id, state);
	</SCRIPT>
	
	<SCRIPT LANGUAGE="JAVASCRIPT" EVENT="UserStatusReset()" FOR="AtMessengerEnabler">
		alert("TEST");
	</SCRIPT>
	



<script type="text/javascript">
<!--
function MM_showHideLayers() { //v9.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) 
  with (document) if (getElementById && ((obj=getElementById(args[i]))!=null)) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v=='hide')?'hidden':v; }
    obj.visibility=v; }
}

function setProfileFavorite(data) {
	var str = "";
	if(data.status == 'exists'){
		str="<a class=\"ic_rt_favorite select\" onclick=\"deleteFavorite();\" href=\"#a\"><span></span></a>";
	}else if(data.status == 'success'){
		str="<a class=\"ic_rt_favorite\" onclick=\"addUserFavorite();\" href=\"#a\"><span></span></a>";
	}
	$jq("#favoriteBtnView").html(str);

}

//즐겨 찾기 추가 여부 
function displyFavorite(data) {
	if(data.status == 'success'){
		iKEP.chkFavorite('${targetUserId}', setProfileFavorite);
	}
}

function addUserFavorite(){
	iKEP.addFavorite('PEOPLE','PF','${profile.userId}','${profile.userName}',displyFavorite);
	
}

function deleteFavorite(){
	iKEP.delFavorite('','${targetUserId}', displyFavorite);
}

//-->
</script>

<script type="text/javascript">
<!--
(function($) {
	$(document).ready(function() {
		<c:if test="${profile.userStatus == 'T'}">
			alert("사용자 정보가 존재하지 않습니다.");
			window.close();
		</c:if>
		document.title="<c:choose><c:when test='${user.localeCode == portal.defaultLocaleCode}'><c:out value='${profile.userName}'/></c:when><c:otherwise><c:out value='${profile.userEnglishName}'/></c:otherwise></c:choose><ikep4j:message pre='${preProfileMain}' key='profile.owner' />";
		// 화면 로딩시 각각 페이지 호출 시작
		getProfile();
		
		var $fncIcons = $jq("#fncIcons").show()
				.find("a").hide();
		<c:if test="${CommonConstant.PACKAGE_VERSION != CommonConstant.IKEP_VERSION_BASIC}">
			$fncIcons.filter("[title=mail]").show()
				.click(function() {	// Mail Popup 이동
					var nameList = ['${profile.userName}'];
					var emailList = ['${profile.mail}'];
					iKEP.sendMailPop(nameList, emailList, "", "", "", "");
				}).attr("href", "#a");
			$fncIcons.filter("[title=message]").show()
				.click(function() {	// Message Popup 이동
					goMessenger('${encryptedId}','HSME_1');AtMessengerEnabler.TrackPopupMenu('${encryptedId}');
				}).attr("href", "#a");
		</c:if>
		<c:if test="${CommonConstant.PACKAGE_VERSION == CommonConstant.IKEP_VERSION_FULL}">
			$fncIcons.filter("[title=sms]").show()
				.click(function() {	// SMS Popup 이동
					//iKEP.popupOpen('<c:url value="${smsUrl}"/>?receiverId=${targetUserId}&name=${profile.userName}&mobile=${profile.mobile}', {width:558, height:432}, "smsPopup");
					iKEP.sendSms(1,'${targetUserId}');
				}).attr("href", "#a");
			$fncIcons.filter("[title=messenger]").show();
		</c:if>
		
		<c:if test="${editAuthFlag != 'true' }">
		
		// Favorite 유무 확인
		iKEP.chkFavorite('${targetUserId}', setProfileFavorite);		
				
		</c:if>
	});
	
	getProfile = function() {
		$.ajax({
		    url : "<c:url value='/support/profile/viewProfile.do'/>",
		    data : {'targetUserId':'${targetUserId}','mode':'popup'}, 
		    type : "get",
		    success : function(result) {
		    	$("#profileView").html(result);
		    }
		});
	}; 
})(jQuery);  
//-->
</script>

	<!--popup_title Start
	<div id="popup_title_2">
        <div class="popup_bgTitle_l"></div>
		<h1><ikep4j:message pre='${preProfileMain}' key='profile.title' /></h1>
		<a href="javascript:iKEP.returnPopup();"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
	</div>
	-->
	<!--//popup_title End-->

				
<!--pr_profile_top Start-->
<c:if test="${profile.userStatus != 'T'}">
<div class="pr_profile_top" style="padding:15px;">

	<div class="corner_RoundBox09" >		
		<h2>Profile</h2>
		<!-- a class="button_pr" href="#a"><span><img src="../../images/icon/ic_plus.gif" alt="" />Follow</span></a -->
		<div class="pr_bg_profile" >
			<div class="profile" >
				<div class="r_bg_profile_input">
					<span id="profileStatusView">${profile.profileStatus}</span>
				</div>
			</div>
		</div>
		<div class="clear"></div>
		
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
	</div>	

	<!--corner_RoundBox07 Start-->
	<div class="corner_RoundBox07 mb10" >
						
		<!--pr_profile Start-->
		<div class="pr_profile">		
			<!--pr_tl Start-->
			<div class="pr_tl" style="margin-right:0px;">		
				<div class="prPhoto">
					<img id="mainPictureImage" src="<c:url value='${profile.profilePicturePath}' />" width="170" height="170" alt="Profile Image" onerror="this.src='<c:url value='/base/images/common/photo_170x170.gif'/>'" />
					<ul id="fncIcons" style="display:none;">
						<li><a title="mail"><img onload="goMessenger('${encryptedId}','HSME_1');" src="<c:url value='/base/images/icon/ic_email.gif' />" alt="mail" /></a></li>
						<li><a title="sms"><img src="<c:url value='/base/images/icon/ic_sms.gif' />" alt="sms" /></a></li>			
						<li><a title="message"><img id="mStatusIcon" title="오프라인" src="<c:url value='/base/images/icon/satom_off.bmp' />" alt="messenger"/></a>
						</li>
						<object classid="clsid:28F3B6CD-AA28-4411-9C41-67B9448DAB4C" width="0" height="0" id="AtMessengerEnabler" codebase="<c:url value='/Bin/AtMessengerWebEnabler.cab'/>">
						     <param name='SERVER_IP' value='10.1.5.36'>
						     <param name='SERVER_PORT' value='2234'>
						     <param name='MYID' value='${encryptedMyId}'>
						     <param name='USECHAT' value='Y'>
						     <param name='USEMESSAGE' value='Y'>
						     <param name='USEFILE' value='Y'>
						     <param name='MESSENGER_TITLE' value='MOORIMMessenger'>
						</object>
					</ul>
				</div>	
				<div id="HSME_1"></div>
				<div class="prInfo">
					<div class="prInfo_name">
                    <c:if test="${leaderYnFlag == 'true' }">
                        <img src="<c:url value='/base/images/icon/people_t.png' />"
                            alt="<ikep4j:message pre='${preProfileMain}' key='leader.icon'/>" />
                    </c:if>
                    <strong><c:out value="${profile.userName}" /> <c:choose>
                            <c:when test="${user.localeCode == portal.defaultLocaleCode}">
                                <c:choose>
                                       <c:when test="${!empty profile.jobDutyName && profile.jobDutyName != ''}">
                                           <c:out value="${profile.jobDutyName}" />
                                       </c:when>                        
                                       <c:otherwise>
                                           <c:out value="${profile.jobTitleName}" />
                                       </c:otherwise>
                                  </c:choose>                                
                            </c:when>
                            <c:otherwise>
                                <c:when test="${!empty profile.jobDutyName && profile.jobDutyName != ''}">
                                           <c:out value="${profile.jobDutyName}" />
                                       </c:when>                        
                                       <c:otherwise>
                                           <c:out value="${profile.jobTitleEnglishName}" />
                                       </c:otherwise>
                            </c:otherwise>
                        </c:choose> </strong> <span><c:out value="${profile.userEnglishName}" /> </span>
                        <span>(<c:out value="${profile.hanziName}" /> )</span> 
                        <c:if test="${editAuthFlag != 'true'}">
							<span id="favoriteBtnView"></span>
						</c:if>                      
                </div>
					<form name="profileMainForm" id="profileMainForm" action="" method="post" style="border: 1px solid white; padding: 0px;" onsubmit="return false">
					<div id="profileView" ></div>
					</form>
				</div>
				
			</div>
			<!--//pr_tl End-->			
			
		</div>
		<!--//pr_profile End-->
		<div class="bg_shadow_l"></div>
		<div class="bg_shadow_c"></div>
		<div class="bg_shadow_r"></div>
		<div class="l_b_corner"></div>
		<div class="r_b_corner"></div>			
	</div>
	<!--//corner_RoundBox07 End-->
</div>
</c:if>
<!--//pr_profile_top End-->
