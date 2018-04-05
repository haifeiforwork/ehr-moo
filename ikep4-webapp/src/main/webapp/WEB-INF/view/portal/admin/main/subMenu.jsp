<%--
=====================================================
* 기능 설명 : 다이나믹 타일즈2 Header Page
* 작성자 : 주길재
=====================================================
--%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>
<c:set var="prefixUi" value="ui.portal.admin.main.subMenu"/>

<script type="text/javascript">
//<![CDATA[
(function($) {
	
	$(document).ready(function() {	
		// left menu setting
		iKEP.setLeftMenu();
		
		var domain = document.domain;
		
		var option = {
			domain: domain,
	   		expires: 365 * 24 * 60 * 60,
	   		path: '/'
        }
		
		<c:if test="${cookieFlag == 'Y'}">
			$jq.cookie("IKEP_PORTAL_ADMIN_MENU_SAVED1", "open", option);
			$jq.cookie("IKEP_PORTAL_ADMIN_MENU_SAVED2", "close", option);
			$jq.cookie("IKEP_PORTAL_ADMIN_MENU_SAVED3", "close", option);
			$jq.cookie("IKEP_PORTAL_ADMIN_MENU_SAVED4", "close", option);
			$jq.cookie("IKEP_PORTAL_ADMIN_MENU_SAVED5", "close", option);
		</c:if>
		
		if($jq.cookie("IKEP_PORTAL_ADMIN_MENU_SAVED1") == "close") {
			$jq("#IKEP_PORTAL_ADMIN_MENU1").hide();
			$jq("#IKEP_PORTAL_ADMIN_MENU1").parent().removeClass("opened");
		}
		
		if($jq.cookie("IKEP_PORTAL_ADMIN_MENU_SAVED2") == "close") {
			$jq("#IKEP_PORTAL_ADMIN_MENU2").hide();
			$jq("#IKEP_PORTAL_ADMIN_MENU2").parent().removeClass("opened");
		}
		
		if($jq.cookie("IKEP_PORTAL_ADMIN_MENU_SAVED3") == "close") {
			$jq("#IKEP_PORTAL_ADMIN_MENU3").hide();
			$jq("#IKEP_PORTAL_ADMIN_MENU3").parent().removeClass("opened");
		}
		
		if($jq.cookie("IKEP_PORTAL_ADMIN_MENU_SAVED4") == "close") {
			$jq("#IKEP_PORTAL_ADMIN_MENU4").hide();
			$jq("#IKEP_PORTAL_ADMIN_MENU4").parent().removeClass("opened");
		}
		
		if($jq.cookie("IKEP_PORTAL_ADMIN_MENU_SAVED5") == "close") {
			$jq("#IKEP_PORTAL_ADMIN_MENU5").hide();
			$jq("#IKEP_PORTAL_ADMIN_MENU5").parent().removeClass("opened");
		}
		
	});
})(jQuery);

function menuSetCookie(cookieName, menuId) {
	
	var domain = document.domain;
	
	var option = {
   		domain: domain,
   		expires: 365,
   		path: '/'
    }
	
	if($jq(menuId).css("display") == 'none') {
		$jq.cookie(cookieName, "open", option);
	} else {
		$jq.cookie(cookieName, "close", option);
	}
}
//]]>
</script>

