<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"
	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<jsp:useBean id="IKepConstant"
	class="com.lgcns.ikep4.framework.constant.JSTLConstant" />

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preProfileMain" value="ui.support.profile.main" />
<c:set var="preGuestbook" value="ui.support.guestbook.view.main" />

<c:set var="preButton" value="ui.support.profile.common.button" />
<c:set var="preMsgProfile" value="message.support.profile.main" />
<c:set var="preMsgGuestbook" value="message.support.guestbook.view.main" />
<c:set var="preMessage" value="message.support.addressbook.popup" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript"
	src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript">function MM_showHideLayers() { //v9.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) 
  with (document) if (getElementById && ((obj=getElementById(args[i]))!=null)) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v=='hide')?'hidden':v; }
    obj.visibility=v; }
}
</script>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<%
	String sFacebookAuthUrl = com.lgcns.ikep4.support.externalSNS.base.Constant.REDIRECT_URL;
%>

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
			//AtMessengerEnabler.GetUserStatus(hsmeIdAr, hsmeTdAr);
		}
	</script>
	
	<SCRIPT LANGUAGE="JAVASCRIPT" EVENT="SetUserStatus(id, state)" FOR="AtMessengerEnabler">
		HSMEUserStatus(id, state);
	</SCRIPT>
	
	<SCRIPT LANGUAGE="JAVASCRIPT" EVENT="UserStatusReset()" FOR="AtMessengerEnabler">
		alert("TEST");
	</SCRIPT>
	

