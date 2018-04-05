<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 


<c:set var="preHeader"  value="ui.portal.myspace.header" /> 
<c:set var="preList"    value="ui.portal.myspace.list" />
<c:set var="preDetail"  value="ui.portal.myspace.detail" />
<c:set var="preButton"  value="ui.portal.myspace.button" /> 
<c:set var="preMessage" value="ui.portal.myspace.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 


<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript" src="<c:url value="/base/js/units/support/favorite/favorite.js"/>"></script>

<script type="text/javascript" language="javascript">

(function($) {
	
	clickMyMenu = function(obj) {
		
		$chk = $jq(obj).parent().hasClass("opened");

		if($chk) {
			$jq(obj).parent().removeClass()
			$jq(obj).parent().children("ul").hide();
		}
		else {
			$jq(obj).parent().addClass("opened")
			$jq(obj).parent().children("ul").show();
		}
		
	}
	
	
	$jq(document).ready(function() { 
		
		$("#btnQuickmenuSetting").click(function(event) {	// quick menu setting button event
			var $menuContainer = $("#divQuickMenuLeft");
			var position = $menuContainer.offset();
			var width = 650;
			new iKEP.Dialog({
				title : "Quick Menu Setting",
				id : "quickMenuSetting",
				url : "<c:url value='/portal/main/quickmenuSetting.do'/>",
				width : width,
				height : 550,
				position : [position.left, position.top+$menuContainer.height()+2],
				callback : function(result) {
					// 메뉴 목록을 새로 rendering 해야함.
					location.reload();	// 우선 reload 처리
				},
				resizable :false,
				titleClass : "ui-dialog-titlebar ui-widget-header-quickmenu ui-corner-all ui-helper-clearfix"
			});
		});
		
		<c:forEach var="quickMenu" items="${portalQuickMenuList}" varStatus="status">
    	<c:if test="${quickMenu.count == 1}">
    		//setTimeout(get<c:out value="${quickMenu.quickMenuId}"/>Count, 5000);
    		get<c:out value="${quickMenu.quickMenuId}"/>Count();    		
    	</c:if>
    	</c:forEach>
		
	});
	
	mailCountRefresh = function (menuId){
		getQuickMenuCount(menuId);
		//get100000709157Count();//메일count refresh
	};
	
	HRCountRefresh = function (menuId){
		getQuickMenuCount(menuId);
		//if("${user.jobDutyCode}" == "12" || "${user.jobDutyCode}" == "18"){
			//get100000788076Count();//HR결제 count refresh
		//}
	};
	
	mailHRCountRefresh = function (menuId){
		get100000709157Count();//메일count refresh
		if("${user.jobDutyCode}" == "12" || "${user.jobDutyCode}" == "18"){
			get100000788076Count();//HR결제 count refresh
		}
	};
	
	cardCountRefresh = function (menuId){
	getQuickMenuCount(menuId);
	//if("${user.jobDutyCode}" == "12" || "${user.jobDutyCode}" == "18"){
		//get100000788076Count();//HR결제 count refresh
	//}
	};
	
})(jQuery);  

function getQuickMenuCount(menuId) {
	var url = "";
	if(menuId == "100000788076"){
		url = "<c:url value='${ikep4j:urlConverter("/portal/main/getHRapprCount.do", user)}'/>";
	}else if(menuId == "100000709157"){
		url = "<c:url value='${ikep4j:urlConverter("/portal/mail/getMailCount.do", user)}'/>";
	}else{
		url = "<c:url value='${ikep4j:urlConverter("/portal/main/getStatementCount.do", user)}'/>";
	}

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
			$jq("#quick_"+menuId+" > .quick_num > a > span").html(count);
		},
		error : function(event, request, settings) { 
			//$jq("#quick_${quickMenu.quickMenuId} > .quick_num").remove();
			//$jq("#quick_${quickMenu.quickMenuId}").append("<div class='quick_num'><a href='#' onclick='return false;'><span>0</span></a></div>");
			$jq("#quick_"+menuId+" > .quick_num > a > span").html("0");
		}
	});
	}

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

	
</script>
 

<div class="perInfo" style="padding-top:35px;">
	<table summary="<ikep4j:message pre='${preList}' key='mySpace' />">
		<caption></caption>
		<tbody>
			<tr>
				<td width="100">
					<img src="<c:url value='/base/images/common/Quick_90_25.png'/>" alt="image" width="90px" height="25px" />
				</td>
			</tr>
		</tbody>
	</table>
</div>

<div class="conList">
<div id="divQuickMenuLeft" class="quick_l">
		<div class="wrap" >
			<ul>
				<c:if test="${empty portalQuickMenuList}">
					<li></li>
				</c:if>
				<c:if test="${!empty portalQuickMenuList}">
				<c:forEach var="quickMenu" items="${portalQuickMenuList}" varStatus="status">
						<li id="quick_${quickMenu.quickMenuId}">
						<a 
						<c:choose>
							<c:when test="${quickMenu.moreUrlType == 'JAVASCRIPT' }">
							</c:when>
							<c:when test="${quickMenu.target == 'INNER'}">
							target="mainFrame"
							</c:when>
							<c:when test="${quickMenu.target == 'WINDOW'}">
							target="_blank"
							</c:when>
						</c:choose>
						
						class="${quickMenu.iconId}"
						
						<c:choose>
						<c:when test="${quickMenu.moreUrlType == 'URL' && !empty quickMenu.moreUrl}">
						href="<c:url value='${ikep4j:urlConverter(quickMenu.moreUrl, user)}'/>" 
							<c:if test="${quickMenu.count == 1}">
							onclick="<c:if test='${quickMenu.quickMenuName == "메일"}'>mailCountRefresh(${quickMenu.quickMenuId});</c:if><c:if test='${quickMenu.quickMenuName == "HR결재"}'>HRCountRefresh(${quickMenu.quickMenuId});</c:if><c:if test='${quickMenu.quickMenuName == "전표결재"}'>cardCountRefresh(${quickMenu.quickMenuId});</c:if>"
							</c:if>
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
		
		<div class="quickset" style="float:right;padding-right:10px;">
			<a id="btnQuickmenuSetting" href="#" title="설정" onclick="return false;"><span>setting</span></a>
		</div>
	</div>
</div>
<div class="btn_refresh"><a href="#a" onclick="refreshMySpace();"><span>refresh</span></a></div>
<div class="LeftContainer_bar">
	<div class="LeftContainer_bar_opened"><a href="#a" onclick="hideMySpace();"><span>closed</span></a></div>
</div>