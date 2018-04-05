<%--
=====================================================
* 기능 설명 : 다이나믹 타일즈2 Header Page
* 작성자 : 주길재
=====================================================
--%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>
<c:set var="prefix">message.templates.base.header</c:set>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<script type="text/javascript">
<!--
// server side time sync
iKEP.setLoadTime(${todayM});	// millisecond

(function($) {
	
	<c:forEach var="popup" items="${popupList}" varStatus="status">
		var dialog_${popup.popupId} ;
	</c:forEach>
	
    $jq(document).ready(function() {
    	var regurl=/eptest.moorim.co.kr/g;
    	if (regurl.test(location.href)) { 
    		changeLogoImage();
    	}
    	
    	$jq('#headerSearchWord').keyup( function(event) { 
    		if (event.which == '13' || event.which === undefined) {
    			headerSearch();
    		}
		});
    	
    	$jq('#selCombo').click(function(){
    		$jq('#selComboBox').toggle();
    	});
    	
    	/** 현재 시간(UTC/GMT) 기준 **/
		var clockOption = {
			//seedTime에 서버 GMT시간을 넣으면 됨. 시간 기준은 millisecond. 스크립트로는 new Date()).getTime() 
	      	seedTime: iKEP.getCurTime().getTime(),	//${todayM}
	      	<c:choose>
				<c:when test="${empty user.localeCode || user.localeCode eq 'en'}">format : "%A, %B %d %Y %I:%M:%S %P"</c:when>
				<c:otherwise>format : "%Y"+iKEPLang.clock.subscriptYear+" %m"+iKEPLang.clock.subscriptMonth+" %d"+iKEPLang.clock.subscriptDay+" (%a) %I:%M:%S %P"</c:otherwise>
			</c:choose>
	    };
		$('.info_date').jclock(clockOption);
    	
		var popupLeft = 50;
    	var popupWidth = 0;
    	var popupTop = 50;
    	var popupWidthInterval = 25;

    	var layerLeft = 50;
    	var layerTop = 100;
    	var layerWidth = 0;
    	var layerHeight = 0;
    	var layerWidthInterval = 25;
    	var layerTopInterval = 47;
		
		<c:forEach var="popup" items="${popupList}" varStatus="status">
			if($jq.cookie("IKEP_POPUP_${popup.popupId}") != 'none') {
				if(${popup.windowYn == 'Y'}) {
	    			if(${popup.popupWidth} < 250) {
	    				popupWidth = 250;
		    		} else {
		    			popupWidth = ${popup.popupWidth};
		    		}
					
					//Popup type
	    			iKEP.popupOpen('<c:url value="/portal/admin/screen/popup/mainPopup.do?popupId=${popup.popupId}"/>', {width:popupWidth, height:${popup.popupHeight}, top:popupTop, left:popupLeft, resizable:false, scrollbar:false}, 'popup${status.count}');
	    			popupLeft += popupWidth +  popupWidthInterval;
				} else {
	    			if(${popup.popupWidth} < 250) {
	    				layerWidth = 250;
	    				layerHeight = parseInt(${popup.popupHeight}) + parseInt(layerTopInterval);
		    		} else {
		    			layerWidth = ${popup.popupWidth};
		    			layerHeight = parseInt(${popup.popupHeight}) + parseInt(layerTopInterval);
		    		}
	    			
	    			dialog_${popup.popupId} = iKEP.showDialog({
						title: '${popup.popupName}',
						url: '<c:url value="/portal/admin/screen/popup/mainPopup.do?popupId=${popup.popupId}"/>',
						width: layerWidth,
						height: layerHeight,
						modal: false,
						scroll: "no",
						resizable:false,
						position : [layerLeft, layerTop]
					});
				
					layerLeft += layerWidth +  layerWidthInterval;
				}
			}
		</c:forEach>
		
    	<c:forEach var="quickMenu" items="${portalQuickMenuList}" varStatus="status">
    	<c:if test="${quickMenu.count == 1}">
    		//setTimeout(get<c:out value="${quickMenu.quickMenuId}"/>Count, 5000);
    		get<c:out value="${quickMenu.quickMenuId}"/>Count();    		
    	</c:if>
    	</c:forEach>
    	
    	<%--c:if test="${CommonConstant.PACKAGE_VERSION eq CommonConstant.IKEP_VERSION_FULL or CommonConstant.PACKAGE_VERSION eq CommonConstant.IKEP_VERSION_LIGHT}">
    	//쪽지 메세지 실행(5초후 실행)
    	setTimeout(quickMessageStart, 5000);
    	</c:if--%>
    });
    
    dialogClose = function(id) {
    	eval("dialog_" + id).close();
    }
    
    headerSearch = function() {
    	if($('input[name=headerSearchColumn]').val()=='tag'){
    		$("#headerSearchForm")[0].method = "get";
    		$("#headerSearchForm")[0].action = "<c:url value='/support/tagging/tagSearch.do'/>";
    		$('input[name=tagName]').val($('input[name=headerSearchWord]').val());
    	}else{
    		$("#headerSearchForm")[0].method = "post";
    		var searchWord = $('input[name=headerSearchWord]').val();
    		$("#headerSearchForm")[0].action = "<c:url value='/search/search.jsp?query=" + searchWord + "'/>";
    	}
    	$("#headerSearchForm")[0].submit();
		
	};
	
	setSearchKey = function(text, key) {
		$('input[name=headerSearchColumn]').val(key);
		$('#selText').text(text);
		$('#selComboBox').toggle();
	}
	
	quickMessageStart = function() {

		//quickMessage();
		
		//쪽지 Interval(1초 : 1000) 
		//setInterval(quickMessage, 60000);
	};
	
	quickMessage = function() {
		$jq.ajax({
			url : "<c:url value='/support/message/newMessageArrived.do'/>",
			data : {},
			type : "post",
			dataType : "html",
			success : function(result) {
				var data = $jq.parseJSON(result);
				
				if(data.count > 0) {
					var senderName = "";
					var senderJobTitle = "";
					
					<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}">
							senderName = data.senderName;
							senderJobTitle = data.senderJobTitle;
						</c:when>
						<c:otherwise>
							senderName = data.senderEnglishName;
							senderJobTitle = data.senderJobTitleEnglish;
						</c:otherwise>
					</c:choose>
						
					var str = '<div class="smsvs_layer">';
						str += '<span class="smsvs_layer_icon"><img src="<c:url value="/base/images/icon/ic_note_c.png"/>" alt="note" /></span> <span class="smsvs_layer_title">New Message Arrived! <span class="colorPoint">('+data.count+')</span></span>';
						str += '<div class="smsvs_layer_more"><a href="<c:url value="/support/message/messageListView.do"/>" onclick="closeMessagePopup();" target="mainFrame"><img src="<c:url value="/base/images/common/btn_more.gif"/>" alt="more" /></a></div>';
						str += '</div>';
						str += '<div class="smsvs_layer_con_t" style="width:236px;">';
						str += '<div class="smsvs_layer_con_tl">';
						str += '<span class="ellipsis"><a href="#a" onclick="popMessageInfo('+ data.messageId +');">'+ data.title +'</a></span>';
						str += '</div>';
						str += '</div>';
						str += '<div class="smsvs_layer_con_info">';
						str += '<div class="smsvs_layer_con_name"><a href="#a">'+senderName+' '+senderJobTitle+'</a></div>';
						str += '<div class="smsvs_layer_con_date">'+data.receiverDate+'</div>';
						str += '</div>';
						
						//쪽지 내용 표시(5초동안) 
						iKEP.alarm(str, 5);				
				}
			}
		});
	}
	
	closeMessagePopup = function() {
		var layer_01 = $jq("div.layer_01");
		$jq("a.layer_close", layer_01).trigger("click");
	}
	
	popMessageInfo = function(messageId) {
		closeMessagePopup();
		
		url = '<c:url value="/support/message/portlet/unifiedMessage/messageView.do"/>';
		$jq.post(url, 
				{'messageId':messageId}, 
				function(data) {
					if("" != data){
						data = $jq.trim(data);
						$jq("#mainPopMessageView").html(data);
						var result = $jq("#mainPopMessageLayer").dialog({width: 400, height:300, modal:false, resizable: false});
				    	return false; 
					}
		});
	};
	
	changeLogoImage = function (logoImageId) {
		var regurl=/eptest.moorim.co.kr/g;
		var defaultLogo="<c:url value='/base/images/common/logo.png'/>";
		if (regurl.test(location.href)) { 
			defaultLogo="<c:url value='/base/images/common/logo_test.gif'/>";
		}else{
			defaultLogo="<c:url value='/base/images/common/logo.png'/>";
		}
		var imgSrc = logoImageId ? "<c:url value='/support/fileupload/downloadFile.do'/>?fileId=" + logoImageId : defaultLogo;

		$("img#logo_bg").attr("src", imgSrc);
	};
})(jQuery);