<script type="text/javascript">
	var $groupTree;

	var validator;
	
	var isDeviceToggle = false;

	//프로파일 이미지 업로드 팝업
	function fileUploadForProfile(userId, targetId, imgSrc, callback) {
		//var height = targetId == "pf_picture" ? 200 : 230;
		var height = 230;
		var options = {
				title : "Profile Image Upload",
				url : iKEP.getContextRoot() + '/support/fileupload/uploadFormForProfile.do?userId='+userId+'&targetId='+targetId+"&editorAttachYn=0&imageYn=1",
				width : 500,
				height : height,
				modal : true,
				resizable : false,
				scroll : "no",
				params : { imgSrc : imgSrc },
				callback: function(result) {
					callback(result.status, result.fileId, result.fileName, result.messgage, result.gubun);
				},
				beforeClose : function(event, ui) {	// IE9에서 plupload객체 오류로 인해 추가
					$iframe = $jq(event.target).children("iframe");
					if($iframe.is("*")) {
						var uploader = $iframe[0].contentWindow.uploader;
						if(uploader) uploader.destroy();
						//iKEP.debug($iframe.contents().find("object"));
					}
				}
			};
		iKEP.showDialog(options);
	};
	
	$jq(document).ready(function() {
		

	
		//iKEP.goProfilePopupMain('user39');
		
		
		
		// 화면 로딩시 각각 페이지 호출 시작
		getProfile();
		
		$jq('#viewMore').show(); //  addnew
		$jq('#myPsInfo_edit_btn').hide();
		$jq('#myPsInfo_save_btn').hide();
		$jq('#myPsInfo_cancel_btn').hide();
		
		
		
		<c:if test="${pageMode != 'simple'}">
			gerExpertTagView();
			//getCareerView();
			getMyDocuemnt();
			//getMyImages();
			getMyGallery();
			getExpertFieldView('view');
			
			getMySchedule();
			<%-- 12.08.23 Francis Choi 무림 페이지를 위해 수정 --%> 
			//getFollowList();
			//getMyComment();
			<%-- 12.08.23 Francis Choi 무림 페이지를 위해 수정 --%> 
			//getMyFiles();
			<%-- 12.08.23 Francis Choi 무림 페이지를 위해 수정 --%> 
			//getMyPsInfo('view');
			
			//activity();
		
		</c:if>
		
		if(parent.createGroupTree) {
			parent.createGroupTree('${targetUserId}');
			//parent.reopenUserTree('${targetUserId}');
		}

		// 화면 로딩시 각각 페이지 호출 종료
		
		// 타인의 프로파일에 들어 왔을때만 Following 버튼 처리 및 Favorite 추가
		<c:if test="${editAuthFlag != 'true' }">
		
		// Favorite 유무 확인
		iKEP.chkFavorite('${targetUserId}', setProfileFavorite);		
				
		</c:if>
		
		// 자기 프로파일에 들어 왔을때문 수정 가능하게 이벤트 처리 
		<c:if test="${editAuthFlag == 'true' }">
		
		$jq('#pf_edit_btn').click(function(){
			 editProfile();
		});
		
		$jq('#pf_passwd_btn').click(function(){

			//alert("암호 변경 페이지 호출");
			iKEP.popupOpen('<c:url value="/portal/main/newPassword.do"/>', {width:530, height:260, status:true, scrollbar:false, toolbar:false, location:false}, 'NewPassword');
		});
		
		// 
		$jq('#pfst_edit_btn').click(function(){
			if($jq("#profileStatusView").children("form").is("*")) {
				$jq("#profileStatus").val($jq("#profileStatusView").children("div").find("span").text());
				$jq("#profileStatusView")
					.children("div").hide().end()
					.children("form").show();
			} else {
				$jq("#profileStatusView")
					.children("div").hide();
				$jq.get("<c:url value='/support/profile/viewProfileStatus.do'/>", {"targetUserId":"${targetUserId}","editflag":"edit"})
					.success(function(res) {
						$jq("#profileStatusView").append(res);
					});
			}
		});
		
		// 신규 추가
		
		$jq('#myPsInfo_edit_btn').click(function(){
			 editMyPsInfo();
		});
		
		$jq('#myPsInfo_cancel_btn').click(function(){
			 getMyPsInfo('cancel');
		});
		
		$jq('#myPsInfo_more_btn').toggle(show, hide);

		function show(){
			getMyPsInfo('view');
			$jq('#viewMore').show();
			$jq('#myPsInfo_edit_btn').show();
			$jq('#MyPsInfoMoreImg').attr("src", "<c:url value='/base/images/common/tree_m.gif' />");
		}
		
		function hide(){
			getMyPsInfo('cancel');
			$jq('#viewMore').hide();
			$jq('#myPsInfo_edit_btn').hide();
			$jq('#myPsInfo_save_btn').hide();
			$jq('#myPsInfo_cancel_btn').hide();
			
			$jq('#MyPsInfoMoreImg').attr("src", "<c:url value='/base/images/common/tree_p.gif' />");
		}
		
		
		// 신규추가 끝
		
		//exfd_edit_btn
		$jq('#exfd_edit_btn').click(function(){
			getExpertFieldView('edit');
		});		

		$jq('#picture_edit').click(function(){
			fileUploadForProfile('${profile.userId}','picture', $jq("#pictureImage").attr("src"), afterFileUpload);
		});
						
		</c:if>
		
		<c:if test="${isAdmin || user.userId eq profile.userId}">
		//관리자인경우에는 사진의 편집이 가능하다
	    $jq('#pf_picture_edit').click(function(){
	            fileUploadForProfile('${profile.userId}','pf_picture', $jq("#profilePictureImage").attr("src"), afterFileUpload);
	    }); 
	    </c:if> 
		
		$jq('#myPsInfo_save_btn').click(function(){
			$jq('#myPsInfoForm').trigger("submit");
		});
		
		$jq('#pf_save_btn').click(function(){
			
			var tmpCurrentJob = "";
			if($jq("#currentJob1").val()!=""){
				tmpCurrentJob = "1. "+$jq("#currentJob1").val();
			}
			if(tmpCurrentJob!=""){
				if($jq("#currentJob2").val() != ""){
					tmpCurrentJob = tmpCurrentJob+"<br>2. "+$jq("#currentJob2").val();
				}
			}else{
				tmpCurrentJob = "1. "+$jq("#currentJob2").val();
			}
			
			if(tmpCurrentJob!=""){
				if($jq("#currentJob3").val() != ""){
					var currentJobs = tmpCurrentJob.split("<br>");
					if(currentJobs.length < 2){
						tmpCurrentJob = tmpCurrentJob+"<br>2. "+$jq("#currentJob3").val();
					}else{
						tmpCurrentJob = tmpCurrentJob+"<br>3. "+$jq("#currentJob3").val();
					}
				}
			}else{
				tmpCurrentJob = "1. "+$jq("#currentJob3").val();
			}
			$jq("#currentJob").val(tmpCurrentJob);
			
			$jq("#profileMainForm").trigger("submit");
		});
		$jq('#pf_cancel_btn').click(function(){
			getProfile();
		});		
		
		$jq(".deviceToggle").live('click', function() {
		    if ($jq(this).attr("class") == "deviceToggle") {
		      this.src = this.src.replace("_down","_up");
		      isDeviceToggle = true;
		    } else {
		      this.src = this.src.replace("_up","_down");
		      isDeviceToggle = false;
		    }
		    $jq(this).toggleClass("on");
		    $jq("#moreDeviceList").toggle();
		    iKEP.iFrameContentResize();
		});
		
		getGuestbookList('1');
		
		setTimeout(resizeFinish, 2000);
		
	});
	
	$jq.template("addrBookItemUser", '<option value="\${id}">\${name}/\${jobPosition}/\${teamName}</option>');
	$jq.template("addrBookItemGroup", '<option value="\${code}">\${name}</option>');
	
	// mobile device lock/unlock
	function updateDeviceStatus(uuid,isLock) {
		$jq.ajax({
		    url : "<c:url value='/support/profile/updateDeviceStatus.do'/>",
		    data : {'uuid':uuid,'isLock':isLock},
		    type : "post",
		    success : function(data) {
	    		if ( data == "OK" ) {
	    			getProfile();	
	    		} else {
	    			alert(data);	
	    		}
		    }
		});
	}
	
	// mobile device initailize 
	function initializeDevice(uuid) {
		if ( confirm("<ikep4j:message pre='${preProfileMain}' key='mobile.init.confirm'/>") ) {
			$jq.ajax({
			    url : "<c:url value='/support/profile/initializeDevice.do'/>",
			    data : {'uuid':uuid},
			    type : "post",
			    success : function(data) {
			    	if ( data == "OK" ) {
		    			getProfile();	
		    		} else {
		    			alert(data);
		    		}
			    }
			});
		}
	}
	
	function resizeFinish(){
		//alert("마지막으로 리사이즈 한번더");
		iKEP.iFrameContentResize();
	}
	
	// 트위터 인증 팝업 호출
	function authTwitterPopup(){
		//${authenticationURL}
		iKEP.popupOpen("<c:url value='/support/externalSNS/TwitterAuth.do'/>", {width:820, height:450, resizable:true, callback : function(result) {
			getProfile();
		}});
	}
	
	// facebook 인증 팝업 호출
	function authFacebookPopup() {
		iKEP.popupOpen("<c:url value='/support/externalSNS/FacebookMain.do'/>", {width:1000, height:650, resizable:true, callback : function(result) {
			getProfile();
		}});
	}
	
	// My Personal Info(Start)
	function getMyPsInfo(mode) {
		if(mode=='view'){
			$jq('#viewMore').hide();
			$jq('#myPsInfo_edit_btn').hide();
			$jq('#myPsInfo_save_btn').hide();
			$jq('#myPsInfo_cancel_btn').hide();
		}else{
			$jq('#viewMore').show();
			$jq('#myPsInfo_edit_btn').show();
			$jq('#myPsInfo_save_btn').hide();
			$jq('#myPsInfo_cancel_btn').hide();
		}
		$jq.ajax({
		    url : "<c:url value='/support/profile/viewMyPsInfo.do'/>",
		    data : {'targetUserId':'${targetUserId}'}, 
		    type : "post",
		    success : function(result) {
		    	$jq("#myPsInfoView").html(result);
		    	//parent.profileResize();
		    	iKEP.iFrameContentResize();
		    }
		});
	}
	
	function editMyPsInfo(){
		$jq("#myPsInfo_edit_btn").hide();
		$jq("#myPsInfo_save_btn").show();
		$jq("#myPsInfo_cancel_btn").show();
		
		$jq.ajax({
		    url : "<c:url value='/support/profile/editMyPsInfo.do'/>",
		    data : {'targetUserId':'${targetUserId}'},
		    type : "post",
		    success : function(result) {
		    	$jq("#myPsInfoView").html(result);
		    }
		});
	}
	// My Personal Info(End)
	
	function getProfile() {
		$jq("#pf_edit_btn").show();
		$jq("#pf_save_btn").hide();
		$jq("#pf_cancel_btn").hide();
		
		$jq.ajax({
		    url : "<c:url value='/support/profile/viewProfile.do'/>",
		    data : {'targetUserId':'${targetUserId}'}, 
		    type : "get",
		    success : function(result) {
		    	  $jq("#profileView").html(result);
		    	//parent.profileResize();
		    	iKEP.iFrameContentResize();
		    }
		});
	}
	
	function editProfile(){
		$jq("#pf_edit_btn").hide();
		$jq("#pf_save_btn").show();
		$jq("#pf_cancel_btn").show();
		
		$jq.ajax({
		    url : "<c:url value='/support/profile/editProfile.do'/>",
		    data : {'targetUserId':'${targetUserId}'},
		    type : "get",
		    success : function(result) {
		    	$jq("#profileView").html(result);
		    }
		});
	}
	
	function getExpertFieldView(editflag) {
		if( editflag == 'edit'){
			$jq("#exfd_edit_btn").hide();
			//$jq("#exfd_save_btn").show();
			//$jq("#exfd_cancel_btn").show();
		}else{
			$jq("#exfd_edit_btn").show();
			//$jq("#exfd_save_btn").hide();
			//$jq("#exfd_cancel_btn").hide();
		}
		
		$jq.ajax({
		    url : "<c:url value='/support/profile/viewExpertFieldView.do'/>",
		    data : {'targetUserId':'${targetUserId}','editflag':eval("'"+editflag+"'")},
		    type : "get",
		    success : function(result) {
		    	$jq("#expertFieldView").html(result);
		    	//parent.profileResize();
		    	iKEP.iFrameContentResize();
		    }
		});
	}
	
	
	//submitExpertFieldView = function() {

	//	$("#exfdForm").validate(validOptions);
	//	$("#exfdForm").submit();
	//}; 
	
	function gerExpertTagView() {
		$jq.ajax({
		    url : "<c:url value='/support/profile/viewExpertTagView.do'/>",
		    data : {'targetUserId':'${targetUserId}'},
		    type : "get",
		    success : function(result) {
		    	$jq("#expertTagView").html(result);
		    	//parent.profileResize();
		    	iKEP.iFrameContentResize();
		    }
		});
	}
	
	function getGuestbookList(pageIndex) {
		$jq.ajax({
		    url : "<c:url value='/support/guestbook/listGuestbookMore.do'/>",
		    data : {'targetUserId':'${targetUserId}','pageIndex':eval("'"+pageIndex+"'")},
		    type : "get",
		    success : function(result) {
		    	$jq("#guestbookView").html(result);
		    	//parent.profileResize();
		    	iKEP.iFrameContentResize();
		    }
		});
	}
	
	
	function getCareerView() {
		$jq.ajax({
		    url : "<c:url value='/support/profile/getCareerR5View.do'/>",
		    data : {'targetUserId':'${targetUserId}'},
		    type : "get",
		    success : function(result) {
		    	$jq("#careerView").html(result);
		    	//parent.profileResize();
		    	iKEP.iFrameContentResize();
		    }
		});
	}

	function getCareerMorePopup() {
		var options = {
			    title : "Career",
			    url : "<c:url value='/support/career/readCareerPopup.do?targetUserId=${targetUserId}'/>",
			    modal : true,
			    //position: 'top',
			    width : 750, 
			    height : 500,
			    maxWidth : 700,
			    maxHeight :500,
			    resizable : false,
			    //callback : function(result) {
			    //	getCareerView();
				//},
				beforeClose : getCareerView
			};
		iKEP.showDialog(options);
	}
	
	//이미지 관련정보 부가정보 테이블에 저장하고 입력창에서 값 변경하기
	function afterFileUpload(status, fileId, fileName, message, gubun){
		//이미지파일 업로드한 후에 받은 파일 id를 서버에 보낸다.
		if( gubun == 'picture') {
			if("" != fileId){
				$jq('#pictureImage').attr('src', iKEP.getContextRoot() + fileName) ;
			}
		}else{
			if("" != fileId){
				$jq('#profilePictureImage').attr('src', iKEP.getContextRoot() + fileName) ;
			}
		}
	}
	
	// 다른 사람의 프로파일로 이동
	function goOtherProfile(targetUserId) {
		document.location.href = "<c:url value='/support/profile/getProfile.do?targetUserId='/>" + targetUserId ;
	}
	
	// 방명록 이동
	function goGuestbook() {
		document.location.href = "<c:url value='/support/guestbook/listGuestbook.do?targetUserId=${targetUserId}'/>" ;
	}
	
	// 소셜 블로그 이동
	function goSocialblog() {
		parent.document.location.href = "<c:url value='${socialblogHomeUrl}?blogOwnerId=${targetUserId}'/>" ;
	}
	
	// 마이크로 블로그 이동
	function goMicroblog() {
		parent.document.location.href = "<c:url value='${microblogHomeUrl}?ownerId=${targetUserId}'/>" ;
	}
	
	
	// My Document 이동
	function goMydocument() {
		//parent.document.location.href = "<c:url value='${myDocumentUrl}?userId=${targetUserId}&userLocale=${user.localeCode}'/>" ;
		parent.document.location.href = "<c:url value='${myDocumentUrl}'/>" ;
	}
	
	// My Image 이동
	function goMyImages() {
		parent.document.location.href = "<c:url value='${myImagesUrl}&userId=${targetUserId}&userLocale=${user.localeCode}'/>" ;
	}
	
	// My Gallery 이동
	goMyGallery = function() {
		//parent.document.location.href = "<c:url value='${myGalleryUrl}&userId=${targetUserId}&userLocale=${user.localeCode}'/>" ;
		document.location.href = "<c:url value='${myGalleryUrl}&userId=${targetUserId}&userLocale=${user.localeCode}'/>" ;
	};
	
	// My Comment 이동
	function goMyComment() {
		parent.document.location.href = "<c:url value='${myCommentUrl}&userId=${targetUserId}&userLocale=${user.localeCode}'/>" ;
	}
	
	// My Files 이동
	function goMyFiles() {
		parent.document.location.href = "<c:url value='${myFilesUrl}&userId=${targetUserId}&userLocale=${user.localeCode}'/>" ;
	}
	
	// My Schedule 로 이동
	function goMySchedule() {
		//iKEP.popupOpen("<c:url value='/lightpack/planner/calendar/viewSchedule.do?userId=${targetUserId}'/>", {width:700, height:320, resizable:true, callback : function(result) {}});
		parent.document.location.href = "<c:url value='/lightpack/planner/calendar/init.do?action=onClickTargetUser&userId=${targetUserId}'/>";
	}
	
	// SMS Popup 이동
	function goSMS() {
		//iKEP.popupOpen('<c:url value="${smsUrl}"/>?receiverId=${targetUserId}&name=${profile.userName}&mobile=${profile.mobile}', {width:558, height:432}, "smsPopup");
		iKEP.sendSms(1,'${targetUserId}');
	}
	
	// Mail Popup 이동
	function goMail() {
		var nameList = ['${profile.userName}'];
		var emailList = ['${profile.mail}'];
		//iKEP.sendMailPop(nameList, emailList, "", "", "", "");	
		iKEP.sendTerraceMailPop(emailList, "", "");  
	}
	
	
	
	// Profile 소유자가 작성한 Document 
	function getMyDocuemnt() {
		$jq.ajax({
		    url : "<c:url value='/support/profile/getMyDocment.do'/>",
		    data : {'targetUserId':'${targetUserId}'}, 
		    type : "get",
		    success : function(result) {
		    	$jq("#mydocumentView").html(result);
		    	//parent.profileResize();
		    	iKEP.iFrameContentResize();
		    }
		});
	}
	
	// Profile 소유자가 작성한 Images
	function getMyImages() {
		$jq.ajax({
		    url : "<c:url value='/support/profile/getMyImages.do'/>",
		    data : {'targetUserId':'${targetUserId}'}, 
		    type : "get",
		    success : function(result) {
		    	$jq("#myimagesView").html(result);
		    	//parent.profileResize();
		    	iKEP.iFrameContentResize();
		    }
		});
	}
	
	// Profile 소유자가 작성한 Gallery Images
	getMyGallery = function() {
		$jq.ajax({
		    url : "<c:url value='/support/profile/getMyGallery.do'/>",
		    data : {'targetUserId':'${targetUserId}'}, 
		    type : "get",
		    success : function(result) {
		    	$jq("#myimagesView").html(result);
		    	//parent.profileResize();
		    	iKEP.iFrameContentResize();
		    }
		});
	};
	
	// Profile 소유자의 Schedule
	function getMySchedule() {
		$jq.ajax({
		    url : "<c:url value='/support/profile/getMySchedule.do'/>",
		    data : {'targetUserId':'${targetUserId}'}, 
		    type : "get",
		    success : function(result) {
		    	$jq(".pr_schedule").html(result);
		    	//parent.profileResize();
		    	iKEP.iFrameContentResize();
		    }
		});
	}
	
	// Profile 소유자의 Following,Follower, Expert Fellow
	function getFollowList() {
		$jq.ajax({
		    url : "<c:url value='/support/profile/getFollowList.do'/>",
		    data : {'targetUserId':'${targetUserId}'}, 
		    type : "get",
		    success : function(result) {
		    	$jq("#followphotoView").html(result);
		    	//parent.profileResize();
		    	iKEP.iFrameContentResize();
		    }
		});
	}
	
	// Profile 소유자가 작성한 Comment
	function getMyComment() {
		$jq.ajax({
		    url : "<c:url value='/support/profile/getMyComment.do'/>",
		    data : {'targetUserId':'${targetUserId}'}, 
		    type : "get",
		    success : function(result) {
		    	$jq("#mycommentView").html(result);
		    	//parent.profileResize();
		    	iKEP.iFrameContentResize();
		    }
		});
	}
	
	// Profile 소유자가 작성한 MyFiles
	function getMyFiles() {
		$jq.ajax({
		    url : "<c:url value='/support/profile/getMyFiles.do'/>",
		    data : {'targetUserId':'${targetUserId}'}, 
		    type : "get",
		    success : function(result) {
		    	$jq("#myfilesView").html(result);
		    	//parent.profileResize();
		    	iKEP.iFrameContentResize();
		    }
		});
	}
	
	
	<c:if test="${editAuthFlag != 'true' }">
	// 즐겨 찾기 추가 여부 
	function setProfileFavorite(data) {
		var str = "";
		if(data.status == 'exists'){
			str="<a class=\"ic_rt_favorite select\" onclick=\"deleteFavorite();\" href=\"#a\"><span></span></a>";
		}else if(data.status == 'success'){
			str="<a class=\"ic_rt_favorite\" onclick=\"addUserFavorite();\" href=\"#a\"><span></span></a>";
		}
		$jq("#favoriteBtnView").html(str);

	}
	
	// 즐겨 찾기 추가 여부 
	function displyFavorite(data) {
		if(data.status == 'success'){
			iKEP.chkFavorite('${targetUserId}', setProfileFavorite);
		}
	}
	
	function addUserFavorite(){
		iKEP.addFavorite('PEOPLE','${IKepConstant.ITEM_TYPE_CODE_PROFILE}','${profile.userId}','${profile.userName}',displyFavorite);
		
	}
	
	function deleteFavorite(){
		iKEP.delFavorite('','${targetUserId}', displyFavorite);
	}
	
	</c:if>
	    
