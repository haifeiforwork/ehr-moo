<%@ include file="/base/common/taglibs.jsp"%>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preLabel"    value="ui.support.sms.label" />
<c:set var="preMessage"    value="ui.support.sms.message" />
<c:set var="preButton"  value="ui.support.sms.button" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
<!--
$jq(function(){
	// Tabs
	$jq("#divTab_sms").tabs();

});

function ifrmHide() {
	$jq(".sms_r").children().show().eq(3).hide()
		.children().remove();
}
$jq(document).ready(function() {	
	<c:if test="${receiveUser.userStatus == 'T'}">
		alert("사용자 정보가 존재하지 않습니다.");
		window.close();
	</c:if>
	//사용자아이디 클릭후 팝업호출시(favorite) 클릭된 사용자의 이름,핸드폰번호,아이디를 수신자 정보로 셋팅
	var paramName = '${receiveUser.userName}';
	var paramMobile = filterDigit('${receiveUser.mobile}');
	var paramReceiverId = '${receiveUser.userId}';

	if(paramMobile!='') {
		$jq("#rd01").val(paramReceiverId);
		$jq("#rn01").text(paramName);
		$jq("#no01").text(paramMobile);
	}

	var senderPhoneno = replaceAll('${user.mobile}','-','');
	$jq("input[name=senderPhoneno]").val(senderPhoneno);

    $jq('#smsPhoneno').keypress( function(event) {
    	var smsPhoneno = $jq('#smsPhoneno').val();
    	if(event.which == '13') {
			if (isDigit(smsPhoneno)) {
				if (phoneNoChk(smsPhoneno)) {			
	    			appendPhone('','',smsPhoneno);
	    			$jq("#smsPhoneno").val('');
	    		}     		
	    	} else {
				iKEP.searchUser(event, "Y", setUser);
	    	}
    	}
	});	
	$jq('#sms_textarea').keyup( function(event) {
		chk_byte();
	});    
	$jq('#userSearchBtn').click( function() { 
		var phonenNo = $jq("#smsPhoneno").val();
		if ( phonenNo != '' && isHan(phonenNo) ) {
			$jq('#smsPhoneno').trigger("keypress");
		}
	});    
	//사용자 검색
	setUser = function(data) {
		var str = "";
		var userId = "";
		var userName = "";
		var jobTitleName = "";
		var teamName = "";
		var mail = "";
		var empNo = "";
		var mobile = "";
		
		var selectCheck;
		
		if(data.length > 0) {
			$jq(data).each(function(index) {
				selectCheck = true;

				if(selectCheck) {
					userId = data[index].id;
					userName = data[index].userName;
					jobTitleName = data[index].jobTitleName;
					teamName = data[index].teamName;
					mail = data[index].email;
					empNo = data[index].empNo;
					mobile = data[index].mobile;
										
					if ( mobile == '' || mobile == null ) {
						alert("<ikep4j:message pre='${preMessage}' key='mobileNullMulti' />");
					} else {
						mobile = replaceAll(mobile, "-", "");
						mobile = replaceAll(mobile, " ", "");
						if (phoneNoChk(mobile)) {
							appendPhone(userId,userName,mobile);
						}
					}
				} 
			});

			$jq("#smsPhoneno").val('');
		} else {
			alert("<ikep4j:message pre='${preMessage}' key='userNull' />");
		}
	};   
	//조직도 팝업 호출
    setAddress = function(data) {
		var name="";
		var userId="";
		var phoneNo="";
		var inputFlag = true;
		$jq.each(data, function() {
			phoneNo = $jq.trim(this.mobile);
			if(phoneNo==null || phoneNo=='null' || phoneNo=='') {
				inputFlag = false;
			}
		});

		if(inputFlag==false) {
			alert("<ikep4j:message pre='${preMessage}' key='mobileNull' />");
			return;
		}		
		$jq.each(data, function() {
			name = $jq.trim(this.userName);
			userId = $jq.trim(this.id);			
			phoneNo = $jq.trim(this.mobile);
			if ( phoneNo == 'null') phoneNo = '';

			phoneNo = filterDigit(phoneNo);
			if (phoneNoChk(phoneNo)) {
				appendPhone(userId, name, phoneNo);
			}
			
		});

	};
	//주소록
	$jq("#btnAddrControl").click(function() {		
		//iKEP.showAddressBook(setAddress, '', {selectType:"user", isAppend:false, selectMaxCnt:100, tabs:{common:1,personal:1}});	
		iKEP.showAddressBook(setAddress, '', {selectType:"user", isAppend:false, selectMaxCnt:100, tabs:{personal:1}});      
	});
	
    //발신함 목록
	$jq("#btnSendList").click(function() {
		
		var $div = $jq(".sms_r").children().hide().eq(3).show();
		var $iframe = $jq('<iframe frameborder=0 scrolling=no style="width:336px; height:403px; position:absolute;top:10px;right:8px;z-index:99;border:0px solid #ffffff;"/>').appendTo($div);
		$iframe.attr("src", "<c:url value="/support/sms/listSms.do"/>");		
		
	});

	//이모티콘
	$jq(".smsbox_1 li, .smsbox_2 li").click(function(event) {
			var textVal = document.getElementById('sms_textarea').value;
			document.getElementById('sms_textarea').value = textVal + $jq(event.target).text();
			document.getElementById('sms_textarea').focus();
			chk_byte();
	}).css({cursor:"pointer"});
	
	//이모티콘 문자서식등 오른쪽 내용에 복사
	$jq(".smssample").click(function(event) {
		if(event.target.tagName.toLowerCase() == "div") {
			var textVal = $jq(event.target).html();
			textVal = replaceAll(textVal,"<BR>","\n");
			textVal = replaceAll(textVal,"<br>","\n");
			textVal = replaceAll(textVal,"&nbsp;"," ");
			document.getElementById('sms_textarea').value = textVal;
			chk_byte();
			document.getElementById('sms_textarea').focus();

		}
	}).find("div").css({cursor:"pointer"});	
	
	$jq("#smsRecentTr_1").mouseover(function() {
		var swpImg = $jq("#smsRecentImg_1").attr('src');
		$jq("#smsRecentImg_1").attr('src',swpImg.replace('minus','plus'));		
	})
	$jq("#smsRecentTr_1").mouseout(function() {
		var swpImg = $jq("#smsRecentImg_1").attr('src');
		$jq("#smsRecentImg_1").attr('src',swpImg.replace('plus','minus'));		
	})
		
});


