<%--
=====================================================
* 기능 설명 : 다이나믹 타일즈2 Header Page
* 작성자 : 주길재
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preMenu"  			value="ui.approval.common.menu" />

<script type="text/javascript">

$jq(document).ready(function(){

	// Left Menu setting
    iKEP.setLeftMenu();
	
	var option = {
   		path: "/ikep4-webapp/approval"
    }
	
	<c:if test="${cookieFlag == 'Y'}">
		$jq.cookie("IKEP_DAPPROVAL_MENU_SAVED2", "close", option);
		$jq.cookie("IKEP_DAPPROVAL_MENU_SAVED3", "open", option);
		//$jq.cookie("IKEP_DAPPROVAL_MENU_SAVED4", "close", option);
		$jq.cookie("IKEP_DAPPROVAL_MENU_SAVED5", "close", option);
		$jq.cookie("IKEP_DAPPROVAL_MENU_SAVED6", "close", option);
		$jq.cookie("IKEP_DAPPROVAL_MENU_SAVED7", "close", option);
		$jq.cookie("IKEP_DAPPROVAL_MENU_SAVED8", "close", option);
		$jq.cookie("IKEP_DAPPROVAL_MENU_SAVED9", "close", option);
		$jq.cookie("IKEP_DAPPROVAL_MENU_SAVED10", "close", option);
		$jq.cookie("IKEP_DAPPROVAL_MENU_SAVED11", "close", option);
		$jq.cookie("IKEP_DAPPROVAL_MENU_SAVED12", "close", option);
	</c:if>
	
	if($jq.cookie("IKEP_DAPPROVAL_MENU_SAVED2") == "close") {
		$jq("#IKEP_DAPPROVAL_MENU2").hide();
		$jq("#IKEP_DAPPROVAL_MENU2").parent().removeClass("opened");
	}
	
	if($jq.cookie("IKEP_DAPPROVAL_MENU_SAVED3") == "close") {
		$jq("#IKEP_DAPPROVAL_MENU3").hide();
		$jq("#IKEP_DAPPROVAL_MENU3").parent().removeClass("opened");
	}
	
	/*if($jq.cookie("IKEP_DAPPROVAL_MENU_SAVED4") == "close") {
		$jq("#IKEP_DAPPROVAL_MENU4").hide();
		$jq("#IKEP_DAPPROVAL_MENU4").parent().removeClass("opened");
	}*/
	
	if($jq.cookie("IKEP_DAPPROVAL_MENU_SAVED5") == "close") {
		$jq("#IKEP_DAPPROVAL_MENU5").hide();
		$jq("#IKEP_DAPPROVAL_MENU5").parent().removeClass("opened");
	}
	
	/*if($jq.cookie("IKEP_DAPPROVAL_MENU_SAVED6") == "close") {
		$jq("#IKEP_DAPPROVAL_MENU6").hide();
		$jq("#IKEP_DAPPROVAL_MENU6").parent().removeClass("opened");
	}*/
	
	if($jq.cookie("IKEP_DAPPROVAL_MENU_SAVED7") == "close") {
		$jq("#IKEP_DAPPROVAL_MENU7").hide();
		$jq("#IKEP_DAPPROVAL_MENU7").parent().removeClass("opened");
	}
	
	if($jq.cookie("IKEP_DAPPROVAL_MENU_SAVED8") == "close") {
		$jq("#IKEP_DAPPROVAL_MENU8").hide();
		$jq("#IKEP_DAPPROVAL_MENU8").parent().removeClass("opened");
	}
	
	if($jq.cookie("IKEP_DAPPROVAL_MENU_SAVED9") == "close") {
		$jq("#IKEP_DAPPROVAL_MENU9").hide();
		$jq("#IKEP_DAPPROVAL_MENU9").parent().removeClass("opened");
	}
	
	if($jq.cookie("IKEP_DAPPROVAL_MENU_SAVED10") == "close") {
		$jq("#IKEP_DAPPROVAL_MENU10").hide();
		$jq("#IKEP_DAPPROVAL_MENU10").parent().removeClass("opened");
	}
	
	if($jq.cookie("IKEP_DAPPROVAL_MENU_SAVED11") == "close") {
		$jq("#IKEP_DAPPROVAL_MENU11").hide();
		$jq("#IKEP_DAPPROVAL_MENU11").parent().removeClass("opened");
	}
	if($jq.cookie("IKEP_DAPPROVAL_MENU_SAVED12") == "close") {
		$jq("#IKEP_DAPPROVAL_MENU12").hide();
		$jq("#IKEP_DAPPROVAL_MENU12").parent().removeClass("opened");
	}
	
	$jq.ajax({    
		url : "<c:url value="/approval/work/userdoc/apprUserDocLeftMenu.do"/>",     
		data : {},     
		success : function(result) {
			//alert(result);
			//$jq("#userDocTree").append(result);
			//return false;
			if(result != "[]") {
				$jq("#userDocTree").bind("loaded.jstree", function (event, datet) {
					$jq(this).jstree("hide_icons");
					$jq("#userDocTree").jstree("open_all");
					 
					 //window.parent.resizeIframe();
				}).jstree({
				    plugins:["themes", "ui", "dnd", "crrm", "json_data"],   
				    "json_data" : {   
				    	 "data" : eval(result)
				    }       
				}).bind("open_node.jstree", function (event, data) {
					
				}).bind("close_node.jstree", function (event, data) {
					
				}).bind("click.jstree", function (event, data) {
					/* alert($(this));
					var node          = data.rslt.o; 
				    var parentNode    = data.inst._get_parent(node);
				    var folderId       = $(node).attr("folderId");
				    alert(folderId); */
				});
				
				/* 노드 클릭시 이벤트*/
			    $jq("#userDocTree").delegate("a", "click", function () {
			    	
			    	if(typeof($jq(this).parent().attr("folderId")) != "undefined"){
			    		$jq.ajax({    
			    			url : "<c:url value="/approval/work/userdoc/listApprUserDoc.do"/>?folderId="+$jq(this).parent().attr("folderId"),     
			    			data : {},     
			    			success : function(result) {
			    				$jq("#mainContents").empty();
			    				$jq("#mainContents").append(result);
			    			},
			    			error : function(event, request, settings){
			    			 	alert("error");
			    			}
			    		}) ;
			    	} 
			     }); 
				
			}else{
				$jq("#userDocTree").text("<ikep4j:message pre='${preMenu}' key='notexist'/>");
			}
		},
		error : function(event, request, settings){
		 	alert("error");
		}
	}) ;
	
	
	
});

