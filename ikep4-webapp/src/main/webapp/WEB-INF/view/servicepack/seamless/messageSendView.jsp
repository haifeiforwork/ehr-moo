<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>
<%@ include file="/base/common/fileUploadControll.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preButton"  value="ui.servicepack.seamless.button" /> 
<c:set var="preMsg"  value="ui.servicepack.seamless.MSG" /> 
<c:set var="preDetail"  value="ui.servicepack.seamless.messageSendView.detail" />
<c:set var="preMessageType"  value="ui.servicepack.seamless.code.messageType" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<!--popup Start-->
<div id="popup">

	<!--popup_title Start-->
	<div id="popup_title_2">
        <div class="popup_bgTitle_l"></div>
		<h1>New Message</h1>
		<a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close'/></span></a>
	</div>
	<!--//popup_title End-->
	
	<!--popup_contents Start-->
	<div id="popup_contents" style="margin-top:30px;">
	
	<form id="messageForm" method="post" action="<c:url value='/servicepack/seamless/seamlessMessageSend.do' />"> 		
		<!--tableTop Start-->
		<div class="tableTop newMessage_check">
			<spring:bind path="messageBox.sendType">
				<c:forEach var="code" items="${messageCode.messageType}">
					<input type="checkbox" class="checkbox" name="${status.expression}" value="${code.key}" /><ikep4j:message pre='${preMessageType}' key='${code.value}'/> 
				</c:forEach>	
			</spring:bind>
			<span class="tdInstruction_top"><ikep4j:message pre='${preDetail}' key='sendInstruction'/></span>		
		</div>
		<!--//tableTop End-->

		<!--blockDetail Start-->
		<div class="blockDetail">
			<table summary="Message">
				<caption></caption>
				<tbody>
					<tr>
						<th scope="row" width="18%"><ikep4j:message pre='${preDetail}' key='receiver'/></th>
						<td>
							<input id="searchUser" name="searchUser" type="text" class="inputbox" title="inputbox"  value='' size="20" />
							<input type="text" style="display: none;" /> 
							<a id="searchUserBtn" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="" />Search</span></a>	
							<a id="btnAddrControl" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_address.gif' />" alt="" />Address</span></a>	
							<div id="AddrControlForm" style="padding-top:4px;">
								<select name="addrGroupList" id="addrGroupList" multiple="multiple" size="5" class="multi w70"><option value=""></option></select>	
								<a id="delMember" class="button_ic valign_bottom" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="" />Delete</span></a>
								<span class="totalNum_s">Total [<span id="selCount">0</span>/<span>${userMessageAdmin.maxReceiverCount}</span>]	</span>
								<spring:bind path="messageBox.receiverList">  
									<input type="hidden" id="receiverList" name="${status.expression}"/>
								</spring:bind>	
							</div>	
						</td>
					</tr>	
					<tr>
						<td colspan="2" scope="row"><div>
							<div class="tdInstruction_top"><ikep4j:message pre='${preDetail}' key='smsInstruction'/></div>
							<spring:bind path="messageBox.contents">  
								<textarea name="${status.expression}" class="w100" title="<ikep4j:message pre='${preDetail}' key='inputText'/>" cols="" rows="13"><c:if test="${messageBox != null}">${messageBox.contents}</c:if></textarea>
							</spring:bind></div>
						</td>
					</tr>	
					<tr>
						<td id="fileUploadArea" colspan="2"></td>
					</tr>
				</tbody>
			</table>			
			
		</div>
		<!--//blockDetail End-->																																			
		<div class="clear"></div>
		
		<!--blockButton Start-->
		<div class="blockButton" style="text-align:center"> 
			<ul> 
				<li><a id="messageSendButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='send'/></span></a></li>
				<li><a id="btnClose" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='close'/></span></a></li>
			</ul>
		</div>
	</form>
	</div>
	<!--//popup_contents End-->
	
	<!--popup_footer Start-->
	<div id="popup_footer"></div>
	<!--//popup_footer End-->
			
</div>
<!--//popup End-->