function replaceAll(str, sep, pad) {
	while(str.indexOf(sep)>-1)
	{
		str = str.replace(sep, pad);
	}
	return str;
}
function trim(str){ 
	str = str.replace(/(^\s*)|(\s*$)/g,""); 
	return str; 
} 
function init_text() {
	var testVal = document.getElementById('sms_textarea').value;
	if ( testVal == "<ikep4j:message pre='${preMessage}' key='initMsg' />" ) {
		document.getElementById('sms_textarea').value = '';
	}
}
//한글인지 여부
function isHan(textVal) {
	var result = false;
	var textLen = textVal.length;
	for (var i = 0; i < textLen; i++) {		
		var oneChar = textVal.charAt(i);
		if (escape(oneChar).length > 4) {	
			result = true;
			break;
		}
	}
	return result;
}
//문자내용의 바이트수 계산
function chk_byte() {
	var textVal = document.getElementById('sms_textarea').value;
	var textLen = textVal.length;	
	
	var bytesLen = 0;
	var totSmsCnt = 1;
	var bytesPerSms = 80

	for (var i = 0; i < textLen; i++) {		
		var oneChar = textVal.charAt(i);
		if (escape(oneChar).length > 4) {		
			bytesLen += 2;						
		} else {		
			bytesLen++;		
		}			
	}		
	if ( textVal != "<ikep4j:message pre='${preMessage}' key='initMsg' />" ) {
		document.getElementById('curbyte').value = bytesLen + " bytes";
		
		if(bytesLen > 80)
		{
			$jq("#smsCnt").text("LMS");  
		}
		else
		{
			$jq("#smsCnt").text("SMS");  
		}	
		
		if ( bytesLen > 2000 ) {
	        alert("<ikep4j:message pre='${preMessage}' key='maxLenSms' />");
	    }
	}
}
//수신자 중복 체크
function receiverDoubleCheck(phoneno) {
	var $li = $jq("#ulSendList").children();
	var phonenoArray = new Array();
	var i = 0;
	$li.each(function() {				
		$jq(this).find("span").each(function() {
			var curSpanId = $jq(this).attr('id');
			var curSplit = curSpanId.substring(0,2);
			var curPhoneno = $jq(this).text();
			if ( curSplit == "no" && curPhoneno != '' ) {
				phonenoArray[i]=curPhoneno;
				i++;
			}
		});
	});		
	for(var i in phonenoArray) {
		if(phonenoArray[i] == phoneno) {
			return true;
		} else {

		}
	}
	return false;
}
//수신자 추가
function appendPhone(id, name, phoneNo) {
	phoneNo = filterDigit(phoneNo);
	if(receiverDoubleCheck(phoneNo)) {
		alert("<ikep4j:message pre='${preMessage}' key='alreadyInsertedReceiver' />");
		return;
	}

	var len=name.length;

	if (len>3) name = name.substring(0,6)+"..";
	var $li = $jq("#ulSendList").children();

	var lastSpanId = "";
	var lastSpanValue = "";
	$li.each(function() {				
		$jq(this).find("span").each(function() {
			if ( $jq(this).text() != '' ) {
				lastSpanId = $jq(this).attr('id');
				lastSpanValue = $jq(this).text();
			}	
		});
	});	

	var curId = lastSpanId.substring(2,lastSpanId.length);
	if(curId.substring(0,1) == "0") curId = replaceAll(curId,"0","");
	curId++;
	if(curId == '') curId = 1;
	if(lastSpanId == '' || lastSpanId < 'no04' ) {
		$jq("#rd0"+curId).val(id);
		$jq("#rn0"+curId).text(name);
		$jq("#no0"+curId).text(phoneNo);
	} else {	
		var paramId = curId;
		if ( curId < 10 ) curId = "0"+curId;	
		$jq("<tr id='tr"+curId+"'>").appendTo("#ulSendList");
		var $newTr = ("#ulSendList tr:last");
		$jq("<input type=hidden id='rd"+curId+"' value='"+id+"'>").appendTo($newTr);					
		$jq("<td>"+curId+"</td>").appendTo($newTr);
		$jq("<td><span id='rn"+curId+"'>"+name+"</span></td>").appendTo($newTr);
		$jq("<td><span id='no"+curId+"'>"+phoneNo+"</span></td>").appendTo($newTr);
		$jq("<td><a href='#a' onclick=\"del_receiverphoneno('"+paramId+"')\"><img src=\"<c:url value='/base/images/icon/ic_sms_x.gif' />\" alt='삭제' /></a></td>").appendTo($newTr);		
	}


}
var mode = 2;
function displayImcon(tmpmode) {
	mode = tmpmode;
	for (var i = 2; i <= 8; i++) {	
		if ( i == mode ) {
			document.getElementById('smsbox_'+i).style.display = "";
			$jq("#liSmsbox"+i).addClass('selected');
		} else {
			document.getElementById('smsbox_'+i).style.display = "none";
			$jq("#liSmsbox"+i).removeClass('selected');
		}

	}
}
var samMode = 1;
function displaySample(tmpSamMode) {
	samMode = tmpSamMode;
	for (var i = 1; i <= 3; i++) {	
		if ( i == samMode ) {
			document.getElementById('smssample_'+i).style.display = "";
			$jq("#liSample"+i).addClass('selected');
		} else {
			document.getElementById('smssample_'+i).style.display = "none";
			$jq("#liSample"+i).removeClass('selected');
		}

	}	
}
//문자 전송
function sendSms(){
	var byteArea = $jq("#curbyte").val();
	var byteAreaArray = byteArea.split(" ");
	var curByte = byteAreaArray[0];
	if ( eval(curByte) > 2000 ) {
		alert("<ikep4j:message pre='${preMessage}' key='maxLenSms' />");
		return;
	}
	
	var receiverIds = new Array();
	var receiverPhonenos = new Array();
	var $li = $jq("#ulSendList").children();
	var i = 0;
	var receiverId = "";
	var receiverPhoneno = "";
	var tmpReceiverId = "";
	var tmpReceiverPhoneno;
	var rowNum = 1;
	var receiverCnt = 0;
	$li.each(function() {
		$jq(this).find("span").each(function() {			
			var a = $jq(this).text();
			if ( i % 2 == 0 ) {
				tmpReceiverId = $jq(this).text();
				rowNum = (i/2)+1;
				tmpReceiverId = "0"+rowNum;
				if ( rowNum >= 10 ) tmpReceiverId = rowNum;	

				tmpReceiverId = $jq("#rd"+tmpReceiverId).val();

				if ( tmpReceiverId == '' ) {
					tmpReceiverId = " ";
				}
			} else {
				tmpReceiverPhoneno = $jq(this).text();
				tmpReceiverPhoneno = replaceAll(tmpReceiverPhoneno,'-','');
				if ( tmpReceiverPhoneno != '' ) {	
					receiverId = receiverId + tmpReceiverId + ":";
					receiverIds[receiverCnt] = tmpReceiverId;
					receiverPhoneno = receiverPhoneno + tmpReceiverPhoneno + ":";
					receiverPhonenos[receiverCnt] = tmpReceiverPhoneno;
					rowNum++;
					receiverCnt++;
				}
			}			
			i++;
		});
	});
	var lastCnt = ${smsRecentBottomCount};
	var smsCnt = $jq("#smsCnt").text();
	var totalSmsCnt = receiverCnt * smsCnt + lastCnt;
	var contents = $jq("#sms_textarea").val();
	contents = $jq.trim(contents);
	//smsAdd(receiverIds, receiverPhonenos, contents);return;	
	if ( contents == "<ikep4j:message pre='${preMessage}' key='initMsg' />" || contents == "") {
		alert("<ikep4j:message key='NotEmpty.sms.contents' />");
		return;
	}
	var senderPhoneno = $jq("#senderPhoneno").val();
	if ( receiverPhoneno == '' ) {
		alert("<ikep4j:message key='NotEmpty.sms.receiverPhoneno' />");
		return;
	}
	if ( senderPhoneno == '' ) {
		alert("<ikep4j:message pre='${preMessage}' key='senderPhoneno' />");
		return;
	} 
	//new part
	iKEP.smsAdd('${user.userId}', receiverIds, receiverPhonenos, contents, "1", ${smsCount});


}
function swap_img_over(num){
	var swpImg = $jq("#smsRecentImg_"+num).attr('src');
	$jq("#smsRecentImg_"+num).attr('src',swpImg.replace('minus','plus'));
}
function swap_img_out(num){
	var swpImg = $jq("#smsRecentImg_"+num).attr('src');
	$jq("#smsRecentImg_"+num).attr('src',swpImg.replace('plus','minus'));
}
function tmpAddPhoneno() {
	var tmpPhoneno = $jq("#smsPhoneno").val();

	if ( tmpPhoneno == '' ) {
		alert("<ikep4j:message key='NotEmpty.sms.receiverPhoneno' />");
		$jq("#smsPhoneno").focus();
	} else {
		if ( isDigit(tmpPhoneno) ) {
			if (phoneNoChk(tmpPhoneno)) {
				appendPhone('','',tmpPhoneno);
				$jq("#smsPhoneno").val('');	
			}			
		} else {
			iKEP.searchUser('', "Y", setUser);
		}		
	}
}
//숫자인지 여부 체크
function isDigit(phonenNo) {
	var result = true;
	var pattern = /^[0-9]+$/;	

	if ( !pattern.test(phonenNo) ) {
		result = false;
	}
	
	return result;
}
//해당 스트링에서 숫자이외 문자 필터링
function filterDigit(phonenNo) {
	var result = true;
	var pattern = /^[0-9]+$/;	

	var result = "";
	var textLen = phonenNo.length;	
	for (var i = 0; i < textLen; i++) {
		curNumber = phonenNo.charAt(i);
		if ( pattern.test(curNumber) ) {
			result = result + curNumber;
		}
	}	
	
	return result;
}
//전화번호 validation 체크
function phoneNoChk(phonenNo) {
	
	if (!isDigit(phonenNo)) {
		alert("<ikep4j:message pre='${preMessage}' key='receiverPhonenoDetail' />");
		return false;			
	}

	return true;
}
//취소 버튼 클릭시
function cancel_text() {
	var $newTr = ("#ulSendList tr:last");
	var lastTrName = $jq($newTr).attr('id');
	var idNum = lastTrName.substring(3,lastTrName.length);

	$jq("#ulSendList").find("span").empty();

	if ( idNum>4) {
		for (var i = 5; i <= idNum; i++) {
			var curId = "0"+i;
			if ( i >= 10 ) curId = i;
			$jq("#tr"+curId).remove();
		}
	}
	for (var i = 1; i <= 4; i++) {
		$jq("#rd0"+i).val('');
	}	
	
	document.getElementById('sms_textarea').value = '';
	chk_byte();
}
//수신자 목록에서 해당 번호 삭제
function del_receiverphoneno(smsNm) {

	var curId = "0"+smsNm;
	if ( smsNm >= 10 ) curId = smsNm;	

	$jq("#rd"+curId).val('');
	$jq("#rn"+curId).empty();
	$jq("#no"+curId).empty();
	
	var $li = $jq("#ulSendList").children();
	var curReceiverId = "";
	var curReceiverName = "";
	var curReceiverPhoneno = "";
	$li.each(function() {				
		$jq(this).find("span").each(function() {
			var curSpanId = $jq(this).attr('id');
			var curSplitChar = curSpanId.substring(0,2);			
			var curSplitNm = curSpanId.substring(2,curSpanId.length);
			var curSplitDigit = curSplitNm;
			var prevSplit = curSplitNm.substring(0,1);
			if ( prevSplit == "0" ) {
				curSplitDigit = curSplitNm.substring(1,curSplitNm.length);
			}
			if ( curSplitChar == "rd" ) curReceiverId = $jq(this).text();
			if ( curSplitChar == "rn" ) curReceiverName = $jq(this).text();
			if ( curSplitChar == "no" ) curReceiverPhoneno = $jq(this).text();
			if ( curSplitChar == "no" && curReceiverPhoneno != '' ) {
				if ( curSplitNm > curId ) {

					var setDigit = curSplitDigit - 1;
					var setSpanNm = setDigit;
					if ( setDigit < 10 ) {
						setSpanNm = "0"+setDigit;	
					}

					$jq("#rd"+curSplitNm).val('');
					$jq("#rn"+curSplitNm).empty();
					$jq("#no"+curSplitNm).empty();					
					$jq("#rd"+setSpanNm).val(curReceiverId);
					$jq("#rn"+setSpanNm).text(curReceiverName);
					$jq("#no"+setSpanNm).text(curReceiverPhoneno);	
					
				}
			}
		});
	});		

}

