<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="sessionPortal" value="${sessionScope['ikep.portal']}" />
<c:set var="sessionUser" value="${sessionScope['ikep.user']}" />

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preTap"    	value="ui.socialpack.microblogging.tap" />
<c:set var="preButton"  value="ui.socialpack.microblogging.button" />
<c:set var="preLink"  	value="ui.socialpack.microblogging.link" /> 
<c:set var="preLabel" 	value="ui.socialpack.microblogging.label" />
<c:set var="preMessage"	value="ui.socialpack.microblogging.message" /> 
<c:set var="preNotice"	value="Notice.Mblog" /> 
<%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript" src="<c:url value="/base/js/units/socialpack/microblogging.js"/>"></script>
<script type="text/javascript">
<!--   
	
	// 현재 탭과 그 탭안에서의 항목
	var nowDiv;
	var typeInTap;

	// 회원이 볼 경우 탭을 설정하여 조회될 수 있게 한다.
	<c:if test="${true == isGroupMember}">
		var nowDiv = "tabs-1";
		var typeInTap = "1";
	</c:if>

	// 스크롤로 자동조회 진행 여부
	var windowScrollFlag = false;
	
(function($) {
	var dropdownTabs;
	$(document).ready(function() {

		var str_microblog_list = "<ul class='microblog_li' name='microblog_list'></ul>";
		
		$("#tabs-1").html(str_microblog_list);
		$("#tabs-2-1").html(str_microblog_list);
		$("#tabs-2-2").html(str_microblog_list);
		$("#tabs-2-3").html(str_microblog_list);
		$("#tabs-2-4").html(str_microblog_list);
		$("#tabs-2-5").html(str_microblog_list);
		$("#tabs-2-6").html(str_microblog_list);
		$("#tabs-3").html(str_microblog_list);
		$("#tabs-4").html(str_microblog_list);
		$("#tabs-5").html(str_microblog_list);
		$("#tabs2-1").html(str_microblog_list);
		$("#tabs2-2").html(str_microblog_list);
		
		var str_ul = "<ul></ul>";	
		$("#divSuggest").html(str_ul);
		$("#myImageContent").html(str_ul);
		$("#myFileContent").html(str_ul);
		$("#microblog_main_menu_layer").html(str_ul);
		
		// 회원이 볼 경우 탭을 보여준다.
		viewGeneralTaps();
		
		// tweet 길이 체크
		iKEP.checkLength("#contents", updateCheck);

		// 검색어 입력 후 엔터쳤을 때 검색
		$jq('#searchString').keypress(function(event) { 
			if(event.which == '13') {
				searchByInputWord('tabs2-1', 'G','${nowMbgroup.mbgroupId}');
			}
		}); 
		
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
        
        // 로그인 사용자의 오른쪽 고정 그룹 Div 보기
        $jq('#loginUserGroupView').click(function(event){
        	viewGroupDiv(event);
        });
        
        // 초대 버튼 이벤트 바인딩
		$jq('#inviteBtn').click(function(event) {
			var url = "<c:url value='/socialpack/microblogging/mbgroupMember/groupMemberForm.do' />";			
			iKEP.popupOpen(url, {width:600, height:271, argument : {mbgroupId:"${nowMbgroup.mbgroupId}",mbgroupName:"${nowMbgroup.mbgroupName}" }});
		});

        // 탈퇴하기 버튼 이벤트 바인딩
		$jq('#withdrawalBtn').click(function(event) {
			checkLastGroupMemberYN();
		});

		// 그룹에 속한 사용자가 본인 한명 뿐인지 여부 조회
		checkLastGroupMemberYN = function(){
			//alert(${nowMbgroup.mbgroupId});
			$jq.get(iKEP.getContextRoot() + "/socialpack/microblogging/mbgroupMember/checkLastGroupMemberYN.do", 
					// 블로그 주인
					{'mbgroupId':'${nowMbgroup.mbgroupId}'}, 
					function(data) {
						//alert(data);
						withdrawal($jq.trim(data));
			});
		};
		
		// 탈퇴함수 (마지막회원이 자신인지 여부)
		// 마지막 회원인지 여부 조회 후, 마지막 회원이면 탈퇴시 그룹이 삭제된다는 확인창을 띄워주고,
		// 마지막이 아니면, 탈퇴 확인창을 띄운다.
		withdrawal = function(YN){
			//alert(YN);
			var comment = '';
			if("Y" == YN){
				comment = "<ikep4j:message pre='${preMessage}' key='confirmWithdrawalAndDeleteGroup' />";
			}else{
				comment = "<ikep4j:message pre='${preMessage}' key='confirmWithdrawal' />";
			}
			
			if(confirm(comment))
			{
				$jq('form[name=mbgroupMemberForm]').attr({
					action:"<c:url value='/socialpack/microblogging/mbgroupMember/removeMbgroupMember.do?mbgroupId=${nowMbgroup.mbgroupId}'/>"
				});
				$jq('form[name=mbgroupMemberForm]').submit();
			}
		}
        
        // 그룹삭제 버튼 이벤트 바인딩
		$jq('#deleteGroupBtn').click(function(event) {
			if(confirm("<ikep4j:message pre='${preMessage}' key='confirmDeleteGroup' />"))
			{
				$jq('form[name=mbgroupForm]').attr({
					action:"<c:url value='/socialpack/microblogging/mbgroup/removeMbgroup.do?id=${nowMbgroup.mbgroupId}'/>"
				});
				$jq('form[name=mbgroupForm]').submit();
			}
		});

        // 그룹 수정 버튼 이벤트 바인딩
		$jq('#editGroupBtn').click(function(event) {
			var url = "<c:url value='/socialpack/microblogging/mbgroup/getMbgroup.do?mbgroupId=${nowMbgroup.mbgroupId}' />";
			iKEP.popupOpen(url, {width:650, height:298, scrollbar:'no'}, 'editGroupPop');
		});

        
        // 탭 클릭시 하단이 탭이 있는 경우 보이게 하기.
		//$("#divTab_s").tabs();
		dropdownTabs = new iKEP.DropdownTabs("#divTab_s", "#divTabSub", -20);
		$("#divTab2_s").tabs();

		// 탭 안의 각종 리스트를 클릭했을 때
		$("ul[name=microblog_list] > li").live("click", function(event) {
			//클릭한 객체의 이름에 따라 작동 구분.
			var tg = $(event.target).attr("name");  
			if(tg=="hashTag")
			{
				hideMicroblog_layer_content(nowDiv);
				// 해시테그로 검색을 한다.
				getHashTag(event.target);				
			}
			else if(tg== "addonLink" || tg == "strongLink"  || tg == "strongButtons")
			{
				// 원래 스크립트만 적용된다.
				hideMicroblog_layer_content(nowDiv);
			}
			else
			{
				//alert("else");
				// 우측 상세보기 레이어의 위치를 정하고 내용을 넣게 된다. (트윗 상세내역과 사용자 정보의 경우)
			    var mblogId = $(this).attr("id").replace('timelineMblogId_','');
				
			    var list_hei = $("ul[name=microblog_list]").position().top + $("ul[name=microblog_list]").height();
			    
			    var top = $(this).position().top;
			    var $div = $("#microblog_layer");
			    var topPos = top-($div.height()/2)+50;
			    if(topPos < 45){
			    	topPos = 45;
			    }
			    var lay_hei = topPos + $div.height();
			    if(lay_hei > list_hei){
			    	topPos = topPos - (lay_hei - list_hei);
			    }
				//iKEP.debug("topPos:"+topPos);
	
			    if(topPos < 45){
			    	topPos = 45;
			    }
			    
			    $div.css( { top:topPos  } );

			    // 상세내역과 사용자 정보가 보여질 레이어를 일단 비운다..
			    $("#microblog_layer_content").empty();
			    
			    // mblogId가 없으면 아무것도 하지 않는다.
			    if('' == mblogId){
			    	
			    }//  userInfo 정보 조회 일 때는 블로그 홈을 보여준다.
			    else if(tg =="userInfo")
				{					
					var userId = $(event.target).attr("id");
					//alert(userId);
					document.location.href = "<c:url value='/socialpack/microblogging/privateHome.do?ownerId='/>"+userId;
				} // 맨션 일 때는 상세 페이지에 사용자상세정보 페이지를 보여준다.
				else if(tg =="mention" )
				{		
					var objectId = $(event.target).attr("id");
					
					// 아이디 타입별로 다르게 보여준다.
					getObjectIdInfo(objectId, $div);
				} // following, follower, 검색의 people 일 때는 상세 페이지에 사용자상세정보 페이지를 보여준다.
				else if("tabs-7" == nowDiv || "tabs-8" == nowDiv || "tabs2-2" == nowDiv)
				{		
					var userId = $(this).attr("id").replace('userUserId_','');
					//alert(userId);
					$div.effect( "slide",  500, function() { detailForUserPop(userId); });
				} // 그 외에는  트윗 상세정보 페이지를 보여준다.
				else
				{
					$(this).siblings().removeClass("selected").end().addClass("selected");
					$div.effect( "slide",  500, function() { detailsForTweetPop(mblogId); });
				}
			}
		});

		$("ul[name=microblog_list] > li").live("mouseover", 
			  function () {			    
			    $(this).addClass("hover");
				$("span.microblog_icon", this).show();
				$("div.ic_micro_ar", this).show();
			  }
			 		
		);
		
		$("ul[name=microblog_list] > li").live("mouseout", 
			function () {
				$(this).removeClass("hover");
				$("span.microblog_icon", this).hide();
				$("div.ic_micro_ar", this).hide();
		  	}			
		);
				
		// 상세보기 레이어를 닫는 이벤트
		$("#close_img").click(function(event) { 
			hideMicroblog_layer_content(nowDiv);
		});


		var firstDraw =  true;
		//iKEP.debug("firstDraw1:"+firstDraw);
		
		<c:if test="${true == isGroupMember}">
			//최초에 타임라인 불러오기
			getTimeline('pre');
		</c:if>

		
		// 스크롤이 맨 하단에 위치했을 때 해당하는 탭의 다음 내용을 자동조회한다.
		$(window).scroll(function(){
			if  ($(window).scrollTop() == $(document).height() - $(window).height())
			{
				//alert(windowScrollFlag);
				if(windowScrollFlag) return;
				windowScrollFlag = true; 
				
				//alert(${maxFeedCount});
				//alert($jq("li", "#"+nowDiv).length);

				//Tab 별로 maxFeedCount보다 데이터가 더 많으면 더이상 조회하지 않게 한다. 예외를 할 탭은 예외 DIV 설정 로직을 추가한다.
				var overflow  = false; 
				var maxFeedCount = ${maxFeedCount};
				if(maxFeedCount <= $jq("li", "#"+nowDiv).length){
					overflow = true;
					alert("<ikep4j:message pre='${preMessage}' key='feedtoomuch' />");
				}
				
				//iKEP.debug("firstDraw2:"+firstDraw);
				if(!firstDraw && !overflow){
					//alert(nowDiv);
					//alert(typeInTap);
					
					// Feed 경우
					if("tabs-1" == nowDiv)
					{
						if("1" == typeInTap)
						{
							getTimeline('pre');
						}
						else{
							getThread('pre');
						}
					} // Member 경우는 모든 멤버를 처음에 다 보여준다.
					else if("tabs-3" == nowDiv)
					{
						//getMemberList('pre','${nowMbgroup.mbgroupId}',nowDiv);
					}  // Twitter  경우
					else if("tabs-4" == nowDiv)
					{
						$jq("#twitterPage").val(eval($jq("#twitterPage").val())+1);
						getTwitter(nowDiv, $jq("#twitterCount").val(), $jq("#twitterPage").val());
					}  // Facebook  경우
					else if("tabs-5" == nowDiv)
					{
						//조회하지 않는다.
					} // Search의 Tweets tab의 경우.
					else if("tabs2-1" == nowDiv)
					{
						var searchString = $jq('#resultSearchString').html();
						searchTweet('pre', searchString, nowDiv, 'G');
					} // Search의 People tab의 경우.
					else if("tabs2-2" == nowDiv)
					{
						var searchString = $jq('#resultSearchString').html();
						getSearchUser('pre', searchString, nowDiv, 'G', '${nowMbgroup.mbgroupId}');
					} // 그 외의 모든 경우
					else{
						getTimeline('pre');
					}
				}
			}
			firstDraw =  false;
			//iKEP.debug("firstDraw3:"+firstDraw);
		});

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
					required : true,
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
								$jq('form[name=mblogForm]')[0].reset();
								updateCheck(0);
								tabs1Click();
								//if("tabs-1" == nowDiv && "1" == typeInTap){
									//getTimeline('post');
								//}
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

	});
	// $(document).ready END
	
	
	// contents의 hashtag를 눌렀을 때, 태그id로 검색한다.
	getHashTag = function(obj) {
		//alert($(obj).attr("id"));
		searchByWord($(obj).attr("id"), 'tabs2-1', 'G', '${nowMbgroup.mbgroupId}');
	}
		
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
	}

	// 팝업에서 글을 썼을 때 콜백함수
	popupCallback = function(){
		tabs1Click();
	}
	
	// 타임라인 조회 (조회해온 데이터를 앞/뒤에 붙일건인지)
	getTimeline = function(standardType){
		//alert(standardType);
		//alert(nowDiv);
		//alert(typeInTap);

		// 실행할 url
		var url = "";
		var addonType = "";
		
		// 다음 리스트 조회시 기준이 되는 값들. 맨마지막이나 맨처음의 id.
		var standardMblogId = '';
		var standardAddonCode = '';
		
		try{
			if("pre" == standardType){
				standardMblogId = $jq("li", "#"+nowDiv).last().attr("id").replace('timelineMblogId_','');
			}else{
				standardMblogId = $jq("li", "#"+nowDiv).first().attr("id").replace('timelineMblogId_','');
			}
		}catch(e){}

		// Feed
		if("tabs-1" == nowDiv )
		{
			url = "<c:url value='/socialpack/microblogging/groupTimelineList.do'/>";
		} // My images, My files, My votes, My links
		else if("tabs-2-1" == nowDiv || "tabs-2-2" == nowDiv || "tabs-2-3" == nowDiv || "tabs-2-4" == nowDiv)
		{
			if("tabs-2-1" == nowDiv )
			{
				addonType = "2";	// 이미지
			}
			else if("tabs-2-2" == nowDiv )
			{
				addonType = "3";	// 파일
			}
			else if("tabs-2-3" == nowDiv )
			{
				addonType = "1";	// 투표
			}
			else if("tabs-2-4" == nowDiv )
			{
				addonType = "0";	// 링크 url
			}

			url = "<c:url value='/socialpack/microblogging/groupAddonListByAddonType.do'/>";
			
			try{
				if("pre" == standardType){
					standardAddonCode = $jq("li", "#"+nowDiv).last().attr("name").replace('timelineAddonCode_','');
				}else{
					standardAddonCode = $jq("li", "#"+nowDiv).first().attr("name").replace('timelineAddonCode_','');
				}
			}catch(e){}
		} //Retweets from outside
		else if("tabs-2-5" == nowDiv )
		{
				url = "<c:url value='/socialpack/microblogging/retwitFromOutsideList.do'/>";
		} // Retweets to outside
		else if("tabs-2-6" == nowDiv )
		{
			url = "<c:url value='/socialpack/microblogging/retwitToOutsideList.do'/>";
		}
		//alert(url);
		//alert(standardMblogId);
		
		// 로딩 이미지 표현할 Div 영역 설정
		addTempLoadingDiv();
		
		$jq.get(url, 
				// mbgroupId				 
				{'mbgroupId':'${nowMbgroup.mbgroupId}', 'addonType':addonType
			// 조회해온 데이터를 앞/뒤에 붙일건지  ,조회기준 MblogId					,조회기준 AddonCode
			, 'standardType':standardType, 'standardMblogId':standardMblogId, 'standardAddonCode':standardAddonCode}, 
				function(data) {
					//alert(data);
					//alert($jq("#"+nowDiv).html());

					if("" != data){
						data = $jq.trim(data);
					}
					if("" == data && "" == $jq.trim($jq("ul", "#"+nowDiv).html())){
						data = "<li><ikep4j:message pre='${preMessage}' key='noData' /></li>";
					}
					
					if("pre" == standardType){
						$jq("ul", "#"+nowDiv).append(data);
					}else{
						$jq("ul", "#"+nowDiv).prepend(data);
					}
					
					// 로딩이미지 표시한 임시영역 제거
					removeTempLoadingDiv();
		});
	};

	// Thread로 볼 때. 타임과 유사.
	getThread = function(standardType){
		//alert(standardType);
		//alert(nowDiv);
		var standardMblogId = '';
		try{
			if("pre" == standardType){
				standardMblogId = $jq("li", "#"+nowDiv).last().attr("name").replace('timelineThreadId_','');
			}else{
				standardMblogId = $jq("li", "#"+nowDiv).first().attr("name").replace('timelineThreadId_','');
			}
		}catch(e){}
		//alert(standardMblogId);
		var url = "<c:url value='/socialpack/microblogging/groupThreadList.do'/>";

		// 로딩 이미지 표현할 Div 영역 설정
		addTempLoadingDiv();
		
		$jq.get(url, 
				// mbgroupId			   			  ,조회해온 데이터를 앞/뒤에 붙일건지  ,조회기준 MblogId
				{'mbgroupId':'${nowMbgroup.mbgroupId}', 'standardType':standardType, 'standardMblogId':standardMblogId}, 
				function(data) {
					windowScrollFlag = false; 
					
					if("" != data){
						data = $jq.trim(data);
					}
					if("" == data && "" == $jq.trim($jq("ul", "#"+nowDiv).html())){
						data = "<li><ikep4j:message pre='${preMessage}' key='noData' /></li>";
					}
					
					if("pre" == standardType){
						$jq("ul", "#"+nowDiv).append(data);
					}else{
						$jq("ul", "#"+nowDiv).prepend(data);
					}
					
					// 로딩이미지 표시한 임시영역 제거
					removeTempLoadingDiv();
		});
	};
		

	//트윗글 삭제
	removeMblog = function(mblogId){
		//alert(mblogId);
		//alert($jq("#timelineMblogId_"+mblogId).attr("id"));
		if(confirm("<ikep4j:message pre='${preMessage}' key='mblogDelete' />")){
			$jq.post("<c:url value='/socialpack/microblogging/removeMblog.do'/>",
					{'id':mblogId}, 
					function(data) {
						//alert(data);
						// 해당 row를 지운다
						$jq("#timelineMblogId_"+mblogId).remove();
			});
		}
	};


	// microblog_main_menu_layer 부분이 아닌 곳을 선택했을 때 감춰준다.
	$("#microblog_main_menu_layer").click(function(event) {event.stopPropagation();});
    if(parent) $(parent.document.body).click(function() { $("#microblog_main_menu_layer").hide(); });
    $(document.body).click(function() { $("#microblog_main_menu_layer").hide(); });

	// divSuggest 부분이 아닌 곳을 선택했을 때 감춰준다.
	$("#divSuggest").click(function(event) {event.stopPropagation();});
    if(parent) $(parent.document.body).click(function() { divHide("divSuggest"); });
    $(document.body).click(function() { divHide("divSuggest"); });
    
    
	// 로그인한 사용자의 group list Div 보여주기
	viewGroupDiv = function(event){
	    var $div = $("#microblog_main_menu_layer");
	    		
    	$div.hide();
		$div.css("left", $(document).width()/2 + $jq(event.target).position().left -5);
		$div.css("top", $jq(event.target).position().top + 40);

	    getGroupList('${sessionUser.userId }',"microblog_main_menu_layer", '3');
	};

	//feed 일 때 timeline, thread 변경시키기
	changeFeedViewType = function(){
//alert(typeInTap);
		$jq("ul", "#tabs-1").empty();
		
		if("1" == typeInTap){
			typeInTap = "2";
			getThread('pre');
		}else{
			typeInTap = "1";
			getTimeline('pre');
		}
	};

	
	tabs1Click = function(){

		<c:if test="${true == isGroupMember}">
			// tapindex에 해당하는 탭이 선택된 것같은 효과.
			dropdownTabs.selectTab(0);
	
			tabsClickEvent('tabs-1');
		</c:if>
		<c:if test="${false == isGroupMember}">
			alert('<ikep4j:message pre='${preMessage}' key='onlyMember' />');
		</c:if>
	};

	// Member tab 클릭시 
	tabs3Click = function(){

		// tapindex에 해당하는 탭이 선택된 것같은 효과.
		dropdownTabs.selectTab(2);

		tabsClickEvent('tabs-3');
	};

	tabsClickEvent = function( div , type )	{
		windowScrollFlag = false; 
		hideMicroblog_layer_content(nowDiv);
		hideTimelineImage();
		
		nowDiv = div;
		typeInTap = type;
		switch(nowDiv) {
			case "tabs-1" :// Feed tab 클릭시
				typeInTap = "1";
				viewGeneralTaps();
				showTimelineImage();

				if(1 >= $jq("li", "#"+nowDiv).length){
					$jq("ul", "#"+nowDiv).empty();
					getTimeline('pre');
				} else getTimeline('post');
				break;
			case "tabs-2" :// 현재 Group tab 클릭시
				typeInTap = "1";
				break;
			case "tabs-3" :// Member tab 클릭시 
				viewGeneralTaps();
				
				$jq("ul", "#"+nowDiv).empty();
				getMemberList('pre','${nowMbgroup.mbgroupId}',nowDiv);
				$jq("#divTab_s_li_3").show();
				break;
			case "tabs-4" :// Twitter tab 클릭시
				//showExternalTap(3);				
				$jq("ul", "#"+nowDiv).empty();
				$jq("#twitterPage").val(1);
				
				getTwitter(nowDiv, $jq("#twitterCount").val(), $jq("#twitterPage").val());
				break;
			case "tabs-5" :// Facebook tab 클릭시
				//showExternalTap(4);	
				$jq("ul", "#"+nowDiv).empty();

				getFacebook(nowDiv);
				break;
			case "tabs2-1" :// 검색시 Tweets tab 클릭시
				var searchString = $jq('#resultSearchString').html();
				searchByWord(searchString, nowDiv, 'G', '${nowMbgroup.mbgroupId}');
				break;
			case "tabs2-2" :// 검색시 People tab 클릭시
				$("#divTab2_s").tabs("select",1);

				var searchString = $jq('#resultSearchString').html();
				getSearchUser('pre', searchString, nowDiv, 'G', '${nowMbgroup.mbgroupId}');
				break;
		}
	};

	
	// 해당 Group tab 의 하위 탭들을  눌렀을 때	
	tabs2typesClick = function(type){
		//typeInTap = type;

		//alert(type);
		if("1" == type){
			nowDiv = "tabs-2-1";
		}else if("2" == type){
			nowDiv = "tabs-2-2";
		}else if("3" == type){
			nowDiv = "tabs-2-3";
		}else if("4" == type){
			nowDiv = "tabs-2-4";
		}else if("5" == type){
			nowDiv = "tabs-2-5";
		}else if("6" == type){
			nowDiv = "tabs-2-6";
		}
		showTabs2typesTap(type);
		//alert(nowDiv);
		//$jq("ul", "#"+nowDiv).empty();
		if(0 == $jq("li", "#"+nowDiv).length){
			getTimeline('pre');
		}
		else
		{
			getTimeline('post');
		}
	};

	// 외부연동 Tap 보여주기
	showTabs2typesTap = function(type){
		$jq("#tabs-2-1").hide();
		$jq("#tabs-2-2").hide();
		$jq("#tabs-2-3").hide();
		$jq("#tabs-2-4").hide();
		$jq("#tabs-2-5").hide();
		$jq("#tabs-2-6").hide();
		$jq("#tabs-2-"+type).show();
	};
	
	// 검색을 했을 경우에 원래 탭들을 보려고 할 때 사용될 수 있다.
	viewGeneralTaps = function(){

		// 회원이 볼 경우 탭을 보여준다.
		<c:if test="${true == isGroupMember}">
			$jq("#divTab_s_li_1").show();
			$jq("#divTab_s_li_2").show();
			$jq("#divTab_s_li_3").show();
			$jq("#divTab_s_li_4").hide();
			$jq("#divTab_s_li_5").hide();

		</c:if>
		// 비회원이 볼 경우 안보이게 한다.
		<c:if test="${false == isGroupMember}">
			$jq("#divTab_s_li_1").hide();
			$jq("#divTab_s_li_2").hide();
			$jq("#divTab_s_li_3").hide();
			$jq("#divTab_s_li_4").hide();
			$jq("#divTab_s_li_5").hide();
			$jq("#timelineimage").hide();
		</c:if>

		//  검색리스트 div는 안보이고, 그 위치에 왼쪽 리스트div보이게 해야한다.
		$jq("#leftDiv1").show();
		$jq("#leftDiv2").hide();
	};
	

	// 외부연동 Tap 보여주기
	showExternalTap = function(tapindex){
		$jq("#divTab_s_li_1").hide();
		$jq("#divTab_s_li_2").hide();
		$jq("#divTab_s_li_3").hide();
		$jq("#divTab_s_li_4").show();
		$jq("#divTab_s_li_5").show();
		
		// tapindex에 해당하는 탭이 선택된 것같은 효과.
		dropdownTabs.selectTab(tapindex);

		//  검색리스트 div는 안보이고, 그 위치에 왼쪽 리스트div보이게 해야한다.
		$jq("#leftDiv1").show();
		$jq("#leftDiv2").hide();
	};
	
	
})(jQuery);

