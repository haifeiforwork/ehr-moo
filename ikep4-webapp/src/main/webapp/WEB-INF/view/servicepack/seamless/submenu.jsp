<%--
=====================================================
* 기능 설명 : SEAMLESSMESSAGE Sub Menu Page
* 작성자 : 손정환
=====================================================
--%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="preMenu"  value="ui.servicepack.seamless.Menu" /> 
<c:set var="preButton"  value="ui.servicepack.seamless.button" /> 
<c:set var="preMsg"  value="ui.servicepack.seamless.MSG" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
<!-- 
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		if("${user.mail}" == "") { alert("<ikep4j:message pre='${preMsg}' key='noMail'/>"); }
		$jq("#newMessageButton").click(function() {   
			var url = "<c:url value='/servicepack/seamless/messageSendView.do'/>";
			iKEP.popupOpen(url , {width:500, height:500});
			setTimeout(mailSyncState, 10000);
		}); 
		iKEP.setLeftMenu();
		var dialogPopLayer = $jq("#setingLayer").dialog({width: 350, height:150, modal:true, resizable: false,autoOpen:false});
		//레이어 나오기
		popUserSetOpen = function() {
			dialogPopLayer.dialog("open");
		};
		
		$jq("#saveButton").click( function() {
			setId = "${user.userId}";
			var isSourceDelete = 0;
			var autoConnect = 0;
			url = "<c:url value='/servicepack/seamless/updateUserSeting.do'/>";
			if($jq('#isSourceDelete').is(":checked")) isSourceDelete = 1;
			if($jq('#autoConnect').is(":checked")) autoConnect = 1;
			$jq.ajax({
			 	type: 'post',
			 	url: url,
			 	data: {'userId':setId, 'isSourceDelete':isSourceDelete, 'autoConnect':autoConnect}
			 })
			 .success(function(data) {
				alert("<ikep4j:message pre='${preMsg}' key='saveOk'/>");
			    	$jq("#setingLayer").dialog("close");
		    })    
			.error(function(event, request, settings) { alert("error"); });  		
		});
		
		$jq("#cancleButton").click( function() {
			dialogPopLayer.dialog("close");
		});
		$jq("#connectInbox").click( function() {
			setId = "${user.userId}";
			url = "<c:url value='/servicepack/seamless/connectImapMail.do'/>";
			if($jq("#divInbox").hasClass("mail_sync1") ) {
				if($jq("#divSent").hasClass("mail_sync2")) {
					alert('Please wait while mail system syncronizing!');
					return;
				}
				$jq("#divInbox").attr("class","mail_sync2")
				$jq("#spanInbox").text("<ikep4j:message pre='${preMsg}' key='mailSynchro'/>");
				$jq.ajax({type: 'post',	url: url, data: {'folderName':"inbox"}})
				 .success(function(data) {
					if(data == "ASYNC") {
						alert("<ikep4j:message pre='${preMsg}' key='syncStart'/>");
						setTimeout(mailSyncState, 10000);
					} else {
						$jq("#divInbox").attr("class","mail_sync1");
						$jq("#spanInbox").text("<ikep4j:message pre='${preMsg}' key='syncMail'/>");
						if(data == "OK") { 
							alert("<ikep4j:message pre='${preMsg}' key='syncSuccess'/>") 
							location.href="<c:url value='/servicepack/seamless/receiveListView.do'/>"; 
						};
					}
			    })    
				.error(function(event, request, settings) { alert("error"); });
			}
		});
		$jq("#connectSent").click( function() {
			setId = "${user.userId}";
			url = "<c:url value='/servicepack/seamless/connectImapMail.do'/>";
			if($jq("#divSent").hasClass("mail_sync1")) {
				
				if($jq("#divInbox").hasClass("mail_sync2")) {
					alert('Please wait while mail system syncronizing!');
					return;
				}
				
				$jq("#divSent").attr("class","mail_sync2")
				$jq("#spanSent").text("<ikep4j:message pre='${preMsg}' key='mailSynchro'/>");
				$jq.ajax({type: 'post',	url: url, data: {'folderName':"sent"}})
				 .success(function(data) {
					if(data == "ASYNC") {
						alert("<ikep4j:message pre='${preMsg}' key='syncStart'/>");
						setTimeout(mailSyncState, 10000);
					} else {
						$jq("#divSent").attr("class","mail_sync1");
						$jq("#spanSent").text("<ikep4j:message pre='${preMsg}' key='syncMail'/>");
						if(data == "OK") { 
							alert("<ikep4j:message pre='${preMsg}' key='syncSuccess'/>") 
							location.href="<c:url value='/servicepack/seamless/sendListView.do'/>"; 
						};
					}
			    })    
				.error(function(event, request, settings) { alert("error"); });
			}
		});
		<c:if test="${seamlessUserSeting.inboxSyncComplete eq 1}">
			$jq("#divInbox").attr("class","mail_sync2")
			$jq("#spanInbox").text("<ikep4j:message pre='${preMsg}' key='mailSynchro'/>");
			mailSyncState();
		</c:if>
		<c:if test="${seamlessUserSeting.sentSyncComplete eq 1}">
			$jq("#divSent").attr("class","mail_sync2")
			$jq("#spanSent").text("<ikep4j:message pre='${preMsg}' key='mailSynchro'/>");
			mailSyncState();
		</c:if>

		$jq("#leftMenu").attr('class', 'leftMenu_service');
		$jq("#mainContents").attr('class', 'conPadding_1');
		$jq("body").attr('class', 'bg_smsvs');
	});
	mailSyncState = function() {
		url = "<c:url value='/servicepack/seamless/mailSyncState.do'/>";
		$jq.ajax({type: 'post',	url: url})
		.success(function(result) {
			var data = $jq.parseJSON(result);
			if ($jq("#divInbox").hasClass("mail_sync2") && data.inboxSyncComplete == 0) {
				$jq("#divInbox").attr("class","mail_sync1")
				$jq("#spanInbox").text("<ikep4j:message pre='${preMsg}' key='syncMail'/>");
				 if(confirm("<ikep4j:message pre='${preMsg}' key='receiveSyncConf'/>")) {
					 location.href="<c:url value='/servicepack/seamless/receiveListView.do'/>"; 
				 }
			}
			if ($jq("#divSent").hasClass("mail_sync2") && data.sentSyncComplete == 0) {
				$jq("#divSent").attr("class","mail_sync1")
				$jq("#spanSent").text("<ikep4j:message pre='${preMsg}' key='syncMail'/>");
				if(confirm("<ikep4j:message pre='${preMsg}' key='sentSyncConf'/>")) {
					 location.href="<c:url value='/servicepack/seamless/sendListView.do'/>"; 
				 }
			}
			if (data.inboxSyncComplete == 1 || data.sentSyncComplete == 1) {
				setTimeout(mailSyncState, 10000);
			}
		});
		
	};
