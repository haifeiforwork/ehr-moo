<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.addressbook.header" /> 
<c:set var="prePrivate"    value="ui.support.addressbook.addrgroup.private" />
<c:set var="prePublic"  value="ui.support.addressbook.addrgroup.public" />

<c:set var="preImport"  value="ui.support.addressbook.import.list" />

<c:set var="preImpButton" value="ui.support.addressbook.import.button" />
<c:set var="preImpMessage"  value="message.support.addressbook.import.list" />

<c:set var="preSumMessage"  value="message.support.addressbook.summary" />
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript" src="<c:url value="/base/js/units/support/addressbook/addressbook.js"/>"></script>
<script type="text/javascript">

var fileCnt = 0;

function CyberdigmpopupSelect( action) { 
	        var iframe = document.getElementById( "select_dialog");
	        var callbackFn = 'CyberdigmselectItem';

	        //팝업 설정( 해당 다이얼로그에 맞도록 수정)
	        var settings = "&settings=width:665,height:480,location:0,menubar:0,resizable:0,scrollbars:0,status:0,toolbar:0";

	        iframe.src = "<c:url value='/base/common/destinySLO.jsp?TARGET_URL=popup&action='/>" + action + "&callBack=" + callbackFn + settings;
	    }

function CyberdigmselectItem( _p, type) {
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
						"<input name=\"ecmFileName\" id=\"ecmFileName\" type=\"text\" value=\""+data[i].fileName+"\" class=\"inputbox w100\" readonly=\"readonly\" />"+
						"<input name=\"ecmFileUrl1\" id=\"ecmFileUrl1\" type=\"hidden\" value=\""+fileUrl1+"\" />"+
						"<input name=\"ecmFileUrl2\" id=\"ecmFileUrl2\" type=\"hidden\" value=\""+fileUrl2+"\" />"+
						"<input name=\"ecmFilePath\" id=\"ecmFilePath\" type=\"hidden\" value=\""+data[i].fileFullPath+"\" />"+
						"<input name=\"ecmFileId\" id=\"ecmFileId\" type=\"hidden\" value=\""+data[i].OID+"\" />"
				);
	    }
	}
    
}	
<!--
(function($) {
	
	$jq(document).ready(function() {
		
		// 화면 로딩시 각각 페이지 호출 시작
		//Addressbook.getLeftMenuView();
		Addressbook.getPrivateAddrgroupView('${addrgroupId}');
		Addressbook.getPrivatePaddrgroupView('${addrgroupId}');
		Addressbook.getPublicAddrgroupView('${addrgroupId}');
		Addressbook.getTeamAddrgroupView('${addrgroupId}');
		iKEP.setLeftMenu();
		// 화면 로딩시 각각 페이지 호출 종료
		
		
		$jq("#checkedAll").click(function() {

	 		if ($jq("#checkedAll").is(":checked")) {
	 			$jq('input:not(checked)').attr("checked", true);
			}else{
		   		$jq('input:checked').attr("checked", false);
		    }
	    });

	});
	
	goAddrbookMain = function() {
		document.location.href = "<c:url value='/support/addressbook/addressbookHome.do'/>" ;
	};
	
	// File Upload Files
	uploadAddressbookFile = function() {

		var fileName = $jq("input[name=file]").val();
		var fileType = $jq("input[name=step1]:radio:checked").val();

		var uploadFlag = false;
		
		if( fileName == "" ) {
			alert('파일을 선택해 주세요');
			return;
		}else{
			var fileExt = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
			if( fileType == "vcard"){
				if( fileExt == "vcf" ){
					uploadFlag = true;
				}else{
					uploadFlag = false;
					alert("<ikep4j:message pre='${preImpMessage}' arguments='.vcf' key='notmatchFile'/>");
				}
			}else if( fileType == "outlook" || fileType == "expoutlook" ){
				if( fileExt == "csv" ){
					uploadFlag = true;
				}else{
					uploadFlag = false;
					alert("<ikep4j:message pre='${preImpMessage}' arguments='.csv' key='notmatchFile'/>");
				}
			}else if( fileType == "excelfile"){
				if( fileExt == "xls" ){
					uploadFlag = true;
				}else{
					uploadFlag = false;
					alert("<ikep4j:message pre='${preImpMessage}' arguments='.xls' key='notmatchFile'/>");
				}
			}
			
			
			if(uploadFlag){
				$jq('#fileForm').submit();
			}
		}
	};
	
	uploadAddressbookFileEcm = function() {
		$jq("#fileForm").attr("action", "<c:url value="/support/addressbook/listAddrbookImportEcm.do"/>");
		$jq('#fileForm').submit();
	};
	
	// Save Upload Files
	saveAddressbookFile = function() {
		
		var countCheckBox = $("input:checkbox:checked").length;

		if(countCheckBox>0){
			
			$jq.ajax({
			    url : '<c:url value="/support/addressbook/saveAddressbookFile.do"/>',
			    data : $("#saveForm").serialize(),
			    type : "post",
			    success : function(result) {
			    	alert("<ikep4j:message pre='${preImpMessage}' arguments='.vcf' key='save'/>");
		        	Addressbook.getAddrbookImport();
			    }
			}).error(function(event, request, settings) { alert("error"); });
			
		}else{
		    alert("<ikep4j:message pre='${preImpMessage}' arguments='.vcf' key='noselect'/>");
		}

	};
	
	// File Upload Files
	cancelAddressbookFile = function() {

		$jq("input[name=file]").attr("value","");
		$jq('#fileForm').submit();
		
	};
	
})(jQuery);  
//-->
</script>

		<!--blockMain Start-->
		<div id="blockMain">
 
			<!--leftMenu Start-->
			<div id="leftMenu">
				<h1 class="none"><ikep4j:message pre='${preHeader}' key='leftmenu'/></h1>
				<h2><a href="#a" onclick="goAddrbookMain()"><ikep4j:message pre='${preHeader}' key='english.title'/></a></h2>
				<div class="blockButton_2 mb10"> 
					<a class="button_2" onclick="Addressbook.editPerson('','P','');" href="#a"><span><img src="<c:url value='/base/images/icon/ic_addressbook.gif'/>" alt="" /><ikep4j:message pre='${prePrivate}' key='newPerson.title'/></span></a>				
				</div>		
				<div class="left_fixed">
					<ul>
						<li class="liFirst opened licurrent"><a href="#a"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${user.userName}</c:when><c:otherwise>${user.userEnglishName}</c:otherwise></c:choose><ikep4j:message pre='${prePrivate}' key='title'/></a>
							<ul class="boxList_child private_group">
								<li>&nbsp;</li>
							</ul>
							<div class="addr_setting">
								<a href="#a" title="setting" onclick="Addressbook.getAddrgroupList('P')"><ikep4j:message pre='${prePrivate}' key='setting.title'/></a> 
							</div>
							<div class="planner_leftbtn">
								<span class="btn_planner_import"><a href="#a" onclick="Addressbook.getAddrbookImport('P');"><ikep4j:message key='ui.support.addressbook.import.button.import'/></a></span>
								<span class="btn_planner_export"><a href="#a" onclick="Addressbook.getAddrbookExport('P');"><ikep4j:message key='ui.support.addressbook.export.button.export'/></a></span>
							</div>	
						</li>	
						<li class="liFirst opened licurrent"><a href="#a"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${user.userName}</c:when><c:otherwise>${user.userEnglishName}</c:otherwise></c:choose><ikep4j:message pre='${prePrivate}' key='grouptitle'/></a>
							<ul class="boxList_child prip_group">
								<li>&nbsp;</li>
							</ul>
							<div class="addr_setting">
								<a href="#a" title="setting" onclick="Addressbook.getAddrgroupList('G')"><ikep4j:message pre='${prePrivate}' key='setting.title'/></a> 
							</div>
							<div class="planner_leftbtn">
								<span class="btn_planner_import"><a href="#a" onclick="Addressbook.getAddrbookImport('G');"><ikep4j:message key='ui.support.addressbook.import.button.import'/></a></span>
								<span class="btn_planner_export"><a href="#a" onclick="Addressbook.getAddrbookExport('G');"><ikep4j:message key='ui.support.addressbook.export.button.export'/></a></span>
							</div>
						</li>		
						<li class="liFirst no_child"><a href="#a" onclick="Addressbook.getAddrHome('${user.groupId}','T','${user.teamName}');">${user.teamName}<ikep4j:message pre='${prePrivate}' key='title'/></a></li>	
						<li class="opened"><a href="#a"><ikep4j:message pre='${prePublic}' key='title'/></a>
							<ul class="boxList_child public_group">
								<li>&nbsp;</li>
							</ul>				
						</li>
						
						<c:if test="${publicManageFlag == 'true'}">
						<li class="opened"><a href="#a"><ikep4j:message pre='${prePublic}' key='administrator.title'/></a>
							<ul>
								<li class="no_child liLast"><a href="#a" onclick="Addressbook.getAddrgroupList('O');"><ikep4j:message pre='${prePublic}' key='management.title'/></a></li>
								<li class="no_child liLast"><a href="#a" onclick="Addressbook.editCategory();">공용그룹 카테고리 관리</a></li>
							</ul>					
						</li>
						</c:if>											
					</ul>															
				</div>
			</div>	
			<!--//leftMenu End-->


			<!--mainContents Start-->
			<div id="mainContents" >
				<h1 class="none"><ikep4j:message pre='${preHeader}' key='contents'/></h1>
				
				<!--subTitle_2 Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${preImport}' key='title'/></h2>
				</div>
				<!--//subTitle_2 End-->				
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${preImport}' key='step1'/></h3>
				</div>
				<!--//subTitle_2 End-->	
				
				<!--addressBox1 Start-->				
				<div class="addressBox1 addressBox1_c">
					<form name="fileForm" id="fileForm" method="post" action="<c:url value="/support/addressbook/listAddrbookImport.do"/>" enctype="multipart/form-data">
					<!-- label class=" cvsfileradiowrap cvsphone" for="phoneradio"><input id="phoneradio" class="filetype" onclick=""  type="radio" title="<ikep4j:message pre='${preImport}' key='step1.vcard'/>" name="step1" value="vcard" <c:if test="${step1=='vcard'}">checked="checked"</c:if> /> <span><ikep4j:message pre='${preImport}' key='step1.vcard' /></span> </label>
					<label class=" cvsfileradiowrap cvsexcel" for="excelradio"><input id="excelradio" class="filetype" onclick="" type="radio" title="<ikep4j:message pre='${preImport}' key='step1.excelfile'/>" name="step1" value="excelfile" <c:if test="${step1=='excelfile'}">checked="checked"</c:if> /> <span><ikep4j:message pre='${preImport}' key='step1.excelfile' /></span> </label>
                    <label class=" cvsfileradiowrap cvsoutlook" for="outlookradio"><input id="outlookradio" class="filetype" onclick="" type="radio" title="<ikep4j:message pre='${preImport}' key='step1.outlook'/>" name="step1" value="outlook" <c:if test="${step1=='outlook'}">checked="checked"</c:if> /> <span><ikep4j:message pre='${preImport}' key='step1.outlook' /></span> </label>
					<label class=" cvsfileradiowrap cvsexpress" for="expressradio"><input id="expressradio" class="filetype" onclick="" type="radio" title="<ikep4j:message pre='${preImport}' key='step1.expoutlook'/>" name="step1" value="expoutlook" <c:if test="${step1=='expoutlook'}">checked="checked"</c:if> /> <span><ikep4j:message pre='${preImport}' key='step1.expoutlook' /></span> </label>
					<div class="clear"></div>
					<div class="addressBox1_line"></div-->
					<c:if test="${!ecmrole || user.essAuthCode == 'E9'}">
					<div class="fileselectwrap">
						<strong><ikep4j:message pre='${preImport}' key='step1.selectFile'/></strong> 
						<input type="file" name="file" id="file" class="file"  size="68" title="<ikep4j:message pre='${preImport}' key='step1.selectFile'/>" style="margin-top:16px;"/>
					</div>
					</c:if>
					<c:if test="${ecmrole && user.essAuthCode != 'E9'}">
					<div class="fileselectwrap" id="ecmFile">
					<a class="button_s" href="#a" onclick="CyberdigmpopupSelect('selectAllFiles');" style="vertical-align:middle;"><span>파일찾기</span></a>
					</div>
					</c:if>
					<input type="hidden" name="pgroupType" id="pgroupType" value="${gType}" />
					<input type="hidden" title="" name="addrgroupName" id="addrgroupName" value="${addrgroupName}" />
                    <input type="hidden" title="" name="addrgroupNavigation" id="addrgroupNavigation" value="${addrgroupNavigation}" /> 					</form>
				</div>		
				<!--//addressBox1 End-->	
				
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<c:if test="${!ecmrole || user.essAuthCode == 'E9'}">
						<li><a class="button" href="#a" onclick="uploadAddressbookFile();"><span><ikep4j:message pre='${preImpButton}' key='import'/></span></a></li>
						</c:if>
						<c:if test="${ecmrole && user.essAuthCode != 'E9'}">
						<li><a class="button" href="#a" onclick="uploadAddressbookFileEcm();"><span><ikep4j:message pre='${preImpButton}' key='import'/></span></a></li>
						</c:if>
					</ul>
				</div>
				<!--//blockButton End-->		
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${preImport}' key='step2'/></h3>
				</div>
				<!--//subTitle_2 End-->	
 
				<!--addressBox1 Start-->				
				<div class="addressBox1 addressBox1_c pty1">
					
					<form id="saveForm" action="" method="post" onsubmit="return false">
					<input type="hidden" name="pgroupTp" id="pgroupTp" value="${groupType}" />
					<!--blockDetail Start-->
					<div class="blockListTable">
									
						<table summary="<ikep4j:message pre='${preSumMessage}' key='addrManageTable'/>">
							<thead>
								<tr>
									<th scope="col" width="5%"><input id="checkedAll" name="input" class="checkbox" title="checkbox" type="checkbox" value="" /></th>
									<th scope="col" width="14%"><ikep4j:message pre='${preImport}' key='step2.companyName'/></th>
									<th scope="col" width="14%"><ikep4j:message pre='${preImport}' key='step2.teamName'/></th>
									<th scope="col" width="14%"><ikep4j:message pre='${preImport}' key='step2.addrPersonName'/></th>
									<th scope="col" width="14%"><ikep4j:message pre='${preImport}' key='step2.jobRankName'/></th>
									<th scope="col" width="13%"><ikep4j:message pre='${preImport}' key='step2.officePhoneno'/></th>
									<th scope="col" width="13%"><ikep4j:message pre='${preImport}' key='step2.mobilePhoneno'/></th>
									<th scope="col" width="13%"><ikep4j:message pre='${preImport}' key='step2.emailAddress'/></th>                                   
                         		</tr>
							</thead>
							<tbody>
							<c:choose>
								<c:when test="${personList.personListSize <= 0}">
									<tr>
									<td colspan="7">
										<ikep4j:message pre='${preImpMessage}' key='nodata'/>
									</td>
								</tr>			        
								</c:when>
								<c:otherwise> 
								
								<c:forEach var="persons" items="${personList.personList}" varStatus="status"> 
								<tr>
									<spring:bind path="personList.personList[${status.index}].personId"><input type="hidden" name="${status.expression}" value="${status.value}"></spring:bind>
									<spring:bind path="personList.personList[${status.index}].personName"><input title="<ikep4j:message pre='${prePriList}' key='personName'/>" type="hidden" name="${status.expression}" value="${status.value}"></spring:bind>
									<spring:bind path="personList.personList[${status.index}].jobRankName"><input title="<ikep4j:message pre='${prePriList}' key='jobRankName'/>" type="hidden" name="${status.expression}" value="${status.value}"></spring:bind>
									<spring:bind path="personList.personList[${status.index}].companyName"><input title="<ikep4j:message pre='${prePriList}' key='companyName'/>" type="hidden" name="${status.expression}" value="${status.value}"></spring:bind>
									<spring:bind path="personList.personList[${status.index}].teamName"><input title="<ikep4j:message pre='${prePriList}' key='teamName'/>" type="hidden" name="${status.expression}" value="${status.value}"></spring:bind>
									<spring:bind path="personList.personList[${status.index}].mobilePhoneno"><input title="<ikep4j:message pre='${prePriList}' key='mobilePhoneno'/>" type="hidden" name="${status.expression}" value="${status.value}"></spring:bind>
									<spring:bind path="personList.personList[${status.index}].officePhoneno"><input title="<ikep4j:message pre='${prePriList}' key='officePhoneno'/>" type="hidden" name="${status.expression}" value="${status.value}"></spring:bind>
									<spring:bind path="personList.personList[${status.index}].mailAddress"><input title="<ikep4j:message pre='${prePriList}' key='mailAddress'/>" type="hidden" name="${status.expression}" value="${status.value}"></spring:bind>
									<spring:bind path="personList.personList[${status.index}].personMemo"><input title="<ikep4j:message pre='${prePriList}' key='personMemo'/>" type="hidden" name="${status.expression}" value="${status.value}"></spring:bind>
									
									<td class="textCenter">
										<spring:bind path="personList.personList[${status.index}].checkFlag">
											<input class="checkbox" title="checkbox" type="checkbox" title="" name="${status.expression}" value="true" />
										</spring:bind>
									</td>
									<td><c:out value="${persons.companyName}"/></td>
									<td><c:out value="${persons.teamName}"/></td>
									<td><c:out value="${persons.personName}"/></td>
									<td><c:out value="${persons.jobRankName}"/></td>
									<td><c:out value="${persons.officePhoneno}"/></td>
									<td><c:out value="${persons.mobilePhoneno}"/></td>
									<td><c:out value="${persons.mailAddress}"/></td>
								</tr>
								</c:forEach>
								
								</c:otherwise>
							</c:choose>	

							</tbody>
						</table>
					</div>
					<!--//blockDetail End-->	
					<div class="blockBlank_10px"></div>	
					<c:if test="${gType == 'P'||groupType == 'P'}">						
					<div>
						<strong><ikep4j:message pre='${preImport}' key='step2.addrgroupName'/> : </strong> 
						<select name="addrgroupId" class="multi w80" title="<ikep4j:message pre='${prePerDetail}' key='addrgroupName'/>" >
							<option value="" selected="selected"><ikep4j:message pre='${prePrivate}' key='undiffer.title'/></option>
							<c:forEach var="addrgroup" items="${addrgroupList.entity}" varStatus="status">
							<option value="${addrgroup.addrgroupId}"><c:out value='${addrgroup.addrgroupName}'/></option>
							</c:forEach>				
						</select> 
					</div>
					</c:if>
					<c:if test="${gType == 'G'||groupType == 'G'}">	
					<div>
						<strong><ikep4j:message pre='${preImport}' key='step2.addrgroupName'/> : </strong> 
						<select name="addrgroupId" class="multi w80" title="<ikep4j:message pre='${prePerDetail}' key='addrgroupName'/>" >
							<c:forEach var="paddrgroup" items="${paddrgroupList.entity}" varStatus="status">
							<option value="${paddrgroup.addrgroupId}"><c:out value='${paddrgroup.addrgroupName}'/></option>
							</c:forEach>				
						</select> 
					</div>
					</c:if>
					</form>
				</div>		
				<!--//addressBox1 End-->	
				
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li><a class="button" href="#a" onclick="saveAddressbookFile()"><span><ikep4j:message pre='${preImpButton}' key='save'/></span></a></li>
						<li><a class="button" href="#a" onclick="cancelAddressbookFile()"><span><ikep4j:message pre='${preImpButton}' key='cancel'/></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->	
				
				<!--directive Start-->
				<div class="directive"> 
					<ul>
						<!-- li><span><ikep4j:message pre='${preImport}' key='context1'/></span></li-->	
						<li><span><ikep4j:message pre='${preImport}' key='context2'/></span></li>	
						<li class="ty1"><span>
						<c:if test="${gType == 'G'||groupType == 'G'}">	
							<a href="#a" onclick="Addressbook.addrbookTeamplatePopup('G');"><ikep4j:message pre='${preImport}' key='context3'/></a>
						</c:if>
						<c:if test="${gType == 'P'||groupType == 'P'}">
							<a href="#a" onclick="Addressbook.addrbookTeamplatePopup('P');"><ikep4j:message pre='${preImport}' key='context3'/></a>
						</c:if>		
						</span></li>																		
					</ul>
				</div>
				<!--//directive End-->																					
					
			</div>
			<!--//mainContents End-->
			<div class="clear"></div>
			<input style="visibility:hidden" id="excelradio" class="filetype" onclick="" type="radio" title="<ikep4j:message pre='${preImport}' key='step1.excelfile'/>" name="step1" value="excelfile" <c:if test="${step1=='excelfile'}">checked="checked"</c:if> />
            <input style="visibility:hidden" id="outlookradio" class="filetype" onclick="" type="radio" title="<ikep4j:message pre='${preImport}' key='step1.outlook'/>" name="step1" value="outlook" <c:if test="${step1=='outlook'}">checked="checked"</c:if> /> 
                    

		</div>
		<c:if test="${ecmrole && user.essAuthCode != 'E9'}">
		<iframe width="0" height="0" src="<c:url value="/base/common/destinySLO.jsp?TARGET_URL=install"/>"></iframe>
		<iframe id="select_dialog" src="" style="display:none;"></iframe>			
		</c:if>
