<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 
<%@ include file="/base/common/fileUploadControll.jsp"%>
<%@ include file="/base/common/DextfileUploadInit.jsp"%>
--%> 
<%pageContext.setAttribute("crlf", "\r\n"); %>

<c:set var="prefix">ui.lightpack.todo.readTodoView</c:set>
<c:set var="messagePrefix">ui.lightpack.todo.message</c:set>
<c:set var="buttonPrefix">ui.lightpack.todo.button</c:set>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript" src="<c:url value="/base/js/plupload/browserplus-min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plupload/plupload.full.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plupload/plupload.browserplus.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plupload/src/javascript/plupload.flash.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plupload/jquery.plupload.queue.min.customize.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/upload.controll.js"/>"></script>

<%--
<script type="text/javascript" src="<c:url value="/base/js/dextUpload.controll.js"/>"></script>
<SCRIPT FOR="FileUploadManager" Event="OnError(nCode, sMsg, sDetailMsg)" LANGUAGE="javascript">
		OnFileManagerError(nCode, sMsg, sDetailMsg);
</SCRIPT>
<script for="FileUploadManager" event="OnChangedStatus" language="javascript"> 
 		dextFileUploadRefresh();
        document.getElementById("imgBtn_fileAdd").focus();
</script>
--%>

<script type="text/javascript">
//<![CDATA[
    var oldFiles;
    var oldSizes;
              
(function($) {


	$jq(document).ready(function() {
		
		// 파일 목록 생성
		iKEP.FileController.setFileList("#taskAttachFile", ${taskFiles});
		
		//파일업로드 컨트롤로 생성
		/*var uploaderOptions = {
			files : ${fileDataListJson}, //수정일때
			isUpdate : true,    // 등록 및 수정일때 true
			allowFileType : "all"
		};
		var fileController = new iKEP.FileController("#todoForm", "#fileUploadArea", uploaderOptions);*/
		/*
		// 웹다이어리에서 파일 업로드는 사용하지 않음. 2014.09.17
		dextFileUploadInit(""+20*1024*1024 ,"1", "all");
		
	      
        <c:if test="${not empty fileDataListJson}"> 
        oldFiles = ${fileDataListJson};
        $jq.each(oldFiles,function(index){
            var fileInfo = $.extend({index:index}, this);
            document.getElementById("FileUploadManager").AddUploadedFile(fileInfo.fileId, fileInfo.fileRealName, fileInfo.fileSize);
         });
        
        dextFileUploadRefresh(); 
        oldSizes =document.getElementById("FileUploadManager").Size;
        </c:if> 
		*/

		//임시저장 클릭
		$jq('#tempSaveButton').click( function(event) {
			
			var contents = $jq("#userContents").val();
			if ("${subworkCode}" == "MYTASK" && contents.length < 1){
				contents = "${task.taskContents}";
				$jq("#userContents").val(contents);
			}
			if(contents.length < 1 || contents.length > 1000) {
				alert("<ikep4j:message pre='${messagePrefix}' key='validation.userContents'/>");
				$jq("#userContents").focus();
				return;
			}	
			
			if(confirm("<ikep4j:message pre="${messagePrefix}" key="save" />")) {
				
				/*
				// 웹다이어리에서 파일 업로드는 사용하지 않음. 2014.09.17
				//파일이 있을경우, 업로드를 먼저 실행함                
                if((oldSizes !=document.getElementById("FileUploadManager").Size)||(document.all["FileUploadManager"].Count>0)){//삭제한것이 있거나 추가한것이 있으면
                    btnTransfer_Onclick("todoForm");
                }else{
                	var form = $jq("#todoForm");
                	oldFileSetting(oldFiles , form);    
                    writeSubmit(form);
                } 
				*/
            	var form = $jq("#todoForm");
            	//oldFileSetting(oldFiles , form);    
                writeSubmit(form);
			}
		});
		
		writeSubmit = function(targetForm){
		   
                  targetForm.submit();
        };
		
		//작업완료 클릭
		$jq('#completeButton').click( function(event) { 
			debugger;
			var contents = $jq("#userContents").val();
			if ("${subworkCode}" == "MYTASK" && contents.length < 1){
				contents = "${task.taskContents}";
				$jq("#userContents").val(contents);
			}

			if(contents.length < 1 || contents.length > 1000) {
				alert("<ikep4j:message pre='${messagePrefix}' key='validation.userContents'/>");
				$jq("#userContents").focus();
				return;
			}
			
			if(confirm("<ikep4j:message pre="${messagePrefix}" key="process" />")) {
				
				
			/* 	fileController.upload(function(isSuccess, files) {
					if(isSuccess == true) {
						$jq('input[name=userStatus]').val("B");
						$jq("#todoForm").submit();
					}
				});  파일 업로드 컨트롤 바꾸면서 삭제*/
				
				/*
				// 웹다이어리에서 파일 업로드는 사용하지 않음. 2014.09.17
				if((oldSizes !=document.getElementById("FileUploadManager").Size)||(document.all["FileUploadManager"].Count>0)){
					$jq('#userStatus').val("B");
					btnTransfer_Onclick("todoForm");
	    		}else{
	    			//alert("파일업로드 없음");
	    			var form = $jq("#todoForm");
                	oldFileSetting(oldFiles , form); 
                	$jq('#userStatus').val("B");
	    			writeSubmit(form);
	    		}
				*/

    			var form = $jq("#todoForm");
            	//oldFileSetting(oldFiles , form); 
            	$jq('#userStatus').val("B");
    			writeSubmit(form);
				
			}
		});
		

	});

	showDetail = function(id) {
		var className = $jq("#tr_" + id).attr("class"); 
		if(className == "none detail") {
			$jq("#tr_" + id).attr("class", "detail"); 
			$jq("#span_" + id).attr("innerHTML", "<img src='<c:url value='/base/images/icon/ic_btn_close.gif'/>' alt='Close' class='btn'/>Close");
		} else {
			$jq("#tr_" + id).attr("class", "none detail"); 
			$jq("#span_" + id).attr("innerHTML", "<img src='<c:url value='/base/images/icon/ic_btn_open.gif'/>' alt='Open' class='btn'/>Open");
		}
		iKEP.iFrameContentResize();
	}

	/*
	function  oldFileSetting(oldFilelist, form){
		 $jq.each(oldFilelist,function(index){
			   var fileInfo = $jq.extend({index:index}, this);
			$jq(form).append('<input type="hidden" name="fileLinkList['+index+'].fileId" value="'+fileInfo.fileId+'"/>');
			$jq(form).append('<input type="hidden" name="fileLinkList['+index+'].flag" value="use"/>');
			$jq(form).append('<input type="hidden" name="fileLinkList['+index+'].fileSize" value="'+fileInfo.size+'"/>');
		});
	}
	*/
	
})(jQuery);

