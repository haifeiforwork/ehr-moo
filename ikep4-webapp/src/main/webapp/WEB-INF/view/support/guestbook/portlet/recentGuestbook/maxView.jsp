<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" /> 
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<% 
	pageContext.setAttribute("lf", "\n"); 
%>

<script type="text/javascript" src="<c:url value='/base/js/units/support/guestbook/guestbook.js'/>"></script>
<script type="text/javascript">
<!--

(function($) {

	var validatorPool = [];
	var validOptionsGuestbook = { 
		rules : {
			contents : {
				required : true,
				maxlength : 150
			}
		},
		messages : {
			contents : {
				required : "<ikep4j:message key='NotNull.guestbook.contents' />",
				maxlength : "<ikep4j:message key='Size.guestbook.contents' />"
			}
		},
		notice : {
			contents : {
				message : "<ikep4j:message key='Size.guestbook.contents' />"
			}
	    },
		submitHandler : function(form) {

			var guestbookType = $.data(form, "guestbookType");
			
			if ( guestbookType == 'GUESTBOOK'){
				$jq.ajax({
				    url : "<c:url value='/support/guestbook/createGuestbook.do'/>" ,
				    data : $(form).serialize(),
				    type : "post",
				    success : function(result) {
				    	viewPortletGuestbookList();
				    	//parent.profileResize();
				    },
				    error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						var validatorId = $.data(form, "validatorId")-1;
						var validator = validatorPool[validatorId];
						validator.showErrors(errorItems);
					}
				});
			}else if( guestbookType == 'LINEREPLY'){
				$jq.ajax({
				    url : "<c:url value='/support/guestbook/createGuestbookLineReply.do'/>" ,
				    data : $(form).serialize(),
				    type : "post",
				    success : function(result) {
				    	var grougId = $(form).find('input[name=groupId]:hidden').val();				    	
				    	Guestbook.viewGuestbookByGroupId(grougId);
				    	//parent.profileResize();
				    },
				    error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						var validatorId = $.data(form, "validatorId")-1;
						var validator = validatorPool[validatorId];
						validator.showErrors(errorItems);
					}
				});
			}
			
		}
	}
	
	viewPortletGuestbookList = function() {
		
		$jq.ajax({
		    url : "<c:url value='/support/guestbook/portlet/recentGuestbook/maxGuestBookListView.do'/>",
		    data : $jq('#searchForm').serialize(),
		    type : "post",
		    success : function(result) {
		    	$jq("#guestbookList").html(result);
		    }
		});
		
		//$jq('#searchForm').submit();
	}; 
	
	inputGuestbookLineReplyValid = function(guestbookId,btn,indentation) {
		
		var $gbLineReplyInputView = "";
		if( indentation == '1' ){
			$gbLineReplyInputView = $jq(btn).parents("div.guestbook_c:first").find(".gbLineReplyInputView:first");
		}else{
			$gbLineReplyInputView = $jq(btn).parents("div.blockComment_re:first").find(".gbLineReplyInputView:first");
		}

		var $rebtnspan = "";
		if( indentation == '1' ){
			$rebtnspan = $jq(btn).parents("span.rebtn:first").find(".ic_reply:first");
		}else{
			$rebtnspan = $jq(btn).parents("div.blockComment_re:first").find(".ic_reply:first");
		}
		$rebtnspan.hide();
		
		$jq.ajax({
		    url : "<c:url value='/support/guestbook/inputGuestbookLineReply.do'/>" ,
		    data : {'guestbookId':eval("'"+guestbookId+"'"),'indentation':eval("'"+indentation+"'")}, 
		    type : "get",
		    success : function(result) {
		    	$jq($gbLineReplyInputView).html(result);
		    	
		    	var $form = $("form", $gbLineReplyInputView);
		    	validatorPool.push(new iKEP.Validator($form, validOptionsGuestbook));
		    	$form.find(".textRight").children("ul").children("li:eq(0)").children("a:eq(0)").click(function(){
					$form.trigger("submit");
				});
		    	$.data($form[0], "guestbookType", "LINEREPLY");
		    	$.data($form[0], "validatorId", validatorPool.length);
		    	//iKEP.checkLength("#contents", updateCheck);
		    	
		    }
		});
	};
	
	gerLeftTreeArea = function() {
		$jq.ajax({
		    url : "<c:url value='/support/profile/getProfileOrgTree.do'/>",
		    data : {'targetUserId':'${targetUserId}'}, 
		    type : "get",
		    success : function(result) {
		    	$jq("#leftTreeArea").html(result);
		    }
		});
	}; 
	
	Guestbook.deleteGuestbook = function(guestbookId,btn) {
		
		var $rebtnspan = $jq(btn).parents("div.guestbook_c:first");
		if(confirm("<ikep4j:message pre='${preMsgGuestbook}' key='comment.delete'/>") == true){
			$jq.ajax({
			    url : "<c:url value='/support/guestbook/deleteGuestbook.do'/>",
			    data : {'guestbookId':eval("'"+guestbookId+"'"),'targetUserId':'${targetUserId}'}, 
			    type : "get",
			    success : function(result) {
			    	//$jq("#profileView").html(result);
			    	//$rebtnspan.remove();
			    	viewPortletGuestbookList();
			    	//parent.profileResize();
			    }
			});
		}
	};
	
	// 개별 건만 지울때 사용
	Guestbook.deleteGuestbookLineReply = function(linereplyId,btn) {
		
		var $blockComment_re = $jq(btn).parents("div.blockComment_re:first");		
		if(confirm("<ikep4j:message pre='${preMsgGuestbook}' key='comment.delete'/>") == true){
			$jq.ajax({
			    url : "<c:url value='/support/guestbook/deleteGuestbook.do'/>",
			    data : {'guestbookId':eval("'"+linereplyId+"'"),'targetUserId':'${targetUserId}'}, 
			    type : "get",
			    success : function(result) {
			    	//$jq("#profileView").html(result);
			    	$blockComment_re.remove();
			    	//parent.profileResize();
			    }
			});
		}
	};
	
	// 개별건 지우고 방명록 댓글 재 사용 위해 재 호출
	Guestbook.deleteGuestbookLineReplyByGroupId = function(linereplyId,groupId) {
		
		if(confirm("<ikep4j:message pre='${preMsgGuestbook}' key='comment.delete'/>") == true){
			$jq.ajax({
			    url : "<c:url value='/support/guestbook/deleteGuestbook.do'/>",
			    data : {'guestbookId':eval("'"+linereplyId+"'"),'targetUserId':'${targetUserId}'}, 
			    type : "get",
			    success : function(result) {
			    	Guestbook.viewGuestbookByGroupId(groupId);
			    	//parent.profileResize();
			    }
			});
		}
	};
	
	
	$jq(document).ready(function() {
		viewPortletGuestbookList();
	});
	
})(jQuery);  
//-->
</script>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<!--mainContents_4 Start-->
<div id="mainContents_4">
	<h1 class="none"><ikep4j:message pre='${preProfileMain}' key='profile.contentsArea'/></h1>
	
	<!--pr_sub Start-->
	<div class="pr_sub">
		<div id="guestbookList">
			<form id="searchForm" method="post" onsubmit="return false"> 
				<input name="portletConfigId" type="hidden" value="${portletConfigId}" />
				<input name="portletId" type="hidden" value="${portletId}" />
				<input name="pageIndex" type="hidden" value="${pageIndex}" />
			</form>
		</div>
	</div>
	<!--//pr_sub End-->
	
</div>
<!--//mainContents_4 End-->