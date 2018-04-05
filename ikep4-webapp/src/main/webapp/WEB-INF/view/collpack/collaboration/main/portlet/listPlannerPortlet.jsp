<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.collpack.collaboration.workspace.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript"> 
(function($) {
	viewItem = function(scheduleId){
		var objSplit = null;
		 if(objSplit == null  ) {
			var url = iKEP.getContextRoot() + "/lightpack/planner/calendar/viewSchedule.do";
			var options = {
				width:700,
				height:340,
				resizable:true,
//				argument : {startDate:event.start.toString(), endDate:event.end.toString()},
				callback : function(result) {
					if(result && result["action"]) {
						switch(result.action) {
							case "update" :
								parent.iKEP.goScheduleUpdate({scheduleId:scheduleId}, null);
								break;
							case "delete" :
								getTodayMySchedule('${currentDate}');
								break;
						}
					}
				}
			};
			iKEP.popupOpen(url + "?scheduleId="+scheduleId, options);
		 }
	};
	

	getTodayMySchedule = function(curDate){ 
	
		$jq("#scheduleChart").empty(); 
		    	
		$jq("#scheduleList").empty(); 
		
		var arrClass = ['','','','','','','','','','','' ,'','','','','','','','','','',''];
		var arrTime = ['08:00~08:30','08:30~09:00','09:00~09:30','09:30~10:00','10:00~10:30','10:30~11:00'
		               ,'11:00~11:30','11:30~12:00','12:00~12:30','12:30~13:00','13:00~13:30','13:30~14:00'
		               ,'14:00~14:30','14:30~15:00','15:00~15:30','15:30~16:00','16:00~16:30','16:30~17:00'
		               ,'17:00~17:30','17:30~18:00','18:00~18:30','18:30~19:00'];
		$jq.ajax({
		    url : "<c:url value='/lightpack/planner/calendar/readWorkspaceSchedule.do' />",
		    data : {'workspaceId':'${workspaceId}','startDate':curDate},  
		    type : "get",
		    success : function(result) {

		    	var startDate, endDate, scheduleId, title;
		    	var chartHtml, liHtml;
		    	
		    	liHtml = "";
		    	chartHtml = "";
		    	$jq.each(result, function(index, value){

		    		$jq.each(value, function(childindex, childvalue){
			    		if( childindex == 'startDate'){
			    			startDate = childvalue;
			    		}
			    		if( childindex == 'endDate'){
			    			endDate = childvalue;
			    		}
			    		if( childindex == 'scheduleId'){
			    			scheduleId = childvalue;
			    		}
						if( childindex == 'title'){
							title = childvalue;
			    		}
		    		});
					
					if( startDate.substring(8,12) <= '0800' && endDate.substring(8,12) >= '0830' ){
						arrClass[0] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '0830' && endDate.substring(8,12) >= '0900' ){
						arrClass[1] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '0900' && endDate.substring(8,12) >= '0930' ){
						arrClass[2] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '0930' && endDate.substring(8,12) >= '1000' ){
						arrClass[3] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1000' && endDate.substring(8,12) >= '1030' ){
						arrClass[4] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1030' && endDate.substring(8,12) >= '1100' ){
						arrClass[5] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1100' && endDate.substring(8,12) >= '1130' ){
						arrClass[6] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1130' && endDate.substring(8,12) >= '1200' ){
						arrClass[7] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1200' && endDate.substring(8,12) >= '1230' ){
						arrClass[8] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1230' && endDate.substring(8,12) >= '1300' ){
						arrClass[9] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1300' && endDate.substring(8,12) >= '1350' ){
						arrClass[10] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1330' && endDate.substring(8,12) >= '1400' ){
						arrClass[11] = "class='pr_schbar_2'";
					}	
					if( startDate.substring(8,12) <= '1400' && endDate.substring(8,12) >= '1430' ){
						arrClass[12] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1430' && endDate.substring(8,12) >= '1500' ){
						arrClass[13] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1500' && endDate.substring(8,12) >= '1530' ){
						arrClass[14] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1530' && endDate.substring(8,12) >= '1600' ){
						arrClass[15] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1600' && endDate.substring(8,12) >= '1630' ){
						arrClass[16] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1630' && endDate.substring(8,12) >= '1700' ){
						arrClass[17] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1700' && endDate.substring(8,12) >= '1730' ){
						arrClass[18] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1730' && endDate.substring(8,12) >= '1800' ){
						arrClass[19] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1800' && endDate.substring(8,12) >= '1830' ){
						arrClass[20] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1830' && endDate.substring(8,12) >= '1900' ){
						arrClass[21] = "class='pr_schbar_2'";
					}
					
					liHtml = liHtml + ("<li><span>" + startDate.substring(8,10) + ":" + startDate.substring(10,12) + " ~ " + endDate.substring(8,10) + ":" + endDate.substring(10,12) + "</span> <a href=\"#a\" onclick=viewItem(\'" + scheduleId + "\');>" + title + "</a></li>");
		    	});
		    	
		    	$.each(arrTime, function(index, value){
		    		chartHtml = chartHtml + "<td "+ arrClass[index] +"><span>"+value+"</span></td>";
		    	});

		    	$jq("#scheduleChart").html(chartHtml); 
		    	
		    	if( liHtml == '' ){
		    		liHtml = "<li><p align='center'><ikep4j:message pre='${prefix}' key='portlet.noDataPlanner'/></p></li>";
		    	}
		    	$jq("#scheduleList").html(liHtml); 

		    }
		});
		//iKEP.iFrameContentResize(); 
	};

	$jq(document).ready(function() {
	
		$jq("#portlet_r_2").empty();
		
		
		
		getTodayMySchedule('${currentDate}');

		$jq("#ui-tabs-1").empty();
		$jq("#ui-tabs-2").empty();
		$jq("#ui-tabs-3").empty();
		$jq("#ui-tabs-4").empty();
		$jq("#ui-tabs-5").empty();
		$jq("#ui-tabs-6").empty();
		$jq("#ui-tabs-7").empty();
		

		$("#divTab6").tabs({
			selected : ${weeklyNum},
			cache : true,
			ajaxOptions: {
				error: function( xhr, status, index, anchor ) {
					$( anchor.hash ).html("Couldn't load this tab. We'll try to fix this as soon as possible. If this wouldn't be a demo." );
				}
			},
			select : function(event, ui) {
				<c:forEach var="weeklyDate" items="${weeklyDate}" varStatus="status">
					if(ui.index==${status.index}){
						getTodayMySchedule('${weeklyDate}');
					}
				</c:forEach>
			},
			load : function(event, ui) {
			}
		});
		
		iKEP.iFrameContentResize(); 
				
	});
})(jQuery);
</script>	

	<div class="po_schedule mb15">
	
		<!--subTitle_1 Start-->
		<div class="subTitle_1">
			<h3>
				<c:choose>
					<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						${workspacePortletLayout.workspacePortlet.portletName}
					</c:when>
					<c:otherwise>
						${workspacePortletLayout.workspacePortlet.portletEnglishName}
					</c:otherwise>
				</c:choose>	
			</h3>
			<div class="btn_more"><a href="<c:url value='/lightpack/planner/calendar/workspaceInit.do'/>?workspaceId=${workspace.workspaceId}&workspaceName=${workspace.workspaceName}"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
		</div>
		<!--//subTitle_1 End-->
		
		<div class="sch_ar">
			<span class="sch_ar_l"></span>
			<span class="sch_ar_date">${weeklyStartDate} ~ ${weeklyEndDate}</span>
			<span class="sch_ar_r"></span>
		</div>
		<!--tab Start-->
				
		<div id="divTab6" class="iKEP_tab_menu_s">
			<ul>
				<c:forEach var="weeklyDate" items="${weeklyDate}" varStatus="status">
				<li style="width:14.4%;">
					<a href="<c:url value='/collpack/collaboration/main/listPlannerPortlet.do?workspaceId=${workspaceId}&portletLayoutId=${workspacePortletLayout.portletLayoutId}&currentDate=${weeklyDate}'/>"><ikep4j:message pre='${prefix}' key='portlet.week${status.index}'/></a>	
				</li>
				</c:forEach>
			</ul>					
		</div>		
		<!--//tab End-->
		

		<div id="tab-1"></div>
		<div id="tab-2"></div>
		<div id="tab-3"></div>
		<div id="tab-4"></div>
		<div id="tab-5"></div>
		<div id="tab-6"></div>
		<div id="tab-7"></div>
		<div class="pr_schedule_c">
			<table summary="" class="pr_schedule_time">
				<caption></caption>
				<tbody>
					<tr>
						<td class="textCenter" style="width:18.18%">09:00</td>
						<td style="width:9.09%;"></td>
						<td class="textCenter" style="width:18.18%">12:00</td>
						<td style="width:9.09%;"></td>
						<td class="textCenter" style="width:18.18%">15:00</td>
						<td style="width:9.09%;"></td>
						<td class="textCenter" style="width:18.18%">18:00</td>
					</tr>
				</tbody>
			</table>
			
			<table summary="My Schedule">
				<caption></caption>
				<thead>
					<tr>
						<th></th>
						<th></th>
						<th><img src="<c:url value='/base/images/common/bar_sch.gif'/>" alt="" /></th>
						<th></th>
						<th></th>
						<th></th>
						<th></th>
						<th></th>
						<th><img src="<c:url value='/base/images/common/bar_sch.gif'/>" alt="" /></th>
						<th></th>
						<th></th>
						<th></th>
						<th></th>
						<th></th>
						<th><img src="<c:url value='/base/images/common/bar_sch.gif'/>" alt="" /></th>
						<th></th>
						<th></th>
						<th></th>
						<th></th>
						<th></th>
						<th><img src="<c:url value='/base/images/common/bar_sch.gif'/>" alt="" /></th>
						<th></th>
					</tr>
				</thead>

				<tbody>
					<tr id="scheduleChart"></tr>
					<tr>
					</tr>					
				</tbody>
			</table>
			<ul id="scheduleList"></ul>

		</div>
		<div class="clear"></div>				
	</div>
