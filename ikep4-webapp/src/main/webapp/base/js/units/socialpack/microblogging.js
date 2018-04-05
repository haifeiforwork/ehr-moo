(function($) {

	// window와 document의 높이 차이
	var documentWindowGapsize = 0; 
	
	$(document).ready(function() {

		
		//*******************************************************************************
		//***************************** 자동 완성 기능 시작 **********************************
		//*******************************************************************************
		var atPosition = -1;
		var $suggest = $("#divSuggest");
		
		var evtAtCheck = function(event) {
			if(event.which == 64) { // @
				$textarea = $(this);
				
				atPosition = event.target.selectionStart;

				// firefox, ie9는 selectionStart 속성이 있으나, ie8 등은 없다.
				if("undefined" == typeof atPosition)
				{
					//alert('event.target.innerText.length:'+event.target.innerText.length);
					//alert(event.target.innerText.indexOf("\n"));  // TextRange..
					if(0 == event.target.innerText.length){
						atPosition = 0;
					}else{
						atPosition = -1;
					}
				}
				//alert(atPosition);
				if(-1 < atPosition ){
					
					var offset = $textarea.unbind("keypress", evtAtCheck)
					    			.bind("keydown", evtArrowKeyCheck)				
					    			.bind("keypress", evtSeachAndSpaceCheck)	//focusout
									.offset();
					$suggest.css({top:$textarea.top+$textarea.outerHeight()+"px", left:offset.left+"px"})
						.bind("click", function(event) {
							if(event.target.tagName.toLowerCase() == "a") {
								$anchor = $(event.target);
								
								var str = $textarea.val();
								var replacePosition = str.indexOf(" ", atPosition);
								if(replacePosition == -1) replacePosition = str.indexOf("\n", atPosition);
								if(replacePosition == -1) replacePosition = str.indexOf("\t", atPosition);
								
			    				var beforeString = str.substring(0, atPosition+1);
			    				var afterString = "";
			    				if(replacePosition > -1) afterString = str.substring(replacePosition, str.length);
			    				
			    				var replaceString = $anchor.html();
			    				$textarea.val(beforeString + replaceString + afterString);
			    				
			    				setSuggestEvent($textarea);
			    				$textarea.focus();
							}
						});
						//.bind("keydown", evtArrowCheck)
						//.show();
					searchForAutoComplete('', $textarea, $("#divSuggest"));
				}
			}
		};
		
		var evtSeachAndSpaceCheck = function(event) {
			$textarea = $(this);
			switch(event.which) {
				case 13 :	// enter	
				case 32 :	// space
				case 9 :	// tab
				case undefined :
					setSuggestEvent($textarea, event.which);
					break;
				case 0 : break;
				default :
					//var xmlHttpRequest = null;
					setTimeout(function(){
						var cutString = $textarea.val().substring(atPosition+1, event.target.selectionStart);
						if(atPosition > event.target.selectionStart ||  cutString.indexOf(" ") > -1 || cutString.indexOf("\n") > -1) {
							setSuggestEvent($textarea);
							return false;
						}
						
						//if(xmlHttpRequest != null) xmlHttpRequest.abort();
	
						searchForAutoComplete(cutString, $textarea, $("#divSuggest"));
	
					}, 100);
			}
		};
		
		var evtArrowKeyCheck = function(event) {
			switch(event.which) {
				case 13 : 
					var selectedItem = $suggest.find("li.selected");
					if(selectedItem.length > 0) {
					    selectedItem.children("a").focus().trigger("click");
					}
					event.preventDefault();
					break;
				case 37 :	// left	
				case 38 : //top
					var items = $suggest.find("li");
					var selectedItem = items.filter(".selected");
					if(selectedItem.length > 0) {
						selectedItem.removeClass("selected");
						var prevItem = selectedItem.prev("li");
						if(prevItem.length > 0) prevItem.addClass("selected");
						else items.eq(items.length-1).addClass("selected");
					} else {
						items.eq(items.length-1).addClass("selected");
					}
					event.preventDefault();
					break;
				case 39 :	// right
				case 40 : //bottom
					var items = $suggest.find("li");
					var selectedItem = items.filter(".selected");
					if(selectedItem.length > 0) {
						selectedItem.removeClass("selected");
						var nextItem = selectedItem.next("li");
						if(nextItem.length > 0) nextItem.addClass("selected");
						else items.eq(0).addClass("selected");
					} else {
						items.eq(0).addClass("selected");
					}
					event.preventDefault();
					break;
			}
		};
		
		var evtArrowCheck = function(event) {
			switch(event.which) {
				//case 37 :	// left	
				case 38 :	// top
					$(event.target).parent().prev("li").children("a").focus();
					//event.preventDefault();
					break;
				//case 39 :	// right
				case 40 :	// bottom
					$(event.target).parent().next("li").children("a").focus();
					//event.preventDefault();
					break;
			}
		};
		
		setSuggestEvent = function(el, keycode) {
			//iKEP.debug("setSuggestEvent!!");
			//iKEP.debug(el);
			if(keycode == 13) {
				var selectedItem = $suggest.find("li.selected");
				if(selectedItem.length > 0) {
				    selectedItem.children("a").focus().trigger("click");
				    return false;
				}
			}
			
			$(el).unbind("keypress", evtSeachAndSpaceCheck)	//focusout
				.unbind("keydown", evtArrowKeyCheck)
				.bind("keypress", evtAtCheck);
			$suggest.unbind("click")
				//.unbind("keydown", evtArrowCheck)
				.hide();

			dialogWindowReduce();
		};

		// 맨션+follow 자동완성 함수
		searchForAutoComplete = function(memberId, conDiv, suggestDiv){
			//alert(memberId);
			//alert(conDiv);
			//alert(suggestDiv);
			var url = iKEP.getContextRoot() + "/socialpack/microblogging/searchForAutoComplete.do";
			$jq.get(url, 
					{'memberId':memberId}, 
					function(data) {
						//alert(data);
						// 검색 데이터가 없으면 
						if("" == $jq.trim(data))
						{
							setSuggestEvent(conDiv);
						}
						else// 검색 데이터가 있으면
						{
							$jq("ul", suggestDiv).empty().append(data);
					
							suggestDiv.show();
							suggestDiv.find("li.selected").removeClass("selected");
							suggestDiv.find("li:first").addClass("selected");

							dialogWindowExpand();
						}
			});
		};
		
		setSuggestEvent("#contents");

		//*******************************************************************************
		//***************************** 자동 완성 기능 끝 ************************************
		//*******************************************************************************

	});
	// $(document).ready END

	divHide = function(div){
		$("#"+div).hide();
		dialogWindowReduce();
	};
	
	// dialog windw 일때 사이즈 크게 하기.
	dialogWindowExpand = function(){
		if(window["dialogWindow"]) {
			//alert('dialog expand');
			if($(document).height() > dialogWindow.container.dialog("option", "height")){
				documentWindowGapsize = $(document).height() - dialogWindow.container.dialog("option", "height") + 60;
				dialogWindow.container.dialog("option", "height", dialogWindow.container.dialog("option", "height") + documentWindowGapsize);
			}
			//alert('documentWindowGapsize'+documentWindowGapsize);
		}		
	};

	// dialog windw 일때 사이즈 다시 작게 하기.
	dialogWindowReduce = function(){
		if(window["dialogWindow"]) {
			//alert('dialog reduce');
			dialogWindow.container.dialog("option", "height", dialogWindow.container.dialog("option", "height") - documentWindowGapsize);
			documentWindowGapsize = 0;
		}		
	};
	
	//입력체크
	updateCheck = function(length){
		$jq("#addonDiv1").show();
		$jq("#addonDiv2").show();
		$jq("#contents").attr("rows", 3);
		textLength = 140 - length;
		document.getElementById("textcount").innerHTML = textLength;
	};

	//단축 url 부가정보 테이블에 저장하고 입력창에서 값 변경하기
	shortUrl = function(){
		//alert('short!!');
		var contentsVal = document.getElementById("contents").value;
		var startIndexHttp = contentsVal.indexOf("http://");
		//iKEP.debug("startIndexHttp:"+startIndexHttp);
		
		//url 이면 url 끝부분을 찾는다.
		if(0 <= startIndexHttp)
		{
			var endIndexHttp = -1;
			
			for (x=startIndexHttp; x<contentsVal.length; x++) {
			      if (contentsVal.charAt(x) == " " || contentsVal.charAt(x) == "\n" || contentsVal.charAt(x) == "\t") 
			      {
			    	  endIndexHttp = x;
			    	  break;
			      }
			      endIndexHttp = x;
			}
			//iKEP.debug("endIndexHttp:"+endIndexHttp);
			
			var stringHttp = contentsVal.substring(startIndexHttp, endIndexHttp+1);
			//iKEP.debug("stringHttp:"+stringHttp);

			// url을 addon에 저장하고 받은 id로 본문을 교체한다.
			if("" != stringHttp){
				$jq.post(iKEP.getContextRoot() + "/socialpack/microblogging/addon/createAddon.do", 
						{'addonType':'0','sourceLink':stringHttp}, 
						function(data) {
							//alert(data);
							//iKEP.debug("data:"+data);
							
							document.getElementById("contents").value = contentsVal.replace(stringHttp,data);
							
							//재귀하여 url이 여러개 있어도 바뀌게 한다.
							shortUrl();
				});
			}
		}
	};

	// 이미지/파일 관련정보 부가정보 테이블에 저장하고 입력창에서 값 변경하기
	//							파일id,  업로드타입(2:이미지,3:파일) - addon type에 따른다.
	saveFileToAddon = function(fileId, fileUploadType){
		//이미지파일 업로드한 후에 받은 파일 id를 서버에 보낸다.
		if("" != fileId){
			$jq.post(iKEP.getContextRoot() + "/socialpack/microblogging/addon/createAddon.do", 
					{'addonType':fileUploadType,'sourceLink':fileId}, 
					function(data) {
						//alert(data);
						document.getElementById("contents").value = document.getElementById("contents").value +data;
			});
		}
		
	};

	// 사용자가 속한 그룹 리스트 조회
	// 						userId, 영역		, 리스트 보여줄 때 +New Group 보여줄지 여부/ 그룹으로 가는 링크로 보여줄지 여부 등등
	getGroupList = function(userId, divArea, showType){
		//alert(userId);
		//alert(divArea);
		//alert(showType);
		$jq.get(iKEP.getContextRoot() + "/socialpack/microblogging/mbgroup/grouplistByUserId.do", 
				// 블로그 주인
				{'userId':userId, 'showType':showType}, 
				function(data) {
					//alert(data);
					if("" != $jq.trim(data)){
						$jq("ul", "#"+divArea).empty().append(data);
						$jq("#"+divArea).show();

						dialogWindowExpand();
					}
					else
					{
						$jq("#"+divArea).hide();
					}
		});
	};

    //검색되거나 선택된 값이 있을경우 실행되는 콜백 메소드
    //result: 검색되거나 선택된 값을 배열형태로 리턴함
    setTeamMention = function(data) {
		$jq.each(data, function() {
			//alert($jq.trim(this.code));
			//alert($jq.trim(this.name));
			$jq("#contents").val($jq("#contents").val()+ " @" + $jq.trim(this.code) + " (" + $jq.trim(this.name) + ")");
		});
	};

	// twit시 groupId 설정
	setMblogGroupId = function(mbgroupId){
		//alert(mbgroupId);
		document.getElementById("mbgroupId").value = mbgroupId;
		if("" != mbgroupId){
			$jq("#contents").val($jq("#contents").val()+"@"+mbgroupId);
		}
		setMenuLayerHide();
	};

	setMenuLayerHide = function(){
	    $("#microblog_main_menu_layer").hide();		
	};

	// 타임라인 안에 있는 이미지를 안보이게 하는 것. 이미지부분이 mblogId과 fileId 로 div가 설정되어있다.
	closeImage = function(twitImageId) {
		//alert(twitImageId);
		//alert($jq('#'+twitImageId));
		//alert($jq('#'+twitImageId).attr("id"));
		$jq('#'+twitImageId).hide();

	};

	// 타임라인 안에 있는 이미지를 다시 보이게 하는 것.
	openImage = function(twitImageId) {
		//alert(twitImageId);
		$jq('#'+twitImageId).show();

	};

	// twitter에서 트윗 리스트 가져오기.
	getTwitter = function(nowDiv, twitterCount, twitterPage){
		//alert(nowDiv);

		// 로딩 이미지 표현할 Div 영역 설정
		addTempLoadingDiv();
		
		$jq.get(iKEP.getContextRoot() + "/support/externalSNS/TwitterList.do", 
				// 조회타입
				{'sectionFlag':'homeTimeLine', 'twitterCount':twitterCount, 'twitterPage':twitterPage}, 
				function(data) {
					//alert(data);
					$jq("ul", "#"+nowDiv).append(data);
					
					// 로딩이미지 표시한 임시영역 제거
					removeTempLoadingDiv();
		});
	};

	// Facebook에서 트윗 리스트 가져오기.
	getFacebook = function(nowDiv){
		//alert(nowDiv);
		$jq.get(iKEP.getContextRoot() + "/support/externalSNS/FacebookList.do", 
				// 조회타입
				{}, 
				function(data) {
					//alert(data);
					$jq("ul", "#"+nowDiv).append(data);
		});
	};


	// Favorite 추가 버튼 생성
	addFavoriteBtn = function(mblogId, divArea) {
		var str = "<span class='microblog_ic_favorite'>";
		str = str + "<a href='#a' name='strongButtons' onclick=\"regFavorite('"+mblogId+"','"+divArea+"');\">";
		str = str + "Favorite</span>";
		str = str + "</a>";
		//alert(str);
		//$jq("#"+divArea).html(str);
		$jq("span[name="+divArea+"]").html(str);
	};
	
	// Favorite 삭제 버튼 생성
	delFavoriteBtn = function(mblogId, divArea) {
		var str = "<span class='microblog_ic_unfavorite'>";
		str = str + "<a href='#a' name='strongButtons' onclick=\"deleteFavorite('"+mblogId+"','"+divArea+"');\">";
		str = str + "Unfavorite</span>";
		str = str + "</a>";
		//alert(str);
		//$jq("#"+divArea).html(str);
		$jq("span[name="+divArea+"]").html(str);
	};
		
	// 관심글로 등록
	regFavorite = function(mblogId, divArea){
		//alert("reg:"+mblogId);
		//alert(divArea);
		$jq.post(iKEP.getContextRoot() + "/socialpack/microblogging/createFavorite.do", 
				{'mblogId':mblogId}, 
				function(data) {
					//alert(data);
					delFavoriteBtn(mblogId, divArea);
		});
	};


	// 관심글 취소하기
	deleteFavorite = function(mblogId, divArea){
		//alert("del:"+mblogId);
		//alert(divArea);
		$jq.post(iKEP.getContextRoot() + "/socialpack/microblogging/removeFavorite.do", 
				{'mblogId':mblogId}, 
				function(data) {
					//alert(data);
					addFavoriteBtn(mblogId, divArea);
		});
	};

	// twit 팝업
	twitPop = function(){
		iKEP.showDialog({     
			title : "Microblogging",     
			url   : iKEP.getContextRoot() + "/socialpack/microblogging/getTweettingPage.do",     
			modal : true,
			width : 800,
			height: 205,
			scroll: 'no',
			callback : function(result) { tabs1Click(); }
		}); 
	};

	//retwit 팝업
	retwitPop = function(mblogId){
		iKEP.showDialog({     
			title : "Retweet",     
			url   : iKEP.getContextRoot() + "/socialpack/microblogging/getMblogRetwitForm.do?mblogId="+mblogId,     
			modal : true,
			width : 800,
			height: 205,
			scroll: 'no',
			callback : function(result) { popupCallback(); }
		}); 
	};

	// 답글 팝업
	replyPop = function(mblogId){
		iKEP.showDialog({     
			title : "Reply",     
			url   : iKEP.getContextRoot() + "/socialpack/microblogging/getMblogReplyForm.do?mblogId="+mblogId,     
			modal : true,
			width : 800,
			height: 260,
			scroll: 'no',
			callback : function(result) { popupCallback(); }
		}); 
	};

	// 그룹추가 팝업
	addGroupPop = function(mblogId){

		var url = iKEP.getContextRoot() + "/socialpack/microblogging/mbgroup/getGroupForm.do";
		
		iKEP.popupOpen(url, {width:650, height:298, scrollbar:'no'}, 'addGroupPop');

		setMenuLayerHide();
	};

	// Setting 팝업
	settingPop = function(){

		var url = iKEP.getContextRoot() + "/socialpack/microblogging/setting/getSettingForm.do";
		
		iKEP.popupOpen(url, {width:480, height:210, scrollbar:'no'}, 'settingPop');
	};

	// 게시글 관련정보 상세조회 layer 팝업
	detailsForTweetPop = function(mblogId){
		$jq.get(iKEP.getContextRoot() + "/socialpack/microblogging/getDetailsForTweet.do",
				{'mblogId':mblogId}, 
				function(data) {
					//alert(data);
					$jq("#microblog_layer_content").html(data);
		});
	};

	// 게시글 관련정보 상세조회 일반 팝업
	detailsForTweetPopup = function(mblogId){

		var pageURL = iKEP.getContextRoot() + "/socialpack/microblogging/getDetailsForTweet.do?mblogId=" + mblogId;
		
		window.open (pageURL, '', 'resizable=1, scrollbar=1, toolbar=1, menubar=1, width=560, height=800');
		
	};

	// 해당 Id가 userId(U)이면 상세조회,  groupId(G)이면 해당group으로 이동, 아니면(N) 작동없음.
	getObjectIdInfo = function(objectId, div){
		var mentionType= '';
		$jq.get(iKEP.getContextRoot() + "/socialpack/microblogging/getObjectIdType.do",
				{'objectId':objectId}, 
				function(data) {
					//iKEP.debug('1'+data);
					mentionType = $jq.trim(data);

					if('U' == mentionType){
						div.effect( "slide",  500, function() { detailForUserPop(objectId); });
					}else if('G' == mentionType){
						document.location.href = iKEP.getContextRoot() + "/socialpack/microblogging/groupHome.do?mbgroupId="+objectId;
					}
		});
	};
	
	
	// 사용자 관련정보 상세조회 layer 팝업
	detailForUserPop = function(userId){
		$jq.get(iKEP.getContextRoot() + "/socialpack/microblogging/getDetailsForUser.do",
				{'userId':userId}, 
				function(data) {
					//alert(data);
					$jq("#microblog_layer_content").html(data);
		});
	};

	// 사용자 관련정보 상세조회 일반 팝업
	detailForUserPopup = function(userId){

		var pageURL = iKEP.getContextRoot() + "/socialpack/microblogging/getDetailsForUser.do?userId=" + userId;
		
		window.open (pageURL, '', 'resizable=1, scrollbar=1, toolbar=1, menubar=1, width=450, height=800');
		
	};

	// 프로파일 페이지로 가기
	viewFullProfile = function(targetUserId){
		//alert(targetUserId);
		//var pageURL = iKEP.getContextRoot() + "/support/profile/getProfile.do?targetUserId=" + targetUserId;
		
		//window.open (pageURL, '_self', '');
		iKEP.goProfileMain(targetUserId); 
	};
	
	//feed가 아닐 때 시계이미지 안보이게 하기
	hideTimelineImage = function(){
		$jq("#timelineimage").hide();
	};

	//feed 일 때 시계이미지 보이게 하기
	showTimelineImage = function(){
		$jq("#timelineimage").show();
	};

	// 이미지 파일 addon 조회 관련 layer setting
	myAddonImageList = function(){
		$(".microblog_insertFile").hide();  // 보이는 경우 파일 레이어 숨김 
		
		var $target = $(".microblog_insertImg");
		var $imgBox = $(".microblog_write_check");
		var $insertFileImage = $("#insertImg");
		var linkWidth = $target.width()*0.68;
		var linkTop = $imgBox.position().top+$imgBox.height()+12;
		//var linkLeft = $(this).position().left-linkWidth;
		var linkLeft = $insertFileImage.position().left-linkWidth;
		$target.css({
			top:linkTop,
			left:linkLeft
		});
		getRecentFileList('myImageContent','2', $target);
		//$target.show();		
	};

	// 파일 addon 조회 관련 layer setting
	myAddonFileList = function(){	
		$(".microblog_insertImg").hide();  // 보이는 경우 이미지 레이어 숨김 
		
		var $target = $(".microblog_insertFile");
		var $imgBox = $(".microblog_write_check");
		var $insertFileImage = $("#insertFile");
		var linkWidth = $target.width()*0.68;
		var linkTop = $imgBox.position().top+$imgBox.height()+12;
		//var linkLeft = $(this).position().left-linkWidth;
		var linkLeft = $insertFileImage.position().left-linkWidth;
		$target.css({
			top:linkTop,
			left:linkLeft
		});
		getRecentFileList('myFileContent','3', $target);
		//$target.show();
	};
	
	// my image, my file list 감추기
	hideInsertMyFileDiv = function(){
		$(".microblog_insertImg").hide();  // 보이는 경우 이미지 레이어 숨김 
		$(".microblog_insertFile").hide();  // 보이는 경우 파일 레이어 숨김 

		//$('#myImageContent').css({height:"0px"});
		//$('#myFileContent' ).css({height:"0px"});
		
		dialogWindowReduce();
	};
	
	// microblog_layer_content 감추기
	hideMicroblog_layer_content = function(nowDiv){
		//alert("hideMicroblog_layer_content s");
		$("#microblog_layer").fadeOut("slow");
		$("#microblog_layer_content").empty();
		if(1 < $jq("li", "#"+nowDiv).length)
		{
			$("ul[name=microblog_list] > li", "#"+nowDiv).siblings(".selected").removeClass("selected");
		}else if(1 == $jq("li", "#"+nowDiv).length){
			$("ul[name=microblog_list] > li", "#"+nowDiv).removeClass("selected");
		}
		//alert("hideMicroblog_layer_content e");
	};
	
	// 자신이 올린 최신 파일 리스트 조회
	getRecentFileList = function(divArea, addonType, target){
		//alert(divArea);
		//alert(addonType);
		$jq.get(iKEP.getContextRoot() + "/socialpack/microblogging/getRecentFileListByAddonType.do", 
				// addonType
				{'addonType':addonType}, 
				function(data) {
					//alert(data);
					// 검색 데이터가 있으면 
					if("" != $jq.trim(data))
					{						
						$jq("ul", "#"+divArea).empty().append(data);
						target.show();

						dialogWindowExpand();
					}
					
		});
	};
	
	//선택한 addon을 contents에 추가한다.
	setAddontoContent = function (addon){
		//alert(addon);
		document.getElementById("contents").value = document.getElementById("contents").value +addon;
		
		hideInsertMyFileDiv();
	};
	
	// 그룹에 속한 멤버 리스트 조회
	getMemberList = function(standardType, mbgroupId, nowDiv){
		var standardUserId = '';
		try{
			if("pre" == standardType){
				standardUserId = $jq("li", "#"+nowDiv).last().attr("id").replace('userUserId_','');
			}else{
				standardUserId = $jq("li", "#"+nowDiv).first().attr("id").replace('userUserId_','');
			}
		}catch(e){}
		//alert(standardUserId);		
		//alert(nowDiv);
		$jq.get(iKEP.getContextRoot() + "/socialpack/microblogging/mbgroupMember/mbgroupMemberUserList.do", 
				// groupId				,조회해온 데이터를 앞/뒤에 붙일건지, 	조회기준 UserId
				{'mbgroupId':mbgroupId, 'standardType':standardType, 'standardUserId':standardUserId}, 
				function(data) {
					//alert(data);
					if("pre" == standardType){
						$jq("ul", "#"+nowDiv).append(data);
					}else{
						$jq("ul", "#"+nowDiv).prepend(data);
					}
		});
	};
		
	// 오른쪽 검색필드에서 검색하기
	searchByInputWord = function(viewDiv, groupOrPrivate, mbgroupId)
	{
		searchByWord(document.searchForm.searchString.value, viewDiv, groupOrPrivate, mbgroupId);
	};

	// 검색어로 검색하기
	searchByWord = function(searchString, viewDiv, groupOrPrivate, mbgroupId){

		nowDiv = viewDiv;
		
		$jq("ul", "#tabs2-1").empty();
		$jq("ul", "#tabs2-2").empty();
		
		//왼쪽 리스트div는 안보이고, 그 위치에 검색리스트 div 보이게 해야한다.
		$jq("#leftDiv1").hide();
		$jq("#leftDiv2").show();
		$("#divTab2_s").tabs("select",0);
		

		$jq('#resultSearchString').html(searchString);

		if("" != searchString){
			$jq.post(iKEP.getContextRoot() + "/socialpack/microblogging/search/createSearch.do", 
					{'searchWord':searchString}, 
					function(data) {
						//alert(data);
						$jq('#searchId').val(data);
						searchTweet('pre',searchString,nowDiv,groupOrPrivate,mbgroupId);
			});
		}
	};
	
	// 검색해서 tweet list 볼 때.
	searchTweet = function(standardType,searchString,nowDiv,groupOrPrivate,mbgroupId){
		//alert(searchString);
		//alert(standardType);
		//alert(nowDiv);
		//alert(groupOrPrivate);
		var standardMblogId = '';
		try{
			if("pre" == standardType){
				standardMblogId = $jq("li", "#"+nowDiv).last().attr("id").replace('timelineMblogId_','');
			}else{
				standardMblogId = $jq("li", "#"+nowDiv).first().attr("id").replace('timelineMblogId_','');
			}
		}catch(e){}
		//alert(standardMblogId);
		
		var url = "";
		if('G' == groupOrPrivate){
			url = iKEP.getContextRoot() + "/socialpack/microblogging/searchGroupTweets.do";
		}else{
			url = iKEP.getContextRoot() + "/socialpack/microblogging/searchTweets.do";
		}
		//alert(url);

		// 로딩 이미지 표현할 Div 영역 설정
		addTempLoadingDiv();
		
		$jq.get(url, 
				// 검색어,			등록자것만 보는지 여부,   조회해온 데이터를 앞/뒤에 붙일건지, 	조회기준 MblogId
				{'searchString':searchString, 'standardType':standardType, 'standardMblogId':standardMblogId, 'mbgroupId':mbgroupId}, 
				function(data) {
					if("" != data){
						data = $jq.trim(data);
					}
					if("" == data && "" == $jq.trim($jq("ul", "#"+nowDiv).html())){
						data = "<li>No Results</li>";
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

	// 검색해서 user list 볼 때.
	getSearchUser = function(standardType,searchString,nowDiv,groupOrPrivate,mbgroupId){
		//alert(searchString);
		//alert(standardType);
		//alert(groupOrPrivate);
		var standardUserId = '';
		try{
			if("pre" == standardType){
				standardUserId = $jq("li", "#"+nowDiv).last().attr("id").replace('userUserId_','');
			}else{
				standardUserId = $jq("li", "#"+nowDiv).first().attr("id").replace('userUserId_','');
			}
		}catch(e){}
		//alert(standardUserId);
		//alert('${ownerId}');
		var url = "";
		if('G' == groupOrPrivate){
			url = iKEP.getContextRoot() + "/socialpack/microblogging/searchGroupPeople.do";
		}else{
			url = iKEP.getContextRoot() + "/socialpack/microblogging/searchPeople.do";
		}
		//alert(url);

		// 로딩 이미지 표현할 Div 영역 설정
		addTempLoadingDiv();
				
		$jq.get(url, 
				// 검색어,			 		    조회해온 데이터를 앞/뒤에 붙일건지, 	조회기준 UserId
				{'searchString':searchString, 'standardType':standardType, 'standardUserId':standardUserId, 'mbgroupId':mbgroupId}, 
				function(data) {
					if("" != data){
						data = $jq.trim(data);
					}
					if("" == data && "" == $jq.trim($jq("ul", "#"+nowDiv).html())){
						data = "<li>No Results</li>";
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
	
	// 검색어 저장
	saveSearchWord = function(){
		//alert($jq('#resultSearchString').html());
		//alert($jq('#searchId').val());
		searchString = $jq('#resultSearchString').html();
		//searchId = $jq('#searchId').val();
		$jq.post(iKEP.getContextRoot() + "/socialpack/microblogging/search/updateSearch.do", 
				// 검색 ID,			 검색어 저장 여부(0: 저장안됨,1: 저장됨)
				{'searchId':$jq('#searchId').val(), 'searchWord':searchString, 'isSave':1}, 
				function(data) {
					alert(data);
		});
	};

	// 검색어 삭제
	removeSearchWord = function(searchIdForRemove){
		//alert(searchIdForRemove);
		$jq.post(iKEP.getContextRoot() + "/socialpack/microblogging/search/updateSearch.do", 
				// 검색 ID,			 검색어 저장 여부(0: 저장안됨,1: 저장됨)
				{'searchId':searchIdForRemove, 'isSave':0}, 
				function(data) {
					//alert(data);
					// 해당 row를 지운다
					$jq("#searchSearchId_"+searchIdForRemove).remove();
		});
	};

	// 저장된 검색어 list 
	getSavedSearchWordList = function(divArea, userId){
		//alert(divArea);
		//alert(userId);
		$jq.get(iKEP.getContextRoot() + "/socialpack/microblogging/search/listBySearchUserId.do", 
				// 블로그 주인
				{'searchUserId':userId}, 
				function(data) {
					//alert(data);
						$jq("ul", "#"+divArea).empty().append(data);
		});
	};

	//그룹초대 승낙하기
	acceptInvite = function(mbgroupId){
		$jq.post(iKEP.getContextRoot() + "/socialpack/microblogging/mbgroupMember/updateMbgroupMember.do", 
				{'mbgroupId':mbgroupId,'status':1}, 
				function(data) {
					//alert(data);
					//해당 ROW를 안보이게 한다.
					$jq('#inviting_'+mbgroupId).hide();
		});
	};
	
	// 그룹초대 거부하기
	denyInvite = function(mbgroupId){
		$jq.post(iKEP.getContextRoot() + "/socialpack/microblogging/mbgroupMember/rejectMbgroupMember.do", 
				{'mbgroupId':mbgroupId}, 
				function(data) {
					//alert(data);
					//해당 ROW를 안보이게 한다.
					$jq('#inviting_'+mbgroupId).hide();
		});
	};

	// Following 추가 버튼 생성
	addFollowBtn = function(followingUserId, divArea) {
		var str = "<a class='button_pr' href=\"javascript:follow('"+followingUserId+"','"+divArea+"')\">";
		str = str + "<span><img src='" + iKEP.getContextRoot() +"/base/images/icon/ic_plus.gif' alt='' />";
		str = str + "follow</span>";
		str = str + "</a>";
		//alert(str);
		$jq("#"+divArea).html(str);
		$jq("span[name="+divArea+"]").html(str);
		
	};
		
	// Following 삭제 버튼 생성
	delFollowBtn = function(followingUserId, divArea) {
		var str = "<a class='button_follow' href=\"javascript:unfollow('"+followingUserId+"','"+divArea+"')\">";
		str = str + "<span>unfollow</span>";
		str = str + "</a>";
		//alert(str);
		$jq("#"+divArea).html(str);
		$jq("span[name="+divArea+"]").html(str);
	};
		
	// follow하기
	follow = function(userId, divArea){
		//alert(userId);
		$jq.post(iKEP.getContextRoot() + "/socialpack/microblogging/follow/createFollow.do", 
				{'followingUserId':userId}, 
				function(data) {
					//alert(data);
					delFollowBtn(userId, divArea);
		});
	};

	// follow 취소하기
	unfollow = function(userId, divArea){
		//alert(userId);
		$jq.post(iKEP.getContextRoot() + "/socialpack/microblogging/follow/removeFollow.do", 
				{'followingUserId':userId}, 
				function(data) {
					//alert(data);
					// 해당 ROW를 안보이게 한다.
					$jq('#userUserId_'+userId).hide();
					addFollowBtn(userId, divArea);
		});
	};
	
	
	
	//*******************************************************************************
	//***************************** 파일업로드 ****************************************
	//*******************************************************************************

	// 파일을 다운로드한다. 공통에서 URL 만들면 바꾼다.
	downloadFilePop = function(fileId) {
		var pageURL = iKEP.getContextRoot() + "/support/fileupload/downloadFile.do?fileId=" + fileId;
		window.open (pageURL, '', 'fullscreen=yes, resizable=yes, scrollbar=yes, toolbar=yes, menubar=yes');
	};

    //업로드완료후 fileId 리턴
	afterFileUpload = function(status, fileId, fileName, message, gubun) {
		if(status == 'success') 
		{
			if("imageupload" == gubun)
			{
				saveFileToAddon(fileId,'2');
				document.getElementById("contents").focus();
			}
			else
			{
				saveFileToAddon(fileId,'3');
				document.getElementById("contents").focus();
			}
		}
		else {
			alert('upload failed..');
		}		
	};

	// 로딩이미지 표시할 임시영역 생성
	addTempLoadingDiv = function (){
		var divArea = "<div id='tempLoadingDiv'></div>";
		$jq("#"+nowDiv).append(divArea);
		$("#tempLoadingDiv").ajaxLoadStart("container");
	};

	// 로딩이미지 표시한 임시영역 제거
	removeTempLoadingDiv = function (){
		windowScrollFlag = false; 
		$("#tempLoadingDiv").ajaxLoadComplete();
		$jq('#tempLoadingDiv').remove();
	};
	
	//*******************************************************************************
	//***************************** 투표 생성 팝업  *************************************
	//*******************************************************************************

	//투표 관련정보 부가정보 테이블에 저장하고 입력창에서 값 변경하기
	setPollId = function(pollId) {
		//alert(pollId);
		if("" != pollId){
			$jq.post(iKEP.getContextRoot() + "/socialpack/microblogging/addon/createAddon.do", 
					{'addonType':'1','sourceLink':pollId}, 
					function(data) {
						//alert(data);
						document.getElementById("contents").value = document.getElementById("contents").value +data;
						document.getElementById("contents").focus();
			});
		}
	};

	//투표 보기 팝업
	detailPollPop = function (pollId){
		var pageURL = iKEP.getContextRoot() + "/support/poll/viewPoll.do?pollId=" + pollId + "&viewMode=apply&docPopup=true";
		iKEP.popupOpen(pageURL, {width:580, height:350}, "pollPopup");
		
		//var pageURL = iKEP.getContextRoot() + "/support/poll/viewPoll.do?pollId=" + pollId;
		//	window.open (pageURL, '', 'width=580,height=230,resizable=no, scrollbar=no, toolbar=no, menubar=no');
	};
	
	
	
	
	
	
	//*******************************************************************************
	//***************************** 외부 SNS 인증관련 **********************************
	//*******************************************************************************
	authTwitterPopup = function(titterAuthCode) {
		//${authenticationURL}
		iKEP.popupOpen(iKEP.getContextRoot() + "/support/externalSNS/TwitterAuth.do", {width:820, height:450, resizable:true, callback : function(result) {
			//alert("callback : popup");
			userInfoReset();
		}});
	}; 
	
	authFacebookPopup = function(facebookAuthCode) {
		iKEP.popupOpen(iKEP.getContextRoot() + "/support/externalSNS/FacebookMain.do", {width:1000, height:650, resizable:true, callback : function(result) {
			//alert("callback : popup");
			userInfoReset();
		}});
	}; 
	
	// 외부 sns 인증후 인증정보를 화면에 다시 셋팅한다.
	userInfoReset =  function () {
		$jq.get(iKEP.getContextRoot() + "/socialpack/microblogging/getSnsAuthInfo.do", 
				{}, 
				function(data) {
					//alert(data);
						document.getElementById("snsAuthInfo").innerHTML = data;
		});
	};
	
	
})(jQuery); 	