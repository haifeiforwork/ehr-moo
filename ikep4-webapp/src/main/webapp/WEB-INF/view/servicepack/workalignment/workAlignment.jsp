<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" 	value="${sessionScope['ikep.user']}" />

<c:set var="preUi" 	value="ui.servicepack.workalignment.main" />
<c:set var="preForm" 	value="ui.servicepack.workalignment" />

<c:set var="format1"><ikep4j:message pre='${preUi}' key='dateFomat1'/></c:set>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.postjson.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/fullcalendar-1.5-cust.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/event_class.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/workAlignmentCalendar.js"/>"></script>

<script  type="text/javascript">
//<![CDATA[
           
      document.write('<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/planner/fullcalendar-1.5.css"/>" />');
      document.write('<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/planner/calendar.css"/>" />');
      
           
	//todo 제목 클릭
	function goURL(isTodo, target, url, systemCode, subworkCode, taskKey, workerId) {
		if(isTodo == "N") {
			alert("<ikep4j:message key='ui.lightpack.todo.message.goUrl'/>");
		} else {
			location.href = "subMain.do?type=todo&systemCode=" + systemCode + "&subworkCode=" + subworkCode + "&taskKey=" + taskKey + "&workerId=" + workerId + "&url=" + url;
		}
	}

	function orderLink(id){
		location.href ="subMain.do?type=todoRead&taskId="+id;
	}

	function viewPopUpProfile(targetUserId){
		var pageURL = "<c:url value='/support/profile/getProfilePopup.do' />" + "?targetUserId=" + targetUserId;
		iKEP.popupOpen( pageURL , {width:800, height:280 , callback:function(result) {} });
	}
	
	function getApprDetail(apprId, listType, entrustUserId) {
		if(entrustUserId == undefined || entrustUserId == '') {
			location.href="<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>?apprId="+apprId+"&listType="+listType;
		}
		else {
			location.href="<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>?apprId="+apprId+"&listType="+listType+"&entrustUserId="+entrustUserId;
		}
	}
	
	//]]>	
</script>


<h1 class="none">컨텐츠영역</h1>
				
