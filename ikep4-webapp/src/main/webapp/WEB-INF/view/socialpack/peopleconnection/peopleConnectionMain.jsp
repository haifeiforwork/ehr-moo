<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="sessionPortal" value="${sessionScope['ikep.portal']}" />
<c:set var="sessionUser" value="${sessionScope['ikep.user']}" />

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preButton"  value="ui.socialpack.microblogging.button" />
<c:set var="preLabel" 	value="ui.socialpack.peopleconnection.label" />
<c:set var="preMBLabel" value="ui.socialpack.microblogging.label" />
<c:set var="preMessage"	value="ui.socialpack.peopleconnection.message" />
<c:set var="preNotice"	value="Notice.Mblog" /> 
<c:set var="preList"    value="ui.support.activitystream.list" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/units/support/favorite/favorite.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/socialpack/microblogging.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/socialpack/socialblog.js"/>"></script>
<script type="text/javascript">
//<!--   

(function($) {
	$(document).ready(function() {

		var str_ul = "<ul></ul>";	
		$("#divSuggest").html(str_ul);
		$("#myImageContent").html(str_ul);
		$("#myFileContent").html(str_ul);
		
		SocialBlog.getBlogintroductionView('${targetUserId}','view');
		
		$("#divTab1").tabs();

		// tweet 길이 체크
		iKEP.checkLength("#contents", updateCheck);
		
        // 이미지 업로드 버튼 이벤트 바인딩
		$jq('#imageupload').click(function(event) {
			//alert("imageupload");
			//파일업로드 팝업창
			iKEP.fileUpload(event.target.id,'0','1');
		});

        // 동영상 업로드 버튼 이벤트 바인딩
		$jq('#fileupload').click(function(event) {
			//alert("fileupload");
			//파일업로드 팝업창
			iKEP.fileUpload(event.target.id,'0','0');
		});
        
        // 투표 버튼 이벤트 바인딩
		$jq('#pollAdd').click(function(event) {
			var url = "<c:url value='/socialpack/microblogging/pollForm.do' />";			
			iKEP.popupOpen(url, {width:600, height:400, callback:setPollId});
		});

        // 팀 맨션 버튼 이벤트 바인딩
		$jq('#teamMention').click(function(event) {
            //파라미터(콜백함수, 전송파라미터, 팝업옵션)
			iKEP.showAddressBook(setTeamMention, "", {selectType:"group"});
		});

        // 트윗쓰기 버튼 이벤트 바인딩
		$jq('#mblogWrite').click(function(event) {
			//form을 submit하려고 시도한다.
			$jq('form[name=mblogForm]').submit();
		});

        // 로그인 사용자의 왼쪽 고정 그룹 Div 보기
        $jq('#loginUserGroupViewL').click(function(event){
        	event.stopPropagation();
        	viewGroupDiv(event);
        });

    	// divSuggest 부분이 아닌 곳을 선택했을 때 감춰준다.
    	$("#divSuggest").click(function(event) {event.stopPropagation();});
        if(parent) $(parent.document.body).click(function() { divHide("divSuggest"); });
        $(document.body).click(function() { divHide("divSuggest"); });
        
        
    	// 로그인한 사용자의 group list Div 보여주기
    	viewGroupDiv = function(event){

    	    var $div = $("#divSuggest");
    	    		
        	$div.hide();
    		$div.css("left", $jq(event.target).position().left );
    		$div.css("top", $jq(event.target).position().top + 20);

    	    getGroupList('${sessionUser.userId }',"divSuggest", '4');
    	}

        
		$("#myImageImg").click(function(event) {
			myAddonImageList();
		});
		
		$("#myFileImg").click(function(event) {		
			myAddonFileList();
		});

		$("#myImageClose").click(function(event) {
			hideInsertMyFileDiv();
		});
		
		$("#myFileClose").click(function(event) {
			hideInsertMyFileDiv();
		});

		// 정합성체크 옵션
		var validOptions = {
			rules : {
				contents : {
					required : false,
					maxlength : 140
				}
			},
			messages : {
				contents : { 
		    		direction : "bottom",
		        	required  : "<ikep4j:message pre='${preNotice}' key='contents' />",
		        	maxlength : "<ikep4j:message pre='${preNotice}' key='contents.max' />"
		    	}
			},
			submitHandler : function(form) {
				
				//alert("privatehome submitHandler");
				if(checkValid())
				{
					//alert('submit!!');
					$.ajax({
						url : "<c:url value='/socialpack/microblogging/createMblog.do'/>",
						type : "post",
						data : $(form).originalSerialize(),
						success : function(data) {
							//alert(data);
							
							if("ok" == data){
								// 실제 form 객체를 reset한다.
								$jq('form[name=mblogForm]')[0].reset();
								updateCheck(0);
								
								reloadAllUpdates();
							}
							else if("TWITTER_ERROR" == data)
							{
								if(confirm("<ikep4j:message pre='${preMessage}' key='retryTwitterAuth' />"))
								{
									authTwitterPopup();
								}
							}
							else if("FACEBOOK_ERROR" == data)
							{
								if(confirm("<ikep4j:message pre='${preMessage}' key='retryFacebookAuth' />"))
								{
									authFacebookPopup();
								}
							}
						},
						error : function(xhr, exMessage) {
							//alert("fail"); 
							var errorItems = $.parseJSON(xhr.responseText).exception;
							validator.showErrors(errorItems);
						}
					});
				}
			}
		};
		
		// form이 submit 될 때과 정합성옵션 연결된다.
		var validator = new iKEP.Validator("#mblogForm", validOptions);

		
		//************ ActivityStream 관련 스크립트 S
		getList();
		
		$jq('#searchWord').keypress(function(event) { 
			if(event.which == '13') {
				getListAndClear();
			}
		}); 
		//************ ActivityStream 관련 스크립트 E
		
	});

	//twit 관련 정합성체크
	checkValid = function(){

		//alert($jq('input[name=twitter]:checkbox:checked').length);
		//alert($jq('input[name=facebook]:checkbox:checked').length);
		//alert($jq('#twitterAccount').val());
		//alert($jq('#twitterAuthCode').val());
		//alert($jq('#facebookAuthCode').val());
		// 트위터 계정이 없는 경우
		if(1 == $jq('input[name=twitter]:checkbox:checked').length && ("" == $jq('#twitterAccount').val() || "" == $jq('#twitterAuthCode').val()))
		{
			if(confirm("<ikep4j:message pre='${preMessage}' key='noTwitterAuth' />"))
			{
				authTwitterPopup();
			}
			return false;
		}

		// facebook 계정이 없는 경우
		if(1 == $jq('input[name=facebook]:checkbox:checked').length && "" == $jq('#facebookAuthCode').val())
		{
			if(confirm("<ikep4j:message pre='${preMessage}' key='noFacebookAuth' />"))
			{
				authFacebookPopup();
			}
			return false;
		}
		
		return true;
	};
	
	// Profile 페이지로 이동
	goProfile = function(targetUserId) {
		document.location.href = "<c:url value='/support/profile/getProfile.do?targetUserId='/>" + targetUserId  ;
	};

	// Microblogging Group Home 페이지로 이동
	goGroupHome = function(mbgroupId) {
		document.location.href = "<c:url value='/socialpack/microblogging/groupHome.do?mbgroupId='/>" + mbgroupId  ;
	};

	// Microblogging Private Home 페이지로 이동
	goPrivateHome = function(ownerId) {
		document.location.href = "<c:url value='/socialpack/microblogging/privateHome.do?ownerId='/>" + ownerId  ;
	};
	
	// favorite 페이지로 이동
	goFavorite = function() {
		document.location.href = "<c:url value='/support/favorite/getListForPeople.do'/>" ;
	};
	
	// Follower List 페이지로 이동
	goFollowList = function(type) {
		if( type == 'FOLLOWING' ){ //tabNum = tab7
			document.location.href = "<c:url value='/socialpack/microblogging/privateHome.do?tabNum=tab7&ownerId=${targetUserId}'/>";
		}else{ //, tabNum = tab8
			document.location.href = "<c:url value='/socialpack/microblogging/privateHome.do?tabNum=tab8&ownerId=${targetUserId}'/>";
		}
	};

	// Who 페이지로 이동
	goWho = function(profileId) {
		document.location.href = "<c:url value='/collpack/who/getWho.do?profileId='/>" + profileId  ;
	};
	
	//************ ActivityStream 관련 스크립트 S		
	getList = function(isScrollSet) {
		
		$jq("#moreDiv").hide();
		$jq("#emptyDiv").hide();
		
		var msg = '<ikep4j:message pre='${preMessage}' key='search.document' />';
		beforeSearch(msg);

		$jq.ajax({     
			url : '<c:url value="/support/activitystream/getSubListForActivityStream.do" />',     
			data :  $jq("#searchForm").serialize(),     
			type : "post",     
			success : function(result) {  
				$jq("#listDiv").append(result);
				if(isScrollSet) setScroll();
			}, 
			error : function(event, request, settings) { alert("error"); }
		});  
		
		afterSearch(msg);
	};
	
	getListAndClear = function() {
		
		$jq("#moreDiv").hide();
		$jq("#emptyDiv").hide();
		$jq("#pageIndex").val(1);
		
		$jq("#currentCount").val("0");
		
		var msg = '<ikep4j:message pre='${preMessage}' key='search.document' />';
		beforeSearch(msg);

		$jq.ajax({     
			url : '<c:url value="/support/activitystream/getSubListForActivityStream.do" />',     
			data :  $jq("#searchForm").serialize(),     
			type : "post",     
			loadingElement : {container:"#listDiv"}, 
			success : function(result) {  
				
				$jq("#listDiv").children().remove();
				$jq("#listDiv").append(result);
			},
			error : function(event, request, settings) { alert("error"); }
		});  
		
		afterSearch(msg);
	};
	
	setCount = function() {
		
		var recordCount = eval($jq("#recordCount").val());
		var currentCount = eval($jq("#currentCount").val());
		
		var countStr = '<ikep4j:message pre='${preList}' key='totalCount' /> ' + recordCount + '<ikep4j:message pre='${preList}' key='count' /> ';
		countStr = countStr + ' (<ikep4j:message pre='${preList}' key='nowCount' /> ' + currentCount + '<ikep4j:message pre='${preList}' key='count' /> '+")";
		$jq("#countDiv").html(countStr);
		
		setMoreDiv();
	}
	
	clickTab = function(obj) {
		
		$jq("#tabLiDiv").children().each(function(index, item) { 
			//$jq(item).removeClass();
		});
		
		$jq("#tabInputDiv").children().each(function(index, item) { 
			$jq(item).val("");
		})
		
		var index = $jq(obj).parent().index();
		
		//$jq(liObj).addClass("on");
		
		var liObj = $jq("#tabLiDiv").children().get(index);
		var inputObj = $jq("#tabInputDiv").children().get(index);
		
		if ($jq(inputObj).attr("id") == "isAll") {
			$jq("#tabInputDiv").children().each(function(index, item) { 
				$jq(item).val($jq(item).attr("id"));
			})
		}
		else {
			$jq(inputObj).val($jq(inputObj).attr("id"));
		}
		
		getListAndClear();
	}
	

	// 리스트 새로고침
	reloadAllUpdates = function(){

		// tap 갯수
		var tapSize = $jq("#tabInputDiv").children().size();
		var inputObj;
		
		for(i=0; i < tapSize; i++){
			inputObj = $jq("#tabInputDiv").children().get(i);
			$jq(inputObj).val($jq(inputObj).attr("id"));
		}

		getListAndClear();
	}
	
	//************ ActivityStream 관련 스크립트 E
	
})(jQuery);