function activity(){

	 $jq.ajax({    
			url : "<c:url value="/collpack/assess/getUserPivInfo.do"/>",     
			data : {userId:'${profile.userId}', portalId:'${profile.portalId}'},     
			type : "post",    
			//dataType : "json",  
			success : function(result) {    

				var data = $jq.parseJSON(result);
								

				var imgStep = Number(data.pviStep) + 1;
				if(imgStep < 10){
					imgStep = "0"+imgStep;
				}
				iKEP.createSWFEmbedObject("#pviStepSwf", "<c:url value='/base/images/activity/personal/step_"+imgStep+".swf'/>", 190, 194);

				$jq("#swf_layer tr:eq(0) td:eq(0)").text(data.friendshipScore);
				$jq("#swf_layer tr:eq(1) td:eq(0)").text(data.participationScore);
				$jq("#swf_layer tr:eq(2) td:eq(0)").text(data.contributionScore);
				$jq("#swf_layer tr:eq(3) td:eq(0)").text(data.applicationScore);
				$jq("#swf_layer tr:eq(4) td:eq(0)").text(data.leadershipScore);

				$jq("#swf_layer tr:eq(0) td:eq(1) img").width(data.friendshipScore/data.friendshipMax*100);
				$jq("#swf_layer tr:eq(1) td:eq(1) img").width(data.participationScore/data.participationMax*100);
				$jq("#swf_layer tr:eq(2) td:eq(1) img").width(data.contributionScore/data.contributionMax*100);
				$jq("#swf_layer tr:eq(3) td:eq(1) img").width(data.applicationScore/data.applicationMax*100);
				$jq("#swf_layer tr:eq(4) td:eq(1) img").width(data.leadershipScore/data.leadershipMax*100);
				
			},
			error : function(xhr, exMessage, httpStatus) {
				
				alert('error');
					
	        }

		}); 
}


