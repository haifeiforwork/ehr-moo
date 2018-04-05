<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%@ include file="/base/common/fileUploadControll.jsp"%>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="prefix">ui.lightpack.todo.readOrderView</c:set>
<c:set var="messagePrefix">ui.lightpack.todo.message</c:set>
<c:set var="buttonPrefix">ui.lightpack.todo.button</c:set>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
	
<script type="text/javascript">
(function($) { 
	$jq(document).ready(function(){
		// 메뉴 카운트 변경
		window.parent.setCountMyTodo("${countMyTodo}");
		
		// 파일 목록 생성
		iKEP.FileController.setFileList("#taskAttachFile", ${taskFiles});
		
		
	
		
	});

	showDetail = function(id) {
		var className = $jq("#tr_" + id).attr("class"); 
		if(className == "none detail") {
			$jq("#tr_" + id).attr("class", "detail"); 
			//<![CDATA[
			var output = "<img src='<c:url value='/base/images/icon/ic_btn_close.gif'/>' alt='Close' class='btn'/>Close";
			//]]>
			$jq("#span_" + id).attr("innerHTML", output);
		} else {
			$jq("#tr_" + id).attr("class", "none detail"); 
			//<![CDATA[
			var output = "<img src='<c:url value='/base/images/icon/ic_btn_open.gif'/>' alt='Open' class='btn'/>Open";
			//]]>
			$jq("#span_" + id).attr("innerHTML", output);
		}
		iKEP.iFrameContentResize();
	}
	

	
	
})(jQuery);	


