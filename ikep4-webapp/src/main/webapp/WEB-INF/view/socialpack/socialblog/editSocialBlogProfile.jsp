<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preWebCommon"  value="ui.socialpack.socialblog.common.webstandard" /> 
<c:set var="preButton"  value="ui.socialpack.socialblog.common.button" /> 

<script type="text/javascript" src="<c:url value="/base/js/units/socialpack/socialblog.js"/>"></script>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
<!--

(function($) {
	
	var validOptionsSBIntro = { 
			rules : {
				//introduction : {
				//	maxlength : 150
				//}
			},
			messages : {
				//introduction : {
				//	maxlength : "<ikep4j:message key='Size.socialBlog.introduction' />"
				//}
			},
			notice : {
				//introduction : {
				//	message : "<ikep4j:message key='Size.socialBlog.introduction' />"
				//}
		    },
			submitHandler : function(form) {
				$jq.ajax({
				    url : "<c:url value='/socialpack/socialblog/saveSocialBlogProfile.do'/>",
				    data : $jq('form[name=sbintroForm]').serialize(),
				    type : "post",
				    success : function(result) {
				    	SocialBlog.getBlogintroductionView('${socialBlog.ownerId}','view');
				    },
				    error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
			}
	}

	var validator = new iKEP.Validator("#sbintroForm", validOptionsSBIntro);
	
	$jq(document).ready(function() {
	
		$jq('#sbit_save_btn').click(function(){
			$("#sbintroForm").trigger("submit");
		});
		$jq('#sbit_edit_btn').click(function(){
			SocialBlog.getBlogintroductionView('${socialBlog.ownerId}','edit');
		});
		$jq('#sbit_cancel_btn').click(function(){
			SocialBlog.getBlogintroductionView('${socialBlog.ownerId}','view');
		});
	
	});
	
	/*
	submitBlogintroductionView = function() {
		
		$jq.post('<c:url value="/socialpack/socialblog/saveSocialBlogProfile.do"/>'
		, $jq('form[name=sbintroForm]').serialize())
        .success(function(data) {
        	SocialBlog.getBlogintroductionView('${socialBlog.ownerId}','view');
		})
        .error(function(event, request, settings) { alert("error"); });
		
	}; 
	*/
	
})(jQuery);  
//-->
</script>

							<c:if test="${socialBlog != null}">
							<c:if test="${editflag == 'view'}">
								<div class="borderFrame_p break-word">
									<p style="display:inline;" id="blogIntroduction"><c:out value="${socialBlog.introduction}" escapeXml="false" /></p>
									<c:if test="${blogOwnerId == user.userId}">
									<div class="edit"><a href="#a" id="sbit_edit_btn"><img src="<c:url value='/base/images/icon/ic_edit.gif' />" alt="edit" /></a></div>
									</c:if>
								</div>
								<div class="borderFrame_lt"></div>
								<div class="borderFrame_rt"></div>
								<div class="borderFrame_lb"></div>
								<div class="borderFrame_rb"></div>
							</c:if>
							<c:if test="${editflag == 'edit'}">
							
								<form name="sbintroForm" id="sbintroForm" action="" method="post" style="border: 1px solid white; padding: 0px;" onsubmit="submitBlogintroductionView();return false;">
								<input name="blogId" type="hidden" value="<c:out value='${socialBlog.blogId}'/>" />	
								<div class="mb10">
									<textarea name="introduction" rows="5" class="edit_textarea"><c:out value="${socialBlog.introduction}" /></textarea>
									<div class="textRight">
										<a id="sbit_save_btn" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a>
										<a id="sbit_cancel_btn" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a>																			
									</div>
								</div>
							
								</form>
								<div class="borderFrame_lt"></div>
								<div class="borderFrame_rt"></div>
								<div class="borderFrame_lb"></div>
								<div class="borderFrame_rb"></div>
							</c:if>
							</c:if>