<c:forEach var="quickMenu" items="${portalQuickMenuList}" varStatus="status">
	<c:if test="${quickMenu.count == 1}">
	function get<c:out value="${quickMenu.quickMenuId}"/>Count() {
		
		var url = "<c:url value='${ikep4j:urlConverter(quickMenu.countUrl, user)}'/>";
		
		$jq.ajax({
			url : url,
			type:"post",
			dataType : "html",
			success : function(data) {	
				
				var count = 0;
				
				if($jq.parseJSON(data) != null) {
					count = $jq.parseJSON(data).count;
					
					if(count == null || count == '' || count < 1) {
						count = 0;
					}
				}
				
				//$jq("#quick_${quickMenu.quickMenuId} > .quick_num").remove();
				//$jq("#quick_${quickMenu.quickMenuId}").append("<div class='quick_num'><a href='#' onclick='return false;'><span>"+count+"</span></a></div>");
				$jq("#quick_${quickMenu.quickMenuId} > .quick_num > a > span").html(count);
			},
			error : function(event, request, settings) { 
				//$jq("#quick_${quickMenu.quickMenuId} > .quick_num").remove();
				//$jq("#quick_${quickMenu.quickMenuId}").append("<div class='quick_num'><a href='#' onclick='return false;'><span>0</span></a></div>");
				$jq("#quick_${quickMenu.quickMenuId} > .quick_num > a > span").html("0");
			}
		});
	}
	
		<c:if test="${quickMenu.intervalTime > 0}">
		setInterval(get<c:out value="${quickMenu.quickMenuId}"/>Count, 60000*<c:out value="${quickMenu.intervalTime}"/>);
		</c:if>
	
	</c:if>