</script>

<%-- 12.08.23 Francis Choi 무림 페이지를 탭 추가 --%>
<!--tab Start-->
<div class="iKEP_tab_menu_common">
	<ul>
		<li class="selected"><a href="#a" onclick="getProfile();"><c:choose>
					<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						<c:out value="${profile.userName}" />
					</c:when>
					<c:otherwise>
						<c:out value="${profile.userEnglishName}" />
					</c:otherwise>
				</c:choose> <ikep4j:message pre='${preProfileMain}' key='profile.owner' /> </a>
		</li>
	</ul>
	<ul>
		<li><a href="#a" onclick="goMyGallery();"><ikep4j:message
					pre='${preProfileMain}' key='profile.photo' /> </a>
		</li>
	</ul>
	<ul>
		<li><a href="#a" onclick="goGuestbook();"><ikep4j:message
					pre='${preProfileMain}' key='profile.guestbook' /> </a>
		</li>
	</ul>
</div>
<!--//tab End-->

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />


<h1 class="none">
	<ikep4j:message pre='${preProfileMain}' key='profile.contentsArea' />
</h1>
<!--pr_profile_top Start-->
<div class="pr_profile_top">
	<div class="corner_RoundBox09">
		<h2>Profile</h2>
		<!-- a class="button_pr" href="#a"><span><img src="../../images/icon/ic_plus.gif" alt="" />Follow</span></a -->
		<div class="pr_bg_profile">
			<div id="profileStatusView" class="profile">
				<div>
					<span>${profile.profileStatus}</span>
					<c:if test="${editAuthFlag == 'true' }">
						<a href="#a" id="pfst_edit_btn"><img
							src="<c:url value='/base/images/icon/ic_edit.gif'/>"
							alt="<ikep4j:message pre='${preButton}' key='update'/>" /> </a>
					</c:if>
				</div>
			</div>
		</div>
		<div class="clear"></div>

		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
	</div>


