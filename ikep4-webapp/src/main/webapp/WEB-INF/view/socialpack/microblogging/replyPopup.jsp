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
//<!--
var dialogWindow = null;
function fnCaller(param, dialog){
	dialogWindow = dialog;
}

(function($) {
	$jq(document).ready(function() {

		var str_ul = "<ul></ul>";	
		$("#divSuggest").html(str_ul);
		$("#myImageContent").html(str_ul);
		$("#myFileContent").html(str_ul);
		
    	// divSuggest 부분이 아닌 곳을 선택했을 때 감춰준다.
    	$("#divSuggest").click(function(event) {event.stopPropagation();});
        if(parent) $(parent.document.body).click(function() { divHide("divSuggest"); });
        $(document.body).click(function() { divHide("divSuggest"); });

		// tweet 길이 체크
		iKEP.checkLength("#contents", updateCheck);
		
		$jq('#contents').focusin();
		$jq('#contents').focus();

        // 로그인 사용자의 그룹 Div 보기
        $jq('#loginUserGroupView').click(function(event){
        	event.stopPropagation();
        	viewGroupDiv(event);
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
				if(checkValid())
				{
					//alert('submit!!');
					$.ajax({
						url : "<c:url value='/socialpack/microblogging/createMblog.do'/>",
						type : "post",
						data : $(form).originalSerialize(),
						success : function(data) {
							//alert(data);
							// 부모창 Timeline 새로 고치고 닫히기
							//dialogWindow.callback(['post','tabs-1']);
							dialogWindow.callback();
							dialogWindow.close();
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

	checkValid = function(){

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

	// 로그인한 사용자의 group list Div 보여주기
	viewGroupDiv = function(event){
		
	    var $div = $("#divSuggest");
	    //alert($div);
	    $div.hide();
		$div.css("left", $jq(event.target).position().left);
		$div.css("top", $jq(event.target).position().top + 20);
	    
	    getGroupList('${sessionUser.userId }',"divSuggest", '4');
	}

})(jQuery);
//-->
</script>
<div id="snsAuthInfo">
	<input type="hidden" id="twitterAccount" 	value = "${sessionUser.twitterAccount}" />
	<input type="hidden" id="twitterAuthCode" 	value = "${sessionUser.twitterAuthCode}" />
	<input type="hidden" id="facebookAuthCode" 	value = "${sessionUser.facebookAuthCode}" />
</div>

<!--트윗 쓰기 페이지  [S]-->
	<!--microblog_write Start-->
	<div class="microblog_write">
		<div class="corner_RoundBox05">
			
			<div class="microblog_write_list"  id="addonDiv">
				<span><a href="#a" id="loginUserGroupView"><img src="<c:url value='/base/images/common/microblog_sel.gif'/>" alt="공유범위" /></a></span>
				<ul>
					<li><a href="#a" onclick="shortUrl();"><ikep4j:message pre='${preButton}' key='shorturl' /></a></li>
					<li><a href="#a" id="imageupload"><ikep4j:message pre='${preButton}' key='imageupload'  /></a></li>
					<li><a href="#a" id="fileupload"><ikep4j:message pre='${preButton}' key='fileupload'  /></a></li>
					<li><a href="#a" id="pollAdd"><ikep4j:message pre='${preButton}' key='vote'  /></a></li>
					<li class="liLast"><a href="#a" id="teamMention"><ikep4j:message pre='${preButton}' key='teamMention'  /></a></li>
				</ul>
			</div>
				<form id="mblogForm" name="mblogForm" method="post"  action="">
					<input type ="hidden" name="mblogType" value = "1" />
					<input type ="hidden" name="parentMblogId" value = "${originMblog.mblogId}" /> 
					<input type ="hidden" name="threadId" value = "${originMblog.threadId}" />
					<input type ="hidden" name="mbgroupId" id="mbgroupId" value = "${originMblog.mbgroupId}" />
					<textarea id="contents" name="contents" title="<ikep4j:message pre='${preLabel}' key='contents' />" cols="" rows="3"  class="w100" >@${originMblog.registerId}</textarea>
					<!--autoComplete_layer Start-->
						<div id="divSuggest" class="none" style="position:absolute; z-index:100; background:white ; width:200px; height:200px; border:1px solid #999;">
						</div>
					<!--//microblog_layer End-->
					<div class="microblog_write_check">
						<span><label><input name="isRetweetAllowed" type="checkbox" title="<ikep4j:message pre='${preLabel}' key='isRetweetAllowed' />" class="checkbox" value="1" checked="checked" /> <img src="<c:url value='/base/images/icon/ic_retweet.gif'/>" alt="retweet" /><ikep4j:message pre='${preLabel}' key='isRetweetAllowed'  /></label></span>
						
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
			
			<div class="microblog_img">
				<a href="#a">
					<!-- //아이디에 해당하는 사진정보. -->
					<img src="<c:url value='${originMblog.profilePicturePath}' />" width="50" height="50" alt="" title="${originMblog.registerId}" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'"/>
				</a>
			</div>
			<div class="microblog_con">
				<span class="microblog_id"><a href="#a" ><c:out value="${originMblog.registerId}"/></a></span>
				<span class="microblog_name">				
					<c:choose>
						<c:when test="${sessionUser.localeCode == sessionPortal.defaultLocaleCode}">
							<c:out value="${originMblog.registerName}"/>
						</c:when>
						<c:otherwise>
							<c:out value="${originMblog.registerEnglishName}"/>
						</c:otherwise>
					</c:choose>
				</span>
				<div class="ic_micro_ar none"></div>
				<p><c:out value="${originMblog.contents}" escapeXml="false"/></p>
			</div>
			<div class="l_t_corner"></div>
			<div class="r_t_corner"></div>
			<div class="l_b_corner"></div>
			<div class="r_b_corner"></div>				
		</div>
	</div>
	<!--//microblog_write End-->