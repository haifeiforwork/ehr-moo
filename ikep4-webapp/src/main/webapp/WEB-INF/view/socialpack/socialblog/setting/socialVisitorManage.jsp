<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preManage" value="ui.socialpack.socialblog.management" />
<c:set var="preManageMenu" value="ui.socialpack.socialblog.management.menu" />
<c:set var="preManageDesign" value="ui.socialpack.socialblog.management.designsetting" />
<c:set var="preManageStat" value="ui.socialpack.socialblog.management.statistics" />

<c:set var="preCode"    value="ui.socialpack.socialblog.common.code" />
<c:set var="preButton"  value="ui.socialpack.socialblog.common.button" /> 
<c:set var="preMessage" value="message.socialpack.socialblog.common.socialBoardItem" />
<c:set var="preSearch"  value="ui.socialpack.socialblog.common.searchCondition" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/units/socialpack/socialblog.js"/>"></script>
<script type="text/javascript">

(function($){

	$jq(document).ready(function() {
		
		// 화면 로딩시 각각 페이지 호출 시작
		viewVisitorStaticChart('${weeklyTerm}','DAILY','');
		// 화면 로딩시 각각 페이지 호출 종료
		
		//<ikep4j:message pre='${preButton}' key='calendar' />
		$("#datetext").datepicker({
			showOn: "button",
			buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			buttonImageOnly: false,
			showOtherMonths : true,
			selectOtherMonths: true,
			hoverWeek : true,
			onSelect: function(date) {
				$("#weeklyListForm input[name=weeklyTerm]").val(date);
				$("#weeklyListForm").submit();
			}
		});

	});
	
	var visitorChartDataList, visitorChartData;
	
	viewVisitorStaticChart = function(weeklyTerm,baseDateType,dayFlag) {
		$jq.ajax({
		    url : "<c:url value='/socialpack/socialblog/setting/socialVisitorManageForChart.do' />",
		    data : {'blogOwnerId':'${socialBlog.ownerId}','weeklyTerm':weeklyTerm,'baseDateType':baseDateType,'dayFlag':dayFlag},
		    type : "get",
		    success : function(result) {
		    	$jq("#chartArea").html(result);
		    }
		});	
	};
	
	// 카테고리 관리 메뉴 조회
	getCategoryManagement = function(blogOwnerId) {
		document.location.href = "<c:url value='/socialpack/socialblog/setting/socialblogSettingHome.do?blogOwnerId=' />"+blogOwnerId ;
	};
	
	// 카테고리 관리 메뉴 조회
	getLayoutManagement = function(blogOwnerId) {
		document.location.href = "<c:url value='/socialpack/socialblog/setting/socialLayoutManage.do?blogOwnerId=' />"+blogOwnerId ;
	};
	
	// 카테고리 관리 메뉴 조회
	getBackgroundManagement = function(blogOwnerId) {
		document.location.href = "<c:url value='/socialpack/socialblog/setting/socialBackgroundManage.do?blogOwnerId=' />"+blogOwnerId ;
	};
	
	// 카테고리 관리 메뉴 조회
	getVisitorManagement = function(blogOwnerId) {
		document.location.href = "<c:url value='/socialpack/socialblog/setting/socialVisitorManage.do?blogOwnerId=' />"+blogOwnerId ;
	};
	
    
})(jQuery);

</script>

	<!--popup_title Start-->
	<div id="popup_title">
		<h1><ikep4j:message pre='${preManage}' key='title' /></h1>
		<a href="javascript:iKEP.returnPopup();"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
	</div>
	<!--//popup_title End-->

	<!--popup_contents Start-->
	<div id="popup_contents">
	
		<!-- Modal window Start -->
		<!-- 사용시class="none"으로 설정 -->
		<div class="" id="layer_p" title="<ikep4j:message pre='${preManage}' key='title' />">
		
			<div class="blog_layout">
				
				<!--leftMenu Start-->
				<div class="floatLeft blog_left">
					<h1 class="none"><ikep4j:message pre='${preManageMenu}' key='leftmenu' /></h1>
					<div class="blog_leftmenu">	
						<ul>
							<li><a onclick="getCategoryManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageMenu}' key='title' /></a>
								<ul>
									<li><a onclick="getCategoryManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageMenu}' key='category.title' /></a></li>
								</ul>
							</li>
							<li><a onclick="getLayoutManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageDesign}' key='title' /></a>
								<ul>
									<li><a onclick="getLayoutManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageDesign}' key='layout.title' /></a></li>
									<li><a onclick="getBackgroundManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageDesign}' key='background.title' /></a></li>
								</ul>
							</li>
							<li><a onclick="SocialBlog.getVisitorManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageStat}' key='title' /></a>
								<ul>
									<li class="licurrent"><a onclick="getVisitorManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageStat}' key='visitor.title' /></a></li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
				<!--//leftMenu End-->
				
				<div class="floatRight" style="width:724px;">
					<div class="blog_layout_contents">
						
						<!--blog_backSelect Start-->
						<div class="blog_layout_Stitle">
							<ul class="floatLeft">
		
								<li><span><ikep4j:message pre='${preManageStat}' key='visitor.menu1' /></span></li>
							</ul>
							<div class="clear"></div>
						</div>
						<!--//blog_backSelect End-->
						
						<!--blockDetail Start-->					
						<div class="blockDetail visit_table">
							<table summary="<ikep4j:message pre='${preManageStat}' key='visitor.menu1' />">
								<caption></caption>
		
								<thead>
									<tr>
										<th scope="col" width="25%" class="textCenter"><ikep4j:message pre='${preManageStat}' key='visitor.accrued.today' /></th>
										<th scope="col" width="25%" class="textCenter"><ikep4j:message pre='${preManageStat}' key='visitor.accrued.yesterday' /></th>
										<th scope="col" width="25%" class="textCenter"><ikep4j:message pre='${preManageStat}' key='visitor.accrued.lastWeek' /></th>
										<th scope="col" width="25%" class="textCenter"><ikep4j:message pre='${preManageStat}' key='visitor.accrued.total' /></th>
									</tr>
		
								</thead>
								<tbody>
									<tr>
										<td class="textCenter"><c:out value='${todayCount}'/> <ikep4j:message pre='${preManageStat}' key='visitor.personCount' /></td>
										<td class="textCenter"><c:out value='${yesterdayCount}'/> <ikep4j:message pre='${preManageStat}' key='visitor.personCount' /></td>
										<td class="textCenter"><c:out value='${lastWeekCount}'/> <ikep4j:message pre='${preManageStat}' key='visitor.personCount' /></td>
										<td class="textCenter"><c:out value='${totalVisitCount}'/> <ikep4j:message pre='${preManageStat}' key='visitor.personCount' /></td>
		
									</tr>
								</tbody>
							</table>
						</div>
						<!--//blockDetail End-->
						
						
						
						
						<!--blog_backSelect Start-->
						<div class="blog_layout_Stitle grayLine">
							<ul class="floatLeft">
								<li><span><ikep4j:message pre='${preManageStat}' key='visitor.menu2' /></span></li>
		
							</ul>
							<div class="clear"></div>
						</div>
						<!--//blog_backSelect End-->
						
						<div id="chartArea">
						</div>
					</div>
				</div>
				
			</div>
			
		</div>	
		<!-- //Modal window End -->
	</div>