function menuSetCookie(cookieName, menuId) {
	
	var option = {
   		path: '/ikep4-webapp/approval'
    }
	
	if($jq(menuId).css("display") == 'none') {
		$jq.cookie(cookieName, "open", option);
	} else {
		$jq.cookie(cookieName, "close", option);
	}
}

</script>
<!--leftMenu Start-->
	<h1 class="none"><ikep4j:message pre='${preMenu}' key='leftMenu'/></h1>	
	<h2><a href="<c:url value="/approval/work/apprMain/apprMainList.do"/>"><ikep4j:message pre='${preMenu}' key='title'/></a></h2>	
	<div id="createApprovalDoc" class="blockButton_2">
        <a class="button_2" href="<c:url value="/approval/work/apprWorkForm/listApprForm.do"/>">
        	<span>
        		<img src="<c:url value='/base/images/icon/ic_registration.gif'/>" width="16" height="16" alt="iKEP icon"> 
				<ikep4j:message pre='${preMenu}' key='listApprForm1'/>
			</span>
		</a>
    </div>
	<div class="left_fixed">
		<ul>
			<li class="liFirst opened">
				<a href="#" onclick="menuSetCookie('IKEP_DAPPROVAL_MENU_SAVED2', '#IKEP_DAPPROVAL_MENU2'); return false;"><img src="<c:url value='/base/images/icon/ic_appr_left_menu01.gif'/>" width="16" height="16" alt="iKEP icon"><ikep4j:message pre='${preMenu}' key='main2'/></a>
				<ul id="IKEP_DAPPROVAL_MENU2">
					<li class="no_child">
						<a id="apprMyrequestOfLeft" href="<c:url value="/approval/work/apprlist/listApprMyRequest.do"/>">
						<ikep4j:message pre='${preMenu}' key='listApprMyRequest'/>
						</a>
					</li>
					<li class="no_child"><a id="apprTempOfLeft" href="<c:url value="/approval/work/apprlist/listApprTemp.do"/>"><ikep4j:message pre='${preMenu}' key='listApprTemp'/></a></li>
					<li class="no_child liLast"><a id="apprRejectOfLeft" href="<c:url value="/approval/work/apprlist/listApprReject.do"/>"><ikep4j:message pre='${preMenu}' key='listApprReject'/></a></li>
				</ul>	
			</li>
			<li class="opened">
				<a href="#" onclick="menuSetCookie('IKEP_DAPPROVAL_MENU_SAVED3', '#IKEP_DAPPROVAL_MENU3');"><img src="<c:url value='/base/images/icon/ic_appr_left_menu02.gif'/>" width="16" height="16" alt="iKEP icon"><ikep4j:message pre='${preMenu}' key='main3'/></a>
				<ul id="IKEP_DAPPROVAL_MENU3">
					<li class="no_child"><a id="apprMainOfLeft" href="<c:url value="/approval/work/apprMain/apprMainList.do"/>"><ikep4j:message pre='${preMenu}' key='apprMainList'/></a></li>
					<li class="no_child"><a id="apprTodoOfLeft" href="<c:url value="/approval/work/apprlist/listApprTodo.do"/>"><ikep4j:message pre='${preMenu}' key='listApprTodo'/></a></li>
					<li class="no_child"><a id="apprCompleteOfLeft" href="<c:url value="/approval/work/apprlist/listApprComplete.do"/>"><ikep4j:message pre='${preMenu}' key='listApprComplete'/></a></li>