function goDelete(taskId, searchConditionString){
	
	if(confirm("작업을 삭제하시겠습니까?")){
		$jq.ajax({
			url : '<c:url value="/lightpack/todo/webDiaryDeleteMyTaskAjax.do"/>',
			type : "post",
			data : { taskId:taskId, searchConditionString:searchConditionString },
			success : function(result) {
				window.parent.getMyTaskList("","");
				window.parent.closeMyTask();
			}
		});
		/*
		var url = "<c:url value='/lightpack/todo/webDiaryDeleteMyTask.do?taskId='/>"+taskId+"&amp;searchConditionString="+searchConditionString;
		
		location.href = url;
		*/
	}
	
}

function goUpdate(taskId, searchConditionString){
	/*
	var p = this.parent;
	p.updateMyTask(taskId, searchConditionString);
	*/
	window.parent.updateMyTask(taskId, searchConditionString);
}

//]]>
</script>

				<h1 class="none"></h1>

				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2>나의 업무 조회</h2>
				</div>
				<!--//pageTitle End-->
	
				<!--blockDetail 1 Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary1'/>">
						<caption></caption>
						<tbody>
							<tr>
								<th scope="row" width="20%"><ikep4j:message pre='${prefix}' key='order.title'/></th>
								<td colspan="3">${task.title}</td>
							</tr>
							<tr>	
								<th scope="row" width="16%"><ikep4j:message pre='${prefix}' key='order.status'/></th>
								<td colspan="3"><c:if test="${task.taskStatus == 'A'}"><ikep4j:message pre='${messagePrefix}' key='status.running1'/></c:if><c:if test="${task.taskStatus == 'B'}"><ikep4j:message pre='${messagePrefix}' key='status.complete'/></c:if></td>
							</tr>
							<c:if test="${task.subworkCode == 'TASK'}">
							<tr>
								<th scope="row"><ikep4j:message pre='${prefix}' key='order.director'/></th>
								<td colspan="3"><c:choose>
													<c:when test="${user.localeCode == portal.defaultLocaleCode}">${task.directorName}&nbsp;${task.directorJobRankName}&nbsp;${task.directorTeamName}</c:when>
													<c:otherwise>${task.directorEnglishName}&nbsp;${task.directorJobRankEnglishName}&nbsp;${task.directorTeamEnglishName}</c:otherwise>
												</c:choose>
								</td>
							</tr>
							</c:if>
							<tr>
								<th scope="row">업무기한</th>
								<td colspan="3"><ikep4j:timezone date="${task.dueDate}" pattern="yyyy.MM.dd HH:mm"/></td>
							</tr>
							<tr>	
								<th scope="row">업무착수</th>
								<td colspan="3"><ikep4j:timezone date="${task.startDate}" pattern="yyyy.MM.dd"/></td>
							</tr>
							<c:if test="${task.subworkCode == 'TASK'}">
							<tr>
								<th scope="row"><ikep4j:message pre='${prefix}' key='order.worker'/></th>
								<td colspan="3">
									<c:forEach var="item" items="${taskUserList}">
										<c:choose>
											<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.workerName}&nbsp;${item.workerJobRankName}&nbsp;${item.workerTeamName}<br/></c:when>
											<c:otherwise>${item.workerEnglishName}&nbsp;${item.workerJobRankEnglishName}&nbsp;${item.workerTeamEnglishName}<br/></c:otherwise>
										</c:choose>
									</c:forEach>
								</td>
							</tr>
							</c:if>
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
				
				<!--blockDetail 2 Start-->
				<div class="blockButton mb15">
					<ul>
						<c:if test="${task.subworkCode != 'TASK'}">
						<%--
						<li><a class="button" href="<c:url value='/lightpack/todo/webDiaryUpdateTodoView.do?taskId=${task.taskId}&amp;searchConditionString=${searchConditionString}'/>"><span><ikep4j:message pre='${buttonPrefix}' key='modify'/></span></a></li>
						--%>
						<li><a class="button" href="javascript:goUpdate('${task.taskId}','${searchConditionString}');"><span><ikep4j:message pre='${buttonPrefix}' key='modify'/></span></a></li>
						<li><a class="button" href="javascript:goDelete('${task.taskId}','${searchConditionString}');"><span><ikep4j:message pre='${buttonPrefix}' key='delete'/></span></a></li>
						</c:if>
						<%--
						<li><a class="button" href="#none" id="tempSaveButton"><span><ikep4j:message pre='${buttonPrefix}' key='tempSave'/></span></a></li>
						--%>
						<li><a class="button" href="#none" id="completeButton"><span>업무완료</span></a></li>
						<%--
						<li><a class="button" href="<c:url value='/lightpack/todo/listTodoView.do?searchConditionString=${searchConditionString}'/>"><span><ikep4j:message pre='${buttonPrefix}' key='list'/></span></a></li>
						--%>
					</ul>
				</div>
				<!--//blockDetail 2 End-->	

				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${prefix}' key='todo.main.title'/></h3>
				</div>
				<!--//subTitle_2 End-->
	
				<c:if test="${taskUser.userStatus == 'A'}">
				<!--blockDetail 2 Start-->
				<div class="blockDetail">
				<form id="todoForm" name="todoForm" action="<c:url value="/lightpack/todo/webDiarySaveTodo.do"/>" method="post"> 
					<input type="hidden" name="workerId" value="${taskUser.workerId}"/>
					<input type="hidden" name="taskId" value="${taskUser.taskId}"/>
					<input type="hidden" name="userStatus" id="userStatus" value="${taskUser.userStatus}"/>
					<input type="hidden" name="searchConditionString" value="${searchConditionString}"/>
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary2'/>">
						<caption></caption>
						<colgroup>
							<col width="20%" />
							<col width="32%" />
							<col width="16%" />
							<col width="32%" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row"><ikep4j:message pre='${prefix}' key='todo.worker'/></th>
								<td colspan="3">[<c:if test="${taskUser.userStatus == 'A'}"><span class="colorPoint"><ikep4j:message pre='${messagePrefix}' key='status.running2'/></span></c:if><c:if test="${taskUser.userStatus == 'B'}"><ikep4j:message pre='${messagePrefix}' key='status.complete'/></c:if>]
												 <c:choose>
													<c:when test="${user.localeCode == portal.defaultLocaleCode}">${taskUser.workerName}&nbsp;${taskUser.workerJobRankName}&nbsp;${taskUser.workerTeamName}</c:when>
													<c:otherwise>${taskUser.workerEnglishName}&nbsp;${taskUser.workerJobRankEnglishName}&nbsp;${taskUser.workerTeamEnglishName}</c:otherwise>
												</c:choose>
								</td>
							</tr>						
							<tr>
								<th scope="row"><ikep4j:message pre='${prefix}' key='todo.updateDate'/></th>
								<td><c:if test="${empty taskUser.updateDate}">N/A</c:if>
												<c:if test="${!empty taskUser.updateDate}"><ikep4j:timezone date="${taskUser.updateDate}" pattern="yyyy.MM.dd HH:mm"/></c:if>
								</td>
								<th scope="row"><ikep4j:message pre='${prefix}' key='todo.completeDate'/></th>
								<td><c:if test="${empty taskUser.userCompleteDate}">N/A</c:if>
												<c:if test="${!empty taskUser.userCompleteDate}"><ikep4j:timezone date="${taskUser.userCompleteDate}" pattern="yyyy.MM.dd HH:mm"/></c:if>
								</td>
							</tr>	
							<tr>
								<th scope="row">업무내용</th>
								<td colspan="3"><textarea rows="5" cols="40" class="inputbox w100" id="userContents" name="userContents">${taskUser.userContents}</textarea></td>
							</tr>									
						</tbody>
					</table>
					<div class="fileAttachArea" style="display:none;">
						<div class="wrap">
							<div class="th" style="width:18%;">
								<div class="title"><ikep4j:message pre='${prefix}' key='todo.attachFile'/></div>
							</div>
							<div class="td" style="margin-left:18%;">
								<div class="content">
									<table style="width:100%;">
								      <tr>
								          <td colspan="2" style="border-color:#e5e5e5">
								              <OBJECT id="FileUploadManager" codeBase="<c:url value="/Bin/DEXTUploadX.cab#version=2,8,2,0"/>"
								                  height="140" width="100%" classid="CLSID:DF75BAFF-7DD5-4B83-AF5E-692067C90316" VIEWASTEXT>
								                   <param name="ButtonVisible" value="FALSE" />
								                   <param name="VisibleContextMenu" value="FALSE" />
								                   <param name="StatusBarVisible" value="FALSE" />
								                   <param name="VisibleListViewFrame" value="FALSE" />
								              </OBJECT>
								          </td>
								      <tr>								
								      <tr>
								          <td style="border-right:none;border-color:#e5e5e5;background-color:#e8e8e8">전체 <span id="_StatusInfo_count"></span>개 <span id="_StatusInfo_size"></span><span id="_Total_size"></span></div>
								          <td align="right" style="border-left:none;border-color:#e5e5e5;background-color:#e8e8e8;">
								          <img src="<c:url value="/base/images/dextupload/btn_fileplus_normal.gif"/>" id="imgBtn_fileAdd" name="Image2"  border="0" onclick="btnAddFile_Onclick()" style="cursor:pointer;valign:absmiddle" />
								          <img src="<c:url value="/base/images/dextupload/btn_listdelete_normal.gif"/>" name="Image4"  border="0" onclick="btnDeleteItem_Onclick()" style="cursor:pointer;valign:absmiddle" />    
								          </td>
								      </tr>
								  </table>
								</div>
							</div>
						</div>
					</div>
				</form>	
				</div>
				<!--//blockDetail 2 End-->		
				</c:if> <!--//taskUser.userStatus == 'A'  -->
	
				<c:if test="${taskUser.userStatus == 'B' || fn:length(taskUserList) != 1}">
				<!--blockDetail_2 Start-->
				<div class="blockDetail_2">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary2'/>">
						<caption></caption>
						<tbody>
							<c:forEach var="item" items="${taskUserList}" varStatus="status">
							<tr>
								<th class="tdFirst" scope="row" width="140">[<c:if test="${item.userStatus == 'A'}"><span class="colorPoint"><ikep4j:message pre='${messagePrefix}' key='status.running2'/></span></c:if><c:if test="${item.userStatus == 'B'}"><ikep4j:message pre='${messagePrefix}' key='status.complete'/></c:if>]</th>
								<td class="tdFirst" width="*">
									<c:choose>
										<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.workerName}&nbsp;${item.workerJobRankName}&nbsp;${item.workerTeamName}</c:when>
										<c:otherwise>${item.workerEnglishName}&nbsp;${item.workerJobRankEnglishName}&nbsp;${item.workerTeamEnglishName}</c:otherwise>
									</c:choose>
									<ikep4j:timezone date="${item.updateDate}" pattern="yyyy.MM.dd HH:mm"/>
								</td>
								<td class="tdFirst textRight" width="80"><a class="button_ic" href="#a" onclick="showDetail('${status.count}')"><span id="span_${status.count}"><img src="<c:url value='/base/images/icon/ic_btn_open.gif'/>" alt="Open" class="btn"/>Open</span></a></td>
							</tr>
							<tr class="none detail" id="tr_${status.count}">
								<th scope="row"></th>
								<td colspan="2">
									<div class="todo_proc_contents">
										<c:choose>
											<c:when test="${empty item.userContents}">
												<ikep4j:message pre='${messagePrefix}' key='contents.nothing'/>
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
				</c:if> <!--//taskUser.userStatus == 'B' || fn:length(taskUserList) != 1 -->