function goDelete(taskId, searchConditionString){
	
	if(confirm("작업을 삭제하시겠습니까?")){
		
		var url = "<c:url value='/lightpack/todo/deleteTask.do?taskId='/>"+taskId+"&amp;searchConditionString="+searchConditionString;
		
		location.href = url;
	}
	
}
</script>

				<h1 class="none"></h1>

				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2>업무 지시 조회</h2>
				</div>
				<!--//pageTitle End-->
	
				<!--blockDetail 1 Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary1'/>">
						<caption></caption>
						<tbody>
							<tr>
								<th scope="row" width="18%"><ikep4j:message pre='${prefix}' key='order.title'/></th>
								<td width="32%">${task.title}</td>
								<th scope="row" width="18%"><ikep4j:message pre='${prefix}' key='order.status'/></th>
								<td width="32%"><c:if test="${task.taskStatus == 'A'}"><ikep4j:message pre='${messagePrefix}' key='status.running1'/></c:if><c:if test="${task.taskStatus == 'B'}"><ikep4j:message pre='${messagePrefix}' key='status.complete'/></c:if></td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre='${prefix}' key='order.director'/></th>
								<td colspan="3"><c:choose>
													<c:when test="${user.localeCode == portal.defaultLocaleCode}">${task.directorName}&nbsp;${task.directorJobRankName}&nbsp;${task.directorTeamName}</c:when>
													<c:otherwise>${task.directorEnglishName}&nbsp;${task.directorJobRankEnglishName}&nbsp;${task.directorTeamEnglishName}</c:otherwise>
												</c:choose>
								</td>
							</tr>
							<tr>
								<th scope="row">업무기한</th>
								<td><ikep4j:timezone date="${task.dueDate}" pattern="yyyy.MM.dd HH:mm"/></td>
								<th scope="row">업무착수</th>
								<td><ikep4j:timezone date="${task.startDate}" pattern="yyyy.MM.dd"/></td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre='${prefix}' key='order.worker'/></th>
								<td colspan="3">
									<c:forEach var="item" items="${taskUserList}">
										<c:choose>
											<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.workerName}&nbsp;${item.workerJobRankName}&nbsp;${item.workerTeamName}<br /></c:when>
											<c:otherwise>${item.workerEnglishName}&nbsp;${item.workerJobRankEnglishName}&nbsp;${item.workerTeamEnglishName}<br/></c:otherwise>
										</c:choose>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre='${prefix}' key='order.contents'/></th>
								<td colspan="3">
									<div class="todo_order_contents" title="<ikep4j:message pre='${prefix}' key='order.contents'/>">
										${fn:replace(task.taskContents, crlf, "<br/>")}
									</div>
								</td>
							</tr>		
							<tr>
								<th scope="row"><ikep4j:message pre='${prefix}' key='order.attachFile'/></th>
								<td colspan="3" id="taskAttachFile"></td>
							</tr>											
						</tbody>
					</table>
				</div>
				<!--//blockDetail 1 End-->
															
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<c:if test="${task.taskStatus == 'A'}">
						<li><a class="button" href="<c:url value='/lightpack/todo/updateTaskView.do?taskId=${task.taskId}&amp;searchConditionString=${searchConditionString}'/>"><span><ikep4j:message pre='${buttonPrefix}' key='modify'/></span></a></li>
						<li><a class="button" href="javascript:goDelete('${task.taskId}','${searchConditionString}');"><span><ikep4j:message pre='${buttonPrefix}' key='delete'/></span></a></li>
						</c:if>
						<li><a class="button" href="<c:url value='/lightpack/todo/listOrderView.do?searchConditionString=${searchConditionString}'/>"><span><ikep4j:message pre='${buttonPrefix}' key='list'/></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->									
				<div class="blockBlank_10px"></div>
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${prefix}' key='todo.main.title'/></h3>
				</div>
				<!--//subTitle_2 End-->
	
				<!--blockDetail_2 Start-->
				<div class="blockDetail_2">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary2'/>">
						<caption></caption>
						<tbody>
							<c:forEach var="item" items="${taskUserList}" varStatus="status">
							<tr>
								<th class="tdFirst" scope="row" width="18%">[<c:if test="${item.userStatus == 'A'}"><span class="colorPoint"><ikep4j:message pre='${messagePrefix}' key='status.running2'/></span></c:if><c:if test="${item.userStatus == 'B'}"><ikep4j:message pre='${messagePrefix}' key='status.complete'/></c:if>]</th>
								<td class="tdFirst" width="*">
									<c:choose>
										<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.workerName}&nbsp;${item.workerJobRankName}&nbsp;${item.workerTeamName}</c:when>
										<c:otherwise>${item.workerEnglishName}&nbsp;${item.workerJobRankEnglishName}&nbsp;${item.workerTeamEnglishName}</c:otherwise>
									</c:choose>
									<ikep4j:timezone date="${item.userCompleteDate}" pattern="yyyy.MM.dd HH:mm"/>
								</td>
								<td class="tdFirst textRight" width="80"><a class="button_ic" href="#a" onclick="showDetail('${status.count}')"><span id="span_${status.count}"><img src="<c:url value='/base/images/icon/ic_btn_open.gif'/>" alt="Open" class="btn"/>Open</span></a></td>
							</tr>
							<tr class="none detail" id="tr_${status.count}">
								<th scope="row"></th>
								<td colspan="2">
									<div class="todo_proc_contents">
										<c:choose>
											<c:when test="${empty item.userContents}">
												업무 내역이 없습니다.
											</c:when>
											<c:otherwise>
												${fn:replace(item.userContents, crlf, "<br/>")}
											</c:otherwise>
										</c:choose>
									</div>
									<div class="todo_proc_files">
										<c:if test="${item.userAttachCount != 0}">
											<c:forEach var="files" items="${item.fileDataList}"> 
												<a href='<c:url value="/support/fileupload/downloadFile.do?fileId=${files.fileId}&amp;thumbnailYn=N" />'><img src="<c:url value='/base/images/icon/ic_attach.gif'/>" alt="download" />&nbsp;${files.fileRealName}</a><br/>
											</c:forEach>
										</c:if>
									</div>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<!--//blockDetail_2 End-->				