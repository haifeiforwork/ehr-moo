<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.board.boardItem.updateBoardView.header" /> 
<c:set var="preDetail"  value="message.collpack.collaboration.board.boardItem.updateBoardView.detail" />
<c:set var="preCode"    value="message.collpack.collaboration.common.code" />
<c:set var="preButton"  value="message.collpack.collaboration.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.boardItem" />
<c:set var="preSearch"  value="message.collpack.collaboration.common.searchCondition" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
 <%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script> 
<%@ include file="/base/common/DextfileUploadInit.jsp"%><%-- 파일업로드용 Import --%> 

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
<c:set var="user"   value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />	

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
        
        $jq("#ecmTb").append(
				"<tr id=\"ecmTr_"+data[i].OID+"\">"+
				"<td><input name=\"ecmCheck\" type=\"checkbox\" class=\"checkbox\" value=\""+data[i].OID+"\" /></td>"+
					"<td><input name=\"ecmFileName_"+data[i].OID+"\" id=\"ecmFileName_"+data[i].OID+"\" type=\"text\" value=\""+data[i].fileName+"\" class=\"inputbox w100\" readonly=\"readonly\" /></td>"+
					"<td><input name=\"ecmFileUrl1_"+data[i].OID+"\" id=\"ecmFileUrl1_"+data[i].OID+"\" type=\"text\" value=\""+fileUrl1.replaceAll("'","")+"\" class=\"inputbox w100\" readonly=\"readonly\" /></td>"+
					"<input name=\"ecmFileUrl2_"+data[i].OID+"\" id=\"ecmFileUrl2_"+data[i].OID+"\" type=\"hidden\" value=\""+fileUrl2.replaceAll("'","")+"\" />"+
					"<input name=\"ecmFilePath_"+data[i].OID+"\" id=\"ecmFilePath_"+data[i].OID+"\" type=\"hidden\" value=\""+data[i].fileFullPath+"\" />"+
					"<input name=\"ecmFileId_"+data[i].OID+"\" id=\"ecmFileId_"+data[i].OID+"\" type=\"hidden\" value=\""+data[i].OID+"\" />"+
				"</tr>"
			);
    }
    
    iKEP.iFrameContentResize();
}		
<!--  
var ecmFlg = "N";
var useActXEditor = "${useActiveX}";
(function($){	 
	$(document).ready(function() { 
		<c:if test="${ecmrole && user.essAuthCode != 'E9'}">
		$("#ecmBtn").show();
     	$("#ecmTb").show();
		var xmlhttp; 
		  
	    if(window.XMLHttpRequest){ // code for IE7+, Firefox, Chrome, Opera, Safari 
	        xmlhttp=new XMLHttpRequest(); 
	    }else{ // code for IE6, IE5 
	        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP"); 
	    } 
	    xmlhttp.open("POST","http://127.0.0.1:36482/",true); 
	    //Request에 따라서 적절한 헤더 정보를 보내준다 
	    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded"); 
	    xmlhttp.send(); 
	  
	    xmlhttp.onreadystatechange=function(){ 
	    	if (xmlhttp.readyState==4 && xmlhttp.status==200){ 
	        	 //$("#normalFileTb").hide();
	          	 //$("#ecmBtn").show();
	          	 //$("#ecmTb").show();
	          	ecmFlg = "Y";
	        }else if(xmlhttp.status==12029){ 
	          	 $("#ecmBtn").hide();
	          	 $("#ecmTb").hide();
	          	 $("#normalFileTb").show();
			} 
	    } 
	    </c:if>
	    <c:if test="${!ecmrole || user.essAuthCode == 'E9'}">
	    	$("#normalFileTb").show();
	    </c:if>
		if(window.parent.topScroll != null) {
			window.parent.topScroll(); 	
		} 
		
		if(window.parent.menuMark != null) { 
			window.parent.menuMark("${boardItem.boardId}");
		}
		
		$("input[name=startDate]").attr("readonly", true).datepicker({
			onSelect : function(){
				var form = $(this)[0].form;
				var validator = $(form).validate();
				validator.element(this);
				validator.element("input[name=endDate]", form);
			},
		    showOn: "button",
		    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true,
		    buttonText : "<ikep4j:message pre='${preDetail}' key='startDate' />"
		});   
		
		$("input[name=endDate]").attr("readonly", true).datepicker({
			onSelect : function(){
				var form = $(this)[0].form;
				var validator = $(form).validate();
				validator.element(this);
				validator.element("input[name=startDate]", form);
			},
		    showOn: "button",
		    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true,
		    buttonText : "<ikep4j:message pre='${preDetail}' key='endDate' />"
		});    
		
		var $select = $("#targetList"), userInfo, groupInfo;
		<c:forEach var="targetUser" items="${boardItem.targetUser}">
			userInfo = {
				type : "user",
				id : "${targetUser.targetGroupId}",
				empNo : "",
						userName : "${targetUser.targetGroupName}",
						jobTitleName : "",
						teamName : "",
				email : "",
				mobile : "",
				group : ""
			};

			$.tmpl(iKEP.template.userOption, userInfo).appendTo($select)
				.data("data", userInfo);
		</c:forEach>
		$jq("#totalMember").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$select.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalMemberMsg' />");
		
		$select = $("#targetGroupList");
		<c:forEach var="targetGroup" items="${boardItem.targetGroup}">
			groupInfo = {
				type : "group",
				code : "${targetGroup.targetGroupId}",
				name : "${targetGroup.targetGroupName}",
				parent : ""
			};

			$.tmpl(iKEP.template.groupOption, groupInfo).appendTo($select)
				.data("data", groupInfo);
		</c:forEach>
		$jq("#totalGroup").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$select.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalGroupMsg' />");
		
		
		$jq("input[name=followBoardPermission]").click(function() {
			if($jq("input[name=followBoardPermission]:checked").val()==0)
			{
				$jq('#readPermissionDiv').show();
				iKEP.iFrameContentResize();
			}
			else{
				$('#readPermissionDiv').hide();
				$jq('#readPermissionList').empty();
				$jq('input[name=searchSubFlag]:hidden').val("0") ;
				iKEP.iFrameContentResize();
			}
		});
		
		$jq("input[name=readPermission]").click(function() {
			if($jq("input[name=readPermission]:checked").val()==4)
			{
				$jq('#readPermissionListDiv').show();
				iKEP.iFrameContentResize();
				
			}
			else{
				$('#readPermissionListDiv').hide();
				$jq('#readPermissionList').empty();
				$jq('input[name=searchSubFlag]:hidden').val("0") ;
				iKEP.iFrameContentResize();
			}
		});
		
		$jq("#deleteReadPermissionButton").click(function(event) {
			event.preventDefault();
			var $rPermissionList=$jq('#readPermissionList');
			$jq('option:selected',$rPermissionList).remove();
		});			
		
		$jq("#addReadPermissionButton").click(function() {
			// 조직도 팝업 테스트
			var dbItems = [];
			
			<c:if test="${!empty boardItem.aclReadPermissionList.groupDetailList}">
			<c:forEach var="item" items="${boardItem.aclReadPermissionList.groupDetailList}">
			dbItems.push({// 공통필수
				type:"group",				
				code:"${item.groupId }",
				name:"${item.groupName}",
				parent:"${item.parentGroupId }",
				groupTypeId:"${item.groupTypeId}",
				hasChild:"${item.childGroupCount}",
				searchSubFlag:<c:if test="${boardItem.searchSubFlag==1}">true</c:if><c:if test="${boardItem.searchSubFlag!=1}">false</c:if>
			});			
			</c:forEach>
			</c:if>
			
			<c:if test="${!empty boardItem.aclReadPermissionList.assignUserDetailList}">

			<c:forEach var="item" items="${boardItem.aclReadPermissionList.assignUserDetailList}">
			dbItems.push({// 공통필수
				type:"user",				
				id:"${item.userId }",
				jobTitle:"${item.jobTitleName }",
				name:"${item.userName }",
				searchSubFlag:false,
				teamName:"${item.teamName }"
			});	
			</c:forEach>
			</c:if>

		
			var items = [];
			var $sel = $jq("#boardItemForm").find("[name=readPermissionList]");
			
			$sel.children().each(function(idx) {
				//iKEP.debug(this);
				//iKEP.debug(dbItems[idx]);
				$jq.data(this, "data", dbItems[idx]);
			});
			
			$jq.each($sel.children(), function() {
				items.push($jq.data(this, "data"));
			});

			$controlType = $jq('input[name=controlType]:hidden').val() ;
			$controlTabType = $jq('input[name=controlTabType]:hidden').val() ;
			$selectType = $jq('input[name=selectType]:hidden').val() ;
			$selectMaxCnt = $jq('input[name=selectMaxCnt]:hidden').val() ;
			$searchSubFlag = $jq('input[name=searchSubFlag]:hidden').val() ;

			var url = "<c:url value='/support/addressbook/addresbookPopup.do'/>"+"?controlType=" + $controlType + "&controlTabType=" + $controlTabType + "&selectType=" + $selectType + "&selectMaxCnt=" + $selectMaxCnt + "&searchSubFlag=" + $searchSubFlag;
			var callback = function(result){
				$sel.empty();
				$jq.each(result, function() {

					var tpl = "";
					
					switch(this.type) {
						case "group" : tpl = "addrBookItemGroup"; break;
						case "user" : tpl = "addrBookItemUser"; break;
					}
					
					if(this.type=="group")
						this.code="G:"+this.code;
					else
						this.id ="U:"+this.id;
					
					var $option = $jq.tmpl(tpl, this).appendTo($sel);

					$jq.data($option[0], "data", this);

					if( this.searchSubFlag == true ){
						$jq('input[name=searchSubFlag]:hidden').val("1") ;
					}
		
				})
			};
			iKEP.showAddressBook(callback, items, {selectType:"all", selectElement:$sel, isAppend:false, tabs:{common:1}});
			
			$("#boardItemForm select[name=searchWorkPlaceName]").change(function() { 
				var workPlaceName = $("#searchForm select[name=searchWorkPlaceName]").val();
				$.post("<c:url value='/collpack/kms/admin/listTeamCodes.do'/>", {"workPlaceName" : workPlaceName}) 
				.success(function(data) {
					$("#searchGroupId").empty();
					$("#searchGroupId").append(data);
				})	
			}); 
			
		});	
		
		$jq("input[name=info]").click(function() {
			infoSum();
		}); 
		
		$jq("input[name=info1]").click(function() {
			$("input[name='info1']:checkbox").not($(this)).attr("checked",false);
			infoSum();
		});
		
		$jq("input[name=info2]").click(function() {
			$("input[name='info2']:checkbox").not($(this)).attr("checked",false);
			infoSum();
		});
		
		infoSum = function() {
			var mark = 0;
			var i = 0;	
			
			$("#boardItemForm input[name=info]").each(
				function(){
					
					if(this.checked){
						mark += Number(this.value);
						$("#boardItemForm input[name=itemDisplays_"+(i+1)+"]").val("1");
						if(i == 0){
							$("#boardItemForm input[name=status]").val("5");
						}
					}else{
						$("#boardItemForm input[name=itemDisplays_"+(i+1)+"]").val("0");
					}
					i++;
				}		
			)
			
			$("#boardItemForm input[name=info1]").each(
				function(){
					
					if(this.checked){
						mark += Number(this.value);
						$("#boardItemForm input[name=itemDisplays_"+(i+1)+"]").val("1");
					}else{
						$("#boardItemForm input[name=itemDisplays_"+(i+1)+"]").val("0");
					}
					i++;
				}		
			)
			
			$("#boardItemForm input[name=info2]").each(
				function(){
				
					if(this.checked){
						mark += Number(this.value);
						$("#boardItemForm input[name=itemDisplays_"+(i+1)+"]").val("1");
					}else{
						$("#boardItemForm input[name=itemDisplays_"+(i+1)+"]").val("0");
					}
					i++;
				}		
			)
			
			$jq('input[name=mark]').val(mark) ;
		};
		
		$("a.listBoardItemButton").click(function() {
			$("#normalFileTb").show();
		}); 
		
		$("a.saveBoardItemButton").click(function() {
			var tempEcmFileId = "";  
			var tempEcmFileName = ""; 
			var tempEcmFilePath = "";  
			var tempEcmFileUrl1 = "";    
			var tempEcmFileUrl2 = ""; 
			var ecmFileCnt = 0;
			var ecmFileId = "";
			var ecmFileName = "";
			var ecmFilePath = "";
			var ecmFileUrl1 = "";
			var ecmFileUrl2 = "";
			
			
			$("#boardItemForm input[name=ecmCheck]").attr("checked", true);  

			$("#boardItemForm input[name=ecmCheck]:checked").each(function(index) { 
				tempEcmFileId = $("#ecmFileId_"+$(this).val()).val();  
				tempEcmFileName = $("#ecmFileName_"+$(this).val()).val(); 
				tempEcmFilePath = $("#ecmFilePath_"+$(this).val()).val();   
				tempEcmFileUrl1 = $("#ecmFileUrl1_"+$(this).val()).val();     
				tempEcmFileUrl2 = $("#ecmFileUrl2_"+$(this).val()).val();  
				if(ecmFileCnt == 0){
					ecmFileId = tempEcmFileId;
					ecmFileName = tempEcmFileName;
					ecmFilePath = tempEcmFilePath;
					ecmFileUrl1 = tempEcmFileUrl1;
					ecmFileUrl2 = tempEcmFileUrl2;
				}else{
					ecmFileId = ecmFileId+"|"+tempEcmFileId;
					ecmFileName = ecmFileName+"|"+tempEcmFileName;
					ecmFilePath = ecmFilePath+"|"+tempEcmFilePath;
					ecmFileUrl1 = ecmFileUrl1+"|"+tempEcmFileUrl1;
					ecmFileUrl2 = ecmFileUrl2+"|"+tempEcmFileUrl2;
				}
				ecmFileCnt++;
			});
			
			$jq('input[name=ecmFileId]').val(ecmFileId);  
			$jq('input[name=ecmFileName]').val(ecmFileName); 
			$jq('input[name=ecmFilePath]').val(ecmFilePath);  
			$jq('input[name=ecmFileUrl1]').val(ecmFileUrl1);    
			$jq('input[name=ecmFileUrl2]').val(ecmFileUrl2); 
			//평가기준선택여부
			var i = 0;
			$("#boardItemForm input[name=info]").each(
				function(){
					if(this.checked){
						i++;
					}
				}
			)
			$("#boardItemForm input[name=info1]").each(
				function(){
					if(this.checked){
						i++;
					}
				}
			)
			$("#boardItemForm input[name=info2]").each(
				function(){
					if(this.checked){
						i++;
					}
				}
			)
			
			//지식확인선택여부
			var $sel1 = $jq("input[name=checkStatus1]:checked");
			//var $sel2 = $jq("input[name=checkStatus2]:checked");
			
			
			if($sel1.length < 1) {
				//alert("지식 확인을 선택해주세요.");
				//return;	
			}

			if(i != 0 && $sel1.length == 0 && $sel1.length == 0){
				
				//alert("지식 확인을 선택 해주세요.");
				//return false;
			}
			
			if($sel1.length > 0) {
				$("#boardItemForm input[name=status]").val("3"); //지식확인
				if(i == 0){
					//alert("평가기준을 선택 해주세요.");
					//return false;
				}
			}else{
				$("#boardItemForm input[name=status]").val("1"); //지식확인 반려
			}
			
			
			
			/* if($sel2.length > 0) {
				$("#boardItemForm input[name=status]").val("5"); //결재확인
				if(i == 0){
					alert("평가기준을 선택 해주세요.");
					return false;
				}
			} */
			
			var $sel2 = $jq("input[name=hopeGradeCheck]:checked");
			if($sel2.length > 0) {
				var hopeGrade = $("input[name=hopeGradeCheck]:checked").attr("value");
				$("#boardItemForm input[name=hopeGrade]").val(hopeGrade); 
			}else{
				alert("보안등급을 선택해 주세요.");
				return;
			}
			
			if($("#boardItemForm select[name=infoGrade]").val() == "E"){
				var iii;
				for(iii=1;iii<18;iii++){
					$("#boardItemForm input[name=itemDisplays_"+(iii)+"]").val("0");
				}
				$("#boardItemForm input[name=mark]").val("0") ;
			}
			
			infoSum();
			//alert($("#boardItemForm input[name=status]").val());
			$("#boardItemForm").trigger("submit"); 
			return false;
		}); 
		
		$("a.assessBoardItemButton").click(function() {

			var tempEcmFileId = "";  
			var tempEcmFileName = ""; 
			var tempEcmFilePath = "";  
			var tempEcmFileUrl1 = "";    
			var tempEcmFileUrl2 = ""; 
			var ecmFileCnt = 0;
			var ecmFileId = "";
			var ecmFileName = "";
			var ecmFilePath = "";
			var ecmFileUrl1 = "";
			var ecmFileUrl2 = "";
			
			
			$("#boardItemForm input[name=ecmCheck]").attr("checked", true);  

			$("#boardItemForm input[name=ecmCheck]:checked").each(function(index) { 
				tempEcmFileId = $("#ecmFileId_"+$(this).val()).val();  
				tempEcmFileName = $("#ecmFileName_"+$(this).val()).val(); 
				tempEcmFilePath = $("#ecmFilePath_"+$(this).val()).val();   
				tempEcmFileUrl1 = $("#ecmFileUrl1_"+$(this).val()).val();     
				tempEcmFileUrl2 = $("#ecmFileUrl2_"+$(this).val()).val();  
				if(ecmFileCnt == 0){
					ecmFileId = tempEcmFileId;
					ecmFileName = tempEcmFileName;
					ecmFilePath = tempEcmFilePath;
					ecmFileUrl1 = tempEcmFileUrl1;
					ecmFileUrl2 = tempEcmFileUrl2;
				}else{
					ecmFileId = ecmFileId+"|"+tempEcmFileId;
					ecmFileName = ecmFileName+"|"+tempEcmFileName;
					ecmFilePath = ecmFilePath+"|"+tempEcmFilePath;
					ecmFileUrl1 = ecmFileUrl1+"|"+tempEcmFileUrl1;
					ecmFileUrl2 = ecmFileUrl2+"|"+tempEcmFileUrl2;
				}
				ecmFileCnt++;
			});
			
			$jq('input[name=ecmFileId]').val(ecmFileId);  
			$jq('input[name=ecmFileName]').val(ecmFileName); 
			$jq('input[name=ecmFilePath]').val(ecmFilePath);  
			$jq('input[name=ecmFileUrl1]').val(ecmFileUrl1);    
			$jq('input[name=ecmFileUrl2]').val(ecmFileUrl2); 
			var assessorIdStr  = $("#boardItemForm input[name=assessorId]").val();
			var assessorNameStr = $("#boardItemForm input[name=assessorName]").val();
			if(assessorIdStr == "" ){
				alert("전문가를 설정해주세요.");
				return false;
			}
			
			var $sel2 = $jq("input[name=hopeGradeCheck]:checked");
			if($sel2.length > 0) {
				var hopeGrade = $("input[name=hopeGradeCheck]:checked").attr("value");
				$("#boardItemForm input[name=hopeGrade]").val(hopeGrade); 
			}else{
				alert("보안등급을 선택해 주세요.");
				return;
			}
			
			//alert("<c:url value='/collpack/kms/board/boardItem/assessMove.do?itemId=${boardItem.itemId}&status=2&assessorId="+assessorId+"&assessorName="+assessorName+"'/>")
			if(confirm("전문가에게 평가를 위임하시겠습니까?")) {
				
				$jq("#boardItemForm input[name=status]").val("2");
				//alert(assessorIdStr + " : " + assessorNameStr);
				$.get("<c:url value='/collpack/kms/board/boardItem/assessMove.do'/>" , {itemId :"${boardItem.itemId}",status:"2",assessorId:assessorIdStr, assessorName:assessorNameStr})
				.success(function(data) {
					location.href="<c:url value='/collpack/kms/board/boardItem/listAssessItemView.do?searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>";
				})
				.error(function(event, request, settings) { alert("error"); }); 	
			}
		}); 
		
		ecmfileDelete = function(){
			$("#boardItemForm input[name=ecmCheck]:checked").each(function(index) { 
				$jq("#ecmTr_"+$(this).val()).remove();
			});
		};
		
		$("#moveBoardItemButton1").live("click", function(){ 
			
			var isKnowhow = $("input[name=isKnowhow1]:checked").attr("value");
			var url = iKEP.getContextRoot() + "/collpack/kms/board/boardItem/viewBoardTreePopup.do?isKnowhow="+isKnowhow+"&orgBoardId=0";
			//var pageURL = "<c:url value='/collpack/kms/board/boardItem/viewBoardTreePopup.do' />?isKnowhow=0&orgBoardId=0";
			iKEP.popupOpen( url , {width:400, height:730 , callback:function(result){moveBoardItem1(result);}}, "MoveBoardItem");
		});
		
		
		moveBoardItem1 = function(result) {
			$('input[name="boardId"]').val(result);
			$.get("<c:url value='/collpack/kms/board/boardItem/getKmsMapName.do?boardId='/>" +result)
			.success(function(data) { 
				$("#kmsMapName").text(data);	
			})
			.error(function(event, request, settings) { alert("error"); });  
		};
		
		var $addHtml  = ""; 
		var $addFullHtml = "";	
		
		setExpert = function(tmpUserId, tmpUserName, tmpTeam, tmpJob) {
			$("#assessor").val(tmpUserName+" "+tmpJob+" "+tmpTeam);
			$("#assessorId").val(tmpUserId);
			$("#assessorName").val(tmpUserName);
		}
		
		
		$jq('#addPersonButton').live("click", function() {		
			var url = iKEP.getContextRoot() + '/collpack/kms/board/boardItem/expertView.do';   
			iKEP.popupOpen(url, {width:500, height:500}, "popview");
		});
		
		
		
		/*$jq('#addPersonButton').live("click", function(event) {			
			event.which = "13";			
			//$addHtml = $(this).parent().parent();
			$addHtml = $(this).parent().parent().parent().parent();
			iKEP.searchUser(event, "N", setUser);
		});	*/
	
		// editor 초기화
		initEditorSet();
		
		
		new iKEP.Validator("#boardItemForm", {   
			rules  : {
				title     		: {required : true, rangelength : [1, 1000] },
				targetSource	: {required : true},
				tag       		: {tagDuplicate : true}
			},
			messages : {
				title     		: {direction : "top",    required : "<ikep4j:message key="message.collpack.collaboration.NotNull.boardItem.title" />", rangelength : "<ikep4j:message key="message.collpack.collaboration.Size.boardItem.title" />"},
				targetSource	: {direction : "top",    required : "출처는 필수 입력항목입니다."},
				tag       		: {direction : "top", 	 tagDuplicate : "<ikep4j:message key="message.collpack.collaboration.TagDuplicate.boardItem.tag" />"} 
			}, 
			
		    submitHandler : function(form) {  
		    	
		    	$(form).find('select[name=targetGroupList] option').each(function(){
					this.selected = true;
				});
		    	
		    	$(form).find('select[name=targetList] option').each(function(){
					this.selected = true;
				});
	    			    	
		    	// ActiveX Editor 사용 여부가 Y인 경우
		    	if(useActXEditor == "Y"){
			    	if (!$.browser.msie) {
			    		//ekeditor 데이타 업데이트
			    		var editor = $("#contents").ckeditorGet(); 
						editor.updateElement();
						//에디터 내 이미지 파일 링크 정보 세팅
						createEditorFileLink("boardItemForm");
			    	} 			    	
		    	}else{
		    		//ekeditor 데이타 업데이트
		    		var editor = $("#contents").ckeditorGet(); 
					editor.updateElement();
					//에디터 내 이미지 파일 링크 정보 세팅
					createEditorFileLink("boardItemForm");
		    	}
				if($jq('input[name=followBoardPermission]:checked').val()==1){
					$jq('input[name=readPermission]').val('${board.readPermission}') ;
				}
			
				$jq("#readPermissionList>option").attr("selected",true);
						    	
		    	if(confirm("<ikep4j:message pre="${preMessage}" key="updateItem" />")) {
		    		if(ecmFlg == "N"){
		    		if((oldSizes !=document.getElementById("FileUploadManager").Size)||(document.all["FileUploadManager"].Count>0)){//삭제한것이 있거나 추가한것이 있으면
		    			btnTransfer_Onclick("boardItemForm");
		    		}else{
		    			//alert("파일업로드 없음");
		    			
		    			oldFileSetting(oldFiles , form);
		    			writeSubmit(form);
		    		}
		    		}else{
		    			writeSubmit(form);
		    		}
		    		



				}
		    }
		});    
		/*		
	    var uploaderOptions = {
	 		<c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if> 
			<c:if test="${board.fileAttachOption eq 0 and not empty board.allowType}">allowExt : "${board.allowType}",</c:if>
			<c:if test="${board.fileAttachOption eq 1 and not empty board.allowType}">allowFileType : "${board.allowType}",</c:if>
		    isUpdate : true,
	    	onLoad : function() {
	    		iKEP.iFrameContentResize();
	    	}
		};  
        
	    var fileController = new iKEP.FileController("#boardItemForm", "#fileUploadArea", uploaderOptions); 
	    */
	    if(ecmFlg == "N"){
		    dextFileUploadInit("${board.fileSize}" ,"${board.fileAttachOption}", "${board.allowType}");
		   
		   var oldFiles;
		   var oldSizes;
		   <c:if test="${not empty fileDataListJson}"> 
		   oldFiles = ${fileDataListJson};
		   $jq.each(oldFiles,function(index){
			   var fileInfo = $.extend({index:index}, this);
			   document.getElementById("FileUploadManager").AddUploadedFile(fileInfo.fileId, fileInfo.fileRealName, fileInfo.fileSize);
			});
		   
		   dextFileUploadRefresh(); 
		   oldSizes =document.getElementById("FileUploadManager").Size;
		   </c:if>  
	    
	    }
	    
		// ActiveX Editor 사용 여부가 Y인 경우
	    if(useActXEditor == "Y"){
		    if (!$.browser.msie) {
		    	//에디터 로딩 후 현재 페이지 높이 세팅 - iframe 높이 조절
		    	var editor = $("#contents").ckeditorGet();
				editor.on("instanceReady",function() {
					iKEP.iFrameContentResize();
				});
				$("input[name=title]").focus();
	    	}
	    }else{
	    	//에디터 로딩 후 현재 페이지 높이 세팅 - iframe 높이 조절
	    	var editor = $("#contents").ckeditorGet();
			editor.on("instanceReady",function() {
				iKEP.iFrameContentResize();
			});
			$("input[name=title]").focus();
	    }
		
	    infoSum();
	    
	    $jq("#btnAddrControl,#btnGroupAddrControl").click(function() {
			var $container = $(this).parent();
			var $select = $("select", $container);
			var isUser = $select.attr("id") == "targetList";

			var items = [];
			$jq.each($select.children(), function() {
				items.push($.data(this, "data"));
			});

			iKEP.showAddressBook(function(result){
				/*$select.empty();
				$jq.each(result, function() {
					$.tmpl(isUser ? iKEP.template.userOption : iKEP.template.groupOption, this).appendTo($select)
						.data("data", this);
				})*/
				
				var msgTotal = "<ikep4j:message key='ui.servicepack.survey.common.total'/>" + $select.children().length + (isUser ? "<ikep4j:message key='ui.servicepack.survey.common.totalMemberMsg'/>" : "<ikep4j:message key='ui.servicepack.survey.common.totalGroupMsg'/>");
				$("span.totalNum_s", $container).text(msgTotal);
			}, items, {selectElement:$select, selectType:isUser ? "user" : "group",selectMaxCnt:2000, tabs:{common:0, personal:1, collaboration:0, sns:0}});
			});
	    
	    $jq('#userName').keypress( function(event) {
			iKEP.searchUser(event, "Y", setUser);
		});
		
		$jq('#userSearchBtn').click( function() {
			$jq('#userName').trigger("keypress");
		});
		
	    $jq('#groupName').keypress( function(event) { 
			iKEP.searchGroup(event, "Y", setGroup); 
		});
		
		$jq('#groupSearchBtn').click( function() { 
			$jq('#groupName').trigger("keypress");
		});
		$jq("#btnDeleteControl,#btnGroupDeleteControl").click(function() {
			
			$jq("#targetGroupList option:selected").each(function () { 
	            $jq(this).remove();
	        }); 
			
			$jq("#targetList option:selected").each(function () { 
	            $jq(this).remove();
	        });  
			
			var selectTree = $jq(this).attr("id");
			
			if( selectTree == "btnDeleteControl" )
			{	
				var $sel = $jq("#boardItemForm").find("#targetList");
				//$jq("#totalMember").text($sel.children().length);
				
				$jq("#totalMember").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$sel.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalMemberMsg' />");
			}
			else
			{	
				var $sel = $jq("#boardItemForm").find("#targetGroupList");
				//$jq("#totalGroup").text($sel.children().length);
				$jq("#totalGroup").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$sel.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalGroupMsg' />");
			}
		});
		
		
	}); 
	
	setUser = function(data) {
		if(data=="")			{
			alert('검색대상이 없습니다');
		}
		else {		
			var $select = $jq("#targetList");
			var duplicationUsers = [];
			$jq.each(data, function() {
				var hasOption = $select.find('option[value="'+this.id+'"]');

				if(hasOption.length > 0){
					duplicationUsers.push(this.userName);
				} else {
					$.tmpl(iKEP.template.userOption, this).appendTo($select)
						.data("data", this);
				}	
			})
			
			$jq("#totalMember").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$select.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalMemberMsg' />");
			
			if(duplicationUsers.length > 0) alert("[" + duplicationUsers.join(",") + "] " + iKEPLang.searchUserGroup.duplicateUser);
			
			$jq('#userName').val('');
		}
	};
	
	setGroup = function(data) {
		if(data=="") {
			alert('검색대상이 없습니다');
		} else {
			var $select = $jq("#targetGroupList");
			var duplicationGroups = [];
			$jq.each(data, function() {
				var hasOption = $select.find('option[value="'+this.code+'"]');

				if(hasOption.length > 0){
					duplicationGroups.push(this.name);
				} else {
					$.tmpl(iKEP.template.groupOption, this).appendTo($select)
						.data("data", this);
				}	
			})
			$jq("#totalGroup").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$select.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalGroupMsg' />");
			
			if(duplicationGroups.length > 0) alert("[" + duplicationGroups.join(",") + "] " + iKEPLang.searchUserGroup.duplicateGroup);
			
			$jq('#groupName').val('');
		}
	};
		
	writeSubmit = function(targetForm){
		if($("input[name=itemDisplayDummy]").is(":checked")) {
			$("input[name=itemDisplay]").val("1");
		} else {
			$("input[name=itemDisplay]").val("0");
		}
	
	
		//에디터 감추기
		if(useActXEditor == "Y"){
	    	if ($.browser.msie) {
	    		//태그프리 선택 탭을 디자인으로 변경 후 저장한다.
	    		var tweTab = document.twe.ActiveTab;
	    		if ( tweTab != 1 ) {
	    			document.twe.ActiveTab = 1;
	    		}
	    		//태그프리 Mime 데이타 세팅
	    		var tweBody = document.twe.MimeValue();
	    		$('input[name="contents"]').val(tweBody);
	    		var tweBodyObj = document.twe.GetBody();
	    		//에디터 내 이미지 파일 링크 정보 세팅
	    		createActiveXEditorFileLink(tweBodyObj,"boardItemForm");
	    		
	    		$("#twe").css("visibility","hidden");
	    	}
		}
		$("#normalFileTb").show();
		$("body").ajaxLoadStart("button");
	
	
		targetForm.submit();
	
	};
	
	/* editor 초기화  */
	initEditorSet = function() {
		// ActiveX Editor 사용 여부가 Y인 경우
	    if(useActXEditor == "Y"){
			// 브라우저가 ie인 경우
			if ($.browser.msie) {
				// div 높이, 넓이 세팅
				var cssObj = {
			      'height' : '450px',
			      'width' : '650px'
			    };
				$('#editorDiv').css(cssObj);
				// hidden 필드 추가(contents) - 수정모드
				iKEP.createActiveXEditor("#editorDiv","${user.localeCode}","#contents",1);
				// ie 세팅
				$('input[name="msie"]').val('1');
			}else{
				// ckeditor 초기화.
				$("#contents").ckeditor($.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
				// ie 이외 브라우저 값 세팅
				$('input[name="msie"]').val('0');
			}
	    }else{
	    	// ckeditor 초기화.
			$("#contents").ckeditor($.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
			// ie 이외 브라우저 값 세팅
			$('input[name="msie"]').val('0');
	    }
	};
	
	$("#moveBoardItemButton1").live("click", function(){ 
		
		var isKnowhow = $("input[name=isKnowhow1]:checked").attr("value");
		var url = iKEP.getContextRoot() + "/collpack/kms/board/boardItem/viewBoardTreePopup.do?isKnowhow="+isKnowhow+"&orgBoardId=0";
		//var pageURL = "<c:url value='/collpack/kms/board/boardItem/viewBoardTreePopup.do' />?isKnowhow=0&orgBoardId=0";
		iKEP.popupOpen( url , {width:400, height:730 , callback:function(result){moveBoardItem1(result);}}, "MoveBoardItem");
	});

	moveBoardItem1 = function(result) {
		$('input[name="boardId"]').val(result);
		$.get("<c:url value='/collpack/kms/board/boardItem/getKmsMapName.do?boardId='/>" +result)
		.success(function(data) { 
			$("#kmsMapName").text(data);	
		})
		.error(function(event, request, settings) { alert("error"); });  
	};
	
	var $addHtml  = ""; 
	var $addFullHtml = "";	
	//var rows = $("#infoWinner_").length+1;
	var rows = ${fn:length(boardItemList.boardItemList)};
	
	if(rows == 0){
		rows++
	}
	
	  
	$("#delButton").live("click", function(){
		
		if(rows > 1){
			var clickedRow = $(this).parent().parent().parent().parent().parent();
			clickedRow.remove();
			rows--;
		}else{
			$jq("input[name=refTitle]").val("");
			$jq("input[name=refItemId]").val("");
			$jq("input[name=refUserInfo]").val("");
			$jq("input[name=refRegisterDate]").val("");
			$jq("input[name=refTargetSource]").val("");
		}
			
	});
	
	$("#addButton").live("click", function() { 
		
		var addRefItemId = "";
					  
		if($addFullHtml == ""){
			addRefItemId = $("#boardItemForm").find('input[name=refItemId]').val();
			
		}else{				
			addRefItemId = $addFullHtml.find('input[name=refItemId]:last').val();
		}
		
		if(addRefItemId == ""){
			//alert("<ikep4j:message pre='${preMessage}' key='required' />");
			alert("데이터를 선택해주세요.");
			return false;
		}			
		
		++rows;			
		if(rows > 6){
			//alert("<ikep4j:message pre='${preMessage}' key='maxRegist' />");
			alert("최대 등록 갯수는 6개입니다. ");
			return false;
		}
		
		$addFullHtml = $("#infoStart").append					
			("<div class=\"blockDetail\" id='infoWinner_'" + rows+">"
           +"		<table summary=\"게시판등록\">"
           +"			<caption></caption>"
           +"			<colgroup>"
           +"				<col width=\"12%\" />"
           +"				<col width=\"20%\" />"
           +"				<col width=\"12%\" />"
           +"				<col width=\"20%\" />"
           +"               <col width=\"12%\" />"
           +"				<col width=\"20%\" />"
           +"				<col width=\"4%\" />"
           +"			</colgroup>"
           +"			<tbody>"
           +" 		  		<tr>"
           +"					<th scope=\"row\">제목</th>"
           +"					<td colspan=\"5\">"
           +"                      	<input name=\"refTitle\" title=\"제목\" class=\"inputbox w70\" type=\"text\"  readonly/>"
           +"                      	<input name=\"refItemId\" value=\"\" type=\"hidden\"/>"
           +"                          <a class=\"button_s\"  id=\"addRefButton\" href=\"#a\"><span>검색</span></a>"
           +"                   </td>"
           +"                   <td class=\"textCenter\"><a href=\"#a\" id=\"addButton\"><img src=\"<c:url value='/base/images/icon/ic_btn_plus.gif'/>\" alt=\"\" /></a></td>"
           +"				</tr>"
           +"				<tr>"
           +"                   <th scope=\"row\">등록자</th>"
           +"					<td><input name=\"refUserInfo\" value=\"\" class=\"inputbox w30\" type=\"text\" readonly/></td>"
           +"					<th scope=\"row\">등록일</th>"
           +"					<td><input name=\"refRegisterDate\" value=\"\" class=\"inputbox w30\" type=\"text\" readonly/></td>"
           +"                   <th scope=\"row\">출처</th>"
           +"					<td><input name=\"refTargetSource\" value=\"\" class=\"inputbox w30\" type=\"text\" readonly/></td>"
           +"					<td class=\"textCenter\"><a href=\"#a\" id=\"delButton\"><img src=\"<c:url value='/base/images/icon/ic_btn_minus.gif'/>\" alt=\"\" /></a></td>"
           +"				</tr>"
           +"           </tbody>"
           +"		</table>"
           +"</div>");		
		
		iKEP.iFrameContentResize();
	});
	

	setRef = function(data) {
		
		$jq(data).each(function(index) {

            $addHtml.find('input[name=refTitle]').val(data[index].title);
            $addHtml.find('input[name=refItemId]').val(data[index].itemId);
            $addHtml.find('input[name=refUserInfo]').val(data[index].registerName);
            $addHtml.find('input[name=refRegisterDate]').val(data[index].registDate);
            $addHtml.find('input[name=refTargetSource]').val(data[index].targetSource);
		});	
	}
	
	$jq('#addRefButton').live("click", function(event) {			
		event.which = "13";			
		//$addHtml = $(this).parent().parent();
		$addHtml = $(this).parent().parent().parent().parent();
		
		//alert(setRef);
		iKEP.searchRef(event, "N", setRef);
	});	
	
	// 사용자 검색 팝업
	iKEP.popupRef = function(name, column, isMulti, callback) {
		var url = iKEP.getContextRoot() + '/collpack/kms/board/boardItem/popupRef.do?isMulti='+isMulti;
		
		iKEP.popupOpen(url, {width:800, height:560, callback:callback, argument:{name:name,column:column,isMulti:isMulti}}, "BoardItemSearch");
	};


	// 사용자 검색 : AJAX
	iKEP.searchRef = function(event, isMulti, callback, column) {

		if (event.which == '13' || event.which === undefined) {
			var $el = jQuery(event.target);
			var name = $el.val();
			
			if(column == undefined) {
				column= "title";
			}
			
			if(!name) {
				iKEP.popupRef(name, column, isMulti, callback);
			} else {
				jQuery.post(iKEP.getContextRoot()+"/support/popup/getUser.do", {name:name, column:column})
				.success(function(data) {
					
					switch(data.userName) {
						case "isMany" : iKEP.popupUser(name, column, isMulti, callback); break;
						case "isEmpty" : callback([]); break;
						default :
							callback([{
								type:"user",
								id:data.userId,
								empNo:data.empNo,
								userName:data.userName,
								jobTitleName:data.jobTitleName,
								group:data.groupId,
								teamName:data.teamName,
								email:data.mail,
								mobile:data.mobile
							}]);
					}
				})
				.error(function(event, request, settings) { alert("error"); });
			}
		}
	};
	
	
	
})(jQuery); 
//-->
</script>

<script language="JScript" FOR="twe" EVENT="OnKeyDown(event)">
	/* 태그프리 에디터 줄바꿈 태그 P => br 로 변경하는 메소드 */	
	if (!event.shiftKey && event.keyCode == 13){
		twe.InsertHtml("<br>");
		event.returnValue = true; <!-- Active Designer에서의 처리를 막음 -->
	}
	if (event.shiftKey && event.keyCode == 13){
		twe.InsertHtml("<p>");
		event.returnValue = true; <!-- Active Designer에서의 처리를 막음 -->
	}		
</script>
<script language="JScript" for="twe" event="OnControlInit()">
	var form = document.boardItemForm;
	document.twe.HtmlValue = $jq("input[name=contents]").val();//.replaceAll("src=\""+iKEP.getContextRoot(), "src=\""+iKEP.getWebAppPath());
	$jq("input[name=title]").focus();
</script>

<c:if test="${popupYn}"><div class="contentIframe"></c:if>
<form id="editorFileUploadParameter" action="null"> 
	<input name="boardId"  value="${board.boardId}" type="hidden"/> 
	<input name="workspaceId"  value="${board.workspaceId}" type="hidden"/>		
	<input name="interceptorKey"  value="collpack.collaboration.board" type="hidden"/> 
</form>
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
<!--pageTitle Start-->
<div id="pageTitle" class="btnline"> 
	<h2>${board.boardName}</h2> 
	<div class="blockButton"> 
		<ul>
			<c:if test="${permission.isSystemAdmin and empty boardItem.expertId and boardItem.status < 3}"><%-- 관리자일경우 버튼 활성화 --%>
				<li><a class="button assessBoardItemButton" href="#a"><span>전문가평가위임</span></a></li>
			</c:if>		
			<c:if test="${permission.isSystemAdmin or user.userId eq boardItem.expertId}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
				<li><a class="button saveBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
			</c:if>  
			<!-- c:if test="${not popupYn}"-->
				<c:choose>
					<c:when test="${pageGubun eq 'boardItem'}">
						<li><a class="button listBoardItemButton" href="<c:url value='/collpack/kms/board/boardItem/readBoardItemView.do?itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&pageGubun=${pageGubun}'/>"><span>취소</span></a></li>				
					</c:when>
					<c:when test="${pageGubun eq 'searchItem'}">
						<li><a class="button listBoardItemButton" href="<c:url value='/collpack/kms/board/boardItem/readSearchItemView.do?itemId=${boardItem.itemId}&isKnowhow=${boardItem.isKnowhow}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&pageGubun=${pageGubun}'/>"><span>취소</span></a></li>				
					</c:when>
					<c:when test="${pageGubun eq 'latestItem'}">
						<li><a class="button listBoardItemButton" href="<c:url value='/collpack/kms/board/boardItem/readLatestItemView.do?itemId=${boardItem.itemId}&isKnowhow=${boardItem.isKnowhow}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&pageGubun=${pageGubun}'/>"><span>취소</span></a></li>				
					</c:when>
					<c:otherwise>
						<li><a class="button listBoardItemButton" href="<c:url value='/collpack/kms/board/boardItem/listAssessItemView.do?boardId=${boardItem.boardId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>				
					</c:otherwise>
				</c:choose>
			<!-- /c:if-->  
		</ul>
	</div>	
</div> 
<!--//pageTitle End--> 
<div class="blockDetail"> 

<form id="boardItemForm" method="post" action="<c:url value='/collpack/kms/board/boardItem/updateAssessItem.do'/>">
	<input name="controlTabType" title="" type="hidden" value="0:1:0:0" />
	<input name="controlType" title="" type="hidden" value="ORG" />
	<input name="selectType" title="" type="hidden" value="ALL" />
	<input name="selectMaxCnt" title="" type="hidden" value="100" />
	<input name="searchSubFlag" title="" type="hidden" value="" />	
	
	<input name="searchConditionString" type="hidden" value="${searchConditionString}" /> 		
	<input name="popupYn"               type="hidden" value="${popupYn}" /> 		
	<input name="boardId"               type="hidden" value="${boardItem.boardId}" /> 		
	<input name="itemId"                type="hidden" value="${boardItem.itemId}" /> 		
	<input name="itemDisplay"           type="hidden" value="${boardItem.itemDisplay}" />
	<input name="itemType" type="hidden" value="0"/>
	<input name="msie"        			type="hidden" value="0" />	
	<input name="status" 				type="hidden" value="${boardItem.status}"/>
	<input name="oriStatus" 			type="hidden" value="${boardItem.status}"/>
	
	<input name="msie"        			type="hidden" value="0" />
	<input name="hopeGrade"        		type="hidden" value="${boardItem.hopeGrade}" />
	<input name="oriRegistDate"        	type="hidden" value="<ikep4j:timezone pattern="yyyy-MM-dd" date="${boardItem.registDate}"/>" />
	<input name="registerId"        	type="hidden" value="${boardItem.registerId}" />
	<input name="registerName"        	type="hidden" value="${boardItem.registerName}" />
	
	<input name="itemDisplays_1"	type="hidden" value="${boardItem.itemDisplays[0]}" />
	<input name="itemDisplays_2"	type="hidden" value="${boardItem.itemDisplays[1]}" />
	<input name="itemDisplays_3"	type="hidden" value="${boardItem.itemDisplays[2]}" />
	<input name="itemDisplays_4"	type="hidden" value="${boardItem.itemDisplays[3]}" />
	<input name="itemDisplays_5"	type="hidden" value="${boardItem.itemDisplays[4]}" />
	<input name="itemDisplays_6"	type="hidden" value="${boardItem.itemDisplays[5]}" />
	<input name="itemDisplays_7"	type="hidden" value="${boardItem.itemDisplays[6]}" />
	<input name="itemDisplays_8"	type="hidden" value="${boardItem.itemDisplays[7]}" />
	<input name="itemDisplays_9"	type="hidden" value="${boardItem.itemDisplays[8]}" />
	<input name="itemDisplays_10"	type="hidden" value="${boardItem.itemDisplays[9]}" />
	<input name="itemDisplays_11"	type="hidden" value="${boardItem.itemDisplays[10]}" />
	
	<input name="itemDisplays_12"	type="hidden" value="${boardItem.itemDisplays[11]}" />
	<input name="itemDisplays_13"	type="hidden" value="${boardItem.itemDisplays[12]}" />
	<input name="itemDisplays_14"	type="hidden" value="${boardItem.itemDisplays[13]}" />
	
	<input name="itemDisplays_15"	type="hidden" value="${boardItem.itemDisplays[14]}" />
	<input name="itemDisplays_16"	type="hidden" value="${boardItem.itemDisplays[15]}" />
	<input name="itemDisplays_17"	type="hidden" value="${boardItem.itemDisplays[16]}" />

	<input name="pageGubun"	type="hidden" value="${pageGubun}" />
	<input type="hidden" name="ecmFileId" value="" /> 
	<input type="hidden" name="ecmFileName" value="" />
	<input type="hidden" name="ecmFilePath" value=""/>  
	<input type="hidden" name="ecmFileUrl1" value=""/> 
	<input type="hidden" name="ecmFileUrl2" value=""/>  
	
	<table summary="<ikep4j:message pre="${preDetail}" key="summary" />">
		<caption></caption>
		<col style="width: 15%"/>
		<col style="width: 35%"/>
		<col style="width: 10%"/>
		<col style="width: 40%"/>
		<tbody> 
		<tr> 
			<spring:bind path="boardItem.title">
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3"> 
				<div>
					<input 
					name="${status.expression}" 
					type="text" 
					class="inputbox" style="width: 50%;" 
					value="${status.value}" 
					size="1000" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					/> 
					<c:forEach items="${status.errorMessages}" var="error">
					    <label for="${status.expression}" class="serverError"> ${error}</label>
					</c:forEach> 				
				</spring:bind>
					
				<spring:bind path="boardItem.itemDisplay">   
					<input 
					name="itemDisplayDummy" 
					type="checkbox" 
					class="checkbox" 
					value="{status.value}"
					size="40" 
					<c:if test="${status.value eq 1}">checked="checked"</c:if>
					title="Top 게시"
					/> 
					Top 게시
				</spring:bind>	
				</div>
			</td> 
		</tr>				
		<tr>  
			<th scope="row"><ikep4j:message pre='${preDetail}' key='registerName' /></th>
			<td>
				${boardItem.registerName} ${boardItem.groupName}
			</td>
			<th scope="row">출처</th>
			<td>
			<spring:bind path="boardItem.targetSource">
				<input name="targetSource" title="출처" class="inputbox w30" type="text" value="${status.value}"/>
			</spring:bind>
			</td>   	 
		</tr>
        <tr>
			<th scope="row">지식분류</th>
			<td>
				<label>
				<input name="isKnowhow1" type="radio" class="radio" value="1" title="지식분류" <c:if test="${boardItem.isKnowhow eq '1'}">checked="checked"</c:if>/> 
				일반정보
				</label>
			 
				<label>
				<input name="isKnowhow1" type="radio" class="radio" value="0" title="지식분류" <c:if test="${boardItem.isKnowhow eq '0'}">checked="checked"</c:if>/> 
				업무노하우
				</label>
				
				<label>
				<input name="isKnowhow1" type="radio" class="radio" value="3" title="지식분류" <c:if test="${boardItem.isKnowhow eq '3'}">checked="checked"</c:if>/> 
				원문 게시판
				</label>
			</td>
			<th scope="row">지식맵</th>
			<td>
				<span id="kmsMapName">${kmsMapName}</span>	
				<a id="moveBoardItemButton1" class="button_s"><span>지식맵</span></a>
			</td>
		</tr>	
		<tr>
			<th scope="row">보안등급요청</th>
			<td colspan="3">
				<label>
				<input name="hopeGradeCheck" type="radio" class="radio" value="B" <c:if test="${boardItem.hopeGrade eq 'B'}">checked</c:if> /> 
				보안
				</label>
				<label>
				<input name="hopeGradeCheck" type="radio" class="radio" value="C"  <c:if test="${boardItem.hopeGrade eq 'C'}">checked</c:if> /> 
				공유
				</label>
				<span class="tdInstruction">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;※ 보안: 팀장 이상, 공유: 전사 임직원</span> 
			</td>
		</tr>
		<tr>   
			<spring:bind path="boardItem.tag">
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3">
				<div>
				<input 
				name="${status.expression}" 
				type="text" 
				class="inputbox w40" 
				value="${status.value}" 
				size="40" 
				title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"/>
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
				<c:forEach items="${status.errorMessages}" var="error">
				    <label for="${status.expression}" class="serverError"> ${error}</label>
				</c:forEach>
				</div>		
			</td>		
			</spring:bind>   
		</tr>	
		<tr>
			<th>알림 대상 부서</th>
			<td>
				<input 
					name="groupName" 
					id="groupName" 
					type="text" 
					class="inputbox"
					size="20" 
					title="groupName"
					/>
				<a name="groupSearchBtn" id="groupSearchBtn" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a>
				<a id="btnGroupAddrControl" name="btnGroupAddrControl" class="button_ic" href="#a"><span><ikep4j:message  key='ui.servicepack.survey.common.button.address' /></span></a>
				<div style="padding-top:4px;">
				    <c:set var="targetGroupListCnt" value="0"/>
					<select name="targetGroupList" id="targetGroupList"   size="5" multiple="multiple" class="multi w80" title="<ikep4j:message pre='${preList}' key='targetGroupList' />"></select>	
					<a class="button_ic valign_bottom" href="#a" id="btnGroupDeleteControl"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="" />Delete</span></a>
					<span id='totalGroup'  class='totalNum_s'><ikep4j:message key='ui.servicepack.survey.common.totalGroup' arguments="${targetGroupListCnt}"/></span>
				</div>
				<ikep4j:message key='ui.servicepack.survey.common.subGroupSelected' />
			</td>
			<th>알림 대상<br>사용자</th>
			<td>
				<input 
					name="userName" 
					id="userName" 
					type="text" 
					class="inputbox"
					size="20" 
					 title=""
					/>
				<a name="userSearchBtn" id="userSearchBtn" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a>
				<a id="btnAddrControl" class="button_ic" href="#a"><span><ikep4j:message  key='ui.servicepack.survey.common.button.address' /></span></a>
				<div style="padding-top:4px;"> 
					<select name="targetList" id="targetList"  size="5" multiple="multiple" class="multi w80" ></select>									
					<a class="button_ic valign_bottom" href="#a" id="btnDeleteControl"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="<ikep4j:message  key='ui.servicepack.survey.common.button.delete' />"/>Delete</span></a>
					<span id='totalMember' class='totalNum_s'></span>
				</div>
			</td>
		</tr>	
		<tr>  
			<spring:bind path="boardItem.contents">
			<td colspan="4" class="ckeditor">
				<div id="editorDiv"">
					<textarea 
					id="contents"
					name="${status.expression}" 
					class="inputbox w100 fullEditor"
					cols="" 
					rows="5" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />">${status.value}</textarea>
				</div> 				
			</td> 
			</spring:bind> 
		</tr>
		</tbody> 
	</table>
	
                <!--blockDetail Start-->
                <div id="pageTitle">
					<h2>활용지식</h2>
				</div>	
                <div class="mb5"></div>
                <div id="infoStart">
                
				<c:choose>
					<c:when test="${not empty boardItemList}">
				<spring:bind path="boardItemList.boardItemList">					
						<c:forEach var="refItemList" varStatus="varStatus" items="${status.value}" >
                <div class="blockDetail" id="infoWinner_${varStatus.index}">						
					<table summary="게시판등록">
						<caption></caption>
						<colgroup>
							<col width="12%" />
							<col width="20%" />
							<col width="12%" />
							<col width="20%" />
                            <col width="12%" />
							<col width="20%" />
							<col width="4%" />
						</colgroup>
						<tbody>						
               		  		<tr>
								<th scope="row">제목</th>
								<td colspan="5">
                                	<input name="refTitle" title="제목" class="inputbox w70" type="text" value="${refItemList.title}" readonly/>
                                	<input name="refItemId" value="${refItemList.itemId}" type="hidden"/>
                                    <a class="button_s" id="addRefButton" href="#a"><span>검색</span></a>
                                </td>
                                <td class="textCenter"><a href="#a" id="addButton"><img src="<c:url value='/base/images/icon/ic_btn_plus.gif'/>" alt="" /></a></td>
							</tr>
							<tr>
                                <th scope="row">등록자</th>
								<td><input name="refUserInfo" class="inputbox w30" type="text" value="${refItemList.registerName}" readonly/></td>
								<th scope="row">등록일</th>
								<td><input name="refRegisterDate" class="inputbox w30" type="text" value="<ikep4j:timezone date="${refItemList.registDate}" pattern="yyyy.MM.dd HH:mm:ss" />" readonly/></td>
                                <th scope="row">출처</th>
								<td><input name="refTargetSource" class="inputbox w30" type="text" value="${refItemList.targetSource}" readonly/></td>
								<td class="textCenter"><a href="#a" id="delButton"><img src="<c:url value='/base/images/icon/ic_btn_minus.gif'/>" alt="" /></a></td>
							</tr>
                        </tbody>
					</table>	
				</div>											
						</c:forEach>  
				</spring:bind>              		  		
					</c:when>
					<c:otherwise>

					</c:otherwise>
				</c:choose>
				
				<c:if test="${fn:length(boardItemList.boardItemList) == 0}">
                <div class="blockDetail" id="infoWinner_0">					
					<table summary="게시판등록">
						<caption></caption>
						<colgroup>
							<col width="12%" />
							<col width="20%" />
							<col width="12%" />
							<col width="20%" />
                            <col width="12%" />
							<col width="20%" />
							<col width="4%" />
						</colgroup>
						<tbody>					
               		  		<tr>
								<th scope="row">제목</th>
								<td colspan="5">
                                	<input name="refTitle" title="제목" class="inputbox w70" type="text"  readonly/>
                                	<input name="refItemId" value="" type="hidden"/>
                                    <a class="button_s" id="addRefButton" href="#a"><span>검색</span></a>
                                </td>
                                <td class="textCenter"><a href="#a" id="addButton"><img src="<c:url value='/base/images/icon/ic_btn_plus.gif'/>" alt="" /></a></td>
							</tr>
							<tr>
                                <th scope="row">등록자</th>
								<td><input name="refUserInfo" value="" class="inputbox w30" type="text" readonly/></td>
								<th scope="row">등록일</th>
								<td><input name="refRegisterDate" value="" class="inputbox w30" type="text" readonly/></td>
                                <th scope="row">출처</th>
								<td><input name="refTargetSource" value="" class="inputbox w30" type="text" readonly/></td>
								<td class="textCenter"><a href="#a" id="delButton"><img src="<c:url value='/base/images/icon/ic_btn_minus.gif'/>" alt="" /></a></td>
							</tr>
                        </tbody>
					</table>							
				</div>
				</c:if>									


				</div>
                
                
                
                <div class="blockDetail">
					<table summary="게시판등록">
						<caption></caption>
						<colgroup>
							<col width="12%" />
							<col width="21%" />
							<col width="12%" />
							<col width="22%" />
                            <col width="12%" />
							<col width="21%" />
						</colgroup>
						<tbody>
                            <tr>
								<th scope="row">평가기준</th>
								<td colspan="5">
                                <div class="blockDetail_3">
                                    <table summary="평가기준">
                                        <caption></caption>
                                        <colgroup>
                                        	<col width="12%"/>
                                            <col width="15%"/>
                                            <col width="40%"/>
                                            <col width="33%"/>
                                        </colgroup>
                                        <tbody>
                                            <tr>
                                            	<th rowspan="3" class="textRight" scope="row">일반정보/원문 게시판</th>
                                                <th scope="row" class="textRight">지식특성</th>
                                                <td colspan="2"><label><input id="checkboxInfo" name="info" class="checkbox" title="checkbox" type="checkbox" value="1" <c:if test="${boardItem.itemDisplays[0] == 1}">checked</c:if>/> ${boardItem.assessNames[0]} (+1)</label></td>
                                            </tr>
                                            <tr>
                                                <th class="textRight">지식내용 및 구성</th>
                                                <td colspan="2">
                                                <label><input id="checkboxInfo" name="info" class="checkbox" title="checkbox" type="checkbox" value="1" <c:if test="${boardItem.isKnowhow eq '1'}">checked</c:if>/> ${boardItem.assessNames[1]} (+1)</label></br>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="textRight">지식활용</th>
                                                <td colspan="2"><label><input id="checkboxInfo" name="info" class="checkbox" title="checkbox" type="checkbox" value="1" <c:if test="${boardItem.itemDisplays[2] == 1}">checked</c:if>/> ${boardItem.assessNames[2]} (+1)</label></td>
                                            </tr>
                                            <tr>
                                              <th rowspan="2" class="textRight" scope="row">업무노하우</th>
                                              <th scope="row" class="textRight">중요도</th>
                                              <td colspan="2">
                                              	<label><input id="checkboxInfo" name="info1" class="checkbox" title="checkbox" type="checkbox" value="3" <c:if test="${boardItem.itemDisplays[3] == 1}">checked</c:if>/> ${boardItem.assessNames[3]} (3점)</label> &nbsp;
                                                <label><input id="checkboxInfo" name="info1" class="checkbox" title="checkbox" type="checkbox" value="2" <c:if test="${boardItem.itemDisplays[4] == 1}">checked</c:if>/> ${boardItem.assessNames[4]} (2점)</label> &nbsp;
                                                <label><input id="checkboxInfo" name="info1" class="checkbox" title="checkbox" type="checkbox" value="1" <c:if test="${boardItem.itemDisplays[5] == 1}">checked</c:if>/> ${boardItem.assessNames[5]} (1점)</label>
                                              </td>
                                            </tr>
                                            <tr>
                                              <th scope="row" class="textRight">활용도</th>
                                              <td colspan="2">
                                              	<label><input id="checkboxInfo" name="info2" class="checkbox" title="checkbox" type="checkbox" value="3" <c:if test="${boardItem.itemDisplays[6] == 1}">checked</c:if>/> ${boardItem.assessNames[6]} (3점)</label> &nbsp;
                                                <label><input id="checkboxInfo" name="info2" class="checkbox" title="checkbox" type="checkbox" value="2" <c:if test="${boardItem.itemDisplays[7] == 1}">checked</c:if>/> ${boardItem.assessNames[7]} (2점)</label> &nbsp;
                                                <label><input id="checkboxInfo" name="info2" class="checkbox" title="checkbox" type="checkbox" value="1" <c:if test="${boardItem.itemDisplays[8] == 1}">checked</c:if>/> ${boardItem.assessNames[8]} (1점)</label>
                                              </td>
                                            </tr>
                                            <tr>
                                                <th colspan="2" scope="row" class="textRight">평가점수</th>
                                                <td colspan="2"><input type="text" class="inputbox" title="inputbox" name="mark" value="${boardItem.mark}" size="20" readonly/></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                </td>
							</tr>
                            <tr>
                                <th scope="row">지식등급</th>
								<td>
                                <select title="지식등급" name="infoGrade">
                                    <option value="B" <c:if test="${boardItem.hopeGrade == 'B'}">selected</c:if>>보안</option>
                                    <option value="C" <c:if test="${boardItem.infoGrade == 'D' || boardItem.hopeGrade == 'C'}">selected</c:if>>공유</option>
                                    <option value="E" <c:if test="${boardItem.infoGrade == 'E'}">selected</c:if>>미공유</option>
                                </select>
                                </td>
								<th scope="row">지식확인</th>
								<td colspan="3"><label><input name="checkStatus1" class="checkbox" title="checkbox" type="checkbox" value="3" checked /> 확인</label></td>
                                <%-- <th scope="row">결재확인</th>
								<td><label><input name="checkStatus2" class="checkbox" title="checkbox" type="checkbox" value="5" <c:if test="${user.userId eq boardItem.expertId}">disabled="true"</c:if><c:if test="${boardItem.status eq 5}">checked</c:if>/> 확인</label></td> --%>
							</tr>
               		  		<tr>
								<th scope="row">전문가</th>
								<td colspan="5">
                                	<input id="assessor" name="assessor" title="전문가" class="inputbox w20" type="text" value="${boardItem.expertName}" readonly/>
                                	<input id="assessorId" name="assessorId" value="${boardItem.expertId}" type="hidden"/>
                                	<input id="assessorName" name="assessorName" value="${boardItem.expertName}" type="hidden"/>
                                    <c:if test="${permission.isSystemAdmin and empty boardItem.expertId}"><%-- 관리자일경우 버튼 활성화 --%>
                                    <a class="button_s" id="addPersonButton" href="#a"><span>Search</span></a>
                                    </c:if>
                                </td>
							</tr>									
                        </tbody>
					</table>
				</div>
				</div>
                <!--//blockDetail End-->
	<table style="width:100%;display:none;" id="ecmTb">
		<tr><th style="width:3%;text-align:center;"></th><th style="width:17%;text-align:center;">파일명</th><th style="width:80%;text-align:center;">URL</th></tr>
		<c:forEach var="ecmFileData" items="${boardItem.ecmFileDataList}" varStatus="ecmFileDataStatus">
			<tr id="ecmTr_${ecmFileData.fileName}">
			<td><input name="ecmCheck" type="checkbox" class="checkbox" value="${ecmFileData.fileName}" /></td>
				<td><input name="ecmFileName_${ecmFileData.fileName}" id="ecmFileName_${ecmFileData.fileName}" type="text" value="${ecmFileData.fileRealName}" class="inputbox w100" readonly="readonly" /></td>
				<td><input name="ecmFileUrl1_${ecmFileData.fileName}" id="ecmFileUrl1_${ecmFileData.fileName}" type="text" value="${ecmFileData.fileUrl1}" class="inputbox w100" readonly="readonly" /></td>
				<input name="ecmFileUrl2_${ecmFileData.fileName}" id="ecmFileUrl2_${ecmFileData.fileName}" type="hidden" value="${ecmFileData.fileUrl2}" />
				<input name="ecmFilePath_${ecmFileData.fileName}" id="ecmFilePath_${ecmFileData.fileName}" type="hidden" value="${ecmFileData.filePath}" />
				<input name="ecmFileId_${ecmFileData.fileName}" id="ecmFileId_${ecmFileData.fileName}" type="hidden" value="${ecmFileData.fileName}" />
			</tr>
		</c:forEach>
	</table>
	<table style="width:100%;display:none;" id="ecmBtn">
		<tr>
			<td align="right" style="border-left:none;border-color:#e5e5e5;background-color:#e8e8e8;">
			<img src="<c:url value="/base/images/dextupload/btn_fileplus_normal.gif"/>" id="imgBtn_fileAdd" name="Image2"  border="0" onclick="CyberdigmpopupSelect('selectAllFiles');" style="cursor:pointer;valign:absmiddle" />
			<img src="<c:url value="/base/images/dextupload/btn_listdelete_normal.gif"/>" name="Image4"  border="0" onclick="ecmfileDelete();" style="cursor:pointer;valign:absmiddle" />	
			</td>
		</tr>
	</table>
	<table style="width:100%;display:none;" id="normalFileTb">
		<tr>
			<td colspan="2" style="border-color:#e5e5e5">
				<OBJECT id="FileUploadManager" codeBase="<c:url value="/Bin/DEXTUploadX.cab#version=2,8,2,0"/>"
					height="140" width="100%" classid="CLSID:DF75BAFF-7DD5-4B83-AF5E-692067C90316" VIEWASTEXT>
					 <param name="ButtonVisible" value="FALSE" />
					 <param name="VisibleContextMenu" value="FALSE" />
					 <param name="StatusBarVisible" value="FALSE" />
					 <param name="VisibleListViewFrame" value="FALSE" />
				</OBJECT>
			</td>
		<tr>

		<tr>
            <td style="border-right:none;border-color:#e5e5e5;background-color:#e8e8e8">전체 <span id="_StatusInfo_count"></span>개 <span id="_StatusInfo_size"></span><span id="_Total_size"></span></div>
			<td align="right" style="border-left:none;border-color:#e5e5e5;background-color:#e8e8e8;">
			<img src="<c:url value="/base/images/dextupload/btn_fileplus_normal.gif"/>" id="imgBtn_fileAdd" name="Image2"  border="0" onclick="btnAddFile_Onclick()" style="cursor:pointer;valign:absmiddle" />
			<img src="<c:url value="/base/images/dextupload/btn_listdelete_normal.gif"/>" name="Image4"  border="0" onclick="btnDeleteItem_Onclick()" style="cursor:pointer;valign:absmiddle" />	
			</td>
		</tr>
	</table> 
	</form> 
</div> 
<!--//blockDetail End-->			
						
<c:if test="${ecmrole && user.essAuthCode != 'E9'}">
	<iframe width="0" height="0" src="<c:url value="/base/common/destinySLO.jsp?TARGET_URL=install"/>"></iframe>
	<%-- <iframe width="0" height="0" src="<c:url value="/base/common/file_sample.jsp"/>"></iframe> --%>
	<iframe id="select_dialog" src="" style="display:none;"></iframe>
	</c:if>	
<!--blockButton Start-->
<div class="blockButton"> 
	<ul> 
		<c:if test="${permission.isSystemAdmin and empty boardItem.expertId and boardItem.status < 3}"><%-- 관리자일경우 버튼 활성화 --%>
			<li><a class="button assessBoardItemButton" href="#a"><span>전문가평가위임</span></a></li>
		</c:if>
		<c:if test="${permission.isSystemAdmin or user.userId eq boardItem.expertId}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
			<li><a class="button saveBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
		</c:if>  
		<!-- c:if test="${not popupYn}"-->
			<c:choose>
				<c:when test="${pageGubun eq 'boardItem'}">
					<li><a class="button listBoardItemButton" href="<c:url value='/collpack/kms/board/boardItem/readBoardItemView.do?itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&pageGubun=${pageGubun}'/>"><span>취소</span></a></li>				
				</c:when>
				<c:when test="${pageGubun eq 'searchItem'}">
					<li><a class="button listBoardItemButton" href="<c:url value='/collpack/kms/board/boardItem/readSearchItemView.do?itemId=${boardItem.itemId}&isKnowhow=${boardItem.isKnowhow}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&pageGubun=${pageGubun}'/>"><span>취소</span></a></li>				
				</c:when>
				<c:when test="${pageGubun eq 'latestItem'}">
					<li><a class="button listBoardItemButton" href="<c:url value='/collpack/kms/board/boardItem/readLatestItemView.do?itemId=${boardItem.itemId}&isKnowhow=${boardItem.isKnowhow}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&pageGubun=${pageGubun}'/>"><span>취소</span></a></li>				
				</c:when>
				<c:otherwise>
					<li><a class="button listBoardItemButton" href="<c:url value='/collpack/kms/board/boardItem/listAssessItemView.do?boardId=${boardItem.boardId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>				
				</c:otherwise>
			</c:choose>
		<!-- /c:if--> 
	 </ul>
</div>
<c:if test="${popupYn}"></div></c:if>
<!--//blockButton End-->