//-->
</script>
<div id="snsAuthInfo">
	<input type="hidden" id="twitterAccount" 	value = "${sessionUser.twitterAccount}" />
	<input type="hidden" id="twitterAuthCode" 	value = "${sessionUser.twitterAuthCode}" />
	<input type="hidden" id="facebookAuthCode" 	value = "${sessionUser.facebookAuthCode}" />
</div>

<!--wrapper Start-->
<div id="wrapper">

	<!--blockContainer Start-->
	<div id="blockContainer">
		
		<!--blockMain Start-->
		<div id="blockMain">
					
			<!--mainContents Start-->
			<div id="mainContents" class="conPadding_2">	
				<h1 class="none">컨텐츠영역</h1>

				<!--blockLeft_svs Start-->
				<div class="blockLeft_svs">
					
					<!--corner_RoundBox07 Start-->
					<div class="corner_RoundBox07 padding10">	
						
						<h2 class="none">개인정보</h2>		
						<div class="prPhoto_2">
							<img id="mainPictureImage" src="<c:url value='${profile.profilePicturePath}' />" width="190" height="190" alt="Profile Image" onerror="this.src='<c:url value='/base/images/common/photo_170x170.gif'/>'" />
						</div>		
						<div class="borderFrame">
							<div class="borderFrame_p">
								<div><img src="<c:url value='/base/images/common/title_aboutme.gif' />" alt="about me" /></div>
								<div id="edit_introduction"></div>
								<div class="role"><ikep4j:message pre='${preLabel}' key='contact' /></div>
								<div><c:out value="${profile.officeBasicAddress}"/> &nbsp; <c:out value="${profile.officeDetailAddress}"/> <br/>
									<c:out value="${profile.timezoneName}"/>
								</div>
								<div class="phone"><img src="<c:url value='/base/images/icon/ic_phone1.gif' />" alt="" /> <c:out value="${profile.mobile}"/></div>
								<div class="mail"><img src="<c:url value='/base/images/icon/ic_mail_2.gif' />" alt="" /> <c:out value="${profile.mail}"/></div>
							</div>
							<div class="borderFrame_lt"></div>
							<div class="borderFrame_rt"></div>
							<div class="borderFrame_lb"></div>
							<div class="borderFrame_rb"></div>
						</div>
	
						<div class="People_t3">
							<h2><ikep4j:message pre='${preLabel}' key='favorite' /> <span class="num">(${portalFavoriteSize})</span></h2>
							<span class="more"><a href="#a" onclick="javascript:goFavorite();"><img class="valign_middle" src="<c:url value='/base/images/icon/ic_plus_s2.gif' />" alt="" /> <ikep4j:message pre='${preButton}' key='viewall' /></a></span>
						</div>	
						<div class="clear"></div>
						<div class="People_img_list">
							<c:if test="${not empty portalFavorite }">
								<ul>
									<c:forEach var="favorite" items="${portalFavorite}" varStatus="loopStatus" begin="0" end="5">
										<li><a href="#a" onclick="javascript:goPrivateHome('${favorite.targetId}');">
											<!-- //아이디에 해당하는 사진정보. -->
											<img src="<c:url value='${favorite.profilePicturePath}' />" width="25" height="25" title="${favorite.title}" alt="" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />
										</a></li>
									</c:forEach>						
								</ul>
							</c:if>
						</div>		

						<div class="People_t3">
							<h2><ikep4j:message pre='${preLabel}' key='followings' /> <span class="num">(${followingCount})</span></h2>
							<span class="more"><a onclick="goFollowList('FOLLOWING');"><img class="valign_middle" src="<c:url value='/base/images/icon/ic_plus_s2.gif' />" alt="" /> <ikep4j:message pre='${preButton}' key='viewall' /></a></span>
						</div>							
						<div class="clear"></div>
						<div class="People_img_list">
							<c:if test="${not empty followingList }">
								<ul>
									<c:forEach var="following" items="${followingList}" varStatus="loopStatus" begin="0" end="5">
										<li><a href="#a" onclick="javascript:goPrivateHome('${following.followingUserId}');">
											<!-- //아이디에 해당하는 사진정보. -->
											<img src="<c:url value='${following.profilePicturePath}' />" width="25" height="25" title="${follower.userId}" alt="" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />
										</a></li>
									</c:forEach>									
								</ul>
							</c:if>
						</div>	

						<div class="People_t3">
							<h2><ikep4j:message pre='${preLabel}' key='followers' /> <span class="num">(${followerCount})</span></h2>
							<span class="more"><a onclick="goFollowList('FOLLOWER');"><img class="valign_middle" src="<c:url value='/base/images/icon/ic_plus_s2.gif' />" alt="" /><ikep4j:message pre='${preButton}' key='viewall' /></a></span>
						</div>							
						<div class="clear"></div>
						<div class="People_img_list">
							<c:if test="${not empty followerList }">
								<ul>
									<c:forEach var="follower" items="${followerList}" varStatus="loopStatus" begin="0" end="5">
										<li><a href="#a" onclick="javascript:goPrivateHome('${follower.userId}');">
											<!-- //아이디에 해당하는 사진정보. -->
											<img src="<c:url value='${follower.profilePicturePath}' />" width="25" height="25" title="${follower.userId}" alt="" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />
										</a></li>
									</c:forEach>								
								</ul>
							</c:if>
						</div>								
							
						<div class="People_t3">
							<h2><ikep4j:message pre='${preLabel}' key='groups' /> <span class="num">(${mbGroupListSize})</span></h2>							
						</div>	
						<div class="clear"></div>
						<div class="People_img_list1">
							<c:if test="${not empty mbGroupList }">
								<ul>
									<c:forEach var="mbGroup" items="${mbGroupList}" varStatus="loopStatus" >
										<li>
											<div class="People_photo"><a href="#a" onclick="javascript:goGroupHome('${mbGroup.mbgroupId}');"><img src="<c:url value='/support/fileupload/downloadFile.do?fileId=${mbGroup.imagefileId}&amp;smallimageYn=Y' />" width="35" height="35" title="${mbGroup.mbgroupName}"  alt="" /></a></div>
											<p class="People_name"><a href="#a">${mbGroup.mbgroupName}</a></p>									
											<p class="People_team"><a href="#a">${mbGroup.memberCount} Members</a></p> 											
										</li>
									</c:forEach>														
								</ul>
							</c:if>
						</div>


						<div class="l_t_corner"></div>
						<div class="r_t_corner"></div>
						<div class="l_b_corner"></div>
						<div class="r_b_corner"></div>				
					</div>
					<!--//corner_RoundBox07 End-->				
					
				</div>
				<!--//blockLeft_svs End-->

				<!--blockCenter_svs Start-->
				<div class="blockCenter_svs">
					<div class="blockCenter_svs_con">
						<h2 class="none"></h2>
						
						<!--corner_RoundBox07 Start-->
						<div class="corner_RoundBox07" style="padding:0 !important;">						
							
							<!--microblog_write Start-->
							<div class="microblog_write">
								<div class="corner_RoundBox05 mb0">
									<h2><ikep4j:message pre='${preMBLabel}' key='title' /></h2>
									<div class="microblog_write_list"  id="addonDiv1" style="display:none;">
										<span><a href="#a" id="loginUserGroupViewL"><img src="<c:url value='/base/images/common/microblog_sel.gif'/>" alt="공유범위" /></a></span>
										<ul>
											<li><a href="#a" onclick="shortUrl();"><ikep4j:message pre='${preButton}' key='shorturl'  /></a></li>
											<li><a href="#a" id="imageupload"><ikep4j:message pre='${preButton}' key='imageupload'  /></a></li>
											<li><a href="#a" id="fileupload"><ikep4j:message pre='${preButton}' key='fileupload'  /></a></li>
											<li><a href="#a" id="pollAdd"><ikep4j:message pre='${preButton}' key='vote'  /></a></li>
											<li class="liLast"><a href="#a" id="teamMention"><ikep4j:message pre='${preButton}' key='teamMention'  /></a></li>
										</ul>
									</div>
									<form id="mblogForm" name="mblogForm" method="post" action="" >
										<input type ="hidden" name="mblogType" value = "0" />
										<input type ="hidden" name="mbgroupId" id="mbgroupId" />
										<div style="margin-right:11px;"><textarea id="contents" name="contents" title="<ikep4j:message pre='${preMBLabel}' key='contents'  />" cols=""  rows="" class="w100"></textarea></div>
										<!--autoComplete_layer Start-->
											<div id="divSuggest" class="none" style="position:absolute; z-index:100; background:white ; width:200px; height:200px; border:1px solid #999;">
											</div>
										<!--//microblog_layer End-->
										<div id="addonDiv2" style="display:none;">
											<div class="microblog_write_check">
												<span><label><input name="isRetweetAllowed" type="checkbox" title="Retweet 허용" class="checkbox" value="1" checked="checked" /> <img src="<c:url value='/base/images/icon/ic_retweet.gif'/>" alt="retweet" /><ikep4j:message pre='${preMBLabel}' key='isRetweetAllowed'  /></label></span>
												
												<span class="bar"><img src="<c:url value='/base/images/common/bar_gray_3.gif'/>" alt="" /></span>
												<span><label><input name="twitter" type="checkbox" title="twitter" class="checkbox" value="1" /> <img src="<c:url value='/base/images/icon/ic_twitter.gif'/>" alt="twitter" /></label></span>
												<span><label><input name="facebook" type="checkbox" title="facebook" class="checkbox" value="1" /> <img src="<c:url value='/base/images/icon/ic_facebook.gif'/>" alt="facebook" /></label></span>
												
												<span class="bar"><img src="<c:url value='/base/images/common/bar_gray_3.gif'/>" alt="" /></span>
												<span class="" id ="insertImg"><a href="#img" title="Insert Image"><img src="<c:url value='/base/images/icon/ic_img_add.png'/>" alt="" id="myImageImg" /></a></span>
												<span class="" id="insertFile"><a href="#file" title="Insert File"><img src="<c:url value='/base/images/icon/ic_file_add.png'/>" alt="" id="myFileImg" /></a></span>
											
												<div class="microblog_write_send">
													<span class="microblog_write_sendnum" id="textcount">140</span>
													<a class="button_blog" href="#a" id="mblogWrite"><span><ikep4j:message pre='${preButton}' key='save' /></span></a>
												</div>
											</div>
										</div>
									</form>	
								</div>
							</div>
							<!--//microblog_write End-->				
							<div class="clear"></div>
				
							<div class="l_t_corner"></div>
							<div class="r_t_corner"></div>
							<div class="l_b_corner"></div>
							<div class="r_b_corner"></div>		
							<div class="microblog_insertImg none">
								<div class="microblog_insertBox">
									<div class="microblog_insertBox_tit_img"><span><ikep4j:message pre='${preMBLabel}' key='image' /></span><a href="#a"><img src="<c:url value='/base/images/icon/ic_close_layer.gif'/>" alt="" id="myImageClose" /></a></div>
									<div id="myImageContent">
									</div>
								</div>
							</div>					
							<div class="microblog_insertFile none">
								<div class="microblog_insertBox">
									<div class="microblog_insertBox_tit_file"><span><ikep4j:message pre='${preMBLabel}' key='file' /></span><a href="#a"><img src="<c:url value='/base/images/icon/ic_close_layer.gif'/>" alt="" id="myFileClose" /></a></div>
									<div id="myFileContent">
									</div>
								</div>
							</div>									
						</div>
						<!--//corner_RoundBox07 End-->

						<!--corner_RoundBox07 Start-->
						<div class="corner_RoundBox07 padding10" style="min-height:510px;">	

							<form id="searchForm" method="post" action="" onsubmit="return false">  
							
							<spring:bind path="searchCondition.recordCount">
								<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
							</spring:bind>	
							
							<spring:bind path="searchCondition.currentCount">
								<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
							</spring:bind>	
							
							<spring:bind path="searchCondition.pageIndex">
								<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
							</spring:bind>		
							
							<div id="tabInputDiv">
								<input type="hidden" id="isAll" name="isAll"  value="isAll" />
								
								<c:forEach var="group" items="${groupList}">
									<input type="hidden" id="${group.groupId}" name="groupList"  value="${group.groupId}" />
								</c:forEach>				
							
								<input type="hidden" id="isFollowing" name="isFollowing"  value="isFollowing" />
								<input type="hidden" id="isFavorite" name="isFavorite"  value="isFavorite" />
								<input type="hidden" id="isMy" name="isMy"  value="isMy" />
							</div>	
									
							<div id="divTab1" class="iKEP_tab">
			
									<ul id="tabLiDiv">
										
										<li><a href="#tabs-1" onclick="clickTab(this)"><ikep4j:message pre='${preList}' key='all' /></a></li>
										
										<c:forEach var="group" items="${groupList}">
											<li><a href="#tabs-1" onclick="clickTab(this)">${group.groupName}</a></li>
										</c:forEach>	
									
										<li><a href="#tabs-1" onclick="clickTab(this)"><ikep4j:message pre='${preList}' key='following' /></a></li>
										<li><a href="#tabs-1" onclick="clickTab(this)"><ikep4j:message pre='${preList}' key='favorite' /></a></li>
										<li><a href="#tabs-1" onclick="clickTab(this)"><ikep4j:message pre='${preList}' key='my' /></a></li>
										
									
									</ul>
									
										
								
								
							<div class="tab_con">
							<div id="tabs-1">
							<!--expert_bl Start-->
							<div class="expert_bl block mb10">
			
								
							
									<ul id="listDiv">
										
											<li></li>
									
									</ul>
									
									
									
							</div>
							<!--//expert_bl End-->	
							</div>
							</div>
							</div>
							<div class="clear"></div>
							
							<!--blockButton_3 Start-->
							<div class="blockButton_3 mb0" onclick="getMore();"> 
								<a class="button_3" href="#a"><span><ikep4j:message pre='${preMessage}' key='list.more' /> <img src="<c:url value='/base/images/icon/ic_more_ar.gif' />" alt="" /></span></a>				
							</div>
				
							<div id="emptyDiv" class="blockButton_3" > 
								<a class="button_3" href="#a"><span><ikep4j:message pre='${preMessage}' key='list.empty' /> </span></a>				
							</div>
							<!--//blockButton_3 End-->						
							<!--//expert_bl End-->	
										
							<div class="l_t_corner"></div>
							<div class="r_t_corner"></div>
							<div class="l_b_corner"></div>
							<div class="r_b_corner"></div>	
							</form>			
						</div>
						<!--//corner_RoundBox07 End-->
																										
					</div>
				</div>
				<!--//blockCenter_svs End-->									

				<!--blockRight_svs Start-->	
				<div class="blockRight_svs">

					<!--corner_RoundBox07 Start-->
					<div class="corner_RoundBox07 padding10">

						<div class="People_t3">
							<h2><ikep4j:message pre='${preLabel}' key='socialGraph' /></h2>							
						</div>	
						<div class="clear"></div>
						<div class="socialranking"><img src="<c:url value='/base/images/icon/ic_ranking.gif' />" alt="" /> <ikep4j:message pre='${preLabel}' key='mySocialRanking' /> : <span class="colorPoint">${myRanking}</span></div>
						<div class="socialgraph">
							<div class="area_lt">
								<div class="area_lt_con">
									<ikep4j:message pre='${preLabel}' key='directConnection' />
									<c:if test="${not empty directList }">
										<ul>
											<c:forEach var="item" items="${directList}">
												<li><a onclick="goProfile('${item.userId}');" href="#a"><img src="<c:url value='${item.profilePicturePath}' />" width="25" height="25" alt="${item.userId}" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></li>
											</c:forEach>
										</ul>
									</c:if>
								</div>
							</div>
							<div class="area_rt">
								<div class="area_rt_con">
									<ikep4j:message pre='${preLabel}' key='associatedConnect' />
									<c:if test="${not empty communicationList }">
										<ul>
											<c:forEach var="item" items="${communicationList}">
												<li><a onclick="goProfile('${item.userId}');" href="#a"><img src="<c:url value='${item.profilePicturePath}' />" width="25" height="25" alt="${item.userId}" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></li>
											</c:forEach>
										</ul>
									</c:if>
								</div>
							</div>
							<div class="area_lb">
								<div class="area_lb_con">
									<ikep4j:message pre='${preLabel}' key='followExperts' />
									<c:if test="${not empty fellowshipList }">
										<ul>
											<c:forEach var="item" items="${fellowshipList}">
												<li><a onclick="goProfile('${item.userId}');" href="#a"><img src="<c:url value='${item.profilePicturePath}' />" width="25" height="25" alt="${item.userId}" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></li>
											</c:forEach>
										</ul>
									</c:if>
								</div>
							</div>	
							<div class="area_rb">
								<div class="area_rb_con">
									<ikep4j:message pre='${preLabel}' key='frequentContact' />
									<c:if test="${not empty expertiseList }">
										<ul>
											<c:forEach var="item" items="${expertiseList}">
												<li><a onclick="goProfile('${item.userId}');" href="#a"><img src="<c:url value='${item.profilePicturePath}' />" width="25" height="25" alt="${item.userId}" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></li>
											</c:forEach>
										</ul>
									</c:if>
								</div>
							</div>	
							<div class="area_center">
								<img src="<c:url value='${profile.profilePicturePath}' />" width="35" height="35" alt="Profile Image" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'"/>
								<div class="frame"><img src="<c:url value='/base/images/common/frame_socialgraph.png' />" alt="" /></div>
							</div>		
						</div>		
						
						<div class="People_t2 mt20">
							<h2><ikep4j:message pre='${preLabel}' key='fellowExpert' /> <a href="#a"><img class="valign_middle" src="<c:url value='/base/images/icon/ic_alliance_q.png' />" alt="" title="<ikep4j:message pre='${preLabel}' key='fellowExpertDesc' />" /></a></h2>
						</div>	
						<div class="clear"></div>
						<div class="People_img_list mt10">
							<c:choose>
							<c:when test="${empty fellowExpertList}">
								<p align="center"><ikep4j:message pre='${preMessage}' key='list.empty'/></p>
							</c:when>
							<c:otherwise>
								<c:if test="${not empty fellowExpertList }">
									<ul>
									<c:forEach var="fellowExpert" items="${fellowExpertList}" varStatus="status">
									<c:if test="${status.index < 5 }" >
										<li><a onclick="goProfile('${fellowExpert.expertUserId}');" href="#a"><img src="<c:url value='${fellowExpert.profilePicturePath}' />" width="25" height="25" alt="${fellowExpert.expertUserId}" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></li>
									</c:if>
									</c:forEach>
									</ul>
									<ul>
									<c:forEach var="fellowExpert" items="${fellowExpertList}" varStatus="status">
									<c:if test="${status.index >= 5 and status.index < 10}" >
										<li><a onclick="goProfile('${fellowExpert.expertUserId}');" href="#a"><img src="<c:url value='${fellowExpert.profilePicturePath}' />" width="25" height="25" alt="${fellowExpert.expertUserId}" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></li>
									</c:if>
									</c:forEach>
									</ul>
								</c:if>
							</c:otherwise>
							</c:choose>			
						</div>		
						
						<div class="People_t2 mt20">
							<h2><ikep4j:message pre='${preLabel}' key='fellowRecommend' /> <a href="#a"><img class="valign_middle" src="<c:url value='/base/images/icon/ic_alliance_q.png' />" alt="" title="<ikep4j:message pre='${preLabel}' key='fellowRecommendDesc' />" /></a></h2>
						</div>			
						<div class="clear"></div>
						<div class="People_img_list1 mt10">
							<c:choose>
							<c:when test="${empty recommendFellowList}">
								<p align="center"><ikep4j:message pre='${preMessage}' key='list.empty'/></p>
							</c:when>
							<c:otherwise>
							<c:if test="${not empty recommendFellowList }">
								<ul>
									<c:forEach var="fellow" items="${recommendFellowList}" varStatus="status">
										<li>
											<div class="People_photo"><a onclick="goProfile('${fellow.userId}');" href="#a"><img src="<c:url value='${fellow.profilePicturePath}' />" width="25" height="25" alt="${fellow.userId}" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></div>
											<p class="People_name"><c:choose><c:when test="${sessionUser.localeCode == sessionPortal.defaultLocaleCode}">${fellow.userName} ${fellow.jobTitleName}</c:when><c:otherwise>${fellow.userEnglishName} ${fellow.jobTitleEnglishName}</strong></c:otherwise></c:choose></p>										
											<p class="People_team"><c:choose><c:when test="${sessionUser.localeCode == sessionPortal.defaultLocaleCode}">${fellow.teamName}</c:when><c:otherwise>${fellow.teamEnglishName}</strong></c:otherwise></c:choose></p> 
										</li>		
									</c:forEach>									
								</ul>
							</c:if>
							</c:otherwise>
							</c:choose>
						</div>		
						
						<div class="People_t2 mt20">
							<h2><ikep4j:message pre='${preLabel}' key='externalExpertRecommend' /> <a href="#a"><img class="valign_middle" src="<c:url value='/base/images/icon/ic_alliance_q.png' />" alt="" title="<ikep4j:message pre='${preLabel}' key='externalExpertRecommendDesc' />" /></a></h2>
						</div>			
						<div class="clear"></div>
						<div class="People_img_list1 mt10">
							<c:choose>
							<c:when test="${empty recommendExternalExpertList}">
								<p align="center"><ikep4j:message pre='${preMessage}' key='list.empty'/></p>
							</c:when>
							<c:otherwise>
							<c:if test="${not empty recommendExternalExpertList }">
								<ul>
									<c:forEach var="externalExpert" items="${recommendExternalExpertList}" varStatus="status">
										<li>
											<div class="People_photo"><a onclick="goWho('${externalExpert.profileId}');" href="#a"><img src="<c:url value='/support/fileupload/downloadFile.do?fileId=${externalExpert.fileId}&amp;thumbnailYn=Y' />" width="25" height="25" alt="${externalExpert.name}" /></a></div>
											<p class="People_name"><a href="#a">${externalExpert.name} ${externalExpert.jobRankName}</a></p>										
											<p class="People_team">${externalExpert.companyName}</p> 
										</li>		
									</c:forEach>									
								</ul>
							</c:if>
							</c:otherwise>
							</c:choose>
						</div>	
					
						<div class="l_t_corner"></div>
						<div class="r_t_corner"></div>
						<div class="l_b_corner"></div>
						<div class="r_b_corner"></div>				
					</div>
					<!--//corner_RoundBox07 End-->
								
				</div>	
				<!--//blockRight_svs End-->	
										
			</div>
			<!--//mainContents End-->
			<div class="clear"></div>
			
		</div>
		<!--//blockMain End-->
		
	</div>
	<!--//blockContainer End-->
	
		
</div>
<!--//wrapper End-->