<%-- <script type="text/javascript" src="<c:url value='/base/js/jquery/plugins.pack.js'/>" ></script> --%>
<%-- <script type="text/javascript" src="<c:url value='/base/js/jquery/jquery.validate-1.8.min.js'/>" ></script> --%>
<%-- <script type="text/javascript" src="<c:url value='/base/js/survey/jquery.blockUI.js'/>"></script> --%>
<script type="text/javascript">
<!--
var sWidth = (screen.availWidth/2);
var sHeight = (screen.availHeight*2/3);
if (sWidth < 500) sWidth= 500;
if (sWidth > 800) sWidth= 800;
if (sHeight < 700) sHeight= 700;
if (sHeight > 800) sHeight= 800;
//window.resizeTo(sWidth,sHeight);

(function($) {
	var tplUserOption = $.template(null, '<option id="\${id}" value="\${id}/\${userName}" >\${userName} \${jobTitleName} \${teamName}</option>');
	var tplEmailOption = $.template(null, '<option id=\${emailId}" value="Mail/\${email}" >eMail/\${email}</option>');
	
    $jq(document).ready(function() {
    	var $selectAddrGroup = $("#addrGroupList").children().remove().end();

    	if("${returnMsg}"=="SAVE") {
    		alert("<ikep4j:message pre='${preMsg}' key='sendMessage'/>");
    		window.close();
    	}
    	if("${replyUser.userId}" != "")  {
			//$selectAddrGroup
			var userInfo = {
				type : "user",
				empNo : "",
				id : "${replyUser.userId}",
				name : "${replyUser.userName}",
				userName : "${replyUser.userName}",
				jobTitleName : "${replyUser.jobTitleName}",
				teamName : "${replyUser.teamName}",
				email : "${replyUser.mail}",
				mobile : "${replyUser.mobile}"
			};
			$.tmpl(tplUserOption, userInfo).appendTo($selectAddrGroup)
				.data("data", userInfo);
			
			$jq("#selCount").text($selectAddrGroup.children().length);
			//$selectAddrGroup.find("option").attr("selected",true);
			//$selectAddrGroup.click();
    	}
    	
    	<c:if test="${replyList != '[]' }">
	    	<c:forEach var="replyItem" items="${replyList}" varStatus = "status"> 
	    		<c:choose>
					<c:when test="${replyItem.userId == null || replyItem.userId eq ''}">
						var emailInfo = {type:"email", emailId:("${replyItem.mail}").replace('@','').replace('.',''), email:"${replyItem.mail}"};
					 	$.tmpl(tplEmailOption, emailInfo).appendTo($selectAddrGroup)
					 		.data("data", emailInfo);
					</c:when>
					<c:otherwise>
						var userInfo = {
							type : "user",
							empNo : "",
							id : "${replyItem.userId}",
							name : "${replyItem.userName}",
							userName : "${replyItem.userName}",
							jobTitleName : "${replyItem.jobTitleName}",
							teamName : "${replyItem.teamName}",
							email : "${replyItem.mail}",
							mobile : "${replyItem.mobile}"
						};
		    			$.tmpl(tplUserOption, userInfo).appendTo($selectAddrGroup)
		    				.data("data", userInfo);
		    		</c:otherwise>
	    		</c:choose>
	    	</c:forEach>
	    	$jq("#selCount").text($selectAddrGroup.children().length);
	    	//$selectAddrGroup.find("option").attr("selected",true);
	    	//$selectAddrGroup.click();
	    </c:if>
    	
    	$jq("#btnClose").click(function() {
    		window.close();
		});
    	
    	 
    	$jq('#btnAddrControl').click( function() {
    		var maxCnt = '${userMessageAdmin.maxReceiverCount}' - $selectAddrGroup.children().length;
    		iKEP.showAddressBook(function(data) {
    			var addedUsers = $.map($selectAddrGroup.children(), function(option) {
    				var data = $(option).data("data");
    				return data.type == "user" ? data.id : null;
    			});
    			
    			$jq.each(data, function() {
					if($.inArray(this.id, addedUsers) == -1) {
						$.tmpl(tplUserOption, this).appendTo($selectAddrGroup)
							.data("data", this);
					}
				});
    			
    			$jq("#selCount").text($selectAddrGroup.children().length);
    			//$selectAddrGroup.find("option").attr("selected",true);
    			//$selectAddrGroup.click();
        	}, "", {selectType:"user", selectMaxCnt:maxCnt, tabs:{common:1}});
		});
    	
		setUser = function(data) {
			if(data == "") {	// []
				var regMail =/^[_a-zA-Z0-9-]+@[\._a-zA-Z0-9-]+\.[a-zA-Z]+$/;
				var email = $jq("#searchUser").val();	// 사용자가 입력한 냉용 : 이메일
				if (regMail.test(email)) {	// 이메일 주소가 맞으면...
					var addedMails = $.map($selectAddrGroup.children(), function(option) {	// 이미 추가된 유저정보
						var data = $(option).data("data");
						return data.type == "email" ? data.email : null;  
					});
				
					if($.inArray(email, addedMails) == -1) {
						var emailInfo = {type:"email", emailId:email.replace('@','').replace('.',''), email:email};
						$.tmpl(tplEmailOption, emailInfo).appendTo($selectAddrGroup)
							.data("data", emailInfo);
						
						$jq("#searchUser").val("");
					}
				 } else {
					alert("<ikep4j:message pre='${preMsg}' key='noUser'/>");
				 }
			} else {
				var addedUsers = $.map($selectAddrGroup.children(), function(option) {	// 이미 추가된 유저정보
					var data = $(option).data("data");
					return data.type == "user" ? data.id : null;  
				});
				$jq.each(data, function() {
					if($.inArray(this.id, addedUsers) == -1) {
						$.tmpl(tplUserOption, this).appendTo($selectAddrGroup)
							.data("data", this);
						
						$jq("#searchUser").val("");
					}
				});
			}
			
			$jq("#selCount").text($selectAddrGroup.children().length);
			
			//$selectAddrGroup.find("option").attr("selected",true);
			//$selectAddrGroup.click();
		};
		
    	$jq("#delMember").click(function() {
    		$jq.each($selectAddrGroup.children("option:selected"), function() {
    			$jq(this).remove();
    		})
    		$jq("#selCount").text($selectAddrGroup.children().length);
		});
    	
    	//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
        $jq('#searchUser').keypress( function(event) {
		    //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchUser(event, "Y", setUser);
		});
		
		//검색버튼을 클릭했을때 이벤트 바인딩
		$jq('#searchUserBtn').click( function() {
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#searchUser').trigger("keypress");
		});
		
		$jq("#messageSendButton").click(function() {   
			
			if($jq("input[name=sendType]:checked").length == 0) {
				alert("<ikep4j:message pre='${preMsg}' key='valueSendType'/>");
				return false;
			}
			
 			var $sel = $jq("select[name=addrGroupList]");
 			if($sel.children().length <1) {
 				alert('<ikep4j:message pre='${preMsg}' key='valueReceive' />');
 				return;	
 			}
			
			$selectAddrGroup.children().attr("selected", true);
			$jq("#messageForm").trigger("submit");
    	}); 
		
		new iKEP.Validator("#messageForm", {
    		rules : {
    			//sendType : "required",
    			//addrGroupList : "required",
    			contents : "required"
    		},
    		messages : {
    			//sendType : "<ikep4j:message pre='${preMsg}' key='valueSendType'/>",
    			//addrGroupList : "<ikep4j:message pre='${preMsg}' key='valueReceive'/>",
    			contents : "<ikep4j:message pre='${preMsg}' key='valueContents'/>"
    		},
    		notice : {
    			//sendType : "<ikep4j:message pre='${preMsg}' key='valueSendType'/>",
    			//addrGroupList : "<ikep4j:message pre='${preMsg}' key='valueReceive'/>",
    			contents : "<ikep4j:message pre='${preMsg}' key='valueContents'/>"
    		},
    		submitHandler : function(form) {
    			var list = [];
        		$jq.each($selectAddrGroup.children(), function(index){
        			list[index] = $jq(this).val();
        		});
        		$jq("#receiverList").val(list);
        		
        		fileController.upload(function(isSuccess, files) {
    				if(isSuccess == true) {
    					$(form).ajaxLoadStart();
    					form.submit();
    				}
    			});
    		}
    	});
		
		var fileController = new iKEP.FileController("#messageForm", "#fileUploadArea", {
			<c:if test="${!empty(fileDataListJson)}">
				files : /* 파일 목록 */ ${fileDataListJson}, 
			</c:if>
			maxTotalSize :  /* 전체 파일 싸이즈 */  ${userMessageAdmin.maxAttachFilesize * 1048576} ,
			allowFileType :/* 업로드제한 파일확장자 */  "all",
			lang :  "en"
		});
		
     });
})(jQuery);  
//-->
</script>