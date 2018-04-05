<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" /> 
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
<!--
function strToDate(str) {
	return new Date(str.substring(0, 4), str.substring(4, 6)-1, str.substring(6, 8), str.substring(8, 10), str.substring(10, 12));
}

(function($) {
	
	var myWorkspaceUrl = iKEP.getContextRoot() + "/collaboration/workspace/myWorkspace.do?userId=";

	var myWorkspaceIds ="";
	getWorkspaceInfo = function(targetUserId) {
		$.getJSON(myWorkspaceUrl + targetUserId, function(data) {
			if(data.length > 0) {
				$.each(data, function(idx, item) {
					myWorkspaceIds=myWorkspaceIds+","+item.workspaceId;
				});
			}
		});
	}
	
	popupMySchedule = function(scheduleId, startDate, endDate) {
		//${authenticationURL}
		var options = {
			width:700,
			height:320,
			resizable:true,
			argument : {startDate:strToDate(startDate), endDate:strToDate(endDate)},
			callback : function(result) {
				if(result && result["action"]) {
					switch(result.action) {
						case "update" :
							iKEP.goScheduleUpdate({scheduleId:scheduleId}, parent);
							break;
						case "delete" :
							getMySchedule();	// profileHome.jsp
							break;
					}
				}
			}
		};
		iKEP.popupOpen("<c:url value='/lightpack/planner/calendar/viewSchedule.do?scheduleId='/>"+scheduleId, options);
	}; 
	
	getTodayMySchedule = function(){ 
		var loginUserId='${user.userId}';
		var arrClass = ['','','','','','','','','','','','','','','','','' ,'','','','','','','','','','',''];
		var arrTime = ['05:00~05:30','05:30~06:00','06:00~06:30','06:30~07:00','07:00~07:30','07:30~08:00','08:00~08:30','08:30~09:00','09:00~09:30','09:30~10:00','10:00~10:30','10:30~11:00'
		               ,'11:00~11:30','11:30~12:00','12:00~12:30','12:30~13:00','13:00~13:30','13:30~14:00'
		               ,'14:00~14:30','14:30~15:00','15:00~15:30','15:30~16:00','16:00~16:30','16:30~17:00'
		               ,'17:00~17:30','17:30~18:00','18:00~18:30','18:30~19:00'];
		$.ajax({
		    url : "<c:url value='/lightpack/planner/calendar/readMySchedule.do'/>" ,
		    data : {'userId':'${targetUserId}'},  
		    type : "get",
		    success : function(result) {
		    	var startDate, endDate, scheduleId, title, schedulePublic, participantId,registerId,color, workspaceId;
		    	var chartHtml, liHtml;
		    	
		    	liHtml = "";
		    	chartHtml = "";
		    	$.each(result, function(index, value){
		    		$.each(value, function(childindex, childvalue){
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
						if( childindex == 'schedulePublic'){
							schedulePublic = childvalue;
			    		}
						if( childindex == 'participantId'){
							participantId = childvalue;
			    		}
						if( childindex == 'registerId'){
							registerId = childvalue;
			    		}
						if( childindex == 'workspaceId'){
							workspaceId = childvalue;
			    		}
						if( childindex == 'color'){
							color = childvalue;
			    		}

		    		});
		    		if( startDate.substring(8,12) <= '0500' && endDate.substring(8,12) >= '0530' ){
                        arrClass[0] = "class='pr_schbar_2'";
                    }
                    if( startDate.substring(8,12) <= '0530' && endDate.substring(8,12) >= '0600' ){
                        arrClass[1] = "class='pr_schbar_2'";
                    }
		    	    if( startDate.substring(8,12) <= '0600' && endDate.substring(8,12) >= '0630' ){
                        arrClass[2] = "class='pr_schbar_2'";
                    }
                    if( startDate.substring(8,12) <= '0630' && endDate.substring(8,12) >= '0700' ){
                        arrClass[3] = "class='pr_schbar_2'";
                    }
                    if( startDate.substring(8,12) <= '0700' && endDate.substring(8,12) >= '0730' ){
                        arrClass[4] = "class='pr_schbar_2'";
                    }		    		
		    		if( startDate.substring(8,12) <= '0730' && endDate.substring(8,12) >= '0800' ){
                        arrClass[5] = "class='pr_schbar_2'";
                    }					
					if( startDate.substring(8,12) <= '0800' && endDate.substring(8,12) >= '0830' ){
						arrClass[6] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '0830' && endDate.substring(8,12) >= '0900' ){
						arrClass[7] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '0900' && endDate.substring(8,12) >= '0930' ){
						arrClass[8] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '0930' && endDate.substring(8,12) >= '1000' ){
						arrClass[9] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1000' && endDate.substring(8,12) >= '1030' ){
						arrClass[10] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1030' && endDate.substring(8,12) >= '1100' ){
						arrClass[11] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1100' && endDate.substring(8,12) >= '1130' ){
						arrClass[12] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1130' && endDate.substring(8,12) >= '1200' ){
						arrClass[13] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1200' && endDate.substring(8,12) >= '1230' ){
						arrClass[14] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1230' && endDate.substring(8,12) >= '1300' ){
						arrClass[15] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1300' && endDate.substring(8,12) >= '1350' ){
						arrClass[16] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1330' && endDate.substring(8,12) >= '1400' ){
						arrClass[17] = "class='pr_schbar_2'";
					}	
					if( startDate.substring(8,12) <= '1400' && endDate.substring(8,12) >= '1430' ){
						arrClass[18] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1430' && endDate.substring(8,12) >= '1500' ){
						arrClass[19] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1500' && endDate.substring(8,12) >= '1530' ){
						arrClass[20] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1530' && endDate.substring(8,12) >= '1600' ){
						arrClass[21] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1600' && endDate.substring(8,12) >= '1630' ){
						arrClass[22] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1630' && endDate.substring(8,12) >= '1700' ){
						arrClass[23] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1700' && endDate.substring(8,12) >= '1730' ){
						arrClass[24] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1730' && endDate.substring(8,12) >= '1800' ){
						arrClass[25] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1800' && endDate.substring(8,12) >= '1830' ){
						arrClass[26] = "class='pr_schbar_2'";
					}
					if( startDate.substring(8,12) <= '1830' && endDate.substring(8,12) >= '1900' ){
						arrClass[27] = "class='pr_schbar_2'";
					}

					if((schedulePublic==0)||(loginUserId==registerId)||(participantId.indexOf(loginUserId)>-1)||(myWorkspaceIds!="" && workspaceId!="" && myWorkspaceIds.indexOf(workspaceId)>-1)){ //class="cal_color_02 cal_color_box" )
					//alert("schedulePublic:"+schedulePublic+"\n"+"participantId:"+participantId+"\n"+"myWorkspaceIds:"+myWorkspaceIds+"\n"+"workspaceId:"+workspaceId+"\n");
						liHtml += "<li><span>" + startDate.substring(8,10) + ":" + startDate.substring(10,12) + " ~ " + endDate.substring(8,10) + ":" + endDate.substring(10,12) + "</span> <span class=\"cal_color_box "+ color +"\"></span><a href=\"#a\" onclick=\"popupMySchedule('" + scheduleId + "', '" + value.startDate + "', '" + value.endDate + "')\">" + title + "</a></li>";
					}else{
						liHtml += "<li><span>" + startDate.substring(8,10) + ":" + startDate.substring(10,12) + " ~ " + endDate.substring(8,10) + ":" + endDate.substring(10,12) + "</span> <span class=\"cal_color_box "+ color +"\"></span><ikep4j:message pre='${preMsgProfile}' key='schedule.notOpen'/></li>";
					}
					
		    	});
		    	
		    	$.each(arrTime, function(index, value){
		    		chartHtml = chartHtml + "<td "+ arrClass[index] +"><span>"+value+"</span></td>";		    		
		    	});

		    	$("#scheduleChart").html(chartHtml); 
		    	if( liHtml == '' ){
		    		liHtml = "<li><p align='center'><ikep4j:message pre='${preMsgProfile}' key='schedule.nodata'/></p></li>";
		    	}
		    	$("#scheduleList").html(liHtml); 

		    }
		});
	};
	
	
	$(document).ready(function() {
		getWorkspaceInfo('${user.userId}');
		getTodayMySchedule();
	});
	
})(jQuery);  
//-->
</script>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

           <div class="pr_schedule_t">
               <h3><ikep4j:message pre='${preProfileMain}' key='schedule.title'/></h3>
               <div class="date"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><ikep4j:timezone date="${today}" pattern="yyyy.MM.dd (EEE)"/></c:when><c:otherwise><ikep4j:timezone date="${today}" pattern="yyyy.MM.dd EEE."/></c:otherwise></c:choose> <% //pattern="message.support.timezone.dateformat.type1" %></div>
               <div class="more"><a onclick="goMySchedule();" href="#a"><img src="<c:url value='/base/images/common/btn_more.gif' />" alt="more" /></a></div>
                                    <div class="pr_schedule_c">
                                <table summary="" class="pr_schedule_time">
                                    <caption></caption>
                                    <tbody>
                                        <tr>
                                            <td class="textCenter" style="width:14.28%">06:00</td>
                                            <td style="width:7.14%;"></td>
                                            <td class="textCenter" style="width:14.28%">09:00</td>
                                            <td style="width:7.14%;"></td>
                                            <td class="textCenter" style="width:14.28%">12:00</td>
                                            <td style="width:7.14%;"></td>
                                            <td class="textCenter" style="width:14.28%">15:00</td>
                                            <td style="width:7.14%;"></td>
                                            <td class="textCenter" style="width:14.28%">18:00</td>
                                        </tr>
                                    </tbody>
                                </table>
                                <table summary="My Schedule">
                                    <caption></caption>
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th></th>
                                            <th><img src="<c:url value='/base/images/common/bar_sch.gif' />" alt="" /></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th><img src="<c:url value='/base/images/common/bar_sch.gif' />" alt="" /></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th><img src="<c:url value='/base/images/common/bar_sch.gif' />" alt="" /></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th><img src="<c:url value='/base/images/common/bar_sch.gif' />" alt="" /></th>
                                            <th></th>
                                        </tr>
                                    </thead>                                    
                                    <tbody>
                                        <tr id="scheduleChart">
                                        </tr>
                                    </tbody>
                                </table>
                                <ul id="scheduleList">
                                </ul>
                            </div>
               
           </div>
							