<table class="framebook" summary="">
	<caption></caption>
	<tbody>
		<tr>
			<td class="framebook_l">
				
				<div class="day">
					<h2 class="none">date</h2>
					<div class="day_dd today"><fmt:formatDate value="${todayTop}" pattern="dd" /> </div>
					
					<div class="day_yymm"><fmt:formatDate value="${todayTop}" pattern="${format1 }" /></div>
					<div class="today_swf" id="todaySwf">
						<script type="text/javascript">iKEP.createSWFEmbedObject("#todaySwf","<c:url value="/base/swf/today.swf"/>", 135, 56);</script>
					</div>
				</div>
											
				<div class="bgTitle_4"><h3><ikep4j:message pre='${preUi}' key='todayWork'/> <span class="colorPoint">(${fn:length(todayFirst) })</span></h3></div>
				<div class="framebook_innerTable mb10">
					<div class="fixed_height">
						<table summary="<ikep4j:message pre='${preUi}' key='todayWork'/>">
							<caption></caption>
							<tbody>
							<c:forEach var="tmp" items="${todayFirst}" varStatus="inx" >
								<c:choose>
									<c:when test="${fn:indexOf(tmp.todoStatusName,'D') > -1}">
										<c:set var="toText" ><ikep4j:message key='ui.lightpack.todo.message.status.delay'/></c:set>
										<c:set var="toClass" value="5"/>
									</c:when>
									<c:otherwise>
										<c:set var="toText" ><ikep4j:message key='ui.lightpack.todo.message.status.running1'/></c:set>
										<c:set var="toClass" value="4"/>
									</c:otherwise>
								</c:choose>
								<tr>
									<td class="date bold"><ikep4j:timezone date="${tmp.dueDate}" pattern="ui.servicepack.workalignment.main.dateFomat2" keyString="true"/></td>
									<td width="38"><div class="cate_block_${toClass }"><span class="cate_tit_${toClass }">${toText}</span></div></td>
									<td class="bold">
									<c:if test="${tmp.systemCode == todoSystemCode && tmp.subworkCode == todoSubworkCode}">
										<div class="ellipsis"><a href="#a" onclick="goURL('Y', '${tmp.target}', '<c:url value="${tmp.url}"/>', '${tmp.systemCode}', '${tmp.subworkCode}', '${tmp.taskKey}', '${tmp.workerId}')">${tmp.title}</a></div>
									</c:if>
									<c:if test="${tmp.systemCode != todoSystemCode || tmp.subworkCode != todoSubworkCode}">
										<div class="ellipsis"><a href="#a" onclick="goURL('N', '${tmp.target}', '<c:url value="${tmp.url}"/>', '${tmp.systemCode}', '${tmp.subworkCode}', '${tmp.taskKey}', '${tmp.workerId}')">${tmp.title}</a></div>
									</c:if>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

				<!--오늘의 일정 Start-->
				<div id="todayCalendar"></div>
				
				<!--//오늘의 일정 End-->					

				<div class="blockBlank_20px"></div>
							
				<!--framebg Start-->				
				<div class="framebg">
					<!--subTitle_2 Start-->
					<div class="subTitle_2">
						<h3><ikep4j:message pre='${preUi}' key='approval'/> <span class="colorPoint">&nbsp;</span></h3>
						<div class="btn_more"><a href="<c:url value="/approval/work/apprlist/listApprTodo.do"/>"><img src="<c:url value="/base/images/common/btn_more.gif"/>" alt="more" /></a></div>
					</div>
					<!--//subTitle_2 End-->	
					<div class="framebook_innerTable">
						<div class="fixed_height">
							<table summary="<ikep4j:message pre='${preUi}' key='approval'/>">
								<caption></caption>
									<tbody>
									<c:if test="${!myTodoResult.emptyRecord}">
		        						<c:forEach var="aplist" items="${myTodoResult.entity}"  varStatus="status">
		        							<c:if test="${status.count <= 5}">
		        							<tr>
		        								<td class="date bold" title="<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprReqDate}"/>"><ikep4j:timezone pattern="MM.dd" date="${aplist.apprReqDate}"/></td>
												<td width="*" class="textLeft ellipsis">
													<c:if test="${aplist.approvalId ne user.userId}">
			        									<a href="#a" title="${aplist.apprTitle}" onclick="getApprDetail('${aplist.apprId}','listApprTodo','${aplist.approvalId}');">
			        										${aplist.apprTitle}
			        									</a>
			        								</c:if>
			        								<c:if test="${aplist.approvalId eq user.userId}">
			        									<a href="#a" title="${aplist.apprTitle}" onclick="getApprDetail('${aplist.apprId}','listApprTodo','');">
			        										${aplist.apprTitle}
			        									</a>
			        								</c:if>
												</td>
												<td width="60" style="text-align:center">
													<a href="#a" onclick="iKEP.showUserContextMenu(this, '${aplist.registerId}', 'bottom')">${aplist.registerName}</a>
												</td>
		        							</tr>
		        							</c:if>
		        						</c:forEach>
		        					</c:if>
		        					<c:if test="${myTodoResult.emptyRecord}">
		        					<tr>
		        						<td class="textCenter" colspan="3">&nbsp;</td>
		        					</tr>				
		        					</c:if>					
									</tbody>
							</table>
						</div>
					</div>

					<!--subTitle_2 Start-->
					<div class="subTitle_2">
						<h3><ikep4j:message pre='${preUi}' key='request'/></h3>
						<div class="btn_more"><a href="<c:url value="/approval/work/apprlist/listApprMyRequest.do"/>"><img src="<c:url value="/base/images/common/btn_more.gif"/>" alt="more" /></a></div>
					</div>
					<!--//subTitle_2 End-->	
					<div class="framebook_innerTable">
						<div class="fixed_height">
							<table summary="<ikep4j:message pre='${preUi}' key='request'/>">
								<caption></caption>
								<tbody>
								<c:if test="${!myRequestResult.emptyRecord}">
	        						<c:forEach var="aplist" items="${myRequestResult.entity}"  varStatus="status">
	        							<c:if test="${status.count <= 5}">
	        							<tr>
	        								<td class="date bold" title="<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprReqDate}"/>"><ikep4j:timezone pattern="MM.dd" date="${aplist.apprReqDate}"/></td>
											<td width="*" class="textLeft ellipsis">
												<a href="#a" title="${aplist.apprTitle}" onclick="getApprDetail('${aplist.apprId}','myRequestList');">
	        										${aplist.apprTitle}
	        									</a>
											</td>
											<td width="60" style="text-align:center">
												<c:if test="${aplist.apprDocStatus == '1'}">
        											<ikep4j:message pre="${preForm}" key="progress" />
        										</c:if>
        										<c:if test="${aplist.apprDocStatus == '2'}">
        											<ikep4j:message pre="${preForm}" key="complete" />
        										</c:if>
        										<c:if test="${aplist.apprDocStatus == '3'}">
        											<ikep4j:message pre="${preForm}" key="reject" />
        										</c:if>
        										<c:if test="${aplist.apprDocStatus == '4'}">
        											<ikep4j:message pre="${preForm}" key="return" />
        										</c:if>
        										<c:if test="${aplist.apprDocStatus == '5'}">
        											<ikep4j:message pre="${preForm}" key="error" />
        										</c:if>
        										<c:if test="${aplist.apprDocStatus == '6'}">
        											<ikep4j:message pre="${preForm}" key="wait" />
        										</c:if>
											</td>
												
	        							</tr>
	        							</c:if>
	        						</c:forEach>
	        					</c:if>
	        					<c:if test="${myRequestResult.emptyRecord}">
	        					<tr>
	        						<td class="textCenter" colspan="3">&nbsp;</td>
	        					</tr>				
	        					</c:if>					
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!--//framebg End-->				
									
			</td>
			<td class="framebook_r">

				<div class="day">
					<h2 class="none">date</h2>
					<div class="day_dd"><fmt:formatDate value="${tomorrowTop}" pattern="dd" /></div>
					<div class="day_yymm"><fmt:formatDate value="${tomorrowTop}" pattern="${format1 }" /></div>
				</div>
											
				<div class="bgTitle_4"><h3><ikep4j:message pre='${preUi}' key='tomarrowWork'/> <span class="colorPoint">(${fn:length(tomorrowFirst) })</span></h3></div>
				<div class="framebook_innerTable mb10">
					<div class="fixed_height">
						<table summary="<ikep4j:message pre='${preUi}' key='tomarrowWork'/>">
							<caption></caption>
							<tbody>
								
								<c:forEach var="tmp" items="${tomorrowFirst}" varStatus="inx" >
								<c:choose>
									<c:when test="${fn:indexOf(tmp.todoStatusName,'D') > -1}">
										<c:set var="toText" ><ikep4j:message key='ui.lightpack.todo.message.status.delay'/></c:set>
										<c:set var="toClass" value="5"/>
									</c:when>
									<c:otherwise>
										<c:set var="toText" ><ikep4j:message key='ui.lightpack.todo.message.status.running1'/></c:set>
										<c:set var="toClass" value="4"/>
									</c:otherwise>
								</c:choose>
								<tr>
									<td class="date bold"><ikep4j:timezone date="${tmp.dueDate}" pattern="ui.servicepack.workalignment.main.dateFomat2" keyString="true"/></td>
									<td width="38"><div class="cate_block_${toClass }"><span class="cate_tit_${toClass }">${toText}</span></div></td>
									<td class="bold">
										<c:if test="${tmp.systemCode == todoSystemCode && tmp.subworkCode == todoSubworkCode}">
											<div class="ellipsis"><a href="#a" onclick="goURL('Y', '${tmp.target}', '<c:url value="${tmp.url}"/>', '${tmp.systemCode}', '${tmp.subworkCode}', '${tmp.taskKey}', '${tmp.workerId}')">${tmp.title}</a></div>
										</c:if>
										<c:if test="${tmp.systemCode != todoSystemCode || tmp.subworkCode != todoSubworkCode}">
											<div class="ellipsis"><a href="#a" onclick="goURL('N', '${tmp.target}', '<c:url value="${tmp.url}"/>', '${tmp.systemCode}', '${tmp.subworkCode}', '${tmp.taskKey}', '${tmp.workerId}')">${tmp.title}</a></div>
										</c:if>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>	
				</div>

				<!--내일의 일정 Start-->
				<div id="nextdayCalendar"></div>
				<!--//내일의 일정 End-->
				
				<div class="blockBlank_20px"></div>
				
				<!--framebg Start-->
				<div class="framebg">
					<!--subTitle_2 Start-->
					<div class="subTitle_2">
						<h3><ikep4j:message pre='${preUi}' key='toDo'/> <span class="colorPoint">(${fn:length(todoList) })</span></h3>
						<div class="btn_more"><a href="subMain.do?type=todo&amp;link=1"><img src="<c:url value="/base/images/common/btn_more.gif"/>" alt="more" /></a></div>
					</div>
					<!--//subTitle_2 End-->	
					<div class="framebook_innerTable">
						<div class="fixed_height">
							<table summary="<ikep4j:message pre='${preUi}' key='toDo'/>">
								<caption></caption>
								<tbody>
								<c:forEach var="item" items="${todoList}" varStatus="inx" begin="0" end="3">
									<tr>
										<td class="date bold"><ikep4j:timezone date="${item.dueDate}" pattern="ui.servicepack.workalignment.main.dateFomat2" keyString="true"/></td>
										<td class="bold">
										<c:if test="${item.systemCode == todoSystemCode && item.subworkCode == todoSubworkCode}">
											<div class="ellipsis"><a href="#a" onclick="goURL('Y', '${item.target}', '<c:url value="${item.url}"/>', '${item.systemCode}', '${item.subworkCode}', '${item.taskKey}', '${item.workerId}')">${item.title}</a></div>
										</c:if>
										<c:if test="${item.systemCode != todoSystemCode || item.subworkCode != todoSubworkCode}">
											<div class="ellipsis"><a href="#a" onclick="goURL('N', '${item.target}', '<c:url value="${item.url}"/>', '${item.systemCode}', '${item.subworkCode}', '${item.taskKey}', '${item.workerId}')">${item.title}</a></div>
										</c:if>
										
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>	
					</div>										

					<!--subTitle_2 Start-->
					<div class="subTitle_2">
						<h3><ikep4j:message pre='${preUi}' key='order'/></h3>
						<div class="btn_more"><a href="subMain.do?type=todo&amp;link=2"><img src="<c:url value="/base/images/common/btn_more.gif"/>" alt="more" /></a></div>
					</div>
					<!--//subTitle_2 End-->	
					<div class="framebook_innerTable">
						<div class="fixed_height">
							<table summary="<ikep4j:message pre='${preUi}' key='order'/>">
								<caption></caption>
								<tbody>
								<c:forEach var="tmp" items="${orderList}" varStatus="inx" begin="0" end="3">
								
									<c:choose>
										<c:when test="${fn:indexOf(tmp.todoStatusName,'D') > -1}">
											<c:set var="toText" ><ikep4j:message key='ui.lightpack.todo.message.status.delay'/></c:set>
											<c:set var="toClass" value="4"/>
										</c:when>
										<c:when test="${fn:indexOf(tmp.todoStatusName,'B') > -1}">
											<c:set var="toText" ><ikep4j:message key='ui.lightpack.todo.message.status.complete'/></c:set>
											<c:set var="toClass" value="5"/>
										</c:when>
										<c:otherwise>
											<c:set var="toText" ><ikep4j:message key='ui.lightpack.todo.message.status.running1'/></c:set>
											<c:set var="toClass" value="6"/>
										</c:otherwise>
									</c:choose>
									
									<tr>
										<td class="date"><ikep4j:timezone date="${tmp.dueDate}" pattern="ui.servicepack.workalignment.main.dateFomat2" keyString="true"/></td>
										<td width="38"><div class="cate_block_${toClass }"><span class="cate_tit_${toClass }">${toText}</span></div></td>
										<td><a href="#a" onclick="orderLink('${tmp.taskKey}');">${tmp.title}</a></td>
										<td class="name"><a href="#a" onclick="viewPopUpProfile('${tmp.workerId}');return false;" title="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${tmp.teamName}</c:when><c:otherwise>${tmp.teamEnglishName}</c:otherwise></c:choose>"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${tmp.etcName} ${tmp.jobTitleName}</c:when><c:otherwise>${tmp.etcEnglishName} ${tmp.jobTitleEnglishName}</c:otherwise></c:choose></a></td>
									</tr>
								</c:forEach>
									
								</tbody>
							</table>
						</div>
					</div>
				</div>	
				<!--//framebg End-->
																					
			</td>
		</tr>
	</tbody>				
</table>

				
				
				