</div>
<!--//pr_profile_top End-->
<!--corner_RoundBox07 Start-->
<div class="corner_RoundBox07 mb10">
	<!--pr_profile Start-->
	<div class="pr_profile">
		<!--pr_tl Start-->
		<div class="pr_tl" style="margin-right: 0px;">
			<div class="prPhoto">
				<img id="profilePictureImage"
					src="<c:url value='${profile.profilePicturePath}' />" width="170"
					height="170" alt="Profile Image"
					onerror="this.src='<c:url value='/base/images/common/photo_170x170.gif'/>'" />					
				<c:if test="${isAdmin || user.userId eq profile.userId}">
					<div class="prPhoto_edit">
						<a href="#a" id="pf_picture_edit"><img
							src="<c:url value='/base/images/common/btn_edit.png'/>"
							alt="<ikep4j:message pre='${preButton}' key='update'/>" /> </a>
					</div>
				</c:if>
				<!-- 본인이 로그인한 경우 -->

				<c:if test="${editAuthFlag == 'false' }">
					<div id="followBtn" class='prPhoto_follow'></div>
				</c:if>
				<ul>
					<li><a onclick="goMail();" href="#a"><img onload="goMessenger('${encryptedId}','HSME_1');" src="<c:url value='/base/images/icon/ic_email.gif' />" alt="mail" /></a></li>
					<li><a onclick="goSMS();" href="#a"><img src="<c:url value='/base/images/icon/ic_sms.gif' />" alt="sms" /></a></li>
					
					<%-- <li <c:if test="${user.userId eq profile.userId }"> style="display:none"</c:if> >
					  <a onclick="goMessenger('${encryptedId}','HSME_1');AtMessengerEnabler.TrackPopupMenu('${encryptedId}');" href="#a">
					   <img id="mStatusIcon" title="오프라인" src="<c:url value='/base/images/icon/satom_off.bmp' />" alt="messenger"/>
					   </a>
					 </li>
					<object classid="clsid:28F3B6CD-AA28-4411-9C41-67B9448DAB4C" width="0" height="0" id="AtMessengerEnabler" codebase="<c:url value='/Bin/AtMessengerWebEnabler.cab'/>">
					     <param name='SERVER_IP' value='10.1.5.36'>
					     <param name='SERVER_PORT' value='2234'>
					     <param name='MYID' value='${encryptedMyId}'>
					     <param name='USECHAT' value='Y'>
					     <param name='USEMESSAGE' value='Y'>
					     <param name='USEFILE' value='Y'>
					     <param name='MESSENGER_TITLE' value='MOORIMMessenger'>
					</object> --%>
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
								<c:out value="${profile.jobTitleName}" />
							</c:when>
							<c:otherwise>
								<c:out value="${profile.jobTitleEnglishName}" />
							</c:otherwise>
						</c:choose> </strong> <span><c:out value="${profile.userEnglishName}" /> </span><span>(
						<c:out value="${profile.hanziName}" /> )</span>

					<c:if test="${editAuthFlag != 'true' }">
						<span id="favoriteBtnView"></span>
					</c:if>
					<c:if test="${editAuthFlag == 'true' }">
						<a href="#a" id="pf_edit_btn"><img	src="<c:url value='/base/images/icon/ic_edit.gif' />" alt="<ikep4j:message pre='${preButton}' key='update'/>" /></a>
						<a href="#a" id="pf_save_btn"><img	src="<c:url value='/base/images/icon/ic_save.gif' />" alt="<ikep4j:message pre='${preButton}' key='save'/>" /></a>
						<a href="#a" id="pf_cancel_btn"><img src="<c:url value='/base/images/icon/ic_cancel.gif' />" alt="<ikep4j:message pre='${preButton}' key='cancel'/>" /></a>
					    <a href="#a" id="pf_passwd_btn" ><img src="<c:url value='/base/images/common/btn_changepassword.gif' />" alt="<ikep4j:message pre='${preButton}' key='changePassword'/>" /></a>
					</c:if>
				</div>

				<form name="profileMainForm" id="profileMainForm" action=""
					method="post" style="border: 1px solid white; padding: 0px;"
					onsubmit="return false">
					<div id="profileView"></div>
				</form>
				<!--  My Personal Info Strat-->
				<form name="myPsInfoForm" id="myPsInfoForm" action="" method="post"
					style="border: 1px solid white; padding: 1px;"
					onsubmit="return false">
					<div id="myPsInfoView"></div>
				</form>
				<!--//  My Personal Info End -->


			</div>
