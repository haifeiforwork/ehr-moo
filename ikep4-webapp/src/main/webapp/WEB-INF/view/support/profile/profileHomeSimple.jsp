<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" />
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<c:set var="preMessage"  value="message.support.addressbook.popup" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<%
	String sFacebookAuthUrl = com.lgcns.ikep4.support.externalSNS.base.Constant.REDIRECT_URL;
%>

<script type="text/javascript">
<!--
	var $groupTree;
	var validator;
	
	var fileUploadForProfile,
		setProfileFavorite, displyFavorite, addUserFavorite, deleteFavorite,
		addFollowBtn, addFollowing, delFollowBtn, delFollowing;

	$jq(document).ready(function() {
		
		//iKEP.goProfilePopupMain('user39');
		
		
		// 화면 로딩시 각각 페이지 호출 시작
		getProfile();
		
		$jq('#viewMore').show(); //  addnew
		$jq('#myPsInfo_edit_btn').hide();
		$jq('#myPsInfo_save_btn').hide();
		$jq('#myPsInfo_cancel_btn').hide();
		
		getGuestbookList('1');
		
		if(parent.createGroupTree) {
			parent.createGroupTree('${targetUserId}');
			//parent.reopenUserTree('${targetUserId}');
		}

		var $fncIcons = $jq("#fncIcons").show()
							.find("a").hide();	
			$fncIcons.filter("[title=mail]").show()
				.click(function() {	// Mail Popup 이동
					var nameList = ['${profile.userName}'];
					var emailList = ['${profile.mail}'];
					iKEP.sendMailPop(nameList, emailList, "", "", "", "");
				}).attr("href", "#a");	
		
		// 타인의 프로파일에 들어 왔을때만 Following 버튼 처리 및 Favorite 추가
		<c:if test="${editAuthFlag != 'true' }">
			// 즐겨 찾기 추가 여부
			setProfileFavorite = function(data) {
				var str = "";
				if(data.status == 'exists'){
					str="<a class=\"ic_rt_favorite select\" onclick=\"deleteFavorite();\" href=\"#a\"><span></span></a>";
				}else if(data.status == 'success'){
					str="<a class=\"ic_rt_favorite\" onclick=\"addUserFavorite();\" href=\"#a\"><span></span></a>";
				}
				$jq("#favoriteBtnView").html(str);
			};
			
			// 즐겨 찾기 추가 여부 
			displyFavorite = function(data) {
				if(data.status == 'success'){
					iKEP.chkFavorite('${targetUserId}', setProfileFavorite);
				}
			};
			
			addUserFavorite = function(){
				iKEP.addFavorite('PEOPLE','${IKepConstant.ITEM_TYPE_CODE_PROFILE}','${profile.userId}','${profile.userName}',displyFavorite);
				reloadFullScreen("/portal/main/listUserMain.do?rightFrameUrl="+encodeURIComponent("/support/profile/getProfile.do?targetUserId=${profile.userId}"));
			};
			
			deleteFavorite = function(){
				iKEP.delFavorite('','${targetUserId}', displyFavorite);
				reloadFullScreen("/portal/main/listUserMain.do?rightFrameUrl="+encodeURIComponent("/support/profile/getProfile.do?targetUserId=${profile.userId}"));
			};
			
			// Favorite 유무 확인
			iKEP.chkFavorite('${targetUserId}', setProfileFavorite);
			
			<c:if test="${CommonConstant.PACKAGE_VERSION eq CommonConstant.IKEP_VERSION_FULL}">
				addFollowBtn = function() {	// Following 추가 버튼 생성
					var str = "<a class='follow' href=\"javascript:addFollowing()\">";
					str = str + "<span>Follow</span>";
					str = str + "</a>";
					$jq("#followBtn").html(str);
				};
				
				addFollowing = function() {	// Profile 소유자가 작성한 MyFiles
					$jq.ajax({
					    url : "<c:url value='/socialpack/microblogging/follow/createFollow.do'/>",
					    data : {'followingUserId':'${targetUserId}'},
					    type : "get",
					    success : function(result) { delFollowBtn(); }
					});
				};
				
				delFollowBtn = function() {	// Following 삭제 버튼 생성
					$jq("#followBtn").html("<a class='following' href=\"javascript:delFollowing()\"><span>Following</span></a>");
				};
				
				delFollowing = function() {	// Profile 소유자가 작성한 MyFiles
					$jq.ajax({
					    url : "<c:url value='/socialpack/microblogging/follow/removeFollow.do'/>",
					    data : {'followingUserId':'${targetUserId}'},
					    type : "get",
					    success : function(result) { addFollowBtn(); }
					});
				};
				
				// Following 유무 확인 시작
				$jq.ajax({
				    url : "<c:url value='/socialpack/microblogging/follow/isExists.do'/>",
				    data : {'followingUserId':'${targetUserId}'},
				    type : "get",
				    success : function(result) {
						if( result == 'true' ){
							delFollowBtn();
						}else{
							addFollowBtn();
						}
				    }
				});
			</c:if>
		</c:if>
		
		// 자기 프로파일에 들어 왔을때문 수정 가능하게 이벤트 처리 
		<c:if test="${editAuthFlag == 'true'}">
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
			
			
			$jq('#pf_edit_btn').click(function(){ editProfile(); });
			$jq('#myPsInfo_edit_btn').click(function(){ editMyPsInfo(); });
			$jq('#myPsInfo_cancel_btn').click(function(){ getMyPsInfo('cancel'); });
			$jq('#picture_edit').click(function(){
				fileUploadForProfile('${profile.userId}','picture', $jq("#pictureImage").attr("src"), afterFileUpload);
			});
			$jq('#pf_picture_edit').click(function(){
				fileUploadForProfile('${profile.userId}','pf_picture', $jq("#profilePictureImage").attr("src"), afterFileUpload);
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

			//프로파일 이미지 업로드 팝업
			fileUploadForProfile = function(userId, targetId, imgSrc, callback) {
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
		</c:if>
		
		$jq('#myPsInfo_save_btn').click(function(){ $jq('#myPsInfoForm').trigger("submit"); });
		$jq('#pf_save_btn').click(function(){ $jq("#profileMainForm").trigger("submit"); });
		$jq('#pf_cancel_btn').click(function(){ getProfile(); });
	});
	
	$jq.template("addrBookItemUser", '<option value="\${id}">\${name}/\${jobPosition}/\${teamName}</option>');
	$jq.template("addrBookItemGroup", '<option value="\${code}">\${name}</option>');
	
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
		parent.document.location.href = "<c:url value='/lightpack/planner/calendar/init.do?action=onClickTargetUser&id=${targetUserId}'/>";
	}
//-->
</script>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<h1 class="none"><ikep4j:message pre='${preProfileMain}' key='profile.contentsArea'/></h1>
				
<!--pr_profile_top Start-->
<div class="pr_profile_top">
	<div class="corner_RoundBox09">		
		<h2>Profile</h2>
		<!-- a class="button_pr" href="#a"><span><img src="../../images/icon/ic_plus.gif" alt="" />Follow</span></a -->
		<div class="pr_bg_profile" >
			<div id="profileStatusView" class="profile" >
				<div>
					<span>${profile.profileStatus}</span>
					<c:if test="${editAuthFlag == 'true' }">
						<a href="#a" id="pfst_edit_btn"><img src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="<ikep4j:message pre='${preButton}' key='update'/>" /></a>
					</c:if>
				</div>
			</div>
		</div>
		<div class="clear"></div>
		
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
	</div>	

	<!--corner_RoundBox07 Start-->
	<div class="corner_RoundBox07 mb10">
						
		<!--pr_profile Start-->
		<div class="pr_profile">			
			<!--pr_tl Start-->
			<div class="pr_tl" style="margin-right:0px;">		
				<div class="prPhoto">
					<img id="profilePictureImage" src="<c:url value='/'/><c:out value='${profile.profilePicturePath}'/>" width="170" height="170" alt="Profile Image" onerror="this.src='<c:url value='/base/images/common/photo_170x170.gif'/>'"/>
					
					<c:if test="${editAuthFlag == 'true' }"><div class="prPhoto_edit"><a href="#a"  id="pf_picture_edit"><img src="<c:url value='/base/images/common/btn_edit.png'/>" alt="<ikep4j:message pre='${preButton}' key='update'/>" /></a></div></c:if> <!-- 본인이 로그인한 경우 -->
					
					<c:if test="${editAuthFlag == 'false' }"><div id="followBtn" class='prPhoto_follow'></div></c:if>
					
					<ul id="fncIcons" style="display:none;">					 
						<li><a title="mail"><img src="<c:url value='/base/images/icon/ic_email.gif' />" alt="mail" /></a></li>
					</ul>
				</div>	
				<div class="prInfo">
					<div class="prInfo_name"><c:if test="${leaderYnFlag == 'true' }"><img src="<c:url value='/base/images/icon/people_t.png' />" alt="<ikep4j:message pre='${preProfileMain}' key='leader.icon'/>" /></c:if> <strong><c:out value="${profile.userName}"/> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${profile.jobTitleName}"/></c:when><c:otherwise><c:out value="${profile.jobTitleEnglishName}"/></c:otherwise></c:choose></strong> <span><c:out value="${profile.userEnglishName}"/></span>
					
						<c:if test="${editAuthFlag != 'true' }">
							<span id="favoriteBtnView"></span> 
						</c:if>
										
						<c:if test="${editAuthFlag == 'true' }">
							<a href="#a" id="pf_edit_btn" ><img src="<c:url value='/base/images/icon/ic_edit.gif' />" alt="<ikep4j:message pre='${preButton}' key='update'/>" /></a>
							<a href="#a" id="pf_save_btn" ><img src="<c:url value='/base/images/icon/ic_save.gif' />" alt="<ikep4j:message pre='${preButton}' key='save'/>" /></a>
							<a href="#a" id="pf_cancel_btn" ><img src="<c:url value='/base/images/icon/ic_cancel.gif' />" alt="<ikep4j:message pre='${preButton}' key='cancel'/>" /></a>
						</c:if>
					</div>
					
					<form name="profileMainForm" id="profileMainForm" action="" method="post" style="border: 1px solid white; padding: 0px;" onsubmit="return false">
					<div id="profileView"></div>
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
<!--//pr_profile_top End-->
 
	<!--  My Personal Info Strat-->
		<c:if test="${editAuthFlag == 'true' }">
			<div class="pr_career">
				<h3>
					<ikep4j:message pre='${preProfileMain}' key='myPsInfo.title'/>
					<a href="#a" id="myPsInfo_edit_btn" ><img src="<c:url value='/base/images/icon/ic_edit.gif' />" alt="<ikep4j:message pre='${preButton}' key='update'/>" /></a>
					<a href="#a" id="myPsInfo_save_btn" ><img src="<c:url value='/base/images/icon/ic_save.gif' />" alt="<ikep4j:message pre='${preButton}' key='save'/>" /></a>
					<a href="#a" id="myPsInfo_cancel_btn" ><img src="<c:url value='/base/images/icon/ic_cancel.gif' />" alt="<ikep4j:message pre='${preButton}' key='cancel'/>" /></a>
				</h3>
				<div class="more"><a href="#a" id="myPsInfo_more_btn"><img style="margin-bottom:-6px" id="MyPsInfoMoreImg" src="<c:url value='/base/images/common/tree_p.gif' />" alt="more" /></a></div>
				<div id="viewMore">
					<form name="myPsInfoForm" id="myPsInfoForm" action="" method="post" style="border: 1px solid white; padding: 0px;" onsubmit="return false">
					<div id="myPsInfoView" class="blockDetail"></div>
					</form>
				</div>
			</div>
		</c:if>
		<!--//  My Personal Info End -->				
	
<!--pr_sub Start-->
<div class="pr_sub">
	
		<!--pr_bl Start-->
		<div class="pr_bl" style="margin-right:0px;">

			<div id="guestbookView"></div>	

		</div>
		<!--//pr_bl End-->
</div>
<!--//pr_sub End-->

<div class="clear"></div>
<div class="clear"></div>
