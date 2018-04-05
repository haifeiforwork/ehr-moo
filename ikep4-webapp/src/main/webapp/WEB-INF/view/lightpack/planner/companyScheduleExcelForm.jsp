<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%@ page import="destiny.link.slo.service.DestinySLO"%>
<%!
public void setSystemProperty( String key, String value) {
    System.setProperty( key, value);
}

public void setCookie( HttpServletResponse response, String key, String value) {
    Cookie cookie = new Cookie( key, value);
    cookie.setPath( "/");
    cookie.setMaxAge( -1);
    cookie.setVersion( 0);
    //cookie.setComment( "destiny slo test");
    cookie.setSecure( false);
    response.addCookie( cookie);
}
%>

<%
// ECM Server Address
String sloServerAddress = "http://ecm.moorim.co.kr";
String sloAPIKey = "0VbXsZYobdOnciJmv4GQ3h16EvOjAoF0icK5sHMSvX4=";

// GW Login User Account
String userAccount = ((com.lgcns.ikep4.support.user.member.model.User)session.getAttribute("ikep.user")).getUserId(); // 로그인 사용자ID
// GW Login User's GroupCode
String userGroupCode = ((com.lgcns.ikep4.support.user.member.model.User)session.getAttribute("ikep.user")).getGroupId(); // 로그인 사용자 부서코드
// ECM Settings
setSystemProperty( "common.SloAddrKey", sloServerAddress);
setSystemProperty( "common.SloAPIKey", sloAPIKey);
setCookie( response, "ACCOUNT", userAccount);
setCookie( response, "GROUP_CODE", userGroupCode);
%>

<c:set var="preDetail"  value="ui.portal.excelupload.detail" />
<c:set var="preButton"  value="ui.portal.excelupload.button" /> 
<c:set var="preButton2"  value="ui.lightpack.planner.common.button" /> 
<c:set var="preMessage" value="ui.portal.excelupload.message" />
<c:set var="userListPrefix" value="message.portal.admin.member.user.list"/>
<c:set var="userUiPrefix" value="ui.portal.admin.member.user"/>
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />


<script type="text/javascript" language="javascript">

(function($) {
	
	
	$jq(document).ready(function() { 
		
		
		
		$jq('#submitBtn').click( function() { 
			
			$jq("#fileForm").trigger("submit");
		});
		
		$jq("#cancelBtn").click(function() {
			
			iKEP.closePop();
		});
		
		var validOptions = {
				rules : {
					file : {
						required : true,
						accept : "xls"
					}
				},
				messages: {
					file :	{
						required : "<ikep4j:message key="NotEmpty.excel.file" />"
						,accept : "<ikep4j:message key="Pattern.excel.file" />"
					}
				}
		}
		
		var validator = new iKEP.Validator("#fileForm", validOptions);
		
	});
	
	// 
	meetingRoomGuide = function() {
		$jq.ajax({
		    url : '<c:url value="/lightpack/planner/calendar/chkDownloadExcelMeetingRoom.do"/>',
		    type : "get",
		    success : function(result) {
		    	if( result > 0 ){
		    		$("#saveForm").submit();
		    	}else{
		    		alert("예약시스템에 연동할 회의실이 없습니다.");
		    	}
		    }
		}).error(function(event, request, settings) { alert("error"); });
	};
	
})(jQuery);  


	
</script>

<SCRIPT LANGUAGE="JavaScript">
var fileCnt = 0;

function CyberdigmpopupSelect( action) { 
	        var iframe = document.getElementById( "select_dialog");
	        var callbackFn = 'CyberdigmselectItem';

	        //팝업 설정( 해당 다이얼로그에 맞도록 수정)
	        var settings = "&settings=width:665,height:480,location:0,menubar:0,resizable:0,scrollbars:0,status:0,toolbar:0";

	        iframe.src = "<c:url value='/base/common/destinySLO.jsp?TARGET_URL=popup&action='/>" + action + "&callBack=" + callbackFn + settings;
	    }

function CyberdigmselectItem( _p, type) {
	$jq("#ecmFileName").remove();
	$jq("#ecmFileUrl2").remove();
	var data = eval( "(" + decodeURIComponent( _p) + ")");

	if(data.length > 1){
		alert("하나의 파일만 선택 가능합니다.");
		return;
	}else{
		for( var i = 0; i < data.length; i++){
	
	    	//내부URL(에이전트 설치 시)
	        var fileUrl1 = "http://127.0.0.1:36482/viewFile?fileName=" + encodeURIComponent( data[i].fileName) + "&docID=" + data[i].targetOID + "&fileID_=" + data[i].OID + "&history=true&overWrite=true&recently=true&clientType=I";
	
	    	//외부URL(에이전트 미설치 시)
	
	    	var index = data[i].fileFullPath.indexOf( '?');
	
	    	var str = data[i].fileFullPath.substring( index + 1, data[i].fileFullPath.length);
	
	    	//개발서버
	        //var fileUrl2 = data[i].fileFullPath;
	
	    	//운영
	        var fileUrl2 =  "http://ecm.moorim.co.kr:80/servlet/blob?" + str;
	
	    	//모바일용 외부 URL
	    	var fileUrl3 = data[i].fileFullPath;
	
	        fileCnt++;
	        
	        $jq("#ecmFile").append(
						"<input name=\"ecmFileName\" id=\"ecmFileName\" type=\"text\" value=\""+data[i].fileName+"\" class=\"inputbox w60\" readonly=\"readonly\" />"+
						"<input name=\"ecmFileUrl2\" id=\"ecmFileUrl2\" type=\"hidden\" value=\""+fileUrl2+"\" />"
				);
	    }
	}
   
}	