<!-- Reportline Start -->
        <div class="none" ><!-- class="pr_reportline"-->
          <table witdh="100%" >
          <colgroup>
          <col style="min-width:120px"/>
          <col style="width:100%" />
          </colgroup>  
          <tr><td colspan=2 witdh="100%"><div class="more"></div></td></tr>   
          <tr>
	          <td class="title">
	               <h3><ikep4j:message pre='${preProfileMain}' key='reportline.title' /></h3>
	          </td>
          <td>
               <c:if test="${reportLineList != null}">
                   <span>                       
                        <img src="<c:url value='/base/images/icon/ic_person.gif' />" alt="" />
                            <c:choose>
                                <c:when test="${user.localeCode == portal.defaultLocaleCode}">
                                    <c:out value="${profile.userName}" />
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${profile.userEnglishName}" />
                                </c:otherwise>
                            </c:choose> <c:choose>
                                <c:when test="${user.localeCode == portal.defaultLocaleCode}">
                                    <c:out value="${profile.jobTitleName}" />
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${profile.jobTitleEnglishName}" />
                                </c:otherwise>
                            </c:choose>                       
                     </span>
                    <c:forEach var="reportLine" items="${reportLineList}" varStatus="status">
                        <c:if test="${status.index == '0' and !(reportLine.leaderId eq profile.userId) && !empty(reportLine.leaderId) }">
                            <span> 
                                  <img  src="<c:url value='/base/images/icon/ic_arrow_2.gif' />" alt="" />
                                   <a href="#a" onclick="goOtherProfile('${reportLine.leaderId}');">                                      
                                        <c:choose>
                                            <c:when test="${user.localeCode == portal.defaultLocaleCode}">
                                                <c:out value="${reportLine.leaderName}" />
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${reportLine.leaderEnglishName}" />
                                            </c:otherwise>
                                        </c:choose> 
                                        <c:choose>
                                            <c:when test="${user.localeCode == portal.defaultLocaleCode}">
                                                <c:out value="${reportLine.leaderJobTitle}" />
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${reportLine.leaderEnglishJobTitle}" />
                                            </c:otherwise>
                                        </c:choose> 
                                     </a>                                      
                                </span>
                        </c:if>
                        <c:if test="${ status.index != '0' and !(reportLine.leaderId eq profile.userId) && !empty(reportLine.leaderId) }">
                               <span>
                                   <img  src="<c:url value='/base/images/icon/ic_arrow_2.gif' />" alt="" />
                                   <a href="#a" onclick="goOtherProfile('${reportLine.leaderId}');">                                      
                                        <c:choose>
                                            <c:when test="${user.localeCode == portal.defaultLocaleCode}">
                                                <c:out value="${reportLine.leaderName}" />
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${reportLine.leaderEnglishName}" />
                                            </c:otherwise>
                                        </c:choose> 
                                        <c:choose>
                                            <c:when test="${user.localeCode == portal.defaultLocaleCode}">
                                                <c:out value="${reportLine.leaderJobTitle}" />
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${reportLine.leaderEnglishJobTitle}" />
                                            </c:otherwise>
                                        </c:choose>
                                     </a>                                      
                                </span>
                        </c:if>
                    </c:forEach>                    
                       
                    </c:if>            
          </td>
          </tr>
          </table>        
        </div>
        <!-- Reportline End -->
		</div>

	</div>
	<!--//pr_tl End-->
	<div class="pr_tr" style="top:30px:width">
		<a href="#a" onmouseover="MM_showHideLayers('swf_layer','','show')"
			onmouseout="MM_showHideLayers('swf_layer','','hide')"
			onfocus="MM_showHideLayers('swf_layer','','show')"
			onblur="MM_showHideLayers('swf_layer','','hide')">
			<div id="pviStepSwf"></div> </a>
		<div class="swf_index">
		<span class="textLeft"></span>
		<span class="colorPoint"></span>
		</div>
	</div>

