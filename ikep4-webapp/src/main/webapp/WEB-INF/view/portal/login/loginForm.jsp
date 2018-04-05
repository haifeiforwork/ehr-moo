<%--
=====================================================
* 기능 설명 : 스프링 시큐리트의 커스텀 로그인 페이지 샘플
* 작성자 : 주길재
=====================================================
--%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<c:set var="prefix" value="message.portal.login.loginForm"/>

<link rel="stylesheet" type="text/css" href="${contextRoot}/base/css/newlogin.css" />

<script type="text/javascript">
/* ====================================================================================== 
	* 인증이 통과되지 않았을 경우, 에러 메시지 처리 예시
    =======================================================================================*/
var goLogin,
	$form;
	

var dialog_100000881573;

var popupLeft = 50;
var popupWidth = 0;
var popupTop = 50;
var popupWidthInterval = 25;

var layerLeft = 50;
var layerTop = 100;
var layerWidth = 0;
var layerHeight = 0;
var layerWidthInterval = 25;
var layerTopInterval = 47;

	
(function($) {
	
	goLogin = function() {
		var name = $("#j_username").val();
		var password = $("#password").val();

		if(name == "") {
			alert('<ikep4j:message pre="${prefix}" key="alert.idEmpty" />');
			$("#j_username").trigger("focus");
			return false;
		}
		
		if(password == "") {
			alert('<ikep4j:message pre="${prefix}" key="alert.idEmpty" />');
			$("#password").trigger("focus");
			return false;
		}

		var domain = document.domain;
		//Cookie Setting 30days
		var option = {
			domain: domain,
       		expires: 60*60*24*30,
       		path: '/'
        }
		
		if($("#idSaveCheck").is(":checked")) {
			$.cookie("IKEP_LOGIN_SAVEID", name, option);
		} else {
			$.cookie("IKEP_LOGIN_SAVEID", "", option);
		}
		
		$("#j_password").val(password);
		
		return true;
	};
	

    dialogClose = function(id) {
    	eval("dialog_" + id).close();
    }
	
	
	$(document).ready(function() {
		
		/* 오픈팝업 막음.
		if($jq.cookie("IKEP_POPUP_100000881573") != 'none') {
			layerWidth = 585;
			layerHeight = parseInt(735) + parseInt(layerTopInterval);
			
			dialog_100000881573 = iKEP.showDialog({
				title: '신규 EP시스템 로그인 안내',
				url: '<c:url value="/portal/admin/screen/popup/mainPopup.do?popupId=100000881573"/>',
				width: layerWidth,
				height: layerHeight,
				modal: false,
				scroll: "no",
				resizable:false,
				position : [layerLeft, layerTop]
			});

		}
		*/
	
		$form = $("#loginForm");
		
		<c:if test="${!empty param.error}">
			<c:choose>
				<c:when test = "${param.error eq '2'}">
				alert("<ikep4j:message pre="${prefix}" key="alert.sessionExpired" />");
				if(opener)
				{
					opener.top.window.location.href='${contextRoot}/loginForm.do';
					self.close();
				} else {
					top.window.location.href='${contextRoot}/loginForm.do';
				}
				</c:when>
				<c:otherwise>
					alert("<ikep4j:message pre="${prefix}" key="alert.idCheck" />");
					$("#blockLogin").show();
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${empty param.error}">
			$("#blockLogin").show();
		</c:if>
		
		$("input[name=j_username]").val($jq.cookie("IKEP_LOGIN_SAVEID"));
		
		var saveId = $jq.cookie("IKEP_LOGIN_SAVEID");
		
		if(saveId !=null && saveId != ''){
			$("#idSaveCheck").attr("checked", "true");
		}

		passwordSearchForm =function() {
			
			var url = '<c:url value="/passwordSearchForm.do"/>';

			iKEP.popupOpen(url, {width:450, height:300}, "passwordSearchForm");
			
		};

		
		$("#btnSubmit").click(function() {
			$form.submit();
		});
		
		$("#j_username").keypress( function(event) { 
			if(event.which == 13) {
				$form.submit();
			}
		});
		
		$("#password").keypress( function(event) {
			if(event.which == 13) {
				$form.submit();
			} else {
				if((event.which >= 65 && event.which <= 90) && !event.shiftKey) { 
					fnShowCapsLockMsg();
				} else {
					fnHiddenCapsLockMsg();
				}
			}
		});
		
		$("#password").keydown( function(event) { 
			 if(event.which == 20)
			  fnHiddenCapsLockMsg();
		});
		
		
		(function() {	// setting label
			function setClone(el) {
				var $el = $(el);
				var $container = $el.parent();
				switch($container.css("position").toLowerCase()) {
					case "absolute" :
					case "relative" :
						break;
					default : $container.css("position", "relative");
				}
				var position = $el.position();
				var $clone = $("#j_username").clone()
						.removeAttr("id")
						.val($el.attr("title"))
						.attr("title", $el.attr("title"))
						.css({position:"absolute", top:position.top+"px", left:position.left+"px"})
						.focusin(function() {
							$(this).remove();
							setTimeout(function() { $el.focus(); }, 0);
						});
				$container.append($clone);
				
				$el.focusin(function() { $clone.trigger("focus"); });
			}
			
			if(!$.trim($("#j_username").val())) setClone("#j_username");
			if(!$.trim($("#password").val())) setClone("#password");
		})();
		
		
	
		
		
	});
	
})(jQuery);

