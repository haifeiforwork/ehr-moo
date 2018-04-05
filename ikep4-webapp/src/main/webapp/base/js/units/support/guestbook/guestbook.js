/**
 * Guestbook 공통 Javascript guestbook.js
 */

	var Guestbook = new (function() {
		
		var _contextRoot = window["contextRoot"] || "";
		this.getContextRoot = function () { return _contextRoot; };
		
	})();
	
	Guestbook.viewGuestbook = function(guestbookId) {
		$jq.ajax({
		    url : this.getContextRoot() + "/support/guestbook/readGuestbook.do",
		    data : {'guestbookId':eval("'"+guestbookId+"'")}, 
		    type : "get",
		    success : function(result) {
		    	$jq("#prependGuestbookview").prepend(result);
		    }
		});
	}; 
	
	/*
	Guestbook.submitGuestbook = function(viewType) {
		
		$jq.ajax({
		    url : this.getContextRoot() + "/support/guestbook/createGuestbook.do",
		    data : $jq('form[name=guestbookForm]').serialize(),
		    type : "post",
		    success : function(result) {
		    	if( viewType == 'PF' ){
		    		getGuestbookList();
		    	}else{
		    		Guestbook.viewGuestbookList();
		    	}
		    }
		});
	}; 
	*/
	
	Guestbook.viewGuestbookLineReply = function(guestbookId,btn) {

		var $prependGbLineReplyview = $jq(btn).parents("div.summaryViewConTest:first").find(".prependGbLineReplyview:first");

		$jq.ajax({
		    url : this.getContextRoot() + "/support/guestbook/readGuestbookLineReply.do",
		    data : {'guestbookId':eval("'"+guestbookId+"'")}, 
		    type : "get",
		    success : function(result) {
		    	$jq($prependGbLineReplyview).prepend(result);
		    }
		});
	}; 
	

	/*
	 * Validation 적용 위해 주석 처리
	// 방명록을 개별 건별로 저장해서 Row 추가 하기 위한 메서드 
	Guestbook.submitGuestbookLineReply = function(btn,indentation) {
		
		$jq.ajax({
		    url : this.getContextRoot() + "/support/guestbook/createGuestbookLineReply.do",
		    data : $jq('form[name=gbLineReplyForm]').serialize(),
		    type : "post",
		    success : function(result) {
		    	Guestbook.viewGuestbookLineReply($jq.trim(result),btn);
		    	Guestbook.cancelGuestbookLineReply(btn,indentation);
		    }
		});
	};
	
	// 방명록 저장이 되면 같은 GroupId 를 가진 전체 조회 하기 위한 메서드
	Guestbook.submitGuestbookLineReplyByGroupId = function(grougId) {
		
		$jq.ajax({
		    url : this.getContextRoot() + "/support/guestbook/createGuestbookLineReply.do",
		    data : $jq('form[name=gbLineReplyForm]').serialize(),
		    type : "post",
		    success : function(result) {
		    	Guestbook.viewGuestbookByGroupId(grougId);
		    }
		});
	};
	*/
	
	
	// 방명록 저장이 되면 같은 GroupId 를 가진 전체 조회 하기 위한 메서드
	Guestbook.viewGuestbookByGroupId = function(guestbookId) {		
		$jq.ajax({
		    url : this.getContextRoot() + "/support/guestbook/readGuestbook.do",
		    data : {'guestbookId':eval("'"+guestbookId+"'")}, 
		    type : "get",
		    success : function(result) {
		    	$jq("#GUEST_" + guestbookId).html(result);
		    	iKEP.iFrameContentResize();
		    }
		});
	}; 
	

	/*
	Guestbook.inputGuestbook = function(targetUserId,viewType) {

		$jq("#guestbook_input").hide();
		$jq("#guestbook_cancel").show();
		$jq.ajax({
		    url : this.getContextRoot() +  "/support/guestbook/inputGuestbook.do",
		    data : {'targetUserId':targetUserId,'viewType':viewType }, 
		    type : "post",
		    success : function(result) {
		    	$jq("#guestbookInputView").html(result);
		    }
		});
	};
	*/
	
	Guestbook.cancelGuestbook = function() {
		$jq("#guestbook_input").show();
		$jq("#guestbook_cancel").hide();
		$jq("#guestbookInputView").empty();
	}; 
	
	/*
	Guestbook.inputGuestbookLineReply = function(guestbookId,btn,indentation) {
		
		var $gbLineReplyInputView = "";
		if( indentation == '1' ){
			$gbLineReplyInputView = $jq(btn).parents("div.guestbook_c:first").find(".gbLineReplyInputView:first");
		}else{
			$gbLineReplyInputView = $jq(btn).parents("div.blockComment_re:first").find(".gbLineReplyInputView:first");
		}

		var $rebtnspan = "";
		if( indentation == '1' ){
			$rebtnspan = $jq(btn).parents("span.rebtn:first").find(".replyBtn:first");
		}else{
			$rebtnspan = $jq(btn).parents("div.blockComment_re:first").find(".replyBtn:first");
		}
		$rebtnspan.hide();
		
		$jq.ajax({
		    url : this.getContextRoot() + "/support/guestbook/inputGuestbookLineReply.do",
		    data : {'guestbookId':eval("'"+guestbookId+"'"),'indentation':eval("'"+indentation+"'")}, 
		    type : "post",
		    success : function(result) {
		    	$jq($gbLineReplyInputView).html(result);
		    }
		});
	};
	*/
	
	Guestbook.cancelGuestbookLineReply = function(btn,indentation) {
		
		var $gbLineReplyInputView = $jq(btn).parents("div.summaryViewConTest:first").find(".gbLineReplyInputView:first");

		var $rebtnspan = "";

		if( indentation == '1' ){
			$rebtnspan = $jq(btn).parents("div.guestbook_c:first").find(".replyBtn:first");
		}else{
			$rebtnspan = $jq(btn).parents("div.blockComment_re:first").find(".replyBtn:first");
		}
		
		$rebtnspan.show();
		$jq($gbLineReplyInputView).empty();
	}; 