//-->
</script>

<div id="snsAuthInfo">
	<input type="hidden" id="twitterAccount" 	value = "${sessionUser.twitterAccount}" />
	<input type="hidden" id="twitterAuthCode" 	value = "${sessionUser.twitterAuthCode}" />
	<input type="hidden" id="facebookAuthCode" 	value = "${sessionUser.facebookAuthCode}" />
</div>

<input name="twitterCount" 		id="twitterCount" 		type="hidden" value="20" />
<input name="twitterPage" 		id="twitterPage"		type="hidden" value="0" />

<form name="mbgroupMemberForm" method="post"  action="">
</form>	
<form name="mbgroupForm" method="post" action="">
</form>		
<!--wrapper Start-->
<div id="wrapper">

	<!--blockContainer Start-->
	<div id="blockContainer" class="leftbg"><!--leftmenu bg 필요없는 화면에서는 class 삭제-->

		
		<!--blockMain Start-->
		<div id="blockMain">
				
			<!--mainContents Start-->
			<div id="mainContents"  class="conPadding_2">
				<h1 class="none">컨텐츠영역</h1>

<!-- microblog_l Start -->
				<div class="microblog_l">
				
					<!--microblog_layer Start-->
					<div class="microblog_layer none" style="background-color:white;" id="microblog_layer">
						<div class="microblog_layer_close">
							<a href="#a"><img src="<c:url value='/base/images/common/btn_close.gif'/>" alt="close" id="close_img"/></a>
						</div>
						<div class="microblog_layer_con" id="microblog_layer_content"></div>
					</div>
					<!--//microblog_layer End-->
					
					<!--일반적인 리스트의 경우 left [S] -->
					<div id="leftDiv1" >
					<c:if test="${true == isGroupMember}">
					<!--트윗 쓰기 페이지  [S]-->
						<!--microblog_write Start-->
						<div class="microblog_write">
							<div class="corner_RoundBox05">
								<h2><ikep4j:message pre='${preLabel}' key='title' /></h2>
								<div class="microblog_write_list"  id="addonDiv1" style="display:none;">
									<ul>
										<li><a href="#a" onclick="shortUrl();"><ikep4j:message pre='${preButton}' key='shorturl'  /></a></li>
										<li><a href="#a" id="imageupload"><ikep4j:message pre='${preButton}' key='imageupload'  /></a></li>
										<li><a href="#a" id="fileupload"><ikep4j:message pre='${preButton}' key='fileupload'  /></a></li>
										<li><a href="#a" id="pollAdd"><ikep4j:message pre='${preButton}' key='vote'  /></a></li>
										<li class="liLast"><a href="#a" id="teamMention"><ikep4j:message pre='${preButton}' key='teamMention'  /></a></li>
									</ul>
								</div>
								<form id="mblogForm" name="mblogForm" method="post"  action="">
									<input type ="hidden" name="mblogType" value = "0" />
									<input type ="hidden" name="mbgroupId" value = "${nowMbgroup.mbgroupId}" />
									<textarea id="contents" name="contents" title="<ikep4j:message pre='${preLabel}' key='contents'  />" cols="" rows="" class="w100">@${nowMbgroup.mbgroupId} </textarea>
									<!--autoComplete_layer Start-->
										<div id="divSuggest" class="none" style="position:absolute; z-index:100; background:white ; width:200px; height:200px; border:1px solid #999;">
										</div>
									<!--//microblog_layer End-->
									<div id="addonDiv2" style="display:none;">
										<div class="microblog_write_check">
											<span><label><input name="isRetweetAllowed" type="checkbox" title="Retweet 허용" class="checkbox" value="1" checked="checked" /> <img src="<c:url value='/base/images/icon/ic_retweet.gif'/>" alt="retweet" /><ikep4j:message pre='${preLabel}' key='isRetweetAllowed'  /></label></span>
											
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
								<div class="l_t_corner"></div>
								<div class="r_t_corner"></div>
								<div class="l_b_corner"></div>
								<div class="r_b_corner"></div>			
								<div class="microblog_insertImg none">
									<div class="microblog_insertBox">
										<div class="microblog_insertBox_tit_img"><span><ikep4j:message pre='${preLabel}' key='image' /></span><a href="#a"><img src="<c:url value='/base/images/icon/ic_close_layer.gif'/>" alt="" id="myImageClose" /></a></div>
										<div id="myImageContent">
										</div>
									</div>
								</div>					
								<div class="microblog_insertFile none">
									<div class="microblog_insertBox">
										<div class="microblog_insertBox_tit_file"><span><ikep4j:message pre='${preLabel}' key='file' /></span><a href="#a"><img src="<c:url value='/base/images/icon/ic_close_layer.gif'/>" alt="" id="myFileClose" /></a></div>
										<div id="myFileContent">
										</div>
									</div>
								</div>					
							</div>
						</div>
						<!--//microblog_write End-->
					</c:if>
					<c:if test="${false == isGroupMember}">
						<!--프로필 페이지  [S]-->
						<div class="microblog_pr">
							<div class="microblog_pr_img">
								<!-- // 사진정보. -->
								<img src="<c:url value='/support/fileupload/downloadFile.do?fileId=${nowMbgroup.imagefileId}&amp;smallimageYn=N' />" width="190" height="190" alt="큰 프로파일 사진" />
							</div>
							<div class="microblog_pr_con">
								<div class="microblog_pr_name">${nowMbgroup.mbgroupName} @${nowMbgroup.mbgroupId}</div>
								<div class="more"> <ikep4j:message pre='${preLabel}' key='created'/> ${nowMbgroup.registDate} </div>
								<p><c:out value="${ikep4j:cutString(nowMbgroup.mbgroupIntroduction,210,'...')}" escapeXml="false"/></p>
							</div>
							<div class="clear">&nbsp;</div>
							<div class="clear"><h3><ikep4j:message pre='${preLabel}' arguments="${nowMbgroup.mbgroupName}" key='groupFuncIntro' /></h3></div>
						</div>		
						<!--프로필 페이지 [E]-->
					</c:if>
						<!--tab Start-->		
						<div id="divTab_s" class="iKEP_tab_blog">
							<ul>
								<li id="divTab_s_li_1"><a href="#tabs-1" onclick="javascript:tabsClickEvent('tabs-1');"><ikep4j:message pre='${preTap}' key='feed'/></a></li>
								<li id="divTab_s_li_2"><a href="#tabs-2" onclick="javascript:tabsClickEvent('tabs-2');">${nowMbgroup.mbgroupId} <img src="<c:url value='/base/images/icon/ic_tab_ar.gif'/>" alt="" /></a></li>
								<li id="divTab_s_li_3"><a href="#tabs-3" onclick="javascript:tabsClickEvent('tabs-3');"><ikep4j:message pre='${preTap}' key='members'/></a></li>
								<li id="divTab_s_li_4" style="display:none;"><a href="#tabs-4" onclick="javascript:tabsClickEvent('tabs-4');"><ikep4j:message pre='${preTap}' key='twitter' /></a></li>
								<li id="divTab_s_li_5" style="display:none;"><a href="#tabs-5" onclick="javascript:tabsClickEvent('tabs-5');"><ikep4j:message pre='${preTap}' key='facebook'/></a></li>
							</ul>
							<div id="timelineimage" class="ic_timeline" title="<ikep4j:message pre='${preLabel}' key='threadOrder' />"><a href="#a" onclick="javascript:changeFeedViewType();"><img src="<c:url value='/base/images/icon/ic_timeline.gif'/>" alt="timeline" /></a></div>
						
							
							<div id="divTabSub">
								<!--Tab Feed -->
								<div class="tabMenu none"></div>
								<!--Tab now Group -->
								<div class="tabMenu none">
									<ul>
										<li><a href="#tabs-2-1" onclick="javascript:tabs2typesClick(1);return false;"><ikep4j:message pre='${preTap}' key='groupimage' /></a></li>
										<li><a href="#tabs-2-2" onclick="javascript:tabs2typesClick(2);return false;"><ikep4j:message pre='${preTap}' key='groupfile' /></a></li>
										<li><a href="#tabs-2-3" onclick="javascript:tabs2typesClick(3);return false;"><ikep4j:message pre='${preTap}' key='groupvote' /></a></li>
										<li><a href="#tabs-2-4" onclick="javascript:tabs2typesClick(4);return false;"><ikep4j:message pre='${preTap}' key='grouplink' /></a></li>
										<li><a href="#tabs-2-5" onclick="javascript:tabs2typesClick(5);return false;"><ikep4j:message pre='${preTap}' key='retweetedfromoutside' /></a></li>
										<li><a href="#tabs-2-6" onclick="javascript:tabs2typesClick(6);return false;"><ikep4j:message pre='${preTap}' key='retweetedtooutside' /></a></li>
									</ul>
								</div>
								<!--Tab Member -->
								<div class="tabMenu none"></div>
								<!--Tab Twitter -->
								<div class="tabMenu none"></div>
								<!--Tab Facebook -->
								<div class="tabMenu none"></div>
							</div>
								
							<div class="tab_con">
								<!--Tab Feed -->
								<div id="tabs-1">
								</div>
								<!--Tab now Group -->
								<div id="tabs-2">	
									<!--Tab now Group 의 하부 리스트들 Div [S] -->
									<div id="tabs-2-1">
									</div>
									<div id="tabs-2-2">
									</div>
									<div id="tabs-2-3">
									</div>
									<div id="tabs-2-4">
									</div>
									<div id="tabs-2-5">
									</div>
									<div id="tabs-2-6">
									</div>
									<!--Tab now Group 의 하부 리스트들 Div [E] -->
								</div>
								<!--Tab Member -->
								<div id="tabs-3">	
								</div>
								<!--Tab Twitter -->
								<div id="tabs-4">	
								</div>
								<!--Tab Facebook -->
								<div id="tabs-5">	
								</div>
							</div>	
						
						</div>		
						<!--//tab End-->
	
					</div>  
					<!--일반적인 리스트의 경우 left [E] -->
					
					
					<!--검색의 경우 left [S] -->
					<div id="leftDiv2" style="display:none;" >
	
						<!--save search Start-->
						<div class="microblog_result">
							<div class="result_back"><a href="#a" onclick="javascript:tabs1Click();"><img src="<c:url value='/base/images/common/btn_back_2.gif'/>" alt="back" /></a></div>
							<div class="result_btn"><a class="button_pr" href="#a" onclick="saveSearchWord();"><span><ikep4j:message pre='${preButton}' key='savethissearch' /></span></a></div>
							<p><ikep4j:message pre='${preLabel}' key='resultfor'  /> <span id="resultSearchString"> </span></p>
							<input type="hidden" id="searchId" />
						</div>
						<!--//save search End-->
						
						<!--tab Start-->		
						<div id="divTab2_s" class="iKEP_tab_blog">
							<ul>
								<li><a href="#tabs2-1" onclick="javascript:tabsClickEvent('tabs2-1');"><ikep4j:message pre='${preTap}' key='tweets' /></a></li>
								<li><a href="#tabs2-2" onclick="javascript:tabsClickEvent('tabs2-2');"><ikep4j:message pre='${preTap}' key='people'/></a></li>
							</ul>	
						
							<div class="tab_con">		
								<div id="tabs2-1">
								</div>
								<div id="tabs2-2">
								</div>
							</div>	
						</div>		
						<!--//tab End-->
					</div>  
					<!--검색의 경우 left [E] -->
											
				</div>
