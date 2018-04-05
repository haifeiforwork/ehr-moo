<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.addressbook.header" /> 
<c:set var="prePrivate"    value="ui.support.addressbook.addrgroup.private" />
<c:set var="prePublic"  value="ui.support.addressbook.addrgroup.public" />

<c:set var="preExport"  value="ui.support.addressbook.export.list" />
<c:set var="preExpButton"  value="ui.support.addressbook.export.button" />
<c:set var="preExpMessage"  value="message.support.addressbook.export.list" />

<c:set var="preSumMessage"  value="message.support.addressbook.summary" />
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript" src="<c:url value="/base/js/units/support/addressbook/addressbook.js"/>"></script>
<script type="text/javascript">
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

	});
	
	goAddrbookMain = function() {
		document.location.href = "<c:url value='/support/addressbook/addressbookHome.do'/>" ;
	};
	
	// Save Upload Files
	downloadAddressbookFile = function() {

		var step1 = '';
		var step2 = '';
		
		$("#saveForm").find('input[name=step1]').each(function(){    
			 if($(this).is(':checked')){
				 //alert($(this).val());
				 step1 = $(this).val();
			 }	
		});
		$("#saveForm").find('input[name=step2]').each(function(){    
            if($(this).is(':checked')){
                //alert($(this).val());
                step2 = $(this).val();
            }  
       });
		
		$jq.ajax({
		    url : '<c:url value="/support/addressbook/chkDownloadAddressbookFile.do"/>',
		    data : {'step1':step1,'step2':step2,'gType':"${gType}"},
		    type : "get",
		    success : function(result) {
		    	if( result > 0 ){
		    		if(step1 == 'outlook'){
		    	        $("#saveForm").attr("action","<c:url value='/support/addressbook/downloadAddressbookCSVFile.do'/>");
		    		}
		    		else{
		    			$("#saveForm").attr("action","<c:url value='/support/addressbook/downloadAddressbookExcelFile.do'/>?gType="+"${gType}");
		    		}
		    	    $("#saveForm").submit();
	                $("#saveForm").attr("action","<c:url value='/support/addressbook/downloadAddressbookFile.do'/>?gType="+"${gType}");	
		    	}else{
		    		alert("<ikep4j:message pre='${preExpMessage}' key='nodata'/>");
		    	}
		    }
		}).error(function(event, request, settings) { alert("error"); });
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
					<h2><ikep4j:message pre='${preExport}' key='title'/></h2>
				</div>
				
				<!--//subTitle_2 End-->
				<form id="saveForm" method="post" action="<c:url value='/support/addressbook/downloadAddressbookFile.do'/>">
				<div class="corner_RoundBox01 mb10">
					<ikep4j:message pre='${preExport}' key='context'/>
					<div class="l_t_corner"></div>
					<div class="r_t_corner"></div>
					<div class="l_b_corner"></div>
					<div class="r_b_corner"></div>
				</div>		
				
				<!--blockDetail Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${preSumMessage}' key='addrManageTable'/>">
						<tbody>
							<!-- tr>
								<th scope="row" width="18%"><ikep4j:message pre='${preExport}' key='fileType'/></th>
								<td width="82%">
									<label><input type="radio" class="radio" title="<ikep4j:message pre='${preExport}' key='fileType.vcard'/>" name="step1" value="vcard" checked="checked" /><img src="<c:url value='/base/images/icon/ic_menu_profile.png'/>" width="16" height="16" alt="" class="valign_top" />&nbsp;<ikep4j:message pre='${preExport}' key='fileType.vcard'/></label>&nbsp;&nbsp;
									<label>
									<input style="visibility:hidden" type="radio" class="radio" title="<ikep4j:message pre='${preExport}' key='fileType.xls'/>" name="step1" value="xls" checked="checked" />
									<ikep4j:message pre='${preExport}' key='fileType.xls' /></label>&nbsp;&nbsp;
									<label>
									<input style="visibility:hidden" type="radio" class="radio" title="<ikep4j:message pre='${preExport}' key='fileType.csv'/>" name="step1" value="outlook" />
									<ikep4j:message pre='${preExport}' key='fileType.csv' /></label>&nbsp;&nbsp;								
								</td>
							</tr-->
							<tr>
								<th scope="row"><ikep4j:message pre='${preExport}' key='targetType'/></th>
								<td  width="82%">
								    	<ul>
										<li class="mb5">
											<label><input type="radio" class="radio" title="<ikep4j:message pre='${preExport}' key='allAddress'/>" name="step2" value="" checked="checked" /><ikep4j:message pre='${preExport}' key='allAddress'/></label>									
										</li>
										<li>
										<c:if test="${gType == 'P'}">	
										<c:if test="${addrgroupList != null}">
										<c:forEach var="addrgroup" items="${addrgroupList.entity}" varStatus="status">
											<c:if test="${addrgroup.groupType == 'P'}">
											<label><input type="radio" class="radio" title="<ikep4j:message pre='${preExport}' key='targetType'/>" name="step2" value="${addrgroup.addrgroupId}" /><c:out value="${addrgroup.addrgroupName}"/></label>
											</c:if>
										</c:forEach>
										</c:if>		
										</c:if>
										<c:if test="${gType == 'G'}">	
										<c:if test="${paddrgroupList != null}">
										<c:forEach var="paddrgroup" items="${paddrgroupList.entity}" varStatus="status">
											<c:if test="${paddrgroup.groupType == 'G'}">
											<label><input type="radio" class="radio" title="<ikep4j:message pre='${preExport}' key='targetType'/>" name="step2" value="${paddrgroup.addrgroupId}" /><c:out value="${paddrgroup.addrgroupName}"/></label>
											</c:if>
										</c:forEach>
										</c:if>		
										</c:if>						
										</li>											
									</ul>												
								</td>
							</tr>
							    
							<!-- tr>
								<th scope="row"><ikep4j:message pre='${preExport}' key='column'/></th>
								<td>
									<ul>
										<li class="mb5">
											<input name="chkName" class="checkbox" title="checkbox" type="checkbox" checked="checked" value="TRUE" onclick="return(false);" disabled /><ikep4j:message pre='${preExport}' key='column.personName'/>&nbsp;&nbsp;		
											<input name="chkEmail" class="checkbox" title="checkbox" type="checkbox" checked="checked" value="TRUE" onclick="return(false);" disabled /><ikep4j:message pre='${preExport}' key='column.emailAddress'/>&nbsp;&nbsp;	
											<input name="chkCompany" class="checkbox" title="checkbox" type="checkbox" checked="checked" value="TRUE" onclick="return(false);" disabled /><ikep4j:message pre='${preExport}' key='column.companyName'/>&nbsp;&nbsp;	
											<input name="chkTeam" class="checkbox" title="checkbox" type="checkbox" checked="checked" value="TRUE" onclick="return(false);" disabled /><ikep4j:message pre='${preExport}' key='column.teamName'/>&nbsp;&nbsp;											
										</li>
										<li>
											<input name="chkJobRank" class="checkbox" title="checkbox" type="checkbox" checked="checked" value="TRUE" onclick="return(false);" disabled /><ikep4j:message pre='${preExport}' key='column.jobRankName'/>&nbsp;&nbsp;		
											<input name="chkPhone" class="checkbox" title="checkbox" type="checkbox" checked="checked" value="TRUE" onclick="return(false);" disabled /><ikep4j:message pre='${preExport}' key='column.mobilePhoneno'/>&nbsp;&nbsp;	
											<input name="chkOffPhone" class="checkbox" title="checkbox" type="checkbox" checked="checked" value="TRUE" onclick="return(false);" disabled /><ikep4j:message pre='${preExport}' key='column.officePhoneno'/>&nbsp;&nbsp;	
											<input name="chkMemo" class="checkbox" title="checkbox" type="checkbox" checked="checked" value="TRUE" onclick="return(false);" disabled /><ikep4j:message pre='${preExport}' key='column.memo'/>&nbsp;&nbsp;										
										</li>											
									</ul>
								</td>
							</tr-->
						</tbody>
					</table>
					<input style="visibility:hidden" type="radio" class="radio" title="<ikep4j:message pre='${preExport}' key='fileType.xls'/>" name="step1" value="xls" checked="checked" />
                    <input style="visibility:hidden" type="radio" class="radio" title="<ikep4j:message pre='${preExport}' key='fileType.csv'/>" name="step1" value="outlook" />
                            
				</div>
				</form>
				<!--//blockDetail End-->									
							
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li><a class="button" href="#a" onclick="downloadAddressbookFile()"><span><ikep4j:message pre='${preExpButton}' key='export'/></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->
			</div>
			<!--//mainContents End-->
			<div class="clear"></div>
		</div>
		<!--//blockMain End-->
	