(function($) {	
	
	
	showDatail = function(url, itemId, subId, title) {
		
		//url = iKEP.getContextRoot() + url;
		//iKEP.popupOpen(url, {width:800, height:600});
		url = iKEP.getContextRoot() + url + itemId + "#" + subId;
		var width = $(window).width()*0.8;
		var height = $(window).height()*0.8;
		
		parent.showMainFrameDialog(url, title, width, height);
	};
	
	showDatail2 = function(url, title) {
		
		url = iKEP.getContextRoot() + url;
		//iKEP.popupOpen(url, {width:800, height:600});
		
		var width = $(window).width()*0.8;
		var height = $(window).height()*0.8;
		
		parent.showMainFrameDialog(url, title, width, height);
	};
	
	// for Profile
	showDatailForProfile = function(url, itemId, subId, title) {
		
		//url = iKEP.getContextRoot() + url;
		//iKEP.popupOpen(url, {width:800, height:600});
		url = iKEP.getContextRoot() + url + itemId + "#" + subId;;
		var width = $(window).width()*0.8;
		var height = window.screen.height * 0.7;
		
		parent.parent.showMainFrameDialog(url, title, width, height);
	};
	
	showDatail2ForProfile = function(url, title) {
		
		url = iKEP.getContextRoot() + url;
		//iKEP.popupOpen(url, {width:800, height:600});
		
		var width = $(window).width()*0.8;
		var height = window.screen.height * 0.7;
		
		parent.parent.showMainFrameDialog(url, title, width, height);
	};
	
	sendMail = function(name, mail) {
		
		var nameList = [name];
		var emailList = [mail];
		iKEP.sendMailPop(nameList, emailList, "", "", "", "");	
	}
	
	sendMessage = function(userId) {
		
		var url = iKEP.getContextRoot() + "/support/message/messageNew.do?senderUserId="+userId;
		iKEP.popupOpen(url ,{width:800, height:600} );
	}
	
	addFollow = function(userId) {
		
		$jq.post(iKEP.getContextRoot()+ "/socialpack/microblogging/follow/createFollow.do", 
				{followingUserId:userId},		
				function(data) {
					
					var str = "<div class='btn_following'>";
					str = str + "<a class='button_follow' href=\"javascript:delFollow('"+userId+"')\">";
					str = str + "<span>Following</span>";
					str = str + "</a>";
					str = str + "</div>";
					$jq("#"+userId+"_follow").html(str);
		});
		
	};
	
	delFollow = function(userId) {
		
		$jq.post(iKEP.getContextRoot()+ "/socialpack/microblogging/follow/removeFollow.do", 
				{followingUserId:userId},		
				function(data) {
					
					var str = "<div class='btn_follow'>";
					str = str + "<a class='button_pr' href=\"javascript:addFollow('"+userId+"')\">";
					str = str + "<span><img src='" + iKEP.getContextRoot() +"/base/images/icon/ic_plus.gif' alt='' />";
					str = str + "Follow</span>";
					str = str + "</a>";
					str = str + "</div>";
					$jq("#"+userId+"_follow").html(str);
		});
		
	};
	
	addFavorite = function(userId, userName) {
		
		iKEP.addFavorite('PEOPLE','PF',userId, userName);
			
		var str = "<a class='ic_rt_favorite valign_middle select' href=\"javascript:delFavorite('"+userId+"','"+userName+"')\">";
		str = str + "</a>";
		
		$jq("#"+userId+"_favorite").html(str);
		
	};
	
	delFavorite = function(userId, userName) {
		
		iKEP.delFavorite('', userId);
					
		var str = "<a class='ic_rt_favorite valign_middle' href=\"javascript:addFavorite('"+userId+"','"+userName+"')\">";
		str = str + "</a>";

		$jq("#"+userId+"_favorite").html(str);
		
	};
	
	chkFavorite = function(userId) {
		
		var str = iKEP.chkFavorite(userId, setFavorite);
	};
	
	setFavorite = function(data) {
		alert(data.status);
	}
	
	getMore = function() {
		
		$jq("#pageIndex").val(eval($jq("#pageIndex").val())+1);
		
		getList(true);

	};
	
	setScroll = function() {
		
		var height = $jq(document).height() - $jq(window).height()
		$jq(window).scrollTop(height);

	};
	
	setMoreDiv = function() {
		
		var recordCount = eval($jq("#recordCount").val());
		var currentCount = eval($jq("#currentCount").val());
		
		if(currentCount == 0) {
			$jq("#emptyDiv").show();
		}
		else {
			$jq("#emptyDiv").hide();
		}
		
		if(recordCount < 10) {
			$jq("#moreDiv").hide();
		}
		else {
			$jq("#moreDiv").show();
		}
		
		if(currentCount == 10 && recordCount==0) {
			$jq("#emptyDiv").show();
		}

	};
	
	beforeSearch = function(msg) {
		if($jq("#searchWord").val() == msg) {
			$jq("#searchWord").val("");
		}
	}
	
	afterSearch = function(msg) {
		if($jq("#searchWord").val() == "") {
			$jq("#searchWord").val(msg);
		}
	}
	
	
})(jQuery); 
	
	
	