</c:forEach>

function logout() {
	if(confirm("<ikep4j:message pre="${prefix}" key="confirm.logoutMessage" />")) {
		/*
		var serverLinkUrl="";
		if(location.hostname=="eptest.moorim.co.kr"){
			serverLinkUrl ="http://smrmeqas.moorim.co.kr:8000/sap/bc/bsp/sap/";
		}else{
			serverLinkUrl ="http://smrmep02.moorim.co.kr:8000/sap/bc/bsp/sap/";
		}
		<c:if test="${!empty user.sapId}">
		essLoginFrame.location.href=serverLinkUrl+"zhr_es_001/index.htm";
		</c:if>
		*/
		location.href = "<c:url value='/logout.do'/>";		
	}
}
//-->
</script>
<!--topInfo Start-->	
<div id="topInfo">
	<h1 class="none"><ikep4j:message pre="${prefix}" key="title1" /></h1>
	<div id="topLogo">
		<a href="<c:url value='/portal/main/portalMain.do'/>">
			<c:if test="${logoImageYn == 'Y'}">
				<img id="logo_bg" src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${logoImageId}" title="<ikep4j:message pre="${prefix}" key="img.ikepLogo" />" alt="<ikep4j:message pre="${prefix}" key="img.ikepLogo" />"/>
			</c:if>
			<c:if test="${logoImageYn != 'Y'}">
				<img id="logo_bg" src="<c:url value='/base/images/common/logo.png'/>" title="<ikep4j:message pre="${prefix}" key="img.ikepLogo" />" alt="<ikep4j:message pre="${prefix}" key="img.ikepLogo" />"/>
			</c:if>
		</a>
	</div>
	<!--utilMenu Start-->	
	<div id="utilMenu">
		<ul>
			<li class="utilmenu_system"><a href="#" id="systemLink" title="<ikep4j:message pre='${prefix}' key='img.system' />" onclick="return false;"><span><ikep4j:message pre='${prefix}' key='img.system' /></span></a></li>
			<c:if test="${CommonConstant.PACKAGE_VERSION eq CommonConstant.IKEP_VERSION_FULL}">			
				<li class="utilmenu_sitemap"><a target="mainFrame" href="<c:url value="/portal/main/portalSitemap.do"/>" title="<ikep4j:message pre="${prefix}" key="img.sitemap" />"><span><ikep4j:message pre="${prefix}" key="img.sitemap" /></span></a></li>
				<li class="utilmenu_help"><a href="#a" id="onlineHelpLink" title="<ikep4j:message pre="${prefix}" key="img.help" />"><span><ikep4j:message pre="${prefix}" key="img.help" /></span></a></li>
			</c:if>
			<c:if test="${isAdmin}">
				<li class="utilmenu_admin"><a target="mainFrame"  href="<c:url value="/portal/admin/screen/loginImage/updateLoginImageForm.do?cookieFlag=Y"/>" title="<ikep4j:message pre="${prefix}" key="img.admin" />"><span><ikep4j:message pre="${prefix}" key="img.admin" /></span></a></li>
			</c:if>
		</ul>
	</div>
	<!--//utilMenu End-->
	<!--personInfo Start-->
	<div id="personInfo">
		<span class="info_date"></span>
		<span class="info_team">
		<c:choose>
			<c:when test="${user.localeCode == portal.defaultLocaleCode}">
				${user.teamName}
				<a target="mainFrame" href="<c:url value='/portal/main/listUserMain.do'/>?rightFrameUrl=/support/profile/getProfile.do" title="${user.userName}">
					<span class="info_name">
						${user.userName}
					</span>
				</a>
				<c:choose>
                    <c:when test="${!empty user.jobDutyName && user.jobDutyName != ''}">
                       ${user.jobDutyName}
                    </c:when>                        
                    <c:otherwise>
                       ${user.jobTitleName}
                    </c:otherwise>
                </c:choose>
			</c:when>
			<c:otherwise>
				${user.teamEnglishName}
				<a target="mainFrame" href="<c:url value='/portal/main/listUserMain.do'/>?rightFrameUrl=/support/profile/getProfile.do" title="${user.userEnglishName}">
					<span class="info_name">
						${user.userEnglishName}
					</span>
				</a>
				<c:choose>
                    <c:when test="${!empty user.jobDutyName && user.jobDutyName != ''}">
                       ${user.jobDutyName}
                    </c:when>                        
                    <c:otherwise>
                      ${user.jobTitleEnglishName}
                    </c:otherwise>
                </c:choose>				
			</c:otherwise>
		</c:choose>
		</span>
		<span class="info_btn"><a href="#a" onclick="javascript: logout();" title="<ikep4j:message pre="${prefix}" key="img.logout" />"><span><ikep4j:message pre="${prefix}" key="img.logout" /></span></a></span> 
	</div>
	<!--//personInfo End-->
	<div class="clear"></div>