<% /*				
                    <li class="no_child"><a id="apprRequestExamOfLeft" href="<c:url value="/approval/work/apprlist/listApprRequestExam.do"/>"><ikep4j:message pre='${preMenu}' key='listApprRequestExam'/></a></li>
					<li class="no_child liLast"><a id="apprExamOfLeft" href="<c:url value="/approval/work/apprlist/listApprExam.do"/>"><ikep4j:message pre='${preMenu}' key='listApprCompleteExam'/></a></li>
*/ %>					
				</ul>	
			</li>
			<li class="opened">
				<a href="#" onclick="menuSetCookie('IKEP_DAPPROVAL_MENU_SAVED5', '#IKEP_DAPPROVAL_MENU5'); return false;"><img src="<c:url value='/base/images/icon/ic_appr_left_menu03.gif'/>" width="16" height="16" alt="iKEP icon"><ikep4j:message pre='${preMenu}' key='main5'/></a>
				<ul id="IKEP_DAPPROVAL_MENU5">
					<li class="no_child"><a id="apprReferenceOfLeft" href="<c:url value="/approval/work/apprlist/listApprReference.do"/>"><ikep4j:message pre='${preMenu}' key='listApprReference'/></a></li>
<%-- 					<li class="no_child"><a id="apprReadingAssignOfLeft" href="<c:url value="/approval/work/apprlist/listApprReadingAssign.do"/>"><ikep4j:message pre='${preMenu}' key='listApprReadingAssign'/></a></li> --%>
					<li class="no_child liLast"><a id="apprDelegateOfLeft" href="<c:url value="/approval/work/apprlist/listApprDelegate.do"/>"><ikep4j:message pre='${preMenu}' key='listApprDelegate'/></a></li>
				</ul>	
			</li>