<!--leftMenu Start-->
<h1 class="none"><ikep4j:message pre="${prefixUi}" key="noneTitle" /></h1>	
<h2 class="han"><ikep4j:message pre="${prefixUi}" key="title" /></h2>
<div class="left_fixed">
	<ul>
		<li class="liFirst opened">
			<a href="#" onclick="menuSetCookie('IKEP_PORTAL_ADMIN_MENU_SAVED1', '#IKEP_PORTAL_ADMIN_MENU1');"><ikep4j:message pre="${prefixUi}" key="menu1" /></a>
			<ul id="IKEP_PORTAL_ADMIN_MENU1">
				<li class="no_child">
					<a id="loginImageLinkOfLeft" href="<c:url value='/portal/admin/screen/loginImage/updateLoginImageForm.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu11" />
					</a>
				</li>
				<li class="no_child">
					<a id="logoImageLinkOfLeft" href="<c:url value='/portal/admin/screen/logoImage/updateLogoImageForm.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu12" />
					</a>
				</li>
				<li class="no_child">
					<a id="menuLinkOfLeft" href="<c:url value='/admin/screen/menu/portalMenuMain.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu13" />
					</a>
				</li>
				<li class="no_child">
					<a id="quickMenuLinkOfLeft" href="<c:url value='/admin/screen/quickmenu/portalQuickMenuMain.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu14" />
					</a>
				</li>
				<li class="no_child">
					<a id="systemLinkOfLeft" href="<c:url value='/admin/screen/system/portalSystemMain.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu15" />
					</a>
				</li>
				<li class="no_child">
					<a id="systemUrlLinkOfLeft" href="<c:url value='/admin/screen/systemurl/portalSystemUrlList.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu16" />
					</a>
				</li>
				<li class="no_child liLast">
					<a id="portalLinkOfLeft" href="<c:url value='/portal/admin/screen/portal/listPortal.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu17" />
					</a>
				</li>
			</ul>					
		</li>
		
		<li class="opened">
			<a href="#" onclick="menuSetCookie('IKEP_PORTAL_ADMIN_MENU_SAVED2','#IKEP_PORTAL_ADMIN_MENU2'); return false;"><ikep4j:message pre="${prefixUi}" key="menu2" /></a>
			<ul id="IKEP_PORTAL_ADMIN_MENU2">
				<li class="no_child">
					<a id="userLinkOfLeft" href="<c:url value='/portal/admin/member/user/getList.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu21" />
					</a>
				</li>
				<li class="no_child">
					<a id="groupLinkOfLeft" href="<c:url value='/portal/admin/group/group/getList.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu22" />
					</a>
				</li>
				<li class="no_child">
					<a id="roleLinkOfLeft" href="<c:url value='/portal/admin/role/role/list.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu23" />
					</a>
				</li>
				<li class="no_child liLast">
					<a id="systemAdminLinkOfLeft" href="<c:url value='/portal/admin/screen/systemAdmin/readSystemAdmin.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu24" />
					</a>
				</li>
			</ul>					
		</li>
		
		<li class="opened">
			<a href="#" onclick="menuSetCookie('IKEP_PORTAL_ADMIN_MENU_SAVED3', '#IKEP_PORTAL_ADMIN_MENU3'); return false;"><ikep4j:message pre="${prefixUi}" key="menu3" /></a>
			<ul id="IKEP_PORTAL_ADMIN_MENU3">
			<%--
				<li class="no_child">
					<a id="themeLinkOfLeft" href="<c:url value='/portal/admin/screen/theme/listTheme.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu31" />
					</a>
				</li>
			--%>
				<li class="no_child">
					<a id="portletMainPageUpdateOfLeft" href="<c:url value='/portal/admin/screen/page/readPageMain.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu32" />
					</a>
				</li>
				<li class="no_child">
					<a id="portletGroupLinkOfLeft" href="<c:url value='/portal/admin/screen/portletCategory/createPortletCategoryForm.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu33" />
					</a>
				</li>
				<li class="no_child">
					<a id="portletMangerOfLeft" href="<c:url value='/portal/admin/screen/portlet/listPortlet.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu34" />
					</a>
				</li>
				<li class="no_child">
					<a id="portletPageManageOfLeft" href="<c:url value='/portal/admin/screen/page/listPage.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu35" />
					</a>
				</li>
				<!-- 포틀릿 레이아웃은 초기 등록한 3Tile 까지만 사용함, 어드민에서 Tiles는 삭제되면 안됨.-->
				<!--  
				<li class="no_child liLast">
					<a id="portletLayoutLinkOfLeft" href="<c:url value='/portal/admin/screen/layout/createLayoutForm.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu36" />
					</a>
				</li>
				-->
				<li class="no_child liLast">
					<a id="popupManageLinkOfLeft" href="<c:url value='/portal/admin/screen/popup/listPopup.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu37" />
					</a>
				</li>
			</ul>					
		</li>
		
		<li class="opened">
			<a href="#" onclick="menuSetCookie('IKEP_PORTAL_ADMIN_MENU_SAVED4', '#IKEP_PORTAL_ADMIN_MENU4'); return false;"><ikep4j:message pre="${prefixUi}" key="menu4" /></a>
			<ul id="IKEP_PORTAL_ADMIN_MENU4">
				<li class="no_child">
					<a id="groupTypeManageOfLeft" href="<c:url value='/portal/admin/group/grouptype/getList.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu41" />
					</a>
				</li>
				<li class="no_child">
					<a id="roleTypeManageOfLeft" href="<c:url value='/portal/admin/role/roletype/getList.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu42" />
					</a>
				</li>
				<li class="no_child">
					<a id="itemTypeManageOfLeft" href="<c:url value='/portal/admin/code/itemtype/getList.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu43" />
					</a>
				</li>
				<li class="no_child">
					<a id="operationManageOfLeft" href="<c:url value='/portal/admin/code/operationcode/getList.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu44" />
					</a>
				</li>
				<li class="no_child">
					<a id="classManageOfLeft" href="<c:url value='/portal/admin/code/classcode/getList.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu45" />
					</a>
				</li>
				<li class="no_child">
					<a id="jobclassOfLeft" href="<c:url value='/portal/admin/code/jobclass/getList.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu49" />
					</a>
				</li>
                <li class="no_child">
                    <a id="companycodeOfLeft" href="<c:url value='/portal/admin/code/companycode/getList.do'/>">
                        <ikep4j:message pre="${prefixUi}" key="subMenu57" />
                    </a>
                </li>
				<li class="no_child">
                    <a id="workPlaceOfLeft" href="<c:url value='/portal/admin/code/workplace/getList.do'/>">
                        <ikep4j:message pre="${prefixUi}" key="subMenu56" />
                    </a>
                </li>
                <li class="no_child">
                    <a id="jobdutyOfLeft" href="<c:url value='/portal/admin/code/jobduty/getList.do'/>">
                        <ikep4j:message pre="${prefixUi}" key="subMenu46" />
                    </a>
                </li>
				<li class="no_child">
					<a id="callManageOfLeft" href="<c:url value='/portal/admin/code/jobtitle/getList.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu48" />
					</a>
				</li>
			
				<!-- li class="no_child">
					<a id="jobrankOfLeft" href="<c:url value='/portal/admin/code/jobrank/getList.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu48" />
					</a>
				</li>
				<li class="no_child">
					<a id="jobclassOfLeft" href="<c:url value='/portal/admin/code/jobclass/getList.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu49" />
					</a>
				</li-->
				
				<li class="no_child">
					<a id="timezoneOfLeft" href="<c:url value='/portal/admin/code/timezone/getList.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu51" />
					</a>
				</li>
				<li class="no_child liLast">
					<a id="localecodeOfLeft" href="<c:url value='/portal/admin/code/localecode/getList.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu52" />
					</a>
				</li>
				<li class="no_child liLast">
					<a id="nationOfLeft" href="<c:url value='/portal/admin/code/nation/getList.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu53" />
					</a>
				</li>
				<li class="no_child liLast">
					<a id="categoryOfLeft" href="<c:url value='/portal/admin/code/category/editCategory.do'/>">
						공용그룹 카테고리 관리
					</a>
				</li>
				<li class="no_child liLast">
					<a id="financeOfLeft" href="<c:url value='/portal/admin/code/finance/editFinance.do'/>">
						재무/손익 정보 기간 관리
					</a>
				</li>
			</ul>					
		</li>
		<c:if test="${CommonConstant.PACKAGE_VERSION != CommonConstant.IKEP_VERSION_BASIC}">
		<!-- Batch Management -->
		<li class="opened">
			<a href="#" onclick="menuSetCookie('IKEP_PORTAL_ADMIN_MENU_SAVED5', '#IKEP_PORTAL_ADMIN_MENU5'); return false;"><ikep4j:message pre="${prefixUi}" key="menu5" /></a>
			<ul id="IKEP_PORTAL_ADMIN_MENU5">
				<li class="no_child">
					<a id="batchTriggerLinkOfLeft" href="<c:url value='/portal/admin/batch/listTrigger.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu54" />
					</a>
				</li>
				<li class="no_child">
					<a id="batchLogLinkOfLeft" href="<c:url value='/portal/admin/batch/listBatchLog.do'/>">
						<ikep4j:message pre="${prefixUi}" key="subMenu55" />
					</a>
				</li>
			</ul>					
		</li>
		</c:if>
	</ul>
</div>	
<!--//leftMenu End-->