<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.addressbook.header" /> 
<c:set var="prePrivate"    value="ui.support.addressbook.addrgroup.private" />
<c:set var="prePublic"  value="ui.support.addressbook.addrgroup.public" />

<c:set var="preAddrMessage" value="message.support.addressbook.addrgroup" />
<c:set var="prePerMessage" value="message.support.addressbook.person" />
<c:set var="prePerButton"  value="ui.support.addressbook.person.button" /> 
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
                               
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript" src="<c:url value="/base/js/units/support/addressbook/addressbook.js"/>"></script>
<script type="text/javascript">
<!--
(function($) {
	
	// 등록된 연락처 조회
	getPersonListByAddrView = function(data) {

		$jq.ajax({
		    url : '<c:url value="/support/addressbook/readAddrbookListByAddrgroup.do"/>',
		    data : data,
		    type : "post",
		    loadingElement : {container:"#mainContents"}, 
		    success : function(result) {
		    	$jq("#addList").html(result);
		    }
		});
		
	};
	
	setSearchInex = function() {
		var arraySearchIndex_ko = ['전체','가','나','다','라','마','바','사','아','자','차','카','타','파','하','A-Z','0-9'];
		var arraySearchIndex_en = ['ALL','A','B','C','D','E','F','G','H','I','J','K' ,'L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','etc','0-9'];
		var searchInexHtml = "<ul>";
		var cnt = 1;
		if( '${user.localeCode}' == 'ko'){
			$.each(arraySearchIndex_ko, function(index, value){
				searchInexHtml = searchInexHtml + "<li><a href=\"#index"+cnt+"\" onclick=\"getPersonListByIndexText(\'"+arraySearchIndex_ko[index]+"\')\" >"+arraySearchIndex_ko[index]+"<\/a><\/li>";
				$jq("#addressTab").append('<div id="index'+cnt+'"><\/div>');
				cnt++;
			});
		}else{
			$.each(arraySearchIndex_en, function(index, value){
				searchInexHtml = searchInexHtml + "<li><a href=\"#a\" onclick=\"getPersonListByIndexText(\'"+arraySearchIndex_en[index]+"\')\" >"+arraySearchIndex_en[index]+"<\/a><\/li>";
				$jq("#addressTab").append('<div id="index'+cnt+'"><\/div>');
				cnt++;
			});
		}
		searchInexHtml = searchInexHtml + "<ul>";
		$jq(".iKEP_tab_s").html(searchInexHtml); 
		$("#addressTab").tabs();
	}
	

	
	getPersonListByIndexText = function(indexText) {
		$("input[name=indexSearchText]").attr("value",indexText);
		getPersonListByAddrView($jq('#searchForm').serialize());
	};
	
    // Person 연락처 삭제 
	deletePersonList= function(addrgroupId,groupType) {
		
		var countCheckBox	=	$("input[name=personIdList]:checkbox:checked").length;
		if(countCheckBox>0){
			
			if(confirm('<ikep4j:message pre="${prePerMessage}" key="confirm.delete"/>')){
				
				$jq.ajax({
				    url : '<c:url value="/support/addressbook/deletePersonList.do"/>',
				    data : $("#personlistForm").serialize(),
				    type : "post",
				    success : function(result) {
				    	getPersonListByAddrView($jq('#searchForm').serialize());
				    }
				}).error(function(event, request, settings) { alert("error"); });
	    	}
	    }else{
	    	alert("<ikep4j:message pre='${prePerMessage}' key='noselect.delete'/>");
	    }
	    
	};
	
	copyTeamAddr= function(addrgroupId,groupType) {
		
		var countCheckBox	=	$("input[name=personIdList]:checkbox:checked").length;
		if(countCheckBox>0){
			$jq.ajax({
			    url : '<c:url value="/support/addressbook/copyTeamList.do"/>',
			    data : $("#personlistForm").serialize(),
			    type : "post",
			    success : function(result) {
			    	alert("팀주소록으로 복사되었습니다.");
			    	getPersonListByAddrView($jq('#searchForm').serialize());
			    }
			}).error(function(event, request, settings) { alert("error"); });
	    }else{
	    	alert("복사할 연락처를 선택하세요.");
	    }
	    
	};
	
	copyMyAddr= function(addrgroupId,groupType) {
		
		var countCheckBox	=	$("input[name=personIdList]:checkbox:checked").length;
		if(countCheckBox>0){
			$jq.ajax({
			    url : '<c:url value="/support/addressbook/copyMyList.do"/>',
			    data : $("#personlistForm").serialize(),
			    type : "post",
			    success : function(result) {
			    	alert("내주소록으로 복사되었습니다.");
			    	getPersonListByAddrView($jq('#searchForm').serialize());
			    }
			}).error(function(event, request, settings) { alert("error"); });
	    }else{
	    	alert("복사할 연락처를 선택하세요.");
	    }
	    
	};
	
    // Person 연락처 삭제 
	removePersonFromGroup = function(addrgroupId,groupType) {

    	if( addrgroupId == '' ){
			alert("<ikep4j:message pre='${preAddrMessage}' key='dataError.delete'/>");
		}else{
			var countCheckBox	=	$("input[name=personIdList]:checkbox:checked").length;
			
			if(countCheckBox>0){
		    	if(confirm('<ikep4j:message pre="${prePerMessage}" key="confirm.remove"/>')){
		    		
		    		$jq.ajax({
					    url : '<c:url value="/support/addressbook/removePersonFromGroup.do"/>',
					    data : $("#personlistForm").serialize(),
					    type : "post",
					    success : function(result) {
					    	getPersonListByAddrView($jq('#searchForm').serialize());
					    }
					}).error(function(event, request, settings) { alert("error"); });
		    		
		    	}
			}else{
	    		alert("<ikep4j:message pre='${prePerMessage}' key='noselect.delete'/>");
	    	}
    	}
	    
	};
		
	goAddrbookMain = function() {
		document.location.href = "<c:url value='/support/addressbook/addressbookHome.do'/>" ;
	};
	
	addInAddr = function(addrgroupId) {
		document.location.href = "<c:url value='/support/addressbook/editAddrgroupList.do'/>?addrgroupId="+addrgroupId+"&groupType=P" ;
	};
	
	addOutAddr = function(addrgroupId) {
		if(addrgroupId == "NOGROUP") addrgroupId = "";
		document.location.href = "<c:url value='/support/addressbook/editPerson.do'/>?addrgroupId="+addrgroupId+"&groupType=P&personId=" ;
	};
	
	//뷰모드 체인지 함수
	changeViewMode = function(changeViewMode) {  
		$("#searchForm input[name=viewMode]").val(changeViewMode);
		getPersonListByAddrView($jq('#searchForm').serialize());
		return false; 
	}; 
	
	
	$jq(document).ready(function() {
		
		// 화면 로딩시 각각 페이지 호출 시작
		//Addressbook.getLeftMenuView();
		Addressbook.getPrivateAddrgroupView('${addrgroupId}');
		Addressbook.getPrivatePaddrgroupView('${addrgroupId}');
		Addressbook.getPublicAddrgroupView('${addrgroupId}');
		Addressbook.getTeamAddrgroupView('${addrgroupId}');
		iKEP.setLeftMenu();
		
		// Tab 동적 생성
		setSearchInex();
		
		getPersonListByAddrView({'addrgroupId':'${addrgroupId}','groupType':'${groupType}','addrgroupName':'${addrgroupName}','userTypeIn':'Y','userTypeOut':'Y'});
		// 화면 로딩시 각각 페이지 호출 종료

		
		$jq("select[name=pagePerRecord]").change(function() {
            //검색용 폼을 서브밋하는 코드 넣으시면 됩니다.
			getPersonListByAddrView($jq('#searchForm').serialize());
        });
		
	});
	
})(jQuery);  
//-->
</script>


		<!--blockMain Start-->
		<div id="blockMain">

			<!--leftMenu Start-->
			<div id="leftMenu">
				<h1 class="none"><ikep4j:message pre='${preHeader}' key='leftmenu'/></h1>
				<h2><a href="#a" onclick="goAddrbookMain()"><span><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_home_address.gif'/>"/></span></a></h2>
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

				<!--pageTitle Start-->			
				<div id="pageTitle">
					<c:choose>
						<c:when test="${empty groupType}">
							<h2><ikep4j:message pre='${prePrivate}' key='addressbook'/> - <ikep4j:message pre='${prePrivate}' key='totalList.title'/></h2>
						</c:when>
						<c:when test="${groupType == 'P'}">
							<h2><ikep4j:message pre='${prePrivate}' key='addressbook'/> - ${addrgroupName}</h2>
						</c:when>
						<c:when test="${groupType == 'G'}">
							<h2><ikep4j:message pre='${prePrivate}' key='addressbook'/> - ${addrgroupName}</h2>
						</c:when>
						<c:when test="${groupType == 'T'}">
							<h2>팀주소록 - ${addrgroupName}</h2>
						</c:when>
						<c:otherwise>
							<h2><ikep4j:message pre='${prePublic}' key='addressbook'/> - ${addrgroupName}</h2>
						</c:otherwise>
					</c:choose>
				</div>
				
				<!--//pageTitle End-->
				
				<!--tab Start-->
				<div class="blockListTable">
				
					<!--tab Start-->		
					<div id="addressTab" style="border:medium none;">
						<div class="iKEP_tab_s pd1"></div>
					</div>
					
				</div>
				<!--//tab End-->

				<div id="addList" class="mb10">
				</div>
				<!--//blockListTable End-->	
				
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
				 		<c:if test="${groupType == 'O' and addrgroupId != '' and publicManageFlag == 'true'}">
							<li><a class="button" href="#a" onclick="removePersonFromGroup('${addrgroupId}','${groupType}');" ><span><ikep4j:message pre='${prePerButton}' key='rowdelete'/></span></a></li>
						</c:if>
						<c:if test="${groupType == 'P'}">
							<li><a class="button" href="#a" onclick="copyTeamAddr('${addrgroupId}','${groupType}');" ><span>팀주소록복사</span></a></li>
							<li><a class="button" href="#a" onclick="addOutAddr('${addrgroupId}');" ><span><ikep4j:message pre='${prePerButton}' key='saveOutAddr'/></span></a></li>
							<c:if test="${addrgroupId == '' || addrgroupId == 'NOGROUP' }">
								<li><a class="button" href="#a" onclick="deletePersonList('${addrgroupId}','${groupType}');" ><span><ikep4j:message pre='${prePerButton}' key='addrdelete'/></span></a></li>
							</c:if>
							<c:if test="${!(addrgroupId == '' || addrgroupId == 'NOGROUP')}">
								<!-- <li><a class="button" href="#a" onclick="deletePersonList('${addrgroupId}','${groupType}');" ><span><ikep4j:message pre='${prePerButton}' key='addrdelete'/></span></a></li> -->
								<li><a class="button" href="#a" onclick="addInAddr('${addrgroupId}');" ><span><ikep4j:message pre='${prePerButton}' key='saveInAddr'/></span></a></li>
								<li><a class="button" href="#a" onclick="removePersonFromGroup('${addrgroupId}','${groupType}');" ><span><ikep4j:message pre='${prePerButton}' key='rowdelete'/></span></a></li>
							</c:if>
						</c:if>
						<c:if test="${groupType == 'G'}">
							<c:if test="${addrgroupId == '' || addrgroupId == 'NOGROUP' }">
								<li><a class="button" href="#a" onclick="deletePersonList('${addrgroupId}','${groupType}');" ><span><ikep4j:message pre='${prePerButton}' key='addrdelete'/></span></a></li>
							</c:if>
							<c:if test="${!(addrgroupId == '' || addrgroupId == 'NOGROUP')}">
								<!-- <li><a class="button" href="#a" onclick="deletePersonList('${addrgroupId}','${groupType}');" ><span><ikep4j:message pre='${prePerButton}' key='addrdelete'/></span></a></li> -->
								<li><a class="button" href="#a" onclick="addInAddr('${addrgroupId}');" ><span><ikep4j:message pre='${prePerButton}' key='saveInAddr'/></span></a></li>
								<li><a class="button" href="#a" onclick="removePersonFromGroup('${addrgroupId}','${groupType}');" ><span><ikep4j:message pre='${prePerButton}' key='rowdelete'/></span></a></li>
							</c:if>
						</c:if>
						<c:if test="${groupType == 'T'}">
							<li><a class="button" href="#a" onclick="copyMyAddr('${addrgroupId}','${groupType}');" ><span>내주소록복사</span></a></li>
							<li><a class="button" href="#a" onclick="removePersonFromGroup('${addrgroupId}','${groupType}');" ><span><ikep4j:message pre='${prePerButton}' key='rowdelete'/></span></a></li>
						</c:if>
					</ul>
				</div>
				<!--//blockButton End-->
														
			</div>
			<!--//mainContents End-->
			<div class="clear"></div>
		</div>

		<!--//blockMain End-->