<% /*							
			<li class="opened">
				<a href="#" onclick="menuSetCookie('IKEP_DAPPROVAL_MENU_SAVED4', '#IKEP_DAPPROVAL_MENU4'); return false;"><img src="<c:url value='/base/images/icon/ic_appr_left_menu04.gif'/>" width="16" height="16" alt="iKEP icon"><ikep4j:message pre='${preMenu}' key='main4'/></a>
				<ul id="IKEP_DAPPROVAL_MENU4">
					<li class="liLast"><a id="apprDeptRecOfLeft" href="<c:url value="/approval/work/apprlist/listApprDeptRec.do"/>"><ikep4j:message pre='${preMenu}' key='listApprDeptRec'/></a></li>
					<li class="liLast"><a id="apprUserRecOfLeft" href="<c:url value="/approval/work/apprlist/listApprUserRec.do"/>"><ikep4j:message pre='${preMenu}' key='listApprUserRec'/></a></li>
				</ul>	
			</li>
			<li class="opened">
				<a href="#" onclick="menuSetCookie('IKEP_DAPPROVAL_MENU_SAVED6', '#IKEP_DAPPROVAL_MENU6'); return false;" ><img src="<c:url value='/base/images/icon/ic_appr_left_menu05.gif'/>" width="16" height="16" alt="iKEP icon"><ikep4j:message pre='${preMenu}' key='main6'/></a>
				<ul id="IKEP_DAPPROVAL_MENU6">
					<li class="no_child"><a id="apprDisplayWaitingOfLeft" href="<c:url value="/approval/work/apprDisplay/listApprDisplayWaiting.do"/>"><ikep4j:message pre='${preMenu}' key='listApprDisplayWaiting'/></a></li>
					<li class="no_child liLast"><a id="apprDisplayCompleteOfLeft" href="<c:url value="/approval/work/apprDisplay/listApprDisplayComplete.do"/>"><ikep4j:message pre='${preMenu}' key='listApprDisplayComplete'/></a></li>
				</ul>	
			</li>
*/ %>							
			<li class="opened">
				<a href="#" onclick="menuSetCookie('IKEP_DAPPROVAL_MENU_SAVED7', '#IKEP_DAPPROVAL_MENU7'); return false;"><img src="<c:url value='/base/images/icon/ic_appr_left_menu06.gif'/>" width="16" height="16" alt="iKEP icon"><ikep4j:message pre='${preMenu}' key='main7'/></a>
				<ul id="IKEP_DAPPROVAL_MENU7">
					<li class="no_child"><a id="apprIntegrateOfLeft" href="<c:url value="/approval/work/apprlist/readApprAllList.do"/>"><ikep4j:message pre='${preMenu}' key='readApprAllList'/></a></li>
					<li class="no_child"><a id="apprMyRequestCompleteOfLeft" href="<c:url value="/approval/work/apprlist/listApprMyRequestComplete.do"/>"><ikep4j:message pre='${preMenu}' key='listApprMyRequestComplete'/></a></li>
					<li class="no_child"><a id="completeApprOfLeft" href="<c:url value="/approval/work/apprlist/listCompleteAppr.do"/>"><ikep4j:message pre='${preMenu}' key='listCompleteAppr'/></a></li>
<%-- 					<li class="no_child"><a id="apprAgreementOfLeft" href="<c:url value="/approval/work/apprlist/listApprAgreement.do"/>"><ikep4j:message pre='${preMenu}' key='listApprAgreement'/></a></li> --%>
<%-- 					<li class="no_child"><a id="apprCompleteRefOfLeft" href="<c:url value="/approval/work/apprlist/listApprCompleteRef.do"/>"><ikep4j:message pre='${preMenu}' key='listApprCompleteRef'/></a></li> --%>
<% /*					<li class="no_child"><a id="apprCompleteExamOfLeft" href="<c:url value="/approval/work/apprlist/listApprCompleteExam.do"/>"><ikep4j:message pre='${preMenu}' key='listApprCompleteExam'/></a></li>*/ %>
					<li class="no_child"><a id="apprDeptIntegrateOfLeft" href="<c:url value="/approval/work/apprlist/listApprDeptIntegrate.do"/>"><ikep4j:message pre='${preMenu}' key='listApprDeptIntegrate'/></a></li>
					<li class="no_child"><a id="apprReadingOfLeft" href="<c:url value="/approval/work/apprlist/listApprReading.do"/>"><ikep4j:message pre='${preMenu}' key='listApprReading'/></a></li>