</SCRIPT>

		<!--popup Start-->
		<div id="popup">
		
				<!--popup_title Start-->
				<div id="popup_title_2">
                    <div class="popup_bgTitle_l"></div>
					<h1><ikep4j:message pre='${preButton2}' key='companyExcel' /></h1>
					<a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
				</div>
				<!--//popup_title End-->
				
				
				<!--popup_contents Start-->
				<div id="popup_contents">
				
							
					<!--blockDetail Start-->
					<div class="blockDetail">
						<form id="saveForm" method="post" action="<c:url value='/lightpack/planner/calendar/downloadExcelMeetingRoom.do'/>">
							<input name="targetUserId" type="hidden" value="${user.userId}" />
						</form>				
					
						<form id="fileForm" method="post" action="<c:url value="/lightpack/planner/calendar/companyScheduleEcmExcelUpload.do"/>" enctype="multipart/form-data">
							
							<input type="hidden" name="token" value="${token}"/>

							<!--directive Start-->
							<div class="directive"> 
								<ul>
									<li><span style="color:#bf000f"><ikep4j:message pre="${userListPrefix}" key="directive.download" /></span></li>	
									<li><span style="color:#bf000f"><ikep4j:message pre="${userListPrefix}" key="directive.notice" /></span></li>	
									<li><span style="color:#bf000f">회의실 예약과 연동하여 등록 할때는 [시스템_예약가능_회의실_목록.xls]를 다운받아 참고하여 [회의실 키]값을 입력하십시요.</span></li>	
								</ul>
							</div>
							<!--//directive End-->
							
							<table summary="<ikep4j:message pre="${preButton2}" key="companyExcel" />">
							<caption></caption>
								<colgroup>
									<col width="25%"/>
									<col width="60%"/>
									<col width="15%"/>
								</colgroup>
					
							<tbody>
							
								<tr>
									<th scope="row" ><ikep4j:message pre='${preDetail}' key='fileForm' /></th>
									<td colspan="2">
										<a href="<c:url value="/base/excel/company_schedule_sample.xls"/>">
											<img  src="<c:url value='/base/images/icon/ic_xls.gif' />"> company_schedule_sample.xls
										</a>&nbsp;&nbsp;
										<a href="javascript:meetingRoomGuide()">
											<img  src="<c:url value='/base/images/icon/ic_xls.gif' />"> 시스템_예약가능_회의실_목록.xls
										</a>
									</td>
								</tr>								
								
								<tr>
									<th scope="row" ><ikep4j:message pre='${preDetail}' key='fileUpload' /></th>
									<td id="ecmFile" style="border-right-color:white;">
										<!-- <input type="file" name="file" id="file" class="file" /> -->
									</td>
									<td style="border-left-color:white;">
										<img src="<c:url value="/base/images/dextupload/btn_fileplus_normal.gif"/>" id="imgBtn_fileAdd" name="Image2"  border="0" onclick="CyberdigmpopupSelect('selectAllFiles');" style="cursor:pointer;valign:absmiddle" />
									</td>
								</tr>
											
							</tbody>					
							
							</table>
						</form>
							
							
					</div>
					<!--//blockDetail End-->
															
					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<li><a class="button" id="submitBtn" href="#"><span><ikep4j:message pre='${preButton}' key='process' /></span></a></li>
							<li><a class="button" id="cancelBtn" href="#"><span><ikep4j:message pre='${preButton}' key='close' /></span></a></li>
						</ul>
					</div>
					<!--//blockButton End-->
					
				
			       
				
				</div>
				<!--//popup_contents End-->
			 
				<!--popup_footer Start-->
				<div id="popup_footer"></div>
				<!--//popup_footer End-->
				
					
		
		</div>
		<!--//popup End-->
		<iframe width="0" height="0" src="<c:url value="/base/common/destinySLO.jsp?TARGET_URL=install"/>"></iframe>
		<iframe width="0" height="0" src="<c:url value="/base/common/file_sample.jsp"/>"></iframe>
		
		<iframe id="select_dialog" src="" style="display:none;"></iframe>
		
	