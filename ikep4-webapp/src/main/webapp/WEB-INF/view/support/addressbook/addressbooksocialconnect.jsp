<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.addressbook.header" />
<c:set var="prePrivate"  value="ui.support.addressbook.addrgroup.private" />
<c:set var="preSocialConnect"  value="ui.support.addressbook.socialconnection" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/units/support/addressbook/addressbook.js"/>"></script>
<script type="text/javascript">
<!--

var tab;

(function($) {

	$(document).ready(function() {
		
		// 화면 로딩시 각각 페이지 호출 시작
		Addressbook.getLeftMenuView();
		Addressbook.getPrivateAddrgroupView('');
		Addressbook.getPublicAddrgroupView('');
		// 화면 로딩시 각각 페이지 호출 종료
		
			tab = $("#divSCTabs").tabs({
			select : function(event, ui) {
				switch($(ui.panel).attr("id")) {
					case "tab-initmate" : 
						alert("initmate");
						break;
					case "tab-following" : 
						getFollowUser('','following');
						break;
					case "tab-follower" : 
						getFollowUser('','follower');
						break;
				}
			}
		});
					
	});
	
	
	getFollowUser = function(standardType,userType){

		var standardUserId = '';
		var url = "";
		
		// Following 조회
		if("following" == userType )
		{
			url = "<c:url value='/socialpack/microblogging/getFollowingList.do'/>";
		} // Follower 조회
		else if("follower" == userType )
		{
			url = "<c:url value='/socialpack/microblogging/getFollowerList.do'/>";
		}

		// 타인 블로그일 때 공통으로 친구인 리스트 조회 여부.
		var bothFollow = "N";
		$jq.post(url, 
				// 블로그주인${userId}				,로그인사람과 동시에 follow여부, 조회해온 데이터를 앞/뒤에 붙일건지, 	조회기준 UserId
				{'ownerId':'${userId}', 'bothFollow':bothFollow, 'standardType':standardType, 'standardUserId':standardUserId}, 
				function(data) {
					if( data == ''){
						data ="<div>";
					}
					if("following" == userType ){
						$jq("#followingList").html(data);
					}else{
						$jq("#followerList").html(data);
					}
		});
	};
	
})(jQuery);  
//-->
</script>

		<!--blockMain Start-->
		<div id="blockMain">

			<!--leftMenu Start-->
			<div id="leftMenu">
			</div>	
			<!--//leftMenu End-->
				
			<!--mainContents Start-->
			<div id="mainContents" >
				
				<h1 class="none"><ikep4j:message pre='${preHeader}' key='contents'/></h1>
		
					<!--subTitle_2 Start-->
					<div id="pageTitle">
						<h2><ikep4j:message pre="${prePrivate}" key="socialconnection.title" /></h2>
					</div>
					<!--//subTitle_2 End-->
		
					<!-- listbody Start -->
					<div id="listbody">
					
						<!--divTabs Start-->
						<div id="divSCTabs" class="iKEP_tab">
						    <ul>
						        <li><a href="#tab-initmate"><ikep4j:message pre="${preSocialConnect}" key="intimateWith" /></a></li>
						        <li><a href="#tab-following"><ikep4j:message pre="${preSocialConnect}" key="Following" /></a></li>
						        <li><a href="#tab-follower"><ikep4j:message pre="${preSocialConnect}" key="Follower" /></a></li>
						    </ul>
						    <div class="tab_con">
						        <div id="tab-initmate"><div id="initmateList">첫번째 탭 내용</div></div>
						        <div id="tab-following"><ul class="microblog_li" id="followingList"></ul></div>
						        <div id="tab-follower"><ul class="microblog_li" id="followerList"></ul></div>
						    </div>               
						</div>       
						<!--//divTabs End-->
					
					</div>
					<!--//listbody End-->
				</div>
								
			</div>
			<!--//mainContents End-->
			<div class="clear"></div>
		</div>
		<!--//blockMain End-->	