<% /*					<li class="no_child"><a id="apprOfficialLeft" href="<c:url value="/approval/work/apprOfficial/listApprOfficial.do"/>"><ikep4j:message pre='${preMenu}' key='listApprOfficial'/></a></li>
					<c:if test="${isReadAll or isSystemAdmin}">
					<li class="no_child liLast"><a id="apprDocAllUserOfLeft" href="<c:url value="/approval/work/apprlist/listApprDocAllUser.do"/>"><ikep4j:message pre='${preMenu}' key='listApprDocAllUser'/></a></li>
					</c:if>
*/ %>					
				</ul>	
			</li>
			<li class="opened">
				<a href="#" onclick="menuSetCookie('IKEP_DAPPROVAL_MENU_SAVED12', '#IKEP_DAPPROVAL_MENU12'); return false;"><img src="<c:url value='/base/images/icon/ic_appr_left_menu06.gif'/>" width="16" height="16" alt="iKEP icon"><ikep4j:message pre='${preMenu}' key='main12'/></a>
				<ul id="IKEP_DAPPROVAL_MENU12" style="overflow:auto;overflow-y:hidden;border-left:#ccc 1px solid;border-right:#ccc 1px solid;width:172px;">
					<li class="no_child liLast" id="userDocTreeOfLeft" >
						<div class="leftTree" id="userDocTree">
						</div>	
					</li>
				</ul>		
			</li>
			<li class="opened">
				<a href="#" onclick="menuSetCookie('IKEP_DAPPROVAL_MENU_SAVED8', '#IKEP_DAPPROVAL_MENU8'); return false;"><img src="<c:url value='/base/images/icon/ic_appr_left_menu07.gif'/>" width="16" height="16" alt="iKEP icon"><ikep4j:message pre='${preMenu}' key='main8'/></a>
				<ul id="IKEP_DAPPROVAL_MENU8">
					<li class="no_child"><a id="entrustFormOfLeft" href="<c:url value="/approval/work/config/updateEntrustForm.do"/>"><ikep4j:message pre='${preMenu}' key='updateEntrustForm'/></a></li>
					<li class="no_child"><a id="signUserOfLeft" href="<c:url value="/approval/work/config/listApprSignUser.do"/>"><ikep4j:message pre='${preMenu}' key='listApprSignUser'/></a></li>
					<li class="no_child"><a id="setAlramFormOfLeft" href="<c:url value="/approval/work/config/updateSetAlramForm.do"/>"><ikep4j:message pre='${preMenu}' key='updateSetAlramForm'/></a></li>
					<li class="no_child"><a id="apprSignFormOfLeft" href="<c:url value="/approval/work/config/apprSignForm.do"/>"><ikep4j:message pre='${preMenu}' key='apprSignForm'/></a></li>
					<li class="no_child"><a id="apprPasswordFormOfLeft" href="<c:url value="/approval/work/config/apprPasswordForm.do"/>"><ikep4j:message pre='${preMenu}' key='apprPasswordForm'/></a></li>
					<c:if test="${isReadAll and !isSystemAdmin}">
					<li class="no_child"><a id="receptionFormLeft" href="<c:url value="/approval/admin/reception/receptionForm.do"/>"><ikep4j:message pre='${preMenu}' key='receptionMgnt'/></a></li>
					</c:if>
					<li class="no_child liLast"><a id="apprUserDocLinkOfLeft" href="<c:url value="/approval/work/userdoc/apprUserDocMain.do"/>"><ikep4j:message pre='${preMenu}' key='main12'/></a></li>
				</ul>	
			</li>
			<c:if test="${isSystemAdmin}">
			<li class="opened">
				<a href="#" onclick="menuSetCookie('IKEP_DAPPROVAL_MENU_SAVED9', '#IKEP_DAPPROVAL_MENU9'); return false;"><img src="<c:url value='/base/images/icon/ic_appr_left_menu08.gif'/>" width="16" height="16" alt="iKEP icon"><ikep4j:message pre='${preMenu}' key='main9'/></a>
				<ul id="IKEP_DAPPROVAL_MENU9">
					<li class="no_child"><a id="apprDocAllAdminLinkOfLeft" href="<c:url value="/approval/work/apprlist/listApprDocAllAdmin.do"/>"><ikep4j:message pre='${preMenu}' key='listApprDocAllAdmin'/></a></li>
					<li class="no_child"><a id="apprReadGroupLinkOfLeft" href="<c:url value="/approval/admin/config/listApprReadGroup.do"/>"><ikep4j:message pre='${preMenu}' key='listApprReadGroup'/></a></li>