function configSms() {
	var url = '<c:url value="/support/smsConfig/listSmsConfig.do"/>';
	
	var options = {
		windowTitle : 'SMS',
		documentTitle : "<ikep4j:message pre='${preLabel}' key='documentTitle' />",
		width:800, 
		height:500, 
		modal:true 
	};
	
	iKEP.portletPopupOpen(url, options, 'smsConfigPopup');  
}

//-->
</script> 

<style type="text/css">*{ime-mode:active}</style>
<input type="hidden" id="receiverId" value="" title="" />	
<!--sms Start-->
<c:if test="${receiveUser.userStatus != 'T'}">
<div id="sms">

	<div class="smsLogo"><img src="<c:url value='/base/images/common/logo_sms.png' />" alt="Moorim" /></div>
	                         
	<!--sms_l Start-->

	<div class="sms_l">
		
		<h1 class="none">SMS</h1>
		<textarea class="sms_textarea" title="sms" name="" cols="" rows="" onclick="init_text()" id="sms_textarea"><ikep4j:message pre='${preMessage}' key='initMsg' /></textarea>
		<div class="smsbyte" style="border:1"><span style="text-align:right;padding:0 0 0 0;" id="smsCnt">SMS</span>
		<input type="text" id="curbyte" title="curbyte" size="20" readonly="readonly" value="0 bytes" style="background:none;border:0;font-family:Tahoma;font-size:1.0em;color:#94a9dd;text-align:right;padding:0 10px 0 0;" />
		</div>
		
		<h2 class="none">phoneno info</h2>
		<div class="numinfo">
			<ikep4j:message pre='${preLabel}' key='receiver' />
			<input type="text" class="smsinput" title="smsPhoneno" name="smsPhoneno" id="smsPhoneno" size="11" maxlength="11" />			
			<span class="smsbtn_add"><a href="#a" onclick="tmpAddPhoneno()" id="userSearchBtn"><ikep4j:message pre='${preButton}' key='add' /></a></span>
		</div>
		
        <form id="AddrControlForm" action="">      	
             <!-- select name="addrGroupList" multiple="multiple" size="5" style="width:300px;"></select-->
             <input name="controlTabType" title="" type="hidden" value="1:0:1:0" />
             <input name="controlType" title="" type="hidden" value="ORG" />
             <input name="selectType" title="" type="hidden" value="USER" />
             <input name="selectMaxCnt" title="" type="hidden" value="100" />
             <input name="searchSubFlag" title="" type="hidden" value="" />		        
		<div class="smslist">
			<h2><ikep4j:message pre='${preLabel}' key='receiverList' /></h2>		
			<div class="smslist_btn">
            	<span><a href="#a" id="btnAddrControl"><ikep4j:message pre='${preButton}' key='addressbook' /></a></span>
			</div>
			<div class="clear"></div>
			<div class="smslist_t" style="height:80px">
				<table summary="<ikep4j:message pre='${preLabel}' key='summary' />">
					<caption></caption>
					<tbody id="ulSendList">
						<tr id="tr01">
							<td width="15"><input type="hidden" id="rd01" title="" />01</td>
							<td width="55"><span id="rn01"></span></td>
							<td width="75"><span id="no01"></span></td>
							<td width="10"><a href="#a" onclick="del_receiverphoneno('1')"><img src="<c:url value='/base/images/icon/ic_sms_x.gif' />" alt="del" /></a></td>
						</tr>						
						<tr id="tr02">
							<td><input type="hidden" id="rd02" title="" />02</td>
							<td><span id="rn02"></span></td>
							<td><span id="no02"></span></td>
							<td><a href="#a" onclick="del_receiverphoneno('2')"><img src="<c:url value='/base/images/icon/ic_sms_x.gif' />" alt="del" /></a></td>
						</tr>
						<tr id="tr03">
							<td><input type="hidden" id="rd03" title="" />03</td>
							<td><span id="rn03"></span></td>
							<td><span id="no03"></span></td>
							<td><a href="#a" onclick="del_receiverphoneno('3')"><img src="<c:url value='/base/images/icon/ic_sms_x.gif' />" alt="del" /></a></td>
						</tr>
						<tr id="tr04">
							<td><input type="hidden" id="rd04" title="" />04</td>
							<td><span id="rn04"></span></td>
							<td><span id="no04"></span></td>
							<td><a href="#a" onclick="del_receiverphoneno('4')"><img src="<c:url value='/base/images/icon/ic_sms_x.gif' />" alt="del" /></a></td>
						</tr>
														
					</tbody>
				</table>
			</div>				
		</div>
        </form>
         		
		<div class="sendnum">
			<ikep4j:message pre='${preLabel}' key='senderPhoneno' /> <input type="text" class="smsinput readonly" readonly="readonly" title="senderPhoneno" name="senderPhoneno" id="senderPhoneno" size="10" />
			<div class="sendnum_btn"><a href="#a" id="btnSendList"><ikep4j:message pre='${preLabel}' key='senderBox' /> </a></div>
		</div>
				
		<h2 class="none">send button</h2>
		<div class="smsbtn">
			<ul>
				<li><a href="#a" onclick="sendSms();"><ikep4j:message pre='${preButton}' key='send' /></a></li>
				<li><a href="#a" onclick="cancel_text()"><ikep4j:message pre='${preButton}' key='cancel' /></a></li>
				<c:if test="${smsAdmin}">
					<li><a href="#a" onclick="configSms();"><ikep4j:message pre='${preLabel}' key='config' /></a></li>
				</c:if>
			</ul>
		</div>
				
	</div>
		
	<!--//sms_l End-->

		<!--sms_r Start-->
	<div class="sms_r">
		<!--tab Start-->		
		<div id="divTab_sms" class="iKEP_tab_sms">
			<ul>
				<li><a href="#tabs-1"><ikep4j:message pre='${preLabel}' key='emoticon' /></a></li>
				<li><a href="#tabs-2"><ikep4j:message pre='${preLabel}' key='textstyle' /></a></li>
				<li><a href="#tabs-3"><ikep4j:message pre='${preLabel}' key='recentBox' /></a></li>
			</ul>

			<!--tabs-1 Start-->		
			<div id="tabs-1">
				<h2 class="none"><ikep4j:message pre='${preLabel}' key='emoticon' /></h2>
				<div class="smsbox_1">
					<ul>
						<li>★</li>
						<li>☆</li>
						<li>☎</li>
						<li>☏</li>
						<li>☜</li>
						<li>☞</li>
						<li>※</li>
						<li>♥</li>
						<li>♡</li>
						<li>♣</li>
						<li>♠</li>
						<li>♤</li>
						<li>♧</li>
						<li>♨</li>
						<li>ㆀ</li>
						<li>♩</li>
						<li>♪</li>
						<li>♬</li>
						<li>■</li>
						<li>□</li>
						<li>▣</li>
						<li>▤</li>
						<li>▦</li>
						<li>▨</li>
						<li>▩</li>
						<li>▒</li>
						<li>▲</li>
						<li>△</li>
						<li>▶</li>
						<li>▷</li>
						<li>▼</li>
						<li>▽</li>
						<li>◀</li>
						<li>◁</li>
						<li>◆</li>
						<li>◇</li>
						<li>◈</li>
						<li>●</li>
						<li>○</li>
						<li>◎</li>
						<li>⊙</li>
						<li>◐</li>
						<li>◑</li>
						<li>←</li>
						<li>↑</li>
						<li>→</li>
						<li>↓</li>
						<li>⇒</li>
						<li>§</li>
						<li>Ø</li>
						<li>∀</li>
						<li>∃</li>
						<li>∏</li>
						<li>∞</li>
						<li>∧</li>
						<li>∪</li>
						<li>∬</li>
						<li>∴</li>
						<li>∽</li>
						<li>≠</li>
						<li>⊃</li>
						<li>￠</li>
						<li>￥</li>
					</ul>                                                          
				</div>

				<div class="smsbox_t">
					<ul>
						<li id="liSmsbox2"><a href="#a" onclick="javascript:displayImcon(2)"><ikep4j:message pre='${preLabel}' key='box01' /></a></li>
						<li id="liSmsbox3"><a href="#a" onclick="javascript:displayImcon(3)"><ikep4j:message pre='${preLabel}' key='box02' /></a></li>
						<li id="liSmsbox4"><a href="#a" onclick="javascript:displayImcon(4)"><ikep4j:message pre='${preLabel}' key='box03' /></a></li>
						<li id="liSmsbox5"><a href="#a" onclick="javascript:displayImcon(5)"><ikep4j:message pre='${preLabel}' key='box04' /></a></li>
						<li id="liSmsbox6"><a href="#a" onclick="javascript:displayImcon(6)"><ikep4j:message pre='${preLabel}' key='box05' /></a></li>
						<li id="liSmsbox7"><a href="#a" onclick="javascript:displayImcon(7)"><ikep4j:message pre='${preLabel}' key='box06' /></a></li>
						<li id="liSmsbox8"><a href="#a" onclick="javascript:displayImcon(8)"><ikep4j:message pre='${preLabel}' key='box07' /></a></li>
					</ul>
				</div>
				<div class="smsbox_2" id="smsbox_2">
					<ul>
						<li>n.n</li>
						<li>^Δ^</li>
						<li>^v^</li>
						<li>^.^</li>
						<li>^_^</li>
						<li>^0^</li>
						<li>^L^</li>
						<li>⌒⌒</li>
						<li>^.~</li>
						<li>^ε^</li>
						<li>^-^b</li>
						<li>*^^*</li>
						<li>^▽^</li>
						<li>=^.^=</li>
						<li>(^^)γ</li>
						<li>&lt;^O^&gt;</li>
						<li>(*^-^)</li>
						<li>(*^o^*)</li>
						<li>^o^~~♬</li>
						<li>☞^.^☜</li>
						<li>o(^-^)o S(^.^)b</li>
						<li>(￣∇￣)</li>
						<li><br />♬(^0^)~♪</li>
						<li>s(￣▽￣)/</li>
					</ul>
				</div>
				
					<div class="smsbox_2" id="smsbox_3" style="display:none;">
						<h3 class="none">사랑</h3>
						<ul>
							<li>♥.♥</li>
							<li>♡.♡</li>
							<li>*♥o♥*</li>
							<li>(~.^)s</li>
							<li>☞♡☜</li>
							<li>γ^ε^γ</li>
							<li>(♡.♡)</li>
							<li>(*`0`*)</li>
							<li>(⌒ε⌒*)</li>
							<li>(*^}{^*)</li>
							<li>づ^0^)づ</li>
							<li>ⓛⓞⓥⓔ</li>
							<li>☜(^^*)☞</li>
							<li>(*^3(^^*)</li>
							<li>(^*^)kiss</li>
							<li>(つ＾з＾)つ</li>
							<li>(*⌒.^)(^ε⌒*)</li>
							<li>(*^-^)♡(^o^*)</li>
						</ul>
					</div>                              
					<div class="smsbox_2"  id="smsbox_4" style="display:none;">
						<h3 class="none">놀람</h3>
						<ul>
							<li>?o?</li>
							<li>O_O</li>
							<li>@_@</li>
							<li>!.!</li>
							<li>⊙⊙</li>
							<li>(o_o)</li>
							<li>(@.@)</li>
							<li>('o')</li>
							<li>★.★</li>
							<li>☆_☆</li>
							<li>⊙.⊙</li>
							<li>☆_☆</li>
							<li>☞_☜</li>
							<li>(o)(o)</li>
							<li>(=_=;)</li>
							<li>⊙⊙ㆀ</li>
							<li>ㆀ-_-ㆀ</li>
							<li>ご,.ごㆀ</li>
							<li>ㅡ..ㅡㆀ</li>
						</ul>
					</div> 
					<div class="smsbox_2"  id="smsbox_5" style="display:none;">
						<h3 class="none">슬픔</h3>
						<ul>
							<li>T.T</li>
							<li>;_;</li>
							<li>TmT</li>
							<li>Θ_Θ</li>
							<li>ㅠ.ㅠ</li>
							<li>ㅜ.ㅜ</li>
							<li>ㅡ.ㅜ</li>
							<li>(v.v)</li>
							<li>(∏.∏)</li>
							<li>ご.ご</li>
							<li>(X_X)</li>
							<li>(>_&lt;)</li>
							<li>+_+;;</li>
							<li>(づ_T)</li>
							<li>(づ_ど)</li>
							<li>o(T^T)o</li>
							<li>(ㅜ.ㅜ)</li>
							<li>(ㅠ.ㅠ)</li>
							<li>(♨_♨)</li>
							<li>(T(oo)T)</li>
						</ul>
					</div>	
					<div class="smsbox_2"  id="smsbox_6" style="display:none;">
						<h3 class="none">피곤</h3>
						<ul>
							<li>=.=</li>
							<li>θ.θ</li>
							<li>@L@</li>
							<li>(-0-)</li>
							<li>(@.@)</li>
							<li>(Z_Z)</li>
							<li>∋.∈</li>
							<li>⊇.⊆</li>
							<li>(u_u)</li>
							<li>(g_g)</li>
							<li>(づ_-)</li>
							<li>(-_ど)</li>
							<li>(=.&amp;=)</li>
							<li>(-.-)Zzz..</li>
							<li>[(￣.￣)]zZ</li>
						</ul>
					</div>	
					<div class="smsbox_2"  id="smsbox_7" style="display:none;">
						<h3 class="none">화남</h3>
						<ul>
							<li>:-<</li>
							<li>ㅡㅡ+</li>
							<li>(`o')</li>
							<li>(#_-)</li>
							<li>ㅡㅡ^</li>
							<li>(@_@)</li>
							<li>☞_☜</li>
							<li>s(￣へ￣ )z</li>
							<li>(-.￥)</li>
							<li>(｀へ´)</li>
							<li>o(-"-)o</li>
							<li>(-_-メ)</li>
							<li>(-"-メ)</li>
							<li>(↗∇↖)</li>
							<li>(-(oo)-)</li>
							<li>(-.-")凸</li>
							<li>(*｀Д´)/</li>
							<li>(/ㅡ_-)/~</li>
							<li>＼(*｀Д´)/</li>
							<li>(-ヘㅡメ)</li>
						</ul>
					</div>						
					<div class="smsbox_2"  id="smsbox_8" style="display:none;">
						<h3 class="none">기타</h3>
						<ul>
							<li>(-_-)</li>
							<li>($_$)</li>
							<li>( ㅅ )</li>
							<li>('⌒')</li>
							<li>ご,,ご</li>
							<li>o(>_&lt;)o</li>
							<li>('º')/)/)</li>
							<li>( ㅅ)=333</li>
							<li>∠(- o -)</li>
							<li>☞(>.&lt;)☜</li>
							<li><(>.&lt;ㆀ)></li>
							<li>┌(ㆀ_ _)┐</li>
							<li>s(ごoご)グ</li>
							<li>(^(**)^))~</li>
							<li>(. .)|/)/)</li>
							<li>('' )( '')</li>
							<li>(=`.`=)@@</li>
						</ul>                           
					</div>				
			</div>
			<!--//tabs-1 End-->	

			<!--tabs-2 Start-->	
			<div id="tabs-2">
				<h2 class="none"><ikep4j:message pre='${preLabel}' key='textStyle' /></h2>
				<div class="smssample_t">
					<ul>
						<li id="liSample1"><a href="#a" onclick="javascript:displaySample(1)">인사/축하</a></li>
						<li id="liSample2"><a href="#a" onclick="javascript:displaySample(2)">생일</a></li>
						<li id="liSample3"><a href="#a" onclick="javascript:displaySample(3)">감동의 시</a></li>						
					</ul>
				</div>	

				<div id="smssample_1">
					<h3 class="none">인사/축하</h3>
					<div class="smssample">
						<div class="sample_set">행운이 가득한♬<br />하루 되세요<br />△▷ ▲▶ △▷<br />◁▽ ◀▼ ◁▽<br />ノ˚ ノ。 ノ 。</div>
						<span>행복한 하루</span>
					</div>
					<div class="smssample">
						<div class="sample_set">ㆀ(.  .)이거먹고<br />┌━∥┐ㆀ활기찬<br />┃&nbsp;&nbsp;&nbsp;∥┃오늘하루<br />┃보약┃시작하세<br />└━━┘요(^O^)/</div>
						<span>활기찬 하루</span>						
					</div>
					<div class="smssample">
						<div class="sample_set"> q∩  ∩p ☆<br /> ☆(*└─┘*)<br />하루1분의웃음이<br />그날을행복하게만<br />든대요~스마일♬</div>
						<span>웃는 하루</span>
					</div>
					<div class="smssample">
						<div class="sample_set">s▶◀s  ///＼＼<br />(*^.^)♥(^.^*)<br />★0-0★ ★0-0-★<br />★ 결혼기념일<br />축하드립니다★</div>
						<span>결혼기념일</span>
					</div>
					<div class="smssample">
						<div class="sample_set">   ┹  ＊*＊<br />＼(^∇^)(^∇^)/<br />여러분∽♡<br />♀Zl들 결혼ㅎH여<br />축ㅎrㅎH주서l요</div>
						<span>결혼기념</span>						
					</div>
					<div class="smssample">
						<div class="sample_set"> /)/)˚。 ☆<br />( . .)☆졸업。☆<br />( づ♡축하해。<br />새로운세상으로<br />힘차게출발하자!</div>
						<span>졸업축하</span>
					</div>	
				</div>

				<div id="smssample_2" style="display:none;">
					<h3 class="none">생일</h3>
					<div class="smssample">
						<div class="sample_set">:..*..★*..*..:<br />ㅅH ○1 ㅊ ㅋr<br />.○.*ㄹ.∏.*.*<br />*˚태어나줘서˚<br />고마워요</div>
						<span>생일축하</span>
					</div>
					<div class="smssample">
						<div class="sample_set"> /)/)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;iiiiiii<br />   ( . .)&nbsp;&nbsp;&nbsp;┏♡♡┓<br /> ( づ♡┏☆""☆┓<br />♡~생일축하해~♡<br />사랑하는당신이여</div>
						<span>♡-생일축하해-♡</span>						
					</div>
					<div class="smssample">
						<div class="sample_set"> +^생일축하해^+ <br /> ())        (() <br /> (.")  **  (".) <br /> ( QQ__ll__QQ ) <br />날선물할께^^ㅋ</div>
						<span>날선물할께</span>
					</div>
					<div class="smssample">
						<div class="sample_set">.*""*.  ζζζ<br />┣━┫┓~~~ ┏<br />┗━┛┗━━┛<br />따뜻한밥+미역국<br />생일축하드림♣</div>
						<span>생일축하드림</span>
					</div>
					<div class="smssample">
						<div class="sample_set">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;iiiiiii<br />&nbsp;&nbsp;┏━♡♡━┓<br />┏"━☆━☆━"┓<br />♡-생일축하해-♡<br />★☆:+.♡.+:★☆</div>
						<span>해피생일</span>						
					</div>
					<div class="smssample">
						<div class="sample_set">♬당신은사랑받기<br />위해태어난사람~<br />당신에삶속에서그<br />사랑받고있지요♪<br />생일축하해♡</div>
						<span>생일축하</span>
					</div>						
				</div>	
				
				<div id="smssample_3" style="display:none;">
					<h3 class="none">감동의 시</h3>
					<div class="smssample">
						<div class="sample_set">.:*:이른새벽:*:.<br />   당신에게 문자를<br />   보냅니다☆.:': <br />    꿈속에서도당신이<br />   그립기때문입니다  </div>
						<span>그리움</span>
					</div>
					<div class="smssample">
						<div class="sample_set">햇살에반하듯..당<br />신에게반하고..음<br />악에취하듯..당신<br />에게취합니다음악<br />햇살사라질때까지</div>
						<span>영원히 사랑해</span>
					</div>
					<div class="smssample">
						<div class="sample_set">*여우에게길들여<br />진어린왕자처럼**<br />나도누군가에게길<br />들여지고싶어**<br />그게너였으면.*</div>
						<span>길들여짐</span>
					</div>
					<div class="smssample">
						<div class="sample_set">어느새화사한봄내<br />음의내마음에들어<br />와즐겁게하네요^^<br />서로같은느낌이라<br />여깁니다^^</div>
						<span>화사한♬봄내음</span>
					</div>
					<div class="smssample">
						<div class="sample_set">열심히일하는당<br />신에게나의짧은<br />메시지가힘이되<br />었으면좋겠어요<br />즐건하루되세요</div>
						<span>나의메세지</span>
					</div>
					<div class="smssample">
						<div class="sample_set">노을이아름다운건<br />구름이있기때문이<br />고사람이아름다운<br />건이루어야할꿈이<br />있기때문입니다☆</div>
						<span>아름다운건..</span>
					</div>					
				</div>

										
			</div>
			<!--//tabs-2 End-->	
			
			<!--tabs-3 Start-->	
			<div id="tabs-3">
				<h2 class="none"><ikep4j:message pre='${preLabel}' key='recentBox' /></h2>
				<div class="smssample_t_2"><ikep4j:message pre='${preLabel}' key='recentBoxTitle' /></div>	
			 	<c:forEach var="sms" items="${searchResult.entity}" varStatus="smsLoopCount">	
					<div class="smssample">
						<% pageContext.setAttribute("newLineChar", "\n"); %>
						<%-- div class="sample_set" title="${sms.contents }">${fn:replace(ikep4j:cutString(sms.contents,70,".."), newLineChar, "<br />")}</div--%>
						<div class="sample_set" title="${sms.contents}">${fn:replace(sms.contents, newLineChar, "<br />")}</div>
					     <c:choose>
					      <c:when test="${sms.registDate == null}">
					       <div>&nbsp;</div>
					      </c:when>
					      <c:otherwise>
					       <div><ikep4j:timezone date="${sms.registDate}" pattern="message.support.sms.timezone.dateformat.type2" keyString="true"/></div>
					      </c:otherwise>
					     </c:choose>						
					</div>				 	 		
				</c:forEach>				
			</div>
			<!--//tabs-3 End-->	
			
		</div>		
		<!--//tab End-->
		
		<div class="clear"></div>
		<div class="smslatest">
			<h2><ikep4j:message pre='${preLabel}' key='recentList' /> <span class="smslatest_num">(<ikep4j:message pre='${preLabel}' key='monthCount' /> <span>${smsRecentBottomCount}/${smsCount}</span><ikep4j:message pre='${preLabel}' key='count' />)</span></h2>
			<div class="smsbox_3">
				<table summary="num name phoneno">
					<thead>
						<tr>
							<th scope="col"><ikep4j:message pre='${preLabel}' key='listNum' /></th>
							<th scope="col"><ikep4j:message pre='${preLabel}' key='listName' /></th>
							<th scope="col"><ikep4j:message pre='${preLabel}' key='listPhoneno' /></th>
						</tr>
					</thead>
					<tbody>																
					 	<c:forEach var="sms" items="${smsRecentBottom}" varStatus="smsLoopCount">	 		
							<tr class="${smsLoopCount.count}" style='cursor:pointer' onclick="appendPhone('${sms.receiverId}','${sms.receiverName}', '${sms.receiverPhoneno}')" onmouseover="swap_img_over(${smsLoopCount.count})" onmouseout="swap_img_out(${smsLoopCount.count})">
								<td>0${smsLoopCount.count}</td>
							    <c:choose>
							    	<c:when test="${user.localeCode == portal.defaultLocaleCode}">
							       		<td>${sms.receiverName}</td>
							      	</c:when>
							      	<c:otherwise>
							       		<td>${sms.receiverEnglishName}</td>
							      	</c:otherwise>
							    </c:choose>																
								<td>${sms.receiverPhoneno} <img id="smsRecentImg_${smsLoopCount.count}" src="<c:url value='/base/images/icon/ic_s_minus.gif' />" alt="" /></td>
							</tr>
						</c:forEach>																							
					</tbody>
				</table>
			</div>			
		</div>
		<div class="none"></div>
	</div>
	<!--//sms_r End-->
	<div class="clear"></div>
</div>
</c:if>
<!--//sms End--> 	
