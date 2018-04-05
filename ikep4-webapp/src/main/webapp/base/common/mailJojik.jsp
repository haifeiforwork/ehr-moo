<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/theme03/jquery_ui_custom.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/theme03/theme.css"/>" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.5.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/plugins.pack.js"/>"></script>

<script type="text/javascript">var contextRoot = "<c:url value="/"/>";</script>
<script type="text/javascript" src="<c:url value="/base/js/langResource/ko.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/common.pack.js"/>"></script>


<%
	String hostname =request.getServerName();
	hostname = hostname.substring(7,hostname.length());
%>

<script type="text/javascript">
<!--
document.domain="<%=hostname %>";

(function($) {
	
	var validator;
	var mailList = "";
	var items =[];
	var referItems =[];
	var bccItems =[];
	
	setReadUserAddress = function(data) {   
		
		//iKEP.debug(data);
		
		$.each(data, function(index, value) {
		
		//iKEP.debug(value);
		
			if(value.gubun=="Receiver") {
				if($("#selectReceiverList option[value=" + value.id + "]").length == 0) {
					$.tmpl( "<option value='{{= id}}' id='{{= id}}^{{= name}}^{{= jobTitle}}^{{= teamName}}^{{= email}}'>{{= name}} {{= jobTitle}} ({{= teamName}}, {{= email}} )</option>", value).appendTo( "#selectReceiverList" );
					items.push(value);
				}
			}
			if(value.gubun=="Refer") {
				if($("#selectReferList option[value=" + value.id + "]").length == 0) {
					$.tmpl( "<option value='{{= id}}' id='{{= id}}^{{= name}}^{{= jobTitle}}^{{= teamName}}^{{= email}}'>{{= name}} {{= jobTitle}} ({{= teamName}}, {{= email}} )</option>", value).appendTo( "#selectReferList" );
					referItems.push(value);
				}
			}
			if(value.gubun=="Bcc") {
				if($("#selectBccList option[value=" + value.id + "]").length == 0) {
					$.tmpl( "<option value='{{= id}}' id='{{= id}}^{{= name}}^{{= jobTitle}}^{{= teamName}}^{{= email}}'>{{= name}} {{= jobTitle}} ({{= teamName}}, {{= email}} )</option>", value).appendTo( "#selectBccList" );
					bccItems.push(value);
				} 
			}	
		
		});
	};
	
	$jq(document).ready(function() {
		//그룹목록 추가 버튼에 이벤트 바인딩
		$("#addReadUserButton1").click( function() {
			//var controlTabType="1:1:0:0";
			
			var items = [];
			var $sel = $jq("#mail").find("[name=selectReceiverList]");
	
			$jq.each($sel.children(), function() {
				items.push($jq.data(this, "data"));
			});
			iKEP.showAddressMailBook(setReadUserAddress, items, {selectType:"all", isAppend:false, tabs:{common:1, personal:1, collaboration:0, sns:0}});
		});
		
	$jq("#addReadUserButton").click(function() {
			// 조직도 팝업 테스트


			$controlType = $jq('input[name=controlType]:hidden').val() ;
			$controlTabType = $jq('input[name=controlTabType]:hidden').val() ;
			$selectType = $jq('input[name=selectType]:hidden').val() ;
			$selectMaxCnt = $jq('input[name=selectMaxCnt]:hidden').val() ;
			$searchSubFlag = $jq('input[name=searchSubFlag]:hidden').val() ;


			// 수정 될 일반 팝업 형태의 팝업 오픈 시 사용될 스크립트
			var url = "<c:url value='/support/popup/addresbookMailPopup.do'/>"+"?controlType=" + $controlType + "&controlTabType=" + $controlTabType + "&selectType=" + $selectType + "&selectMaxCnt=" + $selectMaxCnt + "&searchSubFlag=" + $searchSubFlag;
			iKEP.popupOpen(
				url,
				{
					width:700, height:550,
					argument : {search:"keyword", items:items, referItems:referItems, bccItems:bccItems },
					callback : setReadUserAddress
				}
			);


		});		
		
	});	
	// java script 전역변수 항목에 추가
	$jq.template("addrBookItemUser", "<option value='\${id}'>\${name}/\${jobTitle}/\${teamName}</option>");
	$jq.template("addrBookItemGroup", "<option value='\${code}'>\${name}</option>");	
})(jQuery);  
//주소록 사용자 검색 팝업
popupAddrPerson = function() {
	var url = '<c:url value="/support/addressbook/createAddressForm.do?addrgroupId=&groupType=&personId=&userName=김종철&email=happyi1018@nate.com"/>';
	
	iKEP.popupOpen(url, {width:600, height:275 }, "AddAddress");
};
function address(){
	popupUrl ="<c:url value="/support/addressbook/createAddressForm.do?addrgroupId=&groupType=&personId="/>";
	var features = "channelmode=no, directories=no, fullscreen=no, location=no, menubar=no, scrollbars=no, status=no, titlebar=no, toolbar=no";
	features += ", width=6000, height=450";
	features += ", left=10";
	features += ", top=10";
	features += ", resizable=no";
	
	window.open(popupUrl, "AddAddress", features);
}
//-->
</script>
<a id="addReadUserButton1" class="button_ic" href="#a"><span><img src="/ikep4-webapp/base/images/icon/ic_btn_address.gif" alt="" />조직도팝업1</span></a> 
<br/><br/>
<a id="addReadUserButton" class="button_ic" href="#a"><span><img src="/ikep4-webapp/base/images/icon/ic_btn_address.gif" alt="" />조직도팝업</span></a> 
<br/><br/>
수신자<br/>
<form name="mail" id="mail">
<select title="select" name="selectReceiverList" id="selectReceiverList" size="5" multiple="multiple" class="multi w60" title="수신자" >  			
</select>
<br/>
참조자<br/>
<select title="select" id="selectReferList" size="5" multiple="multiple" class="multi w60" title="참조자" >  			
</select>
<br/>
숨은참조자<br/>
<select title="select" id="selectBccList" size="5" multiple="multiple" class="multi w60" title="숨은참조자" >  			
</select>
<input name="controlTabType" title="" type="hidden" value="1:1:0:0" />
<input name="controlType" title="" type="hidden" value="ORG" />
<input name="selectType" title="" type="hidden" value="ALL" />
<input name="selectMaxCnt" title="" type="hidden" value="500" />
<input name="searchSubFlag" title="" type="hidden" value="" />
</form>

<br />

<br />
<br />
<a href="javascript:popupAddrPerson()"> 주소록 추가 </a>