<%  /*					<li class="no_child"><a id="apprDocSecurityLinkOfLeft" href="<c:url value="/approval/work/apprlist/listApprDocSecurity.do"/>"><ikep4j:message pre='${preMenu}' key='listApprDocSecurity'/></a></li>
					<li class="no_child"><a id="apprReceptionMgntLinkOfLeft" href="<c:url value="/approval/admin/reception/updateReceptionForm.do"/>"><ikep4j:message pre='${preMenu}' key='receptionMgnt'/></a></li>
*/ %>					
					<li class="no_child liLast"><a id="apprPwdInitFormLinkOfLeft" href="<c:url value="/approval/admin/apprPwdInit/apprPwdInitForm.do"/>"><ikep4j:message pre='${preMenu}' key='apprPwdInitForm'/></a></li>
				</ul>	
			</li>
			<li class="opened">
				<a href="#" onclick="menuSetCookie('IKEP_DAPPROVAL_MENU_SAVED10', '#IKEP_DAPPROVAL_MENU10'); return false;"><img src="<c:url value='/base/images/icon/ic_appr_left_menu09.gif'/>" width="16" height="16" alt="iKEP icon"><ikep4j:message pre='${preMenu}' key='main10'/></a>
				<ul id="IKEP_DAPPROVAL_MENU10">
					<li class="no_child"><a id="updateApprConfigLinkOfLeft" href="<c:url value="/approval/admin/config/updateApprConfigForm.do"/>"><ikep4j:message pre='${preMenu}' key='updateApprConfigForm'/></a></li>
<%  /*					<li class="no_child"><a id="apprSystemListLinkOfLeft" href="<c:url value="/approval/admin/apprSystem/apprSystemList.do"/>"><ikep4j:message pre='${preMenu}' key='apprSystemList'/></a></li>*/ %>
					<li class="no_child"><a id="listApprFormLinkOfLeft" href="<c:url value="/approval/admin/apprAdminForm/listApprForm.do"/>"><ikep4j:message pre='${preMenu}' key='listApprForm'/></a></li>
					<li class="no_child"><a id="listViewLinkOfLeft" href="<c:url value="/approval/admin/apprDefLine/listView.do"/>"><ikep4j:message pre='${preMenu}' key='listView'/></a></li>
					<li class="no_child"><a id="apprCodeLinkOfLeft" href="<c:url value="/approval/admin/apprCode/listApprCode.do"/>"><ikep4j:message pre='${preMenu}' key='listApprCode'/></a></li>
<%  /*					<li class="no_child liLast"><a id="apprOfficialConfigOfLeft" href="<c:url value="/approval/admin/apprOfficialConfig/apprOfficialConfigForm.do"/>"><ikep4j:message pre='${preMenu}' key='apprOfficialConfigForm'/></a></li>*/ %>
				</ul>	
			</li>
			<li class="opened">
				<a href="#" onclick="menuSetCookie('IKEP_DAPPROVAL_MENU_SAVED11', '#IKEP_DAPPROVAL_MENU11'); return false;" ><img src="<c:url value='/base/images/icon/ic_appr_left_menu10.gif'/>" width="16" height="16" alt="iKEP icon"><ikep4j:message pre='${preMenu}' key='main11'/></a>
				<ul id="IKEP_DAPPROVAL_MENU11">
					<li class="no_child"><a id="timeStatLinkOfLeft" href="<c:url value="/approval/admin/apprStat/timeStatForm.do"/>"><ikep4j:message pre='${preMenu}' key='docLeadTime'/></a></li>
<%  /*					<li class="no_child"><a id="userStatLinkOfLeft" href="<c:url value="/approval/admin/apprStat/userStat.do"/>"><ikep4j:message pre='${preMenu}' key='userStats'/></a></li>*/%>
					<li class="no_child liLast"><a id="docStatLinkOfLeft" href="<c:url value="/approval/admin/apprStat/formStat.do"/>"><ikep4j:message pre='${preMenu}' key='docStats'/></a></li>
				</ul>	
			</li>
			</c:if>
		</ul>
	</div>
	<div class="pt20">
	</div>