function fnShowCapsLockMsg()
{
 var divCapsLock = document.getElementById("capslockInfo");
 divCapsLock.style.display = "block";
}

function fnHiddenCapsLockMsg()
{
 var divCapsLock = document.getElementById("capslockInfo");
 divCapsLock.style.display = "none";
}

function goIntroInfo(){
	
	iKEP.popupOpen("<c:url value='/base/common/intro_popup.jsp'/>", {width:420,height:400},"preparation");
		
}

function goPasswordSearch(){
	
	iKEP.popupOpen("<c:url value='/base/common/info.jsp'/>", {width:420,height:300},"passwordSearch");
		
}


</script>

<!-- ====================================================================================== 
	* 폼에 POST를 사용하지 않으면, UsernamePasswordAuthenticationFilter에 의해서 로그인 요청이 거부됨.
    ======================================================================================= -->
<form name="loginForm" id="loginForm" action="${contextRoot}/login.do" method="post" onsubmit="return goLogin()">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>NEONET</title>
</head>

<body>
  <div class="login_wrap">
    <dl class="login_box">
      <dt>
        <img src="${contextRoot}/base/images/login/newlogin_logo.png" alt="neonet logo" />
      </dt>
      <dd>
        <span class="login_text">
          무림은 건강한 욕심으로 품격과 집념을 지켜 나갑니다.
        </span>
        <ul class="login_input">
          <li>
            <span class="label_id">ID</span>
            <input name="j_username" id="j_username" type="text" style="ime-mode:inactive" title="메일 아이디" value='<c:if test="${!empty param.error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>' size="16" />
          	<input name="j_password" id="j_password" type="hidden" />
          </li>
          <li>
            <span class="label_pw">PASSWORD</span>
            <input name="password" id="password" type="password" title="패스워드" size="16" />
            <div style="position:absolute; top: 58px; left: 95px;" class="none" id="capslockInfo"><img alt="" src="${contextRoot}/base/images/icon/ic_capslock01.gif"></div>
          </li>
          <li>
            <span class="id_check">
              <input id="idSaveCheck" type="checkbox" />
              <label for="id_checkbox">아이디 저장</label>
            </span>
            <a id="goIntroInfo" href="javascript:passwordSearchForm();" class="pw_check">비밀번호 찾기</a>
          </li>
          <li>
            <a href="#" id="btnSubmit" class="login_btn">Login</a>
          </li>
        </ul>
      </dd>
    </dl>
  </div>
</body>
</html>
</form>