</div>
<!--//topInfo End-->

<!--topMenu Start-->
<div id="topMenu">
	<h1 class="none"><ikep4j:message pre="${prefix}" key="title2" /></h1>	
	<ul>
		<c:set var="parentMenuId" value=""/>
		<c:set var="childCount" value="0"/>			
		
		<c:if test="${empty portalMenuList}">
			<li></li>
		</c:if>
		<c:if test="${!empty portalMenuList}">
		<c:forEach var="menu" items="${portalMenuList}" varStatus="status">
			
		<c:if test="${menu.depthLevel == 1}">
		
		<c:if test="${!status.first && menu.menuId != parentMenuId}">
		<c:if test="${childCount != 0}">
			</ul>
		</c:if>
		</li>
		</c:if>
		
		<c:set var="parentMenuId" value="${menu.menuId}"/>
		<c:set var="childCount" value="0"/>
		<li>
			<a class="${menu.iconId}" 
				<c:choose>
				<c:when test="${menu.menuType == 'ITEM'}">
				<c:if test="${menu.target == 'INNER'}">
					<c:choose>
					    <c:when test="${menu.menuName == 'My Home'}">
					        target="_top"
					    </c:when>
					    <c:otherwise>
					        target="mainFrame"
					    </c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${menu.target == 'WINDOW'}">
				target="_blank"
				</c:if>
				
				<c:if test="${menu.urlType == 'URL'}">
				href="<c:url value='${ikep4j:urlConverter(menu.url, user)}'/>"
				</c:if>
				<c:if test="${menu.urlType == 'JAVASCRIPT'}">
				onclick="${ikep4j:urlConverter(menu.url, user)}"
				</c:if>
				</c:when>
				<c:otherwise>
				href="#" 
				onclick="return false;"
				</c:otherwise>
				</c:choose> 
				>
				<span><c:out value="${menu.menuName}"/></span>
			</a>
		</c:if>
	
		<c:if test="${menu.depthLevel == 2 && menu.parentMenuId == parentMenuId}">
		
		<c:if test="${childCount == 0}">
			<ul>
		</c:if>
		
		<c:set var="childCount" value="${childCount+1}"/>
		
				<li>
					<a 
						<c:choose>
						<c:when test="${menu.menuType == 'ITEM'}">
						<c:if test="${menu.target == 'INNER'}">
						target="mainFrame"
						</c:if>
						<c:if test="${menu.target == 'WINDOW'}">
						target="_blank"
						</c:if>
						
						<c:choose>
						<c:when test="${menu.urlType == 'URL' && !empty menu.url}">
						href="<c:url value='${ikep4j:urlConverter(menu.url, user)}'/>"
						</c:when>
						<c:when test="${menu.urlType == 'JAVASCRIPT' && !empty menu.url}">
						href="#" onclick="${ikep4j:urlConverter(menu.url, user)}"
						</c:when>
						<c:otherwise>
						href="#" 
						onclick="return false;"
						</c:otherwise>
						</c:choose> 
						</c:when>
						<c:otherwise>
						href="#" 
						onclick="return false;"
						</c:otherwise>
						</c:choose> 
					>
						<c:out value="${menu.menuName}"/>
					</a>
				</li>
		
		</c:if>
		
		<c:if test="${status.last}">
		<c:if test="${childCount != 0}">
			</ul>
		</c:if>
		</li>
		</c:if>
		
		</c:forEach>
		</c:if>
	</ul>