<!--//microblog_l End -->
				
				  
				
<!-- microblog_r Start -->
				<div class="microblog_r">

					<!--microblog_main_menu_layer Start-->
					<div style="position:absolute; top:55px; left:50%; width:150px; z-index:10; border:1px solid #8196ff; background-color:white; display:none;" id="microblog_main_menu_layer">
					</div>
					<!--//microblog_main_menu_layer End-->
					
					<div class="microblog_rt">
						<div class="corner_RoundBox06">
							<ul>
								<li><a href="<c:url value='/socialpack/microblogging/privateHome.do?ownerId=${sessionUser.userId}'/>"><img src="<c:url value='/base/images/icon/ic_micro_home.png'/>" alt="" /><ikep4j:message pre='${preLink}' key='home'  /></a></li>
								<li><a href="#a" onclick="javascript:twitPop();"><img src="<c:url value='/base/images/icon/ic_micro_write.png'/>" alt="" /><ikep4j:message pre='${preLink}' key='write' /></a></li>
								<li><a href="#a" id="loginUserGroupView"><img src="<c:url value='/base/images/icon/ic_micro_group.png'/>" alt="" /><ikep4j:message pre='${preLink}' key='groups' /></a></li>
								<li><a href="#a" onclick="javascript:showExternalTap(3);"><img src="<c:url value='/base/images/icon/ic_micro_external.png'/>" alt="" /><ikep4j:message pre='${preLink}' key='external'  /></a></li>
								<li><a href="#a" onclick="javascript:settingPop();"><img src="<c:url value='/base/images/icon/ic_micro_setting.png'/>" alt="" /><ikep4j:message pre='${preLink}' key='setting'  /></a></li>
							</ul>
							<div class="l_t_corner"></div>
							<div class="r_t_corner"></div>
							<div class="l_b_corner"></div>
							<div class="r_b_corner"></div>				
						</div>
					</div>
					<!--microblog_rb Start-->
					<div class="microblog_rb" id="microblog_content">
						<div>
							<div class="microblog_img">
								<!-- // 사진정보. -->
								<img src="<c:url value='/support/fileupload/downloadFile.do?fileId=${nowMbgroup.imagefileId}&amp;smallimageYn=Y' />" width="50" height="50" alt="" title="${nowMbgroup.mbgroupName}" />
							</div>
							<div class="microblog_con">
								<div class="microblog_me">${nowMbgroup.mbgroupName} @${nowMbgroup.mbgroupId}</div>
								<div class="more"><c:out value="${nowMbgroup.mbgroupIntroduction}" escapeXml="false"/></div>
							</div>
							<div class="clear"></div>
						</div>
						
						<div class="microblog_numtable">
							<table summary="Tweets, Members">
								<caption></caption>
								<tbody>
									<tr>
										<td class="microblog_num"><a href="#a" onclick="javascript:tabs1Click();" >${groupTweetCount}</a></td>
										<td class="microblog_num"><a href="#a" onclick="javascript:tabs3Click();" >${nowMemberListSize}</a></td>
									</tr>
									<tr>
										<td><ikep4j:message pre='${preLabel}' key='tweets'  /></td>
										<td><ikep4j:message pre='${preLabel}' key='members'  /></td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<c:if test="${true == isGroupMember}">
							<div class="search_inline">		
								<form name="searchForm" method="post" action="" onsubmit="return false;">									
									<input type="text" class="inputbox" title="inputbox"  id="searchString" name="searchString" value="" size="25" />
									<a href="#a" class="ic_search" onclick="javascript:searchByInputWord('tabs2-1', 'G', '${nowMbgroup.mbgroupId}');"><span><ikep4j:message pre='${preButton}' key='search' /></span></a>
								</form>
							</div>
							<div class="blockBlank_20px"></div>
						</c:if>
						
						<!--Member List Start-->
						<div class="followlist">
							<h3>
								<ikep4j:message pre='${preLabel}' key='members'  />
							</h3>
							<div class="more"><img src="<c:url value='/base/images/icon/ic_plus_s.gif'/>" alt="" /> <a href="#a" onclick="javascript:tabs3Click();" ><ikep4j:message pre='${preButton}' key='viewall' /></a></div>
							<div class="clear"></div>
							<c:if test="${not empty nowMemberList }">
								<ul>
									<c:forEach var="nowMember" items="${nowMemberList}" varStatus="loopStatus" begin="0" end="9">
										<li><a href="<c:url value='/socialpack/microblogging/privateHome.do?ownerId=${nowMember.memberId}'/>">
											<!-- //아이디에 해당하는 사진정보. -->
											<img src="<c:url value='${nowMember.profilePicturePath}' />" width="50" height="50" alt="" title="${nowMember.memberId}" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />
										</a></li>
									</c:forEach>																																							
								</ul>
							</c:if>
						</div>
						<!--//Member List End-->				
						
		
						<c:if test="${true == isGroupMember}">
							<!--invited Member List Start-->
							<div class="invitedby">
								<h3><ikep4j:message pre='${preLabel}' key='inviting' /> ${invitingInfoListSize} <ikep4j:message pre='${preLabel}' key='people' /></h3>
								<c:if test="${not empty invitingInfoList }">
									<ul>
										<c:forEach var="invitingInfo" items="${invitingInfoList}" varStatus="loopStatus" >
											<li>
												<c:choose>
													<c:when test="${sessionUser.localeCode == sessionPortal.defaultLocaleCode}">
														${invitingInfo.teamName} ${invitingInfo.memberName} ${invitingInfo.jobTitleName} by ${invitingInfo.registerName}
													</c:when>
													<c:otherwise>
														${invitingInfo.teamEnglishName} ${invitingInfo.memberEnglishName} ${invitingInfo.jobTitleEnglishName} by ${invitingInfo.registerEnglishName}
													</c:otherwise>
												</c:choose>
												 <span class="invitedby_time">${ikep4j:getTimeInterval(invitingInfo.registDate, sessionUser.localeCode)}</span> 
											</li>
										</c:forEach>					
									</ul>
								</c:if>
							</div>
							<!--//invited Member List End-->
						</c:if>
						
						<!--trends Start-->
						<div class="trends">
							<h3><ikep4j:message pre='${preLabel}' key='grouptrends' /></h3>
							<c:if test="${not empty trendList }">
								<ul>
									<c:set var="trendcount" value="0"/>
									<c:set var="liOpen" value="0"/>
									<c:forEach var="trend" items="${trendList}" varStatus="loopStatus" begin="0" end="9" >
										<c:if test="${0 == trendcount % 3}"><li><c:set var="liOpen" value="1"/></c:if>
											<a href="#a" <c:if test="${true == isGroupMember}"> onclick="javascript:searchByWord('${trend}', 'tabs2-1', 'G', '${nowMbgroup.mbgroupId}');" </c:if> >${trend}</a>
											<c:set var="trendcount" value="${trendcount+1}"/>
										<c:if test="${trendcount > 0 && (0 == trendcount % 3 || 8 == trendcount)}"></li><c:set var="liOpen" value="0"/></c:if>
									</c:forEach>					
									<c:if test="${1 == liOpen}"></li></c:if>
								</ul>
							</c:if>
						</div>
						<!--//trends End-->
						
						<!--buttons Start-->
						<c:if test="${true == isGroupMember}">
							<div class="blockButton_2"> 
								<ul>
									<li><a class="button" id="inviteBtn"><span><ikep4j:message pre='${preButton}' key='inviteNewMember' /></span></a></li>
									<li><a class="button" id="withdrawalBtn"><span><ikep4j:message pre='${preButton}' key='withdrawalGroup' /></span></a></li>
									<c:if test="${sessionUser.userId == nowMbgroup.registerId}">
										<li><a class="button" id="deleteGroupBtn"><span><ikep4j:message pre='${preButton}' key='deleteGroup' /></span></a></li>
										<li><a class="button" id="editGroupBtn"><span><ikep4j:message pre='${preButton}' key='editGroup' /></span></a></li>
									</c:if>
								</ul>
							</div>
						</c:if>
						<!--//buttons End-->
						
					</div>
					<!--//microblog_rb End-->
														
				</div>
<!-- //microblog_r End -->
													
			</div>
			<!--//mainContents End-->
			<div class="clear"></div>
		</div>
		<!--//blockMain End-->
		
	</div>
	<!--//blockContainer End-->
	
		
</div>
<!--//wrapper End-->