//-->
</script>
<!--popup Start-->
<div id="setingLayer" title="<ikep4j:message pre='${preMenu}' key='popTitle'/>" class="none">
	<div class="blockDetail">
		<table>
			<caption></caption>
			<tbody>
				<tr>
					<th style="text-align:left">
						<ikep4j:message pre='${preMenu}' key='isSourceDelete'/>
						<input id="isSourceDelete"  class="checkbox" title="checkbox" type="checkbox" <c:if test="${seamlessUserSeting.isSourceDelete eq 1}">checked</c:if> />
					</th>
				</tr>	
				<tr>
					<th style="text-align:left">
						<ikep4j:message pre='${preMenu}' key='autoConnect'/>
						<input id="autoConnect"  class="checkbox" title="checkbox" type="checkbox" <c:if test="${seamlessUserSeting.autoConnect eq 1}">checked</c:if> />		
					</th>
				</tr>			
			</tbody>
		</table>
	</div>
	<div class="blockButton" style="text-align:center"> 
		<a id="saveButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a>
		<a id="cancleButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancle'/></span></a>
	</div>
</div>
<!--popup End-->

		<!--corner_RoundBox07 Start-->
		<div class="corner_RoundBox07">		
			
			<h1 class="none">leftMenu</h1>
			<h2><a href="#a"><ikep4j:message pre='${preMenu}' key='sideTitle'/></a></h2>
			<div class="blockButton_2 mb10"> 
				<a class="button_2" id="newMessageButton" href="#a"><span><img src="<c:url value="/base/images/icon/ic_message_2.gif"/>" alt="" /> New Messages</span></a>				
			</div>	
			<div class="addr_setting">
				<a href="#a" title="setting" onclick="popUserSetOpen();"><ikep4j:message pre="${preMenu}" key="userSetting" /></a> 
			</div>	
			<div class="left_fixed">
				<ul>
					<li class="liFirst no_child<c:if test="${viewCode eq 'R'}"> licurrent</c:if>" style="position:relative; width:auto;" >
						<a href="<c:url value='/servicepack/seamless/receiveListView.do'/>"><ikep4j:message pre="${preMenu}" key="receiveListView" /></a>
						<div id="divInbox" class="mail_sync1">
							<a id="connectInbox" href="#a" title="setting"><span id="spanInbox"><ikep4j:message pre='${preMsg}' key='syncMail'/></span></a> 
						</div>
					</li>
					<li class="no_child<c:if test="${viewCode eq 'S'}"> licurrent</c:if>" style="position:relative;">
						<a href="<c:url value='/servicepack/seamless/sendListView.do'/>"><ikep4j:message pre="${preMenu}" key="sendListView" /></a>
						<div id="divSent" class="mail_sync1">
							<a id="connectSent" href="#a" title="setting"><span id="spanSent"><ikep4j:message pre='${preMsg}' key='syncMail'/></span></a> 
						</div>
					</li>	
				</ul>
				<div class="boxList mt-1">
					<p><ikep4j:message pre="${preMenu}" key="contactHistoryView" /></p>
					<a href="<c:url value='/servicepack/seamless/contactHistoryView.do'/>"><img src="<c:url value="/base/images/common/btn_more.gif"/>" class="more" alt=""/></a>					
				</div>
				<div class="boxList_sub">
					<ul>
						<c:forEach var="mcontacUser" items="${mcontacUserList}" varStatus = "status"> 
							<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									<li class="ellipsis" title="${mcontacUser.userName} ${mcontacUser.jobTitleName} ${mcontacUser.teamName}">
									<a href="<c:url value='/servicepack/seamless/contactHistoryView.do?setUser=${mcontacUser.userId}'/>">${mcontacUser.userName} ${mcontacUser.jobTitleName} <span class="sublist">${mcontacUser.teamName}</span></a></li>
								</c:when>
								<c:otherwise>
									<li class="ellipsis" title="${mcontacUser.userEnglishName} ${mcontacUser.jobTitleEnglishName} ${mcontacUser.teamEnglishName}">
									<a href="<c:url value='/servicepack/seamless/contactHistoryView.do?setUser=${mcontacUser.userId}'/>">${mcontacUser.userEnglishName} ${mcontacUser.jobTitleEnglishName} <span class="sublist">${mcontacUser.teamEnglishName}</span></a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ul>
				</div>
				<c:if test="${adminYn eq 'Y'}"> 
				<ul>
					<li class="liFirst opened mb10"><a href="#a">Administration</a>
						<ul>
							<li class="no_child<c:if test="${viewCode eq 'A'}"> licurrent</c:if>"><a href="<c:url value='/servicepack/seamless/adminView.do'/>"><ikep4j:message pre="${preMenu}" key="adminView" /></a></li>	
						</ul>					
					</li>				
				</ul>
				</c:if>
			</div>	

			<div class="l_t_corner"></div>
			<div class="r_t_corner"></div>
			<div class="l_b_corner"></div>
			<div class="r_b_corner"></div>				
		</div>
		<!--//corner_RoundBox07 End-->										
			