</div>

<!--//pr_profile End-->
<div class="bg_shadow_l"></div>
<div class="bg_shadow_c"></div>
<div class="bg_shadow_r"></div>
<div class="l_b_corner"></div>
<div class="r_b_corner"></div>
</div>
<!--//corner_RoundBox07 End-->
<!--pr_sub Start-->
<div class="pr_sub">
	<!--pr_bl Start-->
	<div class="pr_bl">
	    <div class="pr_schedule"></div>		
	    
		<div class="pr_specialization">
			<h3>
				<ikep4j:message pre='${preProfileMain}' key='experts.title' />
			</h3><div class="more"></div>
			<form name="exfdForm" id="exfdForm" action="" method="post"
				style="border: 1px solid white; padding: 0px;"
				onsubmit="return false">
				<input name="userId" value="<c:out value="${profile.userId}"/>"
					type="hidden" /> <input name="updaterId"
					value="<c:out value="${profile.userId}"/>" type="hidden" /> <input
					name="updaterName" value="<c:out value="${profile.userName}"/>"
					type="hidden" />
				<div class="pr_specialization_p">
					<span id="expertFieldView"><c:out
							value="${profile.expertField}" /> </span>
					<c:if test="${editAuthFlag == 'true' }">
						<a href="#a" id="exfd_edit_btn"><img
							src="<c:url value='/base/images/icon/ic_edit.gif' />"
							alt="<ikep4j:message pre='${preButton}' key='update'/>" /> </a>
					</c:if>
				</div>
			</form>
			<div id="expertTagView"></div>
		</div>
		<!--div id="careerView"></div-->

		<div id="mydocumentView"></div>

		<div class="pr_images" id="myimagesView"></div>

		<div id="guestbookView"></div>
   </div>
	<!--//pr_bl End-->
	