</div>
<!--//topMenu End-->

<!--blockHeaderSub Start-->
<div id="blockHeaderSub">
	<h1 class="none"><ikep4j:message pre="${prefix}" key="title3" /></h1>
	<div id="divQuickMenuLeft" class="quick_l">
		<div class="quick_move_l">
			<a href="#a"><span>left</span></a> <!--더이상 이동할 수 없는 경우는 다음과 같이 class 삽입____<a href="#a" class="disabled"><span>left</span></a>-->
		</div>
		<div class="wrap">
			<ul>
				<c:if test="${empty portalQuickMenuList}">
					<li></li>
				</c:if>
				<c:if test="${!empty portalQuickMenuList}">
				<c:forEach var="quickMenu" items="${portalQuickMenuList}" varStatus="status">
				<li id="quick_${quickMenu.quickMenuId}">
					<a 
						<c:if test="${quickMenu.target == 'INNER'}">
						target="mainFrame"
						</c:if>
						<c:if test="${quickMenu.target == 'WINDOW'}">
						target="_blank"
						</c:if>
						
						class="${quickMenu.iconId}"
						
						<c:choose>
						<c:when test="${quickMenu.moreUrlType == 'URL' && !empty quickMenu.moreUrl}">
						href="<c:url value='${ikep4j:urlConverter(quickMenu.moreUrl, user)}'/>"
						</c:when>
						<c:when test="${quickMenu.moreUrlType == 'JAVASCRIPT' && !empty quickMenu.moreUrl}">
						href="#" onclick="${ikep4j:urlConverter(quickMenu.moreUrl, user)}"
						</c:when>
						<c:otherwise>
						href="#" onclick="return false;"
						</c:otherwise>
						</c:choose>
						
						title="<c:out value="${quickMenu.quickMenuName}"/>"
					>
						<span><c:out value="${quickMenu.quickMenuName}"/></span>
					</a>
					<c:if test="${quickMenu.count == 1}">
					<div class="quick_num"><a href="#a"><span>0</span></a></div>
					</c:if>
				</li>
				</c:forEach>
				</c:if>
			</ul>
		</div>
		<div class="quick_move_r">
			<a href="#a"><span>right</span></a> <!--더이상 이동할 수 없는 경우는 다음과 같이 class 삽입____<a href="#a" class="disabled"><span>right</span></a>-->
		</div>					
		<div class="quickset">
			<a id="btnQuickmenuSetting" href="#" title="<ikep4j:message pre="${prefix}" key="configQuick" />" onclick="return false;"><span>setting</span></a>
		</div>
	</div>
	
	<!--Quick Link
	<div class="quick_c">
		<div class="quicklink">
			<ul>
				<li class="quicklink_eis"><a href="#a" onclick="goEIS();"><span>BW</span></a></li>
				<li class="quicklink_news"><a href="#a" onclick="goSabo();"><span>경쟁사실적</span></a></li>
			</ul>
		</div>
	</div>
	-->
	
	<!--//Quick Link-->
	<div class="quick_r">
		<div class="quicktxt">
			<ul>
				<li class="quicktxt_facebook"><a href="http://www.facebook.com/themoorim?ref=tn_tnmn" target="_blank" title="facebook"><span>facebook</span></a></li>
				<c:if test="${CommonConstant.PACKAGE_VERSION != CommonConstant.IKEP_VERSION_BASIC}">
				<li class="quicktxt_personal"><a href="#a" onclick="showPersonalBox('personal');" title="<ikep4j:message pre="${prefix}" key="img.personal" />"><span><ikep4j:message pre="${prefix}" key="img.personal" /></span></a></li>
				</c:if>
				<li class="quicktxt_recent"><a href="#a" onclick="showPersonalBox('recent');" title="<ikep4j:message pre="${prefix}" key="img.recent" />"><span><ikep4j:message pre="${prefix}" key="img.recent" /></span></a></li>
				<li class="quicktxt_favorite"><a href="#a" onclick="showPersonalBox('favorite');" title="<ikep4j:message pre="${prefix}" key="img.favorite" />"><span><ikep4j:message pre="${prefix}" key="img.favorite" /></span></a></li>	
			</ul>
			<div class="quicktxt_personalBox none" id="personalBox">
				<div class="quicktxt_menuBox">
					<div class="quicktxt_tit_personal" id="personalBoxTitle"><span>Personal</span><a href="#a" id="personalBoxClose"><img src="<c:url value="/base/images/icon/ic_close_layer.gif"/>" alt="" /></a></div>
					<div id="personalBoxContent" style="width:140px;"></div>
				</div>
			</div>			
		</div>
		
		<!--headerSearch Start-->
		<div class="headerSearch" style="display:none;">
			<div class="headerSearch_sel">
				<h2 class="none"><ikep4j:message pre="${prefix}" key="subTitle" /></h2>
				
				<c:choose>
					<c:when test="${CommonConstant.PACKAGE_VERSION eq CommonConstant.IKEP_VERSION_BASIC  }">
					<!-- 단일항목 -->
					<span class="sel_con" ><ikep4j:message pre="${prefix}" key="user" /></span>
					</c:when>
					<c:otherwise>
					<!-- 컴보박스 검색타입  N개일때-->
					<!--<a class="sel_con" href="#" id="selCombo"><div id="selText"><ikep4j:message pre="${prefix}" key="user" /></div><span>select</span></a>-->
					</c:otherwise>
				</c:choose>
				<div id="selComboBox" class="headerSearch_layer none">
					<ul>
						<li><a href="#a" onclick="setSearchKey('<ikep4j:message pre="${prefix}" key="user" />','title');"><ikep4j:message pre="${prefix}" key="user" /></a></li>
						<cif test="${CommonConstant.PACKAGE_VERSION != CommonConstant.IKEP_VERSION_BASIC  }">
						<li><a href="#a" onclick="setSearchKey('<ikep4j:message pre="${prefix}" key="team" />','team');"><ikep4j:message pre="${prefix}" key="team" /></a></li>
						<li><a href="#a" onclick="setSearchKey('<ikep4j:message pre="${prefix}" key="tag" />','tag');"><ikep4j:message pre="${prefix}" key="tag" /></a></li>
						</cif>
					</ul>
				</div>
			</div>
			<form id="headerSearchForm" method="post" target="mainFrame" action="<c:url value='/portal/main/listUserMain.do'/>" onsubmit="return false;">
				<input name="headerSearchColumn" type="hidden" value="title" />
				<input name="tagName" type="hidden" value="" />
				<input name="listType" type="hidden" value="Search" />
				<input id="headerSearchWord" name="headerSearchWord" title="<ikep4j:message pre="${prefix}" key="img.searchWindow" />" type="text" />
				<a class="sel_btn" href="#a" onclick="javascript: headerSearch();"><span><ikep4j:message pre="${prefix}" key="img.search" /></span></a>
			</form>
			<div class="clear"></div>
		</div>
		<!--//headerSearch End-->
	</div>
</div>
<!--//blockHeaderSub End-->
<div class="none" id="mainPopMessageLayer" title="Message">
	<div id="mainPopMessageView" > </div>
</div>