</div>
<!--//pr_sub End-->
<!--pr_br Start-->
    <div class="pr_br">  
        <!-- div class="pr_mylist">
            <div id="mycommentView"></div>
            <div id="myfilesView"></div>
        </div-->
        <%-- 12.08.23 Francis Choi 무림 블로그 버튼 주석처리 --%>
        <%--div class="pr_gotoblog">
                <h3 class="none"><ikep4j:message pre='${preProfileMain}' key='directLink.title'/></h3>
                <ul>
                    <li><img src="<c:url value='/base/images/icon/ic_socialblog.gif' />" alt="<ikep4j:message pre='${preProfileMain}' key='directLink.socialBlog'/>" /><a onclick="goSocialblog()" href="#a"><ikep4j:message pre='${preProfileMain}' key='directLink.socialBlog'/></a></li>
                    <li><img src="<c:url value='/base/images/icon/ic_microblog.gif' />" alt="<ikep4j:message pre='${preProfileMain}' key='directLink.microBlog'/>" /><a onclick="goMicroblog()" href="#a"><ikep4j:message pre='${preProfileMain}' key='directLink.microBlog'/></a></li>
                </ul>
            </div--%>

        <!--pr_tr Start-->
        <div
            style="position: relative; text-align: center; margin: 5px 10px 15px;">


            <div id="swf_layer" style="left: -60px">
                <table summary="Activity Index">
                    <caption></caption>
                    <tbody>
                        <tr>
                            <th scope="row"><ikep4j:message pre='${preProfileMain}'
                                    key='friendship' />
                            </th>
                            <td align="right"></td>
                            <td align="left"><div class="swf_graph">
                                    <img
                                        src="<c:url value='/base/images/common/bar_color_01.gif' />"
                                        style="width: 100%;" alt="" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><ikep4j:message pre='${preProfileMain}'
                                    key='participation' />
                            </th>
                            <td align="right"></td>
                            <td align="left"><div class="swf_graph">
                                    <img
                                        src="<c:url value='/base/images/common/bar_color_02.gif' />"
                                        style="width: 50%;" alt="" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><ikep4j:message pre='${preProfileMain}'
                                    key='contribution' />
                            </th>
                            <td align="right"></td>
                            <td align="left"><div class="swf_graph">
                                    <img
                                        src="<c:url value='/base/images/common/bar_color_03.gif' />"
                                        style="width: 20%;" alt="" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><ikep4j:message pre='${preProfileMain}'
                                    key='application' />
                            </th>
                            <td align="right"></td>
                            <td align="left"><div class="swf_graph">
                                    <img
                                        src="<c:url value='/base/images/common/bar_color_04.gif' />"
                                        style="width: 80%;" alt="" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><ikep4j:message pre='${preProfileMain}'
                                    key='leadership' />
                            </th>
                            <td align="right"></td>
                            <td align="left"><div class="swf_graph">
                                    <img
                                        src="<c:url value='/base/images/common/bar_color_04.gif' />"
                                        style="width: 80%;" alt="" />
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <!--//pr_tr End-->
    </div>
    <!--//pr_br End-->



<div class="clear"></div>
<div class="clear"></div>
<div class="clear"></div>
<div class="clear"></div>
<div class="clear"></div>

